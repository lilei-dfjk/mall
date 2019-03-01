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
import com.macro.mall.model.UmsCode;
import com.macro.mall.model.UmsType;
import com.macro.mall.service.UmsCodeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 代码配置管理
 * Created by macro on 2018/4/26.
 */
@Controller
@Api(tags = "UmsCodeController", description = "代码配置管理")
@RequestMapping("/code")
public class UmsCodeController {

	@Autowired
	private UmsCodeService umsCodeService;
	
	@ApiOperation(value = "分页查询代码配置类型信息")
    @RequestMapping(value = "/typePageList", method = RequestMethod.GET)
    @ResponseBody
	public Object typePageList(@RequestParam(value = "typeCode", required = false) String typeCode,
			@RequestParam(value = "typeCName", required = false) String typeCName,
			@RequestParam(value = "validStatus", required = false) Integer validStatus,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
		List<UmsType> types = umsCodeService.typePageList(typeCode,typeCName,validStatus,pageNum,pageSize);
		return new CommonResult().pageSuccess(types);
	}
	
	@ApiOperation(value = "增加代码配置类型信息")
    @RequestMapping(value = "/createType", method = RequestMethod.POST)
    @ResponseBody
	public Object createType(@RequestBody UmsType umsType) {
		int count = umsCodeService.createType(umsType);
        if(count>0){
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
	}
	
	@ApiOperation("删除代码配置类型信息")
    @RequestMapping(value = "/deleteType", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteType(@RequestParam("ids") List<Long> ids) {
        int count = umsCodeService.deleteType(ids);
        if(count>0){
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }
	@ApiOperation("修改代码配置类型信息")
    @RequestMapping(value = "/updateType/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object updateType(@PathVariable Long id,@RequestBody UmsType umsType) {
        int count = umsCodeService.updateType(id,umsType);
        if(count>0){
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }
	
    @ApiOperation("根据代码类型查询代码列表")
    @RequestMapping(value = "/codePageList/{typeCode}", method = RequestMethod.GET)
    @ResponseBody
    public Object codePageList(@PathVariable String typeCode,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<UmsCode> codes = umsCodeService.codePageList(typeCode,pageSize,pageNum);
        return new CommonResult().pageSuccess(codes);
    }
    
    @ApiOperation(value = "增加代码配置信息")
    @RequestMapping(value = "/createCode", method = RequestMethod.POST)
    @ResponseBody
    public Object createCode(@RequestBody UmsCode umsCode) {
    	int count = umsCodeService.createCode(umsCode);
        if(count>0){
            return new CommonResult().success(count);
        }else if(count == -1) {
        	return new CommonResult().validateFailed("已经存在代码相同的信息！");
        }else {
        	return new CommonResult().failed();
        }
    }
    @ApiOperation("删除代码配置信息")
    @RequestMapping(value = "/deleteCode", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteCode(@RequestParam("ids") List<Long> ids) {
        int count = umsCodeService.deleteCode(ids);
        if(count>0){
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }
	@ApiOperation("修改代码配置信息")
    @RequestMapping(value = "/updateCode/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object updateCode(@PathVariable Long id,@RequestBody UmsCode umsCode) {
        int count = umsCodeService.updateCode(id,umsCode);
        if(count>0){
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }
	@ApiOperation("修改代码配置信息")
    @RequestMapping(value = "/viewCode/{id}", method = RequestMethod.GET)
    @ResponseBody
	public Object viewCode(@PathVariable Long id) {
		UmsCode umsCode = umsCodeService.viewCode(id);
		return new CommonResult().success(umsCode);
	}
}
