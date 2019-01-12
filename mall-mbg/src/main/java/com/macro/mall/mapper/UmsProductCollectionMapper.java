package com.macro.mall.mapper;

import com.macro.mall.model.UmsProductCollection;
import com.macro.mall.model.UmsProductCollectionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsProductCollectionMapper {
    int countByExample(UmsProductCollectionExample example);

    int deleteByExample(UmsProductCollectionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsProductCollection record);

    int insertSelective(UmsProductCollection record);

    List<UmsProductCollection> selectByExample(UmsProductCollectionExample example);

    UmsProductCollection selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsProductCollection record, @Param("example") UmsProductCollectionExample example);

    int updateByExample(@Param("record") UmsProductCollection record, @Param("example") UmsProductCollectionExample example);

    int updateByPrimaryKeySelective(UmsProductCollection record);

    int updateByPrimaryKey(UmsProductCollection record);
}