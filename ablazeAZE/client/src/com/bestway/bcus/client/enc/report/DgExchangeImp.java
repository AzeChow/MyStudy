/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgExchangeImp extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JTableListModel tableModel = null;
	private EncAction encAction = null;
	private List list = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane1 = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JCalendarComboBox jCalendarComboBox = null;
	private JLabel jLabel2 = null;
	private JCalendarComboBox jCalendarComboBox1 = null;
	private List lsResult = null;
	private ManualDeclareAction manualDecleareAction = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
		/**
	 * This is the default constructor
	 */
	public DgExchangeImp() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
                       "encAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
                    .getApplicationContext().getBean("manualdeclearAction");
		initialize();
		
	}

	private void initUIComponents(){
		List list = manualDecleareAction.findEmsHeadH2k(new Request(CommonVars.getCurrUser(),true));
		if (list != null && list.size()>0){
			String emsno = ((EmsHeadH2k) list.get(0)).getEmsNo();
//			jLabel3.setText("帐册号："+emsno);
		}
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(744, 454);
		this.setTitle("应退换进口料件");
		this.jCalendarComboBox.setDate(CommonVars.getBeginDate());
		this.jCalendarComboBox1.setDate(new Date());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {

				initUIComponents();
				list = new Vector();
				initTable(list);	
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
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		if(list==null){
			list = new ArrayList();
		}
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("帐册号", "bill1", 80));
						list.add(addColumn("备案序号", "bill2", 60));
						list.add(addColumn("商品编码", "bill3", 100));
						list.add(addColumn("名称/规格", "bill4", 100));
						list.add(addColumn("单位", "bill5", 50));
						list.add(addColumn("申报日期", "bill6", 50));
						list.add(addColumn("进口量", "billSum1", 80));
						list.add(addColumn("期限天数", "bill7", 60));				
						list.add(addColumn("应退数量", "billSum2", 80));
						return list;
					}
				});
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
			jSplitPane.setDividerLocation(100);
			jSplitPane.setDividerSize(0);
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
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jLabel.setText("应退换进口料件");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(8, 8, 298, 34);
			
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 24));
			jLabel.setForeground(java.awt.Color.blue);
			jLabel1.setBounds(376, 10, 61, 18);
			jLabel1.setText("报关日期：");
			jLabel2.setBounds(534, 9, 18, 19);
			jLabel2.setText("至");
			
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJCalendarComboBox1(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
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
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
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
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable());
		}
		return jScrollPane1;
	}
	/**
	 * This method initializes jCalendarComboBox	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */    
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(441, 9, 86, 20);
		}
		return jCalendarComboBox;
	}
	/**
	 * This method initializes jCalendarComboBox1	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */    
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.setBounds(555, 9, 87, 21);
		}
		return jCalendarComboBox1;
	}
	class find extends Thread{
		
		public void run(){
			 try {
	            CommonProgress.showProgressDialog();
	            CommonProgress.setMessage("系统正获取数据，请稍后...");
				Date beginDate=CommonVars.dateToStandDate(jCalendarComboBox.getDate());
				Date endDate=CommonVars.dateToStandDate(jCalendarComboBox1.getDate());
				lsResult=encAction.jisuanExchangeImp(new Request(
						CommonVars.getCurrUser()),0,beginDate,endDate);
				CommonProgress.closeProgressDialog();
			 } catch (Exception e) {
		        CommonProgress.closeProgressDialog();
		        JOptionPane.showMessageDialog(DgExchangeImp.this,
		                "获取数据失败：！" + e.getMessage(), "提示", 2);    
			 } finally {			 	
			 	initTable(lsResult);
			 }
		}
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(488, 49, 64, 22);
			jButton.setText("查询");
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					new find().start();
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
			jButton1.setBounds(581, 49, 61, 22);
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
				dispose();
				}
			});
		}
		return jButton1;
	}
  }  //  @jve:decl-index=0:visual-constraint="10,10"
