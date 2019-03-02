package com.macro.mall.model;

import java.io.Serializable;
import java.util.Date;

public class UmsMemberComment implements Serializable {
    private Long id;

    private Long productId;

    private Long memberId;

    private Long orderId;

    private String comment;

    /**
     * 图片
     *
     * @mbggenerated
     */
    private String pics;

    /**
     * 星级
     *
     * @mbggenerated
     */
    private Double star;

    private Date createTime;

    /**
     * 会员昵称
     *
     * @mbggenerated
     */
    private String memberNickName;

    /**
     * 会员头像
     *
     * @mbggenerated
     */
    private String memberHeadPic;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public Double getStar() {
        return star;
    }

    public void setStar(Double star) {
        this.star = star;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMemberNickName() {
        return memberNickName;
    }

    public void setMemberNickName(String memberNickName) {
        this.memberNickName = memberNickName;
    }

    public String getMemberHeadPic() {
        return memberHeadPic;
    }

    public void setMemberHeadPic(String memberHeadPic) {
        this.memberHeadPic = memberHeadPic;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", memberId=").append(memberId);
        sb.append(", orderId=").append(orderId);
        sb.append(", comment=").append(comment);
        sb.append(", pics=").append(pics);
        sb.append(", star=").append(star);
        sb.append(", createTime=").append(createTime);
        sb.append(", memberNickName=").append(memberNickName);
        sb.append(", memberHeadPic=").append(memberHeadPic);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}