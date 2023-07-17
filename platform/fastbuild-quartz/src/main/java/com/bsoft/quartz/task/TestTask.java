package com.bsoft.quartz.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.bsoft.common.utils.StringUtils;
import com.bsoft.common.utils.http.HttpUtils;
import com.bsoft.common.utils.http.HttpsClient;
import com.bsoft.common.utils.sign.RsaUtil;
import com.bsoft.quartz.service.ISysJobService;
import com.bsoft.quartz.service.MyTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("testTask")
@Slf4j
public class TestTask {

    @Autowired
    MyTaskService myService;

    @Value("${quartz.clrcDownload.url}")
    String clrcDownloadUrl;
    @Value("${quartz.clrcDownload.merchant}")
    String merchant;
    @Value("${quartz.clrcDownload.privateKey}")
    String privateKey;
    @Value("${quartz.clrcDownload.publicKey}")
    String publicKey;

    public void testMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void testParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void testNoParams()
    {
        System.out.println("执行无参方法");
    }

    /**
     * C类人才身份证信息下载
     */
    public void clrcDownload() throws Exception {
        if(StringUtils.isNotBlank(clrcDownloadUrl)){
            Map<String,String> map = new LinkedHashMap<>();
            map.put("merchant",merchant);
            String dateTime = DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss");
            map.put("time", dateTime);
            String sign = RsaUtil.sign("merchant="+merchant+"&time="+dateTime,privateKey);
            map.put("sign",sign);

            String requestData = JSONUtil.toJsonStr(map);
            log.info("C类人才下载请求入参requestData："+requestData);
            HttpsClient client = new HttpsClient("application/json;charset=UTF-8");
            String result = client.doPost(clrcDownloadUrl,requestData);
//            String result = HttpUtils.postDateForJson(clrcDownloadUrl,requestData);
            log.info("C类人才下载请求出参result："+result);
            Map<String,String> stringMap = JSONUtil.toBean(result,Map.class);
            if("PY0000".equals(stringMap.get("code"))){
                // code = PY0000 成功
                String resuleData = RsaUtil.decryptByPrivateKey(stringMap.get("response"),privateKey);
                log.info("C类人才下载解密后数据："+result);
                JSONArray sfzhs = JSONUtil.parseArray(resuleData);
                Object [] sfzhArr =  sfzhs.toArray();
                List<Map<String,Object>> list = new ArrayList<>();
                for(int i=0;i<sfzhArr.length;i++){
                    Map<String,Object> objectMap = new HashMap<>();
                    objectMap.put("sfzh",sfzhArr[i]+"");
                    list.add(objectMap);
                }
                myService.saveClrcjbxx(list);
            }
        }
    }
}
