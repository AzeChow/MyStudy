package com.bestway.common.client.materialbase;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.TempRepeatMateriel;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgRepeatMateriel extends JDialogBase {

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

	private MaterialManageAction materialManageAction = null;

	private int bomStructureType = -1;

	private List lsResult = new ArrayList();

	private Integer version = null;

	private JTableListModel tableModel = null;

	public int getBomStructureType() {
		return bomStructureType;
	}

	public void setBomStructureType(int bomStructureType) {
		this.bomStructureType = bomStructureType;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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
	public DgRepeatMateriel() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(657, 502));
		this.setTitle("重复物料");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());

	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			this.showData();
		}
		super.setVisible(b);
	}

	private void showData() {
		List list = materialManageAction.findRepeatMaterial(new Request(
				CommonVars.getCurrUser(), true));
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			@Override
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("是否选择", "isSelected", 50));
				list.add(addColumn("料号", "ptNo", 100));

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

		@Override
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
			cb.setHorizontalAlignment(SwingConstants.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		@Override
		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof TempRepeatMateriel) {
				TempRepeatMateriel temp = (TempRepeatMateriel) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
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
			btnNotSelected.setText("全否");
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
						TempRepeatMateriel tempRepeatMateriel = (TempRepeatMateriel) list
								.get(i);
						if (tempRepeatMateriel.getIsSelected()) {
							lsResult.add(tempRepeatMateriel);
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
			TempRepeatMateriel tempRepeatMateriel = (TempRepeatMateriel) list
					.get(i);
			tempRepeatMateriel.setIsSelected(isSelected);
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
			jLabel.setBounds(new java.awt.Rectangle(48, 3, 50, 24));
			jLabel.setText("查询条件");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(getTfQueryValue(), null);
			jPanel.add(getBtnSearch(), null);
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
			jComboBox.setBounds(new java.awt.Rectangle(102, 3, 106, 24));
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
			tfQueryValue.setBounds(new java.awt.Rectangle(212, 2, 101, 26));
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
			btnSearch.setBounds(new java.awt.Rectangle(315, 2, 65, 26));
			btnSearch.setText("查询");
		}
		return btnSearch;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
