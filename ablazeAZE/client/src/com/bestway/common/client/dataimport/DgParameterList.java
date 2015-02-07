/*
 * Created on 2004-11-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;


import java.util.List;
import java.util.Vector;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.manualdeclare.FmEmsEdiTr;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DBDataRoot;
import com.bestway.bcus.dataimport.entity.ParameterList;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgParameterList extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JTableListModel tableModel = null;
	private DataImportAction dataImportAction = null;
	/**

	 * This method initializes jToolBar	

	 * 	

	 * @return javax.swing.JToolBar	

	 */    
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton3());
		}
		return jToolBar;
	}

	/**

	 * This method initializes jButton	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("增加");
			jButton.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					DgParameterListEdit root = new DgParameterListEdit();
					root.setAdd(true);
					root.setTableModel(tableModel);
					root.setVisible(true);
				}
			});

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
			jButton1.setText("修改");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgParameterList.this,
								"请选择你要修改的资料", "确认", 2);
						return;
					}
					DgParameterListEdit root = new DgParameterListEdit();
					root.setAdd(false);
					root.setTableModel(tableModel);
					root.setVisible(true);
				}
			});

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
			jButton2.setText("删除");
			jButton2.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (JOptionPane.showConfirmDialog(DgParameterList.this,
							"确定删除吗?", "提示", 0)== 0) {
						if (tableModel.getCurrentRow()==null){
							JOptionPane.showMessageDialog(DgParameterList.this,"请选中要删除的资料！","提示",2);
							return;
						}
						ParameterList obj = (ParameterList) tableModel.getCurrentRow();
						try{
							dataImportAction.deleteParameterList(new Request(CommonVars.getCurrUser()),obj);
							tableModel.deleteRow(obj);
						} catch (Exception e1){
							JOptionPane.showMessageDialog(DgParameterList.this,"已被引用，不能删除！","提示",2);
						}
					}
				}
			});

		}
		return jButton2;
	}

	/**

	 * This method initializes jButton3	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("关闭");
			jButton3.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					DgParameterList.this.dispose();
				}
			});

		}
		return jButton3;
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
	 * This is the default constructor
	 */
	public DgParameterList() {
		super();
		dataImportAction = (DataImportAction) CommonVars
	             .getApplicationContext().getBean("dataImportAction");
		initialize();
	}
	private void initTable(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();		
						list.add(addColumn("名称", "pname", 100));
						list.add(addColumn("值", "pvalue", 400));
						list.add(addColumn("是否当前日期", "isNowDate", 120));
						return list;
					}
				});
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("参数设置操作");
		this.setSize(727, 435);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				initData();
			}
		});
		
	}
	private void initData(){
		List list = dataImportAction.findParameterList(new Request(CommonVars.getCurrUser()));
		if (list!=null && !list.isEmpty()){
			initTable(list);
		} else {
			initTable(new Vector());
		}
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
