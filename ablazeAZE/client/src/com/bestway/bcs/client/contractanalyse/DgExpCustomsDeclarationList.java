/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractanalyse;

import java.awt.BorderLayout;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.TableColumn;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgExpCustomsDeclarationList extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton btnPrint = null;

	private JButton btnExit = null;

	// private Contract contract = null;

	private JTableListModel tableModel = null;

	private JButton btnQuery = null;

	private ButtonGroup buttonGroup = null;

	private Integer impExpFlag = null;

	private BcsParameterSet parameterSet = null;

	private ContractAction contractAction = null;

	public Integer getImpExpFlag() {
		return impExpFlag;
	}

	public void setImpExpFlag(Integer impExpFlag) {
		this.impExpFlag = impExpFlag;
	}

	/**
	 * This is the default constructor
	 */
	public DgExpCustomsDeclarationList() {
		super();
		initialize();
		getButtonGroup();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		parameterSet = contractAction.findBcsParameterSet(new Request(
				CommonVars.getCurrUser(), true));
	}

	public void setVisible(boolean b) {
		if (b) {
			if (impExpFlag == ImpExpFlag.IMPORT) {
				this.setTitle("进口报关列表");
			} else {
				this.setTitle("出口报关列表");
			}
			initTable(new ArrayList());
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
		this.setTitle("进口报关单列表");
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
			jContentPane.setLayout(new BorderLayout());
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
			jToolBar.add(getBtnQuery());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnExit());
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
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null || tableModel.getList().size() <= 0) {
						JOptionPane.showMessageDialog(
								DgExpCustomsDeclarationList.this, "没有数据可打印",
								"提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = DgExpCustomsDeclarationList.class
							.getResourceAsStream("report/ImpExpCustomsDeclarationList.jasper");
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					Map<String, Object> parameters = new HashMap<String, Object>();
					// parameters.put("isImport",new Boolean(true));
					// parameters.put("contractNo",contract.getExpContractNo());
					// parameters.put("emsNo",contract.getEmsNo());
					// parameters.put("beginDate",contract.getBeginDate()==null?"":dateFormat.format(contract.getBeginDate()));
					// parameters.put("effectiveDate",contract.getEndDate()==null?"":dateFormat.format(contract.getEndDate()));
					parameters.put("printDate", dateFormat.format(new Date()));
					parameters.put("companyName", CommonVars.getCurrUser()
							.getCompany().getName());
					if (impExpFlag == ImpExpFlag.IMPORT) {
						parameters.put("reportTitle", "进口报关列表");
					} else {
						parameters.put("reportTitle", "出口报关列表");
					}
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
		return btnPrint;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("合同号", "contractNo", 100));
						list.add(addColumn("手册号", "emsNo", 100));
						list
								.add(addColumn("日期", "customsDeclarationDate",
										100));
						list.add(addColumn("报关单号", "customsDeclarationCode",
								100));
						list.add(addColumn("核销单号", "fecavBillCode", 100));
						list.add(addColumn("集装箱号", "containerCode", 100));
						list.add(addColumn("币制", "curr.name", 100));
						list.add(addColumn("金额", "totalMoney", 100));
						list.add(addColumn("件数", "commodityNum", 100));
						list.add(addColumn("毛重", "grossWeight", 100));
						list.add(addColumn("净重", "netWeight", 100));
						list.add(addColumn("页数", "pageCount", 100));
						list.add(addColumn("包装种类", "wrapType.name", 100));
						list.add(addColumn("贸易方式", "tradeMode.name", 100));
						list.add(addColumn("客户", "customer.name", 150));
						list.add(addColumn("是否生效", "iseffective", 100));
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
//		List<JTableListColumn> cm = tableModel.getColumns();
//		cm.get(7).setFractionCount(decimalLength);
//		cm.get(8).setFractionCount(decimalLength);
//		cm.get(9).setFractionCount(decimalLength);
//		cm.get(10).setFractionCount(decimalLength);
	
		TableColumn column = jTable.getColumnModel().getColumn(16);
		column.setCellRenderer(new TableCheckBoxRender());
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCustomsBillQueryCondition dg = new DgCustomsBillQueryCondition();
					dg.setImpExpFlag(impExpFlag);
					dg.setVisible(true);
					if (dg.isOk()) {
						List list = dg.getLsResult();
						initTable(list);
					}
				}
			});
		}
		return btnQuery;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			// buttonGroup.add(this.getRbAll());
			// buttonGroup.add(this.getRbYes());
			// buttonGroup.add(this.getRbNo());
		}
		return buttonGroup;
	}
} // @jve:decl-index=0:visual-constraint="66,-1"
