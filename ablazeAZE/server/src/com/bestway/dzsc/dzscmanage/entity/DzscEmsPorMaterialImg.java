package com.bestway.dzsc.dzscmanage.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.constant.DzscCustomsModifyState;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.constant.DzscCustomsModifyState;
import com.bestway.common.materialbase.entity.Materiel;
/**
 * 电子手册归并料件
 * @author Administrator
 *
 */
public class DzscEmsPorMaterialImg extends BaseScmEntity {
	/**
	 * 电子手册通过备案表头
	 */
	private DzscEmsPorHead dzscEmsPorHead = null;
	/**
	 * 物料主档
	 */
	private Materiel materiel = null;
	/**
	 * 料件单据
	 */
	private DzscEmsImgBill dzscEmsImgBill = null;

	   /**
	 * 修改标志
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String modifyMark;

	public DzscEmsImgBill getDzscEmsImgBill() {
		return dzscEmsImgBill;
	}

	public void setDzscEmsImgBill(DzscEmsImgBill dzscEmsImgBill) {
		this.dzscEmsImgBill = dzscEmsImgBill;
	}

	public DzscEmsPorHead getDzscEmsPorHead() {
		return dzscEmsPorHead;
	}

	public void setDzscEmsPorHead(DzscEmsPorHead dzscEmsPorHead) {
		this.dzscEmsPorHead = dzscEmsPorHead;
	}

	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	public String getModifyMark() {
		return modifyMark;
	}

	public String getCustomsModifyMark() {
		return DzscCustomsModifyState.getCustomsModifyState(modifyMark);
	}

	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}
}
