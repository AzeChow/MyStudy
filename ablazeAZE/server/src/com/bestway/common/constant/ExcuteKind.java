/*
 * Created on 2004-7-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.constant;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ExcuteKind implements Serializable {

	private static final long	serialVersionUID	= 1L;
	/** 每日 */
	public static final int		DAY					= 0;
	/** 每周 */
	public static final int		WEEK				= 1;
	/** 每月 */
	public static final int		MONTH				= 2;
	/** 间隔时间 */
	public static final int		INTERVAL			= 3;
	/** 时间戳 */
	public static final int		TIMESTAMP			= 4;
	/** 手动 */
	public static final int		MANUAL				= 5;

}
