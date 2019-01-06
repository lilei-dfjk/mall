package com.macro.mall.pay.omipayFunction;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

public class ApplyRefund {
    public JSONObject Refund(String m_number, String secret_key,String timeStamp,int amount,String order_no,String out_refund_no,String location){
        return RefundSelect(m_number,secret_key,timeStamp,Integer.toString(amount),order_no,out_refund_no,location);
    }
    public JSONObject Refund(String m_number, String secret_key,String timeStamp,int amount,String order_no,String out_refund_no){
        return RefundSelect(m_number,secret_key,timeStamp,Integer.toString(amount),order_no,out_refund_no,"0");
    }
	public JSONObject RefundSelect(String m_number, String secret_key,String timeStamp,String amount,String order_no,String out_refund_no,String location)
	{
		PayFunction  payFunction = new PayFunction();
		JSONObject jsonResult = null;

		Random intRandom = new Random();
		int tempInt = intRandom.nextInt(22) + 10;
		
        
		String nonce_str = payFunction.getRandomString(tempInt);;
		String sign = payFunction.getMd5(payFunction.getSignString(m_number,timeStamp,nonce_str,secret_key)).toUpperCase();
		
		Map paraMap = new HashMap();
		paraMap.put("m_number", m_number);
		paraMap.put("timestamp", timeStamp);
		paraMap.put("nonce_str", nonce_str);
		paraMap.put("sign", sign);
		paraMap.put("amount", amount);
		paraMap.put("order_no", order_no);
		paraMap.put("out_refund_no", out_refund_no);
		
		String finalUrl = "";
        if(location.equals("0"))
        { 
            finalUrl = payFunction.getUrlString(payFunction.urlCNPrex + "Refund",paraMap);
        }
        else
        {
            finalUrl = payFunction.getUrlString(payFunction.urlPrex + "Refund",paraMap);
        }
	//	System.out.println(finalUrl);
		
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
		int amount  = 100;
		String order_no = "bc112874260946a2af2b7107825e6ce2";
		String out_refund_no = "xxxxxxx";
		 
		String timeStamp = String.valueOf(System.currentTimeMillis());   
		ApplyRefund  applyRefund = new ApplyRefund();
		JSONObject result = applyRefund.Refund(m_number,secret_key,timeStamp,amount,order_no,out_refund_no);
		System.out.println(result);
	}
}
