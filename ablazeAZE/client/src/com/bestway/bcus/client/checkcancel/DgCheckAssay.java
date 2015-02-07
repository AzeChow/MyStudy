/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

/**
 * @author Administratorf
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCheckAssay extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private CommonBaseCodeAction commonBaseCodeObj = null;

	private JPanel jPanel = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel11 = null;

	private JPanel jPanel12 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null;
	
	private AttributiveCellTableModel tableModelImgResult = null;    //料件结果
	private AttributiveCellTableModel tableModelExgResult = null;    //成品结果

	/**
	 * This is the default constructor
	 */
	public DgCheckAssay() {
		super();
		jTable = new MultiSpanCellTable();
		jTable1 = new MultiSpanCellTable();
		initialize();
		commonBaseCodeObj = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel2());
		this.setSize(713, 504);
		this.setTitle("中期核查数据与电子底帐的比较分析");

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				initTableImgResult(new Vector());
				initTableExgResult(new Vector());
				
			}
		});
	}
	private void initTableImgResult(final List list){
		tableModelImgResult = new AttributiveCellTableModel ((MultiSpanCellTable) jTable, list,
				new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("帐册序号", "emsSeqNum", 80));
				list.add(addColumn("名称", "spec", 100));
				list.add(addColumn("规格", "name", 120));
				list.add(addColumn("单位", "unit", 60));						
				list.add(addColumn("中期核查", "beginNum", 80)); 
				list.add(addColumn("电子底帐", "cancelAddNum", 80));
				list.add(addColumn("差额", "cancelMinNum", 80));
				return list;
			}
		});
		TableColumnModel cm = jTable.getColumnModel();
		ColumnGroup gUse= new ColumnGroup("本期进出结存");
		gUse.add(cm.getColumn(5));
		gUse.add(cm.getColumn(6));
		gUse.add(cm.getColumn(7));	
		GroupableTableHeader header = (GroupableTableHeader) jTable.getTableHeader();
        header.addColumnGroup(gUse);
	}
	
	
	private void initTableExgResult(final List list){
		tableModelImgResult = new AttributiveCellTableModel ((MultiSpanCellTable) jTable1, list,
				new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("帐册序号", "emsSeqNum", 80));
				list.add(addColumn("版本", "emsSeqNum", 80));
				list.add(addColumn("名称", "spec", 100));
				list.add(addColumn("规格", "name", 120));
				list.add(addColumn("单位", "unit", 60));						
				list.add(addColumn("中期核查", "beginNum", 80)); 
				list.add(addColumn("电子底帐", "cancelAddNum", 80));
				list.add(addColumn("差额", "cancelMinNum", 80));
				return list;
			}
		});
		TableColumnModel cm = jTable1.getColumnModel();
		ColumnGroup gUse= new ColumnGroup("本期进出结存");
		gUse.add(cm.getColumn(6));
		gUse.add(cm.getColumn(7));
		gUse.add(cm.getColumn(8));	
		GroupableTableHeader header = (GroupableTableHeader) jTable1.getTableHeader();
        header.addColumnGroup(gUse);
	}
	
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJPanel(), java.awt.BorderLayout.SOUTH);
			jPanel2.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * @return Returns the commonBaseCodeObj.
	 */
	public CommonBaseCodeAction getCommonBaseCodeObj() {
		return commonBaseCodeObj;
	}

	/**
	 * @param commonBaseCodeObj
	 *            The commonBaseCodeObj to set.
	 */
	public void setCommonBaseCodeObj(CommonBaseCodeAction commonBaseCodeObj) {
		this.commonBaseCodeObj = commonBaseCodeObj;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton2(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("计算");
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("说明");
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("退出");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCheckAssay.this.dispose();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("料   件", null, getJPanel11(), null);
			jTabbedPane.addTab("成   品", null, getJPanel12(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel11	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jPanel11 = new JPanel();
			jPanel11.setLayout(new BorderLayout());
			jPanel11.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel11;
	}

	/**
	 * This method initializes jPanel12	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel12() {
		if (jPanel12 == null) {
			jPanel12 = new JPanel();
			jPanel12.setLayout(new BorderLayout());
			jPanel12.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel12;
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
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
