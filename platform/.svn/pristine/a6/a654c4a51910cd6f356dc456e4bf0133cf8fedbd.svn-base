package com.bsoft.common.ws.model.request.batch;

import com.bsoft.common.ws.model.basic.RequestSet;
import com.bsoft.common.ws.model.request.batch.data.BatchBusinessData;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 批次声明业务数据
 * @author zhangcb
 * @date 2019-08-09
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "business")
public class BatchReqBusiness {

    // 交换标准编码
    @XmlElement(name = "standardcode")
    private String standardCode;
    // 数据查询条件和返回数据的分页设置参数
    @XmlElement(name = "requestset")
    private RequestSet requestSet = new RequestSet();
    // 业务数据是否进行压缩 0：不压缩，1:压缩
    @XmlElement(name = "datacompress")
    private Integer dataCompress = 0;
    // 采集任务的标识号
    @XmlElement(name = "daqtaskid")
    private String daqTaskId;
    // 主业务数据
    @XmlElement(name = "businessdata")
    private BatchBusinessData batchData = new BatchBusinessData();

    @Override
    public String toString() {
        return "Business:{" +
                "standardCode:'" + standardCode + '\'' +
                ", " + requestSet +
                ", dataCompress:" + dataCompress +
                ", daqTaskId:'" + daqTaskId + '\'' +
                ", businessData:'" + batchData + '\'' +
                '}';
    }

    public String getStandardCode() {
        return standardCode;
    }

    public void setStandardCode(String standardCode) {
        this.standardCode = standardCode;
    }

    public RequestSet getRequestSet() {
        return requestSet;
    }

    public void setRequestSet(RequestSet requestSet) {
        this.requestSet = requestSet;
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

    public BatchBusinessData getBatchData() {
        return batchData;
    }

    public void setBatchData(BatchBusinessData batchData) {
        this.batchData = batchData;
    }
}
