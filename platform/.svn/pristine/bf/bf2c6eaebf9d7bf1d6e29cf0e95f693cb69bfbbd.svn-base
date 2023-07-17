package com.bsoft.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

public class HZSYYHUtil {
	/***
	 * 拼装报文信息
	 * 必要字段！！！
	 * sourceorgan（用于存储服务消费方所在机构编码）  
	 * sourcedomain（用于存储服务消费方所使用的接入系 统编码） 
	 * condition(条件) 
	 * businessdata（用于装载服务提供方所要求的入参业务数据）
	 * @param paramsMessage
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getPublicMessage(Map<String, Object> paramsMessage) throws Exception{
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> message = new HashMap<String, Object>();
		//Map<String, Object> heartbeat = new HashMap<String, Object>();
		if(paramsMessage.get("paramsMessage")!=null){
			message.put("heartbeat", paramsMessage.get("paramsMessage"));
		}else{
			message.put("heartbeat", 0);
		}
		Map<String, Object> switchset = new HashMap<String, Object>();
		Map<String, Object> authority = new HashMap<String, Object>();
		if(paramsMessage.get("authoritytype")!=null){
			authority.put("authoritytype", paramsMessage.get("authoritytype"));
		}else{
			authority.put("authoritytype", "0");
		}
		//用于存储服务提供方要求的用户名， 
		if(paramsMessage.get("username")!=null){
			authority.put("username", paramsMessage.get("username"));
		}else{
			authority.put("username", "");
		}
		//密码
		if(paramsMessage.get("userpwd")!=null){
			authority.put("userpwd", paramsMessage.get("userpwd"));
		}else{
			authority.put("userpwd", "");
		}
		// 用于存储服务提供方要求的授权码
		if(paramsMessage.get("license")!=null){
			authority.put("license", paramsMessage.get("license"));
		}else{
			authority.put("license", "");
		}
		Map<String, Object> visitor = new HashMap<String, Object>();// 用于存储服务消费方信息
		if(paramsMessage.get("sourceorgan")==null){
			throw new Exception("sourceorgan字段不能为空");
		}
		visitor.put("sourceorgan", paramsMessage.get("sourceorgan"));//用于存储服务消费方所在机构编码
		if(paramsMessage.get("sourcedomain")==null){
			throw new Exception("sourcedomain字段不能为空");
		}
		visitor.put("sourcedomain", paramsMessage.get("sourcedomain"));//用于存储服务消费方所使用的接入系 统编码
		
		Map<String, Object> serviceinf = new HashMap<String, Object>();//用于存储请求的服务信息
		if(paramsMessage.get("servicecode")==null){
			throw new Exception("servicecode字段不能为空");
		}
		serviceinf.put("servicecode", paramsMessage.get("servicecode"));//用于存储请求的服务在服务注册中心 的唯一服务编码
		
		Map<String, Object> provider = new HashMap<String, Object>();//用于存储服务提供方的信息
		//用于存储服务提供方所在的机构编码
		if(paramsMessage.get("targetorgan")!=null){
			provider.put("targetorgan", paramsMessage.get("targetorgan"));
		}else{
			provider.put("targetorgan", "");
		}
		//用于存储服务提供方所在的接入系统
		if(paramsMessage.get("targetdomain")!=null){
			provider.put("targetdomain", paramsMessage.get("targetdomain"));
		}else{
			provider.put("targetdomain", "");
		}
		
		switchset.put("authority", authority);
		switchset.put("visitor", visitor);
		switchset.put("provider", provider);
		switchset.put("serviceinf", serviceinf);
		//用于记录服务路由信息
		if(paramsMessage.get("route")!=null){
			switchset.put("route", paramsMessage.get("route"));
		}else{
			switchset.put("route", "");//
		}
		//用于记录服务流程编排相关信息
		if(paramsMessage.get("process")!=null){
			switchset.put("process", paramsMessage.get("process"));
		}else{
			switchset.put("process", "");//
		}
		
		Map<String, Object> business = new HashMap<String, Object>();//用于装载服务提供者需要的入参数据
		if(paramsMessage.get("standardcode")!=null){
			business.put("standardcode", paramsMessage.get("standardcode"));
		}else{
			business.put("standardcode", "");//
		}
		Map<String, Object> requestset = new HashMap<String, Object>();//用于定义数据请求时数据查询条件和返回数据的分页设置参数 
		Map<String, Object> reqcondition = new HashMap<String, Object>();//用于记录用于数据查询的条件
		//单条数据查询条件
		if(paramsMessage.get("condition")!=null){
			//throw new Exception("condition字段不能为空");
			reqcondition.put("condition", paramsMessage.get("condition"));
		}else{
			reqcondition.put("condition", "");
		}
		requestset.put("reqcondition", reqcondition);
		//用于定义返回数据时是否进行分页处 
		if(paramsMessage.get("reqpaging")!=null){
			requestset.put("reqpaging", paramsMessage.get("reqpaging"));
		}else{
			requestset.put("reqpaging", 0);//
		}
		// 用于定义返回分页数据时返回的页索 
		if(paramsMessage.get("reqpageindex")!=null){
			requestset.put("reqpageindex", paramsMessage.get("reqpageindex"));
		}else{
			requestset.put("reqpageindex", -1);//
		}
		// 用于定义返回分页数据时返回的页索 
		if(paramsMessage.get("reqpageset")!=null){
			requestset.put("reqpageset", paramsMessage.get("reqpageset"));
		}else{
			requestset.put("reqpageset", 0);//
		}
		
		
		Map<String,Object> datacompress = new HashMap<String, Object>();
		// 用于定义服务提供方要求的入参业务 
		if(paramsMessage.get("datacompress")!=null){
			business.put("datacompress", paramsMessage.get("datacompress"));
		}else{
			business.put("datacompress", 0);//
		}
		Map<String,Object> daqtaskid = new HashMap<String, Object>();
		// 用于定义服务提供方要求的入参业务 
		if(paramsMessage.get("daqtaskid")!=null){
			business.put("daqtaskid", paramsMessage.get("daqtaskid"));
		}else{
			business.put("daqtaskid", "");//
		}
		Map<String, Object> datasets = new HashMap<String, Object>();
		datasets.put("setcode", "");
		datasets.put("settype", "");
		datasets.put("setdetails", paramsMessage.get("setdetails"));
		Map<String, Object> businessdata = new HashMap<String, Object>();
		businessdata.put("datasets", datasets);
		/*if(paramsMessage.get("businessdata")==null){
			throw new Exception("businessdata字段不能为空");
		}*/
		business.put("requestset", requestset);
		//business.put("datacompress", datacompress);
		//business.put("daqtaskid", daqtaskid);
		business.put("businessdata", businessdata);
		
		//message.put("heartbeat", heartbeat);
		message.put("switchset", switchset);
		message.put("business", business);
		message.put("extendset", "");
		return message;
	}
	/***
	 * 拼装报文信息
	 * 必要字段！！！
	 * sourceorgan（用于存储服务消费方所在机构编码）  
	 * sourcedomain（用于存储服务消费方所使用的接入系 统编码） 
	 * condition(条件) 
	 * businessdata（用于装载服务提供方所要求的入参业务数据）
	 * @param paramsMessage
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getPublicMessageCrb(Map<String, Object> paramsMessage) throws Exception{
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> message = new HashMap<String, Object>();
		//Map<String, Object> heartbeat = new HashMap<String, Object>();
		if(paramsMessage.get("paramsMessage")!=null){
			message.put("heartbeat", paramsMessage.get("paramsMessage"));
		}else{
			message.put("heartbeat", 0);
		}
		Map<String, Object> switchset = new HashMap<String, Object>();
		Map<String, Object> authority = new HashMap<String, Object>();
		if(paramsMessage.get("authoritytype")!=null){
			authority.put("authoritytype", paramsMessage.get("authoritytype"));
		}else{
			authority.put("authoritytype", "0");
		}
		//用于存储服务提供方要求的用户名， 
		if(paramsMessage.get("username")!=null){
			authority.put("username", paramsMessage.get("username"));
		}else{
			authority.put("username", "");
		}
		//密码
		if(paramsMessage.get("userpwd")!=null){
			authority.put("userpwd", paramsMessage.get("userpwd"));
		}else{
			authority.put("userpwd", "");
		}
		// 用于存储服务提供方要求的授权码
		if(paramsMessage.get("license")!=null){
			authority.put("license", paramsMessage.get("license"));
		}else{
			authority.put("license", "");
		}
		Map<String, Object> visitor = new HashMap<String, Object>();// 用于存储服务消费方信息
		if(paramsMessage.get("sourceorgan")==null){
			throw new Exception("sourceorgan字段不能为空");
		}
		visitor.put("sourceorgan", paramsMessage.get("sourceorgan"));//用于存储服务消费方所在机构编码
		if(paramsMessage.get("sourcedomain")==null){
			throw new Exception("sourcedomain字段不能为空");
		}
		visitor.put("sourcedomain", paramsMessage.get("sourcedomain"));//用于存储服务消费方所使用的接入系 统编码
		
		Map<String, Object> serviceinf = new HashMap<String, Object>();//用于存储请求的服务信息
		if(paramsMessage.get("servicecode")==null){
			throw new Exception("servicecode字段不能为空");
		}
		serviceinf.put("servicecode", paramsMessage.get("servicecode"));//用于存储请求的服务在服务注册中心 的唯一服务编码
		
		Map<String, Object> provider = new HashMap<String, Object>();//用于存储服务提供方的信息
		//用于存储服务提供方所在的机构编码
		if(paramsMessage.get("targetorgan")!=null){
			provider.put("targetorgan", paramsMessage.get("targetorgan"));
		}else{
			provider.put("targetorgan", "");
		}
		//用于存储服务提供方所在的接入系统
		if(paramsMessage.get("targetdomain")!=null){
			provider.put("targetdomain", paramsMessage.get("targetdomain"));
		}else{
			provider.put("targetdomain", "");
		}
		
		switchset.put("authority", authority);
		switchset.put("visitor", visitor);
		switchset.put("provider", provider);
		switchset.put("serviceinf", serviceinf);
		//用于记录服务路由信息
		if(paramsMessage.get("route")!=null){
			switchset.put("route", paramsMessage.get("route"));
		}else{
			switchset.put("route", "");//
		}
		//用于记录服务流程编排相关信息
		if(paramsMessage.get("process")!=null){
			switchset.put("process", paramsMessage.get("process"));
		}else{
			switchset.put("process", "");//
		}
		
		Map<String, Object> business = new HashMap<String, Object>();//用于装载服务提供者需要的入参数据
		if(paramsMessage.get("standardcode")!=null){
			business.put("standardcode", paramsMessage.get("standardcode"));
		}else{
			business.put("standardcode", "");//
		}
		Map<String, Object> requestset = new HashMap<String, Object>();//用于定义数据请求时数据查询条件和返回数据的分页设置参数 
		Map<String, Object> reqcondition = new HashMap<String, Object>();//用于记录用于数据查询的条件
		//单条数据查询条件
		if(paramsMessage.get("condition")!=null){
			//throw new Exception("condition字段不能为空");
			reqcondition.put("condition", paramsMessage.get("condition"));
		}else{
			reqcondition.put("condition", "");
		}
		requestset.put("reqcondition", reqcondition);
		//用于定义返回数据时是否进行分页处 
		if(paramsMessage.get("reqpaging")!=null){
			requestset.put("reqpaging", paramsMessage.get("reqpaging"));
		}else{
			requestset.put("reqpaging", 0);//
		}
		// 用于定义返回分页数据时返回的页索 
		if(paramsMessage.get("reqpageindex")!=null){
			requestset.put("reqpageindex", paramsMessage.get("reqpageindex"));
		}else{
			requestset.put("reqpageindex", -1);//
		}
		// 用于定义返回分页数据时返回的页索 
		if(paramsMessage.get("reqpageset")!=null){
			requestset.put("reqpageset", paramsMessage.get("reqpageset"));
		}else{
			requestset.put("reqpageset", 0);//
		}
		
		
		Map<String,Object> datacompress = new HashMap<String, Object>();
		// 用于定义服务提供方要求的入参业务 
		if(paramsMessage.get("datacompress")!=null){
			business.put("datacompress", paramsMessage.get("datacompress"));
		}else{
			business.put("datacompress", 0);//
		}
		Map<String,Object> daqtaskid = new HashMap<String, Object>();
		// 用于定义服务提供方要求的入参业务 
		if(paramsMessage.get("daqtaskid")!=null){
			business.put("daqtaskid", paramsMessage.get("daqtaskid"));
		}else{
			business.put("daqtaskid", "");//
		}
		List list=new ArrayList();
		
		
		
		Map<String, Object> datasets = new HashMap<String, Object>();
		datasets.put("setcode", "B0302.0701.01");
		datasets.put("settype", "");
		datasets.put("setdetails", paramsMessage.get("setdetails"));
		Map<String, Object> businessdata = new HashMap<String, Object>();
		list.add(datasets);
		if(paramsMessage.get("setdetails1")!=null){
			Map<String, Object> datasets1 = new HashMap<String, Object>();
			datasets1.put("setcode", "B0302.0701.02");
			datasets1.put("settype", "");
			datasets1.put("setdetails", paramsMessage.get("setdetails1"));
			list.add(datasets1);
		}
		if(paramsMessage.get("setdetails2")!=null){
			Map<String, Object> datasets2 = new HashMap<String, Object>();
			datasets2.put("setcode", "B0302.0701.03");
			datasets2.put("settype", "");
			datasets2.put("setdetails", paramsMessage.get("setdetails2"));
			list.add(datasets2);
		} 
		if(paramsMessage.get("setdetails3")!=null){
				Map<String, Object> datasets3 = new HashMap<String, Object>();
				datasets3.put("setcode", "B0302.0701.04");
				datasets3.put("settype", "");
				datasets3.put("setdetails", paramsMessage.get("setdetails3"));
				list.add(datasets3);
			}
		businessdata.put("datasets", list);
		business.put("requestset", requestset);
		business.put("businessdata", businessdata);
		message.put("switchset", switchset);
		message.put("business", business);
		message.put("extendset", "");
		return message;
	}
	
	/***
	 * 出参报文拼接
	 * 必传参数： sourceorgan（用于存储服务消费方所在机构编码）
	 * sourcedomain  用于存储服务消费方所使用的接入系统编码
	 * servicecode  服务编码
	 * setdetails 出参方法list
	 * @param paramsMap
	 * @return
	 */
	public static Map<String, Object> getOutMessage(Map<String, Object> paramsMap) throws Exception{
		Map<String, Object> outData = new HashMap<String, Object>();
		Map<String, Object> message = new HashMap<String, Object>();
		Map<String, Object> switchset = new HashMap<String, Object>();
		Map<String, Object> visitor = new HashMap<String, Object>();
		Map<String, Object> serviceinf = new HashMap<String, Object>();
		Map<String, Object> provider = new HashMap<String, Object>();
		Map<String, Object> switchmessage = new HashMap<String, Object>();
		Map<String, Object> business = new HashMap<String, Object>();
		Map<String, Object> returnmessage = new HashMap<String, Object>();
		Map<String, Object>returnset = new HashMap<String, Object>();
		visitor.put("sourceorgan", "3301050000000000000000");//用于存储服务消费方所在机构编码
		visitor.put("sourcedomain","NRHPT00001");//用于存储服务消费方所使用的接入系统编码
		if(paramsMap.get("sourceorgan")!=null){
			visitor.put("sourceorgan", paramsMap.get("sourceorgan"));
//			throw new Exception("sourceorgan字段不能为空");
		}
		if(paramsMap.get("sourcedomain")!=null){
			visitor.put("sourcedomain", paramsMap.get("sourcedomain"));
//			throw new Exception("sourceorgan字段不能为空");
		}
		
		
		if(paramsMap.get("servicecode")==null){
			throw new Exception("servicecode字段不能为空");
		}
		serviceinf.put("servicecode", paramsMap.get("servicecode"));//用于存储请求的服务在服务注册中心 
		
		//用于存储服务提供方所在的机构编
		if(paramsMap.get("targetorgan")!=null){
			provider.put("targetorgan", paramsMap.get("targetorgan"));
		}else{
			provider.put("targetorgan", "");
		}
		//用于存储服务提供方所在的机构编
		if(paramsMap.get("targetdomain")!=null){
			provider.put("targetdomain", paramsMap.get("targetdomain"));
		}else{
			provider.put("targetdomain", "");
		}
		
		//用于装载交换消息的编码  -1失败
		if(paramsMap.get("messagecode")!=null){
			switchmessage.put("messagecode", paramsMap.get("messagecode"));
		}else{
			switchmessage.put("messagecode", "");
		}
		//用于装载交换消息的文本  1.1.2.1.3 执行异常代码
		if(paramsMap.get("messagetext")!=null){
			switchmessage.put("messagetext", paramsMap.get("messagetext"));
		}else{
			switchmessage.put("messagetext", "");
		}
		
		switchset.put("visitor", visitor);
		switchset.put("serviceinf", serviceinf);
		switchset.put("provider", provider);
		switchset.put("route", "");
		switchset.put("process", "");
		switchset.put("switchmessage", switchmessage);
		
		//入参数据标准管理系统中的编码 
		if(paramsMap.get("standardcode")!=null){
			business.put("standardcode", paramsMap.get("standardcode"));
		}else{
			business.put("standardcode", "A00010101");
		}
		//用于装载服务提供方返回的业务相关  失败-1 
		if(paramsMap.get("retcode")!=null){
			returnmessage.put("retcode", paramsMap.get("retcode"));
		}else{
			returnmessage.put("retcode", "");
		}
		//具体的错误信息 代码请参照  1.1.2.1.3 执行异常代码 
		if(paramsMap.get("rettext")!=null){
			returnmessage.put("rettext", paramsMap.get("rettext"));
		}else{
			returnmessage.put("rettext", "");
		}
		
//		总量信息
		if(paramsMap.get("rettotal")!=null){
			returnset.put("rettotal", paramsMap.get("rettotal"));
		}else{
			returnset.put("rettotal", 1);
		}
//		是否分页
		if(paramsMap.get("retpaging")!=null){
			returnset.put("retpaging", paramsMap.get("retpaging"));
		}else{
			returnset.put("retpaging", 0);
		}
//		returnset.put("rettotal", 1);//总量信息
//		returnset.put("retpaging", 0);//是否分页
		returnset.put("retpageindex", -1);//页索引 默认-1
		returnset.put("retpageset", 0);//页容纳的数据行数 默认0
		
		
		business.put("returnmessage", returnmessage);
		business.put("returnset", returnset);
		business.put("datacompress", 0);//数据是否进行压缩 默认0
//		if(paramsMap.get("businessdata")==null){
//			throw new Exception("businessdata 节点不能为空");
//		}
//		if(paramsMap.get("setdetails")==null ){
//			throw new Exception("setdetails 节点不能为空");
//		}
		Map<String, Object> businessdata = new HashMap<String, Object>();
		if("20012008".equals(paramsMap.get("servicecode")+"")){
			businessdata.put("datasets",paramsMap.get("datasets") );
		}else{
			Map<String, Object> datasets = new HashMap<String, Object>();
			datasets.put("setcode", "");
			datasets.put("settype", "");
			datasets.put("setdetails", paramsMap.get("setdetails"));
			if(paramsMap.get("dmp")!=null && !"null".equals(paramsMap.get("dmp"))){
				Map<String, Object> dmp = new HashMap<String, Object>();
				dmp.put("datasets", datasets);
				businessdata.put("dmp", dmp);
			}else{
				businessdata.put("datasets", datasets);
			}
		}
		
		
		
		business.put("businessdata", businessdata);
		message.put("switchset", switchset);
		message.put("business",business);
		message.put("extendset", "");
//		outData.put("message", message);
//		outData.put("extendset", "");
		return message;
	}


	/***
	 * 出参报文拼接
	 * 必传参数： sourceorgan（用于存储服务消费方所在机构编码）
	 * sourcedomain  用于存储服务消费方所使用的接入系统编码
	 * servicecode  服务编码
	 * 只包含businessdata节点
	 * @param paramsMap
	 * @return
	 */
	public static Map<String, Object> getOutBusMessage(Map<String, Object> paramsMap) throws Exception{
		Map<String, Object> outData = new HashMap<String, Object>();
		Map<String, Object> message = new HashMap<String, Object>();
		Map<String, Object> switchset = new HashMap<String, Object>();
		Map<String, Object> visitor = new HashMap<String, Object>();
		Map<String, Object> serviceinf = new HashMap<String, Object>();
		Map<String, Object> provider = new HashMap<String, Object>();
		Map<String, Object> switchmessage = new HashMap<String, Object>();
		Map<String, Object> business = new HashMap<String, Object>();
		Map<String, Object> returnmessage = new HashMap<String, Object>();
		Map<String, Object>returnset = new HashMap<String, Object>();
		visitor.put("sourceorgan", "3301050000000000000000");//用于存储服务消费方所在机构编码
		visitor.put("sourcedomain","NRHPT00001");//用于存储服务消费方所使用的接入系统编码
		if(paramsMap.get("sourceorgan")!=null){
			visitor.put("sourceorgan", paramsMap.get("sourceorgan"));
//			throw new Exception("sourceorgan字段不能为空");
		}
		if(paramsMap.get("sourcedomain")!=null){
			visitor.put("sourcedomain", paramsMap.get("sourcedomain"));
//			throw new Exception("sourceorgan字段不能为空");
		}
		
		
		if(paramsMap.get("servicecode")==null){
			throw new Exception("servicecode字段不能为空");
		}
		serviceinf.put("servicecode", paramsMap.get("servicecode"));//用于存储请求的服务在服务注册中心 
		
		//用于存储服务提供方所在的机构编
		if(paramsMap.get("targetorgan")!=null){
			provider.put("targetorgan", paramsMap.get("targetorgan"));
		}else{
			provider.put("targetorgan", "");
		}
		//用于存储服务提供方所在的机构编
		if(paramsMap.get("targetdomain")!=null){
			provider.put("targetdomain", paramsMap.get("targetdomain"));
		}else{
			provider.put("targetdomain", "");
		}
		
		//用于装载交换消息的编码  -1失败
		if(paramsMap.get("messagecode")!=null){
			switchmessage.put("messagecode", paramsMap.get("messagecode"));
		}else{
			switchmessage.put("messagecode", "");
		}
		//用于装载交换消息的文本  1.1.2.1.3 执行异常代码
		if(paramsMap.get("messagetext")!=null){
			switchmessage.put("messagetext", paramsMap.get("messagetext"));
		}else{
			switchmessage.put("messagetext", "");
		}
		
		switchset.put("visitor", visitor);
		switchset.put("serviceinf", serviceinf);
		switchset.put("provider", provider);
		switchset.put("route", "");
		switchset.put("process", "");
		switchset.put("switchmessage", switchmessage);
		
		//入参数据标准管理系统中的编码 
		if(paramsMap.get("standardcode")!=null){
			business.put("standardcode", paramsMap.get("standardcode"));
		}else{
			business.put("standardcode", "A00010101");
		}
		//用于装载服务提供方返回的业务相关  失败-1 
		if(paramsMap.get("retcode")!=null){
			returnmessage.put("retcode", paramsMap.get("retcode"));
		}else{
			returnmessage.put("retcode", "");
		}
		//具体的错误信息 代码请参照  1.1.2.1.3 执行异常代码 
		if(paramsMap.get("rettext")!=null){
			returnmessage.put("rettext", paramsMap.get("rettext"));
		}else{
			returnmessage.put("rettext", "");
		}
		
//		总量信息
		if(paramsMap.get("rettotal")!=null){
			returnset.put("rettotal", paramsMap.get("rettotal"));
		}else{
			returnset.put("rettotal", 1);
		}
//		是否分页
		if(paramsMap.get("retpaging")!=null){
			returnset.put("retpaging", paramsMap.get("retpaging"));
		}else{
			returnset.put("retpaging", 0);
		}
//		returnset.put("rettotal", 1);//总量信息
//		returnset.put("retpaging", 0);//是否分页
		returnset.put("retpageindex", -1);//页索引 默认-1
		returnset.put("retpageset", 0);//页容纳的数据行数 默认0
		
		
		business.put("returnmessage", returnmessage);
		business.put("returnset", returnset);
		business.put("datacompress", 0);//数据是否进行压缩 默认0
//		if(paramsMap.get("businessdata")==null){
//			throw new Exception("businessdata 节点不能为空");
//		}
		/*if(paramsMap.get("setdetails")==null ){
			throw new Exception("setdetails 节点不能为空");
		}*/
		/*Map<String, Object> datasets = new HashMap<String, Object>();
		datasets.put("setcode", "");
		datasets.put("settype", "");
		datasets.put("setdetails", paramsMap.get("setdetails"));
		businessdata.put("datasets", datasets);*/
		Map<String, Object> businessdata = new HashMap<String, Object>();
		business.put("businessdata", businessdata);
		message.put("switchset", switchset);
		message.put("business",business);
		message.put("extendset", "");
//		outData.put("message", message);
//		outData.put("extendset", "");
		return message;
	}
	
	/***
	 * 根据入参获取条件
	 * @param paramsMap
	 * @return
	 */
	public static Map<String, Object> getCodition(Map<String, Object> paramsMap){
		Map<String, Object> reqData = (Map<String, Object>) ((Map<String, Object>) paramsMap.get("body")).get("messages");
		Map<String, Object> business = (Map<String, Object>) reqData.get("business");
		Map<String, Object> requestset = (Map<String, Object>) business.get("requestset");
		//条件
		Map<String, Object> codition =(Map<String, Object>) ((Map<String, Object>) requestset.get("reqcondition")).get("condition");
		codition.put("reqpaging", requestset.get("reqpaging"));//用于定义返回数据时是否进行分页处 default 0
		codition.put("reqpageindex", requestset.get("reqpageindex"));//用于定义返回分页数据时返回的索引  default -1
		codition.put("reqpageset", requestset.get("reqpageset"));// 用于定义返回分页数据时页的数据行 数 default 0
		return codition;
	}
	
	/***
	 * 根据入参获取条件
	 * @param paramsMap
	 * @return
	 */
	public static Map<String, Object> getCoditionCrb(Map<String, Object> paramsMap){
		Map<String, Object> reqData = (Map<String, Object>) ((Map<String, Object>) paramsMap.get("body")).get("messages");
		Map<String, Object> business = (Map<String, Object>) reqData.get("business");
		Map<String, Object> businessdata = (Map<String, Object>) business.get("businessdata");
		Map<String, Object> requestset = (Map<String, Object>) businessdata.get("datasets");

		//条件
		Map<String, Object> codition =(Map<String, Object>) ((Map<String, Object>) requestset.get("setdetails"));
		/*codition.put("reqpaging", requestset.get("reqpaging"));//用于定义返回数据时是否进行分页处 default 0
		codition.put("reqpageindex", requestset.get("reqpageindex"));//用于定义返回分页数据时返回的索引  default -1
		codition.put("reqpageset", requestset.get("reqpageset"));// 用于定义返回分页数据时页的数据行 数 default 0
*/		return codition;
	}
	
	/***
	 * 根据入参获取Details
	 * @param paramsMap
	 * @return
	 */
	public static Map<String, Object> getDetails(Map<String, Object> paramsMap){
		Map<String, Object> reqData = (Map<String, Object>) ((Map<String, Object>) paramsMap.get("body")).get("messages");
		Map<String, Object> business = (Map<String, Object>) reqData.get("business");
		Map<String, Object> businessdata =  (Map<String, Object>) business.get("businessdata");
		Map<String, Object> setdetails=(Map<String, Object>)((Map<String, Object>)businessdata.get("datasets")).get("setdetails");
		return setdetails;
	}
	
	/**
	 * 根据入参获取Details 可能是list 也可能是Map
	 * @param paramsMap
	 * @return
	 * @author yuzg
	 * @time 2020-3-5下午12:45:54
	 * @description
	 */
	public static Object getListDetails(Map<String, Object> paramsMap){
		Map<String, Object> business = (Map<String, Object>) paramsMap.get("business");
		Map<String, Object> businessdata =  (Map<String, Object>) business.get("businessdata");
		Object setdetails= businessdata.get("datasets");
		return setdetails;
	}
	/***
	 * 拼接错误出参报文信息
	 * @param paramsMap
	 * @return
	 */
	public static Map<String, Object> getErrorOutMesg(Map<String, Object> paramsMap)
	throws Exception{
		Map<String, Object> messages = new HashMap<String, Object>();
		Map<String, Object> outData = new HashMap<String, Object>();
		Map<String, Object> message = new HashMap<String, Object>();
		Map<String, Object> switchset = new HashMap<String, Object>();
		Map<String, Object> visitor = new HashMap<String, Object>();
		Map<String, Object> serviceinf = new HashMap<String, Object>();
		Map<String, Object> provider = new HashMap<String, Object>();
		Map<String, Object> switchmessage = new HashMap<String, Object>();
		Map<String, Object> business = new HashMap<String, Object>();
		Map<String, Object> returnmessage = new HashMap<String, Object>();
		Map<String, Object>returnset = new HashMap<String, Object>();
		visitor.put("sourceorgan", "3301050000000000000000");//用于存储服务消费方所在机构编码
		visitor.put("sourcedomain","NRHPT00001");//用于存储服务消费方所使用的接入系统编码
		if(paramsMap.get("sourceorgan")!=null){
			visitor.put("sourceorgan", paramsMap.get("sourceorgan"));
//			throw new Exception("sourceorgan字段不能为空");
		}
		if(paramsMap.get("sourcedomain")!=null){
			visitor.put("sourcedomain", paramsMap.get("sourcedomain"));
//			throw new Exception("sourceorgan字段不能为空");
		}
		
		
		if(paramsMap.get("servicecode")==null){
			throw new Exception("servicecode字段不能为空");
		}
		serviceinf.put("servicecode", paramsMap.get("servicecode"));//用于存储请求的服务在服务注册中心 
		
		//用于存储服务提供方所在的机构编
		if(paramsMap.get("targetorgan")!=null){
			provider.put("targetorgan", paramsMap.get("targetorgan"));
		}else{
			provider.put("targetorgan", "");
		}
		//用于存储服务提供方所在的机构编
		if(paramsMap.get("targetdomain")!=null){
			provider.put("targetdomain", paramsMap.get("targetdomain"));
		}else{
			provider.put("targetdomain", "");
		}
		
		//用于装载交换消息的编码  -1失败
		if(paramsMap.get("messagecode")!=null){
			switchmessage.put("messagecode", paramsMap.get("messagecode"));
		}else{
			switchmessage.put("messagecode", "");
		}
		//用于装载交换消息的文本  1.1.2.1.3 执行异常代码
		if(paramsMap.get("messagetext")!=null){
			switchmessage.put("messagetext", paramsMap.get("messagetext"));
		}else{
			switchmessage.put("messagetext", "");
		}
		
		switchset.put("visitor", visitor);
		switchset.put("serviceinf", serviceinf);
		switchset.put("provider", provider);
		switchset.put("route", "");
		switchset.put("process", "");
		switchset.put("switchmessage", switchmessage);
		
		//入参数据标准管理系统中的编码 
		if(paramsMap.get("standardcode")!=null){
			business.put("standardcode", paramsMap.get("standardcode"));
		}else{
			business.put("standardcode", "A00010101");
		}
		//用于装载服务提供方返回的业务相关  失败-1 
		if(paramsMap.get("retcode")!=null){
			returnmessage.put("retcode", paramsMap.get("retcode"));
		}else{
			returnmessage.put("retcode", "");
		}
		//具体的错误信息 代码请参照  1.1.2.1.3 执行异常代码 
		if(paramsMap.get("rettext")!=null){
			returnmessage.put("rettext", paramsMap.get("rettext"));
		}else{
			returnmessage.put("rettext", "");
		}
		
//		总量信息
		if(paramsMap.get("rettotal")!=null){
			returnset.put("rettotal", paramsMap.get("rettotal"));
		}else{
			returnset.put("rettotal", 1);
		}
//		是否分页
		if(paramsMap.get("retpaging")!=null){
			returnset.put("retpaging", paramsMap.get("retpaging"));
		}else{
			returnset.put("retpaging", 0);
		}
//		returnset.put("rettotal", 1);//总量信息
//		returnset.put("retpaging", 0);//是否分页
		returnset.put("retpageindex", -1);//页索引 默认-1
		returnset.put("retpageset", 0);//页容纳的数据行数 默认0
		
		
		business.put("returnmessage", returnmessage);
		business.put("returnset", returnset);
		business.put("datacompress", 0);//数据是否进行压缩 默认0
//		if(paramsMap.get("businessdata")==null){
//			throw new Exception("businessdata 节点不能为空");
//		}
//		if(paramsMap.get("setdetails")==null ){
//			throw new Exception("setdetails 节点不能为空");
//		}
		Map<String, Object> datasets = new HashMap<String, Object>();
		datasets.put("setcode", "");
		datasets.put("settype", "");
		Map<String, Object> businessdata = new HashMap<String, Object>();
		businessdata.put("datasets", datasets);
		businessdata.put("setdetails", " ");
		
		business.put("businessdata", businessdata);
		message.put("switchset", switchset);
		message.put("business",business);
		message.put("extendset", "");
//		outData.put("message", message);
//		outData.put("extendset", "");
		return message;
	}
	public  static void main (String[] args){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("sourceorgan", "121");
		paramsMap.put("sourcedomain", "221");
		paramsMap.put("servicecode", "1112");
		Map<String, Object> businessdata = new HashMap<String, Object>();
		Map<String, Object> datasets = new HashMap<String, Object>();
		datasets.put("setcode", "");
		datasets.put("settype", "");
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for(int i=0;i<10;i++){
//			Map<String, Object> obj = new HashMap<String, Object>();
			
			Map<String, Object> setdetails = new HashMap<String, Object>();
			setdetails.put("WS08_10_052_01", "H0001");
			setdetails.put("CT08_10_052_01", "杭州市XXXXX 医院"+i);
//			obj.put("setdetails", setdetails);
			list.add(setdetails);
		}
//		businessdata.put("datasets", datasets);
//		businessdata.put("setdetails", list);
//		paramsMap.put("businessdata", businessdata);
		paramsMap.put("setdetails", list);
		try {
//			Map<String, Object> outDataMap= HZSYYHUtil.getOutMessage(paramsMap);
//			String outDataXml = XMLUtil.mapParseXML(outDataMap, "message");
//			System.out.println(outDataXml);
			
			Map<String, Object> body = new HashMap<String, Object>();
			body.put("standardcode", "REQ.E0302.0101.002");
			body.put("servicecode", paramsMap.get("servicecode"));
			body.put("setdetails", "");
			body.put("retcode", "-1");
			body.put("rettext", "BUS01001");
			Map<String, Object> outMap = HZSYYHUtil.getOutMessage(body);
			String outXml =XMLUtil.mapParseXML(outMap, "messages");
		System.out.println(outXml);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			throw new hz
			e.printStackTrace();
		}
	}
	/***
	 * xmlToMap
	 * @return
	 */
	public static Map<String, Object> xmlToListMap (String xmlData){
		Element root = null;
		try {
			Document doc = DocumentHelper.parseText(xmlData);
			root = doc.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Map<String, Object> resMap =(Map<String, Object>) xml2map(root);
		return resMap;
	}
	public  static Object xml2map(Element element) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    List<Element> elements = element.elements();
	    if (elements.size() == 0) {
	      map.put(element.getName(), element.getText());
	      if (!element.isRootElement()) {
	        return element.getText();
	      }
	    } else if (elements.size() == 1) {
	      map.put(elements.get(0).getName(), xml2map(elements.get(0)));
	    } else if (elements.size() > 1) {
	      // 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
	      // 构造一个map用来去重
	      Map<String, Element> tempMap = new HashMap<String, Element>();
	      for (Element ele : elements) {
	        tempMap.put(ele.getName(), ele);
	      }
	      Set<String> keySet = tempMap.keySet();
	      for (String string : keySet) {
	        Namespace namespace = tempMap.get(string).getNamespace();
	        List<Element> elements2 = element.elements(new QName(string, namespace));
	        // 如果同名的数目大于1则表示要构建list
	        if (elements2.size() > 1) {
	          List<Object> list = new ArrayList<Object>();
	          for (Element ele : elements2) {
	            list.add(xml2map(ele));
	          }
	          map.put(string, list);
	        } else {
	          // 同名的数量不大于1则直接递归去
	          map.put(string, xml2map(elements2.get(0)));
	        }
	      }
	    }

	    return map;
	  }
	/**
	 * 
	 * @param paramsMap
	 * @return
	 */
	public static List<Map<String, Object>> getListDetail(Map<String, Object> paramsMap){
		Map<String, Object> reqData =  ((Map<String, Object>) paramsMap.get("body"));
		Map<String, Object> business = (Map<String, Object>) reqData.get("business");
		Map<String, Object> businessdata =  (Map<String, Object>) business.get("businessdata");
		Map<String, Object> datasets = (Map<String, Object>)businessdata.get("datasets");
		List<Map<String, Object>> setdetails=(List<Map<String, Object>>) datasets.get("setdetails");
		return setdetails;
	}
	
	/**
	 * 体检PDF上传报文组装
	 * @param paramsMessage
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getPublicMessageTjPDF(Map<String, Object> paramsMessage) throws Exception{
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> message = new HashMap<String, Object>();
		//Map<String, Object> heartbeat = new HashMap<String, Object>();
		if(paramsMessage.get("paramsMessage")!=null){
			message.put("heartbeat", paramsMessage.get("paramsMessage"));
		}else{
			message.put("heartbeat", 0);
		}
		Map<String, Object> switchset = new HashMap<String, Object>();
		Map<String, Object> authority = new HashMap<String, Object>();
		if(paramsMessage.get("authoritytype")!=null){
			authority.put("authoritytype", paramsMessage.get("authoritytype"));
		}else{
			authority.put("authoritytype", "0");
		}
		//用于存储服务提供方要求的用户名， 
		if(paramsMessage.get("username")!=null){
			authority.put("username", paramsMessage.get("username"));
		}else{
			authority.put("username", "");
		}
		//密码
		if(paramsMessage.get("userpwd")!=null){
			authority.put("userpwd", paramsMessage.get("userpwd"));
		}else{
			authority.put("userpwd", "");
		}
		// 用于存储服务提供方要求的授权码
		if(paramsMessage.get("license")!=null){
			authority.put("license", paramsMessage.get("license"));
		}else{
			authority.put("license", "");
		}
		Map<String, Object> visitor = new HashMap<String, Object>();// 用于存储服务消费方信息
		if(paramsMessage.get("sourceorgan")==null && paramsMessage.get("SOURCEORGAN")==null){
			throw new Exception("sourceorgan字段不能为空");
		}
		visitor.put("sourceorgan", paramsMessage.get("sourceorgan")==null?paramsMessage.get("SOURCEORGAN"):paramsMessage.get("sourceorgan"));//用于存储服务消费方所在机构编码
		if(paramsMessage.get("sourcedomain")==null && paramsMessage.get("SOURCEDOMAIN")==null){
			throw new Exception("sourcedomain字段不能为空");
		}
		visitor.put("sourcedomain", paramsMessage.get("sourcedomain")==null?paramsMessage.get("SOURCEDOMAIN"):paramsMessage.get("sourcedomain"));//用于存储服务消费方所使用的接入系 统编码
		
		Map<String, Object> serviceinf = new HashMap<String, Object>();//用于存储请求的服务信息
		if(paramsMessage.get("servicecode")==null && paramsMessage.get("SERVICECODE")==null){
			throw new Exception("servicecode字段不能为空");
		}
		serviceinf.put("servicecode", paramsMessage.get("servicecode")==null? paramsMessage.get("SERVICECODE"):paramsMessage.get("servicecode"));//用于存储请求的服务在服务注册中心 的唯一服务编码
		
		Map<String, Object> provider = new HashMap<String, Object>();//用于存储服务提供方的信息
		//用于存储服务提供方所在的机构编码
		if(paramsMessage.get("targetorgan")!=null){
			provider.put("targetorgan", paramsMessage.get("targetorgan"));
		}else{
			provider.put("targetorgan", paramsMessage.get("TARGETORGAN")==null?"":paramsMessage.get("TARGETORGAN"));
		}
		//用于存储服务提供方所在的接入系统
		if(paramsMessage.get("targetdomain")!=null){
			provider.put("targetdomain", paramsMessage.get("targetdomain"));
		}else{
			provider.put("targetdomain", paramsMessage.get("TARGETDOMAIN")==null?"":paramsMessage.get("TARGETDOMAIN"));
		}
		
		switchset.put("authority", authority);
		switchset.put("visitor", visitor);
		switchset.put("provider", provider);
		switchset.put("serviceinf", serviceinf);
		//用于记录服务路由信息
		if(paramsMessage.get("route")!=null){
			switchset.put("route", paramsMessage.get("route"));
		}else{
			switchset.put("route", "");//
		}
		//用于记录服务流程编排相关信息
		if(paramsMessage.get("process")!=null){
			switchset.put("process", paramsMessage.get("process"));
		}else{
			switchset.put("process", "");//
		}
		
		Map<String, Object> business = new HashMap<String, Object>();//用于装载服务提供者需要的入参数据
		if(paramsMessage.get("standardcode")!=null){
			business.put("standardcode", paramsMessage.get("standardcode"));
		}else{
			business.put("standardcode", "");//
		}
		Map<String, Object> requestset = new HashMap<String, Object>();//用于定义数据请求时数据查询条件和返回数据的分页设置参数 
		Map<String, Object> reqcondition = new HashMap<String, Object>();//用于记录用于数据查询的条件
		//单条数据查询条件
		if(paramsMessage.get("condition")!=null){
			//throw new Exception("condition字段不能为空");
			reqcondition.put("condition", paramsMessage.get("condition"));
		}else{
			reqcondition.put("condition", "");
		}
		requestset.put("reqcondition", reqcondition);
		//用于定义返回数据时是否进行分页处 
		if(paramsMessage.get("reqpaging")!=null){
			requestset.put("reqpaging", paramsMessage.get("reqpaging"));
		}else{
			requestset.put("reqpaging", 0);//
		}
		// 用于定义返回分页数据时返回的页索 
		if(paramsMessage.get("reqpageindex")!=null){
			requestset.put("reqpageindex", paramsMessage.get("reqpageindex"));
		}else{
			requestset.put("reqpageindex", -1);//
		}
		// 用于定义返回分页数据时返回的页索 
		if(paramsMessage.get("reqpageset")!=null){
			requestset.put("reqpageset", paramsMessage.get("reqpageset"));
		}else{
			requestset.put("reqpageset", 0);//
		}
		
		
		Map<String,Object> datacompress = new HashMap<String, Object>();
		// 用于定义服务提供方要求的入参业务 
		if(paramsMessage.get("datacompress")!=null){
			business.put("datacompress", paramsMessage.get("datacompress"));
		}else{
			business.put("datacompress", 0);//
		}
		Map<String,Object> daqtaskid = new HashMap<String, Object>();
		// 用于定义服务提供方要求的入参业务 
		if(paramsMessage.get("daqtaskid")!=null){
			business.put("daqtaskid", paramsMessage.get("daqtaskid"));
		}else{
			business.put("daqtaskid", "");//
		}
		Map<String, Object> datasets = new HashMap<String, Object>();
		datasets.put("setcode", "");
		datasets.put("settype", "");
		datasets.put("setdetails", paramsMessage.get("setdetails"));
		datasets.put("base64", paramsMessage.get("base64")); //存储pdf文件的base64编码
		Map<String, Object> businessdata = new HashMap<String, Object>();
		businessdata.put("datasets", datasets);
		/*if(paramsMessage.get("businessdata")==null){
			throw new Exception("businessdata字段不能为空");
		}*/
		business.put("requestset", requestset);
		//business.put("datacompress", datacompress);
		//business.put("daqtaskid", daqtaskid);
		business.put("businessdata", businessdata);
		
		//message.put("heartbeat", heartbeat);
		message.put("switchset", switchset);
		message.put("business", business);
		message.put("extendset", "");
		return message;
	}
}
