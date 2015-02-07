/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.fpt.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 由结转单据生成报关单中间表
 * @author 由结转单据生成报关单中间表 // change the template for this generated type
 *         comment go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
public class MakeFptBillCustomsDeclaration extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 项目类型
	 */
	private Integer projectType;
//	/**
//	 * 结转单据主表ID
//	 */
//	private String fptBillHeadAppNo; //主表申请单号
//	
//	/**
//	 * 结转单据项号(归并序号)
//	 */
//	private Integer fptBillItemTrGno; //结转单据项号(归并序号)
	/**
	 * 报关单的ID
	 */
	private String customsDeclarationCommInId; //报关单明细id


	private String fptBillItemId;//不用此字段 结转单据ID
	
	
	public String getFptBillItemId() {
		return fptBillItemId;
	}

	public void setFptBillItemId(String fptBillItemId) {
		this.fptBillItemId = fptBillItemId;
	}
//
//	public Integer getFptBillItemTrGno() {
//		return fptBillItemTrGno;
//	}
//
//	public void setFptBillItemTrGno(Integer fptBillItemTrGno) {
//		this.fptBillItemTrGno = fptBillItemTrGno;
//	}
//
//	
//	public String getFptBillHeadAppNo() {
//		return fptBillHeadAppNo;
//	}
//
//	public void setFptBillHeadAppNo(String fptBillHeadAppNo) {
//		this.fptBillHeadAppNo = fptBillHeadAppNo;
//	}

	public String getCustomsDeclarationCommInId() {
		return customsDeclarationCommInId;
	}

	public void setCustomsDeclarationCommInId(String customsDeclarationCommInId) {
		this.customsDeclarationCommInId = customsDeclarationCommInId;
	}

	public Integer getProjectType() {
		return projectType;
	}

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

}