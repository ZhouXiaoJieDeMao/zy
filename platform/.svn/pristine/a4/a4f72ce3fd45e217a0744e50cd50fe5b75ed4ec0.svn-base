package com.bsoft.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bsoft.system.domain.yyh.*;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service接口
 *
 * @author fastbuild
 * @date 2022-08-11
 */
public interface IYyhClientService extends IService<YYH_CLIENT>
{
    /**
     * 查询【请填写功能名称】
     *
     * @param clientid 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public YyhClient selectYyhClientByClientid(Long clientid);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param yyhClient 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<YyhClient> selectYyhClientList(YyhClient yyhClient);

    /**
     * 新增【请填写功能名称】
     *
     * @param yyhClient 【请填写功能名称】
     * @return 结果
     */
    public int insertYyhClient(YyhClient yyhClient);

    /**
     * 修改【请填写功能名称】
     *
     * @param yyhClient 【请填写功能名称】
     * @return 结果
     */
    public int updateYyhClient(YyhClient yyhClient);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param clientids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteYyhClientByClientids(Long[] clientids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param clientid 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteYyhClientByClientid(Long clientid);

    List<Map<String,Object>> dynamicSqlList(Map<String, Object> params, String dbid);

    List<TjPDFVo> selectTJPDF(Map<String, Object> params);

    Map<String,Object> getYYHBaseInfo(String laqtjpdf);

    int updateTjbgResult(Map<String, Object> updateMap);

    List<Map<String,Object>> selectTjjlList(Map<String, Object> params, String dbid);

    YyhDataConfig getYyhUpconfig(Map<String,Object> params);

    List<Map<String,Object>> selectYyhUploadList(String yyhUpsql,Map<String,Object> params);

    int updateUpdata(String update_sql);

    void saveErrorUpLog(String setcode, int size, int li_up_ecount);

    void saveRecordLog(YYH_AGGR_RECORD record);

    List<Map<String,Object>> selectGlUploadList(YyhDataConfig config, Map<String, String[]> map);

}
