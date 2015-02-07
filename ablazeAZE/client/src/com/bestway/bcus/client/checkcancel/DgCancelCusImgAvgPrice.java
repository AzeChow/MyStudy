package com.bestway.bcus.client.checkcancel;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.CancelHead;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgCancelCusImgAvgPrice extends JDialogBase {
	private javax.swing.JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JScrollPane jScrollPane = null;
	private JTable tbAvgPric = null;
	private JLabel jLabel = null;
	private JCalendarComboBox cbbBeginDate = null;
	private JLabel jLabel1 = null;
	private JCalendarComboBox cbbEndDate = null;
	private JButton btnSerach = null;
	private JButton btnExit = null;
	private CheckCancelAction checkCancelAction = null;
	private JTableListModel tableModelAvgPrice = null;
	private CancelHead cancelHead = null; // @jve:decl-index=0:
	private Boolean isOwner=null;  //  @jve:decl-index=0:

	public Boolean getIsOwner() {
		return isOwner;
	}

	public void setIsOwner(Boolean isOwner) {
		this.isOwner = isOwner;
	}

	public CancelHead getCancelHead() {
		return cancelHead;
	}

	public void setCancelHead(CancelHead cancelHead) {
		this.cancelHead = cancelHead;
	}

	public DgCancelCusImgAvgPrice() {
		super();
		checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
		initialize();

		initTableAvgPrice(new Vector());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(697, 462);
		this.setTitle("查看每月平均单价表");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				fillWindow();
			}

		});
	}

	private void fillWindow() {
		cbbBeginDate.setDate(cancelHead.getBeginDate());
		cbbEndDate.setDate(cancelHead.getEndDate());

	}

	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
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
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(5);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setDividerLocation(60);
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
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(200, 18, 63, 18));
			jLabel1.setText("结束日期");
			jLabel = new JLabel();
			jLabel.setText("开始日期");
			jLabel.setBounds(new Rectangle(18, 17, 61, 21));
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getBtnSerach(), null);
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
			jScrollPane.setViewportView(getTbAvgPric());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbAvgPric
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbAvgPric() {
		if (tbAvgPric == null) {
			tbAvgPric = new JTable();
		}
		return tbAvgPric;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(87, 15, 109, 22));
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
			cbbEndDate.setBounds(new Rectangle(268, 18, 129, 22));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes btnSerach
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSerach() {
		if (btnSerach == null) {
			btnSerach = new JButton();
			btnSerach.setBounds(new Rectangle(464, 19, 86, 25));
			btnSerach.setText("查询");
			btnSerach.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// 查询单价表
					new FindAvgPrice().execute();
				}
			});
		}
		return btnSerach;
	}

	/**
	 * 查询单价表
	 * 
	 * @author ower
	 * 
	 */
	class FindAvgPrice extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			List ls = new ArrayList();
			try {
				CommonProgress.showProgressDialog(DgCancelCusImgAvgPrice.this);
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				ls = checkCancelAction.findCancelImgAvgPriceLeave(new Request(
						CommonVars.getCurrUser()), cancelHead,isOwner, cbbBeginDate
						.getDate(), cbbEndDate.getDate());
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgCancelCusImgAvgPrice.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			}
			return ls;
		}

		@Override
		protected void done() {
			List list = null;
			try {
				list = (List) this.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			if (list == null) {
				list = new ArrayList();
			}
			CommonProgress.closeProgressDialog();
			initTableAvgPrice(list);

		}
	}

	/**
	 * 初始化Table
	 * 
	 * @param list
	 */
	private void initTableAvgPrice(final List list) {
		tableModelAvgPrice = new JTableListModel(tbAvgPric, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册序号", "emsSeqNum", 60,
								Integer.class));
						list.add(addColumn("开始日期", "beginDate", 80));
						list.add(addColumn("结束日期", "endDate", 80));
						list.add(addColumn("平均单价", "avgPrice", 80));
						list.add(addColumn("期初数量", "beginLeaveNum", 80));
						list.add(addColumn("期初金额", "beginLeaveMoney", 80));
						list.add(addColumn("本月进口数量", "commAmount", 80));
						list.add(addColumn("本月进口金额", "dollarTotalPrice", 80));
						list.add(addColumn("本月耗用数量", "useNum", 80));
						list.add(addColumn("本月耗用金额", "useMoney", 80));
						list.add(addColumn("本月边角料数量", "leftOverImgNum", 80));
						list.add(addColumn("本月边角料金额", "leftOverImgMoney", 80));

						list.add(addColumn("总进口数量", "impNumTotal", 80));
						list.add(addColumn("总进口金额", "impMoneyTotal", 100));

						list.add(addColumn("总耗用数量", "useNumTotal", 80));
						list.add(addColumn("总耗用金额", "useMoneyTotal", 100));

						list.add(addColumn("结余数量", "leaveNum", 80));
						list.add(addColumn("结余金额", "leaveMoney", 100));
						return list;
					}
				});
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(new Rectangle(564, 19, 82, 24));
			btnExit.setText("退出");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCancelCusImgAvgPrice.this.dispose();
				}
			});
		}
		return btnExit;
	}
} // @jve:decl-index=0:visual-constraint="119,16"
