/*
 * Created on 2004-7-2
 * 内部归并
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.innermerge.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DzscCustomsModifyState;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 存放电子手册商品归并资料
 * 
 * @author bsway
 */
public class DzscTenInnerMerge extends BaseScmEntity {

	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 商品归并资料
	 */
	private DzscFourInnerMerge dzscFourInnerMerge = null;

	/**
	 * 十位编码序号
	 */
	private Integer tenSeqNum;

	/**
	 * 十位商品编码<第二层>
	 */
	private Complex tenComplex;

	/**
	 * 十位商品名称tenPtName
	 */
	private String tenPtName;

	/**
	 * 十位商品规格
	 */
	private String tenPtSpec;

	/**
	 * 十位商品单位
	 */
	private Unit tenUnit;

	/**
	 * 加工费单价
	 */
	private Double machinPrice;
	
	/**
	 * 加工费比例
	 */
	private Double machinScale;

	/**
	 * 法定单位比例因子
	 */
	private Double legalUnitGene;

	/**
	 * 第二法定单位比例因子
	 */
	private Double legalUnit2Gene;

	/**
	 * 重量单位比例因子
	 */
	private Double weigthUnitGene;

    /**
	 * 修改标志(归并后10码)
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String tenModifyMark;

	public DzscFourInnerMerge getDzscFourInnerMerge() {
		return dzscFourInnerMerge;
	}

	public void setDzscFourInnerMerge(DzscFourInnerMerge dzscFourInnerMerge) {
		this.dzscFourInnerMerge = dzscFourInnerMerge;
	}

	/**
	 * 获取十位商品编码<第二层>
	 * 
	 * @return tenComplex 十位商品编码<第二层>
	 */
	public Complex getTenComplex() {
		return tenComplex;
	}

	/**
	 * 设置十位商品编码<第二层>
	 * 
	 * @param tenComplex
	 *            十位商品编码<第二层>
	 */
	public void setTenComplex(Complex tenComplex) {
		this.tenComplex = tenComplex;
	}

	// /**
	// * @return Returns the spec.
	// */
	// public String getSpec() {
	// return spec;
	// }
	//
	// /**
	// * @param spec
	// * The spec to set.
	// */
	// public void setSpec(String spec) {
	// this.spec = spec;
	// }

	// public Boolean getIsDeleted() {
	// return isDeleted;
	// }
	//
	// public void setIsDeleted(Boolean isDeleted) {
	// this.isDeleted = isDeleted;
	// }

	/**
	 * 获取十位商品名称
	 * 
	 * @return tenPtName 四位商品名称
	 */
	public String getTenPtName() {
		return tenPtName;
	}

	/**
	 * 设置十位商品名称
	 * 
	 * @param tenPtName
	 *            四位商品名称
	 */
	public void setTenPtName(String tenPtName) {
		this.tenPtName = tenPtName;
	}

	/**
	 * 获取十位商品规格
	 * 
	 * @return tenPtSpec 四位商品规格
	 */
	public String getTenPtSpec() {
		return tenPtSpec;
	}

	/**
	 * 设置十位商品规格
	 * 
	 * @param tenPtSpec
	 *            四位商品规格
	 */
	public void setTenPtSpec(String tenPtSpec) {
		this.tenPtSpec = tenPtSpec;
	}

	/**
	 * 获取十位编码序号
	 * 
	 * @return tenSeqNum 十位编码序号
	 */
	public Integer getTenSeqNum() {
		return tenSeqNum;
	}

	/**
	 * 设置十位编码序号
	 * 
	 * @param tenseqNum
	 *            十位编码序号
	 */
	public void setTenSeqNum(Integer tenseqNum) {
		this.tenSeqNum = tenseqNum;
	}

	/**
	 * 获取十位商品单位
	 * 
	 * @return tenUnit 十位商品单位
	 */
	public Unit getTenUnit() {
		return tenUnit;
	}

	/**
	 * 设置十位商品单位
	 * 
	 * @param tenUnit
	 *            十位商品单位
	 */
	public void setTenUnit(Unit tenUnit) {
		this.tenUnit = tenUnit;
	}

	/**
	 * 获取serialVersionUID
	 * 
	 * @return serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * 
	 * 获取状态
	 * 
	 * @return String 状态
	 */
	public String getTenModifyMarkDesc() {
		String state = this.tenModifyMark;
		if (state.equals(ModifyMarkState.UNCHANGE)) {
			return "未修改";
		} else if (state.equals(ModifyMarkState.ADDED)) {
			return "新增";
		} else if (state.equals(ModifyMarkState.DELETED)) {
			return "删除";
		} else if (state.equals(ModifyMarkState.MODIFIED)) {
			return "已修改";
		}
		return "";
	}

	public String getTenModifyMark() {
		return tenModifyMark;
	}

	public void setTenModifyMark(String tenModifyMark) {
		this.tenModifyMark = tenModifyMark;
	}

	/**
	 * 获取修改标记
	 * 
	 * @return modifyMark 修改标记
	 */
	public String getCustomsModifyMark() {
		return DzscCustomsModifyState.getCustomsModifyState(this.tenModifyMark);
	}

	/**
	 * 附加编码
	 * 
	 * @return
	 */
	public String getTenComplexSCode() {
		if (this.getTenComplex() == null
				|| this.getTenComplex().getCode().length() <= 8) {
			return "";
		} else {
			return this.getTenComplex().getCode().substring(8,
					this.getTenComplex().getCode().length());
		}
	}

	public Double getMachinPrice() {
		return machinPrice;
	}

	public void setMachinPrice(Double machinPrice) {
		this.machinPrice = machinPrice;
	}

	public Double getLegalUnit2Gene() {
		return legalUnit2Gene;
	}

	public void setLegalUnit2Gene(Double legalUnit2Gene) {
		this.legalUnit2Gene = legalUnit2Gene;
	}

	public Double getLegalUnitGene() {
		return legalUnitGene;
	}

	public void setLegalUnitGene(Double legalUnitGene) {
		this.legalUnitGene = legalUnitGene;
	}

	public Double getWeigthUnitGene() {
		return weigthUnitGene;
	}

	public void setWeigthUnitGene(Double weigthUnitGene) {
		this.weigthUnitGene = weigthUnitGene;
	}

	public Double getMachinScale() {
		return machinScale;
	}

	public void setMachinScale(Double machinScale) {
		this.machinScale = machinScale;
	}
	
	
}