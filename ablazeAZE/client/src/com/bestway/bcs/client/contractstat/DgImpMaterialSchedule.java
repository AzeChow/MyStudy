/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractstat;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractstat.action.ContractStatAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Dimension;
import javax.swing.JToolBar;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgImpMaterialSchedule extends JDialogBase {

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

	private JToolBar jToolBar = null;  //  @jve:decl-index=0:visual-constraint="634,-6"

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
	public DgImpMaterialSchedule() {
		super();
		initialize();
		contractStatAction = (ContractStatAction) CommonVars
				.getApplicationContext().getBean("contractStatAction");
	}
	
	public void setVisible(boolean b){
		if(b){
			List list = new ArrayList();
			if(contract!=null){
				this.cbbBeginDate.setDate(contract.getBeginDate());
			}else{
				this.cbbBeginDate.setDate(null);
			}
			if (this.contract != null) {
				list = this.contractStatAction.findImpMaterialSchedule(new Request(
						CommonVars.getCurrUser()), this.contract,cbbBeginDate.getDate(),cbbEndDate.getDate(),false);
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
		this.setTitle("料件执行进度[总表]");
		this.setSize(766, 510);
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
			jLabel.setText("报关日期范围从");
			jLabel1.setText("到");
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
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
						list.add(addColumn("总进口量", "impTotalAmont", 100));
						list.add(addColumn("报关单进口量", "impCDAmount", 100));
						list.add(addColumn("料件进口量", "directImport", 100));
						list.add(addColumn("转厂进口量", "transferFactoryImport",
								100));
						list.add(addColumn("退料出口量", "backMaterialExport", 100));
						list.add(addColumn("退料复出量", "backMaterialReturn", 100));
						list
								.add(addColumn("退料退换量", "backMaterialExchange",
										100));
						list.add(addColumn("成品使用量", "productUse", 100));
						list.add(addColumn("余料情况", "remainAmount", 100));
						list.add(addColumn("缺料情况", "ullage", 100));
						list.add(addColumn("可进口量", "canImportAmount", 100));
						list.add(addColumn("比例", "scale", 100));
//						list.add(addColumn("主料/辅料", "materialType", 100));
						list.add(addColumn("原产国", "country.name", 100));
//						list.add(addColumn("凭证序号", "credenceSerialNo", 100));
						list.add(addColumn("余料金额", "remainMoney", 100));
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
								DgImpMaterialSchedule.this, "没有数据可以打印",
								"提示", JOptionPane.OK_OPTION);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = DgImpMaterialSchedule.class
							.getResourceAsStream("report/ImpMaterialSchedule.jasper");
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
						list = contractStatAction.findImpMaterialSchedule(new Request(
								CommonVars.getCurrUser()), contract,cbbBeginDate.getDate(),cbbEndDate.getDate(),false);
					}
					initTable(list);
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
		}
		return jToolBar;
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
