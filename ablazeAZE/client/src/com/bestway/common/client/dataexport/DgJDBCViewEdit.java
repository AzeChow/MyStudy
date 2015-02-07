package com.bestway.common.client.dataexport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableJDBCModel;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.Request;
import com.bestway.common.dataexport.action.DataExportAction;
import com.bestway.common.dataexport.entity.JDBCDataSource;
import com.bestway.common.dataexport.entity.JDBCView;
import com.bestway.common.tools.entity.TempResultSet;
import com.bestway.ui.editor.HSQLTextPane;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author luosheng 2006/9/1
 * 
 */

public class DgJDBCViewEdit extends JDialogBase {

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
	private JButton				btnOpenFile			= null;
	private JButton				btnSaveFile			= null;
	private File				openFile			= null;
	private JButton				btnOtherSave		= null;
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
	private JPanel				pnTool				= null;
	private int					dataState			= DataState.READONLY;
	private JButton				btnSave				= null;
	private JDBCView			jdbcView			= null;
	private JTableListModel		tableModel			= null;
	private JDBCDataSource		jdbcDataSource		= null;
	private JTextField			tfName				= null;

	/**
	 * This is the default constructor
	 */
	public DgJDBCViewEdit() {
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
		this.setTitle("视图修改");
		this.setSize(733, 541);
		this.setContentPane(getJContentPane());
		this.initUIComponents();
	}

	/**
	 * init Data to UI
	 * 
	 */
	private void initUIComponents() {
		setAllKeyListener(getJContentPane());
	}

	/**
	 * 设置所有的控件的属性和事件
	 * 
	 * @param component
	 */
	private void setAllKeyListener(Component component) {
		if (!(component instanceof Container)) {
			return;
		}
		Container container = (Container) component;
		for (int i = 0; i < container.getComponentCount(); i++) {
			Component temp = container.getComponent(i);
			temp.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_S) {
						saveHsqlFile(false);
					}
					if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_O) {
						openHsqlFile();
					}
				}
			});
			setAllKeyListener(temp);
		}
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			if (dataState == DataState.EDIT) {
				jdbcView = (JDBCView) tableModel.getCurrentRow();
				showData(jdbcView);
			}

			if (jdbcDataSource != null) {
				List<String> tableNames = dataExportAction.getTableNames(
						new Request(CommonVars.getCurrUser()), jdbcDataSource);
				initJTreeSql(tableNames);
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
			jPanel.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJSplitPane1(), java.awt.BorderLayout.CENTER);
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
			jLabel.setText("视图名");
			jLabel.setBounds(new Rectangle(5, 4, 36, 23));
			jToolBar = new JToolBar();
			jToolBar.add(getBtnOpenFile());
			jToolBar.add(getBtnSaveFile());
			jToolBar.add(getBtnOtherSave());
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
			jSplitPane1.setTopComponent(getJScrollPane1());
			jSplitPane1.setDividerLocation(150);
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

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * This method initializes tfName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(new Rectangle(45, 4, 159, 23));
		}
		return tfName;
	}

	public void setJdbcDataSource(JDBCDataSource jdbcDataSource) {
		this.jdbcDataSource = jdbcDataSource;
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

	// 调出文件选择框
	private File getHsqlFile() {
		File txtFile = null;
		JFileChooser fileChooser = new JFileChooser("./");
		// fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
		fileChooser.addChoosableFileFilter(new ExampleFileFilter("txt"));
		fileChooser.addChoosableFileFilter(new ExampleFileFilter("sql"));
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		int state = fileChooser.showDialog(DgJDBCViewEdit.this, "选择HSQL文件");
		if (state == JFileChooser.APPROVE_OPTION) {
			txtFile = fileChooser.getSelectedFile();
		}
		return txtFile;
	}

	class ExampleFileFilter extends FileFilter {
		String	suffix	= "";

		ExampleFileFilter(String suffix) {
			this.suffix = suffix;
		}

		@Override
		public boolean accept(File f) {
			String suffix = getSuffix(f);
			if (f.isDirectory() == true) {
				return true;
			}
			if (suffix != null) {
				if (suffix.toLowerCase().equals(this.suffix)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		@Override
		public String getDescription() {
			return "*." + this.suffix;
		}

		private String getSuffix(File f) {
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1).toLowerCase();
			return suffix;
		}
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

	private void initSqlTable(final TempResultSet rs) {
		new JTableJDBCModel(jTable, rs.getColumnNames(), rs.getRows());
	}

	/**
	 * 执行Sql
	 * 
	 */
	private void execSql() {
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
				executeQuery(sql);
			} else if (isQuery(sql) == IS_UPDATE) {
				executeUpdate(sql);
			} else {
				execute(sql);
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

		private void executeUpdate(String sql) {
			try {
				long beginTime = System.currentTimeMillis();
				int delrows = dataExportAction.executeUpdateSql(new Request(
						CommonVars.getCurrUser()), jdbcDataSource, sql);
				long endTime = System.currentTimeMillis();
				logTextPane.setText("受影响的记录数 : " + delrows + " 条  \n"
						+ "total time: " + (endTime - beginTime) + " 毫秒 ");

			} catch (Exception ex) {
				logTextPane.setText(ex.getMessage());
			}
			jTabbedPane.setSelectedIndex(0);
		}

		private void execute(String sql) {
			try {
				long beginTime = System.currentTimeMillis();
				boolean isOk = dataExportAction.executeSql(new Request(
						CommonVars.getCurrUser()), jdbcDataSource, sql);
				long endTime = System.currentTimeMillis();
				logTextPane.setText("执行成功  \n" + "total time: "
						+ (endTime - beginTime) + " 毫秒 ");
			} catch (Exception ex) {
				logTextPane.setText(ex.getMessage());
			}
			jTabbedPane.setSelectedIndex(0);
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
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOpenFile() {
		if (btnOpenFile == null) {
			btnOpenFile = new JButton();
			btnOpenFile.setText("打开");
			btnOpenFile.setToolTipText("打开(Ctrl+O)");
			btnOpenFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					openHsqlFile();
				}
			});
		}
		return btnOpenFile;
	}

	/**
	 * This method initializes btnSaveFile
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSaveFile() {
		if (btnSaveFile == null) {
			btnSaveFile = new JButton();
			btnSaveFile.setText("保存为文件");
			btnSaveFile.setToolTipText("保存(Ctrl+S)");
			btnSaveFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveHsqlFile(false);
				}
			});
		}
		return btnSaveFile;
	};

	/**
	 * 打开HSql文件
	 * 
	 */
	private void openHsqlFile() {
		btnOpenFile.setEnabled(false);

		File hsqlFile = getHsqlFile();
		if (hsqlFile == null) {
			btnOpenFile.setEnabled(true);
			return;
		}
		StringBuffer stringBuffer = new StringBuffer();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(
					hsqlFile), "UTF-8"));
			String s = "";
			while ((s = in.readLine()) != null) {
				stringBuffer.append(s + "\n");
			}
			in.close();
			this.hsqlTextPane.setText(stringBuffer.toString());
			openFile = hsqlFile;
			hsqlTextPane.setCaretPosition(0);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(DgJDBCViewEdit.this, "打开文件有错！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
		btnOpenFile.setEnabled(true);
	}

	/**
	 * 保存Hsql文件
	 * 
	 */
	private void saveHsqlFile(boolean isNew) {
		btnSaveFile.setEnabled(isNew);

		String text = this.hsqlTextPane.getText();
		File saveFile = openFile;
		if (saveFile == null || isNew == true) {
			String fileName = getSaveHsqlFileName();
			if (fileName == null) {
				btnSaveFile.setEnabled(true);
				return;
			}
			saveFile = new File(fileName);
		}
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(saveFile), "UTF-8"));
			String[] lines = text.split("\n");
			for (String line : lines) {
				out.write(line);
				out.newLine();
			}
			out.flush();
			out.close();
			if (openFile == null) {
				openFile = saveFile;
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(DgJDBCViewEdit.this, "保存文件有错！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
		btnSaveFile.setEnabled(true);
	}

	/**
	 * 获得保存的文件名
	 * 
	 * @return
	 */
	private String getSaveHsqlFileName() {
		JFileChooser fileChooser = new JFileChooser("./");
		// fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
		fileChooser.addChoosableFileFilter(new ExampleFileFilter("txt"));
		fileChooser.addChoosableFileFilter(new ExampleFileFilter("sql"));
		String fileName = "";
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		int state = fileChooser.showDialog(DgJDBCViewEdit.this, "保存HSQL文件");
		if (state == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			String description = fileChooser.getFileFilter().getDescription();
			if (f.getPath().indexOf(".") > 0 || description.indexOf(".") == -1) {
				fileName = f.getPath();
			} else {
				String suffix = description.substring(description.indexOf("."));
				fileName = f.getPath() + suffix;
			}
		} else {
			return null;
		}
		return fileName;
	}

	/**
	 * This method initializes btnOtherSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOtherSave() {
		if (btnOtherSave == null) {
			btnOtherSave = new JButton();
			btnOtherSave.setText("另存为文件");
			btnOtherSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveHsqlFile(true);
				}
			});
		}
		return btnOtherSave;
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
	 * This method initializes pnTool
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTool() {
		if (pnTool == null) {
			pnTool = new JPanel();
			pnTool.setLayout(null);
			pnTool.add(jLabel, null);
			pnTool.add(getTfName(), null);
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
					DgJDBCViewEdit.this.dispose();
				}
			});
		}
		return btnSave;
	}

	private boolean validateData() {
		if (this.tfName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(DgJDBCViewEdit.this, "视图名不可为空！",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		String sql = this.hsqlTextPane.getText();
		if (sql.trim().equals("")) {
			JOptionPane.showMessageDialog(DgJDBCViewEdit.this,
					"视图 SQL 语句不可为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if(isQuery(sql) != IS_QUERY){
			JOptionPane.showMessageDialog(DgJDBCViewEdit.this,
					"SQL 语句不是有效的视图！", "提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}

	/** 保存数据 */
	private void saveData() {
		if (dataState == DataState.ADD) {
			JDBCView jdbcView = new JDBCView();
			fillData(jdbcView);
			jdbcView = this.dataExportAction.saveJDBCView(new Request(
					CommonVars.getCurrUser()), jdbcView);
			this.tableModel.addRow(jdbcView);

		} else if (dataState == DataState.EDIT) {
			fillData(jdbcView);
			jdbcView = this.dataExportAction.saveJDBCView(new Request(
					CommonVars.getCurrUser()), jdbcView);
			this.tableModel.updateRow(jdbcView);
		}
	}

	/** fill data */
	private void fillData(JDBCView jdbcView) {
		jdbcView.setCompany(CommonVars.getCurrUser().getCompany());
		jdbcView.setName(tfName.getText());
		jdbcView.setSqlScript(this.hsqlTextPane.getText());
		jdbcView.setJdbcDataSource(jdbcDataSource);
	}

	/** show data */
	private void showData(JDBCView jdbcView) {
		this.hsqlTextPane.setText(jdbcView.getSqlScript());
		this.tfName.setText(jdbcView.getName());
	}

}
