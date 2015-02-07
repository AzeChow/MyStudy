package com.bestway.bcs.verification.entity;

import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.common.BaseScmEntity;

/**
 * 大类别与十位海关编码对照表
 * @author xc
 * 
 */
public class VFCategoryBcsTenInnerMerge extends BaseScmEntity{

	/**
	 * 大类别
	 */
	private VFCategory category;
	/**
	 * 十位海关编码
	 */
	private BcsTenInnerMerge bcsTenInnerMerge;

	public VFCategory getCategory() {
		return category;
	}

	public void setCategory(VFCategory category) {
		this.category = category;
	}

	public BcsTenInnerMerge getBcsTenInnerMerge() {
		return bcsTenInnerMerge;
	}

	public void setBcsTenInnerMerge(BcsTenInnerMerge bcsTenInnerMerge) {
		this.bcsTenInnerMerge = bcsTenInnerMerge;
	}
	
}
