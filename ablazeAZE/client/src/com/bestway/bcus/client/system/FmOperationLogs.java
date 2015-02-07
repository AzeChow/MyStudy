package com.bestway.bcus.client.system;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.authority.action.AuthorityAction;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class FmOperationLogs extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JButton btnExit = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JComboBox cbbUser = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton btnQuery = null;

	private JButton btnDelete = null;

	private JTableListModel tableModel = null;

	private SystemAction systemAction = null;

	private AuthorityAction authorityAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmOperationLogs() {
		super();
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		authorityAction = (AuthorityAction) CommonVars.getApplicationContext()
				.getBean("authorityAction");
		systemAction.checkLogsAuthority(new Request(CommonVars.getCurrUser()));
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {

		setSize(new java.awt.Dimension(784, 555));

		setTitle("系统日志管理");

		setHelpId("operationLogs");

		setContentPane(getJContentPane());

		addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {

			public void internalFrameOpened(
					javax.swing.event.InternalFrameEvent e) {

				initUIComponents();

				initTable(new ArrayList());
			}
		});

	}

	/**
	 * 初始化窗口上 控件的值
	 * 
	 */
	private void initUIComponents() {
		List dataSource = authorityAction.findUsers(new Request(CommonVars
				.getCurrUser(), true));

		// 用户
		cbbUser.setModel(new DefaultComboBoxModel(dataSource.toArray()));

		cbbUser.setRenderer(CustomBaseRender.getInstance().getRender(
				"loginName", "userName", 100, 100));

		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbUser,
				"loginName", "userName");

		this.cbbUser.setSelectedIndex(-1);

		this.cbbBeginDate.setDate(null);
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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jToolBar.add(getJPanel());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
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
	 * This method initializes 关闭按钮
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmOperationLogs.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(300, 3, 56, 24));
			jLabel2.setText("结束日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(154, 3, 58, 24));
			jLabel1.setText("开始日期");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(3, 4, 26, 23));
			jLabel.setText("用户");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbUser(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnDelete(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUser() {
		if (cbbUser == null) {
			cbbUser = new JComboBox();
			cbbUser.setBounds(new java.awt.Rectangle(32, 3, 119, 24));
		}
		return cbbUser;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new java.awt.Rectangle(214, 3, 81, 24));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new java.awt.Rectangle(359, 3, 82, 24));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {

		if (btnQuery == null) {

			btnQuery = new JButton();

			btnQuery.setBounds(new java.awt.Rectangle(444, 3, 60, 24));

			btnQuery.setText("查询");

			btnQuery.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					// 起始时间
					final Date beginDate = cbbBeginDate.getDate();

					// 结束时间
					final Date endDate = cbbEndDate.getDate();

					// 转Long 值的时间 起始
					long begin = 0L;

					// 结束的 Long
					long end = endDate.getTime();

					if (beginDate != null) {

						begin = beginDate.getTime();

					}

					// 判断 起始时间 不能大于 结束时间
					if (begin > end) {

						JOptionPane.showMessageDialog(FmOperationLogs.this,
								"请输入正确的起始时间与结束时间", "警告提示",
								JOptionPane.WARNING_MESSAGE);

						return;

					}

					/*
					 * 异步处理 使界面不出现停顿
					 */
					SwingWorker<List, Void> worker = new SwingWorker<List, Void>() {

						private List list;

						@Override
						protected List doInBackground() throws Exception {

							btnQuery.setEnabled(false);

							list = systemAction.findOperationLogs(new Request(
									CommonVars.getCurrUser()),
									(AclUser) cbbUser.getSelectedItem(),
									beginDate, endDate);

							return list;
						}

						@Override
						protected void done() {

							btnQuery.setEnabled(true);

							initTable(list);

						}
					};

					worker.execute();

				}
			});
		}
		return btnQuery;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setBounds(new java.awt.Rectangle(510, 3, 60, 24));
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!(JOptionPane.showConfirmDialog(FmOperationLogs.this,
							"你确定要删除日志吗？", "确定", 0) == 0)) {
						return;
					}
					systemAction.deleteOperationLogs(
							new Request(CommonVars.getCurrUser()),
							(AclUser) cbbUser.getSelectedItem(),
							cbbBeginDate.getDate(), cbbEndDate.getDate());
					List list = systemAction.findOperationLogs(new Request(
							CommonVars.getCurrUser()), (AclUser) cbbUser
							.getSelectedItem(), cbbBeginDate.getDate(),
							cbbEndDate.getDate());
					initTable(list);

				}
			});
		}
		return btnDelete;
	}

	private void initTable(List list) {

		tableModel = new JTableListModel(this.jTable, list,
				new JTableListModelAdapter() {

					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("操作者编号", "user.loginName", 100));
						list.add(addColumn("操作者名称", "user.userName", 100));
						list.add(addColumn("操作模组", "moduleCaption", 200));
						list.add(addColumn("操作动作", "permission", 200));
						list.add(addColumn("操作者IP", "operateIP", 100));
						list.add(addColumn("操作时间", "operateDate", 200));
						return list;
					}
				});
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

} // @jve:decl-index=0:visual-constraint="-5,10"
