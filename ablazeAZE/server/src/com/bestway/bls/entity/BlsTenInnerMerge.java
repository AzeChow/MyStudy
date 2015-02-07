/*
 * Created on 2004-6-14
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放报关商品资料
 */

public class BlsTenInnerMerge extends BaseScmEntity {
    private static final long serialVersionUID  = CommonUtils
                                                        .getSerialVersionUID();

    /**
     * 十位商品编码
     */
    private Complex           complex           = null; 
    
    /**
     * 名称
     */
    private String            name              = null; 
    
    /**
     * 规格
     */
    private String            spec              = null; 

    
    /**
     * 归并序号
     */
    private Integer           seqNum            = null; 
    
    /**
     * 第一转换比率
     */
    private Double            firstRate         = null; 
    
    /**
     * 第二转换比率
     */
    private Double            secondRate        = null; 
    
    /**
     * 常用单位
     */
    private Unit              comUnit           = null; 
    
    /**
     * 单位重量
     */
    private Double            unitWeight        = null; 
    
    /**
     * 单价
     */
    private Double            price             = null; 
    
    /**
     * 币制
     */
    private Curr              curr              = null; 
    
    /**
     * 产销国
     */
    private Country           country           = null; 
    
    /**
     * 损耗
     */
    private Double            wear              = null; 
    
//    /**
//     * 类型1
//     */
//    private String            scmCoi            = null; 
    
    /**
     * 主辅料
     */
    private Boolean isMainImg=false;
    
    /**
     * 加工费单价1
     */
    private Double            machPrice         = null; 
    
    /**
     * 第一法定数量
     */
    private Double            legalAmount       = null; 
    
    /**
     * 第二法定数量
     */
    private Double            secondLegalAmount = null; 
    
    /**
     * 是否选中
     */
    private Boolean isSelected=false;

    
    /**
     * 获取常用单位(BCS)
     * 
     * @return comUnit 常用单位(BCS)
     */
    public Unit getComUnit() {
        return comUnit;
    }

    /**
     * 设置常用单位(BCS)
     * 
     * @param comUnit 常用单位(BCS)
     */
    public void setComUnit(Unit comUnit) {
        this.comUnit = comUnit;
    }

    /**
     * 获取产销国(BCS)
     * 
     * @return country 产销国(BCS)
     */
    public Country getCountry() {
        return country;
    }

    /**
     * 设置产销国(BCS)
     * 
     * @param country 产销国(BCS)
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * 获取币制(BCS)
     * 
     * @return curr 币制(BCS)
     */
    public Curr getCurr() {
        return curr;
    }

    /**
     * 设置币制(BCS)
     * 
     * @param curr 币制(BCS)
     */
    public void setCurr(Curr curr) {
        this.curr = curr;
    }

    /**
     * 获取第一转换比率
     * 
     * @return firstRate 第一转换比率
     */
    public Double getFirstRate() {
        return firstRate;
    }

    /**
     * 设置第一转换比率
     * 
     * @param firstRate 第一转换比率
     */
    public void setFirstRate(Double firstRate) {
        this.firstRate = firstRate;
    }

    /**
     * 获取加工费单价1
     * 
     * @return machPrice 加工费单价1
     */
    public Double getMachPrice() {
        return machPrice;
    }

    /**
     * 设置加工费单价1
     * 
     * @param machPrice 加工费单价1
     */
    public void setMachPrice(Double machPrice) {
        this.machPrice = machPrice;
    }

    /**
     * 获取名称
     * 
     * @return name 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     * 
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取单价(BCS)
     * 
     * @return price 单价(BCS)
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 设置单价(BCS)
     * 
     * @param price 单价(BCS)
     */
    public void setPrice(Double price) {
        this.price = price;
    }

//    /**
//     * 获取类型1
//     * 
//     * @return scmCoi 类型1
//     */
//    public String getScmCoi() {
//        return scmCoi;
//    }
//
//    /**
//     * 设置类型1
//     * 
//     * @param scmCoi 类型1
//     */ 
//    public void setScmCoi(String scmCoi) {
//        this.scmCoi = scmCoi;
//    }

    /**
     * 获取第二转换比率
     * 
     * @return secondRate 第二转换比率
     */
    public Double getSecondRate() {
        return secondRate;
    }

    /**
     * 设置第二转换比率
     * 
     * @param secondRate 第二转换比率
     */
    public void setSecondRate(Double secondRate) {
        this.secondRate = secondRate;
    }

    /**
     * 获取凭证序号
     * 
     * @return seqNum 凭证序号
     */
    public Integer getSeqNum() {
        return seqNum;
    }

    /**
     * 设置凭证序号
     * 
     * @param seqNum 凭证序号
     */
    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }

    /**
     * 获取规格
     * 
     * @return spec 规格
     */
    public String getSpec() {
        return spec;
    }

    /**
     * 设置规格
     * 
     * @param spec 规格
     */
    public void setSpec(String spec) {
        this.spec = spec;
    }

      /**
     * 获取单位重量(BCS)
     * 
     * @return unitWeight 单位重量(BCS)
     */
    public Double getUnitWeight() {
        return unitWeight;
    }

    /**
     * 设置单位重量(BCS)
     * 
     * @param unitWeight 单位重量(BCS)
     */
    public void setUnitWeight(Double unitWeight) {
        this.unitWeight = unitWeight;
    }

    /**
     * 获取损耗
     * 
     * @return wear 损耗
     */
    public Double getWear() {
        return wear;
    }

    /**
     * 设置损耗
     * 
     * @param wear 损耗
     */
    public void setWear(Double wear) {
        this.wear = wear;
    }

    /**
     * 获取十位商品编码
     * 
     * @return complex 十位商品编码
     */
    public Complex getComplex() {
        return complex;
    }

    /**
     * 设置十位商品编码
     * 
     * @param complex 十位商品编码
     */
    public void setComplex(Complex complex) {
        this.complex = complex;
    }

    /**
     * 获取第一法定数量
     * 
     * @return legalAmount 第一法定数量
     */
    public Double getLegalAmount() {
        return legalAmount;
    }

    /**
     * 设置第一法定数量
     * 
     * @param legalAmount 第一法定数量
     */
    public void setLegalAmount(Double legalAmount) {
        this.legalAmount = legalAmount;
    }

    /**
     * 获取第二法定数量
     * 
     * @return secondLegalAmount 第二法定数量
     */
    public Double getSecondLegalAmount() {
        return secondLegalAmount;
    }

    /**
     * 设置第二法定数量 
     * 
     * @param secondLegalAmount 第二法定数量
     */
    public void setSecondLegalAmount(Double secondLegalAmount) {
        this.secondLegalAmount = secondLegalAmount;
    }

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Boolean getIsMainImg() {
		return isMainImg;
	}

	public void setIsMainImg(Boolean isMainImg) {
		this.isMainImg = isMainImg;
	}

    
}