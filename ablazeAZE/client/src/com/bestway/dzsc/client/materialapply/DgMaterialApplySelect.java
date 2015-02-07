package com.bestway.dzsc.client.materialapply;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.materialapply.action.MaterialApplyAction;
import com.bestway.dzsc.materialapply.entity.MaterialApply;
import com.bestway.dzsc.materialapply.entity.MaterialChange;
import com.bestway.dzsc.materialapply.entity.TempMaterialApplySelectParam;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgMaterialApplySelect extends JDialogBase {

	private JPanel jContentPane = null;

	private JRadioButton rbAllImgExg = null;

	private JRadioButton rbAllExg = null;

	private JRadioButton rbAllImg = null;

	private JRadioButton rbPartImgExg = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JScrollPane spChangedExg = null;

	private JTable tbChangedExg = null;

	private JToolBar jJToolBarBar = null;

	private JButton btnSelectAll = null;

	private JButton btnSelectNone = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="727,32"

	private JButton btnOK = null;

	private JButton btnCancel = null;

	private TempMaterialApplySelectParam param;

	private boolean isOk = false;

	private JTabbedPane jTabbedPane = null;

	private JTabbedPane tpImg = null;

	private JTabbedPane tpExg = null;

	private JScrollPane spOriginalImg = null;

	private JTable tbOriginalImg = null;

	private JScrollPane spChangedImg = null;

	private JTable tbChangedImg = null;

	private JScrollPane spOriginalExg = null;

	private JTable tbOriginalExg = null;

	private MaterialApplyAction materialApplyAction = null;

	private JTableListModel tableModelImg;

	private JTableListModel tableModelImgChange;

	private JTableListModel tableModelExg;

	private JTableListModel tableModelExgChange;

	public TempMaterialApplySelectParam getParam() {
		return param;
	}

	public boolean isOk() {
		return isOk;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgMaterialApplySelect() {
		super();
		initialize();
		materialApplyAction = (MaterialApplyAction) CommonVars
				.getApplicationContext().getBean("materialApplyAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			setState();
		}
		super.setVisible(true);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(676, 558));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("选择性备案");
		this.setContentPane(getJContentPane());
		this.getButtonGroup();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
		}
		return jContentPane;
	}

	class SelectItemListener implements java.awt.event.ItemListener {
		public void itemStateChanged(java.awt.event.ItemEvent e) {
			if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
				setState();
				DgMaterialApplySelect.this.setVisible(true);
			}
		}
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAllImgExg() {
		if (rbAllImgExg == null) {
			rbAllImgExg = new JRadioButton();
			rbAllImgExg.setText("备案全部未备案的料件和成品");
			rbAllImgExg.setSelected(true);
			rbAllImgExg.setBounds(new Rectangle(66, 25, 188, 29));
			rbAllImgExg.addItemListener(new SelectItemListener());

		}
		return rbAllImgExg;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAllExg() {
		if (rbAllExg == null) {
			rbAllExg = new JRadioButton();
			rbAllExg.setText("备案全部未备案的成品");
			rbAllExg.setBounds(new Rectangle(301, 23, 149, 29));
			rbAllExg.addItemListener(new SelectItemListener());
		}
		return rbAllExg;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAllImg() {
		if (rbAllImg == null) {
			rbAllImg = new JRadioButton();
			rbAllImg.setText("备案全部未备案的料件");
			rbAllImg.setBounds(new Rectangle(301, 58, 149, 28));
			rbAllImg.addItemListener(new SelectItemListener());
		}
		return rbAllImg;
	}

	/**
	 * This method initializes jRadioButton3
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbPartImgExg() {
		if (rbPartImgExg == null) {
			rbPartImgExg = new JRadioButton();
			rbPartImgExg.setText("备案部分未备案的料件和成品");
			rbPartImgExg.setBounds(new Rectangle(66, 61, 188, 29));
			rbPartImgExg.addItemListener(new SelectItemListener());
			rbPartImgExg.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						if (tableModelImg == null) {
							showData();
						}
					}
				}
			});
		}
		return rbPartImgExg;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(15, 19, 636, 106));
			jPanel.setBorder(BorderFactory.createTitledBorder(null,
					"\u5907\u6848\u6570\u636e\u9009\u62e9\u6761\u4ef6",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel.add(getRbAllImgExg(), null);
			jPanel.add(getRbAllExg(), null);
			jPanel.add(getRbAllImg(), null);
			jPanel.add(getRbPartImgExg(), null);
			jPanel.add(getBtnOK(), null);
			jPanel.add(getBtnCancel(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.setBounds(new Rectangle(15, 149, 636, 366));
			jPanel1.setBorder(BorderFactory.createTitledBorder(null,
					"\u6570\u636e\u9009\u62e9",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel1.add(getJJToolBarBar(), BorderLayout.NORTH);
			jPanel1.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpChangedExg() {
		if (spChangedExg == null) {
			spChangedExg = new JScrollPane();
			spChangedExg.setViewportView(getTbChangedExg());
		}
		return spChangedExg;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbChangedExg() {
		if (tbChangedExg == null) {
			tbChangedExg = new JTable();
		}
		return tbChangedExg;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getBtnSelectAll());
			jJToolBarBar.add(getBtnSelectNone());
		}
		return jJToolBarBar;
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
	private JButton getBtnSelectNone() {
		if (btnSelectNone == null) {
			btnSelectNone = new JButton();
			btnSelectNone.setText("全否");
			btnSelectNone
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							selectAll(false);
						}
					});
		}
		return btnSelectNone;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getRbAllImgExg());
			buttonGroup.add(this.getRbAllExg());
			buttonGroup.add(this.getRbAllImg());
			buttonGroup.add(this.getRbPartImgExg());
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(new Rectangle(541, 25, 66, 24));
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					param = new TempMaterialApplySelectParam();
					param.setApplyAllImgExg(rbAllImgExg.isSelected());
					param.setApplyAllImg(rbAllImg.isSelected());
					param.setApplyAllExg(rbAllExg.isSelected());
					param.setApplyPartImgExg(rbPartImgExg.isSelected());
					param.setLsPartExg(getPartExg());
					param.setLsPartExgChange(getPartExgChange());
					param.setLsPartImg(getPartImg());
					param.setLsPartImgChange(getPartImgChange());
					isOk=true;
					dispose();
				}
			});
		}
		return btnOK;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(541, 63, 66, 24));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	private void setState() {
		if (this.rbAllImgExg.isSelected()) {
			this.setSize(new Dimension(676, 175));
		} else if (this.rbAllExg.isSelected()) {
			this.setSize(new Dimension(676, 175));
		} else if (this.rbAllImg.isSelected()) {
			this.setSize(new Dimension(676, 175));
		} else if (this.rbPartImgExg.isSelected()) {
			this.setSize(new Dimension(676, 558));
		}
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
			jTabbedPane.addTab("料件", null, getTpImg(), null);
			jTabbedPane.addTab("成品", null, getTpExg(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jTabbedPane1
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getTpImg() {
		if (tpImg == null) {
			tpImg = new JTabbedPane();
			tpImg.setTabPlacement(JTabbedPane.BOTTOM);
			tpImg.addTab("未变更", null, getSpOriginalImg(), null);
			tpImg.addTab("变更后", null, getSpChangedImg(), null);

		}
		return tpImg;
	}

	/**
	 * This method initializes jTabbedPane2
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getTpExg() {
		if (tpExg == null) {
			tpExg = new JTabbedPane();

			tpExg.setTabPlacement(JTabbedPane.BOTTOM);
			tpExg.addTab("未变更", null, getSpOriginalExg(), null);
			tpExg.addTab("变更后", null, getSpChangedExg(), null);
		}
		return tpExg;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpOriginalImg() {
		if (spOriginalImg == null) {
			spOriginalImg = new JScrollPane();
			spOriginalImg.setViewportView(getTbOriginalImg());
		}
		return spOriginalImg;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbOriginalImg() {
		if (tbOriginalImg == null) {
			tbOriginalImg = new JTable();
		}
		return tbOriginalImg;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpChangedImg() {
		if (spChangedImg == null) {
			spChangedImg = new JScrollPane();
			spChangedImg.setViewportView(getTbChangedImg());
		}
		return spChangedImg;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbChangedImg() {
		if (tbChangedImg == null) {
			tbChangedImg = new JTable();
		}
		return tbChangedImg;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpOriginalExg() {
		if (spOriginalExg == null) {
			spOriginalExg = new JScrollPane();
			spOriginalExg.setViewportView(getTbOriginalExg());
		}
		return spOriginalExg;
	}

	/**
	 * This method initializes jTable3
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbOriginalExg() {
		if (tbOriginalExg == null) {
			tbOriginalExg = new JTable();
		}
		return tbOriginalExg;
	}

	private JTableListModel initTable(List list, JTable table, boolean isChange) {
		if (list == null) {
			list = new Vector();
		}
		JTableListModelAdapter jTableListModelAdapter = null;
		if (!isChange) {
			jTableListModelAdapter = new JTableListModelAdapter() {
				public List InitColumns() {
					List<JTableListColumn> list = new Vector<JTableListColumn>();
					list.add(addColumn("是否选择", "isSelected", 50));
					list.add(addColumn("状态标志", "stateMark", 100));
					list.add(addColumn("修改标志", "modifyMark", 100));
					list.add(addColumn("序号", "no", 50, Integer.class));
					list.add(addColumn("类别", "materiel.scmCoi.name", 80));
					list.add(addColumn("料号", "materiel.ptNo", 100));
					list.add(addColumn("商品编码", "materiel.complex.code", 80));
					list.add(addColumn("工厂商品名称", "materiel.factoryName", 100));
					list.add(addColumn("工厂型号规格", "materiel.factorySpec", 100));
					list.add(addColumn("工厂单位", "materiel.calUnit.name", 50));
					list.add(addColumn("报关单位", "materiel.ptUnit.name", 50));
					list.add(addColumn("单价", "materiel.ptPrice", 50));
					list.add(addColumn("净重", "materiel.ptNetWeight", 50));					
					return list;
				}
			};
		} else if (isChange) {
			jTableListModelAdapter = new JTableListModelAdapter() {
				public List InitColumns() {
					List<JTableListColumn> list = new Vector<JTableListColumn>();
					list.add(addColumn("是否选择", "isSelected", 50));
					list.add(addColumn("状态标志", "stateMark", 100));
					list.add(addColumn("修改标志", "modifyMark", 100));
					list.add(addColumn("序号", "no", 50, Integer.class));
					list.add(addColumn("类别", "scmCoi.name", 80));
					list.add(addColumn("料号", "ptNo", 100));
					list.add(addColumn("商品编码", "complex.code", 80));
					list.add(addColumn("工厂商品名称", "factoryName", 100));
					list.add(addColumn("工厂型号规格", "factorySpec", 100));
					list.add(addColumn("工厂单位", "calUnit.name", 50));
					list.add(addColumn("报关单位", "ptUnit.name", 50));
					list.add(addColumn("单价", "ptPrice", 50));
					list.add(addColumn("净重", "ptNetWeight", 50));
					return list;
				}
			};
		}
		JTableListModel tableModel = new JTableListModel(table, list,
				jTableListModelAdapter);
		jTableListModelAdapter.setEditableColumn(1);
		TableColumn column = table.getColumnModel().getColumn(1);
		column.setCellRenderer(new TableCheckBoxRender());
		column.setCellEditor(new CheckBoxEditor(new JCheckBox()));
		column = table.getColumnModel().getColumn(2);
		column.setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected,
						hasFocus, row, column);
				String state = "";
				if (value != null) {
					state = value.toString();
				}
				if (state.equals(DzscState.ORIGINAL)) {
					this.setText("初始状态");
				} else if (state.equals(DzscState.APPLY)) {
					this.setText("申报状态");
				}
				if (state.equals(DzscState.EXECUTE)) {
					this.setText("生效状态");
				}
				if (state.equals(DzscState.CHANGE)) {
					this.setText("变更状态");
				}
				return this;
			}
		});
		table.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String state = "";
						if (value != null) {
							state = value.toString();
						}
						if (state.equals(ModifyMarkState.UNCHANGE)) {
							this.setText("未修改");
						} else if (state.equals(ModifyMarkState.ADDED)) {
							this.setText("新增");
						}
						if (state.equals(ModifyMarkState.MODIFIED)) {
							this.setText("已修改");
						}
						if (state.equals(ModifyMarkState.DELETED)) {
							this.setText("已删除");
						}
						return this;
					}
				});
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
			if (obj instanceof MaterialApply) {
				MaterialApply temp = (MaterialApply) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			} else if (obj instanceof MaterialChange) {
				MaterialChange temp = (MaterialChange) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	private void showData() {
		String materielType = MaterielType.MATERIEL;
		List list = materialApplyAction.findOriginalMaterial(new Request(
				CommonVars.getCurrUser()), materielType);
		tableModelImg = this.initTable(list, tbOriginalImg, false);
		list = materialApplyAction.findNotApplyMaterialChange(new Request(
				CommonVars.getCurrUser()), materielType);
		tableModelImgChange = this.initTable(list, tbChangedImg, true);
		materielType = MaterielType.FINISHED_PRODUCT;
		list = materialApplyAction.findOriginalMaterial(new Request(CommonVars
				.getCurrUser()), materielType);
		tableModelExg = this.initTable(list, tbOriginalExg, false);
		list = materialApplyAction.findNotApplyMaterialChange(new Request(
				CommonVars.getCurrUser()), materielType);
		tableModelExgChange = this.initTable(list, tbChangedExg, true);

	}

	private void selectAll(boolean isSelected) {
		if (jTabbedPane.getSelectedIndex() == 0) {
			List list = new ArrayList();
			if (tpImg.getSelectedIndex() == 0) {
				list = tableModelImg.getList();
				for (int i = 0; i < list.size(); i++) {
					MaterialApply materialApply = (MaterialApply) list.get(i);
					materialApply.setIsSelected(isSelected);
				}
				tableModelImg.setList(list);
			} else {
				list = tableModelImgChange.getList();
				for (int i = 0; i < list.size(); i++) {
					MaterialChange materialChange = (MaterialChange) list
							.get(i);
					materialChange.setIsSelected(isSelected);
				}
				tableModelImgChange.setList(list);
			}
		} else {
			List list = new ArrayList();
			if (tpExg.getSelectedIndex() == 0) {
				list = tableModelExg.getList();
				for (int i = 0; i < list.size(); i++) {
					MaterialApply materialApply = (MaterialApply) list.get(i);
					materialApply.setIsSelected(isSelected);
				}
				tableModelExg.setList(list);
			} else {
				list = tableModelExgChange.getList();
				for (int i = 0; i < list.size(); i++) {
					MaterialChange materialChange = (MaterialChange) list
							.get(i);
					materialChange.setIsSelected(isSelected);
				}
				tableModelExgChange.setList(list);
			}
		}
	}

	private List getPartExg() {
		List lsResult = new ArrayList();
		if (tableModelExg == null) {
			return lsResult;
		}
		List list = tableModelExg.getList();
		for (int i = 0; i < list.size(); i++) {
			MaterialApply apply = (MaterialApply) list.get(i);
			if (apply.getIsSelected() != null && apply.getIsSelected()) {
				lsResult.add(apply);
			}
		}
		return lsResult;
	}

	private List getPartExgChange() {
		List lsResult = new ArrayList();
		if (tableModelExgChange == null) {
			return lsResult;
		}
		List list = tableModelExgChange.getList();
		for (int i = 0; i < list.size(); i++) {
			MaterialChange apply = (MaterialChange) list.get(i);
			if (apply.getIsSelected() != null && apply.getIsSelected()) {
				lsResult.add(apply);
			}
		}
		return lsResult;
	}

	private List getPartImg() {
		List lsResult = new ArrayList();
		if (tableModelImg == null) {
			return lsResult;
		}
		List list = tableModelImg.getList();
		for (int i = 0; i < list.size(); i++) {
			MaterialApply apply = (MaterialApply) list.get(i);
			if (apply.getIsSelected() != null && apply.getIsSelected()) {
				lsResult.add(apply);
			}
		}
		return lsResult;
	}

	private List getPartImgChange() {
		List lsResult = new ArrayList();
		if (tableModelImgChange == null) {
			return lsResult;
		}
		List list = tableModelImgChange.getList();
		for (int i = 0; i < list.size(); i++) {
			MaterialChange apply = (MaterialChange) list.get(i);
			if (apply.getIsSelected() != null && apply.getIsSelected()) {
				lsResult.add(apply);
			}
		}
		return lsResult;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
