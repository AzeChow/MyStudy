package com.bestway.bcus.client.common;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.CancelCusCustomsDeclara;
import com.bestway.bcus.checkcancel.entity.CancelCustomsDeclara;
import com.bestway.bcus.checkcancel.entity.CancelHead;
import com.bestway.bcus.checkcancel.entity.CancelOwnerCustomsDeclara;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.client.custom.common.EncCommon;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DgQueryCustomsDeclara extends JDialogBase {
	private JPanel panel;
	private JToolBar tbMenu;
	private JLabel lblNewLabel;
	private JTextField tfDeclarationNo;
	private JCalendarComboBox cbbBeginDate;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JCalendarComboBox cbbEndDate;
	private JButton btnQuery;
	private JToolBar toolBar;
	private JButton btnOK;
	private JButton btnClose;
	private JScrollPane scrollPane;
	private JTable table;
	private boolean single;
	private CancelHead cancelHead;
	private boolean isOwner;
	private CheckCancelAction checkCancelAction = null;
	private JTableListModel tableModel = null;
	public DgQueryCustomsDeclara(boolean single, CancelHead cancelHead,
			boolean isOwner) {
		this.setTitle("报关单查询");
		this.setSize(750, 418);
		this.single = single;
		this.cancelHead = cancelHead;
		this.isOwner = isOwner;
		checkCancelAction = (CheckCancelAction) CommonVars.getApplicationContext().getBean("checkCancelAction");
		getContentPane().add(getPanel(), BorderLayout.CENTER);
		initTable(new ArrayList());
	}
	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getTbMenu(), BorderLayout.NORTH);
			panel.add(getToolBar(), BorderLayout.SOUTH);
			panel.add(getScrollPane(), BorderLayout.CENTER);
		}
		return panel;
	}
	private JToolBar getTbMenu() {
		if (tbMenu == null) {
			tbMenu = new JToolBar();
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(0);
			f.setHgap(0);
			tbMenu.setLayout(f);
			tbMenu.add(getLblNewLabel());
			tbMenu.add(getTfDeclarationNo());
			tbMenu.add(getLblNewLabel_1());
			tbMenu.add(getCbbBeginDate());
			tbMenu.add(getLblNewLabel_2());
			tbMenu.add(getCbbEndDate());
			tbMenu.add(getBtnQuery());
		}
		return tbMenu;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("报关单号：");
		}
		return lblNewLabel;
	}
	private JTextField getTfDeclarationNo() {
		if (tfDeclarationNo == null) {
			tfDeclarationNo = new JTextField();
			tfDeclarationNo.setColumns(10);
			tfDeclarationNo.setPreferredSize(new Dimension(103, 21));
		}
		return tfDeclarationNo;
	}
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			if(cancelHead.getBeginDate()!=null){
				cbbBeginDate.setDate(cancelHead.getBeginDate());
			}
			cbbBeginDate.setPreferredSize(new Dimension(90, 21));
		}
		return cbbBeginDate;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("    申报日期：");
		}
		return lblNewLabel_1;
	}
	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("至");
		}
		return lblNewLabel_2;
	}
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			if(cancelHead.getEndDate()!=null){
				cbbEndDate.setDate(cancelHead.getEndDate());
			}
			cbbEndDate.setPreferredSize(new Dimension(90, 21));
		}
		return cbbEndDate;
	}
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton("查询");
			btnQuery.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List list = checkCancelAction.findCustomsDeclaration(new Request(
							CommonVars.getCurrUser(), true), cancelHead, isOwner,cbbBeginDate.getDate(),cbbEndDate.getDate(),tfDeclarationNo.getText());
					System.out.println("list.size()==="+list.size());
					List cancelCustomsDeclaras = new Vector();
					for (int i = 0; i < list.size(); i++) {
						CancelCustomsDeclara cancelCustomsDeclara = null;
						if (isOwner) {
							cancelCustomsDeclara = new CancelOwnerCustomsDeclara();
						} else {
							cancelCustomsDeclara = new CancelCusCustomsDeclara();
						}
						cancelCustomsDeclara.setCustomNo(((CustomsDeclaration) list
								.get(i)).getCustomsDeclarationCode());
						cancelCustomsDeclara.setCustom(((CustomsDeclaration) list
								.get(i)).getDeclarationCustoms());
						cancelCustomsDeclara.setTradeMode(((CustomsDeclaration) list
								.get(i)).getTradeMode());
						cancelCustomsDeclara.setDeclareDate(((CustomsDeclaration) list
								.get(i)).getDeclarationDate());
						cancelCustomsDeclara
								.setInOutportDate(((CustomsDeclaration) list.get(i))
										.getImpExpDate());
						cancelCustomsDeclara.setNote(((CustomsDeclaration) list.get(i))
								.getMemos());
						cancelCustomsDeclara
								.setInOutportType(((CustomsDeclaration) list.get(i))
										.getImpExpType().intValue());

						if (((CustomsDeclaration) list.get(i)).getImpExpFlag() != null
								&& ((CustomsDeclaration) list.get(i)).getImpExpFlag()
										.equals(ImpExpFlag.IMPORT)) {
							cancelCustomsDeclara.setInOutportFlat("I");

						} else if (((CustomsDeclaration) list.get(i)).getImpExpFlag() != null
								&& ((CustomsDeclaration) list.get(i)).getImpExpFlag()
										.equals(ImpExpFlag.EXPORT)) {
							cancelCustomsDeclara.setInOutportFlat("E");
						} else if (((CustomsDeclaration) list.get(i)).getImpExpType() != null
								&& EncCommon
										.isImport(((CustomsDeclaration) list.get(i))
												.getImpExpType().intValue())
								&& ((CustomsDeclaration) list.get(i)).getImpExpFlag()
										.equals(ImpExpFlag.SPECIAL)) {
							cancelCustomsDeclara.setInOutportFlat("I");
						} else if (((CustomsDeclaration) list.get(i)).getImpExpType() != null
								&& !EncCommon.isImport(((CustomsDeclaration) list
										.get(i)).getImpExpType().intValue())
								&& ((CustomsDeclaration) list.get(i)).getImpExpFlag()
										.equals(ImpExpFlag.SPECIAL)) {
							cancelCustomsDeclara.setInOutportFlat("E");
						}
						cancelCustomsDeclaras.add(cancelCustomsDeclara);
					}
					
					initTable(cancelCustomsDeclaras);
				}
			});
		}
		return btnQuery;
	}
	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			toolBar.add(getBtnOK());
			toolBar.add(getBtnClose());
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.CENTER);
			f.setVgap(0);
			f.setHgap(0);
			toolBar.setLayout(f);
		}
		return toolBar;
	}
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton("确定");
			btnOK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnOK;
	}
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton("取消");
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
		}
		return table;
	}
	
	private void initTable(List list){
		JTableListModelAdapter adapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 30));
				list.add(addColumn("报关单号", "customNo", 100));
				list.add(addColumn("进出口类型", "inOutportFlat", 100));
				list.add(addColumn("报关地", "custom.name", 80));
				list.add(addColumn("申报日期", "declareDate", 100));
				list.add(addColumn("进出口日期", "inOutportDate", 100));
				list.add(addColumn("贸易方式", "tradeMode.name", 100));
				return list;
			}
		};
		tableModel = new JTableListModel(table, list,adapter);
		adapter.setEditableColumn(1);
		table.getColumnModel().getColumn(1).setCellRenderer(
				new MultiRenderer());
		table.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
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
				return this;
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
				return null;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb.setSelected(Boolean.valueOf(value.toString()).booleanValue());
			}
			cb.setHorizontalAlignment(JLabel.CENTER);
			cb.addActionListener(this);
			cb.setSelected(true);
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
			if (obj instanceof CancelCustomsDeclara) {
				CancelCustomsDeclara temp = (CancelCustomsDeclara) obj;
				Boolean boo = temp.getIsSelected()==null?false:temp.getIsSelected();
				temp.setIsSelected(!boo);
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}
	
	public List getSelectList(){
		List returnList = new ArrayList();
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			CancelCustomsDeclara cancelCustomsDeclara = (CancelCustomsDeclara)list.get(i);
			if(cancelCustomsDeclara.getIsSelected()!=null&&cancelCustomsDeclara.getIsSelected()){
				returnList.add(cancelCustomsDeclara);
			}
		}
		System.out.println("returnList.size()====="+returnList.size());
		return returnList;
	}
}
