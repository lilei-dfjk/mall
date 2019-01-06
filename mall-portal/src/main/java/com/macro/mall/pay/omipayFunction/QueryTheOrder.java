package com.macro.mall.pay.omipayFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

public class QueryTheOrder {
    public JSONObject QueryOrder(String m_number, String secret_key, String timeStamp, String order_no) {
        return QueryOrderSelect(m_number, secret_key, timeStamp, order_no,"0");
    }
    
    public JSONObject QueryOrder(String m_number, String secret_key, String timeStamp, String order_no, String location) {
        return QueryOrderSelect(m_number, secret_key, timeStamp, order_no,location);
    }
    
    
    public JSONObject QueryOrderSelect(String m_number, String secret_key, String timeStamp, String order_no, String location) {
        PayFunction payFunction = new PayFunction();
        JSONObject jsonResult = null;

        Random intRandom = new Random();
        int tempInt = intRandom.nextInt(22) + 10;

        String nonce_str = payFunction.getRandomString(tempInt);
     
        String sign = payFunction.getMd5(payFunction.getSignString(m_number, timeStamp, nonce_str, secret_key))
                .toUpperCase();

        Map paraMap = new HashMap();
        paraMap.put("m_number", m_number);
        paraMap.put("timestamp", timeStamp);
        paraMap.put("nonce_str", nonce_str);
        paraMap.put("sign", sign);

        paraMap.put("order_no", order_no);

        String finalUrl = "";
        if(location.equals("0"))
        { 
            finalUrl = payFunction.getUrlString(payFunction.urlCNPrex + "QueryOrder", paraMap);
        }
        else
        {
            finalUrl = payFunction.getUrlString(payFunction.urlPrex + "QueryOrder", paraMap);
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

        String order_no = "bc112874260946a2af2b7107825e6ce2";
        String timeStamp = String.valueOf(System.currentTimeMillis());

        QueryTheOrder queryTheOrder = new QueryTheOrder();
        JSONObject result = queryTheOrder.QueryOrder(m_number, secret_key, timeStamp, order_no);
        System.out.println(result);
    }
}
