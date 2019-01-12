package com.macro.mall.portal.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;
import java.util.List;

/**
 * 商品评论
 */
@Data
public class ProductComment {
    @Indexed
    private Long productId;
    @Indexed
    private Long orderId;
    private String comment;
    @Indexed
    private Long userId;
    private String userName;
    private String userIcon;
    private List<String> pics;
    private Date createTime;
}
