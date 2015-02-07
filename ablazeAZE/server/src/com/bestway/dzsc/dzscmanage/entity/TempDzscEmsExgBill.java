package com.bestway.dzsc.dzscmanage.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;

public class TempDzscEmsExgBill extends BaseScmEntity{


   private DzscEmsExgBill exg = null;
   private String errinfo = null;
   
   
   
   
public String getErrinfo() {
	return errinfo;
}
public void setErrinfo(String errinfo) {
	this.errinfo = errinfo;
}
public DzscEmsExgBill getExg() {
	return exg;
}
public void setExg(DzscEmsExgBill exg) {
	this.exg = exg;
}

	
}
