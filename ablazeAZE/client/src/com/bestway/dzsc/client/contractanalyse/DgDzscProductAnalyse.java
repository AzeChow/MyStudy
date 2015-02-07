/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.contractanalyse;

import java.awt.Component;
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
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.contractanalyse.entity.CommInfoImpExp;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.dzsc.contractanalyse.action.DzscAnalyseAction;
import com.bestway.dzsc.message.action.DzscMessageAction;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscProductAnalyse extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JTableListModel tableModel = null;

	private DzscAnalyseAction contractAnalyseAction = null;

	private DzscParameterSet dzscParameterSet = null;

	private DzscMessageAction dzscMessageAction = null;

	/**
	 * This is the default constructor
	 */
	public DgDzscProductAnalyse() {
		super();
		initialize();
		contractAnalyseAction = (DzscAnalyseAction) CommonVars
				.getApplicationContext().getBean("dzscAnalyseAction");
		dzscMessageAction = (DzscMessageAction) CommonVars
				.getApplicationContext().getBean("dzscMessageAction");
		dzscParameterSet = dzscMessageAction.findDzscMessageDirSet(new Request(
				CommonVars.getCurrUser()));
	}

	public void setVisible(boolean b) {
		if (b) {
			//查询时间太久屏蔽掉自动查询功能
//			List list = this.contractAnalyseAction.findCommInfoImpExpAnalyse(
//					new Request(CommonVars.getCurrUser()), false, null, null,
//					null, null, null, null);
			List list=new ArrayList();

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
		this.setTitle("成品分析");
		this.setSize(750, 510);
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
					DgDzscCommInfoQueryCondition dg = new DgDzscCommInfoQueryCondition();
					dg.setImport(false);
					dg.setVisible(true);
					List list = dg.getLsResult();
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
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("打印");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getList().size() <= 0) {
						JOptionPane.showMessageDialog(
								DgDzscProductAnalyse.this, "没有数据可以打印", "提示",
								JOptionPane.OK_OPTION);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = DgDzscMaterielAnalyse.class
							.getResourceAsStream("report/CommodityAnalyse.jasper");
					// SimpleDateFormat dateFormat = new SimpleDateFormat(
					// "yyyy-MM-dd");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("reportTitle", "出口成品流水账");
					parameters.put("companyName", CommonVars.getCurrUser()
							.getCompany().getName());
					JasperPrint jasperPrint = null;
					try {
						jasperPrint = JasperFillManager.fillReport(
								reportStream, parameters, ds);
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
//		for(int i=0;i<list.size();i++){
//			CommInfoImpExp info= (CommInfoImpExp) list.get(i);
//			Integer value=info.getImpExpType();
//			
//			if (value != null) {
//				switch (Integer.parseInt(value.toString())) {
//				case ImpExpType.DIRECT_IMPORT:
//					info.setImpExpTypeString("料件进口");
//					break;
//				case ImpExpType.TRANSFER_FACTORY_IMPORT:
//					info.setImpExpTypeString("料件转厂");
//					break;
//				case ImpExpType.BACK_FACTORY_REWORK:
//					info.setImpExpTypeString("退厂返工");
//					break;
//				case ImpExpType.GENERAL_TRADE_IMPORT:
//					info.setImpExpTypeString( "一般贸易进口");
//					break;
//				case ImpExpType.DIRECT_EXPORT:
//					info.setImpExpTypeString("成品出口");
//					break;
//				case ImpExpType.TRANSFER_FACTORY_EXPORT:
//					info.setImpExpTypeString( "转厂出口");
//					break;
//				case ImpExpType.BACK_MATERIEL_EXPORT:
//					info.setImpExpTypeString( "退料出口");
//					break;
//				case ImpExpType.REWORK_EXPORT:
//					info.setImpExpTypeString("返工复出");
//					break;
//				case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
//					info.setImpExpTypeString("边角料退港");
//					break;
//				case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
//					info.setImpExpTypeString( "边角料内销");
//					break;
//				case ImpExpType.GENERAL_TRADE_EXPORT:
//					info.setImpExpTypeString( "一般贸易出口");
//					break;
//				case ImpExpType.REMAIN_FORWARD_EXPORT:
//					info.setImpExpTypeString("余料结转(出口)");
//					break;
//				case ImpExpType.REMAIN_FORWARD_IMPORT:
//					info.setImpExpTypeString("余料结转(进口)");
//					break;
//					}
//				}
//		}
//		
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("品名", "commName", 100));
						list.add(addColumn("规格", "commSpec", 100));
						list.add(addColumn("合同号",
								"baseCustomsDeclaration.contract", 100));
						list.add(addColumn("进出口类型",
								"baseCustomsDeclaration.impExpType", 100));
						list.add(addColumn("报关日期",
								"baseCustomsDeclaration.declarationDate", 100));
						list.add(addColumn("数量", "commAmount", 100));
						list.add(addColumn("单位", "unit.name", 50));
						list.add(addColumn("价值", "commTotalPrice", 100));
						list
								.add(addColumn(
										"报关单号",
										"baseCustomsDeclaration.customsDeclarationCode",
										100));
						list.add(addColumn("客户名称",
								"baseCustomsDeclaration.customer.name", 100));
						list.add(addColumn("运输工具名称",
								"baseCustomsDeclaration.conveyance", 100));
						list.add(addColumn("单位净重", "netWeight", 100));
						list.add(addColumn("第二数量", "secondAmount", 100));
						list
								.add(addColumn("第二单位", "secondLegalUnit.name",
										100));
						list.add(addColumn("成品序号", "commSerialNo", 100));
						list.add(addColumn("包装", "baseCustomsDeclaration.wrapType.name", 100));
						list.add(addColumn("毛重", "grossWeight", 100));
						list.add(addColumn("净重", "netWeight", 100));
						list.add(addColumn("手册通关备案补充说明", "note", 120));
						return list;
					}
				});

		// 显示小数位,默认2位
		Integer decimalLength = 2;
		if (dzscParameterSet != null
				&& dzscParameterSet.getReportDecimalLength() != null)
			decimalLength = dzscParameterSet.getReportDecimalLength();
		tableModel.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
//		List<JTableListColumn> cm = tableModel.getColumns();
//		cm.get(7).setFractionCount(decimalLength);
//		cm.get(9).setFractionCount(decimalLength);
//		cm.get(13).setFractionCount(decimalLength);
//		cm.get(14).setFractionCount(decimalLength);
//		cm.get(18).setFractionCount(decimalLength);
//		cm.get(19).setFractionCount(decimalLength);


	}
}
