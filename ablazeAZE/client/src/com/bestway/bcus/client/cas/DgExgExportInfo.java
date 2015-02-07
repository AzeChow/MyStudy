/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.ExgExportInfoBase;
import com.bestway.bcus.cas.entity.TempExgExportInfo;
import com.bestway.bcus.client.cas.DgImgOrgUseInfo.TableCellRender;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.cas.report.CasReportVars;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ProgressTask;
import com.bestway.bcus.client.common.TableTextFieldEditor;
import com.bestway.bcus.client.common.TableTextFieldEditorEvent;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author ls
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgExgExportInfo extends JDialogBase {

	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JCalendarComboBox cbbEndDate = null;
	private JTextField tfFillPerson = null;
	private JCheckBox cbOverMillion = null;
	private JButton btnReloadSearch = null;
	private JButton btnClose = null;
	private JButton btnPrint = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel4 = null;
	private JPanel jPanel5 = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable1 = null;
	private JScrollPane jScrollPane1 = null;
	private JTable jTable2 = null;
	private JScrollPane jScrollPane2 = null;
	private JTable jTable3 = null;
	private JScrollPane jScrollPane3 = null;

	private JTableListModel tableModel = null;
	private JTableListModel tableModel1 = null;
	private JTableListModel tableModel2 = null;
	private JTableListModel tableModel3 = null;
	private CasAction casAction = null;
	private JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JButton btnSearch = null;
	private JButton btnSumMoney = null;
	private JPanel jPanel1 = null;
	private JToolBar jToolBar1 = null;
	private JPanel jPanel6 = null;
	private JLabel lbPerson = null;
	private JCalendarComboBox cbbBeginDate = null;
	private JLabel jLabel3 = null;
	private JLabel lbScmCoc = null;
	private JComboBox cbbScmCoc = null;
	private MaterialManageAction materialManageAction = null;
	private Log logger = LogFactory.getLog(this.getClass());
	private Integer maximumFractionDigits = CasCommonVars.getOtherOption()
			.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
			.getOtherOption().getInOutMaximumFractionDigits();

	/**
	 * This method initializes
	 * 
	 */
	public DgExgExportInfo() {
		super(false);
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();
		initUIComponents();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(733, 541);
		this.setContentPane(getJContentPane());
		this.setTitle("加工贸易产品流向情况表");

	}

	private void initUIComponents() {
		int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
				.getYearInt();
		Calendar beginClarendar = Calendar.getInstance();
		beginClarendar.set(Calendar.YEAR, year);
		beginClarendar.set(Calendar.MONTH, 0);
		beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
		this.cbbBeginDate.setDate(beginClarendar.getTime());
		this.cbbBeginDate.setCalendar(beginClarendar);

		Calendar endClarendar = Calendar.getInstance();
		endClarendar.set(Calendar.YEAR, year);
		endClarendar.set(Calendar.MONTH, 11);
		endClarendar.set(Calendar.DAY_OF_MONTH, 31);
		this.cbbEndDate.setDate(endClarendar.getTime());
		this.cbbEndDate.setCalendar(endClarendar);

		initTable(new ArrayList());
		initTable1(new ArrayList());
		initTable2(new ArrayList());
		initTable3(new ArrayList());

		// 初始化客户commonBaseCodeAction
		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbScmCoc.addItem(null);
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbScmCoc, "code", "name", 270);
		this.cbbScmCoc.setSelectedItem(null);

		lbScmCoc.setEnabled(false);
		cbbScmCoc.setEnabled(false);
		if (CasCommonVars.getOtherOption().getFillPerson() != null
				&& !CasCommonVars.getOtherOption().getFillPerson().equals("")) {
			tfFillPerson.setText(CasCommonVars.getOtherOption().getFillPerson()
					.toString());
		}

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(7, 4, 52, 20));
			jLabel3.setText("开始日期");
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel.setBounds(189, 4, 54, 20);
			jLabel.setText("结束日期");
			jPanel.add(jLabel, null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(jLabel3, null);

		}
		return jPanel;
	}

	/**
	 * This method initializes endDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(244, 4, 109, 20);
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes tfFillPerson
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFillPerson() {
		if (tfFillPerson == null) {
			tfFillPerson = new JTextField();
			tfFillPerson.setPreferredSize(new java.awt.Dimension(88, 22));
		}
		return tfFillPerson;
	}

	/**
	 * This method initializes cbOverMillion
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbOverMillion() {
		if (cbOverMillion == null) {
			cbOverMillion = new JCheckBox();
			cbOverMillion.setText("超过百万");
		}
		return cbOverMillion;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnReloadSearch() {
		if (btnReloadSearch == null) {
			btnReloadSearch = new JButton();
			btnReloadSearch.setText("报表计算");
			btnReloadSearch
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (!validateData()) {
								return;
							}
							if (JOptionPane.showConfirmDialog(
									DgExgExportInfo.this, btnReloadSearch
											.getText()
											+ "\n确定要继续吗？？", "警告!!!",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
								new ReloadSearchThread().start();
							}

						}
					});
		}
		return btnReloadSearch;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgExgExportInfo.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List oldList = null;
					if (jTabbedPane.getSelectedIndex() == 0) {
						//oldList = tableModel.getList();
						oldList = tableModel.getCurrentRowsOrder();
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						//oldList = tableModel1.getList();
						oldList = tableModel1.getCurrentRowsOrder();
					} else if (jTabbedPane.getSelectedIndex() == 2) {
						//oldList = tableModel2.getList();
						oldList = tableModel2.getCurrentRowsOrder();
					} else {
						//oldList = tableModel3.getList();
						oldList = tableModel3.getCurrentRowsOrder();
					}
					if (jTabbedPane.getSelectedIndex() == 0
							|| jTabbedPane.getSelectedIndex() == 1) {
						print1(oldList);
					} else {
						print2(oldList);
					}
				}
			});
		}
		return btnPrint;
	}

	private void print1(List oldList) {
		List<TempExgExportInfo> newList = new ArrayList<TempExgExportInfo>();
		// if(cbOverMillion.isSelected()){
		// for(int i = 0; i < oldList.size(); i++){
		// ExgExportInfoBase oldObject = (ExgExportInfoBase) oldList.get(i);
		// if(oldObject.getMoney()<1000000){
		// oldList.remove(i);
		// i--;
		// }
		// }
		// }
		for (int i = 0; i < oldList.size(); i++) {
			ExgExportInfoBase oldObject = (ExgExportInfoBase) oldList.get(i);
			for (int j = 0; j < 11; j++) {
				String fieldName = "f" + Integer.valueOf(j + 1).toString();
				Object value = null;
				try {
					value = PropertyUtils.getProperty(oldObject, fieldName);
					if (value instanceof Double) {
						DecimalFormat df = new DecimalFormat("#,##0.00");
						value = df.format(value);
					}
				} catch (Exception e) {
					logger.error("获取属性时出错，属性名：" + fieldName);
				}
				if (i % 8 == 0) {
					newList.add(new TempExgExportInfo());
				}
				setValueToNewList(newList, i, j, value);
			}
		}

		CasReportVars.setExgExportInfoBaseList(oldList);
		CommonDataSource imgExgDS = new CommonDataSource(newList);
		InputStream masterReportStream = DgFactoryQuery.class
				.getResourceAsStream("report/ExgExportInfo.jasper");
		try {
			String writer = "";
			Company company = (Company) CommonVars.getCurrUser().getCompany();
			Map<String, Object> parameters = new HashMap<String, Object>();
			if (CasCommonVars.getOtherOption().getFillPerson() != null
					&& !CasCommonVars.getOtherOption().getFillPerson().equals("")) {
				writer = CasCommonVars.getOtherOption().getFillPerson()
						.toString();
			} else {
				writer = this.tfFillPerson.getText();
			}
			SimpleDateFormat bartDateFormat = new SimpleDateFormat(
					"yyyy年MM月dd日");
			String fillDate = bartDateFormat.format(new Date());
			parameters.put("writer", writer);
			parameters.put("fillDate", fillDate);
			parameters.put("name", company.getName());
			parameters.put("buOwner", company.getOwner());
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, imgExgDS);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	private void setValueToNewList(List newList, int i, int j, Object value) {
		String fieldName = "vf" + Integer.valueOf(i % 8 + 1).toString();
		Object newObject = newList.get((i / 8) * 11 + j);
		try {
			if (value == null) {
				PropertyUtils.setProperty(newObject, fieldName, "");
			} else {
				PropertyUtils.setProperty(newObject, fieldName, value
						.toString());
			}
		} catch (Exception e) {
			logger.error("设置属性时出错，属性名：" + fieldName);
		}
	}

	private void print2(List list) {
		// Collections.sort(list);

		CommonDataSource ds = new CommonDataSource(list);
		InputStream masterReportStream = DgExgExportInfo.class
				.getResourceAsStream("report/CarryForwardCmpInfo.jasper");
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		String fillDate = bartDateFormat.format(new Date());
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("title", "结转送货对比表");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("自用资料", null, getJPanel2(), null);
			jTabbedPane.addTab("海关资料", null, getJPanel3(), null);
			jTabbedPane.addTab("单据报关单对比", null, getJPanel4(), null);
			jTabbedPane.addTab("结转送货对比", null, getJPanel5(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							setState();
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.add(getJScrollPane3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel5;
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
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
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
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable2() {
		if (jTable2 == null) {
			jTable2 = new JTable();
		}
		return jTable2;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTable2());
		}
		return jScrollPane2;
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
			jPanel1.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJToolBar1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getJPanel6());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			lbScmCoc = new JLabel();
			lbScmCoc.setText("请选择客户");
			lbPerson = new JLabel();
			lbPerson.setText("填表人");
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout.setVgap(2);
			jPanel6 = new JPanel();
			jPanel6.setLayout(flowLayout);
			jPanel6.add(lbScmCoc, null);
			jPanel6.add(getCbbScmCoc(), null);
			jPanel6.add(lbPerson, null);
			jPanel6.add(getTfFillPerson(), null);
			jPanel6.add(getCbOverMillion(), null);
			jPanel6.add(getBtnDelete(), null);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new java.awt.Rectangle(65, 4, 109, 20));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes cbbScmCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setPreferredSize(new java.awt.Dimension(150, 22));
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes jTable3
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable3() {
		if (jTable3 == null) {
			jTable3 = new JTable();
		}
		return jTable3;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getJTable3());
		}
		return jScrollPane3;
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("成品名称/规格/单位", "key", 150));
						list.add(addColumn("1.期末库存", "f1", 100, Double.class));
						list
								.add(addColumn("2.直接报关出口", "f2", 120,
										Double.class));

						list
								.add(addColumn("3.结转报关出口", "f3", 100,
										Double.class));
						list
								.add(addColumn("4.已结转未交货", "f4", 100,
										Double.class));
						list
								.add(addColumn("5.未结转已交货", "f5", 100,
										Double.class));

						list
								.add(addColumn("6.海关批准内销", "f6", 100,
										Double.class));
						list.add(addColumn("7.其它内销", "f7", 100, Double.class));

						list
								.add(addColumn("8.受托加工返回", "f8", 100,
										Double.class));
						list.add(addColumn("9.其它处理", "f9", 100, Double.class));

						list
								.add(addColumn("10.期初库存", "f10", 100,
										Double.class));
						list
								.add(addColumn("11.产量合计", "f11", 100,
										Double.class));

						list.add(addColumn("12.备注", "f12", 100));
						list.add(addColumn("金额", "money", 100, Double.class));

						return list;
					}
				});

	}

	private void initTable1(final List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter(
				maximumFractionDigits) {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("成品名称/规格/单位", "key", 150));
				list.add(addColumn("1.期末库存", "f1", 100, Double.class));
				list.add(addColumn("2.直接报关出口", "f2", 120, Double.class));

				list.add(addColumn("3.结转报关出口", "f3", 100, Double.class));
				list.add(addColumn("4.已结转未交货", "f4", 100, Double.class));
				list.add(addColumn("5.未结转已交货", "f5", 100, Double.class));

				list.add(addColumn("6.海关批准内销", "f6", 100, Double.class));
				list.add(addColumn("7.其它内销", "f7", 100, Double.class));

				list.add(addColumn("8.受托加工返回", "f8", 100, Double.class));
				list.add(addColumn("9.其它处理", "f9", 100, Double.class));

				list.add(addColumn("10.期初库存", "f10", 100, Double.class));
				list.add(addColumn("11.产量合计", "f11", 100, Double.class));

				list.add(addColumn("12.备注", "f12", 100, Double.class));
				list.add(addColumn("金额", "money", 100, Double.class));
				return list;
			}
		};

		tableModel1 = new JTableListModel(jTable1, list,
				jTableListModelAdapter, ListSelectionModel.SINGLE_SELECTION);
		List<Integer> editColumns = new ArrayList<Integer>();
		for (int i = 2; i < 15; i++) {
			if (i == 12) {
				continue;
			}
			editColumns.add(i);
			jTable1.getColumnModel().getColumn(i).setCellEditor(
					new TableTextFieldEditor(new JTextField(), event));
		}
		jTableListModelAdapter.setEditableColumn(editColumns);
		for (int i = 2; i < 15; i++) {
			jTable1.getColumnModel().getColumn(i).setCellRenderer(
					new tableCellRender());
		}
	}

	class tableCellRender extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			if (value != null) {
				if (value instanceof Double) {
					Double tmp = Double.parseDouble((String) value);
					DecimalFormat df = new DecimalFormat("###");
					this.setText("" + df.format(tmp));
				}
			}
			return this;
		}
	}

	/**
	 * cellEditor 回车事件
	 */
	private TableTextFieldEditorEvent event = new TableTextFieldEditorEvent() {
		public Object saveObject(Object obj) {
			return casAction.saveExgExportInfoBase(new Request(CommonVars
					.getCurrUser()), (ExgExportInfoBase) obj);
		}
	};
	private JButton btnDelete = null;

	private void initTable2(final List list) {
		tableModel2 = new JTableListModel(jTable2, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("原材料名称/规格/单位", "key", 200));

						list
								.add(addColumn("2.直接报关出口", "f2", 150,
										Double.class));
						list.add(addColumn("2.直接报关出口(报关单)", "f2CustomNum", 150,
								Double.class));

						return list;
					}
				});

	}

	private void initTable3(final List list) {
		tableModel3 = new JTableListModel(jTable3, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("原材料名称/规格/单位", "key", 200));
						list.add(addColumn("客户", "customerName", 100));

						list.add(addColumn("结转出口数", "hsAmount2102", 100,
								Double.class));
						list.add(addColumn("结转成品退货数", "hsAmount2009", 120,
								Double.class));
						list
								.add(addColumn("送货数", "hsAmount", 100,
										Double.class));
						list.add(addColumn("结转出口报关数", "customNum", 120,
								Double.class));
						list.add(addColumn("已结转未送货", "carryForwardNum", 120,
								Double.class));
						list.add(addColumn("未结转已送货", "unCarryForwardNum", 120,
								Double.class));
						return list;
					}
				});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.NORTH);
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
			jToolBar.add(getBtnSearch());
			jToolBar.add(getBtnReloadSearch());
			jToolBar.add(getJButton2());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查看历史记录");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!setTipMessage()) {
						return;
					}
					if (!validateData()) {
						return;
					}
					new searchThread().start();
				}
			});
		}
		return btnSearch;
	}

	/**
	 * 提示信息
	 * 
	 * @return
	 */
	private boolean setTipMessage() {
		if (jTabbedPane.getSelectedIndex() == 3) {
			if (cbbScmCoc.getSelectedItem() == null) {
				if (JOptionPane.showConfirmDialog(this,
						"客户条件为空!!\n查询所有客户的全年结转交货记录 可能要较长的时间，要继续吗??", "确认",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
					return false;
				}
			}
		}

		return true;

	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (btnSumMoney == null) {
			btnSumMoney = new JButton();
			btnSumMoney.setText("从报关单中统计金额");
			btnSumMoney.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!validateData()) {
						return;
					}
					if (JOptionPane.showConfirmDialog(DgExgExportInfo.this,
							"超百万的金额计算\n确定要继续吗？？", "警告!!!",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
						new sumMoneyThread().start();
					}
				}
			});
		}
		return btnSumMoney;
	}

	private boolean validateData() {
		Date beginDate = this.cbbBeginDate.getDate();
		Date endDate = this.cbbEndDate.getDate();
		beginDate = CommonUtils.getBeginDate(beginDate);
		endDate = CommonUtils.getEndDate(endDate);
		if (beginDate != null && endDate != null) {
			if (beginDate.after(endDate)) {
				JOptionPane.showMessageDialog(this, "开始日期大于结束日期!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		return true;
	}

	/**
	 * 生成单据的折算报关数量
	 * 
	 * @author Administrator
	 * 
	 */
	class searchThread extends Thread {
		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {
				btnSearch.setEnabled(false);
				long beginTime = System.currentTimeMillis();

				List infos = null;
				if (jTabbedPane.getSelectedIndex() == 0) {
					CommonProgress.setMessage(flag, "系统正在重新获取资料，请稍后...");
					infos = casAction.findExgExportInfos(new Request(CommonVars
							.getCurrUser()),
							com.bestway.bcus.client.cas.parameter.CasCommonVars
									.getYear(), cbOverMillion.isSelected());
					tableModel.setList(infos);
					CommonProgress.closeProgressDialog(flag);
				} else {
					//
					// 日期
					//
					Date beginDate = cbbBeginDate.getDate();
					Date endDate = cbbEndDate.getDate();
					beginDate = CommonUtils.getBeginDate(beginDate);
					endDate = CommonUtils.getEndDate(endDate);

					CommonProgress.showProgressDialog(flag,
							DgExgExportInfo.this);
					CommonProgress.setMessage(flag, "系统正在进行查询，请稍后...");
					if (jTabbedPane.getSelectedIndex() == 1) {
						infos = casAction
								.findExgExportInfoCustoms(
										new Request(CommonVars.getCurrUser()),
										com.bestway.bcus.client.cas.parameter.CasCommonVars
												.getYear(), cbOverMillion
												.isSelected());
						tableModel1.setList(infos);
					} else if (jTabbedPane.getSelectedIndex() == 2) {
						infos = casAction.findBillCustomBillCmpExgInfos(
								new Request(CommonVars.getCurrUser()),
								beginDate, endDate);
						tableModel2.setList(infos);
					} else if (jTabbedPane.getSelectedIndex() == 3) {
						infos = casAction.findCarryForwardCmpExgInfos(
								new Request(CommonVars.getCurrUser()),
								beginDate, endDate, (ScmCoc) cbbScmCoc
										.getSelectedItem());
						tableModel3.setList(infos);
					}
					CommonProgress.closeProgressDialog(flag);
				}
				String info = " 任务完成,共用时:"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
				JOptionPane.showMessageDialog(DgExgExportInfo.this, info, "提示",
						2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgExgExportInfo.this, "查询失败！！！"
						+ e.getMessage(), "提示", 2);
			}
			btnSearch.setEnabled(true);
		}
	}

	/**
	 * 生成单据的折算报关数量
	 * 
	 * @author Administrator
	 * 
	 */
	class ReloadSearchThread extends Thread {
		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {

				btnReloadSearch.setEnabled(false);
				//
				// 日期
				//
				Date beginDate = cbbBeginDate.getDate();
				Date endDate = cbbEndDate.getDate();
				beginDate = CommonUtils.getBeginDate(beginDate);
				endDate = CommonUtils.getEndDate(endDate);
				long beginTime = System.currentTimeMillis();

				List infos = null;
				if (jTabbedPane.getSelectedIndex() == 0) {

					ProgressTask progressTask = new ProgressTask() {
						protected void setClientTipMessage() {
							ToolsAction toolsAction = (ToolsAction) CommonVars
									.getApplicationContext().getBean(
											"toolsAction");
							ProgressInfo progressInfo = toolsAction
									.getProgressInfo(flag);
							if (progressInfo != null
									&& progressInfo.getHintMessage() != null) {
								CommonProgress.setMessage(flag, progressInfo
										.getHintMessage());
							}
						}
					};
					CommonProgress.showProgressDialog(flag,
							DgExgExportInfo.this, false, progressTask, 5000);
					CommonProgress.setMessage(flag, "系统正在重新获取资料，请稍后...");
					infos = casAction
							.findExgExportInfos(
									new Request(CommonVars.getCurrUser()),
									beginDate,
									endDate,
									flag,
									CasCommonVars.getOtherOption()
											.getIsShowTransFactIncipient() == null ? false
											: CasCommonVars
													.getOtherOption()
													.getIsShowTransFactIncipient());
					if(cbOverMillion.isSelected()){//超百万
						for (int i = 0; i < infos.size(); i++) {
							ExgExportInfoBase exgExportInfo = (ExgExportInfoBase) infos.get(i);
							if (exgExportInfo.getMoney() < 1000000) {
								exgExportInfo.setF12("金额小于一百万");
							}
						}
					}
					tableModel.setList(infos);
					CommonProgress.closeProgressDialog(flag);
				} else if (jTabbedPane.getSelectedIndex() == 1) {
					CommonProgress.showProgressDialog(flag,
							DgExgExportInfo.this);
					CommonProgress.setMessage(flag, "系统正在重新获取资料，请稍后...");
					casAction.copyExgExportInfoCustom(new Request(CommonVars
							.getCurrUser()));
					infos = casAction.findExgExportInfoCustoms(new Request(
							CommonVars.getCurrUser()),
							com.bestway.bcus.client.cas.parameter.CasCommonVars
									.getYear(), cbOverMillion.isSelected());
					if(cbOverMillion.isSelected()){//超百万
						for (int i = 0; i < infos.size(); i++) {
							ExgExportInfoBase exgExportInfo = (ExgExportInfoBase) infos.get(i);
							if (exgExportInfo.getMoney() < 1000000) {
								exgExportInfo.setF12("金额小于一百万");
							}
						}
					}
					tableModel1.setList(infos);
					CommonProgress.closeProgressDialog(flag);
				}
				String info = " 任务完成,共用时:"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
				JOptionPane.showMessageDialog(DgExgExportInfo.this, info, "提示",
						2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgExgExportInfo.this, "重新获取失败！！！"
						+ e.getMessage(), "提示", 2);
			} finally {
				ToolsAction toolsAction = (ToolsAction) CommonVars
						.getApplicationContext().getBean("toolsAction");
				toolsAction.removeProgressInfo(flag);
			}
			btnReloadSearch.setEnabled(true);
		}
	}

	/**
	 * 进行超百万的金额计算
	 * 
	 * @author Administrator
	 * 
	 */
	class sumMoneyThread extends Thread {
		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {

				//
				// 日期
				//
				Date beginDate = cbbBeginDate.getDate();
				Date endDate = cbbEndDate.getDate();
				beginDate = CommonUtils.getBeginDate(beginDate);
				endDate = CommonUtils.getEndDate(endDate);
				long beginTime = System.currentTimeMillis();

				CommonProgress.showProgressDialog(flag, DgExgExportInfo.this);
				CommonProgress.setMessage(flag, "系统正在进行超百万的金额计算，请稍后...");
				btnSumMoney.setEnabled(false);
				List<ExgExportInfoBase> infos = new ArrayList<ExgExportInfoBase>();
				if (jTabbedPane.getSelectedIndex() == 0) {
					infos = casAction.saveMoneyFromApplyToCustomsByProduct(
							new Request(CommonVars.getCurrUser()), beginDate,
							endDate, false);
					tableModel.setList(infos);
				} else if (jTabbedPane.getSelectedIndex() == 1) {
					infos = casAction.saveMoneyFromApplyToCustomsByProduct(
							new Request(CommonVars.getCurrUser()), beginDate,
							endDate, true);
					tableModel1.setList(infos);
				}

				CommonProgress.closeProgressDialog(flag);
				String info = " 任务完成,共用时:"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
				JOptionPane.showMessageDialog(DgExgExportInfo.this, info, "提示",
						2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgExgExportInfo.this,
						"超百万的金额计算失败！！！" + e.getMessage(), "提示", 2);
			}
			btnSumMoney.setEnabled(true);
		}
	}

	/**
	 * 设置状态
	 * 
	 */
	private void setState() {
		int index = jTabbedPane.getSelectedIndex();

		btnPrint.setEnabled(index != 2);
		cbbScmCoc.setEnabled(index == 3);
		lbScmCoc.setEnabled(index == 3);

		boolean flag1And2 = index == 0 || index == 1;
		btnReloadSearch.setEnabled(flag1And2);
		btnSumMoney.setEnabled(flag1And2);
		cbOverMillion.setEnabled(flag1And2);
		tfFillPerson.setEnabled(flag1And2);
		lbPerson.setEnabled(flag1And2);

		if (index == 0) {
			btnReloadSearch.setText("报表计算");
			btnSearch.setText("查看历史资料");
		} else if (index == 1) {
			btnReloadSearch.setText("重新获取");
			btnSearch.setText("查看历史资料");
		} else {
			btnSearch.setText("查询");
		}

	}

	/**
	 * This method initializes btnDelete	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setToolTipText("打印年审报表时，不显示隐藏的商品，重新获取或关闭后后恢复");
			btnDelete.setText("隐藏");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = null;
					if (jTabbedPane.getSelectedIndex() == 0) {
						 list = tableModel.getCurrentRows();
						 tableModel.deleteRows(list);
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						 list = tableModel1.getCurrentRows();
						 tableModel1.deleteRows(list);
					} else if (jTabbedPane.getSelectedIndex() == 2) {
						 list = tableModel2.getCurrentRows();
						 tableModel2.deleteRows(list);
					} else {
						list = tableModel3.getCurrentRows();
						tableModel3.deleteRows(list);
					}
				}
			});
		}
		return btnDelete;
	}

}
