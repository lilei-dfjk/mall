package com.macro.mall.pay.omipayFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

public class QueryTheRefund {
    public JSONObject QueryRefund(String m_number, String secret_key, String timeStamp, String refund_no)
    {
        return QueryRefundSelect(m_number, secret_key, timeStamp, refund_no,"0");
    }
    
    public JSONObject QueryRefund(String m_number, String secret_key, String timeStamp, String refund_no,String location)
    {
        return QueryRefundSelect(m_number, secret_key, timeStamp, refund_no,location);
    }
    
    public JSONObject QueryRefundSelect(String m_number, String secret_key, String timeStamp, String refund_no,String location) {
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

        paraMap.put("refund_no", refund_no);

        String finalUrl = "";
        if(location.equals("0"))
        { 
            finalUrl =  payFunction.getUrlString(payFunction.urlCNPrex + "QueryRefund", paraMap);
        }
        else
        {
            finalUrl =  payFunction.getUrlString(payFunction.urlPrex + "QueryRefund", paraMap);
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

        String refund_no = "bc112874260946a2af2b7107825e6ce2";

        String timeStamp = String.valueOf(System.currentTimeMillis());
        QueryTheRefund queryTheRefund = new QueryTheRefund();
        JSONObject result = queryTheRefund.QueryRefund(m_number, secret_key, timeStamp, refund_no);
        System.out.println(result);
    }
}
