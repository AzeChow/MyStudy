package com.bestway.bcus.cas.invoice.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.entity.BaseEmsImg;

/**
 * 海关帐的发票信息
 * @author Administrator
 * 
 */
public class CasInvoiceInfo extends BaseScmEntity {
	/**
	 * 版本信息
	 */
	private static final long serialVersionUID = -4088635600085560557L;

	/**
	 * 发票表头
	 */
	private CasInvoice casInvoice = null;

	/**
	 * 商品编码
	 */
	private Complex complex = null;

	/**
	 * 报关名称
	 */
	private String cuName = null;

	/**
	 * 报关规格
	 */
	private String cuSpec = null;

	/**
	 * 数量
	 */
	private Double amount = null;

	/**
	 * 单位
	 */
	private Unit unit = null;

	/**
	 * 单价
	 */

	private Double price = null;

	/**
	 * 总金额
	 */
	private Double totalMoney = null;

	/**
	 * 总净重
	 */
	private Double totalWeight = null;

	/**
	 * 是否核销
	 */
	private Boolean canceled = false;

	/**
	 * 是否完全对应
	 */
	private Boolean toBillsAll = false;

	/**
	 * 对应数量
	 */
	private Double toBillNum = null;

	/**
	 * 核销数量
	 */
	private Double canceledNum = null;
	/**
	 * 来源类型
	 * 	BCUS = 0;	电子帐册
	 *	BCS = 1;	电子化手册
	 *	DZSC = 3;	电子手册
	 */
	private Integer projectType = null;
	/**
	 * 归并序号
	 */
	private Integer seqNum = null;
	/**
	 * 对应帐册核销序号
	 */
	private Integer cancelEmsSeqNum = null;

	/**
	 * 获取数量
	 * @return
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * 设置数量
	 * @param amount
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public String getCuName() {
		return cuName;
	}

	public void setCuName(String cuName) {
		this.cuName = cuName;
	}

	public String getCuSpec() {
		return cuSpec;
	}

	public void setCuSpec(String cuSpec) {
		this.cuSpec = cuSpec;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public CasInvoice getCasInvoice() {
		return casInvoice;
	}

	public void setCasInvoice(CasInvoice casInvoice) {
		this.casInvoice = casInvoice;
	}

	public Boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(Boolean canceled) {
		this.canceled = canceled;
	}

	/**
	 * 获取对应数量
	 * @return
	 */
	public Double getToBillNum() {
		return toBillNum;
	}

	/**
	 * 设置对应数量
	 * @param toBillNum
	 */
	public void setToBillNum(Double toBillNum) {
		this.toBillNum = toBillNum;
	}

	/**
	 * 获取是否完全对应标识符
	 * @return
	 */
	public Boolean getToBillsAll() {
		return toBillsAll;
	}

	/**
	 * 是否完全对应标识符
	 * @param toBillsAll
	 */
	public void setToBillsAll(Boolean toBillsAll) {
		this.toBillsAll = toBillsAll;
	}

	public Boolean getCanceled() {
		return canceled;
	}

	public Double getCanceledNum() {
		return canceledNum;
	}

	public void setCanceledNum(Double canceledNum) {
		this.canceledNum = canceledNum;
	}

	public Integer getProjectType() {
		return projectType;
	}

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getProjectName() {
		Integer project = this.getProjectType();
		if (project == null) {
			return "";
		}
		if (project == ProjectType.BCS) {
			return "电子化手册";
		} else if (project == ProjectType.BCUS) {
			return "电子帐册";
		} else if (project == ProjectType.DZSC) {
			return "电子手册";
		} else {
			return "";
		}

	}

	public Integer getCancelEmsSeqNum() {
		return cancelEmsSeqNum;
	}

	public void setCancelEmsSeqNum(Integer cancelEmsSeqNum) {
		this.cancelEmsSeqNum = cancelEmsSeqNum;
	}

	/**
	 * 单价
	 */
	private Double impost;

	/**
	 * 总价
	 */
	private Double allMoney;

	public Double getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(Double allMoney) {
		this.allMoney = allMoney;
	}

	public Double getImpost() {
		return impost;
	}

	public void setImpost(Double impost) {
		this.impost = impost;
	}
}
