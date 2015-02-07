package com.bestway.bcus.manualdeclare.entity;

import java.io.Serializable;

import com.bestway.bcus.innermerge.entity.InnerMergeData;

public class TempOfInnerMergeData implements Serializable {

	/**
	 * 归并后新增或变更
	 */
	private String changeTypeAfter=null;
	/**
	 * 归并前新增或变更
	 */
	private String changeTypeBefore=null;
	/**
	 * 内部归并资料
	 */
	private InnerMergeData data=null;
	public String getChangeTypeAfter() {
		return changeTypeAfter;
	}
	public void setChangeTypeAfter(String changeTypeAfter) {
		this.changeTypeAfter = changeTypeAfter;
	}
	
	public String getChangeTypeBefore() {
		return changeTypeBefore;
	}
	public void setChangeTypeBefore(String changeTypeBefore) {
		this.changeTypeBefore = changeTypeBefore;
	}
	public InnerMergeData getData() {
		return data;
	}
	public void setData(InnerMergeData data) {
		this.data = data;
	}
	
}
