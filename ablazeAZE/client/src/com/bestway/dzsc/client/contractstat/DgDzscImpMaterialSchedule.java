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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.dzsc.contractstat.action.DzscStatAction;
import com.bestway.dzsc.contractstat.entity.DzscImpMaterialStat;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.message.action.DzscMessageAction;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscImpMaterialSchedule extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private DzscEmsPorHead contract = null;

	private JTableListModel tableModel = null;

	private DzscStatAction contractStatAction = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton jButton2 = null;

	private JToolBar jToolBar = null;

	private JPanel jPanel = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private DzscParameterSet dzscParameterSet = null;

	private DzscMessageAction dzscMessageAction = null;

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
	public DgDzscImpMaterialSchedule() {
		super();
		initialize();
		contractStatAction = (DzscStatAction) CommonVars
				.getApplicationContext().getBean("dzscStatAction");
		dzscMessageAction = (DzscMessageAction) CommonVars
				.getApplicationContext().getBean("dzscMessageAction");
		dzscParameterSet = dzscMessageAction.findDzscMessageDirSet(new Request(
				CommonVars.getCurrUser()));
	}

	public void setVisible(boolean b) {
		if (b) {
			if (contract != null) {
				this.cbbBeginDate.setDate(contract.getBeginDate());
			} else {
				this.cbbBeginDate.setDate(null);
			}
			List list = new ArrayList();
			if (this.contract != null) {
				list = this.contractStatAction.findImpMaterialSchedule(
						new Request(CommonVars.getCurrUser()), this.contract,
						cbbBeginDate.getDate(), cbbEndDate.getDate());
			}
			initTable(list);
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
		this.setTitle("料件执行进度[总表]");
		this.setSize(750, 510);
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
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
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
	private void initTable(List<DzscImpMaterialStat> list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("备案序号", "seqNum", 50));//1
						list.add(addColumn("商品编码", "complex", 100));//2
						list.add(addColumn("品名", "commName", 100));//3
						list.add(addColumn("规格", "commSpec", 100));//4
						list.add(addColumn("单位", "unit.name", 50));//5
						list.add(addColumn("单价", "unitPrice", 100));//6
						list.add(addColumn("合同定量", "contractAmount", 100));//7
						list.add(addColumn("总进口量", "impTotalAmont", 100));//8
						list.add(addColumn("大单进口量", "impCDAmount", 100));//9
						list.add(addColumn("料件进口量", "directImport", 100));//10
						list.add(addColumn("转厂进口量", "transferFactoryImport",
								100));//11
						list.add(addColumn("退料出口量", "backMaterialExport", 100));//12
						list.add(addColumn("退料复出量", "backMaterialReturn", 100));//13
						list
								.add(addColumn("退料退换量", "backMaterialExchange",
										100));//14
						list.add(addColumn("成品使用量", "productUse", 100));//15
						list.add(addColumn("余料情况", "remainAmount", 100));//16
						list.add(addColumn("缺料情况", "ullage", 100));//17
						list.add(addColumn("可进口量", "canImportAmount", 100));//18
						list.add(addColumn("比例", "scale", 100));//19
						list.add(addColumn("主料/辅料", "materialType", 100));//20
						list.add(addColumn("原产国", "country.name", 100));//21
						list.add(addColumn("凭证序号", "credenceSerialNo", 100));//22
						list.add(addColumn("余料金额", "remainMoney", 100));//23
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
//		cm.get(23).setFractionCount(decimalLength);
		CommonVars.castMultiplicationValue(jTable,23,16,6,decimalLength);

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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("打印");
			jButton.setBounds(new java.awt.Rectangle(362, 3, 58, 24));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getList().size() < 1) {
						JOptionPane.showMessageDialog(
								DgDzscImpMaterialSchedule.this, "没有数据可以打印",
								"提示", JOptionPane.OK_OPTION);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = DgDzscBillListImpMaterialStat.class
							.getResourceAsStream("report/DzscImpMaterialSchedule.jasper");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("contractNo", contract.getImContractNo());
					parameters.put("emsNo", contract.getEmsNo());
					parameters.put("beginDate",
							cbbBeginDate.getDate() == null ? ""
									: (new SimpleDateFormat("yyyy-MM-dd"))
											.format(cbbBeginDate.getDate()));
					parameters.put("endDate", cbbEndDate.getDate() == null ? ""
							: (new SimpleDateFormat("yyyy-MM-dd"))
									.format(cbbEndDate.getDate()));
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
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new java.awt.Rectangle(92, 3, 90, 24));
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
			cbbEndDate.setBounds(new java.awt.Rectangle(200, 3, 85, 24));
		}
		return cbbEndDate;
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
			jButton2.setBounds(new java.awt.Rectangle(287, 3, 58, 24));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new FindThread().start();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJPanel());
			jToolBar.add(getJButton1());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(183, 5, 16, 20));
			jLabel3.setText("\u5230");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(3, 5, 89, 20));
			jLabel2.setText("\u62a5\u5173\u65e5\u671f\u8303\u56f4\u4ece");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel2, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJButton(), null);
		}
		return jPanel;
	}

	class FindThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正在查询,请稍候...");
			List list = new ArrayList();
			if (contract != null) {
				list = contractStatAction.findImpMaterialSchedule(new Request(
						CommonVars.getCurrUser()), contract, cbbBeginDate
						.getDate(), cbbEndDate.getDate());
			}
			CommonProgress.closeProgressDialog();
			initTable(list);
		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
