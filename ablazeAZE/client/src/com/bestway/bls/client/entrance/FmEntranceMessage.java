package com.bestway.bls.client.entrance;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bls.action.BlsMessageAction;
import com.bestway.bls.action.EntranceMessageAction;
import com.bestway.bls.client.message.BlsMessageHelper;
import com.bestway.bls.client.storagebill.BlsCommonQuery;
import com.bestway.bls.client.storagebill.FmBaseDelivery;
import com.bestway.bls.client.storagebill.FmExportDelivery;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.BlsServiceType;
import com.bestway.bls.entity.Delivery;
import com.bestway.bls.entity.EntranceMessage;
import com.bestway.bls.entity.EntranceMessageIOFlag;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.GridBagLayout;
import javax.swing.JTabbedPane;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JSplitPane;

/**
 * 货到报告主窗体 edit by cjb 2009.7.7
 * 
 * @author hw
 * 
 */
public class FmEntranceMessage extends JInternalFrameBase {

	private JToolBar jJToolBarBar = null;

	private JScrollPane jScrollPane = null;

	/**
	 * 查询操作页面
	 */
	private PnCommonQueryPage pnCommonQueryPage = null;

	/**
	 * 查询操作页面2
	 */
	private PnCommonQueryPage pnCommonQueryPage2 = null;

	/**
	 * 货到报告表格
	 */
	private JTable tbEntranceMessage = null;

	/**
	 * 新增
	 */
	private JButton btnAdd = null;

	/**
	 * 海关申报
	 */
	private JButton btnApply = null;

	/**
	 * 回执处理
	 */
	private JButton btnProcess = null;

	/**
	 * 修改
	 */
	private JButton btnEdit = null;

	/**
	 * 删除
	 */
	private JButton btnDelete = null;

	/**
	 * 进口货到报告表格模型
	 */
	private JTableListModel tableModelIn = null;

	/**
	 * 出口货到报告表格模型
	 */
	private JTableListModel tableModelOut = null;

	/**
	 * 货到报告操作接口
	 */
	private EntranceMessageAction entranceMessageAction = null;

	/**
	 * 保税物流报文参数操作接口
	 */
	private BlsMessageAction blsMessageAction = null;

	/**
	 * 关闭退出
	 */
	private JButton btnExit = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbEntranceMessageOut = null;

	/**
	 * 更新
	 */
	private JButton btnRefresh = null;

	private JToolBar jJToolBarBar1 = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JToolBar jJToolBarBar11 = null;

	private EntranceMessage entranceMessage = null; // @jve:decl-index=0:

	/**
	 * 构造方法 This method initializes
	 * 
	 */
	public FmEntranceMessage() {
		super();
		initialize();
		entranceMessageAction = (EntranceMessageAction) CommonVars
				.getApplicationContext().getBean("entranceMessageAction");
		blsMessageAction = (BlsMessageAction) CommonVars
				.getApplicationContext().getBean("blsMessageAction");
		List list = entranceMessageAction.findEntranceMessage(new Request(
				CommonVars.getCurrUser()), "I");
		initTable(list);
		getPnCommonQueryPage();
		// getPnCommonQueryPage2();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(763, 513));
		this.setContentPane(getJSplitPane());
		this.setTitle("货到报告");

	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getBtnAdd());
			jJToolBarBar.add(getBtnEdit());
			jJToolBarBar.add(getBtnDelete());
			jJToolBarBar.add(getBtnApply());
			jJToolBarBar.add(getBtnProcess());
			jJToolBarBar.add(getBtnRefresh());
			jJToolBarBar.add(getBtnExit());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbEntranceMessage());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbEntranceMessage
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbEntranceMessage() {
		if (tbEntranceMessage == null) {
			tbEntranceMessage = new JTable();
			tbEntranceMessage
					.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseClicked(java.awt.event.MouseEvent e) {
							if (e.getClickCount() == 2) {
								if (tableModelIn.getCurrentRow() == null) {
									JOptionPane.showMessageDialog(
											FmEntranceMessage.this,
											"请选择要修改的数据！", "提示！",
											JOptionPane.WARNING_MESSAGE);
									return;
								}
								DgEntranceMessage dg = new DgEntranceMessage();
								dg.setTableModel(tableModelIn);
								dg
										.setEntranceMessage((EntranceMessage) tableModelIn
												.getCurrentRow());
								dg.setDataState(DataState.BROWSE);
								dg.setVisible(true);
							} else if (e.getClickCount() == 1) {
								setState();
							}
						}
					});

			tbEntranceMessage.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbEntranceMessage
									.getModel();
							if (tableModel == null) {
								return;
							}
							try {
								if (tableModel.getCurrentRow() != null) {
									setState();
								}
							} catch (Exception cx) {

							}
						}
					});
		}
		return tbEntranceMessage;
	}

	/**
	 * 设置组建状态
	 */
	private void setState() {
		if (jTabbedPane.getSelectedIndex() == 0
				&& tableModelIn.getCurrentRow() != null) {
			entranceMessage = (EntranceMessage) tableModelIn.getCurrentRow();
			// boolean ed = dery.getEffective() == null ? false : dery
			// .getEffective();
		} else if (jTabbedPane.getSelectedIndex() == 1
				&& tableModelOut.getCurrentRow() != null) {
			entranceMessage = (EntranceMessage) tableModelOut.getCurrentRow();
		}
		btnEdit
				.setEnabled((entranceMessage.getDeclareState() == null || DeclareState.APPLY_POR
						.equals(entranceMessage.getDeclareState())));
		btnDelete
				.setEnabled((entranceMessage.getDeclareState() == null || DeclareState.APPLY_POR
						.equals(entranceMessage.getDeclareState())));
		btnApply
				.setEnabled((entranceMessage.getDeclareState() == null || DeclareState.APPLY_POR
						.equals(entranceMessage.getDeclareState())));
		btnProcess
				.setEnabled((entranceMessage.getDeclareState() != null && entranceMessage
						.getDeclareState() == DeclareState.WAIT_EAA));
	}

	/**
	 * 初始化表体数据
	 */
	private JTableListModel initTable(List list) {
		tableModelIn = new JTableListModel(tbEntranceMessage, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("企业编码", "tradeCo.code", 100));
						list.add(addColumn("业务类型", "operType", 100));
						list.add(addColumn("车次", "deliveryNo", 100));
						list.add(addColumn("进出口标志", "ioFlag", 60));
						list.add(addColumn("申报状态", "declareState", 100));
						list.add(addColumn("车牌号", "vehicleLicense", 100));
						list.add(addColumn("承运商代码", "carrierCode", 100));
						list.add(addColumn("承运商名称", "carrierName", 190));
						list.add(addColumn("仓单数", "billCount", 100));
						list.add(addColumn("净重", "netWeight", 100));
						list.add(addColumn("毛重", "grossWeight", 100));
						list.add(addColumn("第1个集装箱号", "containerNo1", 100));
						list.add(addColumn("第2个集装箱号", "containerNo2", 100));
						list.add(addColumn("第1关锁号", "sealNo1", 100));
						list.add(addColumn("第2关锁号", "sealNo2", 100));

						return list;
					}
				});
		// tbContract.getColumnModel().getColumn(1).setCellRenderer(new
		// TableMultiRowRender());
		tbEntranceMessage.getColumnModel().getColumn(4).setCellRenderer(
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
						return EntranceMessageIOFlag.getImpExpFlagSpec(value
								.toString());
					}

				});
		// tbEntranceMessage.getColumnModel().getColumn(14).setCellRenderer(
		// new DefaultTableCellRenderer() {
		// public Component getTableCellRendererComponent(
		// JTable table, Object value, boolean isSelected,
		// boolean hasFocus, int row, int column) {
		// super.getTableCellRendererComponent(table, value,
		// isSelected, hasFocus, row, column);
		// super
		// .setText((value == null) ? castValue(DeclareState.APPLY_POR)
		// : castValue(value));
		// return this;
		// }
		//
		// private String castValue(Object value) {
		// return DeclareState.getDeclareStateSpec(value
		// .toString());
		// }
		// });

		tbEntranceMessage.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super
								.setText((value == null) ? castValue(DeclareState.APPLY_POR)
										: castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return DeclareState.getDeclareStateSpec(value
								.toString());
					}
				});
		return tableModelIn;
	}

	/**
	 * 初始化表体数据 出口
	 */
	private JTableListModel initTableOut(List list) {
		tableModelOut = new JTableListModel(tbEntranceMessageOut, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("企业编码", "tradeCo.code", 100));
						list.add(addColumn("业务类型", "operType", 100));
						list.add(addColumn("车次", "deliveryNo", 100));
						list.add(addColumn("进出口标志", "ioFlag", 60));
						list.add(addColumn("申报状态", "declareState", 100));
						list.add(addColumn("车牌号", "vehicleLicense", 100));
						list.add(addColumn("承运商代码", "carrierCode", 100));
						list.add(addColumn("承运商名称", "carrierName", 190));
						list.add(addColumn("仓单数", "billCount", 100));
						list.add(addColumn("净重", "netWeight", 100));
						list.add(addColumn("毛重", "grossWeight", 100));
						list.add(addColumn("第1个集装箱号", "containerNo1", 100));
						list.add(addColumn("第2个集装箱号", "containerNo2", 100));
						list.add(addColumn("第1关锁号", "sealNo1", 100));
						list.add(addColumn("第2关锁号", "sealNo2", 100));

						return list;
					}
				});
		// tbContract.getColumnModel().getColumn(1).setCellRenderer(new
		// TableMultiRowRender());
		tbEntranceMessageOut.getColumnModel().getColumn(4).setCellRenderer(
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
						return EntranceMessageIOFlag.getImpExpFlagSpec(value
								.toString());
					}

				});
		// tbEntranceMessage.getColumnModel().getColumn(14).setCellRenderer(
		// new DefaultTableCellRenderer() {
		// public Component getTableCellRendererComponent(
		// JTable table, Object value, boolean isSelected,
		// boolean hasFocus, int row, int column) {
		// super.getTableCellRendererComponent(table, value,
		// isSelected, hasFocus, row, column);
		// super
		// .setText((value == null) ? castValue(DeclareState.APPLY_POR)
		// : castValue(value));
		// return this;
		// }
		//
		// private String castValue(Object value) {
		// return DeclareState.getDeclareStateSpec(value
		// .toString());
		// }
		// });

		tbEntranceMessageOut.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super
								.setText((value == null) ? castValue(DeclareState.APPLY_POR)
										: castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return DeclareState.getDeclareStateSpec(value
								.toString());
					}
				});
		return tableModelOut;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String ioFlag = "I";
					if (jTabbedPane.getSelectedIndex() == 0) {
						ioFlag = "I";
					} else {
						ioFlag = "O";
					}
					List list = (List) getDelivery(ioFlag);
					if (list == null || list.size() <= 0) {
						return;
					}
					// 判断是否重复
					List list2 = null;
					// for (int i = 0; i < list.size(); i++) {
					// Delivery delivery = (Delivery) list
					// .get(i);
					// String deliveryNo = delivery.getDeliveryNo();
					// list2 = entranceMessageAction.findDeliveryByDeliveryNo(
					// new Request(CommonVars.getCurrUser()),
					// deliveryNo);
					// }
					// if (!list2.isEmpty()) {
					// JOptionPane.showMessageDialog(FmEntranceMessage.this,
					// "车次号已存在，请重新选择！", "提示！",
					// JOptionPane.WARNING_MESSAGE);
					// return;
					// }

					List deliveryList = entranceMessageAction.importDelivery(
							new Request(CommonVars.getCurrUser()), list);
					for (int i = 0; i < deliveryList.size(); i++) {
						EntranceMessage entranceMessage = (EntranceMessage) deliveryList
								.get(i);
						String deliveryNo = entranceMessage.getDeliveryNo();
						list2 = entranceMessageAction.findDeliveryByDeliveryNo(
								new Request(CommonVars.getCurrUser()),
								deliveryNo);
					}
					if (jTabbedPane.getSelectedIndex() == 0) {
						tableModelIn.addRows(deliveryList);
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						tableModelOut.addRows(deliveryList);
					}
				}
			});
		}
		return btnAdd;
	}

	/**
	 * 获得车次对象
	 */
	public Object getDelivery(String ioFlag) {
		List list = new Vector();
		list.add(new JTableListColumn("企业编码", "tradeCo.code", 80));
		list.add(new JTableListColumn("业务类型", "operType", 80));
		list.add(new JTableListColumn("车次号", "deliveryNo", 100));
		list.add(new JTableListColumn("车牌号", "vehicleLicense", 100));
		list.add(new JTableListColumn("承运单位名称", "carrierCode", 100));
		list.add(new JTableListColumn("仓单数", "billCount", 50));
		list.add(new JTableListColumn("预计到达日期", "schedularArrivalDate", 100));
		list.add(new JTableListColumn("该车载货物件数", "packNo", 50));
		list.add(new JTableListColumn("毛重", "grossWeight", 50));
		list.add(new JTableListColumn("净重", "netWeight", 50));
		list.add(new JTableListColumn("第1个集装箱号", "containerNo1", 100));
		list.add(new JTableListColumn("第2个集装箱号", "containerNo2", 100));
		list.add(new JTableListColumn("第1关锁号", "sealNo1", 100));
		list.add(new JTableListColumn("第2关锁号", "sealNo2", 100));
		list.add(new JTableListColumn("修改标志", "modifyMark", 50));
		list.add(new JTableListColumn("报文发送时间", "messTimeStamp", 100));
		list.add(new JTableListColumn("申报状态", "declareState", 50));
		DgCommonQuery.setTableColumns(list);
		EntranceMessageAction entranceMessageAction = (EntranceMessageAction) CommonVars
				.getApplicationContext().getBean("entranceMessageAction");
		List em = entranceMessageAction.findEntranceMessage(new Request(
				CommonVars.getCurrUser()));
		List dataSource = entranceMessageAction.findAndFiltrateDeliveryNo(
				new Request(CommonVars.getCurrUser()), ioFlag);
		// System.out.println("dataSource.size()="+dataSource.size());
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("车次查询");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * This method initializes btnApply
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnApply() {
		if (btnApply == null) {
			btnApply = new JButton();
			btnApply.setText("海关申报");
			btnApply.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (blsMessageAction.checkIsAutoDeclare(new Request(
							CommonVars.getCurrUser()))) {
						JOptionPane.showMessageDialog(FmEntranceMessage.this,
								"系统设定自动进行海关申报，所以不能进行手动申报！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					EntranceMessage entranceMessage = null;
					if (jTabbedPane.getSelectedIndex() == 0) {
						entranceMessage = (EntranceMessage) tableModelIn
								.getCurrentRow();
					} else {
						entranceMessage = (EntranceMessage) tableModelOut
								.getCurrentRow();
					}

					if (entranceMessage == null) {
						JOptionPane.showMessageDialog(FmEntranceMessage.this,
								"请选择你将要申报的记录", "提示！", 0);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmEntranceMessage.this,
							"确定要进行海关申报吗？", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					// String id = entranceMessage.getId();
					//
					// entranceMessage = (EntranceMessage) entranceMessageAction
					// .load(EntranceMessage.class, id);
					// EntranceMessage entranceMessage = (EntranceMessage)
					// tableModel
					// .getCurrentRow();
					entranceMessage = entranceMessageAction.applyFreightage(
							new Request(CommonVars.getCurrUser()),
							entranceMessage);
					String declareInfo = "";
					if (DeclareState.PROCESS_EXE.equals(entranceMessage
							.getDeclareState())) {
						declareInfo = ("货到报告申报"
								+ entranceMessage.getDeliveryNo() + " 申报成功！");
					} else if (DeclareState.APPLY_POR.equals(entranceMessage
							.getDeclareState())) {
						declareInfo = ("货到报告申报"
								+ entranceMessage.getDeliveryNo() + " 申报失败！");
					} else if (DeclareState.WAIT_EAA.equals(entranceMessage
							.getDeclareState())) {
						declareInfo = ("货到报告申报"
								+ entranceMessage.getDeliveryNo() + " 正在等待审批！");
					}
					if (jTabbedPane.getSelectedIndex() == 0) {
						tableModelIn.updateRow(entranceMessage);
					} else {
						tableModelOut.updateRow(entranceMessage);
					}
					setState();
					JOptionPane
							.showMessageDialog(FmEntranceMessage.this,
									declareInfo, "提示！",
									JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return btnApply;
	}

	/**
	 * This method initializes btnProcess
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnProcess() {
		if (btnProcess == null) {
			btnProcess = new JButton();
			btnProcess.setText("回执处理");
			btnProcess.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (blsMessageAction.checkIsAutoDeclare(new Request(
							CommonVars.getCurrUser()))) {
						JOptionPane.showMessageDialog(FmEntranceMessage.this,
								"系统设定自动进行回执处理，所以不能进行手动回执处理！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					EntranceMessage entranceMessage = null;
					if (jTabbedPane.getSelectedIndex() == 0) {
						entranceMessage = (EntranceMessage) tableModelIn
								.getCurrentRow();
					} else {
						entranceMessage = (EntranceMessage) tableModelOut
								.getCurrentRow();
					}
					if (entranceMessage == null) {
						JOptionPane.showMessageDialog(FmEntranceMessage.this,
								"请选择要回执处理的货到报告资料！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmEntranceMessage.this,
							"确定要进行回执处理吗？", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					// EntranceMessage entranceMessage = (EntranceMessage)
					// tableModel
					// .getCurrentRow();

					String id = entranceMessage.getId();

					entranceMessage = (EntranceMessage) entranceMessageAction
							.load(EntranceMessage.class, id);

					BlsReceiptResult blsReceiptResult = BlsMessageHelper
							.getInstance().showBlsReceiptFile(
									BlsServiceType.FREIGHTAGE_DECLARE,
									entranceMessage.getId());
					if (blsReceiptResult == null) {
						return;
					}
					entranceMessage = entranceMessageAction.processFreightage(
							new Request(CommonVars.getCurrUser()),
							entranceMessage, blsReceiptResult);
					String resultInfo = "";
					if (DeclareState.PROCESS_EXE.equals(entranceMessage
							.getDeclareState())) {
						resultInfo = ("货到报告申报"
								+ entranceMessage.getDeliveryNo() + " 申报成功！");
					} else if (DeclareState.APPLY_POR.equals(entranceMessage
							.getDeclareState())) {
						resultInfo = ("货到报告申报"
								+ entranceMessage.getDeliveryNo() + " 申报失败！");
					} else if (DeclareState.WAIT_EAA.equals(entranceMessage
							.getDeclareState())) {
						resultInfo = ("货到报告申报"
								+ entranceMessage.getDeliveryNo() + " 正在等待审批！");
					}
					tableModelIn.updateRow(entranceMessage);
					JOptionPane.showMessageDialog(FmEntranceMessage.this,
							resultInfo, "提示！", JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return btnProcess;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						if (tableModelIn.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									FmEntranceMessage.this, "没有数据，或请选择数据!",
									"提示", 0);
							return;
						}
						DgEntranceMessage dg = new DgEntranceMessage();
						dg.setTableModel(tableModelIn);
						EntranceMessage entranceMessage = (EntranceMessage) tableModelIn
								.getCurrentRow();
						entranceMessage = (EntranceMessage) entranceMessageAction
								.load(EntranceMessage.class, entranceMessage
										.getId());
						tableModelIn.updateRow(entranceMessage);
						dg.setEntranceMessage(entranceMessage);
						dg.setDataState(DataState.EDIT);
						dg.setVisible(true);
					} else {
						if (tableModelOut.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									FmEntranceMessage.this, "没有数据，或请选择数据!",
									"提示", 0);
							return;
						}
						DgEntranceMessage dg = new DgEntranceMessage();
						dg.setTableModel(tableModelOut);
						EntranceMessage entranceMessage = (EntranceMessage) tableModelOut
								.getCurrentRow();
						entranceMessage = (EntranceMessage) entranceMessageAction
								.load(EntranceMessage.class, entranceMessage
										.getId());
						tableModelOut.updateRow(entranceMessage);
						dg.setEntranceMessage(entranceMessage);
						dg.setDataState(DataState.EDIT);
						dg.setVisible(true);
					}
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						if (tableModelIn.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									FmEntranceMessage.this, "请选择要删除的数据行!",
									"提示", 0);
							return;
						}
					} else {
						if (tableModelOut.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									FmEntranceMessage.this, "请选择要删除的数据行!",
									"提示", 0);
							return;
						}
					}
					deleteRow();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * 删除一行信息
	 */
	public void deleteRow() {
		if (jTabbedPane.getSelectedIndex() == 0) {
			if (tableModelIn.getCurrentRows() != null) {
				if (JOptionPane.showConfirmDialog(this, "是否确定删除数据!", "提示", 0) != 0) {
					return;
				}
			}
			EntranceMessage entranceMessage = (EntranceMessage) tableModelIn
					.getCurrentRow();
			entranceMessageAction.deleteEntranceMessage(new Request(CommonVars
					.getCurrUser()), entranceMessage);
			tableModelIn.deleteRow(entranceMessage);
		} else {
			if (tableModelOut.getCurrentRows() != null) {
				if (JOptionPane.showConfirmDialog(this, "是否确定删除数据!", "提示", 0) != 0) {
					return;
				}
			}
			EntranceMessage entranceMessage = (EntranceMessage) tableModelOut
					.getCurrentRow();
			entranceMessageAction.deleteEntranceMessage(new Request(CommonVars
					.getCurrUser()), entranceMessage);
			tableModelOut.deleteRow(entranceMessage);
		}
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	@Override
	public void setVisible(boolean f) {
		if (f) {
			this.pnCommonQueryPage.setInitState();
			// this.pnCommonQueryPage2.setInitState();
			if (this.entranceMessage == null) {
				this.entranceMessage = new EntranceMessage();
			}
			setState();
			super.setVisible(f);
		}

	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("进口", null, getJPanel2(), null);
			jTabbedPane.addTab("出口", null, getJPanel3(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (jTabbedPane.getSelectedIndex() == 0) {
								List list = entranceMessageAction
										.findEntranceMessage(new Request(
												CommonVars.getCurrUser()), "I");
								initTable(list);
								pnCommonQueryPage.setInitState();
							} else {
								List list = entranceMessageAction
										.findEntranceMessage(new Request(
												CommonVars.getCurrUser()), "O");
								initTableOut(list);
								pnCommonQueryPage.setInitState();
							}
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbEntranceMessageOut());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbEntranceMessage1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbEntranceMessageOut() {
		if (tbEntranceMessageOut == null) {
			tbEntranceMessageOut = new JTable();
			tbEntranceMessageOut
					.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseClicked(java.awt.event.MouseEvent e) {
							if (e.getClickCount() == 2) {
								if (tableModelOut.getCurrentRow() == null) {
									JOptionPane.showMessageDialog(
											FmEntranceMessage.this,
											"请选择要修改的数据！", "提示！",
											JOptionPane.WARNING_MESSAGE);
									return;
								}
								DgEntranceMessage dg = new DgEntranceMessage();
								dg.setTableModel(tableModelOut);
								dg
										.setEntranceMessage((EntranceMessage) tableModelOut
												.getCurrentRow());
								dg.setDataState(DataState.BROWSE);
								dg.setVisible(true);
							} else if (e.getClickCount() == 1) {
								setState();
							}
						}
					});

			tbEntranceMessageOut.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbEntranceMessageOut
									.getModel();
							if (tableModel == null) {
								return;
							}
							try {
								if (tableModel.getCurrentRow() != null) {
									setState();
								}
							} catch (Exception cx) {

							}
						}
					});
		}
		return tbEntranceMessageOut;
	}

	/**
	 * This method initializes btnRefresh
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.setFont(new Font("Dialog", Font.BOLD, 12));
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						List list = entranceMessageAction.findEntranceMessage(
								new Request(CommonVars.getCurrUser()), "I");
						initTable(list);
					} else {
						List list = entranceMessageAction.findEntranceMessage(
								new Request(CommonVars.getCurrUser()), "O");
						initTableOut(list);
					}
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * 公共查询组件
	 * 
	 * @return
	 */
	private PnCommonQueryPage getPnCommonQueryPage() {
		if (pnCommonQueryPage == null) {
			pnCommonQueryPage = new PnCommonQueryPage() {
				@Override
				public JTableListModel initTable(List dataSource) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						return FmEntranceMessage.this.initTable(dataSource);
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						return FmEntranceMessage.this.initTableOut(dataSource);
					}
					return null;
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						return FmEntranceMessage.this.getDataSourceIn(index,
								length, property, value, isLike);
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						return FmEntranceMessage.this.getDataSourceOut(index,
								length, property, value, isLike);
					}
					return null;

				}

			};
		}
		return pnCommonQueryPage;
	}

	// /**
	// * 公共查询组件
	// *
	// * @return
	// */
	// private PnCommonQueryPage getPnCommonQueryPage2() {
	// if (pnCommonQueryPage2 == null) {
	// pnCommonQueryPage2 = new PnCommonQueryPage() {
	// @Override
	// public JTableListModel initTable(List dataSource) {
	// return FmEntranceMessage.this.initTable1(dataSource);
	// }
	//
	// @Override
	// public List getDataSource(int index, int length,
	// String property, Object value, boolean isLike) {
	//
	// return FmEntranceMessage.this.getDataSource(index, length,
	// property, value, isLike);
	// }
	//
	// };
	// }
	// return pnCommonQueryPage2;
	// }

	private List getDataSourceIn(int index, int length, String property,
			Object value, boolean isLike) {
		String inout = "I";
		// if (jTabbedPane.getSelectedIndex() == 1) {
		// inout = "O";
		// }
		return this.entranceMessageAction.findEntranceMessage(new Request(
				CommonVars.getCurrUser()), index, length, property, value,
				isLike, inout);
	}

	private List getDataSourceOut(int index, int length, String property,
			Object value, boolean isLike) {
		String inout = "O";
		// if (jTabbedPane.getSelectedIndex() == 1) {
		// inout = "O";
		// }
		return this.entranceMessageAction.findEntranceMessage(new Request(
				CommonVars.getCurrUser()), index, length, property, value,
				isLike, inout);
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(5);
			jSplitPane.setDividerLocation(29);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setOneTouchExpandable(true);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJJToolBarBar(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJJToolBarBar11(), BorderLayout.NORTH);
			jPanel1.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jJToolBarBar11
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar11() {
		if (jJToolBarBar11 == null) {
			jJToolBarBar11 = new JToolBar();
			jJToolBarBar11.add(getPnCommonQueryPage());
		}
		return jJToolBarBar11;
	}
} // @jve:decl-index=0:visual-constraint="12,18"
