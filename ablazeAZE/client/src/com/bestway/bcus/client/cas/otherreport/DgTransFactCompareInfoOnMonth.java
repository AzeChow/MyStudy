package com.bestway.bcus.client.cas.otherreport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.entity.TempTransFactCompareInfo;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ProgressTask;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.CommonUtils;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;

@SuppressWarnings("unchecked")
public class DgTransFactCompareInfoOnMonth extends JDialogBase {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JLabel jLabel = null;

	private JComboBox cbbScmCoc = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel2 = null;

	private JTextField tfCommName = null;

	private JButton jButton = null;

	private JButton btnQuery = null;

	private JButton btnPrint = null;

	private JButton btnClose = null;

	private JPanel jPanel1 = null;

	private JRadioButton rbTransImport = null;

	private JRadioButton rbTransExport = null;

	private JTableListModel tableModel = null;

	private MaterialManageAction materialManageAction = null;

	private CasAction casAction = null;

	private ButtonGroup buttonGroup = null;  //  @jve:decl-index=0:visual-constraint="882,82"

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;
	
	
	/**
	 * 小数位控制
	 */
	private Integer otherReportMaximumFractionDigits = CasCommonVars.getOtherOption()
			.getOtherReportMaximumFractionDigits() == null ? 2
			: CasCommonVars.getOtherOption()
					.getOtherReportMaximumFractionDigits(); // @jve:decl-index=0:

	private JLabel jLabel3 = null;

	private JTextField tfCommSpec = null;

	private JPanel pnGroupCondition = null;

	private JCheckBox cbScmCoc = null;

	private JCheckBox cbWareName = null;

	private JCheckBox cbWareSpec = null;

	private JLabel lbSelectGroupCondition = null;

	private JLabel lbSelectGroupConditionShow = null;

	private JButton jButton1 = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgTransFactCompareInfoOnMonth() {
		super(false);
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(866, 489));
		this.setTitle("结转对照表");
		this.setContentPane(getJContentPane());
		this.getButtonGroup();
	}
	private void initUIComponents() {
		List lsScmCoc = new ArrayList();
		if (this.rbTransImport.isSelected()) {
			lsScmCoc = this.getManufacturer();
			this.jLabel.setText("供应商名称");
		} else {
			lsScmCoc = this.getCustomer();
			this.jLabel.setText("客户名称");

		}
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(lsScmCoc.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbScmCoc,
				"code", "name", 250);
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));
//		int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
//				.getYearInt();
//		Calendar beginClarendar = Calendar.getInstance();
//		beginClarendar.set(Calendar.YEAR, year);
//		beginClarendar.set(Calendar.MONTH, 0);
//		beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
//		// Date beginDate=beginClarendar.getTime();
//		// this.cbbBeginDate.setDate(beginClarendar.getTime());
//		// this.cbbBeginDate.setCalendar(beginClarendar);
//
//		Calendar endClarendar = Calendar.getInstance();
//		endClarendar.set(Calendar.YEAR, year);
//		endClarendar.set(Calendar.MONTH, 11);
//		endClarendar.set(Calendar.DAY_OF_MONTH, 31);
//		// Date endDate =endClarendar.getTime();
//		int beginYear = beginClarendar.get(Calendar.YEAR);
//		// int beginMonth=beginClarendar.get(Calendar.MONTH);
//
//		int endYear = endClarendar.get(Calendar.YEAR);
//		// int endMonth=endClarendar.get(Calendar.MONTH);
//
//		for (int i = beginYear; i <= endYear; i++) {
//			for (int j = 1; j <= 12; j++) {
//				String smonth = String.valueOf(j);
//				if (smonth.length() == 1) {
//					smonth = ("0" + smonth);
//				}
//				this.cbbBeginMonth.addItem(i + "/" + smonth);
//				this.cbbEndMonth.addItem(i + "/" + smonth);
//			}
//		}
//		if (this.cbbBeginMonth.getItemCount() > 0) {
//			this.cbbBeginMonth.setSelectedIndex(0);
//		}
//		if (this.cbbEndMonth.getItemCount() > 0) {
//			this.cbbEndMonth
//					.setSelectedIndex(this.cbbEndMonth.getItemCount() - 1);
//		}
		// this.cbbEndDate.setDate(endClarendar.getTime());
		// this.cbbEndDate.setCalendar(endClarendar);
		//
		// List dataSource = this.casAction
		// .findLeftoverMaterielBalanceInfo(new Request(CommonVars
		// .getCurrUser(), true));
		initTable(new ArrayList());
	}

	/**
	 * 获得是客户的对象列表
	 */
	private List getCustomer() {
		List list = this.materialManageAction.findScmCocs(new Request(
				CommonVars.getCurrUser(), true));
		List customerList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ScmCoc scmCoc = (ScmCoc) list.get(i);
			if (scmCoc.getIsCustomer() != null
					&& scmCoc.getIsCustomer().booleanValue()) {
				customerList.add(scmCoc);
			}
		}
		return customerList;
	}

	/**
	 * 获得是供应商的对象列表
	 */
	private List getManufacturer() {
		List list = this.materialManageAction.findScmCocs(new Request(
				CommonVars.getCurrUser(), true));
		List manufacturerList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ScmCoc scmCoc = (ScmCoc) list.get(i);
			if (scmCoc.getIsManufacturer() != null
					&& scmCoc.getIsManufacturer().booleanValue()) {
				manufacturerList.add(scmCoc);
			}
		}
		return manufacturerList;
	}

	/**
	 * This method initializes pnContent
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(2);
			jSplitPane.setDividerLocation(85);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes pn
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(17, 58, 65, 23));
			jLabel3.setText("规格型号");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(18, 31, 63, 25));
			jLabel2.setText("商品名称");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(293, 32, 55, 23));
			jLabel11.setText("结束日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(293, 5, 55, 23));
			jLabel1.setText("开始日期");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(18, 4, 64, 26));
			jLabel.setText("供应商");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbScmCoc(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel11, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getTfCommName(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnPrint(), null);
			jPanel.add(getBtnClose(), null);
			jPanel.add(getJPanel1(), null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getTfCommSpec(), null);
			jPanel.add(getPnGroupCondition(), null);
			jPanel.add(getJButton1(), null);
		}
		return jPanel;
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
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new GroupableHeaderTable();
		}
		return jTable;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(new Rectangle(82, 4, 206, 26));
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCommName() {
		if (tfCommName == null) {
			tfCommName = new JTextField();
			tfCommName.setBounds(new Rectangle(82, 31, 180, 26));
			tfCommName.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					if("".equals(tfCommName.getText().trim())){
						tfCommSpec.setText("");
					}
				}
			});
		}
		return tfCommName;
	}

	/**
	 * This method initializes btnCustomSort
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(264, 31, 24, 26));
			jButton.setText("...");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ScmCoc scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();
					String beginMonth = CommonUtils.getDate(CommonUtils.getBeginDate(cbbBeginDate.getDate()), "yyyy-MM");;
					String endMonth = CommonUtils.getDate(cbbEndDate.getDate(), "yyyy-MM");
					boolean isImg = rbTransImport.isSelected();
					Object object = CasQuery.getInstance()
							.findTransFactRecvSendCommName(isImg, scmCoc,
									beginMonth, endMonth);
					if (object != null) {
						tfCommName.setText(((TempObject)(object)).getObject1().toString());
						//tfCommSpec.setText(((TempObject)(object)).getObject2().toString());
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
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(new Rectangle(778, 5, 63, 22));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new SearchThread().start();
				}
			});
		}
		return btnQuery;
	}

	class SearchThread extends Thread {
		public void run() {
			/*if (cbbScmCoc.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(
						DgTransFactCompareInfoOnMonth.this, "请选择客户或供应商", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}*/
			// if (tfCommName.getText().trim().equals("")) {
			// JOptionPane.showMessageDialog(
			// DgTransFactCompareInfoOnMonth.this, "请输入商品名称", "提示",
			// JOptionPane.INFORMATION_MESSAGE);
			// return;
			// }
			if (cbbBeginDate.getDate() == null) {
				JOptionPane.showMessageDialog(
						DgTransFactCompareInfoOnMonth.this, "请选择开始日期", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (cbbEndDate.getDate() == null) {
				JOptionPane.showMessageDialog(
						DgTransFactCompareInfoOnMonth.this, "请选择结束日期", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			btnQuery.setEnabled(false);
			try {
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
				CommonProgress.showProgressDialog(flag,
						DgTransFactCompareInfoOnMonth.this, false,
						progressTask, 5000);
				CommonProgress.setMessage(flag, "系统正获取数据，请稍后...");
				ScmCoc scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();
				Calendar cal = Calendar.getInstance();
				cal.setTime(cbbBeginDate.getDate());
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0,
						0, 0);
				Date beginMonth = cal.getTime();
				
				cal = Calendar.getInstance();
				cal.setTime(cbbEndDate.getDate());
				// 得到一个月最最后一天日期(31/30/29/28)
				int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				// 按你的要求设置时间
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
						maxDay, 23, 59, 59);
				Date endMonth = cal.getTime();
				
				String commName = null;
				String commSpec = null;
				if (!"".equals(tfCommName.getText().trim())) {
					commName = tfCommName.getText().trim() ;
				}
				if (!"".equals(tfCommSpec.getText().trim())) {
					commSpec = tfCommSpec.getText().trim() ;
				}
				boolean isImg = rbTransImport.isSelected();
				/**
				 * 分组条件（分组字段名）
				 */
				List<String> groupCondition = new ArrayList<String>();
				groupCondition.clear();
				if (cbScmCoc.isSelected()) {
					groupCondition.add("scmCocName");
				}

				if (cbWareName.isSelected()) {
					groupCondition.add("hsName");
				}

				if (cbWareSpec.isSelected()) {
					groupCondition.add("hsSpec");
				}
				
				
				List list = casAction.findTransFactCompare(
						new Request(CommonVars.getCurrUser()), isImg, commName, commSpec,
						scmCoc, beginMonth, endMonth,groupCondition);
				CommonProgress.closeProgressDialog(flag);

				//initTable(valueList);
				//initTable2(list);
				//1.如果分组条件供应商，商品名称，规格型号都选择，窗口栏位不变
				//2.如果分组条件供应商，商品名称，规格型号缺少其中一个，关封栏位不显示
				//3.如果分组条件供应商不选择，关封栏位，联系人，联系电话，传真不显示
				if(cbScmCoc.isSelected()&&cbWareName.isSelected()&&cbWareSpec.isSelected()){
					initTable(list);
				}else if(!cbScmCoc.isSelected()&&!cbWareName.isSelected()&&!cbWareSpec.isSelected()){
					initTable(list);
				}else if(cbWareName.isSelected()&&cbWareSpec.isSelected()){
					initTable4(list);
				}else if(cbScmCoc.isSelected()&&cbWareName.isSelected()){
					initTable2(list);
				}else if(cbScmCoc.isSelected()&&cbWareSpec.isSelected()){
					initTable2(list);
				}else if(cbScmCoc.isSelected()){
					initTable2(list);
				}else if(cbWareName.isSelected()){
					initTable3(list);
				} else if (cbWareSpec.isSelected()) {
					initTable3(list);
				}
				
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(
						DgTransFactCompareInfoOnMonth.this, "获取数据失败：！"
								+ e.getMessage(), "提示", 2);
			} finally {
				CommonProgress.closeProgressDialog(flag);
			}
			btnQuery.setEnabled(true);
		}
	}

	/**
	 * 将now转化成字符型->"yyyy-MM-dd"
	 * 
	 * @return
	 */
	public static String nowToDate() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		Date date = new Date();
		String str = formater.format(date);
		if (str == null) {
			return "";
		} else {
			if (str.length() == 10) {
				return str.substring(5, 7);
			} else {
				return str.substring(5);
			}
		}
	}

	/**
	 * 截取日期
	 * 
	 * @param date
	 * @return
	 */
	public Integer getDate(String date) {
		Integer t = 0;
		if (date != null) {
			t = Integer.parseInt(date.substring(0, 1));
		}
		if (t == 0) {
			return Integer.parseInt(date.substring(1));
		} else if (t == 1) {
			return Integer.parseInt(date);
		}
		return null;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setBounds(new Rectangle(778, 33, 63, 22));
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null || tableModel.getList().size() <= 0) {
						JOptionPane.showMessageDialog(
								DgTransFactCompareInfoOnMonth.this, "没有数据可打印",
								"提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List<TempTransFactCompareInfo> list = tableModel.getList();
					/*//otherReportMaximumFractionDigits
					//小数位控制,收／送货数量,收／送货重量,转厂数量,转厂重量,结余数量结余重量,关封余量数量,关封余量重量*/
					for (TempTransFactCompareInfo item : list) {
						item.setSendAmount(item.getSendAmount() == null ? Double.valueOf(0)
										: CommonUtils.getDoubleByDigit(item.getSendAmount(),
														otherReportMaximumFractionDigits));
						item.setSendWeight(item.getSendWeight() == null ? Double.valueOf(0)
								: CommonUtils.getDoubleByDigit(item.getSendWeight(),
												otherReportMaximumFractionDigits));
						item.setTransAmount(item.getTransAmount() == null ? Double.valueOf(0)
								: CommonUtils.getDoubleByDigit(item.getTransAmount(),
												otherReportMaximumFractionDigits));
						item.setTransWeight(item.getTransWeight() == null ? Double.valueOf(0)
								: CommonUtils.getDoubleByDigit(item.getTransWeight(),
												otherReportMaximumFractionDigits));
						item.setRemainAmount(item.getRemainAmount() == null ? Double.valueOf(0)
								: CommonUtils.getDoubleByDigit(item.getRemainAmount(),
												otherReportMaximumFractionDigits));
						item.setRemainWeight(item.getRemainWeight() == null ? Double.valueOf(0)
								: CommonUtils.getDoubleByDigit(item.getRemainWeight(),
												otherReportMaximumFractionDigits));
						item.setEnvelopAmount(item.getEnvelopAmount() == null ? Double.valueOf(0)
								: CommonUtils.getDoubleByDigit(item.getEnvelopAmount(),
												otherReportMaximumFractionDigits));
						item.setEnvelopWeight(item.getEnvelopWeight() == null ? Double.valueOf(0)
								: CommonUtils.getDoubleByDigit(item.getEnvelopWeight(),
												otherReportMaximumFractionDigits));
					}
					
					Map parametersMap = new Hashtable();
					parametersMap.put("scmCocName", cbbScmCoc.getSelectedItem() == null ? ""
							: ((ScmCoc) cbbScmCoc.getSelectedItem())
							.getName());
					parametersMap.put("commName", tfCommName.getText().trim());
					parametersMap.put("scmCoc", (rbTransImport.isSelected() ? "供应商" : "客户"));
					String linkMan = "", tel = "", fax = "";
					parametersMap.put("linkMan", linkMan);
					parametersMap.put("tel", tel);
					parametersMap.put("fax", fax);	
					DgTransFactCompareReportGroupSet transFactCompareGroupSet = new DgTransFactCompareReportGroupSet(list,parametersMap);
					transFactCompareGroupSet.setVisible(true);
				}
			});
		}
		return btnPrint;
	}

//	private class ComparatorImpl implements Comparator {
//		public int compare(Object o1, Object o2) {
//			if (o1 == null || o2 == null) {
//				return 1;
//			}
//			// beginYear + "年" + i + "月"
//
//			ImportExportInfo a = (ImportExportInfo) o1;
//			ImportExportInfo b = (ImportExportInfo) o2;
//			String key1 = (a.getHsName() == null ? "" : a.getHsName())
//					+ (a.getHsSpec() == null ? "" : a.getHsSpec())
//					+ (a.getWareSet() == null ? null : a.getWareSet().getName())
//					+ (a.getHsUnit() == null ? null : a.getHsUnit().getName());
//
//			String key2 = (b.getHsName() == null ? "" : b.getHsName())
//					+ (b.getHsSpec() == null ? "" : b.getHsSpec())
//					+ (b.getWareSet() == null ? null : b.getWareSet().getName())
//					+ (b.getHsUnit() == null ? null : b.getHsUnit().getName());
//
//			//
//			// asc
//			//
//			return key1.compareTo(key2);
//			// if (key1.compareTo(key2)) {
//			// return -1;
//			// } else if (year1 < year2) {
//			// return 1;
//			// } else {
//			// if (month1 > month2) {
//			// return -1;
//			// } else if (month1 < month2) {
//			// return 1;
//			// }
//			// }
//			// return 0;
//		}
//	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setBounds(new Rectangle(778, 60, 63, 22));
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(293, 58, 186, 24));
			jPanel1.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.RAISED));
			jPanel1.add(getRbTransImport(), null);
			jPanel1.add(getRbTransExport(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbTransImport() {
		if (rbTransImport == null) {
			rbTransImport = new JRadioButton();
			rbTransImport.setText("转进");
			rbTransImport.setSelected(true);
			rbTransImport.setBounds(new Rectangle(10, 2, 49, 20));
			rbTransImport.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						initUIComponents();
					}
				}
			});
		}
		return rbTransImport;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbTransExport() {
		if (rbTransExport == null) {
			rbTransExport = new JRadioButton();
			rbTransExport.setText("转出");
			rbTransExport.setBounds(new Rectangle(80, 2, 49, 20));
			rbTransExport.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						initUIComponents();
					}
				}
			});
		}
		return rbTransExport;
	}

	private void initTable(final List list) {
		/*if (tableModel != null) {
			tableModel.setList(list);
			return;
		}*/
		JTableListModelAdapter adapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("供应商", "scmCocName", 100));
				list.add(addColumn("商品名称", "commName", 100));
				list.add(addColumn("型号规格", "commSpec", 100));
				list.add(addColumn("日期", "date", 100));
				list.add(addColumn("数量", "sendAmount", 180));
				list.add(addColumn("重量", "sendWeight", 100));
				list.add(addColumn("数量", "transAmount", 120));
				list.add(addColumn("重量", "transWeight", 100));
				list.add(addColumn("数量", "remainAmount", 180));
				list.add(addColumn("重量", "remainWeight", 180));
				list.add(addColumn("关封号", "envelopNo", 100));
				list.add(addColumn("数量", "envelopAmount", 100));
				list.add(addColumn("重量", "envelopWeight", 100));//没有赋任何值，暂时不知道从哪里获取数据
				list.add(addColumn("有效期", "term", 100));
				list.add(addColumn("是否结案", "isEndCase", 100));
				list.add(addColumn("联系人", "linkMan", 100));
				list.add(addColumn("联系电话", "tel", 100));
				list.add(addColumn("传真", "fax", 100));
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, adapter,
				ListSelectionModel.SINGLE_SELECTION);
		TableColumnModel cm = jTable.getColumnModel();
		cm.getColumn(13).setCellRenderer(new TableCheckBoxRender());
		ColumnGroup gSend = new ColumnGroup(rbTransImport.isSelected() ? "收货"
				: "送货");
		gSend.add(cm.getColumn(5));
		gSend.add(cm.getColumn(6));
		ColumnGroup gTrans = new ColumnGroup("转厂");
		gTrans.add(cm.getColumn(7));
		gTrans.add(cm.getColumn(8));
		ColumnGroup gRemain = new ColumnGroup("结余");
		gRemain.add(cm.getColumn(9));
		gRemain.add(cm.getColumn(10));
		ColumnGroup gEnvelopRemain = new ColumnGroup("关封余量");
		gEnvelopRemain.add(cm.getColumn(12));
		gEnvelopRemain.add(cm.getColumn(13));
		ColumnGroup gEnvelop = new ColumnGroup("关封");
		gEnvelop.add(cm.getColumn(11));
		gEnvelop.add(gEnvelopRemain);
		gEnvelop.add(cm.getColumn(14));
		gEnvelop.add(cm.getColumn(15));
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(gSend);
		header.addColumnGroup(gTrans);
		header.addColumnGroup(gRemain);
		header.addColumnGroup(gEnvelop);
		List<JTableListColumn> xs = tableModel.getColumns();
		xs.get(5).setFractionCount(otherReportMaximumFractionDigits);//收／送货数量
		xs.get(6).setFractionCount(otherReportMaximumFractionDigits);//收／送货重量
		xs.get(7).setFractionCount(otherReportMaximumFractionDigits);//转厂数量
		xs.get(8).setFractionCount(otherReportMaximumFractionDigits);//转厂重量
		xs.get(9).setFractionCount(otherReportMaximumFractionDigits);//结余数量
		xs.get(10).setFractionCount(otherReportMaximumFractionDigits);//结余重量
		xs.get(12).setFractionCount(otherReportMaximumFractionDigits);//关封余量数量
		xs.get(13).setFractionCount(otherReportMaximumFractionDigits);//关封余量重量
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(7).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(8).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(9).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(10).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(12).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(13).setCellRenderer(
				new tableCellRender());
	}
	/**
	 * //2.如果分组条件供应商，商品名称，规格型号缺少其中一个关封栏位不显示
	 * @param list
	 */
	private void initTable2(final List list) {
		if (list == null) {
			return;
		}
		JTableListModelAdapter adapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("供应商", "scmCocName", 100));
				list.add(addColumn("商品名称", "commName", 100));
				list.add(addColumn("型号规格", "commSpec", 100));
				list.add(addColumn("日期", "date", 100));
				list.add(addColumn("数量", "sendAmount", 180));
				list.add(addColumn("重量", "sendWeight", 100));
				list.add(addColumn("数量", "transAmount", 120));
				list.add(addColumn("重量", "transWeight", 100));
				list.add(addColumn("数量", "remainAmount", 180));
				list.add(addColumn("重量", "remainWeight", 180));
				list.add(addColumn("联系人", "linkMan", 100));
				list.add(addColumn("联系电话", "tel", 100));
				list.add(addColumn("传真", "fax", 100));
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, adapter,
				ListSelectionModel.SINGLE_SELECTION);
		TableColumnModel cm = jTable.getColumnModel();
		ColumnGroup gSend = new ColumnGroup(rbTransImport.isSelected() ? "收货"
				: "送货");
		gSend.add(cm.getColumn(5));
		gSend.add(cm.getColumn(6));
		ColumnGroup gTrans = new ColumnGroup("转厂");
		gTrans.add(cm.getColumn(7));
		gTrans.add(cm.getColumn(8));
		ColumnGroup gRemain = new ColumnGroup("结余");
		gRemain.add(cm.getColumn(9));
		gRemain.add(cm.getColumn(10));
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(gSend);
		header.addColumnGroup(gTrans);
		header.addColumnGroup(gRemain);
		List<JTableListColumn> xs = tableModel.getColumns();
		xs.get(5).setFractionCount(otherReportMaximumFractionDigits);//收／送货数量
		xs.get(6).setFractionCount(otherReportMaximumFractionDigits);//收／送货重量
		xs.get(7).setFractionCount(otherReportMaximumFractionDigits);//转厂数量
		xs.get(8).setFractionCount(otherReportMaximumFractionDigits);//转厂重量
		xs.get(9).setFractionCount(otherReportMaximumFractionDigits);//结余数量
		xs.get(10).setFractionCount(otherReportMaximumFractionDigits);//结余重量
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(7).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(8).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(9).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(10).setCellRenderer(
				new tableCellRender());
	}
	/**
	 * 3.如果分组条件供应商不选择个关封栏位，联系人，联系电话，传真不显示
	 * @param list
	 */
	//
	private void initTable3(final List list) {
		if (list == null) {
			return;
		}
		JTableListModelAdapter adapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("供应商", "scmCocName", 100));
				list.add(addColumn("商品名称", "commName", 100));
				list.add(addColumn("型号规格", "commSpec", 100));
				list.add(addColumn("日期", "date", 100));
				list.add(addColumn("数量", "sendAmount", 180));
				list.add(addColumn("重量", "sendWeight", 100));
				list.add(addColumn("数量", "transAmount", 120));
				list.add(addColumn("重量", "transWeight", 100));
				list.add(addColumn("数量", "remainAmount", 180));
				list.add(addColumn("重量", "remainWeight", 180));
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, adapter,
				ListSelectionModel.SINGLE_SELECTION);
		TableColumnModel cm = jTable.getColumnModel();
		ColumnGroup gSend = new ColumnGroup(rbTransImport.isSelected() ? "收货"
				: "送货");
		gSend.add(cm.getColumn(5));
		gSend.add(cm.getColumn(6));
		ColumnGroup gTrans = new ColumnGroup("转厂");
		gTrans.add(cm.getColumn(7));
		gTrans.add(cm.getColumn(8));
		ColumnGroup gRemain = new ColumnGroup("结余");
		gRemain.add(cm.getColumn(9));
		gRemain.add(cm.getColumn(10));
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(gSend);
		header.addColumnGroup(gTrans);
		header.addColumnGroup(gRemain);
		List<JTableListColumn> xs = tableModel.getColumns();
		xs.get(5).setFractionCount(otherReportMaximumFractionDigits);//收／送货数量
		xs.get(6).setFractionCount(otherReportMaximumFractionDigits);//收／送货重量
		xs.get(7).setFractionCount(otherReportMaximumFractionDigits);//转厂数量
		xs.get(8).setFractionCount(otherReportMaximumFractionDigits);//转厂重量
		xs.get(9).setFractionCount(otherReportMaximumFractionDigits);//结余数量
		xs.get(10).setFractionCount(otherReportMaximumFractionDigits);//结余重量
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(7).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(8).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(9).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(10).setCellRenderer(
				new tableCellRender());
	}
	/**
	 * 4.如果分组条件只选择商品名称，规格型号，不选择供应商 联系人，联系电话，传真不显示
	 * @param list
	 */
	private void initTable4(final List list) {
		/*if (tableModel != null) {
			tableModel.setList(list);
			return;
		}*/
		JTableListModelAdapter adapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("供应商", "scmCocName", 100));
				list.add(addColumn("商品名称", "commName", 100));
				list.add(addColumn("型号规格", "commSpec", 100));
				list.add(addColumn("日期", "date", 100));
				list.add(addColumn("数量", "sendAmount", 180));
				list.add(addColumn("重量", "sendWeight", 100));
				list.add(addColumn("数量", "transAmount", 120));
				list.add(addColumn("重量", "transWeight", 100));
				list.add(addColumn("数量", "remainAmount", 180));
				list.add(addColumn("重量", "remainWeight", 180));
				list.add(addColumn("关封号", "envelopNo", 100));
				list.add(addColumn("数量", "envelopAmount", 100));
				list.add(addColumn("重量", "envelopWeight", 100));//没有赋任何值，暂时不知道从哪里获取数据
				list.add(addColumn("有效期", "term", 100));
				list.add(addColumn("是否结案", "isEndCase", 100));
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, adapter,
				ListSelectionModel.SINGLE_SELECTION);
		TableColumnModel cm = jTable.getColumnModel();
		cm.getColumn(13).setCellRenderer(new TableCheckBoxRender());
		ColumnGroup gSend = new ColumnGroup(rbTransImport.isSelected() ? "收货"
				: "送货");
		gSend.add(cm.getColumn(5));
		gSend.add(cm.getColumn(6));
		ColumnGroup gTrans = new ColumnGroup("转厂");
		gTrans.add(cm.getColumn(7));
		gTrans.add(cm.getColumn(8));
		ColumnGroup gRemain = new ColumnGroup("结余");
		gRemain.add(cm.getColumn(9));
		gRemain.add(cm.getColumn(10));
		ColumnGroup gEnvelopRemain = new ColumnGroup("关封余量");
		gEnvelopRemain.add(cm.getColumn(12));
		gEnvelopRemain.add(cm.getColumn(13));
		ColumnGroup gEnvelop = new ColumnGroup("关封");
		gEnvelop.add(cm.getColumn(11));
		gEnvelop.add(gEnvelopRemain);
		gEnvelop.add(cm.getColumn(14));
		gEnvelop.add(cm.getColumn(15));
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(gSend);
		header.addColumnGroup(gTrans);
		header.addColumnGroup(gRemain);
		header.addColumnGroup(gEnvelop);
		List<JTableListColumn> xs = tableModel.getColumns();
		xs.get(5).setFractionCount(otherReportMaximumFractionDigits);//收／送货数量
		xs.get(6).setFractionCount(otherReportMaximumFractionDigits);//收／送货重量
		xs.get(7).setFractionCount(otherReportMaximumFractionDigits);//转厂数量
		xs.get(8).setFractionCount(otherReportMaximumFractionDigits);//转厂重量
		xs.get(9).setFractionCount(otherReportMaximumFractionDigits);//结余数量
		xs.get(10).setFractionCount(otherReportMaximumFractionDigits);//结余重量
		xs.get(12).setFractionCount(otherReportMaximumFractionDigits);//关封余量数量
		xs.get(13).setFractionCount(otherReportMaximumFractionDigits);//关封余量重量
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(7).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(8).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(9).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(10).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(12).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(13).setCellRenderer(
				new tableCellRender());
	}

	class tableCellRender extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			if (value != null) {
				double tmp = Double.parseDouble((String) value);
				this.setText(Double.toString(tmp));
			}
			return this;
		}
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getRbTransImport());
			buttonGroup.add(this.getRbTransExport());
		}
		return buttonGroup;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(353, 5, 127, 22));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes cbbEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(353, 32, 127, 22));
			cbbEndDate.setPreferredSize(new Dimension(185, 22));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes tfCommSpec	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfCommSpec() {
		if (tfCommSpec == null) {
			tfCommSpec = new JTextField();
			tfCommSpec.setBounds(new Rectangle(82, 58, 180, 24));
			tfCommSpec.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(122, 138, 153)));
			tfCommSpec.setEditable(true);
		}
		return tfCommSpec;
	}

	/**
	 * This method initializes pnGroupCondition	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnGroupCondition() {
		if (pnGroupCondition == null) {
			lbSelectGroupConditionShow = new JLabel();
			lbSelectGroupConditionShow.setBounds(new Rectangle(100, 42, 189, 20));
			lbSelectGroupConditionShow.setText("");
			lbSelectGroupCondition = new JLabel();
			lbSelectGroupCondition.setBounds(new Rectangle(8, 42, 88, 20));
			lbSelectGroupCondition.setText("\u5df2\u9009\u5206\u7ec4\u6761\u4ef6\uff1a");
			pnGroupCondition = new JPanel();
			pnGroupCondition.setLayout(null);
			pnGroupCondition.setBounds(new Rectangle(482, 4, 292, 80));
			pnGroupCondition.setBorder(BorderFactory.createTitledBorder(null, "\u5206\u7ec4\u6761\u4ef6", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), Color.blue));
			pnGroupCondition.add(getCbScmCoc(), null);
			pnGroupCondition.add(getCbWareName(), null);
			pnGroupCondition.add(getCbWareSpec(), null);
			pnGroupCondition.add(lbSelectGroupCondition, null);
			pnGroupCondition.add(lbSelectGroupConditionShow, null);
		}
		return pnGroupCondition;
	}

	/**
	 * This method initializes cbScmCoc	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbScmCoc() {
		if (cbScmCoc == null) {
			cbScmCoc = new JCheckBox();
			cbScmCoc.setBounds(new Rectangle(3, 15, 90, 20));
			cbScmCoc.setText("供应商名称");
			cbScmCoc.setSelected(true);
			cbScmCoc.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					refreshGroupCondition();
				}
			});
		}
		return cbScmCoc;
	}

	/**
	 * This method initializes cbWareName	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbWareName() {
		if (cbWareName == null) {
			cbWareName = new JCheckBox();
			cbWareName.setBounds(new Rectangle(93, 16, 76, 20));
			cbWareName.setText("商品名称");
			cbWareName.setSelected(true);
			cbWareName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					refreshGroupCondition();
				}
			});
		}
		return cbWareName;
	}

	/**
	 * This method initializes cbWareSpec	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbWareSpec() {
		if (cbWareSpec == null) {
			cbWareSpec = new JCheckBox();
			cbWareSpec.setBounds(new Rectangle(183, 15, 78, 20));
			cbWareSpec.setText("型号规格");
			cbWareSpec.setSelected(true);
			cbWareSpec.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					refreshGroupCondition();
				}
			});
		}
		return cbWareSpec;
	}
	public void refreshGroupCondition() {
		String groupCondition = "";
		if (cbScmCoc.isSelected()) {
			groupCondition += cbScmCoc.getText();
		}

		if (cbWareName.isSelected()) {
			groupCondition += " /商品名称";
		}

		if (cbWareSpec.isSelected()) {
			groupCondition += " /商品规格";
		}
		lbSelectGroupConditionShow.setText(groupCondition);
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("...");
			jButton1.setBounds(new Rectangle(264, 58, 24, 24));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ScmCoc scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();
					String beginMonth = CommonUtils.getDate(CommonUtils.getBeginDate(cbbBeginDate.getDate()), "yyyy-MM");;
					String endMonth = CommonUtils.getDate(cbbEndDate.getDate(), "yyyy-MM");
					boolean isImg = rbTransImport.isSelected();
					Object object = CasQuery.getInstance()
							.findTransFactRecvSendCommName(isImg, scmCoc,
									beginMonth, endMonth);
					if (object != null) {
						tfCommSpec.setText(((TempObject)(object)).getObject2().toString());
					}
				}
			});
		}
		return jButton1;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
