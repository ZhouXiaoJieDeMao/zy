package com.bsoft.system.mapper;

import java.util.List;
import com.bsoft.system.domain.DrgsSqlconfig;
import org.apache.ibatis.annotations.Param;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author fastbuild
 * @date 2022-07-12
 */
public interface DrgsSqlconfigMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public DrgsSqlconfig selectDrgsSqlconfigById(Long id);

    /**
     * 查询【请填写功能名称】
     *
     * @param serviceId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public DrgsSqlconfig selectDrgsSqlconfigByServiceId(@Param("serviceId") String serviceId, @Param("sqlType") String sqlType);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param drgsSqlconfig 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DrgsSqlconfig> selectDrgsSqlconfigList(DrgsSqlconfig drgsSqlconfig);

    /**
     * 新增【请填写功能名称】
     * 
     * @param drgsSqlconfig 【请填写功能名称】
     * @return 结果
     */
    public int insertDrgsSqlconfig(DrgsSqlconfig drgsSqlconfig);

    /**
     * 修改【请填写功能名称】
     * 
     * @param drgsSqlconfig 【请填写功能名称】
     * @return 结果
     */
    public int updateDrgsSqlconfig(DrgsSqlconfig drgsSqlconfig);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteDrgsSqlconfigById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDrgsSqlconfigByIds(Long[] ids);
}
