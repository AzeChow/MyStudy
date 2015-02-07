package com.bestway.bls.client.message;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bls.action.BlsMessageAction;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DgBlsMessageFlatFileContent extends JDialogBase {
	
	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTextArea jTextArea = null;
	private String messageFileName;  //  @jve:decl-index=0:
	private BlsMessageAction blsMessageAction = null;
	
	public String getMessageFileName() {
		return messageFileName;
	}

	public void setMessageFileName(String messageFileName) {
		this.messageFileName = messageFileName;
	}
	/**
	 * This method initializes 
	 * 
	 */
	public DgBlsMessageFlatFileContent() {
		super();
		initialize();
		blsMessageAction = (BlsMessageAction) CommonVars
		.getApplicationContext().getBean("blsMessageAction");
	}
	public void setVisible(boolean b) {
		if (b) {
			showData();
		}
		super.setVisible(b);
	}
	
	private void showData(){
		 String fileContent=blsMessageAction.readBlsMessageFlatFileContent(new Request(
					CommonVars.getCurrUser(), true),
					messageFileName);
		 this.jTextArea.setText(fileContent);
	}
	
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(617, 460));
        this.setTitle("报文文本内容");
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
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
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
			jScrollPane.setViewportView(getJTextArea());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setEditable(false);
		}
		return jTextArea;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
