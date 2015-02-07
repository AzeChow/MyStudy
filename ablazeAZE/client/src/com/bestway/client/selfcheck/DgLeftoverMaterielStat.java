/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.selfcheck;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.action.CasCheckAction;
import com.bestway.bcus.cas.entity.LeftoverMaterielBalanceInfo;
import com.bestway.bcus.cas.parameterset.entity.OtherOption;
import com.bestway.bcus.client.cas.otherreport.FmBalanceInfo;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSourceNew;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ProgressTask;
import com.bestway.bcus.client.common.TableTextFieldEditor;
import com.bestway.bcus.client.common.TableTextFieldEditorEvent;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.common.ProgressInfo;
import com.bestway.common.ProjectTypeParam;
import com.bestway.common.Request;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Dimension;
import java.io.InputStream;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgLeftoverMaterielStat extends JDialogBase {

	private JPanel				jPanel			= null;
	private JLabel				jLabel			= null;
	private JCalendarComboBox	cbbBeginDate	= null;
	private JButton				btnReloadSearch	= null;
	private JButton				btnClose		= null;
	private JPanel				jPanel2			= null;
	private JTable				jTable			= null;
	private JScrollPane			jScrollPane		= null;
	private JTableListModel		tableModel		= null;
	
	private CasAction			casAction		= null;
	private CasCheckAction		casCheckAction  = null;
	
	private JPanel				jContentPane	= null;
	private JToolBar			jToolBar		= null;
	private JLabel				jLabel1			= null;
	private JCalendarComboBox	cbbEndDate		= null;
	private JCheckBox			cbBcus			= null;
	private JCheckBox			cbBcs			= null;
	private JCheckBox			cbDzsc			= null;
	
	private Boolean         isCancelBefore      = true;  //  @jve:decl-index=0:
	
	private OtherOption otherOption = null; // @jve:decl-index=0:


	/**
	 * This method initializes
	 * 
	 */
	public DgLeftoverMaterielStat() {
		super();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		
		casCheckAction = (CasCheckAction) CommonVars.getApplicationContext().getBean(
				"casCheckAction");
		
		otherOption = CasCommonVars.getOtherOption();
		
		initialize();
		initUIComponents();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(781, 541);
		this.setContentPane(getJContentPane());
		this.setTitle("边角料对比表");
		group = new ButtonGroup();
		group.add(rbCancelBefore);
		group.add(rbCancelAfter);
	}

	private void initUIComponents() {

		int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
				.getYearInt();
		Calendar beginClarendar = Calendar.getInstance();
		beginClarendar.set(Calendar.YEAR, year);
		beginClarendar.set(Calendar.MONTH, 0);
		beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
		this.cbbBeginDate.setDate(beginClarendar.getTime());
		this.cbbBeginDate.setCalendar(beginClarendar);

		Calendar endClarendar = Calendar.getInstance();
		endClarendar.set(Calendar.YEAR, year);
		endClarendar.set(Calendar.MONTH, 11);
		endClarendar.set(Calendar.DAY_OF_MONTH, 31);
		this.cbbEndDate.setDate(endClarendar.getTime());
		this.cbbEndDate.setCalendar(endClarendar);
		
		//保留上次查询时的条件
		cbbBeginDate.setDate(otherOption.getLeftOverBeginDate() == null ? beginClarendar.getTime()
									: otherOption.getLeftOverBeginDate());
		cbbEndDate.setDate(otherOption.getLeftOverEndDate() == null ? endClarendar.getTime()
									: otherOption.getLeftOverEndDate());
		cbBcs.setSelected(otherOption.getLeftOverIsBcs() == null ? false:otherOption.getLeftOverIsBcs());
		cbBcus.setSelected(otherOption.getLeftOverIsBcus() == null ? false:otherOption.getLeftOverIsBcus());
		cbDzsc.setSelected(otherOption.getLeftOverIsDzsc() == null ? false:otherOption.getLeftOverIsDzsc());
		rbCancelBefore.setSelected(otherOption.getLeftOverIsCancelBefore() == null ? true
									:otherOption.getLeftOverIsCancelBefore());
		rbCancelAfter.setSelected(!rbCancelBefore.isSelected());
		
		cbBcus.setEnabled(rbCancelAfter.isSelected());
		
		List dataSource = this.casCheckAction
									.findLeftoverMaterielStatInfo(new Request(CommonVars
											.getCurrUser(), true));
		
		initTable(dataSource);
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout1.setVgap(1);
			jLabel1 = new JLabel();
			jLabel1.setText("结束日期");
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(flowLayout1);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel.setText("开始日期");
			jPanel.add(jLabel, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getCbBcus(), null);
			jPanel.add(getCbBcs(), null);
			jPanel.add(getCbDzsc(), null);
			jPanel.add(getBtnSearch(), null);
			jPanel.add(getBtnClose(), null);
			jPanel.add(getBtnPrint(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes endDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setPreferredSize(new java.awt.Dimension(85, 22));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnReloadSearch == null) {
			btnReloadSearch = new JButton();
			btnReloadSearch.setText("报表计算");
			btnReloadSearch.setPreferredSize(new java.awt.Dimension(85, 26));
			btnReloadSearch
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							
							if (JOptionPane.showConfirmDialog(
									DgLeftoverMaterielStat.this,
									btnReloadSearch.getText() + "\n确定要继续吗？？",
									"警告!!!", JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
								
								
								//必须选择手册类型
								if(cbBcus.isSelected() == false 
										&& cbBcs.isSelected() == false
											&& cbDzsc.isSelected() == false){
									JOptionPane.showMessageDialog(DgLeftoverMaterielStat.this,
											"您须选择一种或多种手册类型！", "注意啦！！！", 0);
									return;
								}
								new ReloadSearchThread().start();
							}

						}
					});
		}
		return btnReloadSearch;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setPreferredSize(new java.awt.Dimension(60, 26));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgLeftoverMaterielStat.this.dispose();
				}
			});
		}
		return btnClose;
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
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new GroupableHeaderTable();
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
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel2(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJPanel());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setPreferredSize(new java.awt.Dimension(85, 22));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes cbBcus
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbBcus() {
		if (cbBcus == null) {
			cbBcus = new JCheckBox();
//			cbBcus.setSelected(true);
			cbBcus.setText("电子帐册");
			cbBcus.setEnabled(false);
		}
		return cbBcus;
	}

	/**
	 * This method initializes cbBcs
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbBcs() {
		if (cbBcs == null) {
			cbBcs = new JCheckBox();
//			cbBcs.setSelected(true);
			cbBcs.setText("电子化手册");
		}
		return cbBcs;
	}

	/**
	 * This method initializes cbDzsc
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbDzsc() {
		if (cbDzsc == null) {
			cbDzsc = new JCheckBox();
//			cbDzsc.setSelected(true);
			cbDzsc.setText("电子手册");
		}
		return cbDzsc;
	}

	private void initTable(final List list) {
		JTableListModelAdapter adapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("商品编码", "complex", 100));
				list.add(addColumn("料件名称/规格/单位", "key", 180));
				list.add(addColumn("1.边角料入库量", "f1", 100));
				list.add(addColumn("2.边角料出库量", "f2", 120));
				list.add(addColumn("3.实际库存量=1-2", "f3", 100));
				
				if(isCancelBefore){
					list.add(addColumn("4.成品出货折损耗", "f4", 180));
				}
				
				
				list.add(addColumn("5.已打税", "f5", 180));
				list.add(addColumn("6.差额 (5-2)", "f6", 100));
				
				if(isCancelBefore){
					list.add(addColumn("7.差额 (4+5-2)", "f7", 100));
				}
				
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, adapter,
				ListSelectionModel.SINGLE_SELECTION);
//		//
//		// 设置列的修改和修改 enter key event
//		//
//		List<Integer> editColumns = new ArrayList<Integer>();
//		for (int i = 9; i < 12; i++) {
//			editColumns.add(i);
//			jTable.getColumnModel().getColumn(i).setCellEditor(
//					new TableTextFieldEditor(new JTextField(), event));
//		}
//		adapter.setEditableColumn(editColumns);

	}

	/**
	 * cellEditor 回车事件
	 */
//	private TableTextFieldEditorEvent	event					= new TableTextFieldEditorEvent() {
//																	public Object saveObject(
//																			Object obj) {
//																		return casAction
//																				.saveLeftoverMaterielBalanceInfo(
//																						new Request(
//																								CommonVars
//																										.getCurrUser(),
//																								true),
//																						(LeftoverMaterielBalanceInfo) obj);  //  @jve:decl-index=0:
//
//																	}
//																};
																
																
	private JPanel						jPanel1					= null;
	private JToolBar					jJToolBarBar			= null;
	private JRadioButton rbCancelBefore = null;
	private JRadioButton rbCancelAfter = null;
	private ButtonGroup group = null;  //  @jve:decl-index=0:
	private JButton btnPrint = null;

	/**
	 * 生成单据的折算报关数量
	 * 
	 * @author Administrator
	 * 
	 */
	class ReloadSearchThread extends Thread {
		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {
				btnReloadSearch.setEnabled(false);
				List infos = new ArrayList();
				String info = "";
				long beginTime = System.currentTimeMillis();
				ProgressTask progressTask = new ProgressTask() {
					protected void setClientTipMessage() {
						ToolsAction toolsAction = (ToolsAction) CommonVars
								.getApplicationContext().getBean("toolsAction");
						ProgressInfo progressInfo = toolsAction
								.getProgressInfo(flag);
						if (progressInfo != null && progressInfo.getHintMessage() != null) {
							CommonProgress.setMessage(flag, progressInfo
									.getHintMessage());
						}
					}
				};
				CommonProgress.showProgressDialog(flag,
						DgLeftoverMaterielStat.this, false, progressTask, 5000);
				CommonProgress.setMessage(flag, "系统正在重新获取资料，请稍后...");
				Date beginDate = cbbBeginDate.getDate();
				Date endDate = cbbEndDate.getDate();
				if (beginDate != null && endDate != null) {
					if (beginDate.before(endDate)) {
						
						infos = casCheckAction.findCalLeftoverMateriel(new Request(
								CommonVars.getCurrUser()), beginDate, endDate,
								getProjectTypeParam(),
								flag,isCancelBefore);
					}
				}
//				System.out.println("infos size = " + infos.size());
				
				initTable(infos);
				
				
				//保存查询条件
				saveSearchCondtion();
				
				CommonProgress.closeProgressDialog(flag);
				info += " 任务完成,共用时:" + (System.currentTimeMillis() - beginTime)
						+ " 毫秒 ";
				JOptionPane.showMessageDialog(DgLeftoverMaterielStat.this,
						info, "提示", 2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgLeftoverMaterielStat.this,
						"重新获取失败！！！" + e.getMessage(), "提示", 2);
			} finally {
				ToolsAction toolsAction = (ToolsAction) CommonVars
						.getApplicationContext().getBean("toolsAction");
				toolsAction.removeProgressInfo(flag);
			}
			btnReloadSearch.setEnabled(true);
		}
	}

	private ProjectTypeParam getProjectTypeParam() {
		ProjectTypeParam projectTypeParam = new ProjectTypeParam();
		projectTypeParam.setIsBcs(this.cbBcs.isSelected());
		projectTypeParam.setIsBcus(this.cbBcus.isSelected());
		projectTypeParam.setIsDzsc(this.cbDzsc.isSelected());
		return projectTypeParam;
	}
	
	/**
	 * 保存查询条件
	 * @author wss
	 */
	private void saveSearchCondtion(){
		otherOption.setLeftOverBeginDate(cbbBeginDate.getDate());
		otherOption.setLeftOverEndDate(cbbEndDate.getDate());
		otherOption.setLeftOverIsBcs(cbBcs.isSelected());
		otherOption.setLeftOverIsBcus(cbBcus.isSelected());
		otherOption.setLeftOverIsDzsc(cbDzsc.isSelected());
		otherOption.setLeftOverIsCancelBefore(rbCancelBefore.isSelected());
		
		otherOption = casAction.saveOtherOption(new Request(CommonVars
				.getCurrUser()), otherOption);
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
			jPanel1.add(getJJToolBarBar(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getRbCancelBefore());
			jJToolBarBar.add(getRbCancelAfter());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes rbCancelBefore	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbCancelBefore() {
		if (rbCancelBefore == null) {
			rbCancelBefore = new JRadioButton();
			rbCancelBefore.setText("核销前打税");
//			rbCancelBefore.setSelected(true);
			rbCancelBefore.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isCancelBefore = true;
					cbBcus.setSelected(false);
					cbBcus.setEnabled(false);
				}
			});
		}
		return rbCancelBefore;
	}

	/**
	 * This method initializes rbCancelAfter	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbCancelAfter() {
		if (rbCancelAfter == null) {
			rbCancelAfter = new JRadioButton();
			rbCancelAfter.setText("核销后打税");
			rbCancelAfter.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isCancelBefore = false;
					cbBcus.setEnabled(true);
				}
			});
		}
		return rbCancelAfter;
	}

	/**
	 * This method initializes btnPrint	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					print();
				}
			});
		}
		return btnPrint;
	}
	
	/**
	 * 打印
	 */
	private void print(){

		try {
			List list = tableModel.getList();
			CustomReportDataSourceNew ds = new CustomReportDataSourceNew(
					list);
	
			//核销前打税
			String str = "report/LeftOverStatCancelBefore.jasper";
			//核销后打税
			if(!isCancelBefore){
				str = "report/LeftOverStatCancelAfter.jasper";
			}
			InputStream masterReportStream = DgLeftoverMaterielStat.class
					.getResourceAsStream(str);
			
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("title", getTitle());
//			parameters.put("type", getMaterielType());
			
			parameters.put("beginDate",cbbBeginDate.getDate());
			parameters.put("endDate",cbbEndDate.getDate());
			JasperPrint jasperPrint = JasperFillManager
					.fillReport(masterReportStream,
							parameters, ds);
			DgReportViewer viewer = new DgReportViewer(
					jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
