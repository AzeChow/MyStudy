/*
 * Created on 2005-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MemoryInfoFactory implements Serializable {
	private static MemoryInfoFactory factory=null;
	
	public static MemoryInfoFactory getInstance(){
		if(factory==null){
			factory=new MemoryInfoFactory();
		}
		return factory;
	}
	
	public MemoryInfo getMemoryInfo(){
		MemoryInfo memoryInfo=new MemoryInfo();
		Runtime runtime = Runtime.getRuntime();
		memoryInfo.setMaxMemory(formatDecimal(runtime.maxMemory() / 1048576.0)
				+ "MB");
		memoryInfo.setTotalMemory(formatDecimal(runtime.totalMemory() / 1048576.0)
		+ "MB");
		memoryInfo.setUsedMemory(formatDecimal(((runtime.totalMemory() - runtime
				.freeMemory()) / 1048576.0)) + "MB");
		memoryInfo.setFreeMemory(formatDecimal(runtime.freeMemory() / 1048576.0)
				+ "MB");
		return memoryInfo;
	}
	
	private String formatDecimal(double f) {
		BigDecimal b = new BigDecimal(f);
		double df = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		return String.valueOf(df);
	}
}
