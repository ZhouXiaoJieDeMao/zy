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
@XmlRootElement(name = "businessdata")
public class UploadData {

    @XmlElement(name = "dmp")
    private Dmp dmp = new Dmp();

    @Override
    public String toString() {
        return "UploadData:{" +
                "" + dmp +
                '}';
    }

    public Dmp getDmp() {
        return dmp;
    }

    public void setDmp(Dmp dmp) {
        this.dmp = dmp;
    }
}
