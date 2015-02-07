package com.bestway.client.fecav;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.fecav.action.FecavAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgBlankOutFecavBill extends JDialogBase {

	private JPanel jContentPane = null;
	private JLabel jLabel1 = null;
	private JScrollPane jScrollPane = null;
	private JTextArea tfReason = null;
	private JButton btnOk = null;
	private JButton btnCancel = null;
	
	private List lsFecavBill=null;
	
	private boolean isOk=false;
	
	private FecavAction fecavAction = null;
	private JLabel jLabel = null;
	private JCalendarComboBox cbbCheckOutData = null;
	/**
	 * This method initializes 
	 * 
	 */
	public DgBlankOutFecavBill() {
		super();
		initialize();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
		"fecavAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new java.awt.Dimension(394,262));
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setContentPane(getJContentPane());
        this.setTitle("核销单作废原因");
			
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(37,162,165,23));
			jLabel.setText("请输入核销作废日期：");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(33,16,163,22));
			jLabel1.setText("请输入作废遗失原因:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getCbbCheckOutData(), null);
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
			jScrollPane.setBounds(new java.awt.Rectangle(32,38,321,118));
			jScrollPane.setViewportView(getTfReason());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	public JTextArea getTfReason() {
		if (tfReason == null) {
			tfReason = new JTextArea();
		}
		return tfReason;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new java.awt.Rectangle(43,191,72,24));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if("".equals(tfReason.getText().trim())){
						JOptionPane.showMessageDialog(DgBlankOutFecavBill.this,"请输入作废遗失的原因"
								,"提示",JOptionPane.OK_OPTION);
						return;
					}
					if(lsFecavBill!=null&&lsFecavBill.size()>0){
						lsFecavBill=fecavAction.blankOutFecavBill(new Request(
							CommonVars.getCurrUser()),lsFecavBill,tfReason.getText(), cbbCheckOutData.getDate());
						isOk=true;
						dispose();
					}
					
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
	private JButton getJButton1() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new java.awt.Rectangle(250,191,72,24));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	public List getLsFecavBill() {
		return lsFecavBill;
	}

	public void setLsFecavBill(List lsFecavBill) {
		this.lsFecavBill = lsFecavBill;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * This method initializes jCalendarComboBox	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	public JCalendarComboBox getCbbCheckOutData() {
		if (cbbCheckOutData == null) {
			cbbCheckOutData = new JCalendarComboBox();
			cbbCheckOutData.setBounds(new java.awt.Rectangle(209,163,144,23));
		}
		return cbbCheckOutData;
	}

}  //  @jve:decl-index=0:visual-constraint="93,14"
