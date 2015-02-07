/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractstat;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.ReportControl;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import javax.swing.JCheckBox;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * 申请单→报关单进出口明细表
 * @author Administrator
 *
 */
public class DgBillToCustomsList extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton btnQuery = null;

	private JButton btnExit = null;

	private JTableListModel tableModel = null;

	private ContractStatAction contractStatAction = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JRadioButton rbYes = null;

	private JRadioButton rbNo = null;

	private JRadioButton rbAll = null;

	private ButtonGroup buttonGroup = null;

	private ContractAction contractAction = null;

	private BcsParameterSet parameterSet = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel2 = null;

	/**
	 * This is the default constructor
	 */
	public DgBillToCustomsList() {
		super();
		contractStatAction = (ContractStatAction) CommonVars
				.getApplicationContext().getBean("contractStatAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		initialize();
		this.getButtonGroup();
		parameterSet = contractAction.findBcsParameterSet(new Request(
				CommonVars.getCurrUser(), true));
	}

	
	public void setVisible(boolean b) {
		if (b) {
			List list = new ArrayList();
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
		this.setTitle("申请单→报关单进出口明细表");
		this.setSize(805, 510);
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
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
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
			jToolBar.setPreferredSize(new Dimension(19, 35));
			jToolBar.setFloatable(false);
			jToolBar.add(getJPanel());
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
			btnQuery.setBounds(new Rectangle(629, 2, 71, 24));
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int state = -1;
					if (rbAll.isSelected()) {
						state = CustomsDeclarationState.ALL;
					} else if (rbNo.isSelected()) {
						state = CustomsDeclarationState.NOT_EFFECTIVED;
					} else if (rbYes.isSelected()) {
						state = CustomsDeclarationState.EFFECTIVED;
					}
					Date beginDate = cbbBeginDate.getDate();
					Date endDate = cbbEndDate.getDate();
					List list =contractStatAction.findBillToCustomsList(
							new Request(CommonVars.getCurrUser()), beginDate, endDate, state);
					if (list != null) {
						initTable(list);
					}
				}
			});
		}
		return btnQuery;
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
			btnExit.setBounds(new Rectangle(709, 2, 60, 24));
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
						list.add(addColumn("单据号",
								"impExpCommodityInfo.impExpRequestBill.billNo", 100));//1
						list.add(addColumn("制单号", "impExpCommodityInfo.makeBillNo", 100));//2
						list.add(addColumn("料号", "impExpCommodityInfo.materiel.ptNo", 90));//3
						list.add(addColumn("数量",
								"impExpCommodityInfo.quantity", 60));//4
						list.add(addColumn("单位",
								"impExpCommodityInfo.materiel.calUnit.name", 50));//5
						list.add(addColumn("进出口标志",
								"bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.impExpFlag", 70));//1
						list
						.add(addColumn(
								"合同号",
								"bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.contract",
								70));//6
						list.add(addColumn("合同序号","bcsCustomsDeclarationCommInfo.commSerialNo", 50));//7
						
						list.add(addColumn("备案名称", "bcsCustomsDeclarationCommInfo.commName", 100));//10
						list.add(addColumn("报关数量", "bcsCustomsDeclarationCommInfo.commAmount", 60));//11
						list.add(addColumn("备案单位", "bcsCustomsDeclarationCommInfo.unit.name", 50));//12
						list.add(addColumn("单价", "bcsCustomsDeclarationCommInfo.commUnitPrice", 60));//15
						list.add(addColumn("件数", "bcsCustomsDeclarationCommInfo.pieces", 50));//16
						list.add(addColumn("箱号", "bcsCustomsDeclarationCommInfo.boxNo", 100));//17
						list.add(addColumn("产销国", "bcsCustomsDeclarationCommInfo.country.name", 60));//17
						list.add(addColumn("净重", "bcsCustomsDeclarationCommInfo.netWeight", 50));//25
						list.add(addColumn("毛重", "bcsCustomsDeclarationCommInfo.grossWeight", 50));//24
						list.add(addColumn("报关单号", "bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.customsDeclarationCode", 100));//27
						return list;
					}
				});

		// 显示小数位,默认2位
		Integer decimalLength = 2;
		if (parameterSet != null
				&& parameterSet.getReportDecimalLength() != null)
			decimalLength = parameterSet.getReportDecimalLength();
		tableModel.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other != null) {
			tableModel.setAllColumnsshowThousandthsign(other
					.getIsAutoshowThousandthsign() == null ? false : other
					.getIsAutoshowThousandthsign());
		}
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							switch (Integer.parseInt(value.toString())) {
							case ImpExpFlag.IMPORT:
								str = "进口报关单";
								break;
							case ImpExpFlag.EXPORT:
								str = "出口报关单";
								break;
							case ImpExpFlag.SPECIAL:
								str = "特殊报关单";
								break;
							}
						}
						this.setText(str);
						return this;
					}
				});
	
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(492, 8, 20, 18));
			jLabel2.setText("至");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(280, 6, 108, 18));
			jLabel1.setText("申请单  录入日期");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(2, 5, 68, 20));
			jLabel.setText("申请单状态");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getRbYes(), null);
			jPanel.add(getRbNo(), null);
			jPanel.add(getRbAll(), null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnExit(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(jLabel2, null);
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
			rbYes.setBounds(new Rectangle(79, 4, 64, 20));
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
			rbNo.setBounds(new Rectangle(145, 4, 64, 20));
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
			rbAll.setBounds(new Rectangle(215, 5, 59, 20));
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
	 * This method initializes cbbBeginDate	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(389, 5, 103, 22));
			cbbBeginDate.setDate(null);
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
			cbbEndDate.setBounds(new Rectangle(513, 5, 96, 22));
		}
		return cbbEndDate;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
