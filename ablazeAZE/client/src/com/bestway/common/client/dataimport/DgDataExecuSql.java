/*
 * Created on 2004-11-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DBDataExecuSql;
import com.bestway.bcus.dataimport.entity.DBDataRoot;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.JLabel;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgDataExecuSql extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JTableListModel tableModel = null;
	private DataImportAction dataImportAction = null;
	private JButton jButton4 = null;
	private DBDataExecuSql root = null;
	private File						txtFile						= null;
	private JButton jButton5 = null;

	private JLabel jLabel = null;
	/**

	 * This method initializes jToolBar	

	 * 	

	 * @return javax.swing.JToolBar	

	 */    
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jLabel = new JLabel();
			jLabel.setText("1.执行与执行文件单据不需要增加年份 2.以'--end'分割语句");
			jLabel.setForeground(java.awt.Color.red);
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton4());
			jToolBar.add(getJButton5());			
			jToolBar.add(getJButton3());
			jToolBar.add(jLabel);
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
			jButton.setText("    增加    ");
			jButton.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					DgSelectFromDb dg = new DgSelectFromDb();
					dg.setVisible(true);
                    if (dg.isOk()){
                    	DBDataRoot dbRoot = dg.getDbRoot();                	
                    	
                    	DgDataExecuSqlEdit root = new DgDataExecuSqlEdit();
    					root.setAdd(true);
    					root.setDbRoot(dbRoot);
    					root.setTableModel(tableModel);
    					root.setVisible(true);
                    }					
					
				}
			});

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
			jButton1.setText("    修改    ");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDataExecuSql.this,
								"请选择你要修改的资料", "确认", 2);
						return;
					}
					DgDataExecuSqlEdit root = new DgDataExecuSqlEdit();
					root.setAdd(false);
					root.setTableModel(tableModel);
					root.setVisible(true);
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
			jButton2.setText("    删除    ");
			jButton2.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (JOptionPane.showConfirmDialog(DgDataExecuSql.this,
							"确定删除吗?", "提示", 0)== 0) {
						if (tableModel.getCurrentRow()==null){
							JOptionPane.showMessageDialog(DgDataExecuSql.this,"请选中要删除的资料！","提示",2);
							return;
						}
						DBDataExecuSql obj  = (DBDataExecuSql) tableModel.getCurrentRow();
						try{
							dataImportAction.deleteDBDataExecuSql(new Request(CommonVars.getCurrUser()),obj);
							tableModel.deleteRow(obj);
						} catch (Exception e1){
							JOptionPane.showMessageDialog(DgDataExecuSql.this,"已被引用，不能删除！","提示",2);
						}
					}
				}
			});

		}
		return jButton2;
	}

	/**

	 * This method initializes jButton3	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("    关闭    ");
			jButton3.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					DgDataExecuSql.this.dispose();
				}
			});

		}
		return jButton3;
	}

	/**

	 * This method initializes jTable	

	 * 	

	 * @return javax.swing.JTable	

	 */    
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**

	 * This method initializes jScrollPane	

	 * 	

	 * @return javax.swing.JScrollPane	

	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}
	/**
	 * This is the default constructor
	 */
	public DgDataExecuSql() {
		super();
		dataImportAction = (DataImportAction) CommonVars
	             .getApplicationContext().getBean("dataImportAction");
		initialize();
	}
	private void initTable(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();		
						list.add(addColumn("数据源", "dataRoot.name", 200));
						list.add(addColumn("事件名称", "name", 250));
						list.add(addColumn("执行语句", "sqlStr", 380));
						return list;
					}
				});
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("事件设置");
		this.setSize(708, 411);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				initData();
			}
		});
		
	}
	private void initData(){
		List list = dataImportAction.findDbDataExecuSql(new Request(CommonVars.getCurrUser()));
		if (list!=null && !list.isEmpty()){
			initTable(list);
		} else {
			initTable(new Vector());
		}
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
	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("    执行    ");
			jButton4.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDataExecuSql.this,
								"请选择你要修改的资料", "确认", 2);
						return;
					}
					root = (DBDataExecuSql) tableModel.getCurrentRow();
				    new execusql().start();
				}
			});
		}
		return jButton4;
	}
	
	
	
	
	class execusql extends Thread{
		public void run(){
			 String message = "";
			 try {
		           CommonProgress.showProgressDialog();
		           CommonProgress.setMessage("系统正在执行数据，请稍后...");
		           message =  dataImportAction.execuSql(new Request(CommonVars.getCurrUser()),root);
		           CommonProgress.closeProgressDialog();
		           JOptionPane.showMessageDialog(DgDataExecuSql.this,
		        		   message, "提示", 2);  
			 } catch (Exception e) {
		        CommonProgress.closeProgressDialog();
		        JOptionPane.showMessageDialog(DgDataExecuSql.this,
		                "执行失败:\n" + e.getMessage(), "提示", 2);    
			 }
	}
	}
	/**
	 * This method initializes jButton5	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("执行文件");
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					txtFile = getHsqlFile();
					if (txtFile == null) {
						return;
					}
					List list = parseTxtFile();
					if (list == null || list.size() < 1){
						JOptionPane.showMessageDialog(DgDataExecuSql.this,
				                "对不起！打开文件为空，请查看文件!", "提示", 2); 
						return;
					}
					new executeHsql(list).start();
				}
			});
		}
		return jButton5;
	}
	
    //调出文件选择框
	private File getHsqlFile() {
		File txtFile = null;
		JFileChooser fileChooser = new JFileChooser("./");
		fileChooser.addChoosableFileFilter(new ExampleFileFilter("txt"));
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		int state = fileChooser.showDialog(DgDataExecuSql.this, "选择SQL文件");
		if (state == JFileChooser.APPROVE_OPTION) {
			txtFile = fileChooser.getSelectedFile();
		}
		return txtFile;
	}
	
	
	
	class ExampleFileFilter extends FileFilter {
		String	suffix	= "";

		ExampleFileFilter(String suffix) {
			this.suffix = suffix;
		}

		public boolean accept(File f) {
			String suffix = getSuffix(f);
			if (f.isDirectory() == true) {
				return true;
			}
			if (suffix != null) {
				if (suffix.toLowerCase().equals(this.suffix)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		public String getDescription() {
			return "*." + this.suffix;
		}

		private String getSuffix(File f) {
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1).toLowerCase();
			return suffix;
		}
	}
	class executeHsql extends Thread {
		List list;
		public executeHsql(List list){
			this.list = list;
		}
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在执行文件资料，请稍后...");
				
				String sqlstr = "";
				int x = 0;
				long beginTime = System.currentTimeMillis();
				for (int i = 0; i < list.size(); i++) {
					String hsql = (String) list.get(i);	
					sqlstr = sqlstr + hsql +"\n";
					try {						
						dataImportAction.execuFileSql(new Request(CommonVars.getCurrUser()),hsql);	
						CommonProgress.closeProgressDialog();
					} catch (Exception ex) {
						CommonProgress.closeProgressDialog();
						JOptionPane.showMessageDialog(DgDataExecuSql.this,
								"执行有错误：\n"+ex+
								"\n"+"第 "+String.valueOf(i+1)+" 条语句"+
								"\n"+
								"请将执行文件E-Mail至负责您公司的百思维顾问邮件中,谢谢!", "提示", 2); 
						x = 1;
						break;
					}
				}
				long endTime = System.currentTimeMillis();					
				if (x == 0){
					JOptionPane.showMessageDialog(DgDataExecuSql.this,
			                "执行成功:\n"+
			                "执行语句：\n"+
			                sqlstr+"共消耗时间: " + (endTime - beginTime)
							+ " 毫秒 ", "提示", 2);  
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private List parseTxtFile() {
		BufferedReader in;
		ArrayList<String> list = new ArrayList<String>();
		try {
			in = new BufferedReader(new FileReader(txtFile));		
			String s = new String();
			String x = "";
			try {
				while ((s = in.readLine()) != null) {
					if (s.trim().equals("")) {
						continue;
					}
					if (!s.trim().equals("--end")) {
						x = x + s;
					}
					if (s.trim().equals("--end")) {
						list.add(x);
						x = "";
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (!"".equals(x)) {
				list.add(x);
				x = "";
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(DgDataExecuSql.this,"打开文件失败!","提示",2);
			return null;
		}
		return list;
	}
	
 }  //  @jve:decl-index=0:visual-constraint="10,10"
