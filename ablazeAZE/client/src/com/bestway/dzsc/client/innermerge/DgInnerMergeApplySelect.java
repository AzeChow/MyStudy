package com.bestway.dzsc.client.innermerge;

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
import com.bestway.dzsc.innermerge.action.DzscInnerMergeAction;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.dzsc.innermerge.entity.TempInnerMergeApplySelectParam;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgInnerMergeApplySelect extends JDialogBase {

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

	private TempInnerMergeApplySelectParam param;

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

	private DzscInnerMergeAction dzscInnerMergeAction = null;

	private JTableListModel tableModelImg;

	private JTableListModel tableModelImgChange;

	private JTableListModel tableModelExg;

	private JTableListModel tableModelExgChange;

	public TempInnerMergeApplySelectParam getParam() {
		return param;
	}

	public boolean isOk() {
		return isOk;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgInnerMergeApplySelect() {
		super();
		initialize();
		dzscInnerMergeAction = (DzscInnerMergeAction) CommonVars
				.getApplicationContext().getBean("dzscInnerMergeAction");
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
				DgInnerMergeApplySelect.this.setVisible(true);
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
			rbAllImgExg.setText("备案全部未备案的料件和成品归并");
			rbAllImgExg.setSelected(true);
			rbAllImgExg.setBounds(new Rectangle(66, 25, 209, 29));
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
			rbAllExg.setText("备案全部未备案的成品归并");
			rbAllExg.setBounds(new Rectangle(301, 23, 174, 29));
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
			rbAllImg.setText("备案全部未备案的料件归并");
			rbAllImg.setBounds(new Rectangle(301, 58, 174, 28));
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
			rbPartImgExg.setText("备案部分未备案的料件和成品归并");
			rbPartImgExg.setBounds(new Rectangle(66, 61, 209, 29));
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
					param = new TempInnerMergeApplySelectParam();
					param.setApplyAllImgExg(rbAllImgExg.isSelected());
					param.setApplyAllImg(rbAllImg.isSelected());
					param.setApplyAllExg(rbAllExg.isSelected());
					param.setApplyPartImgExg(rbPartImgExg.isSelected());
					param.setLsPartExg(getPartImgExgData(tableModelExg));
					param
							.setLsPartExgChange(getPartImgExgData(tableModelExgChange));
					param.setLsPartImg(getPartImgExgData(tableModelImg));
					param
							.setLsPartImgChange(getPartImgExgData(tableModelImgChange));
					isOk = true;
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

	private JTableListModel initTable(List list, JTable table) {
		if (list == null) {
			list = new Vector();
		}
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("是否选择", "isSelected", 50));
				list.add(addColumn("状态", "stateMarkDesc", 100));
				list.add(addColumn("处理标记", "beforeModifyMarkDesc", 100));
				list.add(addColumn("货号", "materiel.ptNo", 50));
				list.add(addColumn("商品编码", "materiel.complex.code", 100));
				list.add(addColumn("商品名称", "materiel.factoryName", 100));
				list.add(addColumn("规格", "materiel.factorySpec", 100));
				list.add(addColumn("单位", "materiel.calUnit.name", 50));
				// list.add(addColumn("单价", "materiel.ptPrice", 100));
				list.add(addColumn("处理标记",
						"dzscTenInnerMerge.tenModifyMarkDesc", 100));
				list.add(addColumn("序号", "dzscTenInnerMerge.tenSeqNum", 50,
						Integer.class));
				list.add(addColumn("10位商品编码",
						"dzscTenInnerMerge.tenComplex.code", 100));
				list.add(addColumn("10位商品名称", "dzscTenInnerMerge.tenPtName",
						100));
				list.add(addColumn("10位商品规格", "dzscTenInnerMerge.tenPtSpec",
						100));
				list.add(addColumn("10位常用单位", "dzscTenInnerMerge.tenUnit.name",
						100));

				return list;
			}
		};

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
			if (obj instanceof DzscInnerMergeData) {
				DzscInnerMergeData data = (DzscInnerMergeData) obj;
				data.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	private void showData() {
		String materielType = MaterielType.MATERIEL;
		List[] lists = dzscInnerMergeAction.findNotApplyInnerMergeData(
				new Request(CommonVars.getCurrUser()), materielType);
		tableModelImg = this.initTable(lists[0], tbOriginalImg);
		tableModelImgChange = this.initTable(lists[1], tbChangedImg);
		materielType = MaterielType.FINISHED_PRODUCT;
		lists = dzscInnerMergeAction.findNotApplyInnerMergeData(new Request(
				CommonVars.getCurrUser()), materielType);
		tableModelExg = this.initTable(lists[0], tbOriginalExg);
		tableModelExgChange = this.initTable(lists[1], tbChangedExg);
	}

	private void selectAll(boolean isSelected, JTableListModel tableModel) {
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			data.setIsSelected(isSelected);
		}
		tableModel.setList(list);
	}

	private void selectAll(boolean isSelected) {
		if (jTabbedPane.getSelectedIndex() == 0) {
			if (tpImg.getSelectedIndex() == 0) {
				selectAll(isSelected, tableModelImg);
			} else {
				selectAll(isSelected, tableModelImgChange);
			}
		} else {
			if (tpExg.getSelectedIndex() == 0) {
				selectAll(isSelected, tableModelExg);
			} else {
				selectAll(isSelected, tableModelExgChange);
			}
		}
	}

	private List getPartImgExgData(JTableListModel tableModel) {
		List lsResult = new ArrayList();
		if (tableModel == null) {
			return lsResult;
		}
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			if (data.getIsSelected() != null && data.getIsSelected()) {
				lsResult.add(data);
			}
		}
		return lsResult;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
