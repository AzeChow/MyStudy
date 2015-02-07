/*
 * Created on 2004-11-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DBDataRoot;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgDataRoot extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JTableListModel tableModel = null;
	private DataImportAction dataImportAction = null;
	private JButton jButton4 = null;

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
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton3());
			jToolBar.add(getJButton4());
		}
		return jToolBar;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("增加");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgDataRootEdit root = new DgDataRootEdit();

					root.setAdd(true);

					root.setTableModel(tableModel);

					root.setVisible(true);
				}
			});

		}
		return jButton;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("修改");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDataRoot.this,
								"请选择你要修改的资料", "确认", 2);
						return;
					}
					DgDataRootEdit root = new DgDataRootEdit();
					root.setAdd(false);
					root.setTableModel(tableModel);
					root.setVisible(true);
				}
			});

		}
		return jButton1;
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
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("删除");
			jButton2.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(DgDataRoot.this,
							"确定删除吗?", "提示", 0) == 0) {
						if (tableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgDataRoot.this,
									"请选中要删除的资料！", "提示", 2);
							return;
						}
						DBDataRoot obj = (DBDataRoot) tableModel
								.getCurrentRow();
						try {
							dataImportAction.deleteDBDataRoot(new Request(
									CommonVars.getCurrUser()), obj);
							tableModel.deleteRow(obj);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(DgDataRoot.this,
									"已被引用，不能删除！", "提示", 2);
						}
					}
				}
			});

		}
		return jButton2;
	}

	/**
	 * 
	 * This method initializes jButton3
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("关闭");
			jButton3.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgDataRoot.this.dispose();
				}
			});

		}
		return jButton3;
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
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This is the default constructor
	 */
	public DgDataRoot() {
		super();
		dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		initialize();
	}

	private void initTable(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("名称", "name", 100));
						list.add(addColumn("连接字符串", "driverClassName", 380));
						return list;
					}
				});
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		setTitle("数据源操作");

		setSize(521, 339);

		setContentPane(getJContentPane());

		addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				initData();
			}
		});

	}

	private void initData() {

		List list = dataImportAction.findDBDataRoot(new Request(CommonVars
				.getCurrUser()));

		System.out.println(list.get(0) + "   -----------   list initdata()");

		initTable(list);

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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("测试连接");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					testConnect();
				}
			});
		}
		return jButton4;
	}

	/**
	 * 测试连接
	 * 
	 */
	private void testConnect() {

		DBDataRoot dBDataRoot = (DBDataRoot) tableModel.getCurrentRow();

		if (dBDataRoot == null) {
			JOptionPane.showMessageDialog(DgDataRoot.this, "请选择测试的记录", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String jdbcDriver = dBDataRoot.getDriverClassName();

		System.out.println(jdbcDriver + "    jdbcDriver ***********");

		String jdbcUrl = dBDataRoot.getUrl();

		System.out.println(jdbcUrl + " jdbcURL ************");

		String userName = dBDataRoot.getUserName();

		System.out.println(userName + " username ************");

		String password = dBDataRoot.getPassword();

		System.out.println(password + " password **************");

		String returnInfo = dataImportAction.isConnect(
				new Request(CommonVars.getCurrUser()), jdbcDriver, jdbcUrl,
				userName, password);
		JOptionPane.showMessageDialog(DgDataRoot.this, returnInfo, "提示",
				JOptionPane.INFORMATION_MESSAGE);
	}

}
