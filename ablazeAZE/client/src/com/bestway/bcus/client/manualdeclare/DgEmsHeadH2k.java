package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.PayMode;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.SendState;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 *         电子账册备案维护 修改 sxk
 */
public class DgEmsHeadH2k extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnInsert = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JButton btnSave = null;

	private JButton btnElse = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jpExg = null;

	private JPanel jpImg = null;

	private JPanel jpHead = null;

	private JTextField tfEmsTpye = null;

	private JTextField tfEmsState = null;

	private JTextField tfDeclareType = null;

	private JTextField tfCopEmsNo = null;

	private JTextField tfEmsNo = null;

	private JTextField tfSancEmsNo = null;

	private JTextField tfTradeCode = null;

	private JTextField tfTradeName = null;

	private JTextField tfMachCode = null;

	private JTextField tfMachName = null;

	private JTextField tfTel = null;

	private JTextField tfAddress = null;

	private JTextField tfDeclareErType = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JTextField tfInputEr = null;

	private JTextField tfInputDate = null;

	private JTextField tfNewApprDate = null;

	private JTextField tfDeclareDate = null;

	private JTextField tfChangeApprDate = null;

	private JTextField tfNote = null;

	private EmsHeadH2k emsHeadH2k = null; // 电子帐册表头 // @jve:decl-index=0:

	private List dataSourceBom = null; // @jve:decl-index=0:

	EmsHeadH2kExg emsHeadH2kExg = null; // 成品 // @jve:decl-index=0:

	private EmsHeadH2kVersion emsHeadH2kVersion = null; // 版本号 //

	// @jve:decl-index=0:

	private PayMode payMode = null; // 保税方式

	private LevyKind levyKind = null; // 征免性质

	private MachiningType machiningType = null; // 加工种类

	private ManualDeclareAction manualdeclearAction = null;

	private JTableListModel tableModel = null;

	private JTableListModel tableModelImg = null;

	private JTableListModel tableModelExg = null;

	private JTableListModel tableModelBom = null;

	private JTableListModel tableModelVersion = null;

	private boolean isChange = false; // 判断是否变更状态

	private boolean isEdit = true; // 判断是否为编辑状态

	private JFormattedTextField tfMachAbility = null;

	private JFormattedTextField tfMaxTurnMoney = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private JTextField tfOutTradeCo = null;

	private JTextField tfIePort = null;

	private JButton btnIePort = null;

	private JTextField tfLevyKind = null;

	private JButton btnLevyKind = null;

	private JTextField tfMachiningType = null;

	private JButton btnMachiningType = null;

	private JTextField tfEmsApprNo = null;

	private JTextField tfPayMode = null;

	private JButton btnPayMode = null;

	private JTable jTableImg = null;

	private JScrollPane jScrollPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JTable jTableExg = null;

	private JScrollPane jScrollPane1 = null;

	private JToolBar jToolBar1 = null;

	private JButton btnAddBom = null;

	private JButton btnEditBom = null;

	private JButton jButton6 = null;

	private JButton jButton7 = null;

	private JButton jButton8 = null;

	private JButton jButton10 = null;

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JTable jTableBom = null;

	private JScrollPane jScrollPane2 = null;

	private JTable jTableVersion = null;

	private JScrollPane jScrollPane3 = null;

	private JPopupMenu jPopupMenu = null; // @jve:decl-index=0:visual-constraint="987,194"

	private JMenuItem jMenuItem = null; // @jve:decl-index=0:visual-constraint="993,507"

	private JMenuItem jMenuItem1 = null; // @jve:decl-index=0:visual-constraint="736,280"

	private int dataState = DataState.BROWSE;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	// private ButtonGroup buttonGroup = new ButtonGroup(); //
	// @jve:decl-index=0:

	private boolean isHistoryChange = false;

	private EmsEdiTrHead emsEdiTrHead = null; // 经营范围表头

	private JButton btnWare = null;

	private JButton btnEditFactoryPrice = null;

	private JButton jButton12 = null;

	private JButton jButton13 = null;

	private JButton btnEditDeclareState = null;

	private JButton btnBomExport = null;

	private JButton jButton15 = null;

	private String isEmsH2kSendSign = ""; // @jve:decl-index=0:

	private JButton btnPrice = null;

	private JMenuItem jMenuItem2 = null; // @jve:decl-index=0:visual-constraint="852,186"

	private JMenuItem jMenuItem3 = null; // @jve:decl-index=0:visual-constraint="923,260"

	private JPopupMenu jPopupMenu1 = null; // @jve:decl-index=0:visual-constraint="953,405"

	private JPanel jPanel4 = null;

	private JComboBox cbbDeclareState = null;

	private JTextField jTextField25 = null;

	private JButton btnSearch = null;

	private String imgLastDate = null; // @jve:decl-index=0:

	private String exgLastDate = null; // @jve:decl-index=0:

	private JPopupMenu jPopupMenu2 = null; // @jve:decl-index=0:visual-constraint="1017,320"

	private JMenuItem jMenuItem4 = null; // @jve:decl-index=0:visual-constraint="888,136"

	private JMenuItem jMenuItem5 = null; // @jve:decl-index=0:visual-constraint="862,175"

	private JButton jButton18 = null;

	private JButton jButton19 = null;

	private JPopupMenu jPopupMenu3 = null; // @jve:decl-index=0:visual-constraint="1009,140"

	private JMenuItem jMenuItem6 = null; // @jve:decl-index=0:visual-constraint="848,160"

	private JMenuItem jMenuItem7 = null; // @jve:decl-index=0:visual-constraint="866,266"

	private JPopupMenu jPopupMenu4 = null; // @jve:decl-index=0:visual-constraint="937,160"

	private JMenuItem jMenuItem8 = null; // @jve:decl-index=0:visual-constraint="917,348"

	private JMenuItem jMenuItem9 = null; // @jve:decl-index=0:visual-constraint="950,359"

	private JButton btnMaxVersion = null;

	private JToolBar jToolBar2 = null;

	private JToolBar jToolBar3 = null;

	private JLabel jLabel31 = null;

	private JPanel jPanel41 = null;

	private JComboBox cbbExgDeclareState = null;

	private JTextField tfExgCondint = null;

	private JButton btnExgSearch = null;

	private JButton btnWareCheck = null;

	private JLabel jLabel311 = null;

	private JPanel jPanel5 = null;

	private JPopupMenu jPopupMenu5 = null;
	/** 新增版本与料件 */
	private JMenuItem miVersionOrImg = null;
	/** 新增版料件 */
	private JMenuItem miImg = null;
	/** 新增料件 */
	private JMenuItem miAllBomImg = null;
	/** 新增单个BOM料件 */
	private JMenuItem miVersionOrBomImg = null;

	private JButton jButtonSupplementBOM = null;

	private List dmList = new Vector();

	/**
	 * This is the default constructor
	 */
	public DgEmsHeadH2k() {
		super();

		manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();
		// buttonGroup.add(jRadioButton);
		// buttonGroup.add(jRadioButton1);
		jRadioButton1.setSelected(true);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(935, 591);
		this.setTitle("电子帐册备案维护");
		this.setContentPane(getJContentPane());
		//
		// public void windowOpened(java.awt.event.WindowEvent e) {
		// showHeadData();
		//
		// // 料件
		// showImgData();
		//
		// // 成品及单耗
		// showExgBomData();
		// }
		// });
	}

	public void dispose() {

		super.dispose();
	}

	public void setVisible(boolean b) {
		if (b == true) {

			long s = System.currentTimeMillis();
			// 显示表头
			showHeadData();
			long e = System.currentTimeMillis();
			System.out.println("time:" + (e - s) / 1000.0);

			s = System.currentTimeMillis();
			// 显示料件
			showImgData();
			e = System.currentTimeMillis();
			System.out.println("time:" + (e - s) / 1000.0);

			s = System.currentTimeMillis();
			// 显示成品及单耗
			showExgBomData();
			e = System.currentTimeMillis();
			System.out.println("time:" + (e - s) / 1000.0);

			setState();
		}
		super.setVisible(b);
	}

	/**
	 * 初始化料件表
	 * 
	 * @param list
	 * @return
	 */
	private JTableListModel initTableImg(final List list) {
		tableModelImg = new JTableListModel(jTableImg, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("修改标志", "modifyMark", 60));
						if (getIsEmsSend()) {
							list.add(addColumn("申报标志", "sendState", 80));
						}
						list.add(addColumn("归并序号", "seqNum", 60, Integer.class));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("申报数量", "qty", 100));
						list.add(addColumn("计量单位", "unit.name", 80));
						list.add(addColumn("第一法定单位", "complex.firstUnit.name",
								80));
						list.add(addColumn("第二法定单位", "complex.secondUnit.name",
								80));
						list.add(addColumn("法定单位比例因子", "legalUnitGene", 80));
						list.add(addColumn("第二法定单位比例因子", "legalUnit2Gene", 80));
						list.add(addColumn("重量比例因子", "weigthUnitGene", 120));
						list.add(addColumn("是否主料", "isMainImg", 100));
						list.add(addColumn("币制", "curr.name", 60));
						list.add(addColumn("企业申报单价", "declarePrice", 80));
						list.add(addColumn("单位毛重", "unitGrossWeight", 80));
						list.add(addColumn("单位净重", "unitNetWeight", 80));
						list.add(addColumn("工厂申报单价", "factoryPrice", 80));
						list.add(addColumn("开始有效期", "beginDate", 80));
						list.add(addColumn("结束有效期", "endDate", 80));
						list.add(addColumn("备注", "note", 100));
						list.add(addColumn("变更次数", "modifyTimes", 80));
						list.add(addColumn("变更日期", "changeDate", 100));
						return list;
					}
				});
		if (getIsEmsSend())
			jTableImg.getColumnModel().getColumn(14)
					.setCellRenderer(new TableCheckBoxRender());
		else
			jTableImg.getColumnModel().getColumn(13)
					.setCellRenderer(new TableCheckBoxRender());
		EmsEdiMergerClientLogic.transModifyMark(jTableImg);
		return tableModelImg;
	}

	/**
	 * 初始化成品版本号表
	 * 
	 * @param list
	 */
	private void initTableVersion(final List list) {
		tableModelVersion = new JTableListModel(jTableVersion, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("版本号", "name", 60));
						return list;
					}
				});
	}

	/**
	 * 初始化成品及单耗表
	 * 
	 * @param list
	 * @return
	 */
	private JTableListModel initTableExg(final List list) {
		tableModelExg = new JTableListModel(jTableExg, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("修改标志", "modifyMark", 60));
						if (getIsEmsSend()) {
							list.add(addColumn("申报标志", "sendState", 80));
						}
						list.add(addColumn("归并序号", "seqNum", 60, Integer.class));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("申报数量", "qty", 100));
						list.add(addColumn("计量单位", "unit.name", 80));
						list.add(addColumn("第一法定单位", "complex.firstUnit.name",
								80));
						list.add(addColumn("第二法定单位", "complex.secondUnit.name",
								80));
						list.add(addColumn("法定单位比例因子", "legalUnitGene", 80));
						list.add(addColumn("第二法定单位比例因子", "legalUnit2Gene", 80));
						list.add(addColumn("重量比例因子", "weigthUnitGene", 120));
						list.add(addColumn("币制", "curr.name", 60));
						list.add(addColumn("企业申报单价", "declarePrice", 80));
						list.add(addColumn("单位毛重", "unitGrossWeight", 80));
						list.add(addColumn("单位净重", "unitNetWeight", 80));
						list.add(addColumn("工厂申报单价", "factoryPrice", 80));
						list.add(addColumn("开始有效期", "beginDate", 80));
						list.add(addColumn("结束有效期", "endDate", 80));
						list.add(addColumn("备注", "note", 100));
						list.add(addColumn("变更次数", "modifyTimes", 80));
						list.add(addColumn("变更日期", "changeDate", 100));
						list.add(addColumn("最大版本号", "maxVersion", 100));

						return list;
					}
				});
		EmsEdiMergerClientLogic.transModifyMark(jTableExg);
		return tableModelExg;
	}

	public boolean getIsEmsSend() {
		
		String isSend = manualdeclearAction.getBpara(
				new Request(CommonVars.getCurrUser()),
				BcusParameter.EmsEdiH2kSend);
		
		if (isSend != null && "1".equals(isSend)) {
			return true;
		}
		return false;
	}

	/**
	 * 初始化成品BOM表
	 * 
	 * @param list
	 * @return
	 */
	private JTableListModel initTableBom(final List list) {
		tableModelBom = new JTableListModel(jTableBom, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("修改标志", "modifyMark", 60));
						if (getIsEmsSend()) {
							list.add(addColumn("申报标志", "sendState", 60));
						}
						list.add(addColumn("料件序号", "seqNum", 60, Integer.class));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("申报单位", "unit.name", 60));
						list.add(addColumn("单耗", "unitWear", 80));
						list.add(addColumn("损耗率%", "wear", 80));
						list.add(addColumn("单价", "price", 80));
						list.add(addColumn("备注", "note", 100));
						list.add(addColumn("成品序号",
								"emsHeadH2kVersion.emsHeadH2kExg.seqNum", 100));
						list.add(addColumn("版本号", "emsHeadH2kVersion.version",
								100));
						list.add(addColumn("变更次数", "modifyTimes", 60));
						list.add(addColumn("变更日期", "changeDate", 80));
						list.add(addColumn("创建人", "createPeople", 80));
						list.add(addColumn("修改人", "modifyPeople", 80));
						return list;
					}
				});
		List<JTableListColumn> cms = tableModelBom.getColumns();
		// int unitweardigits = 6;
		// int weardigits = 6;
		// String unitweardig = manualdeclearAction
		// .getBpara(new Request(CommonVars.getCurrUser()),
		// BcusParameter.EMSBOM_UNITWEAR_DIGITS);
		// String weardig = manualdeclearAction.getBpara(new Request(CommonVars
		// .getCurrUser()), BcusParameter.EMSBOM_WEAR_DIGITS);
		// if (unitweardig != null && !"".equals(unitweardig)) {
		// unitweardigits = Integer.parseInt(unitweardig);
		// }
		// if (weardig != null && !"".equals(weardig)) {
		// weardigits = Integer.parseInt(weardig);
		// }
		// cms.get(8).setFractionCount(unitweardigits);
		// cms.get(9).setFractionCount(weardigits);
		EmsEdiMergerClientLogic.transModifyMark(jTableBom);
		EmsEdiMergerClientLogic.transEmsSendState(jTableBom);
		return tableModelBom;
	}

	private void beginState() {
		if (DgEmsHeadH2k.this.isChange) { // 变更状态
			if (dataState == DataState.EDIT)
				DgEmsHeadH2k.this.setTitle("电子帐册申请变更维护");
			else
				DgEmsHeadH2k.this.setTitle("电子帐册申请变更浏览");
		} else if (DgEmsHeadH2k.this.isChange == false) { // 备案状态
			if (dataState == DataState.EDIT)
				DgEmsHeadH2k.this.setTitle("电子帐册申请备案维护");
			else
				DgEmsHeadH2k.this.setTitle("电子帐册申请备案浏览");
		}
	}

	private void fillWindow() {
		if (emsHeadH2k != null) {
			// 初始基本信息
			if (emsHeadH2k.getEmsType().equals("8")) {
				tfEmsTpye.setText("便捷通关帐册");
			}

			if (emsHeadH2k.getDeclareState().equals("1"))
				tfEmsState.setText("备案申请");
			else if (emsHeadH2k.getDeclareState().equals("2"))
				tfEmsState.setText("等待审批");
			else if (emsHeadH2k.getDeclareState().equals("3"))
				tfEmsState.setText("正在执行");
			if (emsHeadH2k.getDeclareType().equals("1"))
				tfDeclareType.setText("备案申请");
			else
				tfDeclareType.setText("备案变更");
			if (emsHeadH2k.getDeclareErType() != null) {
				if (emsHeadH2k.getDeclareErType().equals("1"))
					tfDeclareErType.setText("企业");
				else if (emsHeadH2k.getDeclareErType().equals("2"))
					tfDeclareErType.setText("代理公司");
				else if (emsHeadH2k.getDeclareErType().equals("2"))
					tfDeclareErType.setText("报关行");
			} else
				tfDeclareErType.setText("");
			tfCopEmsNo.setText(emsHeadH2k.getCopEmsNo());
			tfEmsNo.setText(emsHeadH2k.getEmsNo());
			tfSancEmsNo.setText(emsHeadH2k.getSancEmsNo());
			tfTradeCode.setText(emsHeadH2k.getTradeCode());
			tfTradeName.setText(emsHeadH2k.getTradeName());
			tfMachCode.setText(emsHeadH2k.getMachCode());
			tfMachName.setText(emsHeadH2k.getMachName());
			tfMachAbility.setText(doubleToStr(emsHeadH2k.getMachAbility()));
			tfMaxTurnMoney.setText(doubleToStr(emsHeadH2k.getMaxTurnMoney()));
			tfTel.setText(emsHeadH2k.getTel());
			tfAddress.setText(emsHeadH2k.getAddress());
			cbbBeginDate.setDate(emsHeadH2k.getBeginDate());
			cbbEndDate.setDate(emsHeadH2k.getEndDate());
			tfOutTradeCo.setText(emsHeadH2k.getOutTradeCo());
			tfIePort.setText(emsHeadH2k.getIePort());
			if (emsHeadH2k.getLevyKind() != null)
				tfLevyKind.setText(emsHeadH2k.getLevyKind().getName());
			else
				tfLevyKind.setText("");
			if (emsHeadH2k.getPayMode() != null)
				tfPayMode.setText(emsHeadH2k.getPayMode().getName());
			else
				tfPayMode.setText("");
			if (emsHeadH2k.getMachiningType() != null)
				tfMachiningType
						.setText(emsHeadH2k.getMachiningType().getName());
			else
				tfMachiningType.setText("");
			tfInputEr.setText(emsHeadH2k.getInputEr());
			tfEmsApprNo.setText(emsHeadH2k.getEmsApprNo());
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (emsHeadH2k.getDeclareDate() != null) {
				String getDeclareDate = bartDateFormat.format(emsHeadH2k
						.getDeclareDate());
				tfDeclareDate.setText(getDeclareDate);// 申报日期
			}
			if (emsHeadH2k.getNewApprDate() != null) {
				String getDeclareDate = bartDateFormat.format(emsHeadH2k
						.getNewApprDate());
				tfNewApprDate.setText(getDeclareDate);// 备案批准日期
			}
			if (emsHeadH2k.getChangeApprDate() != null) {
				String getDeclareDate = bartDateFormat.format(emsHeadH2k
						.getChangeApprDate());
				tfChangeApprDate.setText(getDeclareDate);// 变更批准日期
			}
			if (emsHeadH2k.getInputDate() != null) {
				String getInputDate = bartDateFormat.format(emsHeadH2k
						.getInputDate());
				tfInputDate.setText(getInputDate);// 录入日期
			}
			tfNote.setText(emsHeadH2k.getNote());
		}

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */

	private Double strToDouble(String value) { // 转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				// return new Double("0");
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return (Double) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			// return new Double("0");
			return null;
		}
	}

	private String doubleToStr(Double value) { // 转换doubleToStr 取数据
		try {
			if (value == null || value.doubleValue() == 0) {
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return getNumberFormatter().valueToString(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
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
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.setFloatable(false);
			jToolBar.add(getBtnInsert());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnWare());
			jToolBar.add(getBtnEditFactoryPrice());
			jToolBar.add(getJButton13());
			jToolBar.add(getBtnPrice());
			jToolBar.add(getBtnElse());
			jToolBar.add(getBtnBomExport());
			jToolBar.add(getBtnMaxVersion());
			jToolBar.add(getBtnEditDeclareState());
			jToolBar.add(getJButton18());
		}
		return jToolBar;
	}

	/**
	 * 
	 * This method initializes btnInsert
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton 石小凯修改
	 */
	private JButton getBtnInsert() { // 新增料件/成品
		if (btnInsert == null) {
			btnInsert = new JButton();
			btnInsert.setText("新增");
			btnInsert.setPreferredSize(new Dimension(60, 30));
			btnInsert.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 1)// 料件
					{
						if (checkIsSendState()) {
							return;
						}
						List list = null;
						if (CommonVars.getEmsFrom().equals("3")) { // 来自企业内部归并
							list = (List) CommonQuery.getInstance()
									.getMergerImgAfterFromMerger(false,
											emsEdiTrHead, emsHeadH2k);
						}
						// 来自归并关系备案
						else if (CommonVars.getEmsFrom().equals("2")) {
							list = (List) CommonQuery
									.getInstance()
									.getMergerImgAfter(false, emsHeadH2k, false);// 电子帐册料件
						}
						// 来自归并正在执行
						else {
							list = (List) CommonQuery.getInstance()
									.getMergerImgAfter(false, emsHeadH2k, true);// 电子帐册料件
						}
						if (list == null || list.isEmpty())
							return;
						new newImg(list).start();
					} else if (jTabbedPane.getSelectedIndex() == 2) // 成品
					{
						if (checkIsSendState()) {
							return;
						}
						List list = null;
						if (CommonVars.getEmsFrom().equals("3")) { // 来自企业内部归并
							list = (List) CommonQuery.getInstance()
									.getMergerExgAfterFromMerger(false,
											emsEdiTrHead, emsHeadH2k);
						}
						// 来自归并关系备案
						else if (CommonVars.getEmsFrom().equals("2")) {
							list = (List) CommonQuery
									.getInstance()
									.getMergerExgAfter(false, emsHeadH2k, false); // 电子帐册成品
						}
						// 来自归并正在执行
						else {
							list = (List) CommonQuery.getInstance()
									.getMergerExgAfter(false, emsHeadH2k, true); // 电子帐册成品
						}
						if (list == null || list.isEmpty())
							return;
						new newExg(list).start();
					}

				}
			});

		}
		return btnInsert;
	}

	/**
	 * 所选取的新增料件加入料件表中
	 */
	class newImg extends Thread {
		List list = null;

		public newImg(List list) {
			this.list = list;
		}

		public void run() {
			try {
				CommonProgress.showProgressDialog(DgEmsHeadH2k.this);
				CommonProgress.setMessage("系统正在新增资料，请稍后...");
				EmsHeadH2kImg emsHeadH2kImg = null;
				for (int i = 0; i < list.size(); i++) {
					emsHeadH2kImg = (EmsHeadH2kImg) list.get(i);
					emsHeadH2kImg.setEmsHeadH2k(emsHeadH2k);
					emsHeadH2kImg.setModifyMark(ModifyMarkState.ADDED);
					if (isEmsH2kSendSign != null
							&& "1".equals(isEmsH2kSendSign)) {
						emsHeadH2kImg.setSendState(Integer
								.valueOf(SendState.WAIT_SEND));
					}
					emsHeadH2kImg.setModifyTimes(emsHeadH2k.getModifyTimes());
					emsHeadH2kImg.setChangeDate(new Date());// 变更日期
					emsHeadH2kImg.setCompany(CommonVars.getCurrUser()
							.getCompany());
					emsHeadH2kImg = manualdeclearAction.saveEmsHeadH2kImg(
							new Request(CommonVars.getCurrUser()),
							emsHeadH2kImg);
					tableModelImg.addRow(emsHeadH2kImg);
					EmsEdiMergerClientLogic.transModifyMark(jTableImg);
					EmsEdiMergerClientLogic.transEmsSendState(jTableImg);
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
						"新增失败：！" + e.getMessage(), "提示", 2);
			} finally {
				setState();
			}
		}
	}

	/**
	 * 所选取的新增成品加入成品表中
	 */
	class newExg extends Thread {
		List list = null;

		public newExg(List list) {
			this.list = list;
		}

		public void run() {

			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					try {
						CommonProgress.showProgressDialog(DgEmsHeadH2k.this);
						CommonProgress.setMessage("系统正在新增资料，请稍后...");
						EmsHeadH2kExg emsHeadH2kExg = null;
						for (int i = 0; i < list.size(); i++) {
							emsHeadH2kExg = (EmsHeadH2kExg) list.get(i);
							emsHeadH2kExg.setEmsHeadH2k(emsHeadH2k);
							emsHeadH2kExg.setModifyMark(ModifyMarkState.ADDED);
							if (isEmsH2kSendSign != null
									&& "1".equals(isEmsH2kSendSign)) {
								emsHeadH2kExg.setSendState(Integer
										.valueOf(SendState.WAIT_SEND));
							}
							emsHeadH2kExg.setModifyTimes(emsHeadH2k
									.getModifyTimes());
							emsHeadH2kExg.setChangeDate(new Date());// 变更次数
							emsHeadH2kExg.setCompany(CommonVars.getCurrUser()
									.getCompany());
							emsHeadH2kExg = manualdeclearAction
									.saveEmsHeadH2kExg(
											new Request(CommonVars
													.getCurrUser()),
											emsHeadH2kExg);
							tableModelExg.addRow(emsHeadH2kExg);
							EmsEdiMergerClientLogic.transModifyMark(jTableExg);
							EmsEdiMergerClientLogic
									.transEmsSendState(jTableExg);
						}
						CommonProgress.closeProgressDialog();
					} catch (Exception e) {
						CommonProgress.closeProgressDialog();
						JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
								"新增失败：！" + e.getMessage(), "提示", 2);
					} finally {
						setState();
					}
				}
			});
		}
	}

	/**
	 * 
	 * This method initializes btnEdit
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {

			btnEdit = new JButton();

			btnEdit.setText("修改");

			btnEdit.setPreferredSize(new Dimension(60, 30));

			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					// 基础资料
					if (jTabbedPane.getSelectedIndex() == 0) {

						dataState = DataState.EDIT;

						setState();

						// 料件表 & 成品及单耗
					} else {

						// 检查是否申报状态
						if (checkIsSendState()) {

							return;

						}

						//
						DgEmsHeadH2kImgExg dgEmsHeadH2kImgExg = new DgEmsHeadH2kImgExg();

						// 料件
						if (jTabbedPane.getSelectedIndex() == 1) {

							if (tableModelImg.getCurrentRow() == null) {

								JOptionPane.showMessageDialog(
										DgEmsHeadH2k.this, "请选择你要修改的料件资料",
										"确认", 1);

								return;

							}

							if ((!((EmsHeadH2kImg) tableModelImg
									.getCurrentRow()).getModifyMark().equals(
									ModifyMarkState.ADDED))
									&& emsHeadH2k.getDeclareType().equals("3")) {

								JOptionPane
										.showMessageDialog(DgEmsHeadH2k.this,
												"该资料不可以修改！", "确认", 1);
								return;

							}

							dgEmsHeadH2kImgExg.setImg(true);

							dgEmsHeadH2kImgExg.setTableModel(tableModelImg);

							// 成品
						} else if (jTabbedPane.getSelectedIndex() == 2) {

							if (tableModelExg.getCurrentRow() == null) {

								JOptionPane.showMessageDialog(
										DgEmsHeadH2k.this, "请选择你要修改的成品资料",
										"确认", 1);

								return;

							}

							if ((!((EmsHeadH2kExg) tableModelExg
									.getCurrentRow()).getModifyMark().equals(
									ModifyMarkState.ADDED))
									&& emsHeadH2k.getDeclareType().equals("3")) {

								JOptionPane
										.showMessageDialog(DgEmsHeadH2k.this,
												"该资料不可以修改！", "确认", 1);
								return;

							}

							dgEmsHeadH2kImgExg.setImg(false);

							dgEmsHeadH2kImgExg.setTableModel(tableModelExg);
						}
						dgEmsHeadH2kImgExg.setEmsHeadH2k(emsHeadH2k);
						dgEmsHeadH2kImgExg.setVisible(true);

					}
				}
			});

		}
		return btnEdit;
	}

	/**
	 * 
	 * This method initializes btnDelete
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnDelete() { // 删除料件/成品
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setPreferredSize(new Dimension(60, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					switch (getJTabbedPane().getSelectedIndex()) {
					case 0:
						break;
					case 1:
						if (checkIsSendState()) {
							return;
						}
						if (tableModelImg.getCurrentRows() == null) {
							JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
									"请选择要删除的记录", "删除提示", 1);
							return;
						}

						/*
						 * if ((!((EmsHeadH2kImg)
						 * tableModelImg.getCurrentRow()).
						 * getModifyMark().equals(ModifyMarkState.ADDED)) &&
						 * emsHeadH2k.getDeclareType().equals("3")){
						 * JOptionPane.showMessageDialog( DgEmsHeadH2k.this,
						 * "该资料不可以删除！", "确认", 1); return; }
						 */
						if (JOptionPane.showConfirmDialog(DgEmsHeadH2k.this,
								"您确认要删除所选料件吗？", "删除提示", 0) == 0) {
							List ls = tableModelImg.getCurrentRows();
							for (int i = 0; i < ls.size(); i++) {
								EmsHeadH2kImg currRowImg = (EmsHeadH2kImg) ls
										.get(i);
								int type = manualdeclearAction
										.checkToEmsH2kImgBom(new Request(
												CommonVars.getCurrUser()),
												currRowImg);
								if (type == 1 || type == 2) {
									String info = "";
									if (type == 1)
										info = "该料件已经在电子账册成品单耗里存在，不能删除!!";
									if (type == 2)
										info = "该料件已经在报关单中使用，不能删除!!";
									JOptionPane.showMessageDialog(
											DgEmsHeadH2k.this, info, "警告!!",
											JOptionPane.INFORMATION_MESSAGE);
									return;
								}
								// 为新增状态是否删除单耗
								// if(type==3){
								// if
								// (JOptionPane.showConfirmDialog(DgEmsHeadH2k.this,
								// "要一并删除成品单耗中的此料件吗？", "删除提示", 0) == 0) {
								//
								// }
								// }

								if (emsHeadH2k.getDeclareType().equals(
										DelcareType.APPLICATION)
										|| currRowImg.getModifyMark().equals(
												ModifyMarkState.ADDED)) {
									manualdeclearAction.deleteEmsHeadH2kImg(
											new Request(CommonVars
													.getCurrUser()), currRowImg);
									tableModelImg.deleteRow(currRowImg);
								} else {
									currRowImg
											.setModifyMark(ModifyMarkState.DELETED);
									if (isEmsH2kSendSign != null
											&& "1".equals(isEmsH2kSendSign)) {
										currRowImg.setSendState(1);// 准备申报
									}
									currRowImg.setModifyTimes(emsHeadH2k
											.getModifyTimes());// 变更次数
									currRowImg = manualdeclearAction
											.saveEmsHeadH2kImg(new Request(
													CommonVars.getCurrUser()),
													currRowImg);
									tableModelImg.updateRow(currRowImg);
								}
							}
						}
						break;
					case 2:
						if (checkIsSendState()) {
							return;
						}
						if (tableModelExg.getCurrentRows() == null) {
							JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
									"请选择要删除的记录", "删除提示", 1);
							return;
						}
						/*
						 * if ((!((EmsHeadH2kExg)
						 * tableModelExg.getCurrentRow()).
						 * getModifyMark().equals(ModifyMarkState.ADDED)) &&
						 * emsHeadH2k.getDeclareType().equals("3")){
						 * JOptionPane.showMessageDialog( DgEmsHeadH2k.this,
						 * "该资料不可以删除！", "确认", 1); return; }
						 */
						// 2010-10-22 hcl add
						if (manualdeclearAction.checkDeleteEmsHeadH2kExg(
								new Request(CommonVars.getCurrUser()),
								tableModelExg.getCurrentRows()).size() > 0) {
							JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
									"该成品已经有做报关单，不能删除!!", "警告!!",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (JOptionPane.showConfirmDialog(DgEmsHeadH2k.this,
								"您确认要删除所选成品吗？\n注意：其单耗将一并被删除！", "删除提示", 0) == 0) {
							List ls = tableModelExg.getCurrentRows();
							for (int j = 0; j < ls.size(); j++) {
								EmsHeadH2kExg currRowExg = (EmsHeadH2kExg) ls
										.get(j);
								if (emsHeadH2k.getDeclareType().equals(
										DelcareType.APPLICATION)
										|| currRowExg.getModifyMark().equals(
												ModifyMarkState.ADDED)) {
									manualdeclearAction.deleteVersionAndBom(
											new Request(CommonVars
													.getCurrUser()), currRowExg);
									manualdeclearAction.deleteEmsHeadH2kExg(
											new Request(CommonVars
													.getCurrUser()), currRowExg);
									tableModelExg.deleteRow(currRowExg);
									tableModelVersion.getList().clear();
									tableModelVersion.setList(new Vector());
									tableModelBom.getList().clear();
									tableModelBom.setList(new Vector());
								} else {
									List listBom = manualdeclearAction
											.findBomByExg(new Request(
													CommonVars.getCurrUser()),
													currRowExg);
									if (listBom != null && !listBom.isEmpty()) {
										for (int i = 0; i < listBom.size(); i++) {
											EmsHeadH2kBom emsBom = (EmsHeadH2kBom) listBom
													.get(i);
											emsBom.setModifyMark(ModifyMarkState.DELETED);
											if (isEmsH2kSendSign != null
													&& "1".equals(isEmsH2kSendSign)) {
												emsBom.setSendState(1);// 准备申报
											}
											emsBom.setModifyTimes(emsHeadH2k
													.getModifyTimes());// 变更次数
											emsBom = manualdeclearAction.saveEmsHeadH2kBom(
													new Request(CommonVars
															.getCurrUser()),
													emsBom);
											tableModelBom.updateRow(emsBom);
										}
									}
									currRowExg
											.setModifyMark(ModifyMarkState.DELETED);
									if (isEmsH2kSendSign != null
											&& "1".equals(isEmsH2kSendSign)) {
										currRowExg.setSendState(1);// 准备申报
									}
									currRowExg.setModifyTimes(emsHeadH2k
											.getModifyTimes());// 变更次数
									currRowExg = manualdeclearAction
											.saveEmsHeadH2kExg(new Request(
													CommonVars.getCurrUser()),
													currRowExg);
									tableModelExg.updateRow(currRowExg);
								}
							}
						}
						break;
					}
					// changHead(emsHeadH2k);
					setState();
				}
			});

		}
		return btnDelete;
	}

	/**
	 * 
	 * This method initializes btnSave
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.setPreferredSize(new Dimension(60, 30));
			btnSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					saveHead();
				}
			});

		}
		return btnSave;
	}

	/**
	 * 保存表头信息
	 */
	private void saveHead() {
		if (checkIsSendState()) {
			return;
		}
		// 保存
		if (tfMachAbility.getText().equals(""))
			tfMachAbility.setText("0");
		if (tfMaxTurnMoney.getText().equals(""))
			tfMaxTurnMoney.setText("0");
		Double yearCan = strToDouble(tfMachAbility.getText());
		Double turnMoney = strToDouble(tfMaxTurnMoney.getText());
		if (turnMoney.doubleValue() > (yearCan.doubleValue() / 2)) {
			JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
					"最大周转金额不能大于年加工生产能力的一半！", "提示！", 1);
			return;
		}
		fillEmsHeadH2k(emsHeadH2k);
		emsHeadH2k = manualdeclearAction.saveEmsHeadH2k(
				new Request(CommonVars.getCurrUser()), emsHeadH2k);
		tableModel.updateRow(emsHeadH2k);
		dataState = DataState.BROWSE;
		setState();
	}

	/**
	 * 表头信息填充进账册实体用于
	 * 
	 * @param emsHeadH2k
	 */
	private void fillEmsHeadH2k(EmsHeadH2k emsHeadH2k) {
		EmsHeadH2k emsHeadOld = new EmsHeadH2k();
		emsHeadOld = (EmsHeadH2k) emsHeadH2k.clone();
		emsHeadH2k.setTel(tfTel.getText());
		emsHeadH2k.setOutTradeCo(tfOutTradeCo.getText());
		emsHeadH2k.setAddress(tfAddress.getText());
		emsHeadH2k.setIePort(tfIePort.getText());
		emsHeadH2k.setTradeName(tfTradeName.getText());
		emsHeadH2k.setMachName(tfMachName.getText());
		emsHeadH2k.setLevyKind(levyKind);
		emsHeadH2k.setPayMode(payMode);
		emsHeadH2k.setMachiningType(machiningType);
		emsHeadH2k.setEndDate(cbbEndDate.getDate());
		emsHeadH2k.setMaxTurnMoney(strToDouble(tfMaxTurnMoney.getText()));
		emsHeadH2k.setNote(tfNote.getText());
		emsHeadH2k.setEmsNo(tfEmsNo.getText());
		emsHeadH2k.setCopEmsNo(tfCopEmsNo.getText());
		emsHeadH2k.setSancEmsNo(tfSancEmsNo.getText());
		if (!emsHeadH2k.fullEquals(emsHeadOld)
				&& emsHeadH2k.getDeclareType().equals(DelcareType.MODIFY)) {
			emsHeadH2k.setModifyMark(ModifyMarkState.MODIFIED); // 打上已修改标志，
		}
	}

	/**
	 * 
	 * This method initializes btnElse
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnElse() {
		if (btnElse == null) {
			btnElse = new JButton();
			btnElse.setText("其他");
			btnElse.setPreferredSize(new Dimension(60, 30));
			btnElse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 1) {// 料件
						if (tableModelImg.getCurrentRows() == null
								|| tableModelImg.getCurrentRows().size() == 0) {
							JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
									"没有选中数据！", "提示！",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						MyModifJPopupMenu mym = new MyModifJPopupMenu(
								"更新(与内部归并同步)") {
							List list = tableModelImg.getCurrentRows();

							void doOther() {
								if (JOptionPane.showConfirmDialog(
										DgEmsHeadH2k.this, "您确认要与内部归并同步吗?",
										"提示", 0) != 0) {
									return;
								}
								if (tableModelImg == null)
									return;
								if (tableModelImg.getCurrentRows() == null)
									return;
								List ls = tableModelImg.getCurrentRows();
								ls = manualdeclearAction.synchroEmsEdiImg(
										new Request(CommonVars.getCurrUser()),
										ls);
								tableModelImg.updateRows(ls);
							}

							void changeState(String state) {
								if (JOptionPane.showConfirmDialog(
										DgEmsHeadH2k.this, "您确认要修改标记吗?", "提示",
										JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
									return;
								}
								// 修改标记权限
								for (int i = 0; i < list.size(); i++) {
									EmsHeadH2kImg obj = (EmsHeadH2kImg) list
											.get(i);
									obj = manualdeclearAction.changeEMSState(
											new Request(CommonVars
													.getCurrUser()), obj, state);
									tableModelImg.updateRow(obj);
								}
							}
						};
						mym.show(btnElse, 0, btnElse.getHeight());
					} else if (jTabbedPane.getSelectedIndex() == 2) {
						if (tableModelExg.getCurrentRows() == null
								|| tableModelExg.getCurrentRows().size() == 0) {
							JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
									"没有选中数据！", "提示！",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						MyModifJPopupMenu mym = new MyModifJPopupMenu(
								"更新(与内部归并同步)") {
							List list = tableModelExg.getCurrentRows();

							void doOther() {
								if (JOptionPane.showConfirmDialog(
										DgEmsHeadH2k.this, "您确认要与内部归并同步吗?",
										"提示", 0) != 0) {
									return;
								}
								List ls = tableModelExg.getCurrentRows();
								ls = manualdeclearAction.synchroEmsEdiExg(
										new Request(CommonVars.getCurrUser()),
										ls);
								tableModelExg.updateRows(ls);
							}

							void changeState(String state) {
								if (JOptionPane.showConfirmDialog(
										DgEmsHeadH2k.this, "您确认要修改标记吗?", "提示",
										JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
									return;
								}
								// 修改标记权限
								for (int i = 0; i < list.size(); i++) {
									EmsHeadH2kExg obj = (EmsHeadH2kExg) list
											.get(i);
									obj = manualdeclearAction.changeEMSState(
											new Request(CommonVars
													.getCurrUser()), obj, state);
									tableModelExg.updateRow(obj);
								}
							}
						};
						mym.show(btnElse, 0, btnElse.getHeight());
						// }
					}
				}
			});

			btnElse.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					getJPopupMenu3().show(btnElse, 0, btnElse.getHeight());
					getJPopupMenu3().show(btnElse, 0, btnElse.getHeight());
				}
			});

		}
		return btnElse;
	}

	/**
	 * 
	 * 分类容器面板的控制
	 * 
	 * @return javax.swing.JTabbedPane
	 * 
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("基本资料", null, getJpHead(), null);
			jTabbedPane.addTab("料件表", null, getJpImg(), null);
			jTabbedPane.addTab("成品及单耗", null, getJpExg(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {

						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (jTabbedPane.getSelectedIndex() == 0) {
								showHeadData();
							} else if (jTabbedPane.getSelectedIndex() == 1) {
								if (dataState == DataState.EDIT)
									saveHead();
								showImgData();
							} else if (jTabbedPane.getSelectedIndex() == 2) {
								if (dataState == DataState.EDIT)
									saveHead();
								showExgBomData();
							}
							setState();
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
			jpExg.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
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
			jpImg.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jpImg.add(getJToolBar2(), BorderLayout.NORTH);
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
			jpHead.add(getJPanel5(), null);
		}
		return jpHead;
	}

	/**
	 * 
	 * This method initializes tfEmsTpye
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfEmsTpye() {
		if (tfEmsTpye == null) {
			tfEmsTpye = new JTextField();
			tfEmsTpye.setEditable(false);
			tfEmsTpye.setBounds(new Rectangle(131, 23, 140, 25));
		}
		return tfEmsTpye;
	}

	/**
	 * 
	 * This method initializes tfEmsState
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfEmsState() {
		if (tfEmsState == null) {
			tfEmsState = new JTextField();
			tfEmsState.setEditable(false);
			tfEmsState.setBounds(new Rectangle(375, 23, 140, 25));
		}
		return tfEmsState;
	}

	/**
	 * 
	 * This method initializes tfDeclareType
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfDeclareType() {
		if (tfDeclareType == null) {
			tfDeclareType = new JTextField();
			tfDeclareType.setEditable(false);
			tfDeclareType.setBounds(new Rectangle(621, 23, 140, 25));
		}
		return tfDeclareType;
	}

	/**
	 * 
	 * This method initializes tfCopEmsNo
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfCopEmsNo() {
		if (tfCopEmsNo == null) {
			tfCopEmsNo = new JTextField();
			tfCopEmsNo.setBounds(new Rectangle(131, 54, 140, 25));
		}
		return tfCopEmsNo;
	}

	/**
	 * 
	 * This method initializes tfEmsNo
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(new Rectangle(375, 54, 140, 25));
		}
		return tfEmsNo;
	}

	/**
	 * 
	 * This method initializes tfSancEmsNo
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfSancEmsNo() {
		if (tfSancEmsNo == null) {
			tfSancEmsNo = new JTextField();
			tfSancEmsNo.setBounds(new Rectangle(621, 54, 141, 25));
		}
		return tfSancEmsNo;
	}

	/**
	 * 
	 * This method initializes tfTradeCode
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
			tfTradeCode.setBounds(new Rectangle(131, 84, 140, 25));
		}
		return tfTradeCode;
	}

	/**
	 * 
	 * This method initializes tfTradeName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfTradeName() {
		if (tfTradeName == null) {
			tfTradeName = new JTextField();
			tfTradeName.setBounds(new Rectangle(376, 84, 386, 25));
		}
		return tfTradeName;
	}

	/**
	 * 
	 * This method initializes tfMachCode
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
			tfMachCode.setBounds(new Rectangle(131, 113, 140, 25));
		}
		return tfMachCode;
	}

	/**
	 * 
	 * This method initializes tfMachName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfMachName() {
		if (tfMachName == null) {
			tfMachName = new JTextField();
			tfMachName.setBounds(new Rectangle(376, 113, 385, 25));
		}
		return tfMachName;
	}

	/**
	 * 
	 * This method initializes tfTel
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfTel() {
		if (tfTel == null) {
			tfTel = new JTextField();
			tfTel.setBounds(new Rectangle(131, 174, 140, 25));
		}
		return tfTel;
	}

	/**
	 * 
	 * This method initializes tfAddress
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfAddress() {
		if (tfAddress == null) {
			tfAddress = new JTextField();
			tfAddress.setBounds(new Rectangle(376, 174, 385, 25));
		}
		return tfAddress;
	}

	/**
	 * 
	 * This method initializes tfDeclareErType
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfDeclareErType() {
		if (tfDeclareErType == null) {
			tfDeclareErType = new JTextField();
			tfDeclareErType.setBounds(131, 144, 140, 25);
			tfDeclareErType.setEditable(false);
		}
		return tfDeclareErType;
	}

	/**
	 * 
	 * This method initializes cbbBeginDate
	 * 
	 * 
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 * 
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setEnabled(false);
			cbbBeginDate.setBounds(131, 202, 140, 25);
		}
		return cbbBeginDate;
	}

	/**
	 * 
	 * This method initializes cbbEndDate
	 * 
	 * 
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 * 
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(378, 202, 140, 25);
		}
		return cbbEndDate;
	}

	/**
	 * 
	 * This method initializes tfInputEr
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfInputEr() {
		if (tfInputEr == null) {
			tfInputEr = new JTextField();
			tfInputEr.setBounds(131, 293, 140, 25);
			tfInputEr.setEditable(false);
		}
		return tfInputEr;
	}

	/**
	 * 
	 * This method initializes tfInputDate
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfInputDate() {
		if (tfInputDate == null) {
			tfInputDate = new JTextField();
			tfInputDate.setBounds(379, 293, 140, 25);
			tfInputDate.setEditable(false);
		}
		return tfInputDate;
	}

	/**
	 * 
	 * This method initializes tfNewApprDate
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfNewApprDate() {
		if (tfNewApprDate == null) {
			tfNewApprDate = new JTextField();
			tfNewApprDate.setBounds(379, 323, 140, 25);
			tfNewApprDate.setEditable(false);
		}
		return tfNewApprDate;
	}

	/**
	 * 
	 * This method initializes tfDeclareDate
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfDeclareDate() {
		if (tfDeclareDate == null) {
			tfDeclareDate = new JTextField();
			tfDeclareDate.setBounds(131, 323, 140, 25);
			tfDeclareDate.setEditable(false);
		}
		return tfDeclareDate;
	}

	/**
	 * 
	 * This method initializes tfChangeApprDate
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfChangeApprDate() {
		if (tfChangeApprDate == null) {
			tfChangeApprDate = new JTextField();
			tfChangeApprDate.setBounds(621, 323, 140, 25);
			tfChangeApprDate.setEditable(false);
		}
		return tfChangeApprDate;
	}

	/**
	 * 
	 * This method initializes tfNote
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextField();
			tfNote.setBounds(131, 359, 633, 25);
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
	 * This method initializes tfMachAbility
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getTfMachAbility() {
		if (tfMachAbility == null) {
			tfMachAbility = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfMachAbility.setBounds(377, 144, 101, 25);
			tfMachAbility.setFormatterFactory(getDefaultFormatterFactory());
			tfMachAbility.setEditable(false);
		}
		return tfMachAbility;
	}

	/**
	 * 
	 * This method initializes tfMaxTurnMoney
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getTfMaxTurnMoney() {
		if (tfMaxTurnMoney == null) {
			tfMaxTurnMoney = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfMaxTurnMoney.setBounds(621, 144, 101, 25);
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
	 * @return Returns the isEdit.
	 */
	public boolean isEdit() {
		return isEdit;
	}

	/**
	 * @param isEdit
	 *            The isEdit to set.
	 */
	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	/**
	 * 
	 * This method initializes tfOutTradeCo
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfOutTradeCo() {
		if (tfOutTradeCo == null) {
			tfOutTradeCo = new JTextField();
			tfOutTradeCo.setBounds(621, 202, 140, 25);
		}
		return tfOutTradeCo;
	}

	/**
	 * 
	 * This method initializes tfIePort
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfIePort() {
		if (tfIePort == null) {
			tfIePort = new JTextField();
			tfIePort.setBounds(131, 234, 607, 25);
			// jTextField14.setEditable(false);
		}
		return tfIePort;
	}

	/**
	 * 
	 * This method initializes btnIePort
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnIePort() {
		if (btnIePort == null) {
			btnIePort = new JButton();
			btnIePort.setBounds(737, 234, 24, 25);
			btnIePort.setText("..."); // 进出口岸
			btnIePort.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					Customs c = (Customs) CommonQuery.getInstance()
							.getCustoms();
					if (c != null) {
						if (tfIePort.getText().equals(""))
							getTfIePort().setText(c.getCode());
						else
							getTfIePort()
									.setText(
											getTfIePort().getText() + "/"
													+ c.getCode());
					}
				}
			});
		}
		return btnIePort;
	}

	/**
	 * 
	 * This method initializes tfLevyKind
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfLevyKind() {
		if (tfLevyKind == null) {
			tfLevyKind = new JTextField();
			tfLevyKind.setEditable(false);
			tfLevyKind.setBounds(131, 263, 119, 25);
		}
		return tfLevyKind;
	}

	/**
	 * 
	 * This method initializes btnLevyKind
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnLevyKind() {
		if (btnLevyKind == null) {
			btnLevyKind = new JButton();
			btnLevyKind.setBounds(251, 264, 21, 25);
			btnLevyKind.setText("...");
			btnLevyKind.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) { // 征免性质

					LevyKind c = (LevyKind) CommonQuery.getInstance()
							.getLevyKind();
					if (c != null) {
						levyKind = c;
						getTfLevyKind().setText(c.getName());
					}

				}
			});

		}
		return btnLevyKind;
	}

	/**
	 * 
	 * This method initializes tfMachiningType
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfMachiningType() {
		if (tfMachiningType == null) {
			tfMachiningType = new JTextField();
			tfMachiningType.setBounds(621, 263, 118, 25);
			tfMachiningType.setEditable(false);
		}
		return tfMachiningType;
	}

	/**
	 * 
	 * This method initializes btnMachiningType
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnMachiningType() {
		if (btnMachiningType == null) {
			btnMachiningType = new JButton();
			btnMachiningType.setBounds(740, 263, 21, 25);
			btnMachiningType.setText("...");
			btnMachiningType
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) { // 加工种类

							MachiningType c = (MachiningType) CommonQuery
									.getInstance().getMachiningType();
							if (c != null) {
								machiningType = c;
								getTfMachiningType().setText(c.getName());
							}
						}
					});

		}
		return btnMachiningType;
	}

	/**
	 * 
	 * This method initializes tfEmsApprNo
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfEmsApprNo() {
		if (tfEmsApprNo == null) {
			tfEmsApprNo = new JTextField();
			tfEmsApprNo.setBounds(621, 293, 140, 25);
			tfEmsApprNo.setEditable(false);
		}
		return tfEmsApprNo;
	}

	/**
	 * 
	 * This method initializes tfPayMode
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfPayMode() {
		if (tfPayMode == null) {
			tfPayMode = new JTextField();
			tfPayMode.setEditable(false);
			tfPayMode.setBounds(378, 263, 119, 25);
		}
		return tfPayMode;
	}

	/**
	 * 
	 * This method initializes btnPayMode
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnPayMode() {
		if (btnPayMode == null) {
			btnPayMode = new JButton();
			btnPayMode.setBounds(497, 264, 23, 25);
			btnPayMode.setText("...");
			btnPayMode.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					PayMode c = (PayMode) CommonQuery.getInstance()
							.getpaymode();
					if (c != null) {
						payMode = c;
						getTfPayMode().setText(c.getName());
					}

				}
			});

		}
		return btnPayMode;
	}

	/**
	 * 
	 * This method initializes jTableImg
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTableImg() {
		if (jTableImg == null) {
			jTableImg = new JTable();
			jTableImg.setRowHeight(25);

		}
		return jTableImg;
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
			jScrollPane.setViewportView(getJTableImg());
		}
		return jScrollPane;
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
			jSplitPane.setDividerSize(4);
			jSplitPane.setDividerLocation(200);
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
			jPanel.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
			jPanel.add(getJToolBar3(), BorderLayout.NORTH);
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
					null, "成品单耗",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel1.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJSplitPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jTableExg
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTableExg() {
		if (jTableExg == null) {
			jTableExg = new JTable();
			jTableExg.setRowHeight(25);
			jTableExg.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModelExg == null)
								return;
							if (tableModelExg.getCurrentRow() == null)
								return;
							emsHeadH2kExg = (EmsHeadH2kExg) tableModelExg
									.getCurrentRow();
							List dataSourceVersion = null;
							// 得到版本号
							dataSourceVersion = manualdeclearAction
									.findEmsHeadH2kVersion(new Request(
											CommonVars.getCurrUser()),
											emsHeadH2kExg);
							if (dataSourceVersion != null
									&& !dataSourceVersion.isEmpty()) {
								initTableVersion(dataSourceVersion);

								emsHeadH2kVersion = (EmsHeadH2kVersion) dataSourceVersion
										.get(0); // 第一个版本号
								Integer version = emsHeadH2kVersion
										.getVersion();
								dataSourceBom = manualdeclearAction
										.findEmsHeadH2kBom(new Request(
												CommonVars.getCurrUser()), // 得到BOM
												emsHeadH2kVersion);
								if (dataSourceBom != null
										&& !dataSourceBom.isEmpty()) {
									initTableBom(dataSourceBom);
									EmsEdiMergerClientLogic
											.transEmsSendState(jTableBom);
								} else
									initTableBom(new Vector());
							} else {
								initTableVersion(new Vector());
								initTableBom(new Vector());
							}
							setState();
						}
					});
		}
		return jTableExg;
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
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTableExg());
		}
		return jScrollPane1;
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
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar1 = new JToolBar();
			jToolBar1.setFloatable(false);
			jToolBar1.setLayout(f);
			jToolBar1.add(getJButton7());
			jToolBar1.add(getJButton8());
			jToolBar1.add(getJButton10());
			jToolBar1.add(getBtnAddBom());
			jToolBar1.add(getBtnEditBom());
			jToolBar1.add(getJButton6());
			jToolBar1.add(getJButton12());
			jToolBar1.add(getJButton19());
			jToolBar1.add(getJButton15());
			jToolBar1.add(getJRadioButton());

			jToolBar1.add(getJRadioButton1());

			jToolBar1.add(getJButtonSupplementBOM());
		}
		return jToolBar1;
	}

	/**
	 * 
	 * This method initializes btnAddBom
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnAddBom() { // 新增BOM
		if (btnAddBom == null) {
			btnAddBom = new JButton();
			btnAddBom.setText("新增子件");
			btnAddBom.setVisible(false);
			btnAddBom.setPreferredSize(new Dimension(60, 30));
			btnAddBom.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (checkIsSendState()) {
					// return;
					// }
					// if (DgEmsHeadH2k.this.jRadioButton.isSelected()) {
					// fromEmsEdiMergerBeforeBom();// 来自归并关系BOM
					// } else if (DgEmsHeadH2k.this.jRadioButton1.isSelected())
					// {
					// fromEmsHeadImg();// 来自帐册料件
					// }
				}
			});

		}
		return btnAddBom;
	}

	/**
	 * 
	 * This method initializes btnEditBom
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
			btnEditBom.setPreferredSize(new Dimension(70, 30));
			btnEditBom.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkIsSendState()) {
						return;
					}
					if (tableModelBom.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
								"请选择你要修改的BOM资料", "确认", 2);
						return;
					}
					if ((!((EmsHeadH2kBom) tableModelBom.getCurrentRow())
							.getModifyMark().equals(ModifyMarkState.ADDED))
							&& emsHeadH2k.getDeclareType().equals("3")) {
						JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
								"该资料不可以修改！", "确认", 1);
						return;
					}
					EmsHeadH2kBom bom = (EmsHeadH2kBom) tableModelBom
							.getCurrentRow();
					if (bom.getEmsHeadH2kVersion().getEmsHeadH2kExg()
							.getModifyMark().equals(ModifyMarkState.DELETED)) {
						JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
								"成品已删除，不能修改BOM资料！", "确认", 2);
						return;
					}
					DgEmsHeadH2kBom dgemsBom = new DgEmsHeadH2kBom();
					dgemsBom.setEmsHeadH2k(emsHeadH2k);
					dgemsBom.setTableModel(tableModelBom);
					dgemsBom.setVisible(true);
				}
			});

		}
		return btnEditBom;
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
	private JButton getJButton6() { // 删除BOM
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setText("删除子件");
			jButton6.setPreferredSize(new Dimension(70, 30));
			jButton6.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkIsSendState()) {
						return;
					}

					if (tableModelBom.getCurrentRows() == null) {
						JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
								"请选择你要删除的BOM资料", "确认", 2);
						return;
					}
					// 2010-10-22 hcl add
					// if(!((EmsHeadH2kBom)tableModelBom.getCurrentRow()).getModifyMark().equals("3"))
					// if (manualdeclearAction.checkDeleteEmsHeadH2kExg(
					// new Request(CommonVars.getCurrUser()),
					// tableModelExg.getCurrentRows()).size() > 0) {
					// JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
					// "该成品已经有做报关单，不能删除!!", "警告!!",
					// JOptionPane.INFORMATION_MESSAGE);
					// return;
					// }

					/*
					 * if ((!((EmsHeadH2kBom)
					 * tableModelBom.getCurrentRow()).getModifyMark
					 * ().equals(ModifyMarkState.ADDED)) &&
					 * emsHeadH2k.getDeclareType().equals("3")){
					 * JOptionPane.showMessageDialog( DgEmsHeadH2k.this,
					 * "该资料不可以删除！", "确认", 1); return; }
					 */
					if (JOptionPane.showConfirmDialog(DgEmsHeadH2k.this,
							"您确认要删除选定的BOM料件吗?", "删除提示", 0) == 0) {
						List ls = tableModelBom.getCurrentRows();
						for (int j = 0; j < ls.size(); j++) {
							EmsHeadH2kBom currRow = (EmsHeadH2kBom) ls.get(j);
							if (emsHeadH2k.getDeclareType().equals(
									DelcareType.APPLICATION)
									|| currRow.getModifyMark().equals(
											ModifyMarkState.ADDED)) {
								manualdeclearAction.deleteEmsHeadH2kBom(
										new Request(CommonVars.getCurrUser()),
										currRow);
								tableModelBom.deleteRow(currRow);
							} else {
								currRow.setModifyMark(ModifyMarkState.DELETED);
								if (isEmsH2kSendSign != null
										&& "1".equals(isEmsH2kSendSign)) {
									currRow.setSendState(1);// 准备申报
								}
								currRow.setModifyTimes(emsHeadH2k
										.getModifyTimes());// 变更次数
								currRow = manualdeclearAction
										.saveEmsHeadH2kBom(new Request(
												CommonVars.getCurrUser()),
												currRow);
								tableModelBom.updateRow(currRow);
							}
						}
					}
					setState();
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
			jButton7.setText("新增");
			jButton7.setPreferredSize(new Dimension(70, 30));
			jButton7.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) { // 新增版本号
					getJPopupMenu5().show(jButton7, 0, jButton7.getHeight());
				}
			});

		}
		return jButton7;
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
			jButton8.setText("复制到");
			jButton8.setPreferredSize(new Dimension(70, 30));
			jButton8.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkIsSendState()) {
						return;
					}
					if (tableModelVersion.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
								"请选中要复制的版本！", "提示！", 2);
						return;
					}
					emsHeadH2kVersion = (EmsHeadH2kVersion) tableModelVersion
							.getCurrentRow();
					if (emsHeadH2kVersion.getEmsHeadH2kExg().getModifyMark()
							.equals(ModifyMarkState.DELETED)) {
						JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
								"成品已删除，不能复制版本！", "提示！", 2);
						return;
					}
					List list = manualdeclearAction.findEmsHeadH2kBom(
							new Request(CommonVars.getCurrUser()), // 得到BOM
							emsHeadH2kVersion);
					if (!addVerision()) {
						return;
					}
					EmsHeadH2kVersion emsH2kVersion = (EmsHeadH2kVersion) tableModelVersion
							.getCurrentRow();
					for (int i = 0; i < list.size(); i++) {
						EmsHeadH2kBom emsH2kBom = (EmsHeadH2kBom) list.get(i);
						EmsHeadH2kBom emsBom = new EmsHeadH2kBom();
						emsBom.setEmsHeadH2kVersion(emsH2kVersion);
						emsBom.setSeqNum(emsH2kBom.getSeqNum());
						emsBom.setComplex(emsH2kBom.getComplex());
						emsBom.setName(emsH2kBom.getName());
						emsBom.setSpec(emsH2kBom.getSpec());
						emsBom.setUnit(emsH2kBom.getUnit());
						emsBom.setUnitWear(emsH2kBom.getUnitWear());
						emsBom.setWear(emsH2kBom.getWear());
						emsBom.setNote(emsH2kBom.getNote());
						if (isEmsH2kSendSign != null
								&& "1".equals(isEmsH2kSendSign)) {
							emsBom.setSendState(Integer
									.valueOf(SendState.WAIT_SEND));
						}
						emsBom.setChangeDate(new Date());
						emsBom.setModifyMark(ModifyMarkState.ADDED);
						emsBom.setModifyTimes(emsHeadH2k.getModifyTimes()); // 变更次数
						emsBom.setCompany(CommonVars.getCurrUser().getCompany());
						emsBom = manualdeclearAction.saveEmsHeadH2kBom(
								new Request(CommonVars.getCurrUser()), emsBom);
						tableModelBom.addRow(emsBom);
					}
				}
			});
		}
		return jButton8;
	}

	private boolean addVerision() {
		if (emsHeadH2kExg == null) {
			JOptionPane
					.showMessageDialog(DgEmsHeadH2k.this, "请选中成品！", "提示！", 2);
			return false;
		}
		if (emsHeadH2kExg.getModifyMark().equals(ModifyMarkState.DELETED)) {
			JOptionPane.showMessageDialog(DgEmsHeadH2k.this, "成品已删除，不能新增版本号！",
					"提示！", 2);
			return false;
		}
		DgVersion dgVersion = new DgVersion();
		dgVersion.setEmsHeadH2kExg(emsHeadH2kExg);
		dgVersion.setVisible(true);
		if (dgVersion.isOk()) {
			EmsHeadH2kVersion emsH2kVersion = new EmsHeadH2kVersion();
			Integer Version = dgVersion.getDValue();
			emsH2kVersion.setName("版本：" + String.valueOf(Version));
			emsH2kVersion.setVersion(Version);
			emsH2kVersion.setEmsHeadH2kExg(emsHeadH2kExg);
			emsH2kVersion.setCompany(CommonVars.getCurrUser().getCompany());
			emsH2kVersion = manualdeclearAction.saveEmsHeadH2kVersion(
					new Request(CommonVars.getCurrUser()), emsH2kVersion);
			tableModelVersion.addRow(emsH2kVersion);
			setState();
			return true;
		}
		return false;
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
			jButton10.setText("删除版本");
			jButton10.setPreferredSize(new Dimension(70, 30));
			jButton10.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkIsSendState()) {
						return;
					}
					if (tableModelExg.getCurrentRows().size() > 1) {
						JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
								"请选择单个成品进行操作!!", "警告!!",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					EmsHeadH2kExg exg = (EmsHeadH2kExg) tableModelExg
							.getCurrentRow();

					EmsHeadH2kVersion verion = (EmsHeadH2kVersion) tableModelVersion
							.getCurrentRow();
					System.out.println("exg.getSeqNum()=" + exg.getSeqNum());
					System.out.println("verion.getVersion()="
							+ verion.getVersion());
					List versionlist = manualdeclearAction
							.checkDeleteEmsHeadH2kExg(
									new Request(CommonVars.getCurrUser()),
									exg.getSeqNum(), verion.getVersion());
					if (versionlist.size() > 0) {
						JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
								"该成品已经有做报关单，不能删除!!", "警告!!",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					// 2010-10-22 hcl add
					// if (manualdeclearAction.checkDeleteEmsHeadH2kExg(
					// new Request(CommonVars.getCurrUser()),
					// tableModelExg.getCurrentRows()).size() > 0) {
					// JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
					// "该成品已经有做报关单，不能删除!!", "警告!!",
					// JOptionPane.INFORMATION_MESSAGE);
					// return;
					// }

					if (tableModelVersion.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
								"请选择要删除的版本！", "提示！", 2);
						return;
					}

					EmsHeadH2kVersion version = (EmsHeadH2kVersion) tableModelVersion
							.getCurrentRow();
					if (version.getEmsHeadH2kExg().getModifyMark()
							.equals(ModifyMarkState.DELETED)) {
						return;
					}
					if (JOptionPane.showConfirmDialog(DgEmsHeadH2k.this,
							"您确认要删除选定版本吗？\n注意：其BOM料件将一并删除?", "删除提示", 0) == 0) {
						EmsHeadH2kVersion currRow = (EmsHeadH2kVersion) tableModelVersion
								.getCurrentRow();
						List listBom = manualdeclearAction.findEmsHeadH2kBom(
								new Request(CommonVars.getCurrUser()), currRow);
						if (!isAddBom(listBom)) {
							JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
									"该版本不可以被删除！", "提示！", 2);
							return;
						}
						if (emsHeadH2k.getDeclareType().equals(
								DelcareType.APPLICATION)
								|| isAddBom(listBom)) {
							manualdeclearAction.deleteEmsHeadh2kBomByVersion(
									new Request(CommonVars.getCurrUser()),
									currRow);
							tableModelBom.getList().clear();
							tableModelBom.setList(new Vector());

							manualdeclearAction.deleteEmsHeadH2kVersion(
									new Request(CommonVars.getCurrUser()),
									currRow);
							tableModelVersion.deleteRow(currRow);
						} else {
							if (listBom != null && !listBom.isEmpty()) {
								for (int i = 0; i < listBom.size(); i++) {
									EmsHeadH2kBom emsBom = (EmsHeadH2kBom) listBom
											.get(i);
									emsBom.setModifyMark(ModifyMarkState.DELETED);
									if (isEmsH2kSendSign != null
											&& "1".equals(isEmsH2kSendSign)) {
										emsBom.setSendState(1);// 准备申报
									}
									emsBom.setModifyTimes(emsHeadH2k
											.getModifyTimes());// 变更次数
									emsBom = manualdeclearAction
											.saveEmsHeadH2kBom(new Request(
													CommonVars.getCurrUser()),
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
		return jButton10;
	}

	private boolean isAddBom(List listBom) {
		if (listBom != null && !listBom.isEmpty()) {
			for (int i = 0; i < listBom.size(); i++) {
				EmsHeadH2kBom emsBom = (EmsHeadH2kBom) listBom.get(i);
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
	 * 
	 * This method initializes jSplitPane1
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setDividerSize(3);
			jSplitPane1.setDividerLocation(100);
			jSplitPane1.setLeftComponent(getJPanel2());
			jSplitPane1.setRightComponent(getJPanel3());
		}
		return jSplitPane1;
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
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJScrollPane3(), java.awt.BorderLayout.CENTER);
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
			jPanel3.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * 
	 * This method initializes jTableBom
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTableBom() {
		if (jTableBom == null) {
			jTableBom = new JTable();

		}
		return jTableBom;
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
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTableBom());
		}
		return jScrollPane2;
	}

	/**
	 * 
	 * This method initializes jTableVersion
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTableVersion() {
		if (jTableVersion == null) {
			jTableVersion = new JTable();
			jTableVersion.setRowHeight(25);
		}
		jTableVersion.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						valuechange();
					}
				});
		return jTableVersion;
	}

	private void valuechange() {
		if (tableModelVersion == null)
			return;
		if (tableModelVersion.getCurrentRow() == null)
			return;
		emsHeadH2kVersion = (EmsHeadH2kVersion) tableModelVersion
				.getCurrentRow();
		Integer version = emsHeadH2kVersion.getVersion();
		dataSourceBom = manualdeclearAction.findEmsHeadH2kBom(new Request(
				CommonVars.getCurrUser()), // 得到BOM
				emsHeadH2kVersion);
		if (dataSourceBom != null && !dataSourceBom.isEmpty()) {
			initTableBom(dataSourceBom);

		} else {
			initTableBom(new Vector());
		}
		setState();
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
			jScrollPane3.setViewportView(getJTableVersion());
		}
		return jScrollPane3;
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
			// jPopupMenu.add(getJMenuItem());
			jPopupMenu.add(getJMenuItem1());
		}
		return jPopupMenu;
	}

	private void fromEmsHeadImg() {
		if (tableModelVersion.getCurrentRow() == null) {
			JOptionPane
					.showMessageDialog(DgEmsHeadH2k.this, "请输入版本号", "提示！", 2);
			return;
		}

		List list = (List) CommonQuery.getInstance().getImgFromEmsHeadImg( // 电子帐册料件
				false, emsHeadH2k, emsHeadH2kVersion);
		if (list == null || list.isEmpty())
			return;
		new getBomFromEmsHeadImg(list).start();

	}

	class getBomFromEmsHeadImg extends Thread {
		List list = null;

		public getBomFromEmsHeadImg(List list) {
			this.list = list;
		}

		public void run() {
			try {
				CommonProgress.showProgressDialog(DgEmsHeadH2k.this);
				CommonProgress.setMessage("系统正在新增资料，请稍后...");
				EmsHeadH2kBom bom = null;
				for (int i = 0; i < list.size(); i++) {
					bom = (EmsHeadH2kBom) list.get(i);
					bom.setModifyMark(ModifyMarkState.ADDED);
					if (isEmsH2kSendSign != null
							&& "1".equals(isEmsH2kSendSign)) {
						bom.setSendState(Integer.valueOf(SendState.WAIT_SEND));
					}
					bom.setModifyTimes(emsHeadH2k.getModifyTimes());
					bom.setChangeDate(new Date());
					bom.setCompany(CommonVars.getCurrUser().getCompany());
					bom = manualdeclearAction.saveEmsHeadH2kBom(new Request(
							CommonVars.getCurrUser()), bom);
					tableModelBom.addRow(bom);
					// EmsEdiMergerClientLogic.transEmsSendState(jTableBom);
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
						"新增失败：！" + e.getMessage(), "提示", 2);
			} finally {
				setState();
			}
		}
	}

	private void fromEmsEdiMergerBeforeBom(Integer version) {
		long b = System.currentTimeMillis();
		DgEmsHeadH2kBomGet dgEmsHeadBom = new DgEmsHeadH2kBomGet();
		dgEmsHeadBom.setTableModel(tableModelExg);
		dgEmsHeadBom.setVersion(version);
		dgEmsHeadBom.setVisible(true);
		setState();
		List dataSourceVersion = null;
		long e = System.currentTimeMillis();
		System.out.println("init time:" + (e - b) / 1000.0);

		b = System.currentTimeMillis();
		dataSourceVersion = manualdeclearAction.findEmsHeadH2kVersion( // 得到版本号
				new Request(CommonVars.getCurrUser()), emsHeadH2kExg);
		e = System.currentTimeMillis();
		System.out.println("findEmsHeadH2kVersion time:" + (e - b) / 1000.0);

		if (dataSourceVersion != null && !dataSourceVersion.isEmpty()) {

			b = System.currentTimeMillis();
			initTableVersion(dataSourceVersion);
			emsHeadH2kVersion = (EmsHeadH2kVersion) dataSourceVersion.get(0); // 第一个版本号
			e = System.currentTimeMillis();
			System.out.println("initTableVersion time:" + (e - b) / 1000.0);

			b = System.currentTimeMillis();
			dataSourceBom = manualdeclearAction.findEmsHeadH2kBom(new Request(
					CommonVars.getCurrUser()), // 得到BOM
					emsHeadH2kVersion);
			e = System.currentTimeMillis();
			System.out.println("findEmsHeadH2kBom time:" + (e - b) / 1000.0);

			if (dataSourceBom != null && !dataSourceBom.isEmpty()) {
				initTableBom(dataSourceBom);
				EmsEdiMergerClientLogic.transEmsSendState(jTableBom);
			} else {
				initTableBom(new Vector());
			}
		} else {
			initTableVersion(new Vector());
			initTableBom(new Vector());
		}
	}

	/**
	 * 
	 * This method initializes jMenuItem
	 * 
	 * 
	 * 
	 * @return javax.swing.JMenuItem
	 * 
	 */
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem();
			jMenuItem.setText("从归并前BOM拷贝");
			jMenuItem.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

				}
			});

		}
		return jMenuItem;
	}

	/**
	 * 
	 * This method initializes jMenuItem1
	 * 
	 * 
	 * 
	 * @return javax.swing.JMenuItem
	 * 
	 */
	private JMenuItem getJMenuItem1() {
		if (jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.setText("从料件表中选择");
			jMenuItem1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

				}
			});

		}
		return jMenuItem1;
	}

	/**
	 * 判断此账册是否已经发送
	 * 
	 * @return
	 */
	private boolean checkIsSendState() {

		emsHeadH2k = manualdeclearAction.getH2k(
				new Request(CommonVars.getCurrUser()), emsHeadH2k.getId());

		tableModel.updateRow(emsHeadH2k);

		if (DeclareState.WAIT_EAA.equals(emsHeadH2k.getDeclareState())) {

			JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
					"此账册已经发送，正等待审批，不能执行此动作！");

			return true;

		}

		return false;
	}

	void setState() {
		jTabbedPane
				.setEnabled((emsHeadH2k.getDeclareState().equals(
						DeclareState.APPLY_POR) && dataState == DataState.BROWSE)
						|| !emsHeadH2k.getDeclareState().equals(
								DeclareState.APPLY_POR));
		jPanel4.setVisible(!(jTabbedPane.getSelectedIndex() == 0));
		btnInsert.setEnabled(!(jTabbedPane.getSelectedIndex() == 0)
				&& (dataState != DataState.READONLY)); // 新增
		btnEditFactoryPrice.setEnabled(!(jTabbedPane.getSelectedIndex() == 0));// 修改单价
		btnEdit.setEnabled((dataState == DataState.BROWSE && (jTabbedPane
				.getSelectedIndex() == 0))
				|| ((dataState != DataState.READONLY) && isImgExgPageAndExistData()));// 修改
		btnDelete.setEnabled((dataState != DataState.READONLY)
				&& isImgExgPageAndExistData()); // 删除
		btnSave.setEnabled((jTabbedPane.getSelectedIndex() == 0)
				&& dataState != DataState.BROWSE
				&& dataState != DataState.READONLY
				&& !isHeadPageAndPrepareChange()); // 保存

		tfMaxTurnMoney.setEditable(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE
				&& !isHeadPageAndPrepareChange());
		tfTel.setEditable(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE
				&& !isHeadPageAndPrepareChange());
		tfIePort.setEditable(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE
				&& !isHeadPageAndPrepareChange());
		tfAddress.setEditable(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE
				&& !isHeadPageAndPrepareChange());
		tfTradeName.setEditable(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE
				&& !isHeadPageAndPrepareChange());
		tfMachName.setEditable(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE
				&& !isHeadPageAndPrepareChange());
		tfOutTradeCo.setEditable(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE
				&& !isHeadPageAndPrepareChange());
		btnIePort.setEnabled(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE
				&& !isHeadPageAndPrepareChange());
		btnLevyKind.setEnabled(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE
				&& !isHeadPageAndPrepareChange());
		btnPayMode.setEnabled(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE
				&& !isHeadPageAndPrepareChange());
		btnMachiningType.setEnabled(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE
				&& !isHeadPageAndPrepareChange());
		tfNote.setEditable(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE
				&& !isHeadPageAndPrepareChange());

		tfCopEmsNo.setEditable(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE
				&& !isHeadPageAndPrepareChange());
		tfEmsNo.setEditable(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE
				&& !isHeadPageAndPrepareChange());
		tfSancEmsNo.setEditable(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE
				&& !isHeadPageAndPrepareChange());
		cbbEndDate.setEnabled(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE
				&& !isHeadPageAndPrepareChange());

		// btnAddBom.setEnabled(dataState != DataState.READONLY
		// && tableModelVersion.getRowCount() > 0); // add
		btnAddBom.setEnabled(dataState != DataState.READONLY); // add

		btnEditBom.setEnabled(dataState != DataState.READONLY // 修改Bom资料
				&& tableModelBom.getRowCount() > 0); // edit
		jButton6.setEnabled(dataState != DataState.READONLY
				&& tableModelBom.getRowCount() > 0); // delete
		jButton7.setEnabled(dataState != DataState.READONLY
				&& tableModelExg.getRowCount() > 0); // addversion
		jButton8.setEnabled(dataState != DataState.READONLY
				&& tableModelVersion.getRowCount() > 0); // copyversion
		/*
		 * jButton9.setEnabled(dataState != DataState.READONLY &&
		 * tableModelVersion.getRowCount() > 0); //pasteversion
		 */jButton10.setEnabled(dataState != DataState.READONLY
				&& tableModelVersion.getRowCount() > 0);// deleteversion

		jButton12.setEnabled(dataState != DataState.READONLY
				&& tableModelExg.getRowCount() > 0); // 单耗导入
		boolean isSend = getIsEmsSend();
		btnEditDeclareState
				.setEnabled(((dataState != DataState.READONLY) && isImgExgPageAndExistData()));// 修改申报状态
		btnBomExport
				.setEnabled(((dataState != DataState.READONLY) && isExgPage()));// 修改申报状态
		btnMaxVersion.setEnabled(isExgPage());
		btnElse.setEnabled(((dataState != DataState.READONLY) && isImgExgPageAndExistData()));// 更新来自内部归并
		btnEditDeclareState.setVisible(isSend);

		jButton15.setEnabled(dataState != DataState.READONLY
				&& (tableModelBom.getRowCount() > 0));// 改变料件申报状态
		jButton19.setEnabled(dataState != DataState.READONLY
				&& (tableModelBom.getRowCount() > 0));// 更改修改标记
		jButton15.setVisible(isSend);
		btnPrice.setEnabled((dataState != DataState.READONLY)
				&& isImgExgPageAndExistData()
				&& jTabbedPane.getSelectedIndex() == 2);// 修改

	}

	private boolean isImgExgPageAndExistData() {
		if ((jTabbedPane.getSelectedIndex() == 1)
				&& (tableModelImg.getRowCount() > 0)) {
			return true;
		}
		if ((jTabbedPane.getSelectedIndex() == 2)
				&& (tableModelExg.getRowCount() > 0)) {
			return true;
		}
		return false;
	}

	private boolean isExgPage() {
		if (jTabbedPane.getSelectedIndex() == 2) {
			return true;
		}
		return false;
	}

	private boolean isHeadPageAndPrepareChange() {
		if (emsHeadH2k.getDeclareType().equals("3")
				&& jTabbedPane.getSelectedIndex() == 0) {
			return true;
		}
		return false;
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
			jRadioButton.setVisible(false);
			jRadioButton.setText("从BOM拷贝");
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
			jRadioButton1.setVisible(false);
			jRadioButton1.setText("从料件选择");
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

	private void changHead(EmsHeadH2k emsHeadH2k) {
		EmsHeadH2k emsH2k = manualdeclearAction.getH2k(
				new Request(CommonVars.getCurrUser()), emsHeadH2k.getId());
		emsH2k.setImgItems(String.valueOf(tableModelImg.getRowCount()));
		emsH2k.setExgItems(String.valueOf(tableModelExg.getRowCount()));
		emsHeadH2k = manualdeclearAction.saveEmsHeadH2k(
				new Request(CommonVars.getCurrUser()), emsH2k);
	}

	/**
	 * @return Returns the levyMode.
	 */
	public LevyKind getLevyKind() {
		return levyKind;
	}

	/**
	 * This method initializes btnWare
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnWare() {
		if (btnWare == null) {
			btnWare = new JButton();
			btnWare.setText("单耗表");
			btnWare.setPreferredSize(new Dimension(60, 30));
			btnWare.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgShowEmsUnitWaste dg = new DgShowEmsUnitWaste();
					dg.setHead(emsHeadH2k);
					dg.setVisible(true);
					showExgBomData();
				}
			});
		}
		return btnWare;
	}

	/**
	 * This method initializes btnEditFactoryPrice
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditFactoryPrice() {
		if (btnEditFactoryPrice == null) {
			btnEditFactoryPrice = new JButton();
			btnEditFactoryPrice.setText("修改工厂单价");
			btnEditFactoryPrice.setPreferredSize(new Dimension(84, 30));
			btnEditFactoryPrice
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (checkIsSendState()) {
								return;
							}
							getJPopupMenu2().show(btnEditFactoryPrice, 0,
									btnEditFactoryPrice.getHeight());
							getJPopupMenu2().show(btnEditFactoryPrice, 0,
									btnEditFactoryPrice.getHeight());
						}
					});
		}
		return btnEditFactoryPrice;
	}

	/**
	 * This method initializes jButton12
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton12() {
		if (jButton12 == null) {
			jButton12 = new JButton();
			jButton12.setText("单耗导入");
			jButton12.setPreferredSize(new Dimension(70, 30));
			jButton12.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkIsSendState()) {
						return;
					}
					DgEmsHeadH2kImport1 dg = new DgEmsHeadH2kImport1();
					dg.setEmsHeadH2k(emsHeadH2k);
					dg.setImgs(tableModelImg.getList());
					dg.setExgs(tableModelExg.getList());
					dg.setVisible(true);
					if (emsHeadH2kExg == null) {
						return;
					}
					freshBomAndVersion();
				}
			});
		}
		return jButton12;
	}

	private void freshBomAndVersion() {
		List dataSourceVersion = null;
		dataSourceVersion = manualdeclearAction.findEmsHeadH2kVersion( // 得到版本号
				new Request(CommonVars.getCurrUser()), emsHeadH2kExg);
		if (dataSourceVersion != null && !dataSourceVersion.isEmpty()) {
			initTableVersion(dataSourceVersion);

			emsHeadH2kVersion = (EmsHeadH2kVersion) dataSourceVersion.get(0); // 第一个版本号

			Integer version = emsHeadH2kVersion.getVersion();
			dataSourceBom = manualdeclearAction.findEmsHeadH2kBom(new Request(
					CommonVars.getCurrUser()), // 得到BOM
					emsHeadH2kVersion);
			if (dataSourceBom != null && !dataSourceBom.isEmpty()) {
				initTableBom(dataSourceBom);
				EmsEdiMergerClientLogic.transEmsSendState(jTableBom);
			} else {
				initTableBom(new Vector());
			}
		} else {
			initTableVersion(new Vector());
			initTableBom(new Vector());
		}
	}

	/**
	 * This method initializes jButton13
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton13() {
		if (jButton13 == null) {
			jButton13 = new JButton();
			jButton13.setText("更新变更次数");
			jButton13.setVisible(false);
			jButton13.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new tempChange().start();
				}
			});
		}
		return jButton13;
	}

	class tempChange extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(DgEmsHeadH2k.this);
				CommonProgress.setMessage("系统正在进行更新变更次数，请稍后...");
				manualdeclearAction.changeChangeNum(
						new Request(CommonVars.getCurrUser()), emsHeadH2k);
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsHeadH2k.this, "更新完毕！", "提示",
						2);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
						"更新失败！" + e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * This method initializes btnEditDeclareState
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditDeclareState() {
		if (btnEditDeclareState == null) {
			btnEditDeclareState = new JButton();
			btnEditDeclareState.setText("改变申报状态");
			btnEditDeclareState.setPreferredSize(new Dimension(84, 30));
			btnEditDeclareState.setForeground(java.awt.Color.blue);
			btnEditDeclareState
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (checkIsSendState()) {
								return;
							}
							if (jTabbedPane.getSelectedIndex() == 1) {// 料件
								if (tableModelImg.getCurrentRows() == null
										|| tableModelImg.getCurrentRows()
												.size() == 0) {
									JOptionPane.showMessageDialog(
											DgEmsHeadH2k.this, "没有选中数据！",
											"提示！", JOptionPane.WARNING_MESSAGE);
									return;
								}
								MyJPopupMenu mym = new MyJPopupMenu() {
									List list = tableModelImg.getCurrentRows();

									void changeState(String state) {
										// 修改申报状态
										for (int i = 0; i < list.size(); i++) {
											EmsHeadH2kImg obj = (EmsHeadH2kImg) list
													.get(i);

											obj = manualdeclearAction.alterEMSState(
													new Request(CommonVars
															.getCurrUser()),
													obj, state);
											tableModelImg.updateRow(obj);
										}
									}
								};
								mym.show(btnEditDeclareState, 0,
										btnEditDeclareState.getHeight());
							} else if (jTabbedPane.getSelectedIndex() == 2) {
								if (tableModelExg.getCurrentRows() == null
										|| tableModelExg.getCurrentRows()
												.size() == 0) {
									JOptionPane.showMessageDialog(
											DgEmsHeadH2k.this, "没有选中数据！",
											"提示！", JOptionPane.WARNING_MESSAGE);
									return;
								}
								MyJPopupMenu mym = new MyJPopupMenu() {
									List list = tableModelExg.getCurrentRows();

									void changeState(String state) {
										for (int i = 0; i < list.size(); i++) {
											EmsHeadH2kExg obj = (EmsHeadH2kExg) list
													.get(i);
											obj = manualdeclearAction.alterEMSState(
													new Request(CommonVars
															.getCurrUser()),
													obj, state);
											tableModelExg.updateRow(obj);
										}
									}
								};
								mym.show(btnEditDeclareState, 0,
										btnEditDeclareState.getHeight());
								// }
							}
						}
					});
		}
		return btnEditDeclareState;
	}

	/**
	 * This method initializes jButton9
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBomExport() {
		if (btnBomExport == null) {
			btnBomExport = new JButton();
			btnBomExport.setText("BOM导出");
			btnBomExport.setPreferredSize(new Dimension(62, 30));
			btnBomExport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgEmsHeadH2kBomExport dg = new DgEmsHeadH2kBomExport();
					dg.setHead(emsHeadH2k);
					dg.setVisible(true);
				}
			});
		}
		return btnBomExport;
	}

	/**
	 * This method initializes jButton15
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton15() {
		if (jButton15 == null) {
			jButton15 = new JButton();
			jButton15.setText("改变申报状态");
			jButton15.setPreferredSize(new Dimension(84, 30));
			jButton15.setForeground(java.awt.Color.blue);
			jButton15.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkIsSendState()) {
						return;
					}
					if (tableModelBom.getCurrentRows() == null
							|| tableModelBom.getCurrentRows().size() == 0) {
						JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
								"没有选中数据！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					MyJPopupMenu mym = new MyJPopupMenu() {
						List list = tableModelBom.getCurrentRows();

						void changeState(String state) {
							// 修改申报状态权限
							for (int i = 0; i < list.size(); i++) {
								EmsHeadH2kBom obj = (EmsHeadH2kBom) list.get(i);
								obj = manualdeclearAction.alterEMSState(
										new Request(CommonVars.getCurrUser()),
										obj, state);
								tableModelBom.updateRow(obj);
							}
						}
					};
					mym.show(jButton15, 0, jButton15.getHeight());
				}
			});
		}
		return jButton15;
	}

	/**
	 * This method initializes btnPrice
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrice() {
		if (btnPrice == null) {
			btnPrice = new JButton();
			btnPrice.setText("计算单价");
			btnPrice.setPreferredSize(new Dimension(60, 30));
			btnPrice.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkIsSendState()) {
						return;
					}
					getJPopupMenu1().show(btnPrice, 0, btnPrice.getHeight());
					getJPopupMenu1().show(btnPrice, 0, btnPrice.getHeight());
				}
			});
		}
		return btnPrice;
	}

	/**
	 * This method initializes jMenuItem2
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem2() {
		if (jMenuItem2 == null) {
			jMenuItem2 = new JMenuItem();
			jMenuItem2.setText("默认公式");
			jMenuItem2.setForeground(java.awt.Color.blue);
			jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane
							.showConfirmDialog(
									DgEmsHeadH2k.this,
									"=============【计算选择的成品单价】=============\n按默认公式计算：\n 耗用料件总价 = 汇总(单位用量 * 单价)\n是否继续?",
									"确认", 0) != 0) {
						return;
					}
					if (tableModelExg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
								"请选择你要计算的成品资料", "确认", 1);
						return;
					}
					if (tableModelVersion.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
								"请选中要计算的版本！", "提示！", 2);
						return;
					}
					emsHeadH2kVersion = (EmsHeadH2kVersion) tableModelVersion
							.getCurrentRow();
					emsHeadH2kExg = (EmsHeadH2kExg) tableModelExg
							.getCurrentRow();
					emsHeadH2kExg = manualdeclearAction.defaultAmountPrice(
							new Request(CommonVars.getCurrUser()),
							emsHeadH2kExg, emsHeadH2kVersion);
					tableModelExg.updateRow(emsHeadH2kExg);
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
			jMenuItem3 = new JMenuItem();
			jMenuItem3.setText("自定义公式");
			jMenuItem3.setForeground(java.awt.Color.blue);
			jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
								"请选择你要计算的成品资料", "确认", 1);
						return;
					}
					if (tableModelVersion.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
								"请选中要计算的版本！", "提示！", 2);
						return;
					}
					emsHeadH2kVersion = (EmsHeadH2kVersion) tableModelVersion
							.getCurrentRow();
					emsHeadH2kExg = (EmsHeadH2kExg) tableModelExg
							.getCurrentRow();
					DgEmsHeadH2kPrice dg = new DgEmsHeadH2kPrice();
					dg.setVisible(true);
					if (dg.isOk()) {
						Double a = dg.getA();
						Double b = dg.getB();
						emsHeadH2kExg = manualdeclearAction
								.amountExgDefinePrice(
										new Request(CommonVars.getCurrUser()),
										emsHeadH2kExg, emsHeadH2kVersion, a, b);
						tableModelExg.updateRow(emsHeadH2kExg);
					}
				}
			});
		}
		return jMenuItem3;
	}

	/**
	 * This method initializes jPopupMenu1
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu1() {
		if (jPopupMenu1 == null) {
			jPopupMenu1 = new JPopupMenu();
			jPopupMenu1.add(getJMenuItem2());
			jPopupMenu1.add(getJMenuItem3());
		}
		return jPopupMenu1;
	}

	/**
	 * This method initializes jPopupMenu1
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu5() {
		if (jPopupMenu5 == null) {
			jPopupMenu5 = new JPopupMenu();
			jPopupMenu5.add(getMiImg());
			jPopupMenu5.add(getMiVersionOrImg());
			jPopupMenu5.add(getMiVersionOrBomImg());
			jPopupMenu5.add(getMiAllBomImg());
		}
		return jPopupMenu5;
	}

	/**
	 * This method initializes jMenuItem2
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiAllBomImg() {
		if (miAllBomImg == null) {
			miAllBomImg = new JMenuItem();
			miAllBomImg.setText("新增单耗来于归并前全部BOM");
			miAllBomImg.setForeground(java.awt.Color.blue);
			miAllBomImg.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkIsSendState()) {
						return;
					}
					emsHeadH2kExg = (EmsHeadH2kExg) tableModelExg
							.getCurrentRow();
					if (emsHeadH2kExg.getModifyMark().equals(
							ModifyMarkState.DELETED)) {
						JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
								"成品已删除，不能新增BOM!", "提示！", 2);
						return;
					}
					fromEmsEdiMergerBeforeBom(null);// 来自归并关系BOM
				}
			});
		}
		return miAllBomImg;
	}

	/**
	 * This method initializes jMenuItem2
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiVersionOrBomImg() {
		if (miVersionOrBomImg == null) {
			miVersionOrBomImg = new JMenuItem();
			miVersionOrBomImg.setText("新增单耗来于归并前BOM(输入版本)");
			miVersionOrBomImg.setForeground(java.awt.Color.blue);
			miVersionOrBomImg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (checkIsSendState()) {
								return;
							}
							if (!addVerision()) {
								return;
							}
							emsHeadH2kVersion = (EmsHeadH2kVersion) tableModelVersion
									.getCurrentRow();// 版本号
							emsHeadH2kExg = (EmsHeadH2kExg) tableModelExg
									.getCurrentRow();
							if (emsHeadH2kExg.getModifyMark().equals(
									ModifyMarkState.DELETED)) {
								JOptionPane.showMessageDialog(
										DgEmsHeadH2k.this, "成品已删除，不能新增BOM!",
										"提示！", 2);
								return;
							}
							Integer version = emsHeadH2kVersion == null ? null
									: emsHeadH2kVersion.getVersion();
							fromEmsEdiMergerBeforeBom(version);// 来自归并关系BOM
						}
					});
		}
		return miVersionOrBomImg;
	}

	/**
	 * This method initializes jMenuItem2
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiImg() {
		if (miImg == null) {
			miImg = new JMenuItem();
			miImg.setText("新增单耗来于料件");
			miImg.setForeground(java.awt.Color.blue);
			miImg.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkIsSendState()) {
						return;
					}
					// if (!addVerision()) {
					// return;
					// }
					fromEmsHeadImg();// 来自帐册料件
				}
			});
		}
		return miImg;
	}

	/**
	 * This method initializes jMenuItem2
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiVersionOrImg() {
		if (miVersionOrImg == null) {
			miVersionOrImg = new JMenuItem();
			miVersionOrImg.setText("新增单耗来于料件(输入版本)");
			miVersionOrImg.setForeground(java.awt.Color.blue);
			miVersionOrImg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (checkIsSendState()) {
								return;
							}
							if (!addVerision()) {
								return;
							}
							fromEmsHeadImg();// 来自帐册料件
						}
					});
		}
		return miVersionOrImg;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jLabel31 = new JLabel();
			jLabel31.setBounds(new Rectangle(5, 5, 86, 18));
			jLabel31.setText("选择查询条件");
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setPreferredSize(new Dimension(500, 30));
			jPanel4.add(getCbbDeclareState(), null);
			jPanel4.add(getJTextField25(), null);
			jPanel4.add(getBtnSearch(), null);
			jPanel4.add(jLabel31, null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes cbbDeclareState
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDeclareState() {
		if (cbbDeclareState == null) {
			cbbDeclareState = new JComboBox();
			cbbDeclareState.setBounds(new Rectangle(91, 1, 94, 25));
			cbbDeclareState.addItem(null);
			cbbDeclareState.addItem("归并序号");
			cbbDeclareState.addItem("变更日期");
			cbbDeclareState.addItem("变更次数");
			cbbDeclareState.addItem("修改标志");
			cbbDeclareState.addItem("发送状态");
			cbbDeclareState.setSelectedIndex(1);
		}
		return cbbDeclareState;
	}

	/**
	 * This method initializes jTextField25
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField25() {
		if (jTextField25 == null) {
			jTextField25 = new JTextField();
			jTextField25.setBounds(new Rectangle(195, 2, 114, 25));
		}
		return jTextField25;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setBounds(new Rectangle(318, 1, 58, 25));
			btnSearch.setText("查询");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String fields = null;
					Object sValue = null;
					if (cbbDeclareState.getSelectedItem() == null
							|| "".equals(cbbDeclareState.getSelectedItem())
							|| "".equals(jTextField25.getText().trim())) {
						fields = null;
						sValue = null;
					} else if (cbbDeclareState.getSelectedItem().equals("变更日期")) {
						fields = "changeDate";
						sValue = jTextField25.getText().trim();
					} else if (cbbDeclareState.getSelectedItem().equals("变更次数")) {
						fields = "modifyTimes";
						sValue = Integer.valueOf(jTextField25.getText().trim());
					} else if (cbbDeclareState.getSelectedItem().equals("修改标志")) {
						fields = "modifyMark";
						sValue = jTextField25.getText().trim();
					} else if (cbbDeclareState.getSelectedItem().equals("发送状态")) {
						fields = "sendState";
						sValue = Integer.valueOf(jTextField25.getText().trim());
					} else if (cbbDeclareState.getSelectedItem().equals("归并序号")) {
						fields = "seqNum";
						sValue = Integer.valueOf(jTextField25.getText().trim());
					}

					List dataSourceImg = null;
					dataSourceImg = manualdeclearAction.findEmsHeadH2kImgExg(
							new Request(CommonVars.getCurrUser()), emsHeadH2k,
							true, fields, sValue);
					if (dataSourceImg != null && !dataSourceImg.isEmpty()) {
						initTableImg(dataSourceImg);
						EmsEdiMergerClientLogic.transEmsSendState(jTableImg);
					} else {
						initTableImg(new Vector());
					}

				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes jPopupMenu2
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu2() {
		if (jPopupMenu2 == null) {
			jPopupMenu2 = new JPopupMenu();
			jPopupMenu2.add(getJMenuItem4());
			jPopupMenu2.add(getJMenuItem5());
		}
		return jPopupMenu2;
	}

	/**
	 * This method initializes jMenuItem4
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem4() {
		if (jMenuItem4 == null) {
			jMenuItem4 = new JMenuItem();
			jMenuItem4.setText("修改工厂单价");
			jMenuItem4.setForeground(java.awt.Color.blue);
			jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgEmsHeadH2kImgExgPrice dgEmsHeadH2kImgExg = new DgEmsHeadH2kImgExgPrice();
					if (jTabbedPane.getSelectedIndex() == 1)// 料件
					{
						if (tableModelImg.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
									"请选择你要修改的料件资料", "确认", 1);
							return;
						}
						dgEmsHeadH2kImgExg.setImg(true);
						dgEmsHeadH2kImgExg.setTableModel(tableModelImg);
					} else if (jTabbedPane.getSelectedIndex() == 2) {// 成品
						if (tableModelVersion.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
									"请选择你要修改的成品版本资料", "确认", 1);
							return;
						}
						dgEmsHeadH2kImgExg.setImg(false);
						dgEmsHeadH2kImgExg.setTableModel(tableModelVersion);
					}
					dgEmsHeadH2kImgExg.setEmsHeadH2k(emsHeadH2k);
					dgEmsHeadH2kImgExg.setVisible(true);
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
			jMenuItem5.setText("计算工厂单价");
			jMenuItem5.setForeground(java.awt.Color.blue);
			jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane
							.showConfirmDialog(
									DgEmsHeadH2k.this,
									"=============【计算选择的成品单价】=============\n按默认公式计算：\n 成品单价 = 汇总(单耗 * 单价)/增值比率\n是否继续?",
									"确认", 0) != 0) {
						return;
					}
					new amountFactoryPrice().start();

				}
			});
		}
		return jMenuItem5;
	}

	class amountFactoryPrice extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(DgEmsHeadH2k.this);
				CommonProgress.setMessage("系统正在计算工厂单价，请稍后...");
				List list = tableModelExg.getCurrentRows();
				manualdeclearAction.amountFactoryPrice(
						new Request(CommonVars.getCurrUser()), list);
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsHeadH2k.this, "计算完毕,请刷新版本！",
						"提示", 2);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
						"计算失败！" + e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * This method initializes jButton18
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton18() {
		if (jButton18 == null) {
			jButton18 = new JButton();
			jButton18.setText("关闭");
			jButton18.setPreferredSize(new Dimension(60, 30));
			jButton18.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton18;
	}

	/**
	 * This method initializes jButton19
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton19() {
		if (jButton19 == null) {
			jButton19 = new JButton();
			jButton19.setText("其他");
			jButton19.setPreferredSize(new Dimension(70, 30));
			jButton19.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkIsSendState()) {
						return;
					}
					MyModifJPopupMenu mym = new MyModifJPopupMenu("全选(到准备申报)") {
						List list = tableModelBom.getCurrentRows();

						void doOther() {
							if (tableModelVersion == null)
								return;
							if (tableModelVersion.getCurrentRow() == null)
								return;
							emsHeadH2kVersion = (EmsHeadH2kVersion) tableModelVersion
									.getCurrentRow();
							Integer version = emsHeadH2kVersion.getVersion();// 版本号
							dataSourceBom = manualdeclearAction
									.findEmsHeadH2kBom(
											new Request(CommonVars
													.getCurrUser()), // 得到BOM
											emsHeadH2kVersion);
							List ls = manualdeclearAction
									.emsSelectAllSendState(new Request(
											CommonVars.getCurrUser()),
											dataSourceBom);
							tableModelBom.updateRows(ls);
						}

						void changeState(String state) {
							if (JOptionPane.showConfirmDialog(
									DgEmsHeadH2k.this, "您确认要修改标记吗?", "提示",
									JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
								return;
							}
							// 修改标记权限
							for (int i = 0; i < list.size(); i++) {
								EmsHeadH2kBom obj = (EmsHeadH2kBom) list.get(i);
								obj.setModifyMark(state);
								obj = manualdeclearAction.changeEMSState(
										new Request(CommonVars.getCurrUser()),
										obj, state);
								tableModelBom.updateRow(obj);
							}
						}
					};
					mym.show(jButton19, 0, jButton19.getHeight());
				}
			});
			// jButton19.addActionListener(new java.awt.event.ActionListener() {
			// public void actionPerformed(java.awt.event.ActionEvent e) {
			// getJPopupMenu4().show(jButton19, 0, jButton19.getHeight());
			// getJPopupMenu4().show(jButton19, 0, jButton19.getHeight());
			//
			// }
			// });
		}
		return jButton19;
	}

	/**
	 * This method initializes jPopupMenu3
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu3() {
		if (jPopupMenu3 == null) {
			jPopupMenu3 = new JPopupMenu();
			jPopupMenu3.add(getJMenuItem7());// 更改修改表
			jPopupMenu3.add(getJMenuItem6());// 与内部归并同步

		}
		return jPopupMenu3;
	}

	/**
	 * This method initializes jMenuItem6
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem6() {
		if (jMenuItem6 == null) {
			jMenuItem6 = new JMenuItem();
			jMenuItem6.setText("更新(与内部归并同步)");
			jMenuItem6.setForeground(java.awt.Color.blue);
			jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(DgEmsHeadH2k.this,
							"您确认要与内部归并同步吗?", "提示", 0) != 0) {
						return;
					}

					if (jTabbedPane.getSelectedIndex() == 1) {// 料件
						if (tableModelImg == null)
							return;
						if (tableModelImg.getCurrentRows() == null)
							return;
						List ls = tableModelImg.getCurrentRows();
						ls = manualdeclearAction.synchroEmsEdiImg(new Request(
								CommonVars.getCurrUser()), ls);
						tableModelImg.updateRows(ls);
					} else if (jTabbedPane.getSelectedIndex() == 2) {// 成品
						if (tableModelExg == null)
							return;
						if (tableModelExg.getCurrentRows() == null)
							return;
						List ls = tableModelExg.getCurrentRows();
						ls = manualdeclearAction.synchroEmsEdiExg(new Request(
								CommonVars.getCurrUser()), ls);
						tableModelExg.updateRows(ls);
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
			jMenuItem7.setText("改变修改标记");
			jMenuItem7.setForeground(java.awt.Color.blue);
			jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(DgEmsHeadH2k.this,
							"您确认要修改标记吗?", "提示", 0) != 0) {
						return;
					}
					if (jTabbedPane.getSelectedIndex() == 1) {// 料件
						if (tableModelImg == null)
							return;
						if (tableModelImg.getCurrentRows() == null)
							return;
						/*
						 * if (!CommonVars.isManager()){ return; }
						 */
						List ls = tableModelImg.getCurrentRows();
						for (int i = 0; i < ls.size(); i++) {
							EmsHeadH2kImg obj = (EmsHeadH2kImg) ls.get(i);
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
							obj = manualdeclearAction.saveEmsHeadH2kImg(
									new Request(CommonVars.getCurrUser()), obj);
							tableModelImg.updateRow(obj);
						}
					} else if (jTabbedPane.getSelectedIndex() == 2) {
						if (tableModelExg == null)
							return;
						if (tableModelExg.getCurrentRows() == null)
							return;
						/*
						 * if (!CommonVars.isManager()){ return; }
						 */
						List ls = tableModelExg.getCurrentRows();
						for (int i = 0; i < ls.size(); i++) {
							EmsHeadH2kExg obj = (EmsHeadH2kExg) ls.get(i);
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
							obj = manualdeclearAction.saveEmsHeadH2kExg(
									new Request(CommonVars.getCurrUser()), obj);
							tableModelExg.updateRow(obj);
						}
					}
				}
			});
		}
		return jMenuItem7;
	}

	/**
	 * This method initializes jPopupMenu4
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu4() {
		if (jPopupMenu4 == null) {
			jPopupMenu4 = new JPopupMenu();
			jPopupMenu4.add(getJMenuItem8());// 更改修改标记
			jPopupMenu4.add(getJMenuItem9());// 全选
		}
		return jPopupMenu4;
	}

	/**
	 * This method initializes jMenuItem8
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem8() {
		if (jMenuItem8 == null) {
			jMenuItem8 = new JMenuItem();
			jMenuItem8.setText("改变修改标记");
			jMenuItem8.setForeground(java.awt.Color.blue);
			jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(DgEmsHeadH2k.this,
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
					List ls = tableModelBom.getCurrentRows();
					for (int i = 0; i < ls.size(); i++) {
						EmsHeadH2kBom obj = (EmsHeadH2kBom) ls.get(i);
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
						obj = manualdeclearAction.saveEmsHeadH2kBom(
								new Request(CommonVars.getCurrUser()), obj);
						tableModelBom.updateRow(obj);
					}
				}
			});
		}
		return jMenuItem8;
	}

	/**
	 * This method initializes jMenuItem9
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem9() {
		if (jMenuItem9 == null) {
			jMenuItem9 = new JMenuItem();
			jMenuItem9.setText("全选(到准备申报)");
			jMenuItem9.setForeground(java.awt.Color.blue);
			jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelVersion == null)
						return;
					if (tableModelVersion.getCurrentRow() == null)
						return;
					emsHeadH2kVersion = (EmsHeadH2kVersion) tableModelVersion
							.getCurrentRow();
					Integer version = emsHeadH2kVersion.getVersion();// 版本号
					dataSourceBom = manualdeclearAction.findEmsHeadH2kBom(
							new Request(CommonVars.getCurrUser()), // 得到BOM
							emsHeadH2kVersion);
					List ls = manualdeclearAction.emsSelectAllSendState(
							new Request(CommonVars.getCurrUser()),
							dataSourceBom);
					tableModelBom.updateRows(ls);
				}
			});
		}
		return jMenuItem9;
	}

	private void showHeadData() {
		isEmsH2kSendSign = manualdeclearAction.getBpara(
				new Request(CommonVars.getCurrUser()),
				BcusParameter.EmsEdiH2kSend_Sign);
		if (emsHeadH2k == null) {
			emsHeadH2k = (EmsHeadH2k) tableModel.getCurrentRow(); // 获得电子帐册表头
			System.out.println("-----------" + emsHeadH2k.getId());
		}
		if (emsHeadH2k == null) {
			return;
		}
		if (emsHeadH2k != null) {
			emsHeadH2k = manualdeclearAction.getH2k(
					new Request(CommonVars.getCurrUser()), emsHeadH2k.getId());
			tableModel.updateRow(emsHeadH2k);
			payMode = emsHeadH2k.getPayMode();
			machiningType = emsHeadH2k.getMachiningType();
			levyKind = emsHeadH2k.getLevyKind();
		}
		fillWindow();
		if (CommonVars.isCompany("康舒电子")) {
			imgLastDate = manualdeclearAction.findLastUpdateDateByImgExg(
					new Request(CommonVars.getCurrUser()), emsHeadH2k, true);
			exgLastDate = manualdeclearAction.findLastUpdateDateByImgExg(
					new Request(CommonVars.getCurrUser()), emsHeadH2k, false);
		}

		if (emsHeadH2k.getDeclareState().equals(DeclareState.PROCESS_EXE)
				|| (emsHeadH2k.getDeclareState().equals(DeclareState.WAIT_EAA))
				|| isHistoryChange == true) {
			dataState = DataState.READONLY;
		} else
			dataState = DataState.BROWSE;
		// setState();
		beginState();
		List listEmsEdiTr = manualdeclearAction.findEmsEdiTrHead(new Request(
				CommonVars.getCurrUser()));
		for (int i = 0; i < listEmsEdiTr.size(); i++) {
			if (((EmsEdiTrHead) listEmsEdiTr.get(i)).getDeclareState().equals(
					DeclareState.PROCESS_EXE))
				emsEdiTrHead = (EmsEdiTrHead) listEmsEdiTr.get(i); // 获取正在执行的经营范围
		}
	}

	private void showImgData() {
		List dataSourceImg = null;
		dataSourceImg = manualdeclearAction.findEmsHeadH2kImgExg(new Request(
				CommonVars.getCurrUser()), emsHeadH2k, true, "changeDate",
				imgLastDate);
		if (dataSourceImg != null && !dataSourceImg.isEmpty()) {
			initTableImg(dataSourceImg);
			EmsEdiMergerClientLogic.transEmsSendState(jTableImg);
		} else {
			initTableImg(new Vector());
		}
	}

	private void showExgBomData() {
		List dataSourceExg = null;
		dataSourceExg = manualdeclearAction.findEmsHeadH2kImgExg(new Request(
				CommonVars.getCurrUser()), emsHeadH2k, false, "changeDate",
				exgLastDate);
		if (dataSourceExg != null && !dataSourceExg.isEmpty()) {
			initTableExg(dataSourceExg);
			EmsEdiMergerClientLogic.transEmsSendState(jTableExg);

			emsHeadH2kExg = (EmsHeadH2kExg) dataSourceExg.get(0);// 成品
			List dataSourceVersion = null;
			dataSourceVersion = manualdeclearAction.findEmsHeadH2kVersion(
			// 得到版本号
					new Request(CommonVars.getCurrUser()), emsHeadH2kExg);
			if (dataSourceVersion != null && !dataSourceVersion.isEmpty()) {
				initTableVersion(dataSourceVersion);

				emsHeadH2kVersion = (EmsHeadH2kVersion) dataSourceVersion
						.get(0); // 第一个版本号

				Integer version = emsHeadH2kVersion.getVersion();
				dataSourceBom = manualdeclearAction.findEmsHeadH2kBom(
						new Request(CommonVars.getCurrUser()), // 得到BOM
						emsHeadH2kVersion);
				if (dataSourceBom != null && !dataSourceBom.isEmpty()) {
					initTableBom(dataSourceBom);
					EmsEdiMergerClientLogic.transEmsSendState(jTableBom);
				} else {
					initTableBom(new Vector());
				}
			} else {
				initTableVersion(new Vector());
				initTableBom(new Vector());
			}
		} else {
			initTableVersion(new Vector());
			initTableBom(new Vector());
			initTableExg(new Vector());
		}
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
									.findMaxEmsHeadH2kVersion(new Request(
											CommonVars.getCurrUser()));
							List list = tableModelExg.getList();
							for (int i = 0; i < list.size(); i++) {
								EmsHeadH2kExg emsHeadH2kExg = (EmsHeadH2kExg) list
										.get(i);
								emsHeadH2kExg.setMaxVersion(map
										.get(emsHeadH2kExg.getSeqNum()));
							}
							tableModelExg.fireTableDataChanged();
						}
					});
		}
		return btnMaxVersion;
	}

	/**
	 * This method initializes jToolBar2
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			jToolBar2 = new JToolBar();
			jToolBar2.setPreferredSize(new Dimension(18, 30));
			jToolBar2.add(getJPanel4());
		}
		return jToolBar2;
	}

	/**
	 * This method initializes jToolBar3
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar3() {
		if (jToolBar3 == null) {
			jToolBar3 = new JToolBar();
			jToolBar3.setPreferredSize(new Dimension(18, 30));
			jToolBar3.add(getJPanel41());
		}
		return jToolBar3;
	}

	/**
	 * This method initializes jPanel41
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel41() {
		if (jPanel41 == null) {
			jLabel311 = new JLabel();
			jLabel311.setBounds(new Rectangle(5, 5, 86, 18));
			jLabel311.setText("\u9009\u62e9\u67e5\u8be2\u6761\u4ef6");
			jPanel41 = new JPanel();
			jPanel41.setLayout(null);
			jPanel41.setPreferredSize(new Dimension(500, 30));
			jPanel41.add(getCbbExgDeclareState(), null);
			jPanel41.add(getTfExgCondint(), null);
			jPanel41.add(getBtnExgSearch(), null);
			jPanel41.add(getBtnWareCheck(), null);
			jPanel41.add(jLabel311, null);
		}
		return jPanel41;
	}

	/**
	 * This method initializes cbbExgDeclareState
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbExgDeclareState() {
		if (cbbExgDeclareState == null) {
			cbbExgDeclareState = new JComboBox();
			cbbExgDeclareState.setBounds(new Rectangle(91, 1, 94, 25));
			cbbExgDeclareState.addItem(null);
			cbbExgDeclareState.addItem("归并序号");
			cbbExgDeclareState.addItem("变更日期");
			cbbExgDeclareState.addItem("变更次数");
			cbbExgDeclareState.addItem("修改标志");
			cbbExgDeclareState.addItem("发送状态");
			cbbExgDeclareState.setSelectedIndex(1);
		}
		return cbbExgDeclareState;
	}

	/**
	 * This method initializes tfExgCondint
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExgCondint() {
		if (tfExgCondint == null) {
			tfExgCondint = new JTextField();
			tfExgCondint.setBounds(new Rectangle(195, 2, 114, 25));
		}
		return tfExgCondint;
	}

	/**
	 * This method initializes btnExgSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExgSearch() {
		if (btnExgSearch == null) {
			btnExgSearch = new JButton();
			btnExgSearch.setBounds(new Rectangle(318, 1, 58, 25));
			btnExgSearch.setText("\u67e5\u8be2");
			btnExgSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String fields = null;
					Object sValue = null;
					if (cbbExgDeclareState.getSelectedItem() == null
							|| "".equals(cbbExgDeclareState.getSelectedItem())
							|| "".equals(tfExgCondint.getText().trim())) {
						fields = null;
						sValue = null;
					} else if (cbbExgDeclareState.getSelectedItem().equals(
							"变更日期")) {
						fields = "changeDate";
						sValue = tfExgCondint.getText().trim();
					} else if (cbbExgDeclareState.getSelectedItem().equals(
							"变更次数")) {
						fields = "modifyTimes";
						sValue = Integer.valueOf(tfExgCondint.getText().trim());
					} else if (cbbExgDeclareState.getSelectedItem().equals(
							"修改标志")) {
						fields = "modifyMark";
						sValue = tfExgCondint.getText().trim();
					} else if (cbbExgDeclareState.getSelectedItem().equals(
							"发送状态")) {
						fields = "sendState";
						sValue = Integer.valueOf(tfExgCondint.getText().trim());
					} else if (cbbExgDeclareState.getSelectedItem().equals(
							"归并序号")) {
						fields = "seqNum";
						sValue = Integer.valueOf(tfExgCondint.getText().trim());
					}

					// 成品及单耗
					List dataSourceExg = null;
					dataSourceExg = manualdeclearAction.findEmsHeadH2kImgExg(
							new Request(CommonVars.getCurrUser()), emsHeadH2k,
							false, fields, sValue);
					if (dataSourceExg != null && dataSourceExg.size() > 0) {
						initTableExg(dataSourceExg);
						EmsEdiMergerClientLogic.transEmsSendState(jTableExg);

						emsHeadH2kExg = (EmsHeadH2kExg) dataSourceExg.get(0);// 成品
						List dataSourceVersion = null;
						dataSourceVersion = manualdeclearAction
								.findEmsHeadH2kVersion(
										// 得到版本号
										new Request(CommonVars.getCurrUser()),
										emsHeadH2kExg);
						if (dataSourceVersion != null
								&& !dataSourceVersion.isEmpty()) {
							initTableVersion(dataSourceVersion);

							emsHeadH2kVersion = (EmsHeadH2kVersion) dataSourceVersion
									.get(0); // 第一个版本号
							Integer version = emsHeadH2kVersion.getVersion();
							dataSourceBom = manualdeclearAction
									.findEmsHeadH2kBom(
											new Request(CommonVars
													.getCurrUser()), // 得到BOM
											emsHeadH2kVersion);
							if (dataSourceBom != null
									&& dataSourceBom.size() > 0) {
								initTableBom(dataSourceBom);
								EmsEdiMergerClientLogic
										.transEmsSendState(jTableBom);
							} else {
								initTableBom(new Vector());
							}

						} else {
							initTableVersion(new Vector());
							initTableBom(new Vector());
						}

					} else {
						initTableVersion(new Vector());
						initTableBom(new Vector());
						initTableExg(new Vector());
					}

				}
			});
		}
		return btnExgSearch;
	}

	/**
	 * This method initializes btnWareCheck
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnWareCheck() {
		if (btnWareCheck == null) {
			btnWareCheck = new JButton();
			btnWareCheck.setBounds(new Rectangle(384, 1, 82, 25));
			btnWareCheck.setText("单耗检查");
			btnWareCheck.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (manualdeclearAction.isExistNullEmsHeadH2kVersion(
							new Request(CommonVars.getCurrUser()), emsHeadH2k)) {
						if (JOptionPane.showConfirmDialog(DgEmsHeadH2k.this,
								"是否删除没有料件的版本资料?", "提示",
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							manualdeclearAction.deleteEmsHeadH2kVersionWhileNoImg(
									new Request(CommonVars.getCurrUser()),
									emsHeadH2k);
							JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
									"已经成功删除没有料件的版本资料!");
						}
					} else {
						JOptionPane.showMessageDialog(DgEmsHeadH2k.this,
								"不存在没有料件的版本资料!");
					}

				}
			});
		}
		return btnWareCheck;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			javax.swing.JLabel jLabel30 = new JLabel();

			javax.swing.JLabel jLabel29 = new JLabel();

			javax.swing.JLabel jLabel28 = new JLabel();

			javax.swing.JLabel jLabel25 = new JLabel();

			javax.swing.JLabel jLabel16 = new JLabel();

			javax.swing.JLabel jLabel27 = new JLabel();

			javax.swing.JLabel jLabel26 = new JLabel();

			javax.swing.JLabel jLabel24 = new JLabel();

			javax.swing.JLabel jLabel23 = new JLabel();

			javax.swing.JLabel jLabel22 = new JLabel();

			javax.swing.JLabel jLabel21 = new JLabel();

			javax.swing.JLabel jLabel20 = new JLabel();

			javax.swing.JLabel jLabel19 = new JLabel();

			javax.swing.JLabel jLabel18 = new JLabel();

			javax.swing.JLabel jLabel17 = new JLabel();

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
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
			jPanel5.setBounds(new Rectangle(71, 35, 790, 409));
			jPanel5.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createBevelBorder(BevelBorder.LOWERED),
					"\u8868\u5934\u4fe1\u606f",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel5.add(jLabel, null);
			jPanel5.add(getTfEmsTpye(), null);
			jPanel5.add(jLabel1, null);
			jPanel5.add(getTfEmsState(), null);
			jPanel5.add(jLabel2, null);
			jPanel5.add(getTfDeclareType(), null);
			jPanel5.add(jLabel3, null);
			jPanel5.add(getTfCopEmsNo(), null);
			jPanel5.add(jLabel4, null);
			jPanel5.add(getTfEmsNo(), null);
			jPanel5.add(jLabel5, null);
			jPanel5.add(getTfSancEmsNo(), null);
			jPanel5.add(jLabel6, null);
			jPanel5.add(getTfTradeCode(), null);
			jPanel5.add(jLabel7, null);
			jPanel5.add(getTfTradeName(), null);
			jPanel5.add(jLabel8, null);
			jPanel5.add(getTfMachCode(), null);
			jPanel5.add(jLabel9, null);
			jPanel5.add(getTfMachName(), null);
			jPanel5.add(jLabel10, null);
			jPanel5.add(getTfTel(), null);
			jPanel5.add(jLabel11, null);
			jPanel5.add(getTfAddress(), null);
			jLabel.setText("帐册类型");
			jLabel.setBounds(new Rectangle(46, 28, 75, 20));
			jLabel1.setText("帐册状态");
			jLabel1.setBounds(new Rectangle(281, 25, 85, 23));
			jLabel2.setText("申请类型");
			jLabel2.setBounds(new Rectangle(528, 28, 82, 20));
			jLabel3.setText("企业内部编号");
			jLabel3.setBounds(new Rectangle(46, 59, 75, 20));
			jLabel4.setText("帐册编号");
			jLabel4.setBounds(new Rectangle(281, 56, 85, 23));
			jLabel5.setText("批文帐册号");
			jLabel5.setBounds(new Rectangle(528, 59, 82, 20));
			jLabel6.setText("经营单位代码");
			jLabel6.setBounds(new Rectangle(46, 89, 75, 20));
			jLabel7.setText("经营单位名称");
			jLabel7.setBounds(new Rectangle(281, 86, 85, 23));
			jLabel8.setText("收货单位代码");
			jLabel8.setBounds(new Rectangle(46, 118, 75, 20));
			jLabel9.setText("收货单位名称");
			jLabel9.setBounds(new Rectangle(281, 115, 85, 23));
			jLabel10.setText("电话号码");
			jLabel10.setBounds(new Rectangle(46, 179, 75, 20));
			jLabel11.setText("地址");
			jLabel11.setBounds(new Rectangle(281, 176, 85, 23));
			jLabel12.setBounds(46, 207, 75, 20);
			jLabel12.setText("开始有效期");
			jLabel13.setBounds(281, 204, 85, 23);
			jLabel13.setText("结束 有效期");
			jLabel14.setBounds(531, 207, 82, 20);
			jLabel14.setText("外商公司");
			jLabel15.setBounds(46, 149, 75, 20);
			jLabel15.setText("申请单位类型");
			jLabel17.setBounds(281, 146, 85, 23);
			jLabel17.setText("年生产加工能力");
			jLabel17.setForeground(new java.awt.Color(0, 51, 255));
			jLabel18.setBounds(479, 147, 38, 20);
			jLabel18.setText("万美元");
			jLabel18.setForeground(new java.awt.Color(0, 51, 255));
			jLabel19.setBounds(530, 149, 82, 20);
			jLabel19.setText("最大周转金额");
			jLabel19.setForeground(new java.awt.Color(0, 51, 255));
			jLabel20.setBounds(723, 146, 40, 20);
			jLabel20.setText("万美元");
			jLabel20.setForeground(new java.awt.Color(0, 51, 255));
			jLabel21.setBounds(46, 298, 75, 20);
			jLabel21.setText("录入员代号");
			jLabel22.setBounds(281, 295, 85, 23);
			jLabel22.setText("录入日期");
			jLabel23.setBounds(281, 325, 85, 23);
			jLabel23.setText("备案批准日期");
			jLabel24.setBounds(46, 328, 75, 20);
			jLabel24.setText("申报日期");
			jLabel26.setBounds(532, 328, 82, 20);
			jLabel26.setText("变更批准日期");
			jLabel27.setBounds(46, 364, 75, 20);
			jLabel27.setText("备注");
			jLabel16.setBounds(46, 239, 75, 20);
			jLabel16.setText("进出口岸");
			jLabel25.setBounds(46, 268, 75, 20);
			jLabel25.setText("征免性质");
			jLabel25.setForeground(new java.awt.Color(0, 51, 255));
			jLabel28.setBounds(281, 265, 85, 23);
			jLabel28.setText("保税方式");
			jLabel29.setBounds(531, 268, 82, 20);
			jLabel29.setText("加工种类");
			jLabel29.setForeground(new java.awt.Color(0, 51, 255));
			jLabel30.setBounds(532, 298, 82, 20);
			jLabel30.setText("批准证编号");

			jPanel5.add(jLabel12, null);
			jPanel5.add(getTfDeclareErType(), null);
			jPanel5.add(getCbbBeginDate(), null);
			jPanel5.add(jLabel13, null);
			jPanel5.add(getCbbEndDate(), null);
			jPanel5.add(jLabel14, null);
			jPanel5.add(jLabel15, null);
			jPanel5.add(jLabel17, null);
			jPanel5.add(jLabel18, null);
			jPanel5.add(jLabel19, null);
			jPanel5.add(jLabel20, null);
			jPanel5.add(jLabel21, null);
			jPanel5.add(getTfInputEr(), null);
			jPanel5.add(jLabel22, null);
			jPanel5.add(getTfInputDate(), null);
			jPanel5.add(jLabel23, null);
			jPanel5.add(getTfNewApprDate(), null);
			jPanel5.add(jLabel24, null);
			jPanel5.add(getTfDeclareDate(), null);
			jPanel5.add(jLabel26, null);
			jPanel5.add(getTfChangeApprDate(), null);
			jPanel5.add(jLabel27, null);
			jPanel5.add(getTfMachAbility(), null);
			jPanel5.add(getTfMaxTurnMoney(), null);
			jPanel5.add(getTfOutTradeCo(), null);
			jPanel5.add(jLabel16, null);
			jPanel5.add(getTfIePort(), null);
			jPanel5.add(getBtnIePort(), null);
			jPanel5.add(jLabel25, null);
			jPanel5.add(getTfLevyKind(), null);
			jPanel5.add(getBtnLevyKind(), null);
			jPanel5.add(jLabel28, null);
			jPanel5.add(jLabel29, null);
			jPanel5.add(getTfMachiningType(), null);
			jPanel5.add(getBtnMachiningType(), null);
			jPanel5.add(jLabel30, null);
			jPanel5.add(getTfEmsApprNo(), null);
			jPanel5.add(getTfNote(), null);
			jPanel5.add(getTfPayMode(), null);
			jPanel5.add(getBtnPayMode(), null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jButtonSupplementBOM
	 * 
	 * @author zyy
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonSupplementBOM() {
		if (jButtonSupplementBOM == null) {
			jButtonSupplementBOM = new JButton();
			jButtonSupplementBOM.setPreferredSize(new Dimension(70, 30));
			jButtonSupplementBOM.setText("补充BOM");
			jButtonSupplementBOM
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgSupplementBOM dgSupplementBOM = new DgSupplementBOM(
									emsHeadH2k, exgLastDate);
						}
					});
		}
		return jButtonSupplementBOM;
	}

} // @jve:decl-index=0:visual-constraint="10,5"
