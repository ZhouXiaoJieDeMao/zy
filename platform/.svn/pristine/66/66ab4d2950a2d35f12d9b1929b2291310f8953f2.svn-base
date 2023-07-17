//package com.bsoft.system.domain.yyh;
//
//import com.google.gson.JsonObject;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.ByteArrayEntity;
//import org.apache.http.entity.ContentType;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.UnsupportedEncodingException;
//import java.security.InvalidKeyException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//@Component
//public class HttpSign {
//    private String url;
//    private String certId;
//    private String secretKey;
//    private String projectId;
//    private boolean enable;
//    public HttpSign(){}
//    public HttpSign(String url, String certId, String secretKey, String projectId, boolean enable){
//        this.url = url;
//        this.certId = certId;
//        this.secretKey = secretKey;
//        this.projectId = projectId;
//        this.enable = enable;
//    }
//    public boolean getEnable(){
//        return this.enable;
//    }
//    public String getUrl(){
//        return this.url;
//    }
//    public String getCertId(){
//        return this.certId;
//    }
//    public String getSecretKey(){
//        return this.secretKey;
//    }
//    public String getProjectId(){
//        return this.projectId;
//    }
//    /**
//     * InputStream转化为byte[]数组
//     * @param input
//     * @return
//     * @throws IOException
//     */
//    private byte[] toByteArray(InputStream input) throws IOException {
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        byte[] buffer = new byte[4096];
//        int n = 0;
//        while (-1 != (n = input.read(buffer))) {
//            output.write(buffer, 0, n);
//        }
//        return output.toByteArray();
//    }
//    private String bytesToHexString(byte[] src){
//        StringBuilder stringBuilder = new StringBuilder("");
//        if (src == null || src.length <= 0) {
//            return null;
//        }
//        for (int i = 0; i < src.length; i++) {
//            int v = src[i] & 0xFF;
//            String hv = Integer.toHexString(v);
//            if (hv.length() < 2) {
//                stringBuilder.append(0);
//            }
//            stringBuilder.append(hv);
//        }
//        return stringBuilder.toString();
//    }
//    /**
//     * 利用java原生的类实现SHA256加密
//     *
//     * @param str 加密后的报文
//     * @return
//     */
//    public String getSHA256(String str) {
//        MessageDigest messageDigest;
//        String encodestr = "";
//        try {
//            messageDigest = MessageDigest.getInstance("SHA-256");
//            messageDigest.update(str.getBytes("UTF-8"));
//            encodestr = byte2Hex(messageDigest.digest());
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return encodestr;
//    }
//
//    /**
//     * 将byte转为16进制
//     *
//     * @param bytes
//     * @return
//     */
//    private String byte2Hex(byte[] bytes) {
//        StringBuffer stringBuffer = new StringBuffer();
//        String temp = null;
//        for (int i = 0; i < bytes.length; i++) {
//            temp = Integer.toHexString(bytes[i] & 0xFF);
//            if (temp.length() == 1) {
//                // 1得到一位的进行补0操作
//                stringBuffer.append("0");
//            }
//            stringBuffer.append(temp);
//        }
//        return stringBuffer.toString();
//    }
//    public String doSign(String value){
//        String responseData = "";
//        try {
////            String hashValue = getSHA256(value);
//            JsonObject content = new JsonObject();
//            content.addProperty("certId", this.certId);
//            content.addProperty("hashAlg", "SHA256");
//            content.addProperty("hash", value);
//            System.out.println(content.toString());
//            // 2.将请求参数写入HTTP-BODY，JSON格式
//            HttpEntityEnclosingRequestBase req = (HttpEntityEnclosingRequestBase) new HttpPost(this.url);
//            ByteArrayEntity entity = new ByteArrayEntity(content.toString().getBytes("UTF-8"), ContentType.create(
//                    ContentType.APPLICATION_JSON.getMimeType(), "UTF-8"));
//            req.setEntity(entity);
//
//            // 3.对HTTP-BODY内容进行签名，HMAC-SHA256算法，签名结果转十六进制字符串
//            byte[] postBody = toByteArray(entity.getContent());
//            Mac mac = Mac.getInstance("HmacSHA256");
//            SecretKeySpec secretKey = new SecretKeySpec(this.secretKey.getBytes("UTF-8"), "HmacSHA256");
//            mac.init(secretKey);
//            byte[] sign = mac.doFinal(postBody);
//            String hexString = bytesToHexString(sign);
//            // 4.设置HTTP-HERDER
//            req.addHeader("X-timevale-project-id", this.projectId);
//            req.addHeader("X-timevale-signature", hexString);
//            // 5.发送HTTP请求，获取响应
//            DefaultHttpClient clients = new DefaultHttpClient();
//            CloseableHttpResponse result = clients.execute(req);
//            if (null != result.getEntity()) {
//                InputStream resStream = result.getEntity().getContent();
//                byte[] resByte = toByteArray(resStream);
//                responseData = new String(resByte, "UTF-8");
//            }
//            System.out.println("响应结果：" + responseData);
//        } catch (UnsupportedEncodingException | InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return responseData;
//
//    }
//
//    public static void main(String[] args) {
//        HttpSign s = new HttpSign();
//    }
//}
