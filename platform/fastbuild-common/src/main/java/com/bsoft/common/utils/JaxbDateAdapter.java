package com.bsoft.common.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JaxbDateAdapter extends XmlAdapter<String, Date> {

    static final String DATE_FORMAT= "yyyyMMdd";
    static final String IG = "yyyy";

    @Override
    public Date unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        }
        DateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        DateFormat format = new SimpleDateFormat(DATE_FORMAT);
        if ("0001".equals(new SimpleDateFormat(IG).format(v))) {
            return "";
        }
        return format.format(v);
    }


}
