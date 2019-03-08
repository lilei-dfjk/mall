package com.macro.mall.mapper;

import com.macro.mall.model.OmsLogisticOrder;
import com.macro.mall.model.OmsLogisticOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OmsLogisticOrderMapper {
    int countByExample(OmsLogisticOrderExample example);

    int deleteByExample(OmsLogisticOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OmsLogisticOrder record);

    int insertSelective(OmsLogisticOrder record);

    List<OmsLogisticOrder> selectByExample(OmsLogisticOrderExample example);

    OmsLogisticOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OmsLogisticOrder record, @Param("example") OmsLogisticOrderExample example);

    int updateByExample(@Param("record") OmsLogisticOrder record, @Param("example") OmsLogisticOrderExample example);

    int updateByPrimaryKeySelective(OmsLogisticOrder record);

    int updateByPrimaryKey(OmsLogisticOrder record);
}