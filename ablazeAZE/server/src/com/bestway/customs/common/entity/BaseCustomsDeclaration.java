package com.bestway.customs.common.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.countrycode.PreDock;
import com.bestway.bcus.custombase.entity.parametercode.BalanceMode;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.pis.entity.BrokerCorp;

/**
 * 所有的报关单表头都是继承于它
 * 
 * @author refdom
 */
public class BaseCustomsDeclaration extends BaseScmEntity {
	/**
	 * 运费率
	 */
	public static final int FREIGHT_RATE = 1;

	/**
	 * 运费单价
	 */
	public static final int FREIGHT_UNITPRICE = 2;

	/**
	 * 运费总价
	 */
	public static final int FREIGHT_TOTALPRICE = 3;

	/**
	 * 保费率
	 */

	public static final int INSURANCE_RATE = 1;

	/**
	 * 保费总价
	 */
	public static final int INSURANCE_TOTALPRICE = 3;

	/**
	 * 杂费率
	 */
	public static final int INCEDENTAL_EXPENSES_RATE = 1;

	/**
	 * 杂费总价
	 */
	public static final int INCEDENTAL_EXPENSES_TOTALPRICE = 3;

	/**
	 * 进出口标志 IMPORT=0; 进口标志 EXPORT=1; 出口标志 SPECIAL=2; 特殊报关单
	 */
	private Integer impExpFlag; // ImpExpFlag

	/**
	 * 用常量类ImpExpType表示 进出口类型 DIRECT_IMPORT = 0; 料件进口 料件资料 直接进口---料件
	 * TRANSFER_FACTORY_IMPORT = 1; 料件转厂 料件资料 转厂进口---料件 BACK_FACTORY_REWORK = 2;
	 * 退厂返工 成品资料 ---成品 GENERAL_TRADE_IMPORT = 3; 一般贸易进口 --- 料件 DIRECT_EXPORT =
	 * 4; 成品出口 成品资料 ---成品 TRANSFER_FACTORY_EXPORT = 5; 转厂出口 成品资料 ---成品
	 * BACK_MATERIEL_EXPORT = 6; 退料出口 料件资料 ---料件 REWORK_EXPORT = 7; 返工复出 成品资料
	 * ---成品 REMIAN_MATERIAL_BACK_PORT = 8; 边角料退港 ----料件
	 * REMIAN_MATERIAL_DOMESTIC_SALES = 9; 边角料内销 ---料件 GENERAL_TRADE_EXPORT =
	 * 10; 一般贸易出口 ---成品 EQUIPMENT_IMPORT = 11; 设备进口 BACK_PORT_REPAIR = 12; 退港维修
	 * EQUIPMENT_BACK_PORT = 13; 设备退港 REMAIN_FORWARD_EXPORT = 14; 余料结转(出口报关单)
	 * EXPORT_STORAGE = 15; 出口仓储 IMPORT_STORAGE = 16; 进口仓储
	 * MATERIAL_DOMESTIC_SALES = 17; 料件内销，海关批准内销 ---料件 MATERIAL_EXCHANGE = 18;
	 * 料件退换 ---料件 MATERIAL_REOUT = 19; 料件复出 ---料件 All_Type = 20;
	 * 所有（注意：不存在这样的类型，方便在参数设置中使用到） IMPORT_EXG_BACK = 25; 进料成品退换 ---成品
	 * IMPORT_REPAIR_MATERIAL = 26; 修理物品 ---成品 EXPORT_MATERIAL_REBACK = 27;
	 * 修理物品复出 ---成品 EXPORT_EXG_REBACK = 28; 进料成品退换复出 ---成品 REMAIN_FORWARD_IMPORT
	 * = 21; 余料结转(进口报关单)
	 */
	private Integer impExpType;

	/**
	 * 报关单流水号
	 */
	private Integer serialNumber;

	/**
	 * 临时流水号
	 */
	private String tempSerial;

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
	private String tradeName;

	/**
	 * 报关清单号码
	 */
	private String billListId;

	/**
	 * 预录入号
	 */
	private String preCustomsDeclarationCode; // 000008

	/**
	 * 临时预录入号
	 */
	private String tempPreCode; // 99970267576000008

	/**
	 * 统一编号
	 */
	private String unificationCode;

	/**
	 * 是否生效
	 */
	private Boolean effective = false;

	/**
	 * 是否作废
	 */
	private Boolean cancel = false;

	/**
	 * 转关确认
	 */
	private Boolean confirmTransferCIQ = false;

	/**
	 * 是否已经申报
	 */
	private Boolean isSend = false;

	/**
	 * 是否已经核销(现只用于电子帐册)
	 */
	private Boolean isEmsCav = false;

	/**
	 * 进/出口岸
	 */
	private Customs customs;

	/**
	 * 报关单号
	 */
	private String customsDeclarationCode;

	/**
	 * 入库报关单号
	 */
	private String customsDeclarationCodeinHouse;

	/**
	 * 进出口日期
	 */
	private Date impExpDate;

	/**
	 * 申报日期
	 */
	private Date declarationDate;

	/**
	 * 收货单位代码
	 */
	private String machCode;

	/**
	 * 收货单位名称
	 */
	private String machName;

	/**
	 * 运输方式
	 */
	private Transf transferMode;

	/**
	 * 运输工具
	 */
	private String conveyance;

	/**
	 * 提运单号
	 */
	private String billOfLading;

	/**
	 * 贸易方式
	 */
	private Trade tradeMode;

	/**
	 * 征免性质
	 */
	private LevyKind levyKind;

	/**
	 * 征税比例
	 */
	private Double perTax;

	/**
	 * 许可证编号
	 */
	private String license;

	/**
	 * 起运国
	 */
	private Country countryOfLoadingOrUnloading;

	/**
	 * 境内目的地
	 */
	private District domesticDestinationOrSource;

	/**
	 * 装货港口
	 */
	private PortLin portOfLoadingOrUnloading;

	/**
	 * 批准文号
	 */
	// (外汇核销单号)
	private String authorizeFile;

	/**
	 * 合同协议号
	 */
	// （电子备案进出口合同协议号）
	private String contract;

	/**
	 * 成交方式
	 */
	private Transac transac;

	/**
	 * 运费类型 FREIGHT_RATE = 1; 运费率 FREIGHT_UNITPRICE = 2; 运费单价 FREIGHT_TOTALPRICE
	 * = 3; 运费总价
	 */
	private Integer freightageType;

	/**
	 * 运费
	 */
	private Double freightage;

	/**
	 * 运费币制
	 */
	private Curr feeCurr;

	/**
	 * 保险费类型 INSURANCE_RATE = 1; 保费率 INSURANCE_TOTALPRICE = 3; 保费总价
	 */
	private Integer insuranceType;

	/**
	 * 保险费
	 */
	private Double insurance;

	/**
	 * 保费币制
	 */
	private Curr insurCurr;

	/**
	 * 杂费类型 INCEDENTAL_EXPENSES_RATE = 1; 杂费率 INCEDENTAL_EXPENSES_TOTALPRICE =
	 * 3; 杂费总价
	 */
	private Integer incidentalExpensesType;

	/**
	 * 杂费
	 */
	private Double incidentalExpenses;

	/**
	 * 杂费币制
	 */
	private Curr otherCurr;

	/**
	 * 件数
	 */
	private Integer commodityNum;

	/**
	 * 包装种类
	 */
	private Wrap wrapType;

	/**
	 * 毛重
	 */
	private Double grossWeight;

	/**
	 * 净重
	 */
	private Double netWeight;

	/**
	 * 集装箱号
	 */
	private String containerNum;

	/**
	 * 随附单据 <所有的单证代码组合>
	 */
	private String attachedBill;

	/**
	 * 用途
	 */
	private Uses uses;

	/**
	 * 申报单位
	 */
	private Company declarationCompany;

	/**
	 * 币别
	 */
	private Curr currency;

	/**
	 * 汇率
	 */
	private Double exchangeRate;

	/**
	 * 录入员
	 */
	private AclUser creater;

	/**
	 * 申报人员
	 */
	private AclUser declarant;

	// /**
	// * 录入时间
	// */
	// private Date createrDate;

	/**
	 * 客户
	 */
	private ScmCoc customer;

	/**
	 * 报送海关
	 */
	private Customs declarationCustoms;

	/**
	 * 码头
	 */
	private PreDock predock;

	/**
	 * 备注
	 */
	private String memos;

	/**
	 * 备注的证件代码
	 */
	private String certificateCode = null;

	/**
	 * 报关员
	 */
	private String customser;

	/**
	 * 电话
	 */
	private String telephone;

	/**
	 * 条形码
	 */
	private String barCode;

	/**
	 * 生产厂商
	 */
	private Brief manufacturer;

	/**
	 * 所有集装箱号
	 */
	private String allContainerNum0;

	/**
	 * 所有集装箱号
	 */
	private String allContainerNum1;

	/**
	 * 所有集装箱号
	 */
	private String allContainerNum2;

	/**
	 * 结汇方式
	 */
	private BalanceMode balanceMode;

	// 转关运输部分
	/**
	 * 境外运输工具编号
	 */
	private String overseasConveyanceCode;

	/**
	 * 境外运输工具名字
	 */
	private String overseasConveyanceName;

	/**
	 * 境外运输工具航次
	 */
	private String overseasConveyanceFlights;

	/**
	 * 境外运输工具提单号
	 */
	private String overseasConveyanceBillOfLading;

	/**
	 * 境内运输方式
	 */
	private Transf domesticTransferMode;

	/**
	 * 境内运输工具编号
	 */
	private String domesticConveyanceCode;

	/**
	 * 境内运输工具名字
	 */
	private String domesticConveyanceName;

	/**
	 * 境内运输工具航次
	 */
	private String domesticConveyanceFlights;

	/**
	 * 有无纸报关
	 */
	private String isNoBumf;

	/**
	 * 保税仓库
	 */
	private String bondedWarehouse = null;

	/**
	 * 是否检查
	 */
	private Boolean isCheck = false;

	/**
	 * 发票号码
	 */
	private String invoiceCode;

	/**
	 * 关封号
	 */
	private String customsEnvelopBillNo;

	/**
	 * 装箱单
	 */
	private String wlserialNumber;

	/**
	 * KR#
	 */
	private String kr;

	/**
	 * 总金额
	 */
	private Double totalMoney;

	/**
	 * 扩展字段1,此字段已经被群光车次信息征用了
	 */
	private String extendField1;

	/**
	 * 扩展字段2 群光进出口编号
	 */
	private String extendField2;
	/**
	 * 扩展字段3 东聚插件有用到
	 */
	private Integer extendIntgerField3;

	/**
	 * 用于京瓷光学之报检预录入号栏位(在插件中操作)
	 */
	private String extendField3;

	/**
	 * 区分来料、三资设备 SHANZHI=1; 三资设备 LAILIAO=2; 来料设备
	 */
	private Integer fixType;

	/**
	 * 生效时间
	 */
	private Date effectiveDate;

	/**
	 * 生效人
	 */
	private String effectiveMan;

	/**
	 * 关联报关单号
	 */
	private String relativeId;
	/**
	 * 关联手册号
	 */
	private String relativeManualNo;

	/**
	 * 集成通任务ID
	 */
	private String tcsTaskId;
	private String espTaskId;
	/**
	 * 集成通回执通知时间
	 */
	private Date tcsResultTime;

	/**
	 * 操作类型 A：报关单上载 B：报关单、转关单上载 C：报关单申报 D：报关单、转关单申报
	 * E：电子手册报关单上载（此种操作类型的报关单上载时不作非空和逻辑校验） G：报关单暂存
	 */
	private String operType;

	/**
	 * 集成通回执结果信息
	 */
	private String tcsResultMessage;

	/**
	 * 报关单类型
	 */
	private String tcsEntryType;

	/**
	 * 报关标志
	 */
	private String tcsEdiId;

	/**
	 * EDI备注
	 */
	private String tcsType;
	/**
	 * 协同任务备注
	 */
	private String tcsNote;

	/**
	 * 报文传输时间
	 */
	private Date tcsSendTime;

	/**
	 * 补充报关单类型
	 */
	private Integer supplmentType;

	/**
	 * 深加工结转业务key
	 */
	private String btplsKey;
	/**
	 * 申报报关行
	 */
	private String declaraCustomsBroker;

	/**
	 * 报关代理公司
	 */
	private BrokerCorp brokerCorp;

	/**
	 * 航次号
	 */
	private String voyageNo;

	/**
	 * 航次号
	 */
	public String getVoyageNo() {
		return voyageNo;
	}

	/**
	 * 航次号
	 */
	public void setVoyageNo(String voyageNo) {
		this.voyageNo = voyageNo;
	}

	/**
	 * 获取报关代理公司
	 */
	public BrokerCorp getBrokerCorp() {
		return brokerCorp;
	}

	/**
	 * 设置报关代理公司
	 */
	public void setBrokerCorp(BrokerCorp brokerCorp) {
		this.brokerCorp = brokerCorp;
	}

	/**
	 * @return the declaraCustomsBroker
	 */
	public String getDeclaraCustomsBroker() {
		return declaraCustomsBroker;
	}

	/**
	 * @param declaraCustomsBroker
	 *            the declaraCustomsBroker to set
	 */
	public void setDeclaraCustomsBroker(String declaraCustomsBroker) {
		this.declaraCustomsBroker = declaraCustomsBroker;
	}

	public String getBtplsKey() {
		return btplsKey;
	}

	public void setBtplsKey(String btplsKey) {
		this.btplsKey = btplsKey;
	}

	public Integer getSupplmentType() {
		return supplmentType;
	}

	public void setSupplmentType(Integer supplmentType) {
		this.supplmentType = supplmentType;
	}

	public String getTcsNote() {
		return tcsNote;
	}

	public void setTcsNote(String tcsNote) {
		this.tcsNote = tcsNote;
	}

	public Date getTcsSendTime() {
		return tcsSendTime;
	}

	public void setTcsSendTime(Date tcsSendTime) {
		this.tcsSendTime = tcsSendTime;
	}

	public String getTcsEntryType() {
		return tcsEntryType;
	}

	public void setTcsEntryType(String tcsEntryType) {
		this.tcsEntryType = tcsEntryType;
	}

	public String getTcsEdiId() {
		return tcsEdiId;
	}

	public void setTcsEdiId(String tcsEdiId) {
		this.tcsEdiId = tcsEdiId;
	}

	public String getTcsType() {
		return tcsType;
	}

	public void setTcsType(String tcsType) {
		this.tcsType = tcsType;
	}

	public String getTcsTaskId() {
		return tcsTaskId;
	}

	public void setTcsTaskId(String tcsTaskId) {
		this.tcsTaskId = tcsTaskId;
	}

	public Date getTcsResultTime() {
		return tcsResultTime;
	}

	public void setTcsResultTime(Date tcsResultTime) {
		this.tcsResultTime = tcsResultTime;
	}

	// public String getTcsResult() {
	// return tcsResult;
	// }
	//
	// public void setTcsResult(String tcsResult) {
	// this.tcsResult = tcsResult;
	// }

	public String getTcsResultMessage() {
		return tcsResultMessage;
	}

	public void setTcsResultMessage(String tcsResultMessage) {
		this.tcsResultMessage = tcsResultMessage;
	}

	/**
	 * 获取关联报关单号
	 */
	public String getRelativeId() {
		return relativeId;
	}

	/**
	 * 设置关联报关单号
	 */
	public void setRelativeId(String connectDeclarationNo) {
		this.relativeId = connectDeclarationNo;
	}

	/**
	 * 获取关联手册号
	 */
	public String getRelativeManualNo() {
		return relativeManualNo;
	}

	/**
	 * 设置关联手册号
	 */
	public void setRelativeManualNo(String connectNo) {
		this.relativeManualNo = connectNo;
	}

	/**
	 * 取得结费方式
	 * 
	 * @return 结费方式
	 */
	public BalanceMode getBalanceMode() {
		return balanceMode;
	}

	/**
	 * 设置结费方式
	 * 
	 * @param balanceMode
	 *            结费方式
	 */
	public void setBalanceMode(BalanceMode balanceMode) {
		this.balanceMode = balanceMode;
	}

	/**
	 * 取得报关清单号码
	 * 
	 * @return 报关清单号码
	 */
	public String getBillListId() {
		return billListId;
	}

	/**
	 * 设置报关清单号码
	 * 
	 * @param billListId
	 *            报关清单号码
	 */
	public void setBillListId(String billListId) {
		this.billListId = billListId;
	}

	/**
	 * 取得条形码
	 * 
	 * @return 条形码
	 */
	public String getBarCode() {
		return barCode;
	}

	/**
	 * 设置条形码
	 * 
	 * @param barCode
	 *            条形码
	 */
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	/**
	 * 取得生产厂商
	 * 
	 * @return 生产厂商
	 */
	public Brief getManufacturer() {
		return manufacturer;
	}

	/**
	 * 取得所有集装箱号
	 * 
	 * @return 所有集装箱号
	 */
	public String getAllContainerNum() {
		return (allContainerNum0 == null ? ""
				: allContainerNum0.contains(",") ? (allContainerNum0.substring(
						0, 11) + allContainerNum0.substring(
						allContainerNum0.indexOf(','),
						allContainerNum0.indexOf(',') + 12)) : allContainerNum0
						.substring(0, allContainerNum0.length() > 11 ? 11
								: allContainerNum0.length()))
				+ (allContainerNum1 == null ? ""
						: allContainerNum1.contains(",") ? (allContainerNum1
								.substring(0, 11) + allContainerNum1.substring(
								allContainerNum1.indexOf(','),
								allContainerNum1.indexOf(',') + 12))
								: allContainerNum1.substring(0,
										allContainerNum1.length() > 11 ? 11
												: allContainerNum1.length()))
				+ (allContainerNum2 == null ? ""
						: allContainerNum2.contains(",") ? (allContainerNum2
								.substring(0, 11) + allContainerNum2.substring(
								allContainerNum2.indexOf(','),
								allContainerNum2.indexOf(',') + 12))
								: allContainerNum2.substring(0,
										allContainerNum2.length() > 11 ? 11
												: allContainerNum2.length()));
	}

	/**
	 * 取得所有集装箱号
	 * 
	 * @return 所有集装箱号
	 */
	public String getAllContainerNumLong() {
		return (allContainerNum0 == null ? "" : allContainerNum0
				+ (allContainerNum1 == null ? "" : allContainerNum1)
				+ (allContainerNum2 == null ? "" : allContainerNum2));
	}

	/**
	 * 取得所有集装箱号只截取前11位
	 * 
	 * @return 所有集装箱号前11位
	 */
	public String getAllContainerNumInEleven() {
		String result = "";
		if (getAllContainerNum() != null) {
			String[] strs = getAllContainerNum().split(",");
			for (int i = 0; i < strs.length; i++) {
				if (i == 0) {
					result = strs[i].substring(0, 11);
				} else {
					result = result + " / " + strs[i].substring(0, 11);
				}
			}

		}
		return result;
	}

	public String getAllContainerNum1() {
		return allContainerNum1;
	}

	public void setAllContainerNum1(String allContainerNum1) {
		this.allContainerNum1 = allContainerNum1;
	}

	public String getAllContainerNum2() {
		return allContainerNum2;
	}

	public void setAllContainerNum2(String allContainerNum2) {
		this.allContainerNum2 = allContainerNum2;
	}

	/**
	 * 设置所有集装箱号
	 * 
	 * @param allContainerNum
	 *            所有集装箱号
	 */
	public void setAllContainerNum(String allContainerNum) {
		// if (allContainerNum != null && allContainerNum.getBytes().length >
		// 255) {
		// int subLen = 0;
		// StringBuffer sb = new StringBuffer();
		// char[] charValues = allContainerNum.toCharArray();
		// for (int i = 0; i < charValues.length; i++) {
		// int charLen = String.valueOf(charValues[i]).getBytes().length;
		// if (subLen + charLen <= 255) {
		// sb.append(charValues[i]);
		// subLen += charLen;
		// }
		// }
		// allContainerNum = sb.toString();
		// }
		// this.allContainerNum = allContainerNum;
		setMutipFieldValue(allContainerNum, "allContainerNum0",
				"allContainerNum1", "allContainerNum2");
	}

	/**
	 * 设置生产厂商
	 * 
	 * @param manufacturer
	 *            生产厂商
	 */
	public void setManufacturer(Brief manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * 取得报关员
	 * 
	 * @return 报关员
	 */
	public String getCustomser() {
		return customser;
	}

	/**
	 * 设置报关员
	 * 
	 * @param customser
	 *            报关员
	 */
	public void setCustomser(String customser) {
		this.customser = customser;
	}

	/**
	 * 取得电话
	 * 
	 * @return 电话
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * 设置电话
	 * 
	 * @param telephone
	 *            电话
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * 判断是否作废
	 * 
	 * @return 作废
	 */
	public Boolean getCancel() {
		return cancel;
	}

	/**
	 * 设置是否作废标志
	 * 
	 * @param cancel
	 *            作废
	 */
	public void setCancel(Boolean cancel) {
		this.cancel = cancel;
	}

	/**
	 * 取得杂费
	 * 
	 * @return 杂费
	 */
	public Double getIncidentalExpenses() {
		return incidentalExpenses;
	}

	/**
	 * 设置杂费
	 * 
	 * @param incidentalExpenses
	 *            杂费
	 */
	public void setIncidentalExpenses(Double incidentalExpenses) {
		this.incidentalExpenses = incidentalExpenses;
	}

	/**
	 * 取得杂费类型
	 * 
	 * @return 杂费类型
	 */
	public Integer getIncidentalExpensesType() {
		return incidentalExpensesType;
	}

	/**
	 * 设置杂费类型
	 * 
	 * @param incidentalExpensesType
	 *            杂费类型
	 */
	public void setIncidentalExpensesType(Integer incidentalExpensesType) {
		this.incidentalExpensesType = incidentalExpensesType;
	}

	/**
	 * 判断是否确认转关
	 * 
	 * @return 是否确认转关
	 */
	public Boolean getConfirmTransferCIQ() {
		return confirmTransferCIQ;
	}

	/**
	 * 设置是否确认转关标志
	 * 
	 * @param confirmTransferCIQ
	 *            是否确认转关
	 */
	public void setConfirmTransferCIQ(Boolean confirmTransferCIQ) {
		this.confirmTransferCIQ = confirmTransferCIQ;
	}

	/**
	 * 判断是否生效
	 * 
	 * @return 是否生效
	 */
	public Boolean getEffective() {
		return effective;
	}

	/**
	 * 取得是否生效标志
	 * 
	 * @param effective
	 *            是否生效
	 */
	public void setEffective(Boolean effective) {
		this.effective = effective;
	}

	/**
	 * 取得电子帐册号码(电子帐册手册编号)
	 * 
	 * @return 电子帐册号码(电子帐册手册编号)
	 */
	public String getEmsHeadH2k() {
		return emsHeadH2k;
	}

	/**
	 * 设置电子帐册号码(电子帐册手册编号)
	 * 
	 * @param emsHeadH2k
	 *            电子帐册号码(电子帐册手册编号)
	 */
	public void setEmsHeadH2k(String emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}

	/**
	 * 取得备注内容
	 * 
	 * @return 备注
	 */
	public String getMemos() {
		return memos;
	}

	/**
	 * 填写备注内容
	 * 
	 * @param memos
	 *            备注
	 */
	public void setMemos(String memos) {
		this.memos = memos;
	}

	/**
	 * 取得随附单据
	 * 
	 * @return 随附单据
	 */
	public String getAttachedBill() {
		return attachedBill;
	}

	/**
	 * 设置随附单据
	 * 
	 * @param attachedBill
	 *            随附单据
	 */
	public void setAttachedBill(String attachedBill) {
		this.attachedBill = attachedBill;
	}

	/**
	 * 取得批准文号(外汇核销单号)
	 * 
	 * @return 批准文号(外汇核销单号)
	 */
	public String getAuthorizeFile() {
		return authorizeFile;
	}

	/**
	 * 设置批准文号(外汇核销单号)
	 * 
	 * @param authorizeFile
	 *            批准文号(外汇核销单号)
	 */
	public void setAuthorizeFile(String authorizeFile) {
		this.authorizeFile = authorizeFile;
	}

	/**
	 * 取得提运单号
	 * 
	 * @return 提运单号
	 */
	public String getBillOfLading() {
		return billOfLading;
	}

	/**
	 * 设置提运单号
	 * 
	 * @param billOfLading
	 *            提运单号
	 */
	public void setBillOfLading(String billOfLading) {
		this.billOfLading = billOfLading;
	}

	/**
	 * 取得件数
	 * 
	 * @return 件数
	 */
	public Integer getCommodityNum() {
		return commodityNum;
	}

	/**
	 * 设置件数
	 * 
	 * @param commodityNum
	 *            件数
	 */
	public void setCommodityNum(Integer commodityNum) {
		this.commodityNum = commodityNum;
	}

	/**
	 * 取得集装箱号
	 * 
	 * @return 集装箱号
	 */
	public String getContainerNum() {
		return containerNum;
	}

	/**
	 * 设置集装箱号
	 * 
	 * @param containerNum
	 *            集装箱号
	 */
	public void setContainerNum(String containerNum) {
		this.containerNum = containerNum;
	}

	/**
	 * 取得合同协议号
	 * 
	 * @return 合同协议号
	 */
	public String getContract() {
		return contract;
	}

	/**
	 * 设置合同协议号
	 * 
	 * @param contract
	 *            合同协议号
	 */
	public void setContract(String contract) {
		this.contract = contract;
	}

	/**
	 * 取得运输工具
	 * 
	 * @return 运输工具
	 */
	public String getConveyance() {
		return conveyance;
	}

	/**
	 * 设置运输工具
	 * 
	 * @param conveyance
	 *            运输工具
	 */
	public void setConveyance(String conveyance) {
		this.conveyance = conveyance;
	}

	/**
	 * 取得 起运国 or 抵运国
	 * 
	 * @return 起运国 or 抵运国
	 */
	public Country getCountryOfLoadingOrUnloading() {
		return countryOfLoadingOrUnloading;
	}

	/**
	 * 设置起运国 or 抵运国
	 * 
	 * @param countryOfLoadingOrUnloading
	 *            起运国 or 抵运国
	 */
	public void setCountryOfLoadingOrUnloading(
			Country countryOfLoadingOrUnloading) {
		this.countryOfLoadingOrUnloading = countryOfLoadingOrUnloading;
	}

	/**
	 * 取得录入员
	 * 
	 * @return 录入员
	 */
	public AclUser getCreater() {
		return creater;
	}

	/**
	 * 设置录入员
	 * 
	 * @param creater
	 *            录入员
	 */
	public void setCreater(AclUser creater) {
		this.creater = creater;
	}

	/**
	 * 取得币别
	 * 
	 * @return 币别
	 */
	public Curr getCurrency() {
		return currency;
	}

	/**
	 * 设置币别
	 * 
	 * @param currency
	 *            币别
	 */
	public void setCurrency(Curr currency) {
		this.currency = currency;
	}

	/**
	 * 取得客户
	 * 
	 * @return 客户
	 */
	public ScmCoc getCustomer() {
		return customer;
	}

	/**
	 * 设置客户
	 * 
	 * @param customer
	 *            客户
	 */
	public void setCustomer(ScmCoc customer) {
		this.customer = customer;
	}

	/**
	 * 取得进/出口岸
	 * 
	 * @return 进/出口岸
	 */
	public Customs getCustoms() {
		return customs;
	}

	/**
	 * 设置进/出口岸
	 * 
	 * @param customs
	 *            进/出口岸
	 */
	public void setCustoms(Customs customs) {
		this.customs = customs;
	}

	/**
	 * 取得报关单号
	 * 
	 * @return 报关单号
	 */
	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}

	/**
	 * 设置报关单号
	 * 
	 * @param customsDeclarationCode
	 *            报关单号
	 */
	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}

	/**
	 * 取得申报单位
	 * 
	 * @return 申报单位
	 */
	public Company getDeclarationCompany() {
		return declarationCompany;
	}

	/**
	 * 设置申报单位
	 * 
	 * @param declarationCompany
	 *            申报单位
	 */
	public void setDeclarationCompany(Company declarationCompany) {
		this.declarationCompany = declarationCompany;
	}

	/**
	 * 取得报送海关
	 * 
	 * @return 报送海关
	 */
	public Customs getDeclarationCustoms() {
		return declarationCustoms;
	}

	/**
	 * 设置报送海关
	 * 
	 * @param declarationCustoms
	 *            报送海关
	 */
	public void setDeclarationCustoms(Customs declarationCustoms) {
		this.declarationCustoms = declarationCustoms;
	}

	/**
	 * 取得申报日期
	 * 
	 * @return 申报日期
	 */
	public Date getDeclarationDate() {
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// if (declarationDate != null) {
		// return java.sql.Date.valueOf(dateFormat.format(declarationDate));
		// }
		return declarationDate;
	}

	/**
	 * 设置申报日期
	 * 
	 * @param declarationDate
	 *            申报日期
	 */
	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
	}

	/**
	 * 取得境内运输工具编号
	 * 
	 * @return 境内运输工具编号
	 */
	public String getDomesticConveyanceCode() {
		return domesticConveyanceCode;
	}

	/**
	 * 设置境内运输工具编号
	 * 
	 * @param domesticConveyanceCode
	 *            境内运输工具编号
	 */
	public void setDomesticConveyanceCode(String domesticConveyanceCode) {
		this.domesticConveyanceCode = domesticConveyanceCode;
	}

	/**
	 * 取得境内运输工具航次
	 * 
	 * @return 境内运输工具航次
	 */
	public String getDomesticConveyanceFlights() {
		return domesticConveyanceFlights;
	}

	/**
	 * 设置境内运输工具航次
	 * 
	 * @param domesticConveyanceFlights
	 *            境内运输工具航次
	 */
	public void setDomesticConveyanceFlights(String domesticConveyanceFlights) {
		this.domesticConveyanceFlights = domesticConveyanceFlights;
	}

	/**
	 * 取得境内运输工具名字
	 * 
	 * @return 境内运输工具名字
	 */
	public String getDomesticConveyanceName() {
		return domesticConveyanceName;
	}

	/**
	 * 设置境内运输工具名字
	 * 
	 * @param domesticConveyanceName
	 *            境内运输工具名字
	 */
	public void setDomesticConveyanceName(String domesticConveyanceName) {
		this.domesticConveyanceName = domesticConveyanceName;
	}

	/**
	 * 取得境内目的地 or 境内货源地
	 * 
	 * @return 境内目的地 or 境内货源地
	 */
	public District getDomesticDestinationOrSource() {
		return domesticDestinationOrSource;
	}

	/**
	 * 设置境内目的地 or 境内货源地
	 * 
	 * @param domesticDestinationOrSource
	 *            境内目的地 or 境内货源地
	 */
	public void setDomesticDestinationOrSource(
			District domesticDestinationOrSource) {
		this.domesticDestinationOrSource = domesticDestinationOrSource;
	}

	/**
	 * 取得境内运输方式
	 * 
	 * @return 境内运输方式
	 */
	public Transf getDomesticTransferMode() {
		return domesticTransferMode;
	}

	/**
	 * 设置境内运输方式
	 * 
	 * @param domesticTransferMode
	 *            境内运输方式
	 */
	public void setDomesticTransferMode(Transf domesticTransferMode) {
		this.domesticTransferMode = domesticTransferMode;
	}

	/**
	 * 取得汇率
	 * 
	 * @return 汇率
	 */
	public Double getExchangeRate() {
		return exchangeRate;
	}

	/**
	 * 设置汇率
	 * 
	 * @param exchangeRate
	 *            汇率
	 */
	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * 取得运费
	 * 
	 * @return 运费
	 */
	public Double getFreightage() {
		return freightage;
	}

	/**
	 * 设置运费
	 * 
	 * @param freightage
	 *            运费
	 */
	public void setFreightage(Double freightage) {
		this.freightage = freightage;
	}

	/**
	 * 取得运费类型
	 * 
	 * @return 运费类型
	 */
	public Integer getFreightageType() {
		return freightageType;
	}

	/**
	 * 设置运费类型
	 * 
	 * @param freightageType
	 *            运费类型
	 */
	public void setFreightageType(Integer freightageType) {
		this.freightageType = freightageType;
	}

	/**
	 * 取得毛重
	 * 
	 * @return 毛重
	 */
	public Double getGrossWeight() {
		return grossWeight;
	}

	/**
	 * 设置毛重
	 * 
	 * @param grossWeight
	 *            毛重
	 */
	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	/**
	 * 取得进出口日期
	 * 
	 * @return 进出口日期
	 */
	public Date getImpExpDate() {
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// if (impExpDate != null) {
		// return java.sql.Date.valueOf(dateFormat.format(impExpDate));
		// }
		return impExpDate;
	}

	/**
	 * 设置进出口日期
	 * 
	 * @param impExpDate
	 *            进出口日期
	 */
	public void setImpExpDate(Date impExpDate) {
		this.impExpDate = impExpDate;
	}

	/**
	 * 取得进出口标志
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
	 * 取得进出口类型
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
	 * 取得保险费
	 * 
	 * @return 保险费
	 */
	public Double getInsurance() {
		return insurance;
	}

	/**
	 * 设置保险费
	 * 
	 * @param insurance
	 *            保险费
	 */
	public void setInsurance(Double insurance) {
		this.insurance = insurance;
	}

	/**
	 * 取得保险费类型
	 * 
	 * @return 保险费类型
	 */
	public Integer getInsuranceType() {
		return insuranceType;
	}

	/**
	 * 设置保险费类型
	 * 
	 * @param insuranceType
	 *            保险费类型
	 */
	public void setInsuranceType(Integer insuranceType) {
		this.insuranceType = insuranceType;
	}

	/**
	 * 取得许可证编号
	 * 
	 * @return 许可证编号
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * 设置许可证编号
	 * 
	 * @param license
	 *            许可证编号
	 */
	public void setLicense(String license) {
		this.license = license;
	}

	/**
	 * 取得净重
	 * 
	 * @return 净重
	 */
	public Double getNetWeight() {
		return netWeight;
	}

	/**
	 * 设置净重
	 * 
	 * @param netWeight
	 *            净重
	 */
	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	/**
	 * 取得境外运输工具提单号
	 * 
	 * @return 境外运输工具提单号
	 */
	public String getOverseasConveyanceBillOfLading() {
		return overseasConveyanceBillOfLading;
	}

	/**
	 * 设置境外运输工具提单号
	 * 
	 * @param overseasConveyanceBillOfLading
	 *            境外运输工具提单号
	 */
	public void setOverseasConveyanceBillOfLading(
			String overseasConveyanceBillOfLading) {
		this.overseasConveyanceBillOfLading = overseasConveyanceBillOfLading;
	}

	/**
	 * 取得境外运输工具编号
	 * 
	 * @return 境外运输工具编号
	 */
	public String getOverseasConveyanceCode() {
		return overseasConveyanceCode;
	}

	/**
	 * 设置境外运输工具编号
	 * 
	 * @param overseasConveyanceCode
	 *            境外运输工具编号
	 */
	public void setOverseasConveyanceCode(String overseasConveyanceCode) {
		this.overseasConveyanceCode = overseasConveyanceCode;
	}

	/**
	 * 取得境外运输工具航次
	 * 
	 * @return 境外运输工具航次
	 */
	public String getOverseasConveyanceFlights() {
		return overseasConveyanceFlights;
	}

	/**
	 * 设置境外运输工具航次
	 * 
	 * @param overseasConveyanceFlights
	 *            境外运输工具航次
	 */
	public void setOverseasConveyanceFlights(String overseasConveyanceFlights) {
		this.overseasConveyanceFlights = overseasConveyanceFlights;
	}

	/**
	 * 取得境外运输工具名字
	 * 
	 * @return 境外运输工具名字
	 */
	public String getOverseasConveyanceName() {
		return overseasConveyanceName;
	}

	/**
	 * 设置境外运输工具名字
	 * 
	 * @param overseasConveyanceName
	 *            境外运输工具名字
	 */
	public void setOverseasConveyanceName(String overseasConveyanceName) {
		this.overseasConveyanceName = overseasConveyanceName;
	}

	/**
	 * 取得征税比例
	 * 
	 * @return 征税比例
	 */
	public Double getPerTax() {
		return perTax;
	}

	/**
	 * 设置征税比例
	 * 
	 * @param perTax
	 *            征税比例
	 */
	public void setPerTax(Double perTax) {
		this.perTax = perTax;
	}

	/**
	 * 取得装货港口 or 指运港口
	 * 
	 * @return 装货港口 or 指运港口
	 */
	public PortLin getPortOfLoadingOrUnloading() {
		return portOfLoadingOrUnloading;
	}

	/**
	 * 设置装货港口 or 指运港口
	 * 
	 * @param portOfLoadingOrUnloading
	 *            装货港口 or 指运港口
	 */
	public void setPortOfLoadingOrUnloading(PortLin portOfLoadingOrUnloading) {
		this.portOfLoadingOrUnloading = portOfLoadingOrUnloading;
	}

	/**
	 * 取得预录入号
	 * 
	 * @return 预录入号
	 */
	public String getPreCustomsDeclarationCode() {
		return preCustomsDeclarationCode;
	}

	/**
	 * 设置预录入号
	 * 
	 * @param preCustomsDeclarationCode
	 *            预录入号
	 */
	public void setPreCustomsDeclarationCode(String preCustomsDeclarationCode) {
		this.preCustomsDeclarationCode = preCustomsDeclarationCode;
	}

	/**
	 * 取得码头
	 * 
	 * @return 码头
	 */
	public PreDock getPredock() {
		return predock;
	}

	/**
	 * 设置码头
	 * 
	 * @param predock
	 *            码头
	 */
	public void setPredock(PreDock predock) {
		this.predock = predock;
	}

	/**
	 * 取得贸易方式
	 * 
	 * @return 贸易方式
	 */
	public Trade getTradeMode() {
		return tradeMode;
	}

	/**
	 * 设置贸易方式
	 * 
	 * @param tradeMode
	 *            贸易方式
	 */
	public void setTradeMode(Trade tradeMode) {
		this.tradeMode = tradeMode;
	}

	/**
	 * 取得成交方式
	 * 
	 * @return 成交方式
	 */
	public Transac getTransac() {
		return transac;
	}

	/**
	 * 设置成交方式
	 * 
	 * @param transac
	 *            成交方式
	 */
	public void setTransac(Transac transac) {
		this.transac = transac;
	}

	/**
	 * 取得运输方式
	 * 
	 * @return 运输方式
	 */
	public Transf getTransferMode() {
		return transferMode;
	}

	/**
	 * 设置运输方式
	 * 
	 * @param transferMode
	 *            运输方式
	 */
	public void setTransferMode(Transf transferMode) {
		this.transferMode = transferMode;
	}

	/**
	 * 取得统一编号
	 * 
	 * @return 统一编号
	 */
	public String getUnificationCode() {
		return unificationCode;
	}

	/**
	 * 设置统一编号
	 * 
	 * @param unificationCode
	 *            统一编号
	 */
	public void setUnificationCode(String unificationCode) {
		this.unificationCode = unificationCode;
	}

	/**
	 * 取得用途
	 * 
	 * @return 用途
	 */
	public Uses getUses() {
		return uses;
	}

	/**
	 * 设置用途
	 * 
	 * @param uses
	 *            用途
	 */
	public void setUses(Uses uses) {
		this.uses = uses;
	}

	/**
	 * 取得包装种类
	 * 
	 * @return 包装种类
	 */
	public Wrap getWrapType() {
		return wrapType;
	}

	/**
	 * 设置包装种类
	 * 
	 * @param wrapType
	 *            包装种类
	 */
	public void setWrapType(Wrap wrapType) {
		this.wrapType = wrapType;
	}

	/**
	 * 取得有无纸报关("W"表示无纸，"0"表示有纸)
	 * 
	 * @return 有无纸报关("W"表示无纸，"0"表示有纸)
	 */
	public String getIsNoBumf() {
		return isNoBumf;
	}

	/**
	 * 设置有无纸报关("W"表示无纸，"0"表示有纸)
	 * 
	 * @param isNoBumf
	 *            有无纸报关("W"表示无纸，"0"表示有纸)
	 */
	public void setIsNoBumf(String isNoBumf) {
		this.isNoBumf = isNoBumf;
	}

	/**
	 * 取得经营单位代码
	 * 
	 * @return 经营单位代码
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
	 * 取得征免性质
	 * 
	 * @return 征免性质
	 */
	public LevyKind getLevyKind() {
		return levyKind;
	}

	/**
	 * 设置征免性质
	 * 
	 * @param levyKind
	 *            征免性质
	 */
	public void setLevyKind(LevyKind levyKind) {
		this.levyKind = levyKind;
	}

	/**
	 * 取得收货单位代码
	 * 
	 * @return 收货单位代码
	 */
	public String getMachCode() {
		return machCode;
	}

	/**
	 * 取得收货单位名称
	 * 
	 * @return 收货单位名称
	 */
	public String getMachName() {
		return machName;
	}

	/**
	 * 设置收货单位代码
	 * 
	 * @param machCode
	 *            收货单位代码
	 */
	public void setMachCode(String machCode) {
		this.machCode = machCode;
	}

	/**
	 * 设置收货单位名称
	 * 
	 * @param machName
	 *            收货单位名称
	 */
	public void setMachName(String machName) {
		this.machName = machName;
	}

	/**
	 * 取得备注的证件代码
	 * 
	 * @return 备注的证件代码
	 */
	public String getCertificateCode() {
		return certificateCode;
	}

	/**
	 * 设置备注的证件代码
	 * 
	 * @param certificateCode
	 *            备注的证件代码
	 */
	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}

	/**
	 * 取得保税仓库
	 * 
	 * @return 保税仓库
	 */
	public String getBondedWarehouse() {
		return bondedWarehouse;
	}

	/**
	 * 设置保税仓库
	 * 
	 * @param bondedWarehouse
	 *            保税仓库
	 */
	public void setBondedWarehouse(String bondedWarehouse) {
		this.bondedWarehouse = bondedWarehouse;
	}

	/**
	 * 判断是否检查
	 * 
	 * @return 是否检查
	 */
	public Boolean getIsCheck() {
		return isCheck;
	}

	/**
	 * 设置是否检查标志
	 * 
	 * @param isCheck
	 *            是否检查
	 */
	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}

	/**
	 * 取得入库报关单号
	 * 
	 * @return 入库报关单号
	 */
	public String getCustomsDeclarationCodeinHouse() {
		return customsDeclarationCodeinHouse;
	}

	/**
	 * 设置入库报关单号
	 * 
	 * @param customsDeclarationCodeinHouse
	 *            入库报关单号
	 */
	public void setCustomsDeclarationCodeinHouse(
			String customsDeclarationCodeinHouse) {
		this.customsDeclarationCodeinHouse = customsDeclarationCodeinHouse;
	}

	/**
	 * 判断是否已经申报
	 * 
	 * @return 是否已经申报
	 */
	public Boolean getIsSend() {
		return isSend;
	}

	/**
	 * 设置是否已经申报标志
	 * 
	 * @param isSend
	 *            是否已经申报
	 */
	public void setIsSend(Boolean isSend) {
		this.isSend = isSend;
	}

	/**
	 * 取得临时预录入号 //9997045000008
	 * 
	 * @return 临时预录入号 //9997045000008
	 */
	public String getTempPreCode() {
		return tempPreCode;
	}

	/**
	 * 设置临时预录入号 //9997045000008
	 * 
	 * @param tempPreCode
	 *            临时预录入号 //9997045000008
	 */
	public void setTempPreCode(String tempPreCode) {
		this.tempPreCode = tempPreCode;
	}

	/**
	 * 取得发票号码
	 * 
	 * @return 发票号码
	 */
	public String getInvoiceCode() {
		return invoiceCode;
	}

	/**
	 * 设置发票号码
	 * 
	 * @param invoiceCode
	 *            发票号码
	 */
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	/**
	 * 设置关封号码
	 */
	public void setCustomsEnvelopBillNo(String customsEnvelopBillNo) {
		this.customsEnvelopBillNo = customsEnvelopBillNo;
	}

	/**
	 * 取得关封号码
	 * 
	 * @return 关封号码
	 */
	public String getCustomsEnvelopBillNo() {
		return customsEnvelopBillNo;
	}

	/**
	 * 取得报关单流水号
	 * 
	 * @return 报关单流水号
	 */
	public Integer getSerialNumber() {
		return serialNumber;
	}

	/**
	 * 设置报关单流水号
	 * 
	 * @param serialNumber
	 *            报关单流水号
	 */
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * 取得临时流水号（明门使用）
	 * 
	 * @return 临时流水号（明门使用）
	 */
	public String getTempSerial() {
		return tempSerial;
	}

	/**
	 * 设置临时流水号（明门使用）
	 * 
	 * @param tempSerial
	 *            临时流水号（明门使用）
	 */
	public void setTempSerial(String tempSerial) {
		this.tempSerial = tempSerial;
	}

	/**
	 * 取得报关单流水号
	 * 
	 * @return 报关单流水号
	 */
	public String getWlserialNumber() {
		return "WL-" + String.valueOf(serialNumber);
	}

	public String getKr() {
		return kr;
	}

	public void setKr(String kr) {
		this.kr = kr;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getExtendField1() {
		return extendField1;
	}

	public void setExtendField1(String extendField1) {
		this.extendField1 = extendField1;
	}

	public String getExtendField2() {
		return extendField2;
	}

	public void setExtendField2(String extendField2) {
		this.extendField2 = extendField2;
	}

	public Integer getFixType() {
		return fixType;
	}

	public void setFixType(Integer fixType) {
		this.fixType = fixType;
	}

	public Boolean getIsEmsCav() {
		return isEmsCav;
	}

	public void setIsEmsCav(Boolean isEmsCav) {
		this.isEmsCav = isEmsCav;
	}

	public String getExtendField3() {
		return extendField3;
	}

	public void setExtendField3(String extendField3) {
		this.extendField3 = extendField3;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getEffectiveMan() {
		return effectiveMan;
	}

	public void setEffectiveMan(String effectiveMan) {
		this.effectiveMan = effectiveMan;
	}

	public String getAllContainerNum0() {
		return allContainerNum0;
	}

	public void setAllContainerNum0(String allContainerNum0) {
		this.allContainerNum0 = allContainerNum0;
	}

	public Integer getExtendIntgerField3() {
		return extendIntgerField3;
	}

	public void setExtendIntgerField3(Integer extendIntgerField3) {
		this.extendIntgerField3 = extendIntgerField3;
	}

	public Curr getFeeCurr() {
		return feeCurr;
	}

	public void setFeeCurr(Curr feeCurr) {
		this.feeCurr = feeCurr;
	}

	public Curr getInsurCurr() {
		return insurCurr;
	}

	public void setInsurCurr(Curr insurCurr) {
		this.insurCurr = insurCurr;
	}

	public Curr getOtherCurr() {
		return otherCurr;
	}

	public void setOtherCurr(Curr otherCurr) {
		this.otherCurr = otherCurr;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public BaseCustomsDeclaration() {
		super();
	}

	public BaseCustomsDeclaration(String id) {
		super();
		this.setId(id);
	}

	public String getEspTaskId() {
		return espTaskId;
	}

	public void setEspTaskId(String espTaskId) {
		this.espTaskId = espTaskId;
	}

	public AclUser getDeclarant() {
		return declarant;
	}

	public void setDeclarant(AclUser declarant) {
		this.declarant = declarant;
	}

	public BaseCustomsDeclaration(String id, Integer impExpFlag,
			Integer impExpType, Integer serialNumber, String emsHeadH2k,
			String tradeCode, String billListId,
			String preCustomsDeclarationCode, Boolean effective,
			String customsDeclarationCode, Date impExpDate, Date declarationDate) {
		super();
		this.setId(id);
		this.impExpFlag = impExpFlag;
		this.impExpType = impExpType;
		this.serialNumber = serialNumber;
		this.emsHeadH2k = emsHeadH2k;
		this.tradeCode = tradeCode;
		this.billListId = billListId;
		this.preCustomsDeclarationCode = preCustomsDeclarationCode;
		this.effective = effective;
		this.customsDeclarationCode = customsDeclarationCode;
		this.impExpDate = impExpDate;
		this.declarationDate = declarationDate;
	}

}
