/*
 * Created on 2005-5-20
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractstat;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contractstat.action.ContractStatAction;
import com.bestway.bcus.client.common.CommonVars;
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
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator
 * 
 * Preferences - Java - Code Style - Code Templates
 */
public class DgSpecialCustomsList extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton btnQuery = null;

	private JButton btnPrint = null;

	private JButton btnExit = null;

	private JTableListModel tableModel = null;

	private ContractStatAction contractStatAction = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JRadioButton rbYes = null;

	private JRadioButton rbNo = null;

	private JRadioButton rbAll = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="770,116"

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane1 = null;

	private JList jList11 = null;

	private JLabel jLabel1 = null;

	private ButtonGroup buttonGroup1 = null; // @jve:decl-index=0:visual-constraint="775,161"

	private ContractAction contractAction = null;

	private Boolean flag = false; // @jve:decl-index=0:

	private static String SELECT_ALL = "全选"; // @jve:decl-index=0:

	private static String SELECT_NOT_ALL = "不选";
	
	int state = CustomsDeclarationState.EFFECTIVED;
	
	private SystemAction systemAction = null;

	private BcsParameterSet parameterSet = null;
	/**
	 * This is the default constructor
	 */
	public DgSpecialCustomsList() {
		super();
		contractStatAction = (ContractStatAction) CommonVars
				.getApplicationContext().getBean("contractStatAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
		.getBean("systemAction");
		parameterSet = contractAction.findBcsParameterSet(new Request(
				CommonVars.getCurrUser(), true));
		initialize();
		getButtonGroup();
		getButtonGroup1();

	}

	public void setVisible(boolean b) {
		if (b) {
			List list = new ArrayList();
			initTable(list);
			// initcbbContract();
			jSplitPane.setDividerLocation(0.8);
		}
		super.setVisible(b);
	}

	// private void initcbbContract() {
	// List contracts = contractAction.findContractByProcessExe(new Request(
	// CommonVars.getCurrUser()));
	// this.cbbContract.removeAllItems();
	// if (contracts != null && contracts.size() > 0) {
	// // for (int i = 0; i < contracts.size(); i++) {
	// // this.cbbContract.addItem((Contract) contracts.get(i));
	// // }
	// this.cbbContract.setRenderer(CustomBaseRender.getInstance()
	// .getRender("impContractNo", "emsNo", 0, 100)
	// .setForegroundColor(java.awt.Color.red));
	// }
	// if (this.cbbContract.getItemCount() > 0) {
	// cbbContract.setSelectedIndex(0);
	// }
	// }

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("特殊报关登记表");
		this.setSize(750, 510);
		this.setContentPane(getJContentPane());
		// 
		initList();

	}
	
	private void initList(){
		Vector<Object> vector = new Vector<Object>();
		vector.add(new CheckableItemExtra(SELECT_ALL));
		List list = contractStatAction.findSpecialImpExpType(new Request(
				CommonVars.getCurrUser()), 2 ,state);
		for (int i = 0; i < list.size(); i++) {
			String str = list.get(i).toString();
			CheckableItem item = new CheckableItem(str, ImpExpType
					.getImpExpTypeDesc(Integer.valueOf(str)));
			vector.add(item);
		}

		this.jList11.setListData(vector);
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
			btnQuery.setBounds(new Rectangle(415, 3, 67, 24));
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List clist = new ArrayList();
					List<CheckableItem> checkableItemList = new ArrayList<CheckableItem>();
					for (int j = 0; j < DgSpecialCustomsList.this.jList11
							.getModel().getSize(); j++) {
						Object value = DgSpecialCustomsList.this.jList11
								.getModel().getElementAt(j);
						if (value instanceof CheckableItem) {
							CheckableItem item = (CheckableItem) value;
							if (item.isSelected) {
								checkableItemList.add(item);
								clist.add(item.code);
							}
						}
					}
					if (checkableItemList.size() == 0) {
						JOptionPane.showMessageDialog(
								DgSpecialCustomsList.this, "请选择进出口类型!", "提示!",
								JOptionPane.WARNING_MESSAGE);
						return;
					}

					DgStatQueryCondition dg = new DgStatQueryCondition();
					dg.setContracts(clist);
					dg.setImpExpFlag(2);
					dg.setState(state);
					dg.setVisible(true);
					List list = dg.getLsResult();
					if (list != null) {
						initTable(list);
					}
				}
			});
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
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					List dlist = tableModel.getList();

					if (dlist.size() <= 0) {
						JOptionPane.showMessageDialog(
								DgSpecialCustomsList.this, "没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					CustomReportDataSource ds = new CustomReportDataSource(
							dlist);
					InputStream reportStream = DgSpecialCustomsList.class
							.getResourceAsStream("report/SpecialImpExpCommodityStatus.jasper");
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					Map<String, Object> parameters = new HashMap<String, Object>();

					parameters.put("companyName", CommonVars.getCurrUser()
							.getCompany().getName());
					parameters.put("reportTitle", "特殊报关登记表");

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
						list.add(addColumn("合同号", "baseCustomsDeclaration.contract", 100));//2
						list.add(addColumn("备案序号", "commSerialNo", 100));//3
						list.add(addColumn("报关日期",
								"baseCustomsDeclaration.declarationDate", 100));//4
						list.add(addColumn("运输工具名称",
								"baseCustomsDeclaration.conveyance", 100));//5
						list
						.add(addColumn(
								"报关单号",
								"baseCustomsDeclaration.customsDeclarationCode",
								100));//6
						list.add(addColumn("报关单流水号",
								"baseCustomsDeclaration.serialNumber", 100));//7
						
						list.add(addColumn("商品项号", "serialNumber", 100));//8
						list.add(addColumn("商品编码", "complex.code", 100));//9
						list.add(addColumn("品名", "commName", 100));//10
						list.add(addColumn("规格", "commSpec", 100));//11
						list.add(addColumn("单位", "unit.name", 50));//12
						list.add(addColumn("币制", "baseCustomsDeclaration.currency", 50));//13
						list.add(addColumn("数量", "commAmount", 100));//14
						list.add(addColumn("价值", "commTotalPrice", 100));//15
						list.add(addColumn("数量累计", "commAddUpAmount", 100));//16
						list.add(addColumn("原产国", "country.name", 100));//17
						list.add(addColumn("进出口类型",
								"baseCustomsDeclaration.impExpType", 100));//18
						list.add(addColumn("贸易方式",
								"baseCustomsDeclaration.tradeMode.name", 100));//19
						
						list.add(addColumn("客户/供应商名称",
								"baseCustomsDeclaration.customer.name", 100));//20
						list.add(addColumn("申报单位", "baseCustomsDeclaration.declarationCompany.name",100));
						list.add(addColumn("法定数量", "firstAmount", 100));//21
						list.add(addColumn("法定单位", "legalUnit.name", 100));//22
						list.add(addColumn("报关单单价", "commUnitPrice", 100));//23
						list.add(addColumn("毛重", "grossWeight", 100));//24
						list.add(addColumn("净重", "netWeight", 100));//25
						list.add(addColumn("总重量", "grossWeight", 100));//26
						list.add(addColumn("车牌号", "baseCustomsDeclaration.billOfLading", 100));//27
						list.add(addColumn("合同单价", "contractUnitPrice", 100));//30
						list.add(addColumn("统一编号",
								"baseCustomsDeclaration.unificationCode", 100));// 6
						List lists = systemAction.findReportControl(new Request(
								CommonVars.getCurrUser()));
						System.out.println("登记表lists="+lists.size());
						if(lists != null && lists.size() > 0){
							ReportControl reportControl =null;
							for(int i=0;i<lists.size();i++){
								reportControl = (ReportControl) lists.get(0);
							}
							if(reportControl.getSecondLegalUnit()!=null
									&&reportControl.getSecondLegalUnit().equals(new Boolean(true))){
								list.add(addColumn("第二法定单位", "secondLegalUnit.name", 100));//27
							}
							if(reportControl.getSecondAmount()!=null
									&&reportControl.getSecondAmount().equals(new Boolean(true))){
								list.add(addColumn("第二法定数量", "secondAmount", 100));//28
							}
							if(reportControl.getUses()!=null
									&&reportControl.getUses().equals(new Boolean(true))){
								list.add(addColumn("用途", "uses.name", 100));//29
							}
							if(reportControl.getDeclarationCustoms()!=null
									&&reportControl.getDeclarationCustoms().equals(new Boolean(true))){
								list.add(addColumn("报送海关", "baseCustomsDeclaration.declarationCustoms.name", 100));//30
							}
							if(reportControl.getLevyMode()!=null
									&&reportControl.getLevyMode().equals(new Boolean(true))){
								list.add(addColumn("征减免税方式", "levyMode.name", 100));//30
							}
							if(reportControl.getPieces()!=null
									&&reportControl.getPieces().equals(new Boolean(true))){
								list.add(addColumn("件数", "pieces", 100));//30
							}
							if(reportControl.getWrapType()!=null
									&&reportControl.getWrapType().equals(new Boolean(true))){
								list.add(addColumn("包装方式", "baseCustomsDeclaration.wrapType.name", 100));//30
							}
							if(reportControl.getCertificateCode()!=null
									&&reportControl.getCertificateCode().equals(new Boolean(true))){
								list.add(addColumn("备注证件代码", "baseCustomsDeclaration.certificateCode", 100));//30
							}
							if(reportControl.getCustoms()!=null
									&&reportControl.getCustoms().equals(new Boolean(true))){
								list.add(addColumn("进出口口岸", "baseCustomsDeclaration.customs.name", 100));//30
							}
							if(reportControl.getImpExpDate()!=null
									&&reportControl.getImpExpDate().equals(new Boolean(true))){
								list.add(addColumn("进出口日期", "baseCustomsDeclaration.impExpDate", 100));//30
							}
							if(reportControl.getTransferMode()!=null
									&&reportControl.getTransferMode().equals(new Boolean(true))){
								list.add(addColumn("运输方式", "baseCustomsDeclaration.transferMode.name", 100));//30
							}
							if(reportControl.getMemos()!=null
									&&reportControl.getMemos().equals(new Boolean(true))){
								list.add(addColumn("备注其他说明", "baseCustomsDeclaration.memos", 100));//30
							}
							if(reportControl.getLevyKind()!=null
									&&reportControl.getLevyKind().equals(new Boolean(true))){
								list.add(addColumn("征免性质", "baseCustomsDeclaration.levyKind.name", 100));//30
							}
							if(reportControl.getLicense()!=null
									&&reportControl.getLicense().equals(new Boolean(true))){
								list.add(addColumn("许可证号", "baseCustomsDeclaration.license", 100));//30
							}
							if(reportControl.getCountryOfLoadingOrUnloading()!=null
									&&reportControl.getCountryOfLoadingOrUnloading().equals(new Boolean(true))){
								list.add(addColumn("起运国/运抵国", "baseCustomsDeclaration.countryOfLoadingOrUnloading.name", 100));//30
							}
							if(reportControl.getPredock()!=null
									&&reportControl.getPredock().equals(new Boolean(true))){
								list.add(addColumn("码头", "baseCustomsDeclaration.predock.name", 100));//30
							}
							
							if(reportControl.getDomesticDestinationOrSource()!=null
									&&reportControl.getDomesticDestinationOrSource().equals(new Boolean(true))){
								list.add(addColumn("境内目的地/货源地", "baseCustomsDeclaration.domesticDestinationOrSource.name", 100));//30
							}
							if(reportControl.getPortOfLoadingOrUnloading()!=null
									&&reportControl.getPortOfLoadingOrUnloading().equals(new Boolean(true))){
								list.add(addColumn("装货港/指运港", "baseCustomsDeclaration.portOfLoadingOrUnloading.name", 100));//30
							}
							if(reportControl.getAuthorizeFile()!=null
									&&reportControl.getAuthorizeFile().equals(new Boolean(true))){
								list.add(addColumn("核销单号", "baseCustomsDeclaration.authorizeFile", 100));//30
							}
							if(reportControl.getBondedWarehouse()!=null
									&&reportControl.getBondedWarehouse().equals(new Boolean(true))){
								list.add(addColumn("保税仓库", "baseCustomsDeclaration.bondedWarehouse", 100));//30
							}
							if(reportControl.getTransac()!=null
									&&reportControl.getTransac().equals(new Boolean(true))){
								list.add(addColumn("成交方式", "baseCustomsDeclaration.transac.name", 100));//30
							}
							if(reportControl.getGrossWeight()!=null
									&&reportControl.getGrossWeight().equals(new Boolean(true))){
								list.add(addColumn("总毛重", "baseCustomsDeclaration.grossWeight", 100));//30
							}
							if(reportControl.getNetWeight()!=null
									&&reportControl.getNetWeight().equals(new Boolean(true))){
								list.add(addColumn("总净重", "baseCustomsDeclaration.netWeight", 100));//30
							}
							if(reportControl.getRelativeManualNo()!=null
									&&reportControl.getRelativeManualNo().equals(new Boolean(true))){
								list.add(addColumn("关联手册号", "baseCustomsDeclaration.relativeManualNo", 100));//30
							}
							if(reportControl.getCommodityNum()!=null
									&&reportControl.getCommodityNum().equals(new Boolean(true))){
								list.add(addColumn("总件数", "baseCustomsDeclaration.commodityNum", 100));//30
							}
							if(reportControl.getContainerNum()!=null
									&&reportControl.getContainerNum().equals(new Boolean(true))){
								list.add(addColumn("集装箱号", "baseCustomsDeclaration.containerNum", 100));//30
							}
							if(reportControl.getAttachedBill()!=null
									&&reportControl.getAttachedBill().equals(new Boolean(true))){
								list.add(addColumn("随附单据", "baseCustomsDeclaration.attachedBill", 100));//30
							}
							if(reportControl.getRelativeId()!=null
									&&reportControl.getRelativeId().equals(new Boolean(true))){
								list.add(addColumn("关联报关单号", "baseCustomsDeclaration.relativeId", 100));//30
							}
							if(reportControl.getRelativeId()!=null
									&&reportControl.getRelativeId().equals(new Boolean(true))){
								list.add(addColumn("关封号", "baseCustomsDeclaration.customsEnvelopBillNo", 100));//30
							}
							if (reportControl.getPreCustomsDeclarationCode() != null
									&& reportControl.getPreCustomsDeclarationCode().equals(
											new Boolean(true))) {
								list.add(addColumn("预录入号",
										"baseCustomsDeclaration.preCustomsDeclarationCode", 100));
							}
							
						}
						return list;
					}
				});

		// 显示小数位,默认2位
		Integer decimalLength = 2;
		if (parameterSet != null
				&& parameterSet.getReportDecimalLength() != null)
			decimalLength = parameterSet.getReportDecimalLength();
		tableModel.setAllColumnsFractionCount(decimalLength);
//		CommonVars.castMultiplicationValue(jTable, 14, 13, 23, decimalLength);
//		CommonVars.castMultiplicationValue(jTable, 27, 25, 13, decimalLength);
//		CommonVars.castMultiplicationValue(jTable, 28, 26, 13, decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other != null) {
			tableModel.setAllColumnsshowThousandthsign(other
					.getIsAutoshowThousandthsign() == null ? false : other
					.getIsAutoshowThousandthsign());
		}
		jTable.getColumnModel().getColumn(18).setCellRenderer(
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
			jLabel.setBounds(new Rectangle(0, 4, 68, 20));
			jLabel.setText("报关单状态");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getRbYes(), null);
			jPanel.add(getRbNo(), null);
			jPanel.add(getRbAll(), null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnPrint(), null);
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
			rbYes.setBounds(new Rectangle(80, 4, 65, 20));
			rbYes.setText("\u5df2\u751f\u6548");
			rbYes.setSelected(true);
			rbYes.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (rbAll.isSelected()) {
						state = CustomsDeclarationState.ALL;
					} else if (rbNo.isSelected()) {
						state = CustomsDeclarationState.NOT_EFFECTIVED;
					} else if (rbYes.isSelected()) {
						state = CustomsDeclarationState.EFFECTIVED;
					}
					initList();
				}
			});
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
			rbNo.setBounds(new Rectangle(155, 4, 65, 20));
			rbNo.setText("\u672a\u751f\u6548");
			rbNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					
					if (rbAll.isSelected()) {
						state = CustomsDeclarationState.ALL;
					} else if (rbNo.isSelected()) {
						state = CustomsDeclarationState.NOT_EFFECTIVED;
					} else if (rbYes.isSelected()) {
						state = CustomsDeclarationState.EFFECTIVED;
					}
					initList();
				}
			});
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
			rbAll.setBounds(new Rectangle(220, 5, 59, 20));
			rbAll.setText("\u5168\u90e8");
			rbAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					
					if (rbAll.isSelected()) {
						state = CustomsDeclarationState.ALL;
					} else if (rbNo.isSelected()) {
						state = CustomsDeclarationState.NOT_EFFECTIVED;
					} else if (rbYes.isSelected()) {
						state = CustomsDeclarationState.EFFECTIVED;
					}
					initList();
				}
			});
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
			jSplitPane.setDividerSize(4);
			jSplitPane.setDividerLocation(600);
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
			jLabel1.setText("进出口类型：");
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane1(), BorderLayout.CENTER);
			jPanel1.add(jLabel1, BorderLayout.NORTH);
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
	private JList getJList11() {
		if (jList11 == null) {
			jList11 = new JList();
			jList11.setCellRenderer(new CheckListRenderer());
			jList11.setFixedCellHeight(18);
			jList11.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int index = jList11.locationToIndex(e.getPoint());
					Object obj = jList11.getModel().getElementAt(index);
					if (obj instanceof CheckableItemExtra) {
						CheckableItemExtra item = (CheckableItemExtra) obj;
						item.setSelected(!item.isSelected());
						if (item.isSelected()) {
							item.setName(SELECT_NOT_ALL);
						} else {
							item.setName(SELECT_ALL);
						}
						ListModel model = jList11.getModel();
						for (int i = 0; i < model.getSize(); i++) {
							Object o = model.getElementAt(i);
							if (o instanceof CheckableItem) {
								CheckableItem c = (CheckableItem) o;
								c.setSelected(item.isSelected());
							}
						}
						jList11.repaint();
					} else if (obj instanceof CheckableItem) {
						CheckableItem item = (CheckableItem) obj;
						item.setSelected(!item.isSelected());
						Rectangle rect = jList11.getCellBounds(index, index);
						jList11.repaint(rect);
					}
				}
			});
		}
		return jList11;
	}

	/**
	 * This method initializes buttonGroup1
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();

		}
		return buttonGroup1;
	}

	class CheckableItem {
		private String code;

		private String name;

		private boolean isSelected;

		public CheckableItem(String str, String name) {
			this.code = str;
			this.name = name;
			isSelected = false;
		}

		public void setSelected(boolean b) {
			isSelected = b;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String toString() {
			return " (" + code + ")" + name;
		}
	}

	class CheckableItemExtra {
		private String name;

		private boolean isSelected;

		public CheckableItemExtra(String name) {
			this.name = name;
			isSelected = false;
		}

		public void setSelected(boolean b) {
			isSelected = b;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

	/**
	 * 设置JList呈现类
	 */

	class CheckListRenderer extends JCheckBox implements ListCellRenderer {

		public CheckListRenderer() {
			setBackground(UIManager.getColor("List.textBackground"));
			setForeground(UIManager.getColor("List.textForeground"));
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean hasFocus) {
			setEnabled(list.isEnabled());

			if (value instanceof CheckableItemExtra) {
				CheckableItemExtra item = (CheckableItemExtra) value;
				setSelected(item.isSelected());
				setSize(350, 15);
				setFont(new Font(getFont().getName(), Font.BOLD, getFont()
						.getSize()));
				setText("  " + item.getName());
			} else if (value instanceof CheckableItem) {
				CheckableItem item = (CheckableItem) value;
				setSelected(item.isSelected());
				setSize(350, 15);
				setFont(new Font(getFont().getName(), Font.PLAIN, getFont()
						.getSize()));
				setText("  " + item.getCode() + " (" + item.getName() + ")");
			}
			return this;
		}
	}

} // @jve:decl-index=0:visual-constraint="10,10"
