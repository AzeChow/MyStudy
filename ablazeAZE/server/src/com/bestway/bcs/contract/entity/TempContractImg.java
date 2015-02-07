package com.bestway.bcs.contract.entity;

import java.io.Serializable;

public class TempContractImg implements Serializable{
	/**
     * 是否选中
     */
    private Boolean    isSelected = null;
	private ContractImg contractImg = null;
	private String errinfo = null;
	
	public ContractImg getContractImg() {
		return contractImg;
	}
	public void setContractImg(ContractImg contractImg) {
		this.contractImg = contractImg;
	}
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	public String getErrinfo() {
		return errinfo;
	}
	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}
	
}
