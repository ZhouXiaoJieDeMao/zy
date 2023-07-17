package com.bsoft.common.enums.drgs;

/**
 * drgs数据状态类型
 */
public enum DrgsStatus {

     LOCAL_JGID("1"),
     REQ_INFNO("infno"),
     REQ_SIGN_NO("sign_no"),
     REQ_OPTER_NO("opter_no"),
     REQ_INPUT_KEY("input"),
     //------------------
     RES_CODE_KEY("infcode"),
     RES_CODE_SUCCESS("0"),
     RES_ERR_MSG("err_msg"),
     RES_OUTPUT_KEY("output"),
     MAZH_CONECT("|"),
     MAZH_MAIN("main"),
     ERROR_RES("error: "),
     TIMECOLUMN ("TIMECOLUMN"),
     TIMECOLUMN_NULL("无");

    private final String code;

    DrgsStatus(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

}
