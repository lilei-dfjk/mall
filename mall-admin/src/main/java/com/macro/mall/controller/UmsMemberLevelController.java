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
import com.macro.mall.model.UmsMemberLevel;
import com.macro.mall.service.UmsMemberLevelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 会员等级管理Controller
 * Created by macro on 2018/4/26.
 */
@Controller
@Api(tags = "UmsMemberLevelController",description = "会员等级管理")
@RequestMapping("/memberLevel")
public class UmsMemberLevelController {
    @Autowired
    private UmsMemberLevelService memberLevelService;
    
    @ApiOperation("查询所有会员等级")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public Object list(){
        List<UmsMemberLevel> memberLevelList = memberLevelService.list();
        return new CommonResult().success(memberLevelList);
    }
    
    @ApiOperation("添加会员等级")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(@RequestBody UmsMemberLevel memberLevel) {
    	int count = memberLevelService.create(memberLevel);
        if(count>0){
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }
    @ApiOperation("修改会员等级")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable Long id,@RequestBody UmsMemberLevel memberLevel) {
    	int count = memberLevelService.update(id,memberLevel);
        if(count>0){
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }
    @ApiOperation("删除会员等级")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@RequestParam("ids") List<Long> ids) {
    	int count = memberLevelService.delete(ids);
        if(count>0){
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }
    @ApiOperation("查看会员等级")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object view(@PathVariable Long id) {
    	UmsMemberLevel memberLevel = memberLevelService.view(id);
    	return new CommonResult().success(memberLevel);
    }
    @ApiOperation(value = "根据姓名和状态分页查询会员信息")
    @RequestMapping(value = "/pageList", method = RequestMethod.GET)
    @ResponseBody
    public Object pageList(@RequestParam(value = "name", required = false) String name,
    					  @RequestParam(value = "defaultStatus", required = false) Integer status,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<UmsMemberLevel> levels = memberLevelService.pageList(name,status,pageNum,pageSize);
        return new CommonResult().pageSuccess(levels);
    }
}
