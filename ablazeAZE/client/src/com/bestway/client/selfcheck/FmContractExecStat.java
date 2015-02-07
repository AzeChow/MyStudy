/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.selfcheck;

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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcs.client.contractanalyse.JContractList;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractstat.action.ContractStatAction;
import com.bestway.bcs.contractstat.entity.ImpMaterialStat;
import com.bestway.bcs.contractstat.entity.ImpMaterialStatResult;
import com.bestway.bcus.cas.action.CasCheckAction;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.dzsc.client.dzscmanage.JDzscEmsPorHeadList;
import com.bestway.dzsc.contractstat.action.DzscStatAction;
import com.bestway.dzsc.contractstat.entity.DzscImpMaterialStatResult;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.message.action.DzscMessageAction;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;


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
 * 未转报关单数量=申请单中的送货单转报关单的数量（风岗嘉辉嘉安进出货单据转报关单）
 * 可签关封数量=合同定量-已签关数量
 */
public class FmContractExecStat extends JDialogBase {
	private javax.swing.JPanel jContentPane = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
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
	
	//电子化手册
	private ContractStatAction contractStatAction = null;
	//电子手册
	private DzscStatAction dzscContractStatAction = null;
	
	private DzscMessageAction dzscMessageAction = null;
	
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
	private JPanel pnRight = null;
	private JScrollPane jScrollPane1 = null;
	
	//电子化手册
	private JContractList contractList = null;
	
	//电子帐册 （无）
	
	//电子手册
	private JDzscEmsPorHeadList dzscList = null;
	
	private JPanel jPanel10 = null;
	private JRadioButton rbCheckedAll = null;
	private JRadioButton rbCheckedNon = null;
	private JButton btnPrint = null;
	private ContractAction contractAction = null;
	
	private CasCheckAction casCheckAction = null;
	
	private ManualDeclareAction manualDecleareAction = null;
	
    //电子 化手册
	private BcsParameterSet parameterSet = null;
	//电子手册
	private DzscParameterSet dzscParameterSet = null;
	
	private JCheckBox cbStatInvoice = null;
	private JCheckBox cbContractExe = null;
	private JPanel pnChecked = null;
	private JCheckBox cbContractCancel = null;
	private JCheckBox cbDetachCompute = null;
	private JSplitPane jSplitPaneCover = null;
	private JLabel jLabel = null;
	private JComboBox cbbType = null;
	
	private List list = null;  //  @jve:decl-index=0:
	
	private int defaultType = 0;
	
	private int currentType = 0;
	/**
	 * This is the default constructor
	 */
	public FmContractExecStat() {
		super();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		
		//wss:新加
		casCheckAction = (CasCheckAction)CommonVars.getApplicationContext()
				.getBean("casCheckAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
					.getApplicationContext().getBean("manualdeclearAction");
		
		contractStatAction = (ContractStatAction) CommonVars
				.getApplicationContext().getBean("contractStatAction");
		dzscContractStatAction = (DzscStatAction) CommonVars
				.getApplicationContext().getBean("dzscStatAction");
		dzscMessageAction = (DzscMessageAction) CommonVars
		.getApplicationContext().getBean("dzscMessageAction");
		initialize();
		getButtonGroup();
		getButtonGroup1();
		this.cbbBeginDate.setDate(null);
		this.cbbEndDate.setDate(null);
		parameterSet = contractAction.findBcsParameterSet(new Request(
				CommonVars.getCurrUser(), true));
		dzscParameterSet = dzscMessageAction.findDzscMessageDirSet(new Request(
				CommonVars.getCurrUser()));

	}


	public void setVisible(boolean b) {
		if (b) {
			initUI(defaultType);//初始化界
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
		this.setTitle("合同执行情况");
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
								if(0 == currentType){
									ImpMaterialStatResult statResult = contractStatAction
										.impMaterialStat(new Request(CommonVars.getCurrUser()),
															tableModel.getList());
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
								}else{
									DzscImpMaterialStatResult statResult = dzscContractStatAction
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
			jPanel.add(getJSplitPaneCover(), BorderLayout.CENTER);
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
			btnCancel.setBounds(new Rectangle(700, 33, 60, 28));
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
			btnQuery.setBounds(new Rectangle(547, 33, 60, 28));
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(currentType == 1){
						DgContractExeQueryCondition dg = new DgContractExeQueryCondition();
						dg.setImport(true);
						dg.setImg(true);
						dg.setVisible(true);
						list = dg.getLsResult();
						if (list != null) {
							initBcusTable(list);
						}
						return;
					}else{
						if((currentType == 0 && contractList.getSelectedContracts().size() == 0)
								||(currentType == 2 && dzscList.getSelectedContracts().size() == 0)){
							JOptionPane.showMessageDialog(FmContractExecStat.this,
									"请选择合同!", "提示!", JOptionPane.WARNING_MESSAGE);
							return;
						}
						new FindData().start();
					}
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
	 * 初始化  电子化手册  数据进口料件情况统计Table
	 */
	private void initTable(List list) {
		if (tableModel != null) {
			tableModel.setList(list);
		} else {
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("序号", "serialNo", 60,
									Integer.class));// 1
							list.add(addColumn("手册号", "emsNo", 100));// 2
							list.add(addColumn("合同号", "impContractNo", 100));// 3
							list.add(addColumn("商品编码", "complex.code", 100));// 4
							list.add(addColumn("品名", "commName", 100));// 5
							list.add(addColumn("规格", "commSpec", 100));// 6
							list.add(addColumn("单位", "unit.name", 50));// 7
							list.add(addColumn("合同定量", "contractAmount", 100));// 8
							list.add(addColumn("总进口量", "allImpTotalAmont",100));// 9
							list.add(addColumn("本期总进口金额", "impTotalMoney",100));// 10
							list.add(addColumn("本期总进口量", "impTotalAmont", 100));// 11
							list.add(addColumn("本期直接进口量", "directImport", 100));// 12
							list.add(addColumn("本期料件转厂量","transferFactoryImport", 100));// 13
							list.add(addColumn("本期余料转入量", "remainImport", 100));// 14
							list.add(addColumn("本期余料转出量", "remainForward",100));// 15
							list.add(addColumn("本期退换进口量", "exchangeImport",100));// 16
							list.add(addColumn("本期退换出口量", "exchangeExport",100));// 17
							list.add(addColumn("本期料件复出量", "backMaterialReturn",100));// 18
							list.add(addColumn("本期料件内销量", "internalAmount",100));// 19
							list.add(addColumn("本期总边角料数量","allTotalleftovermaterial", 100));// 20
							list.add(addColumn("本期边角料内销量", "leftovermaterial",100));// 21
							list.add(addColumn("本期边角料复出量","leftovermaterialExport", 100));// 22
							list.add(addColumn("本期边角料余量","leftovermaterialremain", 100));// 23
							list.add(addColumn("本期成品使用量", "productUse", 100));// 24
							list.add(addColumn("本期成品使用金额", "productUseMoney",100));// 25
							list.add(addColumn("余料情况", "remainAmount", 100));// 26
							list.add(addColumn("库存量", "stockAmount", 100));// 27
							list.add(addColumn("可进口量", "canImportAmount", 100));// 28
							list.add(addColumn("关封余量", "customsEnvelopRemain",100));// 29
							list.add(addColumn("可直接进口量","canDirectImportAmount", 100));// 30
							list.add(addColumn("单价", "unitPrice", 100));// 31
							list.add(addColumn("未转报关单数量", "noTranCustomsNum",120));// 32
							list.add(addColumn("发票数量", "invoiceNum", 120));// 33
							list.add(addColumn("凭证序号", "credenceNo", 60,Integer.class));// 34
							list.add(addColumn("完成百分比", "completeScale", 60));//35
							list.add(addColumn("可签关封", "commodityInfoRemain", 60));//36;
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
			jTable.getColumnModel().getColumn(24).setCellRenderer(
					new DefaultTableCellRenderer() {
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
									// TODO Auto-generated catch block
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
	 * 初始化  电子手册  数据Table
	 */
	private void initDzscTable(List list) {
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
						// list.add(addColumn("凭证序号", "credenceSerialNo", 100));
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
	}
	
	
	/**
	 * 初始化   电子帐册   数据Table
	 */
	private void initBcusTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("备案序号", "bill1", 60, Integer.class));//1
						list.add(addColumn("商品编号", "bill2", 80));//2
						list.add(addColumn("品名", "bill3", 120));//3
						list.add(addColumn("规格", "bill9", 120));//4
						list.add(addColumn("单位", "bill4", 60));//5
						list.add(addColumn("事业部", "bill5", 100));//6
						list.add(addColumn("1.总进口量=2+12-5", "billSum2", 100));//7
						list.add(addColumn("事业部小计", "billSum14", 100));//8
						list.add(addColumn("2.大单进口量=3+4", "billSum3", 100));//9
						list.add(addColumn("3.直接进口量", "billSum4", 80));//10
						list.add(addColumn("4.转厂进口量", "billSum5", 80));//11
						list.add(addColumn("5.退料出口量", "billSum6", 80));//12
						list.add(addColumn("6.成品使用量", "billSum7", 80));//13

						list.add(addColumn("7.边角料", "billSum8", 60));//14
						list.add(addColumn("8.料件内销", "billSum18", 80));//15
						list.add(addColumn("9.余料情况=1-6-8", "billSum9", 120));//16
						list.add(addColumn("10.仓库数量", "billSum11", 80));//17
						list.add(addColumn("11.差异值=9-10", "billSum12", 100));//18
						list.add(addColumn("12.余料结转(进口)", "billSum1", 100));//19
						list.add(addColumn("13.本期成品使用量", "billSum13", 120));//20
						list.add(addColumn("14.本期边角料", "billSum14", 120));//21
						list.add(addColumn("美元余料金额", "billSum10", 120));//22
						list.add(addColumn("本期直接出口使用量", "billSum16", 120));//23
						list.add(addColumn("本期转厂出口使用量", "billSum17", 120));//24
						return list;
					}
				});
		List<JTableListColumn> ckist = tableModel.getColumns();
		for (int i = 0; i < ckist.size(); i++) {
			JTableListColumn cm = (JTableListColumn) ckist.get(i);
			cm.setFractionCount(3);
		}

		String reportDecimalLength = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser(), true),
				BcusParameter.ReportDecimalLength);
		Integer decimalLength = 2;
		if (reportDecimalLength != null)
			decimalLength = Integer.valueOf(reportDecimalLength);
		tableModel.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
	}
	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(25, 5, 57, 23));
			jLabel.setText("手册类型");
			lbTo = new JLabel();
			lbTo.setBounds(new Rectangle(224, 36, 15, 19));
			lbTo.setText("到");
			lbCustomsDeclarationDate = new JLabel();
			lbCustomsDeclarationDate.setBounds(new Rectangle(25, 36, 82, 23));
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
			jPanel2.add(getBtnCancel(), null);
			jPanel2.add(jLabel, null);
			jPanel2.add(getCbbType(), null);
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
			cbbBeginDate.setBounds(new Rectangle(108, 36, 106, 23));
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
			cbbEndDate.setBounds(new Rectangle(248, 36, 106, 23));
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
			rbYes.setBounds(new Rectangle(488, 5, 68, 23));
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
			rbNo.setBounds(new Rectangle(555, 5, 67, 23));
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
			rbAll.setBounds(new Rectangle(627, 5, 51, 23));
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
			jSplitPane.setRightComponent(getPnRight());
			jSplitPane.setDividerLocation(600);
			jSplitPane.setResizeWeight(1);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes pnRight
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnRight() {
		if (pnRight == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setVgap(1);
			pnRight = new JPanel();
			pnRight.setLayout(borderLayout);
			pnRight.add(getJScrollPane1(), BorderLayout.WEST);
			pnRight.add(getJPanel10(), java.awt.BorderLayout.NORTH);
		}
		return pnRight;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			//jScrollPane1.setViewportView(getcontractList());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes contractList
	 * 
	 * @return com.bestway.bcs.client.contractanalyse.JContractList
	 */
	private JContractList getContractList() {
		if (contractList == null) {
			contractList = new JContractList();
			contractList.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					cbbBeginDate.setDate(findBcsBeginDate());
					cbbEndDate.setDate(findBcsEndDate());
				}
			});
		}
		return contractList;
	}
	
	
	/**
	 * This method initializes dzscList
	 * 
	 * @return com.bestway.bcs.client.contractanalyse.JContractList
	 */
	private JDzscEmsPorHeadList getDzscList() {
		if (dzscList == null) {
			dzscList = new JDzscEmsPorHeadList();
			dzscList.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					cbbBeginDate.setDate(findDzscBeginDate());
					cbbEndDate.setDate(findDzscEndDate());
				}
			});
			
		}
		return dzscList;
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
						//电子化手册
						if(currentType == 0){
							for (int i = 0; i < contractList.getModel().getSize(); i++) {
								Contract contract = (Contract) contractList.getModel()
										.getElementAt(i);
								contract.setSelected(true);
							}
							cbbBeginDate.setDate(findBcsBeginDate());
							cbbEndDate.setDate(findBcsEndDate());
							contractList.repaint();
						}else if (currentType == 2){
							//电子手册
							for (int i = 0; i < dzscList.getModel().getSize(); i++) {
								DzscEmsPorHead contract = (DzscEmsPorHead) dzscList.getModel()
											.getElementAt(i);
								contract.setSelected(true);
							}
							cbbBeginDate.setDate(findDzscBeginDate());
							cbbEndDate.setDate(findDzscEndDate());
							dzscList.repaint();
						}
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
	public Date getAllContractBeginDate() {
		Date date = null;
		for (int i = 0; i < contractList.getModel().getSize(); i++) {
			Contract contract = (Contract) contractList.getModel()
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
		for (int i = 0; i < contractList.getModel().getSize(); i++) {
			Contract contract = (Contract) contractList.getModel()
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


	/**
	 * 找出合同中最小的日期
	 * 
	 * @return
	 */
	public Date findBcsBeginDate() {
		List<Date> list = new ArrayList<Date>();
		Date date = null;
		for (int i = 0; i < contractList.getModel().getSize(); i++) {
			Contract contract = (Contract) contractList.getModel()
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
		}
		return date;
	}

	/**
	 * 找出合同中最大的日期
	 * 
	 * @return
	 */
	public Date findBcsEndDate() {
		List<Date> list = new ArrayList<Date>();

		Date date = null;
		for (int i = 0; i < contractList.getModel().getSize(); i++) {
			Contract contract = (Contract) contractList.getModel()
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
		}
		return date;
	}

	/**
	 * 找出合同中最小的日期
	 * 
	 * @return
	 */
	public Date findDzscBeginDate() {
		List<Date> list = new ArrayList<Date>();
		Date date = null;
		for(int i = 0;i < dzscList.getModel().getSize();i++)
		{
			DzscEmsPorHead contract = (DzscEmsPorHead) dzscList.getModel().getElementAt(i);
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
	 * 找出合同中最大的日期
	 * 
	 * @return
	 */
	public Date findDzscEndDate() {
		List<Date> list = new ArrayList<Date>();
		Date date = null;
		for(int i = 0;i < dzscList.getModel().getSize();i++)
		{
			DzscEmsPorHead contract = (DzscEmsPorHead) dzscList.getModel().getElementAt(i);
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
						if(currentType == 0){
							for (int i = 0; i < contractList.getModel().getSize(); i++) {
								Contract contract = (Contract) contractList
										.getModel().getElementAt(i);
								contract.setSelected(false);
								cbbBeginDate.setDate(null);
								cbbEndDate.setDate(null);
							}
							contractList.repaint();
							
						}else if(currentType == 2){
							for (int i = 0; i < dzscList.getModel().getSize(); i++) {
								DzscEmsPorHead contract = (DzscEmsPorHead) dzscList
										.getModel().getElementAt(i);
								contract.setSelected(false);
								cbbBeginDate.setDate(null);
								cbbEndDate.setDate(null);
							}
							dzscList.repaint();
						}
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
			btnPrint.setBounds(new Rectangle(620, 33, 60, 28));
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(0 == currentType){//电子化手册     电子手册
						Date date = new Date();
						JasperReport tempContractNoSubReport = null;
						List<ImpMaterialStat> dlist = tableModel.getList();
						

						if (dlist.size() <= 0) {
							JOptionPane.showMessageDialog(FmContractExecStat.this,
									"没有数据可打印", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						CustomReportDataSource ds = new CustomReportDataSource(
								dlist);

						String subContactEmss = "";
						String subContact = "";
						
						List<Contract> contracts = contractList
									.getSelectedContracts();
						for (Contract c : contracts) {
							subContactEmss += c.getEmsNo() + "\n";
							subContact += c.getImpContractNo() + "\n";
						}


						InputStream reportStream = FmContractExecStat.class
								.getResourceAsStream("report/ImpMaterialStat.jasper");

						Map<String, Object> parameters = new HashMap<String, Object>();

						parameters.put("beginDate",
								cbbBeginDate.getDate() == null ? ""
										: (new SimpleDateFormat("yyyy-MM-dd"))
												.format(cbbBeginDate.getDate()));

						parameters.put("endDate", cbbEndDate.getDate() == null ? ""
								: (new SimpleDateFormat("yyyy-MM-dd"))
										.format(cbbEndDate.getDate()));

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
					}else if(2 == currentType){//电子手册
						if (tableModel.getList().size() < 1) {
							JOptionPane.showMessageDialog(
									FmContractExecStat.this, "没有数据可以打印", "提示",
									JOptionPane.OK_OPTION);
							return;
						}
						CustomReportDataSource ds = new CustomReportDataSource(
								tableModel.getList());
						
						String subContactEmss = "";
						String subContact = "";
						List<DzscEmsPorHead> contracts = dzscList
								.getSelectedContracts();
						for (DzscEmsPorHead c : contracts) {
							subContactEmss += c.getEmsNo() + "\n";
							subContact += c.getIeContractNo() + "\n";
						}
						
						InputStream reportStream = FmContractExecStat.class
								.getResourceAsStream("report/DzscImpMaterialStat.jasper");
						Map<String, Object> parameters = new HashMap<String, Object>();

						parameters.put("subContactEmss", subContactEmss);
						parameters.put("subContact", subContact);
						
						parameters.put("beginDate",
								cbbBeginDate.getDate() == null ? ""
										: (new SimpleDateFormat("yyyy-MM-dd"))
												.format(cbbBeginDate.getDate()));

						parameters.put("endDate", cbbEndDate.getDate() == null ? ""
								: (new SimpleDateFormat("yyyy-MM-dd"))
										.format(cbbEndDate.getDate()));
						
						JasperPrint jasperPrint;
						try {
							jasperPrint = JasperFillManager.fillReport(
									reportStream, parameters, ds);
							DgReportViewer viewer = new DgReportViewer(jasperPrint);
							viewer.setVisible(true);
						} catch (JRException e1) {
							e1.printStackTrace();
						}
					}else{//电子帐册

						if (tableModel == null) {
							JOptionPane.showMessageDialog(FmContractExecStat.this,
									"没有可打印的记录！", "提示", 2);
							return;
						}
						CommonDataSource imgExgDS = new CommonDataSource(list);

						List company = new Vector(); // 只有一条记录
						company.add(CommonVars.getCurrUser().getCompany());
						CommonDataSource companyDS = new CommonDataSource(company);

						InputStream masterReportStream = FmContractExecStat.class
								.getResourceAsStream("report/ImpUseRecord.jasper");
						InputStream detailReportStream = FmContractExecStat.class
								.getResourceAsStream("report/ImpUseRecordSub.jasper");
						try {
							JasperReport detailReport = (JasperReport) JRLoader
									.loadObject(detailReportStream);
							Map<String, Object> parameters = new HashMap<String, Object>();
							parameters.put("printDate", CommonVars.nowToDate());
							parameters.put("reportTitle", "进口料件情况统计报表");
							parameters.put("companyName", CommonVars.getCurrUser()
									.getCompany().getName());
							parameters.put("imgExgDS", imgExgDS);// 子数据源
							parameters.put("detailReport", detailReport);// 子报表
							JasperPrint jasperPrint;
							jasperPrint = JasperFillManager.fillReport(
									masterReportStream, parameters, companyDS);
							DgReportViewer viewer = new DgReportViewer(jasperPrint);
							viewer.setVisible(true);
						} catch (JRException e1) {
							e1.printStackTrace();
						}
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
		ImpMaterialStatResult statResult = contractStatAction
											.impMaterialStat(new Request(CommonVars.getCurrUser()), 
																tableModel.getList());
		DecimalFormat myformat1 = new DecimalFormat("#.###");
		map.put("ContractImpTotalMoney", statResult.getContractMoney() == null
				|| statResult.getContractMoney() == 0 ? "" : myformat1.format(
				statResult.getContractMoney()).toString());
		
		map.put("ImpTotalMoney", statResult.getImpTotalMoney() == null
				|| statResult.getImpTotalMoney() == 0 ? "" : myformat1.format(
				statResult.getImpTotalMoney()).toString());
		
		map.put("ImpScale", statResult.getImpTotalScale() == null
				|| statResult.getImpTotalScale() == 0 ? "" : myformat1.format(
				statResult.getImpTotalScale()).toString());
		
		map.put("RemainMoney", statResult.getRemainMoney() == null
				|| statResult.getRemainMoney() == 0 ? "" : myformat1.format(
				statResult.getRemainMoney()).toString());
		
		map.put("RemainMoneyScale", statResult.getRemainScale() == null
				|| statResult.getRemainScale() == 0 ? "" : myformat1.format(
				statResult.getRemainScale()).toString());
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
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				int state = -1;
				if (rbAll.isSelected()) {
					state = CustomsDeclarationState.ALL;
				} else if (rbNo.isSelected()) {
					state = CustomsDeclarationState.NOT_EFFECTIVED;
				} else if (rbYes.isSelected()) {
					state = CustomsDeclarationState.EFFECTIVED;
				}
				//电子化手册
				if(currentType == 0){
					List contracts = contractList.getSelectedContracts();
					list = casCheckAction.findImpMaterialStatByContracts(
							new Request(CommonVars.getCurrUser()), contracts,
							cbbBeginDate.getDate(), cbbEndDate.getDate(), state,
							getCbStatInvoice().isSelected(), cbDetachCompute
									.isSelected());
					initTable(list);
				}
				//电子手册
				else if(currentType == 2){
					List contracts = dzscList.getSelectedContracts();
					list = casCheckAction.findImpMaterialStatByDzscEmsPorHeads(new Request(CommonVars
							.getCurrUser()), contracts,CommonVars
							.dateToStandDate(cbbBeginDate.getDate()),
							CommonVars
									.dateToStandDate(cbbEndDate.getDate())
									,state,cbDetachCompute.isSelected());
					initDzscTable(list);
				}
				
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmContractExecStat.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
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
			cbStatInvoice.setBounds(new Rectangle(280, 5, 104, 23));
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
							if (cbContractExe.isSelected()
									&& cbContractCancel.isSelected()) {
								if(currentType == 0 ){
									 contractList.showContractData(true, true);
								}else if(currentType == 2){
									dzscList.showContractData(true, true);
								}
										
							} else if (cbContractExe.isSelected()
									&& !cbContractCancel.isSelected()) {
								if(currentType == 0 ){
									 contractList.showContractData(true, false);
								}else if(currentType == 2){
									dzscList.showContractData(true, false);
								}
							} else if (!cbContractExe.isSelected()
									&& cbContractCancel.isSelected()) {
								if(currentType == 0 ){
									 contractList.showContractData(false,true );
								}else if(currentType == 2){
									dzscList.showContractData(false,true );
								}
							} else {
								if(currentType == 0 ){
									 contractList.showContractData(false, false);
								}else if(currentType == 2){
									dzscList.showContractData(false, false);
								}
							}
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
								if(currentType == 0){
									contractList.showContractData(true, true);
								}else if(currentType == 2){
									dzscList.showContractData(true, true);
								}
							} else if (cbContractExe.isSelected()
									&& !cbContractCancel.isSelected()) {
								if(currentType == 0){
									contractList.showContractData(true, false);
								}else if(currentType == 2){
									dzscList.showContractData(true, false);
								}
							} else if (!cbContractExe.isSelected()
									&& cbContractCancel.isSelected()) {
								if(currentType == 0){
									contractList.showContractData(false, true);
								}else if(currentType == 2){
									dzscList.showContractData(false, true);
								}
							} else {
								if(currentType == 0){
									contractList.showContractData(false, false);
								}else if(currentType == 2){
									dzscList.showContractData(false, false);
								}
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
			cbDetachCompute.setBounds(new Rectangle(390, 5, 77, 21));
			cbDetachCompute.setText("分开统计");
		}
		return cbDetachCompute;
	}

	/**
	 * This method initializes jSplitPaneCover	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPaneCover() {
		if (jSplitPaneCover == null) {
			jSplitPaneCover = new JSplitPane();
			jSplitPaneCover.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPaneCover.setDividerLocation(65);
			jSplitPaneCover.setDividerSize(1);
			jSplitPaneCover.setBottomComponent(getJSplitPane());
			jSplitPaneCover.setTopComponent(getJPanel2());
			jSplitPaneCover.setEnabled(false);
		}
		return jSplitPaneCover;
	}

	/**
	 * This method initializes cbbType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbType() {
		if (cbbType == null) {
			cbbType = new JComboBox();
			cbbType.setBounds(new Rectangle(108, 5, 106, 27));
			cbbType.addItem("电子化手册");
			cbbType.addItem("电子帐册");
			cbbType.addItem("电子手册");
			cbbType.setSelectedIndex(0);
			cbbType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					updateUI(cbbType.getSelectedIndex());
				}
			});
			
		}
		return cbbType;
	}
	
	public void updateUI(int i){
		if(i == currentType){
			return;
		}else{
			currentType = i;//改变当前手册类型
			initUI(currentType);
		}
	}
	
	/*
	 * 初始化界面
	 */
	public void initUI(int i){
		list = new ArrayList();
		initTable(list);
		//电子帐册
		if(i == 1){
			cbStatInvoice.setEnabled(false);
			rbYes.setEnabled(false);
			rbNo.setEnabled(false);
			rbAll.setEnabled(false);
			cbbBeginDate.setEnabled(false);
			cbbEndDate.setEnabled(false);
			cbDetachCompute.setEnabled(false);
			//jPanel1.setVisible(false);
			jTabbedPane.removeTabAt(1);
			
			pnRight.setEnabled(false);
			jSplitPane.setRightComponent(null);
			
			
		}else {
			cbStatInvoice.setEnabled(true);
			rbYes.setEnabled(true);
			rbNo.setEnabled(true);
			rbAll.setEnabled(true);
			cbbBeginDate.setEnabled(true);
			cbbEndDate.setEnabled(true);
			cbDetachCompute.setEnabled(true);
			//jPanel1.setVisible(true);
			jTabbedPane.addTab("统计数据", null, getJPanel1(), null);
			pnRight.setEnabled(true);
			
			//电子化手册
			if(i== 0){
				jSplitPane.setRightComponent(getPnRight());
			jScrollPane1.setViewportView(getContractList());
			}
			//电子手册
			else{
				jSplitPane.setRightComponent(getPnRight());
				jScrollPane1.setViewportView(getDzscList());
			}
					
		}
	}

} // @jve:decl-index=0:visual-constraint="10,10"
