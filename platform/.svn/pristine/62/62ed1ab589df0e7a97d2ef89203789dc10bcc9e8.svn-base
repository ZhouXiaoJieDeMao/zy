package com.bsoft.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

public interface SysDynamicSourceMapper {
//    @Select("${sql}")
    public List<Map<String,Object>> dynamicReportList(@Param("sql")String sql);


    public List<Map<String,Object>> dynamicReportList1(@Param("map")Map map);
    /**
     * 动态sql
     * @param map
     * @return
     */
    public List<Map<String,Object>> dynamicSqlList(@Param("map")Map map);

    /**
     * 动态sql
     * @param map
     * @return
     */
    public List<Map<String,Object>> dynamicSqlList4401(@Param("map")Map map);

    /**
     * 动态sql
     * @param map
     * @return
     */
    public <E>E dynamicSqlList_T(@Param("map")Map map);

    /**
     * 动态sql
     * @param map
     * @return
     */
    public List<Map<String,Object>> dynamicSqlList1(@Param("map")Map map);

    /**
     * 动态sql
     * @param map
     * @return
     */
    public List<Map<String,Object>> dynamicSqlList2(@Param("map")Map map);
}
