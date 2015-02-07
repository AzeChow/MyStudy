/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractanalyse.action.ContractAnalyseAction;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
/**查询条件是针对所有合同的统计
  料件总值=合同备案中表头进口总金额
  已进口材料总值=(料件进口值+转厂进口值+料件退换进口值(包含在料件直接进口中)+余料进口-料件退换出口值-余料出口)*汇率
  完成进口比例=(已进口材料总值/料件总值)*100
  成品总值=合同备案中表头出口总金额
  已出口成品总值=(成品出口值+转厂出口值-退厂返工值+返工复出值)*汇率
  完成出口比例=(已出口成品总值/成品总值)*100
 * 
 * 
 */
public class DgContractAnalyseStat extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton btnPrint = null;

	private JButton btnExit = null;

	private Contract contract = null;

	private JTableListModel tableModel = null;

	private ContractAction contractAction = null;

	private ContractAnalyseAction contractAnalyseAction = null;

	private JPanel jPanel = null;

	private JRadioButton rbYes = null;

	private JRadioButton rbNo = null;

	private JRadioButton rbAll = null;

	private JButton btnQuery = null;

	private ButtonGroup buttonGroup = null;

	private BcsParameterSet parameterSet = null;

	/**
	 * @return Returns the contract.
	 */
	public Contract getContract() {
		return contract;
	}

	/**
	 * @param contract
	 *            The contract to set.
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
	}

	/**
	 * This is the default constructor
	 */
	public DgContractAnalyseStat() {
		super();
		initialize();
		contractAnalyseAction = (ContractAnalyseAction) CommonVars
				.getApplicationContext().getBean("contractAnalyseAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		parameterSet = contractAction.findBcsParameterSet(new Request(
				CommonVars.getCurrUser(), true));
	}

	public void setVisible(boolean b) {
		if (b) {
			initTable();
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
		this.setTitle("各合同执行状况表");
		this.setSize(750, 510);
		this.setContentPane(getJContentPane());
		this.getButtonGroup();
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
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJPanel());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
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
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setBounds(new java.awt.Rectangle(284, 3, 73, 25));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null || tableModel.getList().size() <= 0) {
						JOptionPane.showMessageDialog(
								DgContractAnalyseStat.this, "没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = DgPreCustomsDeclaration.class
							.getResourceAsStream("report/ContractAnalyseStat.jasper");
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					Map<String, Object> parameters = new HashMap<String, Object>();
					// parameters.put("isImport",new Boolean(true));
					// parameters.put("contractNo",contract.getExpContractNo());
					// parameters.put("emsNo",contract.getEmsNo());
					// parameters.put("beginDate",contract.getBeginDate()==null?"":dateFormat.format(contract.getBeginDate()));
					// parameters.put("effectiveDate",contract.getEndDate()==null?"":dateFormat.format(contract.getEndDate()));
					parameters.put("printDate", dateFormat.format(new Date()));
					parameters.put("companyName", CommonVars.getCurrUser()
							.getCompany().getName());
					JasperPrint jasperPrint = null;
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
		return btnPrint;
	}

	/**
	 * This method initializes jButton2
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

	/**
	 * 初始化数据Table
	 */
	private void initTable() {
		int state = -1;
		if (this.rbYes.isSelected()) {
			state = CustomsDeclarationState.EFFECTIVED;
		} else if (this.rbNo.isSelected()) {
			state = CustomsDeclarationState.NOT_EFFECTIVED;
		} else {
			state = CustomsDeclarationState.ALL;
		}
		List list = this.contractAnalyseAction.findContractAnalyseStat(
				new Request(CommonVars.getCurrUser()), state);
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("合同号", "contractNo", 100));
						list.add(addColumn("手册号", "emsNo", 100));
						list.add(addColumn("料件总值", "imgAmount", 100));
						list.add(addColumn("已进口材料总值", "impTotalMoney", 100));
						list.add(addColumn("完成进口比例", "impScale", 100));
						list.add(addColumn("成品总值", "exgAmount", 100));
						list.add(addColumn("已出口成品总值", "expTotalMoney", 100));
						list.add(addColumn("完成出口比例", "expScale", 100));
						list.add(addColumn("有效日期", "availabilityDate", 50));
						list.add(addColumn("变更次数", "changeTimes", 50));
						list.add(addColumn("延期次数", "delayTimes", 50));
						list.add(addColumn("延期时间", "delayTerm", 50));
						list.add(addColumn("核销顺序", "cavOrder", 50));
						list.add(addColumn("核销状态", "cavState", 200));
						list.add(addColumn("备注", "memo", 100));
						return list;
					}
				});

		// 截取小数位
		Integer decimalLength = 2;
		if (parameterSet != null
				&& parameterSet.getReportDecimalLength() != null)
			decimalLength = parameterSet.getReportDecimalLength();
		tableModel.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
//		List<JTableListColumn> cm = tableModel.getColumns();
//		cm.get(3).setFractionCount(decimalLength);
//		cm.get(4).setFractionCount(decimalLength);
//		cm.get(5).setFractionCount(decimalLength);
//		cm.get(6).setFractionCount(decimalLength);
//		cm.get(7).setFractionCount(decimalLength);
//		cm.get(8).setFractionCount(decimalLength);
		

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
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBorder(BorderFactory.createTitledBorder(null, "",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel.add(getRbYes(), null);
			jPanel.add(getRbNo(), null);
			jPanel.add(getRbAll(), null);
			jPanel.add(getBtnPrint(), null);
			jPanel.add(getBtnQuery(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbYes() {
		if (rbYes == null) {
			rbYes = new JRadioButton();
			rbYes.setBounds(new Rectangle(5, 5, 62, 20));
			rbYes.setText("\u5df2\u751f\u6548");
			rbYes.setSelected(true);
		}
		return rbYes;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbNo() {
		if (rbNo == null) {
			rbNo = new JRadioButton();
			rbNo.setBounds(new Rectangle(63, 6, 66, 20));
			rbNo.setText("\u672a\u751f\u6548");
		}
		return rbNo;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAll() {
		if (rbAll == null) {
			rbAll = new JRadioButton();
			rbAll.setBounds(new Rectangle(128, 6, 52, 20));
			rbAll.setText("\u5168\u90e8");

		}
		return rbAll;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(new java.awt.Rectangle(206, 3, 73, 25));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new MyFindThread().start();
				}
			});
		}
		return btnQuery;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.rbAll);
			buttonGroup.add(this.rbYes);
			buttonGroup.add(this.rbNo);
		}
		return buttonGroup;
	}

	class MyFindThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正获取数据，请稍后...");
			initTable();
			CommonProgress.closeProgressDialog();
		}
	}
} // @jve:decl-index=0:visual-constraint="66,-1"
