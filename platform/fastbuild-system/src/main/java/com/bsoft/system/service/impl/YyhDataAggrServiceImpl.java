package com.bsoft.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.bsoft.common.annotation.DataSource;
import com.bsoft.common.enums.DataSourceType;
import com.bsoft.common.enums.drgs.DrgsStatus;
import com.bsoft.common.utils.*;
import com.bsoft.common.utils.YyhbwjxUtil;
import com.bsoft.system.domain.DrgsSqlconfig;
import com.bsoft.system.domain.drgs.DrgsRequestBody;
import com.bsoft.system.domain.yyh.*;
import com.bsoft.system.domain.yyh.Entity.*;
import com.bsoft.system.mapper.DrgsSqlconfigMapper;
import com.bsoft.system.mapper.YyhDetailsDataMapper;
import com.bsoft.system.service.ISysDynamicSourceService;
import com.bsoft.system.service.IYyhClientService;
import com.bsoft.system.service.IYyhDataAggrService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javafx.collections.MapChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.bsoft.common.utils.PageUtils.startPage;

@Service
public class YyhDataAggrServiceImpl implements IYyhDataAggrService {
    private static Logger logger = LoggerFactory.getLogger(YyhDataAggrServiceImpl.class);
    @Value("${spring.datasource.druid.master.url}")
    private String mainDatabaseUrl;
    @Value("${spring.datasource.druid.master.username}")
    private String mainDatabaseUsername;
    @Value("${spring.datasource.druid.master.password}")
    private String mainDatabasePassword;
//    @Autowired
//    private YyhDataAggr yyhDataAggr;

    @Autowired
    private ISysDynamicSourceService iSysDynamicSourceService;
    @Autowired
    private YyhDetailsDataMapper yyhDetailsDataMapper;

    @Value("${aggr.master}")
    private String DBID;

    @Value("${aggr.licence}")
    private String ptsjlicence;

    @Autowired
    private IYyhClientService iYyhClientService;

    @Resource
    private DrgsSqlconfigMapper drgsSqlconfigMapper;

    @Autowired
    private YyhbwjxUtil yyhbw;

    private static final String YYH_MAP_PRIKEY = "MZHKEYS";
    private static final String YYH_MAP_DATAKEY = "MZHDATAS";
    private static final String YYH_DATA_PRIKEY = "upprikey";
    private static final String YYH_DATA_PRIKEYSPLIT = "@";


    //控制同一业务上传调用间隔不超过5分钟，不同业务不超过1分钟
    public Map<String, Object> taskMap = new HashMap<>();

//    @Override
//    public List<Map<String, Object>> getYyhDataAggr() {
//        //前一天
//        Calendar c = Calendar.getInstance();
//        c.setTime(new Date());
//        int day1 = c.get(Calendar.DATE);
//        c.set(Calendar.DATE, day1 - 1);
//        List<Map<String, Object>> list = iSysDynamicSourceService.dynamicReportList("select a.CLIENTID,a.COLRESCODE,a.DESCRIBE,a.SETCODE,b.CLIENT_DATABASE_URL,b.CLIENT_REMARK from YYH_DATA_AGGR a,YYH_CLIENT b WHERE a.SFQY = 1 AND a.CLIENTID=b.CLIENTID ORDER BY a.CLIENTID,a.SORT", DBID);
//        for (Map<String, Object> map : list) {
//            map.put("BEGINDATE", c.getTime());
//            map.put("ENDDATE", c.getTime());
//        }
//        return list;
//    }
//
//    @Override
//    public Map<String, Boolean> doUpload(Map<String, Object> params) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        logger.info("手动上传开始执行。。。。。。time:" + sdf.format(new Date()));
//        try {
//            Map<String, Object> record = (Map<String, Object>) params.get("record");
//            String begindate = (record.get("BEGINDATE") + "").replaceAll("T", " ");
//            begindate = begindate.split(" ")[0].replaceAll("-", "") + "000000";
//            String enddate = (record.get("ENDDATE") + "").replaceAll("T", " ");
//            enddate = enddate.split(" ")[0].replaceAll("-", "") + "235959";
//            Map<String, Object> parampers = new HashMap<>();
//            parampers.put("clientid", Long.parseLong(record.get("CLIENTID") + ""));
////            YyhClient client1 = iYyhClientService.selectYyhClientByClientid(Long.parseLong(record.get("CLIENTID") + ""));
//            YYH_CLIENT client = iYyhClientService.getById(Long.parseLong(record.get("CLIENTID") + ""));
////            YYH_CLIENT client1 = dao.doLoad("from YYH_CLIENT where CLIENTID=:clientid", parampers);
////            YYH_CLIENT client = BeanUtils.mapToBean(BeanUtils.beanToMap(client1), YYH_CLIENT.class);
////            String databaseUrl = client.getCLIENT_DATABASE_URL();
////            if ("".equals(databaseUrl) || databaseUrl == null || "main".equals(databaseUrl)) {//设置jdbc为默认主数据源
////                client.setCLIENT_DATABASE_URL(mainDatabaseUrl);
////                client.setCLIENT_DATABASE_USERNAME(mainDatabaseUsername);
////                client.setCLIENT_DATABASE_PASSWORD(mainDatabasePassword);
////            }
//            parampers.put("colrescode", record.get("COLRESCODE"));
////            YYH_DATA_AGGR dataAggr = dao.doLoad("from YYH_DATA_AGGR WHERE COLRESCODE=:colrescode and CLIENTID=:clientid", parampers);
//            List<Map<String, Object>> dataAggr = iSysDynamicSourceService.dynamicReportList("select * from YYH_DATA_AGGR WHERE COLRESCODE='" + record.get("COLRESCODE") + "' and CLIENTID=" + client.getCLIENTID(), DBID);
//            List<YYH_DATA_AGGR> data_aggrs = new ArrayList<>();
//            YYH_DATA_AGGR yyh_data_aggr = (YYH_DATA_AGGR) BeanMapUtils.mapToObject(dataAggr.get(0), YYH_DATA_AGGR.class);
//            data_aggrs.add(yyh_data_aggr);
////		List<YYH_DATA_AGGR> data_aggrs = baseDao.doQuery("from YYH_DATA_AGGR where SFQY = 1", null);
//            DataAggrVo dataAggrVo = new DataAggrVo();
//            dataAggrVo.setClient(client);
//            dataAggrVo.setDataAggrs(data_aggrs);
//            dataAggrVo.setBegindate(begindate);
//            dataAggrVo.setEnddate(enddate);
//            Map<String, Boolean> result = yyhDataAggr.doDataAggr(taskMap, dataAggrVo, false, httpSign);
//            return result;
//        } catch (Exception e) {
//            throw new BaseException(e.toString());
//        } finally {
//            logger.info("手动上传执行结束。。。。。。time:" + sdf.format(new Date()));
//        }
//    }
//
//    @Override
//    public Map<String, Object> updateSer(DrgsRequestBody body) {
//        Map<String, Object> reMap = new HashMap<String, Object>();
//        reMap.put("code", "0");
//        reMap.put("msg", "任务运行结束");
//        try {
//            Map<String, Object> req = body.getCondition();
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            logger.info("补传上传开始执行。。。。。。time:" + formatter.format(new Date()));
//            Date startDate = body.getBegindate();
//            Date endDate = body.getEnddate();
//            int li_milldate = Integer.parseInt(req.get("milldate") + "");
//            Calendar cal_start = Calendar.getInstance();
//            Date dd_temp = startDate;
//            do {
//                Date dd_start = dd_temp;
//                //开始时间步进
//                cal_start.setTime(dd_start);
//                cal_start.add(Calendar.MINUTE, li_milldate);
//                dd_temp = cal_start.getTime();
////				System.out.println("起止时间："+formatter.format(dd_start)+"-"+formatter.format(dd_temp));
//                //获取“医养护平台信息”任务信息
//
////                List<YYH_CLIENT> client2 = iYyhClientService.list();
////                List<YYH_CLIENT> client3 = new ArrayList<>();
////                client3.add(client2.get(0));
//                List<YYH_CLIENT> clients = iYyhClientService.list();
////                List<YYH_CLIENT> clients = client3;
//                for (YYH_CLIENT client1 : clients) {
//                    logger.info("1" + client1.getCLIENT_DATABASE_URL() + "数据源业务开始上传");
//                    try {
//                        Map<String, Object> parampers = new HashMap<>();
//                        //将YYH_CLIENT 转为map，在转回 YYH_CLIENT
//                        YYH_CLIENT client = (YYH_CLIENT) BeanMapUtils.mapToObject(BeanMapUtils.objectToMap(client1), YYH_CLIENT.class);
//                        //提取数据库链接地址
//                        String databaseUrl = client.getCLIENT_DATABASE_URL();
//                        //默认为主数据库
//                        if ("".equals(databaseUrl) || databaseUrl == null || "main".equals(databaseUrl)) {//设置jdbc为默认主数据源
//                            client.setCLIENT_DATABASE_URL(mainDatabaseUrl);
//                            client.setCLIENT_DATABASE_USERNAME(mainDatabaseUsername);
//                            client.setCLIENT_DATABASE_PASSWORD(mainDatabasePassword);
//                        }
//                        parampers.put("clientid", client1.getCLIENTID());
//                        //YYH_DATA_AGGR，根据clientid获取“医养护数据归集”
////                        List<YYH_DATA_AGGR> data_aggrs = dao.doQuery("from YYH_DATA_AGGR where SFQY = 1 and CLIENTID=:clientid order by SORT", parampers);
//                        List<Map<String, Object>> data_aggrs = iSysDynamicSourceService.dynamicReportList("select * from YYH_DATA_AGGR where SFQY = 1 and CLIENTID=" + client1.getCLIENTID() + " order by SORT", DBID);
//                        List<YYH_DATA_AGGR> aggrList = new ArrayList<>();
////                        for (YYH_DATA_AGGR data_aggr : data_aggrs) {
////                            aggrList.add(data_aggr);
////                        }
//
//                        List<Map<String, Object>> data_aggrs_temp = new ArrayList<>();
//                        if(data_aggrs.size()>0){
//                            data_aggrs_temp.add(data_aggrs.get(0));
//                        }
//                        data_aggrs = data_aggrs_temp;
//                        for (Map data_aggr_temp : data_aggrs) {
//                            //过滤轮询的上传任务，执行间隔分钟
//                            YYH_DATA_AGGR data_aggr = (YYH_DATA_AGGR) BeanMapUtils.mapToObject(data_aggr_temp, YYH_DATA_AGGR.class);
//                            aggrList.add(data_aggr);
//                        }
//                        logger.info("当前数据源待上传数据集：" + aggrList.size());
//                        DataAggrVo dataAggrVo = new DataAggrVo();
//                        dataAggrVo.setClient(client);
//                        dataAggrVo.setDataAggrs(aggrList);
//                        //上传主类
//                        yyhDataAggr.doAggrDataUp(taskMap, dataAggrVo, httpSign, dd_start, dd_temp);
//                    } catch (Exception e) {
//                        logger.info("补传任务" + client1.getCLIENTID() + "数据源业务数据上传有错误，请查看日志", e);
//                        continue;
//                    }
//                    logger.info("补传任务" + client1.getCLIENT_DATABASE_URL() + "数据源业务结束上传");
//                }
//            } while (dd_temp.before(endDate));
//            logger.info("补传任务执行结束。。。。。。time:" + formatter.format(new Date()));
//        } catch (Exception e) {
//            reMap.put("code", "-1");
//            reMap.put("msg", "任务运行异常" + e.getMessage());
//            logger.error("补传数据存在异常：" + e.getMessage(), e);
//        }
//        return reMap;
//    }
//
//    @Override
//    public Map<String, Object> updateSing(Map<String, Object> params) {
//        Map<String, Object> reMap = new HashMap<String, Object>();
//        reMap.put("code", "0");
//        reMap.put("msg", "任务运行结束");
//        return reMap;
//    }
//
//    /**
//     * 核酸报告上传
//     *
//     * @param req
//     * @return
//     */
//    @Override
//    @Transactional(rollbackFor = {Exception.class})
//    public Map<String, Object> updateSingForHsbg(Map<String, Object> req) {
//        Map<String, Object> reMap = new HashMap<String, Object>();
//        reMap.put("code", "0");
//        reMap.put("msg", "任务运行结束");
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            String idcard = req.get("idcard") + "";
//            if (null == idcard || idcard.isEmpty()) {
//                reMap.put("code", "-1");
//                reMap.put("msg", "身份证未获取");
//            } else {
//                logger.info(uuid + "\t个人检验报告补传开始执行。。。。。。time:" + formatter.format(new Date()));
//                //获取核酸报告上传的任务，然后再获取其数据库连接配置
//                //1.获取核酸报告上传任务
//                //List<YYH_DATA_AGGR> data_aggrs = dao.doQuery("from YYH_DATA_AGGR where (COLRESCODE ='REQ.C0101.0303.01' or COLRESCODE ='REQ.C0101.0303.02' or COLRESCODE='REQ.C0101.0303.0201') and (SETCODE ='C0101.0303.01' or SETCODE ='C0101.0303.02' or SETCODE='C0101.0303.0201') order by SORT", null);
//                List<Map<String, Object>> data_aggrs = iSysDynamicSourceService.dynamicReportList("select * from YYH_DATA_AGGR where (COLRESCODE ='REQ.C0101.0303.01' or COLRESCODE ='REQ.C0101.0303.02' or COLRESCODE='REQ.C0101.0303.0201') and (SETCODE ='C0101.0303.01' or SETCODE ='C0101.0303.02' or SETCODE='C0101.0303.0201') order by SORT", DBID);
//                //2.获取配置的数据库信息
//                for (Map data_aggr_temp : data_aggrs) {
//                    YYH_DATA_AGGR data_aggr = (YYH_DATA_AGGR) BeanMapUtils.mapToObject(data_aggr_temp, YYH_DATA_AGGR.class);
////                    Map<String, Object> parampers = new HashMap<>();
////                    parampers.put("clientid", data_aggr.getCLIENTID());
////                    List<YYH_CLIENT> clients = dao.doQuery("from YYH_CLIENT where CLIENTID=:clientid order by CLIENTID", parampers);
//                    YYH_CLIENT clients = iYyhClientService.getById(data_aggr.getCLIENTID());
//                    if (null != clients) {
//                        //将YYH_CLIENT 转为map，在转回 YYH_CLIENT
////                        YYH_CLIENT client = BeanUtils.mapToBean(BeanUtils.beanToMap(clients.get(0)), YYH_CLIENT.class);
//                        YYH_CLIENT client = clients;
//                        //提取数据库链接地址
//                        String databaseUrl = client.getCLIENT_DATABASE_URL();
//                        //默认为主数据库
//                        if ("".equals(databaseUrl) || databaseUrl == null || "main".equals(databaseUrl)) {//设置jdbc为默认主数据源
//                            client.setCLIENT_DATABASE_URL(mainDatabaseUrl);
//                            client.setCLIENT_DATABASE_USERNAME(mainDatabaseUsername);
//                            client.setCLIENT_DATABASE_PASSWORD(mainDatabasePassword);
//                        }
//                        //3.上传
//                        List<YYH_DATA_AGGR> aggrList = new ArrayList<>();
//                        aggrList.add(data_aggr);
//                        DataAggrVo dataAggrVo = new DataAggrVo();
//                        dataAggrVo.setClient(client);
//                        dataAggrVo.setDataAggrs(aggrList);
//                        //上传主类
//                        yyhDataAggr.doDataAggrForHsbg(taskMap, dataAggrVo, httpSign, idcard);
//                    } else {
//                        reMap.put("code", "-1");
//                        reMap.put("msg", "服务配置错误");
//                    }
//                }
//            }
//        } catch (Exception e) {
//            reMap.put("code", "-1");
//            reMap.put("msg", "任务运行异常" + e.getMessage());
//            logger.error(uuid + "\t个人核酸报告补传数据存在异常：" + e.getMessage(), e);
//        } finally {
//            logger.info(uuid + "\t个人核酸报告补传任务执行结束。。。。。。time:" + formatter.format(new Date()));
//        }
//        return reMap;
//    }
//
//    @Override
//    @Transactional(rollbackFor = {Exception.class})
//    public Map<String, Object> updateSingForPerson(Map<String, Object> req) {
//        Map<String, Object> reMap = new HashMap<String, Object>();
//        reMap.put("code", "0");
//        reMap.put("msg", "任务运行结束");
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String ls_colrescode = (String) req.get("COLRESCODE");
//        try {
//            String idcard = req.get("idcard") + "";
//            if (null == idcard || idcard.isEmpty()) {
//                reMap.put("code", "-1");
//                reMap.put("msg", "身份证未获取");
//            } else {
//                logger.info(uuid + "\t个人报告补传开始执行。。。。。。time:" + formatter.format(new Date()));
////                Map<String, Object> data_aggrs_parampers = new HashMap<>();
////                data_aggrs_parampers.put("COLRESCODE", ls_colrescode);
////                List<YYH_DATA_AGGR> data_aggrs = dao.doQuery("from YYH_DATA_AGGR where COLRESCODE=:COLRESCODE order by SORT", data_aggrs_parampers);
//                List<Map<String, Object>> data_aggrs = iSysDynamicSourceService.dynamicReportList("select * from YYH_DATA_AGGR where COLRESCODE='" + ls_colrescode + "' order by SORT", DBID);
//                //2.获取配置的数据库信息
//                for (Map data_aggr_temp : data_aggrs) {
//                    YYH_DATA_AGGR data_aggr = (YYH_DATA_AGGR) BeanMapUtils.mapToObject(data_aggr_temp, YYH_DATA_AGGR.class);
////                    List<YYH_CLIENT> clients = dao.doQuery("from YYH_CLIENT where CLIENTID=:clientid order by CLIENTID", parampers);
//                    YYH_CLIENT clients = iYyhClientService.getById(data_aggr.getCLIENTID());
//                    if (null != clients) {
//                        //将YYH_CLIENT 转为map，在转回 YYH_CLIENT
////                        YYH_CLIENT client = BeanUtils.mapToBean(BeanUtils.beanToMap(clients.get(0)), YYH_CLIENT.class);
//                        YYH_CLIENT client = clients;
//                        //提取数据库链接地址
//                        String databaseUrl = client.getCLIENT_DATABASE_URL();
//                        //默认为主数据库
//                        if ("".equals(databaseUrl) || databaseUrl == null || "main".equals(databaseUrl)) {//设置jdbc为默认主数据源
//                            client.setCLIENT_DATABASE_URL(mainDatabaseUrl);
//                            client.setCLIENT_DATABASE_USERNAME(mainDatabaseUsername);
//                            client.setCLIENT_DATABASE_PASSWORD(mainDatabasePassword);
//                        }
//                        //3.上传
//                        List<YYH_DATA_AGGR> aggrList = new ArrayList<>();
//                        aggrList.add(data_aggr);
//                        DataAggrVo dataAggrVo = new DataAggrVo();
//                        dataAggrVo.setClient(client);
//                        dataAggrVo.setDataAggrs(aggrList);
//                        //上传主类
//                        yyhDataAggr.doDataAggrForPerson(dataAggrVo, httpSign, idcard, req);
//                    } else {
//                        reMap.put("code", "-1");
//                        reMap.put("msg", "服务配置错误");
//                    }
//                }
//            }
//        } catch (Exception e) {
//            reMap.put("code", "-1");
//            reMap.put("msg", "任务运行异常" + e.getMessage());
//            logger.error(uuid + "\t个人核酸报告补传数据存在异常：" + e.getMessage(), e);
//        } finally {
//            logger.info(uuid + "\t个人核酸报告补传任务执行结束。。。。。。time:" + formatter.format(new Date()));
//        }
//        return reMap;
//    }
//
//    @Override
//    public Map<String, Object> getYyhUpdataLog(Map<String, Object> req) {
//        Map<String, Object> reMap = new HashMap<String, Object>();
//        reMap.put("code", "0");
//        reMap.put("msg", "无数据");
//
//        Calendar cal_time = Calendar.getInstance();
//        StringBuffer sb_sql = new StringBuffer("select agg.describe as DESCRIBE,agg.setcode,log.orgcode,log.idcard,log.busid,log.busdate as busdate,log.pushdate as pushdate,log.maintaskid,log.singletaskid,case log.pushresult when '1' then '成功' else '失败' end pushresult");
//        sb_sql.append(" from yyh_data_aggr agg,yyh_uplog log");
//        sb_sql.append(" where agg.setcode = log.sjjbm ");
//        //为空，默认查最近一天的上传明细
//        HashMap<String, Object> hm_paramtes = new HashMap<>();
//        if (null == req) {
//            cal_time.add(Calendar.DAY_OF_YEAR, -1);
//            hm_paramtes.put("BEGINTIME", cal_time.getTime());
//            sb_sql.append(" and log.busdate >= #{map.BEGINTIME}");
//        } else {
//            String ls_begin = req.get("begindate") + "";
//            String ls_end = req.get("enddate") + "";
//            //如果身份证为空，查指定日期的数据
//            String idcard = req.get("idcard") + "";
//            sb_sql.append(" and log.busdate >= #{map.BEGINTIME}");
//            sb_sql.append(" and log.busdate <= #{map.ENDTIME}");
//            hm_paramtes.put("BEGINTIME", DateUtil.parse(ls_begin, "yyyy-MM-dd HH:mm:ss"));
//            hm_paramtes.put("ENDTIME", DateUtil.parse(ls_end, "yyyy-MM-dd HH:mm:ss"));
//            if (null != idcard && !idcard.isEmpty()) {
//                sb_sql.append(" and log.idcard like '%").append(idcard).append("%'");
//            }
//        }
//        hm_paramtes.put("sql", sb_sql.toString());
//        List<Map<String, Object>> list = iSysDynamicSourceService.dynamicSqlList4401(hm_paramtes, DBID);
//        if (null == list || list.size() == 0) {
//            logger.info("-------无数据-------:" + sb_sql.toString());
//        } else {
//            reMap.put("code", "0");
//            reMap.put("msg", "检索成功");
//            reMap.put("data", list);
//        }
//        return reMap;
//    }
//
//    @Override
//    public Map<String, Object> upSjdz(Map<String, Object> req) {
//        Map<String, Object> reMap = new HashMap<String, Object>();
//        reMap.put("code", "0");
//        reMap.put("msg", "任务运行结束");
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            logger.info("数据对账-开始执行。。。。。。time:" + sdf.format(new Date()));
//            Date startDate = sdf.parse(req.get("begindate") + "");
//            Date endDate = sdf.parse(req.get("enddate") + "");
//            Date dd_temp = startDate;
//            String sql = "select b.querykey as qkey,b.queryvalue as qsql,a.sqlcontent,a.colrescode,a.setcode,a.clientid,b.standardcode as scode,a.AGGRJGIDM as AGGRJGIDM from YYH_DATA_AGGR a,yyh_data_aggr_sjdz b where a.setcode = b.setcode and b.sfqy = '1' and b.queryvalue is not null order by a.sort";
//            //List<Map<String, Object>> list_query = dao.doSqlQuery(sql, null);
//            List<Map<String, Object>> list_query = iSysDynamicSourceService.dynamicReportList(sql, DBID);
//            YYH_CLIENT yyh_dzclient = null;
//            Map<String, YYH_CLIENT> map_clients = new HashMap<>();
//            do {
//                //每日上传，时间设定，因数据库中配置的结束时间为小于等于，故此处在适配其他地区时，最多会存在1微秒的差异。
//                Calendar cal_time = Calendar.getInstance();
//                cal_time.setTime(dd_temp);
//                cal_time.set(Calendar.HOUR_OF_DAY, 0);
//                cal_time.set(Calendar.MINUTE, 0);
//                cal_time.set(Calendar.SECOND, 0);
//                cal_time.set(Calendar.MILLISECOND, 0);
//                Date d_start = cal_time.getTime();
//
//                cal_time.set(Calendar.HOUR_OF_DAY, 23);
//                cal_time.set(Calendar.MINUTE, 59);
//                cal_time.set(Calendar.SECOND, 59);
//                cal_time.set(Calendar.MILLISECOND, 999);
//                Date d_end = cal_time.getTime();
//
//                YYH_CLIENT yc_main = new YYH_CLIENT();
//                yc_main.setCLIENT_DATABASE_URL(mainDatabaseUrl);
//                yc_main.setCLIENT_DATABASE_USERNAME(mainDatabaseUsername);
//                yc_main.setCLIENT_DATABASE_PASSWORD(mainDatabasePassword);
//
//                for (Map<String, Object> data_query : list_query) {
//                    try {
//                        Long li_clientid = Long.parseLong(data_query.get("CLIENTID") + "");
//                        //客户端处理，可归并
//                        if (!map_clients.containsKey(li_clientid + "")) {
//                            Map<String, Object> parampers = new HashMap<>();
//                            parampers.put("clientid", li_clientid);
//                            //List<YYH_CLIENT> clients = dao.doQuery("from YYH_CLIENT where CLIENTID=:clientid order by CLIENTID", parampers);
//                            YYH_CLIENT clients = iYyhClientService.getById(li_clientid);
//                            if (null != clients) {
////                                YYH_CLIENT client = BeanUtils.mapToBean(BeanUtils.beanToMap(clients.get(0)), YYH_CLIENT.class);
//                                YYH_CLIENT client = clients;
//                                String databaseUrl = client.getCLIENT_DATABASE_URL();
//                                if ("".equals(databaseUrl) || databaseUrl == null || "main".equals(databaseUrl)) {//设置jdbc为默认主数据源
//                                    client.setCLIENT_DATABASE_URL(mainDatabaseUrl);
//                                    client.setCLIENT_DATABASE_USERNAME(mainDatabaseUsername);
//                                    client.setCLIENT_DATABASE_PASSWORD(mainDatabasePassword);
//                                }
//                                yyh_dzclient = client;
//                                map_clients.put(li_clientid + "", client);
//                            }
//                        } else {
//                            yyh_dzclient = map_clients.get(li_clientid + "");
//                        }
//                        data_query.put("MZHSTART", d_start);
//                        data_query.put("MZHEND", d_end);
//                        yyhDataAggr.doDataAggrForSjdz(data_query, httpSign, yyh_dzclient, yc_main);
//                    } catch (Exception e) {
//                        logger.error("数据对账上传异常，明细：" + data_query);
//                    }
//                }
//                cal_time.add(Calendar.DAY_OF_YEAR, 1);
//                dd_temp = cal_time.getTime();
//            } while (dd_temp.before(endDate));
//            logger.info("补传任务执行结束。。。。。。time:" + sdf.format(new Date()));
//        } catch (Exception e) {
//            reMap.put("code", "-1");
//            reMap.put("msg", "任务运行异常" + e.getMessage());
//            logger.error("补传数据存在异常：" + e.getMessage(), e);
//        }
//        return reMap;
//    }
//
//
//    @Override
//    public String getHeartBeat(String servicecode) {
////        List<YYH_CLIENT> clients = iYyhClientService.list();
////        YyhbwjxUtil yyh = new YyhbwjxUtil(clients.get(0));
//        return null;
//    }
//
//    @Override
//    public String getAggrResult(String servicecode, Map<String, Object> businessdata) throws Exception {
//        return null;
//    }

    @Override
    public List<Map<String,Object>> tJUploadQueryPage(DrgsRequestBody body) {
        Map<String, Object> params = body.getCondition();
        DrgsSqlconfig drgsSqlconfig = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(params.get("type")+"", DrgsStatus.MAZH_MAIN.getCode());
        /**
         * 判断当前用户的机构id
         * 如果是0  则不加条件
         */
        String organizationId = SecurityUtils.getLoginUser().getUser().getDept().getOrganizationId();
        params.put("sql", drgsSqlconfig.getSqlcontent());
        if(!"0".equals(organizationId)&&null!=organizationId){
            params.put("sql", "select * from ("+drgsSqlconfig.getSqlcontent()+" ) b where b.WS08_10_912_06 = '"+organizationId+"'");
            params.put("orgCode",organizationId);
        }
        params.put("begindate",body.getBegindate());
        params.put("enddate",body.getEnddate());
        //查询结果
        startPage(body.getPageNum(),body.getPageSize());
        List<Map<String, Object>> list = iYyhClientService.dynamicSqlList(params, drgsSqlconfig.getDbid());
        return list;
    }

    @Override
    public Map<String, Object> tJPdfUpload(Map params) throws Exception {
        //默认医养护接口地址
        String YYHDZIP = "http://192.26.45.115:8889";
            /**
             *
             * 2.上传
             * 3.结果保存
             */
            //1.查询待上传的数据
        if(!ObjectUtils.isEmpty(params.get("begindate"))){
            params.put("begindate",DateUtil.parseDate(params.get("begindate")+""));
        }
        if(!ObjectUtils.isEmpty(params.get("enddate"))){
            params.put("enddate",DateUtil.parseDate(params.get("enddate")+""));
        }
        List<TjPDFVo> bgList = iYyhClientService.selectTJPDF(params);
        if(bgList.size()<=0){
            logger.error("没有需要上传的体检PDF报告");
            return null;
        }
        //获取医养护的基本入参信息:
        Map<String, Object> baseInfoMap = iYyhClientService.getYYHBaseInfo("LAQTJPDF");
        //调整为系统参数中的YYHDZIP
        YYHDZIP = baseInfoMap.get("YYHDZIP")+"";
        //循环上传
        for(int i=0;i<bgList.size();i++){
            TjPDFVo tjPDFVo = bgList.get(i);
            Map<String,Object> bgmx = JSON.parseObject(JSON.toJSONString(tjPDFVo), new TypeReference<Map<String, String>>() {});

            String WS01_00_018_01 = bgmx.get("WS01_00_018_01")+"";//体检编码
            //String unitid=bgmx.get("WS08_10_052_03")+"";//机构ID
            Map<String, Object> updateMap=new HashMap<String, Object>();
            updateMap.put("WS01_00_018_01", WS01_00_018_01);
            /*
             * 接口调用
             */
            bgmx.remove("BASE64");
            Map<String, Object> reqMap = new HashMap<String, Object>();
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.putAll(baseInfoMap);
            paramsMap.put("servicecode", "FJGHSJSCCJ:FJGHSJSCFW");
            paramsMap.put("standardcode", "");
            paramsMap.put("setdetails", bgmx);
            paramsMap.put("base64", Base64Utils.encodeToString(tjPDFVo.getBASE64()));
            reqMap = HZSYYHUtil.getPublicMessageTjPDF(paramsMap);
            String inputDataXml = com.bsoft.common.utils.XMLUtil.mapParseXML(reqMap, "messages");
            logger.error("体检报告PDF上传入参:"+inputDataXml);
            Map<String,String> inputMap = new HashMap();
            inputMap.put("xmlData", inputDataXml);
            String resStr;
            try {
                resStr = SoapClient.soapExec("resourceMethod",inputMap,"http://register.index.pt.hz.neusoft.com",
                        YYHDZIP+com.bsoft.common.utils.YyhbwjxUtil.FJGHSCFWJK, null,null);
                logger.error("体检报告PDF上传出参："+resStr);
            } catch (Exception e) {
                logger.error("接口调用异常:"+e.getMessage(),e);
                continue; //跳过后续保存
            }
            /*
             * 出参出理
             */
            Map<String, Object> outPutMap = com.bsoft.common.utils.XMLUtil.xmlToListMap(resStr);
            Map<String, Object>  switchsetMap = (Map<String, Object>) outPutMap.get("switchset");
            int messagecode = Tools.parseInt(switchsetMap.get("messagecode")==null?1:switchsetMap.get("messagecode")+"");
            if(messagecode<0){
                throw  new Exception("体检报告PDF上传失败返回参数："+resStr);
            }
            Map<String, Object> businessMap = (Map<String, Object>) outPutMap.get("business");
            Map<String,Object>businessdataMap=(Map<String, Object>) businessMap.get("businessdata");
            Map<String,Object> datasetsMap = (Map<String, Object>) businessdataMap.get("datasets");
            Object setdetails = datasetsMap.get("setdetails");
            Map<String, Object> result=new HashMap<String, Object>();
            if(setdetails instanceof List){
                if(((List<Map<String , Object>>)setdetails).size()== 0 ){
                    continue;
                }
                List<Map<String , Object>> setList = (List<Map<String , Object>>)setdetails;
                result=setList.get(0);
            }else if(setdetails instanceof Map){
                result=(Map<String, Object>) setdetails;
            }else{
                logger.error("获取setdetails异常");
                continue;
            }
            updateMap.put("CERTIFICATEID", result.get("certificateId")+"");
            int k = iYyhClientService.updateTjbgResult(updateMap);
            if(k>0){
                logger.error("更新CERTIFICATEID成功");
            }else{
                logger.error("更新CERTIFICATEID失败");
            }
        }
        return null;
    }

    @Override
    public PageInfo tjjlUploadQueryPage(DrgsRequestBody body) {
        Map<String, Object> params = body.getCondition();
        DrgsSqlconfig drgsSqlconfig = drgsSqlconfigMapper.
                selectDrgsSqlconfigByServiceId(params.get("type")+"", DrgsStatus.MAZH_MAIN.getCode());
        /**
         * 判断当前用户的机构id
         * 如果是0  则不加条件
         */
        String organizationId = SecurityUtils.getLoginUser().getUser().getDept().getOrganizationId();
        params.put("sql", drgsSqlconfig.getSqlcontent());
        if(!"0".equals(organizationId)&&null!=organizationId){
            params.put("sql", "select * from ("+drgsSqlconfig.getSqlcontent()+drgsSqlconfig.getRelationcolumn()+" ) b where b.WS08_10_052_18 = '"+organizationId+"'");
            params.put("orgCode",organizationId);
        }
        params.put("begindate",body.getBegindate());
        params.put("enddate",body.getEnddate());
//        查询结果
        params.put("limit",body.getPageSize());
        params.put("offset",body.getPageSize()*(body.getPageNum()-1));
        List<Map<String, Object>> list = iYyhClientService.selectTjjlList(params, drgsSqlconfig.getDbid());
        PageInfo pageInfo = new PageInfo(list);
        params.remove("limit");
        params.remove("offset");
        List<Map<String, Object>> list2 = iYyhClientService.selectTjjlList(params, drgsSqlconfig.getDbid());
        pageInfo.setTotal(list2.size());
        return pageInfo;
    }


    @Override
    public Map<String, Object> tJjlUpload(Map<String,Object> params) {
        Map<String,Boolean> result = new HashMap<>();
        params.put("sfqy",1);
        //拿到重传的sql
        YyhDataConfig cofnig = iYyhClientService.getYyhUpconfig(params);
        if(!ObjectUtils.isEmpty(cofnig)){
            //1.查询待上传的数据
            if(!ObjectUtils.isEmpty(params.get("begindate"))){
                params.put("begindate",DateUtil.parseDate(params.get("begindate")+""));
            }
            if(!ObjectUtils.isEmpty(params.get("enddate"))){
                params.put("enddate",DateUtil.parseDate(params.get("enddate")+""));
            }
            List<Map<String,Object>> list = iYyhClientService.selectYyhUploadList(cofnig.getSqlcontent(),params);
            if(list.size()<=0){
                logger.error("没有需要上传的体检PDF报告");
                new Exception("没有需要上传的体检PDF报告");
                return null;
            }

//            Date beginDate = new Date();
//            SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
//            SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMddHHmmss");
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            //
//            Calendar cal_time = Calendar.getInstance();
//            cal_time.set(Calendar.HOUR_OF_DAY,0);
//            cal_time.set(Calendar.MINUTE,0);
//            cal_time.set(Calendar.SECOND,0);
//            cal_time.set(Calendar.MILLISECOND,0);
//            Date d_start = cal_time.getTime();
//            String ls_begin = sf1.format(d_start);
//            String ls_end = sf2.format(beginDate);

            //上传语句-添加了是否已上传
            String sql = cofnig.getYyhUpsql();
            //主键更新语句
//            String up_Sql = cofnig.getYyhUpflagSql();
            if(sql != null && !"".equals(sql)) {
                String querySql = sql.trim();
                logger.error("检索语句" + querySql);
                if (list != null && list.size() > 0) {
                    //每个业务批次最大条数
                    Map<Integer, List<Map<String, Object>>> tMap = YyhbwjxUtil.getSubList(list, 100);
                    for (Integer key : tMap.keySet()) {
                        List<Map<String, Object>> tList = tMap.get(key);
                        uploadData(tList,cofnig);

                        String[] orgArrs = tList.stream().map(map->map.get("ws08_10_052_18")).toArray(String[]::new);

                        Map<String,String[]> glMap = new HashMap<>();
                        glMap.put("d.hzsjgdm",orgArrs);
                        String[] tjbhArrs = tList.stream().map(map->map.get("ws01_00_008_01")).toArray(String[]::new);
                        glMap.put("WS01_00_008_01",tjbhArrs);

                        // 健康体检实验室检验
                        params.put("setCode","C0104.0303.11");
                        YyhDataConfig sysjyCofnig = iYyhClientService.getYyhUpconfig(params);
                        List<Map<String,Object>> sysjyList = iYyhClientService.selectGlUploadList(sysjyCofnig,glMap);
                        uploadData(sysjyList,sysjyCofnig);
                        // 健康体检实验室检验常规报告
                        params.put("setCode","C0104.0303.1101");
                        YyhDataConfig syscgbgCofnig = iYyhClientService.getYyhUpconfig(params);
                        List<Map<String,Object>> syscgbgList = iYyhClientService.selectGlUploadList(syscgbgCofnig,glMap);
                        uploadData(syscgbgList,syscgbgCofnig);
                        // 健康体检异常摘要信息
                        params.put("setCode","C0104.0511.11");
                        YyhDataConfig yczyCofnig = iYyhClientService.getYyhUpconfig(params);
                        List<Map<String,Object>> yczyList = iYyhClientService.selectGlUploadList(yczyCofnig,glMap);
                        uploadData(yczyList,yczyCofnig);
                        // 健康体检辅助检查
                        params.put("setCode","C0104.0305.11");
                        YyhDataConfig fzjcCofnig = iYyhClientService.getYyhUpconfig(params);
                        List<Map<String,Object>> fzjcList = iYyhClientService.selectGlUploadList(fzjcCofnig,glMap);
                        uploadData(fzjcList,fzjcCofnig);
                        // 健康体检常规检查
                        params.put("setCode","C0104.0302.11");
                        YyhDataConfig cgjcCofnig = iYyhClientService.getYyhUpconfig(params);
                        List<Map<String,Object>> cgjcList = iYyhClientService.selectGlUploadList(cgjcCofnig,glMap);
                        uploadData(cgjcList,cgjcCofnig);

                        //
//                        Map<Integer, List<Map<String, Object>>> sMap = YyhDataAggr.getSubList(tList, 1000);//分单任务
//                        Map<String, Object> tparams = new HashMap<>();
//                        List<Map<String, Object>> totaldeclares = new ArrayList<>();
//                        Map<String, Object> totaldeclare = new HashMap<>();
//                        totaldeclare.put("colrescode", cofnig.getColrescode());//交换标准编码
//                        totaldeclare.put("tasknum", sMap.size());//任务数
//                        totaldeclare.put("begindatetime", ls_begin);//查询开始时间，这两个时间意义不大，故采用当天0时到现在
//                        totaldeclare.put("enddatetime", ls_end);//查询结束时间
//                        Map<String, Object> tdeclare = new HashMap<>();
//                        tdeclare.put("setcode", cofnig.getSetcode());//数据集编码
//                        tdeclare.put("datanum", tList.size());//数据集总数据量
//                        totaldeclare.put("tdeclare", tdeclare);
//                        totaldeclares.add(totaldeclare);
//                        tparams.put("totaldeclare", totaldeclares);
//                        //声明批次--请求
//                        String trs = yyhbw.batchService(tparams, ptsjlicence);
//                        //请求成功
//                        if (trs != null && yyhbw.checkBw(trs)) {
//                            String ttaskid = yyhbw.getTaskId(trs);
//                            for (Integer skey : sMap.keySet()) {
//                                List<Map<String, Object>> sList = sMap.get(skey);
//                                Map<String, Object> sparams = new HashMap<>();
//                                List<Map<String, Object>> singledeclares = new ArrayList<>();
//                                Map<String, Object> singledeclare = new HashMap<>();
//                                singledeclare.put("totaltaskid", ttaskid);
//                                singledeclare.put("colrescode", cofnig.getColrescode());
//                                singledeclare.put("sn", skey);
//                                Map<String, Object> declare = new HashMap<>();
//                                declare.put("setcode", cofnig.getSetcode());
//                                declare.put("datanum", sList.size());
//                                singledeclare.put("declare", declare);
//                                singledeclares.add(singledeclare);
//                                sparams.put("singledeclare", singledeclares);
//                                //单任务-请求
//                                String srs = yyhbw.taskService(sparams, ptsjlicence);
//                                //成功
//                                if (srs != null && yyhbw.checkBw(srs)) {
//                                    String staskid = yyhbw.getTaskId(srs);
//                                    //数据采集上传
//                                    Map<String, Object> uparams = new HashMap<>();
//                                    uparams.put("standardcode", cofnig.getColrescode());
//                                    uparams.put("daqtaskid", staskid);
//                                    Map<String, Object> dmp = new HashMap<>();
//                                    Map<String, Object> datasets = new HashMap<>();
//                                    datasets.put("setcode", cofnig.getSetcode());
//                                    datasets.put("settype", "");
//                                    Map<String, Object> map_readytoUP = readyToUp(sList);
//                                    List<Map<String, Object>> list_pushdata = (List<Map<String, Object>>) map_readytoUP.get(YYH_MAP_DATAKEY);
//                                    datasets.put("setdetails", list_pushdata);
//                                    dmp.put("datasets", datasets);
//                                    uparams.put("dmp", dmp);
//                                    //上传数据，临安无数据签名
//                                    String urs = yyhbw.uploadService(uparams, null, ptsjlicence);
//                                    Date endDate = new Date();
//                                    long time = (endDate.getTime() - beginDate.getTime()) / 1000;
//                                    logger.info(cofnig.getColrescode() + " ttaskid:" + ttaskid + "上传完成，记录时间：" + sdf.format(endDate) + "本次上传总共耗费：" + time + "秒");
//
//                                    List<String> list_prikeys = (List<String>) map_readytoUP.get(YYH_MAP_PRIKEY);
//
//                                    YYH_AGGR_RECORD record = new YYH_AGGR_RECORD();
//                                    record.setBEGINDATETIME(ls_begin);
//                                    record.setCOLRESCODE(cofnig.getColrescode());
//                                    record.setENDDATETIME(ls_end);
//
//                                    record.setSETCODE(cofnig.getSetcode());
//                                    record.setSINGLEDATANUM(Long.parseLong(sList.size() + ""));
//                                    record.setSINGLETASKID(staskid);
//                                    record.setSN(Long.parseLong(skey + ""));
//                                    record.setTASKNUM(Long.parseLong(sMap.size() + ""));
//                                    record.setTOTALDATANUM(Long.parseLong(tList.size() + ""));
//                                    record.setTOTALTASKID(ttaskid);
//                                    record.setUPLOADTIME(new Date());
//                                    if (urs != null && yyhbw.checkBw(urs)) {
//                                        String etaskid = yyhbw.getTaskId(urs);
//                                        record.setENDTASKID(etaskid);
//                                        //1代表成功；2失败；7是数据对账
//                                        record.setCLIENTID(1L);
//                                        int li_up_ecount = 0;
//                                        //变更上传记录，主键处理，仅上传成功的进行标志更新
//                                        for (String data_prikey : list_prikeys) {
//                                            String[] data_prikeys = data_prikey.split(YYH_DATA_PRIKEYSPLIT);
//                                            int li_index_size = data_prikeys.length;
//                                            String update_sql = up_Sql;//.replace(YYH_DATA_PRIKEY,data_prikey);
//                                            //增加任务号记录 2022年4月25日 10:09:34
//                                            if (update_sql.contains("bdbsingleid")) {
//                                                update_sql = update_sql.replace("bdbsingleid", staskid);
//                                            }
//                                            for (int i = 0; i < li_index_size; i++) {
//                                                update_sql = update_sql.replace("arg" + i, data_prikeys[i]);
//                                            }
//                                            int li_flag = iYyhClientService.updateUpdata(update_sql);
//                                            if (li_flag != 1) {
//                                                logger.error("更新语句："+update_sql);
//                                                li_up_ecount++;
//                                            }
//                                        }
//                                        logger.error(cofnig.getColrescode() + "推送成功:【" + list_pushdata.size() + "】，主键数据：【" + list_prikeys.size() + "】,主键变更失败：【" + li_up_ecount + "】");
//                                        result.put(cofnig.getColrescode(), true);
//                                        if (li_up_ecount > 0) {
//                                            iYyhClientService.saveErrorUpLog(cofnig.getSetcode(), list_prikeys.size(), li_up_ecount);
//                                        }
//                                    } else {
//                                        record.setENDTASKID(UUID.randomUUID().toString());
//                                        record.setCLIENTID(2L);
//                                        logger.error(cofnig.getColrescode() + "上传失败，ttaskid:" + ttaskid + "，总量：" + sList.size());
//                                        result.put(cofnig.getColrescode(), false);
//                                    }
//                                    iYyhClientService.saveRecordLog(record);
//                                    //休息5秒
//                                    //                                        Thread.sleep(5000);
//                                }
//                            }
//                        }


                    }
                } else {
                    logger.info(cofnig.getColrescode() + "上传失败,数据为空，sql：" + sql);
                    result.put(cofnig.getColrescode(), false);
                }
            }

        }

        return null;
    }

    /**
     * 上传记录作废
     *
     * @param params
     * @return
     */
    @Override
    public String discard(Map<String, Object> params) {
        Map<String,Boolean> result = new HashMap<>();
        params.put("sfqy",1);
        YyhDataConfig cofnig = iYyhClientService.getYyhUpconfig(params);
        if(!ObjectUtils.isEmpty(cofnig)){
            if(!ObjectUtils.isEmpty(params.get("begindate"))){
                params.put("begindate",DateUtil.parseDate(params.get("begindate")+""));
            }
            if(!ObjectUtils.isEmpty(params.get("enddate"))){
                params.put("enddate",DateUtil.parseDate(params.get("enddate")+""));
            }

            String sql = cofnig.getYyhZfUpSql(cofnig.getSqlcontent()+" and ws99_99_999_11 = '"+params.get("tjbh")+"'");
            //体检批量作废
/*
            String sql = cofnig.getYyhZfUpSql(cofnig.getSqlcontent()+" and ws99_99_999_11 in('3301850003_23032400046',\n" +
                    "'3301850003_23031400079',\n" +
                    "'3301850003_23031400011',\n" +
                    "'3301850003_23031300053',\n" +
                    "'3301850003_23022000019',\n" +
                    "'3301850003_23032400042',\n" +
                    "'3301850003_23031400074',\n" +
                    "'3301850003_23031300156',\n" +
                    "'3301850003_23031300110',\n" +
                    "'3301850003_23031400041',\n" +
                    "'3301850003_23032400225',\n" +
                    "'3301850003_23032400147',\n" +
                    "'3301850003_23032400075',\n" +
                    "'3301850003_23032300208',\n" +
                    "'3301850003_23031400205',\n" +
                    "'3301850003_23032400060',\n" +
                    "'3301850003_23032100072',\n" +
                    "'3301850003_23031400054',\n" +
                    "'3301850003_23032100270',\n" +
                    "'3301850003_23031400086',\n" +
                    "'3301850003_23022300078',\n" +
                    "'3301850003_23022100039',\n" +
                    "'3301850003_23032800058',\n" +
                    "'3301850003_23031300011',\n" +
                    "'3301850003_23032400035',\n" +
                    "'3301850003_23032400009',\n" +
                    "'3301850003_23031400073',\n" +
                    "'3301850003_23031300046',\n" +
                    "'3301850003_23022000002',\n" +
                    "'3301850003_23032400140',\n" +
                    "'3301850003_23032100161',\n" +
                    "'3301850003_23031400111',\n" +
                    "'3301850003_23031400045',\n" +
                    "'3301850003_23031300074',\n" +
                    "'3301850003_23031300058',\n" +
                    "'3301850003_23022300049',\n" +
                    "'3301850003_23022100036',\n" +
                    "'3301850003_23032400024',\n" +
                    "'3301850003_23031400121',\n" +
                    "'3301850003_23031300155',\n" +
                    "'3301850003_23031300031',\n" +
                    "'3301850003_23022200004',\n" +
                    "'3301850003_23031300103',\n" +
                    "'3301850003_23031400042',\n" +
                    "'3301850003_23031400019',\n" +
                    "'3301850003_23031300130',\n" +
                    "'3301850003_23031300039',\n" +
                    "'3301850003_23031300009',\n" +
                    "'3301850003_23030700089',\n" +
                    "'3301850003_23032100278',\n" +
                    "'3301850003_23031400139',\n" +
                    "'3301850003_23031400039',\n" +
                    "'3301850003_23031400088',\n" +
                    "'3301850003_23031300010',\n" +
                    "'3301850003_23030700009',\n" +
                    "'3301850003_23031400124',\n" +
                    "'3301850003_23031400001',\n" +
                    "'3301850003_23031300089',\n" +
                    "'3301850003_23021900021',\n" +
                    "'3301850003_23031400055',\n" +
                    "'3301850003_23022000022',\n" +
                    "'3301850003_23032400034',\n" +
                    "'3301850003_23032400029',\n" +
                    "'3301850003_23032100054',\n" +
                    "'3301850003_23032100095',\n" +
                    "'3301850003_23031400258',\n" +
                    "'3301850003_23031400065',\n" +
                    "'3301850003_23031400040',\n" +
                    "'3301850003_23031300072',\n" +
                    "'3301850003_23032100117',\n" +
                    "'3301850003_23031400069',\n" +
                    "'3301850003_23022300064',\n" +
                    "'3301850003_23022000024',\n" +
                    "'3301850003_23032400070',\n" +
                    "'3301850003_23031400043',\n" +
                    "'3301850003_23031400154',\n" +
                    "'3301850003_23031300020',\n" +
                    "'3301850003_23021600064',\n" +
                    "'3301850003_23032400053',\n" +
                    "'3301850003_23032400031',\n" +
                    "'3301850003_23031400153',\n" +
                    "'3301850003_23031400101',\n" +
                    "'3301850003_23031300209',\n" +
                    "'3301850003_23022000037',\n" +
                    "'3301850003_23031400049',\n" +
                    "'3301850003_23031400015',\n" +
                    "'3301850003_23032400066',\n" +
                    "'3301850003_23031400255',\n" +
                    "'3301850003_23031400136',\n" +
                    "'3301850003_23031400119',\n" +
                    "'3301850003_23031300080',\n" +
                    "'3301850003_23031300025',\n" +
                    "'3301850003_23031400126',\n" +
                    "'3301850003_23031400120',\n" +
                    "'3301850003_23031400023',\n" +
                    "'3301850003_23031400030',\n" +
                    "'3301850003_23031300179',\n" +
                    "'3301850003_23030700017',\n" +
                    "'3301850003_23022000020',\n" +
                    "'3301850003_23022000013',\n" +
                    "'3301850003_23031400145',\n" +
                    "'3301850003_23031400014',\n" +
                    "'3301850003_23031300015',\n" +
                    "'3301850003_23031300005',\n" +
                    "'3301850003_23031300008',\n" +
                    "'3301850003_23032400061',\n" +
                    "'3301850003_23032400040',\n" +
                    "'3301850003_23032400037',\n" +
                    "'3301850003_23022300052',\n" +
                    "'3301850003_23031400102',\n" +
                    "'3301850003_23031300019',\n" +
                    "'3301850003_23032100150',\n" +
                    "'3301850003_23031400132',\n" +
                    "'3301850003_23031400096',\n" +
                    "'3301850003_23031400095',\n" +
                    "'3301850003_23032100082',\n" +
                    "'3301850003_23031300054',\n" +
                    "'3301850003_23032400049',\n" +
                    "'3301850003_23022300060',\n" +
                    "'3301850003_23022300055',\n" +
                    "'3301850003_23022300047',\n" +
                    "'3301850003_23032400153',\n" +
                    "'3301850003_23032100235',\n" +
                    "'3301850003_23031400115',\n" +
                    "'3301850003_23031300024',\n" +
                    "'3301850003_23031400009',\n" +
                    "'3301850003_23031300118',\n" +
                    "'3301850003_23031300029',\n" +
                    "'3301850003_23031300133',\n" +
                    "'3301850003_23022300042',\n" +
                    "'3301850003_23022200029',\n" +
                    "'3301850003_23022000011',\n" +
                    "'3301850003_23032400055',\n" +
                    "'3301850003_23031300158',\n" +
                    "'3301850003_23031400050',\n" +
                    "'3301850003_23031300182',\n" +
                    "'3301850003_23031300139',\n" +
                    "'3301850003_23031300116',\n" +
                    "'3301850003_23032100048',\n" +
                    "'3301850003_23031300180',\n" +
                    "'3301850003_23031300147',\n" +
                    "'3301850003_23031300048',\n" +
                    "'3301850003_23022300088',\n" +
                    "'3301850003_23022000014',\n" +
                    "'3301850003_23032400139',\n" +
                    "'3301850003_23032400067',\n" +
                    "'3301850003_23032400012',\n" +
                    "'3301850003_23022000023',\n" +
                    "'3301850003_23032100107',\n" +
                    "'3301850003_23031400156',\n" +
                    "'3301850003_23031300034',\n" +
                    "'3301850003_23022200044',\n" +
                    "'3301850003_23022000021',\n" +
                    "'3301850003_23031400084',\n" +
                    "'3301850003_23032400121',\n" +
                    "'3301850003_23032100294',\n" +
                    "'3301850003_23022300093',\n" +
                    "'3301850003_23032400064',\n" +
                    "'3301850003_23031400002',\n" +
                    "'3301850003_23032400038',\n" +
                    "'3301850003_23031400168',\n" +
                    "'3301850003_23031400158',\n" +
                    "'3301850003_23031400146',\n" +
                    "'3301850003_23031400044',\n" +
                    "'3301850003_23031300026',\n" +
                    "'3301850003_23031300016',\n" +
                    "'3301850003_23031300018',\n" +
                    "'3301850003_23032400018',\n" +
                    "'3301850003_23032300169',\n" +
                    "'3301850003_23031400162',\n" +
                    "'3301850003_23031300022',\n" +
                    "'3301850003_23031300021',\n" +
                    "'3301850003_23022300090',\n" +
                    "'3301850003_23022000016',\n" +
                    "'3301850003_23032400052',\n" +
                    "'3301850003_23032400044',\n" +
                    "'3301850003_23032100147',\n" +
                    "'3301850003_23031400215',\n" +
                    "'3301850003_23022000009',\n" +
                    "'3301850003_23011200048',\n" +
                    "'3301850003_23032400056',\n" +
                    "'3301850003_23032400045',\n" +
                    "'3301850003_23031400037',\n" +
                    "'3301850003_23031300068',\n" +
                    "'3301850003_23031400108',\n" +
                    "'3301850003_23031400089',\n" +
                    "'3301850003_23032400160',\n" +
                    "'3301850003_23032400017',\n" +
                    "'3301850003_23031400063',\n" +
                    "'3301850003_23032400074',\n" +
                    "'3301850003_23031300085',\n" +
                    "'3301850003_23031300070',\n" +
                    "'3301850003_23032400146',\n" +
                    "'3301850003_23032100255',\n" +
                    "'3301850003_23031400027',\n" +
                    "'3301850003_23022000006',\n" +
                    "'3301850003_23031400129',\n" +
                    "'3301850003_23031400013',\n" +
                    "'3301850003_23031300113',\n" +
                    "'3301850003_23031300095',\n" +
                    "'3301850003_23030700075',\n" +
                    "'3301850003_23022300057',\n" +
                    "'3301850003_23022000054',\n" +
                    "'3301850003_23031300144',\n" +
                    "'3301850003_23031400005',\n" +
                    "'3301850003_23031300023',\n" +
                    "'3301850003_23031300013',\n" +
                    "'3301850003_23031400264',\n" +
                    "'3301850003_23031400006',\n" +
                    "'3301850003_23031400060',\n" +
                    "'3301850003_23022000012',\n" +
                    "'3301850003_23031400184',\n" +
                    "'3301850003_23031400036',\n" +
                    "'3301850003_23031300030',\n" +
                    "'3301850003_23031300004',\n" +
                    "'3301850003_23031300128',\n" +
                    "'3301850003_23022300066',\n" +
                    "'3301850003_23022100053',\n" +
                    "'3301850003_23031400012',\n" +
                    "'3301850003_23031400253',\n" +
                    "'3301850003_23031400017',\n" +
                    "'3301850003_23031300056',\n" +
                    "'3301850003_23031300028',\n" +
                    "'3301850003_23031300006',\n" +
                    "'3301850003_23022000017')");
*/

            params.remove("tjbh");
            List<Map<String,Object>> list = iYyhClientService.selectYyhUploadList(sql,params);

            StringBuffer sb_info = new StringBuffer();
            for(Map<String, Object> sresult : list){
                sb_info.append(sresult.get("mval").toString()).append(";");
            }

            SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMddHHmmss");
            String begindate =sf2.format(new Date());
            String enddate = sf2.format(new Date());
            String ls_now_time = sf2.format(new Date());
            Map<String, Object> zfUparams = new HashMap<>();
            Map<String, Object> zfdmp = new HashMap<>();
            Map<String, Object> zfDatasets = new HashMap<>();
            Map<String, Object> zf_req_busdata = new HashMap<>();

            zf_req_busdata.put("OrganizationCode", list.get(0).get("ws08_10_052_18"));
            zf_req_busdata.put("OrganizationName",list.get(0).get("ct08_10_052_18"));
            zf_req_busdata.put("DatasetCode",cofnig.getSetcode());
            zf_req_busdata.put("BlacklistTime",begindate);
            zf_req_busdata.put("BusinessCode",cofnig.getBusinessCode());
            zf_req_busdata.put("PackageSerialNumber",begindate+RandomUtil.randomString(8));
            zf_req_busdata.put("PackageNum","1");
            zf_req_busdata.put("DataNum",list.size()+"");
            zf_req_busdata.put("PackageSeq","1");
            zf_req_busdata.put("CurDataNum",list.size()+"");
            zf_req_busdata.put("PrimaryKey",cofnig.getZfzj());
            zf_req_busdata.put("PrimaryValue",sb_info.toString().substring(0,sb_info.length()-1));
            zf_req_busdata.put("CreateTime",ls_now_time);

            zfDatasets.put("setdetails", zf_req_busdata);
            zfDatasets.put("setcode", yyhbw.zfServiceCode);
            zfDatasets.put("settype", "");

            zfdmp.put("datasets", zfDatasets);

            zfUparams.put("standardcode","SJZF");
            zfUparams.put("daqtaskid", ls_now_time+"2211");
            zfUparams.put("dmp", zfdmp);

            Map<String,String> serviceParams = new HashMap<>();
            serviceParams.put("url",yyhbw.zffwUrl);
            serviceParams.put("serviceName",yyhbw.zfServiceName);
            serviceParams.put("namespace",yyhbw.zfNamespace);
            serviceParams.put("servicecode", yyhbw.zfServiceCode);
            String zjdzmx = yyhbw.zfService(zfUparams, null, ptsjlicence,serviceParams);
            logger.info("作废上传返回结果："+zjdzmx);
            if (zjdzmx != null && yyhbw.checkBw(zjdzmx)) {
                //                    String etaskid = yyhbw.getTaskId(urs);
                YYH_AGGR_RECORD record = new YYH_AGGR_RECORD();
                record.setBEGINDATETIME(begindate);
                record.setCOLRESCODE(cofnig.getColrescode());
                record.setENDDATETIME(enddate);
                //主键...
                record.setENDTASKID("ZF"+cofnig.getBusinessCode()+System.currentTimeMillis()+String.format("%04d", new Random().nextInt(9999)));
                record.setSETCODE(cofnig.getSetcode());
                record.setSINGLEDATANUM(Long.parseLong(list.size() + ""));
                record.setSINGLETASKID(ls_now_time);
                record.setSN(Long.parseLong("1"));
                record.setTASKNUM(Long.parseLong(list.size() + ""));
                record.setTOTALDATANUM(Long.parseLong(list.size() + ""));

                record.setUPLOADTIME(new Date());
                record.setCLIENTID(7L);
                //存放机构编号
                record.setTOTALTASKID(list.get(0).get("ws08_10_052_18")+"");
                iYyhClientService.saveRecordLog(record);
            }

        }
        return null;
    }

    /**
     * 将list中的值变为大写，同时提取主键
     * @param list
     * @return
     */
    private Map<String,Object> readyToUp(List<Map<String,Object>> list){
        HashMap<String,Object> mapdatas = new HashMap<>();
        List<String> list_prikey = new ArrayList<>();
        List<Map<String,Object>> list_newUp = new ArrayList<Map<String,Object>>();
//        是否需要使用高性能方法？
//        list.parallelStream().forEach(mapdata -> {});
        list.forEach(mapdata -> {
            HashMap<String,Object> map_data = new HashMap<>();
            mapdata.forEach((kk,vv) -> {
                if(kk.equals(YYH_DATA_PRIKEY)){
                    list_prikey.add(vv.toString());
                }else{
                    map_data.put(kk.toUpperCase(),(null == vv)?"":vv);
                }
            });
            list_newUp.add(map_data);
        });
        mapdatas.put(YYH_MAP_PRIKEY,list_prikey);
        mapdatas.put(YYH_MAP_DATAKEY,list_newUp);
        return mapdatas;
    }

    public void uploadData(List<Map<String, Object>> tList,YyhDataConfig cofnig){
        String up_Sql = cofnig.getYyhUpflagSql();
        Date beginDate = new Date();
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //
        Calendar cal_time = Calendar.getInstance();
        cal_time.set(Calendar.HOUR_OF_DAY,0);
        cal_time.set(Calendar.MINUTE,0);
        cal_time.set(Calendar.SECOND,0);
        cal_time.set(Calendar.MILLISECOND,0);
        Date d_start = cal_time.getTime();
        String ls_begin = sf1.format(d_start);
        String ls_end = sf2.format(beginDate);

        Map<Integer, List<Map<String, Object>>> sMap = YyhbwjxUtil.getSubList(tList, 1000);//分单任务
        Map<String, Object> tparams = new HashMap<>();
        List<Map<String, Object>> totaldeclares = new ArrayList<>();
        Map<String, Object> totaldeclare = new HashMap<>();
        totaldeclare.put("colrescode", cofnig.getColrescode());//交换标准编码
        totaldeclare.put("tasknum", sMap.size());//任务数
        totaldeclare.put("begindatetime", ls_begin);//查询开始时间，这两个时间意义不大，故采用当天0时到现在
        totaldeclare.put("enddatetime", ls_end);//查询结束时间
        Map<String, Object> tdeclare = new HashMap<>();
        tdeclare.put("setcode", cofnig.getSetcode());//数据集编码
        tdeclare.put("datanum", tList.size());//数据集总数据量
        totaldeclare.put("tdeclare", tdeclare);
        totaldeclares.add(totaldeclare);
        tparams.put("totaldeclare", totaldeclares);
        //声明批次--请求
        String trs = yyhbw.batchService(tparams, ptsjlicence);
        //请求成功
        if (trs != null && yyhbw.checkBw(trs)) {
            String ttaskid = yyhbw.getTaskId(trs);
            for (Integer skey : sMap.keySet()) {
                List<Map<String, Object>> sList = sMap.get(skey);
                Map<String, Object> sparams = new HashMap<>();
                List<Map<String, Object>> singledeclares = new ArrayList<>();
                Map<String, Object> singledeclare = new HashMap<>();
                singledeclare.put("totaltaskid", ttaskid);
                singledeclare.put("colrescode", cofnig.getColrescode());
                singledeclare.put("sn", skey);
                Map<String, Object> declare = new HashMap<>();
                declare.put("setcode", cofnig.getSetcode());
                declare.put("datanum", sList.size());
                singledeclare.put("declare", declare);
                singledeclares.add(singledeclare);
                sparams.put("singledeclare", singledeclares);
                //单任务-请求
                String srs = yyhbw.taskService(sparams, ptsjlicence);
                //成功
                if (srs != null && yyhbw.checkBw(srs)) {
                    String staskid = yyhbw.getTaskId(srs);
                    //数据采集上传
                    Map<String, Object> uparams = new HashMap<>();
                    uparams.put("standardcode", cofnig.getColrescode());
                    uparams.put("daqtaskid", staskid);
                    Map<String, Object> dmp = new HashMap<>();
                    Map<String, Object> datasets = new HashMap<>();
                    datasets.put("setcode", cofnig.getSetcode());
                    datasets.put("settype", "");
                    Map<String, Object> map_readytoUP = readyToUp(sList);
                    List<Map<String, Object>> list_pushdata = (List<Map<String, Object>>) map_readytoUP.get(YYH_MAP_DATAKEY);
                    datasets.put("setdetails", list_pushdata);
                    dmp.put("datasets", datasets);
                    uparams.put("dmp", dmp);

                    //上传数据，临安无数据签名
                    uparams.put("url",yyhbw.uploadServiceUrl);
                    Map<String,String> serviceParams = new HashMap<>();
                    serviceParams.put("url",yyhbw.uploadServiceUrl);
                    serviceParams.put("serviceName",yyhbw.uploadServiceName);
                    serviceParams.put("namespace",yyhbw.uploadServiceNamespace);
//                    serviceParams.put("servicecode", yyhbw.uploads);
                    serviceParams.put("servicecode",YyhbwjxUtil.UPLOAD_CODE);
                    String urs = yyhbw.uploadService(uparams, null, ptsjlicence,serviceParams);
                    Date endDate = new Date();
                    long time = (endDate.getTime() - beginDate.getTime()) / 1000;
                    logger.info(cofnig.getColrescode() + " ttaskid:" + ttaskid + "上传完成，记录时间：" + sdf.format(endDate) + "本次上传总共耗费：" + time + "秒");

                    List<String> list_prikeys = (List<String>) map_readytoUP.get(YYH_MAP_PRIKEY);

                    YYH_AGGR_RECORD record = new YYH_AGGR_RECORD();
                    record.setBEGINDATETIME(ls_begin);
                    record.setCOLRESCODE(cofnig.getColrescode());
                    record.setENDDATETIME(ls_end);

                    record.setSETCODE(cofnig.getSetcode());
                    record.setSINGLEDATANUM(Long.parseLong(sList.size() + ""));
                    record.setSINGLETASKID(staskid);
                    record.setSN(Long.parseLong(skey + ""));
                    record.setTASKNUM(Long.parseLong(sMap.size() + ""));
                    record.setTOTALDATANUM(Long.parseLong(tList.size() + ""));
                    record.setTOTALTASKID(ttaskid);
                    record.setUPLOADTIME(new Date());
                    if (urs != null && yyhbw.checkBw(urs)) {
                        String etaskid = yyhbw.getTaskId(urs);
                        record.setENDTASKID(etaskid);
                        //1代表成功；2失败；7是数据对账
                        record.setCLIENTID(1L);
                        int li_up_ecount = 0;
                        //变更上传记录，主键处理，仅上传成功的进行标志更新
                        for (String data_prikey : list_prikeys) {
                            String[] data_prikeys = data_prikey.split(YYH_DATA_PRIKEYSPLIT);
                            int li_index_size = data_prikeys.length;
                            String update_sql = up_Sql;//.replace(YYH_DATA_PRIKEY,data_prikey);
                            //增加任务号记录 2022年4月25日 10:09:34
                            if (update_sql.contains("bdbsingleid")) {
                                update_sql = update_sql.replace("bdbsingleid", staskid);
                            }
                            for (int i = 0; i < li_index_size; i++) {
                                update_sql = update_sql.replace("arg" + i, data_prikeys[i]);
                            }
                            int li_flag = iYyhClientService.updateUpdata(update_sql);
                            if (li_flag != 1) {
                                logger.error("更新语句："+update_sql);
                                li_up_ecount++;
                            }
                        }
                        logger.error(cofnig.getColrescode() + "推送成功:【" + list_pushdata.size() + "】，主键数据：【" + list_prikeys.size() + "】,主键变更失败：【" + li_up_ecount + "】");
//                        result.put(cofnig.getColrescode(), true);
                        if (li_up_ecount > 0) {
                            iYyhClientService.saveErrorUpLog(cofnig.getSetcode(), list_prikeys.size(), li_up_ecount);
                        }
                    } else {
                        record.setENDTASKID(UUID.randomUUID().toString());
                        record.setCLIENTID(2L);
                        logger.error(cofnig.getColrescode() + "上传失败，ttaskid:" + ttaskid + "，总量：" + sList.size());
//                        result.put(cofnig.getColrescode(), false);
                    }
                    iYyhClientService.saveRecordLog(record);
                }
            }
        }
    }

    @Override
    @DataSource(value = DataSourceType.THREE)
    public Map<String,Object> detailsData(Map<String,Object> params){
        List<TjBgjl>  tjBgjlData=  yyhDetailsDataMapper.getTjBgjlData(params);
        List<TjCgjc>  tjCgjcData=  yyhDetailsDataMapper.getTjCgjcData(params);
        List<TjFzjc>  tjFzjcData=  yyhDetailsDataMapper.getTjFzjcData(params);
        List<TjSysjy>  tjSysjyData=  yyhDetailsDataMapper.getTjSysjyData(params);
        List<TjSysjycgbg>  tjSysjycgbgData=  yyhDetailsDataMapper.getTjSysjycgbgData(params);
        List<TjYczyxx>  tjYczyxxData=  yyhDetailsDataMapper.getTjYczyxxData(params);

       return getGroupByDetailsData(tjBgjlData,tjCgjcData,tjFzjcData,tjSysjyData,tjSysjycgbgData,tjYczyxxData);
    }

    private Map<String, Object> getGroupByDetailsData( List<TjBgjl> tjBgjlData,
                                                            List<TjCgjc>  tjCgjcData,
                                                            List<TjFzjc>  tjFzjcData,
                                                            List<TjSysjy>  tjSysjyData,
                                                            List<TjSysjycgbg>  tjSysjycgbgData,
                                                            List<TjYczyxx>  tjYczyxxData) {

        List<Map<String,Object>> mapList = new  ArrayList<Map<String,Object>>();


        int tjbgjlcgjcsl = tjBgjlData.get(0).getWS99_99_241_654();
        int tjbgjlfzjcsl = tjBgjlData.get(0).getWS99_99_241_655();
        int tjbgsysjysl = tjBgjlData.get(0).getWS99_99_241_656();
        int tjbgyczyxxsl =  tjBgjlData.get(0).getWS99_99_241_658();
        long tjsysjymxsl = tjSysjyData.stream().mapToLong(TjSysjy::getWS99_99_241_657).sum();
        Map<String,Object> sjtsmap = new HashMap<>();//数据条分析
        sjtsmap.put("sjly","填报数");

        sjtsmap.put("tjcgjcsl",tjbgjlcgjcsl);
        sjtsmap.put("tjfzjcsl",tjbgjlfzjcsl);
        sjtsmap.put("tjsysjysl",tjbgsysjysl);
        sjtsmap.put("tjsysjymxsl",tjsysjymxsl);
        sjtsmap.put("tjyczyxxsl",tjbgyczyxxsl);



        int tjcgjcsl =  tjCgjcData.size();
        int tjfzjcsl =  tjFzjcData.size();
        int tjsysjysl = tjSysjyData.size();
        int tjsysjycgbgsl = tjSysjycgbgData.size();
        int tjyczyxxsl = tjYczyxxData.size();

        Map<String,Object> sjtsmap2 = new HashMap<>();//数据条分析
        sjtsmap2.put("sjly","记录数");
        sjtsmap2.put("tjcgjcsl",tjcgjcsl);
        sjtsmap2.put("tjfzjcsl",tjfzjcsl);
        sjtsmap2.put("tjsysjysl",tjsysjysl);
        sjtsmap2.put("tjsysjymxsl",tjsysjycgbgsl);
        sjtsmap2.put("tjyczyxxsl",tjyczyxxsl);

        mapList.add(sjtsmap);
        mapList.add(sjtsmap2);





        Map<String,Object> sjmxmap = new HashMap<>();//数据条分析

        sjmxmap.put("tjBgjlData",tjBgjlData);
        sjmxmap.put("tjCgjcData",tjCgjcData);
        sjmxmap.put("tjFzjcData",tjFzjcData);
        sjmxmap.put("tjSysjyData",tjSysjyData);
        sjmxmap.put("tjSysjycgbgData",tjSysjycgbgData);
        sjmxmap.put("tjYczyxxData",tjYczyxxData);

        Map<String,Object> datamap = new HashMap<>();//数据条分析

        datamap.put("map1",mapList);
        datamap.put("map2",sjmxmap);




        return datamap;
    }


}





