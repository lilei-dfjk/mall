package com.macro.mall.logistcs.service;

import com.macro.mall.logistcs.bean.LogisticsOrderBean;
import com.macro.mall.logistcs.cons.LogisticType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpressCalcServiceImpl implements ExpressCalcService {
    @Value("${zh.express.price}")
    private double zhExpressPrice;
    @Value("${ad.express.price}")
    private double adExpressPrice;
    @Value("${bag.weight}")
    private double bagWeight;

    @Override
    public void getExpressPrice(LogisticType logisticType, List<LogisticsOrderBean> orders) {
        orders.stream().forEach(order -> order.setExpressPrice(getExpressPrice(logisticType, order)));
    }

    /**
     * descc:.运费计算按照 （产品重量+包装箱重量）x运费单价计算，每个包装箱按照0.4KG计算，运费单价中环：AUD5.5；
     * 澳德：AUD6，总重量低于1KG按照1KG计算
     *
     * @param logisticType
     * @param orderBean
     * @return
     */
    @Override
    public double getExpressPrice(LogisticType logisticType, LogisticsOrderBean orderBean) {
        double price = 0;
        if (logisticType == LogisticType.ZH) {
            price = zhExpressPrice;
        } else {
            price = adExpressPrice;
        }
        double weight = (orderBean.getTotalWeight() + bagWeight) / 1000;
        if (weight < 1) {
            weight = 1;
        }
        return weight * price;
    }
}
