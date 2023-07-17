package com.bsoft.common.utils.drgs;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * 国家医保工具
 */
@Slf4j(topic = "gjyb")
public class GjybUtils {

    /**
     * 国算加密SM3
     *
     * @param content
     * @param secret
     * @return
     */
    public static String encrytSHA256(String content, String secret) {
        try {
            SM3Digest digest = new SM3Digest();
            byte[] key = secret.getBytes("UTF-8");
            byte[] srcData = content.getBytes("UTF-8");
            KeyParameter keyParameter = new KeyParameter(key);
            HMac mac = new HMac(digest);
            mac.init(keyParameter);
            mac.update(srcData, 0, srcData.length);
            byte[] result = new byte[mac.getMacSize()];
            mac.doFinal(result, 0);
            return new HexBinaryAdapter().marshal(result).toUpperCase();
        } catch (Exception e) {
            log.error("加密异常：" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 基础json报文封装
     * @param funId 功能号
     * @param jgid 机构编号
     * @param jgmc 机构名称
     * @param signno 签到流水号
     * @param mdtrtarea 就医地医保区划，杭州：330100
     * @param insuplc 参保地医保区划，杭州：330100
     * @param version 版本号：V0.1
     * @param hm_input input报文内容
     * @return
     */
    public static String createJsonReq(String funId, String jgid, String jgmc,String opter, String signno, String mdtrtarea, String insuplc, String version, Map<String, Object> hm_input) {
        JSONObject jo_req = new JSONObject();
        jo_req.put("infno", funId);
        //修改：后四位不用随机数，用顺序号
        String ls_msgid = jgid + DateUtil.format(new Date(),"yyyyMMddHHmmss") + NumUtil.getOrderNum();//RandomUtil.randomNumbers(4);

        jo_req.put("msgid", (ls_msgid.length()>30)?ls_msgid.substring(0,30):ls_msgid);
        jo_req.put("mdtrtarea_admvs", mdtrtarea);//就医地规则：如果参保地是杭州本地330100，其他的都是省本级339900
        jo_req.put("insuplc_admdvs", insuplc);//参保地：用自身携带的
        jo_req.put("recer_sys_code", "FSI_LOCAL");
        jo_req.put("dev_no", "gjyb-drg");
        jo_req.put("dev_safe_info", "1");
        jo_req.put("cainfo", "1");
        jo_req.put("signtype", "SM3");
        jo_req.put("infver", version);
        jo_req.put("opter_type", "1");
        jo_req.put("opter", opter);
        jo_req.put("opter_name", "上传服务");

        jo_req.put("inf_time", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        jo_req.put("fixmedins_code", jgid);
        jo_req.put("fixmedins_name", jgmc);
        jo_req.put("sign_no", signno);
        jo_req.put("input", hm_input);
        return jo_req.toJSONString();
    }

    /**
     * 返回报文解析
     * @param resJson
     * @return
     */
    public static JSONObject readResJson(String resJson){
        JSONObject jo_res =  JSON.parseObject(resJson);
        return jo_res;
    }

    /**
     *
     * @Description: 解析json返回
     */
    public static String sentServiceJson(String url,String downInput,String key,String secret) {
        String ls_uuid = IdUtil.simpleUUID();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        long currentTimeMillis = System.currentTimeMillis();
        String contentStr = currentTimeMillis + "\n" + downInput;
        String SHA256Str = currentTimeMillis + ":" + encrytSHA256(contentStr, secret);
        httppost.setHeader("x-ca-key",key);
        httppost.setHeader("x-ca-signature", SHA256Str);
        log.info(ls_uuid + "\t验签报文 ------------- " + contentStr);
        log.info(ls_uuid + "\t验签信息 ------------- " + SHA256Str);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000).build();
        httppost.setConfig(requestConfig);
        ByteArrayEntity entity = new ByteArrayEntity(downInput.getBytes(StandardCharsets.UTF_8));
        //此处应为：json/plain
        entity.setContentType("text/plain");
        httppost.setEntity(entity);
        CloseableHttpResponse response = null;
        String result = null;
        try {
            response = httpclient.execute(httppost);
            HttpEntity responseEntity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                result = EntityUtils.toString(responseEntity, "UTF-8");
                httppost.abort();
                log.error(ls_uuid + "\t调用返回失败，返回信息：-------------"+result);
//                throw new RuntimeException("HttpClient,error status code :" + statusCode + ",ERROR MSG: " + result);
            }
            if (responseEntity != null) {
                // 返回字符串
                result = EntityUtils.toString(responseEntity, "UTF-8");
                log.info(ls_uuid + "\t医保返回 ------------- " + result);
            }
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(ls_uuid + "\t提交给服务器的请求，不符合HTTP协议", e);
        } catch (IOException e) {
            throw new RuntimeException(ls_uuid + "\t向服务器承保接口发起http请求,执行post请求异常", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error(ls_uuid + "\t返回关闭异常："+e.getMessage(),e);
                    throw new RuntimeException("response IOException: ", e);
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    log.error(ls_uuid + "\t客户端关闭异常："+e.getMessage(),e);
                    throw new RuntimeException("httpclient IOException: ", e);
                }
            }
        }
        return result;
    }

    public static void sentService(String url,String downInput,String filePath,String key,String secret) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        long currentTimeMillis = System.currentTimeMillis();
        String contentStr = currentTimeMillis + "\n" + downInput;
        String SHA256Str = currentTimeMillis + ":" + encrytSHA256(contentStr, secret);
        httppost.setHeader("x-ca-key",key);
        httppost.setHeader("x-ca-signature", SHA256Str);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000).build();
        httppost.setConfig(requestConfig);
        ByteArrayEntity entity = new ByteArrayEntity(downInput.getBytes(StandardCharsets.UTF_8));
        entity.setContentType("text/plain");
        httppost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
            HttpEntity responseEntity = response.getEntity();
            String result;
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                result = EntityUtils.toString(responseEntity, "UTF-8");
                httppost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode + ",ERROR MSG: " + result);
            }
            if (responseEntity != null) {
                if (responseEntity.getContentType().getValue().contains("application/octet-stream")) {
                    InputStream content = responseEntity.getContent();
                    // 返回文件流
                    File file = new File(filePath);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    int temp;
                    while ((temp = content.read()) != -1) {
                        fileOutputStream.write(temp);
                    }
                    fileOutputStream.close();
                } else {
                    // 返回字符串
                    result = EntityUtils.toString(responseEntity, "UTF-8");
                    throw new RuntimeException("返回非文件流, 字符串: " + result);
                }
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            throw new RuntimeException("向服务器承保接口发起http请求,执行post请求异常", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    throw new RuntimeException("response IOException: ", e);
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    throw new RuntimeException("httpclient IOException: ", e);
                }
            }
        }
    }

}