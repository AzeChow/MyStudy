/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contractanalyse.action.ContractAnalyseAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JSplitPane;
import java.awt.GridBagLayout;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgExpProductStatus extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private Contract contract = null;

	private JTableListModel tableModel = null;

	private ContractAction contractAction = null;

	private JTableListModel contractTableModel = null;

	private ContractAnalyseAction contractAnalyseAction = null;

	private JTable jTable1 = null;

	private JScrollPane jScrollPane1 = null;

	private JPanel jPanel = null;

	private JLabel lbEmsNo = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	/**
	 * @return Returns the contract.
	 */
	public Contract getContract() {
		return contract;
	}

	/**
	 * @param contract
	 *            The contract to set.
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
	}

	/**
	 * This is the default constructor
	 */
	public DgExpProductStatus() {
		super();
		initialize();
		contractAnalyseAction = (ContractAnalyseAction) CommonVars
				.getApplicationContext().getBean("contractAnalyseAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			List lsContract = contractAction
					.findContractByProcessExe(new Request(CommonVars
							.getCurrUser(), true));
			initContractTable(lsContract);
			List list = new ArrayList();
//			if (this.contract != null) {
//				list = this.contractAnalyseAction.findImpExpCommodityStatus(
//						new Request(CommonVars.getCurrUser()), false, null,
//						null, null, null, null, this.contract);
//			}
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
		this.setTitle("出口成品报关情况表");
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
			jContentPane.add(getJToolBar(), BorderLayout.SOUTH);
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
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
					contract = (Contract) contractTableModel.getCurrentRow();
					if (contract == null) {
						JOptionPane.showMessageDialog(DgExpProductStatus.this,
								"请选择正在执行的合同", "提示", JOptionPane.OK_OPTION);
						return;
					}
					DgAnalyseQueryCondition dg = new DgAnalyseQueryCondition();
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
						return;
					}
					Contract contract = (Contract) contractTableModel
							.getCurrentRow();
					if (contract == null) {
						return;
					}
					if (tableModel.getList().size() <= 0) {
						JOptionPane.showMessageDialog(DgExpProductStatus.this,
								"没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = DgPreCustomsDeclaration.class
							.getResourceAsStream("report/ImpExpCommodityStatus.jasper");
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("isImport", new Boolean(false));
					parameters.put("contractNo", contract.getExpContractNo());
					parameters.put("emsNo", contract.getEmsNo());
					parameters.put("beginDate",
							contract.getBeginDate() == null ? "" : dateFormat
									.format(contract.getBeginDate()));
					parameters.put("effectiveDate",
							contract.getEndDate() == null ? "" : dateFormat
									.format(contract.getEndDate()));
					parameters.put("companyName", CommonVars.getCurrUser()
							.getCompany().getName());
					parameters.put("reportTitle", "出口成品报关情况表");
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
								"baseCustomsDeclaration.declarationDate", 100));
						list.add(addColumn("运输工具名称",
								"baseCustomsDeclaration.conveyance", 100));
						list.add(addColumn("合同序号", "commSerialNo", 100));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("品名", "commName", 100));
						list.add(addColumn("规格", "commSpec", 100));
						list.add(addColumn("单位", "unit.name", 50));
						list.add(addColumn("数量", "commAmount", 100));
						list.add(addColumn("价值", "commTotalPrice", 100));
						list.add(addColumn("数量累计", "commAddUpAmount", 100));
						list
								.add(addColumn(
										"报关单号",
										"baseCustomsDeclaration.customsDeclarationCode",
										100));

						list.add(addColumn("进出口类型",
								"baseCustomsDeclaration.impExpType", 100));
						list.add(addColumn("贸易方式",
								"baseCustomsDeclaration.tradeMode.name", 100));
						list.add(addColumn("客户名称",
								"baseCustomsDeclaration.customer.name", 100));
						list.add(addColumn("法定数量", "firstAmount", 100));
						list.add(addColumn("法定单位", "legalUnit.name", 100));
						
						list.add(addColumn("报关单流水号",
								"baseCustomsDeclaration.serialNumber", 100));
						list.add(addColumn("报关单单价", "commUnitPrice", 100));

						list.add(addColumn("单位毛重", "grossWeight", 100));
						list.add(addColumn("单位净重", "netWeight", 100));
						list.add(addColumn("总重量", "grossWeight", 100));
						return list;
					}
				});

		jTable.getColumnModel().getColumn(12).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							switch (Integer.parseInt(value.toString())) {
							case ImpExpType.DIRECT_IMPORT:
								str = "料件进口";
								break;
							case ImpExpType.TRANSFER_FACTORY_IMPORT:
								str = "料件转厂";
								break;
							case ImpExpType.BACK_FACTORY_REWORK:
								str = "退厂返工";
								break;
							case ImpExpType.GENERAL_TRADE_IMPORT:
								str = "一般贸易进口";
								break;
							case ImpExpType.DIRECT_EXPORT:
								str = "成品出口";
								break;
							case ImpExpType.TRANSFER_FACTORY_EXPORT:
								str = "转厂出口";
								break;
							case ImpExpType.BACK_MATERIEL_EXPORT:
								str = "退料出口";
								break;
							case ImpExpType.REWORK_EXPORT:
								str = "返工复出";
								break;
							case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
								str = "边角料退港";
								break;
							case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
								str = "边角料内销";
								break;
							case ImpExpType.GENERAL_TRADE_EXPORT:
								str = "一般贸易出口";
								break;
							}
						}
						this.setText(str);
						return this;
					}
				});

		// jTable
		// .setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

	}

	/**
	 * 初始化数据Table
	 */
	private void initContractTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		contractTableModel = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("合同号", "expContractNo", 100));
						list.add(addColumn("帐册号 ", "emsNo", 100));
						return list;
					}
				});
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
			jTable1.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) jTable1
									.getModel();
							if (tableModel == null) {
								lbEmsNo.setText("当前合同 ");
								return;
							}
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							if (!lsm.isSelectionEmpty()) {
								List list = new ArrayList();
								if (tableModel.getCurrentRow() != null) {
									Contract exg = ((Contract) tableModel
											.getCurrentRow());
									lbEmsNo.setText("当前合同的手册号："
											+ exg.getEmsNo());
								}
							}
						}
					});
		}
		return jTable1;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			lbEmsNo = new JLabel();
			jPanel.setLayout(new BorderLayout());
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			lbEmsNo.setText("正在执行的合同 ");
			lbEmsNo.setForeground(java.awt.Color.blue);
			jPanel.add(lbEmsNo, java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(600);
			jSplitPane.setDividerSize(5);
			jSplitPane.setLeftComponent(getJScrollPane());
			jSplitPane.setRightComponent(getJPanel1());
		}
		return jSplitPane;
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
			jPanel1.add(getJPanel(), BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel1;
	}
} // @jve:decl-index=0:visual-constraint="86,34"
