package com.macro.mall.logistcs.service;

import com.macro.mall.logistcs.bean.ZhRecordBean;

public interface LogisticApiService {
    String recordLogistic(ZhRecordBean orderBean);

    void uploadIdentify(String name, String identifyNo, String frontUrl, String backUrl);

    void getLogisticTrack(String expressNo);
}
