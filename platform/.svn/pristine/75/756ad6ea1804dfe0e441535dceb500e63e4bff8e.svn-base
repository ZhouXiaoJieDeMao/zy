package com.bsoft.web.controller.drgs;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.bsoft.common.core.controller.BaseController;
import com.bsoft.common.core.domain.AjaxResult;
import com.bsoft.common.core.page.TableDataInfo;
import com.bsoft.common.utils.poi.ExcelUtil;
import com.bsoft.system.domain.drgs.DrgsRequestBody;
import com.bsoft.system.domain.drgs.ViewDrgsVO;
import com.bsoft.system.service.IDrgsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * drgs
 */
@RestController
@RequestMapping("/Gjyb")
public class DrgsController extends BaseController {

    @Autowired
    private IDrgsService iDrgsService;

    /**
     * 查询列表
     * @param body
     * @return
     */
    @PostMapping(value = {"/getJjqsqdList"})
    public TableDataInfo getJjqsqdList(@RequestBody DrgsRequestBody body){
        return getDataTable(iDrgsService.getJjqsqdList(body));
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, DrgsRequestBody body)
    {
        List<Map<String, Object>> list_temp = iDrgsService.getJjqsqdListExport(body);
        List<ViewDrgsVO> list= JSON.parseObject(JSON.toJSONString(list_temp), new TypeReference<List<ViewDrgsVO>>() {});
        ExcelUtil<ViewDrgsVO> util = new ExcelUtil<ViewDrgsVO>(ViewDrgsVO.class);
        util.exportExcel(response, list, "drgs数据");
    }

    /**
     * 查询列表图表
     * @param body
     * @return
     */
    @PostMapping(value = {"/getJjqsqdListForEcharts"})
    public TableDataInfo getJjqsqdListForEcharts(@RequestBody DrgsRequestBody body){
        return getDataTable(iDrgsService.getJjqsqdListForEcharts(body));
    }

    /**
     * 单条4101A数据上传
     * @param body
     * @return
     */
    @PostMapping(value = {"/pushOneMedinfo"})
    public Map<String, Object> doPushOneMedinfo(@RequestBody DrgsRequestBody body) {
        return iDrgsService.pushOneJjqd(body);
    }

    /**
     * 单条4401数据上传
     * @param body
     * @return
     */
    @PostMapping(value = {"/pushZybasy4401"})
    public AjaxResult pushZybasy4401(@RequestBody DrgsRequestBody body) {
        return AjaxResult.success(iDrgsService.pushZybasy4401(body,true));
    }

    /**
     * 单条4401数据上传
     * @param body
     * @return
     */
    @PostMapping(value = {"/pushBatchZybasy4401"})
    public AjaxResult pushBatchZybasy4401(@RequestBody DrgsRequestBody body) {
        return AjaxResult.success(iDrgsService.pushZybasy4401(body,false));
    }

    /**
     * 单条4402数据上传
     * @param body
     * @return
     */
    @PostMapping(value = {"/pushZybasy4402"})
    public AjaxResult pushZybasy4402(@RequestBody DrgsRequestBody body) {
        return AjaxResult.success(iDrgsService.pushZybasy4402(body,true));
    }

    /**
     * 批量4402数据上传
     * @param body
     * @return
     */
    @PostMapping(value = {"/pushBatchZybasy4402"})
    public AjaxResult pushBatchZybasy4402(@RequestBody DrgsRequestBody body) {
        return AjaxResult.success(iDrgsService.pushBatchZybasy4402(body,false));
    }

    /**
     * 单条4402数据上传
     * @param body
     * @return
     */
    @PostMapping(value = {"/pushDzbl4701"})
    public AjaxResult pushDzbl4701(@RequestBody DrgsRequestBody body) {
        return AjaxResult.success(iDrgsService.pushDzbl4701(body,true));
    }

    /**
     * 批量4402数据上传
     * @param body
     * @return
     */
    @PostMapping(value = {"/pushBatchDzbl4701"})
    public AjaxResult pushBatchDzbl4701(@RequestBody DrgsRequestBody body) {
        return AjaxResult.success(iDrgsService.pushBatchDzbl4701(body,false));
    }

    /**
     * 批量执行4101A数据上传
     *
     * @param body
     * @return
     */
    @PostMapping(value = {"/pushBatchMedinfo"})
    public Map<String, Object> doPushBatchMedinfo(@RequestBody DrgsRequestBody body) {
//        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
//        CompletableFuture.runAsync(() -> {
//        }, singleThreadExecutor);
        iDrgsService.pushBatchJjqd(body);
        Map<String, Object> map_res = new HashMap<>();
        map_res.put("code", 0);
        map_res.put("msg", "正在执行");
        return map_res;
    }

    /**
     * 4102医疗保障基金结算清单信息状态修改
     * @param body
     * 请求参数：TSID
     * @return 成功或失败
     */
    @PostMapping(value = {"/confirmOneMedinfo"})
    public Map<String, Object> doConfirmOneMedinfo(@RequestBody DrgsRequestBody body) {
        return iDrgsService.pushConfirmOneMedinfo(body);
    }

    /**
     * 4102批量请求
     *
     * @param body begindate、enddate
     * @return 无，页面重新刷新。
     */
    @PostMapping(value = {"/confirmBatchMedinfo"})
    public Map<String, Object> doConfirmBatchMedinfo(@RequestBody DrgsRequestBody body) {
//        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
//        CompletableFuture.runAsync(() -> {
//            iDrgsService.pushConfirmBatchMedinfo(body);
//        }, singleThreadExecutor);
        iDrgsService.pushConfirmBatchMedinfo(body);
        Map<String, Object> map_res = new HashMap<>();
        map_res.put("code", 200);
        map_res.put("msg", "正在执行");
        return map_res;
    }

    /**
     * 4103医疗保障基金结算清单信息查询
     */
    @PostMapping(value = {"/doQueryYbJsQdxx"})
    public Map<String, Object> doQueryYbJsQdxx(@RequestBody DrgsRequestBody body) {
        return iDrgsService.doQueryYbJsQdxx(body);
    }


    /**
     * 住院清单重置上传状态
     */
    @PostMapping(value = {"/resetStatus"})
    public Map<String, Object> resetStatus(@RequestBody DrgsRequestBody body) {
        return iDrgsService.resetStatus(body);
    }

    /**
     * 病案首页重置上传状态
     */
    @PostMapping(value = {"/resetStatus_temp"})
    public Map<String, Object> resetStatus_temp(@RequestBody DrgsRequestBody body) {
        return iDrgsService.resetStatus_temp(body);
    }
//
//    /**
//     * 电子病历重置上传状态
//     */
//    @PostMapping(value = {"/resetStatus"})
//    public Map<String, Object> resetStatus(@RequestBody DrgsRequestBody body) {
//        return iDrgsService.resetStatus(body);
//    }
//
//    /**
//     * 住院医嘱重置上传状态
//     */
//    @PostMapping(value = {"/resetStatus"})
//    public Map<String, Object> resetStatus(@RequestBody DrgsRequestBody body) {
//        return iDrgsService.resetStatus(body);
//    }
}
