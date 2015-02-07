/*
 * Created on 2004-7-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.message.DgRecvProcessMessage;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.dao.EmsEdiTrDao;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFas;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.bcus.message.entity.MessageQuery;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.EdiType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmEmsHeadH2kFas extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JButton jButton3 = null;

	private JButton jButton4 = null;

	private JButton jButton5 = null;

	private JButton jButton6 = null;

	private JButton jButton7 = null;
	
	private JButton btnBrowse =null;

	private ManualDeclareAction manualDeclearAction = null;
	
	private MessageAction messageAction = null;

	private SystemAction systemAction = null;

	private JTableListModel tableModel = null;
	private JTableListModel tableModel1 = null;

	private EmsHeadH2k emsHeadH2k = null; //电子帐册表头

	private boolean isChange = false;
	private boolean isChange1 = false;
	private int dataState = DataState.BROWSE;

	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable1 = null;
	private JScrollPane jScrollPane1 = null;
	private List dataSource = null;
	private EmsHeadH2kFas fas = null;

	private JButton jPrintButton = null;
	
	private JPopupMenu	pmPrintData = null;
	private JMenuItem printAll = null; 
	private JMenuItem printOut = null;
	private JMenuItem printImport = null;
    /**
	 * This is the default constructor
	 */
	public FmEmsHeadH2kFas() {
		super();

		manualDeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		messageAction = (MessageAction)CommonVars.getApplicationContext()
		   .getBean("messageAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
		.getBean("systemAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(600, 400);
		this.setTitle("电子帐册分册备案申请");
		this.setContentPane(getJContentPane());
		dataSource = manualDeclearAction.findEmsHeadH2k(new Request(
				CommonVars.getCurrUser())); //电子帐册
		if (dataSource != null && !dataSource.isEmpty()){
		   initTable(dataSource);
		   emsHeadH2k = (EmsHeadH2k) dataSource.get(0);
		   String emsNo = emsHeadH2k.getEmsNo();
		   if (emsHeadH2k.getEmsNo() == null){
		   	  emsNo = "";
		   }
		   List list = manualDeclearAction.findEmsHeadH2kFas(new Request(CommonVars.getCurrUser()),emsNo);
		   if (list != null && !list.isEmpty()){
		   	  initTable1(list);
		   } else {
		   	  initTable1(new Vector());
		   }
		} else {
			initTable(new Vector());
			initTable1(new Vector());
		}
		dataState = DataState.BROWSE;
		setState();
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("企业内部编号", "copEmsNo", 100));
						list.add(addColumn("帐册编号", "emsNo", 100));
						list.add(addColumn("批文帐册号", "sancEmsNo", 100));
						list.add(addColumn("申报类型", "declareType", 80));
						list.add(addColumn("审批状态", "declareState", 80));
						list.add(addColumn("修改标志", "modifyMark", 60));
						list.add(addColumn("变更次数", "modifyTimes", 60));
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 120));
						list.add(addColumn("加工单位代码", "machCode", 100));
						list.add(addColumn("加工单位名称", "machName", 120));
						list.add(addColumn("电话", "tel", 80));
						list.add(addColumn("地址", "address", 100));
						list.add(addColumn("申报日期", "declareDate", 80));
						list.add(addColumn("批准日期", "newApprDate", 80));
						list.add(addColumn("年加工能力", "machinAbility", 80));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("1")) {
							returnValue = "申请备案";
							FmEmsHeadH2kFas.this.setChange(false);
						} else if (value.equals("2")) {
							returnValue = "申请变更";
							FmEmsHeadH2kFas.this.setChange(true);
						}
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("1")) {
							if (FmEmsHeadH2kFas.this.isChange)
								returnValue = "申请变更";
							else
								returnValue = "申请备案";
						} else if (value.equals("2"))
							returnValue = "等待审批";
						else if (value.equals("3"))
							returnValue = "正在执行";
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}
					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(ModifyMarkState.ADDED))
							returnValue = "新增";
					    else if (value.equals(ModifyMarkState.DELETED))
							returnValue = "已删除";
						else if (value.equals(ModifyMarkState.MODIFIED))
							returnValue = "已修改";
						else if (value.equals(ModifyMarkState.UNCHANGE))
							returnValue = "未修改";
						return returnValue;
					}
				});

	}
	private void initTable1(final List list) {
		tableModel1 = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("电子帐册编号", "emsHeadH2kNo", 100));
						list.add(addColumn("企业内部编号", "copEmsNo", 100));
						list.add(addColumn("分册号", "emsNo", 100));
						list.add(addColumn("变更次数", "modifyTimes", 60));
						list.add(addColumn("申报类型", "declareType", 80));
						list.add(addColumn("审批状态", "declareState", 80));
						list.add(addColumn("修改标志", "modifyMark", 60));						
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 120));
						list.add(addColumn("申报单位名称", "declareName", 120));
						list.add(addColumn("申报日期", "declareDate", 80));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		jTable1.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("1")) {
							returnValue = "申请备案";
							FmEmsHeadH2kFas.this.setChange1(false);
						} else if (value.equals("2")) {
							returnValue = "申请变更";
							FmEmsHeadH2kFas.this.setChange1(true);
						}
						return returnValue;
					}
				});
		jTable1.getColumnModel().getColumn(6).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("1")) {
							if (FmEmsHeadH2kFas.this.isChange1)
								returnValue = "申请变更";
							else
								returnValue = "申请备案";
						} else if (value.equals("2"))
							returnValue = "等待审批";
						else if (value.equals("3"))
							returnValue = "正在执行";
						return returnValue;
					}
				});
		jTable1.getColumnModel().getColumn(7).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}
					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(ModifyMarkState.ADDED))
							returnValue = "新增";
					    else if (value.equals(ModifyMarkState.DELETED))
							returnValue = "已删除";
						else if (value.equals(ModifyMarkState.MODIFIED))
							returnValue = "已修改";
						else if (value.equals(ModifyMarkState.UNCHANGE))
							returnValue = "未修改";
						return returnValue;
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
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnBrowse());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnChange());
			jToolBar.add(getBtnDeclare());
			jToolBar.add(getBtnProcess());
			jToolBar.add(getJButton7());
			jToolBar.add(getJPrintButton());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
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
	private JButton getBtnAdd() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("新增");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (tableModel.getRowCount()==0){
                    	JOptionPane.showMessageDialog(FmEmsHeadH2kFas.this,"电子帐册数据为空，不能新增分册！","提示",2);
                    	return;
                    }
                    emsHeadH2k = (EmsHeadH2k) dataSource.get(0);
                    if (emsHeadH2k.getEmsNo()==null || emsHeadH2k.getEmsNo().equals("")){
                    	JOptionPane.showMessageDialog(FmEmsHeadH2kFas.this,"帐册编号不能为空!","提示",2);
                    	return;
                    }
                    EmsHeadH2kFas fas = new EmsHeadH2kFas();
                    addFas(emsHeadH2k,fas);
                    fas = manualDeclearAction.saveEmsHeadH2kFas(new Request(CommonVars.getCurrUser()),fas);
                    tableModel1.addRow(fas);
				}  
			});

		}
		return jButton;
	}
    private void addFas(EmsHeadH2k emsHeadH2k,EmsHeadH2kFas fas){
       fas.setCompany(CommonVars.getCurrUser().getCompany());
       fas.setEmsHeadH2kNo(emsHeadH2k.getEmsNo()); //帐册编号
       fas.setCopEmsNo(systemAction
			.generateAutoNo(EmsHeadH2kFas.class)); //企业内部编号
       fas.setDeclareType(DeclareState.APPLY_POR); //申报类型
       fas.setDeclareState(DeclareState.APPLY_POR);//申报状态
       fas.setHistoryState(new Boolean(false));    //历史状态
       fas.setModifyTimes(Integer.valueOf(0));     //变更次数
       fas.setModifyMark(ModifyMarkState.ADDED);//修改标志
       fas.setTradeCode(emsHeadH2k.getTradeCode());//经营单位代码
       fas.setTradeName(emsHeadH2k.getTradeName());//经营单位名称
       fas.setDeclareCode(emsHeadH2k.getMachCode());//申报单位代码
       fas.setDeclareName(emsHeadH2k.getMachName());//申报单位名称
       fas.setInputEr(CommonVars.getCurrUser().getLoginName());//录入员代码
       SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
       Date now = new Date();
	   String defaultDate = bartDateFormat.format(now);
       fas.setInputDate(java.sql.Date.valueOf(defaultDate));  //录入日期       
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
	private JButton getBtnEdit() {
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
	
	/**
	 * 
	 * This method initializes btnView（浏览按钮）
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */

	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setText("浏览");
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmEmsHeadH2kFas.this, "请选择你将要浏览的记录",
								"提示！", 0);
						return;
					}
					DgEmsHeadH2kFas dg = new DgEmsHeadH2kFas();
					dg.setTableModel(tableModel1);
					dg.setVisible(true);
				}
			});

		}
		return btnBrowse;
	}
	
	
	private void edit() {
		if (tableModel1.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmEmsHeadH2kFas.this,
					"请选择你将要修改的记录", "提示！", 0);
			return;
		}
		DgEmsHeadH2kFas dg = new DgEmsHeadH2kFas();
		dg.setChange(((EmsHeadH2kFas) tableModel1.getCurrentRow())
				.getDeclareType().equals(DelcareType.MODIFY));
		dg.setTableModel(tableModel1);
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
	private JButton getBtnDelete() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("删除");
			jButton2.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel1.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmEmsHeadH2kFas.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmEmsHeadH2kFas.this,
							"是否要删除该帐册记录？\n请注意：其下的成品料件将一并被删除！", "确认", 0)== 0) {
						EmsHeadH2kFas emsEdi = (EmsHeadH2kFas) tableModel1
		    			.getCurrentRow();
						    manualDeclearAction.deleteEmsHeadH2kFasImgExg(new Request(
									CommonVars.getCurrUser()), emsEdi);
							manualDeclearAction.deleteEmsHeadH2kFas(new Request(
									CommonVars.getCurrUser()), emsEdi);
							tableModel1.deleteRow(emsEdi);
						}						
						setState();
				}
			});

		}
		return jButton2;
	}

	/**
	 * 
	 * This method initializes jButton3
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnChange() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("变更");
			jButton3.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) { 
					if (tableModel1.getCurrentRow()==null){
						JOptionPane.showMessageDialog(FmEmsHeadH2kFas.this,"请选择要变更的分册!","提示",2);
						return;
					}
					fas = (EmsHeadH2kFas) tableModel1.getCurrentRow();
					EmsHeadH2kFas emsHeadH2kFas = manualDeclearAction.emsHeadH2FasChange(new Request(CommonVars.getCurrUser()),fas);
					tableModel1.addRow(emsHeadH2kFas);
					setState();
				}
			});

		}
		return jButton3;
	}

	/**
	 * 
	 * This method initializes jButton4
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnDeclare() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("海关申报");
			jButton4.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModel1.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmEmsHeadH2kFas.this,
								"请选择你将要申报的记录", "提示！", 0);
						return;
					}		
					if (JOptionPane.showConfirmDialog(FmEmsHeadH2kFas.this,
							"是否确定要将【电子帐册分册】向海关申报吗？", "确认", 0)== 0) {
						new chelonian().start();
					}				
					

				}
			});

		}
		return jButton4;
	}
	
	class chelonian extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(FmEmsHeadH2kFas.this);
				CommonProgress.setMessage("系统正在进行海关申报，请稍后...");
				String messageName = null;
				Date now = new Date();
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				String defaultDate = bartDateFormat.format(now);
				fas = (EmsHeadH2kFas) tableModel1.getCurrentRow();
				fas.setDeclareDate(java.sql.Date.valueOf(defaultDate));// 申报日期
				fas.setDeclareTime(CommonVars.dateToTime(now)); // 申报时间
				fas = manualDeclearAction.saveEmsHeadH2kFas(new Request(
						CommonVars.getCurrUser()), fas);
				tableModel1.updateRow(fas);
				fas = (EmsHeadH2kFas) tableModel1.getCurrentRow();
				List list = CustomBaseList.getInstance().getGbtobigs();
				if(fas.getDeclareType().equals(DelcareType.APPLICATION)){
					messageName=messageAction.exportMessage(new Request(CommonVars.getCurrUser()),fas,1,list)[0];
					messageAction.saveMessageQuery(new Request(CommonVars.getCurrUser()),MessageQuery.SENDTYPE,EdiType.EMS_EDI_H2K_CLEFT
							,DelcareType.APPLICATION,messageName,fas.getCopEmsNo(),"EMS214",0);
				}
				else if(fas.getDeclareType().equals(DelcareType.MODIFY)){
					messageName=messageAction.exportMessage(new Request(CommonVars.getCurrUser()),fas,2,list)[0];
					messageAction.saveMessageQuery(new Request(CommonVars.getCurrUser()),MessageQuery.SENDTYPE,EdiType.EMS_EDI_H2K_CLEFT
							,DelcareType.MODIFY,messageName,fas.getCopEmsNo(),"EMS224",0);
				}		
				fas.setDeclareState("2"); //审批状态 1 ，申请备案 2，等待审批 3，正在执行	
				fas.setDeclareDate(CommonVars.dateToStandDate(now));//申报日期
				fas.setDeclareTime(CommonVars.dateToTime(now));     //申报时间
				fas.setCheckMark("1");   //审批标志
				fas = manualDeclearAction.saveEmsHeadH2kFas(new Request(CommonVars.getCurrUser()), fas);
		        tableModel1.updateRow(fas);
		        CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsHeadH2kFas.this,
						"报文已经生成，位置在中间服务器的：" + messageName, "报文已生成", 1);
				setState();

			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsHeadH2kFas.this, "海关申报失败！"
						+ e.getMessage(), "提示", 2);
			}
		}
	}
		        
				
				
				
				
				
				
	/*private void chelonian() {
		String messageName = null;		
		Date now = new Date();
		fas = (EmsHeadH2kFas) tableModel1.getCurrentRow();
		List list = CustomBaseList.getInstance().getGbtobigs();
		if(fas.getDeclareType().equals(DelcareType.APPLICATION)){
			messageName=messageAction.exportMessage(new Request(CommonVars.getCurrUser()),fas,1,list)[0];
			messageAction.saveMessageQuery(new Request(CommonVars.getCurrUser()),MessageQuery.SENDTYPE,EdiType.EMS_EDI_H2K_CLEFT
					,DelcareType.APPLICATION,messageName,fas.getCopEmsNo(),"EMS214",0);
		}
		else if(fas.getDeclareType().equals(DelcareType.MODIFY)){
			messageName=messageAction.exportMessage(new Request(CommonVars.getCurrUser()),fas,2,list)[0];
			messageAction.saveMessageQuery(new Request(CommonVars.getCurrUser()),MessageQuery.SENDTYPE,EdiType.EMS_EDI_H2K_CLEFT
					,DelcareType.MODIFY,messageName,fas.getCopEmsNo(),"EMS224",0);
		}		
		fas.setDeclareState("2"); //审批状态 1 ，申请备案 2，等待审批 3，正在执行	
		fas.setDeclareDate(CommonVars.dateToStandDate(now));//申报日期
		fas.setDeclareTime(CommonVars.dateToTime(now));     //申报时间
		fas.setCheckMark("1");   //审批标志
		fas = manualDeclearAction.saveEmsHeadH2kFas(new Request(CommonVars.getCurrUser()), fas);
        tableModel1.updateRow(fas);
		JOptionPane.showMessageDialog(this,"报文已经生成，位置在中间服务器的："+messageName,"报文已生成",1);

	}
*/
	/**
	 * 
	 * This method initializes jButton5
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnProcess() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("回执处理");
			jButton5.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgRecvProcessMessage dgProcessMessage = new DgRecvProcessMessage();
					dgProcessMessage.setType("EmsHeadH2kFas");
					dgProcessMessage.setVisible(true);
					
					emsHeadH2k = (EmsHeadH2k) dataSource.get(0);
					List dataSource = manualDeclearAction.findEmsHeadH2kFas(new Request(
							CommonVars.getCurrUser()),emsHeadH2k.getEmsNo());
					initTable1(dataSource);
					setState();
				}
			});

		}
		return jButton5;
	}

	private void turning(EmsHeadH2k emsHeadH2k) {
		 if (emsHeadH2k.getDeclareType().equals(DelcareType.MODIFY)){
	    	List list = manualDeclearAction.findEmsHeadH2k(new Request(
					CommonVars.getCurrUser()));
	    	for (int i=0;i<list.size();i++){
	    		if (((EmsHeadH2k) list.get(i)).getDeclareType().equals(DelcareType.APPLICATION)){
	    			EmsHeadH2k emsHead = (EmsHeadH2k) list.get(i);
	    			emsHead.setHistoryState(new Boolean(true));  
	    			emsHead = manualDeclearAction
					.saveEmsHeadH2k(new Request(CommonVars
							.getCurrUser()), emsHead);
	    			tableModel.deleteRow(emsHead);
	    		}
	    	}
	    	emsHeadH2k.setDeclareType(DelcareType.APPLICATION);  	
	    }
		 emsHeadH2k.setDeclareState(DeclareState.PROCESS_EXE); //审批状态 
	}

	/**
	 * 
	 * This method initializes jButton6
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnExit() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setText("关闭");
			jButton6.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					FmEmsHeadH2kFas.this.dispose();

				}
			});

		}
		return jButton6;
	}

	/**
	 * 
	 * This method initializes jButton7
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setVisible(false);
			jButton7.setText("打印");
		}
		return jButton7;
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
	private void setState() {
		jButton1.setEnabled(isDeclareState());//修改
		jButton2.setEnabled(isDeclareState());//删除
		jButton3.setEnabled(isChangeState() && isPROCESS_EXE());//变更
		//jButton4.setEnabled(isDeclareState());//海关申报
		jButton4.setEnabled(!isPROCESS_EXE());
		jButton5.setEnabled((tableModel1.getCurrentRow() != null));//回执处理
	}

	private boolean isChangeState() { //只有一条记录
		List list = null;
		if (tableModel1.getCurrentRow() != null){
			fas = (EmsHeadH2kFas) tableModel1.getCurrentRow();
			list = manualDeclearAction.findEmsHeadH2kFasByCop(new Request(CommonVars
					.getCurrUser()),fas.getEmsHeadH2kNo(),fas.getCopEmsNo());
			if (list.size() == 1) {
				return true;
			}	
		}
		return false;
	}

	private boolean isDeclareState() {               //分册审批状态为：1
		if (tableModel1.getCurrentRow() != null
				&& !((EmsHeadH2kFas) tableModel1.getCurrentRow())
						.getDeclareState().equals(DeclareState.PROCESS_EXE)
						&& !((EmsHeadH2kFas) tableModel1.getCurrentRow())
						.getDeclareState().equals(DeclareState.WAIT_EAA)) {
			return true;
		}
		return false;
	}
	private boolean isPROCESS_EXE() {               //分册审批状态为：3
		if (tableModel1.getCurrentRow() != null
				&& ((EmsHeadH2kFas) tableModel1.getCurrentRow())
						.getDeclareState().equals(DeclareState.PROCESS_EXE)) {
			return true;
		}
		return false;
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
			jSplitPane.setDividerLocation(200);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setDividerSize(3);
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
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "请选择电子帐册 (注：帐册编号不可为空)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 14), new java.awt.Color(0,102,51)));
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
			jPanel1.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
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
			jScrollPane.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane;
	}

	/**

	 * This method initializes jTable1	

	 * 	

	 * @return javax.swing.JTable	

	 */    
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
			jTable1.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
			                if (tableModel1 == null)
			                	return;
			                if (tableModel1.getCurrentRow() == null)
			                	return;
			                 setState();
						}
					});
			jTable1.addMouseListener(new java.awt.event.MouseAdapter() { 
				public void mouseClicked(java.awt.event.MouseEvent e) {    

					if (e.getClickCount()==2){
						edit();
					}
				}
			});
			jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (tableModel1 == null)
	                	return;
	                if (tableModel1.getCurrentRow() == null)
	                	return;
	                if (!CommonVars.isManager()){
	                	return;
	                }
					if (e.isControlDown() == true && e.getKeyCode() == KeyEvent.VK_L) {
						EmsHeadH2kFas obj = (EmsHeadH2kFas) tableModel1.getCurrentRow();
						if (obj.getDeclareState().equals(DeclareState.APPLY_POR)){
							obj.setDeclareState(DeclareState.WAIT_EAA);
						} else if (obj.getDeclareState().equals(DeclareState.WAIT_EAA)){
							obj.setDeclareState(DeclareState.PROCESS_EXE);
						} else if (obj.getDeclareState().equals(DeclareState.PROCESS_EXE)){
							obj.setDeclareState(DeclareState.APPLY_POR);
						}
						obj = manualDeclearAction.saveEmsHeadH2kFas(new Request(CommonVars.getCurrUser()),obj);
						tableModel1.updateRow(obj);
					}
				}
			});
		}
		return jTable1;
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
			jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane1;
	}

	/**
	 * @return Returns the isChange1.
	 */
	public boolean isChange1() {
		return isChange1;
	}
	/**
	 * @param isChange1 The isChange1 to set.
	 */
	public void setChange1(boolean isChange1) {
		this.isChange1 = isChange1;
	}

	/**
	 * This method initializes jPrintButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJPrintButton() {
		if (jPrintButton == null) {
			jPrintButton = new JButton();
			jPrintButton.setText("打印");
			jPrintButton
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getPmPrintData().show(jPrintButton, 0,
							jPrintButton.getHeight());
					getPmPrintData().show(jPrintButton, 0,
							jPrintButton.getHeight());
					getPmPrintData().show(jPrintButton, 0,
							jPrintButton.getHeight());
				}
			});
		}
		return jPrintButton;
	}

	public JPopupMenu getPmPrintData() {
		if(pmPrintData == null){
		   pmPrintData = new JPopupMenu();
		   pmPrintData.add(getPrintAll());
		   pmPrintData.add(getPrintImport());
		   pmPrintData.add(getPrintOut());
		}
		return pmPrintData;
	}

	public JMenuItem getPrintAll() {
		if (printAll == null) {
			printAll = new JMenuItem();
			printAll.setText("打印完整电子分册");
			printAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					EmsHeadH2kFas head = (EmsHeadH2kFas)tableModel1.getCurrentRow();
					if (tableModel1.getCurrentRow() == null ){
						JOptionPane.showMessageDialog(FmEmsHeadH2kFas.this,
								"电子分册为空，请先申请备案", "提示！", 0);
	                	return;
					}
					try {
						//表头	
						List headAll = manualDeclearAction.findEmsHeadFasAll(new Request(CommonVars.getCurrUser()),head);		
						List headImgd = manualDeclearAction.findEmsHeadFasAllImgd(new Request(CommonVars.getCurrUser()),head);
						CustomReportDataSource emsMaterielDs = new CustomReportDataSource(headAll);						
						CustomReportDataSource emsLjDs = new CustomReportDataSource(headImgd);
						
						InputStream masterReportStream = FmEmsHeadH2kFas.class
							    .getResourceAsStream("report/EmsHeadH2kFasAll.jasper");
						InputStream detailImgReportStream = FmEmsHeadH2kFas.class
			                    .getResourceAsStream("report/EmsHeadH2kFasImdg.jasper");
						JasperReport detailReport = (JasperReport)JRLoader.loadObject(detailImgReportStream);
						
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("headAllDS",emsMaterielDs);
						parameters.put("headImgdDS",emsLjDs);
						parameters.put("detailReport",detailReport);
					
						JasperPrint masterJasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, emsMaterielDs);							
						
						
						//表体成品
						List headExgd = manualDeclearAction.findEmsHeadFasAllExgd(new Request(CommonVars.getCurrUser()),head);
						CustomReportDataSource emsProductDs = new CustomReportDataSource(headExgd);
						InputStream detailExgReportStream = FmEmsHeadH2kFas.class
		                         .getResourceAsStream("report/EmsHeadH2kFasExdg.jasper");
						Map<String, Object> parameters2 = new HashMap<String, Object>();
						parameters2.put("beforePageCount", masterJasperPrint.getPages().size());
						JasperPrint emsProductJasperPrint = JasperFillManager.fillReport(
								detailExgReportStream, parameters2, emsProductDs);	
						int size1 = emsProductJasperPrint.getPages().size();
						for (int i = 0; i < size1; i++) {
							masterJasperPrint.addPage((JRPrintPage) emsProductJasperPrint
									.getPages().get(i));
						}						
						//
						// 显示所有报表
						//
						DgReportViewer viewer = new DgReportViewer(masterJasperPrint);
						viewer.setVisible(true);					
						
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return printAll;
	}
	
	

	public JMenuItem getPrintImport() {
		if (printImport == null) {
			printImport = new JMenuItem();
			printImport.setText("打印进口料件备案变更清单");
			printImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgEmsHeadH2kFasDeclare dg = new DgEmsHeadH2kFasDeclare();
					dg.setTableModel(tableModel1);
					dg.setVisible(true);
				}
			});
		}
		return printImport;
	}

	public JMenuItem getPrintOut() {
		if (printOut == null) {
			printOut = new JMenuItem();
			printOut.setText("打印出口成品备案变更清单");
			printOut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgEmsHeadH2kFasDeclare dg = new DgEmsHeadH2kFasDeclare();
					dg.setTableModel(tableModel1);
					dg.setVisible(true);
				}
			});
		}
		return printOut;
	}
       }