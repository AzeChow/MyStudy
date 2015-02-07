/*
 * Created on 2004-7-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;


import java.text.ParseException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.JTextField;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgVersion extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JButton jButton = null;
	private JButton jButton1 = null;
	private boolean	ok	= false;
	private Integer dValue = null;
	public EmsHeadH2kExg emsHeadH2kExg = null;
	private DefaultFormatterFactory defaultFormatterFactory = null;   //  @jve:decl-index=0:parse
	private NumberFormatter numberFormatter = null;   //  @jve:decl-index=0:parse
	private JTextField jTextField = null;
	private ManualDeclareAction manualdeclearAction = null;
	/**
	 * This is the default constructor
	 */
	public DgVersion() {
		super();
		manualdeclearAction = (ManualDeclareAction) CommonVars
		.getApplicationContext().getBean("manualdeclearAction");
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(236, 123);
		this.setTitle("单耗版本号管理");
		this.setContentPane(getJContentPane());
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			javax.swing.JLabel jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(13, 27, 79, 17);
			jLabel.setText("单耗版本号：");
			jContentPane.add(jLabel, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJTextField(), null);
		}
		return jContentPane;
	}
	/**

	 * This method initializes jButton	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(35, 57, 67, 23);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {
					getResult();
					List list = manualdeclearAction.findExistVersion(new Request(CommonVars
							.getCurrUser()),DgVersion.this.getEmsHeadH2kExg(),jTextField.getText());
					if (list != null && !list.isEmpty()){
						JOptionPane.showMessageDialog(
								DgVersion.this, "对不起，版本号重复,请重新输入！",
								"确认", 2);
						jTextField.setText("");
						return;
					}
					DgVersion.this.setOk(true);
					DgVersion.this.dispose();	
				}
			});

		}
		return jButton;
	}
	private void getResult()
	{
		if (jTextField.getText().equals("")){
			jTextField.setText("0");
			}
		DgVersion.this.setDValue(Integer.valueOf(jTextField.getText()));
	}
	private Double strToDouble(String value) { //转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return (Double) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**

	 * This method initializes jButton1	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(136, 57, 65, 23);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					DgVersion.this.setOk(false);
					DgVersion.this.dispose();	
				}
			});

		}
		return jButton1;
	}

	/**
	 * @return Returns the ok.
	 */
	public boolean isOk() {
		return ok;
	}
	/**
	 * @param ok The ok to set.
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	/**
	 * @return Returns the dValue.
	 */
	public Integer getDValue() {
		return dValue;
	}
	/**
	 * @param value The dValue to set.
	 */
	public void setDValue(Integer value) {
		dValue = value;
	}
	/**

	 * This method initializes defaultFormatterFactory	

	 * 	

	 * @return javax.swing.text.DefaultFormatterFactory	

	 */    
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
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
		}
		return numberFormatter;
	}

	/**

	 * This method initializes jTextField	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(95, 25, 114, 22);
		}
		return jTextField;
	}

	/**
	 * @return Returns the emsHeadH2kExg.
	 */
	public EmsHeadH2kExg getEmsHeadH2kExg() {
		return emsHeadH2kExg;
	}
	/**
	 * @param emsHeadH2kExg The emsHeadH2kExg to set.
	 */
	public void setEmsHeadH2kExg(EmsHeadH2kExg emsHeadH2kExg) {
		this.emsHeadH2kExg = emsHeadH2kExg;
	}
       }  //  @jve:decl-index=0:visual-constraint="10,10"
