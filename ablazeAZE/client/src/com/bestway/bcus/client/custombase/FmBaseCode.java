/*
 * Created on 2004-6-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.custombase;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
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

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Rectangle;

/**
 * @author bsway
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class FmBaseCode extends JInternalFrameBase
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JSplitPane jSplitPane = null;

	private JPanel panelleft = null;

	private JPanel jPanel1 = null;

	private JTableListModel tableModel = null;

	private JToolBar jToolBar = null;

	private JPanel pnlTop = null;

	private JComboBox cbSearchFields = null;

	private JButton btnSearch = null;

	private JTextField tfQueryValue = null;

	private JTable tbBaseCode = null;

	private JScrollPane jScrollPane = null;

	private JTree trBaseCode = null;

	// private int treeSelectedIndex;

	// records
	private String searchCondition = "";

	private String searchFiled = "";

	private CustomBaseAction customBaseAction = null;

	private JButton btnEdit = null;

	private int indexS = 0;

	private JButton btnAdd = null;

	private JButton btnImport = null;

	private PnCommonQueryPage pnCommonQueryPage = null;

	private List dataSource = null;

	private long count = 0L;
	/**
	 * @return Returns the searchCondition.
	 */
	public String getSearchCondition()
	{
		return searchCondition;
	}

	/**
	 * @param searchCondition
	 *            The searchCondition to set.
	 */
	public void setSearchCondition(String searchCondition)
	{
		this.searchCondition = searchCondition;
	}

	/**
	 * @return Returns the searchFiled.
	 */
	public String getSearchFiled()
	{
		return searchFiled;
	}

	/**
	 * @param searchFiled
	 *            The searchFiled to set.
	 */
	public void setSearchFiled(String searchFiled)
	{
		this.searchFiled = searchFiled;
	}

	/**
	 * This is the default constructor
	 */
	public FmBaseCode()
	{
		super();
		customBaseAction = (CustomBaseAction) CommonVars.getApplicationContext()
				.getBean("customBaseAction");
		customBaseAction
				.checkBaseCodeAuthority(new Request(CommonVars.getCurrUser()));
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setContentPane(getJSplitPane());
		this.setSize(922, 379);
		this.setTitle("基础代码");
		this.setHelpId("baseCode");
		this.validate();
		this.pack();

		findData(0);
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
	private JSplitPane getJSplitPane()
	{
		if (jSplitPane == null)
		{
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getJPanel());
			jSplitPane.setRightComponent(getJPanel1());
			jSplitPane.setDividerLocation(130);
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
	private JPanel getJPanel()
	{
		if (panelleft == null)
		{
			panelleft = new JPanel();
			panelleft.setLayout(new java.awt.BorderLayout());
			panelleft.setBackground(java.awt.Color.WHITE);
			panelleft.add(getTrBaseCode(), java.awt.BorderLayout.CENTER);
		}
		return panelleft;
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
	private JPanel getJPanel1()
	{
		if (jPanel1 == null)
		{
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * 
	 * Bar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar()
	{
		if (jToolBar == null)
		{
			jToolBar = new JToolBar();
			jToolBar.add(getPnlTop());
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnImport());

			jToolBar.add(getPnCommonQueryPage());
			jToolBar.setSize(WIDTH, HEIGHT);

		}
		return jToolBar;
	}

	/**
	 * 
	 * This method initializes pnlTop
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnlTop()
	{
		if (pnlTop == null)
		{
			pnlTop = new JPanel();
			pnlTop.setLayout(null);
			pnlTop.setVisible(true);
			pnlTop.add(getJComboBox(), null);
			pnlTop.add(getJButton2(), null);
			pnlTop.add(getJTextField(), null);
			pnlTop.setVisible(false);
		}
		return pnlTop;
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
	private JComboBox getJComboBox()
	{
		if (cbSearchFields == null)
		{
			cbSearchFields = new JComboBox();
			cbSearchFields.setBounds(new Rectangle(0, 3, 207, 25));
			cbSearchFields.addItem("海关代码");
			cbSearchFields.addItem("公司名称");
			cbSearchFields.addItemListener(new java.awt.event.ItemListener()
			{
				/**
				 * 查询字段combox改变事件
				 */
				public void itemStateChanged(java.awt.event.ItemEvent e)
				{
					JComboBox source = (JComboBox) e.getSource();
					String searchFiled = "";
					switch (source.getSelectedIndex())
					{
					case 0:
						searchFiled = "  code "; // 海关编号
						break;
					case 1:
						searchFiled = " name "; // 企业名称
						break;
					}
					setSearchFiled(searchFiled);
				}
			});

		}
		return cbSearchFields;
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
	private JButton getJButton2()
	{
		if (btnSearch == null)
		{
			btnSearch = new JButton();
			btnSearch.setText("查询");
			btnSearch.setBounds(new java.awt.Rectangle(459, 3, 60, 25));
			btnSearch.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					// if (!tfQueryValue.getText().equals("")) {
					// int col=cbSearchFields.getSelectedIndex()==0?1:2;
					// ((JTableListModel)getJTable().getModel()).filter(col,tfQueryValue.getText());
					// } else {
					// findData(5);
					// }
				}
			});

		}
		return btnSearch;
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
	private JTextField getJTextField()
	{
		if (tfQueryValue == null)
		{
			tfQueryValue = new JTextField();
			tfQueryValue.setBounds(new java.awt.Rectangle(224, 3, 231, 25));
		}
		return tfQueryValue;
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
	private JTable getJTable()
	{
		if (tbBaseCode == null)
		{
			tbBaseCode = new JTable();
		}
		return tbBaseCode;
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
	private JScrollPane getJScrollPane()
	{
		if (jScrollPane == null)
		{
			jScrollPane = new JScrollPane();
			jScrollPane.setBackground(java.awt.Color.WHITE);
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes trBaseCode
	 * 
	 * 
	 * 
	 * @return javax.swing.JTree
	 * 
	 */
	private JTree getTrBaseCode()
	{
		if (trBaseCode == null)
		{
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("基础代码");
			DefaultMutableTreeNode root_1 = new DefaultMutableTreeNode("企业性质");
			DefaultMutableTreeNode root_2 = new DefaultMutableTreeNode("投资类型");
			DefaultMutableTreeNode root_3 = new DefaultMutableTreeNode("投资方式");
			DefaultMutableTreeNode root_4 = new DefaultMutableTreeNode("加工种类");
			DefaultMutableTreeNode root_5 = new DefaultMutableTreeNode("申报/报关方式");
			DefaultMutableTreeNode root_6 = new DefaultMutableTreeNode("海关注册公司");
			DefaultMutableTreeNode root_7 = new DefaultMutableTreeNode("繁简体对照表");
			root.add(root_1);
			root.add(root_2);
			root.add(root_3);
			root.add(root_4);
			root.add(root_5);
			root.add(root_6);
			root.add(root_7);
			trBaseCode = new JTree(root);
			trBaseCode
					.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener()
					{

						public void valueChanged(
								javax.swing.event.TreeSelectionEvent e)
						{

							DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) trBaseCode
									.getLastSelectedPathComponent();
							if (selectedNode == null)
							{
								return;
							}
							DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode
									.getParent();
							if (parent != null)
							{
								int selectedIndex = parent.getIndex(selectedNode);
								findData(selectedIndex);
								FmBaseCode.this.tfQueryValue.setText("");
							}

						}
					});

		}
		return trBaseCode;
	}

	/**
	 * 初始化表格
	 */
	private void initTable(List list, int treeSelectedIndex)
	{
		if (treeSelectedIndex == 0)
		{ // 企业性质
			tableModel = new JTableListModel(tbBaseCode, list,
					new JTableListModelAdapter()
					{
						public List InitColumns()
						{
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});
		}
		else if (treeSelectedIndex == 1)
		{ // 投资类型
			tableModel = new JTableListModel(tbBaseCode, list,
					new JTableListModelAdapter()
					{
						public List InitColumns()
						{
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});
		}
		else if (treeSelectedIndex == 2)
		{ // 投资方式
			tableModel = new JTableListModel(tbBaseCode, list,
					new JTableListModelAdapter()
					{
						public List InitColumns()
						{
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});
		}
		else if (treeSelectedIndex == 3)
		{ // 加工种类
			tableModel = new JTableListModel(tbBaseCode, list,
					new JTableListModelAdapter()
					{
						public List InitColumns()
						{
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});
		}
		else if (treeSelectedIndex == 4)
		{ // 申报/报关方式
			tableModel = new JTableListModel(tbBaseCode, list,
					new JTableListModelAdapter()
					{
						public List InitColumns()
						{
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});
		}
		else if (treeSelectedIndex == 5)
		{ // 海关注册公司
			tableModel = new JTableListModel(tbBaseCode, list,
					new JTableListModelAdapter()
					{
						public List InitColumns()
						{
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});
			
		}
		else if (treeSelectedIndex == 6)
		{ // 简繁体对照表
			tableModel = new JTableListModel(tbBaseCode, list,
					new JTableListModelAdapter()
					{
						public List InitColumns()
						{
							List list = new Vector();
							list.add(addColumn("繁体", "bigname", 100));
							list.add(addColumn("简体", "name", 100));
							return list;
						}
					});
		}

		CommonQuery.getInstance().addCommonFilter(cbSearchFields, tfQueryValue,
				btnSearch, tableModel);
	}

	/**
	 * 得到
	 */
	public void findData(int treeSelectedIndex)
	{
		indexS = treeSelectedIndex;
		CustomBaseList customBase = CustomBaseList.getInstance();
		List dataSource = null;
		boolean showSearchPanel = false;
		boolean showModityButton = false;
		if (treeSelectedIndex == 0)
		{
			dataSource = customBase.getCoTypes();// customBaseAction.findCoType("","");
			// //企业性质

		}
		else if (treeSelectedIndex == 1)
		{
			dataSource = customBase.getInvClasses(); // 投资类型
		}
		else if (treeSelectedIndex == 2)
		{
			dataSource = customBase.getInvestModes(); // 投资方式
		}
		else if (treeSelectedIndex == 3)
		{
			dataSource = customBase.getMachiningTypes(); // 加工种类
		}
		else if (treeSelectedIndex == 4)
		{
			dataSource = customBase.getDModes(); // 申报/报关方式
		}
		else if (treeSelectedIndex == 5)
		{
			// 暂时先不初始化 数据
			//dataSource = customBase.getBriefs(); // 海关注册公司
			getJToolBar().add(getPnCommonQueryPage());
			dataSource = new ArrayList<Object>();
			showModityButton = true;

		}
		else if (treeSelectedIndex == 6)
		{
			dataSource = customBase.getGbtobigs(); // 简繁体对照表
			showModityButton = true;
		}
		
		if (dataSource != null)
		{
			initTable(dataSource, treeSelectedIndex);
		}

		this.pnCommonQueryPage.setVisible(treeSelectedIndex == 5);
		this.pnlTop.setVisible(showSearchPanel);
		this.btnEdit.setVisible(showModityButton);
		this.btnAdd.setVisible(showModityButton);
		this.btnImport.setVisible(treeSelectedIndex == 5);
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit()
	{
		if (btnEdit == null)
		{
			btnEdit = new JButton();
			btnEdit.setText("    修改    ");
			btnEdit.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					if (indexS == 6)
					{
						DgBigToBGK dg = new DgBigToBGK();
						dg.setAdd(false);
						dg.setTableModel(tableModel);
						dg.setVisible(true);
					}
					else if (indexS == 5)
					{
						DgBriefs dg = new DgBriefs();
						dg.setAdd(false);
						dg.setTableModel(tableModel);
						dg.setVisible(true);
					}
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd()
	{
		if (btnAdd == null)
		{
			btnAdd = new JButton();
			btnAdd.setText("    新增    ");
			btnAdd.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					if (indexS == 6)
					{
						DgBigToBGK dg = new DgBigToBGK();
						dg.setAdd(true);
						dg.setTableModel(tableModel);
						dg.setVisible(true);
					}
					else if (indexS == 5)
					{
						DgBriefs dg = new DgBriefs();
						dg.setAdd(true);
						dg.setTableModel(tableModel);
						dg.setVisible(true);
					}
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes btnImport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImport()
	{
		if (btnImport == null)
		{
			btnImport = new JButton();
			btnImport.setText("    导入    ");
			btnImport.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					if (FmBaseCode.this.indexS == 5)
					{
						DgBriefImport briefImport = new DgBriefImport();
						briefImport.setTableModelParent(FmBaseCode.this.tableModel);
						briefImport.setVisible(true);
						findData(5);
					}
				}
			});
		}
		return btnImport;
	}

	
	
	
	/**
	 * 公共查询组件
	 * 
	 * @return
	 */
	private PnCommonQueryPage getPnCommonQueryPage()
	{
		if (pnCommonQueryPage == null)
		{
			pnCommonQueryPage = new PnCommonQueryPage()
			{
				List sourceList =null;
				
				/**
				 * 初始化表格
				 */
				@Override
				public JTableListModel initTable(List dataSource)
				{
					FmBaseCode.this.initTable(sourceList, 5);
					return FmBaseCode.this.tableModel;
				}

				/**
				 * 获得数据源
				 */
				@Override
				public List getDataSource(int index, int length, String property,
						Object value, boolean isLike)
				{
					sourceList = customBaseAction.findPageBriefList(
							new Request(CommonVars.getCurrUser()), index, length, property, value, isLike);
					return sourceList;
				}

				/**
				 * 获得总条数
				 */
				@Override
				public Long getDataSourceCount(int index, int length,
						String property, Object value, boolean isLike)
				{
					count = customBaseAction.findPageBriefCount(property, value, isLike);
					return count; 
				}
			};
			pnCommonQueryPage.setMaximumSize(new Dimension(10,35));
		}
		
		return pnCommonQueryPage;
	}


} // @jve:decl-index=0:visual-constraint="10,10"
