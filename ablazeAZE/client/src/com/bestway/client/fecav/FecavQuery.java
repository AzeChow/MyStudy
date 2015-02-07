/*
 * Created on 2005-7-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.fecav;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;
import com.bestway.common.constant.FecavState;
import com.bestway.common.constant.ProjectType;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.fecav.entity.FecavBillStrike;
import com.bestway.fecav.entity.ImpCustomsDeclaration;
import com.bestway.fecav.entity.StrikeImpCustomsDeclaration;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FecavQuery {
	private static FecavQuery fecavQuery = null;

	private static FecavAction fecavAction = null;

	public static FecavQuery getInstance() {
		if (fecavQuery == null) {
			fecavQuery = new FecavQuery();
			fecavAction = (FecavAction) CommonVars.getApplicationContext()
					.getBean("fecavAction");
		}
		return fecavQuery;
	}

	/**
	 * 查询没有内部领用的核销单
	 * 
	 * @return
	 */
	public List findNotInnerObtainFecavBill() {
		List dataSource = fecavAction.findFecavBillByState(new Request(
				CommonVars.getCurrUser()), FecavState.OUTER_OBTAIN);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("核销单号 ", "code", 200));
		list.add(new JTableListColumn("外部领单人", "outerObtain", 100));
		list.add(new JTableListColumn("外部领单日期", "outerObtainDate", 100));
		list.add(new JTableListColumn("外部操作人", "outerOperator", 100));
		list.add(new JTableListColumn("外部操作日期", "outerOperatorDate", 100));
		list.add(new JTableListColumn("标志", "billState", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("内部领用资料");
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable table = dgCommonQuery.getJTable();
				table.getColumnModel().getColumn(6).setCellRenderer(
						new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								int state = -1;
								if (value != null) {
									state = Integer.parseInt(value.toString());
								}
								switch (state) {
								case FecavState.OUTER_OBTAIN:
									this.setText("外部领用");
									break;
								}
								return this;
							}
						});
			}
		});
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 抓取报关单资料
	 * 
	 * @param projectType
	 * @return
	 */
	public Object findExportCustomsDeclaration() {
		List dataSource = fecavAction
				.findCustomsDeclarationInfoForFecav(new Request(CommonVars
						.getCurrUser()));
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("出口日期", "exportDate", 100));
		list.add(new JTableListColumn("申报日期", "declareDate", 100));
		list.add(new JTableListColumn("报关单号", "customsDeclarationCode", 100));
		list.add(new JTableListColumn("合同号码", "contractNo", 100));
		list.add(new JTableListColumn("手册号码", "emsNo", 100));
		list.add(new JTableListColumn("币别", "curr.name", 100));
		list.add(new JTableListColumn("总金额", "totalPrice", 100));
		list.add(new JTableListColumn("报关单来源", "projectType", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("报关单资料");
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable table = dgCommonQuery.getJTable();
				table.getColumnModel().getColumn(8).setCellRenderer(
						new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								String str = "";
								if (value != null) {
									Integer projectType = Integer.valueOf(value
											.toString());
									switch (projectType) {
									case ProjectType.BCUS:
										str = "BCUS";
										break;
									case ProjectType.BCS:
										str = "BCS";
										break;
									}
								}
								this.setText(str);
								return this;
							}
						});
			}
		});
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public List<StrikeImpCustomsDeclaration> findNotBrikeImpCustomsDeclarationBy() {
		List dataSource = fecavAction
				.findBrikeImpCustomsDeclarationToStrike(new Request(CommonVars
						.getCurrUser()));
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("进口报关单号", "customsDeclarationCode", 100));
		list.add(new JTableListColumn("合同号", "contractNo", 100));
		list.add(new JTableListColumn("手册编号 ", "emsNo", 200));
		list.add(new JTableListColumn("报关单总金额", "totalMoney", 100));
		list.add(new JTableListColumn("冲销金额", "strikeMoney", 200));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("进口核销单资料");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 查询可以管制的进口报关单(白单)
	 * 
	 * @return
	 */
	public List findNotUseImpCustomsDeclaration() {
		List dataSource = fecavAction
				.findNotUseImpCustomsDeclaration(new Request(CommonVars
						.getCurrUser()));
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("进口日期", "exportDate", 100));
		list.add(new JTableListColumn("申报日期", "declareDate", 100));
		list.add(new JTableListColumn("报关单号", "customsDeclarationCode", 100));
		list.add(new JTableListColumn("合同号码", "contractNo", 100));
		list.add(new JTableListColumn("手册号码", "emsNo", 100));
		list.add(new JTableListColumn("贸易方式", "tradeMode", 100));
		list.add(new JTableListColumn("币别", "curr.name", 100));
		list.add(new JTableListColumn("总金额", "totalPrice", 100));
		list.add(new JTableListColumn("已冲销金额", "strikeMoney", 100));
		list.add(new JTableListColumn("可冲销金额", "remainMoney", 100));
		list.add(new JTableListColumn("报关单来源", "projectType", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("报关单资料");
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable table = dgCommonQuery.getJTable();
				table.getColumnModel().getColumn(11).setCellRenderer(
						new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								String str = "";
								if (value != null) {
									Integer projectType = Integer.valueOf(value
											.toString());
									switch (projectType) {
									case ProjectType.BCUS:
										str = "联网监管";
										break;
									case ProjectType.BCS:
										str = "纸质手册";
										break;
									case ProjectType.DZSC:
										str = "电子手册";
										break;
									}
								}
								this.setText(str);
								return this;
							}
						});
			}
		});
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 查询可以冲销的进口白单
	 * 
	 * @return
	 */
	public List<ImpCustomsDeclaration> findNotStrikeImpCustomsDeclaration(
			FecavBillStrike fecavBillStrike) {
		List dataSource = fecavAction.findNotStrikeImpCustomsDeclaration(
				new Request(CommonVars.getCurrUser(), true), fecavBillStrike);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("进口报关单号", "customsDeclarationCode", 100));
		list.add(new JTableListColumn("申报日期", "declareDate", 100));
		list.add(new JTableListColumn("白单号", "whiteBillNo", 200));
		list.add(new JTableListColumn("签收编码", "fecavBillStrike.signInNo", 100));
		list.add(new JTableListColumn("合同号", "contractNo", 100));
		list.add(new JTableListColumn("贸易方式", "tradeMode", 100));
		list.add(new JTableListColumn("手册编号 ", "emsNo", 200));
		list.add(new JTableListColumn("币制 ", "curr.name", 100));
		list.add(new JTableListColumn("报关单总金额", "totalMoney", 100));
		list.add(new JTableListColumn("冲销金额", "strikeMoney", 200));
		list.add(new JTableListColumn("可冲销金额", "remainMoney", 100));
		list.add(new JTableListColumn("折美元", "converUSDMoney", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("白单资料");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 查询可以冲销的汇票
	 * 
	 * @return
	 */
	public List findNotBrikeBillOfExchange(FecavBillStrike fecavBillStrike) {
		List dataSource = fecavAction.findNotBrikeBillOfExchange(new Request(
				CommonVars.getCurrUser()), fecavBillStrike);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("汇票号码", "billOfExchangeCode", 100));
		list.add(new JTableListColumn("结汇日期", "endDate", 100));
		list.add(new JTableListColumn("结汇金额", "exchangeMoney", 100));
		list.add(new JTableListColumn("已冲销金额", "strikeMoney", 100));
		list.add(new JTableListColumn("可冲销金额", "remainMoney", 100));
		list.add(new JTableListColumn("折美元", "converUSDMoney", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("汇票资料");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 根据状态查询外汇核销单
	 * 
	 * @param fecavState
	 * @return
	 */
	public List findFecavBillNotStrike(FecavBillStrike fecavBillStrike,
			Integer itemCount) {
		List dataSource = fecavAction.findFecavBillNotStrike(new Request(
				CommonVars.getCurrUser(), true), fecavBillStrike, itemCount);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("出口日期", "exportDate", 100));
		list.add(new JTableListColumn("申报日期", "declareDate", 100));
		list.add(new JTableListColumn("核销单号 ", "code", 200));
		list.add(new JTableListColumn("领单日期", "innerObtainDate", 100));
		list.add(new JTableListColumn("报关单号", "customsDeclarationCode", 200));
		list.add(new JTableListColumn("合同号码", "contractNo", 100));
		list.add(new JTableListColumn("手册号码", "emsNo", 100));
		list.add(new JTableListColumn("币别", "curr.name", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("核销单资料");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得分页查询的外汇核销单明细表
	 * 
	 * @return
	 */
	public void getFecavBillDetail() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("核销单号", "code", 70));
		list.add(new JTableListColumn("报关单号", "customsDeclarationCode", 100));
		list.add(new JTableListColumn("申报日期 ", "declareDate", 80));
		list.add(new JTableListColumn("出口日期", "exportDate", 80));
		list.add(new JTableListColumn("外部领用者", "outerObtain", 80));
		list.add(new JTableListColumn("外部领用日期", "outerObtainDate", 80));
		list.add(new JTableListColumn("内部领用者", "innerObtain", 80));
		list.add(new JTableListColumn("内部领用日期", "innerObtainDate", 80));
		list.add(new JTableListColumn("交单人", "handInBillMan", 80));
		list.add(new JTableListColumn("交单日期", "handInBillDate", 80));
		list.add(new JTableListColumn("退税签收人", "debentureSignInMan", 80));
		list.add(new JTableListColumn("退税签收日期", "debentureSignInDate", 80));
		list.add(new JTableListColumn("冲销者", "fecavBillStrike.strikeMan", 80));
		list.add(new JTableListColumn("冲销期", "fecavBillStrike.strikeDate", 80));
		list
				.add(new JTableListColumn("核销者",
						"fecavBillStrike.cavSignInMan", 80));
		list.add(new JTableListColumn("核销期", "fecavBillStrike.cavSignInDate",
				80));
		list.add(new JTableListColumn("财务签收者",
				"fecavBillStrike.financeSignInMan", 80));
		list.add(new JTableListColumn("财务签收日期",
				"fecavBillStrike.financeSignInDate", 80));
		list.add(new JTableListColumn("关帐人", "fecavBillStrike.closeAccountMan",
				80));
		list.add(new JTableListColumn("关帐日期",
				"fecavBillStrike.closeAccountDate", 80));

		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		final DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//          
				FecavAction fecavAction = (FecavAction) CommonVars
						.getApplicationContext().getBean("fecavAction");
				return fecavAction.findFecavBillDetail(new Request(CommonVars
						.getCurrUser()), index, length, property, value,
						isLike);
			}
		};
		dgCommonQuery.setSize(733, 541);
		dgCommonQuery.setTitle("外汇核销单明细表");
		DgCommonQueryPage.setSingleResult(true);
//		dgCommonQuery.setBottomPanelShow(false);
		dgCommonQuery.setVisible(true);
	}

	public List findNotBlankOutFecavBill() {
		List dataSource = fecavAction.findFecavBillByState(new Request(
				CommonVars.getCurrUser()), FecavState.INNER_OBTAIN);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		// List<Object> list = (List<Object>) (new Vector());
		list.add(new JTableListColumn("出口日期", "exportDate", 100));
		list.add(new JTableListColumn("申报日期", "declareDate", 100));
		list.add(new JTableListColumn("核销单号 ", "code", 200));
		list.add(new JTableListColumn("领单日期", "innerObtainDate", 100));
		list.add(new JTableListColumn("报关单号", "customsDeclarationCode", 200));
		list.add(new JTableListColumn("合同号码", "contractNo", 100));
		list.add(new JTableListColumn("手册号码", "emsNo", 100));
		list.add(new JTableListColumn("币别", "curr.name", 100));
		list.add(new JTableListColumn("总金额", "totalPrice", 100));
		list.add(new JTableListColumn("标志", "billState", 100));
		// ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("报关单资料");
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				TableColumn column = dgCommonQuery.getJTable().getColumnModel()
						.getColumn(10);
				column.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						int state = -1;
						if (value != null) {
							state = Integer.parseInt(value.toString());
						}
						this.setText(CommonVars.getFecavState(state));
						return this;
					}
				});
			}
		});

		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}
	/**
	 * 查询内部领单-lyh
	 * 
	 * @return
	 */
	public List findInnerObtainFecavBill() {
		List<FecavBill> dataSourceOld = new ArrayList();
		dataSourceOld  =  fecavAction
		.findFecavBillNotOuterObtain(new Request(
				CommonVars.getCurrUser()), "", new ArrayList());
		List dataSource = new ArrayList();
		for(FecavBill fb: dataSourceOld){
			if(fb.getBillState()==FecavState.INNER_OBTAIN){
				dataSource.add(fb);
			}
		}
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("出口日期", "exportDate", 100));
		list.add(new JTableListColumn("申报日期", "declareDate", 100));
		list.add(new JTableListColumn("核销单号 ", "code", 200));
		list.add(new JTableListColumn("领单日期", "innerObtainDate", 100));
		list.add(new JTableListColumn("报关单号", "customsDeclarationCode", 200));
		list.add(new JTableListColumn("合同号码", "contractNo", 100));
		list.add(new JTableListColumn("手册号码", "emsNo", 100));
		list.add(new JTableListColumn("币别", "curr.name", 100));
		list.add(new JTableListColumn("总金额", "totalPrice", 100));
		list.add(new JTableListColumn("标志", "billState", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("核销单领用资料");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter(){
			public void windowOpened(java.awt.event.WindowEvent e) {
				TableColumn column = dgCommonQuery.getJTable().getColumnModel()
						.getColumn(10);
				column.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						int state = -1;
						if (value != null) {
							state = Integer.parseInt(value.toString());
						}
						this.setText(CommonVars.getFecavState(state));
						return this;
					}
				});
			}
		});
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}
}
