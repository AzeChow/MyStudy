/*
 * Created on 2005-1-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common;

/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NotFoundDataException extends RuntimeException {
	public NotFoundDataException(String errorMsg){
		super("此数据已经被另一用户删除,"+errorMsg);
	}
}
