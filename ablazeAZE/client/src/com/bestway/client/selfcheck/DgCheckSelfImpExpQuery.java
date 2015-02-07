/*
 * Created on 2004-9-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.selfcheck;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.action.CasCheckAction;
import com.bestway.bcus.cas.entity.ImportExportInfo;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.invoice.entity.SemiProductInfo;
import com.bestway.bcus.cas.invoice.entity.TempThatDayBalance;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CheckBoxListCellRenderer;
import com.bestway.bcus.client.common.CheckBoxListItem;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.SteppedMetalComboBoxUI;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.CommonUtils;
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
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgCheckSelfImpExpQuery extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JCalendarComboBox startDate = null;
	private JCalendarComboBox endDate = null;
	private JCheckBox cbPrintCover = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel6 = null;
	private JPanel jPanel7 = null;
	private JComboBox cmbCustomer = null;
	private JComboBox cmbWareSet = null;
	private JTextField tfName = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JButton btnQuery = null;
	private JButton btnPrint = null;
	private JPopupMenu jPopupMenuPrint = null;
	private JMenuItem printPartNumberGroups = null;
	private JMenuItem printDeclareNameGroups = null;
	private CasCheckAction casAction = null;
	private String materialType;  //  @jve:decl-index=0:
	private JTableListModel tableModel = null;
	private List impExpInfos = null;
	private MaterialManageAction materialManageAction = null;
	private JButton btnName = null;
	private JLabel jLabel11 = null;
	private JTextField tfPtNo = null;
	private JButton btnPtNo = null;
	private JLabel jLabel12 = null;
	private JTextField tfEndPtNo = null;
	private JButton btnEndPtNo = null;
	private JCheckBox cbIsSplitBomVersion = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JTextField tfHsName = null;
	private JButton btnHsName = null;
	private JTextField tfHsSpec = null;
	private JButton btnHsSpec = null;
	public JLabel lbCutomsName=null;
	/**
	 * 小数位控制
	 */
	private Integer maximumFractionDigits = CasCommonVars.getOtherOption()
			.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
			.getOtherOption().getInOutMaximumFractionDigits(); // @jve:decl-index=0:
	private JLabel jLabel2 = null;
	private JTextField tfBillNo = null;
	private JCheckBox cbIsTotalFrontAmount = null;
	private JLabel jLabel3 = null;
	private JTextField tfProNo = null;
	private JCheckBox cbOnlyMinus = null;
	private JButton btnExit1 = null;
	private JLabel jLabel4 = null;
	private JTextField tfCustomsUnit = null;
	private static String str = ""; // @jve:decl-index=0:
	private JCheckBox cbGroupByWarehouse = null;
	private boolean isWaiFa = false;
	//关联查询时传递 的参数
	private Date sDate;
	private String sHsCode;
	private String sHsName;
	private String sHsSpec;
	private String sPtNo;
	private String sPtName;
	private String sPtSpec;

	/**
	 * This is the default constructor
	 */
	public DgCheckSelfImpExpQuery() {
		super(false);
		casAction = (CasCheckAction) CommonVars.getApplicationContext()
				.getBean("casCheckAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();
		initUIComponents();
	}
	
	public void setVisible(boolean b){
		if(b){
			if (materialType.equalsIgnoreCase(MaterielType.FINISHED_PRODUCT)) {
				this.cbIsSplitBomVersion.setEnabled(true);
				this.cbIsSplitBomVersion.setSelected(true);
			} else {
				this.cbIsSplitBomVersion.setEnabled(false);
				this.cbIsSplitBomVersion.setSelected(false);
			}
			initTable(new ArrayList());
		}
		super.setVisible(b);
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	private void initUIComponents() {
		fillCustomer();
		fillWareSet();
		int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
				.getYearInt();
		Calendar beginClarendar = Calendar.getInstance();
		beginClarendar.set(Calendar.YEAR, year);
		beginClarendar.set(Calendar.MONTH, 0);
		beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
		beginClarendar.set(Calendar.HOUR_OF_DAY, 0);
		beginClarendar.set(Calendar.MINUTE, 0);
		beginClarendar.set(Calendar.SECOND, 0);
		this.startDate.setDate(beginClarendar.getTime());
		this.startDate.setCalendar(beginClarendar);

		Calendar endClarendar = Calendar.getInstance();
		endClarendar.set(Calendar.YEAR, year);
		endClarendar.set(Calendar.MONTH, 11);
		endClarendar.set(Calendar.DAY_OF_MONTH, 31);
		beginClarendar.set(Calendar.HOUR_OF_DAY, 23);
		beginClarendar.set(Calendar.MINUTE, 59);
		beginClarendar.set(Calendar.SECOND, 59);
		this.endDate.setDate(endClarendar.getTime());
		this.endDate.setCalendar(endClarendar);
		cbIsTotalFrontAmount.setVisible(false);
		cbGroupByWarehouse.setVisible(false);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("进出口查询");
		this.setSize(769, 541);
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (startDate == null) {
			startDate = new JCalendarComboBox();
			startDate.setSize(new Dimension(157, 22));
			startDate.setLocation(new Point(58, 106));
		}
		return startDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (endDate == null) {
			endDate = new JCalendarComboBox();
			endDate.setLocation(277, 106);
			endDate.setSize(157, 22);
		}
		return endDate;
	}
	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbPrintCover() {
		if (cbPrintCover == null) {
			cbPrintCover = new JCheckBox();
			cbPrintCover.setText("打印封面");
			cbPrintCover.setLocation(new Point(276, 132));
			cbPrintCover.setSize(new Dimension(76, 20));
		}
		return cbPrintCover;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(1);
			jSplitPane.setDividerLocation(160);
			jSplitPane.setTopComponent(getJPanel7());
			jSplitPane.setBottomComponent(getJPanel6());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setLayout(new BorderLayout());
			jPanel6.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("报关单位");
			jLabel4.setSize(new Dimension(48, 22));
			jLabel4.setLocation(new Point(440, 51));
			jLabel3 = new JLabel();
			jLabel3.setText("制单号");
			jLabel3.setLocation(new Point(452, 106));
			jLabel3.setSize(new Dimension(36, 22));
			jLabel2 = new JLabel();
			jLabel2.setText("   单据号");
			jLabel2.setSize(new Dimension(48, 22));
			jLabel2.setLocation(new Point(440, 79));
			jLabel1 = new JLabel();
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel1.setBounds(new Rectangle(216, 51, 62, 22));
			jLabel1.setText("报关规格");
			jLabel = new JLabel();
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel.setLocation(new Point(10, 51));
			jLabel.setSize(new Dimension(48, 22));
			jLabel.setText("报关名称");
			jLabel12 = new JLabel();
			jLabel12.setText("止");
			jLabel12.setLocation(new Point(454, 20));
			jLabel12.setSize(new Dimension(15, 22));
			jLabel11 = new JLabel();
			jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel11.setSize(new Dimension(60, 22));
			jLabel11.setLocation(new Point(216, 20));
			jLabel11.setText("工厂料号从");
			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();
			lbCutomsName = new JLabel();
			jPanel7 = new JPanel();
			jPanel7.setLayout(null);
			lbCutomsName.setText("客户名称");
			lbCutomsName.setSize(new Dimension(48, 22));
			lbCutomsName.setLocation(new Point(10, 79));
			lbCutomsName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel8.setText("仓库");
			jLabel8.setBounds(new Rectangle(252, 79, 25, 22));
			jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setText("工厂名称");
			jLabel9.setBounds(new Rectangle(10, 20, 48, 22));
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setText("日期 从");
			jLabel10.setLocation(new Point(10, 106));
			jLabel10.setSize(new Dimension(48, 22));
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jPanel7
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"过滤条件",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.PLAIN, 12),
									new java.awt.Color(51, 51, 51)));
			jLabel5.setText("止");
			jLabel5.setBounds(new Rectangle(245, 106, 16, 22));
			jPanel7.add(getJComboBox1(), null);
			jPanel7.add(getJComboBox2(), null);
			jPanel7.add(getJComboBox3(), null);
			jPanel7.add(lbCutomsName, null);
			jPanel7.add(jLabel8, null);
			jPanel7.add(jLabel9, null);
			jPanel7.add(getJCalendarComboBox(), null);
			jPanel7.add(getJCalendarComboBox1(), null);
			jPanel7.add(getJButton(), null);
			// jPanel7.add(getBtnPrint(), null);
			jPanel7.add(getBtnPrint(), null);
			jPanel7.add(jLabel10, null);
			jPanel7.add(jLabel5, null);
			jPanel7.add(getBtnName(), null);
			jPanel7.add(jLabel11, null);
			jPanel7.add(getTfPtNo(), null);
			jPanel7.add(getBtnPtNo(), null);
			jPanel7.add(jLabel12, null);
			jPanel7.add(getTfEndPtNo(), null);
			jPanel7.add(getJButton3(), null);
			jPanel7.add(getCbPrintCover(), null);
			jPanel7.add(getCbIsSplitBomVersion(), null);
			jPanel7.add(jLabel, null);
			jPanel7.add(jLabel1, null);
			jPanel7.add(getTfHsName(), null);
			jPanel7.add(getBtnHsName(), null);
			jPanel7.add(getTfHsSpec(), null);
			jPanel7.add(getBtnHsSpec(), null);
			jPanel7.add(jLabel2, null);
			jPanel7.add(getTfBillNo(), null);
			jPanel7.add(getCbIsTotalFrontAmount(), null);
			jPanel7.add(jLabel3, null);
			jPanel7.add(getTfProNo(), null);
			jPanel7.add(getCbOnlyMinus(), null);
			jPanel7.add(getBtnExit1(), null);
			jPanel7.add(jLabel4, null);
			jPanel7.add(getTfCustomsUnit(), null);
			jPanel7.add(getCbGroupByWarehouse(), null);
		}
		return jPanel7;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (cmbCustomer == null) {
			cmbCustomer = new JComboBox();
			cmbCustomer.setSize(new Dimension(157, 22));
			cmbCustomer.setLocation(new Point(58, 79));
		}
		return cmbCustomer;
	}

	public boolean isWaiFa() {
		return isWaiFa;
	}

	public void setWaiFa(boolean isWaiFa) {
		this.isWaiFa = isWaiFa;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox2() {
		if (cmbWareSet == null) {
			cmbWareSet = new JComboBox();
			cmbWareSet.setSize(new Dimension(157, 22));
			cmbWareSet.setLocation(new Point(277, 79));
		}
		return cmbWareSet;
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JTextField getJComboBox3() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(new Rectangle(58, 20, 140, 22));
		}
		return tfName;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable() {
				protected JTableHeader createDefaultTableHeader() {
					return new GroupableTableHeader(columnModel);
				}
			};
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() >= 2) {
						editData();
					}
				}
			});
		}
		return jTable;
	}

	private void editData() {

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
	private JButton getJButton() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setBounds(654, 12, 86, 25);
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
		System.out.println("maximumFractionDigits="
				+ maximumFractionDigits);
		if (startDate.getDate() == null
				|| endDate.getDate() == null) {
			JOptionPane.showMessageDialog(
					DgCheckSelfImpExpQuery.this,
					"查询数据开始日期或结束日期不可为空!", "提示", 2);
			return;
		}
		List<Condition> conditions1 = null;
		List<Condition> conditions2 = null;
		if (!MaterielType.SEMI_FINISHED_PRODUCT
				.equals(materialType))
			conditions1 = getConditions();
		else {
			conditions2 = getMaterialCondtions();
			conditions1 = getSemiFinishedProductCondtions();
		}
		new searchThread(conditions1, conditions2, materialType)
				.start();
	}

	protected List<Condition> getSemiFinishedProductCondtions() {
		List<Condition> conditions = new ArrayList<Condition>();
		List<CheckBoxListItem> checkableItemList = new ArrayList<CheckBoxListItem>();

		//
		// ware set
		//
		checkableItemList.clear();
		for (int j = 0; j < this.cmbWareSet.getModel().getSize(); j++) {
			CheckBoxListItem item = (CheckBoxListItem) this.cmbWareSet
					.getModel().getElementAt(j);
			if (item.getIsSelected()) {
				checkableItemList.add(item);
			}
		}
		for (int j = 0; j < checkableItemList.size(); j++) {
			CheckBoxListItem item = checkableItemList.get(j);
			Condition condition = null;
			if (j == 0) {
				condition = new Condition("and", "(", "wareSet.code", "=", item
						.getCode().trim(), null);
			} else {
				condition = new Condition("or", null, "wareSet.code", "=", item
						.getCode().trim(), null);
			}
			if (j == checkableItemList.size() - 1) {
				condition.setEndBracket(")");
			}
			conditions.add(condition);
		}

		if (!"".equals(tfBillNo.getText().trim())) {
			conditions.add(new Condition("and", null, "billMaster.billNo", "=",
					tfBillNo.getText().trim(), null));
		}
		// if (cmbBillType.getSelectedItem() != null) {
		// conditions.add(new Condition("and", null, "billMaster.billType",
		// "=", cmbBillType.getSelectedItem(), null));
		// }
		if (cmbCustomer.getSelectedItem() != null) {
			conditions.add(new Condition("and", null, "billMaster.scmCoc", "=",
					cmbCustomer.getSelectedItem(), null));
		}
		// 设置时间
		if (startDate.getDate() != null) {
			if (this.cbIsTotalFrontAmount.isSelected()) {
				int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
						.getYearInt();
				Calendar beginClarendar = Calendar.getInstance();
				beginClarendar.set(Calendar.YEAR, year);
				beginClarendar.set(Calendar.MONTH, 0);
				beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
				beginClarendar.set(Calendar.HOUR_OF_DAY, 0);
				beginClarendar.set(Calendar.MINUTE, 0);
				beginClarendar.set(Calendar.SECOND, 0);
				beginClarendar.set(Calendar.MILLISECOND, 0);
				conditions.add(new Condition("and", null,
						"billMaster.validDate", ">=", beginClarendar.getTime(),
						null));
				System.out.println("统计上期结存时间为:" + beginClarendar.getTime());
			} else {
				Date date = startDate.getDate();
				Calendar beginClarendar = Calendar.getInstance();
				beginClarendar.setTime(date);
				beginClarendar.set(Calendar.HOUR, 0);
				beginClarendar.set(Calendar.MINUTE, 0);
				beginClarendar.set(Calendar.SECOND, 0);
				beginClarendar.set(Calendar.MILLISECOND, 0);
				conditions.add(new Condition("and", null,
						"billMaster.validDate", ">=", beginClarendar.getTime(),
						null));
			}
		}
		if (endDate.getDate() != null) {
			Date date = endDate.getDate();
			conditions.add(new Condition("and", null, "billMaster.validDate",
					"<=", CommonUtils.getEndDate(date), null));
		}
		if (tfProNo.getText() != null && (!tfProNo.getText().trim().equals(""))) {
			conditions.add(new Condition("and", null, "productNo", "=", tfProNo
					.getText(), null));
		}
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		return conditions;
	}

	protected List<Condition> getMaterialCondtions() {
		List<Condition> conditions = new ArrayList<Condition>();
		// 设置工厂名称
		if (tfName.getText() != null && !tfName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "materiel.factoryName",
					" = ", tfName.getText(), null));
		}
		// 工厂料号不等于空,结束料号为空时
		if (!tfPtNo.getText().trim().equals("")
				&& tfEndPtNo.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "materiel.ptNo", "=",
					tfPtNo.getText(), null));
		} else // 工厂料号不等于空,结束料号不为空时
		if (!tfPtNo.getText().trim().equals("")
				&& !tfEndPtNo.getText().trim().equals("")) {
			conditions.add(new Condition("and", "(", "materiel.ptNo", ">=",
					tfPtNo.getText(), null));
			conditions.add(new Condition("and", null, "materiel.ptNo", "<=",
					tfEndPtNo.getText(), ")"));
		}

		if (!tfHsName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null,
					"statCusNameRelationHsn.cusName", "=", tfHsName.getText(),
					null));
			System.out.println("hsName"+tfHsName.getText());
			//
			// 如果spec is null
			//
			// if (tfHsSpec.getText().trim().equals("")) {
			// conditions.add(new Condition("and", "(",
			// "materiel.ptSpec is null or a.materiel.ptSpec = ''", null, null,
			// ")"));
			// }
		}
		if (!tfHsSpec.getText().trim().equals("")) {
			conditions.add(new Condition("and", null,
					"statCusNameRelationHsn.cusSpec", "=", tfHsSpec.getText(),
					null));
		}

		if (!tfCustomsUnit.getText().trim().equals("")) {
			conditions.add(new Condition("and", null,
					"statCusNameRelationHsn.cusUnit.name", "=", tfCustomsUnit
							.getText(), null));
		}
		return conditions;
	}

	class searchThread extends Thread {
		List<Condition> conditions1 = null;
		List<Condition> conditions2 = null;
		String materialType = null;

		public searchThread(List<Condition> conditions1,
				List<Condition> conditions2, String materialType) {
			this.conditions1 = conditions1;
			this.conditions2 = conditions2;
			this.materialType = materialType;
		}

		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			btnQuery.setEnabled(false);
			try {
				CommonProgress.showProgressDialog(flag,
						DgCheckSelfImpExpQuery.this);
				CommonProgress.setMessage(flag, "系统正获取数据，请稍后...");
				if (cbIsTotalFrontAmount.isSelected()) {
					impExpInfos = casAction.findImpExpInfos(isWaiFa,
							new Request(CommonVars.getCurrUser()),
							materialType, conditions1, conditions2,
							cbIsSplitBomVersion.isSelected(), startDate
									.getDate());
					System.out.println("impExpInfos.size()="
							+ impExpInfos.size());
				} else {
					impExpInfos = casAction.findImpExpInfos(isWaiFa,
							new Request(CommonVars.getCurrUser()),
							materialType, conditions1, conditions2,
							cbIsSplitBomVersion.isSelected());
					System.out.println("impExpInfos.size()="
							+ impExpInfos.size());
				}
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						// 只显示负数
						showLessthanZero(cbOnlyMinus.isSelected());
					}
				});
				CommonProgress.closeProgressDialog(flag);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgCheckSelfImpExpQuery.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
				initTable(new ArrayList());
			}
			btnQuery.setEnabled(true);
		}
	}

	private void showLessthanZero(boolean isShow) {
		if (impExpInfos == null || impExpInfos.size() == 0) {
			initTable(new ArrayList());
			return;
		}
		if (isShow) {
			List tempList = new ArrayList();
			if (!materialType.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
				for (int i = 0; i < impExpInfos.size(); i++) {
					ImportExportInfo infoTemp = (ImportExportInfo) impExpInfos
							.get(i);
					if ((infoTemp.getRsHsAmount() == null ? 0.0 : infoTemp
							.getRsHsAmount()) < 0
							|| (infoTemp.getRsPtAmount() == null ? 0.0
									: infoTemp.getRsPtAmount()) < 0) {
						tempList.add(infoTemp);
					}
				}
			} else {
				for (int i = 0; i < impExpInfos.size(); i++) {
					SemiProductInfo infoTemp = (SemiProductInfo) impExpInfos
							.get(i);
					if ((infoTemp.getRsHsAmount() == null ? 0.0 : infoTemp
							.getRsHsAmount()) < 0
							|| (infoTemp.getRsPtAmount() == null ? 0.0
									: infoTemp.getRsPtAmount()) < 0) {
						tempList.add(infoTemp);
					}
				}
			}
			initTable(tempList);
		} else {
			initTable(impExpInfos);
		}
	}

	private List getConditions() {
		List<Condition> conditions = new ArrayList<Condition>();
		List<CheckBoxListItem> checkableItemList = new ArrayList<CheckBoxListItem>();

		//
		// ware set
		//
		checkableItemList.clear();
		for (int j = 0; j < this.cmbWareSet.getModel().getSize(); j++) {
			CheckBoxListItem item = (CheckBoxListItem) this.cmbWareSet
					.getModel().getElementAt(j);
			if (item.getIsSelected()) {
				checkableItemList.add(item);
			}
		}
		for (int j = 0; j < checkableItemList.size(); j++) {
			CheckBoxListItem item = checkableItemList.get(j);
			Condition condition = null;
			if (j == 0) {
				condition = new Condition("and", "(", "wareSet.code", "=", item
						.getCode().trim(), null);
			} else {
				condition = new Condition("or", null, "wareSet.code", "=", item
						.getCode().trim(), null);
			}
			if (j == checkableItemList.size() - 1) {
				condition.setEndBracket(")");
			}
			conditions.add(condition);
		}

		if (!"".equals(tfBillNo.getText().trim())) {
			conditions.add(new Condition("and", null, "billMaster.billNo", "=",
					tfBillNo.getText().trim(), null));
		}
		// if (cmbBillType.getSelectedItem() != null) {
		// conditions.add(new Condition("and", null, "billMaster.billType",
		// "=", cmbBillType.getSelectedItem(), null));
		// }
		if (cmbCustomer.getSelectedItem() != null) {
			conditions.add(new Condition("and", null, "billMaster.scmCoc", "=",
					cmbCustomer.getSelectedItem(), null));
		}
		// 设置时间
		if (startDate.getDate() != null) {
			if (this.cbIsTotalFrontAmount.isSelected()) {
				int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
						.getYearInt();
				Calendar beginClarendar = Calendar.getInstance();
				beginClarendar.set(Calendar.YEAR, year);
				beginClarendar.set(Calendar.MONTH, 0);
				beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
				beginClarendar.set(Calendar.HOUR_OF_DAY, 0);
				beginClarendar.set(Calendar.MINUTE, 0);
				beginClarendar.set(Calendar.SECOND, 0);
				beginClarendar.set(Calendar.MILLISECOND, 0);
				conditions.add(new Condition("and", null,
						"billMaster.validDate", ">=", beginClarendar.getTime(),
						null));
				System.out.println("统计上期结存时间为:" + beginClarendar.getTime());
			} else {
				Date date = startDate.getDate();
				Calendar beginClarendar = Calendar.getInstance();
				beginClarendar.setTime(date);
				beginClarendar.set(Calendar.HOUR, 0);
				beginClarendar.set(Calendar.MINUTE, 0);
				beginClarendar.set(Calendar.SECOND, 0);
				beginClarendar.set(Calendar.MILLISECOND, 0);
				conditions.add(new Condition("and", null,
						"billMaster.validDate", ">=", beginClarendar.getTime(),
						null));
			}
		}
		if (endDate.getDate() != null) {
			Date date = endDate.getDate();
			conditions.add(new Condition("and", null, "billMaster.validDate",
					"<=", CommonUtils.getEndDate(date), null));
		}
		// 设置工厂名称
		if (tfName.getText() != null && !tfName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "ptName", " = ", tfName
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
		if (!tfHsName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "hsName", "=", tfHsName
					.getText(), null));
			
		}
		if (!tfHsSpec.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "hsSpec", "=", tfHsSpec
					.getText(), null));
		}

		if (!tfCustomsUnit.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "hsUnit.name", "=",
					tfCustomsUnit.getText(), null));
		}

		if (tfProNo.getText() != null && (!tfProNo.getText().trim().equals(""))) {
			conditions.add(new Condition("and", null, "productNo", "=", tfProNo
					.getText(), null));
		}
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		return conditions;
	}

	/**
	 * This method initializes jPopupMenuPrint
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenuPrint() {
		if (jPopupMenuPrint == null) {
			jPopupMenuPrint = new JPopupMenu();
			jPopupMenuPrint.setSize(new Dimension(110, 36));
			jPopupMenuPrint
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPopupMenuPrint.add(getPrintPartNumberGroups());
			jPopupMenuPrint.add(getPrintDeclareNameGroups());

		}
		return jPopupMenuPrint;
	}

	/**
	 * This method initializes btnAddFinishProduct
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setBounds(new Rectangle(654, 55, 86, 25));
			btnPrint.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// printReport();
					// BcsClientHelper.getInstance().printReport(DgContract.this,contract,
					// cbbPrint);
					getJPopupMenuPrint()
							.show(btnPrint, 0, btnPrint.getHeight());
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JMenuItem getPrintPartNumberGroups() {
		if (printPartNumberGroups == null) {
			printPartNumberGroups = new JMenuItem();
			printPartNumberGroups.setText("打印(料号分组)");
			printPartNumberGroups.setBounds(620, 36, 65, 23);
			printPartNumberGroups
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {
							printReport();
						}

					});
		}
		return printPartNumberGroups;
	}

	/**
	 * 报表打印
	 * 
	 */
	private void printReport() {
		if (impExpInfos == null || impExpInfos.isEmpty()) {
			JOptionPane.showMessageDialog(DgCheckSelfImpExpQuery.this,
					"没有列印的记录！", "提示！", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		btnPrint.setEnabled(false);
		try {
			//
			// 进出仓帐封面参数
			//						
			Map<String, Object> parametersByCover = new HashMap<String, Object>();
			String companyName = CommonVars.getCurrUser().getCompany()
					.getName();
			parametersByCover.put("companyName", companyName);

			//
			// 明细参数
			//						
			List<BaseCompany> company = new ArrayList<BaseCompany>();
			company.add(CommonVars.getCurrUser().getCompany());
			Map<String, Object> parameters = new HashMap<String, Object>();
			if (materialType == MaterielType.MATERIEL) {
				parameters.put("title", "原材料物料帐");
				parametersByCover.put("title", "原材料物料帐");
			} else if (materialType == MaterielType.FINISHED_PRODUCT) {

				parameters.put("title", "产成品物料帐");
				parametersByCover.put("title", "产成品物料帐");

			} else if (materialType == MaterielType.MACHINE) {
				parameters.put("title", "设备进出仓帐");
				parametersByCover.put("title", "设备进出仓帐");
			} else if (materialType == MaterielType.REMAIN_MATERIEL) {
				parameters.put("title", "边角料物料帐");
				parametersByCover.put("title", "边角料物料帐");
			} else if (materialType == MaterielType.BAD_PRODUCT) {
				parameters.put("title", "残次品物料帐");
				parametersByCover.put("title", "残次品物料帐");
			} else if (materialType == MaterielType.SEMI_FINISHED_PRODUCT) {
				if (!isWaiFa) {
					parameters.put("title", "在产品物料帐");
					parametersByCover.put("title", "在产品物料帐");
				} else {
					parameters.put("title", "外发加工物料帐");
					parametersByCover.put("title", "外发加工物料帐");
				}
			}
			if (!tfHsName.getText().trim().equals("")) {
				parameters.put("hsName", tfHsName.getText().trim());
			}
			if (!tfHsSpec.getText().trim().equals("")) {
				parameters.put("hsSpec", tfHsSpec.getText().trim());
			}

			//
			// 明细
			//
			CommonDataSource imgExgDS = new CommonDataSource(impExpInfos,
					maximumFractionDigits);
			InputStream masterReportStream = DgCheckSelfImpExpQuery.class
					.getResourceAsStream("report/CustomInoutWareReport.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, imgExgDS);

			//
			// print cover
			//
			if (this.cbPrintCover.isSelected() == true) {
				//
				// 进出仓帐封面
				//			
				InputStream coverStream = DgCheckSelfImpExpQuery.class
						.getResourceAsStream("report/CustomInOutReportCover.jasper");
				List<Object> list = new ArrayList<Object>();
				list.add("");
				CommonDataSource coverDS = new CommonDataSource(list);
				JasperPrint coverJasperPrint = JasperFillManager.fillReport(
						coverStream, parametersByCover, coverDS);
				int size = coverJasperPrint.getPages().size();
				System.out.println(size);
				for (int i = 0, n = size; i < n; i++) {
					//
					// 放入报表开始位置
					//
					jasperPrint.addPage(i, (JRPrintPage) coverJasperPrint
							.getPages().get(i));
				}
			}
			// 页数
			// CasReportVars.setJasperPrintCount(jasperPrint);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);

		} catch (JRException e1) {
			e1.printStackTrace();
		}
		btnPrint.setEnabled(true);
	}

	/*
	 * 用 2009-01-01 来排序即可,不需要这么麻烦.
	 */
	private class ComparatorImpl implements Comparator {
		public int compare(Object o1, Object o2) {
			if (o1 == null || o2 == null) {
				return 1;
			}
			// beginYear + "年" + i + "月"

			ImportExportInfo a = (ImportExportInfo) o1;
			ImportExportInfo b = (ImportExportInfo) o2;
			String key1 = (a.getHsName() == null ? "" : a.getHsName())
					+ (a.getHsSpec() == null ? "" : a.getHsSpec())
					+ (a.getWareSet() == null ? null : a.getWareSet().getName())
					+ (a.getHsUnit() == null ? null : a.getHsUnit().getName());

			String key2 = (b.getHsName() == null ? "" : b.getHsName())
					+ (b.getHsSpec() == null ? "" : b.getHsSpec())
					+ (b.getWareSet() == null ? null : b.getWareSet().getName())
					+ (b.getHsUnit() == null ? null : b.getHsUnit().getName());

			//
			// asc
			//
			return key1.compareTo(key2);
			// if (key1.compareTo(key2)) {
			// return -1;
			// } else if (year1 < year2) {
			// return 1;
			// } else {
			// if (month1 > month2) {
			// return -1;
			// } else if (month1 < month2) {
			// return 1;
			// }
			// }
			// return 0;
		}
	}

	/**
	 * 报表打印
	 * 
	 */
	private void printReport2() {

		if (impExpInfos == null || impExpInfos.isEmpty()) {
			JOptionPane.showMessageDialog(DgCheckSelfImpExpQuery.this,
					"没有列印的记录！", "提示！", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		printDeclareNameGroups.setEnabled(false);
		try {
			//
			// 进出仓帐封面参数
			//						
			Map<String, Object> parametersByCover = new HashMap<String, Object>();
			String companyName = CommonVars.getCurrUser().getCompany()
					.getName();
			parametersByCover.put("companyName", companyName);

			//
			// 明细参数
			//						
			List<BaseCompany> company = new ArrayList<BaseCompany>();
			company.add(CommonVars.getCurrUser().getCompany());
			Map<String, Object> parameters = new HashMap<String, Object>();
			if (materialType == MaterielType.MATERIEL) {
				parameters.put("title", "原材料物料帐");
				parametersByCover.put("title", "原材料物料帐");
			} else if (materialType == MaterielType.FINISHED_PRODUCT) {
				parameters.put("title", "产成品料物料帐");
				parametersByCover.put("title", "产成品料物料帐");
			} else if (materialType == MaterielType.MACHINE) {
				parameters.put("title", "设备进出仓帐");
				parametersByCover.put("title", "设备进出仓帐");
			} else if (materialType == MaterielType.REMAIN_MATERIEL) {
				parameters.put("title", "边角料进出仓帐");
				parametersByCover.put("title", "边角料进出仓帐");
			} else if (materialType == MaterielType.BAD_PRODUCT) {
				parameters.put("title", "残次品料物料帐");
				parametersByCover.put("title", "残次品料物料帐");
			} else if (materialType == MaterielType.SEMI_FINISHED_PRODUCT) {
				if (!isWaiFa) {
					parameters.put("title", "在产品物料帐");
					parametersByCover.put("title", "在产品物料帐");
				} else {
					parameters.put("title", "外发加工物料帐");
					parametersByCover.put("title", "外发加工物料帐");
				}
			}

			if (!tfHsName.getText().trim().equals("")) {
				parameters.put("hsName", tfHsName.getText().trim());
			}
			if (!tfHsSpec.getText().trim().equals("")) {
				parameters.put("hsSpec", tfHsSpec.getText().trim());
			}

			//
			// 按照仓库分组的话
			//
			if (cbGroupByWarehouse.isSelected()) {
				CommonDataSource imgExgDS = new CommonDataSource(
						groupByWarehouse(), maximumFractionDigits);
				InputStream masterReportStream = DgCheckSelfImpExpQuery.class
						.getResourceAsStream("report/CustomInoutWareReport2.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, imgExgDS);
			}

			CommonDataSource imgExgDS = new CommonDataSource(impExpInfos,
					maximumFractionDigits);
			InputStream masterReportStream = DgCheckSelfImpExpQuery.class
					.getResourceAsStream("report/CustomInoutWareReport2.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, imgExgDS);
			//
			// print cover
			//
			if (this.cbPrintCover.isSelected() == true) {
				//
				// 进出仓帐封面
				//			
				InputStream coverStream = DgCheckSelfImpExpQuery.class
						.getResourceAsStream("report/CustomInOutReportCover.jasper");
				List<Object> list = new ArrayList<Object>();
				list.add("");
				CommonDataSource coverDS = new CommonDataSource(list);
				JasperPrint coverJasperPrint = JasperFillManager.fillReport(
						coverStream, parametersByCover, coverDS);
				int size = coverJasperPrint.getPages().size();
				System.out.println(size);
				for (int i = 0, n = size; i < n; i++) {
					//
					// 放入报表开始位置
					//
					jasperPrint.addPage(i, (JRPrintPage) coverJasperPrint
							.getPages().get(i));
				}
			}
			// 页数
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
		printDeclareNameGroups.setEnabled(true);
	}

	/**
	 * 按照仓库分组统计进出存
	 * 
	 * @return
	 */
	public List groupByWarehouse() {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		java.util.Collections.sort(impExpInfos, new ComparatorImpl());
		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";
		for (int i = 0; i < impExpInfos.size(); i++) {
			ImportExportInfo a = (ImportExportInfo) impExpInfos.get(i);
			String key = (a.getHsName() == null ? "" : a.getHsName())
					+ (a.getHsSpec() == null ? "" : a.getHsSpec())
					+ (a.getWareSet() == null ? null : a.getWareSet().getName())
					+ (a.getHsUnit() == null ? null : a.getHsUnit().getName());
			System.out.println("key=" + key);
			if (!key.equalsIgnoreCase(oldKey)) { // 当排序后不相同的料号初始化为0,但要没有按工厂或报关名称进行查询
				rsPtAmount = 0;
				rsHsAmount = 0;
				// System.out.println("wareSetCode =" + wareSetCode +" ptNo==" +
				// ptNo);
			}
			if (a.getBillType().getWareType().intValue() == 1) {
				rsPtAmount += a.getImpPtAmount();
				rsHsAmount += a.getImpHsAmount();
			} else {
				rsPtAmount -= a.getExpPtAmount();
				rsHsAmount -= a.getExpHsAmount();
			}
			// System.out.println("rsPtAmount =" + rsPtAmount
			// +" rsHsAmount==" +
			// rsHsAmount);
			a.setRsPtAmount(rsPtAmount);
			a.setRsHsAmount(rsHsAmount);
			result.add(a);
			oldKey = key;
		}
		return result;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JMenuItem getPrintDeclareNameGroups() {
		if (printDeclareNameGroups == null) {
			printDeclareNameGroups = new JMenuItem();
			printDeclareNameGroups.setText("打印(报关名分组)");
			printDeclareNameGroups.setBounds(620, 60, 65, 23);
			printDeclareNameGroups
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printReport2();
						}
					});
		}
		return printDeclareNameGroups;
	}

	private void fillWareSet() {
		cmbWareSet.removeAllItems();
		List wareSets = this.materialManageAction.findWareSet(new Request(
				CommonVars.getCurrUser(), true));
		for (int i = 0; i < wareSets.size(); i++) {
			WareSet wareSet = (WareSet) wareSets.get(i);
			CheckBoxListItem item = new CheckBoxListItem();
			item.setCode(wareSet.getCode());
			item.setName(wareSet.getName());
			cmbWareSet.addItem(item);
		}
		this.cmbWareSet.setSelectedItem(null);
		this.cmbWareSet.setRenderer(new CheckBoxListCellRenderer(cmbWareSet));
		this.cmbWareSet.setUI(new SteppedMetalComboBoxUI(225));
		this.setCmbBillTypeEvent(cmbWareSet);
	}

	/**
	 * 设置怎么样编辑JComboBox 上的 JList 和 JTextField
	 * 
	 * @param cbb
	 */
	private void setCmbBillTypeEvent(final JComboBox cbb) {
		final JList listBox;
		try {
			Field field = JComponent.class.getDeclaredField("ui");
			field.setAccessible(true);
			BasicComboBoxUI ui = (BasicComboBoxUI) field.get(cbb);
			field = BasicComboBoxUI.class.getDeclaredField("listBox");
			field.setAccessible(true);
			listBox = (JList) field.get(ui);
		} catch (NoSuchFieldException nsfe) {
			throw new RuntimeException(nsfe);
		} catch (IllegalAccessException iae) {
			throw new RuntimeException(iae);
		}
		if (listBox == null) {
			return;
		}

		listBox.setBackground(Color.white);
		listBox.setFixedCellHeight(20);

		MouseListener[] mouseListener = listBox.getMouseListeners();
		for (int i = 0; i < mouseListener.length; i++) {
			listBox.removeMouseListener(mouseListener[i]);
		}

		listBox.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index = listBox.locationToIndex(e.getPoint());
				if (index == -1) {
					return;
				}
				CheckBoxListItem item = (CheckBoxListItem) listBox.getModel()
						.getElementAt(index);
				item.setIsSelected(!item.getIsSelected());
				Rectangle rect = listBox.getCellBounds(index, index);
				listBox.repaint(rect);
				ComboBoxEditor com = cbb.getEditor();
				JTextField tf = (JTextField) com.getEditorComponent();
				if (tf != null) {
					if (item.getIsSelected()) {
						tf.setText("".equals(tf.getText()) ? item.getName()
								: tf.getText() + "," + item.getName());
					} else {
						String[] strs = tf.getText().split(",");
						String str = "";
						for (int i = 0; i < strs.length; i++) {
							if (item.getName().equalsIgnoreCase(strs[i])) {
								continue;
							}
							if ("".equals(str)) {
								str += strs[i];
							} else {
								str += "," + strs[i];
							}
						}
						tf.setText(str);
					}
				}
			}
		});

		//
		// 当焦点丢失的时候
		//
		ComboBoxEditor com = cbb.getEditor();
		final JTextField tf = (JTextField) com.getEditorComponent();
		tf.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {

				String[] strs = tf.getText().split(",");
				Map<String, CheckBoxListItem> checkBoxListItemMap = new HashMap<String, CheckBoxListItem>();

				int size = listBox.getModel().getSize();
				for (int i = 0; i < size; i++) {
					CheckBoxListItem item = (CheckBoxListItem) listBox
							.getModel().getElementAt(i);
					checkBoxListItemMap.put(item.getName(), item);
				}
				//
				// 根据输入的字符串选中JList中的列表
				//
				String tempText = "";
				// System.out.println("strs.length =="+strs.length);
				for (String str : strs) {
					// System.out.println(str);
					CheckBoxListItem item = checkBoxListItemMap.get(str);
					if (item != null) {
						item.setIsSelected(true);
						if ("".equals(tempText)) {
							tempText += item.getName();
						} else {
							tempText += "," + item.getName();
						}
						checkBoxListItemMap.remove(str);
					}
				}
				//
				// 清除未选中的记录
				//
				Iterator<CheckBoxListItem> iterator = checkBoxListItemMap
						.values().iterator();
				for (; iterator.hasNext();) {
					CheckBoxListItem checkBoxListItem = iterator.next();
					checkBoxListItem.setIsSelected(false);
				}
				//
				// 重新设置其显示文本值
				//
				tf.setText(tempText);
			}
		});

	}

	private void fillCustomer() {
		cmbCustomer.removeAllItems();
		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.cmbCustomer.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cmbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cmbCustomer, "code", "name", 270);
		this.cmbCustomer.setSelectedItem(null);
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
							list.add(addColumn("物料类别", "materielType", 100));
						if(materialType.equals(MaterielType.SEMI_FINISHED_PRODUCT)){
							list.add(addColumn("成品料号", "ptPartFinished", 100));
							list.add(addColumn("成品名称", "ptNameFinished", 150));
							list.add(addColumn("成品规格型号", "ptSpecFinished", 120));
							list.add(addColumn("成品单位", "ptUnitFinished.name", 100));
							list.add(addColumn("成品数量", "ptAmountFinished", 100));
							list.add(addColumn("单位系数", "unitConvertFinished", 100));
						}
						list.add(addColumn("BOM编号/料号", "ptPart", 100));
						list.add(addColumn("工厂商品名称", "ptName", 150));
						list.add(addColumn("工厂规格型号", "ptSpec", 120));
						list.add(addColumn("进出仓日期", "date", 100));
						list.add(addColumn("单据类型", "billType.name", 100));

						list.add(addColumn("凭证号", "impBillNo", 120));
						list.add(addColumn("工厂数量", "impPtAmount", 60));
						list.add(addColumn("海关数量", "impHsAmount", 60));

						list.add(addColumn("凭证号", "expBillNo", 120));
						list.add(addColumn("工厂数量", "expPtAmount", 60));
						list.add(addColumn("海关数量", "expHsAmount", 60));

						list.add(addColumn("工厂数量", "rsPtAmount", 60));
						list.add(addColumn("海关数量", "rsHsAmount", 80));
						list.add(addColumn("报关单号", "customNo", 70));//
						list.add(addColumn("制单号", "productNo", 70));
						list.add(addColumn("收送货商名称", "scmCoc.name", 100));

						// list.add(addColumn("商品序号", "seqNum", 100));

						list.add(addColumn("工厂单位", "ptUnit.name", 60));
						list.add(addColumn("报关名称", "hsName", 100));
						list.add(addColumn("报关规格", "hsSpec", 100));
						list.add(addColumn("海关单位", "hsUnit.name", 100));
						list.add(addColumn("仓库", "wareSet.name", 80));
						list.add(addColumn("备注", "note", 100));

						return list;
					}
				});

		TableColumnModel cm = jTable.getColumnModel();
		ColumnGroup g_in = new ColumnGroup("进仓");
		ColumnGroup g_out = new ColumnGroup("出仓");
		ColumnGroup g_rs = new ColumnGroup("结存");
		if(!materialType.equals(MaterielType.SEMI_FINISHED_PRODUCT)){
			g_in.add(cm.getColumn(7));
			g_in.add(cm.getColumn(8));
			g_in.add(cm.getColumn(9));

			g_out.add(cm.getColumn(10));
			g_out.add(cm.getColumn(11));
			g_out.add(cm.getColumn(12));

			g_rs.add(cm.getColumn(13));
			g_rs.add(cm.getColumn(14));
		}else{
			g_in.add(cm.getColumn(13));
			g_in.add(cm.getColumn(14));
			g_in.add(cm.getColumn(15));

			g_out.add(cm.getColumn(16));
			g_out.add(cm.getColumn(17));
			g_out.add(cm.getColumn(18));

			g_rs.add(cm.getColumn(19));
			g_rs.add(cm.getColumn(20));
		}

		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.setColumnModel(jTable.getColumnModel());
		header.addColumnGroup(g_in);
		header.addColumnGroup(g_out);
		header.addColumnGroup(g_rs);
		jTable.repaint();
		// List<JTableListColumn> xs = tableModel.getColumns();
		// xs.get(7).setFractionCount(2);
		// xs.get(8).setFractionCount(2);
		// xs.get(10).setFractionCount(2);
		// xs.get(11).setFractionCount(2);
		// xs.get(12).setFractionCount(2);
		// xs.get(13).setFractionCount(2);
	}

	/**
	 * This method initializes btnName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnName() {
		if (btnName == null) {
			btnName = new JButton();
			btnName.setText("...");
			btnName.setLocation(new Point(196, 20));
			btnName.setSize(new Dimension(18, 22));
			btnName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String type = null;
					if (materialType.equals(MaterielType.MATERIEL)
							|| materialType.equals(MaterielType.SEMI_FINISHED_PRODUCT))
						type = MaterielType.MATERIEL;
					else
						type = materialType;
						Object object = CasQuery.getInstance()
								.findWuLiaoPtNameFromBillDetail(type);
						if (object != null) {
							tfName.setText((String) ((TempObject) object)
									.getObject());
						}

				}
			});
		}
		return btnName;
	}

	/**
	 * This method initializes tfPtNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtNo() {
		if (tfPtNo == null) {
			tfPtNo = new JTextField();
			tfPtNo.setBounds(new Rectangle(277, 20, 140, 22));
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
			btnPtNo.setText("...");
			btnPtNo.setLocation(new Point(417, 20));
			btnPtNo.setSize(new Dimension(19, 22));
			btnPtNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String type = null;
					if (materialType.equals(MaterielType.MATERIEL)
							|| materialType
									.equals(MaterielType.SEMI_FINISHED_PRODUCT))
						type = MaterielType.MATERIEL;
					else
						type = materialType;
					
						List<TempObject> list = CasQuery.getInstance()
								.getWuLiaoProductptNo(false,
										type, new ArrayList());
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
			tfEndPtNo.setEditable(false);
			tfEndPtNo.setBounds(new Rectangle(490, 20, 120, 22));
			tfEndPtNo.setEnabled(true);
		}
		return tfEndPtNo;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (btnEndPtNo == null) {
			btnEndPtNo = new JButton();
			btnEndPtNo.setEnabled(false);
			btnEndPtNo.setSize(new Dimension(19, 22));
			btnEndPtNo.setLocation(new Point(610, 20));
			btnEndPtNo.setText("...");
			btnEndPtNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String type = null;
					if (materialType.equals(MaterielType.MATERIEL)
							|| materialType
									.equals(MaterielType.SEMI_FINISHED_PRODUCT))
						type = MaterielType.MATERIEL;
					else
						type = materialType;
					List<TempObject> list = CasQuery.getInstance()
							.getWuLiaoProductptNo(false, type,
									new ArrayList());
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel) sm.getObject();
						tfEndPtNo.setText(m == null ? "" : m.getPtNo());
						tfHsName.setText(null);
						tfHsSpec.setText(null);
					}
				}
			});
		}
		return btnEndPtNo;
	}

	/**
	 * This method initializes cbIsSplitBomVersion
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsSplitBomVersion() {
		if (cbIsSplitBomVersion == null) {
			cbIsSplitBomVersion = new JCheckBox();
			cbIsSplitBomVersion.setText("分版本统计");
			cbIsSplitBomVersion.setBounds(new Rectangle(157, 132, 86, 20));
		}
		cbIsSplitBomVersion.setVisible(false);
		return cbIsSplitBomVersion;
	}

	/**
	 * This method initializes tfHsName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsName() {
		if (tfHsName == null) {
			tfHsName = new JTextField();
			tfHsName.setBounds(new Rectangle(58, 51, 140, 22));
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
			btnHsName.setText("...");
			btnHsName.setLocation(new Point(196, 51));
			btnHsName.setSize(new Dimension(19, 22));
			btnHsName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String type = null;
					if (materialType.equals(MaterielType.MATERIEL)
							|| materialType
									.equals(MaterielType.SEMI_FINISHED_PRODUCT))
						type = MaterielType.MATERIEL;
					else
						type = materialType;
						Object object = CasQuery.getInstance()
								.findWuLiaoHsNameFromStatCusNameRelationHsn(type);
						if (object != null) {
							tfHsName.setText((String) ((TempObject) object)
									.getObject());
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
			tfHsSpec.setSize(new Dimension(140, 22));
			tfHsSpec.setLocation(new Point(277, 51));
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
			btnHsSpec.setText("...");
			btnHsSpec.setLocation(new Point(417, 51));
			btnHsSpec.setSize(new Dimension(19, 22));
			btnHsSpec.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String type = null;
					if (materialType.equals(MaterielType.MATERIEL)
							|| materialType
									.equals(MaterielType.SEMI_FINISHED_PRODUCT))
						type = MaterielType.MATERIEL;
					else
						type = materialType;
					Object object = CasQuery.getInstance()
							.findWuLiaoHsSpecFromStatCusNameRelationHsn(type,
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
	 * This method initializes tfBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBillNo() {
		if (tfBillNo == null) {
			tfBillNo = new JTextField();
			tfBillNo.setSize(new Dimension(140, 22));
			tfBillNo.setLocation(new Point(490, 79));
		}
		return tfBillNo;
	}

	/**
	 * This method initializes cbIsTotalFrontAmount
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsTotalFrontAmount() {
		if (cbIsTotalFrontAmount == null) {
			cbIsTotalFrontAmount = new JCheckBox();
			cbIsTotalFrontAmount.setText("统计上期结存");
			cbIsTotalFrontAmount.setLocation(new Point(402, 132));
			cbIsTotalFrontAmount.setSize(new Dimension(97, 20));
		}
		return cbIsTotalFrontAmount;
	}

	/**
	 * This method initializes tfProNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfProNo() {
		if (tfProNo == null) {
			tfProNo = new JTextField();
			tfProNo.setSize(new Dimension(140, 22));
			tfProNo.setLocation(new Point(490, 106));
		}
		return tfProNo;
	}

	/**
	 * This method initializes cbOnlyMinus
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbOnlyMinus() {
		if (cbOnlyMinus == null) {
			cbOnlyMinus = new JCheckBox();
			cbOnlyMinus.setText("显示负数结存");
			cbOnlyMinus.setLocation(new Point(533, 132));
			cbOnlyMinus.setSize(new Dimension(108, 21));
			cbOnlyMinus.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (getCbOnlyMinus().isSelected()) {
						showLessthanZero(true);
					} else {
						showLessthanZero(false);
					}
				}
			});
		}
		return cbOnlyMinus;
	}

	/**
	 * This method initializes btnExit1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit1() {
		if (btnExit1 == null) {
			btnExit1 = new JButton();
			btnExit1.setBounds(new Rectangle(654, 98, 86, 25));
			btnExit1.setText("关闭");
			btnExit1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit1;
	}

	/**
	 * This method initializes tfCustomsUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomsUnit() {
		if (tfCustomsUnit == null) {
			tfCustomsUnit = new JTextField();
			tfCustomsUnit.setSize(new Dimension(140, 22));
			tfCustomsUnit.setLocation(new Point(490, 51));
		}
		return tfCustomsUnit;
	}

	/**
	 * This method initializes cbGroupByWarehouse
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbGroupByWarehouse() {
		if (cbGroupByWarehouse == null) {
			cbGroupByWarehouse = new JCheckBox();
			cbGroupByWarehouse.setBounds(new Rectangle(19, 132, 86, 21));
			cbGroupByWarehouse.setText("按仓库分组");
		}
		return cbGroupByWarehouse;
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
			String hsName,String hsSpec,String ptNo,String ptName,String ptSpec){
		//如果结束日期不为空
		if(endDate != null){
			this.endDate.setDate(endDate);
			if(startDate != null){
				this.startDate.setDate(startDate);
			}else{
				this.startDate.setDate(CommonVars.getEndDate(new Date(endDate.getYear(),0,1)));
			}
		}
		
		this.tfHsName.setText(hsName);
		this.tfHsSpec.setText(hsSpec);
		
		this.tfPtNo.setText(ptNo);
		this.tfEndPtNo.setText("");
		
		this.tfName.setText(ptName);
	}
	

} // @jve:decl-index=0:visual-constraint="10,10"
