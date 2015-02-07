package com.bestway.fecav.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 进口报关单冲销
 * @author table="impcustomsdecl"
 * 
 */
public class ImpCustomsDeclaration extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 签收编码
	 */
	private String signInNo;

	/**
	 * 进口报关单ID
	 */
	private String customsDeclarationId;

	/**
	 * 进口报关单号
	 */
	private String customsDeclarationCode;

	/**
	 * 合同号
	 */
	private String contractNo;

	/**
	 * 手册编号
	 */
	private String emsNo;

	/**
	 * 报关单总金额
	 */
	private Double totalMoney;

	/**
	 * 冲销金额
	 */
	private Double strikeMoney;

	/**
	 * 剩余金额
	 */
	private Double remainMoney;

	/**
	 * 币别
	 */
	private Curr curr;

	/**
	 * 申报日期
	 */
	private Date declareDate;

	/**
	 * 白单号
	 */
	private String whiteBillNo;

	/**
	 * 备注
	 */
	private String memo;

	/**
	 * 预录入号
	 */
	private String preCustomsDeclarationCode;

	/**
	 * 贸易方式
	 */
	private String tradeMode;

	/**
	 * 折美元
	 */
	private Double converUSDMoney;

	/**
	 * 是否可核销
	 */
	private Boolean canStrike = false;

	public String getSignInNo() {
		return signInNo;
	}

	public void setSignInNo(String signInNo) {
		this.signInNo = signInNo;
	}

	/**
	 * 取得备注
	 * 
	 * @return 备注
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 获得预录入号
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
	 * 获得白单号
	 * 
	 * @return 白单号
	 */
	public String getWhiteBillNo() {
		return whiteBillNo;
	}

	/**
	 * 设置白单号
	 * 
	 * @param whiteBillNo
	 *            白单号
	 */
	public void setWhiteBillNo(String whiteBillNo) {
		if (whiteBillNo != null && "".equals(whiteBillNo.trim())) {
			whiteBillNo = null;
		}
		this.whiteBillNo = whiteBillNo;
	}

	/**
	 * 获得合同号
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
	 * 获得进口报关单号
	 * 
	 * @return 进口报关单号
	 */
	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}

	/**
	 * 设置进口报关单号
	 * 
	 * @param customsDeclarationCode
	 *            进口报关单号
	 */
	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}

	/**
	 * 获得手册编号
	 * 
	 * @return 手册编号
	 */
	public String getEmsNo() {
		return emsNo;
	}

	/**
	 * 设置手册编号
	 * 
	 * @param emsNo
	 *            手册编号
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	/**
	 * 获得进口报关单ID
	 * 
	 * @return 进口报关单ID
	 */
	public String getCustomsDeclarationId() {
		return customsDeclarationId;
	}

	/**
	 * 生成进口报关单ID
	 * 
	 * @param customsDeclarationId
	 *            进口报关单ID
	 */
	public void setCustomsDeclarationId(String customsDeclarationId) {
		this.customsDeclarationId = customsDeclarationId;
	}

	/**
	 * 获得剩余金额
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
	 * 获得冲销金额
	 * 
	 * @return 冲销金额
	 */
	public Double getStrikeMoney() {
		return strikeMoney;
	}

	/**
	 * 设置冲销金额
	 * 
	 * @param strikeMoney
	 *            冲销金额
	 */
	public void setStrikeMoney(Double strikeMoney) {
		this.strikeMoney = strikeMoney;
	}

	/**
	 * 获得报关单总金额
	 * 
	 * @return 报关单总金额
	 */
	public Double getTotalMoney() {
		return totalMoney;
	}

	/**
	 * 设置报关单总金额
	 * 
	 * @param totalMoney
	 *            报关单总金额
	 */
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	/**
	 * 获得币别
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
	 * 获得申报日期
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
	 * 获得贸易方式
	 * 
	 * @return 贸易方式
	 */
	public String getTradeMode() {
		return tradeMode;
	}

	/**
	 * 设置贸易方式
	 * 
	 * @param tradeMode
	 *            贸易方式
	 */
	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}

	public Double getConverUSDMoney() {
		return converUSDMoney;
	}

	public void setConverUSDMoney(Double converUSDMoney) {
		this.converUSDMoney = converUSDMoney;
	}

	public Boolean getCanStrike() {
		return canStrike;
	}

	public void setCanStrike(Boolean canStrike) {
		this.canStrike = canStrike;
	}

}
