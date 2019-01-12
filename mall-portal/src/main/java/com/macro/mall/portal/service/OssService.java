package com.macro.mall.portal.service;

import com.macro.mall.portal.model.OssCallbackResult;
import com.macro.mall.portal.model.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;

public interface OssService {
    OssPolicyResult policy();

    OssCallbackResult callback(HttpServletRequest request);
}
