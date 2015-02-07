package com.bestway.common.erpbill.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 客户订单参数设定
 * @author ower
 *
 */
public class Customparames extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
	.getSerialVersionUID();
	
	/**
	 * 作帐方法1:递增法 2：覆盖法
	 */
	private Integer setzzff = 1;
	
	/**
	 * 报关类型 1:电子手册 2：纸制手册 3：纸制手册电子化
	 */
	private Integer setbgtype = 0;
	/**
	 * 单耗来源　１：报关常用工厂ＢＯＭ　　２：ＢＯＭ备案　　３：报关单耗
	 */
	private Integer rateSource = 0;


	/**
	 * wss:2010.05.09新加的
	 * 料件及成品单耗表商品数量保留小数位
	 * @param rateSource
	 * 默认为5
	 */
	private Integer decimalSize = 5;

	/**
	 * 设置单耗来源
	 * @param rateSource
	 */
	public void setRateSource(Integer rateSource) {
		this.rateSource = rateSource;
	}

	/**
	 * 设置报关类型
	 * @param setbgtype
	 */
	public void setSetbgtype(Integer setbgtype) {
		this.setbgtype = setbgtype;
	}

	/**
	 * 设置作帐方法
	 * @param setzzff
	 */
	public void setSetzzff(Integer setzzff) {
		this.setzzff = setzzff;
	}

	/**
	 * 获取单耗来源
	 * @return
	 */
	public Integer getRateSource() {
		return rateSource;
	}

	/**
	 * 获取报关类型
	 * @return
	 */
	public Integer getSetbgtype() {
		return setbgtype;
	}

	/**
	 * 获取作帐方法
	 * @return
	 */
	public Integer getSetzzff() {
		return setzzff;
	}

	/**
	 * 获取保留小数位
	 * @return
	 */
	public Integer getDecimalSize() {
		
		return decimalSize == null ? 5:decimalSize;
	}

	/**
	 * 设置保留小数位
	 * @param decimalSize
	 */
	public void setDecimalSize(Integer decimalSize) {
		this.decimalSize = decimalSize;
	}
	
	
}
