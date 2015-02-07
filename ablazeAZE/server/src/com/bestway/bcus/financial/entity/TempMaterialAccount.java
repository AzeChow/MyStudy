package com.bestway.bcus.financial.entity;

import com.bestway.common.BaseScmEntity;


public class TempMaterialAccount extends BaseScmEntity {
	private MaterialAccount entity = new MaterialAccount();
	
	private String errinfo;


	public MaterialAccount getEntity() {
		return entity;
	}

	public void setEntity(MaterialAccount entity) {
		this.entity = entity;
	}

	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}
}
