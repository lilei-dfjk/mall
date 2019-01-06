package com.macro.mall.pay.omipayFunction;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ExchangeRate {
    public static JSONObject GetExchangeRate(String m_number, String secret_key, String timeStamp, String currency, String base_currency) {
        return GetExchangeRateSelect(m_number, secret_key, timeStamp, currency, base_currency, "0");
    }

    public static JSONObject GetExchangeRate(String m_number, String secret_key, String timeStamp, String currency,
                                      String base_currency, String location) {
        return GetExchangeRateSelect(m_number, secret_key, timeStamp, currency, base_currency, location);
    }

    public static JSONObject GetExchangeRateSelect(String m_number, String secret_key, String timeStamp, String currency,
                                            String base_currency, String location) {
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
        paraMap.put("base_currency", base_currency);
        paraMap.put("currency", currency);

        String finalUrl = "";
        if (location.equals("0")) {
            finalUrl = payFunction.getUrlString(PayFunction.urlCNPrex + "GetExchangeRate", paraMap);
        } else {
            finalUrl = payFunction.getUrlString(PayFunction.urlPrex + "GetExchangeRate", paraMap);
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
        String base_currency = "CNY";
        String currency = "AUD";
        String timeStamp = String.valueOf(System.currentTimeMillis());

        JSONObject result = ExchangeRate.GetExchangeRate(m_number, secret_key, timeStamp, currency, base_currency);
        System.out.println(result);
    }

}
