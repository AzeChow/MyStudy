/*
 * Created on 2004-7-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common;

import java.awt.Frame;

import javax.swing.JFrame;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.util.JConsole;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JScrollPane;
/**
 * @author wp
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FmJavaCommand extends JFrame {

	private javax.swing.JPanel jContentPane = null;

	private JConsole jConsole = null;
	private JToolBar jToolBar = null;
	private JButton jButton = null;
	private JScrollPane jScrollPane = null;
	private Interpreter interpreter = null;
	/**
	 * This method initializes jConsole	
	 * 	
	 * @return bsh.util.JConsole	
	 */    
	private JConsole getJConsole() {
		if (jConsole == null) {
			jConsole = new JConsole();
			interpreter = new Interpreter( jConsole );
			new Thread( interpreter ).start(); 			
		}
		return jConsole;
	}
	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */    
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
		}
		return jToolBar;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("上下文");
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						
						interpreter.println("org.springframework.context.ApplicationContext context=com.bestway.common.CommonUtils.getContext();");
						interpreter.eval("org.springframework.context.ApplicationContext context=com.bestway.common.CommonUtils.getContext();");
						jConsole.requestFocus();
					} catch (EvalError e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return jButton;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJConsole());
		}
		return jScrollPane;
	}
    	public static void main(String[] args) {
	}
	/**
	 * This is the default constructor
	 */
	public FmJavaCommand() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300,200);
		this.setContentPane(getJContentPane());
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
}
