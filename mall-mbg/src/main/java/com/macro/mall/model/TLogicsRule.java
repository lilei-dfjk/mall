package com.macro.mall.model;

import java.io.Serializable;

public class TLogicsRule implements Serializable {
    private Integer id;

    private Short logisType;

    /**
     * 物流分类
     *
     * @mbggenerated
     */
    private String type;

    /**
     * 物流分类名称
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 品名类型
     *
     * @mbggenerated
     */
    private String brandType;

    /**
     * 品名
     *
     * @mbggenerated
     */
    private String brandName;

    /**
     * 数量限制
     *
     * @mbggenerated
     */
    private Integer numberLimit;

    /**
     * 重量限制
     *
     * @mbggenerated
     */
    private Double weightLimit;

    /**
     * 价格限制
     *
     * @mbggenerated
     */
    private Double priceLimit;

    private Integer mixNumberLimit;

    private Double mixWeightLimit;

    private Double mixPriceLimit;

    private String brandMix;

    private String typeMix;

    /**
     * 是否可以与其他大类混装
     *
     * @mbggenerated
     */
    private Short mixTypeFlag;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getLogisType() {
        return logisType;
    }

    public void setLogisType(Short logisType) {
        this.logisType = logisType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandType() {
        return brandType;
    }

    public void setBrandType(String brandType) {
        this.brandType = brandType;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getNumberLimit() {
        return numberLimit;
    }

    public void setNumberLimit(Integer numberLimit) {
        this.numberLimit = numberLimit;
    }

    public Double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(Double weightLimit) {
        this.weightLimit = weightLimit;
    }

    public Double getPriceLimit() {
        return priceLimit;
    }

    public void setPriceLimit(Double priceLimit) {
        this.priceLimit = priceLimit;
    }

    public Integer getMixNumberLimit() {
        return mixNumberLimit;
    }

    public void setMixNumberLimit(Integer mixNumberLimit) {
        this.mixNumberLimit = mixNumberLimit;
    }

    public Double getMixWeightLimit() {
        return mixWeightLimit;
    }

    public void setMixWeightLimit(Double mixWeightLimit) {
        this.mixWeightLimit = mixWeightLimit;
    }

    public Double getMixPriceLimit() {
        return mixPriceLimit;
    }

    public void setMixPriceLimit(Double mixPriceLimit) {
        this.mixPriceLimit = mixPriceLimit;
    }

    public String getBrandMix() {
        return brandMix;
    }

    public void setBrandMix(String brandMix) {
        this.brandMix = brandMix;
    }

    public String getTypeMix() {
        return typeMix;
    }

    public void setTypeMix(String typeMix) {
        this.typeMix = typeMix;
    }

    public Short getMixTypeFlag() {
        return mixTypeFlag;
    }

    public void setMixTypeFlag(Short mixTypeFlag) {
        this.mixTypeFlag = mixTypeFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", logisType=").append(logisType);
        sb.append(", type=").append(type);
        sb.append(", name=").append(name);
        sb.append(", brandType=").append(brandType);
        sb.append(", brandName=").append(brandName);
        sb.append(", numberLimit=").append(numberLimit);
        sb.append(", weightLimit=").append(weightLimit);
        sb.append(", priceLimit=").append(priceLimit);
        sb.append(", mixNumberLimit=").append(mixNumberLimit);
        sb.append(", mixWeightLimit=").append(mixWeightLimit);
        sb.append(", mixPriceLimit=").append(mixPriceLimit);
        sb.append(", brandMix=").append(brandMix);
        sb.append(", typeMix=").append(typeMix);
        sb.append(", mixTypeFlag=").append(mixTypeFlag);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}