package com.bsoft.common.ws.model.request.task.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 单任务申请请求报文
 * @author zhangcb
 * @date 2019-08-13
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "businessdata")
public class TaskBusinessData {

    // 声明类型 0:总声明/1:单次声明
    @XmlElement(name = "declaretype")
    private Integer declareType = 0;
    // 单任务声明
    @XmlElement(name = "singledeclare")
    private SingleDeclare singleDeclare = new SingleDeclare();

    @Override
    public String toString() {
        return "TaskBusinessData:{" +
                "declareType:" + declareType +
                ", " + singleDeclare +
                '}';
    }

    public Integer getDeclareType() {
        return declareType;
    }

    public void setDeclareType(Integer declareType) {
        this.declareType = declareType;
    }

    public SingleDeclare getSingleDeclare() {
        return singleDeclare;
    }

    public void setSingleDeclare(SingleDeclare singleDeclare) {
        this.singleDeclare = singleDeclare;
    }
}
