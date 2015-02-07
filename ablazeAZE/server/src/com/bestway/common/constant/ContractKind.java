/*
 * Created on 2005-3-31
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.constant;

import java.io.Serializable;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ContractKind implements Serializable {

	/**
	 * 外资内销产品
	 */
	public static final String FOREIGN_CAPITAL_SELL_PRODUCT = "A";

	/**
	 * 来料加工
	 */
	public static final String COME_MATERIEL_PROCESS = "B";

	/**
	 * 进料加工
	 */
	public static final String IMPORT_MATERIEL_PROCESS = "C";

	/**
	 * 外资设备
	 */
	public static final String FOREIGN_CAPITAL_MACHINE = "D";

	/**
	 * 保税仓
	 */
	public static final String BONDEN_WAREHOUSE = "P";

	/**
	 * 纸制手册电子化来料手册
	 */
	public static final String COME_MATERIEL_EMS = "U";

	/**
	 * 纸制手册电子化进料手册
	 */
	public static final String IMPORT_MATERIEL_EMS = "R";

	public static String getContractKindSpec(String type) {
		if (FOREIGN_CAPITAL_SELL_PRODUCT.equals(type)) {
			return "外资内销产品";
		} else if (COME_MATERIEL_PROCESS.equals(type)) {
			return "来料加工";
		} else if (IMPORT_MATERIEL_PROCESS.equals(type)) {
			return "进料加工";
		} else if (FOREIGN_CAPITAL_MACHINE.equals(type)) {
			return "外资设备";
		} else if (BONDEN_WAREHOUSE.equals(type)) {
			return "保税仓";
		} else if (COME_MATERIEL_EMS.equals(type)) {
			return "纸质手册电子化来料手册";
		} else if (IMPORT_MATERIEL_EMS.equals(type)) {
			return "纸质手册电子化进料手册";
		} else {
			return "未知类型";
		}
	}
}
