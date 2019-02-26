package com.macro.mall.search.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailMode implements Serializable {
    private List<String> albumPics;
    private BigDecimal cnyPrice;
    private String h5Remark;
    private long productId;
    private Integer maxBuy;
    private int minBuy = 1;
    private String name;
    private String pcRemark;
    private BigDecimal price;
    private Integer stock;
    private BigDecimal weight;
    private boolean collect = false;
}
