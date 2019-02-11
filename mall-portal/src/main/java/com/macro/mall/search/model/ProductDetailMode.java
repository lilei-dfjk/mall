package com.macro.mall.search.model;

import com.macro.mall.model.PmsComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailMode implements Serializable {
    private List<String> albumPics;
    private double cnyPrice;
    private int maxBuy;
    private int minBuy = 1;
    private String name;
    private double price;
    private double weight;
    private String h5Remark;
    private String pcRemark;
}
