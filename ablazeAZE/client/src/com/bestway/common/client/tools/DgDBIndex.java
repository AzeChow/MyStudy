package com.bestway.common.client.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.common.tools.entity.DBIndex;
import com.bestway.ui.render.TableMultiRowRender;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class DgDBIndex extends JDialogBase {

	private JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JButton btnExe = null;
	private JButton btnClose = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JTableListModel tableModel;
	/**
	 * SQL,HSQL操作DAO
	 */
	private ToolsAction toolsAction = null;
	private JRadioButton rbAll = null;
	private JRadioButton rbYearLimitDBTable = null;
	private ButtonGroup buttonGroup = null;  //  @jve:decl-index=0:visual-constraint="506,-4"

	/**
	 * This method initializes
	 * 
	 */
	public DgDBIndex() {
		super();
		initialize();
		this.getButtonGroup();
		toolsAction = (ToolsAction) CommonVars.getApplicationContext().getBean(
				"toolsAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			initTable();
		}
		super.setVisible(b);
	}

	private void initTable() {
		boolean isYearLimitDBTable=this.rbYearLimitDBTable.isSelected();
		List list = toolsAction.getIndexInfo(isYearLimitDBTable);
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("索引名", "indexName", 150));
						list.add(addColumn("表名", "tableName", 150));
						list.add(addColumn("字段名", "fieldNames", 100));
						list.add(addColumn("SQL", "indexSql", 250));
						list.add(addColumn("创建日志", "logInfo", 200));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new TableMultiRowRender());
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new TableMultiRowRender() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						Component c = super
								.getTableCellRendererComponent(table, value,
										isSelected, hasFocus, row, column);
						if (value != null) {
							String log = value.toString();
							if (log.indexOf("成功") >= 0) {
								c.setForeground(Color.GREEN);
							} else if (log.indexOf("失败") >= 0) {
								c.setForeground(Color.RED);
							}
						}
						return c;
					}
				});
		// jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(836, 547));
		this.setTitle("索引优化");
		this.setContentPane(getJContentPane());

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
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getRbAll());
			jToolBar.add(getRbYearLimitDBTable());
			jToolBar.add(getBtnExe());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExe() {
		if (btnExe == null) {
			btnExe = new JButton();
			btnExe.setText("执行");
			btnExe.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new CreateIndexThread().start();
				}
			});
		}
		return btnExe;
	}

	class CreateIndexThread extends Thread {

		@Override
		public void run() {
			List list = tableModel.getList();
			CommonStepProgress.showStepProgressDialog(null);
			CommonStepProgress.setStepMessage("系统正在保存权限数据，请稍后...");
			int maxSize = list.size();
			CommonStepProgress.setStepProgressMaximum(maxSize);
			int num = 0;
			for (int i = 0; i < list.size(); i++) {
				DBIndex dbIndex = (DBIndex) list.get(i);
				String indexSql = dbIndex.getIndexSql();
				try {
					toolsAction.executeSql(indexSql);
					dbIndex.setLogInfo("创建索引" + dbIndex.getIndexName() + "成功");
				} catch (Exception ex) {
					dbIndex.setLogInfo("创建索引" + dbIndex.getIndexName() + "失败："
							+ ex.getMessage());
				}
				tableModel.updateRow(dbIndex);
				num++;
				CommonStepProgress.setStepProgressValue(num);
			}
			CommonStepProgress.closeStepProgressDialog();
		}

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

	/**
	 * This method initializes rbAll	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbAll() {
		if (rbAll == null) {
			rbAll = new JRadioButton();
			rbAll.setText("全部表");
			rbAll.setSelected(true);
			rbAll.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					initTable();
				}
			});
		}
		return rbAll;
	}

	/**
	 * This method initializes rbYearLimitDBTable	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbYearLimitDBTable() {
		if (rbYearLimitDBTable == null) {
			rbYearLimitDBTable = new JRadioButton();
			rbYearLimitDBTable.setText("单据中心表");
			rbYearLimitDBTable.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					initTable();
				}
			});
		}
		return rbYearLimitDBTable;
	}

	/**
	 * This method initializes buttonGroup	
	 * 	
	 * @return javax.swing.ButtonGroup	
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getRbAll());
			buttonGroup.add(this.getRbYearLimitDBTable());
		}
		return buttonGroup;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
