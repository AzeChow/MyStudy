/*
 * Created on 2004-9-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.message;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.bcus.message.entity.MessageQuery;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgCustomsMessageQueryTCS extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel = null; // @jve:decl-index=0:visual-constraint="14,64"
	private JPanel jPanel1 = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel2 = null;
	private JToolBar jToolBar = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JPanel jPanel3 = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel4 = null;
	private JPanel jPanel5 = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable1 = null;
	private JScrollPane jScrollPane1 = null;
	private JTableListModel tableModelSend = null;

	private JTableListModel tableModelRecv = null;
	private JButton jButton3 = null;
	private MessageAction messageAction = null;
	private JPanel jPanel6 = null;
	private JLabel jLabel3 = null;
	private JCalendarComboBox jCalendarComboBox = null;
	private JLabel jLabel4 = null;
	private JCalendarComboBox jCalendarComboBox1 = null;
	private JButton jButton4 = null;
	
	public Integer projectType = null;

	/**
	 * This is the default constructor
	 */
	public DgCustomsMessageQueryTCS(Integer projectType) {
		super();
		messageAction = (MessageAction) CommonVars.getApplicationContext()
				.getBean("messageAction");
		this.projectType = projectType;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("报关单报文信息查询");
		this.setSize(777, 516);
		this.setContentPane(getJContentPane());
		this.jCalendarComboBox.setDate(CommonVars.getNowDate());
		this.jCalendarComboBox1.setDate(new Date());
		initable();
	}

	private void initable() {
		List listSend = null;
		List listRecv = null;
		listSend = messageAction.findSendMessages(new Request(CommonVars
				.getCurrUser()), projectType, CommonUtils
				.getBeginDate(jCalendarComboBox.getDate()), CommonUtils
				.getEndDate(jCalendarComboBox1.getDate()));
		if (listSend != null && !listSend.isEmpty())
			initTableSend(listSend);
		else
			initTableSend(new Vector());

		listRecv = messageAction.findReceiptMessages(new Request(CommonVars
				.getCurrUser()), projectType, CommonUtils
				.getBeginDate(jCalendarComboBox.getDate()), CommonUtils
				.getEndDate(jCalendarComboBox1.getDate()));
		if (listRecv != null && !listRecv.isEmpty())
			initTableRecv(listRecv);
		else
			initTableRecv(new Vector());
	}

	private void initTableSend(final List list) {
		tableModelSend = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						// list.add(addColumn("发送电脑名", "computerName", 60));
						list.add(addColumn("发送时间", "sendDate", "",
								"yyyy-MM-dd HH:mm:SSS", 150));
						list.add(addColumn("预录入号", "ylrh", 180));
						list.add(addColumn("文件名", "fileName", 200));
						list.add(addColumn("手册号", "emsNo", 100));
						list.add(addColumn("发送者", "sendUser", 80));
						return list;
					}
				});
	}

	private void initTableRecv(final List list) {
		tableModelRecv = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("接收时间", "returnDate", "",
								"yyyy-MM-dd HH:mm:SSS", 150));
						list.add(addColumn("通知时间", "noticeDate", 150));
						list.add(addColumn("预录入号", "ylrh", 60));
						list.add(addColumn("报关单号", "seqNo", 160));
						list.add(addColumn("统一编号", "uniteCode", 100));
						list.add(addColumn("回执内容", "success", 120));
						list.add(addColumn("申报口岸", "eportLocationCode", 60));
						list.add(addColumn("申报单位", "corporationName", 60));
						list.add(addColumn("报关员代码", "entrydeclarantNo", 60));
						list.add(addColumn("经营单位代码", "corporationCustomsCode", 60));
						list.add(addColumn("货场代码", "cyNo", 60));
						list.add(addColumn("保税仓库代码", "warehouseNo", 60));
						list.add(addColumn("进出口日期", "importExportDate", 60));
						list.add(addColumn("件数", "packages", 60));
						list.add(addColumn("提单号", "billOfLadingNo", 60));
						list.add(addColumn("运输方式", "meansOfTransportMode", 60));
						list.add(addColumn("航班号", "meansOfTransportId", 60));
						list.add(addColumn("净重", "netWeight", 60));
						list.add(addColumn("毛重", "grossWeight", 60));
						list.add(addColumn("申报日期", "declareDate", 60));
						list.add(addColumn("文件名", "fileName", 200));
						list.add(addColumn("接收者", "returnUser", 100));
						return list;
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
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jLabel.setText("EDI收发信息");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setForeground(new java.awt.Color(255, 153, 51));
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			jPanel.setBackground(new java.awt.Color(255, 255, 254));
			jLabel1.setText("");
			jLabel1.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel2.setText("");
			jLabel2
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jPanel.add(jLabel, java.awt.BorderLayout.CENTER);
			jPanel.add(jLabel1, java.awt.BorderLayout.EAST);
			jPanel.add(jLabel2, java.awt.BorderLayout.WEST);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jTabbedPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("报关单", null, getJPanel2(), null);
		}
		return jTabbedPane;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJToolBar(), java.awt.BorderLayout.SOUTH);
			jPanel2.add(getJPanel3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton3());
			jToolBar.add(getJPanel6());
		}
		return jToolBar;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setVisible(false);
			jButton.setText("详细情况");
		}
		return jButton;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("刷新");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					initable();

				}
			});

		}
		return jButton1;
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("关闭");
			jButton2.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgCustomsMessageQueryTCS.this.dispose();
				}
			});

		}
		return jButton2;
	}

	/**
	 * 
	 * This method initializes jPanel3
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * 
	 * This method initializes jSplitPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(380);
			jSplitPane.setDividerSize(10);
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setLeftComponent(getJPanel4());
			jSplitPane.setRightComponent(getJPanel5());
		}
		return jSplitPane;
	}

	/**
	 * 
	 * This method initializes jPanel4
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * 
	 * This method initializes jPanel5
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel5;
	}

	/**
	 * 
	 * This method initializes jTable
	 * 
	 * 
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
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
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
	 * 
	 * This method initializes jTable1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
	}

	/**
	 * 
	 * This method initializes jScrollPane1
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("回执详细内容");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelRecv == null
							|| tableModelRecv.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgCustomsMessageQueryTCS.this, "请选择接受的EDI信息！",
								"提示", 2);
						return;
					}
					MessageQuery obj = (MessageQuery) tableModelRecv
							.getCurrentRow();
					String file = obj.getFileName();
					new openRecv(file).start();
				}
			});
		}
		return jButton3;
	}

	class openRecv extends Thread {
		List message = new ArrayList();
		String file = "";

		public openRecv(String file) {
			this.file = file;
		}

		public void run() {
			try {
				CommonProgress
						.showProgressDialog(DgCustomsMessageQueryTCS.this);
				CommonProgress.setMessage("系统正准备打开回执，请稍后...");
				message = messageAction.getMessageInfo(new Request(CommonVars
						.getCurrUser()), file);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgCustomsMessageQueryTCS.this,
						"打开回执失败：！" + e.getMessage(), "提示", 2);
			} finally {
				DgLookCustomsMessage dg = new DgLookCustomsMessage();
				dg.setList(message);
				dg.setVisible(true);
			}
		}
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jPanel6 = new JPanel();
			jPanel6.setLayout(null);
			jLabel3.setBounds(5, 6, 63, 18);
			jLabel3.setText("收发日期：");
			jLabel4.setBounds(160, 5, 18, 20);
			jLabel4.setText("止");
			jPanel6.add(jLabel3, null);
			jPanel6.add(getJCalendarComboBox(), null);
			jPanel6.add(jLabel4, null);
			jPanel6.add(getJCalendarComboBox1(), null);
			jPanel6.add(getJButton4(), null);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(69, 5, 89, 20);
		}
		return jCalendarComboBox;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.setBounds(181, 5, 88, 20);
		}
		return jCalendarComboBox1;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(276, 3, 60, 24);
			jButton4.setText("查询");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initable();
				}
			});
		}
		return jButton4;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
