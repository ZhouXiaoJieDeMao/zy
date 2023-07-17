package com.bsoft.common.ws.model.request.reception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 返回结果集
 * @author zhangcb
 * @date 2019-08-09
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "result")
public class Result {

    // 任务编号
    @XmlElement(name = "taskid")
    private String taskId;
    // 接入系统编码
    @XmlElement(name = "domaincode")
    private String domainCode;
    // 接入系统编码对应的名称
    @XmlElement(name = "domainname")
    private String domainName;
    // 交换标准编码,与数据上传时填写的standardcode相同
    @XmlElement(name = "resourcecode")
    private String resourceCode;
    // 交换标准编码对应的名称
    @XmlElement(name = "resourcename")
    private String resourceName;
    // 上传时间
    @XmlElement(name = "uploadtime")
    private String uploadTime;
    // 结果编码
    @XmlElement(name = "resultcode")
    private Integer resultCode = 0;
    // 结果描述
    @XmlElement(name = "resultdesc")
    private String resultdDesc;
    // 数据集检验信息集合
    @XmlElement(name = "tables")
    private Tables tables = new Tables();
    // 数据校验报告
    @XmlElement(name = "report")
    private String report;

    @Override
    public String toString() {
        return "Result:{" +
                "taskId:'" + taskId + '\'' +
                ", domainCode:'" + domainCode + '\'' +
                ", domainName:'" + domainName + '\'' +
                ", resourceCode:'" + resourceCode + '\'' +
                ", resourceName:'" + resourceName + '\'' +
                ", uploadTime:'" + uploadTime + '\'' +
                ", resultCode:" + resultCode +
                ", resultdDesc:'" + resultdDesc + '\'' +
                ", tables:" + tables +
                ", report:'" + report + '\'' +
                '}';
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDomainCode() {
        return domainCode;
    }

    public void setDomainCode(String domainCode) {
        this.domainCode = domainCode;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultdDesc() {
        return resultdDesc;
    }

    public void setResultdDesc(String resultdDesc) {
        this.resultdDesc = resultdDesc;
    }

    public Tables getTables() {
        return tables;
    }

    public void setTables(Tables tables) {
        this.tables = tables;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
