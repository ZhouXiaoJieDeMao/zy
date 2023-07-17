package com.bsoft.common.utils.drgs;

import java.util.concurrent.atomic.AtomicInteger;

public class NumUtil {
    private static AtomicInteger ai_num = new AtomicInteger(1);

    public static String getOrderNum(){
        String ls_num = String.format("%04d",ai_num.get());
        ai_num.set((ai_num.get()+1)%9999);
        return ls_num;
    }
}
