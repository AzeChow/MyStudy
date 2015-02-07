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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
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
import com.bestway.bcus.cas.parameterset.entity.OtherOption;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ProgressTask;
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
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JComboBox;

/**
 * 短溢表（测试版）
 * 
 * @author wss2010.10.27
 */
@SuppressWarnings({"unchecked", "serial"})
public class DgBalanceInfoTest extends JInternalFrameBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2264733601687326241L;
	private JPanel jPanel = null;
	private JCalendarComboBox cbbBeginDate = null;
	private JButton btnReloadSearch = null;
	private JButton btnClose = null;
	private JPanel jPanel2 = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;

	/**
	 * 短溢表tableModel
	 */
	private JTableListModel tableModel = null;

	/**
	 * 海关帐远程接口
	 */
	private CasAction casAction = null;

	private JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JButton btnPrint = null;
	private JLabel jLabel1 = null;
	private JCalendarComboBox cbbEndDate = null;

	/**
	 * 电子帐册 选项
	 */
	private JRadioButton cbBcus = null;

	/**
	 * 电子手册 选项
	 */
	private JRadioButton cbDzsc = null;

	/**
	 * 电子化手册 选项
	 */
	private JRadioButton cbBcs = null;

	private JPanel jPanel1 = null;
	private JToolBar jJToolBarBar = null;

	/**
	 * 库存数据来源于工厂单据
	 */
	private JRadioButton rbInBill = null;

	/**
	 * 库存数据来源于实际盘点库存
	 */
	private JRadioButton rbInImport = null;

	/**
	 * 查询的结果值
	 */
	private List lsResult = null;

	/**
	 * This method initializes
	 * 
	 */
	private ButtonGroup group = new ButtonGroup();  //  @jve:decl-index=0:
	private ButtonGroup groupCustomMode = new ButtonGroup();  //  @jve:decl-index=0:
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

	/**
	 * 是否来自盘点
	 */
	private boolean isFromCheckData = false;

	/**
	 * 是否体现合同中所有料件的短溢情况
	 */
	private boolean isAll = false;
	
	private JPopupMenu  popupMenu = null;
	
	private JMenuItem miOverOrshortAnalyse = null;

	/**
	 * 关联报表 下拉选项
	 */
	private JPopupMenu pmRelation = null; // @jve:decl-index=0:visual-constraint="861,144"
	

	private JMenuItem miThatDayBalanceMateriel = null; // @jve:decl-index=0:visual-constraint="788,171"

	private JMenuItem miCurrentConvert = null;

	private JMenuItem miThatDayBalanceOutSource = null;

	private JMenuItem miProductConvert = null; // @jve:decl-index=0:visual-constraint="788,221"

	private JMenuItem miBalanceDetailMateriel = null;

	private JMenuItem miTransferConvertDetail = null; // @jve:decl-index=0:visual-constraint="792,327"

	private JMenuItem miContractExeStat = null; // @jve:decl-index=0:visual-constraint="799,378"

	/**
	 * 参数设置中的其它选项，保留着上一次的查询条件
	 */
	private OtherOption otherOption = null; // @jve:decl-index=0:

	private JCheckBox cbAll = null;
	private JLabel lbCaleType = null;
	private JComboBox cbbTransferCaleType = null;

	/**
	 * 构造方法
	 */
	public DgBalanceInfoTest() {
		super();
		/**
		 * 初始化海关帐远程接口
		 */
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		/**
		 * 参数设置值
		 */
		otherOption = CasCommonVars.getOtherOption();

		initialize();

		initUIComponents();

		/**
		 * 为关联报表按钮 设置权限
		 */
		this.btnRelation.setVisible(FmMain.getInstance().checkHasModule(
				"ZiWoHeChaGuanLi"));

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		group.add(rbInBill);
		group.add(rbInImport);
		groupCustomMode.add(cbBcs);
		groupCustomMode.add(cbBcus);
		groupCustomMode.add(cbDzsc);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(835, 541);
		this.setContentPane(getJContentPane());
		this.setTitle("短溢表(新版)");
	}

	/**
	 * 初始化组件的一些信息
	 */
	private void initUIComponents() {
		// 开始日期，记帐年度的1月1日
		int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
				.getYearInt();
		Calendar beginClarendar = Calendar.getInstance();
		beginClarendar.set(Calendar.YEAR, year);
		beginClarendar.set(Calendar.MONTH, 0);
		beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
		this.cbbBeginDate.setDate(beginClarendar.getTime());
		this.cbbBeginDate.setCalendar(beginClarendar);

		// 结束日期
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

		cbAll.setSelected(otherOption.getIsAll() == null ? false : otherOption
				.getIsAll());

		isFromCheckData = rbInImport.isSelected();

		isAll = cbAll.isSelected();

		/**
		 * 上次查询的数据
		 */
		lsResult = this.casAction.findBalanceInfo(new Request(CommonVars
				.getCurrUser()));
		initTable(lsResult);
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			lbCaleType = new JLabel();
			lbCaleType.setText("转厂折料方法");
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
			jPanel.add(lbCaleType, null);
			jPanel.add(getCbbTransferCaleType(), null);
			jPanel.add(getBtnSearch(), null);
			jPanel.add(getBtnDeliveryToPlant(), null);
			jPanel.add(getBtnRelation(), null);
			jPanel.add(getBtnPrint(), null);
			jPanel.add(getBtnClose(), null);
		}
		return jPanel;
	}

	/**
	 * 初始化 开始日期
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
	 * 报表计算 按钮
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
									DgBalanceInfoTest.this, btnReloadSearch
											.getText()
											+ "\n确定要继续吗？？", "警告!!!",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

								// 必须选择手册类型
								if (cbBcus.isSelected() == false
										&& cbBcs.isSelected() == false
										&& cbDzsc.isSelected() == false) {
									JOptionPane.showMessageDialog(
											DgBalanceInfoTest.this,
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
	 * 关闭 按钮
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
					DgBalanceInfoTest.this.dispose();
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
	 * 短溢表 table
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
	 * 初始化表
	 * 
	 * @param list
	 */
	private void initTable(final List list) {
		JTableListModelAdapter adapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("名称 ", "name", 100));// 1
				list.add(addColumn("规格", "spec", 150));// 1
				list.add(addColumn("单位", "unitName", 50));// 1
				list.add(addColumn("1.库存原料", "f1", 80));// 3
				list.add(addColumn("B.非保税库存原料", "f0", 100));// 2
				list.add(addColumn("2.在产品折原料", "f2", 90));// 4
				// //wss2010.10.14添加
				list.add(addColumn("3.成品折原料", "f3", 100));// 5
				list.add(addColumn("4.外发加工/厂外存放数", "f4", 130));// 6
				list.add(addColumn("5.实际库存总数 = f1+f2+f3+f4+B", "f5", 180));// 7
				list.add(addColumn("6.已转厂未收货数", "f6", 100));// 8
				list.add(addColumn("7.已送货未转厂数", "f7", 100));// 9
				list.add(addColumn("8.已转厂未送货数", "f8", 100));// 10
				list.add(addColumn("9.已收货未转厂数", "f9", 100));// 11
				list.add(addColumn("10.应有库存数 = f5+f6+f7-f8-f9", "f10", 200));// 12
				list.add(addColumn("11.实际进口数量", "f11", 100));// 13
				list.add(addColumn("12.合同出口耗料", "f12", 100));// 14
				list.add(addColumn("13.合同结余数 = f11-f12", "f13", 180));// 15

				list.add(addColumn("A.国内购买 ", "f18", 180));// 16

				list
						.add(addColumn("14.短(-)溢(+) = f10-f13-A(国内购买)", "f14",
								350));// 17
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, adapter);
		TableColumnModel cm = jTable.getColumnModel();

		ColumnGroup g1 = new ColumnGroup("盘点库存");
		g1.add(cm.getColumn(4));
		g1.add(cm.getColumn(5));
		g1.add(cm.getColumn(6));
		g1.add(cm.getColumn(7));
		g1.add(cm.getColumn(8));
		g1.add(cm.getColumn(9));
		
		ColumnGroup g2 = new ColumnGroup("结转收送情况");
		
		g2.add(cm.getColumn(10));
		g2.add(cm.getColumn(11));
		g2.add(cm.getColumn(12));
		g2.add(cm.getColumn(13));
		
		ColumnGroup g3 = new ColumnGroup("合同执行情况");
		
		g3.add(cm.getColumn(15));
		g3.add(cm.getColumn(16));
		g3.add(cm.getColumn(17));
		
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(g1);
		header.addColumnGroup(g2);
		header.addColumnGroup(g3);

		// 点解只保留两位小数呢？
		int fraction = CasCommonVars.getOtherOption().getBalanceDegree() == null ? 6
				: CasCommonVars.getOtherOption().getBalanceDegree();
		List<JTableListColumn> de = tableModel.getColumns();
//		de.get(2).setFractionCount(fraction);
//		de.get(3).setFractionCount(fraction);
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
		de.get(18).setFractionCount(fraction);
		de.get(19).setFractionCount(fraction);
		// 第六栏，第八栏数据有数据体现一般情况下试不允许的，用红色提醒用户
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
		jTable.getColumnModel().getColumn(12).setCellRenderer(
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
	 * 报表计算 线程
	 */
	class ReloadSearchThread extends Thread {
		public void run() {

			// 用于标识这个对话框的ID
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

				CommonProgress.showProgressDialog(flag, FmMain.getInstance(),
						false, progressTask, 5000);
				CommonProgress.setMessage(flag, "系统正在重新获取资料，请稍后...");

				// 查询日期
				Date beginDate = cbbBeginDate.getDate();
				Date endDate = cbbEndDate.getDate();

				// 是否体现合同中所有料件的短溢情况
				isAll = cbAll.isSelected();

				boolean useNewContract = true;
				if(cbbTransferCaleType.getSelectedIndex() == 1) {
					useNewContract = false;
				}
				
				// 查询
				infos = casAction.findBalanceInfoTest(new Request(CommonVars
						.getCurrUser()), beginDate, endDate,
						getProjectTypeParam(), flag, isFromCheckData, isAll, useNewContract);

				System.out.println("infos size = " + infos.size());
				tableModel.setList(infos);

				// 保存查询条件
				saveSearchCondtion();
				CommonProgress.closeProgressDialog(flag);
				long time = System.currentTimeMillis() - beginTime;
				info += " 任务完成,共用时:" + time / 1000 / 60 + "分" + time / 1000
						% 60 + " 秒 ";
				JOptionPane.showMessageDialog(DgBalanceInfoTest.this, info,
						"提示", 2);
			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				throw new RuntimeException("重新获取失败!!", e);
			} finally {
				CommonProgress.closeProgressDialog(flag);
				btnReloadSearch.setEnabled(true);
				ToolsAction toolsAction = (ToolsAction) CommonVars
						.getApplicationContext().getBean("toolsAction");
				toolsAction.removeProgressInfo(flag);
			}

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
		otherOption.setIsAll(cbAll.isSelected());

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
	 * 打印 按钮
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
					Component comp = (Component) e.getSource();
					getPopupMenu().show(comp, 0, comp.getHeight());
				}
			});
		}
		return btnPrint;
	}

	
	
	
	public JPopupMenu getPopupMenu() {
		if(popupMenu == null){
			popupMenu = new JPopupMenu();
			popupMenu.add(getMiOverOrshortAnalyse());
		}
		return popupMenu;
	}
	/**
	 * 打印短溢表
	 * @return
	 */
	public JMenuItem getMiOverOrshortAnalyse() {
		if(miOverOrshortAnalyse==null){
			miOverOrshortAnalyse = new JMenuItem();
			miOverOrshortAnalyse.setText("打印");
			miOverOrshortAnalyse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// 为什么要从数据库中重新获取结果？
					lsResult = casAction.findBalanceInfo(new Request(CommonVars
							.getCurrUser()));
					if (lsResult != null && !lsResult.isEmpty()) {
						CommonDataSource imgExgDS = new CommonDataSource(
								lsResult);
						List<BaseCompany> company = new ArrayList<BaseCompany>();
						company.add(CommonVars.getCurrUser().getCompany());
						CommonDataSource companyDS = new CommonDataSource(
								company);
						InputStream masterReportStream = DgBalanceInfoTest.class
								.getResourceAsStream("report/BalanceInfoReport.jasper");
						InputStream detailReportStream = DgBalanceInfoTest.class
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
		return miOverOrshortAnalyse;
	}

	/**
	 * 结束日期
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
	 * 电子帐册 选项
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JRadioButton getCbBcus() {
		if (cbBcus == null) {
			cbBcus = new JRadioButton();
			cbBcus.setText("电子帐册");
		}
		return cbBcus;
	}

	/**
	 * 电子化手册 选项
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JRadioButton getCbBcs() {
		if (cbBcs == null) {
			cbBcs = new JRadioButton();
			cbBcs.setText("电子化手册");
		}
		return cbBcs;
	}

	/**
	 * 电子手册 选项
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JRadioButton getCbDzsc() {
		if (cbDzsc == null) {
			cbDzsc = new JRadioButton();
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
			jJToolBarBar.add(getCbAll());
		}
		return jJToolBarBar;
	}

	/**
	 * 数据来源选项 -- 来自工厂单据（默认）
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
	 * 数据来源选项 -- 来自库存盘点
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
	 * 查看送货转厂耗料情况 按钮
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

	/**
	 * 查看送货转厂耗料情况 线程
	 * 
	 * @author
	 */
	class RunMiddleInfoThread extends Thread {
		private BalanceInfo balanceInfo = null;

		public RunMiddleInfoThread(BalanceInfo balanceInfo) {
			this.balanceInfo = balanceInfo;
		}

		public void run() {
			// 用于标识这个对话框的ID
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {
				btnDeliveryToPlant.setEnabled(false);
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

				CommonProgress.showProgressDialog(flag, FmMain.getInstance(),
						false, progressTask, 5000);
				CommonProgress.setMessage(flag, "系统正在计算中间过程的记录，请稍后...");

				// 查询方法
				// 查询日期
				Date beginDate = cbbBeginDate.getDate();
				Date endDate = cbbEndDate.getDate();
				List bInfo = new ArrayList();
				if (cbDzsc.isSelected()) {
					bInfo = casAction.findDeliveryToPlantDzsc(new Request(
							CommonVars.getCurrUser()), balanceInfo, beginDate,endDate,flag);
				} else if (cbBcs.isSelected()) {
					bInfo = casAction.findDeliveryToPlantBcs(new Request(
							CommonVars.getCurrUser()), balanceInfo, beginDate,endDate,flag);
				}else if(cbBcus.isSelected()){
					bInfo = casAction.findDeliveryToPlantBcus(new Request(
							CommonVars.getCurrUser()), balanceInfo, beginDate,endDate,flag);
				}

				// 显示计算结果
				CommonProgress.closeProgressDialog(flag);
				new DgDeliveryToPlant(bInfo, balanceInfo).setVisible(true);

//				info += " 任务完成,共用时:" + (System.currentTimeMillis() - beginTime)
//						+ " 毫秒 ";
//				JOptionPane.showMessageDialog(DgBalanceInfoTest.this, info,
//						"提示", 2);
			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgBalanceInfoTest.this,
						"查看送货转厂耗料情况的记录失败！！！" + e.getMessage(), "提示", 2);
			}finally{
				CommonProgress.closeProgressDialog(flag);
				btnDeliveryToPlant.setEnabled(true);
			}
			
		}
	}

	/**
	 * 报表关联 按钮
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
						JOptionPane.showMessageDialog(DgBalanceInfoTest.this,
								"请选择你要查看的资料", "确认", 1);
						return;
					}

					BalanceInfo b = (BalanceInfo) tableModel.getCurrentRow();
					sDate = cbbEndDate.getDate();

					sHsName = b.getName();
					sHsSpec = b.getSpec();

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
	 * 关联选项
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmRelation(boolean isFromCheckData) {
		if (pmRelation == null) {
			pmRelation = new JPopupMenu();

			// 料件当日结存
			pmRelation.add(getMiThatDayBalanceMateriel());

			// 在产品结存折料表
			pmRelation.add(getMiCurrentConvert());

			// 产成品结存折料表
			pmRelation.add(getMiProductConvert());

			// 外发加工当日结存表
			pmRelation.add(getMiThatDayBalanceOutSource());

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
	 * 关联 原材料当日结存表 选项
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
	 * 关联 在产品结存折料表 选项
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
	 * 关联 外发加工当日结存表 选项
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
	 * 关联 产成品结存折料表 选项
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
	 * 关联 料件结转差额明细表 选项
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
	 * 关联 结转折料明细 选项
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
	 * 关联 合同执行情况 选项
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

	/**
	 * 是否体现合同中所有料件的短溢情况 选项
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbAll() {
		if (cbAll == null) {
			cbAll = new JCheckBox();
			cbAll.setSelected(true);
			cbAll.setText("显示合同与单据(盘点)中的料件(不打√显示正在执行合同中的料件)");
		}
		return cbAll;
	}

	/**
	 * This method initializes cbbTransferCaleType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbTransferCaleType() {
		if (cbbTransferCaleType == null) {
			cbbTransferCaleType = new JComboBox();
			cbbTransferCaleType.setModel(new DefaultComboBoxModel(new String[] {
					"按最新手册", "使用单据对应" }));
		}
		return cbbTransferCaleType;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
