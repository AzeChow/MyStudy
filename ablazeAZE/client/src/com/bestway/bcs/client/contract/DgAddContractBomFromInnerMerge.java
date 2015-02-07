package com.bestway.bcs.client.contract;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.TempContractExg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.WindowConstants;

public class DgAddContractBomFromInnerMerge extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JButton jButton3 = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JTextField jTextField = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private boolean isFromInnerMerge = false;

	private List lsResult = new ArrayList(); // @jve:decl-index=0:

	private ContractExg contractExg = null; // @jve:decl-index=0:

	private JTableListModel tableModel = null;

	private ContractAction contractAction = null; // 合同

	public List getLsResult() {
		return lsResult;
	}

	public ContractExg getContractExg() {
		return contractExg;
	}

	public void setContractExg(ContractExg contractExg) {
		this.contractExg = contractExg;
	}

	public boolean isFromInnerMerge() {
		return isFromInnerMerge;
	}

	public void setFromInnerMerge(boolean isFromInnerMerge) {
		this.isFromInnerMerge = isFromInnerMerge;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgAddContractBomFromInnerMerge() {
		super();
		initialize();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			List list = contractAction.findBcsTenInnerMerge(new Request(
					CommonVars.getCurrUser()), isFromInnerMerge);
			initTable(list);
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(525, 326));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton3());
			jToolBar.add(getJPanel());
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
			jButton.setText("全选");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(true);
				}
			});
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
			jButton1.setText("全否");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(false);
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("确定");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List lsInnerMerge = getSelectedInnerMergeList();
					lsResult = contractAction.saveContractBomFromInnerMerge(
							new Request(CommonVars.getCurrUser()), contractExg,
							lsInnerMerge);
					dispose();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("取消");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(4, 4, 107, 21));
			jLabel.setText("选择商品(\",\"号分开)");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getJTextField(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(113, 3, 234, 23));
			jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					String[] keys = (jTextField.getText().trim() + e
							.getKeyChar()).trim().split(",");
					if (keys.length > 0) {
						List list = tableModel.getList();
						for (int i = 0; i < list.size(); i++) {
							BcsTenInnerMerge temp = (BcsTenInnerMerge) list
									.get(i);
							if (temp.getSeqNum() != null) {
								temp.setIsSelected(checkKey(keys, temp
										.getSeqNum().toString()));
							}
						}
						tableModel.setList(list);
					}
				}
			});
		}
		return jTextField;
	}

	private boolean checkKey(String[] keys, String key) {
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] != null && key != null
					&& keys[i].trim().equals(key.trim())) {
				return true;
			}
		}
		return false;
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
	 * 初始化表格
	 */
	private JTableListModel initTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 100));
				list.add(addColumn("归并序号", "seqNum", 60, Integer.class));
				list.add(addColumn("编码", "complex.code", 80));
				list.add(addColumn("商品名称", "name", 100));
				list.add(addColumn("商品规格", "spec", 100));
				list.add(addColumn("常用单位", "comUnit.name", 80));
				list.add(addColumn("第一法定单位", "complex.firstUnit.name", 120));
				list.add(addColumn("第二法定单位", "complex.secondUnit.name", 120));
				list.add(addColumn("单位重量", "unitWeight", 80));
				list.add(addColumn("单价", "price", 60));
				list.add(addColumn("币制", "curr.name", 60));
				list.add(addColumn("产销国", "country.name", 70));
				list.add(addColumn("加工费单价", "machPrice", 60));
				list.add(addColumn("类型", "scmCoi", 70));
				list.add(addColumn("主辅料", "type", 70));
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		jTable.getColumnModel().getColumn(14).setCellRenderer(
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
						if (value.equals(MaterielType.FINISHED_PRODUCT)) {
							returnValue = "成品";
						} else if (value.equals(MaterielType.MATERIEL)) {
							returnValue = "料件";
						}
						return returnValue;
					}
				});

		jTable.getColumnModel().getColumn(15).setCellRenderer(
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
						if (value.equals("0")) {
							returnValue = "辅料";
						} else if (value.equals("1")) {
							returnValue = "主料";
						}
						return returnValue;
					}
				});

		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		return tableModel;
	}

	/**
	 * 编辑列
	 */

	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;

		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				return null;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb
						.setSelected(Boolean.valueOf(value.toString())
								.booleanValue());
			}
			cb.setHorizontalAlignment(JLabel.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof BcsTenInnerMerge) {
				BcsTenInnerMerge temp = (BcsTenInnerMerge) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}

	}

	/**
	 * 获得选中的海关商品单据
	 */
	public List<BcsTenInnerMerge> getSelectedInnerMergeList() {
		List<BcsTenInnerMerge> newList = new ArrayList<BcsTenInnerMerge>();
		if (this.tableModel == null) {
			return newList;
		}
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof BcsTenInnerMerge) {
				BcsTenInnerMerge temp = (BcsTenInnerMerge) list.get(i);
				if (temp.getIsSelected().booleanValue() == true) {
					newList.add(temp);
				}
			}
		}
		return newList;
	}

	/**
	 * 选中所有 or 清除所有选择
	 */
	private void selectAll(boolean isSelected) {
		if (this.tableModel == null) {
			return;
		}
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof BcsTenInnerMerge) {
				BcsTenInnerMerge temp = (BcsTenInnerMerge) list.get(i);
				temp.setIsSelected(new Boolean(isSelected));
			}
		}
		tableModel.updateRows(list);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
