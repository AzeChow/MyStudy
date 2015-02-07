package com.bestway.dzsc.client.contractstat;

import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.ReportControl;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.client.transferfactory.TransferFactoryQuery;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.dzsc.client.contractanalyse.JDzscContractList;
import com.bestway.dzsc.client.dzscmanage.JDzscEmsPorHeadList;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo;
import com.bestway.dzsc.contractstat.action.DzscStatAction;
import com.bestway.dzsc.contractstat.entity.DzscImpMaterialStat;
import com.bestway.dzsc.contractstat.entity.TempDzscCustomsDeclarCommInfo;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.message.action.DzscMessageAction;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import java.awt.Dimension;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Rectangle;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import java.awt.Font;

/**
 * 电子手册->统计报表->报关等级表(新版)
 * @author 石小凯
 *
 */
public class DgDzscImpMaterialListNew extends JDialogBase {

	private JPanel jPanel = null;
	private JPanel jContentPane = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JLabel lbBeginTime = null;
	private JLabel lbProduct = null;
	private JTextField tfProduct = null;
	private JCalendarComboBox cbbBeginDate = null;
	private JCalendarComboBox cbbEndDate = null;
	private JLabel lbZhi = null;
	private JLabel lbType = null;
	private JButton btnQuery = null;
	private JButton btnPrint = null;
	private JButton btnClose = null;
	private JPanel jPanel4 = null;
	private JPanel jPanel5 = null;
	private JRadioButton jRadioButton = null;
	private JRadioButton jRadioButton1 = null;
	private JCheckBox cbContractExe = null;
	private JScrollPane jScrollPane1 = null;
	private JLabel lbNo = null;
	private JTableListModel tableModel = null;
	private JComboBox cbbImpExpType = null;
	private JDzscEmsPorHeadList jList11 = null;
	private ButtonGroup buttonGroup1 = null;  //  @jve:decl-index=0:
	private JComboBox cbbCommInfo = null;
	private JLabel lbScmcoc = null;
	private JComboBox cbbCustomer = null;
	private MaterialManageAction materialManageAction = null;
	private DzscStatAction contractStatAction = null;
	private DzscMessageAction dzscMessageAction = null;
	private SystemAction systemAction = null;

	private boolean isImport;
	private String printTitle;
	private JButton btnProduct = null;
	private JComboBox cbbBillState = null;
	private JLabel jLabel = null;
	
	public String getPrintTitle() {
		return printTitle;
	}
	public void setPrintTitle(String printTitle) {
		this.printTitle = printTitle;
	}
	public boolean isImport() {
		return isImport;
	}
	public void setImport(boolean isImport) {
		this.isImport = isImport;
	}
	/**
	 * This method initializes 
	 * 
	 */
	public DgDzscImpMaterialListNew() {
		super();
		
		contractStatAction = (DzscStatAction) CommonVars
		.getApplicationContext().getBean("dzscStatAction");
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
		.getBean("systemAction");
		dzscMessageAction = (DzscMessageAction) CommonVars
		.getApplicationContext().getBean("dzscMessageAction");
		initialize();
		getButtonGroup1();
		
	}
	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(this.getJRadioButton());
			buttonGroup1.add(this.getJRadioButton1());
		}
		return buttonGroup1;
	}
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this
		.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//		if(isImport){
	        this.setTitle("进口料进报关登记表");
//	        }
//	        else{
//	        this.setTitle("出口成品报关登记表");
//	        }
        this.setSize(new Dimension(834, 510));
        this.setContentPane(getJContentPane());
        initTable(new ArrayList());	
	}
	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			
		}
		super.setVisible(b);
	}
	protected void initUIComponents() {
		 if (isImport) {
			 System.out.println("111111true11111");
				this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
						ImpExpType.DIRECT_IMPORT).toString(), "料件进口"));
				this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
						ImpExpType.TRANSFER_FACTORY_IMPORT).toString(), "料件转厂"));
				this.cbbImpExpType.addItem(new ItemProperty(String
						.valueOf(ImpExpType.BACK_MATERIEL_EXPORT), "退料出口"));
			} else {System.out.println("22222222false222222");
				this.cbbImpExpType.addItem(new ItemProperty(String
						.valueOf(ImpExpType.DIRECT_EXPORT), "成品出口"));
				this.cbbImpExpType.addItem(new ItemProperty(String
						.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
				this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
						ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
				this.cbbImpExpType.addItem(new ItemProperty(String
						.valueOf(ImpExpType.REWORK_EXPORT), "返工复出"));

			}
	        CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpType);
			this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
					.getRender());
			this.cbbImpExpType.setSelectedIndex(-1);
			
			// 初始化客户commonBaseCodeAction
			List list = materialManageAction.findScmCocs(new Request(CommonVars
					.getCurrUser(), true));
			this.cbbCustomer.setModel(new DefaultComboBoxModel(list.toArray()));
			this.cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
					"code", "name", 100, 170));
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					this.cbbCustomer, "code", "name", 270);
			this.cbbCustomer.setSelectedItem(null);
	}
	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getJPanel2(), BorderLayout.NORTH);
			jContentPane.add(getJPanel3(), BorderLayout.EAST);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(672, 7, 60, 18));
			jLabel.setText("报关单状态");
			lbScmcoc = new JLabel();
			lbScmcoc.setBounds(new Rectangle(222, 7, 52, 18));
			lbScmcoc.setText("客户名称");
			lbNo = new JLabel();
			lbNo.setBounds(new Rectangle(8, 7, 55, 18));
			lbNo.setText("备案序号");
			lbType = new JLabel();
			lbType.setBounds(new Rectangle(471, 7, 70, 18));
			lbType.setText("进出口类型");
			lbZhi = new JLabel();
			lbZhi.setBounds(new Rectangle(446, 31, 19, 18));
			lbZhi.setText("至");
			lbProduct = new JLabel();
			lbProduct.setBounds(new Rectangle(7, 33, 54, 18));
			lbProduct.setText("商品名称");
			lbBeginTime = new JLabel();
			lbBeginTime.setText("报关单日期范围");
			lbBeginTime.setBounds(new Rectangle(222, 32, 91, 18));
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setPreferredSize(new Dimension(1, 60));
			jPanel2.add(lbBeginTime, null);
			jPanel2.add(lbProduct, null);
			jPanel2.add(getTfProduct(), null);
			jPanel2.add(getCbbBeginDate(), null);
			jPanel2.add(getCbbEndDate(), null);
			jPanel2.add(lbZhi, null);
			jPanel2.add(lbType, null);
			jPanel2.add(getBtnQuery(), null);
			jPanel2.add(getBtnPrint(), null);
			jPanel2.add(getBtnClose(), null);
			jPanel2.add(getCbbCommInfo(), null);
			jPanel2.add(lbNo, null);
			jPanel2.add(getCbbImpExpType(), null);
			jPanel2.add(lbScmcoc, null);
			jPanel2.add(getCbbCustomer(), null);
			jPanel2.add(getBtnProduct(), null);
			jPanel2.add(getCbbBillState(), null);
			jPanel2.add(jLabel, null);
		}
		return jPanel2;
	}
	private JComboBox getCbbCommInfo() {
		if (cbbCommInfo == null) {
			cbbCommInfo = new JComboBox();
			cbbCommInfo.setBounds(69, 5, 147, 22);
			cbbCommInfo.setEnabled(false);
		}
		return cbbCommInfo;
	}
	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.setPreferredSize(new Dimension(192, 0));
			jPanel3.add(getJPanel4(), BorderLayout.NORTH);
			jPanel3.add(getJPanel5(), BorderLayout.CENTER);
		}
		return jPanel3;
	}

	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(315, 30, 119, 22));
		}
		return cbbBeginDate;
	}
	
	/**
	 * This method initializes jList11	
	 * 	
	 * @return com.bestway.dzsc.client.dzscmanage.JDzscEmsPorHeadList	
	 */
	private JDzscEmsPorHeadList getJList11() {
		if (jList11 == null) {
			jList11 = new JDzscEmsPorHeadList();
			jList11.addMouseListener(new java.awt.event.MouseAdapter() 
			{
				public void mouseClicked(java.awt.event.MouseEvent e)
				{
					cbbBeginDate.setDate(findBeginDate());
					cbbEndDate.setDate(findEndDate());
					//控制控件可编辑状态
					if(jList11.getSelectedContracts().size()==1){
						cbbCommInfo.setEnabled(true);
						tfProduct.setEditable(false);
						btnProduct.setEnabled(false);
						tfProduct.setText("");
						
						DzscEmsPorHead contract = (DzscEmsPorHead)jList11.getSelectedContracts().get(0);
						cbbCommInfo.setModel(new DefaultComboBoxModel(contractStatAction
								.findCustomsDeclarationCommInfo(
										new Request(CommonVars.getCurrUser()), isImport,
										contract).toArray()));
						cbbCommInfo.setRenderer(CustomBaseRender.getInstance().getRender(
								"seqNum", "name", 50, 100));
						CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
								cbbCommInfo, "seqNum", "name");
						cbbCommInfo.setSelectedIndex(-1);
					}
					else if(jList11.getSelectedContracts().size()>1){
						if(cbbCommInfo.getSelectedItem()!=null){
							if(((TempDzscCustomsDeclarCommInfo)cbbCommInfo.getSelectedItem()).getName()!=null 
									||((TempDzscCustomsDeclarCommInfo)cbbCommInfo.getSelectedItem()).getName().equals("")){
								tfProduct.setText(((TempDzscCustomsDeclarCommInfo)cbbCommInfo.getSelectedItem()).getName());
								cbbCommInfo.setSelectedItem(null);
							}
							}
							cbbCommInfo.setEnabled(false);
							tfProduct.setEditable(true);
							btnProduct.setEnabled(true);
					}
					else{
						
					}
				}
			});
		}
		return jList11;
	}
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(470, 30, 123, 22));
		}
		return cbbEndDate;
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
	 * This method initializes tfProduct	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfProduct() {
		if (tfProduct == null) {
			tfProduct = new JTextField();
			tfProduct.setBounds(new Rectangle(68, 32, 127, 22));
			tfProduct.setEditable(false);
		}
		return tfProduct;
	}

	/**
	 * This method initializes btnQuery	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(new Rectangle(605, 30, 70, 30));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//客户名称
					String customer = "";
					//进出口类型
					String impExpType = "";
					//商品名称
					String ptName="";
					//备案序号
					Integer seqNum=null;
					//备案序号
					if (cbbCommInfo.getSelectedItem()!=null) {
						seqNum=((TempDzscCustomsDeclarCommInfo)cbbCommInfo.getSelectedItem()).getSeqNum();
					}
					//商品名称
					if (!tfProduct.getText().trim().equals("")) {
						ptName=tfProduct.getText().trim();
					}
					//客户名称
					if (cbbCustomer.getSelectedItem() != null) {
						customer = ((ScmCoc) cbbCustomer.getSelectedItem())
								.getId();
					}
					//进出口类型
					if (cbbImpExpType.getSelectedItem() != null) {
						impExpType = ((ItemProperty) cbbImpExpType
								.getSelectedItem()).getCode();
					}
					//报关单日期范围
					Date beginDate = cbbBeginDate.getDate();
					Date endDate = cbbEndDate.getDate();
					List<DzscImpMaterialStat> list = new ArrayList();
					
					//所选择的合同
					List<DzscEmsPorHead> contracts = jList11.getSelectedContracts();
					
					//报关单状态
					String billState = ((ItemProperty)cbbBillState.getSelectedItem()).getCode();
					
					list = contractStatAction.findMaterialImportListNew(
					new Request(CommonVars.getCurrUser()), isImport,
					seqNum,ptName, customer, impExpType, beginDate, endDate,
					contracts, billState);
					initTable(list);
				}
			});
		}
		return btnQuery;
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
						if(isImport){
							list.add(addColumn("原产国", "country.name", 100));//17
						}else{
							list.add(addColumn("最终目的国", "country.name", 100));//17	
						}
						list.add(addColumn("进出口类型",
								"baseCustomsDeclaration.impExpType", 100));//18
						list.add(addColumn("贸易方式",
								"baseCustomsDeclaration.tradeMode.name", 100));//19
						if(isImport){
							list.add(addColumn("供应商名称",
									"baseCustomsDeclaration.customer.name", 100));//20
						}else{
							list.add(addColumn("客户名称",
									"baseCustomsDeclaration.customer.name", 100));//20
						}
						list.add(addColumn("法定数量", "firstAmount", 100));//21
						list.add(addColumn("法定单位", "legalUnit.name", 100));//22
						list.add(addColumn("报关单单价", "commUnitPrice", 100));//23
						list.add(addColumn("毛重", "grossWeight", 100));//24
						list.add(addColumn("净重", "netWeight", 100));//25
						list.add(addColumn("总重量", "grossWeight", 100));//26
						list.add(addColumn("车牌号", "baseCustomsDeclaration.billOfLading", 100));//27
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
							if(reportControl.getDetailNote()!=null
									&&reportControl.getDetailNote().equals(new Boolean(true))){
								list.add(addColumn("表体备注", "detailNote", 100));//30
							}
						}
						
						list.add(addColumn("手册通关备案补充说明", "note", 120));
						return list;
					}
				});

		// 列表中显示小数位,默认5位
		Integer decimalLength = 5;
//		if (parameterSet != null
//				&& parameterSet.getReportDecimalLength() != null)
//			decimalLength = parameterSet.getReportDecimalLength();
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
		jTable.getColumnModel().getColumn(14).setCellRenderer(
				new DefaultTableCellRenderer(){
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "0";
						DzscParameterSet dirSet = dzscMessageAction
						.findDzscMessageDirSet(new Request(CommonVars
								.getCurrUser()));
						if(value != null) {
							str = value.toString();
						}
						this.setText(CommonUtils.formatDoubleByDigit(new Double(str),
								dirSet.getReportDecimalLength()));
						return this;
					}
				});

		// this.tableModel.setExcelFileName(this.contract.getEmsNo());

	}
	/**
	 * This method initializes btnPrint	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setBounds(new Rectangle(675, 30, 70, 30));
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null || tableModel.getList().size() < 1) {
						JOptionPane.showMessageDialog(
								DgDzscImpMaterialListNew.this, "没有数据可以打印", "提示",
								JOptionPane.OK_OPTION);
						return;
					}
//					if (contracts == null) {
//						return;
//					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = JDzscContractList.class
							.getResourceAsStream("report/ImpExpCommodityStatusNew.jasper");
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("isImport", new Boolean(true));
//					parameters.put("contractNo", contract.getIeContractNo());
//					parameters.put("emsNo", contract.getEmsNo());
//					parameters.put("beginDate",
//							contract.getBeginDate() == null ? "" : dateFormat
//									.format(contract.getBeginDate()));
//					parameters.put("effectiveDate",
//							contract.getEndDate() == null ? "" : dateFormat
//									.format(contract.getEndDate()));
					parameters.put("companyName", CommonVars.getCurrUser()
							.getCompany().getName());
					String title=getPrintTitle();
					parameters.put("reportTitle",title);
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
	 * This method initializes btnClose	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setBounds(new Rectangle(745, 30, 70, 30));
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setPreferredSize(new Dimension(1, 60));
			jPanel4.add(getJRadioButton(), new GridBagConstraints());
			jPanel4.add(getJRadioButton1(), new GridBagConstraints());
			jPanel4.add(getCbContractExe(), new GridBagConstraints());
		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.weightx = 1.0;
			jPanel5 = new JPanel();
			jPanel5.setLayout(new GridBagLayout());
			jPanel5.add(getJScrollPane1(), gridBagConstraints);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setText("全否");
			jRadioButton.setBounds(new Rectangle(34, 3, 49, 26));
			jRadioButton.setSelected(true);
			jRadioButton
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jRadioButton.isSelected()) {
						for (int i = 0; i < jList11.getModel()
								.getSize(); i++) {
							DzscEmsPorHead contract = (DzscEmsPorHead) jList11
									.getModel().getElementAt(i);
							contract.setSelected(false);
						}
						jList11.repaint();
						cbbBeginDate.setDate(findBeginDate());
						cbbEndDate.setDate(findEndDate());
					}
				}
			});
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setText("全选");
			jRadioButton1.setBounds(new Rectangle(91, 2, 49, 26));
			jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jRadioButton1.isSelected()) {
						for (int i = 0; i < jList11.getModel().getSize(); i++) {
							DzscEmsPorHead contract = (DzscEmsPorHead) jList11.getModel()
									.getElementAt(i);
							contract.setSelected(true);
						}
						jList11.repaint();
						cbbBeginDate.setDate(findBeginDate());
						cbbEndDate.setDate(findEndDate());
					}
				}
			});
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes cbContractExe	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbContractExe() {
		if (cbContractExe == null) {
			cbContractExe = new JCheckBox();
			cbContractExe.setBounds(new Rectangle(2, 33, 127, 21));
			cbContractExe.setText("正在执行的合同");
			cbContractExe.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(cbContractExe.isSelected()){
						jList11.showContractData(true, true);
					}else if(cbContractExe.isSelected() ){
						jList11.showContractData(true, false);
					}else if(!cbContractExe.isSelected() ){
						jList11.showContractData(false, true);
					}else{
						jList11.showContractData(false, false);
					}
				}
			});
		}
		return cbContractExe;
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
	 * This method initializes cbbImpExpType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(new Rectangle(545, 5, 119, 22));
		}
		return cbbImpExpType;
	}

	/**
	 * 找出合同中最靠前的日期
	 * 
	 * @return
	 */
	public Date findBeginDate()
	{
		List<Date> list = new ArrayList<Date>();
		Date date = null;
		for(int i = 0;i < jList11.getModel().getSize();i++)
		{
			DzscEmsPorHead contract = (DzscEmsPorHead) jList11.getModel().getElementAt(i);
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
	 * 找出合同中最靠后的日期
	 * 
	 * @return
	 */
	public Date findEndDate()
	{
		List<Date> list = new ArrayList<Date>();
		Date date = null;
		for(int i = 0;i < jList11.getModel().getSize();i++)
		{
			DzscEmsPorHead contract = (DzscEmsPorHead) jList11.getModel().getElementAt(i);
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
	 * This method initializes cbbCustomer	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbCustomer() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(new Rectangle(283, 5, 181, 21));
		}
		return cbbCustomer;
	}
	/**
	 * This method initializes btnProduct	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnProduct() {
		if (btnProduct == null) {
			btnProduct = new JButton();
			btnProduct.setBounds(new Rectangle(196, 32, 20, 22));
			btnProduct.setText("...");
			btnProduct.setEnabled(false);
			btnProduct
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String customer = "";
					String impExpType = "";
			
					//客户名称
					if (cbbCustomer.getSelectedItem() != null) {
						customer = ((ScmCoc) cbbCustomer.getSelectedItem())
								.getId();
					}
					//进出口类型
					if (cbbImpExpType.getSelectedItem() != null) {
						impExpType = ((ItemProperty) cbbImpExpType
								.getSelectedItem()).getCode();
					}
					//报关单日期范围
					Date beginDate = cbbBeginDate.getDate();
					Date endDate = cbbEndDate.getDate();
					List<DzscImpMaterialStat> lists = new ArrayList();
					
					//所选择的合同
					List<DzscEmsPorHead> contracts = jList11.getSelectedContracts();
					
					
					//按条件查询商品名称
					Object obj = TransferFactoryQuery.getInstance().findMaterialImportListNew(isImport(),contracts);
					if (obj != null) {
						tfProduct.setText((String) ((TempObject) obj)
								.getObject());
					}
				}
			});
		}
		return btnProduct;
	}
	/**
	 * This method initializes cbbBillState	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbBillState() {
		if (cbbBillState == null) {
			cbbBillState = new JComboBox();
			cbbBillState.setBounds(new Rectangle(744, 6, 71, 22));
			cbbBillState.addItem(new ItemProperty("0","全部"));
			cbbBillState.addItem(new ItemProperty("1","已生效"));
			cbbBillState.addItem(new ItemProperty("2","未生效"));
		}
		return cbbBillState;
	}
}  
