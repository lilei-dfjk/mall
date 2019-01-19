package com.macro.mall.mapper;

import com.macro.mall.model.TLogicsRule;
import com.macro.mall.model.TLogicsRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TLogicsRuleMapper {
    int countByExample(TLogicsRuleExample example);

    int deleteByExample(TLogicsRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TLogicsRule record);

    int insertSelective(TLogicsRule record);

    List<TLogicsRule> selectByExample(TLogicsRuleExample example);

    TLogicsRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TLogicsRule record, @Param("example") TLogicsRuleExample example);

    int updateByExample(@Param("record") TLogicsRule record, @Param("example") TLogicsRuleExample example);

    int updateByPrimaryKeySelective(TLogicsRule record);

    int updateByPrimaryKey(TLogicsRule record);
}