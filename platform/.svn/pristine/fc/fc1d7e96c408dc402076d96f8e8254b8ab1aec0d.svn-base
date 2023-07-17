package com.bsoft.common.ws.model.request.upload.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 包裹层
 * @author zhangcb
 * @date 2019-08-14
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "dmp")
public class Dmp {

    @XmlElement(name = "datasets")
    private DataSets dataSets = new DataSets();

    @Override
    public String toString() {
        return "Dmp:{" +
                "" + dataSets +
                '}';
    }

    public DataSets getDataSets() {
        return dataSets;
    }

    public void setDataSets(DataSets dataSets) {
        this.dataSets = dataSets;
    }
}
