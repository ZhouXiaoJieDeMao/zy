package com.bsoft.system.domain.drgs;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("GJYB_ZYYZ_PUSHLOG")
@Data
public class GJYB_ZYYZ_PUSHLOG implements GJYB_PUSHLOG{

    @TableField(fill = FieldFill.DEFAULT,value = "UUID")
    private String UUID;
    @TableField(value = "PSN_NO")
    private String PSN_NO;
    @TableId("BUSID")
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
}
