package com.macro.mall.service;

import java.util.List;

import com.macro.mall.model.UmsCode;
import com.macro.mall.model.UmsType;

/**
 * 代码配置service
 * @author Frank 2019/02/27
 *
 */
public interface UmsCodeService {

	/**
	 * 分页查询代码配置类型信息
	 * @param typeCode		类型代码
	 * @param typeCName		类型中文名称
	 * @param validStatus	有效状态
	 * @param pageNum		页码
	 * @param pageSize		每页个数
	 * @return
	 */
	List<UmsType> typePageList(String typeCode, String typeCName, Integer validStatus, Integer pageNum,
                               Integer pageSize);

	/**
	 * 创建代码配置类型信息
	 * @param umsType
	 * @return
	 */
	int createType(UmsType umsType);

	/**
	 * 删除代码配置类型信息
	 * @param ids
	 * @return
	 */
	int deleteType(List<Long> ids);

	/**
	 * 更新代码配置类型信息
	 * @param id
	 * @param umsType
	 * @return
	 */
	int updateType(Long id, UmsType umsType);

	/**
	 * 根据类型代码查询代码信息
	 * @param typeCode
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	List<UmsCode> codePageList(String typeCode, Integer pageSize, Integer pageNum);

	/**
	 * 增加代码信息
	 * @param umsCode
	 * @return
	 */
	int createCode(UmsCode umsCode);

	/**
	 * 删除代码信息
	 * @param ids
	 * @return
	 */
	int deleteCode(List<Long> ids);

	/**
	 * 更新代码信息
	 * @param id
	 * @param umsCode
	 * @return
	 */
	int updateCode(Long id, UmsCode umsCode);

	/**
	 * 查看代码信息
	 * @param id
	 * @return
	 */
	UmsCode viewCode(Long id);

}
