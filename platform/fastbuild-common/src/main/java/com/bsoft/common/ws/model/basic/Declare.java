package com.bsoft.common.ws.model.basic;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 单数据库描述
 * @author zhangcb
 * @date 2019-08-12
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tdeclare")
public class Declare {

    // 数据集编码
    @XmlElement(name = "setcode")
    private String setCode;
    // 记录条数
    @XmlElement(name = "datanum")
    private Integer datanum = 0;

    @Override
    public String toString() {
        return "Declare:{" +
                "setCode:'" + setCode + '\'' +
                ", datanum:" + datanum +
                '}';
    }

    public String getSetCode() {
        return setCode;
    }

    public void setSetCode(String setCode) {
        this.setCode = setCode;
    }

    public Integer getDatanum() {
        return datanum;
    }

    public void setDatanum(Integer datanum) {
        this.datanum = datanum;
    }
}
