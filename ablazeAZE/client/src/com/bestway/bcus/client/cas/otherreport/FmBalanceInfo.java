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
import java.io.InputStream;
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
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumnModel;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.BalanceInfo;
import com.bestway.bcus.cas.entity.CheckBalance;
import com.bestway.bcus.cas.entity.ImgOrgUseInfo;
import com.bestway.bcus.cas.parameterset.entity.OtherOption;
import com.bestway.bcus.client.cas.DgFactoryQuery;
import com.bestway.bcus.client.cas.DgImgOrgUseInfo;
import com.bestway.bcus.client.cas.DgImgOrgUseMiddleInfo;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ProgressTask;
import com.bestway.client.common.CommonVariables;
import com.bestway.client.selfcheck.DgClientConvertDetailInfo;
import com.bestway.client.selfcheck.DgCurrentThatDayBalanceToBom;
import com.bestway.client.selfcheck.DgMaterialThatDayBalance;
import com.bestway.client.selfcheck.DgMaterialThatDayBalanceToBom;
import com.bestway.client.selfcheck.DgSupplierBalanceDetailInfo;
import com.bestway.client.selfcheck.FmContractExecStat;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.MaterielType;
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
import javax.swing.JRadioButton;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * @author ls
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class FmBalanceInfo extends JDialogBase {
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
	private JRadioButton rbInBill = null;
	private JRadioButton rbInImport = null;
	private List result = null;
	/**
	 * This method initializes
	 * 
	 */
	private ButtonGroup group = new ButtonGroup();
	private JButton btnDeliveryToPlant = null;
	private JButton btnRelation = null;

	// 关联查询时传递 的参数
	private Date sDate;
	private String sHsCode;
	private String sHsName;
	private String sHsSpec;
	private String sPtNo;
	private String sPtName;
	private String sPtSpec;

	// 是否来自盘点
	private boolean isFromCheckData = false;

	// private String kcType;//库存类型

	private JPopupMenu pmRelation = null; // @jve:decl-index=0:visual-constraint="779,109"

	private JMenuItem miThatDayBalanceMateriel = null; // @jve:decl-index=0:visual-constraint="788,171"

	private JMenuItem miThatDayBalanceCurrent = null;

	private JMenuItem miCurrentConvert = null;

	private JMenuItem miThatDayBalanceProduct = null;

	private JMenuItem miThatDayBalanceOutSource = null;

	private JMenuItem miProductConvert = null; // @jve:decl-index=0:visual-constraint="788,221"

	private JMenuItem miBalanceDetailMateriel = null;

	private JMenuItem miBalanceDetail = null; // @jve:decl-index=0:visual-constraint="787,273"

	private JMenuItem miTransferConvertDetail = null; // @jve:decl-index=0:visual-constraint="792,327"

	private JMenuItem miBalanceDetailProduct = null;

	private JMenuItem miContractExeStat = null; // @jve:decl-index=0:visual-constraint="799,378"

	private OtherOption otherOption = null; // @jve:decl-index=0:

	public FmBalanceInfo() {
		super();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		otherOption = CasCommonVars.getOtherOption();

		initialize();

		initUIComponents();
		this.btnRelation.setVisible(FmMain.getInstance().checkHasModule(
				"ZiWoHeChaGuanLi"));
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

		// 日期
		if (otherOption.getLastTimedate() == null) {

			Calendar endClarendar = Calendar.getInstance();
			endClarendar.set(Calendar.YEAR, year);
			endClarendar.set(Calendar.MONTH, 11);
			endClarendar.set(Calendar.DAY_OF_MONTH, 31);
			this.cbbEndDate.setDate(endClarendar.getTime());
			this.cbbEndDate.setCalendar(endClarendar);
		} else {
			this.cbbEndDate.setDate(otherOption.getLastTimedate());
		}

		// 保留上次查询时的查询条件
		cbBcs.setSelected(otherOption.getIsBcsSelected() == null ? false
				: otherOption.getIsBcsSelected());
		cbBcus.setSelected(otherOption.getIsBcusSelected() == null ? false
				: otherOption.getIsBcusSelected());
		cbDzsc.setSelected(otherOption.getIsDzscSelected() == null ? false
				: otherOption.getIsDzscSelected());

		rbInBill.setSelected(otherOption.getIsFromCheck() == null ? true
				: !otherOption.getIsFromCheck());
		rbInImport.setSelected(otherOption.getIsFromCheck() == null ? false
				: otherOption.getIsFromCheck());

		isFromCheckData = rbInImport.isSelected();

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
			jPanel.add(getBtnSearch(), null);
			jPanel.add(getBtnPrint(), null);
			jPanel.add(getBtnDeliveryToPlant(), null);
			jPanel.add(getBtnRelation(), null);
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
			cbbBeginDate.setPreferredSize(new java.awt.Dimension(95, 25));
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
									FmBalanceInfo.this, btnReloadSearch
											.getText()
											+ "\n确定要继续吗？？", "警告!!!",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

								// 必须选择手册类型
								if (cbBcus.isSelected() == false
										&& cbBcs.isSelected() == false
										&& cbDzsc.isSelected() == false) {
									JOptionPane.showMessageDialog(
											FmBalanceInfo.this,
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
					FmBalanceInfo.this.dispose();
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
				list.add(addColumn("料件/规格/单位", "key", 150));// 1
				list.add(addColumn("1.库存原料", "f1", 100));// 3
				list.add(addColumn("B.非保税库存原料", "f0", 100));// 2

				// list.add(addColumn("2.半成品折原料", "f2", 120));//4

				list.add(addColumn("2.在产品折原料", "f2", 120));// 4
															// //wss2010.10.14添加
				list.add(addColumn("3.成品折原料", "f3", 100));// 5
				list.add(addColumn("4.外发加工/厂外存放数", "f4", 150));// 6
				list.add(addColumn("5.实际库存总数 = f1+f2+f3+f4+B", "f5", 200));// 7
				list.add(addColumn("6.已转厂未收货数", "f6", 100));// 8
				list.add(addColumn("7.已送货未转厂数", "f7", 100));// 9
				list.add(addColumn("8.已转厂未送货数", "f8", 100));// 10
				list.add(addColumn("9.已收货未转厂数", "f9", 100));// 11
				list.add(addColumn("10.应有库存数 = f5+f6+f7-f8-f9", "f10", 200));// 12
				list.add(addColumn("11.实际进口数量", "f11", 100));// 13
				list.add(addColumn("12.合同出口耗料", "f12", 100));// 14
				list.add(addColumn("13.合同结余数 = f11-f12", "f13", 180));// 15

				// list.add(addColumn("14.后续补税数 ", "f15", 180));//16

				list.add(addColumn("A.国内购买 ", "f18", 180));// 16

				// list.add(addColumn("15.料件内销数 ", "f16", 180));
				// list.add(addColumn(
				// "16.短(-)溢(+) = f10-f13+f14(后续补税数)-f15(料件内销数)", "f14",
				// 350));
				list
						.add(addColumn("14.短(-)溢(+) = f10-f13-A(国内购买)", "f14",
								350));// 17
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, adapter);
		TableColumnModel cm = jTable.getColumnModel();

		ColumnGroup g1 = new ColumnGroup("盘点库存");
		g1.add(cm.getColumn(2));
		g1.add(cm.getColumn(3));
		g1.add(cm.getColumn(4));
		g1.add(cm.getColumn(5));
		g1.add(cm.getColumn(6));
		g1.add(cm.getColumn(7));

		ColumnGroup g2 = new ColumnGroup("结转收送情况");
		g2.add(cm.getColumn(8));
		g2.add(cm.getColumn(9));
		g2.add(cm.getColumn(10));
		g2.add(cm.getColumn(11));
		// g2.add(cm.getColumn(12));

		ColumnGroup g3 = new ColumnGroup("合同执行情况");
		g3.add(cm.getColumn(13));
		g3.add(cm.getColumn(14));
		g3.add(cm.getColumn(15));

		// ColumnGroup g4 = new ColumnGroup("保税料件完税情况");
		// g4.add(cm.getColumn(15));
		// g4.add(cm.getColumn(16));
		// ColumnGroup g5 = new ColumnGroup("短溢情况");
		// g5.add(cm.getColumn(16));

		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(g1);
		header.addColumnGroup(g2);
		header.addColumnGroup(g3);
		// header.addColumnGroup(g4);
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
		de.get(12).setFractionCount(fraction);// 合同进口总数
		de.get(13).setFractionCount(fraction);// 耗用
		de.get(14).setFractionCount(fraction);
		de.get(15).setFractionCount(fraction);
		de.get(16).setFractionCount(fraction);
		de.get(17).setFractionCount(fraction);
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
				CommonProgress.showProgressDialog(flag, FmBalanceInfo.this,
						false, progressTask, 5000);
				CommonProgress.setMessage(flag, "系统正在重新获取资料，请稍后...");
				Date beginDate = cbbBeginDate.getDate();
				Date endDate = cbbEndDate.getDate();
				System.out.println("wss beginDate = " + beginDate);
				System.out.println("wss endDate = " + endDate);

				infos = casAction.findBalanceInfo(new Request(CommonVars
						.getCurrUser()), beginDate, endDate,
						getProjectTypeParam(), flag, isFromCheckData);
				System.out.println("infos size = " + infos.size());
				tableModel.setList(infos);

				// 保存查询条件
				saveSearchCondtion();

				CommonProgress.closeProgressDialog(flag);
				info += " 任务完成,共用时:" + (System.currentTimeMillis() - beginTime)
						+ " 毫秒 ";
				JOptionPane
						.showMessageDialog(FmBalanceInfo.this, info, "提示", 2);
			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				throw new RuntimeException("重新获取失败!!", e);
			} finally {
				ToolsAction toolsAction = (ToolsAction) CommonVars
						.getApplicationContext().getBean("toolsAction");
				toolsAction.removeProgressInfo(flag);
			}
			btnReloadSearch.setEnabled(true);
		}
	}

	/**
	 * 保存查询条件
	 * 
	 * @author wss
	 */
	private void saveSearchCondtion() {
		otherOption.setIsBcsSelected(cbBcs.isSelected());
		otherOption.setIsBcusSelected(cbBcus.isSelected());
		otherOption.setIsDzscSelected(cbDzsc.isSelected());
		otherOption.setIsFromCheck(rbInImport.isSelected());
		otherOption.setLastTimedate(cbbEndDate.getDate());

		otherOption = casAction.saveOtherOption(new Request(CommonVars
				.getCurrUser()), otherOption);
	}

	/**
	 * 获取帐册选择条件
	 * 
	 * @return
	 */
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
					result = casAction.findBalanceInfo(new Request(CommonVars
							.getCurrUser()));
					if (result != null && !result.isEmpty()) {
						CommonDataSource imgExgDS = new CommonDataSource(result);
						List<BaseCompany> company = new ArrayList<BaseCompany>();
						company.add(CommonVars.getCurrUser().getCompany());
						CommonDataSource companyDS = new CommonDataSource(
								company);
						InputStream masterReportStream = FmBalanceInfo.class
								.getResourceAsStream("report/BalanceInfoReport.jasper");
						InputStream detailReportStream = FmBalanceInfo.class
								.getResourceAsStream("report/BalanceInfoSubbReport.jasper");
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
			cbbEndDate.setPreferredSize(new java.awt.Dimension(95, 25));
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
	 * This method initializes rbInBill
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbInBill() {
		if (rbInBill == null) {
			rbInBill = new JRadioButton();
			rbInBill.setText("库存数据来源于工厂单据");
			rbInBill.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isFromCheckData = false;
				}
			});
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
			rbInImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isFromCheckData = true;
				}
			});
		}
		return rbInImport;
	}

	/**
	 * This method initializes btnDeliveryToPlant
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeliveryToPlant() {
		if (btnDeliveryToPlant == null) {
			btnDeliveryToPlant = new JButton();
			btnDeliveryToPlant.setText("查看送货转厂耗料情况");
			btnDeliveryToPlant
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							deliveryToPlant();
						}
					});
		}
		return btnDeliveryToPlant;
	}

	/**
	 * 查看送货转厂耗料情况
	 */
	public void deliveryToPlant() {
		BalanceInfo info = (BalanceInfo) tableModel.getCurrentRow();
		if (info == null) {
			JOptionPane.showMessageDialog(this, "请选择要查看送货转厂耗料情况的记录", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		new RunMiddleInfoThread(info).start();
	}

	class RunMiddleInfoThread extends Thread {
		private BalanceInfo balanceInfo = null;

		public RunMiddleInfoThread(BalanceInfo balanceInfo) {
			this.balanceInfo = balanceInfo;
		}

		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {
				btnDeliveryToPlant.setEnabled(false);
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
				CommonProgress.showProgressDialog(flag, FmBalanceInfo.this,
						false, progressTask, 5000);
				CommonProgress.setMessage(flag, "系统正在计算中间过程的记录，请稍后...");
				// 查询方法
				Date date = cbbEndDate.getDate();
				List bInfo = new ArrayList();
//				if (cbDzsc.isSelected()) {
//					bInfo = casAction.findDeliveryToPlantDzsc(new Request(
//							CommonVars.getCurrUser()), balanceInfo, date);
//				} else if (cbBcs.isSelected()) {
//					bInfo = casAction.findDeliveryToPlantBcs(new Request(
//							CommonVars.getCurrUser()), balanceInfo, date);
//				}
				//
				// 显示计算结果
				//
				new DgDeliveryToPlant(bInfo, balanceInfo).setVisible(true);
				CommonProgress.closeProgressDialog(flag);
				info += " 任务完成,共用时:" + (System.currentTimeMillis() - beginTime)
						+ " 毫秒 ";
				JOptionPane
						.showMessageDialog(FmBalanceInfo.this, info, "提示", 2);
			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmBalanceInfo.this,
						"查看送货转厂耗料情况的记录失败！！！" + e.getMessage(), "提示", 2);
			}
			btnDeliveryToPlant.setEnabled(true);
		}
	}

	//	
	// /**
	// * This method initializes btnRelation
	// *
	// * @return javax.swing.JButton
	// */
	// private JButton getBtnRelation() {
	// if (btnRelation == null) {
	// btnRelation = new JButton();
	// btnRelation.setText("关联报表");
	// btnRelation.setEnabled(false);
	// btnRelation.addActionListener(new java.awt.event.ActionListener() {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	//				
	// if (tableModel.getCurrentRow() == null) {
	// JOptionPane.showMessageDialog(
	// FmCheckBalance.this,
	// "请选择你要查看的资料", "确认", 1);
	// return;
	// }
	//					
	// CheckBalance c = (CheckBalance)tableModel.getCurrentRow();
	// sDate = c.getCheckDate();
	// sHsCode = c.getComplex().getCode();
	// sHsName = c.getName();
	// sHsSpec = c.getSpec();
	// // sPtNo = c.getPtNo();
	// // sPtName = c.getPtName();
	// // sPtSpec = c.getPtSpec();
	//					
	// kcType = c.getKcType();
	//					
	// Component comp = (Component) e.getSource();
	// getPmRelation().show(comp, 0, comp.getHeight());
	// }
	// });
	// }
	// return btnRelation;
	// }

	/**
	 * This method initializes btnRelation
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRelation() {
		if (btnRelation == null) {
			btnRelation = new JButton();
			btnRelation.setText("关联报表");
			btnRelation.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBalanceInfo.this,
								"请选择你要查看的资料", "确认", 1);
						return;
					}

					BalanceInfo b = (BalanceInfo) tableModel.getCurrentRow();
					sDate = cbbEndDate.getDate();
					// sHsCode = b
					sHsName = b.getName();
					sHsSpec = b.getSpec();
					// sPtNo = c.getPtNo();
					// sPtName = c.getPtName();
					// sPtSpec = c.getPtSpec();

					// kcType = c.getKcType();

					System.out.println("wss isFromCheckData = "
							+ isFromCheckData);
					Component comp = (Component) e.getSource();
					pmRelation = null;
					getPmRelation(isFromCheckData).show(comp, 0,
							comp.getHeight());
				}
			});
		}
		return btnRelation;
	}

	/**
	 * This method initializes pmRelation
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmRelation(boolean isFromCheckData) {
		if (pmRelation == null) {
			pmRelation = new JPopupMenu();

			// 料件当日结存
			// if(!isFromCheckData){
			pmRelation.add(getMiThatDayBalanceMateriel());
			// }

			// 在产品结存折料表
			pmRelation.add(getMiCurrentConvert());

			// 产成品结存折料表
			// if(!isFromCheckData){
			pmRelation.add(getMiProductConvert());
			// }

			// 外发加工当日结存表
			// if(!isFromCheckData){
			pmRelation.add(getMiThatDayBalanceOutSource());
			// }

			// 料件结转差额明细表
			pmRelation.add(getMiBalanceDetailMateriel());

			// 成品结转折料明细
			pmRelation.add(getMiTransferConvertDetail());

			// 合同执行情况
			pmRelation.add(getMiContractExeStat());
		}
		return pmRelation;
	}

	/**
	 * This method initializes miThatDayBalance
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiThatDayBalanceMateriel() {
		if (miThatDayBalanceMateriel == null) {
			miThatDayBalanceMateriel = new JMenuItem();
			miThatDayBalanceMateriel.setText("原材料当日结存表");
			miThatDayBalanceMateriel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							String materielType = MaterielType.MATERIEL;

							DgMaterialThatDayBalance dg = new DgMaterialThatDayBalance();

							dg.setTitle("原材料当日结存表");
							dg.setMaterielType(materielType);

							dg.setQueryState(isFromCheckData);

							dg.setQueryCondition(null, sDate, sHsCode, sHsName,
									sHsSpec, sPtNo, sPtName, sPtSpec);

							dg.queryData();
							dg.setVisible(true);
						}
					});
		}
		return miThatDayBalanceMateriel;
	}

	/**
	 * This method initializes miThatDayBalance
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiThatDayBalanceCurrent() {
		if (miThatDayBalanceCurrent == null) {
			miThatDayBalanceCurrent = new JMenuItem();
			miThatDayBalanceCurrent.setText("在产品结存折料表");
			miThatDayBalanceCurrent
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							String materielType = "currentTotal";

							DgMaterialThatDayBalance dg = new DgMaterialThatDayBalance();
							dg.setTitle("在产品当日结存表");
							dg.setMaterielType(materielType);
							dg.setQueryCondition(null, sDate, sHsCode, sHsName,
									sHsSpec, sPtNo, sPtName, sPtSpec);

							dg.queryData();
							dg.setVisible(true);
						}
					});
		}
		return miThatDayBalanceCurrent;
	}

	/**
	 * This method initializes miThatDayBalance
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCurrentConvert() {
		if (miCurrentConvert == null) {
			miCurrentConvert = new JMenuItem();
			miCurrentConvert.setText("在产品结存折料表");
			miCurrentConvert
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							DgCurrentThatDayBalanceToBom dg = new DgCurrentThatDayBalanceToBom();

							// 如果是来自单据
							if (isFromCheckData) {
								dg.setQueryState(isFromCheckData);
							}

							dg.setQueryConditionM(sDate, sHsCode, sHsName,
									sHsSpec, sPtNo, sPtName, sPtSpec);

							dg.queryData();

							dg.setVisible(true);
						}
					});
		}
		return miCurrentConvert;
	}

	/**
	 * This method initializes miThatDayBalance
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiThatDayBalanceProduct() {
		if (miThatDayBalanceProduct == null) {
			miThatDayBalanceProduct = new JMenuItem();
			miThatDayBalanceProduct.setText("产成品当日结存表");
			miThatDayBalanceProduct
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							String materielType = MaterielType.FINISHED_PRODUCT;

							DgMaterialThatDayBalance dg = new DgMaterialThatDayBalance();
							dg.setTitle("产成品当日结存表");
							dg.setMaterielType(materielType);
							dg.setQueryCondition(null, sDate, sHsCode, sHsName,
									sHsSpec, sPtNo, sPtName, sPtSpec);

							dg.queryData();
							dg.setVisible(true);
						}
					});
		}
		return miThatDayBalanceProduct;
	}

	/**
	 * This method initializes miThatDayBalance
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiThatDayBalanceOutSource() {
		if (miThatDayBalanceOutSource == null) {
			miThatDayBalanceOutSource = new JMenuItem();
			miThatDayBalanceOutSource.setText("外发加工当日结存表");
			miThatDayBalanceOutSource
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							DgMaterialThatDayBalance dg = new DgMaterialThatDayBalance();
							dg.setTitle("外发加工当日结存表");
							dg.setMaterielType("outSource");

							dg.setQueryState(isFromCheckData);

							dg.setQueryCondition(null, sDate, "", sHsName,
									sHsSpec, "", "", "");

							dg.queryData();
							dg.setVisible(true);
						}
					});
		}
		return miThatDayBalanceOutSource;
	}

	/**
	 * This method initializes miProductConvert
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiProductConvert() {
		if (miProductConvert == null) {
			miProductConvert = new JMenuItem();
			miProductConvert.setText("产成品结存折料表");
			miProductConvert
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							DgMaterialThatDayBalanceToBom dg = new DgMaterialThatDayBalanceToBom();
							dg.setMaterielType(MaterielType.FINISHED_PRODUCT);

							// 如果是来自单据
							if (isFromCheckData) {
								dg.setQueryState(isFromCheckData);
							}
							dg.setQueryConditionM(sDate, sHsCode, sHsName,
									sHsSpec, sPtNo, sPtName, sPtSpec);
							dg.queryData();
							dg.setVisible(true);

						}
					});
		}
		return miProductConvert;
	}

	/**
	 * This method initializes miBalanceDetail
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiBalanceDetailMateriel() {
		if (miBalanceDetailMateriel == null) {
			miBalanceDetailMateriel = new JMenuItem();
			miBalanceDetailMateriel.setText("结转差额明细表");
			miBalanceDetailMateriel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							DgSupplierBalanceDetailInfo dg = new DgSupplierBalanceDetailInfo();
							dg.setTitle("料件结转差额明细表");

							dg.setIsM(true);

							dg.setQueryCondition(sDate, sHsName, sHsSpec);
							dg.queryData();

							dg.setVisible(true);

						}
					});
		}
		return miBalanceDetailMateriel;
	}

	/**
	 * This method initializes miBalanceDetail
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiBalanceDetailProduct() {
		if (miBalanceDetailProduct == null) {
			miBalanceDetailProduct = new JMenuItem();
			miBalanceDetailProduct.setText("成品结转差额明细表");
			miBalanceDetailProduct
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							DgSupplierBalanceDetailInfo dg = new DgSupplierBalanceDetailInfo();
							dg.setTitle("成品结转差额明细表");

							dg.setIsM(false);

							dg.setQueryCondition(sDate, sHsName, sHsSpec);
							dg.queryData();

							dg.setVisible(true);

						}
					});
		}
		return miBalanceDetailProduct;
	}

	/**
	 * This method initializes miTransferConvertDetail
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiTransferConvertDetail() {
		if (miTransferConvertDetail == null) {
			miTransferConvertDetail = new JMenuItem();
			miTransferConvertDetail.setText("结转折料明细");
			miTransferConvertDetail
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgClientConvertDetailInfo dg = new DgClientConvertDetailInfo();

							dg.setQueryCondition(null, sDate, sHsCode, sHsName,
									sHsSpec, sPtNo, sPtName, sPtSpec);
							dg.queryData();

							dg.setVisible(true);
						}
					});
		}
		return miTransferConvertDetail;
	}

	/**
	 * This method initializes miContractExeStat
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiContractExeStat() {
		if (miContractExeStat == null) {
			miContractExeStat = new JMenuItem();
			miContractExeStat.setText("合同执行情况");
			miContractExeStat
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							FmContractExecStat dg = new FmContractExecStat();
							// ShowFormControl.showForm(dg);
							dg.setVisible(true);
						}
					});
		}
		return miContractExeStat;
	}

}
