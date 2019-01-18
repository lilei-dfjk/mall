package com.macro.mall.pay.schedule;

import com.macro.mall.model.Rate;
import com.macro.mall.pay.omipayFunction.ExchangeRate;
import com.macro.mall.pay.rate.RateService;
import com.macro.mall.search.service.EsProductService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 获取汇率定时器
 */
@Component
public class RateChangeTask {
    private Logger LOGGER = LoggerFactory.getLogger(RateChangeTask.class);
    @Value("${pay.memberId}")
    private String memberId;
    @Value("${pay.memberkey}")
    private String memberKey;
    @Autowired
    private RateService rateService;
    @Autowired
    private EsProductService productService;

    /**
     * cron表达式：Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
     * 每10分钟扫描一次，扫描设定超时时间之前下的订单，如果没支付则取消该订单
     */
    @Scheduled(cron = "0 15 10 * * ?")
//    @Scheduled(fixedDelay = 5000)
    private void cancelTimeOutOrder() {
        String base_currency = "CNY";
        String currency = "AUD";
        JSONObject result = ExchangeRate.GetExchangeRate(memberId, memberKey, String.valueOf(System.currentTimeMillis()), currency, base_currency);
        if (result.get("return_code").equals("SUCCESS")) {
            double rate = Double.parseDouble(String.valueOf(result.get("rate")));
            Rate rateMapper = rateService.getRate(currency, base_currency);

            if (rateMapper != null) {
                rateMapper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                rateMapper.setRate(rate);
                rateService.update(rateMapper);
            } else {
                rateMapper = new Rate();
                rateMapper.setBaseCurrency(base_currency);
                rateMapper.setCurrency(currency);
                rateMapper.setRate(rate);
                rateMapper.setCreateTime(new Timestamp(System.currentTimeMillis()));
                rateMapper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                rateService.save(rateMapper);
            }
            productService.importAll();

            LOGGER.info("今天{}最新费率为{}", System.currentTimeMillis(), rate);
        } else {
            LOGGER.error("获取费率失败");
        }
    }
}
