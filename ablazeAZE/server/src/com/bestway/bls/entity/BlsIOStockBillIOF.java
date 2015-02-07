package com.bestway.bls.entity;
/**
 * 控制进出仓标志位类
 * @author hw
 *
 */
public class BlsIOStockBillIOF {

	/**
	 * 入仓
	 */
	public static final String IMPORT = "I"; // 

	/**
	 * 出仓
	 */
	public static final String EXPORT = "O"; // 

	/**
	 * 转换进出仓标志位
	 * @param value
	 * @return
	 */
	public static final String getImpExpFlagSpec(String value) {
		String returnValue = "";
		if (BlsIOStockBillIOF.IMPORT.equals(value)) {
			returnValue = "进仓";
		} else if (BlsIOStockBillIOF.EXPORT.equals(value)) {
			returnValue = "出仓";
		} 
		return returnValue;
	}
}




