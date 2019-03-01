package com.macro.mall.mapper;

import com.macro.mall.model.SysAreas;
import com.macro.mall.model.SysAreasExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysAreasMapper {
    int countByExample(SysAreasExample example);

    int deleteByExample(SysAreasExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysAreas record);

    int insertSelective(SysAreas record);

    List<SysAreas> selectByExample(SysAreasExample example);

    SysAreas selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysAreas record, @Param("example") SysAreasExample example);

    int updateByExample(@Param("record") SysAreas record, @Param("example") SysAreasExample example);

    int updateByPrimaryKeySelective(SysAreas record);

    int updateByPrimaryKey(SysAreas record);
}