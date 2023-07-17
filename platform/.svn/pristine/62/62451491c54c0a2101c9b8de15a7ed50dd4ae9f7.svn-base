package com.bsoft.system.domain.yyh;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

public class MyXmlUtils {
    /**
     * @param map
     * @return
     */
    public static final String getXmlFromMapNoTag(Map<String, Object> map,String root){
        Document document = DocumentHelper.createDocument();
        Element Response = document.addElement(root);
        objectMapToXml(map,Response);
        return document.getRootElement().asXML();
    }

    /**
     * 解析多层循环xml，返回map
     * @param xml
     * @return
     */
    public static HashMap<String, Object> pareXmlToMuiltTrueMap(String xml) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            Document doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();
            xmlToMuitMapS(root, map);
        } catch (Exception e) {
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    private static final void objectMapToXml(Map<String, Object> data, Element el) {
        for(Map.Entry<String,Object> et : data.entrySet()){
            Object value = et.getValue();
            if(null == value){
                continue;
            }else if (value instanceof List) {
                //存在list下内容，不是map的情况
                List<Object> list_temp =  (List<Object>)value;
                if(list_temp.isEmpty()){
                    continue;
                }
                listToElement(et.getKey(), list_temp, el);
            } else if (value instanceof Map) {
                Element sub = el.addElement(et.getKey());
                objectMapToXml((Map<String, Object>) value, sub);
            } else {
                Element sub = el.addElement(et.getKey());
                sub.setText(String.valueOf(et.getValue()));
            }
        }
    }
    /**
     *
     * @param preName
     * @param data
     * @param el
     */
    private static final void listToElement(String preName,List<Object> data, Element el) {
        for (Object o : data) {
            Element sub = el.addElement(preName);
            if (o instanceof Map) {
                objectMapToXml((Map<String, Object>) o, sub);
            } else {
                //当list中不是map时，默认处理方法：preName加个s，继续解析
                Map<String, Object> kk = new HashMap<String, Object>();
                kk.put(preName+"s", o);
                objectMapToXml(kk, sub);
            }
        }
    }
    private static final void xmlToMuitMapS(Element ee, Map<String, Object> map) {
        // 如果是叶节点，则添加结果
        if (ee.isTextOnly()) {
            String value = ee.getText();
            map.put(ee.getName().trim(), value.isEmpty() ? "" : value.trim());
        } else {
            List<Element> elist = ee.elements();
            List<Map<String, Object>> sub_list = new ArrayList<Map<String, Object>>();
            for(Element sub_e : elist){
                if(sub_e.elements().size()>0){
                    LinkedHashMap<String, Object> s_map = new LinkedHashMap<String, Object>();
                    LinkedHashMap<String, Object> sub_map = new LinkedHashMap<String, Object>();
                    xmlToMuitMapS(sub_e,sub_map);
                    s_map.put(sub_e.getName(),sub_map);
                    sub_list.add(s_map);
                }else{
                    xmlToMuitMapS(sub_e,map);
                }
            }
            //将子集放入最初的节点名上
            if (!sub_list.isEmpty()) {
                map.put(ee.getName(), sub_list);
            }
        }
    }

}
