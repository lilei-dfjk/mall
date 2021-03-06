package com.macro.mall.search.dao;

import com.macro.mall.search.domain.EsBrand;
import com.macro.mall.search.domain.EsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 搜索系统中的商品管理自定义Dao
 * Created by macro on 2018/6/19.
 */
public interface EsBrandDao {
    List<EsBrand> getAllEsBrandList(@Param("id") Long id);
}
