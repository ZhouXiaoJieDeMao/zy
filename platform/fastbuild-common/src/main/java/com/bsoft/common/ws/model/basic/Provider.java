package com.bsoft.common.ws.model.basic;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 服务提供方
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "serviceinf")
public class Provider {

    // 服务提供方所在的机构编码
    @XmlElement(name = "targetorgan")
    private String targetOrgan;
    // 服务提供方所在的接入系统编码
    @XmlElement(name = "targetdomain")
    private String targetDomain;

    @Override
    public String toString() {
        return "Provider:{" +
                "targetorgan:'" + targetOrgan + '\'' +
                ", targetdomain:'" + targetDomain + '\'' +
                '}';
    }

    public String getTargetOrgan() {
        return targetOrgan;
    }

    public void setTargetOrgan(String targetOrgan) {
        this.targetOrgan = targetOrgan;
    }

    public String getTargetDomain() {
        return targetDomain;
    }

    public void setTargetDomain(String targetDomain) {
        this.targetDomain = targetDomain;
    }
}
