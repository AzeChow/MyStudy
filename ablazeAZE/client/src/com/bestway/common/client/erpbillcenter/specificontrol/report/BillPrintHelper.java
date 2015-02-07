package com.bestway.common.client.erpbillcenter.specificontrol.report;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcs.client.contract.DgContract;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;

public class BillPrintHelper {

	/**
	 * 打印单据，一页A4纸打分两半打印  A4*2
	 */
	public static void printBillTwo(List<BillMaster> list,
			Map<BillMaster, List<BillDetail>> valuesMap) {
		Object[] printObj = new Object[4];
		List<Object[]> printList = new ArrayList<Object[]>();
		int size = 10;// 单据打印，每小页打印行数
		// 排列打印数据
		for (BillMaster billMaster : list) {
			List<BillDetail> details = valuesMap.get(billMaster);
			int dSize = details.size();
			if (dSize <= size) {
				Object[] objs = { billMaster, details ,1,1};
				printList.add(objs);
			} else {
				int dCount = dSize / size + (dSize % size != 0 ? 1 : 0);// 页数
				for (int i = 0; i < dCount; i++) {
					List dList = new ArrayList<BillDetail>();
					for (int j = 0; j < size; j++) {
						if ((i * size + j) < dSize) {
							dList.add(details.get(i * size + j));
						} else {
							break;
						}
					}
					Object[] objs = { billMaster, dList , i+1 ,dCount};
					printList.add(objs);
				}
			}
		}
		//数据测试
		System.out.println(printList.size());
		try {
			
			// 打印报表
			int allSize = printList.size();
			System.out.println("all ："+allSize);
			int pageSize = allSize / 2 + (allSize % 2 != 0 ? 1 : 0);// 打印报表页数
			System.out.println("报表页："+pageSize);
			CustomReportDataSource ds = new CustomReportDataSource(
					list);
			BillSubReportDataSource.getInstance().setValuesMap(valuesMap);
			JasperPrint jasperPrintMaster = null;
			
			for (int i = 0; i < pageSize; i++) {
				Map<String, Object> parameters = new HashMap<String, Object>();	
				InputStream masterReportStream = BillPrintHelper.class
				.getResourceAsStream("erpBillReportTwo.jasper");				
				// 上半页
				Object[] objsA = printList.get(i*2);
				BillMaster bmA = (BillMaster)objsA[0];
				List<BillDetail> listA = (List)objsA[1];	
				int listASize = listA.size();
				for(int j =0,s=size-listASize;j<s;j++){
					listA.add(new BillDetail());
				}
				CustomReportDataSource dsA = new CustomReportDataSource(listA);
				InputStream masterReportStreamA = BillPrintHelper.class
				        .getResourceAsStream("erpBillReportTwoSubA.jasper");
				JasperReport subReportA = (JasperReport) JRLoader.loadObject(masterReportStreamA);
				parameters.put("dsA", dsA);
//				System.out.println("a list size ="+listA.size());
				parameters.put("subReportA", subReportA);
				parameters.put("currentPageA", objsA[2].toString());
				parameters.put("allPageA", objsA[3].toString());
//				parameters.put("dsB", null);
//				parameters.put("subReportB", null);
//				parameters.put("isShow", "false");
				
				// 下半页
				if((i*2+1)<allSize){
					System.out.println("打下半页");
					Object[] objsB = printList.get(i*2+1);
					BillMaster bmB = (BillMaster)objsB[0];
					List<BillDetail> listB = (List)objsB[1];
					int listBSize = listB.size();
					for(int j =0,s=size-listBSize;j<s;j++){
						listB.add(new BillDetail());
					}
					CustomReportDataSource dsB = new CustomReportDataSource(listB);
					InputStream masterReportStreamB = BillPrintHelper.class
			        .getResourceAsStream("erpBillReportTwoSubA.jasper");
					JasperReport subReportB = (JasperReport) JRLoader.loadObject(masterReportStreamB);
					parameters.put("dsB", dsB);
					parameters.put("subReportB", subReportB);
					parameters.put("currentPageB", objsB[2].toString());
					parameters.put("allPageB", objsB[3].toString());
					parameters.put("subReportB", subReportB);
					parameters.put("isShow", "true");
				}
				if(jasperPrintMaster == null){
					jasperPrintMaster = JasperFillManager.fillReport(
							masterReportStream, parameters, ds);					
				}
				else{
					CustomReportDataSource d = new CustomReportDataSource(
							list);
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							masterReportStream, parameters, d);
					int jpSize = jasperPrint.getPages().size();
//					System.out.println("two size="+jasperPrint.getPages().size());
					for (int k = 0; k < jpSize; k++) {
						jasperPrintMaster.addPage((JRPrintPage)jasperPrint.getPages().get(k));
					}
				}
			}
			DgReportViewer viewer = new DgReportViewer(jasperPrintMaster);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * A4打印单据
	 */
	public static void printBill(List<BillMaster> list,
			Map<BillMaster, List<BillDetail>> valuesMap){
		CustomReportDataSource ds = new CustomReportDataSource(
				list);
		
		try {
			BillSubReportDataSource.getInstance()
					.setValuesMap(valuesMap);
			InputStream subReportStream = BillPrintHelper.class
					.getResourceAsStream("erpBillSubReports.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("subReport", subReport);
			InputStream reportStream = BillPrintHelper.class
					.getResourceAsStream("erpBillReports.jasper");
			JasperPrint jasperPrint = JasperFillManager
					.fillReport(reportStream, parameters,
							ds);
			DgReportViewer dgReportViewer = new DgReportViewer(
					jasperPrint);
			dgReportViewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * A5打印
	 * @param list
	 * @param valuesMap
	 */
	public static void printBillA5(List<BillMaster> list,
			Map<BillMaster, List<BillDetail>> valuesMap){
		CustomReportDataSource ds = new CustomReportDataSource(
				list);
		try {
			BillSubReportDataSource.getInstance()
					.setValuesMap(valuesMap);
			InputStream subReportStream = BillPrintHelper.class
					.getResourceAsStream("erpBillSubReportsA5.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("subReport", subReport);
			InputStream reportStream = BillPrintHelper.class
					.getResourceAsStream("erpBillReportsA5.jasper");
			JasperPrint jasperPrint = JasperFillManager
					.fillReport(reportStream, parameters,
							ds);
			DgReportViewer dgReportViewer = new DgReportViewer(
					jasperPrint);
			dgReportViewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
