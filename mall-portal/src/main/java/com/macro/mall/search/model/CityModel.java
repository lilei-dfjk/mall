package com.macro.mall.search.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityModel implements Serializable {
    /**
     * 层级标识： 1  省份， 2  市， 3  区县
     *
     * @mbggenerated
     */
    private Byte arealevel;
    private Integer cityId;
    /**
     * 名称
     *
     * @mbggenerated
     */
    private String name;
    /**
     * 父节点
     *
     * @mbggenerated
     */
    private Integer parentId;
}
