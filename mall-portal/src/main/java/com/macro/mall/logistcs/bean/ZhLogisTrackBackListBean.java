package com.macro.mall.logistcs.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("Logisticsback")
public class ZhLogisTrackBackListBean implements Serializable {
    @XStreamAlias("ztai")
    private String ztai;
    @XStreamAlias("time")
    private String time;
}
