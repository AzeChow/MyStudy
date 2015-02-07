package com.bestway.bcs.qp.entity;

import java.util.List;

/**
 * 从QP抓取过来的通关备案数据
 * 
 * @author Administrator
 * 
 */
public class TempQPContractData implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7688747954202670865L;
	/**
	 * 通关备案表头资料
	 */
	private BcsQPContract bcsQPContract;
	/**
	 * 归并后料件
	 */
	private List<BcsQPContractImg> ptsEmsListAImg;
	/**
	 * 归并后成品
	 */
	private List<BcsQPContractExg> ptsEmsListAExg;
	/**
	 * 单耗资料
	 */
	private List<BcsQPContractBom> ptsEmsListCm;

	public BcsQPContract getBcsQPContract() {
		return bcsQPContract;
	}

	public void setBcsQPContract(BcsQPContract bcsQPContract) {
		this.bcsQPContract = bcsQPContract;
	}

	public List<BcsQPContractImg> getPtsEmsListAImg() {
		return ptsEmsListAImg;
	}

	public void setPtsEmsListAImg(List<BcsQPContractImg> ptsEmsListAImg) {
		this.ptsEmsListAImg = ptsEmsListAImg;
	}

	public List<BcsQPContractExg> getPtsEmsListAExg() {
		return ptsEmsListAExg;
	}

	public void setPtsEmsListAExg(List<BcsQPContractExg> ptsEmsListAExg) {
		this.ptsEmsListAExg = ptsEmsListAExg;
	}

	public List<BcsQPContractBom> getPtsEmsListCm() {
		return ptsEmsListCm;
	}

	public void setPtsEmsListCm(List<BcsQPContractBom> ptsEmsListCm) {
		this.ptsEmsListCm = ptsEmsListCm;
	}

}
