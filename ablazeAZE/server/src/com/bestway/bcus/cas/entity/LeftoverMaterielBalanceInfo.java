package com.bestway.bcus.cas.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 边角料平衡信息表
 * @author Administrator
 *
 */
public class LeftoverMaterielBalanceInfo extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils
                                                       .getSerialVersionUID();
    
    /**
     * 商品编码 
     */
    private String            complex          = "";                              
    /**
     * 名称
     */
    private String            name             = "";                          
    /**
     * 规格
     */
    private String            spec             = "";                          
    /**
     * 单位
     */
    private String            unitName         = "";                              
    /**
     * 边角料入库量 = 边角料入库单的数量累计
     */
    private Double            f1               = 0.0;                         
    /**
     * 边角料出库量 = 边角料出库单的数量累计
     */
    private Double            f2               = 0.0;                         
    /**
     * 实际库存量 =（f1）-（f2）
     */
    private Double            f3               = 0.0;                         
    /**
     * 海关帐实际损耗量 = 成品入库单折损耗
     */
    private Double            f4               = 0.0;                         
    /**
     * 出口成品损耗量（手册）= 各合同的损耗量之和
     */
    private Double            f5               = 0.0;                         
    /**
     * 差额 = f1 - f5 
     */
    private Double            f6               = 0.0;                         
    /**
     * 单价
     */
    private Double            unitPrice        = 0.0;                         
    /**
     * 出口税率
     */
    private Double            outRate          = 0.0 ;                            
    /**
     * 增值税率
     */
    private Double            taxRate          = 0.0;                         
    /**
     * 应补税额 = 差额 * 单价 * 出口税率 + [(差额 * 单价 * 出口税率 +  差额 * 单价 ) * 增值税率]
     */
    private Double            f7               = 0.0;                         
 
    
    
    /**
     * 取得唯一键值 
     * @return  name+ptSpec+ptUnitName 名称+规格+单位
     */
    public String getKey(){
        String ptSpec  = (this.spec == null || "".equals(this.spec))?"":"/"+this.spec;   
        String ptUnitName = (this.unitName == null || "".equals(this.unitName))?"":"/"+this.unitName; 
        return this.name+ptSpec+ptUnitName;
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


    /**
     * 取得出口税率
     * @return outRate 出口税率
     */
    public Double getOutRate() {
        return outRate;
    }


    /**
     * 设置出口税率
     * @param outRate  出口税率
     */
    public void setOutRate(Double outRate) {
        this.outRate = outRate;
    }


    /**
     * 取得增值税率
     * @return taxRate 增值税率
     */
    public Double getTaxRate() {
        return taxRate;
    }


    /**
     * 设置增值税率
     * @param taxRate 增值税率  
     */
    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }


    /**
     * 取得单价
     * @return unitPrice 单价
     */
    public Double getUnitPrice() {
        return unitPrice;
    }


    /**
     * 设置单价
     * @param unitPrice 单价
     */
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }


    /**
     * 取得边角料入库量（边角料入库单的数量累计）
     * @return  f1 边角料入库量 = 边角料入库单的数量累计
     */
    public Double getF1() {
        return f1;
    }



    /**
     * 边角料入库量（边角料入库单的数量累计）
     * @param f1  边角料入库量 = 边角料入库单的数量累计
     */
    public void setF1(Double f1) {
        this.f1 = f1;
    }





    /**
     * 取得边角料出库量（边角料出库单的数量累计）
     * @return f2 边角料出库量 = 边角料出库单的数量累计
     */
    public Double getF2() {
        return f2;
    }




    /**
     * 设置边角料出库量（边角料出库单的数量累计）
     * @param f2 边角料出库量 = 边角料出库单的数量累计
     */
    public void setF2(Double f2) {
        this.f2 = f2;
    }




    /**
     * 取得实际库存量
     * @return f3=f1 - f2 实际库存数量 = 边角料入库数量 - 边角料出库数量
     */
    public Double getF3() {
        return f1 - f2;
    }


 


    /**
     * 取得海关帐实际损耗量
     * @return f4 海关帐实际损耗量 = 成品入库单折损耗
     */
    public Double getF4() {
        return f4;
    }




    /**
     * 设置海关帐实际损耗量
     * @param f4  海关帐实际损耗量 = 成品入库单折损耗
     */
    public void setF4(Double f4) {
        this.f4 = f4;
    }




    /**
     * 取得出口成品损耗量（各合同的损耗量之和）
     * @return f5 出口成品损耗量（手册）= 各合同的损耗量之和
     */
    public Double getF5() {
        return f5;
    }




    /**
     * 设置出口成品损耗量（各合同的损耗量之和）
     * @param f5  出口成品损耗量（手册）= 各合同的损耗量之和
     */
    public void setF5(Double f5) {
        this.f5 = f5;
    }




    /**
     * 取得差额
     * @return f6 = f1 -f5 差额 = 边角料入库量 - 出口成品损耗量 
     */
    public Double getF6() {
        return f1 -f5;
    }




    



    /**
     * 取得应补税额
     * @return f7 = f5 * unitPrice * outRate  + (f5 * unitPrice * outRate + f5 * unitPrice) * taxRate
     *  应补税额 = 差额 * 单价 * 出口税率 + [(差额 * 单价 * 出口税率 +  差额 * 单价 ) * 增值税率]
     */
    public Double getF7() {
        double f6 = getF6();
        return f6 * unitPrice * outRate  + (f6 * unitPrice * outRate + f6 * unitPrice) * taxRate;
    }
      



    /**
     * 取得名称
     * @return name 名称
     */
    public String getName() {
        return name;
    }




    /**
     * 设置名称
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }




    /**
     * 取得规格
     * @return spec 规格
     */
    public String getSpec() {
        return spec;
    }




    /**
     * 设置规格
     * @param spec 规格
     */
    public void setSpec(String spec) {
        this.spec = spec;
    }




    /**
     * 取得单位
     * @return unitName 单位
     */
    public String getUnitName() {
        return unitName;
    }




    /**
     * 设置单位
     * @param unitName 单位
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }




    
    
}
