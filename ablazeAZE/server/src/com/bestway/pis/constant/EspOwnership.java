package com.bestway.pis.constant;

import java.io.Serializable;

/**
 * 平台归属者 0 进出口企业 ； 1 代理公司
 *
 * @author Administrator
 */
public class EspOwnership implements Serializable{

    public static String ENT = "0";

    public static String AGT = "1";

    public static String getNameByCode(String code) {
        if (code.equals(ENT)) {
            return "进出口企业";
        } else if (code.equals(AGT)) {
            return "代理公司";
        }
        return "";
    }

    public static String getCodeByName(String name) {
        if (name.equals("进出口企业")) {
            return ENT;
        } else if (name.equals("代理公司")) {
            return AGT;
        }
        return "";
    }
}
