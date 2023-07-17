package com.bsoft.common.ws.model.request.reception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 数据集检验信息集合
 * @author zhangcb
 * @date 2019-08-20
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tableinfo")
public class TableInfo {

    // 数据集所对应的表名称
    @XmlElement(name = "tablename")
    private String tableName;
    // 数据集所对应的表名称描述
    @XmlElement(name = "tabledesc")
    private String tableDesc;
    // 数据集编码对应
    @XmlElement(name = "setcode")
    private String setCode;
    // 数据集编码对应的名称
    @XmlElement(name = "setname")
    private String setName;
    // 总数据量，
    @XmlElement(name = "recordcount")
    private Integer recordCount = 0;
    // 错误数据量
    @XmlElement(name = "errorrecordcount")
    private Integer errorRecordCount = 0;
    // 规则数
    @XmlElement(name = "rulecount")
    private Integer ruleCount = 0;
    // 错误数
    @XmlElement(name = "errorcount")
    private Integer errorCount = 0;

    @Override
    public String toString() {
        return "TableInfo:{" +
                "tableName:'" + tableName + '\'' +
                ", tableDesc:'" + tableDesc + '\'' +
                ", setCode:'" + setCode + '\'' +
                ", setName:'" + setName + '\'' +
                ", recordCount:" + recordCount +
                ", errorRecordCount:" + errorRecordCount +
                ", ruleCount:" + ruleCount +
                ", errorCount:" + errorCount +
                '}';
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public String getSetCode() {
        return setCode;
    }

    public void setSetCode(String setCode) {
        this.setCode = setCode;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }

    public Integer getErrorRecordCount() {
        return errorRecordCount;
    }

    public void setErrorRecordCount(Integer errorRecordCount) {
        this.errorRecordCount = errorRecordCount;
    }

    public Integer getRuleCount() {
        return ruleCount;
    }

    public void setRuleCount(Integer ruleCount) {
        this.ruleCount = ruleCount;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }
}
