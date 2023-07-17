package com.bsoft.common.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class JaxbDoubleAdapter extends XmlAdapter<String, Double> {

    @Override
    public Double unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        }
        return Double.valueOf(v);
    }

    @Override
    public String marshal(Double v) throws Exception {
        if (Double.MAX_VALUE == v) {
            return "";
        }
        try {
            return NumberUtil.getDoubleNumberWith2(v).toString();
        } catch (Exception e) {
            return "0";
        }
    }
}
