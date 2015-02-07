package com.bestway.common.warning.entity;

import java.io.Serializable;

public class TempWarningItem implements Serializable {
	/**
	 * 任务名称
	 */
	private String	itemName;			// 任务名称
	/**
	 * 任务标识
	 */
	private Integer	warningItemFlag;	// 任务标识

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getWarningItemFlag() {
		return warningItemFlag;
	}

	public void setWarningItemFlag(Integer warningItemFlag) {
		this.warningItemFlag = warningItemFlag;
	}

}
