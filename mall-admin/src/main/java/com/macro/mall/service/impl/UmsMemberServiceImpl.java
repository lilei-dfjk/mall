package com.macro.mall.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.macro.mall.mapper.UmsMemberLevelMapper;
import com.macro.mall.mapper.UmsMemberMapper;
import com.macro.mall.model.UmsMember;
import com.macro.mall.model.UmsMemberExample;
import com.macro.mall.model.UmsMemberLevel;
import com.macro.mall.model.UmsMemberLevelExample;
import com.macro.mall.service.UmsMemberService;

/**
 * 会员管理Service实现类
 * @author Frank 2019/02/17
 *
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService{
	
	@Autowired
	private UmsMemberMapper memberMapper;
	@Autowired
	private UmsMemberLevelMapper memberLevelMapper;
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public int create(UmsMember umsMember) {
		//注册时间
		umsMember.setCreateTime(new Date());
		String md5Password = passwordEncoder.encodePassword(umsMember.getPassword(), null);
		//密码
		umsMember.setPassword(md5Password);
		//查询默认等级信息
		UmsMemberLevelExample example = new UmsMemberLevelExample();
		example.createCriteria().andDefaultStatusEqualTo(1);
		List<UmsMemberLevel> levels = memberLevelMapper.selectByExample(example);
		if(levels != null && levels.size() > 0) {
			umsMember.setMemberLevelId(levels.get(0).getId());
		}
		return memberMapper.insert(umsMember);
	}

	@Override
	public int delete(List<Long> ids) {
		UmsMemberExample example = new UmsMemberExample();
		example.createCriteria().andIdIn(ids);
		return memberMapper.deleteByExample(example);
	}

	@Override
	public int update(Long id, UmsMember member) {
		member.setId(id);
		return memberMapper.updateByPrimaryKey(member);
	}

	@Override
	public UmsMember query(Long id) {
		return memberMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<UmsMember> list(UmsMember umsMember) {
		UmsMemberExample example = new UmsMemberExample();
		//添加条件
		UmsMemberExample.Criteria criteria = example.createCriteria();
		//姓名
		if (!StringUtils.isEmpty(umsMember.getUsername())) {
			criteria.andUsernameEqualTo(umsMember.getUsername());
		}
		//昵称
		if (!StringUtils.isEmpty(umsMember.getNickname())) {
			criteria.andNicknameEqualTo(umsMember.getNickname());
		}
		//电话
		if (!StringUtils.isEmpty(umsMember.getPhone())) {
			criteria.andPhoneEqualTo(umsMember.getPhone());
		}
		return memberMapper.selectByExample(example);
	}

	@Override
	public List<UmsMember> pageList(String userName, String phone, Integer status, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		UmsMemberExample example = new UmsMemberExample();
		UmsMemberExample.Criteria criteria = example.createCriteria();
		//会员名称
		if (!StringUtils.isEmpty(userName)) {
			criteria.andUsernameLike("%" + userName + "%");
		}
		//会员手机号码
		if (!StringUtils.isEmpty(phone)) {
			criteria.andPhoneLike("%" + phone + "%");
		}
		//启用状态
		if(status != null) {
			criteria.andStatusEqualTo(status);
		}
		return memberMapper.selectByExample(example);
	}

}
