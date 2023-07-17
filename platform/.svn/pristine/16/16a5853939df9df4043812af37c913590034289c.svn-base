package com.bsoft.common.ws.model.request.reception;

import com.bsoft.common.ws.model.basic.ReqSwitchSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 归集上传结果
 * @author zhangcb
 * @date 2019-08-20
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "messages")
public class ReceptionResult {

    // 服务活性
    @XmlElement(name = "heartbeat")
    private Integer heartbeat = 0;
    // 交换集合
    @XmlElement(name = "switchset")
    private ReqSwitchSet reqSwitchSet = new ReqSwitchSet();
    // 业务数据
    @XmlElement(name = "business")
    private ReceptionBusiness receptionBusiness = new ReceptionBusiness();

    @Override
    public String toString() {
        return "ReceptionResult:{" +
                "heartbeat:'" + heartbeat + '\'' +
                ", " + reqSwitchSet +
                ", " + receptionBusiness +
                '}';
    }

    public Integer getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(Integer heartbeat) {
        this.heartbeat = heartbeat;
    }

    public ReqSwitchSet getReqSwitchSet() {
        return reqSwitchSet;
    }

    public void setReqSwitchSet(ReqSwitchSet reqSwitchSet) {
        this.reqSwitchSet = reqSwitchSet;
    }

    public ReceptionBusiness getReceptionBusiness() {
        return receptionBusiness;
    }

    public void setReceptionBusiness(ReceptionBusiness receptionBusiness) {
        this.receptionBusiness = receptionBusiness;
    }
}
