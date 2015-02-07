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
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
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
import com.bestway.common.constant.GbkToBig5Flag;
import com.bestway.common.constant.JDBCFlag;
import com.bestway.common.dataexport.action.DataExportAction;
import com.bestway.common.dataexport.entity.JDBCRegion;
import com.bestway.common.dataexport.entity.JDBCRegionSetup;
import com.bestway.ui.winuicontrol.JDialogBase;


/**
 * @author luosheng 2006/9/1
 * 
 */
public class DgJDBCRegionEdit extends JDialogBase {

	private javax.swing.JPanel	jContentPane				= null;
	private JSplitPane			jSplitPane					= null;
	private JPanel				jPanel						= null;
	private JPanel				jPanel1						= null;
	private JToolBar			jToolBar					= null;
	private JButton				btnEditRegionSetup			= null;
	private JTable				jTable						= null;
	private JScrollPane			jScrollPane					= null;
	private JToolBar			jToolBar1					= null;
	private JButton				btnEdit						= null;
	private JButton				btnSave						= null;
	private JButton				btnClose					= null;
	private JPanel				jPanel2						= null;
	private JTextField			tfRegionName				= null;
	private JTextField			tfDestTableName				= null;
	private JTextField			tfSrcJDBCDataSourceName		= null;
	private JTextField			tfSrcJDBCViewName			= null;
	private JComboBox			cbbGbkToBig5Flag			= null;
	private JTextField			tfNote						= null;
	private DataExportAction	dataExportAction			= null;
	private JDBCRegion			jdbcRegion					= null;
	private JTableListModel		tableModel					= null;
	private JTableListModel		tableModel1					= null;
	private int					dataState					= DataState.BROWSE;
	private JButton				btnDelete					= null;
	private JLabel				lb2							= null;
	private JTextField			tfDestJDBCDataSourceName	= null;

	/**
	 * This is the default constructor
	 */
	public DgJDBCRegionEdit() {
		super();
		dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");
		initialize();
		initUIComponents();
		setState();
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			jdbcRegion = (JDBCRegion) tableModel.getCurrentRow(); // 格式
			showData();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("域编辑");
		this.setSize(697, 495);
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
			jSplitPane.setDividerLocation(150);
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
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJPanel2(), java.awt.BorderLayout.CENTER);
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
			jPanel1.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
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
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnEditRegionSetup());
			jToolBar.add(getBtnDelete());
		}
		return jToolBar;
	}

	/**
	 * 
	 * This method initializes btnEditRegionSetup
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnEditRegionSetup() {
		if (btnEditRegionSetup == null) {
			btnEditRegionSetup = new JButton();
			btnEditRegionSetup.setText("编辑对应关系");
			btnEditRegionSetup
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {
							 if(jdbcRegion == null){
								 JOptionPane.showMessageDialog(DgJDBCRegionEdit.this,
											"域对象不能为空！", "提示", 2);
								 return ;
							 }
							 DgJDBCRegionSetupEdit dg = new DgJDBCRegionSetupEdit();
							 dg.setTableModel1(tableModel1);
							 dg.setJdbcRegion(jdbcRegion);
							 dg.setVisible(true);
						}
					});

		}
		return btnEditRegionSetup;
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
	 * This method initializes jToolBar1
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getBtnEdit());
			jToolBar1.add(getBtnSave());
			jToolBar1.add(getBtnClose());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes tfDestJDBCDataSourceName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDestJDBCDataSourceName() {
		if (tfDestJDBCDataSourceName == null) {
			tfDestJDBCDataSourceName = new JTextField();
			tfDestJDBCDataSourceName.setBackground(Color.white);
			tfDestJDBCDataSourceName.setBounds(new Rectangle(174, 57, 170, 22));
			tfDestJDBCDataSourceName.setEditable(false);
		}
		return tfDestJDBCDataSourceName;
	}

	/**
	 * 
	 * This method initializes btnEdit
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					dataState = DataState.EDIT;
					setState();
				}
			});

		}
		return btnEdit;
	}

	/**
	 * 
	 * This method initializes btnSave
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!validateData()) {
						return;
					}
					saveData();
				}
			});

		}
		return btnSave;
	}

	/**
	 * 
	 * This method initializes btnClose
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

					DgJDBCRegionEdit.this.dispose();
				}
			});

		}
		return btnClose;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			lb2 = new JLabel();
			lb2.setBounds(new Rectangle(95, 59, 70, 20));
			lb2.setText("目的数据源");
			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setBounds(95, 6, 45, 19);
			jLabel.setText("域名称");
			jLabel1.setBounds(381, 58, 56, 21);
			jLabel1.setText("目标表名");
			jLabel2.setBounds(95, 30, 72, 20);
			jLabel2.setText("数据源名称");
			jLabel3.setBounds(381, 30, 56, 21);
			jLabel3.setText("视图名称");
			jLabel4.setBounds(95, 86, 72, 22);
			jLabel4.setText("字符集转换");
			jLabel5.setBounds(381, 85, 47, 21);
			jLabel5.setText("域描述");
			jPanel2.add(jLabel, null);
			jPanel2.add(getTfRegionName(), null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getTfDestTableName(), null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getTfSrcJDBCDataSourceName(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getTfSrcJDBCViewName(), null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getCbbGbkToBig5Flag(), null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getTfNote(), null);
			jPanel2.add(lb2, null);
			jPanel2.add(getTfDestJDBCDataSourceName(), null);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfRegionName() {
		if (tfRegionName == null) {
			tfRegionName = new JTextField();
			tfRegionName.setBounds(174, 6, 170, 22);
		}
		return tfRegionName;
	}

	/**
	 * 
	 * This method initializes jTextField1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfDestTableName() {
		if (tfDestTableName == null) {
			tfDestTableName = new JTextField();
			tfDestTableName.setEditable(false);
			tfDestTableName.setBackground(Color.white);
			tfDestTableName.setBounds(443, 58, 170, 22);
		}
		return tfDestTableName;
	}

	/**
	 * 
	 * This method initializes jTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfSrcJDBCDataSourceName() {
		if (tfSrcJDBCDataSourceName == null) {
			tfSrcJDBCDataSourceName = new JTextField();
			tfSrcJDBCDataSourceName.setEditable(false);
			tfSrcJDBCDataSourceName.setBackground(Color.white);
			tfSrcJDBCDataSourceName.setBounds(174, 30, 170, 22);
		}
		return tfSrcJDBCDataSourceName;
	}

	/**
	 * 
	 * This method initializes jTextField3
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfSrcJDBCViewName() {
		if (tfSrcJDBCViewName == null) {
			tfSrcJDBCViewName = new JTextField();
			tfSrcJDBCViewName.setEditable(false);
			tfSrcJDBCViewName.setBackground(Color.white);
			tfSrcJDBCViewName.setBounds(443, 30, 170, 22);
		}
		return tfSrcJDBCViewName;
	}

	/**
	 * 
	 * This method initializes jComboBox
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getCbbGbkToBig5Flag() {
		if (cbbGbkToBig5Flag == null) {
			cbbGbkToBig5Flag = new JComboBox();
			cbbGbkToBig5Flag.setBounds(174, 86, 170, 22);
		}
		return cbbGbkToBig5Flag;
	}

	/**
	 * 
	 * This method initializes jTextField4
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextField();
			tfNote.setBounds(443, 85, 170, 22);
		}
		return tfNote;
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
	 * 
	 * This method initializes btnDelete
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除对应关系");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel1.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgJDBCRegionEdit.this,
								"请选中字段对应！", "提示", 2);
						return;
					}
					JDBCRegionSetup obj = (JDBCRegionSetup) tableModel1
							.getCurrentRow();
					dataExportAction.deleteJDBCRegionSetup(new Request(
							CommonVars.getCurrUser()), obj);
					tableModel1.deleteRow(obj);

				}
			});

		}
		return btnDelete;
	}

	private void initUIComponents() {
		// 转换
		this.cbbGbkToBig5Flag.removeAllItems();
		this.cbbGbkToBig5Flag
				.addItem(new ItemProperty(GbkToBig5Flag.NO, "不转换"));
		this.cbbGbkToBig5Flag.addItem(new ItemProperty(
				GbkToBig5Flag.BIG5_TO_GBK, "繁转简"));
		this.cbbGbkToBig5Flag.addItem(new ItemProperty(
				GbkToBig5Flag.GBK_TO_BIG5, "简转繁"));
		this.cbbGbkToBig5Flag.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbGbkToBig5Flag);
	}

	private void showData() {
		tfRegionName.setText(jdbcRegion.getRegionName());
		tfDestTableName.setText(jdbcRegion.getDestTableName());
		tfSrcJDBCDataSourceName.setText(jdbcRegion.getSrcJDBCView()
				.getJdbcDataSource().getName());
		tfSrcJDBCViewName.setText(jdbcRegion.getSrcJDBCView().getName());
		tfNote.setText(jdbcRegion.getNote());
		tfDestJDBCDataSourceName.setText(jdbcRegion.getDestJDBCDataSource()
				.getName());
		// 转换方式
		if (jdbcRegion.getGbkToBig5Flag() != null) {
			cbbGbkToBig5Flag.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf(this.jdbcRegion.getGbkToBig5Flag()),
					cbbGbkToBig5Flag));
		} else {
			cbbGbkToBig5Flag.setSelectedIndex(-1);
		}

		List<JDBCRegionSetup> list = dataExportAction.findJDBCRegionSetup(
				new Request(CommonVars.getCurrUser()), jdbcRegion.getId());
		initTable(list);
	}

	private void fillData() {
		jdbcRegion.setRegionName(tfRegionName.getText());
		jdbcRegion.setNote(tfNote.getText());
		if (cbbGbkToBig5Flag.getSelectedItem() != null) {
			jdbcRegion.setGbkToBig5Flag(((ItemProperty) cbbGbkToBig5Flag
					.getSelectedItem()).getCode());
		}
	}

	private boolean validateData() {
		if (tfRegionName.getText().equals("")) {
			JOptionPane.showMessageDialog(DgJDBCRegionEdit.this, "请填写域名称！",
					"确认", 2);
			return false;
		}
		return true;
	}

	private void saveData() {
		fillData();
		jdbcRegion = dataExportAction.saveJDBCRegion(new Request(CommonVars
				.getCurrUser()), jdbcRegion);
		tableModel.updateRow(jdbcRegion);
		dataState = DataState.BROWSE;
		setState();
	}

	private void initTable(List dataSource) {
		tableModel1 = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();	
						list.add(addColumn("目的表字段名", "destFieldName", 80));
						list.add(addColumn("目的表字段类型", "destFieldTypeDesc",
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
		jTable.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
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
				}else if (value.equals(JDBCFlag.SQL)) {
					returnValue = "自定义SQL";
				}
				return returnValue;
			}
		});
		jTable.getColumnModel().getColumn(7).setCellRenderer(new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(8).setCellRenderer(new TableCheckBoxRender());		
	}

	private void setState() {
		btnEdit.setEnabled(dataState == DataState.BROWSE);
		btnSave.setEnabled(!(dataState == DataState.BROWSE));
		tfRegionName.setEditable(!(dataState == DataState.BROWSE));
		cbbGbkToBig5Flag.setEnabled(!(dataState == DataState.BROWSE));
		tfNote.setEditable(!(dataState == DataState.BROWSE));
	}

}
