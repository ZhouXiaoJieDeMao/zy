package com.bsoft.common.utils;

import java.math.BigDecimal;

public class NumberUtil {

    public static Double getDoubleNumberWith2(Double number) {
        if (number == null) return 0D;
        BigDecimal bigDecimal = new BigDecimal(number);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double getDoubleNumberWith4(Double number) {
        if (number == null) return 0D;
        BigDecimal bigDecimal = new BigDecimal(number);
        return bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double getDoubleNumberWith3(Double number) {
        if (number == null) return 0D;
        BigDecimal bigDecimal = new BigDecimal(number);
        return bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double getDoubleNumberWith5(Double number) {
        if (number == null) return 0D;
        BigDecimal bigDecimal = new BigDecimal(number);
        return bigDecimal.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
