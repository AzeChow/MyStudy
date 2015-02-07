/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcs.contractstat.entity.ImpExpCustomsDeclarationCommInfo;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * 
 * 
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgspecialCustomsRecord extends JInternalFrameBase {

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

	/**
	 * This is the default constructor
	 */
	public DgspecialCustomsRecord() {
		super();
		initialize();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			List list = null;
			/*
			 * list = this.encAction.findImpExpCommInfoList(new Request(
			 * CommonVars.getCurrUser()), false,null,null,null,null,null);
			 */
			initTable(list);
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("特殊报关商品登记表");
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

					dg.setSpecial(true);

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

					if (list == null || list.size() == 0 || tableModel == null) {

						JOptionPane.showMessageDialog(
								DgspecialCustomsRecord.this, "没有可打印的记录！", "提示",
								2);

						return;
					}

					CommonDataSource imgExgDS = new CommonDataSource(list);

					List company = new Vector(); // 只有一条记录

					company.add(CommonVars.getCurrUser().getCompany());

					CommonDataSource companyDS = new CommonDataSource(company);

					// 总价
					Double sumPrice = 0.0;

					// 总价值
					Double sumPriceFg = 0.0;

					// 转换后的 String 总价
					String sumPrice1;

					// 转换后的 String 总价值
					String sumPriceFg1;

					for (int i = 0; i < list.size(); i++) {

						sumPrice = sumPrice
								+ ((ImpExpCustomsDeclarationCommInfo) list
										.get(i)).getSumPrice();

						sumPriceFg = sumPriceFg
								+ ((ImpExpCustomsDeclarationCommInfo) list
										.get(i)).getSumPriceFg();

					}

					Integer bgdNum = ((ImpExpCustomsDeclarationCommInfo) list
							.get(list.size() - 1)).getBgdNum();

					sumPrice1 = CommonVars.formatDoubleToString(sumPrice);

					sumPriceFg1 = CommonVars.formatDoubleToString(sumPriceFg);

					InputStream masterReportStream = DgspecialCustomsRecord.class
							.getResourceAsStream("report/SpecialCustomsRecord.jasper");

					InputStream detailReportStream = DgspecialCustomsRecord.class
							.getResourceAsStream("report/SpecialCustomsRecordSub.jasper");
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
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("报关日期",
								"baseCustomsDeclaration.declarationDate", 80));
						list.add(addColumn(
								"报送海关",
								"baseCustomsDeclaration.declarationCustoms.name",
								120));
						list.add(addColumn("进出口日期",
								"baseCustomsDeclaration.impExpDate", 80));
						list.add(addColumn("进出口岸",
								"baseCustomsDeclaration.customs.name", 80));
						list.add(addColumn("车次编号",
								"baseCustomsDeclaration.extendField1", 80));
						list.add(addColumn("进出口编号",
								"baseCustomsDeclaration.extendField2", 80));
						list.add(addColumn("运输工具名称",
								"baseCustomsDeclaration.conveyance", 120));// 5
						list.add(addColumn("帐册序号", "commSerialNo", 60));
						list.add(addColumn(
								"报关单号",
								"baseCustomsDeclaration.customsDeclarationCode",
								150));
						list.add(addColumn("报关单流水号",
								"baseCustomsDeclaration.serialNumber", 100));
						list.add(addColumn("商品项号", "commSerialNo", 60));
						// list.add(addColumn("备案序号", "commSerialNo", 60));
						list.add(addColumn("商品编码", "complex.code", 100));// 10
						list.add(addColumn("品名", "commName", 150));
						list.add(addColumn("规格", "commSpec", 150));
						list.add(addColumn("单位", "unit.name", 50));
						list.add(addColumn("事业部", "projectDept.name", 100));
						list.add(addColumn("版本号", "version", 50));// 15
						list.add(addColumn("数量", "commAmount", 100));
						list.add(addColumn("净重", "netWeight", 100));
						list.add(addColumn("毛重", "grossWeight", 100));
						list.add(addColumn("价值", "commTotalPrice", 100));
						list.add(addColumn("币制",
								"baseCustomsDeclaration.currency.name", 50));
						list.add(addColumn("美元金额", "dollarTotalPrice", 100));
						list.add(addColumn("数量累计", "commAddUpAmount", 100));// 20
						list.add(addColumn("原产国/最终目的国", "country.name", 100));

						list.add(addColumn("外汇核销单号",
								"baseCustomsDeclaration.authorizeFile", 100));
						list.add(addColumn("进出口类型", "impType", 100));

						list.add(addColumn("贸易方式",
								"baseCustomsDeclaration.tradeMode.name", 100));
						list.add(addColumn("客户名称",
								"baseCustomsDeclaration.customer.name", 100));// 25
						list.add(addColumn("第一法定数量", "firstAmount", 100));
						list.add(addColumn("第一法定单位", "legalUnit.name", 100));
						list.add(addColumn("第二法定数量", "secondAmount", 100));
						list.add(addColumn("第二法定单位", "secondLegalUnit.name",
								100));
						list.add(addColumn("总重量",
								"baseCustomsDeclaration.grossWeight", 100));// 30
						list.add(addColumn("申报单价", "commUnitPrice", 100));
						list.add(addColumn("单位毛重", "grossWeight", 100));
						list.add(addColumn("单位净重", "netWeight", 100));
						list.add(addColumn("车牌号",
								"baseCustomsDeclaration.billOfLading", 100));// 27
						list.add(addColumn("报关单项次", "serialNumber", 90));// 34
						list.add(addColumn(
								"预录入号",
								"baseCustomsDeclaration.preCustomsDeclarationCode",
								100));
						list.add(addColumn("统一编号",
								"baseCustomsDeclaration.unificationCode", 100));// 6
						// hwy 2012-8-27
						list.add(addColumn("发票号码",
								"baseCustomsDeclaration.invoiceCode", 100));
						return list;
					}
				});

		// 截取小数位
		String reportDecimalLength = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser(), true),
				BcusParameter.ReportDecimalLength);

		Integer decimalLength = 2;

		if (reportDecimalLength != null)

			decimalLength = Integer.valueOf(reportDecimalLength);

		List<JTableListColumn> cm = tableModel.getColumns();

		cm.get(16).setFractionCount(decimalLength);
		cm.get(17).setFractionCount(decimalLength);
		cm.get(18).setFractionCount(decimalLength);
		cm.get(19).setFractionCount(decimalLength);
		cm.get(20).setFractionCount(decimalLength);

		cm.get(26).setFractionCount(decimalLength);
		cm.get(28).setFractionCount(decimalLength);

		cm.get(30).setFractionCount(decimalLength);
		cm.get(31).setFractionCount(decimalLength);
		cm.get(32).setFractionCount(decimalLength);
		cm.get(33).setFractionCount(decimalLength);
		CommonVars.castMultiplicationValue(jTable, 17, 16, 31, decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());

	}

}
