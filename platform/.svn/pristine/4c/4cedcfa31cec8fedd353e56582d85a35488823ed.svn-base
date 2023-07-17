package com.bsoft.common.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class JaxbIntegerAdapter extends XmlAdapter<String, Integer> {

    @Override
    public Integer unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        }
        return Integer.valueOf(v);
    }

    @Override
    public String marshal(Integer v) throws Exception {
        if (Integer.MAX_VALUE == v) {
            return "";
        }
        return Integer.toString(v);
    }
}