package com.macro.mall.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OmsLogisticOrder implements Serializable {
    private Long id;

    private String orderNo;

    private Long orderId;

    private String logisticOrderNo;

    private Double totalWeight;

    private Integer totalNumber;

    private BigDecimal totalPrice;

    private BigDecimal expressPrice;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getLogisticOrderNo() {
        return logisticOrderNo;
    }

    public void setLogisticOrderNo(String logisticOrderNo) {
        this.logisticOrderNo = logisticOrderNo;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getExpressPrice() {
        return expressPrice;
    }

    public void setExpressPrice(BigDecimal expressPrice) {
        this.expressPrice = expressPrice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", orderId=").append(orderId);
        sb.append(", logisticOrderNo=").append(logisticOrderNo);
        sb.append(", totalWeight=").append(totalWeight);
        sb.append(", totalNumber=").append(totalNumber);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", expressPrice=").append(expressPrice);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}