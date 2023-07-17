package com.bsoft.common.ws.model.request.task;

import com.bsoft.common.ws.model.basic.ReqSwitchSet;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 单任务声明请求报文
 * @author zhangcb
 * @date 2019-08-12
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "messages", namespace = "http://www.neusoft.com/hit/rhin")
public class TaskRequest {

    // 服务活性
    @XmlElement(name = "heartbeat")
    private String heartbeat;
    // 交换集合
    @XmlElement(name = "switchSet")
    private ReqSwitchSet batchSwitchSet = new ReqSwitchSet();
    // 业务数据
    @XmlElement(name = "business")
    private TaskReqBusiness taskReqBusiness = new TaskReqBusiness();

    @Override
    public String toString() {
        return "TaskRequest:{" +
                "heartbeat:'" + heartbeat + '\'' +
                ", " + batchSwitchSet +
                ", " + taskReqBusiness +
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

    public TaskReqBusiness getTaskReqBusiness() {
        return taskReqBusiness;
    }

    public void setTaskReqBusiness(TaskReqBusiness taskReqBusiness) {
        this.taskReqBusiness = taskReqBusiness;
    }
}
