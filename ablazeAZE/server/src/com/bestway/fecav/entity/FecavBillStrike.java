package com.bestway.fecav.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 外汇核销单冲销
 * @author table="fecavbillstrike"
 */
public class FecavBillStrike extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 签收编码
	 */
	private String signInNo;

	/**
	 * 核销签收者
	 */
	private String cavSignInMan;

	/**
	 * 核销签收日期
	 */
	private Date cavSignInDate;

	/**
	 * 财务签收者
	 */
	private String financeSignInMan;

	/**
	 * 财务签收日期
	 */
	private Date financeSignInDate;

	/**
	 * 冲销者
	 */
	private String strikeMan;

	/**
	 * 冲销期
	 */
	private Date strikeDate;

	/**
	 * 核销者
	 */
	private String cancelMan;

	/**
	 * 核销期
	 */
	private Date cancelDate;

	/**
	 * 关帐人
	 */
	private String closeAccountMan;

	/**
	 * 关帐日期
	 */
	private Date closeAccountDate;

	/**
	 * 待核销值(美元)
	 */
	private Double canStrikeTotalMoney;

	/**
	 * 可用抵扣料值(美元)
	 */
	private Double canStrikeImportMoney;

	/**
	 * 待结汇金额(美元)
	 */
	private Double canStrikeExchangeMoney;

	/**
	 * 已抵扣料值(美元)
	 */
	private Double strikedImportMoney;

	/**
	 * 已结汇金额(美元)
	 */
	private Double strikedExchangeMoney;

	/**
	 * 最终折合币别(一般选取美元)
	 */
	private Curr curr;

	/**
	 * 贸易方式
	 */
	private Trade tradeMode;

	/**
	 * 状态
	 * OUTER_OBTAIN = 1; 	外部领用
	 * INNER_OBTAIN = 2;	内部领用
	 * USED = 3; 	核销单使用
	 * DEBENTURE_SIGN_IN=4;	退税联签收
	 * HAND_IN_BILL=5;	核销员交单
	 * CONTROL=6;	管制
	 * STRIKE_BALANCE=7;	冲销
	 * CANCEL=8;	核销
	 * FINANCE_SIGN_IN=9;	财务签收
	 * CLOSE_ACCOUNT=10;	关帐
	 */
	private Integer fecavState;

	/**
	 * 核销单份数
	 */
	private Integer pieces = 0;

	/**
	 * 项目类型
	 * BCUS = 0;	电子帐册
	 * BCS = 1;	电子化手册
	 * DZSC = 3;	电子手册
	 */
	private Integer projectType;

	/**
	 * 账册/手册号
	 */
	private String emsNo;

	/**
	 * 冲销比例
	 */
	private Double strikeRate;

	/**
	 * 取得待结汇金额
	 * 
	 * @return 待结汇金额
	 */
	public Double getCanStrikeExchangeMoney() {
		return canStrikeExchangeMoney;
	}

	/**
	 * 设置待结汇金额
	 * 
	 * @param canStrikeExchangeMoney
	 *            待结汇金额
	 */
	public void setCanStrikeExchangeMoney(Double canStrikeExchangeMoney) {
		this.canStrikeExchangeMoney = canStrikeExchangeMoney;
	}

	/**
	 * 取得可用抵扣料值
	 * 
	 * @return 可用抵扣料值
	 */
	public Double getCanStrikeImportMoney() {
		return canStrikeImportMoney;
	}

	/**
	 * 设置可用抵扣料值
	 * 
	 * @param canStrikeImportMoney
	 *            可用抵扣料值
	 */
	public void setCanStrikeImportMoney(Double canStrikeImportMoney) {
		this.canStrikeImportMoney = canStrikeImportMoney;
	}

	/**
	 * 取得待核销值
	 * 
	 * @return 待核销值
	 */
	public Double getCanStrikeTotalMoney() {
		return canStrikeTotalMoney;
	}

	/**
	 * 设置待核销值
	 * 
	 * @param canStrikeTotalMoney
	 *            待核销值
	 */
	public void setCanStrikeTotalMoney(Double canStrikeTotalMoney) {
		this.canStrikeTotalMoney = canStrikeTotalMoney;
	}

	/**
	 * 取得核销签收日期
	 * 
	 * @return 核销签收日期
	 */
	public Date getCavSignInDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (cavSignInDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(cavSignInDate));
		}
		return cavSignInDate;
	}

	/**
	 * 设置核销签收日期
	 * 
	 * @param cavSignInDate
	 *            核销签收日期
	 */
	public void setCavSignInDate(Date cavSignInDate) {
		this.cavSignInDate = cavSignInDate;
	}

	/**
	 * 获得核销签收者
	 * 
	 * @return 核销签收者
	 */
	public String getCavSignInMan() {
		return cavSignInMan;
	}

	/**
	 * 填写核销签收者
	 * 
	 * @param cavSignInMan
	 *            核销签收者
	 */
	public void setCavSignInMan(String cavSignInMan) {
		this.cavSignInMan = cavSignInMan;
	}

	/**
	 * 取得财务签收日期
	 * 
	 * @return 财务签收日期
	 */
	public Date getFinanceSignInDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (financeSignInDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(financeSignInDate));
		}
		return financeSignInDate;
	}

	/**
	 * 设置财务签收日期
	 * 
	 * @param financeSignInDate
	 *            财务签收日期
	 */
	public void setFinanceSignInDate(Date financeSignInDate) {
		this.financeSignInDate = financeSignInDate;
	}

	/**
	 * 获得财务签收者
	 * 
	 * @return 财务签收者
	 */
	public String getFinanceSignInMan() {
		return financeSignInMan;
	}

	/**
	 * 填写财务签收者
	 * 
	 * @param financeSignInMan
	 *            财务签收者
	 */
	public void setFinanceSignInMan(String financeSignInMan) {
		this.financeSignInMan = financeSignInMan;
	}

	/**
	 * 取得签收编码
	 * 
	 * @return 签收编码
	 */
	public String getSignInNo() {
		return signInNo;
	}

	/**
	 * 设置签收编码
	 * 
	 * @param signInNo
	 *            签收编码
	 */
	public void setSignInNo(String signInNo) {
		this.signInNo = signInNo;
	}

	/**
	 * 获得已结汇金额
	 * 
	 * @return 已结汇金额
	 */
	public Double getStrikedExchangeMoney() {
		return strikedExchangeMoney;
	}

	/**
	 * 设置已结汇金额
	 * 
	 * @param strikedExchangeMoney
	 *            已结汇金额
	 */
	public void setStrikedExchangeMoney(Double strikedExchangeMoney) {
		this.strikedExchangeMoney = strikedExchangeMoney;
	}

	/**
	 * 获得已抵扣料值
	 * 
	 * @return 已抵扣料值
	 */
	public Double getStrikedImportMoney() {
		return strikedImportMoney;
	}

	/**
	 * 设置已抵扣料值
	 * 
	 * @param strikedImportMoney
	 *            已抵扣料值
	 */
	public void setStrikedImportMoney(Double strikedImportMoney) {
		this.strikedImportMoney = strikedImportMoney;
	}

	/**
	 * 获得最终折合币别(一般选取美元)
	 * 
	 * @return 最终折合币别(一般选取美元)
	 */
	public Curr getCurr() {
		return curr;
	}

	/**
	 * 设置最终折合币别(一般选取美元)
	 * 
	 * @param curr
	 *            最终折合币别(一般选取美元)
	 */
	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	/**
	 * 获得状态
	 * 
	 * @return 状态
	 */
	public Integer getFecavState() {
		return fecavState;
	}

	/**
	 * 设置状态
	 * 
	 * @param fecavState
	 *            状态
	 */
	public void setFecavState(Integer fecavState) {
		this.fecavState = fecavState;
	}

	/**
	 * 获得冲销期
	 * 
	 * @return 冲销期
	 */
	public Date getStrikeDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (strikeDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(strikeDate));
		}
		return strikeDate;
	}

	/**
	 * 设置冲销期
	 * 
	 * @param strikeDate
	 *            冲销期
	 */
	public void setStrikeDate(Date strikeDate) {
		this.strikeDate = strikeDate;
	}

	/**
	 * 获得冲销者
	 * 
	 * @return 冲销者
	 */
	public String getStrikeMan() {
		return strikeMan;
	}

	/**
	 * 获得核销期
	 * 
	 * @return 核销期
	 */
	public Date getCancelDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (cancelDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(cancelDate));
		}
		return cancelDate;
	}

	/**
	 * 设置核销期
	 * 
	 * @param cancelDate
	 *            核销期
	 */
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	/**
	 * 获得核销者
	 * 
	 * @return 核销者
	 */
	public String getCancelMan() {
		return cancelMan;
	}

	/**
	 * 设置核销者
	 * 
	 * @param cancelMan
	 *            核销者
	 */
	public void setCancelMan(String cancelMan) {
		this.cancelMan = cancelMan;
	}

	/**
	 * 获得关帐日期
	 * 
	 * @return 关帐日期
	 */
	public Date getCloseAccountDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (closeAccountDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(closeAccountDate));
		}
		return closeAccountDate;
	}

	/**
	 * 设置关帐日期
	 * 
	 * @param closeAccountDate
	 *            关帐日期
	 */
	public void setCloseAccountDate(Date closeAccountDate) {
		this.closeAccountDate = closeAccountDate;
	}

	/**
	 * 获得关帐人
	 * 
	 * @return 关帐人
	 */
	public String getCloseAccountMan() {
		return closeAccountMan;
	}

	/**
	 * 填写关帐人
	 * 
	 * @param closeAccountMan
	 *            关帐人
	 */
	public void setCloseAccountMan(String closeAccountMan) {
		this.closeAccountMan = closeAccountMan;
	}

	/**
	 * 填写冲销者
	 * 
	 * @param strikeMan
	 *            冲销者
	 */
	public void setStrikeMan(String strikeMan) {
		this.strikeMan = strikeMan;
	}

	/**
	 * 获得核销单份数
	 * 
	 * @return 核销单份数
	 */
	public Integer getPieces() {
		return pieces;
	}

	/**
	 * 设置核销单份数
	 * 
	 * @param pieces
	 *            核销单份数
	 */
	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}

	/**
	 * 获得贸易方式
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

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	public Integer getProjectType() {
		return projectType;
	}

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	public Double getStrikeRate() {
		return strikeRate;
	}

	public void setStrikeRate(Double strikeRate) {
		this.strikeRate = strikeRate;
	}

	public Double getRemainStrikeExchangeMoney() {
		return (this.canStrikeExchangeMoney == null ? 0.0
				: this.canStrikeExchangeMoney)
				- (this.strikedExchangeMoney == null ? 0.0
						: this.strikedExchangeMoney);
	}

	public Double getRemainStrikeImportMoney() {
		return (this.canStrikeImportMoney == null ? 0.0
				: this.canStrikeImportMoney)
				- (this.strikedImportMoney == null ? 0.0
						: this.strikedImportMoney);
	}
}
