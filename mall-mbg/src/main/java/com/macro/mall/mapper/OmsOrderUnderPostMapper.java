package com.macro.mall.mapper;

import com.macro.mall.model.OmsOrderUnderPost;
import com.macro.mall.model.OmsOrderUnderPostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OmsOrderUnderPostMapper {
    int countByExample(OmsOrderUnderPostExample example);

    int deleteByExample(OmsOrderUnderPostExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OmsOrderUnderPost record);

    int insertSelective(OmsOrderUnderPost record);

    List<OmsOrderUnderPost> selectByExample(OmsOrderUnderPostExample example);

    OmsOrderUnderPost selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OmsOrderUnderPost record, @Param("example") OmsOrderUnderPostExample example);

    int updateByExample(@Param("record") OmsOrderUnderPost record, @Param("example") OmsOrderUnderPostExample example);

    int updateByPrimaryKeySelective(OmsOrderUnderPost record);

    int updateByPrimaryKey(OmsOrderUnderPost record);
}