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
import com.bestway.bcus.dataimport.entity.DBFormat;
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
public class DgFormat extends JDialogBase {

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
	private JTableListModel		tableModel			= null;
	private JButton				jButton3			= null;
	private JButton				jButton4			= null;
	private List				dbViewList			= null;

	/**
	 * This is the default constructor
	 */
	public DgFormat() {
		super();
		dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		initialize();
		Vector vector = new Vector();
		dbViewList = this.dataImportAction.findView(new Request(CommonVars
				.getCurrUser()));
		for (int i = 0; i < dbViewList.size(); i++) {
			DBView db = (DBView) dbViewList.get(i);
			vector.add(db); // 增加视图
		}
		this.jList.setListData(vector);
		this.jList.setCellRenderer(new UserListCellRenderer());
		if (this.jList.getModel().getSize() > 0) {
			DBView dbView = ((DBView) this.jList.getModel().getElementAt(0));
			this.getPage(dbView);
		}
	}

	class UserListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			String s = "";
			if (value != null) {
				s = ((DBView) value).getName();
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
		this.setSize(740, 463);
		this.setTitle("域定义管理");

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
			jSplitPane.setDividerLocation(140);
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
			jToolBar.add(getJButton4());
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

					DgFormatAdd dg = new DgFormatAdd();
					DBView dBView = (DBView) jList.getSelectedValue();
					dg.setDBView(dBView);
					dg.setDbViewList(dbViewList);
					dg.setTableModel2(tableModel);
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
						JOptionPane.showMessageDialog(DgFormat.this,
								"请选择你要修改的资料", "确认", 1);
						return;
					}
					DgFormatEdit dg = new DgFormatEdit();
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
						JOptionPane.showMessageDialog(DgFormat.this,
								"请先选中要删除的资料", "提示", 2);
					}
					DBFormat dbFormat = (DBFormat) tableModel.getCurrentRow();
					if (JOptionPane.showConfirmDialog(DgFormat.this,
							"是否要删除该资料？\n请注意：其下的字段对应设置将一并被删除！", "确认", 0) == 0) {
						dataImportAction.deleteAllSetup(new Request(CommonVars
								.getCurrUser()), dbFormat);
						dataImportAction.deleteDBFormat(new Request(CommonVars
								.getCurrUser()), dbFormat);
						tableModel.deleteRow(dbFormat);
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
			jLabel.setText("视  图");
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

							DBView dbView = (DBView) (((JList) e
									.getSource()).getSelectedValue());
							getPage(dbView);

						}
					});

		}
		return jList;
	}

	public void getPage(DBView dbView) // 由视图获得域
	{
		List dataSource = null;
		dataSource = dataImportAction.findDBForByDBView(new Request(CommonVars
				.getCurrUser()), dbView);
		initTable(dataSource);
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("域名称", "regionName", 200));
						list.add(addColumn("目标表", "classList.name", 80));
						list.add(addColumn("数据源名称", "dbView.db.name", 80));
						list.add(addColumn("视图名称", "dbView.name", 80));
						list.add(addColumn("域描述", "memo", 100));
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

					DgFormat.this.dispose();

				}
			});

		}
		return jButton3;
	}

	

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("转抄");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null) {
						return;
					}
					if (tableModel.getCurrentRow() == null) {
						return;
					}
					DBFormat df = (DBFormat) tableModel.getCurrentRow();
					df = dataImportAction.formatChange(new Request(CommonVars
							.getCurrUser()), df);
					tableModel.addRow(df);
				}
			});
		}
		return jButton4;
	}
} 
