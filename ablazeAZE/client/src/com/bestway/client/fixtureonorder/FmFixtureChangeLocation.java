package com.bestway.client.fixtureonorder;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.fixtureonorder.action.CheckFixAuthorityAction;
import com.bestway.fixtureonorder.action.FixtureContractAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author fhz 设备移动情况 edit by 陈井彬
 */
public class FmFixtureChangeLocation extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTabbedPane jTabbedPane = null;

	/**
	 * 移动向导
	 */
	private JButton btnGuide = null;

	/**
	 * 关闭按钮
	 */
	private JButton btnExit = null;

	private JScrollPane jScrollPane = null;

	/**
	 * 设备状况表格
	 */
	private JTable tbChangeResult = null;

	private JScrollPane jScrollPane1 = null;

	/**
	 * 移动单据表格
	 */
	private JTable tbChangeBill = null;

	/**
	 * 合同操作接口
	 */
	private FixtureContractAction fixtureContractAction;

	/**
	 * 对应关系操作接口
	 */
	private CheckFixAuthorityAction checkFixAuthorityAction;

	/**
	 * 设备状况表格模型
	 */
	private JTableListModel tableModelChangeResult;

	/**
	 * 移动单据表格模型
	 */
	private JTableListModel tableModelChangeBill;

	/**
	 * This method initializes
	 * 
	 */
	public FmFixtureChangeLocation() {
		super();
		initialize();
		fixtureContractAction = (FixtureContractAction) CommonVars
				.getApplicationContext().getBean("fixtureContractAction");
		checkFixAuthorityAction = (CheckFixAuthorityAction) CommonVars
				.getApplicationContext().getBean("checkFixAuthorityAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(658, 330));
		this.setTitle("设备移动");
		this.setContentPane(getJContentPane());

		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						showData();
					}
				});
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
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
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
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("设备状况", null, getJScrollPane(), null);
			jTabbedPane.addTab("移动单据", null, getJScrollPane1(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							showData();
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnGuide == null) {
			btnGuide = new JButton();
			btnGuide.setText("移动向导");
			btnGuide.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkMoveGuideLocation(new Request(
							CommonVars.getCurrUser()));
					DgFixtureChangeLocation dg = new DgFixtureChangeLocation();
					dg.setVisible(true);
					showData();
				}
			});
		}
		return btnGuide;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmFixtureChangeLocation.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbChangeResult());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbChangeResult() {
		if (tbChangeResult == null) {
			tbChangeResult = new JTable();
		}
		return tbChangeResult;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbChangeBill());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbChangeBill() {
		if (tbChangeBill == null) {
			tbChangeBill = new JTable();
		}
		return tbChangeBill;
	}

	/**
	 * 显示数据
	 */
	private void showData() {
		if (jTabbedPane.getSelectedIndex() == 0) {
			List lsResult = fixtureContractAction
					.findFixtureLocationResultInfo(new Request(CommonVars
							.getCurrUser()));
			initResultTable(lsResult);
		} else {
			List lsBill = fixtureContractAction
					.findFixtureLocationChangeBillInfo(new Request(CommonVars
							.getCurrUser()));
			initBillTable(lsBill);
		}
	}

	/**
	 * 初始化设备状况表格
	 * 
	 * @param list
	 */
	private void initResultTable(List list) {
		JTableListModel.dataBind(tbChangeResult, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = (List<Object>) (new Vector());
						list.add(addColumn("位置", "location.name", 100));
						list.add(addColumn("报关单号", "customsDeclarationCode",
								100));
						list.add(addColumn("进口日期 ", "impExpDate", 100));
						list.add(addColumn("设备名称", "commName", 100));
						list.add(addColumn("规格", "commSpec", 100));
						list.add(addColumn("设备性质", "fixKind", 100));
						list.add(addColumn("报关单数量", "amount", 100));
						list.add(addColumn("报关单项号", "customsDeclaItemNo", 60));
						list.add(addColumn("报关单流水号", "customsBillSeqNo", 60));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("手册编号", "emsNo", 100));
						list.add(addColumn("协议号", "agreementNo", 100));

						return list;
					}
				});
		tbChangeResult.getColumnModel().getColumn(6).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText(changeFixKind(value));
						return this;
					}

				});
		tableModelChangeResult = (JTableListModel) tbChangeResult.getModel();
	}

	/**
	 * 初始化移动单据表格
	 * 
	 * @param list
	 */
	private void initBillTable(List list) {
		JTableListModel.dataBind(tbChangeBill, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = (List<Object>) (new Vector());
						list.add(addColumn("报关单号", "customsDeclarationCode",
								100));
						list.add(addColumn("进口日期 ", "impExpDate", 100));
						list.add(addColumn("设备名称", "commName", 100));
						list.add(addColumn("规格", "commSpec", 100));
						list.add(addColumn("设备性质", "fixKind", 100));
						list.add(addColumn("报关单数量", "amount", 100));
						list.add(addColumn("报关单项号", "customsDeclaItemNo", 60));
						list.add(addColumn("报关单流水号", "customsBillSeqNo", 60));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("手册编号", "emsNo", 100));
						list.add(addColumn("协议号", "agreementNo", 100));
						list.add(addColumn("移动类型", "changeType", 100));
						list.add(addColumn("原位置", "oldLocation.name", 100));
						list.add(addColumn("目标位置", "newLocation.name", 100));
						list.add(addColumn("单据号码", "billCode", 100));
						list.add(addColumn("经手人", "handMan", 100));
						return list;
					}
				});
		tbChangeBill.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText(changeFixKind(value));
						return this;
					}

				});
		tbChangeBill.getColumnModel().getColumn(12).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText(changeChangeType(value));
						return this;
					}

				});
		tableModelChangeBill = (JTableListModel) tbChangeBill.getModel();
	}

	/**
	 * 状态转换
	 * 
	 * @param value
	 * @return
	 */
	private String changeFixKind(Object value) {
		if (value == null)
			return "";
		else
			return Integer.valueOf(value.toString()) == 0 ? "不作价设备" : "借用设备";

	}

	/**
	 * 把value转换成对应的设备状态
	 * 
	 * @param value
	 * @return
	 */
	private String changeChangeType(Object value) {
		if (value == null)
			return "";
		else {
			if (Integer.valueOf(value.toString()) == 0)
				return "设备报关单进厂";
			if (Integer.valueOf(value.toString()) == 1)
				return "设备厂内移动";
			if (Integer.valueOf(value.toString()) == 2)
				return "设备厂内增加";
			if (Integer.valueOf(value.toString()) == 3)
				return "设备厂内减少";
			return "";
		}

	}
} // @jve:decl-index=0:visual-constraint="10,10"
