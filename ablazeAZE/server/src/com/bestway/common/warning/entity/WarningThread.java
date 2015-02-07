package com.bestway.common.warning.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 预警项目列表
 * @author Administrator
 *
 */
public class WarningThread extends BaseScmEntity {
	private static final long	serialVersionUID	= CommonUtils
															.getSerialVersionUID();

	/** 预警项目 */
	public static final int		WARNING_ITEM		= 0;
	/** 计划任务 */
	public static final int		PLAN_TASK			= 1;
	/**
	 * 类型
	 */
	private Integer				type				= WARNING_ITEM;
	/**
	 * 状态
	 */
	private Boolean				isStart				= false;
	
	public Boolean getIsStart() {
		return isStart;
	}
	public void setIsStart(Boolean isStart) {
		this.isStart = isStart;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
