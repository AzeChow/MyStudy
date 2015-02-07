package com.bestway.pis.constant;

import java.io.Serializable;

/**
 * 同步状态
 *
 * @author sjl
 */
public class PisSyncState implements Serializable {

    public static String ERROR = "0";

    public static String SUCCEED = "1";

    public static String NOT_SYNC = "2";

    public static String getNameByCode(String code) {
        if (code.equals(ERROR)) {
            return "同步失败";
        } else if (code.equals(SUCCEED)) {
            return "同步成功";
        } else if (code.equals(NOT_SYNC)) {
            return "未同步";
        }
        return "";
    }

    public static String getCodeByName(String name) {
        if (name.equals("同步失败")) {
            return ERROR;
        } else if (name.equals("同步成功")) {
            return SUCCEED;
        } else if (name.equals("未同步")) {
            return NOT_SYNC;
        }
        return "";
    }
}
