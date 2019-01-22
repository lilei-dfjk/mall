package com.macro.mall.logistcs.bean;

import com.thoughtworks.xstream.XStream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZhRecordBackBean implements Serializable {
    private String backmsg;
    private String chrfydh;
    private String backjudge;
    private String msgtype;
}
