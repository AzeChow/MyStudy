/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.CancelHead;
import com.bestway.bcus.checkcancel.entity.CheckHead;
import com.bestway.bcus.checkcancel.entity.CheckParameter;
import com.bestway.bcus.checkcancel.entity.EmsAnalyHead;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.message.DgRecvProcessMessage;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.RrportDelcareType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

import javax.swing.JLabel;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmCheckParameter extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JButton jButton7 = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private CheckCancelAction checkCancelAction = null;

	private JTableListModel tableModel = null;


	private CheckParameter head = null;
	/**
	 * This is the default constructor
	 */
	public FmCheckParameter() {
		super();
		checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(680, 367);
		this.setTitle("中期核查参数设定");
		this.setContentPane(getJContentPane());

		List dataSource = null;
		dataSource = checkCancelAction.findCheckParameter(new Request(CommonVars
				.getCurrUser()));

		initTable(dataSource);
		setState();

	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("核查次数", "emsNo", 150));
						list.add(addColumn("核查起始日", "beginDate", 150));
						list.add(addColumn("核查截止日", "endDate", 200));
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
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton7());
		}
		return jToolBar;
	}

	private void fillHead(CheckParameter head) {
	   head.setEmsNo(checkCancelAction.getNum(new Request(CommonVars.getCurrUser()),"CheckParameter","emsNo"));
	   head.setBeginDate(java.sql.Date
				.valueOf("2006-01-01"));
	   head.setEndDate(CommonVars.dateToStandDate(new Date()));
	   head.setCompany(CommonVars.getCurrUser().getCompany());
	}

	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("新增");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
						CheckParameter head = new CheckParameter();
						fillHead(head);
						head = checkCancelAction.SaveCheckParameter(
								new Request(CommonVars.getCurrUser()),
								head);
						tableModel.addRow(head);
						setState();
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
			jButton1.setText("修改");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					edit();
				}
			});

		}
		return jButton1;
	}

	private void edit() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmCheckParameter.this, "请选择你将要修改的记录",
					"提示！", 0);
			return;
		}
		DgCheckParameterEdit dg = new DgCheckParameterEdit();
		dg.setTableModel(tableModel);
		dg.setVisible(true);
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("删除");
			jButton2.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCheckParameter.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}

					head = (CheckParameter) tableModel.getCurrentRow();

					if (JOptionPane.showConfirmDialog(FmCheckParameter.this,
							"确定要删除该核销期吗?\n注意：其表体将一并被删除", "确认", 0) == 0) {
                        //new deleteAll().start();   
						try{
						   checkCancelAction.deleteCheckParameter(new Request(CommonVars.getCurrUser()),head);
						   tableModel.deleteRow(head);
						} catch (Exception e1){
							JOptionPane.showMessageDialog(FmCheckParameter.this,
									"不能直接删除，已有引用，请先删除其他资料！", "确认", 1);
							return;
						}
						setState();
					}
				}
			});
		}
		return jButton2;
	}

	class deleteAll extends Thread{
		public void run(){
			try{
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在删除数据，请稍候.....");
				CommonProgress.closeProgressDialog();
			} catch (Exception e){
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmCheckParameter.this,"删除失败：！"+e.getMessage(),"提示",2);
			}
		}
	}
	private void setState() {
		jButton1.setEnabled(tableModel.getRowCount() > 0); //修改
		jButton2.setEnabled(tableModel.getRowCount() > 0); //删除
	}

	/**
	 * 
	 * This method initializes jButton7
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton s
	 */
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setText("关闭");
			jButton7.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					FmCheckParameter.this.dispose();

				}
			});

		}
		return jButton7;
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
		if (jTable == null) {
			jTable = new JTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {
						edit();
					}
				}
			});

		}
		return jTable;
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
		}
		return jScrollPane;
	}


	/**
	 * @return Returns the checkCancelAction.
	 */
	public CheckCancelAction getCheckCancelAction() {
		return checkCancelAction;
	}

	/**
	 * @param checkCancelAction
	 *            The checkCancelAction to set.
	 */
	public void setCheckCancelAction(CheckCancelAction checkCancelAction) {
		this.checkCancelAction = checkCancelAction;
	}
   }  //  @jve:decl-index=0:visual-constraint="23,14"
