package com.bestway.bcs.qp.entity;

import java.util.List;

/**
 * 从QP抓取过来的合同备案数据
 * 
 * @author Administrator
 * 
 */
public class TempQPDictPorData implements java.io.Serializable {
	/**
	 * 合同备案表头资料
	 */
	private BcsQPDictPorHead ptsDictPorHead;
	/**
	 * 料件
	 */
	private List<BcsQPDictPorImg> ptsDictListImg;
	/**
	 * 成品
	 */
	private List<BcsQPDictPorExg> ptsDictListExg;

	public BcsQPDictPorHead getPtsDictPorHead() {
		return ptsDictPorHead;
	}

	public void setPtsDictPorHead(BcsQPDictPorHead ptsTrHead) {
		this.ptsDictPorHead = ptsTrHead;
	}

	public List<BcsQPDictPorImg> getPtsDictListImg() {
		return ptsDictListImg;
	}

	public void setPtsDictListImg(List<BcsQPDictPorImg> ptsTrListImg) {
		this.ptsDictListImg = ptsTrListImg;
	}

	public List<BcsQPDictPorExg> getPtsDictListExg() {
		return ptsDictListExg;
	}

	public void setPtsDictListExg(List<BcsQPDictPorExg> ptsTrListExg) {
		this.ptsDictListExg = ptsTrListExg;
	}

}
