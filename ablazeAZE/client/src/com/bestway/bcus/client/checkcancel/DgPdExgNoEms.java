package com.bestway.bcus.client.checkcancel;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgPdExgNoEms extends JDialogBase {

	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTable tbPdExgNoEms = null;
	private JPanel jPanel2 = null;
	private JButton jButton1 = null;
	private JTableListModel tableModel = null;
    private  List returnList=new ArrayList();  //  @jve:decl-index=0:
    
    
	public List getReturnList() {
		return returnList;
	}

	public void setReturnList(List returnList) {
		this.returnList = returnList;
	}

	/**
	 * This is the default constructor
	 */
	public DgPdExgNoEms() {
		super();
		initialize();

	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(tbPdExgNoEms, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("料号", "ptNo", 110));
						list.add(addColumn("品名", "ptName", 120));
						list.add(addColumn("规格", "ptSpec", 120));
						list.add(addColumn("工单号", "workBillNo", 100));
						list.add(addColumn("版本号", "versionNo", 80));
						list.add(addColumn("计量单位", "calUnit.name", 80));
						list.add(addColumn("盘点数量", "pdNum", 80));
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
		this.setSize(709, 458);
		this.setContentPane(getJContentPane());
		this.setTitle("查看未备案信息");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				initTable(returnList);
			}
		});
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
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getJPanel2(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbPdExgNoEms());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbPdExgNoEms
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbPdExgNoEms() {
		if (tbPdExgNoEms == null) {
			tbPdExgNoEms = new JTable();
		}
		return tbPdExgNoEms;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.add(getJButton1(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgPdExgNoEms.this.dispose();
				}
			});
		}
		return jButton1;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
