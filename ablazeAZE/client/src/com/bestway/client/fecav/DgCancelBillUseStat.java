package com.bestway.client.fecav;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.Request;
import com.bestway.fecav.action.FecavAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgCancelBillUseStat extends JDialogBase {

	private JPanel jContentPane = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JButton btnExit = null;

	private JButton btnSearch = null;

	private JPanel pnTop = null;

	private JLabel jLabel = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbEndDate = null;

	private FecavAction fecavAction = null;
	
	private JTableListModel tableModel = null;
	/**
	 * This method initializes
	 * 
	 */
	public DgCancelBillUseStat() {
		super();
		initialize();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
	}

	public void setVisible(boolean b){
		if(b){
//			initTable(new ArrayList());
			GregorianCalendar thisday = new GregorianCalendar();
			int year = thisday.get(GregorianCalendar.YEAR);
			int month = thisday.get(GregorianCalendar.MONTH);
			GregorianCalendar firstDay = new GregorianCalendar(
					year, month, 1);
			GregorianCalendar lastDay = new GregorianCalendar(year,
					month, 1);
			lastDay.add(GregorianCalendar.MONTH, 1);
			lastDay.add(GregorianCalendar.DATE, -1);
			cbbBeginDate.setDate(firstDay.getTime());
			cbbEndDate.setDate(lastDay.getTime());
			search() ;
		}
		super.setVisible(b);
	}
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(755,547));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("出口外汇核销使用状况");

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
			jTable = new GroupableHeaderTable();
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
		List list = fecavAction.findFecavBillForUsedStat(new Request(CommonVars
				.getCurrUser()), cbbBeginDate.getDate(), cbbEndDate.getDate());
		initTable(list);
	}
	
	private void initTable(List list) {
		tableModel = new JTableListModel(this.getJTable(), list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = (List<Object>) (new Vector());
						list.add(addColumn("领单日期", "outerObtainDate", 80));
						list.add(addColumn("起始核销单号", "outerObtainBeginCode", 160));
						list.add(addColumn("终至核销单号", "outerObtainEndCode", 160));
						list.add(addColumn("份数", "outerObtainPieces", 40));
						list.add(addColumn("领单人", "outerObtainMan", 60));
						
						list.add(addColumn("日期", "usedDate", 80));
						list.add(addColumn("起始核销单号", "usedBeginCode", 160));
						list.add(addColumn("终至核销单号", "usedEndCode", 160));
						list.add(addColumn("份数", "usedPieces", 40));
						list.add(addColumn("交单人", "usedMan", 60));
						list.add(addColumn("领用人", "innerObtainMan", 60));
						
						list.add(addColumn("日期", "blankOutDate", 80));
						list.add(addColumn("起始核销单号", "blankOutBeginCode", 160));
						list.add(addColumn("终至核销单号", "blankOutEndCode", 160));
						list.add(addColumn("份数", "blankOutPieces", 40));
						list.add(addColumn("交单人", "blankOutMan", 60));
						return list;
					}
				});
		TableColumnModel cm = jTable.getColumnModel();
		ColumnGroup gOuterObtain = new ColumnGroup("从外汇局领单情况");
		gOuterObtain.add(cm.getColumn(1));
		gOuterObtain.add(cm.getColumn(2));
		gOuterObtain.add(cm.getColumn(3));
		gOuterObtain.add(cm.getColumn(4));
		gOuterObtain.add(cm.getColumn(5));
		ColumnGroup gUsed = new ColumnGroup("使用状况");	
		gUsed.add(cm.getColumn(6));
		gUsed.add(cm.getColumn(7));
		gUsed.add(cm.getColumn(8));
		gUsed.add(cm.getColumn(9));
		gUsed.add(cm.getColumn(10));
		gUsed.add(cm.getColumn(11));
		ColumnGroup gBlankOut = new ColumnGroup("作废或遗失状况");			
		gBlankOut.add(cm.getColumn(12));
		gBlankOut.add(cm.getColumn(13));
		gBlankOut.add(cm.getColumn(14));
		gBlankOut.add(cm.getColumn(15));
		gBlankOut.add(cm.getColumn(16));
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(gOuterObtain);
		header.addColumnGroup(gUsed);
		header.addColumnGroup(gBlankOut);
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
