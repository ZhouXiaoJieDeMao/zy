package com.bsoft.common.ws.model.basic;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 扩展集合（令牌）
 * @author zhangcb
 * @date 2019-08-09
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "extendset")
public class ExtendSet {

    // 令牌信息
    @XmlElement(name = "token")
    private String token;

    @Override
    public String toString() {
        return "ExtendSet:{" +
                "token:'" + token + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
