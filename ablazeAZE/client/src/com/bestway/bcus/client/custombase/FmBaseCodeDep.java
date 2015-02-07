/*
 * Created on 2004-6-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.custombase;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmBaseCodeDep extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JTree jTree = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JTableListModel tableModel = null;

	private DefaultTreeModel model;

	private int treeSelectedIndex;

	// records
	private String searchField;

	private int searchvalue; //0:不查询，1：查询

	private CustomBaseAction customBaseAction = null;

	private JPanel jPanel4 = null;

	private JComboBox jComboBox = null;

	private JTextField tfQueryValue = null;

	private JButton btnSearch = null;

	/**
	 * This is the default constructor
	 */
	public FmBaseCodeDep() {
		super();
        customBaseAction = (CustomBaseAction) CommonVars
        .getApplicationContext().getBean("customBaseAction");
        customBaseAction.checkBaseCodeDepAuthority(new Request(CommonVars
                .getCurrUser()));
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJSplitPane());
		this.setSize(575, 379);
		this.setTitle("部门代码");
		this.setHelpId("baseCodeDep");
		this.validate();
		this.pack();
		this.setTreeSelectedIndex(0);		
		this.searchvalue = 0; //不查询
		findData(); //得到第一项
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
			jContentPane.add(getJTree(), java.awt.BorderLayout.CENTER);
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
			this.getJSplitPane().setResizeWeight(0.2);
			jSplitPane.setLeftComponent(getJContentPane());
			jSplitPane.setRightComponent(getJPanel2());
			jSplitPane.setDividerLocation(120);
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
	private JPanel getJPanel2() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel22(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJPanel3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jTree
	 * 
	 * 
	 * 
	 * @return javax.swing.JTree
	 *  
	 */
	private JTree getJTree() {
		if (jTree == null) {
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("部门代码");
			DefaultMutableTreeNode root_1 = new DefaultMutableTreeNode("税务部门");
			DefaultMutableTreeNode root_2 = new DefaultMutableTreeNode("工商行政");
			DefaultMutableTreeNode root_3 = new DefaultMutableTreeNode("技术监督");
			DefaultMutableTreeNode root_4 = new DefaultMutableTreeNode("外经贸部门");	

			root.add(root_1);
			root.add(root_2);
			root.add(root_3);
			root.add(root_4);
			jTree = new JTree(root);
			jTree
					.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {

						public void valueChanged(
								javax.swing.event.TreeSelectionEvent e) {

							DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree
									.getLastSelectedPathComponent();
							if (selectedNode == null) {
								return;
							}
							DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode
									.getParent();
							if (parent != null) {
								int selectedIndex = parent
										.getIndex(selectedNode);
								setTreeSelectedIndex(selectedIndex);
								getSearchField();
								findData();
								FmBaseCodeDep.this.tfQueryValue.setText("");

							}
						}
					});

		}
		return jTree;
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

	private JPanel getJPanel22() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJToolBar(), java.awt.BorderLayout.NORTH);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jPanel3
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 *  
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
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
			jToolBar.setFloatable(false);
			jToolBar.add(getJPanel4());
		}
		return jToolBar;
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
			jScrollPane.setBackground(java.awt.Color.WHITE);
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * @return Returns the iBaseCode.
	 */
	public CustomBaseAction getIBaseCode() {
		return customBaseAction;
	}

	/**
	 * @param baseCode
	 *            The iBaseCode to set.
	 */
	public void setIBaseCode(CustomBaseAction baseCode) {
		customBaseAction = baseCode;
	}

	/**
	 * @return Returns the model.
	 */
	public DefaultTreeModel getModel() {
		return model;
	}

	/**
	 * @param model
	 *            The model to set.
	 */
	public void setModel(DefaultTreeModel model) {
		this.model = model;
	}

	/**
	 * @return Returns the sindex.
	 */
	public int getTreeSelectedIndex() {
		return treeSelectedIndex;
		}

	/**
	 * @param sindex
	 *            The sindex to set.
	 */
	public void setTreeSelectedIndex(int sindex) {
		this.treeSelectedIndex = sindex;
	}

	public void getSearchField() {

		boolean isTrue = this.jComboBox.getSelectedItem().equals("部门代码");

		if (isTrue)
			this.searchField = "code";
		else
			this.searchField = "name";

	}

	private String getQueryField() {
		String queryField = "";
		getSearchField();
		queryField = this.searchField;
		return queryField;
	}

	private String getQueryValue() {
		String queryValue = "";
		queryValue = this.getJTextField().getText().trim();
		return queryValue;
	}

	/**
	 * 得到
	 */
	public void findDataByCondition() {

		List dataSource = null;
		if (treeSelectedIndex == 0) {
			dataSource = customBaseAction.findTaxCode(getQueryField(),
					getQueryValue());//税务代码

		} else if (treeSelectedIndex == 1) {
			dataSource = customBaseAction.findSaicCode(getQueryField(),
					getQueryValue()); //工商行政

		} else if (treeSelectedIndex == 2) {
			dataSource = customBaseAction.findStsCode(getQueryField(),
					getQueryValue());//技术监督

		} else if (treeSelectedIndex == 3) {
			dataSource = customBaseAction.findRedDep(getQueryField(),
					getQueryValue());//外经贸部门
		}
		initTable(dataSource, treeSelectedIndex);
	}

	/**
	 * 得到
	 */
	public void findData() {
		CustomBaseList customBase = CustomBaseList.getInstance();
		List dataSource = null;
		boolean isVisible = false;
		if (treeSelectedIndex == 0) {
			dataSource = customBase.getTaxCodes();//税务代码

		} else if (treeSelectedIndex == 1) {
			dataSource = customBase.getSaicCodes(); //工商行政

		} else if (treeSelectedIndex == 2) {
			dataSource = customBase.getStsCodes();//技术监督

		} else if (treeSelectedIndex == 3) {
			dataSource = customBase.getRedDeps();//外经贸部门
		}

		initTable(dataSource, treeSelectedIndex);

	}

	/**
	 * 初始化表格
	 */
	private void initTable(final List list, final int sindex1) {
		if (sindex1 == 0) { //税务代码
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});
		} else if (sindex1 == 1) { //工商行政
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});
		} else if (sindex1 == 2) { //技术监督
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});
		} else if (sindex1 == 3) { //外经贸部门
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});

		}
		CommonQuery.getInstance().addCommonFilter(jComboBox, tfQueryValue,
				btnSearch, tableModel);
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
	 * 
	 * This method initializes jPanel4
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 *  
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			java.awt.GridBagConstraints gridBagConstraints6 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints5 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints4 = new GridBagConstraints();

			jPanel4 = new JPanel();
			jPanel4.setLayout(new GridBagLayout());
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.ipadx = 121;
			gridBagConstraints4.ipady = -1;
			gridBagConstraints4.insets = new java.awt.Insets(3, 3, 1, 2);
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.ipadx = 166;
			gridBagConstraints5.ipady = 4;
			gridBagConstraints5.insets = new java.awt.Insets(3, 2, 1, 4);
			gridBagConstraints6.gridx = 2;
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.ipadx = 19;
			gridBagConstraints6.ipady = -2;
			gridBagConstraints6.insets = new java.awt.Insets(3, 4, 1, 47);
			jPanel4.add(getJComboBox(), gridBagConstraints4);
			jPanel4.add(getJTextField(), gridBagConstraints5);
			jPanel4.add(getBtnSearch(), gridBagConstraints6);
		}
		return jPanel4;
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
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.addItem("部门代码");
			jComboBox.addItem("部门名称");
		}
		return jComboBox;
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
	private JTextField getJTextField() {
		if (tfQueryValue == null) {
			tfQueryValue = new JTextField();
		}
		return tfQueryValue;
	}

	/**
	 * 
	 * This method initializes btnSearch
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查询");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					//查询

//					if (FmBaseCodeDep.this.tfQueryValue.getText().trim().equals(
//							"")) {
//						findData();
//					} else {
//						findDataByCondition();
//					}

				}
			});

		}
		return btnSearch;
	}

	/**
	 * @param comboBox
	 *            The jComboBox to set.
	 */
	public void setJComboBox(JComboBox comboBox) {
		jComboBox = comboBox;
	}
} //  @jve:decl-index=0:visual-constraint="10,46"
