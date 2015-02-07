package com.bestway.bcus.enc.entity;

import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.common.BaseEntity;
import com.bestway.common.CommonUtils;

/**
 * @author fhz
 * 
 * 大小清单报表（申请单转清单转报关单）
 */
public class TempRequestTOApplyTOCustomsReport extends BaseEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 进出口申请单表体
	 */
	private ImpExpCommodityInfo impExpCommodityInfo;

	/**
	 * 报关清单表体
	 */
	private AtcMergeBeforeComInfo atcMergeBeforeComInfo;

	/**
	 * 报关单表体
	 */
	private CustomsDeclarationCommInfo customsCommInfo;

	/**
	 * @return the atcMergeBeforeComInfo
	 */
	public AtcMergeBeforeComInfo getAtcMergeBeforeComInfo() {
		return atcMergeBeforeComInfo;
	}

	/**
	 * @param atcMergeBeforeComInfo the atcMergeBeforeComInfo to set
	 */
	public void setAtcMergeBeforeComInfo(AtcMergeBeforeComInfo atcMergeBeforeComInfo) {
		this.atcMergeBeforeComInfo = atcMergeBeforeComInfo;
	}

	/**
	 * @return the customsCommInfo
	 */
	public CustomsDeclarationCommInfo getCustomsCommInfo() {
		return customsCommInfo;
	}

	/**
	 * @param customsCommInfo the customsCommInfo to set
	 */
	public void setCustomsCommInfo(CustomsDeclarationCommInfo customsCommInfo) {
		this.customsCommInfo = customsCommInfo;
	}

	/**
	 * @return the impExpCommodityInfo
	 */
	public ImpExpCommodityInfo getImpExpCommodityInfo() {
		return impExpCommodityInfo;
	}

	/**
	 * @param impExpCommodityInfo the impExpCommodityInfo to set
	 */
	public void setImpExpCommodityInfo(ImpExpCommodityInfo impExpCommodityInfo) {
		this.impExpCommodityInfo = impExpCommodityInfo;
	}

}
