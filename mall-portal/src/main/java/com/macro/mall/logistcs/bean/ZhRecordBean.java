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
public class ZhRecordBean implements Serializable {
    private String chrusername;
    private String chrstockcode;
    private String chrpassword;
    private String chrzl;
    private String chrsjr;
    private String chrsjrdz;
    private String chrsjrdh;
    private String chrjjr;
    private String chrjjrdh;
    private String chrsfzhm;
    private List<Ydhwxx> ydhwxxlist;

    public static void main(String args[]) {
        XStream xStream = new XStream();
        xStream.alias("ydjbxx", ZhRecordBean.class);
        xStream.alias("ydhwxx", Ydhwxx.class);
        Ydhwxx ydhwxx = new Ydhwxx("12", "12", "12", "12", "12");
        ZhRecordBean recordBean = new ZhRecordBean("1", "2", "2", "2", "2",
                "2", "2", "2", "2", "2", Arrays.asList(ydhwxx));
        List<Ydhwxx> lists = new ArrayList<>();
        lists.add(ydhwxx);
        recordBean.setYdhwxxlist(lists);
        String s = xStream.toXML(recordBean);
        System.out.println(s);
    }
}
