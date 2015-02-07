package com.bestway.dzsc.materialapply.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DzscCustomsModifyState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 存放电子手册－物料申报的数据
 * @author yp
 * 
 * 
 */
public class MaterialApply extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 序号
	 */
	private Integer no;

	/**
	 * 物料
	 */
	private Materiel materiel;

	/**
	 * 状态标志
	 * ORIGINAL = "0"	原始状态
	 * APPLY = "1";	申请
	 * EXECUTE = "2";	执行
	 * CHANGE = "3";	变更
	 * CHECK_CANCEL = "4";	核销
	 * BACK_BILL = "5";	退单
	 */
	private String stateMark;

	  /**
	 * 修改标志
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String modifyMark; 
	
	/**
	 * 是够禁止归并
	 */
	private Boolean isForbidMerge =false;
	/**
	 *  是否选中
	 */
	private Boolean isSelected=false;

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	/**
	 * 获取物料
	 * 
	 * @return materiel 物料
	 */
	public Materiel getMateriel() {
		return materiel;
	}

	/**
	 * 设置物料
	 * 
	 * @param materiel
	 *            物料
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	/**
	 * 获取状态标志
	 * 
	 * @return stateMark 状态标志
	 */
	public String getStateMark() {
		return stateMark;
	}

	/**
	 * 设置状态标志
	 * 
	 * @param stateMark
	 *            状态标志
	 */
	public void setStateMark(String stateMark) {
		this.stateMark = stateMark;
	}

	public String getModifyMark() {
		return modifyMark;
	}

	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Boolean getIsForbidMerge() {
		return isForbidMerge;
	}

	public void setIsForbidMerge(Boolean isForbidMerege) {
		this.isForbidMerge = isForbidMerege;
	}
}
