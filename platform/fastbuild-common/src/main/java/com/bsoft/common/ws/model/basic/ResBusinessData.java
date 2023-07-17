package com.bsoft.common.ws.model.basic;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 批次声明业务数据
 * @author zhangcb
 * @date 2019-08-12
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "businessdata")
public class ResBusinessData {

    // 任务号
    @XmlElement(name = "taskid")
    private String taskId;

    @Override
    public String
    toString() {
        return "ResBusinessData:{" +
                "taskId:'" + taskId + '\'' +
                '}';
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
