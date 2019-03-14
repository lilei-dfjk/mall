package com.macro.mall.logistcs.service;

import com.macro.mall.logistcs.bean.ZhRecordBean;
import com.macro.mall.logistcs.model.LogisticTrackModel;

import java.util.List;

public interface LogisticApiService {
    List<LogisticTrackModel> getLogisticTrack(String expressNo);

    String recordLogistic(ZhRecordBean orderBean);

    void uploadIdentify(String name, String identifyNo, String frontUrl, String backUrl);
}
