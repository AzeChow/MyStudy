package com.bestway.common.client.erpbillcenter.specificontrol;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
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
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxUI;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcs.client.bcsinnermerge.FmBcsFactoryCustoms;
import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.common.CheckBoxListItem;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.client.erpbillcenter.specificontrol.report.BillPrintHelper;
import com.bestway.common.client.erpbillcenter.specificontrol.report.BillSubReportDataSource;
import com.bestway.common.erpbill.action.ErpBillAction;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.GridBagLayout;

public class DgBillReport extends JDialogBase {

	private JSplitPane jSplitPane = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox jCalendarComboBox = null;

	private JLabel jLabel3 = null;

	private JCalendarComboBox jCalendarComboBox1 = null;

	private JLabel jLabel4 = null;

	private JComboBox cbbStorage = null;

	private JLabel jLabel5 = null;

	private JComboBox cbbCustomer = null;

	private JLabel jLabel6 = null;

	private JButton btnQuery = null;

	private JButton btnPrintReport = null;

	private JButton btnCancel = null;

	private CasAction casAction = null;

	private MaterialManageAction materialManageAction = null;

	private JButton jButton = null;

	private JTextField tfName = null;

	private JComboBox cbMostlyBillType = null;

	private JComboBox cbBillType = null;

	private ErpBillAction erpBillAction = null;

	private Map<BillMaster, List<BillDetail>> resultMap = new HashMap(); // @jve:decl-index=0:

	private JTableListModel tableModel = null;

	private JComboBox jComboBox = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JComboBox jComboBox1 = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgBillReport() {
		super();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		erpBillAction = (ErpBillAction) CommonVars.getApplicationContext()
				.getBean("erpBillAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setContentPane(getJSplitPane());
		this.setSize(new Dimension(727, 531));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		fillCustomer();
		fillWareSet();
		fillCbBillType();
		initTable(new ArrayList(), this.getJTable());

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
			jSplitPane.setDividerSize(3);
			jSplitPane.setDividerLocation(85);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setEnabled(false);
		}
		return jSplitPane;
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
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(197, 55, 57, 22));
			jLabel8.setText("是否记帐");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(16, 55, 57, 22));
			jLabel7.setText("是否生效");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(410, 6, 55, 22));
			jLabel6.setText("工厂名称");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(197, 31, 57, 22));
			jLabel5.setText("客        户");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(16, 31, 57, 22));
			jLabel4.setText("仓        库");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(559, 31, 53, 22));
			jLabel3.setText("截止日期");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(410, 31, 57, 22));
			jLabel2.setText("起始日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(197, 7, 57, 22));
			jLabel1.setText("单据类别");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(16, 6, 57, 22));
			jLabel.setText("进出仓别");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJCalendarComboBox1(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getCbbStorage(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getCbbCustomer(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnPrintReport(), null);
			jPanel.add(getBtnCancel(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getFtName(), null);
			jPanel.add(getCbMostlyBillType(), null);
			jPanel.add(getCbBillType(), null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(jLabel7, null);
			jPanel.add(jLabel8, null);
			jPanel.add(getJComboBox1(), null);
		}
		return jPanel;
	}

	/**
	 * cbbBillType This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(new Rectangle(465, 31, 92, 22));
			jCalendarComboBox.setDate(null);
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
			jCalendarComboBox1.setBounds(new Rectangle(611, 31, 92, 22));
		}
		return jCalendarComboBox1;
	}

	/**
	 * This method initializes cbbStorage
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbStorage() {
		if (cbbStorage == null) {
			cbbStorage = new JComboBox();
			cbbStorage.setBounds(new Rectangle(74, 31, 113, 22));
		}
		return cbbStorage;
	}

	private void fillWareSet() {
		cbbStorage.removeAllItems();
		List wareSets = this.materialManageAction.findWareSet(new Request(
				CommonVars.getCurrUser(), true));
		this.cbbStorage.setModel(new DefaultComboBoxModel(wareSets.toArray()));
		this.cbbStorage.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbStorage, "code", "name", 270);
		this.cbbStorage.setSelectedItem(null);
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
	 * This method initializes cbbCustomer
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustomer() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(new Rectangle(253, 31, 141, 22));
		}
		return cbbCustomer;
	}

	private void fillCustomer() {
		cbbCustomer.removeAllItems();
		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbCustomer.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCustomer, "code", "name", 270);
		this.cbbCustomer.setSelectedItem(null);
	}

	private void fillCbBillType() {
		List<BillType> billType = casAction.findBillTypeByProduceType(
				new Request(CommonVars.getCurrUser(), true),
				((ItemProperty) cbMostlyBillType.getItemAt(0)).getCode());
		if (cbBillType.getSelectedItem() != null)
			cbBillType.removeAllItems();
		cbBillType.addItem(new ItemProperty(null, ""));
		for (BillType bt : billType)
			cbBillType.addItem(new ItemProperty(bt.getCode(), bt.getName()));
		tfName.setText(null);

	}

	/**
	 * This method initializes btnQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(new Rectangle(501, 55, 65, 22));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SwingWorker worker = new SwingWorker() {
						@Override
						protected Object doInBackground() throws Exception {
							try {
								CommonProgress.showProgressDialog();
								CommonProgress.setMessage("系统正获取数据，请稍后...");
								Map map = getQueryConditions();
								resultMap = erpBillAction.findBillMaster(
										new Request(CommonVars.getCurrUser()),
										map);
							} catch (Exception e) {
								JOptionPane.showMessageDialog(
										DgBillReport.this, "查询失败！"
												+ e.getMessage(), "提示", 2);
							} finally {
								CommonProgress.closeProgressDialog();
							}
							return null;
						}

						@Override
						protected void done() {
							List masters = new ArrayList(resultMap.keySet());
							CommonProgress.closeProgressDialog();
							initTable(masters, getJTable());

						}
					};
					worker.execute();
				}
			});
		}
		return btnQuery;
	}

	/**
	 * This method initializes btnPrintReport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrintReport() {
		if (btnPrintReport == null) {
			btnPrintReport = new JButton();
			btnPrintReport.setBounds(new Rectangle(570, 55, 65, 22));
			btnPrintReport.setText("打印");
			btnPrintReport
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = tableModel.getCurrentRows();
							if (list == null || list.size() == 0) {
								JOptionPane.showMessageDialog(
										DgBillReport.this, "请选择数据后再打印！", "提示!",
										JOptionPane.WARNING_MESSAGE);
								return;
							}
							String[] options = { "A4", "A4*2","A5" };
							int select = JOptionPane.showOptionDialog(
									DgBillReport.this, "请选择打印类型！", "提示",
									JOptionPane.DEFAULT_OPTION,
									JOptionPane.QUESTION_MESSAGE, null,
									options, options[0]);

							if (0 == select) {
								BillPrintHelper.printBill(list, resultMap);
							}
							if (1 == select) {
								BillPrintHelper.printBillTwo(list, resultMap);
							}
							if (2 == select) {
								BillPrintHelper.printBillA5(list, resultMap);
							}
							//							
						}
					});
		}
		return btnPrintReport;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(638, 55, 65, 22));
			btnCancel.setText("关闭 ");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBillReport.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	private JTableListModel initTable(List list, JTable tb) {
		tableModel = new JTableListModel(tb, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("单据编号", "billNo", 120));
						list.add(addColumn("单据类型", "billType.name", 100));
						list.add(addColumn("生效日期", "validDate", 90));
						list.add(addColumn("客户名称", "scmCoc.name", 200));
						list.add(addColumn("是否生效", "isValid", 70));
						list.add(addColumn("是否记帐", "keepAccounts", 70));
						return list;
					}
				});
		tb.getColumnModel().getColumn(5).setCellRenderer(
				new TableCheckBoxRender());
		tb.getColumnModel().getColumn(6).setCellRenderer(
				new TableCheckBoxRender());
		tb.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		return tableModel;
	}

	class UserListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			String s = "";
			BillType billType = (BillType) value;
			if (billType != null) {
				s = billType.getName();
			}
			setText(s);
			return this;
		}
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(685, 6, 19, 22));
			jButton.setText("...");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbMostlyBillType.getSelectedItem() == null) {
						return;
					}
					ItemProperty prop = (ItemProperty) cbMostlyBillType
							.getSelectedItem();
					String str = prop.getCode();
					Object object = CasQuery.getInstance()
							.findPtNameFromBillDetail(str);
					if (object != null) {
						tfName.setText((String) ((TempObject) object)
								.getObject());
					}
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes ftName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getFtName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(new Rectangle(464, 6, 220, 22));
			tfName.setEditable(false);
		}
		return tfName;
	}

	/**
	 * This method initializes cbMostlyBillType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbMostlyBillType() {
		if (cbMostlyBillType == null) {
			cbMostlyBillType = new JComboBox();
			cbMostlyBillType.setBounds(new Rectangle(74, 6, 112, 22));
			cbMostlyBillType.addItem(new ItemProperty(MaterielType.MATERIEL,
					"料件"));
			cbMostlyBillType.addItem(new ItemProperty(
					MaterielType.FINISHED_PRODUCT, "成品"));
			cbMostlyBillType.addItem(new ItemProperty(MaterielType.MACHINE,
					"设备"));
			cbMostlyBillType.addItem(new ItemProperty(
					MaterielType.SEMI_FINISHED_PRODUCT, "半成品"));
			cbMostlyBillType.addItem(new ItemProperty(MaterielType.BAD_PRODUCT,
					"残次品"));
			cbMostlyBillType.addItem(new ItemProperty(
					MaterielType.REMAIN_MATERIEL, "边角料"));
			cbMostlyBillType.setSelectedIndex(0);
			cbMostlyBillType.setPreferredSize(new java.awt.Dimension(128, 24));
			cbMostlyBillType.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<BillType> billType = casAction
							.findBillTypeByProduceType(new Request(CommonVars
									.getCurrUser(), true),
									((ItemProperty) cbMostlyBillType
											.getSelectedItem()).getCode());
					if (cbBillType.getSelectedItem() != null)
						cbBillType.removeAllItems();
					cbBillType.addItem(new ItemProperty(null, ""));
					for (BillType bt : billType)
						cbBillType.addItem(new ItemProperty(bt.getCode(), bt
								.getName()));
					tfName.setText(null);
				}
			});
		}
		return cbMostlyBillType;
	}

	/**
	 * This method initializes cbBillType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbBillType() {
		if (cbBillType == null) {
			cbBillType = new JComboBox();
			cbBillType.setBounds(new Rectangle(253, 6, 141, 22));
			cbBillType.setPreferredSize(new Dimension(128, 24));
			cbBillType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					tfName.setText(null);
				}
			});
		}
		return cbBillType;
	}

	private Map getQueryConditions() {
		Map map = new HashMap();
		String materielType = ((ItemProperty) cbMostlyBillType
				.getSelectedItem()).getCode();
		map.put("materielType", materielType);

		Object obj = cbBillType.getSelectedItem();
		if (obj == null) {
			map.put("codeBillType", null);
		} else {
			String codeBillType = ((ItemProperty) obj).getCode();
			map.put("codeBillType", codeBillType);
		}
		String factoryName = tfName.getText();
		if (factoryName != null && !factoryName.trim().equals("")) {
			map.put("factoryName", factoryName);
		} else {
			map.put("factoryName", null);
		}
		Object codeWarSet = cbbStorage.getSelectedItem();
		map.put("codeWarSet", codeWarSet);
		Object codeCustomer = cbbCustomer.getSelectedItem();
		map.put("codeCustomer", codeCustomer);
		Date begin = jCalendarComboBox.getDate();
		map.put("begin", begin);
		Date end = jCalendarComboBox1.getDate();
		map.put("end", end);
		Boolean isValid = (Boolean) getJComboBox().getSelectedItem();
		map.put("isValid", isValid);
		Boolean keepAccounts = (Boolean) getJComboBox1().getSelectedItem();
		map.put("keepAccounts", keepAccounts);
		return map;
	}

	class MyFindThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正获取数据，请稍后...");
			Map map = getQueryConditions();
			resultMap = erpBillAction.findBillMaster(new Request(CommonVars
					.getCurrUser()), map);
			List masters = new ArrayList(resultMap.keySet());
			initTable(masters, getJTable());
			CommonProgress.closeProgressDialog();
		}
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(74, 55, 113, 22));
			jComboBox.addItem(null);
			jComboBox.addItem(new Boolean(true));
			jComboBox.addItem(new Boolean(false));
			jComboBox.setRenderer(new SelectedCbb());
			jComboBox.setSelectedIndex(0);
		}
		return jComboBox;
	}

	class SelectedCbb extends DefaultListCellRenderer {
		public SelectedCbb() {
			super();
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			setComponentOrientation(list.getComponentOrientation());
			Color bg = null;
			Color fg = null;

			JList.DropLocation dropLocation = list.getDropLocation();
			if (dropLocation != null && !dropLocation.isInsert()
					&& dropLocation.getIndex() == index) {

				bg = UIManager.getColor("List.dropCellBackground");
				fg = UIManager.getColor("List.dropCellForeground");

				isSelected = true;
			}

			if (isSelected) {
				setBackground(bg == null ? list.getSelectionBackground() : bg);
				setForeground(fg == null ? list.getSelectionForeground() : fg);
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			if (value instanceof Icon) {
				setIcon((Icon) value);
				setText("");
			} else if (value != null && value instanceof Boolean) {
				setIcon(null);
				boolean bvalue = (Boolean) value;
				if (bvalue) {
					setText("是");
				} else {
					setText("否");
				}
			} else if (value == null) {
				setIcon(null);
				setText("全部");
			} else {
				setIcon(null);
				setText("");
			}

			setEnabled(list.isEnabled());
			setFont(list.getFont());

			Border border = null;
			if (cellHasFocus) {
				if (isSelected) {
					border = UIManager
							.getBorder("List.focusSelectedCellHighlightBorder");
				}
				if (border == null) {
					border = UIManager
							.getBorder("List.focusCellHighlightBorder");
				}
			} else {
				// border = getNoFocusBorder();
			}
			setBorder(border);
			return this;

		}
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(new Rectangle(253, 55, 141, 22));
			jComboBox1.addItem(null);
			jComboBox1.addItem(new Boolean(true));
			jComboBox1.addItem(new Boolean(false));
			jComboBox1.setRenderer(new SelectedCbb());
			jComboBox1.setSelectedIndex(0);
		}
		return jComboBox1;
	}
} // @jve:decl-index=0:visual-constraint="77,-298"
