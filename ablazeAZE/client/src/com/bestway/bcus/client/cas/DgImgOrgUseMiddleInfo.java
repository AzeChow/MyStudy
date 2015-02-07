package com.bestway.bcus.client.cas;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.TableColumnModel;

import com.bestway.bcus.cas.entity.ImgOrgUseInfoBase;
import com.bestway.bcus.cas.entity.TempImgOrgUseInfo;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.TableColorRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgImgOrgUseMiddleInfo extends JDialogBase {

	private JPanel			jContentPane	= null;
	private JScrollPane		jScrollPane		= null;
	private JTable			jTable			= null;
	private JToolBar		jToolBar		= null;
	private JPanel			jPanel			= null;
	private JButton			btnExit			= null;
	private JLabel			jLabel			= null;
	private JTableListModel	tableModel		= null;
	private JLabel lbFlag = null;
	private Integer 				maximumFractionDigits 	= CasCommonVars.getOtherOption()
	.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
	.getOtherOption().getInOutMaximumFractionDigits();

	/**
	 * 显示中间过程信息窗体
	 * This method initializes
	 * 
	 */
	public DgImgOrgUseMiddleInfo(List<TempImgOrgUseInfo> dataSource,ImgOrgUseInfoBase imgOrgUseInfo) {
		super();
		initialize();
		lbFlag.setText(imgOrgUseInfo==null?"":imgOrgUseInfo.getKey());
		initTable(dataSource);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(766, 541);
		this.setTitle("海关帐年度报表中间过程");
		this.setContentPane(getJContentPane());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
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
			jTable = new GroupableHeaderTable();
		}
		return jTable;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJPanel());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			lbFlag = new JLabel();
			lbFlag.setBounds(new java.awt.Rectangle(6,0,290,34));
			lbFlag.setForeground(java.awt.Color.blue);
			lbFlag.setText("lbFlag");
			lbFlag.setIcon(new ImageIcon(getClass().getResource(
			"/com/bestway/bcus/client/resources/images/hint.gif")));
			jLabel = new JLabel();
			jLabel.setText("说明:以工厂料号为计算行的数据统计,用于测试年审报表数据的计算正确性");			
			jLabel.setBounds(new java.awt.Rectangle(299,0,399,34));
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(lbFlag, null);
		}
		return jPanel;
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
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	
	
	private void initTable(final List list) {
		JTableListModelAdapter adapter = new JTableListModelAdapter(maximumFractionDigits) {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();

				list.add(addColumn("料号", "ptPart", 150));
				list.add(addColumn("料件/规格/单位", "key", 180));
				
				list.add(addColumn("料件期初单", "f1001", 100,Double.class));
				list.add(addColumn("在产品期初单", "f1002", 100,Double.class));
				list.add(addColumn("直接进口", "f1003", 100,Double.class));
				list.add(addColumn("结转进口", "f1004", 100,Double.class));
				list.add(addColumn("国内购买", "f1005", 100,Double.class));
				list.add(addColumn("车间返回", "f1006", 100,Double.class));
				list.add(addColumn("料件盘盈单", "f1007", 100,Double.class));
				list.add(addColumn("受托加工进库", "f1008", 100,Double.class));
				list.add(addColumn("其他来源", "f1009", 100,Double.class));
				list.add(addColumn("料件转仓入库", "f1010", 100,Double.class));
				list.add(addColumn("海关征税进口", "f1011", 100,Double.class));
				list.add(addColumn("外发加工退回料件", "f1012", 100,Double.class));
				list.add(addColumn("外发加工返回料件", "f1013", 100,Double.class));				
				list.add(addColumn("委外期初单", "f1014", 100,Double.class));
				list.add(addColumn("已收货未结转期初单", "f1015", 120,Double.class));
				list.add(addColumn("已结转未收货期初单", "f1016", 120,Double.class));
				list.add(addColumn("车间领用", "f1101", 100,Double.class));
				list.add(addColumn("料件退换", "f1102", 100,Double.class));
				list.add(addColumn("料件复出", "f1103", 100,Double.class));
				list.add(addColumn("料件盘亏单", "f1104", 100,Double.class));
				list.add(addColumn("料件转仓出库", "f1105", 100,Double.class));
				list.add(addColumn("结转料件退货单", "f1106", 100,Double.class));
				list.add(addColumn("其他料件退货单", "f1107", 100,Double.class));
				list.add(addColumn("其他领用", "f1108", 100,Double.class));
				list.add(addColumn("受托加工领用", "f1109", 100,Double.class));
				list.add(addColumn("外发加工出库", "f1110", 100,Double.class));
				list.add(addColumn("受托加工退回料件", "f1111", 100,Double.class));

				list.add(addColumn("成品期初单", "f2001", 100,Double.class));
				list.add(addColumn("车间入库", "f2002", 100,Double.class));
				list.add(addColumn("退厂返工", "f2003", 100,Double.class));
				list.add(addColumn("成品盘盈单", "f2004", 100,Double.class));
				list.add(addColumn("成品转仓入库", "f2005", 100,Double.class));
				list.add(addColumn("受托加工车间入库", "f2007", 100,Double.class));
				list.add(addColumn("其他成品退货单", "f2008", 100,Double.class));
				list.add(addColumn("结转成品退货单", "f2009", 100,Double.class));
				list.add(addColumn("受托加工退回成品", "f2010", 100,Double.class));
				list.add(addColumn("已交货未结转期初单", "f2011", 120,Double.class));
				list.add(addColumn("已交货未结转期初单", "f2012", 120,Double.class));
				list.add(addColumn("直接出口", "f2101", 100,Double.class));
				list.add(addColumn("结转出口", "f2102", 100,Double.class));
				list.add(addColumn("返回车间", "f2103", 100,Double.class));
				list.add(addColumn("返工复出", "f2104", 100,Double.class));
				list.add(addColumn("成品盘亏单", "f2105", 100,Double.class));
				list.add(addColumn("海关批准内销", "f2106", 100,Double.class));
				list.add(addColumn("其他内销", "f2107", 100,Double.class));
				list.add(addColumn("其他处理", "f2108", 100,Double.class));
				list.add(addColumn("成品转仓出库", "f2109", 100,Double.class));
				list.add(addColumn("受托加工返回", "f2110", 100,Double.class));
				
				list.add(addColumn("半成品期初单", "f4009", 100,Double.class));
				list.add(addColumn("半成品入库", "f4001", 100,Double.class));
				list.add(addColumn("半成品盘盈单", "f4002", 100,Double.class));
				list.add(addColumn("委外加工入库", "f4003", 100,Double.class));
				list.add(addColumn("半成品出库", "f4101", 100,Double.class));
				list.add(addColumn("半成品盘亏单", "f4102", 100,Double.class));
				list.add(addColumn("委外加工出库", "f4103", 100,Double.class));
				list.add(addColumn("外发加工返修半成品", "f4104", 120,Double.class));
				list.add(addColumn("外发加工领料", "f4105", 100,Double.class));
				
				list.add(addColumn("残次品期初单", "f5002", 100,Double.class));
				
				list.add(addColumn("2002车间入库损耗", "f2002Waste", 125,Double.class));
				list.add(addColumn("4003外发加工入库损耗", "f4003Waste", 125,Double.class));
				list.add(addColumn("2007受托加工车间入库损耗", "f2007Waste", 150,Double.class));
				list.add(addColumn("2103返回车间损耗", "f2103Waste", 120,Double.class));
				list.add(addColumn("2110受托加工返回损耗", "f2110Waste", 145,Double.class));
				list.add(addColumn("4104外发加工返修半成品损耗", "f4104Waste", 145,Double.class));
				list.add(addColumn("4105外发加工领料损耗", "f4105Waste", 145,Double.class));		
				
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, adapter);
		TableColumnModel cm = jTable.getColumnModel();
		
		ColumnGroup g1 = new ColumnGroup(" 料件 ");		
		g1.add(cm.getColumn(3));
		g1.add(cm.getColumn(4));
		g1.add(cm.getColumn(5));
		g1.add(cm.getColumn(6));		
		g1.add(cm.getColumn(7));
		g1.add(cm.getColumn(8));
		g1.add(cm.getColumn(9));
		g1.add(cm.getColumn(10));
		g1.add(cm.getColumn(11));
		g1.add(cm.getColumn(12));
		g1.add(cm.getColumn(13));
		g1.add(cm.getColumn(14));
		g1.add(cm.getColumn(15));
		g1.add(cm.getColumn(16));
		g1.add(cm.getColumn(17));
		g1.add(cm.getColumn(18));
		g1.add(cm.getColumn(19));
		g1.add(cm.getColumn(20));
		g1.add(cm.getColumn(21));
		g1.add(cm.getColumn(22));
		g1.add(cm.getColumn(23));
		g1.add(cm.getColumn(24));
		g1.add(cm.getColumn(25));
		g1.add(cm.getColumn(26));
		g1.add(cm.getColumn(27));
		g1.add(cm.getColumn(28));
		g1.add(cm.getColumn(29));
		
		ColumnGroup g2 = new ColumnGroup("成品");
		g2.add(cm.getColumn(30));
		g2.add(cm.getColumn(31));
		g2.add(cm.getColumn(32));
		g2.add(cm.getColumn(33));
		g2.add(cm.getColumn(34));
		g2.add(cm.getColumn(35));
		g2.add(cm.getColumn(36));
		g2.add(cm.getColumn(37));
		g2.add(cm.getColumn(38));
		g2.add(cm.getColumn(39));
		g2.add(cm.getColumn(40));
		g2.add(cm.getColumn(41));
		g2.add(cm.getColumn(42));
		g2.add(cm.getColumn(43));
		g2.add(cm.getColumn(44));
		g2.add(cm.getColumn(45));
		g2.add(cm.getColumn(46));
		g2.add(cm.getColumn(47));
		g2.add(cm.getColumn(48));
		g2.add(cm.getColumn(49));
		g2.add(cm.getColumn(50));
		
		ColumnGroup g3 = new ColumnGroup("半成品");
		g3.add(cm.getColumn(51));
		g3.add(cm.getColumn(52));
		g3.add(cm.getColumn(53));
		g3.add(cm.getColumn(54));
		g3.add(cm.getColumn(55));
		g3.add(cm.getColumn(56));
		g3.add(cm.getColumn(57));
		g3.add(cm.getColumn(58));
		g3.add(cm.getColumn(59));
		
		ColumnGroup g4 = new ColumnGroup("残次品");
		g4.add(cm.getColumn(60));
		
		
		ColumnGroup g5 = new ColumnGroup("损耗");
		
		g5.add(cm.getColumn(61));
		g5.add(cm.getColumn(62));
		g5.add(cm.getColumn(63));
		g5.add(cm.getColumn(64));
		g5.add(cm.getColumn(65));
		g5.add(cm.getColumn(66));
		g5.add(cm.getColumn(67));
		
		
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(g1);
		header.addColumnGroup(g2);
		header.addColumnGroup(g3);
		header.addColumnGroup(g5);
		
		TableColorRender.setTableRowColor(jTable, tableModel
				.getRowCount() - 1);
	}

}
