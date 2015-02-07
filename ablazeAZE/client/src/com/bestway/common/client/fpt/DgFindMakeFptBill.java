package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumnModel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.Request;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgFindMakeFptBill extends JDialogBase {

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JLabel jLabel = null;

	private JPanel jPanel1 = null;

	private JTextField tfCasBillNo = null;

	private JLabel jLabel1 = null;

	private JTextField tfPtNo = null;

	private JCalendarComboBox cbbCasBeginDate = null;

	private JCalendarComboBox cbbCasEndDate = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel41 = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JTextField tfFptCopNo = null;

	private JCalendarComboBox cbbFptBeginDate = null;

	private JLabel jLabel31 = null;

	private JCalendarComboBox cbbFptEndDate = null;

	private JLabel jLabel7 = null;

	private JTextField tfEmsNo = null;

	private JButton btnQuery = null;

	private JButton btnExit = null;

	private JLabel jLabel8 = null;

	private JTextField tfComplex = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JTableListModel tableModel;

	private FptManageAction fptManageAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgFindMakeFptBill() {
		super();
		initialize();
		this.cbbCasBeginDate.setDate(null);
		this.cbbCasEndDate.setDate(null);
		this.cbbFptBeginDate.setDate(null);
		this.cbbFptEndDate.setDate(null);
		this.fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
		initTable(new ArrayList());
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(741, 514));
		this.setTitle("单据中心单据转结转单据信息查询");
		this.setContentPane(getJContentPane());

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
			jSplitPane.setDividerLocation(120);
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setDividerSize(2);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(577, 9, 81, 18));
			jLabel7.setText("手册/帐册编号");
			jLabel = new JLabel();
			jLabel.setText("单据号");
			jLabel.setBounds(new Rectangle(17, 20, 50, 18));
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJPanel1(), null);
			jPanel.add(getJPanel2(), null);
			jPanel.add(jLabel7, null);
			jPanel.add(getTfEmsNo(), null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnExit(), null);
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
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel41 = new JLabel();
			jLabel41.setBounds(new Rectangle(252, 86, 26, 16));
			jLabel41.setText("日期");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(252, 68, 26, 16));
			jLabel4.setText("生效");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(17, 86, 50, 21));
			jLabel3.setText("结束日期");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(17, 63, 50, 19));
			jLabel2.setText("开始日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(17, 42, 50, 18));
			jLabel1.setText("料号");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(8, 3, 291, 113));
			jPanel1
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u5355\u636e\u4e2d\u5fc3\u5355\u636e\u67e5\u8be2\u6761\u4ef6",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.PLAIN, 12),
									new Color(51, 51, 51)));
			jPanel1.add(jLabel, null);
			jPanel1.add(getTfCasBillNo(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getTfPtNo(), null);
			jPanel1.add(getCbbCasBeginDate(), null);
			jPanel1.add(getCbbCasEndDate(), null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(jLabel41, null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCasBillNo() {
		if (tfCasBillNo == null) {
			tfCasBillNo = new JTextField();
			tfCasBillNo.setBounds(new Rectangle(69, 18, 181, 21));
		}
		return tfCasBillNo;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtNo() {
		if (tfPtNo == null) {
			tfPtNo = new JTextField();
			tfPtNo.setBounds(new Rectangle(69, 39, 181, 22));
		}
		return tfPtNo;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbCasBeginDate() {
		if (cbbCasBeginDate == null) {
			cbbCasBeginDate = new JCalendarComboBox();
			cbbCasBeginDate.setBounds(new Rectangle(69, 61, 181, 22));
		}
		return cbbCasBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbCasEndDate() {
		if (cbbCasEndDate == null) {
			cbbCasEndDate = new JCalendarComboBox();
			cbbCasEndDate.setBounds(new Rectangle(69, 85, 181, 22));
		}
		return cbbCasEndDate;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(235, 87, 37, 17));
			jLabel10.setText("日期");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(235, 70, 37, 17));
			jLabel9.setText("收发货");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(10, 40, 74, 21));
			jLabel8.setText("商品编码");
			jLabel31 = new JLabel();
			jLabel31.setBounds(new Rectangle(10, 86, 73, 20));
			jLabel31.setText("结束日期");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(10, 63, 73, 20));
			jLabel6.setText("开始日期");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(10, 18, 73, 20));
			jLabel5.setText("单据内部编号");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(new Rectangle(304, 3, 270, 112));
			jPanel2.setBorder(BorderFactory.createTitledBorder(null,
					"\u7ed3\u8f6c\u5355\u636e\u67e5\u8be2\u6761\u4ef6",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel2.add(jLabel5, null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(getTfFptCopNo(), null);
			jPanel2.add(getCbbFptBeginDate(), null);
			jPanel2.add(jLabel31, null);
			jPanel2.add(getCbbFptEndDate(), null);
			jPanel2.add(jLabel8, null);
			jPanel2.add(getTfComplex(), null);
			jPanel2.add(jLabel9, null);
			jPanel2.add(jLabel10, null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFptCopNo() {
		if (tfFptCopNo == null) {
			tfFptCopNo = new JTextField();
			tfFptCopNo.setBounds(new Rectangle(84, 17, 150, 21));
		}
		return tfFptCopNo;
	}

	/**
	 * This method initializes jCalendarComboBox2
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbFptBeginDate() {
		if (cbbFptBeginDate == null) {
			cbbFptBeginDate = new JCalendarComboBox();
			cbbFptBeginDate.setBounds(new Rectangle(84, 62, 150, 21));
		}
		return cbbFptBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox11
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbFptEndDate() {
		if (cbbFptEndDate == null) {
			cbbFptEndDate = new JCalendarComboBox();
			cbbFptEndDate.setBounds(new Rectangle(84, 87, 150, 21));
		}
		return cbbFptEndDate;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(new Rectangle(577, 30, 148, 20));
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(new Rectangle(584, 84, 64, 24));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = fptManageAction
							.findMakeFptBillFromCasBillInfo(new Request(
									CommonVars.getCurrUser(), true), tfEmsNo
									.getText().trim(), tfCasBillNo.getText()
									.trim(), tfPtNo.getText().trim(),
									tfFptCopNo.getText().trim(), tfComplex
											.getText().trim(), cbbCasBeginDate
											.getDate(),
									cbbCasEndDate.getDate(), cbbFptBeginDate
											.getDate(), cbbFptEndDate.getDate());
					initTable(list);
				}
			});
		}
		return btnQuery;
	}

	private void initTable(List list) {

		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("生成时间", "createDate", 100));
						list.add(addColumn("手册/账册号", "tempBillDetail.emsNo",
								120));
						list.add(addColumn("单据号",
								"tempBillDetail.billMaster.billNo", 160));
						list.add(addColumn("料号", "tempBillDetail.ptPart", 100));
						list.add(addColumn("生效日期",
								"tempBillDetail.billMaster.validDate", 100));
						list.add(addColumn("单据内部编号",
								"tempFptBillItem.fptBillHead.copNo", 100));
						list.add(addColumn("商品编码",
								"tempFptBillItem.complex.code", 160));
						list.add(addColumn("收发货日期",
								"tempFptBillItem.iossueDate", 100));
						return list;
					}
				});
		TableColumnModel cm = jTable.getColumnModel();
		ColumnGroup gBefore = new ColumnGroup("单据中心单据资料");		
		gBefore.add(cm.getColumn(3));
		gBefore.add(cm.getColumn(4));
		gBefore.add(cm.getColumn(5));
		ColumnGroup gAfter = new ColumnGroup("结转单据资料");		
		gAfter.add(cm.getColumn(6));
		gAfter.add(cm.getColumn(7));
		gAfter.add(cm.getColumn(8));
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(gBefore);
		header.addColumnGroup(gAfter);
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(new Rectangle(660, 84, 64, 24));
			btnExit.setText("退出");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplex() {
		if (tfComplex == null) {
			tfComplex = new JTextField();
			tfComplex.setBounds(new Rectangle(84, 39, 150, 21));
		}
		return tfComplex;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
