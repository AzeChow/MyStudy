package com.bestway.dzsc.qp.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.constant.DzscCustomsModifyState;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.constant.DzscCustomsModifyState;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 电子手册归并料件
 * 
 * @author Administrator
 * 
 */
public class DzscQPEmsPorMaterialImg implements java.io.Serializable {

	/**
	 * 物料主档
	 */
	private String materielPtNo = null;
	/**
	 * 料件单据
	 */
	private Integer imgBillSeqNum = null;

	public String getMaterielPtNo() {
		return materielPtNo;
	}

	public void setMaterielPtNo(String materielPtNo) {
		this.materielPtNo = materielPtNo;
	}

	public Integer getImgBillSeqNum() {
		return imgBillSeqNum;
	}

	public void setImgBillSeqNum(Integer imgBillSeqNum) {
		this.imgBillSeqNum = imgBillSeqNum;
	}

}
