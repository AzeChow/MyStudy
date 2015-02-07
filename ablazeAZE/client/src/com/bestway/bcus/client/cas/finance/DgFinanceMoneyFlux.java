/*
 * Created on 2004-10-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.finance;


import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.MoneyMaster;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgFinanceMoneyFlux extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private CommonBaseCodeAction commonBaseCodeAction = null;
	private CasAction casAction = null;	
	private JTableListModel tableModel = null;  
	private List list = null;
	private JTextField jTextField = null;
	/**
	 * This is the default constructor
	 */
	public DgFinanceMoneyFlux() {
		super();
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
	       .getApplicationContext().getBean("commonBaseCodeAction");
        casAction = (CasAction) CommonVars.getApplicationContext()
	       .getBean( "casAction");
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(733, 541);
		this.setTitle("现金流量表");
		this.setContentPane(getJContentPane());
		
	}
    
    public void setVisible(boolean isFlag){
        if(isFlag){
            initUI();
               list = casAction.findMoneyMaster(new Request(
                    CommonVars.getCurrUser()));
               if (list!=null && !list.isEmpty())
               {
                 initTable(list);
               } else {
                 initTable(new Vector());
               }
        }
        super.setVisible(isFlag);
    }
    
    
	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("流水号", "num", 120));
						list.add(addColumn("报表时间", "reportDate", 150));
						list.add(addColumn("备注", "note", 200));
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
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(2);
			jSplitPane.setDividerLocation(50);
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
			javax.swing.JLabel jLabel = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, null));
			jPanel.setBackground(java.awt.Color.white);
			jLabel.setBounds(347, 16, 26, 19);
			jLabel.setText("备注");
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJButton3(), null);
			jPanel.add(getJTextField(), null);
			jPanel.add(jLabel, null);
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
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
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
			jScrollPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		}
		return jScrollPane;
	}

	/**

	 * This method initializes jButton	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(11, 13, 89, 22);
			jButton.setText("增加报表");
			jButton.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
                    MoneyMaster moneyMaster = new MoneyMaster();
                    moneyMaster.setNum(Integer.valueOf(casAction.getNum(new Request(
								CommonVars.getCurrUser()),"MoneyMaster")));
                    Date date = new Date();
                    moneyMaster.setReportDate(CommonVars.dateToStandDate(date));
                    moneyMaster.setIsDelete(new Boolean(false));
                    moneyMaster.setNote(DgFinanceMoneyFlux.this.jTextField.getText());
                    moneyMaster.setCompany(CommonVars.getCurrUser().getCompany());
                    moneyMaster = casAction.saveMoneyMaster(new Request(
								CommonVars.getCurrUser()),moneyMaster);
                    tableModel.addRow(moneyMaster);
                    casAction.moneyDetailadd(new Request(CommonVars.getCurrUser()),moneyMaster);
                    edit();

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
			jButton1.setBounds(104, 13, 75, 21);
			jButton1.setText("修改");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgFinanceMoneyFlux.this, "请选择你将要修改的记录",
								"提示！", 0);
						return;
					}
					edit();
				}
			});

		}
		return jButton1;
	}
    private void edit(){
    	DgFinanceMoneyFluxEdit dg = new DgFinanceMoneyFluxEdit();
		dg.setTableModelMaster(tableModel);
		dg.setVisible(true);
    }
	/**

	 * This method initializes jButton2	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(184, 14, 75, 21);
			jButton2.setText("作废");
			jButton2.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgFinanceMoneyFlux.this, "请选择你将要修改的记录",
								"提示！", 2);
						return;
					}
					MoneyMaster moneyMaster = (MoneyMaster) tableModel.getCurrentRow();
					moneyMaster.setIsDelete(new Boolean(true));
					moneyMaster = casAction.saveMoneyMaster(new Request(CommonVars.getCurrUser()),moneyMaster);
					tableModel.deleteRow(moneyMaster);
					
				}
			});

		}
		
		return jButton2;
	}
    private void initUI(){
    	
    }
	/**

	 * This method initializes jButton3	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(263, 14, 75, 21);
			jButton3.setText("关闭");
			jButton3.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					DgFinanceMoneyFlux.this.dispose();

				}
			});

		}
		return jButton3;
	}

	/**

	 * This method initializes jTextField	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(378, 14, 158, 22);
		}
		return jTextField;
	}

        }  //  @jve:decl-index=0:visual-constraint="10,10"
