package com.bsoft.common.ws.model.basic;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 消息交换定义
 * @author zhangcb
 * @date 2019-08-08
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "authority")
public class  Authority {

    // 安全配置类型
    // 0（无安全配置），1（使用用户名/密码），2（使用授权码），3（使用数字证书），9（其他安全配置），默认值为 0
    @XmlElement(name = "authoritytype")
    private Integer authorityType = 0;
    // 服务提供方要求的用户名
    @XmlElement(name = "username")
    private String username;
    // 服务提供方要求的与用户名对应的密码
    @XmlElement(name = "userpwd")
    private String userpwd;
    // 服务提供方要求的授权码
    @XmlElement(name = "license")
    private String license;

    @Override
    public String toString() {
        return "Authority:{" +
                "authorityType:'" + authorityType + '\'' +
                ", username:'" + username + '\'' +
                ", userpwd:'" + userpwd + '\'' +
                ", license:'" + license + '\'' +
                '}';
    }

    public Integer getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(Integer authorityType) {
        this.authorityType = authorityType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }
}
