package com.bsoft.common.ws.model.response.reception;

import com.bsoft.common.ws.model.basic.ExtendSet;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 采集反馈
 * @author zhangcb
 * @date 2019-09-05
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "messages")
public class ReceptionResponse {

    // 采集交换数据集
    @XmlElement(name = "switchset")
    private ReceptionSwitch receptionSwitch = new ReceptionSwitch();
    // 业务数据
    @XmlElement(name = "business")
    private ReceptionBusiness receptionBusiness = new ReceptionBusiness();
    // 令牌
    @XmlElement(name = "extendset")
    private ExtendSet extendSet = new ExtendSet();

    @Override
    public String toString() {
        return "ReceptionResponse{" +
                "receptionSwitch=" + receptionSwitch +
                ", receptionBusiness=" + receptionBusiness +
                ", extendSet=" + extendSet +
                '}';
    }

    public ReceptionSwitch getReceptionSwitch() {
        return receptionSwitch;
    }

    public void setReceptionSwitch(ReceptionSwitch receptionSwitch) {
        this.receptionSwitch = receptionSwitch;
    }

    public ReceptionBusiness getReceptionBusiness() {
        return receptionBusiness;
    }

    public void setReceptionBusiness(ReceptionBusiness receptionBusiness) {
        this.receptionBusiness = receptionBusiness;
    }

    public ExtendSet getExtendSet() {
        return extendSet;
    }

    public void setExtendSet(ExtendSet extendSet) {
        this.extendSet = extendSet;
    }
}
