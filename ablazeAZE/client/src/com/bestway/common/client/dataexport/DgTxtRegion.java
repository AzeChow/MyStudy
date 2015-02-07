/*
 * Created on 2004-11-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataexport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.FileType;
import com.bestway.common.constant.GbkToBig5Flag;
import com.bestway.common.dataexport.action.DataExportAction;
import com.bestway.common.dataexport.entity.DBToTxtRegion;
import com.bestway.common.dataexport.entity.JDBCDataSource;
import com.bestway.common.dataexport.entity.JDBCView;
import com.bestway.common.dataexport.entity.TxtToDBRegion;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author luosheng 2006/9/1
 * 
 */

public class DgTxtRegion extends JDialogBase {

	private javax.swing.JPanel	jContentPane		= null;
	private JToolBar			jToolBar			= null;
	private JButton				btnAdd				= null;
	private JButton				btnEdit				= null;
	private JButton				btnDelete			= null;
	private JButton				btnClose			= null;
	private JTable				jTable				= null;
	private JScrollPane			jScrollPane			= null;
	private JTableListModel		tableModel			= null;
	private JTableListModel		tableModel1			= null;
	private DataExportAction	dataExportAction	= null;
	private JSplitPane			jSplitPane			= null;
	private JPanel				pnLeft				= null;
	private JPanel				pnRight				= null;
	private JToolBar			jJToolBarBar		= null;
	private JScrollPane			jScrollPane1		= null;
	private JList				jList				= null;
	private JLabel				jLabel				= null;
	private JComboBox			cbbJDBCDataSource	= null;
	private JTabbedPane			jTabbedPane			= null;
	private JButton				btnCopy				= null;
	private JTabbedPane			jTabbedPane1		= null;
	private JPanel				jPanel				= null;
	private JToolBar			jJToolBarBar1		= null;
	private JScrollPane			jScrollPane2		= null;
	private JTable				jTable1				= null;
	private JButton				btnAdd1				= null;
	private JButton				btnEdit1			= null;
	private JButton				btnDelete1			= null;
	private JButton				btnCopy1			= null;
	private JButton				btnClose1			= null;

	/**
	 * This is the default constructor
	 */
	public DgTxtRegion() {
		super(false);
		dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");
		initialize();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initUIComponents();
	}

	private void initUIComponents() {
		initCbbJDBCDataSource();
		initTable1();
	}

	private void initCbbJDBCDataSource() {
		List<JDBCDataSource> list = this.dataExportAction
				.findJDBCDataSource(new Request(CommonVars.getCurrUser()));
		this.cbbJDBCDataSource
				.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbJDBCDataSource.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
				if (value != null && value instanceof JDBCDataSource) {
					JDBCDataSource d = (JDBCDataSource) value;
					this.setText(d.getName());
				}
				return this;
			}
		});
		if (this.cbbJDBCDataSource.getModel().getSize() > 0) {
			this.cbbJDBCDataSource.setSelectedIndex(-1);
			this.cbbJDBCDataSource.setSelectedIndex(0);
		}
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
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnCopy());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * 
	 * This method initializes btnAdd
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("增加");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					JDBCDataSource jdbcDataSource = (JDBCDataSource) (cbbJDBCDataSource
							.getSelectedItem());
					if (jdbcDataSource == null) {
						JOptionPane.showMessageDialog(DgTxtRegion.this,
								"请选择数据源", "确认", 2);
						return;
					}
					DgDBToTxtRegionEdit dg = new DgDBToTxtRegionEdit();
					dg.setTableModel(tableModel);
					JDBCView jdbcView = (JDBCView) (jList.getSelectedValue());
					dg.setJdbcView(jdbcView);
					dg.setDataState(DataState.ADD);
					dg.setVisible(true);

				}
			});

		}
		return btnAdd;
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
					DBToTxtRegion dbToTxtRegion = (DBToTxtRegion) tableModel
							.getCurrentRow();
					if (dbToTxtRegion == null) {
						JOptionPane.showMessageDialog(DgTxtRegion.this,
								"请选择你要修改的资料", "确认", 2);
						return;
					}
					DgDBToTxtRegionEdit dg = new DgDBToTxtRegionEdit();
					dg.setTableModel(tableModel);
					JDBCView jdbcView = (JDBCView) (jList.getSelectedValue());
					dg.setJdbcView(jdbcView);
					dg.setDataState(DataState.EDIT);
					dg.setVisible(true);
				}
			});

		}
		return btnEdit;
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
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(DgTxtRegion.this,
							"确定删除吗?", "提示", 0) == 0) {
						if (tableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgTxtRegion.this,
									"请选中要删除的资料！", "提示", 2);
							return;
						}

						DBToTxtRegion obj = (DBToTxtRegion) tableModel
								.getCurrentRow();
						try {
							dataExportAction.deleteDBToTxtRegion(new Request(
									CommonVars.getCurrUser()), obj);
							tableModel.deleteRow(obj);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(DgTxtRegion.this,
									"已被引用，不能删除！", "提示", 2);
						}
					}
				}
			});

		}
		return btnDelete;
	}

	/**
	 * This method initializes btnCopy
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCopy() {
		if (btnCopy == null) {
			btnCopy = new JButton();
			btnCopy.setText("转抄");
			btnCopy.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgTxtRegion.this,
								"请选中要转抄的资料！", "提示", 2);
						return;
					}

					DBToTxtRegion srcJDBCRegion = (DBToTxtRegion) tableModel
							.getCurrentRow();
					DBToTxtRegion newJDBCRegion = dataExportAction
							.copyDBToTxtRegion(new Request(CommonVars
									.getCurrUser()), srcJDBCRegion);
					tableModel.addRow(newJDBCRegion);
				}
			});
		}
		return btnCopy;
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

					DgTxtRegion.this.dispose();
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
		}
		return jScrollPane;
	}

	private void initTable(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {

						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("域名称", "regionName", 150));
						list.add(addColumn("目的文件路径", "destFilePath", 150));
						list.add(addColumn("导出文件类型", "fileType", 150));
						list.add(addColumn("繁简转换标志", "gbkToBig5Flag", 150));
						list.add(addColumn("源数据源",
								"srcJDBCView.jdbcDataSource.name", 150));
						list.add(addColumn("源视图名", "srcJDBCView.name", 150));
						list.add(addColumn("是否有标题栏导出", "isExistCaption", 150));
						list.add(addColumn("从第几行导出", "exportRowNumber", 150));
						list.add(addColumn("导出文件编码", "encoding", 150));
						list.add(addColumn("域描述", "note", 150));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(7).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
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
						int fileType = Integer.parseInt(value.toString());
						if (fileType == FileType.TXT) {
							returnValue = "文本文件";
						} else if (fileType == FileType.XLS) {
							returnValue = "Excel文件";
						}
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
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

						if (value.equals(GbkToBig5Flag.NO)) {
							returnValue = "不转换";
						} else if (value.equals(GbkToBig5Flag.BIG5_TO_GBK)) {
							returnValue = "繁转简";
						} else if (value.equals(GbkToBig5Flag.GBK_TO_BIG5)) {
							returnValue = "简转繁";
						}
						return returnValue;
					}
				});
	}

	/** 初始化 Txt To DB */
	private void initTable1() {
		List dataSource = this.dataExportAction.findTxtToDBRegion(new Request(
				CommonVars.getCurrUser()));

		tableModel1 = new JTableListModel(jTable1, dataSource,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("域名称", "regionName", 150));
						list.add(addColumn("源文件路径", "srcFilePath", 150));
						list.add(addColumn("导入文件类型", "fileType", 150));
						list.add(addColumn("繁简转换标致", "gbkToBig5Flag", 150));
						list.add(addColumn("目的表", "destTableName", 150));
						list.add(addColumn("目的数据源", "destJDBCDataSource.name",
								150));
						list.add(addColumn("从第几行导入", "importRowNumber", 150));
						list.add(addColumn("导入文件编码", "encoding", 150));
						list.add(addColumn("域描述", "note", 150));
						return list;
					}
				});
		jTable1.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
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
						int fileType = Integer.parseInt(value.toString());
						if (fileType == FileType.TXT) {
							returnValue = "文本文件";
						} else if (fileType == FileType.XLS) {
							returnValue = "Excel文件";
						}
						return returnValue;
					}
				});
		jTable1.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
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

						if (value.equals(GbkToBig5Flag.NO)) {
							returnValue = "不转换";
						} else if (value.equals(GbkToBig5Flag.BIG5_TO_GBK)) {
							returnValue = "繁转简";
						} else if (value.equals(GbkToBig5Flag.GBK_TO_BIG5)) {
							returnValue = "简转繁";
						}
						return returnValue;
					}
				});
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("文本导入导出-----域设置");
		this.setSize(733, 541);
		this.setContentPane(getJContentPane());
	}

	public void setVisible(boolean b) {
		if (b) {
			initData();
		}
		super.setVisible(b);
	}

	private void initData() {

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
			jContentPane.add(getJTabbedPane1(), BorderLayout.CENTER);
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
			jSplitPane.setToolTipText("从DB导出到文本");
			jSplitPane.setLeftComponent(getPnLeft());
			jSplitPane.setRightComponent(getPnRight());
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setDividerLocation(155);
			jSplitPane.setDividerSize(10);

		}
		return jSplitPane;
	}

	/**
	 * This method initializes pnLeft
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnLeft() {
		if (pnLeft == null) {
			pnLeft = new JPanel();
			pnLeft.setLayout(new BorderLayout());
			pnLeft.add(getJJToolBarBar(), BorderLayout.NORTH);
			pnLeft.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return pnLeft;
	}

	/**
	 * This method initializes pnRight
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnRight() {
		if (pnRight == null) {
			pnRight = new JPanel();
			pnRight.setLayout(new BorderLayout());
			pnRight.add(getJScrollPane(), BorderLayout.CENTER);
			pnRight.add(getJToolBar(), BorderLayout.NORTH);

		}
		return pnRight;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jLabel = new JLabel();
			jLabel.setText("数据源");
			jLabel.setSize(new Dimension(38, 18));
			jLabel.setPreferredSize(new Dimension(38, 18));
			jJToolBarBar = new JToolBar();
			jJToolBarBar.setFloatable(false);
			jJToolBarBar.add(jLabel);
			jJToolBarBar.add(getCbbJDBCDataSource());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJList());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
			jList
					.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

						public void valueChanged(
								javax.swing.event.ListSelectionEvent e) {
							if (e.getValueIsAdjusting() == true) {
								return;
							}
							JDBCView jdbcView = (JDBCView) (((JList) e
									.getSource()).getSelectedValue());
							List<DBToTxtRegion> list = dataExportAction
									.findDBToTxtRegion(new Request(CommonVars
											.getCurrUser()), jdbcView);
							initTable(list);
						}
					});
		}
		return jList;
	}

	/**
	 * This method initializes cbbJDBCDataSource
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbJDBCDataSource() {
		if (cbbJDBCDataSource == null) {
			cbbJDBCDataSource = new JComboBox();
			cbbJDBCDataSource.setPreferredSize(new Dimension(31, 22));
			cbbJDBCDataSource.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						JDBCDataSource jdbcDataSource = (JDBCDataSource) cbbJDBCDataSource
								.getSelectedItem();
						if (jdbcDataSource == null) {
							return;
						}
						initJDBCView(jdbcDataSource);
					}
				}
			});
		}
		return cbbJDBCDataSource;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(JTabbedPane.TOP);
			jTabbedPane.addTab("视图名称", null, getJScrollPane1(), null);
		}
		return jTabbedPane;
	}

	private void initJDBCView(JDBCDataSource jdbcDataSource) {
		List<JDBCView> list = this.dataExportAction.findJDBCView(new Request(
				CommonVars.getCurrUser()), jdbcDataSource);
		this.jList.setListData(list.toArray());
		this.jList.setCellRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
				if (value != null && value instanceof JDBCView) {
					JDBCView d = (JDBCView) value;
					this.setText(d.getName());
				}
				return this;
			}
		});
		if (this.jList.getModel().getSize() > 0) {
			this.jList.setSelectedIndex(0);
		}
	}

	/**
	 * This method initializes jTabbedPane1
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane1() {
		if (jTabbedPane1 == null) {
			jTabbedPane1 = new JTabbedPane();
			jTabbedPane1.addTab("从DB导出到文本", null, getJSplitPane(), "从DB导出到文本");
			jTabbedPane1.addTab("从文本导出到DB", null, getJPanel(), "从文本导出到DB");
		}
		return jTabbedPane1;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setToolTipText("从文本导出到DB");
			jPanel.add(getJJToolBarBar1(), BorderLayout.NORTH);
			jPanel.add(getJScrollPane2(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jJToolBarBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar1() {
		if (jJToolBarBar1 == null) {
			jJToolBarBar1 = new JToolBar();
			jJToolBarBar1.add(getBtnAdd1());
			jJToolBarBar1.add(getBtnEdit1());
			jJToolBarBar1.add(getBtnDelete1());
			jJToolBarBar1.add(getBtnCopy1());
			jJToolBarBar1.add(getBtnClose1());
		}
		return jJToolBarBar1;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTable1());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
	}

	/**
	 * This method initializes btnAdd1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd1() {
		if (btnAdd1 == null) {
			btnAdd1 = new JButton();
			btnAdd1.setText("新增");
			btnAdd1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgTxtToDBRegionEdit dg = new DgTxtToDBRegionEdit();
					dg.setTableModel(tableModel1);
					dg.setDataState(DataState.ADD);
					dg.setVisible(true);
				}
			});
		}
		return btnAdd1;
	}

	/**
	 * This method initializes btnEdit1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit1() {
		if (btnEdit1 == null) {
			btnEdit1 = new JButton();
			btnEdit1.setText("修改");
			btnEdit1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					TxtToDBRegion txtToDBRegion = (TxtToDBRegion) tableModel1
							.getCurrentRow();
					if (txtToDBRegion == null) {
						JOptionPane.showMessageDialog(DgTxtRegion.this,
								"请选择你要修改的资料", "确认", 2);
						return;
					}
					DgTxtToDBRegionEdit dg = new DgTxtToDBRegionEdit();
					dg.setTableModel(tableModel1);
					dg.setDataState(DataState.EDIT);
					dg.setVisible(true);
				}
			});
		}
		return btnEdit1;
	}

	/**
	 * This method initializes btnDelete1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete1() {
		if (btnDelete1 == null) {
			btnDelete1 = new JButton();
			btnDelete1.setText("删除");
			btnDelete1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (JOptionPane.showConfirmDialog(DgTxtRegion.this,
							"确定删除吗?", "提示", 0) == 0) {
						if (tableModel1.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgTxtRegion.this,
									"请选中要删除的资料！", "提示", 2);
							return;
						}
						TxtToDBRegion txtToDBRegion = (TxtToDBRegion) tableModel1
								.getCurrentRow();
						try {
							dataExportAction.deleteTxtToDBRegion(new Request(
									CommonVars.getCurrUser()), txtToDBRegion);
							tableModel1.deleteRow(txtToDBRegion);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(DgTxtRegion.this,
									"已被引用，不能删除！", "提示", 2);
						}
					}

				}
			});
		}
		return btnDelete1;
	}

	/**
	 * This method initializes btnCopy1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCopy1() {
		if (btnCopy1 == null) {
			btnCopy1 = new JButton();
			btnCopy1.setText("转抄");
			btnCopy1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel1.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgTxtRegion.this,
								"请选中要转抄的资料！", "提示", 2);
						return;
					}
					TxtToDBRegion srcTxtToDBRegion = (TxtToDBRegion) tableModel1
							.getCurrentRow();
					TxtToDBRegion newJDBCRegion = dataExportAction
							.copyTxtToDBRegion(new Request(CommonVars
									.getCurrUser()), srcTxtToDBRegion);
					tableModel1.addRow(newJDBCRegion);
				}
			});
		}
		return btnCopy1;
	}

	/**
	 * This method initializes btnClose1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose1() {
		if (btnClose1 == null) {
			btnClose1 = new JButton();
			btnClose1.setText("关闭");
			btnClose1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgTxtRegion.this.dispose();
				}
			});
		}
		return btnClose1;
	}

}
