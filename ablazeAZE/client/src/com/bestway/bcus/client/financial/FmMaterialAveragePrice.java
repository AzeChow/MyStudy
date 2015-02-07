package com.bestway.bcus.client.financial;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.financial.action.FinancialAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.DateValueListener;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author
 * 料件加权平均价
 * 
 */
public class FmMaterialAveragePrice extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;
	private JPanel pnMain = null;
	private JCalendarComboBox cbbStartDate = null;
	private JCalendarComboBox cbbEndDate = null;
	private JSplitPane spnMain = null;
	private JPanel pnBottom = null;
	private JPanel pnTop = null;
	private JTextField tfName = null;
	private JTable tbMain = null;
	private JScrollPane spnTable = null;
	private JButton btnQuery = null;
	private JButton btnReQuery = null;
	private JButton btnPrint = null;
	private JButton btnExit = null;
	private FinancialAction financialAction = null;
	/**
	 * 物料类型
	 */
	private String materialType = MaterielType.MATERIEL;
	private JTableListModel tableModel = null;
	/**
	 * 数据源
	 */
	private List impExpInfos = null;
	private JButton btnName = null;
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
	public FmMaterialAveragePrice() {
		super();
		financialAction = (FinancialAction) CommonVars.getApplicationContext().getBean("financialAction");
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
		
		Calendar currentDate = Calendar.getInstance();
		
		Calendar beginClarendar = Calendar.getInstance();
		
		beginClarendar.set(Calendar.YEAR, currentDate.get(Calendar.YEAR));
		beginClarendar.set(Calendar.MONTH, currentDate.get(Calendar.MONTH));
		beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
		this.cbbStartDate.setDate(beginClarendar.getTime());
		this.cbbStartDate.setCalendar(beginClarendar);

		Calendar endClarendar = Calendar.getInstance();
		endClarendar.set(Calendar.YEAR, currentDate.get(Calendar.YEAR));
		endClarendar.set(Calendar.MONTH, currentDate.get(Calendar.MONTH));
		endClarendar.set(Calendar.DAY_OF_MONTH, currentDate.getActualMaximum(Calendar.DAY_OF_MONTH));
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
		this.setTitle("料件加权平均价");
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
			cbbStartDate.setBounds(302, 20, 140, 20);
			cbbStartDate.addDateValueListener(new DateValueListener() {

				public void valueChanged(Date newDate) {
					if (cbbStartDate.getDate() == null) {
						return;
					}
					Calendar beginDate = cbbStartDate.getCalendar();
					Calendar endDate = null;
					if (beginDate != null) {
						endDate = (Calendar) beginDate.clone();
						endDate.set(Calendar.DAY_OF_MONTH, beginDate.getActualMaximum(Calendar.DAY_OF_MONTH));
					}
					beginDate.set(Calendar.DAY_OF_MONTH, 1);
					cbbStartDate.setCalendar(beginDate);
					cbbEndDate.setCalendar(endDate);
				}
			});
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
			cbbEndDate.setLocation(468, 20);
			cbbEndDate.setSize(145, 20);			
			cbbEndDate.addDateValueListener(new DateValueListener() {
				public void valueChanged(Date newDate) {
					if (cbbEndDate.getDate() == null) {
						return;
					}
					Calendar endDate = cbbEndDate.getCalendar();
					Calendar beginDate = null;
					if (endDate != null) {
						beginDate = (Calendar) endDate.clone();
						beginDate.set(Calendar.DAY_OF_MONTH, 1);
					}
					endDate.set(Calendar.DAY_OF_MONTH, endDate.getActualMaximum(Calendar.DAY_OF_MONTH));
					cbbStartDate.setCalendar(beginDate);
					cbbEndDate.setCalendar(endDate);
				}
			});
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
			spnMain.setDividerLocation(77);
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
			pnBottom.add(getSpnTable(), java.awt.BorderLayout.CENTER);
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
			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			pnTop = new JPanel();
			pnTop.setLayout(null);
			jLabel9.setText("料件商品名称");
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setBounds(20, 20, 78, 20);
			jLabel10.setText("日期 从");
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setBounds(253, 20, 50, 20);
			pnTop.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
				"过滤条件",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
				new java.awt.Color(51, 51, 51)))
			;
			jLabel5.setBounds(449, 20, 15, 20);
			jLabel5.setText("止");
			pnTop.add(getJComboBox3(), null);
			pnTop.add(jLabel9, null);
			pnTop.add(getJCalendarComboBox(), null);
			pnTop.add(getJCalendarComboBox1(), null);
			pnTop.add(getJButton(), null);
//			pnTop.add(getBtnReQuery(), null);
//			pnTop.add(getBtnPrint(), null);
			pnTop.add(getJButton2(), null);
			pnTop.add(jLabel10, null);
			pnTop.add(jLabel5, null);
			pnTop.add(getBtnName(), null);
		}
		return pnTop;
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JTextField getJComboBox3() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(100, 20, 128, 20);
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
	 * This method initializes spnTable
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpnTable() {
		if (spnTable == null) {
			spnTable = new JScrollPane();
			spnTable.setViewportView(getTbMain());
		}
		return spnTable;
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
			btnQuery.setBounds(473, 44, 65, 22);
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Map conditions = getConditions();
					new searchThread(conditions).start();
				}
			});
		}
		return btnQuery;
	}
	/**
	 * This method initializes btnReQuery	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnReQuery() {
		if (btnReQuery == null) {
			btnReQuery = new JButton();
			btnReQuery.setBounds(new Rectangle(338, 44, 125, 22));
			btnReQuery.setText("重新计算并保存");
		}
		return btnReQuery;
	}
	/**
	 * 查询线程类
	 * @author hw
	 *
	 */
	class searchThread extends Thread {
		//查询条件
		Map conditions = null;

		/**
		 * 构造函数
		 * @param conditions
		 * @param condition
		 * @param conditionss
		 * @param tiaojian
		 * @param tiaojian2
		 */
		public searchThread(Map conditions) {
			this.conditions = conditions;
		}

		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			btnQuery.setEnabled(false);
			try {
				CommonProgress.setMessage(flag, "系统正获取数据，请稍后...");
				impExpInfos = financialAction.findMaterialAveragePriceList(new Request(
						CommonVars.getCurrUser()), conditions);
				CommonProgress.closeProgressDialog(flag);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmMaterialAveragePrice.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				if (impExpInfos != null && !impExpInfos.isEmpty()) {
					tableModel.setList(impExpInfos);
				} else {
					initTable(new ArrayList());
					JOptionPane.showMessageDialog(FmMaterialAveragePrice.this,
							"没有查到符合条件的记录！", "提示！", 2);
				}
			}
			btnQuery.setEnabled(true);
		}
	}

	/**
	 * 获取查询条件
	 * @return
	 */
	private Map getConditions() {
		Map conditions = new HashMap();

		if (tfName.getText() != null && !tfName.getText().trim().equals("")) {
			conditions.put("hsName", tfName.getText());
		}
		if (cbbStartDate.getDate() != null) {
			conditions.put("startDate", cbbStartDate.getDate());
		}
		if (cbbEndDate.getDate() != null) {
			Date endDate = cbbEndDate.getDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.set(Calendar.HOUR, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999999);
			
			conditions.put("endDate", calendar.getTime());
		}
		return conditions;
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
			btnPrint.setBounds(473, 44, 65, 22);
			btnPrint.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (impExpInfos == null || impExpInfos.isEmpty()) {
						JOptionPane.showMessageDialog(FmMaterialAveragePrice.this,
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
						InputStream masterReportStream = FmMaterialAveragePrice.class
								.getResourceAsStream("report/CustomInoutWareReportWaiFaConsignQueryBack.jasper");
						Map<String, Object> parameters = new HashMap<String, Object>();
						// if (materialType == MaterielType.MATERIEL) {
						parameters.put("title", "原材料进出仓帐");
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
			btnExit.setBounds(548, 44, 65, 22);
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
	}

	/**
	 * 初始化仓库下拉框
	 */
	private void fillWareSet() {
	}

	/**
	 * 初始化主表格
	 * @param list
	 */
	private void initTable(final List list) {
		tableModel = new JTableListModel(tbMain, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("料件商品名称", "hsName", 180));
						list.add(addColumn("料件商品规格型号", "hsSpec", 180));
						list.add(addColumn("料件商品单位", "hsUnit.name", 180));

						list.add(addColumn("数量", "previousCount", 80));
						list.add(addColumn("总额", "previousAmount", 80));

						list.add(addColumn("数量", "thisInCount", 80));
						list.add(addColumn("总额", "thisInAmount", 80));

						list.add(addColumn("加权平均价", "averagePrice", 100));
						
						return list;
					}
				});

		TableColumnModel cm = tbMain.getColumnModel();
		
		ColumnGroup g_last = new ColumnGroup("上月结存");
		g_last.add(cm.getColumn(4));
		g_last.add(cm.getColumn(5));

		ColumnGroup g_this = new ColumnGroup("本月进入");
		g_this.add(cm.getColumn(6));
		g_this.add(cm.getColumn(7));
		
		GroupableTableHeader header = (GroupableTableHeader) tbMain.getTableHeader();
		header.setColumnModel(tbMain.getColumnModel());
		header.addColumnGroup(g_last);
		header.addColumnGroup(g_this);
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
			btnName.setBounds(new java.awt.Rectangle(228, 20, 19, 20));
			btnName.setText("...");
			btnName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = MaterielType.MATERIEL;
					Object object = CasQuery.getInstance()
							.findHsNameFromStatCusNameRelationHsn(materialType);
					if (object != null) {
						tfName.setText((String) ((TempObject) object)
								.getObject());
					}
				}
			});
		}
		return btnName;
	}

}
