/*
 * Created on 2004-9-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.entity.TempObject;
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
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgImpExpChinBuyQuery extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	
	/**
	 * 开始日期
	 */
	private JCalendarComboBox startDate = null;
	
	/**
	 *  结尾日期
	 */
	private JCalendarComboBox endDate = null;
	
	private JCheckBox cbPrintCover = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel6 = null;
	private JPanel jPanel7 = null;
	private JComboBox cmbBillType = null;
	private JComboBox cmbCustomer = null;
	private JComboBox cmbWareSet = null;
	private JTextField tfName = null;
	
	/**
	 * 显示资料的表
	 */
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JButton btnQuery = null;
	private JButton btnPrint = null;
	private JButton btnExit = null;
	
	/**
	 * 海关帐远程接口
	 */
	private CasAction casAction = null;
	
	/**
	 * 表tableModel
	 */
	private JTableListModel tableModel = null;
	
	/**
	 * 查询资料结果
	 */
	private List lsImpExpInfos = null;
	
	/**
	 * 物料管理远程接口
	 */
	private MaterialManageAction materialManageAction = null;
	
	private JButton btnName = null;
	private JLabel jLabel11 = null;
	private JTextField tfPtNo = null;
	private JButton btnPtNo = null;
	private JLabel jLabel12 = null;
	private JTextField tfEndPtNo = null;
	private JButton btnEndPtNo = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JTextField tfHsName = null;
	private JButton btnHsName = null;
	private JTextField tfHsSpec = null;
	private JButton btnHsSpec = null;
	
	/**
	 * 进出仓小数位，默认为6位
	 */
	private Integer maximumFractionDigits = CasCommonVars.getOtherOption()
			.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
			.getOtherOption().getInOutMaximumFractionDigits();
	
	private JLabel jLabel2 = null;
	private JTextField tfBillNo = null;
	
	/**
	 * 统计上期结存
	 */
	private JCheckBox cbIsTotalFrontAmount = null;
	
	private JLabel jLabel3 = null;
	private JTextField tfProNo = null;
	
	/**
	 * This is the default constructor
	 */
	public DgImpExpChinBuyQuery() {
		super(false);
		
		/**
		 * 初始化海关帐远程接口
		 */
		casAction = (CasAction) CommonVars.getApplicationContext().getBean("casAction");
		
		/**
		 * 初始化物料管理接口
		 */
		materialManageAction = (MaterialManageAction) CommonVars
							.getApplicationContext().getBean("materialManageAction");
		initialize();
		initUIComponents();
	}

	/**
	 * 初始化一些组件
	 */
	private void initUIComponents() {
		fillBillType();
		fillCustomer();
		fillWareSet();

		initTable(new ArrayList());

		
		//设置开始日期为记帐年度1月1日
		int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
				.getYearInt();
		Calendar beginClarendar = Calendar.getInstance();
		beginClarendar.set(Calendar.YEAR, year);
		beginClarendar.set(Calendar.MONTH, 0);
		beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
		this.startDate.setDate(beginClarendar.getTime());
		this.startDate.setCalendar(beginClarendar);

		//设置结尾日期为记帐年度12月31日
		Calendar endClarendar = Calendar.getInstance();
		endClarendar.set(Calendar.YEAR, year);
		endClarendar.set(Calendar.MONTH, 11);
		endClarendar.set(Calendar.DAY_OF_MONTH, 31);
		this.endDate.setDate(endClarendar.getTime());
		this.endDate.setCalendar(endClarendar);

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("国内购买进出仓帐");
		this.setSize(773, 541);
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
			startDate.setBounds(438, 15, 86, 22);
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
			endDate.setLocation(539, 15);
			endDate.setSize(86, 22);
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
			cbPrintCover.setBounds(new Rectangle(341, 115, 79, 20));
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
			jSplitPane.setDividerLocation(145);
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
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(457, 90, 36, 20));
			jLabel3.setText("制单号");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(457, 65, 37, 22));
			jLabel2.setText("单据号");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(37, 90, 48, 20));
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel1.setText("报关规格");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(37, 65, 48, 22));
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel.setText("报关名称");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(457, 40, 15, 22));
			jLabel12.setText("止");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(252, 40, 48, 22));
			jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel11.setText("工厂料号");
			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			javax.swing.JLabel jLabel6 = new JLabel();
			jPanel7 = new JPanel();
			jPanel7.setLayout(null);
			jLabel6.setText("单据类型");
			jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel6.setBounds(37, 15, 48, 22);
			jLabel7.setText("客户名称");
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel7.setBounds(37, 40, 48, 22);
			jLabel8.setText("  仓 库  ");
			jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel8.setBounds(252, 90, 47, 20);
			jLabel9.setText("工厂名称");
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setBounds(252, 65, 48, 22);
			jLabel10.setText("日期 从");
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setBounds(385, 15, 43, 22);
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
			jLabel5.setBounds(524, 15, 16, 22);
			jLabel5.setText("止");
			jPanel7.add(getJComboBox(), null);
			jPanel7.add(getJComboBox1(), null);
			jPanel7.add(getJComboBox2(), null);
			jPanel7.add(getJComboBox3(), null);
			jPanel7.add(jLabel6, null);
			jPanel7.add(jLabel7, null);
			jPanel7.add(jLabel8, null);
			jPanel7.add(jLabel9, null);
			jPanel7.add(getJCalendarComboBox(), null);
			jPanel7.add(getJCalendarComboBox1(), null);
			jPanel7.add(getJButton(), null);
			jPanel7.add(getBtnPrint(), null);
			jPanel7.add(getJButton2(), null);
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
		}
		return jPanel7;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (cmbBillType == null) {
			cmbBillType = new JComboBox();
			cmbBillType.setBounds(90, 15, 147, 22);
		}
		return cmbBillType;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (cmbCustomer == null) {
			cmbCustomer = new JComboBox();
			cmbCustomer.setBounds(90, 40, 147, 22);
		}
		return cmbCustomer;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox2() {
		if (cmbWareSet == null) {
			cmbWareSet = new JComboBox();
			cmbWareSet.setBounds(302, 90, 142, 20);
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
			tfName.setBounds(302, 65, 122, 22);
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
	private JButton getJButton() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setBounds(637, 21, 65, 23);
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (startDate.getDate() == null
							|| endDate.getDate() == null) {
						JOptionPane.showMessageDialog(DgImpExpChinBuyQuery.this,
								"查询数据开始日期或结束日期不可为空!", "提示", 2);
						return;
					}
					
					//wss2010.11.23屏蔽原有的方法
//					DgImpExpChinBuyQuerySelect dg = new DgImpExpChinBuyQuerySelect();
//					dg.setVisible(true);
//					if (!dg.isOk()){
//						return;
//					}
//					/**
//					 * selectItem:1,计算结存， 2,产生出口仓单
//					 */
//					int selectItem = dg.getSelectItem();
//					
//					new searchThread( materialType, selectItem).start();
					
					//wss2010.11.23重新整理的方法
					new searchThread().start();

				}
			});
		}
		return btnQuery;
	}

	//wss2010.11.23屏蔽原有的查询线程
//	class searchThread extends Thread {
//		String materialType = null;
//		int selectItem = -1;
//
//		public searchThread(String materialType, int selectItem) {
//			this.materialType = materialType;
//			this.selectItem = selectItem;
//		}
//
//		public void run() {
//			//
//			// 用于标识这个对话框的ID
//			//
//			UUID uuid = UUID.randomUUID();
//			final String flag = uuid.toString();
//			btnQuery.setEnabled(false);
//			try {
//				CommonProgress.showProgressDialog(flag, DgImpExpChinBuyQuery.this);
//				CommonProgress.setMessage(flag, "系统正获取数据，请稍后...");
//				
//				List<Condition> conditions = null;
//				List<Condition> conditions1 = null;
//                if (selectItem == 1){
//                	conditions = getConditions();
//                	if (cbIsTotalFrontAmount.isSelected()) {//统计上月结存
//    					impExpInfos = casAction.findImpExpInfos(new Request(
//    							CommonVars.getCurrUser()), materialType,
//    							conditions, cbIsSplitBomVersion.isSelected(),
//    							//startDate.getDate());//by xxm modify 2007-08-09
//    							dateToStandDate(startDate.getDate()),false);//wss这里没有仓库分组，所以暂时false
//    				} else {
//    					impExpInfos = casAction.findImpExpInfos(new Request(
//    							CommonVars.getCurrUser()), materialType,
//    							conditions, cbIsSplitBomVersion.isSelected(),false);//wss这里没有仓库分组，所以暂时false
//    				}
//                } else { //生成出库单据
//                	conditions = getConditionsGetExpDj();
//                	conditions1 = getConditions();
//                	impExpInfos = casAction.findImpExpInfosGetExpDj(new Request(
//							CommonVars.getCurrUser()), materialType,
//							conditions, conditions1, cbIsSplitBomVersion.isSelected());                	
//                }
//				
//
//				CommonProgress.closeProgressDialog(flag);
//			} catch (Exception e) {
//				CommonProgress.closeProgressDialog(flag);
//				JOptionPane.showMessageDialog(DgImpExpChinBuyQuery.this, "获取数据失败：！"
//						+ e.getMessage(), "提示", 2);
//			} finally {
//				if (impExpInfos != null && !impExpInfos.isEmpty()) {
//					tableModel.setList(impExpInfos);
//				} else {
//					initTable(new ArrayList());
//					JOptionPane.showMessageDialog(DgImpExpChinBuyQuery.this,
//							"没有查到符合条件的记录！", "提示！", 2);
//				}
//			}
//			btnQuery.setEnabled(true);
//		}
//	}
	
	
	/**
	 * 查询线程
	 * 2010.11.23新整理
	 */
	class searchThread extends Thread {
		public void run() {
			
			// 用于标识这个对话框的ID
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			btnQuery.setEnabled(false);
			try {
				CommonProgress.showProgressDialog(flag, DgImpExpChinBuyQuery.this);
				CommonProgress.setMessage(flag, "系统正获取数据，请稍后...");
				
				//获取查询条件
				List<Condition> conditions  = getConditions();
            	
            	if (cbIsTotalFrontAmount.isSelected()) {//统计上月结存
					lsImpExpInfos = casAction.findImpExpInfosOfInnerBuy(new Request(
							CommonVars.getCurrUser()), conditions, 
							dateToStandDate(startDate.getDate()));
				} else {
					lsImpExpInfos = casAction.findImpExpInfosOfInnerBuy(new Request(
							CommonVars.getCurrUser()),conditions);
				}

				CommonProgress.closeProgressDialog(flag);
				tableModel.setList(lsImpExpInfos);
				
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgImpExpChinBuyQuery.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			} 
			btnQuery.setEnabled(true);
		}
	}
	
//	private List getConditionsGetExpDj(){//产生出库
//		List<Condition> conditions = new ArrayList<Condition>();
//
//		// 单据类型
//		List<CheckBoxListItem> checkableItemList = new ArrayList<CheckBoxListItem>();
//		for (int j = 0; j < this.cmbBillType.getModel().getSize(); j++) {
//			CheckBoxListItem item = (CheckBoxListItem) this.cmbBillType
//					.getModel().getElementAt(j);
//			if (item.getIsSelected() && !item.getCode().equals("1113")) {
//				checkableItemList.add(item);
//			}
//		}
//		for (int j = 0; j < checkableItemList.size(); j++) {
//			CheckBoxListItem item = checkableItemList.get(j);
//			Condition condition = null;
//			if (j == 0) {
//				condition = new Condition("and", "(",
//						"billMaster.billType.code", "=", item.getCode().trim(),
//						null);
//			} else {
//				condition = new Condition("or", null,
//						"billMaster.billType.code", "=", item.getCode().trim(),
//						null);
//			}
//			if (j == checkableItemList.size() - 1) {
//				condition.setEndBracket(")");
//			}
//			conditions.add(condition);
//		}
//		if (startDate.getDate() != null) {
//			conditions.add(new Condition("and", null, "billMaster.validDate",
//					//">=", startDate.getDate(), null)); //by xxm 2007-08-09
//					">=", dateToStandDate(startDate.getDate()), null));
//		}
//		if (endDate.getDate() != null) {
//			conditions.add(new Condition("and", null, "billMaster.validDate",
//					//"<=", endDate.getDate(), null));//by xxm 2007-08-09
//			        "<=", dateToStandDate(endDate.getDate()), null));
//		}
//		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
//				Boolean.valueOf(true), null));
//		return conditions;
//	}

	/**
	 * 获取查询条件
	 * @return
	 */
	private List getConditions() {
		List<Condition> conditions = new ArrayList<Condition>();
		
		// 单据类型
		List<CheckBoxListItem> lsCheckableItem = new ArrayList<CheckBoxListItem>();
		for (int j = 0; j < this.cmbBillType.getModel().getSize(); j++) {
			CheckBoxListItem item = (CheckBoxListItem) this.cmbBillType
					.getModel().getElementAt(j);
			if (item.getIsSelected()) {
				lsCheckableItem.add(item);
			}

		}
		for (int j = 0; j < lsCheckableItem.size(); j++) {
			CheckBoxListItem item = lsCheckableItem.get(j);
			Condition condition = null;
			if (j == 0) {
				condition = new Condition("and", "(",
						"billMaster.billType.code", "=", item.getCode().trim(),
						null);
			} else {
				condition = new Condition("or", null,
						"billMaster.billType.code", "=", item.getCode().trim(),
						null);
			}
			if (j == lsCheckableItem.size() - 1) {
				condition.setEndBracket(")");
			}
			conditions.add(condition);
		}

		//仓库
		lsCheckableItem.clear();
		for (int j = 0; j < this.cmbWareSet.getModel().getSize(); j++) {
			CheckBoxListItem item = (CheckBoxListItem) this.cmbWareSet
					.getModel().getElementAt(j);
			if (item.getIsSelected()) {
				lsCheckableItem.add(item);
			}
		}
		for (int j = 0; j < lsCheckableItem.size(); j++) {
			CheckBoxListItem item = lsCheckableItem.get(j);
			Condition condition = null;
			if (j == 0) {
				condition = new Condition("and", "(", "wareSet.code", "=", item
						.getCode().trim(), null);
			} else {
				condition = new Condition("or", null, "wareSet.code", "=", item
						.getCode().trim(), null);
			}
			if (j == lsCheckableItem.size() - 1) {
				condition.setEndBracket(")");
			}
			conditions.add(condition);
		}

		//单据号
		if (!"".equals(tfBillNo.getText().trim())) {
			conditions.add(new Condition("and", null, "billMaster.billNo", "=",
					tfBillNo.getText().trim(), null));
		}
		
		//客户名称
		if (cmbCustomer.getSelectedItem() != null) {
			conditions.add(new Condition("and", null, "billMaster.scmCoc", "=",
					cmbCustomer.getSelectedItem(), null));
		}
		
		//工厂名称
		if (tfName.getText() != null && !tfName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "ptName", " = ", tfName
					.getText(), null));
		}
		
		//开始日期
		if (startDate.getDate() != null) {
			if (this.cbIsTotalFrontAmount.isSelected()) {//统计上月结存
				int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
						.getYearInt();
				Calendar beginCalrendar = Calendar.getInstance();
				beginCalrendar.set(Calendar.YEAR, year);
				beginCalrendar.set(Calendar.MONTH, 0);
				beginCalrendar.set(Calendar.DAY_OF_MONTH, 1);
				beginCalrendar.set(Calendar.HOUR, 0);
				beginCalrendar.set(Calendar.MINUTE, 0);
				beginCalrendar.set(Calendar.SECOND, 0);
				System.out.println(beginCalrendar.getTime().toLocaleString());
				System.out.println("----------------------------------------------------------------"+dateToStandDate(beginCalrendar.getTime()));
				conditions.add(new Condition("and", null,
						"billMaster.validDate", ">=", dateToStandDate(beginCalrendar.getTime()),//beginClarendar.getTime(),//by xxm modify 2007-08-09
						null));
			} else {
				conditions
						.add(new Condition("and", null, "billMaster.validDate",
								//">=", startDate.getDate(), null)); //by xxm 2007-08-09
								">=", dateToStandDate(startDate.getDate()), null));
			}
		}
		
		//结束日期
		if (endDate.getDate() != null) {
			conditions.add(new Condition("and", null, "billMaster.validDate",
			        "<=", dateToStandDate(endDate.getDate()), null));
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
		if (tfProNo.getText() != null && (!tfProNo.getText().trim().equals(""))) {
			conditions.add(new Condition("and", null, "productNo", "=", tfProNo
					.getText(), null));
		}
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		return conditions;
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
			btnPrint.setBounds(637, 52, 65, 23);
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printReport();
				}
			});
		}
		return btnPrint;
	}

	/**
	 * 报表打印
	 * 
	 */
	private void printReport() {
		if (lsImpExpInfos == null || lsImpExpInfos.isEmpty()) {
			JOptionPane.showMessageDialog(DgImpExpChinBuyQuery.this, "没有列印的记录！",
					"提示！", JOptionPane.INFORMATION_MESSAGE);
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
			/*if (materialType == MaterielType.MATERIEL) {
				parameters.put("title", "原材料进出仓帐");
				parametersByCover.put("title", "原材料进出仓帐");
			} else if (materialType == MaterielType.FINISHED_PRODUCT) {
				parameters.put("title", "成品进出仓帐");
				parametersByCover.put("title", "成品进出仓帐");
			} else if (materialType == MaterielType.MACHINE) {
				parameters.put("title", "设备进出仓帐");
				parametersByCover.put("title", "设备进出仓帐");
			} else if (materialType == MaterielType.REMAIN_MATERIEL) {
				parameters.put("title", "边角料进出仓帐");
				parametersByCover.put("title", "边角料进出仓帐");
			} else if (materialType == MaterielType.BAD_PRODUCT) {
				parameters.put("title", "残次品进出仓帐");
				parametersByCover.put("title", "残次品进出仓帐");
			}*/
			parameters.put("title", "国内购买进出仓帐");
			parametersByCover.put("title", "国内购买进出仓帐");
			//
			// 明细
			//
			CommonDataSource imgExgDS = new CommonDataSource(lsImpExpInfos,
					maximumFractionDigits);
			InputStream masterReportStream = DgImpExpChinBuyQuery.class
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
				InputStream coverStream = DgImpExpChinBuyQuery.class
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

			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);

		} catch (JRException e1) {
			e1.printStackTrace();
		}
		btnPrint.setEnabled(true);
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setBounds(637, 85, 65, 23);
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 可选的单据类型
	 */
	private void fillBillType() {
		cmbBillType.removeAllItems();
		List billTypes = casAction.findBillTypeByProduceType(new Request(
				CommonVars.getCurrUser()), MaterielType.MATERIEL);
		for (int i = 0; i < billTypes.size(); i++) {
			BillType billType = (BillType) billTypes.get(i);
			
			if ("1002".equalsIgnoreCase(billType.getCode())// 不统计1002在产品起初单
					|| "1003".equalsIgnoreCase(billType.getCode())// 不统计1003直接进口
					|| "1004".equalsIgnoreCase(billType.getCode())// 不统计1004结转进口
					|| "1015".equalsIgnoreCase(billType.getCode())// 不统计1015已收货未结转期初单
					|| "1016".equalsIgnoreCase(billType.getCode())// 不统计1016未收货已结转期初单
					|| "1102".equalsIgnoreCase(billType.getCode())// 不统计1102料件退换
					|| "1103".equalsIgnoreCase(billType.getCode())// 不统计1103料件复出
					|| "1106".equalsIgnoreCase(billType.getCode())// 不统计1106结转料件退货单
					|| "1114".equalsIgnoreCase(billType.getCode())// 不统计1114受托加工料件出库
						) {
				continue;
			}
			
			CheckBoxListItem item = new CheckBoxListItem();
			item.setCode(billType.getCode());
			item.setName(billType.getName());

			cmbBillType.addItem(item);

		}
		this.cmbBillType.setRenderer(new CheckBoxListCellRenderer(cmbBillType));
		this.cmbBillType.setUI(new SteppedMetalComboBoxUI(225));
		this.setCmbBillTypeEvent(cmbBillType);
		this.cmbBillType.setSelectedItem(null);
	}

	/**
	 * 初始化仓库信息
	 */
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
						tf.setText("".equals(tf.getText())
								? item.getName()
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
	 * 填充客户名称选项
	 */
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

	/**
	 * 初始化表
	 * @param list
	 */
	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("BOM编号(料号)", "ptPart", 100));
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
						list.add(addColumn("发票号", "invoiceNo", 100));//
						//list.add(addColumn("制单号", "productNo", 70));
						list.add(addColumn("收送货商名称", "scmCoc.name", 100));

						list.add(addColumn("商品序号", "seqNum", 100));

						list.add(addColumn("工厂单位", "ptUnit.name", 60));

						list.add(addColumn("海关单位", "hsUnit.name", 100));
						list.add(addColumn("仓库", "wareSet.name", 80));
						list.add(addColumn("备注", "note", 100));

						return list;
					}
				});

		TableColumnModel cm = jTable.getColumnModel();
		ColumnGroup g_in = new ColumnGroup("进仓");
		g_in.add(cm.getColumn(6));
		g_in.add(cm.getColumn(7));
		g_in.add(cm.getColumn(8));

		ColumnGroup g_out = new ColumnGroup("出仓");
		g_out.add(cm.getColumn(9));
		g_out.add(cm.getColumn(10));
		g_out.add(cm.getColumn(11));

		ColumnGroup g_rs = new ColumnGroup("结存");
		g_rs.add(cm.getColumn(12));
		g_rs.add(cm.getColumn(13));

		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.setColumnModel(jTable.getColumnModel());
		header.addColumnGroup(g_in);
		header.addColumnGroup(g_out);
		header.addColumnGroup(g_rs);
		jTable.repaint();
	}

	/**
	 * This method initializes btnName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnName() {
		if (btnName == null) {
			btnName = new JButton();
			btnName.setBounds(new Rectangle(425, 65, 19, 22));
			btnName.setText("...");
			btnName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object object = CasQuery.getInstance()
							.findPtNameFromBillDetail(MaterielType.MATERIEL);
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
			tfPtNo.setBounds(new Rectangle(302, 40, 122, 22));
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
			btnPtNo.setBounds(new Rectangle(425, 40, 19, 22));
			btnPtNo.setText("...");
			btnPtNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
							.getFactoryAndFactualCustomsRalation(false, MaterielType.MATERIEL,
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
			tfEndPtNo.setBounds(new Rectangle(494, 40, 109, 22));
			tfEndPtNo.setEditable(false);
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
			btnEndPtNo.setBounds(new Rectangle(606, 40, 19, 22));
			btnEndPtNo.setEnabled(false);
			btnEndPtNo.setText("...");
			btnEndPtNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalation(false, MaterielType.MATERIEL,
							new ArrayList());
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel)sm.getObject();
						tfEndPtNo.setText(m == null ? "" : m.getPtNo());
					}					
				}
			});
		}
		return btnEndPtNo;
	}

	/**
	 * This method initializes tfHsName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsName() {
		if (tfHsName == null) {
			tfHsName = new JTextField();
			tfHsName.setBounds(new Rectangle(90, 65, 128, 22));
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
			btnHsName.setBounds(new Rectangle(218, 65, 19, 22));
			btnHsName.setText("...");
			btnHsName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object object = CasQuery.getInstance()
							.findHsNameFromStatCusNameRelationHsn(MaterielType.MATERIEL);
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
			tfHsSpec.setBounds(new Rectangle(90, 90, 128, 20));
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
			btnHsSpec.setBounds(new Rectangle(218, 90, 19, 20));
			btnHsSpec.setText("...");
			btnHsSpec.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object object = CasQuery.getInstance()
							.findHsSpecFromStatCusNameRelationHsn(MaterielType.MATERIEL,
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
			tfBillNo.setBounds(new Rectangle(495, 65, 130, 22));
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
			cbIsTotalFrontAmount.setBounds(new Rectangle(526, 115, 102, 20));
			cbIsTotalFrontAmount.setText("统计上期结存");
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
			tfProNo.setBounds(new Rectangle(495, 90, 130, 20));
			tfProNo.setEditable(false);
		}
		return tfProNo;
	}

	
	/**
	 * date->日期型"yyyy-MM-dd"
	 */
	public static Date dateToStandDate(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String defaultDate = bartDateFormat.format(date);
		return java.sql.Date.valueOf(defaultDate);
	}
	
} // @jve:decl-index=0:visual-constraint="10,10"
