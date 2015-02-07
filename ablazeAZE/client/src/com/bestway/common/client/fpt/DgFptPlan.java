/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgFptPlan extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTableListModel tableModel = null;

	private List list = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane1 = null;

	private JLabel jLabel = null;

	private JButton jButton = null;

	private List lsResult = null;

	private JButton jButton1 = null;

	private FptManageAction fptManageAction = null;

	/**
	 * This is the default constructor
	 */
	public DgFptPlan() {
		super();
		this.fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
		initialize();

	}

	private void initUIComponents() {

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(744, 454);
		fptManageAction.findTransferPlan(new Request(CommonVars
				.getCurrUser()));
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {

				initUIComponents();
				list = new Vector();
				initTable(list);
			}
		});
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("厂商编号", "bill1", 80));
						list.add(addColumn("厂商名称", "bill2", 150));
						list.add(addColumn("商品名称", "bill3", 100));
						list.add(addColumn("商品编码", "bill4", 80));
						list.add(addColumn("单位", "bill5", 60));
						list.add(addColumn("关封使用顺序", "bill6", 80));
						// list.add(addColumn("转入合同号", "bill5", 70));
						list.add(addColumn("关封申请数量", "billSum1", 80));
						list.add(addColumn("关封确认日期", "bill8", 80));
						list.add(addColumn("收货未结转", "billSum2", 70));
						list.add(addColumn("关封结余(备案-送货)", "billSum3", 150));
						list.add(addColumn("下周应安排备案27笔", "billSum5", 150));
						list.add(addColumn("下周实际安排备案", "billSum5", 120));
						list.add(addColumn("备注", "billSum5", 60));
						return list;
					}
				});
		/*
		 * jTable.getColumnModel().getColumn(12).setCellRenderer( new
		 * DefaultTableCellRenderer() { public Component
		 * getTableCellRendererComponent( JTable table, Object value, boolean
		 * isSelected, boolean hasFocus, int row, int column) {
		 * super.getTableCellRendererComponent(table, value, isSelected,
		 * hasFocus, row, column); super.setText((value == null) ? "" :
		 * castValue(value)); return this; }
		 * 
		 * private String castValue(Object value) { String returnValue = ""; if
		 * (String.valueOf(value).trim().equals("")) { return ""; } if
		 * (value.equals(Integer.valueOf( ImpExpType.DIRECT_IMPORT).toString()))
		 * returnValue = "料件进口"; else if (value.equals(Integer.valueOf(
		 * ImpExpType.TRANSFER_FACTORY_IMPORT).toString())) returnValue =
		 * "料件转厂"; else if (value.equals(String
		 * .valueOf(ImpExpType.BACK_MATERIEL_EXPORT))) returnValue = "退料出口";
		 * else if (value.equals(String .valueOf(ImpExpType.DIRECT_EXPORT)))
		 * returnValue = "成品出口"; else if (value.equals(String
		 * .valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT))) returnValue = "转厂出口";
		 * else if (value.equals(Integer.valueOf(
		 * ImpExpType.BACK_FACTORY_REWORK).toString())) returnValue = "退厂返工";
		 * else if (value.equals(String .valueOf(ImpExpType.REWORK_EXPORT)))
		 * returnValue = "返工复出"; return returnValue; } });
		 */
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(100);
			jSplitPane.setDividerSize(0);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
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
			jLabel = new JLabel();
			jLabel.setText("转厂安排周报表");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(8, 8, 298, 34);

			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 24));
			jLabel.setForeground(java.awt.Color.blue);

			jPanel.add(jLabel, null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
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
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(500, 66, 94, 23);
			jButton.setText("查询");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new find().start();
				}
			});
		}
		return jButton;
	}

	class find extends Thread {

		@Override
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				lsResult = fptManageAction
						.findTransferPlan(new Request(CommonVars.getCurrUser()));
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgFptPlan.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				initTable(lsResult);
			}
		}
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(603, 66, 86, 23);
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
