package com.bestway.client.fecav;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableColorRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.CommonUtils;
import com.bestway.fecav.action.FecavAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgCavSignInStat extends JDialogBase {

	private JPanel				jContentPane	= null;
	private JScrollPane			jScrollPane		= null;
	private JTable				jTable			= null;
	private JButton				btnExit			= null;
	private JButton				btnSearch		= null;
	private JPanel				pnTop			= null;
	private JLabel				jLabel			= null;
	private JCalendarComboBox	cbbBeginDate	= null;
	private JLabel				jLabel1			= null;
	private JCalendarComboBox	cbbEndDate		= null;
	private FecavAction			fecavAction		= null;

	/**
	 * This method initializes
	 * 
	 */
	public DgCavSignInStat() {
		super();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
		initialize();
//		initTable(new ArrayList());
		search() ;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(755, 539));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("核销签收汇总表");

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getPnTop(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new java.awt.Rectangle(0, 34, 747, 478));
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
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setBounds(new java.awt.Rectangle(407, 5, 62, 23));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
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
			btnSearch.setBounds(new java.awt.Rectangle(339, 5, 62, 23));
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					search();
				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes pnTop
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTop() {
		if (pnTop == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(166, 5, 58, 23));
			jLabel1.setText("结束日期");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(7, 5, 53, 23));
			jLabel.setText("开始日期");
			pnTop = new JPanel();
			pnTop.setLayout(null);
			pnTop.setBounds(new java.awt.Rectangle(0, 0, 746, 32));
			pnTop.add(getBtnExit(), null);
			pnTop.add(getBtnSearch(), null);
			pnTop.add(jLabel, null);
			pnTop.add(getCbbBeginDate(), null);
			pnTop.add(jLabel1, null);
			pnTop.add(getCbbEndDate(), null);
		}
		return pnTop;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setDate(null);
			cbbBeginDate.setBounds(new java.awt.Rectangle(62, 5, 97, 23));
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
			cbbEndDate.setDate(null);
			cbbEndDate.setBounds(new java.awt.Rectangle(226, 5, 97, 23));
		}
		return cbbEndDate;
	}

	/**
	 * 查询
	 * 
	 */
	private void search() {
		Date beginDate = this.cbbBeginDate.getDate();
		Date endDate = this.cbbEndDate.getDate();
		beginDate = CommonUtils.getBeginDate(beginDate);
		endDate = CommonUtils.getEndDate(endDate);
		if (beginDate != null && endDate != null) {
			if (beginDate.after(endDate)) {
				JOptionPane.showMessageDialog(this, "开始日期大于结束日期!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		} 
		List dataSource = fecavAction.findTempCancelSignInStat(new Request(
				CommonVars.getCurrUser()), beginDate, endDate);
		initTable(dataSource);
	}

	/**
	 * 初始化表
	 * 
	 */
	private void initTable(List dataSource) {
		JTableListModel tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("批号 ", "batchNo", 200));
						list.add(addColumn("份数", "count", 100));
						list.add(addColumn("金额", "money", 100));
						list.add(addColumn("核销单号码", "billNoBetween", 200));
						return list;
					}
				});
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		TableColorRender.setTableRowColor(jTable, tableModel.getRowCount() - 1);
	}

}
