package com.bsoft.common.ws.model.basic;

import com.bsoft.common.utils.JaxbDateAdapter;
import com.bsoft.common.utils.JaxbDateTimeAdapter;
import com.bsoft.common.utils.JaxbIntegerAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * 住院病案首页诊断信息
 * @author zhangcb
 * @date 2019-08-28
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "setdetails")
public class MedicalRecordDiagnosisInfo {

    /**
     * 无用#14
     */
    private String SERIALNUM_ID;
    private String TASK_ID;
    private String BATCH_NUM;
    private String LOCAL_ID;
    private String BUSINESS_ID;
    private String BASIC_ACTIVE_ID;
    private String DATAGENERATE_DATE;
    private String DOMAIN_CODE;
    private String ORGANIZATION_CODE;
    private String ORGANIZATION_NAME;
    private String CREATE_DATE;
    private String UPDATE_DATE;
    private String ARCHIVE_DATE;
    private String RECORD_IDEN;

    @XmlTransient private Date lastModified;

    @XmlElement(name ="WS02_01_039_001") private String personname; // 本人姓名
    @XmlElement(name ="WS02_01_040_01") private String sexCode; // 性别代码
    @XmlElement(name ="CT02_01_040_01") private String sexName; // 性别名称
    @XmlJavaTypeAdapter(JaxbIntegerAdapter.class)
    @XmlElement(name ="WS02_01_026_01") private Integer ageYear; // 年龄(岁)
    @XmlJavaTypeAdapter(JaxbIntegerAdapter.class)
    @XmlElement(name ="WS02_01_032_02") private Integer ageMonth; // 年龄(月)
    @XmlJavaTypeAdapter(JaxbDateAdapter.class)
    @XmlElement(name ="WS02_01_005_01_01") private Date birthday; // 出生日期
    @XmlElement(name ="WS02_01_031_01") private String sfzjlbdm; // 身份证件类别代码
    @XmlElement(name ="CT02_01_031_01") private String sfzjlbmc; // 身份证件类别名称
    @XmlElement(name ="WS02_01_030_01") private String idcard; // 身份证件号码Y
    @XmlElement(name ="WS01_00_014_01") private String hpSNo; // 住院号Y
    @XmlElement(name ="WS01_00_004_01") private String mrNo; // 病案号Y
    @XmlElement(name ="WS05_01_901_01") private String diagMark; // 中西医诊断标识Y
    @XmlElement(name ="WS05_01_901_02") private String zczdbs; // 主从诊断标识Y
    @XmlElement(name ="WS05_01_080_01") private String zdsw; // 诊断顺位序号
    @XmlJavaTypeAdapter(JaxbDateTimeAdapter.class)
    @XmlElement(name ="WS05_01_058_01") private Date zdsj; // 诊断日期时间
    @XmlElement(name ="WS05_01_900_01") private String hzzdlx; // 诊断类型代码Y
    @XmlElement(name ="WS99_99_902_125") private String ynzdlx; // 院内诊断类型代码
    @XmlElement(name ="CT05_01_900_01") private String zdlxmc; // 诊断类型名称
    @XmlElement(name ="CT99_99_902_125") private String ynzdlxmc; // 院内诊断类型名称
    @XmlElement(name ="WS05_01_024_01") private String jbzd; // 疾病诊断编码Y
    @XmlElement(name ="WS99_99_902_09") private String ynjbzd; // 院内疾病诊断代码
    @XmlElement(name ="CT05_01_024_01") private String jbzdmc; // 疾病诊断名称
    @XmlElement(name ="CT99_99_902_09") private String ynjbzdmc; // 院内疾病诊断名称
    @XmlElement(name ="WS05_10_130_01") private String zybmdm; // 中医病名代码
    @XmlElement(name ="WS99_99_902_80") private String ynZybmdm; // 院内中医病名代码
    @XmlElement(name ="CT05_10_130_01") private String zybmmc; // 中医病名名称
    @XmlElement(name ="CT99_99_902_80") private String ynZybmmc; // 院内中医病名名称
    @XmlElement(name ="WS05_10_130_02") private String zyhzdm; // 中医证候代码
    @XmlElement(name ="WS99_99_902_81") private String ynZyhzdm; // 院内中医证候代码
    @XmlElement(name ="CT05_10_130_02") private String zyhzmc; // 中医证候名称
    @XmlElement(name ="CT99_99_902_81") private String ynZyhzmc; // 院内中医证候名称
    @XmlElement(name ="WS05_01_024_25") private String extlCauseCode; // 损伤中毒的外部原因疾病代码
    @XmlElement(name ="CT05_01_024_25") private String extlCauseName; // 损伤中毒的外部原因疾病名称
    @XmlElement(name ="WS05_10_152_01") private String extlCauseResult; // 损伤中毒的外部原因
    @XmlElement(name ="WS01_00_005_01") private String blh; // 病理号
    @XmlElement(name ="WS09_00_104_01") private String inHpCondition; // 入院病情代码
    @XmlElement(name ="WS99_99_902_126") private String ynInHpCondition; // 院内入院病情代码
    @XmlElement(name ="CT09_00_104_01") private String inHpCondifitonName; // 入院病情名称
    @XmlElement(name ="CT99_99_902_126") private String ynInHpCondifitonName; // 院内入院病情名称
    @XmlElement(name ="WS05_10_113_01") private String treatResultCode; // 治疗结果代码
    @XmlElement(name ="WS99_99_902_127") private String ynTreatResultCode; // 院内治疗结果代码
    @XmlElement(name ="CT05_10_113_01") private String treatResultName; // 治疗结果名称
    @XmlElement(name ="CT99_99_902_127") private String ynTreatResultName; // 院内治疗结果名称
    @XmlElement(name ="WS02_01_039_031") private String docName; // 责任医师姓名
    @XmlElement(name ="WS08_10_052_01") private String organCode; // 医疗机构组织机构代码Y
    @XmlElement(name ="CT08_10_052_01") private String organName; // 医疗机构组织机构名称Y
    @XmlJavaTypeAdapter(JaxbDateTimeAdapter.class)
    @XmlElement(name ="WS06_00_913_01") private Date bmwcrq; // 编目完成日期时间Y

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getSERIALNUM_ID() {
        return SERIALNUM_ID;
    }

    public void setSERIALNUM_ID(String SERIALNUM_ID) {
        this.SERIALNUM_ID = SERIALNUM_ID;
    }

    public String getTASK_ID() {
        return TASK_ID;
    }

    public void setTASK_ID(String TASK_ID) {
        this.TASK_ID = TASK_ID;
    }

    public String getBATCH_NUM() {
        return BATCH_NUM;
    }

    public void setBATCH_NUM(String BATCH_NUM) {
        this.BATCH_NUM = BATCH_NUM;
    }

    public String getLOCAL_ID() {
        return LOCAL_ID;
    }

    public void setLOCAL_ID(String LOCAL_ID) {
        this.LOCAL_ID = LOCAL_ID;
    }

    public String getBUSINESS_ID() {
        return BUSINESS_ID;
    }

    public void setBUSINESS_ID(String BUSINESS_ID) {
        this.BUSINESS_ID = BUSINESS_ID;
    }

    public String getBASIC_ACTIVE_ID() {
        return BASIC_ACTIVE_ID;
    }

    public void setBASIC_ACTIVE_ID(String BASIC_ACTIVE_ID) {
        this.BASIC_ACTIVE_ID = BASIC_ACTIVE_ID;
    }

    public String getDATAGENERATE_DATE() {
        return DATAGENERATE_DATE;
    }

    public void setDATAGENERATE_DATE(String DATAGENERATE_DATE) {
        this.DATAGENERATE_DATE = DATAGENERATE_DATE;
    }

    public String getDOMAIN_CODE() {
        return DOMAIN_CODE;
    }

    public void setDOMAIN_CODE(String DOMAIN_CODE) {
        this.DOMAIN_CODE = DOMAIN_CODE;
    }

    public String getORGANIZATION_CODE() {
        return ORGANIZATION_CODE;
    }

    public void setORGANIZATION_CODE(String ORGANIZATION_CODE) {
        this.ORGANIZATION_CODE = ORGANIZATION_CODE;
    }

    public String getORGANIZATION_NAME() {
        return ORGANIZATION_NAME;
    }

    public void setORGANIZATION_NAME(String ORGANIZATION_NAME) {
        this.ORGANIZATION_NAME = ORGANIZATION_NAME;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public String getUPDATE_DATE() {
        return UPDATE_DATE;
    }

    public void setUPDATE_DATE(String UPDATE_DATE) {
        this.UPDATE_DATE = UPDATE_DATE;
    }

    public String getARCHIVE_DATE() {
        return ARCHIVE_DATE;
    }

    public void setARCHIVE_DATE(String ARCHIVE_DATE) {
        this.ARCHIVE_DATE = ARCHIVE_DATE;
    }

    public String getRECORD_IDEN() {
        return RECORD_IDEN;
    }

    public void setRECORD_IDEN(String RECORD_IDEN) {
        this.RECORD_IDEN = RECORD_IDEN;
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname;
    }

    public String getSexCode() {
        return sexCode;
    }

    public void setSexCode(String sexCode) {
        this.sexCode = sexCode;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public Integer getAgeYear() {
        return ageYear;
    }

    public void setAgeYear(Integer ageYear) {
        this.ageYear = ageYear;
    }

    public Integer getAgeMonth() {
        return ageMonth;
    }

    public void setAgeMonth(Integer ageMonth) {
        this.ageMonth = ageMonth;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSfzjlbdm() {
        return sfzjlbdm;
    }

    public void setSfzjlbdm(String sfzjlbdm) {
        this.sfzjlbdm = sfzjlbdm;
    }

    public String getSfzjlbmc() {
        return sfzjlbmc;
    }

    public void setSfzjlbmc(String sfzjlbmc) {
        this.sfzjlbmc = sfzjlbmc;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getHpSNo() {
        return hpSNo;
    }

    public void setHpSNo(String hpSNo) {
        this.hpSNo = hpSNo;
    }

    public String getMrNo() {
        return mrNo;
    }

    public void setMrNo(String mrNo) {
        this.mrNo = mrNo;
    }

    public String getDiagMark() {
        return diagMark;
    }

    public void setDiagMark(String diagMark) {
        this.diagMark = diagMark;
    }

    public String getZczdbs() {
        return zczdbs;
    }

    public void setZczdbs(String zczdbs) {
        this.zczdbs = zczdbs;
    }

    public String getZdsw() {
        return zdsw;
    }

    public void setZdsw(String zdsw) {
        this.zdsw = zdsw;
    }

    public Date getZdsj() {
        return zdsj;
    }

    public void setZdsj(Date zdsj) {
        this.zdsj = zdsj;
    }

    public String getHzzdlx() {
        return hzzdlx;
    }

    public void setHzzdlx(String hzzdlx) {
        this.hzzdlx = hzzdlx;
    }

    public String getYnzdlx() {
        return ynzdlx;
    }

    public void setYnzdlx(String ynzdlx) {
        this.ynzdlx = ynzdlx;
    }

    public String getZdlxmc() {
        return zdlxmc;
    }

    public void setZdlxmc(String zdlxmc) {
        this.zdlxmc = zdlxmc;
    }

    public String getYnzdlxmc() {
        return ynzdlxmc;
    }

    public void setYnzdlxmc(String ynzdlxmc) {
        this.ynzdlxmc = ynzdlxmc;
    }

    public String getJbzd() {
        return jbzd;
    }

    public void setJbzd(String jbzd) {
        this.jbzd = jbzd;
    }

    public String getYnjbzd() {
        return ynjbzd;
    }

    public void setYnjbzd(String ynjbzd) {
        this.ynjbzd = ynjbzd;
    }

    public String getJbzdmc() {
        return jbzdmc;
    }

    public void setJbzdmc(String jbzdmc) {
        this.jbzdmc = jbzdmc;
    }

    public String getYnjbzdmc() {
        return ynjbzdmc;
    }

    public void setYnjbzdmc(String ynjbzdmc) {
        this.ynjbzdmc = ynjbzdmc;
    }

    public String getZybmdm() {
        return zybmdm;
    }

    public void setZybmdm(String zybmdm) {
        this.zybmdm = zybmdm;
    }

    public String getYnZybmdm() {
        return ynZybmdm;
    }

    public void setYnZybmdm(String ynZybmdm) {
        this.ynZybmdm = ynZybmdm;
    }

    public String getZybmmc() {
        return zybmmc;
    }

    public void setZybmmc(String zybmmc) {
        this.zybmmc = zybmmc;
    }

    public String getYnZybmmc() {
        return ynZybmmc;
    }

    public void setYnZybmmc(String ynZybmmc) {
        this.ynZybmmc = ynZybmmc;
    }

    public String getZyhzdm() {
        return zyhzdm;
    }

    public void setZyhzdm(String zyhzdm) {
        this.zyhzdm = zyhzdm;
    }

    public String getYnZyhzdm() {
        return ynZyhzdm;
    }

    public void setYnZyhzdm(String ynZyhzdm) {
        this.ynZyhzdm = ynZyhzdm;
    }

    public String getZyhzmc() {
        return zyhzmc;
    }

    public void setZyhzmc(String zyhzmc) {
        this.zyhzmc = zyhzmc;
    }

    public String getYnZyhzmc() {
        return ynZyhzmc;
    }

    public void setYnZyhzmc(String ynZyhzmc) {
        this.ynZyhzmc = ynZyhzmc;
    }

    public String getExtlCauseCode() {
        return extlCauseCode;
    }

    public void setExtlCauseCode(String extlCauseCode) {
        this.extlCauseCode = extlCauseCode;
    }

    public String getExtlCauseName() {
        return extlCauseName;
    }

    public void setExtlCauseName(String extlCauseName) {
        this.extlCauseName = extlCauseName;
    }

    public String getExtlCauseResult() {
        return extlCauseResult;
    }

    public void setExtlCauseResult(String extlCauseResult) {
        this.extlCauseResult = extlCauseResult;
    }

    public String getBlh() {
        return blh;
    }

    public void setBlh(String blh) {
        this.blh = blh;
    }

    public String getInHpCondition() {
        return inHpCondition;
    }

    public void setInHpCondition(String inHpCondition) {
        this.inHpCondition = inHpCondition;
    }

    public String getYnInHpCondition() {
        return ynInHpCondition;
    }

    public void setYnInHpCondition(String ynInHpCondition) {
        this.ynInHpCondition = ynInHpCondition;
    }

    public String getInHpCondifitonName() {
        return inHpCondifitonName;
    }

    public void setInHpCondifitonName(String inHpCondifitonName) {
        this.inHpCondifitonName = inHpCondifitonName;
    }

    public String getYnInHpCondifitonName() {
        return ynInHpCondifitonName;
    }

    public void setYnInHpCondifitonName(String ynInHpCondifitonName) {
        this.ynInHpCondifitonName = ynInHpCondifitonName;
    }

    public String getTreatResultCode() {
        return treatResultCode;
    }

    public void setTreatResultCode(String treatResultCode) {
        this.treatResultCode = treatResultCode;
    }

    public String getYnTreatResultCode() {
        return ynTreatResultCode;
    }

    public void setYnTreatResultCode(String ynTreatResultCode) {
        this.ynTreatResultCode = ynTreatResultCode;
    }

    public String getTreatResultName() {
        return treatResultName;
    }

    public void setTreatResultName(String treatResultName) {
        this.treatResultName = treatResultName;
    }

    public String getYnTreatResultName() {
        return ynTreatResultName;
    }

    public void setYnTreatResultName(String ynTreatResultName) {
        this.ynTreatResultName = ynTreatResultName;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public Date getBmwcrq() {
        return bmwcrq;
    }

    public void setBmwcrq(Date bmwcrq) {
        this.bmwcrq = bmwcrq;
    }
}
