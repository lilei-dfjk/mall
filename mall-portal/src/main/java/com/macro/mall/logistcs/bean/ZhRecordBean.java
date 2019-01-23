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
    /**
     * 营运人名称(平台用户名)
     */
    private String chrusername;
    /**
     * 仓库编码
     */
    private String chrstockcode;
    /**
     * 密码
     */
    private String chrpassword;
    /**
     * 重量
     */
    private String chrzl;
    /**
     * 收件人
     */
    private String chrsjr;
    /**
     * 收件人地址
     */
    private String chrsjrdz;
    /**
     * 收件人电话
     */
    private String chrsjrdh;
    /**
     * 寄件人
     */
    private String chrjjr;
    /**
     * 寄件人电话
     */
    private String chrjjrdh;
    /**
     * 身份证号码
     */
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
