package com.bsoft.system.domain.yyh;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 医养护结果接收表
 */
@TableName(value = "YYH_AGGR_RESULT")
@Data//提供getter setter
public class YYH_AGGR_RESULT {
	@TableId("ENDTASKID")
	private String ENDTASKID;
	@TableField(value = "DOMAINCODE")
	private String DOMAINCODE;
	@TableField(value = "DOMAINNAME")
	private String DOMAINNAME;
	@TableField(value = "RESOURCECODE")
	private String RESOURCECODE;
	@TableField(value = "RESOURCENAME")
	private String RESOURCENAME;
	@JsonFormat( pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@TableField(value = "UPLOADTIME")
	private Date UPLOADTIME;
	@TableField(value = "RESULTCODE")
	private Integer RESULTCODE;
	@TableField(value = "REPORTNAME")
	private String REPORTNAME;
}
