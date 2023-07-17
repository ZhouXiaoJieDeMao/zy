package com.bsoft.system.service.impl;

import java.util.List;
import java.util.Map;

import com.bsoft.system.service.ISysDynamicSourceService;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bsoft.system.mapper.SysDynamicReportMapper;
import com.bsoft.system.domain.SysDynamicReport;
import com.bsoft.system.service.ISysDynamicReportService;

import static com.bsoft.common.utils.PageUtils.startPage;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author fastbuild
 * @date 2022-07-02
 */
@Service
public class SysDynamicReportServiceImpl implements ISysDynamicReportService 
{
    @Autowired
    private SysDynamicReportMapper sysDynamicReportMapper;

    @Autowired
    private ISysDynamicSourceService iSysDynamicSourceService;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public SysDynamicReport selectSysDynamicReportById(Long id)
    {
        return sysDynamicReportMapper.selectSysDynamicReportById(id);
    }

    @Override
    public List<Map<String, Object>> getInfoById(Long id) {
        SysDynamicReport sysDynamicReport = sysDynamicReportMapper.selectSysDynamicReportById(id);
        return iSysDynamicSourceService.dynamicReportList(sysDynamicReport.getOperationSql(),sysDynamicReport.getDatabaseType());
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param sysDynamicReport 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysDynamicReport> selectSysDynamicReportList(SysDynamicReport sysDynamicReport)
    {
        return sysDynamicReportMapper.selectSysDynamicReportList(sysDynamicReport);
    }

    /**
     * 参数分解配置到sql
     * TODO 查询条件需要优化
     * @param sysDynamicReport 【请填写功能名称】
     * @return
     */
    @Override
    public List<Map<String, Object>> reportList(SysDynamicReport sysDynamicReport) {
        sysDynamicReport = sysDynamicReportMapper.selectSysDynamicReportById(sysDynamicReport.getId());
        StringBuffer sql = new StringBuffer(sysDynamicReport.getOperationSql());
        if(sysDynamicReport.getParamSql()!=null&&!"".equals(sysDynamicReport.getParamSql())){
            Map<String,Object> map = sysDynamicReport.getCondition();
            String time = map.get("param1")!=null?map.get("param1")+"":"";
            if(!"".equals(time)){
                String[] times = time.split(",");
                String paramSql = sysDynamicReport.getParamSql();
                paramSql = paramSql.replace("#{param1}","'"+times[0]+"'");
                paramSql = paramSql.replace("#{param2}","'"+times[1]+"'");
                sql.insert(0,"select * from (");
                sql.append(") where "+paramSql);
            }
        }
        String regSql = sql.toString();
        String lastSql = StringEscapeUtils.unescapeHtml4(regSql);
        startPage();
        return iSysDynamicSourceService.dynamicReportList(lastSql,sysDynamicReport.getDatabaseType());
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param sysDynamicReport 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSysDynamicReport(SysDynamicReport sysDynamicReport)
    {
        sysDynamicReport.setOperationSql(StringEscapeUtils.unescapeHtml4(sysDynamicReport.getOperationSql()));
        sysDynamicReport.setParamSql(StringEscapeUtils.unescapeHtml4(sysDynamicReport.getParamSql()));
        return sysDynamicReportMapper.insertSysDynamicReport(sysDynamicReport);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param sysDynamicReport 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSysDynamicReport(SysDynamicReport sysDynamicReport)
    {
        sysDynamicReport.setOperationSql(StringEscapeUtils.unescapeHtml4(sysDynamicReport.getOperationSql()));
        sysDynamicReport.setParamSql(StringEscapeUtils.unescapeHtml4(sysDynamicReport.getParamSql()));
        return sysDynamicReportMapper.updateSysDynamicReport(sysDynamicReport);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteSysDynamicReportByIds(Long[] ids)
    {
        return sysDynamicReportMapper.deleteSysDynamicReportByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteSysDynamicReportById(Long id)
    {
        return sysDynamicReportMapper.deleteSysDynamicReportById(id);
    }
}
