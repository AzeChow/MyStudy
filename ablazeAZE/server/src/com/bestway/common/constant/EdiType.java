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
public class EdiType implements Serializable {
	/**
	 * 经营范围
	 */
	public static final int EMS_EDI_TR = 1;                
	
	/**
	 * 归并关系（归并前部分）
	 */
	public static final int EMS_EDI_MERGER_BEFORE = 2;     
	
	/**
	 * 归并关系（归并后部分）
	 */
	public static final int EMS_EDI_MERGER_AFTER = 3;      
	
	/**
	 * 电子帐册（不包括单损耗）
	 */
	public static final int EMS_EDI_H2K = 4;               
	
	/**
	 * 电子帐册分册
	 */
	public static final int EMS_EDI_H2K_CLEFT = 5;         
	
	/**
	 * 电子帐册联网核查
	 */
	public static final int CHECK = 6;                     
	
	/**
	 * 电子帐册报核（不包括单损耗） 
	 */
    public static final int CANCEL = 7;                    
    
    /**
     * 报关单
     */
    public static final int CustomsDeclare = 8;            
    
    /**
     * 报关清单
     */
    public static final int CUSTOMS_BILL = 9;                   
}
