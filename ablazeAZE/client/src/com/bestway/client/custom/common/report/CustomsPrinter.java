package com.bestway.client.custom.common.report;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contractcav.action.ContractCavAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ConvertNumberToUpperCase;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.custom.common.DgBaseExportCustomsDeclaration;
import com.bestway.client.custom.common.DgBaseImportCustomsDeclaration;
import com.bestway.client.custom.common.DgExportInvoiceItemReportSub;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.MotorCode;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;
import com.bestway.customs.common.entity.ExportInvoice;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.message.action.DzscMessageAction;
import com.bestway.dzsc.message.entity.DzscParameterSet;

/**
 * 进出口报关单打印报表公共类
 * 
 * @author ？ 2008年11月29日贺巍添加注释
 */
@SuppressWarnings("unchecked")
public class CustomsPrinter {
	/**
	 * 用来从缓存报表所需要的信息
	 */
	private Map prop = null;
	/**
	 * 报关单的基础类接口
	 */
	protected BaseEncAction baseEncAction = null;
	/**
	 * 报关单基础操作借口
	 */
	protected CustomBaseAction customBaseAction = null;
	/**
	 * 系统管理操作接口
	 */
	protected SystemAction systemAction = null;
	/**
	 * 料件管理操作接口
	 */
	private MaterialManageAction materialManageAction = null;
	// private ImpCustomsAuthorityAction impCustomsAuthorityAction = null;
	/**
	 * 电子帐册操作接口
	 */
	private ManualDeclareAction manualDecleareAction = null;
	private ContractCavAction contractCavAction = null;

	private DzscMessageAction dzscMessageAction = null;

	private EncAction encAction = null;

	/**
	 * 构造方法初始化Map
	 * 
	 * @param prop
	 */
	private CustomsPrinter(Map prop) {
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		// impCustomsAuthorityAction = (ImpCustomsAuthorityAction) CommonVars
		// .getApplicationContext().getBean("impCustomsAuthorityAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		baseEncAction = (BaseEncAction) prop.get("baseEncAction");
		contractCavAction = (ContractCavAction) CommonVars
				.getApplicationContext().getBean("contractCavAction");
		dzscMessageAction = (DzscMessageAction) CommonVars
				.getApplicationContext().getBean("dzscMessageAction");
		this.prop = prop;
	}

	/**
	 * 静态方法获取map实例
	 * 
	 * @param prop
	 * @return
	 */
	public static CustomsPrinter getInstance(Map prop) {
		return new CustomsPrinter(prop);
	}

	/**
	 * 获取集装箱号（打印使用）
	 * 
	 * @return
	 */
	protected String getContia(BaseCustomsDeclaration customsDeclaration) {
		String contain = "";
		List list = baseEncAction.findContainerByCustomsDeclaration(
				new Request(CommonVars.getCurrUser()), customsDeclaration);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String xs = ((BaseCustomsDeclarationContainer) list.get(i))
						.getContainerCode();
				if (null != customsDeclaration.getContainerNum()) {
					if (customsDeclaration.getContainerNum().length() > 10
							&& !xs.equals(customsDeclaration.getContainerNum()
									.substring(0, 11))) {
						contain = contain + xs + " ";
					}
				}
			}
		}
		if (!contain.equals("")) {
			contain = "集装箱:" + contain;
		}
		return contain;
	}

	/**
	 * 打印进口报关单
	 */
	public void doInPrint0() {

		// 非套打报关单
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");

		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");

		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");

		String attachedBillName = (String) prop.get("attachedBillName");

		int projectType = (Integer) prop.get("projectType");

		boolean noTaoda = !(Boolean) prop.get("isTaoda");

		// System.out.println("测试打印.....");

		DzscParameterSet dzscParameterSet = dzscMessageAction
				.findDzscMessageDirSet1(new Request(CommonVars.getCurrUser(),
						true));

		// 非套打 No Taoda
		if (noTaoda) {
			try {
				List contianList = baseEncAction
						.findContainerByCustomsDeclaration(new Request(
								CommonVars.getCurrUser()), customsDeclaration);

				if (contianList.size() != 0) {
					if (JOptionPane.showConfirmDialog(FmMain.getInstance(),
							"是否打印集装箱及托架?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						CustomsDeclarationSubReportDataSource
								.setContainerData(contianList);
					} else {
						CustomsDeclarationSubReportDataSource
								.setContainerData(new ArrayList());
					}
				} else {
					CustomsDeclarationSubReportDataSource
							.setContainerData(new ArrayList());
				}

				CustomsDeclarationSubReportDataSource
						.setSubReportData(commInfoModel.getList());

				InputStream masterReportStream = null;

				CompanyOther other = CommonVars.getOther();

				// 判断是否打印 显示 转关附加信息
				if (other.getIsTransitadd() == null || !other.getIsTransitadd()) {

					if (customsDeclaration.getEmsHeadH2k() != null
							&& !"".equals(customsDeclaration.getEmsHeadH2k())
							&& customsDeclaration.getEmsHeadH2k()
									.substring(0, 1).equals("H")) {

						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ImportCustomsHEmsDeclarationReport.jasper");

					} else {

						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ImportCustomsDeclarationReport.jasper");

					}

				} else {

					if (customsDeclaration.getEmsHeadH2k() != null
							&& !"".equals(customsDeclaration.getEmsHeadH2k())
							&& customsDeclaration.getEmsHeadH2k()
									.substring(0, 1).equals("H")) {

						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ImportCustomsHEmsDeclarationReportNoadd.jasper");

					} else {

						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ImportCustomsDeclarationReportNoadd.jasper");

					}
				}

				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportCustomsDeclarationCommInfoReport.jasper");

				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);

				InputStream containerReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/CustomsDeclarationContainerReport.jasper");

				JasperReport containerReport = (JasperReport) JRLoader
						.loadObject(containerReportStream);

				Map<String, Object> parameters = new HashMap<String, Object>();

				parameters.put("contia", getContia(customsDeclaration));

				// 只有是BCUS有需要才打印版本栏位
				parameters.put("projectType", String.valueOf(projectType));

				parameters
						.put("memos", StringUtils.isNotBlank(customsDeclaration
								.getMemos()) ? customsDeclaration.getMemos()
								: "");

				parameters.put("commInfoReport", commInfoReport);

				parameters.put("containerReport", containerReport);

				parameters.put("overprint", new Boolean(false));

				Company company = (Company) customsDeclaration.getCompany();

				parameters.put("linkMan", company.getLinkman());

				parameters.put("linkTel", company.getTel());

				parameters.put("tradeFlat", company.getCounFlag());

				parameters.put("coFlat", company.getOwnercounFlag());

				// 是否打印航次
				parameters.put("isPrintNo", dzscParameterSet.getIsPrintNo()
						.toString());

				// 是否打印航次
				parameters.put("isPrintToolCode", dzscParameterSet
						.getIsPrintToolCode().toString());
				// System.out.println(dzscParameterSet.getIsPrintNo().toString()
				// + "   "
				// + dzscParameterSet.getIsPrintToolCode().toString());
				// // 设置毛净重
				// if (customsDeclaration.getImpExpType() ==
				// ImpExpType.TRANSFER_FACTORY_EXPORT
				// || customsDeclaration.getImpExpType() ==
				// ImpExpType.TRANSFER_FACTORY_IMPORT) {// 转厂则保留三位小数
				// DecimalFormat f = new DecimalFormat();
				// f.setMaximumFractionDigits(3);
				// f.setGroupingSize(0);
				// parameters.put("grossWeight", customsDeclaration
				// .getGrossWeight() != null ? f
				// .format(customsDeclaration.getGrossWeight())
				// : "0.0");
				// parameters.put("netWeight", customsDeclaration
				// .getNetWeight() != null ? f
				// .format(customsDeclaration.getNetWeight()) : "0.0");
				// } else {
				// parameters.put("grossWeight", customsDeclaration
				// .getGrossWeight() != null ? String.format("%.0f",
				// customsDeclaration.getGrossWeight()) : "0");
				// parameters.put("netWeight", customsDeclaration
				// .getNetWeight() != null ? String.format("%.0f",
				// customsDeclaration.getNetWeight()) : "0");
				// }
				parameters
						.put("grossWeight",
								customsDeclaration.getGrossWeight() != null ? cutString(customsDeclaration
										.getGrossWeight().toString()) : "0");
				parameters
						.put("netWeight",
								customsDeclaration.getNetWeight() != null ? cutString(customsDeclaration
										.getNetWeight().toString()) : "0");
				String isPrintCustomWithVersion = manualDecleareAction
						.getBpara(new Request(CommonVars.getCurrUser()),
								BcusParameter.PrintCustomWithVersion);
				if (isPrintCustomWithVersion == null)
					isPrintCustomWithVersion = "0";
				parameters.put("isPrintCustomWithVersion",
						isPrintCustomWithVersion);// 用来控制是否显示版本栏位
				parameters.put("attachedBillName", attachedBillName);

				String customsEnvelopBillNo = "";

				if ((customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_IMPORT || customsDeclaration
						.getImpExpType() == ImpExpType.TRANSFER_FACTORY_EXPORT)
						&& customsDeclaration.getAttachedBill() != null
						&& customsDeclaration.getAttachedBill().contains("K")) {

					customsEnvelopBillNo = " [结转申请单号]:"
							+ customsDeclaration.getCustomsEnvelopBillNo();

				}

				parameters.put("customsEnvelopBillNo", customsEnvelopBillNo);

				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);

				DgReportViewer viewer = new DgReportViewer(jasperPrint);

				viewer.setVisible(true);

			} catch (JRException e1) {
				e1.printStackTrace();
			}

		} else {// 套打报关单
			try {

				CustomsDeclarationSubReportDataSource
						.setSubReportData(commInfoModel.getList());

				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportCustomsDeclarationReport.jasper");

				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportCustomsDeclarationCommInfoReport.jasper");

				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);

				CompanyOther other = CommonVars.getOther();

				// 判断是否打印显示转关附加信息
				if (other.getIsTransitadd() == null || !other.getIsTransitadd()) {

					if (customsDeclaration.getEmsHeadH2k() != null
							&& !"".equals(customsDeclaration.getEmsHeadH2k())
							&& customsDeclaration.getEmsHeadH2k()
									.substring(0, 1).equals("H")) {

						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ImportCustomsHEmsDeclarationReport.jasper");

					} else {

						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ImportCustomsDeclarationReport.jasper");

					}

				} else {

					if (customsDeclaration.getEmsHeadH2k() != null
							&& !"".equals(customsDeclaration.getEmsHeadH2k())
							&& customsDeclaration.getEmsHeadH2k()
									.substring(0, 1).equals("H")) {

						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ImportCustomsHEmsDeclarationReportNoadd.jasper");

					} else {

						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ImportCustomsDeclarationReportNoadd.jasper");

					}

				}

				Map<String, Object> parameters = new HashMap<String, Object>();

				parameters.put("contia", getContia(customsDeclaration));

				// 只有是BCUS有需要才打印版本栏位
				parameters.put("projectType", String.valueOf(projectType));

				parameters.put("commInfoReport", commInfoReport);

				parameters.put("overprint", new Boolean(true));

				Company company = (Company) customsDeclaration.getCompany();

				parameters.put("linkMan", company.getLinkman());

				parameters.put("linkTel", company.getTel());

				parameters.put("tradeFlat", company.getCounFlag());

				parameters.put("coFlat", company.getOwnercounFlag());

				parameters.put("isPrintNo", dzscParameterSet.getIsPrintNo()
						.toString());// 是否打印航次

				parameters.put("isPrintToolCode", dzscParameterSet
						.getIsPrintToolCode().toString());// 是否打印航次
				// // 设置毛净重
				// if (customsDeclaration.getImpExpType() ==
				// ImpExpType.TRANSFER_FACTORY_EXPORT
				// || customsDeclaration.getImpExpType() ==
				// ImpExpType.TRANSFER_FACTORY_IMPORT) {// 转厂则保留三位小数
				// DecimalFormat f = new DecimalFormat();
				// f.setGroupingSize(0);
				// f.setMaximumFractionDigits(3);
				// parameters.put("grossWeight", customsDeclaration
				// .getGrossWeight() != null ? f
				// .format(customsDeclaration.getGrossWeight())
				// : "0.0");
				// parameters.put("netWeight", customsDeclaration
				// .getNetWeight() != null ? f
				// .format(customsDeclaration.getNetWeight()) : "0.0");
				// } else {
				// parameters.put("grossWeight", customsDeclaration
				// .getGrossWeight() != null ? String.format("%.0f",
				// customsDeclaration.getGrossWeight()) : "0");
				// parameters.put("netWeight", customsDeclaration
				// .getNetWeight() != null ? String.format("%.0f",
				// customsDeclaration.getNetWeight()) : "0");
				// }
				parameters
						.put("grossWeight",
								customsDeclaration.getGrossWeight() != null ? cutString(customsDeclaration
										.getGrossWeight().toString()) : "0");
				parameters
						.put("netWeight",
								customsDeclaration.getNetWeight() != null ? cutString(customsDeclaration
										.getNetWeight().toString()) : "0");

				String isPrintCustomWithVersion = manualDecleareAction
						.getBpara(new Request(CommonVars.getCurrUser()),
								BcusParameter.PrintCustomWithVersion);

				if (isPrintCustomWithVersion == null) {

					isPrintCustomWithVersion = "0";

				}

				parameters.put("isPrintCustomWithVersion",
						isPrintCustomWithVersion);// 用来控制是否显示版本栏位

				parameters.put("attachedBillName", attachedBillName);

				String customsEnvelopBillNo = "";

				if ((customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_IMPORT || customsDeclaration
						.getImpExpType() == ImpExpType.TRANSFER_FACTORY_EXPORT)
						&& customsDeclaration.getAttachedBill() != null
						&& customsDeclaration.getAttachedBill().contains("K")) {

					customsEnvelopBillNo = " [结转申请单号]:"
							+ customsDeclaration.getCustomsEnvelopBillNo();

				}

				parameters.put("customsEnvelopBillNo", customsEnvelopBillNo);

				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);

				DgReportViewer viewer = new DgReportViewer(jasperPrint);

				viewer.setVisible(true);

			} catch (JRException e1) {

				e1.printStackTrace();

			}
		}
	}

	/**
	 * 打印报关单附页表
	 */
	public void doInPrint1() {
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		CustomsDeclarationSubReportDataSource.setSubReportData(commInfoModel
				.getList());
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		if (noTaoda) {
			try {
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImgShowListReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImgShowListSubReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);

				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isOverPrint", true);
				parameters.put("commInfoReport", commInfoReport);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoModel.getList());
			try {
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImgShowListReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImgShowListSubReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isOverPrint", false);
				parameters.put("commInfoReport", commInfoReport);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 打印发票
	 */
	public void doInPrint2() {
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		DgImportInvoiceMergerReportSet dg = new DgImportInvoiceMergerReportSet(
				true);
		dg.setBaseCustomsDeclaration(customsDeclaration);
		dg.setisImport(false);
		dg.setExp(false);
		dg.setVisible(true);

		Boolean isOk = dg.getIsOk();

		if (null == isOk || !isOk) {
			return;
		}

		if (noTaoda) {
			try {
				CustomsDeclarationSubReportDataSource
						.setSubReportData(commInfoModel.getList());
				InputStream subReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceCommodityReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isCopyPrint", Boolean.valueOf(true));
				parameters.put("subReport", subReport);
				parameters.put("isOverPrint", Boolean.valueOf(false));
				parameters.put("area", dg.getArea());
				parameters.put("dealWay", dg.getDealWay());
				InputStream reportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);
				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {

			try {
				CustomsDeclarationSubReportDataSource
						.setSubReportData(commInfoModel.getList());
				InputStream subReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceCommodityReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isCopyPrint", Boolean.valueOf(false));
				parameters.put("subReport", subReport);
				parameters.put("isOverPrint", Boolean.valueOf(true));
				InputStream reportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);
				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}

		}
	}

	public String formatBig(String amount, int bigD, boolean isZero) {
		if (amount == null || amount.equals("")) {
			amount = "0";
		}
		BigDecimal bd = new BigDecimal(amount);
		String amountStr = bd.setScale(bigD, BigDecimal.ROUND_HALF_UP)
				.toString();
		if (isZero) {
			return amountStr;
		}
		for (int i = amountStr.length(); i > 0; i--) {
			if (amountStr.substring(i - 1, i).equals("0")) {
				amountStr = amountStr.substring(0, i - 1);
			} else if (amountStr.substring(i - 1, i).equals(".")) {
				amountStr = amountStr.substring(0, i - 1);
				break;
			} else {
				break;
			}
		}
		return amountStr;
	}

	/**
	 * 打印新发票
	 * 
	 * @author sxk
	 */
	public void InvoicePrint() {
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		// JTableListModel commInfoModel = (JTableListModel) prop
		// .get("invoiceResult");
		List exportInvoiceList = (List) prop.get("invoiceResult");
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		// 所有出口商品总价格
		Double totalMoney = 0.0;
		// 所有出口商品加工总价格
		Double processTotleMoney = 0.0;
		// 所有出口商品总价格中文大写
		String totalMoneyUpperCase = "";
		// 所有出口商品加工总价格中文大写
		String processTotleMoneyUpperCase = "";

		String stotalMoney = "";
		String sprocessTotleMoney = "";
		String currName = CommonVars.getCurrName();

		Company comp = (Company) customsDeclaration.getCompany();
		if (null != exportInvoiceList && exportInvoiceList.size() > 0) {
			for (int i = 0; i < exportInvoiceList.size(); i++) {
				ExportInvoice exportInvoice = (ExportInvoice) exportInvoiceList
						.get(i);
				if (null != exportInvoice.getCommTotalPrice()
						|| "".equals(exportInvoice.getCommTotalPrice())) {
					totalMoney += exportInvoice.getCommTotalPrice();
				} else {
					totalMoney += 0.0;
				}
				if (null != exportInvoice.getProcessTotalPrice()
						|| "".equals(exportInvoice.getProcessTotalPrice())) {
					processTotleMoney += exportInvoice.getProcessTotalPrice();
				} else {
					processTotleMoney += 0.0;
				}
			}
			totalMoneyUpperCase = ConvertNumberToUpperCase.convertd(formatBig(
					String.valueOf(totalMoney), 2, true));
			processTotleMoneyUpperCase = ConvertNumberToUpperCase
					.convertd(formatBig(String.valueOf(processTotleMoney), 2,
							true));
			stotalMoney = String.valueOf(totalMoney);
			sprocessTotleMoney = String.valueOf(processTotleMoney);
		}

		if (noTaoda) {
			try {
				CustomsDeclarationSubReportDataSource
						.setSubReportData(exportInvoiceList);
				InputStream subReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceCommodityReport3.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isCopyPrint", Boolean.valueOf(true));
				parameters.put("subReport", subReport);
				parameters.put("isOverPrint", Boolean.valueOf(false));
				parameters.put("totalMoney", stotalMoney);
				parameters.put("currName", currName == null ? "" : currName);
				parameters.put("buaddress", comp.getAddress() == null ? ""
						: comp.getAddress());
				parameters.put("butel",
						comp.getButel() == null ? "" : comp.getButel());
				// 客户
				if (null != customsDeclaration.getCustomer()) {
					parameters.put("customerName", customsDeclaration
							.getCustomer().getName() == null ? ""
							: customsDeclaration.getCustomer().getName());
					parameters.put("customerNameTel", customsDeclaration
							.getCustomer().getLinkTel() == null ? ""
							: customsDeclaration.getCustomer().getLinkTel());
					parameters.put("customerNameAddre", customsDeclaration
							.getCustomer().getAddre() == null ? ""
							: customsDeclaration.getCustomer().getAddre());
				} else {
					parameters.put("customerName", " ");
					parameters.put("customerNameTel", " ");
					parameters.put("customerNameAddre", " ");
				}
				parameters.put("processTotleMoney", sprocessTotleMoney);
				parameters.put("totalMoneyUpperCase", totalMoneyUpperCase);
				parameters.put("processTotleMoneyUpperCase",
						processTotleMoneyUpperCase);
				InputStream reportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceReport3.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);
				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {

			try {
				CustomsDeclarationSubReportDataSource
						.setSubReportData(exportInvoiceList);
				InputStream subReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceCommodityReport3.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isCopyPrint", Boolean.valueOf(true));
				parameters.put("subReport", subReport);
				parameters.put("isOverPrint", Boolean.valueOf(true));
				parameters.put("totalMoney", stotalMoney);
				parameters.put("currName", currName == null ? "" : currName);
				parameters.put("buaddress", comp.getAddress() == null ? ""
						: comp.getAddress());
				parameters.put("butel",
						comp.getButel() == null ? "" : comp.getButel());
				/*
				 * System.out
				 * .println("customsDeclaration.getCustomer().getAddre()=" +
				 * customsDeclaration.getCustomer().getAddre());
				 */
				// 客户
				if (null != customsDeclaration.getCustomer()) {
					parameters.put("customerName", customsDeclaration
							.getCustomer().getName() == null ? ""
							: customsDeclaration.getCustomer().getName());
					parameters.put("customerNameTel", customsDeclaration
							.getCustomer().getLinkTel() == null ? ""
							: customsDeclaration.getCustomer().getLinkTel());
					parameters.put("customerNameAddre", customsDeclaration
							.getCustomer().getAddre() == null ? ""
							: customsDeclaration.getCustomer().getAddre());
				} else {
					parameters.put("customerName", " ");
					parameters.put("customerNameTel", " ");
					parameters.put("customerNameAddre", " ");
				}
				parameters.put("processTotleMoney", sprocessTotleMoney);
				parameters.put("totalMoneyUpperCase", totalMoneyUpperCase);
				parameters.put("processTotleMoneyUpperCase",
						processTotleMoneyUpperCase);
				InputStream reportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceReport3.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);
				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}

		}
	}

	/**
	 * 打印发票1
	 */
	public void doInPrintInvoice2() {// 非套打发票
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		if (noTaoda) {
			try {
				Company comp = (Company) customsDeclaration.getCompany();
				CustomsDeclarationSubReportDataSource
						.setSubReportData(commInfoModel.getList());
				InputStream subReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceCommodityReport2.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("subReport", subReport);
				parameters.put("outTradeCo", comp.getOutTradeCo());// 外商公司
				parameters.put("isOverPrint", Boolean.valueOf(false));
				InputStream reportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceReport2.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);
				// reportStream, parameters, ds);
				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			try {
				Company comp = (Company) customsDeclaration.getCompany();
				CustomsDeclarationSubReportDataSource
						.setSubReportData(commInfoModel.getList());
				InputStream subReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceCommodityReport2.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isOverPrint", Boolean.valueOf(true));
				parameters.put("subReport", subReport);
				parameters.put("outTradeCo", comp.getOutTradeCo());// 外商公司
				InputStream reportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceReport2.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);
				// reportStream, parameters, ds);
				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 打印发票2 sxk新增
	 */
	public void doInPrintInvoice3() {// 非套打发票
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		if (noTaoda) {
			try {
				CustomsDeclarationSubReportDataSource
						.setSubReportData(commInfoModel.getList());
				InputStream subReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceTaiPingCommodityReport2.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isCopyPrint", Boolean.valueOf(true));
				parameters.put("subReport", subReport);
				parameters.put("isOverPrint", Boolean.valueOf(true));
				InputStream reportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceTaiPingReport2.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);
				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			try {
				CustomsDeclarationSubReportDataSource
						.setSubReportData(commInfoModel.getList());
				InputStream subReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceTaiPingCommodityReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isCopyPrint", Boolean.valueOf(true));
				parameters.put("subReport", subReport);
				parameters.put("isOverPrint", Boolean.valueOf(false));
				InputStream reportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceTaiPingReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);
				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 非套打发票(太平海关专用发票)
	 */
	public void doInPrint3() {
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		if (noTaoda) {
			try {
				CustomsDeclarationSubReportDataSource
						.setSubReportData(commInfoModel.getList());
				InputStream subReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceTaiPingCommodityReport.jasper");
				CompanyOther other = CommonVars.getOther();
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isCopyPrint", Boolean.valueOf(true));
				parameters.put("subReport", subReport);
				parameters.put("isOverPrint", Boolean.valueOf(true));
				parameters.put(
						"invoiceAddress",
						other.getInvoiceAddress() == null ? "太平" : other
								.getInvoiceAddress());
				System.out.println("other.getInvoiceAddress()=="
						+ other.getInvoiceAddress());
				InputStream reportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceTaiPingReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);
				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			try {
				CustomsDeclarationSubReportDataSource
						.setSubReportData(commInfoModel.getList());
				InputStream subReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceTaiPingCommodityReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isCopyPrint", Boolean.valueOf(true));
				parameters.put("subReport", subReport);
				parameters.put("isOverPrint", Boolean.valueOf(false));
				InputStream reportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportInvoiceTaiPingReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);
				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 打印装箱单
	 */
	public void doInPrint4() {// 非套打装箱单（明门）
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		CustomsDeclarationSubReportDataSource.setSubReportData(commInfoModel
				.getList());
		String billNo = customsDeclaration.getBillListId(); // 报关清单号码
		String impexpno = baseEncAction.getImpExpNoByBillNo(new Request(
				CommonVars.getCurrUser()), billNo);
		CommonVars.setSumMoney(Double.valueOf(0));
		CommonVars.setCurrName(null);
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		if (noTaoda) {
			try {
				String homeCard = customsDeclaration.getBillOfLading() == null ? ""
						: customsDeclaration.getBillOfLading().trim();
				//
				// 汔车司机
				//
				String driver = "";
				if (homeCard != null && !homeCard.trim().equals("")) {
					MotorCode motorCode = materialManageAction
							.findMotorCodeByCode(
									new Request(CommonVars.getCurrUser()),
									homeCard);
					driver = motorCode == null || motorCode.getName() == null ? ""
							: motorCode.getName();
				}

				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCommodityEncasementReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCommodityEncasementSubReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("isOverprint", false);
				parameters.put("isExport", false);
				parameters.put("impexpno", impexpno);
				parameters.put("driver", driver);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoModel.getList());
			String billNo2 = customsDeclaration.getBillListId(); // 报关清单号码
			CommonVars.setSumMoney(Double.valueOf(0));
			String impexpno2 = baseEncAction.getImpExpNoByBillNo(new Request(
					CommonVars.getCurrUser()), billNo2);
			try {
				String homeCard = customsDeclaration.getBillOfLading() == null ? ""
						: customsDeclaration.getBillOfLading().trim();
				//
				// 汔车司机
				//
				String driver = "";
				if (homeCard != null && !homeCard.trim().equals("")) {
					MotorCode motorCode = materialManageAction
							.findMotorCodeByCode(
									new Request(CommonVars.getCurrUser()),
									homeCard);
					driver = motorCode == null || motorCode.getName() == null ? ""
							: motorCode.getName();
				}
				// System.out.println(motorCode);
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCommodityEncasementReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCommodityEncasementSubReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("isOverprint", true);
				parameters.put("isExport", false);
				parameters.put("impexpno", impexpno2);
				parameters.put("driver", driver);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 打印内地海关及香港海关陆路出/进境载货清单
	 * 
	 * @param isTD
	 */
	public void doInPrint6(boolean isTD, int ImpExpFlag) {// 非套打内地海关及香港海关陆路出/进境载货清单
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		// String attachedBillName = (String) prop.get("attachedBillName");
		int projectType = (Integer) prop.get("projectType");
		List list = new ArrayList();
		list.add(customsDeclaration);
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		if (noTaoda) {
			try {
				String homeCard = customsDeclaration.getBillOfLading();
				MotorCode motorCode = null;
				if (homeCard != null && !homeCard.trim().equals("")) {
					motorCode = materialManageAction.findMotorCodeByCode(
							new Request(CommonVars.getCurrUser()), homeCard);
				}
				//
				// 相同运输工具的所有商品明细
				//
				List<Object> otherCommList = new ArrayList<Object>();
				String conveyance = customsDeclaration.getConveyance();
				Set<BaseCustomsDeclaration> set = new HashSet<BaseCustomsDeclaration>();
				//
				// 如果运输工具不为空的时候才进行检索
				//
				System.out.println("标志" + ImpExpFlag);
				List allList = null;
				if (conveyance != null && !conveyance.trim().equals("")) {
					allList = this.baseEncAction
							.findCustomsDeclarationCommInfoByConveyance(
									new Request(CommonVars.getCurrUser()),
									conveyance, ImpExpFlag);
					Map<String, String> tempMap = new HashMap<String, String>();
					for (int i = 0; i < commInfoModel.getList().size(); i++) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) commInfoModel
								.getList().get(i);
						tempMap.put(b.getId(), b.getId());
					}
					for (Object obj : allList) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
						BaseCustomsDeclaration tempB = b
								.getBaseCustomsDeclaration();
						if (!customsDeclaration.getId().equals(tempB.getId())) {
							set.add(tempB);
						}
						if (tempMap.containsKey(b.getId())) {
							continue;
						}
						otherCommList.add(obj);
					}
				}

				//
				// 获得前6条
				//
				List<Object> tempList = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i < 6) {
						tempList.add(commInfoModel.getList().get(i));
					}
					if (i == 5) {
						break;
					}
				}
				//
				// 用 otherCommList 为 tempList 加到 6 个
				//
				if (tempList.size() < 6) {
					int count = tempList.size();
					for (int i = 0; i < otherCommList.size(); i++) {
						if (i < 6 - count) {
							tempList.add(otherCommList.get(i));
						} else {
							break;
						}
					}
				}

				String commodityNum = "";
				String grossWeight = "";
				int tempCommodityNum = 0;
				double tempGrossWeight = 0.0;
				Iterator<BaseCustomsDeclaration> iterator = set.iterator();
				while (iterator.hasNext()) {
					BaseCustomsDeclaration b = iterator.next();
					tempCommodityNum += b.getCommodityNum() == null ? 0 : b
							.getCommodityNum();
					tempGrossWeight += b.getGrossWeight() == null ? 0.0 : b
							.getGrossWeight();
				}
				tempCommodityNum += customsDeclaration.getCommodityNum() == null ? 0
						: customsDeclaration.getCommodityNum();
				tempGrossWeight += customsDeclaration.getGrossWeight() == null ? 0.0
						: customsDeclaration.getGrossWeight();
				commodityNum = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempCommodityNum));
				commodityNum += "(" + tempCommodityNum + ")件";
				grossWeight = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempGrossWeight));
				grossWeight += "(" + tempGrossWeight + ")公斤";

				if (allList == null)
					CustomsDeclarationSubReportDataSource
							.setSubReportData(tempList);
				else
					CustomsDeclarationSubReportDataSource
							.setSubReportData(allList);
				CustomsDeclarationSubReportDataSource
						.setContainerData(baseEncAction
								.findAllContainerByConveyance(new Request(
										CommonVars.getCurrUser()),
										customsDeclaration));

				InputStream subReportStream = DgBaseImportCustomsDeclaration.class

						.getResourceAsStream("report/ExportCustomsAutocarMainfestCommodityReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				// Company company = (Company) customsDeclaration.getCompany();
				Company company = (Company) CommonVars.getCurrUser()
						.getCompany();
				CustomReportDataSource subReportDataList = new CustomReportDataSource(
						tempList);
				Integer pageCount = 1;// 记录载货清单的页数
				// if (commInfoModel.getList().size() > 6)
				// pageCount = 2;
				if (allList != null) {
					Integer count = allList.size();
					if (count <= 6) {
						pageCount = 1;
					} else if (count <= 24 && count > 6) {
						pageCount = 2;
					} else if (count <= 42 && count > 24) {
						pageCount = 3;
					} else if (count <= 60 && count > 42) {
						pageCount = 4;
					} else if (count <= 78 && count > 60) {
						pageCount = 5;
					} else if (count <= 96 && count > 78) {
						pageCount = 6;
					} else {
						pageCount = 7;
					}
				}

				parameters.put("pageCount", pageCount.toString());
				ScmCoc scmcoc = customsDeclaration.getCustomer();
				parameters.put("sendCompany",
						scmcoc == null ? "" : scmcoc.getName());//
				parameters.put("sendCompanyAdd",
						scmcoc == null ? "" : scmcoc.getAddre());
				parameters.put("acceptCompany", company.getName());//
				parameters.put("acceptCompanyAdd", company.getAddress());
				parameters.put("outTradeCo", company.getOutTradeCo());
				parameters.put("isOverPrint", Boolean.valueOf(true));
				// parameters.put("acceptCompany", company.getName());// 外商公司
				// parameters.put("sendCompany", company.getOutTradeCo());//
				// 加工公司
				parameters.put("subReport", subReport);
				parameters.put("list", list);
				parameters.put("subReportDataList", subReportDataList);
				parameters.put("commodityNum", commodityNum);
				parameters.put("grossWeight", grossWeight);
				// 香港车牌
				parameters.put("hongkongCard",
						motorCode == null
								|| motorCode.getHongkongCard() == null ? ""
								: motorCode.getHongkongCard());
				// 运输公司名称
				parameters
						.put("trafficUnit",
								motorCode == null
										|| motorCode.getTrafficUnit() == null ? ""
										: motorCode.getTrafficUnit());
				parameters.put("trafficUnitAdd",
						motorCode == null
								|| motorCode.getTrafficUnitAdd() == null ? ""
								: motorCode.getTrafficUnitAdd());
				parameters.put("trafficUnitTel",
						motorCode == null
								|| motorCode.getTrafficUnitTel() == null ? ""
								: motorCode.getTrafficUnitTel());//
				parameters.put("name",
						motorCode == null || motorCode.getName() == null ? ""
								: motorCode.getName());// 司机姓名
				// BCS 时把手册编号换成合同号
				if (projectType == ProjectType.BCS) {
					parameters.put("emsNo", customsDeclaration.getContract());
				} else {
					parameters.put("emsNo", customsDeclaration.getEmsHeadH2k());
				}
				InputStream reportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomesAutocarMainfestReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);

				//
				// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
				//
				List<Object> t = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i > 5) {
						t.add(commInfoModel.getList().get(i));
					}
				}
				//
				// 用 otherCommList 加到 t 中
				//
				int outCount = 6 - commInfoModel.getList().size();
				for (int i = 0; i < otherCommList.size(); i++) {
					if (i >= outCount) {
						t.add(otherCommList.get(i));
					}
				}

				if (isTD && t.size() > 0) {
					CustomsDeclarationSubReportDataSource.setSubReportData(t);
					InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
					InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
					JasperReport commInfoReport = (JasperReport) JRLoader
							.loadObject(commInfoReportStream);
					Map<String, Object> p = new HashMap<String, Object>();

					p.put("pageCount", pageCount.toString());// 载货清单共有的页数
					p.put("commInfoReport", commInfoReport);
					JasperPrint jasperPrint2 = JasperFillManager.fillReport(
							masterReportStream, p, new CustomReportDataSource(
									list));
					for (int i = 0; i < jasperPrint2.getPages().size(); i++) {
						jasperPrint.addPage((JRPrintPage) jasperPrint2
								.getPages().get(i));
					}
				}

				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			try {

				String homeCard = customsDeclaration.getBillOfLading();
				MotorCode motorCode = null;
				if (homeCard != null && !homeCard.trim().equals("")) {
					motorCode = materialManageAction.findMotorCodeByCode(
							new Request(CommonVars.getCurrUser()), homeCard);
				}
				//
				// 相同运输工具的所有商品明细
				//
				List<Object> otherCommList = new ArrayList<Object>();
				String conveyance = customsDeclaration.getConveyance();
				Set<BaseCustomsDeclaration> set = new HashSet<BaseCustomsDeclaration>();
				//
				// 如果运输工具不为空的时候才进行检索
				//
				List allList = null;
				if (conveyance != null && !conveyance.trim().equals("")) {
					allList = this.baseEncAction
							.findCustomsDeclarationCommInfoByConveyance(
									new Request(CommonVars.getCurrUser()),
									conveyance, ImpExpFlag);
					Map<String, String> tempMap = new HashMap<String, String>();
					for (int i = 0; i < commInfoModel.getList().size(); i++) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) commInfoModel
								.getList().get(i);
						tempMap.put(b.getId(), b.getId());
					}
					for (Object obj : allList) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
						BaseCustomsDeclaration tempB = b
								.getBaseCustomsDeclaration();
						if (!customsDeclaration.getId().equals(tempB.getId())) {
							set.add(tempB);
						}
						if (tempMap.containsKey(b.getId())) {
							continue;
						}
						otherCommList.add(obj);
					}
				}

				//
				// 获得前6条
				//
				List<Object> tempList = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i < 6) {
						tempList.add(commInfoModel.getList().get(i));
					}
					if (i == 5) {
						break;
					}
				}
				//
				// 用 otherCommList 为 tempList 加到 6 个
				//
				if (tempList.size() < 6) {
					int count = tempList.size();
					for (int i = 0; i < otherCommList.size(); i++) {
						if (i < 6 - count) {
							tempList.add(otherCommList.get(i));
						} else {
							break;
						}
					}
				}

				String commodityNum = "";
				String grossWeight = "";
				int tempCommodityNum = 0;
				double tempGrossWeight = 0.0;
				Iterator<BaseCustomsDeclaration> iterator = set.iterator();
				while (iterator.hasNext()) {
					BaseCustomsDeclaration b = iterator.next();
					tempCommodityNum += b.getCommodityNum() == null ? 0 : b
							.getCommodityNum();
					tempGrossWeight += b.getGrossWeight() == null ? 0.0 : b
							.getGrossWeight();
				}
				tempCommodityNum += customsDeclaration.getCommodityNum() == null ? 0
						: customsDeclaration.getCommodityNum();
				tempGrossWeight += customsDeclaration.getGrossWeight() == null ? 0.0
						: customsDeclaration.getGrossWeight();
				commodityNum = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempCommodityNum));
				commodityNum += "(" + tempCommodityNum + ")件";
				grossWeight = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempGrossWeight));
				grossWeight += "(" + tempGrossWeight + ")公斤";

				if (allList == null)
					CustomsDeclarationSubReportDataSource
							.setSubReportData(tempList);
				else
					CustomsDeclarationSubReportDataSource
							.setSubReportData(allList);
				CustomsDeclarationSubReportDataSource
						.setContainerData(baseEncAction
								.findAllContainerByConveyance(new Request(
										CommonVars.getCurrUser()),
										customsDeclaration));
				InputStream subReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsAutocarMainfestCommodityReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				// Company company = (Company) customsDeclaration.getCompany();
				Company company = (Company) CommonVars.getCurrUser()
						.getCompany();
				CustomReportDataSource subReportDataList = new CustomReportDataSource(
						tempList);
				Integer pageCount = 1;// 记录载货清单的页数
				// if (commInfoModel.getList().size() > 6)
				// pageCount = 2;
				if (allList != null) {
					Integer count = allList.size();
					if (count <= 6) {
						pageCount = 1;
					} else if (count <= 24 && count > 6) {
						pageCount = 2;
					} else if (count <= 42 && count > 24) {
						pageCount = 3;
					} else if (count <= 60 && count > 42) {
						pageCount = 4;
					} else if (count <= 78 && count > 60) {
						pageCount = 5;
					} else if (count <= 96 && count > 78) {
						pageCount = 6;
					} else {
						pageCount = 7;
					}
				}

				parameters.put("pageCount", pageCount.toString());
				ScmCoc scmcoc = customsDeclaration.getCustomer();
				parameters.put("sendCompany",
						scmcoc == null ? "" : scmcoc.getName());
				parameters.put("sendCompanyAdd",
						scmcoc == null ? "" : scmcoc.getAddre());
				parameters.put("acceptCompany", company.getName());
				parameters.put("acceptCompanyAdd", company.getAddress());
				parameters.put("outTradeCo", company.getOutTradeCo());
				// ll
				// parameters.put("acceptCompany", company.getName());// 外商公司
				// parameters.put("sendCompany", company.getOutTradeCo());//
				// 加工公司
				parameters.put("isOverPrint", Boolean.valueOf(false));
				parameters.put("subReport", subReport);
				parameters.put("list", list);
				parameters.put("subReportDataList", subReportDataList);
				parameters.put("commodityNum", commodityNum);
				parameters.put("grossWeight", grossWeight);
				// 香港车牌
				parameters.put("hongkongCard",
						motorCode == null
								|| motorCode.getHongkongCard() == null ? ""
								: motorCode.getHongkongCard());
				// 运输公司名称
				parameters
						.put("trafficUnit",
								motorCode == null
										|| motorCode.getTrafficUnit() == null ? ""
										: motorCode.getTrafficUnit());
				parameters.put("trafficUnitAdd",
						motorCode == null
								|| motorCode.getTrafficUnitAdd() == null ? ""
								: motorCode.getTrafficUnitAdd());
				parameters.put("trafficUnitTel",
						motorCode == null
								|| motorCode.getTrafficUnitTel() == null ? ""
								: motorCode.getTrafficUnitTel());//
				parameters.put("name",
						motorCode == null || motorCode.getName() == null ? ""
								: motorCode.getName());// 司机姓名
				// BCS 时把手册编号换成合同号
				if (projectType == ProjectType.BCS) {
					parameters.put("emsNo", customsDeclaration.getContract());
				} else {
					parameters.put("emsNo", customsDeclaration.getEmsHeadH2k());
				}
				InputStream reportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomesAutocarMainfestReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);

				//
				// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
				//
				List<Object> t = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i > 5) {
						t.add(commInfoModel.getList().get(i));
					}
				}
				//
				// 用 otherCommList 加到 t 中
				//
				int outCount = 6 - commInfoModel.getList().size();
				for (int i = 0; i < otherCommList.size(); i++) {
					if (i >= outCount) {
						t.add(otherCommList.get(i));
					}
				}

				if (isTD && t.size() > 0) {
					CustomsDeclarationSubReportDataSource.setSubReportData(t);
					InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
					InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
					JasperReport commInfoReport = (JasperReport) JRLoader
							.loadObject(commInfoReportStream);
					Map<String, Object> p = new HashMap<String, Object>();
					p.put("pageCount", pageCount.toString());// 载货清单共有的页数
					p.put("commInfoReport", commInfoReport);
					JasperPrint jasperPrint2 = JasperFillManager.fillReport(
							masterReportStream, p, new CustomReportDataSource(
									list));
					for (int i = 0; i < jasperPrint2.getPages().size(); i++) {
						jasperPrint.addPage((JRPrintPage) jasperPrint2
								.getPages().get(i));
					}
				}

				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 打印内地海关及香港海关陆路出/进境载货清单(香港)+(附表)")
	 * 
	 * @param isTD
	 */
	public void doInPrint8(boolean isTD, int ImpExpFlag) {// 内地海关及香港海关陆路出/进境载货清单（香港）+(附表)
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		int projectType = (Integer) prop.get("projectType");
		List list = new ArrayList();
		list.add(customsDeclaration);
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		if (noTaoda) {
			try {
				String homeCard = customsDeclaration.getBillOfLading().trim();
				MotorCode motorCode = null;
				if (homeCard != null && !homeCard.trim().equals("")) {
					motorCode = materialManageAction.findMotorCodeByCode(
							new Request(CommonVars.getCurrUser()), homeCard);
				}
				//
				// 相同运输工具的所有商品明细
				//
				List<Object> otherCommList = new ArrayList<Object>();
				String conveyance = customsDeclaration.getConveyance();
				Set<BaseCustomsDeclaration> set = new HashSet<BaseCustomsDeclaration>();
				//
				// 如果运输工具不为空的时候才进行检索
				//
				List allList = null;
				if (conveyance != null && !conveyance.trim().equals("")) {
					allList = this.baseEncAction
							.findCustomsDeclarationCommInfoByConveyance(
									new Request(CommonVars.getCurrUser()),
									conveyance, ImpExpFlag);
					Map<String, String> tempMap = new HashMap<String, String>();
					for (int i = 0; i < commInfoModel.getList().size(); i++) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) commInfoModel
								.getList().get(i);
						tempMap.put(b.getId(), b.getId());
					}
					for (Object obj : allList) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
						BaseCustomsDeclaration tempB = b
								.getBaseCustomsDeclaration();
						if (!customsDeclaration.getId().equals(tempB.getId())) {
							set.add(tempB);
						}
						if (tempMap.containsKey(b.getId())) {
							continue;
						}
						otherCommList.add(obj);
					}
				}

				//
				// 获得前6条
				//
				List<Object> tempList = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i < 6) {
						tempList.add(commInfoModel.getList().get(i));
					}
					if (i == 5) {
						break;
					}
				}
				//
				// 用 otherCommList 为 tempList 加到 6 个
				//
				if (tempList.size() < 6) {
					int count = tempList.size();
					for (int i = 0; i < otherCommList.size(); i++) {
						if (i < 6 - count) {
							tempList.add(otherCommList.get(i));
						} else {
							break;
						}
					}
				}

				String commodityNum = "";
				String grossWeight = "";
				int tempCommodityNum = 0;
				double tempGrossWeight = 0.0;
				Iterator<BaseCustomsDeclaration> iterator = set.iterator();
				while (iterator.hasNext()) {
					BaseCustomsDeclaration b = iterator.next();
					tempCommodityNum += b.getCommodityNum() == null ? 0 : b
							.getCommodityNum();
					tempGrossWeight += b.getGrossWeight() == null ? 0.0 : b
							.getGrossWeight();
				}
				tempCommodityNum += customsDeclaration.getCommodityNum() == null ? 0
						: customsDeclaration.getCommodityNum();
				tempGrossWeight += customsDeclaration.getGrossWeight() == null ? 0.0
						: customsDeclaration.getGrossWeight();
				commodityNum = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempCommodityNum));
				commodityNum += "(" + tempCommodityNum + ")件";
				grossWeight = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempGrossWeight));
				grossWeight += "(" + tempGrossWeight + ")公斤";

				if (allList == null)
					CustomsDeclarationSubReportDataSource
							.setSubReportData(tempList);
				else
					CustomsDeclarationSubReportDataSource
							.setSubReportData(allList);

				InputStream subReportStream = DgBaseImportCustomsDeclaration.class

						.getResourceAsStream("report/ExportCustomsAutocarMainfestCommodityReport2.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				// Company company = (Company) customsDeclaration.getCompany();
				Company company = (Company) CommonVars.getCurrUser()
						.getCompany();
				CustomReportDataSource subReportDataList = new CustomReportDataSource(
						tempList);
				Integer pageCount = 1;// 记录载货清单的页数
				// if (commInfoModel.getList().size() > 6)
				// pageCount = 2;
				if (allList != null) {
					Integer count = allList.size();
					if (count <= 6) {
						pageCount = 1;
					} else if (count <= 24 && count > 6) {
						pageCount = 2;
					} else if (count <= 42 && count > 24) {
						pageCount = 3;
					} else if (count <= 60 && count > 42) {
						pageCount = 4;
					} else if (count <= 78 && count > 60) {
						pageCount = 5;
					} else if (count <= 96 && count > 78) {
						pageCount = 6;
					} else {
						pageCount = 7;
					}
				}

				parameters.put("pageCount", pageCount.toString());
				ScmCoc scmcoc = customsDeclaration.getCustomer();
				parameters.put("sendCompany",
						scmcoc == null ? "" : scmcoc.getName());
				parameters.put("sendCompanyAdd",
						scmcoc == null ? "" : scmcoc.getAddre());
				parameters.put("acceptCompany", company.getName());
				parameters.put("acceptCompanyAdd", company.getAddress());
				parameters.put("outTradeCo", company.getOutTradeCo());
				parameters.put("isOverPrint", Boolean.valueOf(true));
				// parameters.put("acceptCompany", company.getName());// 外商公司
				// parameters.put("sendCompany", company.getOutTradeCo());//
				// 加工公司
				parameters.put("subReport", subReport);
				parameters.put("list", list);
				parameters.put("subReportDataList", subReportDataList);
				parameters.put("subReportStream", subReportStream);
				parameters.put("commodityNum", commodityNum);
				parameters.put("grossWeight", grossWeight);
				// 香港车牌
				parameters.put("hongkongCard",
						motorCode == null
								|| motorCode.getHongkongCard() == null ? ""
								: motorCode.getHongkongCard());
				// 运输公司名称
				parameters
						.put("trafficUnit",
								motorCode == null
										|| motorCode.getTrafficUnit() == null ? ""
										: motorCode.getTrafficUnit());
				parameters.put("trafficUnitAdd",
						motorCode == null
								|| motorCode.getTrafficUnitAdd() == null ? ""
								: motorCode.getTrafficUnitAdd());
				parameters.put("trafficUnitTel",
						motorCode == null
								|| motorCode.getTrafficUnitTel() == null ? ""
								: motorCode.getTrafficUnitTel());//
				parameters.put("name",
						motorCode == null || motorCode.getName() == null ? ""
								: motorCode.getName());// 司机姓名
				parameters.put("zuanghuo", customsDeclaration.getMachName());// 装货地点
				parameters.put("xeihuo",
						customsDeclaration.getCustoms() == null ? ""
								: customsDeclaration.getCustoms().getName());// 卸货地点
				// BCS 时把手册编号换成合同号
				if (projectType == ProjectType.BCS) {
					parameters.put("emsNo", customsDeclaration.getContract());
				} else {
					parameters.put("emsNo", customsDeclaration.getEmsHeadH2k());
				}
				InputStream reportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomesAutocarMainfestReport2.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);

				//
				// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
				//
				List<Object> t = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i > 5) {
						t.add(commInfoModel.getList().get(i));
					}
				}
				//
				// 用 otherCommList 加到 t 中
				//
				int outCount = 6 - commInfoModel.getList().size();
				for (int i = 0; i < otherCommList.size(); i++) {
					if (i >= outCount) {
						t.add(otherCommList.get(i));
					}
				}

				if (isTD && t.size() > 0) {
					CustomsDeclarationSubReportDataSource.setSubReportData(t);
					InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
					InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
					JasperReport commInfoReport = (JasperReport) JRLoader
							.loadObject(commInfoReportStream);
					Map<String, Object> p = new HashMap<String, Object>();
					p.put("pageCount", pageCount.toString());// 载货清单共有的页数
					p.put("commInfoReport", commInfoReport);
					JasperPrint jasperPrint2 = JasperFillManager.fillReport(
							masterReportStream, p, new CustomReportDataSource(
									list));
					for (int i = 0; i < jasperPrint2.getPages().size(); i++) {
						jasperPrint.addPage((JRPrintPage) jasperPrint2
								.getPages().get(i));
					}
				}
				//
				// 显示所有报表信息
				//
				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			try {
				String homeCard = customsDeclaration.getBillOfLading();
				MotorCode motorCode = null;
				if (homeCard != null && !homeCard.trim().equals("")) {
					motorCode = materialManageAction.findMotorCodeByCode(
							new Request(CommonVars.getCurrUser()), homeCard);
				}
				//
				// 相同运输工具的所有商品明细
				//
				List<Object> otherCommList = new ArrayList<Object>();
				String conveyance = customsDeclaration.getConveyance();
				Set<BaseCustomsDeclaration> set = new HashSet<BaseCustomsDeclaration>();
				//
				// 如果运输工具不为空的时候才进行检索
				//
				List allList = null;
				if (conveyance != null && !conveyance.trim().equals("")) {
					allList = this.baseEncAction
							.findCustomsDeclarationCommInfoByConveyance(
									new Request(CommonVars.getCurrUser()),
									conveyance, ImpExpFlag);
					Map<String, String> tempMap = new HashMap<String, String>();
					for (int i = 0; i < commInfoModel.getList().size(); i++) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) commInfoModel
								.getList().get(i);
						tempMap.put(b.getId(), b.getId());
					}
					for (Object obj : allList) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
						BaseCustomsDeclaration tempB = b
								.getBaseCustomsDeclaration();
						if (!customsDeclaration.getId().equals(tempB.getId())) {
							set.add(tempB);
						}
						if (tempMap.containsKey(b.getId())) {
							continue;
						}
						otherCommList.add(obj);
					}
				}

				//
				// 获得前6条
				//
				List<Object> tempList = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i < 6) {
						tempList.add(commInfoModel.getList().get(i));
					}
					if (i == 5) {
						break;
					}
				}
				//
				// 用 otherCommList 为 tempList 加到 6 个
				//
				if (tempList.size() < 6) {
					int count = tempList.size();
					for (int i = 0; i < otherCommList.size(); i++) {
						if (i < 6 - count) {
							tempList.add(otherCommList.get(i));
						} else {
							break;
						}
					}
				}

				String commodityNum = "";
				String grossWeight = "";
				int tempCommodityNum = 0;
				double tempGrossWeight = 0.0;
				Iterator<BaseCustomsDeclaration> iterator = set.iterator();
				while (iterator.hasNext()) {
					BaseCustomsDeclaration b = iterator.next();
					tempCommodityNum += b.getCommodityNum() == null ? 0 : b
							.getCommodityNum();
					tempGrossWeight += b.getGrossWeight() == null ? 0.0 : b
							.getGrossWeight();
				}
				tempCommodityNum += customsDeclaration.getCommodityNum() == null ? 0
						: customsDeclaration.getCommodityNum();
				tempGrossWeight += customsDeclaration.getGrossWeight() == null ? 0.0
						: customsDeclaration.getGrossWeight();
				commodityNum = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempCommodityNum));
				commodityNum += "(" + tempCommodityNum + ")件";
				grossWeight = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempGrossWeight));
				grossWeight += "(" + tempGrossWeight + ")公斤";

				if (allList == null)
					CustomsDeclarationSubReportDataSource
							.setSubReportData(tempList);
				else
					CustomsDeclarationSubReportDataSource
							.setSubReportData(allList);
				CustomsDeclarationSubReportDataSource
						.setContainerData(baseEncAction
								.findAllContainerByConveyance(new Request(
										CommonVars.getCurrUser()),
										customsDeclaration));
				InputStream subReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsAutocarMainfestCommodityReport2.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				// Company company = (Company) customsDeclaration.getCompany();
				Company company = (Company) CommonVars.getCurrUser()
						.getCompany();
				CustomReportDataSource subReportDataList = new CustomReportDataSource(
						tempList);
				Integer pageCount = 1;// 记录载货清单的页数
				// if (commInfoModel.getList().size() > 6)
				// pageCount = 2;
				if (allList != null) {
					Integer count = allList.size();
					if (count <= 6) {
						pageCount = 1;
					} else if (count <= 24 && count > 6) {
						pageCount = 2;
					} else if (count <= 42 && count > 24) {
						pageCount = 3;
					} else if (count <= 60 && count > 42) {
						pageCount = 4;
					} else if (count <= 78 && count > 60) {
						pageCount = 5;
					} else if (count <= 96 && count > 78) {
						pageCount = 6;
					} else {
						pageCount = 7;
					}
				}

				parameters.put("pageCount", pageCount.toString());
				ScmCoc scmcoc = customsDeclaration.getCustomer();
				parameters.put("sendCompany",
						scmcoc == null ? "" : scmcoc.getName());
				parameters.put("sendCompanyAdd",
						scmcoc == null ? "" : scmcoc.getAddre());
				parameters.put("acceptCompany", company.getName());
				parameters.put("acceptCompanyAdd", company.getAddress());
				parameters.put("outTradeCo", company.getOutTradeCo());
				// parameters.put("acceptCompany", company.getName());// 外商公司
				// parameters.put("sendCompany", company.getOutTradeCo());//
				// 加工公司
				parameters.put("isOverPrint", Boolean.valueOf(false));
				parameters.put("subReport", subReport);
				parameters.put("list", list);
				parameters.put("subReportDataList", subReportDataList);
				parameters.put("commodityNum", commodityNum);
				parameters.put("grossWeight", grossWeight);
				// 香港车牌
				parameters.put("hongkongCard",
						motorCode == null
								|| motorCode.getHongkongCard() == null ? ""
								: motorCode.getHongkongCard());
				// 运输公司名称
				parameters
						.put("trafficUnit",
								motorCode == null
										|| motorCode.getTrafficUnit() == null ? ""
										: motorCode.getTrafficUnit());
				parameters.put("trafficUnitAdd",
						motorCode == null
								|| motorCode.getTrafficUnitAdd() == null ? ""
								: motorCode.getTrafficUnitAdd());
				parameters.put("trafficUnitTel",
						motorCode == null
								|| motorCode.getTrafficUnitTel() == null ? ""
								: motorCode.getTrafficUnitTel());//
				parameters.put("name",
						motorCode == null || motorCode.getName() == null ? ""
								: motorCode.getName());// 司机姓名
				parameters.put("zuanghuo", customsDeclaration.getMachName());// 装货地点
				parameters.put("xeihuo",
						customsDeclaration.getCustoms() == null ? ""
								: customsDeclaration.getCustoms().getName());// 卸货地点
				// BCS 时把手册编号换成合同号
				if (projectType == ProjectType.BCS) {
					parameters.put("emsNo", customsDeclaration.getContract());
				} else {
					parameters.put("emsNo", customsDeclaration.getEmsHeadH2k());
				}
				InputStream reportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomesAutocarMainfestReport2.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);

				//
				// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
				//
				List<Object> t = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i > 5) {
						t.add(commInfoModel.getList().get(i));
					}
				}
				//
				// 用 otherCommList 加到 t 中
				//
				int outCount = 6 - commInfoModel.getList().size();
				for (int i = 0; i < otherCommList.size(); i++) {
					if (i >= outCount) {
						t.add(otherCommList.get(i));
					}
				}

				if (isTD && t.size() > 0) {
					CustomsDeclarationSubReportDataSource.setSubReportData(t);
					InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
					InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
					JasperReport commInfoReport = (JasperReport) JRLoader
							.loadObject(commInfoReportStream);
					Map<String, Object> p = new HashMap<String, Object>();
					p.put("pageCount", pageCount.toString());// 载货清单共有的页数
					p.put("commInfoReport", commInfoReport);
					JasperPrint jasperPrint2 = JasperFillManager.fillReport(
							masterReportStream, p, new CustomReportDataSource(
									list));
					for (int i = 0; i < jasperPrint2.getPages().size(); i++) {
						jasperPrint.addPage((JRPrintPage) jasperPrint2
								.getPages().get(i));
					}
				}

				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 内地海关及香港海关陆路出/进境载货清单(附表)
	 */
	public void doInPrint9() {
		// CustomReportDataSource ds = (CustomReportDataSource) prop
		// .get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		// String attachedBillName = (String) prop.get("attachedBillName");
		// int projectType = (Integer) prop.get("projectType");
		List list = new ArrayList();
		list.add(customsDeclaration);
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		if (noTaoda) {
			try {
				//
				// 相同运输工具的所有商品明细
				//
				List<Object> otherCommList = new ArrayList<Object>();
				String conveyance = customsDeclaration.getConveyance();
				//
				// 如果运输工具不为空的时候才进行检索
				//
				if (conveyance != null && !conveyance.trim().equals("")) {
					List allList = this.baseEncAction
							.findCustomsDeclarationCommInfoByConveyance(
									new Request(CommonVars.getCurrUser()),
									conveyance, ImpExpFlag.IMPORT);
					Map<String, String> tempMap = new HashMap<String, String>();
					for (int i = 0; i < commInfoModel.getList().size(); i++) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) commInfoModel
								.getList().get(i);
						tempMap.put(b.getId(), b.getId());
					}
					for (Object obj : allList) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
						if (tempMap.containsKey(b.getId())) {
							continue;
						}
						otherCommList.add(obj);
					}
				}

				//
				// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
				//
				List<Object> t = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i > 5) {
						t.add(commInfoModel.getList().get(i));
					}
				}
				//
				// 用 otherCommList 加到 t 中
				//
				int outCount = 6 - commInfoModel.getList().size();
				for (int i = 0; i < otherCommList.size(); i++) {
					if (i >= outCount) {
						t.add(otherCommList.get(i));
					}
				}

				CustomsDeclarationSubReportDataSource.setSubReportData(t);
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> p = new HashMap<String, Object>();
				Integer pageCount = 1;// 载货清单共有的页数
				if (commInfoModel.getList().size() > 6)
					pageCount = 2;
				p.put("pageCount", pageCount.toString());// 载货清单共有的页数
				p.put("commInfoReport", commInfoReport);
				p.put("isOverPrint", Boolean.valueOf(true));
				JasperPrint jasperPrint2 = JasperFillManager
						.fillReport(masterReportStream, p,
								new CustomReportDataSource(list));
				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint2);
				dgReportViewer.setVisible(true);

			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			try {
				//
				// 相同运输工具的所有商品明细
				//
				List<Object> otherCommList = new ArrayList<Object>();
				String conveyance = customsDeclaration.getConveyance();
				//
				// 如果运输工具不为空的时候才进行检索
				//
				if (conveyance != null && !conveyance.trim().equals("")) {
					List allList = this.baseEncAction
							.findCustomsDeclarationCommInfoByConveyance(
									new Request(CommonVars.getCurrUser()),
									conveyance, ImpExpFlag.IMPORT);
					Map<String, String> tempMap = new HashMap<String, String>();
					for (int i = 0; i < commInfoModel.getList().size(); i++) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) commInfoModel
								.getList().get(i);
						tempMap.put(b.getId(), b.getId());
					}
					for (Object obj : allList) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
						if (tempMap.containsKey(b.getId())) {
							continue;
						}
						otherCommList.add(obj);
					}
				}

				//
				// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
				//
				List<Object> t = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i > 5) {
						t.add(commInfoModel.getList().get(i));
					}
				}
				//
				// 用 otherCommList 加到 t 中
				//
				int outCount = 6 - commInfoModel.getList().size();
				for (int i = 0; i < otherCommList.size(); i++) {
					if (i >= outCount) {
						t.add(otherCommList.get(i));
					}
				}

				CustomsDeclarationSubReportDataSource.setSubReportData(t);
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> p = new HashMap<String, Object>();
				Integer pageCount = 1;// 载货清单共有的页数
				if (commInfoModel.getList().size() > 6)
					pageCount = 2;
				p.put("isOverPrint", Boolean.valueOf(false));
				p.put("pageCount", pageCount.toString());// 载货清单共有的页数
				p.put("commInfoReport", commInfoReport);
				JasperPrint jasperPrint2 = JasperFillManager
						.fillReport(masterReportStream, p,
								new CustomReportDataSource(list));
				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint2);
				dgReportViewer.setVisible(true);

			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 打印装箱单(2)
	 */
	public void doInPrint10() {// 装箱单(2)
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		CustomsDeclarationSubReportDataSource.setSubReportData(commInfoModel
				.getList());
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		if (noTaoda) {
			try {
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportCommodityEncasementReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportCommodityEncasementSubReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				String billOfLading = customsDeclaration.getBillOfLading();// 提运单号
				String name;// 司机姓名
				String trafficUnit;// 运输单位
				if (billOfLading == null) {
					name = "";
					trafficUnit = "";

				} else {
					MotorCode motor = materialManageAction
							.findMotorCodeByCode(
									new Request(CommonVars.getCurrUser()),
									billOfLading);
					if (motor == null) {
						name = "";
						trafficUnit = "";
					} else {
						name = motor.getName() == null ? "" : motor.getName();// 司机姓名
						trafficUnit = motor.getTrafficUnit() == null ? ""
								: motor.getTrafficUnit();// 运输单位
					}
				}
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("overprint", Boolean.valueOf(false));
				parameters.put("isExport", false);
				parameters.put("name", name);
				parameters.put("trafficUnit", trafficUnit);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoModel.getList());
			try {
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportCommodityEncasementReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportCommodityEncasementSubReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				String billOfLading = customsDeclaration.getBillOfLading();// 提运单号
				String name;// 司机姓名
				String trafficUnit;// 运输单位
				if (billOfLading == null) {
					name = "";
					trafficUnit = "";

				} else {
					MotorCode motor = materialManageAction
							.findMotorCodeByCode(
									new Request(CommonVars.getCurrUser()),
									billOfLading);
					if (motor == null) {
						name = "";
						trafficUnit = "";
					} else {
						name = motor.getName() == null ? "" : motor.getName();// 司机姓名
						trafficUnit = motor.getTrafficUnit() == null ? ""
								: motor.getTrafficUnit();// 运输单位
					}
				}
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("overprint", Boolean.valueOf(true));
				parameters.put("isExport", false);
				parameters.put("name", name);
				parameters.put("trafficUnit", trafficUnit);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 装箱单(包装种类在明细)
	 */
	public void doInPrintZhangXiangDan() {// 装箱单(包装种类在明细)
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		CustomsDeclarationSubReportDataSource.setSubReportData(commInfoModel
				.getList());
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		if (noTaoda) {
			try {
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportCommodityEncaseWrapReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportCommodityEncaseWrapSubReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				String billOfLading = customsDeclaration.getBillOfLading();// 提运单号
				String name;// 司机姓名
				String trafficUnit;// 运输单位
				if (billOfLading == null) {
					name = "";
					trafficUnit = "";

				} else {
					MotorCode motor = materialManageAction
							.findMotorCodeByCode(
									new Request(CommonVars.getCurrUser()),
									billOfLading);
					if (motor == null) {
						name = "";
						trafficUnit = "";
					} else {
						name = motor.getName() == null ? "" : motor.getName();// 司机姓名
						trafficUnit = motor.getTrafficUnit() == null ? ""
								: motor.getTrafficUnit();// 运输单位
					}
				}
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("overprint", Boolean.valueOf(false));
				parameters.put("isExport", false);
				parameters.put("name", name);
				parameters.put("trafficUnit", trafficUnit);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoModel.getList());
			try {
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportCommodityEncaseWrapReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportCommodityEncaseWrapSubReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				String billOfLading = customsDeclaration.getBillOfLading();// 提运单号
				String name;// 司机姓名
				String trafficUnit;// 运输单位
				if (billOfLading == null) {
					name = "";
					trafficUnit = "";

				} else {
					MotorCode motor = materialManageAction
							.findMotorCodeByCode(
									new Request(CommonVars.getCurrUser()),
									billOfLading);
					if (motor == null) {
						name = "";
						trafficUnit = "";
					} else {
						name = motor.getName() == null ? "" : motor.getName();// 司机姓名
						trafficUnit = motor.getTrafficUnit() == null ? ""
								: motor.getTrafficUnit();// 运输单位
					}
				}
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("overprint", Boolean.valueOf(true));
				parameters.put("isExport", false);
				parameters.put("name", name);
				parameters.put("trafficUnit", trafficUnit);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 装箱单(包装种类在明细)商品编码及箱号
	 */
	public void doInPrintZhangXiangDanComplexOrBoxNo(boolean isComplex) {// 装箱单(包装种类在明细)
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		// ///柯鹏程
		// Map<String,Integer> mapWrap = new HashMap<String,Integer>();
		// for (int i = 0; i < commInfoModel.getList().size(); i++) {
		// if(commInfoModel.getList().get(i) instanceof
		// BcsCustomsDeclarationCommInfo){
		// BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo =
		// (BcsCustomsDeclarationCommInfo )commInfoModel.getList().get(i);
		// int type =
		// bcsCustomsDeclarationCommInfo.getBaseCustomsDeclaration().getImpExpType();
		// List<ImpExpCommodityInfo> impExpCommodityInfos = encAction
		// .findTempImpExpCommodityInfoBycustomsInfo(new Request(
		// CommonVars.getCurrUser()),
		// bcsCustomsDeclarationCommInfo, type);
		// for (int j = 0; j < impExpCommodityInfos.size(); j++) {
		// ImpExpCommodityInfo
		// impExpCommodityInfo=(ImpExpCommodityInfo)impExpCommodityInfos.get(j);
		// Wrap wrap = impExpCommodityInfo.getWrapType();
		// if(wrap!=null&&impExpCommodityInfo.getPiece()!=null){
		// if(mapWrap.get(wrap.getName())==null){
		// mapWrap.put(wrap.getName(), impExpCommodityInfo.getPiece());
		// }else{
		// mapWrap.put(wrap.getName(),
		// mapWrap.get(wrap.getName())+impExpCommodityInfo.getPiece());
		// }
		// }
		// }
		// }
		// }
		// String wraps = "";
		// Set<String> keys = mapWrap.keySet();
		// for(Iterator it = keys.iterator();it.hasNext();){
		// String key = (String)it.next();
		// // System.out.println(key+"==="+mapWrap.get(key));
		// wraps += key+":"+mapWrap.get(key)+"; ";
		// }
		// ////
		CustomsDeclarationSubReportDataSource.setSubReportData(commInfoModel
				.getList());
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		if (noTaoda) {
			try {
				InputStream masterReportStream = null;
				InputStream commInfoReportStream = null;
				if (isComplex) {
					masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ImportCommodityEncaseWrapComplexReport.jasper");
					commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ImportCommodityEncaseWrapComplexSubReport.jasper");
				} else {
					masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ImportCommodityEncaseWrapBoxNoReport.jasper");
					commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ImportCommodityEncaseWrapBoxNoSubReport.jasper");
				}
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				String billOfLading = customsDeclaration.getBillOfLading();// 提运单号
				String name;// 司机姓名
				String trafficUnit;// 运输单位
				if (billOfLading == null) {
					name = "";
					trafficUnit = "";

				} else {
					MotorCode motor = materialManageAction
							.findMotorCodeByCode(
									new Request(CommonVars.getCurrUser()),
									billOfLading);
					if (motor == null) {
						name = "";
						trafficUnit = "";
					} else {
						name = motor.getName() == null ? "" : motor.getName();// 司机姓名
						trafficUnit = motor.getTrafficUnit() == null ? ""
								: motor.getTrafficUnit();// 运输单位
					}
				}
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("overprint", Boolean.valueOf(false));
				parameters.put("isExport", false);
				parameters.put("name", name);
				parameters.put("trafficUnit", trafficUnit);
				parameters.put("complexOrBoxNo", Boolean.valueOf(isComplex));// 是否打开箱号还是商品编码
				// parameters.put("wraps", wraps);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoModel.getList());
			try {
				InputStream masterReportStream = null;
				InputStream commInfoReportStream = null;
				if (isComplex) {
					masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ImportCommodityEncaseWrapComplexReport.jasper");
					commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ImportCommodityEncaseWrapComplexSubReport.jasper");
				} else {
					masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ImportCommodityEncaseWrapBoxNoReport.jasper");
					commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ImportCommodityEncaseWrapBoxNoSubReport.jasper");
				}
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				String billOfLading = customsDeclaration.getBillOfLading();// 提运单号
				String name;// 司机姓名
				String trafficUnit;// 运输单位
				if (billOfLading == null) {
					name = "";
					trafficUnit = "";

				} else {
					MotorCode motor = materialManageAction
							.findMotorCodeByCode(
									new Request(CommonVars.getCurrUser()),
									billOfLading);
					if (motor == null) {
						name = "";
						trafficUnit = "";
					} else {
						name = motor.getName() == null ? "" : motor.getName();// 司机姓名
						trafficUnit = motor.getTrafficUnit() == null ? ""
								: motor.getTrafficUnit();// 运输单位
					}
				}
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("overprint", Boolean.valueOf(true));
				parameters.put("isExport", false);
				parameters.put("name", name);
				parameters.put("trafficUnit", trafficUnit);
				parameters.put("complexOrBoxNo", Boolean.valueOf(isComplex));// 是否打开箱号还是商品编码
				// parameters.put("wraps", wraps);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void doOutPrint14(Boolean isSanzi) {

		// CustomReportDataSource ds = (CustomReportDataSource) prop
		// .get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		String projectType = prop.get("projectType").toString();
		String cusName = "";
		Double total = 0d;
		Double process = 0d;
		JasperPrint jasperPrintAll = null;
		List<BaseCustomsDeclarationCommInfo> list = new ArrayList();
		Map<String, BaseCustomsDeclarationCommInfo> map = new HashMap<String, BaseCustomsDeclarationCommInfo>();
		List<BaseCustomsDeclarationCommInfo> listInfo = commInfoModel.getList();
		for (BaseCustomsDeclarationCommInfo info : listInfo) {
			String str = info.getCommName() + info.getUnit().getCode();
			total = total
					+ CommonUtils.getDoubleExceptNull(info.getCommTotalPrice());
			// System.out.println(info.getBaseCustomsDeclaration().getEmsHeadH2k());
			// System.out.println(info.getBaseCustomsDeclaration().getContract());
			// System.out.println(info.getBaseCustomsDeclaration().getEmsHeadH2k());
			// System.out.println(info.getCommSerialNo());
			if (projectType.equals(String.valueOf(ProjectType.BCS))) {
				ContractExg exg = contractCavAction
						.getContractExgByContract((info
								.getBaseCustomsDeclaration().getEmsHeadH2k()),
								info.getCommSerialNo(), info
										.getBaseCustomsDeclaration()
										.getAuthorizeFile());
				if (exg != null) {
					System.out.println(exg.getProcessUnitPrice());
				}
				process = process
						+ CommonUtils.getDoubleExceptNull(info.getCommAmount())
						* CommonUtils.getDoubleExceptNull(((exg == null) ? 0
								: CommonUtils.getDoubleExceptNull(exg
										.getProcessUnitPrice())));
			} else if (projectType.equals(String.valueOf(ProjectType.DZSC))) {
				DzscEmsExgBill deb = contractCavAction
						.getDzscEmsExgBillExgByContract((info
								.getBaseCustomsDeclaration().getEmsHeadH2k()),
								info.getCommSerialNo(), info
										.getBaseCustomsDeclaration()
										.getAuthorizeFile());
				process = process
						+ CommonUtils.getDoubleExceptNull(info.getCommAmount())
						* CommonUtils.getDoubleExceptNull(((deb == null) ? 0
								: CommonUtils.getDoubleExceptNull(deb
										.getMachinPrice())));
			} else if (projectType.equals(String.valueOf(ProjectType.BCUS))) {

			}
			if (map.containsKey(str)) {
				BaseCustomsDeclarationCommInfo in = (BaseCustomsDeclarationCommInfo) map
						.get(str);
				in.setCommAmount(CommonUtils.getDoubleExceptNull(in
						.getCommAmount())
						+ CommonUtils.getDoubleExceptNull(info.getCommAmount()));
				in.setCommTotalPrice(CommonUtils.getDoubleExceptNull(in
						.getCommTotalPrice())
						+ CommonUtils.getDoubleExceptNull(info
								.getCommTotalPrice()));
				map.put(str, in);
			} else {
				map.put(str, info);
			}
		}
		Set keys = map.keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			list.add(map.get(it.next()));
		}
		for (BaseCustomsDeclarationCommInfo fo : list) {
			System.out.print(fo.getCommName() + "/t");
			System.out.print(fo.getCommAmount() + "/t");
			System.out.println(fo.getCommTotalPrice());
		}

		if (list.size() > 6) {
			map.clear();
			for (BaseCustomsDeclarationCommInfo info : list) {
				String str = info.getUnit().getCode();
				BaseCustomsDeclarationCommInfo newInfo = new BaseCustomsDeclarationCommInfo();
				if (str != null || "".equals(str)) {
					if (map.containsKey(str)) {
						BaseCustomsDeclarationCommInfo in = (BaseCustomsDeclarationCommInfo) map
								.get(str);
						in.setCommAmount(CommonUtils.getDoubleExceptNull(in
								.getCommAmount())
								+ CommonUtils.getDoubleExceptNull(info
										.getCommTotalPrice()));
						in.setCommTotalPrice(CommonUtils.getDoubleExceptNull(in
								.getCommTotalPrice())
								+ CommonUtils.getDoubleExceptNull(info
										.getCommTotalPrice()));
						map.put(str, in);
					} else {
						newInfo.setUnit(info.getUnit());
						newInfo.setCommAmount(info.getCommAmount());
						newInfo.setCommTotalPrice(info.getCommTotalPrice());
						map.put(str, newInfo);
					}
				}
			}
			list.clear();
			// Set key = map.keySet();
			Iterator<String> i = keys.iterator();
			while (i.hasNext()) {
				list.add(map.get(i.next()));
			}
			cusName = JOptionPane.showInputDialog("请输入货物名称：");
		}
		// int size = commInfoModel.getList().size();
		// int totalNum = size/5+(size%5==0?0:1);
		// System.out.println("size:"+size);
		// System.out.println("共："+totalNum +"页");

		// for (int i = 1; i <= totalNum; i++) {
		// Double total = 0d;
		try {
			// List list = new ArrayList();
			// int row = commInfoModel.getList().size() - (i - 1) * 5;
			// if (row >= 5) {
			// for (int j = 0; j < 5; j++) {
			// list.add(commInfoModel.getList().get((i - 1) * 5 + j));
			// total=total
			// +CommonUtils.getDoubleExceptNull(((BcsCustomsDeclarationCommInfo)(commInfoModel.getList().get((i
			// - 1) * 5 + j))).getCommTotalPrice());
			// }
			// } else {
			// for (int j = 0; j < row; j++) {
			// list.add(commInfoModel.getList().get((i - 1) * 5 + j));
			// total=total
			// +CommonUtils.getDoubleExceptNull(((BcsCustomsDeclarationCommInfo)(commInfoModel.getList().get((i
			// - 1) * 5 + j))).getCommTotalPrice());
			// }
			// }
			List list1 = new ArrayList();
			list1.add(customsDeclaration);
			CustomReportDataSource ds = new CustomReportDataSource(list1);
			CustomReportDataSource subDs = new CustomReportDataSource(list);
			InputStream master = DgBaseImportCustomsDeclaration.class
					.getResourceAsStream("report/ExportShouHuiHeXiaoDian.jasper");
			InputStream masterSub = DgBaseImportCustomsDeclaration.class
					.getResourceAsStream("report/ExportShouHuiHeXiaoDianSubInfo.jasper");
			Map<String, Object> parameters = new HashMap<String, Object>();
			JasperReport commInfoReport = (JasperReport) JRLoader
					.loadObject(masterSub);
			parameters.put("commInfoReport", commInfoReport);
			parameters.put("subDs", subDs);
			parameters.put(
					"totalMoney",
					(total == null ? "" : java.lang.String
							.format("%.2f", total)));
			parameters.put("emsNo", customsDeclaration);
			parameters.put("cusName", cusName);
			parameters.put("type",
					customsDeclaration.getBalanceMode() == null ? ""
							: customsDeclaration.getBalanceMode().getName());
			parameters.put("totalPrice", String.format("%.2f", total));
			parameters.put("curentName",
					customsDeclaration.getCurrency() == null ? ""
							: customsDeclaration.getCurrency().getCurrSymb());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			parameters.put(
					"date",
					customsDeclaration.getImpExpDate() == null ? "" : format
							.format(customsDeclaration.getDeclarationDate()));
			parameters.put("id", customsDeclaration.getId());
			parameters.put("cuNo",
					customsDeclaration.getCustomsDeclarationCode());
			parameters.put("isSanzi", isSanzi.toString());
			Boolean isLaiLiao = !isSanzi;
			parameters.put("isLaiLiao", isLaiLiao.toString());
			CompanyOther other = CommonVars.getOther();
			if (other != null) {
				System.out.println("other is null");
				parameters.put("inDate", other.getMonth() + "天");
			} else
				parameters.put("inDate", "30天");
			parameters.put("contractNo", customsDeclaration.getEmsHeadH2k());
			parameters.put("totalLiaoZhi",
					String.format("%.2f", (total - process)));
			parameters.put("totalProcess", String.format("%.2f", process));
			// String note = "";
			// parameters.put("note", note);
			// if(i==1){
			jasperPrintAll = JasperFillManager.fillReport(master, parameters,
					ds);
			// }else{
			// JasperPrint jasperPrint = JasperFillManager.fillReport(master,
			// parameters, ds);
			// int cexg = jasperPrint.getPages().size();
			// for (int j = 0; j < cexg; j++) {
			// jasperPrintAll
			// .addPage((JRPrintPage) jasperPrint
			// .getPages().get(j));
			// }
			// }
			// jasperPrintAll.addPage((JRPrintPage)jasperPrint.getPages().get(0));
			DgReportViewer viewer = new DgReportViewer(jasperPrintAll);
			viewer.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 打印报关单
	 */
	public void doOutPrint0() {

		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");

		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");

		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");

		List listCommInfo = baseEncAction.findCustomsDeclarationCommInfo(
				new Request(CommonVars.getCurrUser()), customsDeclaration);

		// CustomsDeclarationSubReportDataSource.setSubReportData(commInfoModel
		// .getList());

		CustomsDeclarationSubReportDataSource.setSubReportData(listCommInfo);

		String attachedBillName = (String) prop.get("attachedBillName");

		int projectType = (Integer) prop.get("projectType");

		boolean noTaoda = !(Boolean) prop.get("isTaoda");

		DzscParameterSet dzscParameterSet = dzscMessageAction
				.findDzscMessageDirSet1(new Request(CommonVars.getCurrUser(),
						true));

		// 非套打
		if (noTaoda) {

			try {

				List contianList = baseEncAction
						.findContainerByCustomsDeclaration(new Request(
								CommonVars.getCurrUser()), customsDeclaration);

				if (contianList.size() != 0) {

					if (JOptionPane.showConfirmDialog(FmMain.getInstance(),
							"是否打印集装箱及托架?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

						CustomsDeclarationSubReportDataSource
								.setContainerData(contianList);

					} else {

						CustomsDeclarationSubReportDataSource
								.setContainerData(new ArrayList());

					}

				} else {

					CustomsDeclarationSubReportDataSource
							.setContainerData(new ArrayList());

				}
				// CustomsDeclarationSubReportDataSource
				// .setContainerData(baseEncAction
				// .findContainerByCustomsDeclaration(new Request(
				// CommonVars.getCurrUser()),
				// customsDeclaration));
				CustomsDeclarationSubReportDataSource
						.setSubReportData(commInfoModel.getList());

				InputStream masterReportStream = null;

				CompanyOther other = CommonVars.getOther();

				// 是否打印附加信息
				if (other.getIsTransitadd() == null || !other.getIsTransitadd()) {

					if (customsDeclaration.getEmsHeadH2k() != null
							&& !"".equals(customsDeclaration.getEmsHeadH2k())
							&& customsDeclaration.getEmsHeadH2k()
									.substring(0, 1).equals("H")) {

						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ExportCustomsHEmsDeclarationReport.jasper");

					} else {

						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ExportCustomsDeclarationReport.jasper");

					}

				} else {

					// 判断字符是否为不空
					boolean emsHeadH2kIsNotBlank = StringUtils
							.isNotBlank(customsDeclaration.getEmsHeadH2k());

					if (emsHeadH2kIsNotBlank
							&& customsDeclaration.getEmsHeadH2k()
									.substring(0, 1).equals("H")) {

						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ExportCustomsHEmsDeclarationReportNoadd.jasper");

					} else {

						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ExportCustomsDeclarationReportNoadd.jasper");

					}
				}

				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsDeclarationCommInfoReport.jasper");

				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);

				// 集装箱
				InputStream containerReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/CustomsDeclarationContainerReport.jasper");

				JasperReport containerReport = (JasperReport) JRLoader
						.loadObject(containerReportStream);

				Map<String, Object> parameters = new HashMap<String, Object>();

				parameters.put("contia", getContia(customsDeclaration));

				parameters.put("projectType", String.valueOf(projectType));// 只有是BCUS有需要才打印版本栏位

				parameters.put("commInfoReport", commInfoReport);

				parameters.put("containerReport", containerReport);

				parameters.put("overprint", new Boolean(false));

				Company company = (Company) customsDeclaration.getCompany();

				parameters.put("linkMan", company.getLinkman());

				parameters.put("linkTel", company.getTel());

				parameters.put("tradeFlat", company.getCounFlag());

				parameters.put("coFlat", company.getOwnercounFlag());

				parameters.put("isPrintNo", dzscParameterSet.getIsPrintNo()
						.toString());// 是否打印航次

				parameters.put("isPrintToolCode", dzscParameterSet
						.getIsPrintToolCode().toString());// 是否打印工具编码
				// // 设置毛净重
				// DecimalFormat f = new DecimalFormat();
				// f.setMaximumFractionDigits(3);
				// f.setGroupingSize(0);
				// if (customsDeclaration.getImpExpType() ==
				// ImpExpType.TRANSFER_FACTORY_EXPORT
				// || customsDeclaration.getImpExpType() ==
				// ImpExpType.TRANSFER_FACTORY_IMPORT) {// 转厂则保留三位小数
				// parameters.put("grossWeight", customsDeclaration
				// .getGrossWeight() != null ? f
				// .format(customsDeclaration.getGrossWeight())
				// : "0.0");
				// parameters.put("netWeight", customsDeclaration
				// .getNetWeight() != null ? f
				// .format(customsDeclaration.getNetWeight()) : "0.0");
				// } else {
				// parameters.put("grossWeight", customsDeclaration
				// .getGrossWeight() != null ? String.format("%.0f",
				// customsDeclaration.getGrossWeight()) : "0");
				// parameters.put("netWeight", customsDeclaration
				// .getNetWeight() != null ? String.format("%.0f",
				// customsDeclaration.getNetWeight()) : "0");
				// }

				parameters
						.put("grossWeight",
								customsDeclaration.getGrossWeight() != null ? cutString(customsDeclaration
										.getGrossWeight().toString()) : "0");

				parameters
						.put("netWeight",
								customsDeclaration.getNetWeight() != null ? cutString(customsDeclaration
										.getNetWeight().toString()) : "0");

				String isPrintCustomWithVersion = manualDecleareAction
						.getBpara(new Request(CommonVars.getCurrUser()),
								BcusParameter.PrintCustomWithVersion);

				if (isPrintCustomWithVersion == null) {

					isPrintCustomWithVersion = "0";

				}

				parameters.put("isPrintCustomWithVersion",
						isPrintCustomWithVersion);// 用来控制是否显示版本栏位

				parameters.put("attachedBillName", attachedBillName);

				String customsEnvelopBillNo = "";

				if ((customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_IMPORT || customsDeclaration
						.getImpExpType() == ImpExpType.TRANSFER_FACTORY_EXPORT)
						&& customsDeclaration.getAttachedBill() != null
						&& customsDeclaration.getAttachedBill().contains("K")) {

					customsEnvelopBillNo = " [结转申请单号]:"
							+ customsDeclaration.getCustomsEnvelopBillNo();

				}

				parameters.put("customsEnvelopBillNo", customsEnvelopBillNo);

				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);

				DgReportViewer viewer = new DgReportViewer(jasperPrint);

				viewer.setVisible(true);

			} catch (JRException e1) {

				e1.printStackTrace();

			}

			// 套打
		} else {

			try {

				CustomsDeclarationSubReportDataSource
						.setSubReportData(commInfoModel.getList());

				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsDeclarationReport.jasper");

				CompanyOther other = CommonVars.getOther();

				// 是否打印附加信息
				if (other.getIsTransitadd() == null || !other.getIsTransitadd()) {

					if (customsDeclaration.getEmsHeadH2k() != null
							&& !"".equals(customsDeclaration.getEmsHeadH2k())
							&& customsDeclaration.getEmsHeadH2k()
									.substring(0, 1).equals("H")) {

						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ExportCustomsHEmsDeclarationReport.jasper");

					} else {

						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ExportCustomsDeclarationReport.jasper");

					}

				} else {

					// 判断字符是否为不空
					boolean emsHeadH2kIsNotBlank = StringUtils
							.isBlank(customsDeclaration.getEmsHeadH2k());

					if (emsHeadH2kIsNotBlank
							&& customsDeclaration.getEmsHeadH2k()
									.substring(0, 1).equals("H")) {

						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ExportCustomsHEmsDeclarationReportNoadd.jasper");

					} else {

						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ExportCustomsDeclarationReportNoadd.jasper");

					}
				}

				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsDeclarationCommInfoReport.jasper");

				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);

				Map<String, Object> parameters = new HashMap<String, Object>();

				parameters.put("contia", getContia(customsDeclaration));

				parameters.put("commInfoReport", commInfoReport);

				parameters.put("projectType", String.valueOf(projectType));// 只有是BCUS有需要才打印版本栏位

				parameters.put("overprint", new Boolean(true));

				Company company = (Company) customsDeclaration.getCompany();

				parameters.put("linkMan", company.getLinkman());

				parameters.put("linkTel", company.getTel());

				parameters.put("tradeFlat", company.getCounFlag());

				parameters.put("coFlat", company.getOwnercounFlag());

				// 是否打印航次
				parameters.put("isPrintNo", dzscParameterSet.getIsPrintNo()
						.toString());

				// 是否打印航次
				parameters.put("isPrintToolCode", dzscParameterSet
						.getIsPrintToolCode().toString());

				// 设置毛净重
				DecimalFormat f = new DecimalFormat();

				f.setMaximumFractionDigits(3);

				f.setGroupingSize(0);
				// // 设置毛净重
				// if (customsDeclaration.getImpExpType() ==
				// ImpExpType.TRANSFER_FACTORY_EXPORT
				// || customsDeclaration.getImpExpType() ==
				// ImpExpType.TRANSFER_FACTORY_IMPORT) {// 转厂则保留三位小数
				// parameters.put("grossWeight", customsDeclaration
				// .getGrossWeight() != null ? f
				// .format(customsDeclaration.getGrossWeight())
				// : "0.0");
				// parameters.put("netWeight", customsDeclaration
				// .getNetWeight() != null ? f
				// .format(customsDeclaration.getNetWeight()) : "0.0");
				// } else {
				// parameters.put("grossWeight", customsDeclaration
				// .getGrossWeight() != null ? String.format("%.0f",
				// customsDeclaration.getGrossWeight()) : "0");
				// parameters.put("netWeight", customsDeclaration
				// .getNetWeight() != null ? String.format("%.0f",
				// customsDeclaration.getNetWeight()) : "0");
				// }
				parameters
						.put("grossWeight",
								customsDeclaration.getGrossWeight() != null ? cutString(customsDeclaration
										.getGrossWeight().toString()) : "0");
				parameters
						.put("netWeight",
								customsDeclaration.getNetWeight() != null ? cutString(customsDeclaration
										.getNetWeight().toString()) : "0");

				// String isPrintCustomWithVersion = manualDecleareAction
				// .getBpara(new Request(CommonVars.getCurrUser()),
				// BcusParameter.PrintCustomWithVersion);
				// if (isPrintCustomWithVersion == null)
				// isPrintCustomWithVersion = "0";

				String isPrintCustomWithVersion = "0";

				// 用来控制是否显示版本栏位
				parameters.put("isPrintCustomWithVersion",
						isPrintCustomWithVersion);

				parameters.put("attachedBillName", attachedBillName);

				String customsEnvelopBillNo = "";

				if ((customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_IMPORT || customsDeclaration
						.getImpExpType() == ImpExpType.TRANSFER_FACTORY_EXPORT)
						&& customsDeclaration.getAttachedBill() != null
						&& customsDeclaration.getAttachedBill().contains("K")) {

					customsEnvelopBillNo = " [结转申请单号]:"
							+ customsDeclaration.getCustomsEnvelopBillNo();

				}

				parameters.put("customsEnvelopBillNo", customsEnvelopBillNo);

				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);

				DgReportViewer viewer = new DgReportViewer(jasperPrint);

				viewer.setVisible(true);

			} catch (JRException e1) {

				e1.printStackTrace();

			}
		}
	}

	/**
	 * 打印报关单附页表
	 */
	public void doOutPrint1() {// 装箱单(2)
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		// BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration)
		// prop
		// .get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		CustomsDeclarationSubReportDataSource.setSubReportData(commInfoModel
				.getList());
		// String attachedBillName = (String) prop.get("attachedBillName");
		// int projectType = (Integer) prop.get("projectType");
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		if (noTaoda) {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoModel.getList());
			try {
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImgShowListReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImgShowListSubReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("isOverPrint", new Boolean(true));
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoModel.getList());
			try {
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImgShowListReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImgShowListSubReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("isOverPrint", new Boolean(false));
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 打印装箱单1表
	 */
	public void doOutPrint2() {// 装箱单(1)
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		CustomsDeclarationSubReportDataSource.setSubReportData(commInfoModel
				.getList());
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		CustomsDeclarationSubReportDataSource.setSubReportData(commInfoModel
				.getList());
		CommonVars.setSumMoney(Double.valueOf(0));
		CommonVars.setBeforeSumMoney(Double.valueOf(0));
		Company comp = (Company) customsDeclaration.getCompany();
		try {
			InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
					.getResourceAsStream("report/WExportCommodityEncasementReportLwjg.jasper");
			InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
					.getResourceAsStream("report/WExportCommodityEncasementSubReportLwjg.jasper");
			String billNo4 = customsDeclaration.getBillListId(); // 报关清单号码
			String impexpno4 = baseEncAction.getImpExpNoByBillNo(new Request(
					CommonVars.getCurrUser()), billNo4);
			JasperReport commInfoReport = (JasperReport) JRLoader
					.loadObject(commInfoReportStream);
			String billOfLading = customsDeclaration.getBillOfLading();// 提运单号
			String name;// 司机姓名
			String trafficUnit;// 运输单位
			if (billOfLading == null) {
				name = "";
				trafficUnit = "";
			} else {
				MotorCode motor = materialManageAction.findMotorCodeByCode(
						new Request(CommonVars.getCurrUser()), billOfLading);
				if (motor == null) {
					name = "";
					trafficUnit = "";
				} else {
					name = motor.getName() == null ? "" : motor.getName();// 司机姓名
					trafficUnit = motor.getTrafficUnit() == null ? "" : motor
							.getTrafficUnit();// 运输单位
				}
			}
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("commInfoReport", commInfoReport);
			parameters.put("overprint", !noTaoda);
			parameters.put("isExport", true);
			parameters.put("impexpno", impexpno4);
			parameters.put("outTradeCo", comp.getOutTradeCo());// 外商公司
			parameters.put("name", name);
			parameters.put("trafficUnit", trafficUnit);
			parameters.put("curr",
					customsDeclaration.getCurrency() == null ? ""
							: customsDeclaration.getCurrency().getCurrSymb());// 币制
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 打印出口加工发票
	 */
	public void doOutPrint3() {
		// CustomReportDataSource ds = (CustomReportDataSource) prop
		// .get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		CustomsDeclarationSubReportDataSource.setSubReportData(commInfoModel
				.getList());
		// String attachedBillName = (String) prop.get("attachedBillName");
		int projectType = (Integer) prop.get("projectType");
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		// 计算加工费
		Double process = 0d;// 加工费
		List<BaseCustomsDeclarationCommInfo> listInfo = commInfoModel.getList();
		for (BaseCustomsDeclarationCommInfo info : listInfo) {
			// String str = info.getCommName() + info.getUnit().getCode();
			DzscEmsExgBill exg = contractCavAction
					.getDzscEmsExgBillExgByContract((info
							.getBaseCustomsDeclaration().getEmsHeadH2k()), info
							.getCommSerialNo(), info
							.getBaseCustomsDeclaration().getAuthorizeFile());
			if (exg != null) {
				System.out.println(exg.getMachinPrice());
			}
			process = process
					+ CommonUtils.getDoubleExceptNull(info.getCommAmount())
					* CommonUtils.getDoubleExceptNull(((exg == null) ? 0
							: CommonUtils.getDoubleExceptNull(exg
									.getMachinPrice())));
		}
		System.out.println("加工费：" + process);
		//
		if (noTaoda) {
			if (customsDeclaration != null) {
				System.out.println("SSSSS");
				DgExportInvoiceItemReportSub dgExportInvoiceItemReportSub = new DgExportInvoiceItemReportSub();
				dgExportInvoiceItemReportSub
						.setCustomsDeclaration(customsDeclaration);
				dgExportInvoiceItemReportSub
						.setDeclarationCommInfoList(commInfoModel.getList());
				dgExportInvoiceItemReportSub.setIsOverPrint(true);
				dgExportInvoiceItemReportSub.setProjectType(projectType);
				dgExportInvoiceItemReportSub.setProcess(process);
				dgExportInvoiceItemReportSub.setVisible(true);

			} else {
				JOptionPane.showMessageDialog(FmMain.getInstance(),
						"当前出口报关单记录不存在,请先保存!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			if (customsDeclaration != null) {
				DgExportInvoiceItemReportSub dgExportInvoiceItemReportSub = new DgExportInvoiceItemReportSub();
				dgExportInvoiceItemReportSub
						.setCustomsDeclaration(customsDeclaration);
				dgExportInvoiceItemReportSub
						.setDeclarationCommInfoList(commInfoModel.getList());
				dgExportInvoiceItemReportSub.setIsOverPrint(false);
				dgExportInvoiceItemReportSub.setProjectType(projectType);
				dgExportInvoiceItemReportSub.setProcess(process);
				dgExportInvoiceItemReportSub.setVisible(true);

			} else {
				JOptionPane.showMessageDialog(FmMain.getInstance(),
						"当前出口报关单记录不存在,请先保存!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * 打印来料加工发票
	 */
	public void doOutPrint5() {// 装箱单(2)
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		// BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration)
		// prop
		// .get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		CustomsDeclarationSubReportDataSource.setSubReportData(commInfoModel
				.getList());
		// String attachedBillName = (String) prop.get("attachedBillName");
		// int projectType = (Integer) prop.get("projectType");
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		if (noTaoda) {
			try {
				CustomsDeclarationSubReportDataSource
						.setSubReportData(commInfoModel.getList());
				InputStream subReportStream = DgBaseExportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCMMInvoiceCommodityReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isOverPrint", new Boolean(true));
				parameters.put("subReport", subReport);
				InputStream reportStream = DgBaseExportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCMMInvoiceReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);
				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			try {
				CustomsDeclarationSubReportDataSource
						.setSubReportData(commInfoModel.getList());
				InputStream subReportStream = DgBaseExportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCMMInvoiceCommodityReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isOverPrint", new Boolean(false));
				parameters.put("subReport", subReport);
				InputStream reportStream = DgBaseExportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCMMInvoiceReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);
				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 打印内地海关及香港海关陆路出/出境载货清单
	 * 
	 * @param isTD
	 */
	public void doOutPrint7(boolean isTD, int ImpExpFlag) {
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		CustomsDeclarationSubReportDataSource.setSubReportData(commInfoModel
				.getList());
		// String attachedBillName = (String) prop.get("attachedBillName");
		int projectType = (Integer) prop.get("projectType");
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		List list = new ArrayList();
		list.add(customsDeclaration);
		if (noTaoda) {
			try {
				// 提运单号
				String homeCard = customsDeclaration.getBillOfLading();
				MotorCode motorCode = null;
				String emsNo = "";// 用来记录多份合同号(BCS与DZSC时),DZZC时只取一个emsHeadH2k
				if (homeCard != null && !homeCard.trim().equals("")) {
					motorCode = materialManageAction.findMotorCodeByCode(
							new Request(CommonVars.getCurrUser()), homeCard);
				}
				//
				// 相同运输工具的所有商品明细
				//
				List<Object> otherCommList = new ArrayList<Object>();
				String conveyance = customsDeclaration.getConveyance();// 运输工具
				Set<BaseCustomsDeclaration> set = new HashSet<BaseCustomsDeclaration>();
				Map<String, BaseCustomsDeclaration> customMap = new HashMap<String, BaseCustomsDeclaration>();
				//
				// 如果运输工具不为空的时候才进行检索
				//
				List allList = null;
				if (conveyance != null && !conveyance.trim().equals("")) {
					allList = this.baseEncAction
							.findCustomsDeclarationCommInfoByConveyance(
									new Request(CommonVars.getCurrUser()),
									conveyance, ImpExpFlag);
					Map<String, String> tempMap = new HashMap<String, String>();
					for (int i = 0; i < commInfoModel.getList().size(); i++) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) commInfoModel
								.getList().get(i);
						tempMap.put(b.getId(), b.getId());
					}
					for (Object obj : allList) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
						BaseCustomsDeclaration tempB = b
								.getBaseCustomsDeclaration();
						if (!customsDeclaration.getId().equals(tempB.getId())) {
							set.add(tempB);
						}
						if (tempMap.containsKey(b.getId())) {
							continue;
						}
						otherCommList.add(obj);
					}

					// 获取不同的合同号(BCS与DZSC)
					if (projectType == ProjectType.BCS) {
						customMap.put(customsDeclaration.getContract(),
								customsDeclaration);
						for (Object obj : allList) {
							BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
							BaseCustomsDeclaration tempB = b
									.getBaseCustomsDeclaration();
							customMap.put(tempB.getContract(), tempB);
						}
					} else {
						customMap.put(customsDeclaration.getEmsHeadH2k(),
								customsDeclaration);
						for (Object obj : allList) {
							BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
							BaseCustomsDeclaration tempB = b
									.getBaseCustomsDeclaration();
							customMap.put(tempB.getEmsHeadH2k(), tempB);
						}
					}
				}

				// 用来记录多份合同号(BCS与DZSC时),DZZC时只取一个emsHeadH2k
				Object[] array = customMap.keySet().toArray();
				if (array.length >= 4)
					emsNo = array[0].toString() + " " + array[1].toString()
							+ "\n" + array[2].toString() + " "
							+ array[3].toString();
				else if (array.length == 3)
					emsNo = array[0].toString() + " " + array[1].toString()
							+ "\n" + array[2].toString();
				else {
					for (int i = 0; i < array.length; i++) {
						emsNo += array[i].toString() + "\n";
					}
				}
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("emsNo", emsNo);

				//
				// 获得前6条
				//
				List<Object> tempList = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i < 6) {
						tempList.add(commInfoModel.getList().get(i));
					}
					if (i == 5) {
						break;
					}
				}
				//
				// 用 otherCommList 为 tempList 加到 6 个
				//
				if (tempList.size() < 6) {
					int count = tempList.size();
					for (int i = 0; i < otherCommList.size(); i++) {
						if (i < 6 - count) {
							tempList.add(otherCommList.get(i));
						} else {
							break;
						}
					}
				}

				String commodityNum = "";
				String grossWeight = "";
				int tempCommodityNum = 0;
				double tempGrossWeight = 0.0;
				Iterator<BaseCustomsDeclaration> iterator = set.iterator();
				while (iterator.hasNext()) {
					BaseCustomsDeclaration b = iterator.next();
					tempCommodityNum += b.getCommodityNum() == null ? 0 : b
							.getCommodityNum();
					tempGrossWeight += b.getGrossWeight() == null ? 0.0 : b
							.getGrossWeight();
				}
				tempCommodityNum += customsDeclaration.getCommodityNum() == null ? 0
						: customsDeclaration.getCommodityNum();
				tempGrossWeight += customsDeclaration.getGrossWeight() == null ? 0.0
						: customsDeclaration.getGrossWeight();
				commodityNum = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempCommodityNum));
				grossWeight = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempGrossWeight));
				commodityNum = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempCommodityNum));
				commodityNum += "(" + tempCommodityNum + ")件";
				grossWeight = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempGrossWeight));
				grossWeight += "(" + tempGrossWeight + ")公斤";

				if (allList == null)
					CustomsDeclarationSubReportDataSource
							.setSubReportData(tempList);
				else
					CustomsDeclarationSubReportDataSource
							.setSubReportData(allList);
				CustomsDeclarationSubReportDataSource
						.setContainerData(baseEncAction
								.findAllContainerByConveyance(new Request(
										CommonVars.getCurrUser()),
										customsDeclaration));
				InputStream subReportStream = DgBaseExportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsAutocarMainfestCommodityReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				// Company company = (Company) customsDeclaration.getCompany();
				Company company = (Company) CommonVars.getCurrUser()
						.getCompany();
				CustomReportDataSource subReportDataList = new CustomReportDataSource(
						tempList);
				Integer pageCount = 1;// 记录载货清单的页数
				// if (commInfoModel.getList().size() > 6)
				// pageCount = 2;
				if (allList != null) {
					Integer count = allList.size();
					if (count <= 6) {
						pageCount = 1;
					} else if (count <= 24 && count > 6) {
						pageCount = 2;
					} else if (count <= 42 && count > 24) {
						pageCount = 3;
					} else if (count <= 60 && count > 42) {
						pageCount = 4;
					} else if (count <= 78 && count > 60) {
						pageCount = 5;
					} else if (count <= 96 && count > 78) {
						pageCount = 6;
					} else {
						pageCount = 7;
					}
				}

				parameters.put("pageCount", pageCount.toString());
				ScmCoc scmcoc = customsDeclaration.getCustomer();
				parameters.put("isOverPrint", Boolean.valueOf(true));
				// parameters.put("acceptCompany", scmcoc.getName());
				// parameters.put("acceptCompanyAdd", scmcoc.getAddre());
				parameters.put("acceptCompany",
						scmcoc == null ? "" : scmcoc.getName());
				parameters.put("acceptCompanyAdd",
						scmcoc == null ? "" : scmcoc.getAddre());
				parameters.put("sendCompany", company.getName());
				parameters.put("sendCompanyAdd", company.getAddress());
				parameters.put("outTradeCo", customsDeclaration.getMachName());
				parameters.put("subReport", subReport);
				parameters.put("list", list);
				parameters.put("subReportDataList", subReportDataList);
				parameters.put("commodityNum", commodityNum);
				parameters.put("grossWeight", grossWeight);
				// 香港车牌
				parameters.put("hongkongCard",
						motorCode == null
								|| motorCode.getHongkongCard() == null ? ""
								: motorCode.getHongkongCard());
				// 运输公司名称
				parameters
						.put("trafficUnit",
								motorCode == null
										|| motorCode.getTrafficUnit() == null ? ""
										: motorCode.getTrafficUnit());
				parameters.put("trafficUnitAdd",
						motorCode == null
								|| motorCode.getTrafficUnitAdd() == null ? ""
								: motorCode.getTrafficUnitAdd());
				parameters.put("trafficUnitTel",
						motorCode == null
								|| motorCode.getTrafficUnitTel() == null ? ""
								: motorCode.getTrafficUnitTel());//
				parameters.put("name",
						motorCode == null || motorCode.getName() == null ? ""
								: motorCode.getName());// 司机姓名

				// BCS 时把手册编号换成合同号
				// if (projectType == ProjectType.BCS) {
				// parameters.put("emsNo", customsDeclaration.getContract());
				// } else {
				// parameters.put("emsNo", customsDeclaration.getEmsHeadH2k());
				// }
				InputStream reportStream = DgBaseExportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomesAutocarMainfestReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);

				//
				// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
				//
				List<Object> t = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i > 5) {
						t.add(commInfoModel.getList().get(i));
					}
				}
				//
				// 用 otherCommList 加到 t 中
				//
				int outCount = 6 - commInfoModel.getList().size();
				for (int i = 0; i < otherCommList.size(); i++) {
					if (i >= outCount) {
						t.add(otherCommList.get(i));
					}
				}

				if (isTD && t.size() > 0) {
					CustomsDeclarationSubReportDataSource.setSubReportData(t);
					InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
					InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
					JasperReport commInfoReport = (JasperReport) JRLoader
							.loadObject(commInfoReportStream);
					Map<String, Object> p = new HashMap<String, Object>();
					p.put("pageCount", pageCount.toString());
					p.put("commInfoReport", commInfoReport);
					JasperPrint jasperPrint2 = JasperFillManager.fillReport(
							masterReportStream, p, new CustomReportDataSource(
									list));
					for (int i = 0; i < jasperPrint2.getPages().size(); i++) {
						jasperPrint.addPage((JRPrintPage) jasperPrint2
								.getPages().get(i));
					}
				}

				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			try {
				String homeCard = customsDeclaration.getBillOfLading();
				MotorCode motorCode = null;
				String emsNo = "";// 用来记录多份合同号(BCS与DZSC时),DZZC时只取一个emsHeadH2k
				if (homeCard != null && !homeCard.trim().equals("")) {
					motorCode = materialManageAction.findMotorCodeByCode(
							new Request(CommonVars.getCurrUser()), homeCard);
				}
				//
				// 相同运输工具的所有商品明细
				//
				List<Object> otherCommList = new ArrayList<Object>();
				String conveyance = customsDeclaration.getConveyance();
				Set<BaseCustomsDeclaration> set = new HashSet<BaseCustomsDeclaration>();
				Map<String, BaseCustomsDeclaration> customMap = new HashMap<String, BaseCustomsDeclaration>();
				//
				// 如果运输工具不为空的时候才进行检索
				//
				List allList = null;
				if (conveyance != null && !conveyance.trim().equals("")) {
					allList = this.baseEncAction
							.findCustomsDeclarationCommInfoByConveyance(
									new Request(CommonVars.getCurrUser()),
									conveyance, ImpExpFlag);
					Map<String, String> tempMap = new HashMap<String, String>();
					for (int i = 0; i < commInfoModel.getList().size(); i++) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) commInfoModel
								.getList().get(i);
						tempMap.put(b.getId(), b.getId());
					}
					for (Object obj : allList) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
						BaseCustomsDeclaration tempB = b
								.getBaseCustomsDeclaration();
						if (!customsDeclaration.getId().equals(tempB.getId())) {
							set.add(tempB);
						}
						if (tempMap.containsKey(b.getId())) {
							continue;
						}
						otherCommList.add(obj);
					}
					// 获取不同的合同号(BCS与DZSC)
					if (projectType == ProjectType.BCS) {
						customMap.put(customsDeclaration.getContract(),
								customsDeclaration);
						for (Object obj : allList) {
							BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
							BaseCustomsDeclaration tempB = b
									.getBaseCustomsDeclaration();
							customMap.put(tempB.getContract(), tempB);
						}
					} else {
						customMap.put(customsDeclaration.getEmsHeadH2k(),
								customsDeclaration);
						for (Object obj : allList) {
							BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
							BaseCustomsDeclaration tempB = b
									.getBaseCustomsDeclaration();
							customMap.put(tempB.getEmsHeadH2k(), tempB);
						}
					}

				}

				// 用来记录多份合同号(BCS与DZSC时),DZZC时只取一个emsHeadH2k
				Object[] array = customMap.keySet().toArray();
				if (array.length >= 4)
					emsNo = array[0].toString() + " " + array[1].toString()
							+ "\n" + array[2].toString() + " "
							+ array[3].toString();
				else if (array.length == 3)
					emsNo = array[0].toString() + " " + array[1].toString()
							+ "\n" + array[2].toString();
				else {
					for (int i = 0; i < array.length; i++) {
						emsNo += array[i].toString() + "\n";
					}
				}
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("emsNo", emsNo);

				//
				// 获得前6条
				//
				List<Object> tempList = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i < 6) {
						tempList.add(commInfoModel.getList().get(i));
					}
					if (i == 5) {
						break;
					}
				}
				//
				// 用 otherCommList 为 tempList 加到 6 个
				//
				if (tempList.size() < 6) {
					int count = tempList.size();
					for (int i = 0; i < otherCommList.size(); i++) {
						if (i < 6 - count) {
							tempList.add(otherCommList.get(i));
						} else {
							break;
						}
					}
				}

				String commodityNum = "";
				String grossWeight = "";
				int tempCommodityNum = 0;
				double tempGrossWeight = 0.0;
				Iterator<BaseCustomsDeclaration> iterator = set.iterator();
				while (iterator.hasNext()) {
					BaseCustomsDeclaration b = iterator.next();
					tempCommodityNum += b.getCommodityNum() == null ? 0 : b
							.getCommodityNum();
					tempGrossWeight += b.getGrossWeight() == null ? 0.0 : b
							.getGrossWeight();
				}
				tempCommodityNum += customsDeclaration.getCommodityNum() == null ? 0
						: customsDeclaration.getCommodityNum();
				tempGrossWeight += customsDeclaration.getGrossWeight() == null ? 0.0
						: customsDeclaration.getGrossWeight();
				commodityNum = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempCommodityNum));
				grossWeight = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempGrossWeight));
				commodityNum = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempCommodityNum));
				commodityNum += "(" + tempCommodityNum + ")件";
				grossWeight = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempGrossWeight));
				grossWeight += "(" + tempGrossWeight + ")公斤";

				if (allList == null)
					CustomsDeclarationSubReportDataSource
							.setSubReportData(tempList);
				else
					CustomsDeclarationSubReportDataSource
							.setSubReportData(allList);
				CustomsDeclarationSubReportDataSource
						.setContainerData(baseEncAction
								.findAllContainerByConveyance(new Request(
										CommonVars.getCurrUser()),
										customsDeclaration));
				InputStream subReportStream = DgBaseExportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsAutocarMainfestCommodityReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);

				// Company company = (Company) customsDeclaration.getCompany();
				Company company = (Company) CommonVars.getCurrUser()
						.getCompany();
				CustomReportDataSource subReportDataList = new CustomReportDataSource(
						tempList);
				Integer pageCount = 1;// 记录载货清单的页数
				// if (commInfoModel.getList().size() > 6)
				// pageCount = 2;
				if (allList != null) {
					Integer count = allList.size();
					if (count <= 6) {
						pageCount = 1;
					} else if (count <= 24 && count > 6) {
						pageCount = 2;
					} else if (count <= 42 && count > 24) {
						pageCount = 3;
					} else if (count <= 60 && count > 42) {
						pageCount = 4;
					} else if (count <= 78 && count > 60) {
						pageCount = 5;
					} else if (count <= 96 && count > 78) {
						pageCount = 6;
					} else {
						pageCount = 7;
					}
				}

				parameters.put("pageCount", pageCount.toString());
				ScmCoc scmcoc = customsDeclaration.getCustomer();
				parameters.put("acceptCompany",
						scmcoc == null ? "" : scmcoc.getName());
				parameters.put("acceptCompanyAdd",
						scmcoc == null ? "" : scmcoc.getAddre());
				parameters.put("sendCompany", company.getName());
				parameters.put("sendCompanyAdd", company.getAddress());
				parameters.put("outTradeCo", customsDeclaration.getMachName());
				parameters.put("isOverPrint", Boolean.valueOf(false));
				// parameters.put("acceptCompany", company.getOutTradeCo());//
				// 外商公司
				// parameters.put("sendCompany", company.getName());// 加工公司
				parameters.put("subReport", subReport);
				parameters.put("list", list);
				parameters.put("subReportDataList", subReportDataList);
				parameters.put("commodityNum", commodityNum);
				parameters.put("grossWeight", grossWeight);
				// 香港车牌
				parameters.put("hongkongCard",
						motorCode == null
								|| motorCode.getHongkongCard() == null ? ""
								: motorCode.getHongkongCard());
				// 运输公司名称
				parameters
						.put("trafficUnit",
								motorCode == null
										|| motorCode.getTrafficUnit() == null ? ""
										: motorCode.getTrafficUnit());
				parameters.put("trafficUnitAdd",
						motorCode == null
								|| motorCode.getTrafficUnitAdd() == null ? ""
								: motorCode.getTrafficUnitAdd());
				parameters.put("trafficUnitTel",
						motorCode == null
								|| motorCode.getTrafficUnitTel() == null ? ""
								: motorCode.getTrafficUnitTel());//
				parameters.put("name",
						motorCode == null || motorCode.getName() == null ? ""
								: motorCode.getName());// 司机姓名
				// BCS 时把手册编号换成合同号
				// if (projectType == ProjectType.BCS) {
				// parameters.put("emsNo", customsDeclaration.getContract());
				// } else {
				// parameters.put("emsNo", customsDeclaration.getEmsHeadH2k());
				// }
				InputStream reportStream = DgBaseExportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomesAutocarMainfestReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);

				//
				// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
				//
				List<Object> t = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i > 5) {
						t.add(commInfoModel.getList().get(i));
					}
				}
				//
				// 用 otherCommList 加到 t 中
				//
				int outCount = 6 - commInfoModel.getList().size();
				for (int i = 0; i < otherCommList.size(); i++) {
					if (i >= outCount) {
						t.add(otherCommList.get(i));
					}
				}

				if (isTD && t.size() > 0) {
					CustomsDeclarationSubReportDataSource.setSubReportData(t);
					InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
					InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
					JasperReport commInfoReport = (JasperReport) JRLoader
							.loadObject(commInfoReportStream);
					Map<String, Object> p = new HashMap<String, Object>();
					p.put("pageCount", pageCount.toString());
					p.put("commInfoReport", commInfoReport);
					JasperPrint jasperPrint2 = JasperFillManager.fillReport(
							masterReportStream, p, new CustomReportDataSource(
									list));
					for (int i = 0; i < jasperPrint2.getPages().size(); i++) {
						jasperPrint.addPage((JRPrintPage) jasperPrint2
								.getPages().get(i));
					}
				}

				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}

		}
	}

	/**
	 * 打印内地海关及香港海关陆路出/出境载货清单（香港）+(附表)")
	 * 
	 * @param isTD
	 */
	public void doOutPrint9(boolean isTD) {
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		CustomsDeclarationSubReportDataSource.setSubReportData(commInfoModel
				.getList());
		// String attachedBillName = (String) prop.get("attachedBillName");
		int projectType = (Integer) prop.get("projectType");
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		List list = new ArrayList();
		list.add(customsDeclaration);
		if (noTaoda) {
			try {
				String homeCard = customsDeclaration.getBillOfLading();
				MotorCode motorCode = null;
				if (homeCard != null && !homeCard.trim().equals("")) {
					motorCode = materialManageAction.findMotorCodeByCode(
							new Request(CommonVars.getCurrUser()), homeCard);
				}
				//
				// 相同运输工具的所有商品明细
				//
				List<Object> otherCommList = new ArrayList<Object>();
				String conveyance = customsDeclaration.getConveyance();
				Set<BaseCustomsDeclaration> set = new HashSet<BaseCustomsDeclaration>();
				//
				// 如果运输工具不为空的时候才进行检索
				//
				List allList = null;
				if (conveyance != null && !conveyance.trim().equals("")) {
					allList = this.baseEncAction
							.findCustomsDeclarationCommInfoByConveyance(
									new Request(CommonVars.getCurrUser()),
									conveyance, ImpExpFlag.EXPORT);
					Map<String, String> tempMap = new HashMap<String, String>();
					for (int i = 0; i < commInfoModel.getList().size(); i++) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) commInfoModel
								.getList().get(i);
						tempMap.put(b.getId(), b.getId());
					}
					for (Object obj : allList) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
						BaseCustomsDeclaration tempB = b
								.getBaseCustomsDeclaration();
						if (!customsDeclaration.getId().equals(tempB.getId())) {
							set.add(tempB);
						}
						if (tempMap.containsKey(b.getId())) {
							continue;
						}
						otherCommList.add(obj);
					}
				}

				//
				// 获得前6条
				//
				List<Object> tempList = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i < 6) {
						tempList.add(commInfoModel.getList().get(i));
					}
					if (i == 5) {
						break;
					}
				}
				//
				// 用 otherCommList 为 tempList 加到 6 个
				//
				if (tempList.size() < 6) {
					int count = tempList.size();
					for (int i = 0; i < otherCommList.size(); i++) {
						if (i < 6 - count) {
							tempList.add(otherCommList.get(i));
						} else {
							break;
						}
					}
				}

				String commodityNum = "";
				String grossWeight = "";
				int tempCommodityNum = 0;
				double tempGrossWeight = 0.0;
				Iterator<BaseCustomsDeclaration> iterator = set.iterator();
				while (iterator.hasNext()) {
					BaseCustomsDeclaration b = iterator.next();
					tempCommodityNum += b.getCommodityNum() == null ? 0 : b
							.getCommodityNum();
					tempGrossWeight += b.getGrossWeight() == null ? 0.0 : b
							.getGrossWeight();
				}
				tempCommodityNum += customsDeclaration.getCommodityNum() == null ? 0
						: customsDeclaration.getCommodityNum();
				tempGrossWeight += customsDeclaration.getGrossWeight() == null ? 0.0
						: customsDeclaration.getGrossWeight();
				commodityNum = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempCommodityNum));
				grossWeight = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempGrossWeight));
				commodityNum = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempCommodityNum));
				commodityNum += "(" + tempCommodityNum + ")件";
				grossWeight = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempGrossWeight));
				grossWeight += "(" + tempGrossWeight + ")公斤";

				if (allList == null)
					CustomsDeclarationSubReportDataSource
							.setSubReportData(tempList);
				else
					CustomsDeclarationSubReportDataSource
							.setSubReportData(allList);
				CustomsDeclarationSubReportDataSource
						.setContainerData(baseEncAction
								.findAllContainerByConveyance(new Request(
										CommonVars.getCurrUser()),
										customsDeclaration));
				InputStream subReportStream = DgBaseExportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsAutocarMainfestCommodityReport2.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				// Company company = (Company) customsDeclaration.getCompany();
				Company company = (Company) CommonVars.getCurrUser()
						.getCompany();
				CustomReportDataSource subReportDataList = new CustomReportDataSource(
						tempList);
				Integer pageCount = 1;// 记录载货清单的页数
				// if (commInfoModel.getList().size() > 6)
				// pageCount = 2;
				if (allList != null) {
					Integer count = allList.size();
					if (count <= 6) {
						pageCount = 1;
					} else if (count <= 24 && count > 6) {
						pageCount = 2;
					} else if (count <= 42 && count > 24) {
						pageCount = 3;
					} else if (count <= 60 && count > 42) {
						pageCount = 4;
					} else if (count <= 78 && count > 60) {
						pageCount = 5;
					} else if (count <= 96 && count > 78) {
						pageCount = 6;
					} else {
						pageCount = 7;
					}
				}

				parameters.put("pageCount", pageCount.toString());
				ScmCoc scmcoc = customsDeclaration.getCustomer();
				parameters.put("acceptCompany",
						scmcoc == null ? "" : scmcoc.getName());
				parameters.put("acceptCompanyAdd",
						scmcoc == null ? "" : scmcoc.getAddre());
				parameters.put("sendCompany", company.getName());
				parameters.put("sendCompanyAdd", company.getAddress());
				parameters.put("isOverPrint", Boolean.valueOf(true));
				parameters.put("outTradeCo", customsDeclaration.getMachName());
				// parameters.put("acceptCompany", company.getOutTradeCo());//
				// 外商公司
				// parameters.put("sendCompany", company.getName());// 加工公司
				parameters.put("subReport", subReport);
				parameters.put("list", list);
				parameters.put("subReportDataList", subReportDataList);
				parameters.put("commodityNum", commodityNum);
				parameters.put("grossWeight", grossWeight);
				// 香港车牌
				parameters.put("hongkongCard",
						motorCode == null
								|| motorCode.getHongkongCard() == null ? ""
								: motorCode.getHongkongCard());
				// 运输公司名称
				parameters
						.put("trafficUnit",
								motorCode == null
										|| motorCode.getTrafficUnit() == null ? ""
										: motorCode.getTrafficUnit());
				parameters.put("trafficUnitAdd",
						motorCode == null
								|| motorCode.getTrafficUnitAdd() == null ? ""
								: motorCode.getTrafficUnitAdd());
				parameters.put("trafficUnitTel",
						motorCode == null
								|| motorCode.getTrafficUnitTel() == null ? ""
								: motorCode.getTrafficUnitTel());//
				parameters.put("name",
						motorCode == null || motorCode.getName() == null ? ""
								: motorCode.getName());// 司机姓名
				parameters.put("zuanghuo", customsDeclaration.getMachName());// 装货地点
				parameters.put("xeihuo",
						customsDeclaration.getCustoms() == null ? ""
								: customsDeclaration.getCustoms().getName());// 卸货地点
				// BCS 时把手册编号换成合同号
				if (projectType == ProjectType.BCS) {
					parameters.put("emsNo", customsDeclaration.getContract());
				} else {
					parameters.put("emsNo", customsDeclaration.getEmsHeadH2k());
				}
				InputStream reportStream = DgBaseExportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomesAutocarMainfestReport2.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);

				//
				// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
				//
				List<Object> t = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i > 5) {
						t.add(commInfoModel.getList().get(i));
					}
				}
				//
				// 用 otherCommList 加到 t 中
				//
				int outCount = 6 - commInfoModel.getList().size();
				for (int i = 0; i < otherCommList.size(); i++) {
					if (i >= outCount) {
						t.add(otherCommList.get(i));
					}
				}

				if (isTD && t.size() > 0) {
					CustomsDeclarationSubReportDataSource.setSubReportData(t);
					InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
					InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
					JasperReport commInfoReport = (JasperReport) JRLoader
							.loadObject(commInfoReportStream);
					Map<String, Object> p = new HashMap<String, Object>();
					p.put("pageCount", pageCount.toString());
					p.put("commInfoReport", commInfoReport);
					JasperPrint jasperPrint2 = JasperFillManager.fillReport(
							masterReportStream, p, new CustomReportDataSource(
									list));
					for (int i = 0; i < jasperPrint2.getPages().size(); i++) {
						jasperPrint.addPage((JRPrintPage) jasperPrint2
								.getPages().get(i));
					}
				}

				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			try {
				String homeCard = customsDeclaration.getBillOfLading();
				MotorCode motorCode = null;
				if (homeCard != null && !homeCard.trim().equals("")) {
					motorCode = materialManageAction.findMotorCodeByCode(
							new Request(CommonVars.getCurrUser()), homeCard);
				}
				//
				// 相同运输工具的所有商品明细
				//
				List<Object> otherCommList = new ArrayList<Object>();
				String conveyance = customsDeclaration.getConveyance();
				Set<BaseCustomsDeclaration> set = new HashSet<BaseCustomsDeclaration>();
				//
				// 如果运输工具不为空的时候才进行检索
				//
				List allList = null;
				if (conveyance != null && !conveyance.trim().equals("")) {
					allList = this.baseEncAction
							.findCustomsDeclarationCommInfoByConveyance(
									new Request(CommonVars.getCurrUser()),
									conveyance, ImpExpFlag.EXPORT);
					Map<String, String> tempMap = new HashMap<String, String>();
					for (int i = 0; i < commInfoModel.getList().size(); i++) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) commInfoModel
								.getList().get(i);
						tempMap.put(b.getId(), b.getId());
					}
					for (Object obj : allList) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
						BaseCustomsDeclaration tempB = b
								.getBaseCustomsDeclaration();
						if (!customsDeclaration.getId().equals(tempB.getId())) {
							set.add(tempB);
						}
						if (tempMap.containsKey(b.getId())) {
							continue;
						}
						otherCommList.add(obj);
					}
				}

				//
				// 获得前6条
				//
				List<Object> tempList = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i < 6) {
						tempList.add(commInfoModel.getList().get(i));
					}
					if (i == 5) {
						break;
					}
				}
				//
				// 用 otherCommList 为 tempList 加到 6 个
				//
				if (tempList.size() < 6) {
					int count = tempList.size();
					for (int i = 0; i < otherCommList.size(); i++) {
						if (i < 6 - count) {
							tempList.add(otherCommList.get(i));
						} else {
							break;
						}
					}
				}

				String commodityNum = "";
				String grossWeight = "";
				int tempCommodityNum = 0;
				double tempGrossWeight = 0.0;
				Iterator<BaseCustomsDeclaration> iterator = set.iterator();
				while (iterator.hasNext()) {
					BaseCustomsDeclaration b = iterator.next();
					tempCommodityNum += b.getCommodityNum() == null ? 0 : b
							.getCommodityNum();
					tempGrossWeight += b.getGrossWeight() == null ? 0.0 : b
							.getGrossWeight();
				}
				tempCommodityNum += customsDeclaration.getCommodityNum() == null ? 0
						: customsDeclaration.getCommodityNum();
				tempGrossWeight += customsDeclaration.getGrossWeight() == null ? 0.0
						: customsDeclaration.getGrossWeight();
				commodityNum = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempCommodityNum));
				grossWeight = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempGrossWeight));
				commodityNum = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempCommodityNum));
				commodityNum += "(" + tempCommodityNum + ")件";
				grossWeight = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempGrossWeight));
				grossWeight += "(" + tempGrossWeight + ")公斤";

				if (allList == null)
					CustomsDeclarationSubReportDataSource
							.setSubReportData(tempList);
				else
					CustomsDeclarationSubReportDataSource
							.setSubReportData(allList);
				CustomsDeclarationSubReportDataSource
						.setContainerData(baseEncAction
								.findAllContainerByConveyance(new Request(
										CommonVars.getCurrUser()),
										customsDeclaration));
				InputStream subReportStream = DgBaseExportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsAutocarMainfestCommodityReport2.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				// Company company = (Company) customsDeclaration.getCompany();
				Company company = (Company) CommonVars.getCurrUser()
						.getCompany();
				CustomReportDataSource subReportDataList = new CustomReportDataSource(
						tempList);
				Integer pageCount = 1;// 记录载货清单的页数
				// if (commInfoModel.getList().size() > 6)
				// pageCount = 2;
				if (allList != null) {
					Integer count = allList.size();
					if (count <= 6) {
						pageCount = 1;
					} else if (count <= 24 && count > 6) {
						pageCount = 2;
					} else if (count <= 42 && count > 24) {
						pageCount = 3;
					} else if (count <= 60 && count > 42) {
						pageCount = 4;
					} else if (count <= 78 && count > 60) {
						pageCount = 5;
					} else if (count <= 96 && count > 78) {
						pageCount = 6;
					} else {
						pageCount = 7;
					}
				}

				parameters.put("pageCount", pageCount.toString());
				ScmCoc scmcoc = customsDeclaration.getCustomer();
				parameters.put("acceptCompany",
						scmcoc == null ? "" : scmcoc.getName());
				;
				parameters.put("acceptCompanyAdd",
						scmcoc == null ? "" : scmcoc.getAddre());
				parameters.put("sendCompany", company.getName());
				parameters.put("sendCompanyAdd", company.getAddress());
				parameters.put("outTradeCo", customsDeclaration.getMachName());
				parameters.put("isOverPrint", Boolean.valueOf(false));
				// parameters.put("acceptCompany", company.getOutTradeCo());//
				// 外商公司
				// parameters.put("sendCompany", company.getName());// 加工公司
				parameters.put("subReport", subReport);
				parameters.put("list", list);
				parameters.put("subReportDataList", subReportDataList);
				parameters.put("commodityNum", commodityNum);
				parameters.put("grossWeight", grossWeight);
				// 香港车牌
				parameters.put("hongkongCard",
						motorCode == null
								|| motorCode.getHongkongCard() == null ? ""
								: motorCode.getHongkongCard());
				// 运输公司名称
				parameters
						.put("trafficUnit",
								motorCode == null
										|| motorCode.getTrafficUnit() == null ? ""
										: motorCode.getTrafficUnit());
				parameters.put("trafficUnitAdd",
						motorCode == null
								|| motorCode.getTrafficUnitAdd() == null ? ""
								: motorCode.getTrafficUnitAdd());
				parameters.put("trafficUnitTel",
						motorCode == null
								|| motorCode.getTrafficUnitTel() == null ? ""
								: motorCode.getTrafficUnitTel());//
				parameters.put("name",
						motorCode == null || motorCode.getName() == null ? ""
								: motorCode.getName());// 司机姓名
				parameters.put("zuanghuo", customsDeclaration.getMachName());// 装货地点
				parameters.put("xeihuo",
						customsDeclaration.getCustoms() == null ? ""
								: customsDeclaration.getCustoms().getName());// 卸货地点
				// BCS 时把手册编号换成合同号
				if (projectType == ProjectType.BCS) {
					parameters.put("emsNo", customsDeclaration.getContract());
				} else {
					parameters.put("emsNo", customsDeclaration.getEmsHeadH2k());
				}
				InputStream reportStream = DgBaseExportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomesAutocarMainfestReport2.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);

				//
				// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
				//
				List<Object> t = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i > 5) {
						t.add(commInfoModel.getList().get(i));
					}
				}
				//
				// 用 otherCommList 加到 t 中
				//
				int outCount = 6 - commInfoModel.getList().size();
				for (int i = 0; i < otherCommList.size(); i++) {
					if (i >= outCount) {
						t.add(otherCommList.get(i));
					}
				}

				if (isTD && t.size() > 0) {
					CustomsDeclarationSubReportDataSource.setSubReportData(t);
					InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
					InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
					JasperReport commInfoReport = (JasperReport) JRLoader
							.loadObject(commInfoReportStream);
					Map<String, Object> p = new HashMap<String, Object>();
					p.put("pageCount", pageCount.toString());
					p.put("commInfoReport", commInfoReport);
					JasperPrint jasperPrint2 = JasperFillManager.fillReport(
							masterReportStream, p, new CustomReportDataSource(
									list));
					for (int i = 0; i < jasperPrint2.getPages().size(); i++) {
						jasperPrint.addPage((JRPrintPage) jasperPrint2
								.getPages().get(i));
					}
				}

				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
				dgReportViewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 打印内地海关及香港海关陆路出/出境载货清单（香港）
	 * 
	 * @param isTD
	 */
	public void doOutMergeZaiHuoListPrint() {
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		// JTableListModel commInfoModel = (JTableListModel) prop
		// .get("commInfoModel");
		String conveyance = customsDeclaration.getConveyance();// 运输工具
		List customDeclartList = baseEncAction
				.findCustomsDeclarationCommInfoByConveyance(new Request(
						CommonVars.getCurrUser()), conveyance,
						ImpExpFlag.EXPORT);
		CustomsDeclarationSubReportDataSource
				.setSubReportData(customDeclartList);
		String commodityNum = "";
		String grossWeight = "";
		double tempCommodityNum = 0.0;
		double tempGrossWeight = 0.0;
		List<String> listKey = new ArrayList<String>();
		Set<BaseCustomsDeclaration> set = new HashSet<BaseCustomsDeclaration>();
		for (int i = 0; i < customDeclartList.size(); i++) {
			BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) customDeclartList
					.get(i);
			BaseCustomsDeclaration tempB = b.getBaseCustomsDeclaration();
			if (listKey.contains(tempB.getId())) {
				continue;
			}
			listKey.add(tempB.getId());
			set.add(tempB);
		}
		Iterator<BaseCustomsDeclaration> iterator = set.iterator();
		while (iterator.hasNext()) {
			BaseCustomsDeclaration tempB = iterator.next();
			tempCommodityNum += (tempB.getCommodityNum() == null ? 0.0 : Double
					.valueOf(tempB.getCommodityNum()));// 件数
			tempGrossWeight += (tempB.getGrossWeight() == null ? 0.0 : tempB
					.getGrossWeight());// 毛重
		}
		int projectType = (Integer) prop.get("projectType");
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		List list = new ArrayList();
		list.add(customsDeclaration);
		DgIEMergerZaiHuoListReportSet dg = new DgIEMergerZaiHuoListReportSet();
		dg.setBaseCustomsDeclaration(customsDeclaration);
		dg.setImpExpFlag(ImpExpFlag.EXPORT);
		dg.setBaseEncAction(baseEncAction);
		dg.setVisible(true);
		if (dg.getIsOk()) {
			//
			// 如果运输工具不为空的时候才进行检索
			//
			List allList = dg.getReturnList();

			if (noTaoda) {
				try {
					String homeCard = customsDeclaration.getBillOfLading();
					MotorCode motorCode = null;
					if (homeCard != null && !homeCard.trim().equals("")) {
						motorCode = materialManageAction
								.findMotorCodeByCode(
										new Request(CommonVars.getCurrUser()),
										homeCard);
					}

					commodityNum = ConvertNumberToUpperCase
							.convertInteger(String.valueOf(tempCommodityNum));
					commodityNum += "(" + tempCommodityNum + ")件";
					grossWeight = ConvertNumberToUpperCase
							.convertInteger(CommonUtils.formatDoubleByDigit(
									tempGrossWeight, 4));
					grossWeight += "("
							+ CommonUtils.formatDoubleByDigit(tempGrossWeight,
									4) + ")公斤";

					CustomsDeclarationSubReportDataSource
							.setContainerData(baseEncAction
									.findAllContainerByConveyance(new Request(
											CommonVars.getCurrUser()),
											customsDeclaration));
					InputStream subReportStream = DgBaseExportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsAutocarMainfestCommodityReport2.jasper");
					JasperReport subReport = (JasperReport) JRLoader
							.loadObject(subReportStream);
					Map<String, Object> parameters = new HashMap<String, Object>();
					Company company = (Company) CommonVars.getCurrUser()
							.getCompany();
					CustomReportDataSource subReportDataList = new CustomReportDataSource(
							allList);
					Integer pageCount = 1;// 记录载货清单的页数
					parameters.put("pageCount", pageCount.toString());
					ScmCoc scmcoc = customsDeclaration.getCustomer();
					parameters.put("acceptCompany", scmcoc == null ? ""
							: scmcoc.getName());
					parameters.put("acceptCompanyAdd", scmcoc == null ? ""
							: scmcoc.getAddre());
					parameters.put("sendCompany", company.getName());
					parameters.put("sendCompanyAdd", company.getAddress());
					parameters.put("isOverPrint", Boolean.valueOf(true));
					parameters.put("outTradeCo",
							customsDeclaration.getMachName());
					// 外商公司
					parameters.put("subReport", subReport);
					parameters.put("list", list);
					parameters.put("subReportDataList", subReportDataList);
					parameters.put("commodityNum", commodityNum);
					parameters.put("grossWeight", grossWeight);
					// 香港车牌
					parameters.put("hongkongCard", motorCode == null
							|| motorCode.getHongkongCard() == null ? ""
							: motorCode.getHongkongCard());
					// 运输公司名称
					parameters.put("trafficUnit", motorCode == null
							|| motorCode.getTrafficUnit() == null ? ""
							: motorCode.getTrafficUnit());
					parameters.put("trafficUnitAdd", motorCode == null
							|| motorCode.getTrafficUnitAdd() == null ? ""
							: motorCode.getTrafficUnitAdd());
					parameters.put("trafficUnitTel", motorCode == null
							|| motorCode.getTrafficUnitTel() == null ? ""
							: motorCode.getTrafficUnitTel());//
					parameters
							.put("name",
									motorCode == null
											|| motorCode.getName() == null ? ""
											: motorCode.getName());// 司机姓名
					parameters.put("zuanghuo", dg.getZuangHou());// 装货地点
					parameters.put("xeihuo", dg.getXieHou());// 卸货地点
					// BCS 时把手册编号换成合同号
					if (projectType == ProjectType.BCS) {
						parameters.put("emsNo",
								customsDeclaration.getContract());
					} else {
						parameters.put("emsNo",
								customsDeclaration.getEmsHeadH2k());
					}
					InputStream reportStream = DgBaseExportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomesAutocarMainfestReport2.jasper");
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							reportStream, parameters, ds);
					DgReportViewer dgReportViewer = new DgReportViewer(
							jasperPrint);
					dgReportViewer.setVisible(true);
				} catch (JRException e1) {
					e1.printStackTrace();
				}
			} else {
				try {
					String homeCard = customsDeclaration.getBillOfLading();
					MotorCode motorCode = null;
					if (homeCard != null && !homeCard.trim().equals("")) {
						motorCode = materialManageAction
								.findMotorCodeByCode(
										new Request(CommonVars.getCurrUser()),
										homeCard);
					}
					commodityNum = ConvertNumberToUpperCase
							.convertInteger(String.valueOf(tempCommodityNum));
					commodityNum += "(" + tempCommodityNum + ")件";
					grossWeight = ConvertNumberToUpperCase
							.convertInteger(CommonUtils.formatDoubleByDigit(
									tempGrossWeight, 4));
					grossWeight += "("
							+ CommonUtils.formatDoubleByDigit(tempGrossWeight,
									4) + ")公斤";
					CustomsDeclarationSubReportDataSource
							.setContainerData(baseEncAction
									.findAllContainerByConveyance(new Request(
											CommonVars.getCurrUser()),
											customsDeclaration));
					InputStream subReportStream = DgBaseExportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsAutocarMainfestCommodityReport2.jasper");
					JasperReport subReport = (JasperReport) JRLoader
							.loadObject(subReportStream);
					Map<String, Object> parameters = new HashMap<String, Object>();
					Company company = (Company) CommonVars.getCurrUser()
							.getCompany();
					CustomReportDataSource subReportDataList = new CustomReportDataSource(
							allList);
					Integer pageCount = 1;// 记录载货清单的页数
					parameters.put("pageCount", pageCount.toString());
					ScmCoc scmcoc = customsDeclaration.getCustomer();
					parameters.put("acceptCompany", scmcoc == null ? ""
							: scmcoc.getName());
					parameters.put("acceptCompanyAdd", scmcoc == null ? ""
							: scmcoc.getAddre());
					parameters.put("sendCompany", company.getName());
					parameters.put("sendCompanyAdd", company.getAddress());
					parameters.put("outTradeCo",
							customsDeclaration.getMachName());
					parameters.put("isOverPrint", Boolean.valueOf(false));
					// 外商公司
					parameters.put("subReport", subReport);
					parameters.put("list", list);
					parameters.put("subReportDataList", subReportDataList);
					parameters.put("commodityNum", commodityNum);
					parameters.put("grossWeight", grossWeight);
					// 香港车牌
					parameters.put("hongkongCard", motorCode == null
							|| motorCode.getHongkongCard() == null ? ""
							: motorCode.getHongkongCard());
					// 运输公司名称
					parameters.put("trafficUnit", motorCode == null
							|| motorCode.getTrafficUnit() == null ? ""
							: motorCode.getTrafficUnit());
					parameters.put("trafficUnitAdd", motorCode == null
							|| motorCode.getTrafficUnitAdd() == null ? ""
							: motorCode.getTrafficUnitAdd());
					parameters.put("trafficUnitTel", motorCode == null
							|| motorCode.getTrafficUnitTel() == null ? ""
							: motorCode.getTrafficUnitTel());//
					parameters
							.put("name",
									motorCode == null
											|| motorCode.getName() == null ? ""
											: motorCode.getName());// 司机姓名
					parameters.put("zuanghuo", dg.getZuangHou());// 装货地点
					parameters.put("xeihuo", dg.getXieHou());// 卸货地点
					// BCS 时把手册编号换成合同号
					if (projectType == ProjectType.BCS) {
						parameters.put("emsNo",
								customsDeclaration.getContract());
					} else {
						parameters.put("emsNo",
								customsDeclaration.getEmsHeadH2k());
					}
					InputStream reportStream = DgBaseExportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomesAutocarMainfestReport2.jasper");
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							reportStream, parameters, ds);

					DgReportViewer dgReportViewer = new DgReportViewer(
							jasperPrint);
					dgReportViewer.setVisible(true);
				} catch (JRException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 内地海关及香港海关陆路出/进境载货清单(附表)
	 */
	public void doOutPrint10() {
		// CustomReportDataSource ds = (CustomReportDataSource) prop
		// .get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		CustomsDeclarationSubReportDataSource.setSubReportData(commInfoModel
				.getList());
		// String attachedBillName = (String) prop.get("attachedBillName");
		// int projectType = (Integer) prop.get("projectType");
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		List list = new ArrayList();
		list.add(customsDeclaration);
		if (noTaoda) {
			try {
				//
				// 相同运输工具的所有商品明细
				//
				List<Object> otherCommList = new ArrayList<Object>();
				String conveyance = customsDeclaration.getConveyance();
				//
				// 如果运输工具不为空的时候才进行检索
				//
				if (conveyance != null && !conveyance.trim().equals("")) {
					List allList = this.baseEncAction
							.findCustomsDeclarationCommInfoByConveyance(
									new Request(CommonVars.getCurrUser()),
									conveyance, ImpExpFlag.EXPORT);
					Map<String, String> tempMap = new HashMap<String, String>();
					for (int i = 0; i < commInfoModel.getList().size(); i++) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) commInfoModel
								.getList().get(i);
						tempMap.put(b.getId(), b.getId());
					}
					for (Object obj : allList) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
						if (tempMap.containsKey(b.getId())) {
							continue;
						}
						otherCommList.add(obj);
					}
				}

				//
				// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
				//
				List<Object> t = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i > 5) {
						t.add(commInfoModel.getList().get(i));
					}
				}
				//
				// 用 otherCommList 加到 t 中
				//
				int outCount = 6 - commInfoModel.getList().size();
				for (int i = 0; i < otherCommList.size(); i++) {
					if (i >= outCount) {
						t.add(otherCommList.get(i));
					}
				}

				CustomsDeclarationSubReportDataSource.setSubReportData(t);
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> p = new HashMap<String, Object>();
				Integer pageCount = 1;// 记录载货清单的页数
				if (commInfoModel.getList().size() > 6)
					pageCount = 2;
				p.put("pageCount", pageCount.toString());
				p.put("commInfoReport", commInfoReport);
				p.put("isOverPrint", Boolean.valueOf(true));
				JasperPrint jasperPrint2 = JasperFillManager
						.fillReport(masterReportStream, p,
								new CustomReportDataSource(list));
				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint2);
				dgReportViewer.setVisible(true);

			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			try {
				//
				// 相同运输工具的所有商品明细
				//
				List<Object> otherCommList = new ArrayList<Object>();
				String conveyance = customsDeclaration.getConveyance();
				//
				// 如果运输工具不为空的时候才进行检索
				//
				if (conveyance != null && !conveyance.trim().equals("")) {
					List allList = this.baseEncAction
							.findCustomsDeclarationCommInfoByConveyance(
									new Request(CommonVars.getCurrUser()),
									conveyance, ImpExpFlag.EXPORT);
					Map<String, String> tempMap = new HashMap<String, String>();
					for (int i = 0; i < commInfoModel.getList().size(); i++) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) commInfoModel
								.getList().get(i);
						tempMap.put(b.getId(), b.getId());
					}
					for (Object obj : allList) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
						if (tempMap.containsKey(b.getId())) {
							continue;
						}
						otherCommList.add(obj);
					}
				}

				//
				// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
				//
				List<Object> t = new ArrayList<Object>();
				for (int i = 0; i < commInfoModel.getList().size(); i++) {
					if (i > 5) {
						t.add(commInfoModel.getList().get(i));
					}
				}
				//
				// 用 otherCommList 加到 t 中
				//
				int outCount = 6 - commInfoModel.getList().size();
				for (int i = 0; i < otherCommList.size(); i++) {
					if (i >= outCount) {
						t.add(otherCommList.get(i));
					}
				}

				CustomsDeclarationSubReportDataSource.setSubReportData(t);
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> p = new HashMap<String, Object>();
				Integer pageCount = 1;// 记录载货清单的页数
				if (commInfoModel.getList().size() > 6)
					pageCount = 2;
				p.put("isOverPrint", Boolean.valueOf(false));
				p.put("pageCount", pageCount.toString());
				p.put("commInfoReport", commInfoReport);
				JasperPrint jasperPrint2 = JasperFillManager
						.fillReport(masterReportStream, p,
								new CustomReportDataSource(list));
				DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint2);
				dgReportViewer.setVisible(true);

			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 打印装箱单(2)表
	 */
	public void doOutPrint11() {
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		CustomsDeclarationSubReportDataSource.setSubReportData(commInfoModel
				.getList());
		// String attachedBillName = (String) prop.get("attachedBillName");
		int projectType = (Integer) prop.get("projectType");
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		if (noTaoda) {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoModel.getList());
			CommonVars.setSumMoney(Double.valueOf(0));
			CommonVars.setBeforeSumMoney(Double.valueOf(0));
			try {
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/WExportCommodityEncasementReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/WExportCommodityEncasementSubReport.jasper");
				String billNo5 = customsDeclaration.getBillListId(); // 报关清单号码
				String impexpno5 = baseEncAction.getImpExpNoByBillNo(
						new Request(CommonVars.getCurrUser()), billNo5);
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("overprint", new Boolean(false));
				parameters.put("isExport", true);
				parameters.put("impexpno", impexpno5);
				parameters.put("projectType", String.valueOf(projectType));
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoModel.getList());
			CommonVars.setSumMoney(Double.valueOf(0));
			CommonVars.setBeforeSumMoney(Double.valueOf(0));
			try {
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/WExportCommodityEncasementReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/WExportCommodityEncasementSubReport.jasper");
				String billNo3 = customsDeclaration.getBillListId(); // 报关清单号码
				String impexpno3 = baseEncAction.getImpExpNoByBillNo(
						new Request(CommonVars.getCurrUser()), billNo3);
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("overprint", new Boolean(true));
				parameters.put("isExport", true);
				parameters.put("impexpno", impexpno3);
				parameters.put("projectType", String.valueOf(projectType));
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 打印装箱单(3)表
	 */
	public void doOutPrint12() {//
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		CustomsDeclarationSubReportDataSource.setSubReportData(commInfoModel
				.getList());
		// String attachedBillName = (String) prop.get("attachedBillName");
		// int projectType = (Integer) prop.get("projectType");
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		if (noTaoda) {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoModel.getList());
			CommonVars.setSumMoney(Double.valueOf(0));
			CommonVars.setBeforeSumMoney(Double.valueOf(0));
			try {
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/EcsExportCommodityEncasementReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/EcsExportCommodityEncasementSubReport.jasper");
				String billNo5 = customsDeclaration.getBillListId(); // 报关清单号码
				String impexpno5 = baseEncAction.getImpExpNoByBillNo(
						new Request(CommonVars.getCurrUser()), billNo5);
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("overprint", new Boolean(false));
				parameters.put("isExport", true);
				parameters.put("impexpno", impexpno5);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoModel.getList());
			CommonVars.setSumMoney(Double.valueOf(0));
			CommonVars.setBeforeSumMoney(Double.valueOf(0));
			try {
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/EcsExportCommodityEncasementReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/EcsExportCommodityEncasementSubReport.jasper");
				String billNo3 = customsDeclaration.getBillListId(); // 报关清单号码
				String impexpno3 = baseEncAction.getImpExpNoByBillNo(
						new Request(CommonVars.getCurrUser()), billNo3);
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("overprint", new Boolean(true));
				parameters.put("isExport", true);
				parameters.put("impexpno", impexpno3);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 打印装箱单4表
	 */
	public void doOutPrint13() {// 装箱单(2)
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		CustomsDeclarationSubReportDataSource.setSubReportData(commInfoModel
				.getList());
		// String attachedBillName = (String) prop.get("attachedBillName");
		// int projectType = (Integer) prop.get("projectType");
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		if (noTaoda) {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoModel.getList());
			String billNo = customsDeclaration.getBillListId(); // 报关清单号码
			CommonVars.setSumMoney(Double.valueOf(0));
			CommonVars.setBeforeSumMoney(Double.valueOf(0));
			String impexpno = baseEncAction.getImpExpNoByBillNo(new Request(
					CommonVars.getCurrUser()), billNo);
			try {
				String homeCard = customsDeclaration.getBillOfLading();
				//
				// 汔车司机
				//
				String driver = "";
				if (homeCard != null && !homeCard.trim().equals("")) {
					MotorCode motorCode = materialManageAction
							.findMotorCodeByCode(
									new Request(CommonVars.getCurrUser()),
									homeCard);
					driver = motorCode == null || motorCode.getName() == null ? ""
							: motorCode.getName();
				}

				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCommodityEncasementReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCommodityEncasementSubReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("isOverprint", false);
				parameters.put("isExport", true);
				parameters.put("impexpno", impexpno);
				parameters.put("driver", driver);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoModel.getList());
			String billNo2 = customsDeclaration.getBillListId(); // 报关清单号码
			String impexpno2 = baseEncAction.getImpExpNoByBillNo(new Request(
					CommonVars.getCurrUser()), billNo2);
			CommonVars.setSumMoney(Double.valueOf(0));
			CommonVars.setBeforeSumMoney(Double.valueOf(0));
			try {
				String homeCard = customsDeclaration.getBillOfLading();
				//
				// 汔车司机
				//
				String driver = "";
				if (homeCard != null && !homeCard.trim().equals("")) {
					MotorCode motorCode = materialManageAction
							.findMotorCodeByCode(
									new Request(CommonVars.getCurrUser()),
									homeCard);
					driver = motorCode == null || motorCode.getName() == null ? ""
							: motorCode.getName();
				}

				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCommodityEncasementReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCommodityEncasementSubReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("isOverprint", true);
				parameters.put("isExport", true);
				parameters.put("impexpno", impexpno2);
				parameters.put("driver", driver);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 装箱单(包装种类在明细) 2010-05-21 hcl
	 */
	public void doOutPrintZhangXiangDan() {
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		CustomsDeclarationSubReportDataSource.setSubReportData(commInfoModel
				.getList());
		// String attachedBillName = (String) prop.get("attachedBillName");
		int projectType = (Integer) prop.get("projectType");
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		if (noTaoda) {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoModel.getList());
			CommonVars.setSumMoney(Double.valueOf(0));
			CommonVars.setBeforeSumMoney(Double.valueOf(0));
			try {
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/WExportCommodityEncasementReport_mingxi.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/WExportCommodityEncasementSubReport_mingxi.jasper");
				String billNo5 = customsDeclaration.getBillListId(); // 报关清单号码
				String impexpno5 = baseEncAction.getImpExpNoByBillNo(
						new Request(CommonVars.getCurrUser()), billNo5);
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("overprint", new Boolean(false));
				parameters.put("isExport", true);
				parameters.put("impexpno", impexpno5);
				parameters.put("projectType", String.valueOf(projectType));
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoModel.getList());
			CommonVars.setSumMoney(Double.valueOf(0));
			CommonVars.setBeforeSumMoney(Double.valueOf(0));
			try {
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/WExportCommodityEncasementReport_mingxi.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/WExportCommodityEncasementSubReport_mingxi.jasper");
				String billNo3 = customsDeclaration.getBillListId(); // 报关清单号码
				String impexpno3 = baseEncAction.getImpExpNoByBillNo(
						new Request(CommonVars.getCurrUser()), billNo3);
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("overprint", new Boolean(true));
				parameters.put("isExport", true);
				parameters.put("impexpno", impexpno3);
				parameters.put("projectType", String.valueOf(projectType));
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 装箱单(包装种类在明细)商品编码及箱号
	 */
	public void doOutPrintZhangXiangDanComplexOrBoxNo(boolean isComplex) {
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		// ///柯鹏程
		// Map<String,Integer> mapWrap = new HashMap<String,Integer>();
		// for (int i = 0; i < commInfoModel.getList().size(); i++) {
		// BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo =
		// (BcsCustomsDeclarationCommInfo )commInfoModel.getList().get(i);
		// int type =
		// bcsCustomsDeclarationCommInfo.getBaseCustomsDeclaration().getImpExpType();
		// List<ImpExpCommodityInfo> impExpCommodityInfos = encAction
		// .findTempImpExpCommodityInfoBycustomsInfo(new Request(
		// CommonVars.getCurrUser()),
		// bcsCustomsDeclarationCommInfo, type);
		// for (int j = 0; j < impExpCommodityInfos.size(); j++) {
		// ImpExpCommodityInfo
		// impExpCommodityInfo=(ImpExpCommodityInfo)impExpCommodityInfos.get(j);
		// Wrap wrap = impExpCommodityInfo.getWrapType();
		// if(wrap!=null&&impExpCommodityInfo.getPiece()!=null){
		// if(mapWrap.get(wrap.getName())==null){
		// mapWrap.put(wrap.getName(), impExpCommodityInfo.getPiece());
		// }else{
		// mapWrap.put(wrap.getName(),
		// mapWrap.get(wrap.getName())+impExpCommodityInfo.getPiece());
		// }
		// }
		// }
		// }
		// String wraps = "";
		// Set<String> keys = mapWrap.keySet();
		// for(Iterator it = keys.iterator();it.hasNext();){
		// String key = (String)it.next();
		// // System.out.println(key+"==="+mapWrap.get(key));
		// wraps += key+":"+mapWrap.get(key)+"; ";
		// }
		// ////
		CustomsDeclarationSubReportDataSource.setSubReportData(commInfoModel
				.getList());
		// String attachedBillName = (String) prop.get("attachedBillName");
		int projectType = (Integer) prop.get("projectType");
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		if (noTaoda) {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoModel.getList());
			CommonVars.setSumMoney(Double.valueOf(0));
			CommonVars.setBeforeSumMoney(Double.valueOf(0));
			try {
				InputStream masterReportStream = null;
				InputStream commInfoReportStream = null;
				if (isComplex) {
					masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/WExportCommodityEncasementComplexReport.jasper");
					commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/WExportCommodityEncasementComplexSubReport.jasper");
				} else {
					masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/WExportCommodityEncasementBoxNoReport.jasper");
					commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/WExportCommodityEncasementBoxNoSubReport.jasper");
				}
				String billOfLading = customsDeclaration.getBillOfLading();// 提运单号
				String name;// 司机姓名
				String trafficUnit;// 运输单位
				String receiverUnit = baseEncAction
						.getReceiverUnit(new Request(CommonVars.getCurrUser()));
				List isExportPackinglistOrInvoiceList = baseEncAction
						.findExportPackinglistOrInvoice(new Request(CommonVars
								.getCurrUser(), true));

				Boolean isExportPackinglistOrInvoice = (Boolean) isExportPackinglistOrInvoiceList
						.get(0);
				if (isExportPackinglistOrInvoice == null) {
					isExportPackinglistOrInvoice = false;
				}

				if (billOfLading == null) {
					name = "";
					trafficUnit = "";

				} else {
					MotorCode motor = materialManageAction
							.findMotorCodeByCode(
									new Request(CommonVars.getCurrUser()),
									billOfLading);
					if (motor == null) {
						name = "";
						trafficUnit = "";
					} else {
						name = motor.getName() == null ? "" : motor.getName();// 司机姓名
						trafficUnit = motor.getTrafficUnit() == null ? ""
								: motor.getTrafficUnit();// 运输单位
					}
				}
				String billNo5 = customsDeclaration.getBillListId(); // 报关清单号码
				String impexpno5 = baseEncAction.getImpExpNoByBillNo(
						new Request(CommonVars.getCurrUser()), billNo5);
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				if (true == isExportPackinglistOrInvoice) {
					parameters.put("receiverUnit", receiverUnit);
					parameters.put("receiverCompany", "购货单位");
				}
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("overprint", new Boolean(false));
				parameters.put("isExport", true);
				parameters.put("impexpno", impexpno5);
				parameters.put("name", name);
				parameters.put("trafficUnit", trafficUnit);
				parameters.put("complexOrBoxNo", Boolean.valueOf(isComplex));// 是否打开箱号还是商品编码
				parameters.put("projectType", String.valueOf(projectType));
				// parameters.put("wraps", wraps);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoModel.getList());
			CommonVars.setSumMoney(Double.valueOf(0));
			CommonVars.setBeforeSumMoney(Double.valueOf(0));
			try {
				InputStream masterReportStream = null;
				InputStream commInfoReportStream = null;
				if (isComplex) {
					masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/WExportCommodityEncasementComplexReport.jasper");
					commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/WExportCommodityEncasementComplexSubReport.jasper");
				} else {
					masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/WExportCommodityEncasementBoxNoReport.jasper");
					commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/WExportCommodityEncasementBoxNoSubReport.jasper");
				}
				String billOfLading = customsDeclaration.getBillOfLading();// 提运单号
				String name;// 司机姓名
				String trafficUnit;// 运输单位
				if (billOfLading == null) {
					name = "";
					trafficUnit = "";

				} else {
					MotorCode motor = materialManageAction
							.findMotorCodeByCode(
									new Request(CommonVars.getCurrUser()),
									billOfLading);
					if (motor == null) {
						name = "";
						trafficUnit = "";
					} else {
						name = motor.getName() == null ? "" : motor.getName();// 司机姓名
						trafficUnit = motor.getTrafficUnit() == null ? ""
								: motor.getTrafficUnit();// 运输单位
					}
				}
				String billNo3 = customsDeclaration.getBillListId(); // 报关清单号码
				String impexpno3 = baseEncAction.getImpExpNoByBillNo(
						new Request(CommonVars.getCurrUser()), billNo3);
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("overprint", new Boolean(true));
				parameters.put("isExport", true);
				parameters.put("impexpno", impexpno3);
				parameters.put("name", name);
				parameters.put("trafficUnit", trafficUnit);
				parameters.put("projectType", String.valueOf(projectType));
				parameters.put("complexOrBoxNo", Boolean.valueOf(isComplex));// 是否打开箱号还是商品编码
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 打印报关单集装箱附页
	 */
	public void doInOutPrint20() {
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		List contianList = baseEncAction.findContainerByCustomsDeclaration(
				new Request(CommonVars.getCurrUser()), customsDeclaration);
		List datalist = CustomsDeclarationSubReportDataSource.getInstance()
				.getTempContainerApply(contianList);
		CustomReportDataSource ds = new CustomReportDataSource(datalist);
		if (noTaoda) {
			try {
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("customsDeclarationCode",
						customsDeclaration.getCustomsDeclarationCode());
				parameters.put("isOverprint", false);
				parameters.put("ccount",
						new Integer(contianList.size()).toString());
				InputStream masterReportStream = CustomsPrinter.class
						.getResourceAsStream("ContainerApply.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("customsDeclarationCode",
						customsDeclaration.getCustomsDeclarationCode());
				parameters.put("ccount",
						new Integer(contianList.size()).toString());
				parameters.put("isOverprint", true);
				InputStream masterReportStream = CustomsPrinter.class
						.getResourceAsStream("ContainerApply.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void doExportInvoice() {// 出口货物发票
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		boolean noTaoda = !(Boolean) prop.get("isTaoda");
		List datalist = commInfoModel.getList();
		CustomReportDataSource ds = new CustomReportDataSource(datalist);
		Map<String, Object> parameters = new HashMap<String, Object>();
		// 经营单位
		parameters.put("tradeName", customsDeclaration.getTradeName());
		// 收货单位
		parameters.put("machName",
				customsDeclaration.getCustomer() == null ? ""
						: customsDeclaration.getCustomer().getName());
		// 手册编号
		parameters.put("emsHeadH2k", customsDeclaration.getEmsHeadH2k());
		// 申报日期
		parameters.put("declarationDate", CommonUtils.getDate(
				customsDeclaration.getDeclarationDate(), "yyyy-MM-dd"));
		// 抵运国
		parameters.put("countryOfLoadingOrUnloading", (customsDeclaration
				.getCountryOfLoadingOrUnloading() == null ? ""
				: customsDeclaration.getCountryOfLoadingOrUnloading()
						.getCountryEnname()));
		// 币值
		parameters.put("currency", customsDeclaration.getCurrency()
				.getCurrSymb());
		// 合计数量
		Double totalNum = 0.0;
		// 合计金额
		Double totalMoney = 0.0;
		// 合计箱数
		Double totalBoxNum = 0.0;
		// 合计净重
		Double totalNetWeight = 0.0;
		// 合计毛重
		Double totalGrossWeight = 0.0;
		BaseCustomsDeclarationCommInfo comm = null;
		for (int i = 0; i < datalist.size(); i++) {
			comm = (BaseCustomsDeclarationCommInfo) datalist.get(i);
			if (comm.getCommAmount() != null) {
				totalNum = totalNum + comm.getCommAmount();
			}
			if (comm.getCommTotalPrice() != null) {
				totalMoney = totalMoney + comm.getCommTotalPrice();
			}
			if (comm.getPieces() != null) {
				totalBoxNum = totalBoxNum + comm.getPieces();
			}
			if (comm.getNetWeight() != null) {
				totalNetWeight = totalNetWeight + comm.getNetWeight();
			}
			if (comm.getGrossWeight() != null) {
				totalGrossWeight = totalGrossWeight + comm.getGrossWeight();
			}

		}
		parameters
				.put("totalNum", CommonUtils.formatDoubleByDigit(totalNum, 2));
		parameters.put("totalMoney",
				CommonUtils.formatDoubleByDigit(totalMoney, 2));
		parameters.put("totalBoxNum",
				CommonUtils.formatDoubleByDigit(totalBoxNum, 2));
		parameters.put("totalNetWeight",
				CommonUtils.formatDoubleByDigit(totalNetWeight, 2));
		parameters.put("totalGrossWeight",
				CommonUtils.formatDoubleByDigit(totalGrossWeight, 2));
		if (noTaoda) {
			try {
				parameters.put("isOverprint", false);
				InputStream masterReportStream = CustomsPrinter.class
						.getResourceAsStream("/com/bestway/bcus/client/enc/report/ExportProductInvoiceReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				parameters.put("isOverprint", true);
				InputStream masterReportStream = CustomsPrinter.class
						.getResourceAsStream("/com/bestway/bcus/client/enc/report/ExportProductInvoiceReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void doInMIBcs() {// 装箱单(bcs)
		CustomReportDataSource ds = (CustomReportDataSource) prop
				.get("CustomReportDataSource");
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		JTableListModel commInfoModel = (JTableListModel) prop
				.get("commInfoModel");
		List<BaseCustomsDeclarationCommInfo> list = commInfoModel.getList();
		CustomsDeclarationSubReportDataSource.setSubReportData(list);
		boolean noTaoda = !(Boolean) prop.get("isTaoda");

		try {
			InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
					.getResourceAsStream("report/BCSExportCommodityEncasementReport.jasper");
			InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
					.getResourceAsStream("report/BCSExportCommodityEncasementSubReport.jasper");
			JasperReport commInfoReport = (JasperReport) JRLoader
					.loadObject(commInfoReportStream);
			Company company = (Company) CommonVars.getCurrUser().getCompany(); // 取授权公司设置中【加工单位编号】
			// 【加工单位编号】
			String companyNo = "";
			// 外商公司
			String outTradeCo = "";
			// 币制
			String curr = "";
			if (company != null) {
				companyNo = company.getCode();
				outTradeCo = company.getOutTradeCo();
			}
			String billOfLading = customsDeclaration.getBillOfLading();// 提运单号
			String name;// 司机姓名
			String trafficUnit;// 运输单位
			// 计算总价 SUM总值栏位
			Double total = 0.0;
			Double tmp = null;
			for (int i = 0; list != null && i < list.size(); i++) {
				tmp = list.get(i).getCommTotalPrice();
				if (tmp != null) {
					total += tmp;
				}
			}
			for (int i = 0; list != null && i < 1; i++) {
				Curr currOld = list.get(i).getBaseCustomsDeclaration()
						.getCurrency();
				if (currOld != null) {
					curr = currOld.getCurrSymb();
				}
			}
			if (billOfLading == null) {
				name = "";
				trafficUnit = "";

			} else {
				MotorCode motor = materialManageAction.findMotorCodeByCode(
						new Request(CommonVars.getCurrUser()), billOfLading);
				if (motor == null) {
					name = "";
					trafficUnit = "";
				} else {
					name = motor.getName() == null ? "" : motor.getName();// 司机姓名
					trafficUnit = motor.getTrafficUnit() == null ? "" : motor
							.getTrafficUnit();// 运输单位
				}
			}
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("commInfoReport", commInfoReport);
			parameters.put("overprint", !noTaoda);
			parameters.put("name", name);
			parameters.put("trafficUnit", trafficUnit);
			parameters.put("companyNo", companyNo);
			parameters.put("total", CommonUtils.formatDoubleByDigit(total, 2));
			parameters.put("outTradeCo", outTradeCo);
			parameters.put("curr", curr);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	private String cutString(String str) {
		for (int i = str.length(); i > 0; i--) {
			if (str.substring(i - 1, i).equals("0")) {
				str = str.substring(0, i - 1);
			} else if (str.substring(i - 1, i).equals(".")) {
				str = str.substring(0, i - 1);
				break;
			} else {
				break;
			}
		}
		return str;
	}
}
