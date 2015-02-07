package com.bestway.client.selfcheck;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.action.CasCheckAction;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSourceNew;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.footer.TableFooterType;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author wss
 * 自我核查--转厂分析--收/送货明细
 * 
 */
public class DgRecvSendDetailInfo extends JDialogBase {

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JButton btnReloadSearch = null;

	private JButton btnClose = null;

	private JTable jTable = null;

	private JFooterScrollPane jScrollPane = null;

	private JTableListModel tableModel = null;

	private JPanel jContentPane = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbEndDate = null;

	/**
	 * 料件管理操作接口
	 */
	private MaterialManageAction materialManageAction = null;

	/**
	 * 海关帐自我核查 远程服务接口
	 */
	private CasCheckAction casCheckAction = null;

	/**
	 * 物料类型
	 */
	private String materielType = null; // @jve:decl-index=0:

	public String getMaterielType() {
		return materielType;
	}

	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgRecvSendDetailInfo() {
		super(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		/**
		 * 初始化料件管理操作接口
		 */
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		
		/**
		 * 初始化海关帐自我核查 远程服务接口
		 */
		casCheckAction = (CasCheckAction) CommonVars.getApplicationContext()
				.getBean("casCheckAction");
		initialize();
		initUI();
		initTable(new Vector());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(859, 558);
		this.setTitle("收/送货明细");
		this.setContentPane(getJContentPane());
	}

	/**
	 * 初始化UI控件（供应商选择）
	 * hwy 2012-9-4
	 */
	private void initUI() {
		// 供应商还是客户
		List lsScmCoc = new ArrayList();
		if (MaterielType.MATERIEL.equals(getMaterielType())) {
			jLabel2.setText("客     户");
			lsScmCoc = getCustomer();// 客户
		} else {
			jLabel2.setText("供应商");
			lsScmCoc = getManufacturer();// 供应商
		}
		
		
		
		
		

		this.cbbScmCoc.setModel(new DefaultComboBoxModel(lsScmCoc.toArray()));
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 50, 200));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbScmCoc, "code", "name");
		this.cbbScmCoc.setUI(new CustomBaseComboBoxUI(250));
		this.cbbScmCoc.setSelectedItem(null);

		// 开始结束日期
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

		Calendar endClarendar = Calendar.getInstance();
		endClarendar.set(Calendar.YEAR, year);
		endClarendar.set(Calendar.MONTH, 11);
		endClarendar.set(Calendar.DAY_OF_MONTH, 31);
		beginClarendar.set(Calendar.HOUR_OF_DAY, 23);
		beginClarendar.set(Calendar.MINUTE, 59);
		beginClarendar.set(Calendar.SECOND, 59);
		this.cbbEndDate.setDate(endClarendar.getTime());
		this.cbbEndDate.setCalendar(endClarendar);
	}

	/**
	 * 获得是客户的对象列表
	 */
	private List getCustomer() {
		List list = this.materialManageAction.findScmCocs(new Request(
				CommonVars.getCurrUser(), true));
		List customerList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ScmCoc scmCoc = (ScmCoc) list.get(i);
			if (scmCoc.getIsCustomer() != null
					&& scmCoc.getIsCustomer().booleanValue()) {
				customerList.add(scmCoc);
			}
		}
		return customerList;
	}

	/**
	 * 获得是供应商的对象列表
	 */
	private List getManufacturer() {
		List list = this.materialManageAction.findScmCocs(new Request(
				CommonVars.getCurrUser(), true));
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
	 * This method initializes pn
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(400, 94, 52, 25));
			jLabel6.setText("合同规格");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(400, 68, 52, 25));
			jLabel5.setText("合同名称");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(137, 94, 52, 25));
			jLabel4.setText("内部规格");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(137, 68, 52, 25));
			jLabel3.setText("内部名称");
			jLabel2 = new JLabel();
			jLabel2.setText("供应商");
			jLabel2.setBounds(new Rectangle(136, 15, 52, 25));
			jLabel1 = new JLabel();
			jLabel1.setText("结束日期");
			jLabel1.setBounds(new Rectangle(401, 41, 52, 25));
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBorder(BorderFactory.createTitledBorder(null, "过滤条件",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jLabel.setText("开始日期");
			jLabel.setBounds(new Rectangle(137, 41, 52, 25));
			jPanel.add(jLabel2, null);
			jPanel.add(getCbbScmCoc(), null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getBtnSearch(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getBtnClose(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(jLabel6, null);
			jPanel.add(getTfInnerName(), null);
			jPanel.add(getTfInnerSpec(), null);
			jPanel.add(getTfContractName(), null);
			jPanel.add(getTfContractSpec(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton11(), null);
			jPanel.add(getJButton12(), null);
			jPanel.add(getJButton13(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes endDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setPreferredSize(new java.awt.Dimension(85, 25));
			cbbBeginDate.setBounds(new Rectangle(196, 41, 150, 25));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnReloadSearch == null) {
			btnReloadSearch = new JButton();
			btnReloadSearch.setText("查询");
			btnReloadSearch.setBounds(new Rectangle(650, 26, 60, 25));
			btnReloadSearch.setPreferredSize(new java.awt.Dimension(60, 26));
			btnReloadSearch
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							queryData();
						}
					});
		}
		return btnReloadSearch;
	}

	/**
	 * 查询
	 */
	public void queryData(){

		// 组织查询条件
		List<Condition> conditions = new ArrayList<Condition>();
		// 生效的单据
		conditions.add(new Condition("and", null,
				"billMaster.isValid", "=",
				new Boolean(true), null));
		// 开始日期
		if (cbbBeginDate.getDate() != null) {
			conditions.add(new Condition("and", null,
					"billMaster.validDate", ">=",
					cbbBeginDate.getDate(), null));
		}
		// 结束日期
		if (cbbEndDate.getDate() != null) {
			conditions.add(new Condition("and", null,
					"billMaster.validDate", "<=",
					cbbEndDate.getDate(), null));
		}
		// 供应商或者客户
		if (cbbScmCoc.getSelectedItem() != null) {
			ScmCoc sc = (ScmCoc) cbbScmCoc
					.getSelectedItem();
			conditions.add(new Condition("and", null,
					"billMaster.scmCoc.id", "=",
					sc.getId(), null));
		}
		// 内部名称
		if (!tfInnerName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null,
					"ptName", "=", tfInnerName.getText()
							.trim(), null));
		}
		// 内部规格
		if (!tfInnerSpec.getText().trim().equals("")) {
			conditions.add(new Condition("and", null,
					"ptSpec", "=", tfInnerSpec.getText()
							.trim(), null));
		}
		// 海关名称
		if (!tfContractName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null,
					"hsName", "=", tfContractName.getText()
							.trim(), null));
		}
		// 海关规格
		if (!tfContractSpec.getText().trim().equals("")) {
			conditions.add(new Condition("and", null,
					"hsSpec", "=", tfContractSpec.getText()
							.trim(), null));
		}

		String billDetail = BillUtil
				.getBillDetailTableNameByMaterielType(getMaterielType());
		// 执行查询线程
		new Find(conditions, getMaterielType(), billDetail)
				.execute();
	
	}
	class Find extends SwingWorker {
		// 查询条件
		List<Condition> conditions = null;
		String materielType = null;
		String billDetail = null;

		public Find(List<Condition> conditions, String materielType,
				String billDetail) {
			this.conditions = conditions;
			this.materielType = materielType;
			this.billDetail = billDetail;
		}

		@Override
		protected Object doInBackground() throws Exception {// 后台计算
			// 查询返回结果
			List list = null;
			// 查询
			CommonProgress.showProgressDialog(DgRecvSendDetailInfo.this);
			CommonProgress.setMessage("系统正在执行查询，请稍后...");
			String orderBy = "";
			list = casCheckAction.getRecvOrSendDetail(new Request(CommonVars
					.getCurrUser()), conditions, getMaterielType(), billDetail);
			System.out.println("结果：" + list.size());
			return list;
		}

		@Override
		protected void done() {// 更新视图
			List list = null;
			try {
				list = (List) this.get();
				System.out.println("wss收货送货明细表测试更新视图中list大小为" + list.size());
				System.out.println("值为" + list.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (list == null) {
				list = new ArrayList();
			}
			CommonProgress.closeProgressDialog();
			initTable(list);
		}
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setBounds(new Rectangle(650, 93, 60, 25));
			btnClose.setPreferredSize(new java.awt.Dimension(60, 25));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgRecvSendDetailInfo.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new GroupableHeaderTable();
		}
		return jTable;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JFooterScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes pnContent
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
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setPreferredSize(new java.awt.Dimension(85, 25));
			cbbEndDate.setBounds(new Rectangle(457, 41, 150, 25));
		}
		return cbbEndDate;
	}

	private void initTable(final List list) {
		JTableListModelAdapter adapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("日期", "validDate", 100));// 1
				list.add(addColumn("单据号", "billNo", 180));// 2
				list.add(addColumn("工厂料号", "ptPart", 100));// 3
				list.add(addColumn("工厂品名", "ptName", 100));// 4
				list.add(addColumn("工厂规格", "ptSpec", 120));// 5
				list.add(addColumn("工厂单位", "ptUnit.name", 100));// 6
				list.add(addColumn("采购业务订单号", "poSoBillNo", 180));// 7
				list.add(addColumn("工厂数量", "ptRecvAmount", 180));// 8
				list.add(addColumn("报关数量", "hsRecvAmount", 100));// 9
				list.add(addColumn("工厂数量", "ptBackAmount", 100));// 10
				list.add(addColumn("报关数量", "hsBackAmount", 100));// 11
				list.add(addColumn("商品编码", "complex.code", 100));// 12
				list.add(addColumn("报关名称", "hsName", 100));// 13
				list.add(addColumn("报关单位", "hsUnit.name", 100));// 14
				
				list.add(addColumn("关封号", "envelopNo", 200));// 15
				list.add(addColumn("对应报关单号", "customNo", 200));// 16


				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, adapter,
				ListSelectionModel.SINGLE_SELECTION);
		
		//添加合计功能
		tableModel.clearFooterTypeInfo();
		tableModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		tableModel.addFooterTypeInfo(new TableFooterType(8,
				TableFooterType.SUM, ""));
		tableModel.addFooterTypeInfo(new TableFooterType(9,
				TableFooterType.SUM, ""));
		tableModel.addFooterTypeInfo(new TableFooterType(10,
				TableFooterType.SUM, ""));
		tableModel.addFooterTypeInfo(new TableFooterType(11,
				TableFooterType.SUM, ""));
		
		TableColumnModel cm = jTable.getColumnModel();
		ColumnGroup gOriginal = new ColumnGroup("摘要");
		gOriginal.add(cm.getColumn(1));
		gOriginal.add(cm.getColumn(2));
		gOriginal.add(cm.getColumn(3));
		gOriginal.add(cm.getColumn(4));
		gOriginal.add(cm.getColumn(5));
		gOriginal.add(cm.getColumn(6));
		gOriginal.add(cm.getColumn(7));
		ColumnGroup gSend = new ColumnGroup(
				MaterielType.MATERIEL == getMaterielType() ? "收货" : "送货");
		gSend.add(cm.getColumn(8));
		gSend.add(cm.getColumn(9));
		ColumnGroup gBack = new ColumnGroup("退货");
		gBack.add(cm.getColumn(10));
		gBack.add(cm.getColumn(11));
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(gOriginal);
		header.addColumnGroup(gSend);
		header.addColumnGroup(gBack);
		List<JTableListColumn> xs = tableModel.getColumns();
		xs.get(8).setFractionCount(2);
		xs.get(10).setFractionCount(2);
		jTable.getColumnModel().getColumn(8).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(9).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(10).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(11).setCellRenderer(
				new tableCellRender());
	}

	class tableCellRender extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			if (value == null) {
				this.setText("0.00");
			} else {
				if (value.equals(""))
					this.setText("0.00");
			}
			return this;
		}
	}

	private JLabel jLabel2 = null;

	private JComboBox cbbScmCoc = null;

	// private ButtonGroup buttonGroup = null; //
	// @jve:decl-index=0:visual-constraint="792,76"

	private JButton jButton = null;

	private JSplitPane jSplitPane = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JTextField tfInnerName = null;

	private JTextField tfInnerSpec = null;

	private JTextField tfContractName = null;

	private JTextField tfContractSpec = null;

	private JButton jButton1 = null;

	private JButton jButton11 = null;

	private JButton jButton12 = null;

	private JButton jButton13 = null;

	/**
	 * This method initializes cbbScmCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setPreferredSize(new java.awt.Dimension(115, 25));
			cbbScmCoc.setBounds(new Rectangle(196, 11, 223, 25));
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	/*
	 * private ButtonGroup getButtonGroup() { if (buttonGroup == null) {
	 * buttonGroup = new ButtonGroup(); buttonGroup.add(this.getJRadioButton());
	 * buttonGroup.add(this.getJRadioButton1()); } return buttonGroup; }
	 */

	/**
	 * This method initializes btnCustomSort
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("打印");
			jButton.setBounds(new Rectangle(650, 59, 60, 25));
			jButton.setPreferredSize(new java.awt.Dimension(60, 25));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printData();
				}
			});
		}
		return jButton;
	}

	public void printData() {
		try {
			List list = tableModel.getList();
			CustomReportDataSourceNew ds = new CustomReportDataSourceNew(list);

			InputStream masterReportStream = DgMaterialDifferent.class
					.getResourceAsStream("report/RecvSendDetailInfo.jasper");
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("title", getTitle());
			// parameters.put("type", getMaterielType());
			parameters.put("recvOrSend", MaterielType.MATERIEL
					.equals(getMaterielType()) ? "收货" : "送货");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(2);
			jSplitPane.setDividerLocation(130);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setEnabled(false);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes tfInnerName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInnerName() {
		if (tfInnerName == null) {
			tfInnerName = new JTextField();
			tfInnerName.setBounds(new Rectangle(196, 68, 130, 25));
		}
		return tfInnerName;
	}

	/**
	 * This method initializes tfInnerSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInnerSpec() {
		if (tfInnerSpec == null) {
			tfInnerSpec = new JTextField();
			tfInnerSpec.setBounds(new Rectangle(196, 94, 130, 25));
		}
		return tfInnerSpec;
	}

	/**
	 * This method initializes tfContractName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContractName() {
		if (tfContractName == null) {
			tfContractName = new JTextField();
			tfContractName.setBounds(new Rectangle(457, 68, 130, 25));
		}
		return tfContractName;
	}

	/**
	 * This method initializes tfContractSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContractSpec() {
		if (tfContractSpec == null) {
			tfContractSpec = new JTextField();
			tfContractSpec.setBounds(new Rectangle(457, 94, 130, 25));
		}
		return tfContractSpec;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(326, 68, 20, 25));
			jButton1.setText("...");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = getMaterielType();
					Object object = CasQuery.getInstance()
							.findPtNameFromStatCusNameRelationMt(materialType);
					if (object != null) {
						tfInnerName.setText((String) ((TempObject) object)
								.getObject());
					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton11
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton11() {
		if (jButton11 == null) {
			jButton11 = new JButton();
			jButton11.setBounds(new Rectangle(326, 94, 20, 25));
			jButton11.setText("...");
			jButton11.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = getMaterielType();
					Object object = CasQuery.getInstance()
							.findPtSpecFromStatCusNameRelationMt(materialType,
									tfInnerName.getText());
					if (object != null) {
						tfInnerSpec.setText((String) ((TempObject) object)
								.getObject());
					}
				}
			});
		}
		return jButton11;
	}

	/**
	 * This method initializes jButton12
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton12() {
		if (jButton12 == null) {
			jButton12 = new JButton();
			jButton12.setBounds(new Rectangle(587, 68, 20, 25));
			jButton12.setText("...");
			jButton12.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = getMaterielType();
					Object object = CasQuery.getInstance()
							.findHsNameFromStatCusNameRelationHsn(materialType);
					if (object != null) {
						tfContractName.setText((String) ((TempObject) object)
								.getObject());
						tfContractSpec.setText((String) ((TempObject) object)
								.getObject1());
					}
				}
			});
		}
		return jButton12;
	}

	/**
	 * This method initializes jButton13
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton13() {
		if (jButton13 == null) {
			jButton13 = new JButton();
			jButton13.setBounds(new Rectangle(587, 94, 20, 25));
			jButton13.setText("...");
			jButton13.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = getMaterielType();
					Object object = CasQuery.getInstance()
							.findHsSpecFromStatCusNameRelationHsn(materialType,
									tfContractName.getText());
					if (object != null) {
						tfContractSpec.setText((String) ((TempObject) object)
								.getObject());
					}
				}
			});
		}
		return jButton13;
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

		this.tfContractName.setText(hsName);//报关名称
		this.tfContractSpec.setText(hsSpec);//报关规格
		this.cbbScmCoc.setSelectedItem(scmCoc);//客户

	}
} // @jve:decl-index=0:visual-constraint="10,10"
