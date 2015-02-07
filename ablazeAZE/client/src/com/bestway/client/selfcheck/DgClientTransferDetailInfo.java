package com.bestway.client.selfcheck;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.client.contractanalyse.DgPreCustomsDeclaration;
import com.bestway.bcs.client.contractanalyse.JContractList;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractstat.action.ContractStatAction;
import com.bestway.bcs.contractstat.entity.ImpExpCustomsDeclarationCommInfo;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgClientTransferDetailInfo extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton btnQuery = null;

	private JButton btnPrint = null;

	private JButton btnExit = null;

	private JTableListModel tableModel = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JRadioButton rbYes = null;

	private JRadioButton rbNo = null;

	private JRadioButton rbAll = null;

	private ButtonGroup buttonGroup = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane1 = null;

	private JContractList jList11 = null;//这个到时须改

	private JPanel jPanel10 = null;

	private JRadioButton jRadioButton9 = null;

	private JRadioButton jRadioButton10 = null;

	private JLabel jLabel1 = null;

	private ButtonGroup buttonGroup1 = null;

	private JComboBox cbbContract = null;


	/**
	 * This is the default constructor
	 */
	public DgClientTransferDetailInfo() {
		super();
		initialize();
		getButtonGroup();
		getButtonGroup1();
		}

	public void setVisible(boolean b) {
		if (b) {
			List list = new ArrayList();
			initTable(list);
			jSplitPane.setDividerLocation(0.8);
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
		this.setTitle("出口成品报关登记表");
		this.setSize(815, 534);
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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setBounds(new Rectangle(408, 3, 67, 24));
		}
		return btnQuery;
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
			btnPrint.setBounds(new Rectangle(613, 4, 67, 24));
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
	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();

						list.add(addColumn("手册编号",
								"baseCustomsDeclaration.emsHeadH2k", 100));//1
						list.add(addColumn("合同序号", "commSerialNo", 100));//2
						
						list.add(addColumn("报关日期",
								"baseCustomsDeclaration.declarationDate", 100));//3
						list.add(addColumn("运输工具名称",
								"baseCustomsDeclaration.conveyance", 100));//4	
						list
						.add(addColumn(
								"报关单号",
								"baseCustomsDeclaration.customsDeclarationCode",
								100));//5
						list.add(addColumn("报关单流水号",
								"baseCustomsDeclaration.serialNumber", 100));//6
						list.add(addColumn("商品项号", "serialNumber", 100));//7
						list.add(addColumn("商品编码", "complex.code", 100));//8
						list.add(addColumn("品名", "commName", 100));//9
						list.add(addColumn("规格", "commSpec", 100));//10
						list.add(addColumn("单位", "unit.name", 50));//11
						list.add(addColumn("币制", "baseCustomsDeclaration.currency", 50));//12
						list.add(addColumn("数量", "commAmount", 100));//13
						list.add(addColumn("价值", "commTotalPrice", 100));//14
						list.add(addColumn("数量累计", "commAddUpAmount", 100));//15
						list.add(addColumn("最终目的国", "country.name", 100));//16
					
						list.add(addColumn("进出口类型",
								"baseCustomsDeclaration.impExpType", 100));//17
						list.add(addColumn("贸易方式",
								"baseCustomsDeclaration.tradeMode.name", 100));//18
						list.add(addColumn("出口口岸",
								"baseCustomsDeclaration.customs.name", 100));//19
						list.add(addColumn("客户名称",
								"baseCustomsDeclaration.customer.name", 100));//20
						list.add(addColumn("法定数量", "firstAmount", 100));//21
						list.add(addColumn("法定单位", "legalUnit.name", 100));//22
						list.add(addColumn("报关单单价", "commUnitPrice", 100));//23
						list.add(addColumn("合同单价", "contractUnitPrice", 100));//24

						list.add(addColumn("毛重", "grossWeight", 100));//25
						list.add(addColumn("净重", "netWeight", 100));//26
						list.add(addColumn("总毛重",
								"baseCustomsDeclaration.grossWeight", 100));//27
						list.add(addColumn("总净重",
								"baseCustomsDeclaration.netWeight", 100));//28
						list.add(addColumn("发票号",
								"baseCustomsDeclaration.invoiceCode", 100));//29
						list.add(addColumn("核销单号",
								"baseCustomsDeclaration.authorizeFile", 100));//30
						list.add(addColumn("加工费单价", "processUnitPrice", 100));//31
						// list.add(addColumn("总重量", "grossWeight", 100));
						list.add(addColumn("集装箱号", "baseCustomsDeclaration.containerNum", 150));	
						return list;
					}
				});

		// 显示小数位,默认2位
		Integer decimalLength = 2;
		tableModel.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other != null) {
			tableModel.setAllColumnsshowThousandthsign(other
					.getIsAutoshowThousandthsign() == null ? false : other
					.getIsAutoshowThousandthsign());
		}
		CommonVars.castMultiplicationValue(jTable, 14, 13, 23, decimalLength);
		CommonVars.castMultiplicationValue(jTable, 27, 25, 13, decimalLength);
		CommonVars.castMultiplicationValue(jTable, 28, 26, 13, decimalLength);

		jTable.getColumnModel().getColumn(17).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = ImpExpType.getImpExpTypeDesc(Integer
									.parseInt(value.toString()));
						}
						this.setText(str);
						return this;
					}
				});
		// this.tableModel.setExcelFileName(this.contract.getEmsNo());
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(0, 6, 68, 20));
			jLabel.setText("报关单状态");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getRbYes(), null);
			jPanel.add(getRbNo(), null);
			jPanel.add(getRbAll(), null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnPrint(), null);
			jPanel.add(getCbbContract(), null);
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
			rbYes.setBounds(new Rectangle(96, 4, 64, 20));
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
			rbNo.setBounds(new Rectangle(161, 4, 64, 20));
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
			rbAll.setBounds(new Rectangle(229, 4, 59, 20));
			rbAll.setText("\u5168\u90e8");
		}
		return rbAll;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(rbYes);
			buttonGroup.add(rbNo);
			buttonGroup.add(rbAll);
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getJScrollPane());
			jSplitPane.setRightComponent(getJPanel1());
			jSplitPane.setDividerSize(5);
			jSplitPane.setDividerLocation(630);
			jSplitPane.setResizeWeight(1);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("\u6b63\u5728\u6267\u884c\u7684\u5408\u540c:");
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane1(), BorderLayout.CENTER);
			jPanel1.add(getJPanel10(), BorderLayout.NORTH);
		}
		return jPanel1;
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
	private JContractList getJList11() {
		if (jList11 == null) {
			jList11 = new JContractList();
		}
		return jList11;
	}

	/**
	 * This method initializes jPanel10
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel10() {
		if (jPanel10 == null) {
			jPanel10 = new JPanel();
			jPanel10.setLayout(null);
			jPanel10.setPreferredSize(new Dimension(166, 25));
			jPanel10.add(getJRadioButton9(), null);
			jPanel10.add(getJRadioButton10(), null);
		}
		return jPanel10;
	}

	/**
	 * This method initializes jRadioButton9
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton9() {
		if (jRadioButton9 == null) {
			jRadioButton9 = new JRadioButton();
			jRadioButton9.setBounds(new Rectangle(3, 1, 67, 22));
			jRadioButton9.setText("\u5168\u9009");
		}
		return jRadioButton9;
	}

	/**
	 * This method initializes jRadioButton10
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton10() {
		if (jRadioButton10 == null) {
			jRadioButton10 = new JRadioButton();
			jRadioButton10.setBounds(new Rectangle(85, 2, 67, 22));
			jRadioButton10.setText("\u5168\u5426");
			jRadioButton10.setSelected(true);
		}
		return jRadioButton10;
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

	/**
	 * This method initializes cbbContract
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbContract() {
		if (cbbContract == null) {
			cbbContract = new JComboBox();
			cbbContract.setBounds(new Rectangle(486, 4, 127, 24));
		}
		return cbbContract;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
