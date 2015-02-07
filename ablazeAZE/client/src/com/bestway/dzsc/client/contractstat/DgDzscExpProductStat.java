/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.contractstat;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.dzsc.client.dzscmanage.JDzscEmsPorHeadList;
import com.bestway.dzsc.contractstat.action.DzscStatAction;
import com.bestway.dzsc.contractstat.entity.DzscExpProductStat;
import com.bestway.dzsc.contractstat.entity.DzscExpProductStatResult;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.message.action.DzscMessageAction;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.SwingConstants;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import com.bestway.bcs.client.contractanalyse.JContractList;
import com.bestway.bcs.contract.entity.Contract;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscExpProductStat extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JTextField tfContractExpTotalMoney = null;

	private JTextField tfExpTotalMoney = null;

	private JTextField tfExpScale = null;

	private DzscEmsPorHead contract = null;  //  @jve:decl-index=0:

	private JTableListModel tableModel = null;

	private DzscStatAction contractStatAction = null;

	private JButton jButton2 = null;

	private DzscParameterSet dzscParameterSet = null;

	private DzscMessageAction dzscMessageAction = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JLabel jLabel5 = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel jLabel6 = null;

	private JCalendarComboBox cbbEndDate = null;

	private JCheckBox cbDetachCompute = null;

	private JRadioButton rbYes = null;

	private JRadioButton rbNo = null;

	private JRadioButton rbAll = null;

	private JPanel jPanel4 = null;

	private JPanel jPanel5 = null;

	private JRadioButton jRadioButton10 = null;

	private JRadioButton jRadioButton9 = null;

	private JCheckBox cbContractExe = null;

	private JCheckBox cbContractCancel = null;

	private JScrollPane jScrollPane1 = null;

	private JDzscEmsPorHeadList jList11 = null;

	private ButtonGroup buttonGroup = null;  //  @jve:decl-index=0:
	
	private ButtonGroup buttonGroup1 = null;  //  @jve:decl-index=0:
	/**
	 * @return Returns the contract.
	 */
	public DzscEmsPorHead getContract() {
		return contract;
	}

	/**
	 * @param contract
	 *            The contract to set.
	 */
	public void setContract(DzscEmsPorHead contract) {
		this.contract = contract;
	}

	/**
	 * This is the default constructor
	 */
	public DgDzscExpProductStat() {
		super();
		initialize();
		getButtonGroup();
		getButtonGroup1();
		contractStatAction = (DzscStatAction) CommonVars
				.getApplicationContext().getBean("dzscStatAction");
		dzscMessageAction = (DzscMessageAction) CommonVars
				.getApplicationContext().getBean("dzscMessageAction");
		dzscParameterSet = dzscMessageAction.findDzscMessageDirSet(new Request(
				CommonVars.getCurrUser()));
	}

	public void setVisible(boolean b) {
		if (b) {
			initTable(new ArrayList());
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("出口成品情况统计表");
		this.setSize(823, 506);
		this.setContentPane(getJContentPane());
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
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
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
			jTabbedPane.addTab("出口数据", null, getJPanel(), null);
			jTabbedPane.addTab("统计数据", null, getJPanel1(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (((JTabbedPane) e.getSource())
									.getSelectedIndex() == 1) {
								if (tableModel == null) {
									tfContractExpTotalMoney.setText("");
									tfExpTotalMoney.setText("");
									tfExpScale.setText("");
									return;
								}
								DzscExpProductStatResult statResult = contractStatAction
										.expProductStat(new Request(CommonVars
												.getCurrUser()), tableModel
												.getList());
								DecimalFormat myformat1 = new DecimalFormat(
										"#.###");
								tfContractExpTotalMoney
										.setText(statResult.getContractMoney() == null
												|| statResult
														.getContractMoney() == 0 ? ""
												: myformat1
														.format(
																statResult
																		.getContractMoney())
														.toString());
								tfExpTotalMoney
										.setText(statResult.getExpTotalMoney() == null
												|| statResult
														.getExpTotalMoney() == 0 ? ""
												: myformat1
														.format(
																statResult
																		.getExpTotalMoney())
														.toString());
								tfExpScale
										.setText(statResult.getScale() == null
												|| statResult.getScale() == 0 ? ""
												: myformat1.format(
														statResult.getScale())
														.toString());
							}
						}
					});
		}
		return jTabbedPane;
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
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jPanel.add(getJPanel2(), BorderLayout.NORTH);
			jPanel.add(getJPanel3(), BorderLayout.EAST);
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
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jLabel.setBounds(161, 44, 100, 25);
			jLabel.setText("合同出口金额");
			jLabel1.setBounds(161, 92, 100, 25);
			jLabel1.setText("出口成品总值");
			jLabel2.setBounds(161, 144, 100, 25);
			jLabel2.setText("出口成品总值比率");
			jPanel1.add(jLabel, null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(getTfContractExpTotalMoney(), null);
			jPanel1.add(getTfExpTotalMoney(), null);
			jPanel1.add(getTfExpScale(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("打印");
			jButton.setBounds(new Rectangle(683, 3, 62, 27));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getList().size() < 1) {
						JOptionPane.showMessageDialog(
								DgDzscExpProductStat.this, "没有数据可以打印", "提示",
								JOptionPane.OK_OPTION);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = DgDzscBillListExpProductStat.class
							.getResourceAsStream("report/DzscExpProductStat.jasper");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("contractNo", contract.getImContractNo());
					parameters.put("emsNo", contract.getEmsNo());
					parameters.put("beginDate", "");
					parameters.put("endDate", "");
					// parameters.put("reportTitle",
					// isMaterial?"进口料件清单明细表":"出口成品清单明细表");
					JasperPrint jasperPrint;
					try {
						jasperPrint = JasperFillManager.fillReport(
								reportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("关闭");
			jButton1.setBounds(new Rectangle(749, 3, 62, 27));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContractExpTotalMoney() {
		if (tfContractExpTotalMoney == null) {
			tfContractExpTotalMoney = new JTextField();
			tfContractExpTotalMoney.setBounds(265, 44, 164, 25);
		}
		return tfContractExpTotalMoney;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpTotalMoney() {
		if (tfExpTotalMoney == null) {
			tfExpTotalMoney = new JTextField();
			tfExpTotalMoney.setBounds(265, 92, 164, 25);
		}
		return tfExpTotalMoney;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpScale() {
		if (tfExpScale == null) {
			tfExpScale = new JTextField();
			tfExpScale.setBounds(265, 144, 164, 25);
		}
		return tfExpScale;
	}

	private String formatDouble(Double value) {
		DecimalFormat df = new DecimalFormat("#,##0.##");
		try {
			if (value != null) {
				return df.format(value.doubleValue());
			} else {
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	private void castValue(JTable jTable, int colNum) {
		jTable.getColumnModel().getColumn(colNum).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "0"
								: formatDouble(Double
										.parseDouble((String) value)));
						return this;
					}
				});
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {

		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("备案序号", "seqNum", 50));
						list.add(addColumn("手册号", "emsNo", 100));
						list.add(addColumn("合同号", "ieContractNo", 100));
						list.add(addColumn("商品编码", "complex", 100));
						list.add(addColumn("品名", "commName", 100));
						list.add(addColumn("规格", "commSpec", 100));
						list.add(addColumn("单位", "unit.name", 50));
						list.add(addColumn("单价", "unitPrice", 100));
						list.add(addColumn("合同定量", "contractAmount", 100));
						list.add(addColumn("总出口量", "expTotalAmont", 100));
						list.add(addColumn("成品出口量", "directExport", 100));
						list.add(addColumn("比例", "scale", 100));
						list.add(addColumn("转厂出口量", "transferFactoryExport",
								100));
						list.add(addColumn("可出口量", "canExportAmount", 100));
						list
								.add(addColumn("关封余量", "customsEnvelopRemain",
										100));
						list.add(addColumn("超量", "overAmount", 100));
						list.add(addColumn("退厂返工数", "backFactoryRework", 100));
						list.add(addColumn("返工复出数", "reworkExport", 100));
						list.add(addColumn("加工费单价", "processUnitPrice", 100));
						list.add(addColumn("加工费总价", "processTotalPrice", 100));
						list.add(addColumn("法定单位数量", "legalAmount", 100));
						list.add(addColumn("法定单位", "legalUnit.name", 100));
						list.add(addColumn("单重", "unitWeight", 100));
						list.add(addColumn("单位毛重", "unitGrossWeight", 100));
						list.add(addColumn("单位净重", "unitNetWeight", 100));
						list.add(addColumn("征减免税方式", "levyMode.name", 100));
						list.add(addColumn("手册通关备案补充说明", "note", 120));
						return list;
					}
				});

//		显示小数位,默认2位
		Integer decimalLength=2;
		if(dzscParameterSet!=null&&dzscParameterSet.getReportDecimalLength()!=null)
			decimalLength=dzscParameterSet.getReportDecimalLength();
		tableModel.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
//		List<JTableListColumn> cm = tableModel.getColumns();
//		cm.get(6).setFractionCount(decimalLength);
//		cm.get(7).setFractionCount(decimalLength);
//		cm.get(8).setFractionCount(decimalLength);
//		cm.get(9).setFractionCount(decimalLength);
//		cm.get(10).setFractionCount(decimalLength);
//		cm.get(11).setFractionCount(decimalLength);
//		cm.get(12).setFractionCount(decimalLength);
//		cm.get(13).setFractionCount(decimalLength);
//		cm.get(14).setFractionCount(decimalLength);
//		cm.get(15).setFractionCount(decimalLength);
//		cm.get(16).setFractionCount(decimalLength);
//		cm.get(17).setFractionCount(decimalLength);
//		cm.get(18).setFractionCount(decimalLength);
//		cm.get(19).setFractionCount(decimalLength);
//		cm.get(21).setFractionCount(decimalLength);
//		cm.get(22).setFractionCount(decimalLength);
//		cm.get(23).setFractionCount(decimalLength);
		

		// jTable.getColumnModel().getColumn(3).setCellRenderer(
		// new DefaultTableCellRenderer() {
		// public Component getTableCellRendererComponent(
		// JTable table, Object value, boolean isSelected,
		// boolean hasFocus, int row, int column) {
		// super.getTableCellRendererComponent(table, value,
		// isSelected, hasFocus, row, column);
		// super.setText((value == null) ? "" : castValue1(value));
		// return this;
		// }
		//
		// private String castValue1(Object value) {
		// String returnValue = "";
		//
		// if (value.equals(DeclareState.APPLY_POR)) {
		// returnValue = "正在申请";
		// } else if (value.equals(DeclareState.PROCESS_EXE)) {
		// returnValue = "正在执行";
		// } else if (value.equals(DeclareState.CHANGING_EXE)) {
		// returnValue = "正在变更";
		// }
		// return returnValue;
		// }
		// });
		//
		// jTable.getColumnModel().getColumn(4).setCellRenderer(
		// new DefaultTableCellRenderer() {
		// public Component getTableCellRendererComponent(
		// JTable table, Object value, boolean isSelected,
		// boolean hasFocus, int row, int column) {
		// super.getTableCellRendererComponent(table, value,
		// isSelected, hasFocus, row, column);
		// super.setText((value == null) ? "" : castValue1(value));
		// return this;
		// }
		//
		// private String castValue1(Object value) {
		// String returnValue = "";
		// if (value.equals(ContractKind.BONDEN_WAREHOUSE)) {
		// returnValue = "保税仓";
		// } else if (value
		// .equals(ContractKind.COME_MATERIEL_PROCESS)) {
		// returnValue = "来料加工";
		// } else if (value
		// .equals(ContractKind.IMPORT_MATERIEL_PROCESS)) {
		// returnValue = "进料加工";
		// } else if (value
		// .equals(ContractKind.FOREIGN_CAPITAL_MACHINE)) {
		// returnValue = "外资设备";
		// } else if (value
		// .equals(ContractKind.FOREIGN_CAPITAL_SELL_PRODUCT)) {
		// returnValue = "外资内销产品";
		// }
		// return returnValue;
		// }
		// });
		// jTable
		// .setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("查询");
			jButton2.setPreferredSize(new Dimension(58, 22));
			jButton2.setBounds(new Rectangle(619, 3, 62, 27));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<DzscExpProductStat> list = new ArrayList();
					int state = -1;
					if (rbAll.isSelected()) {
						state = CustomsDeclarationState.ALL;
					} else if (rbNo.isSelected()) {
						state = CustomsDeclarationState.NOT_EFFECTIVED;
					} else if (rbYes.isSelected()) {
						state = CustomsDeclarationState.EFFECTIVED;
					}
					List contracts = jList11.getSelectedContracts();
					list = contractStatAction
								.findExpProductStatByDzscEmsPorHeads(new Request(CommonVars
										.getCurrUser()), contracts,CommonVars
										.dateToStandDate(cbbBeginDate.getDate()),
										CommonVars
												.dateToStandDate(cbbEndDate.getDate())
												,state,cbDetachCompute.isSelected());
					
					initTable(list);
				}
			});
		}
		return jButton2;
	}

	/**
	 * @return the dzscParameterSet
	 */
	public DzscParameterSet getDzscParameterSet() {
		return dzscParameterSet;
	}

	/**
	 * @param dzscParameterSet
	 *            the dzscParameterSet to set
	 */
	public void setDzscParameterSet(DzscParameterSet dzscParameterSet) {
		this.dzscParameterSet = dzscParameterSet;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(213, 4, 19, 25));
			jLabel6.setText("到");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(9, 4, 96, 25));
			jLabel5.setText("报关申报日期");
			jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setPreferredSize(new Dimension(1, 31));
			jPanel2.add(jLabel5, null);
			jPanel2.add(getCbbBeginDate(), null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(getCbbEndDate(), null);
			jPanel2.add(getCbDetachCompute(), null);
			jPanel2.add(getRbYes(), null);
			jPanel2.add(getRbNo(), null);
			jPanel2.add(getRbAll(), null);
			jPanel2.add(getJButton2(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
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
			jPanel3.setLayout(null);
			jPanel3.setPreferredSize(new Dimension(192, 63));
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel5(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes cbbBeginDate	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(108, 4, 101, 25));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes cbbEndDate	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(235, 4, 100, 25));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes cbDetachCompute	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbDetachCompute() {
		if (cbDetachCompute == null) {
			cbDetachCompute = new JCheckBox();
			cbDetachCompute.setBounds(new Rectangle(341, 4, 80, 25));
			cbDetachCompute.setText("分开统计");
		}
		return cbDetachCompute;
	}

	/**
	 * This method initializes rbYes	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbYes() {
		if (rbYes == null) {
			rbYes = new JRadioButton();
			rbYes.setBounds(new Rectangle(424, 4, 61, 25));
			rbYes.setText("已生效");
			rbYes.setSelected(true);
		}
		return rbYes;
	}

	/**
	 * This method initializes rbNo	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbNo() {
		if (rbNo == null) {
			rbNo = new JRadioButton();
			rbNo.setBounds(new Rectangle(492, 4, 68, 25));
			rbNo.setText("未生效");
		}
		return rbNo;
	}

	/**
	 * This method initializes rbAll	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbAll() {
		if (rbAll == null) {
			rbAll = new JRadioButton();
			rbAll.setBounds(new Rectangle(559, 4, 56, 25));
			rbAll.setText("全部");
		}
		return rbAll;
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
			jPanel4.setBounds(new Rectangle(0, 0, 194, 89));
			jPanel4.add(getJRadioButton10(), null);
			jPanel4.add(getJRadioButton9(), null);
			jPanel4.add(getCbContractExe(), null);
			jPanel4.add(getCbContractCancel(), null);
		}
		return jPanel4;
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
			jPanel5.setBounds(new Rectangle(2, 90, 187, 330));
			jPanel5.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jRadioButton10	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton10() {
		if (jRadioButton10 == null) {
			jRadioButton10 = new JRadioButton();
			jRadioButton10.setBounds(new Rectangle(34, 3, 61, 21));
			jRadioButton10.setText("全否");
			jRadioButton10.setSelected(true);
			jRadioButton10.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jRadioButton10.isSelected()) {
						for (int i = 0; i < jList11.getModel().getSize(); i++) {
							DzscEmsPorHead contract = (DzscEmsPorHead) jList11.getModel()
									.getElementAt(i);
							contract.setSelected(false);
						}
						jList11.repaint();
						cbbBeginDate.setDate(findBeginDate());
						cbbEndDate.setDate(findEndDate());
					}
				}
			});
			
			
		}
		return jRadioButton10;
	}

	/**
	 * This method initializes jRadioButton9	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton9() {
		if (jRadioButton9 == null) {
			jRadioButton9 = new JRadioButton();
			jRadioButton9.setBounds(new Rectangle(93, 3, 49, 23));
			jRadioButton9.setText("全选");
			jRadioButton9.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jRadioButton9.isSelected()) {
						for (int i = 0; i < jList11.getModel().getSize(); i++) {
							DzscEmsPorHead contract = (DzscEmsPorHead) jList11.getModel()
									.getElementAt(i);
							contract.setSelected(true);
						}
						jList11.repaint();
						cbbBeginDate.setDate(findBeginDate());
						cbbEndDate.setDate(findEndDate());
					}
				}
			});
		}
		return jRadioButton9;
	}

	/**
	 * This method initializes cbContractExe	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbContractExe() {
		if (cbContractExe == null) {
			cbContractExe = new JCheckBox();
			cbContractExe.setBounds(new Rectangle(4, 25, 185, 25));
			cbContractExe.setText("正在执行的合同");
			cbContractExe.setSelected(true);
			cbContractExe.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(cbContractExe.isSelected() && cbContractCancel.isSelected()){
						jList11.showContractData(true, true);
					}else if(cbContractExe.isSelected() && !cbContractCancel.isSelected()){
						jList11.showContractData(true, false);
					}else if(!cbContractExe.isSelected() && cbContractCancel.isSelected()){
						jList11.showContractData(false, true);
					}else{
						jList11.showContractData(false, false);
					}
				}
			});
		}
		return cbContractExe;
	}

	/**
	 * This method initializes cbContractCancel	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbContractCancel() {
		if (cbContractCancel == null) {
			cbContractCancel = new JCheckBox();
			cbContractCancel.setBounds(new Rectangle(4, 53, 176, 24));
			cbContractCancel.setText("核销的合同");
			cbContractCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(cbContractExe.isSelected() && cbContractCancel.isSelected()){
						jList11.showContractData(true, true);
					}else if(cbContractExe.isSelected() && !cbContractCancel.isSelected()){
						jList11.showContractData(true, false);
					}else if(!cbContractExe.isSelected() && cbContractCancel.isSelected()){
						jList11.showContractData(false, true);
					}else{
						jList11.showContractData(false, false);
					}
				}
			});
		}
		return cbContractCancel;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJList11());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jList11	
	 * 	
	 * @return com.bestway.bcs.client.contractanalyse.JContractList	
	 */
	private JDzscEmsPorHeadList getJList11() {
		if (jList11 == null) {
			jList11 = new JDzscEmsPorHeadList();
			jList11.addMouseListener(new java.awt.event.MouseAdapter() 
			{
				public void mouseClicked(java.awt.event.MouseEvent e)
				{
					cbbBeginDate.setDate(findBeginDate());
					cbbEndDate.setDate(findEndDate());
				}
			});
		}
		return jList11;
	}
	
	/**
	 * 找出合同中最靠前的日期
	 * 
	 * @return
	 */
	public Date findBeginDate()
	{
		List<Date> list = new ArrayList<Date>();
		Date date = null;
		for(int i = 0;i < jList11.getModel().getSize();i++)
		{
			DzscEmsPorHead contract = (DzscEmsPorHead) jList11.getModel().getElementAt(i);
			if(!contract.isSelected())
			{
				continue;
			}
			if(contract.getBeginDate() == null)
			{
				continue;
			}
			if(date==null)
			{
				date = contract.getBeginDate();
			}
			else
			{
				if(contract.getBeginDate().before(date))
				{
					date = contract.getBeginDate();
				}
			}
		}
		return date;
	}
	
	/**
	 * 找出合同中最靠后的日期
	 * 
	 * @return
	 */
	public Date findEndDate()
	{
		List<Date> list = new ArrayList<Date>();
		Date date = null;
		for(int i = 0;i < jList11.getModel().getSize();i++)
		{
			DzscEmsPorHead contract = (DzscEmsPorHead) jList11.getModel().getElementAt(i);
			if( !contract.isSelected())
			{
				continue;
			}
			if(contract.getEndDate() == null)
			{
				continue;
			}
			if(date==null)
			{
				date = contract.getEndDate();
			}
			else
			{
				if(contract.getEndDate().after(date))
				{
					date = contract.getEndDate();
				}
			}
		}
		return date;
	}
	
	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getRbAll());
			buttonGroup.add(this.getRbYes());
			buttonGroup.add(this.getRbNo());
		}
		return buttonGroup;
	}
	
	/**
	 * This method initializes buttonGroup1
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(this.getJRadioButton10());
			buttonGroup1.add(this.getJRadioButton9());
		}
		return buttonGroup1;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
