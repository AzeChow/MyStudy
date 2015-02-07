/*
 * Created on 2004-10-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import java.awt.BorderLayout;
import java.beans.PropertyDescriptor;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.ClassList;
import com.bestway.bcus.dataimport.entity.FieldList;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.common.tools.entity.TempNodeItem;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgFieldCollate extends JDialogBase {

	private javax.swing.JPanel	jContentPane		= null;
	private DataImportAction	dataImportAction	= null;
	private JPanel				jPanel				= null;
	private JSplitPane			jSplitPane			= null;
	private JPanel				jPanel1				= null;
	private JPanel				jPanel2				= null;
	private JToolBar			jToolBar			= null;
	private JButton				jButton				= null;
	private JButton				jButton1			= null;
	private JButton				jButton2			= null;
	private JTable				jTable				= null;
	private JScrollPane			jScrollPane			= null;
	private JToolBar			jToolBar1			= null;
	private JButton				jButton3			= null;
	private JButton				jButton4			= null;
	private JButton				jButton5			= null;
	private JTable				jTable1				= null;
	private JScrollPane			jScrollPane1		= null;
	private ClassList			classList			= null; // @jve:decl-index=0:
	private FieldList			fieldList			= null;
	private JTableListModel		tableModel			= null;

	private JTableListModel		tableModel1			= null;
	private JButton				jButton6			= null;
	private JButton				jButton7			= null;
	private JButton				jButton8			= null;
	private JButton				btnAdd				= null;
	private JButton				btnAddDetail		= null;

	/**
	 * This is the default constructor
	 */
	public DgFieldCollate() {
		super();
		dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("字段对照表");
		this.setSize(758, 521);
		this.setContentPane(getJContentPane());
		initData();
	}

	private void initData() {
		List list = dataImportAction.findClassList(new Request(CommonVars
				.getCurrUser()));
		if (list != null && !list.isEmpty()) {
			initTable(list);
			classList = (ClassList) list.get(0);
			List fieldList = dataImportAction.findFieldList(new Request(
					CommonVars.getCurrUser()), classList);
			if (fieldList != null && !fieldList.isEmpty()) {
				initTableDetail(fieldList);
			} else {
				initTableDetail(new Vector());
			}
		} else {
			initTable(new Vector());
			initTableDetail(new Vector());
		}
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
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	private void initTable(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list
								.add(addColumn("表序号", "sortno", 100,
										Integer.class));
						list.add(addColumn("表中文名称", "name", 150));
						list.add(addColumn("实体类名称", "classPath", 360));
						return list;
					}
				});

	}

	private void initTableDetail(List dataSource) {
		tableModel1 = new JTableListModel(jTable1, dataSource,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list
								.add(addColumn("字段序号", "sortno", 60,
										Integer.class));
						list.add(addColumn("中文名称", "name", 100));
						list.add(addColumn("字段名称", "fieldname", 100));
						list.add(addColumn("字段类型", "fieldtype", 100));
						list.add(addColumn("字段长度", "fieldlen", 100));
						list.add(addColumn("字段说明", "fielddesc", 100));
						list.add(addColumn("是否主键", "iskey", 80));
						list.add(addColumn("是否为空", "isNull", 80));
						return list;
					}
				});
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
			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jLabel.setText("");
			jLabel
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel1.setText("");
			jLabel1.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel2.setText("字段设置对照表");
			jLabel2
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 20));
			jLabel2.setBackground(java.awt.Color.white);
			jLabel2.setForeground(new java.awt.Color(255, 153, 0));
			jPanel.setBackground(java.awt.Color.white);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel.add(jLabel, java.awt.BorderLayout.WEST);
			jPanel.add(jLabel1, java.awt.BorderLayout.EAST);
			jPanel.add(jLabel2, java.awt.BorderLayout.CENTER);
		}
		return jPanel;
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
			jSplitPane.setDividerSize(8);
			jSplitPane.setDividerLocation(200);
			jSplitPane.setTopComponent(getJPanel1());
			jSplitPane.setBottomComponent(getJPanel2());
		}
		return jSplitPane;
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
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
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
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton7());
			jToolBar.add(getJButton8());
			jToolBar.add(getJButton6());
		}
		return jToolBar;
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
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("新增");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgFieldCollateTableAdd dg = new DgFieldCollateTableAdd();
					dg.setTableModel(tableModel);
					dg.setAdd(true);
					dg.setVisible(true);

				}
			});

		}
		return jButton;
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
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("修改");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgFieldCollate.this,
								"请选择你要修改的资料", "确认", 2);
						return;
					}
					DgFieldCollateTableAdd dg = new DgFieldCollateTableAdd();
					dg.setTableModel(tableModel);
					dg.setAdd(false);
					dg.setVisible(true);

				}
			});

		}
		return jButton1;
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("删除");
			jButton2.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgFieldCollate.this,
								"请先选中要删除的资料！", "提示", 2);
						return;
					}
					classList = (ClassList) tableModel.getCurrentRow();
					try {
						dataImportAction.deleteAllField(new Request(CommonVars
								.getCurrUser()), classList);
						initTableDetail(new Vector());
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(DgFieldCollate.this,
								"其对应字段已被引用，不能删除！", "提示", 2);
						return;
					}
					try {
						dataImportAction.deleteClassList(new Request(CommonVars
								.getCurrUser()), classList);
						tableModel.deleteRow(classList);
						tableModel1.getList().clear();
						initTableDetail(new Vector());
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(DgFieldCollate.this,
								"已被引用，不能删除！", "提示", 2);
					}

				}
			});

		}
		return jButton2;
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
		jTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					public void valueChanged(ListSelectionEvent arg0) {
						if (tableModel == null) {
							return;
						}
						if (tableModel.getCurrentRow() == null) {
							return;
						}
						// setState();
						classList = (ClassList) tableModel.getCurrentRow();
						List fieldList = dataImportAction.findFieldList(
								new Request(CommonVars.getCurrUser()),
								classList);
						if (fieldList != null && !fieldList.isEmpty()) {
							initTableDetail(fieldList);
						} else {
							initTableDetail(new Vector());
						}
					}
				});
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
			jToolBar1.add(getBtnAddDetail());
			jToolBar1.add(getJButton3());
			jToolBar1.add(getJButton4());
			jToolBar1.add(getJButton5());
		}
		return jToolBar1;
	}

	/**
	 * 
	 * This method initializes jButton3
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("新增");
			jButton3.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgFieldCollateFieldAdd dg = new DgFieldCollateFieldAdd();
					dg.setTableModel(tableModel1);
					dg.setClassList(classList);
					dg.setAdd(true);
					dg.setVisible(true);

				}
			});

		}
		return jButton3;
	}

	/**
	 * 
	 * This method initializes jButton4
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("修改");
			jButton4.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel1.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgFieldCollate.this,
								"请选择你要修改的资料", "确认", 2);
						return;
					}
					DgFieldCollateFieldAdd dg = new DgFieldCollateFieldAdd();
					dg.setTableModel(tableModel1);
					dg.setClassList(classList);
					dg.setAdd(false);
					dg.setVisible(true);

				}
			});

		}
		return jButton4;
	}

	/**
	 * 
	 * This method initializes jButton5
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("删除");
			jButton5.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModel1.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgFieldCollate.this,
								"请先选中要删除的资料！", "提示", 2);
						return;
					}
					fieldList = (FieldList) tableModel1.getCurrentRow();
					try {
						dataImportAction.deleteFieldList(new Request(CommonVars
								.getCurrUser()), fieldList);
						tableModel1.deleteRow(fieldList);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(DgFieldCollate.this,
								"已被引用，不能删除！", "提示", 2);
					}

				}
			});

		}
		return jButton5;
	}

	/**
	 * 
	 * This method initializes jTable1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
	}

	/**
	 * 
	 * This method initializes jScrollPane1
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
			jScrollPane1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane1;
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
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setText("关闭");
			jButton6.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton6;
	}

	/**
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setText("转抄");
			jButton7.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null) {
						return;
					}
					if (tableModel.getCurrentRow() == null) {
						return;
					}
					classList = (ClassList) tableModel.getCurrentRow();
					classList = dataImportAction.emsEdiChange(new Request(
							CommonVars.getCurrUser()), classList);
					tableModel.addRow(classList);
					List fieldList = dataImportAction.findFieldList(
							new Request(CommonVars.getCurrUser()), classList);
					if (fieldList != null && !fieldList.isEmpty()) {
						initTableDetail(fieldList);
					} else {
						initTableDetail(new Vector());
					}
				}
			});
		}
		return jButton7;
	}

	/**
	 * This method initializes jButton8
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton8() {
		if (jButton8 == null) {
			jButton8 = new JButton();
			jButton8.setText("更新");
			jButton8.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataImportAction.tempChange(new Request(CommonVars
							.getCurrUser()));
					initData();
				}
			});
		}
		return jButton8;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增来自实体类");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addDataFromClassEntity();
				}
			});
		}
		return btnAdd;
	}
	
	private String forClassName(String value) {
		if (value != null) {
			String entityName = value.toString();
			int lastIndex = entityName.lastIndexOf(".");
			if (lastIndex > -1) {
				return entityName.substring(lastIndex + 1);
			}
		}
		return value;
	}

	/** 新增实体 */
	private void addDataFromClassEntity() {
		List<TempNodeItem> dataSource = DataImportCommonQuery.getInstance()
				.getClassEnity();
			if (dataSource != null) {
			int max = dataImportAction.getNumToDriverList(new Request(
					CommonVars.getCurrUser()), "ClassList", "sortno");
			for (TempNodeItem temp : dataSource) {
				ClassList classList = new ClassList();
				classList.setIsCoid(true);
				classList.setClassPath(temp.getClassName());
				classList.setCompany(CommonVars.getCurrUser().getCompany());
				classList.setName(temp.getName());
				classList.setSortno(max);
				classList = dataImportAction.saveClassList(new Request(
						CommonVars.getCurrUser()), classList);
				tableModel.addRow(classList);
				max++;
			}
		}
	}

	/**
	 * This method initializes btnAddDetail
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddDetail() {
		if (btnAddDetail == null) {
			btnAddDetail = new JButton();
			btnAddDetail.setText("新增来自实体");
			btnAddDetail.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addDataDetail();
				}
			});
		}
		return btnAddDetail;
	}

	private void addDataDetail() {
		ClassList classList = (ClassList) tableModel.getCurrentRow();
		if (classList == null) {
			JOptionPane.showMessageDialog(DgFieldCollate.this, "请选择你来自的实体资料",
					"确认", 2);
			return;
		}
		int maxNo = 0;
		List<FieldList> oldDataSource = tableModel1.getList();

		for (int i = 0; i < oldDataSource.size(); i++) {
			FieldList f = oldDataSource.get(i);
			if (f.getSortno() == null) {
				continue;
			}
			int tempMaxNo = f.getSortno();
			if (maxNo < tempMaxNo) {
				maxNo = tempMaxNo;
			}
		}
		List<FieldList> dataSource = DataImportCommonQuery.getInstance()
				.getFieldListByClass(classList);
		if (dataSource != null) {
			for (FieldList f : dataSource) {
				f.setSortno(++ maxNo );
				f = dataImportAction.saveFieldList(new Request(CommonVars
						.getCurrUser()), f);
				tableModel1.addRow(f);
			}
		}
	}
}
