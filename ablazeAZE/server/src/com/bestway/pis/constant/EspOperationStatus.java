package com.bestway.pis.constant;

/**
 *
 * @author GDC
 */
public class EspOperationStatus {

    /**
     * 查看
     */
    public static final String BROWSE = "0";

    /**
     * 新增
     */
    public static final String ADD = "1";

    /**
     * 修改
     */
    public static final String EDIT = "2";

    public static String getNameByCode(String value) {
        String desc = "";
        if (EspOperationStatus.BROWSE.equals(value)) {
            desc = "查看";
        } else if (EspOperationStatus.ADD.equals(value)) {
            desc = "新增";
        } else if (EspOperationStatus.EDIT.equals(value)) {
            desc = "修改";
        }
        return desc;
    }

    public static String getCodeByName(String value) {
        String status = "";
        if ("查看".equals(value)) {
            status = EspOperationStatus.BROWSE;
        } else if ("新增".equals(value)) {
            status = EspOperationStatus.ADD;
        } else if ("修改".equals(value)) {
            status = EspOperationStatus.EDIT;
        }
        return status;
    }
}
