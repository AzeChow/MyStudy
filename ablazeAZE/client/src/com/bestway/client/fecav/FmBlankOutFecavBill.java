package com.bestway.client.fecav;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.FecavState;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmBlankOutFecavBill extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JScrollPane spBlankOut = null;

	private JTable tbBlankOut = null;

	private JButton btnRefresh = null;

	private JButton btnExit = null;

	private JTableListModel blankOutTableModel = null;

	private FecavAction fecavAction = null;

	private JButton btnBlankOutObtain = null;

	private JButton btnSearch = null;

	private JButton btnPrint = null;

	private JButton btnUndoBlankOutObtain = null;

	private JButton jButton = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmBlankOutFecavBill() {
		super();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(597, 366));
		this.setContentPane(getJContentPane());
		this.setTitle("核销单遗失作废管理");
		List list = fecavAction.findIsBlankOutFecavBill(new Request(CommonVars
				.getCurrUser()), "", new ArrayList());
		initBlankOutTable(list);

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
			jContentPane.add(getSpBlankOut(), java.awt.BorderLayout.CENTER);
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
			jToolBar.add(getBtnBlankOutObtain());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnUndoBlankOutObtain());
			jToolBar.add(getJButton());
			jToolBar.add(getBtnSearch());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpBlankOut() {
		if (spBlankOut == null) {
			spBlankOut = new JScrollPane();
			spBlankOut.setViewportView(getTbBlankOut());
		}
		return spBlankOut;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBlankOut() {
		if (tbBlankOut == null) {
			tbBlankOut = new JTable();
			tbBlankOut.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() > 1) {
						List list = new ArrayList();
						list.add(blankOutTableModel.getCurrentRow());
						if (list == null || list.isEmpty()) {
							JOptionPane.showMessageDialog(
									FmBlankOutFecavBill.this, "请选择要修改的数据！",
									"提示！", 1);
							return;
						}
						DgBlankOutFecavBill dg = new DgBlankOutFecavBill();
						dg.setLsFecavBill(list);
						FecavBill fecavBill = (FecavBill) list.get(0);
						dg.getTfReason().setText(fecavBill.getBlankOutReason());
						dg.getCbbCheckOutData().setDate(
								fecavBill.getCheckoutdate());
						dg.setVisible(true);
						if (dg.isOk()) {
							List lsResult = dg.getLsFecavBill();
							if (lsResult != null && lsResult.size() > 0) {

								blankOutTableModel.updateRows(lsResult);
							}
						}
						// initBlankOutTable(list);

					}
				}

			});
			// tbBlankOut.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		}
		return tbBlankOut;
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

					List list = fecavAction.findIsBlankOutFecavBill(
							new Request(CommonVars.getCurrUser()), "",
							new ArrayList());
					initBlankOutTable(list);

				}
			});
		}
		return btnRefresh;
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
					FmBlankOutFecavBill.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	private void initBlankOutTable(List list) {
		JTableListModel.dataBind(tbBlankOut, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = (List<Object>) (new Vector());
						list.add(addColumn("核销单号 ", "code", 200));
						list.add(addColumn("出口日期", "exportDate", 100));
						list.add(addColumn("领单日期", "innerObtainDate", 100));
						list.add(addColumn("领单人", "innerObtain", 100));
						list.add(addColumn("遗失作废日期", "blankOutDate", 200));
						list.add(addColumn("作废核销日期", "checkoutdate", 80));
						list.add(addColumn("遗失作废原因", "blankOutReason", 300));
						return list;
					}
				});
		// TableColumn column = this.tbBlankOut.getColumnModel().getColumn(10);
		// column.setCellRenderer(new DefaultTableCellRenderer() {
		// public Component getTableCellRendererComponent(JTable table,
		// Object value, boolean isSelected, boolean hasFocus,
		// int row, int column) {
		// super.getTableCellRendererComponent(table, value, isSelected,
		// hasFocus, row, column);
		// int state = -1;
		// if (value != null) {
		// state = Integer.parseInt(value.toString());
		// }
		// this.setText(CommonVars.getFecavState(state));
		// return this;
		// }
		// });
		blankOutTableModel = (JTableListModel) tbBlankOut.getModel();
		// CommonQuery.getInstance().addCommonFilter(this.cbbSearchField,
		// this.tfFieldValue, this.btnSearch, this.blankOutTableModel);
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBlankOutObtain() {
		if (btnBlankOutObtain == null) {
			btnBlankOutObtain = new JButton();
			btnBlankOutObtain.setText("新增");
			btnBlankOutObtain
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = FecavQuery.getInstance()
									.findNotBlankOutFecavBill();
							if (list == null || list.isEmpty()) {
								return;
							}
							DgBlankOutFecavBill dg = new DgBlankOutFecavBill();
							FecavBill fecavBill = (FecavBill) list.get(0);
							dg.getTfReason().setText(
									fecavBill.getBlankOutReason());
							dg.getCbbCheckOutData().setDate(
									fecavBill.getCheckoutdate());
							dg.setLsFecavBill(list);
							dg.setVisible(true);
							if (dg.isOk()) {
								List lsResult = dg.getLsFecavBill();
								if (lsResult != null && lsResult.size() > 0) {

									blankOutTableModel.addRows(lsResult);
								}
							}
							// initBlankOutTable(list);
						}
					});

		}
		return btnBlankOutObtain;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndoBlankOutObtain() {
		if (btnUndoBlankOutObtain == null) {
			btnUndoBlankOutObtain = new JButton();
			btnUndoBlankOutObtain.setText("取消作废");
			btnUndoBlankOutObtain
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (blankOutTableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmBlankOutFecavBill.this,
										"请选择你要取消作废的数据", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmBlankOutFecavBill.this, "确定要取消作废的数据??",
									"提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							List list = blankOutTableModel.getCurrentRows();
							List lsResult = fecavAction
									.undoBlankOutFecavBill(new Request(
											CommonVars.getCurrUser()), list);
							if (lsResult != null && lsResult.size() > 0) {
								blankOutTableModel.deleteRows(list);
							}
						}
					});
		}
		return btnUndoBlankOutObtain;
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
					dg.setBlankOut(true);
					dg.setVisible(true);
					if (dg.isOk()) {
						List list = dg.getLsResult();
						initBlankOutTable(list);
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
					if (blankOutTableModel == null
							|| blankOutTableModel.getList().size() == 0) {
						JOptionPane.showMessageDialog(FmBlankOutFecavBill.this,
								"        没有数据可打印！", "没有数据！", 1);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							blankOutTableModel.getList());
					InputStream reportStream = FmUseFecavBill.class
							.getResourceAsStream("report/FecavBillReport.jasper");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("reportTitle", "外汇核销单遗失作废");
					JasperPrint jasperPrint = null;
					try {
						jasperPrint = JasperFillManager.fillReport(
								reportStream, parameters, ds);
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("修改");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					List list = new ArrayList();
					list.add(blankOutTableModel.getCurrentRow());
					if (list == null || list.isEmpty()) {
						JOptionPane.showMessageDialog(FmBlankOutFecavBill.this,
								"请选择要修改的数据！", "提示！", 1);
						return;
					}
					DgBlankOutFecavBill dg = new DgBlankOutFecavBill();
					dg.setLsFecavBill(list);
					FecavBill fecavBill = (FecavBill) list.get(0);
					dg.getTfReason().setText(fecavBill.getBlankOutReason());
					dg.getCbbCheckOutData()
							.setDate(fecavBill.getCheckoutdate());
					dg.setVisible(true);
					if (dg.isOk()) {
						List lsResult = dg.getLsFecavBill();
						if (lsResult != null && lsResult.size() > 0) {

							blankOutTableModel.updateRows(lsResult);
						}
					}
					// initBlankOutTable(list);

				}
			});
		}
		return jButton;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
