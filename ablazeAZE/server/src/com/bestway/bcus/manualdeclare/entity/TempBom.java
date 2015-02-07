/*
 * Created on 2004-12-3
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.entity;

import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;

/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TempBom {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	private String ptNo;
   private String ptName;
   private String ptSpec;
   private double unitWaste;
   private double waste;
   private CalUnit calUnit;
   private double unitAmount;
/**
 * @return Returns the calUnit.
 */
public CalUnit getCalUnit() {
	return calUnit;
}
/**
 * @param calUnit The calUnit to set.
 */
public void setCalUnit(CalUnit calUnit) {
	this.calUnit = calUnit;
}
/**
 * @return Returns the ptName.
 */
public String getFactoryName() {
	return ptName;
}
/**
 * @param ptName The ptName to set.
 */
public void setFactoryName(String ptName) {
	this.ptName = ptName;
}
/**
 * @return Returns the ptNo.
 */
public String getPtNo() {
	return ptNo;
}
/**
 * @param ptNo The ptNo to set.
 */
public void setPtNo(String ptNo) {
	this.ptNo = ptNo;
}
/**
 * @return Returns the ptSpec.
 */
public String getFactorySpec() {
	return ptSpec;
}
/**
 * @param ptSpec The ptSpec to set.
 */
public void setFactorySpec(String ptSpec) {
	this.ptSpec = ptSpec;
}
/**
 * @return Returns the unitAmount.
 */
public double getUnitAmount() {
	return unitAmount;
}
/**
 * @param unitAmount The unitAmount to set.
 */
public void setUnitAmount(double unitAmount) {
	this.unitAmount = unitAmount;
}
/**
 * @return Returns the unitWaste.
 */
public double getUnitWaste() {
	return unitWaste;
}
/**
 * @param unitWaste The unitWaste to set.
 */
public void setUnitWaste(double unitWaste) {
	this.unitWaste = unitWaste;
}
/**
 * @return Returns the waste.
 */
public double getWaste() {
	return waste;
}
/**
 * @param waste The waste to set.
 */
public void setWaste(double waste) {
	this.waste = waste;
}
}
