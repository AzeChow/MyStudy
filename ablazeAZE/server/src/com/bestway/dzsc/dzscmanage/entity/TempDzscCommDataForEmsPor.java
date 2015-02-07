/*
 * Created on 2004-7-2
 * 内部归并
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.dzscmanage.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

/**
 * 临时实体类,TempDzscCommDataForEmsPor
 * 
 * @author bsway
 */
public class TempDzscCommDataForEmsPor implements Serializable {
	
	/**
	 *  十位编码序号
	 */
	private Integer tenSeqNum; 

	/**
	 *  十位商品编码<第二层>
	 */
	private Complex tenComplex; 

	/**
	 *  十位商品名称
	 */
	private String tenPtName; 

	/**
	 *  十位商品规格
	 */
	private String tenPtSpec; 

	/**
	 *  十位商品单位
	 */
	private Unit tenUnit;
	  /**
     * 加工费单价
     */
    private Double machinPrice; 
	/**
	 * 法定单位比例因子
	 */
	private Double legalUnitGene;

	/**
	 * 第二法定单位比例因子
	 */
	private Double legalUnit2Gene;

	/**
	 * 重量单位比例因子
	 */
	private Double weigthUnitGene;
	/**
	 *  四位编码序号<第三层>
	 */
	private Integer fourSeqNum;

	/**
	 *  四位商品编码
	 */
	private String fourComplex;

	/**
	 *  四位商品名称
	 */
	private String fourPtName;

	/**
	 *  四位商品规格
	 */
	private String fourPtSpec; 

	/**
	 * 获取四位商品编码
	 * 
	 * @return fourComplex 四位商品编码
	 */
	public String getFourComplex() {
		return fourComplex;
	}

	/**
	 * 设置四位商品编码
	 * 
	 * @param fourComplex 四位商品编码
	 */
	public void setFourComplex(String fourComplex) {
		this.fourComplex = fourComplex;
	}

	/**
	 * 获取四位商品名称
	 * 
	 * @return fourPtName 四位商品名称
	 */
	public String getFourPtName() {
		return fourPtName;
	}

	/**
	 * 设置四位商品名称
	 * 
	 * @param fourPtName 四位商品名称
	 */
	public void setFourPtName(String fourPtName) {
		this.fourPtName = fourPtName;
	}

	/**
	 * 获取四位商品规格
	 * 
	 * @return fourPtSpec 四位商品规格
	 */
	public String getFourPtSpec() {
		return fourPtSpec;
	}

	/**
	 * 设置四位商品规格
	 * 
	 * @param fourPtSpec 四位商品规格
	 */
	public void setFourPtSpec(String fourPtSpec) {
		this.fourPtSpec = fourPtSpec;
	}

	/**
	 * 获取四位编码序号<第三层>
	 * 
	 * @return fourSeqNum 四位编码序号<第三层>
	 */
	public Integer getFourSeqNum() {
		return fourSeqNum;
	}

	/**
	 * 设置四位编码序号<第三层>
	 * 
	 * @param fourSeqNum 四位编码序号<第三层>
	 */
	public void setFourSeqNum(Integer fourSeqNum) {
		this.fourSeqNum = fourSeqNum;
	}

	/**
	 * 获取十位商品编码<第二层>
	 * 
	 * @return tenComplex 十位商品编码<第二层>
	 */
	public Complex getTenComplex() {
		return tenComplex;
	}

	/**
	 * 设置十位商品编码<第二层>
	 * 
	 * @param tenComplex 十位商品编码<第二层>
	 */
	public void setTenComplex(Complex tenComplex) {
		this.tenComplex = tenComplex;
	}

	/**
	 * 获取四位商品名称
	 * 
	 * @return tenPtName 四位商品名称
	 */
	public String getTenPtName() {
		return tenPtName;
	}

	/**
	 * 设置四位商品名称
	 * 
	 * @param tenPtName 四位商品名称
	 */
	public void setTenPtName(String tenPtName) {
		this.tenPtName = tenPtName;
	}

	/**
	 * 获取四位商品规格
	 * 
	 * @return tenPtSpec 四位商品规格
	 */
	public String getTenPtSpec() {
		return tenPtSpec;
	}

	/**
	 * 设置四位商品规格
	 * 
	 * @param tenPtSpec 四位商品规格
	 */
	public void setTenPtSpec(String tenPtSpec) {
		this.tenPtSpec = tenPtSpec;
	}

	/**
	 * 获取十位编码序号
	 * 
	 * @return tenSeqNum 十位编码序号
	 */
	public Integer getTenSeqNum() {
		return tenSeqNum;
	}

	/**
	 * 设置十位编码序号
	 * 
	 * @param tenSeqNum 十位编码序号
	 */
	public void setTenSeqNum(Integer tenSeqNum) {
		this.tenSeqNum = tenSeqNum;
	}

	/**
	 * 获取四位商品单位
	 * 
	 * @return tenUnit 四位商品单位
	 */
	public Unit getTenUnit() {
		return tenUnit;
	}

	/**
	 * 设置四位商品单位
	 * 
	 * @param tenUnit 四位商品单位
	 */
	public void setTenUnit(Unit tenUnit) {
		this.tenUnit = tenUnit;
	}

	public Double getMachinPrice() {
		return machinPrice;
	}

	public void setMachinPrice(Double machinPrice) {
		this.machinPrice = machinPrice;
	}

	public Double getLegalUnit2Gene() {
		return legalUnit2Gene;
	}

	public void setLegalUnit2Gene(Double legalUnit2Gene) {
		this.legalUnit2Gene = legalUnit2Gene;
	}

	public Double getLegalUnitGene() {
		return legalUnitGene;
	}

	public void setLegalUnitGene(Double legalUnitGene) {
		this.legalUnitGene = legalUnitGene;
	}

	public Double getWeigthUnitGene() {
		return weigthUnitGene;
	}

	public void setWeigthUnitGene(Double weigthUnitGene) {
		this.weigthUnitGene = weigthUnitGene;
	}

}