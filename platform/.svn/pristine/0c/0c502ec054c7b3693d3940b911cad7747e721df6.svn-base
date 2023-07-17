//package com.bsoft.system.domain.yyh;
//
//import cn.hutool.core.date.DateUtil;
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.JSONUtil;
//import com.bsoft.common.utils.HttpSign;
//import org.apache.commons.codec.binary.Base64;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.zip.GZIPInputStream;
//import java.util.zip.GZIPOutputStream;
//
//public class YyhbwjxUtil {
//    private static Logger logger = LoggerFactory.getLogger("aggr");
//
//    private String SOURCEORGAN;
//    private String SOURCEDOMAIN;
//    private String TARGETORGAN;
//    private String TARGETDOMAIN;
//    //批次号声明服务
//    private String PCRWHHQW;
//    //批次号声明服务servicecode
//    private String PCRWHHQW_SERVICECODE;
//    //单次任务声明服务
//    private String DCRWHW;
//    //单次任务声明服务servicecode
//    private String DCRWHW_SERVICECODE;
//    //数据采集上传服务
//    private String SJSCW;
//    //数据采集上传服务servicecode
//    private String SJSCW_SERVICECODE;
//
//    //数据对账服务地址
//    private String SJDZURL;
//    //数据对账服务编码
//    private String SJDZ_SERVICECODE;
//    //心跳服务地址
//    private String FWXTSBURL;
//    //心跳服务编码
//    private String FWXTSBCODE;
//
//    //核酸采集上传地址
//    private String HSCJSCURL;
//    //核酸采集服务号
//    private String HSCJ_SERVICECODE;
//
//    public void setSJDZURL(String SJDZURL) {
//        this.SJDZURL = SJDZURL;
//    }
//    public void setSJDZ_SERVICECODE(String SJDZ_SERVICECODE) {
//        this.SJDZ_SERVICECODE = SJDZ_SERVICECODE;
//    }
//
//    public void setFWXTSBURL(String FWXTSBURL) {
//        this.FWXTSBURL = FWXTSBURL;
//    }
//    public void setFWXTSBCODE(String FWXTSBCODE) {
//        this.FWXTSBCODE = FWXTSBCODE;
//    }
//
//    public void setHSCJSCURL(String HSCJSCURL) {
//        this.HSCJSCURL = HSCJSCURL;
//    }
//
//    public void setHSCJ_SERVICECODE(String HSCJ_SERVICECODE) {
//        this.HSCJ_SERVICECODE = HSCJ_SERVICECODE;
//    }
//
//    public YyhbwjxUtil(YYH_CLIENT client){
//        this.SOURCEDOMAIN = client.getSOURCEDOMAIN() == null ? "" : client.getSOURCEDOMAIN();
//        this.SOURCEORGAN = client.getSOURCEORGAN() == null ? "" : client.getSOURCEORGAN();
//        this.TARGETORGAN = client.getTARGETORGAN() == null ? "" : client.getTARGETORGAN();
//        this.TARGETDOMAIN = client.getTARGETDOMAIN() == null ? "" : client.getTARGETDOMAIN();
//        this.PCRWHHQW = client.getPCRWHHQW();
//        this.PCRWHHQW_SERVICECODE = client.getPCRWHHQW_SERVICECODE();
//        this.DCRWHW = client.getDCRWHW();
//        this.DCRWHW_SERVICECODE = client.getDCRWHW_SERVICECODE();
//        this.SJSCW = client.getSJSCW();
//        this.SJSCW_SERVICECODE = client.getSJSCW_SERVICECODE();
//    }
//
//    /**
//     * 将医养护报文解析messagecode，messagecode=-1表示flase  其他为true  add  bck
//     */
//    public boolean  checkBw(String xml) {
//        boolean flag=true;
//        String newXml=xml.substring(xml.indexOf("<switchmessage>"), xml.indexOf("</switchset>"));
//        Map<String, Object> getSptMsgMap=XMLUtil.xmlParseMap(newXml);
//        List<Map<String, Object>> data=(List<Map<String, Object>>) getSptMsgMap.get("switchmessage");
//        String messagecode=data.get(0).get("messagecode").toString();
//        String newXml1=xml.substring(xml.indexOf("<returnmessage>"), xml.indexOf("<returnset>"));
//        Map<String, Object> getSptMsgMap1=XMLUtil.xmlParseMap(newXml1);
//        List<Map<String, Object>> data1=(List<Map<String, Object>>) getSptMsgMap1.get("returnmessage");
//        String retcode=data1.get(0).get("retcode").toString();
//
//        if("-1".equals(messagecode)||"-1".equals(retcode)){
//            logger.error(data.get(1).get("messagetext")==null?"":data.get(1).get("messagetext").toString());
//            logger.error(data1.get(1).get("rettext")==null?"":data1.get(1).get("rettext").toString());
//            flag=false;
//        }
//        return flag;
//    }
//
//    /**
//     * 获取taskid
//     * @param xml
//     * @return
//     */
//    public String getTaskId(String xml){
//        String ls_node_name = "<taskid>";
//        String newXml=xml.substring(xml.indexOf(ls_node_name)+ls_node_name.length(), xml.indexOf("</taskid>"));
//        return newXml;
//
//    }
//
//    public void heartBeatS(String jgmc,String ip,String license){
//        String xml = "";
//        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        //批次任务号获取 T00MCD6vGvJzxp8dUxvNyrl
//        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<messages xmlns=\"http://www.neusoft.com/hit/rhin\">\n" +
//                "<heartbeat>0</heartbeat>" +
//                "<switchset> <authority> <authoritytype>0</authoritytype>\n" +
//                "<username/>\n" +
//                "<userpwd/>\n";
//        if(null == license){
//            xml +="<license/>\n";
//        }else{
//            xml +="<license>"+license+"</license>";
//        }
//        xml += "</authority> <visitor> ";
//        xml += "<sourceorgan>"+this.SOURCEORGAN+"</sourceorgan> ";
//        xml += "<sourcedomain>"+this.SOURCEDOMAIN+"</sourcedomain> ";
//        xml +=
//                "</visitor> <serviceinf> <servicecode>"+this.FWXTSBCODE+"</servicecode>\n" +
//                        "</serviceinf> <provider> <targetorgan/>\n" +
//                        "<targetdomain/>\n" +
//                        "</provider> <route/>\n" +
//                        "<process/>\n" +
//                        "</switchset> <business> <standardcode/>\n" +
//                        "<requestset> <reqcondition> <condition/>\n" +
//                        "</reqcondition> <reqpaging/>\n" +
//                        "<reqpageindex/>\n" +
//                        "<reqpageset/>\n" +
//                        "</requestset> <datacompress>0</datacompress>\n" +
//                        "<daqtaskid/> <businessdata>\n" +
//                        "<requestparams>\n"+
//                        "<organization_code>"+this.SOURCEORGAN+"</organization_code>\n"+
//                        "<organization_name>"+jgmc+"</organization_name>\n"+
//                        "<ip_address>"+ip+"</ip_address>\n"+
//                        "<update_time>"+ DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss")+"</update_time>\n"+
//                        "</requestparams>";
//        xml+=	"</businessdata>\n" +
//                "</business> <extendset/>\n" +
//                "</messages>";
//        logger.info("心跳服务入参："+xml);
//        String resStr = WebServiceUtil.invoke(this.FWXTSBURL, "report", xml);
//        logger.info("心跳报告出参："+resStr);
//    }
//
//    /**
//     * 获取心跳监测正常返回xmlData
//     */
//    public String getHeartBeatOutXmlData(String servicecode){
//        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<messages xmlns=\"http://www.neusoft.com/hit/rhin\" \n" +
//                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n" +
//                "xsi:schemaLocation=\"http://www.neusoft.com/hit/rhin \n" +
//                "file:///e:/return_message.xsd\">\n" +
//                "<switchset>\n" +
//                "<visitor>\n" ;
//        xml += "<sourceorgan>"+this.SOURCEORGAN+"</sourceorgan> ";
//        xml += "<sourcedomain>"+this.SOURCEDOMAIN+"</sourcedomain> ";
//
//        xml +=
//                "</visitor>\n" +
//                        "<serviceinf>\n" +
//                        "<servicecode>"+servicecode+"</servicecode>\n" +
//                        "</serviceinf>\n" +
//                        "<provider>\n" +
//                        "<targetorgan>"+this.TARGETORGAN+"</targetorgan>\n" +
//                        "<targetdomain>"+this.TARGETDOMAIN+"<targetdomain>\n" +
//                        "</provider>\n" +
//                        "<route></route>\n" +
//                        "<process/>\n" +
//                        "<switchmessage>\n" +
//                        "<messagecode></messagecode>\n" +
//                        "<messagetext/>\n" +
//                        "</switchmessage>\n" +
//                        "</switchset>\n" +
//                        "<business>\n" +
//                        "<standardcode></standardcode>\n" +
//                        "<returnmessage>\n" +
//                        "<retcode/>\n" +
//                        "<rettext/>\n" +
//                        "</returnmessage>\n" +
//                        "<returnset>\n" +
//                        "<rettotal>1</rettotal>" +
//                        "<retpaging>0</retpaging>\n" +
//                        "<retpageindex>-1</retpageindex>\n" +
//                        "<retpageset>0</retpageset>\n" +
//                        "</returnset>\n" +
//                        "<datacompress>0</datacompress>\n" +
//                        "<businessdata></businessdata>\n" +
//                        "</business>\n" +
//                        "</extendset>\n" +
//                        "</messages>";
//        return xml;
//    }
//
//    /**
//     * 接收服务返回xmlData
//     */
//    public String getReceiveXmlData(String servicecode,Integer retcode){
//        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<messages xmlns=\"http://www.neusoft.com/hit/rhin\" \n" +
//                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n" +
//                "xsi:schemaLocation=\"http://www.neusoft.com/hit/rhin \n" +
//                "file:///e:/return_message.xsd\">\n" +
//                "<switchset>\n" +
//                "<visitor>\n" ;
//        xml += "<sourceorgan>"+this.SOURCEORGAN+"</sourceorgan> ";
//        xml += "<sourcedomain>"+this.SOURCEDOMAIN+"</sourcedomain> ";
//
//        xml +=
//                "</visitor>\n" +
//                        "<serviceinf>\n" +
//                        "<servicecode>"+servicecode+"</servicecode>\n" +
//                        "</serviceinf>\n" +
//                        "<provider>\n" +
//                        "<targetorgan>"+this.TARGETORGAN+"</targetorgan>\n" +
//                        "<targetdomain>"+this.TARGETDOMAIN+"<targetdomain>\n" +
//                        "</provider>\n" +
//                        "<route></route>\n" +
//                        "<process/>\n" +
//                        "<switchmessage>\n" +
//                        "<messagecode></messagecode>\n" +
//                        "<messagetext/>\n" +
//                        "</switchmessage>\n" +
//                        "</switchset>\n" +
//                        "<business>\n" +
//                        "<standardcode></standardcode>\n" +
//                        "<returnmessage>\n" +
//                        "<retcode>"+retcode+"</retcode>" +
//                        "<rettext>"+(retcode == 1 ? "接收成功" : "接收失败")+"</rettext>\n" +
//                        "</returnmessage>\n" +
//                        "<returnset>\n" +
//                        "<rettotal>1</rettotal>" +
//                        "<retpaging>0</retpaging>\n" +
//                        "<retpageindex>-1</retpageindex>\n" +
//                        "<retpageset>0</retpageset>\n" +
//                        "</returnset>\n" +
//                        "<datacompress>0</datacompress>\n" +
//                        "<businessdata></businessdata>\n" +
//                        "</business>\n" +
//                        "</extendset>\n" +
//                        "</messages>";
//
//        return xml;
//    }
//
//    /**
//     * 批次服务声明
//     */
//    public String getTotalDeclare(Map<String,Object> params,String license){
//        String xml = "";
//        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        //批次任务号获取 T00MCD6vGvJzxp8dUxvNyrl
//        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<messages xmlns=\"http://www.neusoft.com/hit/rhin\">\n" +
//                "<heartbeat/>" +
//                "<switchset> <authority> <authoritytype/>\n" +
//                "<username/>\n" +
//                "<userpwd/>\n";
//        if(null == license){
//            xml +="<license/>\n";
//        }else{
//            xml +="<license>"+license+"</license>";
//        }
//        xml += "</authority> <visitor> ";
//        xml += "<sourceorgan>"+this.SOURCEORGAN+"</sourceorgan> ";
//        xml += "<sourcedomain>"+this.SOURCEDOMAIN+"</sourcedomain> ";
//        xml +=
//                "</visitor> <serviceinf> <servicecode>"+this.PCRWHHQW_SERVICECODE+"</servicecode>\n" +
//                        "</serviceinf> <provider> <targetorgan/>\n" +
//                        "<targetdomain/>\n" +
//                        "</provider> <route/>\n" +
//                        "<process/>\n" +
//                        "</switchset> <business> <standardcode/>\n" +
//                        "<requestset> <reqcondition> <condition/>\n" +
//                        "</reqcondition> <reqpaging/>\n" +
//                        "<reqpageindex/>\n" +
//                        "<reqpageset/>\n" +
//                        "</requestset> <datacompress/>\n" +
//                        "<daqtaskid>"+sf.format(new Date())+"</daqtaskid> <businessdata>\n" +
//                        "<declaretype>0</declaretype>\n"+
//                        " <collecttype>0</collecttype>\n";
//        if(params.containsKey("totaldeclare")){
//            if(params.get("totaldeclare").getClass().isAssignableFrom(ArrayList.class)){
//                List<Map<String, Object>> totaldeclares = (List<Map<String, Object>> ) params.get("totaldeclare");
//                for(Map<String,Object> map:totaldeclares){
//                    xml += XMLUtil.mapParseXML(map,"totaldeclare");
//                }
//            }else {
//                xml += XMLUtil.mapParseXML((Map<String, Object>) params.get("totaldeclare"),"totaldeclare");
//            }
//        }
//        xml+=	"</businessdata>\n" +
//                "</business> <extendset/>\n" +
//                "</messages>";
//        logger.info("批次服务声明入参："+xml);
//        String resStr = WebServiceUtil.invoke(this.PCRWHHQW, "totalDeclare", xml);
//        return resStr;
//    }
//
//    /**
//     * 单任务声明
//     */
//    public String getSingleDeclare(Map<String,Object> params,String license){
//
//        String xml = "";
//        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        //单任务号获取 S00KVd6itp5dPkw75bAhgTm
//        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<messages xmlns=\"http://www.neusoft.com/hit/rhin\">\n" +
//                "<heartbeat/>" +
//                "<switchset> <authority> <authoritytype/>\n" +
//                "<username/>\n" +
//                "<userpwd/>\n";
//        if(null == license){
//            xml +="<license/>\n";
//        }else{
//            xml +="<license>"+license+"</license>";
//        }
//        xml += "</authority> <visitor> ";
//        xml += "<sourceorgan>"+this.SOURCEORGAN+"</sourceorgan> ";
//        xml += "<sourcedomain>"+this.SOURCEDOMAIN+"</sourcedomain> ";
//        xml +=
//                "</visitor> <serviceinf> <servicecode>"+this.DCRWHW_SERVICECODE+"</servicecode>\n" +
//                        "</serviceinf> <provider> <targetorgan/>\n" +
//                        "<targetdomain/>\n" +
//                        "</provider> <route/>\n" +
//                        "<process/>\n" +
//                        "</switchset> <business> <standardcode/>\n" +
//                        "<requestset> <reqcondition> <condition/>\n" +
//                        "</reqcondition> <reqpaging/>\n" +
//                        "<reqpageindex/>\n" +
//                        "<reqpageset/>\n" +
//                        "</requestset> <datacompress/>\n" +
//                        "<daqtaskid>"+sf.format(new Date())+"</daqtaskid> <businessdata>\n" +
//                        "<declaretype>1</declaretype>\n";
//        if(params.containsKey("singledeclare")){
//            if(params.get("singledeclare").getClass().isAssignableFrom(ArrayList.class)){
//                List<Map<String, Object>> singledeclares = (List<Map<String, Object>> ) params.get("singledeclare");
//                for(Map<String,Object> map:singledeclares){
//                    xml += XMLUtil.mapParseXML(map,"singledeclare");
//                }
//            }else {
//                xml += XMLUtil.mapParseXML((Map<String, Object>) params.get("singledeclare"),"singledeclare");
//            }
//
//        }
//        xml+=	"</businessdata>\n" +
//                "</business> <extendset/>\n" +
//                "</messages>";
//        logger.info("单任务声明入参："+xml);
//        String resStr = WebServiceUtil.invoke(this.DCRWHW, "singleDeclare", xml);
//        return resStr;
//    }
//
//
//    public String uploadHscjMethod(Map<String,Object> params,String reqURL,String method,String serciecode,String license){
//        String xml = "";
//        //数据采集上传
//        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<messages xmlns=\"http://www.neusoft.com/hit/rhin\">\n" +
//                "<heartbeat/>\n" +
//                "<switchset> <authority> <authoritytype/>\n" +
//                "<username/>\n" +
//                "<userpwd/>\n";
//        if(null == license){
//            xml +="<license/>\n";
//        }else{
//            xml +="<license>"+license+"</license>";
//        }
//        xml += "</authority> <visitor> ";
//        xml += "<sourceorgan>"+this.SOURCEORGAN+"</sourceorgan> ";
//        xml += "<sourcedomain>"+this.SOURCEDOMAIN+"</sourcedomain> ";
//        xml+=	"</visitor> <serviceinf> <servicecode>"+serciecode+"</servicecode>\n" +
//                "</serviceinf> <provider> <targetorgan/>\n" +
//                "<targetdomain/>\n" +
//                "</provider> <route/>\n" +
//                "<process/>" +
//                "</switchset> <business>\n" ;
//        xml += "<standardcode></standardcode>";
//        xml += "<requestset> <reqcondition> <condition>\n" +
//                "<collecttype>0</collecttype>\n" +
//                "</condition>\n" +
//                "</reqcondition> <reqpaging>0</reqpaging> <reqpageindex>-1</reqpageindex><reqpageset>0</reqpageset>\n" +
//                "</requestset>\n" +
//                " <datacompress>0</datacompress> ";//压缩标志：
//        xml += "<daqtaskid/>";
//        xml +="<businessdata>";
//
//        Map<String,Object> dmp = (Map<String, Object>) params.get("datasets");
//        String req_xml= XMLUtil.mapParseXML(dmp,"datasets");
//
//        xml += req_xml;
//        xml +="</businessdata>\n" + "</business>\n";
//        xml += "</messages>";
//        logger.info("数据采集上传入参：\n"+xml);
//        String resStr = WebServiceUtil.invoke(reqURL, method, xml);
//        logger.info("数据采集上传出参：\n"+resStr);
//        return resStr;
//    }
//
//    public String getReport(Map<String,Object> params){
//        String xml = "";
//        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//
//        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<messages xmlns=\"http://www.neusoft.com/hit/rhin\">\n" +
//                "<heartbeat/>" +
//                "<switchset> <authority> <authoritytype/>\n" +
//                "<username/>\n" +
//                "<userpwd/>\n";
//        xml +="<license/>\n";
//        xml += "</authority> <visitor>";
//        //请求服务的机构编码
//        xml += "<sourceorgan>"+this.SOURCEORGAN+"</sourceorgan> ";
//        //请求服务的接入系统编码
//        xml += "<sourcedomain>"+this.SOURCEDOMAIN+"</sourcedomain> ";
//        //服务提供方在开发服务(直接调用服务)时，取值为:20201071
//        xml += "</visitor> <serviceinf> <servicecode>"+this.DCRWHW_SERVICECODE+"</servicecode>\n" +
//                "</serviceinf> <provider> <targetorgan/>\n" +
//                "<targetdomain/>\n" +
//                "</provider> <route/>\n" +
//                "<process/>\n" +
//                //standardcode:服务调用方
//                "</switchset> <business> <standardcode/>\n" +
//                //requestset节点:
//                //reqpaging		服务调用方	Y	服务调用方自定义
//                //reqpageindex	服务调用方	Y	服务调用方自定义
//                //reqpageset	服务调用方	Y	服务调用方自定义
//                "<requestset> <reqcondition> <condition>";
//
//        xml += "<domainCode>"+params.get("domainCode")+"</domainCode>";
//        xml += "<resCode>"+params.get("resCode")+"</resCode>";
//        xml += "<startTime>"+params.get("startTime")+"</startTime>";
//        xml += "<endTime>"+params.get("endTime")+"</endTime>";
//
//        xml += "</condition></reqcondition> <reqpaging>0</reqpaging>\n" +
//                "<reqpageindex>-1</reqpageindex>\n" +
//                "<reqpageset>0</reqpageset>\n" +
//                "</requestset> <datacompress>0</datacompress>\n" +
//                "<daqtaskid/><businessdata>\n";
//        xml+=	"</businessdata>\n" +
//                "</business> <extendset/>\n" +
//                "</messages>";
//        logger.info("获取上传报告入参："+xml);
//        String resStr = WebServiceUtil.invoke(this.DCRWHW, "resourceMethod", xml);
//        return resStr;
//    }
//
//    /**
//     * 数据采集上传
//     */
//    public String uploadData(Map<String,Object> params, HttpSign httpSign, String license) throws IOException {
////		boolean flag=true;
////		String xml = "";
////		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
////		//数据采集上传
////		xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
////				"<messages xmlns=\"http://www.neusoft.com/hit/rhin\">\n" +
////				"<heartbeat/>\n" +
////				"<switchset> <authority> <authoritytype/>\n" +
////				"<username/>\n" +
////				"<userpwd/>\n";
////				if(null == license){
////					xml +="<license/>\n";
////				}else{
////					xml +="<license>"+license+"</license>";
////				}
////		xml += "</authority> <visitor> ";
////		xml += "<sourceorgan>"+this.SOURCEORGAN+"</sourceorgan> ";
////		xml += "<sourcedomain>"+this.SOURCEDOMAIN+"</sourcedomain> ";
////		xml+=		"</visitor> <serviceinf> <servicecode>"+this.SJSCW_SERVICECODE+"</servicecode>\n" +
////				"</serviceinf> <provider> <targetorgan/>\n" +
////				"<targetdomain/>\n" +
////				"</provider> <route/>\n" +
////				"<process/>" +
////				"</switchset> <business>\n" ;
////		if(!params.containsKey("standardcode") || "".equals((params.get("standardcode")+"").trim())){
////			logger.error("standardcode不能为空");
////			return null;
////		}
////		xml += "<standardcode>"+(params.get("standardcode")+"").trim()+"</standardcode> ";
////		xml += "<requestset> <reqcondition> <condition>\n" +
////				"<collecttype>0</collecttype>\n" +
////				"</condition>\n" +
////				"</reqcondition> <reqpaging>0</reqpaging> <reqpageindex>-1</reqpageindex> <reqpageset>0</reqpageset>\n" +
////				"</requestset>\n" +
////				" <datacompress>1</datacompress> ";//压缩标志：
////		if(!params.containsKey("daqtaskid") || "".equals((params.get("daqtaskid")+"").trim())){
////			logger.error("daqtaskid不能为空");
////			return null;
////		}
////		xml += "<daqtaskid>"+(params.get("daqtaskid")+"").trim()+"</daqtaskid> ";
////		xml +="<businessdata>";
////
////		//使用datacompress压缩时，业务报文需要加dmp;
////		if(!params.containsKey("dmp") || "".equals((params.get("dmp")+"").trim())){
////			logger.error("dmp不能为空");
////			return null;
////		}
////		//压缩加密businessdata
////		Map<String,Object> dmp = (Map<String, Object>) params.get("dmp");
////		String req_xml= XMLUtil.mapParseXML(dmp,"dmp");
////		if(logger.isInfoEnabled()){
////			logger.info("压缩前数据：-------\n"+req_xml+"\n-------");
////		}
////		String dmpStr = YyhbwjxUtil.compressToString(req_xml,"UTF-8");
//////		xml+= httpSign.getSHA256(dmpStr);
////		xml += dmpStr;
////		xml +=
////				"</businessdata>\n" +
////						"</business>\n";
////		if(httpSign.getEnable()){//需要ca签名
////			String caResult = httpSign.doSign(dmpStr);
////			if(caResult == null){
////				logger.error("ca签名发生错误");
////				return null;
////			}
////			JSONObject jo = JSONUtil.parseObj(caResult);
////			String errCode = jo.get("errCode")+"";
////			if(!"0".equals(errCode)){
////				logger.error("ca签名发生错误:"+caResult);
////				return null;
////			}
////			JSONObject data = (JSONObject) jo.get("data");
////			String signResult = data.get("signResult")+"";
////			String flag1 = signResult.substring(signResult.length()-1,signResult.length());
////			if("\n".equals(flag1)){
////				signResult = signResult.substring(0,signResult.length()-1);
////			}
////			xml += "<extendset>";
////			xml += "<summary>";
////			xml += signResult.replaceAll("\n","");
////			xml += "</summary>";
////			xml += "<timestamp>";
////			xml += "</timestamp>";
////			xml += "</extendset>";
////		}
////		xml += "</messages>";
////
////		logger.info("数据采集上传入参："+xml);
////		String resStr = WebServiceUtil.invoke(this.SJSCW, "handle", xml);
////		return resStr;
//        return uploadMethod(params,httpSign,license,this.SJSCW,"handle",this.SJSCW_SERVICECODE);
//    }
//
//    /**
//     * 数据对账上传
//     */
//    public String uploadSJZDData(Map<String,Object> params,HttpSign httpSign,String license) throws IOException {
//        return uploadMethod(params,httpSign,license,this.SJDZURL,"msgSingleConsume",this.SJDZ_SERVICECODE);
//    }
//
//    private String uploadMethod(Map<String,Object> params,HttpSign httpSign,String license,String reqURL,String method,String serciecode) throws IOException {
//        boolean flag=true;
//        String xml = "";
//        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        //数据采集上传
//        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<messages xmlns=\"http://www.neusoft.com/hit/rhin\">\n" +
//                "<heartbeat/>\n" +
//                "<switchset> <authority> <authoritytype/>\n" +
//                "<username/>\n" +
//                "<userpwd/>\n";
//        if(null == license){
//            xml +="<license/>\n";
//        }else{
//            xml +="<license>"+license+"</license>";
//        }
//        xml += "</authority> <visitor> ";
//        xml += "<sourceorgan>"+this.SOURCEORGAN+"</sourceorgan> ";
//        xml += "<sourcedomain>"+this.SOURCEDOMAIN+"</sourcedomain> ";
//        xml+=		"</visitor> <serviceinf> <servicecode>"+serciecode+"</servicecode>\n" +
//                "</serviceinf> <provider> <targetorgan/>\n" +
//                "<targetdomain/>\n" +
//                "</provider> <route/>\n" +
//                "<process/>" +
//                "</switchset> <business>\n" ;
//        if(!params.containsKey("standardcode") || "".equals((params.get("standardcode")+"").trim())){
//            logger.error("standardcode不能为空");
//            return null;
//        }
//        xml += "<standardcode>"+(params.get("standardcode")+"").trim()+"</standardcode> ";
//
//        xml += "<requestset> <reqcondition> <condition>\n" +
//                "<collecttype>0</collecttype>\n" +
//                "</condition>\n" +
//                "</reqcondition> <reqpaging>0</reqpaging> <reqpageindex>-1</reqpageindex> <reqpageset>0</reqpageset>\n" +
//                "</requestset>\n" +
//                " <datacompress>1</datacompress> ";//压缩标志：
//
//        if(!params.containsKey("daqtaskid") || "".equals((params.get("daqtaskid")+"").trim())){
//            logger.error("daqtaskid不能为空");
//            return null;
//        }
//        xml += "<daqtaskid>"+(params.get("daqtaskid")+"").trim()+"</daqtaskid> ";
//        xml +="<businessdata>";
//
//        //使用datacompress压缩时，业务报文需要加dmp;
//        if(!params.containsKey("dmp") || "".equals((params.get("dmp")+"").trim())){
//            logger.error("dmp不能为空");
//            return null;
//        }
//        //压缩加密businessdata
//        Map<String,Object> dmp = (Map<String, Object>) params.get("dmp");
//        String req_xml= XMLUtil.mapParseXML(dmp,"dmp");
//        if(logger.isInfoEnabled()){
//            logger.info("压缩前数据：\n-------\n"+req_xml+"\n-------");
//        }
//        String dmpStr = YyhbwjxUtil.compressToString(req_xml,"UTF-8");
////		xml+= httpSign.getSHA256(dmpStr);
//        xml += dmpStr;
//        xml +=
//                "</businessdata>\n" +
//                        "</business>\n";
//        if(httpSign.getEnable()){//需要ca签名
//            String caResult = httpSign.doSign(dmpStr);
//            if(caResult == null){
//                logger.error("ca签名发生错误");
//                return null;
//            }
//            JSONObject jo = JSONUtil.parseObj(caResult);
//            String errCode = jo.get("errCode")+"";
//            if(!"0".equals(errCode)){
//                logger.error("ca签名发生错误:"+caResult);
//                return null;
//            }
//            JSONObject data = (JSONObject) jo.get("data");
//            String signResult = data.get("signResult")+"";
//            String flag1 = signResult.substring(signResult.length()-1,signResult.length());
//            if("\n".equals(flag1)){
//                signResult = signResult.substring(0,signResult.length()-1);
//            }
//            xml += "<extendset>";
//            xml += "<summary>";
//            xml += signResult.replaceAll("\n","");
//            xml += "</summary>";
//            xml += "<timestamp>";
//            xml += "</timestamp>";
//            xml += "</extendset>";
//        }
//        xml += "</messages>";
//
//        logger.info("数据采集上传入参："+xml);
//        String resStr = WebServiceUtil.invoke(reqURL, method, xml);
//        return resStr;
//    }
//
//
//    /**
//     * 压缩加密
//     * 1. String 转换为 GZIPOutputStream；
//     * 2. GZIPOutputStream 转换为 Base64.encodeBase64String；
//     * @param str
//     * @param encoding
//     * @return
//     */
//    public static String compressToString(String str, String encoding) throws IOException {
//        if (str == null || str.length() == 0) {
//            return null;
//        }
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        GZIPOutputStream gzip;
//        try {
//            gzip = new GZIPOutputStream(out);
//            gzip.write(str.getBytes(encoding));
//            gzip.close();
//            Base64 base64 = new Base64();
//            String base64Sign = base64.encodeToString(out.toByteArray());
//            return base64Sign;
//        } catch (IOException e) {
//            logger.error("gzip compress error.", e);
//        }finally {
//            out.close();
//        }
//        return null;
//    }
//
//    /**
//     * 解密解压
//     * @param str
//     * @param encoding
//     * @return
//     */
//    public static String uncompressToString(String str,String encoding) throws IOException {
//        if (str == null || str.length() == 0) {
//            return null;
//        }
//        Base64 base64 = new Base64();
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ByteArrayInputStream in = new ByteArrayInputStream(base64.decode(str));
//        try {
//            GZIPInputStream ungzip = new GZIPInputStream(in);
//            byte[] buffer = new byte[256];
//            int n;
//            while ((n = ungzip.read(buffer)) >= 0) {
//                out.write(buffer, 0, n);
//            }
//            return out.toString(encoding);
//        } catch (IOException e) {
//            logger.error("gzip uncompress to string error.", e);
//        }finally {
//            out.close();
//            in.close();
//        }
//        return null;
//    }
//
//    public static String encodeString(String str){
//        str = str.replaceAll("&","&amp;")
//                .replaceAll("'","&apos;")
//                .replaceAll("\"","&quot;")
//                .replaceAll(">","&gt;")
//                .replaceAll("<","&lt;");
//        return str;
//    }
//    public static String decodeString(String str){
//        str = str.replaceAll("&amp;","&;")
//                .replaceAll("&apos;","'")
//                .replaceAll("&quot;","\"")
//                .replaceAll("&gt;",">")
//                .replaceAll("&lt;","<");
//        return str;
//    }
//
//}
//
//
