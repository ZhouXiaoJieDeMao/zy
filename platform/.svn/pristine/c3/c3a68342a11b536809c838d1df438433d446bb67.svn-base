package com.bsoft.system.domain.yyh;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 医养护数据归集
 */
@TableName("YYH_DATA_AGGR")
@Data//提供getter setter
public class YYH_DATA_AGGR {
	@TableId("COLRESCODE")
	private String COLRESCODE;//交换标准码
	@TableField(value = "SETCODE")
	private String SETCODE;//数据集编码
	@TableField(value = "DESCRIBE")
	private String DESCRIBE;//描述
	@TableField(value = "SQLCONTENT")
	private String SQLCONTENT;//SQL
	@TableField(value = "SFQY")
	private Long SFQY;//是否启用
	@TableField(value = "CLIENTID")
	private Long CLIENTID;//数据源ID
	@TableField(value = "LASTEXECUTETIME")
	private Date LASTEXECUTETIME;//最后执行时间
	@TableField(value = "INTERVALMINUTES")
	private Integer INTERVALMINUTES;//执行间隔分钟
}
