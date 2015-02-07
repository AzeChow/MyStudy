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
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.common.dataexport.action.DataExportAction;
import com.bestway.common.dataexport.entity.JDBCDataSource;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author luosheng 2006/9/1
 * 
 */

public class DgTxtToDBDestTableName extends JDialogBase {

	private javax.swing.JPanel	jContentPane			= null;
	private JToolBar			jToolBar				= null;
	private JButton				btnClose				= null;
	private JScrollPane			jScrollPane				= null;
	private DataExportAction	dataExportAction		= null;
	private JPanel				pnRight					= null;
	private JButton				btnSave					= null;
	private JLabel				jLabel1					= null;
	private JComboBox			cbbDestJDBCDataSource	= null;
	private JPanel				jPanel					= null;
	private JTree				jTree					= null;
	private String				destTableName			= null;
	private JDBCDataSource		destJDBCDataSource		= null;
	private boolean				isOk					= false;

	/**
	 * This is the default constructor
	 */
	public DgTxtToDBDestTableName() {
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

					DgTxtToDBDestTableName.this.dispose();
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
		this.setTitle("选择目的数据表");
		this.setSize(560, 541);
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
			jContentPane.add(getPnRight(), BorderLayout.CENTER);
		}
		return jContentPane;
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
	 * This method initializes btnCopy
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("确定");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!validateData()) {
						return;
					}
					isOk = true;
					DgTxtToDBDestTableName.this.dispose();
				}
			});
		}
		return btnSave;
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
						destJDBCDataSource = jdbcDataSource;
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
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbDestJDBCDataSource(), null);
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

	/** 验证数据 */
	private boolean validateData() {

		JDBCDataSource destJDBCDataSource = (JDBCDataSource) this.cbbDestJDBCDataSource
				.getSelectedItem();
		if (destJDBCDataSource == null) {
			JOptionPane.showMessageDialog(this, "目的数据源不能为空！", "提示",
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

	private void showData() {
		ComboBoxModel dataModel = this.cbbDestJDBCDataSource.getModel();
		if (dataModel.getSize() >= 1) {
			this.cbbDestJDBCDataSource.setSelectedIndex(0);
		}
	}

	public String getDestTableName() {
		return destTableName;
	}

	public JDBCDataSource getDestJDBCDataSource() {
		return destJDBCDataSource;
	}

	public boolean isOk() {
		return isOk;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
