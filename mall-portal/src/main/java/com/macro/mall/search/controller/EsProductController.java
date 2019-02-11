package com.macro.mall.search.controller;

import com.macro.mall.mapper.PmsMemberPriceMapper;
import com.macro.mall.model.PmsMemberPrice;
import com.macro.mall.model.PmsMemberPriceExample;
import com.macro.mall.model.UmsMember;
import com.macro.mall.pay.rate.RateService;
import com.macro.mall.portal.service.UmsMemberService;
import com.macro.mall.portal.util.JwtTokenUtil;
import com.macro.mall.search.domain.*;
import com.macro.mall.search.service.EsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 搜索商品管理Controller
 * Created by macro on 2018/6/19.
 */
@Controller
@Api(tags = "EsProductController", description = "搜索商品管理")
@RequestMapping("/product")
public class EsProductController {
    @Autowired
    private EsProductService esProductService;
    @Autowired
    private JwtTokenUtil tokenUtil;
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private PmsMemberPriceMapper memberPriceMapper;
    @Autowired
    private RateService rateService;

    @ApiOperation(value = "导入所有数据库中商品到ES")
    @RequestMapping(value = "/importAll", method = RequestMethod.POST)
    @ResponseBody
    public Object importAllList() {
        int count = esProductService.importAll();
        return new CommonResult().success(count);
    }

    @ApiOperation(value = "根据id获取商品详情")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object info(@PathVariable Long id, HttpServletRequest request) {
        return new CommonResult().success(esProductService.findById(id, request));
    }

    @ApiOperation(value = "根据id删除商品")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable Long id) {
        esProductService.delete(id);
        return new CommonResult().success(null);
    }

    @ApiOperation(value = "根据id批量删除商品")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@RequestParam("ids") List<Long> ids) {
        esProductService.delete(ids);
        return new CommonResult().success(null);
    }

    @ApiOperation(value = "根据id创建商品")
    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object create(@PathVariable Long id) {
        EsProduct esProduct = esProductService.create(id);
        if (esProduct != null) {
            return new CommonResult().success(esProduct);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation(value = "简单搜索")
    @RequestMapping(value = "/search/simple", method = RequestMethod.GET)
    @ResponseBody
    public Object search(@RequestParam(required = false) String keyword,
                         @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                         @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                         HttpServletRequest request) {
        Page<EsProduct> esProductPage = esProductService.search(keyword, pageNum, pageSize);
        initMemberPrice(request, esProductPage);
        return new CommonResult().pageSuccess(esProductPage);
    }

    @ApiOperation(value = "综合搜索、筛选、排序")
    @ApiImplicitParam(name = "sort", value = "排序字段:0->按相关度；1->按新品；2->按销量；3->价格从低到高；4->价格从高到低",
            defaultValue = "0", allowableValues = "0,1,2,3,4", paramType = "query", dataType = "integer")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public Object search(@RequestParam(required = false) String keyword,
                         @RequestParam(required = false) Long brandId,
                         @RequestParam(required = false) Long productCategoryId,
                         @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                         @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                         @RequestParam(required = false, defaultValue = "0") Integer sort,
                         HttpServletRequest request) {
        Page<EsProduct> esProductPage = esProductService.search(keyword, brandId, productCategoryId, pageNum, pageSize, sort);
        initMemberPrice(request, esProductPage);
        return new CommonResult().pageSuccess(esProductPage);
    }

    @ApiOperation(value = "根据商品id推荐商品")
    @RequestMapping(value = "/recommend/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object recommend(@PathVariable Long id,
                            @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                            @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsProduct> esProductPage = esProductService.recommend(id, pageNum, pageSize);
        return new CommonResult().pageSuccess(esProductPage);
    }

    @ApiOperation(value = "获取搜索的相关品牌、分类及筛选属性")
    @RequestMapping(value = "/search/relate", method = RequestMethod.GET)
    @ResponseBody
    public Object searchRelatedInfo(@RequestParam(required = false) String keyword) {
        EsProductRelatedInfo productRelatedInfo = esProductService.searchRelatedInfo(keyword);
        return new CommonResult().success(productRelatedInfo);
    }

    @ApiOperation(value = "获取品牌")
    @RequestMapping(value = "/brands", method = RequestMethod.GET)
    @ResponseBody
    public Object brands() {
        List<EsBrand> brands = esProductService.getBrands();
        return new CommonResult().success(brands);
    }

    @ApiOperation(value = "获取产品类型")
    @RequestMapping(value = "/types", method = RequestMethod.GET)
    @ResponseBody
    public Object types() {
        List<EsProductType> types = esProductService.getProductTypes();
        return new CommonResult().success(types);
    }

    private void initMemberPrice(HttpServletRequest request, Page<EsProduct> pages) {
        if (tokenUtil.isLogin(request)) {
            pages.stream().forEach(esProduct -> {
                Long id = esProduct.getId();
                UmsMember currentMember = memberService.getCurrentMember();
                if (null != currentMember) {
                    Long memberLevelId = currentMember.getMemberLevelId();
                    PmsMemberPriceExample example = new PmsMemberPriceExample();
                    example.createCriteria().andMemberLevelIdEqualTo(memberLevelId)
                            .andProductIdEqualTo(id);
                    List<PmsMemberPrice> pmsMemberPrices = memberPriceMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(pmsMemberPrices)) {
                        esProduct.setPrice(pmsMemberPrices.get(0).getMemberPrice());
                        esProduct.setCnyPrice(esProduct.getPrice().multiply(BigDecimal.valueOf(rateService.getAuToCnyRate())));
                    }
                }
            });
        }
    }

}
