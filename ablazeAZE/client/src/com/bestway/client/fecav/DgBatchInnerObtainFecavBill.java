/*
 * Created on 2005-7-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.fecav;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.fecav.action.FecavAction;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgBatchInnerObtainFecavBill extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private List lsResult = null;

//	private List lsFecavBill =null;
	private FecavAction fecavAction = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JCalendarComboBox cbbInnerObtainDate = null;

	private JTextField tfInnerObtain = null;

	private JLabel jLabel = null;

	private JTextField tfBeginNo = null;

	private JCustomFormattedTextField tfNum = null;

	private JLabel jLabel1 = null;

	private JTextField tfEndNo = null;

	private JLabel jLabel2 = null;

//	public List getLsFecavBill() {
//		return lsFecavBill;
//	}
//
//	public void setLsFecavBill(List lsFecavBill) {
//		this.lsFecavBill = lsFecavBill;
//	}

	/**
	 * This is the default constructor
	 */
	public DgBatchInnerObtainFecavBill() {
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
			this.tfInnerObtain.setText(aclUser.getUserName());
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
		this.setTitle("批量内部领单");
		this.setSize(341, 251);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(36,77,75,27));
			jLabel2.setText("\u6838\u9500\u5355\u7ed3\u675f\u53f7:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(36,49,75,25));
			jLabel1.setText("\u6838\u9500\u5355\u4efd\u6570:");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(36,20,75,23));
			jLabel.setText("\u6838\u9500\u5355\u5f00\u59cb\u53f7:");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(36,137,75,24));
			jLabel4.setText("领单日期： ");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(36,108,75,24));
			jLabel3.setText("领单人：");
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getTfInnerObtain(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getJCalendarComboBox(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfBeginNo(), null);
			jContentPane.add(getTfNum(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfEndNo(), null);
			jContentPane.add(jLabel2, null);
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
			btnOk.setBounds(50, 173, 82, 26);
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if ( "".equals(tfBeginNo.getText().trim())){
                    	JOptionPane.showMessageDialog(DgBatchInnerObtainFecavBill.this,
                    			"核销单开始号不能为空,请检查输入","提示",JOptionPane.OK_OPTION);
                    	return ;
                    }
                    if(tfNum.getValue() == null
                    		|| Double.valueOf(tfNum.getValue().toString()).intValue()<=0){
                    	JOptionPane.showMessageDialog(DgBatchInnerObtainFecavBill.this,
                    			"核销单份数不能为空或小于等于0,请检查输入","提示",JOptionPane.OK_OPTION);
                    	return ;
                    }
					if("".equals(tfInnerObtain.getText().trim())){
						JOptionPane.showMessageDialog(DgBatchInnerObtainFecavBill.this,
								"核销单内部领单人不能为空,请检查输入","提示",JOptionPane.OK_OPTION);
						return ;
					}

					if (cbbInnerObtainDate.getDate()==null) {
						JOptionPane.showMessageDialog(DgBatchInnerObtainFecavBill.this,
								"领单日期不能为空,请检查输入", "提示",
								JOptionPane.OK_OPTION);
						return;
					}
					String beginNo = tfBeginNo.getText().trim();
                    Integer num = Double.valueOf(tfNum.getValue().toString()).intValue();
					String innerObtain=tfInnerObtain.getText();
					Date innerObtainDate=cbbInnerObtainDate.getDate();
					lsResult = fecavAction.batchInnerObtainFecavBill(new Request(
							CommonVars.getCurrUser()),beginNo,num,innerObtain,innerObtainDate);
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
			btnCancel.setBounds(188, 173, 82, 26);
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
		if (cbbInnerObtainDate == null) {
			cbbInnerObtainDate = new JCalendarComboBox();
			cbbInnerObtainDate.setBounds(new java.awt.Rectangle(114,136,191,24));
		}
		return cbbInnerObtainDate;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfInnerObtain() {
		if (tfInnerObtain == null) {
			tfInnerObtain = new JTextField();
			tfInnerObtain.setBounds(new java.awt.Rectangle(114,107,191,24));
		}
		return tfInnerObtain;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfBeginNo() {
		if (tfBeginNo == null) {
			tfBeginNo = new JTextField();
			tfBeginNo.setBounds(new java.awt.Rectangle(114,20,191,24));
		}
		return tfBeginNo;
	}

	/**
	 * This method initializes jCustomFormattedTextField	
	 * 	
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField	
	 */
	private JCustomFormattedTextField getTfNum() {
		if (tfNum == null) {
			NumberFormatter numberFormatter = new NumberFormatter();
			numberFormatter.setMinimum(0);
			DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDisplayFormatter(numberFormatter);
			defaultFormatterFactory.setDefaultFormatter(numberFormatter);
			defaultFormatterFactory.setEditFormatter(numberFormatter);
			tfNum = new JCustomFormattedTextField();
			tfNum.setBounds(new java.awt.Rectangle(114,49,191,24));
			tfNum.setFormatterFactory(defaultFormatterFactory);
			tfNum.setText("");
			tfNum.getDocument().addDocumentListener(new DocumentListener() {

				public void insertUpdate(DocumentEvent e) {
					try {
						tfNum.commitEdit();
					} catch (ParseException e1) {
					}
					showEndFecavBillCode();
				}

				public void removeUpdate(DocumentEvent e) {
					try {
						tfNum.commitEdit();
					} catch (ParseException e1) {
					}
					showEndFecavBillCode();
				}

				public void changedUpdate(DocumentEvent e) {
//					showEndFecavBillCode();
				}
			});
		}
		return tfNum;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfEndNo() {
		if (tfEndNo == null) {
			tfEndNo = new JTextField();
			tfEndNo.setBounds(new java.awt.Rectangle(114,77,191,24));
			tfEndNo.setEditable(false);
		}
		return tfEndNo;
	}
	
	private void showEndFecavBillCode(){
		if ( "".equals(tfBeginNo.getText().trim())){
        	this.tfEndNo.setText("");
        	return ;
        }
        if(tfNum.getValue() == null
        		|| Double.valueOf(tfNum.getValue().toString()).intValue()<=0){
        	this.tfEndNo.setText("");
        	return ;
        }
        String beginNo = tfBeginNo.getText().trim();
        Integer num = Double.valueOf(tfNum.getValue().toString()).intValue();
		String endNo=fecavAction.getEndFecavBillNo(new Request(
        		CommonVars.getCurrUser(),true),beginNo,num);
		this.tfEndNo.setText(endNo);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
