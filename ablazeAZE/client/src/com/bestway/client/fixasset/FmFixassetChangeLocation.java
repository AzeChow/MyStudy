package com.bestway.client.fixasset;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.fixasset.action.FixAssetAction;
import com.bestway.fixtureonorder.action.CheckFixAuthorityAction;
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

public class FmFixassetChangeLocation extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTabbedPane jTabbedPane = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JScrollPane jScrollPane = null;

	private JTable tbChangeResult = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbChangeBill = null;

	private FixAssetAction fixAssetAction;

	private JTableListModel tableModelChangeResult;

	private JTableListModel tableModelChangeBill;

	private CheckFixAuthorityAction checkFixAuthorityAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmFixassetChangeLocation() {
		super();
		initialize();
		fixAssetAction = (FixAssetAction) CommonVars.getApplicationContext()
				.getBean("fixAssetAction");
		checkFixAuthorityAction = (CheckFixAuthorityAction) CommonVars
				.getApplicationContext().getBean("checkFixAuthorityAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(658, 330));
		this.setTitle("设备异动");
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
			jTabbedPane.addTab("异动单据", null, getJScrollPane1(), null);
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
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("移动向导");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction
							.checkFixassetChangeLocation(new Request(CommonVars
									.getCurrUser()));
					DgFixassetChangeLocation dg = new DgFixassetChangeLocation();
					dg.setVisible(true);
					showData();
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
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmFixassetChangeLocation.this.doDefaultCloseAction();
				}
			});
		}
		return jButton1;
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

	private void showData() {
		if (jTabbedPane.getSelectedIndex() == 0) {
			List lsResult = fixAssetAction
					.findFixassetLocationResultInfo(new Request(CommonVars
							.getCurrUser()));
			initResultTable(lsResult);
		} else {
			List lsBill = fixAssetAction
					.findFixassetLocationChangeBillInfo(new Request(CommonVars
							.getCurrUser()));
			initBillTable(lsBill);
		}
	}

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
						list.add(addColumn("报关单数量", "amount", 100));
						list.add(addColumn("报关单项号", "customsDeclaItemNo", 100));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("协议号", "agreementNo", 100));
						list.add(addColumn("报关单流水号", "customsBillSeqNo", 100));
						list.add(addColumn("移动类型", "changeType", 100));
						list.add(addColumn("原位置", "oldLocation.name", 100));
						list.add(addColumn("目标位置", "newLocation.name", 100));
						list.add(addColumn("单据号码", "billCode", 100));
						list.add(addColumn("经手人", "handMan", 100));
						return list;
					}
				});
		tbChangeBill.getColumnModel().getColumn(10).setCellRenderer(
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
		tableModelChangeBill = (JTableListModel) tbChangeResult.getModel();
	}

	private String changeChangeType(Object value){
		if(value==null)
			return "";
		else{
			if(Integer.valueOf(value.toString())==0)
				return "设备报关单进厂";
			if(Integer.valueOf(value.toString())==1)
				return "设备厂内移动";
			if(Integer.valueOf(value.toString())==2)
				return "设备厂内增加";
			if(Integer.valueOf(value.toString())==3)
				return "设备厂内减少";
			return "";
		}
			
	}
	
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
						list.add(addColumn("报关单数量", "amount", 100));
						list.add(addColumn("报关单项号", "customsDeclaItemNo", 100));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("协议号", "agreementNo", 100));
						list.add(addColumn("报关单流水号", "customsBillSeqNo", 100));
						return list;
					}
				});
		tableModelChangeResult = (JTableListModel) tbChangeResult.getModel();
	}
} // @jve:decl-index=0:visual-constraint="10,10"
