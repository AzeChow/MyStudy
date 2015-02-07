/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.ListSelectionModel;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.CheckHead;
import com.bestway.bcus.checkcancel.entity.CheckOwnerAccountComport;
import com.bestway.bcus.checkcancel.entity.CheckParameter;
import com.bestway.bcus.checkcancel.entity.ColorSet;
import com.bestway.bcus.client.checkcancel.FmFactoryCheck.deleteAll;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.enc.report.DgEmsExpExg;
import com.bestway.bcus.client.message.DgRecvProcessMessage;
import com.bestway.bcus.innermerge.entity.InnerMergeFileData;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.JTabbedPane;
import java.awt.Font;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmCheckAccountHead extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private CheckHead checkHead = null;

	private CheckCancelAction checkCancelAction = null;

	private ManualDeclareAction manualDeclearAction = null;

	private JTableListModel tableModel = null;
	
	private JTableListModel tableModelBgImgComparet = null;

	private EmsEdiTrHead emsEdiTrHead = null;

	private boolean isChange = false;

	private MessageAction messageAction = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JPanel jPanel1 = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private AttributiveCellTableModel tableModelImgResult = null;
	
	private AttributiveCellTableModel tableModelImgComparet = null;
	
	private CheckParameter head = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel4 = null;

	private JPanel jPanel5 = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null;

	private JScrollPane jScrollPane2 = null;

	private JTable jTable2 = null;
	
	private List lsResult = null;

	private JButton jButton3 = null;

	private JButton jButton4 = null;

	private JButton jButton5 = null;

	private JButton jButton6 = null;
	
	private List imgResultList = new Vector();

	private JButton jButton7 = null;

	private JButton jButton8 = null;

	private JPanel jPanel6 = null;

	private JScrollPane jScrollPane3 = null;

	private JTable jTable3 = null;

	private JButton jButton9 = null;

	/**
	 * This is the default constructor
	 */
	public FmCheckAccountHead() {
		super();
		checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
		manualDeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		messageAction = (MessageAction) CommonVars.getApplicationContext()
				.getBean("messageAction");
		jTable1 = new MultiSpanCellTable();
		jTable2 = new MultiSpanCellTable();
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(993, 482);
		this.setTitle("中期核查计算表");
		this.setContentPane(getJContentPane());
		
		List dataSource = new Vector();
		dataSource = checkCancelAction.findCheckParameter(new Request(CommonVars
				.getCurrUser(),true));
		checkCancelAction.checkfindCheckOwnerAccountnew (new Request(CommonVars
				.getCurrUser()));
        if (dataSource != null && dataSource.size()>0){
        	initTableCustom(dataSource);
        	head = (CheckParameter) dataSource.get(0);
        	inittable();
        } else {
        	initTableCustom(new Vector());
        	initTableImgResult(new Vector());
        	initTableImgComparet(new Vector());
        	initTableBgImgComparet(new Vector());
        }
        
        setstate();
        
        
		

	}
	
	private void inittable(){
		List list = checkCancelAction.findCheckOwnerAccount(new Request(CommonVars.getCurrUser()), head);
		if (list != null && list.size()>0){
		    initTableImgResult(list);
		} else {
			initTableImgResult(new Vector());
		}
		
		imgResultList = checkCancelAction.findCheckOwnerAccountComport(new Request(CommonVars.getCurrUser()),head);
    	if (imgResultList != null && imgResultList.size()>0){
    		initTableImgComparet(imgResultList);
    	} else {
    		initTableImgComparet(new Vector());
    	}
    	List cyList = checkCancelAction.findCheckBgCy(new Request(CommonVars.getCurrUser()),head);
    	if (cyList != null && cyList.size()>0){
    		initTableBgImgComparet(cyList);
    	} else {
    		initTableBgImgComparet(new Vector());
    	}
	}
	
	private void initTableCustom(final List list) {		
	      tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("核查次数", "emsNo",60));
						list.add(addColumn("核查起始日", "beginDate", 120));
						list.add(addColumn("核查结束日", "endDate", 120));
						return list;
					}
				});
	}
    
	
	private void initTableBgImgComparet(final List list) {
		tableModelBgImgComparet = new JTableListModel(jTable3, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册序号", "seqNum", 100, Integer.class));
						list.add(addColumn("料件名称", "name", 120));
						list.add(addColumn("型号规格", "spec", 120));
						list.add(addColumn("海关单位", "unit.name", 100));
						list.add(addColumn("A.帐面结余", "emsBalance",100));
						list.add(addColumn("B.实际库存", "factNum",100));
						list.add(addColumn("差异数量(A-B)", "cyNum",120));
						return list;
					}
				});
	}
	
	
    
    private void initTableImgResult(List list){
    	if (list == null){
    		list = new Vector();
    	}
		tableModelImgResult = new AttributiveCellTableModel ((MultiSpanCellTable) jTable1, list,
				new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("料号", "ptNo", 100));
				list.add(addColumn("料件名称", "name", 120));
				list.add(addColumn("型号规格", "spec", 100));
				list.add(addColumn("单位", "unit.name", 60));				
				list.add(addColumn("原料库存数量", "materielStockNum", 80)); 
				list.add(addColumn("原料在途数量", "materielOnwayNum", 80));
				list.add(addColumn("成品折料数量", "exgConvertImgNum", 80));
				list.add(addColumn("转进未报数量", "tranNoCustomsNum", 80)); 
				
				list.add(addColumn("废料数量", "flotsamNum",100));
				list.add(addColumn("在线数量", "onLineNum", 100));				
				list.add(addColumn("半成品折料数量", "halfExgConvertImgNum", 120));
				return list;
			}
		});
		TableColumnModel cm = jTable1.getColumnModel();

		ColumnGroup gUse= new ColumnGroup("其他原料库存");
		gUse.add(cm.getColumn(9));
		gUse.add(cm.getColumn(10));
		gUse.add(cm.getColumn(11));
	
		GroupableTableHeader header = (GroupableTableHeader) jTable1.getTableHeader();
        header.addColumnGroup(gUse);
	}
    
    
    private void initTableImgComparet(List list){
    	if (list == null){
    		list = new Vector();
    	}
    	tableModelImgComparet = new AttributiveCellTableModel ((MultiSpanCellTable) jTable2, list,
				new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("料号", "ptNo", 100));
				list.add(addColumn("料件名称", "name", 120));
				list.add(addColumn("型号规格", "spec", 100));
				list.add(addColumn("单位", "unit.name", 60));		
				
				
				list.add(addColumn("企业实际库存 (A)", "materielNum", 100));
				
				list.add(addColumn("原料库存数量", "materielStockNum", 100));
				list.add(addColumn("原料在途数量", "materielOnwayNum", 100));
				list.add(addColumn("成品折料数量", "exgConvertImgNum", 100));
				list.add(addColumn("其他原料库存", "otherStorkNum", 100));
				list.add(addColumn("转进未报数量", "tranNoCustomsNum", 100)); 
				
				list.add(addColumn("库存差异 (A-B)", "cyNum", 100));
				
				return list;
			}
		});
		TableColumnModel cm = jTable2.getColumnModel();

		ColumnGroup gUse= new ColumnGroup("理论库存 (B) = 原料库存 + 原料在途 + 成品折料 + 其他原料 - 转进未报");
		gUse.add(cm.getColumn(6));
		gUse.add(cm.getColumn(7));
		gUse.add(cm.getColumn(8));
		gUse.add(cm.getColumn(9));
		gUse.add(cm.getColumn(10));
		
		GroupableTableHeader header = (GroupableTableHeader) jTable2.getTableHeader();
        header.addColumnGroup(gUse);
        
        /*jTable2.getColumnModel().getColumn(11).setCellRenderer(
    			new ColorTableCellRenderer());*/
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
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}



	/**
	 * @return Returns the isChange.
	 */
	public boolean isChange() {
		return isChange;
	}

	/**
	 * @param isChange
	 *            The isChange to set.
	 */
	public void setChange(boolean isChange) {
		this.isChange = isChange;
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
	 * @return Returns the checkHead.
	 */
	public CheckHead getCheckHead() {
		return checkHead;
	}

	/**
	 * @param checkHead
	 *            The checkHead to set.
	 */
	public void setCheckHead(CheckHead checkHead) {
		this.checkHead = checkHead;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setText("中期核查计算表");
			jLabel.setForeground(java.awt.Color.blue);
			jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 24));
			jPanel = new JPanel();
			jPanel.setBackground(java.awt.Color.white);
			jPanel.add(jLabel, null);
		}
		return jPanel;
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
			jToolBar.add(getJPanel1());
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
			//jButton.setVisible(false);
			jButton.setText(" ");
		}
		return jButton;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(getJButton1(), null);
			jPanel1.add(getJButton2(), null);
			jPanel1.add(getJButton3(), null);
			jPanel1.add(getJButton4(), null);
			jPanel1.add(getJButton5(), null);
			jPanel1.add(getJButton6(), null);
			jPanel1.add(getJButton7(), null);
			jPanel1.add(getJButton8(), null);
			jPanel1.add(getJButton9(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new java.awt.Rectangle(5,5,103,22));
			jButton1.setText("计算说明");
			jButton1.setVisible(false);
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // 
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
			jButton2.setBounds(new java.awt.Rectangle(10,4,140,22));
			jButton2.setText("重新计算中期核查");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(FmCheckAccountHead.this,
							"确定要重新计算中期核查吗?", "确认", 0) == 0) {
						new find().start();
					}
					 
				}
			});
		}
		return jButton2;
	}

	
	class find extends Thread{				
		public void run(){
			 try {
			        CommonProgress.showProgressDialog();
			        CommonProgress.setMessage("系统正获取数据，请稍后...");
			        Date beginDate = head.getBeginDate();
					Date endDate= head.getEndDate();
					checkCancelAction.deleteCheckOwnerAccount(new Request(CommonVars.getCurrUser()),head);
					lsResult = checkCancelAction.accountCheckAccount(new Request(CommonVars.getCurrUser()),head);
					CommonProgress.closeProgressDialog();
				 } catch (Exception e) {
			        CommonProgress.closeProgressDialog();
			        JOptionPane.showMessageDialog(FmCheckAccountHead.this,
			                "获取数据失败：！" + e.getMessage(), "提示", 2);    
				 } finally {			 	
					 initTableImgResult(lsResult);
				 }
			}
		}

	
	
	class findComport extends Thread{				
		public void run(){
			 try {
			        CommonProgress.showProgressDialog();
			        CommonProgress.setMessage("系统正获取数据，请稍后...");
			        
			        //删除对比表
					checkCancelAction.deleteCheckOwnerAccountComport(new Request(CommonVars.getCurrUser()),head);
					List list = tableModelImgResult.getList();
					
					imgResultList = checkCancelAction.getAccountComport(new Request(CommonVars.getCurrUser()),list,head);
					CommonProgress.closeProgressDialog();
				 } catch (Exception e) {
			        CommonProgress.closeProgressDialog();
			        JOptionPane.showMessageDialog(FmCheckAccountHead.this,
			                "获取数据失败：！" + e.getMessage(), "提示", 2);    
				 } finally {			 	
					 initTableImgComparet(imgResultList);
				 }
			}
		}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setDividerLocation(200);
			jSplitPane.setRightComponent(getJPanel3());
			jSplitPane.setLeftComponent(getJPanel2());
		}
		return jSplitPane;
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
			jPanel2.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
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
			jPanel3.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
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
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModel == null || tableModel.getCurrentRow() == null){								
							    return;
							} 
							head = (CheckParameter) tableModel.getCurrentRow();		
							inittable();
						}
					});
		}
		return jTable;
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("核查计算表", null, getJPanel4(), null);			
			jTabbedPane.addTab("核查对比表", null, getJPanel5(), null);
			jTabbedPane.addTab("报关级核查对比表", null, getJPanel6(), null);
			jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					setstate();
				}
			});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
	}

	/**
	 * This method initializes jScrollPane2	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTable2());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jTable2	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable2() {
		if (jTable2 == null) {
			jTable2 = new JTable();
			
			
		}
		return jTable2;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new java.awt.Rectangle(153,4,112,22));
			jButton3.setText("核查对比计算");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(FmCheckAccountHead.this,
							"确定要计算核查对比吗?", "确认", 0) == 0) {
					     new findComport().start();
					}
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
			jButton4.setBounds(new java.awt.Rectangle(269,5,99,22));
			jButton4.setText("同步至核查");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(FmCheckAccountHead.this,
							"确定要同步到核查表吗?", "确认", 0) == 0) {
						List emsH2kList = null;
						emsH2kList = manualDeclearAction
								.findEmsHeadH2k(new Request(CommonVars
										.getCurrUser(),true));
						if (emsH2kList.isEmpty()){
							JOptionPane.showMessageDialog(FmCheckAccountHead.this,"电子帐册中不存在数据","提示",2);
							return;
						} else {
							EmsHeadH2k emsHeadh2k = (EmsHeadH2k) emsH2kList.get(0);
							CheckHead checkHead = new CheckHead();
							
							new toCheckImg(emsHeadh2k).start();
							
						}
					}
				}
			});
		}
		return jButton4;
	}
	
	
	
	class toCheckImg extends Thread{		
		public EmsHeadH2k emsHeadh2k = null;
		
		public toCheckImg(EmsHeadH2k emsHeadH2k){
			this.emsHeadh2k = emsHeadH2k;
		}
		public void run(){
		     try {
		        CommonProgress.showProgressDialog();
		        CommonProgress.setMessage("系统正同步数据，请稍后...");			        
		        //删除对比表
				checkCancelAction.newToCheckHead(new Request(CommonVars.getCurrUser()),head,emsHeadh2k);
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmCheckAccountHead.this,
		                "同步完毕！", "提示", 2); 
				
			 } catch (Exception e) {
		        CommonProgress.closeProgressDialog();
		        JOptionPane.showMessageDialog(FmCheckAccountHead.this,
		                "同步数据失败：！" + e.getMessage(), "提示", 2);    
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
			jButton5.setBounds(new java.awt.Rectangle(790,4,70,23));
			jButton5.setText("退出");
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmCheckAccountHead.this.dispose();
				}
			});
		}
		return jButton5;
	}

	/**
	 * This method initializes jButton6	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setBounds(new java.awt.Rectangle(371,5,126,22));
			jButton6.setText("自定义差异颜色");
			jButton6.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgColorDefine dg = new DgColorDefine();
					dg.setVisible(true);
					List list = tableModelImgComparet.getList();
					initTableImgComparet(list);
				}
			});
		}
		return jButton6;
	}
	
	class ColorTableCellRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component c = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			c = checkValue(table, row, column,c);
			return c;
		}
	}
	
	private Component checkValue(JTable table, int row, int column,Component c) {
		CheckOwnerAccountComport data = (CheckOwnerAccountComport) tableModelImgComparet
				.getObjectByRow(row);
		Double cynum = data.getCyNum() == null?Double.valueOf(0):data.getCyNum();
		//System.out.println("cynum:"+cynum);
		if (cynum == null){
			c.setForeground(table.getForeground());
			c.setBackground(table.getBackground());
		}
		ColorSet obj = checkCancelAction.findColorByNum(new Request(CommonVars.getCurrUser()),cynum);
		if (obj != null){
			int x = obj.getColor();
			c.setBackground(getColor(obj.getColor()));
			c.setForeground(Color.black);
		} else {
			c.setForeground(table.getForeground());
			c.setBackground(table.getBackground());
		}
		return c;
	}
	
	private Color getColor(int x){
		if (x == ColorSet.RED){
			return Color.red;
		} else if (x == ColorSet.DEEPYELLOW){
			return Color.orange;
		} else if (x == ColorSet.YELLOW){
			return Color.yellow;
		} else if (x == ColorSet.DEEPGREEN){
			return Color.green;
		} else if (x == ColorSet.GREEN){
			return Color.cyan;
		}
		return null;
	}

	/**
	 * This method initializes jButton7	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setBounds(new java.awt.Rectangle(617,4,102,23));
			jButton7.setText("修改对比表");
			jButton7.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {					
						if (tableModelImgComparet == null || tableModelImgComparet.getCurrentRow() == null){
							  JOptionPane.showMessageDialog(FmCheckAccountHead.this,"请选择要修改的对比表","提示",2);
										return;
						}
						DgCheckAccountResult dg = new DgCheckAccountResult();
						dg.setTableModel(tableModelImgComparet);
						dg.setVisible(true);
						List list = tableModelImgComparet.getList();
						initTableImgComparet(list);
		       }
			});
		}
		return jButton7;
	}
	
	private void setstate(){
		jButton2.setEnabled(jTabbedPane.getSelectedIndex()==0);
		jButton3.setEnabled(jTabbedPane.getSelectedIndex()==1);
		jButton7.setEnabled(jTabbedPane.getSelectedIndex()==1);
		jButton9.setEnabled(jTabbedPane.getSelectedIndex()==2);
		jButton4.setEnabled(jTabbedPane.getSelectedIndex()==0);
		jButton6.setEnabled(jTabbedPane.getSelectedIndex()==1);
	}

	/**
	 * This method initializes jButton8	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton8() {
		if (jButton8 == null) {
			jButton8 = new JButton();
			jButton8.setBounds(new java.awt.Rectangle(722,4,66,23));
			jButton8.setText("删除");
			jButton8.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new deleteAll().start();
				}
			});
		}
		return jButton8;
	}
	
	
	class deleteAll extends Thread{
		public void run(){
			try{
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在删除数据，请稍候.....");
				if (jTabbedPane.getSelectedIndex() == 0) { 
					List list = tableModelImgResult.getCurrentRows();
					checkCancelAction.deleteList(new Request(CommonVars.getCurrUser()),list);
					tableModelImgResult.deleteRows(list);
				} else  if (jTabbedPane.getSelectedIndex() == 1){ 
					List list = tableModelImgComparet.getCurrentRows();
					checkCancelAction.deleteList(new Request(CommonVars.getCurrUser()),list);
					tableModelImgComparet.deleteRows(list);
				} else  if (jTabbedPane.getSelectedIndex() == 2){ 
					List list = tableModelBgImgComparet.getCurrentRows();
					checkCancelAction.deleteList(new Request(CommonVars.getCurrUser()),list);
					tableModelBgImgComparet.deleteRows(list);
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e){
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmCheckAccountHead.this,"删除失败：！"+e.getMessage(),"提示",2);
			}
		}
	}

	/**
	 * This method initializes jPanel6	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setLayout(new BorderLayout());
			jPanel6.add(getJScrollPane3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jScrollPane3	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getJTable3());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jTable3	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable3() {
		if (jTable3 == null) {
			jTable3 = new JTable();
		}
		return jTable3;
	}

	/**
	 * This method initializes jButton9	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton9() {
		if (jButton9 == null) {
			jButton9 = new JButton();
			jButton9.setBounds(new java.awt.Rectangle(499,4,114,23));
			jButton9.setText("报关核查对比");
			jButton9.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					 new findBgComport().start();
				}
			});
		}
		return jButton9;
	}
	
	
	class findBgComport extends Thread{				
		public void run(){
			 try {
			        CommonProgress.showProgressDialog();
			        CommonProgress.setMessage("系统正获取数据，请稍后...");
			        
			        //删除对比表
					checkCancelAction.deleteCheckImgBgComport(new Request(CommonVars.getCurrUser()),head);
					
					imgResultList = checkCancelAction.findFactoryCheckBgCy(new Request(CommonVars.getCurrUser()),head);
					CommonProgress.closeProgressDialog();
				 } catch (Exception e) {
			        CommonProgress.closeProgressDialog();
			        JOptionPane.showMessageDialog(FmCheckAccountHead.this,
			                "获取数据失败：！" + e.getMessage(), "提示", 2);    
				 } finally {			 	
					 initTableBgImgComparet(imgResultList);
				 }
			}
		}
	
} //  @jve:decl-index=0:visual-constraint="10,10"
