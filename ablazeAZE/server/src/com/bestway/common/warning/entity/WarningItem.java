package com.bestway.common.warning.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 预警管理信息
 * @author Administrator
 *
 */
public class WarningItem extends BaseScmEntity {
	private static final long	serialVersionUID	= CommonUtils
															.getSerialVersionUID();
	/**
	 * 任务名称
	 */
	private String				itemName;											// 任务名称
	/**
	 * 任务标识
	 */
	private Integer				warningItemFlag;									// 任务标识
	/**
	 * 每日,每周,每月,间隔时间
	 */
	private Integer				excuteKind;										// 每日,每周,每月,间隔时间
	/**
	 * 运行日期
	 */
	private String				excuteday;											// 运行日期
	/**
	 * 运行时间
	 */
	private String				excutetime;										// 运行时间
	/**
	 * 数量
	 */
	private Double				amount;											// 数量
	/**
	 *  任务说明
	 */
	private String				note;												// 任务说明

	public String getExcuteday() {
		return excuteday;
	}

	public void setExcuteday(String excuteday) {
		this.excuteday = excuteday;
	}

	public Integer getExcuteKind() {
		return excuteKind;
	}

	public void setExcuteKind(Integer excuteKind) {
		this.excuteKind = excuteKind;
	}

	public String getExcutetime() {
		return excutetime;
	}

	public void setExcutetime(String excutetime) {
		this.excutetime = excutetime;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getWarningItemFlag() {
		return warningItemFlag;
	}

	public void setWarningItemFlag(Integer warningItemFlag) {
		this.warningItemFlag = warningItemFlag;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	
}
