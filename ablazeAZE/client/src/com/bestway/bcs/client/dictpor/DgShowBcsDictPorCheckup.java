package com.bestway.bcs.client.dictpor;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.BorderLayout;

/**
 * @author fhz
 *  显示备案资料库备案的检查结果
 */
public class DgShowBcsDictPorCheckup extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	
	private JTable jTable = null;
	
	private JScrollPane jScrollPane = null;
	
	private AttributiveCellTableModel tableModelDetail = null;
	
	private List list = null;
	
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public DgShowBcsDictPorCheckup(){
		super();
		initialize();
	}
	
	/**
	 * 
	 */
	private void initialize() {
		setTitle("备案资料库检查结果");
		setSize(766, 350);
		setContentPane(getJContentPane());
		
	}

	public void setVisible(boolean b) {
		if (b) {
			if(list==null)
				list = new ArrayList();
			System.out.println("list.size()2--------------------"+list.size());
			initTable(list);
		}
		super.setVisible(b);
	}
	
	/**
	 * @return
	 */
	public javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJSrollPane(), BorderLayout.CENTER);
		
		}
		return jContentPane;
	}

	/**
	 * @return
	 */
	public JTable getJTable() {
		if (jTable == null) {
			jTable = new MultiSpanCellTable();;
		}
		return jTable;
	}

	/**
	 * @return
	 */
	public JScrollPane getJSrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}
	
	/**
	 * @param list
	 */
	public void initTable(List list) {
		tableModelDetail = new AttributiveCellTableModel((MultiSpanCellTable) jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("企业内部编号","copEmsNo", 180));
						list.add(addColumn("基本资料","headErr", 400));
						list.add(addColumn("料件表","imgErr",400));
						list.add(addColumn("成品表","exgErr",400));
						return list;
					}
					
		});
		((MultiSpanCellTable) jTable).combineRows(1, new int[] { 1, 2});
		
	}
}
