package com.macro.mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.macro.mall.dto.CommonResult;
import com.macro.mall.model.TLogicsRule;
import com.macro.mall.service.LogicsRuleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 物流规则管理Controller
 * 
 * @author Frank 2019/02/27
 *
 */
@Controller
@Api(tags = "LogicsRuleController", description = "物流规则管理")
@RequestMapping("/logics")
public class LogicsRuleController {

	@Autowired
	private LogicsRuleService logicsRuleService;

	@ApiOperation(value = "根据条件分页查询物流规则信息")
    @RequestMapping(value = "/pageList", method = RequestMethod.GET)
    @ResponseBody
	public Object pageList(@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "brand", required = false) String brand,
			@RequestParam(value = "mixStatus", required = false) Short mixStatus,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
		List<TLogicsRule> logicsRules = logicsRuleService.pageList(type, brand, mixStatus, pageNum, pageSize);
		return new CommonResult().pageSuccess(logicsRules);
	}
	@ApiOperation("删除物流规则")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@RequestParam("ids") List<Integer> ids) {
        int count = logicsRuleService.delete(ids);
        if(count>0){
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }
	@ApiOperation("增加物流规则")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
	public Object create(@RequestBody TLogicsRule logicsRule) {
		int count = logicsRuleService.create(logicsRule);
		if(count>0) {
			return new CommonResult().success(count);
		}
		return new CommonResult().failed();
	}
	
	@ApiOperation("更新物流规则")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
	public Object update(@PathVariable Integer id,@RequestBody TLogicsRule logicsRule) {
		int count = logicsRuleService.update(id,logicsRule);
		if(count>0) {
			return new CommonResult().success(count);
		}
		return new CommonResult().failed();
	}
	
	@ApiOperation("查看物流规则信息")
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object query(@PathVariable Integer id) {
    	TLogicsRule logicsRule = logicsRuleService.query(id);
    	return new CommonResult().success(logicsRule);
    }
}
