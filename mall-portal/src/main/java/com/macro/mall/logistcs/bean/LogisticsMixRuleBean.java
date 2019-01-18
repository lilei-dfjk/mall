package com.macro.mall.logistcs.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class LogisticsMixRuleBean {
	private int logisType;
	private LogisticsRuleBean logisticsRuleBean;
	private String productType;
	private String parentProductType;
	private Map<String, LogisticsRuleBean> mixs;
}
