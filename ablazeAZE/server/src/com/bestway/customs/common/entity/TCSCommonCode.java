package com.bestway.customs.common.entity;
/**
 * 集成通2.0平台代码标准
 * @version 2.0
 * @author chl
 */
public class TCSCommonCode {
	
	public static final String Im_qq = "001";
	public static final String Im_msn = "002";
	
	public static final String getImType(final String type) {
		if(Im_qq.equals(type)) {
			return "QQ";
		} else if(Im_msn.equals(type)) {
			return "MSN";
		} else {
			return "QQ";
		}
	}
	
	// 报关单类型
	public static final String TcsEntryType_normal = "0";
	public static final String TcsEntryType_list = "L";
	public static final String TcsEntryType_noPaper = "W";
	public static final String TcsEntryType_listAndNoPaper = "D";
	public static final String TcsEntryType_declarationAndNoPaper = "M";
	/**
	 * 报关单类型
	 * 0有纸通关，2013-9-21修改
	 * L为带报关单清单的报关单，
	 * W无纸报关类型，
	 * D既是清单又是无纸报关的情况
	 * M通关无纸化
	 * @param type
	 * @return
	 */
	public static final String getTcsEntryType(final String type) {
		if(TcsEntryType_normal.equals(type)) {
			return "有纸通关";
		} else if(TcsEntryType_list.equals(type)) {
			return "为带报关单清单的报关单";
		} else if(TcsEntryType_noPaper.equals(type)) {
			return "无纸报关";
		} else if(TcsEntryType_listAndNoPaper.equals(type)) {
			return "既是清单又是无纸报关";
		}  else if(TcsEntryType_declarationAndNoPaper.equals(type)) {
			return "通关无纸化";
		} else {
			return "普通报关单";
		}
	}
	
	// EDI备注
	public static final String TcsType_normal = "";
	public static final String TcsType_ML = "ML";
	public static final String TcsType_SD = "SD";
	//2014-3-21 因QP升级，EDI接口也变更，新增属地申报，属地放行”报关单为SS	
	public static final String TcsType_SS = "SS";
	/**
	 * EDI备注
	 * 一般报关单填空值；
	 * ML：保税区进出境备案清单；
	 * SS: “属地申报，口岸验放”报关单。
	 * @param type
	 * @return
	 */
	public static final String getTcsType(final String type) {
		if(TcsType_normal.equals(type)) {
			return "一般报关单";
		} else if(TcsType_ML.equals(type)) {
			return "保税区进出境备案清单";
		} else if(TcsType_SD.equals(type)) {
			return "“属地申报，口岸验放”报关单";
		} else if(TcsType_SS.equals(type)){
			return "“属地申报，属地放行”报关单";
		}else {
			return "一般报关单";
		}
	}
	// 报关标志
	public static final String TcsEdiId_normal = "1";
	public static final String TcsEdiId_southBefore = "5";
	public static final String TcsEdiId_northBefore = "3";
	public static final String TcsEdiId_normal_south = "6";
	
	/**
	 * 报关标志
	 * 1：普通报关；
	 * 3：北方转关提前；
	 * 5：南方转关提前；
	 * 6：普通报关，运输工具名称以‘@’开头，南方H2000直转
	 * @param type
	 * @return
	 */
	public static final String getTcsEdiId(final String type) {
		if(TcsEdiId_normal.equals(type)) {
			return "普通报关";
		} else if(TcsEdiId_northBefore.equals(type)) {
			return "北方转关提前";
		} else if(TcsEdiId_southBefore.equals(type)) {
			return "南方转关提前";
		} else if(TcsEdiId_normal_south.equals(type)) {
			return "普通报关，运输工具名称以‘@’开头，南方H2000直转";
		} else {
			return "普通报关";
		}
	}
	
	// 操作类型
	/**
	 * 操作类型 
	 * A：报关单上载 
	 * B：报关单、转关单上载 
	 * C：报关单申报 
	 * D：报关单、转关单申报
	 * E：电子手册报关单上载（此种操作类型的报关单上载时不作非空和逻辑校验） 
	 * G：报关单暂存
	 */
	public static final String OperType_customs_upload = "A";
	public static final String OperType_customs_transfer_upload = "B";
	public static final String OperType_customs_declaration = "C";
	public static final String OperType_customs_transfer_declaration = "D";
	public static final String OperType_customs_dzsc_upload = "E";
	public static final String OperType_customs_save = "G";
	
	public static final String getOperTypeDesc(final String type) {
		if(OperType_customs_upload.equals(type)) {
			return "报关单上载";
		} else if(OperType_customs_transfer_upload.equals(type)) {
			return "报关单、转关单上载";
		} else if(OperType_customs_declaration.equals(type)) {
			return "报关单申报";
		} else if(OperType_customs_transfer_declaration.equals(type)) {
			return "报关单、转关单申报";
		} else if(OperType_customs_dzsc_upload.equals(type)) {
			return "电子手册报关单上载";
		} else if(OperType_customs_save.equals(type)) {
			return "报关单暂存";
		} else {
			throw new RuntimeException("没找到该操作类型！");
		}
	}
	
	/**
	 * T209	
	 * 报关回执类型	
	 * 		001	导入成功
			002	导入失败
			003	QP接收成功，上载发往数据中心
			004	QP接收成功，申报发往数据中心
			005	QP处理失败，请重新上载或申报
			006	上载成功
			007	上载失败
			008	申报成功
			009	上载未申报（无申报权限）
			010	上载申报失败
			011	海关已接收
			012	担保放行
			013	不被受理
			014	需手工申报
			015	退回修改
			016	报关单已审结
			017	放行交单
			018	需交税费
			019	申报失败
			020	海关删单
			021	报关单重审
			022	已结关
			023	进出口审结/查验/放行通知
			024	出口查验通知
			025	海关已放行 
			026	海关放行前删除
			027	海关准许进港回执（保税港区专用）
			028	其他（回执）

	 */
	/**
	 * 001	导入成功
	 */
	public static final String CHANNEL_001 = "001";
	/**
	 * 002	导入失败
	 */
	public static final String CHANNEL_002 = "002";
	/**
	 * 003	QP接收成功，上载发往数据中心
	 */
	public static final String CHANNEL_003 = "003";
	/**
	 * 004	QP接收成功，申报发往数据中心
	 */
	public static final String CHANNEL_004 = "004";
	/**
	 * 005	QP处理失败，请重新上载或申报
	 */
	public static final String CHANNEL_005 = "005";
	/**
	 * 006	上载成功
	 */
	public static final String CHANNEL_006 = "006";
	/**
	 * 007	上载失败
	 */
	public static final String CHANNEL_007 = "007";
	/**
	 * 008	申报成功
	 */
	public static final String CHANNEL_008 = "008";
	/**
	 * 009	上载未申报（无申报权限）
	 */
	public static final String CHANNEL_009 = "009";
	/**
	 * 010	上载申报失败
	 */
	public static final String CHANNEL_010 = "010";
	/**
	 * 011	海关已接收
	 */
	public static final String CHANNEL_011 = "011";
	/**
	 * 012	担保放行
	 */
	public static final String CHANNEL_012 = "012";
	/**
	 * 013	不被受理
	 */
	public static final String CHANNEL_013 = "013";
	/**
	 * 014	需手工申报
	 */
	public static final String CHANNEL_014 = "014";
	/**
	 * 015	退回修改
	 */
	public static final String CHANNEL_015 = "015";
	/**
	 * 016	报关单已审结
	 */
	public static final String CHANNEL_016 = "016";
	/**
	 * 017	放行交单
	 */
	public static final String CHANNEL_017 = "017";
	/**
	 * 018	需交税费
	 */
	public static final String CHANNEL_018 = "018";
	/**
	 * 019	申报失败
	 */
	public static final String CHANNEL_019 = "019";
	/**
	 * 020	海关删单
	 */
	public static final String CHANNEL_020 = "020";
	/**
	 * 021	报关单重审
	 */
	public static final String CHANNEL_021 = "021";
	/**
	 * 022	已结关
	 */
	public static final String CHANNEL_022 = "022";
	/**
	 * 023	进出口审结/查验/放行通知
	 */
	public static final String CHANNEL_023 = "023";
	/**
	 * 024	出口查验通知
	 */
	public static final String CHANNEL_024 = "024";
	/**
	 * 025	海关已放行 
	 */
	public static final String CHANNEL_025 = "025";
	/**
	 * 026	海关放行前删除
	 */
	public static final String CHANNEL_026 = "026";
	/**
	 * 027	海关准许进港回执（保税港区专用）
	 */
	public static final String CHANNEL_027 = "027";
	/**
	 * 028	其他（回执）
	 */
	public static final String CHANNEL_028 = "028";
}
