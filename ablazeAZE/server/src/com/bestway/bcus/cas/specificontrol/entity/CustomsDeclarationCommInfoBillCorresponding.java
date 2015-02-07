/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.specificontrol.entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 报关单商品信息与海关帐单据的对应
 */
public class CustomsDeclarationCommInfoBillCorresponding extends BaseScmEntity {
    private static final long serialVersionUID           = CommonUtils
                                                                 .getSerialVersionUID();
    /**
     *  报关单ID号
     */
    private String            customsDeclarationId;                                     
    /**
     *  报关单商品信息Id
     */
    private String            customsDeclarationCommInfoId;                             
    /**
     * 报关单号
     */
    private String            customsDeclarationCode;                                  
    /**
     * 电子帐册号码
     */
    private String            emsHeadH2k;                                               
	/**
	 * 进出口类型
	 * DIRECT_IMPORT = 0;	料件进口 料件资料 直接进口---料件
	 * TRANSFER_FACTORY_IMPORT = 1;	料件转厂 料件资料 转厂进口---料件
	 * BACK_FACTORY_REWORK = 2;	退厂返工 成品资料 ---成品
	 * GENERAL_TRADE_IMPORT = 3;	一般贸易进口 --- 料件
	 * DIRECT_EXPORT = 4;	成品出口 成品资料 ---成品
	 * TRANSFER_FACTORY_EXPORT = 5;	转厂出口 成品资料 ---成品
	 * BACK_MATERIEL_EXPORT = 6;	退料出口 料件资料 ---料件
	 * REWORK_EXPORT = 7;	返工复出 成品资料 ---成品
	 * REMIAN_MATERIAL_BACK_PORT = 8;	边角料退港 ----料件
	 * REMIAN_MATERIAL_DOMESTIC_SALES = 9;	边角料内销 ---料件
	 * GENERAL_TRADE_EXPORT = 10;	一般贸易出口 ---成品
	 * EQUIPMENT_IMPORT = 11;	设备进口
	 * BACK_PORT_REPAIR = 12;	退港维修
	 * EQUIPMENT_BACK_PORT = 13;	设备退港
	 * REMAIN_FORWARD_EXPORT = 14;	余料结转(出口报关单)
	 * EXPORT_STORAGE = 15;	出口仓储
	 * IMPORT_STORAGE = 16;	进口仓储
	 * MATERIAL_DOMESTIC_SALES = 17;	料件内销，海关批准内销 ---料件
	 * MATERIAL_EXCHANGE = 18;	料件退换 ---料件
	 * MATERIAL_REOUT = 19;	料件复出 ---料件
	 * All_Type = 20;	所有（注意：不存在这样的类型，方便在参数设置中使用到）
	 * IMPORT_EXG_BACK = 25;	进料成品退换 ---成品
	 * IMPORT_REPAIR_MATERIAL = 26;	修理物品 ---成品
	 * EXPORT_MATERIAL_REBACK = 27;	修理物品复出 ---成品
	 * EXPORT_EXG_REBACK = 28;	进料成品退换复出 ---成品
	 * REMAIN_FORWARD_IMPORT = 21;	余料结转(进口报关单)
	 */
    private Integer           impExpType;                                               
    /**
     *  客户名称
     */
    private ScmCoc            scmCoc;                                                   
    /**
     * 报关单商品流水号
     */
    private Integer           serialNumber;                                             
    /**
     * 商品序号
     */
    private Integer           commSerialNo;                                             

    // (BCUS:电子账册的商品序号，BCS:合同序号)
    /**
     * 商品名称
     */
    private String            commName;                                                 
    /**
     * 商品信息
     */
    private Complex           complex;                                                  
    /**
     * 商品规格
     */
    private String            commSpec;                                                 
    /**
     * 商品数量
     */

    private Double            commAmount;                                               
    /**
     * 与大类单位折算比
     */
    private Double            unitConvert;                                               
    /**
     * 商品单价
     */
    private Double            commUnitPrice;                                           
    /**
     * 进出口日期
     */
    private Date              impExpDate;                                              
    /**
     * 单位
     */
    private Unit              unit;                                                     
    /**
     * 法定单位
     */
    private Unit              legalUnit;                                                
    /**
     * 第二法定单位
     */
    private Unit              secondLegalUnit;                                          
    /**
     * 第一法定数量
     */
    private Double            firstAmount;                                             
    /**
     * 第二法定数量
     */
    private Double            secondAmount;                                            
    /**
     * 版本号
     */
    private String            version;                                                 
    /**
     * 已对应报关数量
     */
    private Double            alreadyCorrespondingAmount = null;                        
    /**
     * 末对应报关数量
     */
    private Double            noCorrespondingAmount      = null;                        
    /**
     * 对应比率
     */
    private Double            correspondingRate          = null;   
    
    /**
     * 关封号
     * wss2010.09.20新增，用于转厂
     */
    private String envelopNo;
    
    
   /**
    * 关封号
    * @return
    */
    public String getEnvelopNo() {
		return envelopNo;
	}
    
    /**
     * 关封号
     * @param envelopNo
     */
	public void setEnvelopNo(String envelopNo) {
		this.envelopNo = envelopNo;
	}
	
	/**
     * 取得商品数量
	 * @return commAmount  商品数量.
	 */
    public Double getCommAmount() {
        return commAmount;
    }
    /**
     * 设置商品数量
	 * @param commAmount 商品数量 
	 */
    public void setCommAmount(Double commAmount) {
        this.commAmount = commAmount;
    }
    /**
     * 取得商品名称
	 * @return commName 商品名称
	 */
    public String getCommName() {
        return commName;
    }
    /**
     * 设置商品名称
	 * @param commName 商品名称.
	 */
    public void setCommName(String commName) {
        this.commName = commName;
    }
    /**
     * 取得商品序号
	 * @return commSerialNo 商品序号.
	 */
    public Integer getCommSerialNo() {
        return commSerialNo;
    }
    /**
     * 设置商品序号
	 * @param commSerialNo 商品序号.
	 */
    public void setCommSerialNo(Integer commSerialNo) {
        this.commSerialNo = commSerialNo;
    }
    /**
     * 取得商品规格
	 * @return commSpec 商品规格.
	 */
    public String getCommSpec() {
        return commSpec;
    }
    /**
     * 设置商品规格
	 * @param commSpec  商品规格
	 */
    public void setCommSpec(String commSpec) {
        this.commSpec = commSpec;
    }
    /**
     * 取得报关单号
	 * @return customsDeclarationCode 报关单号
	 */
    public String getCustomsDeclarationCode() {
        return customsDeclarationCode;
    }
    /**
     * 设置报关单号
	 * @param customsDeclarationCode 报关单号
	 */
    public void setCustomsDeclarationCode(String customsDeclarationCode) {
        this.customsDeclarationCode = customsDeclarationCode;
    }
    /**
     * 取得报关单id
	 * @return customsDeclarationId  报关单id.
	 */
    public String getCustomsDeclarationId() {
        return customsDeclarationId;
    }
    /**
     * 设置报关单id
	 * @param customsDeclarationId  报关单id.
	 */
    public void setCustomsDeclarationId(String customsDeclarationId) {
        this.customsDeclarationId = customsDeclarationId;
    }
    /**
     * 取得电子帐册号码
	 * @return emsHeadH2k 电子帐册号.
	 */
    public String getEmsHeadH2k() {
        return emsHeadH2k;
    }
    /**
     * 设置电子帐册号
	 * @param emsHeadH2k 电子帐册号.
	 */
    public void setEmsHeadH2k(String emsHeadH2k) {
        this.emsHeadH2k = emsHeadH2k;
    }
    /**
     * 取得第一法定数量
	 * @return firstAmount 第一法定数量
	 */
    public Double getFirstAmount() {
        return firstAmount;
    }
    /**
     * 设置第一法定数量
	 * @param firstAmount 第一法定数量
	 */
    public void setFirstAmount(Double firstAmount) {
        this.firstAmount = firstAmount;
    }
    /**
     * 取得报关单日期
	 * @return impExpDate  报关单日期
	 */
    public Date getImpExpDate() {
        if (impExpDate == null) {
            return null;
        }
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return java.sql.Date.valueOf(bartDateFormat.format(impExpDate));
    }
    /**
     * 设置报关单日期
	 * @param impExpDate 报关单日期
	 */
    public void setImpExpDate(Date impExpDate) {
        this.impExpDate = impExpDate;
    }
    /**
     * 取得进出口类型
	 * @return impExpType 进出口类型
	 */
    public Integer getImpExpType() {
        return impExpType;
    }
    /**
     * 设置进出口类型
	 * @param impExpType  进出口类型
	 */
    public void setImpExpType(Integer impExpType) {
        this.impExpType = impExpType;
    }
    /**
     * 取得法定单位
	 * @return legalUnit 法定单位
	 */
    public Unit getLegalUnit() {
        return legalUnit;
    }
    /**
     * 设置法定单位
	 * @param legalUnit  法定单位
	 */
    public void setLegalUnit(Unit legalUnit) {
        this.legalUnit = legalUnit;
    }
    /**
     * 取得第二法定数量
	 * @return secondAmount 第二法定数量
	 */
    public Double getSecondAmount() {
        return secondAmount;
    }
    /**
     * 设置第二法定数量
	 * @param secondAmount 第二法定数量
	 */
    public void setSecondAmount(Double secondAmount) {
        this.secondAmount = secondAmount;
    }
    /**
     * 取得第二法定单位
	 * @return secondLegalUnit 第二法定单位
	 */
    public Unit getSecondLegalUnit() {
        return secondLegalUnit;
    }
    /**
     * 设置第二法定单位
	 * @param secondLegalUnit 第二法定单位
	 */
    public void setSecondLegalUnit(Unit secondLegalUnit) {
        this.secondLegalUnit = secondLegalUnit;
    }
    /**
     * 取得报关单商品流水号
	 * @return serialNumber  报关单商品流水号
	 */
    public Integer getSerialNumber() {
        return serialNumber;
    }
    /**设置报关单商品流水号
	 * @param serialNumber  报关单商品流水号
	 */
    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }
    /**
     * 取得单位
	 * @return unit 单位.
	 */
    public Unit getUnit() {
        return unit;
    }
    /**
     * 设置单位
	 * @param unit 单位
	 */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    /**
     * 取得版本号
	 * @return version 版本号
	 */
    public String getVersion() {
        return version;
    }
    /**
     * 设置版本号
	 * @param version  版本号
	 */
    public void setVersion(String version) {
        this.version = version;
    }
    /**
     * 取得已对应报关数量
	 * @return alreadyCorrespondingAmount 已对应报关数量
	 */
    public Double getAlreadyCorrespondingAmount() {
        return alreadyCorrespondingAmount;
    }
    /**
     * 设置已对应报关数量
	 * @param alreadyCorrespondingAmount  已对应报关数量
	 */
    public void setAlreadyCorrespondingAmount(Double alreadyCorrespondingAmount) {
        this.alreadyCorrespondingAmount = alreadyCorrespondingAmount;
    }

    
    /**
     * 取得对应比率
	 * @param correspondingRate 对应比率
	 */
    public void setCorrespondingRate(Double correspondingRate) {
        this.correspondingRate = correspondingRate;
    }
    /**
     * 设置未对应报关数量
	 * @param noCorrespondingAmount 未对应报关数量
	 */
    public void setNoCorrespondingAmount(Double noCorrespondingAmount) {
        this.noCorrespondingAmount = noCorrespondingAmount;
    }
    /**
     * 取得客户名称
	 * @return scmCoc 客户名称
	 */
    public ScmCoc getScmCoc() {
        return scmCoc;
    }
    /**
     * 设置客户名称
	 * @param scmCoc 客户名称
	 */
    public void setScmCoc(ScmCoc scmCoc) {
        this.scmCoc = scmCoc;
    }
    /**
     * 取得报关单商品信息id
	 * @return customsDeclarationCommInfoId 报关单商品信息id
	 */
    public String getCustomsDeclarationCommInfoId() {
        return customsDeclarationCommInfoId;
    }
    /**
     *设置报关单商品信息id
	 * @param customsDeclarationCommInfoId  报关单商品信息id
	 */
    public void setCustomsDeclarationCommInfoId(
            String customsDeclarationCommInfoId) {
        this.customsDeclarationCommInfoId = customsDeclarationCommInfoId;
    }
    /**
     * 取得商品编码
	 * @return complex 商品编码
	 */
    public Complex getComplex() {
        return complex;
    }
    /**
     * 设置商品编码
	 * @param complex 商品编码
	 */
    public void setComplex(Complex complex) {
        this.complex = complex;
    }

    /**
     *取得商品单价
     * @return commUnitPrice 商品单价
     */
    public Double getCommUnitPrice() {
        return commUnitPrice;
    }

    /**
     * 设置商品单价
     * @param commUnitPrice 商品单价
     */
    public void setCommUnitPrice(Double commUnitPrice) {
        this.commUnitPrice = commUnitPrice;
    }

    /**
     * 取得与大类单位折算比，如果是空默认为1.0
     * @return unitConvert 与大类单位折算比
     */
    public Double getUnitConvert() {        
        return unitConvert;
    }
    /**
     * 设置与大类单位折算比
	 * @param unitConvert  与大类单位折算比
	 */
    public void setUnitConvert(Double unitConvert) {
        this.unitConvert = unitConvert;
    }
    
    
//    /**
//     * 求得对应比率
//     * 
//     * @return df 已对应报关数量/商品数量
//     */
//    public Double getCorrespondingRate() {
//        double alreadyCorrespondingAmount = 0.0;
//        if (this.alreadyCorrespondingAmount != null) {
//            alreadyCorrespondingAmount = this.alreadyCorrespondingAmount;
//        }
//        double unitConvert = this.getUnitConvert() ==null?1.0:this.getUnitConvert();
//        double commAmount = 0.0;
//        if (this.commAmount != null) {
//            commAmount = this.commAmount*unitConvert;
//        }
//        BigDecimal b = new BigDecimal(alreadyCorrespondingAmount / (commAmount));
//        double df = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//        return df;
//    }
    
    /**
     * 取得未对应报关单的数量
	 * @return commAmount - alreadyCorrespondingAmount 商品数量-已对应报关数量
	 */
    public Double getNoCorrespondingAmount3() {
        double alreadyCorrespondingAmount = 0.0;
        if (this.alreadyCorrespondingAmount != null) {
            alreadyCorrespondingAmount = this.alreadyCorrespondingAmount;
        }
        //
        // 当一个工厂物料对应多个报关单(报关单位与大类的折算比例)
        //
        double unitConvert = this.getUnitConvert() ==null?1.0:this.getUnitConvert();        
        double commAmount = 0.0;
        if (this.commAmount != null) {
            commAmount = this.commAmount*unitConvert;
        }
        return commAmount - alreadyCorrespondingAmount;
    }
    
    
    /**
     * 取得未对应报关单的数量
	 * @return commAmount - alreadyCorrespondingAmount 商品数量-已对应报关数量
	 */
	public Double getNoCorrespondingAmount() {
		if (this.commAmount != null && this.alreadyCorrespondingAmount != null) {
			return this.commAmount - this.alreadyCorrespondingAmount;
		} else if(this.commAmount != null && this.alreadyCorrespondingAmount == null){
			return this.commAmount;
		} else if(this.commAmount == null && this.alreadyCorrespondingAmount != null){
			return - alreadyCorrespondingAmount;
		} 
		return 0.0;
	}

	public Double getNoCorrespondingAmount2() {
		double alreadyCorrespondingAmount = 0.0;
		if (this.alreadyCorrespondingAmount != null) {
			alreadyCorrespondingAmount = this.alreadyCorrespondingAmount;
		}
		double commAmount = 0.0;
		if (this.commAmount != null) {
			commAmount = this.commAmount;
		}
		return commAmount - alreadyCorrespondingAmount;
	}
	
    
}
