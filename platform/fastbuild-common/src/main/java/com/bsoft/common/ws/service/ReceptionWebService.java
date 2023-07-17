//package com.bsoft.common.ws.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.jws.WebMethod;
//import javax.jws.WebParam;
//
//@Service
//@javax.jws.WebService(targetNamespace = "http://service.ws.hcn.bsoft.com.cn/", serviceName = "gatherResultReceptionService")
//public class ReceptionWebService {
//
//    @Autowired
//    GatherResultService gatherResultService;
//
//    @WebMethod
//    public String extraction(@WebParam(name = "params") String params) {
//        String xml = gatherResultService.reception(params);
//        return xml;
//    }
//
//}
