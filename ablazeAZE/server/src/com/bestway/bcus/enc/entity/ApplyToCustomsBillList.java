/*
 * Created on 2004-7-23
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 报关清单
 * @author Administrator table="applytocustomsbilllist" 报关清单
 */
public class ApplyToCustomsBillList extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	
	
	/**
	 * 进出口标志中的进口标志
	 */
	public static final int IMPORT = 0;

	/**
	 * 进出口标志中的出口标志
	 */
	public static final int EXPORT = 1;

	/**
	 * 清单状态 ：草稿
	 */
	public static final int DRAFT = 0;

	/**
	 * 清单状态 ：已经申报
	 */
	public static final int ALREADY_SEND = 1;

	/**
	 * 清单状态 ：审批通过
	 */
	public static final int PASSED = 2;

	/**
	 *已转报关单标志
	 */
	//public static final int GENERATED_CUSTOMS_DECLARATION = 3;
	/**
	 *  清单状态：已生效
	 */
	public static final int Effect = 4;
	/**
	 * 清单类型
	 */
	private String billType;
	/**
	 * 进出口标志
	 */
	private String impExpFlagToBW; 
	/**
	 * 申报标志
	 */
	private String customsFlag;
	/**
	 * 料件成品标志
	 */
	private String imgExgFlag;
	
	/**
	 * 把effectstate作为 已转报关单标志
	 */
	private Boolean				effectstate		= false; //private boolean    =  false;
	/**
	 * 状态
	 */
	private String state; 

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
	private Integer impExpFlag;  //  @jve:decl-index=0:

	/**
	 * 清单状态
	 * DRAFT = 0;	清单状态 ：草稿
	 * ALREADY_SEND = 1;	清单状态 ：已经申报
	 * PASSED = 2;	清单状态 ：审批通过
	 * Effect = 4;	清单状态：已生效
	 */
	private Integer listState;

	/**
	 * 电子帐册号码
	 */
	private String emsHeadH2k;

	/**
	 * 经营单位代码
	 */
	private String tradeCode;

	/**
	 * 经营单位名称
	 */
	private String tradeName;  //  @jve:decl-index=0:

	/**
	 * 企业内部清单号码
	 */
	private String listNo;

	/**
	 * 清单统一编号
	 */
	private String listUniteNo;
	
	/**
	 * QP清单统一编号(清单编号)
	 */
	private String listQpBillNo;
	/**
	 * 商品项数
	 */
	private Integer materialNum;

	/**
	 * 清单申报日期
	 */
	private Date listDeclareDate;  //  @jve:decl-index=0:

	/**
	 * 报关单申报日期
	 */
	private Date customsDeclarationDate;

	/**
	 * 报关单流水编号
	 */
	private String customsDeclarationCode;

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
	 * FINISHED_PRODUCT="0";	成品
	 * SEMI_FINISHED_PRODUCT="1";	半成品
	 * MATERIEL="2";	材料--主料
	 * MACHINE="3";	设备
	 * REMAIN_MATERIEL="4";	边角料
	 * BAD_PRODUCT="5";	残次品
	 * ASSISTANT_MATERIAL = "6";	辅料
	 * WASTE_MATERIAL = "7";	消耗料
	 */
	private Integer materielProductFlag;

	/**
	 * 运输方式
	 */
	private Transf transportMode;

	/**
	 * 监管方式
	 */
	private Trade tradeMode;  //  @jve:decl-index=0:

	/**
	 * 录入单位
	 */
	private Brief createdCompany;

	/**
	 * 备注
	 */
	private String memos;

	/**
	 * 来自申请单客户供应商
	 */
	private ScmCoc scmCoc = null;

	/**
	 * 关封号
	 */
	private String customsEnvelopBillNo;

	/**
	 * 申报单位
	 */
	private Company declarationCompany;  //  @jve:decl-index=0:
	/**
	 * 车次编号
	 */
	private String catNo; 

	/**
	 * 取得经营单位代码
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
	 * 取得经营单位名称
	 * 
	 * @return 经营单位名称
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
	 * 取得录入单位
	 * 
	 * @return 录入单位
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
	 * 取得录入日期
	 * 
	 * @return 录入日期
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
	 * 取得录入员
	 * 
	 * @return 录入员
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
	 * 取得报关单流水编号
	 * 
	 * @return 报关单流水编号
	 */
	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}

	/**
	 * 设置报关单流水编号
	 * 
	 * @param customsDeclarationCode
	 *            报关单流水编号
	 */
	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}

	/**
	 * 取得报关单申报日期
	 * 
	 * @return 报关单申报日期
	 */
	public Date getCustomsDeclarationDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (customsDeclarationDate != null) {
			return java.sql.Date.valueOf(dateFormat
					.format(customsDeclarationDate));
		}
		return customsDeclarationDate;
	}

	/**
	 * 设置报关单申报日期
	 * 
	 * @param customsDeclarationDate
	 *            报关单申报日期
	 */
	public void setCustomsDeclarationDate(Date customsDeclarationDate) {
		this.customsDeclarationDate = customsDeclarationDate;
	}

	/**
	 * 获得申报地海关
	 * 
	 * @return 申报地海关
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
	 * 获得电子帐册号码
	 * 
	 * @return 电子帐册号码
	 */
	public String getEmsHeadH2k() {
		return emsHeadH2k;
	}

	/**
	 * 设置电子帐册号码
	 * 
	 * @param emsHeadH2k
	 *            电子帐册号码
	 */
	public void setEmsHeadH2k(String emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}

	/**
	 * 获得进出口岸
	 * 
	 * @return 进出口岸
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
	 * 获得进出口标志
	 * 
	 * @return 进出口标志
	 */
	public Integer getImpExpFlag() {
		return impExpFlag;
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
	 * 获得进出口类型
	 * 
	 * @return 进出口类型
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
	 * 获得清单申报日期
	 * 
	 * @return 清单申报日期
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
	 * 获得内部清单号码
	 * 
	 * @return 内部清单号码
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

	/**
	 * 获得清单状态
	 * 
	 * @return 清单状态
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

	/**
	 * 获得商品项数
	 * 
	 * @return 商品项数
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
	 * 获得料件成品标志
	 * 
	 * @return 料件成品标志
	 */
	public Integer getMaterielProductFlag() {
		return materielProductFlag;
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
	 * 获得备注内容
	 * 
	 * @return 备注
	 */
	public String getMemos() {
		return memos;
	}

	/**
	 * 填写备注内容
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemos(String memo) {
		this.memos = memo;
	}

	/**
	 * 获得监管方式
	 * 
	 * @return 监管方式
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
	 * 获得运输方式
	 * 
	 * @return 运输方式
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
	 * 获得来自申请单客户供应商
	 * 
	 * @return 来自申请单客户供应商
	 */
	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	/**
	 * 设置来自申请单客户供应商
	 * 
	 * @param scmCoc
	 *            来自申请单客户供应商
	 */
	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	public String getCustomsEnvelopBillNo() {
		return customsEnvelopBillNo;
	}

	public void setCustomsEnvelopBillNo(String customsEnvelopBillNo) {
		this.customsEnvelopBillNo = customsEnvelopBillNo;
	}

	public String getListUniteNo() {
		return listUniteNo;
	}

	public void setListUniteNo(String listUniteNo) {
		this.listUniteNo = listUniteNo;
	}

	public String getImpExpFlagToBW() {
		if (impExpFlag != null && impExpFlag.equals(this.IMPORT)) {
			return "I";
		} else {
			return "E";
		}
	}
	public String getImgExgFlag() {
		if (materielProductFlag != null && materielProductFlag.equals(Integer.valueOf(MaterielType.MATERIEL))){
			return "3";
		} else {
			return "4";
		}
	}

	public String getBillType() {
		return "1";
	}

	public String getCustomsFlag() {
		return "2";
	}

	public String getState() {
		return "1";
	}

	public Company getDeclarationCompany() {
		return declarationCompany;
	}

	public void setDeclarationCompany(Company declarationCompany) {
		this.declarationCompany = declarationCompany;
	}

	public String getCatNo() {
		return catNo;
	}

	public void setCatNo(String catNo) {
		this.catNo = catNo;
	}

	public String getListQpBillNo() {
		return listQpBillNo;
	}

	public void setListQpBillNo(String listQpBillNo) {
		this.listQpBillNo = listQpBillNo;
	}

	public Boolean getEffectstate() {
		return effectstate;
	}

	public void setEffectstate(Boolean effectstate) {
		this.effectstate = effectstate;
	}



}
