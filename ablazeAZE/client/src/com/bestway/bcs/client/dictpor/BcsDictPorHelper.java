package com.bestway.bcs.client.dictpor;

import java.util.List;
import java.util.Vector;

import com.bestway.bcs.dictpor.action.BcsDictPorAction;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;

public class BcsDictPorHelper {
	private BcsDictPorAction bcsDictPorAction = (BcsDictPorAction) CommonVars
			.getApplicationContext().getBean("bcsDictPorAction");

	private static BcsDictPorHelper bcsDictPorQuery = null;

	public static BcsDictPorHelper getInstance() {
		if (bcsDictPorQuery == null) {
			bcsDictPorQuery = new BcsDictPorHelper();
		}
		return bcsDictPorQuery;
	}

	/**
	 * 电子手册--成品--新增成品--来自归并
	 */
	public List findInnerMergeNotInBcsDictPor(BcsDictPorHead head,
			String materielType, boolean isFromInnerMerge) {
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List list = bcsDictPorAction.findInnerMergeNotInBcsDictPor(new Request(
				CommonVars.getCurrUser(), true), head, materielType,
				isFromInnerMerge);
		dgCommonQuery.setDataSource(list);
		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		tableColumns.add(new JTableListColumn("归并序号", "seqNum", 50));
		tableColumns.add(new JTableListColumn("商品编码", "complex.code", 80));
		tableColumns.add(new JTableListColumn("商品名称", "name", 80));
		tableColumns.add(new JTableListColumn("规格型号", "spec", 80));
		tableColumns.add(new JTableListColumn("单位", "comUnit.name", 80));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(false);
		String dataFromSpec = "";
		if (isFromInnerMerge) {
			dataFromSpec = "来自于物料与报关对应表";
		} else {
			dataFromSpec = "来自于报关商品资料";
		}
		if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
			dgCommonQuery.setTitle("成品(" + dataFromSpec + ")");
		} else {
			dgCommonQuery.setTitle("料件(" + dataFromSpec + ")");
		}
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

}
