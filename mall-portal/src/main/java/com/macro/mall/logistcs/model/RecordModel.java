package com.macro.mall.logistcs.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RecordModel implements Serializable {
    /**
     * 订单ID
     */

    private long orderId;

    /**
     * 重量
     */
    private double weight;

    /**
     * 收件人
     */
    private String recieverName;

    /**
     * 收件人地址
     */
    private String recieverAddress;

    /**
     * 收件人电话
     */
    private String recieverPhone;

    /**
     * 收件人身份证号
     */
    private String recieverIds;

    /**
     * 收件人省
     */
    private String recieverProvince;

    /**
     * 收件人城市
     */
    private String recieverCity;

    /**
     * 寄件人
     */
    private String posterName;
    /**
     * 收件人电话
     */
    private String posterPhone;
    /**
     * 仓库代码
     */
    private String stockCode = "au";

    /**
     * 产品明细
     */
    private List<RecordItemModel> items;

}
