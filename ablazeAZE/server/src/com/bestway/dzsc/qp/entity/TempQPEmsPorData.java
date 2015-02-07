package com.bestway.dzsc.qp.entity;

import java.util.List;

/**
 * 从QP抓取过来的通关备案数据 
 * @author Administrator
 *
 */
public class TempQPEmsPorData implements java.io.Serializable{
	/**
	 * 通关备案表头资料
	 */
	private DzscQPEmsPorBillHead ptsEmsHead;
	/**
	 * 归并前料件
	 */
	private List<DzscQPEmsPorMaterialExg> ptsEmsListImg;
	/**
	 * 归并前成品
	 */
	private List<DzscQPEmsPorMaterialImg> ptsEmsListExg;
	/**
	 * 归并后料件
	 */
	private List<DzscQPEmsImgBill> ptsEmsListAImg;
	/**
	 * 归并后成品
	 */
	private List<DzscQPEmsExgBill> ptsEmsListAExg;
	/**
	 * 单耗资料
	 */
	private List<DzscQPEmsBomBill> ptsEmsListCm;
	
	public DzscQPEmsPorBillHead getPtsEmsHead() {
		return ptsEmsHead;
	}
	public List<DzscQPEmsPorMaterialExg> getPtsEmsListImg() {
		return ptsEmsListImg;
	}
	public List<DzscQPEmsPorMaterialImg> getPtsEmsListExg() {
		return ptsEmsListExg;
	}
	public List<DzscQPEmsImgBill> getPtsEmsListAImg() {
		return ptsEmsListAImg;
	}
	public List<DzscQPEmsExgBill> getPtsEmsListAExg() {
		return ptsEmsListAExg;
	}
	public List<DzscQPEmsBomBill> getPtsEmsListCm() {
		return ptsEmsListCm;
	}
	public void setPtsEmsHead(DzscQPEmsPorBillHead ptsEmsHead) {
		this.ptsEmsHead = ptsEmsHead;
	}
	public void setPtsEmsListImg(List<DzscQPEmsPorMaterialExg> ptsEmsListImg) {
		this.ptsEmsListImg = ptsEmsListImg;
	}
	public void setPtsEmsListExg(List<DzscQPEmsPorMaterialImg> ptsEmsListExg) {
		this.ptsEmsListExg = ptsEmsListExg;
	}
	public void setPtsEmsListAImg(List<DzscQPEmsImgBill> ptsEmsListAImg) {
		this.ptsEmsListAImg = ptsEmsListAImg;
	}
	public void setPtsEmsListAExg(List<DzscQPEmsExgBill> ptsEmsListAExg) {
		this.ptsEmsListAExg = ptsEmsListAExg;
	}
	public void setPtsEmsListCm(List<DzscQPEmsBomBill> ptsEmsListCm) {
		this.ptsEmsListCm = ptsEmsListCm;
	}
	
		
}
