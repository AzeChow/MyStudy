package com.bestway.bcs.contract.entity;

import java.io.Serializable;

public class TempContractExg implements Serializable{
	/**
     * 是否选中
     */
    private Boolean    isSelected = null;
	private ContractExg contractExg = null;
	private String errinfo = null;
	
	public ContractExg getContractExg() {
		return contractExg;
	}
	public void setContractExg(ContractExg contractExg) {
		this.contractExg = contractExg;
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
