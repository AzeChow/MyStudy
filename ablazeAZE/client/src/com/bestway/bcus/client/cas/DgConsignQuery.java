package com.bestway.bcus.client.cas;

import java.awt.BorderLayout;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.StatCusNameRelationMt;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @？
 * 委外加工外发进出仓账
 * 2010年2月6日贺巍添加注释
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgConsignQuery extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JPanel pnMain = null;
	private JCalendarComboBox cbbStartDate = null;
	private JCalendarComboBox cbbEndDate = null;
	private JSplitPane spnMain = null;
	private JPanel pnBottom = null;
	private JPanel pnTop = null;
	private JComboBox cbbCustomer = null;
	private JComboBox cbbWareSet = null;
	private JTextField tfName = null;
	private JTable tbMain = null;
	private JScrollPane spnTb = null;
	private JButton btnQuery = null;
	private JButton btnPrint = null;
	private JButton btnExit = null;
	private CasAction casAction = null;
	/**
	 * 物料类型
	 */
	private String materialType = MaterielType.MATERIEL;
	private JTableListModel tableModel = null;
	/**
	 * 数据源
	 */
	private List impExpInfos = null;
	private MaterialManageAction materialManageAction = null;
	private JButton btnName = null;
	private JLabel jLabel11 = null;
	private JTextField tfPtNo = null;
	private JButton btnPtNo = null;
	private JLabel jLabel12 = null;
	private JTextField tfEndPtNo = null;
	private JButton btnEndPtNo = null;
	/**
	 * 小数位控制参数
	 */
	private Integer maximumFractionDigits = CasCommonVars.getOtherOption()
			.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
			.getOtherOption().getInOutMaximumFractionDigits();  //  @jve:decl-index=0:

	/**
	 * 构造函数
	 * This is the default constructor
	 */
	public DgConsignQuery() {
		super(false);
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();
		initUIComponents();
	}

	/**
	 * 初始化组件
	 */
	private void initUIComponents() {
		fillCustomer();
		fillWareSet();

		initTable(new ArrayList());

		int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
				.getYearInt();
		Calendar beginClarendar = Calendar.getInstance();
		beginClarendar.set(Calendar.YEAR, year);
		beginClarendar.set(Calendar.MONTH, 0);
		beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
		this.cbbStartDate.setDate(beginClarendar.getTime());
		this.cbbStartDate.setCalendar(beginClarendar);

		Calendar endClarendar = Calendar.getInstance();
		endClarendar.set(Calendar.YEAR, year);
		endClarendar.set(Calendar.MONTH, 11);
		endClarendar.set(Calendar.DAY_OF_MONTH, 31);
		this.cbbEndDate.setDate(endClarendar.getTime());
		this.cbbEndDate.setCalendar(endClarendar);

	}

	/**
	 * 初始化标题和尺寸
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("委外加工外发进出仓帐");
		this.setSize(733, 541);
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
			jContentPane.add(getPnMain(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes pnMain
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnMain() {
		if (pnMain == null) {
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel = new JLabel();
			pnMain = new JPanel();
			pnMain.setLayout(new BorderLayout());
			pnMain.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0,
					0, 0));
			jLabel.setText("0");
			jLabel.setBounds(3, 34, 37, 32);
			jLabel1.setText("JLabel");
			jLabel1.setBounds(3, 65, 37, 32);
			jLabel2.setText("JLabel");
			jLabel2.setBounds(3, 96, 37, 32);
			jLabel3.setText("JLabel");
			jLabel3.setBounds(3, 1, 37, 32);
			jLabel4.setText("日期 从");
			pnMain.add(getSpnMain(), java.awt.BorderLayout.CENTER);
		}
		return pnMain;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (cbbStartDate == null) {
			cbbStartDate = new JCalendarComboBox();
			cbbStartDate.setBounds(302, 14, 141, 21);
		}
		return cbbStartDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setLocation(468, 14);
			cbbEndDate.setSize(147, 21);
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes spnMain
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getSpnMain() {
		if (spnMain == null) {
			spnMain = new JSplitPane();
			spnMain.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			spnMain.setDividerSize(1);
			spnMain.setDividerLocation(98);
			spnMain.setTopComponent(getPnTop());
			spnMain.setBottomComponent(getPnBottom());
		}
		return spnMain;
	}

	/**
	 * This method initializes pnBottom
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnBottom() {
		if (pnBottom == null) {
			pnBottom = new JPanel();
			pnBottom.setLayout(new BorderLayout());
			pnBottom.add(getSpnTb(), java.awt.BorderLayout.CENTER);
		}
		return pnBottom;
	}

	/**
	 * This method initializes pnTop
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTop() {
		if (pnTop == null) {
			jLabel12 = new JLabel();
			jLabel12.setBounds(new java.awt.Rectangle(449, 39, 15, 19));
			jLabel12.setText("止");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new java.awt.Rectangle(238, 39, 64, 20));
			jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel11.setText("工厂料号从");
			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			pnTop = new JPanel();
			pnTop.setLayout(null);
			jLabel7.setText("客户名称");
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel7.setBounds(24, 17, 64, 20);
			jLabel8.setText("仓库");
			jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel8.setBounds(24, 38, 64, 20);
			jLabel9.setText("工厂商品名称");
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setBounds(14, 60, 74, 20);
			jLabel10.setText("日期 从");
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setBounds(253, 16, 49, 18);
			pnTop
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"过滤条件",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.PLAIN, 12),
									new java.awt.Color(51, 51, 51)));
			jLabel5.setBounds(449, 14, 16, 20);
			jLabel5.setText("止");
			pnTop.add(getJComboBox1(), null);
			pnTop.add(getJComboBox2(), null);
			pnTop.add(getJComboBox3(), null);
			pnTop.add(jLabel7, null);
			pnTop.add(jLabel8, null);
			pnTop.add(jLabel9, null);
			pnTop.add(getJCalendarComboBox(), null);
			pnTop.add(getJCalendarComboBox1(), null);
			pnTop.add(getJButton(), null);
			pnTop.add(getBtnPrint(), null);
			pnTop.add(getJButton2(), null);
			pnTop.add(jLabel10, null);
			pnTop.add(jLabel5, null);
			pnTop.add(getBtnName(), null);
			pnTop.add(jLabel11, null);
			pnTop.add(getTfPtNo(), null);
			pnTop.add(getBtnPtNo(), null);
			pnTop.add(jLabel12, null);
			pnTop.add(getTfEndPtNo(), null);
			pnTop.add(getJButton3(), null);
		}
		return pnTop;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(93, 17, 147, 20);
		}
		return cbbCustomer;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox2() {
		if (cbbWareSet == null) {
			cbbWareSet = new JComboBox();
			cbbWareSet.setBounds(93, 38, 147, 20);
		}
		return cbbWareSet;
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JTextField getJComboBox3() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(93, 60, 128, 20);
		}
		return tfName;
	}

	/**
	 * This method initializes tbMain
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbMain() {
		if (tbMain == null) {
			tbMain = new JTable() {
				protected JTableHeader createDefaultTableHeader() {
					return new GroupableTableHeader(columnModel);
				}
			};
		}
		return tbMain;
	}

	/**
	 * This method initializes spnTb
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpnTb() {
		if (spnTb == null) {
			spnTb = new JScrollPane();
			spnTb.setViewportView(getTbMain());
		}
		return spnTb;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setBounds(632, 14, 65, 23);
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<Condition> conditions = getConditions();
					List<Condition> condition = getCondition();
					List<Condition> conditionss = getConditionss();
					new searchThread(conditions, condition, conditionss)
							.start();
				}
			});
		}
		return btnQuery;
	}

	/**
	 * 查询多线程类
	 * @author hw
	 *
	 */
	class searchThread extends Thread {
		/**
		 * 查询条件
		 * @return list
		 */
		List<Condition> conditions = null;

		/**
		 * 获取查询条件
		 * 
		 */
		List<Condition> condition = null;

		/**
		 *查询条件
		 * @return list
		 */
		List<Condition> conditionss = null;

		/**
		 * 构造函数
		 * @param conditions
		 * @param condition
		 * @param conditionss
		 */
		public searchThread(List<Condition> conditions,
				List<Condition> condition, List<Condition> conditionss) {
			this.conditions = conditions;
			this.condition = condition;
			this.conditionss = conditionss;
		}

		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			btnQuery.setEnabled(false);
			try {
				CommonProgress.showProgressDialog(flag, DgConsignQuery.this);
				CommonProgress.setMessage(flag, "系统正获取数据，请稍后...");
				impExpInfos = casAction.getResultWaiFa(new Request(CommonVars
						.getCurrUser(), true), conditions, condition,conditionss);
				CommonProgress.closeProgressDialog(flag);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgConsignQuery.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				if (impExpInfos != null && !impExpInfos.isEmpty()) {
					tableModel.setList(impExpInfos);
				} else {
					initTable(new ArrayList());
					JOptionPane.showMessageDialog(DgConsignQuery.this,
							"没有查到符合条件的记录！", "提示！", 2);
				}
			}
			btnQuery.setEnabled(true);
		}
	}

	/**
	 * 获取查询条件
	 * @return list
	 */
	private List getConditions() {
		List<Condition> conditions = new ArrayList<Condition>();

		if (cbbCustomer.getSelectedItem() != null) {
			conditions.add(new Condition("and", null, "billMaster.scmCoc", "=",
					cbbCustomer.getSelectedItem(), null));
		}
		if (cbbWareSet.getSelectedItem() != null) {
			conditions.add(new Condition("and", null, "wareSet", "=",
					cbbWareSet.getSelectedItem(), null));
		}

		if (tfName.getText() != null && !tfName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "ptName", " = ", tfName
					.getText(), null));
		}
		if (cbbStartDate.getDate() != null) {
			conditions.add(new Condition("and", null, "billMaster.validDate",
					">=", cbbStartDate.getDate(), null));
		}
		if (cbbEndDate.getDate() != null) {
			conditions.add(new Condition("and", null, "billMaster.validDate",
					"<=", cbbEndDate.getDate(), null));
		}
		// 工厂料号不等于空,结束料号为空时
		if (!tfPtNo.getText().trim().equals("")
				&& tfEndPtNo.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "ptPart", "=", tfPtNo
					.getText(), null));
		} else // 工厂料号不等于空,结束料号不为空时
		if (!tfPtNo.getText().trim().equals("")
				&& !tfEndPtNo.getText().trim().equals("")) {
			conditions.add(new Condition("and", "(", "ptPart", ">=", tfPtNo
					.getText(), null));
			conditions.add(new Condition("and", null, "ptPart", "<=", tfEndPtNo
					.getText(), ")"));
		}
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		return conditions;
	}

	/**
	 * 获取查询条件
	 * @return list
	 */
	private List getCondition() {
		List<Condition> condition = new ArrayList<Condition>();

		if (cbbCustomer.getSelectedItem() != null) {
			condition.add(new Condition("and", null, "billMaster.scmCoc", "=",
					cbbCustomer.getSelectedItem(), null));
		}
		if (cbbWareSet.getSelectedItem() != null) {
			condition.add(new Condition("and", null, "wareSet", "=", cbbWareSet
					.getSelectedItem(), null));
		}

		if (tfName.getText() != null && !tfName.getText().trim().equals("")) {
			condition.add(new Condition("and", null, "ptName", " = ", tfName
					.getText(), null));
		}
		if (cbbStartDate.getDate() != null) {
			condition.add(new Condition("and", null, "billMaster.validDate",
					">=", cbbStartDate.getDate(), null));
		}
		if (cbbEndDate.getDate() != null) {
			condition.add(new Condition("and", null, "billMaster.validDate",
					"<=", cbbEndDate.getDate(), null));
		}
		// 工厂料号不等于空,结束料号为空时
		if (!tfPtNo.getText().trim().equals("")
				&& tfEndPtNo.getText().trim().equals("")) {
			condition.add(new Condition("and", null, "ptPart", "=", tfPtNo
					.getText(), null));
		} else // 工厂料号不等于空,结束料号不为空时
		if (!tfPtNo.getText().trim().equals("")
				&& !tfEndPtNo.getText().trim().equals("")) {
			condition.add(new Condition("and", "(", "ptPart", ">=", tfPtNo
					.getText(), null));
			condition.add(new Condition("and", null, "ptPart", "<=", tfEndPtNo
					.getText(), ")"));
		}
		condition.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		return condition;
	}

	/**
	 * 获取查询条件
	 * @return list
	 */
	private List getConditionss() {
		List<Condition> conditionss = new ArrayList<Condition>();

		if (cbbCustomer.getSelectedItem() != null) {
			conditionss.add(new Condition("and", null, "billMaster.scmCoc",
					"=", cbbCustomer.getSelectedItem(), null));
		}
		if (cbbWareSet.getSelectedItem() != null) {
			conditionss.add(new Condition("and", null, "wareSet", "=",
					cbbWareSet.getSelectedItem(), null));
		}

		if (tfName.getText() != null && !tfName.getText().trim().equals("")) {
			conditionss.add(new Condition("and", null, "ptName", " = ", tfName
					.getText(), null));
		}
		if (cbbStartDate.getDate() != null) {
			conditionss.add(new Condition("and", null, "billMaster.validDate",
					">=", cbbStartDate.getDate(), null));
		}
		if (cbbEndDate.getDate() != null) {
			conditionss.add(new Condition("and", null, "billMaster.validDate",
					"<=", cbbEndDate.getDate(), null));
		}
		// 工厂料号不等于空,结束料号为空时
		if (!tfPtNo.getText().trim().equals("")
				&& tfEndPtNo.getText().trim().equals("")) {
			conditionss.add(new Condition("and", null, "ptPart", "=", tfPtNo
					.getText(), null));
		} else // 工厂料号不等于空,结束料号不为空时
		if (!tfPtNo.getText().trim().equals("")
				&& !tfEndPtNo.getText().trim().equals("")) {
			conditionss.add(new Condition("and", "(", "ptPart", ">=", tfPtNo
					.getText(), null));
			conditionss.add(new Condition("and", null, "ptPart", "<=",
					tfEndPtNo.getText(), ")"));
		}
		conditionss.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		return conditionss;
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
			btnPrint.setBounds(632, 39, 65, 23);
			btnPrint.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (impExpInfos == null || impExpInfos.isEmpty()) {
						JOptionPane.showMessageDialog(DgConsignQuery.this,
								"没有列印的记录！", "提示！",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					btnPrint.setEnabled(false);
					try {
						CommonDataSource imgExgDS = new CommonDataSource(
								impExpInfos, maximumFractionDigits);
						List<BaseCompany> company = new ArrayList<BaseCompany>();
						company.add(CommonVars.getCurrUser().getCompany());
						InputStream masterReportStream = DgConsignQuery.class
								.getResourceAsStream("report/CustomInoutWareReportWaiFaConsignQuery.jasper");
						Map<String, Object> parameters = new HashMap<String, Object>();
						// if (materialType == MaterielType.MATERIEL) {
						parameters.put("title", "委外加工外发进出仓帐");
						// } else if (materialType ==
						// MaterielType.FINISHED_PRODUCT) {
						// parameters.put("title", "成品进出仓帐");
						// } else if (materialType == MaterielType.MACHINE) {
						// parameters.put("title", "设备进出仓帐");
						// } else if (materialType ==
						// MaterielType.REMAIN_MATERIEL) {
						// parameters.put("title", "边角料进出仓帐");
						// } else if (materialType == MaterielType.BAD_PRODUCT)
						// {
						// parameters.put("title", "残次品进出仓帐");
						// }
						JasperPrint jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, imgExgDS);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
					btnPrint.setEnabled(true);
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
	private JButton getJButton2() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setBounds(632, 64, 65, 23);
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 初始化客户下拉框
	 */
	private void fillCustomer() {
		cbbCustomer.removeAllItems();
		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbCustomer.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCustomer, "code", "name", 270);
		this.cbbCustomer.setSelectedItem(null);
	}

	/**
	 * 初始化仓库下拉菜单
	 */
	private void fillWareSet() {
		cbbWareSet.removeAllItems();
		List wareSets = this.materialManageAction.findWareSet(new Request(
				CommonVars.getCurrUser(), true));
		this.cbbWareSet.setModel(new DefaultComboBoxModel(wareSets.toArray()));
		this.cbbWareSet.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbWareSet, "code", "name", 270);
		this.cbbWareSet.setSelectedItem(null);
	}

	/**
	 * 初始化主表
	 * @param list
	 */
	private void initTable(final List list) {
		tableModel = new JTableListModel(tbMain, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("BOM编号(料号)", "ptPart", 100));
						list.add(addColumn("工厂商品名称", "ptName", 150));
						list.add(addColumn("工厂规格型号", "ptSpec", 120));
						list.add(addColumn("进出仓日期", "date", 100));
						list.add(addColumn("单据类型", "billType.name", 100));

						list.add(addColumn("凭证号", "impBillNo", 120));
						list.add(addColumn("工厂数量", "impPtAmount", 60));
						list.add(addColumn("海关数量", "impHsAmount", 60));

						list.add(addColumn("凭证号", "expBillNo", 120));
						list.add(addColumn("工厂数量", "expPtAmount", 60));
						list.add(addColumn("海关数量", "expHsAmount", 60));

						list.add(addColumn("工厂数量", "rsPtAmount", 60));
						list.add(addColumn("海关数量", "rsHsAmount", 80));

						list.add(addColumn("制单号", "productNo", 150));
						list.add(addColumn("收送货商名称", "scmCoc.name", 100));

						list.add(addColumn("商品序号", "seqNum", 100));

						list.add(addColumn("工厂单位", "ptUnit.name", 60));

						list.add(addColumn("海关单位", "hsUnit.name", 100));
						list.add(addColumn("仓库", "wareSet.name", 80));
						list.add(addColumn("备注", "note", 100));

						return list;
					}
				});

		TableColumnModel cm = tbMain.getColumnModel();
		ColumnGroup g_in = new ColumnGroup("出仓");
		g_in.add(cm.getColumn(6));
		g_in.add(cm.getColumn(7));
		g_in.add(cm.getColumn(8));

		ColumnGroup g_out = new ColumnGroup("进仓");
		g_out.add(cm.getColumn(9));
		g_out.add(cm.getColumn(10));
		g_out.add(cm.getColumn(11));

		ColumnGroup g_rs = new ColumnGroup("结存");
		g_rs.add(cm.getColumn(12));
		g_rs.add(cm.getColumn(13));

		GroupableTableHeader header = (GroupableTableHeader) tbMain
				.getTableHeader();
		header.setColumnModel(tbMain.getColumnModel());
		header.addColumnGroup(g_in);
		header.addColumnGroup(g_out);
		header.addColumnGroup(g_rs);
		tbMain.repaint();
	}

	/**
	 * This method initializes btnName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnName() {
		if (btnName == null) {
			btnName = new JButton();
			btnName.setBounds(new java.awt.Rectangle(221, 60, 19, 20));
			btnName.setText("...");
			btnName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object object = CasQuery.getInstance()
							.findPtNameFromBillDetail(materialType);
					if (object != null) {
						tfName.setText((String) ((TempObject) object)
								.getObject());
					}
				}
			});
		}
		return btnName;
	}

	/**
	 * This method initializes tfPtNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtNo() {
		if (tfPtNo == null) {
			tfPtNo = new JTextField();
			tfPtNo.setBounds(new java.awt.Rectangle(302, 39, 124, 20));
			tfPtNo.getDocument().addDocumentListener(new DocumentListener() {
				/**
				 * 设置组建状态
				 */
				private void setState() {
					String editAfterQueryValue = tfPtNo.getText().trim();
					if (!"".equalsIgnoreCase(editAfterQueryValue)) {
						tfEndPtNo.setEditable(true);
						btnEndPtNo.setEnabled(true);
					} else {
						tfEndPtNo.setEditable(false);
						btnEndPtNo.setEnabled(false);
					}
				}

				/**
				 * 插入更新
				 */
				public void insertUpdate(DocumentEvent e) {
					setState();
				}

				/**
				 * 删除更新
				 */
				public void removeUpdate(DocumentEvent e) {
					setState();
				}

				/**
				 * 改变更新 
				 */
				public void changedUpdate(DocumentEvent e) {
					setState();
				}

			});
		}
		return tfPtNo;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPtNo() {
		if (btnPtNo == null) {
			btnPtNo = new JButton();
			btnPtNo.setBounds(new java.awt.Rectangle(424, 39, 19, 20));
			btnPtNo.setText("...");
			btnPtNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<StatCusNameRelationMt> list = CasQuery.getInstance()
							.getStatCusNameRelationMt(false, materialType,
									new ArrayList());
					if (list != null && list.size() > 0) {
						StatCusNameRelationMt sm = list.get(0);
						Materiel m = sm.getMateriel();
						tfPtNo.setText(m == null ? "" : m.getPtNo());
					}
				}
			});
		}
		return btnPtNo;
	}

	/**
	 * This method initializes tfEndPtNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEndPtNo() {
		if (tfEndPtNo == null) {
			tfEndPtNo = new JTextField();
			tfEndPtNo.setBounds(new java.awt.Rectangle(468, 39, 130, 20));
			tfEndPtNo.setEditable(false);
			tfEndPtNo.setEnabled(true);
		}
		return tfEndPtNo;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (btnEndPtNo == null) {
			btnEndPtNo = new JButton();
			btnEndPtNo.setBounds(new java.awt.Rectangle(598, 39, 19, 19));
			btnEndPtNo.setEnabled(false);
			btnEndPtNo.setText("...");
			btnEndPtNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<StatCusNameRelationMt> list = CasQuery.getInstance()
							.getStatCusNameRelationMt(false, materialType,
									new ArrayList());
					if (list != null && list.size() > 0) {
						StatCusNameRelationMt sm = list.get(0);
						Materiel m = sm.getMateriel();
						tfEndPtNo.setText(m == null ? "" : m.getPtNo());
					}
				}
			});
		}
		return btnEndPtNo;
	}
}
