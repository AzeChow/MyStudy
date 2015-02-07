/*
 * Created on 2005-3-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorWjHead;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgEditDzscEmsNo extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel2 = null;

	private JTextField tfFourName = null;

	private JButton btnOK = null;

	private JButton btnClose = null;

	private DzscAction dzscAction = null;
	
	private String oldEmsNo;//久的emsNo
	
	private String head = null;//用来判断是合同备案还是通关备案
	
	private String newEmsNo;//新的emsNo
	
	private Boolean hadChange=false;//判断是否有改变了emsNo

	/**
	 * This is the default constructor
	 */
	public DgEditDzscEmsNo() {
		super();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(335, 165);
		this.setContentPane(getJContentPane());
		this.setTitle("料件修改");

	}

	public void setVisible(boolean b) {
		if (b) {
			tfFourName.setText(oldEmsNo);
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel2 = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel2.setBounds(39, 24, 62, 22);
			jLabel2.setText("新手册号");
			jLabel2.setForeground(Color.blue);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getTfFourName(), null);
			jContentPane.add(getBtnOK(), null);
			jContentPane.add(getBtnClose(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFourName() {
		if (tfFourName == null) {
			tfFourName = new JTextField();
			tfFourName.setEditable(true);
			tfFourName.setBounds(100, 24, 183, 22);
		}
		return tfFourName;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(56, 82, 65, 25);
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String newEmsNo=tfFourName.getText().trim();
					if(newEmsNo.equals(oldEmsNo)){
						DgEditDzscEmsNo.this.dispose();
						return;
					}
					DgEditDzscEmsNo.this.newEmsNo=newEmsNo;
					new Edit(oldEmsNo,newEmsNo).start();
					
				}
			});
		}
		return btnOK;
	}

	class Edit extends Thread {
		String newEmsNo;
		String oldEmsNo;
		
		public Edit(String oldEmsNo ,String newEmsNo ){
			this.newEmsNo=newEmsNo;
			this.oldEmsNo=oldEmsNo;
		}
		
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在更新手册编号，请稍后...");
				hadChange=dzscAction.editDzscEmsNo(new Request(CommonVars.getCurrUser()),oldEmsNo,newEmsNo,head);
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEditDzscEmsNo.this, "手册编号更新成功", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEditDzscEmsNo.this, "更新手册编号更新失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				dispose();
				DgEditDzscEmsNo.this.dispose();
			}
		}
	}
	
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setBounds(189, 84, 65, 25);
			btnClose.setText("取消");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgEditDzscEmsNo.this.dispose();
				}
			});
		}
		return btnClose;
	}
	
	/**
	 * @param oldEmsNo the oldEmsNo to set
	 */
	public void setOldEmsNo(String oldEmsNo) {
		this.oldEmsNo = oldEmsNo;
	}

	/**
	 * @param tableModelWj the tableModelWj to set
	 */
	public void setHead(String head) {
		this.head = head;
	}

	/**
	 * @return the hadChange
	 */
	public Boolean getHadChange() {
		return hadChange;
	}

	/**
	 * @return the newEmsNo
	 */
	public String getNewEmsNo() {
		return newEmsNo;
	}

}
