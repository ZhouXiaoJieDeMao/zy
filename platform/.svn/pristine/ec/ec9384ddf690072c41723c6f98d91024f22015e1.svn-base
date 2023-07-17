package com.bsoft.system.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bsoft.system.domain.drgs.GJYB_JJQD_PUSHLOG;
import com.bsoft.system.domain.drgs.GJYB_PUSHLOG;
import com.bsoft.system.mapper.RootMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
 * 动态sql执行语句
 */
public interface ISysDynamicSourceService {
    /**
     * 根据数据库类型执行sql
     * @param sql
     * @param databaseType
     * @return
     */
    public List<Map<String,Object>> dynamicReportList(String sql,String databaseType);

    /**
     * 根据数据库类型执行sql
     * @param map
     * @param databaseType
     * @return
     */
    public List<Map<String,Object>> dynamicReportList1(Map<String,Object> map,String databaseType);

    /**
     * 根据数据库类型执行sql
     * @param map
     * @param databaseType
     * @return
     */
    public List<Map<String,Object>> dynamicSqlList(Map<String,Object> map,String databaseType);

    /**
     * 根据数据库类型执行sql
     * @param map
     * @param databaseType
     * @return
     */
    public List<Map<String,Object>> dynamicSqlList1(Map<String,Object> map,String databaseType);

    /**
     * 根据数据库类型执行sql
     * @param map
     * @param databaseType
     * @return
     */
    public List<Map<String,Object>> dynamicSqlList4401(Map<String,Object> map,String databaseType);


    /**
     * 根据数据库类型执行sql
     * @param map
     * @param databaseType
     * @return
     */
    public <E>E dynamicSql_T(Map<String,Object> map, String databaseType,E t);

    /**
     * 根据数据库类型执行sql
     * @param map
     * @param databaseType
     * @return
     */
    public List<Map<String,Object>> dynamicSqlList12(Map<String,Object> map,String databaseType);

    /**
     * 根据数据库类型执行sql
     * @param map
     * @param databaseType
     * @return
     */
    public List<Map<String,Object>> dynamicSqlList2(Map<String,Object> map,String databaseType);

    /**
     * 根据数据库类型执行sql
     * @param mapper
     * @param databaseType
     * @return
     */
    public Integer dynamicSqlMapper(RootMapper mapper, String databaseType, Object o);

    /**
     * 根据数据库类型执行sql
     * @param mapper
     * @param databaseType
     * @return
     */
    public void dynamicSqlMapperSave(RootMapper mapper, String databaseType, Object o);

    /**
     * 根据数据库类型执行sql
     * @param mapper
     * @param databaseType
     * @return
     */
    public void dynamicSqlMapperUpdateList(RootMapper mapper, String databaseType,List list);

    /**
     * 根据数据库类型执行sql
     * @param mapper
     * @param databaseType
     * @return
     */
    public GJYB_PUSHLOG getDynamicSqlMapper(RootMapper mapper, String databaseType, String o);

}
