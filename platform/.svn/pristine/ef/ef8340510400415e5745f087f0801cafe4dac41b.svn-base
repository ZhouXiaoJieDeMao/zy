package com.bsoft.common.ws.model.basic;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 数据查询条件和返回数据的分页设置参数
 * @author zhangcb
 * @date 2019-08-09
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "requestset")
public class RequestSet {

    // 条件集合
    @XmlElement(name = "reqcondition")
    private Reqcondition reqcondition = new Reqcondition();
    // 是否进行分页处理 0（不分页），1（分页），默认值为0
    @XmlElement(name = "reqpaging")
    private Integer reqpaging = 0;
    // 返回分页数据时返回的页索引 应大于或等于-1，默认值为-1
    @XmlElement(name = "reqpageindex")
    private Integer reqpageIndex = -1;
    // 返回分页数据时页的数据行数 应大于或等于0，默认值为 0
    @XmlElement(name = "reqpageset")
    private Integer reqpageSet = 0;

    @Override
    public String toString() {
        return "RequestSet:{" +
                "" + reqcondition +
                ", reqpaging:" + reqpaging +
                ", reqpageIndex:" + reqpageIndex +
                ", reqpageSet:" + reqpageSet +
                '}';
    }

    public Reqcondition getReqcondition() {
        return reqcondition;
    }

    public void setReqcondition(Reqcondition reqcondition) {
        this.reqcondition = reqcondition;
    }

    public Integer getReqpaging() {
        return reqpaging;
    }

    public void setReqpaging(Integer reqpaging) {
        this.reqpaging = reqpaging;
    }

    public Integer getReqpageIndex() {
        return reqpageIndex;
    }

    public void setReqpageIndex(Integer reqpageIndex) {
        this.reqpageIndex = reqpageIndex;
    }

    public Integer getReqpageSet() {
        return reqpageSet;
    }

    public void setReqpageSet(Integer reqpageSet) {
        this.reqpageSet = reqpageSet;
    }
}
