package com.macro.mall.logistcs.service;

import com.macro.mall.exception.LogisticsException;
import com.macro.mall.logistcs.LogisticTrackModel;
import com.macro.mall.logistcs.bean.ZhRecordBean;

import java.util.List;

public interface LogisticApiService {
    String recordLogistic(ZhRecordBean orderBean);

    void uploadIdentify(String name, String identifyNo, String frontUrl, String backUrl);

    List<LogisticTrackModel> getLogisticTrack(String expressNo);
}
