package com.bsoft.common.utils.http;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 发送https请求 工具类
 * 注意:是Https请求,若是Http,请将 HttpsURLConnection ---> HttpURLConnection
 */
public class HttpsClient {
    private static final Logger log = LoggerFactory.getLogger(HttpsClient.class);

    private String              contentType;
    private String              charset;            //编码  默认:"UTF-8"

    //[强制]在实现的HostnameVerifier子类中，需要使用verify函数效验服务器主机名的合法性，否则会导致恶意程序利用中间人攻击绕过主机名效验。
    //信任固定ip
    static {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                // ip address of the service URL(like.23.28.244.244)
                if (hostname.equals("127.0.0.1xx")) {
                    return true;
                }
                return false;
            }
        });
    }

    //通过重写TrustManager的checkClientTrusted（检查客户端证书信任）和checkServerTrusted（检查服务端证书验证）。
    //以及HostnameVerifier的verify（校验）方法即可取消对证书的所有验证。
    static HostnameVerifier _hv = new HostnameVerifier() {
        @Override
        public boolean verify(String host, SSLSession sslSession) {
            log.warn("Host===>" + host);
            log.warn("sslSession.getPeerHost====>" + sslSession.getPeerHost());
            return true;
        }
    };

    static TrustManager[] _trustCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }
    };

    ///信任证书ssl
    static {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(_hv);
            SSLContext ctxSSL = null;
            ctxSSL = SSLContext.getInstance("SSL");
            ctxSSL.init(null, _trustCerts, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(ctxSSL.getSocketFactory());
        } catch (Exception e) {
            log.error("SSL context set fail: {}", e);
        }
    }

    public HttpsClient(String contentType){
        this.contentType = contentType;
        this.charset = "UTF-8";
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    ///发送 post 请求(有返回值)
    /**************************************************************************************************************
     * @param    strHttpsUrl      目标地址
     * @param    jsonString        参数(json格式)
     * @return   String
     ***************************************************************************************************************/
    public String doPost(String strHttpsUrl, String jsonString) {
        String result = null;
        log.info("[doPost] \n POST请求地址: {} \n POST请求参数: {}", strHttpsUrl, jsonString);
        try {
            DataOutputStream    output = null;
            BufferedReader      reader = null;
            HttpsURLConnection  httpConnnect = null;
            try {
                //创建连接
                URL httpUrl = new URL(strHttpsUrl);
                httpConnnect = (HttpsURLConnection) httpUrl.openConnection();

                //设置连接属性
                httpConnnect.setDoOutput(true);                            //输出(发送数据)
                httpConnnect.setDoInput(true);                              //输入(接收数据)
                httpConnnect.setRequestMethod("POST");                       //设置请求方式
                httpConnnect.setUseCaches(false);                         //设置是否开启缓存，post请求时，缓存必须关掉
                httpConnnect.setInstanceFollowRedirects(true);      //设置连接是否自动处理重定向（setFollowRedirects：所用http连接；setInstanceFollowRedirects：本次连接）
                httpConnnect.setRequestProperty("Content-Type", contentType);   //设置提交内容类型(设置请求头)

                //开始连接
                log.info("[doPost] 开始连接");
                httpConnnect.connect();
                //发送请求
                output = new DataOutputStream(httpConnnect.getOutputStream());

                //发送
                log.info("[doPost] 发送");
                if (StringUtils.isNotBlank(jsonString)) {
                    output.write(jsonString.getBytes(charset));
                }
                output.flush();
                output.close();

                //读取响应
                log.info("[doPost] 读取");
                reader = new BufferedReader(new InputStreamReader(httpConnnect.getInputStream(), charset));
                String lines;
                StringBuffer stringBuffer = new StringBuffer("");
                while ((lines = reader.readLine()) != null) {
                    lines = new String(lines.getBytes());
                    stringBuffer.append(lines);
                }
                result = stringBuffer.toString();
                log.info("[doPost] 返回结果：" + result);
                reader.close();
                // 断开连接
                httpConnnect.disconnect();

            } catch (Exception e) {
                log.error("[doPost] 发送post 请求失败: {}", e.getMessage());
            } finally {
                if (output != null) {
                    output.flush();
                    output.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (httpConnnect != null) {
                    httpConnnect.disconnect();
                }
            }
        } catch (Exception e) {
            log.error("[doPost] 发送post 请求失败: {}", e.getMessage());
        }
        return result;
    }

    ///发送 get 请求
    /**************************************************************************************************************
     * @param    strHttpsUrl      目标地址
     * @return 无
     ***************************************************************************************************************/
    public String doGet(String strHttpsUrl) {
        String result = null;
        log.info("[doGet] GET请求地址: {} ", strHttpsUrl);
        try {
            BufferedReader      reader = null;
            HttpsURLConnection  httpConnnect = null;
            try {
                //创建连接
                URL httpUrl = new URL(strHttpsUrl);
                httpConnnect = (HttpsURLConnection) httpUrl.openConnection();
                httpConnnect.setRequestMethod("GET");                   //设置请求方式
                httpConnnect.connect();                                         //开始连接

                //读取响应
                reader = new BufferedReader(new InputStreamReader(httpConnnect.getInputStream(), charset));
                String lines;
                StringBuffer stringBuffer = new StringBuffer("");
                while ((lines = reader.readLine()) != null) {
                    lines = new String(lines.getBytes());
                    stringBuffer.append(lines);
                }
                result = stringBuffer.toString();
                log.error("[doGet] 请求返回结果：{}" , result);

                reader.close();
                httpConnnect.disconnect();

            } catch (Exception e) {
                log.error("[doGet] 发送get 请求失败: {}", e.getMessage());
            } finally {
                if (reader != null) {
                    reader.close();
                }
                if (httpConnnect != null) {
                    httpConnnect.disconnect();
                }
            }
        } catch (Exception e) {
            log.error("[doGet] 发送get 请求失败: {}", e.getMessage());
        }
        return result;
    }



}
