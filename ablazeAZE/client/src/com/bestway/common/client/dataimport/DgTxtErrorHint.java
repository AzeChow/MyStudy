/*
 * Created on 2004-10-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;


import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JSplitPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTable;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgTxtErrorHint extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JButton jButton1 = null;
	private JPanel jPanel3 = null;
	private List list = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JTableListModel tableModel = null;
	private javax.swing.JLabel jLabel2 = new JLabel();
	private javax.swing.JLabel jLabel1 = new JLabel();
	private javax.swing.JLabel jLabel = new JLabel();
	private String task = "";
	private String mubiao = "";
	private String txtFile = "";
	/**
	 * This is the default constructor
	 */
	public DgTxtErrorHint() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("文本数据浏览保护区");
		this.setSize(457, 337);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter(){
			public void windowOpened(java.awt.event.WindowEvent e){
				DgTxtErrorHint.this.jLabel.setText("任务名称  "+DgTxtErrorHint.this.task);
			    DgTxtErrorHint.this.jLabel1.setText("目标表    "+DgTxtErrorHint.this.mubiao);
			    DgTxtErrorHint.this.jLabel2.setText("文本文件  "+DgTxtErrorHint.this.txtFile);
				initTable(DgTxtErrorHint.this.getList());
			}	
		});
	}
	private void initTable(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();		
						list.add(addColumn("错误信息", "bill1", 400));
						return list;
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	/**

	 * This method initializes jSplitPane	

	 * 	

	 * @return javax.swing.JSplitPane	

	 */    
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(0);
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(100);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
		}
		return jSplitPane;
	}

	/**

	 * This method initializes jPanel	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			javax.swing.JLabel jLabel3 = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBackground(new java.awt.Color(238,238,238));
			jPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel.setText("任务名称");
			jLabel.setBounds(8, 8, 416, 18);
			jLabel.setForeground(java.awt.Color.black);
			jLabel1.setBounds(8, 32, 416, 17);
			jLabel1.setText("目标表");
			jLabel1.setForeground(java.awt.Color.black);
			jLabel2.setBounds(8, 53, 414, 18);
			jLabel2.setText("文本文件");
			jLabel2.setForeground(java.awt.Color.black);
			jLabel3.setBounds(8, 74, 414, 18);
			jLabel3.setText("数据文本有错误，请参照错误提示更改错误重新导入！");
			jLabel3.setForeground(new java.awt.Color(255,51,51));
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
		}
		return jPanel;
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
			jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, null));
			jPanel1.add(getJPanel2(), java.awt.BorderLayout.SOUTH);
			jPanel1.add(getJPanel3(), java.awt.BorderLayout.CENTER);
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
			jPanel2.add(getJButton1(), null);
		}
		return jPanel2;
	}

	/**

	 * This method initializes jButton1	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("退      出");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					DgTxtErrorHint.this.dispose();
				}
			});

		}
		return jButton1;
	}

	/**

	 * This method initializes jPanel3	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "错误信息浏览区", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jPanel3.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * @return Returns the list.
	 */
	public List getList() {
		return list;
	}
	/**
	 * @param list The list to set.
	 */
	public void setList(List list) {
		this.list = list;
	}
	/**

	 * This method initializes jTable	

	 * 	

	 * @return javax.swing.JTable	

	 */    
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setShowHorizontalLines(false);
			jTable.setShowVerticalLines(false);
			jTable.setIntercellSpacing(new java.awt.Dimension(0,0));
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
	 * @return Returns the mubiao.
	 */
	public String getMubiao() {
		return mubiao;
	}
	/**
	 * @param mubiao The mubiao to set.
	 */
	public void setMubiao(String mubiao) {
		this.mubiao = mubiao;
	}
	/**
	 * @return Returns the task.
	 */
	public String getTask() {
		return task;
	}
	/**
	 * @param task The task to set.
	 */
	public void setTask(String task) {
		this.task = task;
	}
	/**
	 * @return Returns the txtFile.
	 */
	public String getTxtFile() {
		return txtFile;
	}
	/**
	 * @param txtFile The txtFile to set.
	 */
	public void setTxtFile(String txtFile) {
		this.txtFile = txtFile;
	}
    }  //  @jve:decl-index=0:visual-constraint="10,10"
