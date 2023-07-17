package com.bsoft.system.service;

import com.bsoft.system.domain.drgs.DrgsRequestBody;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * description：医养护数据采集接口
 */
public interface IYyhDataAggrService {

//    List<Map<String, Object>> getYyhDataAggr();
//
//    Map<String, Boolean> doUpload(Map<String, Object> params);
//
//    Map<String, Object> updateSer(DrgsRequestBody body);
//
//    Map<String, Object> updateSing(Map<String, Object> params);
//
//    Map<String, Object> updateSingForHsbg(Map<String, Object> params);
//
//    Map<String, Object> updateSingForPerson(Map<String, Object> params);
//    /**
//     * 数据上传明细记录获取
//     * @param params
//     * @return
//     */
//	Map<String, Object> getYyhUpdataLog(Map<String, Object> params);
//
//    Map<String, Object> upSjdz(Map<String, Object> params);
//
//
//    String getHeartBeat(String servicecode);
//    String getAggrResult(String servicecode, Map<String, Object> businessdata) throws Exception;

    List<Map<String,Object>> tJUploadQueryPage(DrgsRequestBody body);

    Map<String,Object> tJPdfUpload(Map body) throws Exception;

    PageInfo tjjlUploadQueryPage(DrgsRequestBody body);

    Map<String,Object> tJjlUpload(Map<String,Object> params);

    String discard(Map<String, Object> params);

    /***
     * 查询明细数据
     * @param params
     * @return
     */
    Map<String,Object> detailsData(Map<String,Object> params);
}
