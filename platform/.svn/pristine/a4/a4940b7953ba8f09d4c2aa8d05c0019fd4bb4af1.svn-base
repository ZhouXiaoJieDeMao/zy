package com.bsoft.common.ws.model.request.reception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 查询条件
 * @author zhangcb
 * @date 2019-08-20
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "requestset")
public class ReceptionRequestSet {

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
        return "ReceptionRequestSet:{" +
                "reqpaging:" + reqpaging +
                ", reqpageIndex:" + reqpageIndex +
                ", reqpageset:" + reqpageSet +
                '}';
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
