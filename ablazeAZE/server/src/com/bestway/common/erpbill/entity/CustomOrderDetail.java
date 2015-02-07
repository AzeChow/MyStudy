package com.bestway.common.erpbill.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 客户订单明细
 * 
 * @author Administrator
 * 
 */
public class CustomOrderDetail extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 订单头
	 */
	private CustomOrder customOrder = null;

	/**
	 * 订单货品
	 */
	private Materiel materiel = null;

	/**
	 * 交货日期
	 */
	private Date salesDate = null;

	/**
	 * 是否转厂单据
	 */
	private Boolean ifzc = false;
	/**
	 * 单位
	 */
	private CalUnit calUnit;
	/**
	 * 币别
	 */
	private Curr curr = null;
	/**
	 * 单价
	 */
	private Double unitPrice = null;
	/**
	 * 数量
	 */
	private Double amount = null;

	/**
	 * 报关名称
	 */
	private String bgname = null;

	/**
	 * 报关规格
	 */
	private String bgspec = null;

	/**
	 * 报关单位
	 */
	private Unit bgunit = null;
	/**
	 * 折算报关数量
	 */
	private Double bgamount = null;

	/**
	 * 物料与报关商品的折算系数
	 */
	private Double unitConvert = null;
	/**
	 * 总价
	 */
	private Double totalPrice = null;
	/**
	 * 成品版本号
	 */
	private Integer version = null;

	/**
	 * 是否已转合同
	 */
	private Boolean ifcustomer = false;

	/**
	 * 手册编号
	 */
	private String emsno = null;
	
	/**
	 * 生成合同的企业内部编号
	 * 因为在订单转合同时还没有手册编号
	 * 只能用内部编号
	 */
	private String copEmsNo = null;

	/**
	 * 修改日期
	 */
	private Date editDate = null;
	/**
	 * 已转厂数量
	 */
	private Double transNum = null;
	/**
	 * 已转合同数量
	 */
	private Double contractNum = null;
	/**
	 * 未转厂数量
	 */
	private Double notTransNum = null;
	/**
	 * 未转合同数量
	 */
	private Double notContractNum = null;
	/**
	 *订单状态: 1:有效,2:作废
	 */
	private Integer state = null;

	/**
	 *订单在ＥＲＰ中的ＩＤ
	 */
	private String orderERPId = null;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public CalUnit getCalUnit() {
		return calUnit;
	}

	public void setCalUnit(CalUnit calUnit) {
		this.calUnit = calUnit;
	}

	public CustomOrder getCustomOrder() {
		return customOrder;
	}

	public void setCustomOrder(CustomOrder customOrder) {
		this.customOrder = customOrder;
	}

	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getBgamount() {
		return bgamount;
	}

	public void setBgamount(Double bgamount) {
		this.bgamount = bgamount;
	}

	public String getBgname() {
		return bgname;
	}

	public void setBgname(String bgname) {
		this.bgname = bgname;
	}

	public String getBgspec() {
		return bgspec;
	}

	public void setBgspec(String bgspec) {
		this.bgspec = bgspec;
	}

	public Unit getBgunit() {
		return bgunit;
	}

	public void setBgunit(Unit bgunit) {
		this.bgunit = bgunit;
	}

	public Boolean getIfzc() {
		return ifzc;
	}

	public void setIfzc(Boolean ifzc) {
		this.ifzc = ifzc;
	}

	public Date getSalesDate() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (salesDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(salesDate));
		}
		return salesDate;

	}

	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}

	public String getEmsno() {
		return emsno;
	}

	public void setEmsno(String emsno) {
		this.emsno = emsno;
	}

	public Boolean getIfcustomer() {
		return ifcustomer;
	}

	public void setIfcustomer(Boolean ifcustomer) {
		this.ifcustomer = ifcustomer;
	}

	public Double getUnitConvert() {
		return unitConvert;
	}

	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}

	public Curr getCurr() {
		return curr;
	}

	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public Double getContractNum() {
		return contractNum;
	}

	public void setContractNum(Double contractNum) {
		this.contractNum = contractNum;
	}

	public Double getNotTransNum() {

		return notTransNum;

	}

	public void setNotTransNum(Double notTransNum) {
		this.notTransNum = notTransNum;
	}

	public Double getTransNum() {
		return transNum;
	}

	public void setTransNum(Double transNum) {
		this.transNum = transNum;
	}

	public Double getNotContractNum() {
		return notContractNum;

	}

	public void setNotContractNum(Double notContractNum) {
		this.notContractNum = notContractNum;
	}

	public String getOrderERPId() {
		return orderERPId;
	}

	public void setOrderERPId(String orderERPId) {
		this.orderERPId = orderERPId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	public String getCopEmsNo() {
		return copEmsNo;
	}

	public void setCopEmsNo(String copEmsNo) {
		this.copEmsNo = copEmsNo;
	}
}
