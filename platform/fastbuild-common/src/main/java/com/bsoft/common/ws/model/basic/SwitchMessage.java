package com.bsoft.common.ws.model.basic;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 交换信息
 * @author zhangcb
 * @date 2019-08-13
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "switchmessage")
public class SwitchMessage {

    // 交换信息编码
    @XmlElement(name = "messagecode")
    private String messageCode;
    // 交换信息文本
    @XmlElement(name = "messagetext")
    private String messageText;

    @Override
    public String toString() {
        return "SwitchMessage:{" +
                "messageCode:'" + messageCode + '\'' +
                ", messageText:'" + messageText + '\'' +
                '}';
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
