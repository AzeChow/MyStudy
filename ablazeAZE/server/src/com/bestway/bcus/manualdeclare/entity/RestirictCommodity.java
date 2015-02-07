package com.bestway.bcus.manualdeclare.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 限制类商品
 * @author ower
 *
 */
public class RestirictCommodity extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 类型
	 */
	private String types;
	/**
	 * 帐册序号
	 */
	private String seqNum;

	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 型号规格
	 */
	private String spec;
	/**
	 * 商品编码
	 */
	private String complex;

	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 数量
	 */

	private Double amount;
	/**
	 * 维护时间
	 */
	private Date vindicadate;
	/**
	 * 维护人
	 */
	private String vindicator;
	/**
	 * 恢复日期
	 */
	private Date revertdate;
	/**
	 * 恢复人
	 */
	private String revertuser;

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
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

	public String getComplex() {
		return complex;
	}

	public void setComplex(String complex) {
		this.complex = complex;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getVindicadate() {
		return vindicadate;
	}

	public void setVindicadate(Date vindicadate) {
		this.vindicadate = vindicadate;
	}

	public String getVindicator() {
		return vindicator;
	}

	public void setVindicator(String vindicator) {
		this.vindicator = vindicator;
	}

	public Date getRevertdate() {
		return revertdate;
	}

	public void setRevertdate(Date revertdate) {
		this.revertdate = revertdate;
	}

	public String getRevertuser() {
		return revertuser;
	}

	public void setRevertuser(String revertuser) {
		this.revertuser = revertuser;
	}


	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
