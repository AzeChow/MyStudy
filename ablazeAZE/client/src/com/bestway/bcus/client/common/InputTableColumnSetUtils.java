package com.bestway.bcus.client.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;

public class InputTableColumnSetUtils {
	private static SystemAction systemAction = (SystemAction) CommonVars
			.getApplicationContext().getBean("systemAction");

	private static Map<String, List> columnCache = new HashMap<String, List>();

	/**
	 * 将列的信息缓存起来
	 * 
	 * @param tableFlag
	 * @param list
	 */
	public static void putColumn(String tableFlag, List list) {
		columnCache.put(tableFlag, list);
	}

	public static List getColumn(String tableFlag) {
		return columnCache.get(tableFlag);
	}

	/**
	 * 获取在Table中显示的列的List
	 * 
	 * @param tableFlag
	 * @return
	 */
	public static List getTableColumnList(String tableFlag) {
		List list = columnCache.get(tableFlag);
		List lsResult = new ArrayList();
		List lsDestColumnSet = systemAction.findInputTableColumnSet(
				new Request(CommonVars.getCurrUser()), tableFlag);
		if (!lsDestColumnSet.isEmpty()) {
			for (int i = 0; i < lsDestColumnSet.size(); i++) {
				InputTableColumnSet columnSet = (InputTableColumnSet) lsDestColumnSet
						.get(i);
				for (int j = 0; j < list.size(); j++) {
					JTableListColumn column = (JTableListColumn) list.get(j);
					if (columnSet.getColumnField().equals(column.getProperty())) {
						JTableListColumn tempColumn = new JTableListColumn();
						try {
							PropertyUtils.copyProperties(tempColumn, column);
						} catch (Exception e) {
							e.printStackTrace();
						}

						lsResult.add(tempColumn);
						break;
					}
				}
			}
		} else {
			for (int j = 0; j < list.size(); j++) {
				JTableListColumn column = (JTableListColumn) list.get(j);
				JTableListColumn tempColumn = new JTableListColumn();
				try {
					PropertyUtils.copyProperties(tempColumn, column);
				} catch (Exception e) {
					e.printStackTrace();
				}
				lsResult.add(tempColumn);
			}
		}
		for (int i = 0; i < lsResult.size(); i++) {
			JTableListColumn column = (JTableListColumn) lsResult.get(i);
			column.setCaption(String.valueOf(i + 1) + ":" + column.getCaption());
		}
		JTableListColumn column = new JTableListColumn();
		column.setCaption("错误信息");
		column.setProperty("errinfo");
		column.setWidth(300);
		lsResult.add(column);
		return lsResult;
	}

	/**
	 * 获取需要导入的列，主要是列的名称(实体属性)
	 * 
	 * @param tableFlag
	 * @return
	 */
	public static List<String> getColumnFieldIndex(String tableFlag) {

		List lsDestColumnSet = systemAction.findInputTableColumnSet(
				new Request(CommonVars.getCurrUser()), tableFlag);

		if (lsDestColumnSet.isEmpty()) {
			lsDestColumnSet = columnCache.get(tableFlag);
		}
		List<String> lsIndex = new ArrayList<String>();
		if (lsDestColumnSet == null || lsDestColumnSet.isEmpty()) {
			return lsIndex;
		}
		if (lsDestColumnSet.get(0) instanceof InputTableColumnSet) {
			for (int i = 0; i < lsDestColumnSet.size(); i++) {
				InputTableColumnSet columnSet = (InputTableColumnSet) lsDestColumnSet
						.get(i);
				lsIndex.add(columnSet.getColumnField());
			}
		} else if (lsDestColumnSet.get(0) instanceof JTableListColumn) {
			for (int i = 0; i < lsDestColumnSet.size(); i++) {
				JTableListColumn columnSet = (JTableListColumn) lsDestColumnSet
						.get(i);
				lsIndex.add(columnSet.getProperty());
			}
		}
		return lsIndex;
	}

}
