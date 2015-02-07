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
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SendState implements Serializable {
    /**
     * 未发送
     */
	public static final String NOT_SEND = "0";    
    /**
     * 准备发送
     */
	public static final String WAIT_SEND = "1";
    /**
     * 已经发送
     */
	public static final String SEND = "2";
    
	
}
