/*
 * Created on 2004-9-20
 *
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.outsource;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.entity.StatCusNameRelationMt;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgOutSourceDetailQuery extends JDialogBase {

	private javax.swing.JPanel		jContentPane			= null;
	private JPanel					jPanel					= null;
	private JSplitPane				jSplitPane				= null;
	private JPanel					jPanel1					= null;
	private JPanel					jPanel2					= null;
	private JTable					jTable					= null;
	private JScrollPane				jScrollPane				= null;
	private JLabel					jLabel					= null;
	private JCalendarComboBox		cbbBeginDate			= null;
	private JCalendarComboBox		cbbEndDate				= null;
	private JComboBox				cbbBillType				= null;
	private JList					jList					= null;
	private JScrollPane				jScrollPane1			= null;
	private JComboBox				cbbScmCoc				= null;
	private JButton					btnSearch				= null;
	private JButton					btnPrint				= null;
	private JButton					btnExit					= null;
	private MaterialManageAction materialManageAction = null;
	private CasAction				casAction				= null;
	private JLabel					jLabel6					= null;
	private JLabel					jLabel5					= null;
	private JLabel					jLabel7					= null;
	private String					materielType			= null;
	private String					reportTilte				= null;
	private List					result					= null;
	private JTextField				tfHsName				= null;
	private JLabel					jLabel10				= null;
	private JTextField				tfHsSpec				= null;
	private JTextField				tfPtName				= null;
	private JLabel					jLabel11				= null;
	private JTextField				tfPtSpec				= null;
	private JButton					btnPtSpec				= null;
	private JButton					btnHsName				= null;
	private JButton					btnHsSpec				= null;
	private JButton					btnPtName				= null;
	private JLabel					jLabel12				= null;
	private JTextField				tfPtNo					= null;
	private JButton					btnPtNo					= null;
	private JLabel					jLabel13				= null;
	private JTextField				tfEndPtNo				= null;
	private JButton					btnEndPtNo				= null;
	private JTableListModel			tableModel				= null;

	/**
	 * This is the default constructor
	 */
	public DgOutSourceDetailQuery() {
		super(false);
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initialize();
	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {
			initTopName();
			initUI();
			initTable(new ArrayList());
		}
		super.setVisible(isFlag);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		this.setTitle("委外明细查询");
		this.setSize(733, 541);
		this.setContentPane(getJContentPane());

	}

	private void initUI() {
		String materielType = this.getMaterielType();
		List list = casAction.findBillMaterielType(new Request(CommonVars
				.getCurrUser()), materielType);
		List<BillType> tempList = new ArrayList<BillType>();

		setBillTypeList(materielType, list, tempList);

		this.cbbBillType.setModel(new DefaultComboBoxModel(tempList.toArray()));
		this.cbbBillType.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbBillType, "code", "name");
		this.cbbBillType.setSelectedIndex(-1);
		// 初始化客户
		List listScmCoc = materialManageAction.findScmCocs(new Request(
				CommonVars.getCurrUser()));
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(listScmCoc.toArray()));
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 50, 200));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbScmCoc, "code", "name");
		this.cbbScmCoc.setUI(new CustomBaseComboBoxUI(250));
		this.cbbScmCoc.setSelectedIndex(-1);
		// 初始化仓库
		Vector<CheckableItem> vector = new Vector<CheckableItem>();
		List listWareSet = materialManageAction.findWareSet(new Request(
				CommonVars.getCurrUser()));
		for (int i = 0; i < listWareSet.size(); i++) {
			WareSet obj = (WareSet) listWareSet.get(i);
			CheckableItem item = new CheckableItem(obj.getCode(), obj.getName());
			vector.add(item);
		}
		this.jList.setListData(vector);

		this.cbbBeginDate.setDate(java.sql.Date
				.valueOf(com.bestway.bcus.client.cas.parameter.CasCommonVars
						.getYear()
						+ "-01-01"));
		this.cbbEndDate.setDate(java.sql.Date
				.valueOf(com.bestway.bcus.client.cas.parameter.CasCommonVars
						.getYear()
						+ "-12-31"));
	}

	/**
	 * 设置显示的BillType列表
	 * @param materielType
	 * @param list
	 * @param tempList
	 */
	private void setBillTypeList(String materielType, List list, List<BillType> tempList) {
		if (materielType.equalsIgnoreCase(MaterielType.MATERIEL)) {
			for (int i = 0, n = list.size(); i < n; i++) {
				BillType billType = (BillType) list.get(i);
				int billCode = Integer.valueOf(billType.getCode()).intValue();
				// 1014 委外期初单
				// 1012 外发加工退回料件
				// 1013 外发加工返回料件
				// 1110 外发加工出库
				if (billCode == 1110 || billCode == 1012 || billCode == 1013
						|| billCode == 1014) {
					tempList.add(billType);
				}
			}
		} else if (materielType
				.equalsIgnoreCase(MaterielType.SEMI_FINISHED_PRODUCT)) {
			for (int i = 0, n = list.size(); i < n; i++) {
				BillType billType = (BillType) list.get(i);
				int billCode = Integer.valueOf(billType.getCode()).intValue();
				// 4105 外发加工领料
				// 4104 外发加工返修半成品
				// 4003 委外加工入库
				// 4103 委外加工出库
				if (billCode == 4105 || billCode == 4104 || billCode == 4003
						|| billCode == 4103) {
					tempList.add(billType);
				}
			}
		}
	}

	class CheckableItem {
		private String	code;
		private String	name;
		private boolean	isSelected;

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
			return name + " (" + code + ")";
		}
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("日期", "billMaster.validDate", 80));
						list.add(addColumn("单据类型", "billMaster.billType.name",
								70));
						list.add(addColumn("商品序号", "seqNum", 60));

						list.add(addColumn("工厂料号", "ptPart", 60));
						list.add(addColumn("商品名称", "ptName", 100));
						list.add(addColumn("型号规格", "ptSpec", 100));

						list.add(addColumn("数量", "ptAmount", 60));
						list.add(addColumn("单位", "ptUnit.name", 60));
						list.add(addColumn("折算报关数量", "hsAmount", 80));

						list.add(addColumn("仓库名称", "wareSet.name", 80));
						list
								.add(addColumn("客户名称",
										"billMaster.scmCoc.name", 80));
						// list.add(addColumn("单据序号", "wareType", 80));

						list.add(addColumn("单据号", "billMaster.billNo", 100));
						// list.add(addColumn("单位重量", "name", 100));
						// list.add(addColumn("已报关重量", "wareType", 80));

						list.add(addColumn("报关单位", "hsUnit.name", 80));
						// list.add(addColumn("手册报关单位", "name", 100));
						list.add(addColumn("报关名称", "hsName", 80));

						list.add(addColumn("报关型号规格", "hsSpec", 100));
						list.add(addColumn("制单号", "productNo", 100));
						return list;
					}
				});
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
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();
			jPanel = new JPanel();
			jLabel = new JLabel();
			jPanel.setLayout(new BorderLayout());
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			jPanel.setBackground(java.awt.Color.white);
			jLabel8.setText("");
			jLabel8.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel9.setText("");
			jLabel9
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jPanel.add(jLabel, java.awt.BorderLayout.CENTER);
			jPanel.add(jLabel8, java.awt.BorderLayout.EAST);
			jPanel.add(jLabel9, java.awt.BorderLayout.WEST);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jSplitPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(325);
			jSplitPane.setDividerSize(8);
			jSplitPane.setTopComponent(getJPanel1());
			jSplitPane.setBottomComponent(getJPanel2());
		}
		return jSplitPane;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel13 = new JLabel();
			jLabel13.setBounds(new java.awt.Rectangle(418, 97, 53, 18));
			jLabel13.setText("结束料号");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new java.awt.Rectangle(198, 97, 54, 21));
			jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel12.setText("开始料号");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new java.awt.Rectangle(421, 74, 52, 21));
			jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel11.setText("工厂规格");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new java.awt.Rectangle(415, 52, 58, 21));
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setText("报关规格");
			jLabel7 = new JLabel();

			jLabel6 = new JLabel();

			jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBorder(javax.swing.BorderFactory
					.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jLabel1.setText("日期从:");
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel1.setBounds(205, 5, 47, 21);
			jLabel2.setBounds(447, 6, 26, 21);
			jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel2.setText("到：");
			jLabel3.setBounds(196, 29, 56, 21);
			jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel3.setText("单据类型");
			jLabel4.setBounds(18, 3, 29, 23);
			jLabel4.setText("仓库");
			jLabel5.setBounds(201, 52, 51, 21);
			jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel5.setText("报关名称");
			jLabel7.setBounds(198, 74, 54, 21);
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel7.setText("工厂名称");
			jLabel6.setBounds(401, 29, 72, 21);
			jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel6.setText("客户(供应商)");
			jPanel2.add(jLabel1, null);

			jPanel2.add(getCbbBeginDate(), null);
			jPanel2.add(getCbbBillType(), null);
			jPanel2.add(getTfHsName(), null);
			jPanel2.add(getBtnHsName(), null);
			jPanel2.add(getTfPtName(), null);
			jPanel2.add(getBtnPtName(), null);
			jPanel2.add(getTfPtNo(), null);
			jPanel2.add(getBtnPtNo(), null);
			jPanel2.add(getCbbEndDate(), null);
			jPanel2.add(getCbbScmCoc(), null);
			jPanel2.add(getTfHsSpec(), null);
			jPanel2.add(getBtnHsSpec(), null);
			jPanel2.add(getTfPtSpec(), null);
			jPanel2.add(getBtnPtSpec(), null);
			jPanel2.add(getBtnSearch(), null);
			jPanel2.add(getBtnPrint(), null);
			jPanel2.add(getBtnExit(), null);

			jPanel2.add(jLabel2, null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getJScrollPane1(), null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(jLabel7, null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(jLabel10, null);
			jPanel2.add(jLabel11, null);
			jPanel2.add(jLabel12, null);

			jPanel2.add(jLabel13, null);
			jPanel2.add(getTfEndPtNo(), null);
			jPanel2.add(getBtnEndPtNo(), null);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jTable
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
			jScrollPane.setBorder(javax.swing.BorderFactory
					.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		}
		return jScrollPane;
	}

	private void initTopName() {

		// if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
		// jLabel.setText("成品出入库明细帐:总表");
		// this.setReportTilte("成品出入库明细帐:总表");
		// } else

		if (MaterielType.MATERIEL.equals(materielType)) {
			jLabel.setText("委外料件出入库明细帐");
			this.setReportTilte("委外料件出入库明细帐");
		}
		//			
		// else if (MaterielType.MACHINE.equals(materielType)) {
		// jLabel.setText("设备情况:设备出入库明细帐");
		// this.setReportTilte("设备情况:设备出入库明细帐");
		//
		// }

		else if (MaterielType.SEMI_FINISHED_PRODUCT.equals(materielType)) {
			jLabel.setText("委外半成品出入库明细帐");
			this.setReportTilte("委外半成品出入库明细帐");

		}
		// else if (MaterielType.REMAIN_MATERIEL.equals(materielType)) {
		// jLabel.setText("边角料出入库明细帐:总表");
		// this.setReportTilte("边角料出入库明细帐:总表");
		//
		// } else if (MaterielType.BAD_PRODUCT.equals(materielType)) {
		// jLabel.setText("残次品出入库明细帐:总表");
		// this.setReportTilte("残次品出入库明细帐:总表");
		// }

	}

	/**
	 * 
	 * This method initializes jCalendarComboBox
	 * 
	 * 
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 * 
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(257, 6, 128, 21);
		}
		return cbbBeginDate;
	}

	/**
	 * 
	 * This method initializes jCalendarComboBox1
	 * 
	 * 
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 * 
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(480, 6, 128, 21);
		}
		return cbbEndDate;
	}

	/**
	 * 
	 * This method initializes jComboBox
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getCbbBillType() {
		if (cbbBillType == null) {
			cbbBillType = new JComboBox();
			cbbBillType.setBounds(257, 29, 128, 21);
		}
		return cbbBillType;
	}

	/**
	 * 
	 * This method initializes jList
	 * 
	 * 
	 * 
	 * @return javax.swing.JList
	 * 
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
			jList.setCellRenderer(new CheckListRenderer());
			jList.setFixedCellHeight(18);
			jList.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int index = jList.locationToIndex(e.getPoint());
					CheckableItem item = (CheckableItem) jList.getModel()
							.getElementAt(index);
					item.setSelected(!item.isSelected());
					Rectangle rect = jList.getCellBounds(index, index);
					jList.repaint(rect);
				}
			});
		}
		return jList;
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
			CheckableItem item = (CheckableItem) value;
			setSelected(item.isSelected());
			this.setSize(350, 17);
			setText("  " + item.getCode() + " (" + item.getName() + ")");
			return this;
		}
	}

	/**
	 * 
	 * This method initializes jScrollPane1
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(48, 3, 142, 132);
			jScrollPane1.setViewportView(getJList());
		}
		return jScrollPane1;
	}

	/**
	 * 
	 * This method initializes jComboBox2
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(480, 29, 128, 21);
		}
		return cbbScmCoc;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setBounds(629, 5, 64, 23);
			btnSearch.setText("查询");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!setTipMessage()) {
						return;
					}
					String materielType = getMaterielType();
					
					List<Condition> conditions = new ArrayList<Condition>();
					conditions
							.add(new Condition("and", null,
									"billMaster.isValid", "=",
									new Boolean(true), null));
					if (cbbBillType.getSelectedItem() != null
							&& !cbbBillType.getSelectedItem().equals("")) { // 类型
						conditions.add(new Condition("and", null,
								"billMaster.billType.code", "=",
								((BillType) cbbBillType.getSelectedItem())
										.getCode(), null));
					}else {
						
						if(materielType.equalsIgnoreCase(MaterielType.MATERIEL)){
							
							conditions.add(new Condition("and", "(",
									"billMaster.billType.code", "=","1110", null));// 1110 外发加工出库
							conditions.add(new Condition("or", null,
									"billMaster.billType.code", "=","1012", null));// 1012 外发加工退回料件
							conditions.add(new Condition("or", null,
									"billMaster.billType.code", "=","1013", null));// 1013 外发加工返回料件
							conditions.add(new Condition("or", null,
									"billMaster.billType.code", "=","1014", ")"));// 1014 委外期初单
							
						}else if(materielType.equalsIgnoreCase(MaterielType.SEMI_FINISHED_PRODUCT)){
							
							conditions.add(new Condition("and", "(",
									"billMaster.billType.code", "=","4105", null));//4105 外发加工领料
							conditions.add(new Condition("or", null,
									"billMaster.billType.code", "=","4104", null));// 4104 外发加工返修半成品
							conditions.add(new Condition("or", null,
									"billMaster.billType.code", "=","4003", null));// 4003 委外加工入库
							conditions.add(new Condition("or", null,
									"billMaster.billType.code", "=","4103", ")"));// 4103 委外加工出库
							
						}
					}
					if (cbbBeginDate.getDate() != null) { // 开始日期
						conditions.add(new Condition("and", null,
								"billMaster.validDate", ">=",
								CommonVars.dateToStandDate(cbbBeginDate
										.getDate()), null));
					}
					if (cbbEndDate.getDate() != null) { // 结束日期
						conditions.add(new Condition("and", null,
								"billMaster.validDate", "<=", CommonVars
										.dateToStandDate(cbbEndDate.getDate()),
								null));
					}
					if (!tfHsName.getText().trim().equals("")) {
						conditions.add(new Condition("and", null, "hsName",
								"=", tfHsName.getText(), null));
					}
					if (!tfHsSpec.getText().trim().equals("")) {
						conditions.add(new Condition("and", null, "hsSpec",
								"=", tfHsSpec.getText(), null));
					}
					if (cbbScmCoc.getSelectedItem() != null
							&& !cbbScmCoc.getSelectedItem().equals("")) { // 客户
						conditions.add(new Condition("and", null,
								"billMaster.scmCoc.name", "=",
								((ScmCoc) cbbScmCoc.getSelectedItem())
										.getName(), null));
					}
					if (!tfPtName.getText().trim().equals("")) {
						conditions.add(new Condition("and", null, "ptName",
								"=", tfPtName.getText(), null));
					}
					if (!tfPtSpec.getText().trim().equals("")) {
						conditions.add(new Condition("and", null, "ptSpec",
								"=", tfPtSpec.getText(), null));
					}

					// 工厂料号不等于空,结束料号为空时
					if (!tfPtNo.getText().trim().equals("")
							&& tfEndPtNo.getText().trim().equals("")) {
						conditions.add(new Condition("and", null, "ptPart",
								"=", tfPtNo.getText(), null));
					} else // 工厂料号不等于空,结束料号不为空时
					if (!tfPtNo.getText().trim().equals("")
							&& !tfEndPtNo.getText().trim().equals("")) {
						conditions.add(new Condition("and", "(", "ptPart",
								">=", tfPtNo.getText(), null));
						conditions.add(new Condition("and", null, "ptPart",
								"<=", tfEndPtNo.getText(), ")"));
					}
					// 仓库
					List<CheckableItem> checkableItemList = new ArrayList<CheckableItem>();
					for (int j = 0; j < DgOutSourceDetailQuery.this.jList
							.getModel().getSize(); j++) {
						CheckableItem item = (CheckableItem) DgOutSourceDetailQuery.this.jList
								.getModel().getElementAt(j);
						if (item.isSelected) {
							checkableItemList.add(item);
						}
					}
					for (int j = 0; j < checkableItemList.size(); j++) {
						CheckableItem item = checkableItemList.get(j);
						Condition condition = null;
						if (j == 0) {
							condition = new Condition("and", "(",
									"wareSet.code", "=", item.getCode().trim(),
									null);
						} else {
							condition = new Condition("or", null,
									"wareSet.code", "=", item.getCode().trim(),
									null);
						}
						if (j == checkableItemList.size() - 1) {
							condition.setEndBracket(")");
						}
						conditions.add(condition);
					}
					// 物料类型
					String billDetail = BillUtil
							.getBillDetailTableNameByMaterielType(getMaterielType());
					//  
					new find(conditions, "", billDetail).start();
				}
			});

		}
		return btnSearch;
	}

	class find extends Thread {
		List<Condition>	conditions	= null;
		String			orderBy		= null;
		String			billDetail	= null;

		public find(List<Condition> conditions, String orderBy,
				String billDetail) {
			this.conditions = conditions;
			this.orderBy = orderBy;
			this.billDetail = billDetail;
		}

		public void run() {
			btnSearch.setEnabled(false);
			try {
				CommonProgress.showProgressDialog(DgOutSourceDetailQuery.this);
				CommonProgress.setMessage("系统正获取数据，请稍后...");

				result = casAction.commonCount(new Request(CommonVars
						.getCurrUser()), null, billDetail, conditions, orderBy,
						"");

				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgOutSourceDetailQuery.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				CommonProgress.closeProgressDialog();
				if (result != null && !result.isEmpty()) {
					initTable(result);
				} else {
					initTable(new ArrayList());
					JOptionPane.showMessageDialog(DgOutSourceDetailQuery.this,
							"没有查到符合条件的记录！", "提示！", 2);
				}
			}
			btnSearch.setEnabled(true);
		}
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setBounds(629, 31, 64, 23);
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					btnPrint.setEnabled(false);
					if (result != null && !result.isEmpty()) {
						CommonDataSource imgExgDS = new CommonDataSource(result);
						List<BaseCompany> company = new ArrayList<BaseCompany>();
						company.add(CommonVars.getCurrUser().getCompany());
						CommonDataSource companyDS = new CommonDataSource(
								company);
						String hsName = tfHsName.getText();

						InputStream masterReportStream = DgOutSourceDetailQuery.class
								.getResourceAsStream("report/FactoryQueryReport.jasper");
						InputStream detailReportStream = DgOutSourceDetailQuery.class
								.getResourceAsStream("report/FactoryQueryReportSubb.jasper");
						try {
							JasperReport detailReport = (JasperReport) JRLoader
									.loadObject(detailReportStream);

							Map<String, Object> parameters = new HashMap<String, Object>();
							parameters.put("imgExgDS", imgExgDS);
							parameters.put("detailReport", detailReport);
							parameters.put("reportTilte",
									DgOutSourceDetailQuery.this
											.getReportTilte());
							parameters.put("hsName", hsName);
							JasperPrint jasperPrint;
							jasperPrint = JasperFillManager.fillReport(
									masterReportStream, parameters, companyDS);
							DgReportViewer viewer = new DgReportViewer(
									jasperPrint);
							viewer.setVisible(true);
						} catch (JRException e1) {
							e1.printStackTrace();
						}
					}
					btnPrint.setEnabled(true);
				}
			});

		}
		return btnPrint;
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(629, 57, 64, 23);
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgOutSourceDetailQuery.this.dispose();

				}
			});

		}
		return btnExit;
	}

	/**
	 * 提示信息
	 * 
	 * @return
	 */
	private boolean setTipMessage() {

		String ptNo = tfPtNo.getText().trim();
		if (ptNo.equals("")) {
			if (JOptionPane.showConfirmDialog(this,
					"[料号] 为空!!\n查询可能要较长的时间，要继续吗??", "确认",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
				return false;
			}
		}

		// if (this.cbbScmCoc.getSelectedItem() == null) {
		// if (JOptionPane.showConfirmDialog(DgOutSourceDetailQuery.this,
		// "客户名称为空!!\n查询可能要较长的时间，要继续吗??", "确认",
		// JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) !=
		// JOptionPane.YES_OPTION) {
		// return false;
		// }
		// }
		//
		// if (tfPtName.getText().trim().equals("")
		// && tfPtNo.getText().trim().equals("")) {
		// if (JOptionPane.showConfirmDialog(DgOutSourceDetailQuery.this,
		// "工厂名称和料号都为空!!\n查询可能要较长的时间，要继续吗??", "确认",
		// JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) !=
		// JOptionPane.YES_OPTION) {
		// return false;
		// }
		// }
		return true;
	}

	/**
	 * @return Returns the intOutFlat.
	 */
	public String getMaterielType() {
		return materielType;
	}

	/**
	 * @param intOutFlat
	 *            The intOutFlat to set.
	 */
	public void setMaterielType(String intOutFlat) {
		this.materielType = intOutFlat;
	}

	/**
	 * @return Returns the reportTilte.
	 */
	public String getReportTilte() {
		return reportTilte;
	}

	/**
	 * @param reportTilte
	 *            The reportTilte to set.
	 */
	public void setReportTilte(String reportTilte) {
		this.reportTilte = reportTilte;
	}

	/**
	 * This method initializes tfHsName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsName() {
		if (tfHsName == null) {
			tfHsName = new JTextField();
			tfHsName.setBounds(new java.awt.Rectangle(256, 52, 111, 21));
			// tfHsName.set
		}
		return tfHsName;
	}

	/**
	 * This method initializes tfHsSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsSpec() {
		if (tfHsSpec == null) {
			tfHsSpec = new JTextField();
			tfHsSpec.setBounds(new java.awt.Rectangle(480, 52, 111, 21));
		}
		return tfHsSpec;
	}

	/**
	 * This method initializes tfPtName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtName() {
		if (tfPtName == null) {
			tfPtName = new JTextField();
			tfPtName.setBounds(new java.awt.Rectangle(256, 74, 111, 21));
		}
		return tfPtName;
	}

	/**
	 * This method initializes tfPtSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtSpec() {
		if (tfPtSpec == null) {
			tfPtSpec = new JTextField();
			tfPtSpec.setBounds(new java.awt.Rectangle(480, 74, 111, 21));
		}
		return tfPtSpec;
	}

	/**
	 * This method initializes btnPtSpec
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPtSpec() {
		if (btnPtSpec == null) {
			btnPtSpec = new JButton();
			btnPtSpec.setBounds(new java.awt.Rectangle(591, 74, 17, 21));
			btnPtSpec.setText("...");
			btnPtSpec.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = getMaterielType();
					Object object = CasQuery.getInstance()
							.findPtSpecFromStatCusNameRelationMt(materialType,
									tfPtName.getText());
					if (object != null) {
						tfPtSpec.setText((String) ((TempObject) object)
								.getObject());
					}
				}
			});
		}
		return btnPtSpec;
	}

	/**
	 * This method initializes btnHsName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnHsName() {
		if (btnHsName == null) {
			btnHsName = new JButton();
			btnHsName.setBounds(new java.awt.Rectangle(368, 52, 17, 21));
			btnHsName.setText("...");
			btnHsName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = getMaterielType();
					Object object = CasQuery.getInstance()
							.findHsNameFromStatCusNameRelationHsn(materialType);
					if (object != null) {
						tfHsName.setText((String) ((TempObject) object)
								.getObject());
						tfHsSpec.setText((String) ((TempObject) object)
								.getObject1());
					}
				}
			});
		}
		return btnHsName;
	}

	/**
	 * This method initializes btnHsSpec
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnHsSpec() {
		if (btnHsSpec == null) {
			btnHsSpec = new JButton();
			btnHsSpec.setBounds(new java.awt.Rectangle(591, 52, 17, 21));
			btnHsSpec.setText("...");
			btnHsSpec.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = getMaterielType();
					Object object = CasQuery.getInstance()
							.findHsSpecFromStatCusNameRelationHsn(materialType,
									tfHsName.getText());
					if (object != null) {
						tfHsSpec.setText((String) ((TempObject) object)
								.getObject());
					}
				}
			});
		}
		return btnHsSpec;
	}

	/**
	 * This method initializes btnPtName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPtName() {
		if (btnPtName == null) {
			btnPtName = new JButton();
			btnPtName.setBounds(new java.awt.Rectangle(368, 74, 17, 21));
			btnPtName.setText("...");
			btnPtName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = getMaterielType();
					Object object = CasQuery.getInstance()
							.findPtNameFromStatCusNameRelationMt(materialType);
					if (object != null) {
						tfPtName.setText((String) ((TempObject) object)
								.getObject());
					}
				}
			});
		}
		return btnPtName;
	}

	/**
	 * This method initializes tfPtNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtNo() {
		if (tfPtNo == null) {
			tfPtNo = new JTextField();
			tfPtNo.setBounds(new java.awt.Rectangle(256, 97, 111, 21));
			tfPtNo.getDocument().addDocumentListener(new DocumentListener() {
				private void setState() {
					String editAfterQueryValue = tfPtNo.getText().trim();
					if (!"".equalsIgnoreCase(editAfterQueryValue)) {
						tfEndPtNo.setEditable(true);
						btnEndPtNo.setEnabled(true);
					} else {
						tfEndPtNo.setEditable(false);
						btnEndPtNo.setEnabled(false);
					}
				}

				public void insertUpdate(DocumentEvent e) {
					setState();
				}

				public void removeUpdate(DocumentEvent e) {
					setState();
				}

				public void changedUpdate(DocumentEvent e) {
					setState();
				}

			});
		}
		return tfPtNo;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPtNo() {
		if (btnPtNo == null) {
			btnPtNo = new JButton();
			btnPtNo.setBounds(new java.awt.Rectangle(368, 97, 17, 20));
			btnPtNo.setText("...");
			btnPtNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<StatCusNameRelationMt> list = CasQuery.getInstance()
							.getStatCusNameRelationMt(false, getMaterielType(),
									new ArrayList());
					if (list != null && list.size() > 0) {
						StatCusNameRelationMt sm = list.get(0);
						Materiel m = sm.getMateriel();
						tfPtNo.setText(m == null ? "" : m.getPtNo());
					}
				}
			});
		}
		return btnPtNo;
	}

	/**
	 * This method initializes tfEndPtNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEndPtNo() {
		if (tfEndPtNo == null) {
			tfEndPtNo = new JTextField();
			tfEndPtNo.setBounds(new java.awt.Rectangle(480, 97, 110, 21));
			tfEndPtNo.setEditable(false);
		}
		return tfEndPtNo;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEndPtNo() {
		if (btnEndPtNo == null) {
			btnEndPtNo = new JButton();
			btnEndPtNo.setBounds(new java.awt.Rectangle(591, 97, 17, 20));
			btnEndPtNo.setText("...");
			btnEndPtNo.setEnabled(false);
			btnEndPtNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<StatCusNameRelationMt> list = CasQuery.getInstance()
							.getStatCusNameRelationMt(false, getMaterielType(),
									new ArrayList());
					if (list != null && list.size() > 0) {
						StatCusNameRelationMt sm = list.get(0);
						Materiel m = sm.getMateriel();
						tfEndPtNo.setText(m == null ? "" : m.getPtNo());
					}
				}
			});
		}
		return btnEndPtNo;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
