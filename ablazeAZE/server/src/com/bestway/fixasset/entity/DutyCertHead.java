package com.bestway.fixasset.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 批文协议设备征免税证表头
 * @author Administrator
 *
 */
public class DutyCertHead extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
	.getSerialVersionUID();
	/**
	 * 协议批文号
	 */
	private String sancEmsNo;
	/**
	 * 征免税号
	 */
	private String certNo;

	/**
	 * 申报日期
	 */
	private Date certApplyDate;

	/**
	 * 批准日期
	 */
	private Date certApproveDate;

	/**
	 * 生效日期
	 */
	private Date certEffectDate;
	
	/**
	 * 类型
	 */
	private String type="三资设备";

	public String getSancEmsNo() {
		return sancEmsNo;
	}

	public void setSancEmsNo(String sancEmsNo) {
		this.sancEmsNo = sancEmsNo;
	}

	public Date getCertApplyDate() {
		return certApplyDate;
	}

	public void setCertApplyDate(Date certApplyDate) {
		this.certApplyDate = certApplyDate;
	}

	public Date getCertApproveDate() {
		return certApproveDate;
	}

	public void setCertApproveDate(Date certApproveDate) {
		this.certApproveDate = certApproveDate;
	}

	public Date getCertEffectDate() {
		return certEffectDate;
	}

	public void setCertEffectDate(Date certEffectDate) {
		this.certEffectDate = certEffectDate;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

}
