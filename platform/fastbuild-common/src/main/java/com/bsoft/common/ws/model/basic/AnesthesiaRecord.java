package com.bsoft.common.ws.model.basic;

import com.bsoft.common.utils.JaxbDateTimeAdapter;
import com.bsoft.common.utils.JaxbDoubleAdapter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * 住院手术麻醉
 * @author zhangcb
 * @date 2019-09-04
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "setdetails")
public class AnesthesiaRecord {

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

    @XmlElement(name ="WS02_01_039_001") private String personName; // 本人姓名
    @XmlElement(name ="WS02_01_040_01") private String sexCode; // 性别代码
    @XmlElement(name ="CT02_01_040_01") private String sexName; // 性别名称
    @XmlElement(name ="WS02_01_026_01") private Integer ageYear; // 年龄(岁)
    @XmlElement(name ="WS02_01_032_02") private Integer ageMonth; // 年龄(月)
    @XmlElement(name ="WS99_99_026_01") private Integer ageDay; // 年龄（日）
    @XmlElement(name ="WS99_99_026_02") private Integer ageHour; // 年龄（小时）
    @XmlElement(name ="WS99_99_903_40") private String kh; // 卡号
    @XmlElement(name ="WS99_99_902_07") private String klxdm; // 卡类型代码
    @XmlElement(name ="CT99_99_902_07") private String klxmc; // 卡类型名称
    @XmlElement(name ="WS08_10_908_01") private String mzzysxdm; // 门诊住院属性代码
    @XmlElement(name ="WS01_00_010_01") private String opEmHpNo1; // 门(急)诊号
    @XmlElement(name ="WS01_00_014_01") private String opEmHpNo2; // 住院号Y
    @XmlElement(name ="WS08_10_054_01") private String wardareaname; // 病区名称
    @XmlElement(name ="WS01_00_019_01") private String wardarearoom; // 病房号
    @XmlElement(name ="WS01_00_026_01") private String sickbedid; // 病床号
    @XmlElement(name ="WS01_00_008_03") private String requestnoteid; // 电子申请单编号
    @XmlElement(name ="WS01_00_901_01") private String ssbh; // 手术编号
    @XmlElement(name ="WS06_00_256_01") private String ssjbh; // 手术间编号
    @XmlElement(name ="WS01_00_908_14") private String dcid; // 麻醉记录流水号Y
    @XmlElement(name ="WS99_99_903_15") private String mzsqfsjldh; // 麻醉术前访视记录单号
    @XmlJavaTypeAdapter(JaxbDoubleAdapter.class)
    @XmlElement(name ="WS04_10_188_01") private Double weight; // 体重(kg)
    @XmlElement(name ="WS04_50_001_01") private String abobloodcode; // ABO血型代码
    @XmlElement(name ="CT04_50_001_01") private String aboxxmc; // ABO血型名称
    @XmlElement(name ="WS04_50_010_01") private String rhbloodcode; // Rh血型代码
    @XmlElement(name ="CT04_50_010_01") private String rhxxmc; // Rh血型名称
    @XmlElement(name ="WS05_01_024_19") private String sqzdbm; // 术前诊断编码
    @XmlElement(name ="CT05_01_024_19") private String sqzdmc; // 术前诊断名称
    @XmlElement(name ="WS05_01_024_20") private String shzdbm; // 术后诊断代码
    @XmlElement(name ="CT05_01_024_20") private String shzdmc; // 术后诊断名称
    @XmlElement(name ="WS06_00_296_01") private String zlgcms; // 诊疗过程描述
    @XmlElement(name ="WS06_00_093_01") private String ssjczbm; // 手术及操作代码
    @XmlElement(name ="CT06_00_093_01") private String ssjczmc; // 手术及操作名称
    @XmlElement(name ="WS06_00_307_01") private String mzzxybsdm; // 麻醉中西医标识代码
    @XmlElement(name ="CT06_00_307_01") private String mzzxybsmc; // 麻醉中西医标识名称
    @XmlElement(name ="WS02_10_028_03") private String mzgcjg; // 麻醉观察结果
    @XmlElement(name ="WS06_00_073_01") private String mzffdm; // 麻醉方法代码
    @XmlElement(name ="CT06_00_073_01") private String mzffmc; // 麻醉方法名称
    @XmlElement(name ="WS06_00_136_05") private String mzqyy; // 麻醉前用药
    @XmlElement(name ="WS08_50_022_05") private String mzywmc; // 麻醉药物名称
    @XmlElement(name ="WS04_10_260_01") private String mztw; // 麻醉体位
    @XmlElement(name ="WS06_00_226_01") private String mzms; // 麻醉描述
    @XmlElement(name ="WS06_00_260_01") private String sstwbm; // 手术体位代码
    @XmlElement(name ="CT06_00_260_01") private String sstwmc; // 手术体位名称
    @XmlElement(name ="WS05_10_063_01") private String ccgcms; // 穿刺过程
    @XmlElement(name ="WS05_01_077_01") private String mzhbzbzdm; // 麻醉合并症标志
    @XmlElement(name ="WS06_00_253_01") private String mzxg; // 麻醉效果
    @XmlElement(name ="WS06_00_228_01") private String qgcgfl; // 气管插管分类
    @XmlElement(name ="WS06_00_208_01") private String hxlxdm; // 呼吸类型代码
    @XmlElement(name ="CT06_00_208_01") private String hxlxmc; // 呼吸类型名称
    @XmlElement(name ="WS05_10_129_01") private String asalevel; // 美国麻醉医师协会(ASA)分级标准代码
    @XmlElement(name ="CT05_10_129_01") private String asafjbzmc; // 美国麻醉医师协会(ASA)分级标准名称
    @XmlJavaTypeAdapter(JaxbDateTimeAdapter.class)
    @XmlElement(name ="WS06_00_221_01") private Date operatestarttime; // 手术及操作开始日期时间
    @XmlJavaTypeAdapter(JaxbDateTimeAdapter.class)
    @XmlElement(name ="WS06_00_907_01") private Date operateendtime; // 手术及操作结束日期时间
    @XmlJavaTypeAdapter(JaxbDateTimeAdapter.class)
    @XmlElement(name ="WS06_00_095_03") private Date mzkssj; // 麻醉开始日期时间Y
    @XmlJavaTypeAdapter(JaxbDateTimeAdapter.class)
    @XmlElement(name ="WS06_00_191_01") private Date outoptroomtime; // 出手术室日期时间
    @XmlElement(name ="WS06_00_185_01") private String hzqxdm; // 患者去向
    @XmlElement(name ="WS02_01_039_037") private String operator; // 手术者姓名
    @XmlElement(name ="WS02_01_039_027") private String mzysqm; // 麻醉医师姓名
    @XmlElement(name ="WS02_01_925_22") private String mzysgh; // 麻醉医师工号
    @XmlJavaTypeAdapter(JaxbDateTimeAdapter.class)
    @XmlElement(name ="WS09_00_053_08") private Date qmrqsj; // 麻醉医师签名日期时间
    @XmlElement(name ="WS08_10_025_01") private String deptcode; // 科室代码
    @XmlElement(name ="CT08_10_025_01") private String deptname; // 科室名称
    @XmlElement(name ="WS08_10_052_01") private String organCode; // 医疗机构组织机构代码Y
    @XmlElement(name ="CT08_10_052_01") private String organName; // 医疗机构组织机构名称Y
    @XmlElement(name ="WS02_01_030_01") private String sfzjhm; // 身份证件号码Y

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

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
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

    public String getKh() {
        return kh;
    }

    public void setKh(String kh) {
        this.kh = kh;
    }

    public String getKlxdm() {
        return klxdm;
    }

    public void setKlxdm(String klxdm) {
        this.klxdm = klxdm;
    }

    public String getKlxmc() {
        return klxmc;
    }

    public void setKlxmc(String klxmc) {
        this.klxmc = klxmc;
    }

    public String getMzzysxdm() {
        return mzzysxdm;
    }

    public void setMzzysxdm(String mzzysxdm) {
        this.mzzysxdm = mzzysxdm;
    }

    public Integer getAgeMonth() {
        return ageMonth;
    }

    public void setAgeMonth(Integer ageMonth) {
        this.ageMonth = ageMonth;
    }

    public Integer getAgeDay() {
        return ageDay;
    }

    public void setAgeDay(Integer ageDay) {
        this.ageDay = ageDay;
    }

    public Integer getAgeHour() {
        return ageHour;
    }

    public void setAgeHour(Integer ageHour) {
        this.ageHour = ageHour;
    }

    public String getOpEmHpNo1() {
        return opEmHpNo1;
    }

    public void setOpEmHpNo1(String opEmHpNo1) {
        this.opEmHpNo1 = opEmHpNo1;
    }

    public String getOpEmHpNo2() {
        return opEmHpNo2;
    }

    public void setOpEmHpNo2(String opEmHpNo2) {
        this.opEmHpNo2 = opEmHpNo2;
    }

    public String getWardareaname() {
        return wardareaname;
    }

    public void setWardareaname(String wardareaname) {
        this.wardareaname = wardareaname;
    }

    public String getWardarearoom() {
        return wardarearoom;
    }

    public void setWardarearoom(String wardarearoom) {
        this.wardarearoom = wardarearoom;
    }

    public String getSickbedid() {
        return sickbedid;
    }

    public void setSickbedid(String sickbedid) {
        this.sickbedid = sickbedid;
    }

    public String getRequestnoteid() {
        return requestnoteid;
    }

    public void setRequestnoteid(String requestnoteid) {
        this.requestnoteid = requestnoteid;
    }

    public String getSsbh() {
        return ssbh;
    }

    public void setSsbh(String ssbh) {
        this.ssbh = ssbh;
    }

    public String getSsjbh() {
        return ssjbh;
    }

    public void setSsjbh(String ssjbh) {
        this.ssjbh = ssjbh;
    }

    public String getDcid() {
        return dcid;
    }

    public void setDcid(String dcid) {
        this.dcid = dcid;
    }

    public String getMzsqfsjldh() {
        return mzsqfsjldh;
    }

    public void setMzsqfsjldh(String mzsqfsjldh) {
        this.mzsqfsjldh = mzsqfsjldh;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getAbobloodcode() {
        return abobloodcode;
    }

    public void setAbobloodcode(String abobloodcode) {
        this.abobloodcode = abobloodcode;
    }

    public String getAboxxmc() {
        return aboxxmc;
    }

    public void setAboxxmc(String aboxxmc) {
        this.aboxxmc = aboxxmc;
    }

    public String getRhbloodcode() {
        return rhbloodcode;
    }

    public void setRhbloodcode(String rhbloodcode) {
        this.rhbloodcode = rhbloodcode;
    }

    public String getRhxxmc() {
        return rhxxmc;
    }

    public void setRhxxmc(String rhxxmc) {
        this.rhxxmc = rhxxmc;
    }

    public String getSqzdbm() {
        return sqzdbm;
    }

    public void setSqzdbm(String sqzdbm) {
        this.sqzdbm = sqzdbm;
    }

    public String getSqzdmc() {
        return sqzdmc;
    }

    public void setSqzdmc(String sqzdmc) {
        this.sqzdmc = sqzdmc;
    }

    public String getShzdbm() {
        return shzdbm;
    }

    public void setShzdbm(String shzdbm) {
        this.shzdbm = shzdbm;
    }

    public String getShzdmc() {
        return shzdmc;
    }

    public void setShzdmc(String shzdmc) {
        this.shzdmc = shzdmc;
    }

    public String getZlgcms() {
        return zlgcms;
    }

    public void setZlgcms(String zlgcms) {
        this.zlgcms = zlgcms;
    }

    public String getSsjczbm() {
        return ssjczbm;
    }

    public void setSsjczbm(String ssjczbm) {
        this.ssjczbm = ssjczbm;
    }

    public String getSsjczmc() {
        return ssjczmc;
    }

    public void setSsjczmc(String ssjczmc) {
        this.ssjczmc = ssjczmc;
    }

    public String getMzzxybsdm() {
        return mzzxybsdm;
    }

    public void setMzzxybsdm(String mzzxybsdm) {
        this.mzzxybsdm = mzzxybsdm;
    }

    public String getMzzxybsmc() {
        return mzzxybsmc;
    }

    public void setMzzxybsmc(String mzzxybsmc) {
        this.mzzxybsmc = mzzxybsmc;
    }

    public String getMzgcjg() {
        return mzgcjg;
    }

    public void setMzgcjg(String mzgcjg) {
        this.mzgcjg = mzgcjg;
    }

    public String getMzffdm() {
        return mzffdm;
    }

    public void setMzffdm(String mzffdm) {
        this.mzffdm = mzffdm;
    }

    public String getMzffmc() {
        return mzffmc;
    }

    public void setMzffmc(String mzffmc) {
        this.mzffmc = mzffmc;
    }

    public String getMzqyy() {
        return mzqyy;
    }

    public void setMzqyy(String mzqyy) {
        this.mzqyy = mzqyy;
    }

    public String getMzywmc() {
        return mzywmc;
    }

    public void setMzywmc(String mzywmc) {
        this.mzywmc = mzywmc;
    }

    public String getMztw() {
        return mztw;
    }

    public void setMztw(String mztw) {
        this.mztw = mztw;
    }

    public String getMzms() {
        return mzms;
    }

    public void setMzms(String mzms) {
        this.mzms = mzms;
    }

    public String getSstwbm() {
        return sstwbm;
    }

    public void setSstwbm(String sstwbm) {
        this.sstwbm = sstwbm;
    }

    public String getSstwmc() {
        return sstwmc;
    }

    public void setSstwmc(String sstwmc) {
        this.sstwmc = sstwmc;
    }

    public String getCcgcms() {
        return ccgcms;
    }

    public void setCcgcms(String ccgcms) {
        this.ccgcms = ccgcms;
    }

    public String getMzhbzbzdm() {
        return mzhbzbzdm;
    }

    public void setMzhbzbzdm(String mzhbzbzdm) {
        this.mzhbzbzdm = mzhbzbzdm;
    }

    public String getMzxg() {
        return mzxg;
    }

    public void setMzxg(String mzxg) {
        this.mzxg = mzxg;
    }

    public String getQgcgfl() {
        return qgcgfl;
    }

    public void setQgcgfl(String qgcgfl) {
        this.qgcgfl = qgcgfl;
    }

    public String getHxlxdm() {
        return hxlxdm;
    }

    public void setHxlxdm(String hxlxdm) {
        this.hxlxdm = hxlxdm;
    }

    public String getHxlxmc() {
        return hxlxmc;
    }

    public void setHxlxmc(String hxlxmc) {
        this.hxlxmc = hxlxmc;
    }

    public String getAsalevel() {
        return asalevel;
    }

    public void setAsalevel(String asalevel) {
        this.asalevel = asalevel;
    }

    public String getAsafjbzmc() {
        return asafjbzmc;
    }

    public void setAsafjbzmc(String asafjbzmc) {
        this.asafjbzmc = asafjbzmc;
    }

    public Date getOperatestarttime() {
        return operatestarttime;
    }

    public void setOperatestarttime(Date operatestarttime) {
        this.operatestarttime = operatestarttime;
    }

    public Date getOperateendtime() {
        return operateendtime;
    }

    public void setOperateendtime(Date operateendtime) {
        this.operateendtime = operateendtime;
    }

    public Date getMzkssj() {
        return mzkssj;
    }

    public void setMzkssj(Date mzkssj) {
        this.mzkssj = mzkssj;
    }

    public Date getOutoptroomtime() {
        return outoptroomtime;
    }

    public void setOutoptroomtime(Date outoptroomtime) {
        this.outoptroomtime = outoptroomtime;
    }

    public String getHzqxdm() {
        return hzqxdm;
    }

    public void setHzqxdm(String hzqxdm) {
        this.hzqxdm = hzqxdm;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getMzysqm() {
        return mzysqm;
    }

    public void setMzysqm(String mzysqm) {
        this.mzysqm = mzysqm;
    }

    public String getMzysgh() {
        return mzysgh;
    }

    public void setMzysgh(String mzysgh) {
        this.mzysgh = mzysgh;
    }

    public Date getQmrqsj() {
        return qmrqsj;
    }

    public void setQmrqsj(Date qmrqsj) {
        this.qmrqsj = qmrqsj;
    }

    public String getDeptcode() {
        return deptcode;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
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

    public String getSfzjhm() {
        return sfzjhm;
    }

    public void setSfzjhm(String sfzjhm) {
        this.sfzjhm = sfzjhm;
    }
}
