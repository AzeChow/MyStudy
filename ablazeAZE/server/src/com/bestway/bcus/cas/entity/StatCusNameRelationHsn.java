package com.bestway.bcus.cas.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.entity.ScmCoi;
/**
 * 海关帐对帐表
 * @author ower
 *
 */
public class StatCusNameRelationHsn extends BaseScmEntity {
	/**
	 * 版本信息
	 */
	private static final long serialVersionUID = 2760885720086609030L;

	/**
	 * 单位折算
	 */
	private Double unitConvert;

	/**
	 * 序号
	 */
	private Integer seqNum;

	/**
	 * 商品编码
	 */
	private Complex complex;

	/**
	 * 名称
	 */
	private String cusName;

	/**
	 * 规格
	 */
	private String cusSpec;

	/**
	 * 计量单位
	 */
	private Unit cusUnit;

	/**
	 * 版本
	 */
	private Integer version;

	/**
	 * 手册号
	 */
	private String emsNo;

	/**
	 * 手册起始期限
	 */
	private Date emsBeginDate;

	/**
	 * 手册结束期限
	 */
	private Date emsEndDate;

	/**
	 * 管理类型
	 * 
	 * 	BCUS = 0;
	 *	BCS = 1;
	 *	DZSC = 3;
	 */
	private Integer projectType;

	/**
	 * 物料类型
	 */
	private String materielType;
	
	//以下为导入数据时用到的临时字段
	private String temp1;
	
	private String temp2;
	
	private String errorReason;
	
	private Integer invalidationRow;

	public Date getEmsBeginDate() {
		return emsBeginDate;
	}

	public void setEmsBeginDate(Date emsBeginDate) {
		this.emsBeginDate = emsBeginDate;
	}

	public Date getEmsEndDate() {
		return emsEndDate;
	}

	public void setEmsEndDate(Date emsEndDate) {
		this.emsEndDate = emsEndDate;
	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	public Integer getProjectType() {
		return projectType;
	}

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getMaterielType() {
		return materielType;
	}

	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusSpec() {
		return cusSpec;
	}

	public void setCusSpec(String cusSpec) {
		this.cusSpec = cusSpec;
	}

	public Unit getCusUnit() {
		return cusUnit;
	}

	public void setCusUnit(Unit cusUnit) {
		this.cusUnit = cusUnit;
	}

	public Double getUnitConvert() {
		return unitConvert;
	}

	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}

	public String getProjectName() {
		String proj = "";
		if (getProjectType() != null) {
			int m = Integer.parseInt(getProjectType().toString());
			switch (m) {
			case ProjectType.BCS:
				proj = "电子化手册";
				break;
			case ProjectType.BCUS:
				proj = "电子帐册";
				break;
			case ProjectType.DZSC:
				proj = "电子手册";
				break;
			default:
				proj = "";
				break;
			}
		}
		return proj;
	}

	/**
	 * @return the temp1
	 */
	public String getTemp1() {
		return temp1;
	}

	/**
	 * @param temp1 the temp1 to set
	 */
	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}

	/**
	 * @return the temp2
	 */
	public String getTemp2() {
		return temp2;
	}

	/**
	 * @param temp2 the temp2 to set
	 */
	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}

	/**
	 * @return the errorReason
	 */
	public String getErrorReason() {
		return errorReason;
	}

	/**
	 * @param errorReason the errorReason to set
	 */
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

	/**
	 * @return the invalidationRow
	 */
	public Integer getInvalidationRow() {
		return invalidationRow;
	}

	/**
	 * @param invalidationRow the invalidationRow to set
	 */
	public void setInvalidationRow(Integer invalidationRow) {
		this.invalidationRow = invalidationRow;
	}

}
