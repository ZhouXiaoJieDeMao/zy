package com.bsoft.common.ws.model.basic;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 返回业务相关消息
 * @author zhangcb
 * @date 2019-08-09
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "returnmessage")
public class ReturnMessage {

    // 业务相关消息的编码
    @XmlElement(name = "retcode")
    private String retCode;
    // 业务相关消息文本
    @XmlElement(name = "rettext")
    private String retText;

    @Override
    public String toString() {
        return "ReturnMessage:{" +
                "retCode:'" + retCode + '\'' +
                ", retText:'" + retText + '\'' +
                '}';
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetText() {
        return retText;
    }

    public void setRetText(String retText) {
        this.retText = retText;
    }
}
