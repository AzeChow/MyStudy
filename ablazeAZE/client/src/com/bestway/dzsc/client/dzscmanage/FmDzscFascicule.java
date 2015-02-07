/*
 * Created on 2004-7-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFas;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscBusinessType;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.client.message.DzscCommon;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHeadFas;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Styrle - Code Templates
 */
public class FmDzscFascicule extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JButton btnChange = null;

	private JButton btnApply = null;

	private JButton btnProcess = null;

	private JButton btnExit = null;

	private JButton jButton7 = null;

	private DzscAction dzscAction = null;

	private MessageAction messageAction = null;

	private SystemAction systemAction = null;

	private JTableListModel tableModel = null;

	private JTableListModel tableModelFas = null;

	private DzscEmsPorHead dzscEmsPorHead = null; // 电子手册表头

	private boolean isChange = false;

	private int dataState = DataState.BROWSE;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JTable tbEmsPorHead = null;

	private JScrollPane jScrollPane = null;

	private JTable tbFas = null;

	private JScrollPane jScrollPane1 = null;

	private List dataSource = null;

	private DzscEmsPorHeadFas fas = null;

	/**
	 * This is the default constructor
	 */
	public FmDzscFascicule() {
		super();

		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		messageAction = (MessageAction) CommonVars.getApplicationContext()
				.getBean("messageAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(600, 400);
		this.setTitle("手册分册备案申请");
		this.setContentPane(getJContentPane());
		dataSource = dzscAction.findDzscEmsPorHeadExcu1(new Request(CommonVars
				.getCurrUser())); // 电子手册(正在执行)
		if (dataSource != null && !dataSource.isEmpty()) {
			initTable(dataSource);
			dzscEmsPorHead = (DzscEmsPorHead) dataSource.get(0);
			String emsNo = dzscEmsPorHead.getEmsNo();
			if (dzscEmsPorHead.getEmsNo() == null) {
				emsNo = "";
			}
			List list = dzscAction.findDzscEmsPorHeadFas(new Request(CommonVars
					.getCurrUser()), emsNo);
			if (list != null && !list.isEmpty()) {
				initTableFas(list);
			} else {
				initTableFas(new Vector());
			}
		} else {
			initTable(new Vector());
			initTableFas(new Vector());
		}
		dataState = DataState.BROWSE;
		setState();
	}

	private void initTable(final List list) {

		tableModel = new JTableListModel(tbEmsPorHead, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("流水号", "seqNum", 50));
						list.add(addColumn("合同号", "ieContractNo", 100));
						list.add(addColumn("手册编号", "emsNo", 100));
						list.add(addColumn("起始日期", "beginDate", 100));
						list.add(addColumn("结束日期", "endDate", 100));
						list.add(addColumn("核销期限", "destroyDate", 100));
						list.add(addColumn("备注", "note", 100));
						list.add(addColumn("合同类型", "bargainType.name", 100));
						list.add(addColumn("合同状态", "declareState", 100));
						list.add(addColumn("延期期限", "deferDate", 100));
						list.add(addColumn("进出口岸", "iePort1.name", 100));
						list.add(addColumn("经营单位", "tradeName", 100));
						list.add(addColumn("贸易方式", "trade.name", 100));
						list.add(addColumn("收货单位", "machName", 100));
						list.add(addColumn("贸易国别", "tradeCountry.name", 100));
						list.add(addColumn("批文号", "sancEmsNo", 100));
						list.add(addColumn("协议书号", "agreementNo", 100));
						list.add(addColumn("出口合同号", "imContractNo", 100));
						list.add(addColumn("进口总值", "imgAmount", 100));
						list.add(addColumn("出口总值", "exgAmount", 100));
						list.add(addColumn("币制", "curr.name", 100));
						list.add(addColumn("监管费率", "wardshipRate", 100));
						list.add(addColumn("监管费", "wardshipFee", 100));
						list.add(addColumn("进出口岸二", "iePort2.name", 100));
						list.add(addColumn("进出口岸三", "iePort3.name", 100));
						list.add(addColumn("进出口岸四", "iePort4.name", 100));
						list.add(addColumn("进出口岸五", "iePort5.name", 100));
						list.add(addColumn("企业地址", "enterpriseAddress", 100));
						list.add(addColumn("联系人", "linkMan", 100));
						list.add(addColumn("联系电话", "contactTel", 100));

						return list;
					}
				});
		tbEmsPorHead.getColumnModel().getColumn(9).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(DzscState.APPLY)) {
							returnValue = "正在申请";
						} else if (value.equals(DzscState.EXECUTE)) {
							returnValue = "正在执行";
						} else if (value.equals(DzscState.CHANGE)) {
							returnValue = "正在变更";
						}
						return returnValue;
					}
				});

	}

	private void initTableFas(final List list) {
		tableModelFas = new JTableListModel(tbFas, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = new Vector<Object>();
						list.add(addColumn("电子手册编号", "emsNo", 100));
						list.add(addColumn("分册号", "fasEmsNo", 100));
						list.add(addColumn("分册口岸", "iePortFas.name", 100));
						list.add(addColumn("分册的期限", "limitDate", 100));
						list.add(addColumn("企业内部编号", "copEmsNo", 100));
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 150));
						list.add(addColumn("申报单位名称", "machName", 150));
						list.add(addColumn("审批状态", "declareState", 80));
						list.add(addColumn("修改标志", "modifyMark", 60));
						list.add(addColumn("进口原料项数", "materielItemCount", 100));
						list.add(addColumn("出口原料项数", "productItemCount", 100));
						list.add(addColumn("申报日期", "declareDate", 100));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		tbFas.getColumnModel().getColumn(9).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(DzscState.ORIGINAL)) {
							returnValue = "初始状态";

						} else if (value.equals(DzscState.APPLY))
							returnValue = "正在申请";
						else if (value.equals(DzscState.EXECUTE))
							returnValue = "正在执行";
						else if (value.equals(DzscState.CHANGE))
							returnValue = "正在变更";
						return returnValue;
					}
				});
		tbFas.getColumnModel().getColumn(10).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(ModifyMarkState.ADDED))
							returnValue = "新增";
						else if (value.equals(ModifyMarkState.DELETED))
							returnValue = "已删除";
						else if (value.equals(ModifyMarkState.MODIFIED))
							returnValue = "已修改";
						else if (value.equals(ModifyMarkState.UNCHANGE))
							returnValue = "未修改";
						return returnValue;
					}
				});

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnChange());
			jToolBar.add(getBtnDeclare());
			jToolBar.add(getBtnProcess());
			jToolBar.add(getJButton7());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnAdd() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("新增");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getRowCount() == 0) {
						JOptionPane.showMessageDialog(FmDzscFascicule.this,
								"电子手册数据为空，不能新增分册！", "提示", 2);
						return;
					}
					dzscEmsPorHead = (DzscEmsPorHead) tableModel
							.getCurrentRow();
					if (dzscEmsPorHead.getEmsNo() == null
							|| dzscEmsPorHead.getEmsNo().equals("")) {
						JOptionPane.showMessageDialog(FmDzscFascicule.this,
								"手册编号不能为空!", "提示", 2);
						return;
					}
					DzscEmsPorHeadFas fas = new DzscEmsPorHeadFas();
					addFas(dzscEmsPorHead, fas);
					fas = dzscAction.saveDzscEmsPorHeadFas(new Request(
							CommonVars.getCurrUser()), fas);
					tableModelFas.addRow(fas);
				}
			});

		}
		return jButton;
	}

	private void addFas(DzscEmsPorHead dzscEmsPorHead, DzscEmsPorHeadFas fas) {
		fas.setCompany(CommonVars.getCurrUser().getCompany());
		fas.setEmsNo(dzscEmsPorHead.getEmsNo()); // 手册编号
		// fas.setCopEmsNo(systemAction.generateAutoNo(EmsHeadH2kFas.class)); //
		// 企业内部编号
		fas.setDeclareState(DzscState.ORIGINAL);// 申报状态
		fas.setHistoryState(new Boolean(false)); // 历史状态
		fas.setModifyTimes(Integer.valueOf(0)); // 变更次数
		fas.setModifyMark(ModifyMarkState.ADDED);// 修改标志
		fas.setTradeCode(dzscEmsPorHead.getTradeCode());// 经营单位代码
		fas.setTradeName(dzscEmsPorHead.getTradeName());// 经营单位名称
		fas.setMachCode(dzscEmsPorHead.getMachCode());// 申报单位代码
		fas.setMachName(dzscEmsPorHead.getMachName());// 申报单位名称
		fas.setInputEr(CommonVars.getCurrUser().getLoginName());// 录入员代码
		fas.setImgAmount(dzscEmsPorHead.getImgAmount());
		fas.setExgAmount(dzscEmsPorHead.getExgAmount());
		fas.setMaterielItemCount(dzscEmsPorHead.getMaterielItemCount());
		fas.setProductItemCount(dzscEmsPorHead.getProductItemCount());
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String defaultDate = bartDateFormat.format(now);
		fas.setInputDate(java.sql.Date.valueOf(defaultDate)); // 录入日期
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					edit();
					setState();
				}
			});

		}
		return btnEdit;
	}

	private void edit() {
		if (tableModelFas.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmDzscFascicule.this, "请选择你将要修改的记录",
					"提示！", 0);
			return;
		}
		DgDzscFascicule dg = new DgDzscFascicule();
		dg.setChange(DelcareType.MODIFY
				.equals(((DzscEmsPorHeadFas) tableModelFas.getCurrentRow())
						.getDeclareType()));
		dg.setTableModel(tableModelFas);
		dg.setDataState(DataState.EDIT);
		dg.setVisible(true);
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelFas.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmDzscFascicule.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmDzscFascicule.this,
							"是否要删除该手册记录？", "确认", 0) == 0) {
						DzscEmsPorHeadFas emsEdi = (DzscEmsPorHeadFas) tableModelFas
								.getCurrentRow();
						dzscAction.deleteDzscEmsPorHeadFas(new Request(
								CommonVars.getCurrUser()), emsEdi);
						tableModelFas.deleteRow(emsEdi);
					}
					setState();
				}
			});

		}
		return btnDelete;
	}

	/**
	 * 
	 * This method initializes jButton3
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnChange() {
		if (btnChange == null) {
			btnChange = new JButton();
			btnChange.setText("变更");
			btnChange.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelFas.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmDzscFascicule.this,
								"请选择要变更的分册!", "提示", 2);
						return;
					}
					fas = (DzscEmsPorHeadFas) tableModelFas.getCurrentRow();
					DzscEmsPorHeadFas fasChange = dzscAction.emsHeadFasChange(
							new Request(CommonVars.getCurrUser()), fas);

					tableModelFas.addRow(fasChange);
					setState();
				}
			});

		}
		return btnChange;
	}

	/**
	 * 
	 * This method initializes jButton4
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnDeclare() {
		if (btnApply == null) {
			btnApply = new JButton();
			btnApply.setText("海关申报");
			btnApply.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelFas.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmDzscFascicule.this,
								"     请选择你将要申报的记录", "提示！", 0);
						return;
					}
					DzscEmsPorHeadFas data = (DzscEmsPorHeadFas) tableModelFas
							.getCurrentRow();
					try {
						DeclareFileInfo fileInfo = dzscAction
								.applyDzscEmsPorHeadFas(new Request(CommonVars
										.getCurrUser()), data);
						JOptionPane.showMessageDialog(FmDzscFascicule.this,
								fileInfo.getFileInfoSpec(), "提示",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(FmDzscFascicule.this,
								"申报失败 " + ex.getMessage(), "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					data.setDeclareState(DzscState.APPLY);
					data.setDeclareDate(new Date());
					tableModelFas.updateRow(data);
					setState();
				}
			});

		}
		return btnApply;
	}

	/**
	 * 
	 * This method initializes jButton5
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnProcess() {
		if (btnProcess == null) {
			btnProcess = new JButton();
			btnProcess.setText("回执处理");
			btnProcess.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelFas.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmDzscFascicule.this,
								"     请你选择将要处理的回执！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					DzscEmsPorHeadFas data = (DzscEmsPorHeadFas) tableModelFas
							.getCurrentRow();
					if (!data.getDeclareState().equals(DzscState.APPLY)) {
						JOptionPane.showMessageDialog(FmDzscFascicule.this,
								"      该分册还没有生成报文或报文已处理！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					List lsReturnFile=DzscCommon.getInstance().showDzscReceiptFile(
							DzscBusinessType.FASCICULE, data.getCopEmsNo());
					if (lsReturnFile.size()<=0) {
						return;
					}
					try {
						String result = dzscAction.proccessDzscEmsPorHeadFas(
								new Request(CommonVars.getCurrUser()), data,lsReturnFile);
						JOptionPane.showMessageDialog(FmDzscFascicule.this,
								"回执处理成功！" + result, "提示！",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(FmDzscFascicule.this,
								"回执处理失败！" + ex.getMessage(), "错误！",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					data.setDeclareState(DzscState.EXECUTE);
					tableModelFas.updateRow(data);
					setState();
					// JOptionPane.showMessageDialog(FmDzscFascicule.this,
					// "回执处理成功", "提示！", JOptionPane.INFORMATION_MESSAGE);
				}
			});

		}
		return btnProcess;
	}

	/**
	 * 
	 * This method initializes jButton6
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					FmDzscFascicule.this.dispose();

				}
			});

		}
		return btnExit;
	}

	/**
	 * 
	 * This method initializes jButton7
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setVisible(false);
			jButton7.setText("打印");
		}
		return jButton7;
	}

	/**
	 * @return Returns the isChange.
	 */
	public boolean isChange() {
		return isChange;
	}

	/**
	 * @param isChange
	 *            The isChange to set.
	 */
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	private void setState() {
		if (tableModelFas.getCurrentRow() == null) {
			return;
		}
		DzscEmsPorHeadFas data = (DzscEmsPorHeadFas) tableModelFas
				.getCurrentRow();
		String state = data.getDeclareState();
		this.btnEdit.setEnabled(state.equals(DzscState.ORIGINAL)
				|| state.equals(DzscState.CHANGE));
		this.btnDelete.setEnabled(state.equals(DzscState.ORIGINAL)
				|| state.equals(DzscState.CHANGE));
		this.btnChange.setEnabled(state.equals(DzscState.EXECUTE));
		this.btnApply.setEnabled(state.equals(DzscState.ORIGINAL)
				|| state.equals(DzscState.CHANGE));
		this.btnProcess.setEnabled(state.equals(DzscState.APPLY));
	}

	/**
	 * 
	 * This method initializes jSplitPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(200);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setDividerSize(3);
		}
		return jSplitPane;
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"请选择电子手册 (注：手册编号不可为空)",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					new java.awt.Font("Dialog", java.awt.Font.BOLD, 14),
					new java.awt.Color(0, 102, 51)));
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jTable
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getTbEmsPorHead() {
		if (tbEmsPorHead == null) {
			tbEmsPorHead = new JTable();
			tbEmsPorHead.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModel == null)
								return;
							if (tableModel.getCurrentRow() == null)
								return;

							DzscEmsPorHead emsHead = ((DzscEmsPorHead) tableModel
									.getCurrentRow());
							List listdetail = dzscAction.findDzscEmsPorHeadFas(
									new Request(CommonVars.getCurrUser()),
									emsHead.getEmsNo());

							// listdetail.add(bcsInnerMerge);
							if (listdetail != null && listdetail.size() > 0) {
								initTableFas(listdetail);
								setState();
							} else {
								initTableFas(new Vector());
							}

						}
					});
		}
		return tbEmsPorHead;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbEmsPorHead());
			jScrollPane
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes jTable1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getTbFas() {
		if (tbFas == null) {
			tbFas = new JTable();
			tbFas.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						DgDzscFascicule dg = new DgDzscFascicule();
						dg.setChange(DelcareType.MODIFY
								.equals(((DzscEmsPorHeadFas) tableModelFas
										.getCurrentRow()).getDeclareType()));
						dg.setTableModel(tableModelFas);
						dg.setDataState(DataState.BROWSE);
						dg.setVisible(true);
					}
				}
			});
			tbFas.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()
									|| tableModelFas == null) {
								return;
							}
							setState();
						}
					});
		}
		return tbFas;
	}

	/**
	 * 
	 * This method initializes jScrollPane1
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbFas());
			jScrollPane1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane1;
	}

}