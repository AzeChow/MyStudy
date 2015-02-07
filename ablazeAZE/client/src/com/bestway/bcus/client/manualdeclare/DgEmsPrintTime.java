/*
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgEmsPrintTime extends JDialogBase {

	private JPanel				jContentPane	= null;
	private JFormattedTextField	tf				= null;
	private JPanel				jPanel			= null;
	private JLabel				jLabel			= null;
	private JButton				btnOk			= null;
	private JButton				jButton1		= null;
	private boolean				ok				= false;

	/**
	 * This method initializes
	 * 
	 */
	public DgEmsPrintTime() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(281, 141));
		this.setTitle("按变更次数打印");
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getJButton1(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes tf
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTf() {
		if (tf == null) {
			tf = new JFormattedTextField();
			tf.setFormatterFactory(getDefaultFormatterFactory());
			tf.setBounds(new java.awt.Rectangle(69, 15, 142, 20));
		}
		return tf;
	}

	 /**
     * This method initializes defaultFormatterFactory
     * 
     * @return javax.swing.text.DefaultFormatterFactory
     */
    private DefaultFormatterFactory defaultFormatterFactory = null;
    private DefaultFormatterFactory getDefaultFormatterFactory() {
        if (defaultFormatterFactory == null) {
            defaultFormatterFactory = new DefaultFormatterFactory();
            defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
            defaultFormatterFactory.setEditFormatter(getNumberFormatter());
        }
        return defaultFormatterFactory;
    }

    /**
     * This method initializes numberFormatter
     * 
     * @return javax.swing.text.NumberFormatter
     */
    private NumberFormatter numberFormatter = null;
    private NumberFormatter getNumberFormatter() {
        if (numberFormatter == null) {
            numberFormatter = new NumberFormatter();
        }
        return numberFormatter;
    }
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(14, 15, 51, 20));
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel.setText("变更次数");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new java.awt.Rectangle(20, 17, 236, 51));
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel.add(getTf(), null);
			jPanel.add(jLabel, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new java.awt.Rectangle(131, 75, 58, 22));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ok = true;
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
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new java.awt.Rectangle(197, 75, 58, 22));
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ok = false;
					dispose();
				}
			});
		}
		return jButton1;
	}
	
	
	/**
	 * 获得变更次数
	 * @return
	 */
	public int getTime(){
		int returnInt = -1;
		if(tf.getValue()!=null){
			Double d = Double.valueOf(tf.getValue().toString());
			returnInt = d.intValue() ;
		}
		return returnInt;
	}

	
	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}
} 

