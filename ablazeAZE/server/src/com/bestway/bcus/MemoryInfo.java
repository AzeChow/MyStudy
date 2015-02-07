/*
 * Created on 2005-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus;

import java.io.Serializable;

/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemoryInfo implements Serializable {
	private String maxMemory;
	private String totalMemory;
	private String usedMemory;
	private String freeMemory;
	/**
	 * @return Returns the freeMemory.
	 */
	public String getFreeMemory() {
		return freeMemory;
	}
	/**
	 * @return Returns the maxMemory.
	 */
	public String getMaxMemory() {
		return maxMemory;
	}
	/**
	 * @return Returns the totalMemory.
	 */
	public String getTotalMemory() {
		return totalMemory;
	}
	/**
	 * @return Returns the usedMemory.
	 */
	public String getUsedMemory() {
		return usedMemory;
	}
	/**
	 * @param freeMemory The freeMemory to set.
	 */
	public void setFreeMemory(String freeMemory) {
		this.freeMemory = freeMemory;
	}
	/**
	 * @param maxMemory The maxMemory to set.
	 */
	public void setMaxMemory(String maxMemory) {
		this.maxMemory = maxMemory;
	}
	/**
	 * @param totalMemory The totalMemory to set.
	 */
	public void setTotalMemory(String totalMemory) {
		this.totalMemory = totalMemory;
	}
	/**
	 * @param usedMemory The usedMemory to set.
	 */
	public void setUsedMemory(String usedMemory) {
		this.usedMemory = usedMemory;
	}
}
