/*
 * Created on 2005-7-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.constant;

import java.io.Serializable;

/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public final class InvoiceState implements Serializable{
	public static final int DRAFT=0;//领用	
	public static final int USED=1;//已使用
	public static final int CANCELED=2;//作废
	public static final int CANCEL_AFTER_VERIFICATION=3;//核销
}
