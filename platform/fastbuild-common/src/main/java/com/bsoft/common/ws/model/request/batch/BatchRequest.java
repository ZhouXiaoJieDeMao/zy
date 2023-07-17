package com.bsoft.common.ws.model.request.batch;

import com.bsoft.common.ws.model.basic.ReqSwitchSet;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 批次声明请求报文
 * @author zhangcb
 * @date 2019-08-12
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "messages", namespace = "http://www.neusoft.com/hit/rhin")
public class BatchRequest {

    // 服务活性
    @XmlElement(name = "heartbeat")
    private String heartbeat;
    // 交换集合
    @XmlElement(name = "switchSet")
    private ReqSwitchSet batchSwitchSet = new ReqSwitchSet();
    // 业务数据
    @XmlElement(name = "business")
    private BatchReqBusiness batchBusiness = new BatchReqBusiness();

    @Override
    public String toString() {
        return "BatchRequest:{" +
                "heartbeat:'" + heartbeat + '\'' +
                ", " + batchSwitchSet +
                ", " + batchBusiness +
                '}';
    }

    public String getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(String heartbeat) {
        this.heartbeat = heartbeat;
    }

    public ReqSwitchSet getBatchSwitchSet() {
        return batchSwitchSet;
    }

    public void setBatchSwitchSet(ReqSwitchSet batchSwitchSet) {
        this.batchSwitchSet = batchSwitchSet;
    }

    public BatchReqBusiness getBatchBusiness() {
        return batchBusiness;
    }

    public void setBatchBusiness(BatchReqBusiness batchBusiness) {
        this.batchBusiness = batchBusiness;
    }
}
