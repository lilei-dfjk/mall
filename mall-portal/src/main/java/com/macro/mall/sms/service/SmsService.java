package com.macro.mall.sms.service;

import com.aliyuncs.CommonResponse;

public interface SmsService {
    CommonResponse sendSms(String countryCode, String telephone, String content);
}
