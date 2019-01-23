package com.macro.mall.logistcs.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("back")
public class ZhLogisTrackBackBean implements Serializable {
    @XStreamAlias("kdgsname")
    private String kdgsname;
    @XStreamAlias("kdgsdh")
    private String kdgsdh;
    @XStreamAlias("fydh")
    private String fydh;
    @XStreamAlias("countrytype")
    private String countrytype;
    @XStreamImplicit
    private List<ZhLogisTrackBackListBean> Logisticsback;
}
