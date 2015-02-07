/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.ImgOrgUseInfo;
import com.bestway.bcus.cas.entity.ImgOrgUseInfoBase;
import com.bestway.bcus.cas.entity.TempExgExportInfo;
import com.bestway.bcus.cas.entity.TempImgOrgUseInfo;
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
import com.bestway.ui.winuicontrol.JTableBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * 加工贸易原材料来源与使用情况表窗体 2009年 8月7日 贺巍添加注释
 * 
 * @author refactor ls // change the template for this generated type comment go
 *         to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgImgOrgUseInfo extends JDialogBase {

	private JPanel jPanel = null;
	
	private MaterialManageAction materialManageAction = null;
	
	private JLabel jLabel = null;
	
	private JCalendarComboBox cbbEndDate = null;
	
	private JTextField tfFillPerson = null;
	
	private JCheckBox cbOverMillion = null;
	
	private JTabbedPane jTabbedPane = null;
	
	private JPanel jPanel2 = null;
	 
	private JPanel jPanel3 = null;
	
	private JPanel jPanel4 = null;
	
	private JPanel jPanel5 = null;
	
	private JTableBase tbOccupied = null;
	
	private JScrollPane jScrollPane = null;
	
	private JTableBase tbCustomsformation = null;
	
	private JScrollPane jScrollPane1 = null;
	
	private JTable tbCompareBillDeclaration = null;
	
	private JScrollPane jScrollPane2 = null;
	
	private JTable tbReceivingCarryComparison = null;
	
	private JScrollPane jScrollPane3 = null;
	
	private JTableListModel tableModel = null;
	
	private JTableListModel tableModelCustomsformation = null;
	
	private JTableListModel tableModelCompareBillDeclaration = null;
	
	private JTableListModel tableModelReceivingCarryComparison = null;
	
	private JToolBar jToolBar = null;
	
	private JButton btnReloadSearch = null;
	
	private JButton btnPrint = null;
	
	private JButton btnExit = null;
	
	private JPanel jContentPane = null;
	
	private CasAction casAction = null;
	
	private JButton btnSearch = null;
	
	private JButton btnSumMoney = null;
	
	private JComboBox cbbScmCoc = null;
	
	/**
	 * 小数位控制参数
	 */
	private Integer maximumFractionDigits = CasCommonVars.getOtherOption()
			.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
			.getOtherOption().getInOutMaximumFractionDigits(); // @jve:decl-index=0:

	/**
	 * 构造函数 This method initializes
	 * 
	 */
	public DgImgOrgUseInfo() {
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
	 * 初始化标题和窗体尺寸 This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(897, 614);
		this.setContentPane(getJContentPane());
		this.setTitle("加工贸易原材料来源与使用情况表");
	}

	/**
	 * 初始化组建
	 * 
	 */
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
		initTableCustomsformation(new ArrayList());
		initTableCompareBillDeclaration(new ArrayList());
		initTableReceivingCarryComparison(new ArrayList());

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
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(4, 4, 53, 22));
			jLabel1.setText("开始日期");
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel.setText("结束日期");
			jLabel.setBounds(new java.awt.Rectangle(163, 4, 48, 22));
			jPanel.add(jLabel, null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbBeginDate(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes cbbEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setPreferredSize(new java.awt.Dimension(85, 22));
			cbbEndDate.setBounds(new java.awt.Rectangle(216, 4, 94, 22));
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
			tfFillPerson.setPreferredSize(new java.awt.Dimension(65, 22));
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
	 * 打印报表
	 * 
	 * @param oldList
	 */
	private void printSelf(List oldList) {
		List<TempExgExportInfo> newList = new ArrayList<TempExgExportInfo>();
//		if (cbOverMillion.isSelected()) {
//			for (int i = 0; i < oldList.size(); i++) {
//				ImgOrgUseInfoBase oldObject = (ImgOrgUseInfoBase) oldList
//						.get(i);
//				if (oldObject.getMoney() < 1000000) {
//					oldList.remove(i);
//					i--;
//				}
//			}
//		}
		for (int i = 0; i < oldList.size(); i++) {
			ImgOrgUseInfoBase oldObject = (ImgOrgUseInfoBase) oldList.get(i);
			for (int j = 0; j < 26; j++) {
				String fieldName = "f" + Integer.valueOf(j + 1).toString();
				Object value = null;
				try {
					value = PropertyUtils.getProperty(oldObject, fieldName);
					if (value instanceof Double) {
						DecimalFormat df = new DecimalFormat("#,##0.00");
						value = df.format(value);
					}
				} catch (Exception e) {
					System.out.println("获取属性时出错，属性名：" + fieldName);
					e.printStackTrace();
				}
				if (i % 8 == 0) {
					newList.add(new TempExgExportInfo());
				}
				setValueToNewList(newList, i, j, value);
			}
		}
		CasReportVars.setImgOrgUseInfoBaseList(oldList);
		CommonDataSource imgExgDS = new CommonDataSource(newList);
		InputStream masterReportStream = DgFactoryQuery.class
				.getResourceAsStream("report/ImgOrgUseInfo.jasper");
		try {
			String writer = "";
			Company company = (Company) CommonVars.getCurrUser().getCompany();
			Map<String, Object> parameters = new HashMap<String, Object>();
			if (CasCommonVars.getOtherOption().getFillPerson() != null
					&& !CasCommonVars.getOtherOption().getFillPerson().equals(
							"")) {
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

	/**
	 * 把值赋予到新的新的List中去
	 * 
	 * @param newList
	 * @param i
	 * @param j
	 * @param value
	 */
	private void setValueToNewList(List newList, int i, int j, Object value) {
		String fieldName = "vf" + Integer.valueOf(i % 8 + 1).toString();
		Object newObject = newList.get((i / 8) * 26 + j);
		try {
			if (value == null) {
				PropertyUtils.setProperty(newObject, fieldName, "");
			} else {
				PropertyUtils.setProperty(newObject, fieldName, value
						.toString());
			}
		} catch (Exception e) {
			System.out.println("设置属性时出错，属性名：" + fieldName);
			e.printStackTrace();
		}
	}

	/**
	 * 打印结转收货对比表
	 * 
	 * @param list
	 */
	private void printNoSelf(List list) {

		CommonDataSource ds = new CommonDataSource(list);
		InputStream masterReportStream = DgExgExportInfo.class
				.getResourceAsStream("report/CarryForwardCmpInfo.jasper");

		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		String fillDate = bartDateFormat.format(new Date());
		try {

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("title", "结转收货对比表");
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
			jTabbedPane.addTab("结转收货对比", null, getJPanel5(), null);
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
	 * This method initializes tbOccupied
	 * 
	 * @return javax.swing.JTable
	 */
	private JTableBase getTbOccupied() {
		if (tbOccupied == null) {
			tbOccupied = new JTableBase();
			tbOccupied.setJarDataSaveOrder(0);
		}
		return tbOccupied;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbOccupied());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbCustomsformation
	 * 
	 * @return javax.swing.JTable
	 */
	private JTableBase getTbCustomsformation() {
		if (tbCustomsformation == null) {
			tbCustomsformation = new JTableBase();
			tbCustomsformation.setJarDataSaveOrder(1);
		}
		return tbCustomsformation;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbCustomsformation());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbCompareBillDeclaration
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbCompareBillDeclaration() {
		if (tbCompareBillDeclaration == null) {
			tbCompareBillDeclaration = new JTable();
		}
		return tbCompareBillDeclaration;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbCompareBillDeclaration());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes tbReceivingCarryComparison
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbReceivingCarryComparison() {
		if (tbReceivingCarryComparison == null) {
			tbReceivingCarryComparison = new JTable();
		}
		return tbReceivingCarryComparison;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getTbReceivingCarryComparison());
		}
		return jScrollPane3;
	}

	/**
	 * 初始化自用资料表体
	 * 
	 * @param list
	 */
	private void initTable(final List list) {
		tableModel = new JTableListModel(tbOccupied, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("原材料名称/规格/单位", "key", 200));
						list.add(addColumn("1.期初库存", "f1", 100, Double.class));

						list
								.add(addColumn("2.直接报关进口", "f2", 120,
										Double.class));
						list
								.add(addColumn("3.结转报关进口", "f3", 100,
										Double.class));
						list
								.add(addColumn("4.已结转未收货", "f4", 100,
										Double.class));

						list
								.add(addColumn("5.未结转已收货", "f5", 100,
										Double.class));
						list.add(addColumn("6.其中：海关征税进口", "f6", 150,
								Double.class));
						list.add(addColumn("7.国内采购", "f7", 100, Double.class));

						list.add(addColumn("8.受托加工", "f8", 100, Double.class));
						list.add(addColumn("9.外发加工返回料件", "f9", 150,
								Double.class));

						list
								.add(addColumn("10.其它来源", "f10", 100,
										Double.class));
						list
								.add(addColumn("11.来源合计", "f11", 100,
										Double.class));

						list.add(addColumn("12.直接出口成品耗用", "f12", 150,
								Double.class));
						list.add(addColumn("13.实际结转成品耗用", "f13", 150,
								Double.class));

						list.add(addColumn("14.海关批准内销耗用", "f14", 150,
								Double.class));
						list
								.add(addColumn("15.退运出口", "f15", 100,
										Double.class));
						list.add(addColumn("16.其它内销耗用", "f16", 100,
								Double.class));

						list.add(addColumn("17.受托加工返回耗用", "f17", 150,
								Double.class));
						list
								.add(addColumn("18.发外加工", "f18", 100,
										Double.class));
						list.add(addColumn("19.发外返回成品耗用", "f19", 150,
								Double.class));

						list.add(addColumn("20.损耗", "f20", 100, Double.class));
						list
								.add(addColumn("21.其它使用", "f21", 100,
										Double.class));
						list
								.add(addColumn("22.使用合计", "f22", 100,
										Double.class));

						list.add(addColumn("23.期末实际库存", "f23", 100,
								Double.class));
						list.add(addColumn("24.其中：库存原材料", "f24", 150,
								Double.class));
						list.add(addColumn("25.库存在产品耗用", "f25", 150,
								Double.class));

						list.add(addColumn("26.库存产成品耗用", "f26", 150,
								Double.class));
						list.add(addColumn("27.备注", "f27", 100));
						list.add(addColumn("金额", "money", 100, Double.class));
						// list.add(addColumn("金额", "money", 100));
						return list;
					}
				});
		// jTable.getColumnModel().getColumn(29).setCellRenderer(
		// new tableCellRender());
	}

	/**
	 * 初始化海关资料表体
	 * 
	 * @param list
	 */
	private void initTableCustomsformation(final List list) {

		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter(
				maximumFractionDigits) {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("原材料名称/规格/单位", "key", 200));
				list.add(addColumn("1.期初库存", "f1", 100, Double.class));

				list.add(addColumn("2.直接报关进口", "f2", 120, Double.class));
				list.add(addColumn("3.结转报关进口", "f3", 100, Double.class));
				list.add(addColumn("4.已结转未收货", "f4", 100, Double.class));

				list.add(addColumn("5.未结转已收货", "f5", 100, Double.class));
				list.add(addColumn("6.其中：海关征税进口", "f6", 150, Double.class));
				list.add(addColumn("7.国内采购", "f7", 100, Double.class));

				list.add(addColumn("8.受托加工", "f8", 100, Double.class));
				list.add(addColumn("9.外发加工返回料件", "f9", 150, Double.class));

				list.add(addColumn("10.其它来源", "f10", 100, Double.class));
				list.add(addColumn("11.来源合计", "f11", 100, Double.class));

				list.add(addColumn("12.直接出口成品耗用", "f12", 150, Double.class));
				list.add(addColumn("13.实际结转成品耗用", "f13", 150, Double.class));

				list.add(addColumn("14.海关批准内销耗用", "f14", 150, Double.class));
				list.add(addColumn("15.退运出口", "f15", 100, Double.class));
				list.add(addColumn("16.其它内销耗用", "f16", 100, Double.class));

				list.add(addColumn("17.受托加工返回耗用", "f17", 150, Double.class));
				list.add(addColumn("18.发外加工", "f18", 100, Double.class));
				list.add(addColumn("19.发外返回成品耗用", "f19", 150, Double.class));

				list.add(addColumn("20.损耗", "f20", 100, Double.class));
				list.add(addColumn("21.其它使用", "f21", 100, Double.class));
				list.add(addColumn("22.使用合计", "f22", 100, Double.class));

				list.add(addColumn("23.期末实际库存", "f23", 100, Double.class));
				list.add(addColumn("24.其中：库存原材料", "f24", 150, Double.class));
				list.add(addColumn("25.库存在产品耗用", "f25", 150, Double.class));

				list.add(addColumn("26.库存产成品耗用", "f26", 150, Double.class));
				list.add(addColumn("27.备注", "f27", 100));
				list.add(addColumn("金额", "money", 100, Double.class));
				// list.add(addColumn("金额", "money", 100));
				return list;
			}
		};
		tableModelCustomsformation = new JTableListModel(tbCustomsformation, list,
				jTableListModelAdapter, ListSelectionModel.SINGLE_SELECTION);
		//
		// 设置列的修改和修改 enter key event
		//
		List<Integer> editColumns = new ArrayList<Integer>();
		for (int i = 2; i < 30; i++) {
			if (i == 12 || i == 23 || i == 24) {
				continue;
			}
			editColumns.add(i);
			tbCustomsformation.getColumnModel().getColumn(i).setCellEditor(
					new TableTextFieldEditor(new JTextField(), event));
		}
		jTableListModelAdapter.setEditableColumn(editColumns);
		for (int i = 2; i < 30; i++) {
			tbCustomsformation.getColumnModel().getColumn(i).setCellRenderer(
					new TableCellRender());
		}
	}

	/**
	 * 内部类，表体渲染器
	 * 
	 * @author ?
	 * 
	 */
	class TableCellRender extends DefaultTableCellRenderer {
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
			return casAction.saveImgOrgUseInfoBase(new Request(CommonVars
					.getCurrUser()), (ImgOrgUseInfoBase) obj);

		}
	};
	private JPanel jPanel1 = null;
	private JToolBar jJToolBarBar = null;
	private JPanel jPanel6 = null;
	private JLabel lbScmCoc = null;
	private JButton btnMiddleInfo = null;
	private JLabel lbPerson = null;
	private JLabel jLabel1 = null;
	private JCalendarComboBox cbbBeginDate = null;

	private JButton btnDelete = null;

	/**
	 * 初始化单据报关单表体
	 * 
	 * @param list
	 */
	private void initTableCompareBillDeclaration(final List list) {
		tableModelCompareBillDeclaration = new JTableListModel(tbCompareBillDeclaration, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("原材料名称/规格/单位", "key", 200));
						list.add(addColumn("2.直接报关进口", "f2", 120));
						list.add(addColumn("2.直接报关进口(报关单)", "f2CustomNum",	150));
						list.add(addColumn("4.料件复出", "fimgReOut", 60));
						list.add(addColumn("5.料件复出(报关单)", "fimgReOutCustomNum", 150));
						list.add(addColumn("6.料件退换", "fimgExchange", 60));
						list.add(addColumn("7.料件退换(报关单)",	"fimgExchangeCustomNum", 150));
						list.add(addColumn("15.退运出口(4+6)", "f15", 100));
						list.add(addColumn("15.退运出口(报关单5+7)", "f15CustomNum", 150));

						return list;
					}
				});
	}

	/**
	 * 初始化结转收货对比表体
	 * 
	 * @param list
	 */
	private void initTableReceivingCarryComparison(final List list) {
		tableModelReceivingCarryComparison = new JTableListModel(tbReceivingCarryComparison, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("料件名称/规格/单位", "key", 200));
						list.add(addColumn("供应商", "customerName", 150));
						list.add(addColumn("结转进口数", "hsAmount1004", 100,
								Double.class));
						list.add(addColumn("结转料件退货数", "hsAmount1106", 100,
								Double.class));
						list
								.add(addColumn("收货数", "hsAmount", 100,
										Double.class));
						list.add(addColumn("结转报关数", "customNum", 100,
								Double.class));
						list.add(addColumn("已结转未收货", "carryForwardNum", 100,
								Double.class));
						list.add(addColumn("未结转已收货", "unCarryForwardNum", 100,
								Double.class));
						return list;
					}
				});
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
			jToolBar.add(getBtnMiddleInfo());
			jToolBar.add(getBtnSumMoney());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new java.awt.Rectangle(63, 4, 94, 22));
			cbbBeginDate.setPreferredSize(new Dimension(85, 22));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jButton
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
									DgImgOrgUseInfo.this, btnReloadSearch
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
					List oldList = null;
					if (jTabbedPane.getSelectedIndex() == 0) {
//						oldList = tableModel.getList();
						oldList = tableModel.getCurrentRowsOrder();
					} else if (jTabbedPane.getSelectedIndex() == 1) {
//						oldList = tableModelCustomsformation.getList();
						oldList = tableModelCustomsformation.getCurrentRowsOrder();
					} else if (jTabbedPane.getSelectedIndex() == 2) {
//						oldList = tableModelCompareBillDeclaration.getList();
						oldList = tableModelCompareBillDeclaration.getCurrentRowsOrder();
					} else {
//						oldList = tableModelReceivingCarryComparison.getList();
						oldList = tableModelReceivingCarryComparison.getCurrentRowsOrder();
					}
					if (jTabbedPane.getSelectedIndex() == 0
							|| jTabbedPane.getSelectedIndex() == 1) {
						printSelf(oldList);
					} else {
						printNoSelf(oldList);
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
			btnExit.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
		}
		return btnExit;
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
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJJToolBarBar(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getJPanel6());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			lbPerson = new JLabel();
			lbPerson.setText("填表人");
			lbScmCoc = new JLabel();
			lbScmCoc.setText("请选择供应商:");
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
	 * This method initializes btnMiddleInfo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnMiddleInfo() {
		if (btnMiddleInfo == null) {
			btnMiddleInfo = new JButton();
			btnMiddleInfo.setText("中间过程");
			btnMiddleInfo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (!validateData()) {
								return;
							}
							runMiddleInfo();
						}
					});
		}
		return btnMiddleInfo;
	}

	/**
	 * This method initializes btnReloadSearch
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
					new SearchThread().start();
				}
			});
		}
		return btnSearch;
	}

	/**
	 * 检索日期方法
	 * 
	 * @return
	 */
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
	 * 提示信息
	 * 
	 * @return
	 */
	private boolean setTipMessage() {
		if (jTabbedPane.getSelectedIndex() == 3) {
			if (cbbScmCoc.getSelectedItem() == null) {
				if (JOptionPane.showConfirmDialog(this,
						"供应商条件为空!!\n查询所有供应商的全年结转收货记录 可能要较长的时间，要继续吗??", "确认",
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
	private JButton getBtnSumMoney() {
		if (btnSumMoney == null) {
			btnSumMoney = new JButton();
			btnSumMoney.setText("从报关单中统计金额");
			btnSumMoney.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!validateData()) {
						return;
					}

					if (JOptionPane.showConfirmDialog(DgImgOrgUseInfo.this,
							"超百万的金额计算\n确定要继续吗？？", "警告!!!",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
						new SumMoneyThread().start();
					}
				}
			});
		}
		return btnSumMoney;
	}

	/**
	 * 生成单据的折算报关数量
	 * 
	 * @author Administrator
	 * 
	 */
	class SearchThread extends Thread {

		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {
				CommonProgress.showProgressDialog(flag, DgImgOrgUseInfo.this);
				CommonProgress.setMessage(flag, "系统正在进行查询，请稍后...");
				btnSearch.setEnabled(false);
				List infos = null;
				long beginTime = System.currentTimeMillis();
				String info = "";
				//
				// 日期
				//
				Date beginDate = cbbBeginDate.getDate();
				Date endDate = cbbEndDate.getDate();
				beginDate = CommonUtils.getBeginDate(beginDate);
				endDate = CommonUtils.getEndDate(endDate);

				if (jTabbedPane.getSelectedIndex() == 0) {
					infos = casAction.findImgOrgUseInfos(new Request(CommonVars
							.getCurrUser()),
							com.bestway.bcus.client.cas.parameter.CasCommonVars
									.getYear(), cbOverMillion.isSelected());
					tableModel.setList(infos);
				} else if (jTabbedPane.getSelectedIndex() == 1) {
					infos = casAction.findImgOrgUseInfoCustoms(new Request(
							CommonVars.getCurrUser()),
							com.bestway.bcus.client.cas.parameter.CasCommonVars
									.getYear(), cbOverMillion.isSelected());
					tableModelCustomsformation.setList(infos);
				} else if (jTabbedPane.getSelectedIndex() == 2) {
					infos = casAction.findBillCustomBillCmpImgInfos(
							new Request(CommonVars.getCurrUser()), beginDate,
							endDate);
					initTableCompareBillDeclaration(infos);
				} else if (jTabbedPane.getSelectedIndex() == 3) {
					infos = casAction.findCarryForwardCmpImgInfos(new Request(
							CommonVars.getCurrUser()), beginDate, endDate,
							(ScmCoc) cbbScmCoc.getSelectedItem());
					tableModelReceivingCarryComparison.setList(infos);
				}
				CommonProgress.closeProgressDialog(flag);
				info += " 任务完成,共用时:" + (System.currentTimeMillis() - beginTime)
						+ " 毫秒 ";
				JOptionPane.showMessageDialog(DgImgOrgUseInfo.this, info, "提示",
						2);
			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgImgOrgUseInfo.this, "查询失败！！！"
						+ e.getMessage(), "提示", 2);
			}
			btnSearch.setEnabled(true);
		}
	}

	/**
	 * 重新生海关帐,年度料件线程
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
				long beginTime = System.currentTimeMillis();
				String info = "";
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
							DgImgOrgUseInfo.this, false, progressTask, 5000);
					CommonProgress.setMessage(flag, "系统正在重新获取资料，请稍后...");

					infos = casAction.findImgOrgUseInfos(new Request(CommonVars
							.getCurrUser()), cbbBeginDate.getDate(), cbbEndDate
							.getDate(), flag, CasCommonVars.getOtherOption()
							.getIsShowTransFactIncipient() == null ? false
							: CasCommonVars.getOtherOption()
									.getIsShowTransFactIncipient());
					if (cbOverMillion.isSelected()) {
					for (int i = 0; i < infos.size(); i++) {
						ImgOrgUseInfoBase oldObject = (ImgOrgUseInfoBase) infos
								.get(i);
						if (oldObject.getMoney() < 1000000) {
							oldObject.setF27(oldObject.getF27()+"金额小于一百万");
						}
					}
				}
					System.out.println("infos size = " + infos.size());
					tableModel.setList(infos);
					CommonProgress.closeProgressDialog(flag);

				} else if (jTabbedPane.getSelectedIndex() == 1) {
					CommonProgress.showProgressDialog(flag,
							DgImgOrgUseInfo.this);
					CommonProgress.setMessage(flag, "系统正在重新获取资料，请稍后...");
					casAction.copyImgOrgUseInfoCustom(new Request(CommonVars
							.getCurrUser()));
					infos = casAction.findImgOrgUseInfoCustoms(new Request(
							CommonVars.getCurrUser()),
							com.bestway.bcus.client.cas.parameter.CasCommonVars
									.getYear(), cbOverMillion.isSelected());
					tableModelCustomsformation.setList(infos);
					CommonProgress.closeProgressDialog(flag);
				}
				info += " 任务完成,共用时:" + (System.currentTimeMillis() - beginTime)
						+ " 毫秒 ";
				JOptionPane.showMessageDialog(DgImgOrgUseInfo.this, info, "提示",
						2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgImgOrgUseInfo.this, "重新获取失败！！！"
						+ e.getMessage(), "提示", 2);
			}
			btnReloadSearch.setEnabled(true);
		}
	}

	/**
	 * 进行超百万的金额计算线程
	 * 
	 * @author Administrator
	 * 
	 */
	class SumMoneyThread extends Thread {
		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {
				long beginTime = System.currentTimeMillis();
				String info = "";

				CommonProgress.showProgressDialog(flag, DgImgOrgUseInfo.this);
				CommonProgress.setMessage(flag, "系统正在进行超百万的金额计算，请稍后...");
				btnSumMoney.setEnabled(false);

				//
				// 日期
				//
				Date beginDate = cbbBeginDate.getDate();
				Date endDate = cbbEndDate.getDate();
				beginDate = CommonUtils.getBeginDate(beginDate);
				endDate = CommonUtils.getEndDate(endDate);

				List<ImgOrgUseInfoBase> infos = new ArrayList<ImgOrgUseInfoBase>();
				if (jTabbedPane.getSelectedIndex() == 0) {
					infos = casAction.saveMoneyFromApplyToCustomsByMateriel(
							new Request(CommonVars.getCurrUser()), beginDate,
							endDate, false);
					tableModel.setList(infos);
				} else if (jTabbedPane.getSelectedIndex() == 1) {
					infos = casAction.saveMoneyFromApplyToCustomsByMateriel(
							new Request(CommonVars.getCurrUser()), beginDate,
							endDate, true);
					tableModelCustomsformation.setList(infos);
				}
				CommonProgress.closeProgressDialog(flag);
				info += " 金额计算任务完成,共用时:"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
				JOptionPane.showMessageDialog(DgImgOrgUseInfo.this, info, "提示",
						2);
			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgImgOrgUseInfo.this,
						"超百万的金额计算失败！！！" + e.getMessage(), "提示", 2);
			}
			btnSumMoney.setEnabled(true);
		}
	}

	/**
	 * 运行运算中间信息
	 */
	private void runMiddleInfo() {
		ImgOrgUseInfo imgOrgUseInfo = (ImgOrgUseInfo) this.tableModel
				.getCurrentRow();
		if (imgOrgUseInfo == null) {
			JOptionPane.showMessageDialog(this, "请选择要计算中间过程的记录", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		new RunMiddleInfoThread(imgOrgUseInfo).start();

	}

	/**
	 * 中间过程线程
	 * 
	 * @author ？
	 * 
	 */
	class RunMiddleInfoThread extends Thread {
		private ImgOrgUseInfo imgOrgUseInfo = null;

		/**
		 * 构造方法
		 * 
		 * @param imgOrgUseInfo
		 */
		public RunMiddleInfoThread(ImgOrgUseInfo imgOrgUseInfo) {
			this.imgOrgUseInfo = imgOrgUseInfo;
		}

		/**
		 * 线程运行方法
		 */
		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {
				btnMiddleInfo.setEnabled(false);

				String info = "";
				long beginTime = System.currentTimeMillis();
				ProgressTask progressTask = new ProgressTask() {
					protected void setClientTipMessage() {
						ToolsAction toolsAction = (ToolsAction) CommonVars
								.getApplicationContext().getBean("toolsAction");
						ProgressInfo progressInfo = toolsAction
								.getProgressInfo(flag);
						if (progressInfo != null
								&& progressInfo.getHintMessage() != null) {
							CommonProgress.setMessage(flag, progressInfo
									.getHintMessage());
						}
					}
				};
				CommonProgress.showProgressDialog(flag, DgImgOrgUseInfo.this,
						false, progressTask, 5000);
				CommonProgress.setMessage(flag, "系统正在计算中间过程的记录，请稍后...");

				List<TempImgOrgUseInfo> infos = casAction
						.findTempImgOrgUseInfo(new Request(CommonVars
								.getCurrUser()), cbbBeginDate.getDate(),
								cbbEndDate.getDate(), imgOrgUseInfo, flag);
				System.out.println("infos size = " + infos.size());
				CommonProgress.closeProgressDialog(flag);
				info += " 任务完成,共用时:" + (System.currentTimeMillis() - beginTime)
						+ " 毫秒 ";
				JOptionPane.showMessageDialog(DgImgOrgUseInfo.this, info, "提示",
						2);

				//
				// 显示计算结果
				//
				new DgImgOrgUseMiddleInfo(infos, imgOrgUseInfo)
						.setVisible(true);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgImgOrgUseInfo.this,
						"计算中间过程的记录失败！！！" + e.getMessage(), "提示", 2);
			}
			btnMiddleInfo.setEnabled(true);
		}
	}

	/**
	 * 设置状态
	 * 
	 */
	private void setState() {
		int index = jTabbedPane.getSelectedIndex();

		btnMiddleInfo.setEnabled(index == 0);
		btnPrint.setEnabled(index != 2);
		cbbScmCoc.setEnabled(index == 3);
		lbScmCoc.setEnabled(index == 3);

		boolean flag1And2 = index == 0 || index == 1;
		btnReloadSearch.setEnabled(flag1And2);
		btnSumMoney.setEnabled(flag1And2);
		tfFillPerson.setEnabled(flag1And2);
		cbOverMillion.setEnabled(flag1And2);
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
			btnDelete.setText("隐藏");
			btnDelete.setToolTipText("打印年审报表时，不显示隐藏的商品，重新获取或关闭后后恢复");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = null;
					if (jTabbedPane.getSelectedIndex() == 0) {
						 list = tableModel.getCurrentRows();
						 tableModel.deleteRows(list);
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						 list = tableModelCustomsformation.getCurrentRows();
						 tableModelCustomsformation.deleteRows(list);
					} else if (jTabbedPane.getSelectedIndex() == 2) {
						 list = tableModelCompareBillDeclaration.getCurrentRows();
						 tableModelCompareBillDeclaration.deleteRows(list);
					} else {
						list = tableModelReceivingCarryComparison.getCurrentRows();
						tableModelReceivingCarryComparison.deleteRows(list);
					}
				}
			});
		}
		return btnDelete;
	}

	// (
	// /**
	// * 线程运行方法
	// */
	// public void run() {
	// //
	// // 用于标识这个对话框的ID
	// //
	// UUID uuid = UUID.randomUUID();
	// final String flag = uuid.toString();
	// try {
	// btnMiddleInfo.setEnabled(false);
	//
	// String info = "";
	// long beginTime = System.currentTimeMillis();
	// ProgressTask progressTask = new ProgressTask() {
	// protected void setClientTipMessage() {
	// ToolsAction toolsAction = (ToolsAction) CommonVars
	// .getApplicationContext().getBean("toolsAction");
	// ProgressInfo progressInfo = toolsAction
	// .getProgressInfo(flag);
	// if (progressInfo != null
	// && progressInfo.getHintMessage() != null) {
	// CommonProgress.setMessage(flag, progressInfo
	// .getHintMessage());
	// }
	// }
	// };
	// CommonProgress.showProgressDialog(flag, DgImgOrgUseInfo.this,
	// false, progressTask, 5000);
	// CommonProgress.setMessage(flag, "系统正在计算中间过程的记录，请稍后...");
	//
	// List<TempImgOrgUseInfo> infos = casAction
	// .findTempImgOrgUseInfo(new Request(CommonVars
	// .getCurrUser()), cbbBeginDate.getDate(),
	// cbbEndDate.getDate(), imgOrgUseInfo, flag);
	// System.out.println("infos size = " + infos.size());
	// CommonProgress.closeProgressDialog(flag);
	// info += " 任务完成,共用时:" + (System.currentTimeMillis() - beginTime)
	// + " 毫秒 ";
	// JOptionPane.showMessageDialog(DgImgOrgUseInfo.this, info, "提示",
	// 2);
	//
	// //
	// // 显示计算结果
	// //
	// new DgImgOrgUseMiddleInfo(infos, imgOrgUseInfo)
	// .setVisible(true);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// CommonProgress.closeProgressDialog(flag);
	// JOptionPane.showMessageDialog(DgImgOrgUseInfo.this,
	// "计算中间过程的记录失败！！！" + e.getMessage(), "提示", 2);
	// }
	// btnMiddleInfo.setEnabled(true);
	// }
	// }
	//
	// /**
	// * 设置状态
	// *
	// */
	// private void setState() {
	// int index = jTabbedPane.getSelectedIndex();
	//
	// btnMiddleInfo.setEnabled(index == 0);
	// btnPrint.setEnabled(index != 2);
	// cbbScmCoc.setEnabled(index == 3);
	// lbScmCoc.setEnabled(index == 3);
	//
	// boolean flag1And2 = index == 0 || index == 1;
	// btnReloadSearch.setEnabled(flag1And2);
	// btnSumMoney.setEnabled(flag1And2);
	// tfFillPerson.setEnabled(flag1And2);
	// cbOverMillion.setEnabled(flag1And2);
	// lbPerson.setEnabled(flag1And2);
	//
	// if (index == 0) {
	// btnReloadSearch.setText("报表计算");
	// btnSearch.setText("查看历史资料");
	// } else if (index == 1) {
	// btnReloadSearch.setText("重新获取");
	// btnSearch.setText("查看历史资料");
	// } else {
	// btnSearch.setText("查询");
	// }
	// })

}  //  @jve:decl-index=0:visual-constraint="10,10"
