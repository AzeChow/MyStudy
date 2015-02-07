/*
 * Created on 2005-5-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage;

import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;

/**
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscEmsPorCommFrom extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTable tbCommInfo = null;

	private JScrollPane jScrollPane = null;

	private JTableListModel tableModel = null;

	private DzscAction dzscAction = null;

	private DzscEmsPorHead dzscEmsPorHead = null;

	private boolean ok = false;

	private List returnList = null;

	// private boolean merger = true;
	// private Integer seqNum = null;
	// private String type = null; //类型

	private JPanel jPanel = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private JPanel jPanel1 = null;

	private JComboBox cbbQueryField = null;

	private JTextField tfQueryValue = null;

	private JButton btnQuery = null;

	private JToolBar jToolBar = null;

	private boolean isMaterial = false;

	private JButton jButton = null;

	private JButton jButton1 = null;

	public boolean isMaterial() {
		return isMaterial;
	}

	public void setMaterial(boolean isMaterial) {
		this.isMaterial = isMaterial;
	}

	public DgDzscEmsPorCommFrom() {
		super();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		initialize();
		setAlwaysOnTop(true);

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(659, 469);
		this.setContentPane(getJContentPane());
		this.setTitle("选择备案商品");
		tbCommInfo = new MultiSpanCellTable();
		tbCommInfo.getColumnModel().addColumnModelListener(
				new TableColumnModelListener() {
					/** Tells listeners that a column was added to the model. */
					public void columnAdded(TableColumnModelEvent e) {
					}

					/** Tells listeners that a column was removed from the model. */
					public void columnRemoved(TableColumnModelEvent e) {
					}

					/** Tells listeners that a column was repositioned. */
					public void columnMoved(TableColumnModelEvent e) {
					}

					/**
					 * Tells listeners that a column was moved due to a margin
					 * change.
					 */
					public void columnMarginChanged(ChangeEvent e) {
					}

					public void columnSelectionChanged(ListSelectionEvent e) {
						int[] columns = ((MultiSpanCellTable) tbCommInfo)
								.getSelectedColumns();
						int[] rows = ((MultiSpanCellTable) tbCommInfo)
								.getSelectedRows();
						if (columns.length < 1 || rows.length < 1) {
							return;
						}
						if (columns[0] >= 0 && columns[0] < 4) {
							tbCommInfo.setColumnSelectionInterval(1, 3);
							return;
						} else if (columns[0] >= 4 && columns[0] < 9) {
							tbCommInfo.setColumnSelectionInterval(4, 8);
							return;
						}
					}
				});
		jScrollPane.setViewportView(tbCommInfo);
	}

	public void setVisible(boolean b) {
		if (b) {
			List list = dzscAction
					.findMerger10ForDzscEmsPor(new Request(CommonVars
							.getCurrUser(), true), dzscEmsPorHead, isMaterial);
			this.initTable(list);
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
		}
		return jContentPane;
	}

	private void getResult() {
		List list = ((JTableListModel) getTbCommInfo().getModel())
				.getCurrentRows();
		list = this.dzscAction.saveDzscEmsPorImgExgBill(new Request(CommonVars
				.getCurrUser()), dzscEmsPorHead, isMaterial, list);
		setReturnList(list);
		if (tableModel.getCurrentRow() != null) {
			this.setOk(true);
		} else {
			this.setOk(false);
		}
		this.dispose();

	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbCommInfo() {
		if (tbCommInfo == null) {
			tbCommInfo = new JTable();
			tbCommInfo.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						getResult();
					}
				}
			});
		}
		return tbCommInfo;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbCommInfo());
		}
		return jScrollPane;
	}

	private void initTable(final List list) {
		tableModel = new AttributiveCellTableModel(
				(MultiSpanCellTable) tbCommInfo, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("4编码序号", "fourSeqNum", 80,
								Integer.class));
						list.add(addColumn("4商品编码", "fourComplex", 80));
						list.add(addColumn("4商品名称", "fourPtName", 100));
						list.add(addColumn("10编码序号", "tenSeqNum", 80,
								Integer.class));
						list.add(addColumn("10商品编码", "tenComplex.code", 80));
						list.add(addColumn("10商品名称", "tenPtName", 150));
						list.add(addColumn("10商品规格", "tenPtSpec", 150));
						list.add(addColumn("单位", "tenUnit.name", 40));
						return list;
					}
				});
		tbCommInfo
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		refreshTable();
		CommonQuery.getInstance().addCommonFilter(cbbQueryField, tfQueryValue,
				btnQuery, tableModel);
	}

	/**
	 * 
	 */
	private void refreshTable() {
		((MultiSpanCellTable) tbCommInfo).combineRows(new int[] { 1, 4 },
				new int[] { 1, 2, 3 });
	}

	/**
	 * @param head
	 *            The head to set.
	 */
	public void setDzscEmsPorHead(DzscEmsPorHead head) {
		this.dzscEmsPorHead = head;
	}

	/**
	 * @return Returns the ok.
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * @param ok
	 *            The ok to set.
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * @return Returns the returnList.
	 */
	public List getReturnList() {
		return returnList;
	}

	/**
	 * @param returnList
	 *            The returnList to set.
	 */
	public void setReturnList(List returnList) {
		this.returnList = returnList;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.add(getBtnOk(), null);
			jPanel.add(getBtnCancel(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getResult();
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setOk(false);
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(getCbbQueryField(), null);
			jPanel1.add(getTfQueryValue(), null);
			jPanel1.add(getBtnQuery(), null);
			jPanel1.add(getJButton(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbQueryField() {
		if (cbbQueryField == null) {
			cbbQueryField = new JComboBox();
			cbbQueryField.setName("jComboBox");
			cbbQueryField.setBounds(new java.awt.Rectangle(3,3,239,24));
		}
		return cbbQueryField;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfQueryValue() {
		if (tfQueryValue == null) {
			tfQueryValue = new JTextField();
			tfQueryValue.setName("jTextField");
			tfQueryValue.setBounds(new java.awt.Rectangle(250,3,305,24));
		}
		return tfQueryValue;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setBounds(new java.awt.Rectangle(560,3,58,24));
			btnQuery.setName("jButton2");
		}
		return btnQuery;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.add(getJPanel1());
			jToolBar.add(getJButton1());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(643,13,34,10));
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText(" ");
		}
		return jButton1;
	}
} // @jve:decl-index=0:visual-constraint="4,10"
