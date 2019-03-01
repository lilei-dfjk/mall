package com.macro.mall.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.macro.mall.mapper.UmsCodeMapper;
import com.macro.mall.mapper.UmsTypeMapper;
import com.macro.mall.model.UmsCode;
import com.macro.mall.model.UmsCodeExample;
import com.macro.mall.model.UmsType;
import com.macro.mall.model.UmsTypeExample;
import com.macro.mall.service.UmsCodeService;

import lombok.extern.slf4j.Slf4j;

/**
 * 代码配置Service实现层
 * @author Frank 2019/02/27
 */
@Service
@Slf4j
public class UmsCodeServiceImpl implements UmsCodeService{

	@Autowired
	private UmsCodeMapper codeMapper;
	@Autowired
	private UmsTypeMapper typeMapper;
	@Override
	public List<UmsType> typePageList(String typeCode, String typeCName, Integer validStatus, Integer pageNum,
			Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		UmsTypeExample example = new UmsTypeExample();
		UmsTypeExample.Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(typeCode)) {
			criteria.andTypeCodeLike("%" +typeCode + "%");
		}
		if(!StringUtils.isEmpty(typeCName)) {
			criteria.andTypeCNameLike("%" + typeCName + "%");
		}
		if(validStatus != null) {
			criteria.andValidStatusEqualTo(validStatus);
		}
		return typeMapper.selectByExample(example);
	}
	@Override
	public int createType(UmsType umsType) {
		umsType.setCreateTime(new Date());
		umsType.setUpdateTime(new Date());
		return typeMapper.insert(umsType);
	}
	@Override
	public int deleteType(List<Long> ids) {
		UmsTypeExample example = new UmsTypeExample();
		example.createCriteria().andIdIn(ids);
		return typeMapper.deleteByExample(example);
	}
	@Override
	public int updateType(Long id, UmsType umsType) {
		umsType.setId(id);
		umsType.setUpdateTime(new Date());
		return typeMapper.updateByPrimaryKey(umsType);
	}
	@Override
	public List<UmsCode> codePageList(String typeCode, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		UmsCodeExample example = new UmsCodeExample();
		UmsCodeExample.Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(typeCode)) {
			criteria.andTypeCodeEqualTo(typeCode);
		}
		return codeMapper.selectByExample(example);
	}
	@Override
	public int createCode(UmsCode umsCode) {
		umsCode.setCreateTime(new Date());
		umsCode.setUpdateTime(new Date());
		UmsCodeExample example = new UmsCodeExample();
		example.createCriteria().andTypeCodeEqualTo(umsCode.getTypeCode()).
			andCodeCodeEqualTo(umsCode.getCodeCode());
		List<UmsCode> codes = codeMapper.selectByExample(example);
		if(codes != null && codes.size() > 0) {
			log.info("已经存在相同的代码信息！");
			return -1;
		}else {
			return codeMapper.insert(umsCode);
		}
	}
	@Override
	public int deleteCode(List<Long> ids) {
		UmsCodeExample example = new UmsCodeExample();
		example.createCriteria().andIdIn(ids);
		return codeMapper.deleteByExample(example);
	}
	@Override
	public int updateCode(Long id, UmsCode umsCode) {
		umsCode.setId(id);
		umsCode.setUpdateTime(new Date());
		return codeMapper.updateByPrimaryKey(umsCode);
	}
	@Override
	public UmsCode viewCode(Long id) {
		return codeMapper.selectByPrimaryKey(id);
	}
	
}
