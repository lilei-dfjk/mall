package com.macro.mall.portal.component;

import com.github.pagehelper.PageHelper;
import com.macro.mall.logistcs.bean.ZhRecordBean;
import com.macro.mall.logistcs.service.LogisticApiService;
import com.macro.mall.mapper.OmsOrderUnderPostMapper;
import com.macro.mall.model.OmsOrderUnderPost;
import com.macro.mall.model.OmsOrderUnderPostExample;
import com.macro.mall.model.UmsMemberReceiveAddress;
import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.model.OmsOrderModel;
import com.macro.mall.portal.model.OmsPostStatus;
import com.macro.mall.portal.service.OmsOrderService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderPostTask {
    private Logger LOGGER = LoggerFactory.getLogger(OrderPostTask.class);
    @Autowired
    private LogisticApiService logisticService;
    @Autowired
    private OmsOrderService portalOrderService;
    @Autowired
    private OmsOrderUnderPostMapper postMapper;

    private void accept(OmsOrderUnderPost post) {
        try {
            LOGGER.info("订单出库中:{}", post.getOrderId());
            post.setStatus((short) OmsPostStatus.HANDLING.getValue());
            postMapper.updateByPrimaryKeySelective(post);
            CommonResult result = portalOrderService.getOrderId(post.getOrderId());
            if (result.getCode() == CommonResult.SUCCESS && null != result.getData()) {
                OmsOrderModel data = (OmsOrderModel) result.getData();
                UmsMemberReceiveAddress address = data.getAddress();
                logisticService.uploadIdentify(address.getName(), address.getIdentityNo(), address.getIdentityFront(), address.getIdentityBack());
                ZhRecordBean orderBean = new ZhRecordBean();
//                logisticService.recordLogistic();
            }
        } catch (Exception ex) {
            LOGGER.error("订单出库错误:{}，{}", post.getOrderId(), ex);
        } finally {
            post.setStatus((short) OmsPostStatus.COMPLETE.getValue());
            postMapper.updateByPrimaryKeySelective(post);
        }
    }

    /**
     * cron表达式：Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
     * 每10分钟扫描一次，扫描设定超时时间之前下的订单，如果没支付则取消该订单
     */
    @Scheduled(cron = "0/10 * * ? * ?")
    private void post() {
        PageHelper.startPage(0, 1);
        OmsOrderUnderPostExample example = new OmsOrderUnderPostExample();
        example.createCriteria().andStatusEqualTo((short) OmsPostStatus.INIT.getValue());
        example.setOrderByClause("create_time");
        List<OmsOrderUnderPost> omsOrderUnderPosts = postMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(omsOrderUnderPosts)) {
            omsOrderUnderPosts.stream().forEach(this::accept);
        }
    }
}
