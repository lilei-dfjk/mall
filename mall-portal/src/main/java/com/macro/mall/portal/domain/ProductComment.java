package com.macro.mall.portal.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 商品评论
 */
@Data
public class ProductComment {
    private Long productId;
    private String comment;
    private Long userId;
    private String userName;
    private String userIcon;
    private List<String> pics;
    private Date createTime;
}
