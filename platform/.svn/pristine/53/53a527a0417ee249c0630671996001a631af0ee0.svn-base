package com.bsoft.system.domain.yyh;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 医养护数据采集上传记录表
 */
@TableName("YYH_AGGR_RECORD")
@Data//提供getter setter
public class YYH_AGGR_RECORD {
	@TableId("ENDTASKID")
	private String ENDTASKID;
	@TableField(value = "COLRESCODE")
	private String COLRESCODE;
	@TableField(value = "SETCODE")
	private String SETCODE;
	@TableField(value = "CLIENTID")
	private Long CLIENTID;//数据源ID
	@TableField(value = "TASKNUM")
	private Long TASKNUM;
	@TableField(value = "TOTALDATANUM")
	private Long TOTALDATANUM;
	@TableField(value = "SINGLEDATANUM")
	private Long SINGLEDATANUM;
	@TableField(value = "TOTALTASKID")
	private String TOTALTASKID;
	@TableField(value = "SINGLETASKID")
	private String SINGLETASKID;
	@TableField(value = "SN")
	private Long SN;
	@TableField(value = "BEGINDATETIME")
	private String BEGINDATETIME;
	@TableField(value = "ENDDATETIME")
	private String ENDDATETIME;
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@TableField(value = "UPLOADTIME")
    private Date UPLOADTIME;
	@TableField(value = "ORGANIZATION_CODE")
    private String ORGANIZATION_CODE;
	@TableField(value = "ORGANIZATION_NAME")
    private String ORGANIZATION_NAME;
}
