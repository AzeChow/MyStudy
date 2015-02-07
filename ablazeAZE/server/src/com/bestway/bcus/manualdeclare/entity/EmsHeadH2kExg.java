/*
 * Created on 2004-7-12
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseEmsExg;

/**
 * 电子帐册成品
 * 
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class EmsHeadH2kExg extends BaseEmsExg {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 电子帐册表头
	 */
	private EmsHeadH2k emsHeadH2k;

	// private Integer seqNum; //成品序号
	// private Complex complex; //商品编码
	// private String name; //商品名称
	// private String spec; //规格型号
	// private Unit unit; //计量单位

	// private Unit legalUnit; //法定单位
	/**
	 * 法定单位比例因子
	 */
	private Double legalUnitGene;

	// private Unit legalUnit2; //第二法定单位
	/**
	 * 第二法定单位比例因子
	 */
	private Double legalUnit2Gene;

	/**
	 * 重量单位比例因子
	 */
	private Double weigthUnitGene;

	/**
	 * 币制
	 */
	private Curr curr;

	/**
	 * 征免方式
	 */
	private LevyMode levyMode;

	/**
	 * 企业申报数量
	 */
	private Double qty; // 

	/**
	 * 申报单价人民币
	 */
	private Double declarePriceMo;

	// private String note; //备注
	/**
	 * 修改次数
	 */
	private Integer modifyTimes;

    /**
	 * 修改标志
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String modifyMark;

	/**
	 * 最大批准余量
	 */
	private Double maxApprSpace;
	

	/**
	 * 初始库存数量
	 */
	private Double firstQty;


	/**
	 * 单位毛重
	 */
	private Double unitGrossWeight;

	/**
	 * 单位净重
	 */
	private Double unitNetWeight;

	/**
	 * 工厂单价
	 */
	private Double factoryPrice;

	/**
	 * 开始有效期
	 */
	private Date beginDate;

	/**
	 * 结束有效期
	 */
	private Date endDate;

	/**
	 * 变更日期
	 */
	private Date changeDate;

	/**
	 * 发送状态
	 * NOT_SEND = "0";	未发送
	 * WAIT_SEND = "1";	准备发送
	 * SEND = "2";	已经发送
	 */
	private Integer sendState;  

	/**
	 * Temp 版本号
	 */
	private Integer version;
	/**
	 * 最大版本号 2010-05-06新增hcl
	 */
	private Integer maxVersion;

	public Integer getMaxVersion() {
		return maxVersion;
	}

	public void setMaxVersion(Integer maxVersion) {
		this.maxVersion = maxVersion;
	}

	/**
	 * @return Returns the modifyMark.
	 */
	public String getModifyMark() {
		return modifyMark;
	}

	/**
	 * @param modifyMark
	 *            The modifyMark to set.
	 */
	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	/**
	 * @return Returns the modifyTimes.
	 */
	public Integer getModifyTimes() {
		return modifyTimes;
	}

	/**
	 * @param modifyTimes
	 *            The modifyTimes to set.
	 */
	public void setModifyTimes(Integer modifyTimes) {
		this.modifyTimes = modifyTimes;
	}

	// /**
	// * @return Returns the complex.
	// */
	// public Complex getComplex() {
	// return complex;
	// }
	// /**
	// * @param complex The complex to set.
	// */
	// public void setComplex(Complex complex) {
	// this.complex = complex;
	// }
	/**
	 * @return Returns the curr.
	 */
	public Curr getCurr() {
		return curr;
	}

	/**
	 * @param curr
	 *            The curr to set.
	 */
	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	/**
	 * @return Returns the emsHeadH2k.
	 */
	public EmsHeadH2k getEmsHeadH2k() {
		return emsHeadH2k;
	}

	/**
	 * @param emsHeadH2k
	 *            The emsHeadH2k to set.
	 */
	public void setEmsHeadH2k(EmsHeadH2k emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}

	/**
	 * @return Returns the legalUnit.
	 */
	/*
	 * public Unit getLegalUnit() { return this.getComplex().getFirstUnit(); }
	 */
	/**
	 * @param legalUnit
	 *            The legalUnit to set.
	 */
	/*
	 * public void setLegalUnit(Unit legalUnit) { this.legalUnit = legalUnit; }
	 *//**
		 * @return Returns the legalUnit2.
		 */
	/*
	 * public Unit getLegalUnit2() { return this.getComplex().getSecondUnit(); }
	 */
	/**
	 * @param legalUnit2
	 *            The legalUnit2 to set.
	 */
	/*
	 * public void setLegalUnit2(Unit legalUnit2) { this.legalUnit2 =
	 * legalUnit2; }
	 */
	/**
	 * @return Returns the legalUnit2Gene.
	 */
	public Double getLegalUnit2Gene() {
		return legalUnit2Gene;
	}

	/**
	 * @param legalUnit2Gene
	 *            The legalUnit2Gene to set.
	 */
	public void setLegalUnit2Gene(Double legalUnit2Gene) {
		this.legalUnit2Gene = legalUnit2Gene;
	}

	/**
	 * @return Returns the legalUnitGene.
	 */
	public Double getLegalUnitGene() {
		return legalUnitGene;
	}

	/**
	 * @param legalUnitGene
	 *            The legalUnitGene to set.
	 */
	public void setLegalUnitGene(Double legalUnitGene) {
		this.legalUnitGene = legalUnitGene;
	}

	/**
	 * @return Returns the levyMode.
	 */
	public LevyMode getLevyMode() {
		return levyMode;
	}

	/**
	 * @param levyMode
	 *            The levyMode to set.
	 */
	public void setLevyMode(LevyMode levyMode) {
		this.levyMode = levyMode;
	}

	// /**
	// * @return Returns the name.
	// */
	// public String getName() {
	// return name;
	// }
	// /**
	// * @param name The name to set.
	// */
	// public void setName(String name) {
	// this.name = name;
	// }
	// /**
	// * @return Returns the note.
	// */
	// public String getNote() {
	// return note;
	// }
	// /**
	// * @param note The note to set.
	// */
	// public void setNote(String note) {
	// this.note = note;
	// }
	// /**
	// * @return Returns the seqNum.
	// */
	// public String getSeqNum() {
	// return seqNum;
	// }
	// /**
	// * @param seqNum The seqNum to set.
	// */
	// public void setSeqNum(String seqNum) {
	// this.seqNum = seqNum;
	// }
	// /**
	// * @return Returns the spec.
	// */
	// public String getSpec() {
	// return spec;
	// }
	// /**
	// * @param spec The spec to set.
	// */
	// public void setSpec(String spec) {
	// this.spec = spec;
	// }
	// /**
	// * @return Returns the unit.
	// */
	// public Unit getUnit() {
	// return unit;
	// }
	// /**
	// * @param unit The unit to set.
	// */
	// public void setUnit(Unit unit) {
	// this.unit = unit;
	// }
	/**
	 * @return Returns the weigthUnitGene.
	 */
	public Double getWeigthUnitGene() {
		return weigthUnitGene;
	}

	/**
	 * @param weigthUnitGene
	 *            The weigthUnitGene to set.
	 */
	public void setWeigthUnitGene(Double weigthUnitGene) {
		this.weigthUnitGene = weigthUnitGene;
	}

	// /**
	// * @return Returns the declarePrice.
	// */
	// public Double getDeclarePrice() {
	// return declarePrice;
	// }
	// /**
	// * @param declarePrice The declarePrice to set.
	// */
	// public void setDeclarePrice(Double declarePrice) {
	// this.declarePrice = declarePrice;
	// }
	/**
	 * @return Returns the declarePriceMo.
	 */
	public Double getDeclarePriceMo() {
		return declarePriceMo;
	}

	/**
	 * @param declarePriceMo
	 *            The declarePriceMo to set.
	 */
	public void setDeclarePriceMo(Double declarePriceMo) {
		this.declarePriceMo = declarePriceMo;
	}

	/**
	 * @return Returns the maxApprSpace.
	 */
	public Double getMaxApprSpace() {
		return maxApprSpace;
	}

	/**
	 * @param maxApprSpace
	 *            The maxApprSpace to set.
	 */
	public void setMaxApprSpace(Double maxApprSpace) {
		this.maxApprSpace = maxApprSpace;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getFactoryPrice() {
		return factoryPrice;
	}

	public void setFactoryPrice(Double factoryPrice) {
		this.factoryPrice = factoryPrice;
	}

	public Double getUnitGrossWeight() {
		return unitGrossWeight;
	}

	public void setUnitGrossWeight(Double unitGrossWeight) {
		this.unitGrossWeight = unitGrossWeight;
	}

	public Double getUnitNetWeight() {
		return unitNetWeight;
	}

	public void setUnitNetWeight(Double unitNetWeight) {
		this.unitNetWeight = unitNetWeight;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public Integer getSendState() {
		return sendState;
	}

	public void setSendState(Integer sendState) {
		this.sendState = sendState;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double amount) {
		this.qty = amount;
	}

	public Double getFirstQty() {
		return firstQty;
	}

	public void setFirstQty(Double firstQty) {
		this.firstQty = firstQty;
	}
}
