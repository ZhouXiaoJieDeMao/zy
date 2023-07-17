package com.bsoft.common.ws.model.basic;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 批次声明交换数据
 * @author zhangcb
 * @date 2019-08-08
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "switchset")
public class ReqSwitchSet {

    // 消息交换定义
    @XmlElement(name = "authority")
    private Authority authority = new Authority();
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

    
    public String toString() {
        return "SwitchSet:{" +
                "" + authority +
                ", " + visitor +
                ", " + serviceInf +
                ", " + provider +
                ", route:'" + route + '\'' +
                ", process:'" + process + '\'' +
                '}';
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

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
}
