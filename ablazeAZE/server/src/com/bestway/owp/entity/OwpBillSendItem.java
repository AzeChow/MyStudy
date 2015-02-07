package com.bestway.owp.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
/**
 * 外发加工 - 收发货登记 - 发货单企业表体
 * @author 石小凯 2010.08.10
 */
public class OwpBillSendItem extends BaseScmEntity {
	private static final long serialVersionUID = -8133228387152509645L;

	/**
	 * 发货单表头
	 */
	private OwpBillSendHead head;
	/**
	 * 序号
	 */
	private Integer listNo;
	
	/**
	 * 申表请序号
	 */
	private Integer appGNo;
	
	/**
	 *商品项号 非空 
	 *(手册序号)
	 */
	private Integer trNo ;
	
	/**
	 * 商品编码
	 */
	private Complex complex;
	
	/**
	 * 商品名称
	 */
	private String hsName;
	
	/**
	 * 商品规格
	 */
	private String hsSpec;
	
	/**
	 * 计量单位
	 */
	private Unit hsUnit;
	
	/**
	 * 申报数量
	 */
	private Double qty;
	
	/**
	 * 备注
	 */
	private String note;

	/**
	 * 修改标记
	 * "0"：未修改  "1":修改   "2"：删除   "3"：增加
	 */
	private String modifyMark;
	
	
	public String getModifyMark() {
		return modifyMark;
	}

	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	public Integer getTrNo() {
		return trNo;
	}

	public void setTrNo(Integer trNo) {
		this.trNo = trNo;
	}

	/**
	 * 获取收货单表头
	 * @return
	 */
	public OwpBillSendHead getHead() {
		return head;
	}

	/**
	 * 设置收货单表头
	 * @param head
	 */
	public void setHead(OwpBillSendHead head) {
		this.head = head;
	}

	/**
	 * 获取序号
	 * @return
	 */
	public Integer getListNo() {
		return listNo;
	}

	/**
	 * 设置序号
	 * @param listNo
	 */
	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	/**
	 * 获取申表请序号
	 * @return
	 */
	public Integer getAppGNo() {
		return appGNo;
	}

	/**
	 * 设置申表请序号
	 * @param appGNo
	 */
	public void setAppGNo(Integer appGNo) {
		this.appGNo = appGNo;
	}

	/**
	 * 获取商品编码
	 * @return
	 */
	public Complex getComplex() {
		return complex;
	}

	/**
	 * 设置商品编码
	 * @param complex
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	/**
	 * 获取商品名称
	 * @return
	 */
	public String getHsName() {
		return hsName;
	}

	/**
	 * 设置商品名称
	 * @param hsName
	 */
	public void setHsName(String hsName) {
		this.hsName = hsName;
	}

	/**
	 * 获取商品规格
	 * @return
	 */
	public String getHsSpec() {
		return hsSpec;
	}

	/**
	 * 设置商品规格
	 * @param hsSpec
	 */
	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}

	/**
	 * 获取计量单位
	 * @return
	 */
	public Unit getHsUnit() {
		return hsUnit;
	}

	/**
	 * 设置计量单位
	 * @param hsUnit
	 */
	public void setHsUnit(Unit hsUnit) {
		this.hsUnit = hsUnit;
	}

	/**
	 * 获取申报数量
	 * @return
	 */
	public Double getQty() {
		return qty;
	}

	/**
	 * 设置申报数量
	 * @param qty
	 */
	public void setQty(Double qty) {
		this.qty = qty;
	}

	/**
	 * 获取备注
	 * @return
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 设置备注
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	


}
