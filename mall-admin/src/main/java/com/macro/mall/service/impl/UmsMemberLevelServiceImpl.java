package com.macro.mall.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.macro.mall.mapper.UmsMemberLevelMapper;
import com.macro.mall.model.UmsMemberLevel;
import com.macro.mall.model.UmsMemberLevelExample;
import com.macro.mall.service.UmsMemberLevelService;

/**
 * 会员等级管理Service实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class UmsMemberLevelServiceImpl implements UmsMemberLevelService{
	
	//日志
	private static final Logger log = LoggerFactory.getLogger(UmsMemberLevelServiceImpl.class);
	
    @Autowired
    private UmsMemberLevelMapper memberLevelMapper;
    @Override
    public List<UmsMemberLevel> list() {
        UmsMemberLevelExample example = new UmsMemberLevelExample();
        return memberLevelMapper.selectByExample(example);
    }
	@Override
	public int create(UmsMemberLevel memberLevel) {
		/**
		 * 1、根据选择的默认等级方式判断是否已经存在默认等级
		 */
		if(memberLevel.getDefaultStatus() == 1) {
			UmsMemberLevelExample example = new UmsMemberLevelExample();
			example.createCriteria().andDefaultStatusEqualTo(memberLevel.getDefaultStatus());
			List<UmsMemberLevel> levels = memberLevelMapper.selectByExample(example);
			if(levels != null && levels.size() > 0) {
				log.info("已经存在默认等级，不能继续添加默认等级");
				return -1;
			}
		}
		/**
		 * 2、插入数据
		 */
		return memberLevelMapper.insert(memberLevel);
	}
	@Override
	public int update(Long id, UmsMemberLevel memberLevel) {
		/**
		 * 1、根据选择的默认等级方式判断是否已经存在默认等级
		 */
		if(memberLevel.getDefaultStatus() == 1) {
			UmsMemberLevelExample example = new UmsMemberLevelExample();
			example.createCriteria().andDefaultStatusEqualTo(memberLevel.getDefaultStatus());
			List<UmsMemberLevel> levels = memberLevelMapper.selectByExample(example);
			if(levels != null && levels.size() > 0) {
				log.info("已经存在默认等级，不能继续添加默认等级");
				return -1;
			}
		}
		/**
		 * 2、更新数据
		 */
		memberLevel.setId(id);
		return memberLevelMapper.updateByPrimaryKey(memberLevel);
	}
	@Override
	public int delete(List<Long> ids) {
		UmsMemberLevelExample example = new UmsMemberLevelExample();
		example.createCriteria().andIdIn(ids);
		return memberLevelMapper.deleteByExample(example);
	}
	@Override
	public UmsMemberLevel view(Long id) {
		return memberLevelMapper.selectByPrimaryKey(id);
	}
	@Override
	public List<UmsMemberLevel> pageList(String name, Integer status, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		UmsMemberLevelExample example = new UmsMemberLevelExample();
		UmsMemberLevelExample.Criteria criteria = example.createCriteria();
		//等级名称
		if(!StringUtils.isEmpty(name)) {
			criteria.andNameLike("%" + name +"%");
		}
		// 默认状态
		if(status != null) {
			criteria.andDefaultStatusEqualTo(status);
		}
		return memberLevelMapper.selectByExample(example);
	}
}
