/*
 * Created on 2004-11-12
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import java.awt.Frame;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DBDataRoot;
import com.bestway.bcus.dataimport.entity.DBView;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import com.bestway.ui.editor.SQLTextPane;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgViewSave extends JDialogBase {

	private javax.swing.JPanel	jContentPane		= null;

	private DataImportAction	dataImportAction	= null;
	private boolean				isAdd				= true;
	private boolean				isOk				= false;
	private String				sqlStr				= null;
	private DBDataRoot			dbDataRoot			= null;
	private DBView				dbView				= null;
	private DBView				db					= null;
	private JTableListModel		tableModel			= null;
	private JToolBar			jToolBar			= null;
	private JPanel				jPanel				= null;
	private JLabel				jLabel				= null;
	private JTextField			jTextField			= null;
	private JButton				jButton				= null;
	private JButton				jButton1			= null;
	private JButton				jButton2			= null;
	private JButton				jButton3			= null;
	private JSplitPane			jSplitPane			= null;
	private JPanel				jPanel1				= null;
	private JPanel				jPanel2				= null;
	private JButton				jButton4			= null;
	private JTable				jTable				= null;
	private JScrollPane			jScrollPane1		= null;
	private JScrollPane			jScrollPane2		= null;

	private SQLTextPane sqlTextPane = null;
	/**
	 * This is the default constructor
	 */
	public DgViewSave() {
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
		this.setTitle("新增视图");
		this.setSize(686, 473);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {

				if (isAdd == false) // 编辑
				{
					dbView = (DBView) tableModel.getCurrentRow();
					dbDataRoot = dbView.getDb();
					fillWindow();
				} else {
					DgViewSave.this.sqlTextPane.setText(DgViewSave.this
							.getSqlStr());
				}
			}
		});

	}

	private void fillWindow() {
		this.setDbDataRoot(dbView.getDb());
		this.jTextField.setText(dbView.getName());
		this.sqlTextPane.setText(dbView.getSqlScript());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	private void fillData(DBView db) {
		db.setDb(this.getDbDataRoot());
		db.setName(this.jTextField.getText());
		db.setSqlScript(this.sqlTextPane.getText());
	}

	private boolean checkisNull() {
		if (this.jTextField.getText().equals("")) {
			JOptionPane.showMessageDialog(DgViewSave.this, "视图名称不可为空！", "提示！",
					2);
			return false;
		}
		return true;
	}

	/**
	 * @return Returns the sqlStr.
	 */
	public String getSqlStr() {
		return sqlStr;
	}

	/**
	 * @param sqlStr
	 *            The sqlStr to set.
	 */
	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	/**
	 * @return Returns the dbDataRoot.
	 */
	public DBDataRoot getDbDataRoot() {
		return dbDataRoot;
	}

	/**
	 * @param dbDataRoot
	 *            The dbDataRoot to set.
	 */
	public void setDbDataRoot(DBDataRoot dbDataRoot) {
		this.dbDataRoot = dbDataRoot;
	}

	/**
	 * @return Returns the dbView.
	 */
	public DBView getDbView() {
		return dbView;
	}

	/**
	 * @param dbView
	 *            The dbView to set.
	 */
	public void setDbView(DBView dbView) {
		this.dbView = dbView;
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
	 * @return Returns the db.
	 */
	public DBView getDb() {
		return db;
	}

	/**
	 * @param db
	 *            The db to set.
	 */
	public void setDb(DBView db) {
		this.db = db;
	}

	/**
	 * @return Returns the isAdd.
	 */
	public boolean isAdd() {
		return isAdd;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	/**
	 * @return Returns the isOk.
	 */
	public boolean isOk() {
		return isOk;
	}

	/**
	 * @param isOk
	 *            The isOk to set.
	 */
	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getJPanel());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setText("视图名称");
			jLabel.setBounds(11, 6, 48, 18);
			jPanel.add(jLabel, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJButton3(), null);
			jPanel.add(getJButton4(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(64, 4, 159, 22);
		}
		return jTextField;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("最大化");
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
			jButton1.setText("保存");
			jButton1.setBounds(286, 2, 58, 25);
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!checkisNull()) {
						return;
					}
					if (DgViewSave.this.isAdd) {
						DBView db = new DBView();
						fillData(db);
						db = dataImportAction.saveDBView(new Request(CommonVars
								.getCurrUser()), db);
						DgViewSave.this.setDb(db);
						DgViewSave.this.setOk(true);
					} else {
						fillData(dbView);
						dbView = dataImportAction.saveDBView(new Request(
								CommonVars.getCurrUser()), dbView);
						tableModel.updateRow(dbView);
					}
					dispose();

				}
			});

		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("验证");
			jButton2.setBounds(227, 2, 58, 25);
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String sql = DgViewSave.this.sqlTextPane.getText();
					sql = dataImportAction.formatSql(new Request(CommonVars
							.getCurrUser()), sql);
					System.out.println("-- Format Sql After:" + sql);
					if (dataImportAction.textSql(
							new Request(CommonVars.getCurrUser()), dbDataRoot,
							sql).equals("成功")) {
						JOptionPane.showMessageDialog(DgViewSave.this,
								"SQL语句验证成功！", "提示！", 2);
						// new showFiles().start();
					} else {
						JOptionPane.showMessageDialog(DgViewSave.this,
								dataImportAction.textSql(new Request(CommonVars
										.getCurrUser()), dbDataRoot, sql),
								"提示！", 2);
					}
				}
			});

		}
		return jButton2;
	}

	/*class showFiles extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(DgViewSave.this);
				CommonProgress.setMessage("系统正在读取资料，请稍后...");
				String sql = DgViewSave.this.sqlTextPane.getText();
				int columnCount;
				columnCount = dataImportAction.getFieldsPart(new Request(
						CommonVars.getCurrUser()), dbDataRoot,
						DgViewSave.this.sqlTextPane.getText());
				Vector vector = new Vector();
				Vector columns = new Vector();
				jTable.setColumnModel(new DefaultTableColumnModel());
				for (int j = 1; j <= columnCount; j++) {
					String columnName = dataImportAction.getFieldName2(
							new Request(CommonVars.getCurrUser()), dbDataRoot,
							DgViewSave.this.sqlTextPane.getText(), j);
					columns.add(columnName);
				}
				vector = new Vector();
				TableModel dm = new DefaultTableModel(vector, columns);
				jTable.setModel(dm);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				System.out.println("读取数据出错：" + e.getMessage());
			} finally {
				CommonProgress.closeProgressDialog();
			}
		}
	}*/

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("取消");
			jButton3.setBounds(432, 2, 58, 25);
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (DgViewSave.this.isAdd) {
						DgViewSave.this.setOk(false);
					}
					dispose();
				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(300);
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setTopComponent(getJPanel1());
			jSplitPane.setBottomComponent(getJPanel2());
		}
		return jSplitPane;
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
			jPanel1.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
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
			jPanel2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(346, 2, 84, 25);
			jButton4.setText("显示数据");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String sql = DgViewSave.this.sqlTextPane.getText();
					sql = dataImportAction.formatSql(new Request(CommonVars
							.getCurrUser()), sql);
					System.out.println("-- Format Sql After:" + sql);
					//不需要测试
					/*if (!dataImportAction.textSql(
							new Request(CommonVars.getCurrUser()), dbDataRoot,
							sql).equals("成功")) {
						JOptionPane.showMessageDialog(DgViewSave.this,
								dataImportAction.textSql(new Request(CommonVars
										.getCurrUser()), dbDataRoot, sql),
								"提示！", 2);
						return;
					}*/
					new TxtDataRunnable(sql).start();
				}
			});
		}
		return jButton4;
	}

	class TxtDataRunnable extends Thread {
		String sql = "";
		
		private TxtDataRunnable(String sql){
			this.sql = sql;
		}
		public void run() {
			try {
				CommonProgress.showProgressDialog(DgViewSave.this);
				CommonProgress.setMessage("系统正在读取资料，请稍后...");
				
				int columnCount;
				try {
					columnCount = dataImportAction.getFieldsPart(new Request(
							CommonVars.getCurrUser()), dbDataRoot, sql);
					System.out.println("--Get columnCount:"+columnCount);
					Vector vector = new Vector();
					Vector columns = new Vector();
					jTable.setColumnModel(new DefaultTableColumnModel());
					
					System.out.println("--Start Get ColumnName");
					
					columns = dataImportAction.getFieldName2(
							new Request(CommonVars.getCurrUser()),
							dbDataRoot, sql, columnCount);
					
					System.out.println("--Finally Get ColumnName");
					vector = dataImportAction.getTableColumn(new Request(
							CommonVars.getCurrUser()), dbDataRoot, sql,
							columnCount);
					System.out.println("--Finally Init Data 10 T");
					TableModel dm = new DefaultTableModel(vector, columns);
					jTable.setModel(dm);
				} catch (SQLException e) {
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(DgViewSave.this, "读取数据出错"
							+ e.getMessage(), "提示", 2);
				}
			} finally {
				CommonProgress.closeProgressDialog();
			}
		}
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			//jTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		}
		return jTable;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable());
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
			jScrollPane2.setViewportView(getSqlTextPane());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes sqlTextPane	
	 * 	
	 * @return com.bestway.ui.editor.SQLTextPane	
	 */
	private SQLTextPane getSqlTextPane() {
		if (sqlTextPane == null) {
			sqlTextPane = new SQLTextPane();
		}
		return sqlTextPane;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
