package com.bestway.bcus.cas.entity;

import java.io.Serializable;

/**
 * 单据导入从文本导入出错时显示出错信息表
 * 
 * @author ower
 * 
 */
public class TempBillInputErrMassageShow implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 于第?行检查数据错误
	 */
	private Integer invalidationRow;
	/**
	 * 料号
	 */
	private String ptNo;

	/**
	 * 错误原因
	 */
	private String errorReason;

	/**
	 * 获取数据出错的行数
	 * 
	 * @return the invalidationRow
	 */
	public Integer getInvalidationRow() {
		return invalidationRow;
	}

	/**
	 * 设置数据出错的行数
	 * 
	 * @param invalidationRow
	 *            the invalidationRow to set
	 */
	public void setInvalidationRow(Integer invalidationRow) {
		this.invalidationRow = invalidationRow;
	}

	/**
	 * 获取出错原因
	 * 
	 * @return the errorReason 出错原因
	 */
	public String getErrorReason() {
		return errorReason;
	}

	/**
	 * 设置出错原因
	 * 
	 * @param errorReason
	 *            the errorReason to set 出错原因
	 */
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

	/**
	 * 取得料号
	 * 
	 * @return the ptNo 料号
	 */
	public String getPtNo() {
		return ptNo;
	}

	/**
	 * 设置料号
	 * 
	 * @param ptNo
	 *            the ptNo to set 料号
	 */
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
		;
	}

}
