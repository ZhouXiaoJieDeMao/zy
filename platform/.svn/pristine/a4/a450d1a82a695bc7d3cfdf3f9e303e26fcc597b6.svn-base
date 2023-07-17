package com.bsoft.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bsoft.common.annotation.Log;
import com.bsoft.common.core.controller.BaseController;
import com.bsoft.common.core.domain.AjaxResult;
import com.bsoft.common.enums.BusinessType;
import com.bsoft.system.domain.DrgsSqlconfig;
import com.bsoft.system.service.IDrgsSqlconfigService;
import com.bsoft.common.utils.poi.ExcelUtil;
import com.bsoft.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 * 
 * @author fastbuild
 * @date 2022-07-12
 */
@RestController
@RequestMapping("/system/sqlconfig")
public class DrgsSqlconfigController extends BaseController
{
    @Autowired
    private IDrgsSqlconfigService drgsSqlconfigService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:sqlconfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(DrgsSqlconfig drgsSqlconfig)
    {
        startPage();
        List<DrgsSqlconfig> list = drgsSqlconfigService.selectDrgsSqlconfigList(drgsSqlconfig);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:sqlconfig:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DrgsSqlconfig drgsSqlconfig)
    {
        List<DrgsSqlconfig> list = drgsSqlconfigService.selectDrgsSqlconfigList(drgsSqlconfig);
        ExcelUtil<DrgsSqlconfig> util = new ExcelUtil<DrgsSqlconfig>(DrgsSqlconfig.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:sqlconfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(drgsSqlconfigService.selectDrgsSqlconfigById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:sqlconfig:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DrgsSqlconfig drgsSqlconfig)
    {
        return toAjax(drgsSqlconfigService.insertDrgsSqlconfig(drgsSqlconfig));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:sqlconfig:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DrgsSqlconfig drgsSqlconfig)
    {
        return toAjax(drgsSqlconfigService.updateDrgsSqlconfig(drgsSqlconfig));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:sqlconfig:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(drgsSqlconfigService.deleteDrgsSqlconfigByIds(ids));
    }
}
