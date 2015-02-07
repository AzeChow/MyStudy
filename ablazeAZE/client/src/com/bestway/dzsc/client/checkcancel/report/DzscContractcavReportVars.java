/*
 * Created on 2005-5-27
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.checkcancel.report;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.contractcav.entity.ContractExgCav;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.dzsc.checkcancel.entity.DzscContractExgCav;

/**
 * @author ls // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DzscContractcavReportVars {

	private static DzscContractcavReportVars c = null;
	private static List<DzscContractExgCav> contractExgCavList = new ArrayList<DzscContractExgCav>();
	private static Map<Integer, String> contractExgCavMap = new HashMap<Integer, String>();
	private static Map<Integer, Integer> contractExgCavSeqNumMap = new HashMap<Integer, Integer>();

	private DzscContractcavReportVars() {

	}

	public static DzscContractcavReportVars getInstance() {
		if (c == null) {
			c = new DzscContractcavReportVars();
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
		if (DzscContractcavReportVars.contractExgCavMap.size() > 0
				|| index < DzscContractcavReportVars.contractExgCavMap.size()) {
			Integer k = contractExgCavSeqNumMap.get(index);
			if (k == null) {
				return "";
			}
			if (DzscContractcavReportVars.contractExgCavList.size() > 0
					|| k < DzscContractcavReportVars.contractExgCavList.size()) {
				return DzscContractcavReportVars.contractExgCavMap.get(k);
			}
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
		if (DzscContractcavReportVars.contractExgCavSeqNumMap.size() > 0
				|| index < DzscContractcavReportVars.contractExgCavSeqNumMap
						.size()) {
			return DzscContractcavReportVars.contractExgCavSeqNumMap.get(index)
					.toString();
		}
		return "";
	}

	public static void setDzscContractExgCavList(
			List<DzscContractExgCav> contractExgCavList) {
		DzscContractcavReportVars.contractExgCavList = contractExgCavList;
		for (int i = 0; i < DzscContractcavReportVars.contractExgCavList.size(); i++) {
			DzscContractExgCav contractExgCav = DzscContractcavReportVars.contractExgCavList
					.get(i);
			Integer it = contractExgCav.getSeqNum();
			String value = contractExgCav.getName()+"/"
					+ (contractExgCav.getSpec() == null ? "" : contractExgCav
							.getSpec());
			// System.out.println(i + " : " + value);
			contractExgCavMap.put(it, value);
			contractExgCavSeqNumMap.put(i, it);
		}
	}
	
	public static String setDigit(String str,Integer size){
		if(str==null || "".equals(str)){
			return "";
		}
		Double d = new Double(str);
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(size);
		
		return df.format(d);
	}
}
