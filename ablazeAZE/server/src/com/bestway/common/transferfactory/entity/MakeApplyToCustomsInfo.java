/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.transferfactory.entity;

import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 生成中间表信息
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MakeApplyToCustomsInfo extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	// private AtcMergeBeforeComInfo atcMergeBeforeComInfo = null;
	// //报关清单归并前商品信息Id
	// private CustomsEnvelopCommodityInfo customsEnvelopCommodityInfo = null;
	// //关封商品信息Id
	// private TransferFactoryCommodityInfo transferFactoryCommodityInfo = null;
	// //结转商品信息Id
	// private Double transferQuantity = null; //已转数量
	/**
	 * 报关清单归并后商品信息Id
	 */
	private String mergeAfterCommInfoId = null;
	/**
	 * 结转商品信息Id
	 */
	private String transFactCommInfo = null; 

	public String getMergeAfterCommInfoId() {
		return mergeAfterCommInfoId;
	}

	public void setMergeAfterCommInfoId(String mergeAfterCommInfoId) {
		this.mergeAfterCommInfoId = mergeAfterCommInfoId;
	}

	public String getTransFactCommInfo() {
		return transFactCommInfo;
	}

	public void setTransFactCommInfo(String transFactCommInfo) {
		this.transFactCommInfo = transFactCommInfo;
	}

	// /**
	// * @return Returns the atcMergeBeforeComInfo.
	// */
	// public AtcMergeBeforeComInfo getAtcMergeBeforeComInfo() {
	// return atcMergeBeforeComInfo;
	// }
	// /**
	// * @param atcMergeBeforeComInfo The atcMergeBeforeComInfo to set.
	// */
	// public void setAtcMergeBeforeComInfo(
	// AtcMergeBeforeComInfo atcMergeBeforeComInfo) {
	// this.atcMergeBeforeComInfo = atcMergeBeforeComInfo;
	// }
	// /**
	// * @return Returns the customsEnvelopCommodityInfo.
	// */
	// public CustomsEnvelopCommodityInfo getCustomsEnvelopCommodityInfo() {
	// return customsEnvelopCommodityInfo;
	// }
	// /**
	// * @param customsEnvelopCommodityInfo The customsEnvelopCommodityInfo to
	// set.
	// */
	// public void setCustomsEnvelopCommodityInfo(
	// CustomsEnvelopCommodityInfo customsEnvelopCommodityInfo) {
	// this.customsEnvelopCommodityInfo = customsEnvelopCommodityInfo;
	// }
	// /**
	// * @return Returns the transferFactoryCommodityInfo.
	// */
	// public TransferFactoryCommodityInfo getTransferFactoryCommodityInfo() {
	// return transferFactoryCommodityInfo;
	// }
	// /**
	// * @param transferFactoryCommodityInfo The transferFactoryCommodityInfo to
	// set.
	// */
	// public void setTransferFactoryCommodityInfo(
	// TransferFactoryCommodityInfo transferFactoryCommodityInfo) {
	// this.transferFactoryCommodityInfo = transferFactoryCommodityInfo;
	// }
	// /**
	// * @return Returns the transferQuantity.
	// */
	// public Double getTransferQuantity() {
	// return transferQuantity;
	// }
	// /**
	// * @param transferQuantity The transferQuantity to set.
	// */
	// public void setTransferQuantity(Double transferQuantity) {
	// this.transferQuantity = transferQuantity;
	// }
}