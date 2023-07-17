package com.bsoft.common.ws.model.response.reception;

import com.bsoft.common.ws.model.basic.ReturnMessage;
import com.bsoft.common.ws.model.basic.ReturnSet;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 采集反馈业务数据
 * @author zhangcb
 * @date 2019-09-05
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "business")
public class ReceptionBusiness {

    // 交换标准编码
    @XmlElement(name = "standardcode")
    private String standardCode;
    // 返回信息
    @XmlElement(name = "returnmessage")
    private ReturnMessage returnMessage = new ReturnMessage();
    // 返回集合
    @XmlElement(name = "returnset")
    private ReturnSet returnSet = new ReturnSet();
    // 业务数据是否进行压缩 0：不压缩，1:压缩
    @XmlElement(name = "datacompress")
    private String dataCompress;
    // 主业务数据
    @XmlElement(name = "businessdata")
    private String businessData;

    @Override
    public String toString() {
        return "ReceptionBusiness{" +
                "standardCode:'" + standardCode + '\'' +
                ", returnMessage=" + returnMessage +
                ", returnSet=" + returnSet +
                ", dataCompress:'" + dataCompress + '\'' +
                ", businessData:'" + businessData + '\'' +
                '}';
    }

    public String getStandardCode() {
        return standardCode;
    }

    public void setStandardCode(String standardCode) {
        this.standardCode = standardCode;
    }

    public ReturnMessage getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(ReturnMessage returnMessage) {
        this.returnMessage = returnMessage;
    }

    public ReturnSet getReturnSet() {
        return returnSet;
    }

    public void setReturnSet(ReturnSet returnSet) {
        this.returnSet = returnSet;
    }

    public String getDataCompress() {
        return dataCompress;
    }

    public void setDataCompress(String dataCompress) {
        this.dataCompress = dataCompress;
    }

    public String getBusinessData() {
        return businessData;
    }

    public void setBusinessData(String businessData) {
        this.businessData = businessData;
    }
}
