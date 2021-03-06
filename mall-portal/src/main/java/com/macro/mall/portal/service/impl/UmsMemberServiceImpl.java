package com.macro.mall.portal.service.impl;

import com.aliyuncs.CommonResponse;
import com.macro.mall.mapper.UmsMemberLevelMapper;
import com.macro.mall.mapper.UmsMemberMapper;
import com.macro.mall.model.UmsMember;
import com.macro.mall.model.UmsMemberExample;
import com.macro.mall.model.UmsMemberLevel;
import com.macro.mall.model.UmsMemberLevelExample;
import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.domain.MemberDetails;
import com.macro.mall.portal.model.UserMemberModel;
import com.macro.mall.portal.model.UserMemberStatus;
import com.macro.mall.portal.service.RedisService;
import com.macro.mall.portal.service.UmsMemberService;
import com.macro.mall.portal.util.JwtTokenUtil;
import com.macro.mall.sms.service.SmsService;
import com.macro.mall.weixin.bean.SNSUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsMemberServiceImpl.class);
    @Value("${authCode.expire.seconds}")
    private Long AUTH_CODE_EXPIRE_SECONDS;
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UmsMemberLevelMapper memberLevelMapper;
    @Autowired
    private UmsMemberMapper memberMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Value("${portal.verify.code}")
    private boolean verifyCode;

    @Override
    public UmsMember getByUsername(String username) {
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsMember> memberList = memberMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(memberList)) {
            return memberList.get(0);
        }
        return null;
    }

    @Override
    public UmsMember getById(Long id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    @Override
    public CommonResult register(String username, String password, String telephone, String authCode, String mail) {
        //验证验证码
        if (verifyCode && !verifyAuthCode(authCode, telephone)) {
            return new CommonResult().failed("验证码错误");
        }
        //查询是否已有该用户
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andUsernameEqualTo(username);
        example.or(example.createCriteria().andPhoneEqualTo(telephone));
        List<UmsMember> umsMembers = memberMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(umsMembers)) {
            return new CommonResult().failed("该用户已经存在");
        }
        //没有该用户进行添加操作
        UmsMember umsMember = new UmsMember();
        umsMember.setUsername(username);
        umsMember.setPhone(telephone);
        umsMember.setPassword(passwordEncoder.encode(password));
        umsMember.setCreateTime(new Date());
        umsMember.setStatus(1);
        umsMember.setMail(mail);
        //获取默认会员等级并设置
        UmsMemberLevelExample levelExample = new UmsMemberLevelExample();
        levelExample.createCriteria().andDefaultStatusEqualTo(1);
        List<UmsMemberLevel> memberLevelList = memberLevelMapper.selectByExample(levelExample);
        if (!CollectionUtils.isEmpty(memberLevelList)) {
            umsMember.setMemberLevelId(memberLevelList.get(0).getId());
        }
        memberMapper.insert(umsMember);
        umsMember.setPassword(null);
        return new CommonResult().success("注册成功", null);
    }

    @Override
    public CommonResult generateAuthCode(String countryCode, String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        //验证码绑定手机号并存储到redis
        CommonResponse commonResponse = smsService.sendSms(countryCode, telephone, sb.toString());
        if(commonResponse.getHttpResponse().isSuccess()) {
            redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + telephone, sb.toString());
            redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + telephone, AUTH_CODE_EXPIRE_SECONDS);
            return new CommonResult().success("获取验证码成功");
        }
        return new CommonResult().success("获取验证码失败");
    }

    @Override
    public CommonResult updatePassword(String telephone, String password, String authCode) {
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andPhoneEqualTo(telephone);
        List<UmsMember> memberList = memberMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(memberList)) {
            return new CommonResult().failed("该账号不存在");
        }
        //验证验证码
        if (!verifyAuthCode(authCode, telephone)) {
            return new CommonResult().failed("验证码错误");
        }
        UmsMember umsMember = memberList.get(0);
        umsMember.setPassword(passwordEncoder.encode(password));
        memberMapper.updateByPrimaryKeySelective(umsMember);
        return new CommonResult().success("密码修改成功", null);
    }

    @Override
    public void updateUserInfo(UserMemberModel userMemberModel) {
        UmsMember currentMember = getCurrentMember();
        UmsMember umsMember = memberMapper.selectByPrimaryKey(currentMember.getId());
        umsMember.setIcon(userMemberModel.getHeadPic());
        umsMember.setPhone(userMemberModel.getTelephone());
        umsMember.setMail(userMemberModel.getEmail());
        umsMember.setNickname(userMemberModel.getNickname());
        memberMapper.updateByPrimaryKey(umsMember);
    }

    @Override
    public CommonResult wxAuthInit(SNSUserInfo snsUserInfo) {
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andWxOpenIdEqualTo(snsUserInfo.getOpenId());
        List<UmsMember> umsMembers = memberMapper.selectByExample(example);
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(umsMembers)) {
            return new CommonResult().failed("已經初始化");
        }
        UmsMember umsMember = new UmsMember();
        umsMember.setCreateTime(new Date());
        umsMember.setUsername(snsUserInfo.getNickname());
        umsMember.setGender(snsUserInfo.getSex());
        umsMember.setIcon(snsUserInfo.getHeadImgUrl());
        umsMember.setWxOpenId(snsUserInfo.getOpenId());
        umsMember.setWxUnionId(snsUserInfo.getUnionid());
        umsMember.setStatus(UserMemberStatus.INIT.getValue());
        int insert = memberMapper.insert(umsMember);
        return new CommonResult().success(insert);
    }

    @Override
    public UmsMember getCurrentMember() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        MemberDetails memberDetails = (MemberDetails) auth.getPrincipal();
        return memberDetails.getUmsMember();
    }

    @Override
    public void updateIntegration(Long id, Integer integration) {
        UmsMember record = new UmsMember();
        record.setId(id);
        record.setIntegration(integration);
        memberMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, passwordEncoder.encode(password));
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            token = jwtTokenUtil.generateToken(userDetails);
            updateLoginTimeByUsername(username);
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    private void updateLoginTimeByUsername(String username) {
        UmsMember record = new UmsMember();
        record.setCreateTime(new Date());
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andUsernameEqualTo(username);
        memberMapper.updateByExampleSelective(record, example);
    }

    /**
     * 添加登录记录
     *
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
//        UmsMember admin = getAdminByUsername(username);
//        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
//        loginLog.setAdminId(admin.getId());
//        loginLog.setCreateTime(new Date());
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        loginLog.setIp(request.getRemoteAddr());
//        loginLogMapper.insert(loginLog);
    }

    //对输入的验证码进行校验
    private boolean verifyAuthCode(String authCode, String telephone) {
        if (StringUtils.isEmpty(authCode)) {
            return false;
        }
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        return authCode.equals(realAuthCode);
    }

}
