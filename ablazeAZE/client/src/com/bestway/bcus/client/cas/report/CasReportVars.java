/*
 * Created on 2005-5-27
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.BillFixingBase;
import com.bestway.bcus.cas.entity.ExgExportInfoBase;
import com.bestway.bcus.cas.entity.ImgOrgUseInfoBase;
import com.bestway.bcus.cas.parameterset.entity.WriteAccountYear;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;

/**
 * @author ls // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CasReportVars {

	private static CasReportVars c = null;

	private static List<ExgExportInfoBase> exgExportInfoBaseList = new ArrayList<ExgExportInfoBase>();
	private static Map<Integer, String> exgExportInfoBaseKeyMap = new HashMap<Integer, String>();
	private static Map<Integer, String> exgExportInfoBaseNoteKeyMap = new HashMap<Integer, String>();
	
	private static List<ImgOrgUseInfoBase> imgOrgUseInfoBaseList = new ArrayList<ImgOrgUseInfoBase>();
	private static Map<Integer, String> imgOrgUseInfoBaseKeyMap = new HashMap<Integer, String>();
	private static Map<Integer, String> imgOrgUseInfoBaseNoteKeyMap = new HashMap<Integer, String>();
	private static List<BillFixingBase> billFixingBaseList = new ArrayList<BillFixingBase>();
	private static Map<Integer, String> billFixingBaseKeyMap = new HashMap<Integer, String>();
	private static Map<Integer, String> billFixingBaseNoteKeyMap = new HashMap<Integer, String>();
	private CasAction casAction = null;

	private CasReportVars() {
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
	}

	public static CasReportVars getInstance() {
		if (c == null) {
			c = new CasReportVars();
		}
		return c;
	}

	/**
	 * 获得成品名称
	 * 
	 * @param index
	 * @return
	 */
	public String getExgName(int index) {
		if (exgExportInfoBaseKeyMap.size() > 0
				&& index <= exgExportInfoBaseKeyMap.size()) {
			return exgExportInfoBaseKeyMap.get(index);
		}
		return "";
	}

	/**
	 * 获得成品备注
	 * 
	 * @param index
	 * @return
	 */
	public String getExgNameNote(int index) {
		if (exgExportInfoBaseNoteKeyMap.size() > 0
				&& index <= exgExportInfoBaseNoteKeyMap.size()) {
			return exgExportInfoBaseNoteKeyMap.get(index);
		}
		return "";
	}
	
	
	/**
	 * 获得料件名称
	 * 
	 * @param index
	 * @return
	 */
	public String getImgName(int index) {
		if (imgOrgUseInfoBaseKeyMap.size() > 0
				&& index <= imgOrgUseInfoBaseKeyMap.size()) {
			return imgOrgUseInfoBaseKeyMap.get(index);
		}
		return "";
	}
	
	/**
	 * 获得料件备注
	 * 
	 * @param index
	 * @return
	 */
	public String getImgNameNote(int index) {
		if (imgOrgUseInfoBaseNoteKeyMap.size() > 0
				&& index <= imgOrgUseInfoBaseNoteKeyMap.size()) {
			return imgOrgUseInfoBaseNoteKeyMap.get(index);
		}
		return "";
	}

	/**
	 * 获得设备名称
	 * 
	 * @param index
	 * @return
	 */
	public String getFixName(int index) {
		if (billFixingBaseKeyMap.size() > 0
				&& index <= billFixingBaseKeyMap.size()) {
			return billFixingBaseKeyMap.get(index);
		}
		return "";
	}
	
	/**
	 * 获得设备备注
	 * 
	 * @param index
	 * @return
	 */
	public String getFixNameNote(int index) {
		if (billFixingBaseNoteKeyMap.size() > 0
				&& index <= billFixingBaseNoteKeyMap.size()) {
			return billFixingBaseNoteKeyMap.get(index);
		}
		return "";
	}


	public static void setExgExportInfoBaseList(
			List<ExgExportInfoBase> exgExportInfoBaseList) {
		CasReportVars.exgExportInfoBaseList = exgExportInfoBaseList;
		exgExportInfoBaseKeyMap.clear();
		for (int i = 0, n = CasReportVars.exgExportInfoBaseList.size(); i < n; i++) {
			ExgExportInfoBase version = CasReportVars.exgExportInfoBaseList
					.get(i);
			exgExportInfoBaseKeyMap.put(i + 1, version.getKey() == null ? ""
					: version.getKey());
			exgExportInfoBaseNoteKeyMap.put(i + 1, version.getF12() == null ? ""
					: version.getF12());
		}
	}

	public static void setImgOrgUseInfoBaseList(
			List<ImgOrgUseInfoBase> imgOrgUseInfoBaseList) {
		CasReportVars.imgOrgUseInfoBaseList = imgOrgUseInfoBaseList;
		imgOrgUseInfoBaseKeyMap.clear();
		for (int i = 0, n = CasReportVars.imgOrgUseInfoBaseList.size(); i < n; i++) {
			ImgOrgUseInfoBase version = CasReportVars.imgOrgUseInfoBaseList
					.get(i);
			imgOrgUseInfoBaseKeyMap.put(i + 1, version.getKey() == null ? ""
					: version.getKey());
			imgOrgUseInfoBaseNoteKeyMap.put(i + 1, version.getF27() == null ? "":version.getF27());
		}
	}

	public static void setBillFixingBaseList(
			List<BillFixingBase> billFixingBaseList) {
		CasReportVars.billFixingBaseList = billFixingBaseList;
		billFixingBaseKeyMap.clear();
		for (int i = 0, n = CasReportVars.billFixingBaseList.size(); i < n; i++) {
			BillFixingBase version = CasReportVars.billFixingBaseList.get(i);
			billFixingBaseKeyMap.put(i + 1, version.getBill1() == null ? ""
					: version.getBill1());
			billFixingBaseNoteKeyMap.put(i + 1, version.getBill2() == null ? ""
					: version.getBill2());
			
		}
	}

	public String getWriteAccountYear() {
		WriteAccountYear war = casAction.findWriteAccountYear(new Request(
				CommonVars.getCurrUser()));
		if (war != null && war.getYear() != null) {
			return war.getYear() + "年度";
		}
		return "";
	}

	public String getWriteAccountYearForSeBei() {
		WriteAccountYear war = casAction.findWriteAccountYear(new Request(
				CommonVars.getCurrUser()));
		if (war != null && war.getYear() != null) {

			Integer be = war.getYear() - 4;
			return "至填表日前溯5年内   " + be.toString() + "---" + war.getYear()
					+ "年度";
		}
		return "";
	}

//	private static ThreadLocal t = new ThreadLocal();
//
//	public static void setJasperPrintCount(JasperPrint jasperPrint) {
//		t.set(jasperPrint);
//	}
//
//	public static String getJasperPrintCount() {
//		if (t.get() != null) {
//			return String.valueOf(((JasperPrint) t.get()).getPages().size());
//		} else {
//			return "";
//		}
//	}
}
