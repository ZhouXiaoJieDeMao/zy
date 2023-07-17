package com.bsoft.common.ws.model.request.reception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 处理结果
 * @author zhangcb
 * @date 2019-08-20
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "businessdata")
public class ReceptionBusinessData {

    // 返回结果集
    @XmlElement(name = "result")
    private Result result = new Result();

    @Override
    public String toString() {
        return "ReceptionBusinessData:{" +
                "" + result +
                '}';
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
