package com.bsoft.common.ws.model.basic;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 返回的业务数据描述信息
 * @author zhangcb
 * @date 2019-08-09
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "returnset")
public class ReturnSet {

    // 业务数据总量信息
    @XmlElement(name = "rettotal")
    private Integer retTotal = 0;
    // 业务数据是否分页
    @XmlElement(name = "retpaging")
    private Integer retpaging = 0;
    // 业务数据的页索引
    @XmlElement(name = "retpageindex")
    private Integer retpageIndex = -1;
    // 业务数据的页容纳的数据行数
    @XmlElement(name = "retpageset")
    private Integer retpageSet = 0;

    @Override
    public String toString() {
        return "ReturnSet:{" +
                "retTotal:" + retTotal +
                ", retpaging:" + retpaging +
                ", retpageIndex:" + retpageIndex +
                ", retpageSet:" + retpageSet +
                '}';
    }

    public Integer getRetTotal() {
        return retTotal;
    }

    public void setRetTotal(Integer retTotal) {
        this.retTotal = retTotal;
    }

    public Integer getRetpaging() {
        return retpaging;
    }

    public void setRetpaging(Integer retpaging) {
        this.retpaging = retpaging;
    }

    public Integer getRetpageIndex() {
        return retpageIndex;
    }

    public void setRetpageIndex(Integer retpageIndex) {
        this.retpageIndex = retpageIndex;
    }

    public Integer getRetpageSet() {
        return retpageSet;
    }

    public void setRetpageSet(Integer retpageSet) {
        this.retpageSet = retpageSet;
    }
}
