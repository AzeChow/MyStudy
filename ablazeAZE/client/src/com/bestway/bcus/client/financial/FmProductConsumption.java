package com.bestway.bcus.client.financial;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.financial.action.FinancialAction;
import com.bestway.bcus.financial.entity.ProductConsumption;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.DateValueListener;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author
 * 成品单耗调整表
 * 
 */
public class FmProductConsumption extends JInternalFrameBase {

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
	private JButton btnReQuery = null;
	private JButton btnPrint = null;
	private JButton btnExit = null;
	/**
	 * 物料类型
	 */
	private String materialType = MaterielType.MATERIEL;  //  @jve:decl-index=0:
	private JTableListModel tableModel = null;
	/**
	 * 数据源
	 */
	private List impExpInfos = null;
	private FinancialAction financialAction = null;
	private JButton btnName = null;
	private DefaultFormatterFactory defaultFormatterFactory = null;
	private NumberFormatter numberFormatter = null;
	/**
	 * 小数位控制参数
	 */
	private Integer maximumFractionDigits = CasCommonVars.getOtherOption()
			.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
			.getOtherOption().getInOutMaximumFractionDigits();  //  @jve:decl-index=0:
	private JTextField tfGoodsName = null;
	private JButton btnGoodsName = null;
	private JButton btnSave = null;

	/**
	 * 构造函数
	 * This is the default constructor
	 */
	public FmProductConsumption() {
		super();
		financialAction = (FinancialAction) CommonVars.getApplicationContext().getBean("financialAction");
		initialize();
		initUIComponents();
	}

	/**
	 * 初始化组件
	 */
	private void initUIComponents() {
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
	}

	/**
	 * 初始化标题和尺寸
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("成品单耗调整表");
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
			pnMain.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
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
			cbbStartDate.setBounds(302, 20, 140, 20);
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
			javax.swing.JLabel jLabel7 = new JLabel();
			pnTop = new JPanel();
			pnTop.setLayout(null);
			jLabel7.setText("成品商品名称");
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel7.setBounds(20, 20, 75, 20);
			jLabel9.setText("料件商品名称");
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setBounds(20, 45, 75, 20);
			jLabel10.setText("日期 从");
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setBounds(253, 20, 49, 20);
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
			pnTop.add(jLabel7, null);
			pnTop.add(jLabel9, null);
			pnTop.add(getJCalendarComboBox(), null);
			pnTop.add(getJCalendarComboBox1(), null);
			pnTop.add(getJButton(), null);
			pnTop.add(getBtnReQuery(), null);
//			pnTop.add(getBtnPrint(), null);
			pnTop.add(getJButton2(), null);
			pnTop.add(jLabel10, null);
			pnTop.add(jLabel5, null);
			pnTop.add(getBtnName(), null);
			pnTop.add(getTfGoodsName(), null);
			pnTop.add(getBtnGoodsName(), null);
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
			tfName.setBounds(97, 45, 128, 20);
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
			btnReQuery.setBounds(new Rectangle(325, 44, 90, 22));
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
				impExpInfos = financialAction.findProductConsumptionList(new Request(CommonVars.getCurrUser()), conditions);
				CommonProgress.closeProgressDialog(flag);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmProductConsumption.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				if (impExpInfos != null && !impExpInfos.isEmpty()) {
					tableModel.setList(impExpInfos);
				} else {
					initTable(new ArrayList());
					JOptionPane.showMessageDialog(FmProductConsumption.this,
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

		if (tfGoodsName.getText() != null && !tfGoodsName.getText().trim().equals("")) {
			conditions.put("hsName", tfGoodsName.getText());
		}
		if (tfName.getText() != null && !tfName.getText().trim().equals("")) {
			conditions.put("ljName", tfName.getText());
		}
		if (cbbStartDate.getDate() != null) {
			conditions.put("startDate", cbbStartDate.getDate());
		}
		if (cbbEndDate.getDate() != null) {
			conditions.put("endDate", cbbEndDate.getDate());
		}
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
			btnPrint.setBounds(492, 44, 65, 22);
			btnPrint.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (impExpInfos == null || impExpInfos.isEmpty()) {
						JOptionPane.showMessageDialog(FmProductConsumption.this,
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
						InputStream masterReportStream = FmProductConsumption.class
								.getResourceAsStream("report/CustomInoutWareReportWaiFaConsignQueryBack.jasper");
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("title", "原材料进出仓帐");
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
			btnExit.setBounds(492, 44, 65, 22);
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
	private void commitValueChange(final JFormattedTextField tf) {
		try {
			tf.commitEdit();
		} catch (Exception ex) {
		}
		if (tableModel == null || tableModel.getCurrentRow() == null) {
			return;
		}
		ProductConsumption detail = (ProductConsumption) tableModel.getCurrentRow();
		//单耗/净耗
		double unitWaste = (detail.getUnitWaste() == null ? 0.0 : detail.getUnitWaste());
		//损耗率
		double waste = (detail.getWaste() == null ? 0.0 : detail.getWaste());
		//调后单项用量
		double alterRate = (detail.getAlterRate() == null ? 0.0 : detail.getAlterRate());
		
		double changeUnitWaste = 0;
		if(unitWaste != 0.00 && waste != 0.00 && alterRate != 00){
			double tempWast = FinancialCommon.sub(1, waste/100);
			if(tempWast > 0){
				changeUnitWaste = FinancialCommon.mul(alterRate, FinancialCommon.div(unitWaste, tempWast));
			}
		}
		if(changeUnitWaste != 0){
			detail.setChangeUnitWaste(changeUnitWaste);
			double thisProduction = (detail.getThisProduction() == null ? 0.0 : detail.getThisProduction());
			if(thisProduction != 0.00)
				detail.setLjNeedQuantity(FinancialCommon.mul(thisProduction, changeUnitWaste));				
		}
		
		btnSave.setEnabled(Boolean.TRUE);
		detail.setIsSelected(true);
	}
	/**
	 * 初始化主表格
	 * @param list
	 */
	private void initTable(final List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter(maximumFractionDigits) {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("成品名称", "hsName", 100));
				list.add(addColumn("成品规格", "hsSpec", 150));
				list.add(addColumn("成品单位", "hsUnit.name", 80));
				
				list.add(addColumn("料件名称", "ljName", 100));
				list.add(addColumn("料件规格", "ljSpec", 150));
				list.add(addColumn("料件单位", "ljUnit.name", 80));

				list.add(addColumn("净耗", "unitWaste", 100));
				list.add(addColumn("损耗率", "waste", 100));

				list.add(addColumn("上下浮率", "alterRate", 100));
				list.add(addColumn("调后单项用量", "changeUnitWaste", 100));

				list.add(addColumn("本月产量", "thisProduction", 100));
				list.add(addColumn("本月需料量", "ljNeedQuantity", 100));

				list.add(addColumn("料件单价", "ljPrice", 100));
				
				return list;
			}
		};

		tableModel = new JTableListModel(tbMain, list, jTableListModelAdapter);
		
		jTableListModelAdapter.setEditableColumn(7);
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
		tbMain.getColumnModel().getColumn(7).setCellEditor(new JTextFieldEditor(tf));
		tbMain.getColumnModel().getColumn(7).setCellRenderer(new ForcedEditTableCellRenderer());
		tbMain.getColumnModel().getColumn(8).setCellEditor(new JTextFieldEditor(tf));
		tbMain.getColumnModel().getColumn(8).setCellRenderer(new ForcedEditTableCellRenderer());
		tbMain.getColumnModel().getColumn(9).setCellEditor(new JTextFieldEditor(tf));
		tbMain.getColumnModel().getColumn(9).setCellRenderer(new ForcedEditTableCellRenderer());
		
		TableColumnModel cm = tbMain.getColumnModel();
		GroupableTableHeader header = (GroupableTableHeader) tbMain.getTableHeader();
		header.setColumnModel(tbMain.getColumnModel());
		tbMain.repaint();
	}

	/**
	 * This method initializes btnName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnName() {
		if (btnName == null) {
			btnName = new JButton();
			btnName.setBounds(new java.awt.Rectangle(225, 45, 20, 20));
			btnName.setText("...");
			btnName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = MaterielType.MATERIEL;
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
	 * This method initializes tfGoodsName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfGoodsName() {
		if (tfGoodsName == null) {
			tfGoodsName = new JTextField();
			tfGoodsName.setBounds(new Rectangle(97, 20, 128, 20));
		}
		return tfGoodsName;
	}

	/**
	 * This method initializes btnGoodsName	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnGoodsName() {
		if (btnGoodsName == null) {
			btnGoodsName = new JButton();
			btnGoodsName.setBounds(new Rectangle(225, 20, 20, 20));
			btnGoodsName.setText("...");

			btnGoodsName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = MaterielType.FINISHED_PRODUCT;
					Object object = CasQuery.getInstance()
							.findHsNameFromStatCusNameRelationHsn(materialType);
					if (object != null) {
						tfGoodsName.setText((String) ((TempObject) object)
								.getObject());
					}
				}
			});
		}
		return btnGoodsName;
	}

	/**
	 * This method initializes btnSave	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(new Rectangle(422, 44, 65, 22));
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
			ProductConsumption product = (ProductConsumption) oriList.get(i);
			if(product.getIsSelected())
				list.add(product);
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
				financialAction.updateProductConsumption(new Request(CommonVars.getCurrUser()), list);
				CommonProgress.closeProgressDialog(flag);
				isOk = true;
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
			} finally {
				btnSave.setEnabled(Boolean.FALSE);
			}
			if(isOk){
				JOptionPane.showMessageDialog(FmProductConsumption.this,
						"更新数据成功！", "提示！", 2);
			}else{
				JOptionPane.showMessageDialog(FmProductConsumption.this,
						"更新数据失败：！", "提示", 2);
			}
		}
	}
}
