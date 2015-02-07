package com.bestway.common.fpt.entity;

import java.io.Serializable;

import com.bestway.common.erpbill.entity.CustomOrderDetail;

public class TempFptAppheadAndOrder implements Serializable{
	private CustomOrderDetail detial = null;
	private Boolean isSelected = false;
	private Double tempAmount = 0.0;
	public CustomOrderDetail getDetial() {
		return detial;
	}
	public void setDetial(CustomOrderDetail detial) {
		this.detial = detial;
	}
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	public Double getTempAmount() {
		return tempAmount;
	}
	public void setTempAmount(Double tempAmount) {
		this.tempAmount = tempAmount;
	}
}
