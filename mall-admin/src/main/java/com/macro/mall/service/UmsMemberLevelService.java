package com.macro.mall.service;

import com.macro.mall.model.UmsMemberLevel;

import java.util.List;

/**
 * 会员等级管理Service
 * Created by macro on 2018/4/26.
 */
public interface UmsMemberLevelService {
	
    /**
     * 获取所有会员登录
     */
    List<UmsMemberLevel> list();

    /**
     * 增加会员等级信息
     * @param memberLevel	会员等级内容
     * @return
     */
	int create(UmsMemberLevel memberLevel);

	/**
	 * 更新会员等级信息
	 * @param id			会员等级ID
	 * @param memberLevel	会员等级内容
	 * @return
	 */
	int update(Long id, UmsMemberLevel memberLevel);

	/**
	 * 删除会员等级信息
	 * @param ids			会员等级ID
	 * @return
	 */
	int delete(List<Long> ids);

	/**
	 * 查看会员等级信息
	 * @param id			会员等级ID
	 * @return
	 */
	UmsMemberLevel view(Long id);

	/**
	 *  根据会员等级名称、状态查询分页查询会员等级信息
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<UmsMemberLevel> pageList(String name, Integer status, Integer pageNum, Integer pageSize);
}
