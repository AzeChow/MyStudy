package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.mail.Message;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptEmail;
import com.bestway.common.fpt.entity.FptEmailParamver;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmFptEmail extends JInternalFrameBase {

	private JPanel jPanel = null;

	private JToolBar jJToolBarBar = null;

	private JButton btnAddEmail = null;

	private JButton btnSet = null;

	private JButton btnIREmail = null;

	private JTableListModel tableModel = null;

	private JButton btnClose = null;

	private int dataState = -1;

	private FptManageAction fptManageAction = null; // @jve:decl-index=0:

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel = null;

	private JScrollPane jScrollPane2 = null;

	private JList jList = null;

	private JPanel jPanel11 = null;

	private JSplitPane jSplitPane1 = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbToEmail = null;

	private JTableListModel toEmailModel = null;

	private String irEmailType = "0"; // @jve:decl-index=0:

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JButton btdelete = null;

	private JButton btRecve = null;

	private JButton btSreach = null;

	public String getIrEmailType() {
		return irEmailType;
	}

	public void setIrEmailType(String irEmailType) {
		this.irEmailType = irEmailType;
	}

	/**
	 * This method initializes
	 * 
	 */
	public FmFptEmail() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setTitle("邮件收发");
		this.setSize(new Dimension(590, 603));
		this.setSize(new Dimension(657, 499));
		this.setContentPane(getJPanel());
		List aress = new Vector();
		List email = new Vector();
		fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
		email = fptManageAction.FindFptEmail(new Request(CommonVars
				.getCurrUser()), "0");
		fillList();
		ininTable(email);
		aress = fptManageAction.FindFptEmailToAress(new Request(
				CommonVars.getCurrUser()));
		ininTable1(aress);
		setState();
	}

	private void fillList() {
		Vector vector = new Vector();
		vector.add("收邮件");
		vector.add("已发邮件");
		vector.add("已删除");
		this.jList.setListData(vector);
		if (this.jList.getModel().getSize() > 0) {
			this.setIrEmailType(irEmailType);
			this.jList.setSelectedIndex(0);
		}
	}

	/**
	 * 
	 * 初始化表格对象
	 */
	private void ininTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("是否处理", "isCancel", 60));
						if (jList.getSelectedIndex() == 1) {
							list.add(addColumn("收件人", "myEmailAdress", 200));
						} else {
							list.add(addColumn("发件人", "toEmailAdress", 200));
						}
						list.add(addColumn("主题", "mailSubject", 150));
						list.add(addColumn("发送类型", "sysType", 75));
						list.add(addColumn("发送时间", "createDate", 100));
						list.add(addColumn("帐户", "smtpServer", 120));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new checkBoxRenderer());
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : FptBusinessType
								.getFptBusinessTypeDesc(value.toString()));
						return this;
					}
				});
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	class checkBoxRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				checkBox.setSelected(false);
			} else if (value.equals("")) {
				checkBox.setSelected(false);
			} else if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
			}
			checkBox.setHorizontalAlignment(SwingConstants.CENTER);
			checkBox.setBackground(table.getBackground());
			if (isSelected) {
				checkBox.setForeground(table.getSelectionForeground());
				checkBox.setBackground(table.getSelectionBackground());
			} else {
				checkBox.setForeground(table.getForeground());
				checkBox.setBackground(table.getBackground());
			}
			return checkBox;
		}
	}

	/**
	 * 
	 * 初始化表格对象
	 */
	private void ininTable1(List list) {
		toEmailModel = new JTableListModel(tbToEmail, list,
				new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("发件人地址", "toEmailAdress", 100));
						return list;
					}
				});
		tbToEmail
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

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
			jPanel.add(getJJToolBarBar(), BorderLayout.NORTH);
			jPanel.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getBtnSet());
			jJToolBarBar.add(getBtnAddEmail());
			jJToolBarBar.add(getBtnIREmail());
			jJToolBarBar.add(getBtRecve());
			jJToolBarBar.add(getBtSreach());
			jJToolBarBar.add(getBtdelete());
			jJToolBarBar.add(getBtnClose());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes btnAddEmail
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddEmail() {
		if (btnAddEmail == null) {
			btnAddEmail = new JButton();
			btnAddEmail.setText("创建邮件");
			btnAddEmail.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addData();
					List listSource = fptManageAction.FindFptEmail(
							new Request(CommonVars.getCurrUser()), "1");
					ininTable(listSource);
				}

			});
		}
		return btnAddEmail;
	}

	private void addData() {
		DgFptCreateEmail dg = new DgFptCreateEmail();
		dg.setDataState(DataState.EDIT);
		dg.setVisible(true);
	}

	private void addData1() {
		FptEmail fp = (FptEmail) this.tableModel.getCurrentRow();
		DgFptCreateEmail dg = new DgFptCreateEmail();
		dg.setDataState(DataState.BROWSE);
		dg.setEmail(fp);
		dg.setVisible(true);
	}

	/**
	 * This method initializes btnSet
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSet() {
		if (btnSet == null) {
			btnSet = new JButton();
			btnSet.setText("设置");
			btnSet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SetDate();
				}
			});
		}
		return btnSet;
	}

	/**
	 * 设置参数
	 */
	public void SetDate() {
		DgFptEmail dg = new DgFptEmail();
		dg.setDataState(DataState.EDIT);
		dg.setVisible(true);

	}

	/**
	 * This method initializes btnIREmail
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnIREmail() {
		if (btnIREmail == null) {
			btnIREmail = new JButton();
			btnIREmail.setText("接收邮件");
			btnIREmail.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// 接收邮件
					new sendOrReceivMail().start();

				}
			});
		}
		return btnIREmail;
	}

	private void findFptEmail() {
		List listSource = fptManageAction.FindFptEmail(new Request(
				CommonVars.getCurrUser()), "0");
		ininTable(listSource);
		List aress = fptManageAction
				.FindFptEmailToAress(new Request(CommonVars.getCurrUser()));
		ininTable1(aress);
		setState();
	}

	/**
	 * 接收邮件
	 */
	class sendOrReceivMail extends Thread {
		@Override
		public void run() {
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				try {
					CommonStepProgress.setStepMessage("系统正在获取参数，请稍后...");
					FptEmailParamver paramver = fptManageAction
							.findFptEmailParamver(new Request(CommonVars
									.getCurrUser()));
					String str = "";
					if ("0".equals(paramver.getType())) {
						str = "pop3";
					} else if ("1".equals(paramver.getType())) {
						str = "IMAP";
					} else {
						str = "HTTP";
					}
					String pop3 = str;
					String pop = paramver.getPopServer();
					String uerName = paramver.getUserName();
					String passWord = paramver.getPassword();
					String popport = paramver.getPoppport();
					boolean isAuthenticator=paramver.getIsAuthenticator();
					CommonStepProgress.setStepMessage("系统正在接收邮件，请稍后...");
					Message[] msg = RecivedMail.getInstance().receiveMail(pop3,
							pop, uerName, passWord, popport,isAuthenticator);
					if (msg == null || msg.length == 0) {
						CommonStepProgress.closeStepProgressDialog();
						JOptionPane.showMessageDialog(null, "收件箱的邮件数:0", "提示",
								0);
						return;
					}
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(null, "接收邮件成功！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(null, "接收邮件失败 "
							+ ex.getMessage(), "确认", 1);
				}
				findFptEmail();
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->"
						+ ex.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();

			}
		}

	}

	private void setState() {
		btdelete.setEnabled(jList.getSelectedIndex() == 0
				|| jList.getSelectedIndex() == 1
				|| jList.getSelectedIndex() == 2);
		this.btRecve.setEnabled(jList.getSelectedIndex() == 0);
		this.btnAddEmail.setEnabled(jList.getSelectedIndex() == 1);
		this.btnIREmail.setEnabled(jList.getSelectedIndex() == 0);
		btSreach.setEnabled(jList.getSelectedIndex() == 0);
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
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmFptEmail.this.dispose();
				}
			});
		}
		return btnClose;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		// tableModel = invalid: "IWAV0177E Expression "tableModel" is too
		// complicated. - tableModel";
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
			jSplitPane.setDividerSize(5);
			jSplitPane.setOneTouchExpandable(false);
			jSplitPane.setLeftComponent(getJPanel1());
			jSplitPane.setRightComponent(getJPanel11());
		}
		return jSplitPane;
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
			jPanel1.add(getJPanel2(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJSplitPane1(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel = new JLabel();
			jLabel.setText("本地文件夹");
			jPanel2 = new JPanel();
			jPanel2.add(jLabel, null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJList());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
			jList
					.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
						public void valueChanged(
								javax.swing.event.ListSelectionEvent e) {

							if (e.getValueIsAdjusting() == true) {
								return;
							}
							String selectedBillType = String
									.valueOf((((JList) e.getSource())
											.getSelectedIndex()));
							List listSource = null;
							if (jList.getSelectedIndex() == 2) {
								listSource = fptManageAction
										.FindFptEmail(new Request(CommonVars
												.getCurrUser()),
												selectedBillType);
							} else if (jList.getSelectedIndex() == 0) {
								listSource = fptManageAction
										.FindFptEmail(new Request(CommonVars
												.getCurrUser()),
												selectedBillType);
							} else {
								listSource = fptManageAction
										.FindFptEmail(new Request(CommonVars
												.getCurrUser()),
												selectedBillType);
							}
							ininTable(listSource);
							setState();
						}

					});
		}
		return jList;
	}

	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jPanel11 = new JPanel();
			jPanel11.setLayout(new BorderLayout());
			jPanel11.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel11;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setDividerLocation(200);
			jSplitPane1.setToolTipText("");
			jSplitPane1.setTopComponent(getJScrollPane2());
			jSplitPane1.setBottomComponent(getJScrollPane1());
			jSplitPane1.setDividerSize(1);
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbToEmail());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbToEmail
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbToEmail() {
		if (tbToEmail == null) {
			tbToEmail = new JTable();
		}
		return tbToEmail;
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
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						addData1();
					}
				}
			});
		}
		jTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						JTableListModel tableModel = (JTableListModel) jTable
								.getModel();
						if (tableModel == null) {
							return;
						}
						try {
							if (tableModel.getCurrentRow() != null) {
								setState();
							}
						} catch (Exception cx) {

						}
					}
				});
		return jTable;
	}

	/**
	 * This method initializes btdelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtdelete() {
		if (btdelete == null) {
			btdelete = new JButton();
			btdelete.setText("删除");
			btdelete.setEnabled(false);
			btdelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FptEmail fe = (FptEmail) tableModel.getCurrentRow();
					if (fe == null) {
						return;
					}
					if (jList.getSelectedIndex() == 2) {
						if (tableModel.getCurrentRow() != null) {
							if (JOptionPane.showConfirmDialog(null,
									"是否确定删除数据!", "提示", 0) != 0) {
								return;
							}
						}
						List lsit = new ArrayList();
						List<FptEmail> list = tableModel.getCurrentRows();
						for (FptEmail data : list) {
							lsit.add(data);
							fptManageAction
									.deleteFptEmail(new Request(CommonVars
											.getCurrUser()), data);

						}
						tableModel.deleteRows(lsit);

					} else {
						fptManageAction.deleteFptEmailState(
								new Request(CommonVars.getCurrUser()), fe);
						tableModel.deleteRow(tableModel.getCurrentRow());
					}

				}
			});
		}
		return btdelete;
	}

	/**
	 * This method initializes btRecve
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtRecve() {
		if (btRecve == null) {
			btRecve = new JButton();
			btRecve.setText("处理邮件");
			btRecve.setEnabled(false);
			btRecve.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					excEmail();
					findFptEmail();
				}
			});
		}
		return btRecve;
	}

	/**
	 * 处理邮件
	 */
	protected void excEmail() {
		FptEmail email = (FptEmail) this.tableModel.getCurrentRow();
		if (email == null) {
			JOptionPane.showMessageDialog(null, "请选择要处理的邮件!", "提示", 0);
			return;
		} else {
			if (FptBusinessType.FPT_OTHER.equals(email.getSysType())) {
				JOptionPane.showMessageDialog(null, "不能处理发送类型为[其它]的邮件，只能浏览!",
						"提示", 0);
				return;
			}
			DgFptExcEmail dg = new DgFptExcEmail();
			dg.setEmail(email);
			dg.setDataState(DataState.EDIT);
			dg.setVisible(true);
		}

	}

	/**
	 * This method initializes btSreach
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtSreach() {
		if (btSreach == null) {
			btSreach = new JButton();
			btSreach.setText("浏览");
			btSreach.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addData1();
				}
			});
		}
		return btSreach;
	}

} // @jve:decl-index=0:visual-constraint="36,0"
