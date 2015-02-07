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
import com.bestway.dzsc.contractstat.action.DzscStatAction;
import com.bestway.dzsc.contractstat.entity.DzscExpProductStat;
import com.bestway.dzsc.contractstat.entity.DzscImpMaterialStat;
import com.bestway.dzsc.contractstat.entity.DzscImpMaterialStatResult;
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
import com.bestway.dzsc.client.dzscmanage.JDzscEmsPorHeadList;
import java.awt.Point;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgDzscImpMaterialStat extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton jButton = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JTextField tfContractImpTotalMoney = null;

	private JTextField tfImpTotalMoney = null;

	private JTextField tfImpScale = null;

	private JTextField tfRemainMoney = null;

	private JTextField tfRemainMoneyScale = null;

	private DzscEmsPorHead contract = null;  //  @jve:decl-index=0:

	private JTableListModel tableModel = null;

	private DzscStatAction contractStatAction = null;

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

	private JButton jButton21 = null;

	private JButton jButton11 = null;

	private JPanel jPanel4 = null;

	private JRadioButton jRadioButton10 = null;

	private JRadioButton jRadioButton9 = null;

	private JCheckBox cbContractExe = null;

	private JCheckBox cbContractCancel = null;

	private JPanel jPanel5 = null;

	private JScrollPane jScrollPane1 = null;

	private JDzscEmsPorHeadList jList11 = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:

	private ButtonGroup buttonGroup1 = null; // @jve:decl-index=0:

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
	public DgDzscImpMaterialStat() {
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
		this.setTitle("进口料件情况统计表");
		this.setSize(834, 510);
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
			jTabbedPane.addTab("进口料进数据", null, getJPanel(), null);
			jTabbedPane.addTab("统计数据", null, getJPanel1(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (((JTabbedPane) e.getSource())
									.getSelectedIndex() == 1) {
								if (tableModel == null) {
									tfContractImpTotalMoney.setText("");
									tfImpTotalMoney.setText("");
									tfImpScale.setText("");
									tfRemainMoney.setText("");
									tfRemainMoneyScale.setText("");
									return;
								}
								DzscImpMaterialStatResult statResult = contractStatAction
										.impMaterialStat(new Request(CommonVars
												.getCurrUser()), tableModel
												.getList());
								DecimalFormat myformat1 = new DecimalFormat(
										"#.###");
								tfContractImpTotalMoney
										.setText(statResult.getContractMoney() == null
												|| statResult
														.getContractMoney() == 0 ? ""
												: myformat1
														.format(
																statResult
																		.getContractMoney())
														.toString());
								System.out.println();
								tfImpTotalMoney
										.setText(statResult.getImpTotalMoney() == null
												|| statResult
														.getImpTotalMoney() == 0 ? ""
												: myformat1
														.format(
																statResult
																		.getImpTotalMoney())
														.toString());
								tfImpScale
										.setText(statResult.getImpTotalScale() == null
												|| statResult
														.getImpTotalScale() == 0 ? ""
												: myformat1
														.format(
																statResult
																		.getImpTotalScale())
														.toString());
								tfRemainMoney
										.setText(statResult.getRemainMoney() == null
												|| statResult.getRemainMoney() == 0 ? ""
												: myformat1
														.format(
																statResult
																		.getRemainMoney())
														.toString());
								tfRemainMoneyScale
										.setText(statResult.getRemainScale() == null
												|| statResult.getRemainScale() == 0 ? ""
												: myformat1
														.format(
																statResult
																		.getRemainScale())
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
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jLabel.setBounds(161, 44, 100, 25);
			jLabel.setText("合同进口金额");
			jLabel1.setBounds(161, 92, 100, 25);
			jLabel1.setText("进口料件总值");
			jLabel2.setBounds(161, 144, 100, 25);
			jLabel2.setText("进口料件总值比率");
			jLabel3.setBounds(161, 193, 100, 25);
			jLabel3.setText("余料总值");
			jLabel4.setBounds(161, 242, 100, 25);
			jLabel4.setText("余料总值比率");
			jPanel1.add(jLabel, null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(getTfContractImpTotalMoney(), null);
			jPanel1.add(getTfImpTotalMoney(), null);
			jPanel1.add(getTfImpScale(), null);
			jPanel1.add(getTfRemainMoney(), null);
			jPanel1.add(getTfRemainMoneyScale(), null);
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
			jButton.setBounds(new Rectangle(676, 1, 71, 27));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getList().size() < 1) {
						JOptionPane.showMessageDialog(
								DgDzscImpMaterialStat.this, "没有数据可以打印", "提示",
								JOptionPane.OK_OPTION);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = DgDzscBillListImpMaterialStat.class
							.getResourceAsStream("report/DzscImpMaterialStat.jasper");
					Map<String, Object> parameters = new HashMap<String, Object>();
					
					List contracts = jList11.getSelectedContracts();
					
					String emsNos = "";// 手册号
					String imContractNos = "";//合同号
					DzscEmsPorHead contract = null;
					boolean firstEmsNo = true;
					for (int i = 0; i < contracts.size(); i++) {
						contract = (DzscEmsPorHead) contracts.get(i);
						if(contract != null) {
							if(firstEmsNo) {
								emsNos = contract.getEmsNo();
								imContractNos = contract.getImContractNo();
								firstEmsNo = false;
							} else {
								emsNos = emsNos + ";" + contract.getEmsNo();
								imContractNos = imContractNos + ";" + contract.getImContractNo();
							}
						}
					}
					
					parameters.put("contractNo", imContractNos);
					parameters.put("emsNo", emsNos);
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
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContractImpTotalMoney() {
		if (tfContractImpTotalMoney == null) {
			tfContractImpTotalMoney = new JTextField();
			tfContractImpTotalMoney.setBounds(265, 44, 164, 25);
		}
		return tfContractImpTotalMoney;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImpTotalMoney() {
		if (tfImpTotalMoney == null) {
			tfImpTotalMoney = new JTextField();
			tfImpTotalMoney.setBounds(265, 92, 164, 25);
		}
		return tfImpTotalMoney;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImpScale() {
		if (tfImpScale == null) {
			tfImpScale = new JTextField();
			tfImpScale.setBounds(265, 144, 164, 25);
		}
		return tfImpScale;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemainMoney() {
		if (tfRemainMoney == null) {
			tfRemainMoney = new JTextField();
			tfRemainMoney.setBounds(265, 193, 164, 25);
		}
		return tfRemainMoney;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemainMoneyScale() {
		if (tfRemainMoneyScale == null) {
			tfRemainMoneyScale = new JTextField();
			tfRemainMoneyScale.setBounds(265, 242, 164, 25);
		}
		return tfRemainMoneyScale;
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
						list.add(addColumn("总进口量", "impTotalAmont", 100));
						list.add(addColumn("报关单进口量", "impCDAmount", 100));
						list.add(addColumn("料件进口量", "directImport", 100));
						list.add(addColumn("转厂进口量", "transferFactoryImport",
								100));
						list.add(addColumn("退料出口量", "backMaterialExport", 100));
						list.add(addColumn("退料复出量", "backMaterialReturn", 100));
						list
								.add(addColumn("退料退换量", "backMaterialExchange",
										100));
						list.add(addColumn("余料转进量", "remainImport", 100));
						list.add(addColumn("余料转出量", "remainForward", 100));
						list
								.add(addColumn("关封余量", "customsEnvelopRemain",
										100));
						list.add(addColumn("成品使用量", "productUse", 100));
						list.add(addColumn("余料情况", "remainAmount", 100));
						// list.add(addColumn("缺料情况", "ullage", 100));
						list.add(addColumn("可进口量", "canImportAmount", 100));
						list.add(addColumn("比例", "scale", 100));
						list.add(addColumn("主料/辅料", "materialType", 100));
						list.add(addColumn("原产国", "country.name", 100));
						
						list.add(addColumn("第一法定单位", "legalUnit.name", 100));
//						list.add(addColumn("第一法定数量", "legalAmount", 100));
						list.add(addColumn("第二法定单位", "secondLegalUnit.name", 100));
//						list.add(addColumn("第二法定数量", "secondAmount", 100));
						
						list.add(addColumn("余料金额", "remainMoney", 100));
						return list;
					}
				});
		// 显示小数位,默认2位
		Integer decimalLength = 2;
		if (dzscParameterSet != null
				&& dzscParameterSet.getReportDecimalLength() != null)
			decimalLength = dzscParameterSet.getReportDecimalLength();
		tableModel.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
		// List<JTableListColumn> cm = tableModel.getColumns();
		// cm.get(6).setFractionCount(decimalLength);
		// cm.get(7).setFractionCount(decimalLength);
		// cm.get(8).setFractionCount(decimalLength);
		// cm.get(9).setFractionCount(decimalLength);
		// cm.get(10).setFractionCount(decimalLength);
		// cm.get(11).setFractionCount(decimalLength);
		// cm.get(12).setFractionCount(decimalLength);
		// cm.get(13).setFractionCount(decimalLength);
		// cm.get(14).setFractionCount(decimalLength);
		// cm.get(15).setFractionCount(decimalLength);
		// cm.get(16).setFractionCount(decimalLength);
		// cm.get(17).setFractionCount(decimalLength);
		// cm.get(18).setFractionCount(decimalLength);
		// cm.get(19).setFractionCount(decimalLength);
		// cm.get(20).setFractionCount(decimalLength);
		// cm.get(21).setFractionCount(decimalLength);
		// cm.get(24).setFractionCount(decimalLength);

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
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(189, 1, 20, 27));
			jLabel6.setText("到");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(8, 1, 82, 27));
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
			jPanel2.add(getJButton21(), null);
			jPanel2.add(getJButton11(), null);
			jPanel2.add(getJButton(), null);
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
			jPanel3.setPreferredSize(new Dimension(192, 0));
			jPanel3.add(getJPanel4(), BorderLayout.NORTH);
			jPanel3.add(getJPanel5(), BorderLayout.CENTER);
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
			cbbBeginDate.setBounds(new Rectangle(91, 1, 96, 27));
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
			cbbEndDate.setBounds(new Rectangle(213, 1, 99, 27));
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
			cbDetachCompute.setBounds(new Rectangle(315, 1, 73, 27));
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
			rbYes.setBounds(new Rectangle(400, 1, 61, 27));
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
			rbNo.setBounds(new Rectangle(469, 1, 63, 27));
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
			rbAll.setBounds(new Rectangle(538, 1, 63, 27));
			rbAll.setText("全部");
		}
		return rbAll;
	}

	/**
	 * This method initializes jButton21
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton21() {
		if (jButton21 == null) {
			jButton21 = new JButton();
			jButton21.setBounds(new Rectangle(602, 1, 71, 27));
			jButton21.setText("查询");
			jButton21.setPreferredSize(new Dimension(58, 22));
			jButton21.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					List<DzscImpMaterialStat> list = new ArrayList();
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
							.findImpMaterialStatByDzscEmsPorHeads(new Request(
									CommonVars.getCurrUser()), contracts,
									CommonVars.dateToStandDate(cbbBeginDate
											.getDate()), CommonVars
											.dateToStandDate(cbbEndDate
													.getDate()), state,
									cbDetachCompute.isSelected());

					initTable(list);
				}
			});
		}
		return jButton21;
	}

	/**
	 * This method initializes jButton11
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton11() {
		if (jButton11 == null) {
			jButton11 = new JButton();
			jButton11.setBounds(new Rectangle(750, 1, 71, 27));
			jButton11.setText("关闭");
			jButton11.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton11;
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
			jPanel4.setPreferredSize(new Dimension(1, 89));
			jPanel4.add(getJRadioButton10(), null);
			jPanel4.add(getJRadioButton9(), null);
			jPanel4.add(getCbContractExe(), null);
			jPanel4.add(getCbContractCancel(), null);
		}
		return jPanel4;
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
			jRadioButton10
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (jRadioButton10.isSelected()) {
								for (int i = 0; i < jList11.getModel()
										.getSize(); i++) {
									DzscEmsPorHead contract = (DzscEmsPorHead) jList11
											.getModel().getElementAt(i);
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
			jRadioButton9
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (jRadioButton9.isSelected()) {
								for (int i = 0; i < jList11.getModel()
										.getSize(); i++) {
									DzscEmsPorHead contract = (DzscEmsPorHead) jList11
											.getModel().getElementAt(i);
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
			cbContractExe
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbContractExe.isSelected()
									&& cbContractCancel.isSelected()) {
								jList11.showContractData(true, true);
							} else if (cbContractExe.isSelected()
									&& !cbContractCancel.isSelected()) {
								jList11.showContractData(true, false);
							} else if (!cbContractExe.isSelected()
									&& cbContractCancel.isSelected()) {
								jList11.showContractData(false, true);
							} else {
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
			cbContractCancel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbContractExe.isSelected()
									&& cbContractCancel.isSelected()) {
								jList11.showContractData(true, true);
							} else if (cbContractExe.isSelected()
									&& !cbContractCancel.isSelected()) {
								jList11.showContractData(true, false);
							} else if (!cbContractExe.isSelected()
									&& cbContractCancel.isSelected()) {
								jList11.showContractData(false, true);
							} else {
								jList11.showContractData(false, false);
							}
						}
					});
		}
		return cbContractCancel;
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
			jPanel5.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel5;
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
	 * @return com.bestway.dzsc.client.dzscmanage.JDzscEmsPorHeadList
	 */
	private JDzscEmsPorHeadList getJList11() {
		if (jList11 == null) {
			jList11 = new JDzscEmsPorHeadList();
			jList11.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
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
	public Date findBeginDate() {
		List<Date> list = new ArrayList<Date>();
		Date date = null;
		for (int i = 0; i < jList11.getModel().getSize(); i++) {
			DzscEmsPorHead contract = (DzscEmsPorHead) jList11.getModel()
					.getElementAt(i);
			if (!contract.isSelected()) {
				continue;
			}
			if (contract.getBeginDate() == null) {
				continue;
			}
			if (date == null) {
				date = contract.getBeginDate();
			} else {
				if (contract.getBeginDate().before(date)) {
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
	public Date findEndDate() {
		List<Date> list = new ArrayList<Date>();
		Date date = null;
		for (int i = 0; i < jList11.getModel().getSize(); i++) {
			DzscEmsPorHead contract = (DzscEmsPorHead) jList11.getModel()
					.getElementAt(i);
			if (!contract.isSelected()) {
				continue;
			}
			if (contract.getEndDate() == null) {
				continue;
			}
			if (date == null) {
				date = contract.getEndDate();
			} else {
				if (contract.getEndDate().after(date)) {
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
