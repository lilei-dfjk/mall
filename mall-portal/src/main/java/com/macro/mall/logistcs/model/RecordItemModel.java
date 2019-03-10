package com.macro.mall.logistcs.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RecordItemModel implements Serializable {

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 品名
     */
    private String brandName;

    /**
     * 品牌
     */
    private String brandType;

    /**
     * 规格
     */
    private String spec;

    /**
     * 价格
     */
    private double price;

    /**
     * 数量
     */
    private int quality;

    /**
     * 重量
     */
    private double weight;
}
