package com.bsoft.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

	/**  
     * 自动生成32位的UUid，对应数据库的主键id进行插入用。  
     * @return  
     */  
    public static String getUUID() {  
          
        return UUID.randomUUID().toString().replace("-", "");  
    }  
  
	
	/**
	 * 随机生成六位数验证码 
	 * @return
	 */
	public static int getRandomNum(){
		 Random r = new Random();
		 return r.nextInt(900000)+100000;//(Math.random()*(999999-100000)+100000)
	}
	
	/**
	 * 检测字符串是否不为空(null,"","null")
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s){
		return s!=null && !"".equals(s) && !"null".equals(s);
	}
	
	/**
	 * 检测字符串是否为空(null,"","null")
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s){
		return s==null || "".equals(s) || "null".equals(s);
	}
	
	/**
	 * 字符串转换为字符串数组
	 * @param str 字符串
	 * @param splitRegex 分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str,String splitRegex){
		if(isEmpty(str)){
			return null;
		}
		return str.split(splitRegex);
	}
	
	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * @param str	字符串
	 * @return
	 */
	public static String[] str2StrArray(String str){
		return str2StrArray(str,",\\s*");
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date){
		return date2Str(date,"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date){
		if(notEmpty(date)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return new Date();
		}else{
			return null;
		}
	}
	
	/**
	 * 按照参数format的格式，日期转字符串
	 * @param date
	 * @param format
	 * 
	 * @return
	 */
	public static String date2Str(Date date,String format){
		if(date!=null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}else{
			return "";
		}
	}
	
	/**
	 * 把时间根据时、分、秒转换为时间段
	 * @param StrDate
	 */
	public static String getTimes(String StrDate){
		String resultTimes = "";
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date now;
	    
	    try {
	    	now = new Date();
	    	Date date=df.parse(StrDate);
	    	long times = now.getTime()-date.getTime();
	    	long day  =  times/(24*60*60*1000);
	    	long hour = (times/(60*60*1000)-day*24);
	    	long min  = ((times/(60*1000))-day*24*60-hour*60);
	    	long sec  = (times/1000-day*24*60*60-hour*60*60-min*60);
	        
	    	StringBuffer sb = new StringBuffer();
	    	//sb.append("发表于：");
	    	if(hour>0 ){
	    		sb.append(hour+"小时前");
	    	} else if(min>0){
	    		sb.append(min+"分钟前");
	    	} else{
	    		sb.append(sec+"秒前");
	    	}
	    		
	    	resultTimes = sb.toString();
	    } catch (ParseException e) {
	    	e.printStackTrace();
	    }
	    
	    return resultTimes;
	}
	
	/**
	 * 写txt里的单行内容
	 * @param fileP  文件路径
	 * @param content  写入的内容
	 */
	public static void writeFile(String fileP,String content){
		String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
		filePath = (filePath.trim() + fileP.trim()).substring(6).trim();
		if(filePath.indexOf(":") != 1){
			filePath = File.separator + filePath;
		}
		try {
	        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filePath),"utf-8");      
	        BufferedWriter writer=new BufferedWriter(write);          
	        writer.write(content);      
	        writer.close(); 

	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	  * 验证邮箱
	  * @param email
	  * @return
	  */
	 public static boolean checkEmail(String email){
	  boolean flag = false;
	  try{
	    String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	    Pattern regex = Pattern.compile(check);
	    Matcher matcher = regex.matcher(email);
	    flag = matcher.matches();
	   }catch(Exception e){
	    flag = false;
	   }
	  return flag;
	 }
	
	 /**
	  * 验证手机号码
	  * @param mobileNumber
	  * @return
	  */
	 public static boolean checkMobileNumber(String mobileNumber){
	  boolean flag = false;
	  try{
	    Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
	    Matcher matcher = regex.matcher(mobileNumber);
	    flag = matcher.matches();
	   }catch(Exception e){
	    flag = false;
	   }
	  return flag;
	 }
	 
	 
	/**
	 * 读取txt里的单行内容
	 * @param fileP  文件路径
	 */
	public static String readTxtFile(String fileP) {
		try {
			
			String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
			filePath = filePath.replaceAll("file:/", "");
			filePath = filePath.replaceAll("%20", " ");
			filePath = filePath.trim() + fileP.trim();
			if(filePath.indexOf(":") != 1){
				filePath = File.separator + filePath;
			}
			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { 		// 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
				new FileInputStream(file), encoding);	// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					return lineTxt;
				}
				read.close();
			}else{
				System.out.println("找不到指定的文件,查看此路径是否正确:"+filePath);
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
		}
		return "";
	}
	
	/**
	 * 去除字符串中的回车、换行符、制表符
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	/**
	 * MD5加密
	 * @param key
	 * @return
	 */
	public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
	public static String getJGMC (String jgid){
		if("330105".equals(jgid)){
			return "拱墅区";
		}else if("330105001".equals(jgid)){
			return "米市巷中心";
		}else if("330105002".equals(jgid)){
			return "小河中心";
		}else if("330105005".equals(jgid)){
			return "拱宸桥中心";
		}else if("330105007".equals(jgid)){
			return "大关上塘卫生服务中心";
		}else if("330105102".equals(jgid)){
			return "康桥中心管理员";
		}else if("330105103".equals(jgid)){
			return "半山中心";
		}else if("330105104".equals(jgid)){
			return "祥符中心";
		}else if("330105105".equals(jgid)){
			return "计划生育";
		}else if("330105106".equals(jgid)){
			return "桃源中心管理员";
		}else{
			return jgid;
		}
	}
	
	/**
	 * 获取组织机构代码 为了防止各个区域表结构不一样 以及重复调用，
	 * 故 平台所需要的组织机构代码统一在Tools类中维护 by yuzg at 2019-04-09
	 * @param jgid
	 * @return
	 */
	public static String getOrgCode(String jgid){
		jgid= jgid.trim();
		//拱墅
		 if(jgid.indexOf("330105001")>=0){
			return "470240455";
		}else if(jgid.indexOf("330105002")>=0){
			return "470240447";
		}else if(jgid.indexOf("330105005")>=0){
			return "470241044";
		}else if(jgid.indexOf("330105007")>=0){
			return "470240498";
		}else if(jgid.indexOf("330105102")>=0){
			return "470240471";
		}else if(jgid.indexOf("330105103")>=0){
			return "470240551";
		}else if(jgid.indexOf("330105104")>=0){
			return "470270304";
		}else if(jgid.indexOf("330105105")>=0){
			return "470241810";
		}else if(jgid.indexOf("330105106")>=0){
			return "PDY102438";
		}else if(jgid.indexOf("330102004")>=0){//上城
			return "470155103";
		}else if(jgid.indexOf("330102009")>=0){
			return "470155111";
		}else if(jgid.indexOf("330102008")>=0){
			return "470211099";
		}else if(jgid.indexOf("330102003")>=0){
			return "470155031";
		}else if(jgid.indexOf("330102001")>=0){
			return "470155058";
		}else if(jgid.indexOf("330102002")>=0){
			return "PDY100010";
		}else if(jgid.indexOf("330108003")>=0){//滨江
			return "470454322";
		}else if(jgid.indexOf("330108002")>=0){
			return "470454314";
		}else if(jgid.indexOf("330108001")>=0){
			return "470454306";
		}/*else if(jgid.equals("1")){   //临安去区属医院
			return "470362276";
		}else if(jgid.equals("2")){
			return "470362305";
		}else if(jgid.equals("3")){
			return "470362268";
		}else if(jgid.equals("4")){
			return "PDY900448";
		}else if(jgid.equals("6")){
			return "470362321";
		}else if(jgid.equals("7")){
			return "470364351";
		}*/
		 else if(jgid.equals("1")){   //临安去社区
			return "470364052";
		}else if(jgid.equals("2")){
			return "PDY705562";
		}else if(jgid.equals("3")){
			return "741031074";
		}else if(jgid.equals("4")){
			return "470364087";
		}else if(jgid.equals("5")){
			return "PDY000028";
		}else if(jgid.equals("6")){
			return "727200679";
		}else if(jgid.equals("7")){
			return "741039260";
		}else if(jgid.equals("8")){
			return "741030047";
		}else if(jgid.equals("9")){
			return "470364108";
		}else if(jgid.equals("10")){
			return "470364183";
		}else if(jgid.equals("11")){
			return "47036226H";
		}else if(jgid.equals("12")){
			return "47036226G";
		}else if(jgid.equals("13")){
			return "47036226B";
		}else if(jgid.equals("14")){
			return "47036226D";
		}else if(jgid.equals("15")){
			return "47036226C";
		}else if(jgid.equals("16")){
			return "470364271";
		}else if(jgid.equals("17")){
			return "47036226F";
		}else if(jgid.equals("18")){
			return "470364386";
		}else if(jgid.equals("19")){
			return "741040691";
		}else if(jgid.equals("20")){
			return "470364378";
		}else if(jgid.equals("21")){
			return "741036713";
		}else if(jgid.equals("22")){
			return "741046778";
		}else if(jgid.equals("23")){
			return "47036230D";
		}else if(jgid.equals("24")){
			return "PDY006163";
		}else if(jgid.equals("25")){
			return "PDY006163";
		}else if(jgid.equals("26")){
			return "470362372";
		}else if(jgid.equals("28")){
			return "470362321";
		}else if(jgid.equals("29")){
			return "470364351";
		}
		else if(jgid.indexOf("30")>=0){
			return "";
		}
		/*else if(jgid.equals("1")){   //临安区民营医院
			return "470362276";
		}else if(jgid.equals("2")){
			return "470362305";
		}else if(jgid.equals("3")){
			return "470362268";
		}else if(jgid.equals("4")){
			return "PDY900448";
		}else if(jgid.equals("6")){
			return "470362321";
		}else if(jgid.equals("7")){
			return "470364351";
		}*/
		else{ 
			return "传入的机构代码："+jgid+"找不到有效的组织机构代码";
		}
	}
	
	/**
	 * 获取组织机构名称
	 * @param jgid
	 * @return
	 */
	public static String getOrgName(String jgid){
		 if(jgid.indexOf("330105001")>=0){
			return "米市巷中心";
		}else if(jgid.indexOf("330105002")>=0){
			return "小河中心";
		}else if(jgid.indexOf("330105005")>=0){
			return "拱宸桥中心";
		}else if(jgid.indexOf("330105007")>=0){
			return "大关上塘卫生服务中心";
		}else if(jgid.indexOf("330105102")>=0){
			return "康桥中心管理员";
		}else if(jgid.indexOf("330105103")>=0){
			return "半山中心";
		}else if(jgid.indexOf("330105104")>=0){
			return "祥符中心";
		}else if(jgid.indexOf("330105105")>=0){
			return "计划生育";
		}else if(jgid.indexOf("330105106")>=0){
			return "桃源中心";
		}else if(jgid.indexOf("330102001")>=0){//上城
			return "清波街道社区卫生服务中心";
		}else if(jgid.indexOf("330102001001")>=0){
			return "中国美院南山医务室";
		}else if(jgid.indexOf("330102002")>=0){
			return "望江街道社区卫生服务中心";
		}else if(jgid.indexOf("330102003")>=0){
			return "湖滨街道社区卫生服务中心";
		}else if(jgid.indexOf("330102004")>=0){
			return "小营街道社区卫生服务中心";
		}else if(jgid.indexOf("330102008")>=0){
			return "南星街道社区卫生服务中心";
		}else if(jgid.indexOf("330102009")>=0){
			return "紫阳街道社区卫生服务中心";
		}else if(jgid.indexOf("330102010")>=0){
			return "上城区妇幼保健计划生育服务中心";
		}else if(jgid.indexOf("330108001")>=0){//滨江
			return "西兴街道社区卫生服务中心";
		}else if(jgid.indexOf("330108002")>=0){
			return "长河街道社区卫生服务中心";
		}else if(jgid.indexOf("330108003")>=0){
			return "浦沿街道社区卫生服务中心";
		}/*else if(jgid.equals("1")){   //临安区区属医院
			return "杭州市临安区中医院";
		}else if(jgid.equals("2")){
			return "杭州市临安区第四人民医院";
		}else if(jgid.equals("3")){
			return "杭州市临安区第三人民医院";
		}else if(jgid.equals("4")){
			return "临安区第五人民医院";
		}else if(jgid.equals("6")){
			return "临安区口腔医院";
		}else if(jgid.equals("7")){
			return "临安区昌化中医骨伤医院";
		}*/
		 else if(jgid.equals("1")){   //临安去社区
			return "临安区锦城街道社区卫生服务中心";
		}else if(jgid.equals("2")){
			return "临安区锦南街道社区卫生服务中心";
		}else if(jgid.equals("3")){
			return "临安区玲珑街道社区卫生服务中心";
		}else if(jgid.equals("4")){
			return "临安区青山湖街道社区卫生服务中心";
		}else if(jgid.equals("5")){
			return "临安区青山湖街道横畈社区卫生服务中心";
		}else if(jgid.equals("6")){
			return "临安区高虹镇中心卫生院";
		}else if(jgid.equals("7")){
			return "临安区太湖源镇中心卫生院";
		}else if(jgid.equals("8")){
			return "临安区板桥镇中心卫生院";
		}else if(jgid.equals("9")){
			return "临安区板桥镇三口卫生院";
		}else if(jgid.equals("10")){
			return "临安区於潜镇中心卫生院";
		}else if(jgid.equals("11")){
			return "临安区於潜镇千洪卫生院";
		}else if(jgid.equals("12")){
			return "临安区天目山镇中心卫生院";
		}else if(jgid.equals("13")){
			return "临安区天目山镇西天目卫生院";
		}else if(jgid.equals("14")){
			return "临安区太阳镇中心卫生院";
		}else if(jgid.equals("15")){
			return "临安区太阳镇横路卫生院";
		}else if(jgid.equals("16")){
			return "临安区潜川镇中心卫生院";
		}else if(jgid.equals("17")){
			return "临安区潜川镇乐平卫生院";
		}else if(jgid.equals("18")){
			return "临安区昌化镇中心卫生院";
		}else if(jgid.equals("19")){
			return "临安区河桥镇中心卫生院";
		}else if(jgid.equals("20")){
			return "临安区湍口镇中心卫生院";
		}else if(jgid.equals("21")){
			return "临安区龙岗镇中心卫生院";
		}else if(jgid.equals("22")){
			return "临安区清凉峰镇中心卫生院";
		}else if(jgid.equals("23")){
			return "临安区清凉峰镇马啸卫生院";
		}else if(jgid.equals("24")){
			return "临安区岛石镇新桥卫生院";
		}else if(jgid.equals("25")){
			return "临安区昌北人民医院";
		}else if(jgid.equals("26")){
			return "临安区妇幼保健和计划生育中心";
		}else if(jgid.equals("27")){
			return "临安区安康医院";
		}else if(jgid.equals("28")){
			return "临安区口腔医院";
		}else if(jgid.equals("29")){
			return "临安区昌化中医骨伤医院";
		}else if(jgid.indexOf("30")>=0){
			return "临安区锦北街道社区卫生服务中心";
		}
		else{
			return "传入的机构代码："+jgid+"找不到有效的组织机构名称";
		}
	}
	
	
	/**
	 * 获取组织机构 对应的财政代码
	 * @param jgid
	 * @return
	 */
	public static String getCZCode(String jgid){
		 if(jgid.indexOf("330105001")>=0){
				return "5001";
			}else if(jgid.indexOf("330105002")>=0){
				return "5002";
			}else if(jgid.indexOf("330105005")>=0){
				return "5003";
			}else if(jgid.indexOf("330105007")>=0){
				return "5007";
			}else if(jgid.indexOf("330105102")>=0){
				return "5006";
			}else if(jgid.indexOf("330105103")>=0){
				return "5005";
			}else if(jgid.indexOf("330105104")>=0){
				return "5004";
			}else if(jgid.indexOf("330105105")>=0){
				return "5010";
			}else if(jgid.indexOf("330105106")>=0){
				return "桃源中心";
			}
		 return "";
	}
	
	public static void main(String[] args) {
		System.out.println(getRandomNum());
		System.out.println(getUUID());
		boolean isOk =urlIsReach("http://192.46.32.145:80/hzdwzszf/axqpmainservice");
		System.out.println("isOK?:"+isOk);
	}
	/***
	 * 拼装报文信息
	 * 必要字段！！！
	 * sourceorgan（用于存储服务消费方所在机构编码）  
	 * sourcedomain（用于存储服务消费方所使用的接入系 统编码） 
	 * condition(条件) 
	 * businessdata（用于装载服务提供方所要求的入参业务数据）
	 * @param paramsMessage
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getPublicMessage(Map<String, Object> paramsMessage) throws Exception{
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> message = new HashMap<String, Object>();
		//Map<String, Object> heartbeat = new HashMap<String, Object>();
		if(paramsMessage.get("paramsMessage")!=null){
			message.put("heartbeat", paramsMessage.get("paramsMessage"));
		}else{
			message.put("heartbeat", 0);
		}
		Map<String, Object> switchset = new HashMap<String, Object>();
		Map<String, Object> authority = new HashMap<String, Object>();
		if(paramsMessage.get("authoritytype")!=null){
			authority.put("authoritytype", paramsMessage.get("authoritytype"));
		}else{
			authority.put("authoritytype", "0");
		}
		//用于存储服务提供方要求的用户名， 
		if(paramsMessage.get("username")!=null){
			authority.put("username", paramsMessage.get("username"));
		}else{
			authority.put("username", "");
		}
		//密码
		if(paramsMessage.get("userpwd")!=null){
			authority.put("userpwd", paramsMessage.get("userpwd"));
		}else{
			authority.put("userpwd", "");
		}
		// 用于存储服务提供方要求的授权码
		if(paramsMessage.get("license")!=null){
			authority.put("license", paramsMessage.get("license"));
		}else{
			authority.put("license", "");
		}
		Map<String, Object> visitor = new HashMap<String, Object>();// 用于存储服务消费方信息
		if(paramsMessage.get("sourceorgan")==null){
			throw new Exception("sourceorgan字段不能为空");
		}
		visitor.put("sourceorgan", paramsMessage.get("sourceorgan"));//用于存储服务消费方所在机构编码
		if(paramsMessage.get("sourcedomain")==null){
			throw new Exception("sourcedomain字段不能为空");
		}
		visitor.put("sourcedomain", paramsMessage.get("sourcedomain"));//用于存储服务消费方所使用的接入系 统编码
		
		Map<String, Object> serviceinf = new HashMap<String, Object>();//用于存储请求的服务信息
		if(paramsMessage.get("servicecode")==null){
			throw new Exception("servicecode字段不能为空");
		}
		serviceinf.put("servicecode", paramsMessage.get("servicecode"));//用于存储请求的服务在服务注册中心 的唯一服务编码
		
		Map<String, Object> provider = new HashMap<String, Object>();//用于存储服务提供方的信息
		//用于存储服务提供方所在的机构编码
		if(paramsMessage.get("targetorgan")!=null){
			provider.put("targetorgan", paramsMessage.get("targetorgan"));
		}else{
			provider.put("targetorgan", "");
		}
		//用于存储服务提供方所在的接入系统
		if(paramsMessage.get("targetdomain")!=null){
			provider.put("targetdomain", paramsMessage.get("targetdomain"));
		}else{
			provider.put("targetdomain", "");
		}
		
		switchset.put("authority", authority);
		switchset.put("visitor", visitor);
		switchset.put("provider", provider);
		switchset.put("serviceinf", serviceinf);
		//用于记录服务路由信息
		if(paramsMessage.get("route")!=null){
			switchset.put("route", paramsMessage.get("route"));
		}else{
			switchset.put("route", "");//
		}
		//用于记录服务流程编排相关信息
		if(paramsMessage.get("process")!=null){
			switchset.put("process", paramsMessage.get("process"));
		}else{
			switchset.put("process", "");//
		}
		
		Map<String, Object> business = new HashMap<String, Object>();//用于装载服务提供者需要的入参数据
		if(paramsMessage.get("standardcode")!=null){
			business.put("standardcode", paramsMessage.get("standardcode"));
		}else{
			business.put("standardcode", "");//
		}
		Map<String, Object> requestset = new HashMap<String, Object>();//用于定义数据请求时数据查询条件和返回数据的分页设置参数 
		Map<String, Object> reqcondition = new HashMap<String, Object>();//用于记录用于数据查询的条件
		//单条数据查询条件
		if(paramsMessage.get("condition")!=null){
			//throw new HISHZYYHException("condition字段不能为空");
			reqcondition.put("condition", paramsMessage.get("condition"));
		}else{
			reqcondition.put("condition", "");
		}
		requestset.put("reqcondition", reqcondition);
		//用于定义返回数据时是否进行分页处 
		if(paramsMessage.get("reqpaging")!=null){
			requestset.put("reqpaging", paramsMessage.get("reqpaging"));
		}else{
			requestset.put("reqpaging", 0);//
		}
		// 用于定义返回分页数据时返回的页索 
		if(paramsMessage.get("reqpageindex")!=null){
			requestset.put("reqpageindex", paramsMessage.get("reqpageindex"));
		}else{
			requestset.put("reqpageindex", -1);//
		}
		// 用于定义返回分页数据时返回的页索 
		if(paramsMessage.get("reqpageset")!=null){
			requestset.put("reqpageset", paramsMessage.get("reqpageset"));
		}else{
			requestset.put("reqpageset", 0);//
		}
		
		
		Map<String,Object> datacompress = new HashMap<String, Object>();
		// 用于定义服务提供方要求的入参业务 
		if(paramsMessage.get("datacompress")!=null){
			business.put("datacompress", paramsMessage.get("datacompress"));
		}else{
			business.put("datacompress", 0);//
		}
		Map<String,Object> daqtaskid = new HashMap<String, Object>();
		// 用于定义服务提供方要求的入参业务 
		if(paramsMessage.get("daqtaskid")!=null){
			business.put("daqtaskid", paramsMessage.get("daqtaskid"));
		}else{
			business.put("daqtaskid", "");//
		}
		Map<String, Object> datasets = new HashMap<String, Object>();
		datasets.put("setcode", "");
		datasets.put("settype", "");
		datasets.put("setdetails", paramsMessage.get("setdetails"));
		Map<String, Object> businessdata = new HashMap<String, Object>();
		businessdata.put("datasets", datasets);
		/*if(paramsMessage.get("businessdata")==null){
			throw new HISHZYYHException("businessdata字段不能为空");
		}*/
		business.put("requestset", requestset);
		//business.put("datacompress", datacompress);
		//business.put("daqtaskid", daqtaskid);
		business.put("businessdata", businessdata);
		
		//message.put("heartbeat", heartbeat);
		message.put("switchset", switchset);
		message.put("business", business);
		message.put("extendset", "");
		return message;
	}
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	public static String getErrorMsg(Exception e){
		Map<String, Object> errorMap = new HashMap<String, Object>();
		getAllMsgFromThrowable(e, errorMap);
		String exceptionError = "";
		for(String key:errorMap.keySet()){
			exceptionError+=errorMap.get(key);
		}
		Map<String, Object> res_map = new HashMap<String, Object>();
		res_map.put("code", -1);
		res_map.put("message",exceptionError);
		return XMLUtil.mapParseXML(res_map, "interface");
	}
	
	/**
	 * 获取异常中所有的错误
	 * @param t
	 * @return
	 * @author yuzg
	 * @time 2018-12-21下午12:08:24
	 * @description
	 */
	private static void getAllMsgFromThrowable(Throwable t,Map<String, Object> map){
		if(t.getCause() != null){
			map.put(t.getCause().toString(),  t.getCause().getMessage());
			getAllMsgFromThrowable(t.getCause(), map);
		}else{
			map.put(t.getMessage(),  t.getMessage());
		}
	}
	
	
	/**
	 * url是否已经连接
	 * @param url
	 * @return
	 */
	public static boolean urlIsReach(String url) {
		if (url==null) {
			return false;
		}
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			//设置如果两秒不能返回连接信息 默认连接失败 by yuzg
			connection.setReadTimeout(2000);
			if (HttpURLConnection.HTTP_OK==connection.getResponseCode()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	/**
	 * 转int 类型
	 * @param o
	 * @return
	 */
	public static int parseInt(Object o ){
		if (o == null || "".equals(o)) {
			return 0;
		}
		return Integer.parseInt(o + "");
	}
	/**
	 * 转long 型
	 * @param o
	 * @return
	 */
	public static long parseLong(Object o ){
		if (o == null || "".equals(o)) {
			return new Long(0);
		}
		return Long.parseLong(o + "");
	}
	/**
	 * 获取所有的错误Str
	 * @description
	 * @param e
	 * @return
	 */
	public static String getErrorMsgStr (Exception e){
		Map<String, Object> errorMap = new HashMap<String, Object>();
		getAllMsgFromThrowable(e, errorMap);
		String exceptionError = "";
		for(String key:errorMap.keySet()){
			exceptionError+=errorMap.get(key);
		}
		return exceptionError;
	}
	 public static String getDateTime() {
			Date date = new Date();
			SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return matter.format(date);
		}
	 
		/**
		 * 获得指定位数的随机数
		 * @param length
		 * @return
		 * @author yuzg
		 * @time 2019-6-5上午10:27:28
		 * @description
		 */
		public static String getRandom(int length){
			String val = "";
			Random random = new Random();
			for (int i = 0; i < length; i++) {
				val += String.valueOf(random.nextInt(10));
			}
			return val;
		}
		
}
