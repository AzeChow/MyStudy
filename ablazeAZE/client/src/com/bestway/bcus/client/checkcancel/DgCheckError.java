package com.bestway.bcus.client.checkcancel;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;

import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextPane;

public class DgCheckError extends JDialogBase {

	private JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JPanel jPanel2 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JTextPane jTextPane = null;
	private JTableListModel tableModel = null;
	private List list = null;
	private boolean ok = true;
	private String errorStr = "";
	private String errorType = "0";
	/**
	 * This is the default constructor
	 */
	public DgCheckError() {
		super();
		initialize();
		
	}

	
	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("料号", "ptNo", 200));
						list.add(addColumn("版本号", "version", 100));
						list.add(addColumn("库存数量", "materStockNum", 100));
						return list;
					}
				});
	}
	
	
	private void initTableImg(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("料号", "ptNo", 100));	
						list.add(addColumn("原料库存数量", "materStock", 100));
						list.add(addColumn("原料在途数量", "materByWay", 100));
						list.add(addColumn("成品折料数量", "passExgUse", 100));
						list.add(addColumn("在线数量", "onlines", 100));
						list.add(addColumn("半成品折料数量", "halfExgConvert", 100));
						list.add(addColumn("废料数量", "depose", 100));
						list.add(addColumn("转进未报数量", "turnInNoReport", 100));
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
		this.setSize(709, 458);
		this.setContentPane(getJContentPane());
		this.setTitle("数据导入错误提示");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				jTextPane.setText(errorStr);
				if (errorType.equals("0")){
			    	initTable(list);
				} else if (errorType.equals("1")){
					initTableImg(list);
				}
			}
		});
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
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setDividerLocation(300);
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
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jPanel1.add(getJPanel2(), java.awt.BorderLayout.SOUTH);
			jPanel1.add(getJTextPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
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
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("保存");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

                      DgCheckError.this.setOk(true);
                      DgCheckError.this.dispose();
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
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCheckError.this.setOk(false);
                    DgCheckError.this.dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			jTextPane.setEditable(false);
			jTextPane.setForeground(java.awt.Color.red);
		}
		return jTextPane;
	}


	public List getList() {
		return list;
	}


	public void setList(List list) {
		this.list = list;
	}


	public boolean isOk() {
		return ok;
	}


	public void setOk(boolean ok) {
		this.ok = ok;
	}


	public String getErrorStr() {
		return errorStr;
	}


	public void setErrorStr(String errorStr) {
		this.errorStr = errorStr;
	}


	public String getErrorType() {
		return errorType;
	}


	public void setErrorType(String type) {
		this.errorType = type;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
