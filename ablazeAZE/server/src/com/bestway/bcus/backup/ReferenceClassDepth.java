/*
 * Created on 2005-1-12
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.backup;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ReferenceClassDepth implements Comparable, Serializable {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
	private String className = null;

	private int depth = 0;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int compareTo(Object arg0) {
		ReferenceClassDepth referenceClassDepth = (ReferenceClassDepth) arg0;
		return referenceClassDepth.getDepth() - this.depth;
	}
}