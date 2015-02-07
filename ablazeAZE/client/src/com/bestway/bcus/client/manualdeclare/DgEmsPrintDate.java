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

import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import javax.swing.SwingConstants;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

/**
 * @author fhz
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgEmsPrintDate extends JDialogBase {

	private JPanel				jContentPane	= null;
	private JCalendarComboBox	ccbBegin		= null;
	private JPanel				jPanel			= null;
	private JLabel				jLabel			= null;
	private JButton				btnOk			= null;
	private JButton				jButton1		= null;
	private boolean				ok				= false;
	private JCalendarComboBox ccbEnd = null;
	private JLabel jLabel1 = null;
	private JPanel jPanel1 = null;
	private JRadioButton jRadioButton = null;
	private JRadioButton jRadioButton1 = null;
	private ButtonGroup buttonGroup = null;  //  @jve:decl-index=0:visual-constraint="401,54"
	/**
	 * This method initializes
	 * 
	 */
	public DgEmsPrintDate() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(347, 187));
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("按变更时间打印");
		this.setContentPane(getJContentPane());
		this.getButtonGroup();
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
			jContentPane.add(getJPanel1(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes tf
	 * 
	 * @return javax.swing.JCalendarComboBox
	 */
	private JCalendarComboBox getCcbBegin() {
		if (ccbBegin == null) {
			ccbBegin = new JCalendarComboBox();
			ccbBegin.setBounds(new Rectangle(58, 16, 113, 20));
		}
		return ccbBegin;
	}
   
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(174, 16, 20, 20));
			jLabel1.setText("到");
			jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(5, 15, 51, 20));
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel.setText("变更时间");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(20, 17, 313, 51));
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel.add(getCcbBegin(), null);
			jPanel.add(jLabel, null);
			jPanel.add(getCcbEnd(), null);
			jPanel.add(jLabel1, null);
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
			btnOk.setBounds(new Rectangle(101, 117, 58, 22));
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
			jButton1.setBounds(new Rectangle(167, 117, 58, 22));
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
	 * 获得开始时间
	 * @return
	 */
	public Date getBeginDate(){
		if(ccbBegin.getDate()!=null){
			return ccbBegin.getDate() ;
		}
		return null;
	}

	
	/**
	 * 获得结束时间
	 * @return
	 */
	public Date getEndDate(){
		if(ccbEnd.getDate()!=null){
			return ccbEnd.getDate();
		}
		return null;
	}

	public boolean isDeclared(){
		return this.jRadioButton.isSelected();
	}	
	
	
	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * This method initializes jCustomFormattedTextField	
	 * 	
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField	
	 */
	private JCalendarComboBox getCcbEnd() {
		if (ccbEnd == null) {
			ccbEnd = new JCalendarComboBox();
			ccbEnd.setBounds(new Rectangle(196, 16, 113, 21));
		}
		return ccbEnd;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(20, 73, 313, 38));
			jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel1.add(getJRadioButton(), null);
			jPanel1.add(getJRadioButton1(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new Rectangle(13, 11, 128, 16));
			jRadioButton.setSelected(true);
			jRadioButton.setText("已备案");
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setBounds(new Rectangle(164, 10, 122, 19));
			jRadioButton1.setText("正在申请备案");
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes buttonGroup	
	 * 	
	 * @return javax.swing.ButtonGroup	
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getJRadioButton());
			buttonGroup.add(this.getJRadioButton1());
		}
		return buttonGroup;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10" 

