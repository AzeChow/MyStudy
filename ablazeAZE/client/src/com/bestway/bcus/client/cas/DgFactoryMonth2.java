/*
 * Created on 2004-9-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolTip;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.parameterset.entity.OtherOption;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.JMultiLineToolTip;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgFactoryMonth2 extends JDialogBase {

	private javax.swing.JPanel	jContentPane	= null;

	private JSplitPane			jSplitPane		= null;
	private JPanel				jPanel1			= null;
	private JPanel				jPanel2			= null;
	private JTable				jTable			= null;
	private JScrollPane			jScrollPane		= null;

	private JLabel				jLabel			= null;
	private int					titleName		= 1;
	private JTableListModel		tableModel		= null;
	private JButton				btnSearch		= null;
	private JButton				btnPrint		= null;
	private JButton				btnExit			= null;
	private CasAction			casAction		= null;
	private String				materielType	= null;
	private JCalendarComboBox	cbbEndDate		= null;
	private List				result			= null;
	private String				reportTitle		= null;  //  @jve:decl-index=0:
	private boolean				isRefresh		= true;					// 是否决定重新保存
	private List<Integer>		notExistList	= new ArrayList<Integer>();  //  @jve:decl-index=0:
	private JComboBox			cbbMonth		= null;
	private JLabel				jLabel4			= null;
	private JButton				btnRefresh		= null;
	private OtherOption otherOption = null; // @jve:decl-index=0:
	

	/**
	 * 小数位控制
	 */
	private Integer allMaximumFractionDigits = CasCommonVars.getOtherOption()
			.getAllReportInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
			.getOtherOption().getAllReportInOutMaximumFractionDigits(); // @jve:decl-index=0:
	
	/**
	 * This is the default constructor
	 */
	public DgFactoryMonth2(String materielType) {
		super(false);
		this.materielType = materielType;
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		otherOption = CasCommonVars.getOtherOption();
		initialize();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		this.setTitle("工厂查询统计报表");
		this.setSize(754, 541);
		this.setContentPane(getJContentPane());
	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {
			initUIComponents();
			initTable(new ArrayList());
		}
		super.setVisible(isFlag);
	}

	private void initUIComponents() {
		jLabel = new JLabel();
		if (materielType.equals(MaterielType.MATERIEL)) {
			jLabel.setText("材料月报表");
			this.setReportTitle("材料月报表");
		} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
			jLabel.setText("成品月报表");
			this.setReportTitle("成品月报表");
		} else if (materielType.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			jLabel.setText("半成品月报表");
			this.setReportTitle("半成品月报表");
		}
		//
		// 初始化本月的最后一天
		//
		Calendar clar = Calendar.getInstance();
		clar.set(Calendar.YEAR,
				com.bestway.bcus.client.cas.parameter.CasCommonVars
						.getYearInt());
		clar.set(Calendar.DAY_OF_MONTH, clar.getActualMaximum(Calendar.DATE));
		this.cbbEndDate.setDate(clar.getTime());
		this.cbbEndDate.setCalendar(clar);
		this.cbbMonth.setSelectedItem(clar.get(Calendar.MONTH)+1);
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter(allMaximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();						
						list.add(addColumn("报关名称", "ptName", 100));// 名称
						list.add(addColumn("报关型号规格", "ptSpec", 100));// 规格
						list.add(addColumn("单位", "ptPart", 100));// 料号

						list.add(addColumn("数量", "frontMonthPtAmount", 60)); // 上月结存
						list.add(addColumn("重量", "frontMonthNetWeight", 80));

						list.add(addColumn("数量", "importPtAmount", 60)); // 本月进入
						list.add(addColumn("重量", "importNetWeight", 80));

						list.add(addColumn("数量", "exportPtAmount", 60)); // 本月发货
						list.add(addColumn("重量", "exportNetWeight", 80));

						list.add(addColumn("数量", "currentMonthPtAmount", 60)); // 本月结存
						list.add(addColumn("重量", "currentMonthNetWeight", 80)); //
						return list;
					}
				});
		TableColumnModel cm = jTable.getColumnModel();
		ColumnGroup materielName = new ColumnGroup("商品名称");
		materielName.add(cm.getColumn(1));
		materielName.add(cm.getColumn(2));
		materielName.add(cm.getColumn(3));

		ColumnGroup preSum = new ColumnGroup("上月结存");
		preSum.add(cm.getColumn(4));
		preSum.add(cm.getColumn(5));

		ColumnGroup thisInto = new ColumnGroup("本月进入");
		thisInto.add(cm.getColumn(6));
		thisInto.add(cm.getColumn(7));

		ColumnGroup thisOut = new ColumnGroup("本月发货");
		thisOut.add(cm.getColumn(8));
		thisOut.add(cm.getColumn(9));

		ColumnGroup thisSum = new ColumnGroup("本月结存");
		thisSum.add(cm.getColumn(10));
		thisSum.add(cm.getColumn(11));

		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(materielName);
		header.addColumnGroup(preSum);
		header.addColumnGroup(thisInto);
		header.addColumnGroup(thisOut);
		header.addColumnGroup(thisSum);
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}



	/**
	 * 
	 * This method initializes jSplitPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(48);
			jSplitPane.setDividerSize(2);
			jSplitPane.setTopComponent(getJPanel2());
			jSplitPane.setBottomComponent(getJPanel1());
		}
		return jSplitPane;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(162, 11, 25, 24));
			jLabel4.setText("月份");
			javax.swing.JLabel jLabel1 = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBorder(javax.swing.BorderFactory
					.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jLabel1.setBounds(11, 11, 51, 24);
			jLabel1.setText("结束日期");
			jPanel2.add(jLabel1, null);
			jPanel2.add(getCbbEndDate(), null);
			jPanel2.add(getBtnSearch(), null);
			jPanel2.add(getBtnPrint(), null);
			jPanel2.add(getBtnExit(), null);
			jPanel2.add(getCbbMonth(), null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getBtnRefresh(), null);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jTable
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new GroupableHeaderTable();
		}
		return jTable;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
			jScrollPane.setBorder(javax.swing.BorderFactory
					.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		}
		return jScrollPane;
	}

	/**
	 * @return Returns the titleName.
	 */
	public int getTitleName() {
		return titleName;
	}

	/**
	 * @param titleName
	 *            The titleName to set.
	 */
	public void setTitleName(int titleName) {
		this.titleName = titleName;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton(){
				public JToolTip createToolTip() {
					return new JMultiLineToolTip();
				}
			};
			btnSearch
					.setToolTipText("月结存数据,用于计算下个月的结存.\n如果结束日期为当月的最后一天,那么会保存,否则仅做查询而已");
			btnSearch.setBounds(286, 11, 98, 24);
			btnSearch.setText("查询并保存");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!validateData()) {
						return;
					}
					Calendar clar = cbbEndDate.getCalendar();
					int currentMonth = clar.get(Calendar.MONTH) + 1;
					// System.out.println(currentMonth);
					notExistList = casAction.findFrontMonthIsNotExist2(
							new Request(CommonVars.getCurrUser(), true),
							materielType, currentMonth);
					if (notExistList.size() > 0) {
						Collections.sort(notExistList);
						String info = "本月前的月份【 ";
						for (int i = 0; i < notExistList.size(); i++) {
							if (i == notExistList.size() - 1) {
								info += notExistList.get(i);
							} else {
								info += notExistList.get(i) + ", ";
							}
						}
						info += " 】没有结存记录 !! \n 是否要生成这些月的结存数据呢??\n\n确定要继续吗？？";
						if (JOptionPane.showConfirmDialog(DgFactoryMonth2.this,
								info, "警告!!!", JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
							return;
						}
					}
					Calendar endCalendar = DgFactoryMonth2.this.cbbEndDate
							.getCalendar();
					Calendar beginCalendar = Calendar.getInstance();
					beginCalendar.set(Calendar.YEAR, endCalendar
							.get(Calendar.YEAR));
					beginCalendar.set(Calendar.MONTH, endCalendar
							.get(Calendar.MONTH));
					beginCalendar.set(Calendar.DAY_OF_MONTH, 1);
					Date endDate = CommonVars.dateToStandDate(endCalendar
							.getTime());
					Date beginDate = CommonVars.dateToStandDate(beginCalendar
							.getTime());
					new find(beginDate, endDate).start();
				}
			});

		}
		return btnSearch;
	}

	class find extends Thread {
		Date	beginDate	= null;
		Date	endDate		= null;

		public find(Date beginDate, Date endDate) {
			this.endDate = endDate;
			this.beginDate = beginDate;
		}

		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();

			btnSearch.setEnabled(false);
			btnRefresh.setEnabled(false);
			try {
				CommonProgress.showProgressDialog(flag, DgFactoryMonth2.this);
				CommonProgress.setMessage(flag, "系统正获取数据，请稍后...");
				result = casAction.getAllMonth2(new Request(CommonVars
						.getCurrUser()), materielType, notExistList, beginDate,
						endDate, isRefresh);
				CommonProgress.closeProgressDialog(flag);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgFactoryMonth2.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				if (result != null && !result.isEmpty()) {
					initTable(result);
				} else {
					initTable(new ArrayList());
					JOptionPane.showMessageDialog(DgFactoryMonth2.this,
							"没有查到符合条件的记录！", "提示！", 2);
				}
			}
			btnSearch.setEnabled(true);
			btnRefresh.setEnabled(true);
		}
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setBounds(552, 11, 65, 24);
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					btnPrint.setEnabled(false);
					if (result != null && !result.isEmpty()) {
						CommonDataSource imgExgDS = new CommonDataSource(result,allMaximumFractionDigits);
						List<BaseCompany> company = new Vector<BaseCompany>();
						company.add(CommonVars.getCurrUser().getCompany());
						CommonDataSource companyDS = new CommonDataSource(
								company);

						Calendar calendar = DgFactoryMonth2.this.cbbEndDate
								.getCalendar();
						Calendar beginCalendar = Calendar.getInstance();
						beginCalendar.set(Calendar.YEAR, calendar
								.get(Calendar.YEAR));
						beginCalendar.set(Calendar.MONTH, calendar
								.get(Calendar.MONTH));
						beginCalendar.set(Calendar.DAY_OF_MONTH, 1);

						String riqi = CommonVars.dateToString(beginCalendar
								.getTime())
								+ '~'
								+ CommonVars.dateToString(calendar.getTime());
						InputStream masterReportStream = DgFactoryQuery.class
								.getResourceAsStream("report/FactoryMonthReport2.jasper");
						InputStream detailReportStream = DgFactoryQuery.class
								.getResourceAsStream("report/FactoryMonthReportSubb2.jasper");
						try {
							JasperReport detailReport = (JasperReport) JRLoader
									.loadObject(detailReportStream);
							
							Map<String, Object> parameters = new HashMap<String, Object>();
							String decimalLength  = otherOption.getAllReportInOutMaximumFractionDigits() == null ? "3"
									: otherOption.getAllReportInOutMaximumFractionDigits().toString();
							parameters.put("decimalLength", decimalLength);
							parameters.put("imgExgDS", imgExgDS);
							parameters.put("detailReport", detailReport);
							parameters.put("riqi", riqi);
							parameters.put("reportTilte", DgFactoryMonth2.this
									.getReportTitle());
							JasperPrint jasperPrint;
							
							jasperPrint = JasperFillManager.fillReport(
									masterReportStream, parameters, companyDS);
							DgReportViewer viewer = new DgReportViewer(
									jasperPrint);
							viewer.setVisible(true);
						} catch (JRException e1) {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(DgFactoryMonth2.this,
								"没有数据进行列印！", "提示！", 2);
					}
					btnPrint.setEnabled(true);
				}
			});

		}
		return btnPrint;
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(622, 11, 65, 24);
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgFactoryMonth2.this.dispose();

				}
			});

		}
		return btnExit;
	}

	/**
	 * 
	 * This method initializes jCalendarComboBox1
	 * 
	 * 
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 * 
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(63, 11, 93, 24);
			cbbEndDate.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					Calendar endDate = cbbEndDate.getCalendar();
					int currentDay = endDate.get(Calendar.DAY_OF_MONTH);
					int endDay = endDate.getActualMaximum(Calendar.DATE);
					if (endDay == currentDay) {
						isRefresh = true;
						btnSearch.setText("查询并保存");
					} else {
						isRefresh = false;
						btnSearch.setText("查询");
					}
					cbbMonth.setSelectedItem(endDate.get(Calendar.MONTH) + 1);	
				}
			});
		}
		return cbbEndDate;
	}

	

	/**
	 * @return Returns the reportTitle.
	 */
	public String getReportTitle() {
		return reportTitle;
	}

	/**
	 * @param reportTitle
	 *            The reportTitle to set.
	 */
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	/**
	 * 验证数据
	 * 
	 * @return
	 */
	private boolean validateData() {
		if (this.cbbEndDate.getDate() == null) {
			JOptionPane.showMessageDialog(DgFactoryMonth2.this, "结束日期不可为空 !! ",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		int currentYear = Integer
				.parseInt(com.bestway.bcus.client.cas.parameter.CasCommonVars
						.getYear());
		Calendar clar = this.cbbEndDate.getCalendar();
		int year = clar.get(Calendar.YEAR);
		if (currentYear != year) {
			JOptionPane.showMessageDialog(DgFactoryMonth2.this, "不能查询 " + year
					+ " 年的记录\n你应该选择 " + currentYear + " 年的记录进行查询,因为您的记帐年度为 "
					+ currentYear, "提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * This method initializes cbbMonth
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMonth() {
		if (cbbMonth == null) {
			cbbMonth = new JComboBox();
			cbbMonth.setBounds(new java.awt.Rectangle(190, 11, 69, 24));
			for (int i = 0; i < 12; i++) {
				cbbMonth.addItem(i + 1);
			}
			cbbMonth.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					JComboBox cbbMonth = (JComboBox)e.getSource();
					if (e.getStateChange() == ItemEvent.SELECTED) {
						int month = (Integer) cbbMonth.getSelectedItem();
						//
						// 初始化本月的最后一天
						//
						Calendar clar = Calendar.getInstance();
						clar
								.set(
										Calendar.YEAR,
										com.bestway.bcus.client.cas.parameter.CasCommonVars
												.getYearInt());
						clar.set(Calendar.MONTH, month - 1);
						clar.set(Calendar.DAY_OF_MONTH, 1);
						System.out.println(clar
								.getActualMaximum(Calendar.DATE));
						
						clar.set(Calendar.DAY_OF_MONTH, clar
								.getActualMaximum(Calendar.DATE));
//						cbbEndDate.removeChangeListener(changeListener);
						cbbEndDate.setDate(clar.getTime());
						cbbEndDate.setCalendar(clar);
//						cbbEndDate.addChangeListener(changeListener);
					}
				}
			});
		}
		return cbbMonth;
	}

	/**
	 * This method initializes btnRefresh
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton() {
				public JToolTip createToolTip() {
					return new JMultiLineToolTip();
				}
			};
			btnRefresh
					.setToolTipText("月结存数据,用于计算下个月的结存.\n如果结束日期为当月的最后一天,那么会保存,否则仅做查询而已");
			btnRefresh.setBounds(new java.awt.Rectangle(389, 11, 158, 24));
			btnRefresh.setText("重新生成月度结存记录");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!validateData()) {
						return;
					}
					Calendar endCalendar = DgFactoryMonth2.this.cbbEndDate
							.getCalendar();
					Calendar beginCalendar = Calendar.getInstance();
					beginCalendar.set(Calendar.YEAR, endCalendar
							.get(Calendar.YEAR));
					beginCalendar.set(Calendar.MONTH, endCalendar
							.get(Calendar.MONTH));
					beginCalendar.set(Calendar.DAY_OF_MONTH, 1);
					Date endDate = CommonVars.dateToStandDate(endCalendar
							.getTime());
					Date beginDate = CommonVars.dateToStandDate(beginCalendar
							.getTime());
					//
					// 要更新的月列表
					//
					DgFactoryMonthTip dg = new DgFactoryMonthTip(endCalendar
							.get(Calendar.MONTH));
					dg.setVisible(true);
					if(dg.isOk()){
						notExistList = dg.returnValue() ;
						new find(beginDate, endDate).start();
					}					
				}
			});
		}
		return btnRefresh;
	}

}
