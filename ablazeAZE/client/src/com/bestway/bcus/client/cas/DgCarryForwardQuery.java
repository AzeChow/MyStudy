/*
 * Created on 2004-9-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.CarryForwardInfo;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author 陈海鹏
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCarryForwardQuery extends JDialogBase {

	private JPanel					jPanel					= null;
	private JPanel					jPanel1					= null;
	private JTable					jTable					= null;
	private JScrollPane				jScrollPane				= null;
	private JSplitPane				jSplitPane				= null;
	private JLabel					jLabel					= null;
	private JLabel					jLabel1					= null;
	private JLabel					jLabel2					= null;
	private JLabel					jLabel3					= null;
	private JCheckBox				cbIsToday				= null;
	private JButton					btnQuery				= null;
	private JButton					btnPrint				= null;
	private JCalendarComboBox		cmbEndDate				= null;
	private JComboBox				cmbType					= null;
	private JComboBox				cbbWareSet				= null;
	private MaterialManageAction materialManageAction = null;
	private CasAction				casAction				= null;
	private JTableListModel			tableModel				= null;
	private JTextField				tfHsName				= null;
	private JButton					btnHsName				= null;
	private JButton					btnExit					= null;
	private JLabel					jLabel4					= null;
	private JTextField				tfHsSpec				= null;
	private JButton					btnHsSpec				= null;
	private Integer					maximumFractionDigits	= CasCommonVars
																	.getOtherOption()
																	.getInOutMaximumFractionDigits() == null ? 6
																	: CasCommonVars
																			.getOtherOption()
																			.getInOutMaximumFractionDigits();

	/**
	 * This method initializes
	 * 
	 */
	public DgCarryForwardQuery() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {

			initUIComponents();

		}
		super.setVisible(isFlag);
	}

	private void initUIComponents() {
		// 初始化仓库
		List list = materialManageAction.findWareSet(new Request(CommonVars
				.getCurrUser(),true));
		this.cbbWareSet.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbWareSet.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbWareSet, "code", "name");
		this.cbbWareSet.setSelectedItem(null);
		//
		// 初始化cmbType
		//
		cmbType.addItem(new ItemProperty(MaterielType.MATERIEL, "料件"));
		cmbType.addItem(new ItemProperty(MaterielType.FINISHED_PRODUCT, "成品"));
		cmbType.setSelectedIndex(0);
		//
		// 初始化日期
		//
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date());
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			cmbEndDate.setDate(sdf1.parse(year + "-12-31"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		initTable(new ArrayList());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("成品，原材料结转明细账查询");
		this.setContentPane(getJPanel());
		this.setSize(733, 541);

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(377, 35, 53, 20));
			jLabel4.setText("报关规格");
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jLabel.setText("日期到");
			jLabel.setBounds(12, 8, 36, 18);
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel1.setText("仓库");
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel1.setLocation(180, 7);
			jLabel1.setSize(50, 20);
			jLabel2.setBounds(12, 35, 36, 20);
			jLabel2.setText("类型");
			jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel3.setText("报关名称");
			jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel3.setLocation(181, 35);
			jLabel3.setSize(50, 20);
			jPanel1.add(jLabel, null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(getCbIsToday(), null);
			jPanel1.add(getBtnQuery(), null);
			jPanel1.add(getBtnPrint(), null);
			jPanel1.add(getCmbEndDate(), null);
			jPanel1.add(getCmbType(), null);
			jPanel1.add(getCmbWareSet(), null);
			jPanel1.add(getTfName(), null);
			jPanel1.add(getBtnHsName(), null);
			jPanel1.add(getBtnExit(), null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(getTfHsSpec(), null);
			jPanel1.add(getBtnHsSpec(), null);
		}
		return jPanel1;
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
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(70);
			jSplitPane.setDividerSize(1);
			jSplitPane.setTopComponent(getJPanel1());
			jSplitPane.setBottomComponent(getJScrollPane());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes cbIsToday
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsToday() {
		if (cbIsToday == null) {
			cbIsToday = new JCheckBox();
			cbIsToday.setBounds(372, 7, 74, 21);
			cbIsToday.setText("是否当日");
		}
		return cbIsToday;
	}

	/**
	 * This method initializes btnQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setLocation(544, 7);
			btnQuery.setSize(66, 21);
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List conditions = getConditions();
					if (conditions == null) {
						return;
					}
					String type = ((ItemProperty) cmbType.getSelectedItem())
							.getCode();
					List carryForwardInfos = casAction.findCarryForwardInfos(
							new Request(CommonVars.getCurrUser()), type,
							conditions);
					tableModel.setList(carryForwardInfos);
					if (carryForwardInfos.isEmpty()) {
						JOptionPane.showMessageDialog(DgCarryForwardQuery.this,
								"没有查到符合条件的记录！", "提示！",
								JOptionPane.INFORMATION_MESSAGE);
					}

				}
			});
		}
		return btnQuery;
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
			btnPrint.setLocation(544, 33);
			btnPrint.setSize(66, 21);
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List reportMasters = tableModel.getList();

					CommonDataSource masterDS = new CommonDataSource(
							reportMasters,maximumFractionDigits);

					InputStream masterReportStream = DgConsignQuery.class
							.getResourceAsStream("report/CarryForwardDetail.jasper");

					try {
						Map<String, Object> parameters = new HashMap<String, Object>();
						String unitName = "";
						String weightUnitName = "";
						if (tableModel.getList().size() > 0) {
							unitName = ((CarryForwardInfo) tableModel.getList()
									.get(0)).getHsUnit().getName();
							if (((CarryForwardInfo) tableModel.getList().get(0))
									.getHsWeightUnit() != null) {
								unitName = ((CarryForwardInfo) tableModel
										.getList().get(0)).getHsWeightUnit()
										.getName();
							}
						}
						parameters.put("nameSpec", tfHsName.getText()
								+ tfHsSpec.getText());
						parameters.put("unitName", unitName);
						parameters.put("weightUnitName", weightUnitName);
						String type = ((ItemProperty) cmbType.getSelectedItem())
								.getCode();
						if (type.equals(MaterielType.MATERIEL)) {
							parameters.put("title", "料件结转明细帐(转入方使用)");
							parameters.put("type", "收货");
						} else {
							parameters.put("title", "成品结转明细帐(转出方使用)");
							parameters.put("type", "送货");
						}
						JasperPrint jasperPrint;

						jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, masterDS);
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
	 * This method initializes cmbEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCmbEndDate() {
		if (cmbEndDate == null) {
			cmbEndDate = new JCalendarComboBox();
			cmbEndDate.setBounds(52, 8, 105, 20);
		}
		return cmbEndDate;
	}

	/**
	 * This method initializes cmbType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCmbType() {
		if (cmbType == null) {
			cmbType = new JComboBox();
			cmbType.setSelectedItem(null);
			cmbType.setBounds(52, 35, 105, 20);
			cmbType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (ItemEvent.SELECTED == e.getStateChange()) {

					}
				}
			});
		}
		return cmbType;
	}

	/**
	 * This method initializes cmbWareSet
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCmbWareSet() {
		if (cbbWareSet == null) {
			cbbWareSet = new JComboBox();
			cbbWareSet.setBounds(236, 7, 125, 21);
			cbbWareSet.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (ItemEvent.SELECTED == e.getStateChange()) {

					}
				}
			});
		}
		return cbbWareSet;
	}

	private List getConditions() {
		List<Condition> conditions = new ArrayList<Condition>();
		if (cmbEndDate.getDate().equals(new Date(0))) {
			JOptionPane.showMessageDialog(this, "单据截止日期不可为空！");
			return null;
		}
		// if (cbbWareSet.getSelectedIndex() == -1) {
		// JOptionPane.showMessageDialog(this, "仓库不可为空！");
		// return null;
		// }
		if ("".equals(tfHsName.getText())) {
			JOptionPane.showMessageDialog(this, "报关名称不可为空");
			return null;
		} else {
			conditions.add(new Condition("and", null, "hsName", " = ", tfHsName
					.getText(), null));
		}
		String spec = this.tfHsSpec.getText();
		if (spec.equals("")) {
			conditions.add(new Condition("and", "(", "hsSpec", " is null",
					null, null));
			conditions
					.add(new Condition("or", null, "hsSpec", " = ", spec, ")"));
		} else {
			conditions.add(new Condition("and", null, "hsSpec", " = ", spec,
					null));
		}
		conditions.add(new Condition("and", null, "billMaster.validDate", "<=",
				cmbEndDate.getDate(), null));

		String materielType = ((ItemProperty) cmbType.getSelectedItem())
				.getCode();
		if (materielType.equals(MaterielType.MATERIEL)) {// 料件,结转进口
			conditions.add(new Condition("and", null,
					"billMaster.billType.code", "=", "1004", null));
		} else { // 成品,结转出口
			conditions.add(new Condition("and", null,
					"billMaster.billType.code", "=", "2102", null));
		}
		return conditions;
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();

						list.add(addColumn("客户全称", "scmCoc.name", 100));
						list.add(addColumn("收货起止日期", "validDate", 80));
						list.add(addColumn("凭证号", "billNo", 80));
						list.add(addColumn("收货数量", "hsAmount", 80));
						list.add(addColumn("收货重量", "weight", 80));
						list.add(addColumn("累积数量", "accAmount", 80));

						list.add(addColumn("结转数量", "carryForwardAmount", 80));
						list.add(addColumn("累积结转数量", "acccarryForwardAmount",
								80));

						list.add(addColumn("收货未结转数量", "unCarryForwardAmount",
								80));
						list.add(addColumn("结转未收货数量",
								"exceedCarryForwardAmount", 80));

						return list;
					}
				});
	}

	/**
	 * This method initializes tfName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfHsName == null) {
			tfHsName = new JTextField();
			tfHsName.setBounds(new java.awt.Rectangle(238, 35, 106, 20));
		}
		return tfHsName;
	}

	/**
	 * This method initializes btnName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnHsName() {
		if (btnHsName == null) {
			btnHsName = new JButton();
			btnHsName.setBounds(new java.awt.Rectangle(345, 35, 17, 19));
			btnHsName.setText("...");
			btnHsName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materielType = ((ItemProperty) cmbType
							.getSelectedItem()).getCode();
					Object object = CasQuery.getInstance()
							.findHsNameFromStatCusNameRelationHsn(materielType);
					if (object != null) {
						tfHsName.setText((String) ((TempObject) object)
								.getObject());
						tfHsSpec.setText((String) ((TempObject) object)
								.getObject1());
					}
				}
			});
		}
		return btnHsName;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(new java.awt.Rectangle(619, 7, 68, 21));
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCarryForwardQuery.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes tfSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsSpec() {
		if (tfHsSpec == null) {
			tfHsSpec = new JTextField();
			tfHsSpec.setBounds(new java.awt.Rectangle(431, 35, 79, 20));
		}
		return tfHsSpec;
	}

	/**
	 * This method initializes btnSpec
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnHsSpec() {
		if (btnHsSpec == null) {
			btnHsSpec = new JButton();
			btnHsSpec.setBounds(new java.awt.Rectangle(510, 35, 18, 19));
			btnHsSpec.setText("...");
			btnHsSpec.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materielType = ((ItemProperty) cmbType
							.getSelectedItem()).getCode();
					Object object = CasQuery.getInstance()
							.findHsSpecFromStatCusNameRelationHsn(materielType,
									tfHsName.getText());
					if (object != null) {
						tfHsSpec.setText((String) ((TempObject) object)
								.getObject());
					}
				}
			});
		}
		return btnHsSpec;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
