package com.bestway.bls.client.message;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bls.action.BlsMessageAction;
import com.bestway.bls.entity.BlsServiceType;
import com.bestway.bls.entity.EntranceMessageIOFlag;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class FmAutoDeclareInfo extends JInternalFrameBase {

	private JPanel jContentPane = null;
	private JTabbedPane jTabbedPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JScrollPane jScrollPane = null;
	private JTable tbBackBill = null;
	private JScrollPane jScrollPane1 = null;
	private JTable tbAutoDeclare = null;
	private JRadioButton rbInDelivery = null;
	private JRadioButton rbOutDelivery = null;
	private JRadioButton rbOutFeightage = null;
	private JRadioButton rbInFeightage = null;
	private JLabel lbBeginDate = null;
	private JLabel lbEndDate = null;
	private JButton btnQuery = null;
	private JButton btnDelete = null;
	private JLabel jLabel2 = null;
	private JCalendarComboBox cbbBeginDate = null;
	private JCalendarComboBox cbbEndDate = null;
	private JTextField tfDeliveryNo = null;
	private JPanel jPanel1 = null;
	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="640,30"
	private BlsMessageAction blsMessageAction = null;
	private JTableListModel tableModelAutoDeclare = null;
	private JTableListModel tableModelBackBill = null;
	private JRadioButton rbCollateBind = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmAutoDeclareInfo() {
		super();
		initialize();
		this.getButtonGroup();
		blsMessageAction = (BlsMessageAction) CommonVars
				.getApplicationContext().getBean("blsMessageAction");
		this.cbbBeginDate.setDate(null);
		this.cbbEndDate.setDate(null);
		setState();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(725, 412));
		this.setContentPane(getJContentPane());
		this.setTitle("自动申报处理信息");

		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						queryData();
					}
				});
	}

	private void setState() {
		this.lbBeginDate.setVisible(jTabbedPane.getSelectedIndex() == 0);
		this.lbEndDate.setVisible(jTabbedPane.getSelectedIndex() == 0);
		this.cbbBeginDate.setVisible(jTabbedPane.getSelectedIndex() == 0);
		this.cbbEndDate.setVisible(jTabbedPane.getSelectedIndex() == 0);
		this.jLabel2.setText(this.rbCollateBind.isSelected() ? "单证编号" : "车次号");
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
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("自动申报及回执处理信息", null, getJScrollPane1(), null);
			jTabbedPane.addTab("自动申报退单信息", null, getJScrollPane(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							setState();
						}
					});
		}
		return jTabbedPane;
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
			jSplitPane.setDividerLocation(125);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJTabbedPane());
			jSplitPane.setDividerSize(5);
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
			jLabel2 = new JLabel();
			jLabel2.setText("车次号");
			jLabel2.setBounds(new Rectangle(28, 58, 59, 22));
			lbEndDate = new JLabel();
			lbEndDate.setText("结束日期");
			lbEndDate.setBounds(new Rectangle(222, 88, 55, 22));
			lbBeginDate = new JLabel();
			lbBeginDate.setText("开始日期");
			lbBeginDate.setBounds(new Rectangle(28, 88, 58, 22));
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(lbBeginDate, null);
			jPanel.add(lbEndDate, null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnDelete(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getTfDeliveryNo(), null);
			jPanel.add(getJPanel1(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbBackBill());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBackBill() {
		if (tbBackBill == null) {
			tbBackBill = new JTable();
		}
		return tbBackBill;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbAutoDeclare());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbAutoDeclare() {
		if (tbAutoDeclare == null) {
			tbAutoDeclare = new JTable();
		}
		return tbAutoDeclare;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbInDelivery() {
		if (rbInDelivery == null) {
			rbInDelivery = new JRadioButton();
			rbInDelivery.setText("进仓车次");
			rbInDelivery.setSelected(true);
			rbInDelivery.setBounds(new Rectangle(34, 19, 77, 21));
			rbInDelivery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setState();
				}
			});
		}
		return rbInDelivery;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbOutDelivery() {
		if (rbOutDelivery == null) {
			rbOutDelivery = new JRadioButton();
			rbOutDelivery.setText("出仓车次");
			rbOutDelivery.setBounds(new Rectangle(145, 19, 77, 21));
			rbOutDelivery
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setState();
						}
					});
		}
		return rbOutDelivery;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbOutFeightage() {
		if (rbOutFeightage == null) {
			rbOutFeightage = new JRadioButton();
			rbOutFeightage.setText("出仓货到报告");
			rbOutFeightage.setBounds(new Rectangle(371, 19, 109, 21));
			rbOutFeightage
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setState();
						}
					});
		}
		return rbOutFeightage;
	}

	/**
	 * This method initializes jRadioButton3
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbInFeightage() {
		if (rbInFeightage == null) {
			rbInFeightage = new JRadioButton();
			rbInFeightage.setText("进仓货到报告");
			rbInFeightage.setBounds(new Rectangle(245, 19, 108, 21));
			rbInFeightage
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setState();
						}
					});
		}
		return rbInFeightage;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setBounds(new Rectangle(553, 89, 69, 22));
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					queryData();
				}
			});
		}
		return btnQuery;
	}

	/**
	 * 查询数据
	 */
	private void queryData() {
		String serviceType = "";
		String inOutFlag = "";
		if (this.rbCollateBind.isSelected()) {
			serviceType = BlsServiceType.COLLATEBIND_DECLARE;
		} else if (this.rbInDelivery.isSelected()
				|| this.rbOutDelivery.isSelected()) {
			serviceType = BlsServiceType.MANIFEST_DECLARE;
		} else {
			serviceType = BlsServiceType.FREIGHTAGE_DECLARE;
		}
		if (this.rbInDelivery.isSelected() || this.rbInFeightage.isSelected()) {
			inOutFlag = "I";
		} else if (this.rbOutDelivery.isSelected()
				|| this.rbOutFeightage.isSelected()) {
			inOutFlag = "O";
		} else {
			inOutFlag = "";
		}
		String deliveryNo = this.tfDeliveryNo.getText().trim();
		Date beginDate = this.cbbBeginDate.getDate();
		Date endDate = this.cbbEndDate.getDate();
		if (jTabbedPane.getSelectedIndex() == 0) {
			List list = blsMessageAction.findBlsAutoDeclareProcessInfo(
					new Request(CommonVars.getCurrUser()), serviceType,
					inOutFlag, deliveryNo, beginDate, endDate);
			this.initAutoDeclareTable(list);
		} else {
			List list = blsMessageAction.findBlsAutoBackBillInfo(new Request(
					CommonVars.getCurrUser()), serviceType, inOutFlag,
					deliveryNo);
			this.initBackBillTable(list);
		}
	}

	/**
	 * 显示自动申报回执处理的内容
	 * 
	 * @param list
	 */
	private void initAutoDeclareTable(List list) {
		tableModelAutoDeclare = new JTableListModel(tbAutoDeclare, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("系统类型", "serviceType", 100));
						list.add(addColumn("申报 或 处理回执", "isAutoDeclare", 100));
						list.add(addColumn("进仓或出仓", "inOut", 100));
						list.add(addColumn("仓单编号", "billCode", 100));
						list.add(addColumn("时间", "declareProcessDate", "",
								"yyyy-MM-dd HH:mm:SSS", 150));
						list.add(addColumn("申报结果", "declareProcessResult", 80));
						list.add(addColumn("备注", "memo", 150));
						return list;
					}
				});
		tbAutoDeclare.getColumnModel().getColumn(1).setCellRenderer(
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
						return BlsServiceType.getServiceDescByType(value
								.toString());
					}

				});
		tbAutoDeclare.getColumnModel().getColumn(2).setCellRenderer(
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
						boolean b = Boolean.valueOf(value.toString().trim());
						if (b) {
							return "自动申报";
						} else {
							return "自动回执处理";
						}
					}

				});
		tbAutoDeclare.getColumnModel().getColumn(3).setCellRenderer(
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
		tbAutoDeclare.getColumnModel().getColumn(6).setCellRenderer(
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
						String s = value.toString().trim();
						if ("0".equals(s)) {
							return "退单";
						} else if ("1".equals(s)) {
							return "通过";
						} else if ("2".equals(s)) {
							return "正在审批";
						} else if ("3".equals(s)) {
							return "申报时出错";
						}
						return "不识别的类型：" + s;
					}

				});
	}

	/**
	 * 显示自动申报回执处理的内容
	 * 
	 * @param list
	 */
	private void initBackBillTable(List list) {
		tableModelBackBill = new JTableListModel(tbBackBill, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("系统类型", "serviceType", 100));
						list.add(addColumn("进仓或出仓", "inOut", 100));
						list.add(addColumn("仓单编号", "billCode", 100));
						list.add(addColumn("退单(或发送失败)次数", "declareTimes", 150));
						list.add(addColumn("退单时间", "declareDates", 150));

						return list;
					}
				});
		tbBackBill.getColumnModel().getColumn(1).setCellRenderer(
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
						return BlsServiceType.getServiceDescByType(value
								.toString());
					}

				});
		tbBackBill.getColumnModel().getColumn(2).setCellRenderer(
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
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setBounds(new Rectangle(627, 89, 61, 22));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					deleteData();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * 数据
	 */
	private void deleteData() {

		if (jTabbedPane.getSelectedIndex() == 0) {
			List list = tableModelAutoDeclare.getCurrentRows();
			if (list.size() <= 0) {
				JOptionPane.showMessageDialog(this, "请选择你要删除的数据");
				return;
			}
			blsMessageAction.deleteBlsAutoDeclareProcessInfo(new Request(
					CommonVars.getCurrUser()), list);
			tableModelAutoDeclare.deleteRows(list);
		} else {
			List list = tableModelBackBill.getCurrentRows();
			if (list.size() <= 0) {
				JOptionPane.showMessageDialog(this, "请选择你要删除的数据");
				return;
			}
			blsMessageAction.deleteBlsAutoBackBillInfo(new Request(CommonVars
					.getCurrUser()), list);
			tableModelBackBill.deleteRows(list);
		}
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(89, 88, 107, 22));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(280, 88, 100, 22));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDeliveryNo() {
		if (tfDeliveryNo == null) {
			tfDeliveryNo = new JTextField();
			tfDeliveryNo.setBounds(new Rectangle(90, 58, 600, 22));
		}
		return tfDeliveryNo;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBorder(BorderFactory.createTitledBorder(null,
					"\u7cfb\u7edf\u7c7b\u578b",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel1.setBounds(new Rectangle(29, 5, 661, 48));
			jPanel1.add(getRbInDelivery(), null);
			jPanel1.add(getRbOutDelivery(), null);
			jPanel1.add(getRbInFeightage(), null);
			jPanel1.add(getRbOutFeightage(), null);
			jPanel1.add(getRbCollateBind(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getRbInDelivery());
			buttonGroup.add(this.getRbOutDelivery());
			buttonGroup.add(this.getRbInFeightage());
			buttonGroup.add(this.getRbOutFeightage());
			buttonGroup.add(this.getRbCollateBind());
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbCollateBind() {
		if (rbCollateBind == null) {
			rbCollateBind = new JRadioButton();
			rbCollateBind.setBounds(new Rectangle(490, 19, 75, 21));
			rbCollateBind.setText("核销");
			rbCollateBind
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setState();
						}
					});
		}
		return rbCollateBind;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
