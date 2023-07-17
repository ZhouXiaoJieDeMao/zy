package com.bsoft.system.domain.yyh;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 医养护平台信息
 */
@TableName("YYH_CLIENT")
@Data//提供getter setter
public class YYH_CLIENT {
    @TableId("CLIENTID")
    private Long CLIENTID;//ID
    @TableField(value = "CLIENT_DATABASE_URL")
    private String CLIENT_DATABASE_URL;//客户端数据源地址 如值为main，表示当前数据库
    @TableField(value = "CLIENT_DATABASE_USERNAME")
    private String CLIENT_DATABASE_USERNAME;//客户端数据源用户名
    @TableField(value = "CLIENT_DATABASE_PASSWORD")
    private String CLIENT_DATABASE_PASSWORD;//客户端数据源密码
    @TableField(value = "CLIENT_REMARK")
    private String CLIENT_REMARK;//客户端描述
    @TableField(value = "SOURCEORGAN")
    private String SOURCEORGAN;//服务请求方的 22 位机构编码取卫计委提供的。没有机构编码的，非医疗机构，由平台统一分配。
    @TableField(value = "SOURCEDOMAIN")
	private String SOURCEDOMAIN;//服务请求方的 10 位接入系统编码。由平台统一分配。
    @TableField(value = "TARGETORGAN")
	private String TARGETORGAN;//22位卫计委机构编码
    @TableField(value = "TARGETDOMAIN")
	private String TARGETDOMAIN;//杭州医养护平台接入系统编码
    @TableField(value = "PCRWHHQW")
    private String PCRWHHQW;//批次号声明服务地址
    @TableField(value = "PCRWHHQW_SERVICECODE")
    private String PCRWHHQW_SERVICECODE;//批次号声明服务入参servicecode
    @TableField(value = "DCRWHW")
    private String DCRWHW;//单次任务号声明服务地址
    @TableField(value = "DCRWHW_SERVICECODE")
    private String DCRWHW_SERVICECODE;//单次任务号声明服务入参servicecode
    @TableField(value = "SJSCW")
    private String SJSCW;//上传服务地址
    @TableField(value = "SJSCW_SERVICECODE")
    private String SJSCW_SERVICECODE;//上传服务入参servicecode
}