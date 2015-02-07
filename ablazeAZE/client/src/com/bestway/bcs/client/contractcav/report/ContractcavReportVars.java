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
public class ContractcavReportVars {

	private static ContractcavReportVars c = null;

	private static List<ContractExgCav> contractExgCavList = new ArrayList<ContractExgCav>();

	private static Map<Integer, String> contractExgCavNameMap = new HashMap<Integer, String>();

	private static Map<Integer, String> contractExgCavSeqNumMap = new HashMap<Integer, String>();

	private ContractcavReportVars() {

	}

	public static ContractcavReportVars getInstance() {
		if (c == null) {
			c = new ContractcavReportVars();
		}
		return c;
	}

	/**
	 * 获得name and spec
	 * 
	 * @param index
	 * @return
	 */
	public String getNameAndSpec(int index) {
		if (ContractcavReportVars.contractExgCavNameMap.size() > 0
				|| index < ContractcavReportVars.contractExgCavNameMap.size()) {
			return ContractcavReportVars.contractExgCavNameMap.get(index);
		}
		return "";
	}

	/**
	 * 获得name and spec
	 * 
	 * @param index
	 * @return
	 */
	public String getSeqNum(int index) {
		if (ContractcavReportVars.contractExgCavSeqNumMap.size() > 0
				|| index < ContractcavReportVars.contractExgCavSeqNumMap.size()) {
			return ContractcavReportVars.contractExgCavSeqNumMap.get(index);
		}
		return "";
	}

	public static void setContractExgCavList(
			List<ContractExgCav> contractExgCavList) {
		ContractcavReportVars.contractExgCavList = contractExgCavList;
		for (int i = 0; i < ContractcavReportVars.contractExgCavList.size(); i++) {
			ContractExgCav contractExgCav = ContractcavReportVars.contractExgCavList
					.get(i);
			String value = contractExgCav.getName()
					+ (contractExgCav.getSpec() == null ? ""
							: ("/" + contractExgCav.getSpec()));
			contractExgCavNameMap.put(i, value);
			contractExgCavSeqNumMap.put(i, contractExgCav.getSeqNum().toString());
		}
	}
}
