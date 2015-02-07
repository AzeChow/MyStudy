package com.bestway.wjsz.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ContractKind;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscEmsType;
import com.bestway.common.constant.DzscState;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.waijing.action.WjszAction;

public class FmSContractHead extends JInternalFrameBase {

	private JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel4 = null;
	private JRadioButton rbDzsc = null;
	private JRadioButton rbBcs = null;
	private JButton btnUpload = null;
	private JButton jButton2 = null;
	private ButtonGroup group = new ButtonGroup();
	private JTableListModel tableModel = null;// 表头
	private JTableListModel tableModelImg = null;// 料件
	private JTableListModel tableModelExg = null;// 成品
	private JTableListModel tableModelBom = null;// 单耗
	private JLabel jLabel = null;
	private WjszAction wjszAction = null;
	private int selectIndex = 0;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel5 = null;
	private JScrollPane jScrollPane = null;
	private JTable tbHead = null;
	private JScrollPane jScrollPane1 = null;
	private JTable tbImg = null;
	private JSplitPane jSplitPane1 = null;
	private JScrollPane jScrollPane2 = null;
	private JTable tbExg = null;
	private JScrollPane jScrollPane3 = null;
	private JTable tbBom = null;
	private JButton jButton3 = null;
	private JButton jButton4 = null;
	private JButton btnWjSeqNum = null;
	private JButton btnDownload = null;
	private JButton btnRefresh = null;

	/**
	 * This is the default constructor
	 */
	public FmSContractHead() {
		super();
		wjszAction = (WjszAction) CommonVars.getApplicationContext().getBean(
				"wjszAction");
		initialize();
		WjszClientUtil.login();
		com.bestway.jptds.client.common.CommonVars.getApplicationContext();
		selectIndex = (Integer) wjszAction.getConModule(new Request(CommonVars
				.getCurrUser()));
		moduSelect();
	}

	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(1088, 537);
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameClosed(
							javax.swing.event.InternalFrameEvent e) {
						wjszAction.saveConModuleSelect(new Request(CommonVars
								.getCurrUser()), selectIndex);
					}
				});
		this.setContentPane(getJContentPane());
		this.setTitle("合同数据交换");
		group.add(rbDzsc);
		group.add(rbBcs);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(0);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setDividerLocation(120);
		}
		return jSplitPane;
	}

	/**
	 * 电子手册通关备案表头
	 */
	public JTableListModel initTableDzscHead(JTable jTable) {
		List list = (List) wjszAction.findDzscEmsPorHead(new Request(CommonVars
				.getCurrUser()));
		if (list != null && list.size() > 0) {
			return initTableDzscHead(jTable, list);
		} else {
			return initTableDzscHead(jTable, new Vector());
		}
	}

	private JTableListModel initTableDzscHead(JTable jTable, final List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 80));
				list.add(addColumn("流水号", "seqNum", 50));
				list.add(addColumn("外经申请文号", "wjComputerNo", 200));
				list.add(addColumn("合同号", "ieContractNo", 100));
				list.add(addColumn("手册编号", "emsNo", 100));
				list.add(addColumn("起始日期", "beginDate", 100));
				list.add(addColumn("结束日期", "endDate", 100));
				list.add(addColumn("核销期限", "destroyDate", 100));
				list.add(addColumn("备注", "note", 100));
				list.add(addColumn("合同类型", "emsType", 100));
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
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new MultiRenderer());
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));

		jTable.getColumnModel().getColumn(11).setCellRenderer(
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
						if (value.equals(DzscState.ORIGINAL)) {
							returnValue = "原始状态";
						} else if (value.equals(DzscState.APPLY)) {
							returnValue = "正在申请";
						} else if (value.equals(DzscState.EXECUTE)) {
							returnValue = "正在执行";
						} else if (value.equals(DzscState.CHANGE)) {
							returnValue = "正在变更";
						} else if (value.equals(DzscState.BACK_BILL)) {
							returnValue = "退单状态";
						} else if (value.equals(DzscState.CHECK_CANCEL)) {
							returnValue = "核销状态";
						}
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(10).setCellRenderer(
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
						if (value.equals(DzscEmsType.COME_PROCESS_EMS_BILL)) {
							returnValue = "加工贸易来料手册";
						} else if (value
								.equals(DzscEmsType.IMPORT_PROCESS_EMS_BILL)) {
							returnValue = "加工贸易进料手册";
						}
						if (value.equals(DzscEmsType.MACHINE_PROCESS_EMS_BILL)) {
							returnValue = "加工贸易设备手册";
						}
						return returnValue;
					}
				});
		return tableModel;
	}

	/**
	 * render table JchcckBox 列
	 */
	class MultiRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				value = false;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
				checkBox.setHorizontalAlignment(JLabel.CENTER);
				checkBox.setBackground(table.getBackground());
				if (isSelected) {
					checkBox.setForeground(table.getSelectionForeground());
					checkBox.setBackground(table.getSelectionBackground());
				} else {
					checkBox.setForeground(table.getForeground());
					checkBox.setBackground(table.getBackground());
				}
				return checkBox;
			}
			String str = (value == null) ? "" : value.toString();
			return super.getTableCellRendererComponent(table, str, isSelected,
					hasFocus, row, column);
		}
	}

	/**
	 * 编辑列
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;

		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				value = false;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb
						.setSelected(Boolean.valueOf(value.toString())
								.booleanValue());
			}
			cb.setHorizontalAlignment(JLabel.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof DzscEmsPorHead) {
				DzscEmsPorHead temp = (DzscEmsPorHead) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}

	}

	private void moduSelect() {
		jTabbedPane.setSelectedIndex(0);
		if (selectIndex == 0) {// 电子帐册/经营范围
			return;
		} else if (selectIndex == 1) {// 电子手册
			// jLabel.setText("提示：只来自通关备案【原始状态】或【正在变更】状态下！");
			rbDzsc.setSelected(true);
			tableModel = initTableDzscHead(tbHead);
			tableModelImg = SClientLogic.initTableDzscPorImg(tbImg, null);
			tableModelBom = SClientLogic.initTableDzscPorBom(tbBom, null);
			tableModelExg = SClientLogic.initTableDzscPorExg(tbExg, null);

		} else if (selectIndex == 2) {// 电子化手册
			// jLabel.setText("提示：只来自通关备案【原始状态】或【正在变更】状态下！");
			rbBcs.setSelected(true);
			tableModel = initTableContractHead(tbHead);
			tableModelImg = SClientLogic.initTableContractImg(tbImg, null);
			tableModelBom = SClientLogic.initTableContractBom(tbBom, null);
			tableModelExg = SClientLogic.initTableContractExg(tbExg, null);

		}
		btnWjSeqNum.setEnabled(rbDzsc.isSelected());
	}

	/**
	 * 刷新合同头数据
	 */
	private void refreshHead() {
		if (selectIndex == 0) {// 电子帐册/经营范围
			return;
		} else if (selectIndex == 1) {// 电子手册
			tableModel = initTableDzscHead(tbHead);
		} else if (selectIndex == 2) {// 电子化手册
			tableModel = initTableContractHead(tbHead);
		}
	}

	/**
	 * 刷新成品和料件的数据
	 */
	private void refreshImgExgData() {
		if (selectIndex == 0) {// 电子帐册/经营范围
			return;
		} else if (selectIndex == 1) {// 电子手册
			DzscEmsPorHead porHead = (DzscEmsPorHead) tableModel
					.getCurrentRow();
			tableModelImg = SClientLogic.initTableDzscPorImg(tbImg, porHead);
			tableModelExg = SClientLogic.initTableDzscPorExg(tbExg, porHead);
		} else if (selectIndex == 2) {// 电子化手册
			Contract head = (Contract) tableModel.getCurrentRow();
			tableModelImg = SClientLogic.initTableContractImg(tbImg, head);
			tableModelExg = SClientLogic.initTableContractExg(tbExg, head);

		}
	}

	/**
	 * 电子化手册备案表头
	 */
	public JTableListModel initTableContractHead(JTable jTable) {
		List list = (List) wjszAction.findContractHead(new Request(CommonVars
				.getCurrUser()));
		if (list != null && list.size() > 0) {
			return initTableContractHead(jTable, list);
		} else {
			return initTableContractHead(jTable, new Vector());
		}
	}

	/**
	 * 初始化数据Table
	 */
	private JTableListModel initTableContractHead(JTable jTable, final List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("是否选择", "isSelected", 100));
				list.add(addColumn("手册编号", "emsNo", 100));
				list.add(addColumn("外经申请文号", "wjComputerNo", 200));
				list.add(addColumn("进口合同号", "impContractNo", 100));
				list.add(addColumn("出口合同号", "expContractNo", 100));
				list.add(addColumn("合同状态", "declareState", 60));
				list.add(addColumn("合同性质", "emsType", 100));
				list.add(addColumn("开始有效期", "beginDate", 100));
				list.add(addColumn("有效期限", "endDate", 100));
				list.add(addColumn("延期期限", "deferDate", 100));
				list.add(addColumn("核销日期", "destroyDate", 100));
				list.add(addColumn("企业内部编号", "copEmsNo", 100));
				list.add(addColumn("报送关区", "declareCustoms.name", 100));

				list.add(addColumn("批文帐册号", "sancEmsNo", 100));
				list.add(addColumn("经营单位代码", "tradeCode", 100));
				list.add(addColumn("经营单位名称", "tradeName", 100));

				list.add(addColumn("进口口岸", "iePort1.name", 100));
				list.add(addColumn("外商公司", "outTradeCo", 100));
				list.add(addColumn("征免性质", "levyKind.name", 50));
				list.add(addColumn("保税方式", "payMode.name", 50));
				list.add(addColumn("加工种类", "machiningType.name", 50));
				list.add(addColumn("备案批准日期", "newApprDate", 100));
				list.add(addColumn("变更批准日期", "changeApprDate", 100));
				list.add(addColumn("批准证号", "emsApprNo", 100));
				list.add(addColumn("审批标志", "checkMark", 100));
				list.add(addColumn("执行标志", "exeMark", 100));
				list.add(addColumn("贸易方式", "trade.name", 100));
				list.add(addColumn("贸易国别", "tradeCountry.name", 100));
				list.add(addColumn("企业地址", "enterpriseAddress", 100));
				list.add(addColumn("联系人", "linkMan", 100));
				list.add(addColumn("联系电话", "contactTel", 100));
				list.add(addColumn("协义书号", "agreementNo", 100));

				list.add(addColumn("进口总金额", "imgAmount", 100));
				list.add(addColumn("出口总金额", "exgAmount", 100));
				list.add(addColumn("币制", "curr.name", 100));
				list.add(addColumn("监管费用", "wardshipFee", 100));
				list.add(addColumn("监管费率", "wardshipRate", 100));
				list.add(addColumn("成交方式", "transac.name", 100));
				list.add(addColumn("进口口岸2", "iePort2.name", 100));
				list.add(addColumn("进口口岸3", "iePort3.name", 100));
				list.add(addColumn("进口口岸4", "iePort4.name", 100));
				list.add(addColumn("进口口岸5", "iePort5.name", 100));
				list.add(addColumn("审批人", "approver", 100));
				list.add(addColumn("审批日期", "approveDate", 100));
				list.add(addColumn("许可证号", "permitNo", 100));
				list.add(addColumn("备注", "memo", 100));
				return list;
			}
		};

		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new MultiRenderer1());
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor1(new JCheckBox()));

		jTable.getColumnModel().getColumn(6).setCellRenderer(
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
						return DeclareState.getDeclareStateSpec(value
								.toString());
					}
				});

		jTable.getColumnModel().getColumn(7).setCellRenderer(
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
						return ContractKind.getContractKindSpec(value
								.toString().trim());
					}
				});
		return tableModel;
	}

	/**
	 * render table JchcckBox 列
	 */
	class MultiRenderer1 extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				value = false;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
				checkBox.setHorizontalAlignment(JLabel.CENTER);
				checkBox.setBackground(table.getBackground());
				if (isSelected) {
					checkBox.setForeground(table.getSelectionForeground());
					checkBox.setBackground(table.getSelectionBackground());
				} else {
					checkBox.setForeground(table.getForeground());
					checkBox.setBackground(table.getBackground());
				}
				return checkBox;
			}
			String str = (value == null) ? "" : value.toString();
			return super.getTableCellRendererComponent(table, str, isSelected,
					hasFocus, row, column);
		}
	}

	/**
	 * 编辑列
	 */
	class CheckBoxEditor1 extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;

		private JTable table = null;

		public CheckBoxEditor1(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				value = false;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb
						.setSelected(Boolean.valueOf(value.toString())
								.booleanValue());
			}
			cb.setHorizontalAlignment(JLabel.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof Contract) {
				Contract temp = (Contract) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(10, 54, 672, 25));
			jLabel.setForeground(java.awt.Color.blue);
			jLabel.setText("");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJPanel4(), null);
			jPanel.add(getBtnUpload(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(jLabel, null);
			jPanel.add(getJButton3(), null);
			jPanel.add(getJButton4(), null);
			jPanel.add(getBtnWjSeqNum(), null);
			jPanel.add(getBtnDownload(), null);
			jPanel.add(getBtnRefresh(), null);
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
			jPanel1.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.TOP);
			jTabbedPane.addTab("表头", null, getJPanel2(), null);
			jTabbedPane.addTab("料件总表", null, getJPanel5(), null);
			jTabbedPane.addTab("成品与单损耗", null, getJPanel3(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							JTabbedPane tab = (JTabbedPane) e.getSource();
							if (tab.getSelectedIndex() == 1) { // -----------------------料件
								if (selectIndex == 0) {// 电子帐册/经营范围
									return;
								} else if (selectIndex == 1) {// 电子手册
									DzscEmsPorHead porHead = (DzscEmsPorHead) tableModel
											.getCurrentRow();
									if (porHead == null) {
										JOptionPane.showMessageDialog(
												FmSContractHead.this,
												"请选择表头资料！", "提示", 2);
										jTabbedPane.setSelectedIndex(0);
										return;
									}
									tableModelImg = SClientLogic
											.initTableDzscPorImg(tbImg, porHead);
								} else if (selectIndex == 2) {// 电子化手册
									Contract porHead = (Contract) tableModel
											.getCurrentRow();
									if (porHead == null) {
										JOptionPane.showMessageDialog(
												FmSContractHead.this,
												"请选择表头资料！", "提示", 2);
										jTabbedPane.setSelectedIndex(0);
										return;
									}
									tableModelImg = SClientLogic
											.initTableContractImg(tbImg,
													porHead);
								} else if (selectIndex == 3) {// 普通手册
									return;
								}
							} else if (tab.getSelectedIndex() == 2) {// ------------------成品
								if (selectIndex == 0) {// 电子帐册/经营范围
									return;
								} else if (selectIndex == 1) {// 电子手册
									DzscEmsPorHead porHead = (DzscEmsPorHead) tableModel
											.getCurrentRow();
									if (porHead == null) {
										JOptionPane.showMessageDialog(
												FmSContractHead.this,
												"请选择表头资料！", "提示", 2);
										jTabbedPane.setSelectedIndex(0);
										return;
									}
									tableModelExg = SClientLogic
											.initTableDzscPorExg(tbExg, porHead);
									DzscEmsExgBill exg = (DzscEmsExgBill) tableModelExg
											.getCurrentRow();
									tableModelBom = SClientLogic
											.initTableDzscPorBom(tbBom, exg);
								} else if (selectIndex == 2) {// 电子化手册
									Contract porHead = (Contract) tableModel
											.getCurrentRow();
									if (porHead == null) {
										JOptionPane.showMessageDialog(
												FmSContractHead.this,
												"请选择表头资料！", "提示", 2);
										jTabbedPane.setSelectedIndex(0);
										return;
									}
									tableModelExg = SClientLogic
											.initTableContractExg(tbExg,
													porHead);
									ContractExg exg = (ContractExg) tableModelExg
											.getCurrentRow();
									tableModelBom = SClientLogic
											.initTableContractBom(tbBom, exg);
								} else if (selectIndex == 3) {// 普通手册
									return;
								}
							}
						}
					});

		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setBounds(new java.awt.Rectangle(8, 7, 673, 45));
			jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "模式选择",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					java.awt.Color.blue));
			jPanel4.add(getRbDzsc(), null);
			jPanel4.add(getRbBcs(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes rbDzsc
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbDzsc() {
		if (rbDzsc == null) {
			rbDzsc = new JRadioButton();
			rbDzsc.setBounds(new java.awt.Rectangle(14, 14, 148, 21));
			rbDzsc.setText("电子手册(通关备案)");
			rbDzsc.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectIndex = 1;
					moduSelect();
					wjszAction.saveConModuleSelect(new Request(CommonVars
							.getCurrUser()), selectIndex);
				}
			});
		}
		return rbDzsc;
	}

	/**
	 * This method initializes rbBcs
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBcs() {
		if (rbBcs == null) {
			rbBcs = new JRadioButton();
			rbBcs.setBounds(new java.awt.Rectangle(168, 14, 164, 21));
			rbBcs.setText("电子化手册(通关备案)");
			rbBcs.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectIndex = 2;
					moduSelect();
					wjszAction.saveConModuleSelect(new Request(CommonVars
							.getCurrUser()), selectIndex);
				}
			});
		}
		return rbBcs;
	}

	/**
	 * This method initializes btnUpload
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUpload() {
		if (btnUpload == null) {
			btnUpload = new JButton();
			btnUpload.setBounds(new Rectangle(20, 87, 99, 25));
			btnUpload.setText("上传(选择)");
			btnUpload.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (selectIndex == 1) {// 电子手册
						List<DzscEmsPorHead> selectLs = new ArrayList<DzscEmsPorHead>();
						List allList = tableModel.getList();
						for (int i = 0; i < allList.size(); i++) {
							DzscEmsPorHead head = (DzscEmsPorHead) allList
									.get(i);
							if (head.getIsSelected() != null
									&& head.getIsSelected().equals(
											new Boolean(true))) {
								selectLs.add(head);
							}
						}

						if (selectLs.size() < 1) {
							JOptionPane.showMessageDialog(FmSContractHead.this,
									"请选择要上传的资料", "提示", 2);
							return;
						}

						if (JOptionPane.showConfirmDialog(FmSContractHead.this,
								"您确定要将选择的手册上传到外经系统吗?", "提示",
								JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
							return;
						}

						boolean isConver = false;
						if (JOptionPane.showConfirmDialog(FmSContractHead.this,
								"是否覆盖已存在且未审核的资料?", "提示",
								JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
							isConver = true;
						}

						for (int j = 0; j < selectLs.size(); j++) {
							DzscEmsPorHead head = (DzscEmsPorHead) selectLs
									.get(j);
							String message = SUploadLogic.uploadDataDzsc(
									isConver, head);
							JOptionPane.showMessageDialog(FmSContractHead.this,
									message, "提示", 2);
						}
						refreshHead();
					} else if (selectIndex == 2) {// 电子化手册

						List<Contract> selectLs = new ArrayList<Contract>();
						List allList = tableModel.getList();
						for (int i = 0; i < allList.size(); i++) {
							Contract head = (Contract) allList.get(i);
							if (head.getIsSelected() != null
									&& head.getIsSelected().equals(
											new Boolean(true))) {
								selectLs.add(head);
							}
						}

						if (selectLs.size() < 1) {
							JOptionPane.showMessageDialog(FmSContractHead.this,
									"请选择要上传的资料", "提示", 2);
							return;
						}

						if (JOptionPane.showConfirmDialog(FmSContractHead.this,
								"您确定要将选择的手册上传到外经系统吗?", "提示",
								JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
							return;
						}

						boolean isConver = false;
						if (JOptionPane.showConfirmDialog(FmSContractHead.this,
								"是否覆盖已存在且未审核的资料?", "提示",
								JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
							isConver = true;
						}

						for (int j = 0; j < selectLs.size(); j++) {
							Contract head = (Contract) selectLs.get(j);
							String message = SUploadLogic.uploadDataContract(
									isConver, head);
							JOptionPane.showMessageDialog(FmSContractHead.this,
									message, "提示", 2);
						}
						refreshHead();
					}

				}
			});
		}
		return btnUpload;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(771, 87, 78, 25));
			jButton2.setText("退出");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton2;
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
			jPanel2.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jPanel3.add(getJSplitPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbHead());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbHead
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbHead() {
		if (tbHead == null) {
			tbHead = new JTable();
		}
		return tbHead;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbImg());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbImg
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImg() {
		if (tbImg == null) {
			tbImg = new JTable();
		}
		return tbImg;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setDividerLocation(200);
			jSplitPane1.setTopComponent(getJScrollPane2());
			jSplitPane1.setBottomComponent(getJScrollPane3());
			jSplitPane1.setDividerSize(0);
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbExg());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes tbExg
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExg() {
		if (tbExg == null) {
			tbExg = new JTable();
			tbExg.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (selectIndex == 0) {// 电子帐册/经营范围
								return;
							} else if (selectIndex == 1) {// 电子手册
								if (tableModelExg == null)
									return;
								if (tableModelExg.getCurrentRow() == null)
									return;
								DzscEmsExgBill exg = (DzscEmsExgBill) tableModelExg
										.getCurrentRow();
								tableModelBom = SClientLogic
										.initTableDzscPorBom(tbBom, exg);
							} else if (selectIndex == 2) {// 电子化手册
								if (tableModelExg == null)
									return;
								if (tableModelExg.getCurrentRow() == null)
									return;
								ContractExg exg = (ContractExg) tableModelExg
										.getCurrentRow();
								tableModelBom = SClientLogic
										.initTableContractBom(tbBom, exg);
							}

						}
					});
		}
		return tbExg;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getTbBom());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes tbBom
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBom() {
		if (tbBom == null) {
			tbBom = new JTable();
		}
		return tbBom;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(215, 87, 180, 25));
			jButton3.setText("删除外经申请文号(选择)");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (selectIndex == 1) {
						List allList = tableModel.getList();
						for (int i = 0; i < allList.size(); i++) {
							DzscEmsPorHead head = (DzscEmsPorHead) allList
									.get(i);
							if (head.getIsSelected() != null
									&& head.getIsSelected().equals(
											new Boolean(true))) {
								head.setWjComputerNo(null);
								head = (DzscEmsPorHead) wjszAction
										.saveDzscEmsPorHead(new Request(
												CommonVars.getCurrUser()), head);
								tableModel.updateRow(head);
							}
						}
					} else if (selectIndex == 2) {

						List allList = tableModel.getList();
						for (int i = 0; i < allList.size(); i++) {
							Contract head = (Contract) allList.get(i);
							if (head.getIsSelected() != null
									&& head.getIsSelected().equals(
											new Boolean(true))) {
								head.setWjComputerNo(null);
								head = (Contract) wjszAction.saveContract(
										new Request(CommonVars.getCurrUser()),
										head);
								tableModel.updateRow(head);
							}
						}
					}

				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(new Rectangle(402, 87, 144, 25));
			jButton4.setText("修改外经申请文号");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgSContractWjCode dg = new DgSContractWjCode();
					dg.setTableModel(tableModel);
					dg.setSelectInt(selectIndex);
					dg.setDataState(DataState.EDIT);
					dg.setVisible(true);

				}
			});
		}
		return jButton4;
	}

	/**
	 * This method initializes btnWjSeqNum
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnWjSeqNum() {
		if (btnWjSeqNum == null) {
			btnWjSeqNum = new JButton();
			btnWjSeqNum.setBounds(new Rectangle(563, 87, 114, 25));
			btnWjSeqNum.setText("修改合同序号");
			btnWjSeqNum.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (selectIndex == 1) {// 电子手册
						if (jTabbedPane.getSelectedIndex() == 1) {// 料件
							DgSContractWjSeqNum dg = new DgSContractWjSeqNum();
							dg.setLj(true);
							dg.setTableModel(tableModelImg);
							dg.setSelectInt(selectIndex);
							dg.setDataState(DataState.EDIT);
							dg.setVisible(true);
						} else if (jTabbedPane.getSelectedIndex() == 2) {
							DgSContractWjSeqNum dg = new DgSContractWjSeqNum();
							dg.setLj(false);
							dg.setTableModel(tableModelExg);
							dg.setSelectInt(selectIndex);
							dg.setDataState(DataState.EDIT);
							dg.setVisible(true);
						}
					}

				}
			});
		}
		return btnWjSeqNum;
	}

	/**
	 * This method initializes btnDownload
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDownload() {
		if (btnDownload == null) {
			btnDownload = new JButton();
			btnDownload.setBounds(new Rectangle(126, 87, 73, 25));
			btnDownload.setText("下载");
			btnDownload.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (selectIndex == 1) {// 电子手册
						List<DzscEmsPorHead> selectLs = new ArrayList<DzscEmsPorHead>();
						List allList = tableModel.getList();
						for (int i = 0; i < allList.size(); i++) {
							DzscEmsPorHead head = (DzscEmsPorHead) allList
									.get(i);
							if (head.getIsSelected() != null
									&& head.getIsSelected().equals(
											new Boolean(true))) {
								selectLs.add(head);
							}
						}

						if (selectLs.size() < 1) {
							JOptionPane.showMessageDialog(FmSContractHead.this,
									"请选择要下载的资料", "提示", 2);
							return;
						}
						boolean isConver = false;
						if (JOptionPane.showConfirmDialog(FmSContractHead.this,
								"是否覆盖已存在且未审核的资料?", "提示",
								JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
							isConver = true;
						}
						for (DzscEmsPorHead dzscEmsPorHead : selectLs) {
							if (DzscState.EXECUTE.equals(dzscEmsPorHead
									.getDeclareState())) {
								JOptionPane.showMessageDialog(
										FmSContractHead.this, "手册"
												+ dzscEmsPorHead.getCopTrNo()
												+ "正在执行，所以不能导入。");
								continue;
							}
							if (DzscState.APPLY.equals(dzscEmsPorHead
									.getDeclareState())) {
								JOptionPane.showMessageDialog(
										FmSContractHead.this, "手册"
												+ dzscEmsPorHead.getCopTrNo()
												+ "正在申报，所以不能导入。");
								continue;
							}
							String message = SDownloadLogic.downloadDataDzsc(
									isConver, dzscEmsPorHead);
							JOptionPane.showMessageDialog(FmSContractHead.this,
									message);
							refreshImgExgData();
						}
					} else if (selectIndex == 2) {// 电子化手册
						List<Contract> selectLs = new ArrayList<Contract>();
						List allList = tableModel.getList();
						for (int i = 0; i < allList.size(); i++) {
							Contract head = (Contract) allList.get(i);
							if (head.getIsSelected() != null
									&& head.getIsSelected().equals(
											new Boolean(true))) {
								selectLs.add(head);
							}
						}
						if (selectLs.size() < 1) {
							JOptionPane.showMessageDialog(FmSContractHead.this,
									"请选择要下载的资料", "提示", 2);
							return;
						}
						boolean isConver = false;
						if (JOptionPane.showConfirmDialog(FmSContractHead.this,
								"是否覆盖已存在且未审核的资料?", "提示",
								JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
							isConver = true;
						}
						for (Contract contract : selectLs) {
							if (DeclareState.PROCESS_EXE.equals(contract
									.getDeclareState())) {
								JOptionPane.showMessageDialog(
										FmSContractHead.this, "手册"
												+ contract.getCopEmsNo()
												+ "正在执行，所以不能导入。");
								continue;
							}
							if (DeclareState.WAIT_EAA.equals(contract
									.getDeclareState())) {
								JOptionPane.showMessageDialog(
										FmSContractHead.this, "手册"
												+ contract.getCopEmsNo()
												+ "正在申报，所以不能导入。");
								continue;
							}
							String message = SDownloadLogic
									.downloadDataContract(isConver, contract);
							JOptionPane.showMessageDialog(FmSContractHead.this,
									message);
							refreshImgExgData();
						}
					}
				}
			});
		}
		return btnDownload;
	}

	/**
	 * This method initializes btnRefresh	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setBounds(new Rectangle(685, 86, 76, 26));
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					moduSelect();
				}
			});
		}
		return btnRefresh;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
