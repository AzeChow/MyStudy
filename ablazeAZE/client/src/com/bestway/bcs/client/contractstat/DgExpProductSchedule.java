/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractstat;

import java.awt.BorderLayout;
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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractstat.action.ContractStatAction;
import com.bestway.bcs.contractstat.entity.ImpMaterialStatResult;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Rectangle;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.awt.CardLayout;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgExpProductSchedule extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private Contract contract = null;

	private JTableListModel tableModel = null;

	private ContractStatAction contractStatAction = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JLabel jLabel = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton jButton2 = null;

	private JPanel jPanel = null;

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
	public DgExpProductSchedule() {
		super();
		initialize();
		contractStatAction = (ContractStatAction) CommonVars
				.getApplicationContext().getBean("contractStatAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			if (contract != null) {
				this.cbbBeginDate.setDate(contract.getBeginDate());
			} else {
				this.cbbBeginDate.setDate(null);
			}
			List list = new ArrayList();
			if (this.contract != null) {
				list = this.contractStatAction.findExpProductSchedule(
						new Request(CommonVars.getCurrUser()), this.contract,
						cbbBeginDate.getDate(), cbbEndDate.getDate());
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
		this.setTitle("成品执行进度[总表]");
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
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jLabel.setText("报关日期范围从");
			jLabel.setName("jLabel");
			jLabel1.setText("到");
			jLabel1.setName("jLabel1");
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getJPanel(), BorderLayout.NORTH);
		}
		return jContentPane;
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
	 * 初始化数据Table
	 */
	private void initTable(List list) {

		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("序号", "serialNo", 100, Integer.class));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("品名", "commName", 100));
						list.add(addColumn("规格", "commSpec", 100));
						list.add(addColumn("单位", "unit.name", 50));
						list.add(addColumn("单价", "unitPrice", 100));
						list.add(addColumn("合同定量", "contractAmount", 100));
						list.add(addColumn("总出口量", "expTotalAmont", 100));
						list.add(addColumn("成品出口量", "directExport", 100));
						list.add(addColumn("比例", "scale", 100));
						list.add(addColumn("转厂出口量", "transferFactoryExport",
								100));
						list.add(addColumn("可出口量", "canExportAmount", 100));
						list.add(addColumn("超量", "overAmount", 100));
						list
								.add(addColumn("退厂返工数", "backFactoryRework",
										100));
						list.add(addColumn("返工复出数", "reworkExport", 100));
						list.add(addColumn("加工费单价", "processUnitPrice", 100));
						list.add(addColumn("加工费总价", "processTotalPrice", 100));
						list.add(addColumn("法定单位数量", "legalAmount", 100));
						list.add(addColumn("法定单位", "legalUnit.name", 100));
						list.add(addColumn("单重", "unitWeight", 100));
						list.add(addColumn("单位毛重", "unitGrossWeight", 100));
						list.add(addColumn("单位净重", "unitNetWeight", 100));
						list.add(addColumn("征减免税方式", "levyMode.name", 100));
						return list;
					}
				});
		this.tableModel.setExcelFileName(this.contract.getEmsNo());
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("打印");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getList().size() < 1) {
						JOptionPane.showMessageDialog(
								DgExpProductSchedule.this, "没有数据可以打印", "提示",
								JOptionPane.OK_OPTION);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = DgExpProductSchedule.class
							.getResourceAsStream("report/ExpProductSchedule.jasper");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("contractNo", contract.getImpContractNo());
					parameters.put("emsNo", contract.getEmsNo());
					parameters.put("beginDate",
							cbbBeginDate.getDate() == null ? ""
									: (new SimpleDateFormat("yyyy-MM-dd"))
											.format(cbbBeginDate.getDate()));
					parameters.put("endDate", cbbEndDate.getDate() == null ? ""
							: (new SimpleDateFormat("yyyy-MM-dd"))
									.format(cbbEndDate.getDate()));
					// parameters.put("reportTitle",
					// isMaterial?"进口料件清单明细表":"出口成品清单明细表");
					JasperPrint jasperPrint;
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
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setName("cbbBeginDate");
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setName("cbbEndDate");
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("查询");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = new ArrayList();
					if (contract != null) {
						list = contractStatAction.findExpProductSchedule(
								new Request(CommonVars.getCurrUser()),
								contract, cbbBeginDate.getDate(), cbbEndDate
										.getDate());
					}
					initTable(list);
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			jPanel = new JPanel();
			jPanel.setLayout(flowLayout);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
		}
		return jPanel;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
