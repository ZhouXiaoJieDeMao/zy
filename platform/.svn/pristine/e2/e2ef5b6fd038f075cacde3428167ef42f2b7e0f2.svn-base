package com.bsoft.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.bsoft.common.annotation.Excel;
import com.bsoft.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 drgs_sqlconfig
 * 
 * @author fastbuild
 * @date 2022-07-12
 */
public class DrgsSqlconfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** serviceId */
    @Excel(name = "serviceId")
    private String serviceid;

    /** sql类型 */
    @Excel(name = "sql类型")
    private String sqltype;

    /** sql */
    @Excel(name = "sql")
    private String sqlcontent;

    /** 参数 */
    @Excel(name = "参数")
    private String relationcolumn;

    /** 启用标志 */
    @Excel(name = "启用标志")
    private String sfqy;

    /** 数据源标志 */
    @Excel(name = "数据源标志")
    private String dbid;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setServiceid(String serviceid) 
    {
        this.serviceid = serviceid;
    }

    public String getServiceid() 
    {
        return serviceid;
    }
    public void setSqltype(String sqltype) 
    {
        this.sqltype = sqltype;
    }

    public String getSqltype() 
    {
        return sqltype;
    }
    public void setSqlcontent(String sqlcontent) 
    {
        this.sqlcontent = sqlcontent;
    }

    public String getSqlcontent() 
    {
        return sqlcontent;
    }
    public void setRelationcolumn(String relationcolumn) 
    {
        this.relationcolumn = relationcolumn;
    }

    public String getRelationcolumn() 
    {
        return relationcolumn;
    }
    public void setSfqy(String sfqy) 
    {
        this.sfqy = sfqy;
    }

    public String getSfqy() 
    {
        return sfqy;
    }
    public void setDbid(String dbid) 
    {
        this.dbid = dbid;
    }

    public String getDbid() 
    {
        return dbid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("serviceid", getServiceid())
            .append("sqltype", getSqltype())
            .append("sqlcontent", getSqlcontent())
            .append("relationcolumn", getRelationcolumn())
            .append("sfqy", getSfqy())
            .append("dbid", getDbid())
            .append("remark", getRemark())
            .toString();
    }
}
