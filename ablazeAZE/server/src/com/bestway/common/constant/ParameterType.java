/*
 * Created on 2004-7-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.constant;

import java.io.Serializable;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ParameterType implements Serializable {
	// 系统共用参数设置
	public static final int sendDir = 1; // 报文发送路径
	public static final int recvDir = 2; // 回执存放路径
	public static final int recvBillDir = 20; // 清单回执存放路径
	public static final int finallyDir = 3; // 处理完路径
	public static final int logDir = 4; // 日志存放路径
	public static final int year = 5; // 海关帐年度
	public static final int emsFrom = 6; // 电子帐册数据来源
	public static final int txtInput = 7; // 文本导入路径
	public static final int txtInputFinally = 8; // 文本导入成功路径
	public static final int txtInputFail = 9; // 文本导入失败路径
	public static final int txtlog = 10; // 文本导入日志路径
	public static final int style = 12; // 系统风格
	public static final int isBigToGBK = 0; // 报文输出是否繁转简

	// 无纸通关参数设置
	public static final int contractFrom = 11; // 合同备案来源
	public static final int unitWasteWriteBackExg = 13; // 修改单耗时自动计算成品总额
	public static final int unitWasteWriteBackImg = 14; // 修改单耗时自动计算料件总额
	public static final int isVailCustomsOther = 15; // 报关单生效是否检查附加信息
	public static final int isCanModifyUnitPrice = 18; // 进出口报关单的单价是否可以修改
	public static final int isPrintAll = 24;
	public static final int isCanModifyNumber = 19;	//出口报关单预录入号、统一编号、报关单号是否可修改设定
	// BOM系统参数设定
	public static final int bomStructureType = 16; // BOM构成类型

	// 报关单要允许输入超量的数据
	public static final int CUSTOM_AMOUNT_OUT = 17; // BOM构成类型
	// 允许多本备案资料库备案
	public static final int putOnRecords = 21; // 允许多本备案资料库备案
	//电子化手册特殊报关单来源
	public static final int SpecialCustoms = 22; // 特殊报关单来源
	
	public static final int h2kMergerModifyNo = 23; // 修改经营范围与归并关系的备案序号
	public static final int H2K_TR_INPUT = 24; // 经营范围可手工增加
}
