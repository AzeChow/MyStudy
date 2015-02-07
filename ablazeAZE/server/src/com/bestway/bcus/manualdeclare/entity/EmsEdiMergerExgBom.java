/*
 * Created on 2004-7-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 成品BOM表
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */   // 成品BOM表（来自于归并前料件）
public class EmsEdiMergerExgBom extends BaseScmEntity{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 归并前成品
	 */
	private EmsEdiMergerVersion emsEdiMergerVersion;  
//	/**
//	 * 归并前料件
//	 */
//	private EmsEdiMergerImgBefore emsEdiMergerImgBefore; 
	/**
	 * 料件货号
	 */
	private String ptNo;      
	/**
	 * 商品编码
	 */
	private Complex complex;  
	/**
	 * 商品名称
	 */
	private String name;       
	/**
	 * 规格型号
	 */
	private String spec;   
	/**
	 * 申报单位
	 */
	private Unit unit;        
	/**
	 * 单耗
	 */
	private Double unitWaste;  
	/**
	 * 损耗
	 */
	private Double waste;     
	/**
	 * 加工性质
	 */
	private String machinKind;  
	/**
	 * 开始日期
	 */
    private Date beginDate;    
    /**
     * 结束日期
     */
    private Date endDate;      
    /**
     * 备注
     */
	private String note;       
	/**
	 * 变更次数
	 */
	private Integer modifyTimes;
    /**
	 * 修改标志
	 * ModifyMarkState
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String modifyMark;  
	/**
	 * 变更日期
	 */
	private Date changeDate;    
	/**
	 * 发送状态SendState
	 * NOT_SEND = "0";	未发送
	 * WAIT_SEND = "1";	准备发送
	 * SEND = "2";	已经发送
	 */
	private Integer sendState; 
	
	/**
	 * 修改前单耗（技嘉用）
	 */
	private Double modifyAgjUnitWaste;
	
	/**
	 * 修改前损耗率%（技嘉用）
	 */
	private Double modifyAgjWaste;

	/**
	 * 单耗差异（技嘉用）
	 */
	private Double unitWasteDifferences;
	
	/**
	 * BOM备份标志（技嘉用）
	 */
	private Boolean isBackup;
	
	/**
	 * @return Returns the modifyMark.
	 */
	public String getModifyMark() {
		return modifyMark;
	}
	/**
	 * @param modifyMark The modifyMark to set.
	 */
	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}
	/**
	 * @return Returns the modifyTimes.
	 */
	public Integer getModifyTimes() {
		return modifyTimes;
	}
	/**
	 * @param modifyTimes The modifyTimes to set.
	 */
	public void setModifyTimes(Integer modifyTimes) {
		this.modifyTimes = modifyTimes;
	}
	/**
	 * 返回更改次数（海关申报用，固定为0）
	 */
	public Integer getModifyTimesMark() {
		return 0;
	}
	/**
	 * @return Returns the beginDate.
	 */
	public Date getBeginDate() {
		return beginDate;
	}
	/**
	 * @param beginDate The beginDate to set.
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	/**
	 * @return Returns the complex.
	 */
	public Complex getComplex() {
		return complex;
	}
	/**
	 * @param complex The complex to set.
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
	}


	/**
	 * @return Returns the endDate.
	 */
	public Date getEndDate() {
		
//		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
//		"yyyy-MM-dd HH:mm:ss");
//        try {
//	        return dateFormat.parse("9999-12-31 00:00:00");
//        } catch (Exception ex) {
//	        ex.printStackTrace();
//        }
		return null;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return Returns the machinKind.
	 */
	public String getMachinKind() {
		return machinKind;
	}
	/**
	 * @param machinKind The machinKind to set.
	 */
	public void setMachinKind(String machinKind) {
		this.machinKind = machinKind;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the note.
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note The note to set.
	 */
	public void setNote(String note) {
		this.note = note;
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
	 * @return Returns the spec.
	 */
	public String getSpec() {
		return spec;
	}
	/**
	 * @param spec The spec to set.
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
	/**
	 * @return Returns the unit.
	 */
	public Unit getUnit() {
		return unit;
	}
	/**
	 * @param unit The unit to set.
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	/**
	 * @return Returns the unitWaste.
	 */
	public Double getUnitWaste() {
		return unitWaste;
	}
	/**
	 * @param unitWaste The unitWaste to set.
	 */
	public void setUnitWaste(Double unitWaste) {
		this.unitWaste = unitWaste;
	}
	/**
	 * @return Returns the waste.
	 */
	public Double getWaste() {
		return waste;
	}
	/**
	 * @param waste The waste to set.
	 */
	public void setWaste(Double waste) {
		this.waste = waste;
	}
//	/**
//	 * @return Returns the emsEdiMergerImgBefore.
//	 */
//	public EmsEdiMergerImgBefore getEmsEdiMergerImgBefore() {
//		return emsEdiMergerImgBefore;
//	}
//	/**
//	 * @param emsEdiMergerImgBefore The emsEdiMergerImgBefore to set.
//	 */
//	public void setEmsEdiMergerImgBefore(
//			EmsEdiMergerImgBefore emsEdiMergerImgBefore) {
//		this.emsEdiMergerImgBefore = emsEdiMergerImgBefore;
//	}
	/**
	 * @return Returns the emsEdiMergerVersion.
	 */
	public EmsEdiMergerVersion getEmsEdiMergerVersion() {
		return emsEdiMergerVersion;
	}
	/**
	 * @param emsEdiMergerVersion The emsEdiMergerVersion to set.
	 */
	public void setEmsEdiMergerVersion(EmsEdiMergerVersion emsEdiMergerVersion) {
		this.emsEdiMergerVersion = emsEdiMergerVersion;
	}
	public Date getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	public Integer getSendState() {
		return sendState;
	}
	public void setSendState(Integer sendState) {
		this.sendState = sendState;
	}
	
	public Double getWasteL(){
		if (this.getWaste() == null){
			return Double.valueOf(0);
		}
		return this.getWaste() / 100;
	}
	public String getNameSpec(){
		String name = (this.getName() == null) ?"":this.getName();
		String spec = (this.getSpec() == null) ?"":this.getSpec();
		return name + "/" + spec;
	}
	public Double getUnitWasteDifferences() {
		return unitWasteDifferences;
	}
	public void setUnitWasteDifferences(Double unitWasteDifferences) {
		this.unitWasteDifferences = unitWasteDifferences;
	}
	public Double getModifyAgjWaste() {
		return modifyAgjWaste;
	}
	public void setModifyAgjWaste(Double modifyAgjWaste) {
		this.modifyAgjWaste = modifyAgjWaste;
	}
	public Double getModifyAgjUnitWaste() {
		return modifyAgjUnitWaste;
	}
	public void setModifyAgjUnitWaste(Double modifyAgjUnitWaste) {
		this.modifyAgjUnitWaste = modifyAgjUnitWaste;
	}
	public Boolean getIsBackup() {
		return isBackup;
	}
	public void setIsBackup(Boolean isBackup) {
		this.isBackup = isBackup;
	}
}
