package com.macro.mall.pay.omipayFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

public class QROrder {

    public JSONObject MakeQROrder(String m_number, String secret_key, String timeStamp, String order_name,
            String currency, int amount, String notify_url, String platform, String out_order_no) {
        return MakeQROrderSelect(m_number, secret_key, timeStamp, order_name, currency, Integer.toString(amount),notify_url, platform, out_order_no,"0");
    }
    
    public JSONObject MakeQROrder(String m_number, String secret_key, String timeStamp, String order_name,
            String currency, int amount, String notify_url, String platform, String out_order_no,String location) {
        return MakeQROrderSelect(m_number, secret_key, timeStamp, order_name, currency, Integer.toString(amount),notify_url, platform, out_order_no,location);
    }
     
    
    public JSONObject MakeQROrderSelect(String m_number, String secret_key, String timeStamp, String order_name,
            String currency, String amount, String notify_url, String platform, String out_order_no,String location) {
        PayFunction payFunction = new PayFunction();
        JSONObject jsonResult = null;

        Random intRandom = new Random();
        int tempInt = intRandom.nextInt(22) + 10;

        String nonce_str = payFunction.getRandomString(tempInt);
        ;
        String sign = payFunction.getMd5(payFunction.getSignString(m_number, timeStamp, nonce_str, secret_key))
                .toUpperCase();

        Map paraMap = new HashMap();
        paraMap.put("m_number", m_number);
        paraMap.put("timestamp", timeStamp);
        paraMap.put("nonce_str", nonce_str);
        paraMap.put("sign", sign);
        paraMap.put("order_name", order_name);
        paraMap.put("currency", currency);
        paraMap.put("amount", amount);
        paraMap.put("notify_url", notify_url);
        paraMap.put("platform", platform);
        paraMap.put("out_order_no", out_order_no);

        String finalUrl = "";
        if(location.equals("0"))
        { 
            finalUrl = payFunction.getUrlString(payFunction.urlCNPrex + "MakeQROrder", paraMap);        
        }
        else
        {
            finalUrl = payFunction.getUrlString(payFunction.urlPrex + "MakeQROrder", paraMap);
        }
     

        try {
            jsonResult = payFunction.sendRequest(finalUrl, "UTF-8");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return jsonResult;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String m_number = "000034";
        String secret_key = "04be255ac7734ee6a4430d1c3fe7bbae";
        String order_name = "测试商品";
        String currency = "AUD";
        int amount = 100;
        String platform = "WECHATPAY";
        String notify_url = "http://your_site.com/receive_notify.html";
        String out_order_no = "SEORD000001";
        String timeStamp = String.valueOf(System.currentTimeMillis());
        QROrder qrOrder = new QROrder();
        JSONObject result = qrOrder.MakeQROrder(m_number, secret_key, timeStamp, order_name, currency, amount,
                notify_url, platform, out_order_no,"1");
        System.out.println(result);
    }

}
