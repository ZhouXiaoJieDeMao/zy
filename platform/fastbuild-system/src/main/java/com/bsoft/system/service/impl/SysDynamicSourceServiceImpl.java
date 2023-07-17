package com.bsoft.system.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bsoft.common.annotation.DataSource;
import com.bsoft.common.enums.DataSourceType;
import com.bsoft.system.domain.drgs.GJYB_JJQD_PUSHLOG;
import com.bsoft.system.domain.drgs.GJYB_PUSHLOG;
import com.bsoft.system.mapper.RootMapper;
import com.bsoft.system.mapper.SysDynamicSourceMapper;
import com.bsoft.system.mapper.SysNoticeMapper;
import com.bsoft.system.service.ISysDynamicSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@DataSource(value = DataSourceType.SLAVE)
public class SysDynamicSourceServiceImpl implements ISysDynamicSourceService {

    @Autowired
    private SysDynamicSourceMapper sysDynamicSourceMapper;

    @Override
    public List<Map<String, Object>> dynamicReportList(String sql,String databaseType) {
        return sysDynamicSourceMapper.dynamicReportList(sql);
    }

    @Override
    public List<Map<String, Object>> dynamicReportList1(Map<String,Object> map, String databaseType) {
        return sysDynamicSourceMapper.dynamicReportList1(map);
    }

    @Override
    public List<Map<String, Object>> dynamicSqlList(Map<String, Object> map, String databaseType) {
        return sysDynamicSourceMapper.dynamicSqlList(map);
    }

    @Override
    public List<Map<String, Object>> dynamicSqlList4401(Map<String, Object> map, String databaseType) {
        return sysDynamicSourceMapper.dynamicSqlList4401(map);
    }

    @Override
    public <E>E dynamicSql_T(Map<String, Object> map, String databaseType, E t) {
        return sysDynamicSourceMapper.dynamicSqlList_T(map);
    }

    @Override
    public List<Map<String, Object>> dynamicSqlList12(Map<String, Object> map, String databaseType) {
        return null;
    }

    @Override
    public List<Map<String, Object>> dynamicSqlList1(Map<String, Object> map, String databaseType) {
        return sysDynamicSourceMapper.dynamicSqlList1(map);
    }

    @Override
    public List<Map<String, Object>> dynamicSqlList2(Map<String, Object> map, String databaseType) {
        return sysDynamicSourceMapper.dynamicSqlList2(map);
    }

    @Override
    public Integer dynamicSqlMapper(RootMapper mapper, String databaseType, Object o) {
        return mapper.updateById(o);
    }

    @Override
    public void dynamicSqlMapperSave(RootMapper mapper, String databaseType, Object o) {
        mapper.insert(o);
    }

    @Override
    public void dynamicSqlMapperUpdateList(RootMapper mapper, String databaseType, List list) {
        mapper.updateBatch(list);
    }

    @Override
    public GJYB_PUSHLOG getDynamicSqlMapper(RootMapper mapper, String databaseType, String o) {
        return (GJYB_PUSHLOG)mapper.selectById(o);
    }
}
