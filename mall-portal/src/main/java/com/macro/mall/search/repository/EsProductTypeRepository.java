package com.macro.mall.search.repository;

import com.macro.mall.search.domain.EsProductType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 商品ES操作类
 * Created by macro on 2018/6/19.
 */
public interface EsProductTypeRepository extends ElasticsearchRepository<EsProductType, Long> {

}
