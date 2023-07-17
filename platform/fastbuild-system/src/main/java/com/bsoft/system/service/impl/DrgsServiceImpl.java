package com.bsoft.system.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONObject;
import com.bsoft.common.enums.drgs.DrgsStatus;
import com.bsoft.common.utils.BeanMapUtils;
import com.bsoft.common.utils.DateUtils;
import com.bsoft.common.utils.SecurityUtils;
import com.bsoft.common.utils.StringUtils;
import com.bsoft.common.utils.drgs.GjybUtils;
import com.bsoft.common.utils.drgs.PcInfoUtils;
import com.bsoft.system.domain.DrgsSqlconfig;
import com.bsoft.system.domain.drgs.*;
import com.bsoft.system.mapper.*;
import com.bsoft.system.service.IDrgsService;
import com.bsoft.system.service.ISysDynamicSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.bsoft.common.utils.PageUtils.startPage;

@Service
public class DrgsServiceImpl implements IDrgsService {
    /**
     * 签到编号
     */
    private static String SIGN_NO = "";
    String LOCAL_JGID = "1";
    String REQ_INFNO = "infno";
    String REQ_SIGN_NO = "sign_no";
    String REQ_OPTER_NO = "opter_no";
    String REQ_INPUT_KEY = "input";
    //------------------
    String RES_CODE_KEY = "infcode";
    String RES_CODE_SUCCESS = "0";
    String RES_ERR_MSG = "err_msg";
    String RES_OUTPUT_KEY = "output";

    String MAZH_CONECT = "|";
    String MAZH_MAIN = "main";
    String ERROR_RES = "error: ";

    String TIMECOLUMN = "TIMECOLUMN";
    String TIMECOLUMN_NULL = "无";
    //日志打印
    private static final Logger log = LoggerFactory.getLogger(DrgsServiceImpl.class);
    @Value("${gjyb.baseip}")
    private String baseip;
    @Value("${gjyb.key}")
    private String HOSIDKEY;
    @Value("${gjyb.jgmc}")
    private String HOSNAME;
    @Value("${gjyb.secret}")
    private String HOSSECRET;
    @Value("${gjyb.opter}")
    private String OPTER;

    @Value("${gjyb.jgid}")
    private String JGID;
    @Value("${gjyb.localadmvs}")
    private String localadvms;
    @Value("${gjyb.9001}")
    private String signInUrl;
    @Value("${gjyb.9002}")
    private String signOutUrl;
    @Value("${gjyb.4101A}")
    private String ylbzqdUrl;
    @Value("${gjyb.4102}")
    private String updYlbzqdUrl;
    @Value("${gjyb.4205}")
    private String zfbrxxUrl;
    @Value("${gjyb.4206}")
    private String zfbrcxUrl;
    @Value("${gjyb.4103}")
    private String ybjsxxUrl;
    @Value("${gjyb.4401}")
    private String basyUrl;
    @Value("${gjyb.4402}")
    private String zyyzUrl;
    @Value("${gjyb.4701}")
    private String dzblUrl;
    @Value("${gjyb.startAreaUpload}")
    private boolean startAreaUpload;
    @Value("${gjyb.master}")
    private String DBID;

    @Autowired
    private DrgsSqlconfigMapper drgsSqlconfigMapper;
    @Autowired
    private ISysDynamicSourceService iSysDynamicSourceService;
    @Autowired
    private GJYBMapper gjybMapper;

    @Autowired
    private GJYBPUSHLOGMapper gjybpushlogMapper;
    @Autowired
    private GJYBPUSHLOG_TSMapper gjybpushlog_tsMapper;
    @Autowired
    private GJYBZYBAPUSHLOGMapper gjybzybapushlogMapper;
    @Autowired
    private GJYBDZBLPUSHLOGMapper gjybdzblpushlogMapper;
    @Autowired
    private GJYBZYYZPUSHLOGMapper gjybzyyzpushlogMapper;

    @Override
    public List<Map<String, Object>> getJjqsqdList(DrgsRequestBody body) {
        Map<String, Object> params = body.getCondition();
        DrgsSqlconfig drgsSqlconfig = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(params.get("type")+"", DrgsStatus.MAZH_MAIN.getCode());
        //TODO  需要判断空值
        /**
         * 判断当前用户的机构id
         * 如果是0  则不加条件
         */
        String organizationId = SecurityUtils.getLoginUser().getUser().getDept().getOrganizationId();
        params.put("sql", drgsSqlconfig.getSqlcontent());
        if(!"0".equals(organizationId)&&null!=organizationId){
            params.put("sql", "select * from ("+drgsSqlconfig.getSqlcontent()+" ) b where b.jgid = '"+organizationId+"'");
        }
        //日期向后推一天
        params.put("enddate", DateUtils.dateAdd(body.getEnddate(), 1));
        params.put("begindate", body.getBegindate());
        //查询结果
        startPage(body.getPageNum(),body.getPageSize());
        List<Map<String, Object>> list = iSysDynamicSourceService.dynamicSqlList(params, drgsSqlconfig.getDbid());
        return list;
    }

    @Override
    public List<Map<String, Object>> getJjqsqdListForEcharts(DrgsRequestBody body) {
        Map<String, Object> params = body.getCondition();
        DrgsSqlconfig drgsSqlconfig = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(params.get("type")+"", DrgsStatus.MAZH_MAIN.getCode());
        //TODO  需要判断空值
        /**
         * 判断当前用户的机构id
         * 如果是0  则不加条件
         */
        String organizationId = SecurityUtils.getLoginUser().getUser().getDept().getOrganizationId();
        params.put("sql", drgsSqlconfig.getSqlcontent());
        if(!"0".equals(organizationId)&&null!=organizationId){
            params.put("sql", "select * from ("+drgsSqlconfig.getSqlcontent()+" ) b where b.jgid = '"+organizationId+"'");
        }
        //日期向后推一天
        params.put("enddate", DateUtils.dateAdd(body.getEnddate(), 1));
        params.put("begindate", body.getBegindate());
        //查询结果
        List<Map<String, Object>> list = iSysDynamicSourceService.dynamicSqlList(params, drgsSqlconfig.getDbid());
        return list;
    }

    @Override
    public List<Map<String, Object>> getJjqsqdListExport(DrgsRequestBody body) {
        Map<String, Object> params = body.getCondition();
        DrgsSqlconfig drgsSqlconfig = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(params.get("type")+"", DrgsStatus.MAZH_MAIN.getCode());
        //TODO  需要判断空值
        /**
         * 判断当前用户的机构id
         * 如果是0  则不加条件
         */
        String organizationId = SecurityUtils.getLoginUser().getUser().getDept().getOrganizationId();
        params.put("sql", drgsSqlconfig.getSqlcontent());
        if(!"0".equals(organizationId)&&null!=organizationId){
            params.put("sql", "select * from ("+drgsSqlconfig.getSqlcontent()+" ) b where b.jgid = '"+organizationId+"'");
        }
        //日期向后推一天
        params.put("enddate", DateUtils.dateAdd(body.getEnddate(), 1));
        params.put("begindate", body.getBegindate());
        //查询结果
        List<Map<String, Object>> list = iSysDynamicSourceService.dynamicSqlList(params, drgsSqlconfig.getDbid());
        return list;
    }


    /**
     * 4101单条记录上传
     *
     * @param body
     * @return
     */
    @Override
    public Map<String, Object> pushOneJjqd(DrgsRequestBody body) {
        Map<String, Object> result_map = new HashMap<>();
        String result = "0";
        String error = "";
        String ls_funid = "4101A";
        boolean autoflag = true;
        DrgsSqlconfig mainConfig = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, DrgsStatus.MAZH_MAIN.getCode());
        Map<String, Object> params = body.getCondition();
        String URL = getRequestUrl(ylbzqdUrl);
        DrgsSqlconfig setlinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, "setlinfo");
        DrgsSqlconfig opspdiseinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, "opspdiseinfo");
        DrgsSqlconfig diseinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, "diseinfo");
        DrgsSqlconfig bldinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, "bldinfo");
        DrgsSqlconfig icuinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, "icuinfo");
        DrgsSqlconfig oprninfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, "oprninfo");
        String ls_mainSql = mainConfig.getSqlcontent();
        if (null == ls_mainSql || ls_mainSql.isEmpty()) {
            log.error(ls_funid + "主语句未配置，任务不执行。");
            error = "4101A主语句未配置，任务不执行。";
            result_map.put("code",-1);
            result_map.put("msg",error);
            return result_map;
        } else {
            //先检索出需要上传的人员信息
            String ls_query_person = "";
            //获取参数
            String ls_main_key = mainConfig.getRelationcolumn().trim();
            if (autoflag) {
                ls_query_person = ls_mainSql;
                //TODO 此处为单个病人上传时，autoflag设置为true了
                if (params.get("zyh") != null) {
                    String sql = StringUtils.numberOrString(params.get("zyh"))==false?"'"+params.get("zyh")+"'":params.get("zyh")+"";
                    ls_query_person = "select * " + " from (" + ls_mainSql + ") a where a.\"" + ls_main_key + "\"= " + sql;
                }
            } else {
                //TODO 手工上传，目前未实现
                ls_query_person = "select a.\"" + ls_main_key + "\" from (" + ls_mainSql + ") a where a.\"setl_begn_date\">= :begindate and a.\"setl_begn_date\" <= :enddate";
            }
            List<Map<String, Object>> list_main = iSysDynamicSourceService.dynamicReportList(ls_query_person, mainConfig.getDbid());
            if (null == list_main || list_main.isEmpty()) {
                log.info(ls_funid + "本次无数据，执行语句---{}，查询条件--{}", ls_query_person, params);
                error = "4101A主语句未查询到数据。";
                result_map.put("code",-1);
                result_map.put("msg",error);
            } else {
                for (Map<String, Object> mainData : list_main) {
                    HashMap<String, Object> hm_input = new LinkedHashMap<>();
                    Map<String, Object> map_main = mainData;
                    //setlinfo   sql   TODO 判断是否为空
                    String setlinfo_sql = setlinfo.getSqlcontent();
                    String setlinfo_key = setlinfo.getRelationcolumn();
                    String sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                    String ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                    List<Map<String, Object>> list_baseInfo = iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid());
                    if (list_baseInfo == null || list_baseInfo.isEmpty()) {
                        log.error(ls_funid + "-未检索到数据：【" + setlinfo.getRelationcolumn().trim() + "】，值：" + map_main);
                        error = ls_funid + "4101A-未检索到数据：【" + setlinfo.getRelationcolumn().trim() + "】，值：" + map_main;
                        continue;
                    }
                    hm_input.put("setlinfo", list_baseInfo.get(0));
                    setlinfo_sql = opspdiseinfo.getSqlcontent();
                    setlinfo_key = opspdiseinfo.getRelationcolumn();
                    sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                    ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                    hm_input.put("opspdiseinfo", iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid()));
                    //住院诊断信息   TODO 可以优化
                    setlinfo_sql = diseinfo.getSqlcontent();
                    setlinfo_key = diseinfo.getRelationcolumn();
                    sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                    ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                    hm_input.put("diseinfo", iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid()));
                    //
                    setlinfo_sql = oprninfo.getSqlcontent();
                    setlinfo_key = oprninfo.getRelationcolumn();
                    sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                    ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                    hm_input.put("oprninfo", iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid()));
                    setlinfo_sql = icuinfo.getSqlcontent();
                    setlinfo_key = icuinfo.getRelationcolumn();
                    sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                    ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                    hm_input.put("icuinfo", iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid()));
                    setlinfo_sql = bldinfo.getSqlcontent();
                    setlinfo_key = bldinfo.getRelationcolumn();
                    sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                    ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                    hm_input.put("bldinfo", iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid()));
                    String hOSIDKEY = HOSIDKEY;
                    String hOSNAME = HOSNAME;
                    String oPTER = OPTER;
                    String hOSSECRET = HOSSECRET;
                    String insdadvms = localadvms; //参保地
                    String medadvms = localadvms;//就医地
                    String jgid = "1";//机构id
                    if (startAreaUpload == true) {
                        hOSIDKEY = map_main.get("HOSIDKEY") + "";
                        hOSNAME = map_main.get("HOSNAME") + "";
                        oPTER = map_main.get("OPTER") + "";
                        hOSSECRET = map_main.get("HOSSECRET") + "";
                        insdadvms = map_main.get("INSDADVMS") + "";
                        medadvms = map_main.get("MEDADVMS") + "";
                        jgid = map_main.get("JGID") + "";
                    }
                    String sign_no = initSign(jgid, oPTER);
                    String ls_reqJson = getRequesJson(ls_funid, hm_input, medadvms, insdadvms, hOSIDKEY, hOSNAME, oPTER, sign_no);
                    String ls_resJson = GjybUtils.sentServiceJson(URL, ls_reqJson, hOSIDKEY, hOSSECRET);
                    JSONObject jo_res = GjybUtils.readResJson(ls_resJson);
                    //日志保存
                    GJYB_JJQD_PUSHLOG pushlog = new GJYB_JJQD_PUSHLOG();
                    pushlog.setUUID(IdUtil.simpleUUID());
                    pushlog.setPSN_NO((null == list_baseInfo.get(0).get("PSN_NO")) ? "-" : list_baseInfo.get(0).get("PSN_NO") + "");
                    //推送时间更新
                    pushlog.setPUSHDATE(new Date());
                    //
                    pushlog.setBUSID(map_main.get(ls_main_key) + "");
                    pushlog.setFUNID(ls_funid);
                    if ("0".equals(jo_res.getString(RES_CODE_KEY))) {
                        JSONObject jo_output = jo_res.getJSONObject(RES_OUTPUT_KEY);
//                            实际上没有data节点
//                            JSONObject jo_id = jo_output.getJSONObject("data");
                        pushlog.setRESID(jo_output.getString("setl_list_id"));
                        result_map.put("setl_list_id", jo_output.getString("setl_list_id"));
                        pushlog.setSCSHBZ("1");
                        result_map.put("code",0);
                        result_map.put("msg","成功");
                    } else {
                        log.error(jo_res.toJSONString());
                        String ls_error_msg = ERROR_RES + jo_res.getString(RES_ERR_MSG);
                        ls_error_msg = (ls_error_msg.length() > 1700) ? ls_error_msg.substring(0, 1700) + "-logfile" : ls_error_msg;
                        if(ls_error_msg.indexOf("状态为:1")>-1){
                            //这个状态表示已经上传过
                            pushlog.setRESID("已上传");
                            pushlog.setSCSHBZ("1");
                        }else{
                            pushlog.setRESID(ls_error_msg);
                            pushlog.setSCSHBZ("0");
                        }
                        error = "4101A医保上传异常：" + jo_res.toJSONString();
                        result_map.put("code",-1);
                        result_map.put("msg",error);
                    }
                    if(iSysDynamicSourceService.getDynamicSqlMapper(gjybpushlogMapper,DBID,pushlog.getBUSID())!=null){
                        iSysDynamicSourceService.dynamicSqlMapper(gjybpushlogMapper,DBID,pushlog);
                    }else{
                        iSysDynamicSourceService.dynamicSqlMapperSave(gjybpushlogMapper,DBID,pushlog);
                    }
                }
            }
        }
        return result_map;
    }

    public Map<String,Object> pushDzbl4701(DrgsRequestBody body, boolean autoflag) {
        String ls_funid = "4701";
        Map<String,Object> result_map = new HashMap<>();
        Map<String, Object> params = body.getCondition();
        String result = "0";
        String error = "";
        try {
            String URL = getRequestUrl(dzblUrl);
            DrgsSqlconfig mainConfig = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid , MAZH_MAIN);
            DrgsSqlconfig adminfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid ,   "adminfo");
            DrgsSqlconfig coursrinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid ,   "coursrinfo");
            DrgsSqlconfig dieinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid ,   "dieinfo");
            DrgsSqlconfig diseinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid ,   "diseinfo");
            DrgsSqlconfig oprninfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid ,   "oprninfo");
            DrgsSqlconfig rescinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid ,   "rescinfo");
            DrgsSqlconfig dscginfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid ,   "dscginfo");
            String ls_mainSql = mainConfig.getSqlcontent();
            if (null == ls_mainSql || ls_mainSql.isEmpty()) {
                log.info(ls_funid + "主语句未配置，任务不执行。");
                error = "4701主语句未配置，任务不执行。";
            } else {
                //先检索出需要上传的人员信息
                String ls_query_person = "";
                String ls_main_key = mainConfig.getRelationcolumn().trim();
//                Map<String, Object> params = new HashMap<>();
                if (autoflag) {
                    ls_query_person =ls_mainSql;// "select a.\"" + ls_main_key + "\" from (" + ls_mainSql + ") a LEFT JOIN gjyb_dzbl_pushlog b on a.\"" + ls_main_key + "\" = b.busid where b.busid is null";
                    if(params.get("zyh") != null){
                        String sql = StringUtils.numberOrString(params.get("zyh"))==false?"'"+params.get("zyh")+"'":params.get("zyh")+"";
                        ls_query_person = "select * " + " from (" + ls_mainSql + ") a where a.\"" + ls_main_key + "\"= "+sql;
                    }
                } else {
                    ls_query_person = "select a.\"" + ls_main_key + "\" from (" + ls_mainSql + ") a where a.\"eval_time\">= :begindate and a.\"eval_time\" <= :enddate";
//                    params = aparams;//new HashMap<>();
//                    params.put("begindate", getPreData(-7));
                }
                try {
                    List<Map<String, Object>> list_main = iSysDynamicSourceService.dynamicReportList(ls_query_person, mainConfig.getDbid());
                    if (null == list_main || list_main.isEmpty()) {
//                        if(!autoflag){
                        log.info(ls_funid + "本次无数据，执行语句---{}，查询条件--{}",ls_query_person,params);
                        error = "4701主语句未查询到数据";
//                        }
//                        log.info(ls_funid + "本次无数据，执行类型--{}", (autoflag) ? "计划任务" : "手工执行");
                    }else{
                        for (Map<String, Object> mainData : list_main) {
                            HashMap<String, Object> hm_input = new LinkedHashMap<>();
                            //根据主键获取一条数据
                            String setlinfo_sql = adminfo.getSqlcontent();
                            String setlinfo_key = adminfo.getRelationcolumn();
                            String sql = StringUtils.numberOrString(mainData.get(setlinfo_key))==false?"'"+mainData.get(setlinfo_key)+"'":mainData.get(setlinfo_key)+"";
                            String ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                            Map<String, Object> map_main = mainData;
//                            List<Map<String, Object>> list_baseInfo = getRelationData(adminfo, getGjybDbConfig(), map_main, true);
                            List<Map<String, Object>> list_baseInfo = iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid());
                            if(list_baseInfo == null || list_baseInfo.isEmpty()){
                                log.error(ls_funid + "-未检索到数据：【"+adminfo.getRelationcolumn().trim()+"】，值："+map_main);
                                error = ls_funid + "4701-未检索到数据：【"+adminfo.getRelationcolumn().trim()+"】，值："+map_main;
                                continue;
                            }
                            hm_input.put("adminfo", BeanMapUtils.ClobToString(list_baseInfo.get(0)));
                            setlinfo_sql = coursrinfo.getSqlcontent();
                            setlinfo_key = coursrinfo.getRelationcolumn();
                            sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                            ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;

                            hm_input.put("coursrinfo", BeanMapUtils.ClobToString1(iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid())));
                            setlinfo_sql = dieinfo.getSqlcontent();
                            setlinfo_key = dieinfo.getRelationcolumn();
                            sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                            ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                            hm_input.put("dieinfo", BeanMapUtils.ClobToString1(iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid())));
                            setlinfo_sql = diseinfo.getSqlcontent();
                            setlinfo_key = diseinfo.getRelationcolumn();
                            sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                            ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                            hm_input.put("diseinfo", BeanMapUtils.ClobToString1(iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid())));
                            setlinfo_sql = oprninfo.getSqlcontent();
                            setlinfo_key = oprninfo.getRelationcolumn();
                            sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                            ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                            hm_input.put("oprninfo", BeanMapUtils.ClobToString1(iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid())));
                            setlinfo_sql = rescinfo.getSqlcontent();
                            setlinfo_key = rescinfo.getRelationcolumn();
                            sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                            ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                            hm_input.put("rescinfo", BeanMapUtils.ClobToString1(iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid())));
                            setlinfo_sql = dscginfo.getSqlcontent();
                            setlinfo_key = dscginfo.getRelationcolumn();
                            sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                            ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                            hm_input.put("dscginfo", BeanMapUtils.ClobToString1(iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid())));
                            String hOSIDKEY  =HOSIDKEY;
                            String   hOSNAME = HOSNAME;
                            String oPTER = OPTER;
                            String  hOSSECRET=HOSSECRET;
                            String insdadvms = localadvms; //参保地
                            String medadvms = localadvms;//就医地
                            String jgid = "1";//机构id
                            if(startAreaUpload==true){
                                hOSIDKEY =map_main.get("HOSIDKEY")+"";
                                hOSNAME =map_main.get("HOSNAME")+"";
                                oPTER =map_main.get("OPTER")+"";
                                hOSSECRET = map_main.get("HOSSECRET")+"";
                                insdadvms = map_main.get("INSDADVMS")+"";
                                medadvms = map_main.get("MEDADVMS")+"";
                                jgid= map_main.get("JGID")+"";
                            }
                            String sign_no=initSign(jgid,oPTER);
                            String ls_reqJson = getRequesJson(ls_funid, hm_input,medadvms,insdadvms,hOSIDKEY,hOSNAME,oPTER,sign_no);
                            String ls_resJson = GjybUtils.sentServiceJson(URL, ls_reqJson, hOSIDKEY, hOSSECRET);
                            JSONObject jo_res = GjybUtils.readResJson(ls_resJson);

                            GJYB_DZBL_PUSHLOG pushlog = new GJYB_DZBL_PUSHLOG();
                            pushlog.setUUID(IdUtil.simpleUUID());
                            pushlog.setPSN_NO((null == list_baseInfo.get(0).get("psn_no")) ? "-" : list_baseInfo.get(0).get("psn_no")+"");
                            pushlog.setPUSHDATE(new Date());
                            //
                            pushlog.setBUSID(map_main.get(ls_main_key)+"");
                            if ("0".equals(jo_res.getString(RES_CODE_KEY))) {
                                pushlog.setRESID("上传成功");
                                pushlog.setSCSHBZ("2");
                                result = "1";
                            } else {
                                log.error(jo_res.toJSONString());
                                String ls_error_msg = ERROR_RES + jo_res.getString(RES_ERR_MSG);
                                ls_error_msg = (ls_error_msg.length() > 1700) ? ls_error_msg.substring(0, 1700) + "-logfile" : ls_error_msg;
                                pushlog.setRESID(ls_error_msg);
                                pushlog.setSCSHBZ("0");
                                error = "4701医保上传异常：" + jo_res.toJSONString();
                            }
                            pushlog.setFUNID(ls_funid);
                            if(iSysDynamicSourceService.getDynamicSqlMapper(gjybdzblpushlogMapper,DBID,pushlog.getBUSID())!=null){
                                iSysDynamicSourceService.dynamicSqlMapper(gjybdzblpushlogMapper,DBID,pushlog);
                            }else{
                                iSysDynamicSourceService.dynamicSqlMapperSave(gjybdzblpushlogMapper,DBID,pushlog);
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("[检索语句{}，条件{}]", ls_query_person, autoflag);
                    log.error(ls_funid + "-异常信息：" + e.getMessage(), e);
                    error = ls_funid + "4701-异常信息：" + e.getMessage();
                }
            }
        } catch (Exception e) {
            log.error(ls_funid + "-异常信息：" + e.getMessage(), e);
            error = ls_funid + "4701-异常信息：" + e.getMessage();
        }finally {
            result_map.put("result",result);
            result_map.put("error",error);
            return result_map;
        }
    }

    @Override
    public Map<String, Object> pushBatchDzbl4701(DrgsRequestBody body, boolean autoflag) {
        String ls_funid = "4701";
        Map<String,Object> result_map = new HashMap<>();
        Map<String, Object> params = body.getCondition();
        String result = "0";
        String error = "";
        try {
            String URL = getRequestUrl(dzblUrl);
            DrgsSqlconfig mainConfig = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid , MAZH_MAIN);
            DrgsSqlconfig adminfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid ,   "adminfo");
            DrgsSqlconfig coursrinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid ,   "coursrinfo");
            DrgsSqlconfig dieinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid ,   "dieinfo");
            DrgsSqlconfig diseinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid ,   "diseinfo");
            DrgsSqlconfig oprninfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid ,   "oprninfo");
            DrgsSqlconfig rescinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid ,   "rescinfo");
            DrgsSqlconfig dscginfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid ,   "dscginfo");
            String ls_mainSql = mainConfig.getSqlcontent();
            if (null == ls_mainSql || ls_mainSql.isEmpty()) {
                log.info(ls_funid + "主语句未配置，任务不执行。");
                error = "4701主语句未配置，任务不执行。";
            } else {
                //先检索出需要上传的人员信息
                String ls_query_person = "";
                String ls_main_key = mainConfig.getRelationcolumn().trim();
                if (autoflag) {
                    ls_query_person =ls_mainSql;// "select a.\"" + ls_main_key + "\" from (" + ls_mainSql + ") a LEFT JOIN gjyb_dzbl_pushlog b on a.\"" + ls_main_key + "\" = b.busid where b.busid is null";
                    if(params.get("zyh") != null){
                        String sql = StringUtils.numberOrString(params.get("zyh"))==false?"'"+params.get("zyh")+"'":params.get("zyh")+"";
                        ls_query_person = "select * " + " from (" + ls_mainSql + ") a where a.\"" + ls_main_key + "\"= "+sql;
                    }
                } else {
                    //获取参数
                    //TODO 需要优化
                    DrgsSqlconfig drgsSqlconfig = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId("DZBL", DrgsStatus.MAZH_MAIN.getCode());
                    Map<String, Object> params1 = body.getCondition();
                    //TODO  需要判断空值
                    /**
                     * 判断当前用户的机构id
                     * 如果是0  则不加条件
                     */
                    String organizationId = SecurityUtils.getLoginUser().getUser().getDept().getOrganizationId();
                    params1.put("sql", drgsSqlconfig.getSqlcontent());
                    if(!"0".equals(organizationId)&&null!=organizationId){
                        params1.put("sql", "select * from ("+drgsSqlconfig.getSqlcontent()+" ) b where b.jgid = '"+organizationId+"'");
                    }
                    //日期向后推一天
                    params1.put("enddate", DateUtils.dateAdd(body.getEnddate(), 1));
                    params1.put("begindate", body.getBegindate());
                    //查询结果
                    List<Map<String, Object>> list = iSysDynamicSourceService.dynamicSqlList(params1, drgsSqlconfig.getDbid());
                    try {
                        for(int i=0;i<list.size();i++){
                            String sql_1 = StringUtils.numberOrString(list.get(i).get("zyh"))==false?"'"+list.get(i).get("zyh")+"'":list.get(i).get("zyh")+"";
                            ls_query_person = "select * " + " from (" + ls_mainSql + ") a where a.\"" + ls_main_key + "\"= "+sql_1;
                            List<Map<String, Object>> list_main = iSysDynamicSourceService.dynamicReportList(ls_query_person, mainConfig.getDbid());
                            if (null == list_main || list_main.isEmpty()) {
                                log.info(ls_funid + "本次无数据，执行语句---{}，查询条件--{}",ls_query_person,list.get(i));
                                error = "4701主语句未查询到数据";
                            }else{
                                for (Map<String, Object> mainData : list_main) {
                                    HashMap<String, Object> hm_input = new LinkedHashMap<>();
                                    //根据主键获取一条数据
                                    String setlinfo_sql = adminfo.getSqlcontent();
                                    String setlinfo_key = adminfo.getRelationcolumn();
                                    String sql = StringUtils.numberOrString(mainData.get(setlinfo_key))==false?"'"+mainData.get(setlinfo_key)+"'":mainData.get(setlinfo_key)+"";
                                    String ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                                    Map<String, Object> map_main = mainData;
                                    List<Map<String, Object>> list_baseInfo = iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid());
                                    if(list_baseInfo == null || list_baseInfo.isEmpty()){
                                        log.error(ls_funid + "-未检索到数据：【"+adminfo.getRelationcolumn().trim()+"】，值："+map_main);
                                        error = ls_funid + "4701-未检索到数据：【"+adminfo.getRelationcolumn().trim()+"】，值："+map_main;
                                        continue;
                                    }
                                    hm_input.put("adminfo", BeanMapUtils.ClobToString(list_baseInfo.get(0)));
                                    setlinfo_sql = coursrinfo.getSqlcontent();
                                    setlinfo_key = coursrinfo.getRelationcolumn();
                                    sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                                    ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                                    hm_input.put("coursrinfo", BeanMapUtils.ClobToString1(iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid())));
                                    setlinfo_sql = dieinfo.getSqlcontent();
                                    setlinfo_key = dieinfo.getRelationcolumn();
                                    sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                                    ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                                    hm_input.put("dieinfo", BeanMapUtils.ClobToString1(iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid())));
                                    setlinfo_sql = diseinfo.getSqlcontent();
                                    setlinfo_key = diseinfo.getRelationcolumn();
                                    sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                                    ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                                    hm_input.put("diseinfo", BeanMapUtils.ClobToString1(iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid())));
                                    setlinfo_sql = oprninfo.getSqlcontent();
                                    setlinfo_key = oprninfo.getRelationcolumn();
                                    sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                                    ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                                    hm_input.put("oprninfo", BeanMapUtils.ClobToString1(iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid())));
                                    setlinfo_sql = rescinfo.getSqlcontent();
                                    setlinfo_key = rescinfo.getRelationcolumn();
                                    sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                                    ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                                    hm_input.put("rescinfo", BeanMapUtils.ClobToString1(iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid())));
                                    setlinfo_sql = dscginfo.getSqlcontent();
                                    setlinfo_key = dscginfo.getRelationcolumn();
                                    sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                                    ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                                    hm_input.put("dscginfo", BeanMapUtils.ClobToString1(iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid())));
                                    String hOSIDKEY  =HOSIDKEY;
                                    String   hOSNAME = HOSNAME;
                                    String oPTER = OPTER;
                                    String  hOSSECRET=HOSSECRET;
                                    String insdadvms = localadvms; //参保地
                                    String medadvms = localadvms;//就医地
                                    String jgid = "1";//机构id
                                    if(startAreaUpload==true){
                                        hOSIDKEY =map_main.get("HOSIDKEY")+"";
                                        hOSNAME =map_main.get("HOSNAME")+"";
                                        oPTER =map_main.get("OPTER")+"";
                                        hOSSECRET = map_main.get("HOSSECRET")+"";
                                        insdadvms = map_main.get("INSDADVMS")+"";
                                        medadvms = map_main.get("MEDADVMS")+"";
                                        jgid= map_main.get("JGID")+"";
                                    }
                                    String sign_no=initSign(jgid,oPTER);
                                    String ls_reqJson = getRequesJson(ls_funid, hm_input,medadvms,insdadvms,hOSIDKEY,hOSNAME,oPTER,sign_no);
                                    String ls_resJson = GjybUtils.sentServiceJson(URL, ls_reqJson, hOSIDKEY, hOSSECRET);
                                    JSONObject jo_res = GjybUtils.readResJson(ls_resJson);

                                    GJYB_DZBL_PUSHLOG pushlog = new GJYB_DZBL_PUSHLOG();
                                    pushlog.setUUID(IdUtil.simpleUUID());
                                    pushlog.setPSN_NO((null == list_baseInfo.get(0).get("psn_no")) ? "-" : list_baseInfo.get(0).get("psn_no")+"");
                                    pushlog.setPUSHDATE(new Date());
                                    //
                                    pushlog.setBUSID(map_main.get(ls_main_key)+"");
                                    if ("0".equals(jo_res.getString(RES_CODE_KEY))) {
                                        pushlog.setRESID("上传成功");
                                        pushlog.setSCSHBZ("2");
                                        result = "1";
                                    } else {
                                        log.error(jo_res.toJSONString());
                                        String ls_error_msg = ERROR_RES + jo_res.getString(RES_ERR_MSG);
                                        ls_error_msg = (ls_error_msg.length() > 1700) ? ls_error_msg.substring(0, 1700) + "-logfile" : ls_error_msg;
                                        pushlog.setRESID(ls_error_msg);
                                        pushlog.setSCSHBZ("0");
                                        error = "4701医保上传异常：" + jo_res.toJSONString();
                                    }
                                    pushlog.setFUNID(ls_funid);
                                    if(iSysDynamicSourceService.getDynamicSqlMapper(gjybdzblpushlogMapper,DBID,pushlog.getBUSID())!=null){
                                        iSysDynamicSourceService.dynamicSqlMapper(gjybdzblpushlogMapper,DBID,pushlog);
                                    }else{
                                        iSysDynamicSourceService.dynamicSqlMapperSave(gjybdzblpushlogMapper,DBID,pushlog);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.error("[检索语句{}，条件{}]", ls_query_person, autoflag);
                        log.error(ls_funid + "-异常信息：" + e.getMessage(), e);
                        error = ls_funid + "4701-异常信息：" + e.getMessage();
                    }
                }
            }
        } catch (Exception e) {
            log.error(ls_funid + "-异常信息：" + e.getMessage(), e);
            error = ls_funid + "4701-异常信息：" + e.getMessage();
        }finally {
            result_map.put("result",result);
            result_map.put("error",error);
            return result_map;
        }
    }

    @Override
    public Map<String, Object> pushZybasy4401(DrgsRequestBody body,boolean autoflag) {
        String ls_funid = "4401";
        Map<String,Object> result_map = new HashMap<>();
        String result = "0";
        String error = "";
        try {
            String URL = getRequestUrl(basyUrl);
            DrgsSqlconfig mainConfig = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, DrgsStatus.MAZH_MAIN.getCode());
            DrgsSqlconfig baseinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, "baseinfo");
            DrgsSqlconfig diseinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, "diseinfo");
            DrgsSqlconfig icuinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, "icuinfo");
            DrgsSqlconfig oprninfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, "oprninfo");
            String ls_mainSql = mainConfig.getSqlcontent();
            if (null == ls_mainSql || ls_mainSql.isEmpty()) {
                log.info(ls_funid + "主语句未配置，任务不执行。");
                error = "4401主语句未配置，任务不执行。";
            } else {
                //先检索出需要上传的人员信息
                String ls_query_person = "";
                String ls_main_key = mainConfig.getRelationcolumn().trim();
                Map<String, Object> params = body.getCondition();
                if (autoflag) {
                    if(params.get("zyh") != null){
                        String sql = StringUtils.numberOrString(params.get("zyh"))==false?"'"+params.get("zyh")+"'":params.get("zyh")+"";
                        ls_query_person = "select * " + " from (" + ls_mainSql + ") a where a.\"" + ls_main_key + "\"= "+sql;
                    }
                    params.put("sql", ls_query_person);
                } else {
                    //日期向后推一天
                    if(body.getEnddate()!=null&&body.getBegindate()!=null){
                        params.put("enddate", DateUtils.dateAdd(body.getEnddate(), 1));
                        params.put("begindate", body.getBegindate());
                    }
                    DrgsSqlconfig drgsSqlconfig = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId("BASY", DrgsStatus.MAZH_MAIN.getCode());
                    ls_query_person = "select * from (" + drgsSqlconfig.getSqlcontent() + ")";
                    String organizationId = SecurityUtils.getLoginUser().getUser().getDept().getOrganizationId();
                    params.put("sql", ls_query_person);
                    if(!"0".equals(organizationId)&&null!=organizationId){
                        params.put("sql", "select * from ("+drgsSqlconfig.getSqlcontent()+" ) b where b.jgid = '"+organizationId+"'");
                    }
                }
                try {
                    List<Map<String, Object>> list_main = iSysDynamicSourceService.dynamicSqlList4401(params, mainConfig.getDbid());
                    if (null == list_main || list_main.isEmpty()) {
                        log.info(ls_funid + "本次无数据，执行语句---{}，查询条件--{}",ls_query_person,params);
                        error  = "4401主语句未查询到数据。";
                    }else{
                        for (Map<String, Object> mainData : list_main) {
                            HashMap<String, Object> hm_input = new LinkedHashMap<>();
                            Map<String, Object> map_main = mainData;
                            //setlinfo   sql   TODO 判断是否为空
                            String setlinfo_sql = baseinfo.getSqlcontent();
                            String setlinfo_key = baseinfo.getRelationcolumn();
                            String sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                            String ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                            List<Map<String, Object>> list_baseInfo = iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid());
                            if(list_baseInfo == null || list_baseInfo.isEmpty()){
                                log.error(ls_funid + "-未检索到数据：【"+baseinfo.getRelationcolumn().trim()+"】，值："+map_main);
                                error = ls_funid + "4401-未检索到数据：【"+baseinfo.getRelationcolumn().trim()+"】，值："+map_main;
                                continue;
                            }
                            hm_input.put("baseinfo", list_baseInfo.get(0));
                            //住院诊断信息
                            setlinfo_sql = diseinfo.getSqlcontent();
                            setlinfo_key = diseinfo.getRelationcolumn();
                            sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                            ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                            hm_input.put("diseinfo", iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid()));
                            //手术记录
                            setlinfo_sql = oprninfo.getSqlcontent();
                            setlinfo_key = oprninfo.getRelationcolumn();
                            sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                            ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                            hm_input.put("oprninfo", iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid()));
                            //icu
                            setlinfo_sql = icuinfo.getSqlcontent();
                            setlinfo_key = icuinfo.getRelationcolumn();
                            sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                            ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                            hm_input.put("icuinfo", iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid()));
                            String hOSIDKEY  =HOSIDKEY;
                            String   hOSNAME = HOSNAME;
                            String oPTER = OPTER;
                            String  hOSSECRET=HOSSECRET;
                            String insdadvms = localadvms; //参保地
                            String medadvms = localadvms;//就医地
                            String jgid = "1";//机构id
                            if(startAreaUpload==true){
                                hOSIDKEY =map_main.get("HOSIDKEY")+"";
                                hOSNAME =map_main.get("HOSNAME")+"";
                                oPTER =map_main.get("OPTER")+"";
                                hOSSECRET = map_main.get("HOSSECRET")+"";
                                insdadvms = map_main.get("INSDADVMS")+"";
                                medadvms = map_main.get("MEDADVMS")+"";
                                jgid= map_main.get("JGID")+"";
                            }
                            String sign_no= initSign(jgid,oPTER);
                            String ls_reqJson = getRequesJson(ls_funid, hm_input,medadvms,insdadvms,hOSIDKEY,hOSNAME,oPTER,sign_no);
                            String ls_resJson = GjybUtils.sentServiceJson(URL, ls_reqJson, hOSIDKEY, hOSSECRET);
                            JSONObject jo_res = GjybUtils.readResJson(ls_resJson);

                            GJYB_ZYBA_PUSHLOG pushlog = new GJYB_ZYBA_PUSHLOG();
                            pushlog.setUUID(IdUtil.simpleUUID());
                            pushlog.setPSN_NO((null == list_baseInfo.get(0).get("PSN_NO")) ? "-" : list_baseInfo.get(0).get("PSN_NO")+"");
                            pushlog.setPUSHDATE(new Date());
                            //主键从main中获取
                            pushlog.setBUSID(map_main.get(ls_main_key)+"");
                            if ("0".equals(jo_res.getString(RES_CODE_KEY))) {
                                pushlog.setRESID("上传成功");
                                pushlog.setSCSHBZ("2");
                                result = "1";
                            } else {
                                log.error(jo_res.toJSONString());
                                String ls_error_msg = ERROR_RES + jo_res.getString(RES_ERR_MSG);
                                ls_error_msg = (ls_error_msg.length() > 1700) ? ls_error_msg.substring(0, 1700) + "-logfile" : ls_error_msg;
                                pushlog.setRESID(ls_error_msg);
                                pushlog.setSCSHBZ("0");
                                error = "4401医保上传异常：" + jo_res.toJSONString();
                            }
                            pushlog.setFUNID(ls_funid);
                            if(iSysDynamicSourceService.getDynamicSqlMapper(gjybzybapushlogMapper,DBID,pushlog.getBUSID())!=null){
                                iSysDynamicSourceService.dynamicSqlMapper(gjybzybapushlogMapper,DBID,pushlog);
                            }else{
                                iSysDynamicSourceService.dynamicSqlMapperSave(gjybzybapushlogMapper,DBID,pushlog);
                            }
                        }
                    }
                } catch (Exception e) {
                    result = "0";
                    log.error("[检索语句{}，条件{}]", ls_query_person, autoflag);
                    log.error(ls_funid + "-异常信息：" + e.getMessage(), e);
                    error = ls_funid + "4401-异常信息：" + e.getMessage();
                }
            }
        } catch (Exception e) {
            result = "0";
            log.error(ls_funid + "-异常信息：" + e.getMessage(), e);
            error = ls_funid + "4401-异常信息：" + e.getMessage();
        }finally {
            result_map.put("result",result);
            result_map.put("error",error);
            return result_map;
        }
    }

    @Override
    public Map<String, Object> pushZybasy4402(DrgsRequestBody body, boolean autoflag) {
        String ls_funid = "4402";
        Map<String,Object> result_map = new HashMap<>();
        String result = "0";
        String error = "";
        try {
            String URL = getRequestUrl(zyyzUrl);
            DrgsSqlconfig mainConfig = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, DrgsStatus.MAZH_MAIN.getCode());
//            DrgsSqlconfig baseinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, "baseinfo");
            String ls_mainSql = mainConfig.getSqlcontent();
            if (null == ls_mainSql || ls_mainSql.isEmpty()) {
                log.info(ls_funid + "主语句未配置，任务不执行。");
                error = ls_funid + "4402主语句未配置，任务不执行。";
            } else {
                //先检索出需要上传的人员信息
                String ls_query_person = "";
                String ls_main_key = mainConfig.getRelationcolumn().trim();
                Map<String, Object> params = body.getCondition();
                if (autoflag) {
                    ls_query_person = ls_mainSql;//"select a.\"" + ls_main_key + "\" from (" + ls_mainSql + ") a LEFT JOIN gjyb_zyyz_pushlog b on a.\"" + ls_main_key + "\" = b.busid where b.busid is null";
                    if(params.get("zyh") != null){
                        String sql = StringUtils.numberOrString(params.get("zyh"))==false?"'"+params.get("zyh")+"'":params.get("zyh")+"";
                        ls_query_person = "select * " + " from (" + ls_mainSql + ") a where a.\"" + ls_main_key + "\"= "+sql;
                    }
                } else {
                    ls_query_person = "select a.\"" + ls_main_key + "\" from (" + ls_mainSql + ") a where a.\"drord_isu_time\">= :begindate and a.\"drord_isu_time\" <= :enddate";
                }
                try {
                    List<Map<String, Object>> list_main = iSysDynamicSourceService.dynamicReportList(ls_query_person, mainConfig.getDbid());
                    if (null == list_main || list_main.isEmpty()) {
//                        if(!autoflag){
                        log.info(ls_funid + "本次无数据，执行语句---{}，查询条件--{}",ls_query_person,params);
                        error  = "4402主语句未查询到数据。";
//                        }
//                        log.info(ls_funid + "本次无数据，执行类型--{}", (autoflag) ? "计划任务" : "手工执行");
                    }else{
//                        for (Map<String, Object> mainData : list_main) {
                        if(list_main.size()>0){
                            HashMap<String, Object> hm_input = new LinkedHashMap<>();
                            Map<String, Object> map_main = list_main.get(0);
                            //setlinfo   sql   TODO 判断是否为空
//                            String setlinfo_sql = baseinfo.getSqlcontent();
//                            String setlinfo_key = baseinfo.getRelationcolumn();
//                            String sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
//                            String ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
//                            List<Map<String, Object>> list_baseInfo = iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid());
//                            if(list_baseInfo == null ||list_baseInfo.isEmpty()){
//                                log.error(ls_funid + "-未检索到数据：【"+baseinfo.getRelationcolumn().trim()+"】，值："+map_main);
//                                error = ls_funid + "4402-未检索到数据：【"+baseinfo.getRelationcolumn().trim()+"】，值："+map_main;
//                                continue;
//                            }
                            hm_input.put("data", list_main);
                            String hOSIDKEY  =HOSIDKEY;
                            String   hOSNAME = HOSNAME;
                            String oPTER = OPTER;
                            String  hOSSECRET=HOSSECRET;
                            String insdadvms = localadvms; //参保地
                            String medadvms = localadvms;//就医地
                            String jgid = "1";
                            if(startAreaUpload==true){
                                hOSIDKEY =map_main.get("HOSIDKEY")+"";
                                hOSNAME =map_main.get("HOSNAME")+"";
                                oPTER =map_main.get("OPTER")+"";
                                hOSSECRET = map_main.get("HOSSECRET")+"";
                                insdadvms = map_main.get("INSDADVMS")+"";
                                medadvms = map_main.get("MEDADVMS")+"";
                                jgid= map_main.get("JGID")+"";
                            }
                            String sign_no=initSign(jgid,oPTER);
                            String ls_reqJson = getRequesJson(ls_funid, hm_input,medadvms,insdadvms,hOSIDKEY,hOSNAME,oPTER,sign_no);
                            String ls_resJson = GjybUtils.sentServiceJson(URL, ls_reqJson, hOSIDKEY, hOSSECRET);
                            JSONObject jo_res = GjybUtils.readResJson(ls_resJson);

                            GJYB_ZYYZ_PUSHLOG pushlog = new GJYB_ZYYZ_PUSHLOG();
                            pushlog.setUUID(IdUtil.simpleUUID());
                            //大小写问题：存在医院未按照“"”的方式添加
                            pushlog.setPSN_NO((null == list_main.get(0).get("PSN_NO")) ? "-" : list_main.get(0).get("PSN_NO")+"");
                            pushlog.setPUSHDATE(new Date());
                            //
                            pushlog.setBUSID(map_main.get(ls_main_key)+"");
                            if ("0".equals(jo_res.getString(RES_CODE_KEY))) {
                                pushlog.setRESID("上传成功");
                                pushlog.setSCSHBZ("1");
                                result = "1";
                            } else {
                                log.error(jo_res.toJSONString());
                                String ls_error_msg = ERROR_RES + jo_res.getString(RES_ERR_MSG);
                                ls_error_msg = (ls_error_msg.length() > 1700) ? ls_error_msg.substring(0, 1700) + "-logfile" : ls_error_msg;
                                pushlog.setRESID(ls_error_msg);
                                pushlog.setSCSHBZ("0");
                                error = "4402医保上传异常："+jo_res.toJSONString();
                            }
                            pushlog.setFUNID(ls_funid);
                            if(iSysDynamicSourceService.getDynamicSqlMapper(gjybzyyzpushlogMapper,DBID,pushlog.getBUSID())!=null){
                                iSysDynamicSourceService.dynamicSqlMapper(gjybzyyzpushlogMapper,DBID,pushlog);
                            }else{
                                iSysDynamicSourceService.dynamicSqlMapperSave(gjybzyyzpushlogMapper,DBID,pushlog);
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("[检索语句{}，条件{}]", ls_query_person, autoflag);
                    log.error(ls_funid + "-异常信息：" + e.getMessage(), e);
                    error = ls_funid + "4402-异常信息：" + e.getMessage();
                }
            }
        } catch (Exception e) {
            log.error(ls_funid + "-异常信息：" + e.getMessage(), e);
            error = ls_funid + "4402-异常信息：" + e.getMessage();
        }finally {
            result_map.put("result",result);
            result_map.put("error",error);
            return result_map;
        }
    }

    @Override
    public Map<String, Object> pushBatchZybasy4402(DrgsRequestBody body, boolean autoflag) {
        Map<String, Object> params = body.getCondition();
        DrgsSqlconfig drgsSqlconfig = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(params.get("type")+"", DrgsStatus.MAZH_MAIN.getCode());
        //TODO  需要判断空值
        /**
         * 判断当前用户的机构id
         * 如果是0  则不加条件
         */
        String organizationId = SecurityUtils.getLoginUser().getUser().getDept().getOrganizationId();
        params.put("sql", drgsSqlconfig.getSqlcontent());
        if(!"0".equals(organizationId)&&null!=organizationId){
            params.put("sql", "select * from ("+drgsSqlconfig.getSqlcontent()+" ) b where b.jgid = '"+organizationId+"'");
        }
        //日期向后推一天
        params.put("enddate", DateUtils.dateAdd(body.getEnddate(), 1));
        params.put("begindate", body.getBegindate());
        //查询结果
        List<Map<String, Object>> list = iSysDynamicSourceService.dynamicSqlList(params, drgsSqlconfig.getDbid());
        String ls_funid = "4402";
        Map<String,Object> result_map = new HashMap<>();
        String result = "0";
        String error = "";
        try {
            String URL = getRequestUrl(zyyzUrl);
            DrgsSqlconfig mainConfig = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, DrgsStatus.MAZH_MAIN.getCode());
            String ls_mainSql = mainConfig.getSqlcontent();
            if (null == ls_mainSql || ls_mainSql.isEmpty()) {
                log.info(ls_funid + "主语句未配置，任务不执行。");
                error = ls_funid + "4402主语句未配置，任务不执行。";
            } else {
                //先检索出需要上传的人员信息
                String ls_query_person = "";
                String ls_main_key = mainConfig.getRelationcolumn().trim();
                for(int i=0;i<list.size();i++){
                        ls_query_person = ls_mainSql;
                        if(list.get(i).get("zyh") != null){
                            String sql = StringUtils.numberOrString(list.get(i).get("zyh"))==false?"'"+list.get(i).get("zyh")+"'":list.get(i).get("zyh")+"";
                            ls_query_person = "select * " + " from (" + ls_mainSql + ") a where a.\"" + ls_main_key + "\"= "+sql;
                        }
                    try {
                        List<Map<String, Object>> list_main = iSysDynamicSourceService.dynamicReportList(ls_query_person, mainConfig.getDbid());
                        if (null == list_main || list_main.isEmpty()) {
                            log.info(ls_funid + "本次无数据，执行语句---{}，查询条件--{}",ls_query_person,list.get(i));
                            error  = "4402主语句未查询到数据。";
                        }else{
                            if(list_main.size()>0){
                                HashMap<String, Object> hm_input = new LinkedHashMap<>();
                                Map<String, Object> map_main = list_main.get(0);
                                hm_input.put("data", list_main);
                                String hOSIDKEY  =HOSIDKEY;
                                String   hOSNAME = HOSNAME;
                                String oPTER = OPTER;
                                String  hOSSECRET=HOSSECRET;
                                String insdadvms = localadvms; //参保地
                                String medadvms = localadvms;//就医地
                                String jgid = "1";
                                if(startAreaUpload==true){
                                    hOSIDKEY =map_main.get("HOSIDKEY")+"";
                                    hOSNAME =map_main.get("HOSNAME")+"";
                                    oPTER =map_main.get("OPTER")+"";
                                    hOSSECRET = map_main.get("HOSSECRET")+"";
                                    insdadvms = map_main.get("INSDADVMS")+"";
                                    medadvms = map_main.get("MEDADVMS")+"";
                                    jgid= map_main.get("JGID")+"";
                                }
                                String sign_no=initSign(jgid,oPTER);
                                String ls_reqJson = getRequesJson(ls_funid, hm_input,medadvms,insdadvms,hOSIDKEY,hOSNAME,oPTER,sign_no);
                                String ls_resJson = GjybUtils.sentServiceJson(URL, ls_reqJson, hOSIDKEY, hOSSECRET);
                                JSONObject jo_res = GjybUtils.readResJson(ls_resJson);

                                GJYB_ZYYZ_PUSHLOG pushlog = new GJYB_ZYYZ_PUSHLOG();
                                pushlog.setUUID(IdUtil.simpleUUID());
                                //大小写问题：存在医院未按照“"”的方式添加
                                pushlog.setPSN_NO((null == list_main.get(0).get("PSN_NO")) ? "-" : list_main.get(0).get("PSN_NO")+"");
                                pushlog.setPUSHDATE(new Date());
                                //
                                pushlog.setBUSID(map_main.get(ls_main_key)+"");
                                if ("0".equals(jo_res.getString(RES_CODE_KEY))) {
                                    pushlog.setRESID("上传成功");
                                    pushlog.setSCSHBZ("1");
                                    result = "1";
                                } else {
                                    log.error(jo_res.toJSONString());
                                    String ls_error_msg = ERROR_RES + jo_res.getString(RES_ERR_MSG);
                                    ls_error_msg = (ls_error_msg.length() > 1700) ? ls_error_msg.substring(0, 1700) + "-logfile" : ls_error_msg;
                                    pushlog.setRESID(ls_error_msg);
                                    pushlog.setSCSHBZ("0");
                                    error = "4402医保上传异常："+jo_res.toJSONString();
                                }
                                pushlog.setFUNID(ls_funid);
                                if(iSysDynamicSourceService.getDynamicSqlMapper(gjybzyyzpushlogMapper,DBID,pushlog.getBUSID())!=null){
                                    iSysDynamicSourceService.dynamicSqlMapper(gjybzyyzpushlogMapper,DBID,pushlog);
                                }else{
                                    iSysDynamicSourceService.dynamicSqlMapperSave(gjybzyyzpushlogMapper,DBID,pushlog);
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.error("[检索语句{}，条件{}]", ls_query_person, autoflag);
                        log.error(ls_funid + "-异常信息：" + e.getMessage(), e);
                        error = ls_funid + "4402-异常信息：" + e.getMessage();
                    }
                }
            }
        } catch (Exception e) {
            log.error(ls_funid + "-异常信息：" + e.getMessage(), e);
            error = ls_funid + "4402-异常信息：" + e.getMessage();
        }finally {
            result_map.put("result",result);
            result_map.put("error",error);
            return result_map;
        }
    }

    /**
     * 4101A批量
     * @param body
     * @return
     */
    @Override
    public Map<String, Object> pushBatchJjqd(DrgsRequestBody body) {
        Map<String, Object> result_map = new HashMap<>();
        String result = "0";
        String error = "";
        String ls_funid = "4101A";
        DrgsSqlconfig mainConfig = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, DrgsStatus.MAZH_MAIN.getCode());
        Map<String, Object> params = body.getCondition();
        String URL = getRequestUrl(ylbzqdUrl);
        DrgsSqlconfig setlinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, "setlinfo");
        DrgsSqlconfig opspdiseinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, "opspdiseinfo");
        DrgsSqlconfig diseinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, "diseinfo");
        DrgsSqlconfig bldinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, "bldinfo");
        DrgsSqlconfig icuinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, "icuinfo");
        DrgsSqlconfig oprninfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, "oprninfo");
        String ls_mainSql = mainConfig.getSqlcontent();
        if (null == ls_mainSql || ls_mainSql.isEmpty()) {
            log.error(ls_funid + "主语句未配置，任务不执行。");
            error = "4101A主语句未配置，任务不执行。";
        } else {
            //获取参数
            //TODO  需要判断空值
            params.put("sql", ls_mainSql);
            //日期向后推一天
            params.put("enddate", DateUtils.dateAdd(body.getEnddate(), 1));
            params.put("begindate", body.getBegindate());
            String ls_main_key = mainConfig.getRelationcolumn().trim();
            long startTime = System.currentTimeMillis();
            List<Map<String, Object>> list_main = iSysDynamicSourceService.dynamicSqlList2(params, mainConfig.getDbid());
            //TODO 需要优化
            DrgsSqlconfig drgsSqlconfig = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId("YBJJQD", DrgsStatus.MAZH_MAIN.getCode());
            Map<String, Object> params1 = body.getCondition();
            //TODO  需要判断空值
//            params1.put("sql", drgsSqlconfig.getSqlcontent());
            /**
             * 判断当前用户的机构id
             * 如果是0  则不加条件
             */
            String organizationId = SecurityUtils.getLoginUser().getUser().getDept().getOrganizationId();
            params1.put("sql", drgsSqlconfig.getSqlcontent());
            if(!"0".equals(organizationId)&&null!=organizationId){
                params1.put("sql", "select * from ("+drgsSqlconfig.getSqlcontent()+" ) b where b.jgid = '"+organizationId+"'");
            }
            //日期向后推一天
            params1.put("enddate", DateUtils.dateAdd(body.getEnddate(), 1));
            params1.put("begindate", body.getBegindate());
            //查询结果
            List<Map<String, Object>> list_main_temp = new ArrayList<>();
            List<Map<String, Object>> list = iSysDynamicSourceService.dynamicSqlList(params1, drgsSqlconfig.getDbid());
            //有需要上传的数据才核对
            if(list.size()>0){
                for(Map<String,Object>map :list_main){
                    //GJYB_JJQD_PUSHLOG bean = iSysDynamicSourceService.getDynamicSqlMapper(gjybpushlogMapper,DBID,map.get("zyh")+"");
                    //params1.put("zyh",map.get("zyh")+"");
                    for(Map<String,Object>map_inner :list) {
                        //log.info("4101A_main{},查询视图{}.",map_inner.get("ZYH")+"",map.get("zyh"));
                        //字符串和数值不匹配优化
                        if ((map_inner.get("ZYH")+"").equals(map.get("zyh")+"")&&!"2".equals(map_inner.get("SCSHBZ") + "")) {
                            list_main_temp.add(map);
                        }
                    }
                }
            }
            list_main = list_main_temp;
            System.out.println("耗时："+(System.currentTimeMillis()-startTime));
            if (null == list_main || list_main.isEmpty()) {
//                        if(!autoflag){
                log.info(ls_funid + "本次无数据，执行语句---{}，查询条件--{}", "", params);
                error = "4101A主语句未查询到数据。";
//                        }
            } else {
//                List<GJYB_JJQD_PUSHLOG> list = new ArrayList<>();
                for (Map<String, Object> mainData : list_main) {
                    HashMap<String, Object> hm_input = new LinkedHashMap<>();
                    Map<String, Object> map_main = mainData;
                    //setlinfo   sql   TODO 判断是否为空
                    String setlinfo_sql = setlinfo.getSqlcontent();
                    String setlinfo_key = setlinfo.getRelationcolumn();
                    String sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                    String ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                    List<Map<String, Object>> list_baseInfo = iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid());
                    if (list_baseInfo == null || list_baseInfo.isEmpty()) {
                        log.error(ls_funid + "-未检索到数据：【" + setlinfo.getRelationcolumn().trim() + "】，值：" + map_main);
                        error = ls_funid + "4101A-未检索到数据：【" + setlinfo.getRelationcolumn().trim() + "】，值：" + map_main;
                        continue;
                    }
                    hm_input.put("setlinfo", list_baseInfo.get(0));
                    setlinfo_sql = opspdiseinfo.getSqlcontent();
                    setlinfo_key = opspdiseinfo.getRelationcolumn();
                    sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                    ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                    hm_input.put("opspdiseinfo", iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid()));
                    //住院诊断信息
                    setlinfo_sql = diseinfo.getSqlcontent();
                    setlinfo_key = diseinfo.getRelationcolumn();
                    sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                    ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                    hm_input.put("diseinfo", iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid()));
                    //
                    setlinfo_sql = oprninfo.getSqlcontent();
                    setlinfo_key = oprninfo.getRelationcolumn();
                    sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                    ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                    hm_input.put("oprninfo", iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid()));
                    setlinfo_sql = icuinfo.getSqlcontent();
                    setlinfo_key = icuinfo.getRelationcolumn();
                    sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                    ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                    hm_input.put("icuinfo", iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid()));
                    setlinfo_sql = bldinfo.getSqlcontent();
                    setlinfo_key = bldinfo.getRelationcolumn();
                    sql = StringUtils.numberOrString(map_main.get(setlinfo_key))==false?"'"+map_main.get(setlinfo_key)+"'":map_main.get(setlinfo_key)+"";
                    ls_sql = "select * " + " from (" + setlinfo_sql + ") a where a.\"" + setlinfo_key + "\"= " + sql;
                    hm_input.put("bldinfo", iSysDynamicSourceService.dynamicReportList(ls_sql, mainConfig.getDbid()));
                    String hOSIDKEY = HOSIDKEY;
                    String hOSNAME = HOSNAME;
                    String oPTER = OPTER;
                    String hOSSECRET = HOSSECRET;
                    String insdadvms = localadvms; //参保地
                    String medadvms = localadvms;//就医地
                    String jgid = "1";//机构id
                    if (startAreaUpload == true) {
                        hOSIDKEY = map_main.get("HOSIDKEY") + "";
                        hOSNAME = map_main.get("HOSNAME") + "";
                        oPTER = map_main.get("OPTER") + "";
                        hOSSECRET = map_main.get("HOSSECRET") + "";
                        insdadvms = map_main.get("INSDADVMS") + "";
                        medadvms = map_main.get("MEDADVMS") + "";
                        jgid = map_main.get("JGID") + "";
                    }
                    String sign_no = initSign(jgid, oPTER);
                    String ls_reqJson = getRequesJson(ls_funid, hm_input, medadvms, insdadvms, hOSIDKEY, hOSNAME, oPTER, sign_no);
                    String ls_resJson = GjybUtils.sentServiceJson(URL, ls_reqJson, hOSIDKEY, hOSSECRET);
                    JSONObject jo_res = GjybUtils.readResJson(ls_resJson);
                    //日志保存
                    GJYB_JJQD_PUSHLOG pushlog = new GJYB_JJQD_PUSHLOG();
                    pushlog.setUUID(IdUtil.simpleUUID());
                    pushlog.setPSN_NO((null == list_baseInfo.get(0).get("PSN_NO")) ? "-" : list_baseInfo.get(0).get("PSN_NO") + "");
                    //推送时间更新
                    pushlog.setPUSHDATE(new Date());
                    //
                    log.error(ls_funid + "-BUSID更新字段：【" + map_main.get(ls_main_key) + "】，值：" + ls_main_key);
                    pushlog.setBUSID(map_main.get(ls_main_key) + "");
                    pushlog.setFUNID(ls_funid);
                    if ("0".equals(jo_res.getString(RES_CODE_KEY))) {
                        JSONObject jo_output = jo_res.getJSONObject(RES_OUTPUT_KEY);
//                            实际上没有data节点
//                            JSONObject jo_id = jo_output.getJSONObject("data");
                        pushlog.setRESID(jo_output.getString("setl_list_id"));
                        result_map.put("setl_list_id", jo_output.getString("setl_list_id"));
                        pushlog.setSCSHBZ("1");
                        result = "1";
                    } else {
                        log.error(jo_res.toJSONString());
                        String ls_error_msg = ERROR_RES + jo_res.getString(RES_ERR_MSG);
                        ls_error_msg = (ls_error_msg.length() > 1700) ? ls_error_msg.substring(0, 1700) + "-logfile" : ls_error_msg;
                        if(ls_error_msg.indexOf("状态为:1")>-1){
                            //这个状态表示已经上传过
                            pushlog.setRESID("已上传");
                            pushlog.setSCSHBZ("1");
                        }else{
                            pushlog.setRESID(ls_error_msg);
                            pushlog.setSCSHBZ("0");
                        }
                        error = "4101A医保上传异常：" + jo_res.toJSONString();
                    }
                    if(iSysDynamicSourceService.getDynamicSqlMapper(gjybpushlogMapper,DBID,pushlog.getBUSID())!=null){
                        iSysDynamicSourceService.dynamicSqlMapper(gjybpushlogMapper,DBID,pushlog);
                    }else{
                        iSysDynamicSourceService.dynamicSqlMapperSave(gjybpushlogMapper,DBID,pushlog);
                    }
//                    list.add(pushlog);
//                    if(list.size()==100){
//                        iSysDynamicSourceService.dynamicSqlMapperUpdateList(gjybpushlogMapper,DBID,list);
//                        list = new ArrayList<>();
//                    }
                }
//                iSysDynamicSourceService.dynamicSqlMapperUpdateList(gjybpushlogMapper,DBID,list);
            }
        }
        return null;
    }

    /**
     * @param body 入参TSID
     * @return
     */
    @Override
    public Map<String, Object> pushConfirmOneMedinfo(DrgsRequestBody body) {
        Map<String, Object> map_res = new HashMap<>();
        Map<String, Object> params_request = body.getCondition();
        //从gjyb_jjqd_pushlog中，根据uuid获取唯一的4101A上传返回数据，然后上传
        String jgid = "1";
        //YBSHYJ is null
        String hql_jjqd = "select  UUID as UUID, PSN_NO as PSN_NO, BUSID as BUSID, PUSHDATE as PUSHDATE, RESID as RESID, LOGTIME as LOGTIME, FUNID as FUNID, SCSHBZ as SCSHBZ, YBSHYJ as YBSHYJ, QRCSSJ as QRCSSJ from GJYB_JJQD_PUSHLOG where resid is not null and scshbz = '1' and uuid = '" + params_request.get("TSID") + "'";
        //TODO
        List<Map<String, Object>> list_jjqd = iSysDynamicSourceService.dynamicReportList(hql_jjqd, DBID);
        if (null != list_jjqd && list_jjqd.size() == 1) {
            String ls_funid = "4101A";
            DrgsSqlconfig mainConfig = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, DrgsStatus.MAZH_MAIN.getCode());
            DrgsSqlconfig setlinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, "setlinfo");
            if (null == mainConfig || null == setlinfo) {
                map_res.put("code", 1);
                map_res.put("msg", "无配置");
                return null;
            }
            try {
                //查询
                GJYB_JJQD_PUSHLOG pushlog = (GJYB_JJQD_PUSHLOG) BeanMapUtils.mapToObject(list_jjqd.get(0), GJYB_JJQD_PUSHLOG.class);
                Map<String, Object> params = new HashMap<>();
                Map<String, Object> par_main = new HashMap<>();
                par_main.put("ZYH", pushlog.getBUSID());
                String sql = StringUtils.numberOrString(pushlog.getBUSID())==false?"'"+pushlog.getBUSID()+"'":pushlog.getBUSID();
                String ls_query_main = "select * from (" + mainConfig.getSqlcontent() + ") a where a.\"" + mainConfig.getRelationcolumn() + "\"= " + sql;

                List<Map<String, Object>> list_mapmain = iSysDynamicSourceService.dynamicReportList(ls_query_main, mainConfig.getDbid());
                params.put("ZYH", pushlog.getBUSID());
                params.put("PSNNO", pushlog.getPSN_NO());
                String ls_query_setlid = "select setl_id as SETL_ID from (" + setlinfo.getSqlcontent() + ") a where a.psn_no ='" + pushlog.getPSN_NO() + "' and a.\"" + setlinfo.getRelationcolumn() + "\"= " + sql;
                //在未考虑多数据源的情况下，可不用如此麻烦
                List<Map<String, Object>> list_setmain = iSysDynamicSourceService.dynamicReportList(ls_query_setlid, setlinfo.getDbid());
                if (null != list_mapmain && list_mapmain.size() == 1 && null != list_setmain && list_setmain.size() == 1) {
                    Map<String, Object> map_main = list_mapmain.get(0);
                    Map<String, Object> map_data = list_setmain.get(0);
                    map_data.put("psn_no", pushlog.getPSN_NO());
                    //多机构考虑
                    String hOSIDKEY = HOSIDKEY;
                    String hOSNAME = HOSNAME;
                    String oPTER = OPTER;
                    String hOSSECRET = HOSSECRET;
                    String insdadvms = localadvms; //参保地
                    String medadvms = localadvms;//就医地
                    if (startAreaUpload == true) {
                        hOSIDKEY = map_main.get("HOSIDKEY") + "";
                        hOSNAME = map_main.get("HOSNAME") + "";
                        oPTER = map_main.get("OPTER") + "";
                        hOSSECRET = map_main.get("HOSSECRET") + "";
                        insdadvms = map_main.get("INSDADVMS") + "";
                        medadvms = map_main.get("MEDADVMS") + "";
                        jgid = map_main.get("JGID") + "";
                    }
                    String sign_no = initSign(jgid, oPTER);
                    Map<String, Object> base = new HashMap<String, Object>();
                    ArrayList<Map<String, Object>> setl_infos = new ArrayList<Map<String, Object>>();
                    Map<String, Object> setl_info = new HashMap<String, Object>();
                    //医保无撤销功能，可不考虑
                    map_data.put("stas_type", "1");
                    setl_infos.add(map_data);
                    setl_info.put("stastinfo", setl_infos);
                    base.put("data", setl_info);

                    ls_funid = "4102";
                    String ls_reqJson = getRequesJson(ls_funid, base, medadvms, insdadvms, hOSIDKEY, hOSNAME, oPTER, sign_no);
                    String ls_resJson = GjybUtils.sentServiceJson(getRequestUrl(updYlbzqdUrl), ls_reqJson, hOSIDKEY, hOSSECRET);
                    JSONObject jo_res = GjybUtils.readResJson(ls_resJson);
                    String ls_infcode = jo_res.getString("infcode");
                    if ("0".equals(ls_infcode)) {
                        map_res.put("code", 0);
                        map_res.put("msg", "成功");
                        pushlog.setSCSHBZ("2");
                        pushlog.setRESID("已提交");
                    } else {
                        map_res.put("code", 99);
                        map_res.put("msg", jo_res.get("err_msg"));
                        String err_msg = jo_res.get("err_msg").toString();
                        pushlog.setRESID(err_msg);
                        if(err_msg.indexOf("已提交数据")>-1){
                            pushlog.setSCSHBZ("2");
                            pushlog.setRESID("已提交");
                        }
                        log.error("4102提交医保返回:"+jo_res.toJSONString());

                    }
                    if(iSysDynamicSourceService.getDynamicSqlMapper(gjybpushlogMapper,DBID,pushlog.getBUSID())!=null){
                        iSysDynamicSourceService.dynamicSqlMapper(gjybpushlogMapper,DBID,pushlog);
                    }else{
                        iSysDynamicSourceService.dynamicSqlMapperSave(gjybpushlogMapper,DBID,pushlog);
                    }
                }
//                }
            } catch (Exception e) {
                map_res.put("code", 2);
                map_res.put("msg", "执行异常");
                log.error("4102上传失败:" + e.getMessage(), e);
            }
        } else {
            map_res.put("code", 3);
            map_res.put("msg", "日志记录错误");
        }
        return map_res;
    }

    @Override
    public Map<String, Object> pushConfirmBatchMedinfo(DrgsRequestBody body) {
        DrgsSqlconfig drgsSqlconfig = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId("YBJJQD", DrgsStatus.MAZH_MAIN.getCode());
        Map<String, Object> params = body.getCondition();
        //TODO  需要判断空值
        /**
         * 判断当前用户的机构id
         * 如果是0  则不加条件
         */
        String organizationId = SecurityUtils.getLoginUser().getUser().getDept().getOrganizationId();
        params.put("sql", drgsSqlconfig.getSqlcontent());
        if(!"0".equals(organizationId)&&null!=organizationId){
            params.put("sql", "select * from ("+drgsSqlconfig.getSqlcontent()+" ) b where b.jgid = '"+organizationId+"'");
        }
        //日期向后推一天
        params.put("enddate", DateUtils.dateAdd(body.getEnddate(), 1));
        params.put("begindate", body.getBegindate());
        //查询结果
        try {
            //TODO   数据库类型
//            List<Map<String, Object>> list_jjqd = iSysDynamicSourceService.dynamicSqlList1(params, DBID);
            List<Map<String, Object>> list_jjqd = iSysDynamicSourceService.dynamicSqlList(params, drgsSqlconfig.getDbid());
            for(Map<String, Object> pushlog:list_jjqd){
                body.getCondition().put("TSID",pushlog.get("TSID"));
                pushConfirmOneMedinfo(body);
            }
        } catch (Exception e) {
            log.error("批量执行异常；"+e.getMessage(),e);
        }
        return null;
    }

    @Override
    public Map<String, Object> doQueryYbJsQdxx(DrgsRequestBody body) {
        Map<String, Object> map_res = new HashMap<>();
        Map<String, Object> params_request = body.getCondition();
        String hql_jjqd = "select UUID as UUID, PSN_NO as PSN_NO, BUSID as BUSID, PUSHDATE as PUSHDATE, RESID as RESID, LOGTIME as LOGTIME, FUNID as FUNID, SCSHBZ as SCSHBZ, YBSHYJ as YBSHYJ, QRCSSJ as QRCSSJ from GJYB_JJQD_PUSHLOG where busid = '" + params_request.get("RELATION")+"'";
//        String test = "select * from v_gjyb_jjqdplmx";
        //TODO
        List<Map<String, Object>> list_jjqd = iSysDynamicSourceService.dynamicReportList(hql_jjqd, DBID);
        GJYB_JJQD_PUSHLOG pushlog = null;
        if(list_jjqd.size()>0) {
            try {
                pushlog = (GJYB_JJQD_PUSHLOG) BeanMapUtils.mapToObject(list_jjqd.get(0), GJYB_JJQD_PUSHLOG.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //必须要有日志
        if (null != list_jjqd && list_jjqd.size() > 0) {
            String ls_funid = "4101A";
            DrgsSqlconfig mainConfig = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, DrgsStatus.MAZH_MAIN.getCode());
            DrgsSqlconfig setlinfo = drgsSqlconfigMapper.selectDrgsSqlconfigByServiceId(ls_funid, "setlinfo");
            if (null == mainConfig || null == setlinfo) {
                map_res.put("code", 1);
                map_res.put("msg", "无配置");
                return null;
            }
            JSONObject jo_res = pullJjqdLog(mainConfig, setlinfo, pushlog);
            if (null != jo_res) {
                map_res.put("code", 0);
                map_res.put("ybResult", jo_res);
                map_res.put("msg", "查询上传记录成功");
            } else {
                map_res.put("code", 1);
                map_res.put("msg", "医保查询失败");
            }
        } else {
            map_res.put("code", 11);
            map_res.put("msg", "未上传数据，请先上传");
        }
        return map_res;
    }

    @Override
    public Map<String, Object> resetStatus(DrgsRequestBody body) {
        Map<String, Object> params = body.getCondition();
//        GJYB_JJQD_PUSHLOG jjqd = gjybpushlogMapper.selectById(params.get("TSID")+"");
        GJYB_JJQD_PUSHLOG_TS jjqd = (GJYB_JJQD_PUSHLOG_TS)iSysDynamicSourceService.getDynamicSqlMapper(gjybpushlog_tsMapper,DBID,params.get("TSID")+"");
        jjqd.setSCSHBZ(params.get("status")+"");
        if("0".equals(params.get("status")+"")){
            jjqd.setRESID("");
        }else{
            jjqd.setRESID("已上传");
        }
//        int count = gjybpushlogMapper.updateById(jjqd);
        int count = iSysDynamicSourceService.dynamicSqlMapper(gjybpushlog_tsMapper,DBID,jjqd);
        Map<String, Object> map_res = new HashMap<>();
        if(count>0){
            map_res.put("code", 0);
            map_res.put("ybResult", "");
            map_res.put("msg", "成功");
        }else{
            map_res.put("code", 11);
            map_res.put("msg", "未上传数据，请先上传");
        }
        return map_res;
    }

    @Override
    public Map<String, Object> resetStatus_temp(DrgsRequestBody body) {
        Map<String, Object> params = body.getCondition();
        int count = 0;
        if("basy".equals(params.get("type").toString())){
            GJYB_ZYBA_PUSHLOG jjqd = (GJYB_ZYBA_PUSHLOG)iSysDynamicSourceService.getDynamicSqlMapper(gjybzybapushlogMapper,DBID,params.get("TSID")+"");
            jjqd.setSCSHBZ(params.get("status")+"");
            if("0".equals(params.get("status")+"")){
                jjqd.setRESID("");
            }else{
                jjqd.setRESID("已上传");
            }
            count = iSysDynamicSourceService.dynamicSqlMapper(gjybzybapushlogMapper,DBID,jjqd);
        }else if("zyyz".equals(params.get("type").toString())){
            GJYB_ZYYZ_PUSHLOG jjqd = (GJYB_ZYYZ_PUSHLOG)iSysDynamicSourceService.getDynamicSqlMapper(gjybzyyzpushlogMapper,DBID,params.get("TSID")+"");
            jjqd.setSCSHBZ(params.get("status")+"");
            if("0".equals(params.get("status")+"")){
                jjqd.setRESID("");
            }else{
                jjqd.setRESID("已上传");
            }
            count = iSysDynamicSourceService.dynamicSqlMapper(gjybzyyzpushlogMapper,DBID,jjqd);
        }else if("dzbl".equals(params.get("type").toString())){
            GJYB_DZBL_PUSHLOG jjqd = (GJYB_DZBL_PUSHLOG)iSysDynamicSourceService.getDynamicSqlMapper(gjybdzblpushlogMapper,DBID,params.get("TSID")+"");
            jjqd.setSCSHBZ(params.get("status")+"");
            if("0".equals(params.get("status")+"")){
                jjqd.setRESID("");
            }else{
                jjqd.setRESID("已上传");
            }
            count = iSysDynamicSourceService.dynamicSqlMapper(gjybdzblpushlogMapper,DBID,jjqd);
        }

        Map<String, Object> map_res = new HashMap<>();
        if(count>0){
            map_res.put("code", 0);
            map_res.put("ybResult", "");
            map_res.put("msg", "成功");
        }else{
            map_res.put("code", 11);
            map_res.put("msg", "未上传数据，请先上传");
        }
        return map_res;
    }

    public JSONObject pullJjqdLog(DrgsSqlconfig mainConfig, DrgsSqlconfig setlinfo, GJYB_JJQD_PUSHLOG pushlog) {
        String sql = StringUtils.numberOrString(pushlog.getBUSID())==false?"'"+pushlog.getBUSID()+"'":pushlog.getBUSID();
        String ls_query_main = "select * from (" + mainConfig.getSqlcontent() + ") a where a.\"" + mainConfig.getRelationcolumn() + "\"= " + sql;
        String ls_query_setlid = "select setl_id from (" + setlinfo.getSqlcontent() + ") a where a.psn_no ='" + pushlog.getPSN_NO() + "' and a.\"" + setlinfo.getRelationcolumn() + "\"= " + sql;
        JSONObject jo_res = null;
        try {
            String jgid = "1";
            String ls_funid = "4103";
            List<Map<String, Object>> list_mapmain = iSysDynamicSourceService.dynamicReportList(ls_query_main, mainConfig.getDbid());
            //在未考虑多数据源的情况下，可不用如此麻烦
            List<Map<String, Object>> list_setmain = iSysDynamicSourceService.dynamicReportList(ls_query_setlid, setlinfo.getDbid());
            if (null != list_mapmain && list_mapmain.size() == 1 && null != list_setmain && list_setmain.size() == 1) {
                Map<String, Object> map_main = list_mapmain.get(0);
                Map<String, Object> map_data = list_setmain.get(0);
                map_data.put("psn_no", pushlog.getPSN_NO());
                //多机构考虑
                String hOSIDKEY = HOSIDKEY;
                String hOSNAME = HOSNAME;
                String oPTER = OPTER;
                String hOSSECRET = HOSSECRET;
                String insdadvms = localadvms; //参保地
                String medadvms = localadvms;//就医地
                if (startAreaUpload == true) {
                    hOSIDKEY = map_main.get("HOSIDKEY") + "";
                    hOSNAME = map_main.get("HOSNAME") + "";
                    oPTER = map_main.get("OPTER") + "";
                    hOSSECRET = map_main.get("HOSSECRET") + "";
                    insdadvms = map_main.get("INSDADVMS") + "";
                    medadvms = map_main.get("MEDADVMS") + "";
                    jgid = map_main.get("JGID") + "";
                }
                String sign_no = "";// initSign(jgid,oPTER);
                Map<String, Object> base = new HashMap<String, Object>();
                base.put("data", map_data);
                String ls_reqJson = getRequesJson(ls_funid, base, medadvms, insdadvms, hOSIDKEY, hOSNAME, oPTER, sign_no);
                String ls_resJson = GjybUtils.sentServiceJson(getRequestUrl(ybjsxxUrl), ls_reqJson, hOSIDKEY, hOSSECRET);
                jo_res = GjybUtils.readResJson(ls_resJson);

                JSONObject jo_output = jo_res.getJSONObject("output");
                JSONObject jo_setinfo = jo_output.getJSONObject("setlinfo");

                String ls_stas_type = jo_setinfo.getString("stas_type");
                String ls_chk_cont = jo_setinfo.getString("chk_cont");
                if (null != ls_stas_type) {
                    String ls_setl_list_sn = jo_setinfo.getString("setl_list_sn");
                    //已提交
                    if ("1".equals(ls_stas_type)) {
                        pushlog.setSCSHBZ("2");
                    } else {
                        pushlog.setSCSHBZ("1");
//                        if (null == ls_chk_cont) {
//                            pushlog.setYBSHYJ("暂无");
//                        } else {
//                            pushlog.setYBSHYJ(ls_chk_cont);
//                        }
                    }
                    //当本地记录为错误，而医保有数据时
                    if ((null != ls_setl_list_sn) && pushlog.getRESID().startsWith("error") && !pushlog.getRESID().equalsIgnoreCase(ls_setl_list_sn)) {
                        pushlog.setRESID(ls_setl_list_sn);
                    }
                } else {
                    //医保无数据
                    pushlog.setSCSHBZ("0");
                }
                //gjybpushlogMapper.updateById(pushlog);
                iSysDynamicSourceService.dynamicSqlMapper(gjybpushlogMapper,DBID,pushlog);
            }
        } catch (Exception e) {
            log.error("4103查询异常：" + e.getMessage(), e);
        }
        return jo_res;
    }

    /**
     * 多机构版本，每个机构不同的签到编号
     *
     * @param jgid 机构代码
     */
    public String initSign(String jgid, String oPTER) {
        String sign_no = "";
        String sql = "select max(cast(qdbh as numeric)) as QDBH from gjyb_qdjl where ygdm='" + oPTER + "' and qtbz = 0 and jgid ='" + jgid + "'";
        //需要做个缓存策略，一定时间内有效，那样就不用重复查询了。TODO
        List<Map<String, Object>> list_siglist = iSysDynamicSourceService.dynamicReportList(sql, DBID);
        if (null == list_siglist || list_siglist.isEmpty() || null == list_siglist.get(0).get("QDBH")) {
            //签到
            sign_no = getSignNo(jgid, oPTER);
            if (null == sign_no || sign_no.isEmpty()) {
                log.error("---------请联系将日志发送给开发人员---------");
                log.error("---------请联系将日志发送给开发人员---------");
            } else {
                SIGN_NO = sign_no;
                //保存签到记录
                GJYB_QDJL qdjl = new GJYB_QDJL();
                qdjl.setJGID(jgid);
                qdjl.setQDBH(sign_no);
                qdjl.setYGDM(oPTER);
                qdjl.setJSJM("mac-pc");//计算机名
                qdjl.setIPDZ(PcInfoUtils.getIP());
                qdjl.setMAC(PcInfoUtils.getMACADD());
                qdjl.setQDSJ(new Date());
                qdjl.setQTBZ(0);
                gjybMapper.insert(qdjl);
            }
        } else {
            sign_no = list_siglist.get(0).get("QDBH") + "";
            SIGN_NO = sign_no;
        }
        return sign_no;
    }

    /**
     * 签到，通过jgid切换不同机构
     *
     * @return
     */
    private String getSignNo(String jgid, String oPTER) {
        String ls_hoskey = "", ls_hossec = "", ls_hosName = "";
        if (null == jgid || LOCAL_JGID.equals(jgid)) {
            ls_hoskey = HOSIDKEY;
            ls_hossec = HOSSECRET;
        } else {
            //根据不同机构获取相应的机构编码和秘钥
            ls_hoskey = getXtcsValue(jgid, "GJYB_SECRET");
            ls_hossec = getXtcsValue(jgid, "GJYB_YYBH");
            ls_hosName = getXtcsValue(jgid, "GJYB_YYMC");

        }
//        ls_hoskey = HOSIDKEY;
//        ls_hossec = HOSSECRET;
//        ls_hosName = HOSNAME;
        String URL = getRequestUrl(signInUrl);
        HashMap<String, Object> hm_input = new LinkedHashMap<>();
        HashMap<String, String> hm_sign = new LinkedHashMap<>();
        hm_sign.put(REQ_OPTER_NO, oPTER);
        hm_sign.put("mac", PcInfoUtils.getMACADD());
        hm_sign.put("ip", PcInfoUtils.getIP());
        hm_input.put("signIn", hm_sign);
        String ls_reqJson = getRequesJson("9001", hm_input, localadvms, localadvms, ls_hoskey, ls_hosName, oPTER, "");
        String ls_resJson = GjybUtils.sentServiceJson(URL, ls_reqJson, ls_hoskey, ls_hossec);
        JSONObject jo_res = GjybUtils.readResJson(ls_resJson);
        if ("0".equals(jo_res.getString(RES_CODE_KEY))) {
            JSONObject jo_output = jo_res.getJSONObject(RES_OUTPUT_KEY);
            JSONObject jo_signout = jo_output.getJSONObject("signinoutb");
            return jo_signout.getString(REQ_SIGN_NO);
        } else {
            //失败，就尝试一次签退
            SIGN_NO = "";
//            sendSignOut(jgid, oPTER);
            log.error(jo_res.getString(RES_ERR_MSG));
            return null;
        }
    }

    /**
     * 获取系统参数
     *
     * @param jgid
     * @param csmc
     * @return
     */
    public String getXtcsValue(String jgid, String csmc) {
        String querySql = "select * from GY_XTCS a where a.csmc = " + csmc + " and a.jgid = " + jgid + " ";
        Map<String, Object> reqMpa = new HashMap<String, Object>();
        reqMpa.put("CSMC", csmc);
        reqMpa.put("JGID", jgid);
        String resStr = "";
        try {
            //TODO 设置一个默认数据库
            List<Map<String, Object>> resDataList = iSysDynamicSourceService.dynamicReportList(querySql, DBID);
            Map<String, Object> res_map = resDataList.get(0);
            resStr = res_map.get("CSZ") + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resStr;
    }

    /**
     * @param funid     接口交易号
     * @param hm_input  入参
     * @param medadvms  就医地
     * @param insdadvms 参保地
     * @param HOSIDKEY  key
     * @param HOSNAME   医院名称
     * @param OPTER     操作者
     * @return
     */
    private String getRequesJson(String funid, Map<String, Object> hm_input, String medadvms, String insdadvms, String HOSIDKEY, String HOSNAME, String OPTER, String sign_no) {
        log.info("医保交易======接口交易号:{} ,hm_input:{} ,medadvms:{} ,insdadvms:{} ,HOSIDKEY:{} ,HOSNAME:{} ,OPTER:{}。"
                ,funid,hm_input,medadvms,insdadvms,HOSIDKEY,HOSNAME,OPTER);
        return GjybUtils.createJsonReq(funid, HOSIDKEY, HOSNAME, OPTER, sign_no, medadvms, insdadvms, "V1.0", hm_input);
    }

    private String getRequestUrl(String url) {
        if (null == baseip) {
            //TODO 多机构版本，涉及地址、机构编号及秘钥，不考虑
        }
        return baseip + url;
    }
}
