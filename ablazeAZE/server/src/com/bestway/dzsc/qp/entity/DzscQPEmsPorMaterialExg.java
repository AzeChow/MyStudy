package com.bestway.dzsc.qp.entity;


/**
 * 电子手册归并成品
 * 
 * @author Administrator
 * 
 */
public class DzscQPEmsPorMaterialExg implements java.io.Serializable {
	/**
	 * 物料主档
	 */
	private String materielPtNo = null;
	/**
	 * 成品单据
	 */
	private Integer exgBillSeqNum = null;

	public String getMaterielPtNo() {
		return materielPtNo;
	}

	public void setMaterielPtNo(String materielPtNo) {
		this.materielPtNo = materielPtNo;
	}

	public Integer getExgBillSeqNum() {
		return exgBillSeqNum;
	}

	public void setExgBillSeqNum(Integer exgBillSeqNum) {
		this.exgBillSeqNum = exgBillSeqNum;
	}

}
