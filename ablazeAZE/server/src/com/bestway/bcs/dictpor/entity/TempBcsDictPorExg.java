package com.bestway.bcs.dictpor.entity;

import com.bestway.common.BaseScmEntity;

public class TempBcsDictPorExg extends BaseScmEntity{

	private BcsDictPorExg exg = null;
	 private String errinfo = null;
	 
	 
	public String getErrinfo() {
		return errinfo;
	}
	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}
	public BcsDictPorExg getExg() {
		return exg;
	}
	public void setExg(BcsDictPorExg exg) {
		this.exg = exg;
	}

}
