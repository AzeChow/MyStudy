package com.bestway.bcs.dictpor.entity;

import com.bestway.common.BaseScmEntity;

public class TempBcsDictPorImg extends BaseScmEntity{

	private BcsDictPorImg img = null;
	 private String errinfo = null;
	 
	 
	public String getErrinfo() {
		return errinfo;
	}
	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}
	public BcsDictPorImg getImg() {
		return img;
	}
	public void setImg(BcsDictPorImg img) {
		this.img = img;
	} 
}
