package com.bestway.common.client.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JToolTip;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.commons.beanutils.PropertyUtils;
import org.pf.joi.Inspector;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.JMultiLineToolTip;
import com.bestway.client.common.CommonVariables;
import com.bestway.client.util.JTableJDBCModel;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.Request;
import com.bestway.common.tools.action.CheckToolsAuthorityAction;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.common.tools.entity.TempNodeItem;
import com.bestway.common.tools.entity.TempResultSet;
import com.bestway.ui.editor.HSQLTextPane;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Dimension;

/**
 * @author luosheng 2006/9/1 SQL,HSQL查询工具
 * checked by 陈井彬 2008-12.6
 */

public class FmQueryTool extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JToolBar jToolBar = null;
	private JSplitPane jSplitPane1 = null;
	private JScrollPane jScrollPane1 = null;
	private JScrollPane jScrollPane2 = null;
	/**
	 * 查询结果表格
	 */
	private JTable tableResult = null;
	private JPanel jPanel1 = null;
	/**
	 * 执行HSQL按钮
	 */
	private JButton btnExecuteByHSql = null;
	/**
	 * SQL,HSQL操作DAO
	 */
	private ToolsAction toolsAction = null;
	/**
	 * 授权检查Action
	 */
	private CheckToolsAuthorityAction checkToolsAuthorityAction = null;
	/**
	 * 状态按钮
	 */
	private JButton btnStatus = null;
	/**
	 * 执行文件
	 */
	private JButton btnExecuteFile = null;
	/**
	 * SQL文件
	 */
	private File txtFile = null;
	private HSQLTextPane hsqlTextPane = null;
	/**
	 * 关闭按钮
	 */
	private JButton btnClose = null;
	private JTabbedPane jTabbedPane = null;
	private JScrollPane jScrollPane = null;
	private HSQLTextPane logTextPane = null;
	/**
	 * 打开按钮
	 */
	private JButton btnOpenFile = null;
	/**
	 * 保存按钮
	 */
	private JButton btnSaveFile = null;
	/**
	 * 打开的文件
	 */
	private File openFile = null;
	/**
	 * 另存为按钮
	 */
	private JButton btnOtherSave = null;
	private boolean isChangeText = false;
	/**
	 * HSQL树
	 */
	private JTree jTreeHsql = null;
	private JScrollPane jScrollPane3 = null;
	private JPanel jPanel2 = null;
	private JToolBar toolBarSearch = null;
	private JTextField tfSearch = null;
	/**
	 * 执行SQL按钮
	 */
	private JButton btnExecuteBySql = null;
	private JTabbedPane jTabbedPane1 = null;
	private JScrollPane jScrollPane4 = null;
	/**
	 * 数据库表列表
	 */
	private JTree jTreeSql = null;
	/**
	 * 查询
	 */
	private static int IS_QUERY = 0;
	/**
	 * 更新
	 */
	private static int IS_UPDATE = 1;
	/**
	 * 删除
	 */
	private static int IS_DDL = 2;
	private JButton btnCreateIndex = null;

	/**
	 * This is the default constructor 构造函数
	 */
	public FmQueryTool() {
		super();
		checkToolsAuthorityAction = (CheckToolsAuthorityAction) CommonVars
				.getApplicationContext().getBean("checkToolsAuthorityAction");
		checkToolsAuthorityAction.checkToolsAuthority(new Request(CommonVars
				.getCurrUser()));
		toolsAction = (ToolsAction) CommonVars.getApplicationContext().getBean(
				"toolsAction");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosing(InternalFrameEvent e) {
				FmQueryTool.this
						.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				if (isChangeText == true) {
					int state = JOptionPane.showConfirmDialog(FmQueryTool.this,
							"文字已经改变,是否保存文件!!", "保存信息!!!",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					if (state == JOptionPane.YES_OPTION) {
						saveHsqlFile(false);
						FmQueryTool.this
								.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					} else if (state == JOptionPane.NO_OPTION) {
						FmQueryTool.this
								.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					} else {
						FmQueryTool.this
								.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					}
				}
			}
		});
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("对象查询工具");
		this.setSize(841, 455);
		this.setContentPane(getJContentPane());
		this.initUIComponents();
	}

	/**
	 * init Data to UI
	 * 
	 */
	private void initUIComponents() {
		setAllKeyListener(getJContentPane());
		initJTreeHsql();
		initJTreeSql();
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
			jToolBar = new JToolBar();
			jToolBar.add(getBtnOpenFile());
			jToolBar.add(getBtnSaveFile());
			jToolBar.add(getBtnOtherSave());
			jToolBar.add(getBtnExecuteByHSql());
			jToolBar.add(getBtnExecuteBySql());
			jToolBar.add(getJButton());
			jToolBar.add(getBtnStatus());
			jToolBar.add(getBtnCreateIndex());
			jToolBar.add(getBtnClose());
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
		if (tableResult == null) {
			tableResult = new JTable();
			tableResult.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() > 1) {
						TableModel tableModel = tableResult.getModel();
						if (!(tableModel instanceof JTableListModel)) {
							return;
						}
						JTableListModel jTableListModel = (JTableListModel) tableResult
								.getModel();
						Object o = jTableListModel.getCurrentRow();
						if (o != null) {
							Inspector.basicInspect(o);
						}
					}
				}
			});
		}
		return tableResult;
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
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExecuteByHSql() {
		if (btnExecuteByHSql == null) {
			btnExecuteByHSql = new JButton();
			btnExecuteByHSql.setText("执行 HSQL");
			btnExecuteByHSql.setToolTipText("执行 HSQL (F5)");
			btnExecuteByHSql
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {
							execHsql();
						}

					});

		}
		return btnExecuteByHSql;
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
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnStatus() {
		if (btnStatus == null) {
			btnStatus = new JButton();
			btnStatus.setText("状态");
			btnStatus.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = toolsAction.getEntityInfo();
					initHsqlTable(list);
					jTabbedPane.setSelectedIndex(1);
				}
			});
		}
		return btnStatus;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnExecuteFile == null) {
			btnExecuteFile = new JButton();
			btnExecuteFile.setText("执行文件");
			btnExecuteFile
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							txtFile = getHsqlFile();
							if (txtFile == null) {
								return;
							}
							new executeHsql().start();
						}
					});
		}
		return btnExecuteFile;
	}

	/**
	 * 执行查询
	 * 
	 * @author chen
	 * 
	 */
	class executeHsql extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在执行文件资料，请稍后...");
				List list = parseTxtFile();
				for (int i = 0; i < list.size(); i++) {
					String hsql = (String) list.get(i);
					System.out.println("hsql:" + hsql);
					logTextPane.setText(logTextPane.getText() + "\n"
							+ "--hsql:" + hsql);
					try {
						long beginTime = System.currentTimeMillis();
						int delrows = toolsAction.execute(hsql);
						long endTime = System.currentTimeMillis();
						logTextPane.setText("受影响的记录数 : " + delrows + " 条  \n"
								+ "total time: " + (endTime - beginTime)
								+ " 毫秒 ");
					} catch (Exception ex) {
						logTextPane.setText("执行有错误：" + ex.getMessage());
					}
				}
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmQueryTool.this, "执行成功！", "提示",
						2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			jTabbedPane.setSelectedIndex(0);
		}
	}

	/**
	 * 解析SQL文件
	 * 
	 * @return
	 */
	private List parseTxtFile() {
		BufferedReader in;
		ArrayList<String> list = new ArrayList<String>();
		try {
			in = new BufferedReader(new FileReader(txtFile));
			String s = new String();
			try {
				String x = "";
				while ((s = in.readLine()) != null) {
					if (s.trim().equals("")) {
						continue;
					}
					if (!s.trim().equals("--end")) {
						x = x + s;
					}
					if (s.trim().equals("--end")) {
						list.add(x);
						x = "";
					}
				}
				if (!"".equals(x)) {
					list.add(x);
					x = "";
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 调出文件选择框
	 * 
	 * @return
	 */
	private File getHsqlFile() {
		File txtFile = null;
		JFileChooser fileChooser = new JFileChooser("./");
		// fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
		fileChooser.addChoosableFileFilter(new ExampleFileFilter("txt"));
		fileChooser.addChoosableFileFilter(new ExampleFileFilter("sql"));
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		int state = fileChooser.showDialog(FmQueryTool.this, "选择HSQL文件");
		if (state == JFileChooser.APPROVE_OPTION) {
			txtFile = fileChooser.getSelectedFile();
		}
		return txtFile;
	}

	/**
	 * 文件过滤器
	 * 
	 * @author chen
	 * 
	 */
	class ExampleFileFilter extends FileFilter {
		String suffix = "";

		ExampleFileFilter(String suffix) {
			this.suffix = suffix;
		}

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
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_F5) {
						execHsql();
					}
					if (e.getKeyCode() == KeyEvent.VK_F6) {
						execSql();
					}
				}
			});
			hsqlTextPane.getDocument().addDocumentListener(
					new DocumentListener() {

						public void insertUpdate(DocumentEvent e) {
							isChangeText = true;
						}

						public void removeUpdate(DocumentEvent e) {
							isChangeText = true;
						}

						public void changedUpdate(DocumentEvent e) {

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
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
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

	/**
	 * 初始化表格
	 * 
	 * @param objList
	 */
	private void initHsqlTable(final List objList) {
		JTableListModel tableModel = new JTableListModel(getJTable(), objList,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						Class cls = getClazz(objList);
						if (objList != null && objList.size() > 0
								&& cls != null) {
							if (CommonVariables
									.isSingleBasicType(cls.getName())) {
								list.add(addColumn("结果值", 250));
							} else if (cls.isArray()) {
								Object[] mobj = (Object[]) objList.get(0);
								for (int i = 0; i < mobj.length; i++) {
									list.add(addColumn(String.valueOf(i),
											String.valueOf(i), 100));
								}
							} else {
								PropertyDescriptor[] origDescriptors = PropertyUtils
										.getPropertyDescriptors(cls);
								for (int i = 0; i < origDescriptors.length - 1; i++) {
									String mName = origDescriptors[i].getName();
									list.add(addColumn(mName, mName, 100));
								}
							}
						}
						return list;
					}

				});
		tableModel.setMiRenderColumnEnabled(false);
	}

	/**
	 * 返回对象的类型
	 * 
	 * @param objList
	 * @return
	 */
	private Class getClazz(List objList) {
		for (int i = 0; i < objList.size(); i++) {
			if (objList.get(i) != null) {
				return objList.get(i).getClass();
			}
		}
		return String.class;
	}

	/**
	 * 执行HSql
	 * 
	 */
	private void execHsql() {
		new execHsql().start();
	}

	/**
	 * 执行HSQL
	 * 
	 * @author chen
	 * 
	 */
	class execHsql extends Thread {

		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();

			CommonProgress.showProgressDialog(flag, FmMain.getInstance(),
					false, null, 0);
			CommonProgress.setMessage(flag, "正在执行 HSQL 语句, 请稍后...");

			btnExecuteByHSql.setEnabled(false);
			this.executesql();
			btnExecuteByHSql.setEnabled(true);

			CommonProgress.closeProgressDialog(flag);
		}

		private void executesql() {
			String hsql = hsqlTextPane.getSelectedText() == null ? hsqlTextPane
					.getText() : hsqlTextPane.getSelectedText();
			if (hsql.trim().equals("")) {
				return;
			}
			if (isQuery(hsql)) {
				executeQuery(hsql);
			} else {
				executeUpdate(hsql);
			}
		}

		/**
		 * 是否是查找
		 * 
		 * @param hsql
		 * @return
		 */
		private boolean isQuery(String hsql) {
			boolean isQuery = false;
			String tempHsql = hsql.toLowerCase().trim();
			if (tempHsql.startsWith("select") || tempHsql.startsWith("from")) {
				isQuery = true;
			}
			return isQuery;
		}

		/**
		 * 执行查询
		 * 
		 * @param hsql
		 */
		private void executeQuery(String hsql) {
			List objList = new ArrayList();
			try {
				long beginTime = System.currentTimeMillis();
				objList = toolsAction.execHql(hsql);
				long endTime = System.currentTimeMillis();
				String classType = "";
				if (objList.size() > 0) {
					classType = getClazz(objList).getName();
				}
				logTextPane.setText("总记录数 : " + objList.size()
						+ " 条  \nclassType:" + classType + "\ntotal time: "
						+ (endTime - beginTime) + " 毫秒");

				jTabbedPane.setSelectedIndex(1);
			} catch (Exception ex) {
				logTextPane.setText(ex.getMessage());
				jTabbedPane.setSelectedIndex(0);
			}
			initHsqlTable(objList);
		}

		/**
		 * 执行更新
		 * 
		 * @param hsql
		 */
		private void executeUpdate(String hsql) {
			try {
				long beginTime = System.currentTimeMillis();
				int delrows = toolsAction.execute(hsql);
				long endTime = System.currentTimeMillis();

				logTextPane.setText("受影响的记录数 : " + delrows + " 条  \n"
						+ "total time: " + (endTime - beginTime) + " 毫秒 ");

			} catch (Exception ex) {
				logTextPane.setText(ex.getMessage());
			}
			jTabbedPane.setSelectedIndex(0);
		}
	}

	/**
	 * 初始化表格
	 * 
	 * @param rs
	 */
	private void initSqlTable(final TempResultSet rs) {
		new JTableJDBCModel(tableResult, rs.getColumnNames(), rs.getRows());
	}

	/**
	 * 执行Sql
	 * 
	 */
	private void execSql() {
		new execSql().start();
	}

	/**
	 * 执行SQL
	 * 
	 * @author chen
	 * 
	 */
	class execSql extends Thread {

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

		/**
		 * 是否是查找
		 * 
		 * @param hsql
		 * @return
		 */
		private int isQuery(String hsql) {
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
		 * 执行查询
		 * 
		 * @param sql
		 */
		private void executeQuery(String sql) {
			TempResultSet rs = new TempResultSet();
			try {
				long beginTime = System.currentTimeMillis();
				rs = toolsAction.getTempResultSet(sql);
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

		/**
		 * 执行更新
		 * 
		 * @param sql
		 */
		private void executeUpdate(String sql) {
			try {
				long beginTime = System.currentTimeMillis();
				int delrows = toolsAction.executeUpdateSql(sql);
				long endTime = System.currentTimeMillis();

				logTextPane.setText("受影响的记录数 : " + delrows + " 条  \n"
						+ "total time: " + (endTime - beginTime) + " 毫秒 ");

			} catch (Exception ex) {
				logTextPane.setText(ex.getMessage());
			}
			jTabbedPane.setSelectedIndex(0);
		}

		/**
		 * 运行
		 * 
		 * @param sql
		 */
		private void execute(String sql) {
			try {
				long beginTime = System.currentTimeMillis();
				boolean isOk = toolsAction.executeSql(sql);
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
			btnSaveFile.setText("保存");
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
			isChangeText = false;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(FmQueryTool.this, "打开文件有错！", "提示",
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
			isChangeText = false;
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
			JOptionPane.showMessageDialog(FmQueryTool.this, "保存文件有错！", "提示",
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
		int state = fileChooser.showDialog(FmQueryTool.this, "保存HSQL文件");
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
			btnOtherSave.setText("另存为");
			btnOtherSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveHsqlFile(true);
				}
			});
		}
		return btnOtherSave;
	}

	/**
	 * This method initializes jTreeHsql
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getJTreeHsql() {
		if (jTreeHsql == null) {
			jTreeHsql = new JTree() {
				public JToolTip createToolTip() {
					return new JMultiLineToolTip();
				}
			};
			jTreeHsql.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jTreeHsql.addMouseListener(new java.awt.event.MouseAdapter() {

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
		return jTreeHsql;
	}

	/**
	 * 是否是基本类型
	 * 
	 * @param cls
	 * @return
	 */
	private boolean isBasicType(String cls) {
		boolean isTrue = false;
		if (cls.equals(int.class.getName()) || cls.equals(long.class.getName())
				|| cls.equals(short.class.getName())
				|| cls.equals(double.class.getName())
				|| cls.equals(float.class.getName())
				|| cls.equals(boolean.class.getName())
				|| cls.equals(Integer.class.getName())
				|| cls.equals(Long.class.getName())
				|| cls.equals(Short.class.getName())
				|| cls.equals(Double.class.getName())
				|| cls.equals(Float.class.getName())
				|| cls.equals(String.class.getName())
				|| cls.equals(Boolean.class.getName())
				|| cls.equals(Date.class.getName())
				|| cls.equals(Calendar.class.getName())
				|| cls.equals(Set.class.getName())
				|| cls.equals(List.class.getName())
				|| cls.equals(Map.class.getName())
				|| cls.equals(HashMap.class.getName())
				|| cls.equals(HashSet.class.getName())) {
			isTrue = true;
		}
		return isTrue;
	}

	/**
	 * 创建子节点
	 * 
	 * @param selPath
	 */
	private void createNode(TreePath selPath) {

		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) selPath
				.getLastPathComponent();
		if (treeNode.getChildCount() > 0) {
			return;
		}
		Object value = treeNode.getUserObject();
		if (!(value instanceof TempNodeItem)) {
			return;
		}
		String className = ((TempNodeItem) value).getClassName();

		try {
			if (isBasicType(className)) {
				return;
			}
			PropertyDescriptor[] props = PropertyUtils
					.getPropertyDescriptors(Class.forName(className));
			List<TempNodeItem> tempNodeItemList = new ArrayList<TempNodeItem>();
			for (int i = 0; i < props.length; i++) {
				String tempField = props[i].getName();
				if (tempField.equals("class")) {
					continue;
				}
				Class clazz = props[i].getPropertyType();
				TempNodeItem temp = new TempNodeItem();
				temp.setClassName(clazz.getName());
				temp.setName(tempField);
				temp.setCnName("");
				tempNodeItemList.add(temp);
			}
			tempNodeItemList = this.toolsAction.getAnnotate(tempNodeItemList,
					className);
			//
			// 删除所有子节点
			//
			// treeNode.removeAllChildren();
			for (TempNodeItem temp : tempNodeItemList) {
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(temp);
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
	private void initJTreeHsql() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("数据库");
		List<TempNodeItem> list = this.toolsAction.getTempNodeItems();
		for (TempNodeItem temp : list) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(temp);
			root.add(node);
		}
		DefaultTreeModel model = new DefaultTreeModel(root);
		jTreeHsql.setModel(model);
		ToolTipManager.sharedInstance().registerComponent(jTreeHsql);
		jTreeHsql.setCellRenderer(new HsqlTreeCellRenderer());
	}

	/**
	 * 初始化事
	 * 
	 */
	private void initJTreeSql() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("数据库");
		Map<TempNodeItem, List<TempNodeItem>> map = this.toolsAction
				.getTableColumnMap();
		List<TempNodeItem> keyList = new ArrayList<TempNodeItem>();
		keyList.addAll(map.keySet());
		Collections.sort(keyList);
		for (TempNodeItem key : keyList) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(key);
			List<TempNodeItem> list = map.get(key);
			for (TempNodeItem temp : list) {
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(
						temp);
				node.add(childNode);
			}
			root.add(node);
		}
		DefaultTreeModel model = new DefaultTreeModel(root);
		jTreeSql.setModel(model);
		ToolTipManager.sharedInstance().registerComponent(jTreeSql);
		jTreeSql.setCellRenderer(new SqlTreeCellRenderer());
	}

	/**
	 * 树Cell呈现
	 */
	class SqlTreeCellRenderer extends DefaultTreeCellRenderer {

		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, sel, expanded,
					leaf, row, hasFocus);
			Object object = ((DefaultMutableTreeNode) value).getUserObject();
			if (object instanceof TempNodeItem) {
				TempNodeItem temp = (TempNodeItem) object;
				setText(temp.getName());
				String className = temp.getClassName();
				setToolTipText(temp.getCnName());
			} else {
				if (object != null) {
					setText(object.toString());
					setToolTipText(object.toString());
				}
			}
			return this;
		}
	}

	/**
	 * 树Cell呈现
	 */
	class HsqlTreeCellRenderer extends DefaultTreeCellRenderer {

		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, sel, expanded,
					leaf, row, hasFocus);
			Object object = ((DefaultMutableTreeNode) value).getUserObject();
			if (object instanceof TempNodeItem) {
				TempNodeItem temp = (TempNodeItem) object;
				setText(temp.getName());
				String className = temp.getClassName();
				setToolTipText(temp.getCnName());
				if (!isBasicType(className)) {
					this.setForeground(Color.BLUE);
				}
			} else {
				if (object != null) {
					setText(object.toString());
					setToolTipText(object.toString());
				}
			}
			return this;
		}
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setToolTipText("类名");
			jScrollPane3.setViewportView(getJTreeHsql());
		}
		return jScrollPane3;
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
					if (jTabbedPane1.getSelectedIndex() == 0) {
						searchNode(jTreeHsql, searchText);
					} else {
						searchNode(jTreeSql, searchText);
					}
				}

			});
			tfSearch.addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					JTextField tf = (JTextField) e.getSource();
					tf.selectAll();
				}

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
			if (object instanceof TempNodeItem) {
				TempNodeItem temp = (TempNodeItem) object;
				String name = temp.getName().toLowerCase();
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
			btnExecuteBySql.setText("执行 SQL");
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
			jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane1.addTab("类名", null, getJScrollPane3(), "类名");
			jTabbedPane1.addTab("表名", null, getJScrollPane4(), "表名");
			jTabbedPane1.setSelectedIndex(1);
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
			jTreeSql = new JTree() {
				public JToolTip createToolTip() {
					return new JMultiLineToolTip();
				}
			};
		}
		return jTreeSql;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCreateIndex() {
		if (btnCreateIndex == null) {
			btnCreateIndex = new JButton();
			btnCreateIndex.setText("索引优化");
			btnCreateIndex.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDBIndex dg=new DgDBIndex();
					dg.setVisible(true);
				}
			});
		}
		return btnCreateIndex;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
