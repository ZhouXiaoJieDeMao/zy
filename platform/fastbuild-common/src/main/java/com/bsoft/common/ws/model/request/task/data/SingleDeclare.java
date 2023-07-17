package com.bsoft.common.ws.model.request.task.data;

import com.bsoft.common.ws.model.basic.Declare;
import javax.xml.bind.annotation.*;

/**
 * 单任务声明
 * @author zhangcb
 * @date 2019-08-13
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "singledeclare")
public class SingleDeclare {

    @XmlTransient
    private String data;
    // 总声明任务号
    @XmlElement(name = "totaltaskid")
    private String totalTaskId;
    // 交换标准编码
    @XmlElement(name = "colrescode")
    private String colresCode;
    // 序号：本单次声明在总声明规划的任务数中是第几次，从1开始
    @XmlElement(name = "sn")
    private Integer sn = 1;
    // 任务声明
    @XmlElement(name = "declare")
    private Declare declare = new Declare();

    @Override
    public String toString() {
        return "SingleDeclare:{" +
                "totalTaskId:'" + totalTaskId + '\'' +
                ", colresCode:'" + colresCode + '\'' +
                ", sn:" + sn +
                ", " + declare +
                '}';
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTotalTaskId() {
        return totalTaskId;
    }

    public void setTotalTaskId(String totalTaskId) {
        this.totalTaskId = totalTaskId;
    }

    public String getColresCode() {
        return colresCode;
    }

    public void setColresCode(String colresCode) {
        this.colresCode = colresCode;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public Declare getDeclare() {
        return declare;
    }

    public void setDeclare(Declare declare) {
        this.declare = declare;
    }
}
