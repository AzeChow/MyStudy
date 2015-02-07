package com.bestway.dzsc.qp.entity;

import java.util.List;

/**
 * 从QP抓取过来的合同备案数据
 * 
 * @author Administrator
 * 
 */
public class TempQPEmsTrData implements java.io.Serializable{
	/**
	 * 合同备案表头资料
	 */
	private DzscQPEmsPorWjHead ptsTrHead;
	/**
	 * 料件
	 */
	private List<DzscQPEmsImgWj> ptsTrListImg;
	/**
	 * 成品
	 */
	private List<DzscQPEmsExgWj> ptsTrListExg;

	public DzscQPEmsPorWjHead getPtsTrHead() {
		return ptsTrHead;
	}

	public void setPtsTrHead(DzscQPEmsPorWjHead ptsEmsHead) {
		this.ptsTrHead = ptsEmsHead;
	}

	public List<DzscQPEmsImgWj> getPtsTrListImg() {
		return ptsTrListImg;
	}

	public List<DzscQPEmsExgWj> getPtsTrListExg() {
		return ptsTrListExg;
	}

	public void setPtsTrListImg(List<DzscQPEmsImgWj> ptsTrListImg) {
		this.ptsTrListImg = ptsTrListImg;
	}

	public void setPtsTrListExg(List<DzscQPEmsExgWj> ptsTrListExg) {
		this.ptsTrListExg = ptsTrListExg;
	}

	
}
