package com.bestway.client.custom.common;

import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
/**
 * 显示报关单的错误信息类
 * @author ?
 *
 */
public class DgShowCustomerError extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	
	private JToolBar jToolBar = null;
	
	private JButton jbtnYes = null;
	
	private JButton jbtnNo = null;
	
	private JTable jTable = null;
	
	private JButton jbtnCancel = null; 
	
	private JScrollPane jScrollPane = null;
	
	private JTableListModel tableModelDetail = null;
	
	private List list = null;  //  @jve:decl-index=0:
	
	private boolean isDzscEmsPor = false;
	
	int  flag = 0;

	private JButton btnReEffective = null;

	private JButton btnReCheck = null;
	
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public DgShowCustomerError(JFrame fserr){
		super(fserr,true);
		initialize();
	}
	
	public DgShowCustomerError(List list){
		if(this.getList() == null)
		this.setList(list);
		initialize();
	}

	public DgShowCustomerError(List list, boolean isDzscEmsPor){
		this.isDzscEmsPor = isDzscEmsPor;
		if(this.getList() == null)
		this.setList(list);
		initialize();
	}
	private void initialize() {
		this.setTitle("报关单错误列表");
		this.setSize(539, 350);
		this.setContentPane(getJContentPane());
		this.initTable(list);
		this.setVisible(true);
	}

	public JButton getJbtnNo() {
		if(jbtnNo == null){
			jbtnNo = new JButton();
			jbtnNo.setText("强制生效");
			jbtnNo.setPreferredSize(new Dimension(60, 30));
			jbtnNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					flag = 1;
					dispose();
				}
			});
		}
		return jbtnNo;
	}

	
	public JButton getJbtnCancel() {
		if(jbtnCancel == null){
			jbtnCancel = new JButton();
			jbtnCancel.setText("关闭");
			jbtnCancel.setPreferredSize(new Dimension(60, 30));
			jbtnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					flag = 3;
					dispose();
				}
			});
		}
		return jbtnCancel;
	}

	public void setJbtnCancel(JButton jbtnCancel) {
		this.jbtnCancel = jbtnCancel;
	}

	public JButton getJbtnYes() {
		if(jbtnYes == null){
			jbtnYes = new JButton();
			jbtnYes.setText("强制检查");
			jbtnYes.setPreferredSize(new Dimension(60, 30));
			jbtnYes.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					flag = 2;
					dispose();
				}
			});
		}
		return jbtnYes;
	}

	public javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJSrollPane(), BorderLayout.CENTER);
			if(!isDzscEmsPor)
				jContentPane.add(getJToolBar(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	public JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setRowHeight(25);
		}
		return jTable;
	}

	public JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.add(getJbtnNo(),BorderLayout.CENTER);
			jToolBar.add(getJbtnYes(),BorderLayout.CENTER);
			jToolBar.add(getBtnReEffective());
			jToolBar.add(getBtnReCheck());
			jToolBar.add(getJbtnCancel(),BorderLayout.CENTER);
		}
		return jToolBar;
	}

	public JScrollPane getJSrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}
	
	public void initTable(final List list) {
		tableModelDetail = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						if(isDzscEmsPor)
							list.add(addColumn("错误内容","error", 300));
						else{
							list.add(addColumn("报关单号","customsDeclarationCode",200));
							list.add(addColumn("错误内容","error",300));
						}
						return list;
					}
		});
		
	}

	/**
	 * This method initializes btnReEffective	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnReEffective() {
		if (btnReEffective == null) {
			btnReEffective = new JButton();
			btnReEffective.setText("强制取消生效");
			btnReEffective.setPreferredSize(new Dimension(84, 30));
			btnReEffective.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					flag = 4;
					dispose();
				}
			});
		}
		return btnReEffective;
	}

	/**
	 * This method initializes btnReCheck	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnReCheck() {
		if (btnReCheck == null) {
			btnReCheck = new JButton();
			btnReCheck.setText("强制取消检查");
			btnReCheck.setPreferredSize(new Dimension(84, 30));
			btnReCheck.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					flag = 5;
					dispose();
				}
			});
		}
		return btnReCheck;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
