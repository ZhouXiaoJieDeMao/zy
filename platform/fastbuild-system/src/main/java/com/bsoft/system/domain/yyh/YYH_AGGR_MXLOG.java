package com.bsoft.system.domain.yyh;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName(value = "YYH_UPLOG")
@Data//提供getter setter
public class YYH_AGGR_MXLOG {
    @TableId("UUID")
    private String UUID;
    @TableField(value = "SJJBM")
    private String SJJBM;
    @TableField(value = "IDCARD")
    private String IDCARD;
    @TableField(value = "ORGCODE")
    private String ORGCODE;
    @TableField(value = "BUSID")
    private String BUSID;
    @TableField(value = "BUSDATE")
    private Date BUSDATE;
    @TableField(value = "MAINTASKID")
    private String MAINTASKID;
    @TableField(value = "SINGLETASKID")
    private String SINGLETASKID;
    @TableField(value = "PUSHRESULT")
    private String PUSHRESULT;
}
