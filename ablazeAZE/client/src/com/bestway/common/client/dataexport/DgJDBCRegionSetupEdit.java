/*
 * Created on 2004-11-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataexport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.JDBCFlag;
import com.bestway.common.dataexport.action.DataExportAction;
import com.bestway.common.dataexport.entity.JDBCRegion;
import com.bestway.common.dataexport.entity.JDBCRegionSetup;
import com.bestway.common.dataexport.entity.JDBCView;
import com.bestway.common.dataexport.entity.TempJDBCColumn;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author luosheng 2006/9/1
 * 
 */
public class DgJDBCRegionSetupEdit extends JDialogBase {

	private javax.swing.JPanel	jContentPane		= null;
	private JSplitPane			jSplitPane			= null;
	private JPanel				jPanel				= null;
	private JPanel				jPanel1				= null;
	private JButton				btnClose			= null;
	private JTable				jTable				= null;
	private JScrollPane			jScrollPane			= null;
	private JButton				btnAdd				= null;
	private JComboBox			cbbJDBCFlag			= null;
	private DataExportAction	dataExportAction	= null;
	private JTableListModel		tableModel			= null;
	private JTableListModel		tableModel1			= null;
	private JDBCRegion			jdbcRegion			= null;
	private JLabel				jLabel				= null;
	private JLabel				jLabel1				= null;
	private JButton				btnDelete			= null;
	private JToolBar			jToolBar			= null;
	private JScrollPane			jScrollPane2		= null;
	private JTable				tbSrc				= null;
	private JScrollPane			jScrollPane1		= null;
	private JTable				tbDest				= null;
	private JCheckBox			cbIsKey				= null;
	private JTableListModel		srcTableModel		= null;
	private JTableListModel		destTableModel		= null;
	private JButton				btnSql				= null;
	private JButton				btnEdit				= null;
	private int					dataState			= DataState.BROWSE;
	private JDBCRegionSetup		jdbcRegionSetup		= null;

	/**
	 * This is the default constructor
	 */
	public DgJDBCRegionSetupEdit() {
		super();
		dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("字段对照");
		this.setSize(641, 494);
		this.setContentPane(getJContentPane());
		this.setContentPane(getJContentPane());
		initUIComponents();
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			showData();
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
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jSplitPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(200);
			jSplitPane.setDividerSize(0);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
		}
		return jSplitPane;
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
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();

			jLabel = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel2.setBounds(222, 93, 54, 21);
			jLabel2.setText("转换方式");
			jLabel.setBounds(222, 5, 192, 20);
			jLabel.setText("目标表：");
			jLabel.setForeground(new java.awt.Color(251, 133, 15));
			jLabel1.setBounds(222, 30, 192, 20);
			jLabel1.setText("源视图：");
			jLabel1.setForeground(new java.awt.Color(251, 131, 15));
			jPanel.add(getBtnAdd(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getCbbJDBCFlag(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getBtnDelete(), null);
			jPanel.add(getJScrollPane2(), null);
			jPanel.add(getJScrollPane1(), null);
			jPanel.add(getCbIsKey(), null);
			jPanel.add(getBtnSql(), null);
			jPanel.add(getBtnEdit(), null);
		}
		return jPanel;
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
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					dispose();
				}
			});

		}
		return btnClose;
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
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {

						public void valueChanged(ListSelectionEvent arg0) {
							if (tableModel == null) {
								return;
							}
							if (tableModel.getCurrentRow() == null) {
								return;
							}
							JDBCRegionSetup data = (JDBCRegionSetup) tableModel
									.getCurrentRow();
							showValue(data);
						}
					});
		}
		return jTable;
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
			jScrollPane
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setBounds(223, 161, 59, 25);
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					addData();
				}
			});

		}
		return btnAdd;
	}

	private JComboBox getCbbJDBCFlag() {
		if (cbbJDBCFlag == null) {
			cbbJDBCFlag = new JComboBox();
			cbbJDBCFlag.setBounds(278, 93, 136, 21);
			cbbJDBCFlag.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {

						ItemProperty item = (ItemProperty) cbbJDBCFlag
								.getSelectedItem();
						String jdbcFlag = item == null ? "" : item.getCode();
						btnSql.setEnabled(JDBCFlag.SQL.equals(jdbcFlag));
					}
				}
			});
		}
		return cbbJDBCFlag;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * @return Returns the dbFormat.
	 */
	public JDBCRegion getJdbcRegion() {
		return this.jdbcRegion;
	}

	/**
	 * @param dbFormat
	 *            The dbFormat to set.
	 */
	public void setJdbcRegion(JDBCRegion jdbcRegion) {
		this.jdbcRegion = jdbcRegion;
	}

	/**
	 * @return Returns the tableModel1.
	 */
	public JTableListModel getTableModel1() {
		return tableModel1;
	}

	/**
	 * @param tableModel1
	 *            The tableModel1 to set.
	 */
	public void setTableModel1(JTableListModel tableModel1) {
		this.tableModel1 = tableModel1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setBounds(355, 161, 59, 25);
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgJDBCRegionSetupEdit.this, "请选中要删除的对应?", "提示",
								2);
						return;
					}

					if (dataState == DataState.BROWSE) { // 删除
						JDBCRegionSetup setup = (JDBCRegionSetup) tableModel
								.getCurrentRow();
						dataExportAction.deleteJDBCRegionSetup(new Request(
								CommonVars.getCurrUser()), setup);
						tableModel.deleteRow(setup);
						tableModel1.deleteRow(setup);
					} else if (dataState == DataState.EDIT) { // 取消
						dataState = DataState.BROWSE;
						setState();
					}

				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setBounds(new Rectangle(419, 2, 212, 196));
			jScrollPane2.setBackground(Color.white);
			jScrollPane2.setViewportView(getTbSrc());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes tbSrc
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbSrc() {
		if (tbSrc == null) {
			tbSrc = new JTable();
			tbSrc.setBackground(Color.white);
			// tbSrc.setShowHorizontalLines(false);
			// tbSrc.setShowVerticalLines(false);
		}
		return tbSrc;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(new Rectangle(2, 2, 214, 195));
			jScrollPane1.setViewportView(getTbDest());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes btnSql
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSql() {
		if (btnSql == null) {
			btnSql = new JButton();
			btnSql.setBounds(new Rectangle(278, 123, 136, 25));
			btnSql.setText("显示转换SQL");
			btnSql.setEnabled(false);
			btnSql.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showSqlDialog(jdbcRegionSetup);
				}
			});
		}
		return btnSql;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */

	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setBounds(new Rectangle(289, 161, 59, 25));
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JDBCRegionSetup temp = (JDBCRegionSetup) tableModel
							.getCurrentRow();
					if (temp == null) {
						JOptionPane.showMessageDialog(
								DgJDBCRegionSetupEdit.this, "请选择要修改的对应记录！",
								"提示", 2);
						return;
					}
					if (dataState == DataState.BROWSE) { // 修改
						jdbcRegionSetup = temp;
						dataState = DataState.EDIT;
						setState();
					} else if (dataState == DataState.EDIT) { // 保存
						saveData();
						dataState = DataState.BROWSE;
						setState();
					}
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes tbDest
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbDest() {
		if (tbDest == null) {
			tbDest = new JTable();
			// tbDest.setShowHorizontalLines(false);
			// tbDest.setShowVerticalLines(false);
		}
		return tbDest;
	}

	/**
	 * This method initializes cbIsKey
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsKey() {
		if (cbIsKey == null) {
			cbIsKey = new JCheckBox();
			cbIsKey.setBounds(new Rectangle(222, 53, 188, 37));
			cbIsKey.setText("<html>当前目标字段是否是在更新导入时<br>判定唯一性的关键列</html>");
		}
		return cbIsKey;
	}

	private void initUIComponents() {
		// 转换方式
		this.cbbJDBCFlag.removeAllItems();
		this.cbbJDBCFlag.addItem(new ItemProperty(JDBCFlag.NO, "无"));
		this.cbbJDBCFlag.addItem(new ItemProperty(JDBCFlag.SQL, "自定义SQL"));
		this.cbbJDBCFlag.addItem(new ItemProperty(JDBCFlag.CURRENT_COMPANY_ID,
				"当前公司ID"));
		this.cbbJDBCFlag.addItem(new ItemProperty(JDBCFlag.GUID, "32位的GUID"));
		this.cbbJDBCFlag
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbJDBCFlag);
	}

	private void showData() {
		//
		// 显示视图名
		//
		cbbJDBCFlag.setSelectedIndex(0);
		this.jLabel.setText("目标表：" + jdbcRegion.getDestTableName());// 目标表
		this.jLabel1.setText("源视图：" + jdbcRegion.getSrcJDBCView().getName());// 源视图

		initSourceFields();
		initDestFields();
		initData();
	}

	private void initData() {
		List list = dataExportAction.findJDBCRegionSetup(new Request(CommonVars
				.getCurrUser()), jdbcRegion.getId());
		initTable(list);
		if (list.size() > 0) {
			JDBCRegionSetup data = (JDBCRegionSetup) list.get(0);
			showValue(data);
		}
	}

	private void initTable(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("目的表字段名", "destFieldName", 80));
						list
								.add(addColumn("目的表字段类型", "destFieldTypeDesc",
										100));
						list.add(addColumn("转换方式", "jdbcFlag", 60));
						list.add(addColumn("源表字段名", "srcFieldName", 80));
						list.add(addColumn("源表字段类型", "srcFieldTypeDesc", 80));
						list.add(addColumn("目的表字段对应的SQL", "sqlStr", 150));
						list.add(addColumn("SQL是否使用缓存", "isCache", 100));
						list.add(addColumn("是否是判定唯一性的关键列", "isKey", 150));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";

						if (value.equals(JDBCFlag.NO)) {
							returnValue = "不转换";
						} else if (value.equals(JDBCFlag.CURRENT_COMPANY_ID)) {
							returnValue = "当前公司ID";
						} else if (value.equals(JDBCFlag.GUID)) {
							returnValue = "32位的GUID";
						} else if (value.equals(JDBCFlag.SQL)) {
							returnValue = "自定义SQL";
						}
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(7).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(8).setCellRenderer(
				new TableCheckBoxRender());
	}

	private void initSourceFields() { // 源表字段名
		JDBCView jdbcView = jdbcRegion.getSrcJDBCView();
		List<TempJDBCColumn> list = dataExportAction.getSrcTempJDBCColumn(
				new Request(CommonVars.getCurrUser()), jdbcView
						.getJdbcDataSource(), jdbcView.getSqlScript());
		initTableSource(list);
	}

	private void initTableSource(List dataSource) {
		srcTableModel = new JTableListModel(tbSrc, dataSource,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("源表字段名", "fieldName", 90));
						list.add(addColumn("类型", "fieldTypeDesc", 90));
						return list;
					}
				});
	}

	private void initDestFields() { // 目的表字段名
		List<TempJDBCColumn> list = dataExportAction
				.getDestTempJDBCColumn(new Request(CommonVars.getCurrUser()),
						jdbcRegion.getDestJDBCDataSource(), jdbcRegion
								.getDestTableName());
		initTableDest(list);
	}

	private void initTableDest(List dataSource) {
		destTableModel = new JTableListModel(tbDest, dataSource,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("目的表字段名", "fieldName", 85));
						list.add(addColumn("类型", "fieldTypeDesc", 62));
						list.add(addColumn("主键", "isPrimaryKey", 28));
						return list;
					}
				});
		tbDest.getColumnModel().getColumn(3).setCellRenderer(
				new TableCheckBoxRender());
	}

	private void showValue(JDBCRegionSetup data) {
		if (data.getJdbcFlag() != null) {
			this.cbbJDBCFlag.setSelectedIndex(ItemProperty.getIndexByCode(data
					.getJdbcFlag(), cbbJDBCFlag));
		} else {
			this.cbbJDBCFlag.setSelectedIndex(-1);
		}
		boolean isKey = data.getIsKey() == null ? false : data.getIsKey();
		this.cbIsKey.setSelected(isKey);

		List destDataSource = destTableModel.getList();
		for (int i = 0; i < destDataSource.size(); i++) {
			TempJDBCColumn destJDBCColumn = (TempJDBCColumn) destDataSource
					.get(i);
			if (data.getDestFieldName().equals(destJDBCColumn.getFieldName())) {
				destTableModel.setTableSelectedRow(destJDBCColumn);
				break;
			}
		}

		List srcDataSource = srcTableModel.getList();
		if (data.getSrcFieldName() != null) {
			for (int i = 0; i < srcDataSource.size(); i++) {
				TempJDBCColumn srcJDBCColumn = (TempJDBCColumn) srcDataSource
						.get(i);
				if (data.getSrcFieldName().equals(srcJDBCColumn.getFieldName())) {
					srcTableModel.setTableSelectedRow(srcJDBCColumn);
					break;
				}
			}
		}

		jdbcRegionSetup = data;
		dataState = DataState.BROWSE;
		setState();
	}

	/** 新增 */
	private void addData() {
		dataState = DataState.ADD;
		JDBCRegionSetup d = fillData();

		if (d == null) {
			dataState = DataState.BROWSE;
			setState();
			return;
		} else {
			jdbcRegionSetup = dataExportAction.saveJDBCRegionSetup(new Request(
					CommonVars.getCurrUser()), d);
			tableModel.addRow(jdbcRegionSetup);
			tableModel1.addRow(jdbcRegionSetup);
			dataState = DataState.BROWSE;
			setState();
		}
	}

	/** 保存 */
	private void saveData() {
		JDBCRegionSetup d = fillData();
		if (d == null) {
			this.dataState = DataState.BROWSE;
			setState();
			return;
		} else {
			jdbcRegionSetup = dataExportAction.saveJDBCRegionSetup(new Request(
					CommonVars.getCurrUser()), d);
			tableModel.updateRow(jdbcRegionSetup);
			tableModel1.updateRow(jdbcRegionSetup);
			this.dataState = DataState.BROWSE;
			setState();
		}
	}

	private JDBCRegionSetup fillData() {
		TempJDBCColumn destJDBCColumn = (TempJDBCColumn) destTableModel
				.getCurrentRow();
		TempJDBCColumn srcJDBCColumn = (TempJDBCColumn) srcTableModel
				.getCurrentRow();
		if (destJDBCColumn == null) {
			JOptionPane.showMessageDialog(DgJDBCRegionSetupEdit.this,
					"没有选中目的字段!", "提示", 2);
			return null;
		}
		//
		// 新增对象
		//
		JDBCRegionSetup jdbcRegionSetup = null;
		if (dataState == DataState.ADD) {
			jdbcRegionSetup = new JDBCRegionSetup();
		} else {
			jdbcRegionSetup = this.jdbcRegionSetup;
		}

		ItemProperty item = (ItemProperty) this.cbbJDBCFlag.getSelectedItem();
		String jdbcFlag = item.getCode();
		if (JDBCFlag.NO.equals(jdbcFlag)) {
			if (srcJDBCColumn == null) {
				JOptionPane.showMessageDialog(DgJDBCRegionSetupEdit.this,
						"没有选中的源字段!", "提示", 2);
				return null;
			}
			jdbcRegionSetup.setCompany(CommonVars.getCurrUser().getCompany());
			jdbcRegionSetup.setDestFieldName(destJDBCColumn.getFieldName());
			jdbcRegionSetup.setDestFieldType(destJDBCColumn.getFieldType());
			jdbcRegionSetup.setDestFieldTypeDesc(destJDBCColumn
					.getFieldTypeDesc());
			jdbcRegionSetup.setIsPrimaryKey(destJDBCColumn.getIsPrimaryKey());
			jdbcRegionSetup.setSqlStr("");

			jdbcRegionSetup.setSrcFieldName(srcJDBCColumn.getFieldName());
			jdbcRegionSetup.setSrcFieldType(srcJDBCColumn.getFieldType());
			jdbcRegionSetup.setSrcFieldTypeDesc(srcJDBCColumn
					.getFieldTypeDesc());
			jdbcRegionSetup.setJdbcFlag(jdbcFlag);
			jdbcRegionSetup.setIsKey(this.cbIsKey.isSelected());
			jdbcRegionSetup.setJdbcRegion(jdbcRegion);

		} else if (JDBCFlag.CURRENT_COMPANY_ID.equals(jdbcFlag)
				|| JDBCFlag.GUID.equals(jdbcFlag)) {

			jdbcRegionSetup.setCompany(CommonVars.getCurrUser().getCompany());
			jdbcRegionSetup.setDestFieldName(destJDBCColumn.getFieldName());
			jdbcRegionSetup.setDestFieldType(destJDBCColumn.getFieldType());
			jdbcRegionSetup.setDestFieldTypeDesc(destJDBCColumn
					.getFieldTypeDesc());
			jdbcRegionSetup.setIsPrimaryKey(destJDBCColumn.getIsPrimaryKey());
			jdbcRegionSetup.setSqlStr("");

			jdbcRegionSetup.setSrcFieldName(null);
			jdbcRegionSetup.setSrcFieldType(null);
			jdbcRegionSetup.setSrcFieldTypeDesc(null);
			jdbcRegionSetup.setJdbcFlag(jdbcFlag);
			jdbcRegionSetup.setIsKey(this.cbIsKey.isSelected());
			jdbcRegionSetup.setJdbcRegion(jdbcRegion);

		} else if (JDBCFlag.SQL.equals(jdbcFlag)) {
			if (dataState == DataState.ADD) {
				//
				// dialog --
				//
				if (showSqlDialog(jdbcRegionSetup) == false) {
					return null;
				}
			}
			jdbcRegionSetup.setCompany(CommonVars.getCurrUser().getCompany());
			jdbcRegionSetup.setDestFieldName(destJDBCColumn.getFieldName());
			jdbcRegionSetup.setDestFieldType(destJDBCColumn.getFieldType());
			jdbcRegionSetup.setDestFieldTypeDesc(destJDBCColumn
					.getFieldTypeDesc());
			jdbcRegionSetup.setIsPrimaryKey(destJDBCColumn.getIsPrimaryKey());

			jdbcRegionSetup.setSrcFieldName(null);
			jdbcRegionSetup.setSrcFieldType(null);
			jdbcRegionSetup.setSrcFieldTypeDesc(null);

			jdbcRegionSetup.setJdbcFlag(jdbcFlag);
			jdbcRegionSetup.setIsKey(this.cbIsKey.isSelected());
			jdbcRegionSetup.setJdbcRegion(jdbcRegion);
		}
		return jdbcRegionSetup;
	}

	private void setState() {
		this.btnAdd.setEnabled(dataState == DataState.BROWSE);
		this.jTable.setEnabled(dataState == DataState.BROWSE);

		if (dataState == DataState.BROWSE) {
			this.btnDelete.setText("删除");
			this.btnEdit.setText("修改");
		} else if (dataState == DataState.EDIT) {
			this.btnDelete.setText("取消");
			this.btnEdit.setText("保存");
		}
	}

	private boolean showSqlDialog(JDBCRegionSetup jdbcRegionSetup) {
		if (jdbcRegionSetup == null) {
			JOptionPane.showMessageDialog(DgJDBCRegionSetupEdit.this,
					"没有对应记录可显示!", "提示", 2);
			return false;
		}
		TempJDBCColumn destJDBCColumn = (TempJDBCColumn) destTableModel
				.getCurrentRow();
		if (destJDBCColumn == null) {
			JOptionPane.showMessageDialog(DgJDBCRegionSetupEdit.this,
					"没有选中目的字段!", "提示", 2);
			return false;
		}
		
		String destFieldInfo = "";
		if (dataState == DataState.ADD) {
			destFieldInfo = destJDBCColumn.getFieldName() + " "
					+ destJDBCColumn.getFieldTypeDesc();
		} else {
			destFieldInfo = jdbcRegionSetup.getDestFieldName() + " "
					+ jdbcRegionSetup.getDestFieldTypeDesc();
		}

		List srcDataSource = this.srcTableModel.getList();

		DgJDBCRegionSetupSql dg = new DgJDBCRegionSetupSql();
		dg.setDataState(dataState);
		dg.setJdbcRegion(jdbcRegion);
		dg.setJdbcRegionSetup(jdbcRegionSetup);
		dg.setDestFieldInfo(destFieldInfo);
		dg.setSrcDataSource(srcDataSource);
		dg.setVisible(true);
		return dg.isOk();
	}

}
