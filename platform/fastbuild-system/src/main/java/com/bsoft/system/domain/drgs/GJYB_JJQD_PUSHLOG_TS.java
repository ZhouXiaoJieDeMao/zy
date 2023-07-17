package com.bsoft.system.domain.drgs;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("gjyb_jjqd_pushlog")
@Data
public class GJYB_JJQD_PUSHLOG_TS implements GJYB_PUSHLOG{

    @TableId("UUID")
    private String UUID;
    @TableField(value = "PSN_NO")
    private String PSN_NO;
    @TableField(value = "BUSID")
    private String BUSID;
    @TableField(value = "PUSHDATE")
    private Date PUSHDATE;
    @TableField(value = "RESID")
    private String RESID;
    @TableField(fill = FieldFill.INSERT,value = "FUNID")
    private String FUNID;
    @TableField(value = "SCSHBZ")
    private String SCSHBZ;
    @TableField(value = "YBSHYJ")
    private String YBSHYJ;
    @TableField(value = "QRCSSJ")
    private Date QRCSSJ;
}
