/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.cas.entity.BillTemp;
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
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class DgImpUseRecord extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JTableListModel tableModel = null;

	private EncAction encAction = null;

	private List list = null; // @jve:decl-index=0:

	private ManualDeclareAction manualDecleareAction = null;

	/**
	 * This is the default constructor
	 */
	public DgImpUseRecord() {
		super();
		initialize();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			/*
			 * list = this.encAction.findImpExpCommInfoUse(new Request(
			 * CommonVars.getCurrUser()), true,null,null,null,null,null);
			 */
			list = new Vector();
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
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("进口料件使用情况表");
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
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {
						BillTemp b = (BillTemp) tableModel.getCurrentRow();
						DgLookMaterielAmount dg = new DgLookMaterielAmount();
						dg.setTemp(b);
						dg.setVisible(true);
					}
				}
			});
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

					DgImpExpUseQueryCondition dg = new DgImpExpUseQueryCondition();
					dg.setImport(true);
					dg.setImg(true);
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
	 * 不分事业部 进口料件情况统计表
	 * 
	 * @param seqNum
	 *            料件序号
	 * @param customer
	 *            客户供应商
	 * @param IEType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isDeclaration
	 *            是否为申报日期
	 * @param isdept
	 *            是否区分事业部	 
	 * @param isEffect
	 *            是否生效
	 * @param isCountStoreNum
	 *            是否统计仓库里的数量
	 * @param deptCode
	 *            部门ID号
	 * @param isDept
	 *            是否区分事业部       
	 * @return 有效期内符合条件的进口料件情况
	 */
	public void showData(Integer seqNum, String customer, String impExpType, Date beginDate,
			Date endDate, boolean isDeclaration, int isEffect,
			boolean isCountStoreNum, String deptCode, boolean isDept) {
		
		
		if (isDeclaration) { // 申报日期 (isDeclaration =
			// true)
			list = encAction.findImpExpCommInfoUseNoDept(new Request(
					CommonVars.getCurrUser()), true, seqNum, customer,
					impExpType, beginDate, endDate, true, isEffect, isCountStoreNum// 是否统计仓库里的数量
					);
		} else {// 结关日期
			list = encAction.findImpExpCommInfoUseNoDept(new Request(
					CommonVars.getCurrUser()), true, seqNum, customer,
					impExpType, beginDate, endDate, false, isEffect, isCountStoreNum// 是否统计仓库里的数量
					);
		}
		
//		if (isDeclaration) { // 申报日期 (isDeclaration =
//			// true)
//			list = encAction.findImpExpCommInfoUseForDept(new Request(
//					CommonVars.getCurrUser()), true, seqNum, customer, impExpType,
//					beginDate, endDate, false, deptCode, isEffect
//					);
//		} else {// 结关日期
//			list = encAction.findImpExpCommInfoUseForDept(new Request(
//					CommonVars.getCurrUser()), true, seqNum, customer,
//					impExpType, beginDate, endDate, false, isEffect// 是否统计仓库里的数量
//					);
//		}
		if (list != null) {
			initTable(list);
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
			jButton1.setText("打印");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null) {
						JOptionPane.showMessageDialog(DgImpUseRecord.this,
								"没有可打印的记录！", "提示", 2);
						return;
					}
					CommonDataSource imgExgDS = new CommonDataSource(list);

					List company = new Vector(); // 只有一条记录
					company.add(CommonVars.getCurrUser().getCompany());
					CommonDataSource companyDS = new CommonDataSource(company);

					InputStream masterReportStream = DgImpUseRecord.class
							.getResourceAsStream("report/ImpUseRecord.jasper");
					InputStream detailReportStream = DgImpUseRecord.class
							.getResourceAsStream("report/ImpUseRecordSub.jasper");
					try {
						JasperReport detailReport = (JasperReport) JRLoader
								.loadObject(detailReportStream);
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("printDate", CommonVars.nowToDate());
						parameters.put("reportTitle", "进口料件情况统计报表");
						parameters.put("companyName", CommonVars.getCurrUser()
								.getCompany().getName());
						parameters.put("imgExgDS", imgExgDS);// 子数据源
						parameters.put("detailReport", detailReport);// 子报表
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
				list.add(addColumn("备案序号", "bill1", 60, Integer.class));
				list.add(addColumn("商品编号", "bill2", 80));
				list.add(addColumn("品名", "bill3", 120));
				list.add(addColumn("规格", "bill9", 120));
				list.add(addColumn("单位", "bill4", 60));
				list.add(addColumn("事业部", "bill5", 100));
				list.add(addColumn("1.本期总进口量=2+12-5", "billSum2", 100));
				list.add(addColumn("事业部小计", "billSum14", 100));
				list.add(addColumn("2.本期大单进口量=3+4", "billSum3", 100));
				list.add(addColumn("3.直接进口量", "billSum4", 80));
				list.add(addColumn("4.转厂进口量", "billSum5", 80));
				list.add(addColumn("5.退料出口量", "billSum6", 80));
				list.add(addColumn("6.总成品使用量", "billSum7", 80));
				list.add(addColumn("7.总边角料", "billSum8", 60));
				list.add(addColumn("8.本期料件内销", "billSum18", 80));
				list.add(addColumn("9.本期余料情况=1-13-8", "billSum9", 120));
				list.add(addColumn("10.仓库数量", "billSum11", 80));
				list.add(addColumn("11.差异值=9-10", "billSum12", 100));
				list.add(addColumn("12.余料结转(进口)", "billSum1", 100));
				list.add(addColumn("13.本期成品使用量", "billSum13", 120));
				list.add(addColumn("14.本期边角料", "billSum14", 120));
				list.add(addColumn("美元余料金额", "billSum10", 120));
				list.add(addColumn("本期直接出口使用量", "billSum16", 120));
				list.add(addColumn("本期转厂出口使用量", "billSum17", 120));
				list.add(addColumn("本期返工复出使用量", "billSum20", 120));
				list.add(addColumn("本期退厂返工使用量", "billSum21", 120));
				return list;
			}
		};
		JTableListModel.dataBind(jTable, list, tableModelAdapter,
				ListSelectionModel.SINGLE_SELECTION);
		tableModel = (JTableListModel) jTable.getModel();
		List<JTableListColumn> ckist = tableModel.getColumns();
		for (int i = 0; i < ckist.size(); i++) {
			JTableListColumn cm = (JTableListColumn) ckist.get(i);
			cm.setFractionCount(3);
		}

		String reportDecimalLength = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser(), true),
				BcusParameter.ReportDecimalLength);
		Integer decimalLength = 2;
		if (reportDecimalLength != null)
			decimalLength = Integer.valueOf(reportDecimalLength);
		tableModel.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
	}
}
