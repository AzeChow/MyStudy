/*
 * Created on 2004-7-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractexe.entity;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.common.CommonUtils;

/**
 * 存放临时的进出口申请单表体资料
 * 
 * @author Administrator
 */
public class TempBcsImpExpCommodityInfo implements Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 选中判断，true位被选中
	 */
	private Boolean isSelected = null;

	/**
	 * 进出口申请单表体
	 */
	private ImpExpCommodityInfo impExpCommodityInfo = null;

	/**
	 * 合同备案成品
	 */
	private ContractExg contractExg = null;

	/**
	 * 合同备案料件
	 */
	private ContractImg contractImg = null;

	private Integer seqNo = null;
	/**
	 * 保存折算后的数量－－申请单转清单时用到
	 */
	private Double converCount = null;
	
	/**
	 * 是否为不同包装方式的归并--康磁电子使用
	 */
	private Boolean isDifferWrapToMerger;
	
	/**
	 * 是否超量
	 */
	private Boolean isOutAmount;

	/**
	 * 得意用
	 */
	private List temps = new ArrayList();
	
	/**
	 * 保存归并前表体
	 */
	private List<TempBcsImpExpCommodityInfo> CommodityInfos = new ArrayList<TempBcsImpExpCommodityInfo>();
	
	/**
	 * 获取合同备案成品
	 * 
	 * @return contractExg 合同备案成品
	 */
	public ContractExg getContractExg() {
		return contractExg;
	}

	/**
	 * 设置合同备案成品
	 * 
	 * @param contractExg
	 *            合同备案成品
	 */
	public void setContractExg(ContractExg contractExg) {
		this.contractExg = contractExg;
	}

	/**
	 * 获取合同备案料件
	 * 
	 * @return contractImg 合同备案料件
	 */
	public ContractImg getContractImg() {
		return contractImg;
	}

	/**
	 * 设置合同备案料件
	 * 
	 * @param contractImg
	 *            合同备案料件
	 */
	public void setContractImg(ContractImg contractImg) {
		this.contractImg = contractImg;
	}

	/**
	 * 获取选中判断，true位被选中
	 * 
	 * @return isSelected 选中判断，true位被选中
	 */
	public Boolean getIsSelected() {
		return isSelected;
	}

	/**
	 * 设置选中判断，true位被选中
	 * 
	 * @param isSelected
	 *            选中判断，true位被选中
	 */
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * 获取进出口申请单表体
	 * 
	 * @return impExpCommodityInfo 进出口申请单表体
	 */
	public ImpExpCommodityInfo getImpExpCommodityInfo() {
		return impExpCommodityInfo;
	}

	/**
	 * 设置进出口申请单表体
	 * 
	 * @param impExpCommodityInfo
	 *            进出口申请单表体
	 */
	public void setImpExpCommodityInfo(ImpExpCommodityInfo impExpCommodityInfo) {
		this.impExpCommodityInfo = impExpCommodityInfo;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
	/**
	 * 保存折算后的数量－－申请单转清单时用到
	 */
	public Double getConverCount() {
		return converCount;
	}
	/**
	 * 保存折算后的数量－－申请单转清单时用到
	 */
	public void setConverCount(Double converCount) {
		this.converCount = converCount;
	}
	/**
	 * 
	 * 是否为不同包装方式的归并--康磁电子使用
	 * @return
	 */
	public Boolean getIsDifferWrapToMerger() {
		return isDifferWrapToMerger;
	}
	/**
	 * 是否为不同包装方式的归并--康磁电子使用
	 * @param isDifferWrapToMerger
	 */
	public void setIsDifferWrapToMerger(Boolean isDifferWrapToMerger) {
		this.isDifferWrapToMerger = isDifferWrapToMerger;
	}

	/**
	 * 保存归并前表体
	 */
	public List<TempBcsImpExpCommodityInfo> getCommodityInfos() {
		return CommodityInfos;
	}

	/**
	 * 保存归并前表体
	 */
	public void setCommodityInfos(List<TempBcsImpExpCommodityInfo> commodityInfos) {
		CommodityInfos = commodityInfos;
	}

	/**
	 * 是否超量
	 */
	public Boolean getIsOutAmount() {
		return isOutAmount;
	}

	/**
	 * 是否超量
	 */
	public void setIsOutAmount(Boolean isOutAmount) {
		this.isOutAmount = isOutAmount;
	}

	public List getTemps() {
		return temps;
	}

	public void setTemps(List temps) {
		this.temps = temps;
	}
	
	
}