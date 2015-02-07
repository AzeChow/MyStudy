package com.bestway.client.fecav;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.FecavState;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import javax.swing.JCheckBox;

public class FmInnerObtainFecavBill extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnRefresh = null;

	private JButton btnExit = null;

	// private JTableListModel outerTableModel = null;

	private JTableListModel innerTableModel = null;

	private FecavAction fecavAction = null;

	private JScrollPane spInner = null;

	private JTable tbInner = null;

	private JButton btnInnerObtain = null;

	private JButton btnUndoInnerObtain = null;

	private JButton btnSearch = null;

	private JButton btnPrint = null;

	private JLabel lbCount = null;

	private JButton BtnEdit = null;

	private JCheckBox jCheckBox = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmInnerObtainFecavBill() {
		super();
		initialize();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(681, 365));
		this.setContentPane(getJContentPane());
		this.setTitle("内部领单");

		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						List list = fecavAction.findFecavBillNotOuterObtain(
								new Request(CommonVars.getCurrUser()), "",
								new ArrayList());
						initInnerTable(list);
						showCount(list);
						//								  
						//					
					}
				});

	}

	private void showCount(List list) {
		// List list = fecavAction.findFecavBillNotOuterObtain(
		// new Request(CommonVars.getCurrUser()));
		this.lbCount.setText("   总份数：" + list.size());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lbCount = new JLabel();
			lbCount.setText("JLabel");
			lbCount.setForeground(java.awt.Color.BLUE);
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getSpInner(), java.awt.BorderLayout.CENTER);
			jContentPane.add(lbCount, java.awt.BorderLayout.SOUTH);
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
			jToolBar.add(getBtnInnerObtain());
			jToolBar.add(getBtnUndoInnerObtain());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnSearch());
			jToolBar.add(getBtnExit());
			jToolBar.add(getJCheckBox());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (jTabbedPane.getSelectedIndex() == 0) {
					// List list = fecavAction.findFecavBillByState(
					// new Request(CommonVars.getCurrUser()),
					// FecavState.OUTER_OBTAIN);
					// initOuterTable(list);
					// } else if (jTabbedPane.getSelectedIndex() == 1) {
					// List list = fecavAction.findFecavBillByState(
					// new Request(CommonVars.getCurrUser()),
					// FecavState.INNER_OBTAIN);
					// List list = fecavAction.findFecavBillNotOuterObtain(
					// new Request(CommonVars.getCurrUser()), "",
					// new ArrayList());
					// initInnerTable(list);
					// showCount(list);
					// }
					refresh();
				}
			});
		}
		return btnRefresh;
	}

	private void refresh() {
		List list = fecavAction.findFecavBillNotOuterObtain(new Request(
				CommonVars.getCurrUser()), "", new ArrayList());
		initInnerTable(list);
		showCount(list);
		setState();
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmInnerObtainFecavBill.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	private void initInnerTable(List list) {
		JTableListModel.dataBind(tbInner, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("核销单号 ", "code", 200));
				list.add(addColumn("内部领单人", "innerObtain", 100));
				list.add(addColumn("内部领单日期", "innerObtainDate", 100));
				list.add(addColumn("内部操作人", "innerOperator", 100));
				list.add(addColumn("内部操作日期", "innerOperatorDate", 100));
				list.add(addColumn("出口口岸", "impExpCIQ.name", 80));
				list.add(addColumn("标志", "billState", 150));
				return list;
			}
		});
		TableColumn column = this.tbInner.getColumnModel().getColumn(7);
		column.setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected,
						hasFocus, row, column);
				int state = -1;
				if (value != null) {
					state = Integer.parseInt(value.toString());
				}
				if (state == FecavState.INNER_OBTAIN) {
					this.setText("未使用");
				} else {
					this.setText("已使用");
				}
				return this;
			}
		});
		innerTableModel = (JTableListModel) tbInner.getModel();
		// CommonQuery.getInstance().addCommonFilter(this.cbbSearchField,
		// this.tfFieldValue, this.btnSearch, this.innerTableModel);
	}

	/**
	 * This method initializes spInner
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpInner() {
		if (spInner == null) {
			spInner = new JScrollPane();
			spInner.setViewportView(getTbInner());
		}
		return spInner;
	}

	/**
	 * This method initializes tbInner
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbInner() {
		if (tbInner == null) {
			tbInner = new JTable();
		}
		return tbInner;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnInnerObtain() {
		if (btnInnerObtain == null) {
			btnInnerObtain = new JButton();
			btnInnerObtain.setText("内部领用");
			btnInnerObtain
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// if (outerTableModel.getCurrentRow() == null) {
							// JOptionPane.showMessageDialog(
							// FmInnerObtainFecavBill.this,
							// "请选择你要内部领用的数据", "提示",
							// JOptionPane.OK_OPTION);
							// }
							// List list = outerTableModel.getCurrentRows();
							List list = FecavQuery.getInstance()
									.findNotInnerObtainFecavBill();
							if (list == null || list.size() < 1) {
								return;
							}
							// DgBatchInnerObtainFecavBill dg = new
							// DgBatchInnerObtainFecavBill();
							//
							// dg.setVisible(true);
							// List lsResult = dg.getLsResult();
							List lsResult = fecavAction
									.batchInnerObtainFecavBill(new Request(
											CommonVars.getCurrUser()), list,
											null, null);
							if (lsResult != null && lsResult.size() > 0) {
								innerTableModel.addRows(lsResult);
							}
							showCount(innerTableModel.getList());
						}

						//						
					});

		}
		return btnInnerObtain;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndoInnerObtain() {
		if (btnUndoInnerObtain == null) {
			btnUndoInnerObtain = new JButton();
			btnUndoInnerObtain.setText("取消内部领用");
			btnUndoInnerObtain
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (innerTableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmInnerObtainFecavBill.this,
										"请选择你要取消内部领用的数据", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmInnerObtainFecavBill.this,
									"确定要取消内部已领用的核销单??", "提示",
									JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							List list = innerTableModel.getCurrentRows();
							for (int i = list.size() - 1; i >= 0; i--) {
								FecavBill fecavBill = (FecavBill) list.get(i);
								if (FecavState.INNER_OBTAIN != fecavBill
										.getBillState()) {
									list.remove(i);
								}
							}
							List lsResult = fecavAction
									.batchUndoInnerObtainFecavBill(new Request(
											CommonVars.getCurrUser()), list);
							if (lsResult != null && lsResult.size() > 0) {
								innerTableModel.deleteRows(list);
								// outerTableModel.addRows(lsResult);
							}
							showCount(innerTableModel.getList());
						}

					});

		}
		return btnUndoInnerObtain;
	}

	private void setState() {
		// this.btnInnerObtain.setVisible(jTabbedPane.getSelectedIndex()==0);
		// this.btnUndoInnerObtain.setVisible(jTabbedPane.getSelectedIndex()==1);
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查询");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgFecavBillQuery dg = new DgFecavBillQuery();
					dg.setFecavState(FecavState.INNER_OBTAIN);
					dg.setVisible(true);
					if (dg.isOk()) {
						List list = dg.getLsResult();
						initInnerTable(list);
						showCount(list);
					}
				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (innerTableModel == null) {
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							innerTableModel.getList());
					InputStream reportStream = FmOuterObtainFecavBill.class
							.getResourceAsStream("report/InnerObtainFecavBill.jasper");
					JasperPrint jasperPrint = null;

					try {
						jasperPrint = JasperFillManager.fillReport(
								reportStream, null, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes BtnModify
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (BtnEdit == null) {
			BtnEdit = new JButton();
			BtnEdit.setText("修改");
			BtnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (innerTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmInnerObtainFecavBill.this, "请选择你要修改的内部领单",
								"提示", JOptionPane.OK_OPTION);
						return;
					}
					FecavBill fecavBill = (FecavBill) innerTableModel
							.getCurrentRow();
					DgEditInnerObtainFecavBill dg = new DgEditInnerObtainFecavBill();
					dg.setFecavBill(fecavBill);
					dg.setVisible(true);
					if (dg.isOk()) {
						refresh();
					}
					showCount(innerTableModel.getList());
				}
			});
		}
		return BtnEdit;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setText("同步口岸");
			jCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Customs customs = null;
					if (innerTableModel.getCurrentRow() == null) {
						return;
					}
					if (jCheckBox.isSelected()) {
						FecavBill impexpciq = (FecavBill) innerTableModel
								.getCurrentRow();
						if (impexpciq.getImpExpCIQ() == null) {
							JOptionPane.showMessageDialog(
									FmInnerObtainFecavBill.this,
									"请选择一条有出口口岸的内部单，以便于同步", "提示",
									JOptionPane.OK_OPTION);
							jCheckBox.setSelected(false);
							return;
						} else {
							customs = impexpciq.getImpExpCIQ();
						}
						// 把出口口岸同步与其它内部单
						if (fecavAction.findCustomsInofFecavBill(new Request(
								CommonVars.getCurrUser()), customs)) {
							JOptionPane.showMessageDialog(
									FmInnerObtainFecavBill.this, "同步完成!", "提示",
									JOptionPane.OK_OPTION);
							jCheckBox.setSelected(false);
							refresh();
						}
					}
				}
			});
		}
		return jCheckBox;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
