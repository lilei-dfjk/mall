package com.macro.mall.model;

import java.io.Serializable;
import java.util.Date;

public class UmsType implements Serializable {
    private Long id;

    /**
     * 类型代码
     *
     * @mbggenerated
     */
    private String typeCode;

    /**
     * 类型中文名称
     *
     * @mbggenerated
     */
    private String typeCName;

    /**
     * 类型英文名称
     *
     * @mbggenerated
     */
    private String typeEName;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 有效状态；1->有效，0-无效
     *
     * @mbggenerated
     */
    private Integer validStatus;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeCName() {
        return typeCName;
    }

    public void setTypeCName(String typeCName) {
        this.typeCName = typeCName;
    }

    public String getTypeEName() {
        return typeEName;
    }

    public void setTypeEName(String typeEName) {
        this.typeEName = typeEName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", typeCode=").append(typeCode);
        sb.append(", typeCName=").append(typeCName);
        sb.append(", typeEName=").append(typeEName);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remark=").append(remark);
        sb.append(", validStatus=").append(validStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}