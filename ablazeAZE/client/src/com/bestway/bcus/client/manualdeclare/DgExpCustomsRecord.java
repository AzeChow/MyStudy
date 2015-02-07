/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.Color;
import java.awt.Component;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcs.contractstat.entity.ImpExpCustomsDeclarationCommInfo;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.enc.DgExportCustomsDeclaration;
import com.bestway.bcus.client.enc.DgImportCustomsDeclaration;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.ReportControl;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * 
 * 
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgExpCustomsRecord extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JTableListModel tableModel = null;

	private EncAction encAction = null;

	private List list = null;

	private MaterialManageAction materialManageAction = null;

	private ManualDeclareAction manualDecleareAction = null;

	private SystemAction systemAction = null;
	
	private JButton btnRefCustom = null;

	private JButton btnRefQingDan = null;

	/**
	 * This is the default constructor
	 */
	public DgExpCustomsRecord() {
		super();
		initialize();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			if(list == null) {
				list = new ArrayList();
			}
			initTable(list);
		}
		super.setVisible(b);
	}
	
	/**
	 * @param seqNum
	 *            料件序号
	 * @param customer
	 *            客户供应商
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param version 版本
	 * @param isDeclaration
	 *            是否为申报日期
	 * @param isEffect
	 *            是否生效
	 * @param isDept
	 *            是否分事业部
     
	 * @return 有效期内符合条件的进口料件情况
	 */
	public void showData(Integer seqNum, String customer, String impExpType, Date beginDate,
			Date endDate,String version, boolean isDeclaration, int isEffect,
			boolean isDept, List deptCode, boolean impExpFlag) {
		
		
		list = encAction.findImpExpCommInfoList(new Request(CommonVars
				.getCurrUser()), impExpFlag, seqNum, customer,impExpType, beginDate,
				endDate,version,isDeclaration, isDept, deptCode, isEffect);

		if (list != null) {
			initTable(list);
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("出口成品报关登记表");
		this.setSize(744, 454);
		this.setContentPane(getJContentPane());
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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getBtnRefCustom());
			jToolBar.add(getBtnRefQingDan());
			jToolBar.add(getJButton2());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("查询");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgQueryCondition dg = new DgQueryCondition();
					dg.setImport(false);
					dg.setVisible(true);
					list = dg.getLsResult();
					if (list != null) {
						initTable(list);
					}
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	public String formatBig(String num, int digits) {
		double unroundedValue = 0;
		if (num == null || "".equals(num)) {
			return "0";
		}
		unroundedValue = Double.parseDouble(num);
		int x = 1;
		for (int i = 0; i < digits; i++) {
			x = x * 10;
		}
		double intPortion = Math.floor(unroundedValue);
		double unroundedDecimalPortion = (unroundedValue - intPortion);
		double roundedDecimalPortion = Math.round(unroundedDecimalPortion * x);
		roundedDecimalPortion = roundedDecimalPortion / x;
		double total = intPortion + roundedDecimalPortion;
		return (new Double(total)).toString();
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("打印");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null) {
						JOptionPane.showMessageDialog(DgExpCustomsRecord.this,
								"没有可打印的记录！", "提示", 2);
						return;
					}
					CommonDataSource imgExgDS = new CommonDataSource(list);
					List company = new Vector(); // 只有一条记录
					company.add(CommonVars.getCurrUser().getCompany());
					CommonDataSource companyDS = new CommonDataSource(company);
					int i = list.size() - 1;

					Double sumPrice = ((ImpExpCustomsDeclarationCommInfo) list
							.get(i)).getSumPrice();
					String sumPrice1 = CommonVars
							.formatDoubleToString(sumPrice);

					Double sumPriceFg = ((ImpExpCustomsDeclarationCommInfo) list
							.get(i)).getSumPriceFg();
					String sumPriceFg1 = CommonVars
							.formatDoubleToString(sumPriceFg); // 总价值

					Integer bgdNum = ((ImpExpCustomsDeclarationCommInfo) list
							.get(i)).getBgdNum();

					System.out.print("总金额:" + sumPrice1);
					InputStream masterReportStream = DgExpCustomsRecord.class
							.getResourceAsStream("report/ImpExpCustomsRecord.jasper");
					InputStream detailReportStream = DgExpCustomsRecord.class
							.getResourceAsStream("report/ImpExpCustomsRecordSub.jasper");
					try {
						JasperReport detailReport = (JasperReport) JRLoader
								.loadObject(detailReportStream);
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("printDate", CommonVars.nowToDate());
						parameters.put("reportTitle", "出口成品报关登记表");
						parameters.put("companyName", CommonVars.getCurrUser()
								.getCompany().getName());
						parameters.put("imgExgDS", imgExgDS);// 子数据源
						parameters.put("detailReport", detailReport);// 子报表
						parameters.put("sumPrice", sumPrice1);// 总金额（美圆）
						parameters.put("sumPriceFG", sumPriceFg1);// 总价值
						parameters.put("bgdNum", String.valueOf(bgdNum));
						JasperPrint jasperPrint;
						jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, companyDS);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("关闭");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton2;
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		JTableListModelAdapter tableModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("手册编号", "baseCustomsDeclaration.emsHeadH2k",
						100));// 1
				list.add(addColumn("合同号", "baseCustomsDeclaration.contract",
						100));// 2
				list.add(addColumn("备案序号", "commSerialNo", 100));// 3
				list.add(addColumn("报关日期",
						"baseCustomsDeclaration.declarationDate", 100));// 4
				list.add(addColumn("运输工具名称",
						"baseCustomsDeclaration.conveyance", 100));// 5
				list.add(addColumn("报关单号",
						"baseCustomsDeclaration.customsDeclarationCode", 100));// 6
				list.add(addColumn("报关单流水号",
						"baseCustomsDeclaration.serialNumber", 100));// 7

				list.add(addColumn("商品项号", "serialNumber", 100));// 8
				list.add(addColumn("商品编码", "complex.code", 100));// 9
				list.add(addColumn("品名", "commName", 100));// 10
				list.add(addColumn("规格", "commSpec", 100));// 11
				list.add(addColumn("单位", "unit.name", 50));// 12
				list
						.add(addColumn("币制", "baseCustomsDeclaration.currency",
								50));// 13
				list.add(addColumn("美元金额", "dollarTotalPrice", 100));
				list.add(addColumn("数量", "commAmount", 100));// 14
				list.add(addColumn("价值", "commTotalPrice", 100));// 15
				list.add(addColumn("数量累计", "commAddUpAmount", 100));// 16
				list.add(addColumn("最终目的国", "country.name", 100));// 17
				list.add(addColumn("进出口类型",
						"baseCustomsDeclaration.impExpType", 100));// 18
				list.add(addColumn("贸易方式",
						"baseCustomsDeclaration.tradeMode.name", 100));// 19

				list.add(addColumn("客户名称",
						"baseCustomsDeclaration.customer.name", 100));// 20
				list.add(addColumn("法定数量", "firstAmount", 100));// 21
				list.add(addColumn("法定单位", "legalUnit.name", 100));// 22
				list.add(addColumn("报关单单价", "commUnitPrice", 100));// 23
				list.add(addColumn("毛重", "grossWeight", 100));// 24
				list.add(addColumn("净重", "netWeight", 100));// 25
				list.add(addColumn("总重量", "grossWeight", 100));// 26
				list.add(addColumn("车牌号",
						"baseCustomsDeclaration.billOfLading", 100));// 27
				list.add(addColumn("事业部", "projectDept.name", 100));// 26
				list.add(addColumn("版本号", "version", 100));//
				list.add(addColumn("统一编号",
						"baseCustomsDeclaration.unificationCode", 100));// 6
				List lists = systemAction.findReportControl(new Request(
						CommonVars.getCurrUser()));
				System.out.println("登记表lists=" + lists.size());
				if (lists != null && lists.size() > 0) {
					ReportControl reportControl = null;
					for (int i = 0; i < lists.size(); i++) {
						reportControl = (ReportControl) lists.get(0);
					}
					if (reportControl.getSecondLegalUnit() != null
							&& reportControl.getSecondLegalUnit().equals(
									new Boolean(true))) {
						list.add(addColumn("第二法定单位", "secondLegalUnit.name",
								100));// 27
					}
					if (reportControl.getSecondAmount() != null
							&& reportControl.getSecondAmount().equals(
									new Boolean(true))) {
						list.add(addColumn("第二法定数量", "secondAmount", 100));// 28
					}
					if (reportControl.getUses() != null
							&& reportControl.getUses()
									.equals(new Boolean(true))) {
						list.add(addColumn("用途", "uses.name", 100));// 29
					}
					if (reportControl.getDeclarationCustoms() != null
							&& reportControl.getDeclarationCustoms().equals(
									new Boolean(true))) {
						list
								.add(addColumn(
										"报送海关",
										"baseCustomsDeclaration.declarationCustoms.name",
										100));// 30
					}
					if (reportControl.getLevyMode() != null
							&& reportControl.getLevyMode().equals(
									new Boolean(true))) {
						list.add(addColumn("征减免税方式", "levyMode.name", 100));// 30
					}
					if (reportControl.getPieces() != null
							&& reportControl.getPieces().equals(
									new Boolean(true))) {
						list.add(addColumn("件数", "pieces", 100));// 30
					}
					if (reportControl.getWrapType() != null
							&& reportControl.getWrapType().equals(
									new Boolean(true))) {
						list.add(addColumn("包装方式",
								"baseCustomsDeclaration.wrapType.name", 100));// 30
					}
					if (reportControl.getCertificateCode() != null
							&& reportControl.getCertificateCode().equals(
									new Boolean(true))) {
						list.add(addColumn("备注证件代码",
								"baseCustomsDeclaration.certificateCode", 100));// 30
					}
					if (reportControl.getCustoms() != null
							&& reportControl.getCustoms().equals(
									new Boolean(true))) {
						list.add(addColumn("进出口口岸",
								"baseCustomsDeclaration.customs.name", 100));// 30
					}
					if (reportControl.getImpExpDate() != null
							&& reportControl.getImpExpDate().equals(
									new Boolean(true))) {
						list.add(addColumn("进出口日期",
								"baseCustomsDeclaration.impExpDate", 100));// 30
					}
					if (reportControl.getTransferMode() != null
							&& reportControl.getTransferMode().equals(
									new Boolean(true))) {
						list
								.add(addColumn(
										"运输方式",
										"baseCustomsDeclaration.transferMode.name",
										100));// 30
					}
					if (reportControl.getMemos() != null
							&& reportControl.getMemos().equals(
									new Boolean(true))) {
						list.add(addColumn("备注其他说明",
								"baseCustomsDeclaration.memos", 100));// 30
					}
					if (reportControl.getLevyKind() != null
							&& reportControl.getLevyKind().equals(
									new Boolean(true))) {
						list.add(addColumn("征免性质",
								"baseCustomsDeclaration.levyKind.name", 100));// 30
					}
					if (reportControl.getLicense() != null
							&& reportControl.getLicense().equals(
									new Boolean(true))) {
						list.add(addColumn("许可证号",
								"baseCustomsDeclaration.license", 100));// 30
					}
					if (reportControl.getCountryOfLoadingOrUnloading() != null
							&& reportControl.getCountryOfLoadingOrUnloading()
									.equals(new Boolean(true))) {
						list
								.add(addColumn(
										"起运国/运抵国",
										"baseCustomsDeclaration.countryOfLoadingOrUnloading.name",
										100));// 30
					}
					if (reportControl.getPredock() != null
							&& reportControl.getPredock().equals(
									new Boolean(true))) {
						list.add(addColumn("码头",
								"baseCustomsDeclaration.predock.name", 100));// 30
					}

					if (reportControl.getDomesticDestinationOrSource() != null
							&& reportControl.getDomesticDestinationOrSource()
									.equals(new Boolean(true))) {
						list
								.add(addColumn(
										"境内目的地",
										"baseCustomsDeclaration.domesticDestinationOrSource.name",
										100));// 30
					}
					if (reportControl.getPortOfLoadingOrUnloading() != null
							&& reportControl.getPortOfLoadingOrUnloading()
									.equals(new Boolean(true))) {
						list
								.add(addColumn(
										"装货港/指运港",
										"baseCustomsDeclaration.portOfLoadingOrUnloading.name",
										100));// 30
					}
					if (reportControl.getAuthorizeFile() != null
							&& reportControl.getAuthorizeFile().equals(
									new Boolean(true))) {
						list.add(addColumn("核销单号",
								"baseCustomsDeclaration.authorizeFile", 100));// 30
					}
					if (reportControl.getBondedWarehouse() != null
							&& reportControl.getBondedWarehouse().equals(
									new Boolean(true))) {
						list.add(addColumn("保税仓库",
								"baseCustomsDeclaration.bondedWarehouse", 100));// 30
					}
					if (reportControl.getTransac() != null
							&& reportControl.getTransac().equals(
									new Boolean(true))) {
						list.add(addColumn("成交方式",
								"baseCustomsDeclaration.transac.name", 100));// 30
					}
					if (reportControl.getGrossWeight() != null
							&& reportControl.getGrossWeight().equals(
									new Boolean(true))) {
						list.add(addColumn("总毛重",
								"baseCustomsDeclaration.grossWeight", 100));// 30
					}
					if (reportControl.getNetWeight() != null
							&& reportControl.getNetWeight().equals(
									new Boolean(true))) {
						list.add(addColumn("总净重",
								"baseCustomsDeclaration.netWeight", 100));// 30
					}
					if (reportControl.getRelativeManualNo() != null
							&& reportControl.getRelativeManualNo().equals(
									new Boolean(true))) {
						list
								.add(addColumn(
										"关联手册号",
										"baseCustomsDeclaration.relativeManualNo",
										100));// 30
					}
					if (reportControl.getCommodityNum() != null
							&& reportControl.getCommodityNum().equals(
									new Boolean(true))) {
						list.add(addColumn("总件数",
								"baseCustomsDeclaration.commodityNum", 100));// 30
					}
					if (reportControl.getContainerNum() != null
							&& reportControl.getContainerNum().equals(
									new Boolean(true))) {
						list.add(addColumn("集装箱号",
								"baseCustomsDeclaration.containerNum", 100));// 30
					}
					if (reportControl.getAttachedBill() != null
							&& reportControl.getAttachedBill().equals(
									new Boolean(true))) {
						list.add(addColumn("随附单据",
								"baseCustomsDeclaration.attachedBill", 100));// 30
					}
					if (reportControl.getRelativeId() != null
							&& reportControl.getRelativeId().equals(
									new Boolean(true))) {
						list.add(addColumn("关联报关单号",
								"baseCustomsDeclaration.relativeId", 100));// 30
					}
					if (reportControl.getRelativeId() != null
							&& reportControl.getRelativeId().equals(
									new Boolean(true))) {
						list.add(addColumn("关封号",
								"baseCustomsDeclaration.customsEnvelopBillNo",
								100));// 30
					}
					if (reportControl.getInvoiceCode() != null
							&& reportControl.getInvoiceCode().equals(
									new Boolean(true))) {
						list.add(addColumn("发票号",
								"baseCustomsDeclaration.invoiceCode", 100));// 30
					}
					
					if (reportControl.getPreCustomsDeclarationCode() != null
							&& reportControl.getPreCustomsDeclarationCode().equals(
									new Boolean(true))) {
						list.add(addColumn("预录入号",
								"baseCustomsDeclaration.preCustomsDeclarationCode", 100));
					}
					if(reportControl.getCreater() != null
							&& reportControl.getCreater().equals(
									new Boolean(true))){
						list.add(addColumn("录入员"
								,"baseCustomsDeclaration.creater.userName",80));
					}
					if (reportControl.getDetailNote() != null
							&& reportControl.getDetailNote().equals(
									new Boolean(true))) {
						list.add(addColumn("表体备注",
								"detailNote", 100));// 30
					}
					
					if(Boolean.TRUE.equals(reportControl.getCustomerCode())){
						int index = findIxdex(list,"客户名称");
						list.add(index,addColumn("客户代码",
								"baseCustomsDeclaration.customer.code", 100));
					}
				}
				return list;
			}
		};
		JTableListModel.dataBind(jTable, list, tableModelAdapter,
				ListSelectionModel.SINGLE_SELECTION);
		tableModel = (JTableListModel) jTable.getModel();
		String reportDecimalLength = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser(), true),
				BcusParameter.ReportDecimalLength);
		Integer decimalLength = 2;
		if (reportDecimalLength != null)
			decimalLength = Integer.valueOf(reportDecimalLength);
		tableModel.setAllColumnsFractionCount(decimalLength);

		CompanyOther other = CommonVars.getOther();
		if (other != null) {
			tableModel.setAllColumnsshowThousandthsign(other
					.getIsAutoshowThousandthsign() == null ? false : other
					.getIsAutoshowThousandthsign());
		}
		jTable.getColumnModel().getColumn(19).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (isSelected) {
							setForeground(new Color(0, 0, 204));
							setBackground(table.getSelectionBackground());
						} else {
							if (row == table.getSelectedRow()) {
								setForeground(table.getSelectionForeground());
								setBackground(table.getSelectionBackground());
							} else {
								setForeground(table.getForeground());
								setBackground(table.getBackground());
							}
						}
						String str = "";
						if (value != null) {
							str = ImpExpType.getImpExpTypeDesc(Integer
									.parseInt(value.toString()));
						}
						this.setText(str);
						return this;
					}
				});

		// this.tableModel.setExcelFileName(this.contract.getEmsNo());

	}
	
	/**
	 * 查找列的位置,找不到时,返回list.size
	 * @param list
	 * @param caption 列名
	 * @return
	 */
	private int findIxdex(List<JTableListColumn> list,String caption){
		int index = list.size();
		for (int i = 0; i < list.size(); i++) {
			JTableListColumn column = list.get(i);
			if(column.getCaption().equals(caption)){
				return i+1;
			}
		}
		return index;
	}
	
	/**
	 * 显示报关单。
	 */
	public void showCus() {
//		DgExportCustomsDeclaration dg = new DgExportCustomsDeclaration();
//		
//		ImpExpCustomsDeclarationCommInfo cus = (ImpExpCustomsDeclarationCommInfo) tableModel.getCurrentRow();
//		if(cus == null) {
//			throw new RuntimeException("必须选择报表中的一条记录！");
//		}
//		dg.setDataState(DataState.BROWSE);
//		dg.show(cus.getBaseCustomsDeclaration());
		
		ImpExpCustomsDeclarationCommInfo cus = (ImpExpCustomsDeclarationCommInfo) tableModel.getCurrentRow();
		if(cus == null) {
			throw new RuntimeException("必须选择报表中的一条记录！");
		}
		if(cus.getBaseCustomsDeclaration().getImpExpType()==ImpExpType.BACK_FACTORY_REWORK){
			DgImportCustomsDeclaration dg = new DgImportCustomsDeclaration();
			dg.setDataState(DataState.BROWSE);
			dg.show(cus.getBaseCustomsDeclaration());
		}else{
			DgExportCustomsDeclaration dg = new DgExportCustomsDeclaration();
			dg.setDataState(DataState.BROWSE);
			dg.show(cus.getBaseCustomsDeclaration());
		}
	}
	
	private JButton getBtnRefCustom() {
		if (btnRefCustom == null) {
			btnRefCustom = new JButton();
			btnRefCustom.setText("关联报关单");
			btnRefCustom.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showCus();
				}
			});
		}
		return btnRefCustom;
	}
	
	private JButton getBtnRefQingDan() {
		if (btnRefQingDan == null) {
			btnRefQingDan = new JButton();
			btnRefQingDan.setText("关联大小清单");
			btnRefQingDan.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgRequestTOApplyTOCustomsReport dg = new DgRequestTOApplyTOCustomsReport();
					ImpExpCustomsDeclarationCommInfo cus = (ImpExpCustomsDeclarationCommInfo) tableModel.getCurrentRow();
					if(cus == null) {
						throw new RuntimeException("必须选择报表中的一条记录！");
					}
					dg.showFromDgImpCustomsRecord(false, cus.getBaseCustomsDeclaration().getCustomsDeclarationCode(), 
							cus.getCommSerialNo() + "");
					
				}
			});
		}
		return btnRefQingDan;
	}

}
