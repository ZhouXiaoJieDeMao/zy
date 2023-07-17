package com.bsoft.system.service.impl;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bsoft.common.annotation.DataSource;
import com.bsoft.common.enums.DataSourceType;
import com.bsoft.system.domain.yyh.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bsoft.system.mapper.YyhClientMapper;
import com.bsoft.system.service.IYyhClientService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author fastbuild
 * @date 2022-08-11
 */
@Service
public class YyhClientServiceImpl extends ServiceImpl<YyhClientMapper, YYH_CLIENT> implements IYyhClientService
{
    @Autowired
    private YyhClientMapper yyhClientMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param clientid 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public YyhClient selectYyhClientByClientid(Long clientid)
    {
        return yyhClientMapper.selectYyhClientByClientid(clientid);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param yyhClient 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<YyhClient> selectYyhClientList(YyhClient yyhClient)
    {
        return yyhClientMapper.selectYyhClientList(yyhClient);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param yyhClient 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertYyhClient(YyhClient yyhClient)
    {
        return yyhClientMapper.insertYyhClient(yyhClient);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param yyhClient 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateYyhClient(YyhClient yyhClient)
    {
        return yyhClientMapper.updateYyhClient(yyhClient);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param clientids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteYyhClientByClientids(Long[] clientids)
    {
        return yyhClientMapper.deleteYyhClientByClientids(clientids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param clientid 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteYyhClientByClientid(Long clientid)
    {
        return yyhClientMapper.deleteYyhClientByClientid(clientid);
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<Map<String, Object>> dynamicSqlList(Map<String, Object> map, String dbid) {
        return yyhClientMapper.dynamicSqlList(map);
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<TjPDFVo> selectTJPDF(Map<String, Object> map) {
        return yyhClientMapper.selectTJPDF(map);
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public Map<String, Object> getYYHBaseInfo(String laqtjpdf) {
        return yyhClientMapper.getYYHBaseInfo(laqtjpdf);
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public int updateTjbgResult(Map<String, Object> updateMap) {
        return yyhClientMapper.updateTjbgResult(updateMap);
    }

    @Override
    @DataSource(value = DataSourceType.THREE)
    public List<Map<String, Object>> selectTjjlList(Map<String, Object> map, String dbid) {
//        if (map != null & map.get("brxm")!= null & map.get("brxm")!= "") {
//
//            String map_v = (String) map.get("brxm");
//            map_v = "'%"+map_v+"%'";
//            map.put("brxm",map_v);
//
//        }

        return yyhClientMapper.selectTjjlList(map);
    }

    @Override
    @DataSource(value = DataSourceType.FOUR)
    public YyhDataConfig getYyhUpconfig(Map<String,Object> map) {
        return yyhClientMapper.getYyhUpconfig(map);
    }

    @Override
    @DataSource(value = DataSourceType.THREE)
    public List<Map<String, Object>> selectYyhUploadList(String yyhUpsql,Map<String,Object> params) {
        return yyhClientMapper.selectYyhUploadList(yyhUpsql,params);
    }

    @Override
    @DataSource(value = DataSourceType.THREE)
    public int updateUpdata(String update_sql) {
        return yyhClientMapper.updateUpdata(update_sql);
    }

    @Override
    @DataSource(value = DataSourceType.FOUR)
    public void saveErrorUpLog(String setcode, int size, int li_up_ecount) {
        yyhClientMapper.saveErrorUpLog(setcode,size,li_up_ecount);
    }

    @Override
    @DataSource(value = DataSourceType.FOUR)
    public void saveRecordLog(YYH_AGGR_RECORD record) {
        yyhClientMapper.saveRecordLog(record);
    }

    @Override
    @DataSource(value = DataSourceType.THREE)
    public List<Map<String, Object>> selectGlUploadList(YyhDataConfig config, Map<String, String[]> map) {
        StringBuffer sql = new StringBuffer(config.getSqlcontent());
        for(String key : map.keySet()){
            sql.append(" and "+ key +" in (");
            String [] strings = map.get(key);
            StringBuffer sb = new StringBuffer("");
            for(String str : strings){
                sb.append("'" +str+"',");
            }
            sql.append(sb.toString().substring(0,sb.length()-1)+")");
        }
        return yyhClientMapper.selectListBySql(sql.toString());
    }


}
