package com.bestway.bls.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.BaseScmEntity;

/**
 * 保税物料进口申请表体
 * @author chen
 *
 */
public class GoodsList extends BaseScmEntity{

	/**
	 * 海关商品编码(Complex)
	 */
	private Complex codeTS;
	
	/**
	 * 保税物料进口申请表表头
	 * @return
	 */     
	private MaterielImportApply materielImportApply;

	public MaterielImportApply getMaterielImportApply() {
		return materielImportApply;
	}

	public void setMaterielImportApply(MaterielImportApply materielImportApply) {
		this.materielImportApply = materielImportApply;
	}

	public Complex getCodeTS() {
		return codeTS;
	}

	public void setCodeTS(Complex codeTS) {
		this.codeTS = codeTS;
	}
	
	
}
