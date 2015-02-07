package com.bestway.bcs.dictpor.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 备案资料库成品表
 *
 */
public class BcsDictPorExg extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 备案资料库表头
	 */
	private BcsDictPorHead dictPorHead = null;

	/**
	 * 备案序号
	 */
	private Integer seqNum = null;

	/**
	 * 归并序号
	 */
	private Integer innerMergeSeqNum = null;

	/**
	 * 十位商品编码
	 */
	private Complex complex = null;

	/**
	 * 名称
	 */
	private String commName = null;

	/**
	 * 规格
	 */
	private String commSpec = null;

	// /**
	// * 第一转换比率
	// */
	// private Double firstRate = null;
	//    
	// /**
	// * 第二转换比率
	// */
	// private Double secondRate = null;

	/**
	 * 常用单位
	 */
	private Unit comUnit = null;

	// /**
	// * 单位重量
	// */
	// private Double unitWeight = null;

	/**
	 * 单价
	 */
	private Double unitPrice = null;

	/**
	 * 币制
	 */
	private Curr curr = null;

	// /**
	// * 产销国
	// */
	// private Country country = null;

	// /**
	// * 损耗
	// */
	// private Double wear = null;

	// /**
	// * 类型1
	// */
	// private String scmCoi = null;

	// /**
	// * 主辅料
	// */
	// private String type = null;
	//    
	// /**
	// * 加工费单价1
	// */
	// private Double machPrice = null;

	// /**
	// * 第一法定数量
	// */
	// private Double legalAmount = null;
	//
	// /**
	// * 第二法定数量
	// */
	// private Double secondLegalAmount = null;

	   /**
	 * 修改标志
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String modifyMark;

	/**
	 * 备注
	 */
	private String memo;
	
	/**
	 * 成品编码（外经接口使用）
	 * @return
	 */
	private String wjCode;
	
	

	public BcsDictPorHead getDictPorHead() {
		return dictPorHead;
	}

	public void setDictPorHead(BcsDictPorHead credenceHead) {
		this.dictPorHead = credenceHead;
	}

	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	public String getCommSpec() {
		return commSpec;
	}

	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public Unit getComUnit() {
		return comUnit;
	}

	public void setComUnit(Unit comUnit) {
		this.comUnit = comUnit;
	}

	public Curr getCurr() {
		return curr;
	}

	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	// public Double getLegalAmount() {
	// return legalAmount;
	// }
	//
	// public void setLegalAmount(Double legalAmount) {
	// this.legalAmount = legalAmount;
	// }

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double price) {
		this.unitPrice = price;
	}

	// public Double getSecondLegalAmount() {
	// return secondLegalAmount;
	// }
	//
	// public void setSecondLegalAmount(Double secondLegalAmount) {
	// this.secondLegalAmount = secondLegalAmount;
	// }

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getModifyMark() {
		return modifyMark;
	}

	public void setModifyMark(String declareState) {
		this.modifyMark = declareState;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * 商品编号
	 * 
	 * @return
	 */
	public String getComplexTCode() {
		if (this.getComplex() == null
				|| this.getComplex().getCode().length() < 8) {
			return "";
		} else {
			return this.getComplex().getCode().substring(0,8);
		}
	}
	/**
	 * 附加编码
	 * 
	 * @return
	 */
	public String getComplexSCode() {
		if (this.getComplex() == null
				|| this.getComplex().getCode().length() <= 8) {
			return "";
		} else {
			return this.getComplex().getCode().substring(8,
					this.getComplex().getCode().length());
		}
	}

	public Integer getInnerMergeSeqNum() {
		return innerMergeSeqNum;
	}

	public void setInnerMergeSeqNum(Integer tenSeqNum) {
		this.innerMergeSeqNum = tenSeqNum;
	}

	public String getWjCode() {
		return wjCode;
	}

	public void setWjCode(String wjCode) {
		this.wjCode = wjCode;
	}
}
