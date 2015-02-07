package com.bestway.client.selfcheck;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JRadioButton;
import java.awt.GridBagLayout;

public class DgClientBalanceDetailInfo extends JDialogBase {

	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JTableListModel tableModel = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel7 = null;
	private JTextField tfWareName = null;
	private JCalendarComboBox cbbBeginDate = null;
	private JTextField tfWareSpec = null;
	private JCalendarComboBox cbbEndDate = null;
	private JButton btnQuery = null;
	private JButton btnPrint = null;
	private JButton btnClose = null;
	private JSplitPane jSplitPane = null;
	private JPanel pnTop = null;
	private JLabel jLabel1 = null;
	/**
	 * This method initializes 
	 * 
	 */
	public DgClientBalanceDetailInfo() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(779, 567));
        this.setContentPane(getJContentPane());
        this.setTitle("转厂差额分表");
        initTable(new Vector());	//初始化表格
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
			jTable = new JTable();
		}
		return jTable;
	}
	
	
	/**
	 * 初始化表格
	 */
	private JTableListModel initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn(" 客户名称", "", 60));
						list.add(addColumn("商品名称", "", 80));
						list.add(addColumn("商品规格", "", 100));
						list.add(addColumn("单位", "", 100));
						list.add(addColumn("业务类型", "", 80));
						list.add(addColumn("其实期初", "",120));
						list.add(addColumn("1月", "",120));
						list.add(addColumn("2月", "", 80));
						list.add(addColumn("3月", "", 60));
						list.add(addColumn("4月", "", 60));
						list.add(addColumn("5月", "", 70));
						list.add(addColumn("6月", "", 60));
						list.add(addColumn("7月", "", 70));
						list.add(addColumn("8月", "", 70));
						list.add(addColumn("9月", "", 70));
						list.add(addColumn("10月", "", 60));
						list.add(addColumn("11月", "", 70));
						list.add(addColumn("12月", "", 70));
						list.add(addColumn("差额总值", "", 70));
						list.add(addColumn("年度合计", "", 60));
						list.add(addColumn("转厂期限", "", 70));
						list.add(addColumn("关封余量", "", 70));
						return list;
					}
				});
		return tableModel;
	}

	/**
	 * This method initializes tfWareName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfWareName() {
		if (tfWareName == null) {
			tfWareName = new JTextField();
			tfWareName.setBounds(new Rectangle(135, 15, 158, 20));
		}
		return tfWareName;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			// cbbBeginDate.setDate(findBeginData2());
			cbbBeginDate.setBounds(new Rectangle(135, 35, 158, 20));
		}
		return cbbBeginDate;
	}
	
	
	/**
	 * This method initializes tfWareSpec	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfWareSpec() {
		if (tfWareSpec == null) {
			tfWareSpec = new JTextField();
			tfWareSpec.setBounds(new Rectangle(430, 15, 158, 20));
		}
		return tfWareSpec;
	}

	/**
	 * This method initializes cbbEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(430, 35, 158, 20));
		}
		return cbbEndDate;
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
			btnQuery.setBounds(new Rectangle(621, 10, 74, 22));
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
			btnPrint.setBounds(new Rectangle(621, 34, 74, 22));
		}
		return btnPrint;
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
			btnClose.setBounds(new Rectangle(621, 58, 74, 22));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(1);
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setTopComponent(getPnTop());
			jSplitPane.setDividerLocation(86);
			
		}
		return jSplitPane;
	}

	/**
	 * This method initializes pnTop	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnTop() {
		if (pnTop == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(320, 35, 16, 18));
			jLabel1.setText("至");
			pnTop = new JPanel();
			pnTop.setLayout(null);
			pnTop.setBorder(
					BorderFactory.createTitledBorder(null, 
													"选项", 
													TitledBorder.DEFAULT_JUSTIFICATION, 
													TitledBorder.DEFAULT_POSITION, 
													new Font("Dialog", Font.BOLD, 12), 
													Color.blue));
			jLabel7 = new JLabel();
			jLabel7.setText("报表日期");
			jLabel7.setBounds(new Rectangle(365, 35, 62, 18));
			jLabel5 = new JLabel();
			jLabel5.setText("商品规格");
			jLabel5.setBounds(new Rectangle(365, 15, 62, 18));
			jLabel3 = new JLabel();
			jLabel3.setText("报表日期");
			jLabel3.setBounds(new Rectangle(72, 35, 62, 18));
			jLabel2 = new JLabel();
			jLabel2.setText("商品名称");
			jLabel2.setBounds(new Rectangle(72, 15, 62, 18));
			pnTop.add(jLabel2, null);
			pnTop.add(getTfWareName(), null);
			pnTop.add(jLabel3, null);
			pnTop.add(getCbbBeginDate(), null);
			pnTop.add(getCbbEndDate(), null);
			pnTop.add(jLabel7, null);
			pnTop.add(jLabel5, null);
			pnTop.add(getTfWareSpec(), null);
			pnTop.add(getBtnClose(), null);
			pnTop.add(getBtnPrint(), null);
			pnTop.add(getBtnQuery(), null);
			pnTop.add(jLabel1, null);
		}
		return pnTop;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
