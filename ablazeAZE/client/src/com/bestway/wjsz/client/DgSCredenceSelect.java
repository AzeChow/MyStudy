package com.bestway.wjsz.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrExg;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrImg;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.jptds.common.DeclareState;
import com.bestway.jptds.common.ModifyMarkState;
import com.bestway.jptds.credence.action.CredenceAction;
import com.bestway.jptds.credence.entity.ExgCredence;
import com.bestway.jptds.credence.entity.ImgCredence;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgSCredenceSelect extends JDialogBase {

	private CredenceAction wjCredenceAction = (CredenceAction) com.bestway.jptds.client.common.CommonVars
			.getApplicationContext().getBean("credenceAction");

	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JButton btnOK = null;
	private JButton btnCancel = null;
	private JTabbedPane jTabbedPane = null;
	private JScrollPane jScrollPane = null;
	private JTable tbImg = null;
	private JScrollPane jScrollPane1 = null;
	private JTable tbExg = null;
	private boolean isOK = false;
	private JTableListModel tableModelImg;
	private JTableListModel tableModelExg;

	private JButton btnSelectAll = null;

	private JButton btnNotSelect = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgSCredenceSelect() {
		super();
		initialize();
		showCredenceData();
	}

	private void showCredenceData() {
		List listImgCredence = wjCredenceAction.findImgCredence(
				com.bestway.jptds.client.common.CommonVars.getRequest(), "",
				"", "");
		List listExgCredence = wjCredenceAction.findExgCredence(
				com.bestway.jptds.client.common.CommonVars.getRequest(), "",
				"", "");
		this.initTableImg(listImgCredence);
		this.initTableExg(listExgCredence);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(646, 425));
		this.setTitle("外经凭证资料");
		this.setContentPane(getJContentPane());

	}

	public boolean isOK() {
		return isOK;
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
			jContentPane.add(getJPanel(), BorderLayout.SOUTH);
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.add(getBtnSelectAll(), null);
			jPanel.add(getBtnNotSelect(), null);
			jPanel.add(getBtnOK(), null);
			jPanel.add(getBtnCancel(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnOK
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOK = true;
					dispose();
				}
			});
		}
		return btnOK;
	}

	/**
	 * This method initializes btnCancel
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

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("凭证料件", null, getJScrollPane(), null);
			jTabbedPane.addTab("凭证成品", null, getJScrollPane1(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbImg());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbImg
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImg() {
		if (tbImg == null) {
			tbImg = new JTable();
		}
		return tbImg;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbExg());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbExg
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExg() {
		if (tbExg == null) {
			tbExg = new JTable();
		}
		return tbExg;
	}

	private void initTableImg(final List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 80));
				list.add(addColumn("料件编号", "credenceNo", 60, String.class));
				list.add(addColumn("商品编码", "complexCode", 90, String.class));
				list.add(addColumn("商品名称", "name", 180, String.class));
				list.add(addColumn("型号规格", "spec", 180, String.class));
				list.add(addColumn("原产地", "country.name", 100));
				list.add(addColumn("申报单位", "unit.name", 60, String.class));
				list.add(addColumn("单价", "unitPrice", 70, String.class));
				list.add(addColumn("币制", "curr.name", 80, String.class));
				list.add(addColumn("审核状态", "appState", 80, String.class));
				list.add(addColumn("修改标志", "modifyMark", 60, String.class));

				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModelImg = new JTableListModel(tbImg, list, jTableListModelAdapter);
		tbImg.getColumnModel().getColumn(1)
				.setCellRenderer(new MultiRenderer());
		tbImg.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		setImgTableRowColor(tbImg);
	}

	public void setImgTableRowColor(JTable table) {
		int columnCount = table.getModel().getColumnCount();
		for (int i = 2; i < columnCount; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(
					new DefaultTableCellRenderer() {

						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							super.getTableCellRendererComponent(table, value,
									isSelected, hasFocus, row, column);
							String appState = ((String) table.getModel()
									.getValueAt(row, 10)).toString();
							if (appState.equals(DeclareState.DECLARE_PASS)) {
								setForeground(new Color(0, 153, 0));
							} else if (appState
									.equals(DeclareState.DECLARE_NO_PASS)) {
								setForeground(Color.RED);
							} else if (appState
									.equals(DeclareState.DECLARE_WAIT)) {
								setForeground(new Color(0, 0, 204));
							} else {
								setForeground(Color.BLACK);
							}
							if (column == 10) {
								super.setText(DeclareState
										.getDeclareState(appState.toString()));
							}
							String modifyMark = ((String) table.getModel()
									.getValueAt(row, 11)).toString();
							if (column == 11) {
								super.setText(ModifyMarkState
										.getModifyMarkSpec(modifyMark
												.toString()));
							}
							return this;
						}
					});
		}
	}

	private void initTableExg(final List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 80));
				list.add(addColumn("成品编号", "credenceNo", 60, String.class));
				list.add(addColumn("商品编码", "complexCode", 80));
				list.add(addColumn("商品名称", "name", 150, String.class));
				list.add(addColumn("型号规格", "spec", 100, String.class));
				list.add(addColumn("消费国地", "country.name", 70));
				list.add(addColumn("申报单位", "unit.name", 50, String.class));
				list.add(addColumn("单价", "unitPrice", 80, String.class));
				list.add(addColumn("申报加工单价", "declareprocessUnitPrice", 90,
						String.class));
				list.add(addColumn("批准加工单价", "approveprocessUnitPrice", 90,
						String.class));
				list.add(addColumn("币制", "curr.name", 45, String.class));
				list.add(addColumn("审核状态", "appState", 80, String.class));
				list.add(addColumn("修改标志", "modifyMark", 60, String.class));

				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModelExg = new JTableListModel(tbExg, list, jTableListModelAdapter);
		tbExg.getColumnModel().getColumn(1)
				.setCellRenderer(new MultiRenderer());
		tbExg.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		setExgTableRowColor(tbExg);
	}

	public void setExgTableRowColor(JTable table) {
		int columnCount = table.getModel().getColumnCount();
		for (int i = 2; i < columnCount; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(
					new DefaultTableCellRenderer() {

						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							super.getTableCellRendererComponent(table, value,
									isSelected, hasFocus, row, column);
							String appState = ((String) table.getModel()
									.getValueAt(row, 12)).toString();
							if (appState.equals(DeclareState.DECLARE_PASS)) {
								setForeground(new Color(0, 153, 0));
							} else if (appState
									.equals(DeclareState.DECLARE_NO_PASS)) {
								setForeground(Color.RED);
							} else if (appState
									.equals(DeclareState.DECLARE_WAIT)) {
								setForeground(new Color(0, 0, 204));
							} else {
								setForeground(Color.black);
							}
							if (column == 12) {
								super.setText(DeclareState
										.getDeclareState(appState.toString()));
							}
							String modifyMark = ((String) table.getModel()
									.getValueAt(row, 13)).toString();
							if (column == 13) {
								super.setText(ModifyMarkState
										.getModifyMarkSpec(modifyMark
												.toString()));
							}
							return this;
						}
					});
		}
	}

	/**
	 * render table JchcckBox 列
	 */
	class MultiRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				value = false;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
				checkBox.setHorizontalAlignment(JLabel.CENTER);
				checkBox.setBackground(table.getBackground());
				if (isSelected) {
					checkBox.setForeground(table.getSelectionForeground());
					checkBox.setBackground(table.getSelectionBackground());
				} else {
					checkBox.setForeground(table.getForeground());
					checkBox.setBackground(table.getBackground());
				}
				return checkBox;
			}
			String str = (value == null) ? "" : value.toString();
			return super.getTableCellRendererComponent(table, str, isSelected,
					hasFocus, row, column);
		}
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
				value = false;
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
			if (obj instanceof ImgCredence) {
				ImgCredence temp = (ImgCredence) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			if (obj instanceof ExgCredence) {
				ExgCredence temp = (ExgCredence) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}

	}

	/**
	 * 抓取选择的凭证料件
	 * 
	 * @return
	 */
	public List getSelectImgCredence() {
		List listImg = new ArrayList();
		List list = tableModelImg.getList();
		for (int i = 0; i < list.size(); i++) {
			ImgCredence imgCredence = (ImgCredence) list.get(i);
			if (imgCredence.getIsSelected() != null
					&& imgCredence.getIsSelected()) {
				listImg.add(imgCredence);
			}
		}
		return listImg;
	}

	/**
	 * 抓取选择的凭证成品
	 * 
	 * @return
	 */
	public List getSelectExgCredence() {
		List listImg = new ArrayList();
		List list = tableModelExg.getList();
		for (int i = 0; i < list.size(); i++) {
			ExgCredence exgCredence = (ExgCredence) list.get(i);
			if (exgCredence.getIsSelected() != null
					&& exgCredence.getIsSelected()) {
				listImg.add(exgCredence);
			}
		}
		return listImg;
	}

	/**
	 * This method initializes btnSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setText("全部选中");
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectData(true);
				}
			});
		}
		return btnSelectAll;
	}

	/**
	 * This method initializes btnNotSelect
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNotSelect() {
		if (btnNotSelect == null) {
			btnNotSelect = new JButton();
			btnNotSelect.setText("全部不选");
			btnNotSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectData(false);
				}
			});
		}
		return btnNotSelect;
	}

	private void selectData(boolean isSelect) {
		if (jTabbedPane.getSelectedIndex() == 0) {// 料件
			List list = tableModelImg.getList();
			for (int i = 0; i < list.size(); i++) {
				ImgCredence imgCredence = (ImgCredence) list.get(i);
				imgCredence.setIsSelected(isSelect);
				tableModelImg.updateRow(imgCredence);
			}
		} else if (jTabbedPane.getSelectedIndex() == 1) {// 成品
			List list = tableModelExg.getList();
			for (int i = 0; i < list.size(); i++) {
				ExgCredence exgCredence = (ExgCredence) list.get(i);
				exgCredence.setIsSelected(isSelect);
				tableModelExg.updateRow(exgCredence);
			}
		}
	}

} // @jve:decl-index=0:visual-constraint="10,10"
