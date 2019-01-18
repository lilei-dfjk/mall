package com.macro.mall.pay.omipayFunction;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class JSAPIOrder {
    public static JSONObject MakeJSAPIOrder(String m_number, String secret_key, String timeStamp, String order_name, String currency, int amount, String notify_url, String redirect_url, String out_order_no, String location, boolean direct_pay) {
        return MakeJSAPIOrderSelect(m_number, secret_key, timeStamp, order_name, currency, amount, notify_url, redirect_url, out_order_no, location, direct_pay);
    }

    public static JSONObject MakeJSAPIOrder(String m_number, String secret_key, String timeStamp, String order_name, String currency, int amount, String notify_url, String redirect_url, String out_order_no, boolean direct_pay) {
        return MakeJSAPIOrderSelect(m_number, secret_key, timeStamp, order_name, currency, amount, notify_url, redirect_url, out_order_no, "0", direct_pay);
    }

    public static JSONObject MakeJSAPIOrderSelect(String m_number, String secret_key, String timeStamp, String order_name, String currency, int amount, String notify_url, String redirect_url, String out_order_no, String location, boolean direct_pay) {
        PayFunction payFunction = new PayFunction();
        JSONObject jsonResult = null;
        Random intRandom = new Random();
        int tempInt = intRandom.nextInt(22) + 10;
        String nonce_str = payFunction.getRandomString(tempInt);
        String sign = payFunction.getMd5(payFunction.getSignString(m_number, timeStamp, nonce_str, secret_key)).toUpperCase();

        Map paraMap = new HashMap();
        paraMap.put("m_number", m_number);
        paraMap.put("timestamp", timeStamp);
        paraMap.put("nonce_str", nonce_str);
        paraMap.put("sign", sign);
        paraMap.put("order_name", order_name);
        paraMap.put("currency", currency);
        paraMap.put("amount", amount);
        paraMap.put("notify_url", notify_url);
        paraMap.put("redirect_url", redirect_url);
        paraMap.put("out_order_no", out_order_no);
        if (direct_pay) {
            paraMap.put("direct_pay", 1);
        }

        String finalUrl = "";
        if (location.equals("0")) {
            finalUrl = payFunction.getUrlString(PayFunction.urlCNPrex + "MakeJSAPIOrder", paraMap);
        } else {
            finalUrl = payFunction.getUrlString(PayFunction.urlPrex + "MakeJSAPIOrder", paraMap);
        }
        System.out.println(finalUrl);
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
        String redirect_url = "http://410c108e.ngrok.io";
        String notify_url = "http://410c108e.ngrok.io";
        String out_order_no = "SEORD000001";
        String timeStamp = String.valueOf(System.currentTimeMillis());
        JSONObject result = JSAPIOrder.MakeJSAPIOrder(m_number, secret_key, timeStamp, order_name, currency, amount,
                notify_url, redirect_url, out_order_no, true);
        System.out.println(result);
    }

}
