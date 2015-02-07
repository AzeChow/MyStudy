/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.otherreport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.TableColumnModel;

import com.bestway.bcus.cas.action.CasAction;
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
import com.bestway.common.ProgressInfo;
import com.bestway.common.ProjectTypeParam;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.ui.render.TableMultiRowRender;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JFrameBase;
import com.bestway.ui.winuicontrol.JFrameBaseHelper;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.InputStream;

import javax.swing.JRadioButton;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmBalanceInfo2 extends JDialogBase {

	private JPanel jPanel = null;

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

	private JButton btnPrint = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbEndDate = null;

	private JCheckBox cbBcus = null;

	private JCheckBox cbDzsc = null;

	private JCheckBox cbBcs = null;

	private JPanel jPanel1 = null;

	private JToolBar jJToolBarBar = null;

	private JButton btnExcelImport = null;

	private JRadioButton rbInBill = null;

	private JRadioButton rbInImport = null;
	private List result = null;
	/**
	 * This method initializes
	 * 
	 */
	private ButtonGroup group = new ButtonGroup();
	public FmBalanceInfo2() {
		super();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initialize();
		initUIComponents();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		group.add(rbInBill);
		group.add(rbInImport);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(733, 541);
		this.setContentPane(getJContentPane());
		this.setTitle("短溢表");

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

		result = this.casAction.findBalanceInfo(new Request(CommonVars
				.getCurrUser()));
		initTable(result);
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
			jPanel = new JPanel();
			jPanel.setLayout(flowLayout);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getBtnExcelImport(), null);
			jPanel.add(getBtnSearch(), null);
			jPanel.add(getBtnPrint(), null);
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
			cbbBeginDate.setVisible(false);
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
									FmBalanceInfo2.this, btnReloadSearch
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
					FmBalanceInfo2.this.dispose();
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
		JTableListModelAdapter adapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("料件/规格/单位", "key", 150));
				list.add(addColumn("1.库存原材料", "f1", 100));
				list.add(addColumn("2.半成品、成品折原料", "f2", 120));
				list.add(addColumn("3.库存原材料合计", "f3", 120));
				list.add(addColumn("1A.库存原料", "f1A", 100));
				list.add(addColumn("2A.半成品折原料", "f2A", 120));
				list.add(addColumn("3A.成品折原料", "f3A", 100));
				list.add(addColumn("4.库存原材料合计 = 1A+2A+3A-3", "f4", 120));
				list.add(addColumn("5.已收货未转厂数", "f5", 100));
				list.add(addColumn("6.(内购)实际进料", "f5A", 100));
				list.add(addColumn("7.(内购)已结案合同发票核销数", "f5B", 100));
				list.add(addColumn("8.已转厂未收货数", "f6", 100));
				list.add(addColumn("9.已送货未转厂数", "f7", 100));
				list.add(addColumn("10.已转厂未送货数", "f8", 100));
				list.add(addColumn("11.(内购)成品出货内购耗料", "f9", 100));
				list.add(addColumn("12.合同库存数(监管) = f5+f6+f7-f8-f9", "f10", 200));
				list.add(addColumn("13.合同进口数", "f11", 100));
				list.add(addColumn("14.合同出口耗料", "f12", 100));
				list.add(addColumn("15.合同结余数 = f13-f14", "f13", 180));
				list.add(addColumn("16.短(-)溢(+) = f10-f13", "f14", 180));
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, adapter);

		TableColumnModel cm = jTable.getColumnModel();
		ColumnGroup g1 = new ColumnGroup("盘点库存");

		ColumnGroup g11 = new ColumnGroup("内购");
		g11.add(cm.getColumn(2));
		g11.add(cm.getColumn(3));
		g11.add(cm.getColumn(4));
		ColumnGroup g12 = new ColumnGroup("监管");
		g12.add(cm.getColumn(5));
		g12.add(cm.getColumn(6));
		g12.add(cm.getColumn(7));
		g12.add(cm.getColumn(8));
		g1.add(g11);
		g1.add(g12);
		
		ColumnGroup g2 = new ColumnGroup("转厂情况");
		ColumnGroup g21 = new ColumnGroup("原料");
		g21.add(cm.getColumn(9));
		g21.add(cm.getColumn(10));
		g21.add(cm.getColumn(11));
		g21.add(cm.getColumn(12));
		ColumnGroup g22 = new ColumnGroup("成品折原料");
		g22.add(cm.getColumn(13));
		g22.add(cm.getColumn(14));
		g22.add(cm.getColumn(15));
		ColumnGroup g23 = new ColumnGroup("合同库存");
		g23.add(cm.getColumn(16));
		g2.add(g21);
		g2.add(g22);
		g2.add(g23);

		ColumnGroup g3 = new ColumnGroup("合同执行情况(监管)");
		g3.add(cm.getColumn(17));
		g3.add(cm.getColumn(18));
		g3.add(cm.getColumn(19));
		ColumnGroup g4 = new ColumnGroup("短溢情况(监管)");
		g4.add(cm.getColumn(20));
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(g1);
		header.addColumnGroup(g2);
		header.addColumnGroup(g3);
		header.addColumnGroup(g4);
		
		int fraction = 2;
		List<JTableListColumn> de = tableModel.getColumns();
		de.get(2).setFractionCount(fraction);
		de.get(3).setFractionCount(fraction);
		de.get(4).setFractionCount(fraction);
		de.get(5).setFractionCount(fraction);
		de.get(6).setFractionCount(fraction);
		de.get(7).setFractionCount(fraction);
		de.get(8).setFractionCount(fraction);
		de.get(9).setFractionCount(fraction);
		de.get(10).setFractionCount(fraction);
		de.get(11).setFractionCount(fraction);
		de.get(12).setFractionCount(fraction);//合同进口总数
		de.get(13).setFractionCount(fraction);//耗用
		de.get(14).setFractionCount(fraction);
		de.get(15).setFractionCount(fraction);
		de.get(16).setFractionCount(fraction);
		de.get(17).setFractionCount(fraction);
		de.get(18).setFractionCount(fraction);
		de.get(19).setFractionCount(fraction);
		de.get(20).setFractionCount(fraction);
		
		//第六栏，第八栏数据有数据体现一般情况下试不允许的，用红色提醒用户
		jTable.getColumnModel().getColumn(8).setCellRenderer(
				new TableMultiRowRender() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						Component c = super
								.getTableCellRendererComponent(table, value,
										isSelected, hasFocus, row, column);
						c.setForeground(Color.RED);
						return c;
					}
				});

		jTable.getColumnModel().getColumn(10).setCellRenderer(
				new TableMultiRowRender() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						Component c = super
								.getTableCellRendererComponent(table, value,
										isSelected, hasFocus, row, column);
						c.setForeground(Color.RED);
						return c;
					}
				});
		
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
				List infos = new ArrayList();
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
				CommonProgress.showProgressDialog(flag, FmBalanceInfo2.this,
						false, progressTask, 5000);
				CommonProgress.setMessage(flag, "系统正在重新获取资料，请稍后...");
				Date beginDate = cbbBeginDate.getDate();
				Date endDate = cbbEndDate.getDate();
//				if (beginDate != null && endDate != null) {
//					if (beginDate.before(endDate)) {
						boolean isFromCheckData  = false;
						if (rbInImport.isSelected()){
							isFromCheckData = true;
						}
						infos = casAction.findBalanceInfo2(new Request(
								CommonVars.getCurrUser()), null, endDate,
								getProjectTypeParam(),flag, true, isFromCheckData);
//					}
//				}
				System.out.println("infos size = " + infos.size());
//				tableModel.setList(infos);
				initTable(infos);
				CommonProgress.closeProgressDialog(flag);
				info += " 任务完成,共用时:" + (System.currentTimeMillis() - beginTime)
						+ " 毫秒 ";
				JOptionPane
						.showMessageDialog(FmBalanceInfo2.this, info, "提示", 2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmBalanceInfo2.this, "重新获取失败！！！"
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
	 * This method initializes btnHelp
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			UUID uuid = UUID.randomUUID();
			btnPrint.setActionCommand(uuid.toString());
			btnPrint.setPreferredSize(new java.awt.Dimension(60, 26));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (result != null && !result.isEmpty()) {

						CommonDataSource imgExgDS = new CommonDataSource(result);
						List<BaseCompany> company = new ArrayList<BaseCompany>();
						company.add(CommonVars.getCurrUser().getCompany());
						CommonDataSource companyDS = new CommonDataSource(
								company);

						InputStream masterReportStream = FmBalanceInfo.class
								.getResourceAsStream("report/BalanceInfo2Report.jasper");
						InputStream detailReportStream = FmBalanceInfo.class
								.getResourceAsStream("report/BalanceInfo2SubbReport.jasper");
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
		return btnPrint;
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
			jJToolBarBar.add(getRbInBill());
			jJToolBarBar.add(getRbInImport());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes btnExcelImport	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnExcelImport() {
		if (btnExcelImport == null) {
			btnExcelImport = new JButton();
			btnExcelImport.setText("导入盘点数据");
			btnExcelImport.addActionListener(new java.awt.event.ActionListener(){

				public void actionPerformed(ActionEvent e) {
					DgBaLanceInfoImportFromFile dg = new DgBaLanceInfoImportFromFile(FmBalanceInfo2.this,false);
					dg.setTableModel(tableModel);
					dg.setVisible(true);
				}
				
			});			
		}
		return btnExcelImport;
	}

	/**
	 * This method initializes rbInBill	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbInBill() {
		if (rbInBill == null) {
			rbInBill = new JRadioButton();
			rbInBill.setText("库存数据来源于工厂单据");
		}
		return rbInBill;
	}

	/**
	 * This method initializes rbInImport	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbInImport() {
		if (rbInImport == null) {
			rbInImport = new JRadioButton();
			rbInImport.setText("库存数据来源于实际盘点库存");
		}
		return rbInImport;
	}

}
