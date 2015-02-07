/*
 * Created on 2004-9-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas;

import java.awt.BorderLayout;
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

import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgFactorySum extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JLabel jLabel = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JList jList = null;

	private JScrollPane jScrollPane1 = null;

	private JButton btnSearch = null;

	private JButton btnPrint = null;

	private JButton btnExit = null;

	private MaterialManageAction materialManageAction = null;

	private CasAction casAction = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel7 = null;

	private String materielType = null;  //  @jve:decl-index=0:

	private List result = null;

	private String reportTitle = null;

	private JTextField tfHsName = null;

	private JButton btnHsName = null;

	private JLabel jLabel8 = null;

	private JTextField tfHsSpec = null;

	private JButton btnHsSpec = null;

	private JTextField tfPtName = null;

	private JButton btnPtName = null;

	private JLabel jLabel9 = null;

	private JTextField tfPtSpec = null;

	private JButton btnPtSpec = null;

	private JLabel jLabel10 = null;

	private JTextField tfPtNo = null;

	private JButton btnPtNo = null;

	private JLabel jLabel11 = null;

	private JTextField tfEndPtNo = null;

	private JButton btnEndPtNo = null;

	private JCheckBox cbShowZeroStore = null;

	private static String SELECT_ALL = "全选";

	private static String SELECT_NOT_ALL = "不选";

	/**
	 * 小数位控制
	 */
	private Integer allMaximumFractionDigits = CasCommonVars.getOtherOption()
			.getAllReportInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
			.getOtherOption().getAllReportInOutMaximumFractionDigits();  //  @jve:decl-index=0:

	private JCheckBox cbShowVersion = null;
	
	/**
	 * This is the default constructor
	 */
	public DgFactorySum() {
		super(false);
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");

		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("工厂查询统计报表");
		this.setSize(733, 551);
		this.setContentPane(getJContentPane());

	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {
			initTopName();
			initUI();
			initTable(new ArrayList());
		}
		super.setVisible(isFlag);
	}

	private void initUI() {

		// 初始化仓库
		Vector<Object> vector = new Vector<Object>();
		List list4 = materialManageAction.findWareSet(new Request(CommonVars
				.getCurrUser(), true));
		for (int i = 0; i < list4.size(); i++) {
			if (i == 0) {
				vector.add(new CheckableItemExtra(SELECT_ALL));
			}
			WareSet obj = (WareSet) list4.get(i);
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
		//只有成品统计时才显示 是否分版本统计 选项
		cbShowVersion.setVisible(MaterielType.FINISHED_PRODUCT.equals(getMaterielType()));
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
			return "" + name + " (" + code + ")";
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

	private void initTable(final List list) {
		JTableListModel tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter(allMaximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
//						list.add(addColumn("商品编码", "bill9", 100));// 商品编码
						list.add(addColumn("工厂料号" 
								+ ((MaterielType.FINISHED_PRODUCT.equals(getMaterielType()) && cbShowVersion.isSelected())? "(版本号)":""),
										"bill1", 100));
						list.add(addColumn("商品名称", "bill2", 100));
						list.add(addColumn("型号规格", "bill3", 100));

						list.add(addColumn("数量", "billSum1", 60));
						list.add(addColumn("单位", "bill4", 60));
						list.add(addColumn("折算报关数量", "billSum2", 80));

						list.add(addColumn("仓库名称", "bill5", 80));
						list.add(addColumn("报关单位", "bill6", 80));
						list.add(addColumn("报关名称", "bill7", 80));
						list.add(addColumn("报关型号规格", "bill8", 100));
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
			jPanel = new JPanel();
			jLabel = new JLabel();
//			jPanel.setLayout(new BorderLayout());
//			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
//			jPanel
//					.setBorder(javax.swing.BorderFactory
//							.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
//			jPanel.setBackground(java.awt.Color.white);
//			jLabel3.setText("");
//			jLabel3.setIcon(new ImageIcon(getClass().getResource(
//					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
//			jLabel6.setText("");
//			jLabel6
//					.setIcon(new ImageIcon(
//							getClass()
//									.getResource(
//											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
//			jPanel.add(jLabel, java.awt.BorderLayout.CENTER);
//			jPanel.add(jLabel3, java.awt.BorderLayout.EAST);
//			jPanel.add(jLabel6, java.awt.BorderLayout.WEST);
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
			jSplitPane.setDividerSize(2);
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
			jLabel11 = new JLabel();
			jLabel11.setBounds(new java.awt.Rectangle(378, 77, 53, 21));
			jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel11.setText("结束料号");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new java.awt.Rectangle(187, 77, 51, 22));
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setText("开始料号");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new java.awt.Rectangle(379, 54, 51, 22));
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setText("工厂规格");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new java.awt.Rectangle(379, 31, 51, 22));
			jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel8.setText("报关规格");
			jLabel7 = new JLabel();
			jLabel5 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBorder(javax.swing.BorderFactory
					.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jLabel1.setText("日期从:");
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel1.setBounds(187, 6, 51, 22);
			jLabel2.setBounds(379, 6, 51, 22);
			jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel2.setText("到：");
			jLabel4.setBounds(13, 3, 26, 19);
			jLabel4.setText("仓库");
			jLabel5.setBounds(187, 31, 51, 22);
			jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel5.setText("报关名称");
			jLabel7.setBounds(187, 54, 51, 22);
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel7.setText("工厂名称");
			jPanel2.add(jLabel1, null);

			jPanel2.add(getCbbBeginDate(), null);
			jPanel2.add(getTfHsName(), null);
			jPanel2.add(getBtnHsName(), null);
			jPanel2.add(getTfHsSpec(), null);
			jPanel2.add(getBtnHsSpec(), null);
			jPanel2.add(getTfPtNo(), null);
			jPanel2.add(getBtnPtNo(), null);
			jPanel2.add(getCbbEndDate(), null);
			jPanel2.add(getTfPtName(), null);
			jPanel2.add(getBtnPtName(), null);
			jPanel2.add(getTfPtSpec(), null);
			jPanel2.add(getBtnPtSpec(), null);
			jPanel2.add(getBtnSearch(), null);
			jPanel2.add(getBtnPrint(), null);
			jPanel2.add(getBtnExit(), null);

			jPanel2.add(jLabel10, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getJScrollPane1(), null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(jLabel7, null);
			jPanel2.add(jLabel8, null);
			jPanel2.add(jLabel9, null);

			jPanel2.add(jLabel11, null);
			jPanel2.add(getTfEndPtNo(), null);
			jPanel2.add(getBtnEndPtNo(), null);
			jPanel2.add(getCbShowZeroStore(), null);
			jPanel2.add(getCbShowVersion(), null);
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

		if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
			jLabel.setText("成品仓库库存情况表");
			this.setReportTitle("成品仓库库存情况表");
		} else if (MaterielType.MATERIEL.equals(materielType)) {
			jLabel.setText("料件仓库库存情况表");
			this.setReportTitle("料件仓库库存情况表");
		} else if (MaterielType.MACHINE.equals(materielType)) {
			jLabel.setText("设备仓库库存情况表");
			this.setReportTitle("设备仓库库存情况表");

		} else if (MaterielType.SEMI_FINISHED_PRODUCT.equals(materielType)) {
			jLabel.setText("半成品仓库库存情况表");
			this.setReportTitle("半成品仓库库存情况表");

		} else if (MaterielType.REMAIN_MATERIEL.equals(materielType)) {
			jLabel.setText("边角料仓库库存情况表");
			this.setReportTitle("边角料仓库库存情况表");

		} else if (MaterielType.BAD_PRODUCT.equals(materielType)) {
			jLabel.setText("残次品仓库库存情况表");
			this.setReportTitle("残次品仓库库存情况表");
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
			cbbBeginDate.setBounds(240, 6, 122, 22);
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
			cbbEndDate.setBounds(432, 6, 122, 22);
		}
		return cbbEndDate;
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

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

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
				setFont(new Font(getFont().getName(),Font.BOLD, getFont().getSize()));
				setText("  " + item.getName());
			} else if (value instanceof CheckableItem) {
				CheckableItem item = (CheckableItem) value;
				setSelected(item.isSelected());
				setFont(new Font(getFont().getName(),Font.PLAIN, getFont().getSize()));
				setSize(350, 17);
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
			jScrollPane1.setBounds(42, 3, 129, 120);
			jScrollPane1.setViewportView(getJList());
		}
		return jScrollPane1;
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
			btnSearch.setBounds(591, 6, 70, 24);
			btnSearch.setText("查询");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<Condition> conditions = new ArrayList<Condition>();
					if (!setTipMessage()) {
						return;
					}
					setConditions(conditions);
					materielType = getMaterielType();
					new find(conditions).start();
				}
			});

		}
		return btnSearch;
	}

	class find extends Thread {
		List<Condition> conditions = null;

		public find(List<Condition> conditions) {
			this.conditions = conditions;
		}

		public void run() {
			try {
				CommonProgress.showProgressDialog(DgFactorySum.this);
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				result = casAction.getCasSum(new Request(CommonVars
						.getCurrUser()), materielType, conditions,
						cbShowZeroStore.isSelected(),cbShowVersion.isSelected());
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgFactorySum.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				CommonProgress.closeProgressDialog();
				if (result != null && !result.isEmpty()) {
					initTable(result);
				} else {
					initTable(new ArrayList());
					JOptionPane.showMessageDialog(DgFactorySum.this,
							"没有查到符合条件的记录！", "提示！", 2);
				}
			}
		}
	}

	private void setConditions(List<Condition> conditions) {
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				new Boolean(true), null));
		if (cbbBeginDate.getDate() != null) // 开始日期
			conditions.add(new Condition("and", null, "billMaster.validDate",
					">=", CommonVars.dateToStandDate(cbbBeginDate.getDate()),
					null));
		if (cbbEndDate.getDate() != null) // 结束日期
			conditions.add(new Condition("and", null, "billMaster.validDate",
					"<=", CommonVars.dateToStandDate(cbbEndDate.getDate()),
					null));
		if (!tfHsName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "hsName", "=", tfHsName
					.getText(), null));
		}
		if (!tfHsSpec.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "hsSpec", "=", tfHsSpec
					.getText(), null));
		}
		if (!tfPtName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "ptName", "=", tfPtName
					.getText(), null));
		}
		if (!tfPtSpec.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "ptSpec", "=", tfPtSpec
					.getText(), null));
		}
		// 工厂料号不等于空,结束料号为空时
		if (!tfPtNo.getText().trim().equals("")
				&& tfEndPtNo.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "ptPart", "=", tfPtNo
					.getText(), null));
		} else // 工厂料号不等于空,结束料号不为空时
		if (!tfPtNo.getText().trim().equals("")
				&& !tfEndPtNo.getText().trim().equals("")) {
			conditions.add(new Condition("and", "(", "ptPart", ">=", tfPtNo
					.getText(), null));
			conditions.add(new Condition("and", null, "ptPart", "<=", tfEndPtNo
					.getText(), ")"));
		}
		// 仓库
		List<CheckableItem> checkableItemList = new ArrayList<CheckableItem>();
		for (int j = 0; j < this.jList.getModel().getSize(); j++) {
			Object value = this.jList.getModel().getElementAt(j);
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
				condition = new Condition("and", "(", "wareSet.code", "=", item
						.getCode(), null);
			} else {
				condition = new Condition("or", null, "wareSet.code", "=", item
						.getCode(), null);
			}
			if (j == checkableItemList.size() - 1) {
				condition.setEndBracket(")");
			}
			conditions.add(condition);
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
			btnPrint.setBounds(591, 34, 69, 23);
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (result != null && !result.isEmpty()) {
						CommonDataSource imgExgDS = new CommonDataSource(result,allMaximumFractionDigits);
						List<BaseCompany> company = new ArrayList<BaseCompany>();
						company.add(CommonVars.getCurrUser().getCompany());
						CommonDataSource companyDS = new CommonDataSource(
								company);

						InputStream masterReportStream = DgFactoryQuery.class
								.getResourceAsStream("report/FactorySumReport.jasper");
						InputStream detailReportStream = DgFactoryQuery.class
								.getResourceAsStream("report/FactorySumReportSubb.jasper");
						try {
							JasperReport detailReport = (JasperReport) JRLoader
									.loadObject(detailReportStream);

							Map<String, Object> parameters = new HashMap<String, Object>();
							parameters.put("imgExgDS", imgExgDS);
							parameters.put("detailReport", detailReport);
							parameters.put("reportTilte", DgFactorySum.this
									.getReportTitle());
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
			btnExit.setBounds(591, 61, 69, 23);
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgFactorySum.this.dispose();

				}
			});

		}
		return btnExit;
	}

	/**
	 * 提示信息
	 * 
	 */
	private boolean setTipMessage() {

		if (tfHsName.getText().trim().equals("")) {
			if (JOptionPane.showConfirmDialog(DgFactorySum.this,
					"报关名称为空!!\n查询可能要较长的时间，要继续吗??", "确认",
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
	 * @return Returns the reportTitle.
	 */
	public String getReportTitle() {
		return reportTitle;
	}

	/**
	 * @param reportTitle
	 *            The reportTitle to set.
	 */
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	/**
	 * This method initializes tfHsName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsName() {
		if (tfHsName == null) {
			tfHsName = new JTextField();
			tfHsName.setBounds(new java.awt.Rectangle(240, 31, 103, 22));
		}
		return tfHsName;
	}

	/**
	 * This method initializes btnHsName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnHsName() {
		if (btnHsName == null) {
			btnHsName = new JButton();
			btnHsName.setBounds(new java.awt.Rectangle(343, 31, 19, 20));
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
	 * This method initializes tfHsSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsSpec() {
		if (tfHsSpec == null) {
			tfHsSpec = new JTextField();
			tfHsSpec.setBounds(new java.awt.Rectangle(432, 30, 103, 22));
		}
		return tfHsSpec;
	}

	/**
	 * This method initializes btnHsSpec
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnHsSpec() {
		if (btnHsSpec == null) {
			btnHsSpec = new JButton();
			btnHsSpec.setBounds(new java.awt.Rectangle(535, 31, 19, 20));
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
	 * This method initializes tfPtName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtName() {
		if (tfPtName == null) {
			tfPtName = new JTextField();
			tfPtName.setBounds(new java.awt.Rectangle(240, 54, 103, 22));
		}
		return tfPtName;
	}

	/**
	 * This method initializes btnPtName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPtName() {
		if (btnPtName == null) {
			btnPtName = new JButton();
			btnPtName.setBounds(new java.awt.Rectangle(343, 55, 19, 20));
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
	 * This method initializes tfPtSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtSpec() {
		if (tfPtSpec == null) {
			tfPtSpec = new JTextField();
			tfPtSpec.setBounds(new java.awt.Rectangle(432, 54, 103, 22));
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
			btnPtSpec.setBounds(new java.awt.Rectangle(535, 55, 19, 20));
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
	 * This method initializes tfPtNO
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtNo() {
		if (tfPtNo == null) {
			tfPtNo = new JTextField();
			tfPtNo.setBounds(new java.awt.Rectangle(241, 77, 101, 21));
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
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPtNo() {
		if (btnPtNo == null) {
			btnPtNo = new JButton();
			btnPtNo.setBounds(new java.awt.Rectangle(343, 77, 19, 20));
			btnPtNo.setText("...");
			btnPtNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalation(false, getMaterielType(),
							new ArrayList());
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel)sm.getObject();
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
			tfEndPtNo.setBounds(new java.awt.Rectangle(432, 77, 103, 21));
			tfEndPtNo.setEditable(false);
		}
		return tfEndPtNo;
	}

	/**
	 * This method initializes btnEndPtNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEndPtNo() {
		if (btnEndPtNo == null) {
			btnEndPtNo = new JButton();
			btnEndPtNo.setBounds(new java.awt.Rectangle(535, 77, 19, 20));
			btnEndPtNo.setText("...");
			btnEndPtNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalation(false, getMaterielType(),
							new ArrayList());
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel)sm.getObject();
						tfEndPtNo.setText(m == null ? "" : m.getPtNo());
					}					
				}
			});
			btnEndPtNo.setEnabled(false);
		}
		return btnEndPtNo;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbShowZeroStore() {
		if (cbShowZeroStore == null) {
			cbShowZeroStore = new JCheckBox();
			cbShowZeroStore
					.setBounds(new java.awt.Rectangle(182, 102, 194, 20));
			cbShowZeroStore.setSelected(true);
			cbShowZeroStore
					.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			cbShowZeroStore
					.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
			cbShowZeroStore.setText("是否显示结存数量为 0 的记录");
		}
		return cbShowZeroStore;
	}

	/**
	 * This method initializes cbShowVersion	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbShowVersion() {
		if (cbShowVersion == null) {
			cbShowVersion = new JCheckBox();
			cbShowVersion.setBounds(new Rectangle(435, 102, 117, 21));
			cbShowVersion.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
			cbShowVersion.setText("是否分版本统计");
		}
		return cbShowVersion;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
