package com.macro.mall.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OmsLogicOrderItem implements Serializable {
    private Long id;

    private Long logicOrderId;

    private Long productId;

    private String productName;

    private String productType;

    private String productSn;

    private String brand;

    private Integer number;

    private BigDecimal weight;

    private BigDecimal price;

    private String pic;

    private BigDecimal cnyPrice;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLogicOrderId() {
        return logicOrderId;
    }

    public void setLogicOrderId(Long logicOrderId) {
        this.logicOrderId = logicOrderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public BigDecimal getCnyPrice() {
        return cnyPrice;
    }

    public void setCnyPrice(BigDecimal cnyPrice) {
        this.cnyPrice = cnyPrice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", logicOrderId=").append(logicOrderId);
        sb.append(", productId=").append(productId);
        sb.append(", productName=").append(productName);
        sb.append(", productType=").append(productType);
        sb.append(", productSn=").append(productSn);
        sb.append(", brand=").append(brand);
        sb.append(", number=").append(number);
        sb.append(", weight=").append(weight);
        sb.append(", price=").append(price);
        sb.append(", pic=").append(pic);
        sb.append(", cnyPrice=").append(cnyPrice);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}