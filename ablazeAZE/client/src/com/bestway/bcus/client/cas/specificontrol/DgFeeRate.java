package com.bestway.bcus.client.cas.specificontrol;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import com.bestway.common.client.materialbase.FmCommonBaseCode;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgFeeRate extends JDialogBase{

	private JPanel jContentPane = null;
	private JDesktopPane jDesktopPane = null;

	/**
	 * This method initializes 
	 * 
	 */
	public DgFeeRate() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {        
        this.setSize(755, 539);
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
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJDesktopPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jDesktopPane	
	 * 	
	 * @return javax.swing.JDesktopPane	
	 */
	private JDesktopPane getJDesktopPane() {
		if (jDesktopPane == null) {
			jDesktopPane = new JDesktopPane();
		}
		return jDesktopPane;
	}
	
	
	public void setVisible(boolean isFlag){
		if(isFlag){
			FmCommonBaseCode fm = new FmCommonBaseCode();	
			showForm(fm,this.jDesktopPane);
		//((JInternalFrameBase) fm).setMainFrame(this);
		}
		super.setVisible(isFlag);
	}
	
	
	public static JInternalFrame showForm(FmCommonBaseCode form, JDesktopPane desktop) {		
		form.setClosable(true);		
		form.setIconifiable(true);
		form.setResizable(true);
		try {
			desktop.add(form);
			try {
				form.setMaximum(true);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
			form.setVisibleToCurrRate(true);
			form.moveToFront();
		} catch (RuntimeException e) {
			desktop.remove(form);
			throw (e);
		}
		return form;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
