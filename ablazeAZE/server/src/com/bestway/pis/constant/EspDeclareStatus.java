package com.bestway.pis.constant;

public class EspDeclareStatus {
	  /**
     * 全部
     */
    public static final String ALL = "0"; // 全部

    /**
     * 草稿
     */
    public static final String DRAFT = "1"; // 草稿
    /**
     * 服务公司回退
     */
    public static final String AGT_ROLLBACK = "1.5";    //服务公司回退

    /**
     * 已提交,待接单
     */
    public static final String WAIT_ACCEPT = "2"; // 已提交,待接单

    /**
     * 申请撤单
     */
    public static final String APPLY_BACK_APPLY = "2.5";    //申请撤单
    /**
     * 企业撤单
     */
    public static final String APPLY_BACK = "3"; // 企业撤单

    /**
     * 检查中
     */
    public static final String AGT_CHECKING = "3.5"; //检查中

    /**
     * 已接单
     */
    public static final String AGT_ACCEPTED = "4"; // 已接单

    /**
     * 资料检查不通过
     */
    public static final String AGT_BACK = "5"; // 资料检查不通过

    /**
     * 已接单,待分派
     */
    public static final String AGT_WAITASSIGN = "6"; //已接单,待分派

    /**
     * 已分派,待申报海关
     */
    public static final String QP_WAITAPPLY = "7"; // 已分派,待申报海关

    /**
     * 海关审核退单
     */
    public static final String QP_BACK = "8"; // 海关申报退单

    /**
     * 已向海关申报
     */
    public static final String QP_APPLIED = "9";// 已向海关申报

    /**
     * 查车中
     */
    public static final String QP_CHECKVEHICLE = "10"; // 查车中
    /**
     * 递单中
     */
    public static final String QP_DELIVERY = "11";   //递单中    
    /**
     * 已递单,待放行
     */
    public static final String QP_WAITPASS = "12"; // 已递单,待放行
    /**
     * 待退税
     */
    public static final String QP_BACKTAX = "13"; // 待退税
    /**
     * 待付款
     */
    public static final String IS_WAITPAY = "14"; // 待付款

    /**
     * 未付款
     */
    public static final String IS_UNPAY = "15"; // 未付款

    /**
     * 已付款
     */
    public static final String IS_PAYED = "16"; // 已付款

    /**
     * 已完结
     */
    public static final String IS_FINISHED = "17"; // 已完结

    /**
     * 已分派,待对碰
     */
    public static final String ODT_WAIT_RECON = "18"; // 已分派,待对碰

    /**
     * 已对碰成功,待申报海关
     */
    public static final String ODT_QP_WAITAPPLY = "19"; // 已对碰成功,待申报海关

    /**
     * 获取发送状态
     *
     * @param value 发送状态(值)
     * @return
     */
    public static final String getEspDeclareStatusDesc(String value) {
        String returnValue = "";
        if (EspDeclareStatus.ALL.equals(value)) {
            returnValue = "全部";
        } else if (EspDeclareStatus.DRAFT.equals(value)) {
            returnValue = "草稿";
        } else if (EspDeclareStatus.WAIT_ACCEPT.equals(value)) {
            returnValue = "已提交,待接单";
        } else if (EspDeclareStatus.APPLY_BACK.equals(value)) {
            returnValue = "企业撤单";
        } else if (EspDeclareStatus.AGT_ACCEPTED.equals(value)) {
            returnValue = "已接单";
        } else if (EspDeclareStatus.AGT_BACK.equals(value)) {
            returnValue = "资料检查不通过";
        } else if (AGT_WAITASSIGN.equals(value)) {
            returnValue = "已接单,待分派";
        } else if (EspDeclareStatus.QP_WAITAPPLY.equals(value)) {
            returnValue = "已分派,待申报海关";
        } else if (EspDeclareStatus.QP_BACK.equals(value)) {
            returnValue = "海关审核退单";
        } else if (EspDeclareStatus.QP_APPLIED.equals(value)) {
            returnValue = "已向海关申报";
        } else if (EspDeclareStatus.QP_CHECKVEHICLE.equals(value)) {
            returnValue = "查车中";
        } else if (EspDeclareStatus.QP_WAITPASS.equals(value)) {
            returnValue = "已递单,待放行";
        } else if (EspDeclareStatus.QP_BACKTAX.equals(value)) {
            returnValue = "待退税";
        } else if (EspDeclareStatus.IS_WAITPAY.equals(value)) {
            returnValue = "待付款";
        } else if (EspDeclareStatus.IS_UNPAY.equals(value)) {
            returnValue = "未付款";
        } else if (EspDeclareStatus.IS_FINISHED.equals(value)) {
            returnValue = "已完结";
        } else if (EspDeclareStatus.AGT_CHECKING.equals(value)) {
            returnValue = "检查中";
        } else if (EspDeclareStatus.QP_DELIVERY.equals(value)) {
            returnValue = "递单中";
        } else if (EspDeclareStatus.ODT_WAIT_RECON.equals(value)) {
            returnValue = "已分派,待对碰";
        } else if (EspDeclareStatus.ODT_QP_WAITAPPLY.equals(value)) {
            returnValue = "已对碰成功,待申报海关";
        } else if(APPLY_BACK_APPLY.equals(value)){
            returnValue = "申请撤单";
        } else if(AGT_ROLLBACK.equals(value)){
            returnValue = "服务公司回单";
        }
        return returnValue;
    }

    /**
     * 获取发送状态值
     *
     * @param value 发送状态
     * @return 发送状态值
     */
    public static final String getEspDeclareStatusByDesc(String value) {
        String returnValue = "";
        if ("全部".equals(value)) {
            returnValue = EspDeclareStatus.ALL;
        } else if ("草稿".equals(value)) {
            returnValue = EspDeclareStatus.DRAFT;
        } else if ("已提交,待接单".equals(value)) {
            returnValue = EspDeclareStatus.WAIT_ACCEPT;
        } else if ("企业撤单".equals(value)) {
            returnValue = EspDeclareStatus.APPLY_BACK;
        } else if ("已接单".equals(value)) {
            returnValue = EspDeclareStatus.AGT_ACCEPTED;
        } else if ("资料检查不通过".equals(value)) {
            returnValue = EspDeclareStatus.AGT_BACK;
        } else if ("已分派".equals(value)) {
            returnValue = EspDeclareStatus.QP_WAITAPPLY;
        } else if("海关审核退单".equals(value)){
            returnValue = EspDeclareStatus.QP_BACK;
        } else if ("已向海关申报".equals(value)) {
            returnValue = EspDeclareStatus.QP_APPLIED;
        } else if ("查车中".equals(value)) {
            returnValue = EspDeclareStatus.QP_CHECKVEHICLE;
        } else if ("递单中".equals(value)) {
            returnValue = EspDeclareStatus.QP_DELIVERY;
        } else if ("已递单,待放行".equals(value)) {
            returnValue = EspDeclareStatus.QP_WAITPASS;
        } else if ("待退税".equals(value)) {
            returnValue = EspDeclareStatus.QP_BACKTAX;
        } else if ("已付款".equals(value)) {
            returnValue = EspDeclareStatus.IS_PAYED;
        } else if ("未付款".equals(value)) {
            returnValue = EspDeclareStatus.IS_UNPAY;
        } else if ("已完结".equals(value)) {
            returnValue = EspDeclareStatus.IS_FINISHED;
        } else if ("检查中".equals(value)) {
            returnValue = EspDeclareStatus.AGT_CHECKING;
        } else if ("已接单,待分派".equals(value)) {
            returnValue = AGT_WAITASSIGN;
        } else if ("已分派,待对碰".equals(value)) {
            returnValue = ODT_WAIT_RECON;
        } else if ("已对碰成功,待申报海关".equals(value)) {
            returnValue = ODT_QP_WAITAPPLY;
        } else if("申请撤单".equals(value)){
            returnValue = APPLY_BACK_APPLY;
        }else if("服务公司回单".equals(value)){
            returnValue = AGT_ROLLBACK;
        }
        return returnValue;
    }

}
