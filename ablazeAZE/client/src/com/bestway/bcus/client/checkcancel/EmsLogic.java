/*
 * Created on 2005-3-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.EmsAnalyHead;
import com.bestway.bcus.checkcancel.entity.EmsBgExg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.footer.TableFooterType;
import com.bestway.common.Request;

/**
 * @author xxm
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class EmsLogic {
	private static NumberFormatter numberFormatter = null;
	private static CheckCancelAction checkCancelAction = (CheckCancelAction) CommonVars
			.getApplicationContext().getBean("checkCancelAction");

	// 填充差异分析
	public static JTableListModel initTableCy(JTableListModel tableModel,
			JTable jTable, final List list) {
		return tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@SuppressWarnings("unchecked")
					public List InitColumns() {
						List list = new Vector();
						// list.add(addColumn("帐册编号", "emsNo", 90));
						list.add(addColumn("备案序号", "seqNum", 50));
						list.add(addColumn("料件名称", "name", 100));
						list.add(addColumn("型号规格", "spec", 120));
						list.add(addColumn("计量单位", "unit.name", 60));
						list.add(addColumn("单价", "price", 60));
						list.add(addColumn("帐面结余(A)", "emsBalance", 90));
						list.add(addColumn("实物库存(B)", "factNum", 90));
						list.add(addColumn("差异数(A-B)", "cyNum", 90));
						list.add(addColumn("关税(USD)", "usd", 90));
						list.add(addColumn("增值税(USD)", "usdAdd", 90));
						return list;
					}
				});
	}

	// 填充报关--原料进口
	public static JTableListModel initTableBgImg(JTableListModel tableModel,
			JTable jTable, final List list) {
		 tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册编号", "emsNo", 90));
						list.add(addColumn("备案序号", "seqNum", 50));
						list.add(addColumn("料件名称", "name", 100));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("计量单位", "unit.name", 50));
						list.add(addColumn("期初数量", "initNum", 80));
						list.add(addColumn("进口数量", "inNum", 80));
						list.add(addColumn("转厂数量", "changeNum", 80));
						list.add(addColumn("余料转入", "remainInNum", 80));
						list.add(addColumn("余料转出", "remainOutNum", 80));
						list.add(addColumn("退料数量", "exitNum", 80));
						list.add(addColumn("总进口", "totalNum", 80));
						return list;
					}
				});
		
		tableModel.clearFooterTypeInfo();
		tableModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		for (int i = 6; i < 13; i++) {
			tableModel.addFooterTypeInfo(new TableFooterType(i,
					TableFooterType.SUM, "", 5));
		}
		return tableModel;
	}

	// 填充报关--成品出口--上
	public static JTableListModel initTableBgExgS(JTableListModel tableModel,
			JTable jTable, final List list) {
		 tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册编号", "emsNo", 90));
						list.add(addColumn("备案序号", "seqNum", 50));
						list.add(addColumn("成品名称", "name", 110));
						list.add(addColumn("版本", "versionNo", 50));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("计量单位", "unit.name", 80));
						list.add(addColumn("出口数量 ", "outNum", 80));
						list.add(addColumn("转厂数量 ", "changeNum", 80));
						list.add(addColumn("退厂返工", "exitNum", 70));
						list.add(addColumn("返工复出", "exitNum", 70));
						list.add(addColumn("总出口", "totalNum", 70));
						return list;
					}
				});
		tableModel.clearFooterTypeInfo();
		tableModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		for (int i = 7; i < 12; i++) {
			tableModel.addFooterTypeInfo(new TableFooterType(i,
					TableFooterType.SUM, "", 5));
		}
		return tableModel;
	}

	// 填充报关--成品出口--下
	public static JTableListModel initTableBgExgX(JTableListModel tableModel,
			JTable jTable, final List list) {
		 tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("成品序号", "exg.seqNum", 50));
						list.add(addColumn("成品名称", "exg.name", 80));
						list.add(addColumn("版本", "exg.versionNo", 50));
						list.add(addColumn("型号规格", "exg.name", 110));
//						list.add(addColumn("保税类别", "no", 80));
						list.add(addColumn("料件序号", "seqNum", 50));
						list.add(addColumn("料件名称", "name", 80));
						list.add(addColumn("型号规格", "spec", 80));
						list.add(addColumn("计量单位", "unit.name", 60));
						list.add(addColumn("单耗 ", "unitWare", 80));
						list.add(addColumn("损耗 ", "ware", 80));
						list.add(addColumn("总耗用", "totalWare", 80));
						list.add(addColumn("总出口", "exg.totalNum", 70));
						list.add(addColumn("帐册编号", "emsNo", 100));
						return list;
					}
				});
		tableModel.clearFooterTypeInfo();
		tableModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		for (int i = 11; i < 13; i++) {
			tableModel.addFooterTypeInfo(new TableFooterType(i,
					TableFooterType.SUM, "", 5));
		}
		return tableModel;
	}

	// 填充报关--进出口总表
	public static JTableListModel initTableBgImgExgTotal(
			JTableListModel tableModel, JTable jTable, final List list) {
		return tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册编号", "emsNo", 90));
						list.add(addColumn("备案序号", "seqNum", 50));
						list.add(addColumn("料件名称", "name", 120));
						list.add(addColumn("型号规格", "spec", 120));
						list.add(addColumn("计量单位", "unit.name", 80));
						list.add(addColumn("进口数量(A) ", "inNum", 120));
						list.add(addColumn("成品耗用(B) ", "exgWare", 120));
						list.add(addColumn("结余量(A-B)", "balance", 80));
						return list;
					}
				});
	}

	// 差异分析
	public static JTableListModel initTableCy(JTableListModel tableModel,
			JTable jTable, EmsAnalyHead head) {
		List list = checkCancelAction.findEmsCy(new Request(CommonVars
				.getCurrUser()), head);
		if (list != null && list.size() > 0) {
			return initTableCy(tableModel, jTable, list);
		} else {
			return initTableCy(tableModel, jTable, new Vector());
		}
	}

	// 报关--原料进口
	public static JTableListModel initTableBgImg(JTableListModel tableModel,
			JTable jTable, EmsAnalyHead head) {
		List list = checkCancelAction.findEmsBgImg(new Request(CommonVars
				.getCurrUser()), head);
		if (list != null && list.size() > 0) {
			return initTableBgImg(tableModel, jTable, list);
		} else {
			return initTableBgImg(tableModel, jTable, new Vector());
		}
	}

	// 报关--成品出口折料
	public static JTableListModel initTableBgExgX(JTableListModel tableModel,
			JTable jTable, EmsAnalyHead head, String exgId) {
		if (exgId == null) {
			return initTableBgExgX(tableModel, jTable, new Vector());
		}
		List list = checkCancelAction.findEmsBgExgBg(new Request(CommonVars
				.getCurrUser()), head,exgId);
		if (list != null && list.size() > 0) {
			return initTableBgExgX(tableModel, jTable, list);
		} else {
			return initTableBgExgX(tableModel, jTable, new Vector());
		}
	}

	// 报关--成品出口
	public static JTableListModel initTableBgExgS(JTableListModel tableModelExg, JTable jTable, EmsAnalyHead head) {
		List list = checkCancelAction.findEmsBgExg(new Request(CommonVars
				.getCurrUser()), head);
		if (list != null && list.size() > 0) {
			return  initTableBgExgS(tableModelExg, jTable, list);
		} else {
			return initTableBgExgS(tableModelExg, jTable, new Vector());
		}
	}

	// 报关--原料进口总表
	public static JTableListModel initTableBgTotal(JTableListModel tableModel,
			JTable jTable, EmsAnalyHead head) {
		List list = checkCancelAction.findEmsBgTotal(new Request(CommonVars
				.getCurrUser()), head);
		if (list != null && list.size() > 0) {
			return initTableBgImgExgTotal(tableModel, jTable, list);
		} else {
			return initTableBgImgExgTotal(tableModel, jTable, new Vector());
		}
	}

}
