package com.bestway.pis.constant;

/**
 * 主营业务类型
 *
 * @author Administrator
 */
public class EspMainBusinessType {

    /**
     * 特殊监管仓
     */
    public static final String BUSINESS_TYPE_SSZ = "YWLX001";

    /**
     * 备案资料库备案、变更
     */
    public static final String BUSINESS_TYPE_DICT = "YWLX002";

    /**
     * 通关手册备案、变更
     */
    public static final String BUSINESS_TYPE_CONTRACT = "YWLX003";

    /**
     * 出口报关、报检
     */
    public static final String BUSINESS_TYPE_EXP = "YWLX004";
    /**
     * 进口报关、报检
     */
    public static final String BUSINESS_TYPE_IMP = "YWLX005";
    /**
     * 合同核销
     */
    public static final String BUSINESS_TYPE_CAV = "YWLX006";

    /**
     * 一般贸易
     */
    public static final String BUSINESS_TYPE_GENERALTRADE = "YWLX007";

    /**
     * 深加工业务
     */
    public static final String BUSINESS_TYPE_FPT = "YWLX008";

    /**
     * 获取主营业务名称
     *
     * @param code
     * @return
     */
    public static String getNameByCode(String code) {
        if (BUSINESS_TYPE_SSZ.equals(code)) {
            return "特殊监管仓";
        } else if (BUSINESS_TYPE_DICT.equals(code)) {
            return "备案资料库备案、变更";
        } else if (BUSINESS_TYPE_CONTRACT.equals(code)) {
            return "通关手册备案、变更";
        } else if (BUSINESS_TYPE_EXP.equals(code)) {
            return "出口报关、报检";
        } else if (BUSINESS_TYPE_IMP.equals(code)) {
            return "进口报关、报检";
        } else if (BUSINESS_TYPE_CAV.equals(code)) {
            return "合同核销";
        } else if (BUSINESS_TYPE_GENERALTRADE.equals(code)) {
            return "一般贸易";
        } else if (BUSINESS_TYPE_FPT.equals(code)) {
            return "深加工业务";
        }
        return "";
    }

    /**
     * 获取主营业务类型代码
     *
     * @param name
     * @return
     */
    public static String getCodeByName(String name) {
        if ("特殊监管仓".equals(name)) {
            return BUSINESS_TYPE_SSZ;
        } else if ("备案资料库备案、变更".equals(name)) {
            return BUSINESS_TYPE_DICT;
        } else if ("通关手册备案、变更".equals(name)) {
            return BUSINESS_TYPE_CONTRACT;
        } else if ("出口报关、报检".equals(name)) {
            return BUSINESS_TYPE_EXP;
        } else if ("进口报关、报检".equals(name)) {
            return BUSINESS_TYPE_IMP;
        } else if ("合同核销".equals(name)) {
            return BUSINESS_TYPE_CAV;
        } else if ("一般贸易".equals(name)) {
            return BUSINESS_TYPE_GENERALTRADE;
        } else if ("深加工业务".equals(name)) {
            return BUSINESS_TYPE_FPT;
        }
        return "";
    }

}
