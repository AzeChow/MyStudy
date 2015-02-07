/*
 * Created on 2004-10-14
 * 
 * // Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt.report;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.fpt.entity.FptBillItem;
import com.bestway.common.fpt.entity.PrintFptAppHead;
import com.bestway.common.fpt.entity.PrintFptBillHead;


public class CustomsEnvelopSubReportDataSource extends CustomReportDataSource {

	private static FptManageAction transferFactoryManageAction = null;

	private static List list = null;

	private static List subReportData = null;

	// /**
	// * 正在执行
	// */
	// public static final String PROCESS_EXE = "1"; // 正在执行
	//	
	// /**
	// * 全部
	// */
	// public static final String PROCESS_ALL = "0"; // 全部
	// /**
	// * 正在执行
	// */
	// public static final int PROCESS_EXE1 = 1; // 正在执行
	//	
	// /**
	// * 全部
	// */
	// public static final int PROCESS_ALL1 = 0; // 全部

	private CustomsEnvelopSubReportDataSource(List list) {
		super(list);
	}
	
	public static CustomsEnvelopSubReportDataSource getInstance(String serNum,String inOutFlag) {		
		if (subReportData == null) {
			return null;
		}
		inOutFlag = inOutFlag.trim().toUpperCase();
		list = new ArrayList();
		for (int i = 0; i < subReportData.size(); i++) {
			    Object obj = subReportData.get(i);	
		        if(obj instanceof FptAppItem){
		        	FptAppItem c = (FptAppItem)obj;
		        	if (c.getInOutFlag().trim().toUpperCase().equals(inOutFlag)) {
						PrintFptAppHead head = (PrintFptAppHead)c.getFptAppHead();
						if(serNum.equals(String.valueOf(head.getSerialNumber()))){
							list.add(c);
						}
					}
		        }else if(obj instanceof FptBillItem) {
		        	FptBillItem c = (FptBillItem)obj;
		        	if (c.getBillSort().trim().toUpperCase().equals(inOutFlag)) {
						PrintFptBillHead head = (PrintFptBillHead)c.getFptBillHead();
						if(serNum.equals(String.valueOf(head.getSerialNumber()))){
							list.add(c);
						}
					}
		        }
		}
		System.out.println(list.size());
		CustomsEnvelopSubReportDataSource.list = list;
		return new CustomsEnvelopSubReportDataSource(list);
	}
	
	public static CustomsEnvelopSubReportDataSource getInstance(String inOutFlag) {		
		if (subReportData == null) {
			return null;
		}
		inOutFlag = inOutFlag.trim().toUpperCase();
		list = new ArrayList();
		for (int i = 0; i < subReportData.size(); i++) {
			  Object obj = subReportData.get(i);	
		        if(obj instanceof FptAppItem){
		        	FptAppItem c = (FptAppItem)obj;
		        	if (c.getInOutFlag().trim().toUpperCase().equals(inOutFlag)) {
						FptAppHead head = (FptAppHead)c.getFptAppHead();
							list.add(c);
					}
		        }else if(obj instanceof FptBillItem) {
		        	FptBillItem c = (FptBillItem)obj;
		        	if (c.getBillSort().trim().toUpperCase().equals(inOutFlag)) {
						FptBillHead head = (FptBillHead)c.getFptBillHead();
							list.add(c);
					}
		        }
		}
		System.out.println(list.size());
		CustomsEnvelopSubReportDataSource.list = list;
		return new CustomsEnvelopSubReportDataSource(list);
	}

	/**
	 * @param subReportData
	 *            The subReportData to set.
	 */
	public static void setSubReportData(List subReportData) {
		CustomsEnvelopSubReportDataSource.subReportData = subReportData;
	}

	//
	// public static final String getDataState(int value) {
	// String returnValue = "";
	// switch (value) {
	// case CustomsEnvelopSubReportDataSource.PROCESS_ALL1: // 0
	// returnValue = "全部";
	// break;
	// case CustomsEnvelopSubReportDataSource.PROCESS_EXE1: // 1
	// returnValue = "正在执行";
	// break;
	// }
	// return returnValue;
	// }
	
	/**
	 * 以出，收货各5笔明细记录 分割成一个报表数据
	 * @param FptBillHead 收发货单表头
	 * @param outList 出口商品明细
	 * @param inList 进口商品明细
	 * @return 父报表数据源
	 */
	public static  List<PrintFptBillHead> splitReportData(FptBillHead fptBillHead,
			List<FptBillItem> outList, List<FptBillItem> inList){
		List<PrintFptBillHead> masterLs =new ArrayList<PrintFptBillHead>();
		//保持进出口明细商品笔数相同，以笔数多的为准，笔数不足的不全，以便适应打印报表行数格式
		if(outList==null){
			outList = new ArrayList<FptBillItem>();
		}
		if(inList==null){
			inList = new ArrayList<FptBillItem>();
		}
		int size = Math.abs(outList.size()-inList.size());
		if(outList.size()>inList.size()){
			for (int i = 0; i < size; i++) {
				FptBillItem item = new FptBillItem();
				item.setFptBillHead(outList.get(0).getFptBillHead());
				item.setBillSort(FptInOutFlag.IN);
				item.setListNo(i+1);
				inList.add(item);
			}
		}else if (inList.size()>outList.size()){
			for (int i = 0; i < size; i++) {
				FptBillItem item = new FptBillItem();
				item.setFptBillHead(inList.get(0).getFptBillHead());
				item.setBillSort(FptInOutFlag.OUT);
				item.setListNo(i+1);
				outList.add(item);
			}
		}
		//复制第一笔资料
		PrintFptBillHead head = new PrintFptBillHead();
 		try {
			PropertyUtils.copyProperties(head, fptBillHead);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		head.setSerialNumber(0);		
		//以每5笔为一个单元，拆分发货数据
		for (int i = 0; i <outList.size(); i++) {
			if(i%5==0){
				if(i!=0){
					head = (PrintFptBillHead)head.clone();
					head.setSerialNumber(head.getSerialNumber()+1);	
				}
				masterLs.add(head);
			}
			outList.get(i).setFptBillHead(head);
		}
		//以每5笔为一个单元，拆分收货数据
		for (int i = 0,j=0; i <inList.size(); i++) {
			if( i % 5 == 0){
				if(j < masterLs.size()){
					head = masterLs.get(j);
				}else{
					head = (PrintFptBillHead)head.clone();
		    		head.setSerialNumber(head.getSerialNumber()+1);
		    		masterLs.add(head);
				}
				j++;
			}
			inList.get(i).setFptBillHead(head);
		}
		outList.addAll(inList);
		return masterLs;
	}
	
}