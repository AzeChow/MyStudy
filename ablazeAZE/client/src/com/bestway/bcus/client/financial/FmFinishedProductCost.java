package com.bestway.bcus.client.financial;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.ComboBoxEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CheckBoxListCellRenderer;
import com.bestway.bcus.client.common.CheckBoxListItem;
import com.bestway.bcus.client.common.CheckBoxListItemForFinancial;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.SteppedMetalComboBoxUI;
import com.bestway.bcus.financial.action.FinancialAction;
import com.bestway.bcus.financial.entity.ProductAccount;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.DateValueListener;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author
 * 成品成本进出仓帐
 * 
 */
public class FmFinishedProductCost extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;
	private JPanel pnMain = null;
	private JCalendarComboBox cbbStartDate = null;
	private JCalendarComboBox cbbEndDate = null;
	private JSplitPane spnMain = null;
	private JPanel pnBottom = null;
	private JPanel pnTop = null;
	private JTextField tfName = null;
	private JTable tbMain = null;
	private JScrollPane spnTable = null;
	private JButton btnQuery = null;
	private JButton btnPrint = null;
	private JButton btnExit = null;
	private FinancialAction financialAction = null;
	private JComboBox cmbBillType = null;
	/**
	 * 数据源
	 */
	private List impExpInfos = null;
	private JButton btnName = null;
	private DefaultFormatterFactory defaultFormatterFactory = null;
	private NumberFormatter numberFormatter = null;
	/**
	 * 小数位控制参数
	 */
	private Integer maximumFractionDigits = CasCommonVars.getOtherOption()
			.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
			.getOtherOption().getInOutMaximumFractionDigits();  //  @jve:decl-index=0:
	private JButton btnReQuery = null;
	private JButton btnInput = null;
	private JTableListModel tableModel = null;
	/**
	 * 构造函数
	 * This is the default constructor
	 */
	public FmFinishedProductCost() {
		super();
		financialAction = (FinancialAction) CommonVars.getApplicationContext().getBean(
				"financialAction");
		initialize();
		initUIComponents();
	}

	/**
	 * 初始化组件
	 */
	private void initUIComponents() {
		fillWareSet();
		fillBillType();
		initTable(new ArrayList());
		
		Calendar currentDate = Calendar.getInstance();
		
		Calendar beginClarendar = Calendar.getInstance();
		
		beginClarendar.set(Calendar.YEAR, currentDate.get(Calendar.YEAR));
		beginClarendar.set(Calendar.MONTH, currentDate.get(Calendar.MONTH));
		beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
		this.cbbStartDate.setDate(beginClarendar.getTime());
		this.cbbStartDate.setCalendar(beginClarendar);

		Calendar endClarendar = Calendar.getInstance();
		endClarendar.set(Calendar.YEAR, currentDate.get(Calendar.YEAR));
		endClarendar.set(Calendar.MONTH, currentDate.get(Calendar.MONTH));
		endClarendar.set(Calendar.DAY_OF_MONTH, currentDate.getActualMaximum(Calendar.DAY_OF_MONTH));
		this.cbbEndDate.setDate(endClarendar.getTime());
		this.cbbEndDate.setCalendar(endClarendar);

	}
	private void fillBillType() {
		cmbBillType.removeAllItems();
		List billTypes = financialAction.findBillType(new Request(CommonVars.getCurrUser()), FinancialCommon.FINANCIAL_TYPE_PRODUCT_ACCOUNT);
		String defaultItemName = "";
		for (int i = 0; i < billTypes.size(); i++) {
			BillType billType = (BillType) billTypes.get(i);

			CheckBoxListItemForFinancial item = new CheckBoxListItemForFinancial();
			item.setCode(billType.getCode());
			item.setName(billType.getName());
			
			if(billType.getCode().equals("2001") 
					|| billType.getCode().equals("2002")
					|| billType.getCode().equals("2103")
					|| billType.getCode().equals("2101")
					|| billType.getCode().equals("2102")
					|| billType.getCode().equals("2009")
					|| billType.getCode().equals("2003")
					|| billType.getCode().equals("2104")){
				defaultItemName += billType.getName() + ",";
				item.setIsSelected(Boolean.TRUE);
			}

			cmbBillType.addItem(item);
		}
		this.cmbBillType.setSelectedItem(null);
		this.cmbBillType.setRenderer(new CheckBoxListCellRenderer(cmbBillType));
		this.cmbBillType.setUI(new SteppedMetalComboBoxUI(225));
		this.setCmbBillTypeEvent(cmbBillType);
		
		if(!"".equals(defaultItemName)){
			defaultItemName = defaultItemName.substring(0, defaultItemName.length() - 1);
		}
		ComboBoxEditor com = cmbBillType.getEditor();
		JTextField tf = (JTextField) com.getEditorComponent();
		tf.setText(defaultItemName);
	}
	/**
	 * 初始化标题和尺寸
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("成品成本进出仓帐");
		this.setSize(733, 541);
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
			jContentPane.add(getPnMain(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes pnMain
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnMain() {
		if (pnMain == null) {
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel = new JLabel();
			pnMain = new JPanel();
			pnMain.setLayout(new BorderLayout());
			pnMain.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0,
					0, 0));
			jLabel.setText("0");
			jLabel.setBounds(3, 34, 37, 32);
			jLabel1.setText("JLabel");
			jLabel1.setBounds(3, 65, 37, 32);
			jLabel2.setText("JLabel");
			jLabel2.setBounds(3, 96, 37, 32);
			jLabel3.setText("JLabel");
			jLabel3.setBounds(3, 1, 37, 32);
			jLabel4.setText("日期 从");
			pnMain.add(getSpnMain(), java.awt.BorderLayout.CENTER);
		}
		return pnMain;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (cbbStartDate == null) {
			cbbStartDate = new JCalendarComboBox();
			cbbStartDate.setBounds(312, 20, 100, 20);
			cbbStartDate.addDateValueListener(new DateValueListener() {

				public void valueChanged(Date newDate) {
					if (cbbStartDate.getDate() == null) {
						return;
					}
					Calendar beginDate = cbbStartDate.getCalendar();
					Calendar endDate = null;
					if (beginDate != null) {
						endDate = (Calendar) beginDate.clone();
						endDate.set(Calendar.DAY_OF_MONTH, beginDate.getActualMaximum(Calendar.DAY_OF_MONTH));
					}
					beginDate.set(Calendar.DAY_OF_MONTH, 1);
					cbbStartDate.setCalendar(beginDate);
					cbbEndDate.setCalendar(endDate);
				}
			});
		}
		return cbbStartDate;
	}
	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setLocation(438, 20);
			cbbEndDate.setSize(100, 20);			
			cbbEndDate.addDateValueListener(new DateValueListener() {
				public void valueChanged(Date newDate) {
					if (cbbEndDate.getDate() == null) {
						return;
					}
					Calendar endDate = cbbEndDate.getCalendar();
					Calendar beginDate = null;
					if (endDate != null) {
						beginDate = (Calendar) endDate.clone();
						beginDate.set(Calendar.DAY_OF_MONTH, 1);
					}
					endDate.set(Calendar.DAY_OF_MONTH, endDate.getActualMaximum(Calendar.DAY_OF_MONTH));
					cbbStartDate.setCalendar(beginDate);
					cbbEndDate.setCalendar(endDate);
				}
			});
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes spnMain
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getSpnMain() {
		if (spnMain == null) {
			spnMain = new JSplitPane();
			spnMain.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			spnMain.setDividerSize(1);
			spnMain.setDividerLocation(77);
			spnMain.setTopComponent(getPnTop());
			spnMain.setBottomComponent(getPnBottom());
		}
		return spnMain;
	}

	/**
	 * This method initializes pnBottom
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnBottom() {
		if (pnBottom == null) {
			pnBottom = new JPanel();
			pnBottom.setLayout(new BorderLayout());
			pnBottom.add(getSpnTable(), java.awt.BorderLayout.CENTER);
		}
		return pnBottom;
	}

	/**
	 * This method initializes pnTop
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTop() {
		if (pnTop == null) {
			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			pnTop = new JPanel();
			pnTop.setLayout(null);
			jLabel7.setText("数据来源");
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel7.setBounds(12, 45, 78, 20);
			jLabel9.setText("成品名称");
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setBounds(12, 20, 78, 20);
			jLabel10.setText("日期 从");
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setBounds(260, 20, 50, 20);
			pnTop.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
				"过滤条件",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
				new java.awt.Color(51, 51, 51)))
			;
			jLabel5.setBounds(420, 20, 15, 20);
			jLabel5.setText("止");
			pnTop.add(getJComboBox3(), null);
			pnTop.add(jLabel7, null);
			pnTop.add(jLabel9, null);
			pnTop.add(getJComboBox(), null);
			pnTop.add(getJCalendarComboBox(), null);
			pnTop.add(getJCalendarComboBox1(), null);
			pnTop.add(getJButton(), null);
//			pnTop.add(getBtnPrint(), null);
			pnTop.add(getSave(), null);
			pnTop.add(getJButton2(), null);
			pnTop.add(jLabel10, null);
			pnTop.add(jLabel5, null);
			pnTop.add(getBtnName(), null);
			pnTop.add(getBtnReQuery(), null);
			pnTop.add(getBtnInput(), null);
		}
		return pnTop;
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JTextField getJComboBox3() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(95, 20, 128, 20);
		}
		return tfName;
	}

	/**
	 * This method initializes tbMain
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbMain() {
		if (tbMain == null) {
			tbMain = new JTable() {
				protected JTableHeader createDefaultTableHeader() {
					return new GroupableTableHeader(columnModel);
				}
			};
		}
		return tbMain;
	}

	/**
	 * This method initializes spnTable
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpnTable() {
		if (spnTable == null) {
			spnTable = new JScrollPane();
			spnTable.setViewportView(getTbMain());
		}
		return spnTable;
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
			btnQuery.setBounds(330, 44, 65, 22);
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Map conditions = getConditions();
					new searchThread(conditions).start();
				}
			});
		}
		return btnQuery;
	}
	JButton btnSave = null;
	private JButton getSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.setBounds(490, 44, 65, 22);
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new saveThread(getUpdateDataList()).start();					
				}
			});
		}
		return btnSave;
	}
	private List getUpdateDataList(){
		List list = new ArrayList();
		List oriList = tableModel.getList();
		for(int i = 0 ; i < oriList.size() ; i ++){
			ProductAccount productAccount = (ProductAccount) oriList.get(i);
			if(productAccount.getIsSelected())
				list.add(productAccount);
		}
		
		return list;
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
				CheckBoxListItemForFinancial item = (CheckBoxListItemForFinancial) listBox.getModel()
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
				Map<String, CheckBoxListItemForFinancial> checkBoxListItemMap = new HashMap<String, CheckBoxListItemForFinancial>();

				int size = listBox.getModel().getSize();
				for (int i = 0; i < size; i++) {
					CheckBoxListItemForFinancial item = (CheckBoxListItemForFinancial) listBox
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
					CheckBoxListItemForFinancial item = checkBoxListItemMap.get(str);
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
				Iterator<CheckBoxListItemForFinancial> iterator = checkBoxListItemMap
						.values().iterator();
				for (; iterator.hasNext();) {
					CheckBoxListItemForFinancial checkBoxListItem = iterator.next();
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
	 * 查询线程类
	 * @author hw
	 *
	 */
	class searchThread extends Thread {
		//查询条件
		Map conditions = null;
		/**
		 * 构造函数
		 * @param conditions
		 * @param condition
		 * @param conditionss
		 * @param tiaojian
		 * @param tiaojian2
		 */
		public searchThread(Map conditions) {
			this.conditions = conditions;
		}

		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			btnQuery.setEnabled(false);
			btnReQuery.setEnabled(false);
			btnSave.setEnabled(false);
			try {
				CommonProgress.setMessage(flag, "系统正获取数据，请稍后...");
				impExpInfos = financialAction.findFinishedProductCostList(new Request(
						CommonVars.getCurrUser()), conditions);
				CommonProgress.closeProgressDialog(flag);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmFinishedProductCost.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				if (impExpInfos != null && !impExpInfos.isEmpty()) {
					tableModel.setList(impExpInfos);
				} else {
					initTable(new ArrayList());
					JOptionPane.showMessageDialog(FmFinishedProductCost.this,
							"没有查到符合条件的记录！", "提示！", 2);
				}
			}
			btnQuery.setEnabled(true);
			btnReQuery.setEnabled(true);
		}
	}

	/**
	 * 获取查询条件
	 * @return
	 */
	private Map getConditions() {
		Map conditions = new HashMap();

		if (tfName.getText() != null && !tfName.getText().trim().equals("")) {
			conditions.put("", tfName.getText());
		}
		if (cbbStartDate.getDate() != null) {
			conditions.put("startDate", cbbStartDate.getDate());
		}
		if (cbbEndDate.getDate() != null) {
			Date endDate = cbbEndDate.getDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.set(Calendar.HOUR, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999999);
			
			conditions.put("endDate", calendar.getTime());
		}
		List billType = new ArrayList();
		for (int j = 0; j < this.cmbBillType.getModel().getSize(); j++) {
			CheckBoxListItemForFinancial item = (CheckBoxListItemForFinancial) this.cmbBillType.getModel().getElementAt(j);
			if (item.getIsSelected()) {
				billType.add(item.getCode());
			}
		}
		conditions.put("billType", billType);
		return conditions;
	}

	/**
	 * 获取查询条件
	 * @return
	 */
	private List getCondition() {
		List<Condition> condition = new ArrayList<Condition>();

		if (tfName.getText() != null && !tfName.getText().trim().equals("")) {
			condition.add(new Condition("and", null, "ptName", " = ", tfName
					.getText(), null));
		}
		if (cbbStartDate.getDate() != null) {
			condition.add(new Condition("and", null, "billMaster.validDate",
					">=", cbbStartDate.getDate(), null));
		}
		if (cbbEndDate.getDate() != null) {
			condition.add(new Condition("and", null, "billMaster.validDate",
					"<=", cbbEndDate.getDate(), null));
		}
		
		condition.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		return condition;
	}

	/**
	 * 获取查询条件
	 * @return
	 */
	private List getConditionss() {
		List<Condition> conditionss = new ArrayList<Condition>();

		if (tfName.getText() != null && !tfName.getText().trim().equals("")) {
			conditionss.add(new Condition("and", null, "ptName", " = ", tfName
					.getText(), null));
		}
		if (cbbStartDate.getDate() != null) {
			conditionss.add(new Condition("and", null, "billMaster.validDate",
					">=", cbbStartDate.getDate(), null));
		}
		if (cbbEndDate.getDate() != null) {
			conditionss.add(new Condition("and", null, "billMaster.validDate",
					"<=", cbbEndDate.getDate(), null));
		}
		conditionss.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		return conditionss;
	}

	/**
	 * 获取查询条件
	 * @return
	 */
	private List getTiaojian() {
		List<Condition> tiaojian = new ArrayList<Condition>();

		if (tfName.getText() != null && !tfName.getText().trim().equals("")) {
			tiaojian.add(new Condition("and", null, "ptName", " = ", tfName
					.getText(), null));
		}
		if (cbbStartDate.getDate() != null) {
			tiaojian.add(new Condition("and", null, "billMaster.validDate",
					">=", cbbStartDate.getDate(), null));
		}
		if (cbbEndDate.getDate() != null) {
			tiaojian.add(new Condition("and", null, "billMaster.validDate",
					"<=", cbbEndDate.getDate(), null));
		}
		tiaojian.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		return tiaojian;
	}

	/**
	 * 获取查询条件
	 * @return
	 */
	private List getTiaojian2() {
		List<Condition> tiaojian2 = new ArrayList<Condition>();

		if (tfName.getText() != null && !tfName.getText().trim().equals("")) {
			tiaojian2.add(new Condition("and", null, "ptName", " = ", tfName
					.getText(), null));
		}
		if (cbbStartDate.getDate() != null) {
			tiaojian2.add(new Condition("and", null, "billMaster.validDate",
					">=", cbbStartDate.getDate(), null));
		}
		if (cbbEndDate.getDate() != null) {
			tiaojian2.add(new Condition("and", null, "billMaster.validDate",
					"<=", cbbEndDate.getDate(), null));
		}
		tiaojian2.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		return tiaojian2;
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
			btnPrint.setBounds(560, 44, 65, 22);
			btnPrint.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (impExpInfos == null || impExpInfos.isEmpty()) {
						JOptionPane.showMessageDialog(FmFinishedProductCost.this,
								"没有列印的记录！", "提示！",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					btnPrint.setEnabled(false);
					try {
						CommonDataSource imgExgDS = new CommonDataSource(
								impExpInfos, maximumFractionDigits);
						List<BaseCompany> company = new ArrayList<BaseCompany>();
						company.add(CommonVars.getCurrUser().getCompany());
						InputStream masterReportStream = FmMaterialAccount.class
								.getResourceAsStream("report/CustomInoutWareReportWaiFaConsignQueryBack.jasper");
						Map<String, Object> parameters = new HashMap<String, Object>();
						// if (materialType == MaterielType.MATERIEL) {
						parameters.put("title", "成品成本进出仓帐");
						// } else if (materialType ==
						// MaterielType.FINISHED_PRODUCT) {
						// parameters.put("title", "成品进出仓帐");
						// } else if (materialType == MaterielType.MACHINE) {
						// parameters.put("title", "设备进出仓帐");
						// } else if (materialType ==
						// MaterielType.REMAIN_MATERIEL) {
						// parameters.put("title", "边角料进出仓帐");
						// } else if (materialType == MaterielType.BAD_PRODUCT)
						// {
						// parameters.put("title", "残次品进出仓帐");
						// }
						JasperPrint jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, imgExgDS);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
					btnPrint.setEnabled(true);
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
	private JButton getJButton2() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setBounds(560, 44, 65, 22);
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 初始化仓库下拉框
	 */
	private void fillWareSet() {
	}

	/**
	 * 初始化主表格
	 * @param list
	 */
	private void initTable(final List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter(maximumFractionDigits) {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("报关名称", "hsName", 100));
				list.add(addColumn("报关型号规格", "hsSpec", 150));
				list.add(addColumn("单位", "hsUnit.name", 80));

				list.add(addColumn("数量", "previousCount", 80));
				list.add(addColumn("总金额", "previousAmount", 80));

				list.add(addColumn("数量", "thisInCount", 80));
				list.add(addColumn("总金额", "thisInAmount", 80));

				list.add(addColumn("直接出口数量", "thisExportCount", 80));
				list.add(addColumn("直接出口总金额", "thisExportAmount", 85));

				list.add(addColumn("发出数量", "thisOutCount", 80));
				list.add(addColumn("发出总金额", "thisOutAmount", 80));

				list.add(addColumn("数量", "balanceCount", 80));
				list.add(addColumn("总金额", "balanceAmount", 80));
				
				return list;
			}
		};
		tableModel = new JTableListModel(tbMain, list, jTableListModelAdapter);

		jTableListModelAdapter.setEditableColumn(4);
		jTableListModelAdapter.setEditableColumn(5);
		jTableListModelAdapter.setEditableColumn(6);
		jTableListModelAdapter.setEditableColumn(7);
		jTableListModelAdapter.setEditableColumn(8);
		jTableListModelAdapter.setEditableColumn(9);
		jTableListModelAdapter.setEditableColumn(10);
		jTableListModelAdapter.setEditableColumn(11);
		tableModel.setAllowSetValue(true);
		final JFormattedTextField tf = new JFormattedTextField();
		tf.setFormatterFactory(getDefaultFormatterFactory());
		tf.addFocusListener(new FocusAdapter() {
			public void focusGained(final FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						((JTextField) e.getSource()).selectAll();
					}
				});
			}

			public void focusLost(FocusEvent e) {

			}
		});
		tf.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
			}

			public void insertUpdate(DocumentEvent e) {
				commitValueChange(tf);
			}

			public void removeUpdate(DocumentEvent e) {
				commitValueChange(tf);
			}
		});
		tbMain.getColumnModel().getColumn(4).setCellEditor(new JTextFieldEditor(tf));
		tbMain.getColumnModel().getColumn(4).setCellRenderer(new ForcedEditTableCellRenderer());
		tbMain.getColumnModel().getColumn(5).setCellEditor(new JTextFieldEditor(tf));
		tbMain.getColumnModel().getColumn(5).setCellRenderer(new ForcedEditTableCellRenderer());
		tbMain.getColumnModel().getColumn(6).setCellEditor(new JTextFieldEditor(tf));
		tbMain.getColumnModel().getColumn(6).setCellRenderer(new ForcedEditTableCellRenderer());
		tbMain.getColumnModel().getColumn(7).setCellEditor(new JTextFieldEditor(tf));
		tbMain.getColumnModel().getColumn(7).setCellRenderer(new ForcedEditTableCellRenderer());
		tbMain.getColumnModel().getColumn(8).setCellEditor(new JTextFieldEditor(tf));
		tbMain.getColumnModel().getColumn(8).setCellRenderer(new ForcedEditTableCellRenderer());
		tbMain.getColumnModel().getColumn(9).setCellEditor(new JTextFieldEditor(tf));
		tbMain.getColumnModel().getColumn(9).setCellRenderer(new ForcedEditTableCellRenderer());
		tbMain.getColumnModel().getColumn(10).setCellEditor(new JTextFieldEditor(tf));
		tbMain.getColumnModel().getColumn(10).setCellRenderer(new ForcedEditTableCellRenderer());
		tbMain.getColumnModel().getColumn(11).setCellEditor(new JTextFieldEditor(tf));
		tbMain.getColumnModel().getColumn(11).setCellRenderer(new ForcedEditTableCellRenderer());
		
		TableColumnModel cm = tbMain.getColumnModel();
		ColumnGroup g_goods = new ColumnGroup("商品名称");
		g_goods.add(cm.getColumn(1));
		g_goods.add(cm.getColumn(2));
		g_goods.add(cm.getColumn(3));

		ColumnGroup g_last = new ColumnGroup("上月结存");
		g_last.add(cm.getColumn(4));
		g_last.add(cm.getColumn(5));

		ColumnGroup g_this = new ColumnGroup("本月进入");
		g_this.add(cm.getColumn(6));
		g_this.add(cm.getColumn(7));
		
		ColumnGroup g_out = new ColumnGroup("本月发货(料)");
		g_out.add(cm.getColumn(8));
		g_out.add(cm.getColumn(9));
		g_out.add(cm.getColumn(10));
		g_out.add(cm.getColumn(11));
		
		ColumnGroup g_leave = new ColumnGroup("本月结存");
		g_leave.add(cm.getColumn(12));
		g_leave.add(cm.getColumn(13));

		GroupableTableHeader header = (GroupableTableHeader) tbMain
				.getTableHeader();
		header.setColumnModel(tbMain.getColumnModel());
		header.addColumnGroup(g_goods);
		header.addColumnGroup(g_last);
		header.addColumnGroup(g_this);
		header.addColumnGroup(g_out);
		header.addColumnGroup(g_leave);
		tbMain.repaint();
	}
	protected DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	public NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
			DecimalFormat decimalFormat1 = new DecimalFormat(); // @jve:decl-index=0:
			decimalFormat1.setMaximumFractionDigits(6);
			decimalFormat1.setGroupingSize(0);
			numberFormatter.setFormat(decimalFormat1);
		}
		return numberFormatter;
	}
	/**
	 * 编辑列
	 */
	class JTextFieldEditor extends DefaultCellEditor implements KeyListener,
			FocusListener {
		private JFormattedTextField tf;

		private JTable table = null;

		public JTextFieldEditor(JFormattedTextField tf) {
			super(tf);
			this.tf = tf;
			this.setClickCountToStart(1);
			this.tf.addKeyListener(this);
			this.tf.addFocusListener(this);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value != null) {
				tf.setValue(Double.parseDouble(value.toString()));
			}

			this.table = table;
			return tf;
		}

		public Object getCellEditorValue() {
			if (tf.getValue() == null) {
				return 0.0;
			} else {
				return Double.valueOf(tf.getValue().toString());
			}
		}

		public void focusGained(FocusEvent e) {

		}

		public void keyTyped(KeyEvent e) {

		}

		public void keyPressed(KeyEvent e) {

		}

		public void keyReleased(KeyEvent e) {
		}

		public void focusLost(FocusEvent e) {
//			if (table == null || table.getModel() == null) {
//				return;
//			}
//			// if (tf.getValue() != null) {
//			// double waste = Double.parseDouble(tf.getValue().toString());
//			// if (waste >= 1 || waste < 0) {
//			// JOptionPane.showMessageDialog(
//			// DgEditMaterialBomUnitWaste.this, "损耗不能大于等于1或者小于0",
//			// "提示", JOptionPane.INFORMATION_MESSAGE);
//			// // tf.setValue(0.0);
//			// tf.requestFocus();
//			// return;
//			// }
//			// }
//			JTableListModel tableModel = (JTableListModel) this.table
//					.getModel();
//			MaterialBomDetail detail = (MaterialBomDetail) tableModel
//					.getCurrentRow();
//			double unitWaste = (detail.getUnitWaste() == null ? 0.0 : detail
//					.getUnitWaste());
//			double waste = (detail.getWaste() == null ? 0.0 : detail.getWaste());
//			if (waste >= 1 || waste < 0) {
//				// JOptionPane.showMessageDialog(DgEditBomApplyUnitWaste.this,
//				// "损耗不能大于等于100或者小于0", "提示",
//				// JOptionPane.INFORMATION_MESSAGE);
//				tf.setValue(0.0);
//				waste = 0.0;
//			}
//			double unitUsed = CommonVars.getDoubleByDigit(unitWaste
//					/ (1 - waste), 5);
//			detail.setUnitUsed(unitUsed);
			commitValueChange(tf);
		}
	}
	public class ForcedEditTableCellRenderer extends DefaultTableCellRenderer {

		/** Creates a new instance of ForcedEditTableCellRenderer */
		public ForcedEditTableCellRenderer() {
			super();
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// Obtain the default component.
			Component comp = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);

			if (hasFocus && isSelected) {
				TableModel tblModel = table.getModel();
				if (tblModel.isCellEditable(row, column)) {
					// Cell is editable
					table.editCellAt(row, column);
					table.getEditorComponent().requestFocus();
					JFormattedTextField tf = (JFormattedTextField) table.getEditorComponent();
					if (value == null) {
						tf.setValue(0.0);
					} else {
						tf.setValue(Double.parseDouble(value.toString()));
					}
					tf.selectAll();
				}
			}
			return comp;
		}

	}
	private void commitValueChange(final JFormattedTextField tf) {
		try {
			tf.commitEdit();
		} catch (Exception ex) {
		}
		if (tableModel == null || tableModel.getCurrentRow() == null) {
			return;
		}
		ProductAccount productAccount = (ProductAccount) tableModel.getCurrentRow();

		productAccount.setBalanceCount(FinancialCommon.sub(FinancialCommon.add(productAccount.getPreviousCount(), productAccount.getThisInCount()), FinancialCommon.add(productAccount.getThisOutCount(), productAccount.getThisExportCount())));
		productAccount.setBalanceAmount(FinancialCommon.sub(FinancialCommon.add(productAccount.getPreviousAmount(), productAccount.getThisInAmount()), FinancialCommon.add(productAccount.getThisOutAmount(), productAccount.getThisExportAmount())));
		
		productAccount.setIsSelected(Boolean.TRUE);
		
		this.btnSave.setEnabled(Boolean.TRUE);
	}
	
	/**
	 * This method initializes btnName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnName() {
		if (btnName == null) {
			btnName = new JButton();
			btnName.setBounds(new Rectangle(226, 20, 20, 20));
			btnName.setText("...");
			btnName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = MaterielType.FINISHED_PRODUCT;
					Object object = CasQuery.getInstance()
							.findHsNameFromStatCusNameRelationHsn(materialType);
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
	 * This method initializes btnReQuery	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnReQuery() {
		if (btnReQuery == null) {
			btnReQuery = new JButton();
			btnReQuery.setBounds(new Rectangle(398, 44, 90, 22));
			btnReQuery.setText("重新计算");
			btnReQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Map conditions = getConditions();
					conditions.put("isReLoad", "1");
					
					new searchThread(conditions).start();
				}
			});
		}
		return btnReQuery;
	}

	/**
	 * This method initializes btnInput	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnInput() {
		if (btnInput == null) {
			btnInput = new JButton();
			btnInput.setBounds(new Rectangle(545, 19, 115, 22));
			btnInput.setText("导入修改数据");
			btnInput.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgFinishedProductCostImport dgImport = new DgFinishedProductCostImport();
					if(cbbStartDate.getDate() == null){
						JOptionPane.showMessageDialog(FmFinishedProductCost.this, "导入前，请选择月份", "提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					dgImport.setImportMonth(FinancialCommon.getThisMonthStr(cbbStartDate.getDate()));
					dgImport.setVisible(Boolean.TRUE);	
				}
			});
		}
		return btnInput;
	}
	private JComboBox getJComboBox() {
		if (cmbBillType == null) {
			cmbBillType = new JComboBox();
			cmbBillType.setBounds(95, 45, 226, 20);
		}
		return cmbBillType;
	}
	/**
	 * 保存线程类
	 * @author hw
	 *
	 */
	class saveThread extends Thread {
		//保存列表
		List list = null;

		/**
		 * 构造函数
		 * @param list
		 */
		public saveThread(List list) {
			this.list = list;
		}

		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			boolean isOk = false;
			try {
				btnSave.setEnabled(Boolean.FALSE);
				CommonProgress.setMessage(flag, "系统正在更新数据，请稍后...");
				financialAction.updateFinishedProductCost(new Request(CommonVars.getCurrUser()), list);
				CommonProgress.closeProgressDialog(flag);
				isOk = true;
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				btnSave.setEnabled(Boolean.TRUE);
			} finally {
//				btnSave.setEnabled(Boolean.FALSE);
			}
			if(isOk){
				JOptionPane.showMessageDialog(FmFinishedProductCost.this,
						"更新数据成功！", "提示！", 2);
			}else{
				JOptionPane.showMessageDialog(FmFinishedProductCost.this,
						"更新数据失败：！", "提示", 2);
			}
		}
	}
}
