package com.macro.mall.mapper;

import com.macro.mall.model.UmsType;
import com.macro.mall.model.UmsTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsTypeMapper {
    int countByExample(UmsTypeExample example);

    int deleteByExample(UmsTypeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsType record);

    int insertSelective(UmsType record);

    List<UmsType> selectByExample(UmsTypeExample example);

    UmsType selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsType record, @Param("example") UmsTypeExample example);

    int updateByExample(@Param("record") UmsType record, @Param("example") UmsTypeExample example);

    int updateByPrimaryKeySelective(UmsType record);

    int updateByPrimaryKey(UmsType record);
}