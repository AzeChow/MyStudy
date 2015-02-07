package com.bestway.dzsc.client.customslist;

import java.awt.Component;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.dzsc.customslist.action.DzscListAction;
import com.bestway.dzsc.customslist.entity.DzscCustomsBillList;

public class DzscListQuery {
	private static DzscListQuery dzscListQuery = null;

	public static DzscListQuery getInstance() {
		if (dzscListQuery == null) {
			dzscListQuery = new DzscListQuery();
		}
		return dzscListQuery;
	}

	/**
	 * 取得临时申报商品信息
	 * 
	 * @return
	 */
	public List getTempDeclaredCommInfo(DzscCustomsBillList billList,
			Integer materielProductFlag) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("手册序号", "emsSerialNo", 100));
		list.add(new JTableListColumn("商品货号", "materiel.ptNo", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "name", 100));
		list.add(new JTableListColumn("商品规格", "spec", 100));
		list.add(new JTableListColumn("单位", "unit.name", 50));
		list.add(new JTableListColumn("法定单位", "legalUnit.name", 50));
		list.add(new JTableListColumn("法定单位二", "legalUnit2.name", 50));
		// list.add(new JTableListColumn("属性", "materielType", 50));
		DgCommonQuery.setTableColumns(list);
		DzscListAction dzscListAction = (DzscListAction) CommonVars
				.getApplicationContext().getBean("dzscListAction");
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource((dzscListAction.getTempDeclaredCommInfo(
				new Request(CommonVars.getCurrUser(), true), billList,
				materielProductFlag)));
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery
				.setTitle(materielProductFlag == null ? ""
						: materielProductFlag.toString().equals(
								MaterielType.MATERIEL) ? "料件(来自于通关备案)"
								: "成品(来自于通关备案)");
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable table = dgCommonQuery.getJTable();
				// table.getColumnModel().getColumn(1).setCellRenderer(
				// new TableCheckBoxRender());
				// table.getColumnModel().getColumn(8).setCellRenderer(
				// new DefaultTableCellRenderer() {
				// public Component getTableCellRendererComponent(
				// JTable table, Object value,
				// boolean isSelected, boolean hasFocus,
				// int row, int column) {
				// super.getTableCellRendererComponent(table,
				// value, isSelected, hasFocus, row,
				// column);
				// String str = "";
				// if (value != null) {
				// String tempStr = value.toString();
				// if (tempStr.equals(MaterielType.MATERIEL)) {
				// str = "料件";
				// } else if (tempStr
				// .equals(MaterielType.FINISHED_PRODUCT)) {
				// str = "成品";
				// }
				// }
				// this.setText(str);
				// return this;
				// }
				// });
			}
		});
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

}
