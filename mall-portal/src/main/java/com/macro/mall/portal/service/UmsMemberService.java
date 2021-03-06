package com.macro.mall.portal.service;

import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.model.UserMemberModel;
import com.macro.mall.weixin.bean.SNSUserInfo;
import org.springframework.transaction.annotation.Transactional;

public interface UmsMemberService {
    /**
     * 根据用户名获取会员
     */
    UmsMember getByUsername(String username);

    /**
     * 根据会员编号获取会员
     */
    UmsMember getById(Long id);

    /**
     * 用户注册
     */
    @Transactional
    CommonResult register(String username, String password, String telephone, String authCode, String mail);

    /**
     * 生成验证码
     */
    CommonResult generateAuthCode(String countryCode, String telephone);

    /**
     * 修改密码
     */
    @Transactional
    CommonResult updatePassword(String telephone, String password, String authCode);

    /**
     * 修改用户信息
     */
    @Transactional
    void updateUserInfo(UserMemberModel userMemberModel);

    @Transactional
    CommonResult wxAuthInit(SNSUserInfo snsUserInfo);

    /**
     * 获取当前登录会员
     */
    UmsMember getCurrentMember();

    /**
     * 根据会员id修改会员积分
     */
    void updateIntegration(Long id,Integer integration);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username,String password);
}
