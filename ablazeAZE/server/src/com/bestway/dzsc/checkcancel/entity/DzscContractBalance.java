package com.bestway.dzsc.checkcancel.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DzscContractBalance implements Serializable {

	private Integer commSerialNo;


	/**
	 * 商品编码
	 */
	private String complexCode;
	/**
	 * 料件名称
	 */
	private String commName;
	/**
	 * 料件规格
	 */
	private String commSpec;
	/**
	 * 料件名称规格
	 */
	private String commNameSpec;
	/**
	 * 单位
	 */
	private String unitName;
	/**
	 * 单价
	 */
	private Double commUnitPrice=0.0;
	/**
	 * 合同余量
	 */
	Map<String,Double> contract;
	/**
	 * 合同总本数、用于报表动态列生成
	 */
	List<String> contractEmsNo;
	/**
	 * 合同余量、测试用属性
	 */
	List<Double> contractTotle;
	
	public String getCommNameSpec() {
		return commNameSpec;
	}

	public void setCommNameSpec(String commNameSpec) {
		this.commNameSpec = commNameSpec;
	}

	public List<Double> getContractTotle() {
		return contractTotle;
	}

	public void setContractTotle(List<Double> contractTotle) {
		this.contractTotle = contractTotle;
	}

	public List<String> getContractEmsNo() {
		return contractEmsNo;
	}

	public void setContractEmsNo(List<String> contractEmsNo) {
		this.contractEmsNo = contractEmsNo;
	}

	private Double total=0.0;

	public String getComplexCode() {
		return complexCode;
	}

	public void setComplexCode(String complexCode) {
		this.complexCode = complexCode;
	}

	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	public String getCommSpec() {
		return commSpec;
	}

	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Double getCommUnitPrice() {
		return commUnitPrice;
	}

	public void setCommUnitPrice(Double commUnitPrice) {
		this.commUnitPrice = commUnitPrice;
	}

	public Map<String,Double> getContract() {
		return contract;
	}

	public void setContract(Map<String,Double> contract) {
		this.contract = contract;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getCommSerialNo() {
		return commSerialNo;
	}

	public void setCommSerialNo(Integer commSerialNo) {
		this.commSerialNo = commSerialNo;
	}
}
