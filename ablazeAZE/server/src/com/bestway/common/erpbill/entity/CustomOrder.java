package com.bestway.common.erpbill.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 客户订单表头
 * 
 * @author Administrator
 * 
 */
public class CustomOrder extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 订单号
	 */
	private String billCode = null;
	/**
	 * 订单日期
	 */
	private Date orderDate = null;
	/**
	 * 订单客户
	 */
	private ScmCoc customer = null;

	/**
	 * 是否生效
	 */
	private Boolean ifok = false;

	/**
	 * 订单类型(0:销售订单 1:采购订单)
	 */
	private Integer ordertype = 0;

	/**
	 * 导入次数(订单版本号)
	 */
	private Integer importcount = 0;

	/**
	 * 生成合同的企业内部编号
	 * 没保存到数据库中临时存放表
	 * 体中的内部编号
	 */
	private String copEmsNo = null;

	/**
	 * 是否转厂单据
	 */
	private Boolean ifzc = false;

	/**
	 * 报关类型 1:电子手册 2：纸制手册 3：纸制手册电子化
	 */
	private Integer customType = 0;
	/**
	 * 单耗来源　１：报关常用工厂ＢＯＭ　　２：ＢＯＭ备案　　３：报关单耗
	 */
	private Integer rateSource = 0;

	/**
	 * 客户订单参数
	 */
	private Customparames customparames = null;
	/**
	 * 已转合同项数
	 */
	private Integer isChangeToContract;
	/**
	 * 未转合同数
	 */
	private Integer notChangeToContract;
	/**
	 * 定单总项数
	 */
	private Integer contractCount;

	public Boolean getIfok() {
		return ifok;
	}

	public void setIfok(Boolean ifok) {
		this.ifok = ifok;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public ScmCoc getCustomer() {
		return customer;
	}

	public void setCustomer(ScmCoc customer) {
		this.customer = customer;
	}

	public Date getOrderDate() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (orderDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(orderDate));
		}

		return orderDate;
	}

	public void setOrderDate(Date orderDate) {

		this.orderDate = orderDate;
	}

	public Customparames getCustomparames() {
		return customparames;
	}

	public void setCustomparames(Customparames customparames) {
		this.customparames = customparames;
	}

	public Boolean getIfzc() {
		return ifzc;
	}

	public void setIfzc(Boolean ifzc) {
		this.ifzc = ifzc;
	}

	public void setCustomType(Integer customType) {
		this.customType = customType;
	}

	public void setImportcount(Integer importcount) {
		this.importcount = importcount;
	}

	public void setOrdertype(Integer ordertype) {
		this.ordertype = ordertype;
	}

	public void setRateSource(Integer rateSource) {
		this.rateSource = rateSource;
	}

	public Integer getCustomType() {
		return customType;
	}

	public Integer getImportcount() {
		return importcount;
	}

	public Integer getOrdertype() {
		return ordertype;
	}
	/**
	 * 单耗来源　１：报关常用工厂ＢＯＭ　　２：ＢＯＭ备案　　３：报关单耗
	 */
	public Integer getRateSource() {
		return rateSource;
	}

	public Integer getContractCount() {
		return contractCount;
	}

	public void setContractCount(Integer contractCount) {
		this.contractCount = contractCount;
	}

	public Integer getIsChangeToContract() {
		return isChangeToContract;
	}

	public void setIsChangeToContract(Integer isChangeToContract) {
		this.isChangeToContract = isChangeToContract;
	}

	public Integer getNotChangeToContract() {
		return notChangeToContract;
	}

	public void setNotChangeToContract(Integer notChangeToContract) {
		this.notChangeToContract = notChangeToContract;
	}

	public String getCopEmsNo() {
		return copEmsNo;
	}

	public void setCopEmsNo(String copEmsNo) {
		this.copEmsNo = copEmsNo;
	}

}
