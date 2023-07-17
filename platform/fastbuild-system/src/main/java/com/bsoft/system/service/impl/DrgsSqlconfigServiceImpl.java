package com.bsoft.system.service.impl;

import java.util.List;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bsoft.system.mapper.DrgsSqlconfigMapper;
import com.bsoft.system.domain.DrgsSqlconfig;
import com.bsoft.system.service.IDrgsSqlconfigService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author fastbuild
 * @date 2022-07-12
 */
@Service
public class DrgsSqlconfigServiceImpl implements IDrgsSqlconfigService 
{
    @Autowired
    private DrgsSqlconfigMapper drgsSqlconfigMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public DrgsSqlconfig selectDrgsSqlconfigById(Long id)
    {
        return drgsSqlconfigMapper.selectDrgsSqlconfigById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param drgsSqlconfig 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<DrgsSqlconfig> selectDrgsSqlconfigList(DrgsSqlconfig drgsSqlconfig)
    {
        return drgsSqlconfigMapper.selectDrgsSqlconfigList(drgsSqlconfig);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param drgsSqlconfig 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertDrgsSqlconfig(DrgsSqlconfig drgsSqlconfig)
    {
        drgsSqlconfig.setRelationcolumn(StringEscapeUtils.unescapeHtml4(drgsSqlconfig.getRelationcolumn()));
        return drgsSqlconfigMapper.insertDrgsSqlconfig(drgsSqlconfig);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param drgsSqlconfig 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateDrgsSqlconfig(DrgsSqlconfig drgsSqlconfig)
    {
        drgsSqlconfig.setRelationcolumn(StringEscapeUtils.unescapeHtml4(drgsSqlconfig.getRelationcolumn()));
        return drgsSqlconfigMapper.updateDrgsSqlconfig(drgsSqlconfig);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDrgsSqlconfigByIds(Long[] ids)
    {
        return drgsSqlconfigMapper.deleteDrgsSqlconfigByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDrgsSqlconfigById(Long id)
    {
        return drgsSqlconfigMapper.deleteDrgsSqlconfigById(id);
    }
}
