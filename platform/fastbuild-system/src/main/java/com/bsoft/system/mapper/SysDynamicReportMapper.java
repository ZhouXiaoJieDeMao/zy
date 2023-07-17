package com.bsoft.system.mapper;

import java.util.List;
import java.util.Map;

import com.bsoft.system.domain.SysDynamicReport;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author fastbuild
 * @date 2022-07-02
 */
public interface SysDynamicReportMapper 
{
    @Select("${sql}")
    public List<Map<String,Object>> dynamicReportList(@Param("sql")String sql);
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public SysDynamicReport selectSysDynamicReportById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param sysDynamicReport 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SysDynamicReport> selectSysDynamicReportList(SysDynamicReport sysDynamicReport);

    /**
     * 新增【请填写功能名称】
     * 
     * @param sysDynamicReport 【请填写功能名称】
     * @return 结果
     */
    public int insertSysDynamicReport(SysDynamicReport sysDynamicReport);

    /**
     * 修改【请填写功能名称】
     * 
     * @param sysDynamicReport 【请填写功能名称】
     * @return 结果
     */
    public int updateSysDynamicReport(SysDynamicReport sysDynamicReport);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteSysDynamicReportById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysDynamicReportByIds(Long[] ids);
}
