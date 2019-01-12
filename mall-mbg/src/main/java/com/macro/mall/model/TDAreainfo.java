package com.macro.mall.model;

import java.io.Serializable;

public class TDAreainfo implements Serializable {
    private Integer id;

    /**
     * 名称
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 层级标识： 1  省份， 2  市， 3  区县
     *
     * @mbggenerated
     */
    private Byte arealevel;

    /**
     * 父节点
     *
     * @mbggenerated
     */
    private Integer parentId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getArealevel() {
        return arealevel;
    }

    public void setArealevel(Byte arealevel) {
        this.arealevel = arealevel;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", arealevel=").append(arealevel);
        sb.append(", parentId=").append(parentId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}