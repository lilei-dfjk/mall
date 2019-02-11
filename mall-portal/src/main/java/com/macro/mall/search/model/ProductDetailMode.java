package com.macro.mall.search.model;

import com.macro.mall.model.PmsComment;
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
    private long id;
    private List<String> albumPics;
    private BigDecimal cnyPrice;
    private Integer maxBuy;
    private int minBuy = 1;
    private String name;
    private BigDecimal price;
    private BigDecimal weight;
    private String h5Remark;
    private String pcRemark;
}
