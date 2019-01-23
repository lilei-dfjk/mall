package com.macro.mall.logistcs.util;

import com.macro.mall.exception.LogisticsException;
import com.macro.mall.logistcs.bean.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.Base64Encoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.axis.client.Call;
import org.apache.commons.io.IOUtils;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.net.URL;
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
        String result = "";
        try {
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);
            call.setTimeout(2000);
            call.setOperationName(qName);// WSDL里面描述的接口名称
            call.addParameter("stock",
                    org.apache.axis.encoding.XMLType.XSD_DATE,
                    javax.xml.rpc.ParameterMode.IN);// 接口的参数
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
            result = (String) call.invoke(new Object[]{xml});
        } catch (Exception e) {
            e.printStackTrace();
            throw new LogisticsException(e.getMessage(), e);
        }
        // 给方法传递参数，并且调用方法
        XStream xStream = new XStream();
        xStream.alias("BackMap", ZhRecordBackBean.class);
        return (ZhRecordBackBean) xStream.fromXML(result);
    }

    public static IdcardbackBean uploadIdentify(String name, String identifyNo, String frontUrl, String backUrl) throws LogisticsException {
        try {
            String endpoint = "http://www.zhonghuan.com.au:8085/API/cxf/common/idcardservice?wsdl";
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);
            call.setTimeout(10000);
            call.setOperationName(new QName("http://zh.au.service.IdcardServiceI.com", "upIdcard"));// WSDL里面描述的接口名称
            call.addParameter("consignee",
                    org.apache.axis.encoding.XMLType.XSD_DATE,
                    javax.xml.rpc.ParameterMode.IN);// 接口的参数
            call.addParameter("cusidcard",
                    org.apache.axis.encoding.XMLType.XSD_DATE,
                    javax.xml.rpc.ParameterMode.IN);// 接口的参数
            call.addParameter("baseidupimg",
                    org.apache.axis.encoding.XMLType.XSD_DATE,
                    javax.xml.rpc.ParameterMode.IN);// 接口的参数
            call.addParameter("baseiddownimg",
                    org.apache.axis.encoding.XMLType.XSD_DATE,
                    javax.xml.rpc.ParameterMode.IN);// 接口的参数
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型

            String result = (String) call.invoke(new Object[]{name, identifyNo, getBase64Image(frontUrl), getBase64Image(backUrl)});
            XStream xStream = new XStream();
            xStream.alias("Idcardback", IdcardbackBean.class);
            return (IdcardbackBean) xStream.fromXML(result);
        } catch (Exception e) {
            System.err.println(e.toString());
            throw new LogisticsException(e.getMessage(), e);
        }
    }

    private static String getBase64Image(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        byte[] bytes = IOUtils.toByteArray(url.openConnection().getInputStream());
        Base64Encoder encoder = new Base64Encoder();
        return encoder.encode(bytes);
    }

    public static ZhLogisTrackBackBean logisticsTrack(String logisNo) throws LogisticsException {
        try {
            String endpoint = "http://www.zhonghuan.com.au:8085/API/cxf/common/logisticsservice?wsdl";
            // 直接引用远程的wsdl文件
            // 以下都是套路
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);
            call.setTimeout(2000);
            call.setOperationName(new QName("http://zh.au.service.LogisticsServiceI.com", "getLogisticsInformation"));// WSDL里面描述的接口名称
            call.addParameter("fydh",
                    org.apache.axis.encoding.XMLType.XSD_DATE,
                    javax.xml.rpc.ParameterMode.IN);// 接口的参数
            call.addParameter("countrytype",
                    org.apache.axis.encoding.XMLType.XSD_DATE,
                    javax.xml.rpc.ParameterMode.IN);// 接口的参数
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
            String result = (String) call.invoke(new Object[]{logisNo, "au"});
            XStream xStream = new XStream(new DomDriver("utf8"));
            xStream.processAnnotations(ZhLogisTrackBackBean.class); // 识别obj类中的注解
            xStream.processAnnotations(ZhLogisTrackBackListBean.class); // 识别obj类中的注解
            return (ZhLogisTrackBackBean) xStream.fromXML(result);
        } catch (Exception e) {
            System.err.println(e.toString());
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
            ZhLogisTrackBackBean zhLogisTrackBackBean = ZhUtils.logisticsTrack(record.getChrfydh());
            System.out.println(zhLogisTrackBackBean);
            IdcardbackBean idcardbackBean = ZhUtils.uploadIdentify("张三", "37126236283",
                    "https://aoyibuy-oss.oss-ap-southeast-2.aliyuncs.com/mall/images/20181231/%E7%BE%8E%E5%8F%AF%E5%8D%93.jpg",
                    "https://aoyibuy-oss.oss-ap-southeast-2.aliyuncs.com/mall/images/20181231/%E7%BE%8E%E5%8F%AF%E5%8D%93.jpg");
            System.out.println(idcardbackBean);
        } catch (LogisticsException e) {
            e.printStackTrace();
        }
    }

}
