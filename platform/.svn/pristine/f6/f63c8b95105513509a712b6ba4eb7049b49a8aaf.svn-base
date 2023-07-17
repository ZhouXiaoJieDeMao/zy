package com.bsoft.common.ws.model.request.reception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

/**
 * 数据集校验结果
 * @author zhangcb
 * @date 2019-08-20
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tables")
public class Tables {

    // 数据集检验信息集合
    @XmlElement(name = "tableinfo")
    private TableInfo[] tableInfo = new TableInfo[]{ new TableInfo() };

    @Override
    public String toString() {
        return "Tables: " +
                "" + Arrays.toString(tableInfo);
    }

    public TableInfo[] getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo[] tableInfo) {
        this.tableInfo = tableInfo;
    }
}
