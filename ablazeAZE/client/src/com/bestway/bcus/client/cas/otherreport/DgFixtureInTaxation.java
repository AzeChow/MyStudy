package com.bestway.bcus.client.cas.otherreport;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import javax.swing.JPanel;
import javax.swing.JLabel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.io.InputStream;

/**
 * @author fhz
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgFixtureInTaxation extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton btnQuery = null;

	private JButton btnPrint = null;

	private JButton btnExit = null;

	private JTableListModel tableModel = null;

	private JPanel jPanel = null;

	private JCalendarComboBox cbbBeginDate = null;
	
	private JCalendarComboBox cbbEndDate = null;

	private CasAction casAction = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	/**
	 * This is the default constructor
	 */
	public DgFixtureInTaxation() {
		super();
		casAction = (CasAction) CommonVars
		.getApplicationContext().getBean("casAction");
		initialize();
	}

	public void setVisible(boolean b) {
		if (b) {
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
		this.setTitle("征税设备清单(已解除监管)");
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
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
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
			jToolBar.add(getJPanel());
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setBounds(new Rectangle(563, 2, 58, 24));
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Date beginDate = cbbBeginDate.getDate();
					Date endDate = cbbEndDate.getDate();
					List list = casAction.getFixtureInTaxationReport(
							new Request(CommonVars.getCurrUser()),
							 beginDate, endDate);
					if (list.isEmpty()) {
						JOptionPane.showMessageDialog(
								DgFixtureInTaxation.this, "查到零笔数据!",
								"提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					initTable(list);
				}
			});
		}
		return btnQuery;
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
			btnPrint.setBounds(new Rectangle(621, 2, 58, 24));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getList().isEmpty()) {
						JOptionPane.showMessageDialog(
								DgFixtureInTaxation.this, "没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("companyName", ((Company)CommonVars.getCurrUser().getCompany()).getName());
					InputStream reportStream = DgFixtureInTaxation.class
							.getResourceAsStream("report/FixtureInTaxationReport.jasper");
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
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(186, 2, 98, 24);
		}
		return cbbEndDate;
	}
	
	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(62, 2, 99, 24);
		}
		return cbbBeginDate;
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
						list.add(addColumn("序号",
								"serialNumber", 20));
						list.add(addColumn("企业编号",
								"ptPart", 100));
						list.add(addColumn("工厂名称", "ptName", 100));
						list.add(addColumn("报关名称", "commName", 100));
						list.add(addColumn("协议号", "contractNo", 100));
						list.add(addColumn("报关单号/发票号", "customInvoiceNo", 100));
						list.add(addColumn("进口日期", "impExpDate", 100));
						list.add(addColumn("解除监管日期", "outImpExpDate", 100));
						list.add(addColumn("单位", "unitName", 50));
						list.add(addColumn("数量", "commAmount", 100));
						list.add(addColumn("单价", "commUnitPrice", 100));
						list.add(addColumn("总价", "commTotalPrice", 100));
						list.add(addColumn("贸易方式", "tradeModeName", 100));
						list.add(addColumn("存放地点", "location", 100));
						list.add(addColumn("保管人", "holdMan", 100));
						list.add(addColumn("备注", "note", 150));
						return list;
					}
				});
	}
	
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(162, 3, 23, 21));
			jLabel2.setText("至");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(5, 4, 56, 22));
			jLabel1.setText("申报时间");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnPrint(), null);

			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
		}
		return jPanel;
	}

} 
