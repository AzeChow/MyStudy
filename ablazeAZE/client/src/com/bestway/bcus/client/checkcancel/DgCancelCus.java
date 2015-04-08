/*TradeMode
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumnModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.action.CheckCancelAuthorityAction;
import com.bestway.bcus.checkcancel.entity.CancelCusCustomsDeclara;
import com.bestway.bcus.checkcancel.entity.CancelCusExgResult;
import com.bestway.bcus.checkcancel.entity.CancelCusHead;
import com.bestway.bcus.checkcancel.entity.CancelCusImgResult;
import com.bestway.bcus.checkcancel.entity.CancelCustomsDeclara;
import com.bestway.bcus.checkcancel.entity.CancelImgResult;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgQueryCustomsDeclara;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.footer.TableFooterType;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.RrportDelcareType;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.DateValueListener;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgCancelCus extends JDialogBase {

	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JPanel jPanel4 = null;

	private JPanel jPanel5 = null;

	private boolean show = true;

	private JTextField jTextField = null;

	private JTextField tfCancelTimes = null;

	private JFormattedTextField jTextField2 = null;

	private JFormattedTextField jTextField3 = null;

	private JTextField jTextField5 = null;

	private JFormattedTextField jFormattedTextField = null;

	private JTextField jTextField6 = null;

	private JCalendarComboBox jCalendarComboBox = null;

	private JFormattedTextField jTextField7 = null;

	private JFormattedTextField jTextField8 = null;

	private JFormattedTextField jTextField9 = null;

	private JFormattedTextField jTextField10 = null;

	private JTextField jTextField11 = null;

	private JCalendarComboBox jCalendarComboBox1 = null;

	private JFormattedTextField jTextField12 = null;

	private JFormattedTextField jTextField13 = null;

	private JFormattedTextField jTextField15 = null;

	private JTextField jTextField16 = null;

	private JTextField jTextField18 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JButton jButton3 = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private JToolBar jToolBar = null;

	private JButton jButton4 = null;

	private JButton jButton5 = null;

	private JButton jButton6 = null;

	private JTable jTableCustom = null;

	private JScrollPane jScrollPane = null;

	private CancelCusHead cancelHead = null; // 核销报头 // @jve:decl-index=0:

	private CancelCusCustomsDeclara cancelCustomsDeclara = null; // 核销--报关单

	private CheckCancelAction checkCancelAction = null;

	private ManualDeclareAction manualDeclearAction = null;

	private int dataState = DataState.BROWSE;

	private JTableListModel tableModel = null;

	private JTableListModel tableModelCustom = null; // 报关单

	private AttributiveCellTableModel tableModelImgResult = null; // 料件结果

	private AttributiveCellTableModel tableModelExgResult = null; // 成品结果

	private JTableListModel tableModelImgBefore = null; // 料件中间过程数据

	private JTableListModel tableModelExgBefore = null; // 成品中间过程数据

	private JTableListModel tableModelUnitWear = null; // 单耗表

	private AttributiveCellTableModel tableModelPrice = null; // 单价表

	private JButton jButton7 = null;

	private JToolBar jToolBar1 = null;

	private JButton jButton8 = null;

	private JButton jButton9 = null;

	private JTabbedPane jTabbedPane1 = null;

	private JPanel jPanel6 = null;

	private JPanel jPanel8 = null;

	private JToolBar jToolBar2 = null;

	private JButton jButton10 = null;

	private JButton jButton11 = null;

	private JTabbedPane jTabbedPane2 = null;

	private JPanel jPanel9 = null;

	private JPanel jPanel11 = null;

	private JTable jTableImgResult = null;

	private JFooterScrollPane jScrollPane1 = null;

	private JTable jTableExgResult = null;

	private JFooterScrollPane jScrollPane2 = null;

	private JTable jTableImgBefore = null;

	private JScrollPane jScrollPane3 = null;

	private JTable jTableExgBefore = null;

	private JScrollPane jScrollPane4 = null;

	private JFormattedTextField tfInnerCancelMoney = null;

	private JFormattedTextField jFormattedTextField2 = null;

	private JCalendarComboBox jCalendarComboBox2 = null;

	private JCalendarComboBox jCalendarComboBox3 = null;

	private CheckCancelAuthorityAction checkCancelAuthorityAction = null;

	private JPanel jPanel7 = null;

	private JScrollPane jScrollPane5 = null;

	private JTable jTable = null;

	private JButton jButton12 = null;

	private JButton jButton13 = null;

	private JButton btnExcImport = null;
	/**
	 * 查询操作页面
	 */
	private PnCommonQueryPage pnCommonQueryPage = null;

	private JCheckBox cbUnitWear = null;
	private JPopupMenu pMenu = null; // @jve:decl-index=0:visual-constraint="807,243"
	private JMenuItem item1 = null;
	private JMenuItem item2 = null;
	private JMenuItem item3 = null;
	private JMenuItem item4 = null;
	private JMenu menu = null;
	private JMenu menu1 = null;
	private List listAvgPrice = null;
	private Boolean isEmsCancelAvgPrice = false; // @jve:decl-index=0:
	private Boolean isEmsCancelAvgTatolPrice = false; // @jve:decl-index=0:
	private JFormattedTextField ftfPrice = null;
	private JPanel jPanel10 = null;

	private JPanel jPanel12 = null;

	private JPanel jPanel13 = null;

	private JLabel jLabel31 = null;

	private JCheckBox cbReCompute = null;

	private JScrollPane jScrollPane6 = null;

	private JTable tbDanJia = null;

	private JButton btOk = null;

	private JPanel jPanelReal = null;

	private JLabel jLabel61 = null;

	private JLabel jLabel62 = null;

	private JLabel jLabel18 = null;

	private JFormattedTextField tfOverImgExport = null;

	private JLabel jLabel261 = null;

	private JLabel jLabel32 = null;

	private JFormattedTextField tfScale = null;

	private JLabel jLabel33 = null;

	private JLabel jLabel34 = null;

	private JTextField tfCheckStaffName = null;

	private JLabel jLabel35 = null;

	private JTextField tfCheckStaffTime = null;

	private JPanel jPanelSimulation = null;

	private JPanel jPanelCover = null;

	private JLabel jLabel51 = null;

	private JLabel jLabel63 = null;

	private JCustomFormattedTextField jFormattedTextField21 = null;

	private JLabel jLabel131 = null;

	private JLabel jLabel141 = null;

	private JFormattedTextField jTextField81 = null;

	private JLabel jLabel231 = null;

	private JLabel jLabel221 = null;

	private JFormattedTextField jTextField131 = null;

	private JLabel jLabel151 = null;

	private JLabel jLabel161 = null;

	private JFormattedTextField jTextField91 = null;

	private JLabel jLabel241 = null;

	private JCustomFormattedTextField tfInnerCancelMoney1 = null;

	private JCustomFormattedTextField jFormattedTextField1 = null;

	private JLabel jLabel171 = null;

	private JLabel jLabel251 = null;

	private JFormattedTextField jTextField151 = null;

	private JLabel jLabel262 = null;

	private JLabel jLabel611 = null;

	private JLabel jLabel621 = null;

	private JLabel jLabel181 = null;

	private JFormattedTextField tfOverImgExport1 = null;

	private JLabel jLabel2611 = null;

	private JButton jButton14 = null;

	private JButton btNext = null;

	int totalCount = 0; // 总行
	int rowCount = 0; // 当前行
	private Toolkit toolkit = Toolkit.getDefaultToolkit(); // @jve:decl-index=0:

	private JFormattedTextField innerCancelFillTaxMoney;

	/**
	 * This is the default constructor
	 */
	public DgCancelCus() {
		super();

		jTableImgResult = new MultiSpanCellTable();
		jTableExgResult = new MultiSpanCellTable();
		tbDanJia = new MultiSpanCellTable();
		checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
		manualDeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		checkCancelAuthorityAction = (CheckCancelAuthorityAction) CommonVars
				.getApplicationContext().getBean("checkCancelAuthorityAction");
		initialize();

		initUIComponents();

		initTableCustom(new Vector());
		initTableImgResult(new Vector());
		initTableImgBefore(new Vector());
		initTableExgResult(new Vector());
		initTableExgBefore(new Vector());

		// getDate();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("核销申请表计算");
		this.setSize(749, 700);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				if (!(DgCancelCus.this.isShow())) {
					jTabbedPane.removeTabAt(5);
					jTabbedPane.removeTabAt(4);
					jTabbedPane.removeTabAt(3);
					jTabbedPane.removeTabAt(2);

				}
				if (tableModel.getCurrentRow() != null) {
					cancelHead = (CancelCusHead) tableModel.getCurrentRow();
				}

				fillWindow();
				getDates();
				if (cancelHead.getDeclareState().equals(
						DeclareState.PROCESS_EXE)
						|| cancelHead.getDeclareState().equals(
								DeclareState.WAIT_EAA)) {
					dataState = DataState.READONLY;
				} else
					dataState = DataState.BROWSE;
				setState();
			}
		});
		toolkit.addAWTEventListener(new CapListener(), AWTEvent.KEY_EVENT_MASK);
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		CustomFormattedTextFieldUtils.setFormatterFactory(this.jTextField2, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.jTextField7, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.jTextField12, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.jTextField3, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.jTextField8, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.jTextField13, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(
				this.jFormattedTextField2, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.jTextField9, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(
				this.tfInnerCancelMoney, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(
				this.jFormattedTextField, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.jTextField10, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.jTextField15, 5);

		String isPrice = manualDeclearAction.getBpara(
				new Request(CommonVars.getCurrUser()),
				BcusParameter.Ems_CancelCus_Price);
		if (isPrice != null && "0".equals(isPrice)) {
			isEmsCancelAvgPrice = true;
		} else if (isPrice != null && "1".equals(isPrice)) {
			isEmsCancelAvgTatolPrice = true;
		}

	}

	/**
	 * 初始化料件--
	 */
	private void initImg() {
		List imgResultList = checkCancelAction.findCancelImgResult(new Request(
				CommonVars.getCurrUser()), cancelHead, false);
		if (imgResultList != null && !imgResultList.isEmpty())
			initTableImgResult(imgResultList);
		else
			initTableImgResult(new Vector());

		List imgBeforeList = checkCancelAction.findCancelImgBefore(new Request(
				CommonVars.getCurrUser()), cancelHead, false);
		if (imgBeforeList != null && !imgBeforeList.isEmpty())
			initTableImgBefore(imgBeforeList);
		else
			initTableImgBefore(new Vector());

	}

	private void initPrice() {
		List imgResultList = null;
		imgResultList = checkCancelAction.findCancelImgResult(new Request(
				CommonVars.getCurrUser()), cancelHead, false);
		if (imgResultList != null && !imgResultList.isEmpty())
			initTablePrice(imgResultList);
		else
			initTablePrice(new Vector());
	}

	private void initExg() {
		List exgResultList = null;
		exgResultList = checkCancelAction.findCancelExgResult(new Request(
				CommonVars.getCurrUser()), cancelHead, false);
		if (exgResultList != null && !exgResultList.isEmpty())
			initTableExgResult(exgResultList);
		else
			initTableExgResult(new Vector());

		List exgBeforeList = null;
		exgBeforeList = checkCancelAction.findCancelExgBefore(new Request(
				CommonVars.getCurrUser()), cancelHead, false);
		if (exgBeforeList != null && !exgBeforeList.isEmpty())
			initTableExgBefore(exgBeforeList);
		else
			initTableExgBefore(new Vector());
	}

	private void initTableCustom(final List list) {

		tableModelCustom = new JTableListModel(jTableCustom, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("报关单号", "customNo", 140));
						list.add(addColumn("报关地", "custom.name", 100));
						list.add(addColumn("进出口类型", "inOutportFlat", 80));
						list.add(addColumn("申报日期", "declareDate", 80));
						list.add(addColumn("进出口日期", "inOutportDate", 80));
						list.add(addColumn("贸易方式", "tradeMode.name", 100));
						list.add(addColumn("备注", "note", 100));

						return list;
					}
				});
		// this.jTableCustom
		// .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	private void initTableImgResult(final List list) {
		tableModelImgResult = new AttributiveCellTableModel(
				(MultiSpanCellTable) jTableImgResult, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册序号", "emsSeqNum", 60));
						list.add(addColumn("料件名称", "name", 200));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("单位", "unit", 80));

						list.add(addColumn("期初数量", "beginNum", 80));
						list.add(addColumn("期初金额", "beginMoney", 80));

						list.add(addColumn("核增数量", "cancelAddNum", 80));
						list.add(addColumn("核减数量", "cancelMinNum", 80));

						list.add(addColumn("总数量", "useSumNum", 80)); // 消耗
						list.add(addColumn("总价值", "useSumPrice", 80));
						list.add(addColumn("总重量", "useSumWeight", 80));

						list.add(addColumn("总数量", "innerUseSumNum", 80)); // 料件内销
						list.add(addColumn("总价值", "innerUseSumPrice", 80));

						list.add(addColumn("数量", "leftOverImgNum", 80)); // 边角料
						list.add(addColumn("总价值", "leftOverImgSumPrice", 80));
						list.add(addColumn("总重量", "leftOverImgSumWeight", 80));

						list.add(addColumn("数量", "leaveNum", 80)); // 应剩余
						list.add(addColumn("总价值", "leaveSumPrice", 80));
						list.add(addColumn("总重量", "leaveSumWeight", 80));

						list.add(addColumn("数量", "factLeaveNum", 80)); // 实际剩余
						list.add(addColumn("总价值", "factLeaveSumPrice", 80));
						list.add(addColumn("总重量", "factLeaveSumWeight", 80));

						list.add(addColumn("数量", "resultNum", 80));
						list.add(addColumn("价值", "resultSumPrice", 80));

						list.add(addColumn("国内购买", "inChinaBuyNum", 80));
						list.add(addColumn("国内购买价值", "inChinaBuyMoney", 80));
						list.add(addColumn("其他来源", "otherSourceNum", 80));
						if (isEmsCancelAvgTatolPrice) {
							list.add(addColumn("平均单价", "averagePrice", 80));
						}
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		TableColumnModel cm = jTableImgResult.getColumnModel();

		ColumnGroup gUse = new ColumnGroup("消耗");
		gUse.add(cm.getColumn(9));
		gUse.add(cm.getColumn(10));
		gUse.add(cm.getColumn(11));

		ColumnGroup gInnerUse = new ColumnGroup("料件内销");
		gInnerUse.add(cm.getColumn(12));
		gInnerUse.add(cm.getColumn(13));

		ColumnGroup gLeftOver = new ColumnGroup("边角料");
		gLeftOver.add(cm.getColumn(14));
		gLeftOver.add(cm.getColumn(15));
		gLeftOver.add(cm.getColumn(16));

		ColumnGroup gLeave = new ColumnGroup("应剩余");
		gLeave.add(cm.getColumn(17));
		gLeave.add(cm.getColumn(18));
		gLeave.add(cm.getColumn(19));

		ColumnGroup gFactLeave = new ColumnGroup("实际剩余");
		gFactLeave.add(cm.getColumn(20));
		gFactLeave.add(cm.getColumn(21));
		gFactLeave.add(cm.getColumn(22));

		ColumnGroup result = new ColumnGroup("结余");
		result.add(cm.getColumn(23));
		result.add(cm.getColumn(24));

		ColumnGroup other = new ColumnGroup("其他数量");
		other.add(cm.getColumn(25));
		other.add(cm.getColumn(26));
		other.add(cm.getColumn(27));

		GroupableTableHeader header = (GroupableTableHeader) jTableImgResult
				.getTableHeader();
		header.addColumnGroup(gUse);
		header.addColumnGroup(gInnerUse);
		header.addColumnGroup(gLeftOver);
		header.addColumnGroup(gLeave);
		header.addColumnGroup(gFactLeave);
		header.addColumnGroup(result);
		header.addColumnGroup(other);
		tableModelImgResult.clearFooterTypeInfo();
		tableModelImgResult.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		for (int i = 5; i < 27; i++) {
			tableModelImgResult.addFooterTypeInfo(new TableFooterType(i,
					TableFooterType.SUM, "", 5));
		}
	}

	private void initTableExgResult(final List list) {
		tableModelExgResult = new AttributiveCellTableModel(
				(MultiSpanCellTable) jTableExgResult, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册序号", "emsSeqNum", 60));
						list.add(addColumn("成品名称", "name", 200));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("单位", "unit", 80));

						list.add(addColumn("总数量", "useSumNum", 80)); // 消耗
						list.add(addColumn("总价值", "useSumPrice", 80));
						list.add(addColumn("总重量", "useSumWeight", 80));

						list.add(addColumn("总数量", "innerUseSumNum", 80)); // 成品内销
						list.add(addColumn("总价值", "innerUseSumPrice", 80));

						list.add(addColumn("数量", "leftOverImgNum", 80)); // 残次品
						list.add(addColumn("总价值", "leftOverImgSumPrice", 80));
						list.add(addColumn("总重量", "leftOverImgSumWeight", 80));

						list.add(addColumn("数量", "factLeaveNum", 80)); // 实际剩余
						list.add(addColumn("总价值", "factLeaveSumPrice", 80));
						list.add(addColumn("总重量", "factLeaveSumWeight", 80));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		TableColumnModel cm = jTableExgResult.getColumnModel();

		ColumnGroup gUse = new ColumnGroup("消耗");
		gUse.add(cm.getColumn(5));
		gUse.add(cm.getColumn(6));
		gUse.add(cm.getColumn(7));

		ColumnGroup gInnerUse = new ColumnGroup("成品内销");
		gInnerUse.add(cm.getColumn(8));
		gInnerUse.add(cm.getColumn(9));

		ColumnGroup gLeftOver = new ColumnGroup("残次品");
		gLeftOver.add(cm.getColumn(10));
		gLeftOver.add(cm.getColumn(11));
		gLeftOver.add(cm.getColumn(12));

		ColumnGroup gFactLeave = new ColumnGroup("实际剩余");
		gFactLeave.add(cm.getColumn(13));
		gFactLeave.add(cm.getColumn(14));
		gFactLeave.add(cm.getColumn(15));

		GroupableTableHeader header = (GroupableTableHeader) jTableExgResult
				.getTableHeader();
		header.addColumnGroup(gUse);
		header.addColumnGroup(gInnerUse);
		header.addColumnGroup(gLeftOver);
		header.addColumnGroup(gFactLeave);
		tableModelExgResult.clearFooterTypeInfo();
		tableModelExgResult.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		for (int i = 5; i < 16; i++) {
			tableModelExgResult.addFooterTypeInfo(new TableFooterType(i,
					TableFooterType.SUM, "", 5));
		}

	}

	private void initTableImgBefore(final List list) {
		tableModelImgBefore = new JTableListModel(jTableImgBefore, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册序号", "emsSeqNum", 60,
								Integer.class));
						list.add(addColumn("核扣代码", "checkCode", 80));
						list.add(addColumn("核扣方法", "checkWay", 80));
						list.add(addColumn("数量", "num", 80));
						list.add(addColumn("金额", "price", 80));
						list.add(addColumn("核扣名称", "checkName", 100));
						list.add(addColumn("核扣方式说明", "checkModeShow", 120));
						list.add(addColumn("贸易方式", "tradeMode", 100));
						list.add(addColumn("备注", "note", 120));
						return list;
					}
				});
	}

	private void initTableExgBefore(final List list) {
		tableModelExgBefore = new JTableListModel(jTableExgBefore, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册序号", "emsSeqNum", 60,
								Integer.class));
						list.add(addColumn("核扣代码", "checkCode", 80));
						list.add(addColumn("核扣方法", "checkWay", 80));
						list.add(addColumn("数量", "num", 80));
						list.add(addColumn("金额", "price", 80));
						list.add(addColumn("核扣名称", "checkName", 100));
						list.add(addColumn("核扣方式说明", "checkModeShow", 120));
						list.add(addColumn("贸易方式", "tradeMode", 100));
						list.add(addColumn("备注", "note", 120));
						return list;
					}
				});
	}

	// private String forNumq(Double d, int dig) {
	// if (d == null || d.equals(Double.valueOf(0))) {
	// return "";
	// }
	// double shuliang = d.doubleValue();
	// BigDecimal bd = new BigDecimal(shuliang);
	// String totalshuliang = bd.setScale(dig, BigDecimal.ROUND_HALF_UP)
	// .toString();
	// for (int i = totalshuliang.length(); i > 0; i--) {
	// if (totalshuliang.substring(i - 1, i).equals("0")) {
	// totalshuliang = totalshuliang.substring(0, i - 1);
	// } else if (totalshuliang.substring(i - 1, i).equals(".")) {
	// totalshuliang = totalshuliang.substring(0, i - 1);
	// break;
	// } else {
	// break;
	// }
	// }
	// return totalshuliang;
	// }

	private void fillWindow() {
		jTextField.setText(cancelHead.getEmsNo());
		jTextField6.setText(cancelHead.getEmsApprNo());
		if (cancelHead.getDeclareType().equals(RrportDelcareType.BEGINDELCARE)) {
			jTextField11.setText("预报核");
		} else if (cancelHead.getDeclareType()
				.equals(RrportDelcareType.DELCARE)) {
			jTextField11.setText("正式报核");
		}

		tfCancelTimes.setText(cancelHead.getCancelTimes());
		jCalendarComboBox.setDate(cancelHead.getBeginDate());
		jCalendarComboBox1.setDate(cancelHead.getEndDate());
		jTextField2.setValue(cancelHead.getInportCustomNum());
		jTextField7.setValue(cancelHead.getOutportCustomNum());
		jTextField12.setValue(cancelHead.getDeclareImgNum());
		jTextField3.setValue(cancelHead.getDeclareExgNum());
		// 获取本期进口总金额
		jTextField8.setValue(cancelHead.getThisInportMoney());
		// 获取本期出口总金额
		jTextField13.setValue(cancelHead.getThisOutportMoney());
		// 获取期初料件总金额
		jFormattedTextField2.setValue(cancelHead.getBeginImgMoney());
		// 获取期末结余料件总金额(
		jTextField9.setValue(cancelHead.getEndBalanceImgMoney());
		// 获取内销金额
		tfInnerCancelMoney.setValue(cancelHead.getInnerCancelMoney());
		// 获取内销补税税额
		jTextField10.setValue(cancelHead.getInnerCancelFillTaxMoney());
		// 获取出口成品耗用料件金额
		jFormattedTextField.setValue(cancelHead.getOutportExgUseImgNum());
		// 获取退运料件金额
		jTextField15.setValue(cancelHead.getExitImgMoney());
		// 获取余料结转出口
		tfOverImgExport.setValue(cancelHead.getRemainExportMoney());

		// 获取本期进口总金额(模拟数据Jpanel)
		jTextField81.setValue(cancelHead.getSimulateThisInportMoney());
		// 获取本期出口总金额(模拟数据Jpanel)
		jTextField131.setValue(cancelHead.getSimulateThisOutportMoney());
		// 获取期初料件总金额(模拟数据Jpanel)
		jFormattedTextField21.setValue(cancelHead.getSimulateBeginImgMoney());
		// 获取期末结余料件总金额(模拟数据Jpanel)
		jTextField91.setValue(cancelHead.getSimulateEndBalanceImgMoney());
		// 获取内销金额(模拟数据Jpanel)
		tfInnerCancelMoney1.setValue(cancelHead.getSimulateInnerCancelMoney());
		// 获取出口成品耗用料件金额(模拟数据Jpanel)
		jFormattedTextField1.setValue(cancelHead
				.getSimulateOutportExgUseImgNum());
		// 获取退运料件金额(模拟数据Jpanel)
		jTextField151.setValue(cancelHead.getSimulateExitImgMoney());
		// 获取余料结转出口(模拟数据Jpanel)
		tfOverImgExport1.setValue(cancelHead.getSimulateRemainExportMoney());
		jTextField5.setText(cancelHead.getNote());
		jTextField16.setText(cancelHead.getInputUser()); // 录入员
		jCalendarComboBox3.setDate(cancelHead.getInputDate()); // 录入日期
		jCalendarComboBox2.setDate(cancelHead.getDeclareDate());// 申报日期
		jTextField18.setText(cancelHead.getDeclareTime()); // 申报时间
		tfCheckStaffName.setText(cancelHead.getCheckStaffName()); // 财务审核人员
		if (cancelHead.getCheckStaffTime() != null
				&& !"".equals(cancelHead.getCheckStaffTime())) {
			tfCheckStaffTime.setText(CommonUtils.getDate(
					cancelHead.getCheckStaffTime(), "yyyy-MM-dd")); // 财务审核时间
		}
		tfScale.setText(CommonUtils.formatDoubleByDigit(cancelHead.getScale(),
				5));
	}

	public Double formatBig(Object amount) {
		if (amount == null) {
			return 0.0;
		}
		// BigDecimal bd = new BigDecimal(amount.toString());
		// String amountStr = bd.setScale(5, BigDecimal.ROUND_HALF_UP)
		// .toString();
		// return Double.valueOf(amountStr);
		return CommonVars.getDoubleByDigit(new Double(amount.toString()), 5);
	}

	//
	// private String doubleToStr(Double value) { // 转换doubleToStr 取数据
	// try {
	// if (value == null || value.doubleValue() == 0) {
	// return null;
	// }
	// getNumberFormatter().setValueClass(Double.class);
	// return getNumberFormatter().valueToString(value);
	// } catch (ParseException e) {
	// e.printStackTrace();
	// return null;
	// }
	// }

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */

	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			javax.swing.JLabel jLabel = new JLabel();
			jPanel = new JPanel();
			jLabel.setText("核销计算表");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setForeground(new java.awt.Color(0, 102, 51));
			jPanel.setBorder(javax.swing.BorderFactory
					.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			jPanel.add(jLabel, null);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jTabbedPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JTabbedPane
	 * 
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.TOP);
			jTabbedPane.addTab("核销表头", null, getJPanel2(), null);
			jTabbedPane.addTab("报关单", null, getJPanel3(), null);
			jTabbedPane.addTab("料件", null, getJPanel4(), null);
			jTabbedPane.addTab("成品", null, getJPanel5(), null);
			jTabbedPane.addTab("单耗表", null, getJPanel7(), null);

			jTabbedPane.addTab("单价表", null, getJPanel10(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							new InitTable().execute();
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			javax.swing.JLabel jLabel30 = new JLabel();

			javax.swing.JLabel jLabel29 = new JLabel();

			javax.swing.JLabel jLabel28 = new JLabel();

			javax.swing.JLabel jLabel27 = new JLabel();

			javax.swing.JLabel jLabel21 = new JLabel();

			javax.swing.JLabel jLabel20 = new JLabel();

			javax.swing.JLabel jLabel19 = new JLabel();

			javax.swing.JLabel jLabel12 = new JLabel();

			javax.swing.JLabel jLabel11 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();

			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			jLabel1.setBounds(39, 26, 50, 19);
			jLabel1.setText("帐册编号");
			jLabel2.setBounds(39, 57, 53, 19);
			jLabel2.setText("核销次数");
			jLabel3.setBounds(39, 86, 87, 20);
			jLabel3.setText("进口报关单份数");
			jLabel4.setBounds(39, 118, 75, 21);
			jLabel4.setText("报核成品项数");
			jLabel9.setBounds(39, 175, 33, 20);
			jLabel9.setText("备注");
			jLabel10.setBounds(256, 27, 51, 19);
			jLabel10.setText("批准证号");
			jLabel11.setBounds(256, 58, 75, 20);
			jLabel11.setText("报核开始日期");
			jLabel12.setBounds(256, 87, 89, 19);
			jLabel12.setText("出口报关单份数");
			jLabel19.setBounds(500, 27, 58, 18);
			jLabel19.setText("报核类型");
			jLabel20.setBounds(500, 58, 76, 22);
			jLabel20.setText("报核截止日期");
			jLabel21.setBounds(257, 115, 76, 21);
			jLabel21.setText("报核料件项数");
			jLabel27.setBounds(39, 146, 55, 20);
			jLabel27.setText("录入日期");
			jLabel28.setBounds(256, 148, 42, 18);
			jLabel28.setText("录入员");
			jLabel29.setBounds(500, 91, 76, 18);
			jLabel29.setText("申报日期");
			jLabel30.setBounds(500, 119, 76, 18);
			jLabel30.setText("申报时间");
			jPanel2.add(jLabel1, null);
			jPanel2.add(getJTextField(), null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getTfCancelTimes(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getJTextField2(), null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getJTextField3(), null);
			jPanel2.add(getJTextField5(), null);
			jPanel2.add(jLabel9, null);
			jPanel2.add(jLabel10, null);
			jPanel2.add(getJTextField6(), null);
			jPanel2.add(jLabel11, null);
			jPanel2.add(getJCalendarComboBox(), null);
			jPanel2.add(jLabel12, null);
			jPanel2.add(getJTextField7(), null);
			jPanel2.add(jLabel19, null);
			jPanel2.add(getJTextField11(), null);
			jPanel2.add(jLabel20, null);
			jPanel2.add(getJCalendarComboBox1(), null);
			jPanel2.add(jLabel21, null);
			jPanel2.add(getJTextField12(), null);
			jPanel2.add(jLabel27, null);
			jPanel2.add(jLabel28, null);
			jPanel2.add(getJTextField16(), null);
			jPanel2.add(jLabel29, null);
			jPanel2.add(jLabel30, null);
			jPanel2.add(getJTextField18(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton2(), null);
			jPanel2.add(getJButton3(), null);
			jPanel2.add(getJButton7(), null);
			jPanel2.add(getJCalendarComboBox2(), null);
			jPanel2.add(getJCalendarComboBox3(), null);
			jPanel2.add(getJPanelReal(), null);
			jPanel2.add(getCoverPanel(), null);
			jPanel2.add(getJPanelSimulation(), null);
			jPanel2.add(getJButton14(), null);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jPanel3
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel3.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * 
	 * This method initializes jPanel4
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(getJToolBar2(), java.awt.BorderLayout.NORTH);
			jPanel4.add(getJTabbedPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * 
	 * This method initializes jPanel5
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel5.add(getJTabbedPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel5;
	}

	/**
	 * @return Returns the show.
	 */
	public boolean isShow() {
		return show;
	}

	/**
	 * @param show
	 *            The show to set.
	 */
	public void setShow(boolean show) {
		this.show = show;
	}

	/**
	 * 
	 * This method initializes jTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(95, 26, 130, 22);
			jTextField.setEditable(false);
		}
		return jTextField;
	}

	/**
	 * 
	 * This method initializes tfCancelTimes
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfCancelTimes() {
		if (tfCancelTimes == null) {
			tfCancelTimes = new JTextField();
			// jTextField1.setEditable(false);
			tfCancelTimes.setBounds(95, 57, 130, 22);
		}
		return tfCancelTimes;
	}

	/**
	 * 
	 * This method initializes jTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JFormattedTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JFormattedTextField();
			jTextField2.setEditable(false);
			jTextField2.setBounds(125, 86, 100, 22);
		}
		return jTextField2;
	}

	/**
	 * 
	 * This method initializes jTextField3
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JFormattedTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JFormattedTextField();
			jTextField3.setEditable(false);
			jTextField3.setBounds(125, 118, 101, 22);
		}
		return jTextField3;
	}

	/**
	 * 
	 * This method initializes jTextField5
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			jTextField5.setBounds(95, 175, 474, 22);
		}
		return jTextField5;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField() {
		if (jFormattedTextField == null) {
			jFormattedTextField = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField.setBounds(new Rectangle(453, 103, 144, 22));
			jFormattedTextField
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField;
	}

	/**
	 * 
	 * This method initializes jTextField6
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
			jTextField6.setEditable(false);
			jTextField6.setBounds(321, 27, 149, 22);
		}
		return jTextField6;
	}

	/**
	 * 
	 * This method initializes jCalendarComboBox
	 * 
	 * 
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 * 
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(338, 58, 131, 22);
			jCalendarComboBox.addDateValueListener(new DateValueListener() {

				public void valueChanged(Date newDate) {
					// System.out.println("杨万里 淡仍显螒赤人");
					// if(dataState==DataState.ADD){
					Calendar cal = Calendar.getInstance();
					cal.setTime(CommonVars.dateToStandDate(jCalendarComboBox
							.getDate()));

					int week = cal.get(Calendar.WEEK_OF_YEAR);// WEEK_OF_YEAR:类
					// cbbWeek.setSelectedItem(String.valueOf(week));
					System.out.println("week=" + week);
					int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
					System.out.println("dayOfYear=" + dayOfYear);
					// cal.get(Calendar.DAY_OF_WEEK);//获取本周的当前天数(比如：是本周的第几天)
					// System.out.println("dayOfWeek="+dayOfWeek);
					Calendar calFirstDayInThisWeek = (Calendar) cal.clone();
					calFirstDayInThisWeek.add(Calendar.DATE,
							cal.getActualMinimum(Calendar.DAY_OF_YEAR)
									- dayOfYear);
					Calendar calLastDayInThisWeek = (Calendar) cal.clone();// clone():创建并返回此对象的一个副本。
					calLastDayInThisWeek.add(Calendar.DATE,
							cal.getActualMaximum(Calendar.DAY_OF_YEAR)
									- dayOfYear);

					Calendar nowDate = (Calendar) cal.clone();
					nowDate.add(Calendar.DAY_OF_YEAR, +179);
					Date nowDates = nowDate.getTime();
					jCalendarComboBox1.setDate(nowDates);
				}
				// }

			});
		}
		return jCalendarComboBox;
	}

	/**
	 * 
	 * This method initializes jTextField7
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JFormattedTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JFormattedTextField();
			jTextField7.setEditable(false);
			jTextField7.setBounds(354, 87, 117, 22);
		}
		return jTextField7;
	}

	/**
	 * 
	 * This method initializes jTextField8
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JFormattedTextField getJTextField8() {
		if (jTextField8 == null) {
			jTextField8 = new JFormattedTextField();
			// jTextField8.setEditable(false);
			jTextField8.setBounds(new Rectangle(453, 17, 144, 22));
		}
		return jTextField8;
	}

	/**
	 * 
	 * This method initializes jTextField9
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JFormattedTextField getJTextField9() {
		if (jTextField9 == null) {
			jTextField9 = new JFormattedTextField();
			// jTextField9.setEditable(false);
			jTextField9.setBounds(new Rectangle(453, 44, 144, 22));
		}
		return jTextField9;
	}

	/**
	 * 
	 * This method initializes jTextField10
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JFormattedTextField getJTextField10() {
		if (jTextField10 == null) {
			jTextField10 = new JFormattedTextField();
			// jTextField10.setEditable(false);
			jTextField10.setBounds(new Rectangle(453, 74, 144, 22));
		}
		return jTextField10;
	}

	/**
	 * 
	 * This method initializes jTextField11
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField11() {
		if (jTextField11 == null) {
			jTextField11 = new JTextField();
			jTextField11.setEditable(false);
			jTextField11.setBounds(577, 27, 127, 22);
		}
		return jTextField11;
	}

	/**
	 * 
	 * This method initializes jCalendarComboBox1
	 * 
	 * 
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 * 
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.setBounds(577, 58, 127, 22);
			// jCalendarComboBox1.setDate(getDates());
		}
		return jCalendarComboBox1;
	}

	public Date getDates() {
		// if (tableModel.getCurrentRow() != null) {
		// cancelHead = (CancelCusHead) tableModel.getCurrentRow();
		// }
		if (cancelHead.getBeginDate() != null
				&& cancelHead.getEndDate() == null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(CommonVars.dateToStandDate(cancelHead.getBeginDate()));
			System.out.println(jCalendarComboBox.getDate());
			int week = cal.get(Calendar.WEEK_OF_YEAR);// WEEK_OF_YEAR:类
			// java.util.Calendar
			// 中的静态变量
			// cbbWeek.setSelectedItem(String.valueOf(week));
			System.out.println("week=" + week);
			int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
			System.out.println("dayOfYear=" + dayOfYear);
			// int dayOfWeek =
			// cal.get(Calendar.DAY_OF_WEEK);//获取本周的当前天数(比如：是本周的第几天)
			// System.out.println("dayOfWeek="+dayOfWeek);
			Calendar calFirstDayInThisWeek = (Calendar) cal.clone();
			calFirstDayInThisWeek.add(Calendar.DATE,
					cal.getActualMinimum(Calendar.DAY_OF_YEAR) - dayOfYear);
			Calendar calLastDayInThisWeek = (Calendar) cal.clone();// clone():创建并返回此对象的一个副本。
			calLastDayInThisWeek.add(Calendar.DATE,
					cal.getActualMaximum(Calendar.DAY_OF_YEAR) - dayOfYear);

			Calendar nowDate = (Calendar) cal.clone();
			nowDate.add(Calendar.DAY_OF_YEAR, +179);
			Date nowDates = nowDate.getTime();
			// jCalendarComboBox1.setDate(nowDates);
			jCalendarComboBox1.setDate(nowDates);
			return nowDates;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * This method initializes jTextField12
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JFormattedTextField getJTextField12() {
		if (jTextField12 == null) {
			jTextField12 = new JFormattedTextField();
			jTextField12.setEditable(false);
			jTextField12.setBounds(334, 115, 108, 22);
		}
		return jTextField12;
	}

	/**
	 * 
	 * This method initializes jTextField13
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JFormattedTextField getJTextField13() {
		if (jTextField13 == null) {
			jTextField13 = new JFormattedTextField();
			// jTextField13.setEditable(false);
			jTextField13.setBounds(new Rectangle(109, 44, 144, 22));
		}
		return jTextField13;
	}

	/**
	 * 
	 * This method initializes jTextField15
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JFormattedTextField getJTextField15() {
		if (jTextField15 == null) {
			jTextField15 = new JFormattedTextField();
			// jTextField15.setEditable(false);
			jTextField15.setBounds(new Rectangle(109, 102, 144, 22));
		}
		return jTextField15;
	}

	/**
	 * 
	 * This method initializes jTextField16
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField16() {
		if (jTextField16 == null) {
			jTextField16 = new JTextField();
			// jTextField16.setEditable(false);
			jTextField16.setBounds(328, 148, 100, 22);
		}
		return jTextField16;
	}

	/**
	 * 
	 * This method initializes jTextField18
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField18() {
		if (jTextField18 == null) {
			jTextField18 = new JTextField();
			// jTextField18.setEditable(false);
			jTextField18.setBounds(577, 117, 103, 22);
		}
		return jTextField18;
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
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(253, 561, 61, 25);
			jButton.setText("修改");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					dataState = DataState.EDIT;
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
			jButton1.setBounds(330, 561, 63, 25);
			jButton1.setText("保存");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkCancelAuthorityAction.saveDataCancel(new Request(
							CommonVars.getCurrUser()));
					cancelHead.setDeclareDate(jCalendarComboBox2.getDate());
					cancelHead.setCancelTimes(tfCancelTimes.getText());
					cancelHead.setDeclareTime(jTextField18.getText());
					cancelHead.setInputDate(jCalendarComboBox3.getDate());
					cancelHead.setInputUser(jTextField16.getText());
					cancelHead.setBeginDate(jCalendarComboBox.getDate());
					cancelHead.setEndDate(jCalendarComboBox1.getDate());
					cancelHead
							.setOutportExgUseImgNum(formatBig(jFormattedTextField
									.getValue()));
					cancelHead.setNote(jTextField5.getText());
					cancelHead.setInnerCancelMoney(formatBig(tfInnerCancelMoney
							.getValue()));
					cancelHead.setBeginImgMoney(formatBig(jFormattedTextField2
							.getValue()));

					// 计算本期进口总金额
					cancelHead.setThisInportMoney(formatBig(jTextField8
							.getValue()));
					// 计算本期出口总金额
					cancelHead.setThisOutportMoney(formatBig(jTextField13
							.getValue()));
					// 计算期末结余总金额
					cancelHead.setEndBalanceImgMoney(formatBig(jTextField9
							.getValue()));
					// 计算内销总金额
					cancelHead.setInnerCancelMoney(formatBig(tfInnerCancelMoney
							.getValue()));
					// 计算内销补税金额
					cancelHead
							.setInnerCancelFillTaxMoney(formatBig(jTextField10
									.getValue()));
					// 计算退运料件金额
					cancelHead.setExitImgMoney(formatBig(jTextField15
							.getValue()));
					// 余料结转出口
					cancelHead.setRemainExportMoney((formatBig(tfOverImgExport
							.getValue())));
					// 比例
					// cancelHead.setScale((formatBig(tfScale
					// .getValue())));
					// 比例 =（出口成品耗用总金额/本期出口总金额）*100
					if ((formatBig(jFormattedTextField.getValue())) != 0) {
						double Proportion = 0;
						Proportion = (formatBig(jFormattedTextField.getValue()))
								/ (formatBig(jTextField13.getValue())) * 100;
						cancelHead.setScale((formatBig(Proportion)));
					} else {
						cancelHead.setScale(0.0);
					}
					tfScale.setText(CommonUtils.formatDoubleByDigit(
							cancelHead.getScale(), 5));
					cancelHead = (CancelCusHead) checkCancelAction
							.saveCancelHead(
									new Request(CommonVars.getCurrUser()),
									cancelHead);
					// 财务审核人员
					cancelHead
							.setCheckStaffName(tfCheckStaffName.getText() == null ? ""
									: tfCheckStaffName.getText());
					// 财务审核时间
					cancelHead.setCheckStaffTime(new Date());
					// 一下为模拟数据
					// 期初料件总金额
					cancelHead
							.setSimulateBeginImgMoney(formatBig(jFormattedTextField21
									.getValue()));
					// 本期出口总金额
					cancelHead
							.setSimulateThisOutportMoney(formatBig(jTextField131
									.getValue()));
					// 内销金额
					cancelHead
							.setSimulateInnerCancelMoney(formatBig(tfInnerCancelMoney1
									.getValue()));
					// 退运料件金额
					cancelHead.setSimulateExitImgMoney(formatBig(jTextField151
							.getValue()));
					// 本期进口总金额
					cancelHead
							.setSimulateThisInportMoney(formatBig(jTextField81
									.getValue()));
					// 期末结余料件总金额
					cancelHead
							.setSimulateEndBalanceImgMoney(formatBig(jTextField91
									.getValue()));
					// 出口成品耗用总金额
					cancelHead
							.setSimulateOutportExgUseImgNum(formatBig(jFormattedTextField2
									.getValue()));
					// 余料结转出口
					// 内销补税金额
					cancelHead
							.setInnerCancelFillTaxMoney(formatBig(jTextField10
									.getValue()));
					cancelHead
							.setSimulateOutportExgUseImgNum(formatBig(jFormattedTextField1
									.getValue()));
					tableModel.updateRow(cancelHead);
					dataState = DataState.BROWSE;
					setState();
				}
			});

		}
		return jButton1;
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
			jButton2.setBounds(54, 561, 115, 25);
			jButton2.setText("计算核销表头");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkCancelAuthorityAction
							.dataCancelCancelData(new Request(CommonVars
									.getCurrUser()));
					/*
					 * if (JOptionPane.showConfirmDialog(DgCancel.this,
					 * "核销计算将首先清空现有报核成品,料件的内容,\n然后根据报关单和帐册内容重新计算。\n\n确定继续吗?",
					 * "提示信息", 0) == 0) {
					 */
					new CalCancelHead().start();
				}
			});

		}
		return jButton2;
	}

	class CalCancelHead extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(DgCancelCus.this);
				CommonProgress.setMessage("系统正在核销计算，请稍后...");
				cancelHead = (CancelCusHead) checkCancelAction
						.fillCancelHeadData(
								new Request(CommonVars.getCurrUser()),
								cancelHead, false);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgCancelCus.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				tableModel.updateRow(cancelHead);
				fillWindow();
				/*
				 * tfScale.setText(cancelHead.getScale().toString()); Double
				 * OutportExgUseImgNum = new Double(0); Double OutportMoney =
				 * new Double(0); Double scale =new Double(0);
				 * if(cancelHead.getOutportExgUseImgNum()!=null){
				 * OutportExgUseImgNum = cancelHead.getOutportExgUseImgNum(); }
				 * if(cancelHead.getThisOutportMoney()!=null){ OutportMoney =
				 * cancelHead.getThisOutportMoney(); }
				 * if(!OutportExgUseImgNum.equals
				 * (0.0)||!OutportMoney.equals(0.0)){ MathContext mc = new
				 * MathContext(5, RoundingMode.HALF_DOWN);
				 * //精度为5，舍入模式为大于0.5进1，否则舍弃。 BigDecimal a = new
				 * BigDecimal(OutportMoney); BigDecimal b = new
				 * BigDecimal(OutportMoney); System.out.println(a.divide(b,mc));
				 * scale= a.divide(b,mc).doubleValue(); }
				 * tfScale.setText(scale.toString());//比例=出口成品耗用总金额/本期出口总金额）*100
				 * // }
				 */}
		}
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
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(182, 560, 61, 25);
			jButton3.setText("打印");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cancelHead != null
							&& cancelHead.getDeclareType().equals(
									RrportDelcareType.BEGINDELCARE)) {// 预报核
						List list = checkCancelAction.getCustomsIEToTemp(
								new Request(CommonVars.getCurrUser()),
								cancelHead, false);
						if (list == null && list.size() < 0) {
							JOptionPane.showMessageDialog(DgCancelCus.this,
									"没有可打印的记录！", "提示", 2);
							return;
						}
						Company company = (Company) CommonVars.getCurrUser()
								.getCompany();
						CommonDataSource imgExgDS = new CommonDataSource(list);
						List headlist = new Vector(); // 只有一条记录
						headlist.add(cancelHead);
						CommonDataSource companyDS = new CommonDataSource(
								headlist);
						InputStream masterReportStream = DgCancelCus.class
								.getResourceAsStream("report/cancelYReport.jasper");
						InputStream detailReportStream = DgCancelCus.class
								.getResourceAsStream("report/cancelYReportSub.jasper");
						try {
							JasperReport detailReport = (JasperReport) JRLoader
									.loadObject(detailReportStream);
							Map<String, Object> parameters = new HashMap<String, Object>();
							parameters.put("name", company.getName());
							parameters.put("code", company.getCode());
							parameters.put("imgExgDS", imgExgDS);// 子数据源
							parameters.put("detailReport", detailReport);// 子报表
							JasperPrint jasperPrint;
							jasperPrint = JasperFillManager.fillReport(
									masterReportStream, parameters, companyDS);
							DgReportViewer viewer = new DgReportViewer(
									jasperPrint);
							viewer.setVisible(true);
						} catch (JRException e1) {
							e1.printStackTrace();
						}
					} else {// 正式报核
						List imgExgs = checkCancelAction.findCancelImgResult(
								new Request(CommonVars.getCurrUser()),
								cancelHead, false); // 表体
						Company company = (Company) CommonVars.getCurrUser()
								.getCompany();
						CommonDataSource imgExgDS = new CommonDataSource(
								imgExgs);
						List headlist = new Vector();
						headlist.add(cancelHead);
						CommonDataSource companyDS = new CommonDataSource(
								headlist);
						InputStream masterReportStream = DgCancelCus.class
								.getResourceAsStream("report/cancelReport.jasper");
						InputStream detailReportStream = DgCancelCus.class
								.getResourceAsStream("report/cancelReportSub.jasper");
						try {
							JasperReport detailReport = (JasperReport) JRLoader
									.loadObject(detailReportStream);
							Map<String, Object> parameters = new HashMap<String, Object>();
							parameters.put("name", company.getName());
							parameters.put("code", company.getCode());
							parameters.put("imgExgDS", imgExgDS);// 子数据源
							parameters.put("detailReport", detailReport);// 子报表

							parameters.put("tel", company.getTel());
							parameters.put("owner", company.getOwner());
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
		return jButton3;
	}

	/**
	 * 
	 * This method initializes defaultFormatterFactory
	 * 
	 * 
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 * 
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * 
	 * This method initializes numberFormatter
	 * 
	 * 
	 * 
	 * @return javax.swing.text.NumberFormatter
	 * 
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
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
			jToolBar.add(getJButton4());
			jToolBar.add(getJButton5());
			jToolBar.add(getJButton6());
		}
		return jToolBar;
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
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("新增"); // 新增报关单
			jButton4.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkCancelAuthorityAction
							.dataCancelAddCustoms(new Request(CommonVars
									.getCurrUser()));
					// List list = (List) CommonQuery.getInstance()
					// .getCustomsDeclara(false, cancelHead, false);
					DgQueryCustomsDeclara dg = new DgQueryCustomsDeclara(false,
							cancelHead, false);
					dg.setVisible(true);
					List list = dg.getSelectList();
					if (list == null || list.isEmpty())
						return;
					for (int i = 0; i < list.size(); i++) {
						cancelCustomsDeclara = (CancelCusCustomsDeclara) list
								.get(i);
						cancelCustomsDeclara.setCancelHead(cancelHead);
						cancelCustomsDeclara.setCompany(CommonVars
								.getCurrUser().getCompany());
						cancelCustomsDeclara = (CancelCusCustomsDeclara) checkCancelAction
								.saveCancelCustomsDeclara(new Request(
										CommonVars.getCurrUser()),
										cancelCustomsDeclara);
						tableModelCustom.addRow(cancelCustomsDeclara);

					}

					modityHead();
				}
			});

		}
		return jButton4;
	}

	/**
	 * 
	 * This method initializes jButton5
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("移除");
			jButton5.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkCancelAuthorityAction
							.dataCancelDeleteCustoms(new Request(CommonVars
									.getCurrUser()));
					if (tableModelCustom.getCurrentRows() == null) {
						JOptionPane.showMessageDialog(DgCancelCus.this,
								"请选择你要移除的资料", "确认", 2);
						return;
					}
					List list = tableModelCustom.getCurrentRows();
					if (JOptionPane.showConfirmDialog(DgCancelCus.this,
							"你确定要移除此记录吗?", "确认", 0) == 0) {
						for (int i = 0; i < list.size(); i++) {
							CancelCusCustomsDeclara cancelCustoms = (CancelCusCustomsDeclara) list
									.get(i);
							checkCancelAction.deleteCancelCustomsDeclara(
									new Request(CommonVars.getCurrUser()),
									cancelCustoms);

						}
						modityHead();
						tableModelCustom.deleteRows(list);
					}

				}
			});

		}
		return jButton5;
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
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setText("获取核销报关单");
			jButton6.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkCancelAuthorityAction
							.dataCancelGetCustoms(new Request(CommonVars
									.getCurrUser()));
					if (JOptionPane.showConfirmDialog(DgCancelCus.this,
							"获取核销报关单将清空现有报关单,确定获取核销报关单吗?", "确认", 0) == 0) {
						new getCustoms().start();
					}
				}
			});

		}
		return jButton6;
	}

	class getCustoms extends Thread {

		public void run() {
			List list = new ArrayList();
			try {
				CommonProgress.showProgressDialog(DgCancelCus.this);
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				checkCancelAction.getCustomsToCheckHead(
						new Request(CommonVars.getCurrUser()), cancelHead,
						false);
				list = checkCancelAction.findCancelCustomsDeclara(new Request(
						CommonVars.getCurrUser()), cancelHead, false);

				modityHead();
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgCancelCus.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				initTableCustom(list);
			}
		}
	}

	private void modityHead() {
		cancelHead = (CancelCusHead) checkCancelAction.modityCancelHead(
				new Request(CommonVars.getCurrUser()), cancelHead, false);
		fillWindow();
		tableModel.updateRow(cancelHead);
	}

	/**
	 * 
	 * This method initializes jTableCustom
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTableCustom() {
		if (jTableCustom == null) {
			jTableCustom = new JTable();
			jTableCustom.setRowHeight(25);
			jTableCustom.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (tableModelCustom == null)
						return;
					if (tableModelCustom.getCurrentRow() == null)
						return;
					if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_L) {
						CancelCustomsDeclara obj = (CancelCustomsDeclara) tableModelCustom
								.getCurrentRow();
						if (obj.getInOutportFlat() == null
								|| obj.getInOutportFlat().equals("")) {
							// cancelHead.setInportCustomNum(cancelHead.getInportCustomNum()+1);
							obj.setInOutportFlat("I");
						} else if (obj.getInOutportFlat() != null
								&& obj.getInOutportFlat().equals("I")) {
							/*
							 * cancelHead.setInportCustomNum(cancelHead.
							 * getInportCustomNum () - 1);
							 * cancelHead.setOutportCustomNum(cancelHead
							 * .getOutportCustomNum() + 1);
							 */
							obj.setInOutportFlat("E");
						} else if (obj.getInOutportFlat() != null
								&& obj.getInOutportFlat().equals("E")) {
							/*
							 * cancelHead.setInportCustomNum(cancelHead.
							 * getInportCustomNum () + 1);
							 * cancelHead.setOutportCustomNum(cancelHead
							 * .getOutportCustomNum() - 1);
							 */
							obj.setInOutportFlat("I");
						}
						/*
						 * cancelHead = (CancelCusHead)
						 * checkCancelAction.saveCancelHead(new
						 * Request(CommonVars.getCurrUser()),cancelHead);
						 * fillWindow(); tableModel.updateRow(cancelHead);
						 */
						obj = checkCancelAction.saveCancelCustomsDeclara(
								new Request(CommonVars.getCurrUser()), obj);
						tableModelCustom.updateRow(obj);
						modityHead();
					}
				}
			});
		}
		return jTableCustom;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTableCustom());
		}
		return jScrollPane;
	}

	private void setState() {
		btnExcImport.setEnabled(cancelHead.getDeclareType().equals(
				RrportDelcareType.DELCARE)
				&& (dataState != DataState.READONLY));

		jButton2.setEnabled(cancelHead.getDeclareType().equals(
				RrportDelcareType.DELCARE)
				&& (dataState != DataState.READONLY));
		jButton.setEnabled(dataState == DataState.BROWSE
				&& (dataState != DataState.READONLY));
		jButton14.setEnabled(dataState != DataState.BROWSE
				&& (dataState != DataState.READONLY));
		this.jTabbedPane.setEnabled(dataState == DataState.BROWSE
				|| dataState == DataState.READONLY);

		jButton4.setEnabled(!cancelHead.getDeclareType().equals(
				RrportDelcareType.DELCARE)
				&& dataState != DataState.READONLY);

		jButton5.setEnabled(!cancelHead.getDeclareType().equals(
				RrportDelcareType.DELCARE)
				&& dataState != DataState.READONLY);
		jButton6.setEnabled(!cancelHead.getDeclareType().equals(
				RrportDelcareType.DELCARE)
				&& dataState != DataState.READONLY);

		jButton1.setEnabled(dataState != DataState.BROWSE
				&& (dataState != DataState.READONLY));
		jCalendarComboBox.setEnabled(dataState != DataState.BROWSE
				&& (dataState != DataState.READONLY));
		jCalendarComboBox1.setEnabled(dataState != DataState.BROWSE
				&& (dataState != DataState.READONLY));

		tfCancelTimes.setEditable(dataState != DataState.BROWSE
				&& (dataState != DataState.READONLY));
		jTextField5.setEditable(dataState != DataState.BROWSE
				&& (dataState != DataState.READONLY));
		jTextField8.setEditable(dataState != DataState.BROWSE
				&& (dataState != DataState.READONLY));
		jTextField13.setEditable(dataState != DataState.BROWSE
				&& (dataState != DataState.READONLY));
		jTextField9.setEditable(dataState != DataState.BROWSE
				&& (dataState != DataState.READONLY));

		jTextField10.setEditable(dataState != DataState.BROWSE
				&& (dataState != DataState.READONLY));
		jTextField15.setEditable(dataState != DataState.BROWSE
				&& (dataState != DataState.READONLY));

		jCalendarComboBox3.setEnabled(dataState != DataState.BROWSE
				&& (dataState != DataState.READONLY));
		jCalendarComboBox2.setEnabled(dataState != DataState.BROWSE
				&& (dataState != DataState.READONLY));
		jTextField16.setEditable(dataState != DataState.BROWSE
				&& (dataState != DataState.READONLY));
		jTextField18.setEditable(dataState != DataState.BROWSE
				&& (dataState != DataState.READONLY));
		tfInnerCancelMoney.setEditable(dataState != DataState.BROWSE
				&& (dataState != DataState.READONLY));
		jFormattedTextField.setEditable(dataState != DataState.BROWSE
				&& (dataState != DataState.READONLY));
		jFormattedTextField2.setEditable(dataState != DataState.BROWSE
				&& (dataState != DataState.READONLY));

		jButton10.setEnabled(jTabbedPane2.getSelectedIndex() == 0
				&& (dataState != DataState.READONLY));

		jButton12.setEnabled(jTabbedPane2.getSelectedIndex() == 0
				&& (dataState != DataState.READONLY));

		jButton11.setEnabled(jTabbedPane2.getSelectedIndex() == 0
				&& (dataState != DataState.READONLY));
		jButton8.setEnabled(jTabbedPane1.getSelectedIndex() == 0
				&& (dataState != DataState.READONLY));

		jButton13.setEnabled(jTabbedPane1.getSelectedIndex() == 0
				&& (dataState != DataState.READONLY));

		jButton9.setEnabled(jTabbedPane1.getSelectedIndex() == 0
				&& (dataState != DataState.READONLY));
		tfOverImgExport.setEditable(dataState != DataState.BROWSE
				&& (dataState != DataState.READONLY));

	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
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
			jButton7.setBounds(501, 561, 59, 25);
			jButton7.setText("关闭");
			jButton7.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgCancelCus.this.dispose();
				}
			});

		}
		return jButton7;
	}

	/**
	 * 
	 * This method initializes jToolBar1
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getJButton8());
			jToolBar1.add(getJButton13());
			jToolBar1.add(getJButton9());
		}
		return jToolBar1;
	}

	/**
	 * 
	 * This method initializes jButton8
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton8() {
		if (jButton8 == null) {
			jButton8 = new JButton();
			jButton8.setText("修改");
			jButton8.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkCancelAuthorityAction.dataCancelModityCp(new Request(
							CommonVars.getCurrUser()));
					exgedit();
				}
			});

		}
		return jButton8;
	}

	private void exgedit() {
		if (tableModelExgResult.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(DgCancelCus.this, "请选择你将要修改的记录",
					"提示！", 2);
			return;
		}
		DgCancelCusExg dgCancelExg = new DgCancelCusExg();
		CancelCusExgResult exg = (CancelCusExgResult) tableModelExgResult
				.getCurrentRow();
		dgCancelExg.setCancelExg(exg);
		dgCancelExg.setTableModel(tableModelExgResult);
		dgCancelExg.setVisible(true);
	}

	/**
	 * 
	 * This method initializes jButton9
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton9() {
		if (jButton9 == null) {
			jButton9 = new JButton();
			jButton9.setText("计算核销成品表"); // 获取成品
			jButton9.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkCancelAuthorityAction.dataCancelGetCp(new Request(
							CommonVars.getCurrUser()));
					if (JOptionPane.showConfirmDialog(DgCancelCus.this,
							"获取报核成品将清空现有报核成品，确定获取报核成品吗？", "确认", 0) == 0) {
						new FindExg().execute();
					}
				}
			});
		}
		return jButton9;
	}

	class FindExg extends SwingWorker {
		@Override
		protected Object doInBackground() throws Exception {
			try {
				CommonProgress.showProgressDialog(DgCancelCus.this);
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				checkCancelAction.deleteCancelExg(
						new Request(CommonVars.getCurrUser()), cancelHead,
						false);
				tableModelExgBefore.getList().clear();
				checkCancelAction.getExg(new Request(CommonVars.getCurrUser()),
						cancelHead, false); // 得到中间成品数据

				List emsH2kList = null;
				emsH2kList = manualDeclearAction
						.findEmsHeadH2kInExecuting(new Request(CommonVars
								.getCurrUser()));
				if (emsH2kList != null && emsH2kList.size() > 0) {
					EmsHeadH2k emsH2k = (EmsHeadH2k) emsH2kList.get(0);
					checkCancelAction.getCancelExg(
							new Request(CommonVars.getCurrUser()), cancelHead,
							emsH2k, false);
				}
				initExg();
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgCancelCus.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			}
			return null;
		}
	}

	/**
	 * 
	 * This method initializes jTabbedPane1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTabbedPane
	 * 
	 */
	private JTabbedPane getJTabbedPane1() {
		if (jTabbedPane1 == null) {
			jTabbedPane1 = new JTabbedPane();
			jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane1.addTab("核算结果", null, getJPanel6(), null);
			jTabbedPane1.addTab("中间过程", null, getJPanel8(), null);
			jTabbedPane1
					.addChangeListener(new javax.swing.event.ChangeListener() {

						public void stateChanged(javax.swing.event.ChangeEvent e) {

							setState();

						}
					});

		}
		return jTabbedPane1;
	}

	/**
	 * 
	 * This method initializes jPanel6
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setLayout(new BorderLayout());
			jPanel6.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel6;
	}

	/**
	 * 
	 * This method initializes jPanel8
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jPanel8 = new JPanel();
			jPanel8.setLayout(new BorderLayout());
			jPanel8.add(getJScrollPane4(), java.awt.BorderLayout.CENTER);
		}
		return jPanel8;
	}

	/**
	 * 
	 * This method initializes jToolBar2
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			jToolBar2 = new JToolBar();
			jToolBar2.add(getJButton10());
			jToolBar2.add(getJButton12());
			jToolBar2.add(getJButton11());
			jToolBar2.add(getCbUnitWear());
			jToolBar2.add(getExcBtn());

		}
		return jToolBar2;
	}

	/**
	 * 获取 料件 导入单价
	 * 
	 * @return
	 */
	private JButton getExcBtn() {

		if (btnExcImport == null) {

			btnExcImport = new JButton("批量导入内购数");

			btnExcImport.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					DgCancelImgByInnerBuyImport byInnerBuyImport = new DgCancelImgByInnerBuyImport();

					byInnerBuyImport.setImgCancelResults(tableModelImgResult
							.getList());

					byInnerBuyImport.setVisible(true);
				}
			});
		}
		return btnExcImport;

	}

	/**
	 * 
	 * This method initializes jButton10
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton10() {
		if (jButton10 == null) {
			jButton10 = new JButton();
			jButton10.setText("修改");
			jButton10.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkCancelAuthorityAction.dataCancelModityLj(new Request(
							CommonVars.getCurrUser()));
					imgedit();

				}
			});

		}
		return jButton10;
	}

	/**
	 * 
	 * This method initializes jButton10
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtOk() {
		if (btOk == null) {
			btOk = new JButton();
			btOk.setText("重新计算核销表头");
			btOk.setToolTipText("重新计算核销表头金额");
			btOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					/*
					 * if (tableModelPrice.getCurrentRow() == null) { return; }
					 * CancelImgResult cr = (CancelImgResult) tableModelPrice
					 * .getCurrentRow(); // 手调单价 Double selfPrice =
					 * Double.valueOf(ftfPrice.getValue() .toString());
					 * cr.setSelfPrice(selfPrice);
					 * tableModelPrice.updateRow(cr);
					 * 
					 * // 根据手调单价调整
					 * 
					 * // 应剩余金额,先减去原来的
					 * cr.setLeaveSumPrice(CommonUtils.getDoubleExceptNull(cr
					 * .getLeaveSumPrice()) - CommonUtils.getDoubleExceptNull(cr
					 * .getUseSumPrice()));
					 * 
					 * // 消耗金额
					 * cr.setUseSumPrice(CommonUtils.getDoubleExceptNull(cr
					 * .getUseSumNum()) selfPrice); // 应剩余金额,加上新的
					 * cr.setLeaveSumPrice(CommonUtils.getDoubleExceptNull(cr
					 * .getLeaveSumPrice()) + CommonUtils.getDoubleExceptNull(cr
					 * .getUseSumPrice())); // 结余金额=应剩余金额
					 * cr.setResultSumPrice(CommonUtils.getDoubleExceptNull(cr
					 * .getLeaveSumPrice()));
					 * 
					 * checkCancelAction.saveCancelImgResult(new Request(
					 * CommonVars.getCurrUser()), cr);
					 * 
					 * tableModelPrice.updateRow(cr);
					 * tableModelImgResult.updateRow(cr);
					 */
					// 重新计算表头
					new CalCancelHead().start();
				}
			});

		}
		return btOk;
	}

	private void imgedit() {
		if (tableModelImgResult.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(DgCancelCus.this, "请选择你将要修改的记录",
					"提示！", 2);
			return;
		}
		DgCancelCusImg dgCancelImg = new DgCancelCusImg();
		dgCancelImg.setTableModel(tableModelImgResult);
		dgCancelImg.setVisible(true);
	}

	private JPopupMenu getPMenu() {
		if (pMenu == null) {
			pMenu = new JPopupMenu();
			pMenu.add(getMenu());
			pMenu.add(getMenu1());
		}
		return pMenu;
	}

	private JMenu getMenu() {
		if (menu == null) {
			menu = new JMenu();
			menu.setEnabled(isEmsCancelAvgPrice);
			menu.add(getItem1());
			menu.add(getItem4());
			menu.add(getItem3());
			menu.setText("报核周期每月平均单价");
		}
		return menu;
	}

	private JMenu getMenu1() {
		if (menu1 == null) {
			menu1 = new JMenu();
			menu1.setEnabled(isEmsCancelAvgTatolPrice);
			menu1.add(getItem2());
			menu1.setText("报核周期总平均单价");
		}
		return menu1;
	}

	private JMenuItem getItem1() {
		if (item1 == null) {
			item1 = new JMenuItem();
			item1.setText("步－：计算每月平均单价");
			item1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new FindImgAveragePrice().execute();

				}
			});
		}
		return item1;
	}

	private JMenuItem getItem4() {
		if (item4 == null) {
			item4 = new JMenuItem();
			item4.setText("步二：计算报核料件表");
			item4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					listAvgPrice = checkCancelAction
							.findCancelImgAvgPriceLeave(
									new Request(CommonVars.getCurrUser()),
									cancelHead, false, null, null);
					if (listAvgPrice != null && listAvgPrice.size() > 0) {
						checkCancelAuthorityAction.dataCancelGetLj(new Request(
								CommonVars.getCurrUser()));
						if (cbUnitWear.isSelected()) {
							if (JOptionPane.showConfirmDialog(DgCancelCus.this,
									"选取[计算单耗表]选项请要耐心等待？", "确认", 0) == 1) {
								return;
							}
						}
						if (JOptionPane.showConfirmDialog(DgCancelCus.this,
								"获取报核料件将清空现有报核料件，确定获取报核料件吗？", "确认", 0) == 0) {
							new FindImgByMonth().execute();

						}

					} else {
						JOptionPane.showMessageDialog(DgCancelCus.this,
								"请先计算每月平均单价", "提示", 2);
						return;
					}
				}
			});
		}
		return item4;
	}

	private JMenuItem getItem3() {
		if (item3 == null) {
			item3 = new JMenuItem();
			item3.setText("查看每月平均单价");
			item3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgCancelCusImgAvgPrice dgCancelPrice = new DgCancelCusImgAvgPrice();
					dgCancelPrice.setIsOwner(false);
					dgCancelPrice.setCancelHead(cancelHead);
					dgCancelPrice.setVisible(true);

				}
			});
		}
		return item3;
	}

	private JMenuItem getItem2() {
		if (item2 == null) {
			item2 = new JMenuItem();
			item2.setText("计算报核料件表");
			item2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkCancelAuthorityAction.dataCancelGetLj(new Request(
							CommonVars.getCurrUser()));
					if (cbUnitWear.isSelected()) {
						if (JOptionPane.showConfirmDialog(DgCancelCus.this,
								"选取[计算单耗表]选项请要耐心等待？", "确认", 0) == 1) {
							return;
						}
					}
					if (JOptionPane.showConfirmDialog(DgCancelCus.this,
							"获取报核料件将清空现有报核料件，确定获取报核料件吗？", "确认", 0) == 0) {
						new FindImg().execute();

					}

				}
			});
		}
		return item2;
	}

	/**
	 * 
	 * This method initializes jButton11
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton11() {
		if (jButton11 == null) {
			jButton11 = new JButton();
			jButton11.setText("计算核销料件表"); // 获取料件
			jButton11.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					Component comp = (Component) e.getSource();
					getPMenu().show(comp, 0, comp.getHeight());
				}
			});
		}
		return jButton11;
	}

	class FindImgAveragePrice extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			List ls = new ArrayList();
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				// 删除每月平均单价表
				CommonStepProgress.setStepMessage("删除每月平均单价表");
				checkCancelAction.deleteCancelImgAvgPrice(new Request(
						CommonVars.getCurrUser()), cancelHead, false);
				List emsH2kList = null;
				emsH2kList = manualDeclearAction
						.findEmsHeadH2kInExecuting(new Request(CommonVars
								.getCurrUser()));
				if (emsH2kList != null && emsH2kList.size() > 0) {
					Request request = new Request(CommonVars.getCurrUser());
					request.setTaskId(taskId);
					EmsHeadH2k emsH2k = (EmsHeadH2k) emsH2kList.get(0);
					listAvgPrice = checkCancelAction.getImgAveragePriceByMonth(
							request, cancelHead, emsH2k, false);
				}
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(DgCancelCus.this, "计算成功！", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(DgCancelCus.this,
						"获取数据失败：！" + e.getMessage(), "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
			return ls;
		}
	}

	class FindImgByMonth extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			List ls = new ArrayList();
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				CommonStepProgress.setStepMessage(" -- 开始删除数据");
				checkCancelAction.deleteCancelImg(
						new Request(CommonVars.getCurrUser()), cancelHead,
						false);
				// 删除单耗表
				checkCancelAction.deleteCancelUnitWear(
						new Request(CommonVars.getCurrUser()), cancelHead,
						false);
				CommonStepProgress.setStepMessage(" -- 删除完毕！");
				tableModelImgBefore.getList().clear();
				checkCancelAction.getImg(new Request(CommonVars.getCurrUser()),
						cancelHead, false); // 得到中间料件数据
				CommonStepProgress.setStepMessage(" -- 获得中间料件数据！");
				List emsH2kList = null;
				emsH2kList = manualDeclearAction
						.findEmsHeadH2kInExecuting(new Request(CommonVars
								.getCurrUser()));
				if (emsH2kList != null && emsH2kList.size() > 0) {
					EmsHeadH2k emsH2k = (EmsHeadH2k) emsH2kList.get(0);
					Request request = new Request(CommonVars.getCurrUser());
					request.setTaskId(taskId);
					ls = checkCancelAction.getCancelImgByMonth(request,
							cancelHead, emsH2k, false, cbUnitWear.isSelected());
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(DgCancelCus.this, "计算成功！",
							"提示", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(DgCancelCus.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			}
			return ls;
		}

		@Override
		protected void done() {
			List list = null;
			try {
				list = (List) this.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			if (list == null) {
				list = new ArrayList();
			}
			if (list.get(1) != null) {
				initTableUnitWear((List) list.get(1));
			} else {
				initTableUnitWear(new Vector());
			}
			CommonProgress.closeProgressDialog();
			initImg();

		}
	}

	class FindImg extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			List ls = new ArrayList();
			jButton11.setEnabled(false);
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				System.out.println(" -- 开始删除数据");
				CommonStepProgress.setStepMessage("系统正在删除数据");
				checkCancelAction.deleteCancelImg(
						new Request(CommonVars.getCurrUser()), cancelHead,
						false);
				checkCancelAction.deleteCancelUnitWear(
						new Request(CommonVars.getCurrUser()), cancelHead,
						false);
				System.out.println(" -- 删除完毕！");
				CommonStepProgress.setStepMessage("系统删除完毕");
				tableModelImgBefore.getList().clear();
				CommonStepProgress.setStepMessage("系统正在获得中间料件数据");
				checkCancelAction.getImg(new Request(CommonVars.getCurrUser()),
						cancelHead, false); // 得到中间料件数据
				System.out.println(" -- 获得中间料件数据！");
				List emsH2kList = null;
				emsH2kList = manualDeclearAction
						.findEmsHeadH2kInExecuting(new Request(CommonVars
								.getCurrUser()));
				if (emsH2kList != null && emsH2kList.size() > 0) {
					CommonStepProgress.setStepMessage("系统正在计算核销料件数据");
					EmsHeadH2k emsH2k = (EmsHeadH2k) emsH2kList.get(0);
					checkCancelAction.getCancelImg(
							new Request(CommonVars.getCurrUser()), cancelHead,
							emsH2k, false, cbUnitWear.isSelected());

				}
			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(DgCancelCus.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				jButton11.setEnabled(true);
			}
			return ls;
		}

		@Override
		protected void done() {
			CommonStepProgress.closeStepProgressDialog();
			JOptionPane.showMessageDialog(DgCancelCus.this, "料件核销计算成功！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			initImg();
			initPrice();
			pnCommonQueryPage.setInitState();

		}
	}

	/**
	 * 
	 * This method initializes jTabbedPane2
	 * 
	 * 
	 * 
	 * @return javax.swing.JTabbedPane
	 * 
	 */
	private JTabbedPane getJTabbedPane2() {
		if (jTabbedPane2 == null) {
			jTabbedPane2 = new JTabbedPane();
			jTabbedPane2.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane2.addTab("核算结果", null, getJPanel9(), null);
			jTabbedPane2.addTab("中间过程", null, getJPanel11(), null);
			jTabbedPane2
					.addChangeListener(new javax.swing.event.ChangeListener() {

						public void stateChanged(javax.swing.event.ChangeEvent e) {
							setState();
						}
					});

		}
		return jTabbedPane2;
	}

	/**
	 * 
	 * This method initializes jPanel9
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel9() {
		if (jPanel9 == null) {
			jPanel9 = new JPanel();
			jPanel9.setLayout(new BorderLayout());
			jPanel9.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel9;
	}

	/**
	 * 
	 * This method initializes jPanel11
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jPanel11 = new JPanel();
			jPanel11.setLayout(new BorderLayout());
			jPanel11.add(getJScrollPane3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel11;
	}

	/**
	 * 
	 * This method initializes jTableImgResult
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTableImgResult() {
		if (jTableImgResult == null) {
			jTableImgResult = new JTable();
			jTableImgResult.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {
						imgedit();
					}
				}
			});
		}
		return jTableImgResult;
	}

	/**
	 * 
	 * This method initializes jScrollPane1
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JFooterScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JFooterScrollPane();
			jScrollPane1.setViewportView(getJTableImgResult());
		}
		return jScrollPane1;
	}

	/**
	 * 
	 * This method initializes jTableExgResult
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTableExgResult() {
		if (jTableExgResult == null) {
			jTableExgResult = new JTable();
			jTableExgResult.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {
						exgedit();
					}
				}
			});
		}
		return jTableExgResult;
	}

	/**
	 * 
	 * This method initializes jScrollPane2
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JFooterScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JFooterScrollPane();
			jScrollPane2.setViewportView(getJTableExgResult());
		}
		return jScrollPane2;
	}

	/**
	 * 
	 * This method initializes jTableImgBefore
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTableImgBefore() {
		if (jTableImgBefore == null) {
			jTableImgBefore = new JTable();
			jTableImgBefore.setRowHeight(25);
		}
		return jTableImgBefore;
	}

	/**
	 * 
	 * This method initializes jScrollPane3
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getJTableImgBefore());
		}
		return jScrollPane3;
	}

	/**
	 * 
	 * This method initializes jTableExgBefore
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTableExgBefore() {
		if (jTableExgBefore == null) {
			jTableExgBefore = new JTable();
			jTableExgBefore.setRowHeight(25);
		}
		return jTableExgBefore;
	}

	/**
	 * 
	 * This method initializes jScrollPane4
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setViewportView(getJTableExgBefore());
		}
		return jScrollPane4;
	}

	/**
	 * This method initializes tfInnerCancelMoney
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfInnerCancelMoney() {
		if (tfInnerCancelMoney == null) {
			tfInnerCancelMoney = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfInnerCancelMoney.setBounds(new Rectangle(109, 75, 144, 22));
		}
		return tfInnerCancelMoney;
	}

	/**
	 * This method initializes jFormattedTextField2
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJFormattedTextField2() {
		if (jFormattedTextField2 == null) {
			jFormattedTextField2 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField2.setBounds(new Rectangle(109, 16, 144, 22));
		}
		return jFormattedTextField2;
	}

	/**
	 * This method initializes jCalendarComboBox2
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox2() {
		if (jCalendarComboBox2 == null) {
			jCalendarComboBox2 = new JCalendarComboBox();
			jCalendarComboBox2.setBounds(577, 88, 127, 23);
		}
		return jCalendarComboBox2;
	}

	/**
	 * This method initializes jCalendarComboBox3
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox3() {
		if (jCalendarComboBox3 == null) {
			jCalendarComboBox3 = new JCalendarComboBox();
			jCalendarComboBox3.setBounds(98, 145, 129, 21);
		}
		return jCalendarComboBox3;
	}

	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(new BorderLayout());
			jPanel7.add(this.getPnCommonQueryPage(),
					java.awt.BorderLayout.NORTH);
			jPanel7.add(getJScrollPane5(), java.awt.BorderLayout.CENTER);
		}
		return jPanel7;
	}

	/**
	 * This method initializes jScrollPane5
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane5() {
		if (jScrollPane5 == null) {
			jScrollPane5 = new JScrollPane();
			jScrollPane5.setViewportView(getJTable());
		}
		return jScrollPane5;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setRowHeight(25);
		}
		return jTable;
	}

	private JTableListModel initTableUnitWear(final List list) {

		tableModelUnitWear = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("成品序号", "cpSeqNum", 60,
								Integer.class));
						list.add(addColumn("版本号", "version", 60, Integer.class));
						list.add(addColumn("料件序号", "ljSeqNum", 60,
								Integer.class));
						list.add(addColumn("单耗", "unitWear", 100));
						list.add(addColumn("损耗", "wear", 100));
						list.add(addColumn("单位用量", "unitUse", 100));
						list.add(addColumn("出口数量", "cpUse", 100));
						list.add(addColumn("耗用量", "ljUse", 100));
						return list;
					}
				});
		return tableModelUnitWear;
	}

	private void initTablePrice(final List list) {

		tableModelPrice = new AttributiveCellTableModel(
				(MultiSpanCellTable) tbDanJia, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册序号", "emsSeqNum", 70,
								Integer.class));
						// 期初
						list.add(addColumn("数量", "beginNum", 70, Integer.class));
						list.add(addColumn("金额", "beginMoney", 70,
								Integer.class));
						// 本期
						list.add(addColumn("数量", "cancelNum", 100));
						list.add(addColumn("金额", "cancelMoney", 100));
						// 内购
						list.add(addColumn("数量", "inChinaBuyNum", 100));
						list.add(addColumn("金额", "inChinaBuyMoney", 100));

						list.add(addColumn("系统单价", "sysPrice", 100));
						list.add(addColumn("手调单价", "selfPrice", 100));
						return list;
					}
				});

		TableColumnModel cm = tbDanJia.getColumnModel();

		ColumnGroup gUse = new ColumnGroup("本期期初");
		gUse.add(cm.getColumn(2));
		gUse.add(cm.getColumn(3));

		ColumnGroup gInnerUse = new ColumnGroup("本期进口");
		gInnerUse.add(cm.getColumn(4));
		gInnerUse.add(cm.getColumn(5));

		ColumnGroup gLeftOver = new ColumnGroup("本期国内购买");
		gLeftOver.add(cm.getColumn(6));
		gLeftOver.add(cm.getColumn(7));

		GroupableTableHeader header = (GroupableTableHeader) tbDanJia
				.getTableHeader();

		header.addColumnGroup(gUse);
		header.addColumnGroup(gInnerUse);
		header.addColumnGroup(gLeftOver);

	}

	/**
	 * This method initializes jButton12
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton12() {
		if (jButton12 == null) {
			jButton12 = new JButton();
			jButton12.setText("移除");
			jButton12.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (JOptionPane.showConfirmDialog(DgCancelCus.this,
							"您确认要移除选择的料件吗？\n删除完毕后请点击表头【核销表头计算】。", "确认", 0) == 0) {
						List ls = tableModelImgResult.getCurrentRows();
						checkCancelAction.deleteCancelImgExgList(new Request(
								CommonVars.getCurrUser()), ls);
						tableModelImgResult.deleteRows(ls);
					}
				}
			});
		}
		return jButton12;
	}

	/**
	 * This method initializes jButton13
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton13() {
		if (jButton13 == null) {
			jButton13 = new JButton();
			jButton13.setText("移除");
			jButton13.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (JOptionPane.showConfirmDialog(DgCancelCus.this,
							"您确认要移除选择的成品吗？\n删除完毕后请点击表头【核销表头计算】。", "确认", 0) == 0) {
						List ls = tableModelExgResult.getCurrentRows();
						checkCancelAction.deleteCancelImgExgList(new Request(
								CommonVars.getCurrUser()), ls);
						tableModelExgResult.deleteRows(ls);
					}

				}
			});
		}
		return jButton13;
	}

	class InitTable extends SwingWorker {
		@Override
		protected Object doInBackground() throws Exception {
			List list = new ArrayList();
			try {
				getJTabbedPane().setEnabled(false);
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在获取数据，请稍候！");
				if (getJTabbedPane().getSelectedIndex() == 4) {
					if (tableModelUnitWear == null
							|| tableModelUnitWear.getList().isEmpty()) {
						// list = checkCancelAction.findCancelUnitWear(
						// new Request(CommonVars.getCurrUser()),
						// cancelHead, false);
						// if (list != null && list.size() > 0) {
						// initTableUnitWear(list);
						// } else {
						// initTableUnitWear(new Vector());
						// }
						pnCommonQueryPage.setInitState();
					}

				} else if (getJTabbedPane().getSelectedIndex() == 3) {
					if (tableModelExgBefore == null
							|| tableModelExgBefore.getList().isEmpty()) {
						initExg();
					}
				} else if (getJTabbedPane().getSelectedIndex() == 2) {
					if (tableModelImgResult == null
							|| tableModelImgResult.getList().isEmpty()) {
						initImg();
					}
				} else if (getJTabbedPane().getSelectedIndex() == 1) {
					if (tableModelCustom == null
							|| tableModelCustom.getList().isEmpty()) {
						list = checkCancelAction.findCancelCustomsDeclara(
								new Request(CommonVars.getCurrUser()),
								cancelHead, false);
						if (list != null && !list.isEmpty())
							initTableCustom(list);
						else
							initTableCustom(new Vector());
					}
				} else if (getJTabbedPane().getSelectedIndex() == 5) {
					initPrice();
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				getJTabbedPane().setEnabled(true);
				CommonProgress.closeProgressDialog();
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			getJTabbedPane().setEnabled(true);
			return null;
		}

	}

	/**
	 * This method initializes cbUnitWear
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbUnitWear() {
		if (cbUnitWear == null) {
			cbUnitWear = new JCheckBox();
			cbUnitWear.setText("计算料件同时计算单耗表");
		}
		return cbUnitWear;
	}

	/**
	 * 获得数据源
	 * 
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List getDataSource(int index, int length, String property,
			Object value, boolean isLike) {
		return checkCancelAction.findCancelUnitWear(
				new Request(CommonVars.getCurrUser()), cancelHead, false,
				index, length, property, value, isLike);
	}

	/**
	 * 公共查询组件
	 * 
	 * @return
	 */
	@SuppressWarnings("serial")
	private PnCommonQueryPage getPnCommonQueryPage() {
		if (pnCommonQueryPage == null) {
			pnCommonQueryPage = new PnCommonQueryPage() {

				@Override
				public JTableListModel initTable(List dataSource) {
					return DgCancelCus.this.initTableUnitWear(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					return DgCancelCus.this.getDataSource(index, length,
							property, value, isLike);
				}

			};
		}
		return pnCommonQueryPage;
	}

	/**
	 * This method initializes jPanel10
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel10() {
		if (jPanel10 == null) {
			jPanel10 = new JPanel();
			jPanel10.setLayout(new BorderLayout());
			jPanel10.add(getJPanel13(), BorderLayout.NORTH);
			jPanel10.add(getJPanel12(), BorderLayout.CENTER);
		}
		return jPanel10;
	}

	/**
	 * This method initializes jPanel12
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel12() {
		if (jPanel12 == null) {
			jPanel12 = new JPanel();
			jPanel12.setLayout(new BorderLayout());
			jPanel12.add(getJScrollPane6(), BorderLayout.CENTER);
		}
		return jPanel12;
	}

	/**
	 * This method initializes jPanel13
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel13() {
		if (jPanel13 == null) {
			jLabel31 = new JLabel();
			jLabel31.setText("修改单价");
			jLabel31.setToolTipText("输入单价点击【回车】，系统会自动计算料件表，重新关闭并打开核销申请表料件界面，可查看修改后金额！");
			jPanel13 = new JPanel();
			jPanel13.setLayout(new FlowLayout());
			jPanel13.add(jLabel31, null);
			jPanel13.add(getFtfPrice());
			jPanel13.add(getBtNext(), null);
			jPanel13.add(getBtOk());
			// jPanel13.add(getCbReCompute(), null);
		}
		return jPanel13;
	}

	/**
	 * 单价
	 * 
	 * @return
	 */
	private JFormattedTextField getFtfPrice() {
		if (ftfPrice == null) {
			ftfPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			ftfPrice.setColumns(8);
			ftfPrice.setBounds(104, 213, 91, 22);
			ftfPrice.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						int row = tbDanJia.getSelectedRow();
						if (row < 0) {
							row = 0;
							tableModelPrice.nextRow();
						}
						tableModelPrice.nextRow();
						CancelImgResult cr = (CancelCusImgResult) tableModelPrice
								.getDataByRow(row);
						/**
						 * HWY2013-7-12修改如下 【重新计算单价表】方法有误 在修改单价，按回车后，系统已经开始计算。
						 * 因计算料件表中，计算公式=核增金额-核减金额 +期初金额-（耗用数量）*平均单价
						 * 前面三个金额固定的，所以我们只需考虑耗用金额即可 1、 应剩余金额=应剩余金额 + 耗用总价值
						 * 【手调单价前耗用金额】 2、 手调单价后耗用金额=耗用数量 * 手调单价 3、
						 * 手调单价后应剩余金额=手调前应剩余金额 – 手调单价后耗用金额 4、 实际剩余金额 = 手调单价后剩余金额
						 */
						// 手调单价
						Double selfPrice = Double.valueOf(ftfPrice.getText()
								.toString());
						cr.setSelfPrice(selfPrice);
						// 根据手调单价调整
						// 应剩余金额,先加上原来的 应剩余金额=应剩余金额 + 耗用总价值 【手调单价前耗用金额】
						// 2015-3-30当应剩余金额小于0时，直接根据应剩余数量*平均单价
						cr.setLeaveSumPrice((CommonUtils.getDoubleExceptNull(cr
								.getLeaveSumPrice()) + CommonUtils
								.getDoubleExceptNull(cr.getUseSumPrice())) < Double
								.valueOf(0) ? cr.getLeaveNum() * selfPrice
								: CommonUtils.getDoubleExceptNull(cr
										.getLeaveSumPrice())
										+ CommonUtils.getDoubleExceptNull(cr
												.getUseSumPrice()));

						// 手调单价后耗用金额=耗用数量 * 手调单价
						cr.setUseSumPrice(CommonUtils.getDoubleExceptNull(cr
								.getUseSumNum()) * selfPrice);
						// 应剩余金额,手调单价后应剩余金额=手调前应剩余金额 – 手调单价后耗用金额
						cr.setLeaveSumPrice((CommonUtils.getDoubleExceptNull(cr
								.getLeaveSumPrice()) - CommonUtils
								.getDoubleExceptNull(cr.getUseSumPrice())) < Double
								.valueOf(0) ? cr.getLeaveNum() * selfPrice
								: CommonUtils.getDoubleExceptNull(cr
										.getLeaveSumPrice())
										- CommonUtils.getDoubleExceptNull(cr
												.getUseSumPrice()));
						cr.setResultSumPrice(CommonUtils.getDoubleExceptNull(cr
								.getLeaveSumPrice()));// 结余金额
						cr.setFactLeaveSumPrice(CommonUtils
								.getDoubleExceptNull(cr.getLeaveSumPrice()));// 实际剩余金额
						checkCancelAction.saveCancelImgResult(new Request(
								CommonVars.getCurrUser()), cr);
						ftfPrice.setValue(CommonUtils
								.getDoubleExceptNull(((CancelImgResult) tableModelPrice
										.getCurrentRow()).getSelfPrice()));
						ftfPrice.selectAll();
					}
				}
			});
			ftfPrice.setFocusTraversalKeysEnabled(false);
		}
		return ftfPrice;
	}

	/**
	 * This method initializes cbReCompute
	 * 
	 * @return javax.swing.JCheckBox
	 */
	JCheckBox getCbReCompute() {
		if (cbReCompute == null) {
			cbReCompute = new JCheckBox();
			cbReCompute.setText("重新计算表头、料件、成品价格");
		}
		return cbReCompute;
	}

	/**
	 * This method initializes jScrollPane6
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane6() {
		if (jScrollPane6 == null) {
			jScrollPane6 = new JScrollPane();
			jScrollPane6.setViewportView(getTbDanJia());
		}
		return jScrollPane6;
	}

	/**
	 * This method initializes tbDanJia
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbDanJia() {
		if (tbDanJia == null) {
			tbDanJia = new JTable();
		}
		tbDanJia.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (tableModelPrice != null
						&& tableModelPrice.getCurrentRow() != null) {
					CancelImgResult cr = (CancelImgResult) tableModelPrice
							.getCurrentRow();
					totalCount = tableModelPrice.getRowCount();
					rowCount = tableModelPrice.getCurrRowCount();
					ftfPrice.setValue(CommonUtils.getDoubleExceptNull(cr
							.getSelfPrice()));
				}
			}
		});
		return tbDanJia;
	}

	/**
	 * This method initializes jPanelReal
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelReal() {
		if (jPanelReal == null) {
			jLabel35 = new JLabel();
			jLabel35.setBounds(new Rectangle(338, 161, 103, 18));
			jLabel35.setText("财务审核时间");
			jLabel34 = new JLabel();
			jLabel34.setBounds(new Rectangle(12, 160, 92, 18));
			jLabel34.setText("财务审核人员");
			jLabel33 = new JLabel();
			jLabel33.setBounds(new Rectangle(264, 133, 50, 18));
			jLabel33.setText("%");
			jLabel32 = new JLabel();
			jLabel32.setBounds(new Rectangle(11, 134, 92, 18));
			jLabel32.setText("比例");
			jLabel261 = new JLabel();
			jLabel261.setBounds(new Rectangle(617, 135, 50, 18));
			jLabel261.setText("(\u7f8e\u5143)");
			jLabel18 = new JLabel();
			jLabel18.setBounds(new Rectangle(338, 135, 103, 18));
			jLabel18.setText("余料结转出口");
			jLabel62 = new JLabel();
			jLabel62.setBounds(new Rectangle(264, 72, 50, 18));
			jLabel62.setText("(\u7f8e\u5143)");
			jLabel61 = new JLabel();
			jLabel61.setBounds(new Rectangle(264, 44, 50, 18));
			jLabel61.setText("(\u7f8e\u5143)");
			jPanelReal = new JPanel();
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel5 = new JLabel();
			jLabel5.setText("期初料件总金额");
			jLabel5.setBounds(new Rectangle(10, 16, 92, 18));
			jLabel6.setText("(美元)");
			jLabel6.setBounds(new Rectangle(264, 16, 50, 20));
			javax.swing.JLabel jLabel14 = new JLabel();
			javax.swing.JLabel jLabel13 = new JLabel();
			jLabel13.setText("本期进口总金额");
			jLabel13.setBounds(new Rectangle(338, 18, 103, 20));
			jLabel14.setText("(美元)");
			jLabel14.setBounds(new Rectangle(617, 22, 50, 18));
			jPanelReal.setLayout(null);
			jPanelReal.setBounds(new Rectangle(23, 214, 689, 194));
			jPanelReal.setBorder(BorderFactory.createTitledBorder(null,
					"\u7533\u62a5\u6d77\u5173\u7684\u6570\u636e",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), Color.blue));
			jPanelReal.add(jLabel5, null);
			jPanelReal.add(jLabel6, null);
			jPanelReal.add(getJFormattedTextField2(), null);
			jPanelReal.add(jLabel13, null);
			jPanelReal.add(jLabel14, null);
			jPanelReal.add(getJTextField8(), null);
			javax.swing.JLabel jLabel23 = new JLabel();
			jLabel23.setText("(美元)");
			jLabel23.setBounds(new Rectangle(618, 107, 50, 20));
			jPanelReal.add(jLabel23, null);
			javax.swing.JLabel jLabel22 = new JLabel();
			jLabel22.setText("本期出口总金额");
			jLabel22.setBounds(new Rectangle(10, 44, 92, 20));
			jPanelReal.add(jLabel22, null);
			jPanelReal.add(getJTextField13(), null);
			javax.swing.JLabel jLabel15 = new JLabel();
			jLabel15.setText("期末结余料件总金额");
			jLabel15.setBounds(new Rectangle(338, 46, 111, 18));
			jPanelReal.add(jLabel15, null);
			javax.swing.JLabel jLabel16 = new JLabel();
			jLabel16.setText("(美元)");
			jLabel16.setBounds(new Rectangle(617, 48, 50, 16));
			jPanelReal.add(jLabel16, null);
			jPanelReal.add(getJTextField9(), null);
			javax.swing.JLabel jLabel24 = new JLabel();
			jLabel24.setText("内销金额");
			jLabel24.setBounds(new Rectangle(10, 75, 92, 21));
			jPanelReal.add(jLabel24, null);
			jPanelReal.add(getTfInnerCancelMoney(), null);
			javax.swing.JLabel jLabel8 = new JLabel();
			jLabel8.setText("(人民币)");
			jLabel8.setBounds(new Rectangle(618, 75, 50, 18));
			jPanelReal.add(jLabel8, null);
			javax.swing.JLabel jLabel7 = new JLabel();
			jLabel7.setText("内销补税税额");
			jLabel7.setBounds(new Rectangle(338, 76, 103, 18));
			jPanelReal.add(jLabel7, null);
			jPanelReal.add(getJFormattedTextField(), null);
			javax.swing.JLabel jLabel17 = new JLabel();
			jLabel17.setText("出口成品耗用总金额");
			jLabel17.setBounds(new Rectangle(338, 106, 113, 18));
			jPanelReal.add(jLabel17, null);
			jPanelReal.add(getJTextField10(), null);
			javax.swing.JLabel jLabel25 = new JLabel();
			jLabel25.setText("退运料件金额");
			jLabel25.setBounds(new Rectangle(10, 103, 92, 19));
			jPanelReal.add(jLabel25, null);
			jPanelReal.add(getJTextField15(), null);
			javax.swing.JLabel jLabel26 = new JLabel();
			jLabel26.setText("(美元)");
			jLabel26.setBounds(new Rectangle(264, 105, 50, 18));
			jPanelReal.add(jLabel26, null);
			jPanelReal.add(jLabel61, null);
			jPanelReal.add(jLabel62, null);
			jPanelReal.add(jLabel18, null);
			jPanelReal.add(getTfOverImgExport(), null);
			jPanelReal.add(jLabel261, null);
			jPanelReal.add(jLabel32, null);
			jPanelReal.add(getTfScale(), null);
			jPanelReal.add(jLabel33, null);
			jPanelReal.add(jLabel34, null);
			jPanelReal.add(getTfCheckStaffName(), null);
			jPanelReal.add(jLabel35, null);
			jPanelReal.add(getTfCheckStaffTime(), null);
		}
		return jPanelReal;
	}

	/**
	 * This method initializes tfOverImgExport
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfOverImgExport() {
		if (tfOverImgExport == null) {
			tfOverImgExport = new JFormattedTextField();
			tfOverImgExport.setBounds(new Rectangle(453, 134, 144, 22));
		}
		return tfOverImgExport;
	}

	/**
	 * This method initializes tfScale
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfScale() {
		if (tfScale == null) {
			tfScale = new JFormattedTextField();
			tfScale.setBounds(new Rectangle(109, 132, 144, 22));
			tfScale.setEditable(false);
		}
		return tfScale;
	}

	/**
	 * This method initializes tfCheckStaffName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCheckStaffName() {
		if (tfCheckStaffName == null) {
			tfCheckStaffName = new JTextField();
			tfCheckStaffName.setEditable(false);
			tfCheckStaffName.setBounds(new Rectangle(109, 161, 144, 22));
		}
		return tfCheckStaffName;
	}

	/**
	 * This method initializes tftfCheckStaffTime
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCheckStaffTime() {
		if (tfCheckStaffTime == null) {
			tfCheckStaffTime = new JTextField();
			tfCheckStaffTime.setEditable(false);
			tfCheckStaffTime.setBounds(new Rectangle(453, 161, 144, 22));
		}
		return tfCheckStaffTime;
	}

	/**
	 * This method initializes jPanelSimulation
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSimulation() {
		if (jPanelSimulation == null) {
			jLabel2611 = new JLabel();
			jLabel2611.setBounds(new Rectangle(618, 110, 50, 18));
			jLabel2611.setText("(\u7f8e\u5143)");
			jLabel181 = new JLabel();
			jLabel181.setBounds(new Rectangle(341, 108, 103, 18));
			jLabel181.setText("余料结转出口");
			jLabel621 = new JLabel();
			jLabel621.setBounds(new Rectangle(264, 72, 50, 18));
			jLabel621.setText("(\u7f8e\u5143)");
			jLabel611 = new JLabel();
			jLabel611.setBounds(new Rectangle(264, 44, 50, 18));
			jLabel611.setText("(\u7f8e\u5143)");
			jLabel262 = new JLabel();
			jLabel262.setBounds(new Rectangle(264, 105, 50, 18));
			jLabel262.setText("(\u7f8e\u5143)");
			jLabel251 = new JLabel();
			jLabel251.setBounds(new Rectangle(10, 103, 92, 19));
			jLabel251.setText("\u9000\u8fd0\u6599\u4ef6\u91d1\u989d");
			jLabel171 = new JLabel();
			jLabel171.setBounds(new Rectangle(339, 75, 113, 18));
			jLabel171
					.setText("\u51fa\u53e3\u6210\u54c1\u8017\u7528\u603b\u91d1\u989d");
			jLabel241 = new JLabel();
			jLabel241.setBounds(new Rectangle(10, 75, 92, 21));
			jLabel241.setText("\u5185\u9500\u91d1\u989d");
			jLabel161 = new JLabel();
			jLabel161.setBounds(new Rectangle(617, 48, 50, 16));
			jLabel161.setText("(\u7f8e\u5143)");
			jLabel151 = new JLabel();
			jLabel151.setBounds(new Rectangle(338, 46, 115, 18));
			jLabel151
					.setText("\u671f\u672b\u7ed3\u4f59\u6599\u4ef6\u603b\u91d1\u989d");
			jLabel221 = new JLabel();
			jLabel221.setBounds(new Rectangle(10, 44, 92, 20));
			jLabel221.setText("\u672c\u671f\u51fa\u53e3\u603b\u91d1\u989d");
			jLabel231 = new JLabel();
			jLabel231.setBounds(new Rectangle(617, 77, 50, 20));
			jLabel231.setText("(\u7f8e\u5143)");
			jLabel141 = new JLabel();
			jLabel141.setBounds(new Rectangle(617, 22, 50, 18));
			jLabel141.setText("(\u7f8e\u5143)");
			jLabel131 = new JLabel();
			jLabel131.setBounds(new Rectangle(338, 18, 103, 20));
			jLabel131.setText("\u672c\u671f\u8fdb\u53e3\u603b\u91d1\u989d");
			jLabel63 = new JLabel();
			jLabel63.setBounds(new Rectangle(264, 16, 50, 20));
			jLabel63.setText("(\u7f8e\u5143)");
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(10, 16, 92, 18));
			jLabel51.setText("\u671f\u521d\u6599\u4ef6\u603b\u91d1\u989d");
			jPanelSimulation = new JPanel();
			jPanelSimulation.setLayout(null);
			jPanelSimulation.setBounds(new Rectangle(26, 410, 685, 135));
			jPanelSimulation.setBorder(BorderFactory.createTitledBorder(null,
					"\u7cfb\u7edf\u6a21\u62df\u8ba1\u7b97\u7684\u6570\u636e",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), Color.blue));
			jPanelSimulation.add(jLabel51, null);
			jPanelSimulation.add(jLabel63, null);
			jPanelSimulation.add(getJFormattedTextField21(), null);
			jPanelSimulation.add(jLabel131, null);
			jPanelSimulation.add(jLabel141, null);
			jPanelSimulation.add(getJTextField81(), null);
			jPanelSimulation.add(jLabel231, null);
			jPanelSimulation.add(jLabel221, null);
			jPanelSimulation.add(getJTextField131(), null);
			jPanelSimulation.add(jLabel151, null);
			jPanelSimulation.add(jLabel161, null);
			jPanelSimulation.add(getJTextField91(), null);
			jPanelSimulation.add(jLabel241, null);
			jPanelSimulation.add(getTfInnerCancelMoney1(), null);
			jPanelSimulation.add(getJFormattedTextField1(), null);
			jPanelSimulation.add(jLabel171, null);
			jPanelSimulation.add(jLabel251, null);
			jPanelSimulation.add(getJTextField151(), null);
			jPanelSimulation.add(jLabel262, null);
			jPanelSimulation.add(jLabel611, null);
			jPanelSimulation.add(jLabel621, null);
			jPanelSimulation.add(jLabel181, null);
			jPanelSimulation.add(getTfOverImgExport1(), null);
			jPanelSimulation.add(jLabel2611, null);
		}
		return jPanelSimulation;
	}

	/**
	 * This method initializes jFormattedTextField21
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getJFormattedTextField21() {
		if (jFormattedTextField21 == null) {
			DefaultFormatterFactory defaultFormatterFactory1 = new DefaultFormatterFactory();
			defaultFormatterFactory1.setDisplayFormatter(new NumberFormatter());
			defaultFormatterFactory1.setDefaultFormatter(new NumberFormatter());
			jFormattedTextField21 = new JCustomFormattedTextField();
			jFormattedTextField21.setEditable(false);
			jFormattedTextField21.setBounds(new Rectangle(109, 16, 144, 22));
			jFormattedTextField21.setFormatterFactory(defaultFormatterFactory1);
		}
		return jFormattedTextField21;
	}

	/**
	 * This method initializes jTextField81
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJTextField81() {
		if (jTextField81 == null) {
			DefaultFormatterFactory defaultFormatterFactory1 = new DefaultFormatterFactory();
			defaultFormatterFactory1.setDisplayFormatter(new NumberFormatter());
			defaultFormatterFactory1.setDefaultFormatter(new NumberFormatter());
			jTextField81 = new JFormattedTextField();
			jTextField81.setEditable(false);
			jTextField81.setBounds(new Rectangle(453, 17, 144, 22));
			jTextField81.setFormatterFactory(defaultFormatterFactory1);
		}
		return jTextField81;
	}

	/**
	 * This method initializes jTextField131
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJTextField131() {
		if (jTextField131 == null) {
			DefaultFormatterFactory defaultFormatterFactory1 = new DefaultFormatterFactory();
			defaultFormatterFactory1.setDisplayFormatter(new NumberFormatter());
			defaultFormatterFactory1.setDefaultFormatter(new NumberFormatter());
			jTextField131 = new JFormattedTextField();
			jTextField131.setEditable(false);
			jTextField131.setBounds(new Rectangle(109, 44, 144, 22));
			jTextField131.setFormatterFactory(defaultFormatterFactory1);
		}
		return jTextField131;
	}

	/**
	 * This method initializes jTextField91
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJTextField91() {
		if (jTextField91 == null) {
			DefaultFormatterFactory defaultFormatterFactory1 = new DefaultFormatterFactory();
			defaultFormatterFactory1.setDisplayFormatter(new NumberFormatter());
			defaultFormatterFactory1.setDefaultFormatter(new NumberFormatter());
			jTextField91 = new JFormattedTextField();
			jTextField91.setEditable(false);
			jTextField91.setBounds(new Rectangle(453, 44, 144, 22));
			jTextField91.setFormatterFactory(defaultFormatterFactory1);
		}
		return jTextField91;
	}

	/**
	 * This method initializes tfInnerCancelMoney1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfInnerCancelMoney1() {
		if (tfInnerCancelMoney1 == null) {
			DefaultFormatterFactory defaultFormatterFactory1 = new DefaultFormatterFactory();
			defaultFormatterFactory1.setDisplayFormatter(new NumberFormatter());
			defaultFormatterFactory1.setDefaultFormatter(new NumberFormatter());
			tfInnerCancelMoney1 = new JCustomFormattedTextField();
			tfInnerCancelMoney1.setEditable(false);
			tfInnerCancelMoney1.setBounds(new Rectangle(109, 75, 144, 22));
			tfInnerCancelMoney1.setFormatterFactory(defaultFormatterFactory1);
		}
		return tfInnerCancelMoney1;
	}

	/**
	 * This method initializes jFormattedTextField1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getJFormattedTextField1() {
		if (jFormattedTextField1 == null) {
			DefaultFormatterFactory defaultFormatterFactory1 = new DefaultFormatterFactory();
			defaultFormatterFactory1.setDisplayFormatter(new NumberFormatter());
			defaultFormatterFactory1.setDefaultFormatter(new NumberFormatter());
			jFormattedTextField1 = new JCustomFormattedTextField();
			jFormattedTextField1.setEditable(false);
			jFormattedTextField1.setBounds(new Rectangle(453, 75, 144, 22));
			jFormattedTextField1.setFormatterFactory(defaultFormatterFactory1);
		}
		return jFormattedTextField1;
	}

	/**
	 * This method initializes jTextField151
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJTextField151() {
		if (jTextField151 == null) {
			DefaultFormatterFactory defaultFormatterFactory1 = new DefaultFormatterFactory();
			defaultFormatterFactory1.setDisplayFormatter(new NumberFormatter());
			defaultFormatterFactory1.setDefaultFormatter(new NumberFormatter());
			jTextField151 = new JFormattedTextField();
			jTextField151.setEditable(false);
			jTextField151.setBounds(new Rectangle(109, 102, 144, 22));
			jTextField151.setFormatterFactory(defaultFormatterFactory1);
		}
		return jTextField151;
	}

	/**
	 * This method initializes tfOverImgExport1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfOverImgExport1() {
		if (tfOverImgExport1 == null) {
			DefaultFormatterFactory defaultFormatterFactory1 = new DefaultFormatterFactory();
			defaultFormatterFactory1.setDisplayFormatter(new NumberFormatter());
			defaultFormatterFactory1.setDefaultFormatter(new NumberFormatter());
			tfOverImgExport1 = new JFormattedTextField();
			tfOverImgExport1.setEditable(false);
			tfOverImgExport1.setBounds(new Rectangle(453, 102, 144, 22));
			tfOverImgExport1.setFormatterFactory(defaultFormatterFactory1);
		}
		return tfOverImgExport1;
	}

	/**
	 * This method initializes jButton14
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton14() {
		if (jButton14 == null) {
			jButton14 = new JButton();
			jButton14.setBounds(new Rectangle(404, 561, 89, 25));
			jButton14.setText("财务审核");
			jButton14.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkCancelAuthorityAction.financialAudit(new Request(
							CommonVars.getCurrUser()));
					if (JOptionPane.showConfirmDialog(DgCancelCus.this,
							"确定通过财务审核吗?", "提示信息", 0) == 0) {
						Date date = new Date();
						tfCheckStaffName.setText(CommonVars.getCurrUser()
								.getUserName());
						tfCheckStaffTime.setText(CommonUtils.getDate(date,
								"yyyy-MM-dd"));
						// 财务审核人员
						cancelHead
								.setCheckStaffName(tfCheckStaffName.getText() == null ? ""
										: tfCheckStaffName.getText());
						// 财务审核时间
						cancelHead.setCheckStaffTime(new Date());
						cancelHead = (CancelCusHead) checkCancelAction
								.saveCancelHead(
										new Request(CommonVars.getCurrUser()),
										cancelHead);
						tableModel.updateRow(cancelHead);
						dataState = DataState.BROWSE;
						setState();
					}
				}
			});

		}
		return jButton14;
	}

	/**
	 * This method initializes btNext
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtNext() {
		if (btNext == null) {
			btNext = new JButton();
			btNext.setText("下一笔");
			btNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int row = tbDanJia.getSelectedRow();
					if (row < 0) {
						row = 0;
						tableModelPrice.nextRow();
					}
					tableModelPrice.nextRow();
					CancelImgResult cr = (CancelCusImgResult) tableModelPrice
							.getDataByRow(row);
					// 手调单价
					Double selfPrice = Double.valueOf(ftfPrice.getText()
							.toString());
					cr.setSelfPrice(selfPrice);
					// 根据手调单价调整
					// 应剩余金额,先减去原来的
					cr.setLeaveSumPrice(CommonUtils.getDoubleExceptNull(cr
							.getLeaveSumPrice())
							- CommonUtils.getDoubleExceptNull(cr
									.getUseSumPrice()));

					// 消耗金额
					cr.setUseSumPrice(CommonUtils.getDoubleExceptNull(cr
							.getUseSumNum()) * selfPrice);
					// 应剩余金额,加上新的
					cr.setLeaveSumPrice(CommonUtils.getDoubleExceptNull(cr
							.getLeaveSumPrice())
							+ CommonUtils.getDoubleExceptNull(cr
									.getUseSumPrice()));
					cr.setResultSumPrice(CommonUtils.getDoubleExceptNull(cr
							.getLeaveSumPrice()));
					checkCancelAction.saveCancelImgResult(new Request(
							CommonVars.getCurrUser()), cr);
					ftfPrice.setValue(CommonUtils
							.getDoubleExceptNull(((CancelImgResult) tableModelPrice
									.getCurrentRow()).getSelfPrice()));
				}
			});
		}
		return btNext;
	}

	// 把原来的模拟数据遮挡
	public JPanel getCoverPanel() {
		if (jPanelCover == null) {
			jPanelCover = new JPanel();
			jPanelCover.setLayout(null);
			jPanelCover.setBounds(new Rectangle(26, 410, 685, 135));
			jPanelCover.setPreferredSize(new Dimension(685, 135));
			JTextField jta = new JTextField();
			jta.setText("请企业认真核对申报海关的数据");
			jta.setBounds(new Rectangle(0, 0, 685, 135));
			jta.setPreferredSize(new Dimension(685, 135));
			// jta.setFont(new Font("Dialog", Font.PLAIN, 18));
			// jta.setForeground(Color.blue);
			jta.setEditable(false);
			jPanelCover.add(jta);
		}
		return jPanelCover;
	}

	/**
	 * 为窗口注册一个快捷键Ctrl+H
	 * 
	 * @author Administrator
	 *
	 */
	class CapListener implements AWTEventListener {
		public void eventDispatched(AWTEvent event) {
			if (((KeyEvent) event).isControlDown() == true
					&& ((KeyEvent) event).getKeyCode() == KeyEvent.VK_H) {
				jPanelCover.setVisible(false);
				System.out.println("当按下Ctrl+H时移除遮挡层。。。。");
			}
		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
