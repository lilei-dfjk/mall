package com.macro.mall.dao;

import com.macro.mall.model.PmsMemberPrice;
import com.macro.mall.model.PmsProductLogisticRule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物流价格Dao
 * Created by macro on 2018/4/26.
 */
public interface PmsProductLogisticRuleDao {
    int insertList(@Param("list") List<PmsProductLogisticRule> logisticRuleList);
}
