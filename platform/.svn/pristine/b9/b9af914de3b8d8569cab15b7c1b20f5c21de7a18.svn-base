package com.bsoft.system.domain.yyh;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebServiceUtil {
    protected static Logger logger = LoggerFactory.getLogger(WebServiceUtil.class);
    private static Map<String,Client> clientMap = new ConcurrentHashMap<>();
    private static Client getClient(String wsdlUrl){
        if(clientMap.containsKey(wsdlUrl)){
            return clientMap.get(wsdlUrl);
        }else {
            // 创建动态客户端
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(wsdlUrl);
            HTTPConduit conduit = (HTTPConduit) client.getConduit();
            HTTPClientPolicy policy = new HTTPClientPolicy();
            long timeout = 10 * 60 * 1000;//
            policy.setConnectionTimeout(timeout);
            policy.setReceiveTimeout(timeout);
            conduit.setClient(policy);
            clientMap.put(wsdlUrl,client);
            return client;
        }
    }

//    public static String invoke(String wsdlUrl,String methodName,Object... params){
//        return invoke(wsdlUrl,null,methodName,params);
//    }

    public static String invokeUseAction(String wsdlUrl,String soapaction,String methodName,Object... params){
        // 创建动态客户端
        Client client = getClient(wsdlUrl);
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME, PASS_WORD));
        Object[] objects = new Object[0];
        try {
            if(null != soapaction){
                QName meth_qname = new QName(soapaction,methodName);
                objects = client.invoke(meth_qname, params);
            }else{
                objects = client.invoke(methodName, params);
            }
            logger.info("返回数据:" + objects[0]);
            return objects[0].toString();
        } catch (Exception e) {
            logger.error("上传失败："+e.getMessage(),e);
        }
        return null;
    }

    public static String invoke(String wsdlUrl,String methodName,Object... params){
        // 创建动态客户端
        Client client = getClient(wsdlUrl);
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME, PASS_WORD));
        Object[] objects = new Object[0];
        try {
//            if(null != soapaction){
//                QName meth_qname = new QName(soapaction,methodName);
//                objects = client.invoke(meth_qname, params);
//            }else{
                objects = client.invoke(methodName, params);
//            }
            logger.info("返回数据:" + objects[0]);
            return objects[0].toString();
        } catch (Exception e) {
            logger.error("上传失败："+e.getMessage(),e);
        }
        return null;
    }
}
