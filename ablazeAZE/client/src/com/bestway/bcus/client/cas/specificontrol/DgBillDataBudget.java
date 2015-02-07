/*
 * Created on 2005-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.specificontrol;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgBillDataBudget extends JDialogBase {

	private JPanel		jContentPane		= null;

	private JTabbedPane	jTabbedPane			= null;

	private JPanel		pn1					= null;

	private JPanel		pn2					= null;

	private CasAction	casAction			= null;

	private JToolBar	jToolBar			= null;

	private JButton		btnTransfer			= null;

	private JButton		btnExit				= null;

	private JTable		tb1					= null;

	private JScrollPane	jScrollPane			= null;

	private JTable		tb2					= null;

	private JScrollPane	jScrollPane1		= null;

	private JCheckBox	cbShowTransferBill1	= null;

	private JLabel		jLabel2				= null;

	private JComboBox	cbbImpExpType1		= null;

	private JLabel		jLabel3				= null;

	private JComboBox	cbbMonth1			= null;

	private JLabel		jLabel4				= null;

	private JPanel		jPanel				= null;

	/**
	 * This method initializes
	 * 
	 */
	public DgBillDataBudget() {
		super();
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initUIComponents();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("单据转换");
		this.setContentPane(getJContentPane());
		this.setSize(755, 539);
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
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}
	

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.TOP);
			jTabbedPane.addTab("出口单转入库单", null, getPn1(), null);
			jTabbedPane.addTab("入库单转领料单和边角料入库单", null, getPn2(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							setState();
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn1() {
		if (pn1 == null) {
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			pn1 = new JPanel();
			pn1.setLayout(null);
			pn1.setToolTipText("单据对应");
			jLabel2.setBounds(199, 9, 30, 23);
			jLabel2.setText("月份:");
			jLabel3.setBounds(5, 9, 57, 23);
			jLabel3.setText("单据类型:");
			jLabel4.setBounds(14, 241, 135, 21);
			jLabel4.setText("生成的单据");
			pn1.add(getJScrollPane(), null);
			pn1.add(getJScrollPane1(), null);
			pn1.add(getJCheckBox(), null);
			pn1.add(jLabel2, null);
			pn1.add(getJComboBox2(), null);
			pn1.add(jLabel3, null);
			pn1.add(getJComboBox3(), null);
			pn1.add(jLabel4, null);
		}
		return pn1;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JLabel	jLabel40	= null;

	private JLabel	jLabel30	= null;

	private JPanel getPn2() {
		if (pn2 == null) {
			jLabel6 = new JLabel();
			jLabel5 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jLabel40 = new JLabel();
			jLabel30 = new JLabel();
			pn2 = new JPanel();
			pn2.setLayout(null);
			jLabel30.setBounds(10, 9, 54, 23);
			jLabel30.setText("单据类型:");
			jLabel40.setBounds(8, 163, 53, 16);
			jLabel40.setText("领料单:");
			pn2.setToolTipText("单据对应");
			jLabel.setBounds(184, 9, 30, 23);
			jLabel.setText("月份:");
			jLabel1.setBounds(304, 9, 53, 23);
			jLabel1.setText("领用仓库:");
			jLabel5.setBounds(448, 9, 66, 23);
			jLabel5.setText("边角料仓库:");
			jLabel6.setBounds(5, 300, 87, 15);
			jLabel6.setText("边角料入库单:");
			pn2.add(getJScrollPane2(), null);
			pn2.add(getJScrollPane3(), null);
			pn2.add(getJCheckBox2(), null);
			pn2.add(getJComboBox4(), null);
			pn2.add(jLabel30, null);
			pn2.add(getJComboBox5(), null);
			pn2.add(jLabel40, null);

			pn2.add(jLabel, null);
			pn2.add(jLabel1, null);
			pn2.add(jLabel5, null);
			pn2.add(getJComboBox(), null);
			pn2.add(getJComboBox1(), null);
			pn2.add(getJScrollPane4(), null);
			pn2.add(jLabel6, null);
		}
		return pn2;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox	cbShowTransferBill2	= null;

	private JCheckBox getJCheckBox2() {
		if (cbShowTransferBill2 == null) {
			cbShowTransferBill2 = new JCheckBox();
			cbShowTransferBill2.setBounds(623, 9, 110, 23);
			cbShowTransferBill2.setText("显示已结转单据");
		}
		return cbShowTransferBill2;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox	cbMakeInMaterialBill	= null;

	private JCheckBox getJCheckBox3() {
		if (cbMakeInMaterialBill == null) {
			cbMakeInMaterialBill = new JCheckBox();
			cbMakeInMaterialBill.setText("是否生成领料单");
			cbMakeInMaterialBill.setVisible(false);
			cbMakeInMaterialBill.setBounds(132, 2, 110, 17);
		}
		return cbMakeInMaterialBill;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setPreferredSize(new java.awt.Dimension(91, 45));
			jToolBar.add(getJPanel());
			jToolBar.add(getJButton3());
			jToolBar.add(getJButton4());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (btnTransfer == null) {
			btnTransfer = new JButton();
			btnTransfer.setText("转换");
		}
		return btnTransfer;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
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

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (tb1 == null) {
			tb1 = new JTable();
		}
		return tb1;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable	tb3	= null;

	private JTable getJTable2() {
		if (tb3 == null) {
			tb3 = new JTable();
		}
		return tb3;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable	tb5	= null;

	private JTable getJTable3() {
		if (tb5 == null) {
			tb5 = new JTable();
		}
		return tb5;
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
			jScrollPane.setBounds(0, 42, 742, 195);
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane	jScrollPane2	= null;

	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTable2());
			jScrollPane2.setBounds(0, 42, 742, 120);
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane	jScrollPane3	= null;

	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getJTable3());
			jScrollPane3.setBounds(0, 315, 742, 120);
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (tb2 == null) {
			tb2 = new JTable();
		}
		return tb2;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(0, 265, 742, 182);
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (cbShowTransferBill1 == null) {
			cbShowTransferBill1 = new JCheckBox();
			cbShowTransferBill1.setBounds(348, 9, 142, 23);
			cbShowTransferBill1.setText("显示已结转的单据");
			cbShowTransferBill1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// Auto-generated
							// Event
							// stub
							// actionPerformed()
						}
					});
		}
		return cbShowTransferBill1;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox2() {
		if (cbbImpExpType1 == null) {
			cbbImpExpType1 = new JComboBox();
			cbbImpExpType1.setBounds(62, 9, 131, 23);
		}
		return cbbImpExpType1;
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox3() {
		if (cbbMonth1 == null) {
			cbbMonth1 = new JComboBox();
			cbbMonth1.setBounds(228, 9, 103, 23);
		}
		return cbbMonth1;
	}

	private JComboBox	cbbImpExpType2	= null;

	private JComboBox getJComboBox4() {
		if (cbbImpExpType2 == null) {
			cbbImpExpType2 = new JComboBox();
			cbbImpExpType2.setBounds(62, 9, 119, 23);
		}
		return cbbImpExpType2;
	}

	private JComboBox	cbbMonth2				= null;

	private JCheckBox	cbCustomer				= null;

	private JLabel		jLabel					= null;

	private JLabel		jLabel1					= null;

	private JLabel		jLabel5					= null;

	private JComboBox	cbbWareSet2				= null;

	private JComboBox	cbbWareSet1				= null;

	private JCheckBox	cbBudgetMaterielBill	= null;

	private JCheckBox	cbReverseUnitWaste		= null;

	private JCheckBox	cbMakeInRemainBill		= null;

	private JCheckBox	cbBudgetInRemainBill	= null;

	private JTable		tb4						= null;

	private JScrollPane	jScrollPane4			= null;

	private JLabel		jLabel6					= null;

	private JComboBox getJComboBox5() {
		if (cbbMonth2 == null) {
			cbbMonth2 = new JComboBox();
			cbbMonth2.setBounds(213, 9, 82, 23);
		}
		return cbbMonth2;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJCheckBox1(), null);
			jPanel.add(getJCheckBox3(), null);
			jPanel.add(getJCheckBox4(), null);
			jPanel.add(getJCheckBox5(), null);
			jPanel.add(getJCheckBox6(), null);
			jPanel.add(getJCheckBox7(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox1() {
		if (cbCustomer == null) {
			cbCustomer = new JCheckBox();
			cbCustomer.setBounds(5, 2, 122, 17);
			cbCustomer.setText("保持客户名称不变");
		}
		return cbCustomer;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (cbbWareSet2 == null) {
			cbbWareSet2 = new JComboBox();
			cbbWareSet2.setBounds(514, 9, 88, 23);
		}
		return cbbWareSet2;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (cbbWareSet1 == null) {
			cbbWareSet1 = new JComboBox();
			cbbWareSet1.setBounds(356, 9, 88, 23);
		}
		return cbbWareSet1;
	}

	/**
	 * This method initializes jCheckBox4
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox4() {
		if (cbBudgetMaterielBill == null) {
			cbBudgetMaterielBill = new JCheckBox();
			cbBudgetMaterielBill.setBounds(245, 2, 115, 17);
			cbBudgetMaterielBill.setVisible(false);
			cbBudgetMaterielBill.setText("分别预算领料单");
		}
		return cbBudgetMaterielBill;
	}

	/**
	 * This method initializes jCheckBox5
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox5() {
		if (cbReverseUnitWaste == null) {
			cbReverseUnitWaste = new JCheckBox();
			cbReverseUnitWaste.setBounds(132, 21, 145, 17);
			cbReverseUnitWaste.setVisible(false);
			cbReverseUnitWaste.setText("反推单耗为0的料件");
		}
		return cbReverseUnitWaste;
	}

	/**
	 * This method initializes jCheckBox6
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox6() {
		if (cbMakeInRemainBill == null) {
			cbMakeInRemainBill = new JCheckBox();
			cbMakeInRemainBill.setBounds(359, 2, 147, 17);
			cbMakeInRemainBill.setVisible(false);
			cbMakeInRemainBill.setText("是否生成边角料入库单");
		}
		return cbMakeInRemainBill;
	}

	/**
	 * This method initializes jCheckBox7
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox7() {
		if (cbBudgetInRemainBill == null) {
			cbBudgetInRemainBill = new JCheckBox();
			cbBudgetInRemainBill.setBounds(508, 2, 149, 17);
			cbBudgetInRemainBill.setVisible(false);
			cbBudgetInRemainBill.setText("分别预算边角料入库单");
		}
		return cbBudgetInRemainBill;
	}

	/**
	 * This method initializes jTable4
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable4() {
		if (tb4 == null) {
			tb4 = new JTable();
		}
		return tb4;
	}

	/**
	 * This method initializes jScrollPane4
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setBounds(0, 180, 742, 120);
			jScrollPane4.setViewportView(getJTable4());
		}
		return jScrollPane4;
	}

	/**
	 * 初始化数据
	 * 
	 */
	private void initUIComponents() {
		//
		// 初始化月份控件
		//
		initMonthUI(this.cbbMonth1);
		initMonthUI(this.cbbMonth2);
		this.cbbMonth1.setSelectedItem(null);
		this.cbbMonth2.setSelectedItem(null);
		//
		// 初始化仓库
		//
		 MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
			.getApplicationContext().getBean("materialManageAction");
		List list = materialManageAction.findWareSet(new Request(CommonVars
				.getCurrUser(), true));
		DefaultComboBoxModel model = new DefaultComboBoxModel(list.toArray()); 
		this.cbbWareSet1.setModel(model);
		this.cbbWareSet1.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbWareSet1, "code", "name");

		this.cbbWareSet2.setModel(model);
		this.cbbWareSet2.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbWareSet2, "code", "name");
		
		
		//
		// 初始化所有表
		//
		initBillMaster(new ArrayList(), tb1);
		initBillMaster(new ArrayList(), tb2);
		initBillMaster(new ArrayList(), tb3);
		initBillMaster(new ArrayList(), tb4);
		initBillMaster(new ArrayList(), tb5);
	}

	/**
	 * 打印
	 */
	private void printReport() {
		JOptionPane.showMessageDialog(this, "没有数据可以打印!!", "提示",
				JOptionPane.INFORMATION_MESSAGE);
		return;
	}

	/**
	 * 查询
	 */
	private void search() {
		if (this.jTabbedPane.getSelectedIndex() == 0) {

		} else if (this.jTabbedPane.getSelectedIndex() == 1) {

		}
	}

	/**
	 * 设置状态
	 * 
	 */
	private void setState() {
		boolean isVisible = this.jTabbedPane.getSelectedIndex() == 1;
		cbMakeInMaterialBill.setVisible(isVisible);
		cbBudgetMaterielBill.setVisible(isVisible);
		cbMakeInRemainBill.setVisible(isVisible);
		cbBudgetInRemainBill.setVisible(isVisible);
		cbReverseUnitWaste.setVisible(isVisible);
		cbCustomer.setVisible(!isVisible);
	}

	/**
	 * 初始化月份控件
	 * 
	 * @param cbb
	 */
	private void initMonthUI(JComboBox cbb) {
		for (int i = 0; i < 12; i++) {
			cbb.addItem(i + 1);
		}
		cbb.addItem("全年");
	}

	/**
	 * 初始化BillMaster
	 * 
	 */
	private void initBillMaster(List list, JTable tb) {
		new JTableListModel(tb, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("类别", "billType.name", 100));
				list.add(addColumn("单据号", "billNo", 100));
				list.add(addColumn("仓库", "wareSet.name", 80));
				list.add(addColumn("客户名称", "scmCoc.name", 100));
				list.add(addColumn("有效", "isValid", 50));
				list.add(addColumn("生效日期", "validDate", 100));
				list.add(addColumn("是否记帐", "keepAccounts", 50));
				return list;
			}
		});
		tb.getColumnModel().getColumn(5).setCellRenderer(
				new TableCheckBoxRender());
		tb.getColumnModel().getColumn(7).setCellRenderer(
				new TableCheckBoxRender());
	}

}
