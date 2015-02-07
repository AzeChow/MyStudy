/*
 * Created on 2005-5-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractstat.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

/**
 * 存放统计报表中的进口料件报关情况表资料-(有包括进出口总值报表【外经办用】)
 * 2011-03-03 check by hcl
 */
public class ImpMaterialStat implements Serializable, Comparable {

	/**
	 * 余料进口
	 */
	private Double remainImport;

	/**
	 * 余料转出
	 */
	private Double remainForward;
	/**
	 * 总边角料
	 */
	private Double allTotalleftovermaterial;
	/**
	 * 预计边角料征税量
	 */
	private Double estimateOvermaterial;
	
	/**
	 * 边角料内销
	 */
	private Double leftovermaterial;
	/**
	 * 边角料复出
	 */
	private Double leftovermaterialExport;
	/**
	 * 边角料余量
	 */
	private Double leftovermaterialremain;
	/**
	 * 料件退换出口数金额
	 */
	private Double fuchuExport;
	/**
	 * 内销数量
	 */
	private Double internalAmount;

	/**
	 * 料件退换进口数
	 */
	private Double exchangeImport;

	/**
	 * 料件退换出口数
	 */
	private Double exchangeExport;

	/**
	 * 成品使用金额
	 */
	private Double productUseMoney;

	/**
	 * 序号
	 */
	private String serialNo;

	/**
	 * 关封余量
	 */
	private Double customsEnvelopRemain;
	
	/**
	 * 可签关封
	 */
	private Double commodityInfoRemain;

	/**
	 * 可直接进口量
	 */
	private Double canDirectImportAmount;

	/**
	 * 总进口量
	 */
	private Double allImpTotalAmont;

	/**
	 * 商品编码
	 */
	private Complex complex;
	


	/**
	 * 品名
	 */
	private String commName;

	/**
	 * 规格
	 */
	private String commSpec;

	/**
	 * 单位
	 */
	private Unit unit;

	/**
	 * 单价
	 */
	private Double unitPrice;

	/**
	 * 合同定量
	 */
	private Double contractAmount;

	/**
	 * 本期总进口量
	 */
	private Double impTotalAmont;

	/**
	 * 报关单进口量
	 */
	private Double impCDAmount;

	/**
	 * 料件进口量
	 */
	private Double directImport;

	/**
	 * 转厂进口量
	 */
	private Double transferFactoryImport;

	/**
	 * 退料出口量
	 */
	private Double backMaterialExport;

	/**
	 * 退料复出量
	 */
	private Double backMaterialReturn;

	/**
	 * 退料退换量
	 */
	private Double backMaterialExchange;

	/**
	 * 本期成品使用量
	 */
	private Double productUse;

	/**
	 * 余量
	 */
	private Double remainAmount;

	/**
	 * 缺量
	 */
	private Double ullage;

	/**
	 * 库存
	 */
	private Double stockAmount;

	/**
	 * 可进口量
	 */
	private Double canImportAmount;

	/**
	 * 比例
	 */
	private Double scale;

	/**
	 * 是主料还是辅料
	 */
	private String materialType;

	/**
	 * 原产国
	 */
	private Country country;

	/**
	 * 余料金额
	 */
	private Double remainMoney;

	/**
	 * 手册号,对应列名contract.getEmsNo()
	 */
	private String emsNo = null;
	
	/**
	 * 合同号,对应contract.getImpContractNo()
	 */
	private String impContractNo;

	/**
	 * 未转报关单数量
	 */
	private Double noTranCustomsNum;

	/**
	 * 凭证序号
	 */
	private Integer credenceNo;
	/**
	 * 发票数量
	 */
	private Double invoiceNum = null;
	/**
	 * 本期进口总金额
	 */
	private Double impTotalMoney = null;
	
	/**
	 * 完成比例
	 */
    private Double completeScale = null;
    
    /**
     * 归并序号
     */
    private Integer innerMergeSeqNum=null;
    
    private String note =null;
    
    /**
     * 直接出口总值[寮步外经办用]
     */
    private Double OutdirectMoneywj = null;
    
    /**
     * 黄埔关区外 直接出口总值[寮步外经办用]
     */
    private Double OutdirectMoneywjOut = null;
    
    /**
     * 黄埔关区内 直接出口总值[寮步外经办用]
     */
    private Double OutdirectMoneywjIn = null;
    
    /**
     * 直接进口总值[寮步外经办用]
     */
    private Double ImpdirectMoneywj = null;
    
    /**
     * 黄埔关区外直接进口
     */
    private Double ImpdirectMoneywjOut = null;
    
    /**
     * 黄埔关区内直接进口
     */
    private Double ImpdirectMoneywjIn = null;
    /**
     * 结转进口关区外或保税仓值[寮步外经办用]
     */
    private Double ImptransEMoneywj = null;
    /**
     * 结转进口关区内或保税仓值[寮步外经办用]
     */
    private Double ImptransIMoneywj = null;
    /**
     * 结转出口关区外或保税仓值[寮步外经办用]
     */
    private Double OuttransEMoneywj = null;
    /**
     * 结转出口关区内或保税仓值[寮步外经办用]
     */
    private Double OuttransIMoneywj = null;
    /**
     * 月份
     */
    private String Month = null;
    
	/**
     * 填表人
     */
    private String FillName = null;
    /**
     * 填表人电话
     */
    private String FillTel = null;
    /**
     * 填表人Email
     */
    private String FillEmail =null;
    /**
     * 联系人
     */
    private String LinkName = null;
    /**
     * 联系人人电话
     */
    private String LinkTel = null;
    /**
     * 联系人Email
     */
    private String LinkEmail =null;
    
    /**
     * 获取完成比例
     */
	public Double getCompleteScale() {
		return allImpTotalAmont*100 / contractAmount ;
	}
	/**
	 * 获取本期进口总金额
	 * 
	 * @return impTotalMoney
	 */
	public Double getImpTotalMoney() {
		return impTotalMoney;
	}
	/**
	 * 设置本期进口总金额
	 * 
	 * @param impTotalMoney
	 *            本期进口总金额
	 */
	public void setImpTotalMoney(Double impTotalMoney) {
		this.impTotalMoney = impTotalMoney;
	}

	/**
	 * 获取退料退换量
	 * 
	 * @return backMaterialExchange 退料退换量
	 */
	public Double getBackMaterialExchange() {
		return backMaterialExchange;
	}

	/**
	 * 设置退料退换量
	 * 
	 * @param backMaterialExchange
	 *            退料退换量
	 */
	public void setBackMaterialExchange(Double backMaterialExchange) {
		this.backMaterialExchange = backMaterialExchange;
	}

	/**
	 * 获取退料出口量
	 * 
	 * @return backMaterialExport 退料出口量
	 */
	public Double getBackMaterialExport() {
		return backMaterialExport;
	}

	/**
	 * 设置退料出口量
	 * 
	 * @param backMaterialExport
	 *            退料出口量
	 */
	public void setBackMaterialExport(Double backMaterialExport) {
		this.backMaterialExport = backMaterialExport;
	}

	/**
	 * 获取退料复出量
	 * 
	 * @return backMaterialReturn 退料复出量
	 */
	public Double getBackMaterialReturn() {
		return backMaterialReturn;
	}

	/**
	 * 设置退料复出量
	 * 
	 * @param backMaterialReturn
	 *            退料复出量
	 */
	public void setBackMaterialReturn(Double backMaterialReturn) {
		this.backMaterialReturn = backMaterialReturn;
	}

	/**
	 * 获取可进口量
	 * 
	 * @return canImportAmount 可进口量
	 */
	public Double getCanImportAmount() {
		return canImportAmount;
	}

	/**
	 * 设置可进口量
	 * 
	 * @param canImportAmount
	 *            可进口量
	 */
	public void setCanImportAmount(Double canImportAmount) {
		this.canImportAmount = canImportAmount;
	}

	/**
	 * 获取品名
	 * 
	 * @return commName 品名
	 */
	public String getCommName() {
		return commName;
	}

	/**
	 * 设置品名
	 * 
	 * @param commName
	 *            品名
	 */
	public void setCommName(String commName) {
		this.commName = commName;
	}

	/**
	 * 获取规格
	 * 
	 * @return commSpec 规格
	 */
	public String getCommSpec() {
		return commSpec;
	}

	/**
	 * 设置规格
	 * 
	 * @param commSpec
	 *            规格
	 */
	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}

	/**
	 * 获取合同定量
	 * 
	 * @return contractAmount 合同定量
	 */
	public Double getContractAmount() {
		return contractAmount;
	}

	/**
	 * 设置合同定量
	 * 
	 * @param contractAmount
	 *            合同定量
	 */
	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}

	/**
	 * 获取原产国
	 * 
	 * @return country 原产国
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * 设置原产国
	 * 
	 * @param country
	 *            原产国
	 */
	public void setCountry(Country country) {
		this.country = country;
	}
	
   /**
    * 获取料件退换出口数金额
    * @return
    */
	public Double getFuchuExport() {
		return fuchuExport;
	}
	
	/**
	 * 设置料件退换出口数金额
	 * @param fuchuExport
	 */
	public void setFuchuExport(Double fuchuExport) {
		this.fuchuExport = fuchuExport;
	}
	/**
	 * 获取料件进口量
	 * 
	 * @return directImport 料件进口量
	 */
	public Double getDirectImport() {
		return directImport;
	}

	/**
	 * 设置料件进口量
	 * 
	 * @param directImport
	 *            料件进口量
	 */
	public void setDirectImport(Double directImport) {
		this.directImport = directImport;
	}

	/**
	 * 获取报关单进口量
	 * 
	 * @return impCDAmount 报关单进口量
	 */
	public Double getImpCDAmount() {
		return impCDAmount;
	}

	/**
	 * 设置报关单进口量
	 * 
	 * @param impCDAmount
	 *            报关单进口量
	 */
	public void setImpCDAmount(Double impCDAmount) {
		this.impCDAmount = impCDAmount;
	}

	/**
	 * 获取本期总进口量
	 * 
	 * @return impTotalAmont 总进口量
	 */
	public Double getImpTotalAmont() {
		return impTotalAmont;
	}

	/**
	 * 设置本期总进口量
	 * 
	 * @param impTotalAmont
	 *            总进口量
	 */
	public void setImpTotalAmont(Double impTotalAmont) {
		this.impTotalAmont = impTotalAmont;
	}

	/**
	 * 获取是主料还是辅料
	 * 
	 * @return materialType 是主料还是辅料
	 */
	public String getMaterialType() {
		return materialType;
	}

	/**
	 * 设置是主料还是辅料
	 * 
	 * @param materialType
	 *            是主料还是辅料
	 */
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	/**
	 * 获取成品使用量
	 * 
	 * @return productUse 成品使用量
	 */
	public Double getProductUse() {
		return productUse;
	}

	/**
	 * 设置成品使用量
	 * 
	 * @param productUse
	 *            成品使用量
	 */
	public void setProductUse(Double productUse) {
		this.productUse = productUse;
	}

	/**
	 * 获取余量
	 * 
	 * @return remainAmount 余量
	 */
	public Double getRemainAmount() {
		return remainAmount;
	}

	/**
	 * 设置余量
	 * 
	 * @param remainAmount
	 *            余量
	 */
	public void setRemainAmount(Double remainAmount) {
		this.remainAmount = remainAmount;
	}

	/**
	 * 获取余料金额
	 * 
	 * @return remainMoney 余料金额
	 */
	public Double getRemainMoney() {
		return remainMoney;
	}

	/**
	 * 设置余料金额
	 * 
	 * @param remainMoney
	 *            余料金额
	 */
	public void setRemainMoney(Double remainMoney) {
		this.remainMoney = remainMoney;
	}

	/**
	 * 获取比例
	 * 
	 * @return scale 比例
	 */
	public Double getScale() {
		return scale;
	}

	/**
	 * 设置比例
	 * 
	 * @param scale
	 *            比例
	 */
	public void setScale(Double scale) {
		this.scale = scale;
	}

	/**
	 * 获取转厂进口量
	 * 
	 * @return transferFactoryImport 转厂进口量
	 */
	public Double getTransferFactoryImport() {
		return transferFactoryImport;
	}

	/**
	 * 设置转厂进口量
	 * 
	 * @param transferFactoryImport
	 *            转厂进口量
	 */
	public void setTransferFactoryImport(Double transferFactoryImport) {
		this.transferFactoryImport = transferFactoryImport;
	}

	/**
	 * 获取缺量
	 * 
	 * @return ullage 缺量
	 */
	public Double getUllage() {
		return ullage;
	}

	/**
	 * 设置缺量
	 * 
	 * @param ullage
	 *            缺量
	 */
	public void setUllage(Double ullage) {
		this.ullage = ullage;
	}

	/**
	 * 获取单位
	 * 
	 * @return unit 单位
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * 设置单位
	 * 
	 * @param unit
	 *            单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * 获取单价
	 * 
	 * @return unitPrice 单价
	 */
	public Double getUnitPrice() {
		return unitPrice;
	}

	/**
	 * 设置单价
	 * 
	 * @param unitPrice
	 *            单价
	 */
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * 获取商品编码
	 * 
	 * @return complex 商品编码
	 */
	public Complex getComplex() {
		return complex;
	}

	/**
	 * 设置商品编码
	 * 
	 * @param complex
	 *            商品编码
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	/**
	 * 获取可直接进口量
	 * 
	 * @return canDirectImportAmount 可直接进口量
	 */
	public Double getCanDirectImportAmount() {
		return canDirectImportAmount;
	}

	/**
	 * 设置可直接进口量
	 * 
	 * @param canDirectImportAmount
	 *            可直接进口量
	 */
	public void setCanDirectImportAmount(Double canDirectImportAmount) {
		this.canDirectImportAmount = canDirectImportAmount;
	}

	/**
	 * 获取关封余量
	 * 
	 * @return customsEnvelopRemain 关封余量
	 */
	public Double getCustomsEnvelopRemain() {
		return customsEnvelopRemain;
	}

	/**
	 * 设置关封余量
	 * 
	 * @param customsEnvelopRemain
	 *            关封余量
	 */
	public void setCustomsEnvelopRemain(Double customsEnvelopRemain) {
		this.customsEnvelopRemain = customsEnvelopRemain;
	}

	/**
	 * 获取料件退换出口数
	 * 
	 * @return exchangeExport 料件退换出口数
	 */
	public Double getExchangeExport() {
		return exchangeExport;
	}

	/**
	 * 设置料件退换出口数
	 * 
	 * @param exchangeExport
	 *            料件退换出口数
	 */
	public void setExchangeExport(Double exchangeExport) {
		this.exchangeExport = exchangeExport;
	}

	/**
	 * 获取料件退换进口数
	 * 
	 * @return exchangeImport 料件退换进口数
	 */
	public Double getExchangeImport() {
		return exchangeImport;
	}

	/**
	 * 设置料件退换进口数
	 * 
	 * @param exchangeImport
	 *            料件退换进口数
	 */
	public void setExchangeImport(Double exchangeImport) {
		this.exchangeImport = exchangeImport;
	}

	/**
	 * 获取内销数量
	 * 
	 * @return internalAmount 内销数量
	 */
	public Double getInternalAmount() {
		return internalAmount;
	}

	/**
	 * 设置内销数量
	 * 
	 * @param internalAmount
	 *            内销数量
	 */
	public void setInternalAmount(Double internalAmount) {
		this.internalAmount = internalAmount;
	}

	/**
	 * 获取成品使用金额
	 * 
	 * @return productUseMoney 成品使用金额
	 */
	public Double getProductUseMoney() {
		return productUseMoney;
	}

	/**
	 * 设置成品使用金额
	 * 
	 * @param productUseMoney
	 *            成品使用金额
	 */
	public void setProductUseMoney(Double productUseMoney) {
		this.productUseMoney = productUseMoney;
	}

	/**
	 * 获取余料转出
	 * 
	 * @return remainForward 余料转出
	 */
	public Double getRemainForward() {
		return remainForward;
	}

	/**
	 * 设置余料转出
	 * 
	 * @param remainForward
	 *            余料转出
	 */
	public void setRemainForward(Double remainForward) {
		this.remainForward = remainForward;
	}

	/**
	 * 获取余料进口
	 * 
	 * @return remainImport 余料进口
	 */
	public Double getRemainImport() {
		return remainImport;
	}

	/**
	 * 设置余料进口
	 * 
	 * @param remainImport
	 *            余料进口
	 */
	public void setRemainImport(Double remainImport) {
		this.remainImport = remainImport;
	}

	/**
	 * 获取序号
	 * 
	 * @return serialNo 序号
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * 设置序号
	 * 
	 * @param serialNo
	 *            序号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 获取总进口量
	 * 
	 * @return allImpTotalAmont 总进口量
	 */
	public Double getAllImpTotalAmont() {
		return allImpTotalAmont;
	}

	/**
	 * 设置总进口量
	 * 
	 * @param allImpTotalAmont
	 *            总进口量
	 */
	public void setAllImpTotalAmont(Double allImpTotalAmont) {
		this.allImpTotalAmont = allImpTotalAmont;
	}

	/**
	 * 重compareTo方法，用serialNo（序号）进行比较
	 * 
	 * @param o
	 *            要比较的
	 * @return int 比o小时返回-1，相等时返回0，大于时返回1
	 */
	public int compareTo(Object o) {
		if (o == null) {
			return 1;
		}
		ImpMaterialStat stat = (ImpMaterialStat) o;
		if (this.getSerialNo() == null && stat.getSerialNo() == null) {
			return 0;
		}
		if (this.getSerialNo() == null && stat.getSerialNo() != null) {
			return -1;
		}
		if (this.getSerialNo() != null && stat.getSerialNo() == null) {
			return 1;
		}
		int serialNo1 = Integer.valueOf(this.getSerialNo());
		int serialNo2 = Integer.valueOf(stat.getSerialNo());
		if ((serialNo1 - serialNo2) > 0) {
			return 1;
		} else if ((serialNo1 - serialNo2) == 0) {
			return 0;
		} else if ((serialNo1 - serialNo2) < 0) {
			return -1;
		}
		return 0;
	}
	/**
	 * 获取库存
	 */
	public Double getStockAmount() {
		return stockAmount;
	}
	/**
	 * 设置库存
	 */
	public void setStockAmount(Double stockAmount) {
		this.stockAmount = stockAmount;
	}
	/**
	 * 获取手册号
	 */
	public String getEmsNo() {
		return emsNo;
	}
	/**
	 * 设置手册号
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	/**
	 * 获取未转报关单数量
	 */
	public Double getNoTranCustomsNum() {
		return noTranCustomsNum;
	}
	/**
	 * 设置未转报关单数量
	 */
	public void setNoTranCustomsNum(Double noTranCustomsNum) {
		this.noTranCustomsNum = noTranCustomsNum;
	}
	/**
	 * 获取凭证序号
	 */
	public Integer getCredenceNo() {
		return credenceNo;
	}
	/**
	 * 设置凭证序号
	 */
	public void setCredenceNo(Integer credenceNo) {
		this.credenceNo = credenceNo;
	}
	/**
	 * 获取发票数量
	 */
	public Double getInvoiceNum() {
		return invoiceNum;
	}
	/**
	 * 设置发票数量
	 */
	public void setInvoiceNum(Double invoiceNum) {
		this.invoiceNum = invoiceNum;
	}
	/**
	 * 获取边角料内销
	 */
	public Double getLeftovermaterial() {
		return leftovermaterial;
	}
	/**
	 * 设置边角料内销
	 */
	public void setLeftovermaterial(Double leftovermaterial) {
		this.leftovermaterial = leftovermaterial;
	}
	/**
	 * 获取边角料复出
	 */
	public Double getLeftovermaterialExport() {
		return leftovermaterialExport;
	}
	/**
	 * 设置边角料复出
	 */
	public void setLeftovermaterialExport(Double leftovermaterialExport) {
		this.leftovermaterialExport = leftovermaterialExport;
	}
	/**
	 * 获取边角料余量
	 */
	public Double getLeftovermaterialremain() {
		return leftovermaterialremain;
	}
	/**
	 * 设置边角料余量
	 */
	public void setLeftovermaterialremain(Double leftovermaterialremain) {
		this.leftovermaterialremain = leftovermaterialremain;
	}
	/**
	 * 获取边角料总量
	 */
	public Double getAllTotalleftovermaterial() {
		return allTotalleftovermaterial;
	}
	/**
	 * 设置边角料总量
	 */
	public void setAllTotalleftovermaterial(Double allTotalleftovermaterial) {
		this.allTotalleftovermaterial = allTotalleftovermaterial;
	}
	/**
	 * 获取合同号
	 */
	public String getImpContractNo() {
		return impContractNo;
	}
	/**
	 * 设置合同号
	 */
	public void setImpContractNo(String impContractNo) {
		this.impContractNo = impContractNo;
	}
	/**
	 * 获取可签关封
	 */
	public Double getCommodityInfoRemain() {
		return commodityInfoRemain;
	}
	/**
	 * 设置可签关封
	 */
	public void setCommodityInfoRemain(Double commodityInfoRemain) {
		this.commodityInfoRemain = commodityInfoRemain;
	}
	/**
	 * 获取归并序号
	 */
	public Integer getInnerMergeSeqNum() {
		return innerMergeSeqNum;
	}
	/**
	 * 设置归并序号
	 */
	public void setInnerMergeSeqNum(Integer innerMergeSeqNum) {
		this.innerMergeSeqNum = innerMergeSeqNum;
	}
	/**
	 * 取得备注内容
	 * @return 备注
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置备注内容
	 * @param note 备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 *  直接出口总值[外经办用]
	 * @return
	 */
	public Double getOutdirectMoneywj() {
		return OutdirectMoneywj;
	}
	public void setOutdirectMoneywj(Double OutdirectMoneywj) {
		this.OutdirectMoneywj = OutdirectMoneywj;
	}
	/**
	 *  直接进口总值[外经办用]
	 * @return
	 */
	public Double getImpdirectMoneywj() {
		return ImpdirectMoneywj;
	}
	/**
	 * 直接进口总值[外经办用]
	 * @param impdirectMoneywj
	 */
	public void setImpdirectMoneywj(Double impdirectMoneywj) {
		ImpdirectMoneywj = impdirectMoneywj;
	}
	/**
	 * 结转进口关区外或保税仓值[寮步外经办用]
	 * @return
	 */
	public Double getImptransEMoneywj() {
		return ImptransEMoneywj;
	}
	/**
	 * 结转进口关区外或保税仓值[寮步外经办用]
	 * @param imptransEMoneywj
	 */
	public void setImptransEMoneywj(Double imptransEMoneywj) {
		ImptransEMoneywj = imptransEMoneywj;
	}
	/**
	 * 结转进口关区内或保税仓值[寮步外经办用]
	 * @return
	 */
	public Double getImptransIMoneywj() {
		return ImptransIMoneywj;
	}
	/**
	 * 结转进口关区内或保税仓值[寮步外经办用]
	 * @param imptransIMoneywj
	 */
	public void setImptransIMoneywj(Double imptransIMoneywj) {
		ImptransIMoneywj = imptransIMoneywj;
	}
	/**
	 * 结转出口关区外或保税仓值[寮步外经办用]
	 * @return
	 */
	public Double getOuttransEMoneywj() {
		return OuttransEMoneywj;
	}
	/**
	 * 结转出口关区外或保税仓值[寮步外经办用]
	 * @param outtransEMoneywj
	 */
	public void setOuttransEMoneywj(Double outtransEMoneywj) {
		OuttransEMoneywj = outtransEMoneywj;
	}
	/**
	 * 结转出口关区内或保税仓值[寮步外经办用]
	 * @return
	 */
	public Double getOuttransIMoneywj() {
		return OuttransIMoneywj;
	}
	/**
	 * 结转出口关区内或保税仓值[寮步外经办用]
	 * @param outtransIMoneywj
	 */
	public void setOuttransIMoneywj(Double outtransIMoneywj) {
		OuttransIMoneywj = outtransIMoneywj;
	}
	public String getFillName() {
		return FillName;
	}
	public void setFillName(String fillName) {
		FillName = fillName;
	}
	public String getFillTel() {
		return FillTel;
	}
	public void setFillTel(String fillTel) {
		FillTel = fillTel;
	}
	public String getFillEmail() {
		return FillEmail;
	}
	public void setFillEmail(String fillEmail) {
		FillEmail = fillEmail;
	}
	public String getLinkName() {
		return LinkName;
	}
	public void setLinkName(String linkName) {
		LinkName = linkName;
	}
	public String getLinkTel() {
		return LinkTel;
	}
	public void setLinkTel(String linkTel) {
		LinkTel = linkTel;
	}
	public String getLinkEmail() {
		return LinkEmail;
	}
	public void setLinkEmail(String linkEmail) {
		LinkEmail = linkEmail;
	}
	public String getMonth() {
		return Month;
	}
	public void setMonth(String month) {
		Month = month;
	}
	/**
	 * 预计边角料征税量
	 * @return
	 */
	public Double getEstimateOvermaterial() {
		return estimateOvermaterial;
	}
	/**
	 * 预计边角料征税量
	 */
	public void setEstimateOvermaterial(Double estimateOvermaterial) {
		this.estimateOvermaterial = estimateOvermaterial;
	}
	
	 /**
     * 黄埔关区内直接进口
     */
	public Double getImpdirectMoneywjIn() {
		return ImpdirectMoneywjIn;
	}
	
	 /**
     * 黄埔关区内直接进口
     */
	public void setImpdirectMoneywjIn(Double impdirectMoneywjIn) {
		ImpdirectMoneywjIn = impdirectMoneywjIn;
	}
	
	 /**
     * 黄埔关区外直接进口
     */
	public Double getImpdirectMoneywjOut() {
		return ImpdirectMoneywjOut;
	}
	
	 /**
     * 黄埔关区外直接进口
     */
	public void setImpdirectMoneywjOut(Double impdirectMoneywjOut) {
		ImpdirectMoneywjOut = impdirectMoneywjOut;
	}
	
	/**
     * 黄埔关区内 直接出口总值[寮步外经办用]
     */
	public Double getOutdirectMoneywjIn() {
		return OutdirectMoneywjIn;
	}
	
	/**
     * 黄埔关区内 直接出口总值[寮步外经办用]
     */
	public void setOutdirectMoneywjIn(Double outdirectMoneywjIn) {
		OutdirectMoneywjIn = outdirectMoneywjIn;
	}
	
	/**
     * 黄埔关区外 直接出口总值[寮步外经办用]
     */
	public Double getOutdirectMoneywjOut() {
		return OutdirectMoneywjOut;
	}
	
	/**
     * 黄埔关区外 直接出口总值[寮步外经办用]
     */
	public void setOutdirectMoneywjOut(Double outdirectMoneywjOut) {
		OutdirectMoneywjOut = outdirectMoneywjOut;
	}
	
}  
