package com.bestway.customs.common.entity;

/**
 * 关封信息，报关单使用
 * @author Administrator
 *
 */
public class TempTransFactInfo implements java.io.Serializable {
	/**
	 * 关封号
	 */
	private String billNo;
	/**
	 * 客户或供应商名称
	 */
	private String scmCocName;
	/**
	 * 商品编码
	 */
	private String complexCode;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 商品规格
	 */
	private String spec;
	/**
	 * 商品数量
	 */
	private double qty;
	
	/**
	 * 备注
	 */
	private String memo;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String customsEnvelopBillNo) {
		this.billNo = customsEnvelopBillNo;
	}

	public String getScmCocName() {
		return scmCocName;
	}

	public void setScmCocName(String scmCocName) {
		this.scmCocName = scmCocName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public String getComplexCode() {
		return complexCode;
	}

	public void setComplexCode(String complexCode) {
		this.complexCode = complexCode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
