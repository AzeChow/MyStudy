/*
 * Created on 2004-9-20
 *
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
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
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.footer.JFooterTable;
import com.bestway.client.util.footer.TableFooterType;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.client.erpbillcenter.DgBillMaster;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgFactoryQuery extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JFooterTable jTable = null;

	private JFooterScrollPane jScrollPane = null;

	private JLabel jLabel = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JComboBox cbbBillType = null;

	private JList jList = null;

	private JScrollPane jScrollPane1 = null;

	private JComboBox cbbScmCoc = null;

	private JButton btnSearch = null;

	private JButton btnPrint = null;

	private JButton btnExit = null;

	private MaterialManageAction materialManageAction = null;

	private CasAction casAction = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel7 = null;

	private String materielType = null; // @jve:decl-index=0:

	private String reportTilte = null;

	private List result = null;

	private JTextField tfHsName = null;

	private JLabel jLabel10 = null;

	private JTextField tfHsSpec = null;

	private JTextField tfPtName = null;

	private JLabel jLabel11 = null;

	private JTextField tfPtSpec = null;

	private JButton btnPtSpec = null;

	private JButton btnHsName = null;

	private JButton btnHsSpec = null;

	private JButton btnPtName = null;

	private JLabel jLabel12 = null;

	private JTextField tfPtNo = null;

	private JButton btnPtNo = null;

	private JLabel jLabel13 = null;

	private JTextField tfEndPtNo = null;

	private JButton btnEndPtNo = null;

	private JTableListModel tableModel = null;

	private static String SELECT_ALL = "全选";

	private static String SELECT_NOT_ALL = "不选"; // @jve:decl-index=0:
	private FindSearchCompany findThread = null; // @jve:decl-index=0:
	/**
	 * 小数位控制
	 */
	private Integer allMaximumFractionDigits = CasCommonVars.getOtherOption()
			.getAllReportInOutMaximumFractionDigits() == null ? 6
			: CasCommonVars.getOtherOption()
					.getAllReportInOutMaximumFractionDigits(); // @jve:decl-index=0:

	private JLabel jLabel14 = null;

	private JLabel lbAllData = null;

	private JLabel jLabel16 = null;

	private JLabel lbShowData = null;

	private JLabel jLabel18 = null;

	private JCheckBox cbCustomsNoIsNull = null;

	/**
	 * This is the default constructor
	 */
	public DgFactoryQuery() {
		super(false);
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initialize();
	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {
			lbAllData.setText("0");
			lbShowData.setText("0");
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

		this.setTitle("工厂查询统计报表");
		this.setSize(752, 541);
		this.setContentPane(getJContentPane());

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				if (findThread != null) {
					findThread.interrupt();
				}
			}
		});
	}

	private void initUI() {

		List list = casAction.findBillMaterielType(
				new Request(CommonVars.getCurrUser()), this.getMaterielType());

		this.cbbBillType.setModel(new DefaultComboBoxModel(list.toArray()));

		this.cbbBillType.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));

		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbBillType, "code", "name");

		this.cbbBillType.setSelectedIndex(-1);

		// 初始化客户
		List listScmCoc = materialManageAction.findScmCocs(new Request(
				CommonVars.getCurrUser(), true));

		this.cbbScmCoc.setModel(new DefaultComboBoxModel(listScmCoc.toArray()));
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 50, 200));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbScmCoc, "code", "name");
		this.cbbScmCoc.setUI(new CustomBaseComboBoxUI(250));
		this.cbbScmCoc.setSelectedIndex(-1);

		// 初始化仓库
		Vector<Object> vector = new Vector<Object>();

		List listWareSet = materialManageAction.findWareSet(new Request(
				CommonVars.getCurrUser(), true));

		for (int i = 0; i < listWareSet.size(); i++) {
			if (i == 0) {
				vector.add(new CheckableItemExtra(SELECT_ALL));
			}
			WareSet obj = (WareSet) listWareSet.get(i);
			CheckableItem item = new CheckableItem(obj.getCode(), obj.getName());
			vector.add(item);
		}

		this.jList.setListData(vector);

		this.cbbBeginDate.setDate(java.sql.Date
				.valueOf(com.bestway.bcus.client.cas.parameter.CasCommonVars
						.getYear() + "-01-01"));
		this.cbbEndDate.setDate(java.sql.Date
				.valueOf(com.bestway.bcus.client.cas.parameter.CasCommonVars
						.getYear() + "-12-31"));
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
			return name + " (" + code + ")";
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

	/*
	 * 初始化查询的结果表格
	 */
	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter(allMaximumFractionDigits) {
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
						list.add(addColumn("客户名称", "billMaster.scmCoc.name", 80));

						// 送货单号
						list.add(addColumn("送货单号", "takeBillNo", 100));

						// list.add(addColumn("单据序号", "wareType", 80));
						list.add(addColumn("单据号", "billMaster.billNo", 100));
						// list.add(addColumn("单位重量", "name", 100));
						// list.add(addColumn("已报关重量", "wareType", 80));
						list.add(addColumn("报关单号", "customNo", 100));
						list.add(addColumn("报关单位", "hsUnit.name", 80));
						// list.add(addColumn("手册报关单位", "name", 100));
						list.add(addColumn("报关名称", "hsName", 80));

						list.add(addColumn("报关型号规格", "hsSpec", 100));

						list.add(addColumn("金额", "totalPrice", 100));

						list.add(addColumn("制单号", "productNo", 100));

						list.add(addColumn("备注", "note", 100));

						return list;
					}
				});
		// 页脚
		tableModel.clearFooterTypeInfo();
		tableModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		tableModel.addFooterTypeInfo(new TableFooterType(7,
				TableFooterType.SUM, ""));
		// tableModel.addFooterTypeInfo(new TableFooterType(8,
		// TableFooterType.SUM, ""));
		tableModel.addFooterTypeInfo(new TableFooterType(9,
				TableFooterType.SUM, ""));
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
			// jPanel.setLayout(new BorderLayout());
			// jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD,
			// 18));
			// jPanel
			// .setBorder(javax.swing.BorderFactory
			// .createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			// jPanel.setBackground(java.awt.Color.white);
			// jLabel8.setText("");
			// jLabel8.setIcon(new ImageIcon(getClass().getResource(
			// "/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			// jLabel9.setText("");
			// jLabel9
			// .setIcon(new ImageIcon(
			// getClass()
			// .getResource(
			// "/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			// jPanel.add(jLabel, java.awt.BorderLayout.CENTER);
			// jPanel.add(jLabel8, java.awt.BorderLayout.EAST);
			// jPanel.add(jLabel9, java.awt.BorderLayout.WEST);
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
			jSplitPane.setDividerLocation(145);
			jSplitPane.setDividerSize(8);
			jSplitPane.setTopComponent(getJPanel2());
			jSplitPane.setBottomComponent(getJPanel1());
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
			jLabel18 = new JLabel();
			jLabel18.setBounds(new Rectangle(688, 121, 20, 18));
			jLabel18.setText("条");
			jLabel18.setForeground(Color.BLUE);
			lbShowData = new JLabel();
			lbShowData.setBounds(new Rectangle(605, 121, 83, 18));
			lbShowData.setText("JLabel");
			lbShowData.setForeground(Color.BLUE);
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(492, 121, 110, 18));
			jLabel16.setText("条        已显示数据：");
			jLabel16.setForeground(Color.BLUE);
			lbAllData = new JLabel();
			lbAllData.setBounds(new Rectangle(412, 121, 77, 18));
			lbAllData.setText("JLabel");
			lbAllData.setForeground(Color.BLUE);
			jLabel14 = new JLabel();
			jLabel14.setBounds(new Rectangle(322, 121, 88, 18));
			jLabel14.setText("查询数据共有 ：");
			jLabel14.setForeground(Color.BLUE);
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
			jPanel2.add(jLabel14, null);
			jPanel2.add(lbAllData, null);
			jPanel2.add(jLabel16, null);
			jPanel2.add(lbShowData, null);
			jPanel2.add(jLabel18, null);
			jPanel2.add(getCbCustomsNoIsNull(), null);
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
			jTable = new JFooterTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() >= 2) {
						editData();
					}
				}
			});
		}
		return jTable;
	}

	private void editData() {

		BillDetail tmp = (BillDetail) tableModel.getCurrentRow();

		BillMaster billMaster = tmp.getBillMaster();

		if (billMaster.getBillType() == null) {
			return;
		}

		BillMaster master = casAction.findBillMaster(
				new Request(CommonVars.getCurrUser()), billMaster.getId(),
				billMaster.getBillType().getBillType());

		DgBillMaster dgBillMaster = new DgBillMaster();

		dgBillMaster.setDataState(DataState.EDIT);

		dgBillMaster.setBillType(billMaster.getBillType());

		// dgBillMaster.setTableModel(tableModel);

		dgBillMaster.setBillMaster(master);

		dgBillMaster.setEditFromQuery(true);

		dgBillMaster.setBillCategoryBillMaster(billMaster.getBillType()
				.getBillType());

		dgBillMaster.setValidDate(master.getValidDate());

		dgBillMaster.setVisible(true);
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
	private JFooterScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JFooterScrollPane();
			jScrollPane.setViewportView(getJTable());
			jScrollPane.setBorder(javax.swing.BorderFactory
					.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		}
		return jScrollPane;
	}

	private void initTopName() {

		if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
			jLabel.setText("成品出入库明细帐");
			this.setReportTilte("成品出入库明细帐");
		} else if (MaterielType.MATERIEL.equals(materielType)) {
			jLabel.setText("料件出入库明细帐");
			this.setReportTilte("料件出入库明细帐");
		} else if (MaterielType.MACHINE.equals(materielType)) {
			jLabel.setText("设备出入库明细帐");
			this.setReportTilte("设备出入库明细帐");

		} else if (MaterielType.SEMI_FINISHED_PRODUCT.equals(materielType)) {
			jLabel.setText("半成品出入库明细帐");
			this.setReportTilte("半成品出入库明细帐");

		} else if (MaterielType.REMAIN_MATERIEL.equals(materielType)) {
			jLabel.setText("边角料出入库明细帐");
			this.setReportTilte("边角料出入库明细帐");

		} else if (MaterielType.BAD_PRODUCT.equals(materielType)) {
			jLabel.setText("残次品出入库明细帐");
			this.setReportTilte("残次品出入库明细帐");
		}

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
					Object obj = jList.getModel().getElementAt(index);
					if (obj instanceof CheckableItemExtra) {
						CheckableItemExtra item = (CheckableItemExtra) obj;
						item.setSelected(!item.isSelected());
						if (item.isSelected()) {
							item.setName(SELECT_NOT_ALL);
						} else {
							item.setName(SELECT_ALL);
						}
						ListModel model = jList.getModel();
						for (int i = 0; i < model.getSize(); i++) {
							Object o = model.getElementAt(i);
							if (o instanceof CheckableItem) {
								CheckableItem c = (CheckableItem) o;
								c.setSelected(item.isSelected());
							}
						}
						jList.repaint();
					} else if (obj instanceof CheckableItem) {
						CheckableItem item = (CheckableItem) obj;
						item.setSelected(!item.isSelected());
						Rectangle rect = jList.getCellBounds(index, index);
						jList.repaint(rect);
					}
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

			if (value instanceof CheckableItemExtra) {
				CheckableItemExtra item = (CheckableItemExtra) value;
				setSelected(item.isSelected());
				setSize(350, 17);
				setFont(new Font(getFont().getName(), Font.BOLD, getFont()
						.getSize()));
				setText("  111" + item.getName());
			} else if (value instanceof CheckableItem) {
				CheckableItem item = (CheckableItem) value;
				setSelected(item.isSelected());
				setSize(350, 17);
				setFont(new Font(getFont().getName(), Font.PLAIN, getFont()
						.getSize()));
				setText("  " + item.getCode() + " (" + item.getName() + ")");
			}
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
	 * 查询按钮 初始化
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setBounds(642, 7, 64, 23);
			btnSearch.setText("查询");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!setTipMessage()) {
						return;
					}
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
					}
					if (cbbBeginDate.getDate() != null) { // 开始日期
						conditions.add(new Condition("and", null,
								"billMaster.validDate", ">=", CommonVars
										.getBeginDate(cbbBeginDate.getDate()),
								null));
					}
					if (cbbEndDate.getDate() != null) { // 结束日期
						conditions.add(new Condition("and", null,
								"billMaster.validDate", "<=", CommonVars
										.getEndDate(cbbEndDate.getDate()), null));
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
					// 报关单号是否允许为空
					if (!cbCustomsNoIsNull.isSelected()) {
						conditions.add(new Condition("and", null, "customNo",
								"!= ", "", null));
					}
					// 仓库
					List<CheckableItem> checkableItemList = new ArrayList<CheckableItem>();
					for (int j = 0; j < DgFactoryQuery.this.jList.getModel()
							.getSize(); j++) {
						Object value = DgFactoryQuery.this.jList.getModel()
								.getElementAt(j);
						if (value instanceof CheckableItem) {
							CheckableItem item = (CheckableItem) value;
							if (item.isSelected) {
								checkableItemList.add(item);
							}
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
					// new find(conditions, "order by a.billMaster.validDate ",
					// billDetail).start();
					// new find().start();
					if (findThread != null) {
						findThread.interrupt();
					}

					findAll(conditions, "order by a.billMaster.validDate ",
							billDetail);
				}
			});

		}
		return btnSearch;
	}

	// class find extends Thread {
	// public void run() {
	// try {
	// btnSearch.setEnabled(false);
	// List list = new ArrayList();
	// List listUpdate = new ArrayList();
	// List<Condition> conditions = new ArrayList<Condition>();
	// conditions.add(new Condition("and", null, "isValid", "=",
	// new Boolean(true), null));
	// if (cbbBillType.getSelectedItem() != null
	// && !cbbBillType.getSelectedItem().equals("")) { // 类型
	// conditions.add(new Condition("and", null, "billType.code",
	// "=", ((BillType) cbbBillType.getSelectedItem())
	// .getCode(), null));
	// }
	// if (cbbBeginDate.getDate() != null) { // 开始日期
	// conditions.add(new Condition("and", null, "validDate",
	// ">=", CommonVars.getBeginDate(cbbBeginDate
	// .getDate()), null));
	// }
	// if (cbbEndDate.getDate() != null) { // 结束日期
	// conditions.add(new Condition("and", null, "validDate",
	// "<=", CommonVars.getEndDate(cbbEndDate.getDate()),
	// null));
	// }
	// if (!tfHsName.getText().trim().equals("")) {
	// conditions.add(new Condition("and", null, "hsName", "=",
	// tfHsName.getText(), null));
	// }
	// if (!tfHsSpec.getText().trim().equals("")) {
	// conditions.add(new Condition("and", null, "hsSpec", "=",
	// tfHsSpec.getText(), null));
	// }
	// if (cbbScmCoc.getSelectedItem() != null
	// && !cbbScmCoc.getSelectedItem().equals("")) { // 客户
	// conditions.add(new Condition("and", null, "scmCoc.name",
	// "=", ((ScmCoc) cbbScmCoc.getSelectedItem())
	// .getName(), null));
	// }
	// if (!tfPtName.getText().trim().equals("")) {
	// conditions.add(new Condition("and", null, "ptName", "=",
	// tfPtName.getText(), null));
	// }
	// if (!tfPtSpec.getText().trim().equals("")) {
	// conditions.add(new Condition("and", null, "ptSpec", "=",
	// tfPtSpec.getText(), null));
	// }
	//
	// // 工厂料号不等于空,结束料号为空时
	// if (!tfPtNo.getText().trim().equals("")
	// && tfEndPtNo.getText().trim().equals("")) {
	// conditions.add(new Condition("and", null, "ptPart", "=",
	// tfPtNo.getText(), null));
	// } else // 工厂料号不等于空,结束料号不为空时
	// if (!tfPtNo.getText().trim().equals("")
	// && !tfEndPtNo.getText().trim().equals("")) {
	// conditions.add(new Condition("and", "(", "ptPart", ">=",
	// tfPtNo.getText(), null));
	// conditions.add(new Condition("and", null, "ptPart", "<=",
	// tfEndPtNo.getText(), ")"));
	// }
	// // 仓库
	// List<CheckableItem> checkableItemList = new ArrayList<CheckableItem>();
	// for (int j = 0; j < DgFactoryQuery.this.jList.getModel()
	// .getSize(); j++) {
	// Object value = DgFactoryQuery.this.jList.getModel()
	// .getElementAt(j);
	// if (value instanceof CheckableItem) {
	// CheckableItem item = (CheckableItem) value;
	// if (item.isSelected) {
	// checkableItemList.add(item);
	// }
	// }
	// }
	// for (int j = 0; j < checkableItemList.size(); j++) {
	// CheckableItem item = checkableItemList.get(j);
	// Condition condition = null;
	// if (j == 0) {
	// condition = new Condition("and", "(", "wareSet.code",
	// "=", item.getCode().trim(), null);
	// } else {
	// condition = new Condition("or", null, "wareSet.code",
	// "=", item.getCode().trim(), null);
	// }
	// if (j == checkableItemList.size() - 1) {
	// condition.setEndBracket(")");
	// }
	// conditions.add(condition);
	// }
	// String orderBy = " order by a.validDate ";
	// String masterTalbeName = BillUtil
	// .getBillMasterTableNameByMaterielType(getMaterielType());
	// String detailTableName = BillUtil
	// .getBillDetailTableNameByMaterielType(getMaterielType());
	// CommonStepProgress.showStepProgressDialog();
	// CommonStepProgress.setStepMessage("系统开始进行查询，请稍后...");
	// List selectList = casAction.commonCount(new Request(CommonVars
	// .getCurrUser()), null, masterTalbeName, conditions,
	// null, "");
	// CommonStepProgress.setStepProgressMaximum(selectList.size());
	// CommonStepProgress.setStepMessage("系统总有[" + selectList.size()
	// + "]开始进行查询，请稍后...");
	//
	// int i = 0;
	// for (; i < selectList.size(); i++) {
	// BillMaster billMaster = (BillMaster) selectList.get(i);
	// CommonStepProgress.setStepProgressValue(i);
	// // listUpdate.add(billMaster);
	// list.addAll(casAction.findBillDetailByParentFromMasterList(
	// new Request(CommonVars.getCurrUser()),
	// detailTableName, billMaster.getId()));
	// CommonStepProgress.setStepMessage("系统正在查询 ［ " + i
	// + " 条］ 数据");
	// // if (i % 50 == 0 && i != 0) {
	// //
	// // listUpdate.clear();
	// // }
	// }
	// // if (listUpdate.size() > 0) {
	// // list.addAll(casAction.findBillDetailByParentFromMasterList(new
	// // Request(CommonVars
	// // .getCurrUser()), listUpdate));
	// // CommonStepProgress.setStepMessage("系统正在查询 ［ " + i
	// // + " 条］ 数据");
	// // listUpdate.clear();
	// // }
	// initTable(list);
	//
	// CommonStepProgress.closeStepProgressDialog();
	// } catch (Exception e) {
	// CommonStepProgress.closeStepProgressDialog();
	// JOptionPane.showMessageDialog(DgFactoryQuery.this, "查询失败：！"
	// + e.getMessage(), "提示", 2);
	// } finally {
	// CommonStepProgress.closeStepProgressDialog();
	// btnSearch.setEnabled(true);
	// }
	// }
	// }

	/**
	 * 线程查询
	 * 
	 * @param ownerCode
	 * @param ownerName
	 */
	private void findAll(List<Condition> conditions, String orderBy,
			String billDetail) {
		if (findThread == null) {
			findThread = new FindSearchCompany(conditions, orderBy, billDetail);
			findThread.start();
		} else {
			findThread.interrupt();
			findThread = new FindSearchCompany(conditions, orderBy, billDetail);
			findThread.start();
		}
	}

	/**
	 * 线程查询数据查错
	 */
	class FindSearchCompany extends Thread {

		List<Condition> conditions = null;
		String orderBy = null;
		String billDetail = null;
		CompanyOther other = CommonVars.getOther();
		private int length = other.getPageSize();
		private int index = 0;
		private boolean isInterrupt = false;
		private boolean isRunning = true;

		public boolean isIsInterrupt() {
			return isInterrupt;
		}

		public void setIsInterrupt(boolean isInterrupt) {
			this.isInterrupt = isInterrupt;
		}

		public boolean isIsRunning() {
			return isRunning;
		}

		public FindSearchCompany(List<Condition> conditions, String orderBy,
				String billDetail) {
			this.conditions = conditions;
			this.orderBy = orderBy;
			this.billDetail = billDetail;
			lbAllData.setText("0");
			lbShowData.setText("0");
			initTable(new Vector());
		}

		public void interrupt() {
			if (this.isRunning) {
				this.setIsInterrupt(true);
			}
			while (this.isRunning) {
				try {
					Thread.currentThread().sleep(200);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}

		public void run() {

			isRunning = true;

			final List templist = new ArrayList();

			try {

				tableModel = null;

				Long beginTime = System.currentTimeMillis();

				btnSearch.setEnabled(false);

				CommonStepProgress.showStepProgressDialog();

				CommonStepProgress.setStepMessage("系统正在统计总数，请稍后...");

				int count = casAction.countCommonSearchPage(new Request(
						CommonVars.getCurrUser()), null, billDetail,
						conditions, "", "");

				lbAllData.setText(String.valueOf(count));

				if (length <= 3000) {

					length = 3000;

				}

				CommonStepProgress.setStepMessage("系统总共有：【" + count
						+ "】条数据，正在查询前【" + length + "】条数据");

				CommonStepProgress.setStepProgressMaximum(count);

				if (count > 0) {

					List temp = casAction.commonSearchPageList(new Request(
							CommonVars.getCurrUser()), index, length, null,
							billDetail, conditions, "", "");

					CommonStepProgress.setStepProgressValue(index);

					while (temp.size() > 0 && !isInterrupt) {

						templist.addAll(temp);

						Long endTimes = System.currentTimeMillis();

						CommonStepProgress.setStepProgressValue(index);

						CommonStepProgress.setStepMessage("系统总共有【" + count
								+ "】条数据,已查询并显示的数据【" + templist.size()
								+ "】,已耗时【" + ((endTimes - beginTime) / 1000)
								+ "】秒");

						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								if (tableModel == null) {
									initTable(templist);
								} else {
									int row = jTable.getSelectedRow();
									tableModel.setList(templist);
									tableModel.setSelectRow(row);
								}
							}
						});

						lbShowData.setText(String.valueOf(templist.size()));

						index += length;

						temp = casAction.commonSearchPageList(new Request(
								CommonVars.getCurrUser()), index, length, null,
								billDetail, conditions, "", "");

					}
				}
				isRunning = false;
			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(DgFactoryQuery.this,
						"查询失败：！" + e.getMessage(), "提示", 2);
			} finally {
				CommonStepProgress.closeStepProgressDialog();
				btnSearch.setEnabled(true);
			}
		}
	}

	// class find extends Thread {
	// List<Condition> conditions = null;
	//
	// String orderBy = null;
	//
	// String billDetail = null;
	//
	// public find(List<Condition> conditions, String orderBy,
	// String billDetail) {
	// this.conditions = conditions;
	// this.orderBy = orderBy;
	// this.billDetail = billDetail;
	// }
	//
	// public void run() {
	// btnSearch.setEnabled(false);
	// try {
	// CommonProgress.showProgressDialog(DgFactoryQuery.this);
	// CommonProgress.setMessage("系统正获取数据，请稍后...");
	//
	// List result1 = new ArrayList();
	// if(MaterielType.MATERIEL.equals(getMaterielType())){
	// //
	// // 加入残次品出库
	// //
	// // 物料类型
	// String billDetail = BillUtil
	// .getBillDetailTableNameByMaterielType(MaterielType.BAD_PRODUCT);
	//
	// List<Condition> conditions2 = new ArrayList();
	// conditions2.addAll(conditions);
	// conditions2.add(new Condition("and", null,
	// "billMaster.billType.code", "=",
	// "5101", null)); //残次品出库 5101
	// conditions2.add(new Condition("and", "(",
	// "note", "=",
	// "料件", null)); //note == 料件
	// conditions2.add(new Condition("or", null,
	// "note is null or a.note = '' ", null,
	// null, ")"));
	// result1 = casAction.commonCount(new Request(CommonVars
	// .getCurrUser()), null, billDetail, conditions2, orderBy,
	// "");
	// }
	// result = casAction.commonCount(new Request(CommonVars
	// .getCurrUser()), null, billDetail, conditions, orderBy,
	// "");
	// result.addAll(result1);
	// CommonProgress.closeProgressDialog();
	// } catch (Exception e) {
	// CommonProgress.closeProgressDialog();
	// JOptionPane.showMessageDialog(DgFactoryQuery.this, "获取数据失败：！"
	// + e.getMessage(), "提示", 2);
	// } finally {
	// CommonProgress.closeProgressDialog();
	// if (result != null && !result.isEmpty()) {
	// initTable(result);
	// } else {
	// initTable(new ArrayList());
	// JOptionPane.showMessageDialog(DgFactoryQuery.this,
	// "没有查到符合条件的记录！", "提示！", 2);
	// }
	// }
	// btnSearch.setEnabled(true);
	// }
	// }

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
			btnPrint.setBounds(642, 33, 64, 23);
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					btnPrint.setEnabled(false);
					result = tableModel.getList();
					if (result != null && !result.isEmpty()) {

						CommonDataSource imgExgDS = new CommonDataSource(
								result, allMaximumFractionDigits);
						List<BaseCompany> company = new ArrayList<BaseCompany>();
						company.add(CommonVars.getCurrUser().getCompany());
						CommonDataSource companyDS = new CommonDataSource(
								company);
						String hsName = tfHsName.getText();

						InputStream masterReportStream = DgFactoryQuery.class
								.getResourceAsStream("report/FactoryQueryReport.jasper");
						InputStream detailReportStream = DgFactoryQuery.class
								.getResourceAsStream("report/FactoryQueryReportSubb.jasper");
						try {
							JasperReport detailReport = (JasperReport) JRLoader
									.loadObject(detailReportStream);

							Map<String, Object> parameters = new HashMap<String, Object>();
							parameters.put("imgExgDS", imgExgDS);
							parameters.put("detailReport", detailReport);
							parameters.put("reportTilte",
									DgFactoryQuery.this.getReportTilte());
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
			btnExit.setBounds(642, 59, 64, 23);
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgFactoryQuery.this.dispose();
					if (findThread != null) {
						findThread.interrupt();
					}
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
					List<TempObject> list = CasQuery.getInstance()
							.getFactoryAndFactualCustomsRalation(false,
									getMaterielType(), new ArrayList());
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel) sm.getObject();
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
					List<TempObject> list = CasQuery.getInstance()
							.getFactoryAndFactualCustomsRalation(false,
									getMaterielType(), new ArrayList());
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel) sm.getObject();
						tfEndPtNo.setText(m == null ? "" : m.getPtNo());
					}
				}
			});
		}
		return btnEndPtNo;
	}

	/**
	 * This method initializes cbCustomsNoIsNull
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCustomsNoIsNull() {
		if (cbCustomsNoIsNull == null) {
			cbCustomsNoIsNull = new JCheckBox();
			cbCustomsNoIsNull.setBounds(new Rectangle(198, 120, 121, 21));
			cbCustomsNoIsNull.setText("允许报关单号为空");
			cbCustomsNoIsNull.setSelected(true);
		}
		return cbCustomsNoIsNull;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
