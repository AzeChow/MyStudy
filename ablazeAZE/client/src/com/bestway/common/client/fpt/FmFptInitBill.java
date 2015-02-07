/*
 * Created on 2004-10-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptInitBill;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmFptInitBill extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTabbedPane jTabbedPane = null;

	private JButton btnAdd = null;
	
	private JButton btnBrowse = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JButton btnExit = null;

	private JPanel pnImport = null;

	private JPanel pnExport = null;

	private JTable tbImport = null;

	private JScrollPane jScrollPane = null;

	private JTable tbExport = null;

	private JScrollPane jScrollPane1 = null;

	private JTableListModel importTableModel = null;

	private JTableListModel exportTableModel = null;

	private FptInitBill transferFactoryInitBill = null;

	private FptManageAction fptManageAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmFptInitBill() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("进出货转厂期初单");
		this.setContentPane(getJContentPane());
		this.setSize(555, 308);
		// this.jTabbedPane.setEnabledAt(1,false);//明门暂时需求，其他需要使用再做修改
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					@Override
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						showImpInitData();
						setState();
					}
				});
		fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
		tbImport.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						if (importTableModel == null) {
							return;
						}
						try {
							if (importTableModel.getCurrentRow() != null) {
								setState();
							}
						} catch (Exception ee) {
						}
					}
				});
		tbExport.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						if (exportTableModel == null) {
							return;
						}
						try {
							if (exportTableModel.getCurrentRow() != null) {
								setState();
							}
						} catch (Exception ee) {
						}
					}
				});
	}

	private void setState() {
		FptInitBill initBill = null;
		if (jTabbedPane.getSelectedIndex() == 0) {
			initBill = (FptInitBill) importTableModel
					.getCurrentRow();
		} else {
			initBill = (FptInitBill) exportTableModel
					.getCurrentRow();
		}
		if (initBill != null) {
			this.btnDelete.setEnabled(initBill.getIsEffective() == null ? true
					: !initBill.getIsEffective().booleanValue());
			this.btnEdit.setEnabled(initBill.getIsEffective() == null ? true
					: !initBill.getIsEffective().booleanValue());
		}
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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
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
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnBrowse());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
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
			jTabbedPane.addTab("进货期初单", null, getPnImport(), null);
			jTabbedPane.addTab("出货期初单", null, getPnExport(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (jTabbedPane.getSelectedIndex() == 0) {
								showImpInitData();
							} else if (jTabbedPane.getSelectedIndex() == 1) {
								showExpInitData();
							}
						}
					});
		}
		return jTabbedPane;
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
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JTableListModel tableModel = null;
					boolean isImport = true;
					if (jTabbedPane.getSelectedIndex() == 0) {
						tableModel = importTableModel;
						isImport = true;
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						tableModel = exportTableModel;
						isImport = false;
					}
					DgFptInitBill dg = new DgFptInitBill();
					dg.setTransferTableModel(tableModel);
					dg.setDataState(DataState.ADD);
					dg.setImport(isImport);
					dg.setVisible(true);
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setText("显示");
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JTableListModel tableModel = null;
					boolean isImport = true;
					if (jTabbedPane.getSelectedIndex() == 0) {
						tableModel = importTableModel;
						isImport = true;
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						tableModel = exportTableModel;
						isImport = false;
					}
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(null, "请选择要查看的资料!", "提示",
								0);
						return;
					}
					DgFptInitBill dg = new DgFptInitBill();
					dg.setTransferTableModel(tableModel);
					dg.setDataState(DataState.BROWSE);
					dg.setImport(isImport);
					dg.setVisible(true);
				}
			});
		}
		return btnBrowse;
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
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JTableListModel tableModel = null;
					boolean isImport = true;
					if (jTabbedPane.getSelectedIndex() == 0) {
						tableModel = importTableModel;
						isImport = true;
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						tableModel = exportTableModel;
						isImport = false;
					}
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(null, "请选择要修改的资料!", "提示",
								0);
						return;
					}
					DgFptInitBill dg = new DgFptInitBill();
					dg.setTransferTableModel(tableModel);
					dg.setDataState(DataState.EDIT);
					dg.setImport(isImport);
					dg.setVisible(true);
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
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JTableListModel tableModel = null;
					if (jTabbedPane.getSelectedIndex() == 0) {
						tableModel = importTableModel;
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						tableModel = exportTableModel;
					}
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(null, "请选择要删除的资料!", "提示",
								0);
						return;
					}
					if (JOptionPane.showConfirmDialog(
							FmFptInitBill.this, "是否确定删除数据!!!",
							"提示", 0) != 0) {
						return;
					}
					FptInitBill transferFactoryInitBill = (FptInitBill) tableModel
							.getCurrentRow();
					fptManageAction.deleteTransferFactoryInitBill(
							new Request(CommonVars.getCurrUser()),
							transferFactoryInitBill);
					tableModel.deleteRow(transferFactoryInitBill);
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes pnImport
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnImport() {
		if (pnImport == null) {
			pnImport = new JPanel();
			pnImport.setLayout(new BorderLayout());
			pnImport.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return pnImport;
	}

	/**
	 * This method initializes pnExport
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnExport() {
		if (pnExport == null) {
			pnExport = new JPanel();
			pnExport.setLayout(new BorderLayout());
			pnExport.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return pnExport;
	}

	/**
	 * This method initializes tbImport
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImport() {
		if (tbImport == null) {
			tbImport = new JTable();
			tbImport.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						JTableListModel tableModel = null;
						boolean isImport = true;
						if (jTabbedPane.getSelectedIndex() == 0) {
							tableModel = importTableModel;
							isImport = true;
						} else if (jTabbedPane.getSelectedIndex() == 1) {
							tableModel = exportTableModel;
							isImport = false;
						}
						if (tableModel.getCurrentRow() == null) {
							return;
						}
						DgFptInitBill dg = new DgFptInitBill();
						dg.setTransferTableModel(tableModel);
						dg.setDataState(DataState.BROWSE);
						dg.setImport(isImport);
						dg.setVisible(true);
					}
				}
			});
		}
		return tbImport;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbImport());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbExport
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExport() {
		if (tbExport == null) {
			tbExport = new JTable();
			tbExport.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						JTableListModel tableModel = null;
						boolean isImport = true;
						if (jTabbedPane.getSelectedIndex() == 0) {
							tableModel = importTableModel;
							isImport = true;
						} else if (jTabbedPane.getSelectedIndex() == 1) {
							tableModel = exportTableModel;
							isImport = false;
						}
						if (tableModel.getCurrentRow() == null) {
							return;
						}
						DgFptInitBill dg = new DgFptInitBill();
						dg.setTransferTableModel(tableModel);
						dg.setDataState(DataState.BROWSE);
						dg.setImport(isImport);
						dg.setVisible(true);
					}
				}
			});
		}
		return tbExport;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbExport());
		}
		return jScrollPane1;
	}

	private void showImpInitData() {
		List list = this.fptManageAction
				.findTransferFactoryInitBill(new Request(CommonVars
						.getCurrUser()), true);
		importTableModel = new JTableListModel(tbImport, list,
				new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("单据号",
								"transferFactoryInitBillCode", 150));
						list.add(addColumn("生效日期", "effectiveDate", 100));
						list.add(addColumn("生效", "isEffective", 50));
						list.add(addColumn("商品项数", "itemCount", 50));
						list.add(addColumn("客户供应商", "scmCoc.name", 100));
						list.add(addColumn("录入日期", "buildDate", 100));
						list.add(addColumn("录入单位号码", "buildCompanyCode", 100));
						list.add(addColumn("录入单位名称", "buildCompanyName", 150));
						return list;
					}
				});
		tbImport.getColumnModel().getColumn(3).setCellRenderer(
				new checkBoxRenderer());
	}

	private void showExpInitData() {
		List list = this.fptManageAction
				.findTransferFactoryInitBill(new Request(CommonVars
						.getCurrUser()), false);
		exportTableModel = new JTableListModel(tbExport, list,
				new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("单据号",
								"transferFactoryInitBillCode", 150));
						list.add(addColumn("生效日期", "effectiveDate", 100));
						list.add(addColumn("生效", "isEffective", 50));
						list.add(addColumn("商品项数", "itemCount", 50));
						list.add(addColumn("客户供应商", "scmCoc.name", 100));
						list.add(addColumn("录入日期", "buildDate", 100));
						list.add(addColumn("录入单位号码", "buildCompanyCode", 100));
						list.add(addColumn("录入单位名称", "buildCompanyName", 150));
						return list;
					}
				});
		tbExport.getColumnModel().getColumn(3).setCellRenderer(
				new checkBoxRenderer());
	}

	class checkBoxRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				checkBox.setSelected(false);
			} else if (value.equals("")) {
				checkBox.setSelected(false);
			} else if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
			}
			checkBox.setHorizontalAlignment(SwingConstants.CENTER);
			checkBox.setBackground(table.getBackground());
			if (isSelected) {
				checkBox.setForeground(table.getSelectionForeground());
				checkBox.setBackground(table.getSelectionBackground());
			} else {
				checkBox.setForeground(table.getForeground());
				checkBox.setBackground(table.getBackground());
			}
			return checkBox;
		}
	}
} // @jve:decl-index=0:visual-constraint="4,3"
