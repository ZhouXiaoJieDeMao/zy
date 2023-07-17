package com.bsoft.common.ws.model.response;

import com.bsoft.common.ws.model.basic.ExtendSet;
import com.bsoft.common.ws.model.basic.ResBusiness;
import com.bsoft.common.ws.model.basic.ResSwitchSet;
import javax.xml.bind.annotation.*;

/**
 * 采集反馈回调
 * @author zhangcb
 * @date 2019-08-12
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "messages")
public class WsResponse {

    @XmlTransient private String xml;

    // 交换数据集
    @XmlElement(name = "switchset")
    private ResSwitchSet switchSet = new ResSwitchSet();
    // 声明业务数据
    @XmlElement(name = "business")
    private ResBusiness business =  new ResBusiness();
    // 令牌
    @XmlElement(name = "extendset")
    private ExtendSet extendSet = new ExtendSet();

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    @Override
    public String toString() {
        return "WsResponse:{" +
                "" + switchSet +
                ", " + business +
                ", " + extendSet +
                '}';
    }

    public ResSwitchSet getSwitchSet() {
        return switchSet;
    }

    public void setSwitchSet(ResSwitchSet switchSet) {
        this.switchSet = switchSet;
    }

    public ResBusiness getBusiness() {
        return business;
    }

    public void setBusiness(ResBusiness business) {
        this.business = business;
    }

    public ExtendSet getExtendSet() {
        return extendSet;
    }

    public void setExtendSet(ExtendSet extendSet) {
        this.extendSet = extendSet;
    }
}
