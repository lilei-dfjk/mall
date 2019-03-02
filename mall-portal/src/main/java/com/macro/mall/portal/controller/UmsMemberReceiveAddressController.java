package com.macro.mall.portal.controller;

import com.macro.mall.model.UmsMemberReceiveAddress;
import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.service.UmsMemberReceiveAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员收货地址管理Controller
 * Created by macro on 2018/8/28.
 */
@Controller
@Api(tags = "UmsMemberReceiveAddressController", description = "会员收货地址管理")
@RequestMapping("/member/address")
public class UmsMemberReceiveAddressController {
    @Autowired
    private UmsMemberReceiveAddressService memberReceiveAddressService;

    @ApiOperation("添加收货地址")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(@RequestParam(required = true) String name, @RequestParam(required = true) String phoneNumber, @RequestParam(required = true) Integer defaultStatus,
                      @RequestParam(required = true) String postCode, @RequestParam(required = true) String province, @RequestParam(required = true) String city,
                      @RequestParam(required = true) String region, @RequestParam(required = true) String detailAddress, @RequestParam(required = true) String identityFront,
                      @RequestParam(required = true) String identityBack) {
        UmsMemberReceiveAddress address = new UmsMemberReceiveAddress();
        address.setName(name);
        address.setCity(city);
        address.setRegion(region);
        address.setPostCode(postCode);
        address.setProvince(province);
        address.setPhoneNumber(phoneNumber);
        address.setIdentityBack(identityBack);
        address.setDefaultStatus(defaultStatus);
        address.setDetailAddress(detailAddress);
        address.setIdentityFront(identityFront);
        int count = memberReceiveAddressService.add(address);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @ApiOperation("获取默认收获地址")
    @RequestMapping(value = "/default", method = RequestMethod.GET)
    @ResponseBody
    public Object defaultAddrr() {
        UmsMemberReceiveAddress address = memberReceiveAddressService.defaultAddrr();
        return new CommonResult().success(address);
    }

    @ApiOperation("删除收货地址")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@PathVariable Long id) {
        int count = memberReceiveAddressService.delete(id);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @ApiOperation("获取单个收获地址")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object getItem(@PathVariable Long id) {
        UmsMemberReceiveAddress address = memberReceiveAddressService.getItem(id);
        return new CommonResult().success(address);
    }

    @ApiOperation("显示所有收货地址")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list() {
        List<UmsMemberReceiveAddress> addressList = memberReceiveAddressService.list();
        return new CommonResult().success(addressList);
    }

    @ApiOperation("修改收货地址")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable Long id, @RequestParam(required = true) String name, @RequestParam(required = true) String phoneNumber, @RequestParam(required = true) Integer defaultStatus,
                         @RequestParam(required = true) String postCode, @RequestParam(required = true) String province, @RequestParam(required = true) String city,
                         @RequestParam(required = true) String region, @RequestParam(required = true) String detailAddress, @RequestParam(required = true) String identityFront,
                         @RequestParam(required = true) String identityBack) {
        UmsMemberReceiveAddress address = new UmsMemberReceiveAddress();
        address.setName(name);
        address.setCity(city);
        address.setRegion(region);
        address.setPostCode(postCode);
        address.setProvince(province);
        address.setPhoneNumber(phoneNumber);
        address.setIdentityBack(identityBack);
        address.setDefaultStatus(defaultStatus);
        address.setDetailAddress(detailAddress);
        address.setIdentityFront(identityFront);
        int count = memberReceiveAddressService.update(id, address);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }
}
