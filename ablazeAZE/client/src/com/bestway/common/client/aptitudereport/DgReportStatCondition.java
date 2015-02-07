package com.bestway.common.client.aptitudereport;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import java.awt.Rectangle;

import javax.swing.ComboBoxEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.client.common.CheckBoxListCellRendererNotCode;
import com.bestway.bcus.client.common.CheckBoxListItem;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.SteppedMetalComboBoxUI;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.aptitudereport.action.AptitudeReportAction;
import com.bestway.common.aptitudereport.entity.FiltCondition;
import com.bestway.common.aptitudereport.entity.GroupValue;
import com.bestway.common.aptitudereport.entity.ReportField;
import com.bestway.common.aptitudereport.entity.SelectCondition;
import com.bestway.common.aptitudereport.entity.StatTypeValue;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgReportStatCondition extends JDialogBase {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel = null;

	private JComboBox cbbAimObject = null;

	private JLabel jLabel1 = null;

	private JTextField tfReportName = null;

	private JButton btnClose = null;

	private JButton btnEdit = null;

	private JButton btnSave = null;

	private JPanel jPanel3 = null;

	private JPanel jPanel5 = null;

	private JPanel jPanel6 = null;

	private JScrollPane jScrollPane2 = null;

	private JButton btnAnd = null;

	private JButton btnOr = null;

	private JButton btnDel1 = null;

	private JComboBox cbbFieldName = null;

	private JComboBox cbbOperator = null;

	private JTextField tfValue = null;

	private JButton jButton11 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JComboBox cbbGroupingColumn = null;

	private JComboBox cbbStatColumn = null;

	private JPanel jPanel7 = null;

	private JComboBox cbbStatValue = null;

	private JComboBox cbbOperator1 = null;

	private JTextField tfStatValue = null;

	private int dataState = DataState.BROWSE;

	private AptitudeReportAction aptitudeReportAction = null;

	private SelectCondition selectCondition = null; // 表头 // @jve:decl-index=0:

	private Vector<ReportField> vectorSource = new Vector<ReportField>(); // @jve:decl-index=0:

	private Vector<ReportField> vectorDest = new Vector<ReportField>(); // @jve:decl-index=0:

	private Vector<FiltCondition> vectorCondition = new Vector<FiltCondition>(); // @jve:decl-index=0:

	private JTableListModel tableModel = null;

	private boolean isAdd = true;

	private JTableListModel tableModelDetail = null;

	private ToolsAction toolsAction = null;

	private GroupValue groupValue = null; // @jve:decl-index=0:

	private boolean isMasterTable = true;

	private int dataStateReportQuery = DataState.BROWSE;

	private List<FiltCondition> addFiltConditionList = null; // @jve:decl-index=0:

	private List<FiltCondition> removeFiltConditionList = null; // @jve:decl-index=0:

	private List<GroupValue> groupValueByStatTypeList = null; // @jve:decl-index=0:

	private List<StatTypeValue> statTypeValueList = null; // @jve:decl-index=0:

	private JList listFiltCondition = null;

	private String logic = "and"; // @jve:decl-index=0:

	private JPanel jPanel8 = null;

	private JCheckBox cbSum = null;

	private JCheckBox cbAvg = null;

	private JCheckBox cbMin = null;

	private JCheckBox cbMax = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private String dataType = null; // @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public DgReportStatCondition() {
		super();
		toolsAction = (ToolsAction) CommonVars.getApplicationContext().getBean(
				"toolsAction");
		aptitudeReportAction = (AptitudeReportAction) CommonVars
				.getApplicationContext().getBean("aptitudeReportAction");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(630, 375);
		this.setTitle("报表查询条件");
		this.setPreferredSize(new Dimension(730, 550));
		this.setContentPane(getJContentPane());

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
			jContentPane.setPreferredSize(new Dimension(730, 350));
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJPanel3(), BorderLayout.CENTER);
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
			jToolBar.setPreferredSize(new Dimension(730, 36));
			jToolBar.add(getJPanel());
		}
		return jToolBar;
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
			jPanel.add(getJPanel1(), BorderLayout.WEST);
			jPanel.add(getJPanel2(), BorderLayout.CENTER);
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
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(187, 4, 57, 25));
			jLabel1.setText("报表名称");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(10, 3, 53, 23));
			jLabel.setText("数据来源");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setPreferredSize(new Dimension(400, 36));
			jPanel1.add(jLabel, null);
			jPanel1.add(getCbbAimObject(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getTfReportName(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setPreferredSize(new Dimension(200, 34));
			jPanel2.add(getBtnSave(), null);
			jPanel2.add(getBtnEdit(), null);
			jPanel2.add(getBtnClose(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes cbbAimObject
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAimObject() {
		if (cbbAimObject == null) {
			cbbAimObject = new JComboBox();
			cbbAimObject.setBounds(new Rectangle(63, 3, 123, 26));
			//
			// model
			//

			cbbAimObject.addItem(new ItemProperty("  ", "  "));

			cbbAimObject.addItem(new ItemProperty(
					"com.bestway.bcus.cas.bill.entity.BillDetailMateriel",
					"料件单据"));
			cbbAimObject.addItem(new ItemProperty(
					"com.bestway.bcus.cas.bill.entity.BillDetailProduct",
					"成品单据"));
			cbbAimObject.addItem(new ItemProperty(
					"com.bestway.bcus.cas.bill.entity.BillDetailFixture",
					"设备单据"));
			cbbAimObject.addItem(new ItemProperty(
					"com.bestway.bcus.cas.bill.entity.BillDetailHalfProduct",
					"半成品单据"));
			cbbAimObject.addItem(new ItemProperty(
					"com.bestway.bcus.cas.bill.entity.BillDetailRemainProduct",
					"残次品单据"));
			cbbAimObject
					.addItem(new ItemProperty(
							"com.bestway.bcus.cas.bill.entity.BillDetailLeftoverMateriel",
							"边角料单据"));
			cbbAimObject
					.addItem(new ItemProperty(
							"com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo",
							"电子手册报关单"));
			cbbAimObject
					.addItem(new ItemProperty(
							"com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo",
							"纸质手册报关单"));
			cbbAimObject.addItem(new ItemProperty(
					"com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo",
					"电子帐册报关单"));
			cbbAimObject.addItem(new ItemProperty(
					"com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo",
					"电子帐册报关清单"));
			cbbAimObject
					.addItem(new ItemProperty(
							"com.bestway.dzsc.customslist.entity.DzscBillListBeforeCommInfo",
							"电子手册报关清单"));

			cbbAimObject.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object value = ((ItemProperty) cbbAimObject
							.getSelectedItem()).getCode();
					String trageClassName = String.valueOf(value);
					cbbFieldName.removeAllItems();
					cbbGroupingColumn.removeAllItems();
					cbbStatColumn.removeAllItems();
//					cbbStatValue.removeAllItems();
					cbSum.setSelected(false);
					cbAvg.setSelected(false);
					cbMin.setSelected(false);
					cbMax.setSelected(false);

					try {
						Object obj = Class.forName(trageClassName)
								.newInstance();
						int index = cbbAimObject.getSelectedIndex();
						statTypeValueList = new ArrayList<StatTypeValue>();
						cbbStatColumn.addItem(new ItemProperty("", ""));
						cbbFieldName.addItem(new ItemProperty("", ""));
						if (obj instanceof BillDetail) {

							cbbStatColumn.addItem(new ItemProperty("customNum",
									"对应报关数量"));
							cbbStatColumn.addItem(new ItemProperty("hsAmount",
									"折算海关数量"));
							cbbStatColumn.addItem(new ItemProperty("hsPrice",
									"海关单价"));
							cbbStatColumn.addItem(new ItemProperty("money",
									"金额"));
							cbbStatColumn.addItem(new ItemProperty("netWeight",
									"净重"));
							cbbStatColumn.addItem(new ItemProperty(
									"noCustomNum", "末对应报关数量"));
							cbbStatColumn.addItem(new ItemProperty("ptAmount",
									"数量"));
							cbbStatColumn.addItem(new ItemProperty("ptPrice",
									"单价"));
							cbbStatColumn.addItem(new ItemProperty(
									"unitConvert", "折算报关单位比率"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"billMaster.billNo", "单据号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"customNo", "对应报关单号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"hsName", "海关商品名称"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"hsSpec", "海关商品规格型号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"productNo", "制单号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"ptName", "商品名称"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"ptPart", "工厂料号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"ptSpec", "规格型号"));

							cbbFieldName.addItem(new ItemProperty("customNum",
									"对应报关数量"));
							cbbFieldName.addItem(new ItemProperty("hsAmount",
									"折算海关数量"));
							cbbFieldName.addItem(new ItemProperty("hsPrice",
									"海关单价"));
							cbbFieldName
									.addItem(new ItemProperty("money", "金额"));
							cbbFieldName.addItem(new ItemProperty("netWeight",
									"净重"));
							cbbFieldName.addItem(new ItemProperty(
									"noCustomNum", "末对应报关数量"));
							cbbFieldName.addItem(new ItemProperty("ptAmount",
									"数量"));
							cbbFieldName.addItem(new ItemProperty("ptPrice",
									"单价"));
							cbbFieldName.addItem(new ItemProperty(
									"unitConvert", "折算报关单位比率"));

							cbbFieldName.addItem(new ItemProperty("billMaster.billNo",
									"单据号"));

							cbbFieldName.addItem(new ItemProperty("customNo",
									"对应报关单号"));

							cbbFieldName.addItem(new ItemProperty("hsName",
									"海关商品名称"));

							cbbFieldName.addItem(new ItemProperty("hsSpec",
									"海关商品规格型号"));

							cbbFieldName.addItem(new ItemProperty("productNo",
									"制单号"));

							cbbFieldName.addItem(new ItemProperty("ptName",
									"商品名称"));

							cbbFieldName.addItem(new ItemProperty("ptPart",
									"工厂料号"));

							cbbFieldName.addItem(new ItemProperty("ptSpec",
									"规格型号"));

							cbbFieldName.addItem(new ItemProperty(
									"billMaster.validDate", "日期"));

						} else if (obj instanceof BaseCustomsDeclarationCommInfo) {
							cbbStatColumn.addItem(new ItemProperty(
									"commAmount", "商品数量"));
							cbbStatColumn.addItem(new ItemProperty(
									"commTotalPrice", "商品总价"));
							cbbStatColumn.addItem(new ItemProperty(
									"commUnitPrice", "商品单价"));
							cbbStatColumn.addItem(new ItemProperty(
									"dollarTotalPrice", "美元总价"));
							cbbStatColumn.addItem(new ItemProperty(
									"firstAmount", "第一法定数量"));
							cbbStatColumn.addItem(new ItemProperty(
									"grossWeight", "毛重"));
							cbbStatColumn.addItem(new ItemProperty("netWeight",
									"净重"));
							cbbStatColumn.addItem(new ItemProperty(
									"secondAmount", "第二法定数量"));
							cbbStatColumn.addItem(new ItemProperty(
									"unitWeight", "单位重量"));
							cbbStatColumn.addItem(new ItemProperty("pieces",
									"件(箱)数"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"serialNumber", "报关单商品流水号"));
							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"commSerialNo", "商品序号"));
							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"commName", "商品名称"));
							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"commSpec", "商品规格"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"materielNo", "货号"));
							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"version", "版本号"));
							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"addType", "新增来源"));
							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"detailNote", "详细型号规格"));
							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "baseCustomsDeclaration.serialNumber",
									 "报关单流水号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "baseCustomsDeclaration.emsHeadH2k",
									 "电子帐册号码"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "baseCustomsDeclaration.tradeCode",
									 "经营单位代码"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "baseCustomsDeclaration.tradeName",
									 "经营单位名称"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "baseCustomsDeclaration.billListId",
									 "报关清单号码"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "baseCustomsDeclaration.preCustomsDeclarationCode",
									 "预录入号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "baseCustomsDeclaration.unificationCode",
									 "统一编号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "baseCustomsDeclaration.customsDeclarationCode",
									 "报关单号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "baseCustomsDeclaration.customsDeclarationCodeinHouse",
									 "入库报关单号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "baseCustomsDeclaration.machCode",
									 " 收货单位代码"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "baseCustomsDeclaration.machName",
									 " 收货单位名称"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "baseCustomsDeclaration.conveyance",
									 " 运输工具"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "baseCustomsDeclaration.billOfLading",
									 " 提运单号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "baseCustomsDeclaration.license",
									 " 许可证编号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "baseCustomsDeclaration.authorizeFile",
									 " 批准文号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "baseCustomsDeclaration.contract",
									 " 合同协议号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "baseCustomsDeclaration.containerNum",
									 " 集装箱号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "baseCustomsDeclaration.customsEnvelopBillNo",
									 " 关封号"));
							
							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "baseCustomsDeclaration.wlserialNumber",
									 " 装箱单"));


							cbbFieldName.addItem(new ItemProperty("commAmount",
									"商品数量"));
							cbbFieldName.addItem(new ItemProperty(
									"commTotalPrice", "商品总价"));
							cbbFieldName.addItem(new ItemProperty(
									"commUnitPrice", "商品单价"));
							cbbFieldName.addItem(new ItemProperty(
									"dollarTotalPrice", "美元总价"));
							cbbFieldName.addItem(new ItemProperty(
									"firstAmount", "第一法定数量"));
							cbbFieldName.addItem(new ItemProperty(
									"grossWeight", "毛重"));
							cbbFieldName.addItem(new ItemProperty("netWeight",
									"净重"));
							cbbFieldName.addItem(new ItemProperty(
									"secondAmount", "第二法定数量"));
							cbbFieldName.addItem(new ItemProperty("unitWeight",
									"单位重量"));
							cbbFieldName.addItem(new ItemProperty("pieces",
									"件(箱)数"));
							cbbFieldName.addItem(new ItemProperty(
									"serialNumber", "报关单商品流水号"));
							cbbFieldName.addItem(new ItemProperty(
									"commSerialNo", "商品序号"));
							cbbFieldName.addItem(new ItemProperty("commName",
									"商品名称"));
							cbbFieldName.addItem(new ItemProperty("commSpec",
									"商品规格"));

							cbbFieldName.addItem(new ItemProperty("materielNo",
									"货号"));
							cbbFieldName.addItem(new ItemProperty("version",
									"版本号"));
							cbbFieldName.addItem(new ItemProperty("addType",
									"新增来源"));
							cbbFieldName.addItem(new ItemProperty("detailNote",
									"详细型号规格"));
							cbbFieldName.addItem(new ItemProperty(
									"baseCustomsDeclaration.declarationDate",
									"申报日期"));
							cbbFieldName.addItem(new ItemProperty(
									"baseCustomsDeclaration.impExpDate",
									"进出口日期"));
							cbbFieldName.addItem(new ItemProperty(
									 "baseCustomsDeclaration.serialNumber",
									 "报关单流水号"));

							cbbFieldName.addItem(new ItemProperty(
									 "baseCustomsDeclaration.emsHeadH2k",
									 "电子帐册号码"));

							cbbFieldName.addItem(new ItemProperty(
									 "baseCustomsDeclaration.tradeCode",
									 "经营单位代码"));

							cbbFieldName.addItem(new ItemProperty(
									 "baseCustomsDeclaration.tradeName",
									 "经营单位名称"));

							cbbFieldName.addItem(new ItemProperty(
									 "baseCustomsDeclaration.billListId",
									 "报关清单号码"));

							cbbFieldName.addItem(new ItemProperty(
									 "baseCustomsDeclaration.preCustomsDeclarationCode",
									 "预录入号"));

							cbbFieldName.addItem(new ItemProperty(
									 "baseCustomsDeclaration.unificationCode",
									 "统一编号"));

							cbbFieldName.addItem(new ItemProperty(
									 "baseCustomsDeclaration.customsDeclarationCode",
									 "报关单号"));

							cbbFieldName.addItem(new ItemProperty(
									 "baseCustomsDeclaration.customsDeclarationCodeinHouse",
									 "入库报关单号"));

							cbbFieldName.addItem(new ItemProperty(
									 "baseCustomsDeclaration.machCode",
									 " 收货单位代码"));

							cbbFieldName.addItem(new ItemProperty(
									 "baseCustomsDeclaration.machName",
									 " 收货单位名称"));

							cbbFieldName.addItem(new ItemProperty(
									 "baseCustomsDeclaration.conveyance",
									 " 运输工具"));

							cbbFieldName.addItem(new ItemProperty(
									 "baseCustomsDeclaration.billOfLading",
									 " 提运单号"));

							cbbFieldName.addItem(new ItemProperty(
									 "baseCustomsDeclaration.license",
									 " 许可证编号"));

							cbbFieldName.addItem(new ItemProperty(
									 "baseCustomsDeclaration.authorizeFile",
									 " 批准文号"));

							cbbFieldName.addItem(new ItemProperty(
									 "baseCustomsDeclaration.contract",
									 " 合同协议号"));

							cbbFieldName.addItem(new ItemProperty(
									 "baseCustomsDeclaration.containerNum",
									 " 集装箱号"));

							cbbFieldName.addItem(new ItemProperty(
									 "baseCustomsDeclaration.customsEnvelopBillNo",
									 " 关封号"));
							
							cbbFieldName.addItem(new ItemProperty(
									 "baseCustomsDeclaration.wlserialNumber",
									 " 装箱单"));

							

						} else if (obj instanceof com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo) {

							cbbStatColumn.addItem(new ItemProperty(
									"declaredAmount", "申报数量"));
							cbbStatColumn.addItem(new ItemProperty(
									"grossWeight", "毛重"));
							cbbStatColumn.addItem(new ItemProperty(
									"legalAmount", "法定数量"));
							cbbStatColumn.addItem(new ItemProperty("netWeight",
									"净重"));
							cbbStatColumn.addItem(new ItemProperty(
									"secondLegalAmount", "第二法定数量"));
							cbbStatColumn.addItem(new ItemProperty(
									"totalNetWeight", "总重量"));
							cbbStatColumn.addItem(new ItemProperty(
									"declaredPrice", "企业申报单价"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"serialNo", "商品序号"));
							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"customsNo", "报关单号"));
							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"emsSerialNo", "帐册序号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "afterComInfo.billList.emsHeadH2k", "电子帐册号码"));							

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "afterComInfo.billList.tradeCode", "经营单位代码"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "afterComInfo.billList.tradeName", "经营单位名称"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "afterComInfo.billList.listNo", "清单号码"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "afterComInfo.billList.listUniteNo", "清单统一编号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "afterComInfo.billList.customsDeclarationCode", "报关单流水编号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "afterComInfo.billList.customsEnvelopBillNo", "关封号"));


							cbbFieldName.addItem(new ItemProperty(
									"declaredAmount", "申报数量"));
							cbbFieldName.addItem(new ItemProperty(
									"grossWeight", "毛重"));
							cbbFieldName.addItem(new ItemProperty(
									"legalAmount", "法定数量"));
							cbbFieldName.addItem(new ItemProperty("netWeight",
									"净重"));
							cbbFieldName.addItem(new ItemProperty(
									"secondLegalAmount", "第二法定数量"));
							cbbFieldName.addItem(new ItemProperty(
									"totalNetWeight", "总重量"));
							cbbFieldName.addItem(new ItemProperty(
									"declaredPrice", "企业申报单价"));

							cbbFieldName.addItem(new ItemProperty("serialNo",
									"商品序号"));
							cbbFieldName.addItem(new ItemProperty("customsNo",
									"报关单号"));
							cbbFieldName.addItem(new ItemProperty(
									"emsSerialNo", "帐册序号"));
							cbbFieldName.addItem(new ItemProperty(
									"billList.listDeclareDate", "清单申报日期"));
							cbbFieldName.addItem(new ItemProperty(
									"billList.createdDate", "录入日期"));
							
							cbbFieldName.addItem(new ItemProperty(
									 "afterComInfo.billList.billType", "清单类型"));
							
							cbbFieldName.addItem(new ItemProperty(
									 "afterComInfo.billList.emsHeadH2k", "电子帐册号码"));							

							cbbFieldName.addItem(new ItemProperty(
									 "afterComInfo.billList.tradeCode", "经营单位代码"));

							cbbFieldName.addItem(new ItemProperty(
									 "afterComInfo.billList.tradeName", "经营单位名称"));

							cbbFieldName.addItem(new ItemProperty(
									 "afterComInfo.billList.listNo", "清单号码"));

							cbbFieldName.addItem(new ItemProperty(
									 "afterComInfo.billList.listUniteNo", "清单统一编号"));

							cbbFieldName.addItem(new ItemProperty(
									 "afterComInfo.billList.customsDeclarationCode", "报关单流水编号"));

							cbbFieldName.addItem(new ItemProperty(
									 "afterComInfo.billList.customsEnvelopBillNo", "关封号"));


						} else if (obj instanceof com.bestway.dzsc.customslist.entity.DzscBillListBeforeCommInfo) {
						
							cbbStatColumn.addItem(new ItemProperty(
									"declaredAmount", "申报数量"));
							cbbStatColumn.addItem(new ItemProperty(
									"grossWeight", "毛重"));
							cbbStatColumn.addItem(new ItemProperty(
									"legalAmount", "法定数量"));
							cbbStatColumn.addItem(new ItemProperty("netWeight",
									"净重"));
							cbbStatColumn.addItem(new ItemProperty(
									"secondLegalAmount", "第二法定数量"));
							cbbStatColumn.addItem(new ItemProperty(
									"declaredPrice", "企业申报单价"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"no", "流水序号"));
							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"emsSerialNo", "手册序号"));
							cbbGroupingColumn.addItem(new CheckBoxListItem(
									"bomVersion", "成品版本号"));
							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "afterComInfo.billList.emsHeadH2k", "手册号码"));
							
							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "afterComInfo.billList.tradeCode", "经营单位代码"));
							
							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "afterComInfo.billList.tradeName", "经营单位名称"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "afterComInfo.billList.listNo", "清单号码"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "afterComInfo.billList.copEmsNo", "企业内部编号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "afterComInfo.billList.customsSeqNo", "报关单统一编号"));

							cbbGroupingColumn.addItem(new CheckBoxListItem(
									 "afterComInfo.billList.customsEnvelopBillNo", "关封号"));
							

							cbbFieldName.addItem(new ItemProperty(
									"declaredAmount", "申报数量"));
							cbbFieldName.addItem(new ItemProperty(
									"grossWeight", "毛重"));
							cbbFieldName.addItem(new ItemProperty(
									"legalAmount", "法定数量"));
							cbbFieldName.addItem(new ItemProperty("netWeight",
									"净重"));
							cbbFieldName.addItem(new ItemProperty(
									"secondLegalAmount", "第二法定数量"));
							cbbFieldName.addItem(new ItemProperty(
									"declaredPrice", "企业申报单价"));

							cbbFieldName
									.addItem(new ItemProperty("no", "流水序号"));
							cbbFieldName.addItem(new ItemProperty(
									"emsSerialNo", "手册序号"));
							cbbFieldName.addItem(new ItemProperty("bomVersion",
									"成品版本号"));
							cbbFieldName.addItem(new ItemProperty(
									"billList.listDeclareDate", "清单申报日期"));
							cbbFieldName.addItem(new ItemProperty(
									"billList.createdDate", "录入日期"));
							
							cbbFieldName.addItem(new ItemProperty(
									 "afterComInfo.billList.emsHeadH2k", "手册号码"));
							
							cbbFieldName.addItem(new ItemProperty(
									 "afterComInfo.billList.tradeCode", "经营单位代码"));
							
							cbbFieldName.addItem(new ItemProperty(
									 "afterComInfo.billList.tradeName", "经营单位名称"));

							cbbFieldName.addItem(new ItemProperty(
									 "afterComInfo.billList.listNo", "清单号码"));

							cbbFieldName.addItem(new ItemProperty(
									 "afterComInfo.billList.copEmsNo", "企业内部编号"));

							cbbFieldName.addItem(new ItemProperty(
									 "afterComInfo.billList.customsSeqNo", "报关单统一编号"));

							cbbFieldName.addItem(new ItemProperty(
									 "afterComInfo.billList.customsEnvelopBillNo", "关封号"));

						}

						addFiltConditionList = new ArrayList<FiltCondition>();
						removeFiltConditionList = new ArrayList<FiltCondition>();

						cbbGroupingColumn.setSelectedItem(null);
						cbbGroupingColumn
								.setRenderer(new CheckBoxListCellRendererNotCode(
										cbbGroupingColumn));
						cbbGroupingColumn
								.setUI(new SteppedMetalComboBoxUI(220));
						setCmbBillTypeEvent(cbbGroupingColumn);

					} catch (Exception ex) {
						ex.printStackTrace();

					}

					cbbAimObject.validate();
				}

			});

		}
		return cbbAimObject;
	}

	/**
	 * This method initializes tfReportName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfReportName() {
		if (tfReportName == null) {
			tfReportName = new JTextField();
			tfReportName.setBounds(new Rectangle(244, 3, 156, 25));
		}
		return tfReportName;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setBounds(new Rectangle(139, 5, 60, 25));
			btnClose.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgReportStatCondition.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setBounds(new Rectangle(74, 5, 60, 25));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					dataState = DataState.EDIT;
					setState();
				}
			});

		}
		return btnEdit;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.setBounds(new Rectangle(9, 5, 60, 25));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<GroupValue> groupList = getGroupingList();
					if (validateData() == true) {
						return;
					}
					List<GroupValue> groupValueByStatTypeList = getGroupValueByStatTypeList();
					if (dataState == DataState.ADD) {
						SelectCondition selectCondition = new SelectCondition();
						GroupValue groupValue = new GroupValue();
						fillData(selectCondition);
						selectCondition = aptitudeReportAction
								.saveSelectCondition(new Request(CommonVars
										.getCurrUser()), selectCondition,
										groupList, statTypeValueList,
										groupValueByStatTypeList,
										addFiltConditionList);
						DgReportStatCondition.this.setSelect(selectCondition,
								groupValue);
						if (isMasterTable == true) {
							tableModel.addRow(selectCondition);
						}

						if (cbbStatValue.getSelectedItem() != null
								&& cbbOperator1.getSelectedItem() != null
								&& tfStatValue.getText() != null) {
							FiltCondition filtCondition = new FiltCondition();
							String statValue = ((ItemProperty) cbbStatValue
									.getSelectedItem()).getName();
							String enStatValue = ((ItemProperty) cbbStatValue
									.getSelectedItem()).getCode();
							String operator = ((ItemProperty) cbbOperator1
									.getSelectedItem()).getCode();
							String value = tfStatValue.getText();
							filtCondition.setFieldName(statValue);
							filtCondition.setEnFieldName(enStatValue);
							filtCondition.setConditionType("2");
							filtCondition.setLogic("having");
							filtCondition.setOperator(operator);
							filtCondition.setValue(value);
							filtCondition.setSelectCondition(selectCondition);
							filtCondition.setSqlSentence("having" + statValue
									+ operator + value);
							filtCondition.setEnSqlSentence("having "
									+ enStatValue + operator + value);
							aptitudeReportAction.saveFiltCondition(new Request(
									CommonVars.getCurrUser()), filtCondition);
						}

					} else if (dataState == DataState.EDIT) {
						fillData(selectCondition);
						List<StatTypeValue> statTypeValueDelList = getStatTypeValue();
						List<FiltCondition> filtConditionList = getFiltCondition();
						List<FiltCondition> StatConditionList = getStatCondition();
						List<GroupValue> list = aptitudeReportAction
								.findGroupValue(new Request(CommonVars
										.getCurrUser()), selectCondition
										.getId());
						// 删除分组条件
						aptitudeReportAction.deleteGroupValue(new Request(
								CommonVars.getCurrUser()), list);

						// 删除筛选条件
						for (int i = 0; i < filtConditionList.size(); i++) {

							aptitudeReportAction.deleteFiltCondition(
									new Request(CommonVars.getCurrUser()),
									filtConditionList.get(i));

						}

						// 删除统计条件

						for (int i = 0; i < StatConditionList.size(); i++) {
							aptitudeReportAction.deleteFiltCondition(
									new Request(CommonVars.getCurrUser()),
									StatConditionList.get(i));

						}

						// 删除统计方式
						for (int i = 0; i < statTypeValueDelList.size(); i++) {

							aptitudeReportAction.deleteStatTypeValue(
									new Request(CommonVars.getCurrUser()),
									statTypeValueDelList.get(i));

						}
						selectCondition = aptitudeReportAction
								.saveSelectCondition(new Request(CommonVars
										.getCurrUser()), selectCondition,
										groupList, statTypeValueList,
										groupValueByStatTypeList,
										addFiltConditionList);

						// 保存统计条件
						if (cbbStatValue.getSelectedItem() != null
								&& cbbOperator1 != null
								&& tfStatValue.getText() != null) {
							FiltCondition filtCondition = new FiltCondition();
							String statValue = ((ItemProperty) cbbStatValue
									.getSelectedItem()).getName();
							String enStatValue = ((ItemProperty) cbbStatValue
									.getSelectedItem()).getCode();
							String operator = ((ItemProperty) cbbOperator1
									.getSelectedItem()).getCode();
							String value = tfStatValue.getText();
							filtCondition.setFieldName(statValue);
							filtCondition.setEnFieldName(enStatValue);
							filtCondition.setConditionType("2");
							filtCondition.setLogic("having");
							filtCondition.setOperator(operator);
							filtCondition.setValue(value);
							filtCondition.setSelectCondition(selectCondition);
							filtCondition.setSqlSentence("having" + statValue
									+ operator + value);
							filtCondition.setEnSqlSentence("having "
									+ enStatValue + operator + value);
							aptitudeReportAction.saveFiltCondition(new Request(
									CommonVars.getCurrUser()), filtCondition);
						}

					}

					dataState = DataState.BROWSE;
					setState();
				}

			});

		}
		return btnSave;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.setPreferredSize(new Dimension(730, 514));
			jPanel3.add(getJPanel5(), BorderLayout.CENTER);
			jPanel3.add(getJPanel6(), BorderLayout.SOUTH);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
			jPanel5.setPreferredSize(new Dimension(720, 170));
			jPanel5.setBorder(BorderFactory.createTitledBorder(null,
					"\u7b5b\u9009\u6761\u4ef6",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null,
					SystemColor.activeCaption));
			jPanel5.add(getJScrollPane2(), null);
			jPanel5.add(getBtnAnd(), null);
			jPanel5.add(getBtnOr(), null);
			jPanel5.add(getBtnDel1(), null);
			jPanel5.add(getCbbFieldName(), null);
			jPanel5.add(getCbbOperator(), null);
			jPanel5.add(getTfValue(), null);
			// jPanel5.add(getJButton11(), null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(20, 77, 61, 25));
			jLabel6.setText("统计栏位");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(20, 30, 60, 24));
			jLabel5.setText("分组栏位");
			jPanel6 = new JPanel();
			jPanel6.setLayout(null);
			jPanel6.setPreferredSize(new Dimension(730, 130));
			jPanel6.setBorder(BorderFactory.createTitledBorder(null,
					"\u5206\u7ec4\u7edf\u8ba1",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), SystemColor.activeCaption));
			jPanel6.add(jLabel5, null);
			jPanel6.add(jLabel6, null);
			jPanel6.add(getCbbStatColumn(), null);
			jPanel6.add(getJPanel7(), null);
			jPanel6.add(getJPanel8(), null);
			jPanel6.add(getCbbGroupingColumn(), null);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setBounds(new Rectangle(377, 14, 221, 155));
			jScrollPane2.setViewportView(getListFiltCondition());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes btnAnd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAnd() {
		if (btnAnd == null) {
			btnAnd = new JButton();
			btnAnd.setBounds(new Rectangle(275, 30, 60, 29));
			btnAnd.setText("and");
			btnAnd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (e.getSource() == btnAnd) {
						setLogic(btnAnd.getText());
					}

					if (checkNull()) {
						return;
					}
					String logic = getLogic();
					String fieldName = ((ItemProperty) cbbFieldName
							.getSelectedItem()).getName();
					String enFieldName = ((ItemProperty) cbbFieldName
							.getSelectedItem()).getCode();
					if(enFieldName.equalsIgnoreCase("billMaster.validDate")
							 ||enFieldName.equalsIgnoreCase("billList.listDeclareDate")
							 ||enFieldName.equalsIgnoreCase("billList.createdDate")
							 ||enFieldName.equalsIgnoreCase("baseCustomsDeclaration.impExpDate")
							 ||enFieldName.equalsIgnoreCase("baseCustomsDeclaration.declarationDate")
							 ||enFieldName.equalsIgnoreCase("billList.listDeclareDate")
							 ||enFieldName.equalsIgnoreCase("billList.createdDate")){
							 String dataType = "java.util.Date";						
							 setDataType(dataType);					
							 }
					String operator = ((ItemProperty) cbbOperator
							.getSelectedItem()).getCode();
					String cnOpertor = ((ItemProperty) cbbOperator
							.getSelectedItem()).getName();
					String value = tfValue.getText();
					String sqlSentence = logic + " " + fieldName + " "
							+ cnOpertor + " " + value;
					String enSqlSentence = logic + " (" + enFieldName + " "
							+ operator + " " + value + ")";

					Object classname = ((ItemProperty) cbbAimObject
							.getSelectedItem()).getCode();
					String trageClassName = String.valueOf(classname);
					try {

						PropertyDescriptor[] props;
						props = PropertyUtils.getPropertyDescriptors(Class
								.forName(trageClassName));
						for (int i = 0; i < props.length; i++) {
							Class clazz = props[i].getPropertyType();
							String tempField = props[i].getName();
							Object cls = props[i].getClass();
							if (tempField.equalsIgnoreCase(enFieldName)) {
								String dataType = clazz.getName();
								setDataType(dataType);
							}

						}

					} catch (ClassNotFoundException f) {
						f.printStackTrace();
					}
					dataType = getDataType();

					addFiltCondition(logic, fieldName, operator, value,
							sqlSentence, enSqlSentence, enFieldName, dataType);
					if("java.util.Date".equals(dataType)){
						   try{
							      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							      java.util.Date outDate = dateFormat.parse(value);
							      java.sql.Date simpleDate=new java.sql.Date(outDate.getTime());
							      value=simpleDate.toString();
							    }catch(Exception f){
							    	getValiDate();
							    	int i = getAddFiltConditionList().size()-1;
							    	List<FiltCondition> list = getAddFiltConditionList();
							    	FiltCondition condition = (FiltCondition) list.get(i);
							    	remove(condition);
							    } 
					}
					clearData();

				}
			});

		}
		return btnAnd;
	}

	/**
	 * This method initializes btnOr
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOr() {
		if (btnOr == null) {
			btnOr = new JButton();
			btnOr.setBounds(new Rectangle(275, 80, 60, 29));
			btnOr.setText("or");
			btnOr.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (e.getSource() == btnOr) {
						setLogic(btnOr.getText());
					}

					if (checkNull()) {
						return;
					}
					String logic = getLogic();
					String fieldName = ((ItemProperty) cbbFieldName
							.getSelectedItem()).getName();
					String enFieldName = ((ItemProperty) cbbFieldName
							.getSelectedItem()).getCode();
					if(enFieldName.equalsIgnoreCase("billMaster.validDate")
							 ||enFieldName.equalsIgnoreCase("billList.listDeclareDate")
							 ||enFieldName.equalsIgnoreCase("billList.createdDate")
							 ||enFieldName.equalsIgnoreCase("baseCustomsDeclaration.impExpDate")
							 ||enFieldName.equalsIgnoreCase("baseCustomsDeclaration.declarationDate")
							 ||enFieldName.equalsIgnoreCase("billList.listDeclareDate")
							 ||enFieldName.equalsIgnoreCase("billList.createdDate")){
							 String dataType = "java.util.Date";						
							 setDataType(dataType);					
							 }					
					String value = tfValue.getText();
					String operator = ((ItemProperty) cbbOperator
							.getSelectedItem()).getCode();
					String cnOpertor = ((ItemProperty) cbbOperator
							.getSelectedItem()).getName();
					String sqlSentence = logic + " " + fieldName + " "
							+ cnOpertor + " " + value;
					String enSqlSentence = logic + " (" + enFieldName + " "
							+ operator + " " + value + ")";

					Object classname = ((ItemProperty) cbbAimObject
							.getSelectedItem()).getCode();
					String trageClassName = String.valueOf(classname);
					try {

						PropertyDescriptor[] props;
						props = PropertyUtils.getPropertyDescriptors(Class
								.forName(trageClassName));
						for (int i = 0; i < props.length; i++) {
							Class clazz = props[i].getPropertyType();
							String tempField = props[i].getName();
							Object cls = props[i].getClass();
							if (tempField.equalsIgnoreCase(enFieldName)) {
								String dataType = clazz.getName();
								setDataType(dataType);
							}

						}

					} catch (ClassNotFoundException f) {
						f.printStackTrace();
					}
					dataType = getDataType();

					addFiltCondition(logic, fieldName, operator, value,
							sqlSentence, enSqlSentence, enFieldName, dataType);
					if("java.util.Date".equals(dataType)){
						   try{
							      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							      java.util.Date outDate = dateFormat.parse(value);
							      java.sql.Date simpleDate=new java.sql.Date(outDate.getTime());
							      value=simpleDate.toString();
							    }catch(Exception f){
//							    	java.util.Date today=new Date(System.currentTimeMillis());
//							    	String year = today.toString().substring(24, 28);
//							    	value = year+"-01-01";
							    	getValiDate();
							    	int i = getAddFiltConditionList().size()-1;
							    	List<FiltCondition> list = getAddFiltConditionList();
							    	FiltCondition condition = (FiltCondition) list.get(i);
							    	remove(condition);
							    } 
					}
					clearData();

				}
			});
		}
		return btnOr;
	}

	/**
	 * This method initializes btnDel1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDel1() {
		if (btnDel1 == null) {
			btnDel1 = new JButton();
			btnDel1.setBounds(new Rectangle(275, 130, 60, 29));
			btnDel1.setText("删除");
			btnDel1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					removeFiltCondition();
				}
			});
		}
		return btnDel1;
	}

	void addFiltCondition(String logic, String fieldName, String operator,
			String value, String sqlSentence, String enSqlSentence,
			String enFieldName, String dataType) {

		FiltCondition condition = new FiltCondition();
		condition.setBeginBracket("(");
		condition.setConditionType("1");
		condition.setDataType(dataType);
		condition.setEndBracket(")");
		condition.setFieldName(fieldName);
		condition.setLogic(logic);
		condition.setValue(value);
		condition.setOperator(operator);
		condition.setSqlSentence(sqlSentence);
		condition.setEnFieldName(enFieldName);
		condition.setEnSqlSentence(enSqlSentence);
		addFiltConditionList.add(condition);
		vectorCondition.addElement(condition);

		setAddFiltConditionList(addFiltConditionList);
		this.listFiltCondition.setListData(vectorCondition);
		this.listFiltCondition.setCellRenderer(new ConditionListCellRenderer());
	}

	void removeFiltCondition() {
		if (this.listFiltCondition.getSelectedValue() == null) {
			JOptionPane.showMessageDialog(this, "请选择要删除的筛选条件!", "提示", 1);
			return;
		}

		Object[] removedField = this.listFiltCondition.getSelectedValues();
		for (int i = 0; i < removedField.length; i++) {
			FiltCondition condition = (FiltCondition) removedField[i];
			vectorCondition.removeElement(condition);
			removeFiltConditionList.add(condition);
			addFiltConditionList.remove(condition);

		}
		setAddFiltConditionList(addFiltConditionList);
		setRemoveFiltConditionList(removeFiltConditionList);
		this.listFiltCondition.setListData(vectorCondition);
		this.listFiltCondition.setCellRenderer(new ConditionListCellRenderer());

	}

	class ConditionListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			String s = "";
			if (value != null) {
				s = ((FiltCondition) value).getSqlSentence();
			}
			setText(s);
			return this;
		}
	}

	/**
	 * This method initializes cbbFieldName
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFieldName() {
		if (cbbFieldName == null) {
			cbbFieldName = new JComboBox();
			cbbFieldName.setBounds(new Rectangle(45, 28, 185, 29));
			cbbFieldName.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					setState();
				}
			});

		}
		return cbbFieldName;
	}

	/**
	 * This method initializes cbbOperator
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbOperator() {
		if (cbbOperator == null) {
			cbbOperator = new JComboBox();
			cbbOperator.setBounds(new Rectangle(45, 80, 88, 29));
			cbbOperator.addItem(new ItemProperty("  ", "  "));
			cbbOperator.addItem(new ItemProperty("=", "等于"));
			cbbOperator.addItem(new ItemProperty(">", "大于"));
			cbbOperator.addItem(new ItemProperty(">=", "大于等于"));
			cbbOperator.addItem(new ItemProperty("<", "小于"));
			cbbOperator.addItem(new ItemProperty("<=", "小于等于"));
			cbbOperator.addItem(new ItemProperty("<>", "不等于"));
			cbbOperator.addItem(new ItemProperty("like", "LIKE"));
			cbbOperator.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					setState();
				}
			});
		}
		return cbbOperator;
	}

	/**
	 * This method initializes tfValue
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfValue() {
		if (tfValue == null) {
			tfValue = new JTextField();
			tfValue.setBounds(new Rectangle(45, 130, 163, 29));
//			tfValue.setText("0");
		}
		return tfValue;
	}

	/**
	 * This method initializes jButton11
	 * 
	 * @return javax.swing.JButton
	 */
	// private JButton getJButton11() {
	// if (jButton11 == null) {
	// jButton11 = new JButton();
	// jButton11.setBounds(new Rectangle(205, 129, 22, 28));
	// jButton11.setText("...");
	// }
	// return jButton11;
	// }
	/**
	 * This method initializes cbbGroupingColumn
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbGroupingColumn() {
		if (cbbGroupingColumn == null) {
			cbbGroupingColumn = new JComboBox();
			cbbGroupingColumn.setBounds(new Rectangle(85, 28, 140, 27));
			cbbGroupingColumn
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {

							setState();
						}
					});
		}
		return cbbGroupingColumn;
	}

	/**
	 * This method initializes cbbStatColumn
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbStatColumn() {
		if (cbbStatColumn == null) {
			cbbStatColumn = new JComboBox();
			cbbStatColumn.setBounds(new Rectangle(85, 75, 140, 27));
			cbbStatColumn
			.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					cbbStatValue.removeAllItems();
					cbSum.setSelected(false);
					cbAvg.setSelected(false);
					cbMin.setSelected(false);
					cbMax.setSelected(false);
					List<StatTypeValue> statTypeValueList = getStatTypeValueList();
					statTypeValueList.removeAll(statTypeValueList);

//					int count = cbbStatColumn.getItemCount();
//					System.out.println("ccccccccccc=="+count);
//
//					String statValue = cbbStatColumn.getSelectedItem()
//							.toString();
//					String code = ((ItemProperty) cbbStatColumn
//							.getSelectedItem()).getCode();
//
//					groupValueByStatTypeList = new ArrayList();
//
//					for (int i = 0; i < statTypeValueList.size(); i++) {
//						GroupValue groupValueByStatType = new GroupValue();
//						//							
//						String enName = statTypeValueList.get(i)
//								.getCode()
//								+ "(" + code + ")";
//						String CnName = statValue + "("
//								+ statTypeValueList.get(i).getName()
//								+ ")";
//						cbbStatValue.addItem(new ItemProperty(enName,
//								CnName));
//
//						groupValueByStatType.setCode(enName);
//						groupValueByStatType.setName(CnName);
//						groupValueByStatType.setGroupType(1);
//						groupValueByStatTypeList
//								.add(groupValueByStatType);
//					}
//					setGroupValueByStatTypeList(groupValueByStatTypeList);
					setState();

				}
			});
}
return cbbStatColumn;
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
			jPanel7.setBounds(new Rectangle(438, 13, 166, 113));
			jPanel7.setBorder(BorderFactory.createTitledBorder(null,
					"\u7edf\u8ba1\u6761\u4ef6",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), SystemColor.activeCaption));
			jPanel7.add(getCbbStatValue(), null);
			jPanel7.add(getCbbOperator1(), null);
			jPanel7.add(getTfStatValue(), null);
		}
		return jPanel7;
	}

	/**
	 * This method initializes cbbStatValue
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbStatValue() {
		if (cbbStatValue == null) {
			cbbStatValue = new JComboBox();
			cbbStatValue.setBounds(new Rectangle(12, 17, 129, 27));
			cbbStatValue.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
				
					setState();

				}
			});
		}
		return cbbStatValue;
	}

	/**
	 * This method initializes cbbOperator1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbOperator1() {
		if (cbbOperator1 == null) {
			cbbOperator1 = new JComboBox();
			cbbOperator1.setBounds(new Rectangle(12, 50, 67, 27));
			cbbOperator1.addItem(new ItemProperty(">", "大于"));
			cbbOperator1.addItem(new ItemProperty(">=", "大于等于"));
			cbbOperator1.addItem(new ItemProperty("=", "等于"));
			cbbOperator1.addItem(new ItemProperty("<", "小于"));
			cbbOperator1.addItem(new ItemProperty("<=", "小于等于"));
			cbbOperator1.addItem(new ItemProperty("<>", "不等于"));
			cbbOperator1.addItem(new ItemProperty("like", "LIKE"));
			cbbOperator1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					setState();
				}
			});
		}
		return cbbOperator1;
	}

	/**
	 * This method initializes tfStatValue
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfStatValue() {
		if (tfStatValue == null) {
			tfStatValue = new JTextField();
			tfStatValue.setBounds(new Rectangle(12, 82, 130, 27));
			tfStatValue.setText("0");
		}
		return tfStatValue;
	}

	/**
	 * 设置怎么样编辑JComboBox 上的 JList 和 JTextField
	 * 
	 * @param cbb
	 */
	private void setCmbBillTypeEvent(final JComboBox cbb) {
		final JList listBox;
		try {
			Field field = JComponent.class.getDeclaredField("ui");
			field.setAccessible(true);
			BasicComboBoxUI ui = (BasicComboBoxUI) field.get(cbb);
			field = BasicComboBoxUI.class.getDeclaredField("listBox");
			field.setAccessible(true);
			listBox = (JList) field.get(ui);
		} catch (NoSuchFieldException nsfe) {
			throw new RuntimeException(nsfe);
		} catch (IllegalAccessException iae) {
			throw new RuntimeException(iae);
		}
		if (listBox == null) {
			return;
		}

		listBox.setBackground(Color.white);
		listBox.setFixedCellHeight(20);
		boolean editFlag = setEditFlag();

		if (editFlag) {
			List list = getIndexOfGroupingColumnValue();
			for (int j = 0; j < list.size(); j++) {
				int index = Integer.valueOf(list.get(j).toString());
				CheckBoxListItem item = (CheckBoxListItem) listBox.getModel()
						.getElementAt(index);
				item.setIsSelected(!item.getIsSelected());
				Rectangle rect = listBox.getCellBounds(index, index);
				listBox.repaint(rect);
				ComboBoxEditor com = cbb.getEditor();
				JTextField tf = (JTextField) com.getEditorComponent();
				if (tf != null) {
					if (item.getIsSelected()) {
						tf.setText("".equals(tf.getText()) ? item.getName()
								: tf.getText() + "," + item.getName());
					} else {
						String[] strs = tf.getText().split(",");
						String str = "";
						for (int i = 0; i < strs.length; i++) {
							if (item.getName().equalsIgnoreCase(strs[i])) {
								continue;
							}
							if ("".equals(str)) {
								str += strs[i];
							} else {
								str += "," + strs[i];
							}
						}
						tf.setText(str);
					}
				}
			}

		}

		MouseListener[] mouseListener = listBox.getMouseListeners();
		for (int i = 0; i < mouseListener.length; i++) {
			listBox.removeMouseListener(mouseListener[i]);
		}

		listBox.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index = listBox.locationToIndex(e.getPoint());
				if (index == -1) {
					return;
				}
				CheckBoxListItem item = (CheckBoxListItem) listBox.getModel()
						.getElementAt(index);
				item.setIsSelected(!item.getIsSelected());
				Rectangle rect = listBox.getCellBounds(index, index);
				listBox.repaint(rect);
				ComboBoxEditor com = cbb.getEditor();
				JTextField tf = (JTextField) com.getEditorComponent();
				if (tf != null) {
					if (item.getIsSelected()) {
						tf.setText("".equals(tf.getText()) ? item.getName()
								: tf.getText() + "," + item.getName());
					} else {
						String[] strs = tf.getText().split(",");
						String str = "";
						for (int i = 0; i < strs.length; i++) {
							if (item.getName().equalsIgnoreCase(strs[i])) {
								continue;
							}
							if ("".equals(str)) {
								str += strs[i];
							} else {
								str += "," + strs[i];
							}
						}
						tf.setText(str);
					}
				}

			}
		});

		//
		// 当焦点丢失的时候
		//
		ComboBoxEditor com = cbb.getEditor();
		final JTextField tf = (JTextField) com.getEditorComponent();
		tf.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {

				String[] strs = tf.getText().split(",");
				Map<String, CheckBoxListItem> checkBoxListItemMap = new HashMap<String, CheckBoxListItem>();

				int size = listBox.getModel().getSize();
				System.out.println(size);
				for (int i = 0; i < size; i++) {
					CheckBoxListItem item = (CheckBoxListItem) listBox
							.getModel().getElementAt(i);
					checkBoxListItemMap.put(item.getName(), item);
				}
				//
				// 根据输入的字符串选中JList中的列表
				//
				String tempText = "";
				for (String str : strs) {
					CheckBoxListItem item = checkBoxListItemMap.get(str);
					if (item != null) {
						item.setIsSelected(true);
						if ("".equals(tempText)) {
							tempText += item.getName();
						} else {
							tempText += "," + item.getName();
						}
						checkBoxListItemMap.remove(str);
					}
				}
				//
				// 清除未选中的记录
				//
				Iterator<CheckBoxListItem> iterator = checkBoxListItemMap
						.values().iterator();
				for (; iterator.hasNext();) {
					CheckBoxListItem checkBoxListItem = iterator.next();
					checkBoxListItem.setIsSelected(false);
				}
				//
				// 重新设置其显示文本值
				//
				tf.setText(tempText);
			}
		});

	}

	private boolean setEditFlag() {
		boolean editFlag = false;
		if (dataState == DataState.EDIT) {
			editFlag = true;
		}
		return editFlag;
	}

	private List<GroupValue> getGroupingList() {
		List<GroupValue> groupList = new ArrayList<GroupValue>();
		List<CheckBoxListItem> checkableItemList = getCheckableItemList();
		int count = getCheckableItemList().size();
		for (int j = 0; j < count; j++) {
			GroupValue groupValue = new GroupValue();
			groupValue.setCode(checkableItemList.get(j).getCode());
			groupValue.setName(checkableItemList.get(j).getName());
			groupList.add(groupValue);
		}

		return groupList;
	}

	private List getIndexOfGroupingColumnValue() {
		List<Integer> indexList = new ArrayList<Integer>();
		int index = 0;
		List<CheckBoxListItem> checkableItemList = new ArrayList<CheckBoxListItem>();
		List<GroupValue> groupList = aptitudeReportAction.findGroupValue(
				new Request(CommonVars.getCurrUser()), selectCondition.getId(),
				0);
		for (int i = 0; i < cbbGroupingColumn.getModel().getSize(); i++) {
			CheckBoxListItem item = (CheckBoxListItem) this.cbbGroupingColumn
					.getModel().getElementAt(i);
			checkableItemList.add(item);
			for (int j = 0; j < groupList.size(); j++) {
				boolean flag = groupList.get(j).getName().equalsIgnoreCase(
						checkableItemList.get(i).getName());
				if (flag) {
					index = i;
					indexList.add(index);
				}

			}
		}
		return indexList;

	}

	private List<CheckBoxListItem> getCheckableItemList() {
		List<CheckBoxListItem> checkableItemList = new ArrayList<CheckBoxListItem>();
		for (int j = 0; j < this.cbbGroupingColumn.getModel().getSize(); j++) {
			CheckBoxListItem item = (CheckBoxListItem) this.cbbGroupingColumn
					.getModel().getElementAt(j);
			if (item.getIsSelected()) {

				checkableItemList.add(item);

			}

		}
		return checkableItemList;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	private void fillData(SelectCondition selectCondition) {

		selectCondition.setAimObjectValue(((ItemProperty) this.cbbAimObject
				.getSelectedItem()).getName());

		selectCondition.setStatColumnValue(((ItemProperty) this.cbbStatColumn
				.getSelectedItem()).getName());

		selectCondition.setReportName(tfReportName.getText());
		selectCondition.setStatColumn(((ItemProperty) this.cbbStatColumn
				.getSelectedItem()).getCode());

		selectCondition.setAimObject(((ItemProperty) this.cbbAimObject
				.getSelectedItem()).getCode());

		selectCondition.setReportType(0);

	}

	public void setSelect(SelectCondition selectCondition, GroupValue groupValue) {
		this.selectCondition = selectCondition;
		this.groupValue = groupValue;

	}

	private void setState() {

		this.cbbAimObject.setEnabled(dataState == DataState.ADD);
		this.tfReportName.setEnabled(dataState == DataState.ADD
				|| dataState == DataState.EDIT);
		this.cbbFieldName.setEnabled(dataState == DataState.ADD
				|| dataState == DataState.EDIT);
		this.cbbOperator.setEnabled(dataState == DataState.ADD
				|| dataState == DataState.EDIT);
		this.tfValue.setEnabled(dataState == DataState.ADD
				|| dataState == DataState.EDIT);
		this.cbbGroupingColumn.setEnabled(dataState == DataState.ADD
				|| dataState == DataState.EDIT);
		this.cbbStatColumn.setEnabled(dataState == DataState.ADD
				|| dataState == DataState.EDIT);
		this.cbAvg.setEnabled(dataState == DataState.ADD
				|| dataState == DataState.EDIT);
		this.cbMax.setEnabled(dataState == DataState.ADD
				|| dataState == DataState.EDIT);
		this.cbMin.setEnabled(dataState == DataState.ADD
				|| dataState == DataState.EDIT);
		this.cbSum.setEnabled(dataState == DataState.ADD
				|| dataState == DataState.EDIT);
		this.cbbOperator1.setEnabled(dataState == DataState.ADD
				|| dataState == DataState.EDIT);
		this.cbbStatValue.setEnabled(dataState == DataState.ADD
				|| dataState == DataState.EDIT);
		this.btnSave.setEnabled(dataState == DataState.ADD
				|| dataState == DataState.EDIT);

	}

	private void setAddFiltConditionList(
			List<FiltCondition> addFiltConditionList) {

		this.addFiltConditionList = addFiltConditionList;
	}

	private List<FiltCondition> getAddFiltConditionList() {
		return addFiltConditionList;
	}

	private void setRemoveFiltConditionList(
			List<FiltCondition> removeFiltConditionList) {

		this.removeFiltConditionList = removeFiltConditionList;
	}

	private List<FiltCondition> getRemoveFiltConditionList() {
		return removeFiltConditionList;
	}

	private boolean validateData() {
		List list = getCheckableItemList();
		if (tfReportName.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "报表名称不能为空！", "提示！", 0);
			return true;
		}
		if (cbbAimObject.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(this, "数据来源不能为空！", "提示！", 0);
			return true;
		}
		if (list.size() == 0) {

			JOptionPane.showMessageDialog(this, "分组栏位不能为空！", "提示！", 0);
			return true;
		}
		if (cbbStatColumn.getSelectedItem().equals("")
				|| cbbStatColumn.getSelectedItem() == null) {

			JOptionPane.showMessageDialog(this, "统计栏位不能为空！", "提示！", 0);
			return true;
		}
		if (cbbOperator1.getSelectedItem() == null
				|| cbbOperator1.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(this, "统计条件不能为空！", "提示！", 0);
			return true;
		}
		if (tfStatValue.getText() == null
				|| tfStatValue.getText().equalsIgnoreCase("")) {
			JOptionPane.showMessageDialog(this, "统计条件不能为空！", "提示！", 0);
			return true;
		}

		if (cbSum.getSelectedObjects() == null&&cbAvg.getSelectedObjects() == null
				&&cbMin.getSelectedObjects() == null&&cbMax.getSelectedObjects() == null) {
			JOptionPane.showMessageDialog(this, "统计方式不能为空！", "提示！", 0);
			return true;
		}

		return false;
	}

	/**
	 * This method initializes listFiltCondition
	 * 
	 * @return javax.swing.JList
	 */
	private JList getListFiltCondition() {
		if (listFiltCondition == null) {
			listFiltCondition = new JList();
		}
		return listFiltCondition;
	}

	private void setLogic(String logic) {
		this.logic = logic;
	}

	private String getLogic() {
		return logic;
	}

	private boolean checkNull() { // 检查是否为空
		if (cbbFieldName.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(this, "字段名称不能为空！", "提示！", 0);
			return true;
		}
		if (tfValue.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "判断值不能为空！", "提示！", 0);
			return true;
		}
		if (cbbOperator.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(this, "判断符号不能为空！", "提示！", 0);
			return true;
		}

		return false;
	}

	private boolean checknull1() {
		if (this.cbbAimObject.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(this, "数据来源不能为空！", "提示！", 0);
			return true;
		}
		if (cbbStatColumn.getSelectedItem().toString().equalsIgnoreCase("")) {

			JOptionPane.showMessageDialog(this, "统计条件不能为空！", "提示！", 0);

			return true;
		}

		return false;
	}

	private void clearData() {
		tfValue.setText("");
		cbbFieldName.setSelectedItem("");
		cbbOperator.setSelectedItem(null);

	}

	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(105, 25, 50, 20));
			jLabel9.setText("平均值");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(30, 65, 50, 20));
			jLabel8.setText("最小值");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(105, 65, 50, 20));
			jLabel7.setText("最大值");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(30, 25, 50, 20));
			jLabel4.setText("求和");
			jPanel8 = new JPanel();
			jPanel8.setLayout(null);
			jPanel8.setBounds(new Rectangle(246, 11, 168, 115));
			jPanel8.setBorder(BorderFactory.createTitledBorder(null,
					"\u7edf\u8ba1\u65b9\u5f0f",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null,
					SystemColor.activeCaption));
			jPanel8.add(getCbSum(), null);
			jPanel8.add(getCbAvg(), null);
			jPanel8.add(getCbMin(), null);
			jPanel8.add(getCbMax(), null);
			jPanel8.add(jLabel4, null);
			jPanel8.add(jLabel7, null);
			jPanel8.add(jLabel8, null);
			jPanel8.add(jLabel9, null);
		}
		return jPanel8;
	}

	/**
	 * This method initializes cbSum
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbSum() {
		if (cbSum == null) {
			cbSum = new JCheckBox();
			cbSum.setBounds(new Rectangle(10, 25, 20, 20));
			cbSum.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checknull1()) {
						return;
					}
					// groupValueByStatTypeList = null;
					cbbStatValue.removeAllItems();
					List<StatTypeValue> statTypeValueList = getStatTypeValueList();
					StatTypeValue statTypeValue = new StatTypeValue();
					String statValue = cbbStatColumn.getSelectedItem()
							.toString();
					String code = ((ItemProperty) cbbStatColumn
							.getSelectedItem()).getCode();
					if (e.getSource() == cbSum
							&& cbSum.getSelectedObjects() != null) {
						statTypeValue.setCode("SUM");
						statTypeValue.setName("求和");
						statTypeValue.setIsSelect(1);
						statTypeValueList.add(statTypeValue);

					}
					if (cbSum.getSelectedObjects() == null) {
						for (int i = 0; i < statTypeValueList.size(); i++) {
							if (statTypeValueList.get(i).getCode()
									.equalsIgnoreCase("SUM")) {
								statTypeValueList.remove(statTypeValueList
										.get(i));
							}

						}

					}

					// setGroupValueByStatTypeList(null);
					setStatTypeValueList(statTypeValueList);
					//					cbbStatValue.removeAllItems();
					groupValueByStatTypeList = new ArrayList();
					for (int i = 0; i < statTypeValueList.size(); i++) {
						GroupValue groupValueByStatType = new GroupValue();
						//						
						String enName = statTypeValueList.get(i).getCode()
								+ "(" + code + ")";
						String CnName = statValue + "("
								+ statTypeValueList.get(i).getName() + ")";
							cbbStatValue.addItem(new ItemProperty(enName, CnName));

						groupValueByStatType.setCode(enName);
						groupValueByStatType.setName(CnName);
						groupValueByStatType.setGroupType(1);
						groupValueByStatTypeList.add(groupValueByStatType);
					}
					setGroupValueByStatTypeList(groupValueByStatTypeList);
					setState();
				}

			});
		}
		return cbSum;
	}

	
	/**
	 * This method initializes cbAvg
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbAvg() {
		if (cbAvg == null) {
			cbAvg = new JCheckBox();
			cbAvg.setBounds(new Rectangle(85, 25, 20, 20));
			cbAvg.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checknull1()) {
						return;
					}
					// groupValueByStatTypeList = null;
					cbbStatValue.removeAllItems();
					List<StatTypeValue> statTypeValueList = getStatTypeValueList();
					StatTypeValue statTypeValue = new StatTypeValue();
					String statValue = cbbStatColumn.getSelectedItem()
							.toString();
					String code = ((ItemProperty) cbbStatColumn
							.getSelectedItem()).getCode();
					if (e.getSource() == cbAvg
							&& cbAvg.getSelectedObjects() != null) {
						statTypeValue.setCode("AVG");
						statTypeValue.setName("平均值");
						statTypeValue.setIsSelect(1);
						statTypeValueList.add(statTypeValue);

					}
					if (cbAvg.getSelectedObjects() == null) {
						for (int i = 0; i < statTypeValueList.size(); i++) {
							if (statTypeValueList.get(i).getCode()
									.equalsIgnoreCase("AVG")) {
								statTypeValueList.remove(statTypeValueList
										.get(i));
							}
						}

					}
					setStatTypeValueList(statTypeValueList);
					//					cbbStatValue.removeAllItems();
					groupValueByStatTypeList = new ArrayList();
					for (int i = 0; i < statTypeValueList.size(); i++) {
						GroupValue groupValueByStatType = new GroupValue();

						String enName = statTypeValueList.get(i).getCode()
								+ "(" + code + ")";
						String CnName = statValue + "("
								+ statTypeValueList.get(i).getName() + ")";
							cbbStatValue.addItem(new ItemProperty(enName, CnName));

						groupValueByStatType.setCode(enName);
						groupValueByStatType.setName(CnName);
						groupValueByStatType.setGroupType(1);
						groupValueByStatTypeList.add(groupValueByStatType);
					}
					setGroupValueByStatTypeList(groupValueByStatTypeList);
					setState();
				}

			});
		}
		return cbAvg;
	}

	/**
	 * This method initializes cbMin
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbMin() {
		if (cbMin == null) {
			cbMin = new JCheckBox();
			cbMin.setBounds(new Rectangle(10, 65, 20, 20));
			cbMin.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checknull1()) {
						return;
					}
					// groupValueByStatTypeList = null;
					cbbStatValue.removeAllItems();
					List<StatTypeValue> statTypeValueList = getStatTypeValueList();
					StatTypeValue statTypeValue = new StatTypeValue();
					String statValue = cbbStatColumn.getSelectedItem()
							.toString();
					String code = ((ItemProperty) cbbStatColumn
							.getSelectedItem()).getCode();
					if (e.getSource() == cbMin
							&& cbMin.getSelectedObjects() != null) {
						statTypeValue.setCode("MIN");
						statTypeValue.setName("最小值");
						statTypeValue.setIsSelect(1);
						statTypeValueList.add(statTypeValue);

					}
					if (cbMin.getSelectedObjects() == null) {
						for (int i = 0; i < statTypeValueList.size(); i++) {
							if (statTypeValueList.get(i).getCode()
									.equalsIgnoreCase("MIN")) {
								statTypeValueList.remove(statTypeValueList
										.get(i));
							}
						}

					}
					setStatTypeValueList(statTypeValueList);
					//					cbbStatValue.removeAllItems();
					groupValueByStatTypeList = new ArrayList();
					for (int i = 0; i < statTypeValueList.size(); i++) {
						GroupValue groupValueByStatType = new GroupValue();

						String enName = statTypeValueList.get(i).getCode()
								+ "(" + code + ")";
						String CnName = statValue + "("
								+ statTypeValueList.get(i).getName() + ")";
							cbbStatValue.addItem(new ItemProperty(enName, CnName));

						groupValueByStatType.setCode(enName);
						groupValueByStatType.setName(CnName);
						groupValueByStatType.setGroupType(1);
						groupValueByStatTypeList.add(groupValueByStatType);
					}
					setGroupValueByStatTypeList(groupValueByStatTypeList);
					setState();
				}

			});
		}
		return cbMin;
	}

	/**
	 * This method initializes cbMax
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbMax() {
		if (cbMax == null) {
			cbMax = new JCheckBox();
			cbMax.setBounds(new Rectangle(85, 65, 20, 20));
			cbMax.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checknull1()) {
						return;
					}
					// groupValueByStatTypeList = null;
					cbbStatValue.removeAllItems();
					List<StatTypeValue> statTypeValueList = getStatTypeValueList();
					StatTypeValue statTypeValue = new StatTypeValue();
					String statValue = cbbStatColumn.getSelectedItem()
							.toString();
					String code = ((ItemProperty) cbbStatColumn
							.getSelectedItem()).getCode();
					if (e.getSource() == cbMax
							&& cbMax.getSelectedObjects() != null) {
						statTypeValue.setCode("MAX");
						statTypeValue.setName("最大值");
						statTypeValue.setIsSelect(1);
						statTypeValueList.add(statTypeValue);

					}
					if (cbMax.getSelectedObjects() == null) {
						for (int i = 0; i < statTypeValueList.size(); i++) {
							if (statTypeValueList.get(i).getCode()
									.equalsIgnoreCase("MAX")) {
								statTypeValueList.remove(statTypeValueList
										.get(i));

							}
						}
					}
					//					cbbStatValue.removeAllItems();
					setStatTypeValueList(statTypeValueList);
					groupValueByStatTypeList = new ArrayList();
					for (int i = 0; i < statTypeValueList.size(); i++) {
						GroupValue groupValueByStatType = new GroupValue();

						String enName = statTypeValueList.get(i).getCode()
								+ "(" + code + ")";
						String CnName = statValue + "("
								+ statTypeValueList.get(i).getName() + ")";
							cbbStatValue.addItem(new ItemProperty(enName, CnName));

						groupValueByStatType.setCode(enName);
						groupValueByStatType.setName(CnName);
						groupValueByStatType.setGroupType(1);
						groupValueByStatTypeList.add(groupValueByStatType);
					}
					setGroupValueByStatTypeList(groupValueByStatTypeList);
					setState();
				}

			});
		}
		return cbMax;
	}

	private void setStatTypeValueList(List<StatTypeValue> statTypeValueList) {
		this.statTypeValueList = statTypeValueList;
	}

	private List<StatTypeValue> getStatTypeValueList() {

		return statTypeValueList;
	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {
			if (dataState == DataState.EDIT) { // 编辑
				Object temp = tableModel.getCurrentRow();
				if (temp != null) {
					if (temp instanceof SelectCondition) {
						selectCondition = (SelectCondition) temp;
						isMasterTable = true;
					} else if (temp instanceof FiltCondition) {
						selectCondition = ((FiltCondition) temp)
								.getSelectCondition();
						isMasterTable = false;
					}

				}
				initUI(); // 初始化控件
				initUIData();// 初始化数据
				setState();
			} else if (dataState == DataState.ADD) { // 新增

				initUI();
				setState();
			} else if (dataState == DataState.BROWSE) {
				Object temp = tableModel.getCurrentRow();
				if (temp != null) {
					if (temp instanceof SelectCondition) {
						selectCondition = (SelectCondition) temp;
						isMasterTable = true;
					} else if (temp instanceof FiltCondition) {
						selectCondition = ((FiltCondition) temp)
								.getSelectCondition();

						isMasterTable = false;
					}
				}
				initUI(); // 初始化控件
				initUIData();// 初始化数据
				setContainerEnabled(this, false);

			}
			initUIJList();

		}
		super.setVisible(isFlag);
	}

	private void initUI() {

		this.cbbAimObject.setUI(new CustomBaseComboBoxUI(250));

		this.cbbStatColumn.setUI(new CustomBaseComboBoxUI(250));

		this.cbbGroupingColumn.setUI(new CustomBaseComboBoxUI(250));

	}

	private void initUIData() {
		List<FiltCondition> statCondition = getStatCondition();
		List<GroupValue> groupValueList = new ArrayList<GroupValue>();
		if (selectCondition.getAimObject() != null) {

			cbbAimObject.setSelectedIndex(ItemProperty.getIndexByCode(
					selectCondition.getAimObject(), cbbAimObject));

		} else {
			cbbAimObject.setSelectedItem(null);
		}
		if (selectCondition.getStatColumn() != null) {
			cbbStatColumn.setSelectedIndex(ItemProperty.getIndexByCode(
					selectCondition.getStatColumn(), cbbStatColumn));
		} else {
			cbbStatColumn.setSelectedItem(null);
		}

		List<StatTypeValue> statTypeValueList = getStatTypeValue();
		setStatTypeValueList(statTypeValueList);

		for (int i = 0; i < statTypeValueList.size(); i++) {

			String statColumnEn = ((ItemProperty) cbbStatColumn
					.getSelectedItem()).getCode();
			String statColumnCn = ((ItemProperty) cbbStatColumn
					.getSelectedItem()).getName();
			if (statTypeValueList.get(i).getCode().equalsIgnoreCase("SUM")) {
				cbSum.setSelected(true);
			}
			if (statTypeValueList.get(i).getCode().equalsIgnoreCase("AVG")) {
				cbAvg.setSelected(true);
			}
			if (statTypeValueList.get(i).getCode().equalsIgnoreCase("MIN")) {
				cbMin.setSelected(true);
			}
			if (statTypeValueList.get(i).getCode().equalsIgnoreCase("MAX")) {
				cbMax.setSelected(true);
			}
			GroupValue groupValue = new GroupValue();
			String code = "";
			String name = "";
			String enName = "";
			String cnName = "";
			enName = ((ItemProperty) cbbStatColumn.getSelectedItem()).getCode();
			cnName = ((ItemProperty) cbbStatColumn.getSelectedItem()).getName();

			code = statTypeValueList.get(i).getCode() + " (" + enName + " )";
			name = cnName + "( " + statTypeValueList.get(i).getName() + " )";
			groupValue.setCode(code);
			groupValue.setName(name);
			groupValue.setGroupType(1);
			groupValue.setId(selectCondition.getId());
			groupValueList.add(groupValue);

			// if (statTypeValueList.get(i).getCode().equalsIgnoreCase("COUNT"))
			// {
			// cbCount.setSelected(true);
			// }
			cbbStatValue.addItem(new ItemProperty(statTypeValueList.get(i)
					.getCode()
					+ "(" + statColumnEn + ")", statColumnCn + "("
					+ statTypeValueList.get(i).getName() + ")"));
		}
		setGroupValueByStatTypeList(groupValueList);
		for (int i = 0; i < statCondition.size(); i++) {
			cbbStatValue.setSelectedIndex(ItemProperty.getIndexByCode(
					statCondition.get(i).getEnFieldName(), cbbStatValue));
			tfStatValue.setText(statCondition.get(i).getValue());
			cbbOperator1.setSelectedIndex(ItemProperty.getIndexByCode(
					statCondition.get(i).getOperator(), cbbOperator1));

		}

		tfReportName.setText(selectCondition.getReportName());

	}

	/**
	 * 设置容器除JLabel外所有控件的enabled属性
	 */
	protected void setContainerEnabled(Container container, boolean isFlag) {
		for (int i = 0; i < container.getComponentCount(); i++) {
			Component component = container.getComponent(i);
			if ((component instanceof JButton)) {
				component.setEnabled(isFlag);
			} else if (component instanceof Container) {
				setContainerEnabled((Container) component, isFlag);
			}
		}
	}

	private void initUIJList() {
		if (selectCondition != null) {
			List<FiltCondition> filtConditionList = getFiltCondition();
			for (int i = 0; i < filtConditionList.size(); i++) {
				vectorCondition.addElement(filtConditionList.get(i));
			}
			setAddFiltConditionList(filtConditionList);
			this.listFiltCondition.setListData(vectorCondition);
			this.listFiltCondition
					.setCellRenderer(new ConditionListCellRenderer());

		}
	}

	private List<FiltCondition> getFiltCondition() {
		List<FiltCondition> filtConditionList = aptitudeReportAction
				.findFiltCondition(new Request(CommonVars.getCurrUser()),
						selectCondition.getId(), "1");
		return filtConditionList;
	}

	private List<FiltCondition> getStatCondition() {
		List<FiltCondition> filtConditionList = aptitudeReportAction
				.findFiltCondition(new Request(CommonVars.getCurrUser()),
						selectCondition.getId(), "2");
		return filtConditionList;
	}

	private List<StatTypeValue> getStatTypeValue() {
		List<StatTypeValue> statTypeValueList = aptitudeReportAction
				.findStatTypeValue(new Request(CommonVars.getCurrUser()),
						selectCondition.getId());

		return statTypeValueList;
	}

	private void setDataType(String dataType) {
		this.dataType = dataType;
	}

	private String getDataType() {
		return dataType;
	}

	private void setGroupValueByStatTypeList(
			List<GroupValue> groupValueByStatTypeList) {
		this.groupValueByStatTypeList = groupValueByStatTypeList;
	}

	private List<GroupValue> getGroupValueByStatTypeList() {
		return groupValueByStatTypeList;
	}
	
	private void getValiDate(){

		JOptionPane.showMessageDialog(this, "日期格式不正确！正确格式为"+
				"2006-01-01"+"!"+"请重新输入!!!",
				"提示！", 0);

	}

	private void remove(FiltCondition condition){
		vectorCondition.removeElement(condition);
		removeFiltConditionList.add(condition);
		addFiltConditionList.remove(condition);
		setAddFiltConditionList(addFiltConditionList);
		setRemoveFiltConditionList(removeFiltConditionList);
		this.listFiltCondition.setListData(vectorCondition);
		this.listFiltCondition.setCellRenderer(new ConditionListCellRenderer());


	}

}
