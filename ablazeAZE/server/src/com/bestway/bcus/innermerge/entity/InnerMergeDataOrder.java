/*
 * Created on 2004-7-2
 * 内部归并
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.entity;

// 李扬更改程序
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 存放企业内部归并－－内部归并资料
 * 
 * @author bsway
 */
public class InnerMergeDataOrder extends BaseScmEntity {

	private static final long	serialVersionUID	= CommonUtils
															.getSerialVersionUID();

	/**
	 * 序号
	 */
	private Integer seqNum;
	/**
	 * 归并序号
	 */
	private Integer ptNo;
	/**
	 * 商品编码
	 */
	private Integer tenComplex;
	/**
	 * 品名规格
	 */
	private Integer namespec;
	/**
	 * 名称
	 */
	private Integer name;
	
	/**
	 * 规格
	 */
	private Integer spec;
	
	/**
	 * 法定计量单位
	 */
	private Integer funit;
	
	/**
	 * 企业计量单位
	 */
	private Integer calunit;
	
	/**
	 * 备案序号
	 */
	private Integer hsSeqNum;
	/**
	 * 备案商品编码
	 */
	private Integer hsComplex;
	
	/**
	 * 备案商品编码名称
	 */
	private Integer hsnamespec;
	
	/**
	 * 备案名称
	 */
	private Integer hsname;
	
	/**
	 * 备案规格
	 */
	private Integer hsspec;
	
	/**
	 * 备案法定计量单位
	 */
	private Integer hsfunit;
	/**
	 * 备案计量单位
	 */
	private Integer hsunit;
	
	/**
	 * 四位备案序号
	 */
	private Integer fourseqnum;
	
	/**
	 * 四位商品名称
	 */
	private Integer fourName;
	
	/**
	 * 四位备案序号
	 */
	private Integer fourcomplex;
	/**
	 * 单重
	 */
	private Integer unitWeight;
	
	/**
	 * 单位折算系数
	 */
	private Integer unitConvert;
	
	
	
	
	public Integer getCalunit() {
		return calunit;
	}
	public void setCalunit(Integer calunit) {
		this.calunit = calunit;
	}
	public Integer getFourcomplex() {
		return fourcomplex;
	}
	public void setFourcomplex(Integer fourcomplex) {
		this.fourcomplex = fourcomplex;
	}
	public Integer getFourName() {
		return fourName;
	}
	public void setFourName(Integer fourName) {
		this.fourName = fourName;
	}
	public Integer getFourseqnum() {
		return fourseqnum;
	}
	public void setFourseqnum(Integer fourseqnum) {
		this.fourseqnum = fourseqnum;
	}
	public Integer getFunit() {
		return funit;
	}
	public void setFunit(Integer funit) {
		this.funit = funit;
	}
	public Integer getHsComplex() {
		return hsComplex;
	}
	public void setHsComplex(Integer hsComplex) {
		this.hsComplex = hsComplex;
	}
	public Integer getHsfunit() {
		return hsfunit;
	}
	public void setHsfunit(Integer hsfunit) {
		this.hsfunit = hsfunit;
	}
	public Integer getHsname() {
		return hsname;
	}
	public void setHsname(Integer hsname) {
		this.hsname = hsname;
	}
	public Integer getHsnamespec() {
		return hsnamespec;
	}
	public void setHsnamespec(Integer hsnamespec) {
		this.hsnamespec = hsnamespec;
	}
	public Integer getHsSeqNum() {
		return hsSeqNum;
	}
	public void setHsSeqNum(Integer hsSeqNum) {
		this.hsSeqNum = hsSeqNum;
	}
	public Integer getHsspec() {
		return hsspec;
	}
	public void setHsspec(Integer hsspec) {
		this.hsspec = hsspec;
	}
	public Integer getHsunit() {
		return hsunit;
	}
	public void setHsunit(Integer hsunit) {
		this.hsunit = hsunit;
	}
	public Integer getName() {
		return name;
	}
	public void setName(Integer name) {
		this.name = name;
	}
	public Integer getNamespec() {
		return namespec;
	}
	public void setNamespec(Integer namespec) {
		this.namespec = namespec;
	}
	public Integer getPtNo() {
		return ptNo;
	}
	public void setPtNo(Integer ptNo) {
		this.ptNo = ptNo;
	}
	public Integer getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	public Integer getSpec() {
		return spec;
	}
	public void setSpec(Integer spec) {
		this.spec = spec;
	}
	public Integer getTenComplex() {
		return tenComplex;
	}
	public void setTenComplex(Integer tenComplex) {
		this.tenComplex = tenComplex;
	}
	public Integer getUnitConvert() {
		return unitConvert;
	}
	public void setUnitConvert(Integer unitConvert) {
		this.unitConvert = unitConvert;
	}
	public Integer getUnitWeight() {
		return unitWeight;
	}
	public void setUnitWeight(Integer unitWeight) {
		this.unitWeight = unitWeight;
	}
	
}