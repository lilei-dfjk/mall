package com.macro.mall.pay.omipayFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

public class UserInfo {
    public JSONObject GetUserInfo(String m_number, String secret_key, String timeStamp, String redirect_uri) {
        return GetUserInfoSelect(m_number, secret_key, timeStamp, redirect_uri,"0");
    }
    
     
    
    public JSONObject GetUserInfoSelect(String m_number, String secret_key, String timeStamp, String redirect_uri, String location) {
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
        paraMap.put("redirect_uri", redirect_uri);
        paraMap.put("sign", sign);
        
        String finalUrl = "";
        if(location.equals("0"))
        { 
            finalUrl = payFunction.getUrlString(payFunction.urlCNAuthPrex + "GetUserInfo", paraMap);        
        }
        else
        {
            finalUrl = payFunction.getUrlString(payFunction.urlAuthPrex + "GetUserInfo", paraMap);
        }
       // System.out.println(finalUrl);

        try {
            jsonResult = payFunction.sendRequest(finalUrl, "UTF-8");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return jsonResult;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
     // TODO Auto-generated method stub
        String m_number = "000034";
        String secret_key = "04be255ac7734ee6a4430d1c3fe7bbae";
        String redirect_uri = "http://yoursite.com/redirect.html";
        String timeStamp = String.valueOf(System.currentTimeMillis());
        UserInfo userInfo = new UserInfo();
        JSONObject result = userInfo.GetUserInfo(m_number, secret_key, timeStamp,redirect_uri);
        System.out.println(result);
    }

}
