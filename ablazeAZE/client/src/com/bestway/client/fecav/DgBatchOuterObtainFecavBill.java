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
import com.bestway.fecav.entity.FecavBill;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgBatchOuterObtainFecavBill extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JCustomFormattedTextField tfNum = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:

	private List lsResult = null;  //  @jve:decl-index=0:

	private FecavAction fecavAction = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JCalendarComboBox cbbOuterObtainDate = null;

	private JTextField tfOuterObtain = null;

	private JTextField tfBeginNo = null;

	private JLabel jLabel = null;

	private JTextField tfEndNo = null;

	/**
	 * This is the default constructor
	 */
	public DgBatchOuterObtainFecavBill() {
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
			this.tfOuterObtain.setText(aclUser.getUserName());
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
		this.setTitle("批量外部领单");
		this.setSize(343, 269);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(37,82,77,24));
			jLabel.setText("核销单结束号:");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(36,141,78,24));
			jLabel4.setText("领单日期： ");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(36,112,78,24));
			jLabel3.setText("领单人：");
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel1.setBounds(36, 24, 78, 24);
			jLabel1.setText("核销单开始号:");
			jLabel2.setBounds(36, 53, 78, 24);
			jLabel2.setText("核销单份数:");
			jContentPane.add(getTfBeginNo(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getTfNum(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getJCalendarComboBox(), null);
			jContentPane.add(getTfOuterObtain(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfEndNo(), null);
			jContentPane.add(getTfOuterObtain(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jCustomFormattedTextField1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfNum() {
		if (tfNum == null) {
			tfNum = new JCustomFormattedTextField();
			tfNum.setBounds(114, 53, 191, 24);
			tfNum.setFormatterFactory(getDefaultFormatterFactory());
			tfNum.setText("");
			tfNum.getDocument().addDocumentListener(new DocumentListener() {

				public void insertUpdate(DocumentEvent e) {
					try {
						tfNum.commitEdit();
					} catch (ParseException e1) {
					}
					//showEndFecavBillCode();
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(38, 179, 82, 26);
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					if (tfPreFix.getText().length() != 12) {
//						JOptionPane.showMessageDialog(DgBatchObtainFecavBill.this,
//								"发票的版次号要等于12位,请检查输入", "提示",
//								JOptionPane.OK_OPTION);
//						return;
//					}
					// if(tfBeginSerialNo.getValue()==null||tfBeginSerialNo.getValue().toString().length()!=8){
					// JOptionPane.showMessageDialog(DgAutoMakeInvoice.this,"发票开始号要等于8位,请检查输入","提示",JOptionPane.OK_OPTION);
					// return;
					// }
					// if(tfEndSerialNo.getValue()==null||tfEndSerialNo.getValue().toString().length()!=8){
					// JOptionPane.showMessageDialog(DgAutoMakeInvoice.this,"发票开始号要等于8位,请检查输入","提示",JOptionPane.OK_OPTION);
					// return;
					// }
					if ("".equals(tfOuterObtain.getText().trim())){
						JOptionPane.showMessageDialog(DgBatchOuterObtainFecavBill.this,
								"领单人不能为空,请检查输入","提示",JOptionPane.OK_OPTION);
						return ;
					}
                    if ( "".equals(tfBeginNo.getText().trim())){
                    	JOptionPane.showMessageDialog(DgBatchOuterObtainFecavBill.this,
                    			"核销单开始号不能为空,请检查输入","提示",JOptionPane.OK_OPTION);
                    	return ;
                    }
					if(tfBeginNo.getText()!=null&&tfBeginNo.getText().toString().length()!=9){
						 JOptionPane.showMessageDialog(DgBatchOuterObtainFecavBill.this,"核销单开始号要为9位,请检查输入","提示",JOptionPane.OK_OPTION);
						 return;
					}
//					List<FecavBill> list = fecavAction.findFecavBill(
//							new Request(CommonVars.getCurrUser()));
//					String begin = tfBeginNo.getText().trim();
//					for(FecavBill element : list){
//						if(element.getCode().equals(begin)){
//							JOptionPane.showMessageDialog(DgBatchOuterObtainFecavBill.this,
//	                    			"核销单开始号已存在,请检查输入","提示",JOptionPane.OK_OPTION);
//							return;
//						}	
//					}
                    if(tfNum.getValue() == null
                    		|| Double.valueOf(tfNum.getValue().toString()).intValue()<=0){
                    	JOptionPane.showMessageDialog(DgBatchOuterObtainFecavBill.this,
                    			"核销单份数不能为空或小于等于0,请检查输入","提示",JOptionPane.OK_OPTION);
                    	return ;
                    }
                    
                    if (cbbOuterObtainDate.getDate() == null){
                    	JOptionPane.showMessageDialog(DgBatchOuterObtainFecavBill.this,
                    			"领单日期不能为空,请检查输入","提示",JOptionPane.OK_OPTION);
                    	return ;
                    }
                    String beginNo = tfBeginNo.getText().trim();
                    Integer num = Double.valueOf(tfNum.getValue().toString()).intValue();
                    String  outerObtain = tfOuterObtain.getText();
                    Date    outerObtainDate = cbbOuterObtainDate.getDate();
                    lsResult = fecavAction.batchOuterObtainFecavBill(new Request(
                    		CommonVars.getCurrUser()),beginNo,num,outerObtain,outerObtainDate);
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
			btnCancel.setBounds(220, 179, 82, 26);
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
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
			numberFormatter.setMinimum(0);
		}
		return numberFormatter;
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
		if (cbbOuterObtainDate == null) {
			cbbOuterObtainDate = new JCalendarComboBox();
			cbbOuterObtainDate.setBounds(new java.awt.Rectangle(114,140,191,24));
		}
		return cbbOuterObtainDate;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfOuterObtain() {
		if (tfOuterObtain == null) {
			tfOuterObtain = new JTextField();
			tfOuterObtain.setBounds(new java.awt.Rectangle(115,112,191,24));
		}
		return tfOuterObtain;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfBeginNo() {
		if (tfBeginNo == null) {
			tfBeginNo = new JTextField();
			tfBeginNo.setBounds(new java.awt.Rectangle(114,24,191,24));
		}
		return tfBeginNo;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfEndNo() {
		if (tfEndNo == null) {
			tfEndNo = new JTextField();
			tfEndNo.setBounds(new java.awt.Rectangle(114,81,191,24));
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
