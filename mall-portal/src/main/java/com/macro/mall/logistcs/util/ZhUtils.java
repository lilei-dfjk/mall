package com.macro.mall.logistcs.util;

import com.macro.mall.exception.LogisticsException;
import com.macro.mall.logistcs.bean.Ydhwxx;
import com.macro.mall.logistcs.bean.ZhRecordBackBean;
import com.macro.mall.logistcs.bean.ZhRecordBean;
import com.thoughtworks.xstream.XStream;
import org.apache.axis.client.Call;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZhUtils {

    private final String recordEndpoint = "http://www.zhonghuan.com.au:8085/API/cxf/au/recordservice?wsdl";

    public static String getRecordXml(ZhRecordBean recordBean) {
        XStream xStream = new XStream();
        xStream.alias("ydjbxx", ZhRecordBean.class);
        xStream.alias("ydhwxx", Ydhwxx.class);
        return xStream.toXML(recordBean);
    }

    public static ZhRecordBackBean record(ZhRecordBean recordBean) throws LogisticsException {
        String endpoint = "http://www.zhonghuan.com.au:8085/API/cxf/au/recordservice?wsdl";
        QName qName = new QName("http://zh.au.service.RecordServiceI.com", "getRecordrtxml");
        String xml = getRecordXml(recordBean);
        String s = wsdlRequest(endpoint, qName, xml);
        // 给方法传递参数，并且调用方法
        XStream xStream = new XStream();
        xStream.alias("BackMap", ZhRecordBackBean.class);
        return (ZhRecordBackBean) xStream.fromXML(s);
    }

    public static ZhRecordBackBean uploadIdentify(ZhRecordBean recordBean) throws LogisticsException {
        return null;
    }

    public static String logisticsTrack(String logisNo) throws LogisticsException {
        String xml = "<fydh>" + logisNo + "</fydh>" + "<countrytype>au</countrytype>";
        String endpoint = "http://www.zhonghuan.com.au:8085/API/cxf/common/logisticsservice?wsdl";
        QName qName = new QName("http://zh.au.service.LogisticsServiceI.com", "getLogisticsInformation");
        String s = wsdlRequest(endpoint, qName, xml);
        return s;
    }

    private static String wsdlRequest(String endpoint, QName qname, String requestXml) throws LogisticsException {
        try {
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);
            call.setTimeout(2000);
            call.setOperationName(qname);// WSDL里面描述的接口名称
            call.addParameter("stock",
                    org.apache.axis.encoding.XMLType.XSD_DATE,
                    javax.xml.rpc.ParameterMode.IN);// 接口的参数
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
            return (String) call.invoke(new Object[]{requestXml});
        } catch (Exception e) {
            e.printStackTrace();
            throw new LogisticsException(e.getMessage(), e);
        }
    }

    public static void main(String args[]) {
        Ydhwxx ydhwxx = new Ydhwxx("成人奶粉", "德运", "900g", "5.00", "2");
        ZhRecordBean recordBean = new ZhRecordBean("usertest", "au", "123456", "1", "12",
                "1213", "13626994142", "luyuan", "0450494903", "352227198407180525", Arrays.asList(ydhwxx));
        List<Ydhwxx> lists = new ArrayList<>();
        lists.add(ydhwxx);
        recordBean.setYdhwxxlist(lists);
        try {
            ZhRecordBackBean record = ZhUtils.record(recordBean);
            System.out.println(record);
            String s = ZhUtils.logisticsTrack(record.getChrfydh());
        } catch (LogisticsException e) {
            e.printStackTrace();
        }
    }

}
