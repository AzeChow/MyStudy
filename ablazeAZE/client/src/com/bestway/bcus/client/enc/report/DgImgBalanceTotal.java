/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc.report;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.beanutils.BeanUtilsBean;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.client.common.CheckBoxListCellRenderer;
import com.bestway.bcus.client.common.CheckBoxListItem;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.SteppedMetalComboBoxUI;
import com.bestway.bcus.client.manualdeclare.DgImpCustomsRecord;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgImgBalanceTotal extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JTableListModel tableModel = null;

	private EncAction encAction = null;

	private List list = null; // @jve:decl-index=0:

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane1 = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox jCalendarComboBox = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox jCalendarComboBox1 = null;

	private JLabel jLabel3 = null;

	private JButton jButton = null;

	private List lsResult = new ArrayList();; // @jve:decl-index=0:

	private JButton jButton1 = null;

	private ManualDeclareAction manualDecleareAction = null;

	private JRadioButton rbByDeclarationDate = null;

	private JRadioButton rbByEndCustomsDate = null;

	private ButtonGroup buttonGroup = null;

	private JLabel jLabel4 = null;

	private JComboBox jComboBox = null;

	private JCheckBox jCheckBox1 = null;

	private MaterialManageAction materialManageAction = null;

	private JPanel jPanel2 = null;

	private JRadioButton rbEffect = null;

	private JRadioButton rbUnEffect = null;

	private JRadioButton rbAll = null;

	private ButtonGroup buttonGroup1 = null; // @jve:decl-index=0:

	private JCheckBox jCheckBox = null;
	private List slist = new ArrayList(); // @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public DgImgBalanceTotal() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();

	}

	private void initUIComponents() {
		List list = manualDecleareAction.findEmsHeadH2k(new Request(CommonVars
				.getCurrUser(), true));
		if (list != null && list.size() > 0) {
			String emsno = ((EmsHeadH2k) list.get(0)).getEmsNo();
			jLabel3.setText("帐册号：" + emsno);
		}

		// 事业部
		fillProject();
		// List projectList = materialManageAction.findProjectDept(new Request(
		// CommonVars.getCurrUser(), true));
		// this.jComboBox
		// .setModel(new DefaultComboBoxModel(projectList.toArray()));
		// this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender(
		// "code", "name"));
		// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
		// this.jComboBox, "code", "name");
		// jComboBox.setSelectedIndex(-1);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(756, 514);
		this.setTitle("料件进出平衡状况汇总表");
		this.jCalendarComboBox.setDate(CommonVars.getBeginDate());
		this.jCalendarComboBox1.setDate(new Date());
		
		getButtonGroup();
		getButtonGroup1();
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {

			initUIComponents();
			list = new Vector();
			initTable(list);
		}
		super.setVisible(b);
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
	 * 初始化数据Table BillTemp
	 */
	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}

		JTableListModelAdapter tableModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("备案序号", "bill1", 80, Integer.class));
				list.add(addColumn("商品编码", "bill2", 80));
				list.add(addColumn("品名规格", "bill3", 150));
				list.add(addColumn("单位", "bill4", 50));
				list.add(addColumn("事业部", "bill5", 100));
				list.add(addColumn("1.报关平均美元单价", "billSum1", 100));
				list.add(addColumn("申报帐册单价", "billSum2", 120));
				list.add(addColumn("2.总进口量(3+4+5-6-7)", "billSum3", 60));
				list.add(addColumn("3.直接进口量", "billSum4", 80));
				list.add(addColumn("4.结转进口量", "billSum5", 80));
				list.add(addColumn("5.余料结转进口", "billSum6", 80));
				list.add(addColumn("6.余料结转出口", "billSum7", 80));
				list.add(addColumn("7.退料出口量", "billSum8", 80));
				list.add(addColumn("8.内销数量", "billSum9", 60));
				list.add(addColumn("9.总进口美元金额(3+4+5-6-7)", "billSum10", 100));
				list.add(addColumn("10.成品耗用量(17+18+19-20)", "billSum11", 150));
				list.add(addColumn("11.成品总耗用金额=10*1", "billSum12", 240));
				list.add(addColumn("12.帐册库存量=2-10-8+21", "billSum13", 200));
				list.add(addColumn("13.库存量", "billSum14", 60));
				list.add(addColumn("14.差异值=12-13", "billSum15", 200));
				list.add(addColumn("15.差额=2-10", "billSum16", 200));
				list.add(addColumn("16.边角料", "billSum17", 150));
				list.add(addColumn("17.直接出口耗用量", "billSum18", 150));
				list.add(addColumn("18.转厂出口耗用量", "billSum19", 150));
				list.add(addColumn("19.返工复出耗用量", "billSum20", 150));
				list.add(addColumn("20.退厂返工耗用量", "billSum21", 150));
				list.add(addColumn("21.期初库存量", "billSum22", 100));
				list.add(addColumn("内销金额", "billSum23", 100));
				list.add(addColumn("期初金额", "billSum24", 100));
				list.add(addColumn("结余金额", "billSum25", 100));
				return list;
			}
		};
		JTableListModel.dataBind(jTable, list, tableModelAdapter,
				ListSelectionModel.SINGLE_SELECTION);
		tableModel = (JTableListModel) jTable.getModel();
		
		setUpSportColumn(jTable, 6, "平均单价 = （期初金额+直接进口金额+结转进口金额+海关批准内销金额-" +
				"退料出口金额）/（期初数量+直接进口数量+结转进口数量+海关批准内销数量-退料出口数量）");
		setUpSportColumn(jTable, 9, "双击可关联‘直接进口类型’料件进口报关登记表");
		setUpSportColumn(jTable, 10, "双击可关联‘结转进口类型’料件进口报关登记表");
		setUpSportColumn(jTable, 11, "双击可关联‘余料结转进口类型’料件进口报关登记表");
		setUpSportColumn(jTable, 12, "双击可关联‘余料结转出口类型’料件进口报关登记表");
		setUpSportColumn(jTable, 13, "双击可关联‘退料出口类型’料件进口报关登记表");
		setUpSportColumn(jTable, 14, "双击可关联‘料件内销类型’料件进口报关登记表");
		
		setUpSportColumn(jTable, 23, "双击可关联‘直接出口类型’料件耗用明细表（帐册级）");
		setUpSportColumn(jTable, 24, "双击可关联‘转厂出口类型’料件耗用明细表（帐册级）");
		setUpSportColumn(jTable, 25, "双击可关联‘返工复出口类型’料件耗用明细表（帐册级）");
		setUpSportColumn(jTable, 26, "双击可关联‘退厂返工类型’料件耗用明细表（帐册级）");
		
		// 截取小数位
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
	 * 为表格的某个列设置提示（不包含表头）
	 * 
	 * @param table
	 * @param colIndex
	 * @param tipText
	 */
	public void setUpSportColumn(JTable table, int colIndex, String tipText) {
		TableColumn sportColumn = table
				.getColumnModel().getColumn(colIndex);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText(tipText);
		sportColumn.setCellRenderer(renderer);
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */

	public ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(getRbEffect());
			buttonGroup1.add(getRbUnEffect());
			buttonGroup1.add(getRbAll());
		}
		return buttonGroup1;
	}

	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(150);
			jSplitPane.setDividerSize(0);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel4 = new JLabel();
			jLabel4.setForeground(java.awt.Color.red);
			jLabel4.setBounds(new Rectangle(145, 59, 59, 18));
			jLabel4.setText("事业部");
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jLabel.setText("料件进出平衡状况汇总表");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setPreferredSize(new Dimension(1, 1));
			jLabel.setBounds(213, 0, 298, 34);

			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 24));
			jLabel.setForeground(java.awt.Color.blue);
			jLabel1.setText("本期日期：");
			jLabel1.setBounds(new Rectangle(3, 20, 75, 26));
			jLabel2.setText("至");
			jLabel2.setBounds(new Rectangle(215, 20, 39, 26));
			jLabel3.setText("帐册号：");

			jLabel3.setBounds(new Rectangle(18, 90, 184, 20));
			jPanel.add(jLabel, null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJPanel2(), null);
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
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
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
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					// 双击
					if(e.getClickCount() == 2) {
						int col = jTable.getSelectedColumn();
						if(col == 8 || col == 9 || col == 10  || col == 11
								 || col == 12 || col == 13 || col == 14) {
							Integer seqNum = null;
							BillTemp billTemp = (BillTemp) tableModel.getCurrentRow();
							seqNum = Integer.parseInt(billTemp.getBill1());
							String impExpType = null;
							if(col == 9) {
								impExpType = ImpExpType.DIRECT_IMPORT + "";
							} else if(col == 10) {
								impExpType = ImpExpType.TRANSFER_FACTORY_IMPORT + "";
							} else if(col == 11) {
								impExpType = ImpExpType.REMAIN_FORWARD_IMPORT + "";
							} else if(col == 12) {
								impExpType = ImpExpType.REMAIN_FORWARD_EXPORT + "";
							} else if(col == 13) {
								impExpType = ImpExpType.BACK_MATERIEL_EXPORT + "";
							} else if(col == 14) {
								impExpType = ImpExpType.MATERIAL_DOMESTIC_SALES + "";
							}
							showImpCustomsRecord(seqNum, impExpType);
						} else if(col == 23 || col == 24 || col == 25 || col == 26) {
							Integer impExpType = null; 
							if(col == 23) {
								impExpType = ImpExpType.DIRECT_EXPORT;
							} else if(col == 24) {
								impExpType = ImpExpType.TRANSFER_FACTORY_EXPORT ;
							} else if(col == 25) {
								impExpType = ImpExpType.REWORK_EXPORT;
							} else if(col == 26) {
								impExpType = ImpExpType.BACK_FACTORY_REWORK;
							}
							
							showDgExgWearDetail(impExpType);
						}
					}
				}
			});
		}
		return jTable;
	}
	
	public void showImpCustomsRecord(Integer seqNum, String impExpType) {
		DgImpCustomsRecord dg = new DgImpCustomsRecord();
		int iseffect = -1;
		if (rbEffect.isSelected()) {
			iseffect = CustomsDeclarationState.EFFECTIVED;
		} else if (rbUnEffect.isSelected()) {
			iseffect = CustomsDeclarationState.NOT_EFFECTIVED;
		} else if (rbAll.isSelected()) {
			iseffect = CustomsDeclarationState.ALL;
		}
		dg.showData(seqNum, null, impExpType, getJCalendarComboBox().getDate(), 
				getJCalendarComboBox1().getDate(), getRbByDeclarationDate().isSelected(),
				iseffect, false, null, true);
		ShowFormControl.refreshInteralForm(dg);
	}
	
	public void showDgExgWearDetail(Integer impExpType) {
		Integer seqNum = null;
		BillTemp billTemp = (BillTemp) tableModel.getCurrentRow();
		seqNum = Integer.parseInt(billTemp.getBill1());
		DgExgWearDetail dg = new DgExgWearDetail();
		dg.showFromDgImgBalanceTotal(impExpType, getJCalendarComboBox().getDate(), 
				getJCalendarComboBox1().getDate(), seqNum);
	}
	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setPreferredSize(new Dimension(453, 450));
			jScrollPane1.setViewportView(getJTable());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(new Rectangle(95, 20, 109, 26));
		}
		return jCalendarComboBox;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.setBounds(new Rectangle(274, 21, 109, 26));
		}
		return jCalendarComboBox1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(650, 32, 86, 23);
			jButton.setText("查询");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jCheckBox1.isSelected()) { // 分事业部
						List<CheckBoxListItem> checkableItemList = new ArrayList<CheckBoxListItem>();
						for (int j = 0; j < jComboBox.getModel().getSize(); j++) {
							CheckBoxListItem item = (CheckBoxListItem) jComboBox
									.getModel().getElementAt(j);
							if (item.getIsSelected()) {
								checkableItemList.add(item);
							}
						}
						if (checkableItemList.size() == 0) {
							JOptionPane.showMessageDialog(
									DgImgBalanceTotal.this, "请选择事业部!", "提示", 2);
							return;
						}
						new findfordept().start();
					} else {// 不分事业部
						new find().start();
					}
				}
			});
		}
		return jButton;
	}

	class find extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");

				// 生效状态
				int iseffect = -1;
				boolean isCalStoreNum = false;
				if (rbEffect.isSelected()) {
					iseffect = CustomsDeclarationState.EFFECTIVED;
				} else if (rbUnEffect.isSelected()) {
					iseffect = CustomsDeclarationState.NOT_EFFECTIVED;
				} else if (rbAll.isSelected()) {
					iseffect = CustomsDeclarationState.ALL;
				}
				if(jCheckBox.isSelected()){
					 isCalStoreNum = true;
				}
				Date beginDate = CommonVars.dateToStandDate(jCalendarComboBox
						.getDate());
				Date endDate = CommonVars.dateToStandDate(jCalendarComboBox1
						.getDate());
				if (rbByDeclarationDate.isSelected()) {// 申报日期
					lsResult = encAction.jisuanImgBalanceTotal(new Request(
							CommonVars.getCurrUser()), beginDate, endDate,
							iseffect, true,isCalStoreNum);
				} else if (rbByEndCustomsDate.isSelected()) {// 结关日期
					lsResult = encAction.jisuanImgBalanceTotal(new Request(
							CommonVars.getCurrUser()), beginDate, endDate,
							iseffect, false,isCalStoreNum);
				}
				slist.clear();
				for (int i = 0; i < lsResult.size(); i++) {

					BillTemp btt = (BillTemp) lsResult.get(i);
					BillTemp bt = new BillTemp();
					BeanUtilsBean.getInstance().copyProperties(bt, btt);
					Double b13 = bt.getBillSum13() == null ? 0.0 : bt
							.getBillSum13();
					Double b22 = bt.getBillSum22() == null ? 0.0 : bt
							.getBillSum22();
					Double b15 = bt.getBillSum15() == null ? 0.0 : bt
							.getBillSum15();
					bt.setBillSum13(b13 + b22);
					bt.setBillSum15(b15 + b22);
					slist.add(bt);
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgImgBalanceTotal.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				initTable(lsResult);
			}
		}
	}

	class findfordept extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				List<String> deptCode = null;
				// 事业部
				List<CheckBoxListItem> checkableItemList = new ArrayList<CheckBoxListItem>();
				for (int j = 0; j < jComboBox.getModel().getSize(); j++) {
					CheckBoxListItem item = (CheckBoxListItem) jComboBox
							.getModel().getElementAt(j);
					if (item.getIsSelected()) {
						checkableItemList.add(item);
					}
				}
				deptCode = new ArrayList<String>();
				for (int i = 0; i < checkableItemList.size(); i++) {
					deptCode.add((checkableItemList.get(i)).getCode());
				}
				int iseffect = -1;
				if (rbEffect.isSelected()) {
					iseffect = CustomsDeclarationState.EFFECTIVED;
				} else if (rbUnEffect.isSelected()) {
					iseffect = CustomsDeclarationState.NOT_EFFECTIVED;
				} else if (rbAll.isSelected()) {
					iseffect = CustomsDeclarationState.ALL;
				}
				Date beginDate = CommonVars.dateToStandDate(jCalendarComboBox
						.getDate());
				Date endDate = CommonVars.dateToStandDate(jCalendarComboBox1
						.getDate());
				if (rbByDeclarationDate.isSelected()) {// 申报日期
					lsResult = encAction.jisuanImgBalanceTotalForDept(
							new Request(CommonVars.getCurrUser()), beginDate,
							endDate, iseffect, true, deptCode);
				} else if (rbByEndCustomsDate.isSelected()) {// 结关日期
					lsResult = encAction.jisuanImgBalanceTotalForDept(
							new Request(CommonVars.getCurrUser()), beginDate,
							endDate, iseffect, false, deptCode);
				}
				slist.clear();
				for (int i = 0; i < lsResult.size(); i++) {

					BillTemp btt = (BillTemp) lsResult.get(i);
					BillTemp bt = new BillTemp();
					BeanUtilsBean.getInstance().copyProperties(bt, btt);
					Double b13 = bt.getBillSum13() == null ? 0.0 : bt
							.getBillSum13();
					Double b22 = bt.getBillSum22() == null ? 0.0 : bt
							.getBillSum22();
					bt.setBillSum13(b13 + b22);
					slist.add(bt);
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgImgBalanceTotal.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				initTable(lsResult);
			}
		}
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(650, 73, 86, 23);
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbByDeclarationDate() {
		if (rbByDeclarationDate == null) {
			rbByDeclarationDate = new JRadioButton();
			rbByDeclarationDate.setText("按申报日期查询");
			rbByDeclarationDate.setBounds(new Rectangle(413, 20, 109, 26));
			rbByDeclarationDate.setSelected(true);
		}
		return rbByDeclarationDate;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbByEndCustomsDate() {
		if (rbByEndCustomsDate == null) {
			rbByEndCustomsDate = new JRadioButton();
			rbByEndCustomsDate.setText("按结关日期查询");

			rbByEndCustomsDate.setBounds(new Rectangle(522, 19, 109, 26));
		}
		return rbByEndCustomsDate;
	}

	public ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			ButtonGroup buttonGroup = new ButtonGroup();
			buttonGroup.add(getRbByDeclarationDate());
			buttonGroup.add(getRbByEndCustomsDate());
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setEnabled(false);
			jComboBox.setBounds(new Rectangle(224, 55, 159, 27));
		}
		return jComboBox;
	}

	/**
	 * 填充事业部
	 */
	private void fillProject() {
		jComboBox.removeAllItems();
		List projectList = materialManageAction.findProjectDept(new Request(
				CommonVars.getCurrUser(), true));
		for (int i = 0; i < projectList.size(); i++) {
			ProjectDept billType = (ProjectDept) projectList.get(i);
			CheckBoxListItem item = new CheckBoxListItem();
			item.setCode(billType.getCode());
			item.setName(billType.getName());
			jComboBox.addItem(item);
		}
		this.jComboBox.setSelectedItem(null);
		this.jComboBox.setRenderer(new CheckBoxListCellRenderer(jComboBox));
		this.jComboBox.setUI(new SteppedMetalComboBoxUI(225));
		this.setCmbBillTypeEvent(jComboBox);
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

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox();
			jCheckBox1.setForeground(java.awt.Color.red);
			jCheckBox1.setBounds(new Rectangle(17, 56, 129, 23));
			jCheckBox1.setText("是否分事业部统计");
			jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jCheckBox1.isSelected()) {
						jComboBox.setEnabled(true);
					} else {
						jComboBox.setEnabled(false);
					}
				}
			});
		}
		return jCheckBox1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();

			jPanel2.setLayout(null);
			jPanel2.setBounds(new Rectangle(2, 34, 648, 111));
			jPanel2.add(jLabel1, null);
			jPanel2.add(getJCalendarComboBox(), null);
			jPanel2.add(getJCalendarComboBox1(), null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getRbByDeclarationDate(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getJCheckBox1(), null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getJComboBox(), null);
			jPanel2.add(getRbEffect(), null);
			jPanel2.add(getRbUnEffect(), null);
			jPanel2.add(getRbAll(), null);
			jPanel2.add(getRbByEndCustomsDate(), null);
			jPanel2.add(getJCheckBox(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes Radiotrue
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbEffect() {
		if (rbEffect == null) {
			rbEffect = new JRadioButton();
			rbEffect.setBounds(new Rectangle(413, 64, 68, 21));
			rbEffect.setText("已生效");
			rbEffect.setSelected(true);
		}
		return rbEffect;
	}

	/**
	 * This method initializes Radiofalse
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbUnEffect() {
		if (rbUnEffect == null) {
			rbUnEffect = new JRadioButton();
			rbUnEffect.setBounds(new Rectangle(497, 64, 73, 21));
			rbUnEffect.setText("未生效");
		}
		return rbUnEffect;
	}

	/**
	 * This method initializes Radioall
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAll() {
		if (rbAll == null) {
			rbAll = new JRadioButton();
			rbAll.setBounds(new Rectangle(576, 65, 55, 21));
			rbAll.setText("全部");
		}
		return rbAll;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(414, 86, 192, 22));
			jCheckBox.setToolTipText("期初库存量取本期起始范围前的数据报核-正式报核的结余栏位的数量");
			jCheckBox.setText("期初库存参与帐册库存计算");
			jCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (getJCheckBox().isSelected()) {
						initTable(slist);
					} else {
						initTable(lsResult);
					}
				}
			});
		}
		return jCheckBox;
	}
} // @jve:decl-index=0:visual-constraint="97,37"
