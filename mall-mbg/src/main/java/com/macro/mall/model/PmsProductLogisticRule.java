package com.macro.mall.model;

import java.io.Serializable;
import java.util.Date;

public class PmsProductLogisticRule implements Serializable {
    private Integer id;

    private Long productId;

    private Short logisticType;

    private String ruleType;

    private String ruleBrandType;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Short getLogisticType() {
        return logisticType;
    }

    public void setLogisticType(Short logisticType) {
        this.logisticType = logisticType;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getRuleBrandType() {
        return ruleBrandType;
    }

    public void setRuleBrandType(String ruleBrandType) {
        this.ruleBrandType = ruleBrandType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", logisticType=").append(logisticType);
        sb.append(", ruleType=").append(ruleType);
        sb.append(", ruleBrandType=").append(ruleBrandType);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}