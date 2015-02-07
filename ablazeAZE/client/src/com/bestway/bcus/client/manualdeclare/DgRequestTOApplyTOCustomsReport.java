package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.enc.DgApplyToCustomsBillList;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.enc.entity.TempRequestTOApplyTOCustomsReport;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.custom.common.DgImpExpRequestBill;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.JTableListModel.SortMouseListener;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author fhz
 */
@SuppressWarnings("unchecked")
public class DgRequestTOApplyTOCustomsReport extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private AttributiveCellTableModel tableModel = null;

	private List list = null;  //  @jve:decl-index=0:

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane1 = null;

	private JButton jButton = null;

	private List lsResult = null;  //  @jve:decl-index=0:

	private JButton jButton1 = null;

	private EncAction encAction = null;  //  @jve:decl-index=0:

	private JComboBox cbbScmCoc = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox ccbbBeginDate = null;

	private JLabel jLabel3 = null;

	private JCalendarComboBox ccbbEndDate = null;

	private JLabel jLabel4 = null;

	private JComboBox cbbBillNo = null;

	private JLabel jLabel51 = null;

	private JComboBox cbbBillType = null;

	private JLabel jLabel6 = null;

	private JComboBox cbbImpExpFlag = null;

	private JLabel jLabel511 = null;

	private JTextField tfBomNo = null;

	private JButton btnBomNo = null;

	private ManualDeclareAction manualDeclareAction = null; // @jve:decl-index=0:

	private JCheckBox CbbListUnitNo = null;

	private JLabel jLabel = null;

	private JLabel jLabel5 = null;

	private JTextField tfCustomDeclareCode = null;

	private JTextField tfCommSerial = null;

	private JButton btnRefApplyToCustomsBillList = null;

	private JButton btnImpExpCommodityInfo = null;

	/**
	 * This is the default constructor
	 */
	public DgRequestTOApplyTOCustomsReport() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(1000, 500);
		this.setTitle("大小清单差异分析");
		initUIComponents();
//		this.addWindowListener(new java.awt.event.WindowAdapter() {
//			public void windowOpened(java.awt.event.WindowEvent e) {
//				list = new Vector();
//				initTable(list);
//			}
//		});
	}
	@Override
	public void setVisible(boolean b) {
		if (b) {
			list = new Vector();
			initTable(list);
		}
		super.setVisible(b);
	}
	public void showFromDgImpCustomsRecord(boolean impExpFlag, String declareCustomCode, String commSerial) {
		if(impExpFlag) {
			cbbImpExpFlag.setSelectedIndex(1);
		} else {
			cbbImpExpFlag.setSelectedIndex(0);
		}
		ccbbBeginDate.setDate(null);
		tfCommSerial.setText(commSerial);
		tfCustomDeclareCode.setText(declareCustomCode);
		ShowFormControl.showForm(this);
		new find().start();
	}
	private void initUIComponents() {

		// 初始进出类型
		// this.cbbImpExpFlag.removeAllItems();
		this.cbbImpExpFlag.addItem(new ItemProperty(
				MaterielType.FINISHED_PRODUCT, "出口"));
		this.cbbImpExpFlag
				.addItem(new ItemProperty(MaterielType.MATERIEL, "进口"));
		this.cbbImpExpFlag.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbImpExpFlag);
		this.cbbImpExpFlag.setSelectedIndex(-1);

		// 初始单据类型
		initCbbBillType(null);

		// 初始单据号
		initCbbBillNo(null, null);

		// 初始客户供应商品
		initCbbScmCoc(null, null);

	}

	/**
	 * 根据进出口标志，初始化单据类型
	 * 
	 * @param impExpFlagCode
	 *            进出类型
	 */
	private void initCbbBillType(String impExpFlagCode) {
		cbbBillType.removeAllItems();
		if (impExpFlagCode == null) {
			// 进口
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_IMPORT), "料件进口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_IMPORT), "料件转厂"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_FACTORY_REWORK), "退厂返工"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.GENERAL_TRADE_IMPORT), "进料成品退换"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.IMPORT_EXG_BACK), "料件进口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.IMPORT_REPAIR_MATERIAL), "修理物品"));

			// 出口
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_EXPORT), "成品出口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_MATERIEL_EXPORT), "退料出口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REWORK_EXPORT), "返工复出"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMIAN_MATERIAL_BACK_PORT), "边角料退港"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES),
					"边角料内销"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.GENERAL_TRADE_EXPORT), "一般贸易出口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.EXPORT_MATERIAL_REBACK), "修理物品复出"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.EXPORT_EXG_REBACK), "进料成品退换复出"));
		} else if (impExpFlagCode.equals(MaterielType.MATERIEL)) {
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_IMPORT), "料件进口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_IMPORT), "料件转厂"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_FACTORY_REWORK), "退厂返工"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.GENERAL_TRADE_IMPORT), "进料成品退换"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.IMPORT_EXG_BACK), "料件进口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.IMPORT_REPAIR_MATERIAL), "修理物品"));
		} else if (impExpFlagCode.equals(MaterielType.FINISHED_PRODUCT)) {
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_EXPORT), "成品出口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_MATERIEL_EXPORT), "退料出口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REWORK_EXPORT), "返工复出"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMIAN_MATERIAL_BACK_PORT), "边角料退港"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES),
					"边角料内销"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.GENERAL_TRADE_EXPORT), "一般贸易出口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.EXPORT_MATERIAL_REBACK), "修理物品复出"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.EXPORT_EXG_REBACK), "进料成品退换复出"));
		}
		this.cbbBillType
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbBillType);
		this.cbbBillType.setSelectedIndex(-1);
	}

	/**
	 * 根据进出类型、单据类型，初始化单据号
	 * 
	 * @param impExpFlagCode
	 *            进出类型
	 * @param billTypeCode
	 *            单据类型
	 */
	private void initCbbBillNo(String impExpFlagCode, String billTypeCode) {
		cbbBillNo.removeAllItems();
		List list = encAction.findImpExpRequestBillByPara(new Request(
				CommonVars.getCurrUser(), true), impExpFlagCode, billTypeCode);
		this.cbbBillNo.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbBillNo.setRenderer(CustomBaseRender.getInstance().getRender(
				"billNo", "billNo", 170, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbBillNo, "billNo", "billNo", 340);
		cbbBillNo.setSelectedIndex(-1);

	}

	/**
	 * 根据进出类型、单据类型，初始化单据号
	 * 
	 * @param impExpFlagCode
	 *            进出类型
	 * @param billTypeCode
	 *            单据类型
	 */
	private void initCbbScmCoc(String impExpFlagCode, String billTypeCode) {
		cbbScmCoc.removeAllItems();
		List list = encAction.findScmCocsByPara(new Request(CommonVars
				.getCurrUser(), true), impExpFlagCode, billTypeCode);
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 150, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbScmCoc, "code", "name", 320);
		cbbScmCoc.setSelectedIndex(-1);
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
	 * 初始化数据Table
	 */
	private JTableListModel initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		tableModel = new AttributiveCellTableModel((MultiSpanCellTable) jTable,
				list, new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						// 申请单
						list.add(addColumn("申请单号",
								"impExpCommodityInfo.impExpRequestBill.billNo",
								100));//1
						list.add(addColumn("制单号",
								"impExpCommodityInfo.makeBillNo", 100));//2
						list
								.add(addColumn(
										"单据类型",
										"impExpCommodityInfo.impExpRequestBill.billType",
										80));//3
						list.add(addColumn("BOM编号",
								"impExpCommodityInfo.materiel.ptNo", 100));//4
						list.add(addColumn("品名规格",
								"impExpCommodityInfo.extendMemo", 150));//5
						list.add(addColumn("数量",
								"impExpCommodityInfo.quantity", 80));//6
						list
								.add(addColumn(
										"单位",
										"impExpCommodityInfo.materiel.calUnit.name",
										50));//7
						list.add(addColumn("单价",
								"impExpCommodityInfo.unitPrice", 80));//8
						list.add(addColumn("金额",
								"impExpCommodityInfo.amountPrice", 100));//9
						list
								.add(addColumn(
										"是否已生效",
										"impExpCommodityInfo.impExpRequestBill.isAvailability",
										70));//10
						list
								.add(addColumn(
										"生效日期",
										"impExpCommodityInfo.impExpRequestBill.beginAvailability",
										100));//11
						list.add(addColumn("扩展备注","impExpCommodityInfo.extendMemo" ,150));//12
						list
								.add(addColumn(
										"公司名称",
										"impExpCommodityInfo.impExpRequestBill.scmCoc.name",
										150));//13
						
						// 清单
						list
								.add(addColumn(
										"内部清单号",
										"atcMergeBeforeComInfo.afterComInfo.billList.listNo",
										100));//14
						list
								.add(addColumn(
										"统一编号",
										"atcMergeBeforeComInfo.afterComInfo.billList.listUniteNo",
										100));//15
						list
								.add(addColumn(
										"清单类型",
										"atcMergeBeforeComInfo.afterComInfo.billList.impExpType",
										80));//16
						list
								.add(addColumn(
										"帐册序号",
										"atcMergeBeforeComInfo.afterComInfo.emsSerialNo",
										50));//17
						list
								.add(addColumn(
										"商品编码",
										"atcMergeBeforeComInfo.afterComInfo.complex.code",
										80));//18
						list.add(addColumn("品名规格",
								"atcMergeBeforeComInfo.extendMemo", 150));//19
						list.add(addColumn("数量",
								"atcMergeBeforeComInfo.declaredAmount", 80));//20
						list.add(addColumn("申报单位",
								"atcMergeBeforeComInfo.afterComInfo.unit.name",
								50));//21
						list.add(addColumn("产销国",
								"atcMergeBeforeComInfo.salesCountry.name", 150));//22
						list.add(addColumn("净重",
								"atcMergeBeforeComInfo.netWeight", 80));//23
						list.add(addColumn("毛重",
								"atcMergeBeforeComInfo.grossWeight", 80));//24
						list.add(addColumn("单价",
								"atcMergeBeforeComInfo.declaredPrice", 80));//25
						list.add(addColumn("金额",
								"atcMergeBeforeComInfo.totalPrice", 120));//26
						// list.add(addColumn("生效",
						// "atcMergeBeforeComInfo.afterComInfo.billList.", 80));
						// 报关单
						list.add(addColumn("备案序号",
								"customsCommInfo.commSerialNo", 50));//27
						list
								.add(addColumn(
										"报关单号",
										"customsCommInfo.baseCustomsDeclaration.customsDeclarationCode",
										120));//28
						list
								.add(addColumn(
										"统一编号",
										"customsCommInfo.baseCustomsDeclaration.unificationCode",
										120));//29
						list
								.add(addColumn(
										"进出口类型",
										"customsCommInfo.baseCustomsDeclaration.impExpType",
										80));//30
						list.add(addColumn("名称", "customsCommInfo.commName",
								80));//31
						list.add(addColumn("规格", "customsCommInfo.commSpec",
								80));//32
						list.add(addColumn("数量", "customsCommInfo.commAmount",
								80));//33
						list.add(addColumn("单位", "customsCommInfo.unit.name",
								50));//34
						list.add(addColumn("单价",
								"customsCommInfo.commUnitPrice", 80));//35
						list.add(addColumn("金额",
								"customsCommInfo.commTotalPrice", 100));//36
						list.add(addColumn("集装箱号","customsCommInfo.baseCustomsDeclaration.containerNum",100));//37
						list.add(addColumn("原产国/抵运国",
								"customsCommInfo.country.name", 100));//38
						list
								.add(addColumn(
										"申报日期",
										"customsCommInfo.baseCustomsDeclaration.declarationDate",
										100));//39
						list
								.add(addColumn(
										"进出口日期",
										"customsCommInfo.baseCustomsDeclaration.impExpDate",
										100));//40
						list
								.add(addColumn(
										"是否已生效",
										"customsCommInfo.baseCustomsDeclaration.effective",
										80));//41
						return list;
					}
				});

		// 截取小数位
		String reportDecimalLength = manualDeclareAction.getBpara(new Request(
				CommonVars.getCurrUser(), true),
				BcusParameter.ReportDecimalLength);
		Integer decimalLength = 2;
		if (reportDecimalLength != null)
			decimalLength = Integer.valueOf(reportDecimalLength);

		List<JTableListColumn> cms = tableModel.getColumns();
		cms.get(6).setFractionCount(decimalLength);
		cms.get(8).setFractionCount(decimalLength);
		cms.get(9).setFractionCount(decimalLength);
		CommonVars.castMultiplicationValue(jTable, 9, 6, 8, decimalLength);

		cms.get(20).setFractionCount(decimalLength);
		cms.get(23).setFractionCount(decimalLength);
		cms.get(24).setFractionCount(decimalLength);
		cms.get(25).setFractionCount(decimalLength);
		cms.get(26).setFractionCount(decimalLength);
		CommonVars.castMultiplicationValue(jTable, 26, 25, 20, decimalLength);

		cms.get(33).setFractionCount(decimalLength);
		cms.get(35).setFractionCount(decimalLength);
		cms.get(36).setFractionCount(decimalLength);
		CommonVars.castMultiplicationValue(jTable, 36, 33, 35, decimalLength);

		jTable.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

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
						switch (Integer.valueOf(String.valueOf(value))) {
						// 进口
						case ImpExpType.DIRECT_IMPORT:
							returnValue = "料件进口";
							break;
						case ImpExpType.TRANSFER_FACTORY_IMPORT:
							returnValue = "料件转厂";
							break;
						case ImpExpType.BACK_FACTORY_REWORK:
							returnValue = "退厂返工";
							break;
						case ImpExpType.GENERAL_TRADE_IMPORT:
							returnValue = "进料成品退换";
							break;
						case ImpExpType.IMPORT_EXG_BACK:
							returnValue = "料件进口";
							break;
						case ImpExpType.IMPORT_REPAIR_MATERIAL:
							returnValue = "修理物品";
							break;
						// 出口
						case ImpExpType.DIRECT_EXPORT:
							returnValue = "成品出口";
							break;
						case ImpExpType.TRANSFER_FACTORY_EXPORT:
							returnValue = "转厂出口";
							break;
						case ImpExpType.BACK_MATERIEL_EXPORT:
							returnValue = "退料出口";
							break;
						case ImpExpType.REWORK_EXPORT:
							returnValue = "返工复出";
							break;
						case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
							returnValue = "边角料退港";
							break;
						case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
							returnValue = "边角料内销";
							break;
						case ImpExpType.GENERAL_TRADE_EXPORT:
							returnValue = "一般贸易出口";
							break;
						case ImpExpType.EXPORT_MATERIAL_REBACK:
							returnValue = "修理物品复出";
							break;
						case ImpExpType.EXPORT_EXG_REBACK:
							returnValue = "进料成品退换复出";
							break;
						default:
							returnValue = "没有对应的类型";
							break;
						}
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(16).setCellRenderer(
				new DefaultTableCellRenderer() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

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
						switch (Integer.valueOf(String.valueOf(value))) {
						// 进口
						case ImpExpType.DIRECT_IMPORT:
							returnValue = "料件进口";
							break;
						case ImpExpType.TRANSFER_FACTORY_IMPORT:
							returnValue = "料件转厂";
							break;
						case ImpExpType.BACK_FACTORY_REWORK:
							returnValue = "退厂返工";
							break;
						case ImpExpType.GENERAL_TRADE_IMPORT:
							returnValue = "一般贸易进口";
							break;
						case ImpExpType.IMPORT_EXG_BACK:
							returnValue = "进料成品退换";
							break;
						case ImpExpType.IMPORT_REPAIR_MATERIAL:
							returnValue = "修理物品";
							break;
						// 出口
						case ImpExpType.DIRECT_EXPORT:
							returnValue = "成品出口";
							break;
						case ImpExpType.TRANSFER_FACTORY_EXPORT:
							returnValue = "转厂出口";
							break;
						case ImpExpType.BACK_MATERIEL_EXPORT:
							returnValue = "退料出口";
							break;
						case ImpExpType.REWORK_EXPORT:
							returnValue = "返工复出";
							break;
						case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
							returnValue = "边角料退港";
							break;
						case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
							returnValue = "边角料内销";
							break;
						case ImpExpType.GENERAL_TRADE_EXPORT:
							returnValue = "一般贸易出口";
							break;
						case ImpExpType.EXPORT_MATERIAL_REBACK:
							returnValue = "修理物品复出";
							break;
						case ImpExpType.EXPORT_EXG_REBACK:
							returnValue = "进料成品退换复出";
							break;
						default:
							returnValue = "没有对应的类型";
							break;
						}
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(30).setCellRenderer(
				new DefaultTableCellRenderer() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

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
						switch (Integer.valueOf(String.valueOf(value))) {
						// 进口
						case ImpExpType.DIRECT_IMPORT:
							returnValue = "料件进口";
							break;
						case ImpExpType.TRANSFER_FACTORY_IMPORT:
							returnValue = "料件转厂";
							break;
						case ImpExpType.BACK_FACTORY_REWORK:
							returnValue = "退厂返工";
							break;
						case ImpExpType.REMAIN_FORWARD_IMPORT:
							returnValue = "余料结转进口";
							break;
						case ImpExpType.MATERIAL_DOMESTIC_SALES:
							returnValue = "海关批准内销";
							break;
						// 出口
						case ImpExpType.DIRECT_EXPORT:
							returnValue = "成品出口";
							break;
						case ImpExpType.TRANSFER_FACTORY_EXPORT:
							returnValue = "转厂出口";
							break;
						case ImpExpType.BACK_MATERIEL_EXPORT:
							returnValue = "退料出口";
							break;
						case ImpExpType.REWORK_EXPORT:
							returnValue = "返工复出";
							break;
						case ImpExpType.REMAIN_FORWARD_EXPORT:
							returnValue = "余料结转";
							break;
						default:
							returnValue = "没有对应的类型";
							break;
						}
						return returnValue;
					}
				});

		jTable.getColumnModel().getColumn(10).setCellRenderer(
				new DefaultTableCellRenderer() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "否";
						if (value.toString().equals("true")) {
							returnValue = "是";
						} else
							returnValue = "否";
						return returnValue;
					}

				});

		jTable.getColumnModel().getColumn(41).setCellRenderer(
				new DefaultTableCellRenderer() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "否";
						if (value.toString().equals("true")) {
							returnValue = "是";
						} else
							returnValue = "否";
						return returnValue;
					}
				});
		if (!CbbListUnitNo.isSelected()) {
			jTable.getColumnModel().removeColumn(
					jTable.getColumnModel().getColumn(29));
			jTable.getColumnModel().removeColumn(
					jTable.getColumnModel().getColumn(15));
			((MultiSpanCellTable) jTable).combineRows(25, new int[] { 26, 27,
					28, 29, 30, 31, 32, 33, 34, 35, 36, 37 ,38 ,39 });
		} else {
			jTable.getColumnModel().getColumn(15).setCellRenderer(
					new DefaultTableCellRenderer() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean cellHasFocus, int row, int col) {
							super.getTableCellRendererComponent(table, value,
									isSelected, cellHasFocus, row, col);
							setForeground(Color.blue);
							return this;
						}
					});
			jTable.getColumnModel().getColumn(29).setCellRenderer(
					new DefaultTableCellRenderer() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean cellHasFocus, int row, int col) {
							super.getTableCellRendererComponent(table, value,
									isSelected, cellHasFocus, row, col);
							setForeground(Color.blue);
							return this;
						}
					});
//			((MultiSpanCellTable) jTable).combineRows(27, new int[] {
//					27, 28, 29, 30, 31, 32, 33, 34, 35, 36 ,37 ,38 ,39, 40, 41});
		}
		
//		((MultiSpanCellTable) jTable).combineRows(new int[]{14,20},new int[] {14,20,22}, new int[] { 14,
//				15, 16, 17, 18, 19, 20, 21, 22, 23, 24 , 25, 26});
		removeMouseListener(jTable);
		
		TableColumnModel cm = jTable.getColumnModel();
		if (CbbListUnitNo.isSelected()) {
			ColumnGroup gRequest = new ColumnGroup("申请单");
			gRequest.add(cm.getColumn(1));
			gRequest.add(cm.getColumn(2));
			gRequest.add(cm.getColumn(3));
			gRequest.add(cm.getColumn(4));
			gRequest.add(cm.getColumn(5));
			gRequest.add(cm.getColumn(6));
			gRequest.add(cm.getColumn(7));
			gRequest.add(cm.getColumn(8));
			gRequest.add(cm.getColumn(9));
			gRequest.add(cm.getColumn(10));
			gRequest.add(cm.getColumn(11));
			gRequest.add(cm.getColumn(12));
			gRequest.add(cm.getColumn(13));
			ColumnGroup gApply = new ColumnGroup("清单");
			gApply.add(cm.getColumn(14));
			gApply.add(cm.getColumn(15));
			gApply.add(cm.getColumn(16));
			gApply.add(cm.getColumn(17));
			gApply.add(cm.getColumn(18));
			gApply.add(cm.getColumn(19));
			gApply.add(cm.getColumn(20));
			gApply.add(cm.getColumn(21));
			gApply.add(cm.getColumn(22));
			gApply.add(cm.getColumn(23));
			gApply.add(cm.getColumn(24));
			gApply.add(cm.getColumn(25));
			gApply.add(cm.getColumn(26));
			ColumnGroup gCustoms = new ColumnGroup("报关单");
			gCustoms.add(cm.getColumn(27));
			gCustoms.add(cm.getColumn(28));
			gCustoms.add(cm.getColumn(29));
			gCustoms.add(cm.getColumn(30));
			gCustoms.add(cm.getColumn(31));
			gCustoms.add(cm.getColumn(32));
			gCustoms.add(cm.getColumn(33));
			gCustoms.add(cm.getColumn(34));
			gCustoms.add(cm.getColumn(35));
			gCustoms.add(cm.getColumn(36));
			gCustoms.add(cm.getColumn(37));
			gCustoms.add(cm.getColumn(38));
			gCustoms.add(cm.getColumn(39));
			gCustoms.add(cm.getColumn(40));
			gCustoms.add(cm.getColumn(41));
			GroupableTableHeader header = (GroupableTableHeader) jTable
					.getTableHeader();
			header.addColumnGroup(gRequest);
			header.addColumnGroup(gApply);
			header.addColumnGroup(gCustoms);
		} else {
			ColumnGroup gRequest = new ColumnGroup("申请单");
			gRequest.add(cm.getColumn(1));
			gRequest.add(cm.getColumn(2));
			gRequest.add(cm.getColumn(3));
			gRequest.add(cm.getColumn(4));
			gRequest.add(cm.getColumn(5));
			gRequest.add(cm.getColumn(6));
			gRequest.add(cm.getColumn(7));
			gRequest.add(cm.getColumn(8));
			gRequest.add(cm.getColumn(9));
			gRequest.add(cm.getColumn(10));
			gRequest.add(cm.getColumn(11));
			gRequest.add(cm.getColumn(12));
			gRequest.add(cm.getColumn(13));
			ColumnGroup gApply = new ColumnGroup("清单");
			gApply.add(cm.getColumn(14));
			gApply.add(cm.getColumn(15));
			gApply.add(cm.getColumn(16));
			gApply.add(cm.getColumn(17));
			gApply.add(cm.getColumn(18));
			gApply.add(cm.getColumn(19));
			gApply.add(cm.getColumn(20));
			gApply.add(cm.getColumn(21));
			gApply.add(cm.getColumn(22));
			gApply.add(cm.getColumn(23));
			gApply.add(cm.getColumn(24));
			gApply.add(cm.getColumn(25));
			ColumnGroup gCustoms = new ColumnGroup("报关单");
			gCustoms.add(cm.getColumn(26));
			gCustoms.add(cm.getColumn(27));
			gCustoms.add(cm.getColumn(28));
			gCustoms.add(cm.getColumn(29));
			gCustoms.add(cm.getColumn(30));
			gCustoms.add(cm.getColumn(31));
			gCustoms.add(cm.getColumn(32));
			gCustoms.add(cm.getColumn(33));
			gCustoms.add(cm.getColumn(34));
			gCustoms.add(cm.getColumn(35));
			gCustoms.add(cm.getColumn(36));
			gCustoms.add(cm.getColumn(37));
			gCustoms.add(cm.getColumn(38));
			gCustoms.add(cm.getColumn(39));
			GroupableTableHeader header = (GroupableTableHeader) jTable
					.getTableHeader();
			header.addColumnGroup(gRequest);
			header.addColumnGroup(gApply);
			header.addColumnGroup(gCustoms);
		}
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return tableModel;
		}
		tableModel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
		return tableModel;
	}
	
	public void removeMouseListener(JTable table) {
		MouseListener[] mouseListeners = table.getTableHeader()
				.getMouseListeners();
		for (int i = 0; i < mouseListeners.length; i++) {
			if (mouseListeners[i] instanceof SortMouseListener) {
				table.getTableHeader().removeMouseListener(mouseListeners[i]);
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
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(100);
			jSplitPane.setDividerSize(0);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
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
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(650, 37, 60, 23));
			jLabel5.setText("账册序号");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(650, 7, 60, 23));
			jLabel.setText("报关单号");
			jLabel511 = new JLabel();
			jLabel511.setBounds(new Rectangle(3, 7, 57, 23));
			jLabel511.setText("进出类型");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(247, 7, 66, 23));
			jLabel6.setText("BOM编号");
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(3, 37, 56, 23));
			jLabel51.setText("单据类型");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(3, 67, 56, 23));
			jLabel4.setText("单据号");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(455, 67, 25, 23));
			jLabel3.setText("至");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(247, 67, 67, 23));
			jLabel2.setText("生效日期由");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(247, 37, 66, 23));
			jLabel1.setText("客户供应商");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getCbbScmCoc(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getCcbbBeginDate(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getCcbbEndDate(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getCbbBillNo(), null);
			jPanel.add(jLabel51, null);
			jPanel.add(getCbbBillType(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(getCbbImpExpFlag(), null);
			jPanel.add(jLabel511, null);
			jPanel.add(getTfBomNo(), null);
			jPanel.add(getBtnBomNo(), null);
			jPanel.add(getCbbListUnitNo(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel5, null);
			jPanel.add(getTfCustomDeclareCode(), null);
			jPanel.add(getTfCommSerial(), null);
			jPanel.add(getBtnRefApplyToCustomsBillList(), null);
			jPanel.add(getBtnImpExpCommodityInfo(), null);
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
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
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
			jTable = new MultiSpanCellTable();
			
		}
		return jTable;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(862, 7, 94, 23);
			jButton.setText("查询");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new find().start();
				}
			});
		}
		return jButton;
	}

	class find extends Thread {

		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				Date beginDate = ccbbBeginDate.getDate();// 开始日期
				Date endDate = ccbbEndDate.getDate(); // 结束日期
				ScmCoc scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();// 客户供应商
				String impExpFlagCode = null;// 进出类型Code
				String billTypeCode = null;// 单据类型Code
				if (cbbBillType.getSelectedItem() != null) {
					billTypeCode = ((ItemProperty) cbbBillType
							.getSelectedItem()).getCode();
				}
				if (cbbImpExpFlag.getSelectedItem() != null) {
					impExpFlagCode = ((ItemProperty) cbbImpExpFlag
							.getSelectedItem()).getCode();
				}

				ImpExpRequestBill impExpRequestBill = null;
				if (cbbBillNo.getSelectedItem() != null) {
					impExpRequestBill = (ImpExpRequestBill) cbbBillNo
							.getSelectedItem();
				}

				String bomNo = null;
				if (tfBomNo.getText() != null && !tfBomNo.getText().equals(""))
					bomNo = tfBomNo.getText();
				if (CbbListUnitNo.isSelected()) {
					lsResult = encAction.getRequestTOApplyTOCustomsReport(
							new Request(CommonVars.getCurrUser()),
							impExpFlagCode, billTypeCode, impExpRequestBill,
							bomNo, scmCoc, beginDate, endDate, true, tfCustomDeclareCode.getText(), 
							tfCommSerial.getText());
				} else {
					lsResult = encAction.getRequestTOApplyTOCustomsReport(
							new Request(CommonVars.getCurrUser()),
							impExpFlagCode, billTypeCode, impExpRequestBill,
							bomNo, scmCoc, beginDate, endDate, false, tfCustomDeclareCode.getText(), 
							tfCommSerial.getText());
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(
						DgRequestTOApplyTOCustomsReport.this, "获取数据失败：！"
								+ e.getMessage(), "提示", 2);
			} finally {
				initTable(lsResult);
			}
		}
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(862, 37, 94, 23);
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(new Rectangle(316, 37, 303, 23));
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCcbbBeginDate() {
		if (ccbbBeginDate == null) {
			ccbbBeginDate = new JCalendarComboBox();
			ccbbBeginDate.setBounds(new Rectangle(316, 67, 137, 23));
		}
		return ccbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCcbbEndDate() {
		if (ccbbEndDate == null) {
			ccbbEndDate = new JCalendarComboBox();
			ccbbEndDate.setBounds(new Rectangle(483, 67, 137, 23));
		}
		return ccbbEndDate;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBillNo() {
		if (cbbBillNo == null) {
			cbbBillNo = new JComboBox();
			cbbBillNo.setBounds(new Rectangle(61, 67, 166, 23));
			cbbBillNo.setSelectedIndex(-1);

		}
		return cbbBillNo;
	}

	/**
	 * This method initializes cbbProjectType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBillType() {
		if (cbbBillType == null) {
			cbbBillType = new JComboBox();
			cbbBillType.setBounds(new Rectangle(61, 37, 166, 23));
			cbbBillType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					String impExpFlagCode = null;// 进出类型Code
					String billTypeCode = null;// 单据类型Code
					if (cbbImpExpFlag.getSelectedItem() != null)
						impExpFlagCode = ((ItemProperty) cbbImpExpFlag
								.getSelectedItem()).getCode();
					if (cbbBillType.getSelectedItem() != null)
						billTypeCode = ((ItemProperty) cbbBillType
								.getSelectedItem()).getCode();
					initCbbBillNo(impExpFlagCode, billTypeCode);
					initCbbScmCoc(impExpFlagCode, billTypeCode);
				}
			});
		}
		return cbbBillType;
	}

	/**
	 * This method initializes cbbProjectType1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpFlag() {
		if (cbbImpExpFlag == null) {
			cbbImpExpFlag = new JComboBox();
			cbbImpExpFlag.setBounds(new Rectangle(61, 7, 166, 23));
			cbbImpExpFlag.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					String impExpFlagCode = null;// 进出类型Code
					String billTypeCode = null;// 单据类型Code
					if (cbbImpExpFlag.getSelectedItem() != null)
						impExpFlagCode = ((ItemProperty) cbbImpExpFlag
								.getSelectedItem()).getCode();
					if (cbbBillType.getSelectedItem() != null)
						billTypeCode = ((ItemProperty) cbbBillType
								.getSelectedItem()).getCode();
					initCbbBillType(impExpFlagCode);
					initCbbScmCoc(impExpFlagCode, billTypeCode);

				}
			});
		}
		return cbbImpExpFlag;
	}

	/**
	 * This method initializes tfComplexCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBomNo() {
		if (tfBomNo == null) {
			tfBomNo = new JTextField();
			tfBomNo.setBounds(new Rectangle(316, 7, 137, 23));
		}
		return tfBomNo;
	}

	/**
	 * This method initializes btnComplexCode
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBomNo() {
		if (btnBomNo == null) {
			btnBomNo = new JButton();
			btnBomNo.setBounds(new Rectangle(453, 7, 22, 23));
			btnBomNo.setText("...");
			btnBomNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String impExpFlagCode = null;// 进出类型Code
					String billTypeCode = null;// 单据类型Code
					ImpExpRequestBill impExpRequestBill = null;// 申请单据
					if (cbbImpExpFlag.getSelectedItem() != null)
						impExpFlagCode = ((ItemProperty) cbbImpExpFlag
								.getSelectedItem()).getCode();// 进出类型Code
					if (cbbBillType.getSelectedItem() != null)
						billTypeCode = ((ItemProperty) cbbBillType
								.getSelectedItem()).getCode();// 单据类型Code
					if (cbbBillNo.getSelectedItem() != null)
						impExpRequestBill = (ImpExpRequestBill) cbbBillNo
								.getSelectedItem();// 单据

					List dataSource = encAction
							.getDistinctImpExpCommodityInfoByName(new Request(
									CommonVars.getCurrUser()), impExpFlagCode,
									billTypeCode, impExpRequestBill, null,
									null, null, null);
					Object[] obj = (Object[]) CommonQuery.getInstance()
							.getImpExpCommodityInfoByPara(dataSource, "申请单资料");
					if (obj != null)
						tfBomNo.setText(String.valueOf(obj[0]));
				}
			});
		}
		return btnBomNo;
	}

	/**
	 * This method initializes CbbListUnitNo
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbbListUnitNo() {
		if (CbbListUnitNo == null) {
			CbbListUnitNo = new JCheckBox();
			CbbListUnitNo.setForeground(java.awt.Color.red);
			CbbListUnitNo.setBounds(new Rectangle(509, 6, 109, 21));
			CbbListUnitNo.setText("是否按统一编号");
		}
		return CbbListUnitNo;
	}

	/**
	 * This method initializes tfCustomDeclareCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfCustomDeclareCode() {
		if (tfCustomDeclareCode == null) {
			tfCustomDeclareCode = new JTextField();
			tfCustomDeclareCode.setBounds(new Rectangle(718, 7, 120, 23));
		}
		return tfCustomDeclareCode;
	}

	/**
	 * This method initializes tfCommSerial	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfCommSerial() {
		if (tfCommSerial == null) {
			tfCommSerial = new JTextField();
			tfCommSerial.setBounds(new Rectangle(719, 37, 120, 23));
		}
		return tfCommSerial;
	}

	/**
	 * This method initializes btnRefApplyToCustomsBillList	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRefApplyToCustomsBillList() {
		if (btnRefApplyToCustomsBillList == null) {
			btnRefApplyToCustomsBillList = new JButton();
			btnRefApplyToCustomsBillList.setBounds(new Rectangle(719, 67, 94, 23));
			btnRefApplyToCustomsBillList.setText("关联清单");
			btnRefApplyToCustomsBillList
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgApplyToCustomsBillList dg = new DgApplyToCustomsBillList();
							TempRequestTOApplyTOCustomsReport tmp = (TempRequestTOApplyTOCustomsReport) tableModel.getCurrentRow();
							dg.setVisible(tmp.getAtcMergeBeforeComInfo().getAfterComInfo()
									.getBillList().getListNo(), tmp.getAtcMergeBeforeComInfo().getAfterComInfo()
									.getBillList().getEmsHeadH2k(), ImpExpFlag.IMPORT);
						}
					});
		}
		return btnRefApplyToCustomsBillList;
	}

	/**
	 * This method initializes btnImpExpCommodityInfo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnImpExpCommodityInfo() {
		if (btnImpExpCommodityInfo == null) {
			btnImpExpCommodityInfo = new JButton();
			btnImpExpCommodityInfo.setBounds(new Rectangle(862, 67, 94, 23));
			btnImpExpCommodityInfo.setText("关联申请单");
			btnImpExpCommodityInfo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					TempRequestTOApplyTOCustomsReport tmp = (TempRequestTOApplyTOCustomsReport) tableModel.getCurrentRow();
					DgImpExpRequestBill dg = new DgImpExpRequestBill();
					
					dg.showWithParams(tmp.getImpExpCommodityInfo().getImpExpRequestBill().getBillNo(),
							tmp.getImpExpCommodityInfo().getImpExpRequestBill().getBillType(), 
							false, ProjectType.BCUS, -1);
				}
			});
		}
		return btnImpExpCommodityInfo;
	}
}
