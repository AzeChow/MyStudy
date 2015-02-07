/*
 * Created on 2005-5-27
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ManualdeclareReportVars {

	private static ManualdeclareReportVars	c						= null;
	private static List<EmsHeadH2kVersion>	emsHeadH2kVersionList	= new ArrayList<EmsHeadH2kVersion>();
	private static Map<Integer, String>		versionMap				= new HashMap<Integer, String>();
	private static Map<Integer, String>		seqNumMap				= new HashMap<Integer, String>();

	private ManualdeclareReportVars() {

	}

	public static ManualdeclareReportVars getInstance() {
		if (c == null) {
			c = new ManualdeclareReportVars();
		}
		return c;
	}

	/**
	 * 获得版本号
	 * 
	 * @param index
	 * @return
	 */
	public String getVersion(int index) {
		if (ManualdeclareReportVars.versionMap.size() > 0
				|| index < ManualdeclareReportVars.versionMap.size()) {
			return ManualdeclareReportVars.versionMap.get(index);
		}
		return "";
	}

	/**
	 * 获得成品序号
	 * 
	 * @param index
	 * @return
	 */
	public String getSeqNum(int index) {
		if (ManualdeclareReportVars.seqNumMap.size() > 0
				|| index < ManualdeclareReportVars.seqNumMap.size()) {
			return ManualdeclareReportVars.seqNumMap.get(index);
		}
		return "";
	}

	

	public static void setEmsHeadH2kVersionList(
			List<EmsHeadH2kVersion> emsHeadH2kVersionList) {
		versionMap.clear();
		seqNumMap.clear();
		ManualdeclareReportVars.emsHeadH2kVersionList = emsHeadH2kVersionList;
		for (int i = 0; i < ManualdeclareReportVars.emsHeadH2kVersionList
				.size(); i++) {
			EmsHeadH2kVersion version = ManualdeclareReportVars.emsHeadH2kVersionList
					.get(i);

			versionMap.put(i, version.getVersion() == null ? "" : version
					.getVersion().toString());
			seqNumMap
					.put(i, version.getEmsHeadH2kExg().getSeqNum() == null ? ""
							: version.getEmsHeadH2kExg().getSeqNum().toString());			
		}
	}
}
