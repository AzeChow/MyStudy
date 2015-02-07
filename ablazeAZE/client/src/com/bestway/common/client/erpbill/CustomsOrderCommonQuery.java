package com.bestway.common.client.erpbill;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.erpbill.action.OrderCommonAction;
import com.bestway.common.materialbase.action.MaterialManageAction;

public class CustomsOrderCommonQuery {
	public static CustomsOrderCommonQuery customsOrderCommonQuery = null;
	private OrderCommonAction orderCommonAction = null;

	public static CustomsOrderCommonQuery getInstance() {
		if (customsOrderCommonQuery == null) {
			customsOrderCommonQuery = new CustomsOrderCommonQuery();
		}
		return customsOrderCommonQuery;
	}

	public Object findMaterielByType(String materielType) {
		orderCommonAction = (OrderCommonAction) CommonVars
				.getApplicationContext().getBean("orderCommonAction");
		List dataSource = new ArrayList();
		dataSource = orderCommonAction.findMaterielBtAllInnerMeger(new Request(
				CommonVars.getCurrUser()), materielType);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("类别", "scmCoi.name", 80));
		list.add(new JTableListColumn("料号", "ptNo", 80));
		list.add(new JTableListColumn("工厂名称", "factoryName", 180));
		list.add(new JTableListColumn("工厂规格", "factorySpec", 180));
		list.add(new JTableListColumn("工厂单位", "calUnit.name", 80));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择物料");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 用于订单统计报表中取得订单号码
	 */
	public List getTempCustomsOrder(Integer customType) {
		List list = new Vector();
		list.add(new JTableListColumn("订单号", "bill1", 100));
		list.add(new JTableListColumn("订单日期", "bill2", 100));
		list.add(new JTableListColumn("客户供应商", "bill3", 200));
		DgCommonQuery.setTableColumns(list);
		orderCommonAction = (OrderCommonAction) CommonVars
				.getApplicationContext().getBean("orderCommonAction");
		List dataSource = new ArrayList();
		dataSource = orderCommonAction.findTempCustomsOrder(new Request(
				CommonVars.getCurrUser()), customType);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择订单号码");
		dgCommonQuery.setLike(true);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}
}
