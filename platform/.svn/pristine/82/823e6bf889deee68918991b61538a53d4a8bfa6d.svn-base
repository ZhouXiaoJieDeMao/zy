package com.bsoft.common.ws.model.basic;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 服务消费方
 * @author zhangcb
 * @date 2019-08-08
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "visitor")
public class Visitor {

    // 服务消费方所在机构编码
    @XmlElement(name = "sourceorgan")
    private String sourceOrgan;
    // 服务消费方所使用的接入系统编码
    @XmlElement(name = "sourcedomain")
    private String sourceDomain;

    public String getSourceOrgan() {
        return sourceOrgan;
    }

    @Override
    public String toString() {
        return "Visitor:{" +
                "sourceOrgan:'" + sourceOrgan + '\'' +
                ", sourceDomain:'" + sourceDomain + '\'' +
                '}';
    }

    public void setSourceOrgan(String sourceOrgan) {
        this.sourceOrgan = sourceOrgan;
    }

    public String getSourceDomain() {
        return sourceDomain;
    }

    public void setSourceDomain(String sourceDomain) {
        this.sourceDomain = sourceDomain;
    }
}
