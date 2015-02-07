package com.bestway.client.selfcheck.report;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.common.MaterielType;

/**
 * 自我核查帮助类
 * @author chensir
 *
 */
public class CheckMysqlUntil {
	
	public static String MATERIEL = "料件";
	public static String PRODUCE = "成品";
	public static String HALF_PRODUCE = "半成品";
	public static String CURR_PRODUCE = "在产品";
	public static String OUT_PRODUCE = "外发";
	public static String BAD_PRODUCE = "残次品";
	/**
	 *  根据单据类型判断物料类别
	 * @param bill
	 * @return
	 */
	public static String getType(BillDetail bill,String billDetail){
		return getType(bill.getBillMaster().getBillType().getCode(),billDetail);
	}
	
	/**
	 * 根据单据代码判断物料类别
	 * 只判断在产品与外发
	 * @param code
	 * @return
	 */
	public static String getType(String code,String billDetail){
		if(code == null || billDetail == null){
			return "";
		}
		if(MaterielType.MATERIEL.equals(billDetail)){
			return MATERIEL;
		}
		if(MaterielType.FINISHED_PRODUCT.equals(billDetail)){
			return PRODUCE;
		}
		if(MaterielType.BAD_PRODUCT.equals(billDetail)){
			return BAD_PRODUCE;
		}
		if("currentTotal".equals(billDetail)){
			if("1101".equals(code)){
				return MATERIEL;
			}
			if("2002".equals(code)){
				return PRODUCE;
			}
			return HALF_PRODUCE;
		}
		if("outSource".equals(billDetail)){
			if(code.startsWith("1")){
				return MATERIEL;
			}else if(code.startsWith("2")){
				return PRODUCE;
			}
			return HALF_PRODUCE;
		}
		return "";
	}
}
