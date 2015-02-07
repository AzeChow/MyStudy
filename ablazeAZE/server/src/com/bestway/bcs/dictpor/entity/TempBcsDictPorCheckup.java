package com.bestway.bcs.dictpor.entity;

import com.bestway.common.BaseEntity;
import com.bestway.common.CommonUtils;

/**
 * @author fhz
 * 
 * 备案资料库检查结果
 */
public class TempBcsDictPorCheckup extends BaseEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 企业内部编号
	 */
	String copEmsNo;
	
	/**
	 * 基本资料的错误
	 */
	String headErr;
	
	/**
	 * 料件的错误
	 */
	String imgErr;
	
	/**
	 * 成品的错误
	 */
	String exgErr;

	/**
	 * @return the imgErr
	 */
	public String getImgErr() {
		return imgErr;
	}

	/**
	 * @param imgErr the imgErr to set
	 */
	public void setImgErr(String imgErr) {
		this.imgErr = imgErr;
	}

	/**
	 * @return the headErr
	 */
	public String getHeadErr() {
		return headErr;
	}

	/**
	 * @param headErr the headErr to set
	 */
	public void setHeadErr(String headErr) {
		this.headErr = headErr;
	}

	/**
	 * @return the copEmsNo
	 */
	public String getCopEmsNo() {
		return copEmsNo;
	}

	/**
	 * @param copEmsNo the copEmsNo to set
	 */
	public void setCopEmsNo(String copEmsNo) {
		this.copEmsNo = copEmsNo;
	}

	/**
	 * @return the exgErr
	 */
	public String getExgErr() {
		return exgErr;
	}

	/**
	 * @param exgErr the exgErr to set
	 */
	public void setExgErr(String exgErr) {
		this.exgErr = exgErr;
	}

}
