/*
 * Created on 2004-9-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.dataexport.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 自动导出线程记录
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ThreadData extends BaseScmEntity {
	private static final long	serialVersionUID	= CommonUtils
															.getSerialVersionUID();
	/**
	 * 文本
	 */
	public static final int		TEXT_TYPE			= 0;							// 类型
/**
 * DB
 */
	public static final int		DB_TYPE				= 1;							// 类型

	/**
	 * 状态
	 */
	private Boolean				isStart				= false;
	/**
	 * 类型
	 * TEXT_TYPE			= 0;	文本
	 * DB_TYPE				= 1;	DB
	 */
	private Integer				type;												

	// （文本，DB）

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Boolean getIsStart() {
		return isStart;
	}

	public void setIsStart(Boolean isStart) {
		this.isStart = isStart;
	}

}
