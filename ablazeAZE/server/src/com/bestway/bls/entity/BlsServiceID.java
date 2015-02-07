package com.bestway.bls.entity;

/**
 * 保税物流系统海关申报 serviceId
 * 
 * @author Administrator
 * 
 */
public class BlsServiceID implements java.io.Serializable {
	/**
	 * 仓单申报：
	 */
	public final static String MANIFEST_DECLARE = "soa://hp.customs.gov.cn/Manifest/Manifest/ManifestDeclare";//"soa://hp.customs.gov.cn/Mnl/Manifest/ManifestDeclare";
	/**
	 * 货到通知
	 */
	public final static String FREIGHTAGE_DECLARE = "soa://hp.customs.gov.cn/Manifest/Manifest/FreightageDeclare";//"soa://hp.customs.gov.cn/Mnl/Manifest/FreightageDeclare";
	/**
	 * 核销申报：
	 */
	public final static String COLLATEBIND_DECLARE = "soa://hp.customs.gov.cn/Manifest/DEC/CollateBindDeclare";//"soa://hp.customs.gov.cn/Mnl/DEC/CollateBindDeclare";
	/**
	 * 仓单作废申请：
	 */
	public final static String BILLCANCEL_APPLY = "soa://hp.customs.gov.cn/Mnl/Manifest/BillCancelApply";
	/**
	 * 换货申请申请：
	 */
	public final static String CARGOREPLACE_APPLY = "soa://hp.customs.gov.cn/Mnl/Manifest/CargoReplaceApply";
	/**
	 * 增值性加工申请：
	 */
	public final static String PROCESS_APPLY = "soa://hp.customs.gov.cn/Mnl/Manifest/ProcessApply";
	/**
	 * 仓转仓申请：
	 */
	public final static String TRN_APPLY = "soa://hp.customs.gov.cn/Mnl/Warehouse/TrnApply";
	/**
	 * 弃货申请：
	 */
	public final static String CARGOABANDON_APPLY = "soa://hp.customs.gov.cn/Mnl/Manifest/CargoAbandonApply";
	/**
	 * 保税物料、装配货物进口申请：
	 */
	public final static String IMPORT_APPLY = "soa://hp.customs.gov.cn/Mnl/Warehouse/ImportApply";
	/**
	 * 状态查询
	 */
	public final static String GET_STATUS = "soa://hp.customs.gov.cn/System/GetStatus";
	
	/**
	 * 库存查询
	 */
	public final static String QUERY_STOCK = "soa://hp.customs.gov.cn/Manifest/Objects/QueryStock";//"soa://hp.customs.gov.cn/Mnl/Objects/QueryStock";
	/**
	 * 仓单查询
	 */
	public final static String QUERY_BILL = "soa://hp.customs.gov.cn/Manifest/Objects/QueryBill";//"soa://hp.customs.gov.cn/Mnl/Objects/QueryBill";
	/**
	 * 服务查询
	 */
	public final static String QUERY_SERVICE_ID = "soa://hp.customs.gov.cn/Manifest/Objects/QueryServiceID";//"soa://hp.customs.gov.cn/Mnl/Objects/QueryServiceID";
	
	/**
	 * 根据ID返回类型
	 * @param serviceID
	 * @return
	 */
	public final static String getServiceTypeByID(String serviceID){
		if(BlsServiceID.MANIFEST_DECLARE.equalsIgnoreCase(serviceID)){
			return BlsServiceType.MANIFEST_DECLARE;
		}else if(BlsServiceID.FREIGHTAGE_DECLARE.equalsIgnoreCase(serviceID)){
			return BlsServiceType.FREIGHTAGE_DECLARE;
		}else if(BlsServiceID.COLLATEBIND_DECLARE.equalsIgnoreCase(serviceID)){
			return BlsServiceType.COLLATEBIND_DECLARE;
		}else if(BlsServiceID.BILLCANCEL_APPLY.equalsIgnoreCase(serviceID)){
			return BlsServiceType.BILLCANCEL_APPLY;
		}else if(BlsServiceID.CARGOREPLACE_APPLY.equalsIgnoreCase(serviceID)){
			return BlsServiceType.CARGOREPLACE_APPLY;
		}else if(BlsServiceID.PROCESS_APPLY.equalsIgnoreCase(serviceID)){
			return BlsServiceType.PROCESS_APPLY;
		}else if(BlsServiceID.TRN_APPLY.equalsIgnoreCase(serviceID)){
			return BlsServiceType.TRN_APPLY;
		}else if(BlsServiceID.CARGOABANDON_APPLY.equalsIgnoreCase(serviceID)){
			return BlsServiceType.CARGOABANDON_APPLY;
		}else if(BlsServiceID.IMPORT_APPLY.equalsIgnoreCase(serviceID)){
			return BlsServiceType.IMPORT_APPLY;
		}else if(BlsServiceID.GET_STATUS.equalsIgnoreCase(serviceID)){
			return BlsServiceType.GET_STATUS;
		}else if(BlsServiceID.QUERY_STOCK.equalsIgnoreCase(serviceID)){
			return BlsServiceType.QUERY_STOCK;
		}else if(BlsServiceID.QUERY_BILL.equalsIgnoreCase(serviceID)){
			return BlsServiceType.QUERY_BILL;
		}else if(BlsServiceID.QUERY_SERVICE_ID.equalsIgnoreCase(serviceID)){
			return BlsServiceType.QUERY_SERVICE_ID;
		}else{
			return "";
		}
	}
}
