package com.macro.mall.service;

import java.util.List;

import com.macro.mall.model.TLogicsRule;

/**
 * 
 * @author Frank 2019/02/27 物流规则Service
 *
 */
public interface LogicsRuleService {

	/**
	 * 根据条件分页查询物流规则信息
	 * @param type		物流分类
	 * @param brand		品牌分类
	 * @param mixStatus 是否可混装
	 * @param pageNum	页码
	 * @param pageSize	每页个数
	 * @return
	 */
	List<TLogicsRule> pageList(String type, String brand, Short mixStatus, Integer pageNum, Integer pageSize);

	/**
	 * 删除物流规则
	 * @param ids		ids
	 * @return
	 */
	int delete(List<Integer> ids);

	/**
	 * 增加物流规则信息
	 * @param logicsRule 物流规则信息
	 * @return
	 */
	int create(TLogicsRule logicsRule);

	/**
	 * 更新物流规则信息 
	 * @param id		 id
	 * @param logicsRule 物流规则信息
	 * @return
	 */
	int update(Integer id, TLogicsRule logicsRule);

	/**
	 * 查看物流规则信息
	 * @param id		 id
	 * @return
	 */
	TLogicsRule query(Integer id);

}
