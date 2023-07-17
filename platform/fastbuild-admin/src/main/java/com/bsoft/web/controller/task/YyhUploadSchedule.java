//package com.bsoft.web.controller.task;
//
//import com.bsoft.common.utils.BeanMapUtils;
//import com.bsoft.system.domain.yyh.*;
//import com.bsoft.system.service.ISysDynamicSourceService;
//import com.bsoft.system.service.IYyhClientService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * 医养护数据上传
// * Created by Administrator on 2018/9/11.
// */
//@Component
//public class YyhUploadSchedule {
//    private static Logger logger = LoggerFactory.getLogger("aggr");
//    @Autowired
//    private ISysDynamicSourceService iSysDynamicSourceService;
//    @Value("${spring.datasource.url}")
//    private String mainDatabaseUrl;
//    @Value("${spring.datasource.username}")
//    private String mainDatabaseUsername;
//    @Value("${spring.datasource.password}")
//    private String mainDatabasePassword;
//    @Autowired
//    private YyhDataAggr yyhDataAggr;
//    //判断是否上传
//    @Value("${aggr.enable}")
//    private boolean enable;
//    @Value("${aggr.heartable}")
//    private boolean heartAble;
//    //核酸报告自动上传
//    @Value("${aggr.hsbgrun}")
//    private boolean hsbgenable;
//    @Value("${aggr.hsrzts}")
//    private int hsrzts;
//
//    @Value("${aggr.hscjrun}")
//    private boolean hscjenable;
//
//    @Autowired
//    private HttpSign httpSign;
//
//    @Autowired
//    private IYyhClientService iYyhClientService;
//
//    //控制同一业务上传调用间隔不超过5分钟，不同业务不超过1分钟
//    public Map<String,Object> taskMap = new ConcurrentHashMap<String,Object>();
//
//    /**
//     * 心跳服务
//     */
//    @Async
//    @Scheduled(cron = "0 0/5 * * * ?")
//    public void heartSched() throws Exception {
//        if(heartAble) {
//            YYH_CLIENT yyh_dzclient = null;
//            List<YYH_CLIENT> clients = iYyhClientService.list();
//            if (null != clients) {
////                YYH_CLIENT client = BeanUtils.mapToBean(BeanUtils.beanToMap(clients.get(0)), YYH_CLIENT.class);
//                YYH_CLIENT client = (YYH_CLIENT) BeanMapUtils.mapToObject(BeanMapUtils.objectToMap(clients.get(0)),YYH_CLIENT.class);
//                String databaseUrl = client.getCLIENT_DATABASE_URL();
//                if ("".equals(databaseUrl) || databaseUrl == null || "main".equals(databaseUrl)) {//设置jdbc为默认主数据源
//                    client.setCLIENT_DATABASE_URL(mainDatabaseUrl);
//                    client.setCLIENT_DATABASE_USERNAME(mainDatabaseUsername);
//                    client.setCLIENT_DATABASE_PASSWORD(mainDatabasePassword);
//                }
//                yyh_dzclient = client;
//            }
//            yyhDataAggr.heartReport(yyh_dzclient);
//        }
//    }
//
//    /**
//     * 定时上传，每天2点
//     */
//    @Scheduled(cron = "${aggr.yyhtcron}")
//    @Transactional(rollbackFor = {Exception.class})
//    @Async
//    public void timer(){
//        if(enable){
//            //时间格式
//            SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            logger.info("定时任务1开始执行。。。。。。time:"+sdf.format(new Date()));
//            //获取“医养护平台信息”任务信息
//            List<YYH_CLIENT> clients = iYyhClientService.list();
//            //开始上传
//            for(YYH_CLIENT client1 : clients){
//                logger.info("定时任务1"+client1.getCLIENT_DATABASE_URL()+"数据源业务开始上传");
//                try{
//                    //将YYH_CLIENT 转为map，在转回 YYH_CLIENT
//                    YYH_CLIENT client = (YYH_CLIENT) BeanMapUtils.mapToObject(BeanMapUtils.objectToMap(client1),YYH_CLIENT.class);
//                    //提取数据库链接地址
//                    String databaseUrl = client.getCLIENT_DATABASE_URL();
//                    //默认为主数据库
//                    if("".equals(databaseUrl) || databaseUrl == null || "main".equals(databaseUrl)){//设置jdbc为默认主数据源
//                        client.setCLIENT_DATABASE_URL(mainDatabaseUrl);
//                        client.setCLIENT_DATABASE_USERNAME(mainDatabaseUsername);
//                        client.setCLIENT_DATABASE_PASSWORD(mainDatabasePassword);
//                    }
//                    //YYH_DATA_AGGR，根据clientid获取“医养护数据归集”.
//                    List<Map<String,Object>> data_aggrs = iSysDynamicSourceService.dynamicReportList("select * from YYH_DATA_AGGR where SFQY = 1 and CLIENTID="+client1.getCLIENTID()+" order by SORT","SLAVE");
////                    List<YYH_DATA_AGGR> data_aggrs = baseDao.doQuery("from YYH_DATA_AGGR where SFQY = 1 and CLIENTID=:clientid order by SORT", parampers);
//                    List<YYH_DATA_AGGR> aggrList = new ArrayList<>();
//                    for(Map data_aggr_temp : data_aggrs){
//                        //过滤轮询的上传任务，执行间隔分钟
//                        YYH_DATA_AGGR data_aggr = (YYH_DATA_AGGR)BeanMapUtils.mapToObject(data_aggr_temp,YYH_DATA_AGGR.class);
//                        if(data_aggr.getINTERVALMINUTES() == null || data_aggr.getINTERVALMINUTES() == 0){
//                            aggrList.add(data_aggr);
//                        }
//                    }
//                    DataAggrVo dataAggrVo = new DataAggrVo();
//                    dataAggrVo.setClient(client);
//                    dataAggrVo.setDataAggrs(aggrList);
//                    //上传主类
//                    yyhDataAggr.doDataAggr(taskMap,dataAggrVo,true,httpSign);
//                }catch (Exception e){
//                    logger.info("定时任务1"+client1.getCLIENTID()+"数据源业务数据上传有错误，请查看日志",e);
//                    continue;
//                }
//                logger.info("定时任务1"+client1.getCLIENT_DATABASE_URL()+"数据源业务结束上传");
//            }
//            logger.info("定时任务1执行结束。。。。。。time:"+sdf.format(new Date()));
//        }
//    }
//
//    /**
//     * 每2分钟执行一次
//     */
//    @Scheduled(cron = "${aggr.yyhscron}")
//    @Transactional(rollbackFor = {Exception.class})
//    @Async
//    public void timer1(){
//        if(enable){
//            SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            logger.info("定时任务2开始执行。。。。。。time:"+sdf.format(new Date()));
//            List<YYH_CLIENT> clients = iYyhClientService.list();
//            for(YYH_CLIENT client1 : clients){
//                logger.info("定时任务2"+client1.getCLIENT_DATABASE_URL()+"数据源业务开始上传");
//                try{
//                    Map<String,Object> parampers = new HashMap<>();
//                    YYH_CLIENT client = (YYH_CLIENT)BeanMapUtils.mapToObject(BeanMapUtils.objectToMap(client1), YYH_CLIENT.class);
//                    String databaseUrl = client.getCLIENT_DATABASE_URL();
//                    if("".equals(databaseUrl) || databaseUrl == null || "main".equals(databaseUrl)){//设置jdbc为默认主数据源
//                        client.setCLIENT_DATABASE_URL(mainDatabaseUrl);
//                        client.setCLIENT_DATABASE_USERNAME(mainDatabaseUsername);
//                        client.setCLIENT_DATABASE_PASSWORD(mainDatabasePassword);
//                    }
//                    parampers.put("clientid",client1.getCLIENTID());
////                    List<YYH_DATA_AGGR> data_aggrs = baseDao.doQuery("select * from YYH_DATA_AGGR where SFQY = 1 and CLIENTID="+client1.getCLIENTID()+" order by SORT", parampers);
//
//                    List<Map<String,Object>> data_aggrs = iSysDynamicSourceService.dynamicReportList("select * from YYH_DATA_AGGR where SFQY = 1 and CLIENTID="+client1.getCLIENTID()+" order by SORT","SLAVE");
//
//                    List<YYH_DATA_AGGR> aggrList = new ArrayList<>();
//                    for(Map data_aggr_temp : data_aggrs){//过滤定时的上传任务
//                        YYH_DATA_AGGR data_aggr = (YYH_DATA_AGGR)BeanMapUtils.mapToObject(data_aggr_temp,YYH_DATA_AGGR.class);
//                        if(data_aggr.getINTERVALMINUTES() != null && data_aggr.getINTERVALMINUTES() != 0){
//                            aggrList.add(data_aggr);
//                        }
//                    }
//                    DataAggrVo dataAggrVo = new DataAggrVo();
//                    dataAggrVo.setClient(client);
//                    dataAggrVo.setDataAggrs(aggrList);
//                    yyhDataAggr.doDataAggr(taskMap,dataAggrVo,true,httpSign);
//                }catch (Exception e){
//                    logger.info("定时任务2"+client1.getCLIENTID()+"数据源业务数据上传有错误，请查看日志",e);
//                    continue;
//                }
//                logger.info("定时任务2"+client1.getCLIENT_DATABASE_URL()+"数据源业务结束上传");
//            }
//            logger.info("定时任务2执行结束。。。。。。time:"+sdf.format(new Date()));
//        }
//    }
//
//    @Scheduled(cron = "${aggr.yyhscron}")
//    @Transactional(rollbackFor = {Exception.class})
//    @Async
//    public void timer4(){
//        if(enable){
//            SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            logger.info("定时任务2开始执行。。。。。。time:"+sdf.format(new Date()));
//            List<YYH_CLIENT> clients = iYyhClientService.list();
//            for(YYH_CLIENT client1 : clients){
//                logger.info("定时任务2"+client1.getCLIENT_DATABASE_URL()+"数据源业务开始上传");
//                try{
//                    Map<String,Object> parampers = new HashMap<>();
//                    YYH_CLIENT client = (YYH_CLIENT)BeanMapUtils.mapToObject(BeanMapUtils.objectToMap(client1), YYH_CLIENT.class);
//                    String databaseUrl = client.getCLIENT_DATABASE_URL();
//                    if("".equals(databaseUrl) || databaseUrl == null || "main".equals(databaseUrl)){//设置jdbc为默认主数据源
//                        client.setCLIENT_DATABASE_URL(mainDatabaseUrl);
//                        client.setCLIENT_DATABASE_USERNAME(mainDatabaseUsername);
//                        client.setCLIENT_DATABASE_PASSWORD(mainDatabasePassword);
//                    }
//                    parampers.put("clientid",client1.getCLIENTID());
//
//                    List<Map<String,Object>> data_aggrs = iSysDynamicSourceService.dynamicReportList("select * from YYH_DATA_AGGR where SFQY = 4 and CLIENTID="+client1.getCLIENTID()+" order by SORT","SLAVE");
//
////                    List<YYH_DATA_AGGR> data_aggrs = baseDao.doQuery("from YYH_DATA_AGGR where SFQY = 4 and CLIENTID=:clientid order by SORT", parampers);
//                    List<YYH_DATA_AGGR> aggrList = new ArrayList<>();
//                    for(Map data_aggr_temp : data_aggrs){//过滤定时的上传任务
//                        YYH_DATA_AGGR data_aggr = (YYH_DATA_AGGR)BeanMapUtils.mapToObject(data_aggr_temp,YYH_DATA_AGGR.class);
//                        if(data_aggr.getINTERVALMINUTES() != null && data_aggr.getINTERVALMINUTES() != 0){
//                            aggrList.add(data_aggr);
//                        }
//                    }
//                    DataAggrVo dataAggrVo = new DataAggrVo();
//                    dataAggrVo.setClient(client);
//                    dataAggrVo.setDataAggrs(aggrList);
//                    yyhDataAggr.doDataAggr(taskMap,dataAggrVo,true,httpSign);
//                }catch (Exception e){
//                    logger.info("定时任务2"+client1.getCLIENTID()+"数据源业务数据上传有错误，请查看日志",e);
//                    continue;
//                }
//                logger.info("定时任务2"+client1.getCLIENT_DATABASE_URL()+"数据源业务结束上传");
//            }
//            logger.info("定时任务2执行结束。。。。。。time:"+sdf.format(new Date()));
//        }
//    }
//
//
//    @Scheduled(cron = "${aggr.hsbgcron}")
//    @Transactional(rollbackFor = {Exception.class})
//    @Async
//    public void hsbgTimer() throws Exception{
//        if(hsbgenable){
//            SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            logger.info("hsbg-开始执行。。。。。。time:"+sdf.format(new Date()));
//            //检索检验报告所在
//            List<Map<String,Object>> data_aggrs = iSysDynamicSourceService.dynamicReportList("select * from YYH_DATA_AGGR where (COLRESCODE ='REQ.C0101.0303.01' or COLRESCODE ='REQ.C0101.0303.02' or COLRESCODE='REQ.C0101.0303.0201') and (SETCODE ='C0101.0303.01' or SETCODE ='C0101.0303.02' or SETCODE='C0101.0303.0201') order by SORT","SLAVE");
////            List<YYH_DATA_AGGR> data_aggrs = baseDao.doQuery("from YYH_DATA_AGGR where (COLRESCODE ='REQ.C0101.0303.01' or COLRESCODE ='REQ.C0101.0303.02' or COLRESCODE='REQ.C0101.0303.0201') and (SETCODE ='C0101.0303.01' or SETCODE ='C0101.0303.02' or SETCODE='C0101.0303.0201') order by SORT", null);
//            YYH_CLIENT yyh_hsclient = null;
//            for(Map data_aggr_temp : data_aggrs){
//                YYH_DATA_AGGR data_aggr = (YYH_DATA_AGGR)BeanMapUtils.mapToObject(data_aggr_temp,YYH_DATA_AGGR.class);
//                if(null == yyh_hsclient){
//                    Map<String,Object> parampers = new HashMap<>();
//                    parampers.put("clientid",data_aggr.getCLIENTID());
//                    YYH_CLIENT clients = iYyhClientService.getById(data_aggr.getCLIENTID());
//                    if(clients.getCLIENTID()!=null){
//                        YYH_CLIENT client = clients;
//                        String databaseUrl = client.getCLIENT_DATABASE_URL();
//                        if("".equals(databaseUrl) || databaseUrl == null || "main".equals(databaseUrl)){//设置jdbc为默认主数据源
//                            client.setCLIENT_DATABASE_URL(mainDatabaseUrl);
//                            client.setCLIENT_DATABASE_USERNAME(mainDatabaseUsername);
//                            client.setCLIENT_DATABASE_PASSWORD(mainDatabasePassword);
//                        }else{
//                            logger.error("检验申请单和报告的数据源配置有误，请检查相关配置");
//                            return;
//                        }
//                        yyh_hsclient = client;
//                    }
//                }
//                List<YYH_DATA_AGGR> aggrList = new ArrayList<>();
//                aggrList.add(data_aggr);
//                DataAggrVo dataAggrVo = new DataAggrVo();
//                dataAggrVo.setClient(yyh_hsclient);
//                dataAggrVo.setDataAggrs(aggrList);
//                yyhDataAggr.doDataAggrForHsbg(taskMap,dataAggrVo,httpSign,null);
//            }
//            logger.info("hsbg-执行结束。。。。。。time:"+sdf.format(new Date()));
//        }
//    }
//
//    /**
//     * 医养护数据对账
//     */
//    @Scheduled(cron = "${aggr.sjdzcron}")
//    @Transactional(rollbackFor = {Exception.class})
//    @Async
//    public void sjdzTimer() throws Exception{
//        if(enable){
//            SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            logger.info("数据对账-开始执行。。。。。。time:"+sdf.format(new Date()));
//            //List<YYH_DATA_AGGR> list_sjdz = baseDao.doQuery("from YYH_DATA_AGGR where SFQY = 3 and CLIENTID=:clientid order by SORT", null);
//            String sql = "select b.querykey as qkey,b.queryvalue as qsql,a.sqlcontent,a.colrescode,a.setcode,a.clientid,b.standardcode as scode ,a.AGGRJGIDM as AGGRJGIDM  from YYH_DATA_AGGR a,yyh_data_aggr_sjdz b where a.setcode = b.setcode and b.sfqy = '1' and b.queryvalue is not null order by a.sort";
////            List<Map<String, Object>> list_query = baseDao.doSqlQuery(sql,null);
//            List<Map<String, Object>> list_query = iSysDynamicSourceService.dynamicReportList(sql,"SLAVE");
//            YYH_CLIENT yyh_dzclient = null;
//            Map<String,YYH_CLIENT> map_clients = new HashMap<>();
//            YYH_CLIENT yc_main = new YYH_CLIENT();
//            yc_main.setCLIENT_DATABASE_URL(mainDatabaseUrl);
//            yc_main.setCLIENT_DATABASE_USERNAME(mainDatabaseUsername);
//            yc_main.setCLIENT_DATABASE_PASSWORD(mainDatabasePassword);
//
//            for(Map<String, Object> data_query : list_query){
//                Long li_clientid = Long.parseLong(data_query.get("CLIENTID")+"");
//                if(!map_clients.containsKey(li_clientid+"")){
//                    Map<String,Object> parampers = new HashMap<>();
//                    parampers.put("clientid",li_clientid);
//                    YYH_CLIENT clients = iYyhClientService.getById(li_clientid);
//                    if(clients.getCLIENTID()!=null){
//                        YYH_CLIENT client = clients;
//                        String databaseUrl = client.getCLIENT_DATABASE_URL();
//                        if("".equals(databaseUrl) || databaseUrl == null || "main".equals(databaseUrl)){//设置jdbc为默认主数据源
//                            client.setCLIENT_DATABASE_URL(mainDatabaseUrl);
//                            client.setCLIENT_DATABASE_USERNAME(mainDatabaseUsername);
//                            client.setCLIENT_DATABASE_PASSWORD(mainDatabasePassword);
//                        }
//                        yyh_dzclient = client;
//                        map_clients.put(li_clientid+"",client);
//                    }
//                }else{
//                    yyh_dzclient = map_clients.get(li_clientid+"");
//                }
//                //每日上传，时间设定，因数据库中配置的结束时间为小于等于，故此处在适配其他地区时，最多会存在1微秒的差异。
//                Calendar cal_time = Calendar.getInstance();
//                cal_time.set(Calendar.HOUR_OF_DAY,23);
//                cal_time.set(Calendar.MINUTE,59);
//                cal_time.set(Calendar.SECOND,59);
//                cal_time.set(Calendar.MILLISECOND,999);
//                cal_time.add(Calendar.DAY_OF_YEAR,-1);
//                Date d_end = cal_time.getTime();
//                cal_time.set(Calendar.HOUR_OF_DAY,0);
//                cal_time.set(Calendar.MINUTE,0);
//                cal_time.set(Calendar.SECOND,0);
//                cal_time.set(Calendar.MILLISECOND,0);
//                Date d_start = cal_time.getTime();
//                data_query.put("MZHSTART",d_start);
//                data_query.put("MZHEND",d_end);
//                yyhDataAggr.doDataAggrForSjdz(data_query,httpSign,yyh_dzclient,yc_main);
//            }
//            logger.info("数据对账-执行结束。。。。。。time:"+sdf.format(new Date()));
//        }
//    }
//
//    @Scheduled(cron="${aggr.hscjcron}")
//    @Transactional
//    @Async
//    public void pushHscjInfo() throws Exception{
//        if(hscjenable){
//            SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            logger.info("核酸采集-开始执行。。。。。。time:"+sdf.format(new Date()));
////            List<YYH_DATA_AGGR> data_aggrs = baseDao.doQuery("from YYH_DATA_AGGR where SFQY = 3", null);
//            List<Map<String,Object>> data_aggrs = iSysDynamicSourceService.dynamicReportList("select * from YYH_DATA_AGGR where SFQY = 3","SLAVE");
//            YYH_CLIENT yyh_dzclient = null;
//            Map<String,YYH_CLIENT> map_clients = new HashMap<>();
//            //即一个数据集一个数据集的传
//            for(Map data_aggr_temp : data_aggrs){
//                List<YYH_DATA_AGGR> data_aggrs_ = new ArrayList<>();
//                YYH_DATA_AGGR data_aggr = (YYH_DATA_AGGR)BeanMapUtils.mapToObject(data_aggr_temp,YYH_DATA_AGGR.class);
//                data_aggrs_.add(data_aggr);
//                Long li_clientid = data_aggr.getCLIENTID();
//                if(!map_clients.containsKey(li_clientid+"")){
//                    Map<String,Object> parampers = new HashMap<>();
//                    parampers.put("clientid",li_clientid);
//                    YYH_CLIENT clients = iYyhClientService.getById(li_clientid);
//                    if(clients.getCLIENTID()!=null){
//                        YYH_CLIENT client = clients;
//                        String databaseUrl = client.getCLIENT_DATABASE_URL();
//                        if("".equals(databaseUrl) || databaseUrl == null || "main".equals(databaseUrl)){//设置jdbc为默认主数据源
//                            client.setCLIENT_DATABASE_URL(mainDatabaseUrl);
//                            client.setCLIENT_DATABASE_USERNAME(mainDatabaseUsername);
//                            client.setCLIENT_DATABASE_PASSWORD(mainDatabasePassword);
//                        }
//                        yyh_dzclient = client;
//                        map_clients.put(li_clientid+"",client);
//                    }
//                }else{
//                    yyh_dzclient = map_clients.get(li_clientid+"");
//                }
//                DataAggrVo dataAggrVo = new DataAggrVo();
//                dataAggrVo.setClient(yyh_dzclient);
//                dataAggrVo.setDataAggrs(data_aggrs_);
//                yyhDataAggr.doDataAggrForHscj(dataAggrVo);
//            }
//            logger.info("核酸采集-执行结束。。。。。。time:"+sdf.format(new Date()));
//        }
//    }
//
//    /**
//     * 日志清理时间
//     */
//    @Scheduled(cron="0 0 3 * * ?")//${aggr.yuplgcron}
//    @Transactional
//    @Async
//    public void deleteUplog(){
//        long ll_start = System.nanoTime();
//        logger.info("-----------------delete from yyh_uplog where pushdate <= sysdate - 9-----------------");
//        String sql = "delete from yyh_uplog where pushdate <= :DAYY";
//        if(enable){
//            try {
//                Calendar cal_time = Calendar.getInstance();
//                cal_time.add(Calendar.DAY_OF_YEAR,-Math.abs(hsrzts));
//                HashMap<String,Object> paramter = new HashMap<>();
//                paramter.put("DAYY",cal_time.getTime());
////                int li_result = baseDao.executeSql(sql,paramter);
//                int li_result = 0;
//                        logger.error("定时删除医养护上传明细记录数："+li_result);
//            } catch (Exception e) {
//                logger.error("删除出现异常："+e.getMessage(),e);
//            }
//        }
//        long ll_end = System.nanoTime();
//        logger.info("-----------------执行时间："+(ll_end - ll_start)/1000000+"毫秒-----------------");
//    }
//}
