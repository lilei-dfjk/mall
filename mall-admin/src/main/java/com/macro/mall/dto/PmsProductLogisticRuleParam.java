package com.macro.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 物流规则
 */
@Data
public class PmsProductLogisticRuleParam {
    @ApiModelProperty(value = "品牌首字母", required = true)
    @NotEmpty(message = "产品Id不能为空")
    private String productId;
    @ApiModelProperty(value = "物流公司")
    private String logisticType;
    @ApiModelProperty(value = "物流规则")
    private String ruleType;
    @ApiModelProperty(value = "物流品牌规则")
    private String ruleBrandType;
}
