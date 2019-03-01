package com.macro.mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.macro.mall.mapper.TLogicsRuleMapper;
import com.macro.mall.model.TLogicsRule;
import com.macro.mall.model.TLogicsRuleExample;
import com.macro.mall.service.LogicsRuleService;

/**
 * 物流规则Service实现类
 * @author Frank 2019/02/27
 *
 */
@Service
public class LogicsRuleServiceImpl implements LogicsRuleService{
	

	@Autowired
	private TLogicsRuleMapper tLogicsRuleMapper;
	
	@Override
	public List<TLogicsRule> pageList(String type, String brand, Short mixStatus, Integer pageNum,
			Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		TLogicsRuleExample example = new TLogicsRuleExample();
		TLogicsRuleExample.Criteria criteria = example.createCriteria();
		//物流分类名称
		if (!StringUtils.isEmpty(type)) {
			criteria.andNameLike("%" + type +"%");
		}
		//品牌分类名称
		if(!StringUtils.isEmpty(brand)) {
			criteria.andBrandNameLike("%" + brand + "%");
		}
		//是否可以混装标志
		if(mixStatus != null) {
			criteria.andMixTypeFlagEqualTo(mixStatus);
		}
		return tLogicsRuleMapper.selectByExample(example);
	}

	@Override
	public int delete(List<Integer> ids) {
		TLogicsRuleExample example = new TLogicsRuleExample();
		example.createCriteria().andIdIn(ids);
		return tLogicsRuleMapper.deleteByExample(example);
	}

	@Override
	public int create(TLogicsRule logicsRule) {
		return tLogicsRuleMapper.insert(logicsRule);
	}

	@Override
	public int update(Integer id, TLogicsRule logicsRule) {
		logicsRule.setId(id);
		return tLogicsRuleMapper.updateByPrimaryKey(logicsRule);
	}

	@Override
	public TLogicsRule query(Integer id) {
		return tLogicsRuleMapper.selectByPrimaryKey(id);
	}

}
