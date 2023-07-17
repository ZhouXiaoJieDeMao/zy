package com.bsoft.web.controller.system;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bsoft.common.annotation.Log;
import com.bsoft.common.core.controller.BaseController;
import com.bsoft.common.core.domain.AjaxResult;
import com.bsoft.common.enums.BusinessType;
import com.bsoft.system.domain.SysDynamicReport;
import com.bsoft.system.service.ISysDynamicReportService;
import com.bsoft.common.utils.poi.ExcelUtil;
import com.bsoft.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 * 
 * @author fastbuild
 * @date 2022-07-02
 */
@RestController
@RequestMapping("/system/report")
public class SysDynamicReportController extends BaseController
{
    @Autowired
    private ISysDynamicReportService sysDynamicReportService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:report:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDynamicReport sysDynamicReport)
    {
        startPage();
        List<SysDynamicReport> list = sysDynamicReportService.selectSysDynamicReportList(sysDynamicReport);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:report:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDynamicReport sysDynamicReport)
    {
        List<SysDynamicReport> list = sysDynamicReportService.selectSysDynamicReportList(sysDynamicReport);
        ExcelUtil<SysDynamicReport> util = new ExcelUtil<SysDynamicReport>(SysDynamicReport.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:report:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysDynamicReportService.selectSysDynamicReportById(id));
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:report:query')")
    @GetMapping(value = "/sql/{id}")
    public TableDataInfo getInfoById(@PathVariable("id") Long id)
    {
        startPage();
        List<Map<String,Object>> list = sysDynamicReportService.getInfoById(id);
        return getDataTable(list);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:report:list')")
    @GetMapping("/reportList")
    public TableDataInfo reportList(SysDynamicReport sysDynamicReport)
    {

        List<Map<String,Object>> list = sysDynamicReportService.reportList(sysDynamicReport);
        return getDataTable(list);
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:report:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysDynamicReport sysDynamicReport)
    {
        return toAjax(sysDynamicReportService.insertSysDynamicReport(sysDynamicReport));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:report:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysDynamicReport sysDynamicReport)
    {
        return toAjax(sysDynamicReportService.updateSysDynamicReport(sysDynamicReport));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:report:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysDynamicReportService.deleteSysDynamicReportByIds(ids));
    }
}
