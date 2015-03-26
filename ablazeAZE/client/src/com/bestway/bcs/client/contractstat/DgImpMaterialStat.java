/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractstat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
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
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.client.contractanalyse.JContractList;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractstat.action.ContractStatAction;
import com.bestway.bcs.contractstat.entity.ImpMaterialStat;
import com.bestway.bcs.contractstat.entity.ImpMaterialStatResult;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * 进口料件情况统计表
 * 
 * lm checked by 2009-09-14
 * 
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
/**
 * 各栏位计算公式 合同定量= 合同中对应料件备案的数量 总进口量=料件直接进口总量+料件转厂总量+料件总余料转入量-总退换出口量
 * 本期总进口量=本期直接进口量+本期转厂量+本期余料转入量-本期退换出口量 本期直接进口量=进口报关单中进口类型为直接进口的数量
 * 本期料件转厂量=进口报关单中进口类型为转厂进口的数量 本期余料转入量=进口报关单中进口类型为余料结转的数量
 * 本期余料转出量=出口报关单中出口类型为余料结转的数量 本期退换进口量=进口报关单中进口类型为直接进口，贸易方式为0300，0700的数量
 * 本期退换出口量=出口报关单中出口类型为退料出口,贸易方式为0300，0700的数量
 * 本期料件复出量=出口报关单中出口类型为退料出口,贸易方式为0265，0664的数量
 * 本期料件内销量=进口报关单中进口类型为海关批准内销,贸易方式为0644，0245的数量 本期总边角料数量=本期成品使用量*(损耗*0.01)
 * 本期边角料内销量=进口报关单中进口类型为海关批准内销,贸易方式为0844，0845的数量
 * 本期边角料复出量=进口报关单中进口类型为海关批准内销,贸易方式为0864，0865的数量 本期边角料余量=本期总边角料数量-本期边角料复出-本期边角料内销
 * 本期成品使用量=本期总出口量*单耗/((1-损耗)*0.01) 本期成品使用金额=本期成品使用量*合同单价
 * 余料情况=本期总进口量－本期料件复出数量（进料料件复出0664／来料料件复出0265）-成品使用量-料件内销 库存量=余料-余料结转出口
 * 可进口量=合同定量-本期总进口量 关封余量=关封管理明细中的本厂数量—转厂进口量 可直接进口量=可进口量-关封余量
 * 未转报关单数量=申请单中的送货单转报关单的数量（风岗嘉辉嘉安进出货单据转报关单） 可签关封数量=合同定量-已签关数量
 */
@SuppressWarnings("unchecked")
public class DgImpMaterialStat extends JInternalFrameBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JPanel jContentPane = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JToolBar jToolBar = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JButton btnCancel = null;
	private JButton btnQuery = null;
	private JLabel lbContractImpTotalMoney = null;
	private JLabel lbImpTotalMoney = null;
	private JLabel lbImpScale = null;
	private JLabel lbRemainMoney = null;
	private JLabel lbRemainMoneyScale = null;
	private JTextField tfContractImpTotalMoney = null;
	private JTextField tfImpTotalMoney = null;
	private JTextField tfImpScale = null;
	private JTextField tfRemainMoney = null;
	private JTextField tfRemainMoneyScale = null;
	private JTableListModel tableModel = null;
	private ContractStatAction contractStatAction = null;
	private JPanel jPanel2 = null;
	private JLabel lbCustomsDeclarationDate = null;
	private JCalendarComboBox cbbBeginDate = null;
	private JLabel lbTo = null;
	private JCalendarComboBox cbbEndDate = null;
	private JRadioButton rbYes = null;
	private JRadioButton rbNo = null;
	private JRadioButton rbAll = null;
	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:
	private ButtonGroup buttonGroup1 = null; // @jve:decl-index=0:
	private JSplitPane jSplitPane = null;
	private JPanel jPanel11 = null;
	private JScrollPane jScrollPane1 = null;
	private JContractList lsContract = null;
	private JPanel jPanel10 = null;
	private JRadioButton rbCheckedAll = null;
	private JRadioButton rbCheckedNon = null;
	private JButton btnPrint = null;
	private ContractAction contractAction = null;
	private BcsParameterSet parameterSet = null;
	private JCheckBox cbStatInvoice = null;
	private JCheckBox cbContractExe = null;
	private JPanel pnChecked = null;
	private JCheckBox cbContractCancel = null;
	private JCheckBox cbDetachCompute = null;

	/**
	 * This is the default constructor
	 */
	public DgImpMaterialStat() {
		super();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		contractStatAction = (ContractStatAction) CommonVars
				.getApplicationContext().getBean("contractStatAction");
		initialize();
		getButtonGroup();
		getButtonGroup1();
		this.cbbBeginDate.setDate(null);
		// this.cbbBeginDate.setDate(findBeginData2());
		this.cbbEndDate.setDate(null);
		parameterSet = contractAction.findBcsParameterSet(new Request(
				CommonVars.getCurrUser(), true));
		// this.cbbBeginDate.setEnabled(false);
	}

	public void setVisible(boolean b) {
		if (b) {
			initTable(new ArrayList());

			jSplitPane.setDividerLocation(0.8);

			lsContract.showContractData(true, false);

		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("进口料件情况统计表");
		this.setSize(868, 538);
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
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
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
			jTabbedPane.addTab("进口料件数据", null, getJPanel(), null);
			jTabbedPane.addTab("统计数据", null, getJPanel1(), null);
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
			jPanel.add(getJToolBar(), BorderLayout.NORTH);
			jPanel.add(getJSplitPane(), BorderLayout.CENTER);
			// jPanel.add(getJTable(), BorderLayout.NORTH);
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
			lbRemainMoneyScale = new JLabel();
			lbRemainMoney = new JLabel();
			lbImpScale = new JLabel();
			lbImpTotalMoney = new JLabel();
			lbContractImpTotalMoney = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			lbContractImpTotalMoney.setBounds(161, 44, 100, 25);
			lbContractImpTotalMoney.setText("合同进口金额");
			lbImpTotalMoney.setBounds(161, 92, 100, 25);
			lbImpTotalMoney.setText("进口料件总值");
			lbImpScale.setBounds(161, 144, 100, 25);
			lbImpScale.setText("进口料件总值比率");
			lbRemainMoney.setBounds(161, 193, 100, 25);
			lbRemainMoney.setText("余料总值");
			lbRemainMoneyScale.setBounds(161, 242, 100, 25);
			lbRemainMoneyScale.setText("余料总值比率");
			jPanel1.add(lbContractImpTotalMoney, null);
			jPanel1.add(lbImpTotalMoney, null);
			jPanel1.add(lbImpScale, null);
			jPanel1.add(lbRemainMoney, null);
			jPanel1.add(lbRemainMoneyScale, null);
			jPanel1.add(getTfContractImpTotalMoney(), null);
			jPanel1.add(getTfImpTotalMoney(), null);
			jPanel1.add(getTfImpScale(), null);
			jPanel1.add(getTfRemainMoney(), null);
			jPanel1.add(getTfRemainMoneyScale(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJPanel2());
			jToolBar.add(getBtnCancel());
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
	 * This method initializes btncancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("退出");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes btnQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setBounds(new Rectangle(631, 3, 64, 24));
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (lsContract.getSelectedContracts().size() == 0) {
						JOptionPane.showMessageDialog(DgImpMaterialStat.this,
								"请选择合同!", "提示!", JOptionPane.WARNING_MESSAGE);
						return;
					}
					// new find().start();
					// SwingUtilities.invokeLater(new FindData());
					new FindData().start();
				}
			});
		}
		return btnQuery;
	}

	/**
	 * This method initializes tfContractImpTotalMoney
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
	 * This method initializes tfImpTotalMoney
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
	 * This method initializes tfImpScale
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
	 * This method initializes tfRemainMoney
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
	 * This method initializes tfRemainMoneyScale
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

	/**
	 * 初始化数据进口料件情况统计Table
	 */
	private void initTable(List list) {
		if (tableModel != null) {
			tableModel.setList(list);
		} else {
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							// list.add(addColumn("手册号", "emsNo", 100));
							list.add(addColumn("序号", "serialNo", 60,
									Integer.class));// 1
							list.add(addColumn("手册号", "emsNo", 100));// 2
							list.add(addColumn("合同号", "impContractNo", 100));// 3
							list.add(addColumn("商品编码", "complex.code", 100));// 4
							list.add(addColumn("品名", "commName", 100));// 5
							list.add(addColumn("规格", "commSpec", 100));// 6
							list.add(addColumn("单位", "unit.name", 50));// 7
							list.add(addColumn("合同定量", "contractAmount", 100));// 8
							list.add(addColumn("总进口量", "allImpTotalAmont", 100));// 9
							list.add(addColumn("本期总进口金额", "impTotalMoney", 100));// 10
							list.add(addColumn("本期总进口量", "impTotalAmont", 100));// 11
							// list.add(addColumn("报关单进口量", "impCDAmount",
							// 100));
							list.add(addColumn("本期直接进口量", "directImport", 100));// 12
							list.add(addColumn("本期料件转厂量",
									"transferFactoryImport", 100));// 13
							list.add(addColumn("本期余料转入量", "remainImport", 100));// 14
							list.add(addColumn("本期余料转出量", "remainForward", 100));// 15
							list.add(addColumn("本期退换进口量", "exchangeImport", 100));// 16
							list.add(addColumn("本期退换出口量", "exchangeExport", 100));// 17
							list.add(addColumn("本期料件复出量", "backMaterialReturn",
									100));// 18
							list.add(addColumn("本期料件内销量", "internalAmount", 100));// 19
							list.add(addColumn("本期总边角料数量",
									"allTotalleftovermaterial", 100));// 20
							list.add(addColumn("本期边角料内销量", "leftovermaterial",
									100));// 21
							list.add(addColumn("本期边角料复出量",
									"leftovermaterialExport", 100));// 22
							list.add(addColumn("本期边角料余量",
									"leftovermaterialremain", 100));// 23
							list.add(addColumn("本期成品使用量", "productUse", 100));// 24
							list.add(addColumn("本期成品使用金额", "productUseMoney",
									100));// 25
							list.add(addColumn("余料情况", "remainAmount", 100));// 26
							// list.add(addColumn("缺料情况", "ullage", 100));
							list.add(addColumn("库存量", "stockAmount", 100));// 27
							list.add(addColumn("可进口量", "canImportAmount", 100));// 28
							// list.add(addColumn("比例", "scale", 100));
							// list.add(addColumn("主料/辅料", "materialType",
							// 100));
							// list.add(addColumn("原产国", "country.name", 100));
							// list.add(addColumn("余料金额", "remainMoney", 100));
							list.add(addColumn("关封余量", "customsEnvelopRemain",
									100));// 29
							list.add(addColumn("可直接进口量",
									"canDirectImportAmount", 100));// 30
							list.add(addColumn("单价", "unitPrice", 100));// 31
							list.add(addColumn("未转报关单数量", "noTranCustomsNum",
									120));// 32
							list.add(addColumn("发票数量", "invoiceNum", 80));// 33
							list.add(addColumn("记录号", "credenceNo", 80,
									Integer.class));// 34
							list.add(addColumn("完成百分比", "completeScale", 60));// 35
							list.add(addColumn("可签关封", "commodityInfoRemain",
									60));// 36;
							list.add(addColumn("归并序号", "innerMergeSeqNum", 60));// 37;
							list.add(addColumn("预计边角料征税量",
									"estimateOvermaterial", 100));// 37;
							list.add(addColumn("合同料件表备注", "note", 120));// 38;
							return list;
						}
					});
			// 显示小数位,默认2位
			Integer decimalLength = 2;
			if (parameterSet != null
					&& parameterSet.getReportDecimalLength() != null)
				decimalLength = parameterSet.getReportDecimalLength();
			// List<JTableListColumn> cm = tableModel.getColumns();
			tableModel.setAllColumnsFractionCount(decimalLength);
			CommonVars.castMultiplicationValue(jTable, 25, 31, 24,
					decimalLength);
			CompanyOther other = CommonVars.getOther();
			if (other != null) {
				tableModel.setAllColumnsshowThousandthsign(other
						.getIsAutoshowThousandthsign() == null ? false : other
						.getIsAutoshowThousandthsign());
			}
			jTable.getColumnModel().getColumn(24)
					.setCellRenderer(new DefaultTableCellRenderer() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							super.getTableCellRendererComponent(table, value,
									isSelected, hasFocus, row, column);
							if (value != null) {
								double remainAmount = 0.0;
								try {
									remainAmount = new DecimalFormat().parse(
											(String) value).doubleValue();
								} catch (ParseException e) {
									e.printStackTrace();
								}// Double.parseDouble(value.toString());
								if (remainAmount < 0) {
									// this.setBackground(Color.RED);
									// this.setForeground(Color.YELLOW);
									this.setForeground(Color.RED);
								} else {
									// this.setBackground(Color.WHITE);
									this.setForeground(Color.BLACK);
								}
							} else {
								// this.setBackground(Color.WHITE);
								this.setForeground(Color.BLACK);
							}
							// if (state == FecavState.INNER_OBTAIN) {
							// this.setText("未使用");
							// } else {
							// this.setText("已使用");
							// }
							return this;
						}
					});
		}
		// this.tableModel.setExcelFileName(this.contract.getEmsNo());
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			lbTo = new JLabel();
			lbTo.setBounds(new Rectangle(166, 3, 15, 19));
			lbTo.setText("到");
			lbCustomsDeclarationDate = new JLabel();
			lbCustomsDeclarationDate.setBounds(new Rectangle(3, 3, 76, 23));
			lbCustomsDeclarationDate.setText("报关申报日期");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.add(lbCustomsDeclarationDate, null);
			jPanel2.add(getCbbBeginDate(), null);
			jPanel2.add(lbTo, null);
			jPanel2.add(getCbbEndDate(), null);
			jPanel2.add(getRbYes(), null);
			jPanel2.add(getRbNo(), null);
			jPanel2.add(getRbAll(), null);
			jPanel2.add(getBtnQuery(), null);
			jPanel2.add(getBtnPrint(), null);
			jPanel2.add(getCbStatInvoice(), null);
			jPanel2.add(getCbDetachCompute(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			// cbbBeginDate.setDate(findBeginData2());
			cbbBeginDate.setBounds(new Rectangle(76, 3, 90, 23));
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
			cbbEndDate.setBounds(new Rectangle(181, 3, 91, 23));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes rbYes
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbYes() {
		if (rbYes == null) {
			rbYes = new JRadioButton();
			rbYes.setBounds(new Rectangle(449, 3, 68, 23));
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
			rbNo.setBounds(new Rectangle(513, 3, 67, 23));
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
			rbAll.setBounds(new Rectangle(579, 3, 51, 23));
			rbAll.setText("全部");
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
			buttonGroup1.add(this.getRbCheckedNon());
			buttonGroup1.add(this.getRbCheckedAll());
		}
		return buttonGroup1;
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
			jSplitPane.setDividerSize(4);
			jSplitPane.setRightComponent(getJPanel11());
			jSplitPane.setDividerLocation(600);
			jSplitPane.setResizeWeight(1);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setVgap(1);
			jPanel11 = new JPanel();
			jPanel11.setLayout(borderLayout);
			jPanel11.add(getJScrollPane1(), BorderLayout.WEST);
			jPanel11.add(getJPanel10(), java.awt.BorderLayout.NORTH);
		}
		return jPanel11;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getLsContract());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes lsContract
	 * 
	 * @return com.bestway.bcs.client.contractanalyse.JContractList
	 */
	private JContractList getLsContract() {
		if (lsContract == null) {
			lsContract = new JContractList();
			lsContract.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					cbbBeginDate.setDate(findBeginDate());
					cbbEndDate.setDate(findEndDate());
				}
			});
		}
		return lsContract;
	}

	/**
	 * This method initializes jPanel10
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel10() {
		if (jPanel10 == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(3);
			gridLayout.setVgap(1);
			gridLayout.setColumns(1);
			jPanel10 = new JPanel();
			jPanel10.setLayout(gridLayout);
			jPanel10.setPreferredSize(new Dimension(154, 60));
			jPanel10.add(pnChecked(), null);
			jPanel10.add(getCbContractExe(), null);
			jPanel10.add(getCbContractCancel(), null);
		}
		return jPanel10;
	}

	/**
	 * This method initializes rbCheckedAll
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbCheckedAll() {
		if (rbCheckedAll == null) {
			rbCheckedAll = new JRadioButton();
			rbCheckedAll.setText("\u5168\u9009");
			rbCheckedAll.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (rbCheckedAll.isSelected()) {
						// for(int i = 0;i < jList11.getModel().getSize();i++)
						// {
						// Contract contract = (Contract) jList11.getModel()
						// .getElementAt(i);
						// contract.setSelected(true);
						// }
						// getAllContractBegainDate();
						cbbBeginDate.setDate(getAllContractBegainDate());
						cbbEndDate.setDate(getAllContractEndDate());
						lsContract.repaint();
					}
				}
			});
		}
		return rbCheckedAll;
	}

	/**
	 * 当合同全部被选中时显示所有合同中的最小开始日期
	 * 
	 * @return
	 */
	public Date getAllContractBegainDate() {
		Date date = null;
		for (int i = 0; i < lsContract.getModel().getSize(); i++) {
			Contract contract = (Contract) lsContract.getModel()
					.getElementAt(i);
			contract.setSelected(true);
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
	 * 当合同全部被选中时显示所有合同中的最大终止日期
	 * 
	 * @return
	 */
	public Date getAllContractEndDate() {
		Date date = null;
		for (int i = 0; i < lsContract.getModel().getSize(); i++) {
			Contract contract = (Contract) lsContract.getModel()
					.getElementAt(i);
			contract.setSelected(true);
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

	// /**
	// * 找出合同中最靠前的日期
	// *
	// * @return
	// */
	// public Date findBeginData()
	// {
	// DateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
	// boolean isSelected = false;
	// boolean before = false;
	// Date beginDate = null;
	// List<Date> list = new ArrayList<Date>();
	// for(int i = 0;i < jList11.getModel().getSize();i++)
	// {
	// Contract contract = (Contract) jList11.getModel().getElementAt(i);
	// isSelected = contract.getIsSelected();
	// if(isSelected)
	// {
	// beginDate = contract.getBeginDate();
	// // System.out.println("beginDate="+beginDate);
	// list.add(beginDate);
	// if(list.size() > 1)
	// {
	// for(int t = 0;t < list.size();t++)
	// {
	// // jList11.getModel().getSize()
	// Date beginDateList = (Date) list.get(t);
	// Date begEndDateList = (Date) list.get(t + 1);
	// before = beginDateList.before(begEndDateList);
	// // String str=myFormatter.format(beginDateList);
	// System.out.println("begEndDateList=" + begEndDateList);
	// System.out.println("begEndDateList" + begEndDateList);
	// if(before)
	// {
	// return beginDateList;
	// }
	// else
	// {
	// beginDateList = begEndDateList;
	// return beginDateList;
	// }
	// }
	// }
	// else
	// {
	// // System.out.println("beginDate="+beginDate);
	// // System.out.println("list="+list.size());
	// continue;
	// }
	// }
	// }
	// return null;
	// }

	/**
	 * 找出合同中最小的日期
	 * 
	 * @return
	 */
	public Date findBeginDate() {
		// List<Contract> selectedList = new ArrayList<Contract>();
		// for(int i = 0;i < jList11.getModel().getSize();i++)
		// {
		// Contract contract = (Contract) jList11.getModel().getElementAt(i);
		// if(contract.getIsSelected() != null && contract.getIsSelected())
		// {
		// selectedList.add(contract);
		// }
		// }
		Date date = null;
		// boolean flag = false;
		for (int i = 0; i < lsContract.getModel().getSize(); i++) {
			Contract contract = (Contract) lsContract.getModel()
					.getElementAt(i);
			if (contract.getIsSelected() == null || !contract.getIsSelected()) {
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
			// flag = true;
		}
		return date;
	}

	/**
	 * 找出合同中最大的日期
	 * 
	 * @return
	 */
	public Date findEndDate() {
		// List<Contract> selectedList = new ArrayList<Contract>();
		// for(int i = 0;i < jList11.getModel().getSize();i++)
		// {
		// Contract contract = (Contract) jList11.getModel().getElementAt(i);
		// if(contract.getIsSelected() != null && contract.getIsSelected())
		// {
		// selectedList.add(contract);
		// }
		// }
		Date date = null;
		// boolean flag = false;
		for (int i = 0; i < lsContract.getModel().getSize(); i++) {
			Contract contract = (Contract) lsContract.getModel()
					.getElementAt(i);
			if (contract.getIsSelected() == null || !contract.getIsSelected()) {
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
			// flag = true;
		}
		return date;
	}

	/**
	 * This method initializes rbCheckedNon
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbCheckedNon() {
		if (rbCheckedNon == null) {
			rbCheckedNon = new JRadioButton();
			rbCheckedNon.setText("\u5168\u5426");
			rbCheckedNon.setSelected(true);
			rbCheckedNon.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (rbCheckedNon.isSelected()) {
						for (int i = 0; i < lsContract.getModel().getSize(); i++) {
							Contract contract = (Contract) lsContract
									.getModel().getElementAt(i);
							contract.setSelected(false);
							cbbBeginDate.setDate(null);
							cbbEndDate.setDate(null);
						}
						lsContract.repaint();
					}
				}
			});
		}
		return rbCheckedNon;
	}

	/**
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setBounds(new Rectangle(704, 3, 64, 24));
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (cbbContract.getSelectedItem() == null) {
					// JOptionPane.showMessageDialog(DgImpMaterialStat.this,
					// "没有正在执行的合同!", "提示!",
					// JOptionPane.INFORMATION_MESSAGE);
					// return;
					// }
					// Contract contract = (Contract) cbbContract
					// .getSelectedItem();
					List<ImpMaterialStat> dlist = tableModel.getList();
					List<Contract> contracts = lsContract
							.getSelectedContracts();
					// List dsList = new ArrayList();
					// for (ImpMaterialStat data : dlist) {
					// if (data.getEmsNo().equals(
					// ((Contract) cbbContract.getSelectedItem())
					// .getEmsNo())) {
					// dsList.add(data);
					// }
					// }
					if (dlist.size() <= 0) {
						JOptionPane.showMessageDialog(DgImpMaterialStat.this,
								"没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							dlist);
					// CustomReportDataSource subds = new
					// CustomReportDataSource(
					// contracts);
					String subContactEmss = "";
					String subContact = "";
					for (Contract c : contracts) {
						subContactEmss += c.getEmsNo() + "\n";
						subContact += c.getImpContractNo() + "\n";
					}
					// System.out.println(subContactEmss);
					InputStream reportStream = DgImpExpScheduleDetail.class
							.getResourceAsStream("report/ImpMaterialStat.jasper");
					// InputStream subreportStream =
					// DgImpExpScheduleDetail.class
					// .getResourceAsStream("report/ImpMaterialStatSub.jasper");
					// try
					// {
					// tempContractNoSubReport = (JasperReport) JRLoader
					// .loadObject(subreportStream);
					// }
					// catch(JRException e2)
					// {
					// e2.printStackTrace();
					// }
					Map<String, Object> parameters = new HashMap<String, Object>();
					// parameters.put("contractNo",
					// contract.getImpContractNo());
					// parameters.put("emsNo", contract.getEmsNo());
					parameters.put(
							"beginDate",
							cbbBeginDate.getDate() == null ? ""
									: (new SimpleDateFormat("yyyy-MM-dd"))
											.format(cbbBeginDate.getDate()));
					// cbbBeginDate.setDate(findBeginData());
					// System.out.println("begin="+findBeginData());
					parameters.put(
							"endDate",
							cbbEndDate.getDate() == null ? ""
									: (new SimpleDateFormat("yyyy-MM-dd"))
											.format(cbbEndDate.getDate()));
					// parameters.put("companyName", contract.getCompany()
					// .getName());
					// parameters.put("sub", tempContractNoSubReport);
					// parameters.put("subData", subds);
					parameters.put("subContactEmss", subContactEmss);
					parameters.put("subContact", subContact);
					putPrintParameters(parameters);
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
		return btnPrint;
	}

	/**
	 * 设置报表打印参数
	 * 
	 * @param map
	 */
	private void putPrintParameters(Map map) {
		ImpMaterialStatResult statResult = contractStatAction.impMaterialStat(
				new Request(CommonVars.getCurrUser()), tableModel.getList());
		DecimalFormat myformat1 = new DecimalFormat("#.###");
		map.put("ContractImpTotalMoney",
				statResult.getContractMoney() == null
						|| statResult.getContractMoney() == 0 ? "" : myformat1
						.format(statResult.getContractMoney()).toString());
		// tfContractImpTotalMoney.setText(statResult.getContractMoney() == null
		// || statResult.getContractMoney() == 0 ? "" : myformat1.format(
		// statResult.getContractMoney()).toString());
		map.put("ImpTotalMoney",
				statResult.getImpTotalMoney() == null
						|| statResult.getImpTotalMoney() == 0 ? "" : myformat1
						.format(statResult.getImpTotalMoney()).toString());
		// tfImpTotalMoney.setText(statResult.getImpTotalMoney() == null
		// || statResult.getImpTotalMoney() == 0 ? "" : myformat1.format(
		// statResult.getImpTotalMoney()).toString());
		map.put("ImpScale",
				statResult.getImpTotalScale() == null
						|| statResult.getImpTotalScale() == 0 ? "" : myformat1
						.format(statResult.getImpTotalScale()).toString());
		// tfImpScale.setText(statResult.getImpTotalScale() == null
		// || statResult.getImpTotalScale() == 0 ? "" : myformat1.format(
		// statResult.getImpTotalScale()).toString());
		map.put("RemainMoney",
				statResult.getRemainMoney() == null
						|| statResult.getRemainMoney() == 0 ? "" : myformat1
						.format(statResult.getRemainMoney()).toString());
		// tfRemainMoney.setText(statResult.getRemainMoney() == null
		// || statResult.getRemainMoney() == 0 ? "" : myformat1.format(
		// statResult.getRemainMoney()).toString());
		map.put("RemainMoneyScale",
				statResult.getRemainScale() == null
						|| statResult.getRemainScale() == 0 ? "" : myformat1
						.format(statResult.getRemainScale()).toString());
		// tfRemainMoneyScale.setText(statResult.getRemainScale() == null
		// || statResult.getRemainScale() == 0 ? "" : myformat1.format(
		// statResult.getRemainScale()).toString());
	}

	/**
	 * 执行进口料件情况统计表多线程类
	 * 
	 * @author Administrator
	 * 
	 */
	class FindData extends Thread {
		public void run() {
			List list = new ArrayList();
			try {
				int state = -1;
				if (rbAll.isSelected()) {
					state = CustomsDeclarationState.ALL;
				} else if (rbNo.isSelected()) {
					state = CustomsDeclarationState.NOT_EFFECTIVED;
				} else if (rbYes.isSelected()) {
					state = CustomsDeclarationState.EFFECTIVED;
				}
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				List contracts = lsContract.getSelectedContracts();
				// 判断手册料件在备案库是否存在;
				Boolean ischeck = contractStatAction.ischeckImgExg(contracts,
						MaterielType.MATERIEL);
				if (ischeck) {
					JOptionPane.showMessageDialog(DgImpMaterialStat.this,
							"所查询的合同中，有料件记录号在备案资料库中不存在，请检查更新");
				}
				list = findImpMaterialStatByContracts(contracts,
						cbbBeginDate.getDate(), cbbEndDate.getDate(), state,
						getCbStatInvoice().isSelected(),
						cbDetachCompute.isSelected());

				if (list == null) {
					tfContractImpTotalMoney.setText("");
					tfImpTotalMoney.setText("");
					tfImpScale.setText("");
					tfRemainMoney.setText("");
					tfRemainMoneyScale.setText("");
					return;
				}
				ImpMaterialStatResult statResult = contractStatAction
						.impMaterialStat(new Request(CommonVars.getCurrUser()),
								list);
				DecimalFormat myformat1 = new DecimalFormat("#.###");
				tfContractImpTotalMoney
						.setText(statResult.getContractMoney() == null
								|| statResult.getContractMoney() == 0 ? ""
								: myformat1.format(
										statResult.getContractMoney())
										.toString());
				tfImpTotalMoney.setText(statResult.getImpTotalMoney() == null
						|| statResult.getImpTotalMoney() == 0 ? "" : myformat1
						.format(statResult.getImpTotalMoney()).toString());
				tfImpScale.setText(statResult.getImpTotalScale() == null
						|| statResult.getImpTotalScale() == 0 ? "" : myformat1
						.format(statResult.getImpTotalScale()).toString());
				tfRemainMoney.setText(statResult.getRemainMoney() == null
						|| statResult.getRemainMoney() == 0 ? "" : myformat1
						.format(statResult.getRemainMoney()).toString());
				tfRemainMoneyScale.setText(statResult.getRemainScale() == null
						|| statResult.getRemainScale() == 0 ? "" : myformat1
						.format(statResult.getRemainScale()).toString());
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgImpMaterialStat.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				initTable(list);
			}
		}
	}

	/**
	 * This method initializes cbStatInvoice
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbStatInvoice() {
		if (cbStatInvoice == null) {
			cbStatInvoice = new JCheckBox();
			cbStatInvoice.setBounds(new Rectangle(270, 3, 104, 23));
			cbStatInvoice.setText("统计发票数据");
		}
		return cbStatInvoice;
	}

	/**
	 * This method initializes cbContractExe
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbContractExe() {
		if (cbContractExe == null) {
			cbContractExe = new JCheckBox();
			cbContractExe.setText("正在执行的合同");
			cbContractExe.setSelected(true);
			cbContractExe
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							/*
							 * 根据所选的状态 初始化所有合同号
							 */
							lsContract.showContractData(
									cbContractExe.isSelected(),
									cbContractCancel.isSelected());

						}
					});
		}
		return cbContractExe;
	}

	/**
	 * This method initializes pnChecked
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel pnChecked() {
		if (pnChecked == null) {
			pnChecked = new JPanel();
			pnChecked.setLayout(new GridBagLayout());
			pnChecked.add(getRbCheckedAll(), new GridBagConstraints());
			pnChecked.add(getRbCheckedNon(), new GridBagConstraints());
		}
		return pnChecked;
	}

	/**
	 * This method initializes cbContractCancel
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbContractCancel() {
		if (cbContractCancel == null) {
			cbContractCancel = new JCheckBox();
			cbContractCancel.setText("核销的合同");
			cbContractCancel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbContractExe.isSelected()
									&& cbContractCancel.isSelected()) {
								lsContract.showContractData(true, true);
							} else if (cbContractExe.isSelected()
									&& !cbContractCancel.isSelected()) {
								lsContract.showContractData(true, false);
							} else if (!cbContractExe.isSelected()
									&& cbContractCancel.isSelected()) {
								lsContract.showContractData(false, true);
							} else {
								lsContract.showContractData(false, false);
							}
						}
					});
		}
		return cbContractCancel;
	}

	/**
	 * This method initializes cbDetachCompute
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbDetachCompute() {
		if (cbDetachCompute == null) {
			cbDetachCompute = new JCheckBox();
			cbDetachCompute.setBounds(new Rectangle(373, 3, 77, 21));
			cbDetachCompute.setText("分开统计");
		}
		return cbDetachCompute;
	}

	public List<ImpMaterialStat> findImpMaterialStatByContracts(List contracts,
			Date beginDate, Date endDate, int state, boolean isCountInvoice,
			boolean isDetachCompute) {
		if (isDetachCompute) {
			System.out.println("分开统计工作");
			List<ImpMaterialStat> lsResult = new ArrayList<ImpMaterialStat>();
			for (int i = 0; i < contracts.size(); i++) {
				CommonProgress.setMessage("统计" + contracts.size() + "本合同中的第"
						+ (i + 1) + "本");
				Contract contract = (Contract) contracts.get(i);
				List<ImpMaterialStat> lsData = contractStatAction
						.findImpMaterialStat(contract, beginDate, endDate,
								state, isCountInvoice);
				for (ImpMaterialStat ims : lsData) {
					ims.setEmsNo(contract.getEmsNo());
					ims.setImpContractNo(contract.getImpContractNo());
					lsResult.add(ims);
				}
			}
			return lsResult;
		} else {
			if (contracts.size() == 1) {
				Contract contract = (Contract) contracts.get(0);
				List<ImpMaterialStat> lsData = contractStatAction
						.findImpMaterialStatForOne(contract, beginDate,
								endDate, state, isCountInvoice);
				for (ImpMaterialStat ims : lsData) {
					ims.setEmsNo("");
				}
				return lsData;
			} else {
				Map map = new HashMap();
				for (int i = 0; i < contracts.size(); i++) {
					CommonProgress.setMessage("统计" + contracts.size()
							+ "本合同中的第" + (i + 1) + "本");
					Contract contract = (Contract) contracts.get(i);
					List<ImpMaterialStat> lsData = contractStatAction
							.findImpMaterialStat(contract, beginDate, endDate,
									state, isCountInvoice);
					for (ImpMaterialStat newData : lsData) {
						newData.setEmsNo("");
						String key = (newData.getComplex().getCode().trim())
								+ (newData.getCommName() == null ? "" : newData
										.getCommName().trim())
								+ (newData.getCommSpec() == null ? "" : newData
										.getCommSpec().trim())
								+ (newData.getUnit() == null ? "" : newData
										.getUnit().getName().trim());
						newData.setUnitPrice(null);
						newData.setCommodityInfoRemain(null);
						// newData.setCredenceNo(null);
						/**
						 * 进口料件情况统计表单独查询一本手册，或者查询多 本手册但是分开统计时，可以带出手册里面料件的说明栏位
						 * 所以多本查询时，去掉备注
						 */
						newData.setNote("");
						if (map.get(key) == null) {
							if (contracts.size() > 1) {
								newData.setSerialNo(null);
							}
							map.put(key, newData);
						} else {
							ImpMaterialStat oldData = (ImpMaterialStat) map
									.get(key);
							/**
							 * 未转报关单数量
							 */
							oldData.setNoTranCustomsNum((oldData
									.getNoTranCustomsNum() == null ? 0.0
									: oldData.getNoTranCustomsNum())
									+ (newData.getNoTranCustomsNum() == null ? 0.0
											: newData.getNoTranCustomsNum()));
							/**
							 * 余料进口
							 */
							oldData.setRemainImport((oldData.getRemainImport() == null ? 0.0
									: oldData.getRemainImport())
									+ (newData.getRemainImport() == null ? 0.0
											: newData.getRemainImport()));
							/**
							 * 余料转出
							 */
							oldData.setRemainForward((oldData
									.getRemainForward() == null ? 0.0 : oldData
									.getRemainForward())
									+ (newData.getRemainForward() == null ? 0.0
											: newData.getRemainForward()));
							/**
							 * 内销数量
							 */
							oldData.setInternalAmount((oldData
									.getInternalAmount() == null ? 0.0
									: oldData.getInternalAmount())
									+ (newData.getInternalAmount() == null ? 0.0
											: newData.getInternalAmount()));
							/**
							 * 料件退换进口数
							 */
							oldData.setExchangeImport((oldData
									.getExchangeImport() == null ? 0.0
									: oldData.getExchangeImport())
									+ (newData.getExchangeImport() == null ? 0.0
											: newData.getExchangeImport()));
							/**
							 * 边角料复出
							 */
							oldData.setLeftovermaterialExport((oldData
									.getLeftovermaterialExport() == null ? 0.0
									: oldData.getLeftovermaterialExport())
									+ (newData.getLeftovermaterialExport() == null ? 0.0
											: newData
													.getLeftovermaterialExport()));
							/**
							 * 边角料内销
							 */
							oldData.setLeftovermaterial((oldData
									.getLeftovermaterial() == null ? 0.0
									: oldData.getLeftovermaterial())
									+ (newData.getLeftovermaterial() == null ? 0.0
											: newData.getLeftovermaterial()));
							/**
							 * 总边角料内销
							 */
							oldData.setAllTotalleftovermaterial((oldData
									.getAllTotalleftovermaterial() == null ? 0.0
									: oldData.getAllTotalleftovermaterial())
									+ (newData.getAllTotalleftovermaterial() == null ? 0.0
											: newData
													.getAllTotalleftovermaterial()));
							/**
							 * 总边角料余量
							 */
							oldData.setLeftovermaterialremain((oldData
									.getLeftovermaterialremain() == null ? 0.0
									: oldData.getLeftovermaterialremain())
									+ (newData.getLeftovermaterialremain() == null ? 0.0
											: newData
													.getLeftovermaterialremain()));
							/**
							 * 退换出口量
							 */
							oldData.setExchangeExport((oldData
									.getExchangeExport() == null ? 0.0
									: oldData.getExchangeExport())
									+ (newData.getExchangeExport() == null ? 0.0
											: newData.getExchangeExport()));
							/**
							 * 成品使用金额
							 */
							oldData.setProductUseMoney((oldData
									.getProductUseMoney() == null ? 0.0
									: oldData.getProductUseMoney())
									+ (newData.getProductUseMoney() == null ? 0.0
											: newData.getProductUseMoney()));
							/**
							 * 关封余量
							 */
							oldData.setCustomsEnvelopRemain((oldData
									.getCustomsEnvelopRemain() == null ? 0.0
									: oldData.getCustomsEnvelopRemain())
									+ (newData.getCustomsEnvelopRemain() == null ? 0.0
											: newData.getCustomsEnvelopRemain()));
							/**
							 * 可直接进口量
							 */
							oldData.setCanDirectImportAmount((oldData
									.getCanDirectImportAmount() == null ? 0.0
									: oldData.getCanDirectImportAmount())
									+ (newData.getCanDirectImportAmount() == null ? 0.0
											: newData
													.getCanDirectImportAmount()));
							/**
							 * 总进口量
							 */
							oldData.setAllImpTotalAmont(oldData
									.getAllImpTotalAmont() == null ? 0.0
									: oldData.getAllImpTotalAmont()
											+ (newData.getAllImpTotalAmont() == null ? 0.0
													: newData
															.getAllImpTotalAmont()));
							/**
							 * 合同定量
							 */
							oldData.setContractAmount(checkNullForDouble(oldData
									.getContractAmount())
									+ checkNullForDouble(newData
											.getContractAmount()));
							/**
							 * 本期总进口量
							 */
							oldData.setImpTotalAmont(checkNullForDouble(oldData
									.getImpTotalAmont()
									+ newData.getImpTotalAmont()));
							/**
							 * 本期总进口金额
							 */
							oldData.setImpTotalMoney(checkNullForDouble(oldData
									.getImpTotalMoney()
									+ newData.getImpTotalMoney()));
							/**
							 * 报关单进口量
							 */
							oldData.setImpCDAmount(checkNullForDouble(oldData
									.getImpCDAmount())
									+ checkNullForDouble(newData
											.getImpCDAmount()));
							/**
							 * 料件进口量
							 */
							oldData.setDirectImport(checkNullForDouble(oldData
									.getDirectImport())
									+ checkNullForDouble(newData
											.getDirectImport()));
							/**
							 * 转厂进口量
							 */
							oldData.setTransferFactoryImport(checkNullForDouble(oldData
									.getTransferFactoryImport())
									+ checkNullForDouble(newData
											.getTransferFactoryImport()));

							/**
							 * 退料退换量
							 */
							oldData.setBackMaterialExchange(checkNullForDouble(oldData
									.getBackMaterialExchange())
									+ checkNullForDouble(newData
											.getBackMaterialExchange()));
							/**
							 * 退料复出量
							 */
							oldData.setBackMaterialReturn(checkNullForDouble(oldData
									.getBackMaterialReturn())
									+ checkNullForDouble(newData
											.getBackMaterialReturn()));
							/**
							 * 退料出口量
							 */
							oldData.setBackMaterialExport(checkNullForDouble(oldData
									.getBackMaterialExport())
									+ checkNullForDouble(newData
											.getBackMaterialExport()));
							/**
							 * 成品使用量
							 */
							oldData.setProductUse(checkNullForDouble(oldData
									.getProductUse())
									+ checkNullForDouble(newData
											.getProductUse()));
							/**
							 * 余量
							 */
							oldData.setRemainAmount(checkNullForDouble(oldData
									.getRemainAmount())
									+ checkNullForDouble(newData
											.getRemainAmount()));
							/**
							 * 缺量
							 */
							oldData.setUllage(checkNullForDouble(oldData
									.getUllage())
									+ checkNullForDouble(newData.getUllage()));
							/**
							 * 库存
							 */
							oldData.setStockAmount(checkNullForDouble(oldData
									.getStockAmount())
									+ checkNullForDouble(newData
											.getStockAmount()));
							/**
							 * 可进口量
							 */
							oldData.setCanImportAmount(checkNullForDouble(oldData
									.getCanImportAmount())
									+ checkNullForDouble(newData
											.getCanImportAmount()));
							/**
							 * 预计边角料征税量=总边角料数量*（总进口数量/总出口成品使用量）
							 */
							if (oldData.getProductUse() != 0.0) {
								oldData.setEstimateOvermaterial(CommonUtils
										.getDoubleExceptNull(oldData
												.getAllTotalleftovermaterial())
										* (CommonUtils
												.getDoubleExceptNull(oldData
														.getImpTotalAmont()) / CommonUtils
												.getDoubleExceptNull(oldData
														.getProductUse())));
							} else {
								oldData.setEstimateOvermaterial(0.0);
							}
							/**
							 * 余料金额
							 */
							oldData.setRemainMoney(checkNullForDouble(oldData
									.getRemainMoney())
									+ checkNullForDouble(newData
											.getRemainMoney()));
							/**
							 * 单价
							 */
							oldData.setUnitPrice(null);
						}
					}
				}
				List lsResult = new ArrayList(map.values());
				Collections.sort(lsResult);
				return lsResult;
			}
		}
	}

	/**
	 * 检查是否为空，如果是则返回0
	 * 
	 * @param dou
	 *            小数Double
	 * @return
	 */
	private Double checkNullForDouble(Double dou) {
		return dou == null ? 0.0 : dou;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
