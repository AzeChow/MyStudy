package com.bestway.bcs.contractcav.entity;

import java.io.Serializable;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 合同核销表 
 */
public class TempContractCheckCav implements Serializable{

	 private static final long serialVersionUID  = CommonUtils
	    .getSerialVersionUID();
    //*************************************进口*******************************
	//*****************报关单数
	/**
	 * 进口料件直接进口报关单数量
	 */
	private Integer impDirectCDCount = new Integer(0);
	
	/**
	 * 料件转厂报关单数量
	 */
	private Integer impTransferCDCount = new Integer(0);
	
	/**
	 * 成品退厂返工报关单数量
	 */
	private Integer impProcuderCDCount = new Integer(0);	
	
	/**
	 * 进口报关单总数=直接进口+转厂进口+退厂返工
	 */
	private Integer impTotalCDCount = new Integer(0);
	
	//*****************件数
	/**
	 * 料件直接进口-件数
	 */
	private Integer impDirectPieces = new Integer(0);
	
	/**
	 * 料件转厂--件数 
	 */
	private Integer impTransferPieces = new Integer(0);
	
	/**
	 * 成品退厂返工--件数 
	 */
	private Integer impProcuderPieces = new Integer(0);
	
	/**
	 * 进口件数总数=直接进口+转厂进口+退厂返工
	 */
	private Integer impTotalPieces = new Integer(0);
	
	//*****************毛重
	/**
	 * 料件直接进口-毛重
	 */
	private Double impDirectGrossWeight = new Double(0.0);
	
	/**
	 * 料件转厂--毛重
	 */
	private Double impTransferGrossWeight = new Double(0.0);
	
	/**
	 * 成品退厂返工--毛重
	 */
	private Double impProcuderGrossWeight = new Double(0.0);
	
	/**
	 * 进口件数毛重=直接进口+转厂进口+退厂返工
	 */
	private Double impTotalGrossWeight = new Double(0.0);
	
	//*****************净重
	/**
	 * 料件直接进口-净重
	 */
	private Double impDirectNetWeight = new Double(0.0);
	
	/**
	 * 料件转厂--净重
	 */
	private Double impTransferNetWeight = new Double(0.0);
	
	/**
	 * 成品退厂返工--净重
	 */
	private Double impProcuderNetWeight = new Double(0.0);
	
	/**
	 * 进口件数净重=直接进口+转厂进口+退厂返工
	 */
	private Double impTotalNetWeight = new Double(0.0);
	
	//*****************总值
	/**
	 * 料件直接进口-总值
	 */
	private Double impDirectTotalValue = new Double(0.0);
	
	/**
	 * 料件转厂--总值
	 */
	private Double impTransferTotalValue = new Double(0.0);
	
	/**
	 * 成品退厂返工--总值 
	 */
	private Double impProcuderTotalValue = new Double(0.0);
	
	/**
	 * 进口件数总值=直接进口+转厂进口+退厂返工
	 */
	private Double impTotalvalue = new Double(0.0);
	
	//*****************加工费
	/**
	 * 料件直接进口-加工费
	 */
	private Double impDirectProcessPrice = new Double(0.0);
	
	/**
	 * 料件转厂--加工费
	 */
	private Double impTransferProcessPrice = new Double(0.0);
	
	/**
	 * 成品退厂返工--加工费 
	 */
	private Double impProcuderProcessPrice = new Double(0.0);
	
	/**
	 * 进口件数加工费=直接进口+转厂进口+退厂返工
	 */
	private Double impTotalProcessPrice = new Double(0.0);
	
	//*************************************出口*******************************
	//*****************报关单数
	/**
	 * 出口成品直接出口报关单数量
	 */
	private Integer expDirectCDCount = new Integer(0);
	
	/**
	 * 成品转厂报关单数量
	 */
	private Integer expTransferCDCount = new Integer(0);
	
	/**
	 * 退料出口报关单数量
	 */
	private Integer expReturnMeCDCount = new Integer(0);
	
	/**
	 * 放弃料值报关单数量
	 */
	private Integer expQuitMeCDCount = new Integer(0);
	
	/**
	 * 出口报关单总数=直接出口+转厂出口+退料出口+放弃料值
	 */
	private Integer expTotalCDCount = new Integer(0);
	
	//*****************件数
	/**
	 * 成品直接出口-件数
	 */
	private Integer expDirectPieces = new Integer(0);
	
	/**
	 * 成品转厂出口--件数 
	 */
	private Integer expTransferPieces = new Integer(0);
	
	/**
	 * 退料出口--件数 
	 */
	private Integer expReturnMePieces = new Integer(0);
	
	/**
	 * 放弃料件-件数
	 */
	private Integer expQuitMePieces = new Integer(0);
	
	/**
	 * 出口总件数=直接出口+转厂出口+退料出口+放弃料值
	 */
	private Integer expTotalPieces = new Integer(0);
	
	//*****************毛重
	/**
	 * 成品直接出口-毛重
	 */
	private Double expDirectGrossWeight = new Double(0.0);
	
	/**
	 * 成品转厂出口--毛重
	 */
	private Double expTransferGrossWeight = new Double(0.0);
	
	/**
	 * 退料出口--毛重
	 */
	private Double expReturnMeGrossWeight = new Double(0.0);
	
	/**
	 * 放弃料件-毛重
	 */
	private Double expQuitGrossWeight = new Double(0.0);
	
	/**
	 * 出口总毛量=直接出口+转厂出口+退料出口+放弃料值
	 */
	private Double expTotalGrossWeight = new Double(0.0);
	
	//*****************净重
	/**
	 * 成品直接出口-净重
	 */
	private Double expDirectNetWeight = new Double(0.0);
	
	/**
	 * 成品转厂出口--净重
	 */
	private Double expTransferNetWeight = new Double(0.0);
	
	/**
	 * 退料出口--净重
	 */
	private Double expReturnMeNetWeight = new Double(0.0);
	
	/**
	 * 放弃料件-净重
	 */
	private Double expQuitNetWeight = new Double(0.0);
	
	/**
	 * 出口净重=直接出口+转厂出口+退料出口+放弃料值
	 */
	private Double expTotalNetWeight = new Double(0.0);
	
	//*****************总值
	/**
	 * 成品直接出口-总值
	 */
	private Double expDirectTotalValue = new Double(0.0);
	
	/**
	 * 成品转厂出口--总值
	 */
	private Double expTransferTotalValue = new Double(0.0);
	
	/**
	 * 退料出口--总值 
	 */
	private Double expReturnMeTotalValue = new Double(0.0);
	
	/**
	 * 放弃料件--总值
	 */
	private Double expQuitTotalvalue = new Double(0.0);
	
	/**
	 * 出口总值=直接出口+转厂出口+退料出口+放弃料值
	 */
	private Double expTotalvalue = new Double(0.0);
	
	//*****************加工费
	/**
	 * 成品直接出口-加工费
	 */
	private Double expDirectProcessPrice = new Double(0.0);
	
	/**
	 * 成品转厂出口--加工费
	 */
	private Double expTransferProcessPrice = new Double(0.0);
	
	/**
	 * 退料出口--加工费 
	 */
	private Double expReturnMeProcessPrice = new Double(0.0);
	
	/**
	 * 放弃料件--加工费
	 */
	private Double expQuitProcessPrice = new Double(0.0);
	
	//****************************************总计
	/**
	 * 出口加工费总值=直接出口+转厂出口+退料出口+放弃料值
	 */
	private Double expTotalProcessPrice = new Double(0.0);
	
	/**
	 * 国内购料总值
	 */
	private Double tatalValeGuoLei = new Double(0.0);
	/**
	 * 国内购料净重
	 */
	private Double netWeightGuoLei = new Double(0.0);
	/**
	 * 国内购料包装重
	 */
	private Double packWeightGuoLei = new Double(0.0);
	

	public Integer getImpDirectCDCount() {
		return impDirectCDCount;
	}

	public void setImpDirectCDCount(Integer impDirectCDCount) {
		this.impDirectCDCount = impDirectCDCount;
	}

	public Integer getImpTransferCDCount() {
		return impTransferCDCount;
	}

	public void setImpTransferCDCount(Integer impTransferCDCount) {
		this.impTransferCDCount = impTransferCDCount;
	}

	public Integer getImpProcuderCDCount() {
		return impProcuderCDCount;
	}

	public void setImpProcuderCDCount(Integer impProcuderCDCount) {
		this.impProcuderCDCount = impProcuderCDCount;
	}

	public Integer getImpTotalCDCount() {
		return impTotalCDCount;
	}

	public void setImpTotalCDCount(Integer impTotalCDCount) {
		this.impTotalCDCount = impTotalCDCount;
	}

	public Integer getImpDirectPieces() {
		return impDirectPieces;
	}

	public void setImpDirectPieces(Integer impDirectPieces) {
		this.impDirectPieces = impDirectPieces;
	}

	public Integer getImpTransferPieces() {
		return impTransferPieces;
	}

	public void setImpTransferPieces(Integer impTransferPieces) {
		this.impTransferPieces = impTransferPieces;
	}

	public Integer getImpProcuderPieces() {
		return impProcuderPieces;
	}

	public void setImpProcuderPieces(Integer impProcuderPieces) {
		this.impProcuderPieces = impProcuderPieces;
	}

	public Integer getImpTotalPieces() {
		return impTotalPieces;
	}

	public void setImpTotalPieces(Integer impTotalPieces) {
		this.impTotalPieces = impTotalPieces;
	}

	public Double getImpDirectGrossWeight() {
		return impDirectGrossWeight;
	}

	public void setImpDirectGrossWeight(Double impDirectGrossWeight) {
		this.impDirectGrossWeight = impDirectGrossWeight;
	}

	public Double getImpTransferGrossWeight() {
		return impTransferGrossWeight;
	}

	public void setImpTransferGrossWeight(Double impTransferGrossWeight) {
		this.impTransferGrossWeight = impTransferGrossWeight;
	}

	public Double getImpProcuderGrossWeight() {
		return impProcuderGrossWeight;
	}

	public void setImpProcuderGrossWeight(Double impProcuderGrossWeight) {
		this.impProcuderGrossWeight = impProcuderGrossWeight;
	}

	public Double getImpTotalGrossWeight() {
		return impTotalGrossWeight;
	}

	public void setImpTotalGrossWeight(Double impTotalGrossWeight) {
		this.impTotalGrossWeight = impTotalGrossWeight;
	}

	public Double getImpDirectNetWeight() {
		return impDirectNetWeight;
	}

	public void setImpDirectNetWeight(Double impDirectNetWeight) {
		this.impDirectNetWeight = impDirectNetWeight;
	}

	public Double getImpTransferNetWeight() {
		return impTransferNetWeight;
	}

	public void setImpTransferNetWeight(Double impTransferNetWeight) {
		this.impTransferNetWeight = impTransferNetWeight;
	}

	public Double getImpProcuderNetWeight() {
		return impProcuderNetWeight;
	}

	public void setImpProcuderNetWeight(Double impProcuderNetWeight) {
		this.impProcuderNetWeight = impProcuderNetWeight;
	}

	public Double getImpTotalNetWeight() {
		return impTotalNetWeight;
	}

	public void setImpTotalNetWeight(Double impTotalNetWeight) {
		this.impTotalNetWeight = impTotalNetWeight;
	}

	public Double getImpDirectTotalValue() {
		return impDirectTotalValue;
	}

	public void setImpDirectTotalValue(Double impDirectTotalValue) {
		this.impDirectTotalValue = impDirectTotalValue;
	}

	public Double getImpTransferTotalValue() {
		return impTransferTotalValue;
	}

	public void setImpTransferTotalValue(Double impTransferTotalValue) {
		this.impTransferTotalValue = impTransferTotalValue;
	}

	public Double getImpProcuderTotalValue() {
		return impProcuderTotalValue;
	}

	public void setImpProcuderTotalValue(Double impProcuderTotalValue) {
		this.impProcuderTotalValue = impProcuderTotalValue;
	}

	public Double getImpTotalvalue() {
		return impTotalvalue;
	}

	public void setImpTotalvalue(Double impTotalvalue) {
		this.impTotalvalue = impTotalvalue;
	}

	public Double getImpDirectProcessPrice() {
		return impDirectProcessPrice;
	}

	public void setImpDirectProcessPrice(Double impDirectProcessPrice) {
		this.impDirectProcessPrice = impDirectProcessPrice;
	}

	public Double getImpTransferProcessPrice() {
		return impTransferProcessPrice;
	}

	public void setImpTransferProcessPrice(Double impTransferProcessPrice) {
		this.impTransferProcessPrice = impTransferProcessPrice;
	}

	public Double getImpProcuderProcessPrice() {
		return impProcuderProcessPrice;
	}

	public void setImpProcuderProcessPrice(Double impProcuderProcessPrice) {
		this.impProcuderProcessPrice = impProcuderProcessPrice;
	}

	public Double getImpTotalProcessPrice() {
		return impTotalProcessPrice;
	}

	public void setImpTotalProcessPrice(Double impTotalProcessPrice) {
		this.impTotalProcessPrice = impTotalProcessPrice;
	}

	public Integer getExpDirectCDCount() {
		return expDirectCDCount;
	}

	public void setExpDirectCDCount(Integer expDirectCDCount) {
		this.expDirectCDCount = expDirectCDCount;
	}

	public Integer getExpTransferCDCount() {
		return expTransferCDCount;
	}

	public void setExpTransferCDCount(Integer expTransferCDCount) {
		this.expTransferCDCount = expTransferCDCount;
	}

	public Integer getExpReturnMeCDCount() {
		return expReturnMeCDCount;
	}

	public void setExpReturnMeCDCount(Integer expReturnMeCDCount) {
		this.expReturnMeCDCount = expReturnMeCDCount;
	}

	public Integer getExpQuitMeCDCount() {
		return expQuitMeCDCount;
	}

	public void setExpQuitMeCDCount(Integer expQuitMeCDCount) {
		this.expQuitMeCDCount = expQuitMeCDCount;
	}

	public Integer getExpTotalCDCount() {
		return expTotalCDCount;
	}

	public void setExpTotalCDCount(Integer expTotalCDCount) {
		this.expTotalCDCount = expTotalCDCount;
	}

	public Integer getExpDirectPieces() {
		return expDirectPieces;
	}

	public void setExpDirectPieces(Integer expDirectPieces) {
		this.expDirectPieces = expDirectPieces;
	}

	public Integer getExpTransferPieces() {
		return expTransferPieces;
	}

	public void setExpTransferPieces(Integer expTransferPieces) {
		this.expTransferPieces = expTransferPieces;
	}

	public Integer getExpReturnMePieces() {
		return expReturnMePieces;
	}

	public void setExpReturnMePieces(Integer expReturnMePieces) {
		this.expReturnMePieces = expReturnMePieces;
	}

	public Integer getExpQuitMePieces() {
		return expQuitMePieces;
	}

	public void setExpQuitMePieces(Integer expQuitMePieces) {
		this.expQuitMePieces = expQuitMePieces;
	}

	public Integer getExpTotalPieces() {
		return expTotalPieces;
	}

	public void setExpTotalPieces(Integer expTotalPieces) {
		this.expTotalPieces = expTotalPieces;
	}

	public Double getExpDirectGrossWeight() {
		return expDirectGrossWeight;
	}

	public void setExpDirectGrossWeight(Double expDirectGrossWeight) {
		this.expDirectGrossWeight = expDirectGrossWeight;
	}

	public Double getExpTransferGrossWeight() {
		return expTransferGrossWeight;
	}

	public void setExpTransferGrossWeight(Double expTransferGrossWeight) {
		this.expTransferGrossWeight = expTransferGrossWeight;
	}

	public Double getExpReturnMeGrossWeight() {
		return expReturnMeGrossWeight;
	}

	public void setExpReturnMeGrossWeight(Double expReturnMeGrossWeight) {
		this.expReturnMeGrossWeight = expReturnMeGrossWeight;
	}

	public Double getExpQuitGrossWeight() {
		return expQuitGrossWeight;
	}

	public void setExpQuitGrossWeight(Double expQuitGrossWeight) {
		this.expQuitGrossWeight = expQuitGrossWeight;
	}

	public Double getExpTotalGrossWeight() {
		return expTotalGrossWeight;
	}

	public void setExpTotalGrossWeight(Double expTotalGrossWeight) {
		this.expTotalGrossWeight = expTotalGrossWeight;
	}

	public Double getExpDirectNetWeight() {
		return expDirectNetWeight;
	}

	public void setExpDirectNetWeight(Double expDirectNetWeight) {
		this.expDirectNetWeight = expDirectNetWeight;
	}

	public Double getExpTransferNetWeight() {
		return expTransferNetWeight;
	}

	public void setExpTransferNetWeight(Double expTransferNetWeight) {
		this.expTransferNetWeight = expTransferNetWeight;
	}

	public Double getExpReturnMeNetWeight() {
		return expReturnMeNetWeight;
	}

	public void setExpReturnMeNetWeight(Double expReturnMeNetWeight) {
		this.expReturnMeNetWeight = expReturnMeNetWeight;
	}

	public Double getExpQuitNetWeight() {
		return expQuitNetWeight;
	}

	public void setExpQuitNetWeight(Double expQuitNetWeight) {
		this.expQuitNetWeight = expQuitNetWeight;
	}

	public Double getExpTotalNetWeight() {
		return expTotalNetWeight;
	}

	public void setExpTotalNetWeight(Double expTotalNetWeight) {
		this.expTotalNetWeight = expTotalNetWeight;
	}

	public Double getExpDirectTotalValue() {
		return expDirectTotalValue;
	}

	public void setExpDirectTotalValue(Double expDirectTotalValue) {
		this.expDirectTotalValue = expDirectTotalValue;
	}

	public Double getExpTransferTotalValue() {
		return expTransferTotalValue;
	}

	public void setExpTransferTotalValue(Double expTransferTotalValue) {
		this.expTransferTotalValue = expTransferTotalValue;
	}

	public Double getExpReturnMeTotalValue() {
		return expReturnMeTotalValue;
	}

	public void setExpReturnMeTotalValue(Double expReturnMeTotalValue) {
		this.expReturnMeTotalValue = expReturnMeTotalValue;
	}

	public Double getExpQuitTotalvalue() {
		return expQuitTotalvalue;
	}

	public void setExpQuitTotalvalue(Double expQuitTotalvalue) {
		this.expQuitTotalvalue = expQuitTotalvalue;
	}

	public Double getExpTotalvalue() {
		return expTotalvalue;
	}

	public void setExpTotalvalue(Double expTotalvalue) {
		this.expTotalvalue = expTotalvalue;
	}

	public Double getExpDirectProcessPrice() {
		return expDirectProcessPrice;
	}

	public void setExpDirectProcessPrice(Double expDirectProcessPrice) {
		this.expDirectProcessPrice = expDirectProcessPrice;
	}

	public Double getExpTransferProcessPrice() {
		return expTransferProcessPrice;
	}

	public void setExpTransferProcessPrice(Double expTransferProcessPrice) {
		this.expTransferProcessPrice = expTransferProcessPrice;
	}

	public Double getExpReturnMeProcessPrice() {
		return expReturnMeProcessPrice;
	}

	public void setExpReturnMeProcessPrice(Double expReturnMeProcessPrice) {
		this.expReturnMeProcessPrice = expReturnMeProcessPrice;
	}

	public Double getExpTotalProcessPrice() {
		return expTotalProcessPrice;
	}

	public void setExpTotalProcessPrice(Double expTotalProcessPrice) {
		this.expTotalProcessPrice = expTotalProcessPrice;
	}

	public Double getExpQuitProcessPrice() {
		return expQuitProcessPrice;
	}

	public void setExpQuitProcessPrice(Double expQuitProcessPrice) {
		this.expQuitProcessPrice = expQuitProcessPrice;
	}

	public Double getTatalValeGuoLei() {
		return tatalValeGuoLei;
	}

	public void setTatalValeGuoLei(Double tatalValeGuoLei) {
		this.tatalValeGuoLei = tatalValeGuoLei;
	}

	public Double getNetWeightGuoLei() {
		return netWeightGuoLei;
	}

	public void setNetWeightGuoLei(Double netWeightGuoLei) {
		this.netWeightGuoLei = netWeightGuoLei;
	}

	public Double getPackWeightGuoLei() {
		return packWeightGuoLei;
	}

	public void setPackWeightGuoLei(Double packWeightGuoLei) {
		this.packWeightGuoLei = packWeightGuoLei;
	}
	
	
}
