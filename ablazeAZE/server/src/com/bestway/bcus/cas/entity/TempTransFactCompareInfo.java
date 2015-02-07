package com.bestway.bcus.cas.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;

public class TempTransFactCompareInfo implements java.io.Serializable {
	private String commName;

	private String commSpec;
	
	private String hsUnit;

	private String date;

	private Double sendAmount;

	private Double sendWeight;

	private Double transAmount;

	private Double transWeight;

	private Double remainAmount;

	private Double remainWeight;

	private String envelopNo;

	private Double envelopAmount;

	private Double envelopWeight;

	private String term;
	
	private Boolean isEndCase = false;
	
	private String scmCocName;
	
	private String ptSpec;


	public String getScmCocName() {
		return scmCocName;
	}

	public void setScmCocName(String scmCocName) {
		this.scmCocName = scmCocName;
	}

	public String getCommSpec() {
		return commSpec;
	}

	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}

	public String getPtSpec() {
		return ptSpec;
	}

	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	/**
	 * 联系人
	 */
	private String linkMan;
	
	/**
	 * 获取联系人
	 * @return
	 */
	public String getLinkMan() {
		return linkMan;
	}

	/**
	 * 设置联系人
	 * @param linkMan
	 */
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	/**
	 * 电话号码
	 */
	private String tel;

	/**
	 * 获取联系电话
	 * @return
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * 设置联系电话
	 * @param tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * 传真
	 */
	private String fax;
	
	/**
	 * 获取传真
	 * @return
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * 设置传真
	 * @param fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	
	
	
	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	public String getHsUnit() {
		return hsUnit;
	}

	public void setHsUnit(String hsUnit) {
		this.hsUnit = hsUnit;
	}

	public String getDate() {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		if(!date.equals("期初")&&date.length()!=7){
//			Date tmp = new Date();
//			try {
//				tmp = sdf.parse(date);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			date = sdf.format(tmp);
//		}
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getEnvelopAmount() {
		return CommonUtils.getDoubleExceptNull(envelopAmount);
	}

	public void setEnvelopAmount(Double envelopAmount) {
		this.envelopAmount = envelopAmount;
	}

	public String getEnvelopNo() {
		return envelopNo;
	}

	public void setEnvelopNo(String envelopNo) {
		this.envelopNo = envelopNo;
	}

	public Double getEnvelopWeight() {
		return CommonUtils.getDoubleExceptNull(envelopWeight);
	}

	public void setEnvelopWeight(Double envelopWeight) {
		this.envelopWeight = envelopWeight;
	}

	public Double getRemainAmount() {
//		return CommonUtils.getDoubleExceptNull(this.getSendAmount())-CommonUtils.getDoubleExceptNull(this.getTransAmount());
		return CommonUtils.getDoubleExceptNull(remainAmount);
	}

	public void setRemainAmount(Double remainAmount) {
		this.remainAmount = remainAmount;
	}

	public Double getRemainWeight() {
//		return CommonUtils.getDoubleExceptNull(this.getSendWeight())-CommonUtils.getDoubleExceptNull(this.getTransWeight());
		return CommonUtils.getDoubleExceptNull(remainWeight);
	}

	public void setRemainWeight(Double remainWeight) {
		this.remainWeight = remainWeight;
	}

	public Double getSendAmount() {
		return CommonUtils.getDoubleExceptNull(sendAmount);
	}

	public void setSendAmount(Double sendAmount) {
		this.sendAmount = sendAmount;
	}

	public Double getSendWeight() {
		return CommonUtils.getDoubleExceptNull(sendWeight);
	}

	public void setSendWeight(Double sendWeight) {
		this.sendWeight = sendWeight;
	}

	/**
	 * 有效期
	 * @return
	 */
	public String getTerm() {
		return term;
	}
	/**
	 * 有效期
	 * @return
	 */
	public void setTerm(String term) {
		this.term = term;
	}

	public Double getTransAmount() {
		return CommonUtils.getDoubleExceptNull(transAmount);
	}

	public void setTransAmount(Double transAmount) {
		this.transAmount = transAmount;
	}

	public Double getTransWeight() {
		return CommonUtils.getDoubleExceptNull(transWeight);
	}

	public void setTransWeight(Double transWeight) {
		this.transWeight = transWeight;
	}

	public Boolean getIsEndCase() {
		return isEndCase;
	}

	public void setIsEndCase(Boolean isEndCase) {
		this.isEndCase = isEndCase;
	}

	public String getYear() {
		if ("期初".equals(this.date)) {
			return this.getDate().substring(0, 1);
		}
		if (this.date == null) {
			return "";
		} else {
			return this.date.substring(0, 4);
		}
	}

	public String getMonth() {
		if ("期初".equals(this.date)) {
			return this.getDate().substring(1, 2);
		}
		if (this.date == null) {
			return "";
		} else {
			if (this.date.length() == 10) {
				return this.date.substring(5, 7);
			} else {
				return this.date.substring(5);
			}
		}
	}

	public String getDay() {
		if ("期初".equals(this.date)) {
			return "";
		}
		if (this.date == null) {
			return "";
		} else {
			if (this.date.length() == 10) {
				return this.date.substring(8);
			} else {
				return "";
			}
		}
	}
	
	public void initAmount() {
		this.setSendAmount(0.0);
		this.setSendWeight(0.0);
		this.setEnvelopAmount(0.0);
		this.setEnvelopWeight(0.0);
		this.setRemainAmount(0.0);
		this.setRemainWeight(0.0);
		this.setTransAmount(0.0);
		this.setTransWeight(0.0);
	}
}
