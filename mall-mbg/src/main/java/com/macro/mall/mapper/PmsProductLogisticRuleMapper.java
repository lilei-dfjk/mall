package com.macro.mall.mapper;

import com.macro.mall.model.PmsProductLogisticRule;
import com.macro.mall.model.PmsProductLogisticRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsProductLogisticRuleMapper {
    int countByExample(PmsProductLogisticRuleExample example);

    int deleteByExample(PmsProductLogisticRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmsProductLogisticRule record);

    int insertSelective(PmsProductLogisticRule record);

    List<PmsProductLogisticRule> selectByExample(PmsProductLogisticRuleExample example);

    PmsProductLogisticRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmsProductLogisticRule record, @Param("example") PmsProductLogisticRuleExample example);

    int updateByExample(@Param("record") PmsProductLogisticRule record, @Param("example") PmsProductLogisticRuleExample example);

    int updateByPrimaryKeySelective(PmsProductLogisticRule record);

    int updateByPrimaryKey(PmsProductLogisticRule record);
}