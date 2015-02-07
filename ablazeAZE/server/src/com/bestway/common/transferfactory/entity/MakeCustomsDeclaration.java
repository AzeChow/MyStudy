/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.transferfactory.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 由结转单据生成报关单中间表
 * @author 由结转单据生成报关单中间表 // change the template for this generated type
 *         comment go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
public class MakeCustomsDeclaration extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 项目类型
	 */
	private Integer projectType;
	/**
	 * 结转单据的ID
	 */
	private String transFactBillId;
	/**
	 * 报关单的ID
	 */
	private String customsDeclarationId;

	public String getCustomsDeclarationId() {
		return customsDeclarationId;
	}

	public void setCustomsDeclarationId(String customsDeclarationId) {
		this.customsDeclarationId = customsDeclarationId;
	}

	public Integer getProjectType() {
		return projectType;
	}

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	public String getTransFactBillId() {
		return transFactBillId;
	}

	public void setTransFactBillId(String transFactBillId) {
		this.transFactBillId = transFactBillId;
	}
}