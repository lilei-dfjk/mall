package com.macro.mall.search.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityModel implements Serializable {
    private Map<String, String> province_list;
    private Map<String, String> city_list;
    private Map<String, String> county_list;
}
