package com.bestway.dzsc.materialapply.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.MaterialBomMaster;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 存放电子手册－单耗申报的成品资料
 * 
 * @author yp
 */
public class MaterialBomMasterApply extends BaseScmEntity{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();

	/**
	 * 料件
	 */
	private Materiel materiel;
	
	/**
	 * 是否变更状态
	 */
	private Boolean isChanged = false;

	/**
	 * 状态表示
	 * ORIGINAL = "0";	原始状态
	 * APPLY = "1"; 	申请
	 * EXECUTE = "2";	执行
	 * CHANGE = "3";	变更
	 * CHECK_CANCEL = "4";	 核销
	 * BACK_BILL = "5";	退单
	 */
	private String stateMark;
	/**
	 * 获取料件
	 * 
	 * @return materiel 料件
	 */
	public Materiel getMateriel() {
		return materiel;
	}
	
	/**
	 * 设置料件
	 * 
	 * @param materiel 料件
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	public Boolean getIsChanged() {
		return isChanged;
	}

	public void setIsChanged(Boolean isChanged) {
		this.isChanged = isChanged;
	}

	public String getStateMark() {
		return stateMark;
	}

	public void setStateMark(String stateMark) {
		this.stateMark = stateMark;
	}
}
