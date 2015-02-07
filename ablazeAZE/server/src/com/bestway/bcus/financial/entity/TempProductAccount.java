package com.bestway.bcus.financial.entity;

import com.bestway.common.BaseScmEntity;


public class TempProductAccount extends BaseScmEntity {
	private ProductAccount entity = new ProductAccount();
	
	private String errinfo;


	public ProductAccount getEntity() {
		return entity;
	}

	public void setEntity(ProductAccount entity) {
		this.entity = entity;
	}

	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}
}
