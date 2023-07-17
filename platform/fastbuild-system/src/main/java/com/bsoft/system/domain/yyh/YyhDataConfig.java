package com.bsoft.system.domain.yyh;

/**
 * 医养护数据上传配置文件
 */
public class YyhDataConfig {
    private String setcode;//数据集编码
    private String colrescode;//交换标准编码
    private String describe;//数据集描述
    private String sqlcontent;//数据库查询语句
    private String upflagsql;//更新标志条件语句-如yyhupflag !=1
    private String upsql;//变更语句，根据主键进行变更，查询语句中增加一个额外的字段
    private String endcon;//查询末尾条件-如order by limit

    private String querykey;//主键字段说明
    private String queryvalue;//主键值获取
    private String queryjgtj;//对账获取条件
    //运行模式，1：定时;2:实时
    private String runmodel;

    private String businessCode;//作废业务编码

    private String zfzj; // 作废上传主键说明
    private String zfzjz; // 作废上传主键获取

    public YyhDataConfig() {
    }

    public YyhDataConfig(String setcode, String colrescode, String describe, String sqlcontent, String endcon) {
        this.setcode = setcode;
        this.colrescode = colrescode;
        this.describe = describe;
        this.sqlcontent = sqlcontent;
        this.endcon = endcon;
    }

    /**
     * 获取医养护数据上传配置语句
     * @return
     */
    public String getYyhUpsql(){
        StringBuffer sb_sql = new StringBuffer();
        sb_sql.append(getSqlcontent().trim());
        sb_sql.append(" ");
        sb_sql.append(getUpflagsql().trim());
        sb_sql.append(" ");
        sb_sql.append(getEndcon().trim());
        return sb_sql.toString();
    }

    /**
     * 获取修改上传记录表的语句
     * @return
     */
    public String getYyhUpflagSql(){
        return getUpsql().trim();
    }

    /**
     * 数据对账检索语句获取
     * @return
     */
    public String getYyhSjdzUpSql(){
        StringBuffer sb_sql = new StringBuffer();
        sb_sql.append("select ");
        sb_sql.append(getQueryvalue().trim());
        sb_sql.append(" as MVAL from (");
        sb_sql.append(getSqlcontent().trim());
        sb_sql.append(" ");
        sb_sql.append(getQueryjgtj().trim());
        sb_sql.append(") t limit 300000");
        return sb_sql.toString();
    }

    /**
     * 数据作废检索语句获取
     * @return
     */
    public String getYyhZfUpSql(String sql){
        StringBuffer sb_sql = new StringBuffer();
        sb_sql.append("select ");
        sb_sql.append(getZfzjz().trim());
        sb_sql.append(" as MVAL,* from (");
        sb_sql.append(sql.trim());
        sb_sql.append(" ");
//        sb_sql.append(getQueryjgtj().trim());
        sb_sql.append("  order by ywsj desc  limit 1000000) t ");
        return sb_sql.toString();
    }

    public String getYyhSjdzAllUpSql(String lsjgid){
        StringBuffer sb_sql = new StringBuffer();
        sb_sql.append("select ");
        sb_sql.append(getQueryvalue().trim());
        sb_sql.append(" as MVAL from (");
        sb_sql.append(getSqlcontent().trim());
        sb_sql.append(" ");
        String ls_jgtj = getQueryjgtj().trim();
        int li_jgid = ls_jgtj.indexOf(lsjgid);
        sb_sql.append(ls_jgtj.substring(li_jgid+lsjgid.length()));
        sb_sql.append(") t limit 300000");
        return sb_sql.toString();
    }


    /**
     * 获取完整的bdb检索语句
     *
     * @param addCon 添加的条件，如
     * @return
     */
    public String getUserQuerySql(String addCon) {
        return this.sqlcontent + " " + addCon + " " + this.endcon;
    }

    public String getSetcode() {
        return setcode;
    }

    public void setSetcode(String setcode) {
        this.setcode = setcode;
    }

    public String getColrescode() {
        return colrescode;
    }

    public void setColrescode(String colrescode) {
        this.colrescode = colrescode;
    }

    public String getDescribe() {
        return (null ==describe)?"":describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getSqlcontent() {
        return (null == sqlcontent)?"":sqlcontent;
    }

    public void setSqlcontent(String sqlcontent) {
        this.sqlcontent = sqlcontent;
    }

    public String getUpflagsql() {
        return (null == upflagsql)?"":upflagsql;
    }

    public void setUpflagsql(String upflagsql) {
        this.upflagsql = upflagsql;
    }

    public String getUpsql() {
        return (null == upsql)?"":upsql;
    }

    public void setUpsql(String upsql) {
        this.upsql = upsql;
    }

    public String getEndcon() {
        return (null == endcon)?"":endcon;
    }

    public void setEndcon(String endcon) {
        this.endcon = endcon;
    }

    public String getQuerykey() {
        return (null == querykey)?"":querykey;
    }

    public void setQuerykey(String querykey) {
        this.querykey = querykey;
    }

    public String getQueryvalue() {
        return (null ==queryvalue)?"":queryvalue;
    }

    public void setQueryvalue(String queryvalue) {
        this.queryvalue = queryvalue;
    }

    public String getQueryjgtj() {
        return (null == queryjgtj)?"":queryjgtj;
    }

    public void setQueryjgtj(String queryjgtj) {
        this.queryjgtj = queryjgtj;
    }

    public String getRunmodel() {
        return (null ==runmodel)?"":runmodel;
    }

    public void setRunmodel(String runmodel) {
        this.runmodel = runmodel;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getZfzj() {
        return zfzj;
    }

    public void setZfzj(String zfzj) {
        this.zfzj = zfzj;
    }

    public String getZfzjz() {
        return zfzjz;
    }

    public void setZfzjz(String zfzjz) {
        this.zfzjz = zfzjz;
    }
}
