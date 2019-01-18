package com.macro.mall.logistcs.bean;

import lombok.Data;

import java.util.List;

@Data
public class LogisticsRuleBean {
	private int id;
	private String logisType;
	private String brandRuleType;
	private Integer numberLimit;
	private Double weightLimit;
	private Double priceLimit;
	private Integer mixNumberLimit;
	private Double mixWeightLimit;
	private Double mixPriceLimit;
	private List<String> mixs;
	/**
	 * 是否可混装其他种类产品
	 */
	private boolean mixTypeFlag;
}
