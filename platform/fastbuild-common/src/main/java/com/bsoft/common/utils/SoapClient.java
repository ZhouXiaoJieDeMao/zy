package com.bsoft.common.utils;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPHeaderElement;
import javax.xml.namespace.QName;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Simon Shen
 * @Create: 2019/3/7 12:04 AM
 */
public class SoapClient {

    /**
     *
     * @param method soap方法
     * @param param soap参数和值
     * @param soapaction 域名，这是在server定义的 targetNamespace
     * @param url wsdl地址
     * @param  sOAPHeaderParam head值
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public static String soapExec(String method, Map<String, String> param,String soapaction,String url,
                                  Map<String, String> sOAPHeaderParam,String HeadName) throws Exception {

        Service service = new Service();
        try {
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(url);

            //设置Head部分
            if (sOAPHeaderParam != null && sOAPHeaderParam.size() > 0) {
                SOAPHeaderElement sOAPHeaderElement =new SOAPHeaderElement(soapaction,HeadName);
//                List<String> list = new ArrayList<String>();
                for (String key : sOAPHeaderParam.keySet()) {
                    sOAPHeaderElement.addChildElement(key).setValue(sOAPHeaderParam.get(key));
                }
                call.addHeader(sOAPHeaderElement);
            }

            //设置要调用哪个方法
            call.setOperationName(new QName(soapaction, method));
            //（标准的类型）
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
            call.setUseSOAPAction(true);
            call.setSOAPActionURI(soapaction + method);
            String[] params = {};
            if (param != null && param.size() > 0) {
                List<String> list = new ArrayList<String>();
                for (String key : param.keySet()) {
                    //设置要传递的参数
                    call.addParameter(new QName(soapaction, key), org.apache.axis.encoding.XMLType.XSD_STRING,
                            javax.xml.rpc.ParameterMode.IN);
                    list.add(param.get(key));
                }
                params = list.toArray(new String[list.size()]);
            }
            //调用方法并传递参数
            return (String) call.invoke(params);
        } catch (Exception ex) {
           ex.printStackTrace();
        }
		return null;

    }


    
    public static void main(String[] args){
    	Map<String, String> param = new HashMap<String, String>();
        param.put("inStr", "<interface><org_code>470240551</org_code><start_time>2019-03-01</start_time><end_time>2019-04-12</end_time><flag>0</flag></interface>");
        param.put("methodName", "dc_check_bill");
        String newResult = null;

        Map<String, String> sOAPHeaderParam =new HashMap<String, String>();
        sOAPHeaderParam.put("orgcode","470240551");
        sOAPHeaderParam.put("password","q1w2e3r4");
        sOAPHeaderParam.put("signatures","1735363ce530c7c767b0243188b8d8414");
        try {
            newResult = (SoapClient.soapExec("CommonWebService", 
            		param, 
            		"http://tempuri.org/", 
            		"http://192.26.5.126/citybrain/HZYWebService.asmx",
            		sOAPHeaderParam,
            		"AppSoapHeader"));
       System.out.println("return2:"+newResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
