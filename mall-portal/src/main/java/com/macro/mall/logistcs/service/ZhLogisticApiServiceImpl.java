package com.macro.mall.logistcs.service;

import com.macro.mall.logistcs.bean.OrderBean;
import com.macro.mall.logistcs.bean.ZhRecordBackBean;
import com.macro.mall.logistcs.bean.ZhRecordBean;
import com.macro.mall.logistcs.util.ZhUtils;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

@Service
public class ZhLogisticApiServiceImpl implements LogisticApiService {
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

    @Override
    public void getLogisticTrack(String expressNo) {

    }
}