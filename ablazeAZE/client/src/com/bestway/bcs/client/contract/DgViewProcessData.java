package com.bestway.bcs.client.contract;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.bestway.ui.winuicontrol.JDialogBase;

public class DgViewProcessData extends JDialogBase {

	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTextArea taData = null;
	private List dataSource=new ArrayList();
	
	public List getDataSource() {
		return dataSource;
	}

	public void setDataSource(List dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * This method initializes 
	 * 
	 */
	public DgViewProcessData() {
		super();
		initialize();
	}
	
	public void setVisible(boolean b){
		if(b){
			this.showData();
		}
		super.setVisible(b);
	}
	
	private void showData(){
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<dataSource.size();i++){
			String data=dataSource.get(i).toString();
			sb.append(data+"\n");
		}
		taData.setText(sb.toString());
	}
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new java.awt.Dimension(795,431));
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
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jScrollPane.setViewportView(getTaData());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getTaData() {
		if (taData == null) {
			taData = new JTextArea();
		}
		return taData;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
