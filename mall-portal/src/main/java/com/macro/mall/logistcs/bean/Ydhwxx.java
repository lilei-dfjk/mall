package com.macro.mall.logistcs.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ydhwxx {
    /**
     * 品名
     */
    private String chrpm;
    /**
     * 品牌
     */
    private String chrpp;
    /**
     * 规格型号
     */
    private String chrggxh;
    /**
     * 价值
     */
    private String chrjz;
    /**
     * 件数
     */
    private String chrjs;
}