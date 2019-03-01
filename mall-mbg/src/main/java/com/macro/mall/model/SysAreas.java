package com.macro.mall.model;

import java.io.Serializable;
import java.util.Date;

public class SysAreas implements Serializable {
    /**
     * 区划ID
     *
     * @mbggenerated
     */
    private String id;

    /**
     * 父级ID
     *
     * @mbggenerated
     */
    private String parentId;

    /**
     * 全称
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 全称聚合
     *
     * @mbggenerated
     */
    private String mergerName;

    /**
     * 简称
     *
     * @mbggenerated
     */
    private String shortName;

    /**
     * 简称聚合
     *
     * @mbggenerated
     */
    private String mergerShortName;

    /**
     * 级别
     *
     * @mbggenerated
     */
    private String levelType;

    /**
     * 区号
     *
     * @mbggenerated
     */
    private String cityCode;

    /**
     * 邮编
     *
     * @mbggenerated
     */
    private String zipCode;

    /**
     * 全拼
     *
     * @mbggenerated
     */
    private String pinyin;

    /**
     * 简拼
     *
     * @mbggenerated
     */
    private String jianpin;

    /**
     * 首字母
     *
     * @mbggenerated
     */
    private String firstChar;

    /**
     * 经度
     *
     * @mbggenerated
     */
    private String lng;

    /**
     * 纬度
     *
     * @mbggenerated
     */
    private String lat;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 删除标识，1：未删除；2：已删除；
     *
     * @mbggenerated
     */
    private String delFlag;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMergerName() {
        return mergerName;
    }

    public void setMergerName(String mergerName) {
        this.mergerName = mergerName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getMergerShortName() {
        return mergerShortName;
    }

    public void setMergerShortName(String mergerShortName) {
        this.mergerShortName = mergerShortName;
    }

    public String getLevelType() {
        return levelType;
    }

    public void setLevelType(String levelType) {
        this.levelType = levelType;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getJianpin() {
        return jianpin;
    }

    public void setJianpin(String jianpin) {
        this.jianpin = jianpin;
    }

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", parentId=").append(parentId);
        sb.append(", name=").append(name);
        sb.append(", mergerName=").append(mergerName);
        sb.append(", shortName=").append(shortName);
        sb.append(", mergerShortName=").append(mergerShortName);
        sb.append(", levelType=").append(levelType);
        sb.append(", cityCode=").append(cityCode);
        sb.append(", zipCode=").append(zipCode);
        sb.append(", pinyin=").append(pinyin);
        sb.append(", jianpin=").append(jianpin);
        sb.append(", firstChar=").append(firstChar);
        sb.append(", lng=").append(lng);
        sb.append(", lat=").append(lat);
        sb.append(", remark=").append(remark);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}