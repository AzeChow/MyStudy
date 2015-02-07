/*
 * Created on 2004-9-14
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.bill.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 单据表头
 */
public class BillMaster extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 单据类型
	 */
	private BillType billType;
	/**
	 * 单据号码
	 */
	private String billNo;
	/**
	 * 是否生效
	 */
	private Boolean isValid = false;
	/**
	 * 生效日期
	 */
	private Date validDate;
	/**
	 * 是否记帐
	 */
	private Boolean keepAccounts = false;
	/**
	 * 备注
	 */
	private String notice;
	/**
	 * 客户
	 */
	private ScmCoc scmCoc;
	/**
	 * 是否有效
	 */
	private Boolean isFlag = false;
	/**
	 * 关封号
	 */
	private String envelopNo = null;
	/**
	 * 序号（临时存放）
	 */
	private String tempNo; // 单据类型代码

	/**
	 * 序号（临时存放日期）
	 */
	private String tempNo1; // 日期

	/**
	 * 序号（临时存放供应商代码）
	 */
	private String tempNo2; // 供应商代码

	/**
	 * 序号（临时存放其他字段）
	 */
	private String tempNo3; // 其他字段

	/**
	 * 取得单据号码
	 * 
	 * @return billNo 单据号码.
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * 设置单据号码
	 * 
	 * @param billNo
	 *            单据号码
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * 取得单据类型
	 * 
	 * @return billType 单据类型.
	 */
	public BillType getBillType() {
		return billType;
	}

	/**
	 * 设置单据类型
	 * 
	 * @param billType
	 *            单据类型
	 */
	public void setBillType(BillType billType) {
		this.billType = billType;
	}

	/**
	 * 取得是否生效标志
	 * 
	 * @return isValid 是否生效.
	 */
	public Boolean getIsValid() {
		return isValid;
	}

	/**
	 * 设置是否生效标志
	 * 
	 * @param isValid
	 *            是否生效
	 */
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	/**
	 * 取得是否记帐标志
	 * 
	 * @return keepAccounts 是否记帐.
	 */
	public Boolean getKeepAccounts() {
		return keepAccounts;
	}

	/**
	 * 设置是否记帐标志
	 * 
	 * @param keepAccounts
	 *            是否记帐
	 */
	public void setKeepAccounts(Boolean keepAccounts) {
		this.keepAccounts = keepAccounts;
	}

	/**
	 * 取得客户名称
	 * 
	 * @return scmCoc 客户.
	 */
	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	/**
	 * 设置客户名称
	 * 
	 * @param scmCoc
	 *            客户
	 */
	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	/**
	 * 取得生效日期
	 * 
	 * @return validDate 生效日期.
	 */
	public Date getValidDate() {
		if (validDate == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return java.sql.Date.valueOf(bartDateFormat.format(validDate));
	}

	/**
	 * 设置生效日期
	 * 
	 * @param validDate
	 *            生效日期
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**
	 * 取得是否记帐标志
	 * 
	 * @return isFlag 是否记帐.
	 */
	public Boolean getIsFlag() {
		return isFlag;
	}

	/**
	 * 设置是否记帐标志
	 * 
	 * @param isFlag
	 *            是否记帐.
	 */
	public void setIsFlag(Boolean isFlag) {
		this.isFlag = isFlag;
	}

	/**
	 * 取得序号（临时存放）
	 * 
	 * @return tempNo 序号（临时存放）.
	 */
	public String getTempNo() {
		return tempNo;
	}

	/**
	 * 设置序号
	 * 
	 * @param tempNo
	 *            序号（临时存放）.
	 */
	public void setTempNo(String tempNo) {
		this.tempNo = tempNo;
	}

	public String getTempNo1() {
		return tempNo1;
	}

	public void setTempNo1(String tempNo1) {
		this.tempNo1 = tempNo1;
	}

	public String getTempNo2() {
		return tempNo2;
	}

	public void setTempNo2(String tempNo2) {
		this.tempNo2 = tempNo2;
	}

	public String getTempNo3() {
		return tempNo3;
	}

	public void setTempNo3(String tempNo3) {
		this.tempNo3 = tempNo3;
	}

	public String getEnvelopNo() {
		return envelopNo;
	}

	public void setEnvelopNo(String envelopNo) {
		this.envelopNo = envelopNo;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}
}
