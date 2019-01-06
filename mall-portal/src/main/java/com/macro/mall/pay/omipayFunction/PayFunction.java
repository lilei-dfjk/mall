package com.macro.mall.pay.omipayFunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.security.cert.CertificateException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public class PayFunction {

    public static String urlPrex = "https://www.omipay.com.au/omipay/api/v2/";
    public static String urlCNPrex = "https://www.omipay.com.cn/omipay/api/v2/";
    public static String urlAuthPrex = "https://www.omipay.com.cn/omipay/api/auth/";
    public static String urlCNAuthPrex = "https://www.omipay.com.cn/omipay/api/auth/";

    public SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("TLSv1.2");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }

    public JSONObject sendRequest(String url, String encoding)
            throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {

        // 采用绕过验证的方式处理https请求
        SSLContext sslcontext = createIgnoreVerifySSL();

        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext)).build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClients.custom().setConnectionManager(connManager);

        // 创建自定义的httpclient对象
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
        // CloseableHttpClient client = HttpClients.createDefault();

        // 创建get方式请求对象
        HttpGet httpGet = new HttpGet(url);
        // 设置header信息
        // 指定报文头【Content-type】、【User-Agent】
        httpGet.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = client.execute(httpGet);

        } catch (Exception ex) {
            System.out.println(ex);
        }

        JSONObject jsonResult = null;
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            String str = "";
            try {
                /** 读取服务器返回过来的json字符串数据 **/
                str = EntityUtils.toString(httpResponse.getEntity());

                /** 把json字符串转换成json对象 **/
                jsonResult = JSONObject.fromObject(str);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

        return jsonResult;
    }

    private MessageDigest md5 = null;

    public PayFunction() {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 用于获取一个String的md5值
     * 
     * @param string
     * @return
     */
    public String getMd5(String str) {
        byte[] bs = md5.digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for (byte x : bs) {
            if ((x & 0xff) >> 4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }

    public String getSignString(String m_number, String timestamp, String nonce_str, String secret_key) {
        return m_number + "&" + timestamp + "&" + nonce_str + "&" + secret_key;
    }

    public String getUrlString(String baseUrl, Map<String, String> paraMap) {
        String url = baseUrl + "?";
        Iterator paras = paraMap.entrySet().iterator();
        while (paras.hasNext()) {
            Map.Entry entry = (Map.Entry) paras.next();
            String tempKey = entry.getKey().toString();
            String tempValue = entry.getValue().toString();
            url += tempKey + "=" + tempValue;
            if (paras.hasNext()) {
                url += "&";
            }

        }
        return url;
    }

    public String getRandomString(int length) { // length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer randomString = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            randomString.append(base.charAt(number));
        }
        return randomString.toString();
    }

    public JSONObject postJSON(String url, Map<String, String> paraMap, String encoding)   
            throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {

        // 采用绕过验证的方式处理https请求
        SSLContext sslcontext = createIgnoreVerifySSL();

        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext)).build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClients.custom().setConnectionManager(connManager);

        // 创建自定义的httpclient对象
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();

        HttpPost httpPost = new HttpPost(url);
      //  CloseableHttpClient client = HttpClients.createDefault();
    

        // json方式
        JSONObject jsonParam = new JSONObject();
        Iterator paras = paraMap.entrySet().iterator();
        while (paras.hasNext()) {
            Map.Entry entry = (Map.Entry) paras.next();
            String tempKey = entry.getKey().toString();
            String tempValue = entry.getValue().toString();
            jsonParam.put(tempKey,tempValue);

        }
        
        StringEntity entity = new StringEntity(jsonParam.toString(), encoding);// 解决中文乱码问题
        entity.setContentEncoding(encoding);
        entity.setContentType("application/json");
        httpPost.setEntity(entity);      
        String responseContent = null;
        HttpResponse response = client.execute(httpPost);
        JSONObject jsonResult = null;
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity httpEntity = response.getEntity();
            responseContent = EntityUtils.toString(httpEntity, encoding);
            jsonResult = JSONObject.fromObject(responseContent);
        }
        return jsonResult;
    }



}
