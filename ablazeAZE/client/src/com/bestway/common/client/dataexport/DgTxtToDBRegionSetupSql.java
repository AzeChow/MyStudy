package com.bestway.common.client.dataexport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableJDBCModel;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.Request;
import com.bestway.common.dataexport.action.DataExportAction;
import com.bestway.common.dataexport.entity.JDBCDataSource;
import com.bestway.common.dataexport.entity.TempJDBCColumn;
import com.bestway.common.dataexport.entity.TxtToDBRegion;
import com.bestway.common.dataexport.entity.TxtToDBRegionSetup;
import com.bestway.common.tools.entity.TempResultSet;
import com.bestway.ui.editor.HSQLTextPane;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author luosheng 2006/9/1
 * 
 */

public class DgTxtToDBRegionSetupSql extends JDialogBase {

	private static final long	serialVersionUID	= 1L;
	private javax.swing.JPanel	jContentPane		= null;
	private JSplitPane			jSplitPane			= null;
	private JPanel				jPanel				= null;
	private JToolBar			jToolBar			= null;
	private JSplitPane			jSplitPane1			= null;
	private JScrollPane			jScrollPane1		= null;
	private JScrollPane			jScrollPane2		= null;
	private JTable				jTable				= null;
	private JPanel				jPanel1				= null;
	private HSQLTextPane		hsqlTextPane		= null;
	private JButton				btnClose			= null;
	private JTabbedPane			jTabbedPane			= null;
	private JScrollPane			jScrollPane			= null;
	private HSQLTextPane		logTextPane			= null;
	private JPanel				jPanel2				= null;
	private JToolBar			toolBarSearch		= null;
	private JTextField			tfSearch			= null;
	private JButton				btnExecuteBySql		= null;
	private JTabbedPane			jTabbedPane1		= null;
	private JScrollPane			jScrollPane4		= null;
	private JTree				jTreeSql			= null;
	private static int			IS_QUERY			= 0;
	private static int			IS_UPDATE			= 1;
	private static int			IS_DDL				= 2;
	private DataExportAction	dataExportAction	= null;
	private JLabel				jLabel				= null;
	private JComboBox			cbbJDBCDataSource	= null;
	private JPanel				pnTool				= null;
	private int					dataState			= DataState.BROWSE;
	private JButton				btnSave				= null;
	private List				srcDataSource		= new ArrayList();
	private JLabel				jLabel1				= null;
	private JSplitPane			jSplitPane2			= null;
	private JScrollPane			jScrollPane5		= null;
	private JTable				tbSrc				= null;
	private String				destFieldInfo		= null;
	private TxtToDBRegionSetup	txtToDBRegionSetup	= null;
	private JCheckBox			cbIsCache			= null;
	private JPanel				jPanel5				= null;
	private JToolBar			jToolBar1			= null;
	private boolean				isOk				= false;
	private TxtToDBRegion		txtToDBRegion		= null;

	/**
	 * This is the default constructor
	 */
	public DgTxtToDBRegionSetupSql() {
		super();
		dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("自定义SQL转换");
		this.setSize(775, 544);
		this.setContentPane(getJContentPane());
		this.setContentPane(getJContentPane());
		this.initUIComponents();
	}

	/**
	 * init Data to UI
	 * 
	 */
	private void initUIComponents() {
		initCbbJDBCDataSource();
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
		this.cbbJDBCDataSource.setSelectedItem(null);
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			showData();
			if (dataState == DataState.EDIT) {

			}
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setRightComponent(getJPanel());
			jSplitPane.setDividerLocation(155);
			jSplitPane.setLeftComponent(getJPanel2());
			jSplitPane.setDividerSize(10);
		}
		return jSplitPane;
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
			jPanel.add(getJSplitPane1(), java.awt.BorderLayout.CENTER);
			jPanel.add(getJPanel5(), BorderLayout.NORTH);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jLabel = new JLabel();
			jLabel.setText("数据源");
			jLabel.setBounds(new Rectangle(5, 3, 36, 24));
			jToolBar = new JToolBar();
			jToolBar.add(getBtnExecuteBySql());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnClose());
			jToolBar.add(getPnTool());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setOneTouchExpandable(true);
			jSplitPane1.setDividerLocation(205);
			jSplitPane1.setTopComponent(getJSplitPane2());
			jSplitPane1.setBottomComponent(getJPanel1());
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getHsqlTextPane());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setToolTipText("查找结果集");
			jScrollPane2.setViewportView(getJTable());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
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
	 * This method initializes hsqlTextPane
	 * 
	 * @return com.bestway.ui.editor.HSQLTextPane
	 */
	private HSQLTextPane getHsqlTextPane() {
		if (hsqlTextPane == null) {
			hsqlTextPane = new HSQLTextPane();
			hsqlTextPane.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_F6) {
						execSql();
					}
				}
			});
		}
		return hsqlTextPane;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
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
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(SwingConstants.BOTTOM);
			jTabbedPane.addTab("日志信息", null, getJScrollPane(), "日志信息");
			jTabbedPane.addTab("查找结果集", null, getJScrollPane2(), "查找结果集");
			jTabbedPane.addTab("SQL 转换语句实例", null, getJScrollPane6(),
					"SQL 转换语句实例");
			jTabbedPane.setSelectedIndex(2);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setToolTipText("日志信息");
			jScrollPane.setViewportView(getLogTextPane());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes logTextPane
	 * 
	 * @return com.bestway.ui.editor.HSQLTextPane
	 */
	private HSQLTextPane getLogTextPane() {
		if (logTextPane == null) {
			logTextPane = new HSQLTextPane();
		}
		return logTextPane;
	}

	/**
	 * This method initializes logTextPane
	 * 
	 * @return com.bestway.ui.editor.HSQLTextPane
	 */
	private HSQLTextPane	exampleTextPane		= null;
	private JScrollPane		jScrollPane6		= null;
	private JCheckBox		cbIsBig5DataBase	= null;

	private HSQLTextPane getExampleTextPane() {
		if (exampleTextPane == null) {
			exampleTextPane = new HSQLTextPane();
			exampleTextPane
					.setText("--@type 代表的是视图中的 type 字段名 "
							+ "\n--@name 代表的是视图中的 name 字段名"
							+ "\n/*\n* 请注意参数名称一定要与视图列名一定要相同,并区分大小写\n*/"
							+ "\nselect a.amount from table a where a.type = @type and a.name = @name");
		}
		return exampleTextPane;
	}

	private void initSqlTable(final TempResultSet rs) {
		new JTableJDBCModel(jTable, rs.getColumnNames(), rs.getRows());
	}

	/**
	 * 执行Sql
	 * 
	 */
	private void execSql() {
		JDBCDataSource jdbcDataSource = (JDBCDataSource) cbbJDBCDataSource
				.getSelectedItem();
		if (jdbcDataSource == null) {
			return;
		}
		new ExecSql(jdbcDataSource).start();
	}

	class ExecSql extends Thread {
		JDBCDataSource	jdbcDataSource	= null;

		public ExecSql(JDBCDataSource jdbcDataSource) {
			this.jdbcDataSource = jdbcDataSource;
		}

		@Override
		public void run() {

			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();

			CommonProgress.showProgressDialog(flag, FmMain.getInstance(),
					false, null, 0);
			CommonProgress.setMessage(flag, "正在执行 SQL 语句, 请稍后...");

			btnExecuteBySql.setEnabled(false);
			this.exec();
			btnExecuteBySql.setEnabled(true);

			CommonProgress.closeProgressDialog(flag);

		}

		private void exec() {
			String sql = hsqlTextPane.getSelectedText() == null ? hsqlTextPane
					.getText() : hsqlTextPane.getSelectedText();
			if (sql.trim().equals("")) {
				return;
			}
			if (isQuery(sql) == IS_QUERY) {
				sql = dataExportAction.getTestSql(new Request(CommonVars
						.getCurrUser()), sql, "1=1");
				executeQuery(sql);
			} else {
				logTextPane.setText("不是有效的查询语句!!");
				jTabbedPane.setSelectedIndex(0);
			}
		}

		private void executeQuery(String sql) {
			TempResultSet rs = new TempResultSet();
			try {
				long beginTime = System.currentTimeMillis();
				rs = dataExportAction.getTempResultSet(new Request(CommonVars
						.getCurrUser()), jdbcDataSource, sql);
				long endTime = System.currentTimeMillis();
				logTextPane.setText("总记录数 : " + rs.getRows().size()
						+ " 条 \ntotal time: " + (endTime - beginTime) + " 毫秒");

				jTabbedPane.setSelectedIndex(1);
			} catch (Exception ex) {
				logTextPane.setText(ex.getMessage());
				jTabbedPane.setSelectedIndex(0);
			}
			initSqlTable(rs);
		}

	}

	/**
	 * 是否是查找
	 * 
	 * @param hsql
	 * @return
	 */
	private static int isQuery(String hsql) {
		int isQuery = IS_DDL;
		String tempHsql = hsql.toLowerCase().trim();
		if (tempHsql.startsWith("select")) {
			isQuery = IS_QUERY;
		} else if (tempHsql.startsWith("update")
				|| tempHsql.startsWith("insert")
				|| tempHsql.startsWith("delete")) {
			isQuery = IS_UPDATE;
		}
		return isQuery;
	}

	/**
	 * 创建子节点
	 * 
	 * @param selPath
	 */
	private void createNode(TreePath selPath) {

		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) selPath
				.getLastPathComponent();
		Object root = jTreeSql.getModel().getRoot();

		if (!(root.equals(treeNode.getParent()))) {
			return;
		}
		if (treeNode.getChildCount() > 0) {
			return;
		}
		JDBCDataSource jdbcDataSource = (JDBCDataSource) cbbJDBCDataSource
				.getSelectedItem();
		if (jdbcDataSource == null) {
			return;
		}

		String tableName = (String) treeNode.getUserObject();

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
		jTreeSql.setModel(model);
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getToolBarSearch(), java.awt.BorderLayout.NORTH);
			jPanel2.add(getJTabbedPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	public String getDestFieldInfo() {
		return destFieldInfo;
	}

	public void setDestFieldInfo(String destField) {
		this.destFieldInfo = destField;
	}

	public TxtToDBRegionSetup getTxtToDBRegionSetup() {
		return txtToDBRegionSetup;
	}

	public void setTxtToDBRegionSetup(TxtToDBRegionSetup jdbcRegionSetup) {
		this.txtToDBRegionSetup = jdbcRegionSetup;
	}

	/**
	 * This method initializes cbIsCache
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsCache() {
		if (cbIsCache == null) {
			cbIsCache = new JCheckBox();
			cbIsCache.setText("导入时是否缓存该SQL的结果值,再进行导入,选中将提高导入效率");
			cbIsCache.setPreferredSize(new Dimension(188, 22));
		}
		return cbIsCache;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.add(getJToolBar(), BorderLayout.NORTH);
			jPanel5.add(getJToolBar1(), BorderLayout.CENTER);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.setFloatable(true);
			jToolBar1.add(getCbIsCache());
			jToolBar1.add(getCbIsBig5DataBase());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jScrollPane6
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane6() {
		if (jScrollPane6 == null) {
			jScrollPane6 = new JScrollPane();
			jScrollPane6.setToolTipText("SQL 转换语句实例");
			jScrollPane6.setViewportView(getExampleTextPane());
		}
		return jScrollPane6;
	}

	/**
	 * This method initializes toolBarSearch
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getToolBarSearch() {
		if (toolBarSearch == null) {
			toolBarSearch = new JToolBar();
			toolBarSearch.setFloatable(false);
			toolBarSearch.add(getTfSearch());
		}
		return toolBarSearch;
	}

	/**
	 * This method initializes tfSearch
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSearch() {
		if (tfSearch == null) {
			tfSearch = new JTextField();
			tfSearch.setText("查找...");
			tfSearch.addCaretListener(new CaretListener() {
				public void caretUpdate(CaretEvent e) {
					JTextField tf = (JTextField) e.getSource();
					String searchText = tf.getText();
					if (searchText.equals("查找...")) {
						return;
					}
					searchNode(jTreeSql, searchText);
				}

			});
			tfSearch.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					JTextField tf = (JTextField) e.getSource();
					tf.selectAll();
				}

				@Override
				public void focusLost(FocusEvent e) {
					JTextField tf = (JTextField) e.getSource();
					if (tf.getText().trim().equals("")) {
						tf.setText("查找...");
					}
				}
			});
		}
		return tfSearch;
	}

	/**
	 * 查找节点
	 * 
	 * @param searchText
	 */
	private void searchNode(JTree jTree, String searchText) {
		DefaultTreeModel model = (DefaultTreeModel) jTree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		int count = root.getChildCount();
		boolean isMatch = false;
		for (int i = 0; i < count; i++) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) root
					.getChildAt(i);
			Object object = node.getUserObject();
			if (object instanceof String) {
				String temp = (String) object;
				String name = temp.toLowerCase();
				if (name.startsWith(searchText.toLowerCase())) {
					TreePath path = getPath(node);
					jTree.scrollPathToVisible(path);
					jTree.setSelectionPath(path);
					isMatch = true;
					break;
				}
			}
		}
		if (isMatch == false) {
			TreePath path = new TreePath(root);
			jTree.scrollPathToVisible(path);
			jTree.setSelectionPath(path);
		}
	}

	/**
	 * 获得树的路径
	 * 
	 * @param node
	 * @return
	 */
	public TreePath getPath(TreeNode node) {
		List<TreeNode> list = new ArrayList<TreeNode>();
		// Add all nodes to list
		while (node != null) {
			list.add(node);
			node = node.getParent();
		}
		Collections.reverse(list);
		// Convert array of nodes to TreePath
		return new TreePath(list.toArray());
	}

	/**
	 * This method initializes btnExecuteBySql
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExecuteBySql() {
		if (btnExecuteBySql == null) {
			btnExecuteBySql = new JButton();
			btnExecuteBySql.setText("验证 SQL");
			btnExecuteBySql.setToolTipText("执行 SQL (F6)");
			btnExecuteBySql
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {
							execSql();
						}

					});
		}
		return btnExecuteBySql;
	}

	/**
	 * This method initializes jTabbedPane1
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane1() {
		if (jTabbedPane1 == null) {
			jTabbedPane1 = new JTabbedPane();
			jTabbedPane1.setTabPlacement(SwingConstants.BOTTOM);
			jTabbedPane1.addTab("表名", null, getJScrollPane4(), "表名");
		}
		return jTabbedPane1;
	}

	/**
	 * This method initializes jSplitPane2
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane2() {
		if (jSplitPane2 == null) {
			jSplitPane2 = new JSplitPane();
			jSplitPane2.setOneTouchExpandable(true);
			jSplitPane2.setDividerLocation(425);
			jSplitPane2.setLeftComponent(getJScrollPane1());
			jSplitPane2.setRightComponent(getJScrollPane5());
			jSplitPane2.setDividerSize(8);
		}
		return jSplitPane2;
	}

	/**
	 * This method initializes jScrollPane5
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane5() {
		if (jScrollPane5 == null) {
			jScrollPane5 = new JScrollPane();
			jScrollPane5.setViewportView(getTbSrc());
		}
		return jScrollPane5;
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

	public List getSrcDataSource() {
		return srcDataSource;
	}

	public void setSrcDataSource(List srcDataSource) {
		this.srcDataSource = srcDataSource;
	}

	/**
	 * This method initializes jScrollPane4
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setToolTipText("表名");
			jScrollPane4.setViewportView(getJTreeSql());
		}
		return jScrollPane4;
	}

	/**
	 * This method initializes jTreeSql
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getJTreeSql() {
		if (jTreeSql == null) {
			jTreeSql = new JTree();
			jTreeSql.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jTreeSql.addMouseListener(new java.awt.event.MouseAdapter() {

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
		return jTreeSql;
	}

	/**
	 * This method initializes cbbJDBCDataSource
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbJDBCDataSource() {
		if (cbbJDBCDataSource == null) {
			cbbJDBCDataSource = new JComboBox();
			cbbJDBCDataSource.setBounds(new Rectangle(44, 3, 152, 24));
			cbbJDBCDataSource.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						JDBCDataSource jdbcDataSource = (JDBCDataSource) cbbJDBCDataSource
								.getSelectedItem();
						List<String> tableNames = dataExportAction
								.getTableNames(new Request(CommonVars
										.getCurrUser()), jdbcDataSource);
						initJTreeSql(tableNames);
					}
				}
			});
		}
		return cbbJDBCDataSource;
	}

	/**
	 * This method initializes pnTool
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTool() {
		if (pnTool == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(200, 3, 245, 24));
			jLabel1.setForeground(new java.awt.Color(251, 131, 15));
			jLabel1.setText("目标字段:");
			pnTool = new JPanel();
			pnTool.setLayout(null);
			pnTool.add(jLabel, null);
			pnTool.add(getCbbJDBCDataSource(), null);
			pnTool.add(jLabel1, null);
		}
		return pnTool;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This method initializes btnSave
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
					isOk = true;
					dispose();
				}
			});
		}
		return btnSave;
	}

	/** 保存数据 */
	private void saveData() {
		if (dataState == DataState.ADD) {
			fillData(txtToDBRegionSetup);

		} else if (dataState == DataState.EDIT) {
			fillData(txtToDBRegionSetup);
		}
	}

	/** fill data */
	private void fillData(TxtToDBRegionSetup txtToDBRegionSetup) {
		txtToDBRegionSetup.setSqlStr(this.hsqlTextPane.getText());
		JDBCDataSource jdbcDataSource = (JDBCDataSource) cbbJDBCDataSource
				.getSelectedItem();
		txtToDBRegionSetup.setParaJDBCDataSource(jdbcDataSource);
		txtToDBRegionSetup.setIsCache(cbIsCache.isSelected());
		txtToDBRegionSetup.setIsBig5DataBase(cbIsBig5DataBase.isSelected());
	}

	/** show data */
	private void showData() {
		this.hsqlTextPane
				.setText(this.txtToDBRegionSetup.getSqlStr() == null ? ""
						: this.txtToDBRegionSetup.getSqlStr());
		jLabel1.setText("目标字段:" + destFieldInfo);
		if (txtToDBRegionSetup.getParaJDBCDataSource() == null) {
			this.cbbJDBCDataSource.setSelectedItem(txtToDBRegion
					.getDestJDBCDataSource());
		} else {
			this.cbbJDBCDataSource.setSelectedItem(txtToDBRegionSetup
					.getParaJDBCDataSource());
		}
		this.cbIsCache
				.setSelected(txtToDBRegionSetup.getIsCache() == null ? false
						: txtToDBRegionSetup.getIsCache());
		//
		// 源表字段名
		//
		initSourceFields();
		setState();
	}

	private boolean validateData() {
		String sql = this.hsqlTextPane.getText();
		if (sql.trim().equals("")) {
			JOptionPane.showMessageDialog(DgTxtToDBRegionSetupSql.this,
					"视图 SQL 语句不可为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (isQuery(sql) != IS_QUERY) {
			JOptionPane.showMessageDialog(DgTxtToDBRegionSetupSql.this,
					"SQL 语句不是有效的查询语句！", "提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		JDBCDataSource jdbcDataSource = (JDBCDataSource) cbbJDBCDataSource
				.getSelectedItem();
		List<TempJDBCColumn> list = this.dataExportAction.getSrcTempJDBCColumn(
				new Request(CommonVars.getCurrUser()), jdbcDataSource, sql);
		if (list.size() != 1) {
			JOptionPane.showMessageDialog(DgTxtToDBRegionSetupSql.this,
					"SQL 返回列大于 1 ,这是不允许的 ！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}

	private void initSourceFields() { // 源表字段名
		initTableSource(srcDataSource);
	}

	private void initTableSource(List dataSource) {
		JTableListModel srcTableModel = new JTableListModel(tbSrc, dataSource,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("源表字段名", "fieldName", 132));
						return list;
					}
				});
	}

	private void setState() {
		//
		// 
		//
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * This method initializes cbIsBig5DataBase
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsBig5DataBase() {
		if (cbIsBig5DataBase == null) {
			cbIsBig5DataBase = new JCheckBox();
			cbIsBig5DataBase.setText("参数SQL数据源是否是繁体数据库");
		}
		return cbIsBig5DataBase;
	}

	public void setTxtToDBRegion(TxtToDBRegion txtToDBRegion) {
		this.txtToDBRegion = txtToDBRegion;
	}
}
