package com.bestway.dzsc.client.dzscmanage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.TempDzscProductVersionInfo;
import com.bestway.dzsc.innermerge.action.DzscInnerMergeAction;
import com.bestway.dzsc.innermerge.entity.DzscCustomsBomExg;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgDzscAutoImpEmsBomFromCustomsBom extends JDialogBase {

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JScrollPane jScrollPane = null;

	private JTable tbUnitWaste = null;

	private JToolBar jJToolBarBar = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbBomExg = null;

	private JButton btnConfirm = null;

	private JButton btnCancel = null;

	private DzscAction dzscAction = null;

	private DzscInnerMergeAction dzscInnerMergeAction = null;

	private JTableListModel tableModelBomBill = null;

	private JTableListModel tableModelExg = null;

	private DzscEmsExgBill exgBill = null;

	public DzscEmsExgBill getExgBill() {
		return exgBill;
	}

	public void setExgBill(DzscEmsExgBill exgBill) {
		this.exgBill = exgBill;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgDzscAutoImpEmsBomFromCustomsBom() {
		super();
		initialize();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		dzscInnerMergeAction = (DzscInnerMergeAction) CommonVars
				.getApplicationContext().getBean("dzscInnerMergeAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(651, 446));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("从报关单耗导入通关备案单耗");
		this.setContentPane(getJContentPane());

	}

	public void setVisible(boolean b) {
		if (b) {
			showExgData();
			if (tableModelExg.getCurrentRow() == null) {
				return;
			}
			DzscCustomsBomExg exg = (DzscCustomsBomExg) tableModelExg
					.getCurrentRow();
			// lbExgInfo.setText("当前成品序号为:" + exg.getSeqNum()
			// + " 名称为:" + exg.getName());
			List list = dzscInnerMergeAction.findDzscCustomsBomDetail(
					new Request(CommonVars.getCurrUser()), exg);
			initTableBomBill(list);
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
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(2);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setDividerLocation(180);
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
			jPanel.add(getJJToolBarBar(), BorderLayout.SOUTH);
			jPanel.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbUnitWaste());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbUnitWaste() {
		if (tbUnitWaste == null) {
			tbUnitWaste = new JTable();
		}
		return tbUnitWaste;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getBtnConfirm());
			jJToolBarBar.add(getBtnCancel());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbBomExg());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBomExg() {
		if (tbBomExg == null) {
			tbBomExg = new JTable();
			tbBomExg.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModelExg == null)
								return;
							if (tableModelExg.getCurrentRow() == null)
								return;
							DzscCustomsBomExg exg = (DzscCustomsBomExg) tableModelExg
									.getCurrentRow();
							// lbExgInfo.setText("当前成品序号为:" + exg.getSeqNum()
							// + " 名称为:" + exg.getName());
							List list = dzscInnerMergeAction
									.findDzscCustomsBomDetail(new Request(
											CommonVars.getCurrUser()), exg);
							initTableBomBill(list);
						}
					});
		}
		return tbBomExg;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnConfirm() {
		if (btnConfirm == null) {
			btnConfirm = new JButton();
			btnConfirm.setText("确定");
			btnConfirm.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelBomBill == null
							|| tableModelBomBill.getList().size() <= 0) {
						JOptionPane.showMessageDialog(
								DgDzscAutoImpEmsBomFromCustomsBom.this,
								"当前成品没有单耗资料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List list = tableModelBomBill.getList();
					dzscAction.saveDzscEmsBomBillByCustomsBom(new Request(
							CommonVars.getCurrUser(), true), exgBill, list);
					dispose();
				}
			});
		}
		return btnConfirm;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	private void initTableExgBill(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("归并序号", "tenSeqNum", 100, Integer.class));
				list.add(addColumn("海关编码", "complex.code", 100));
				list.add(addColumn("商品名称", "name", 100));
				list.add(addColumn("型号规格", "spec", 100));
				list.add(addColumn("单位", "unit.name", 80));
				return list;
			}
		};
		tableModelExg = new JTableListModel(tbBomExg, list,
				jTableListModelAdapter);

	}

	private void initTableBomBill(List list) {
		tableModelBomBill = new JTableListModel(tbUnitWaste, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("归并序号", "tenSeqNum", 100,
								Integer.class));
						list.add(addColumn("海关编码", "complex.code", 100));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("单位", "unit.name", 80));
						list.add(addColumn("单耗", "unitWare", 80));
						list.add(addColumn("损耗率%", "ware", 80));
						list.add(addColumn("单项用量", "unitDosage", 80));
						return list;
					}
				});
	}

	private void showExgData() {
		List list = this.dzscAction.findDzscCustomsBomExg(new Request(
				CommonVars.getCurrUser(), true), exgBill);
		initTableExgBill(list);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
