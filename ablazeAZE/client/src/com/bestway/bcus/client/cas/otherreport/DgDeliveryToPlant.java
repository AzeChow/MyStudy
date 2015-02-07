package com.bestway.bcus.client.cas.otherreport;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import com.bestway.bcus.cas.entity.BalanceInfo;
import com.bestway.bcus.cas.entity.TempDeliveryToPlant;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.footer.JFooterTable;
import com.bestway.client.util.footer.TableFooterType;
import com.bestway.ui.render.TableMultiRowRender;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 查看送货转厂耗料情况窗体
 * 
 * @author 贺巍
 * 
 */
public class DgDeliveryToPlant extends JDialogBase {

	private JToolBar jJToolBarBar = null;

	private JPanel jPanel1 = null;

	private JLabel lbFlag = null;

	private JPanel jPanel = null;

	private JFooterScrollPane jScrollPane = null;

	private JFooterTable tbDeliveryToPlant = null;

	private JButton btnExit = null;

	private JLabel jLabel = null;

	private JTableListModel tableModel = null;

	/**
	 * 构造方法 This method initializes
	 * 
	 */
	public DgDeliveryToPlant(List<TempDeliveryToPlant> dataSource,
			BalanceInfo balanceInfo) {
		super();
		initialize();
		lbFlag.setText(balanceInfo == null ? "" : balanceInfo.getKey());
		if (dataSource.size() == 0) {
			initTable(new ArrayList());
		} else {
			initTable(dataSource);
		}

	}

	/**
	 * 初始化标题和显示尺寸 This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(793, 481));
		this.setContentPane(getJPanel());
		this.setTitle("查看送货转厂耗料情况");

	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.setPreferredSize(new Dimension(19, 34));
			jJToolBarBar.add(getJPanel1());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel = new JLabel();
			jLabel.setText("说明:察看送货转厂耗料情况");
			jLabel.setBounds(new Rectangle(291, -1, 283, 34));
			lbFlag = new JLabel();
			lbFlag.setText("JLabel");
			lbFlag.setBounds(new Rectangle(1, 0, 290, 34));
			lbFlag.setForeground(java.awt.Color.blue);
			lbFlag.setText("lbFlag");
			lbFlag.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/hint.gif")));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(lbFlag, null);
			jPanel1.add(jLabel, null);
			jPanel1.add(getBtnExit(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
			jPanel.add(getJJToolBarBar(), BorderLayout.NORTH);
		}
		return jPanel;
	}

	/**
	 * 初始化表
	 * 
	 * @param list
	 */
	private void initTable(final List list) {
		JTableListModelAdapter adapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("客户", "customer", 100));
				list.add(addColumn("成品名称", "ptName", 120));
				list.add(addColumn("规格", "ptSpec", 60));
				list.add(addColumn("单位", "ptUnitName", 50));
				list.add(addColumn("结转出口单据 ", "carriedOverExportBillsAmount",
						100, Double.class));
				list
						.add(addColumn("结转出口报关单",
								"carryOverExportDeclarationsAmount", 100,
								Double.class));
				list.add(addColumn("已送货未转厂数折料", "carryOverF7Difference", 100,
						Double.class));

				list.add(addColumn("已转厂未送货数折料", "carryOverF8Difference", 100,
						Double.class));
				list.add(addColumn("合同单耗", "contractConsumption", 80,
						Double.class));
				list.add(addColumn("合同损耗", "contracLoss", 80, Double.class));
				list.add(addColumn("最大合同号", "conrtact", 70));
				return list;
			}
		};
		tableModel = new JTableListModel(tbDeliveryToPlant, list, adapter);
		tbDeliveryToPlant
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableModel.clearFooterTypeInfo();
		tableModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		tableModel.addFooterTypeInfo(new TableFooterType(5,
				TableFooterType.SUM, ""));
		tableModel.addFooterTypeInfo(new TableFooterType(6,
				TableFooterType.SUM, ""));
		tableModel.addFooterTypeInfo(new TableFooterType(7,
				TableFooterType.SUM, ""));
		tableModel.addFooterTypeInfo(new TableFooterType(8,
				TableFooterType.SUM, ""));
		tableModel.addFooterTypeInfo(new TableFooterType(9,
				TableFooterType.SUM, ""));
		tableModel.addFooterTypeInfo(new TableFooterType(10,
				TableFooterType.SUM, ""));
		tbDeliveryToPlant.getColumnModel().getColumn(1).setCellRenderer(
				new TableMultiRowRender());
		tbDeliveryToPlant.getColumnModel().getColumn(2).setCellRenderer(
				new TableMultiRowRender());
		tbDeliveryToPlant.getColumnModel().getColumn(3).setCellRenderer(
				new TableMultiRowRender());
		tbDeliveryToPlant.getColumnModel().getColumn(4).setCellRenderer(
				new TableMultiRowRender());
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JFooterScrollPane();
			jScrollPane.setViewportView(getTbDeliveryToPlant());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbDeliveryToPlant
	 * 
	 * @return javax.swing.JTable
	 */
	private JFooterTable getTbDeliveryToPlant() {
		if (tbDeliveryToPlant == null) {
			tbDeliveryToPlant = new JFooterTable();
		}
		return tbDeliveryToPlant;
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
			btnExit.setBounds(new Rectangle(698, 2, 62, 25));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
