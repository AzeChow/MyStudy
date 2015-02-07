package com.bestway.common.client.dataimport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DBDataRoot;
import com.bestway.bcus.dataimport.entity.DBView;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgView extends JDialogBase {

	private JPanel				jPanel				= null;

	private JSplitPane			jSplitPane			= null;

	private JPanel				jPanel1				= null;

	private JPanel				jPanel2				= null;

	private JPanel				jPanel3				= null;

	private JToolBar			jToolBar			= null;

	private JButton				jButton				= null;

	private JButton				jButton1			= null;

	private JButton				jButton2			= null;

	private JTable				jTable				= null;

	private JScrollPane			jScrollPane			= null;

	private DataImportAction	dataImportAction	= null;

	private JPanel				jPanel4				= null;

	private JScrollPane			jScrollPane1		= null;

	private JList				jList				= null;
	private DBDataRoot			dbDataRoot			= null; // 数据源

	private JTableListModel		tableModel			= null;

	private JButton				jButton3			= null;

	/**
	 * This is the default constructor
	 */
	public DgView() {
		super();
		dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		initialize();
		Vector vector = new Vector();
		List dbRoot = this.dataImportAction.findDBDataRoot(new Request(
				CommonVars.getCurrUser()));
		for (int i = 0; i < dbRoot.size(); i++) {
			DBDataRoot db = (DBDataRoot) dbRoot.get(i);
			vector.add(db); // 增加数据源
		}
		this.jList.setListData(vector);
		this.jList.setCellRenderer(new UserListCellRenderer());
		if (this.jList.getModel().getSize() > 0) {
			dbDataRoot = ((DBDataRoot) this.jList.getModel().getElementAt(0));
			this.getPage(dbDataRoot);
		}
	}

	class UserListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			String s = "";
			if (value != null) {
				s = ((DBDataRoot) value).getName();
				setIcon(CommonVars.getRcpIconSource().getIcon(
						"images.table.icon"));
			}
			setText(s);
			return this;
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel());
		this.setSize(768, 456);
		this.setTitle("视图操作");

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
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
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
			jSplitPane.setLeftComponent(getJPanel1());
			jSplitPane.setRightComponent(getJPanel2());
			jSplitPane.setResizeWeight(0.2);
			jSplitPane.setDividerLocation(120);
			jSplitPane.setDividerSize(8);
		}
		return jSplitPane;
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
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJPanel4(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
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
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJPanel3(), java.awt.BorderLayout.NORTH);
			jPanel2.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
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
			jPanel3.add(getJToolBar(), java.awt.BorderLayout.CENTER);
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
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton3());
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
			jButton.setText("新增");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgViewEdit dg = new DgViewEdit();
					dg.setDbDataRoot(dbDataRoot);
					dg.setTableModel1(tableModel);
					dg.setVisible(true);
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
						JOptionPane.showMessageDialog(DgView.this,
								"请选择你要修改的资料", "确认", 1);
						return;
					}
					DgViewSave dg = new DgViewSave();
					dg.setAdd(false);
					dg.setTableModel(tableModel);
					dg.setVisible(true);
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
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgView.this,
								"请选中要删除的资料!", "提示", 2);
					}
					try {
						DBView obj = (DBView) tableModel.getCurrentRow();
						dataImportAction.deleteDBView(new Request(CommonVars
								.getCurrUser()), obj);
						tableModel.deleteRow(obj);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(DgView.this,
								"已被引用，不能删除！", "提示", 2);
					}

				}
			});

		}
		return jButton2;
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
			javax.swing.JLabel jLabel = new JLabel();

			jPanel4 = new JPanel();
			jLabel.setText("数据源");
			jPanel4.add(jLabel, null);
		}
		return jPanel4;
	}

	/**
	 * 
	 * This method initializes jScrollPane1
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJList());
		}
		return jScrollPane1;
	}

	/**
	 * 
	 * This method initializes jtRightList
	 * 
	 * 
	 * 
	 * @return javax.swing.JList
	 * 
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
			jList
					.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

						public void valueChanged(
								javax.swing.event.ListSelectionEvent e) {

							DgView.this.dbDataRoot = (DBDataRoot) (((JList) e
									.getSource()).getSelectedValue());
							getPage(dbDataRoot);

						}
					});

		}
		return jList;
	}

	public void getPage(DBDataRoot db) // 获得视图
	{
		List dataSource = null;
		dataSource = dataImportAction.findDBView(new Request(CommonVars
				.getCurrUser()), db);
		initTable(dataSource);
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("视图名称", "name", 250));
						list.add(addColumn("SQL脚本", "sqlScript", 300));
						list.add(addColumn("是否生效", "isEffect", 80));
						return list;
					}
				});
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

					DgView.this.dispose();

				}
			});

		}
		return jButton3;
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
} // @jve:decl-index=0:visual-constraint="10,10"
