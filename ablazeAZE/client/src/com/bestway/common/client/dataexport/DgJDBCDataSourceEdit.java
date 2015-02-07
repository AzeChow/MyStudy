/*
 * Created on 2004-11-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataexport;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DriverList;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.dataexport.action.DataExportAction;
import com.bestway.common.dataexport.entity.JDBCDataSource;
import com.bestway.ui.winuicontrol.JDialogBase;


/**
 * @author luosheng 2006/9/1
 * 
 */
public class DgJDBCDataSourceEdit extends JDialogBase {

	private javax.swing.JPanel	jContentPane		= null;

	private JPanel				jPanel				= null;
	private JTextField			tfName				= null;
	private JButton				jButton				= null;
	private JButton				jButton1			= null;
	private DataExportAction	dataExportAction	= null;
	private DataImportAction	dataImportAction	= null;
	private boolean				isAdd				= true;
	private JDBCDataSource		jdbcDataSource		= null;
	private JTableListModel		tableModel			= null;
	private JPanel				jPanel1				= null;
	private JTextField			tfDriverClassName	= null;
	private JTextField			tfUrl				= null;
	private JPasswordField		tfPassword			= null;
	private JTextField			tfUserName			= null;
	private JLabel				jLabel5				= null;
	private JComboBox			cbbDriverList		= null;

	/**
	 * This is the default constructor
	 */
	public DgJDBCDataSourceEdit() {
		super();
		dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");
		dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		initialize();
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("修改数据源");
		this.setSize(479, 351);
		this.setContentPane(getJContentPane());	
	}

	
	@Override
	public void setVisible(boolean b){
		if(b){
			if (isAdd == false) // 编辑
			{
				cbbDriverList.setEnabled(false);
				cbbDriverList.setSelectedItem(null);
				jdbcDataSource = (JDBCDataSource) tableModel
						.getCurrentRow();
				fillWindow();
			}else{
				cbbDriverList.setEnabled(true);
				cbbDriverList.setSelectedItem(null);
			}	
		}
		super.setVisible(b);
	}
	
	
	
	private void initUIComponents() {
		List dataSource = this.dataImportAction.findDriverList(new Request(
				CommonVars.getCurrUser()));
		dataSource.add(null);
		this.cbbDriverList.setModel(new DefaultComboBoxModel(dataSource
				.toArray()));
		this.cbbDriverList.setRenderer(new DefaultListCellRenderer(){
			@Override
			public Component getListCellRendererComponent(JList list, Object value,
					int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected,
						cellHasFocus);
				if(value != null && value instanceof DriverList){
					DriverList d = (DriverList)value;
					this.setText(d.getName());
				}				
				return this;
			}
		});

	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(18, 21, 70, 22));
			jLabel5.setText("驱动列表");
			javax.swing.JLabel jLabel = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(18, 50, 68, 22);
			jLabel.setText("数据源名称");
			jPanel.add(jLabel, null);
			jPanel.add(getTfName(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJPanel1(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getCbbDriverList(), null);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(91, 50, 243, 22);
		}
		return tfName;
	}

	private boolean checkNull() {
		if (this.tfName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "数据源名称不能为空，请输入!", "提示", 2);
			return true;
		}
		if (this.tfDriverClassName.getText().trim().equals("")
				|| this.tfUrl.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "连接字符串不能为空，请输入!", "提示", 2);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(165, 263, 65, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (checkNull()) {
						return;
					}
					if (isAdd()) {
						addData();
					} else {
						modifyData();
					}

					DgJDBCDataSourceEdit.this.dispose();
				}
			});

		}
		return jButton;
	}

	private void addData() {
		JDBCDataSource db = new JDBCDataSource();
		fillData(db);
		db = dataExportAction.saveJDBCDataSource(new Request(CommonVars
				.getCurrUser()), db);
		tableModel.addRow(db);
	}

	private void modifyData() {
		fillData(jdbcDataSource);
		jdbcDataSource = dataExportAction.saveJDBCDataSource(new Request(
				CommonVars.getCurrUser()), jdbcDataSource);
		tableModel.updateRow(jdbcDataSource);
	}

	private void fillData(JDBCDataSource jdbcDataSource) {
		jdbcDataSource.setCompany(CommonVars.getCurrUser().getCompany());
		jdbcDataSource.setName(this.tfName.getText());
		jdbcDataSource.setDriverClassName(this.tfDriverClassName.getText());
		jdbcDataSource.setUrl(this.tfUrl.getText());
		jdbcDataSource.setUserName(this.tfUserName.getText());
		jdbcDataSource.setPassword(String
				.valueOf(this.tfPassword.getPassword()));
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(271, 264, 68, 25);
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgJDBCDataSourceEdit.this.dispose();

				}
			});

		}
		return jButton1;
	}

	private void fillWindow() {
		this.tfName.setText(jdbcDataSource.getName());
		this.tfDriverClassName.setText(jdbcDataSource.getDriverClassName());
		this.tfUrl.setText(jdbcDataSource.getUrl());
		this.tfUserName.setText(jdbcDataSource.getUserName());
		this.tfPassword.setText(jdbcDataSource.getPassword());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
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
	 * @param isAdd
	 *            The isAdd to set.
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
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			jPanel1.setLayout(null);
			jPanel1.setBounds(16, 87, 443, 156);
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "连接字符串",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel1.setBounds(9, 23, 103, 22);
			jLabel1.setText("driverClassName");
			jLabel2.setBounds(10, 51, 33, 21);
			jLabel2.setText("url");
			jLabel3.setBounds(10, 77, 71, 23);
			jLabel3.setText("userName");
			jLabel4.setBounds(11, 107, 72, 20);
			jLabel4.setText("password");
			jPanel1.add(jLabel1, null);
			jPanel1.add(getTfDriverClassName(), null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(getTfUrl(), null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(getTfPassword(), null);
			jPanel1.add(getTfUserName(), null);
			jPanel1.add(jLabel4, null);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jTextField1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfDriverClassName() {
		if (tfDriverClassName == null) {
			tfDriverClassName = new JTextField();
			tfDriverClassName.setBounds(110, 23, 322, 22);
		}
		return tfDriverClassName;
	}

	/**
	 * 
	 * This method initializes jTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfUrl() {
		if (tfUrl == null) {
			tfUrl = new JTextField();
			tfUrl.setBounds(43, 50, 389, 22);
		}
		return tfUrl;
	}

	/**
	 * 
	 * This method initializes jPasswordField
	 * 
	 * 
	 * 
	 * @return javax.swing.JPasswordField
	 * 
	 */
	private JPasswordField getTfPassword() {
		if (tfPassword == null) {
			tfPassword = new JPasswordField();
			tfPassword.setBounds(111, 105, 321, 22);
		}
		return tfPassword;
	}

	/**
	 * 
	 * This method initializes jTextField3
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfUserName() {
		if (tfUserName == null) {
			tfUserName = new JTextField();
			tfUserName.setBounds(110, 77, 322, 22);
		}
		return tfUserName;
	}

	/**
	 * This method initializes cbbDriverList
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDriverList() {
		if (cbbDriverList == null) {
			cbbDriverList = new JComboBox();
			cbbDriverList.setBounds(new Rectangle(91, 21, 243, 22));
			cbbDriverList.addItemListener(new ItemListener(){

				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.SELECTED){
						DriverList d = (DriverList)cbbDriverList.getSelectedItem();
						if(d == null){
							tfDriverClassName.setText("");
							tfUrl.setText("");														
						}else{
							tfDriverClassName.setText(d.getDriverClassName());
							tfUrl.setText(d.getUrl());
							tfName.setText(d.getName());
						}
					}
					
				}
				
			});
		}
		return cbbDriverList;
	}

}
