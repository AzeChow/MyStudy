package com.bestway.common.client.message;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bswmail.qp.entity.MailReceiveStatusInfo;
import com.bestway.bswmail.qp.entity.MailSendStatusInfo;
import com.bestway.bswmail.qp.entity.MailServiceStatus;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.BcsBusinessType;
import com.bestway.common.constant.CspProcessResult;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.message.action.CspMessageAction;
import com.bestway.common.message.entity.CspMessageSend;
import com.bestway.common.message.entity.CspParameterSet;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public abstract class FmCspMessageQuery extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JButton jButton = null;

	protected CspMessageAction cspMessageAction = null;

	private JSplitPane jSplitPane = null;

	private JTabbedPane jTabbedPane = null;

	private JSplitPane jSplitPane1 = null;

	private JSplitPane jSplitPane2 = null;

	private JPanel jPanel = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbSend = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane2 = null;

	private JTable tbRecv = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JButton btnSendQuery = null;

	private JCalendarComboBox cbbSendBeginDate = null;

	private JCalendarComboBox cbbSendEndDate = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JCalendarComboBox cbbRecvBeginDate = null;

	private JCalendarComboBox cbbRecvEndDate = null;

	private JButton btnRecvQuery = null;

	private JTableListModel tableModelSend;

	private JTableListModel tableModelRecv;

	private JTableListModel tableModelRecvDetail;

	private JTableListModel tableModelNotProcess;

	private JPanel jPanel2 = null;

	private JTextPane jTextPane = null;

	private JScrollPane jScrollPane = null;

	private JList jList = null;

	private JSplitPane jSplitPane3 = null;

	private JScrollPane jScrollPane3 = null;

	private JTable tbRecvDetail = null;

	private JPanel jPanel3 = null;

	private JScrollPane jScrollPane4 = null;

	private JTable tbNotProcess = null;

	private JButton jButton1 = null;

	private String sendFileName = null;

	private JButton btnSendQuery1 = null;

	private JToolBar jJToolBarBar = null;

	private JButton btnRefreshNotProcessData = null;

	private JLabel jLabel4 = null;

	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // @jve:decl-index=0:

	private JButton btnDeleteNotProcessData = null;

	private JSplitPane jSplitPane5 = null;

	private JScrollPane jScrollPane12 = null;

	private JTable tbNotProcessDetail = null;

	public FmCspMessageQuery() {
		super();
		initialize();
		cspMessageAction = getCspMessageAction();		
		setState();
	}

	protected abstract CspMessageAction getCspMessageAction();

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(761, 531));
		this.setTitle("报文信息查询");
		this.setContentPane(getJContentPane());
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameClosed(
							javax.swing.event.InternalFrameEvent e) {
					}

					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						initUIComponents();
						showData();
					}
				});
	}

	@Override
	public void dispose() {
		super.dispose();
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("生成模拟回执");
			jButton.setBounds(new Rectangle(397, 12, 112, 23));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ItemProperty item = (ItemProperty) jList.getSelectedValue();
					if (item == null) {
						JOptionPane.showMessageDialog(FmCspMessageQuery.this,
								"请选择系统类型", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					String sysType = item.getCode();
					if (tableModelSend.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCspMessageQuery.this,
								"请选择要生成模拟的已发送报文", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					CspMessageSend messageSend = (CspMessageSend) tableModelSend
							.getCurrentRow();
					makeDefaultReturnInfoMessage(sysType, messageSend);
				}
			});
		}
		return jButton;
	}

	/**
	 * 生成模拟回执
	 * 
	 * @param sysType
	 * @param messageSend
	 */
	protected abstract void makeDefaultReturnInfoMessage(String sysType,
			CspMessageSend messageSend);

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(4);
			jSplitPane.setDividerLocation(150);
			jSplitPane.setLeftComponent(getJPanel2());
			jSplitPane.setRightComponent(getJTabbedPane());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("未处理回执查询", null, getJPanel3(), null);
			jTabbedPane.addTab("发送报文查询", null, getJSplitPane1(), null);
			jTabbedPane.addTab("已处理的回执查询", null, getJSplitPane2(), null);
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
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setDividerLocation(80);
			jSplitPane1.setTopComponent(getJPanel());
			jSplitPane1.setBottomComponent(getJScrollPane1());
			jSplitPane1.setDividerSize(2);
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jSplitPane2
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane2() {
		if (jSplitPane2 == null) {
			jSplitPane2 = new JSplitPane();
			jSplitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane2.setDividerLocation(50);
			jSplitPane2.setTopComponent(getJPanel1());
			jSplitPane2.setBottomComponent(getJSplitPane3());
			jSplitPane2.setDividerSize(2);
		}
		return jSplitPane2;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(21, 42, 53, 23));
			jLabel1.setText("结束日期");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(21, 12, 55, 23));
			jLabel.setText("开始日期");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getBtnSendQuery(), null);
			jPanel.add(getCbbSendBeginDate(), null);
			jPanel.add(getCbbSendEndDate(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getBtnSendQuery1(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbSend());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbSend() {
		if (tbSend == null) {
			tbSend = new JTable();
		}
		return tbSend;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(203, 12, 53, 23));
			jLabel3.setText("结束日期");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(21, 12, 55, 23));
			jLabel2.setText("开始日期");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(getCbbRecvBeginDate(), null);
			jPanel1.add(getCbbRecvEndDate(), null);
			jPanel1.add(getBtnRecvQuery(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbRecv());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbRecv() {
		if (tbRecv == null) {
			tbRecv = new JTable();
			tbRecv.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							showRecvDetailData();
						}
					});
		}
		return tbRecv;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSendQuery() {
		if (btnSendQuery == null) {
			btnSendQuery = new JButton();
			btnSendQuery.setBounds(new Rectangle(274, 12, 115, 23));
			btnSendQuery.setText("查询");
			btnSendQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jList.getSelectedValue() == null) {
						JOptionPane.showMessageDialog(FmCspMessageQuery.this,
								"请选择系统类型", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					showSendData();
				}
			});
		}
		return btnSendQuery;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbSendBeginDate() {
		if (cbbSendBeginDate == null) {
			cbbSendBeginDate = new JCalendarComboBox();
			cbbSendBeginDate.setBounds(new Rectangle(75, 12, 107, 23));
		}
		return cbbSendBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbSendEndDate() {
		if (cbbSendEndDate == null) {
			cbbSendEndDate = new JCalendarComboBox();
			cbbSendEndDate.setBounds(new Rectangle(75, 42, 107, 23));
		}
		return cbbSendEndDate;
	}

	/**
	 * This method initializes jCalendarComboBox2
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbRecvBeginDate() {
		if (cbbRecvBeginDate == null) {
			cbbRecvBeginDate = new JCalendarComboBox();
			cbbRecvBeginDate.setBounds(new Rectangle(75, 12, 107, 23));
		}
		return cbbRecvBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox21
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbRecvEndDate() {
		if (cbbRecvEndDate == null) {
			cbbRecvEndDate = new JCalendarComboBox();
			cbbRecvEndDate.setBounds(new Rectangle(257, 12, 103, 23));
		}
		return cbbRecvEndDate;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRecvQuery() {
		if (btnRecvQuery == null) {
			btnRecvQuery = new JButton();
			btnRecvQuery.setBounds(new Rectangle(390, 12, 61, 23));
			btnRecvQuery.setText("查询");
			btnRecvQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jList.getSelectedValue() == null) {
						JOptionPane.showMessageDialog(FmCspMessageQuery.this,
								"请选择系统类型", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					showRecvData();
				}
			});
		}
		return btnRecvQuery;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJTextPane(), BorderLayout.NORTH);
			jPanel2.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jTextPane
	 * 
	 * @return javax.swing.JTextPane
	 */
	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			jTextPane.setText("系统类型");
			jTextPane.setBackground(new Color(238, 238, 238));
			jTextPane.setEditable(false);
		}
		return jTextPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
			jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jList
					.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
						public void valueChanged(
								javax.swing.event.ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							showData();
						}
					});
		}
		return jList;
	}

	/**
	 * 取得业务类型 比如备案资料库，通关手册等
	 * 
	 * @return
	 */
	protected abstract Vector getBusinessType();

	/**
	 * 初始化客户端组件
	 * 
	 */
	private void initUIComponents() {
		Vector<ItemProperty> vector = getBusinessType();
		jList.setListData(vector);
		if (jList.getModel().getSize() > 0) {
			jList.setSelectedIndex(0);
		}
		this.cbbRecvBeginDate.setDate(null);
		this.cbbRecvEndDate.setDate(null);
		this.cbbSendBeginDate.setDate(null);
		this.cbbSendEndDate.setDate(null);
	}

	/**
	 * 显示接收文件的内容
	 * 
	 * @param list
	 */
	private void initRecvTable(List list) {
		tableModelRecv = new JTableListModel(tbRecv, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("企业内部编号", "copEmsNo", 100));
						list.add(addColumn("核查/报核次数", "colDcrTime", 100));
						list.add(addColumn("数据中心统一编号", "seqNo", 100));
						list.add(addColumn("海关分册编号", "emsNo", 80));
						list.add(addColumn("数据类型", "applType", 100));
						list.add(addColumn("处理结果", "chkMark", 100));
						list.add(addColumn("通知时间", "noticeDate", 100));
						list.add(addColumn("处理者", "userName", 100));
						list.add(addColumn("文件名称", "fileName", 300));
						return list;
					}
				});
	}

	/**
	 * 显示发送文件的内容
	 * 
	 * @param list
	 */
	private void initSendTable(List list) {
		tableModelSend = new JTableListModel(tbSend, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("系统类型", "sysType", 100));
						list.add(addColumn("发送时间", "sendRecvTime", 100));
						list.add(addColumn("企业内部编码", "copEmsNo", 160));
						list.add(addColumn("文件名称", "fileName", 200));
						list.add(addColumn("发送者", "sendRecvEr", 100));
						return list;
					}
				});
		tbSend.getColumnModel().getColumn(1)
		.setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(
					JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);
				super.setText((value == null) ? "" : castValue(value));
				return this;
			}
			private String castValue(Object value) {
				return BcsBusinessType.getBcsBusinessTypeDesc(value.toString());
			}
		});
	}

	/**
	 * 显示没有处理的回执信息
	 * 
	 * @param list
	 */
	private void initNotProcessTable(List list) {
		tableModelNotProcess = new JTableListModel(tbNotProcess, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("企业内部编号", "copNo", 100));
						list.add(addColumn("核查/报核次数", "colDcrTime", 100));
						list.add(addColumn("数据中心统一编号", "seqNo", 100));
						list.add(addColumn("海关分册编号", "emsNo", 80));
						list.add(addColumn("数据类型", "sysType", 80));
						list.add(addColumn("处理结果", "chkMark", 100));
						list.add(addColumn("通知时间", "formatedNoticeDate", 120));
						list.add(addColumn("文件名称", "fileName", 300));
						return list;
					}
				});
		tbNotProcess.getColumnModel().getColumn(8).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : CspProcessResult
								.getResultDesc(value.toString().trim()));
						return this;
					}

				});
	}

	/**
	 * 显示没有处理的回执的明细信息
	 * 
	 * @param list
	 */
	private void initNotProcessDetailTable(List list) {
		new JTableListModel(tbNotProcessDetail, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("信息明细", "resultInfo", 500));
						return list;
					}
				});
	}

	/**
	 * 显示已处理的回执的明细信息
	 * 
	 * @param list
	 */
	private void initRecvDetailTable(List list) {
		tableModelRecvDetail = new JTableListModel(tbRecvDetail, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("信息明细", "resultInfo", 500));
						return list;
					}
				});
	}

	/**
	 * 显示发送的报文信息
	 * 
	 */
	private void showSendData() {
		ItemProperty item = (ItemProperty) jList.getSelectedValue();
		List list = new ArrayList();
		if (item != null) {
			String sysType = item.getCode();
			Date beginDate = this.cbbSendBeginDate.getDate();
			Date endDate = this.cbbSendEndDate.getDate();
			list = cspMessageAction.findCspMessageSend(new Request(CommonVars
					.getCurrUser()), sysType, beginDate, endDate);
		}
		this.initSendTable(list);
	}

	/**
	 * 显示已处理接收的回执信息
	 * 
	 */
	private void showRecvData() {
		ItemProperty item = (ItemProperty) jList.getSelectedValue();
		List list = new ArrayList();
		if (item != null) {
			String sysType = item.getCode();
			Date beginDate = this.cbbRecvBeginDate.getDate();
			Date endDate = this.cbbRecvEndDate.getDate();
			list = cspMessageAction.findCspReceiptResult(new Request(CommonVars
					.getCurrUser()), sysType, beginDate, endDate, sendFileName);
		}
		this.initRecvTable(list);
		showRecvDetailData();
	}

	/**
	 * 显示已处理接收的回执明细信息
	 * 
	 */
	private void showRecvDetailData() {
		List list = new ArrayList();
		if (this.tableModelRecv != null) {
			CspReceiptResult result = (CspReceiptResult) this.tableModelRecv
					.getCurrentRow();
			if (result != null) {
				list = cspMessageAction.findCspReceiptResultDetail(new Request(
						CommonVars.getCurrUser()), result);
			}
		}
		this.initRecvDetailTable(list);
	}

	/**
	 * 显示没有处理的回执信息
	 * 
	 */
	private void showNotProcessData() {
		ItemProperty item = (ItemProperty) jList.getSelectedValue();
		List list = new ArrayList();
		if (item != null) {
			String sysType = item.getCode();
			list = cspMessageAction.findNotProcessReturnFile(new Request(
					CommonVars.getCurrUser()), sysType, null);
		}
		this.initNotProcessTable(list);
	}

	/**
	 * 显示没有处理的回执明细信息
	 * 
	 */
	private void showNotProcessDetailData() {
		List list = new ArrayList();
		if (tableModelNotProcess != null) {
			CspReceiptResult cspReceiptResult = (CspReceiptResult) tableModelNotProcess
					.getCurrentRow();
			if (cspReceiptResult != null
					&& cspReceiptResult.getReceiptResultDetailList() != null) {
				list = cspReceiptResult.getReceiptResultDetailList();
			}
		}
		this.initNotProcessDetailTable(list);
	}

	/**
	 * This method initializes jSplitPane3
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane3() {
		if (jSplitPane3 == null) {
			jSplitPane3 = new JSplitPane();
			jSplitPane3.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane3.setDividerSize(2);
			jSplitPane3.setDividerLocation(180);
			jSplitPane3.setBottomComponent(getJScrollPane3());
			jSplitPane3.setTopComponent(getJScrollPane2());
		}
		return jSplitPane3;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getTbRecvDetail());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbRecvDetail() {
		if (tbRecvDetail == null) {
			tbRecvDetail = new JTable();
		}
		return tbRecvDetail;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJJToolBarBar(), BorderLayout.NORTH);
			jPanel3.add(getJSplitPane5(), BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jScrollPane4
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setViewportView(getTbNotProcess());
		}
		return jScrollPane4;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbNotProcess() {
		if (tbNotProcess == null) {
			tbNotProcess = new JTable();
			tbNotProcess.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							showNotProcessDetailData();
						}
					});
		}
		return tbNotProcess;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(397, 42, 112, 23));
			jButton1.setText("读取报文内容");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelSend.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCspMessageQuery.this,
								"请选择要查看的报文", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					CspMessageSend messageSend = (CspMessageSend) tableModelSend
							.getCurrentRow();
					String messageFileName = messageSend.getFileName();
					ItemProperty item = (ItemProperty) jList.getSelectedValue();
					if (item == null) {
						JOptionPane.showMessageDialog(FmCspMessageQuery.this,
								"请选择系统类型", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					String sysType = item.getCode();
					String formatFileName = getFormatFileNameByType(sysType,
							messageFileName);
					DgCspMessageFileContent dg = new DgCspMessageFileContent();
					dg.setMessageFileName(messageFileName);
					dg.setFormatFileName(formatFileName);
					dg.setCspMessageAction(cspMessageAction);
					dg.setVisible(true);
				}
			});
		}
		return jButton1;
	}

	/**
	 * 获取报文格式内容
	 * 
	 * @param sysType
	 * @param messageFileName
	 * @return
	 */
	protected String getFormatFileNameByType(String sysType,
			String messageFileName) {
		return cspMessageAction.getFormatFileNameByType(new Request(CommonVars
				.getCurrUser()), sysType);
	}

	/**
	 * This method initializes btnSendQuery1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSendQuery1() {
		if (btnSendQuery1 == null) {
			btnSendQuery1 = new JButton();
			btnSendQuery1.setBounds(new Rectangle(274, 42, 115, 23));
			btnSendQuery1.setText("查看报文回执");
			btnSendQuery1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelSend.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmCspMessageQuery.this, "请选择要查看回执的报文",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							CspMessageSend messageSend = (CspMessageSend) tableModelSend
									.getCurrentRow();
							sendFileName = messageSend.getFileName();
							jTabbedPane.setSelectedIndex(0);
							sendFileName = null;
						}
					});
		}
		return btnSendQuery1;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("未处理的回执信息");
			jLabel4.setForeground(Color.blue);
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getBtnRefreshNotProcessData());
			jJToolBarBar.add(getBtnDeleteNotProcessData());
			jJToolBarBar.add(jLabel4);
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefreshNotProcessData() {
		if (btnRefreshNotProcessData == null) {
			btnRefreshNotProcessData = new JButton();
			btnRefreshNotProcessData.setText("刷新");
			btnRefreshNotProcessData
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							showNotProcessData();
						}
					});
		}
		return btnRefreshNotProcessData;
	}

//	/**
//	 * 判断是否整合邮箱
//	 * 
//	 * @return
//	 */
//	private boolean checkRemoteDxpMail() {
//		CspParameterSet cspParameterSet = cspMessageAction
//				.getCspParameterSet(new Request(CommonVars.getCurrUser()));
//		if (cspParameterSet == null) {
//			return false;
//		}
//		if (cspParameterSet.getReadFromCard() == null
//				|| !cspParameterSet.getReadFromCard()) {
//			return false;
//		}
//		if (cspParameterSet.getRemoteSignData() == null
//				|| !cspParameterSet.getRemoteSignData()) {
//			return false;
//		}
//		if (cspParameterSet.getRemoteDxpMail() == null
//				|| !cspParameterSet.getRemoteDxpMail()) {
//			return false;
//		}
//		return true;
//	}

	
	/**
	 * 设置窗口状态
	 * 
	 */
	private void setState() {
//		if (this.checkRemoteDxpMail()) {
//			this.jTabbedPane.setEnabledAt(0, true);
//			this.jTabbedPane.setSelectedIndex(0);
//		} else {
			this.jTabbedPane.setEnabledAt(0, false);
			this.jTabbedPane.setSelectedIndex(0);
//		}
	}

	/**
	 * 显示数据
	 * 
	 */
	private void showData() {
 if (jTabbedPane.getSelectedIndex() == 0) {
			showNotProcessData();
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			showSendData();
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			showRecvData();
		}
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteNotProcessData() {
		if (btnDeleteNotProcessData == null) {
			btnDeleteNotProcessData = new JButton();
			btnDeleteNotProcessData.setText("删除");
			btnDeleteNotProcessData
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelNotProcess.getCurrentRows().size() <= 0) {
								JOptionPane.showMessageDialog(
										FmCspMessageQuery.this, "请选择要删除的回执信息");
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmCspMessageQuery.this, "你确定要要删除这些回执信息?",
									"确认", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
								return;
							}
							List list = tableModelNotProcess.getCurrentRows();
							for (int i = 0; i < list.size(); i++) {
								CspReceiptResult cspReceiptResult = (CspReceiptResult) list
										.get(i);
								cspMessageAction.deleteNotProcessReturnFile(
										new Request(CommonVars.getCurrUser()),
										cspReceiptResult);
							}
							showNotProcessData();
						}
					});
		}
		return btnDeleteNotProcessData;
	}

	/**
	 * This method initializes jSplitPane5
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane5() {
		if (jSplitPane5 == null) {
			jSplitPane5 = new JSplitPane();
			jSplitPane5.setDividerLocation(200);
			jSplitPane5.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane5.setTopComponent(getJScrollPane4());
			jSplitPane5.setBottomComponent(getJScrollPane12());
			jSplitPane5.setDividerSize(5);
		}
		return jSplitPane5;
	}

	/**
	 * This method initializes jScrollPane12
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane12() {
		if (jScrollPane12 == null) {
			jScrollPane12 = new JScrollPane();
			jScrollPane12.setViewportView(getTbNotProcessDetail());
		}
		return jScrollPane12;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbNotProcessDetail() {
		if (tbNotProcessDetail == null) {
			tbNotProcessDetail = new JTable();
		}
		return tbNotProcessDetail;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
