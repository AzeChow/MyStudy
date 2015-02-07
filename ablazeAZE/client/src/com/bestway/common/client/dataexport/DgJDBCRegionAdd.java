/*
 * Created on 2004-11-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataexport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
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
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.GbkToBig5Flag;
import com.bestway.common.dataexport.action.DataExportAction;
import com.bestway.common.dataexport.entity.JDBCDataSource;
import com.bestway.common.dataexport.entity.JDBCRegion;
import com.bestway.common.dataexport.entity.JDBCView;
import com.bestway.ui.winuicontrol.JDialogBase;


/**
 * @author luosheng 2006/9/1
 * 
 */
public class DgJDBCRegionAdd extends JDialogBase {

	private javax.swing.JPanel	jContentPane			= null;
	private JToolBar			jToolBar				= null;
	private JButton				btnClose				= null;
	private JScrollPane			jScrollPane				= null;
	private JTableListModel		tableModel				= null;
	private DataExportAction	dataExportAction		= null;
	private JSplitPane			jSplitPane				= null;
	private JPanel				pnLeft					= null;
	private JPanel				pnRight					= null;
	private JToolBar			jJToolBarBar			= null;
	private JScrollPane			jScrollPane1			= null;
	private JList				jList					= null;
	private JLabel				jLabel					= null;
	private JComboBox			cbbJDBCDataSource		= null;
	private JTabbedPane			jTabbedPane				= null;
	private JButton				btnSave					= null;
	private JLabel				jLabel1					= null;
	private JComboBox			cbbDestJDBCDataSource	= null;
	private JPanel				jPanel					= null;
	private JTree				jTree					= null;
	private JLabel				jLabel2					= null;
	private JTextField			tfName					= null;
	private JDBCView			jdbcView				= null;
	private String				destTableName			= null;

	/**
	 * This is the default constructor
	 */
	public DgJDBCRegionAdd() {
		super();
		dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");
		initialize();
		initUIComponents();
	}

	private void initUIComponents() {
		initCbbJDBCDataSource();
	}

	private void initCbbJDBCDataSource() {
		List<JDBCDataSource> list = this.dataExportAction
				.findJDBCDataSource(new Request(CommonVars.getCurrUser()));
		DefaultComboBoxModel model = new DefaultComboBoxModel(list.toArray());
		DefaultListCellRenderer renderer = new DefaultListCellRenderer() {
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
		};

		this.cbbJDBCDataSource.setModel(model);
		this.cbbJDBCDataSource.setRenderer(renderer);
		this.cbbJDBCDataSource.setSelectedItem(null);

		this.cbbDestJDBCDataSource.setModel(new DefaultComboBoxModel(list
				.toArray()));
		this.cbbDestJDBCDataSource.setRenderer(renderer);
		this.cbbDestJDBCDataSource.setSelectedItem(null);
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			showData();
		}
		super.setVisible(b);
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
			jLabel1 = new JLabel();
			jLabel1.setText("目的数据源");
			jLabel1.setBounds(new Rectangle(1, 6, 60, 18));
			jToolBar = new JToolBar();
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnClose());
			jToolBar.add(getJPanel());
		}
		return jToolBar;
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

					DgJDBCRegionAdd.this.dispose();
				}
			});

		}
		return btnClose;
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
			jScrollPane.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
					"\u76ee\u7684\u8868", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			jScrollPane.setViewportView(getJTree());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("新增域设置");
		this.setSize(733, 541);
		this.setContentPane(getJContentPane());
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
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
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
			jSplitPane.setLeftComponent(getPnLeft());
			jSplitPane.setRightComponent(getPnRight());
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setDividerLocation(225);
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
			jLabel.setText("视图数据源");
			jLabel.setSize(new Dimension(38, 18));
			jLabel.setPreferredSize(new Dimension(65, 18));
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
			cbbJDBCDataSource.setPreferredSize(new Dimension(31, 25));
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
			jTabbedPane.setTabPlacement(SwingConstants.TOP);
			jTabbedPane.addTab("视图名称", null, getJScrollPane1(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes btnCopy
	 * 
	 * @return javax.swing.JButton
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
					DgJDBCRegionAdd.this.dispose();
				}
			});
		}
		return btnSave;
	}

	private void initJDBCView(JDBCDataSource jdbcDataSource) {
		List<JDBCView> list = this.dataExportAction.findJDBCView(new Request(
				CommonVars.getCurrUser()), jdbcDataSource);
		this.jList.setListData(list.toArray());
		this.jList.setCellRenderer(new DefaultListCellRenderer() {
			@Override
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
	 * This method initializes cbbDestJDBCDataSource
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDestJDBCDataSource() {
		if (cbbDestJDBCDataSource == null) {
			cbbDestJDBCDataSource = new JComboBox();
			cbbDestJDBCDataSource.setPreferredSize(new Dimension(31, 25));
			cbbDestJDBCDataSource.setBounds(new Rectangle(63, 2, 158, 26));
			cbbDestJDBCDataSource.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						JDBCDataSource jdbcDataSource = (JDBCDataSource) cbbDestJDBCDataSource
								.getSelectedItem();
						if (jdbcDataSource == null) {
							return;
						}
						List<String> tableNames = dataExportAction
								.getTableNames(new Request(CommonVars
										.getCurrUser()), jdbcDataSource);
						initJTreeSql(tableNames);
					}
				}
			});
		}
		return cbbDestJDBCDataSource;
	}

	/**
	 * 初始化事
	 * 
	 */
	private void initJTreeSql(List<String> tableNames) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("数据库");
		for (String tableName : tableNames) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(tableName);
			root.add(node);
		}
		DefaultTreeModel model = new DefaultTreeModel(root);
		jTree.setModel(model);
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(226, 6, 38, 18));
			jLabel2.setText("域名称");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbDestJDBCDataSource(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getTfName(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTree
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getJTree() {
		if (jTree == null) {
			jTree = new JTree();
			jTree.addMouseListener(new java.awt.event.MouseAdapter() {

				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					JTree tree = (JTree) e.getSource();
					tree.setCursor(new Cursor(Cursor.WAIT_CURSOR));
					if (tree.getRowForLocation(e.getX(), e.getY()) == -1) {
						tree.setCursor(new Cursor(Cursor.HAND_CURSOR));
						return;
					}
					TreePath selPath = tree.getPathForLocation(e.getX(), e
							.getY());
					try {
						if (e.getClickCount() == 1) {
							createNode(selPath);
						}
					} finally {
						tree.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}
				}
			});
		}
		return jTree;
	}

	/**
	 * 创建子节点
	 * 
	 * @param selPath
	 */
	private void createNode(TreePath selPath) {

		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) selPath
				.getLastPathComponent();
		Object root = jTree.getModel().getRoot();

		if (!(root.equals(treeNode.getParent()))) {
			return;
		}
		if (treeNode.getChildCount() > 0) {
			return;
		}
		JDBCDataSource jdbcDataSource = (JDBCDataSource) cbbDestJDBCDataSource
				.getSelectedItem();
		if (jdbcDataSource == null) {
			return;
		}

		String tableName = (String) treeNode.getUserObject();
		destTableName = tableName;
		try {
			List<String> columnNames = this.dataExportAction.getColumnNames(
					new Request(CommonVars.getCurrUser()), jdbcDataSource,
					tableName);

			for (String column : columnNames) {
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(column);
				treeNode.add(node);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This method initializes tfName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(new Rectangle(265, 2, 129, 26));
		}
		return tfName;
	}

	public JDBCView getJdbcView() {
		return jdbcView;
	}

	public void setJdbcView(JDBCView jdbcView) {
		this.jdbcView = jdbcView;
	}

	/** 验证数据 */
	private boolean validateData() {

		JDBCDataSource destJDBCDataSource = (JDBCDataSource) this.cbbDestJDBCDataSource
				.getSelectedItem();
		if (destJDBCDataSource == null) {
			JOptionPane.showMessageDialog(this, "目的数据源不能为空！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (this.tfName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "域名称不可为空！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		TreePath selPath = this.jTree.getSelectionPath();
		if (selPath == null) {
			JOptionPane.showMessageDialog(this, "请选择目的表名！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) selPath
				.getLastPathComponent();
		Object root = jTree.getModel().getRoot();
		if (!(root.equals(treeNode.getParent()))) {
			JOptionPane.showMessageDialog(this, "请选择目的表名！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		return true;
	}

	/** 保存数据 */
	private void saveData() {
		JDBCRegion jdbcRegion = new JDBCRegion();
		fillData(jdbcRegion);
		jdbcRegion = this.dataExportAction.saveJDBCRegion(new Request(
				CommonVars.getCurrUser()), jdbcRegion);
		this.tableModel.addRow(jdbcRegion);
	}

	/** fill data */
	private void fillData(JDBCRegion jdbcRegion) {
		jdbcRegion.setCompany(CommonVars.getCurrUser().getCompany());
		jdbcRegion.setRegionName(tfName.getText());
		JDBCDataSource destJDBCDataSource = (JDBCDataSource) this.cbbDestJDBCDataSource
				.getSelectedItem();
		jdbcRegion.setDestJDBCDataSource(destJDBCDataSource);
		JDBCView jdbcView = (JDBCView) (jList.getSelectedValue());
		jdbcRegion.setSrcJDBCView(jdbcView);
		jdbcRegion.setGbkToBig5Flag(GbkToBig5Flag.NO);
		jdbcRegion.setDestTableName(destTableName);
	}

	private void showData() {
		if (jdbcView != null) {
			JDBCDataSource srcJDBCDataSource = jdbcView.getJdbcDataSource();
			//
			// 这里会执行事件
			//
			this.cbbJDBCDataSource.setSelectedItem(srcJDBCDataSource);
			//
			// 执行事件后 ,选中视图名
			//
			this.jList.setSelectedValue(jdbcView, true);

			ComboBoxModel dataModel = this.cbbDestJDBCDataSource.getModel();
			if (dataModel.getSize() == 1) {
				this.cbbDestJDBCDataSource.setSelectedIndex(0);
			} else if (dataModel.getSize() > 1) {
				for (int i = 0; i < dataModel.getSize(); i++) {
					JDBCDataSource destJDBCDataSource = (JDBCDataSource) dataModel
							.getElementAt(i);
					if (!destJDBCDataSource.getId().equals(
							srcJDBCDataSource.getId())) {
						this.cbbDestJDBCDataSource
								.setSelectedItem(destJDBCDataSource);
						break;
					}
				}
			}
		}
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

}
