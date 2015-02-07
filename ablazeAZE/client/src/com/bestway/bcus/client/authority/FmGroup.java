/**
 * EditBY xxm
 */
package com.bestway.bcus.client.authority;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.authority.action.AuthorityAction;
import com.bestway.common.authority.entity.AclGroup;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
public class FmGroup extends JInternalFrameBase {

	JTableListModel tableModel;
	JPanel jContentPane = null;
	AuthorityAction authorityAction = null;
	private JToolBar jToolBar = null;
	private JTable jTable = null; //  @jve:decl-index=0:visual-constraint="12,277"
	private JScrollPane jScrollPane = null;
	private JButton btnAdd = null;
	private JButton btnEdit = null;
	private JButton btnDelete = null;
	private JButton btnRefresh = null;

	private JButton jButton = null;
	public FmGroup() {
		super();
		authorityAction = (AuthorityAction) CommonVars
	       .getApplicationContext().getBean("authorityAction");
		initialize();
    }

	private void addDialog(boolean isAdd) {
		DgGroup dgGroup =new DgGroup();
		dgGroup.setIsAdd(isAdd);
		dgGroup.setTableModel(tableModel);
		dgGroup.setVisible(true);
	}

	private void initTable(List list) {
		tableModel = new JTableListModel(this.jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("群组名称", "groupName", 150));
						list.add(addColumn("群组描述", "groupDescription", 300));
						return list;
					}
				});
	}

	private void loadData() {
		List dataSource = authorityAction.findGroups(new Request(CommonVars.getCurrUser()));
		if (dataSource!=null && !dataSource.isEmpty()) {
			initTable(dataSource);
		} else {
			initTable(new Vector());
		}
	}

	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("群组");
		this.setHelpId("group");
		this.setSize(533, 325);
		this.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
		   public void internalFrameOpened(javax.swing.event.InternalFrameEvent e) {
				loadData();
		   }
		});

	}
	public JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 *  
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getJButton());
		}
		return jToolBar;
	}

	/**
	 * 
	 * This method initializes jTable
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 *  
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 *  
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes btnAdd
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					CommonVars.checkPermission("Authority","saveUser");
						addDialog(true);
				}
			});

		}
		return btnAdd;
	}
	/**
	 * 
	 * This method initializes btnEdit
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmGroup.this,
								"请选择你要修改的资料", "确认", 1);
						return;
					}
					addDialog(false);
				}
			});

		}
		return btnEdit;
	}

	/**
	 * 
	 * This method initializes btnDelete
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
	
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmGroup.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}
					AclGroup group = (AclGroup) tableModel.getCurrentRow();
					if (JOptionPane.showConfirmDialog(FmGroup.this,
							"你确定要删除此记录吗?", "确认", 0) == 0) {
						try{
						authorityAction.deleteGroup(new Request(CommonVars.getCurrUser()),
								group);}catch(Exception e1){
									JOptionPane.showMessageDialog(FmGroup.this,
											"你所选择的群组正在被引用，不能删除", "确认", 1);
									return;	
								}
						tableModel.deleteRow(group);
					}

				}
			});

		}
		return btnDelete;
	}

	/**
	 * 
	 * This method initializes btnRefresh
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmGroup.this.loadData();

				}
			});

		}
		return btnRefresh;
	}
	/**

	 * This method initializes jButton	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("关闭");
			jButton.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					dispose();

				}
			});

		}
		return jButton;
	}

 } //  @jve:decl-index=0:visual-constraint="35,18"
