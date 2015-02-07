package com.bestway.common.client.dataimport;

import java.awt.BorderLayout;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.TableModel;

import org.pf.joi.Inspector;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DBQuerySql;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.client.util.JTableJDBCModel;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.Request;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.common.tools.entity.TempResultSet;
import com.bestway.ui.editor.HSQLTextPane;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmQuerySql extends JInternalFrameBase {

	private JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JToolBar jToolBar = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private ToolsAction toolsAction = null;
	private JButton jButton3 = null;
	private JTabbedPane jTabbedPane = null;
	private JScrollPane jScrollPane = null;
	private JScrollPane jScrollPane2 = null;
	private HSQLTextPane logTextPane = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane1 = null;
	private JTable jTable1 = null;
	private JTableListModel tableModelSql = null;
	private DataImportAction dataImportAction = null;
	private SystemAction systemAction = null;
	private JButton btnDelete = null;

	/**
	 * This is the default constructor
	 */
	public FmQuerySql() {
		super();
		toolsAction = (ToolsAction) CommonVars.getApplicationContext().getBean(
				"toolsAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		systemAction.checkQueryAuthority(new Request(CommonVars.getCurrUser()));
		dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		initialize();

		List dataSource = dataImportAction.findDBQuerySql(new Request(
				CommonVars.getCurrUser()));
		if (dataSource != null && dataSource.size() > 0) {
			initTableSql(dataSource);
		} else {
			initTableSql(new Vector());
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		setSize(720, 544);

		setContentPane(getJContentPane());

		setTitle("公共查询");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
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
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setDividerLocation(200);
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
			jPanel.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
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
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton3());

		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {

			jButton = new JButton();

			jButton.setText("新增");

			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgQuerySqlEdit dg = new DgQuerySqlEdit();

					dg.setTableModel(tableModelSql);

					dg.setAdd(true);

					dg.setVisible(true);

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
			jButton1.setText("修改");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelSql == null
							|| tableModelSql.getCurrentRow() == null) {
						return;
					}
					DgQuerySqlEdit dg = new DgQuerySqlEdit();
					dg.setTableModel(tableModelSql);
					dg.setAdd(false);
					dg.setVisible(true);
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
			jButton2.setText("执行SQL");
			jButton2.setToolTipText("执行 SQL (F5)");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelSql == null
							|| tableModelSql.getCurrentRow() == null) {
						return;
					}
					execSql();
				}
			});
		}
		return jButton2;
	}

	/**
	 * 执行Sql
	 * 
	 */
	private void execSql() {
		new execSql().start();
	}

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

			jButton2.setEnabled(false);
			this.exec();
			jButton2.setEnabled(true);

			CommonProgress.closeProgressDialog(flag);

		}

		private void exec() {
			DBQuerySql obj = (DBQuerySql) tableModelSql.getCurrentRow();
			String sql = obj.getSqlStr();
			if (sql.trim().equals("")) {
				return;
			}
			executeQuery(sql);

		}

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
	}

	private void initSqlTable(final TempResultSet rs) {
		new JTableJDBCModel(jTable, rs.getColumnNames(), rs.getRows());
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("退出");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmQuerySql.this.dispose();
				}
			});
		}
		return jButton3;
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

	private HSQLTextPane getLogTextPane() {
		if (logTextPane == null) {
			logTextPane = new HSQLTextPane();
		}
		return logTextPane;
	}

	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() > 1) {
						TableModel tableModel = jTable.getModel();
						if (!(tableModel instanceof JTableListModel)) {
							return;
						}
						JTableListModel jTableListModel = (JTableListModel) jTable
								.getModel();
						Object o = jTableListModel.getCurrentRow();
						if (o != null) {
							Inspector.basicInspect(o);
						}
					}
				}
			});
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
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
	}

	private void initTableSql(final List list) {
		tableModelSql = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("名称", "name", 200));
						list.add(addColumn("语句", "sqlStr", 400));
						return list;
					}
				});
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(FmQuerySql.this,
							"确定删除吗?", "提示", 0) == 0) {
						if (tableModelSql.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(FmQuerySql.this,
									"请选中要删除的资料！", "提示", 2);
							return;
						}

						DBQuerySql db = (DBQuerySql) tableModelSql
								.getCurrentRow();
						try {
							dataImportAction.deleteDBQuerySql(new Request(
									CommonVars.getCurrUser()), db);

							tableModelSql.deleteRow(db);
						} catch (Exception e1) {
							throw new RuntimeException(e1);
							// JOptionPane.showMessageDialog(DgQuerySql.this,
							// "已被引用，不能删除！", "提示", 2);
						}
					}
				}
			});
		}
		return btnDelete;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
