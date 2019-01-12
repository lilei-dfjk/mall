package com.macro.mall.mapper;

import com.macro.mall.model.TDAreainfo;
import com.macro.mall.model.TDAreainfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TDAreainfoMapper {
    int countByExample(TDAreainfoExample example);

    int deleteByExample(TDAreainfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TDAreainfo record);

    int insertSelective(TDAreainfo record);

    List<TDAreainfo> selectByExample(TDAreainfoExample example);

    TDAreainfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TDAreainfo record, @Param("example") TDAreainfoExample example);

    int updateByExample(@Param("record") TDAreainfo record, @Param("example") TDAreainfoExample example);

    int updateByPrimaryKeySelective(TDAreainfo record);

    int updateByPrimaryKey(TDAreainfo record);
}