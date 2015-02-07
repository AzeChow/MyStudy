package com.bestway.client.selfcheck;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.invoice.entity.CustomsEnvelopCommodityInfoEntity;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSourceNew;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.client.transferfactory.TransferFactoryQuery;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.action.TransferFactoryManageAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgSupplierEnvelopDetailInfo extends JDialogBase {

	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JTableListModel tableModel = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel7 = null;
	private JComboBox cbbClientName = null;
	private JTextField tfContractNo = null;
	private JTextField tfWareName = null;
	private JCalendarComboBox cbbBeginDate = null;
	private JTextField tfApplyFormNo = null;
	private JTextField tfCustomsDeclarationCode = null;
	private JTextField tfWareSpec = null;
	private JCalendarComboBox cbbEndDate = null;
	private JButton btnQuery = null;
	private JButton btnPrint = null;
	private JButton btnClose = null;
	private JCheckBox cbAll = null;
	private JCheckBox cbOnContractExec = null;
	private JCheckBox cbCaseClosed = null;
	private JCheckBox cbCaseNotClosed = null;
	private JSplitPane jSplitPane = null;
	private JPanel pnTop = null;
	private JLabel jLabel8 = null;
	public MaterialManageAction materialManageAction = null;
	public TransferFactoryManageAction transferFactoryManageAction = null;
	private JButton btnContractNo = null;
	private JButton btnWareName = null;
	private JButton btnApplyFormNo = null;
	private JButton btnCustomsDeclarationCode = null;
	private JButton btnWareSpec = null;
	private boolean isImport;
	private JButton btnRelation = null;
	private JPopupMenu pmRelation = null;  //  @jve:decl-index=0:visual-constraint="837,215"
	private JMenuItem miRecvSendDetailInfo = null;  //  @jve:decl-index=0:visual-constraint="812,289"
	private JMenuItem miTransferDetailInfo = null;  //  @jve:decl-index=0:visual-constraint="812,321"
	private JMenuItem miBalanceAllInfo = null;  //  @jve:decl-index=0:visual-constraint="812,352"
	private JMenuItem miBalanceDetailInfo = null;  //  @jve:decl-index=0:visual-constraint="812,352"
	private JMenuItem miExeContractDetail = null;  //  @jve:decl-index=0:visual-constraint="813,382"
	
	//关联查询时传递 的参数
	private Date sBDate;
	private Date sEDate;
	private String sHsCode;
	private String sHsName;
	private String sHsSpec;
	private String sCustomerName;
	private ScmCoc sScmCoc;
	
	
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
	public DgSupplierEnvelopDetailInfo() {
		super();
		
		this.materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		
		this.transferFactoryManageAction = (TransferFactoryManageAction) CommonVars
		.getApplicationContext().getBean(
				"transferFactoryManageAction");
		
		initialize();
		initComponents();
		
	}

	private void initComponents() {
		List scmCoces = new ArrayList();
		scmCoces = this.getManufacturer();
		System.out.println("scmCoces.size()"+scmCoces.size());
		this.cbbClientName.setModel(new DefaultComboBoxModel(scmCoces
				.toArray()));
		this.cbbClientName.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name", 80, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbClientName, "code", "name", 250);
		this.cbbClientName.setSelectedItem(null);
		
		initTable(new ArrayList());
	}
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(861, 609));
        this.setContentPane(getJContentPane());
        this.setTitle("(供货商)关封明细表");
		jTable.setModel(initTable(new Vector()));	//初始化表格
		int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
		.getYearInt();
		Calendar beginClarendar = Calendar.getInstance();
		beginClarendar.set(Calendar.YEAR, year);
		beginClarendar.set(Calendar.MONTH, 0);
		beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
		beginClarendar.set(Calendar.HOUR_OF_DAY, 0);
		beginClarendar.set(Calendar.MINUTE, 0);
		beginClarendar.set(Calendar.SECOND, 0);
		this.cbbBeginDate.setDate(beginClarendar.getTime());
		this.cbbBeginDate.setCalendar(beginClarendar);
	}

	/**
	 * 获得是供应商的对象列表
	 */
	public List getManufacturer() {
		List list = this.materialManageAction.findScmCocsfacturerByXiaok(new Request(
				CommonVars.getCurrUser()));
		List manufacturerList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ScmCoc scmCoc = (ScmCoc) list.get(i);
			if (scmCoc.getIsManufacturer() != null
					&& scmCoc.getIsManufacturer().booleanValue()) {
				manufacturerList.add(scmCoc);
			}
		}
		return manufacturerList;
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
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
	 * 初始化表格
	 */
	private JTableListModel initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("关封日期", "beginAvailability", 100));
						list.add(addColumn("关封号", "customsEnvelopBillNo", 100));
						list.add(addColumn("客户名称", "scmCocName", 100));
						list.add(addColumn("合同号", "purchaseAndSaleContractNo", 100));
						list.add(addColumn("报关单号", "carryForwardApplyToCustomsBillNo", 100));
						list.add(addColumn("商品编码", "complexCode",100));
						list.add(addColumn("商品名称", "ptName",100));
						list.add(addColumn("商品规格", "ptSpec", 100));
						list.add(addColumn("商品单位", "unitName", 100));
						list.add(addColumn("关封数量", "ownerQuantity", 100));
						
						//list.add(addColumn("", "", 70));
						//list.add(addColumn("", "", 60));
						//list.add(addColumn("", "", 70));
						//list.add(addColumn("", "", 70));
						//list.add(addColumn("", "", 70));
						//list.add(addColumn("", "", 60));
						//list.add(addColumn("", "", 70));
						//list.add(addColumn("", "", 70));
						return list;
					}
				});
		return tableModel;
	}

	/**
	 * This method initializes cbbClientName	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbClientName() {
		if (cbbClientName == null) {
			cbbClientName = new JComboBox();
			cbbClientName.setBounds(new Rectangle(137, 9, 158, 20));
		}
		return cbbClientName;
	}

	/**
	 * This method initializes tfContractNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfContractNo() {
		if (tfContractNo == null) {
			tfContractNo = new JTextField();
			tfContractNo.setBounds(new Rectangle(137, 30, 138, 20));
		}
		return tfContractNo;
	}

	/**
	 * This method initializes tfWareName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfWareName() {
		if (tfWareName == null) {
			tfWareName = new JTextField();
			tfWareName.setBounds(new Rectangle(137, 50, 138, 20));
		}
		return tfWareName;
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
			cbbBeginDate.setBounds(new Rectangle(137, 70, 158, 20));
		}
		return cbbBeginDate;
	}
	
	
	/**
	 * This method initializes tfApplyFormNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfApplyFormNo() {
		if (tfApplyFormNo == null) {
			tfApplyFormNo = new JTextField();
			tfApplyFormNo.setBounds(new Rectangle(436, 10, 137, 20));
		}
		return tfApplyFormNo;
	}

	/**
	 * This method initializes tfCustomsDeclarationCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfCustomsDeclarationCode() {
		if (tfCustomsDeclarationCode == null) {
			tfCustomsDeclarationCode = new JTextField();
			tfCustomsDeclarationCode.setBounds(new Rectangle(436, 30, 137, 20));
		}
		return tfCustomsDeclarationCode;
	}

	/**
	 * This method initializes tfWareSpec	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfWareSpec() {
		if (tfWareSpec == null) {
			tfWareSpec = new JTextField();
			tfWareSpec.setBounds(new Rectangle(436, 50, 137, 20));
		}
		return tfWareSpec;
	}
	
	
	/**
	 * This method initializes cbbEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(436, 70, 158, 20));
		}
		return cbbEndDate;
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
			btnQuery.setBounds(new Rectangle(627, 21, 82, 28));
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					queryData();
				}
			}); 
				
		}
		return btnQuery;
	}
	
	/**
	 * 查询
	 */
	public void queryData(){
		//查询
		//组织查询条件 :关封管理
		List<Condition> conditions = new ArrayList<Condition>();
		//组织查询条件:转厂申请表
		List<Condition> conditions1= new ArrayList<Condition>();
		
		conditions.add(new Condition("and", null, "customsEnvelopBill.isImpExpGoods",
				"=", true, null));
		
		conditions1.add(new Condition("and", null, "fptAppHead.inOutFlag",
				"=", "1", null));
		//客户名称
		if(cbbClientName.getSelectedItem()!=null){
		ScmCoc s=(ScmCoc)cbbClientName.getSelectedItem();
		conditions.add(new Condition("and", null, "customsEnvelopBill.scmCoc.name",
					"=", s.getName(), null));
		conditions1.add(new Condition("and", null, "fptAppHead.scmCoc.name",
				"=", s.getName(), null));
		}
		 //关封号
		if (!tfApplyFormNo.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "customsEnvelopBill.customsEnvelopBillNo",
					"=", tfApplyFormNo.getText(), null));
			conditions1.add(new Condition("and", null, "fptAppHead.appNo",
					"=", tfApplyFormNo.getText(), null));
		}
		 //合同号
		if (!tfContractNo.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "customsEnvelopBill.purchaseAndSaleContractNo",
					"=", tfContractNo.getText(), null));
			conditions1.add(new Condition("and", null, "fptAppHead.contrNo",
					"=", tfContractNo.getText(), null));
		}
		 //报关单号
		if (!tfCustomsDeclarationCode.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "customsEnvelopBill.carryForwardApplyToCustomsBillNo",
					"=", tfCustomsDeclarationCode.getText(), null));
		}
		 //商品名称
		if (!tfWareName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "ptName",
					"=", tfWareName.getText(), null));
			conditions1.add(new Condition("and", null, "name",
					"=", tfWareName.getText(), null));
		}
		 //商品规格
		if (!tfWareSpec.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "ptSpec",
					"=", tfWareSpec.getText(), null));
			conditions1.add(new Condition("and", null, "spec",
					"=", tfWareSpec.getText(), null));
		}
		// 生效日期
		if (cbbBeginDate.getDate() != null) { // 开始日期
			conditions.add(new Condition("and", null,
					"customsEnvelopBill.beginAvailability", ">=",cbbBeginDate.getDate(), null));
			conditions1.add(new Condition("and", null,
					"fptAppHead.inDate", ">=",cbbBeginDate.getDate(), null));
		}
		if (cbbEndDate.getDate() != null) { // 结束日期
			conditions.add(new Condition("and", null,
					"customsEnvelopBill.beginAvailability", "<=",cbbEndDate.getDate(), null));
			conditions1.add(new Condition("and", null,
					"fptAppHead.inDate", "<=",cbbEndDate.getDate(), null));
		}
		if(!cbAll.isSelected()){
			if(cbCaseClosed.isSelected()&& !cbCaseNotClosed.isSelected()){
				conditions.add(new Condition("and", null,
						"customsEnvelopBill.isEndCase", "=",true, null));
			}
			else if(!cbCaseClosed.isSelected()&& cbCaseNotClosed.isSelected()){
				conditions.add(new Condition("and", null,
						"customsEnvelopBill.isEndCase", "=",false, null));
			}
			
		}
		if(cbOnContractExec.isSelected()){
			conditions.add(new Condition("and", null,
					"customsEnvelopBill.isAvailability", "=",true, null));
		}
		conditions1.add(new Condition("and", null,
				"fptAppHead.declareState", "=","3", null));
		new Find(conditions,conditions1).execute();
	
	}
	class Find extends SwingWorker{
		//查询条件
		List<Condition> conditions = null;
		List<Condition> conditions1 = null;
		public Find(List<Condition> conditions,List<Condition> conditions1) {
			this.conditions = conditions;
			this.conditions1 = conditions1;
		}

		@Override
		protected Object doInBackground() throws Exception {//后台计算
			//查询返回结果
			List<CustomsEnvelopCommodityInfoEntity> list = null;
			//查询
			CommonProgress.showProgressDialog(DgSupplierEnvelopDetailInfo.this);
			CommonProgress.setMessage("系统正在执行查询，请稍后...");
			String orderBy = "";
		//	list = casCheckAction.getCurrentBillDetailBom(new Request(CommonVars.getCurrUser()),casConvert,billDetail);
			
			list = transferFactoryManageAction.getEnvelopBillDetail(new Request(CommonVars.getCurrUser()),conditions,conditions1);
			return list;
		}

		@Override
		protected void done() {//更新视图
			List list=null;
			try {
				list = (List)this.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(list==null){
				list=new ArrayList();
			}
			CommonProgress.closeProgressDialog();
			initTable(list);
		}
	}

	/**
	 * This method initializes btnPrint	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setBounds(new Rectangle(722, 21, 82, 28));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						List list = tableModel.getList();
						CustomReportDataSourceNew ds = new CustomReportDataSourceNew(
								list);
						InputStream masterReportStream = DgMaterialThatDayBalance.class
								.getResourceAsStream("report/SupplierEnvelop.jasper");
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("title", getTitle());
						JasperPrint jasperPrint = JasperFillManager
								.fillReport(masterReportStream,
										parameters, ds);
						DgReportViewer viewer = new DgReportViewer(
								jasperPrint);
						viewer.setVisible(true);
					} catch (Exception ex) {
						ex.printStackTrace();
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
			btnClose.setText("关闭");
			btnClose.setBounds(new Rectangle(722, 60, 82, 28));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes cbAll	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbAll() {
		if (cbAll == null) {
			cbAll = new JCheckBox();
			cbAll.setText("全部");
			cbAll.setBounds(new Rectangle(349, 91, 60, 21));
		}
		return cbAll;
	}

	/**
	 * This method initializes cbOnContractExec	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbOnContractExec() {
		if (cbOnContractExec == null) {
			cbOnContractExec = new JCheckBox();
			cbOnContractExec.setText("在执行合同");
			cbOnContractExec.setBounds(new Rectangle(487, 91, 97, 21));
		}
		return cbOnContractExec;
	}

	/**
	 * This method initializes cbCaseClosed	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbCaseClosed() {
		if (cbCaseClosed == null) {
			cbCaseClosed = new JCheckBox();
			cbCaseClosed.setText("已结案");
			cbCaseClosed.setBounds(new Rectangle(79, 91, 69, 21));
		}
		return cbCaseClosed;
	}

	/**
	 * This method initializes cbCaseNotClosed	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbCaseNotClosed() {
		if (cbCaseNotClosed == null) {
			cbCaseNotClosed = new JCheckBox();
			cbCaseNotClosed.setText("未结案");
			cbCaseNotClosed.setBounds(new Rectangle(206, 91, 78, 21));
		}
		return cbCaseNotClosed;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(1);
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setTopComponent(getPnTop());
			jSplitPane.setDividerLocation(116);
			
		}
		return jSplitPane;
	}

	/**
	 * This method initializes pnTop	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnTop() {
		if (pnTop == null) {
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(325, 70, 17, 18));
			jLabel8.setText("至");
			pnTop = new JPanel();
			pnTop.setLayout(null);
			pnTop.setBorder(
					BorderFactory.createTitledBorder(null, 
													"折料选项", 
													TitledBorder.DEFAULT_JUSTIFICATION, 
													TitledBorder.DEFAULT_POSITION, 
													new Font("Dialog", Font.BOLD, 12), 
													Color.blue));
			jLabel7 = new JLabel();
			jLabel7.setText("报表日期");
			jLabel7.setBounds(new Rectangle(371, 70, 62, 18));
			jLabel6 = new JLabel();
			jLabel6.setText("商品规格");
			jLabel6.setBounds(new Rectangle(371, 50, 62, 18));
			jLabel5 = new JLabel();
			jLabel5.setText("报关单号");
			jLabel5.setBounds(new Rectangle(371, 30, 62, 18));
			jLabel4 = new JLabel();
			jLabel4.setText("关封号");
			jLabel4.setBounds(new Rectangle(371, 10, 62, 18));
			jLabel3 = new JLabel();
			jLabel3.setText("报表日期");
			jLabel3.setBounds(new Rectangle(74, 70, 62, 18));
			jLabel2 = new JLabel();
			jLabel2.setText("商品名称");
			jLabel2.setBounds(new Rectangle(74, 50, 62, 18));
			jLabel1 = new JLabel();
			jLabel1.setText("合同号");
			jLabel1.setBounds(new Rectangle(74, 30, 62, 18));
			jLabel = new JLabel();
			jLabel.setText("客户名称");
			jLabel.setBounds(new Rectangle(74, 10, 62, 18));
			pnTop.add(jLabel, null);
			pnTop.add(getCbbClientName(), null);
			pnTop.add(jLabel1, null);
			pnTop.add(getTfContractNo(), null);
			pnTop.add(jLabel2, null);
			pnTop.add(getTfWareName(), null);
			pnTop.add(jLabel3, null);
			pnTop.add(getCbbBeginDate(), null);
			pnTop.add(getCbCaseClosed(), null);
			pnTop.add(getCbCaseNotClosed(), null);
			pnTop.add(getCbAll(), null);
			pnTop.add(getCbOnContractExec(), null);
			pnTop.add(getCbbEndDate(), null);
			pnTop.add(jLabel7, null);
			pnTop.add(jLabel6, null);
			pnTop.add(jLabel5, null);
			pnTop.add(jLabel4, null);
			pnTop.add(getTfApplyFormNo(), null);
			pnTop.add(getTfCustomsDeclarationCode(), null);
			pnTop.add(getTfWareSpec(), null);
			pnTop.add(getBtnClose(), null);
			pnTop.add(getBtnPrint(), null);
			pnTop.add(getBtnQuery(), null);
			pnTop.add(jLabel8, null);
			pnTop.add(getBtnContractNo(), null);
			pnTop.add(getBtnWareName(), null);
			pnTop.add(getBtnApplyFormNo(), null);
			pnTop.add(getBtnCustomsDeclarationCode(), null);
			pnTop.add(getBtnWareSpec(), null);
			pnTop.add(getBtnRelation(), null);
		}
		return pnTop;
	}

	/**
	 * This method initializes btnContractNo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnContractNo() {
		if (btnContractNo == null) {
			btnContractNo = new JButton();
			btnContractNo.setBounds(new Rectangle(275, 30, 21, 19));
			btnContractNo.setText("...");
			btnContractNo
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object obj = TransferFactoryQuery.getInstance().findEnvelopBillByContractNoNew(isImport());
					if (obj != null) {
						CustomsEnvelopCommodityInfoEntity c = (CustomsEnvelopCommodityInfoEntity) obj;
						tfContractNo.setText(c
								.getPurchaseAndSaleContractNo());
					}
				}
			});
		}
		return btnContractNo;
	}

	/**
	 * This method initializes btnWareName	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnWareName() {
		if (btnWareName == null) {
			btnWareName = new JButton();
			btnWareName.setBounds(new Rectangle(275, 50, 21, 19));
			btnWareName.setText("...");
			btnWareName
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object obj = TransferFactoryQuery.getInstance().findAllEnvelopShangpinNew(isImport());
					if (obj != null) {
						CustomsEnvelopCommodityInfoEntity c = (CustomsEnvelopCommodityInfoEntity) obj;
						tfWareName.setText(c
								.getPtName());
					}
				}
			});
		}
		return btnWareName;
	}

	/**
	 * This method initializes btnApplyFormNo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnApplyFormNo() {
		if (btnApplyFormNo == null) {
			btnApplyFormNo = new JButton();
			btnApplyFormNo.setBounds(new Rectangle(573, 10, 21, 20));
			btnApplyFormNo.setText("...");
			btnApplyFormNo
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object obj = TransferFactoryQuery.getInstance().findAllEnvelopBillNew(isImport());
					if (obj != null) {
						CustomsEnvelopCommodityInfoEntity c = (CustomsEnvelopCommodityInfoEntity) obj;
						tfApplyFormNo.setText(c
								.getCustomsEnvelopBillNo());
					}
				}
			});
		}
		return btnApplyFormNo;
	}

	/**
	 * This method initializes btnCustomsDeclarationCode	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCustomsDeclarationCode() {
		if (btnCustomsDeclarationCode == null) {
			btnCustomsDeclarationCode = new JButton();
			btnCustomsDeclarationCode.setBounds(new Rectangle(573, 31, 21, 19));
			btnCustomsDeclarationCode.setText("...");
			btnCustomsDeclarationCode
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object obj = TransferFactoryQuery.getInstance().findAllEnvelopBillByBillNoNew(isImport());
					if (obj != null) {
						CustomsEnvelopCommodityInfoEntity c = (CustomsEnvelopCommodityInfoEntity) obj;
						tfCustomsDeclarationCode.setText(c
								.getCarryForwardApplyToCustomsBillNo());
					}
				}
			});
		}
		return btnCustomsDeclarationCode;
	}

	/**
	 * This method initializes btnWareSpec	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnWareSpec() {
		if (btnWareSpec == null) {
			btnWareSpec = new JButton();
			btnWareSpec.setBounds(new Rectangle(573, 51, 21, 18));
			btnWareSpec.setText("...");
			btnWareSpec
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object obj = TransferFactoryQuery.getInstance().findAllEnvelopSpec(isImport());
					if (obj != null) {
						CustomsEnvelopCommodityInfoEntity c = (CustomsEnvelopCommodityInfoEntity) obj;
						tfWareSpec.setText(c
								.getPtSpec());
					}
				}
			});
		}
		return btnWareSpec;
	}

	/**
	 * 关联查询时的参数注入
	 * @param startDate
	 * @param endDate
	 * @param hsCode
	 * @param hsName
	 * @param hsSpec
	 * @param ptNo
	 * @param ptName
	 * @param ptSpec
	 * @author wss
	 */
	public void setQueryCondition(Date startDate,Date endDate,String hsCode,
			String hsName,String hsSpec,ScmCoc scmCoc){
		//如果结束日期不为空
		if(endDate != null){
			this.cbbEndDate.setDate(endDate);
			if(startDate != null){
				this.cbbBeginDate.setDate(startDate);
			}else{
				this.cbbBeginDate.setDate(CommonVars.getEndDate(new Date(endDate.getYear(),0,1)));			}
		}

		this.tfWareName.setText(hsName);
		this.tfWareSpec.setText(hsSpec);
		this.cbbClientName.setSelectedItem(scmCoc);

	}

	/**
	 * This method initializes btnRelation	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRelation() {
		if (btnRelation == null) {
			btnRelation = new JButton();
			btnRelation.setBounds(new Rectangle(627, 60, 82, 28));
			btnRelation.setText("关联报表");
			btnRelation.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgSupplierEnvelopDetailInfo.this,
								"请选择你要查看的资料", "确认", 1);
						return;
					}
					CustomsEnvelopCommodityInfoEntity c = (CustomsEnvelopCommodityInfoEntity)tableModel.getCurrentRow();
					
					sBDate = cbbBeginDate.getDate();
					sEDate = cbbEndDate.getDate();
					sHsCode = c.getComplexCode();
					sHsName = c.getPtName();
					sHsSpec = c.getPtSpec();
					sCustomerName = c.getScmCocName();
					sScmCoc = c.getScmCoc();
					
					Component comp = (Component) e.getSource();
					getPmRelation().show(comp, 0, comp.getHeight());
				}
			});
		}
		return btnRelation;
	}
	
	/**
	 * This method initializes pmRelation	
	 * 	
	 * @return javax.swing.JPopupMenu	
	 */
	private JPopupMenu getPmRelation() {
		if (pmRelation == null) {
			pmRelation = new JPopupMenu();

			//收送货明细
			pmRelation.add(getMiRecvSendDetailInfo());
			
			//转厂明细
			pmRelation.add(getMiTransferDetailInfo());
			
			//结转差额总表
			pmRelation.add(getMiBalanceAllInfo());
			
			//结转差额分表
			pmRelation.add(getMiBalanceDetailInfo());
			
			//合同执行情况
			pmRelation.add(getMiExeContractDetail());			
		}
		return pmRelation;
	}
	
	
	/**
	 * This method initializes miRecvSendDetailInfo	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiRecvSendDetailInfo() {
		if (miRecvSendDetailInfo == null) {
			miRecvSendDetailInfo = new JMenuItem();
			miRecvSendDetailInfo.setText("收/送货明细");
			miRecvSendDetailInfo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String type = MaterielType.MATERIEL;
					String title = "收货明细表";
//					if(!isM){
//						type = MaterielType.FINISHED_PRODUCT;
//						title = "送货明细表";
//					}
					DgRecvSendDetailInfo dg = new DgRecvSendDetailInfo();
					dg.setMaterielType(type);
					dg.setTitle(title);
					dg.setQueryCondition(sBDate, sEDate, sHsCode, sHsName, sHsSpec, sScmCoc);
					
					dg.queryData();
					
					dg.setVisible(true);
				}
			});
		}
		return miRecvSendDetailInfo;
	}
	/**
	 * This method initializes miTransferDetailInfo	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiTransferDetailInfo() {
		if (miTransferDetailInfo == null) {
			miTransferDetailInfo = new JMenuItem();
			miTransferDetailInfo.setText("转厂明细");
			miTransferDetailInfo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					boolean isImp = true;
//					if(!isM){
//						isImp = false;
//					}
					
					DgTransferDetailInfo dg = new DgTransferDetailInfo();
					dg.setIsImport(isImp);
					dg.setTitle("结转明细表");
					
					dg.setVisible(true);				
					
				}
			});
		}
		return miTransferDetailInfo;
	}
	
	
	/**
	 * This method initializes miBalanceAllInfo	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiBalanceAllInfo() {
		if (miBalanceAllInfo == null) {
			miBalanceAllInfo = new JMenuItem();
			miBalanceAllInfo.setText("结转差额总表");
			miBalanceAllInfo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgSupplierBalanceAllInfo dg = new DgSupplierBalanceAllInfo();
					dg.setTitle("结转差额总表");
					dg.setIsM(true);
					
					dg.setQueryCondition(sEDate, sHsName, sHsSpec,sScmCoc);
					dg.queryData();
					
					dg.setVisible(true);
				}
			});
		}
		return miBalanceAllInfo;
	}
	
	
	/**
	 * This method initializes miBalanceDetailInfo	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiBalanceDetailInfo() {
		if (miBalanceDetailInfo == null) {
			miBalanceDetailInfo = new JMenuItem();
			miBalanceDetailInfo.setText("结转差额总表");
			miBalanceDetailInfo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgSupplierBalanceDetailInfo dg = new DgSupplierBalanceDetailInfo();
					dg.setTitle("结转差额分表");
					dg.setIsM(true);
					
					dg.setQueryCondition(sEDate, sHsName, sHsSpec);
					dg.queryData();
					
					dg.setVisible(true);
				}
			});
		}
		return miBalanceDetailInfo;
	}
	/**
	 * This method initializes miExeContractDetail	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiExeContractDetail() {
		if (miExeContractDetail == null) {
			miExeContractDetail = new JMenuItem();
			miExeContractDetail.setText("合同执行情况");
			miExeContractDetail.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmContractExecStat dg = new FmContractExecStat();
//					ShowFormControl.showForm(dg);
					dg.setVisible(true);				
				}
			});
		}
		return miExeContractDetail;
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
