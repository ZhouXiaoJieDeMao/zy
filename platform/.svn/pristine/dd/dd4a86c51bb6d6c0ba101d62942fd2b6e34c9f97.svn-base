package com.bsoft.common.ws.service;

import com.bsoft.common.utils.XmlBeanUtil;
import com.bsoft.common.ws.model.basic.ReturnMessage;
import com.bsoft.common.ws.model.request.batch.BatchRequest;
import com.bsoft.common.ws.model.request.task.TaskRequest;
import com.bsoft.common.ws.model.request.upload.UploadRequest;
import com.bsoft.common.ws.model.response.WsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class ServiceCall {

    private static Logger log = LoggerFactory.getLogger(ServiceCall.class);

    /**
     * 批次声明
     */
    @Value("${wsdl.batchService.url}")
    private String batchServiceUrl;
    @Value("${wsdl.batchService.namespace}")
    private String batchServiceNamespace;
    @Value("${wsdl.batchService.serviceName}")
    private String batchServiceName;

    /**
     * 单任务声明
     */
    @Value("${wsdl.taskService.url}")
    private String taskServiceUrl;
    @Value("${wsdl.taskService.namespace}")
    private String taskServiceNamespace;
    @Value("${wsdl.taskService.serviceName}")
    private String taskServiceName;

    /**
     * 单任务声明
     */
    @Value("${wsdl.uploadService.url}")
    private String uploadServiceUrl;
    @Value("${wsdl.uploadService.namespace}")
    private String uploadServiceNamespace;
    @Value("${wsdl.uploadService.serviceName}")
    private String uploadServiceName;

    public WsResponse batchService(BatchRequest batchRequest) {
        URL url = null;
        try {
            url = new URL(batchServiceUrl);
        } catch (MalformedURLException e) {
            log.error("批次URL调用无效",e);
//            e.printStackTrace();
        }
        QName qName = new QName(batchServiceNamespace, batchServiceName);
        javax.xml.ws.Service service = javax.xml.ws.Service.create(url, qName);
        CollectDeclareService webService = service.getPort(CollectDeclareService.class);
        String result = null;
        try {
            String xml = XmlBeanUtil.convertToXml(batchRequest);
//            System.out.println(xml);
            result = webService.totalDeclare(xml);

        } catch (Exception e) {
            log.error("批次声明服务异常："+e.getMessage(),e);
//            System.out.println("start");
//            e.printStackTrace();
//            System.out.println("end");
        }
//        System.out.println("result:" + result);
        if (result == null) {
            return null;
        } else {
            WsResponse wsResponse = new WsResponse();
            try {
                wsResponse = XmlBeanUtil.converyToJavaBean(result, WsResponse.class);
                wsResponse.setXml(result);
            } catch (Exception e) {
                wsResponse = new WsResponse();
                wsResponse.setXml(result);
//                e.printStackTrace();
                log.error("返回结果处理异常："+e.getMessage(),e);
            } finally {
                return wsResponse;
            }
        }
    }

    public WsResponse taskService(TaskRequest taskRequest) {
        URL url = null;
        try {
            url = new URL(taskServiceUrl);
        } catch (MalformedURLException e) {
            log.error("批次URL调用无效",e);
//            e.printStackTrace();
        }
        QName qName = new QName(taskServiceNamespace, taskServiceName);
        javax.xml.ws.Service service = javax.xml.ws.Service.create(url, qName);
        CollectDeclareService webService = service.getPort(CollectDeclareService.class);
        String result = null;
        try {
            String xml = XmlBeanUtil.convertToXml(taskRequest);
//            System.out.println(xml);
            result = webService.singleDeclare(xml);
        } catch (Exception e) {
            log.error("任务批次申请异常："+e.getMessage(),e);
        }
        if (result == null) {
            return null;
        } else {
            WsResponse wsResponse = new WsResponse();
            try {
                wsResponse = XmlBeanUtil.converyToJavaBean(result, WsResponse.class);
                wsResponse.setXml(result);
            } catch (Exception e) {
                wsResponse = new WsResponse();
                wsResponse.setXml(result);
                log.error("返回结果异常："+e.getMessage(),e);
//                e.printStackTrace();
            } finally {
                return wsResponse;
            }
        }
    }

    public WsResponse uploadService(UploadRequest uploadRequest) {
        URL url = null;
        try {
            url = new URL(uploadServiceUrl);
        } catch (MalformedURLException e) {
            log.error("上传URL调用无效"+e.getMessage(),e);
//            e.printStackTrace();
        }
        QName qName = new QName(uploadServiceNamespace, uploadServiceName);
        javax.xml.ws.Service service = javax.xml.ws.Service.create(url, qName);
        CarryXmlToDbService webService = service.getPort(CarryXmlToDbService.class);
        String result = null;
        try {
            String xml = XmlBeanUtil.convertToXml(uploadRequest);
//            System.out.println(xml);
            result = webService.handle(xml);
        } catch (Exception e) {
            log.error("任务请求异常："+e.getMessage(),e);
//            System.out.println("start");
//            e.printStackTrace();
//            System.out.println("end");
            WsResponse wsResponse = new WsResponse();
            ReturnMessage returnMessage = wsResponse.getBusiness().getReturnMessage();
            returnMessage.setRetCode("-1");
            returnMessage.setRetText(e.getMessage());
            return wsResponse;
        }
//        System.out.println("result:" + result);
        if (result == null) {
            return null;
        } else {
            WsResponse wsResponse = new WsResponse();
            try {
                wsResponse = XmlBeanUtil.converyToJavaBean(result, WsResponse.class);
                wsResponse.setXml(result);
            } catch (Exception e) {
                wsResponse = new WsResponse();
                wsResponse.setXml(result);
//                e.printStackTrace();
                log.error("返回结果异常："+e.getMessage(),e);
            } finally {
                return wsResponse;
            }
        }
    }
}
