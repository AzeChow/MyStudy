/*
 * Created on 2004-9-25
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 结转商品信息
 */
public class CarryForwardInfo implements Serializable {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
	*报关单号
	*/
	String customNo;
	/**
	*归并序号
	*/
	Integer seqNum;
	
	/**
	*报关名称
	*/
	String hsName;
	/**
	*规格
	*/
	String hsSpec;
	/**
	*报关单位
	*/
	Unit hsUnit;
	/**
	*报关重量单位
	*/
	Unit hsWeightUnit;
	/**
	*客户
	*/
	ScmCoc scmCoc;
	/**
	*有效日期
	*/
	Date validDate;
	/**
	*收货凭证号,进出仓单据号
	*/
	String billNo;
	/**
	*海关数量,收货数量
	*/
	Double hsAmount;
	/**
	*收货重量
	*/
	Double weight;
	/**
	*累积数量
	*/
	Double accAmount;
	/**
	*结转数量
	*/
	Double carryForwardAmount;
	/**
	*累积结转数量
	*/
	Double accCarryForwardAmount;
	/**
	*收货未结转数
	*/
	Double unCarryForwardAmount;
	/**
	*结转未收货数
	*/
	Double exceedCarryForwardAmount;
	/**
	 * 初始化报关名称,规格,单位,重量单位,客户,有效期限,进出仓单据号,收货数量,收货重量,报关单号,归并序号,结转数量
	 * @param billDetail 单据明细.
	 */
	public CarryForwardInfo(BillDetail billDetail){
		
		hsName = billDetail.getHsName();
		hsSpec = billDetail.getHsSpec();
		hsUnit = billDetail.getHsUnit();
		hsWeightUnit = billDetail.getWeightUnit();
		scmCoc = billDetail.getBillMaster().getScmCoc();
		validDate = billDetail.getBillMaster().getValidDate();
		billNo = billDetail.getBillMaster().getBillNo();
		hsAmount = billDetail.getHsAmount();
		weight = billDetail.getNetWeight();
		
		customNo = billDetail.getCustomNo();
		seqNum = billDetail.getSeqNum();
		
		carryForwardAmount = billDetail.getCustomNum();
		
	}
	
	/**
	 * 取得积累数量
	 * @return accAmount 积累数量.
	 */
	public Double getAccAmount() {
		return accAmount;
	}
	/**
	 * 设置积累数量
	 * @param accAmount 积累数量.
	 */
	public void setAccAmount(Double accAmount) {
		this.accAmount = accAmount;
	}
	/**
	 * 取得累积结转数量
	 * @return  accCarryForwardAmount 累积结转数量.
	 */
	public Double getAccCarryForwardAmount() {
		return accCarryForwardAmount;
	}
	/**
	 * 设置累积结转数量
	 * @param accCarryForwardAmount 累积结转数量.
	 */
	public void setAccCarryForwardAmount(Double accCarryForwardAmount) {
		this.accCarryForwardAmount = accCarryForwardAmount;
	}
	/**
	 * 取得结转数量
	 * @return carryForwardAmount 结转数量.
	 */
	public Double getCarryForwardAmount() {
		return carryForwardAmount;
	}
	/**
	 * 设置结转数量
	 * @param carryForwardAmount 结转数量.
	 */
	public void setCarryForwardAmount(Double carryForwardAmount) {
		this.carryForwardAmount = carryForwardAmount;
	}
	/**
	 * 取得收货数量
	 * @return hsAmount 收货数量.
	 */
	public Double getHsAmount() {
		return hsAmount;
	}
	/**
	 * 设置收货数量
	 * @param hsAmount 收货数量.
	 */
	public void setHsAmount(Double hsAmount) {
		this.hsAmount = hsAmount;
	}
	/**
	 * 取得收货未结转数
	 * @return unCarryForwardAmount 收货未结转数.
	 */
	public Double getUnCarryForwardAmount() {
		return unCarryForwardAmount;
	}
	/**
	 * 设置收货未结转数
	 * @param unCarryForwardAmount 收货未结转数.
	 */
	public void setUnCarryForwardAmount(Double unCarryForwardAmount) {
		this.unCarryForwardAmount = unCarryForwardAmount;
	}
	/**
	 * 取得进出仓单据号
	 * @return  billNo 进出仓单据号.
	 */
	public String getBillNo() {
		return billNo;
	}
	/**
	 * 取得报关单号
	 * @return customNo 报关单号.
	 */
	public String getCustomNo() {
		return customNo;
	}
	/**
	 * 取得报关名称
	 * @return hsName 报关名称.
	 */
	public String getHsName() {
		return hsName;
	}
	/**
	 * 取得规格
	 * @return  hsSpec 规格.
	 */
	public String getHsSpec() {
		return hsSpec;
	}
	/**
	 * 取得单位 自定义unit型
	 * @return  hsUnit 单位.
	 */
	public Unit getHsUnit() {
		return hsUnit;
	}
	/**
	 * 取得重量单位  自定义unit型
	 * @return hsWeightUnit 重量单位.
	 */
	public Unit getHsWeightUnit() {
		return hsWeightUnit;
	}
	/**
	 * 取得客户 自定义scmcoc型
	 * @return scmCoc 客户.
	 */
	public ScmCoc getScmCoc() {
		return scmCoc;
	}
	/**
	 * 取得有效日期
	 * @return validDate 有效日期.
	 */
	public Date getValidDate() {
		return validDate;
	}
	/**
	 * 取得收货重量
	 * @return  weight 收货重量.
	 */
	public Double getWeight() {
		return weight;
	}
	/**
	 * 取得归并序号
	 * @return seqNum 归并序号.
	 */
	public Integer getSeqNum() {
		return seqNum;
	}
	/**
	 * 取得结转未收货数
	 * @return exceedCarryForwardAmount 结转未收货数.
	 */
	public Double getExceedCarryForwardAmount() {
		return exceedCarryForwardAmount;
	}
	/**
	 * 设置结转未收货数
	 * @param exceedCarryForwardAmount 结转未收货数.
	 */
	public void setExceedCarryForwardAmount(Double exceedCarryForwardAmount) {
		this.exceedCarryForwardAmount = exceedCarryForwardAmount;
	}
}
