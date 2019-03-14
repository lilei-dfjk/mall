package com.macro.mall.logistcs.service;

import com.macro.mall.exception.LogisticsException;
import com.macro.mall.logistcs.bean.ZhLogisTrackBackBean;
import com.macro.mall.logistcs.bean.ZhLogisTrackBackListBean;
import com.macro.mall.logistcs.bean.ZhRecordBackBean;
import com.macro.mall.logistcs.bean.ZhRecordBean;
import com.macro.mall.logistcs.model.LogisticTrackModel;
import com.macro.mall.logistcs.util.ZhUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZhLogisticApiServiceImpl implements LogisticApiService {
    @Override
    public List<LogisticTrackModel> getLogisticTrack(String expressNo) {
        ZhLogisTrackBackBean zhLogisTrackBackBean = null;
        try {
            zhLogisTrackBackBean = ZhUtils.logisticsTrack(expressNo);
            List<ZhLogisTrackBackListBean> logisticsback = zhLogisTrackBackBean.getLogisticsback();
            if (CollectionUtils.isNotEmpty(logisticsback)) {
                return logisticsback.stream().map(logic -> new LogisticTrackModel(logic.getZtai(), logic.getTime(), "")).collect(Collectors.toList());
            }
        } catch (LogisticsException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String recordLogistic(ZhRecordBean zhRecordBean) {
        try {
            ZhRecordBackBean record = ZhUtils.record(zhRecordBean);
            if (record.getBackjudge().equals("true")) {
                return record.getChrfydh();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void uploadIdentify(String name, String identifyNo, String frontUrl, String backUrl) {

    }
}
