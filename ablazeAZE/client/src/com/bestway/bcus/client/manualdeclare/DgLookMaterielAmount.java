/*
 * Created on 2005-9-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import javax.swing.JFrame;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
/**
 * @author wp
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgLookMaterielAmount extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JButton jButton = null;
	private JPanel jPanel = null;
	private JButton jButton1 = null;
	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JLabel jLabel1 = null;
	private JTextField jTextField1 = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JTableListModel tableModel = null;
	private BillTemp temp = null;
	
	private EncAction encAction = null;
	/**
	 * This is the default constructor
	 */
	public DgLookMaterielAmount() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
              "encAction");
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(512, 323);
		this.setContentPane(getJContentPane());
		this.setTitle("查看物料仓库数量");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
			   if (temp != null){				
		           List list = encAction.findMaterielAmountBySeqNum(new Request(CommonVars.getCurrUser()),Integer.valueOf(temp.getBill1()));
		           jTextField.setText(String.valueOf(temp.getBillSum9()));
		           jTextField1.setText(String.valueOf(temp.getBillSum11()));
		           if (list != null && list.size()>0){
		           	  initTable(list);
		           } else {
		           	  initTable(new Vector());
		           }
			   } else {
			   	  initTable(new Vector());
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
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */    
	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List <JTableListColumn>list = new Vector<JTableListColumn>();
						list.add(addColumn("料号", "bill1", 60));
						list.add(addColumn("商品名称", "bill2", 150));
						list.add(addColumn("型号规格", "bill3", 150));
						list.add(addColumn("单位", "bill4", 60));
						list.add(addColumn("折算报关数量", "billSum1", 100));
						return list;
					}
				});
	}
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.add(getJButton());
			jToolBar.add(getJPanel());
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
			jButton.setText(" ");
		}
		return jButton;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(9, 5, 55, 14);
			jLabel.setText("余料情况");
			jLabel1.setBounds(154, 5, 69, 18);
			jLabel1.setText("仓库数量");
			jPanel.add(getJButton1(), null);
			jPanel.add(jLabel, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJTextField1(), null);
		}
		return jPanel;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(338, 5, 75, 21);
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
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setEditable(false);
			jTextField.setBounds(70, 5, 81, 20);
		}
		return jTextField;
	}
	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setEditable(false);
			jTextField1.setBounds(225, 5, 90, 20);
		}
		return jTextField1;
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
	 * @param temp The temp to set.
	 */
	public void setTemp(BillTemp temp) {
		this.temp = temp;
	}
        }  //  @jve:decl-index=0:visual-constraint="10,10"
