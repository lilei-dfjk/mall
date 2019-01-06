package com.macro.mall.pay.omipayFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

public class OnlineOrder {
    public JSONObject MakeOnlineOrder(String m_number, String secret_key, String timeStamp,String order_name,String currency,int amount,String notify_url,String out_order_no,String return_url,String type)
    {
        return MakeOnlineOrderSelect(m_number,secret_key,timeStamp,order_name,currency,Integer.toString(amount),notify_url,out_order_no,return_url,type,"0");
    }
    
    public JSONObject MakeOnlineOrder(String m_number, String secret_key, String timeStamp,String order_name,String currency,int amount,String notify_url,String out_order_no,String return_url,String type,String location)
    {
        return MakeOnlineOrderSelect(m_number,secret_key,timeStamp,order_name,currency,Integer.toString(amount),notify_url ,out_order_no,return_url,type,"1");
    }
    
    public JSONObject MakeOnlineOrderSelect(String m_number, String secret_key, String timeStamp,String order_name,String currency,String amount,String notify_url,String out_order_no,String return_url,String type,String location)
    {
        PayFunction  payFunction = new PayFunction();
        JSONObject jsonResult = null;
        
        Random intRandom = new Random();
        int tempInt = intRandom.nextInt(22) + 10; 
        String nonce_str = payFunction.getRandomString(tempInt);        
        String sign = payFunction.getMd5(payFunction.getSignString(m_number,timeStamp,nonce_str,secret_key)).toUpperCase();
        
        Map paraMap = new HashMap();
        paraMap.put("m_number", m_number);
        paraMap.put("timestamp", timeStamp);
        paraMap.put("nonce_str", nonce_str);
        paraMap.put("sign", sign);
        paraMap.put("order_name", order_name);
        paraMap.put("currency", currency);
        paraMap.put("amount", amount);
        paraMap.put("notify_url", notify_url);
        paraMap.put("out_order_no", out_order_no);
        paraMap.put("return_url", return_url);
        paraMap.put("type", type);
        
        String finalUrl = "";
        if(location.equals("0"))
        {
            finalUrl = payFunction.getUrlString(payFunction.urlCNPrex + "MakeOnlineOrder",paraMap);
        }
        else
        {
            finalUrl = payFunction.getUrlString(payFunction.urlPrex + "MakeOnlineOrder",paraMap);
        }
      //  System.out.println(finalUrl);
        
        try
        {
            jsonResult = payFunction.sendRequest(finalUrl, "UTF-8");
        }catch(Exception ex)
        {
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
        String notify_url = "http://your_site.com/receive_notify.html";
        String out_order_no = "SEORD000001";
        String return_url = "http://your_site.com/receive_return.html";
        String type = "wap";
        String timeStamp = String.valueOf(System.currentTimeMillis());  
        OnlineOrder onlineOrder = new OnlineOrder();
        JSONObject result = onlineOrder.MakeOnlineOrder(m_number,secret_key,timeStamp,order_name,currency,amount,notify_url,out_order_no,return_url,type);
        System.out.println(result);
    }
}
