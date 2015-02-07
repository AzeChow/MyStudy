package com.bestway.common.client.erpbill;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.client.common.CheckBoxListCellRendererNotCode;
import com.bestway.bcus.client.common.CheckBoxListItem;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.SteppedMetalComboBoxUI;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.aptitudereport.entity.GroupValue;
import com.bestway.common.erpbill.action.OrderCommonAction;
import com.bestway.common.erpbill.entity.TempCustomOrder;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.JCheckBox;

public class DgOrderQuery extends JDialogBase {

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JScrollPane jScrollPane = null;

	private JLabel jLabel = null;

	private JTextField tfOrderCode = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbDateStart = null;

	private JCalendarComboBox cbbDateEnd = null;

	private JLabel jLabel2 = null;

	private JButton btnSearch = null;

	private JButton btnPrint = null;

	private JButton btnClose = null;

	private JTableListModel tableModel;

	private JTableListModel tableModel1;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JScrollPane jScrollPane1 = null;

	private JScrollPane jScrollPane2 = null;

	private JTable tbImport = new JTable();

	private JTable tbExport = new JTable();

	private String index = "0"; // @jve:decl-index=0:

	private String type = ""; // @jve:decl-index=0:

	private String impExpType = null;

	private JLabel jLabel10 = null;

	private JComboBox cbbScmCoc = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:

	private JButton jButton1 = null;

	private OrderCommonAction orderCommonAction = null;

	private MaterialManageAction materialManageAction = null;

	private ButtonGroup group = new ButtonGroup(); // @jve:decl-index=0:

	private Integer customType = 0;  //  @jve:decl-index=0:

	private String reportTitle = null;

	private List listResult = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbGroupingColumn = null;

	private List<String> groupingColumnVale = new ArrayList<String>(); // @jve:decl-index=0:

	private String groupingColumn = null;

	private JCheckBox jCheckBox = null;

	private boolean isTransfer = false;

	/**
	 * This method initializes
	 * 
	 */
	public DgOrderQuery(int setbgtype) {
		super();
		this.customType = setbgtype;
		orderCommonAction = (OrderCommonAction) CommonVars
				.getApplicationContext().getBean("orderCommonAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();

		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setPreferredSize(new Dimension(465, 464));
		this.setSize(new Dimension(700, 500));
		this.setLocation(new Point(0, 0));
		this.setTitle("订单明细统计");
		this.setContentPane(getJContentPane());
		this.cbbDateStart.setDate(null);
		this.cbbDateEnd.setDate(null);

	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		cbbGroupingColumn.removeAllItems();
		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbScmCoc, "code", "name", 270);
		cbbScmCoc.setSelectedIndex(-1);
		cbbGroupingColumn.addItem(new CheckBoxListItem(
				"customOrderDetail.salesdate", "送货日期"));
		cbbGroupingColumn.addItem(new CheckBoxListItem(
				"customOrderDetail.materiel.ptNo", "料号"));
		cbbGroupingColumn.addItem(new CheckBoxListItem(
				"customOrderDetail.customOrder.customer", "客户"));
		cbbGroupingColumn.setSelectedItem(null);
		cbbGroupingColumn.setRenderer(new CheckBoxListCellRendererNotCode(
				cbbGroupingColumn));
		cbbGroupingColumn.setUI(new SteppedMetalComboBoxUI(220));
		setCmbBillTypeEvent(cbbGroupingColumn);
		initTable(null);
		initTable1(null);

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
		// boolean editFlag = setEditFlag();

		// if (editFlag) {
		List list = getIndexOfGroupingColumnValue();
		for (int j = 0; j < list.size(); j++) {

			int index = Integer.valueOf(list.get(j).toString());
			CheckBoxListItem item = (CheckBoxListItem) listBox.getModel()
					.getElementAt(index);
			item.setIsSelected(!item.getIsSelected());
			Rectangle rect = listBox.getCellBounds(index, index);
			listBox.repaint(rect);
			ComboBoxEditor com = cbb.getEditor();
			JTextField tf = (JTextField) com.getEditorComponent();

			if (tf != null) {
				if (item.getIsSelected()) {
					tf.setText("".equals(tf.getText()) ? item.getName() : tf
							.getText()
							+ "," + item.getName());

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
					groupingColumn = str;
					tf.setText(str);

				}
			}
		}

		// }

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
						groupingColumn = str;
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
				for (String str : strs) {
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
				groupingColumn = tempText;
				tf.setText(tempText);

			}
		});

	}

	private List getIndexOfGroupingColumnValue() {
		List<Integer> indexList = new ArrayList<Integer>();
		int index = 0;
		List<CheckBoxListItem> checkableItemList = new ArrayList<CheckBoxListItem>();
		for (int i = 0; i < cbbGroupingColumn.getModel().getSize(); i++) {
			CheckBoxListItem item = (CheckBoxListItem) this.cbbGroupingColumn
					.getModel().getElementAt(i);
			checkableItemList.add(item);

			if (item.getIsSelected()) {
				index = i;
				indexList.add(index);
			}

		}

		return indexList;

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setPreferredSize(new Dimension(95, 149));
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(5);
			jSplitPane.setPreferredSize(new Dimension(95, 149));
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setDividerLocation(90);
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
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(205, 50, 68, 25));
			jLabel3.setText("分组条件");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(2, 50, 69, 25));
			jLabel10.setText("客户/供应商");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(359, 14, 18, 25));
			jLabel2.setText("到");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(205, 15, 71, 25));
			jLabel1.setText("送货日期 从");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(2, 15, 54, 25));
			jLabel.setText("订单号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getTfOrderCode(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbDateStart(), null);
			jPanel.add(getCbbDateEnd(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel10, null);
			jPanel.add(getCbbScmCoc(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getBtnSearch(), null);
			jPanel.add(getBtnClose(), null);
			jPanel.add(getBtnPrint(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getCbbGroupingColumn(), null);
			jPanel.add(getJCheckBox(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTabbedPane());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tfOrderCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOrderCode() {
		if (tfOrderCode == null) {
			tfOrderCode = new JTextField();
			tfOrderCode.setBounds(new Rectangle(58, 15, 120, 25));
		}
		return tfOrderCode;
	}

	/**
	 * This method initializes cbbDateStart
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbDateStart() {
		if (cbbDateStart == null) {
			cbbDateStart = new JCalendarComboBox();
			cbbDateStart.setBounds(new Rectangle(277, 14, 80, 25));

		}
		return cbbDateStart;
	}

	/**
	 * This method initializes cbbDateEnd
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbDateEnd() {
		if (cbbDateEnd == null) {
			cbbDateEnd = new JCalendarComboBox();
			cbbDateEnd.setBounds(new Rectangle(377, 14, 80, 25));
		}
		return cbbDateEnd;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查询");
			btnSearch.setBounds(new Rectangle(470, 14, 58, 25));
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					new FindDataThread().start();
				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setBounds(new Rectangle(542, 14, 58, 25));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JTableListModel tableModel = null;

					if ("0".equals(index)) {
						tableModel = (JTableListModel) tbImport.getModel();
						reportTitle = " 定 单 成 品 统 计 表 ";
					} else {
						tableModel = (JTableListModel) tbExport.getModel();
						reportTitle = " 定 单 料 件 统 计 表 ";
					}

					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());

					try {
						Map<String, Object> parameters = new HashMap<String, Object>();
						if (isTransfer) {
							InputStream reportStream = DgOrderQuery.class
									.getResourceAsStream("report/OrderIsTransReport0.jasper");
							parameters.put("reportTitle", reportTitle);
							JasperPrint jasperPrint = JasperFillManager
									.fillReport(reportStream, parameters, ds);
							DgReportViewer dgReportViewer = new DgReportViewer(
									jasperPrint);
							dgReportViewer.setVisible(true);
						} else {
							InputStream reportStream = DgOrderQuery.class
									.getResourceAsStream("report/OrderReport0.jasper");
							parameters.put("reportTitle", reportTitle);
							JasperPrint jasperPrint = JasperFillManager
									.fillReport(reportStream, parameters, ds);
							DgReportViewer dgReportViewer = new DgReportViewer(
									jasperPrint);
							dgReportViewer.setVisible(true);
						}

					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnPrint;
	}

	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		JTableListModelAdapter tableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				String[] strs = split(groupingColumn, ",");
				list.add(addColumn("商品编码", "code", 80));
				list.add(addColumn("商品名称", "name", 150));
				list.add(addColumn("规格型号", "spec", 100));
				list.add(addColumn("计量单位", "unit", 80));
				for (int i = 0; i < strs.length; i++) {
					if (!"".equals(strs[i].trim())) {
						if ("送货日期".equals(strs[i].trim())) {
							list.add(addColumn("送货日期", "salesdate", 80));
						} else if ("料号".equals(strs[i].trim())) {
							list.add(addColumn("料号", "ptNo", 80));
						} else if ("客户".equals(strs[i].trim())) {
							list.add(addColumn("客户/供应商", "customer", 80));
						}
					}
				}
				list.add(addColumn("数量", "amount", 100));
				list.add(addColumn("未转合同数量", "notContractNum", 100));
				list.add(addColumn("已转合同数量", "contractNum", 100));
				if (isTransfer) {

					list.add(addColumn("未转关封数量", "notTransNum", 100));
					list.add(addColumn("已转关封数量", "transNum", 100));
				}

				return list;
			}
		};

		tableModel1 = new JTableListModel(tbImport, list, tableListModelAdapter);

	}

	private void initTable1(List list) {
		if (list == null) {
			list = new ArrayList();
		}

		JTableListModelAdapter tableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				String[] strs = split(groupingColumn, ",");
				list.add(addColumn("商品编码", "code", 80));
				list.add(addColumn("商品名称", "name", 150));
				list.add(addColumn("规格型号", "spec", 100));
				list.add(addColumn("计量单位", "unit", 80));
				for (int i = 0; i < strs.length; i++) {
					if (!"".equals(strs[i].trim())) {
						if ("送货日期".equals(strs[i].trim())) {
							list.add(addColumn("送货日期", "salesdate", 80));
						} else if ("料号".equals(strs[i].trim())) {
							list.add(addColumn("料号", "ptNo", 80));
						} else if ("客户".equals(strs[i].trim())) {
							list.add(addColumn("客户/供应商", "customer", 80));
						}
					}

				}
				list.add(addColumn("数量", "amount", 100));
				list.add(addColumn("未转合同数量", "notContractNum", 100));
				list.add(addColumn("已转合同数量", "contractNum", 100));
				if (isTransfer) {

					list.add(addColumn("未转关封数量", "notTransNum", 100));
					list.add(addColumn("已转关封数量", "transNum", 100));
				}

				return list;
			}
		};

		tableModel1 = new JTableListModel(tbExport, list, tableListModelAdapter);

	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setBounds(new Rectangle(618, 14, 58, 25));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOrderQuery.this.dispose();
				}
			});
		}
		return btnClose;
	}

	private class FindDataThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正在查询数据,请等待...");
			try {
				if ("0".equals(index)) {
					type = "0";
					setImpExpType(type);
				} else {
					type = "2";
					setImpExpType(type);
				}
				Date dateBegin = null;
				Date dateEnd = null;
				String orderCode = null;
				ScmCoc scmcoc = null;
				if ((!"".equals(tfOrderCode.getText()))
						|| tfOrderCode.getText() != null) {
					orderCode = tfOrderCode.getText();
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

				if (cbbDateStart.getDate() != null) {
					dateBegin = java.sql.Date.valueOf(dateFormat
							.format(cbbDateStart.getDate()));
				}
				if (cbbDateEnd.getDate() != null) {
					dateEnd = java.sql.Date.valueOf(dateFormat
							.format(cbbDateEnd.getDate()));
				}

				if (cbbScmCoc.getSelectedItem() != null) {
					scmcoc = (ScmCoc) cbbScmCoc.getSelectedItem();
				}

				listResult = orderCommonAction.findOrders(new Request(
						CommonVars.getCurrUser()), type, scmcoc, dateBegin,
						dateEnd, orderCode, isTransfer, customType,
						groupingColumn);

				if ("0".equals(index)) {
					initTable(listResult);
				} else {
					initTable1(listResult);
				}

				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				e.printStackTrace();

			}

		}
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("成品", null, getJPanel1(), null);
			jTabbedPane.addTab("料件", null, getJPanel2(), null);
			jTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (jTabbedPane.getSelectedIndex() == 0) {
								setIndex("0");

							} else {
								setIndex("1");
							}
						}
					});
		}
		return jTabbedPane;
	}

	private void setIndex(String index) {
		this.index = index;
	}

	private JTable getCurrentTable() {
		if (jTabbedPane.getSelectedIndex() == 0) {
			return tbImport;
		} else {
			return tbExport;
		}
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
			jPanel1.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJScrollPane2(), BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setPreferredSize(new Dimension(70, 72));
			jScrollPane1.setViewportView(getTbImport());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setPreferredSize(new Dimension(70, 72));
			jScrollPane2.setViewportView(getTbExport());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes tbImport
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImport() {
		if (tbImport == null) {
			tbImport = new JTable();
		}
		return tbImport;
	}

	/**
	 * This method initializes tbExport
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExport() {
		if (tbExport == null) {
			tbExport = new JTable();
		}
		return tbExport;
	}

	private void setImpExpType(String type) {
		this.type = type;
	}

	/**
	 * This method initializes cbbScmCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(new Rectangle(71, 50, 126, 25));
			cbbScmCoc.addItem(new ItemProperty("", ""));
			cbbScmCoc.addItem(new ItemProperty("true", "是"));

			cbbScmCoc.addItem(new ItemProperty("false", "否"));

		}
		return cbbScmCoc;
	}

	/**
	 * 初始化Cbb呈现
	 * 
	 * @param cbb
	 */
	private void initComboBoxRenderer(JComboBox cbb) {
		cbb.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbb);
		cbb.setSelectedItem(null);
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */

	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();

		}
		return buttonGroup;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("....");
			jButton1.setBounds(new Rectangle(177, 15, 20, 25));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// DgOrderArrayConditionValue dg = new
					// DgOrderArrayConditionValue();
					// dg.setConditionValue(tfOrderCode.getText().trim());
					// dg.setVisible(true);
					// if (dg.isOK()) {
					// tfOrderCode.setText(dg.getConditionValue());
					// }

					List list =CustomsOrderCommonQuery.getInstance()
							.getTempCustomsOrder(customType);
					System.out.println("list="+list);
					if (list == null) {
						return;
					}
					String s = "";
					for (int i = 0; i < list.size(); i++) {
						BillTemp obj = (BillTemp) list.get(i);
						if (i == 0) {
							s = obj.getBill1();
						} else {
							s = s + "," + obj.getBill1();
						}
					}
					tfOrderCode.setText(s);

				}
			});

		}

		return jButton1;
	}

	private class RadioActionListner implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {

			if (((JRadioButton) e.getSource()).getName().equals("HT")) {
				initTable(null);
				initTable1(null);

			} else if (((JRadioButton) e.getSource()).getName().equals("ZC")) {
				initTable(null);
				initTable1(null);

			}

		}
	}

	public void setCustomType(int type) {
		this.customType = type;
	}

	public int getCustomType() {
		return this.customType;
	}

	/**
	 * This method initializes cbbGroupingColumn
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbGroupingColumn() {
		if (cbbGroupingColumn == null) {
			cbbGroupingColumn = new JComboBox();
			cbbGroupingColumn.setBounds(new Rectangle(277, 50, 180, 25));
			cbbGroupingColumn
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {

						}
					});
		}
		return cbbGroupingColumn;
	}

	/**
	 * 字符串分离
	 * 
	 * @param sourceStr
	 * @param matchStr
	 * @return
	 */
	private String[] split(String sourceStr, String matchStr) {
		if (sourceStr == null) {
			return new String[0];
		}
		List<String> list = new ArrayList<String>();
		while (sourceStr.indexOf(matchStr) != -1) {
			String tempS = sourceStr.substring(0, sourceStr.indexOf(matchStr));
			sourceStr = sourceStr.substring(sourceStr.indexOf(matchStr)
					+ matchStr.length(), sourceStr.length());

			if (!"".equals(tempS.trim()) && tempS != null) {
				list.add(tempS);
			}
		}

		list.add(sourceStr);
		String[] str = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {

			str[i] = list.get(i);

		}
		return str;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(470, 53, 76, 25));
			jCheckBox.setText("是否转厂");
			jCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jCheckBox.getSelectedObjects() != null) {
						isTransfer = true;

					} else {

						isTransfer = false;
					}

				}
			});
		}
		return jCheckBox;
	}

} // @jve:decl-index=0:visual-constraint="10,10"

