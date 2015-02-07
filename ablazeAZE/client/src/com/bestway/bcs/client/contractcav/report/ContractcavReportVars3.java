/*
 * Created on 2005-5-27
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractcav.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.contractcav.entity.ContractExgCav;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ContractcavReportVars3 {

	private static ContractcavReportVars3 c = null;

	private static List<ContractExgCav> contractExgCavList = new ArrayList<ContractExgCav>();

	private static Map<Integer, String> contractExgCavAmountAndUnitMap = new HashMap<Integer, String>();

	private static Map<Integer, String> contractExgCavSeqNumMap = new HashMap<Integer, String>();

	private ContractcavReportVars3() {

	}

	public static ContractcavReportVars3 getInstance() {
		if (c == null) {
			c = new ContractcavReportVars3();
		}
		return c;
	}

	/**
	 * 获得amount and unit
	 * 
	 * @param index
	 * @return
	 */
	public String getAmountAndUnit(int index) {
		if (ContractcavReportVars3.contractExgCavAmountAndUnitMap.size() > 0
				|| index < ContractcavReportVars3.contractExgCavAmountAndUnitMap.size()) {
			return ContractcavReportVars3.contractExgCavAmountAndUnitMap.get(index);
		}
		return "";
	}

	/**
	 * 获得SeqNum
	 * 
	 * @param index
	 * @return
	 */
	public String getSeqNum(int index) {
		if (ContractcavReportVars3.contractExgCavSeqNumMap.size() > 0
				|| index < ContractcavReportVars3.contractExgCavSeqNumMap.size()) {
			return ContractcavReportVars3.contractExgCavSeqNumMap.get(index);
		}
		return "";
	}

	public static void setContractExgCavList(
			List<ContractExgCav> contractExgCavList) {
		ContractcavReportVars3.contractExgCavList = contractExgCavList;
		for (int i = 0; i < ContractcavReportVars3.contractExgCavList.size(); i++) {
			ContractExgCav contractExgCav = ContractcavReportVars3.contractExgCavList
			.get(i);
			String value = contractExgCav.getDirectExport()
			+ (contractExgCav.getUnit() == null ? ""
					: ("/"+contractExgCav.getUnit().getName()));
			contractExgCavAmountAndUnitMap.put(i, value);
			contractExgCavSeqNumMap.put(i, contractExgCav.getSeqNum().toString());
		}
	}
}
