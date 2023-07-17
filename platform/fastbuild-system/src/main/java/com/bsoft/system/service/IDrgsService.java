package com.bsoft.system.service;


import com.bsoft.system.domain.drgs.DrgsRequestBody;

import java.util.List;
import java.util.Map;

public interface IDrgsService {

    /**
     * 查询drgs病人记录
     * @param body
     * @return
     */
    List<Map<String, Object>> getJjqsqdList(DrgsRequestBody body);


    List<Map<String, Object>> getJjqsqdListForEcharts(DrgsRequestBody body);

    /**
     * 查询drgs病人记录
     * @param body
     * @return
     */
    List<Map<String, Object>> getJjqsqdListExport(DrgsRequestBody body);

    /**
     * 单条4101A数据上传
     * @param body
     * @return
     */
    Map<String,Object> pushOneJjqd(DrgsRequestBody body);

    /**
     * 单条4401数据上传
     * @param body
     * @return
     */
    Map<String,Object> pushZybasy4401(DrgsRequestBody body,boolean autoflag);

    /**
     * 单条4402数据上传
     * @param body
     * @return
     */
    Map<String,Object> pushZybasy4402(DrgsRequestBody body,boolean autoflag);

    /**
     * 批量4402数据上传
     * @param body
     * @return
     */
    Map<String,Object> pushBatchZybasy4402(DrgsRequestBody body,boolean autoflag);

    /**
     * 批量4101A数据上传
     * @param body
     * @return
     */
    Map<String,Object> pushBatchJjqd(DrgsRequestBody body);

    /**
     * 4102基金清单确认
     * @param body 入参TSID
     * @return
     */
    Map<String,Object> pushConfirmOneMedinfo(DrgsRequestBody body);

    /**
     * 单条4701数据上传
     * @param body
     * @return
     */
    Map<String,Object> pushDzbl4701(DrgsRequestBody body, boolean autoflag);
    /**
     * 批量4701数据上传
     * @param body
     * @return
     */
    Map<String,Object> pushBatchDzbl4701(DrgsRequestBody body,boolean autoflag);
    /**
     * 4102基金清单批量确认
     * @param body
     * @return
     */
    Map<String,Object> pushConfirmBatchMedinfo(DrgsRequestBody body);

    /**
     * 4103医疗保障基金结算清单信息查询
     */
    Map<String,Object> doQueryYbJsQdxx(DrgsRequestBody body);

    /**
     * 4103医疗保障基金结算清单信息查询
     */
    Map<String,Object> resetStatus(DrgsRequestBody body);
    /**
     * 4103医疗保障基金结算清单信息查询
     */
    Map<String,Object> resetStatus_temp(DrgsRequestBody body);
}
