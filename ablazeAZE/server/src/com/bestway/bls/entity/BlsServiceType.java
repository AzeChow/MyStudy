package com.bestway.bls.entity;

/**
 * 保税物流系统海关申报 serviceId
 * 
 * @author Administrator
 * 
 */
public class BlsServiceType implements java.io.Serializable {
	/**
	 * 车次仓单申报
	 */
	public final static String MANIFEST_DECLARE = "0";
	/**
	 * 货到通知
	 */
	public final static String FREIGHTAGE_DECLARE = "1";
	/**
	 * 核销申报
	 */
	public final static String COLLATEBIND_DECLARE = "2";
	/**
	 * 仓单作废申请
	 */
	public final static String BILLCANCEL_APPLY = "3";
	/**
	 * 换货申请申请
	 */
	public final static String CARGOREPLACE_APPLY = "4";
	/**
	 * 增值性加工申请
	 */
	public final static String PROCESS_APPLY = "5";
	/**
	 * 仓转仓申请
	 */
	public final static String TRN_APPLY = "6";
	/**
	 * 弃货申请
	 */
	public final static String CARGOABANDON_APPLY = "7";
	/**
	 * 保税物料、装配货物进口申请
	 */
	public final static String IMPORT_APPLY = "8";
	/**
	 * 状态查询
	 */
	public final static String GET_STATUS = "9";
	/**
	 * 库存查询
	 */
	public final static String QUERY_STOCK = "10";
	/**
	 * 仓单查询
	 */
	public final static String QUERY_BILL = "11";
	/**
	 * 服务查询
	 */
	public final static String QUERY_SERVICE_ID = "12";
	/**
	 * 根据类型返回ID
	 * @param serviceType
	 * @return
	 */
	public final static String getServiceIdByType(String serviceType){
		if(BlsServiceType.MANIFEST_DECLARE.equals(serviceType)){
			return BlsServiceID.MANIFEST_DECLARE;
		}else if(BlsServiceType.FREIGHTAGE_DECLARE.equals(serviceType)){
			return BlsServiceID.FREIGHTAGE_DECLARE;
		}else if(BlsServiceType.COLLATEBIND_DECLARE.equals(serviceType)){
			return BlsServiceID.COLLATEBIND_DECLARE;
		}else if(BlsServiceType.BILLCANCEL_APPLY.equals(serviceType)){
			return BlsServiceID.BILLCANCEL_APPLY;
		}else if(BlsServiceType.CARGOREPLACE_APPLY.equals(serviceType)){
			return BlsServiceID.CARGOREPLACE_APPLY;
		}else if(BlsServiceType.PROCESS_APPLY.equals(serviceType)){
			return BlsServiceID.PROCESS_APPLY;
		}else if(BlsServiceType.TRN_APPLY.equals(serviceType)){
			return BlsServiceID.TRN_APPLY;
		}else if(BlsServiceType.CARGOABANDON_APPLY.equals(serviceType)){
			return BlsServiceID.CARGOABANDON_APPLY;
		}else if(BlsServiceType.IMPORT_APPLY.equals(serviceType)){
			return BlsServiceID.IMPORT_APPLY;
		}else if(BlsServiceType.GET_STATUS.equals(serviceType)){
			return BlsServiceID.GET_STATUS;
		}else if(BlsServiceType.QUERY_STOCK.equals(serviceType)){
			return BlsServiceID.QUERY_STOCK;
		}else if(BlsServiceType.QUERY_BILL.equals(serviceType)){
			return BlsServiceID.QUERY_BILL;
		}else if(BlsServiceType.QUERY_SERVICE_ID.equals(serviceType)){
			return BlsServiceID.QUERY_SERVICE_ID;
		}else{
			return "";
		}
	}
	/**
	 * 根据类型返回文字描述
	 * @param serviceType
	 * @return
	 */
	public final static String getServiceDescByType(String serviceType){
		if(BlsServiceType.MANIFEST_DECLARE.equals(serviceType)){
			return "车次仓单申报";
		}else if(BlsServiceType.FREIGHTAGE_DECLARE.equals(serviceType)){
			return "货到通知";
		}else if(BlsServiceType.COLLATEBIND_DECLARE.equals(serviceType)){
			return "核销申报";
		}else if(BlsServiceType.BILLCANCEL_APPLY.equals(serviceType)){
			return "仓单作废申请";
		}else if(BlsServiceType.CARGOREPLACE_APPLY.equals(serviceType)){
			return "换货申请申请";
		}else if(BlsServiceType.PROCESS_APPLY.equals(serviceType)){
			return "增值性加工申请";
		}else if(BlsServiceType.TRN_APPLY.equals(serviceType)){
			return "仓转仓申请";
		}else if(BlsServiceType.CARGOABANDON_APPLY.equals(serviceType)){
			return "弃货申请";
		}else if(BlsServiceType.IMPORT_APPLY.equals(serviceType)){
			return "保税物料、装配货物进口申请";
		}else if(BlsServiceType.GET_STATUS.equals(serviceType)){
			return "状态查询";
		}else if(BlsServiceType.QUERY_STOCK.equals(serviceType)){
			return "库存查询";
		}else if(BlsServiceType.QUERY_BILL.equals(serviceType)){
			return "仓单查询";
		}else if(BlsServiceType.QUERY_SERVICE_ID.equals(serviceType)){
			return "服务查询";
		}else{
			return "";
		}
	}
}
