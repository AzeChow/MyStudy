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
import javax.swing.SwingConstants;
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
import com.bestway.bcus.client.common.CheckBoxListItemForFinancial;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.SteppedMetalComboBoxUI;
import com.bestway.bcus.financial.action.FinancialAction;
import com.bestway.bcus.financial.entity.MonthlyProductionAndSales;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.DateValueListener;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author
 * 每月产量与销量
 * 
 */
public class FmMonthlyProductionAndSales extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;
	private JPanel pnMain = null;
	private JCalendarComboBox cbbStartDate = null;
	private JCalendarComboBox cbbEndDate = null;
	private JSplitPane spnMain = null;
	private JPanel pnBottom = null;
	private JPanel pnTop = null;
	private JComboBox cmbBillType = null;
	private JTextField tfName = null;
	private JTable tbMain = null;
	private JScrollPane spnTable = null;
	private JButton btnQuery = null;
	private JButton btnReQuery = null;
	private JButton btnPrint = null;
	private JLabel jLabel101 = null;
	private JButton btnExit = null;
	private FinancialAction financialAction = null;
	/**
	 * 物料类型
	 */
	private String materialType = MaterielType.MATERIEL;  //  @jve:decl-index=0:
	private JTableListModel tableModel = null;
	private DefaultFormatterFactory defaultFormatterFactory = null;
	private NumberFormatter numberFormatter = null;
	/**
	 * 数据源
	 */
	private List impExpInfos = null;
	private JButton btnName = null;
	/**
	 * 小数位控制参数
	 */
	private Integer maximumFractionDigits = CasCommonVars.getOtherOption()
			.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
			.getOtherOption().getInOutMaximumFractionDigits();  //  @jve:decl-index=0:
	private JButton btnImport = null;
	private JButton btnSave = null;

	/**
	 * 构造函数
	 * This is the default constructor
	 */
	public FmMonthlyProductionAndSales() {
		super();
		financialAction = (FinancialAction) CommonVars.getApplicationContext().getBean("financialAction");
		
		initialize();
		initUIComponents();
	}

	/**
	 * 初始化组件
	 */
	private void initUIComponents() {
		fillBillType(materialType);
		fillCustomer();
		fillWareSet();

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
		
		this.btnSave.setEnabled(Boolean.FALSE);
	}

	/**
	 * 初始化标题和尺寸
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("每月产量与销量");
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
			cbbStartDate.setBounds(305, 20, 140, 20);
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
			cbbEndDate.setLocation(468, 20);
			cbbEndDate.setSize(140, 20);			
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
			pnTop = new JPanel();
			pnTop.setLayout(null);
			jLabel9.setText("成品商品名称");
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setBounds(20, 20, 75, 20);
			jLabel10.setText("日期 从");
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setBounds(253, 20, 50, 20);
			jLabel101 = new JLabel();
			jLabel101.setBounds(new Rectangle(20, 45, 75, 20));
			jLabel101.setText("单据类型");
			jLabel101.setHorizontalAlignment(SwingConstants.RIGHT);
			pnTop.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
				"过滤条件",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
				new java.awt.Color(51, 51, 51)))
			;
			jLabel5.setBounds(449, 20, 15, 20);
			jLabel5.setText("止");
			pnTop.add(getJComboBox3(), null);
			pnTop.add(jLabel9, null);
			pnTop.add(jLabel101, null);
			pnTop.add(getJCalendarComboBox(), null);
			pnTop.add(getJCalendarComboBox1(), null);
			pnTop.add(getJButton(), null);
			pnTop.add(getJComboBox(), null);
			pnTop.add(getBtnReQuery(), null);
//			pnTop.add(getBtnPrint(), null);
			pnTop.add(getJButton2(), null);
			pnTop.add(jLabel10, null);
			pnTop.add(jLabel5, null);
			pnTop.add(getBtnName(), null);
			pnTop.add(getBtnImport(), null);
			pnTop.add(getBtnSave(), null);
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
			tfName.setBounds(97, 20, 128, 20);
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
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (cmbBillType == null) {
			cmbBillType = new JComboBox();
			cmbBillType.setBounds(97, 45, 148, 20);
		}
		return cmbBillType;
	}
	private void fillBillType(String materialType) {
		cmbBillType.removeAllItems();
		List billTypes = financialAction.findBillType(new Request(CommonVars.getCurrUser()), FinancialCommon.FINANCIAL_TYPE_MONTHLY_PRODUCTION_AND_SALES);
		String defaultItemName = "";
		for (int i = 0; i < billTypes.size(); i++) {
			BillType billType = (BillType) billTypes.get(i);
			CheckBoxListItemForFinancial item = new CheckBoxListItemForFinancial();
			item.setCode(billType.getCode());
			item.setName(billType.getName());
			if(billType.getCode().equals("2002")
					|| billType.getCode().equals("2103")
					|| billType.getCode().equals("2101")
					|| billType.getCode().equals("2102")
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
					CheckBoxListItemForFinancial item = (CheckBoxListItemForFinancial) listBox.getModel().getElementAt(i);
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setBounds(253, 44, 65, 22);
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Map conditions = getConditions();
					new searchThread(conditions).start();
				}
			});
		}
		return btnQuery;
	}

	private JButton getBtnReQuery() {
		if (btnReQuery == null) {
			btnReQuery = new JButton();
			btnReQuery.setBounds(new Rectangle(327, 44, 90, 22));
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
				impExpInfos = financialAction.findMonthlyProductionAndSalesList(new Request(CommonVars.getCurrUser()), conditions);
				CommonProgress.closeProgressDialog(flag);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmMonthlyProductionAndSales.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				if (impExpInfos != null && !impExpInfos.isEmpty()) {
					tableModel.setList(impExpInfos);
				} else {
					initTable(new ArrayList());
					JOptionPane.showMessageDialog(FmMonthlyProductionAndSales.this,
							"没有查到符合条件的记录！", "提示！", 2);
				}
				btnSave.setEnabled(Boolean.FALSE);
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
			conditions.put("hsName", tfName.getText().trim());
		}
		if (cbbStartDate.getDate() != null) {
			conditions.put("startDate", cbbStartDate.getDate());
		}
		if (cbbEndDate.getDate() != null) {
			conditions.put("endDate", cbbEndDate.getDate());
		}
		List billType = new ArrayList();
		for (int j = 0; j < this.cmbBillType.getModel().getSize(); j++) {
			CheckBoxListItemForFinancial item = (CheckBoxListItemForFinancial) this.cmbBillType.getModel().getElementAt(j);
			if (item.getIsSelected()) {
				billType.add(item.getCode());
				System.out.println(item.getCode() + ":" + item.getName());
			}
		}
		conditions.put("billType", billType);
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
			btnPrint.setBounds(620, 44, 65, 22);
			btnPrint.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (impExpInfos == null || impExpInfos.isEmpty()) {
						JOptionPane.showMessageDialog(FmMonthlyProductionAndSales.this,
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
						InputStream masterReportStream = FmMonthlyProductionAndSales.class
								.getResourceAsStream("report/CustomInoutWareReportWaiFaConsignQueryBack.jasper");
						Map<String, Object> parameters = new HashMap<String, Object>();
						// if (materialType == MaterielType.MATERIEL) {
						parameters.put("title", "原材料进出仓帐");
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
			btnExit.setBounds(620, 44, 65, 22);
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 初始化客户下拉框
	 */
	private void fillCustomer() {
//		cbbCustomer.removeAllItems();
//		List list = materialManageAction.findScmCocs(new Request(CommonVars
//				.getCurrUser(), true));
//		this.cbbCustomer.setModel(new DefaultComboBoxModel(list.toArray()));
//		this.cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
//				"code", "name", 100, 170));
//		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
//				this.cbbCustomer, "code", "name", 270);
//		this.cbbCustomer.setSelectedItem(null);
	}

	/**
	 * 初始化仓库下拉框
	 */
	private void fillWareSet() {
//		cbbWareSet.removeAllItems();
//		List wareSets = this.materialManageAction.findWareSet(new Request(
//				CommonVars.getCurrUser(), true));
//		this.cbbWareSet.setModel(new DefaultComboBoxModel(wareSets.toArray()));
//		this.cbbWareSet.setRenderer(CustomBaseRender.getInstance().getRender(
//				"code", "name", 100, 170));
//		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
//				this.cbbWareSet, "code", "name", 270);
//		this.cbbWareSet.setSelectedItem(null);
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
				
				list.add(addColumn("上月结存数量", "lastQuantity", 80));

				list.add(addColumn("数量", "productionQuantity", 80));
				list.add(addColumn("单价", "productionPrice", 80));
				list.add(addColumn("总价", "productionAccount", 80));

				list.add(addColumn("数量", "saleQuantity", 80));
				list.add(addColumn("单价", "salePrice", 80));
				list.add(addColumn("总价", "saleAccount", 80));
				
				return list;
			}
		};	

		tableModel = new JTableListModel(tbMain, list, jTableListModelAdapter);
		
		jTableListModelAdapter.setEditableColumn(5);
		jTableListModelAdapter.setEditableColumn(6);

		jTableListModelAdapter.setEditableColumn(8);
		jTableListModelAdapter.setEditableColumn(9);
		
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
		tbMain.getColumnModel().getColumn(5).setCellEditor(new JTextFieldEditor(tf));
		tbMain.getColumnModel().getColumn(5).setCellRenderer(new ForcedEditTableCellRenderer());
		tbMain.getColumnModel().getColumn(6).setCellEditor(new JTextFieldEditor(tf));
		tbMain.getColumnModel().getColumn(6).setCellRenderer(new ForcedEditTableCellRenderer());
		
		tbMain.getColumnModel().getColumn(8).setCellEditor(new JTextFieldEditor(tf));
		tbMain.getColumnModel().getColumn(8).setCellRenderer(new ForcedEditTableCellRenderer());
		tbMain.getColumnModel().getColumn(9).setCellEditor(new JTextFieldEditor(tf));
		tbMain.getColumnModel().getColumn(9).setCellRenderer(new ForcedEditTableCellRenderer());
		
		TableColumnModel cm = tbMain.getColumnModel();
		
		ColumnGroup g_out = new ColumnGroup("本月产量");
		g_out.add(cm.getColumn(5));
		g_out.add(cm.getColumn(6));
		g_out.add(cm.getColumn(7));
		
		ColumnGroup g_sale = new ColumnGroup("本月销量");
		g_sale.add(cm.getColumn(8));
		g_sale.add(cm.getColumn(9));
		g_sale.add(cm.getColumn(10));
		
		GroupableTableHeader header = (GroupableTableHeader) tbMain.getTableHeader();
		header.setColumnModel(tbMain.getColumnModel());
		header.addColumnGroup(g_out);
		header.addColumnGroup(g_sale);
		tbMain.repaint();
	}
	private void commitValueChange(final JFormattedTextField tf) {
		try {
			tf.commitEdit();
		} catch (Exception ex) {
		}
		if (tableModel == null || tableModel.getCurrentRow() == null) {
			return;
		}
		MonthlyProductionAndSales detail = (MonthlyProductionAndSales) tableModel.getCurrentRow();
		
		double productionQuantity = (detail.getProductionQuantity() == null ? 0.0 : detail.getProductionQuantity());
		double productionPrice = (detail.getProductionPrice() == null ? 0.0 : detail.getProductionPrice());
		if(productionPrice != 0.00 && productionQuantity != 0.00)
			detail.setProductionAccount(FinancialCommon.mul(productionQuantity, productionPrice));
		double saleQuantity = (detail.getSaleQuantity() == null ? 0.0 : detail.getSaleQuantity());
		double salePrice = (detail.getSalePrice() == null ? 0.0 : detail.getSalePrice());
		if(saleQuantity != 0.00 && salePrice != 0.00)
			detail.setSaleAccount(FinancialCommon.mul(saleQuantity, salePrice));
		btnSave.setEnabled(Boolean.TRUE);
		detail.setIsSelected(true);
	}
	/**
	 * This method initializes btnName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnName() {
		if (btnName == null) {
			btnName = new JButton();
			btnName.setBounds(new java.awt.Rectangle(225, 20, 20, 20));
			btnName.setText("...");
			btnName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = MaterielType.FINISHED_PRODUCT;
					Object object = CasQuery.getInstance()
							.findHsNameFromStatCusNameRelationHsn(materialType);
					if (object != null) {
						tfName.setText((String) ((TempObject) object).getObject());
					}
				}
			});
		}
		return btnName;
	}

	/**
	 * This method initializes btnImport	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton();
			btnImport.setBounds(new Rectangle(493, 44, 115, 22));
			btnImport.setText("导入修改数据");
			btnImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgMonthlyProductionAndSalesImport dgImport = new DgMonthlyProductionAndSalesImport();
					if(cbbStartDate.getDate() == null){
						JOptionPane.showMessageDialog(FmMonthlyProductionAndSales.this, "导入前，请选择月份", "提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					dgImport.setImportMonth(FinancialCommon.getThisMonthStr(cbbStartDate.getDate()));
					dgImport.setVisible(Boolean.TRUE);
				}
			});
		}
		return btnImport;
	}

	/**
	 * This method initializes btnSave	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(new Rectangle(425, 44, 60, 22));
			btnSave.setText("保存");
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
			MonthlyProductionAndSales monthly = (MonthlyProductionAndSales) oriList.get(i);
			if(monthly.getIsSelected())
				list.add(monthly);
		}
		
		return list;
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
				CommonProgress.setMessage(flag, "系统正在更新数据，请稍后...");
				financialAction.updateMonthlyProductionAndSales(new Request(CommonVars.getCurrUser()), list);
				CommonProgress.closeProgressDialog(flag);
				isOk = true;
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
			} finally {
				btnSave.setEnabled(Boolean.FALSE);
			}
			if(isOk){
				JOptionPane.showMessageDialog(FmMonthlyProductionAndSales.this,
						"更新数据成功！", "提示！", 2);
			}else{
				JOptionPane.showMessageDialog(FmMonthlyProductionAndSales.this,
						"更新数据失败：！", "提示", 2);
			}
		}
	}
}
