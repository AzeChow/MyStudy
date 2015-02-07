package com.bestway.dzsc.dzscmanage.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;

public class TempDzscEmsImgBill extends BaseScmEntity {

	private DzscEmsImgBill img = null;
	private String errinfo = null;
	private Double amount = null;
	private Double money = null;
	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}

	public DzscEmsImgBill getImg() {
		return img;
	}

	public void setImg(DzscEmsImgBill img) {
		this.img = img;
	}

	public Double getAmount() {
		if(this.amount == null){
			return 0d;
		}
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getMoney() {
		if(this.money == null){
			return 0d;
		}
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	
}
