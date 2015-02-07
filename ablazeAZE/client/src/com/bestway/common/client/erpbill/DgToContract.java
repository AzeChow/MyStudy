package com.bestway.common.client.erpbill;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Frame;
import java.awt.BorderLayout;
import javax.swing.JDialog;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.common.erpbill.action.OrderCommonAction;
import com.bestway.common.erpbill.entity.TempDateCheck;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JScrollPane;

public class DgToContract extends JDialogBase {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JComboBox cbbContract = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JTextField jTextField = null;

	private JTextField jTextField1 = null;

	private JButton jButton2 = null;

	private JLabel jLabel3 = null;
	
	private OrderCommonAction orderCommonAction = null;

	private Integer customType = null;  //  @jve:decl-index=0:
	
	private List orderDetailList = null;  //  @jve:decl-index=0:
	
	private List listCustomOrderDetail = null;
	
	private List listError = null;
	/**
	 * @param owner
	 */
	public DgToContract() {
		super();
		orderCommonAction = (OrderCommonAction) CommonVars.getApplicationContext()
    	.getBean("orderCommonAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(400, 300);
		this.setTitle("转合同");
		this.setContentPane(getJContentPane());
		this.cbbContract.removeAllItems();
		this.jButton1.setEnabled(false);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(8, 15, 377, 35));
			jLabel3.setText("");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(80, 150, 81, 24));
			jLabel2.setText("实际转合同数");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(80, 110, 70, 24));
			jLabel1.setText("未转合同数");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(80, 70, 70, 24));
			jLabel.setText("合同协议号");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setPreferredSize(new Dimension(300, 200));
			jContentPane.add(jLabel, null);
			jContentPane.add(getCbbContract(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(getJButton2(), null);
			jContentPane.add(jLabel3, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes cbbContract	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbContract() {
		if (cbbContract == null) {
			cbbContract = new JComboBox();
			cbbContract.setBounds(new Rectangle(170, 70, 120, 24));
		}
		return cbbContract;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(50, 220, 85, 24));
			jButton.setText("数据检查");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(cbbContract.getSelectedItem()==null){
						JOptionPane.showMessageDialog(DgToContract.this,
								"请选择出口合同号", "确认", 2);
						return;	
					}
					String emsNo = cbbContract.getSelectedItem().toString().trim();
					TempDateCheck tempDateCheck = orderCommonAction.dateCheckedList(new Request(
							CommonVars.getCurrUser()), orderDetailList, customType, emsNo);
					listCustomOrderDetail = tempDateCheck.getListCustomOrderDetail();
					listError = tempDateCheck.getListError();

					if(listError.size()>0){
						String message = getErrorMessage(listError,emsNo);
						jLabel.setText(message);
						jLabel.setForeground(Color.RED);
					}else{
						String message  = "该成品在手册编号为"+emsNo+"的通关合同备案中已备案.可以转合同！";
						jLabel.setText(message);
						jLabel.setForeground(Color.blue);
					}
					jButton1.setEnabled(true);
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
			jButton1.setBounds(new Rectangle(150, 220, 85, 24));
			jButton1.setText("转合同");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					if(jTextField1.getText()==null){
						JOptionPane.showMessageDialog(DgToContract.this,
								"转合同的数量不能为空", "确认", 2);
						return;	
					}

					Double notToContract = Double.valueOf(jTextField.getText().trim());
					Double toContract = Double.valueOf(jTextField1.getText().trim());
					if(toContract>notToContract){
						JOptionPane.showMessageDialog(DgToContract.this,
								"实际转合同数不能大于未转合同数", "确认", 2);
						return;	
					}
					String emsNo = cbbContract.getSelectedItem().toString().trim();
					orderCommonAction.ChangeToContract(new Request(
							CommonVars.getCurrUser()), listCustomOrderDetail, customType, emsNo,toContract);
				}
			});
		}

		return jButton1;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(170, 110, 120, 24));
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new Rectangle(170, 150, 120, 24));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(250, 220, 60, 24));
			jButton2.setText("关闭");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgToContract.this.dispose();
				}
			});
		}
		return jButton2;
	}
	
	public void setVisible(boolean b) {
		List contracts = orderCommonAction.findContractByProcessExe(new Request(
				CommonVars.getCurrUser()),customType);

		cbbContract.addItem(null);
		if(customType>1){
			for(int i = 0;i<contracts.size();i++){
				Contract contract = (Contract) contracts.get(i);
				String emsNo = contract.getEmsNo();
				cbbContract.addItem(emsNo);
			}			
		}else if(customType==1){
			for(int i = 0;i<contracts.size();i++){
				DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(i);
				String emsNo = contract.getEmsNo();
				cbbContract.addItem(emsNo);
			}			
		}
		super.setVisible(b);
	}
	public void setCustomType(Integer customType){
		this.customType = customType;		
	}
	public Integer getCustomType(){
		return customType;
	}
	private String getErrorMessage(List list,String emsNo){
		String message = "";
		for(int i = 0;i<list.size();i++){
			String ptNo = list.get(i).toString();
			String comm = ",";
			if(i==list.size()-1){
				comm ="";
			}
			message +=ptNo+comm;
		}
		message += "在手册编号为"+emsNo+"的通关合同备案中没有备案.不能转合同！";
		return message;
	}
}
