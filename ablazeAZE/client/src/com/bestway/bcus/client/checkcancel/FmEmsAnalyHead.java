/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.action.CheckCancelAuthorityAction;
import com.bestway.bcus.checkcancel.entity.EmsAnalyHead;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Dimension;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmEmsAnalyHead extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JButton jButton7 = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private CheckCancelAction checkCancelAction = null;  //  @jve:decl-index=0:

	private JTableListModel tableModel = null;


	private JButton jButton3 = null;
	private JButton jButton4 = null;
	private JButton jButton5 = null;
	private JLabel jLabel = null;
	private EmsAnalyHead head = null;
	private CheckCancelAuthorityAction checkCancelAuthorityAction = null;
	/**
	 * This is the default constructor
	 */
	public FmEmsAnalyHead() {
		super();
		checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
		checkCancelAuthorityAction = (CheckCancelAuthorityAction) CommonVars
           .getApplicationContext().getBean("checkCancelAuthorityAction");
		initialize();
		checkCancelAuthorityAction.analyseBrown(new Request(CommonVars.getCurrUser()));

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(760, 367);
		this.setTitle("核销期设置");
		this.setContentPane(getJContentPane());

		List dataSource = null;
		dataSource = checkCancelAction.findEmsAnalyHead(new Request(CommonVars
				.getCurrUser()));

		initTable(dataSource);
		setState();

	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("核销流水号", "emsNo", 150));
						list.add(addColumn("核销起始日", "beginDate", 150));
						list.add(addColumn("核销截止日", "endDate", 150));
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
			jLabel = new JLabel();
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jLabel.setText("核销期设置,更易于控制模拟核销");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel.setForeground(new java.awt.Color(0,102,51));
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton3());
			jToolBar.add(getJButton4());
			jToolBar.add(getJButton5());
			jToolBar.add(getJButton7());
			jToolBar.add(jLabel);
		}
		return jToolBar;
	}

	private void fillHead(EmsAnalyHead head) {
	   head.setEmsNo(checkCancelAction.getNum(new Request(CommonVars.getCurrUser()),"EmsAnalyHead","emsNo"));
	   head.setBeginDate(CommonVars.dateToStandDate(new Date()));
	   head.setEndDate(CommonVars.dateToStandDate(new Date()));
	   head.setCompany(CommonVars.getCurrUser().getCompany());
	}

	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("新增");
			jButton.setPreferredSize(new Dimension(70, 30));
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
						EmsAnalyHead head = new EmsAnalyHead();
						fillHead(head);
						head = checkCancelAction.SaveEmsAnalyHead(
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
			jButton1.setPreferredSize(new Dimension(70, 30));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkCancelAuthorityAction.analyseModity(new Request(CommonVars.getCurrUser()));
					edit();
				}
			});

		}
		return jButton1;
	}

	private void edit() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmEmsAnalyHead.this, "请选择你将要修改的记录",
					"提示！", 0);
			return;
		}
		DgEmsAnalyEdit dg = new DgEmsAnalyEdit();
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
			jButton2.setPreferredSize(new Dimension(60, 30));
			jButton2.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkCancelAuthorityAction.analyseDelete(new Request(CommonVars.getCurrUser()));
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmEmsAnalyHead.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}

					head = (EmsAnalyHead) tableModel.getCurrentRow();

					if (JOptionPane.showConfirmDialog(FmEmsAnalyHead.this,
							"确定要删除该核销期吗?\n注意：其表体将一并被删除", "确认", 0) == 0) {
                        new deleteAll().start();                    
						tableModel.deleteRow(head);
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
				checkCancelAction.deleteEmsBgTotal(new Request(CommonVars.getCurrUser()),FmEmsAnalyHead.this.head);     //删除报关进出口总表EmsBgTotal						
				checkCancelAction.deleteEmsBgExgImgAll(new Request(CommonVars.getCurrUser()),FmEmsAnalyHead.this.head,"",true); //删除报关成品出口折料EmsBgExgBg
				checkCancelAction.deleteEmsBgExgAll(new Request(CommonVars.getCurrUser()),FmEmsAnalyHead.this.head);    //删除报关成品出口EmsBgExg
				checkCancelAction.deleteEmsBgImgAll(new Request(CommonVars.getCurrUser()),FmEmsAnalyHead.this.head);    //删除报关料件进口EmsBgImg
				
				checkCancelAction.deleteEmsPdTotalAll(new Request(CommonVars.getCurrUser()),FmEmsAnalyHead.this.head);   //清空盘点分析总表EmsPdTotal
				checkCancelAction.deleteEmsPdExgImgBgAll(new Request(CommonVars.getCurrUser()),FmEmsAnalyHead.this.head);//删除盘点报关折料EmsPdExgImgBg
				checkCancelAction.deletePdImgBg(new Request(CommonVars.getCurrUser()),FmEmsAnalyHead.this.head); //删除盘点料件报关EmsPdImgBg
				checkCancelAction.deletePdExgBg(new Request(CommonVars.getCurrUser()),FmEmsAnalyHead.this.head); //删除盘点成品报关EmsPdImgBg
				checkCancelAction.deleteEmsPdImgAll(new Request(CommonVars.getCurrUser()),FmEmsAnalyHead.this.head); //EmsPdImg
				checkCancelAction.deleteEmsPdExgAll(new Request(CommonVars.getCurrUser()),FmEmsAnalyHead.this.head); //EmsPdExg
				
				checkCancelAction.deleteEmsCyAll(new Request(CommonVars.getCurrUser()),head);//清空差异分析总表
				checkCancelAction.deleteEmsAnalyHead(new Request(CommonVars.getCurrUser()),head);
				CommonProgress.closeProgressDialog();
			} catch (Exception e){
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsAnalyHead.this,"删除失败：！"+e.getMessage(),"提示",2);
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
			jButton7.setPreferredSize(new Dimension(70, 30));
			jButton7.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					FmEmsAnalyHead.this.dispose();

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


	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("报关数据分析");
			jButton3.setPreferredSize(new Dimension(90, 30));
			jButton3.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					checkCancelAuthorityAction.analyseData(new Request(CommonVars.getCurrUser()));
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmEmsAnalyHead.this, "请选择你将要修改的记录",
								"提示！", 0);
						return;
					}
					DgEmsAnalyBg dg = new DgEmsAnalyBg();
					dg.setTableModel(tableModel);
					dg.setVisible(true);
					
				}
			});
		}
		return jButton3;
	}
	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("盘点数据分析");
			jButton4.setPreferredSize(new Dimension(90, 30));
			jButton4.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					checkCancelAuthorityAction.analysePd(new Request(CommonVars.getCurrUser()));
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmEmsAnalyHead.this, "请选择你将要修改的记录",
								"提示！", 0);
						return;
					}
					DgEmsAnalyPanD dg = new DgEmsAnalyPanD();
					dg.setTableModel(tableModel);
					dg.setVisible(true);
				}
			});
		}
		return jButton4;
	}
	/**
	 * This method initializes jButton5	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("差异分析");
			jButton5.setPreferredSize(new Dimension(70, 30));
			jButton5.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					checkCancelAuthorityAction.analyseCy(new Request(CommonVars.getCurrUser()));
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmEmsAnalyHead.this, "请选择你将要修改的记录",
								"提示！", 0);
						return;
					}
					DgEmsAnalyDiff dg = new DgEmsAnalyDiff();
					dg.setTableModel(tableModel);
					dg.setVisible(true);
				}
			});
		}
		return jButton5;
	}
   }  //  @jve:decl-index=0:visual-constraint="23,14"
