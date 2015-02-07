/*
 * Created on 2004-11-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.ClassList;
import com.bestway.bcus.dataimport.entity.DBFormat;
import com.bestway.bcus.dataimport.entity.DBView;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgFormatAdd extends JDialogBase {

	private javax.swing.JPanel	jContentPane		= null;

	private JToolBar			jToolBar			= null;
	private JTextField			tfFlag			= null;
	private JTextField			jTextField1			= null;
	private JPanel				jPanel				= null;
	private JButton				jButton				= null;
	private JButton				jButton1			= null;
	private JSplitPane			jSplitPane			= null;
	private JPanel				jPanel1				= null;
	private JPanel				jPanel2				= null;
	private JTable				tbDBView				= null;
	private JScrollPane			jScrollPane			= null;
	private DataImportAction	dataImportAction	= null;
	private JTableListModel		dBViewTableModel			= null;
	private JTableListModel		tableModel1			= null;
	private JTableListModel		tableModel2			= null;
	private JTable				jTable1				= null;
	private JScrollPane			jScrollPane1		= null;
	private DBView				dBView				= null;
	private List				dbViewList			= new ArrayList();

	/**
	 * This is the default constructor
	 */
	public DgFormatAdd() {
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
		this.setTitle("新增域操作");
		this.setSize(777, 473);
		this.setContentPane(getJContentPane());		
	}
	
	public void setVisible(boolean isFlag){
		if(isFlag){
			initData();
		}
		super.setVisible(isFlag);
	}
	
	
	

	private void initData() {
		//
		// init tbDBView
		//
		initTable(dbViewList);
		if (dBView != null ) {
//			this.tfFlag.setText(dBView.getName() + "设置");
			dBViewTableModel.setTableSelectedRow(dBView);
		}		
		//
		// init classList
		//
		List classList = dataImportAction.findClassList(new Request(CommonVars
				.getCurrUser()));		
		initTable1(classList);
	}

	private void initTable(List dataSource) {
		dBViewTableModel = new JTableListModel(tbDBView, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("视图名称", "name", 120));
						return list;
					}
				});

	}

	private void initTable1(List dataSource) {
		tableModel1 = new JTableListModel(jTable1, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("表序号", "sortno", 40));
						list.add(addColumn("表中文名称", "name", 300));
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
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jLabel.setText("      域名称    ");
			jLabel1.setText("       域描述   ");
			jToolBar.add(jLabel);
			jToolBar.add(getJTextField());
			jToolBar.add(jLabel1);
			jToolBar.add(getJTextField1());
		}
		return jToolBar;
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
	private JTextField getJTextField() {
		if (tfFlag == null) {
			tfFlag = new JTextField();
		}
		return tfFlag;
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
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
		}
		return jTextField1;
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
			jPanel = new JPanel();
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
		}
		return jPanel;
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
			jButton.setText("保存");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tfFlag.getText().equals("")) {
						JOptionPane.showMessageDialog(DgFormatAdd.this,
								"请填写域名称！", "确认", 2);
						return;
					}
					if (dBViewTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgFormatAdd.this,
								"请选中视图来源", "确认", 2);
						return;
					}
					if (tableModel1.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgFormatAdd.this,
								"请选中要导入的目标表", "确认", 2);
						return;
					}
					DBFormat db = new DBFormat();
					db.setRegionName(tfFlag.getText());
					db.setClassList((ClassList) tableModel1.getCurrentRow());
					db.setGbflag("0");
					db.setDbView((DBView) dBViewTableModel.getCurrentRow());
					db.setCompany(CommonVars.getCurrUser().getCompany());
					db.setMemo(jTextField1.getText());
					db = dataImportAction.saveDBFormat(new Request(CommonVars
							.getCurrUser()), db);
					tableModel2.addRow(db);
					dispose();

				}
			});

		}
		return jButton;
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
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					dispose();
				}
			});

		}
		return jButton1;
	}

	/**
	 * 
	 * This method initializes jSplitPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(200);
			jSplitPane.setDividerSize(0);
			jSplitPane.setLeftComponent(getJPanel1());
			jSplitPane.setRightComponent(getJPanel2());
		}
		return jSplitPane;
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
			jPanel1.setLayout(new BorderLayout());
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "视图选取",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
					new java.awt.Color(51, 51, 51)));
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "目标表选取",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
					new java.awt.Color(51, 51, 51)));
			jPanel2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jTable
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTable() {
		if (tbDBView == null) {
			tbDBView = new JTable();
			tbDBView.setIntercellSpacing(new java.awt.Dimension(0, 0));
			tbDBView.setShowHorizontalLines(false);
			tbDBView.setShowVerticalLines(false);
			tbDBView.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (dBViewTableModel == null)
								return;
							if (dBViewTableModel.getCurrentRow() == null)
								return;
							DBView obj = (DBView) dBViewTableModel.getCurrentRow();
							DgFormatAdd.this.tfFlag.setText(obj.getName()
									+ "设置");
						}
					});

		}
		return tbDBView;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
			jScrollPane
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return dBViewTableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.dBViewTableModel = tableModel;
	}

	/**
	 * @return Returns the tableModel1.
	 */
	public JTableListModel getTableModel1() {
		return tableModel1;
	}

	/**
	 * @param tableModel1
	 *            The tableModel1 to set.
	 */
	public void setTableModel1(JTableListModel tableModel1) {
		this.tableModel1 = tableModel1;
	}

	/**
	 * @return Returns the tableModel2.
	 */
	public JTableListModel getTableModel2() {
		return tableModel2;
	}

	/**
	 * @param tableModel2
	 *            The tableModel2 to set.
	 */
	public void setTableModel2(JTableListModel tableModel2) {
		this.tableModel2 = tableModel2;
	}

	/**
	 * 
	 * This method initializes jTable1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
	}

	/**
	 * 
	 * This method initializes jScrollPane1
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	public DBView getDBView() {
		return dBView;
	}

	public void setDBView(DBView view) {
		dBView = view;
	}

	public void setDbViewList(List dbViewList) {
		this.dbViewList = dbViewList;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
