package com.bestway.bcus.client.cas.otherreport;

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
import java.util.ArrayList;
import java.util.Calendar;
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
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.cas.DgImpExpQuery;
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
import java.awt.Point;

public class DgTranFactoryMarginQuery extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel6 = null;
	private JPanel jPanel7 = null;
	private JComboBox cmbCustomer = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JButton btnQuery = null;
	private JButton btnPrint = null;
	private JButton btnExit = null;
	private CasAction casAction = null;
	private String materialType;
	private JTableListModel tableModel = null;
	private List impExpInfos = null;
	private MaterialManageAction materialManageAction = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JTextField tfHsName = null;
	private JButton btnHsName = null;
	private JTextField tfHsSpec = null;
	private JButton btnHsSpec = null;
	private Integer maximumFractionDigits = CasCommonVars.getOtherOption()
			.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
			.getOtherOption().getInOutMaximumFractionDigits();  //  @jve:decl-index=0:
	/**
	 * This is the default constructor
	 */
	public DgTranFactoryMarginQuery() {
		super(false);
		//this.materialType = materialType;
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();
		initUIComponents();
	}

	private void initUIComponents() {
		fillCustomer();
		initTable(new ArrayList());
		int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
				.getYearInt();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("转厂差额查询");
		this.setSize(714, 541);
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
			jContentPane.add(getJSplitPane(),null);
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
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(1);
			jSplitPane.setDividerLocation(75);
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
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(9, 42, 62, 20));
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel1.setText("报关单位");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(232, 15, 65, 20));
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel.setText("报关名称");
			javax.swing.JLabel jLabel7 = new JLabel();
			jPanel7 = new JPanel();
			jPanel7.setLayout(null);
			jLabel7.setText("客户名称");
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel7.setBounds(8, 15, 64, 20);
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
			jPanel7.add(getJComboBox1(), null);
			jPanel7.add(jLabel7, null);
			jPanel7.add(getJButton(), null);
			jPanel7.add(getBtnPrint(), null);
			jPanel7.add(getJButton2(), null);
			jPanel7.add(jLabel, null);
			jPanel7.add(jLabel1, null);
			jPanel7.add(getTfHsName(), null);
			jPanel7.add(getBtnHsName(), null);
			jPanel7.add(getTfHsSpec(), null);
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
			cmbCustomer.setBounds(77, 15, 147, 20);
		}
		return cmbCustomer;
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
			btnQuery.setBounds(474, 27, 65, 23);
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<Condition> conditions = getConditions();
					new searchThread(conditions, materialType).start();
				}
			});
		}
		return btnQuery;
	}

	class searchThread extends Thread {
		List<Condition> conditions = null;
		String materialType = null;

		public searchThread(List<Condition> conditions, String materialType) {
			this.conditions = conditions;
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
				CommonProgress.showProgressDialog(flag, DgTranFactoryMarginQuery.this);
				CommonProgress.setMessage(flag, "系统正获取数据，请稍后...");

//				if (cbIsTotalFrontAmount.isSelected()) {
//					impExpInfos = casAction.findImpExpInfos(new Request(
//							CommonVars.getCurrUser()), materialType,
//							conditions, cbIsSplitBomVersion.isSelected(),
//							startDate.getDate());
//				} else {
//					impExpInfos = casAction.findImpExpInfos(new Request(
//							CommonVars.getCurrUser()), materialType,
//							conditions, cbIsSplitBomVersion.isSelected());
//				}

				CommonProgress.closeProgressDialog(flag);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgTranFactoryMarginQuery.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				if (impExpInfos != null && !impExpInfos.isEmpty()) {
					tableModel.setList(impExpInfos);
				} else {
					initTable(new ArrayList());
					JOptionPane.showMessageDialog(DgTranFactoryMarginQuery.this,
							"没有查到符合条件的记录！", "提示！", 2);
				}
			}
			btnQuery.setEnabled(true);
		}
	}

	private List getConditions() {
		List<Condition> conditions = new ArrayList<Condition>();

		if (cmbCustomer.getSelectedItem() != null) {
			conditions.add(new Condition("and", null, "billMaster.scmCoc", "=",
					cmbCustomer.getSelectedItem(), null));
		}
		if (!tfHsName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "hsName", "=", tfHsName
					.getText(), null));
		}
		if (!tfHsSpec.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "hsSpec", "=", tfHsSpec
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
			btnPrint.setBounds(547, 27, 65, 23);
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
		if (impExpInfos == null || impExpInfos.isEmpty()) {
			JOptionPane.showMessageDialog(DgTranFactoryMarginQuery.this, "没有列印的记录！",
					"提示！", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		btnPrint.setEnabled(false);
		try {
			//
			// 
			//						
			Map<String, Object> parametersByCover = new HashMap<String, Object>();
			String companyName = CommonVars.getCurrUser().getCompany()
					.getName();
			parametersByCover.put("companyName", companyName);
			
			CommonDataSource temp = new CommonDataSource(new ArrayList());
			
			InputStream ReportStream = DgImpExpQuery.class
			.getResourceAsStream("report/TranFactoryMargin.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					ReportStream, parametersByCover, temp);			

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
			btnExit.setBounds(620, 27, 65, 23);
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
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
						list.add(addColumn("年","",40));
						list.add(addColumn("月","",20));
						
						list.add(addColumn("本月","",80));
						list.add(addColumn("累计","",80));
						
						list.add(addColumn("本月","",80));
						list.add(addColumn("累计","",80));
						
						list.add(addColumn("本月","",80));
						list.add(addColumn("累计","",80));
						
						list.add(addColumn("本月","",80));
						list.add(addColumn("累计","",80));
						
						list.add(addColumn("数量","",100));
						list.add(addColumn("重量","",100));
						
						list.add(addColumn("关封号","",100));
						list.add(addColumn("数量","",80));
						list.add(addColumn("重量","",80));
						list.add(addColumn("有效期","",100));

						return list;
					}
				});

		TableColumnModel cm = jTable.getColumnModel();
		ColumnGroup g_in_num = new ColumnGroup("数量");
		g_in_num.add(cm.getColumn(3));
		g_in_num.add(cm.getColumn(4));
		
		ColumnGroup g_in_weight = new ColumnGroup("重量");
		g_in_weight.add(cm.getColumn(5));
		g_in_weight.add(cm.getColumn(6));
		
		ColumnGroup g_out_num = new ColumnGroup("数量");
		g_out_num.add(cm.getColumn(7));
		g_out_num.add(cm.getColumn(8));
		
		ColumnGroup g_out_weight = new ColumnGroup("重量");
		g_out_weight.add(cm.getColumn(9));
		g_out_weight.add(cm.getColumn(10));
		
		ColumnGroup g_margin = new ColumnGroup("差额");
		g_margin.add(cm.getColumn(11));
		g_margin.add(cm.getColumn(12));		

		ColumnGroup g_bill_margin = new ColumnGroup("关封余额");
		g_bill_margin.add(cm.getColumn(14));
		g_bill_margin.add(cm.getColumn(15));		
		
		ColumnGroup g_in = new ColumnGroup("收(送货)");
		g_in.add(g_in_num);
		g_in.add(g_in_weight);
		
		ColumnGroup g_out = new ColumnGroup("转厂");
		g_out.add(g_out_num);
		g_out.add(g_out_weight);
		
		ColumnGroup g_bill = new ColumnGroup("关封");
		g_bill.add(cm.getColumn(13));
		g_bill.add(g_bill_margin);
		g_bill.add(cm.getColumn(16));

		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.setColumnModel(jTable.getColumnModel());
		header.addColumnGroup(g_in);
		header.addColumnGroup(g_out);
		header.addColumnGroup(g_margin);
		header.addColumnGroup(g_bill);
		jTable.repaint();
	}

	/**
	 * This method initializes tfHsName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsName() {
		if (tfHsName == null) {
			tfHsName = new JTextField();
			tfHsName.setBounds(new Rectangle(302, 15, 129, 20));
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
			btnHsName.setBounds(new Rectangle(434, 14, 19, 20));
			btnHsName.setText("...");
			btnHsName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
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
			tfHsSpec.setBounds(new Rectangle(77, 42, 129, 20));
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
			btnHsSpec.setBounds(new Rectangle(205, 42, 19, 20));
			btnHsSpec.setText("...");
			btnHsSpec.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
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

}
