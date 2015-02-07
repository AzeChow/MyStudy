package com.bestway.fecav.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.AclUser;

/**
 * 外汇核销单
 * 
 * @author wuzepei table="fecavbill"
 * 
 */
public class FecavBill extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 外汇核销单冲销
	 */
	private FecavBillStrike fecavBillStrike;

	/**
	 * 核销单号
	 */
	private String code;

	/**
	 * 报关单ID
	 */
	private String customsDeclarationId;

	/**
	 * 报关单号
	 */
	private String customsDeclarationCode;

	/**
	 * 出口日期
	 */
	private Date exportDate;

	/**
	 * 申报日期
	 */
	private Date declareDate;

	/**
	 * 合同号码
	 */
	private String contractNo;

	/**
	 * 手册号码
	 */
	private String emsNo;

	/**
	 * 币别
	 */
	private Curr curr;

	/**
	 * 报关单总金额
	 */
	private Double totalPrice;

	/**
	 * 已冲销金额
	 */
	private Double brikeMoney;

	/**
	 * 剩余金额
	 */
	private Double remainMoney;

	/**
	 * 是否打印白单
	 */
	private Boolean isPrintWhite = false;

	/**
	 * 是否打印黄单
	 */
	private Boolean isPrintYellow = false;

	/**
	 * 外部领用者
	 */
	private String outerObtain;

	/**
	 * 外部领用日期
	 */
	private Date outerObtainDate;

	/**
	 * 外部领用操作者
	 */
	private String outerOperator;

	/**
	 * 外部领用操作日期
	 */
	private Date outerOperatorDate;

	/**
	 * 内部领用者
	 */
	private String innerObtain;

	/**
	 * 内部领用日期
	 */
	private Date innerObtainDate;

	/**
	 * 内部领用操作者
	 */
	private String innerOperator;

	/**
	 * 内部领用操作日期
	 */
	private Date innerOperatorDate;

	/**
	 * 内部进出口岸
	 */
	private Customs impExpCIQ;

	/**
	 * 交单人
	 */
	private String handInBillMan;

	/**
	 * 交单日期
	 */
	private Date handInBillDate;

	/**
	 * 退税签收人
	 */
	private String debentureSignInMan;

	/**
	 * 退税签收日期
	 */
	private Date debentureSignInDate;

	/**
	 * 使用者
	 */
	private String usedMan;

	/**
	 * 使用日期
	 */
	private Date usedDate;

	/**
	 * 单据状态 1:外部领用;2:内部领用; 3:核销单使用; 4:退税联签收; 5:核销员交单; 6:管制; 7:冲销; 8:核销;
	 * 9:财务签收;10:关帐
	 */
	private Integer billState;

	/**
	 * 是否遗失作废
	 */
	private Boolean isBlankOut = false;

	/**
	 * 遗失作废交单人
	 */
	private String blankOutMan;

	/**
	 * 遗失作废日期
	 */
	private Date blankOutDate;

	/**
	 * 作废原因
	 */
	private String blankOutReason;

	/**
	 * 是否选中
	 */
	private Boolean isSelected = false;

	/**
	 * 核销作废日期
	 */
	private Date checkoutdate;

	/**
	 * 折美元
	 */
	private Double converUSDMoney;

	/**
	 * 取得外汇核销单冲销内容
	 * 
	 * @return 外汇核销单冲销
	 */
	public FecavBillStrike getFecavBillStrike() {
		return fecavBillStrike;
	}

	/**
	 * 设置外汇核销单冲销内容
	 * 
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 */
	public void setFecavBillStrike(FecavBillStrike fecavBillStrike) {
		this.fecavBillStrike = fecavBillStrike;
	}

	/**
	 * 取得单据状态
	 * 
	 * @return 单据状态
	 */
	public Integer getBillState() {
		return billState;
	}

	/**
	 * 设置单据状态
	 * 
	 * @param billState
	 *            单据状态
	 */
	public void setBillState(Integer billState) {
		this.billState = billState;
	}

	/**
	 * 取得核销单号
	 * 
	 * @return 核销单号
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置核销单号
	 * 
	 * @param code
	 *            核销单号
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 取得合同号
	 * 
	 * @return 合同号
	 */
	public String getContractNo() {
		return contractNo;
	}

	/**
	 * 设置合同号
	 * 
	 * @param contractNo
	 *            合同号
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * 取得币别
	 * 
	 * @return 币别
	 */
	public Curr getCurr() {
		return curr;
	}

	/**
	 * 设置币别
	 * 
	 * @param curr
	 *            币别
	 */
	public void setCurr(Curr curr) {
		this.curr = curr;
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
	 * 取得报关单ID
	 * 
	 * @return 报关单ID
	 */
	public String getCustomsDeclarationId() {
		return customsDeclarationId;
	}

	/**
	 * 设置报关单ID
	 * 
	 * @param customsDeclarationId
	 *            报关单ID
	 */
	public void setCustomsDeclarationId(String customsDeclarationId) {
		this.customsDeclarationId = customsDeclarationId;
	}

	/**
	 * 取得申报日期
	 * 
	 * @return 申报日期
	 */
	public Date getDeclareDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (declareDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(declareDate));
		}
		return declareDate;
	}

	/**
	 * 设置申报日期
	 * 
	 * @param declareDate
	 *            申报日期
	 */
	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	/**
	 * 取得手册号
	 * 
	 * @return 手册号
	 */
	public String getEmsNo() {
		return emsNo;
	}

	/**
	 * 设置手册号
	 * 
	 * @param emsNo
	 *            手册号
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	/**
	 * 取得出口日期
	 * 
	 * @return 出口日期
	 */
	public Date getExportDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (exportDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(exportDate));
		}
		return exportDate;
	}

	/**
	 * 设置出口日期
	 * 
	 * @param exportDate
	 *            出口日期
	 */
	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}

	/**
	 * 取得内部领用者
	 * 
	 * @return 内部领用者
	 */
	public String getInnerObtain() {
		return innerObtain;
	}

	/**
	 * 设置内部领用者
	 * 
	 * @param innerObtain
	 *            内部领用者
	 */
	public void setInnerObtain(String innerObtain) {
		this.innerObtain = innerObtain;
	}

	/**
	 * 取得内部领用日期
	 * 
	 * @return 内部领用日期
	 */
	public Date getInnerObtainDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (innerObtainDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(innerObtainDate));
		}
		return innerObtainDate;
	}

	/**
	 * 设置内部领用日期
	 * 
	 * @param innerObtainDate
	 *            内部领用日期
	 */
	public void setInnerObtainDate(Date innerObtainDate) {
		this.innerObtainDate = innerObtainDate;
	}

	/**
	 * 取得内部领用操作者
	 * 
	 * @return 内部领用操作者
	 */
	public String getInnerOperator() {
		return innerOperator;
	}

	/**
	 * 设置内部领用操作者
	 * 
	 * @param innerOperator
	 *            内部领用操作者
	 */
	public void setInnerOperator(String innerOperator) {
		this.innerOperator = innerOperator;
	}

	/**
	 * 取得内部领用操作日期
	 * 
	 * @return 内部领用操作日期
	 */
	public Date getInnerOperatorDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (innerOperatorDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(innerOperatorDate));
		}
		return innerOperatorDate;
	}

	/**
	 * 设置内部领用操作日期
	 * 
	 * @param innerOperatorDate
	 *            内部领用操作日期
	 */
	public void setInnerOperatorDate(Date innerOperatorDate) {
		this.innerOperatorDate = innerOperatorDate;
	}

	/**
	 * 取得外部领用者
	 * 
	 * @return 外部领用者
	 */
	public String getOuterObtain() {
		return outerObtain;
	}

	/**
	 * 设置外部领用者
	 * 
	 * @param outerObtain
	 *            外部领用者
	 */
	public void setOuterObtain(String outerObtain) {
		this.outerObtain = outerObtain;
	}

	/**
	 * 取得外部领用日期
	 * 
	 * @return 外部领用日期
	 */
	public Date getOuterObtainDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (outerObtainDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(outerObtainDate));
		}
		return outerObtainDate;
	}

	/**
	 * 设置外部领用日期
	 * 
	 * @param outerObtainDate
	 *            外部领用日期
	 */
	public void setOuterObtainDate(Date outerObtainDate) {
		this.outerObtainDate = outerObtainDate;
	}

	/**
	 * 取得外部领用操作者
	 * 
	 * @return 外部领用操作者
	 */
	public String getOuterOperator() {
		return outerOperator;
	}

	/**
	 * 设置外部领用操作者
	 * 
	 * @param outerOperator
	 *            外部领用操作者
	 */
	public void setOuterOperator(String outerOperator) {
		this.outerOperator = outerOperator;
	}

	/**
	 * 取得外部领用操作日期
	 * 
	 * @return 外部领用操作日期
	 */
	public Date getOuterOperatorDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (outerOperatorDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(outerOperatorDate));
		}
		return outerOperatorDate;
	}

	/**
	 * 设置外部领用操作日期
	 * 
	 * @param outerOperatorDate
	 *            外部领用操作日期
	 */
	public void setOuterOperatorDate(Date outerOperatorDate) {
		this.outerOperatorDate = outerOperatorDate;
	}

	/**
	 * 取得报关单总金额
	 * 
	 * @return 报关单总金额
	 */
	public Double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * 设置报关单总金额
	 * 
	 * @param totalPrice
	 *            报关单总金额
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * 判断是否打印白单
	 * 
	 * @return 是否打印白单
	 */
	public Boolean getIsPrintWhite() {
		return isPrintWhite;
	}

	/**
	 * 设置是否打印白单标志
	 * 
	 * @param isPrintWhite
	 *            是否打印白单
	 */
	public void setIsPrintWhite(Boolean isPrintWhite) {
		this.isPrintWhite = isPrintWhite;
	}

	/**
	 * 判断是否打印黄单
	 * 
	 * @return 是否打印黄单
	 */
	public Boolean getIsPrintYellow() {
		return isPrintYellow;
	}

	/**
	 * 设置是否打印黄单标志
	 * 
	 * @param isPrintYellow
	 *            是否打印黄单
	 */
	public void setIsPrintYellow(Boolean isPrintYellow) {
		this.isPrintYellow = isPrintYellow;
	}

	/**
	 * 判断是否遗失作废
	 * 
	 * @return 是否遗失作废
	 */
	public Boolean getIsBlankOut() {
		return isBlankOut;
	}

	/**
	 * 设置是否遗失作废标志
	 * 
	 * @param isBlankOut
	 *            是否遗失作废
	 */
	public void setIsBlankOut(Boolean isBlankOut) {
		this.isBlankOut = isBlankOut;
	}

	/**
	 * 取得遗失作废原因
	 * 
	 * @return 遗失作废原因
	 */
	public String getBlankOutReason() {
		return blankOutReason;
	}

	/**
	 * 填写遗失作废原因
	 * 
	 * @param blankOutReason
	 *            遗失作废原因
	 */
	public void setBlankOutReason(String blankOutReason) {
		this.blankOutReason = blankOutReason;
	}

	/**
	 * 取得已冲销金额
	 * 
	 * @return 已冲销金额
	 */
	public Double getBrikeMoney() {
		return brikeMoney;
	}

	/**
	 * 设置已冲销金额
	 * 
	 * @param brikeMoney
	 *            已冲销金额
	 */
	public void setBrikeMoney(Double brikeMoney) {
		this.brikeMoney = brikeMoney;
	}

	/**
	 * 取得剩余金额
	 * 
	 * @return 剩余金额
	 */
	public Double getRemainMoney() {
		return remainMoney;
	}

	/**
	 * 设置剩余金额
	 * 
	 * @param remainMoney
	 *            剩余金额
	 */
	public void setRemainMoney(Double remainMoney) {
		this.remainMoney = remainMoney;
	}

	/**
	 * 取得使用日期
	 * 
	 * @return 使用日期
	 */
	public Date getUsedDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (usedDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(usedDate));
		}
		return usedDate;
	}

	/**
	 * 设置使用日期
	 * 
	 * @param usedDate
	 *            使用日期
	 */
	public void setUsedDate(Date usedDate) {
		this.usedDate = usedDate;
	}

	/**
	 * 获得使用人
	 * 
	 * @return 使用人
	 */
	public String getUsedMan() {
		return usedMan;
	}

	/**
	 * 填写使用人
	 * 
	 * @param usedMan
	 *            使用人
	 */
	public void setUsedMan(String usedMan) {
		this.usedMan = usedMan;
	}

	/**
	 * 获得遗失作废交单人
	 * 
	 * @return 遗失作废交单人
	 */
	public String getBlankOutMan() {
		return blankOutMan;
	}

	/**
	 * 填写遗失作废交单人
	 * 
	 * @param blankOutMan
	 *            遗失作废交单人
	 */
	public void setBlankOutMan(String blankOutMan) {
		this.blankOutMan = blankOutMan;
	}

	/**
	 * 取得遗失作废日期
	 * 
	 * @return 遗失作废日期
	 */
	public Date getBlankOutDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (blankOutDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(blankOutDate));
		}
		return blankOutDate;
	}

	/**
	 * 设置遗失作废日期
	 * 
	 * @param blankOutDate
	 *            遗失作废日期
	 */
	public void setBlankOutDate(Date blankOutDate) {
		this.blankOutDate = blankOutDate;
	}

	/**
	 * 取得退税签收日期
	 * 
	 * @return 退税签收日期
	 */
	public Date getDebentureSignInDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (debentureSignInDate != null) {
			return java.sql.Date
					.valueOf(dateFormat.format(debentureSignInDate));
		}
		return debentureSignInDate;
	}

	/**
	 * 设置退税签收日期
	 * 
	 * @param drawbackSignInDate
	 *            退税签收日期
	 */
	public void setDebentureSignInDate(Date drawbackSignInDate) {
		this.debentureSignInDate = drawbackSignInDate;
	}

	/**
	 * 获得退税签收人
	 * 
	 * @return 退税签收人
	 */
	public String getDebentureSignInMan() {
		return debentureSignInMan;
	}

	/**
	 * 填写退税签收人
	 * 
	 * @param drawbackSignInMan
	 *            退税签收人
	 */
	public void setDebentureSignInMan(String drawbackSignInMan) {
		this.debentureSignInMan = drawbackSignInMan;
	}

	/**
	 * 获得交单日期
	 * 
	 * @return 交单日期
	 */
	public Date getHandInBillDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (handInBillDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(handInBillDate));
		}
		return handInBillDate;
	}

	/**
	 * 设置交单日期
	 * 
	 * @param putInBillDate
	 *            交单日期
	 */
	public void setHandInBillDate(Date putInBillDate) {
		this.handInBillDate = putInBillDate;
	}

	/**
	 * 获得交单人
	 * 
	 * @return 交单人
	 */
	public String getHandInBillMan() {
		return handInBillMan;
	}

	/**
	 * 填写交单人
	 * 
	 * @param putInBillMan
	 *            交单人
	 */
	public void setHandInBillMan(String putInBillMan) {
		this.handInBillMan = putInBillMan;
	}

	/**
	 * 判断是否选中
	 * 
	 * @return 是否选中
	 */
	public Boolean getIsSelected() {
		return isSelected;
	}

	/**
	 * 设置是否选中标志
	 * 
	 * @param isSelected
	 *            是否选中
	 */
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * 获得核销作废日期
	 * 
	 * @return 核销作废日期
	 */
	public Date getCheckoutdate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (checkoutdate != null) {
			return java.sql.Date.valueOf(dateFormat.format(checkoutdate));
		}
		return checkoutdate;
	}

	/**
	 * 设置核销作废日期
	 * 
	 * @param checkoutdate
	 *            核销作废日期
	 */
	public void setCheckoutdate(Date checkoutdate) {
		this.checkoutdate = checkoutdate;
	}

	public Double getConverUSDMoney() {
		return converUSDMoney;
	}

	public void setConverUSDMoney(Double converUSDMoney) {
		this.converUSDMoney = converUSDMoney;
	}

	public Customs getImpExpCIQ() {
		return impExpCIQ;
	}

	public void setImpExpCIQ(Customs impExpCIQ) {
		this.impExpCIQ = impExpCIQ;
	}

}
