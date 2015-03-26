/*
 * Created on 2004-6-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.client.common.InitialFocusSetter.FocusSetter;
import com.bestway.client.common.CommonVariables;
import com.bestway.client.util.DataType;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.SerialColumn;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgCommonQuery extends JDialogBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JPanel jContentPane = null;

	private JPanel jPanel = null;

	protected JComboBox cbbQueryField = null;

	protected JTextField tfQueryValue = null;

	private JPanel jPanel1 = null;

	private JButton btnOK = null;

	private JButton btnCancel = null;

	protected JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private boolean ok = false;

	private Object returnValue;

	protected List returnList;

	protected JTableListModel tableModel = null;

	protected List dataSource = null; // @jve:decl-index=0:

	private static boolean singleResult = true;

	private Object initObject = null;

	protected JToolBar jToolBar = null;

	private static List tableColumns = null;

	private static Hashtable hsSelect = new Hashtable();

	protected JRadioButton jRadioButton = null;

	protected JRadioButton jRadioButton1 = null;

	private ButtonGroup group = new ButtonGroup(); // @jve:decl-index=0:

	private boolean isLike = true;

	private boolean isDefaultAll = false;// 默认选择全部

	public boolean isDefaultAll() {
		return isDefaultAll;
	}

	public void setDefaultAll(boolean isDefaultAll) {
		this.isDefaultAll = isDefaultAll;
	}

	/**
	 * @return Returns the tableColumns.
	 */
	public static List getTableColumns() {
		return tableColumns;
	}

	/**
	 * @param tableColumns
	 *            The tableColumns to set.
	 */
	public static void setTableColumns(List columns) {
		tableColumns = columns;
	}

	/**
	 * @return Returns the singleResult.
	 */
	public boolean isSingleResult() {
		return singleResult;
	}

	/**
	 * @param singleResult
	 *            The singleResult to set.
	 */
	public static void setSingleResult(boolean b) {
		singleResult = b;
	}

	/**
	 * @param singleResult
	 *            The singleResult to set.
	 */
	public boolean getSingleResult() {
		return singleResult;
	}

	/**
	 * @return Returns the dataSource.
	 */
	public List getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource
	 *            The dataSource to set.
	 */
	public void setDataSource(List dataSource) {
		this.dataSource = dataSource;
	}

	public void setSelectedRow(Object obj) {
		this.initObject = obj;
	}

	/**
	 * @return Returns the returnList.
	 */
	public List getReturnList() {
		return returnList;
	}

	/**
	 * @param returnList
	 *            The returnList to set.
	 */
	private void setReturnList(List returnList) {
		this.returnList = returnList;
	}

	/**
	 * @return Returns the returnValue.
	 */
	public Object getReturnValue() {
		return returnValue;
	}

	/**
	 * @param returnValue
	 *            The returnValue to set.
	 */
	private void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgCommonQuery() {
		super();
		initialize();
		this.addWindowFocusListener(new FocusSetter(tfQueryValue));
		// setAlwaysOnTop(true);
		setSingleResult(true);

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("查询");
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setSize(670, 418);
		group.add(jRadioButton1);
		group.add(jRadioButton);

	}

	/**
	 * 
	 * This method initializes jContentPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
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
	protected JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getCbbQueryField(), null);
			jPanel.add(getTfQueryValue(), null);
		}
		return jPanel;
	}

	class ComboBoxItem {
		private String code;

		private String name;

		public ComboBoxItem(String code, String name) {
			this.code = code;
			this.name = name;
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
			return name;
		}
	}

	/**
	 * 
	 * This method initializes cbbQueryField
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getCbbQueryField() {
		if (cbbQueryField == null) {
			cbbQueryField = new JComboBox();
			cbbQueryField.setBounds(4, 3, 127, 23);
		}
		return cbbQueryField;
	}

	/**
	 * 
	 * This method initializes tfQueryValue
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfQueryValue() {
		if (tfQueryValue == null) {
			tfQueryValue = new JTextField();
			tfQueryValue.setBounds(135, 4, 205, 23);
			tfQueryValue.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyChar() == KeyEvent.VK_ENTER) {
						// setSelectedFirstRow();
						getResult();// 回车时直接获取数据
					}
				}
			});
			tfQueryValue
					.addCaretListener(new javax.swing.event.CaretListener() {
						public void caretUpdate(javax.swing.event.CaretEvent e) {
							Integer col = null;
							Object obj = getCbbQueryField().getSelectedItem();
							if (obj == null || !(obj instanceof Item)) {
								return;
							}
							Item itm = (Item) obj;
							if (itm == null || itm.getName() == null) {
								return;
							}
							String pro = itm.getName();
							List<JTableListColumn> list = ((JTableListModel) tableModel)
									.getColumns();
							for (int i = 0; i < list.size(); i++) {
								JTableListColumn data = list.get(i);
								if (data.getCaption() == null) {
									continue;
								}
								if (data.getCaption().equals(pro))
									col = i;
							}
							boolean isEqual = true;
							if (jRadioButton.isSelected()) {
								isEqual = true;
							} else {
								isEqual = false;
							}
							String value = getTfQueryValue().getText();
							if (value == null || value.equals("")) {
								return;
							}
							if (tableModel instanceof JTableListModel
									&& col != null) {
								((JTableListModel) tableModel).filter(col,
										value, isEqual, 0);
							}
						}
					});
		}
		return tfQueryValue;
	}

	// 出掉数组重复值
	private String delRe(String value) {
		String[] x = value.split(",");
		List list = new Vector();
		for (int i = 0; i < x.length; i++) {
			if (!isexist(list, x[i])) {
				list.add(x[i]);
			}
		}

		String xx = "";
		for (int j = 0; j < list.size(); j++) {
			String name = (String) list.get(j);
			if (j == 0) {
				xx = name;
				continue;
			}
			xx = xx + "," + name;
		}
		return xx;
	}

	private boolean isexist(List list, String str) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null) {
				continue;
			}
			String x = list.get(i).toString();
			if (x.equals(str)) {
				return true;
			}
		}
		return false;
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
			jPanel1.add(getBtnOK(), null);
			jPanel1.add(getBtnCancel(), null);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes btnOK
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					getResult();
				}

			});

		}
		return btnOK;
	}

	/**
	 * 
	 */
	private void getResult() {

		if (this.getSingleResult()) {

			// 当没有选择到数据时,按"确定"或"Enter"时默认取第一条数据
			if (((JTableListModel) tableModel).getRowCount() > 0) {

				int size = ((JTableListModel) getJTable().getModel())
						.getRowCount();

				List list = ((JTableListModel) tableModel).getCurrentRows();

				if (size > 0 && !tfQueryValue.getText().equals("")
						&& list.isEmpty()) {
					jTable.getSelectionModel()
							.setSelectionInterval(0, size - 1);

				}

				list = ((JTableListModel) tableModel).getCurrentRows();

				if (list.size() > 0) {

					setReturnValue(list.get(0));
				} else {
					setReturnValue(((JTableListModel) tableModel).getValueAt(0));
				}

			}

			// 当没有选择到数据时,按"确定"或"Enter"时默认取显示的全部数据
		} else {

			int size = ((JTableListModel) tableModel).getRowCount();

			List list = ((JTableListModel) tableModel).getCurrentRows();

			if (size > 0 && !tfQueryValue.getText().equals("")
					&& list.isEmpty()) {
				jTable.getSelectionModel().setSelectionInterval(0, size - 1);
			}

			list = ((JTableListModel) tableModel).getCurrentRows();

			setReturnList(list);
		}
		this.setOk(true);
		this.dispose();
	}

	/**
	 * 
	 */
	private void setSelectedFirstRow() {

		if (((JTableListModel) getJTable().getModel()).getRowCount() > 0) {
			List list = ((JTableListModel) getJTable().getModel())
					.getCurrentRows();
			if (list.size() > 0) {
				// setReturnValue(list.get(0));
				// ((JTableListModel) getJTable().getModel()).sets(list.get(0));
			} else {
				// setReturnValue(((JTableListModel) getJTable().getModel())
				// .getValueAt(0));
				((JTableListModel) getJTable().getModel()).setSelectRow(0);
			}
		}

		if (((JTableListModel) getJTable().getModel()).getRowCount() > 0) {
			// ((JTableListModel) getJTable().getModel()).setSelectRow(0);
			getJTable().requestFocus();
		}

		//
		//
		//
		// if (((JTableListModel) getJTable().getModel()).getColumnCount() > 0)
		// {
		// ((JTableListModel) getJTable().getModel()).setSelectRow(0);
		// }
	}

	/**
	 * 
	 * This method initializes btnCancel
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCommonQuery.this.setOk(false);
					DgCommonQuery.this.dispose();
				}
			});

		}
		return btnCancel;
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
	public JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						if (getSingleResult()) {
							setReturnValue(((JTableListModel) getJTable()
									.getModel()).getCurrentRow());
						} else {
							setReturnList(((JTableListModel) getJTable()
									.getModel()).getCurrentRowsSele());
						}
						setOk(true);
						dispose();
					}
				}
			});
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModel == null)
								return;
							if (tableModel.getCurrentRow() == null)
								return;
						}
					});
			jTable.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == 10) {// Enter
						getResult();
					}
				}
			});
		}
		return jTable;
	}

	public boolean isExist(int name) {
		int[] s = jTable.getSelectedColumns();
		for (int i = 0; i < s.length; i++) {
			if (s[i] == name) {
				return true;
			}
		}
		return false;
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
		}
		return jScrollPane;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	protected JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.add(getJPanel());
			jToolBar.add(getJRadioButton());
			jToolBar.add(getJRadioButton1());
		}
		return jToolBar;
	}

	private void getTableModel(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						if (DgCommonQuery.getTableColumns() == null) {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(new JTableListColumn("编号", "code", 100));
							list.add(new JTableListColumn("名称", "name", 150));
							DgCommonQuery.setTableColumns(list);
							return list;
						} else {
							List list = DgCommonQuery.getTableColumns()
									.subList(
											0,
											DgCommonQuery.getTableColumns()
													.size());
							DgCommonQuery.tableColumns = null;
							return list;
						}

					}
				});
	}

	public void setVisible(boolean b) {
		if (b) {
			jRadioButton1.setSelected(this.isLike);
			jRadioButton.setSelected(!this.isLike);
			getTableModel(dataSource);
			//
			// 不能自定义列
			//
			tableModel.setMiRenderColumnEnabled(false);

			if (initObject != null) {
				tableModel.setTableSelectedRow(initObject);
			}
			if (DgCommonQuery.this.getSingleResult()) {
				this.jTable
						.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			} else {
				this.jTable
						.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			}

			if (tableModel != null) {
				for (int i = 0; i < tableModel.getColumnCount(); i++) {
					JTableListColumn c = tableModel.getColumns().get(i);
					if (c instanceof SerialColumn) {
						continue;
					}
					if (c.isShowSearch() == false) {
						continue;
					}
					getCbbQueryField().addItem(
							new Item(c.getCaption(),
									c.getCustomProperty() == null ? c
											.getProperty() : c
											.getCustomProperty(), c
											.getDataType() != DataType.NULL ? c
											.getDataType()
											: getDataTypeByColumns(c
													.getProperty())));
					// if ("materiel.ptNo".equals(c.getProperty())) {
					// String key = "materiel.ptNo";
					// indexmap.put(key, i);
					// }
				}
				// if (indexmap.get("materiel.ptNo") != null) {
				// int index = indexmap.get("materiel.ptNo") - 1;
				// cbbQueryField.setSelectedIndex(index);
				// }

			}

			// int columnCount = tableModel.getColumnCount();
			// getCbbQueryField().removeAllItems();
			// for (int i = 1; i < columnCount; i++) {
			// getCbbQueryField().addItem(tableModel.getColumnName(i));
			// }
			//

			getCbbQueryField().setSelectedIndex(0);
		}
		setTableColumnWidth();
		List<Object> components = new ArrayList<Object>();
		components.add(getTfQueryValue());
		components.add(jTable);
		// components.add(getBtnOK());
		components.add(null);
		this.setComponentFocusList(components);
		super.setVisible(b);
	}

	/**
	 * 如果这个列module的总体长度 > 了表的长度那么不做任何动作 反之设置这个module的长度等于这个表的宽度
	 */
	private void setTableColumnWidth() {
		if (this.jScrollPane.getWidth() > jTable.getColumnModel()
				.getTotalColumnWidth()) {
			int width = this.jScrollPane.getWidth()
					- jTable.getColumnModel().getColumn(0).getWidth() - 2;
			int count = this.jTable.getColumnCount() - 1;
			int eachColumnWidth = width / count;
			for (int i = 1; i <= count; i++) {
				jTable.getColumnModel().getColumn(i)
						.setPreferredWidth(eachColumnWidth);
			}
			jScrollPane.setViewportView(getJTable());
		}
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			// jRadioButton.setSelected(false);
			jRadioButton.setText("精确");

		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			// jRadioButton1.setSelected(true);
			jRadioButton1.setText("模糊");
		}
		return jRadioButton1;
	}

	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}

	/**
	 * 获得数据类型来自列
	 * 
	 * @param col
	 * @return
	 */
	private int getDataTypeByColumns(String sProp) {
		List list = tableModel.getList();
		if (list == null || list.size() <= 0) {
			return DataType.NULL;
		}
		int dataType = DataType.NULL;
		try {
			if (list.get(0) == null) {
				return dataType;
			}
			Class cls = CommonVariables.getTypeByField(list.get(0).getClass(),
					sProp);
			if (cls.equals(Integer.class) || cls.equals(Long.class)
					|| cls.equals(Short.class)) {
				dataType = DataType.INTEGER;
			} else if (cls.equals(Double.class) || cls.equals(Float.class)) {
				dataType = DataType.DOUBLE;
			} else if (cls.equals(String.class)) {
				dataType = DataType.STRING;
			} else if (cls.equals(Boolean.class)) {
				dataType = DataType.BOOLEAN;
			} else if (cls.equals(Date.class) || cls.equals(Calendar.class)) {
				dataType = DataType.DATE;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dataType;
	}

} // @jve:decl-index=0:visual-constraint="10,10"

