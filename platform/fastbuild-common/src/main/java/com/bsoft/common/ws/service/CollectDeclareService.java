package com.bsoft.common.ws.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "CollectDeclareService", targetNamespace = "http://collectdeclareservice.webservice.entrance.si.neusoft.com/")
public interface CollectDeclareService {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     * @throws Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "singleDeclare", targetNamespace = "http://collectdeclareservice.webservice.entrance.si.neusoft.com/", className = "com.neusoft.si.entrance.webservice.collectdeclareservice.SingleDeclare")
    @ResponseWrapper(localName = "singleDeclareResponse", targetNamespace = "http://collectdeclareservice.webservice.entrance.si.neusoft.com/", className = "com.neusoft.si.entrance.webservice.collectdeclareservice.SingleDeclareResponse")
    public String singleDeclare(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0)
        throws Exception
    ;

    /**
     *
     * @param arg0
     * @return
     *     returns java.lang.String
     * @throws Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "afterDeclare", targetNamespace = "http://collectdeclareservice.webservice.entrance.si.neusoft.com/", className = "com.neusoft.si.entrance.webservice.collectdeclareservice.AfterDeclare")
    @ResponseWrapper(localName = "afterDeclareResponse", targetNamespace = "http://collectdeclareservice.webservice.entrance.si.neusoft.com/", className = "com.neusoft.si.entrance.webservice.collectdeclareservice.AfterDeclareResponse")
    public String afterDeclare(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0)
        throws Exception
    ;

    /**
     *
     * @param arg0
     * @return
     *     returns java.lang.String
     * @throws Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "totalDeclare", targetNamespace = "http://collectdeclareservice.webservice.entrance.si.neusoft.com/", className = "com.neusoft.si.entrance.webservice.collectdeclareservice.TotalDeclare")
    @ResponseWrapper(localName = "totalDeclareResponse", targetNamespace = "http://collectdeclareservice.webservice.entrance.si.neusoft.com/", className = "com.neusoft.si.entrance.webservice.collectdeclareservice.TotalDeclareResponse")
    public String totalDeclare(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0)
        throws Exception
    ;

}