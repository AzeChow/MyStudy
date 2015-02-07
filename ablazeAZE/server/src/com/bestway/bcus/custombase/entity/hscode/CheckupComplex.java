package com.bestway.bcus.custombase.entity.hscode;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.constant.ImpExpFlag;
/**
 * 商检的编码
 * @author ower
 *
 */
public class CheckupComplex extends BaseScmEntity {
	
	/**
	 *	商品编码
	 */
	private Complex complex = null;
	
	/**
	 * 进出口标志
	 * IMPORT=0;	进口标志
	 * EXPORT=1;	出口标志
	 * SPECIAL=2;	特殊报关单
	 */
	private Integer impExpFlag = ImpExpFlag.IMPORT;

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public Integer getImpExpFlag() {
		return impExpFlag;
	}

	public void setImpExpFlag(Integer impExpFlag) {
		this.impExpFlag = impExpFlag;
	}

}
