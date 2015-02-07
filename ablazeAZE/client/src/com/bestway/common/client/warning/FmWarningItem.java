package com.bestway.common.client.warning;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ExcuteKind;
import com.bestway.common.warning.action.WarningAction;
import com.bestway.common.warning.entity.TempWarningItem;
import com.bestway.common.warning.entity.WarningItem;
import com.bestway.common.warning.entity.WarningThread;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmWarningItem extends JInternalFrameBase {

	private JPanel			jContentPane	= null;
	private JToolBar		jToolBar		= null;
	private JScrollPane		jScrollPane		= null;
	private JTable			jTable			= null;
	private JButton			btnAdd			= null;
	private JButton			btnEdit			= null;
	private JButton			btnDelete		= null;
	private JButton			btnClose		= null;
	private JTableListModel	tableModel		= null;
	private WarningAction	warningAction	= null;
	private JCheckBox		cbStart			= null;

	/**
	 * This method initializes
	 * 
	 */
	public FmWarningItem() {
		super();
		initialize();
		this.setTitle("预警项目");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		warningAction = (WarningAction) CommonVars.getApplicationContext()
				.getBean("warningAction");
		initTable();
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(456, 319));
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
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnClose());
			jToolBar.add(getCbStart());
		}
		return jToolBar;
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
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.setPreferredSize(new Dimension(70, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addData();
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setPreferredSize(new Dimension(70, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					editData();
				}
			});
		}
		return btnEdit;
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
			btnDelete.setPreferredSize(new Dimension(70, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					deleteData();
				}
			});
		}
		return btnDelete;
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
			btnClose.setPreferredSize(new Dimension(70, 30));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	
	/**
	 * This method initializes cbStart
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbStart() {
		if (cbStart == null) {
			cbStart = new JCheckBox();
			cbStart.setText("启动预警任务");
			cbStart.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					WarningThread warningThread = warningAction
							.findWarningThread(new Request(CommonVars
									.getCurrUser()), WarningThread.WARNING_ITEM);
					if (cbStart.isSelected()) {
						if (warningThread == null) {
							warningThread = new WarningThread();
						}
						warningThread.setCompany(CommonVars.getCurrUser()
								.getCompany());
						warningThread.setType(WarningThread.WARNING_ITEM);
						warningThread.setIsStart(true);
						warningAction.saveWarningThread(new Request(CommonVars
								.getCurrUser()), warningThread);
						warningAction.startWarningThread(new Request(
								CommonVars.getCurrUser()));
					} else {
						if (warningThread != null) {
							warningThread.setIsStart(false);
							warningAction.saveWarningThread(new Request(
									CommonVars.getCurrUser()), warningThread);
							warningAction.shutDownWarningThread(new Request(
									CommonVars.getCurrUser()));
						}
					}
				}
			});
		}
		return cbStart;
	}
	
	
	

	private void initUIComponents() {
		WarningThread warningThread = this.warningAction.findWarningThread(
				new Request(CommonVars.getCurrUser()), WarningThread.WARNING_ITEM);
		if (warningThread != null) {
			this.cbStart.setSelected(warningThread.getIsStart());
		}
	}
	
	
	private void initTable() {
		List<WarningItem> list = this.warningAction
				.findWarningItem(new Request(CommonVars.getCurrUser()));
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("名称", "itemName", 185));
						list.add(addColumn("执行类别", "excuteKind", 100));
						list.add(addColumn("运行日期", "excuteday", 100));
						list.add(addColumn("运行时间", "excutetime", 60));
						list.add(addColumn("X 数量", "amount", 100));
						list.add(addColumn("任务说明", "note", 255));
						return list;
					}
				});

		jTable.getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);

						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						int executeKind = Integer.parseInt(String
								.valueOf(value));
						if (executeKind == ExcuteKind.DAY) {
							returnValue = "每日";
						} else if (executeKind == ExcuteKind.WEEK) {
							returnValue = "每周";
						} else if (executeKind == ExcuteKind.MONTH) {
							returnValue = "每月";
						} else if (executeKind == ExcuteKind.INTERVAL) {
							returnValue = "间隔";
						}
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						WarningItem planTask = (WarningItem) tableModel
								.getValueAt(row);
						super.setText((value == null) ? "" : castValue1(value,
								planTask));
						return this;
					}

					private String castValue1(Object value, WarningItem planTask) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (planTask.getExcuteKind().intValue() == ExcuteKind.WEEK) {
							if (value.equals("1")) {
								returnValue = "星期天";
							} else if (value.equals("2")) {
								returnValue = "星期一";
							} else if (value.equals("3")) {
								returnValue = "星期二";
							} else if (value.equals("4")) {
								returnValue = "星期三";
							} else if (value.equals("5")) {
								returnValue = "星期四";
							} else if (value.equals("6")) {
								returnValue = "星期五";
							} else if (value.equals("7")) {
								returnValue = "星期六";
							}
						} else if (planTask.getExcuteKind().intValue() == ExcuteKind.MONTH) {
							if (value.equals("L")) {
								returnValue = "本月最后一天";
							} else {
								returnValue = value + "日";
							}
						}
						return returnValue;
					}
				});
	}

	/** 新增数据 */
	private void addData() {
		//
		// get temp warning item
		//
		Object obj = null;
		if (tableModel != null) {
			obj = WarningCommonQuery.getInstance().getTempWarningItem(
					this.tableModel.getList());
		}
		if (obj == null) {
			return;
		}
		TempWarningItem tempWarningItem = (TempWarningItem) obj;

		DgWarningItem dialog = new DgWarningItem();
		dialog.setAdd(true);
		dialog.setTempWarningItem(tempWarningItem);
		dialog.setTableModel(tableModel);
		dialog.setVisible(true);
	}

	/** 修改数据 */
	private void editData() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(this, "请选择要修改的数据", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		DgWarningItem dialog = new DgWarningItem();
		dialog.setAdd(false);
		dialog.setTableModel(tableModel);
		dialog.setVisible(true);
	}

	/** 删除数据 */
	private void deleteData() {
		WarningItem warningItem = (WarningItem) tableModel.getCurrentRow();
		if (warningItem == null) {
			JOptionPane.showMessageDialog(this, "请选择要删除的数据", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (JOptionPane.showConfirmDialog(this, "确定要删除这条数据吗??", "确定",
				JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
			return;
		}
		warningAction.deleteWarningItem(new Request(CommonVars.getCurrUser()),
				warningItem);
		tableModel.deleteRow(warningItem);
	}

	

}