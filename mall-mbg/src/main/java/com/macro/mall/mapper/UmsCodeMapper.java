package com.macro.mall.mapper;

import com.macro.mall.model.UmsCode;
import com.macro.mall.model.UmsCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsCodeMapper {
    int countByExample(UmsCodeExample example);

    int deleteByExample(UmsCodeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsCode record);

    int insertSelective(UmsCode record);

    List<UmsCode> selectByExample(UmsCodeExample example);

    UmsCode selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsCode record, @Param("example") UmsCodeExample example);

    int updateByExample(@Param("record") UmsCode record, @Param("example") UmsCodeExample example);

    int updateByPrimaryKeySelective(UmsCode record);

    int updateByPrimaryKey(UmsCode record);
}