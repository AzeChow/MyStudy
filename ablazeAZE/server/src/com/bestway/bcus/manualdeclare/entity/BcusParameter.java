/*
 * Created on 2004-6-14
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * BCUS参数
 * 
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class BcusParameter extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 帐册转换BOM单耗小数位
	 */
	public static final int EMSBOM_UNITWEAR_DIGITS = 1;

	/**
	 * 帐册转换BOM损耗小数位
	 */
	public static final int EMSBOM_WEAR_DIGITS = 2;

	/**
	 * 报关单表体信息是否自动取整
	 */
	public static final int CUSTOMS_FORINT = 3;

	/**
	 * 电子帐册归并关系是否分批发送
	 */
	public static final int EmsSEND = 4;

	/**
	 * 电子帐册归并关系是否分批发送同时打上申报标记
	 */
	public static final int EmsSEND_Sign = 8;

	/**
	 * 报关单是否先查询再新增
	 */
	public static final int BGD_ISQUERY = 5;

	/**
	 * 电子帐册是否分批发送
	 */
	public static final int EmsEdiH2kSend = 6;

	/**
	 * 电子帐册是否分批发送是否同时打上申报标记
	 */
	public static final int EmsEdiH2kSend_Sign = 9;

	/**
	 * 申请单转报关单是否默认单价
	 */
	public static final int DefaultPrice = 7;

	/**
	 * 关封申请表编号
	 */
	public static final int TransferCode = 10;

	/**
	 * 报关单保存是否同时检查
	 */
	public static final int CUSTOMS_SAVA_CHECK = 11;

	/**
	 * 报关单境外运输工具是否自动带出境内运输工具编号
	 */
	public static final int CUSTOMS_JW_JN = 12;

	/**
	 * 增值比率（明门使用）
	 */
	public static final int INCREMENT_RATE = 13;

	/**
	 * 电子帐册单耗导入是否成品一对一导入
	 */
	public static final int EMS_IMPOERT_ONEEXG = 14;

	/**
	 * 电子帐册报关单导入文件存放路径
	 */
	public static final int LOAD_QP_BGD_DIR = 15;

	/**
	 * 打印出口报关单报表时是否有版本栏位(只有BCUS才有版本栏位)
	 */
	public static final int PrintCustomWithVersion = 16;

	/**
	 * 报表的小数位位数长度
	 */
	public static final int ReportDecimalLength = 17;

//	/**
//	 * 申请单转清单数量小数位
//	 */
//	public static final int APLLY_TO_BILL_AMOUNT = 18;
//
//	/**
//	 * 申请单转清单总金额小数位
//	 */
//	public static final int APLLY_TO_BILL_TOTALPRICE = 19;
//
//	/**
//	 * 申请单转清单单价小数位
//	 */
//	public static final int APLLY_TO_BILL_PRICE = 20;

	/**
	 * 申请单转清单默认小数位
	 */
	public static final String APLLY_TO_BILL_DEFAULT = "3";
	/**
	 * 帐册单耗单位小数据位控制
	 */
	public static final int EMS_BOM_PRICE = 21;
	/**
	 * 归并关系BOM转是否需要转国内购料
	 */
	public static final int EMS_BOM_MaterilSoure = 22;
	/**
	 * 申请单转清单归并关系中BOM最大版本号
	 */
	public static final int MERGER_BOM_MaxVer = 23;
	
	/**
	 * 帐册报核出口耗料金额单价来源
	 */
	public static final int Ems_CancelCus_Price = 24;
	/**
	 * 帐册按照出口、进口数量，控制退换、复出数量
	 */
	public static final int Ems_Amount_Control = 25;
	/**
	 * 报关清单数量栏位控制
	 */
	public static final int BILL_TO_CONTROL_AMOUNT = 26;
	/**
	 * 报关清单总价栏位控制
	 */
	public static final int BILL_TO_CONTROL_TOTALPRICE = 27;
	/**
	 * 报关清单单价栏位控制
	 */
	public static final int BILL_TO_CONTROL_PRICE = 28;
	/**
	 * 类型 EMSBOM_UNITWEAR_DIGITS = 1; 帐册转换BOM单耗小数位 EMSBOM_WEAR_DIGITS = 2;
	 * 帐册转换BOM损耗小数位 CUSTOMS_FORINT = 3; 报关单是否自动取整 EmsSEND = 4; 电子帐册归并关系是否分批发送
	 * EmsSEND_Sign = 8; 电子帐册归并关系是否分批发送同时打上申报标记 BGD_ISQUERY = 5; 报关单是否先查询再新增
	 * EmsEdiH2kSend = 6; 电子帐册是否分批发送 EmsEdiH2kSend_Sign = 9;
	 * 电子帐册是否分批发送是否同时打上申报标记 DefaultPrice = 7; 申请单转报关单是否默认单价 TransferCode = 10;
	 * 关封申请表编号 CUSTOMS_SAVA_CHECK = 11; 报关单保存是否同时检查 CUSTOMS_JW_JN = 12;
	 * 报关单境外运输工具是否自动带出境内运输工具编号 INCREMENT_RATE = 13; 增值比率（明门使用）
	 * EMS_IMPOERT_ONEEXG = 14; 电子帐册单耗导入是否成品一对一导入 LOAD_QP_BGD_DIR = 15;
	 * 电子帐册报关单导入文件存放路径 PrintCustomWithVersion = 16;
	 * 打印出口报关单报表时是否有版本栏位(只有BCUS才有版本栏位) ReportDecimalLength = 17; 报表的小数位位数长度
	 * APLLY_TO_BILL_AMOUNT = 18; 申请单转清单数量小数位 APLLY_TO_BILL_TOTALPRICE = 19;
	 * 申请单转清单总金额小数位 APLLY_TO_BILL_PRICE = 20; 申请单转清单单价小数位 APLLY_TO_BILL_DEFAULT =
	 * "3"; 申请单转清单默认小数位
	 */
	/**
	 * 电子帐册报关清单导入文件存放路径
	 */
	public static final int LOAD_APPLY_QP_BGD_DIR = 29;
	/**
	 * 本期进口总金额是否扣减退料出口金额
	 */
	public static final int ImgImpTotalMoney = 30;
	
	/**
	 * 申请单转报关清单商品单限制值
	 */
	public static final int BILL_CONTROL_PRICE = 31;
	
	/**
	 * 核销时，实际剩余数量包含非保税数量（适用于东莞凤岗海关）
	 */
	public static final int CAV_REAL_NUM_INCLUDE_NONBONDE = 32;
	
	public static final int RESULT = 33;
	
	/**
	 * 正式报核表头进出口总金额包含报关单表头运费和保费
	 */
	public static final int Premium_Price = 34;

	private int type;
	/**
	 * 当前值
	 */
	private String strValue;

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
