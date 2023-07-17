package com.bsoft.system.domain.yyh;

import com.bsoft.system.mapper.YYHAGGRMXLOGMapper;
import com.bsoft.system.service.ISysDynamicSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AggrLogServiceImpl {

    private static Logger logger = LoggerFactory.getLogger("aggr");

    /**
     * 动态数据库保存
     */
    @Autowired
    private ISysDynamicSourceService iSysDynamicSourceService;

    @Value("${gjyb.master}")
    private String DBID;
    @Autowired
    private YYHAGGRMXLOGMapper yyhaggrmxlogMapperMapper;

    @Value("${aggr.uplogflag}")
    private boolean uplogflag;

//    public void saveLastModifyTime(Map<String,Object> params,IBaseDao baseDao){
//        baseDao.executeHql("update YYH_DATA_AGGR set LASTEXECUTETIME=:now where COLRESCODE=:COLRESCODE",params);
//    }

    /**
     * 推送明细数据保存
     * @param sList
     * @param ttaskid
     * @param staskid
     * @param data_aggr
     * @param result
     */
//    @Modifying
    public void savePushResult(List<Map<String,Object>> sList, String ttaskid, String staskid, YYH_DATA_AGGR data_aggr, String result){
        if(!uplogflag){
            return;
        }
        //获取数据集编码对应的相关信息
        YyhDataMeta ydm = YyhDataMeta.getYyhMetaData(data_aggr.getSETCODE());
        if(null != ydm){
            //明细记录
            for(Map<String,Object> data : sList){
                YYH_AGGR_MXLOG mxlog = new YYH_AGGR_MXLOG();
                mxlog.setUUID(UUID.randomUUID().toString().replace("-",""));
                mxlog.setMAINTASKID(ttaskid);
                mxlog.setSINGLETASKID(staskid);
                mxlog.setPUSHRESULT(result);
                //一般等于data_aggr.getSETCODE()
                mxlog.setSJJBM(ydm.getSjjbm());
                //身份证信息
                if(!ydm.getIdcard().isEmpty()){
                    Object o_id = data.get(ydm.getIdcard());
                    mxlog.setIDCARD((null ==o_id)?"(-)":(String)o_id);
                }
                //机构信息
                Object o_orgcode = data.get(ydm.getOrgcode());
                mxlog.setORGCODE((null == o_orgcode)?"1212":(String)o_orgcode);
                //业务主键
                if(!ydm.getBusid().isEmpty()){
                    Object o_id = data.get(ydm.getBusid());
                    //java.math.BigDecimal cannot be cast to java.lang.String
                    if(o_id instanceof BigDecimal){
                        BigDecimal bd_id = (BigDecimal) o_id;
                        mxlog.setBUSID((null ==o_id)?"(-)":bd_id.toString());
                    }else{
                        mxlog.setBUSID((null ==o_id)?"(-)":o_id.toString());
                    }

                }
                //业务时间
                Object o_busdate = data.get(ydm.getBusdate());
                try {
                    if(o_busdate instanceof Date ||o_busdate instanceof Time){
                        mxlog.setBUSDATE((Date) o_busdate);
                    }else if(o_busdate instanceof String){
                        String ls_busdate = (String) o_busdate;
                        if(ls_busdate.length() == 14){
                            SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
                            mxlog.setBUSDATE(sf1.parse(ls_busdate));
                        }else if(ls_busdate.length() == 8){
                            SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
                            mxlog.setBUSDATE(sf1.parse(ls_busdate));
                        }
                    }else{
                        mxlog.setBUSDATE(new Date(1212));
                    }
                } catch (ParseException e) {
                    mxlog.setBUSDATE(new Date(1212));
                    logger.error("业务时间转换异常："+e.getMessage(),e);
                }
//                baseDao.save(mxlog);
                if(iSysDynamicSourceService.getDynamicSqlMapper(yyhaggrmxlogMapperMapper,DBID,mxlog.getBUSID())!=null){
                    iSysDynamicSourceService.dynamicSqlMapper(yyhaggrmxlogMapperMapper,DBID,mxlog);
                }else{
                    iSysDynamicSourceService.dynamicSqlMapperSave(yyhaggrmxlogMapperMapper,DBID,mxlog);
                }
            }
        }
    }
}
