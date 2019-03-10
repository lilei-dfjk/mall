package com.macro.mall.logistcs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticTrackModel {
    private String track;
    private String time;
    private String location;
}
