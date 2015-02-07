/*
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerVersion;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.SendState;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class DgEmsEdiMerger extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jbAdd = null;

	private JButton jbEdit = null;

	private JButton jbDelete = null;

	private JButton jbSave = null;

	private JButton jbExit = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jpExg = null;

	private JPanel jpImg = null;

	private JPanel jpHead = null;

	private JPanel jpBom = null;

	private JTextField tfEmsNo = null;

	private JTextField tfChangeTimes = null;

	private JTextField tfCopEmsNo = null;

	private JTextField tfSancEmsNo = null;

	private JTextField tfEmsApprNo = null;

	private JTextField tfEmsType = null;

	private JTextField tfTradeCode = null;

	private JTextField tfTradeName = null;

	private JTextField tfMachCode = null;

	private JTextField tfMachName = null;

	private JTextField tfTel = null;

	private JTextField tfAddress = null;

	private JTextField tfDeclareErType = null;

	private JCalendarComboBox cmbBeginDate = null;

	private JCalendarComboBox cmbEndDate = null;

	private JTextField tfInputUser = null;

	private JTextField tfInputDate = null;

	private JTextField jTextField18 = null;

	private JTextField tfDeclareDate = null;

	private JTextField tfDeclareTime = null;

	private JTextField jTextField21 = null;

	private JTextField tfNote = null;

	private EmsEdiMergerHead emsEdiMergerHead = null; // 归并关系表头

	private EmsEdiTrHead emsEdiTrHead = null; // 经营范围表头

	private EmsEdiMergerExgBefore emsExgBefore = null; // 归并前成品 //
	// @jve:decl-index=0:

	private EmsEdiMergerImgBefore emsImgBefore = null; // 归并前料件

	private EmsEdiMergerImgAfter emsImgAfter = null; // 归并后料件

	private EmsEdiMergerExgAfter emsExgAfter = null; // 归并后成品

	private EmsEdiMergerExgBom emsBom = null; // Bom

	private EmsEdiMergerVersion emsEdiMergerVersion = null; // 版本号 // //
															// @jve:decl-index=0:
	// @jve:decl-index=0:

	private ManualDeclareAction manualdeclearAction = null;

	private CommonBaseCodeAction commonBaseCodeAction = null;

	private JTableListModel tableModel = null; // 表头table

	private JTableListModel tableModelImgAfter = null;

	private JTableListModel tableModelImgBefore = null;

	private JTableListModel tableModelExgAfter = null;

	private JTableListModel tableModelExgBefore = null;

	private JTableListModel tableModelBomExg = null;// 归并关系BOM对应的成品

	private JTableListModel tableModelBom = null; // BOM

	private JTableListModel tableModelVersion = null;

	private boolean isChange = false; // 判断是否变更状态

	private JFormattedTextField tfInvestAmount = null;

	private JFormattedTextField tfMachinAbility = null;

	private JFormattedTextField tfMaxTurnMoney = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JTable jTableImgAfter = null;

	private JScrollPane jScrollPane = null;

	private JToolBar jToolBar1 = null;

	private JButton jbAddImgBefore = null;

	private JButton jbEditImgBefore = null;

	private JButton jbDeleteImgBefore = null;

	private JTable jTableImgBefore = null;

	private JScrollPane jScrollPane1 = null;

	private List dataSourceImgAfter = null; // 显示归并后料件数据List

	private List dataSourceExgAfter = null; // 显示归并后成品数据List

	private List dataSourceBom = null; // 显示BOM数据List

	private JSplitPane jSplitPane1 = null;

	private List dataSourceBomExg = null; // 显示归并BOM对应的成品

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JTable jTableExgAfter = null;

	private JScrollPane jScrollPane2 = null;

	private JToolBar jToolBar2 = null;

	private JButton jbAddExgBefore = null;

	private JButton jbEditExgBefore = null;

	private JButton jbDeleteExgBefore = null;

	private JTable jTableExgBefore = null;

	private JScrollPane jScrollPane3 = null;

	private JSplitPane jSplitPane2 = null;

	private JPanel jPanel4 = null;

	private JPanel jPanel5 = null;

	private JTable jTableBomExg = null;// bom 对应归并前成品

	private JScrollPane jScrollPane4 = null;

	private JToolBar jToolBar3 = null;

	private JButton btnAddBom = null;

	private JButton btnEditBom = null;

	private JButton btnDeleteBom = null;

	private JButton btnCopyBom = null;

	private JButton btnPasteBom = null;

	private JPopupMenu jPopupMenu = null; // @jve:decl-index=0:visual-constraint="844,376"

	private JMenuItem jMenuProBom = null;

	private JMenuItem jMenuMergerAfterImg = null;

	private int dataState = DataState.BROWSE;

	private JButton jButton = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private ButtonGroup buttonGroup = new ButtonGroup();

	private boolean isHistoryChange = false;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JSplitPane jSplitPane3 = null;

	private JPanel jPanel6 = null;

	private JTabbedPane jTabbedPane7 = null;

	private JTable jTableVersion = null;

	private JScrollPane jScrollPane5 = null;

	private JTable jTableBom = null;

	private JButton jBimport = null;

	private CompanyOther other = CommonVars.getOther();

	private JButton jButton3 = null;

	private JButton jButton4 = null;

	private JButton jButton5 = null;

	private JButton jButton6 = null;

	private JButton jButton7 = null;

	private JLabel jLabel28 = null;

	private JLabel jLabel29 = null;

	private List imgbeforeList = new Vector(); // @jve:decl-index=0:

	private List exgbeforeList = new Vector();

	private String isSendSign = ""; // @jve:decl-index=0:

	private JButton jButton8 = null;

	private JButton jButton10 = null;

	private JButton jButton11 = null;

	private JButton jButton12 = null;

	private JPopupMenu jPopupMenu1 = null; // @jve:decl-index=0:visual-constraint="834,196"

	private JMenuItem jMenuItem = null; // @jve:decl-index=0:visual-constraint="803,139"

	private JMenuItem jMenuItem1 = null; // @jve:decl-index=0:visual-constraint="736,201"

	private JPopupMenu jPopupMenu2 = null; // @jve:decl-index=0:visual-constraint="847,317"

	private JMenuItem jMenuItem2 = null; // @jve:decl-index=0:visual-constraint="740,248"

	private JMenuItem jMenuItem3 = null; // @jve:decl-index=0:visual-constraint="698,258"

	private JPopupMenu jPopupMenu3 = null; // @jve:decl-index=0:visual-constraint="782,292"

	private JMenuItem jMenuItem4 = null; // @jve:decl-index=0:visual-constraint="797,203"

	private JMenuItem jMenuItem5 = null; // @jve:decl-index=0:visual-constraint="730,210"

	private JPopupMenu jPopupMenu4 = null; // @jve:decl-index=0:visual-constraint="804,244"

	private JMenuItem jMenuItem6 = null; // @jve:decl-index=0:visual-constraint="737,217"

	private JMenuItem jMenuItem7 = null; // @jve:decl-index=0:visual-constraint="738,330"

	private JButton btnWasterToInt = null;

	private JButton jButton9 = null;

	private JPanel jPanel8 = null;

	private JPanel jPanel9 = null;

	private JTable jTableimg = null;

	private JScrollPane jScrollPane6 = null;

	private JScrollPane jScrollPane7 = null;

	private JButton btnCope = null;

	private JButton btnMaxVersion = null;

	private JButton btnReverseCheck = null;

	private JPanel jPanel7 = null;

	/**
	 * This is the default constructor
	 */
	public DgEmsEdiMerger() {
		super();
		manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		initialize();
		buttonGroup.add(jRadioButton);
		buttonGroup.add(jRadioButton1);
		tableModelImgAfter = EmsEdiMergerClientLogic.initTableImgAfter(
				jTableImgAfter, dataSourceImgAfter);
		tableModelImgBefore = EmsEdiMergerClientLogic.initTableImgBefore(
				jTableImgBefore, null);
		tableModelExgAfter = EmsEdiMergerClientLogic.initTableExgAfter(
				jTableExgAfter, dataSourceExgAfter);
		tableModelExgBefore = EmsEdiMergerClientLogic.initTableExgBefore(
				jTableExgBefore, null);
		tableModelBomExg = EmsEdiMergerClientLogic.initTableExgBefore(
				jTableBomExg, new Vector());
		initTableVersion(new Vector());
		tableModelBom = EmsEdiMergerClientLogic.initTableBom(jTableBom,
				new Vector());
		jRadioButton1.setSelected(true);
	}

	public void setVisible(boolean f) {
		if (f) {

			long s = System.currentTimeMillis();
			long b = s;
			isSendSign = manualdeclearAction.getBpara(
					new Request(CommonVars.getCurrUser()),
					BcusParameter.EmsSEND_Sign);
			if (tableModel.getCurrentRow() != null) {
				emsEdiMergerHead = (EmsEdiMergerHead) tableModel
						.getCurrentRow(); // 获得归并关系表头信息
			}
			long e = System.currentTimeMillis();
			System.out.println("获得归并关系表头信息time:" + (e - s) / 1000.0);

			fillWindow();

			s = System.currentTimeMillis();
			new FindEmsEdiMergerImgAfterAndImgBefore().start();
			e = System.currentTimeMillis();
			System.out.println("加载归并后料件数据，显示第一条归并后料件的归并前料件。time:" + (e - s)
					/ 1000.0);

			s = System.currentTimeMillis();
			new FindEmsEdiMergerExgAfterAndExgBefore().start();
			e = System.currentTimeMillis();
			System.out.println("加载归并后成品数据，显示第一条归并后成品的归并前成品。time:" + (e - s)
					/ 1000.0);

			s = System.currentTimeMillis();
			new FindEmsEdiMergerExgBeforeAndBom().start();
			e = System.currentTimeMillis();
			System.out.println("加载归并前成品数据，显示第一条归并前成品的BOM。time:" + (e - s)
					/ 1000.0);

			if (emsEdiMergerHead.getDeclareState().equals(
					DeclareState.PROCESS_EXE)
					|| (emsEdiMergerHead.getDeclareState()
							.equals(DeclareState.WAIT_EAA))
					|| isHistoryChange == true) {
				dataState = DataState.READONLY;
			} else
				dataState = DataState.BROWSE;

			setState();
			beginState();

			List listEmsEdiTr = manualdeclearAction
					.findEmsEdiTrHead(new Request(CommonVars.getCurrUser()));
			for (int i = 0; i < listEmsEdiTr.size(); i++) {
				if (((EmsEdiTrHead) listEmsEdiTr.get(i)).getDeclareState()
						.equals(DeclareState.PROCESS_EXE))
					emsEdiTrHead = (EmsEdiTrHead) listEmsEdiTr.get(i); // 获取正在执行的经营范围
			}

			e = System.currentTimeMillis();
			System.out.println("---------加载数据总时间:" + (e - b) / 1000.0);
		}

		super.setVisible(f);
	}

	/**
	 * 加载归并后料件数据，显示第一条归并后料件的归并前料件。
	 * 
	 * @author chl
	 */
	class FindEmsEdiMergerImgAfterAndImgBefore extends Thread {
		public void run() {
			dataSourceImgAfter = manualdeclearAction // 归并后料件数据
					.findEmsEdiMergerImgAfter(
							new Request(CommonVars.getCurrUser()),
							emsEdiMergerHead);
			tableModelImgAfter = EmsEdiMergerClientLogic.initTableImgAfter(
					jTableImgAfter, dataSourceImgAfter);
			EmsEdiMergerClientLogic.transModifyMark(jTableImgAfter);
			EmsEdiMergerClientLogic.transSendState(jTableImgAfter);

			if (dataSourceImgAfter != null && !dataSourceImgAfter.isEmpty()) {
				// 归并前料件数据
				emsImgAfter = (EmsEdiMergerImgAfter) dataSourceImgAfter.get(0);
				List list = manualdeclearAction.findEmsEdiMergerImgBefore(
						new Request(CommonVars.getCurrUser()), emsImgAfter);
				tableModelImgBefore = EmsEdiMergerClientLogic
						.initTableImgBefore(jTableImgBefore, list);
				EmsEdiMergerClientLogic.transModifyMark(jTableImgBefore);
				EmsEdiMergerClientLogic.transSendState(jTableImgBefore);
			} else {
				tableModelImgBefore = EmsEdiMergerClientLogic
						.initTableImgBefore(jTableImgBefore, new Vector());
			}
		}
	}

	/**
	 * 加载归并后成品数据，显示第一条归并后成品的归并前成品。
	 * 
	 * @author chl
	 */
	class FindEmsEdiMergerExgAfterAndExgBefore extends Thread {
		public void run() {
			dataSourceExgAfter = manualdeclearAction // 归并后成品数据
					.findEmsEdiMergerExgAfter(
							new Request(CommonVars.getCurrUser()),
							emsEdiMergerHead);
			tableModelExgAfter = EmsEdiMergerClientLogic.initTableExgAfter(
					jTableExgAfter, dataSourceExgAfter);
			EmsEdiMergerClientLogic.transModifyMark(jTableExgAfter);
			EmsEdiMergerClientLogic.transSendState(jTableExgAfter);

			if (dataSourceExgAfter != null && !dataSourceExgAfter.isEmpty()) {
				// 显示归并前成品数据
				emsExgAfter = (EmsEdiMergerExgAfter) dataSourceExgAfter.get(0);// 归并后
				List list = manualdeclearAction.findEmsEdiMergerExgBefore(
						new Request(CommonVars.getCurrUser()), emsExgAfter);
				tableModelExgBefore = EmsEdiMergerClientLogic
						.initTableExgBefore(jTableExgBefore, list);
				EmsEdiMergerClientLogic.transModifyMark(jTableExgBefore);
				EmsEdiMergerClientLogic.transSendState(jTableExgBefore);
			} else {
				tableModelExgBefore = EmsEdiMergerClientLogic
						.initTableExgBefore(jTableExgBefore, new Vector());
			}
		}
	}

	/**
	 * 加载归并前成品数据，显示第一条归并前成品的BOM。
	 * 
	 * @author chl
	 */
	class FindEmsEdiMergerExgBeforeAndBom extends Thread {
		public void run() {
			// 归并前所有的成品
			dataSourceBomExg = manualdeclearAction.findEmsEdiMergerExgBefore(
					new Request(CommonVars.getCurrUser()), emsEdiMergerHead);

			// 显示bom
			if (dataSourceBomExg != null && dataSourceBomExg.size() > 0) {
				tableModelBomExg = EmsEdiMergerClientLogic.initTableExgBefore(
						jTableBomExg, dataSourceBomExg);
				EmsEdiMergerClientLogic.transModifyMark(jTableBomExg);
				EmsEdiMergerClientLogic.transSendState(jTableBomExg);
				emsExgBefore = (EmsEdiMergerExgBefore) dataSourceBomExg.get(0);
				List dataSourceVersion = manualdeclearAction
						.findEmsEdiMergerVersion(
								// 得到版本号
								new Request(CommonVars.getCurrUser()),
								emsExgBefore);
				if (dataSourceVersion != null && dataSourceVersion.size() > 0) {
					initTableVersion(dataSourceVersion);
					valuechange();
				} else {
					initTableVersion(new Vector());
					tableModelBom = EmsEdiMergerClientLogic.initTableBom(
							jTableBom, new Vector());
				}
			} else {
				initTableVersion(new Vector());
				tableModelBom = EmsEdiMergerClientLogic.initTableBom(jTableBom,
						new Vector());
				tableModelBomExg = EmsEdiMergerClientLogic.initTableExgBefore(
						jTableBomExg, new Vector());
			}
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(919, 530);
		this.setTitle("归并关系备案维护");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
			}
		});
	}

	private void beginState() {
		if (DgEmsEdiMerger.this.isChange) { // 变更状态
			if (dataState == DataState.EDIT)
				DgEmsEdiMerger.this.setTitle("归并关系申请变更维护");
			else
				DgEmsEdiMerger.this.setTitle("归并关系申请变更浏览");
		} else if (DgEmsEdiMerger.this.isChange == false) { // 备案状态
			if (dataState == DataState.EDIT)
				DgEmsEdiMerger.this.setTitle("归并关系申请备案维护");
			else
				DgEmsEdiMerger.this.setTitle("归并关系申请备案浏览");
		}
	}

	private void fillWindow() {
		if (emsEdiMergerHead != null) {
			// 初始基本信息
			tfEmsNo.setText(emsEdiMergerHead.getEmsNo());
			if (emsEdiMergerHead.getModifyTimes() != null) {
				tfChangeTimes.setText(emsEdiMergerHead.getModifyTimes()
						.toString());
			} else {
				tfChangeTimes.setText("");
			}
			tfCopEmsNo.setText(emsEdiMergerHead.getCopEmsNo());
			tfSancEmsNo.setText(emsEdiMergerHead.getSancEmsNo());
			tfEmsApprNo.setText(emsEdiMergerHead.getEmsApprNo());
			tfEmsType.setText("便捷通关帐册");
			tfTradeCode.setText(emsEdiMergerHead.getTradeCode());
			tfTradeName.setText(emsEdiMergerHead.getTradeName());
			tfMachCode.setText(emsEdiMergerHead.getMachCode());
			tfMachName.setText(emsEdiMergerHead.getMachName());
			tfTel.setText(emsEdiMergerHead.getTel());
			tfAddress.setText(emsEdiMergerHead.getAddress());
			cmbBeginDate.setDate(emsEdiMergerHead.getBeginDate());
			cmbEndDate.setDate(emsEdiMergerHead.getEndDate());
			if (emsEdiMergerHead.getDeclareErType() != null) {
				if (emsEdiMergerHead.getDeclareErType().equals("1"))
					tfDeclareErType.setText("企业");
				else if (emsEdiMergerHead.getDeclareErType().equals("2"))
					tfDeclareErType.setText("代理公司");
				else if (emsEdiMergerHead.getDeclareErType().equals("3"))
					tfDeclareErType.setText("报关行");
			}
			tfInvestAmount.setText(EmsEdiMergerClientLogic
					.doubleToStr(emsEdiMergerHead.getInvestAmount()));
			tfMachinAbility.setText(EmsEdiMergerClientLogic
					.doubleToStr(emsEdiMergerHead.getMachAbility()));
			tfMaxTurnMoney.setText(EmsEdiMergerClientLogic
					.doubleToStr(emsEdiMergerHead.getMaxTurnMoney()));
			tfInputUser.setText(emsEdiMergerHead.getInputEr());
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (emsEdiMergerHead.getDeclareDate() != null) {
				String getDeclareDate = bartDateFormat.format(emsEdiMergerHead
						.getDeclareDate());
				tfDeclareDate.setText(getDeclareDate);// 申报日期
			}
			if (emsEdiMergerHead.getNewApprDate() != null) {
				String getDeclareDate = bartDateFormat.format(emsEdiMergerHead
						.getNewApprDate());
				jTextField18.setText(getDeclareDate);// 备案批准日期
			}
			if (emsEdiMergerHead.getChangeApprDate() != null) {
				String getDeclareDate = bartDateFormat.format(emsEdiMergerHead
						.getChangeApprDate());
				jTextField21.setText(getDeclareDate);// 变更批准日期
			}
			SimpleDateFormat bartTimeFormat = new SimpleDateFormat("HH:mm:ss");
			if (emsEdiMergerHead.getDeclareTime() != null) {
				String getDeclareTime = bartTimeFormat.format(emsEdiMergerHead
						.getDeclareTime());
				tfDeclareTime.setText(getDeclareTime);// 申报时间
			}

			if (emsEdiMergerHead.getInputDate() != null) {
				String getInputDate = bartDateFormat.format(emsEdiMergerHead
						.getInputDate());
				tfInputDate.setText(getInputDate);// 录入日期
			}
			tfNote.setText(emsEdiMergerHead.getNote());
		}
	}

	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(0);
			f.setHgap(0);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.setFloatable(false);
			jToolBar.add(getJbAdd());
			jToolBar.add(getJButton());
			jToolBar.add(getJbEdit());
			jToolBar.add(getJbDelete());
			jToolBar.add(getJButton12());
			jToolBar.add(getJButton3());
			jToolBar.add(getJButton7());
			jToolBar.add(getJbSave());
			jToolBar.add(getJButton9());
			jToolBar.add(getBtnMaxVersion());
			jToolBar.add(getBtnReverseCheck());
			jToolBar.add(getJbExit());
		}
		return jToolBar;
	}

	/**
	 * 
	 * This method initializes jbAdd
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJbAdd() { // 归并后料件或成品新增
		if (jbAdd == null) {
			jbAdd = new JButton();
			jbAdd.setText("新增");
			jbAdd.setPreferredSize(new Dimension(60, 30));
			jbAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 1)// 料件
					{
						if (CommonVars.getOther() != null
								&& CommonVars.getOther()
										.getIsAutoNewMergerBefore()) {
							new newMergerImgExgAfter().start();// 新增归并后自动带出归并前
						} else {
							List list = (List) CommonQuery.getInstance()
									.getMergerImg10(false, emsEdiTrHead,
											emsEdiMergerHead, ""); // common
							if (list == null || list.isEmpty())
								return;
							new newMergerAfterImg(list).start();
						}

					} else if (jTabbedPane.getSelectedIndex() == 2) // 成品
					{

						if (CommonVars.getOther() != null
								&& CommonVars.getOther()
										.getIsAutoNewMergerBefore()) {
							new newMergerImgExgAfter().start();// 新增归并后自动带出归并前
						} else {
							List list = (List) CommonQuery.getInstance()
									.getMergerExg10(false, emsEdiTrHead,
											emsEdiMergerHead, ""); // common
							// dialog
							if (list == null || list.isEmpty())
								return;
							new newMergerAfterExg(list).start();
						}

					}
				}
			});
		}
		return jbAdd;
	}

	class newMergerImgExgAfter extends Thread {
		public void run() {
			try {

				if (jTabbedPane.getSelectedIndex() == 1)// 料件
				{
					EmsEdiMergerImgAfter emsMergerImg = null;
					List list = (List) CommonQuery.getInstance()
							.getMergerImg10(false, emsEdiTrHead,
									emsEdiMergerHead, ""); // common
					if (list == null || list.isEmpty()) {
						return;
					}
					CommonProgress.showProgressDialog(DgEmsEdiMerger.this);
					CommonProgress.setMessage("系统正在新增资料，请稍后...");
					for (int i = 0; i < list.size(); i++) {
						emsMergerImg = (EmsEdiMergerImgAfter) list.get(i);
						emsMergerImg.setEmsEdiMergerHead(emsEdiMergerHead);
						emsMergerImg.setModifyMark(ModifyMarkState.ADDED);
						if (isSendSign != null && "1".equals(isSendSign)) {
							emsMergerImg.setSendState(Integer
									.valueOf(SendState.WAIT_SEND));
						}
						emsMergerImg.setModifyTimes(emsEdiMergerHead
								.getModifyTimes());// 变更次数
						emsMergerImg.setChangeDate(new Date());// 修改日期
						emsMergerImg.setCompany(CommonVars.getCurrUser()
								.getCompany());
						emsMergerImg = manualdeclearAction
								.saveEmsEdiMergerImgAfter(new Request(
										CommonVars.getCurrUser()), emsMergerImg); // 保存归并后

						tableModelImgAfter.addRow(emsMergerImg);

						EmsEdiMergerClientLogic.fillMergerImgBeforeData(
								emsMergerImg, emsEdiMergerHead, list,
								MaterielType.MATERIEL, jTableImgBefore);// 新增归并前
						EmsEdiMergerClientLogic.fillImgBefore(
								tableModelImgBefore, emsMergerImg,
								jTableImgBefore);
						CommonProgress.closeProgressDialog();
					}
				} else if (jTabbedPane.getSelectedIndex() == 2) // 成品
				{
					EmsEdiMergerExgAfter emsMergerExgAfter = null;
					List list = (List) CommonQuery.getInstance()
							.getMergerExg10(false, emsEdiTrHead,
									emsEdiMergerHead, ""); // common
					// dialog
					if (list == null || list.isEmpty()) {
						// CommonProgress.closeProgressDialog();
						return;
					}
					CommonProgress.showProgressDialog(DgEmsEdiMerger.this);
					CommonProgress.setMessage("系统正在新增资料，请稍后...");
					for (int i = 0; i < list.size(); i++) {
						emsMergerExgAfter = (EmsEdiMergerExgAfter) list.get(i);
						emsMergerExgAfter.setEmsEdiMergerHead(emsEdiMergerHead);
						emsMergerExgAfter.setModifyMark(ModifyMarkState.ADDED);
						if (isSendSign != null && "1".equals(isSendSign)) {
							emsMergerExgAfter.setSendState(Integer
									.valueOf(SendState.WAIT_SEND));
						}
						emsMergerExgAfter.setModifyTimes(emsEdiMergerHead
								.getModifyTimes());// 变更次数
						emsMergerExgAfter.setChangeDate(new Date()); // 修改
						emsMergerExgAfter.setCompany(CommonVars.getCurrUser()
								.getCompany());
						emsMergerExgAfter = manualdeclearAction
								.saveEmsEdiMergerExgAfter(new Request(
										CommonVars.getCurrUser()),
										emsMergerExgAfter); // 保存归并后
						tableModelExgAfter.addRow(emsMergerExgAfter);
						EmsEdiMergerClientLogic.fillMergerExgBeforeData(
								emsMergerExgAfter, emsEdiMergerHead, list,
								MaterielType.FINISHED_PRODUCT, jTableExgBefore);// 新增归并前
						EmsEdiMergerClientLogic.fillExgBefore(
								tableModelExgBefore, tableModelBomExg,
								emsMergerExgAfter, jTableExgBefore,
								jTableBomExg);
					}
				}
				CommonProgress.closeProgressDialog();
				setState();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
						"新增失败：！" + e.getMessage(), "提示", 2);
			} finally {
				CommonProgress.closeProgressDialog();
				setState();
			}
		}
	}

	class newMergerAfterExg extends Thread {
		List list = null;

		public newMergerAfterExg(List list) {
			this.list = list;
		}

		public void run() {
			try {
				CommonProgress.showProgressDialog(DgEmsEdiMerger.this);
				CommonProgress.setMessage("系统正在新增资料，请稍后...");
				EmsEdiMergerExgAfter emsMergerExgAfter = null;
				for (int i = 0; i < list.size(); i++) {
					emsMergerExgAfter = (EmsEdiMergerExgAfter) list.get(i);
					emsMergerExgAfter.setEmsEdiMergerHead(emsEdiMergerHead);
					emsMergerExgAfter.setModifyMark(ModifyMarkState.ADDED);
					if (isSendSign != null && "1".equals(isSendSign)) {
						emsMergerExgAfter.setSendState(Integer
								.valueOf(SendState.WAIT_SEND));
					}
					emsMergerExgAfter.setModifyTimes(emsEdiMergerHead
							.getModifyTimes());// 变更次数
					emsMergerExgAfter.setChangeDate(new Date()); // 修改
					emsMergerExgAfter.setCompany(CommonVars.getCurrUser()
							.getCompany());
					List list = (List) CommonQuery.getInstance()
							.getMergerExgBeforeTen(false, emsExgAfter,
									emsEdiMergerHead);
					if (list != null && list.size() == 1) {
						System.out.println("自动计算");
						EmsEdiMergerExgBefore exgBefore = (EmsEdiMergerExgBefore) list
								.get(0);
						emsMergerExgAfter.setWeigthUnitGene(exgBefore
								.getInnerMergerData().getMateriel()
								.getPtNetWeight());

						if (emsMergerExgAfter.getComplex() != null
								&& emsMergerExgAfter.getComplex()
										.getFirstUnit() != null
								&& emsMergerExgAfter.getComplex()
										.getFirstUnit().getName().equals("千克")) {
							emsMergerExgAfter.setLegalUnitGene(1d);
						} else if (emsMergerExgAfter.getComplex()
								.getFirstUnit() != null
								&& emsMergerExgAfter
										.getComplex()
										.getFirstUnit()
										.getName()
										.equals(emsMergerExgAfter.getUnit()
												.getName())) {
							emsMergerExgAfter.setLegalUnitGene(exgBefore
									.getInnerMergerData().getMateriel()
									.getPtNetWeight());
						}

						if (emsMergerExgAfter.getComplex() != null
								&& emsMergerExgAfter.getComplex()
										.getSecondUnit() != null
								&& emsMergerExgAfter.getComplex()
										.getSecondUnit().getName().equals("千克")) {
							emsMergerExgAfter.setLegalUnit2Gene(1d);
						} else if (emsMergerExgAfter.getComplex()
								.getSecondUnit() != null
								&& emsMergerExgAfter
										.getComplex()
										.getSecondUnit()
										.getName()
										.equals(emsMergerExgAfter.getUnit()
												.getName())) {
							emsMergerExgAfter.setLegalUnit2Gene(exgBefore
									.getInnerMergerData().getMateriel()
									.getPtNetWeight());
						}

					}
					emsMergerExgAfter = manualdeclearAction
							.saveEmsEdiMergerExgAfter(
									new Request(CommonVars.getCurrUser()),
									emsMergerExgAfter); // 保存归并后
					// //test
					// System.out.println("重量比例"+emsMergerExgAfter.getWeigthUnitGene());
					// System.out.println("第一法定"+emsMergerExgAfter.getLegalUnitGene());
					// System.out.println("第二法定"+emsMergerExgAfter.getLegalUnit2Gene());
					tableModelExgAfter.addRow(emsMergerExgAfter);
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
						"新增失败：！" + e.getMessage(), "提示", 2);
			} finally {
				CommonProgress.closeProgressDialog();
				setState();
			}
		}
	}

	class newMergerAfterImg extends Thread {
		List list = null;

		public newMergerAfterImg(List list) {
			this.list = list;
		}

		public void run() {
			try {
				CommonProgress.showProgressDialog(DgEmsEdiMerger.this);
				CommonProgress.setMessage("系统正在新增资料，请稍后...");
				EmsEdiMergerImgAfter emsMergerImg = null;
				for (int i = 0; i < list.size(); i++) {
					emsMergerImg = (EmsEdiMergerImgAfter) list.get(i);
					emsMergerImg.setEmsEdiMergerHead(emsEdiMergerHead);
					emsMergerImg.setModifyMark(ModifyMarkState.ADDED);
					if (isSendSign != null && "1".equals(isSendSign)) {
						emsMergerImg.setSendState(Integer
								.valueOf(SendState.WAIT_SEND));
					}
					emsMergerImg.setModifyTimes(emsEdiMergerHead
							.getModifyTimes());// 变更次数
					emsMergerImg.setChangeDate(new Date());// 修改日期
					emsMergerImg.setCompany(CommonVars.getCurrUser()
							.getCompany());
					emsMergerImg = manualdeclearAction
							.saveEmsEdiMergerImgAfter(
									new Request(CommonVars.getCurrUser()),
									emsMergerImg); // 保存归并后
					tableModelImgAfter.addRow(emsMergerImg);
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
						"新增失败：！" + e.getMessage(), "提示", 2);
			} finally {
				setState();
			}
		}
	}

	/**
	 * 
	 * This method initializes jbEdit
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJbEdit() {
		if (jbEdit == null) {
			jbEdit = new JButton();
			jbEdit.setText("修改");

			jbEdit.setPreferredSize(new Dimension(60, 30));
			jbEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						dataState = DataState.EDIT;
						setState();
					} else {
						DgEmsEdiMergerAfter dgemsafter = new DgEmsEdiMergerAfter();
						if (jTabbedPane.getSelectedIndex() == 1) {// 料件
							if (tableModelImgAfter.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgEmsEdiMerger.this,
										"请选择你要修改的归并后的料件资料", "确认", 2);
								return;
							}
							dgemsafter.setImg(true);
							dgemsafter.setTableModel(tableModelImgAfter);
						} else if (jTabbedPane.getSelectedIndex() == 2) {// 成品
							if (tableModelExgAfter.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgEmsEdiMerger.this,
										"请选择你要修改的归并后的成品资料", "确认", 2);
								return;
							}
							dgemsafter.setImg(false);
							dgemsafter.setTableModel(tableModelExgAfter);
						}
						dgemsafter.setEmsEdiMergerHead(emsEdiMergerHead);
						dgemsafter.setVisible(true);
					}
				}
			});

		}
		return jbEdit;
	}

	/**
	 * 
	 * This method initializes jbDelete
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJbDelete() { // 归并后料件成品表删除
		if (jbDelete == null) {
			jbDelete = new JButton();
			jbDelete.setText("删除");
			jbDelete.setPreferredSize(new Dimension(60, 30));
			jbDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 1) { // 料件
						if (tableModelImgAfter.getCurrentRows() == null) {
							JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
									"请选择你要删除的料件资料", "确认", 2);
							return;
						}
						if (JOptionPane.showConfirmDialog(DgEmsEdiMerger.this,
								"您确认要删除所选料件吗？\n注意：其归并前料件将一并被删除！", "删除提示", 0) == 0) {
							List ls = tableModelImgAfter.getCurrentRows();
							for (int j = 0; j < ls.size(); j++) {
								emsImgAfter = (EmsEdiMergerImgAfter) ls.get(j);
								List checkList = manualdeclearAction
										.checkToEmsH2kImg(new Request(
												CommonVars.getCurrUser()),
												emsImgAfter);
								if (checkList != null)
									if (checkList.size() > 0) {
										JOptionPane
												.showMessageDialog(
														DgEmsEdiMerger.this,
														"归并数据已经在电子账册存在，不能删除!!",
														"警告!!",
														JOptionPane.INFORMATION_MESSAGE);
										return;
									}
								if (emsEdiMergerHead.getDeclareType().equals(
										DelcareType.APPLICATION)
										|| // 申请备案状态
										emsImgAfter.getModifyMark().equals(
												ModifyMarkState.ADDED)) {
									System.out.println("-- 修改料件标记");
									manualdeclearAction.editImgBeforeList(
											new Request(CommonVars
													.getCurrUser()),
											emsImgAfter);

									manualdeclearAction
											.deleteEmsEdiMergerImgBeforeAll(
													new Request(CommonVars
															.getCurrUser()),
													emsImgAfter);
									manualdeclearAction
											.deleteEmsEdiMergerImgAfter(
													new Request(CommonVars
															.getCurrUser()),
													emsImgAfter);

									tableModelImgAfter.deleteRow(emsImgAfter);
									tableModelImgBefore.getList().clear();
									tableModelImgBefore.setList(new Vector());
								} else {
									List emsImgBefores = manualdeclearAction
											.findEmsEdiMergerImgBefore(
													new Request(CommonVars
															.getCurrUser()),
													emsImgAfter);
									for (int i = 0; i < emsImgBefores.size(); i++) {
										EmsEdiMergerImgBefore emsImg = (EmsEdiMergerImgBefore) emsImgBefores
												.toArray()[i];
										emsImg.setModifyMark(ModifyMarkState.DELETED);
										if (isSendSign != null
												&& "1".equals(isSendSign)) {
											emsImg.setSendState(1);// 准备申报
										}
										emsImg.setModifyTimes(emsEdiMergerHead
												.getModifyTimes()); // 变更次数
										emsImg = manualdeclearAction.saveEmsEdiMergerImgBefore(
												new Request(CommonVars
														.getCurrUser()), emsImg);
										tableModelImgBefore.updateRow(emsImg);
									}
									emsImgAfter
											.setModifyMark(ModifyMarkState.DELETED);
									emsImgAfter.setModifyTimes(emsEdiMergerHead
											.getModifyTimes()); // 变更次数
									if (isSendSign != null
											&& "1".equals(isSendSign)) {
										emsImgAfter.setSendState(1);// 准备申报
									}
									emsImgAfter = manualdeclearAction
											.saveEmsEdiMergerImgAfter(
													new Request(CommonVars
															.getCurrUser()),
													emsImgAfter);
									tableModelImgAfter.updateRow(emsImgAfter);
								}
							}
						}
					} else if (jTabbedPane.getSelectedIndex() == 2) {// 成品
						if (tableModelExgAfter.getCurrentRows() == null) {
							JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
									"请选择你要删除的成品资料", "确认", 2);
							return;
						}
						if (JOptionPane.showConfirmDialog(DgEmsEdiMerger.this,
								"您确认要删除所选成品吗？\n注意其归并前成品与单耗一并被删除！", "删除提示", 0) == 0) {
							List ls = tableModelExgAfter.getCurrentRows();
							for (int k = 0; k < ls.size(); k++) {
								emsExgAfter = (EmsEdiMergerExgAfter) ls.get(k); // 归并后
								List checkList = manualdeclearAction
										.checkToEmsH2kExg(new Request(
												CommonVars.getCurrUser()),
												emsExgAfter);
								if (checkList != null)
									if (checkList.size() > 0) {
										JOptionPane
												.showMessageDialog(
														DgEmsEdiMerger.this,
														"归并数据已经在电子账册存在，不能删除!!",
														"警告!!",
														JOptionPane.INFORMATION_MESSAGE);
										return;
									}

								if (emsEdiMergerHead.getDeclareType().equals(
										DelcareType.APPLICATION)
										|| emsExgAfter.getModifyMark().equals(
												ModifyMarkState.ADDED)) {
									System.out.println("-- 修改成品标记");
									manualdeclearAction.editExgBeforeList(
											new Request(CommonVars
													.getCurrUser()),
											emsExgAfter);

									manualdeclearAction.deleteBomByAfter(
											new Request(CommonVars
													.getCurrUser()),
											emsExgAfter);// 删除BOM
									manualdeclearAction
											.deleteEmsEdiMergerExgBeforeAll(
													new Request(CommonVars
															.getCurrUser()),
													emsExgAfter);// 删除归并前
									manualdeclearAction
											.deleteEmsEdiMergerExgAfter(
													new Request(CommonVars
															.getCurrUser()),
													emsExgAfter);

									tableModelExgAfter.deleteRow(emsExgAfter);
									tableModelExgBefore.getList().clear();
									tableModelExgBefore.setList(new Vector());
									tableModelBomExg.getList().clear();
									tableModelBomExg.setList(new Vector());
									tableModelVersion.getList().clear();
									tableModelVersion.setList(new Vector());
									tableModelBom.getList().clear();
									tableModelBom.setList(new Vector());
								} else {
									List list = manualdeclearAction
											.findEmsEdiMergerBomByAfter(
													new Request(CommonVars
															.getCurrUser()),
													emsExgAfter);
									if (list != null && !list.isEmpty()) {
										for (int j = 0; j < list.size(); j++) {
											EmsEdiMergerExgBom emsBom = (EmsEdiMergerExgBom) list
													.get(j);
											emsBom.setModifyMark(ModifyMarkState.DELETED);
											emsBom.setModifyTimes(emsEdiMergerHead
													.getModifyTimes());// 变更次数
											if (isSendSign != null
													&& "1".equals(isSendSign)) {
												emsBom.setSendState(1);// 准备申报
											}
											emsBom = manualdeclearAction.saveEmsEdiMergerExgBom(
													new Request(CommonVars
															.getCurrUser()),
													emsBom);
										}
									}
									List emsExgBefores = manualdeclearAction
											.findEmsEdiMergerExgBefore(
													new Request(CommonVars
															.getCurrUser()),
													emsExgAfter);
									for (int i = 0; i < emsExgBefores.size(); i++) {
										EmsEdiMergerExgBefore emsExg = (EmsEdiMergerExgBefore) emsExgBefores
												.toArray()[i];
										emsExg.setModifyMark(ModifyMarkState.DELETED);
										if (isSendSign != null
												&& "1".equals(isSendSign)) {
											emsExg.setSendState(1);// 准备申报
										}
										emsExg = manualdeclearAction.saveEmsEdiMergerExgBefore(
												new Request(CommonVars
														.getCurrUser()), emsExg);
										tableModelExgBefore.deleteRow(emsExg);
									}
									emsExgAfter
											.setModifyMark(ModifyMarkState.DELETED);
									if (isSendSign != null
											&& "1".equals(isSendSign)) {
										emsExgAfter.setSendState(1);// 准备申报
									}
									emsExgAfter.setModifyTimes(emsEdiMergerHead
											.getModifyTimes()); // 变更次数
									emsExgAfter = manualdeclearAction
											.saveEmsEdiMergerExgAfter(
													new Request(CommonVars
															.getCurrUser()),
													emsExgAfter);
									tableModelExgAfter.updateRow(emsExgAfter);
									EmsEdiMergerClientLogic.fillExgBefore(
											tableModelExgBefore,
											tableModelBomExg, emsExgAfter,
											jTableExgBefore, jTableBomExg);
								}
							}
							setState();
						}
					}
				}
			});
		}
		return jbDelete;
	}

	/**
	 * 
	 * This method initializes jbSave
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJbSave() {
		if (jbSave == null) {
			jbSave = new JButton();
			jbSave.setText("保存");
			jbSave.setPreferredSize(new Dimension(60, 30));
			jbSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					// 表头保存
					if (tfMachinAbility.getText().equals(""))
						tfMachinAbility.setText("0");
					if (tfMaxTurnMoney.getText().equals(""))
						tfMaxTurnMoney.setText("0");
					Double yearCan = EmsEdiMergerClientLogic
							.strToDouble(tfMachinAbility.getText());
					Double turnMoney = EmsEdiMergerClientLogic
							.strToDouble(tfMaxTurnMoney.getText());
					if (turnMoney.doubleValue() > (yearCan.doubleValue() / 2)) {
						JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
								"最大周转金额不能大于年加工生产能力的一半！", "提示！", 2);
						return;
					}
					fillEmsEdiMergerHead(emsEdiMergerHead);
					emsEdiMergerHead = manualdeclearAction
							.saveEmsEdiMergerHead(
									new Request(CommonVars.getCurrUser()),
									emsEdiMergerHead);
					tableModel.updateRow(emsEdiMergerHead);
					dataState = DataState.BROWSE;
					setState();

				}
			});

		}
		return jbSave;
	}

	/**
	 * 
	 * This method initializes jbExit
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJbExit() {
		if (jbExit == null) {
			jbExit = new JButton();
			jbExit.setText("关闭");
			jbExit.setPreferredSize(new Dimension(60, 30));
			jbExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgEmsEdiMerger.this.dispose();

				}
			});

		}
		return jbExit;
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
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("基本资料", null, getJpHead(), null);
			jTabbedPane.addTab("料件归并", null, getJpImg(), null);
			jTabbedPane.addTab("成品归并", null, getJpExg(), null);
			jTabbedPane.addTab("归并前BOM", null, getJpBom(), null);

			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {

						public void stateChanged(javax.swing.event.ChangeEvent e) {

							if (jTabbedPane.getSelectedIndex() == 3) {
								// if (tableModelExgBefore.getCurrentRow() ==
								// null) {
								// return;
								// }
								// emsExgBefore = (EmsEdiMergerExgBefore)
								// tableModelBomExg
								// .getCurrentRow();
								// List dataSourceVersion = null;
								// if (emsExgBefore != null) {
								// dataSourceVersion = manualdeclearAction
								// .findEmsEdiMergerVersion(
								// // 得到版本号
								// new Request(CommonVars
								// .getCurrUser()),
								// emsExgBefore);
								// }
								// if (dataSourceVersion != null
								// && dataSourceVersion.size() > 0) {
								// System.out.println("=00="+dataSourceVersion.size());
								// initTableVersion(dataSourceVersion);
								// System.out.println("=11="+dataSourceVersion.size());
								// // valuechange();
								// } else {
								// initTableVersion(new Vector());
								// tableModelBom = EmsEdiMergerClientLogic
								// .initTableBom(jTableBom,
								// new Vector());
								// }
								// setState();

								// 当在成品归并的Jpanel添加成品后，切换到最好一个Jpanel归并前Bom是，重新刷新jTableBomExg数据源
								// 归并前所有的成品
								dataSourceBomExg = manualdeclearAction
										.findEmsEdiMergerExgBefore(new Request(
												CommonVars.getCurrUser()),
												emsEdiMergerHead);
								if (dataSourceBomExg != null
										&& dataSourceBomExg.size() > 0) {
									tableModelBomExg = EmsEdiMergerClientLogic
											.initTableExgBefore(jTableBomExg,
													dataSourceBomExg);
									EmsEdiMergerClientLogic
											.transModifyMark(jTableBomExg);
									EmsEdiMergerClientLogic
											.transSendState(jTableBomExg);
									emsExgBefore = (EmsEdiMergerExgBefore) dataSourceBomExg
											.get(0);
									List dataSourceVersion = manualdeclearAction
											.findEmsEdiMergerVersion(
													// 得到版本号
													new Request(CommonVars
															.getCurrUser()),
													emsExgBefore);
									if (dataSourceVersion != null
											&& dataSourceVersion.size() > 0) {
										initTableVersion(dataSourceVersion);
										valuechange();
									} else {
										initTableVersion(new Vector());
										tableModelBom = EmsEdiMergerClientLogic
												.initTableBom(jTableBom,
														new Vector());
									}
								}

							} else {
								setState();
							}

						}
					});

		}
		return jTabbedPane;
	}

	/**
	 * 
	 * This method initializes jpExg
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJpExg() {
		if (jpExg == null) {
			jpExg = new JPanel();
			jpExg.setLayout(new BorderLayout());
			jpExg.add(getJSplitPane1(), java.awt.BorderLayout.CENTER);
		}
		return jpExg;
	}

	/**
	 * 
	 * This method initializes jpImg
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJpImg() {
		if (jpImg == null) {
			jpImg = new JPanel();
			jpImg.setLayout(new BorderLayout());
			jpImg.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jpImg;
	}

	/**
	 * 
	 * This method initializes jpHead
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJpHead() {
		if (jpHead == null) {

			jpHead = new JPanel();
			jpHead.setLayout(null);

			jpHead.add(getJPanel7(), null);

		}
		return jpHead;
	}

	/**
	 * 
	 * This method initializes jpBom
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJpBom() {
		if (jpBom == null) {
			jpBom = new JPanel();
			jpBom.setLayout(new BorderLayout());
			jpBom.add(getJSplitPane2(), java.awt.BorderLayout.CENTER);
		}
		return jpBom;
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
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(new Rectangle(132, 21, 142, 25));
		}
		return tfEmsNo;
	}

	/**
	 * 
	 * This method initializes jTextField1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfChangeTimes() {
		if (tfChangeTimes == null) {
			tfChangeTimes = new JTextField();
			tfChangeTimes.setEditable(false);
			tfChangeTimes.setBounds(new Rectangle(389, 24, 142, 22));
		}
		return tfChangeTimes;
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
	private JTextField getTfCopEmsNo() {
		if (tfCopEmsNo == null) {
			tfCopEmsNo = new JTextField();
			tfCopEmsNo.setBounds(new Rectangle(618, 22, 142, 25));
		}
		return tfCopEmsNo;
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
	private JTextField getTfSancEmsNo() {
		if (tfSancEmsNo == null) {
			tfSancEmsNo = new JTextField();
			tfSancEmsNo.setBounds(new Rectangle(132, 53, 142, 25));
		}
		return tfSancEmsNo;
	}

	/**
	 * 
	 * This method initializes jTextField4
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfEmsApprNo() {
		if (tfEmsApprNo == null) {
			tfEmsApprNo = new JTextField();
			tfEmsApprNo.setEditable(false);
			tfEmsApprNo.setBounds(new Rectangle(389, 53, 142, 25));
		}
		return tfEmsApprNo;
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
	private JTextField getTfEmsType() {
		if (tfEmsType == null) {
			tfEmsType = new JTextField();
			tfEmsType.setEditable(false);
			tfEmsType.setBounds(new Rectangle(618, 54, 142, 25));
		}
		return tfEmsType;
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
	private JTextField getTfTradeCode() {
		if (tfTradeCode == null) {
			tfTradeCode = new JTextField();
			tfTradeCode.setEditable(false);
			tfTradeCode.setBounds(new Rectangle(132, 83, 142, 25));
		}
		return tfTradeCode;
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
	private JTextField getTfTradeName() {
		if (tfTradeName == null) {
			tfTradeName = new JTextField();
			tfTradeName.setBounds(new Rectangle(389, 83, 373, 25));
		}
		return tfTradeName;
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
	private JTextField getTfMachCode() {
		if (tfMachCode == null) {
			tfMachCode = new JTextField();
			tfMachCode.setEditable(false);
			tfMachCode.setBounds(new Rectangle(132, 117, 142, 25));
		}
		return tfMachCode;
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
	private JTextField getTfMachName() {
		if (tfMachName == null) {
			tfMachName = new JTextField();
			tfMachName.setBounds(new Rectangle(389, 117, 373, 25));
		}
		return tfMachName;
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
	private JTextField getTfTel() {
		if (tfTel == null) {
			tfTel = new JTextField();
			tfTel.setBounds(new Rectangle(132, 153, 142, 25));
		}
		return tfTel;
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
	private JTextField getTfAddress() {
		if (tfAddress == null) {
			tfAddress = new JTextField();
			tfAddress.setBounds(new Rectangle(389, 153, 373, 25));
		}
		return tfAddress;
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
	private JTextField getTfDeclareErType() {
		if (tfDeclareErType == null) {
			tfDeclareErType = new JTextField();
			tfDeclareErType.setEditable(false);
			tfDeclareErType.setBounds(new Rectangle(618, 183, 142, 25));
		}
		return tfDeclareErType;
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
	private JCalendarComboBox getCmbBeginDate() {
		if (cmbBeginDate == null) {
			cmbBeginDate = new JCalendarComboBox();
			cmbBeginDate.setBounds(new Rectangle(132, 182, 137, 25));
		}
		return cmbBeginDate;
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
	private JCalendarComboBox getCmbEndDate() {
		if (cmbEndDate == null) {
			cmbEndDate = new JCalendarComboBox();
			cmbEndDate.setBounds(new Rectangle(389, 182, 142, 25));
		}
		return cmbEndDate;
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
	private JTextField getTfInputUser() {
		if (tfInputUser == null) {
			tfInputUser = new JTextField();
			tfInputUser.setEditable(false);
			tfInputUser.setBounds(new Rectangle(132, 245, 142, 25));
		}
		return tfInputUser;
	}

	/**
	 * 
	 * This method initializes jTextField17
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfInputDate() {
		if (tfInputDate == null) {
			tfInputDate = new JTextField();
			tfInputDate.setEditable(false);
			tfInputDate.setBounds(new Rectangle(389, 245, 142, 25));
		}
		return tfInputDate;
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
			jTextField18.setEditable(false);
			jTextField18.setBounds(new Rectangle(618, 246, 142, 25));
		}
		return jTextField18;
	}

	/**
	 * 
	 * This method initializes jTextField19
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfDeclareDate() {
		if (tfDeclareDate == null) {
			tfDeclareDate = new JTextField();
			tfDeclareDate.setEditable(false);
			tfDeclareDate.setBounds(new Rectangle(132, 277, 142, 25));
		}
		return tfDeclareDate;
	}

	/**
	 * 
	 * This method initializes jTextField20
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfDeclareTime() {
		if (tfDeclareTime == null) {
			tfDeclareTime = new JTextField();
			tfDeclareTime.setEditable(false);
			tfDeclareTime.setBounds(new Rectangle(389, 276, 142, 25));
		}
		return tfDeclareTime;
	}

	/**
	 * 
	 * This method initializes jTextField21
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField21() {
		if (jTextField21 == null) {
			jTextField21 = new JTextField();
			jTextField21.setEditable(false);
			jTextField21.setBounds(new Rectangle(618, 278, 142, 25));
		}
		return jTextField21;
	}

	/**
	 * 
	 * This method initializes jTextField22
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextField();
			tfNote.setBounds(new Rectangle(132, 308, 629, 25));
		}
		return tfNote;
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
	 * This method initializes jFormattedTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getTfInvestAmount() {
		if (tfInvestAmount == null) {
			tfInvestAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfInvestAmount.setBounds(new Rectangle(132, 216, 106, 25));
			tfInvestAmount.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfInvestAmount;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField1
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getTfMachinAbility() {
		if (tfMachinAbility == null) {
			tfMachinAbility = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfMachinAbility.setBounds(new Rectangle(389, 216, 107, 25));
			tfMachinAbility.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfMachinAbility;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getTfMaxTurnMoney() {
		if (tfMaxTurnMoney == null) {
			tfMaxTurnMoney = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfMaxTurnMoney.setBounds(new Rectangle(618, 217, 109, 25));
			tfMaxTurnMoney.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfMaxTurnMoney;
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
			defaultFormatterFactory.setDefaultFormatter(EmsEdiMergerClientLogic
					.getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(EmsEdiMergerClientLogic
					.getNumberFormatter());
		}
		return defaultFormatterFactory;
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
	 * 
	 * This method initializes jSplitPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(5);
			jSplitPane.setDividerLocation(150);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
		}
		return jSplitPane;
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
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"归并后料件",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
					new java.awt.Color(51, 51, 51)));
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "归并前料件",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel1.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jTableImgAfter
	 * 
	 * 
	 * 归并后料件
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTableImgAfter() {
		if (jTableImgAfter == null) {
			jTableImgAfter = new JTable();
			jTableImgAfter.setRowHeight(25);
			jTableImgAfter.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModelImgAfter == null)
								return;
							EmsEdiMergerImgAfter emsImgAfter = (EmsEdiMergerImgAfter) tableModelImgAfter
									.getCurrentRow();
							if (emsImgAfter == null)
								return;
							EmsEdiMergerClientLogic.fillImgBefore(
									tableModelImgBefore, emsImgAfter,
									jTableImgBefore);
							setState();
						}
					});
		}
		return jTableImgAfter;
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
			jScrollPane.setViewportView(getJTableImgAfter());
		}
		return jScrollPane;
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
			jLabel28 = new JLabel();
			jLabel28.setText("   当前归并前有：0 笔记录");
			jLabel28.setForeground(java.awt.Color.red);
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(0);
			f.setHgap(0);
			jToolBar1 = new JToolBar();
			jToolBar1.setPreferredSize(new Dimension(508, 34));
			jToolBar1.setLayout(f);
			jToolBar1.add(getJbAddImgBefore());
			jToolBar1.add(getJbEditImgBefore());
			jToolBar1.add(getJbDeleteImgBefore());
			jToolBar1.add(getJButton11());
			jToolBar1.add(getJButton4());

			jToolBar1.add(jLabel28);
		}
		return jToolBar1;
	}

	/**
	 * 
	 * This method initializes jbAddImgBefore
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJbAddImgBefore() { // 增加归并料件前资料
		if (jbAddImgBefore == null) {
			jbAddImgBefore = new JButton();
			jbAddImgBefore.setText("新增");
			jbAddImgBefore.setPreferredSize(new Dimension(65, 30));
			jbAddImgBefore
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {
							// 取归并前料件
							if (tableModelImgAfter.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgEmsEdiMerger.this, "请选择归并后料件资料",
										"确认", 2);
								return;
							}
							emsImgAfter = (EmsEdiMergerImgAfter) tableModelImgAfter
									.getCurrentRow(); // 取得归并后对象
							if (emsImgAfter.getModifyMark().equals(
									ModifyMarkState.DELETED)) {
								JOptionPane.showMessageDialog(
										DgEmsEdiMerger.this, "归并后料件已删除，不能新增！",
										"确认", 2);
								return;
							}
							List list = (List) CommonQuery.getInstance()
									.getMergerImgBefore10(false, emsImgAfter,
											emsEdiMergerHead);
							if (list == null || list.isEmpty())
								return;
							new addImgBefore(list).start();
						}
					});

		}
		return jbAddImgBefore;
	}

	class addImgBefore extends Thread {
		List list = null;

		public addImgBefore(List list) {
			this.list = list;
		}

		public void run() {
			try {
				CommonProgress.showProgressDialog(DgEmsEdiMerger.this);
				CommonProgress.setMessage("系统正在新增资料，请稍后...");

				// EmsEdiMergerImgBefore emsImgBefore = null;
				// for (int i = 0; i < list.size(); i++) {
				// emsImgBefore = (EmsEdiMergerImgBefore) list.get(i);
				// emsImgBefore.setEmsEdiMergerImgAfter(emsImgAfter);
				// emsImgBefore.setCompany(CommonVars.getCurrUser()
				// .getCompany());
				// emsImgBefore.setModifyMark(ModifyMarkState.ADDED);
				// if (isSendSign != null && "1".equals(isSendSign)) {
				// emsImgBefore.setSendState(Integer
				// .valueOf(SendState.WAIT_SEND));
				// }
				// emsImgBefore.setModifyTimes(emsEdiMergerHead
				// .getModifyTimes());
				// emsImgBefore.setChangeDate(new Date());
				// emsImgBefore = manualdeclearAction
				// .saveEmsEdiMergerImgBefore(
				// new Request(CommonVars.getCurrUser()),
				// emsImgBefore);
				//
				// InnerMergeData data = emsImgBefore.getInnerMergerData();
				// data.setIsExistMerger(new Boolean(true));
				// commonBaseCodeAction.saveInnerMergeData(new Request(
				// CommonVars.getCurrUser()), data);
				// }

				commonBaseCodeAction.handleEmsEdiMergerImgBefore(list,
						emsImgAfter, CommonVars.getCurrUser().getCompany(),
						isSendSign, emsEdiMergerHead);

				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
						"新增失败：！" + e.getMessage(), "提示", 2);
			} finally {
				CommonProgress.closeProgressDialog();
				tableModelImgBefore = EmsEdiMergerClientLogic.fillImgBefore(
						tableModelImgBefore, emsImgAfter, jTableImgBefore);
				EmsEdiMergerClientLogic.transSendState(jTableImgBefore);
				setState();
			}
		}
	}

	/**
	 * 
	 * This method initializes jbEditImgBefore
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJbEditImgBefore() { // 修改归并前料件资料
		if (jbEditImgBefore == null) {
			jbEditImgBefore = new JButton();
			jbEditImgBefore.setText("修改");
			jbEditImgBefore.setPreferredSize(new Dimension(65, 30));
			jbEditImgBefore
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelImgBefore.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgEmsEdiMerger.this,
										"请选择你要修改的归并前的料件资料", "确认", 2);
								return;
							}
							EmsEdiMergerImgBefore imgBefore = (EmsEdiMergerImgBefore) tableModelImgBefore
									.getCurrentRow();
							if (imgBefore.getEmsEdiMergerImgAfter()
									.getModifyMark()
									.equals(ModifyMarkState.DELETED)) {
								JOptionPane.showMessageDialog(
										DgEmsEdiMerger.this, "归并后料件已删除，不能修改！",
										"确认", 2);
								return;
							}
							DgEmsEdiMergerBefore dgemsbefore = new DgEmsEdiMergerBefore();
							dgemsbefore.setImg(true);
							dgemsbefore.setEmsEdiMergerHead(emsEdiMergerHead);
							dgemsbefore.setTableModel(tableModelImgBefore);
							if (CommonVars.getH2kMergerModifyNo() != null) {
								if (CommonVars.getH2kMergerModifyNo()) {
									dgemsbefore.setModifySeqNUm(true);
								}
							}
							dgemsbefore.setVisible(true);

						}
					});

		}
		return jbEditImgBefore;
	}

	/**
	 * 
	 * This method initializes jbDeleteImgBefore
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJbDeleteImgBefore() {
		if (jbDeleteImgBefore == null) { // 归并前料件删除
			jbDeleteImgBefore = new JButton();
			jbDeleteImgBefore.setText("删除");
			jbDeleteImgBefore.setPreferredSize(new Dimension(65, 30));
			jbDeleteImgBefore
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							if (tableModelImgBefore.getCurrentRows() == null) {
								JOptionPane.showMessageDialog(
										DgEmsEdiMerger.this, "请选择你要删除的料件资料",
										"确认", 2);
								return;
							}
							List ls = tableModelImgBefore.getCurrentRows();
							for (int i = 0; i < ls.size(); i++) {
								emsImgBefore = (EmsEdiMergerImgBefore) ls
										.get(i);

								if (emsEdiMergerHead.getDeclareType().equals(
										DelcareType.APPLICATION)
										|| emsImgBefore.getModifyMark().equals(
												ModifyMarkState.ADDED)) {
									manualdeclearAction
											.deleteEmsEdiMergerImgBefore(
													new Request(CommonVars
															.getCurrUser()),
													emsImgBefore);

									Integer seqNum = emsImgAfter.getSeqNum();
									String ptNo = emsImgBefore.getPtNo();
									commonBaseCodeAction.editMergerFlag0(
											new Request(CommonVars
													.getCurrUser()),
											MaterielType.MATERIEL, ptNo);

									tableModelImgBefore.deleteRow(emsImgBefore);
								} else {
									emsImgBefore
											.setModifyMark(ModifyMarkState.DELETED);
									if (isSendSign != null
											&& "1".equals(isSendSign)) {
										emsImgBefore.setSendState(1); // 准备申报
									}
									emsImgBefore = manualdeclearAction
											.saveEmsEdiMergerImgBefore(
													new Request(CommonVars
															.getCurrUser()),
													emsImgBefore);
									tableModelImgBefore.updateRow(emsImgBefore);
								}
								setState();
							}
						}
					});
		}
		return jbDeleteImgBefore;
	}

	/**
	 * 
	 * This method initializes jTableImgBefore 归并前料件
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
	 * This method initializes jScrollPane1
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTableImgBefore());
		}
		return jScrollPane1;
	}

	/**
	 * 
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setDividerLocation(150);
			jSplitPane1.setDividerSize(5);
			jSplitPane1.setTopComponent(getJPanel2());
			jSplitPane1.setBottomComponent(getJPanel3());
		}
		return jSplitPane1;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "归并后成品",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel2.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "归并前成品",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel3.add(getJToolBar2(), java.awt.BorderLayout.NORTH);
			jPanel3.add(getJScrollPane3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * 
	 * This method initializes jTableExgAfter
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTableExgAfter() {
		if (jTableExgAfter == null) {
			jTableExgAfter = new JTable();
			jTableExgAfter.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModelExgAfter == null)
								return;
							emsExgAfter = (EmsEdiMergerExgAfter) tableModelExgAfter
									.getCurrentRow();
							if (emsExgAfter == null)
								return;
							EmsEdiMergerClientLogic.fillExgBefore(
									tableModelExgBefore, tableModelBomExg,
									emsExgAfter, jTableExgBefore, jTableBom);
							setState();
						}
					});
		}
		return jTableExgAfter;
	}

	/**
	 * 
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTableExgAfter());
		}
		return jScrollPane2;
	}

	/**
	 * 
	 * This method initializes jToolBar2
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			jLabel29 = new JLabel();
			jLabel29.setText("   当前归并前有： 0 笔记录");
			jLabel29.setForeground(java.awt.Color.red);
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(0);
			f.setHgap(0);
			jToolBar2 = new JToolBar();
			jToolBar2.setPreferredSize(new Dimension(511, 34));
			jToolBar2.setLayout(f);
			jToolBar2.add(getJbAddExgBefore());
			jToolBar2.add(getJbEditExgBefore());
			jToolBar2.add(getJbDeleteExgBefore());
			jToolBar2.add(getJButton10());
			jToolBar2.add(getJButton5());
			jToolBar2.add(jLabel29);
		}
		return jToolBar2;
	}

	/**
	 * 
	 * This method initializes jbAddExgBefore
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJbAddExgBefore() { // 新增归并前成品
		if (jbAddExgBefore == null) {
			jbAddExgBefore = new JButton();
			jbAddExgBefore.setText("新增");
			jbAddExgBefore.setPreferredSize(new Dimension(65, 30));
			jbAddExgBefore
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {
							// 取归并前成品
							if (tableModelExgAfter.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgEmsEdiMerger.this, "请选择归并后成品资料",
										"确认", 2);
								return;
							}
							emsExgAfter = (EmsEdiMergerExgAfter) tableModelExgAfter
									.getCurrentRow(); // 取得归并后对象
							if (emsExgAfter.getModifyMark().equals(
									ModifyMarkState.DELETED)) {
								JOptionPane.showMessageDialog(
										DgEmsEdiMerger.this, "归并后成品已删除，不能新增！",
										"确认", 2);
								return;
							}

							List list = (List) CommonQuery.getInstance()
									.getMergerExgBefore10(false, emsExgAfter,
											emsEdiMergerHead);
							if (list == null || list.isEmpty())
								return;
							new addExgBefore(list).start();

						}
					});

		}
		return jbAddExgBefore;
	}

	class addExgBefore extends Thread {
		List list = null;

		public addExgBefore(List list) {
			this.list = list;
		}

		public void run() {
			try {
				CommonProgress.showProgressDialog(DgEmsEdiMerger.this);
				CommonProgress.setMessage("系统正在新增资料，请稍后...");

				// EmsEdiMergerExgBefore emsExgBefore = null;
				// for (int i = 0; i < list.size(); i++) {
				// emsExgBefore = (EmsEdiMergerExgBefore) list.get(i);
				// emsExgBefore.setEmsEdiMergerExgAfter(emsExgAfter);
				// emsExgBefore.setCompany(CommonVars.getCurrUser()
				// .getCompany());
				// emsExgBefore.setModifyMark(ModifyMarkState.ADDED);
				// if (isSendSign != null && "1".equals(isSendSign)) {
				// emsExgBefore.setSendState(Integer
				// .valueOf(SendState.WAIT_SEND));
				// }
				// emsExgBefore.setModifyTimes(emsEdiMergerHead
				// .getModifyTimes());
				// emsExgBefore.setChangeDate(new Date());
				// emsExgBefore = manualdeclearAction
				// .saveEmsEdiMergerExgBefore(new Request(CommonVars
				// .getCurrUser()), emsExgBefore);
				// InnerMergeData data = emsExgBefore.getInnerMergerData();
				// data.setIsExistMerger(new Boolean(true));
				// commonBaseCodeAction.saveInnerMergeData(new Request(
				// CommonVars.getCurrUser()), data);
				// }
				commonBaseCodeAction.handleEmsEdiMergerExgBefore(list,
						emsExgAfter, CommonVars.getCurrUser().getCompany(),
						isSendSign, emsEdiMergerHead);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
						"新增失败：！" + e.getMessage(), "提示", 2);
			} finally {
				EmsEdiMergerClientLogic.fillExgBefore(tableModelExgBefore,
						tableModelBomExg, emsExgAfter, jTableExgBefore,
						jTableBomExg);
				EmsEdiMergerClientLogic.transSendState(jTableExgBefore);
				setState();
			}
		}
	}

	/**
	 * 
	 * This method initializes jbEditExgBefore
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJbEditExgBefore() { // 修改归并前成品
		if (jbEditExgBefore == null) {
			jbEditExgBefore = new JButton();
			jbEditExgBefore.setText("修改");
			jbEditExgBefore.setPreferredSize(new Dimension(65, 30));
			jbEditExgBefore
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {

							if (tableModelExgBefore.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgEmsEdiMerger.this,
										"请选择你要修改的归并前的成品资料", "确认", 2);
								return;
							}
							EmsEdiMergerExgBefore exgBefore = (EmsEdiMergerExgBefore) tableModelExgBefore
									.getCurrentRow();
							if (exgBefore.getEmsEdiMergerExgAfter()
									.getModifyMark()
									.equals(ModifyMarkState.DELETED)) {
								JOptionPane.showMessageDialog(
										DgEmsEdiMerger.this, "归并后成品已删除，不能修改！",
										"确认", 2);
								return;
							}
							DgEmsEdiMergerBefore dgemsbefore = new DgEmsEdiMergerBefore();
							dgemsbefore.setImg(false);
							dgemsbefore.setEmsEdiMergerHead(emsEdiMergerHead);
							dgemsbefore.setTableModel(tableModelExgBefore);
							if (CommonVars.getH2kMergerModifyNo() != null) {
								if (CommonVars.getH2kMergerModifyNo()) {
									dgemsbefore.setModifySeqNUm(true);
								}
							}
							dgemsbefore.setVisible(true);

						}
					});

		}
		return jbEditExgBefore;
	}

	/**
	 * 
	 * This method initializes jbDeleteExgBefore
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJbDeleteExgBefore() { // 删除归并前成品
		if (jbDeleteExgBefore == null) {
			jbDeleteExgBefore = new JButton();
			jbDeleteExgBefore.setText("删除");
			jbDeleteExgBefore.setPreferredSize(new Dimension(65, 30));
			jbDeleteExgBefore
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {

							if (tableModelExgBefore.getCurrentRows() == null) {
								JOptionPane.showMessageDialog(
										DgEmsEdiMerger.this, "请选择你要删除的成品资料",
										"确认", 2);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									DgEmsEdiMerger.this,
									"您确认要删除所选成品吗？\n注意其归并后成品与单耗一并被删除！", "删除提示",
									0) != 0) {
								return;
							}
							List ls = tableModelExgBefore.getCurrentRows();
							for (int j = 0; j < ls.size(); j++) {
								emsExgBefore = (EmsEdiMergerExgBefore) ls
										.get(j);
								if (emsEdiMergerHead.getDeclareType().equals(
										DelcareType.APPLICATION)
										|| emsExgBefore.getModifyMark().equals(
												ModifyMarkState.ADDED)) {
									Integer seqNum = emsExgAfter.getSeqNum();
									String ptNo = emsExgBefore.getPtNo();
									manualdeclearAction
											.deleteEmsEdiMergerMergerBomAll(
													new Request(CommonVars
															.getCurrUser()),
													emsExgBefore);
									manualdeclearAction
											.deleteEmsEdiMergerExgBefore(
													new Request(CommonVars
															.getCurrUser()),
													emsExgBefore);
									tableModelExgBefore.deleteRow(emsExgBefore);
									EmsEdiMergerClientLogic.fillExgBefore(
											tableModelExgBefore,
											tableModelBomExg, emsExgBefore
													.getEmsEdiMergerExgAfter(),
											jTableExgBefore, jTableBomExg); // 注意填了两个JTable
									tableModelVersion.getList().clear();
									tableModelVersion.setList(new Vector());
									tableModelBom.getList().clear();
									tableModelBom.setList(new Vector());

									System.out.println("  -- 修改内部归并标记");
									commonBaseCodeAction.editMergerFlag0(
											new Request(CommonVars
													.getCurrUser()),
											MaterielType.FINISHED_PRODUCT, ptNo);
								} else {
									List list = manualdeclearAction // BOM
											.findEmsEdiBom(new Request(
													CommonVars.getCurrUser()),
													emsExgBefore);
									if (list != null && !list.isEmpty()) {
										for (int i = 0; i < list.size(); i++) {
											EmsEdiMergerExgBom emsBom = (EmsEdiMergerExgBom) list
													.get(i);
											emsBom.setModifyMark(ModifyMarkState.DELETED);
											if (isSendSign != null
													&& "1".equals(isSendSign)) {
												emsBom.setSendState(1); // 准备申报
											}
											emsBom = manualdeclearAction.saveEmsEdiMergerExgBom(
													new Request(CommonVars
															.getCurrUser()),
													emsBom);
											tableModelBom.updateRow(emsBom);
										}
									}
									emsExgBefore
											.setModifyMark(ModifyMarkState.DELETED);
									if (isSendSign != null
											&& "1".equals(isSendSign)) {
										emsExgBefore.setSendState(1); // 准备申报
									}
									emsExgBefore = manualdeclearAction
											.saveEmsEdiMergerExgBefore(
													new Request(CommonVars
															.getCurrUser()),
													emsExgBefore);
									tableModelExgBefore.updateRow(emsExgBefore);

								}
								setState();
							}
						}
					});

		}
		return jbDeleteExgBefore;
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
			jScrollPane3.setViewportView(getJTableExgBefore());
		}
		return jScrollPane3;
	}

	/**
	 * 
	 * This method initializes jSplitPane2
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane2() {
		if (jSplitPane2 == null) {
			jSplitPane2 = new JSplitPane();
			jSplitPane2.setDividerLocation(150);
			jSplitPane2.setDividerSize(5);
			jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane2.setTopComponent(getJPanel4());
			jSplitPane2.setBottomComponent(getJPanel5());
		}
		return jSplitPane2;
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
			jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "归并前成品",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel4.add(getJScrollPane4(), java.awt.BorderLayout.CENTER);
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
			jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "成品BOM表",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel5.add(getJToolBar3(), java.awt.BorderLayout.NORTH);
			jPanel5.add(getJSplitPane3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel5;
	}

	/**
	 * 
	 * This method initializes jTableBomExg
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTableBomExg() {
		if (jTableBomExg == null) {
			jTableBomExg = new JTable();
			jTableBomExg.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					if (tableModelBomExg == null) {
						return;
					}
					if (jTabbedPane.getSelectedIndex() != 3) {
						return;
					}
					if (tableModelBomExg.getCurrentRow() == null) {
						return;
					}
					emsExgBefore = (EmsEdiMergerExgBefore) tableModelBomExg
							.getCurrentRow(); // 获得归并关系

					List dataSourceVersion = null;
					dataSourceVersion = manualdeclearAction
							.findEmsEdiMergerVersion(
									// 得到版本号
									new Request(CommonVars.getCurrUser()),
									emsExgBefore);
					if (dataSourceVersion != null
							&& dataSourceVersion.size() > 0) {

						initTableVersion(dataSourceVersion);
						valuechange();
					} else {
						initTableVersion(new Vector());
						tableModelBom = EmsEdiMergerClientLogic.initTableBom(
								jTableBom, new Vector());
					}
					setState();
				}
			});
			// jTableBomExg.getSelectionModel().addListSelectionListener(
			// new ListSelectionListener() {
			// public void valueChanged(ListSelectionEvent e) {
			// if (e.getValueIsAdjusting()) {
			// return;
			// }
			//
			// }
			// });
		}
		return jTableBomExg;
	}

	// class findBom extends Thread {
	// EmsEdiMergerExgBefore emsExgBefore = null;
	//
	// public findBom(EmsEdiMergerExgBefore emsExgBefore) {
	// this.emsExgBefore = emsExgBefore;
	// }
	//
	// public void run() {
	// try {
	// CommonProgress.showProgressDialog(DgEmsEdiMerger.this);
	// CommonProgress.setMessage("系统正在BOM资料，请稍后...");
	// List dataSourceVersion = null;
	// dataSourceVersion = manualdeclearAction
	// .findEmsEdiMergerVersion(
	// // 得到版本号
	// new Request(CommonVars.getCurrUser()),
	// emsExgBefore);
	// if (dataSourceVersion != null && dataSourceVersion.size() > 0) {
	// initTableVersion(dataSourceVersion);
	// emsEdiMergerVersion = (EmsEdiMergerVersion) dataSourceVersion
	// .get(0); // 第一个版本号
	// Integer version = emsEdiMergerVersion.getVersion();
	// dataSourceBom = manualdeclearAction.findEmsEdiMergerBom(
	// new Request(CommonVars.getCurrUser()), // 得到BOM
	// emsEdiMergerVersion);
	// if (dataSourceBom != null && !dataSourceBom.isEmpty()) {
	// tableModelBom = EmsEdiMergerClientLogic.initTableBom(
	// jTableBom, dataSourceBom);
	// EmsEdiMergerClientLogic.transModifyMark(jTableBom);
	// EmsEdiMergerClientLogic.transSendState(jTableBom);
	// } else {
	// tableModelBom = EmsEdiMergerClientLogic.initTableBom(
	// jTableBom, new Vector());
	// }
	// } else {
	// initTableVersion(new Vector());
	// tableModelBom = EmsEdiMergerClientLogic.initTableBom(
	// jTableBom, new Vector());
	// }
	// CommonProgress.closeProgressDialog();
	// } catch (Exception e) {
	// CommonProgress.closeProgressDialog();
	// JOptionPane.showMessageDialog(DgEmsEdiMerger.this, "获取失败！"
	// + e.getMessage(), "提示", 2);
	// } finally {
	// setState();
	// }
	// }
	// }

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
			jScrollPane4.setViewportView(getJTableBomExg());
		}
		return jScrollPane4;
	}

	/**
	 * 
	 * This method initializes jToolBar3
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar3() {
		if (jToolBar3 == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(0);
			f.setHgap(0);
			jToolBar3 = new JToolBar();
			jToolBar3.setLayout(f);
			jToolBar3.setFloatable(false);
			jToolBar3.add(getJButton1());
			jToolBar3.add(getJButton2());
			jToolBar3.add(getBtnCope());
			jToolBar3.add(getBtnAddBom());
			jToolBar3.add(getBtnEditBom());
			jToolBar3.add(getBtnDeleteBom());
			// jToolBar3.add(getBtnCopyBom());
			// jToolBar3.add(getBtnPasteBom());
			// jToolBar3.add(getJButton1());
			// jToolBar3.add(getJButton2());
			jToolBar3.add(getJBimport());
			jToolBar3.add(getJButton8());
			jToolBar3.add(getJButton6());
			jToolBar3.add(getBtnWasterToInt());
			jToolBar3.add(getJRadioButton1());
			jToolBar3.add(getJRadioButton());
		}
		return jToolBar3;
	}

	/**
	 * 
	 * This method initializes jbAddBom
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnAddBom() { // 新增成品Bom
		if (btnAddBom == null) {
			btnAddBom = new JButton();
			btnAddBom.setText("新增子件");
			btnAddBom.setPreferredSize(new Dimension(60, 30));
			btnAddBom.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					/*
					 * getJPopupMenu().show(btnAddBom, 0,
					 * -getJPopupMenu().getHeight());
					 * getJPopupMenu().show(btnAddBom, 0,
					 * -getJPopupMenu().getHeight());
					 */
					if (jRadioButton.isSelected()) {
						fromMaterielBom();// 从产品结构
					} else if (jRadioButton1.isSelected()) {
						fromEmsEdiMergerBefore();
					}
					valuechange();
				}
			});
		}
		return btnAddBom;
	}

	/**
	 * 
	 * This method initializes jbEditBom
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnEditBom() {
		if (btnEditBom == null) {
			btnEditBom = new JButton();
			btnEditBom.setText("修改子件");
			btnEditBom.setPreferredSize(new Dimension(60, 30));
			btnEditBom.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelBom.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
								"请选择你要修改的归并后的料件资料", "确认", 2);
						return;
					}
					EmsEdiMergerExgBom bom = (EmsEdiMergerExgBom) tableModelBom
							.getCurrentRow();
					// 有待修改
					/*
					 * if
					 * (bom.getEmsEdiMergerExgBefore().getModifyMark().equals(
					 * ModifyMarkState.DELETED)){
					 * JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
					 * "归并后成品已删除，不能修改！", "确认", 2); return; }
					 */
					DgEmsEdiMergerBom dgemsBom = new DgEmsEdiMergerBom();
					dgemsBom.setEmsEdiMergerHead(emsEdiMergerHead);
					dgemsBom.setTableModel(tableModelBom);
					dgemsBom.setVisible(true);
				}
			});

		}
		return btnEditBom;
	}

	/**
	 * 
	 * This method initializes jbDeleteBom
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnDeleteBom() { // 删除BOM表
		if (btnDeleteBom == null) {
			btnDeleteBom = new JButton();
			btnDeleteBom.setText("删除子件");
			btnDeleteBom.setPreferredSize(new Dimension(60, 30));
			btnDeleteBom.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelBom.getCurrentRows() == null) {
						JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
								"请选择你要删除的BOM资料", "确认", 2);
						return;

					}
					List ls = tableModelBom.getCurrentRows();
					for (int j = 0; j < ls.size(); j++) {
						emsBom = (EmsEdiMergerExgBom) ls.get(j);
						if (emsEdiMergerHead.getDeclareType().equals(
								DelcareType.APPLICATION)
								|| emsBom.getModifyMark().equals(
										ModifyMarkState.ADDED)) {
							manualdeclearAction.deleteEmsEdiMergerExgBom(
									new Request(CommonVars.getCurrUser()),
									emsBom);
							tableModelBom.deleteRow(emsBom);
						} else {
							emsBom.setModifyMark(ModifyMarkState.DELETED);
							if (isSendSign != null && "1".equals(isSendSign)) {
								emsBom.setSendState(1);// 准备发送
							}
							emsBom.setModifyTimes(emsEdiMergerHead
									.getModifyTimes()); // 变更次数
							emsBom = manualdeclearAction
									.saveEmsEdiMergerExgBom(new Request(
											CommonVars.getCurrUser()), emsBom);
							tableModelBom.updateRow(emsBom);
						}
						setState();
					}
				}
			});

		}
		return btnDeleteBom;
	}

	/**
	 * 
	 * This method initializes jbCopyBom
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnCopyBom() {
		if (btnCopyBom == null) {
			btnCopyBom = new JButton();
			btnCopyBom.setVisible(false);
			btnCopyBom.setText("复制BOM");
		}
		return btnCopyBom;
	}

	/**
	 * 
	 * This method initializes jbPasteBom
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnPasteBom() {
		if (btnPasteBom == null) {
			btnPasteBom = new JButton();
			btnPasteBom.setVisible(false);
			btnPasteBom.setText("粘贴BOM");
		}
		return btnPasteBom;
	}

	/**
	 * 
	 * This method initializes jPopupMenu
	 * 
	 * 
	 * 
	 * @return javax.swing.JPopupMenu
	 * 
	 */
	private JPopupMenu getJPopupMenu() {
		if (jPopupMenu == null) {
			jPopupMenu = new JPopupMenu();
			jPopupMenu.setVisible(true);
			jPopupMenu.add(getJMenuProBom());
			jPopupMenu.add(getJMenuMergerAfterImg());
		}
		return jPopupMenu;
	}

	/**
	 * 
	 * This method initializes jMenuProBom
	 * 
	 * 
	 * 
	 * @return javax.swing.JMenuItem
	 * 
	 */
	private void fromMaterielBom() {
		// if (emsExgAfter == null) {
		// JOptionPane.showMessageDialog(DgEmsEdiMerger.this, "请先选择归并后成品！");
		// return;
		// }
		emsExgBefore = (EmsEdiMergerExgBefore) tableModelBomExg.getCurrentRow(); // 获得归并关系
		if (emsExgBefore == null) {
			JOptionPane.showMessageDialog(DgEmsEdiMerger.this, "请先选择归并前成品！");
			return;
		}
		emsEdiMergerVersion = (EmsEdiMergerVersion) tableModelVersion
				.getCurrentRow();
		if (emsEdiMergerVersion == null) {
			JOptionPane.showMessageDialog(DgEmsEdiMerger.this, "请先选择成品版本！");
			return;
		}
		DgEmsEdiMergerFromOrgUnitWaste dg = new DgEmsEdiMergerFromOrgUnitWaste();
		dg.setEmsExgBefore(emsExgBefore);
		dg.setEdiMergerVersion(emsEdiMergerVersion);
		dg.setVisible(true);
		if (dg.isOk()) {
			List listr = dg.getReturnList();
			if (listr == null) {
				return;
			}
			new addMaterielFromBom(listr).start();
		}
	}

	class addMaterielFromBom extends Thread {
		List list = null;

		public addMaterielFromBom(List list) {
			this.list = list;
		}

		public void run() {
			try {
				CommonProgress.showProgressDialog(DgEmsEdiMerger.this);
				CommonProgress.setMessage("系统正在新增资料，请稍后...");
				manualdeclearAction.addBomFromSelectBom(
						new Request(CommonVars.getCurrUser()), emsExgBefore,
						emsEdiMergerVersion);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
						"新增失败！" + e.getMessage(), "提示", 2);
			} finally {
				freshBomAndVersion();
				setState();
			}
		}
	}

	private JMenuItem getJMenuProBom() {
		if (jMenuProBom == null) {
			jMenuProBom = new JMenuItem();
			jMenuProBom.setText("从报关常用工厂BOM拷贝");
			jMenuProBom.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

				}
			});

		}
		return jMenuProBom;
	}

	private void fromEmsEdiMergerBefore() {
		EmsEdiMergerExgBefore emsExgBefore = (EmsEdiMergerExgBefore) tableModelBomExg
				.getCurrentRow();
		if (emsExgBefore == null) {
			JOptionPane.showMessageDialog(DgEmsEdiMerger.this, "请先选择归并前成品！");
			return;
		}
		if (emsExgBefore.getModifyMark().equals(ModifyMarkState.DELETED)) {
			JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
					"归并后成品已删除，不能新增！", "确认", 2);
			return;
		}
		if (!EmsEdiMergerClientLogic.fillBomFromMergerBeforeImg(
				emsEdiMergerHead, tableModelBom, emsExgBefore, jTableBom,
				emsEdiMergerVersion)) {
			return;
		}
		setState();
	}

	/**
	 * 
	 * This method initializes jMenuMergerAfterImg
	 * 
	 * 
	 * 
	 * @return javax.swing.JMenuItem
	 * 
	 */
	private JMenuItem getJMenuMergerAfterImg() {
		if (jMenuMergerAfterImg == null) {
			jMenuMergerAfterImg = new JMenuItem();
			jMenuMergerAfterImg.setText("从归并前料件");
			jMenuMergerAfterImg
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {

						}
					});
		}
		return jMenuMergerAfterImg;
	}

	private void setState() {
		boolean isSend = EmsEdiMergerClientLogic.getIsSend();
		boolean enable = dataState != DataState.READONLY;
		boolean editable = dataState != DataState.BROWSE;
		jTabbedPane.setEnabled((emsEdiMergerHead.getDeclareState().equals(
				DeclareState.APPLY_POR) && dataState == DataState.BROWSE)
				|| !emsEdiMergerHead.getDeclareState().equals(
						DeclareState.APPLY_POR));
		jbAdd.setEnabled(!(jTabbedPane.getSelectedIndex() == 0)
				&& (!(jTabbedPane.getSelectedIndex() == 3)) && (enable)); // 新增
		jButton.setEnabled(!(jTabbedPane.getSelectedIndex() == 0)
				&& (!(jTabbedPane.getSelectedIndex() == 3)) && (enable)); // 增加半成品
		btnReverseCheck
				.setVisible((jTabbedPane.getSelectedIndex() == 3)
						&& (emsEdiMergerHead.getDeclareType().equals("2") && emsEdiMergerHead
								.getDeclareState().equals("1"))); // 增加半成品
		jbEdit.setEnabled((dataState == DataState.BROWSE && (jTabbedPane
				.getSelectedIndex() == 0))
				|| ((enable) && isImgExgPageAndExistData()));// 修改

		jButton3.setEnabled(((enable) && isImgExgPageAndExistData()));// 修改申报状态
		jButton12.setEnabled(((enable) && isImgExgPageAndExistData()));// 更新来自内部归并
		jButton3.setVisible(isSend);

		jButton9.setEnabled(jTabbedPane.getSelectedIndex() == 3);// BOM导出

		jbDelete.setEnabled((enable) && isImgExgPageAndExistData()); // 删除
		jbSave.setEnabled((jTabbedPane.getSelectedIndex() == 0) && editable
				&& enable); // 保存

		tfTel.setEditable(editable && !DgEmsEdiMerger.this.isChange && enable);
		tfEmsNo.setEditable(editable && enable);
		tfCopEmsNo.setEditable(editable && enable);
		tfSancEmsNo.setEditable(editable && enable);
		tfAddress.setEditable(editable && !DgEmsEdiMerger.this.isChange
				&& enable);
		cmbBeginDate.setEnabled(editable && enable
				&& !DgEmsEdiMerger.this.isChange);
		cmbEndDate.setEnabled(editable && enable);
		tfMaxTurnMoney.setEditable(editable && enable);
		tfInvestAmount.setEditable(editable && enable);
		tfMachinAbility.setEditable(editable && enable);
		tfNote.setEditable(editable && enable);
		tfMachName.setEditable(editable && enable);
		tfTradeName.setEditable(editable && enable);
		jbAddImgBefore.setEnabled(enable
				&& (tableModelImgAfter.getRowCount() > 0));
		jbEditImgBefore.setEnabled(enable
				&& (tableModelImgBefore.getRowCount() > 0));
		jButton4.setEnabled(enable && (tableModelImgBefore.getRowCount() > 0));// 改变料件申报状态
		jButton11.setEnabled(enable && (tableModelImgBefore.getRowCount() > 0));// 改变修改标记
		jButton4.setVisible(isSend);
		jbDeleteImgBefore.setEnabled(enable
				&& tableModelImgBefore.getRowCount() > 0);
		jbAddExgBefore.setEnabled(enable
				&& tableModelExgAfter.getRowCount() > 0);
		jbEditExgBefore.setEnabled(enable
				&& tableModelExgBefore.getRowCount() > 0);
		jButton5.setEnabled(enable && tableModelExgBefore.getRowCount() > 0);// 改变成品申报状态
		jButton10.setEnabled(enable && tableModelExgBefore.getRowCount() > 0);// 改变修改标记
		jButton5.setVisible(isSend);
		jbDeleteExgBefore.setEnabled(enable
				&& tableModelExgBefore.getRowCount() > 0);
		btnCope.setEnabled(tableModelVersion.getRowCount() > 0);
		btnAddBom.setEnabled(enable && tableModelVersion.getRowCount() > 0); // 新增BOM
		btnEditBom.setEnabled(enable && tableModelBom.getRowCount() > 0); // 修改Bom资料
		jButton6.setEnabled(enable && tableModelBom.getRowCount() > 0); // 修改Bom申报状态资料
		jButton8.setEnabled(enable && tableModelBom.getRowCount() > 0); // 修改Bom申报状态资料
		jButton6.setVisible(isSend);
		btnDeleteBom.setEnabled(enable && tableModelBom.getRowCount() > 0); // 删除BOM资料

		jButton1.setEnabled(enable && tableModelExgBefore.getRowCount() > 0);// 新增版本
		jButton2.setEnabled(enable && tableModelVersion.getRowCount() > 0); // 删除版本
		jBimport.setEnabled(enable && tableModelExgBefore.getRowCount() > 0);// BOM导入

		jButton7.setVisible((jTabbedPane.getSelectedIndex() == 1)
				|| (jTabbedPane.getSelectedIndex() == 2));

		jLabel28.setText("   当前归并前有："
				+ (tableModelImgBefore == null ? 0 : tableModelImgBefore
						.getRowCount()) + " 笔记录");
		jLabel29.setText("   当前归并前有："
				+ (tableModelExgBefore == null ? 0 : tableModelExgBefore
						.getRowCount()) + " 笔记录");
		btnMaxVersion.setEnabled(jTabbedPane.getSelectedIndex() == 3);
	}

	private boolean isImgExgPageAndExistData() {
		if ((jTabbedPane.getSelectedIndex() == 1)
				&& (tableModelImgAfter.getRowCount() > 0)) {
			return true;
		}
		if ((jTabbedPane.getSelectedIndex() == 2)
				&& (tableModelExgAfter.getRowCount() > 0)) {
			return true;
		}
		return false;
	}

	private void fillEmsEdiMergerHead(EmsEdiMergerHead emsediMerger) { // 表头保存
		EmsEdiMergerHead emsEdiMergerHeadOld = new EmsEdiMergerHead();
		emsEdiMergerHeadOld = (EmsEdiMergerHead) emsediMerger.clone();
		emsediMerger.setTel(tfTel.getText());
		emsediMerger.setEmsNo(tfEmsNo.getText());
		emsediMerger.setAddress(tfAddress.getText());
		emsediMerger.setEndDate(cmbEndDate.getDate());
		emsediMerger.setTradeName(tfTradeName.getText().trim());
		emsediMerger.setMachName(tfMachName.getText().trim());
		emsediMerger.setInvestAmount(EmsEdiMergerClientLogic
				.strToDouble(tfInvestAmount.getText()));
		emsediMerger.setMachAbility(EmsEdiMergerClientLogic
				.strToDouble(tfMachinAbility.getText()));
		emsediMerger.setMaxTurnMoney(EmsEdiMergerClientLogic
				.strToDouble(tfMaxTurnMoney.getText()));
		emsediMerger.setNote(tfNote.getText());
		emsediMerger.setSancEmsNo(tfSancEmsNo.getText());
		emsediMerger.setCopEmsNo(tfCopEmsNo.getText());
		if (!emsediMerger.fullEquals(emsEdiMergerHeadOld)
				&& emsediMerger.getDeclareType().equals(DelcareType.MODIFY)) {
			emsediMerger.setModifyMark(ModifyMarkState.MODIFIED); // 已修改
		}
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
			jButton.setText("增加(半成品)");
			jButton.setPreferredSize(new Dimension(80, 30));
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (jTabbedPane.getSelectedIndex() == 1)// 料件
					{
						EmsEdiMergerImgAfter emsMergerImg = null;
						List list = (List) CommonQuery
								.getInstance()
								.getMergerImg10(false, emsEdiTrHead,
										emsEdiMergerHead, MaterielType.MATERIEL); // common
						// dialog
						if (list == null || list.isEmpty())
							return;
						for (int i = 0; i < list.size(); i++) {
							emsMergerImg = (EmsEdiMergerImgAfter) list.get(i);
							emsMergerImg.setEmsEdiMergerHead(emsEdiMergerHead);
							emsMergerImg.setModifyMark(ModifyMarkState.ADDED);
							emsMergerImg.setChangeDate(new Date());
							emsMergerImg.setCompany(CommonVars.getCurrUser()
									.getCompany());
							emsMergerImg = manualdeclearAction
									.saveEmsEdiMergerImgAfter(new Request(
											CommonVars.getCurrUser()),
											emsMergerImg); // 保存归并后
							tableModelImgAfter.addRow(emsMergerImg);
							EmsEdiMergerClientLogic.fillMergerImgBeforeData(
									emsMergerImg, emsEdiMergerHead, list,
									MaterielType.SEMI_FINISHED_PRODUCT,
									jTableImgBefore);// 新增归并前
							EmsEdiMergerClientLogic.fillImgBefore(
									tableModelImgBefore, emsMergerImg,
									jTableImgBefore);
						}
					} else if (jTabbedPane.getSelectedIndex() == 2) // 成品
					{
						EmsEdiMergerExgAfter emsMergerExgAfter = null;
						List list = (List) CommonQuery.getInstance()
								.getMergerExg10(false, emsEdiTrHead,
										emsEdiMergerHead,
										MaterielType.FINISHED_PRODUCT); // common
						// dialog
						if (list == null || list.isEmpty())
							return;
						for (int i = 0; i < list.size(); i++) {
							emsMergerExgAfter = (EmsEdiMergerExgAfter) list
									.get(i);
							emsMergerExgAfter
									.setEmsEdiMergerHead(emsEdiMergerHead);
							emsMergerExgAfter
									.setModifyMark(ModifyMarkState.ADDED);
							emsMergerExgAfter.setChangeDate(new Date());
							emsMergerExgAfter.setCompany(CommonVars
									.getCurrUser().getCompany());
							emsMergerExgAfter = manualdeclearAction
									.saveEmsEdiMergerExgAfter(new Request(
											CommonVars.getCurrUser()),
											emsMergerExgAfter); // 保存归并后
							tableModelExgAfter.addRow(emsMergerExgAfter);
							EmsEdiMergerClientLogic.fillMergerExgBeforeData(
									emsMergerExgAfter, emsEdiMergerHead, list,
									MaterielType.SEMI_FINISHED_PRODUCT,
									jTableExgBefore);// 新增归并前
							EmsEdiMergerClientLogic.fillExgBefore(
									tableModelExgBefore, tableModelBomExg,
									emsMergerExgAfter, jTableExgBefore,
									jTableBomExg);
						}
					}
					setState();
				}
			});

		}
		return jButton;
	}

	/**
	 * 
	 * This method initializes jRadioButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			// jRadioButton.setVisible(false);
			jRadioButton.setText("从报关常用工厂BOM拷贝");
		}
		return jRadioButton;
	}

	/**
	 * 
	 * This method initializes jRadioButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setText("从归并前料件");
		}
		return jRadioButton1;
	}

	/**
	 * @return Returns the isHistoryChange.
	 */
	public boolean isHistoryChange() {
		return isHistoryChange;
	}

	/**
	 * @param isHistoryChange
	 *            The isHistoryChange to set.
	 */
	public void setHistoryChange(boolean isHistoryChange) {
		this.isHistoryChange = isHistoryChange;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("新增版本");
			jButton1.setPreferredSize(new Dimension(60, 30));
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) { // 新增版本号
					addVerision();

				}
			});
		}
		return jButton1;
	}

	private void addVerision() {
		if (emsExgBefore == null) {
			JOptionPane.showMessageDialog(DgEmsEdiMerger.this, "请选中成品！", "提示！",
					2);
			return;
		}
		if (emsExgBefore.getModifyMark().equals(ModifyMarkState.DELETED)) {
			JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
					"成品已删除，不能新增版本号！", "提示！", 2);
			return;
		}
		DgMergerVersion dgVersion = new DgMergerVersion();
		dgVersion.setEmsExgBefore(emsExgBefore);
		dgVersion.setVisible(true);
		if (dgVersion.isOk()) {
			EmsEdiMergerVersion emsH2kVersion = new EmsEdiMergerVersion();
			Integer Version = dgVersion.getDValue();
			emsH2kVersion.setName("版本：" + String.valueOf(Version));
			emsH2kVersion.setVersion(Version);
			emsH2kVersion.setEmsEdiMergerBefore(emsExgBefore);
			emsH2kVersion.setCompany(CommonVars.getCurrUser().getCompany());
			emsH2kVersion = manualdeclearAction.saveEmsEdiMergerVersion(
					new Request(CommonVars.getCurrUser()), emsH2kVersion);
			tableModelVersion.addRow(emsH2kVersion);
			setState();
		}
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("删除版本");
			jButton2.setPreferredSize(new Dimension(60, 30));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelVersion.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
								"请选择要删除的版本！", "提示！", 2);
						return;
					}

					EmsEdiMergerVersion version = (EmsEdiMergerVersion) tableModelVersion
							.getCurrentRow();
					if (version.getEmsEdiMergerBefore().getModifyMark()
							.equals(ModifyMarkState.DELETED)) {
						return;
					}
					if (JOptionPane.showConfirmDialog(DgEmsEdiMerger.this,
							"您确认要删除选定版本吗？\n注意：其BOM料件将一并删除?", "删除提示", 0) == 0) {
						EmsEdiMergerVersion currRow = (EmsEdiMergerVersion) tableModelVersion
								.getCurrentRow();
						List listBom = manualdeclearAction.findEmsEdiMergerBom(
								new Request(CommonVars.getCurrUser()), currRow);
						if (!isAddBom(listBom)) {
							JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
									"该版本不可以被删除！", "提示！", 2);
							return;
						}
						if (emsEdiMergerHead.getDeclareType().equals(
								DelcareType.APPLICATION)
								|| isAddBom(listBom)) {
							manualdeclearAction.deleteEmsMergerBomByVersion(
									new Request(CommonVars.getCurrUser()),
									currRow);
							tableModelBom.getList().clear();
							tableModelBom.setList(new Vector());

							manualdeclearAction.deleteEmsEdiMergerVersion(
									new Request(CommonVars.getCurrUser()),
									currRow);
							tableModelVersion.deleteRow(currRow);
						} else {
							if (listBom != null && !listBom.isEmpty()) {
								for (int i = 0; i < listBom.size(); i++) {
									EmsEdiMergerExgBom emsBom = (EmsEdiMergerExgBom) listBom
											.get(i);
									emsBom.setModifyMark(ModifyMarkState.DELETED);
									if (isSendSign != null
											&& "1".equals(isSendSign)) {
										emsBom.setSendState(1); // 准备发送
									}
									emsBom.setModifyTimes(emsEdiMergerHead
											.getModifyTimes()); // 变更次数
									emsBom = manualdeclearAction
											.saveEmsEdiMergerExgBom(
													new Request(CommonVars
															.getCurrUser()),
													emsBom);
									tableModelBom.updateRow(emsBom);
								}
							}
						}
					}
					setState();
				}
			});
		}
		return jButton2;
	}

	private boolean isAddBom(List listBom) {
		if (listBom != null && !listBom.isEmpty()) {
			for (int i = 0; i < listBom.size(); i++) {
				EmsEdiMergerExgBom emsBom = (EmsEdiMergerExgBom) listBom.get(i);
				if (!emsBom.getModifyMark().equals(ModifyMarkState.ADDED)) {
					return false;
				}
			}
			return true;
		} else {
			return true;
		}

	}

	/**
	 * This method initializes jSplitPane3
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane3() {
		if (jSplitPane3 == null) {
			jSplitPane3 = new JSplitPane();
			jSplitPane3.setDividerLocation(100);
			jSplitPane3.setDividerSize(5);
			jSplitPane3.setLeftComponent(getJPanel6());
			jSplitPane3.setRightComponent(getJTabbedPane7());
		}
		return jSplitPane3;
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
			jPanel6.add(getJScrollPane5(), java.awt.BorderLayout.CENTER);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JTabbedPane getJTabbedPane7() {
		if (jTabbedPane7 == null) {
			jTabbedPane7 = new JTabbedPane();
			jTabbedPane7.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane7.addTab("归并前BOM", null, getJPanel8(), null);
			jTabbedPane7.addTab("货号对应的备案号", null, getJPanel9(), null);
			jTabbedPane7
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {

							if (jTabbedPane7.getSelectedIndex() == 1) {

								getJTableimgDate();

							} else if (jTabbedPane7.getSelectedIndex() == 0) {
								if (tableModelVersion.getCurrentRow() == null) {
									dataSourceBom = null;
								}
							}
						}
					});

		}
		return jTabbedPane7;
	}

	public void getJTableimgDate() {

		List list = null;
		// String[] sSeqNum = new String[dataSourceBom ==
		// null ? 0
		// : dataSourceBom.size()];

		if (dataSourceBom != null && dataSourceBom.size() > 0) {
			// for (int i = 0; i < dataSourceBom.size();
			// i++) {
			// EmsEdiMergerExgBom bom =
			// (EmsEdiMergerExgBom) dataSourceBom
			// .get(i);
			// sSeqNum[i] = bom.getPtNo();
			// }
			list = manualdeclearAction.findEdiBomptNoToBomseqNum(new Request(
					CommonVars.getCurrUser()), null, emsEdiMergerVersion
					.getId());
			if (list != null && !list.isEmpty()) {
				EmsEdiMergerClientLogic.initTableBomImg(jTableimg, list);
			} else {
				EmsEdiMergerClientLogic
						.initTableBomImg(jTableimg, new Vector());
			}
		} else {
			EmsEdiMergerClientLogic.initTableBomImg(jTableimg, new Vector());
		}

	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTableVersion() {
		if (jTableVersion == null) {
			jTableVersion = new JTable();
			jTableVersion.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					valuechange();
				}
			});
			// jTableVersion.getSelectionModel().addListSelectionListener(
			// new ListSelectionListener() {
			// public void valueChanged(ListSelectionEvent e) {
			// if (e.getValueIsAdjusting()) {
			// return;
			// }
			// JTableListModel tableModel = (JTableListModel) jTableVersion
			// .getModel();
			// if (tableModel == null) {
			// return;
			// }
			// valuechange();
			// }
			// });

		}
		return jTableVersion;
	}

	/**
	 * 查询Bom
	 */
	private void valuechange() {
		if (tableModelVersion == null)
			return;
		if (tableModelVersion.getCurrentRow() == null)
			return;

		emsEdiMergerVersion = (EmsEdiMergerVersion) tableModelVersion
				.getCurrentRow();
		// Integer version = emsEdiMergerVersion.getVersion();

		dataSourceBom = manualdeclearAction.findEmsEdiMergerBom(new Request(
				CommonVars.getCurrUser()), emsEdiMergerVersion);
		if (dataSourceBom != null && !dataSourceBom.isEmpty()) {
			tableModelBom = EmsEdiMergerClientLogic.initTableBom(jTableBom,
					dataSourceBom);
			EmsEdiMergerClientLogic.transModifyMark(jTableBom);
			EmsEdiMergerClientLogic.transSendState(jTableBom);
		} else {
			tableModelBom = EmsEdiMergerClientLogic.initTableBom(jTableBom,
					new Vector());
			EmsEdiMergerClientLogic.initTableBomImg(jTableimg, new Vector());
		}
		getJTableimgDate();

		// if (dataSourceBom != null && !dataSourceBom.isEmpty()) {
		// tableModelBom = EmsEdiMergerClientLogic.initTableBom(jTableBom,
		// dataSourceBom);
		// EmsEdiMergerClientLogic.transModifyMark(jTableBom);
		// EmsEdiMergerClientLogic.transSendState(jTableBom);
		// System.out.println("===========================================");
		// } else {
		// tableModelBom = EmsEdiMergerClientLogic.initTableBom(jTableBom,
		// new Vector());
		// System.out.println("===========================================");
		// }

		setState();
	}

	/**
	 * This method initializes jScrollPane5
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane5() {
		if (jScrollPane5 == null) {
			jScrollPane5 = new JScrollPane();
			jScrollPane5.setViewportView(getJTableVersion());
		}
		return jScrollPane5;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTableBom() {
		if (jTableBom == null) {
			jTableBom = new JTable();
			jTableBom.setRowHeight(25);
		}
		return jTableBom;
	}

	private void initTableVersion(List list) {
		tableModelVersion = new JTableListModel(jTableVersion, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("版本号", "name", 60));
						list.add(addColumn("单重", "unitWeight", 60));
						list.add(addColumn("企业版本", "cmpVersion", 60));
						return list;
					}
				});
	}

	/**
	 * This method initializes jBimport bom导入功能
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJBimport() {
		if (jBimport == null) {
			jBimport = new JButton();
			jBimport.setText("BOM导入");
			jBimport.setPreferredSize(new Dimension(62, 30));
			jBimport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgEmsEdiMergerImport dg = new DgEmsEdiMergerImport();
					dg.setEmsEdiMergerHead(emsEdiMergerHead);
					dg.setExgAfter(emsExgAfter);
					dg.setEmsEdiMergerExgBefore(emsExgBefore);
					dg.setVisible(true);
					if (emsExgBefore == null) {
						return;
					}
					freshBomAndVersion();

				}
			});
		}
		return jBimport;
	}

	private void freshBomAndVersion() {
		if (tableModelBomExg.getCurrentRow() == null) {
			return;
		}
		emsExgBefore = (EmsEdiMergerExgBefore) tableModelBomExg.getCurrentRow(); // 获得归并关系
		List dataSourceVersion = null;
		dataSourceVersion = manualdeclearAction.findEmsEdiMergerVersion( // 得到版本号
				new Request(CommonVars.getCurrUser()), emsExgBefore);
		if (dataSourceVersion != null && !dataSourceVersion.isEmpty()) {
			initTableVersion(dataSourceVersion);
			emsEdiMergerVersion = (EmsEdiMergerVersion) dataSourceVersion
					.get(0); // 第一个版本号
			// Integer version = emsEdiMergerVersion.getVersion();

			dataSourceBom = manualdeclearAction.findEmsEdiMergerBom(
					new Request(CommonVars.getCurrUser()), // 得到BOM
					emsEdiMergerVersion);

			if (dataSourceBom != null && !dataSourceBom.isEmpty()) {
				tableModelBom = EmsEdiMergerClientLogic.initTableBom(jTableBom,
						dataSourceBom);
				EmsEdiMergerClientLogic.transModifyMark(jTableBom);
				EmsEdiMergerClientLogic.transSendState(jTableBom);
			} else {
				tableModelBom = EmsEdiMergerClientLogic.initTableBom(jTableBom,
						new Vector());
			}
		} else {
			initTableVersion(new Vector());
			tableModelBom = EmsEdiMergerClientLogic.initTableBom(jTableBom,
					new Vector());
		}
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("改变申报状态");
			jButton3.setPreferredSize(new Dimension(84, 30));
			jButton3.setForeground(java.awt.Color.blue);
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (jTabbedPane.getSelectedIndex() == 1) {// 料件
						if (tableModelImgAfter == null
								|| tableModelImgAfter.getCurrentRows() == null
								|| tableModelImgAfter.getCurrentRows().size() == 0) {
							JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
									"没有选中数据！", "提示！",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						MyJPopupMenu mym = new MyJPopupMenu() {

							List list = tableModelImgAfter.getCurrentRows();

							void changeState(String state) {
								for (int i = 0; i < list.size(); i++) {
									EmsEdiMergerImgAfter obj = (EmsEdiMergerImgAfter) list
											.get(i);
									// 修改申报状态权限
									obj = manualdeclearAction.alterState(
											new Request(CommonVars
													.getCurrUser()), obj, state);
									tableModelImgAfter.updateRow(obj);
								}
							}
						};
						mym.show(jButton3, 0, jButton3.getHeight());
					}

					else if (jTabbedPane.getSelectedIndex() == 2) {
						if (tableModelExgAfter == null
								|| tableModelExgAfter.getCurrentRows() == null
								|| tableModelExgAfter.getCurrentRows().size() == 0) {
							JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
									"没有选中数据！", "提示！",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						MyJPopupMenu mym = new MyJPopupMenu() {
							List list = tableModelExgAfter.getCurrentRows();

							void changeState(String state) {
								for (int i = 0; i < list.size(); i++) {
									EmsEdiMergerExgAfter obj = (EmsEdiMergerExgAfter) list
											.get(i);
									// 修改申报状态权限
									obj = manualdeclearAction.alterState(
											new Request(CommonVars
													.getCurrUser()), obj, state);
									tableModelExgAfter.updateRow(obj);
								}
							}
						};
						mym.show(jButton3, 0, jButton3.getHeight());

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
			jButton4.setText("改变申报状态");
			jButton4.setPreferredSize(new Dimension(84, 30));
			jButton4.setForeground(java.awt.Color.blue);
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelImgBefore == null
							|| tableModelImgBefore.getCurrentRows() == null
							|| tableModelImgBefore.getCurrentRows().size() == 0) {
						JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
								"没有选中数据！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					MyJPopupMenu mym = new MyJPopupMenu() {

						void changeState(String state) {
							List list = tableModelImgBefore.getCurrentRows();
							for (int i = 0; i < list.size(); i++) {
								EmsEdiMergerImgBefore obj = (EmsEdiMergerImgBefore) list
										.get(i);
								// 修改申报状态权限
								obj = manualdeclearAction.alterState(
										new Request(CommonVars.getCurrUser()),
										obj, state);
								tableModelImgBefore.updateRow(obj);
							}
						}
					};
					mym.show(jButton4, 0, jButton4.getHeight());
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
			jButton5.setText("改变申报状态");
			jButton5.setPreferredSize(new Dimension(84, 30));
			jButton5.setForeground(java.awt.Color.blue);
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExgBefore == null
							|| tableModelExgBefore.getCurrentRows() == null
							|| tableModelExgBefore.getCurrentRows().size() == 0) {
						JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
								"没有选中数据！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					MyJPopupMenu mym = new MyJPopupMenu() {
						List list = tableModelExgBefore.getCurrentRows();

						void changeState(String state) {
							for (int i = 0; i < list.size(); i++) {
								EmsEdiMergerExgBefore obj = (EmsEdiMergerExgBefore) list
										.get(i);
								// 修改申报状态权限
								obj = manualdeclearAction.alterState(
										new Request(CommonVars.getCurrUser()),
										obj, state);
								tableModelExgBefore.updateRow(obj);
							}
						}
					};
					mym.show(jButton5, 0, jButton5.getHeight());
				}
			});

			//
			//
			// jButton5.addActionListener(new java.awt.event.ActionListener() {
			// public void actionPerformed(java.awt.event.ActionEvent e) {
			// List list = tableModelExgBefore.getCurrentRows();
			// for (int i = 0; i < list.size(); i++) {
			// EmsEdiMergerExgBefore obj = (EmsEdiMergerExgBefore) list
			// .get(i);
			// if (obj.getSendState() == null
			// || obj.getSendState().equals(
			// Integer.valueOf(SendState.NOT_SEND))) {
			// obj.setSendState(Integer
			// .valueOf(SendState.WAIT_SEND));
			// } else if (obj.getSendState().equals(
			// Integer.valueOf(SendState.WAIT_SEND))) {
			// obj.setSendState(Integer.valueOf(SendState.SEND));
			// } else if (obj.getSendState().equals(
			// Integer.valueOf(SendState.SEND))) {
			// obj.setSendState(Integer
			// .valueOf(SendState.NOT_SEND));
			// }
			// obj = manualdeclearAction.saveEmsEdiMergerExgBefore(
			// new Request(CommonVars.getCurrUser()), obj);
			// tableModelExgBefore.updateRow(obj);
			// }
			// }
			// });
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
			jButton6.setText("改变申报状态");
			jButton6.setPreferredSize(new Dimension(84, 30));
			jButton6.setForeground(java.awt.Color.blue);
			jButton6.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelBom == null
							|| tableModelBom.getCurrentRows() == null
							|| tableModelBom.getCurrentRows().size() == 0) {
						JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
								"没有选中数据！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					MyJPopupMenu mym = new MyJPopupMenu() {
						List<EmsEdiMergerExgBom> list = tableModelBom
								.getCurrentRows();

						void changeState(String state) {
							// for (int i = 0; i < list.size(); i++) {
							// EmsEdiMergerExgBom obj = (EmsEdiMergerExgBom)
							// list
							// .get(i);
							// // 修改申报状态权限
							// obj = manualdeclearAction.alterState(
							// new Request(CommonVars.getCurrUser()),
							// obj, state);
							// tableModelBom.updateRow(obj);
							// }
							// /////////////柯鹏程
							for (int i = 0; i < list.size(); i++) {
								EmsEdiMergerExgBom obj = (EmsEdiMergerExgBom) list
										.get(i);
								obj.setSendState(Integer.valueOf(state));
							}
							manualdeclearAction.alterStates(new Request(
									CommonVars.getCurrUser()), list);
							tableModelBom.updateRow(list);
							// /////////////////
						}
					};
					mym.show(jButton6, 0, jButton6.getHeight());
				}
			});
		}
		return jButton6;
	}

	/**
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setText("查询");
			jButton7.setPreferredSize(new Dimension(60, 30));
			jButton7.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgByPtQuery dg = new DgByPtQuery();
					dg.setVisible(true);
					if (dg.isOk()) {
						String ptNo = dg.getDValue();
						if (jTabbedPane.getSelectedIndex() == 1) { // 料件
							List list = manualdeclearAction
									.findEmsEdiMergerImgBeforeByGNo(
											new Request(CommonVars
													.getCurrUser()), true,
											ptNo, emsEdiMergerHead);
							if (list != null && list.size() > 0) {
								EmsEdiMergerImgBefore before = (EmsEdiMergerImgBefore) list
										.get(0);
								emsImgAfter = before.getEmsEdiMergerImgAfter();
								tableModelImgAfter
										.setTableSelectedRow(emsImgAfter);
								tableModelImgBefore.setTableSelectedRow(before);
							} else {
								JOptionPane.showMessageDialog(
										DgEmsEdiMerger.this, "对不起，没有查询到你要的资料！",
										"提示", 2);
							}
						} else {// 成品
							List list = manualdeclearAction
									.findEmsEdiMergerImgBeforeByGNo(
											new Request(CommonVars
													.getCurrUser()), false,
											ptNo, emsEdiMergerHead);
							if (list != null && list.size() > 0) {
								EmsEdiMergerExgBefore before = (EmsEdiMergerExgBefore) list
										.get(0);
								emsExgAfter = before.getEmsEdiMergerExgAfter();
								tableModelExgAfter
										.setTableSelectedRow(emsExgAfter);
								tableModelExgBefore.setTableSelectedRow(before);
							} else {
								JOptionPane.showMessageDialog(
										DgEmsEdiMerger.this, "对不起，没有查询到你要的资料！",
										"提示", 2);
							}
						}
					}
				}
			});
		}
		return jButton7;
	}

	/**
	 * This method initializes jButton8
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton8() {
		if (jButton8 == null) {
			jButton8 = new JButton();
			jButton8.setText("其他");
			jButton8.setPreferredSize(new Dimension(60, 30));
			jButton8.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelBom == null
							|| tableModelBom.getCurrentRows() == null
							|| tableModelBom.getCurrentRows().size() == 0) {
						JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
								"没有选中数据！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					MyModifJPopupMenu mym = new MyModifJPopupMenu("全选(到准备申报)") {
						List list = tableModelBom.getCurrentRows();

						void doOther() {
							emsEdiMergerVersion = (EmsEdiMergerVersion) tableModelVersion
									.getCurrentRow();
							Integer version = emsEdiMergerVersion.getVersion();
							dataSourceBom = manualdeclearAction
									.findEmsEdiMergerBom(
											new Request(CommonVars
													.getCurrUser()), // 得到BOM
											emsEdiMergerVersion);
							List ls = manualdeclearAction
									.emsMergerSelectAllSendState(new Request(
											CommonVars.getCurrUser()),
											dataSourceBom);
							tableModelBom.updateRows(ls);
						}

						void changeState(String state) {
							if (JOptionPane.showConfirmDialog(
									DgEmsEdiMerger.this, "您确认要修改标记吗?", "提示",
									JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
								return;
							}
							for (int i = 0; i < list.size(); i++) {
								EmsEdiMergerExgBom obj = (EmsEdiMergerExgBom) list
										.get(i);
								// 修改标志权限
								obj = manualdeclearAction.changeState(
										new Request(CommonVars.getCurrUser()),
										obj, state);
								tableModelBom.updateRow(obj);
							}
						}
					};
					mym.show(jButton8, 0, jButton8.getHeight());
				}
			});

			jButton8.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getJPopupMenu4().show(jButton8, 0, jButton8.getHeight());
					getJPopupMenu4().show(jButton8, 0, jButton8.getHeight());

				}
			});
		}
		return jButton8;
	}

	/**
	 * This method initializes jButton10
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton10() {
		if (jButton10 == null) {
			jButton10 = new JButton();
			jButton10.setText("其他");
			jButton10.setPreferredSize(new Dimension(65, 30));
			jButton10.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExgBefore == null
							|| tableModelExgBefore.getCurrentRows() == null
							|| tableModelExgBefore.getCurrentRows().size() == 0) {
						JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
								"没有选中数据！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					MyModifJPopupMenu mym = new MyModifJPopupMenu("重新归并") {
						List list = tableModelExgBefore.getCurrentRows();

						void doOther() {
							DgEmsBySeqNum dg = new DgEmsBySeqNum();
							dg.setVisible(true);
							if (dg.getDValue() == null
									|| dg.getDValue().equals("")) {
								return;
							}
							if (!dg.isOk()) {
								return;
							}
							Integer seqNum = null;
							try {
								seqNum = Integer.valueOf(dg.getDValue());
							} catch (Exception e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(
										DgEmsEdiMerger.this, "请输入正确的整数！",
										"提示！", JOptionPane.WARNING_MESSAGE);
							}
							List ls = tableModelExgBefore.getCurrentRows();
							manualdeclearAction.changeMergerSeqNumExg(
									new Request(CommonVars.getCurrUser()), ls,
									emsEdiMergerHead, seqNum);
							tableModelExgBefore.deleteRows(ls);

						}

						void changeState(String state) {
							if (JOptionPane.showConfirmDialog(
									DgEmsEdiMerger.this, "您确认要修改标记吗?", "提示",
									JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
								return;
							}
							for (int i = 0; i < list.size(); i++) {
								EmsEdiMergerExgBefore obj = (EmsEdiMergerExgBefore) list
										.get(i);
								// 修改标志权限
								obj = manualdeclearAction.changeState(
										new Request(CommonVars.getCurrUser()),
										obj, state);
								tableModelExgBefore.updateRow(obj);
							}
						}
					};
					mym.show(jButton10, 0, jButton10.getHeight());

					// getJPopupMenu3().show(jButton10, 0,
					// jButton10.getHeight());
					// getJPopupMenu3().show(jButton10, 0,
					// jButton10.getHeight());

				}
			});
		}
		return jButton10;
	}

	/**
	 * This method initializes jButton11
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton11() {
		if (jButton11 == null) {
			jButton11 = new JButton();
			jButton11.setText("其他");
			jButton11.setPreferredSize(new Dimension(65, 30));
			jButton11.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelImgBefore == null
							|| tableModelImgBefore.getCurrentRows() == null
							|| tableModelImgBefore.getCurrentRows().size() == 0) {
						JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
								"没有选中数据！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					MyModifJPopupMenu mym = new MyModifJPopupMenu("重新归并") {

						void doOther() {
							DgEmsBySeqNum dg = new DgEmsBySeqNum();
							dg.setVisible(true);
							if (!dg.isOk()) {
								return;
							}
							Integer seqNum = null;
							try {
								seqNum = Integer.valueOf(dg.getDValue());
							} catch (Exception e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(
										DgEmsEdiMerger.this, "请输入正确的整数！",
										"提示！", JOptionPane.WARNING_MESSAGE);
							}
							List ls = tableModelImgBefore.getCurrentRows();
							manualdeclearAction.changeMergerSeqNumImg(
									new Request(CommonVars.getCurrUser()), ls,
									emsEdiMergerHead, seqNum);
							tableModelImgBefore.deleteRows(ls);
						}

						void changeState(String state) {
							List list = tableModelImgBefore.getCurrentRows();
							if (JOptionPane.showConfirmDialog(
									DgEmsEdiMerger.this, "您确认要修改标记吗?", "提示",
									JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
								return;
							}
							for (int i = 0; i < list.size(); i++) {
								EmsEdiMergerImgBefore obj = (EmsEdiMergerImgBefore) list
										.get(i);
								// 修改标志权限
								obj = manualdeclearAction.changeState(
										new Request(CommonVars.getCurrUser()),
										obj, state);
								tableModelImgBefore.updateRow(obj);
							}
						}
					};
					mym.show(jButton11, 0, jButton11.getHeight());

					// getJPopupMenu2().show(jButton11, 0,
					// jButton11.getHeight());
					// getJPopupMenu2().show(jButton11, 0,
					// jButton11.getHeight());

				}
			});
		}
		return jButton11;
	}

	/**
	 * This method initializes jButton12
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton12() {
		if (jButton12 == null) {
			jButton12 = new JButton();
			jButton12.setText("其他");
			jButton12.setPreferredSize(new Dimension(60, 30));
			jButton12.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 1) {// 料件
						if (tableModelImgAfter == null
								|| tableModelImgAfter.getCurrentRows() == null
								|| tableModelImgAfter.getCurrentRows().size() == 0) {
							JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
									"没有选中数据！", "提示！",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						MyModifJPopupMenu mym = new MyModifJPopupMenu(
								"更新(与内部归并同步)") {
							List list = tableModelImgAfter.getCurrentRows();

							void doOther() {
								// List list =
								// tableModelImgAfter.getCurrentRows();

								// list =
								// manualdeclearAction.synchroEmsMergerImg(
								// new Request(CommonVars.getCurrUser()),
								// list);
								// tableModelImgAfter.updateRows(list);
								new UniteAndUpdate(list).execute();
							}

							void changeState(String state) {
								if (JOptionPane.showConfirmDialog(
										DgEmsEdiMerger.this, "您确认要修改标记吗?",
										"提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
									return;
								}
								for (int i = 0; i < list.size(); i++) {
									EmsEdiMergerImgAfter obj = (EmsEdiMergerImgAfter) list
											.get(i);
									// 修改标志权限
									obj = manualdeclearAction.changeState(
											new Request(CommonVars
													.getCurrUser()), obj, state);
									tableModelImgAfter.updateRow(obj);
									// 若为删除关联的归并前也要设为删除状态
									boolean isImg = true;
									if (state.equals(ModifyMarkState.DELETED)) {
										manualdeclearAction.changeStateList(
												new Request(CommonVars
														.getCurrUser()), obj
														.getId(), state, isImg);
										EmsEdiMergerClientLogic.fillImgBefore(
												tableModelImgBefore, obj,
												jTableImgBefore);
									}
								}
							}
						};
						mym.show(jButton12, 0, jButton12.getHeight());
					} else if (jTabbedPane.getSelectedIndex() == 2) {
						if (tableModelExgAfter == null
								|| tableModelExgAfter.getCurrentRows() == null
								|| tableModelExgAfter.getCurrentRows().size() == 0) {
							JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
									"没有选中数据！", "提示！",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						MyModifJPopupMenu mym = new MyModifJPopupMenu(
								"更新(与内部归并同步)") {
							List list = tableModelExgAfter.getCurrentRows();

							void doOther() {

								// List list =
								// tableModelExgAfter.getCurrentRows();
								// list =
								// manualdeclearAction.synchroEmsMergerExg(
								// new Request(CommonVars.getCurrUser()),
								// list);
								// tableModelExgAfter.updateRows(list);
								new UniteAndUpdate(list).execute();

							}

							void changeState(String state) {
								if (JOptionPane.showConfirmDialog(
										DgEmsEdiMerger.this, "您确认要修改标记吗?",
										"提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
									return;
								}
								for (int i = 0; i < list.size(); i++) {
									EmsEdiMergerExgAfter obj = (EmsEdiMergerExgAfter) list
											.get(i);
									// 修改标志权限
									obj = manualdeclearAction.changeState(
											new Request(CommonVars
													.getCurrUser()), obj, state);
									tableModelExgAfter.updateRow(obj);
									// 若为删除关联的归并前也要设为删除状态
									boolean isImg = false;
									if (state.equals(ModifyMarkState.DELETED)) {
										manualdeclearAction.changeStateList(
												new Request(CommonVars
														.getCurrUser()), obj
														.getId(), state, isImg);
										EmsEdiMergerClientLogic.fillExgBefore(
												tableModelExgBefore,
												tableModelBom, obj,
												jTableExgBefore, jTableBom);
									}
								}
							}
						};
						mym.show(jButton12, 0, jButton12.getHeight());
						// }
					}
				}
			});

			// jButton12.addActionListener(new java.awt.event.ActionListener() {
			// public void actionPerformed(java.awt.event.ActionEvent e) {
			// getJPopupMenu1().show(jButton12, 0, jButton12.getHeight());
			// getJPopupMenu1().show(jButton12, 0, jButton12.getHeight());
			// }
			// });
		}
		return jButton12;
	}

	class UniteAndUpdate extends SwingWorker {

		List list = null;

		public UniteAndUpdate(List list) {
			this.list = list;
		}

		@Override
		protected Object doInBackground() throws Exception {
			// TODO Auto-generated method stub
			CommonProgress.showProgressDialog(DgEmsEdiMerger.this);
			CommonProgress.setMessage("系统正在与内部归并同步，请稍后...");

			if (jTabbedPane.getSelectedIndex() == 1) {
				list = manualdeclearAction.synchroEmsMergerImg(new Request(
						CommonVars.getCurrUser()), list);
				if (list != null) {
					tableModelImgAfter.updateRows(list);
				}
			} else if (jTabbedPane.getSelectedIndex() == 2) {
				list = manualdeclearAction.synchroEmsMergerExg(new Request(
						CommonVars.getCurrUser()), list);
				if (list != null) {
					tableModelExgAfter.updateRows(list);
				}
			}
			CommonProgress.closeProgressDialog();
			return null;
		}

	}

	/**
	 * This method initializes jPopupMenu1
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu1() {
		if (jPopupMenu1 == null) {
			jPopupMenu1 = new JPopupMenu();
			jPopupMenu1.add(getJMenuItem());
			jPopupMenu1.add(getJMenuItem1());
		}
		return jPopupMenu1;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem();
			jMenuItem.setText("更改修改标记");
			jMenuItem.setForeground(java.awt.Color.blue);
			jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(DgEmsEdiMerger.this,
							"您确认要修改标记吗?", "提示", 0) != 0) {
						return;
					}
					if (jTabbedPane.getSelectedIndex() == 1) {// 料件
						if (tableModelImgAfter == null)
							return;
						if (tableModelImgAfter.getCurrentRows() == null)
							return;
						/*
						 * if (!CommonVars.isManager()){ return; }
						 */
						List ls = tableModelImgAfter.getCurrentRows();
						for (int i = 0; i < ls.size(); i++) {
							EmsEdiMergerImgAfter obj = (EmsEdiMergerImgAfter) ls
									.get(i);
							if (obj.getModifyMark().equals(
									ModifyMarkState.ADDED)) {
								obj.setModifyMark(ModifyMarkState.UNCHANGE);
							} else if (obj.getModifyMark().equals(
									ModifyMarkState.UNCHANGE)) {
								obj.setModifyMark(ModifyMarkState.MODIFIED);
							} else if (obj.getModifyMark().equals(
									ModifyMarkState.MODIFIED)) {
								obj.setModifyMark(ModifyMarkState.DELETED);
							} else if (obj.getModifyMark().equals(
									ModifyMarkState.DELETED)) {
								obj.setModifyMark(ModifyMarkState.ADDED);
							}
							obj = manualdeclearAction.saveEmsEdiMergerImgAfter(
									new Request(CommonVars.getCurrUser()), obj);
							tableModelImgAfter.updateRow(obj);
						}
					} else if (jTabbedPane.getSelectedIndex() == 2) {
						if (tableModelExgAfter == null)
							return;
						if (tableModelExgAfter.getCurrentRows() == null)
							return;
						/*
						 * if (!CommonVars.isManager()){ return; }
						 */
						List ls = tableModelExgAfter.getCurrentRows();
						for (int i = 0; i < ls.size(); i++) {
							EmsEdiMergerExgAfter obj = (EmsEdiMergerExgAfter) ls
									.get(i);
							if (obj.getModifyMark().equals(
									ModifyMarkState.ADDED)) {
								obj.setModifyMark(ModifyMarkState.UNCHANGE);
							} else if (obj.getModifyMark().equals(
									ModifyMarkState.UNCHANGE)) {
								obj.setModifyMark(ModifyMarkState.MODIFIED);
							} else if (obj.getModifyMark().equals(
									ModifyMarkState.MODIFIED)) {
								obj.setModifyMark(ModifyMarkState.DELETED);
							} else if (obj.getModifyMark().equals(
									ModifyMarkState.DELETED)) {
								obj.setModifyMark(ModifyMarkState.ADDED);
							}
							obj = manualdeclearAction.saveEmsEdiMergerExgAfter(
									new Request(CommonVars.getCurrUser()), obj);
							tableModelExgAfter.updateRow(obj);
						}
					}
				}
			});
		}
		return jMenuItem;
	}

	/**
	 * This method initializes jMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem1() {
		if (jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.setText("更新(与内部归并同步)");
			jMenuItem1.setForeground(java.awt.Color.blue);
			jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 1) {// 料件
						List list = tableModelImgAfter.getCurrentRows();
						list = manualdeclearAction.synchroEmsMergerImg(
								new Request(CommonVars.getCurrUser()), list);
						tableModelImgAfter.updateRows(list);
					} else if (jTabbedPane.getSelectedIndex() == 2) {// 成品
						List list = tableModelExgAfter.getCurrentRows();
						list = manualdeclearAction.synchroEmsMergerExg(
								new Request(CommonVars.getCurrUser()), list);
						tableModelExgAfter.updateRows(list);
					}
				}
			});
		}
		return jMenuItem1;
	}

	/**
	 * This method initializes jPopupMenu2
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu2() {
		if (jPopupMenu2 == null) {
			jPopupMenu2 = new JPopupMenu();
			jPopupMenu2.add(getJMenuItem3());
			jPopupMenu2.add(getJMenuItem2());
		}
		return jPopupMenu2;
	}

	/**
	 * This method initializes jMenuItem2
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem2() {
		if (jMenuItem2 == null) {
			jMenuItem2 = new JMenuItem();
			jMenuItem2.setText("重新归并");
			jMenuItem2.setForeground(java.awt.Color.blue);
			jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgEmsBySeqNum dg = new DgEmsBySeqNum();
					dg.setVisible(true);
					Integer seqNum = Integer.valueOf(dg.getDValue());
					if (tableModelImgBefore == null)
						return;
					if (tableModelImgBefore.getCurrentRows() == null)
						return;
					List ls = tableModelImgBefore.getCurrentRows();
					manualdeclearAction.changeMergerSeqNumImg(new Request(
							CommonVars.getCurrUser()), ls, emsEdiMergerHead,
							seqNum);
					tableModelImgBefore.deleteRows(ls);
				}
			});
		}
		return jMenuItem2;
	}

	/**
	 * This method initializes jMenuItem3
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem3() {
		if (jMenuItem3 == null) {
			jMenuItem3.setText("更改修改标记");
			jMenuItem3 = new JMenuItem();
			jMenuItem3.setForeground(java.awt.Color.blue);
			jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(DgEmsEdiMerger.this,
							"您确认要修改标记吗?", "提示", 0) != 0) {
						return;
					}
					if (tableModelImgBefore == null)
						return;
					if (tableModelImgBefore.getCurrentRows() == null)
						return;
					/*
					 * if (!CommonVars.isManager()){ return; }
					 */
					List list = tableModelImgBefore.getCurrentRows();
					for (int i = 0; i < list.size(); i++) {
						EmsEdiMergerImgBefore obj = (EmsEdiMergerImgBefore) list
								.get(i);
						if (obj.getModifyMark().equals(ModifyMarkState.ADDED)) {
							obj.setModifyMark(ModifyMarkState.UNCHANGE);
						} else if (obj.getModifyMark().equals(
								ModifyMarkState.UNCHANGE)) {
							obj.setModifyMark(ModifyMarkState.MODIFIED);
						} else if (obj.getModifyMark().equals(
								ModifyMarkState.MODIFIED)) {
							obj.setModifyMark(ModifyMarkState.DELETED);
						} else if (obj.getModifyMark().equals(
								ModifyMarkState.DELETED)) {
							obj.setModifyMark(ModifyMarkState.ADDED);
						}
						obj = manualdeclearAction.saveEmsEdiMergerImgBefore(
								new Request(CommonVars.getCurrUser()), obj);
						tableModelImgBefore.updateRow(obj);
					}
				}
			});
		}
		return jMenuItem3;
	}

	/**
	 * This method initializes jPopupMenu3
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu3() {
		if (jPopupMenu3 == null) {
			jPopupMenu3 = new JPopupMenu();
			jPopupMenu3.add(getJMenuItem4());
			jPopupMenu3.add(getJMenuItem5());

		}
		return jPopupMenu3;
	}

	/**
	 * This method initializes jMenuItem4
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem4() {
		if (jMenuItem4 == null) {
			jMenuItem4 = new JMenuItem();
			jMenuItem4.setText("更改修改标记");
			jMenuItem4.setForeground(java.awt.Color.blue);
			jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(DgEmsEdiMerger.this,
							"您确认要修改标记吗?", "提示", 0) != 0) {
						return;
					}
					if (tableModelExgBefore == null)
						return;
					if (tableModelExgBefore.getCurrentRows() == null)
						return;
					/*
					 * if (!CommonVars.isManager()){ return; }
					 */

					List list = tableModelExgBefore.getCurrentRows();
					for (int i = 0; i < list.size(); i++) {
						EmsEdiMergerExgBefore obj = (EmsEdiMergerExgBefore) list
								.get(i);
						if (obj.getModifyMark().equals(ModifyMarkState.ADDED)) {
							obj.setModifyMark(ModifyMarkState.UNCHANGE);
						} else if (obj.getModifyMark().equals(
								ModifyMarkState.UNCHANGE)) {
							obj.setModifyMark(ModifyMarkState.MODIFIED);
						} else if (obj.getModifyMark().equals(
								ModifyMarkState.MODIFIED)) {
							obj.setModifyMark(ModifyMarkState.DELETED);
						} else if (obj.getModifyMark().equals(
								ModifyMarkState.DELETED)) {
							obj.setModifyMark(ModifyMarkState.ADDED);
						}
						obj = manualdeclearAction.saveEmsEdiMergerExgBefore(
								new Request(CommonVars.getCurrUser()), obj);
						tableModelExgBefore.updateRow(obj);
					}
				}
			});
		}
		return jMenuItem4;
	}

	/**
	 * This method initializes jMenuItem5
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem5() {
		if (jMenuItem5 == null) {
			jMenuItem5 = new JMenuItem();
			jMenuItem5.setText("重新归并");
			jMenuItem5.setForeground(java.awt.Color.blue);
			jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgEmsBySeqNum dg = new DgEmsBySeqNum();
					dg.setVisible(true);
					if (dg.getDValue() == null || dg.getDValue().equals("")) {
						return;
					}
					Integer seqNum = Integer.valueOf(dg.getDValue());
					if (tableModelExgBefore == null)
						return;
					if (tableModelExgBefore.getCurrentRows() == null)
						return;
					List ls = tableModelExgBefore.getCurrentRows();
					manualdeclearAction.changeMergerSeqNumExg(new Request(
							CommonVars.getCurrUser()), ls, emsEdiMergerHead,
							seqNum);
					tableModelExgBefore.deleteRows(ls);
				}
			});
		}
		return jMenuItem5;
	}

	/**
	 * This method initializes jPopupMenu4
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu4() {
		if (jPopupMenu4 == null) {
			jPopupMenu4 = new JPopupMenu();
			jPopupMenu4.add(getJMenuItem6());
			jPopupMenu4.add(getJMenuItem7());
		}
		return jPopupMenu4;
	}

	/**
	 * This method initializes jMenuItem6
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem6() {
		if (jMenuItem6 == null) {
			jMenuItem6 = new JMenuItem();
			jMenuItem6.setText("更改修改标记");
			jMenuItem6.setForeground(java.awt.Color.blue);
			jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(DgEmsEdiMerger.this,
							"您确认要修改标记吗?", "提示", 0) != 0) {
						return;
					}
					if (tableModelBom == null)
						return;
					if (tableModelBom.getCurrentRows() == null)
						return;
					/*
					 * if (!CommonVars.isManager()){ return; }
					 */
					List list = tableModelBom.getCurrentRows();
					for (int i = 0; i < list.size(); i++) {
						EmsEdiMergerExgBom obj = (EmsEdiMergerExgBom) list
								.get(i);
						if (obj.getModifyMark().equals(ModifyMarkState.ADDED)) {
							obj.setModifyMark(ModifyMarkState.UNCHANGE);
						} else if (obj.getModifyMark().equals(
								ModifyMarkState.UNCHANGE)) {
							obj.setModifyMark(ModifyMarkState.MODIFIED);
						} else if (obj.getModifyMark().equals(
								ModifyMarkState.MODIFIED)) {
							obj.setModifyMark(ModifyMarkState.DELETED);
						} else if (obj.getModifyMark().equals(
								ModifyMarkState.DELETED)) {
							obj.setModifyMark(ModifyMarkState.ADDED);
						}
						obj = manualdeclearAction.saveEmsEdiMergerExgBom(
								new Request(CommonVars.getCurrUser()), obj);
						tableModelBom.updateRow(obj);
					}
				}
			});
		}
		return jMenuItem6;
	}

	/**
	 * This method initializes jMenuItem7
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem7() {
		if (jMenuItem7 == null) {
			jMenuItem7 = new JMenuItem();
			jMenuItem7.setText("全选(到准备申报)");
			jMenuItem7.setForeground(java.awt.Color.blue);
			jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelVersion == null)
						return;
					if (tableModelVersion.getCurrentRow() == null)
						return;
					emsEdiMergerVersion = (EmsEdiMergerVersion) tableModelVersion
							.getCurrentRow();
					Integer version = emsEdiMergerVersion.getVersion();
					dataSourceBom = manualdeclearAction.findEmsEdiMergerBom(
							new Request(CommonVars.getCurrUser()), // 得到BOM
							emsEdiMergerVersion);
					List ls = manualdeclearAction.emsMergerSelectAllSendState(
							new Request(CommonVars.getCurrUser()),
							dataSourceBom);
					tableModelBom.updateRows(ls);
				}
			});
		}
		return jMenuItem7;
	}

	/**
	 * This method initializes btnWasterToInt
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnWasterToInt() {
		if (btnWasterToInt == null) {
			btnWasterToInt = new JButton();
			btnWasterToInt.setText("损耗取整");
			btnWasterToInt.setPreferredSize(new Dimension(60, 30));
			btnWasterToInt
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = tableModelBom.getList();
							if (list.size() == 0) {
								JOptionPane.showMessageDialog(
										DgEmsEdiMerger.this, "没有料件,不能取整!",
										"提示!", JOptionPane.WARNING_MESSAGE);
								return;
							} else {
								List rlist = manualdeclearAction
										.makeEmsEdiMergerExgBomWasteToInt(
												new Request(CommonVars
														.getCurrUser()), list);
								tableModelBom.updateRows(rlist);
								JOptionPane.showMessageDialog(
										DgEmsEdiMerger.this, "该版本所有料件损耗已成功取整!",
										"提示!", JOptionPane.INFORMATION_MESSAGE);
							}
						}
					});
		}
		return btnWasterToInt;
	}

	private void makeWasterToInt() {

	}

	/**
	 * This method initializes jButton9
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton9() {
		if (jButton9 == null) {
			jButton9 = new JButton();
			jButton9.setText("BOM导出");
			jButton9.setPreferredSize(new Dimension(62, 30));
			jButton9.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBomExport dg = new DgBomExport();
					dg.setHead(emsEdiMergerHead);
					dg.setVisible(true);
				}
			});
		}
		return jButton9;
	}

	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jPanel8 = new JPanel();
			jPanel8.setLayout(new BorderLayout());
			jPanel8.add(getJScrollPane6(), BorderLayout.CENTER);
		}
		return jPanel8;
	}

	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel9() {
		if (jPanel9 == null) {
			jPanel9 = new JPanel();
			jPanel9.setLayout(new BorderLayout());
			jPanel9.add(getJScrollPane7(), BorderLayout.CENTER);
		}
		return jPanel9;
	}

	/**
	 * This method initializes jTableimg
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTableimg() {
		if (jTableimg == null) {
			jTableimg = new JTable();
			jTableimg.setRowHeight(25);
		}
		return jTableimg;
	}

	/**
	 * This method initializes jScrollPane6
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane6() {
		if (jScrollPane6 == null) {
			jScrollPane6 = new JScrollPane();
			jScrollPane6.setViewportView(getJTableBom());
		}
		return jScrollPane6;
	}

	/**
	 * This method initializes jScrollPane7
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane7() {
		if (jScrollPane7 == null) {
			jScrollPane7 = new JScrollPane();
			jScrollPane7.setViewportView(getJTableimg());
		}
		return jScrollPane7;
	}

	/**
	 * This method initializes btnCope
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCope() {
		if (btnCope == null) {
			btnCope = new JButton();
			btnCope.setText("复制到");
			btnCope.setPreferredSize(new Dimension(60, 30));
			btnCope.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					EmsEdiMergerVersion emsEdiMergerVersion = (EmsEdiMergerVersion) tableModelVersion
							.getCurrentRow();
					if (emsEdiMergerVersion == null) {
						JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
								"请选择版本号!");
					} else {
						// 输入的版本号字符串
						String strVersion = JOptionPane.showInputDialog(
								DgEmsEdiMerger.this, "请输入整数新版本号!");
						// 输入的版本号
						Integer version;
						// 转换
						try {
							version = Integer.parseInt(strVersion);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(DgEmsEdiMerger.this,
									"请输入整数新版本号!");
							return;
						}
						// 判断版本号是否已经存在
						List<EmsEdiMergerVersion> versions = tableModelVersion
								.getList();
						for (EmsEdiMergerVersion v : versions) {
							if (v.getVersion().equals(version)) {
								JOptionPane.showMessageDialog(
										DgEmsEdiMerger.this, "已经存在的版本号!");
								return;
							}
						}
						// 保存版本号以及相关成品BOM信息
						EmsEdiMergerVersion emsEdiMergerVersion1 = new EmsEdiMergerVersion();
						try {
							PropertyUtils.copyProperties(emsEdiMergerVersion1,
									emsEdiMergerVersion);
						} catch (Exception e1) {

						}
						emsEdiMergerVersion1.setId(null);
						emsEdiMergerVersion1.setVersion(version);
						emsEdiMergerVersion1.setName("版本：" + version);
						emsEdiMergerVersion1 = manualdeclearAction
								.saveEmsEdiMergerVersion(
										new Request(CommonVars.getCurrUser()),
										emsEdiMergerVersion1);
						tableModelVersion.addRow(emsEdiMergerVersion1);
						// Bom保存
						// TODO emsEdiMergerVersion is null
						// List<EmsEdiMergerExgBom> oldList = tableModelBom
						// .getList();
						List<EmsEdiMergerExgBom> oldList = manualdeclearAction
								.findEmsEdiMergerBom(
										new Request(CommonVars.getCurrUser()), // 得到BOM
										emsEdiMergerVersion);
						List<EmsEdiMergerExgBom> newList = new ArrayList<EmsEdiMergerExgBom>();
						newList = manualdeclearAction.newAndCope(oldList,
								emsEdiMergerVersion1);
						tableModelBom.setList(newList);
						EmsEdiMergerClientLogic.transModifyMark(jTableBom);
						EmsEdiMergerClientLogic.transSendState(jTableBom);
					}
				}
			});
		}
		return btnCope;
	}

	/**
	 * This method initializes btnMaxVersion
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnMaxVersion() {
		if (btnMaxVersion == null) {
			btnMaxVersion = new JButton();
			btnMaxVersion.setText("最大版本号");
			btnMaxVersion.setPreferredSize(new Dimension(72, 30));
			btnMaxVersion
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Map<String, Integer> map = manualdeclearAction
									.findMaxMaterielBomVersion(new Request(
											CommonVars.getCurrUser()));
							List list = tableModelBomExg.getList();
							for (int i = 0; i < list.size(); i++) {
								EmsEdiMergerExgBefore emsEdiMergerExgBefore = (EmsEdiMergerExgBefore) list
										.get(i);
								emsEdiMergerExgBefore.setMaxVersion(map
										.get(emsEdiMergerExgBefore.getPtNo()
												+ "/"
												+ emsEdiMergerExgBefore
														.getEmsEdiMergerExgAfter()
														.getEmsEdiMergerHead()
														.getDeclareState()));
							}
							tableModelBomExg.fireTableDataChanged();
						}
					});
		}
		return btnMaxVersion;
	}

	/**
	 * This method initializes btnReverseCheck
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnReverseCheck() {
		if (btnReverseCheck == null) {
			btnReverseCheck = new JButton();
			btnReverseCheck.setText("反向查询");
			btnReverseCheck.setPreferredSize(new Dimension(60, 30));
			btnReverseCheck
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgReverseCheck dg = new DgReverseCheck();
							dg.setEmsEdiMergerHead(emsEdiMergerHead);
							dg.setVisible(true);
						}
					});
		}
		return btnReverseCheck;
	}

	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(null);
			javax.swing.JLabel jLabel27 = new JLabel();

			javax.swing.JLabel jLabel26 = new JLabel();

			javax.swing.JLabel jLabel25 = new JLabel();

			javax.swing.JLabel jLabel24 = new JLabel();

			javax.swing.JLabel jLabel23 = new JLabel();

			javax.swing.JLabel jLabel22 = new JLabel();

			javax.swing.JLabel jLabel21 = new JLabel();

			javax.swing.JLabel jLabel20 = new JLabel();

			javax.swing.JLabel jLabel19 = new JLabel();

			javax.swing.JLabel jLabel18 = new JLabel();

			javax.swing.JLabel jLabel17 = new JLabel();

			javax.swing.JLabel jLabel16 = new JLabel();

			javax.swing.JLabel jLabel15 = new JLabel();

			javax.swing.JLabel jLabel14 = new JLabel();

			javax.swing.JLabel jLabel13 = new JLabel();

			javax.swing.JLabel jLabel12 = new JLabel();

			javax.swing.JLabel jLabel11 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();

			javax.swing.JLabel jLabel9 = new JLabel();

			javax.swing.JLabel jLabel8 = new JLabel();

			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel6 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();
			jLabel.setText("帐册编号");
			jLabel.setBounds(new Rectangle(25, 28, 97, 18));
			jLabel1.setText("变更次数");
			jLabel1.setBounds(new Rectangle(289, 29, 90, 17));
			jLabel2.setText("企业内部编号");
			jLabel2.setBounds(new Rectangle(543, 28, 72, 18));
			jLabel3.setText("批文帐册号");
			jLabel3.setBounds(new Rectangle(25, 60, 97, 18));
			jLabel4.setText("批准证编号");
			jLabel4.setBounds(new Rectangle(289, 61, 90, 17));
			jLabel5.setText("帐册类型");
			jLabel5.setBounds(new Rectangle(543, 60, 72, 18));
			jLabel6.setText("经营单位代码");
			jLabel6.setBounds(new Rectangle(25, 90, 97, 18));
			jLabel7.setText("经营单位名称");
			jLabel7.setBounds(new Rectangle(289, 91, 90, 17));
			jLabel8.setText("收货单位代码");
			jLabel8.setBounds(new Rectangle(25, 124, 97, 18));
			jLabel9.setText("收货单位名称");
			jLabel9.setBounds(new Rectangle(289, 125, 90, 17));
			jLabel10.setText("电话号码");
			jLabel10.setBounds(new Rectangle(25, 160, 97, 18));
			jLabel11.setText("地址");
			jLabel11.setBounds(new Rectangle(289, 161, 90, 17));
			jLabel12.setText("开始有效期");
			jLabel12.setBounds(new Rectangle(25, 188, 90, 17));
			jLabel13.setText("结束有效期");
			jLabel13.setBounds(new Rectangle(287, 186, 92, 25));
			jLabel14.setText("申请单位类型");
			jLabel14.setBounds(new Rectangle(543, 189, 72, 18));
			jLabel15.setText("投资金额");
			jLabel15.setBounds(new Rectangle(25, 223, 97, 18));
			jLabel15.setForeground(new java.awt.Color(0, 51, 255));
			jLabel16.setText("万美元");
			jLabel16.setBounds(new Rectangle(498, 220, 36, 18));
			jLabel16.setForeground(new java.awt.Color(0, 51, 255));
			jLabel17.setText("年生产加工能力");
			jLabel17.setBounds(new Rectangle(289, 224, 90, 17));
			jLabel17.setForeground(new java.awt.Color(0, 51, 255));
			jLabel18.setText("万美元");
			jLabel18.setBounds(new Rectangle(240, 221, 36, 18));
			jLabel18.setForeground(new java.awt.Color(0, 51, 255));
			jLabel19.setText("最大周转金额");
			jLabel19.setBounds(new Rectangle(543, 223, 72, 18));
			jLabel19.setForeground(new java.awt.Color(0, 51, 255));
			jLabel20.setText("万美元");
			jLabel20.setBounds(new Rectangle(725, 223, 36, 18));
			jLabel20.setForeground(new java.awt.Color(0, 51, 255));
			jLabel21.setText("录入员代号");
			jLabel21.setBounds(new Rectangle(25, 252, 97, 18));
			jLabel22.setText("录入日期");
			jLabel22.setBounds(new Rectangle(289, 253, 90, 17));
			jLabel23.setText("备案批准日期");
			jLabel23.setBounds(new Rectangle(543, 252, 72, 18));
			jLabel24.setText("申报日期");
			jLabel24.setBounds(new Rectangle(25, 282, 90, 17));
			jLabel25.setText("申报时间");
			jLabel25.setBounds(new Rectangle(287, 280, 97, 18));
			jLabel26.setText("变更批准日期");
			jLabel26.setBounds(new Rectangle(543, 284, 72, 18));
			jLabel27.setText("备注");
			jLabel27.setBounds(new Rectangle(25, 315, 97, 18));
			jPanel7.setBounds(new Rectangle(67, 33, 786, 352));
			jPanel7.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createBevelBorder(BevelBorder.LOWERED),
					"\u8868\u5934\u4fe1\u606f",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel7.add(jLabel, null);
			jPanel7.add(getTfEmsNo(), null);
			jPanel7.add(jLabel1, null);
			jPanel7.add(getTfChangeTimes(), null);
			jPanel7.add(jLabel2, null);
			jPanel7.add(getTfCopEmsNo(), null);
			jPanel7.add(jLabel3, null);
			jPanel7.add(getTfSancEmsNo(), null);
			jPanel7.add(jLabel4, null);
			jPanel7.add(getTfEmsApprNo(), null);
			jPanel7.add(jLabel5, null);
			jPanel7.add(getTfEmsType(), null);
			jPanel7.add(jLabel6, null);
			jPanel7.add(getTfTradeCode(), null);
			jPanel7.add(jLabel7, null);
			jPanel7.add(getTfTradeName(), null);
			jPanel7.add(jLabel8, null);
			jPanel7.add(getTfMachCode(), null);
			jPanel7.add(jLabel9, null);
			jPanel7.add(getTfMachName(), null);
			jPanel7.add(jLabel10, null);
			jPanel7.add(getTfTel(), null);
			jPanel7.add(jLabel11, null);
			jPanel7.add(getTfAddress(), null);
			jPanel7.add(jLabel12, null);
			jPanel7.add(getTfDeclareErType(), null);
			jPanel7.add(getCmbBeginDate(), null);
			jPanel7.add(getCmbEndDate(), null);
			jPanel7.add(jLabel14, null);
			jPanel7.add(jLabel15, null);
			jPanel7.add(jLabel16, null);
			jPanel7.add(jLabel17, null);
			jPanel7.add(jLabel18, null);
			jPanel7.add(jLabel19, null);
			jPanel7.add(jLabel20, null);
			jPanel7.add(jLabel21, null);
			jPanel7.add(getTfInputUser(), null);
			jPanel7.add(jLabel22, null);
			jPanel7.add(getTfInputDate(), null);
			jPanel7.add(jLabel23, null);
			jPanel7.add(getJTextField18(), null);
			jPanel7.add(jLabel24, null);
			jPanel7.add(getTfDeclareDate(), null);
			jPanel7.add(jLabel25, null);
			jPanel7.add(getTfDeclareTime(), null);
			jPanel7.add(jLabel26, null);
			jPanel7.add(getJTextField21(), null);
			jPanel7.add(jLabel27, null);
			jPanel7.add(getTfNote(), null);
			jPanel7.add(getTfInvestAmount(), null);
			jPanel7.add(getTfMachinAbility(), null);
			jPanel7.add(getTfMaxTurnMoney(), null);
			jPanel7.add(jLabel13, null);
		}
		return jPanel7;
	}

	// public void aaa(){
	// if (dataSourceBom != null
	// && dataSourceBom.size() > 0) {
	// list = manualdeclearAction
	// .findEdiBomptNoToBomseqNum(
	// new Request(CommonVars
	// .getCurrUser()),
	// null, emsEdiMergerVersion
	// .getId());
	// if (list != null && !list.isEmpty()) {
	// EmsEdiMergerClientLogic
	// .initTableBomImg(jTableimg,
	// list);
	// } else {
	// EmsEdiMergerClientLogic
	// .initTableBomImg(jTableimg,
	// new Vector());
	// }
	// } else {
	// EmsEdiMergerClientLogic.initTableBomImg(
	// jTableimg, new Vector());
	// }
	// }
} // @jve:decl-index=0:visual-constraint="-129,9"

abstract class MyJPopupMenu extends JPopupMenu {
	JMenuItem itm1 = null;

	JMenuItem itm2 = null;

	JMenuItem itm3 = null;

	public MyJPopupMenu() {
		super();
		this.add(getItm1());
		this.add(getItm2());
		this.add(getItm3());
	}

	abstract void changeState(String state);

	public JMenuItem getItm1() {
		if (itm1 == null) {
			itm1 = new JMenuItem("未发送");
			itm1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					changeState(SendState.NOT_SEND);
				}
			});
		}
		return itm1;
	}

	public JMenuItem getItm2() {
		if (itm2 == null) {
			itm2 = new JMenuItem("准备发送");
			itm2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					changeState(SendState.WAIT_SEND);
				}
			});
		}
		return itm2;
	}

	public JMenuItem getItm3() {
		if (itm3 == null) {
			itm3 = new JMenuItem("已经发送");
			itm3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					changeState(SendState.SEND);
				}
			});
		}
		return itm3;
	}
}

abstract class MyModifJPopupMenu extends JPopupMenu {
	JMenuItem itm1 = null;

	JMenuItem itm2 = null;

	JMenuItem itm3 = null;

	JMenuItem itm4 = null;

	JMenuItem itm5 = null;

	public MyModifJPopupMenu(String str) {
		super();
		this.add(getItm1());
		this.add(getItm2());
		this.add(getItm3());
		this.add(getItm4());
		this.addSeparator();
		this.add(getItm5());
		itm5.setText(str);
	}

	abstract void changeState(String state);

	abstract void doOther();

	public JMenuItem getItm1() {
		if (itm1 == null) {
			itm1 = new JMenuItem("更改修改标志（未修改）");
			itm1.setForeground(Color.blue);
			itm1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					changeState(ModifyMarkState.UNCHANGE);
				}
			});
		}
		return itm1;
	}

	public JMenuItem getItm2() {
		if (itm2 == null) {
			itm2 = new JMenuItem("更改修改标志（已修改）");
			itm2.setForeground(Color.blue);
			itm2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					changeState(ModifyMarkState.MODIFIED);
				}
			});
		}
		return itm2;
	}

	public JMenuItem getItm3() {
		if (itm3 == null) {
			itm3 = new JMenuItem("更改修改标志（已删除）");
			itm3.setForeground(Color.blue);
			itm3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					changeState(ModifyMarkState.DELETED);
				}
			});
		}
		return itm3;
	}

	public JMenuItem getItm4() {
		if (itm4 == null) {
			itm4 = new JMenuItem("更改修改标志（新增）");
			itm4.setForeground(Color.blue);
			itm4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					changeState(ModifyMarkState.ADDED);
				}
			});
		}
		return itm4;
	}

	public JMenuItem getItm5() {
		if (itm5 == null) {
			itm5 = new JMenuItem(" ");
			itm5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doOther();
				}
			});
		}
		return itm5;
	}
}