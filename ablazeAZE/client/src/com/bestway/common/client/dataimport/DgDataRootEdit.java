/*
 * Created on 2004-11-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;


import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DBDataRoot;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgDataRootEdit extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel = null;
	private JTextField jTextField = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private DataImportAction dataImportAction = null;
	private boolean isAdd = true;
	private DBDataRoot dbDataRoot = null;
	private JTableListModel tableModel = null;
	private String driverClassName = null;
	private String url = null;
	private String userName = null;
	private String password = null;
	private String serverName = null;
	private String dataName = null;
	private String flat = null;
	private JPanel jPanel1 = null;
	private JTextField jTextField1 = null;
	private JTextField jTextField2 = null;
	private JPasswordField jPasswordField = null;
	private JTextField jTextField3 = null;
	/**

	 * This method initializes jPanel	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			javax.swing.JLabel jLabel = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(13, 30, 68, 22);
			jLabel.setText("数据源名称");
			jPanel.add(jLabel, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJPanel1(), null);
		}
		return jPanel;
	}

	/**

	 * This method initializes jTextField	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(87, 29, 244, 22);
		}
		return jTextField;
	}
	private  boolean checkNull(){
		if (this.jTextField.getText().trim().equals(""))
		{
		  JOptionPane.showMessageDialog(this,"数据源名称不能为空，请输入!","提示",2);
		  return true;
		}
		if (this.jTextField1.getText().trim().equals("") || this.jTextField2.getText().trim().equals(""))
		{
		  JOptionPane.showMessageDialog(this,"连接字符串不能为空，请输入!","提示",2);
		  return true;
		}
		return false;
	}
	/**

	 * This method initializes jButton	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(91, 207, 65, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					if (checkNull())
					{
						return;	
					}
					if (isAdd()) {
						addData();
					}
					else
					{
						modifyData();
					}
					
					DgDataRootEdit.this.dispose();
				}
			});

		}
		return jButton;
	}
	private void addData()
	{
		DBDataRoot db = new DBDataRoot();
		fillData(db);
		db  = dataImportAction.saveDBDataRoot(new Request(CommonVars
				 .getCurrUser()),db);
		tableModel.addRow(db);		
	}
	private void modifyData()
	{
		fillData(dbDataRoot);
		dbDataRoot  = dataImportAction.saveDBDataRoot(new Request(CommonVars
				 .getCurrUser()),dbDataRoot);
		tableModel.updateRow(dbDataRoot);
	}
	private void fillData(DBDataRoot dbDataRoot){
		dbDataRoot.setCompany(CommonVars.getCurrUser().getCompany());
		dbDataRoot.setName(this.jTextField.getText());
		dbDataRoot.setDriverClassName(this.getDriverClassName());
		dbDataRoot.setUrl(this.getUrl());
		dbDataRoot.setUserName(this.getUserName());
		dbDataRoot.setServerName(this.getServerName());
		dbDataRoot.setDataName(this.getDataName());
		dbDataRoot.setPassword(this.getPassword());
		dbDataRoot.setFlat(this.getFlat());
	}
	/**

	 * This method initializes jButton1	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(207, 207, 68, 25);
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					DgDataRootEdit.this.dispose();

				}
			});

		}
		return jButton1;
	}

	/**

	 * This method initializes jButton2	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(327, 68, 23, 21);
			jButton2.setText("...");
			jButton2.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
                   
					DgConnectStr dg = new DgConnectStr();
					dg.setAdd(DgDataRootEdit.this.isAdd);
					if (!DgDataRootEdit.this.isAdd){
					  dg.setDbDataRoot(dbDataRoot);	
					}
					dg.setVisible(true);
					if (dg.isOk()){
						DgDataRootEdit.this.setDriverClassName(dg.getDriverClassName());
						DgDataRootEdit.this.setUrl(dg.getUrl());
						DgDataRootEdit.this.setUserName(dg.getUserName());
						DgDataRootEdit.this.setPassword(dg.getPassword());
						DgDataRootEdit.this.setFlat(dg.getNum());
						DgDataRootEdit.this.setServerName(dg.getServerName());
						DgDataRootEdit.this.setDataName(dg.getDataName());
						DgDataRootEdit.this.jTextField1.setText(dg.getDriverClassName());
						DgDataRootEdit.this.jTextField2.setText(dg.getUrl());
						DgDataRootEdit.this.jTextField3.setText(dg.getUserName());
						DgDataRootEdit.this.jPasswordField.setText(dg.getPassword());
						if (DgDataRootEdit.this.jTextField.getText().equals("")){
							jTextField.setText(dg.getDataName());
						}
					}
				}
			});

		}
		return jButton2;
	}

	/**
	 * This is the default constructor
	 */
	public DgDataRootEdit() {
		super();
		dataImportAction = (DataImportAction) CommonVars
              .getApplicationContext().getBean("dataImportAction");
		initialize();	
	}
	private void fillWindow(){
		this.jTextField.setText(dbDataRoot.getName());
		this.setFlat(dbDataRoot.getFlat());
		this.setDriverClassName(dbDataRoot.getDriverClassName());
		this.setUrl(dbDataRoot.getUrl());
		this.setUserName(dbDataRoot.getUserName());
		this.setPassword(dbDataRoot.getPassword());
		this.setServerName(dbDataRoot.getServerName());
		this.setDataName(dbDataRoot.getDataName());
		this.jTextField1.setText(dbDataRoot.getDriverClassName());
		this.jTextField2.setText(dbDataRoot.getUrl());
		this.jTextField3.setText(dbDataRoot.getUserName());
		this.jPasswordField.setText(dbDataRoot.getPassword());
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("修改数据源");
		this.setSize(371, 281);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				if (isAdd == false) //编辑
				{
					dbDataRoot = (DBDataRoot) tableModel.getCurrentRow();  	
					fillWindow();
				}	
			}
		});
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
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	/**
	 * @return Returns the isAdd.
	 */
	public boolean isAdd() {
		return isAdd;
	}
	/**
	 * @param isAdd The isAdd to set.
	 */
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}
	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}
	/**
	 * @param tableModel The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * @return Returns the driverClassName.
	 */
	public String getDriverClassName() {
		return driverClassName;
	}
	/**
	 * @param driverClassName The driverClassName to set.
	 */
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return Returns the url.
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return Returns the flat.
	 */
	public String getFlat() {
		return flat;
	}
	/**
	 * @param flat The flat to set.
	 */
	public void setFlat(String flat) {
		this.flat = flat;
	}
	/**
	 * @return Returns the dataName.
	 */
	public String getDataName() {
		return dataName;
	}
	/**
	 * @param dataName The dataName to set.
	 */
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	/**
	 * @return Returns the serverName.
	 */
	public String getServerName() {
		return serverName;
	}
	/**
	 * @param serverName The serverName to set.
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	/**

	 * This method initializes jPanel1	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			jPanel1.setLayout(null);
			jPanel1.setBounds(14, 60, 315, 142);
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "连接字符串", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jLabel1.setBounds(9, 23, 103, 22);
			jLabel1.setText("driverClassName");
			jLabel2.setBounds(10, 51, 33, 21);
			jLabel2.setText("url");
			jLabel3.setBounds(10, 77, 71, 23);
			jLabel3.setText("userName");
			jLabel4.setBounds(11, 107, 72, 20);
			jLabel4.setText("password");
			jPanel1.add(jLabel1, null);
			jPanel1.add(getJTextField1(), null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(getJTextField2(), null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(getJPasswordField(), null);
			jPanel1.add(getJTextField3(), null);
			jPanel1.add(jLabel4, null);
		}
		return jPanel1;
	}

	/**

	 * This method initializes jTextField1	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(110, 23, 197, 22);
		}
		return jTextField1;
	}

	/**

	 * This method initializes jTextField2	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(44, 50, 263, 22);
		}
		return jTextField2;
	}

	/**

	 * This method initializes jPasswordField	

	 * 	

	 * @return javax.swing.JPasswordField	

	 */    
	private JPasswordField getJPasswordField() {
		if (jPasswordField == null) {
			jPasswordField = new JPasswordField();
			jPasswordField.setBounds(111, 105, 197, 22);
		}
		return jPasswordField;
	}

	/**

	 * This method initializes jTextField3	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(110, 77, 197, 22);
		}
		return jTextField3;
	}

     }  //  @jve:decl-index=0:visual-constraint="10,10"
