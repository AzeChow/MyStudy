/*
 * Created on 2008-11-19
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
 * @author lm
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ContractcavReportVars2 {

	private static ContractcavReportVars2 c = null;

	private static List<ContractExgCav> contractExgCavList = new ArrayList<ContractExgCav>();

	private static Map<Integer, String> contractExgCavNameMap = new HashMap<Integer, String>();

	private static Map<Integer, String> contractExgCavSeqNumMap = new HashMap<Integer, String>();

	private ContractcavReportVars2() {

	}

	public static ContractcavReportVars2 getInstance() {
		if (c == null) {
			c = new ContractcavReportVars2();
		}
		return c;
	}

	/**
	 * 获得amount and unit
	 * 
	 * @param index
	 * @return
	 */
	public String getProductName(int index) {
		if (ContractcavReportVars2.contractExgCavNameMap.size() > 0
				|| index < ContractcavReportVars2.contractExgCavNameMap.size()) {
			return ContractcavReportVars2.contractExgCavNameMap.get(index);
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
		if (ContractcavReportVars2.contractExgCavSeqNumMap.size() > 0
				|| index < ContractcavReportVars2.contractExgCavSeqNumMap.size()) {
			return ContractcavReportVars2.contractExgCavSeqNumMap.get(index);
		}
		return "";
	}

	public static void setContractExgCavList(
			List<ContractExgCav> contractExgCavList) {
		ContractcavReportVars2.contractExgCavList = contractExgCavList;
		for (int i = 0; i < ContractcavReportVars2.contractExgCavList.size(); i++) {
			ContractExgCav contractExgCav = ContractcavReportVars2.contractExgCavList
					.get(i);
			String value = contractExgCav.getName();
			contractExgCavNameMap.put(i, value);
			contractExgCavSeqNumMap.put(i, contractExgCav.getSeqNum().toString());
		}
	}
}
