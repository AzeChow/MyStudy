package com.bestway.bcus.cas.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
/**
 * 工厂和实际报关对应表
 * @author ower
 *
 */
public class FactoryAndFactualCustomsRalation extends BaseScmEntity {
	/**
	 * 版本信息
	 */
	private static final long serialVersionUID = -9126204848565501707L;

	/**
	 * 工厂物料
	 */
	private Materiel materiel;  //  @jve:decl-index=0:

	/**
	 * 实际报关
	 */
	private StatCusNameRelationHsn statCusNameRelationHsn;  //  @jve:decl-index=0:

	/**
	 * 物料与实际报关的折算系数
	 */
	private Double unitConvert;  //  @jve:decl-index=0:
	/**
	 * 客户供应商
	 */
	private ScmCoc scmCoc;

	//private Double inNetWeight;
	/**
	 * 备注 
	 */
	private String remark;
	
	/**
	 * 获取备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 临时字段1 
	 */
	private String temp1;
	
	/**
	 * 临时字段2 
	 */
	private String temp2;
	
	/**
	 * 临时字段3 
	 */
	private String temp3;
	
	/**
	 * 于第?行检查数据错误
	 */
	private Integer invalidationRow;
	
	/**
	 * 错误原因
	 */
	private String errorReason;

	public StatCusNameRelationHsn getStatCusNameRelationHsn() {
		return statCusNameRelationHsn;
	}

	public void setStatCusNameRelationHsn(
			StatCusNameRelationHsn statCusNameRelationHsn) {
		this.statCusNameRelationHsn = statCusNameRelationHsn;
	}

	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	public Double getUnitConvert() {
		return unitConvert;
	}

	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
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
	 * @return the temp3
	 */
	public String getTemp3() {
		return temp3;
	}

	/**
	 * @param temp3 the temp3 to set
	 */
	public void setTemp3(String temp3) {
		this.temp3 = temp3;
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

	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}
}
