package com.macro.mall.service;

import java.util.List;

import com.macro.mall.model.UmsMember;

/**
 * 会员Service
 * @author Frank 2019/02/17
 *
 */
public interface UmsMemberService {

	/**
	 * 增加会员信息
	 * @param umsMember 会员内容
	 * @return
	 */
	int create(UmsMember umsMember);

	/**
	 * 删除会员信息
	 * @param ids 		会员ID
	 * @return
	 */
	int delete(List<Long> ids);

	/**
	 * 更新会员信息
	 * @param id		会员ID
	 * @param member	会员内容
	 * @return
	 */
	int update(Long id, UmsMember member);

	/**
	 * 查看会员信息
	 * @param id		会员ID
	 * @return
	 */
	UmsMember query(Long id);

	/**
	 * 查询所有会员信息
	 * @param umsMember	会员内容
	 * @return
	 */
	List<UmsMember> list(UmsMember umsMember);

	/**
	 * 根据姓名和电话分页查询会员信息
	 * @param userName	会员名称
	 * @param phone		会员电话
	 * @param pageNum	页码
	 * @param pageSize	每页个数
	 * @return
	 */
	List<UmsMember> pageList(String userName, String phone, Integer status, Integer pageNum, Integer pageSize);

}
