package com.macro.mall.model;

import java.io.Serializable;
import java.util.Date;

public class UmsCode implements Serializable {
    private Long id;

    /**
     * 代码
     *
     * @mbggenerated
     */
    private String codeCode;

    /**
     * 类型代码
     *
     * @mbggenerated
     */
    private String typeCode;

    /**
     * 代码中文名称
     *
     * @mbggenerated
     */
    private String codeCName;

    /**
     * 代码英文名称
     *
     * @mbggenerated
     */
    private String codeEName;

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
     * 有效状态，0->无效，1->有效
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

    public String getCodeCode() {
        return codeCode;
    }

    public void setCodeCode(String codeCode) {
        this.codeCode = codeCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getCodeCName() {
        return codeCName;
    }

    public void setCodeCName(String codeCName) {
        this.codeCName = codeCName;
    }

    public String getCodeEName() {
        return codeEName;
    }

    public void setCodeEName(String codeEName) {
        this.codeEName = codeEName;
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
        sb.append(", codeCode=").append(codeCode);
        sb.append(", typeCode=").append(typeCode);
        sb.append(", codeCName=").append(codeCName);
        sb.append(", codeEName=").append(codeEName);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remark=").append(remark);
        sb.append(", validStatus=").append(validStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}