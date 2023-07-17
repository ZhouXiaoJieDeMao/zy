package com.bsoft.common.ws.model.request.upload.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 数据集
 * @author zhangcb
 * @date 2019-08-14
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "datasets")
public class DataSets {

    @XmlElement(name = "setcode")
    private String setCode;
    @XmlElement(name = "setdetails")
    private String setDetails;

    @Override
    public String toString() {
        return "DataSets:{" +
                "setCode:'" + setCode + '\'' +
                ", setDetails:" + setDetails +
                '}';
    }

    public String getSetCode() {
        return setCode;
    }

    public void setSetCode(String setCode) {
        this.setCode = setCode;
    }

    public String getSetDetails() {
        return setDetails;
    }

    public void setSetDetails(String setDetails) {
        this.setDetails = setDetails;
    }
}
