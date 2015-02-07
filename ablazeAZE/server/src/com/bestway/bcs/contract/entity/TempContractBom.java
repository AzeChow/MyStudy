package com.bestway.bcs.contract.entity;

import java.io.Serializable;

public class TempContractBom implements Serializable{

	private ContractBom contractBom = null;
	private String errinfo = null;
	
	public ContractBom getContractBom() {
		return contractBom;
	}
	public void setContractBom(ContractBom contractBom) {
		this.contractBom = contractBom;
	}
	public String getErrinfo() {
		return errinfo;
	}
	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}
	
	
}
