package com.bestway.wjsz.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.waijing.action.WjszAction;

public class DgSContractWjCode extends JDialogBase {

	private JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JButton btnEdit = null;
	private JButton btnSave = null;
	private JButton btnUndo = null;
	private JButton btnPrevious = null;
	private JButton btnNext = null;
	private JButton btnClose = null;
	private JPanel jPanel = null;
	private JLabel jLabel1 = null;
	private JTextField jTextField1 = null;
	private int dataState = DataState.BROWSE;
	private JTableListModel tableModel = null;
    private WjszAction wjszAction = null;
    private int SelectInt = 0;
    
    private DzscEmsPorHead porhead = null;
    private Contract conHead = null;
	/**
	 * This is the default constructor
	 */
	public DgSContractWjCode() {
		super();
		wjszAction = (WjszAction) CommonVars.getApplicationContext().getBean("wjszAction");
		initialize();
	}

	
	public void setVisible(boolean b) {
        if (b) {
            if (dataState != DataState.ADD){//修改
                showData();                
            }            
            setState();
        }
        initkey();
        super.setVisible(b);        
    }
    
	 private void setState() {
         this.btnEdit.setEnabled(dataState == DataState.BROWSE);//编辑
         this.btnSave.setEnabled(dataState != DataState.BROWSE);//保存
         this.btnUndo.setEnabled(dataState != DataState.BROWSE);//取消
         this.btnPrevious.setEnabled(tableModel.hasPreviousRow());
         this.btnNext.setEnabled(tableModel.hasNextRow());
       
         this.jTextField1.setEditable(dataState != DataState.BROWSE);
     }
	
	//显示输入
    private void showData() {
    	if (SelectInt == 1){//电子手册
    		porhead = (DzscEmsPorHead)tableModel.getCurrentRow();
    		this.jTextField1.setText(porhead.getWjComputerNo());
    	} else if (SelectInt == 2){ //电子化手册
    		conHead = (Contract)tableModel.getCurrentRow();
    		this.jTextField1.setText(conHead.getWjComputerNo());
    	}
		
    }
    
    
    private void initkey(){
        List<Object> components = new ArrayList<Object>();
        components.add(this.jTextField1);
        components.add(this.btnSave);
        components.add(new Component[] { this.btnNext,this.btnClose });
        this.setComponentFocusList(components);
    }
    
    
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(441, 189);
		this.setContentPane(getJContentPane());
		this.setTitle("修改外经申请文号");
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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnUndo());
			jToolBar.add(getBtnPrevious());
			jToolBar.add(getBtnNext());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;//修改 
			        setState();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (SelectInt == 1){//电子手册
						porhead.setWjComputerNo(jTextField1.getText().trim());
						porhead = (DzscEmsPorHead) wjszAction.saveDzscEmsPorHead(new Request(CommonVars.getCurrUser()),porhead);
						tableModel.updateRow(porhead);
					} else if (SelectInt == 2){//电子化手册
						conHead.setWjComputerNo(jTextField1.getText().trim());
						conHead = (Contract) wjszAction.saveContract(new Request(CommonVars.getCurrUser()),conHead);
						tableModel.updateRow(conHead);
					}					
					dataState = DataState.BROWSE;
			        setState();
					
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnUndo() {
		if (btnUndo == null) {
			btnUndo = new JButton();
			btnUndo.setText("取消");
			btnUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showData();//取消
			        dataState = DataState.BROWSE;
			        setState();
				}
			});
		}
		return btnUndo;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setText("上笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.previousRow()) {
			            btnPrevious.setEnabled(false);
			            btnNext.setEnabled(true);
			        } else {
			            btnPrevious.setEnabled(true);
			            btnNext.setEnabled(true);
			        }
			        showData();//上笔
			        dataState = DataState.EDIT;
			        setState();
				}
			});
		}
		return btnPrevious;
	}

	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setText("下笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.nextRow()) {
			            btnPrevious.setEnabled(true);
			            btnNext.setEnabled(false);
			        } else {
			            btnPrevious.setEnabled(true);
			            btnNext.setEnabled(true);
			        }
			        showData();//下笔
			        dataState = DataState.EDIT;
			        setState();
				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes jButton5	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();//关闭
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(4,41,89,26));
			jLabel1.setText("外经申请文号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJTextField1(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new java.awt.Rectangle(92,41,301,26));
		}
		return jTextField1;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}


	public int getSelectInt() {
		return SelectInt;
	}


	public void setSelectInt(int selectInt) {
		SelectInt = selectInt;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
