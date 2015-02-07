package com.bestway.dzsc.customslist.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.ImpExpType;

/**
 * 报关清单表头
 * 
 * @author Administrator Created on 2004-7-23
 */
public class DzscCustomsBillList extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 进出口类型
	 * DIRECT_IMPORT = 0;	料件进口 料件资料 直接进口---料件
	 * TRANSFER_FACTORY_IMPORT = 1;	料件转厂 料件资料 转厂进口---料件
	 * BACK_FACTORY_REWORK = 2;	退厂返工 成品资料 ---成品
	 * GENERAL_TRADE_IMPORT = 3;	一般贸易进口 --- 料件
	 * DIRECT_EXPORT = 4;	成品出口 成品资料 ---成品
	 * TRANSFER_FACTORY_EXPORT = 5;	转厂出口 成品资料 ---成品
	 * BACK_MATERIEL_EXPORT = 6;	退料出口 料件资料 ---料件
	 * REWORK_EXPORT = 7;	返工复出 成品资料 ---成品
	 * REMIAN_MATERIAL_BACK_PORT = 8;	边角料退港 ----料件
	 * REMIAN_MATERIAL_DOMESTIC_SALES = 9;	边角料内销 ---料件
	 * GENERAL_TRADE_EXPORT = 10;	一般贸易出口 ---成品
	 * EQUIPMENT_IMPORT = 11;	设备进口
	 * BACK_PORT_REPAIR = 12;	退港维修
	 * EQUIPMENT_BACK_PORT = 13;	设备退港
	 * REMAIN_FORWARD_EXPORT = 14;	余料结转(出口报关单)
	 * EXPORT_STORAGE = 15;	出口仓储
	 * IMPORT_STORAGE = 16;	进口仓储
	 * MATERIAL_DOMESTIC_SALES = 17;	料件内销，海关批准内销 ---料件
	 * MATERIAL_EXCHANGE = 18;	料件退换 ---料件
	 * MATERIAL_REOUT = 19;	料件复出 ---料件
	 * All_Type = 20;	所有（注意：不存在这样的类型，方便在参数设置中使用到）
	 * IMPORT_EXG_BACK = 25;	进料成品退换 ---成品
	 * IMPORT_REPAIR_MATERIAL = 26;	修理物品 ---成品
	 * EXPORT_MATERIAL_REBACK = 27;	修理物品复出 ---成品
	 * EXPORT_EXG_REBACK = 28;	进料成品退换复出 ---成品
	 * REMAIN_FORWARD_IMPORT = 21;	余料结转(进口报关单)
	 */
	private Integer impExpType;

	/**
	 * 进出口标志
	 * IMPORT=0;	进口标志
	 * EXPORT=1;	出口标志
	 * SPECIAL=2;	特殊报关单
	 */
	private Integer impExpFlag;

	/**
	 * 进出口时间
	 */
	private Date impExpDate;

	/**
	 * 清单状态
	 * DRAFT = 0;	清单状态 ：草稿
	 * ALREADY_SEND = 1;	清单状态 ：已经申报
	 * PASSED = 2;	清单状态 ：审批通过
	 * Effect = 4;	清单状态：已生效
	 */
	private Integer listState;
	
	/**
	 * 清单类型
	 * NORMAL_DCR = 0;	一般报关单
	 * PRE_TRANS_DCR = 1;	提前转关报关单
	 * SECOND_TRANS_DCR = 2;	二次转关报关单
	 * APANAGE_DCR = 3;	属地报关
	 */
	private Integer listType;

	/**
	 * 手册号码
	 */
	private String emsHeadH2k;

	/**
	 * 经营单位代码
	 */
	private String tradeCode;

	/**
	 * 经营单位名称
	 */
	private String tradeName;

	/**
	 * 清单号码
	 */
	private String listNo;

	/**
	 * 企业内部编号
	 */
	private String copEmsNo;

	/**
	 * 商品项数
	 */
	private Integer materialNum;

	/**
	 * 清单申报日期
	 */
	private Date listDeclareDate;

//	/**
//	 * 报关单申报日期
//	 */
//	private Date customsDeclarationDate;

	/**
	 * 报关单统一编号
	 */
	private String customsSeqNo;

	/**
	 * 录入日期
	 */
	private Date createdDate;

	/**
	 * 录入员
	 */
	private AclUser createdUser;

	/**
	 * 进出口岸
	 */
	private Customs impExpCIQ;

	/**
	 * 申报地海关
	 */
	private Customs declareCIQ;

	/**
	 * 料件成品标志
	 */
	private Integer materielProductFlag;

	/**
	 * 运输方式
	 */
	private Transf transportMode;

	/**
	 * 贸易方式
	 */
	private Trade tradeMode;

	/**
	 * 录入单位
	 */
	private Brief createdCompany;

	/**
	 * 备注
	 */
	private String memos;

	/**
	 * 是否已转报关单
	 */
	private Boolean isGenerateDeclaration = false;

	/**
	 * 关封号
	 */
	private String customsEnvelopBillNo;
	/**
	 *  是否选中
	 */
	private Boolean isSelected=false;

	/**
	 * 获取经营单位代码
	 * 
	 * @return tradeCode 经营单位代码
	 */
	public String getTradeCode() {
		return tradeCode;
	}

	/**
	 * 设置经营单位代码
	 * 
	 * @param tradeCode
	 *            经营单位代码
	 */
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	/**
	 * 获取经营单位名称
	 * 
	 * @return tradeName 经营单位名称
	 */
	public String getTradeName() {
		return tradeName;
	}

	/**
	 * 设置经营单位名称
	 * 
	 * @param tradeName
	 *            经营单位名称
	 */
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	/**
	 * 获取录入单位
	 * 
	 * @return createdCompany 录入单位
	 */
	public Brief getCreatedCompany() {
		return createdCompany;
	}

	/**
	 * 设置录入单位
	 * 
	 * @param createdCompany
	 *            录入单位
	 */
	public void setCreatedCompany(Brief createdCompany) {
		this.createdCompany = createdCompany;
	}

	/**
	 * 获取录入日期
	 * 
	 * @return createdDate 录入日期
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * 设置录入日期
	 * 
	 * @param createdDate
	 *            录入日期
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * 获取录入员
	 * 
	 * @return createdUser 录入员
	 */
	public AclUser getCreatedUser() {
		return createdUser;
	}

	/**
	 * 设置录入员
	 * 
	 * @param createdUser
	 *            录入员
	 */
	public void setCreatedUser(AclUser createdUser) {
		this.createdUser = createdUser;
	}

	/**
	 * 获取报关单编号
	 * 
	 * @return customsDeclarationCode 报关单编号
	 */
	public String getCustomsSeqNo() {
		return customsSeqNo;
	}

	/**
	 * 设置报关单编号
	 * 
	 * @param customsDeclarationCode
	 *            报关单编号
	 */
	public void setCustomsSeqNo(String customsDeclarationCode) {
		this.customsSeqNo = customsDeclarationCode;
	}

//	/**
//	 * 获取报关单申报日期
//	 * 
//	 * @return customsDeclarationDate 报关单申报日期
//	 */
//	public Date getCustomsDeclarationDate() {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		if (customsDeclarationDate != null) {
//			return java.sql.Date.valueOf(dateFormat
//					.format(customsDeclarationDate));
//		}
//		return customsDeclarationDate;
//	}
//
//	/**
//	 * 设置报关单申报日期
//	 * 
//	 * @param customsDeclarationDate
//	 *            报关单申报日期
//	 */
//	public void setCustomsDeclarationDate(Date customsDeclarationDate) {
//		this.customsDeclarationDate = customsDeclarationDate;
//	}

	/**
	 * 获取申报地海关
	 * 
	 * @return declareCIQ 申报地海关
	 */
	public Customs getDeclareCIQ() {
		return declareCIQ;
	}

	/**
	 * 设置申报地海关
	 * 
	 * @param declareCIQ
	 *            申报地海关
	 */
	public void setDeclareCIQ(Customs declareCIQ) {
		this.declareCIQ = declareCIQ;
	}

	/**
	 * 获取手册号码
	 * 
	 * @return emsHeadH2k 手册号码
	 */
	public String getEmsHeadH2k() {
		return emsHeadH2k;
	}

	/**
	 * 设置手册号码
	 * 
	 * @param emsHeadH2k
	 *            手册号码
	 */
	public void setEmsHeadH2k(String emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}

	/**
	 * 获取进出口岸
	 * 
	 * @return impExpCIQ 进出口岸
	 */
	public Customs getImpExpCIQ() {
		return impExpCIQ;
	}

	/**
	 * 设置进出口岸
	 * 
	 * @param impExpCIQ
	 *            进出口岸
	 */
	public void setImpExpCIQ(Customs impExpCIQ) {
		this.impExpCIQ = impExpCIQ;
	}

	/**
	 * 获取进出口标志
	 * 
	 * @return impExpFlag 进出口标志
	 */
	public Integer getImpExpFlag() {
		return impExpFlag;
	}

	/**
	 * 获取进出口标志
	 * 
	 * @return impExpFlag 进出口标志
	 */
	public String getCustomsImpExpFlag() {
		if (this.impExpType.equals(ImpExpType.DIRECT_IMPORT)
				|| this.impExpType.equals(ImpExpType.TRANSFER_FACTORY_IMPORT)
				|| this.impExpType.equals(ImpExpType.REMAIN_FORWARD_IMPORT)
				|| this.impExpType.equals(ImpExpType.BACK_FACTORY_REWORK)) {
			return "I";
		} else {
			return "E";
		}
	}

	/**
	 * 设置进出口标志
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 */
	public void setImpExpFlag(Integer impExpFlag) {
		this.impExpFlag = impExpFlag;
	}

	/**
	 * 获取进出口类型
	 * 
	 * @return impExpType 进出口类型
	 */
	public Integer getImpExpType() {
		return impExpType;
	}

	/**
	 * 设置进出口类型
	 * 
	 * @param impExpType
	 *            进出口类型
	 */
	public void setImpExpType(Integer impExpType) {
		this.impExpType = impExpType;
	}

	/**
	 * 获取清单申报日期
	 * 
	 * @return listDeclareDate 清单申报日期
	 */
	public Date getListDeclareDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (listDeclareDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(listDeclareDate));
		}
		return listDeclareDate;
	}

	/**
	 * 设置清单申报日期
	 * 
	 * @param listDeclareDate
	 *            清单申报日期
	 */
	public void setListDeclareDate(Date listDeclareDate) {
		this.listDeclareDate = listDeclareDate;
	}

	/**
	 * 获取清单号码
	 * 
	 * @return listNo 清单号码
	 */
	public String getListNo() {
		return listNo;
	}

	/**
	 * 设置清单号码
	 * 
	 * @param listNo
	 *            清单号码
	 */
	public void setListNo(String listNo) {
		this.listNo = listNo;
	}

	public String getCopEmsNo() {
		return copEmsNo;
	}

	public void setCopEmsNo(String copEmsNo) {
		this.copEmsNo = copEmsNo;
	}

	/**
	 * 获取清单状态
	 * 
	 * @return listState 清单状态
	 */
	public Integer getListState() {
		return listState;
	}

	/**
	 * 设置清单状态
	 * 
	 * @param listState
	 *            清单状态
	 */
	public void setListState(Integer listState) {
		this.listState = listState;
	}

	public Integer getListType() {
		return listType;
	}

	public void setListType(Integer listType) {
		this.listType = listType;
	}

	/**
	 * 获取商品项数
	 * 
	 * @return materialNum 商品项数
	 */
	public Integer getMaterialNum() {
		return materialNum;
	}

	/**
	 * 设置商品项数
	 * 
	 * @param materialNum
	 *            商品项数
	 */
	public void setMaterialNum(Integer materialNum) {
		this.materialNum = materialNum;
	}

	/**
	 * 获取料件成品标志
	 * 
	 * @return materielProductFlag 料件成品标志
	 */
	public Integer getMaterielProductFlag() {
		return materielProductFlag;
	}

	/**
	 * 获取料件成品标志
	 * 
	 * @return materielProductFlag 料件成品标志
	 */
	public Integer getCustomsImgExgFlag() {
		if (this.materielProductFlag == null) {
			return 3;
		}
		if (MaterielType.MATERIEL.equals(materielProductFlag.toString())) {
			return 3;
		} else {
			return 4;
		}
	}

	/**
	 * 设置料件成品标志
	 * 
	 * @param materielProductFlag
	 *            料件成品标志
	 */
	public void setMaterielProductFlag(Integer materielProductFlag) {
		this.materielProductFlag = materielProductFlag;
	}

	/**
	 * 获取备注
	 * 
	 * @return memo 备注
	 */
	public String getMemos() {
		return memos;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemos(String memo) {
		this.memos = memo;
	}

	/**
	 * 获取监管方式
	 * 
	 * @return tradeMode 监管方式
	 */
	public Trade getTradeMode() {
		return tradeMode;
	}

	/**
	 * 设置监管方式
	 * 
	 * @param tradeMode
	 *            监管方式
	 */
	public void setTradeMode(Trade tradeMode) {
		this.tradeMode = tradeMode;
	}

	/**
	 * 获取运输方式
	 * 
	 * @return transportMode 运输方式
	 */
	public Transf getTransportMode() {
		return transportMode;
	}

	/**
	 * 设置运输方式
	 * 
	 * @param transportMode
	 *            运输方式
	 */
	public void setTransportMode(Transf transportMode) {
		this.transportMode = transportMode;
	}

	/**
	 * 获取是否已转报关单，true为已转
	 * 
	 * @return isGenerateDeclaration 是否已转报关单，true为已转
	 */
	public Boolean getIsGenerateDeclaration() {
		return isGenerateDeclaration;
	}

	/**
	 * 设置是否已转报关单，true为已转
	 * 
	 * @param isGenerateDeclaration
	 *            是否已转报关单，true为已转
	 */
	public void setIsGenerateDeclaration(Boolean isGenerateDeclaration) {
		this.isGenerateDeclaration = isGenerateDeclaration;
	}

	/**
	 * 关封号
	 */
	public String getCustomsEnvelopBillNo() {
		return customsEnvelopBillNo;
	}

	/**
	 * 关封号
	 */
	public void setCustomsEnvelopBillNo(String customsEnvelopBillNo) {
		this.customsEnvelopBillNo = customsEnvelopBillNo;
	}

	public Date getImpExpDate() {
		return impExpDate;
	}

	public void setImpExpDate(Date impExpDate) {
		this.impExpDate = impExpDate;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
}
