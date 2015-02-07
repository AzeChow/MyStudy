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
public class FmBaseCodeCountry extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JTree jTree = null;

	private JPanel panelTop = null;

	private JPanel jPanel3 = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JTableListModel tableModel = null;

	private DefaultTreeModel model;

	private int treeSelectedIndex;

	private int page;

	private int pageCount = 25; //default 50

	// records
	private String searchFiled;

	//	private int searchIndex; //0:不查询，1：查询

	private CustomBaseAction customBaseAction = null;

	private JPanel jPanel4 = null;

	private JComboBox jComboBox = null;

	private JTextField tfQueryValue = null;

	private JButton btnSearch = null;

	private JButton jButton = null;
	
	private JButton jButton1 = null;
	
	private int indexS = 0;
	/**
	 * This is the default constructor
	 */
	public FmBaseCodeCountry() {
		super();
        customBaseAction = (CustomBaseAction) CommonVars
        .getApplicationContext().getBean("customBaseAction");
        customBaseAction.checkBaseCodeCountryAuthority(new Request(CommonVars
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
		this.setTitle("国家地区");
		this.setHelpId("baseCodeCountry");
		this.validate();
		this.pack();
		this.setTreeSelectedIndex(0);		

		//		this.searchIndex = 0; //不查询
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
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("国家地区");
			DefaultMutableTreeNode root_1 = new DefaultMutableTreeNode("国家地区");
			DefaultMutableTreeNode root_2 = new DefaultMutableTreeNode("海关关区");
			DefaultMutableTreeNode root_3 = new DefaultMutableTreeNode(
					"国内进出口口岸");
			DefaultMutableTreeNode root_4 = new DefaultMutableTreeNode(
					"国际进出口港口");
			DefaultMutableTreeNode root_5 = new DefaultMutableTreeNode(
					"国内进出口码头");
			DefaultMutableTreeNode root_6 = new DefaultMutableTreeNode(
			"地区代码");

			root.add(root_1);
			root.add(root_2);
			root.add(root_3);
			root.add(root_4);
			root.add(root_5);
			root.add(root_6);
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
								//								searchIndex = 0;
								getSearchField();
								findData();
								FmBaseCodeCountry.this.tfQueryValue.setText("");
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
		if (panelTop == null) {
			panelTop = new JPanel();
			panelTop.setLayout(new BorderLayout());
			panelTop.add(getJToolBar(), java.awt.BorderLayout.NORTH);
		}
		return panelTop;
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

	public void getSearchField() {
		boolean isTrue = this.jComboBox.getSelectedItem().equals("代码");

		if (isTrue)
			this.searchFiled = "code";
		else
			this.searchFiled = "name";
	}

	private String getQueryField() {
		String queryField = "";
		//	  queryField=this.getJComboBox().getSelectedItem().toString();
		getSearchField();
		queryField = this.searchFiled;
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
		boolean isVisible = false;
		if (treeSelectedIndex == 0) {
			dataSource = customBaseAction.findCountry(getQueryField(),
					getQueryValue()); //国家地区

		} else if (treeSelectedIndex == 1) {
			dataSource = customBaseAction.findCustoms(getQueryField(),
					getQueryValue()); //海关关区

		} else if (treeSelectedIndex == 2) {
			dataSource = customBaseAction.findPortInternal(getQueryField(),
					getQueryValue()); //国内进出口口岸

		} else if (treeSelectedIndex == 3) {
			dataSource = customBaseAction.findPortLin(getQueryField(),
					getQueryValue()); //国际进出口港口
		} else if (treeSelectedIndex == 4) {
			dataSource = customBaseAction.findPreDock(getQueryField(),
					getQueryValue()); //国内进出口码头
		}else if (treeSelectedIndex == 5) {
			dataSource = customBaseAction.findDistrict(getQueryField(),
					getQueryValue()); //地区代码
		}

		initTable(dataSource, treeSelectedIndex);
		//	this.panel_top.setVisible(isVisible);
	}

	/**
	 * 得到
	 */
	public void findData() {
		indexS = treeSelectedIndex;
		CustomBaseList customBase = CustomBaseList.getInstance();
		List dataSource = null;
		boolean isVisible = false;
		if (treeSelectedIndex == 0) {
			dataSource = customBase.getCountrys(); //国家地区

		} else if (treeSelectedIndex == 1) {
			dataSource = customBase.getCustoms(); //海关关区
			isVisible=true;

		} else if (treeSelectedIndex == 2) {
			dataSource = customBase.getPortInternals(); //国内进出口口岸
			isVisible=true;

		} else if (treeSelectedIndex == 3) {
			dataSource = customBase.getPortLins(); //国际进出口港口
			isVisible = true;
		} else if (treeSelectedIndex == 4) {
			dataSource = customBase.getPreDocks(); //国内进出口码头
			/**
			 * @author zyy 添加新增修改功能
			 */
			isVisible=true;
		} else if (treeSelectedIndex == 5) {
			dataSource = customBase.getDistrict(); //地区代码
		}
		this.jButton.setVisible(isVisible);
        this.jButton1.setVisible(isVisible);
		initTable(dataSource, treeSelectedIndex);
		//	this.panel_top.setVisible(isVisible);
	}

	/**
	 * 初始化表格
	 */
	private void initTable(final List list, final int sindex1) {
		if (sindex1 == 0) { //国家地区
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("国家代码", "code", 100));
							list.add(addColumn("英文名称", "countryEnname", 150));
							list.add(addColumn("中文名称", "name", 150));
							list.add(addColumn("检验标记", "countryMark", 100));
							return list;
						}
					});
		} else if (sindex1 == 1) { //海关关区
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("关区代码", "code", 100));
							list.add(addColumn("关区名称", "name", 200));
							//list.add(addColumn("序号", "customsSerial", 100));
							return list;
						}
					});
		} else if (sindex1 == 2) { //国内进出口口岸
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("口岸代码", "code", 100));
							list.add(addColumn("口岸名称", "name", 300));
							return list;
						}
					});
		} else if (sindex1 == 3) { //国际进出口港口
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("港口代码", "code", 100));
							list.add(addColumn("港口英文名称", "portEnname", 200));
							list.add(addColumn("港口中文名称", "name", 200));
							list.add(addColumn("航线", "portLine", 100));
							return list;
						}
					});

		} else if (sindex1 == 4) { //国内进出口码头
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("港口代码", "code", 100));
							list.add(addColumn("港口名称", "name", 220));
							list.add(addColumn("简称（报关 填写规则）", "shortName", 150));
							list.add(addColumn("关区", "cusCode", 100));
							return list;
						}
					});
		} else if (sindex1 == 5) { //地区代码
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("地区代码", "code", 100));
							list.add(addColumn("地区名称", "name", 300));
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
			java.awt.GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			
			java.awt.GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			
			java.awt.GridBagConstraints gridBagConstraints3 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints2 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints1 = new GridBagConstraints();

			jPanel4 = new JPanel();
			jPanel4.setLayout(new GridBagLayout());
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.ipadx = 80;
			gridBagConstraints1.ipady = -3;
			gridBagConstraints1.insets = new java.awt.Insets(3, 9, 3, 3);
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.ipadx = 171;
			gridBagConstraints2.ipady = 2;
			gridBagConstraints2.insets = new java.awt.Insets(3, 3, 3, 7);
			gridBagConstraints3.gridx = 2;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.ipadx = 4;
			gridBagConstraints3.ipady = -4;
			gridBagConstraints3.insets = new java.awt.Insets(3, 7, 3, 1);
			gridBagConstraints4.gridx = 3;
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.ipadx = 4;
			gridBagConstraints4.ipady = -4;
			gridBagConstraints4.insets = new java.awt.Insets(3, 1, 3, 1);
			gridBagConstraints5.gridx = 4;
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.ipadx = 4;
			gridBagConstraints5.ipady = -4;
			gridBagConstraints5.insets = new java.awt.Insets(3, 1, 3, 1);
			jPanel4.add(getJComboBox(), gridBagConstraints1);
			jPanel4.add(getJTextField(), gridBagConstraints2);
			jPanel4.add(getBtnSearch(), gridBagConstraints3);
			jPanel4.add(getJButton1(), gridBagConstraints4);
			jPanel4.add(getJButton(), gridBagConstraints5);
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
			jComboBox.addItem("代码");
			jComboBox.addItem("名称");
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
					//					searchIndex = 1;
//					getSearchField();
//					if (FmBaseCodeCountry.this.getJTextField().getText().trim()
//							.equals("")) {
//						findData();
//					} else {
//						findDataByCondition();
//					}

				}
			});

		}
		return btnSearch;
	}

	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("修改");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				   if(indexS == 1){
				    	DgCustoms dg = new DgCustoms();
				    	dg.setAdd(false);
				    	dg.setTableModel(tableModel);
				    	dg.setVisible(true);
				    }else if(indexS == 2){
				    	DgPortInternal dg = new DgPortInternal();
				    	dg.setAdd(false);
				    	dg.setTableModel(tableModel);
				    	dg.setVisible(true);
				    }else if(indexS == 3){
				    	DgOpenPort dg = new DgOpenPort();
				    	dg.setAdd(false);
				    	dg.setTableModel(tableModel);
				    	dg.setVisible(true);
				    }else if(indexS == 4){
				    	DgPreDock dg = new DgPreDock();
				    	dg.setAdd(false);
				    	dg.setTableModel(tableModel);
				    	dg.setVisible(true);
				    }
				}
			});
		}
		return jButton;
	}
	
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("新增");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(indexS == 1){
						DgCustoms dg = new DgCustoms();
				    	dg.setAdd(true);
				    	dg.setTableModel(tableModel);
				    	dg.setVisible(true);
				    }else if(indexS == 2){
				    	DgPortInternal dg = new DgPortInternal();
				    	dg.setAdd(true);
				    	dg.setTableModel(tableModel);
				    	dg.setVisible(true);
				    }else if(indexS == 3){
				    	DgOpenPort dg = new DgOpenPort();
				    	dg.setAdd(true);
				    	dg.setTableModel(tableModel);
				    	dg.setVisible(true);
				    }else if(indexS == 4){
				    	DgPreDock dg = new DgPreDock();
				    	dg.setAdd(true);
				    	dg.setTableModel(tableModel);
				    	dg.setVisible(true);
				    }
				}
			});
		}
		return jButton1;
	}
	
	/**
	 * @param comboBox
	 *            The jComboBox to set.
	 */
	public void setJComboBox(JComboBox comboBox) {
		jComboBox = comboBox;
	}

	/**
	 * @return Returns the treeSelectedIndex.
	 */
	public int getTreeSelectedIndex() {
		return treeSelectedIndex;
	}

	/**
	 * @param treeSelectedIndex
	 *            The treeSelectedIndex to set.
	 */
	public void setTreeSelectedIndex(int treeSelectedIndex) {
		this.treeSelectedIndex = treeSelectedIndex;
	}
} //  @jve:decl-index=0:visual-constraint="10,46"
