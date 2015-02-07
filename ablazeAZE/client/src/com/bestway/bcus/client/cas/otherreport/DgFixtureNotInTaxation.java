/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.otherreport;

import java.io.InputStream;
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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import javax.swing.JComboBox;

/**
 * @author fhz
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgFixtureNotInTaxation extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton btnQuery = null;

	private JButton btnPrint = null;

	private JButton btnExit = null;

	private JTableListModel tableModel = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JComboBox cbbState = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private CasAction casAction = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	/**
	 * This is the default constructor
	 */
	public DgFixtureNotInTaxation() {
		super();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("");
		this.setSize(750, 510);
		this.setContentPane(getJContentPane());
		initcbbContract();
	}

	public void setVisible(boolean b) {
		if (b) {
			String code = ((Company) CommonVars.getCurrUser().getCompany())
					.getCode();// 加工单位代码
			char[] a = new String(code.getBytes(), 0, code.length())
					.toCharArray();
			if (a[5] >= 'A' && a[5] <= 'z' && a[6] >= 'A' && a[6] <= 'z') {// 当公司的加工单位编码的第六和第七位为字母为来料公司否则为三资
				this.setTitle("不作价设备清单");
			} else {
				this.setTitle("减免税设备清单");
			}
			List list = new ArrayList();
			initTable(list);
		}
		super.setVisible(b);
	}

	private void initcbbContract() {
		// 初始化状态
		this.cbbState.removeAllItems();
		this.cbbState.addItem(new ItemProperty(" ", " "));
		this.cbbState.addItem(new ItemProperty("001", "未解除监管"));
		this.cbbState.addItem(new ItemProperty("002", "已解除监管"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbState);
		this.cbbState.setRenderer(CustomBaseRender.getInstance().getRender());
		this.cbbState.setSelectedIndex(0);
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
					ItemProperty itemProperty = (ItemProperty) cbbState
							.getSelectedItem();
					String stateCode = itemProperty.getCode();
					Date beginDate = cbbBeginDate.getDate();
					Date endDate = cbbEndDate.getDate();
					List list = casAction.getFixtureNotInTaxationReport(
							new Request(CommonVars.getCurrUser()), stateCode,
							beginDate, endDate);
					if (list.isEmpty()) {
						JOptionPane.showMessageDialog(
								DgFixtureNotInTaxation.this, "查到零笔数据!",
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
								DgFixtureNotInTaxation.this, "没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("title", getTitle());
					InputStream reportStream = DgFixtureNotInTaxation.class
							.getResourceAsStream("report/FixtureNotInTaxationReport.jasper");
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
						list.add(addColumn("序号", "serialNumber", 20));
						list.add(addColumn("企业编号", "billDetail.ptPart", 100));
						list.add(addColumn("工厂名称", "billDetail.ptName", 100));
						list.add(addColumn("报关名称",
								"baseCustomsCommInfo.commName", 100));
						list
								.add(addColumn(
										"协议号",
										"baseCustomsCommInfo.baseCustomsDeclaration.contract",
										100));
						list
								.add(addColumn(
										"报关单号",
										"baseCustomsCommInfo.baseCustomsDeclaration.customsDeclarationCode",
										100));
						list
								.add(addColumn(
										"进口日期",
										"baseCustomsCommInfo.baseCustomsDeclaration.impExpDate",
										100));
						list.add(addColumn("单位",
								"baseCustomsCommInfo.unit.name", 50));
						list.add(addColumn("数量",
								"baseCustomsCommInfo.commAmount", 100));
						list.add(addColumn("单价",
								"baseCustomsCommInfo.commUnitPrice", 100));
						list.add(addColumn("总价",
								"baseCustomsCommInfo.commTotalPrice", 100));
						list.add(addColumn("状态", "stateName", 100));
						list.add(addColumn("存放地点", "billDetail.location", 100));
						list.add(addColumn("保管人", "billDetail.holdMan", 100));
						list.add(addColumn("备注", "billDetail.note", 150));
						return list;
					}
				});
	}

	private String change(Object value) {
		if (value == null)
			return "";
		else
			return Integer.valueOf(value.toString()) == 0 ? "不作价设备" : "借用设备";

	}

	/**
	 * This method initializes jPanel减免设备结转
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
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(304, 5, 39, 20));
			jLabel.setText("状态");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getCbbContract(), null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnPrint(), null);

			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes cbbContract
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbContract() {
		if (cbbState == null) {
			cbbState = new JComboBox();
			cbbState.setBounds(new Rectangle(343, 2, 130, 24));
		}
		return cbbState;
	}
}
