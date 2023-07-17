package com.bsoft.common.ws.listener;

import javax.xml.bind.Marshaller;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

public class MarshallerListener extends Marshaller.Listener {

    public static final String BLANK_CHAR = "";
    public static final Integer BLANK_NUMBER = Integer.MAX_VALUE;
    public static final Double REPLACE_DOUBLE = Double.MAX_VALUE;
    static Calendar calendar = null;
    static {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 0);
    }
    public static final Date BLANK_DATE = calendar.getTime();

    @Override
    public void beforeMarshal(Object source) {
        super.beforeMarshal(source);
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                // 填充未赋值字段
                if (f.getType() == String.class && f.get(source) == null) {
                    f.set(source, BLANK_CHAR);
                }
                if (f.getType() == Date.class && f.get(source) == null) {
                    f.set(source, BLANK_DATE);
                }
                if (f.getType() == Double.class && f.get(source) == null) {
                    f.set(source, REPLACE_DOUBLE);
                }
                if (f.getType() == Integer.class && f.get(source) == null) {
                    f.set(source, BLANK_NUMBER);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
