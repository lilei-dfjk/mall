package com.macro.mall.search.service.impl;

import com.macro.mall.logistcs.bean.LogisticsRuleBean;
import com.macro.mall.logistcs.cons.LogisticType;
import com.macro.mall.mapper.PmsMemberPriceMapper;
import com.macro.mall.model.*;
import com.macro.mall.pay.rate.RateService;
import com.macro.mall.portal.service.MemberCollectionService;
import com.macro.mall.portal.service.PmsProductLogisticRuleService;
import com.macro.mall.portal.service.PortalProductService;
import com.macro.mall.portal.service.UmsMemberService;
import com.macro.mall.portal.util.JwtTokenUtil;
import com.macro.mall.search.dao.EsBrandDao;
import com.macro.mall.search.dao.EsProductDao;
import com.macro.mall.search.dao.EsProductTypeDao;
import com.macro.mall.search.domain.EsBrand;
import com.macro.mall.search.domain.EsProduct;
import com.macro.mall.search.domain.EsProductRelatedInfo;
import com.macro.mall.search.domain.EsProductType;
import com.macro.mall.search.model.ProductDetailMode;
import com.macro.mall.search.repository.EsBrandRepository;
import com.macro.mall.search.repository.EsProductRepository;
import com.macro.mall.search.repository.EsProductTypeRepository;
import com.macro.mall.search.service.EsProductService;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.InternalFilter;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 商品搜索管理Service实现类
 * Created by macro on 2018/6/19.
 */
@Service
public class EsProductServiceImpl implements EsProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsProductServiceImpl.class);
    @Autowired
    private EsBrandDao brandDao;
    @Autowired
    private EsBrandRepository brandRepository;
    @Autowired
    private MemberCollectionService collectionService;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private PmsMemberPriceMapper memberPriceMapper;
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private PortalProductService portalProductService;
    @Autowired
    private EsProductDao productDao;
    @Autowired
    private PmsProductLogisticRuleService productLogisticRuleService;
    @Autowired
    private EsProductRepository productRepository;
    @Autowired
    private EsProductTypeDao productTypeDao;
    @Autowired
    private EsProductTypeRepository productTypeRepository;
    @Autowired
    private RateService rateService;
    @Autowired
    private JwtTokenUtil tokenUtil;

    @Override
    public ProductDetailMode findById(Long id, HttpServletRequest request) {
        PmsProduct productInfo = portalProductService.getProductInfo(id);
        LogisticsRuleBean logisticRuleBean = productLogisticRuleService.getLogisticRuleBean(id, LogisticType.ZH);
        ProductDetailMode mode = new ProductDetailMode();
        mode.setProductId(id);
        String albumPics = productInfo.getAlbumPics();
        if (!StringUtils.isEmpty(albumPics)) {
            String[] split = albumPics.split(",");
            if (ArrayUtils.isNotEmpty(split)) {
                mode.setAlbumPics(CollectionUtils.arrayToList(split));
            }
        }
        mode.setPic(productInfo.getPic());
        mode.setPrice(mode.getPrice());
        mode.setH5Remark(productInfo.getDetailMobileHtml());
        mode.setPcRemark(productInfo.getDetailHtml());
        mode.setName(productInfo.getName());
        mode.setWeight(productInfo.getWeight());
        mode.setPrice(productInfo.getPrice());
        mode.setStock(productInfo.getStock());
        mode.setMaxBuy(logisticRuleBean.getNumberLimit());
        if (tokenUtil.isLogin(request)) {
            UmsMember currentMember = memberService.getCurrentMember();
            if (null != currentMember) {
                Long memberLevelId = currentMember.getMemberLevelId();
                PmsMemberPriceExample example = new PmsMemberPriceExample();
                example.createCriteria().andMemberLevelIdEqualTo(memberLevelId)
                        .andProductIdEqualTo(id);
                List<PmsMemberPrice> pmsMemberPrices = memberPriceMapper.selectByExample(example);
                if (!CollectionUtils.isEmpty(pmsMemberPrices)) {
                    mode.setPrice(pmsMemberPrices.get(0).getMemberPrice());
                }
                UmsProductCollection collection = collectionService.findByProductId(id, currentMember.getId());
                mode.setCollect(null == collection);
            }
        }

        mode.setCnyPrice(mode.getPrice().multiply(BigDecimal.valueOf(rateService.getAuToCnyRate())));
        return mode;
    }

    @Override
    public int importAll() {
        List<EsProduct> esProductList = productDao.getAllEsProductList(null);
        esProductList.stream().forEach(esProduct -> {
            esProduct.setCnyPrice(esProduct.getPrice().multiply(BigDecimal.valueOf(rateService.getAuToCnyRate())));
        });
        productRepository.deleteAll();
        Iterable<EsProduct> esProductIterable = productRepository.saveAll(esProductList);
        importAllBrands();
        importAllProductTypes();
        Iterator<EsProduct> iterator = esProductIterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public int importAllBrands() {
        List<EsBrand> allEsBrandList = brandDao.getAllEsBrandList(null);
        brandRepository.deleteAll();
        brandRepository.saveAll(allEsBrandList);
        return 1;
    }

    @Override
    public int importAllProductTypes() {
        List<EsProductType> all = productTypeDao.getAllEsProductTypeList(null);
        productTypeRepository.deleteAll();
        productTypeRepository.saveAll(all);
        return 1;
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public EsProduct create(Long id) {
        EsProduct result = null;
        List<EsProduct> esProductList = productDao.getAllEsProductList(id);
        if (esProductList.size() > 0) {
            EsProduct esProduct = esProductList.get(0);
            result = productRepository.save(esProduct);
        }
        return result;
    }

    @Override
    public void delete(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            List<EsProduct> esProductList = new ArrayList<>();
            for (Long id : ids) {
                EsProduct esProduct = new EsProduct();
                esProduct.setId(id);
                esProductList.add(esProduct);
            }
            productRepository.deleteAll(esProductList);
        }
    }

    @Override
    public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNum, pageSize);
        return productRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageable);
    }

    @Override
    public Page<EsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort) {
        Pageable pageable = new PageRequest(pageNum, pageSize);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //分页
        nativeSearchQueryBuilder.withPageable(pageable);
        //过滤
        if (brandId != null || productCategoryId != null) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if (!StringUtils.isEmpty(brandId)) {
                boolQueryBuilder.must(QueryBuilders.termQuery("brandId", brandId));
            }
            if (!StringUtils.isEmpty(productCategoryId)) {
                boolQueryBuilder.must(QueryBuilders.termQuery("productCategoryId", productCategoryId));
            }
            nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
        }
        //搜索
        FieldValueFactorFunctionBuilder name = ScoreFunctionBuilders.fieldValueFactorFunction("name").setWeight(10);
        FieldValueFactorFunctionBuilder subTitle = ScoreFunctionBuilders.fieldValueFactorFunction("subTitle").setWeight(5);
        FieldValueFactorFunctionBuilder keywords = ScoreFunctionBuilders.fieldValueFactorFunction("keywords").setWeight(2);
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{new FunctionScoreQueryBuilder.FilterFunctionBuilder(name),
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(subTitle), new FunctionScoreQueryBuilder.FilterFunctionBuilder(keywords)};
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                .setMinScore(2);
        if (StringUtils.isEmpty(keyword)) {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
        } else {
            nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
        }
        //排序
        if (sort == 1) {
            //按新品从新到旧
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC));
        } else if (sort == 2) {
            //按销量从高到低
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("sale").order(SortOrder.DESC));
        } else if (sort == 3) {
            //按价格从低到高
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
        } else if (sort == 4) {
            //按价格从高到低
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
        } else {
            //按相关度
            nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        }
        nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        LOGGER.info("DSL:{}", searchQuery.getQuery().toString());
        return productRepository.search(searchQuery);
    }

    @Override
    public Page<EsProduct> recommend(Long id, Integer pageNum, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNum, pageSize);
        List<EsProduct> esProductList = productDao.getAllEsProductList(id);
        if (esProductList.size() > 0) {
            EsProduct esProduct = esProductList.get(0);
            String keyword = esProduct.getName();
            Long brandId = esProduct.getBrandId();
            Long productCategoryId = esProduct.getProductCategoryId();
            //根据商品标题、品牌、分类进行搜索
            FieldValueFactorFunctionBuilder name = ScoreFunctionBuilders.fieldValueFactorFunction("name").setWeight(8);
            FieldValueFactorFunctionBuilder subTitle = ScoreFunctionBuilders.fieldValueFactorFunction("subTitle").setWeight(2);
            FieldValueFactorFunctionBuilder keywords = ScoreFunctionBuilders.fieldValueFactorFunction("keywords").setWeight(2);
            FieldValueFactorFunctionBuilder brandIds = ScoreFunctionBuilders.fieldValueFactorFunction("brandId").setWeight(10);
            FieldValueFactorFunctionBuilder productCategoryIds = ScoreFunctionBuilders.fieldValueFactorFunction("productCategoryId").setWeight(6);
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{new FunctionScoreQueryBuilder.FilterFunctionBuilder(name),
                    new FunctionScoreQueryBuilder.FilterFunctionBuilder(subTitle), new FunctionScoreQueryBuilder.FilterFunctionBuilder(keywords),
                    new FunctionScoreQueryBuilder.FilterFunctionBuilder(brandIds), new FunctionScoreQueryBuilder.FilterFunctionBuilder(productCategoryIds)};
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                    .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                    .setMinScore(2);
            NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
            builder.withQuery(functionScoreQueryBuilder);
            builder.withPageable(pageable);
            NativeSearchQuery searchQuery = builder.build();
            LOGGER.info("DSL:{}", searchQuery.getQuery().toString());
            return productRepository.search(searchQuery);
        }
        return new PageImpl<>(null);
    }

    @Override
    public EsProductRelatedInfo searchRelatedInfo(String keyword) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //搜索条件
        if (StringUtils.isEmpty(keyword)) {
            builder.withQuery(QueryBuilders.matchAllQuery());
        } else {
            builder.withQuery(QueryBuilders.multiMatchQuery(keyword, "name", "subTitle", "keywords"));
        }
        //聚合搜索品牌名称
        builder.addAggregation(AggregationBuilders.terms("brandNames").field("brandName"));
        //集合搜索分类名称
        builder.addAggregation(AggregationBuilders.terms("productCategoryNames").field("productCategoryName"));
        //聚合搜索商品属性，去除type=1的属性
        AbstractAggregationBuilder aggregationBuilder = AggregationBuilders.nested("allAttrValues", "attrValueList")
                .subAggregation(AggregationBuilders.filter("productAttrs", QueryBuilders.termQuery("attrValueList.type", 1))
                        .subAggregation(AggregationBuilders.terms("attrIds")
                                .field("attrValueList.productAttributeId")
                                .subAggregation(AggregationBuilders.terms("attrValues")
                                        .field("attrValueList.value"))
                                .subAggregation(AggregationBuilders.terms("attrNames")
                                        .field("attrValueList.name"))));
        builder.addAggregation(aggregationBuilder);
        NativeSearchQuery searchQuery = builder.build();
        return elasticsearchTemplate.query(searchQuery, response -> {
            LOGGER.info("DSL:{}", searchQuery.getQuery().toString());
            return convertProductRelatedInfo(response);
        });
    }

    @Override
    public List<EsBrand> getBrands() {
        Sort sort = new Sort(Sort.Direction.ASC, "sort");
        Iterable<EsBrand> all = brandRepository.findAll(sort);
        List<EsBrand> esBrands = IterableUtils.toList(all);
        return esBrands;
    }

    @Override
    public List<EsProductType> getProductTypes() {
        Sort sort = new Sort(Sort.Direction.ASC, "sort");
        Iterable<EsProductType> all = productTypeRepository.findAll(sort);
        return IterableUtils.toList(all);
    }

    /**
     * 将返回结果转换为对象
     */
    private EsProductRelatedInfo convertProductRelatedInfo(SearchResponse response) {
        EsProductRelatedInfo productRelatedInfo = new EsProductRelatedInfo();
        Map<String, Aggregation> aggregationMap = response.getAggregations().getAsMap();
        //设置品牌
        Aggregation brandNames = aggregationMap.get("brandNames");
        List<String> brandNameList = new ArrayList<>();
        for (int i = 0; i < ((StringTerms) brandNames).getBuckets().size(); i++) {
            brandNameList.add(((StringTerms) brandNames).getBuckets().get(i).getKeyAsString());
        }
        productRelatedInfo.setBrandNames(brandNameList);
        //设置分类
        Aggregation productCategoryNames = aggregationMap.get("productCategoryNames");
        List<String> productCategoryNameList = new ArrayList<>();
        for (int i = 0; i < ((StringTerms) productCategoryNames).getBuckets().size(); i++) {
            productCategoryNameList.add(((StringTerms) productCategoryNames).getBuckets().get(i).getKeyAsString());
        }
        productRelatedInfo.setProductCategoryNames(productCategoryNameList);
        //设置参数
        Aggregation productAttrs = aggregationMap.get("allAttrValues");
        LongTerms longTerms = (LongTerms) (((InternalFilter) productAttrs.getMetaData().get("productAttrs")).getAggregations().get("attrIds"));
        List<LongTerms.Bucket> attrIds = longTerms.getBuckets();
        List<EsProductRelatedInfo.ProductAttr> attrList = new ArrayList<>();
        for (Terms.Bucket attrId : attrIds) {
            EsProductRelatedInfo.ProductAttr attr = new EsProductRelatedInfo.ProductAttr();
            attr.setAttrId((Long) attrId.getKey());
            List<String> attrValueList = new ArrayList<>();
            List<StringTerms.Bucket> attrValues = ((StringTerms) attrId.getAggregations().get("attrValues")).getBuckets();
            List<StringTerms.Bucket> attrNames = ((StringTerms) attrId.getAggregations().get("attrNames")).getBuckets();
            for (Terms.Bucket attrValue : attrValues) {
                attrValueList.add(attrValue.getKeyAsString());
            }
            attr.setAttrValues(attrValueList);
            if (!CollectionUtils.isEmpty(attrNames)) {
                String attrName = attrNames.get(0).getKeyAsString();
                attr.setAttrName(attrName);
            }
            attrList.add(attr);
        }
        productRelatedInfo.setProductAttrs(attrList);
        return productRelatedInfo;
    }
}
