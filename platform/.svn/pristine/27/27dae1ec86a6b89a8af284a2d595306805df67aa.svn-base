package com.bsoft.common.utils;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Map对象
 * 实体相互转换
 */
public class BeanMapUtils {


    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;
        Object obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                continue;
            }

            //clob转bean
            field.setAccessible(true);
            if(map.get(field.getName()) instanceof java.sql.Clob){
                field.set(obj, ClobToString((java.sql.Clob)map.get(field.getName()) ));
            }else if(map.get(field.getName()) instanceof BigDecimal){
                BigDecimal c = (BigDecimal)map.get(field.getName());
                if(field.getType().getName().indexOf("Long")>-1) {
                    Long l = c.longValue();
                    field.set(obj, l);
                }else if(field.getType().getName().indexOf("Integer")>-1){
                    Integer l = c.intValue();
                    field.set(obj, l);
                }
            }else{
                field.set(obj, map.get(field.getName()));
            }
        }

        return obj;
    }


    /**
     * clob 转 String
     * @param clob
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static String ClobToString(Clob clob) throws SQLException,
            IOException {
        String reString = null;
        if (clob == null) {
            return null;
        }
        Reader inStreamDoc = clob.getCharacterStream();
        char[] tempDoc = new char[(int) clob.length()];
        inStreamDoc.read(tempDoc);
        inStreamDoc.close();
        reString = new String(tempDoc);

        return reString;
    }

    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }

    /**
     * clob 转 String
     * @param list
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public static List<Map<String,Object>> ClobToString1(List<Map<String,Object>> list) throws IOException, SQLException {
        for(Map map : list){
            Iterator entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                if(entry.getValue() instanceof java.sql.Clob){
                    map.put(entry.getKey(),ClobToString((java.sql.Clob)entry.getValue()));
                }
            }
        }
        return list;
    }

    /**
     * clob 转 String
     * @param map
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public static Map ClobToString(Map map) throws IOException, SQLException {
        Iterator entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            if(entry.getValue() instanceof java.sql.Clob){
                map.put(entry.getKey(),ClobToString((java.sql.Clob)entry.getValue()));
            }
        }
        return map;
    }
}
