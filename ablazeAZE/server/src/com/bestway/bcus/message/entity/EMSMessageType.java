package com.bestway.bcus.message.entity;
/**
 * 
 * 报文类型规范
 * @author chl
 * @version 1.0
 * @createDate 2013-10-8
 */
public class EMSMessageType implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**===============企业->数据中心==============**/
	/**==================报文发送=================**/
	/**
	 * 经营范围备案
	 */
	public static final String EMS211 = "EMS211";
	/**
	 * 归并关系备案(归并前部分)
	 */
	public static final String EMS212 = "EMS212";
	/**
	 * 归并关系备案(归并后部分)
	 */
	public static final String EMS215 = "EMS215";
	/**
	 * 电子帐册备案(不包括单损耗)
	 */
	public static final String EMS213 = "EMS213";
	/**
	 * 电子帐册分册备案
	 */
	public static final String EMS214 = "EMS214";
	/**
	 * 经营范围变更
	 */
	public static final String EMS221 = "EMS221";
	/**
	 * 归并关系变更(归并前部分)
	 */
	public static final String EMS222 = "EMS222";
	/**
	 * 归并关系变更(归并后部分)
	 */
	public static final String EMS225 = "EMS225";
	/**
	 * 电子帐册变更(不包括单损耗)
	 */
	public static final String EMS223 = "EMS223";
	/**
	 * 电子帐册分册变更
	 */
	public static final String EMS224 = "EMS224";
	/**
	 * 电子帐册单损耗变更
	 */
	public static final String EMS226 = "EMS226";
	/**
	 * 电子帐册中期联网核查
	 */
	public static final String EMS230 = "EMS230";
	/**
	 * 电子帐册报核
	 */
	public static final String EMS231 = "EMS231";
	/**
	 * 电子帐册实时核查
	 */
	public static final String EMS232 = "EMS232";
	
	
	/**===============数据中心->企业==============**/
	/**==================接收回执=================**/
	/**
	 * 经营范围备案回执
	 */
	public static final String EMS311 = "EMS311";	
	/**
	 * 归并关系备案回执
	 */
	public static final String EMS312 = "EMS312";	
	/**
	 * 电子帐册备案回执
	 */
	public static final String EMS313 = "EMS313";	
	/**
	 * 电子帐册分册备案回执
	 */
	public static final String EMS314 = "EMS314";	
	/**
	 * 经营范围变更回执
	 */
	public static final String EMS321 = "EMS321";	
	/**
	 * 归并关系变更回执
	 */
	public static final String EMS322 = "EMS322";	
	/**
	 * 电子帐册变更回执
	 */
	public static final String EMS323 = "EMS323";	
	/**
	 * 电子帐册分册变更回执
	 */
	public static final String EMS324 = "EMS324";	
	/**
	 * 电子帐册中期联网核查回执
	 */
	public static final String EMS330 = "EMS330";	
	/**
	 * 电子帐册报核回执
	 */
	public static final String EMS331 = "EMS331";	
	/**
	 * 电子帐册实时核查回执
	 */
	public static final String EMS332 = "EMS332";	

	
}
