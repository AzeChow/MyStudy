package com.bestway.bcus.client.checkcancel;

import com.bestway.common.BaseScmEntity;

/**
 * 
 * 临时实体 -- 用于 导入计算内购金额
 * 
 * @author zcj
 *
 */
public class TempCancelImgResult extends BaseScmEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 料件序号
	 */
	private Integer seqNum;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 规格
	 */
	private String commSpec;

	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 内购数量
	 */
	private Double innerUseSumNum;

	/**
	 * 内购金额
	 */
	private Double inChinaBuyNum;

	/**
	 * 错误信息
	 */
	private String errinfo;

	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCommSpec() {
		return commSpec;
	}

	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}

	public Double getInnerUseSumNum() {
		return innerUseSumNum;
	}

	public void setInnerUseSumNum(Double innerUseSumNum) {
		this.innerUseSumNum = innerUseSumNum;
	}

	/**
	 * 内购金额
	 */
	public Double getInChinaBuyNum() {
		return inChinaBuyNum;
	}

	public void setInChinaBuyNum(Double inChinaBuyNum) {
		this.inChinaBuyNum = inChinaBuyNum;
	}

}
