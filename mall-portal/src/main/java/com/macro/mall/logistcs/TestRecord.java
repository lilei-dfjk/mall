package com.macro.mall.logistcs;

public class TestRecord {
    public static void main(String[] args) {
        String  stock = "<?xml version='1.0' encoding='UTF-8' standalone='no'?>";
        stock+="<ydjbxx>";
        stock+="<chrusername>usertest</chrusername>";
        stock+="<chrstockcode>au</chrstockcode>";
        stock+="<chrpassword>zh67792225</chrpassword>";
        stock+="<chryyrmc>2077</chryyrmc>";
        stock+="<chrzydhm>160-91239396</chrzydhm>";
        stock+="<chrhbh>CX110/CX052</chrhbh>";
        stock+="<chrjckrq></chrjckrq>";
        stock+="<chrzl></chrzl>";
        stock+="<chrsjr></chrsjr>";
        stock+="<chrsjrdz></chrsjrdz>";
        stock+="<chrsjrdh>13626994142</chrsjrdh>";
        stock+="<chrjjr>luyuan</chrjjr>";
        stock+="<chrjjrdh>0450494903</chrjjrdh>";
        stock+="<chrsfzhm>352227198407180525</chrsfzhm>";
        stock+="<ydhwxxlist>";
        stock+="<ydhwxx>";
        stock+="<chrpm>成人奶粉</chrpm>";
        stock+="<chrpp>德运</chrpp>";
        stock+="<chrggxh>900</chrggxh>";
        stock+="<chrjz>50.00</chrjz>";
        stock+="<chrjs>25</chrjs>";
        stock+="</ydhwxx>";
        stock+="<ydhwxx>";
        stock+="<chrpm>成人奶粉</chrpm>";
        stock+="<chrpp>德运</chrpp>";
        stock+="<chrggxh>900</chrggxh>";
        stock+="<chrjz>50.00</chrjz>";
        stock+="<chrjs>25</chrjs>";
        stock+="</ydhwxx>";
        stock+="</ydhwxxlist>";
        stock+="</ydjbxx>";
//        JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
//        bean.setServiceClass(RecordServiceI.class);
//        bean.setAddress("http://localhost:8081/API/cxf/au/recordservice?wsdl");
//        RecordServiceI client = (RecordServiceI) bean.create();
//        System.out.println(client.getRecord(stock));
    }
}
