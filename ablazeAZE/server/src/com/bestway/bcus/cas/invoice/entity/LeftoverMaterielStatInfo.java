package com.bestway.bcus.cas.invoice.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 边角料状况信息表
 * @author wss
 *
 */
public class LeftoverMaterielStatInfo extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils
                                                       .getSerialVersionUID();
    
    /**
     * 商品编码 
     */
    private String            complex            = "";     
    
    /**
     * 名称
     */
    private String            hsName             = "";  
    
    /**
     * 规格
     */
    private String            hsSpec             = "";                          
    /**
     * 单位
     */
    private String            hsUnitName         = "";        
    
    /**
     * 边角料入库量 = 边角料入库单 (所有年度的) 数量累计
     */
    private Double            f1               = 0.0;    
    
    /**
     * 边角料出库量 = 边角料出库单 (所有年度的) 数量累计
     */
    private Double            f2               = 0.0;     
    
    /**
     * 实际库存量 =（f1）-（f2）
     */
    private Double            f3               = 0.0;     
    
    /**
     * 成品出库折损耗（系统中所有 未结案合同的结转出口报关单+直接出口报关单 折合同损耗）
     */
    private Double            f4               = 0.0;  
    
    /**
     * 已打税（系统中已结案合同边角料打税的报关单数据）
     */
    private Double            f5               = 0.0;   
    
    /**
     * 差额 = f5 - f2
     * (已打税未出库期初单+已打税-边角料出库)
     */
    private Double            f6               = 0.0;  
    
    /**
     * 差额 = f4 + f5 - f2
     * (已打税未出库期初+成品出库存折损耗+已打税边角料—边角料出库)
     */
    private Double            f7        = 0.0;                         
   
    
    /**
     * 取得唯一键值 
     * @return  name+ptSpec+ptUnitName 名称+规格+单位
     */
    public String getKey(){
        String hsSpec  = (this.hsSpec == null || "".equals(this.hsSpec))?"":"/"+this.hsSpec;   
        String hsUnitName = (this.hsUnitName == null || "".equals(this.hsUnitName))?"":"/"+this.hsUnitName; 
        return this.hsName+hsSpec+hsUnitName;
    }
    
    /**
     * 取得商品编码
     * @return complex 商品编码
     */
    public String getComplex() {
        return complex;
    }


    /**
     * 设置商品编码
     * @param complex 商品编码
     */
    public void setComplex(String complex) {
        this.complex = complex;
    }


	public String getHsName() {
		return hsName;
	}


	public void setHsName(String hsName) {
		this.hsName = hsName;
	}


	public String getHsSpec() {
		return hsSpec;
	}


	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}


	public String getHsUnitName() {
		return hsUnitName;
	}


	public void setHsUnitName(String hsUnitName) {
		this.hsUnitName = hsUnitName;
	}

	/**
	 * 边角料入库单 (所有年度的) 数量累计
	 * @return
	 */
	public Double getF1() {
		return f1;
	}


	/**
	 * 边角料入库单 (所有年度的) 数量累计
	 * @param f1
	 */
	public void setF1(Double f1) {
		this.f1 = f1;
	}

	/**
	 * 边角料出库单 (所有年度的) 数量累计
	 * @return
	 */
	public Double getF2() {
		return f2;
	}


	/**
	 * 边角料出库单 (所有年度的) 数量累计
	 * @param f2
	 */
	public void setF2(Double f2) {
		this.f2 = f2;
	}


	/**
	 * 实际库存量 =（f1）-（f2）
	 * @return
	 */
	public Double getF3() {
		return (f1 == null? 0.0:f1) - (f2 == null? 0.0:f2) ;
	}


	/**
	 * 实际库存量 =（f1）-（f2）
	 * @param f3
	 */
	public void setF3(Double f3) {
		this.f3 = f3;
	}


	/**
	 * 成品出库折损耗（系统中所有 未结案合同的结转出口报关单+直接出口报关单 折合同损耗）
	 * @return
	 */
	public Double getF4() {
		return f4;
	}


	/**
	 * 成品出库折损耗（系统中所有 未结案合同的结转出口报关单+直接出口报关单 折合同损耗）
	 * @param f4
	 */
	public void setF4(Double f4) {
		this.f4 = f4;
	}


	/**
	 * 已打税（系统中已结案合同边角料打税的报关单数据）
	 * @return
	 */
	public Double getF5() {
		return f5;
	}


	/**
	 * 已打税（系统中已结案合同边角料打税的报关单数据）
	 * @param f5
	 */
	public void setF5(Double f5) {
		this.f5 = f5;
	}

	/**
	 * 差额 = f5 - f2
	 * @return
	 * (已打税未出库期初单+已打税-边角料出库)
	 */
	public Double getF6(){
		return f6 == null ? 0.0 : f6 
				+ (getF5() == null ? 0.0 : getF5())
				- (getF2() == null ? 0.0 : getF2());
				
	}

	/**
	 * 差额 = f5 - f2
	 * @param f6
	 */
	public void setF6(Double f6) {
		this.f6 = f6;
	}


	/**
	 * 差额 = f4 + f5 - f2
	 * @return
	 */
	public Double getF7() {
		return f7 == null ? 0.0 : f7 
				+ (getF4() == null ? 0.0 : getF4())
				+ (getF5() == null ? 0.0 : getF5())
				- (getF2() == null ? 0.0 : getF2());
	}


	/**
	 * 差额 = f4 + f5 - f2
	 * @param f7
	 */
	public void setF7(Double f7) {
		this.f7 = f7;
	}


}
