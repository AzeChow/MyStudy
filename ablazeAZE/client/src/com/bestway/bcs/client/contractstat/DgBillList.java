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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contractstat.action.ContractStatAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * 进出口申请单报表
 * 
 * @author Administrator
 * 
 */
public class DgBillList extends JInternalFrameBase {

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

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:

	private ContractAction contractAction = null;

	private BcsParameterSet parameterSet = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel2 = null;

	private JCheckBox cbbImport = null;

	private JCheckBox cbbExport = null;

	private JComboBox cbbImpExpType = null;
	private ButtonGroup buttonGroupType = null; // @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public DgBillList() {
		super();
		contractStatAction = (ContractStatAction) CommonVars
				.getApplicationContext().getBean("contractStatAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		initialize();
		parameterSet = contractAction.findBcsParameterSet(new Request(
				CommonVars.getCurrUser(), true));
		this.getButtonGroup();
		this.getButtonGroupType();
		// iniCompontGUI(true);
	}

	public void setVisible(boolean b) {
		if (b) {
			initTable(new ArrayList());
		}
		super.setVisible(b);
	}

	private void iniCompontGUI(boolean isFlat) {
		if (isFlat) {// 出口
			cbbImpExpType.removeAllItems();
			cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_EXPORT), "成品出口"));
			cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
			cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_MATERIEL_EXPORT), "退料出口"));
			cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REWORK_EXPORT), "返工复出"));
			cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMIAN_MATERIAL_BACK_PORT), "边角料退港"));
			cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES),
					"边角料内销"));
			cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.GENERAL_TRADE_EXPORT), "一般贸易出口"));
			cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.EXPORT_MATERIAL_REBACK), "修理物品复出"));
			cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.EXPORT_EXG_REBACK), "进料成品退换复出"));
		} else {// 进口
			cbbImpExpType.removeAllItems();
			cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_IMPORT), "料件进口"));
			cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_IMPORT), "料件转厂"));
			cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_FACTORY_REWORK), "退厂返工"));
			cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.GENERAL_TRADE_IMPORT), "一般贸易进口"));
			cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.IMPORT_EXG_BACK), "进料成品退换"));
			cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.IMPORT_REPAIR_MATERIAL), "修理物品"));
		}
		cbbImpExpType.setSelectedIndex(-1);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("进出口申请单明细表");
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
			jToolBar.setPreferredSize(new Dimension(19, 70));
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
			btnQuery.setBounds(new Rectangle(643, 18, 71, 24));
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
					String billType = cbbImpExpType.getSelectedItem() == null ? null
							: ((ItemProperty) cbbImpExpType.getSelectedItem())
									.getCode();
					System.out.println("===billType==" + billType);
					Date beginDate = cbbBeginDate.getDate();
					Date endDate = cbbEndDate.getDate();
					List list = contractStatAction.findBillList(new Request(
							CommonVars.getCurrUser()), beginDate, endDate,
							billType, state);
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
			btnExit.setBounds(new Rectangle(723, 18, 60, 24));
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
								"impExpCommodityInfo.impExpRequestBill.billNo",
								100));// 1
						list.add(addColumn("制单号",
								"impExpCommodityInfo.makeBillNo", 100));// 2
						list.add(addColumn("料号",
								"impExpCommodityInfo.materiel.ptNo", 90));// 3
						list.add(addColumn("数量",
								"impExpCommodityInfo.quantity", 60));// 4
						list
								.add(addColumn(
										"单位",
										"impExpCommodityInfo.materiel.calUnit.name",
										50));// 5
						// list.add(addColumn("进出口标志",
						// "bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.impExpFlag",
						// 70));//1
						list
								.add(addColumn(
										"合同号",
										"bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.contract",
										70));// 6
						list.add(addColumn("合同序号",
								"impExpCommodityInfo.seqNum", 50));// 7

						list.add(addColumn("备案名称",
								"impExpCommodityInfo.afterName", 100));// 10
						list.add(addColumn("报关数量",
								"impExpCommodityInfo.tempAmount", 60));// 11
						list.add(addColumn("备案单位",
								"impExpCommodityInfo.afterUnit", 50));// 12

						list.add(addColumn("单价",
								"impExpCommodityInfo.unitPrice", 60));// 15

						list.add(addColumn("件数", "impExpCommodityInfo.piece",
								50));// 16
						list.add(addColumn("箱号", "impExpCommodityInfo.boxNo",
								100));// 17
						list.add(addColumn("产销国",
								"impExpCommodityInfo.country.name", 60));// 17
						list.add(addColumn("净重",
								"impExpCommodityInfo.netWeight", 50));// 25
						list.add(addColumn("毛重",
								"impExpCommodityInfo.grossWeight", 50));// 24
						list
								.add(addColumn(
										"报关单号",
										"bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.customsDeclarationCode",
										100));// 27
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
		// jTable.getColumnModel().getColumn(6).setCellRenderer(
		// new DefaultTableCellRenderer() {
		// public Component getTableCellRendererComponent(
		// JTable table, Object value, boolean isSelected,
		// boolean hasFocus, int row, int column) {
		// super.getTableCellRendererComponent(table, value,
		// isSelected, hasFocus, row, column);
		// String str = "";
		// if (value != null) {
		// switch (Integer.parseInt(value.toString())) {
		// case ImpExpFlag.IMPORT:
		// str = "进口报关单";
		// break;
		// case ImpExpFlag.EXPORT:
		// str = "出口报关单";
		// break;
		// case ImpExpFlag.SPECIAL:
		// str = "特殊报关单";
		// break;
		// }
		// }
		// this.setText(str);
		// return this;
		// }
		// });

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(500, 43, 20, 18));
			jLabel2.setText("至");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(288, 41, 108, 18));
			jLabel1.setText("申请单  录入日期");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(290, 11, 68, 20));
			jLabel.setText("申请单状态");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setSize(new Dimension(791, 40));
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
			jPanel.add(getCbbImport(), null);
			jPanel.add(getCbbExport(), null);
			jPanel.add(getCbbImpExpType(), null);
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
			rbYes.setBounds(new Rectangle(397, 6, 64, 20));
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
			rbNo.setBounds(new Rectangle(470, 6, 64, 20));
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
			rbAll.setBounds(new Rectangle(547, 7, 59, 20));
			rbAll.setText("\u5168\u90e8");
		}
		return rbAll;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroupType() {
		if (buttonGroupType == null) {
			buttonGroupType = new ButtonGroup();
			buttonGroupType.add(cbbExport);
			buttonGroupType.add(cbbImport);
		}
		return buttonGroupType;
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
			cbbBeginDate.setBounds(new Rectangle(397, 40, 103, 22));
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
			cbbEndDate.setBounds(new Rectangle(521, 40, 103, 22));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes cbbImport
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbbImport() {
		if (cbbImport == null) {
			cbbImport = new JCheckBox();
			cbbImport.setBounds(new Rectangle(17, 5, 112, 21));
			cbbImport.setText("单据进口类型");
			cbbImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					iniCompontGUI(false);
				}
			});
		}
		return cbbImport;
	}

	/**
	 * This method initializes cbbExport
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbbExport() {
		if (cbbExport == null) {
			cbbExport = new JCheckBox();
			cbbExport.setBounds(new Rectangle(17, 39, 111, 21));
			cbbExport.setText("单据出口类型");
			cbbExport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					iniCompontGUI(true);
				}
			});
		}
		return cbbExport;
	}

	/**
	 * This method initializes cbbImpExpType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(new Rectangle(129, 18, 149, 23));
		}
		return cbbImpExpType;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
