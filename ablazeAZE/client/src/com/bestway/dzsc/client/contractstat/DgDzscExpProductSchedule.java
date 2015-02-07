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
import com.bestway.dzsc.contractstat.entity.DzscExpProductStat;
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
public class DgDzscExpProductSchedule extends JDialogBase {

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
	public DgDzscExpProductSchedule() {
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
			List list = new ArrayList();
			if (contract != null) {
				this.cbbBeginDate.setDate(contract.getBeginDate());
			} else {
				this.cbbBeginDate.setDate(null);
			}
			if (this.contract != null) {
				list = this.contractStatAction.findExpProductSchedule(
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
		this.setTitle("成品执行进度[总表]");
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
	private void initTable(List<DzscExpProductStat> list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("备案序号", "seqNum", 50));
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
						list.add(addColumn("手册通关备案补充说明", "note",120));
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
//		cm.get(20).setFractionCount(decimalLength);
//		cm.get(21).setFractionCount(decimalLength);
//		cm.get(22).setFractionCount(decimalLength);
		

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
			jButton.setBounds(new java.awt.Rectangle(389, 3, 72, 24));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getList().size() < 1) {
						JOptionPane.showMessageDialog(
								DgDzscExpProductSchedule.this, "没有数据可以打印",
								"提示", JOptionPane.OK_OPTION);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = DgDzscBillListExpProductStat.class
							.getResourceAsStream("report/DzscExpProductSchedule.jasper");
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
			cbbBeginDate.setBounds(new java.awt.Rectangle(92, 3, 100, 24));
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
			cbbEndDate.setBounds(new java.awt.Rectangle(213, 3, 85, 24));
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
			jButton2.setBounds(new java.awt.Rectangle(301, 3, 72, 24));
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
			jLabel3.setBounds(new java.awt.Rectangle(193, 5, 19, 21));
			jLabel3.setText("\u5230");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(4, 4, 89, 21));
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
				list = contractStatAction.findExpProductSchedule(new Request(
						CommonVars.getCurrUser()), contract, cbbBeginDate
						.getDate(), cbbEndDate.getDate());
			}
			CommonProgress.closeProgressDialog();
			initTable(list);
		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
