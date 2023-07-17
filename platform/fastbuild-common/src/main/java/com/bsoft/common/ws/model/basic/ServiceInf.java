package com.bsoft.common.ws.model.basic;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 服务信息
 * @author zhangcb
 * @date 2019-08-09
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "serviceinf")
public class ServiceInf {

    // hsb 发布服务标识
    @XmlElement(name = "servicecode")
    private String serviceCode;

    @Override
    public String toString() {
        return "ServiceInf:{" +
                "servicecode:'" + serviceCode + '\'' +
                '}';
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
}
