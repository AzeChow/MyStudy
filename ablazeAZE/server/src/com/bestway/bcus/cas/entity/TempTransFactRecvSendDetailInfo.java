package com.bestway.bcus.cas.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.ScmCoc;
/**
 * 
 * @author kfb  进出仓单据资料
 *
 */
public class TempTransFactRecvSendDetailInfo implements java.io.Serializable {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
	/**
	 * 单据号码
	 */
	private String billNo;

	/**
	 * 采购/业务订单号
	 */
	private String poSoBillNo;

	/**
	 * 生效日期
	 */
	private Date validDate;

	/**
	 * 工厂料号
	 */
	private String ptPart;

	/**
	 * 商品名称
	 */
	private String ptName;

	/**
	 * 规格型号
	 */
	private String ptSpec;

	/**
	 * 单位
	 */
	private CalUnit ptUnit;

	/**
	 * 收货数量
	 */
	private Double ptRecvAmount;

	/**
	 * 退货数量
	 */
	private Double ptBackAmount;

	/**
	 * 海关商品编码
	 */

	private Complex complex;

	/**
	 * 海关商品名称
	 */
	private String hsName;

	/**
	 * 海关商品规格型号
	 */
	private String hsSpec;

	/**
	 * 序号
	 */
	private Integer seqNum;

	/**
	 * 折算海关收货数量
	 */
	private Double hsRecvAmount;
	/**
	 * 收货重量
	 */
	private Double recvNetWeight;
	/**
	 * 退货重量
	 */
	private Double backNetWeight;

	/**
	 * 折算海关退货数量
	 */
	private Double hsBackAmount;
	
	/**
	 * 海关单位
	 */
	private Unit hsUnit;
	
	/**
	 * 客户/供应商 - 临时存放数据
	 */
	private ScmCoc scmCoc;
	/**
	 * 备注 -临时存放单据体数据
	 */
	private String notice;
	/**
	 * 备注 -临时存放单据头数据
	 */
	private String note;
	/**
	 * 发货单号  hwy
	 */
	private String takeBillNo;
	/**
	 * 对应报关单号 -临时存放数据
	 */
	private String customNo;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public Double getHsRecvAmount() {
		return hsRecvAmount;
	}

	public void setHsRecvAmount(Double hsAmount) {
		this.hsRecvAmount = hsAmount;
	}

	public String getHsName() {
		return hsName;
	}

	public void setHsName(String hsName) {
		this.hsName = hsName;
	}

	public String getHsSpec() {
		return hsSpec;
	}

	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}

	public Unit getHsUnit() {
		return hsUnit;
	}

	public void setHsUnit(Unit hsUnit) {
		this.hsUnit = hsUnit;
	}

	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getPtPart() {
		return ptPart;
	}

	public void setPtPart(String ptPart) {
		this.ptPart = ptPart;
	}

	public String getPtSpec() {
		return ptSpec;
	}

	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	public CalUnit getPtUnit() {
		return ptUnit;
	}
	
	public Double getRecvNetWeight() {
		return recvNetWeight;
	}

	public void setRecvNetWeight(Double recvNetWeight) {
		this.recvNetWeight = recvNetWeight;
	}

	public Double getBackNetWeight() {
		return backNetWeight;
	}

	public void setBackNetWeight(Double backNetWeight) {
		this.backNetWeight = backNetWeight;
	}

	public void setPtUnit(CalUnit ptUnit) {
		this.ptUnit = ptUnit;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public String getPoSoBillNo() {
		return poSoBillNo;
	}

	public void setPoSoBillNo(String poSoBillNo) {
		this.poSoBillNo = poSoBillNo;
	}

	public Double getPtBackAmount() {
		return ptBackAmount;
	}

	public void setPtBackAmount(Double ptBackAmount) {
		this.ptBackAmount = ptBackAmount;
	}

	public Double getPtRecvAmount() {
		return ptRecvAmount;
	}

	public void setPtRecvAmount(Double ptRecvAmount) {
		this.ptRecvAmount = ptRecvAmount;
	}

	public Double getHsBackAmount() {
		return hsBackAmount;
	}

	public void setHsBackAmount(Double hsBackAmount) {
		this.hsBackAmount = hsBackAmount;
	}

	public String getBillYear() {
		if (this.validDate == null) {
			return "";
		} else {
			return dateFormat.format(this.validDate).substring(0, 4);
		}
	}

	public String getBillMonth() {
		if (this.validDate == null) {
			return "";
		} else {
			return dateFormat.format(this.validDate).substring(5, 7);
		}
	}

	public String getBillDay() {
		if (this.validDate == null) {
			return "";
		} else {
			return dateFormat.format(this.validDate).substring(8);
		}
	}

	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getCustomNo() {
		return customNo;
	}

	public void setCustomNo(String customNo) {
		this.customNo = customNo;
	}

	public String getTakeBillNo() {
		return takeBillNo;
	}

	public void setTakeBillNo(String takeBillNo) {
		this.takeBillNo = takeBillNo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
