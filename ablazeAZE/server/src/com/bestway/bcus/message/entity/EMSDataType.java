package com.bestway.bcus.message.entity;

/**
 * 数据结构标志规范 数据标志(20字符)
 * 
 * @author chl
 * @version 1.0
 * @createDate 2013-10-8
 */
public class EMSDataType implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 关键字表
	 */
	public static final String EMS_EDI_KEY = "<EMS_EDI_KEY         >";
	/**
	 * 经营范围表头
	 */
	public static final String EMS_EDI_TR_HEAD = "<EMS_EDI_TR_HEAD     >";
	/**
	 * 电子帐册表头
	 */
	public static final String EMS_EDI_HEAD = "<EMS_EDI_HEAD        >";
	/**
	 * 电子帐册报核表头
	 */
	public static final String EMS_EDI_HEAD_DCR = "<EMS_EDI_HEAD_DCR    >";
	/**
	 * 联网核查表头
	 */
	public static final String EMS_EDI_COL_HEAD = "<EMS_EDI_COL_HEAD    >";
	/**
	 * 报关清单表头
	 */
	public static final String EDI_CL_HEAD = "<EDI_CL_HEAD         >";
	/**
	 * 报关单表头
	 */
	public static final String EDI_ENT_HEAD = "<EDI_ENT_HEAD        >";
	/**
	 * 电子帐册分册表头
	 */
	public static final String EMS_EDI_FAS_HEAD = "<EMS_EDI_FAS_HEAD    >";
	/**
	 * 货物清单货物表体
	 */
	public static final String EDI_CL_LIST = "<EDI_CL_LIST         >";
	/**
	 * 报关单表体
	 */
	public static final String EDI_ENT_LIST = "<EDI_ENT_LIST        >";
	/**
	 * 成品经营范围
	 */
	public static final String EMS_EDI_TR_EXG = "<EMS_EDI_TR_EXG      >";
	/**
	 * 料件经营范围
	 */
	public static final String EMS_EDI_TR_IMG = "<EMS_EDI_TR_IMG      >";
	/**
	 * 成品归并关系表
	 */
	public static final String EMS_EDI_EXG_DICT = "<EMS_EDI_EXG_DICT    >";
	/**
	 * 料件归并关系表
	 */
	public static final String EMS_EDI_IMG_DICT = "<EMS_EDI_IMG_DICT    >";
	/**
	 * 归并前详细成品
	 */
	public static final String EMS_EDI_ORG_EXG = "<EMS_EDI_ORG_EXG     >";
	/**
	 * 归并前详细料件
	 */
	public static final String EMS_EDI_ORG_IMG = "<EMS_EDI_ORG_IMG     >";
	/**
	 * 归并前BOM表
	 */
	public static final String EMS_EDI_ORG_BOM = "<EMS_EDI_ORG_BOM     >";
	/**
	 * 归并后成品
	 */
	public static final String EMS_EDI_EXG = "<EMS_EDI_EXG         >";
	/**
	 * 归并后料件
	 */
	public static final String EMS_EDI_IMG = "<EMS_EDI_IMG         >";
	/**
	 * 归并后单耗
	 */
	public static final String EMS_EDI_CM = "<EMS_EDI_CM          >";
	/**
	 * 分册成品
	 */
	public static final String EMS_EDI_FAS_EXG = "<EMS_EDI_FAS_EXG     >";
	/**
	 * 分册料件
	 */
	public static final String EMS_EDI_FAS_IMG = "<EMS_EDI_FAS_IMG     >";
	/**
	 * 核销料件
	 */
	public static final String EMS_EDI_DCR_IMG = "<EMS_EDI_DCR_IMG     >";
	/**
	 * 核销成品
	 */
	public static final String EMS_EDI_DCR_EXG = "<EMS_EDI_DCR_EXG     >";
	/**
	 * 料件核算表
	 */
	public static final String EMS_EDI_CLR_IMG = "<EMS_EDI_CLR_IMG     >";
	/**
	 * 成品核算表
	 */
	public static final String EMS_EDI_CLR_EXG = "<EMS_EDI_CLR_EXG     >";
	/**
	 * 归并前料件核算表
	 */
	public static final String EMS_EDI_ORG_CLR_IMG = "<EMS_EDI_ORG_CLR_IMG >";
	/**
	 * 归并前成品核算表
	 */
	public static final String EMS_EDI_ORG_CLR_EXG = "<EMS_EDI_ORG_CLR_EXG >";
	/**
	 * 料件中期核查表
	 */
	public static final String EMS_EDI_COL_IMG = "<EMS_EDI_COL_IMG >";
	/**
	 * 成品中期核查表
	 */
	public static final String EMS_EDI_COL_EXG = "<EMS_EDI_COL_EXG >";
	/**
	 * 核销报关单
	 */
	public static final String EMS_EDI_DCR_ENT = "<EMS_EDI_DCR_ENT     >";
	/**
	 * 残次品(废)
	 */
	public static final String EMS_EDI_DCR_EXR = "<EMS_EDI_DCR_EXR     >";
	/**
	 * 边角(废)料
	 */
	public static final String EMS_EDI_DCR_IMR = "<EMS_EDI_DCR_IMR     >";
	/**
	 * 海关审核结果
	 */
	public static final String EMS_EDI_RESULT = "<EMS_EDI_RESULT      >";
	/**
	 * 海关审核结果表头
	 */
	public static final String EMS_EDI_CHK_MSG = "<EMS_EDI_CHK_MSG     >";
	/**
	 * 帐册货物审核结果
	 */
	public static final String EMS_EDI_G_CHK_MSG = "<EMS_EDI_G_CHK_MSG   >";
	/**
	 * 帐册单耗审核结果
	 */
	public static final String EMS_EDI_CM_CHK_MSG = "<EMS_EDI_CM_CHK_MSG  >";
	/**
	 * 帐册报关单审核结果
	 */
	public static final String EMS_EDI_ENT_CHK_MSG = "<EMS_EDI_ENT_CHK_MSG >";
	/**
	 * 审核结果文字信息
	 */
	public static final String EMS_EDI_CHK_INFO = "<EMS_EDI_CHK_INFO    >";

	/**
	 * 一份数据起始标志
	 */
	public static final String ELEMENT = "<ELEMENT             >";
	/**
	 * 一份数据结束标志
	 */
	public static final String ELEMENT_END = "</ELEMENT            >";
	/**
	 * 数据密押
	 */
	public static final String HASH_SIGN = "<HASH_SIGN            >";
	
}
