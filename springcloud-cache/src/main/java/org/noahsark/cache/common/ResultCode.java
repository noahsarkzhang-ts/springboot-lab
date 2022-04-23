package org.noahsark.cache.common;

import java.io.Serializable;

/**
 * @author haoxr
 * @date 2020-06-23
 **/
public enum ResultCode implements IResultCode, Serializable {

    // 成功
    SUCCESS("0", "success"),
    FAIL("1", "System Fail");

    ResultCode() {
    }

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    private String code;

    private String msg;

    @Override
    public String toString() {
        return "{"
                + "\"code\":\"" + code + '\"'
                + ", \"msg\":\"" + msg + '\"'
                + '}';
    }


    public static ResultCode getValue(String code) {
        for (ResultCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }

        // 默认系统执行错误
        return FAIL;
    }
}
