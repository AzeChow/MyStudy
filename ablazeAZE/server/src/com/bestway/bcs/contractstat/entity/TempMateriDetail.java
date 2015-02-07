package com.bestway.bcs.contractstat.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
/**
 * 
 * @author lyh 2012-11-28
 *
 */
public class TempMateriDetail implements Serializable , Comparable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//料件序号
	private Integer cpseqNum;
	//料件货号
	private String imgSeqNum;
	//料件报关名称
	private String imgCommName;
	//料件报关规格
	private String imgCommSpec;
	//料件报关单位
	private Unit imgUnit;
	//成品货号
	private String exgSeqNum;
	//成品报关名称
	private String exgCommName;
	//成品报关规格
	private String exgCommSpec;
	//成品报关单位
	private Unit exgUnit;
	//版本号
	private String hsVersion;
	//出口量
	private Double exportNum;
	//出口耗用量
	private Double exportSumNum;
	//报关单号
	private String customsDeclarationCode;
	//进出口类型
	private int impExpType;
	//报关日期
	private Date declarationDate;
	//申请单号
	private String billNo;
	/**
	 * 单耗
	 */
	private Double unitWaste;  
	
	/**
	 * 损耗
	 */
	private Double waste;  
	
	public Integer getCpseqNum() {
		return cpseqNum;
	}
	public void setCpseqNum(Integer cpseqNum) {
		this.cpseqNum = cpseqNum;
	}

	public String getImgSeqNum() {
		return imgSeqNum;
	}
	public void setImgSeqNum(String imgSeqNum) {
		this.imgSeqNum = imgSeqNum;
	}
	public String getImgCommName() {
		return imgCommName;
	}
	public void setImgCommName(String imgCommName) {
		this.imgCommName = imgCommName;
	}
	public String getImgCommSpec() {
		return imgCommSpec;
	}
	public void setImgCommSpec(String imgCommSpec) {
		this.imgCommSpec = imgCommSpec;
	}
	public Unit getImgUnit() {
		return imgUnit;
	}
	public void setImgUnit(Unit imgUnit) {
		this.imgUnit = imgUnit;
	}
	public String getExgSeqNum() {
		return exgSeqNum;
	}
	public void setExgSeqNum(String exgSeqNum) {
		this.exgSeqNum = exgSeqNum;
	}
	public String getExgCommName() {
		return exgCommName;
	}
	public void setExgCommName(String exgCommName) {
		this.exgCommName = exgCommName;
	}
	public String getExgCommSpec() {
		return exgCommSpec;
	}
	public void setExgCommSpec(String exgCommSpec) {
		this.exgCommSpec = exgCommSpec;
	}
	public Unit getExgUnit() {
		return exgUnit;
	}
	public void setExgUnit(Unit exgUnit) {
		this.exgUnit = exgUnit;
	}
	public String getHsVersion() {
		return hsVersion;
	}
	public void setHsVersion(String hsVersion) {
		this.hsVersion = hsVersion;
	}
	public Double getExportNum() {
		return exportNum;
	}
	public void setExportNum(Double exportNum) {
		this.exportNum = exportNum;
	}
	public Double getExportSumNum() {
		return exportSumNum;
	}
	public void setExportSumNum(Double exportSumNum) {
		this.exportSumNum = exportSumNum;
	}
	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}
	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}
	public int getImpExpType() {
		return impExpType;
	}
	public void setImpExpType(int impExpType) {
		this.impExpType = impExpType;
	}
	public Date getDeclarationDate() {
		return declarationDate;
	}
	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	public Double getUnitWaste() {
		return unitWaste;
	}
	public void setUnitWaste(Double unitWaste) {
		this.unitWaste = unitWaste;
	}
	public Double getWaste() {
		return waste;
	}
	public void setWaste(Double waste) {
		this.waste = waste;
	}
	@Override
	public int compareTo(Object obj) {
		TempMateriDetail info = (TempMateriDetail)obj;
		if(this.getImgSeqNum()==null){
			return -1;
		}
		if(this.getImgSeqNum().compareTo(info.getImgSeqNum())>0){
			return 1;
		}else if (this.getImgSeqNum().compareTo(info.getImgSeqNum())<=0) {
			return -1;
		}else{
			return 0;
		}
	}
}
