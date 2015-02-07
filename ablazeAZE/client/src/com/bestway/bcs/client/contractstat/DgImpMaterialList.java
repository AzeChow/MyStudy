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
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import javax.swing.JCheckBox;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgImpMaterialList extends JInternalFrameBase {

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

	private ButtonGroup buttonGroup = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane1 = null;

	private JContractList jList11 = null;

	private JPanel jPanel10 = null;

	private JRadioButton jRadioButton9 = null;

	private JRadioButton jRadioButton10 = null;

	private ButtonGroup buttonGroup1 = null;

	private JComboBox cbbContract = null;

	private ContractAction contractAction = null;

	private BcsParameterSet parameterSet = null;

	private JCheckBox cbContractExe = null;

	private JCheckBox cbContractCancel = null;

	private JPanel jPanel2 = null;
	
	private SystemAction systemAction = null;

	/**
	 * This is the default constructor
	 */
	public DgImpMaterialList() {
		super();
		contractStatAction = (ContractStatAction) CommonVars
				.getApplicationContext().getBean("contractStatAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
		.getBean("systemAction");
		initialize();
		this.getButtonGroup();
		parameterSet = contractAction.findBcsParameterSet(new Request(
				CommonVars.getCurrUser(), true));
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
		this.setTitle("进口料件报关登记表");
		this.setSize(750, 510);
		this.setContentPane(getJContentPane());
		getButtonGroup1();
		initcbbContract();
	}

	private void initcbbContract() {
		List contracts = contractAction.findContractByProcessExe(new Request(
				CommonVars.getCurrUser()));
		this.cbbContract.removeAllItems();
		if (contracts != null && contracts.size() > 0) {
			// for (int i = 0; i < contracts.size(); i++) {
			// this.cbbContract.addItem((Contract) contracts.get(i));
			// }
			this.cbbContract.setRenderer(CustomBaseRender.getInstance()
					.getRender("impContractNo", "emsNo", 0, 100)
					.setForegroundColor(java.awt.Color.red));
		}
		if (this.cbbContract.getItemCount() > 0) {
			cbbContract.setSelectedIndex(0);
		}
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
			btnQuery.setBounds(new Rectangle(437, 2, 58, 24));
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List clist = jList11.getSelectedContracts();
					if (clist.size() == 0) {
						JOptionPane.showMessageDialog(DgImpMaterialList.this,
								"请选择合同!", "提示!", JOptionPane.WARNING_MESSAGE);
						return;
					}
					int state = -1;
					if (rbAll.isSelected()) {
						state = CustomsDeclarationState.ALL;
					} else if (rbNo.isSelected()) {
						state = CustomsDeclarationState.NOT_EFFECTIVED;
					} else if (rbYes.isSelected()) {
						state = CustomsDeclarationState.EFFECTIVED;
					}
					DgStatQueryCondition dg = new DgStatQueryCondition();
					dg.setContracts(clist);
					dg.setImport(true);
					dg.setState(state);
					dg.setVisible(true);
					List list = dg.getLsResult();
					if (list != null  ) {
						list = contractStatAction.getTotal(new Request(CommonVars.getCurrUser()), list);
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
			btnPrint.setBounds(new Rectangle(621, 2, 58, 24));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbContract.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(DgImpMaterialList.this,
								"没有正在执行的合同!", "提示!",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					Contract contract = (Contract) cbbContract
							.getSelectedItem();
					List<ImpExpCustomsDeclarationCommInfo> dlist = tableModel
							.getList();
					List dsList = new ArrayList();
					for (ImpExpCustomsDeclarationCommInfo data : dlist) {
						if(data.getBaseCustomsDeclaration()!=null){
							if (data.getBaseCustomsDeclaration().getEmsHeadH2k()
									.equals(
											((Contract) cbbContract
													.getSelectedItem()).getEmsNo())) {
								dsList.add(data);
							}
						}
					}
					if (dsList.size() <= 0) {
						JOptionPane.showMessageDialog(DgImpMaterialList.this,
								"没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					CustomReportDataSource ds = new CustomReportDataSource(
							dsList);
					InputStream reportStream = DgPreCustomsDeclaration.class
							.getResourceAsStream("report/ImpExpCommodityStatus.jasper");
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("isImport", new Boolean(true));
					parameters.put("contractNo", contract.getExpContractNo());
					parameters.put("emsNo", contract.getEmsNo());
					parameters.put("beginDate",
							contract.getBeginDate() == null ? "" : dateFormat
									.format(contract.getBeginDate()));
					parameters.put("effectiveDate",
							contract.getEndDate() == null ? "" : dateFormat
									.format(contract.getEndDate()));
					parameters.put("companyName", CommonVars.getCurrUser()
							.getCompany().getName());
					parameters.put("reportTitle", "进口料件报关登记表");
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

	int column = 19;
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
						//list.add(addColumn("报关单份数", "bgdNum", 80,
								//Integer.class));//customsPiece
						list.add(addColumn("商品项号", "serialNumber", 100));//8
						list.add(addColumn("商品编码", "complex.code", 100));//9
						list.add(addColumn("品名", "commName", 100));//10
						list.add(addColumn("规格", "commSpec", 100));//11
						list.add(addColumn("规范申报规格", "declareSpec", 100));//12
						list.add(addColumn("单位", "unit.name", 50));//13
						list.add(addColumn("币制", "baseCustomsDeclaration.currency", 50));//14
						list.add(addColumn("数量", "commAmount", 100));//15
						list.add(addColumn("价值", "commTotalPrice", 100));//16
						list.add(addColumn("数量累计", "commAddUpAmount", 100));//17
						list.add(addColumn("原产国", "country.name", 100));//18
						list.add(addColumn("进出口类型",
								"baseCustomsDeclaration.impExpType", 100));//19
						list.add(addColumn("贸易方式",
								"baseCustomsDeclaration.tradeMode.name", 100));//20
						
						list.add(addColumn("供应商名称",
								"baseCustomsDeclaration.customer.name", 100));//21
						list.add(addColumn("法定数量", "firstAmount", 100));//22
						list.add(addColumn("法定单位", "legalUnit.name", 100));//23
						list.add(addColumn("报关单单价", "commUnitPrice", 100));//24
						list.add(addColumn("毛重", "grossWeight", 100));//25
						list.add(addColumn("净重", "netWeight", 100));//26
						list.add(addColumn("总重量", "grossWeight", 100));//27
						list.add(addColumn("车牌号", "baseCustomsDeclaration.billOfLading", 100));//28
						list.add(addColumn("合同单价", "contractUnitPrice", 100));//29
						list.add(addColumn("统一编号",
								"baseCustomsDeclaration.unificationCode", 100));//30
						List lists = systemAction.findReportControl(new Request(
								CommonVars.getCurrUser()));
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
							if(reportControl.getContractAmount()!=null
									&&reportControl.getContractAmount().equals(new Boolean(true))){
								list.add(14,addColumn("合同金额", "dollarTotalPrice",100));
								column = 20;
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
								list.add(addColumn("包装方式(头)", "baseCustomsDeclaration.wrapType.name", 100));//30
							}
							if(reportControl.getWrapTypeDetail()!=null
									&&reportControl.getWrapTypeDetail().equals(new Boolean(true))){
								list.add(addColumn("包装方式(体)", "wrapType.name", 100));//30
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
								list.add(addColumn("境内目的地", "baseCustomsDeclaration.domesticDestinationOrSource.name", 100));//30
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
							if(reportControl.getInvoiceCode()!=null
									&&reportControl.getInvoiceCode().equals(new Boolean(true))){
								list.add(addColumn("发票号", "baseCustomsDeclaration.invoiceCode", 100));//30
							}
							
							if (reportControl.getPreCustomsDeclarationCode() != null
									&& reportControl.getPreCustomsDeclarationCode().equals(
											new Boolean(true))) {
								list.add(addColumn("预录入号",
										"baseCustomsDeclaration.preCustomsDeclarationCode", 100));
							}
							if (reportControl.getCustomdeclarationMun() != null
									&& reportControl.getCustomdeclarationMun().equals(new Boolean(true))) {
								list.add(addColumn("报关单份数","bgdNum", 80,Integer.class));
							}
							if(reportControl.getCreater() != null
									&& reportControl.getCreater().equals(new Boolean(true))){
								list.add(addColumn("录入员","baseCustomsDeclaration.creater.userName",80));
							
							}
							if(reportControl.getDetailNote() != null
									&&reportControl.getDetailNote().equals(new Boolean(true))){
							list.add(addColumn("表体备注", "detailNote", 100));
							}
							
							if(Boolean.TRUE.equals(reportControl.getCustomerCode())){
								int index = findIxdex(list,"供应商名称");
								list.add(index,addColumn("供应商代码",
										"baseCustomsDeclaration.customer.code", 100));
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
	//	CommonVars.castMultiplicationValue(jTable, 28, 26, 13, decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other != null) {
			tableModel.setAllColumnsshowThousandthsign(other
					.getIsAutoshowThousandthsign() == null ? false : other
					.getIsAutoshowThousandthsign());
		}
		jTable.getColumnModel().getColumn(column).setCellRenderer(
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
	 * 查找列的位置,找不到时,返回list.size
	 * @param list
	 * @param caption 列名
	 * @return
	 */
	private int findIxdex(List<JTableListColumn> list,String caption){
		int index = list.size();
		for (int i = 0; i < list.size(); i++) {
			JTableListColumn column = list.get(i);
			if(column.getCaption().equals(caption)){
				return i+1;
			}
		}
		return index;
	}
	
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(2, 5, 68, 20));
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
			rbYes.setBounds(new Rectangle(111, 5, 64, 20));
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
			rbNo.setBounds(new Rectangle(175, 5, 64, 20));
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
			rbAll.setBounds(new Rectangle(235, 5, 59, 20));
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
			jList11.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							replaceItem();
						}
					});

				}

			});
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
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(3);
			gridLayout.setColumns(1);
			jPanel10 = new JPanel();
			jPanel10.setLayout(gridLayout);
			jPanel10.setPreferredSize(new Dimension(171, 60));
			jPanel10.add(getJPanel2(), null);
			jPanel10.add(getCbContractExe(), null);
			jPanel10.add(getCbContractCancel(), null);
			
		}
		return jPanel10;
	}

	/**
	 * 重新添加cbbContract的元素
	 */
	private void replaceItem() {
		List tempList = jList11.getSelectedContracts();
		if (tempList != null && tempList.size() > 0) {
			cbbContract.removeAllItems();
			for (int i = 0; i < tempList.size(); i++) {
				cbbContract.addItem((Contract) tempList.get(i));
			}
		} else {
			initcbbContract();
		}
	}

	/**
	 * This method initializes jRadioButton9
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton9() {
		if (jRadioButton9 == null) {
			jRadioButton9 = new JRadioButton();
			jRadioButton9.setText("\u5168\u9009");
			jRadioButton9.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jRadioButton9.isSelected()) {
						for (int i = 0; i < jList11.getModel().getSize(); i++) {
							Contract contract = (Contract) jList11.getModel()
									.getElementAt(i);
							contract.setSelected(true);
						}
						jList11.repaint();
						replaceItem();
					}
				}
			});
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
			jRadioButton10.setText("\u5168\u5426");
			jRadioButton10.setSelected(true);
			jRadioButton10.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jRadioButton10.isSelected()) {
						for (int i = 0; i < jList11.getModel().getSize(); i++) {
							Contract contract = (Contract) jList11.getModel()
									.getElementAt(i);
							contract.setSelected(false);
						}
						jList11.repaint();
						replaceItem();
					}
				}
			});
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
			cbbContract.setBounds(new Rectangle(498, 2, 122, 24));
		}
		return cbbContract;
	}

	/**
	 * This method initializes cbContractExe	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbContractExe() {
		if (cbContractExe == null) {
			cbContractExe = new JCheckBox();
			cbContractExe.setText("\u6b63\u5728\u6267\u884c\u7684\u5408\u540c");
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
			cbContractCancel.setText("\u6838\u9500\u7684\u5408\u540c");
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
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.add(getJRadioButton9(), new GridBagConstraints());
			jPanel2.add(getJRadioButton10(), new GridBagConstraints());
		}
		return jPanel2;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
