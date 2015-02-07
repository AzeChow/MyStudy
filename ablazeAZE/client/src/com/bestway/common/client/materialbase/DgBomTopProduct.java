package com.bestway.common.client.materialbase;

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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.BomStructureType;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgBomTopProduct extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

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

	private List lsResult = new ArrayList();  //  @jve:decl-index=0:

	private Integer version = null;

	private JTableListModel tableModel = null;

	private JPanel jPanel1 = null;

	private JButton btnCancel = null;

	private JCheckBox cbMerge = null;
	

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
	public DgBomTopProduct() {
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
		this.setSize(new Dimension(730, 502));
		this.setTitle("BOM成品");
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
		List list = new ArrayList();
		if (this.bomStructureType == BomStructureType.NO_VERSION_NO_DATE
				&& version != null) {
			list = materialManageAction.findTopProductBomManage(new Request(
					CommonVars.getCurrUser(), true), version);
		} else if (this.bomStructureType == BomStructureType.HAVE_VERSION_NO_DATE) {
			list = materialManageAction.findTopProductBomManageForVersion(new Request(
					CommonVars.getCurrUser(), true));
		} else if (this.bomStructureType == BomStructureType.NO_VERSION_HAS_DATE) {
			list = materialManageAction.findTopProductBomManage(new Request(
					CommonVars.getCurrUser(), true));
		}
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			@Override
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("是否选择", "isSelected", 50));
				list.add(addColumn("类别(0或1)", "scmCoi.coiProperty", 100));//wss:2010.05.10新增:为区分成品与半成品
				list.add(addColumn("料号", "ptNo", 100));
				list.add(addColumn("商品编码", "complex.code", 80));
				list.add(addColumn("工厂商品名称", "factoryName", 100));
				list.add(addColumn("工厂型号规格", "factorySpec", 100));
				list.add(addColumn("单位", "calUnit.name", 50));
				// list.add(addColumn("商品名称", "ptName", 100));
				// list.add(addColumn("型号规格", "ptSpec", 100));
				list.add(addColumn("单价", "ptPrice", 50));
				list.add(addColumn("净重", "ptNetWeight", 50));
				// list.add(addColumn("状态标志", "stateMark", 50));
				return list;
			}
		};
		
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		
		jTable.getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText("0".equals(value)?"成品":"半成品");
		                return this;
					}
				});
		
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
			List<Object> objs = tableModel.getCurrentRows();
			for(Object obj : objs){
				if (obj instanceof EnterpriseMaterial) {
					EnterpriseMaterial temp = (EnterpriseMaterial) obj;
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
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel1(), BorderLayout.SOUTH);
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
		}
		return jContentPane;
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
			btnSelectAll.setBounds(new Rectangle(390, 3, 68, 25));
			btnSelectAll.setPreferredSize(new Dimension(65, 30));
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
			btnNotSelected.setBounds(new Rectangle(470, 3, 68, 25));
			btnNotSelected.setPreferredSize(new Dimension(65, 30));
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
			btnOk.setBounds(new Rectangle(248, 5, 68, 25));
			btnOk.setPreferredSize(new Dimension(65, 30));
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = tableModel.getList();
					for (int i = 0; i < list.size(); i++) {
						EnterpriseMaterial material = (EnterpriseMaterial) list
								.get(i);
						if (material.getIsSelected()) {
							lsResult.add(material);
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
			EnterpriseMaterial enterpriseMaterial = (EnterpriseMaterial) list
					.get(i);
			enterpriseMaterial.setIsSelected(isSelected);
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
			jPanel.setPreferredSize(new Dimension(1, 32));
			jPanel.add(jLabel, null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(getTfQueryValue(), null);
			jPanel.add(getBtnSearch(), null);
			jPanel.add(getBtnSelectAll(), null);
			jPanel.add(getBtnNotSelected(), null);
			jPanel.add(getCbMerge(), null);
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

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setPreferredSize(new Dimension(50, 35));
			jPanel1.add(getBtnOk(), null);
			jPanel1.add(getBtnCancel(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes btnCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(350, 5, 68, 25));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBomTopProduct.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes cbMerge	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbMerge() {
		if (cbMerge == null) {
			cbMerge = new JCheckBox();
			cbMerge.setBounds(new Rectangle(550, 5, 160, 21));
			cbMerge.setText("相同料号单损耗是否合并");
			cbMerge.setVisible(true);
		}
		return cbMerge;
	}
	
	public boolean getIsMerge() {
		return cbMerge.isSelected();
	}

} // @jve:decl-index=0:visual-constraint="10,10"
