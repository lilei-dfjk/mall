package com.macro.mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.macro.mall.dto.CommonResult;
import com.macro.mall.model.UmsMember;
import com.macro.mall.service.UmsMemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 会员管理Controller
 * @author Frank 2019/02/17
 *
 */
@Controller
@Api(tags = "UmsMemberController", description = "会员管理")
@RequestMapping("/member")
public class UmsMemberController {
    @Autowired
    private UmsMemberService umsMemberService;
    
    @ApiOperation("添加会员")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object add(@RequestBody UmsMember umsMember) {
        int count = umsMemberService.create(umsMember);
        if(count>0){
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }
    
    @ApiOperation("删除会员")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@RequestParam("ids") List<Long> ids) {
        int count = umsMemberService.delete(ids);
        if(count>0){
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @ApiOperation("修改会员")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable Long id,@RequestBody UmsMember member) {
        int count = umsMemberService.update(id,member);
        if(count>0){
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }
    @ApiOperation("查看会员")
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object query(@PathVariable Long id) {
    	UmsMember umsMember = umsMemberService.query(id);
    	return new CommonResult().success(umsMember);
    }
    
    @ApiOperation("条件查询会员信息")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@ModelAttribute UmsMember umsMember) {
    	List<UmsMember> members = umsMemberService.list(umsMember);
    	return new CommonResult().success(members);
    }
    
    @ApiOperation(value = "根据姓名和电话分页查询会员信息")
    @RequestMapping(value = "/pageList", method = RequestMethod.GET)
    @ResponseBody
    public Object pageList(@RequestParam(value = "userName", required = false) String userName,
    					  @RequestParam(value = "phone", required = false) String phone,
    					  @RequestParam(value = "status", required = false) Integer status,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<UmsMember> umsMembers = umsMemberService.pageList(userName,phone,status,pageNum,pageSize);
        return new CommonResult().pageSuccess(umsMembers);
    }
}
