/*
 * Created on 2004-7-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.ui.winuicontrol.calendar;

import javax.swing.JFrame;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import com.sun.java.swing.plaf.windows.WindowsSpinnerUI;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.DateFormatter;
/**
 * @author wp
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Test extends JFrame {

	private javax.swing.JPanel jContentPane = null;

	private JCalendarPanel jCalendarPanel = null;
	private JMonthPanel jMonthPanel = null;
	private JCalendarComboBox jCalendarComboBox = null;
	private SpinnerDateModel spinnerDateModel = null;   //  @jve:decl-index=0:parse
	private WindowsSpinnerUI windowsSpinnerUI = null;   //  @jve:decl-index=0:parse
	private JSpinner jSpinner = null;
	private JFormattedTextField jFormattedTextField = null;
	private DefaultFormatterFactory defaultFormatterFactory = null;   //  @jve:decl-index=0:parse
	private DateFormatter dateFormatter = null;   //  @jve:decl-index=0:parse
	/**
	 * This method initializes jCalendarPanel	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarPanel	
	 */    
	private JCalendarPanel getJCalendarPanel() {
		if (jCalendarPanel == null) {
			jCalendarPanel = new JCalendarPanel();
			jCalendarPanel.setBounds(14, 40, 225, 204);
		}
		return jCalendarPanel;
	}
	/**
	 * This method initializes jMonthPanel	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JMonthPanel	
	 */    
	private JMonthPanel getJMonthPanel() {
		if (jMonthPanel == null) {
			jMonthPanel = new JMonthPanel();
			jMonthPanel.setBounds(297, 64, 192, 155);
		}
		return jMonthPanel;
	}
	/**
	 * This method initializes jCalendarComboBox	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */    
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(246, 11, 159, 29);
		}
		return jCalendarComboBox;
	}
	/**
	 * This method initializes spinnerDateModel	
	 * 	
	 * @return javax.swing.SpinnerDateModel	
	 */    
	private SpinnerDateModel getSpinnerDateModel() {
		if (spinnerDateModel == null) {
			spinnerDateModel = new SpinnerDateModel();
			spinnerDateModel.setCalendarField(5);
		}
		return spinnerDateModel;
	}
	/**
	 * This method initializes windowsSpinnerUI	
	 * 	
	 * @return com.sun.java.swing.plaf.windows.WindowsSpinnerUI	
	 */    
	private WindowsSpinnerUI getWindowsSpinnerUI() {
		if (windowsSpinnerUI == null) {
			windowsSpinnerUI = new WindowsSpinnerUI();
		}
		return windowsSpinnerUI;
	}
	/**
	 * This method initializes jSpinner	
	 * 	
	 * @return javax.swing.JSpinner	
	 */    
	private JSpinner getJSpinner() {
		if (jSpinner == null) {
			jSpinner = new JSpinner();
			jSpinner.setBounds(47, 258, 116, 22);
			jSpinner.setModel(getSpinnerDateModel());
		}
		return jSpinner;
	}
	/**
	 * This method initializes jFormattedTextField	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */    
	private JFormattedTextField getJFormattedTextField() {
		if (jFormattedTextField == null) {
			jFormattedTextField = new JFormattedTextField();
			jFormattedTextField.setBounds(180, 258, 133, 22);
			jFormattedTextField.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField;
	}
	/**
	 * This method initializes defaultFormatterFactory	
	 * 	
	 * @return javax.swing.text.DefaultFormatterFactory	
	 */    
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getDateFormatter());
		}
		return defaultFormatterFactory;
	}
	/**
	 * This method initializes dateFormatter	
	 * 	
	 * @return javax.swing.text.DateFormatter	
	 */    
	private DateFormatter getDateFormatter() {
		if (dateFormatter == null) {
			dateFormatter = new DateFormatter();
		}
		return dateFormatter;
	}
           	public static void main(String[] args) {
 		Test test=new Test();
 		test.setVisible(true);
	}
	/**
	 * This is the default constructor
	 */
	public Test() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(576, 444);
		this.setContentPane(getJContentPane());
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJCalendarPanel(), null);
			jContentPane.add(getJMonthPanel(), null);
			jContentPane.add(getJCalendarComboBox(), null);
			jContentPane.add(getJSpinner(), null);
			jContentPane.add(getJFormattedTextField(), null);
		}
		return jContentPane;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
