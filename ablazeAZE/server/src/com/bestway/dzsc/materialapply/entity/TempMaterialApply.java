package com.bestway.dzsc.materialapply.entity;

import com.bestway.common.constant.DzscCustomsModifyState;
import com.bestway.common.materialbase.entity.Materiel;

public class TempMaterialApply extends Materiel {
	/**
	 * 序号
	 */
	private Integer no;

	/**
	 * 修改标志,对应列名modifyMark
	 */
	private String modifyMark;

	public String getModifyMark() {
		return modifyMark;
	}

	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	/**
	 * 获取海关修改标志
	 * 
	 * @return editMark 修改标志
	 */
	public String getCustomsModifyMark() {
		return DzscCustomsModifyState.getCustomsModifyState(modifyMark);
	}

	/**
	 * 附加编码
	 * 
	 * @return
	 */
	public String getComplexSCode() {
		if (this.getComplex() == null
				|| this.getComplex().getCode().length() <= 8) {
			return "";
		} else {
			return this.getComplex().getCode().substring(8,
					this.getComplex().getCode().length());
		}
	}
}
