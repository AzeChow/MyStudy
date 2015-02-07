package com.bestway.bls.client.message;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bls.action.BlsMessageAction;
import com.bestway.bls.entity.BlsMessageSend;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.BlsServiceType;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JTextArea;

public class FmBlsMessageQuery extends JInternalFrameBase {

	private JPanel jContentPane = null;

	protected BlsMessageAction blsMessageAction = null;

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

	private JButton jButton1 = null;

	private String keyCode = null;

	private JButton btnSendQuery1 = null;

	// private Timer timer = null;

	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // @jve:decl-index=0:

	private JTextArea jTextArea = null;

	public FmBlsMessageQuery() {
		super();
		initialize();
		blsMessageAction = (BlsMessageAction) CommonVars
				.getApplicationContext().getBean("blsMessageAction");
	}

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
						// if (timer != null) {
						// timer.stop();
						// }
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
		// if (timer != null) {
		// timer.stop();
		// }
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
	 * 生成模拟回执
	 * 
	 * @param sysType
	 * @param messageSend
	 */
	protected void makeDefaultReturnInfoMessage(String sysType,
			BlsMessageSend messageSend) {

	}

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
						JOptionPane.showMessageDialog(FmBlsMessageQuery.this,
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
						JOptionPane.showMessageDialog(FmBlsMessageQuery.this,
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
	protected Vector getBusinessType() {
		Vector<ItemProperty> vector = new Vector<ItemProperty>();
		vector.add(new ItemProperty(String
				.valueOf(BlsServiceType.MANIFEST_DECLARE), BlsServiceType
				.getServiceDescByType(BlsServiceType.MANIFEST_DECLARE)));
		vector.add(new ItemProperty(String
				.valueOf(BlsServiceType.FREIGHTAGE_DECLARE), BlsServiceType
				.getServiceDescByType(BlsServiceType.FREIGHTAGE_DECLARE)));
		vector.add(new ItemProperty(String
				.valueOf(BlsServiceType.COLLATEBIND_DECLARE), BlsServiceType
				.getServiceDescByType(BlsServiceType.COLLATEBIND_DECLARE)));
		vector.add(new ItemProperty(String
				.valueOf(BlsServiceType.BILLCANCEL_APPLY), BlsServiceType
				.getServiceDescByType(BlsServiceType.BILLCANCEL_APPLY)));
		vector.add(new ItemProperty(String
				.valueOf(BlsServiceType.CARGOREPLACE_APPLY), BlsServiceType
				.getServiceDescByType(BlsServiceType.CARGOREPLACE_APPLY)));
		vector.add(new ItemProperty(String
				.valueOf(BlsServiceType.PROCESS_APPLY), BlsServiceType
				.getServiceDescByType(BlsServiceType.PROCESS_APPLY)));
		vector.add(new ItemProperty(String.valueOf(BlsServiceType.TRN_APPLY),
				BlsServiceType.getServiceDescByType(BlsServiceType.TRN_APPLY)));
		vector.add(new ItemProperty(String
				.valueOf(BlsServiceType.CARGOABANDON_APPLY), BlsServiceType
				.getServiceDescByType(BlsServiceType.CARGOABANDON_APPLY)));
		vector.add(new ItemProperty(
				String.valueOf(BlsServiceType.IMPORT_APPLY), BlsServiceType
						.getServiceDescByType(BlsServiceType.IMPORT_APPLY)));
		return vector;
	}

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
						list.add(addColumn("数据类型", "messageType", 100));
						list.add(addColumn("信息描述", "description", 300));
//						list.add(addColumn("单证号码", "keyCode", 60));
//						list.add(addColumn("信息代码", "messageCode", 60));
						list.add(addColumn("服务状态", "serviceStatus", 100));
						list.add(addColumn("服务句柄", "serviceHandle", 80));
						list.add(addColumn("通知日期", "formatedNoticeDate","","yyyy-MM-dd HH:mm:SSS", 100));
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
						list.add(addColumn("系统类型", "messageType", 100));
						list.add(addColumn("发送时间", "sendTime","","yyyy-MM-dd HH:mm:SSS", 100));
						list.add(addColumn("企业内部编码", "keyCode", 160));
						list.add(addColumn("文件名称", "fileName", 200));
						list.add(addColumn("发送者", "sendEr", 100));
						return list;
					}
				});
	}

	// /**
	// * 显示已处理的回执的明细信息
	// *
	// * @param list
	// */
	// private void initRecvDetailTable(List list) {
	// tableModelRecvDetail = new JTableListModel(tbRecvDetail, list,
	// new JTableListModelAdapter() {
	// public List InitColumns() {
	// List<JTableListColumn> list = new Vector<JTableListColumn>();
	// list.add(addColumn("信息明细", "resultInfo", 500));
	// return list;
	// }
	// });
	// }

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
			list = blsMessageAction.findBlsMessageSend(new Request(CommonVars
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
			list = blsMessageAction.findBlsReceiptResult(new Request(CommonVars
					.getCurrUser()), sysType, beginDate, endDate, keyCode);
		}
		this.initRecvTable(list);
		// showRecvDetailData();
	}

	/**
	 * 显示已处理接收的回执明细信息
	 * 
	 */
	private void showRecvDetailData() {
		// List list = new ArrayList();
		String servicehandle = "";
		if (this.tableModelRecv != null) {
			BlsReceiptResult result = (BlsReceiptResult) this.tableModelRecv
					.getCurrentRow();
			if (result != null) {
				// list = blsMessageAction.findBlsReceiptResultDetail(new
				// Request(
				// CommonVars.getCurrUser()), result);
				servicehandle = result.getServiceHandle();
			}
		}
		jTextArea.setText(servicehandle);
		// this.initRecvDetailTable(list);
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
			jScrollPane3.setViewportView(getJTextArea());
		}
		return jScrollPane3;
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
						JOptionPane.showMessageDialog(FmBlsMessageQuery.this,
								"请选择要查看的报文", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					BlsMessageSend messageSend = (BlsMessageSend) tableModelSend
							.getCurrentRow();
					String messageFileName = messageSend.getFileName();
					ItemProperty item = (ItemProperty) jList.getSelectedValue();
					if (item == null) {
						JOptionPane.showMessageDialog(FmBlsMessageQuery.this,
								"请选择系统类型", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					String sysType = item.getCode();
					String formatFileName = getFormatFileNameByType(sysType,
							messageFileName);
					DgBlsMessageStructFileContent dg = new DgBlsMessageStructFileContent();
					dg.setMessageFileName(messageFileName);
					dg.setFormatFileName(formatFileName);
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
		return blsMessageAction.getFormatFileNameByType(new Request(CommonVars
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
										FmBlsMessageQuery.this, "请选择要查看回执的报文",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							BlsMessageSend messageSend = (BlsMessageSend) tableModelSend
									.getCurrentRow();
							keyCode = messageSend.getKeyCode();
							jTabbedPane.setSelectedIndex(1);
							keyCode = null;
						}
					});
		}
		return btnSendQuery1;
	}

	/**
	 * 显示数据
	 * 
	 */
	private void showData() {
		if (jTabbedPane.getSelectedIndex() == 0) {
			showSendData();
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			showRecvData();
		}
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setEditable(false);
		}
		return jTextArea;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
