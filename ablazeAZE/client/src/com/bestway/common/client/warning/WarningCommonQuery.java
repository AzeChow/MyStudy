/*
 * Created on 2005-3-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.warning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;
import com.bestway.common.warning.action.WarningAction;
import com.bestway.common.warning.entity.TempWarningItem;
import com.bestway.common.warning.entity.WarningItem;

/**
 * @author xxm
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class WarningCommonQuery {
	private static WarningCommonQuery	bcsQuery	= null;

	public synchronized static WarningCommonQuery getInstance() {
		if (bcsQuery == null) {
			bcsQuery = new WarningCommonQuery();
		}
		return bcsQuery;
	}

	
	/**
	 * 获得预警项目
	 * @param existItem
	 * @return
	 */
	public Object getTempWarningItem(List<WarningItem> existItem) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		Map<Integer, Integer> existMap = new HashMap<Integer, Integer>();
		for (WarningItem item : existItem) {
			existMap.put(item.getWarningItemFlag(), item.getWarningItemFlag());
		}

		list.add(new JTableListColumn("项目名称", "itemName", 500));
		DgCommonQuery.setTableColumns(list);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		WarningAction warningAction = (WarningAction) CommonVars
				.getApplicationContext().getBean("warningAction");
		List<TempWarningItem> dataSource = warningAction
				.getTempWarningItem(new Request(CommonVars.getCurrUser()));
		for (int i = dataSource.size() - 1; i >= 0; i--) {
			TempWarningItem item = dataSource.get(i);
			if(existMap.containsKey(item.getWarningItemFlag())){
				dataSource.remove(i);
			}
		}
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("选择预警项目");
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

}
