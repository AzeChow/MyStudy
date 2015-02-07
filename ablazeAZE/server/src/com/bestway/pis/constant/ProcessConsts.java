package com.bestway.pis.constant;

/**
 * 流程常量
 * @author kpc
 */
public final class ProcessConsts {
    /**
     * 用户自定义业务流程 业务资料上传 开始日期字段 键
     */
    public static final String CUSTOM_START_DATE = "custom.start.date";
    /**
     * 用户自定义业务流程期望办结日期字段 键
     */
    public static final String CUSTOM_FINISH_DATE = "custom.finish.date";
    /**
     * 业务流程级别
     */
    public static final String CUSTOM_PROCESS_LEVEL = "custom.process.level";
    /**
     * 用户定义企业海关编码
     */
    public static final String CUSTOM_COMPANY_CODE  = "custom.company.code";
    /**
     * 用户定义企业名称
     */
    public static final String CUSTOM_COMPANY_NAME  = "custom.company.name";
    
     /**
     * 自定义备注 键
     */
    public static final String CUSTOM_NOTE = "custom.note";
    
    /**
     * 分派流程办理人 键
     */
    public static final String ASSIGN_USERID = "assign.userid";
    /**
     * 分派流程办理人名称 键
     */
    public static final String ASSIGN_USERNAME = "assign.username";
    /**
     * 分派日期 键
     */
    public static final String ASSIGN_DATE = "assign.date";
    /**
     * 分派办理完结日期 键
     */
    public static final String ASSIGN_PROCESS_DONE_DATE = "assign.process.done.date";
    /**
     * 分派备注 键
     */
    public static final String ASSIGN_NOTE = "assign.note";
    
    /////////////////////////////流程级别定义////////////////////
    /**
     * 流程 紧急程度 1：一般 2:紧急 0:不急
     */
    public static final String PROCESS_LEVEL_NORMAL = "1"; //一般
    public static final String PROCESS_LEVEL_FAST = "2";    //紧急
    public static final String PROCESS_LEVEL_SLOW = "0";    //不急    
    /////////////////////////////流程级别定义 end////////////////////
    
    /**
     * 流程用户自定义操作
     */
    public static final String PROCESS_PARAM_OPERATION = "process.custom.operation";
    /**
     * 用户撤单操作
     */
    public static final String PARAM_OPERATION_CANCELBILL = "custom.cancelbill";
    
}
