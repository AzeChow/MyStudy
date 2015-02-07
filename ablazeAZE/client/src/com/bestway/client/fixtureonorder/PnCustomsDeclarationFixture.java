package com.bestway.client.fixtureonorder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.fixtureonorder.action.FixtureContractAction;


/**
 * @author fhz
 *
 */
public class PnCustomsDeclarationFixture extends JPanel {

	private static final long serialVersionUID = 1L;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JTableListModel tableModel;

	private FixtureContractAction fixtureContractAction;

	/**
	 * This is the default constructor
	 */
	public PnCustomsDeclarationFixture() {
		super();
		initialize();
		fixtureContractAction = (FixtureContractAction) CommonVars.getApplicationContext()
				.getBean("fixtureContractAction");
		List list = fixtureContractAction.findCustomsDeclarationFixture(new Request(
				CommonVars.getCurrUser()));
		initTable(list);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(274, 200);
		this.setLayout(new BorderLayout());
		this.add(getJScrollPane(), BorderLayout.CENTER);
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

	private void initTable(List list) {
		JTableListModel.dataBind(jTable, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("报关单号", "customsDeclarationCode", 100));
				list.add(addColumn("进口日期 ", "impExpDate", 100));
				list.add(addColumn("设备名称", "commName", 100));
				list.add(addColumn("规格", "commSpec", 100));
				list.add(addColumn("报关单数量", "amount", 100));
				list.add(addColumn("设备性质", "fixKind", 100));
//				list.add(addColumn("加入位置数量", "locationAmount", 100));
				list.add(addColumn("报关单项号", "customsDeclaItemNo", 100));
				list.add(addColumn("商品编码", "complex.code", 100));
				list.add(addColumn("手册号", "emsNo", 100));
				list.add(addColumn("协议号", "agreementNo", 100));
				list.add(addColumn("报关单流水号", "customsBillSeqNo", 100));
				return list;
			}
		});
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText(change(value));
						return this;
					}
				
				});
		tableModel = (JTableListModel) jTable.getModel();
		
	}
	
	private String change(Object value){
		if(value==null)
			return "";
		else
			return Integer.valueOf(value.toString())==0?"不作价设备":"借用设备";
			
	}
	
	public List getCustomsDeclarationFixture(){
		if(tableModel==null){
			return new ArrayList();
		}else{
			return tableModel.getList();
		}
	}

} // @jve:decl-index=0:visual-constraint="10,10"
