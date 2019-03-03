package com.macro.mall.sms.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.macro.mall.portal.util.JsonUtil;
import com.macro.mall.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SmsServiceImpl implements SmsService {
    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.sms.code.cn}")
    private String cnCode;
    @Value("${aliyun.sms.domain}")
    private String domain;
    @Value("${aliyun.sms.endpoint}")
    private String endpoint;
    @Value("${aliyun.sms.sign}")
    private String sign;

    @Override
    public CommonResponse sendSms(String countryCode, String telephone, String content) {
        DefaultProfile profile = DefaultProfile.getProfile(endpoint, accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        //域名，请勿修改
        request.setDomain("dysmsapi.ap-southeast-1.aliyuncs.com");
        //API版本号，请勿修改
        request.setVersion("2018-05-01");
        //API名称
        request.setAction("SendMessageWithTemplate");
        //接收号码，格式为：国际码+号码，必填
        request.putQueryParameter("To", countryCode + telephone);
        request.putQueryParameter("From", sign);
        Map<String, String> map = new HashMap<>();
        map.put("code", content);
        request.putQueryParameter("TemplateCode", cnCode);
        request.putQueryParameter("TemplateParam", JsonUtil.objectToJson(map));
        request.putQueryParameter("SmsUpExtendCode", "12345");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendToCn(String countryCode, String telephone, String content) {

    }

}
