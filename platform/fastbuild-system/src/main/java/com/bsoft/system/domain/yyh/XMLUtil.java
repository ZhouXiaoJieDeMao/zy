package com.bsoft.system.domain.yyh;

import org.dom4j.*;

import java.util.*;

public class XMLUtil {
	/**
	 * map解析成xml格式字符串
	 */
	public static String mapParseXML(Map<String, Object> data, String head) {
		Document doc = DocumentHelper.createDocument();
		Element e = doc.addElement(head);
		mapToElement(data, e);
		//云医平台对axis2支撑不够 必须要是去掉 <?xml version="1.0" encoding="UTF-8"?> 才能通过验证
		return doc.getRootElement().asXML();
	}

	private static void listToElement(String QName,
			List<Map<String, Object>> list, Element el) {
		for (Map<String, Object> map : list) {
			Element sub = el.addElement(QName);
			mapToElement(map, sub);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void mapToElement(Map<String, Object> data, Element el) {
		for (String key : data.keySet()) {
			if("AGGRJGIDM".equals(key)){
				continue;
			}
			Object value = data.get(key);
			if (value == null) {
				value = "";
			}
			if (value instanceof List) {
				listToElement(key, (List) value, el);
			} else if (value instanceof Map) {
				Element sub = el.addElement(key);
				mapToElement((Map) value, sub);
			} else {
				Element sub = el.addElement(key);
				sub.setText(hexValideByByte(String.valueOf(value).getBytes()));
			}
		}
	}

	/**
	 * 将字符串进行字节校验，去除半个字节的数据
	 */
	private static String hexValideByByte(byte[] bytes) {
		StringBuffer sb_hexvalue= new StringBuffer();
		for (byte bb : bytes) {
			String ls_bb = Integer.toHexString(bb & 0xFF);
			//小于1f的，都是控制符，一般不会出来..
			if(ls_bb.length() == 2){
				sb_hexvalue.append(ls_bb);
			}else{
				//如果是制表符，则变成换行，其他不处理
				if(ls_bb.equals("9")){
					sb_hexvalue.append("20");
				}
			}
		}
		String ls_hex_value = sb_hexvalue.toString();
		int li_hex_length = ls_hex_value.length();
		//如果是2的倍数，则认为不存在半个字节数据
		if(li_hex_length%2==0){
			return new String(hexToByteArray(ls_hex_value));
		}else{
			ls_hex_value = ls_hex_value.substring(0,li_hex_length-1);
			return new String(hexToByteArray(ls_hex_value));
		}
	}

	private static byte[] hexToByteArray(String inHex) {
		int hexlen = inHex.length();
		byte[] result;
		if (hexlen % 2 == 1) {
			//奇数
			hexlen++;
			result = new byte[(hexlen / 2)];
			inHex = "0" + inHex;
		} else {
			//偶数
			result = new byte[(hexlen / 2)];
		}
		int j = 0;
		for (int i = 0; i < hexlen; i += 2) {
			result[j] = hexToByte(inHex.substring(i, i + 2));
			j++;
		}
		return result;
	}
	private static byte hexToByte(String inHex) {
		return (byte) Integer.parseInt(inHex, 16);
	}
	/**
	 * xml字符串解析成map集合
	 * 
	 * @param s
	 * @return
	 */
	public static Map<String, Object> xmlParseMap(String s) {
		Element root = null;
		try {
			Document doc = DocumentHelper.parseText(s);
			root = doc.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return getText(root, new HashMap<String, Object>());

	}

	private static Map<String, Object> getText(Element e,
			Map<String, Object> map) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		map.put(e.getName(), list);
		Iterator<Element> it = e.elementIterator();
		while (it.hasNext()) {
			Map<String, Object> m = new HashMap<String, Object>();
			Element el = it.next();
			if (el.elements().size() != 0) {
				m = getText(el, m);
			} else {
				m.put(el.getName(), el.getText());
			}
			list.add(m);
		}
		return map;
	}

	/**
	 * xml字符串解析成map集合
	 * 
	 * @param s
	 * @return
	 */
	public static Map<String, Object> xmlParseMap_Map(String s) {
		Element root = null;
		try {
			Document doc = DocumentHelper.parseText(s);
			root = doc.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return getTextMap(root, new HashMap<String, Object>());

	}
	
	private static Map<String, Object> getTextMap(Element e,
			Map<String, Object> map) {
		Map<String, Object> eleMap =new  HashMap<String, Object>();
		map.put(e.getName(), eleMap);
		Iterator<Element> it = e.elementIterator();
		while (it.hasNext()) {
			Map<String, Object> m = new HashMap<String, Object>();
			Element el = it.next();
			if (el.elements().size() != 0) {
				m = getText(el, m);
			} else {
				m.put(el.getName(), el.getText());
			}
			eleMap.putAll(m);
		}
		return map;
	}
	
	
	public static Map<String,Object> xml2map(String xml) throws DocumentException{
		Document doc = DocumentHelper.parseText(xml);
		Element element = doc.getRootElement();
		return (Map<String, Object>) element2map(element);
	}

	public static Object element2map(Element element) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<Element> elements = element.elements();
		if (elements.size() == 0) {
			map.put(element.getName(), element.getText());
			if (!element.isRootElement()) {
				return element.getText();
			}
		} else if (elements.size() == 1) {
			map.put(elements.get(0).getName(), element2map(elements.get(0)));
		} else if (elements.size() > 1) {
			// 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
			// 构造一个map用来去重
			Map<String, Element> tempMap = new HashMap<String, Element>();
			for (Element ele : elements) {
				tempMap.put(ele.getName(), ele);
			}
			Set<String> keySet = tempMap.keySet();
			for (String string : keySet) {
				Namespace namespace = tempMap.get(string).getNamespace();
				List<Element> elements2 = element.elements(new QName(string, namespace));
				// 如果同名的数目大于1则表示要构建list
				if (elements2.size() > 1) {
					List<Object> list = new ArrayList<Object>();
					for (Element ele : elements2) {
						list.add(element2map(ele));
					}
					map.put(string, list);
				} else {
					// 同名的数量不大于1则直接递归去
					map.put(string, element2map(elements2.get(0)));
				}
			}
		}

		return map;
	}

}
