package com.bestway.bcs.client.contractexe;

import com.bestway.bcs.contractexe.entity.TempBcsImpExpCommodityInfo;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JToolBar;
import javax.swing.JButton;

public class DgBcsImpExpCommodityInfo extends JDialogBase{
	private JTable table;
	
	private List list;
	
	private boolean isProduct = false;
	
	private JTableListModelAdapter jTableListModelAdapter;
	
	private AttributiveCellTableModel tableModel;
	
	private JButton btnDetermine;
	
	public DgBcsImpExpCommodityInfo() {
		
		this.setBounds(new Rectangle(800,500));
		this.setTitle("请选择报关资料！");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane,BorderLayout.CENTER);
		
		table = new MultiSpanCellTable();
		scrollPane.setViewportView(table);
		
		JToolBar toolBar = new JToolBar();
		panel.add(toolBar,BorderLayout.NORTH);
		toolBar.add(getBtnDetermine());
		
	}
	
	public JButton getBtnDetermine() {
		if(btnDetermine==null){
			btnDetermine = new JButton("确定");
			btnDetermine.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnDetermine;
	}
	
	@Override
	public void setVisible(boolean b) {
		initTible(list);
		super.setVisible(b);
	}

	private void initTible(List list){
		jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 50));

				list.add(addColumn("料号", "impExpCommodityInfo.materiel.ptNo", 100));
				list.add(addColumn("归并序号", "seqNo", 50));
				if (isProduct) {
					list.add(addColumn("备案序号", "contractExg.seqNum", 60));
					list.add(addColumn("商品编码", "contractExg.complex.code",
									100));
					list.add(addColumn("名称", "contractExg.name", 120));
				} else {
					list.add(addColumn("备案序号", "contractImg.seqNum", 60));
					list.add(addColumn("商品编码", "contractImg.complex.code",
									100));
					list.add(addColumn("名称", "contractImg.name", 120));
				}

				if (isProduct) {
					list.add(addColumn("规格型号", "contractExg.spec", 120));
					list.add(addColumn("单位", "contractExg.unit.name", 60));
				} else {
					list.add(addColumn("规格型号", "contractImg.spec", 120));
					list.add(addColumn("单位", "contractImg.unit.name", 60));
				}
				return list;
			}
		};
		
		tableModel = new AttributiveCellTableModel((MultiSpanCellTable)table, list, jTableListModelAdapter);
		
		jTableListModelAdapter.setEditableColumn(1);
		table.getColumnModel().getColumn(1).setCellRenderer(
				new MultiRenderer());
		table.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		
		((MultiSpanCellTable) table).combineRows(2,new int[]{2});
	}
	
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public void setIsProduct(boolean isProduct){
		this.isProduct = isProduct;
	}
	
	public boolean getIsProduct(){
		return this.isProduct;
	}
	
	public List getSelectList(){
		List returnList = new ArrayList();
		
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			TempBcsImpExpCommodityInfo temp = (TempBcsImpExpCommodityInfo) list.get(i);
			if(Boolean.TRUE.equals(temp.getIsSelected())){
				returnList.add(temp);
			}
		}
		return returnList;
	}
	
	/**
	 * render table JcheckBox 列 （用于将boolean 值转为JcheckBox）
	 */
	class MultiRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		/**
		 * 重写getTableCellRendererComponent方法
		 */
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
	 * 编辑列 （用于表格JCheckBox的编辑）
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;
		private JTable table = null;

		/**
		 * 构造CheckBoxEditor内部类
		 * 
		 * @param checkBox
		 */
		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		/**
		 * 编辑方法
		 */
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				return null;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb.setSelected(Boolean.valueOf(value.toString())
								.booleanValue());
			}
			cb.setHorizontalAlignment(JLabel.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		/**
		 * 获取编辑值
		 */
		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		/**
		 * 触发方法
		 */
		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof TempBcsImpExpCommodityInfo) {
				TempBcsImpExpCommodityInfo temp = (TempBcsImpExpCommodityInfo) obj;
				String oldMaterielID = temp.getImpExpCommodityInfo().getMateriel().getId();
				String oldCountryCode = temp.getImpExpCommodityInfo().getCountry()==null?""
						:temp.getImpExpCommodityInfo().getCountry().getCode();
				
				List list = tableModel.getList();
				for (int i = 0; i < list.size(); i++) {
					TempBcsImpExpCommodityInfo tempInfo = (TempBcsImpExpCommodityInfo) list.get(i);
					String newMaterielID = tempInfo.getImpExpCommodityInfo().getMateriel().getId();
					String newCountryCode = tempInfo.getImpExpCommodityInfo().getCountry()==null?""
							:temp.getImpExpCommodityInfo().getCountry().getCode();
					
					if((newMaterielID.equals(oldMaterielID))&&
							(newCountryCode.equals(oldCountryCode))){
						tempInfo.setIsSelected(false);
						tableModel.updateRow(tempInfo);
					}
				}
				
				temp.setIsSelected(new Boolean(true));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}
}