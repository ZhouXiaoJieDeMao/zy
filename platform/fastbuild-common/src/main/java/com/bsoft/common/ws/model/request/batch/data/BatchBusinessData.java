package com.bsoft.common.ws.model.request.batch.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 批次申请请求报文
 * @author zhangcb
 * @date 2019-08-12
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "businessdata")
public class BatchBusinessData {

    // 声明类型 0:总声明/1:单次声明
    @XmlElement(name = "declaretype")
    private Integer declareType = 0;
    // 采集类型 0:增量采集
    @XmlElement(name = "collecttype")
    private Integer collectType = 0;
    // 整体描述
    @XmlElement(name = "totaldeclare")
    private TotalDeclare[] totalDeclare = new TotalDeclare[]{ new TotalDeclare() };

    @Override
    public String toString() {
        return "BatchData:{" +
                "declareType:" + declareType +
                ", collectType:" + collectType +
                ", " + totalDeclare +
                '}';
    }

    public Integer getDeclareType() {
        return declareType;
    }

    public void setDeclareType(Integer declareType) {
        this.declareType = declareType;
    }

    public Integer getCollectType() {
        return collectType;
    }

    public void setCollectType(Integer collectType) {
        this.collectType = collectType;
    }

    public TotalDeclare[] getTotalDeclare() {
        return totalDeclare;
    }

    public void setTotalDeclare(TotalDeclare[] totalDeclare) {
        this.totalDeclare = totalDeclare;
    }
}
