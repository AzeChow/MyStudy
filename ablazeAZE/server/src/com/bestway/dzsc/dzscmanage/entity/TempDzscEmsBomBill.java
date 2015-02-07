package com.bestway.dzsc.dzscmanage.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;

public class TempDzscEmsBomBill extends BaseScmEntity{


   private DzscEmsBomBill bom = null;
   private String errinfo = null;
   
   
   
   
public String getErrinfo() {
	return errinfo;
}
public void setErrinfo(String errinfo) {
	this.errinfo = errinfo;
}
public DzscEmsBomBill getBom() {
	return bom;
}
public void setBom(DzscEmsBomBill bom) {
	this.bom = bom;
}

	
}
