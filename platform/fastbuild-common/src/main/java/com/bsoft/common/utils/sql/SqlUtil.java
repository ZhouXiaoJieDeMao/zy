package com.bsoft.common.utils.sql;

import com.bsoft.common.exception.UtilException;
import com.bsoft.common.utils.StringUtils;

/**
 * sql操作工具类
 * 
 * @author fastbuild
 */
public class SqlUtil
{
    /**
     * 定义常用的 sql关键字
     */
    public static String SQL_REGEX = "select |insert |delete |update |drop |count |exec |chr |mid |master |truncate |char |and |declare ";

    /**
     * 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value)
    {
        if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value))
        {
            throw new UtilException("参数不符合规范，不能进行查询");
        }
        return value;
    }
    /**
     * 检查字符，防止注入绕过
     */
    public static boolean sql_inj(String str){
        String inj_str = "insert|delete|update|chr|mid|master|truncate|char|declare|;";
        //这里的东西还可以自己添加
        String[] inj_stra=inj_str.split("\\|");
        for (int i=0 ; i < inj_stra.length ; i++ ){
            if (str.indexOf(inj_stra[i])>=0){
                return true;
            }
        }
        return false;
    }

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value)
    {
        return value.matches(SQL_PATTERN);
    }

    /**
     * SQL关键字检查
     */
    public static void filterKeyword(String value)
    {
        if (StringUtils.isEmpty(value))
        {
            return;
        }
        String[] sqlKeywords = StringUtils.split(SQL_REGEX, "\\|");
        for (String sqlKeyword : sqlKeywords)
        {
            if (StringUtils.indexOfIgnoreCase(value, sqlKeyword) > -1)
            {
                throw new UtilException("参数存在SQL注入风险");
            }
        }
    }
}
