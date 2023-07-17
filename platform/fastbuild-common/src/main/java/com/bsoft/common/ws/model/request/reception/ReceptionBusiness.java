package com.bsoft.common.ws.model.request.reception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 业务数据
 * @author zhangcb
 * @date 2019-08-20
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "business")
public class ReceptionBusiness {

    // 交换标准编码
    @XmlElement(name = "standardcode")
    private String standardCode;
    // 数据查询条件和返回数据的分页设置参数
    @XmlElement(name = "requestset")
    private ReceptionRequestSet receptionRequestSet = new ReceptionRequestSet();
    // 业务数据是否进行压缩 0：不压缩，1:压缩
    @XmlElement(name = "datacompress")
    private Integer dataCompress  = 0;
    // 采集任务的标识号
    @XmlElement(name = "daqtaskid")
    private String daqTaskId;
    // 处理结果
    @XmlElement(name = "businessdata")
    private ReceptionBusinessData receptionBusinessData = new ReceptionBusinessData();

    @Override
    public String toString() {
        return "ReceptionBusiness:{" +
                "standardCode:'" + standardCode + '\'' +
                ", " + receptionRequestSet +
                ", dataCompress:" + dataCompress +
                ", daqTaskId:'" + daqTaskId + '\'' +
                ", " + receptionBusinessData +
                '}';
    }

    public String getStandardCode() {
        return standardCode;
    }

    public void setStandardCode(String standardCode) {
        this.standardCode = standardCode;
    }

    public ReceptionRequestSet getReceptionRequestSet() {
        return receptionRequestSet;
    }

    public void setReceptionRequestSet(ReceptionRequestSet receptionRequestSet) {
        this.receptionRequestSet = receptionRequestSet;
    }

    public Integer getDataCompress() {
        return dataCompress;
    }

    public void setDataCompress(Integer dataCompress) {
        this.dataCompress = dataCompress;
    }

    public String getDaqTaskId() {
        return daqTaskId;
    }

    public void setDaqTaskId(String daqTaskId) {
        this.daqTaskId = daqTaskId;
    }

    public ReceptionBusinessData getReceptionBusinessData() {
        return receptionBusinessData;
    }

    public void setReceptionBusinessData(ReceptionBusinessData receptionBusinessData) {
        this.receptionBusinessData = receptionBusinessData;
    }
}
