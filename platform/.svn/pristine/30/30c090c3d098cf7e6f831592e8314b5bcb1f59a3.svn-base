package com.bsoft.common.ws.model.request.upload;


import com.bsoft.common.ws.model.basic.ReqSwitchSet;

import javax.xml.bind.annotation.*;

/**
 * 归集上传请求报文
 * @author zhangcb
 * @date 2019-08-13
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "messages", namespace = "http://www.neusoft.com/hit/rhin")
public class UploadRequest {

    // 服务活性
    @XmlElement(name = "heartbeat")
    private String heartbeat;
    // 交换集合
    @XmlElement(name = "switchSet")
    private ReqSwitchSet batchSwitchSet = new ReqSwitchSet();
    // 业务数据
    @XmlElement(name = "business")
    private UploadReqBusiness business = new UploadReqBusiness();
    @XmlTransient
    private String data;

    @Override
    public String toString() {
        return "UploadRequest:{" +
                "heartbeat:'" + heartbeat + '\'' +
                ", batchSwitchSet:" + batchSwitchSet +
                ", business:" + business +
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

    public UploadReqBusiness getBusiness() {
        return business;
    }

    public void setBusiness(UploadReqBusiness business) {
        this.business = business;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
