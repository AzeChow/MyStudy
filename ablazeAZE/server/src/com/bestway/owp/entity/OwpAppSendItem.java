package com.bestway.owp.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 外发加工 - 申请表 - 发出货物商品
 * @author wss2010.08.04
 */
public class OwpAppSendItem extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 申请表表头
	 */
	private OwpAppHead head = null;
	/**
	 * 外发序号
	 */
	private Integer seqNum = null;
	
	
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
	
	/**
	 *商品项号 非空 
	 *(手册序号)
	 */
	private Integer trNo = null;


	
	/**
	 * 外发序号
	 * @return
	 */
	public Integer getSeqNum() {
		return seqNum;
	}
	
	/**
	 * 外发序号
	 * @param 
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}


	/**
	 * 商品编码
	 * @return
	 */
	public Complex getComplex() {
		return complex;
	}

	/**
	 * 商品编码
	 * @param complex
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	/**
	 * 商品名称
	 * @return
	 */
	public String getHsName() {
		return hsName;
	}

	/**
	 * 商品名称
	 * @param hsName
	 */
	public void setHsName(String hsName) {
		this.hsName = hsName;
	}

	/**
	 * 商品规格
	 * @return
	 */
	public String getHsSpec() {
		return hsSpec;
	}

	/**
	 * 商品规格
	 * @param hsSpec
	 */
	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}

	/**
	 * 计量单位
	 * @return
	 */
	public Unit getHsUnit() {
		return hsUnit;
	}

	/**
	 * 计量单位
	 * @param hsUnit
	 */
	public void setHsUnit(Unit hsUnit) {
		this.hsUnit = hsUnit;
	}

	/**
	 * 申报数量
	 * @return
	 */
	public Double getQty() {
		return qty;
	}

	/**
	 * 申报数量
	 * @param qty
	 */
	public void setQty(Double qty) {
		this.qty = qty;
	}

	/**
	 * 备注
	 * @return
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 备注
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 申请表表头
	 * @return
	 */
	public OwpAppHead getHead() {
		return head;
	}

	/**
	 * 申请表表头
	 * @param head
	 */
	public void setHead(OwpAppHead head) {
		this.head = head;
	}

	/**
	 * 修改标记 "0"：未修改 "1":修改 "2"：删除 "3"：增加
	 * @return
	 */
	public String getModifyMark() {
		return modifyMark;
	}

	/**
	 * 修改标记 "0"：未修改 "1":修改 "2"：删除 "3"：增加
	 * @param modifyMark
	 */
	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	/**
	 * 商品项号 非空 (通关手册备案中的料件序号)
	 * @return
	 */
	public Integer getTrNo() {
		return trNo;
	}

	/**
	 * 商品项号 非空 (通关手册备案中的料件序号)
	 * @param trNo
	 */
	public void setTrNo(Integer trNo) {
		this.trNo = trNo;
	}

	
	
}

