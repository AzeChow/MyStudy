/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgImpFinancialRecord extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton btnSearch = null;

	private JButton btnClose = null;

	private JTableListModel tableModel = null;

	private EncAction encAction = null;

	private List list = null; // @jve:decl-index=0:

	private MaterialManageAction materialManageAction = null;

	private ManualDeclareAction manualDecleareAction = null;

	private SystemAction systemAction = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	/**
	 * This is the default constructor
	 */
	public DgImpFinancialRecord() {
		super();
		initialize();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			cbbEndDate
			.setDate(CommonUtils
					.lastDayOfMonth(cbbBeginDate
							.getDate()));
			initTable(new Vector());
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
		this.setTitle("进口财务报表");
		this.setSize(744, 454);
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
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jLabel1 = new JLabel();
			jLabel1.setText("   \u81f3     ");
			jLabel = new JLabel();
			jLabel.setText("\u7533\u62a5\u65e5\u671f    ");
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.add(jLabel);
			jToolBar.add(getCbbBeginDate());
			jToolBar.add(jLabel1);
			jToolBar.add(getCbbEndDate());
			jToolBar.add(getBtnSearch());
			jToolBar.add(getBtnClose());
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
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查询");
			btnSearch.setPreferredSize(new Dimension(65, 30));
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Date beginDate = cbbBeginDate.getDate();
					Date endDate = cbbEndDate.getDate();
					List list = encAction
							.calcCustomsDeclarationCommInfoAsFinancial(
									new Request(CommonVars.getCurrUser()),
									beginDate, endDate, "0");
					if (list != null && list.size() > 0) {
						initTable(list);
					} else {
						initTable(new ArrayList());
					}
				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setPreferredSize(new Dimension(65, 30));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		JTableListModelAdapter tableModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("所属期", "suoShuQi", 60));// 1
				list.add(addColumn("序号", "xuHao", 50));// 2
				list.add(addColumn("所属期标识", "suoShuQiBiaoShi", 70));// 3
				list.add(addColumn("进料登记册号", "jlDjch", 100));// 4
				list.add(addColumn("进口报关单号", "jkbgdh", 130));// 5
				list.add(addColumn("代理证明号", "dlzmh", 100));// 6
				list.add(addColumn("进口商品代码", "jckspdm", 100));// 7
				list.add(addColumn("报关单项号", "serialNumber", 50));// 7
				list.add(addColumn("进口数量", "jcksl", 100));// 8
				list.add(addColumn("原币代码", "ybdm", 100));// 9

				list.add(addColumn("原币到岸价", "ybdaj", 100));// 10
				list.add(addColumn("原币汇率", "", 100));// 11
				list.add(addColumn("人民币到岸价", "", 100));// 11
				list.add(addColumn("美元汇率", "", 100));// 11
				list.add(addColumn("美元到岸价", "", 100));// 11
				list.add(addColumn("海关实征税额", "", 100));// 11
				list.add(addColumn("备注", "", 100));// 11
				return list;
			}
		};

		JTableListModel.dataBind(jTable, list, tableModelAdapter,
				ListSelectionModel.SINGLE_SELECTION);
		tableModel = (JTableListModel) jTable.getModel();

		String reportDecimalLength = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser(), true),
				BcusParameter.ReportDecimalLength);
		Integer decimalLength = 2;
		if (reportDecimalLength != null)
			decimalLength = Integer.valueOf(reportDecimalLength);
		tableModel.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other != null) {
			tableModel.setAllColumnsshowThousandthsign(other
					.getIsAutoshowThousandthsign() == null ? false : other
					.getIsAutoshowThousandthsign());
		}
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setPreferredSize(new Dimension(110, 22));
			cbbBeginDate
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (cbbBeginDate.getDate() != null) {
								cbbEndDate
										.setDate(CommonUtils
												.lastDayOfMonth(cbbBeginDate
														.getDate()));
							}
						}
					});
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
			cbbEndDate.setPreferredSize(new Dimension(110, 22));
		}
		return cbbEndDate;
	}
}
