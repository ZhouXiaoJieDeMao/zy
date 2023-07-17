//package com.bsoft.system.domain.yyh;
//
//import cn.hutool.core.date.DateUtil;
//import com.bsoft.common.utils.HttpSign;
//import com.bsoft.system.mapper.YYHAGGRREORDMapper;
//import com.bsoft.system.mapper.YYHDATAAGGRMapper;
//import com.bsoft.system.service.ISysDynamicSourceService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.net.Inet4Address;
//import java.net.InetAddress;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * 医养护数据归集类
// */
//@Component
//public class YyhDataAggr {
//    private static Logger logger = LoggerFactory.getLogger("aggr");
//
//    @Value("${aggr.hsbgsqd}")
//    private String hsbgsqd;
//    @Value("${aggr.hsbgjyd}")
//    private String hsbgjyd;
//    @Value("${aggr.hsbgmc}")
//    private String hsbgmc;
//    @Value("${aggr.hsbgmxd}")
//    private String hsbgmxd;
//
//    @Value("${aggr.licence}")
//    private String ptsjlicence;
//
//    @Value("${aggr.yyhaddss}")
//    private Integer yyhaddss;
//    @Value("${aggr.hsbgaddm}")
//    private Integer hsbgaddm;
//    @Value("${aggr.sjdzurl}")
//    private String sjdzurl;
//    @Value("${aggr.sjdzcod}")
//    private String sjdzcode;
//    @Value("${aggr.sjdzyymc}")
//    private String sjdzyymc;
//    @Value("${aggr.sjdzyyid}")
//    private String sjdzyyid;
//
//    @Value("${aggr.fwxtsburl}")
//    private String fwxtsburl;
//    @Value("${aggr.fwxtsbcode}")
//    private String fwxtsbcode;
//
//    @Value("${aggr.sjdzflag}")
//    private boolean sjdzflag;
//
//    @Value("${aggr.drsleep}")
//    private boolean drsleep;
//
//    @Value("${aggr.hscjurl}")
//    private String hscjurl;
//    @Value("${aggr.hscjcod}")
//    private String hscjcod;
//    @Value("${aggr.hscjjgsj}")
//    private int hscjjgsj;
//    @Autowired
//    private AggrLogServiceImpl aggrLogService;
//
//    /**
//     * 动态数据库保存
//     */
//    @Autowired
//    private ISysDynamicSourceService iSysDynamicSourceService;
//
//    @Value("${gjyb.master}")
//    private String DBID;
//    @Autowired
//    private YYHAGGRREORDMapper yyhaggrreordMapper;
//    @Autowired
//    private YYHDATAAGGRMapper yyhdataaggrMapper;
//
//    private static int totalSize = 30000;//一批次最大 上传数据量
//    private static int singleSize = 10000;//一任务最大 上传数据量
//
//
//    public void heartReport(YYH_CLIENT yyh_dzclient){
//        YyhbwjxUtil yyhbw = new YyhbwjxUtil(yyh_dzclient);
//        yyhbw.setFWXTSBURL(fwxtsburl);
//        yyhbw.setFWXTSBCODE(fwxtsbcode);
////        InetAddress ip=Inet4Address.getLocalHost();
//        yyhbw.heartBeatS(sjdzyymc,"127.0.0.1",ptsjlicence);
//    }
//
//    /**
//     * 数据集补传上传
//     * @param taskMap
//     * @param dataAggrVo
//     * @param httpSign
//     * @param start
//     * @param end
//     * @return
//     */
//    public Map<String,Boolean> doAggrDataUp(Map<String,Object> taskMap, DataAggrVo dataAggrVo, HttpSign httpSign, Date start, Date end){
//        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
//        Map<String,Boolean> result = new HashMap<>();
//        YYH_CLIENT client = dataAggrVo.getClient();
//        List<YYH_DATA_AGGR> dataAggrs = dataAggrVo.getDataAggrs();
//        if(client == null){
//            logger.error("请先维护医养护信息表（YYH_CLIENT）");
//            return null;
//        }
//        String databaseUrl = client.getCLIENT_DATABASE_URL();
//        String databaseUsername = client.getCLIENT_DATABASE_USERNAME();
//        String databasePassword = client.getCLIENT_DATABASE_PASSWORD();
//        if("".equals(databaseUrl) || databaseUrl == null){
//            logger.error("请先维护医养护信息表（YYH_CLIENT-CLIENT_DATABSE_URL）-(CLIENTID="+client.getCLIENTID()+")");
//            return null;
//        }
//        if("".equals(databaseUsername) || databaseUsername == null){
//            logger.error("请先维护医养护信息表（YYH_CLIENT-CLIENT_DATABSE_USERNAME）-(CLIENTID="+client.getCLIENTID()+")");
//            return null;
//        }
//        if("".equals(databasePassword) || databasePassword == null){
//            logger.error("请先维护医养护信息表（YYH_CLIENT-CLIENT_DATABSE_PASSWORD）-(CLIENTID="+client.getCLIENTID()+")");
//            return null;
//        }
//        /**
//         * 每个业务一条sql，循环上传，间隔上传及总量控制上传，为东软服务方限制。
//         * 增量上传时 同一业务上传调用间隔不超过5分钟，不同业务不超过1分钟
//         * 每个业务按批次上传，最大一批次上传totalSize数据量
//         * 每批次下一次单任务最大上传singleSize数据量
//         */
//        YyhbwjxUtil yyhbw = new YyhbwjxUtil(client);
//        for(YYH_DATA_AGGR data_aggr : dataAggrs){
//            logger.info(data_aggr.getCOLRESCODE()+",name:"+YyhDataMeta.getYyhDataNameByJhbzbm(data_aggr.getCOLRESCODE().trim())+"开始上传");
//            try{
////              InetAddress ip=Inet4Address.getLocalHost();
//                String sql = data_aggr.getSQLCONTENT().trim();
//                if(sql != null && !"".equals(sql)){
//                    Map<String,Object> sqlParams = new HashMap<>();
//                    sqlParams.put("begindate",start);
//                    sqlParams.put("enddate",end);
//                    String querySql = "select '' as SERIALNUM_ID,'' as TASK_ID,'' as BATCH_NUM,'' as LOCAL_ID,'' as BUSINESS_ID,'' as BASIC_ACTIVE_ID,'' as DATAGENERATE_DATE,'' as ORGANIZATION_CODE,'' as ORGANIZATION_NAME,'' as DOMAIN_CODE,'' as CREATE_DATE,'' as UPDATE_DATE,'' as ARCHIVE_DATE,'' as RECORD_IDEN,t.* from (\n"+sql.trim()+"\n) t";
//                    if(querySql.indexOf(":begindate") == -1){
//                        logger.info(data_aggr.getCOLRESCODE()+":sql缺少入参begindate");
//                        result.put(data_aggr.getCOLRESCODE(),false);
//                        continue;
//                    }else{
//                        querySql = querySql.replaceAll(":begindate","#{map.begindate}");
//                    }
//                    if(querySql.indexOf(":enddate") == -1){
//                        logger.info(data_aggr.getCOLRESCODE()+":sql缺少入参enddate");
//                        result.put(data_aggr.getCOLRESCODE(),false);
//                        continue;
//                    }else{
//                        querySql = querySql.replaceAll(":enddate","#{map.enddate}");
//                    }
//                    sqlParams.put("sql",querySql);
////                    List<Map<String,Object>> list = getYyhData(querySql,sqlParams,databaseUrl,databaseUsername,databasePassword);
//                    List<Map<String,Object>> list = iSysDynamicSourceService.dynamicSqlList2(sqlParams,client.getCLIENTID()+"");
//                    if(null == list){
//                        continue;
//                    }
//                    Date beginDate = new Date();
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    logger.info(data_aggr.getCOLRESCODE()+"开始准备上传，记录时间："+sdf.format(beginDate));
//                    if(list != null && list.size() > 0){
//                        Map<Integer,List<Map<String,Object>>> tMap = getSubList(list,totalSize);//分批次
//                        for(Integer key : tMap.keySet()){
//                            List<Map<String,Object>> tList = tMap.get(key);
//                            Map<Integer,List<Map<String,Object>>> sMap = getSubList(tList,singleSize);//分单任务
//                            Map<String,Object> tparams = new HashMap<>();
//                            List<Map<String,Object>> totaldeclares = new ArrayList<>();
//                            Map<String,Object> totaldeclare = new HashMap<>();
//                            totaldeclare.put("colrescode",data_aggr.getCOLRESCODE());//交换标准编码
//                            totaldeclare.put("tasknum",sMap.size());//任务数
//                            totaldeclare.put("begindatetime",sf1.format(start));//查询开始时间
//                            totaldeclare.put("enddatetime",sf1.format(end));//查询结束时间
//                            Map<String,Object> tdeclare = new HashMap<>();
//                            tdeclare.put("setcode",data_aggr.getSETCODE());//数据集编码
//                            tdeclare.put("datanum",tList.size());//数据集总数据量
//                            totaldeclare.put("tdeclare",tdeclare);
//                            totaldeclares.add(totaldeclare);
//                            tparams.put("totaldeclare",totaldeclares);
//                            //总任务声明
//                            String trs = yyhbw.getTotalDeclare(tparams,ptsjlicence);
//                            //请求成功
//                            if(trs != null && yyhbw.checkBw(trs)){
//                                String ttaskid = yyhbw.getTaskId(trs);
//                                //单任务号获取
//                                for(Integer skey : sMap.keySet()){
//                                    List<Map<String,Object>> sList = sMap.get(skey);
//                                    Map<String,Object> sparams = new HashMap<>();
//                                    List<Map<String,Object>> singledeclares = new ArrayList<>();
//                                    Map<String,Object> singledeclare = new HashMap<>();
//                                    singledeclare.put("totaltaskid",ttaskid);
//                                    singledeclare.put("colrescode",data_aggr.getCOLRESCODE());
//                                    singledeclare.put("sn",skey);
//                                    Map<String,Object> declare = new HashMap<>();
//                                    declare.put("setcode",data_aggr.getSETCODE());
//                                    declare.put("datanum",sList.size());
//                                    singledeclare.put("declare",declare);
//                                    singledeclares.add(singledeclare);
//                                    sparams.put("singledeclare",singledeclares);
//                                    //单任务声明
//                                    String srs = yyhbw.getSingleDeclare(sparams,ptsjlicence);
//                                    //成功
//                                    if(srs != null && yyhbw.checkBw(srs)){
//                                        String staskid = yyhbw.getTaskId(srs);
//                                        //数据采集上传
//                                        Map<String,Object> uparams = new HashMap<>();
//                                        uparams.put("standardcode",data_aggr.getCOLRESCODE());
//                                        uparams.put("daqtaskid",staskid);
//                                        Map<String,Object> dmp = new HashMap<>();
//                                        Map<String,Object> datasets = new HashMap<>();
//                                        datasets.put("setcode",data_aggr.getSETCODE());
//                                        datasets.put("settype","");
//                                        //数据内容提取，用于保存日志
//                                        datasets.put("setdetails",sList);
//                                        dmp.put("datasets",datasets);
//                                        uparams.put("dmp",dmp);
//                                        if(drsleep){
//                                            //增量上传时，控制同一业务上传调用间隔不超过5分钟，不同业务不超过1分钟
//                                            if(taskMap != null && taskMap.containsKey("colrescode")){
//                                                Long currenttime = (Long) taskMap.get("currenttime");
//                                                Long n = System.currentTimeMillis();
//                                                Long det = (taskMap.get("colrescode")+"").equals(data_aggr.getCOLRESCODE())
//                                                        ? 5*60*1000 - (n - currenttime) : 1*60*1000 - (n - currenttime);
//                                                if(det > 0){//线程sleep剩余时间
//                                                    Thread.sleep(det);
//                                                }
//                                            }
//                                        }
//
//                                        //上传数据
//                                        String urs = yyhbw.uploadData(uparams,httpSign,ptsjlicence);
//                                        Date endDate = new Date();
//                                        long time = (endDate.getTime()-beginDate.getTime())/1000;
//                                        logger.info(data_aggr.getCOLRESCODE()+" ttaskid:"+ttaskid+"上传完成，记录时间："+sdf.format(endDate)+"本次上传总共耗费："+time+"秒");
//                                        if(taskMap != null){
//                                            taskMap.put("colrescode",data_aggr.getCOLRESCODE());
//                                            taskMap.put("currenttime",System.currentTimeMillis());
//                                        }
//                                        if(urs != null && yyhbw.checkBw(urs)){
//                                            String etaskid = yyhbw.getTaskId(urs);
//                                            //保存上传记录
//                                            YYH_AGGR_RECORD record = new YYH_AGGR_RECORD();
//                                            record.setBEGINDATETIME(sf1.format(start));
//                                            record.setCOLRESCODE(data_aggr.getCOLRESCODE());
//                                            record.setENDDATETIME(sf1.format(end));
//                                            record.setENDTASKID(etaskid);
//                                            record.setSETCODE(data_aggr.getSETCODE());
//                                            record.setSINGLEDATANUM(Long.parseLong(sList.size()+""));
//                                            record.setSINGLETASKID(staskid);
//                                            record.setSN(Long.parseLong(skey+""));
//                                            record.setTASKNUM(Long.parseLong(sMap.size()+""));
//                                            record.setTOTALDATANUM(Long.parseLong(tList.size()+""));
//                                            record.setTOTALTASKID(ttaskid);//+"^"+ip.getHostAddress()
//                                            record.setUPLOADTIME(new Date());
//                                            record.setCLIENTID(client.getCLIENTID());
////                                            baseDao.save(record);
//                                            if(iSysDynamicSourceService.getDynamicSqlMapper(yyhaggrreordMapper,DBID,record.getENDTASKID())!=null){
//                                                iSysDynamicSourceService.dynamicSqlMapper(yyhaggrreordMapper,DBID,record);
//                                            }else{
//                                                iSysDynamicSourceService.dynamicSqlMapperSave(yyhaggrreordMapper,DBID,record);
//                                            }
//                                            //手动提交
//                                            aggrLogService.savePushResult(sList,ttaskid,staskid,data_aggr,"1");
//                                            logger.info(data_aggr.getCOLRESCODE()+"上传成功");
//                                            result.put(data_aggr.getCOLRESCODE(),true);
//                                        }else {
//                                            aggrLogService.savePushResult(sList,ttaskid,staskid,data_aggr,"2");
//                                            logger.info(data_aggr.getCOLRESCODE()+"上传失败，ttaskid:"+ttaskid+"，总量："+sList.size());
//                                            result.put(data_aggr.getCOLRESCODE(),false);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    } else {
//                        logger.info(data_aggr.getCOLRESCODE()+"上传失败,list为空 sql"+sql+"   sqlParams:"+sqlParams);
//                        result.put(data_aggr.getCOLRESCODE(),false);
//                        continue;
//                    }
//                }else {
//                    logger.info(data_aggr.getCOLRESCODE()+"上传失败,为空sql");
//                    result.put(data_aggr.getCOLRESCODE(),false);
//                    continue;
//                }
//            }catch(Exception e){
//                logger.error(data_aggr.getCOLRESCODE()+"上传失败"+e.getMessage(),e);
//                result.put(data_aggr.getCOLRESCODE(),false);
//                continue;
//            }
//        }
//        return result;
//    }
//
//    /**
//     * 数据归集上传
//     * @param dataAggrVo
//     * @param isAutoUpload
//     * @return Map<String,Boolean> 返回指定colrescode的数据上传成功或失败结果
//     */
//    public Map<String,Boolean> doDataAggr(Map<String,Object> taskMap, DataAggrVo dataAggrVo,boolean isAutoUpload,HttpSign httpSign){
//        YYH_CLIENT client = dataAggrVo.getClient();
//        List<YYH_DATA_AGGR> dataAggrs = dataAggrVo.getDataAggrs();
//        String begindate = dataAggrVo.getBegindate();
//        String enddate = dataAggrVo.getEnddate();
//        Map<String,Boolean> result = new HashMap<>();
//        Date now = new Date();
//        if(client == null){
//            logger.info("请先维护医养护信息表（YYH_CLIENT）");
//            return null;
//        }
//        String databaseUrl = client.getCLIENT_DATABASE_URL();
//        String databaseUsername = client.getCLIENT_DATABASE_USERNAME();
//        String databasePassword = client.getCLIENT_DATABASE_PASSWORD();
//        if("".equals(databaseUrl) || databaseUrl == null){
//            logger.info("请先维护医养护信息表（YYH_CLIENT-CLIENT_DATABSE_URL）-(CLIENTID="+client.getCLIENTID()+")");
//            return null;
//        }
//        if(!"main".equals(databaseUrl)&&("".equals(databaseUsername) || databaseUsername == null)){
//            logger.info("请先维护医养护信息表（YYH_CLIENT-CLIENT_DATABSE_USERNAME）-(CLIENTID="+client.getCLIENTID()+")");
//            return null;
//        }
//        if(!"main".equals(databaseUrl)&&("".equals(databasePassword) || databasePassword == null)){
//            logger.info("请先维护医养护信息表（YYH_CLIENT-CLIENT_DATABSE_PASSWORD）-(CLIENTID="+client.getCLIENTID()+")");
//            return null;
//        }
//
//        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
//        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
//        //增量上传默认上传前一天的数据，如果是实时上传，此处时间还会变更。
//        if(isAutoUpload){
//            //前一天
//            Calendar c = Calendar.getInstance();
//            c.setTime(new Date());
//            int day1 = c.get(Calendar.DATE);
//            c.set(Calendar.DATE, day1 - 1);
//            begindate = sf.format(c.getTime())+"000000";
//            enddate = sf.format(c.getTime())+"235959";
//        }
//        /**
//         * 每个业务一条sql，循环上传
//         * 增量上传时 同一业务上传调用间隔不超过5分钟，不同业务不超过1分钟
//         * 每个业务按批次上传，最大一批次上传totalSize数据量
//         * 每批次下一次单任务最大上传singleSize数据量
//         */
//        YyhbwjxUtil yyhbw = new YyhbwjxUtil(client);
//        for(YYH_DATA_AGGR data_aggr : dataAggrs){
//            logger.info(data_aggr.getCOLRESCODE()+"开始上传");
//            try{
//                InetAddress ip=Inet4Address.getLocalHost();
//                String sql = data_aggr.getSQLCONTENT().trim();
//                if(sql != null && !"".equals(sql)){
//                    Map<String,Object> sqlParams = new HashMap<>();
//                    Integer intervalMinutes = data_aggr.getINTERVALMINUTES();
//                    //间隔上传
//                    if(intervalMinutes != null && intervalMinutes > 0 && isAutoUpload){
//                        if(data_aggr.getLASTEXECUTETIME() == null){
//                            logger.info(data_aggr.getCOLRESCODE()+"未到上传时间");
//                            result.put(data_aggr.getCOLRESCODE(),true);
//                            if(isAutoUpload){
//                                //更新执行时间
////                                Map<String,Object> params = new HashMap<>();
////                                params.put("now",now);
////                                params.put("COLRESCODE",data_aggr.getCOLRESCODE());
//                                YYH_DATA_AGGR record = new YYH_DATA_AGGR();
////                                aggrLogService.saveLastModifyTime(params,baseDao);
//                                record.setLASTEXECUTETIME(now);
//                                record.setCOLRESCODE(data_aggr.getCOLRESCODE());
////                                                aggrLogService.saveLastModifyTime(params,baseDao);
//                                iSysDynamicSourceService.dynamicSqlMapper(yyhdataaggrMapper,DBID,record);
//
//                            }
//                            continue;
//                        }
//                        Date lastExecuteTime = data_aggr.getLASTEXECUTETIME();
//                        //如果间隔时间已经大于规定的时间（分钟），则执行
//                        long diff = now.getTime() - lastExecuteTime.getTime();
//                        long difMin = diff/(1000*60);
//                        //添加时间，如果小于0则为0；大于60，则为60
//                        int li_timess = (yyhaddss < 0 )?0:((yyhaddss > 60)?60:yyhaddss);
//                        if(difMin >= intervalMinutes){
//                            Calendar c = Calendar.getInstance();
//                            if(difMin >= 60){//已经超过1个小时的延时，直接跨过自定义轮询时间间隔，强制执行到现在时间点
//                                c.setTime(now);
//                            }else {
//                                //延时小于一小时的，按轮询时间间隔执行
//                                c.setTime(lastExecuteTime);
//                                c.set(Calendar.MINUTE,c.get(Calendar.MINUTE)+intervalMinutes);
//                                c.add(Calendar.SECOND,li_timess);
//                            }
//                            Calendar c_begin = Calendar.getInstance();
//                            c_begin.setTime(lastExecuteTime);
//                            c_begin.add(Calendar.SECOND,-li_timess);
//                            begindate = sf1.format(c_begin.getTime());
//                            enddate = sf1.format(c.getTime());
//                        }else {
//                            logger.info(data_aggr.getCOLRESCODE()+"未到上传时间");
//                            result.put(data_aggr.getCOLRESCODE(),true);
//                            continue;
//                        }
//                    }else {
//                        if(data_aggr.getLASTEXECUTETIME() != null && sf.format(data_aggr.getLASTEXECUTETIME()).equals(sf.format(now)) && isAutoUpload){
//                            logger.info(data_aggr.getCOLRESCODE()+"当天已上传");
//                            result.put(data_aggr.getCOLRESCODE(),true);
//                            continue;
//                        }
//                    }
//
//                    String querySql = "select '' as SERIALNUM_ID,'' as TASK_ID,'' as BATCH_NUM,'' as LOCAL_ID,'' as BUSINESS_ID,'' as BASIC_ACTIVE_ID,'' as DATAGENERATE_DATE,'' as ORGANIZATION_CODE,'' as ORGANIZATION_NAME,'' as DOMAIN_CODE,'' as CREATE_DATE,'' as UPDATE_DATE,'' as ARCHIVE_DATE,'' as RECORD_IDEN,t.* from (\n"+sql.trim()+"\n) t";
//                    if(querySql.indexOf(":begindate") == -1){
//                        logger.info(data_aggr.getCOLRESCODE()+":sql缺少入参begindate");
//                        result.put(data_aggr.getCOLRESCODE(),false);
//                        continue;
//                    }else{
//                        querySql = querySql.replaceAll(":begindate","#{map.begindate}");
//                    }
//                    if(querySql.indexOf(":enddate") == -1){
//                        logger.info(data_aggr.getCOLRESCODE()+":sql缺少入参enddate");
//                        result.put(data_aggr.getCOLRESCODE(),false);
//                        continue;
//                    }else{
//                        querySql = querySql.replaceAll(":enddate","#{map.enddate}");
//                    }
//
//                    Date ld_begindate_par = sf1.parse(begindate);
//                    Date ld_enddate_par = sf1.parse(enddate);
//
//                    sqlParams.put("begindate",ld_begindate_par);
//                    sqlParams.put("enddate",ld_enddate_par);
//                    sqlParams.put("sql",querySql);
////                    List<Map<String,Object>> list = getYyhData(querySql,sqlParams,databaseUrl,databaseUsername,databasePassword);
//                    List<Map<String,Object>> list = iSysDynamicSourceService.dynamicSqlList2(sqlParams,client.getCLIENTID()+"");
//                    if(null == list){
//                        continue;
//                    }
//                    Date beginDate = new Date();
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    logger.info(data_aggr.getCOLRESCODE()+"开始准备上传，记录时间："+sdf.format(beginDate));
//                   if(list != null && list.size() > 0){
//                       Map<Integer,List<Map<String,Object>>> tMap = getSubList(list,totalSize);//分批次
//                       for(Integer key : tMap.keySet()){
//                            List<Map<String,Object>> tList = tMap.get(key);
//                            Map<Integer,List<Map<String,Object>>> sMap = getSubList(tList,singleSize);//分单任务
//                            Map<String,Object> tparams = new HashMap<>();
//                            List<Map<String,Object>> totaldeclares = new ArrayList<>();
//                            Map<String,Object> totaldeclare = new HashMap<>();
//                            totaldeclare.put("colrescode",data_aggr.getCOLRESCODE());//交换标准编码
//                            totaldeclare.put("tasknum",sMap.size());//任务数
//                            totaldeclare.put("begindatetime",begindate);//查询开始时间
//                            totaldeclare.put("enddatetime",enddate);//查询结束时间
//                            Map<String,Object> tdeclare = new HashMap<>();
//                            tdeclare.put("setcode",data_aggr.getSETCODE());//数据集编码
//                            tdeclare.put("datanum",tList.size());//数据集总数据量
//                            totaldeclare.put("tdeclare",tdeclare);
//                            totaldeclares.add(totaldeclare);
//                            tparams.put("totaldeclare",totaldeclares);
//                            //声明批次--请求
//                            String trs = yyhbw.getTotalDeclare(tparams,ptsjlicence);
//                            //请求成功
//                            if(trs != null && yyhbw.checkBw(trs)){
//                                String ttaskid = yyhbw.getTaskId(trs);
//                                //单任务号获取 S00KVd6itp5dPkw75bAhgTm
//                                for(Integer skey : sMap.keySet()){
//                                    List<Map<String,Object>> sList = sMap.get(skey);
//                                    Map<String,Object> sparams = new HashMap<>();
//                                    List<Map<String,Object>> singledeclares = new ArrayList<>();
//                                    Map<String,Object> singledeclare = new HashMap<>();
//                                    singledeclare.put("totaltaskid",ttaskid);
//                                    singledeclare.put("colrescode",data_aggr.getCOLRESCODE());
//                                    singledeclare.put("sn",skey);
//                                    Map<String,Object> declare = new HashMap<>();
//                                    declare.put("setcode",data_aggr.getSETCODE());
//                                    declare.put("datanum",sList.size());
//                                    singledeclare.put("declare",declare);
//                                    singledeclares.add(singledeclare);
//                                    sparams.put("singledeclare",singledeclares);
//                                    //单任务-请求
//                                    String srs = yyhbw.getSingleDeclare(sparams,ptsjlicence);
//                                    //成功
//                                    if(srs != null && yyhbw.checkBw(srs)){
//                                        String staskid = yyhbw.getTaskId(srs);
//                                        //数据采集上传
//                                        Map<String,Object> uparams = new HashMap<>();
//                                        uparams.put("standardcode",data_aggr.getCOLRESCODE());
//                                        uparams.put("daqtaskid",staskid);
//                                        Map<String,Object> dmp = new HashMap<>();
//                                        Map<String,Object> datasets = new HashMap<>();
//                                        datasets.put("setcode",data_aggr.getSETCODE());
//                                        datasets.put("settype","");
//                                        datasets.put("setdetails",sList);
//                                        dmp.put("datasets",datasets);
//                                        uparams.put("dmp",dmp);
//                                        //东软上传休眠时间，通过配置开启和关闭
//                                        if(drsleep){
//                                            //增量上传时，控制同一业务上传调用间隔不超过5分钟，不同业务不超过1分钟
//                                            if(taskMap != null && taskMap.containsKey("colrescode") && isAutoUpload){
//                                                Long currenttime = (Long) taskMap.get("currenttime");
//                                                Long n = System.currentTimeMillis();
//                                                Long det = (taskMap.get("colrescode")+"").equals(data_aggr.getCOLRESCODE())
//                                                        ? 5*60*1000 - (n - currenttime) : 1*60*1000 - (n - currenttime);
//                                                if(det > 0){//线程sleep剩余时间
//                                                    Thread.sleep(det);
//                                                }
//                                            }
//                                        }
//                                        //上传数据
//                                        String urs = yyhbw.uploadData(uparams,httpSign,ptsjlicence);
//                                        Date d_endDate = new Date();
//                                        long time = (d_endDate.getTime()-beginDate.getTime())/1000;
//                                        logger.info(data_aggr.getCOLRESCODE()+" ttaskid:"+ttaskid+"上传完成，记录时间："+sdf.format(d_endDate)+"本次上传总共耗费："+time+"秒");
//                                        if(taskMap != null){
//                                            taskMap.put("colrescode",data_aggr.getCOLRESCODE());
//                                            taskMap.put("currenttime",System.currentTimeMillis());
//                                        }
//                                        if(urs != null && yyhbw.checkBw(urs)){
////                                            System.out.println("success");
//                                            String etaskid = yyhbw.getTaskId(urs);
//                                            //保存上传记录
//                                            YYH_AGGR_RECORD record = new YYH_AGGR_RECORD();
//                                            record.setBEGINDATETIME(begindate);
//                                            record.setCOLRESCODE(data_aggr.getCOLRESCODE());
//                                            record.setENDDATETIME(enddate);
//                                            record.setENDTASKID(etaskid);
//                                            record.setSETCODE(data_aggr.getSETCODE());
//                                            record.setSINGLEDATANUM(Long.parseLong(sList.size()+""));
//                                            record.setSINGLETASKID(staskid);
//                                            record.setSN(Long.parseLong(skey+""));
//                                            record.setTASKNUM(Long.parseLong(sMap.size()+""));
//                                            record.setTOTALDATANUM(Long.parseLong(tList.size()+""));
//                                            record.setTOTALTASKID(ttaskid);//+"^"+ip.getHostAddress()
//                                            record.setUPLOADTIME(new Date());
//                                            record.setCLIENTID(client.getCLIENTID());
////                                            baseDao.save(record);
//                                            if(iSysDynamicSourceService.getDynamicSqlMapper(yyhaggrreordMapper,DBID,record.getENDTASKID())!=null){
//                                                iSysDynamicSourceService.dynamicSqlMapper(yyhaggrreordMapper,DBID,record);
//                                            }else{
//                                                iSysDynamicSourceService.dynamicSqlMapperSave(yyhaggrreordMapper,DBID,record);
//                                            }
//                                            if(isAutoUpload){
//                                                //更新执行时间
////                                                Map<String,Object> params = new HashMap<>();
//                                                //这里万一东软平台有问题一直卡着会更新到之前的LASTEXECUTETIME
//                                                //比如说 开始上传是13:00 上传完成是17:00 这时候更新YYH_DATA_AGGR的
//                                                //LASTEXECUTETIME还是13:00 这样会导致重传  核酸报告延迟率增加
//                                                //还是按照以前 数据量不会少
////                                                params.put("now",ld_enddate_par);
////                                                params.put("now",new Date());
////                                                params.put("COLRESCODE",data_aggr.getCOLRESCODE());
//                                                YYH_DATA_AGGR record1 = new YYH_DATA_AGGR();
//                                                record1.setLASTEXECUTETIME(ld_enddate_par);
//                                                record1.setCOLRESCODE(data_aggr.getCOLRESCODE());
////                                                aggrLogService.saveLastModifyTime(params,baseDao);
//                                                iSysDynamicSourceService.dynamicSqlMapper(yyhdataaggrMapper,DBID,record1);
//                                            }
//                                            //手动提交
//                                            aggrLogService.savePushResult(sList,ttaskid,staskid,data_aggr,"1");
//                                            logger.info(data_aggr.getCOLRESCODE()+"上传成功");
//                                            result.put(data_aggr.getCOLRESCODE(),true);
//                                        }else {
//                                            aggrLogService.savePushResult(sList,ttaskid,staskid,data_aggr,"2");
//                                            logger.error(data_aggr.getCOLRESCODE()+"上传失败，ttaskid:"+ttaskid+"，总量："+sList.size());
//                                            result.put(data_aggr.getCOLRESCODE(),false);
//                                        }
//                                    }
//                                }
//                            }
//                       }
//                    } else {
//                        if(isAutoUpload){
//                            //更新执行时间
////                            Map<String,Object> params = new HashMap<>();
////                            params.put("now",ld_enddate_par);
////                            params.put("COLRESCODE",data_aggr.getCOLRESCODE());
//                            YYH_DATA_AGGR record = new YYH_DATA_AGGR();
////                            aggrLogService.saveLastModifyTime(params,baseDao);
//                            record.setLASTEXECUTETIME(ld_enddate_par);
//                            record.setCOLRESCODE(data_aggr.getCOLRESCODE());
////                                                aggrLogService.saveLastModifyTime(params,baseDao);
//                            iSysDynamicSourceService.dynamicSqlMapper(yyhdataaggrMapper,DBID,record);
//                        }
//                        logger.info(data_aggr.getCOLRESCODE()+"上传失败,list为空 sql"+sql+"   sqlParams:"+sqlParams);
//                        result.put(data_aggr.getCOLRESCODE(),false);
//                        continue;
//                    }
//                }else {
//                    logger.info(data_aggr.getCOLRESCODE()+"上传失败"+"为空sql");
//                    result.put(data_aggr.getCOLRESCODE(),false);
//                    continue;
//                }
//            }catch(Exception e){
//                logger.error(data_aggr.getCOLRESCODE()+"上传失败"+e.getMessage(),e);
//                result.put(data_aggr.getCOLRESCODE(),false);
//                continue;
//            }
//        }
//        return result;
//    }
//    @Transactional(rollbackFor = {Exception.class})
//    public Map<String,Boolean> doDataAggrForPerson(DataAggrVo dataAggrVo,HttpSign httpSign,String idcard,Map<String, Object> req){
//        String ls_uuid = UUID.randomUUID().toString().replaceAll("-","");
//        YYH_CLIENT client = dataAggrVo.getClient();
//        List<YYH_DATA_AGGR> dataAggrs = dataAggrVo.getDataAggrs();
//        Map<String,Boolean> result = new HashMap<>();
//        Date now = new Date();
//        if(client == null){
//            logger.info(ls_uuid + ",请先维护医养护信息表（YYH_CLIENT）");
//            return null;
//        }
//        String databaseUrl = client.getCLIENT_DATABASE_URL();
//        String databaseUsername = client.getCLIENT_DATABASE_USERNAME();
//        String databasePassword = client.getCLIENT_DATABASE_PASSWORD();
//        if("".equals(databaseUrl) || databaseUrl == null){
//            logger.info(ls_uuid + ",请先维护医养护信息表（YYH_CLIENT-CLIENT_DATABSE_URL）-(CLIENTID="+client.getCLIENTID()+")");
//            return null;
//        }
//        if("".equals(databaseUsername) || databaseUsername == null){
//            logger.info(ls_uuid + ",请先维护医养护信息表（YYH_CLIENT-CLIENT_DATABSE_USERNAME）-(CLIENTID="+client.getCLIENTID()+")");
//            return null;
//        }
//        if("".equals(databasePassword) || databasePassword == null){
//            logger.info(ls_uuid + ",请先维护医养护信息表（YYH_CLIENT-CLIENT_DATABSE_PASSWORD）-(CLIENTID="+client.getCLIENTID()+")");
//            return null;
//        }
//        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
//        //前一天
//        Calendar c = Calendar.getInstance();
//        if(null == idcard) {
//            c.add(Calendar.DAY_OF_YEAR, -1);
//        }else{
//            //如果是个人数据补传，则时间为一周
//            c.add(Calendar.DAY_OF_YEAR, -7);
//        }
//        String begindate = sf1.format(c.getTime());
//        String enddate = sf1.format(now);
//        String IDKEY = (String) req.get("IDKEY");
//        String BUSKEY = (String) req.get("BUSKEY");
//        String BUSValue = (String) req.get("BUSVALUE");
//        YyhbwjxUtil yyhbw = new YyhbwjxUtil(client);
//        for(YYH_DATA_AGGR data_aggr : dataAggrs){
//            logger.info(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+"-开始上传");
//            try{
//                String sql = data_aggr.getSQLCONTENT().trim();
//                if(sql != null && !"".equals(sql)){
//                    Map<String,Object> sqlParams = new HashMap<>();
//                    sqlParams.put("begindate",c.getTime());
//                    sqlParams.put("enddate",now);
//
//                    String querySql = "select '' as SERIALNUM_ID,'' as TASK_ID,'' as BATCH_NUM,'' as LOCAL_ID,'' as BUSINESS_ID,'' as BASIC_ACTIVE_ID,'' as DATAGENERATE_DATE,'' as ORGANIZATION_CODE,'' as ORGANIZATION_NAME,'' as DOMAIN_CODE,'' as CREATE_DATE,'' as UPDATE_DATE,'' as ARCHIVE_DATE,'' as RECORD_IDEN,t.* from (\n"+sql+"\n) t";
//                    if(querySql.indexOf(":begindate") == -1){
//                        logger.error(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+":sql缺少入参begindate");
//                        result.put(data_aggr.getCOLRESCODE(),false);
//                        continue;
//                    }
//                    if(querySql.indexOf(":enddate") == -1){
//                        logger.error(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+":sql缺少入参enddate");
//                        result.put(data_aggr.getCOLRESCODE(),false);
//                        continue;
//                    }
//                    querySql = querySql + " where t."+IDKEY+"='"+idcard+"' and t."+BUSKEY+"='"+BUSValue+"'";
////                    List<Map<String,Object>> list = getYyhData(querySql,sqlParams,databaseUrl,databaseUsername,databasePassword);
//                    List<Map<String,Object>> list = iSysDynamicSourceService.dynamicReportList(querySql,client.getCLIENTID()+"");
//                    if(null == list||list.isEmpty()){
//                        logger.error(ls_uuid + ",-=-=-=-=-=-=补传无数据-=-=-=-=-=-="+querySql+"，条件："+sqlParams);
//                        continue;
//                    }else{
//                        logger.info(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+"，核酸报告数据上传量："+list.size());
//                    }
//                    if(list != null && list.size() > 0){
//                        Date beginDate = new Date();
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        logger.info(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+"开始准备上传，记录时间："+sdf.format(beginDate));
//                        Map<Integer,List<Map<String,Object>>> tMap = getSubList(list,totalSize);//分批次
//                        for(Integer key : tMap.keySet()){
//                            List<Map<String,Object>> tList = tMap.get(key);
//                            Map<Integer,List<Map<String,Object>>> sMap = getSubList(tList,singleSize);//分单任务
//                            Map<String,Object> tparams = new HashMap<>();
//                            List<Map<String,Object>> totaldeclares = new ArrayList<>();
//                            Map<String,Object> totaldeclare = new HashMap<>();
//                            totaldeclare.put("colrescode",data_aggr.getCOLRESCODE());//交换标准编码
//                            totaldeclare.put("tasknum",sMap.size());//任务数
//                            totaldeclare.put("begindatetime",begindate);//查询开始时间
//                            totaldeclare.put("enddatetime",enddate);//查询结束时间
//                            Map<String,Object> tdeclare = new HashMap<>();
//                            tdeclare.put("setcode",data_aggr.getSETCODE());//数据集编码
//                            tdeclare.put("datanum",tList.size());//数据集总数据量
//                            totaldeclare.put("tdeclare",tdeclare);
//                            totaldeclares.add(totaldeclare);
//                            tparams.put("totaldeclare",totaldeclares);
//                            //声明批次--请求
//                            String trs = yyhbw.getTotalDeclare(tparams,ptsjlicence);
//                            //请求成功
//                            if(trs != null && yyhbw.checkBw(trs)){
//                                String ttaskid = yyhbw.getTaskId(trs);
//                                for(Integer skey : sMap.keySet()){
//                                    List<Map<String,Object>> sList = sMap.get(skey);
//                                    Map<String,Object> sparams = new HashMap<>();
//                                    List<Map<String,Object>> singledeclares = new ArrayList<>();
//                                    Map<String,Object> singledeclare = new HashMap<>();
//                                    singledeclare.put("totaltaskid",ttaskid);
//                                    singledeclare.put("colrescode",data_aggr.getCOLRESCODE());
//                                    singledeclare.put("sn",skey);
//                                    Map<String,Object> declare = new HashMap<>();
//                                    declare.put("setcode",data_aggr.getSETCODE());
//                                    declare.put("datanum",sList.size());
//                                    singledeclare.put("declare",declare);
//                                    singledeclares.add(singledeclare);
//                                    sparams.put("singledeclare",singledeclares);
//                                    //单任务-请求
//                                    String srs = yyhbw.getSingleDeclare(sparams,ptsjlicence);
//                                    //成功
//                                    if(srs != null && yyhbw.checkBw(srs)){
//                                        String staskid = yyhbw.getTaskId(srs);
//                                        //数据采集上传
//                                        Map<String,Object> uparams = new HashMap<>();
//                                        uparams.put("standardcode",data_aggr.getCOLRESCODE());
//                                        uparams.put("daqtaskid",staskid);
//                                        Map<String,Object> dmp = new HashMap<>();
//                                        Map<String,Object> datasets = new HashMap<>();
//                                        datasets.put("setcode",data_aggr.getSETCODE());
//                                        datasets.put("settype","");
//                                        datasets.put("setdetails",sList);
//                                        dmp.put("datasets",datasets);
//                                        uparams.put("dmp",dmp);
//                                        //上传数据
//                                        String urs = yyhbw.uploadData(uparams,httpSign,ptsjlicence);
//                                        Date endDate = new Date();
//                                        long time = (endDate.getTime()-beginDate.getTime())/1000;
//                                        logger.info(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+" ttaskid:"+ttaskid+"上传完成，记录时间："+sdf.format(endDate)+"本次上传总共耗费："+time+"秒");
//                                        if(urs != null && yyhbw.checkBw(urs)){
//                                            String etaskid = yyhbw.getTaskId(urs);
//                                            //保存上传记录
//                                            YYH_AGGR_RECORD record = new YYH_AGGR_RECORD();
//                                            record.setBEGINDATETIME(begindate);
//                                            record.setCOLRESCODE(data_aggr.getCOLRESCODE());
//                                            record.setENDDATETIME(enddate);
//                                            record.setENDTASKID(etaskid);
//                                            record.setSETCODE(data_aggr.getSETCODE());
//                                            record.setSINGLEDATANUM(Long.parseLong(sList.size()+""));
//                                            record.setSINGLETASKID(staskid);
//                                            record.setSN(Long.parseLong(skey+""));
//                                            record.setTASKNUM(Long.parseLong(sMap.size()+""));
//                                            record.setTOTALDATANUM(Long.parseLong(tList.size()+""));
//                                            record.setTOTALTASKID(ttaskid);//+"^"+ip.getHostAddress()
//                                            record.setUPLOADTIME(new Date());
//                                            record.setCLIENTID(client.getCLIENTID());
////                                            baseDao.save(record);
//                                            if(iSysDynamicSourceService.getDynamicSqlMapper(yyhaggrreordMapper,DBID,record.getENDTASKID())!=null){
//                                                iSysDynamicSourceService.dynamicSqlMapper(yyhaggrreordMapper,DBID,record);
//                                            }else{
//                                                iSysDynamicSourceService.dynamicSqlMapperSave(yyhaggrreordMapper,DBID,record);
//                                            }
//                                            //手动提交
//                                            aggrLogService.savePushResult(sList,ttaskid,staskid,data_aggr,"1");
//                                            logger.info(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+"上传成功");
//                                            result.put(data_aggr.getCOLRESCODE(),true);
//                                        }else {
//                                            aggrLogService.savePushResult(sList,ttaskid,staskid,data_aggr,"2");
//                                            logger.error(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+"上传失败，ttaskid:"+ttaskid+"，总量："+sList.size()+"\n原文："+urs);
//                                            result.put(data_aggr.getCOLRESCODE(),false);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }else {
//                    logger.info(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+"上传失败,为空sql");
//                    result.put(data_aggr.getCOLRESCODE(),false);
//                    continue;
//                }
//            }catch(Exception e){
//                logger.error(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+"上传失败"+e.getMessage(),e);
//                result.put(data_aggr.getCOLRESCODE(),false);
//                continue;
//            }
//        }
//        return result;
//    }
//    @Transactional(rollbackFor = {Exception.class})
//    public Map<String,Boolean> doDataAggrForHsbg(Map<String,Object> taskMap, DataAggrVo dataAggrVo,HttpSign httpSign,String idcard){
//        String ls_uuid = UUID.randomUUID().toString().replaceAll("-","");
//        Map<String,Boolean> result = new HashMap<>();
//        Date now = new Date();
//        YYH_CLIENT client = dataAggrVo.getClient();
//        if(!checkClientConfig(client,ls_uuid)){
//            return null;
//        }
//        String databaseUrl = client.getCLIENT_DATABASE_URL();
//        String databaseUsername = client.getCLIENT_DATABASE_USERNAME();
//        String databasePassword = client.getCLIENT_DATABASE_PASSWORD();
//
//        List<YYH_DATA_AGGR> dataAggrs = dataAggrVo.getDataAggrs();
//        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
//        //前一天
//        Calendar c = Calendar.getInstance();
//        if(null == idcard) {
////            c.add(Calendar.DAY_OF_YEAR, -1);
//            //取配置时间
//            c.add(Calendar.MINUTE,(hsbgaddm <= 0)?-7:-hsbgaddm);
//        }else{
//            //如果是个人数据补传，则时间为一周
//            c.add(Calendar.DAY_OF_YEAR, -7);
//        }
//       String begindate = sf1.format(c.getTime());
//       String enddate = sf1.format(now);
//
//        YyhbwjxUtil yyhbw = new YyhbwjxUtil(client);
//        for(YYH_DATA_AGGR data_aggr : dataAggrs){
//            logger.info(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+"-开始上传");
//            try{
//                String sql = data_aggr.getSQLCONTENT().trim();
//                if(sql != null && !"".equals(sql)){
//                    Map<String,Object> sqlParams = new HashMap<>();
//                    sqlParams.put("begindate",c.getTime());
//                    sqlParams.put("enddate",now);
//
//                    String querySql = "select '' as SERIALNUM_ID,'' as TASK_ID,'' as BATCH_NUM,'' as LOCAL_ID,'' as BUSINESS_ID,'' as BASIC_ACTIVE_ID,'' as DATAGENERATE_DATE,'' as ORGANIZATION_CODE,'' as ORGANIZATION_NAME,'' as DOMAIN_CODE,'' as CREATE_DATE,'' as UPDATE_DATE,'' as ARCHIVE_DATE,'' as RECORD_IDEN,t.* from (\n"+sql+"\n) t";
//                    if(querySql.indexOf(":begindate") == -1){
//                        logger.error(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+":sql缺少入参begindate");
//                        result.put(data_aggr.getCOLRESCODE(),false);
//                        continue;
//                    }
//                    if(querySql.indexOf(":enddate") == -1){
//                        logger.error(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+":sql缺少入参enddate");
//                        result.put(data_aggr.getCOLRESCODE(),false);
//                        continue;
//                    }
//                    //添加指定参数
//                    if(data_aggr.getCOLRESCODE().equals("REQ.C0101.0303.01")){
//                        querySql = querySql +" where t."+hsbgsqd+" like '"+hsbgmc+"%'";
//                    }else if(data_aggr.getCOLRESCODE().equals("REQ.C0101.0303.02")){
//                        querySql = querySql +" where t."+hsbgjyd+" like '"+hsbgmc+"%'";
//                    }else if(data_aggr.getCOLRESCODE().equals("REQ.C0101.0303.0201")){
//                        querySql = querySql +" where t."+hsbgmxd+" like '"+hsbgmc+"%'";
//                    }else{
//                        logger.error(ls_uuid + ",数据集：" +"未知，结束上传。");
//                        return null;
//                    }
//                    if(null != idcard){
//                        if(data_aggr.getCOLRESCODE().equals("REQ.C0101.0303.0201")){
//                            querySql = querySql + " and t.WS02_01_906_01 ='"+idcard+"'";
//                        }else{
//                            querySql = querySql + " and t.WS02_01_030_01 ='"+idcard+"'";
//                        }
//                        logger.error(ls_uuid + ",个人补传数据："+idcard);
//                    }
////                    List<Map<String,Object>> list = getYyhData(querySql,sqlParams,databaseUrl,databaseUsername,databasePassword);
//                    List<Map<String,Object>> list = iSysDynamicSourceService.dynamicReportList(querySql,client.getCLIENTID()+"");
//                    if(null == list||list.isEmpty()){
//                        logger.error(ls_uuid + ",-=-=-=-=-=-=补传无数据-=-=-=-=-=-="+querySql+"，条件："+sqlParams);
//                        continue;
//                    }else{
//                        logger.info(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+"，核酸报告数据上传量："+list.size());
//                    }
//                    if(list != null && list.size() > 0){
//                        Date beginDate = new Date();
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        logger.info(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+"开始准备上传，记录时间："+sdf.format(beginDate));
//                        Map<Integer,List<Map<String,Object>>> tMap = getSubList(list,totalSize);//分批次
//                        for(Integer key : tMap.keySet()){
//                            List<Map<String,Object>> tList = tMap.get(key);
//                            Map<Integer,List<Map<String,Object>>> sMap = getSubList(tList,singleSize);//分单任务
//                            Map<String,Object> tparams = new HashMap<>();
//                            List<Map<String,Object>> totaldeclares = new ArrayList<>();
//                            Map<String,Object> totaldeclare = new HashMap<>();
//                            totaldeclare.put("colrescode",data_aggr.getCOLRESCODE());//交换标准编码
//                            totaldeclare.put("tasknum",sMap.size());//任务数
//                            totaldeclare.put("begindatetime",begindate);//查询开始时间
//                            totaldeclare.put("enddatetime",enddate);//查询结束时间
//                            Map<String,Object> tdeclare = new HashMap<>();
//                            tdeclare.put("setcode",data_aggr.getSETCODE());//数据集编码
//                            tdeclare.put("datanum",tList.size());//数据集总数据量
//                            totaldeclare.put("tdeclare",tdeclare);
//                            totaldeclares.add(totaldeclare);
//                            tparams.put("totaldeclare",totaldeclares);
//                            //声明批次--请求
//                            String trs = yyhbw.getTotalDeclare(tparams,ptsjlicence);
//                            //请求成功
//                            if(trs != null && yyhbw.checkBw(trs)){
//                                String ttaskid = yyhbw.getTaskId(trs);
//                                for(Integer skey : sMap.keySet()){
//                                    List<Map<String,Object>> sList = sMap.get(skey);
//                                    Map<String,Object> sparams = new HashMap<>();
//                                    List<Map<String,Object>> singledeclares = new ArrayList<>();
//                                    Map<String,Object> singledeclare = new HashMap<>();
//                                    singledeclare.put("totaltaskid",ttaskid);
//                                    singledeclare.put("colrescode",data_aggr.getCOLRESCODE());
//                                    singledeclare.put("sn",skey);
//                                    Map<String,Object> declare = new HashMap<>();
//                                    declare.put("setcode",data_aggr.getSETCODE());
//                                    declare.put("datanum",sList.size());
//                                    singledeclare.put("declare",declare);
//                                    singledeclares.add(singledeclare);
//                                    sparams.put("singledeclare",singledeclares);
//                                    //单任务-请求
//                                    String srs = yyhbw.getSingleDeclare(sparams,ptsjlicence);
//                                    //成功
//                                    if(srs != null && yyhbw.checkBw(srs)){
//                                        String staskid = yyhbw.getTaskId(srs);
//                                        //数据采集上传
//                                        Map<String,Object> uparams = new HashMap<>();
//                                        uparams.put("standardcode",data_aggr.getCOLRESCODE());
//                                        uparams.put("daqtaskid",staskid);
//                                        Map<String,Object> dmp = new HashMap<>();
//                                        Map<String,Object> datasets = new HashMap<>();
//                                        datasets.put("setcode",data_aggr.getSETCODE());
//                                        datasets.put("settype","");
//                                        datasets.put("setdetails",sList);
//                                        dmp.put("datasets",datasets);
//                                        uparams.put("dmp",dmp);
//                                        //上传数据
//                                        String urs = yyhbw.uploadData(uparams,httpSign,ptsjlicence);
//                                        Date endDate = new Date();
//                                        long time = (endDate.getTime()-beginDate.getTime())/1000;
//                                        logger.info(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+" ttaskid:"+ttaskid+"上传完成，记录时间："+sdf.format(endDate)+"本次上传总共耗费："+time+"秒");
//                                        if(urs != null && yyhbw.checkBw(urs)){
//                                            String etaskid = yyhbw.getTaskId(urs);
//                                            //保存上传记录
//                                            YYH_AGGR_RECORD record = new YYH_AGGR_RECORD();
//                                            record.setBEGINDATETIME(begindate);
//                                            record.setCOLRESCODE(data_aggr.getCOLRESCODE());
//                                            record.setENDDATETIME(enddate);
//                                            record.setENDTASKID(etaskid);
//                                            record.setSETCODE(data_aggr.getSETCODE());
//                                            record.setSINGLEDATANUM(Long.parseLong(sList.size()+""));
//                                            record.setSINGLETASKID(staskid);
//                                            record.setSN(Long.parseLong(skey+""));
//                                            record.setTASKNUM(Long.parseLong(sMap.size()+""));
//                                            record.setTOTALDATANUM(Long.parseLong(tList.size()+""));
//                                            record.setTOTALTASKID(ttaskid);//+"^"+ip.getHostAddress()
//                                            record.setUPLOADTIME(new Date());
//                                            record.setCLIENTID(client.getCLIENTID());
////                                            baseDao.save(record);
//                                            if(iSysDynamicSourceService.getDynamicSqlMapper(yyhaggrreordMapper,DBID,record.getENDTASKID())!=null){
//                                                iSysDynamicSourceService.dynamicSqlMapper(yyhaggrreordMapper,DBID,record);
//                                            }else{
//                                                iSysDynamicSourceService.dynamicSqlMapperSave(yyhaggrreordMapper,DBID,record);
//                                            }
//                                            aggrLogService.savePushResult(sList,ttaskid,staskid,data_aggr,"1");
//                                            logger.info(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+"上传成功");
//                                            result.put(data_aggr.getCOLRESCODE(),true);
//                                        }else {
//                                            aggrLogService.savePushResult(sList,ttaskid,staskid,data_aggr,"2");
//                                            logger.error(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+"上传失败，ttaskid:"+ttaskid+"，总量："+sList.size()+"\n原文："+urs);
//                                            result.put(data_aggr.getCOLRESCODE(),false);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }else {
//                    logger.info(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+"上传失败,为空sql");
//                    result.put(data_aggr.getCOLRESCODE(),false);
//                    continue;
//                }
//            }catch(Exception e){
//                logger.error(ls_uuid + ",数据集：" +data_aggr.getCOLRESCODE()+"上传失败"+e.getMessage(),e);
//                result.put(data_aggr.getCOLRESCODE(),false);
//                continue;
//            }
//        }
//        return result;
//    }
//
//    /**
//     * 数据对账任务执行
//     * @param httpSign
//     * @return
//     */
//    @Transactional(rollbackFor = {Exception.class})
//    public Map<String,Boolean> doDataAggrForSjdz(Map<String,Object> mapQuery,HttpSign httpSign,YYH_CLIENT yyh_dzclient,YYH_CLIENT yyh_main){
//        String ls_uuid = UUID.randomUUID().toString().replaceAll("-","");
//        Map<String,Boolean> result = new HashMap<>();
//
//        if(!checkClientConfig(yyh_dzclient,ls_uuid)){
//            return null;
//        }
//        String databaseUrl = yyh_dzclient.getCLIENT_DATABASE_URL();
//        String databaseUsername = yyh_dzclient.getCLIENT_DATABASE_USERNAME();
//        String databasePassword = yyh_dzclient.getCLIENT_DATABASE_PASSWORD();
//
//        YyhbwjxUtil yyhbw = new YyhbwjxUtil(yyh_dzclient);
//        yyhbw.setSJDZURL(sjdzurl);
//        yyhbw.setSJDZ_SERVICECODE(sjdzcode);
//        logger.info(ls_uuid + ",数据集：" +mapQuery.get("SETCODE")+"-开始对账上传");
//        try{
//            String sql = mapQuery.get("SQLCONTENT").toString().trim();
//            String query_column = mapQuery.get("QSQL").toString().trim();
//            String pkey_column = mapQuery.get("QKEY").toString().trim();
//            if(sql != null && !"".equals(sql)) {
//                Map<String, Object> sqlParams = new HashMap<>();
//                sqlParams.put("begindate", mapQuery.get("MZHSTART"));
//                sqlParams.put("enddate", mapQuery.get("MZHEND"));
//                String begindate = DateUtil.format((Date) mapQuery.get("MZHSTART"), "yyyyMMddHHmmss");
//                String enddate = DateUtil.format((Date) mapQuery.get("MZHEND"), "yyyyMMddHHmmss");
//
//                boolean lb_muljgid = false;
//                List<Map<String, Object>> list_jgids = null;
//                String ls_jgdz = "select WS08_10_052_01,CT08_10_052_01,JGID from gy_yyhjgdz where status = 1";
//                //通过异常捕获来判断是否是多机构版本
//                try {
//                    //仅从主库中获取配置
//                    //list_jgids =getSQLData(ls_jgdz,sqlParams, yyh_main.getCLIENT_DATABASE_URL(), yyh_main.getCLIENT_DATABASE_USERNAME(), yyh_main.getCLIENT_DATABASE_PASSWORD());
//                    list_jgids = iSysDynamicSourceService.dynamicReportList(ls_jgdz,yyh_main.getCLIENTID()+"");
//                    if(null != list_jgids && list_jgids.size()>1){
//                        lb_muljgid = true;
//                    }
//                } catch (Exception e) {
//                }
//                String querySql = "select '' as SERIALNUM_ID,'' as TASK_ID,'' as BATCH_NUM,'' as LOCAL_ID,'' as BUSINESS_ID,'' as BASIC_ACTIVE_ID,'' as DATAGENERATE_DATE,'' as ORGANIZATION_CODE,'' as ORGANIZATION_NAME,'' as DOMAIN_CODE,'' as CREATE_DATE,'' as UPDATE_DATE,'' as ARCHIVE_DATE,'' as RECORD_IDEN," + query_column + " as MVAL from (\n" + sql.trim() + "\n) t";
//                if (querySql.indexOf(":begindate") == -1) {
//                    logger.error(ls_uuid + ",数据集：" + mapQuery.get("SETCODE") + ":sql缺少入参begindate");
//                    result.put(mapQuery.get("SETCODE") + "", false);
//                } else if (querySql.indexOf(":enddate") == -1) {
//                    logger.error(ls_uuid + ",数据集：" + mapQuery.get("SETCODE") + ":sql缺少入参enddate");
//                    result.put(mapQuery.get("SETCODE") + "", false);
//                }else{
//                    if(lb_muljgid && list_jgids.size() >1){
//                        for(Map<String, Object> map_jgid : list_jgids){
//                            try {
//                                String  querySqlArea = querySql + " where t." ;
//                                String dzJgid = "AGGRJGIDM";
//                                //如果在aggr里面配置过AGGRJGIDM的值 就取对应的机构代码的id
//                                if(mapQuery.get("AGGRJGIDM")!=null && !"".equals(mapQuery.get("AGGRJGIDM")+"")){
//                                    dzJgid = mapQuery.get("AGGRJGIDM")+"";
//                                }
//                                String ls_jgid = map_jgid.get("WS08_10_052_01")+"";
//                                querySqlArea+=dzJgid+" ='"+ls_jgid+"'";
////                                List<Map<String, Object>> list = getYyhData(querySqlArea, sqlParams, databaseUrl, databaseUsername, databasePassword);
//                                List<Map<String,Object>> list = iSysDynamicSourceService.dynamicReportList(querySql,yyh_dzclient.getCLIENTID()+"");
//                                logger.info(ls_uuid + ",数据集：" + mapQuery.get("SETCODE")+"，机构【"+ls_jgid + "】对账数据上传量：" + list.size());
//                                Date beginDate = new Date();
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////                            logger.info(ls_uuid + ",数据集：" + mapQuery.get("SETCODE") + "开始准备上传，记录时间：" + sdf.format(beginDate));
//                                Map<String, Object> tparams = new HashMap<>();
//                                List<Map<String, Object>> totaldeclares = new ArrayList<>();
//                                Map<String, Object> totaldeclare = new HashMap<>();
//                                totaldeclare.put("colrescode", mapQuery.get("COLRESCODE"));//交换标准编码
//                                totaldeclare.put("tasknum", 1);//任务数
//                                totaldeclare.put("begindatetime", begindate);//查询开始时间
//                                totaldeclare.put("enddatetime", enddate);//查询结束时间
//                                Map<String, Object> tdeclare = new HashMap<>();
//                                tdeclare.put("setcode", mapQuery.get("SETCODE"));//数据集编码
//                                tdeclare.put("datanum", 1);//数据集总数据量
//                                totaldeclare.put("tdeclare", tdeclare);
//                                totaldeclares.add(totaldeclare);
//                                tparams.put("totaldeclare", totaldeclares);
//                                //数据采集上传
//                                Map<String, Object> uparams = new HashMap<>();
//                                uparams.put("standardcode", mapQuery.get("SCODE"));
//                                String ls_now_time = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
//                                uparams.put("daqtaskid", ls_now_time);
//                                Map<String, Object> dmp = new HashMap<>();
//                                Map<String, Object> datasets = new HashMap<>();
//                                datasets.put("setcode", mapQuery.get("SETCODE"));
//                                datasets.put("settype", "");
//
//                                HashMap<String,String> req_busdata = new HashMap<String,String>();
//                                //拼接primary_value
//                                StringBuffer sb_info = new StringBuffer();
//                                for(Map<String, Object> sresult : list){
//                                    sb_info.append(sresult.get("MVAL").toString()).append(";");
//                                }
//                                req_busdata.put("organization_code",ls_jgid);
//                                req_busdata.put("organization_name",map_jgid.get("CT08_10_052_01")+"");
//                                req_busdata.put("dataset_code",mapQuery.get("SETCODE")+"");
//                                req_busdata.put("data_num",list.size()+"");
//                                req_busdata.put("biz_date",DateUtil.format((Date) mapQuery.get("MZHSTART"), "yyyyMMdd"));
//                                req_busdata.put("primary_key",pkey_column);
//                                req_busdata.put("primary_value",sb_info.toString());
//                                req_busdata.put("upload_time",ls_now_time);
//                                req_busdata.put("is_append","0");
//                                datasets.put("setdetails", req_busdata);
//
//                                dmp.put("datasets", datasets);
//                                uparams.put("dmp", dmp);
//                                //上传数据
//                                String urs = yyhbw.uploadSJZDData(uparams, httpSign, ptsjlicence);
//                                Date endDate = new Date();
//                                long time = (endDate.getTime() - beginDate.getTime()) / 1000;
//                                logger.info(ls_uuid + ",数据集：" + mapQuery.get("SETCODE") +"，机构【"+ls_jgid + "】对账完成，记录时间：" + sdf.format(endDate) + "本次上传总共耗费：" + time + "秒");
//                                if (urs != null && yyhbw.checkBw(urs)) {
//                                    //保存上传记录
//                                    YYH_AGGR_RECORD record = new YYH_AGGR_RECORD();
//                                    record.setBEGINDATETIME(begindate);
//                                    record.setCOLRESCODE(mapQuery.get("SETCODE")+"");
//                                    record.setENDDATETIME(enddate);
//                                    //主键...
//                                    record.setENDTASKID("sjdz"+DateUtil.format(new Date(), "yyyyMMddHHmmss")+String.format("%04d", new Random().nextInt(9999)));
//                                    record.setSETCODE(mapQuery.get("SETCODE")+"");
//                                    record.setSINGLEDATANUM(Long.parseLong(list.size() + ""));
//                                    record.setSINGLETASKID(ls_now_time);
//                                    record.setSN(Long.parseLong("1"));
//                                    record.setTASKNUM(Long.parseLong(list.size() + ""));
//                                    record.setTOTALDATANUM(Long.parseLong(list.size() + ""));
//                                    record.setTOTALTASKID("xxx");
//                                    record.setUPLOADTIME(new Date());
//                                    record.setCLIENTID(yyh_dzclient.getCLIENTID());
//                                    record.setORGANIZATION_CODE(ls_jgid);
//                                    record.setORGANIZATION_NAME(map_jgid.get("CT08_10_052_01")+"");
////                                    baseDao.save(record);
//                                    if(iSysDynamicSourceService.getDynamicSqlMapper(yyhaggrreordMapper,DBID,record.getENDTASKID())!=null){
//                                        iSysDynamicSourceService.dynamicSqlMapper(yyhaggrreordMapper,DBID,record);
//                                    }else{
//                                        iSysDynamicSourceService.dynamicSqlMapperSave(yyhaggrreordMapper,DBID,record);
//                                    }
//                                } else {
//                                    logger.error(ls_uuid + ",数据集：" + mapQuery.get("SETCODE") +"，机构【"+ls_jgid + "】对账返回失败。");
//                                }
//                            } catch (Exception e) {
//                              logger.error(ls_uuid + ",分机构对账，存在异常："+e.getMessage(),e);
//                            }
//                        }
//                    }
//                    //区域总账对账 & 单机构对账
//                    if (sjdzflag) {
////                        List<Map<String, Object>> list = getYyhData(querySql, sqlParams, databaseUrl, databaseUsername, databasePassword);
//                        List<Map<String,Object>> list = iSysDynamicSourceService.dynamicReportList(querySql,yyh_dzclient.getCLIENTID()+"");
//                        logger.info(ls_uuid + ",数据集：" + mapQuery.get("SETCODE") + "，对账数据上传量：" + list.size());
//                        Date beginDate = new Date();
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////                        logger.info(ls_uuid + ",数据集：" + mapQuery.get("SETCODE") + "开始准备上传，记录时间：" + sdf.format(beginDate));
//                        Map<String, Object> tparams = new HashMap<>();
//                        List<Map<String, Object>> totaldeclares = new ArrayList<>();
//                        Map<String, Object> totaldeclare = new HashMap<>();
//                        totaldeclare.put("colrescode", mapQuery.get("COLRESCODE"));//交换标准编码
//                        totaldeclare.put("tasknum", 1);//任务数
//                        totaldeclare.put("begindatetime", begindate);//查询开始时间
//                        totaldeclare.put("enddatetime", enddate);//查询结束时间
//                        Map<String, Object> tdeclare = new HashMap<>();
//                        tdeclare.put("setcode", mapQuery.get("SETCODE"));//数据集编码
//                        tdeclare.put("datanum", 1);//数据集总数据量
//                        totaldeclare.put("tdeclare", tdeclare);
//                        totaldeclares.add(totaldeclare);
//                        tparams.put("totaldeclare", totaldeclares);
//                        //数据采集上传
//                        Map<String, Object> uparams = new HashMap<>();
//                        uparams.put("standardcode", mapQuery.get("SCODE"));
//                        String ls_now_time = DateUtil.format(new Date(), "yyyyMMddHHmmss");
//                        uparams.put("daqtaskid", ls_now_time + "121");
//                        Map<String, Object> dmp = new HashMap<>();
//                        Map<String, Object> datasets = new HashMap<>();
//                        datasets.put("setcode", mapQuery.get("SETCODE"));
//                        datasets.put("settype", "");
//
//                        HashMap<String, String> req_busdata = new HashMap<String, String>();
//                        //拼接primary_value
//                        StringBuffer sb_info = new StringBuffer();
//                        for (Map<String, Object> sresult : list) {
//                            sb_info.append(sresult.get("MVAL").toString()).append(";");
//                        }
//                        //若是区域的，也可以配置sjdzyyid
//                        req_busdata.put("organization_code", (null == sjdzyyid || sjdzyyid.isEmpty()) ? yyh_dzclient.getSOURCEORGAN() : sjdzyyid);
//                        req_busdata.put("organization_name", sjdzyymc);
//                        req_busdata.put("dataset_code", mapQuery.get("SETCODE") + "");
//                        req_busdata.put("data_num", list.size() + "");
//                        req_busdata.put("biz_date", DateUtil.format((Date) mapQuery.get("MZHSTART"), "yyyyMMdd"));
//                        req_busdata.put("primary_key", pkey_column);
//                        req_busdata.put("primary_value", sb_info.toString());
//                        req_busdata.put("upload_time", ls_now_time);
//                        req_busdata.put("is_append", "0");
//                        datasets.put("setdetails", req_busdata);
//
//                        dmp.put("datasets", datasets);
//                        uparams.put("dmp", dmp);
//                        //上传数据
//                        String urs = yyhbw.uploadSJZDData(uparams, httpSign, ptsjlicence);
//                        Date endDate = new Date();
//                        long time = (endDate.getTime() - beginDate.getTime()) / 1000;
//                        logger.info(ls_uuid + ",数据集：" + mapQuery.get("SETCODE") + "区域对账完成，记录时间：" + sdf.format(endDate) + "本次上传总共耗费：" + time + "秒");
//                        if (urs != null && yyhbw.checkBw(urs)) {
//                            //保存上传记录
//                            YYH_AGGR_RECORD record = new YYH_AGGR_RECORD();
//                            record.setBEGINDATETIME(begindate);
//                            record.setCOLRESCODE(mapQuery.get("SETCODE") + "");
//                            record.setENDDATETIME(enddate);
//                            //主键...
//                            record.setENDTASKID("sjdz" + DateUtil.format(new Date(), "yyyyMMddHHmmss") + String.format("%04d", new Random().nextInt(9999)));
//                            record.setSETCODE(mapQuery.get("SETCODE") + "");
//                            record.setSINGLEDATANUM(Long.parseLong(list.size() + ""));
//                            record.setSINGLETASKID(ls_now_time);
//                            record.setSN(Long.parseLong("1"));
//                            record.setTASKNUM(Long.parseLong(list.size() + ""));
//                            record.setTOTALDATANUM(Long.parseLong(list.size() + ""));
//                            record.setTOTALTASKID("xxx");
//                            record.setUPLOADTIME(new Date());
//                            record.setCLIENTID(yyh_dzclient.getCLIENTID());
////                            baseDao.save(record);
//                            if(iSysDynamicSourceService.getDynamicSqlMapper(yyhaggrreordMapper,DBID,record.getENDTASKID())!=null){
//                                iSysDynamicSourceService.dynamicSqlMapper(yyhaggrreordMapper,DBID,record);
//                            }else{
//                                iSysDynamicSourceService.dynamicSqlMapperSave(yyhaggrreordMapper,DBID,record);
//                            }
//                        }
//                    }
//                }
//            }
//        }catch(Exception e){
//            logger.error(ls_uuid + ",数据集：" +mapQuery.get("SETCODE")+"对账失败"+e.getMessage(),e);
//            result.put(mapQuery.get("SETCODE")+"",false);
//        }
//        return result;
//    }
//
//    @Transactional(rollbackFor = {Exception.class})
//    public Map<String, Boolean> doDataAggrForHscj(DataAggrVo dataAggrVo) {
//        String ls_uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        YYH_CLIENT client = dataAggrVo.getClient();
//        Map<String, Boolean> result = new HashMap<>();
//        if (client == null) {
//            logger.info("请先维护医养护信息表（YYH_CLIENT）");
//            return null;
//        }
//        String databaseUrl = client.getCLIENT_DATABASE_URL();
//        String databaseUsername = client.getCLIENT_DATABASE_USERNAME();
//        String databasePassword = client.getCLIENT_DATABASE_PASSWORD();
//        if ("".equals(databaseUrl) || databaseUrl == null) {
//            logger.info("请先维护医养护信息表（YYH_CLIENT-CLIENT_DATABSE_URL）-(CLIENTID=" + client.getCLIENTID() + ")");
//            return null;
//        }
//        if ("".equals(databaseUsername) || databaseUsername == null) {
//            logger.info("请先维护医养护信息表（YYH_CLIENT-CLIENT_DATABSE_USERNAME）-(CLIENTID=" + client.getCLIENTID() + ")");
//            return null;
//        }
//        if ("".equals(databasePassword) || databasePassword == null) {
//            logger.info("请先维护医养护信息表（YYH_CLIENT-CLIENT_DATABSE_PASSWORD）-(CLIENTID=" + client.getCLIENTID() + ")");
//            return null;
//        }
//        YyhbwjxUtil yyhbw = new YyhbwjxUtil(client);
//        yyhbw.setHSCJSCURL(hscjurl);
//        yyhbw.setHSCJ_SERVICECODE(hscjcod);
//
//        List<YYH_DATA_AGGR> dataAggrs = dataAggrVo.getDataAggrs();
//        YYH_DATA_AGGR data_aggr = dataAggrs.get(0);
//        logger.info(ls_uuid + ",核酸采集-开始上传");
//        try {
//            String sql = data_aggr.getSQLCONTENT().trim();
//            if (sql != null && !"".equals(sql)) {
//                Map<String, Object> sqlParams = new HashMap<>();
//                Calendar c = Calendar.getInstance();
//                Date d_end = c.getTime();
//                c.add(Calendar.MINUTE, -hscjjgsj);
//                Date d_begin = c.getTime();
//                sqlParams.put("begindate", d_begin);
//                sqlParams.put("enddate", d_end);
//                if (sql.indexOf(":begindate") == -1) {
//                    logger.info("核酸采集sql缺少入参begindate");
//                    result.put(data_aggr.getCOLRESCODE(), false);
//                }
//                if (sql.indexOf(":enddate") == -1) {
//                    logger.info("核酸采集ql缺少入参enddate");
//                    result.put(data_aggr.getCOLRESCODE(), false);
//                }
////                List<Map<String, Object>> list = getYyhData(sql, sqlParams, databaseUrl, databaseUsername, databasePassword);
//                List<Map<String,Object>> list = iSysDynamicSourceService.dynamicReportList(sql,client.getCLIENTID()+"");
//                if (null == list || list.isEmpty()) {
//                    logger.error("无核酸数据，未上传。");
//                } else {
//                    Map<Integer,List<Map<String,Object>>> map_list = getSubList(list,100);
//                    for(Integer key : map_list.keySet()){
//                        List<Map<String,Object>> tList = map_list.get(key);
//                        Map<String, Object> dmp = new HashMap<>();
//                        Map<String, Object> datasets = new HashMap<>();
//                        datasets.put("setcode", "");
//                        datasets.put("settype", "");
//                        datasets.put("setdetails", tList);
//                        dmp.put("datasets", datasets);
//                        //上传数据
//                        String urs = yyhbw.uploadHscjMethod(dmp,hscjurl,"resourceMethod",hscjcod,ptsjlicence);
//                        if (urs != null && yyhbw.checkBw(urs)) {
//                            //TODO 模式变更
//                            logger.error("数据上传量："+tList.size());
//                        }
//                    }
//                }
//            } else {
//                logger.info("上传失败为空sql");
//                result.put(data_aggr.getCOLRESCODE(), false);
//            }
//        } catch (Exception e) {
//            logger.error("核酸采集上传异常："+e.getMessage(),e);
//            result.put(data_aggr.getCOLRESCODE(), false);
//        }
//        return result;
//    }
//
//
////    public List<Map<String,Object>> getSQLData(String querySql,Map<String,Object> sqlParams, String databaseUrl, String databaseUsername, String databasePassword) throws Exception {
////        List<Map<String,Object>> list = null;
////        try {
////            if(databaseUrl.indexOf("jdbc:oracle") != -1){//oracle
////                if(checkReadyToSleep(DataType.ORACLE)){
////                    logger.error("当前 oracle 为配置休息时间："+oracleSleep);
////                    return null;
////                }
////                JDBCForOracle jdbc = new JDBCForOracle();
////                list = jdbc.executeQuery(querySql,sqlParams,databaseUrl,databaseUsername,databasePassword);
////            }else if(databaseUrl.indexOf("jdbc:sqlserver") != -1){//sqlserver
////                if(checkReadyToSleep(DataType.MSSQL)){
////                    logger.error("当前 SQLserver 为配置休息时间："+sqlServerSleep);
////                    return null;
////                }
////                JDBCForSqlServer jdbc = new JDBCForSqlServer();
////                list = jdbc.executeQuery(querySql,sqlParams,databaseUrl,databaseUsername,databasePassword);
////            }else if(databaseUrl.indexOf("jdbc:jtds:sqlserver") != -1){
////                if(checkReadyToSleep(DataType.MSSQL)){
////                    logger.error("当前 SQLserver 为配置休息时间："+sqlServerSleep);
////                    return null;
////                }
////                JDBCForJtdsSqlServer jdbc = new JDBCForJtdsSqlServer();
////                list = jdbc.executeQuery(querySql,sqlParams,databaseUrl,databaseUsername,databasePassword);
////            }else if(databaseUrl.indexOf("jdbc:mysql") != -1) {//mysql
////                if (checkReadyToSleep(DataType.MYSQL)) {
////                    logger.error("当前 mysql 为配置休息时间：" + mysqlSleep);
////                    return null;
////                }
////                JDBCForMySql jdbc = new JDBCForMySql();
////                list = jdbc.executeQuery(querySql, sqlParams, databaseUrl, databaseUsername, databasePassword);
////            }else{
////                logger.error("无法识别的数据源地址:"+databaseUrl);
////            }
////        } catch (SQLException e) {
////            logger.error("数据检索异常："+e.getMessage(),e);
////            throw new Exception("Hello Java!");
////        }
////        return list;
////    }
//
//
////    /**
////     * 获取上传数据
////     * @param querySql
////     * @param sqlParams
////     * @param databaseUrl
////     * @param databaseUsername
////     * @param databasePassword
////     * @return
////     */
////    public List<Map<String,Object>> getYyhData(String querySql,Map<String,Object> sqlParams, String databaseUrl, String databaseUsername, String databasePassword){
////        List<Map<String,Object>> list = null;
////        try {
////            if(databaseUrl.indexOf("jdbc:oracle") != -1){//oracle
////                if(checkReadyToSleep(DataType.ORACLE)){
////                    logger.error("当前 oracle 为配置休息时间："+oracleSleep);
////                    return null;
////                }
////                JDBCForOracle jdbc = new JDBCForOracle();
////                list = jdbc.rsDataToList(querySql,sqlParams,databaseUrl,databaseUsername,databasePassword);
////            }else if(databaseUrl.indexOf("jdbc:sqlserver") != -1){//sqlserver
////                if(checkReadyToSleep(DataType.MSSQL)){
////                    logger.error("当前 SQLserver 为配置休息时间："+sqlServerSleep);
////                    return null;
////                }
////                JDBCForSqlServer jdbc = new JDBCForSqlServer();
////                list = jdbc.rsDataToList(querySql,sqlParams,databaseUrl,databaseUsername,databasePassword);
////            }else if(databaseUrl.indexOf("jdbc:jtds:sqlserver") != -1){
////                if(checkReadyToSleep(DataType.MSSQL)){
////                    logger.error("当前 SQLserver 为配置休息时间："+sqlServerSleep);
////                    return null;
////                }
////                JDBCForJtdsSqlServer jdbc = new JDBCForJtdsSqlServer();
////                list = jdbc.rsDataToList(querySql,sqlParams,databaseUrl,databaseUsername,databasePassword);
////            }else if(databaseUrl.indexOf("jdbc:mysql") != -1) {//mysql
////                if (checkReadyToSleep(DataType.MYSQL)) {
////                    logger.error("当前 mysql 为配置休息时间：" + mysqlSleep);
////                    return null;
////                }
////                JDBCForMySql jdbc = new JDBCForMySql();
////                list = jdbc.rsDataToList(querySql, sqlParams, databaseUrl, databaseUsername, databasePassword);
////            }else{
////                logger.error("无法识别的数据源地址:"+databaseUrl);
////            }
////        } catch (SQLException e) {
////           logger.error("数据检索异常："+e.getMessage(),e);
////        }
////        return list;
////    }
//
//    private boolean checkClientConfig(YYH_CLIENT client,String uuid){
//        boolean lb_client = false;
//        if(client == null){
//            logger.info(uuid + ",请先维护医养护信息表（YYH_CLIENT）");
//        }else{
//            String databaseUrl = client.getCLIENT_DATABASE_URL();
//            String databaseUsername = client.getCLIENT_DATABASE_USERNAME();
//            String databasePassword = client.getCLIENT_DATABASE_PASSWORD();
//            if(("".equals(databaseUrl) || databaseUrl == null)||("".equals(databaseUsername) || databaseUsername == null)||("".equals(databasePassword) || databasePassword == null)){
//                logger.info(uuid + ",请先维护医养护信息表（YYH_CLIENT-CLIENT_DATABSE_URL）-(CLIENTID="+client.getCLIENTID()+")");
//            }else{
//                lb_client = true;
//            }
//        }
//        return lb_client;
//    }
//
//    /**
//     * 按num数据量截取list
//     * @param list
//     * @param num
//     * @return
//     */
//    public static Map<Integer,List<Map<String,Object>>> getSubList(List<Map<String,Object>> list,int num){
//        int len = list.size();
//        Map<Integer,List<Map<String,Object>>> result = new HashMap<>();
//        if(len > num){
//            int i = 0;
//            for(i=0;i< (int)((len-1)/num);i++){
//                List<Map<String,Object>> list1 = list.subList(i*num, (i+1)*num);
//                result.put(i+1,list1);
//            }
//            List<Map<String,Object>> list2 = list.subList(i*num, len);
//            result.put(i+1,list2);
//        }else{
//            result.put(1,list);
//        }
//        return result;
//    }
//
////    /**
////     * 根据配置判断当前数据库是否该休息了
////     * @return
////     */
////    public boolean checkReadyToSleep(DataType type){
////        boolean lb_sleep = false;
////        String sleep = "";
////        switch (type){
////            case MSSQL:
////                sleep = sqlServerSleep;
////                break;
////            case MYSQL:
////                sleep = mysqlSleep;
////                break;
////            case ORACLE:
////                sleep = oracleSleep;
////                break;
////            default:
////                break;
////        }
////        if(null!= sleep && !sleep.isEmpty() && sleep.contains("-")){
////            try {
////                String[] ls_sleeps = sleep.split("-");
////                String ls_start =ls_sleeps[0];
////                String ls_end = ls_sleeps[1];
////                String[] ls_start_time = ls_start.split(":");
////                String[] ls_end_time = ls_end.split(":");
////                Calendar am_calendar = Calendar.getInstance();
////                am_calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(ls_start_time[0]));
////                am_calendar.set(Calendar.MINUTE,Integer.parseInt(ls_start_time[1]));
////                Calendar pm_calendar = Calendar.getInstance();
////                pm_calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(ls_end_time[0]));
////                pm_calendar.set(Calendar.MINUTE,Integer.parseInt(ls_end_time[1]));
////                Date now = new Date();
////                if(now.after(am_calendar.getTime())&&now.before(pm_calendar.getTime())){
////                    lb_sleep = true;
////                }
////            } catch (Exception e) {
////               logger.error("数据库休息配置有误，请检查："+e.getMessage(),e);
////            }
////        }
////        return lb_sleep;
////    }
//}
