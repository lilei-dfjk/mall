package com.macro.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class LogisticsRuleBean {
	private int logisType;
	private Integer numberLimit;
	private Integer weightLimit;
	private Double priceLimit;
	private String productType;
	private String parentProductType;
	private List<String> mixs;
}
