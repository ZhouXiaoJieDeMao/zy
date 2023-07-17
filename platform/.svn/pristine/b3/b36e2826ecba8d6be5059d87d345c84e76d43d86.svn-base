package com.bsoft.system.mapper;

import com.bsoft.system.domain.yyh.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author fastbuild
 * @date 2022-08-11
 */
public interface YyhClientMapper extends RootMapper<YYH_CLIENT>
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
     * 删除【请填写功能名称】
     *
     * @param clientid 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteYyhClientByClientid(Long clientid);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param clientids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteYyhClientByClientids(Long[] clientids);

    List<Map<String,Object>> dynamicSqlList(@Param("map")Map<String, Object> map);

    List<TjPDFVo> selectTJPDF(@Param("map")Map<String, Object> map);

    Map<String,Object> getYYHBaseInfo(@Param("laqtjpdf") String laqtjpdf);

    int updateTjbgResult(@Param("map") Map<String, Object> updateMap);

    List<Map<String,Object>> selectTjjlList(@Param("map")Map<String, Object> map);

    YyhDataConfig getYyhUpconfig(@Param("map")Map<String, Object> map);

    List<Map<String,Object>> selectYyhUploadList(@Param("yyhUpsql")String yyhUpsql,@Param("map")Map<String,Object> params);

    int updateUpdata(@Param("yyhUpsql")String update_sql);

    void saveErrorUpLog(@Param("setcode")String setcode,@Param("upnum")int size, @Param("errnum")int li_up_ecount);

    void saveRecordLog(@Param("record") YYH_AGGR_RECORD record);

    List<Map<String,Object>> selectListBySql(@Param("sql")String sql);


}
