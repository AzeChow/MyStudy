/*
 * Created on 2005-7-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.invoice;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.TableColorRender;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.InvoiceState;
import com.bestway.invoice.action.InvoiceAction;
import com.bestway.invoice.entity.Invoice;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class FmInvoice extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JToolBar jToolBar1 = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;

	private JTable tbDraft = null;
	private JScrollPane jScrollPane = null;
	private JButton btnAdd = null;
	private JButton btnEdit = null;
	private JButton btnDelete = null;
	private JButton btnCancel = null;
	private JButton btnCav = null;
	private JButton btnExit = null;
	private InvoiceAction invoiceAction = null;
	private JTableListModel tableModel = null;
	private JButton btnRollBack = null;
	private Invoice invoice;
	private JTabbedPane jTabbedPane = null;
	private JPanel pnDraft = null;
	private JPanel pnUsed = null;
	private JPanel pnCav = null;
	private JPanel pnCanceled = null;
	private JScrollPane jScrollPane1 = null;
	private JTable tbUsed = null;
	private JScrollPane jScrollPane2 = null;
	private JTable tbCav = null;
	private JScrollPane jScrollPane3 = null;
	private JTable tbCanceled = null;
	private JLabel jLabel1 = null;
	private JTextField jtfStartInvoiceCode = null;
	private JLabel jLabel2 = null;
	private JTextField jtfEndInvoiceCode = null;
	private JButton jbtFind = null;
	private String startInvoiceCode = ""; // @jve:decl-index=0:
	private String endInvoiceCode = "";

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	/**
	 * This is the default constructor
	 */
	public FmInvoice() {
		super();

		initialize();
		invoiceAction = (InvoiceAction) CommonVars.getApplicationContext()
				.getBean("invoiceAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("出口专用发票管理");
		this.setSize(638, 347);
		this.setContentPane(getJContentPane());
		this.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
			public void internalFrameOpened(
					javax.swing.event.InternalFrameEvent e) {
				// List list = invoiceAction.findInvoice(new Request(
				// CommonVars.getCurrUser()));
				// initTable(list);
				retriveInvoiceData();
				setState();
			}
		});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			// jContentPane.setLayout(new java.awt.BorderLayout());
			// jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			// jContentPane.add(getJTabbedPane(),java.awt.BorderLayout.CENTER);
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new GridLayout(2, 1));
			jPanel.add(getJToolBar());
			jPanel.add(getJToolBar1());
		}
		return jPanel;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel2 = new JLabel("结束票号");
			jLabel2.setBounds(new java.awt.Rectangle(173, 3, 52, 25));
			jLabel1 = new JLabel("开始票号");
			jLabel1.setBounds(new java.awt.Rectangle(2, 3, 53, 25));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			// jPanel1.add(getJtfVersionCode(), null);
			// jPanel1.add(getJbnVersionCode(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getJtfStartInvoiceCode(), null);
			// jPanel1.add(getJbtStartInvoiceCode(), null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(getJtfEndInvoiceCode(), null);
			// jPanel1.add(getJbtEndInvoiceCode(), null);
			jPanel1.add(getJbtFind(), null);
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
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			// jToolBar.add(getBtnUse());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnCav());
			jToolBar.add(getBtnRollBack());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getJPanel1());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbDraft() {
		if (tbDraft == null) {
			tbDraft = new JTable();
			tbDraft.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbDraft
									.getModel();
							if (tableModel == null) {
								return;
							}

							try {
								if (tableModel.getCurrentRow() != null) {
									setState();
								}
							} catch (Exception cx) {

							}
						}
					});
			tbDraft.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {

						DgInvoice dg = new DgInvoice();

						Invoice invoice = (Invoice) tableModel.getCurrentRow();

						dg.setInvoice(invoice);

						if (StringUtils.isBlank(invoice.getId())) {

							return;

						}

						System.out.println(invoice);

						if (invoice.getState() == InvoiceState.CANCEL_AFTER_VERIFICATION
								|| invoice.getState() == InvoiceState.CANCELED
								|| invoice.getState() == InvoiceState.USED) {

							dg.setDataState(DataState.BROWSE);

						} else {

							dg.setDataState(DataState.EDIT);

						}
						dg.setVisible(true);
						invoice = dg.getInvoice();
						if (invoice != null) {
							tableModel.updateRow(invoice);
							tableModel.setTableSelectedRow(invoice);
						} else {
							tableModel.updateRow(invoice);
							Invoice invoice1 = invoiceAction.checkInvoice(
									new Request(CommonVars.getCurrUser()),
									invoice);
							invoice1.setState(InvoiceState.USED);
						}
					}
				}
			});
		}
		return tbDraft;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbDraft());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgAutoMakeInvoice dg = new DgAutoMakeInvoice();
					dg.setVisible(true);
					List list = dg.getLsResult();
					if (list != null) {
						tableModel.addRows(list);
					}
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes jbtFind
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmInvoice.this,
								"请选择要修改的发票记录", "提示", JOptionPane.OK_OPTION);
						return;
					}
					DgInvoice dg = new DgInvoice();
					dg.setInvoice((Invoice) tableModel.getCurrentRow());
					dg.setDataState(DataState.EDIT);
					dg.setVisible(true);
					Invoice invoice = dg.getInvoice();
					if (invoice != null) {
						tableModel.updateRow(invoice);
					}
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmInvoice.this,
								"请选择要删除的发票记录", "提示", JOptionPane.OK_OPTION);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmInvoice.this,
							"你确定要删除此发票吗", "提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					List list = ((JTableListModel) tbDraft.getModel())
							.getCurrentRows();
					invoiceAction.deleteAll(
							new Request(CommonVars.getCurrUser()), list);
					tableModel.deleteRows(list);
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	// private JButton getBtnUse() {
	// if (btnUse == null) {
	// btnUse = new JButton();
	// btnUse.setText("使用");
	// btnUse.addActionListener(new java.awt.event.ActionListener() {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// if (tableModel.getCurrentRow() == null) {
	// JOptionPane.showMessageDialog(FmInvoice.this,
	// "请选择要使用的发票记录", "提示", JOptionPane.OK_OPTION);
	// return;
	// }
	// if (JOptionPane.showConfirmDialog(FmInvoice.this,
	// "你确定要使用此发票吗", "提示", JOptionPane.OK_CANCEL_OPTION) !=
	// JOptionPane.OK_OPTION) {
	// return;
	// }
	// Invoice invoice = (Invoice) tableModel.getCurrentRow();
	// invoice = invoiceAction.useInvoice(new Request(CommonVars
	// .getCurrUser()), invoice);
	// tableModel.deleteRow(invoice);
	// }
	// });
	// }
	// return btnUse;
	// }
	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("作废");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmInvoice.this,
								"请选择要使用的发票记录", "提示", JOptionPane.OK_OPTION);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmInvoice.this,
							"你确定要作废此发票吗", "提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					Invoice invoice = (Invoice) tableModel.getCurrentRow();
					DgMemo dg = new DgMemo();
					dg.setInvoice(invoice);
					dg.setVisible(true);
					invoice = dg.getInvoice();
					tableModel.deleteRow(invoice);
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCav() {
		if (btnCav == null) {
			btnCav = new JButton();
			btnCav.setText("核销");
			btnCav.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmInvoice.this,
								"请选择要使用的发票记录", "提示", JOptionPane.OK_OPTION);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmInvoice.this,
							"你确定要核销此发票吗", "提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					Invoice invoice = (Invoice) tableModel.getCurrentRow();
					invoice = invoiceAction.cancelAfterVerificationInvoice(
							new Request(CommonVars.getCurrUser()), invoice);
					tableModel.deleteRow(invoice);
				}
			});
		}
		return btnCav;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmInvoice.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 设置状态
	 * 
	 * @param isCancelAfterVerification
	 */

	// private void setState() {
	// Invoice invoice = (Invoice) tableModel.getCurrentRow();
	// if(invoice == null){
	// return ;
	// }
	// if(invoice.getState().equals(InvoiceState.CANCELED)){
	// boolean isCancel=true;
	// btnUse.setEnabled(!isCancel);
	// btnEdit.setEnabled(!isCancel);
	// btnDelete.setEnabled(!isCancel);
	// btnCancel.setEnabled(!isCancel);
	// btnCav.setEnabled(!isCancel);
	//
	// }
	// else
	// if(invoice.getState().equals(InvoiceState.CANCEL_AFTER_VERIFICATION)){
	// boolean isCancelAfterVerification=true;
	// btnUse.setEnabled(!isCancelAfterVerification);
	// btnEdit.setEnabled(!isCancelAfterVerification);
	// btnDelete.setEnabled(!isCancelAfterVerification);
	// btnCancel.setEnabled(!isCancelAfterVerification);
	// btnCav.setEnabled(!isCancelAfterVerification);
	// }
	// else if(invoice.getState().equals(InvoiceState.USED)){
	// boolean isUse=true;
	// btnEdit.setEnabled(!isUse);
	// btnDelete.setEnabled(!isUse);
	// btnUse.setEnabled(!isUse);
	// btnCancel.setEnabled(isUse);
	// btnCav.setEnabled(isUse);
	// }
	// else if(invoice.getState().equals(InvoiceState.DRAFT)){
	// boolean isDraft=true;
	// btnCancel.setEnabled(!isDraft);
	// btnCav.setEnabled(!isDraft);
	// btnUse.setEnabled(isDraft);
	// btnEdit.setEnabled(isDraft);
	// btnDelete.setEnabled(isDraft);
	// }
	// }
	private void setState() {
		// Invoice invoice = (Invoice) tableModel.getCurrentRow();
		// if (invoice == null) {
		// return;
		// }
		// this.btnEdit.setEnabled(invoice.getState().equals(InvoiceState.DRAFT));
		// this.btnDelete
		// .setEnabled(invoice.getState().equals(InvoiceState.DRAFT));
		// this.btnUse.setEnabled(invoice.getState().equals(InvoiceState.DRAFT));
		// this.btnCancel.setEnabled(invoice.getState().equals(InvoiceState.USED));
		// this.btnCav.setEnabled(invoice.getState().equals(InvoiceState.USED));
		// this.btnRollBack.setEnabled(invoice.getState()
		// .equals(InvoiceState.USED));
		int currentIndex = jTabbedPane.getSelectedIndex();
		this.btnAdd.setVisible(currentIndex == 0);
		this.btnEdit.setVisible(currentIndex == 0);
		this.btnDelete.setVisible(currentIndex == 0);
		// this.btnUse.setVisible(currentIndex==0);
		// this.btnUse.setVisible(false);
		this.btnCancel.setVisible(currentIndex == 0);
		this.btnCav.setVisible(currentIndex == 1);
		this.btnRollBack.setVisible(currentIndex == 2 || currentIndex == 3);

	}

	private JTable getCurrentTable() {
		switch (jTabbedPane.getSelectedIndex()) {
		case 0:
			return tbDraft;
		case 1:
			return tbUsed;
		case 2:
			return tbCav;
		case 3:
			return tbCanceled;
		default:
			return null;
		}
	}

	private void initTable(List list) {
		JTable currentTable = getCurrentTable();
		if (currentTable == null) {
			return;
		}
		tableModel = new JTableListModel(currentTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = (List<Object>) (new Vector());
						list.add(addColumn("版次号", "versionCode", 120));
						list.add(addColumn("发票号", "invoiceCode", 200));
						list.add(addColumn("报关单号", "customsDeclaredCode", 150));
						list.add(addColumn("金额", "money", 100));
						list.add(addColumn("录入日期", "beginDate", 100));
						list.add(addColumn("截止日期", "endDate", 100));
						list.add(addColumn("状态", "state", 50));
						list.add(addColumn("作废原因", "memo", 300));
						return list;
					}
				});
		TableColumn column = currentTable.getColumnModel().getColumn(7);
		TableColorRender.setTableRowColor(currentTable,
				tableModel.getRowCount() - 1);
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
				switch (state) {
				case InvoiceState.DRAFT:
					this.setText("领用");
					break;
				case InvoiceState.USED:
					this.setText("使用");
					break;
				case InvoiceState.CANCELED:
					this.setText("作废");
					break;
				case InvoiceState.CANCEL_AFTER_VERIFICATION:
					this.setText("核销");
					break;
				}
				return this;
			}
		});
	}

	/**
	 * This method initializes btnRollBack
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRollBack() {
		if (btnRollBack == null) {
			btnRollBack = new JButton();
			btnRollBack.setText("回卷");
			btnRollBack.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmInvoice.this,
								"请选择要回卷的发票记录", "提示", JOptionPane.OK_OPTION);
						return;
					}

					if (e.getSource() == btnRollBack) {

						if (JOptionPane.showConfirmDialog(FmInvoice.this,
								"你确定要回卷此发票吗", "提示",
								JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
							return;
						}

					}

					Invoice invoice = (Invoice) tableModel.getCurrentRow();
					if (jTabbedPane.getSelectedIndex() == 2)
						invoice = invoiceAction.rollBackInvoice(new Request(
								CommonVars.getCurrUser()), invoice,
								InvoiceState.USED);
					else if (jTabbedPane.getSelectedIndex() == 3)
						invoice = invoiceAction.rollBackInvoice(new Request(
								CommonVars.getCurrUser()), invoice,
								InvoiceState.DRAFT);
					tableModel.deleteRow(invoice);
					// setState();

					// boolean isDraft=true;
					// btnCancel.setEnabled(!isDraft);
					// btnCav.setEnabled(!isDraft);
					// btnUse.setEnabled(isDraft);
					// btnEdit.setEnabled(isDraft);
					// btnDelete.setEnabled(isDraft);
					// tableModel.updateRow(invoice);

				}
			});
		}
		return btnRollBack;
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
			jTabbedPane.addTab("未使用", null, getPnDraft(), null);
			jTabbedPane.addTab("已使用", null, getPnUsed(), null);
			jTabbedPane.addTab("已核销", null, getPnCav(), null);
			jTabbedPane.addTab("已作废", null, getPnCanceled(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							retriveInvoiceData();
							setState();
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnDraft() {
		if (pnDraft == null) {
			pnDraft = new JPanel();
			pnDraft.setLayout(new BorderLayout());
			pnDraft.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return pnDraft;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnUsed() {
		if (pnUsed == null) {
			pnUsed = new JPanel();
			pnUsed.setLayout(new BorderLayout());
			pnUsed.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return pnUsed;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnCav() {
		if (pnCav == null) {
			pnCav = new JPanel();
			pnCav.setLayout(new BorderLayout());
			pnCav.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return pnCav;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnCanceled() {
		if (pnCanceled == null) {
			pnCanceled = new JPanel();
			pnCanceled.setLayout(new BorderLayout());
			pnCanceled.add(getJScrollPane3(), java.awt.BorderLayout.CENTER);
		}
		return pnCanceled;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbUsed());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbUsed() {
		if (tbUsed == null) {
			tbUsed = new JTable();
		}
		return tbUsed;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbCav());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbCav() {
		if (tbCav == null) {
			tbCav = new JTable();
		}
		return tbCav;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getTbCanceled());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbCanceled() {
		if (tbCanceled == null) {
			tbCanceled = new JTable();
		}
		return tbCanceled;
	}

	private List getDataSource() {
		switch (jTabbedPane.getSelectedIndex()) {
		case 0:
			return invoiceAction.findInvoiceByState(
					new Request(CommonVars.getCurrUser()), InvoiceState.DRAFT,
					startInvoiceCode, endInvoiceCode);
		case 1:
			return invoiceAction.findInvoiceByState(
					new Request(CommonVars.getCurrUser()), InvoiceState.USED,
					startInvoiceCode, endInvoiceCode);
		case 2:
			return invoiceAction.findInvoiceByState(
					new Request(CommonVars.getCurrUser()),
					InvoiceState.CANCEL_AFTER_VERIFICATION, startInvoiceCode,
					endInvoiceCode);
		case 3:
			return invoiceAction.findInvoiceByState(
					new Request(CommonVars.getCurrUser()),
					InvoiceState.CANCELED, startInvoiceCode, endInvoiceCode);
		default:
			return new ArrayList();
		}
	}

	private void retriveInvoiceData() {
		List list = this.getDataSource();
		this.initTable(list);
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJtfStartInvoiceCode() {
		if (jtfStartInvoiceCode == null) {
			jtfStartInvoiceCode = new JTextField();
			jtfStartInvoiceCode.setBounds(new Rectangle(55, 3, 113, 24));
		}
		return jtfStartInvoiceCode;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJtfEndInvoiceCode() {
		if (jtfEndInvoiceCode == null) {
			jtfEndInvoiceCode = new JTextField();
			jtfEndInvoiceCode.setBounds(new Rectangle(225, 3, 113, 24));
		}
		return jtfEndInvoiceCode;
	}

	/**
	 * This method initializes jbtFind
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJbtFind() {
		if (jbtFind == null) {
			jbtFind = new JButton();
			jbtFind.setBounds(new Rectangle(346, 3, 63, 24));
			jbtFind.setText("查询");
			jbtFind.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					startInvoiceCode = jtfStartInvoiceCode.getText();
					endInvoiceCode = jtfEndInvoiceCode.getText();
					retriveInvoiceData();
					startInvoiceCode = "";
					endInvoiceCode = "";

				}
			});
		}
		return jbtFind;
	}

}
