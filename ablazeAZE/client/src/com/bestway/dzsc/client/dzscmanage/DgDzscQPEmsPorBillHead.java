package com.bestway.dzsc.client.dzscmanage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.qp.entity.DzscQPEmsPorBillHead;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgDzscQPEmsPorBillHead extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JButton btnSelectAll = null;

	private JButton btnNotSelected = null;

	private JButton btnOk = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JComboBox jComboBox = null;

	private JTextField tfQueryValue = null;

	private JButton btnSearch = null;

	private DzscAction dzscAction = null;

	private List lsResult = new ArrayList();

	private JTableListModel tableModel = null;

	private JCheckBox cbIsOverWrite = null;
	/**
	 * 已经存在的手册号 
	 */
	private List lsExistEmsNo=new ArrayList();  //  @jve:decl-index=0:
	
	public void setLsExistEmsNo(List lsExistEmsNo) {
		this.lsExistEmsNo = lsExistEmsNo;
	}
	
	public List getLsResult() {
		return lsResult;
	}

	public void setLsResult(List lsResult) {
		this.lsResult = lsResult;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgDzscQPEmsPorBillHead() {
		super();
		initialize();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(657, 502));
		this.setTitle("通关备案表头");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());

	}

	public void setVisible(boolean b) {
		if (b) {
			this.showData();
		}
		super.setVisible(b);
	}

	private void showData() {
		List list = dzscAction.findDzscQPEmsPorBillHead(new Request(CommonVars
				.getCurrUser(), true));
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("是否选择", "isSelected", 50));
				list.add(addColumn("加工单位编码", "machCode", 80));
				list.add(addColumn("加工单位名称", "machName", 80));
				list.add(addColumn("手册编号", "emsNo", 100));
				list.add(addColumn("企业内部编号", "copTrNo", 80));
				list.add(addColumn("经营单位编码", "tradeCode", 80));
				list.add(addColumn("经营单位名称", "tradeName", 80));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		TableColumn column = this.getJTable().getColumnModel().getColumn(1);
		column.setCellRenderer(new TableCheckBoxRender());
		column.setCellEditor(new CheckBoxEditor(new JCheckBox()));
		CommonQuery.getInstance().addCommonFilter(jComboBox, tfQueryValue,
				btnSearch, tableModel);
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						List list=lsExistEmsNo;
						String emsNo=(value == null ? "" : value.toString().trim());
						if(list.contains(emsNo)){
							this.setForeground(Color.RED);
						}else{
							this.setForeground(table.getForeground());
						}						
						return this;
					}					
				});
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
			List<Object> objs = tableModel.getCurrentRows();
			for (Object obj : objs) {
				if (obj instanceof DzscQPEmsPorBillHead) {
					DzscQPEmsPorBillHead temp = (DzscQPEmsPorBillHead) obj;
					temp.setIsSelected(new Boolean(cb.isSelected()));
					tableModel.updateRow(obj);
				}
			}
			fireEditingStopped();
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
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jToolBar.add(getBtnSelectAll());
			jToolBar.add(getBtnNotSelected());
			jToolBar.add(getBtnOk());
			jToolBar.add(getJPanel());
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setText("全选");
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(true);
				}
			});
		}
		return btnSelectAll;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNotSelected() {
		if (btnNotSelected == null) {
			btnNotSelected = new JButton();
			btnNotSelected.setText("全部取消选择");
			btnNotSelected
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							selectAll(false);
						}
					});
		}
		return btnNotSelected;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = tableModel.getList();
					for (int i = 0; i < list.size(); i++) {
						DzscQPEmsPorBillHead dzscQPEmsPorHead = (DzscQPEmsPorBillHead) list
								.get(i);
						if (dzscQPEmsPorHead.getIsSelected()) {
							lsResult.add(dzscQPEmsPorHead);
						}
					}
					dispose();
				}
			});
		}
		return btnOk;
	}

	private void selectAll(boolean isSelected) {
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			DzscQPEmsPorBillHead dzscQPEmsPorHead = (DzscQPEmsPorBillHead) list
					.get(i);
			dzscQPEmsPorHead.setIsSelected(isSelected);
		}
		// tableModel.updateRows(list);
		tableModel.setList(list);

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(97, 3, 50, 24));
			jLabel.setText("查询条件");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(getTfQueryValue(), null);
			jPanel.add(getBtnSearch(), null);
			jPanel.add(getCbIsOverWrite(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(151, 3, 106, 24));
		}
		return jComboBox;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfQueryValue() {
		if (tfQueryValue == null) {
			tfQueryValue = new JTextField();
			tfQueryValue.setBounds(new Rectangle(261, 2, 101, 26));
		}
		return tfQueryValue;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setBounds(new Rectangle(364, 2, 65, 26));
			btnSearch.setText("查询");
		}
		return btnSearch;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbIsOverWrite() {
		if (cbIsOverWrite == null) {
			cbIsOverWrite = new JCheckBox();
			cbIsOverWrite.setBounds(new Rectangle(7, 4, 81, 22));
			cbIsOverWrite.setSelected(true);
			cbIsOverWrite.setText("覆盖导入");
		}
		return cbIsOverWrite;
	}
	
	/**
	 * 是否覆盖导入
	 * @return
	 */
	public boolean isOverWrite(){
		return cbIsOverWrite.isSelected();
	}

} // @jve:decl-index=0:visual-constraint="10,10"
