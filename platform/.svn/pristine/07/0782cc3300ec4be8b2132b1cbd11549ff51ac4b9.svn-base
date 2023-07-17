package com.bsoft.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.GZIPOutputStream;

import com.bsoft.common.ws.service.CarryXmlToDbService;
import com.bsoft.common.ws.service.CollectDeclareService;
import com.bsoft.common.ws.service.ServiceImplToService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;

@Component
public class YyhbwjxUtil {
	protected static Logger logger = LoggerFactory.getLogger(YyhbwjxUtil.class);

    public static final String LINAN_SYSTEM_CODE = "3301000039";
    public static final String LINAN_ORGAN_CODE = "00250841433018511P4001";
    public static final String BATCH_CODE = "XBSJCJCJL:PCRWHHQL";
    public static final String TASK_CODE = "XBSJCJCJL:HQDCRWHL";
    public static final String UPLOAD_CODE = "XBSJCJCJL:SJSCL";

	//医疗机构注册服务
	public static final String  SCJGWSDL= "/NRHPB00010/YLJGZCFW?wsdl";
	//医疗机构修改服务
	public static final String  XGJGWSDL= "/NRHPB00010/YLJGXGFW?wsdl";
	//科室注册服务
	public static final String  KSZCFW= "/NRHPB00010/KSZCFW?wsdl";
	//科室修改服务
	public static final String  KSXGFW= "/NRHPB00010/KSXGFW?wsdl";
	//医护人员注册服务
	public static final String  YHRYZCFW= "/NRHPB00010/YHRYZCFW?wsdl";
	//医护人员修改服务
	public static final String  YHRYXGFW= "/NRHPB00010/YHRYXGFW?wsdl";
	//上传医生停诊信息
	public static final String  TZTZFW= "/NRHPA00017/TZTZFW?wsdl";
	//上传医生删除停诊信息
	public static final String  QXTZFW= "/NRHPA00017/QXTZFW?wsdl";
	//机构更新号源服务
	public static final String  GXHYFW= "/NRHPB00012/GXHYFW?wsdl";

	//传染病上报
	//public static final String  CRBSBFW= "/NRHPA00033/CRBXXSB?wsdl";

	//传染病上报正式
	public static final String  CRBSBFW= "/CRBSBZSFW/CRBXXSBZS?wsdl";

	//处方上传服务
	public static final String  CFSCFW= "/MBCCFXMJK/CFSCFW?wsdl";

	//药品目录查询服务
	public static final String  YPMLCXFW= "/MBCCFXMJK/YPMLCXFW?wsdl";

	//供应商目录查询服务
	public static final String  GYSMLCXFW= "/MBCCFXMJK/GYSMLCXFW?wsdl";

	//取消处方服务
	public static final String  QXCFFW= "/MBCCFXMJK/QXCFFW?wsdl";
	//获取供应商供应商品信息服务
	public static final String  HQGYSGYSPXXFW= "/MBCCFXMJK/HQGYSGYSPXXFW?wsdl";

	//处方信息查询服务
	public static final String  CFXXCXFW= "/MBCCFXMJK/CFXXCXFW?wsdl";


	//处方信息查询服务
	public static final String  DZXXTJFW= "/MBCCFXMJK/DZXXTJFW?wsdl";
	/***
	 * 医养护测试
	 */
	public static final String YYHDZCSIP = "http://192.46.32.161:8885";
	/***
	 * 医养护正式
	 */
	public static final String YYHDZZSIP = "http://192.46.32.161:8889";

	//个人基本信息查询服务
	public static final String GRJBXXCXFW ="/3301000024/GRJBXXCXFW?wsdl";
	//慢病信息查询服务
	public static final String MBXXCXFW ="/3301000024/MBXXCXFW?wsdl";
	//慢病数据上传
	public static final String MBSJSC ="/GSQSJSC?wsdl";
	public static final String ZJSQSJKMJBXXCXFW="/ZJSQSJKMJBXXCXJK/ZJSQSJKMJBXXCXFW?wsdl";
	/**
	 * 新冠疫苗接种信息按区查询服务
	 */
	public static final String XGYMJZXXAQCX = "/NRHPA00017/HZSMYGHSJCXJK/XGYMJZXXAQCX";
	/**
	 * 新冠疫苗接种信息按区查询服务
	 */
	public static final String HZSXXRQCXFW = "/NRHPA00017/HZSXXRQCXFWJK/HZSXXRQCXFW";

	/**
	 * 上报患者诊查费减免记录服务
	 */
	public static final String SBHZZCFJMJL = "/ZJSWCXXRYZXXJSJK/SBHZZCFJMJL";

	/**
	 * 3.1.非结构化数据上传服务  ?wsdl可能不需要　　待测试
	 */
	public static final String FJGHSCFWJK = "/FJGHSCFWJK";

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
     * 上传服务
     */
    @Value("${wsdl.uploadService.url}")
    public String uploadServiceUrl;
    @Value("${wsdl.uploadService.namespace}")
    public String uploadServiceNamespace;
    @Value("${wsdl.uploadService.serviceName}")
    public String uploadServiceName;

    /**
     * 作废服务
     */
    @Value("${wsdl.zfService.zfServiceCode}")
    public String zfServiceCode;
    @Value("${wsdl.zfService.url}")
    public String zffwUrl;
    @Value("${wsdl.zfService.serviceName}")
    public String zfServiceName;
    @Value("${wsdl.zfService.namespace}")
    public String zfNamespace;

	/**
	 * 将医养护报文解析messagecode，messagecode=-1表示flase  其他为true  add  bck
	 */
	public static boolean  checkBw(String xml) {
		boolean flag=true;
		String  newXml=xml.substring(xml.indexOf("<switchmessage>"), xml.indexOf("</switchset>"));
		Map<String,Object> getSptMsgMap=XMLUtil.xmlParseMap(newXml);
		List<Map<String,Object>> data=(List<Map<String,Object>>) getSptMsgMap.get("switchmessage");
		String messagecode=data.get(0).get("messagecode").toString();
		String  newXml1=xml.substring(xml.indexOf("<returnmessage>"), xml.indexOf("<returnset>"));
		Map<String,Object> getSptMsgMap1=XMLUtil.xmlParseMap(newXml1);
		List<Map<String,Object>> data1=(List<Map<String,Object>>) getSptMsgMap1.get("returnmessage");
		String retcode=data1.get(0).get("retcode").toString();
		if("-1".equals(messagecode)||"-1".equals(retcode)){
			logger.error(data.get(1).get("messagetext")==null?"":data.get(1).get("messagetext").toString());
			logger.error(data1.get(1).get("rettext")==null?"":data1.get(1).get("rettext").toString());
			flag=false;
		}
		return flag;
	}

	/**
	 * xml所需要的一些必要参数
	 */
	public static Map<String, Object>   getCsInfo() {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("sourceorgan", "3301050000000000000000");
		paramsMap.put("sourcedomain", "3301000012");
		paramsMap.put("targetorgan", "3301000000000000000000");
		paramsMap.put("targetdomain", "NRHPB00010");
		return paramsMap;
	}
	/**
	 * xml所需要的一些必要参数
	 */
	public  static Map<String, Object>   getCRInfo() {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("sourceorgan", "3301050000000000000000");
		paramsMap.put("sourcedomain", "3301000012");
		paramsMap.put("targetorgan", "3301000000000000000000");
		paramsMap.put("targetdomain", "NRHPA00033");
		return paramsMap;
	}

	/**
	 * xml云药房必要参数
	 */
	public static Map<String, Object>   getYyfInfo() {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("sourceorgan", "3301050000000000000000");
		paramsMap.put("sourcedomain", "3301000012");
		paramsMap.put("targetdomain", "3301000035");
		return paramsMap;
	}
	/**
	 * xml所需要的一些必要参数
	 */
	public static Map<String, Object>   getCsTZInfo() {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("sourceorgan", "3301050000000000000000");
		paramsMap.put("sourcedomain", "3301000012");
		paramsMap.put("targetorgan", "3301000000000000000000");
		paramsMap.put("targetdomain", "NRHPA00017");
		return paramsMap;
	}
	/**
	 * xml所需要的一些必要参数
	 */
	public static Map<String, Object>   getCsCXInfo() {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("sourceorgan", "3301050000000000000000");
		paramsMap.put("sourcedomain", "3301000012");
		paramsMap.put("targetorgan", "3301000000000000000000");
		paramsMap.put("targetdomain", "NRHPA00012");
		return paramsMap;
	}

	/**
	 * 滨江新冠查询基本参数xml所需要的一些必要参数
	 */
	public static Map<String, Object>   getBjXgCsCXInfo() {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("sourceorgan", "3301080000000000000000");
		paramsMap.put("sourcedomain", "3301000016");
		paramsMap.put("targetorgan", "3301000000000000000000");
		paramsMap.put("targetdomain", "NRHPB00010");
		return paramsMap;
	}


	/**
	 * 更新号源服务
	 * @param list
	 * @param YYHDZIP
	 */
	public static boolean doUploadGxhy(List<Map<String,Object>> list,String YYHDZIP){
		boolean flag=true;
		Map<String, Object> params= new HashMap<String, Object>();
		Map<String, Object> paramsMap = YyhbwjxUtil.getCsCXInfo();
		paramsMap.put("servicecode", "MZHYGL:GXHYFW");
		paramsMap.put("standardcode", "REQ.E0101.0102.001");
		Map<String, Object> businessdata = new HashMap<String, Object>();
		Map<String, Object> datasets = new HashMap<String, Object>();
		datasets.put("setcode", "");
		datasets.put("settype", "");
		paramsMap.put("setdetails", list);
		Map<String, Object> outDataMap;
		try {
			outDataMap = HZSYYHUtil.getPublicMessage(paramsMap);
			String outDataXml = XMLUtil.mapParseXML(outDataMap, "messages");
			logger.error("医养护更新号源入参："+outDataXml);
			Map<String,String> inputMap = new HashMap();
			inputMap.put("xmlData", outDataXml);
			String	resStr = SoapClient.soapExec("resourceMethod",inputMap,"http://webservice.rsm.neusoft.com/",YYHDZIP+YyhbwjxUtil.GXHYFW, null,null);
			logger.error("医养护更新号源出参："+resStr);
			if(YyhbwjxUtil.checkBw(resStr)){
			return flag;
		}else{
			flag=false;
			return flag;
		}

	} catch (Exception e) {
		e.printStackTrace();
	}
		return flag;
	}

    /**
     * 获取taskid
     * @param xml
     * @return
     */
    public String getTaskId(String xml){
        String ls_node_name = "<taskid>";
        String newXml=xml.substring(xml.indexOf(ls_node_name)+ls_node_name.length(), xml.indexOf("</taskid>"));
        return newXml;

    }

    public String batchService(Map<String,Object> params,String license) {
        URL url = null;
        try {
            url = new URL(batchServiceUrl);
        } catch (MalformedURLException e) {
            logger.error("批次URL调用无效",e);
        }
        QName qName = new QName(batchServiceNamespace, batchServiceName);
        javax.xml.ws.Service service = javax.xml.ws.Service.create(url, qName);
        CollectDeclareService webService = service.getPort(CollectDeclareService.class);
        String result = null;
        try {
            String xml = createBatchXml(params,license);
            result = webService.totalDeclare(xml);
        } catch (Exception e) {
            logger.error("批次声明服务异常："+e.getMessage(),e);
        }
        return result;
    }

    public String taskService(Map<String,Object> params,String license) {
        URL url = null;
        try {
            url = new URL(taskServiceUrl);
        } catch (MalformedURLException e) {
            logger.error("批次URL调用无效",e);
        }
        QName qName = new QName(taskServiceNamespace, taskServiceName);
        javax.xml.ws.Service service = javax.xml.ws.Service.create(url, qName);
        CollectDeclareService webService = service.getPort(CollectDeclareService.class);
        String result = null;
        try {
            String xml = createSingleDeclare(params,license);
            result = webService.singleDeclare(xml);
        } catch (Exception e) {
            logger.error("任务批次申请异常："+e.getMessage(),e);
        }
        return result;
    }

    public String uploadService(Map<String,Object> params,HttpSign httpSign,String license,Map<String,String> serviceParams) {
        URL url = null;
        String result = null;
        try {
            String serviceUrl = (String) serviceParams.get("url");
            if(StringUtils.isNotBlank(serviceUrl)){
                url = new URL(serviceUrl);
            }else{
                logger.error("任务请求url地址空：");
                return result;
            }
        } catch (MalformedURLException e) {
            logger.error("上传URL调用无效"+e.getMessage(),e);
        }
        String serviceNamespace = serviceParams.get("namespace");
        String serviceName = serviceParams.get("serviceName");
        QName qName = new QName(serviceNamespace, serviceName);
        javax.xml.ws.Service service = javax.xml.ws.Service.create(url, qName);
        CarryXmlToDbService webService = service.getPort(CarryXmlToDbService.class);
        try {
            String UPLOAD_CODE = (String) serviceParams.get("servicecode");
            String xml = createUploadXml(params,httpSign,license,UPLOAD_CODE);
            result = webService.handle(xml);
        } catch (Exception e) {
            logger.error("任务请求异常："+e.getMessage(),e);

        }
        return result;
    }

    public String zfService(Map<String,Object> params,HttpSign httpSign,String license,Map<String,String> serviceParams) {
        URL url = null;
        String result = null;
        try {
            String serviceUrl = (String) serviceParams.get("url");
            if(StringUtils.isNotBlank(serviceUrl)){
                url = new URL(serviceUrl);
            }else{
                logger.error("任务请求url地址空：");
                return result;
            }
        } catch (MalformedURLException e) {
            logger.error("上传URL调用无效"+e.getMessage(),e);
        }
        String serviceNamespace = serviceParams.get("namespace");
        String serviceName = serviceParams.get("serviceName");
        QName qName = new QName(serviceNamespace, serviceName);
        javax.xml.ws.Service service = javax.xml.ws.Service.create(url, qName);
        ServiceImplToService webService = service.getPort(ServiceImplToService.class);
        try {
            String UPLOAD_CODE = (String) serviceParams.get("servicecode");
            String xml = createZfXml(params,httpSign,license,UPLOAD_CODE);
            result = webService.resourceMethod(xml);
        } catch (Exception e) {
            logger.error("任务请求异常："+e.getMessage(),e);

        }
        return result;
    }

    private String createBatchXml(Map<String,Object> params,String license){
        String xml = "";
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<messages xmlns=\"http://www.neusoft.com/hit/rhin\">\n" +
                "<heartbeat/>" +
                "<switchset> <authority> <authoritytype/>\n" +
                "<username/>\n" +
                "<userpwd/>\n";
        if(null == license){
            xml +="<license/>\n";
        }else{
            xml +="<license>"+license+"</license>";
        }
        xml += "</authority> <visitor> ";
        xml += "<sourceorgan>"+LINAN_ORGAN_CODE+"</sourceorgan> ";
        xml += "<sourcedomain>"+LINAN_SYSTEM_CODE+"</sourcedomain> ";
        xml += "</visitor> <serviceinf> <servicecode>"+BATCH_CODE+"</servicecode>\n" +
                "</serviceinf> <provider> <targetorgan/>\n" +
                "<targetdomain/>\n" +
                "</provider> <route/>\n" +
                "<process/>\n" +
                "</switchset> <business> <standardcode/>\n" +
                "<requestset> <reqcondition> <condition/>\n" +
                "</reqcondition> <reqpaging/>\n" +
                "<reqpageindex/>\n" +
                "<reqpageset/>\n" +
                "</requestset> <datacompress/>\n" +
                //批次声明服务，批次号设置为17位时间
                "<daqtaskid>"+sf.format(new Date())+"</daqtaskid> <businessdata>\n" +
                "<declaretype>0</declaretype>\n"+
                " <collecttype>0</collecttype>\n";
        if(params.containsKey("totaldeclare")){
            if(params.get("totaldeclare").getClass().isAssignableFrom(ArrayList.class)){
                List<Map<String, Object>> totaldeclares = (List<Map<String, Object>> ) params.get("totaldeclare");
                for(Map<String,Object> map:totaldeclares){
                    xml += XMLUtil.mapParseXML(map,"totaldeclare");
                }
            }else {
                xml += XMLUtil.mapParseXML((Map<String, Object>) params.get("totaldeclare"),"totaldeclare");
            }
        }
        xml+=	"</businessdata>\n" +
                "</business> <extendset/>\n" +
                "</messages>";
        logger.info("批次服务声明入参："+xml);
        return xml;
    }

    private String createUploadXml(Map<String,Object> params,HttpSign httpSign,String license,String serciecode) throws IOException {
        String xml = "";
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //数据采集上传
        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<messages xmlns=\"http://www.neusoft.com/hit/rhin\">\n" +
                "<heartbeat/>\n" +
                "<switchset> <authority> <authoritytype/>\n" +
                "<username/>\n" +
                "<userpwd/>\n";
        if(null == license){
            xml +="<license/>\n";
        }else{
            xml +="<license>"+license+"</license>";
        }
        xml += "</authority> <visitor> ";
        xml += "<sourceorgan>"+LINAN_ORGAN_CODE+"</sourceorgan> ";
        xml += "<sourcedomain>"+LINAN_SYSTEM_CODE+"</sourcedomain> ";
        xml+=		"</visitor> <serviceinf> <servicecode>"+serciecode+"</servicecode>\n" +
                "</serviceinf> <provider> <targetorgan/>\n" +
                "<targetdomain/>\n" +
                "</provider> <route/>\n" +
                "<process/>" +
                "</switchset> <business>\n" ;
        if(!params.containsKey("standardcode") || "".equals((params.get("standardcode")+"").trim())){
            logger.error("standardcode不能为空");
            return null;
        }
        xml += "<standardcode>"+(params.get("standardcode")+"").trim()+"</standardcode> ";

        xml += "<requestset> <reqcondition> <condition>\n" +
                "<collecttype>0</collecttype>\n" +
                "</condition>\n" +
                "</reqcondition> <reqpaging>0</reqpaging> <reqpageindex>-1</reqpageindex> <reqpageset>0</reqpageset>\n" +
                "</requestset>\n" +
                " <datacompress>1</datacompress> ";//压缩标志：

        if(!params.containsKey("daqtaskid") || "".equals((params.get("daqtaskid")+"").trim())){
            logger.error("daqtaskid不能为空");
            return null;
        }
        xml += "<daqtaskid>"+(params.get("daqtaskid")+"").trim()+"</daqtaskid> ";
        xml +="<businessdata>";

        //使用datacompress压缩时，业务报文需要加dmp;
        if(!params.containsKey("dmp") || "".equals((params.get("dmp")+"").trim())){
            logger.error("dmp不能为空");
            return null;
        }
        //压缩加密businessdata
        Map<String,Object> dmp = (Map<String, Object>) params.get("dmp");
        String req_xml= XMLUtil.mapParseXML(dmp,"dmp");

        if(logger.isInfoEnabled()){
            logger.info("压缩前数据：\n-------\n"+req_xml+"\n-------");
        }
        String dmpStr = YyhbwjxUtil.compressToString(req_xml,"UTF-8");
        xml += dmpStr;
        xml +=
                "</businessdata>\n" +
                        "</business>\n";
        xml += "</messages>";

        logger.info("数据采集上传入参："+xml);

        return xml;

    }

    private String createZfXml(Map<String,Object> params,HttpSign httpSign,String license,String serciecode) throws IOException {
        String xml = "";
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //数据采集上传
        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<messages xmlns=\"http://www.neusoft.com/hit/rhin\">\n" +
                "<heartbeat></heartbeat>\n" +
                "<switchset> <authority> <authoritytype>0</authoritytype>\n" +
                "<username/>\n" +
                "<userpwd/>\n";
        if(null == license){
            xml +="<license/>\n";
        }else{
            xml +="<license>"+license+"</license>";
        }
        xml += "</authority> <visitor> ";
        xml += "<sourceorgan>"+LINAN_ORGAN_CODE+"</sourceorgan> ";
        xml += "<sourcedomain>"+LINAN_SYSTEM_CODE+"</sourcedomain> ";
        xml+=		"</visitor> <serviceinf> <servicecode>"+serciecode+"</servicecode>\n" +
                "</serviceinf> <provider> <targetorgan/>\n" +
                "<targetdomain/>\n" +
                "</provider> <route/>\n" +
                "<process/>" +
                "</switchset> <business>\n" ;
        if(!params.containsKey("standardcode") || "".equals((params.get("standardcode")+"").trim())){
            logger.error("standardcode不能为空");
            return null;
        }
        xml += "<standardcode>"+(params.get("standardcode")+"").trim()+"</standardcode> ";

        xml += "<requestset> <reqcondition/>" +
                "<reqpaging>0</reqpaging> <reqpageindex>-1</reqpageindex> <reqpageset>0</reqpageset>\n" +
                "</requestset>\n" +
                " <datacompress>1</datacompress> ";//压缩标志：

        if(!params.containsKey("daqtaskid") || "".equals((params.get("daqtaskid")+"").trim())){
            logger.error("daqtaskid不能为空");
            return null;
        }
        xml += "<daqtaskid>"+(params.get("daqtaskid")+"").trim()+"</daqtaskid> ";
        xml +="<businessdata>";

        //使用datacompress压缩时，业务报文需要加dmp;
        if(!params.containsKey("dmp") || "".equals((params.get("dmp")+"").trim())){
            logger.error("dmp不能为空");
            return null;
        }
        //压缩加密businessdata
        Map<String,Object> dmp = (Map<String, Object>) params.get("dmp");
        String req_xml= XMLUtil.mapParseXML(dmp,"dmp");

        if(logger.isInfoEnabled()){
            logger.info("压缩前数据：\n-------\n"+req_xml+"\n-------");
        }
        String dmpStr = YyhbwjxUtil.compressToString(req_xml,"UTF-8");
        xml += dmpStr;
        xml +=
                "</businessdata>\n" +
                        "</business>\n";
        xml += "</messages>";

        logger.info("数据采集上传入参："+xml);

        return xml;

    }

    /**
     * 压缩加密
     * 1. String 转换为 GZIPOutputStream；
     * 2. GZIPOutputStream 转换为 Base64.encodeBase64String；
     * @param str
     * @param encoding
     * @return
     */
    public static String compressToString(String str, String encoding) throws IOException {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(encoding));
            gzip.close();
//			Base64 base64 = new Base64();
//			String base64Sign = base64.encodeToString(out.toByteArray());
            Base64.Encoder encoder = Base64.getEncoder();
            String base64Sign = encoder.encodeToString(out.toByteArray());
            return base64Sign;
        } catch (IOException e) {
            logger.error("gzip compress error.", e);
        }finally {
            out.close();
        }
        return null;
    }

    private String createSingleDeclare(Map<String,Object> params,String license){
        String xml = "";
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //单任务号获取 S00KVd6itp5dPkw75bAhgTm
        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<messages xmlns=\"http://www.neusoft.com/hit/rhin\">\n" +
                "<heartbeat/>" +
                "<switchset> <authority> <authoritytype/>\n" +
                "<username/>\n" +
                "<userpwd/>\n";
        if(null == license){
            xml +="<license/>\n";
        }else{
            xml +="<license>"+license+"</license>";
        }
        xml += "</authority> <visitor> ";
        xml += "<sourceorgan>"+LINAN_ORGAN_CODE+"</sourceorgan> ";
        xml += "<sourcedomain>"+LINAN_SYSTEM_CODE+"</sourcedomain> ";
        xml +=
                "</visitor> <serviceinf> <servicecode>"+TASK_CODE+"</servicecode>\n" +
                        "</serviceinf> <provider> <targetorgan/>\n" +
                        "<targetdomain/>\n" +
                        "</provider> <route/>\n" +
                        "<process/>\n" +
                        "</switchset> <business> <standardcode/>\n" +
                        "<requestset> <reqcondition> <condition/>\n" +
                        "</reqcondition> <reqpaging/>\n" +
                        "<reqpageindex/>\n" +
                        "<reqpageset/>\n" +
                        "</requestset> <datacompress/>\n" +
                        "<daqtaskid>"+sf.format(new Date())+"</daqtaskid> <businessdata>\n" +
                        "<declaretype>1</declaretype>\n";
        if(params.containsKey("singledeclare")){
            if(params.get("singledeclare").getClass().isAssignableFrom(ArrayList.class)){
                List<Map<String, Object>> singledeclares = (List<Map<String, Object>> ) params.get("singledeclare");
                for(Map<String,Object> map:singledeclares){
                    xml += XMLUtil.mapParseXML(map,"singledeclare");
                }
            }else {
                xml += XMLUtil.mapParseXML((Map<String, Object>) params.get("singledeclare"),"singledeclare");
            }

        }
        xml+=	"</businessdata>\n" +
                "</business> <extendset/>\n" +
                "</messages>";
        logger.info("单任务声明入参："+xml);
        return xml;
    }

    /**
     //     * 按num数据量截取list
     //     * @param list
     //     * @param num
     //     * @return
     //     */
    public static Map<Integer,List<Map<String,Object>>> getSubList(List<Map<String,Object>> list,int num){
        int len = list.size();
        Map<Integer,List<Map<String,Object>>> result = new HashMap<>();
        if(len > num){
            int i = 0;
            for(i=0;i< (int)((len-1)/num);i++){
                List<Map<String,Object>> list1 = list.subList(i*num, (i+1)*num);
                result.put(i+1,list1);
            }
            List<Map<String,Object>> list2 = list.subList(i*num, len);
            result.put(i+1,list2);
        }else{
            result.put(1,list);
        }
        return result;
    }
}
