package com.bestway.bls.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 仓单特殊申请商品列表
 * @author Administrator
 *
 */
public class BillSpecialApplyApplyList extends BaseScmEntity{

	/**
	 * 仓单特殊申请单表头
	 */
	private BillSpecialApplyHead billSpecialApplyHead;
	
	/**
	 * GNo 表体序号(Integer) 流水号
	 * 
	 * @uml.property name="gNo"
	 */
	private Integer seqNo;
	
	/**
	 * 商品名称
	 */
	private String qtName;
	
	/**
	 * 商品规格
	 */
	private String qtModel;
	
	/**
	 * 商品编码
	 */
	private String qtCode;
	
	
	/**
	 * 商品单位
	 */
	private String qtUnit;
	
	/**
	 * 数量
	 */
	private Double qtTy;

	/**
	 * 仓单特殊申请单表头
	 * @return
	 */
	public BillSpecialApplyHead getBillSpecialApplyHead() {
		return billSpecialApplyHead;
	}

	/**
	 * 仓单特殊申请单表头
	 * @return
	 */
	public void setBillSpecialApplyHead(BillSpecialApplyHead billSpecialApplyHead) {
		this.billSpecialApplyHead = billSpecialApplyHead;
	}
	
	public Double getQtTy() {
		return qtTy;
	}

	public void setQtTy(Double qtTy) {
		this.qtTy = qtTy;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public String getQtName() {
		return qtName;
	}

	public void setQtName(String qtName) {
		this.qtName = qtName;
	}

	public String getQtModel() {
		return qtModel;
	}

	public void setQtModel(String qtModel) {
		this.qtModel = qtModel;
	}

	public String getQtCode() {
		return qtCode;
	}

	public void setQtCode(String qtCode) {
		this.qtCode = qtCode;
	}

	public String getQtUnit() {
		return qtUnit;
	}

	public void setQtUnit(String qtUnit) {
		this.qtUnit = qtUnit;
	}

	

}
