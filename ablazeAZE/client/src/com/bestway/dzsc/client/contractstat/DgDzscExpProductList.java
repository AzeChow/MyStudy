/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.contractstat;

import java.awt.Component;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.dzsc.client.contractanalyse.JDzscContractList;
import com.bestway.dzsc.contractstat.action.DzscStatAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.message.action.DzscMessageAction;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscExpProductList extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private DzscEmsPorHead contract = null;

	private JTableListModel tableModel = null;

	private DzscStatAction contractStatAction = null;

	private DzscParameterSet dzscParameterSet = null;

	private DzscMessageAction dzscMessageAction = null;

	/**
	 * @return Returns the contract.
	 */
	public DzscEmsPorHead getContract() {
		return contract;
	}

	/**
	 * @param contract
	 *            The contract to set.
	 */
	public void setContract(DzscEmsPorHead contract) {
		this.contract = contract;
	}

	/**
	 * This is the default constructor
	 */
	public DgDzscExpProductList() {
		super();
		initialize();
		contractStatAction = (DzscStatAction) CommonVars
				.getApplicationContext().getBean("dzscStatAction");
		dzscMessageAction = (DzscMessageAction) CommonVars
				.getApplicationContext().getBean("dzscMessageAction");
		dzscParameterSet = dzscMessageAction.findDzscMessageDirSet(new Request(
				CommonVars.getCurrUser()));
	}

	public void setVisible(boolean b) {
		if (b) {
			List list = new ArrayList();
			if (this.contract != null) {
				list = this.contractStatAction.findMaterialImportList(
						new Request(CommonVars.getCurrUser()), false, null,
						null, null, null, null, this.contract);
			}
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
		this.setTitle("出口成品报关登记表");
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
					if (contract == null) {
						return;
					}
					DgDzscStatQueryCondition dg = new DgDzscStatQueryCondition();
					dg.setContract(contract);
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
					if (tableModel == null) {
						JOptionPane.showMessageDialog(
								DgDzscExpProductList.this, "没有数据可以打印", "提示",
								JOptionPane.OK_OPTION);
						return;
					}
					if (contract == null) {
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = JDzscContractList.class
							.getResourceAsStream("report/ImpExpCommodityStatus.jasper");
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("isImport", new Boolean(false));
					parameters.put("contractNo", contract.getIeContractNo());
					parameters.put("emsNo", contract.getEmsNo());
					parameters.put("beginDate",
							contract.getBeginDate() == null ? "" : dateFormat
									.format(contract.getBeginDate()));
					parameters.put("effectiveDate",
							contract.getEndDate() == null ? "" : dateFormat
									.format(contract.getEndDate()));
					parameters.put("companyName", CommonVars.getCurrUser()
							.getCompany().getName());
					parameters.put("reportTitle", "出口成品报关登记表");
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
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("报关日期",
								"baseCustomsDeclaration.declarationDate", 100));//1
						list.add(addColumn("运输工具名称",
								"baseCustomsDeclaration.conveyance", 100));//2
						list.add(addColumn("合同序号", "commSerialNo", 100));//3
						list
						.add(addColumn(
								"报关单号",
								"baseCustomsDeclaration.customsDeclarationCode",
								100));//4
						list.add(addColumn("报关单流水号",
								"baseCustomsDeclaration.serialNumber", 100));//5
						list.add(addColumn("商品项号", "serialNumber", 100));//6
						list.add(addColumn("商品编码", "complex.code", 100));//7
						list.add(addColumn("品名", "commName", 100));//8
						list.add(addColumn("规格", "commSpec", 100));//9
						list.add(addColumn("单位", "unit.name", 50));//10
						list.add(addColumn("数量", "commAmount", 100));//11
						list.add(addColumn("价值", "commTotalPrice", 100));//12
						list.add(addColumn("数量累计", "commAddUpAmount", 100));//13
						list.add(addColumn("最终目的国", "country.name", 100));//14
						
						list.add(addColumn("进出口类型",
								"baseCustomsDeclaration.impExpType", 100));//15
						list.add(addColumn("贸易方式",
								"baseCustomsDeclaration.tradeMode.name", 100));//16
						list.add(addColumn("客户名称",
								"baseCustomsDeclaration.customer.name", 100));//17
						list.add(addColumn("法定数量", "firstAmount", 100));//18
						list.add(addColumn("法定单位", "legalUnit.name", 100));//19
						list.add(addColumn("总重量", "grossWeight", 100));//20
						
						list.add(addColumn("报关单单价", "commUnitPrice", 100));//21
						list.add(addColumn("币制", "baseCustomsDeclaration.currency.currSymb", 50));//22
						list.add(addColumn("单位毛重", "grossWeight", 100));//23
						list.add(addColumn("单位净重", "netWeight", 100));//24
						list.add(addColumn("车牌号","baseCustomsDeclaration.billOfLading", 100));
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
//		cm.get(8).setFractionCount(decimalLength);
//		cm.get(9).setFractionCount(decimalLength);
//		cm.get(10).setFractionCount(decimalLength);
//		cm.get(15).setFractionCount(decimalLength);
//		cm.get(17).setFractionCount(decimalLength);
//		cm.get(19).setFractionCount(decimalLength);
//		cm.get(20).setFractionCount(decimalLength);
//		cm.get(21).setFractionCount(decimalLength);
		
		CommonVars.castMultiplicationValue(jTable,12,11,21,decimalLength);

		jTable.getColumnModel().getColumn(15).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = CommonVars
									.getImpExpTypeName(value.toString());
						}
						this.setText(str);
						return this;
					}
				});
	}

}
