package com.bsoft.common.ws.model.response.reception;

import com.bsoft.common.ws.model.basic.Provider;
import com.bsoft.common.ws.model.basic.ServiceInf;
import com.bsoft.common.ws.model.basic.Visitor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 采集交换数据集
 * @author zhangcb
 * @date 2019-09-05
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "switchset")
public class ReceptionSwitch {

    // 服务消费方
    @XmlElement(name = "visitor")
    private Visitor visitor = new Visitor();
    // 服务信息
    @XmlElement(name = "serviceinf")
    private ServiceInf serviceInf = new ServiceInf();
    // 服务提供方
    @XmlElement(name = "provider")
    private Provider provider = new Provider();
    // 服务路由
    @XmlElement(name = "route")
    private String route;
    // 服务流程编排
    @XmlElement(name = "process")
    private String process;
    // 交换信息
    @XmlElement(name = "switchmessage")
    private String switchMessage;

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public ServiceInf getServiceInf() {
        return serviceInf;
    }

    public void setServiceInf(ServiceInf serviceInf) {
        this.serviceInf = serviceInf;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getSwitchMessage() {
        return switchMessage;
    }

    public void setSwitchMessage(String switchMessage) {
        this.switchMessage = switchMessage;
    }

    @Override
    public String toString() {
        return "ReceptionSwitch{" +
                "visitor=" + visitor +
                ", serviceInf=" + serviceInf +
                ", provider=" + provider +
                ", route:'" + route + '\'' +
                ", process:'" + process + '\'' +
                ", switchMessage:'" + switchMessage + '\'' +
                '}';
    }
}
