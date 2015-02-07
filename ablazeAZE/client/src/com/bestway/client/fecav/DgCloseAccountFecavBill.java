/*
 * Created on 2005-7-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.fecav;

import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.FecavState;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavBillStrike;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCloseAccountFecavBill extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private List lsResult = null;

	private List lsFecavBill =null;
	private FecavAction fecavAction = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JCalendarComboBox cbbStrikeDate = null;

	private JTextField tfStrikeMan = null;

	public List getLsFecavBill() {
		return lsFecavBill;
	}

	public void setLsFecavBill(List lsFecavBill) {
		this.lsFecavBill = lsFecavBill;
	}

	/**
	 * This is the default constructor
	 */
	public DgCloseAccountFecavBill() {
		super();
		initialize();
		fecavAction = (FecavAction) CommonVars.getApplicationContext()
				.getBean("fecavAction");
		initUIComponents();
	}

	/**
	 * 初始化用户名称
	 *
	 */
	private void initUIComponents(){
		if (CommonVars.getCurrUser() != null) {
			AclUser aclUser = (AclUser) CommonVars.getCurrUser();
			this.tfStrikeMan.setText(aclUser.getUserName());
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("核销单关帐");
		this.setSize(341, 179);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(34,59,78,24));
			jLabel4.setText("关帐日期： ");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(34,30,78,24));
			jLabel3.setText("关帐者：");
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getJCalendarComboBox(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(50, 95, 82, 26);
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if ("".equals(tfStrikeMan.getText().trim())) {
						JOptionPane.showMessageDialog(DgCloseAccountFecavBill.this,
								"关帐人不能为空,请检查输入", "提示",
								JOptionPane.OK_OPTION);
						return;
					}
					if (cbbStrikeDate.getDate()==null) {
						JOptionPane.showMessageDialog(DgCloseAccountFecavBill.this,
								"关帐日期不能为空,请检查输入", "提示",
								JOptionPane.OK_OPTION);
						return;
					}
					String closeAccountMan=tfStrikeMan.getText();
					Date closeAccountDate=cbbStrikeDate.getDate();
//					for(int i=0;i<lsFecavBill.size();i++){
//						FecavBillStrike f = (FecavBillStrike)lsFecavBill.get(i);
//						f.setCloseAccountMan(signInMan);
//						f.setCloseAccountDate(signInDate);
//						f.setFecavState(FecavState.CLOSE_ACCOUNT );
//					}
					lsResult = fecavAction.closeAccountFecavBill(new Request(
							CommonVars.getCurrUser()),lsFecavBill,closeAccountMan,closeAccountDate);
					dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(188, 95, 82, 26);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * @return Returns the lsResult.
	 */
	public List getLsResult() {
		return lsResult;
	}

	/**
	 * @param lsResult
	 *            The lsResult to set.
	 */
	public void setLsResult(List lsResult) {
		this.lsResult = lsResult;
	}

	/**
	 * This method initializes jCalendarComboBox	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (cbbStrikeDate == null) {
			cbbStrikeDate = new JCalendarComboBox();
			cbbStrikeDate.setBounds(new java.awt.Rectangle(112,58,191,24));
		}
		return cbbStrikeDate;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (tfStrikeMan == null) {
			tfStrikeMan = new JTextField();
			tfStrikeMan.setBounds(new java.awt.Rectangle(112,29,191,24));
		}
		return tfStrikeMan;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
