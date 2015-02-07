/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractanalyse;

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

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contractanalyse.action.ContractAnalyseAction;
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
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgMaterielAnalyse extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JTableListModel tableModel = null;

	private ContractAnalyseAction contractAnalyseAction = null;

	private BcsParameterSet parameterSet = null;

	private ContractAction contractAction = null;

	/**
	 * This is the default constructor
	 */
	public DgMaterielAnalyse() {
		super();
		initialize();
		contractAnalyseAction = (ContractAnalyseAction) CommonVars
				.getApplicationContext().getBean("contractAnalyseAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		parameterSet = contractAction.findBcsParameterSet(new Request(
				CommonVars.getCurrUser(), true));
	}

	public void setVisible(boolean b) {
		if (b) {
			// List list = this.contractAnalyseAction.findCommInfoImpExpAnalyse(
			// new Request(CommonVars.getCurrUser()), true, null, null,
			// null, null, null, null);
			List list = new ArrayList();
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
		this.setTitle("料件分析");
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
					DgCommInfoQueryCondition dg = new DgCommInfoQueryCondition();
					dg.setImport(true);
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
						JOptionPane.showMessageDialog(DgMaterielAnalyse.this,
								"没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = DgMaterielAnalyse.class
							.getResourceAsStream("report/CommodityAnalyse.jasper");
					// SimpleDateFormat dateFormat = new SimpleDateFormat(
					// "yyyy-MM-dd");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("reportTitle", "料件进口流水帐");
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
		for(int i=0;i<list.size();i++){
			CommInfoImpExp info= (CommInfoImpExp) list.get(i);
			Integer value=info.getImpExpType();
			
			if (value != null) {
				switch (Integer.parseInt(value.toString())) {
				case ImpExpType.DIRECT_IMPORT:
					info.setImpExpTypeString("料件进口");
					break;
				case ImpExpType.TRANSFER_FACTORY_IMPORT:
					info.setImpExpTypeString("料件转厂");
					break;
				case ImpExpType.BACK_FACTORY_REWORK:
					info.setImpExpTypeString("退厂返工");
					break;
				case ImpExpType.GENERAL_TRADE_IMPORT:
					info.setImpExpTypeString( "一般贸易进口");
					break;
				case ImpExpType.DIRECT_EXPORT:
					info.setImpExpTypeString("成品出口");
					break;
				case ImpExpType.TRANSFER_FACTORY_EXPORT:
					info.setImpExpTypeString( "转厂出口");
					break;
				case ImpExpType.BACK_MATERIEL_EXPORT:
					info.setImpExpTypeString( "退料出口");
					break;
				case ImpExpType.REWORK_EXPORT:
					info.setImpExpTypeString("返工复出");
					break;
				case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
					info.setImpExpTypeString("边角料退港");
					break;
				case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
					info.setImpExpTypeString( "边角料内销");
					break;
				case ImpExpType.GENERAL_TRADE_EXPORT:
					info.setImpExpTypeString( "一般贸易出口");
					break;
				case ImpExpType.REMAIN_FORWARD_EXPORT:
					info.setImpExpTypeString("余料结转(出口)");
					break;
				case ImpExpType.REMAIN_FORWARD_IMPORT:
					info.setImpExpTypeString("余料结转(进口)");
					break;
				}
			}
		}
		
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("品名", "commName", 100));
						list.add(addColumn("规格", "commSpec", 100));
						list.add(addColumn("合同号", "contract", 100));
						list.add(addColumn("进出口类型", "impExpTypeString", 100));
						list.add(addColumn("报关日期", "declarationDate", 100));
						list.add(addColumn("数量", "commAmount", 100));
						list.add(addColumn("单位", "unitName.name", 50));
						list.add(addColumn("价值", "commTotalPrice", 100));
						list.add(addColumn("币制", "currency", 100));
						list.add(addColumn("合同金额", "currencyCommTotalPrice", 100));
						list.add(addColumn("报关单号", "customsDeclarationCode",
								100));
						list.add(addColumn("客户名称", "customerName.name", 100));
						list.add(addColumn("运输工具名称", "conveyance", 100));
						list.add(addColumn("单位净重", "unitWeight", 100));
						list.add(addColumn("第一数量", "firstAmount", 100));
						list
						.add(addColumn("第一单位", "firstLegalUnit.name",
								100));
						list.add(addColumn("第二数量", "secondAmount", 100));
						list
								.add(addColumn("第二单位", "secondLegalUnit.name",
										100));
						list.add(addColumn("备案序号", "commSerialNo", 100));
						list.add(addColumn("包装", "wrapType.name", 100));
						list.add(addColumn("毛重", "grossWeight", 100));
						list.add(addColumn("净重", "netWeight", 100));

						return list;
					}
				});

		// 截取小数位
		Integer decimalLength = 2;
		if (parameterSet != null
				&& parameterSet.getReportDecimalLength() != null)
			decimalLength = parameterSet.getReportDecimalLength();
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
