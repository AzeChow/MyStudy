package com.bestway.client.custom.common;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JSplitPane;
import java.awt.GridBagLayout;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;

public class DgImportedBGDInfo extends JDialogBase {

	private JPanel jContentPane = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JTableListModel tableModel;

	private List lsSuccess; // @jve:decl-index=0:

	private List lsError; // @jve:decl-index=0:

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JToolBar jToolBar = null;

	private JLabel jLabel = null;

	private JToolBar jToolBar1 = null;

	private JLabel jLabel1 = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null;

	private JButton jButton = null;

	public List getLsError() {
		return lsError;
	}

	public void setLsError(List lsError) {
		this.lsError = lsError;
	}

	public List getLsSuccess() {
		return lsSuccess;
	}

	public void setLsSuccess(List dataSource) {
		this.lsSuccess = dataSource;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgImportedBGDInfo() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(625, 424));
		this.setTitle("导入报关单信息");
		this.setContentPane(getJContentPane());

	}

	public void setVisible(boolean b) {
		if (b) {
			if (lsSuccess == null) {
				lsSuccess = new ArrayList();
			}
			if (lsError == null) {
				lsError = new ArrayList();
			}
			initTable(lsSuccess);
			initErrorTable(lsError);
		}
		super.setVisible(b);
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
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
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

	private void initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {

						List<JTableListColumn> list = new Vector<JTableListColumn>();

						list.add(addColumn("文件名称", "fileName", 200));
						list.add(addColumn("日期", "date", 100));
						list.add(addColumn("进出口标志", "impExpFlag", 80));
						list.add(addColumn("报关单号", "customsDeclarationCode",
								150));
						list.add(addColumn("报关单流水号", "serialNumber",
								50));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							switch (Integer.parseInt(value.toString())) {
							case ImpExpFlag.IMPORT:
								str = "进口报关单(I)";
								break;
							case ImpExpFlag.EXPORT:
								str = "出口报关单(E)";
								break;
							case ImpExpFlag.SPECIAL:
								str = "特殊报关单";
								break;
							}
						}
						this.setText(str);
						return this;
					}
				});
	}

	private void initErrorTable(List list) {
		tableModel = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {

						List<JTableListColumn> list = new Vector<JTableListColumn>();

						list.add(addColumn("文件名称", "fileName", 300));
						list.add(addColumn("日期", "date", 100));
						list.add(addColumn("进出口标志", "impExpFlag", 80));
						list.add(addColumn("错误信息", "errorInfo", 500));
						return list;
					}
				});
		jTable1.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							switch (Integer.parseInt(value.toString())) {
							case ImpExpFlag.IMPORT:
								str = "进口(I)";
								break;
							case ImpExpFlag.EXPORT:
								str = "出口(E)";
								break;
							}
						}
						this.setText(str);
						return this;
					}
				});
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(200);
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setDividerSize(2);
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
			jPanel.add(getJToolBar(), BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
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
			jPanel1.add(getJToolBar1(), BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), BorderLayout.CENTER);
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
			jLabel = new JLabel();
			jLabel
					.setText("导入成功的报关单信息                                                                                                                 ");
			jLabel.setForeground(Color.blue);
			jToolBar = new JToolBar();
			jToolBar.setOrientation(JToolBar.HORIZONTAL);
			jToolBar.add(jLabel);
			jToolBar.add(getJButton());
			jToolBar.setPreferredSize(new Dimension(Double.valueOf(
					jToolBar.getPreferredSize().getWidth()).intValue(),
					Double.valueOf(jLabel.getPreferredSize().getHeight())
							.intValue() + 5));
			// jButton.setPreferredSize(new Dimension(jButton.getWidth(), 5));
		}
		return jToolBar;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("导入失败的报关单信息");
			jLabel1.setForeground(Color.blue);
			jToolBar1 = new JToolBar();
			jToolBar1.add(jLabel1);
		}
		return jToolBar1;
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

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("关闭");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
