package com.bsoft.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.bsoft.common.annotation.Excel;
import com.bsoft.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 sys_dynamic_report
 * 
 * @author fastbuild
 * @date 2022-07-07
 */
public class SysDynamicReport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 执行sql */
    @Excel(name = "执行sql")
    private String operationSql;

    /** 执行参数 */
    @Excel(name = "执行参数")
    private String paramSql;

    /** 报表名称 */
    @Excel(name = "报表名称")
    private String operationName;

    /** 需要展示的列表 */
    @Excel(name = "需要展示的列表")
    private String operationList;

    /** 状态   1正常  99注销 */
    @Excel(name = "状态   1正常  99注销")
    private String status;

    /** 数据库类型
不填是默认主数据库
填写之后就是使用动态数据源 */
    @Excel(name = "数据库类型 不填是默认主数据库 填写之后就是使用动态数据源")
    private String databaseType;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOperationSql(String operationSql) 
    {
        this.operationSql = operationSql;
    }

    public String getOperationSql() 
    {
        return operationSql;
    }
    public void setParamSql(String paramSql) 
    {
        this.paramSql = paramSql;
    }

    public String getParamSql() 
    {
        return paramSql;
    }
    public void setOperationName(String operationName) 
    {
        this.operationName = operationName;
    }

    public String getOperationName() 
    {
        return operationName;
    }
    public void setOperationList(String operationList) 
    {
        this.operationList = operationList;
    }

    public String getOperationList() 
    {
        return operationList;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setDatabaseType(String databaseType) 
    {
        this.databaseType = databaseType;
    }

    public String getDatabaseType() 
    {
        return databaseType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("operationSql", getOperationSql())
            .append("paramSql", getParamSql())
            .append("remark", getRemark())
            .append("operationName", getOperationName())
            .append("operationList", getOperationList())
            .append("status", getStatus())
            .append("databaseType", getDatabaseType())
            .toString();
    }
}
