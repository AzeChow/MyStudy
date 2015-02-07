package com.bestway.bcus.cas.specificontrol.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.common.materialbase.entity.ScmCoc;

public class TempBillCorrespondingSearch implements Serializable {
	/**
	 * 开始日期
	 */
	private Date	beginDate				= null;
	/**
	 * 结束日期
	 */
	private Date	endDate					= null;
	/**
	 * 单据类型
	 */
	private Integer	impExpType				= null;
	/**
	 * 工厂料号
	 */
	private String	ptPart					= null;
	/**
	 * 结束料号
	 */
	private String	endPtPart					= null;
	/**
	 * 商品名称
	 */
	private String	ptName					= null;
	/**
	 * 报关单号
	 */
	private String	customsDeclarationCode	= null;
	/**
	 * 电子帐册号码
	 */
	private String	emsHeadH2k				= null;
	/**
	 * 商品名称
	 */
	private String	commName				= null;
	/**
	 * 商品规格
	 */
	private String	commSpec				= null;
	/**
	 * 商品名称
	 */
	private ScmCoc	scmCoc					= null;

	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}

	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}

	public String getEmsHeadH2k() {
		return emsHeadH2k;
	}

	public void setEmsHeadH2k(String emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}

	public Integer getImpExpType() {
		return impExpType;
	}

	public void setImpExpType(Integer impExpType) {
		this.impExpType = impExpType;
	}

	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getPtPart() {
		return ptPart;
	}

	public void setPtPart(String ptPart) {
		this.ptPart = ptPart;
	}

	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	public String getCommSpec() {
		return commSpec;
	}

	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEndPtPart() {
		return endPtPart;
	}

	public void setEndPtPart(String endPtPart) {
		this.endPtPart = endPtPart;
	}

}
