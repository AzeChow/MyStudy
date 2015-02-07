/*
 * Created on 2004-11-12
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;



import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DBDataExecuSql;
import com.bestway.bcus.dataimport.entity.DBDataRoot;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import com.bestway.ui.editor.SQLTextPane;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgDataExecuSqlEdit extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private DataImportAction dataImportAction = null;
	private boolean isAdd = true;
	private DBDataExecuSql dbView = null; //视图参数设置
	private JTableListModel tableModel = null; 
	private JToolBar jToolBar = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton3 = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JButton jButton4 = null;
	private JScrollPane jScrollPane = null;
	private JTextArea jTextArea1 = null;

	private SQLTextPane SQLTextPane = null;

	private JScrollPane jScrollPane1 = null;
	private DBDataRoot dbRoot  = null;

	private JLabel jLabel1 = null;
	/**
	 * This is the default constructor
	 */
	public DgDataExecuSqlEdit() {
		super();
		dataImportAction = (DataImportAction) CommonVars
               .getApplicationContext().getBean("dataImportAction");
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("新增视图参数设置");
		this.setSize(750, 473);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter(){
			public void windowOpened(java.awt.event.WindowEvent e){

				if (isAdd == false) //编辑
				{
					dbView = (DBDataExecuSql) tableModel.getCurrentRow();
					fillWindow();
				} else {
					jLabel1.setText("数据源："+(dbRoot==null?"本地":dbRoot.getName()));
				}
			}	
		});
		
	}
	private void fillWindow(){
		this.jTextField.setText(dbView.getName());
		this.SQLTextPane.setText(dbView.getSqlStr());	
		dbRoot = dbView.getDataRoot();
		jLabel1.setText("数据源："+(dbView.getDataRoot() == null ? "本地":dbView.getDataRoot().getName()));
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	private void fillData(DBDataExecuSql db){      
        db.setName(this.jTextField.getText());
		db.setSqlStr(this.SQLTextPane.getText());
		db.setDataRoot(dbRoot);
	}
    private boolean checkisNull(){
    	if (this.jTextField.getText().equals("")){
    		JOptionPane.showMessageDialog(DgDataExecuSqlEdit.this, "名称不可为空！", "提示！", 2);
			return false;	
    	}
    	return true;
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
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */    
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getJPanel());
		}
		return jToolBar;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(437,2,237,25));
			jLabel1.setForeground(java.awt.Color.red);
			jLabel1.setText("");
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setText("事件名称");
			jLabel.setBounds(11, 6, 48, 18);
			jPanel.add(jLabel, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton3(), null);
			jPanel.add(getJButton4(), null);
			jPanel.add(jLabel1, null);
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
			jTextField.setBounds(64, 4, 159, 22);
		}
		return jTextField;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("最大化");
		}
		return jButton;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("保存");
			jButton1.setBounds(231, 2, 58, 25);
			jButton1.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					   if (!checkisNull()){
	                   	  return; 
	                   }
					   saveData();
	                   dispose();
	                   
					}
				});

			}
		return jButton1;
	}
	private void saveData(){
		if (DgDataExecuSqlEdit.this.isAdd){
      	    DBDataExecuSql db = new DBDataExecuSql();
            fillData(db);
            db = dataImportAction.saveDBDataExecuSql(new Request(CommonVars.getCurrUser()),db);
            dbView = db;
            tableModel.addRow(db);
      } else {
          	fillData(dbView);
          	dbView = dataImportAction.saveDBDataExecuSql(new Request(CommonVars.getCurrUser()),dbView);
          	tableModel.updateRow(dbView);
      }
	}
	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("取消");
			jButton3.setBounds(376, 2, 58, 25);
			jButton3.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					dispose();
				}
			});
		}
		return jButton3;
	}
	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(300);
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setTopComponent(getJPanel1());
			jSplitPane.setBottomComponent(getJPanel2());
		}
		return jSplitPane;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}
	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(291, 2, 84, 25);
			jButton4.setText("执行");
			jButton4.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {  
					 if (!checkisNull()){
	                   	 return; 
	                 }
					 saveData();
					 new execusql().start();
				}
			});
		}
		return jButton4;
	}
	class execusql extends Thread{
		public void run(){
			 try {
		           CommonProgress.showProgressDialog();
		           CommonProgress.setMessage("系统正在执行数据，请稍后...");
		           String message = dataImportAction.execuSql(new Request(CommonVars.getCurrUser()),dbView);
		           CommonProgress.closeProgressDialog();
		           JOptionPane.showMessageDialog(DgDataExecuSqlEdit.this,
		                message, "提示", 2);  
			 } catch (Exception e) {
		        CommonProgress.closeProgressDialog();
		        JOptionPane.showMessageDialog(DgDataExecuSqlEdit.this,
		                "执行失败:\n" + e.getMessage(), "提示", 2);    
			 }
	}
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getSQLTextPane());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jTextArea1	
	 * 	
	 * @return javax.swing.JTextArea	
	 */    
	private JTextArea getJTextArea1() {
		if (jTextArea1 == null) {
			jTextArea1 = new JTextArea();
		}
		return jTextArea1;
	}
	/**
	 * This method initializes SQLTextPane	
	 * 	
	 * @return com.bestway.ui.editor.SQLTextPane	
	 */
	private SQLTextPane getSQLTextPane() {
		if (SQLTextPane == null) {
			SQLTextPane = new SQLTextPane();
		}
		return SQLTextPane;
	}
	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTextArea1());
		}
		return jScrollPane1;
	}
	public DBDataRoot getDbRoot() {
		return dbRoot;
	}
	public void setDbRoot(DBDataRoot dbRoot) {
		this.dbRoot = dbRoot;
	}
           }  //  @jve:decl-index=0:visual-constraint="10,10"
