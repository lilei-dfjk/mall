package com.macro.mall.pay.omipayFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

public class PushPayMsg {
    public JSONObject PushPayResultMessage(String m_number, String secret_key, String timeStamp, String notify_url,
            String return_code, String order_no, String out_order_no, String currency, int total_amount,
            String order_time, String pay_time, int exchange_rate, int cny_amount) {
        PayFunction payFunction = new PayFunction();
        JSONObject jsonResult = null;

        Random intRandom = new Random();
        int tempInt = intRandom.nextInt(22) + 10;

        String nonce_str = payFunction.getRandomString(tempInt);      
        String sign = payFunction.getMd5(payFunction.getSignString(m_number, timeStamp, nonce_str, secret_key))
                .toUpperCase();

        Map paraMap = new HashMap();
        paraMap.put("return_code", return_code);
        paraMap.put("nonce_str", nonce_str);
        paraMap.put("timestamp", timeStamp);
        paraMap.put("sign", sign);
        paraMap.put("order_no", order_no);
        paraMap.put("out_order_no", out_order_no);
        paraMap.put("currency", currency);
        paraMap.put("total_amount", Integer.toString(total_amount));
        paraMap.put("order_time", order_time);
        paraMap.put("pay_time", pay_time);
        paraMap.put("exchange_rate", Integer.toString(exchange_rate));
        paraMap.put("cny_amount", Integer.toString(cny_amount));

        try {
            jsonResult = payFunction.postJSON(notify_url, paraMap, "UTF-8");
           

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return jsonResult;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String m_number =  "000034";
        String secret_key = "04be255ac7734ee6a4430d1c3fe7bbae";
        String return_code = "SUCCESS";
        Random intRandom = new Random();
        int tempInt = intRandom.nextInt(22) + 10;
        PayFunction payFunction = new PayFunction();
        String nonce_str = payFunction.getRandomString(tempInt);
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String sign = payFunction.getMd5(payFunction.getSignString(m_number, timeStamp, nonce_str, secret_key))
                .toUpperCase();       
        String notify_url = "http://localhost:8080/Demo/Omipay/ValidatePushMsg";
        String order_no = "测试商品";
        String out_order_no = "SEORD000001";
        String currency =  "AUD";
        int total_amount = 100;
        String order_time = "2017-12-11";
        String pay_time = "2017-12-11";
        int exchange_rate = 2;
        int cny_amount = 100;
        
        PushPayMsg pushPayMsg = new PushPayMsg();
        JSONObject jsonResult =pushPayMsg.PushPayResultMessage(m_number, secret_key, timeStamp, notify_url,
                return_code, order_no, out_order_no, currency,  total_amount,
                order_time,  pay_time, exchange_rate, cny_amount);
        System.out.println(jsonResult.get("return_code"));
    }
}
