package com.bestway.common.client.erpbillcenter.specificontrol.report;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JREmptyDataSource;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.common.materialbase.entity.ScmCoc;

public class BillSubReportDataSource {
	private Map<BillMaster, List<BillDetail>> valuesMap = null;
	private static BillSubReportDataSource billSubReportDataSource = null;

	public void setValuesMap(Map<BillMaster, List<BillDetail>> valuesMap) {
		this.valuesMap = valuesMap;
	}


	public static BillSubReportDataSource getInstance() {
		if (billSubReportDataSource == null) {
			billSubReportDataSource = new BillSubReportDataSource();
		}
		return billSubReportDataSource;
	}
	
	public String getIsCustoms(String id) {
		Set<BillMaster> set = valuesMap.keySet();
		Iterator itr = set.iterator();
		while (itr.hasNext()) {
			BillMaster master = (BillMaster) itr.next();
			if (master.getId().equals(id)) {
				if (master.getScmCoc() == null) {
					return "";
				}
				ScmCoc scmCoc = master.getScmCoc();
				if (scmCoc.getIsCustomer() != null && scmCoc.getIsCustomer()) {
					return "客户：";
				} else if (scmCoc.getIsManufacturer() != null
						&& scmCoc.getIsManufacturer()) {
					return "供应商：";
				} else {
					return "";
				}
			}
		}
		return "";
	}

	public JREmptyDataSource getSubData(String id) {
		Set<BillMaster> set = valuesMap.keySet();
		final int displaySize = 10;
		Iterator itr = set.iterator();
		while (itr.hasNext()) {
			BillMaster master = (BillMaster) itr.next();
			if (master.getId().equals(id)) {
				List<BillDetail> list = valuesMap.get(master);
				int size = list.size();
				if (size <= displaySize) {
					for (int i = 0; i < (displaySize - size); i++) {
						list.add(new BillDetail());
					}
				}
				return new CustomReportDataSource(valuesMap.get(master));
			}
		}
		return new CustomReportDataSource(new ArrayList());
	}

	public JREmptyDataSource getSubDatas(String id) {
		Set<BillMaster> set = valuesMap.keySet();
		final int displaySize = 11;
		Iterator itr = set.iterator();
		while (itr.hasNext()) {
			BillMaster master = (BillMaster) itr.next();
			if (master.getId().equals(id)) {
				List<BillDetail> list = valuesMap.get(master);
				int size = list.size() % displaySize;
				// System.out.println("添加："+(displaySize-size));
				if (size > 0) {
					for (int i = 0; i < (displaySize - size); i++) {
						list.add(new BillDetail());
					}
				}
				return new CustomReportDataSource(valuesMap.get(master));
			}
		}
		return new CustomReportDataSource(new ArrayList());
	}
}
