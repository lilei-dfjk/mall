package com.macro.mall.mapper;

import com.macro.mall.model.OmsLogicOrderItem;
import com.macro.mall.model.OmsLogicOrderItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OmsLogicOrderItemMapper {
    int countByExample(OmsLogicOrderItemExample example);

    int deleteByExample(OmsLogicOrderItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OmsLogicOrderItem record);

    int insertSelective(OmsLogicOrderItem record);

    List<OmsLogicOrderItem> selectByExample(OmsLogicOrderItemExample example);

    OmsLogicOrderItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OmsLogicOrderItem record, @Param("example") OmsLogicOrderItemExample example);

    int updateByExample(@Param("record") OmsLogicOrderItem record, @Param("example") OmsLogicOrderItemExample example);

    int updateByPrimaryKeySelective(OmsLogicOrderItem record);

    int updateByPrimaryKey(OmsLogicOrderItem record);
}