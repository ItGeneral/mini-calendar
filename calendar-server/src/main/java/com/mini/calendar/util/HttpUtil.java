package com.mini.calendar.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author： seagull
 * @date 2017年12月1日 上午9:29:40
 */
public class HttpUtil {


    public static String get(String url, boolean verifySSL){
        return get(url, new HashMap<>(), verifySSL);
    }

    public static String get(String url){
        return get(url, new HashMap<>(), true);
    }

    /**
     * 字符串参数
     * @return 返回请求结果
     */
    public static String get(String url, Map<String, String> headerMap, boolean verifySSL) {
        StringBuffer resultBuffer;

        BufferedReader br = null;
        // 构建请求参数
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient httpclient = null;
        try {
            if (verifySSL){
                httpclient = HttpClients.createDefault();
            }else{
                //采用绕过验证的方式处理https请求
                SSLContext sslcontext = createIgnoreVerifySSL();
                //设置协议http和https对应的处理socket链接工厂的对象
                Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.INSTANCE)
                        .register("https", new SSLConnectionSocketFactory(sslcontext, NoopHostnameVerifier.INSTANCE))
                        .build();
                PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
                HttpClients.custom().setConnectionManager(connManager);
                httpclient = HttpClients.custom().setConnectionManager(connManager).build();
            }

            for (String key : headerMap.keySet()) {
                httpGet.setHeader(key, headerMap.get(key));
            }

            HttpResponse response = httpclient.execute(httpGet);
            // 读取服务器响应数据
            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), Charset.forName("gbk")));
            String temp;
            resultBuffer = new StringBuffer();
            while ((temp = br.readLine()) != null) {
                resultBuffer.append(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
					throw new RuntimeException(e);
                }
            }
            if (httpclient != null){
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultBuffer.toString();
    }

    /**
     * 使用HttpClient以JSON格式发送post请求
     *
     * @param params   请求参数
     * @return 返回请求结果
     */
    public static String post(String url, String params, boolean verifySSL) {
        return doPost(url, params, new HashMap<>(), verifySSL);
    }

    public static String post(String url, String params, Map<String, String> header) {
        return doPost(url, params, header, true);
    }

    public static String post(String url, String params, Map<String, String> header, boolean verifySSL) {
        return doPost(url, params, header, verifySSL);
    }

    public static String delete(String url, String params, Map<String, String> header){
        StringBuffer resultBuffer;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setHeader("Content-Type", "application/json");
        for (String key : header.keySet()) {
            httpDelete.setHeader(key, header.get(key));
        }

        try {
            CloseableHttpResponse execute = httpclient.execute(httpDelete);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "";
    }

    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }


    private static String doPost(String url, String params, Map<String, String> header, boolean verifySSL){
        StringBuffer resultBuffer;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(60 * 1000)
                .setConnectionRequestTimeout(60 * 1000)
                //设置请求和传输超时时间
                .setSocketTimeout(60 * 1000).build();
        httpPost.setConfig(requestConfig);
        // 构建请求参数
        BufferedReader br = null;
        CloseableHttpClient httpclient = null;
        try {
            if (verifySSL){
                httpclient = HttpClients.createDefault();
            }else{
                //采用绕过验证的方式处理https请求
                SSLContext sslcontext = createIgnoreVerifySSL();
                //设置协议http和https对应的处理socket链接工厂的对象
                Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.INSTANCE)
                        .register("https", new SSLConnectionSocketFactory(sslcontext, NoopHostnameVerifier.INSTANCE))
                        .build();
                PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
                HttpClients.custom().setConnectionManager(connManager);
                httpclient = HttpClients.custom().setConnectionManager(connManager).build();
            }

            StringEntity entity = new StringEntity(params, StandardCharsets.UTF_8);
            httpPost.setEntity(entity);
            for (String key : header.keySet()) {
                httpPost.setHeader(key, header.get(key));
            }
            CloseableHttpResponse response = httpclient.execute(httpPost);
            // 读取服务器响应数据
            resultBuffer = new StringBuffer();
            if (null != response.getEntity()) {
                br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
                String temp;
                while ((temp = br.readLine()) != null) {
                    resultBuffer.append(temp);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (httpclient != null){
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultBuffer.toString();
    }

}
