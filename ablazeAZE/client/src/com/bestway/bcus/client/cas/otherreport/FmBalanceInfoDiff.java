/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.otherreport;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.BalanceInfo;
import com.bestway.bcus.cas.entity.BalanceInfo2;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ProgressTask;
import com.bestway.client.common.CommonVariables;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.CommonUtils;
import com.bestway.common.ProgressInfo;
import com.bestway.common.ProjectTypeParam;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JFrameBase;
import com.bestway.ui.winuicontrol.JFrameBaseHelper;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Dimension;
import java.io.InputStream;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmBalanceInfoDiff extends JDialogBase {

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JButton btnReloadSearch = null;

	private JButton btnClose = null;

	private JPanel jPanel2 = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JTableListModel tableModel = null;

	private CasAction casAction = null;

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbEndDate = null;

	private JCheckBox cbBcus = null;

	private JCheckBox cbDzsc = null;

	private JCheckBox cbBcs = null;

	private JPanel jPanel1 = null;

	private JToolBar jJToolBarBar = null;
	
	private Integer 		   maximumFractionDigits 	= CasCommonVars.getOtherOption()
	.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
	.getOtherOption().getInOutMaximumFractionDigits();

	private JButton btnPirnt = null;

	private List result = null;
	/**
	 * This method initializes
	 * 
	 */
	public FmBalanceInfoDiff() {
		super();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
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
		this.setSize(733, 541);
		this.setContentPane(getJContentPane());
		this.setTitle("短溢差额表");

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

//		List list = this.casAction.findBalanceInfo(new Request(CommonVars
//				.getCurrUser()));
		initTable(new ArrayList());
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout.setHgap(6);
			flowLayout.setVgap(2);
			jLabel1 = new JLabel();
			jLabel1.setText("结束日期");
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(flowLayout);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel.setText("开始日期");
			jPanel.add(jLabel, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getBtnSearch(), null);
			jPanel.add(getBtnPirnt(), null);
			jPanel.add(getBtnClose(), null);
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
			cbbBeginDate.setPreferredSize(new Dimension(90, 25));
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
			btnReloadSearch.setPreferredSize(new java.awt.Dimension(86, 26));
			btnReloadSearch
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (JOptionPane.showConfirmDialog(
									FmBalanceInfoDiff.this, btnReloadSearch
											.getText()
											+ "\n确定要继续吗？？", "警告!!!",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
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
					FmBalanceInfoDiff.this.dispose();
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

	private void initTable(final List list) {
		JTableListModelAdapter adapter = new JTableListModelAdapter(maximumFractionDigits) {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("料件/规格/单位", "key", 150));
				list.add(addColumn("1.帐面库存数", "f1", 200));
				list.add(addColumn("2.盘点库存数", "f2", 100));
				list.add(addColumn("3.差(-)额(+) = f1-f12", "f3", 180));
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, adapter);
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
				List<BalanceInfo2> infos = new ArrayList();
				String info = "";
				long beginTime = System.currentTimeMillis();
				ProgressTask progressTask = new ProgressTask() {
					protected void setClientTipMessage() {
						ToolsAction toolsAction = (ToolsAction) CommonVars
								.getApplicationContext().getBean("toolsAction");
						ProgressInfo progressInfo = toolsAction
								.getProgressInfo(flag);
						if (progressInfo != null
								&& progressInfo.getHintMessage() != null) {
							CommonProgress.setMessage(flag, progressInfo
									.getHintMessage());
						}
					}
				};
				CommonProgress.showProgressDialog(flag, FmBalanceInfoDiff.this,
						false, progressTask, 5000);
				CommonProgress.setMessage(flag, "系统正在重新获取资料，请稍后...");
				Date beginDate = cbbBeginDate.getDate();
				Date endDate = cbbEndDate.getDate();
				if (beginDate != null && endDate != null) {
					if (beginDate.before(endDate)) {
						CommonProgress.setMessage(flag, "开始计算来源于盘点数据的短溢表... !!");
						List<BalanceInfo> fromCheckInfos = casAction.findBalanceInfo(new Request(
								CommonVars.getCurrUser()), beginDate, endDate,
								getProjectTypeParam(), flag, true);
						CommonProgress.setMessage(flag, "开始计算来源于单据数据的短溢表... !!");
						List<BalanceInfo> fromBillInfos = casAction.findBalanceInfo(new Request(
								CommonVars.getCurrUser()), beginDate, endDate,
								getProjectTypeParam(), flag, false);
						for(BalanceInfo item : fromBillInfos){
							BalanceInfo2 tmp = new BalanceInfo2();
							tmp.setName(item.getName());
							tmp.setSpec(item.getSpec());
							tmp.setUnitName(item.getUnitName());
							tmp.setF1(item.getF5());
							infos.add(tmp);
						}
						for(int i = 0;i<fromCheckInfos.size();i++){
							BalanceInfo tmp = fromCheckInfos.get(i);
							BalanceInfo2 returnTmp = infos.get(i);
							returnTmp.setF2(tmp.getF5());
							returnTmp.setF3(CommonUtils.getDoubleExceptNull(returnTmp.getF1())
									-CommonUtils.getDoubleExceptNull(returnTmp.getF2()));
						}
//						infos = casAction.findBalanceInfo2(new Request(
//								CommonVars.getCurrUser()), beginDate, endDate,
//								getProjectTypeParam(), flag,false,false);
					}
				}
				System.out.println("infos size = " + infos.size());
//				tableModel.setList(infos);
				initTable(infos);
				CommonProgress.closeProgressDialog(flag);
				info += " 任务完成,共用时:" + (System.currentTimeMillis() - beginTime)
						+ " 毫秒 ";
				JOptionPane
						.showMessageDialog(FmBalanceInfoDiff.this, info, "提示", 2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmBalanceInfoDiff.this, "重新获取失败！！！"
						+ e.getMessage(), "提示", 2);
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
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setPreferredSize(new Dimension(90, 25));
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
			cbBcus.setSelected(true);
			cbBcus.setText("电子帐册");
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
			cbBcs.setSelected(true);
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
			cbDzsc.setSelected(true);
			cbDzsc.setText("电子手册");
		}
		return cbDzsc;
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
			jJToolBarBar.add(getCbBcus());
			jJToolBarBar.add(getCbBcs());
			jJToolBarBar.add(getCbDzsc());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes btnPirnt	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPirnt() {
		if (btnPirnt == null) {
			btnPirnt = new JButton();
			btnPirnt.setPreferredSize(new Dimension(60, 26));
			btnPirnt.setText("打印");
			btnPirnt.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getList() != null && !tableModel.getList().isEmpty()) {
						result = tableModel.getList();
						CommonDataSource imgExgDS = new CommonDataSource(result);
						List<BaseCompany> company = new ArrayList<BaseCompany>();
						company.add(CommonVars.getCurrUser().getCompany());
						CommonDataSource companyDS = new CommonDataSource(
								company);

						InputStream masterReportStream = FmBalanceInfo.class
								.getResourceAsStream("report/BalanceInfoDiffReport.jasper");
						InputStream detailReportStream = FmBalanceInfo.class
								.getResourceAsStream("report/BalanceInfoDiffSubbReport.jasper");
						try {
							JasperReport detailReport = (JasperReport) JRLoader
									.loadObject(detailReportStream);

							Map<String, Object> parameters = new HashMap<String, Object>();
							parameters.put("imgExgDS", imgExgDS);
							parameters.put("detailReport", detailReport);
							JasperPrint jasperPrint;
							jasperPrint = JasperFillManager.fillReport(
									masterReportStream, parameters, companyDS);
							DgReportViewer viewer = new DgReportViewer(
									jasperPrint);
							viewer.setVisible(true);
						} catch (JRException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
		}
		return btnPirnt;
	}

}
