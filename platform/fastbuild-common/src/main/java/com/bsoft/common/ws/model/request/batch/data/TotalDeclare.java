package com.bsoft.common.ws.model.request.batch.data;

import com.bsoft.common.ws.model.basic.Declare;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 整体描述
 * @author zhangcb
 * @date 2019-08-12
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "totaldeclare")
public class TotalDeclare {

    // 交换标准编码
    @XmlElement(name = "colrescode")
    private String colreScode;
    // 任务数
    @XmlElement(name = "tasknum")
    private Integer taskNum = 0;
    // 开始时间
    @XmlElement(name = "begindatetime")
    private String beginDateTime;
    // 结束时间
    @XmlElement(name = "enddatetime")
    private String endDateTime;
    // 数据集描述
    @XmlElement(name = "tdeclare")
    private Declare declare = new Declare();

    @Override
    public String toString() {
        return "TotalDeclare:{" +
                "colreScode:'" + colreScode + '\'' +
                ", taskNum:'" + taskNum + '\'' +
                ", beginDateTime:'" + beginDateTime + '\'' +
                ", endDateTime:'" + endDateTime + '\'' +
                ", [" + declare + "]" +
                '}';
    }

    public String getColreScode() {
        return colreScode;
    }

    public void setColreScode(String colreScode) {
        this.colreScode = colreScode;
    }

    public Integer getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(Integer taskNum) {
        this.taskNum = taskNum;
    }

    public String getBeginDateTime() {
        return beginDateTime;
    }

    public void setBeginDateTime(String beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Declare getDeclare() {
        return declare;
    }

    public void setDeclare(Declare declare) {
        this.declare = declare;
    }
}
