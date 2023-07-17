package com.bsoft.system.domain.drgs;

import com.bsoft.common.annotation.DataSource;
import com.bsoft.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * drgs返回视图
 */
@Data
public class ViewDrgsVO {
    /**
     * 机构编码
     */
    @Excel(name = "机构ID")
    private String JGID;
    /**
     * 机构编码
     */
    @Excel(name = "机构编码")
    private String JGMC;
    /**
     * 病人姓名
     */
    @Excel(name = "病人姓名")
    private String BRXM;
    /**
     * 住院号
     */
    @Excel(name = "住院号")
    private String ZYH;
    /**
     * 上传标志
     */
    @Excel(name = "上传标志 （0未上传1已上传2已提交）")
    private String SCSHBZ;
    /**
     * 出院时间
     */
    @Excel(name = "出院时间")
    private Date CYSJ;
    /**
     * 医保返回信息
     */
    @Excel(name = "医保返回信息")
    private String RESID;
}