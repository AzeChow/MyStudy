package com.bestway.client.fixasset;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.AgreementState;
import com.bestway.fixasset.action.FixAssetAction;
import com.bestway.fixasset.entity.Agreement;
import com.bestway.fixasset.entity.AgreementInvoiceDetail;
import com.bestway.fixasset.entity.AgreementInvoiceHead;
import com.bestway.fixasset.entity.AgreementWarHead;
import com.bestway.fixtureonorder.action.CheckFixAuthorityAction;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Dimension;
import java.awt.Rectangle;

public class FmAgreementInvoice extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JToolBar jToolBar = null;

	private JButton btnClose = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel = null;

	private JComboBox cbbAgreement = null;

	private JButton btnPrint = null;

	private JScrollPane jScrollPane = null;

	private JTable tbHead = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JSplitPane jSplitPane1 = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbDetail = null;

	private JPanel jPanel4 = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JTextField tfInvoiceNo = null;

	private JCalendarComboBox cbbInvoiceDate = null;

	private JTextField tfLC = null;

	private JTextField tfAccBank = null;

	private JTextField tfTransporter = null;

	private JCalendarComboBox cbbTransTime = null;

	private JComboBox cbbTransMode = null;

	private JButton btnSender = null;

	private JButton btnReceiver = null;

	private JButton jButton4 = null;

	private JLabel jLabel8 = null;

	private JScrollPane jScrollPane2 = null;

	private JScrollPane jScrollPane3 = null;

	private JScrollPane jScrollPane4 = null;

	private JScrollPane jScrollPane5 = null;

	private JTextArea taSender = null;

	private JTextArea taReceiver = null;

	private JTextArea jTextArea2 = null;

	private JTextArea taMemo = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel12 = null;

	private JTextField tfCommName = null;

	private JCustomFormattedTextField tfAmount = null;

	private JCustomFormattedTextField tfUnitPrice = null;

	private JCustomFormattedTextField tfTotalPrice = null;

	private JPanel jContentPane1 = null;

	private FixAssetAction fixAssetAction;

	private JTableListModel tableModelHead;

	private JTableListModel tableModelDetail;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private CheckFixAuthorityAction checkFixAuthorityAction = null;

	private JLabel jLabel81 = null;

	private JLabel jLabel82 = null;

	private JLabel jLabel83 = null;

	public FmAgreementInvoice() {
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
		this.setSize(new Dimension(731, 518));
		this.setContentPane(getJContentPane());
		this.setTitle("商务发票");
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						initUIComponents();
						showHeadData();
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
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
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(4);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJTabbedPane());
			jSplitPane.setDividerLocation(200);
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
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJPanel1());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmAgreementInvoice.this.doDefaultCloseAction();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(6, 5, 55, 21));
			jLabel.setText("批文号：");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(jLabel, null);
			jPanel1.add(getCbbAgreement(), null);
			jPanel1.add(getBtnPrint(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAgreement() {
		if (cbbAgreement == null) {
			cbbAgreement = new JComboBox();
			cbbAgreement.setBounds(new java.awt.Rectangle(63, 3, 127, 24));
			cbbAgreement.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						showHeadData();
					}
				}
			});
		}
		return cbbAgreement;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setBounds(new java.awt.Rectangle(193, 3, 63, 24));
			btnPrint.setText("打印");
			btnPrint.setVisible(false);
		}
		return btnPrint;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbHead());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbHead() {
		if (tbHead == null) {
			tbHead = new JTable();
			tbHead.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbHead
									.getModel();
							if (tableModel == null) {
								return;
							}
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							List list = new ArrayList();
							if (!lsm.isSelectionEmpty()) {
								if (tableModel.getCurrentRow() != null) {
									AgreementInvoiceHead invoiceHead = ((AgreementInvoiceHead) tableModel
											.getCurrentRow());
									list = fixAssetAction
											.findAgreementInvoiceDetail(
													new Request(CommonVars
															.getCurrUser()),
													invoiceHead);
									showHeadData(invoiceHead);
								}
							}
							initCommInfoTable(list);

						}
					});
		}
		return tbHead;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("基本信息", null, getJPanel2(), null);
			jTabbedPane.addTab("商品明细", null, getJPanel3(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							List list = new ArrayList();
							AgreementInvoiceHead invoiceHead = ((AgreementInvoiceHead) tableModelHead
									.getCurrentRow());
							if (jTabbedPane.getSelectedIndex() == 0) {
								if (invoiceHead != null) {
									showHeadData(invoiceHead);
								}
							} else if (jTabbedPane.getSelectedIndex() == 1) {
								if (invoiceHead != null) {
									list = fixAssetAction
											.findAgreementInvoiceDetail(
													new Request(CommonVars
															.getCurrUser()),
													invoiceHead);

								}
								initCommInfoTable(list);
							}
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel83 = new JLabel();
			jLabel83.setBounds(new Rectangle(517, 6, 93, 27));
			jLabel83.setText("接收者");
			jLabel82 = new JLabel();
			jLabel82.setBounds(new Rectangle(267, 6, 95, 27));
			jLabel82.setText("发送者");
			jLabel81 = new JLabel();
			jLabel81.setBounds(new Rectangle(267, 112, 95, 26));
			jLabel81.setText("委托者");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new java.awt.Rectangle(517, 112, 93, 26));
			jLabel8.setText("备注");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(34, 175, 59, 26));
			jLabel7.setText("运输方式");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(34, 151, 59, 21));
			jLabel6.setText("运输时间");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(34, 122, 59, 25));
			jLabel5.setText("运输方");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(34, 97, 59, 24));
			jLabel4.setText("开户银行");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(34, 67, 59, 25));
			jLabel3.setText("信用证：");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(34, 39, 59, 24));
			jLabel2.setText("发票日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(34, 12, 59, 24));
			jLabel1.setText("发票号码");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(jLabel7, null);
			jPanel2.add(getTfInvoiceNo(), null);
			jPanel2.add(getCbbInvoiceDate(), null);
			jPanel2.add(getTfLC(), null);
			jPanel2.add(getTfAccBank(), null);
			jPanel2.add(getTfTransporter(), null);
			jPanel2.add(getCbbTransTime(), null);
			jPanel2.add(getCbbTransMode(), null);
//			jPanel2.add(getBtnSender(), null);
//			jPanel2.add(getBtnReceiver(), null);
//			jPanel2.add(getJButton4(), null);
			jPanel2.add(jLabel8, null);
			jPanel2.add(getJScrollPane2(), null);
			jPanel2.add(getJScrollPane3(), null);
			jPanel2.add(getJScrollPane4(), null);
			jPanel2.add(getJScrollPane5(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(jLabel81, null);
			jPanel2.add(jLabel82, null);
			jPanel2.add(jLabel83, null);
		}
		return jPanel2;
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
			jPanel3.add(getJSplitPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setDividerSize(3);
			jSplitPane1.setTopComponent(getJScrollPane1());
			jSplitPane1.setBottomComponent(getJPanel4());
			jSplitPane1.setDividerLocation(100);
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
			jScrollPane1.setViewportView(getTbDetail());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbDetail() {
		if (tbDetail == null) {
			tbDetail = new JTable();
			tbDetail.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbDetail
									.getModel();
							if (tableModel == null) {
								return;
							}
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							if (!lsm.isSelectionEmpty()) {
								if (tableModel.getCurrentRow() != null) {
									AgreementInvoiceDetail invoiceDetail = ((AgreementInvoiceDetail) tableModel
											.getCurrentRow());
									showDetailData(invoiceDetail);
								}
							}

						}
					});
		}
		return tbDetail;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jLabel12 = new JLabel();
			jLabel12.setBounds(new java.awt.Rectangle(236, 59, 39, 25));
			jLabel12.setText("金额");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new java.awt.Rectangle(237, 18, 39, 25));
			jLabel11.setText("数量");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new java.awt.Rectangle(26, 62, 44, 24));
			jLabel10.setText("单价");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new java.awt.Rectangle(26, 16, 44, 24));
			jLabel9.setText("名称");
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.add(jLabel9, null);
			jPanel4.add(jLabel10, null);
			jPanel4.add(jLabel11, null);
			jPanel4.add(jLabel12, null);
			jPanel4.add(getTfCommName(), null);
			jPanel4.add(getTfAmount(), null);
			jPanel4.add(getTfUnitPrice(), null);
			jPanel4.add(getTfTotalPrice(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInvoiceNo() {
		if (tfInvoiceNo == null) {
			tfInvoiceNo = new JTextField();
			tfInvoiceNo.setBounds(new java.awt.Rectangle(96, 12, 127, 25));
		}
		return tfInvoiceNo;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbInvoiceDate() {
		if (cbbInvoiceDate == null) {
			cbbInvoiceDate = new JCalendarComboBox();
			cbbInvoiceDate.setBounds(new java.awt.Rectangle(96, 39, 127, 25));
		}
		return cbbInvoiceDate;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLC() {
		if (tfLC == null) {
			tfLC = new JTextField();
			tfLC.setBounds(new java.awt.Rectangle(96, 67, 127, 25));
		}
		return tfLC;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAccBank() {
		if (tfAccBank == null) {
			tfAccBank = new JTextField();
			tfAccBank.setBounds(new java.awt.Rectangle(96, 96, 127, 25));
		}
		return tfAccBank;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTransporter() {
		if (tfTransporter == null) {
			tfTransporter = new JTextField();
			tfTransporter.setBounds(new java.awt.Rectangle(96, 125, 127, 24));
		}
		return tfTransporter;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbTransTime() {
		if (cbbTransTime == null) {
			cbbTransTime = new JCalendarComboBox();
			cbbTransTime.setBounds(new java.awt.Rectangle(96, 151, 127, 22));
		}
		return cbbTransTime;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTransMode() {
		if (cbbTransMode == null) {
			cbbTransMode = new JComboBox();
			cbbTransMode.setBounds(new java.awt.Rectangle(96, 176, 127, 24));
		}
		return cbbTransMode;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSender() {
		if (btnSender == null) {
			btnSender = new JButton();
			btnSender.setBounds(new java.awt.Rectangle(267, 6, 95, 27));
			btnSender.setText("发送者...");
		}
		return btnSender;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnReceiver() {
		if (btnReceiver == null) {
			btnReceiver = new JButton();
			btnReceiver.setBounds(new java.awt.Rectangle(517, 6, 93, 27));
			btnReceiver.setText("接收者...");
		}
		return btnReceiver;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(new java.awt.Rectangle(267, 112, 95, 26));
			jButton4.setText("委托者...");
		}
		return jButton4;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setBounds(new java.awt.Rectangle(267, 34, 213, 55));
			jScrollPane2.setViewportView(getTaSender());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setBounds(new java.awt.Rectangle(267, 142, 213, 59));
			jScrollPane3.setViewportView(getJTextArea2());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jScrollPane4
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setBounds(new java.awt.Rectangle(517, 34, 191, 55));
			jScrollPane4.setViewportView(getTaReceiver());
		}
		return jScrollPane4;
	}

	/**
	 * This method initializes jScrollPane5
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane5() {
		if (jScrollPane5 == null) {
			jScrollPane5 = new JScrollPane();
			jScrollPane5.setBounds(new java.awt.Rectangle(517, 142, 191, 59));
			jScrollPane5.setViewportView(getTaMemo());
		}
		return jScrollPane5;
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getTaSender() {
		if (taSender == null) {
			taSender = new JTextArea();
		}
		return taSender;
	}

	/**
	 * This method initializes jTextArea1
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getTaReceiver() {
		if (taReceiver == null) {
			taReceiver = new JTextArea();
		}
		return taReceiver;
	}

	/**
	 * This method initializes jTextArea2
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea2() {
		if (jTextArea2 == null) {
			jTextArea2 = new JTextArea();
		}
		return jTextArea2;
	}

	/**
	 * This method initializes jTextArea3
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getTaMemo() {
		if (taMemo == null) {
			taMemo = new JTextArea();
		}
		return taMemo;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCommName() {
		if (tfCommName == null) {
			tfCommName = new JTextField();
			tfCommName.setBounds(new java.awt.Rectangle(71, 16, 113, 25));
		}
		return tfCommName;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfAmount() {
		if (tfAmount == null) {
			tfAmount = new JCustomFormattedTextField();
			tfAmount.setBounds(new java.awt.Rectangle(279, 17, 98, 26));
		}
		return tfAmount;
	}

	/**
	 * This method initializes jCustomFormattedTextField1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfUnitPrice() {
		if (tfUnitPrice == null) {
			tfUnitPrice = new JCustomFormattedTextField();
			tfUnitPrice.setBounds(new java.awt.Rectangle(71, 62, 113, 25));
		}
		return tfUnitPrice;
	}

	/**
	 * This method initializes jCustomFormattedTextField2
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfTotalPrice() {
		if (tfTotalPrice == null) {
			tfTotalPrice = new JCustomFormattedTextField();
			tfTotalPrice.setBounds(new java.awt.Rectangle(278, 60, 98, 26));
		}
		return tfTotalPrice;
	}

	private void initUIComponents() {
		List list = fixAssetAction.findAgreementExcuting(new Request(CommonVars
				.getCurrUser()));
		this.cbbAgreement.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbAgreement.setRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
				String str = "";
				if (value != null) {
					Agreement agreement = (Agreement) value;
					str = agreement.getSancEmsNo();
					if (AgreementState.EXECUTING == Integer.valueOf(agreement
							.getDeclareState())) {
						str += "(正在执行)";
					}
				}
				setText(str);
				return this;
			}
		});
		cbbAgreement.setSelectedItem(null);
		cbbAgreement.setUI(new CustomBaseComboBoxUI(300));

		// 初始化运输方式
		this.cbbTransMode.setModel(CustomBaseModel.getInstance()
				.getTransfModel());
		this.cbbTransMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTransMode);
		this.cbbTransMode.setSelectedIndex(-1);
	}

	private void initHeadTable(List list) {
		JTableListModel.dataBind(tbHead, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("发票日期", "invoiceDate", 100));
				list.add(addColumn("发票号码", "invoiceCode", 100));
				list.add(addColumn("出发地", "fromPlace", 100));
				list.add(addColumn("目的地", "toPlace", 100));
				list.add(addColumn("运输方", "transporter", 100));
				list.add(addColumn("运输时间", "transTime", 100));
				list.add(addColumn("运输方式", "transMode.name", 100));
				list.add(addColumn("备注事项", "note", 100));
				list.add(addColumn("发送者", "sender", 100));
				list.add(addColumn("接收者", "receiver", 100));
				list.add(addColumn("委托者", "consigner", 100));
				list.add(addColumn("信用证", "lc", 100));
				list.add(addColumn("委托银行", "accBank", 100));
				return list;
			}
		});
		tableModelHead = (JTableListModel) tbHead.getModel();
	}

	private void initCommInfoTable(List list) {
		JTableListModel.dataBind(tbDetail, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("主序号", "mainNo", 100));
				list.add(addColumn("海关编号", "complex.code", 100));
				list.add(addColumn("名称", "commName", 100));
				list.add(addColumn("规格", "commSpec", 100));
				list.add(addColumn("单价", "unitPrice", 100));
				list.add(addColumn("数量", "amount", 100));
				list.add(addColumn("金额", "totalPrice", 100));
				list.add(addColumn("单位", "unit.name", 100));
				return list;
			}
		});
		tableModelDetail = (JTableListModel) tbDetail.getModel();
	}

	private void fillHeadData(AgreementInvoiceHead invoiceHead) {
		if (invoiceHead == null) {
			return;
		}
		invoiceHead.setInvoiceCode(this.tfInvoiceNo.getText().trim());
		invoiceHead.setInvoiceDate(this.cbbInvoiceDate.getDate());
		invoiceHead.setLc(this.tfLC.getText().trim());
		invoiceHead.setAccBank(this.tfAccBank.getText().trim());
		invoiceHead.setTransporter(this.tfTransporter.getText().trim());
		invoiceHead.setTransTime(this.cbbTransTime.getDate());
		invoiceHead.setSender(taSender.getText().trim());
		invoiceHead.setReceiver(taReceiver.getText().trim());
		invoiceHead.setConsigner(jTextArea2.getText().trim());
		invoiceHead.setNote(taMemo.getText().trim());
		invoiceHead.setTransMode((Transf) this.cbbTransMode.getSelectedItem());
	}

	private void showHeadData(AgreementInvoiceHead invoiceHead) {
		if (invoiceHead == null) {
			return;
		}
		this.tfInvoiceNo.setText(invoiceHead.getInvoiceCode());
		this.cbbInvoiceDate.setDate(invoiceHead.getInvoiceDate());
		this.tfLC.setText(invoiceHead.getLc());
		this.tfAccBank.setText(invoiceHead.getAccBank());
		this.tfTransporter.setText(invoiceHead.getTransporter());
		this.cbbTransTime.setDate(invoiceHead.getTransTime());
		this.cbbTransMode.setSelectedItem(invoiceHead.getTransMode());
		this.taSender.setText(invoiceHead.getSender());
		this.taReceiver.setText(invoiceHead.getReceiver());
		this.jTextArea2.setText(invoiceHead.getConsigner());
		this.taMemo.setText(invoiceHead.getNote());
	}

	private void showDetailData(AgreementInvoiceDetail invoiceDetail) {
		if (invoiceDetail == null) {
			return;
		}
		this.tfCommName.setText(invoiceDetail.getCommName());
		this.tfAmount.setValue(invoiceDetail.getAmount());
		this.tfUnitPrice.setValue(invoiceDetail.getUnitPrice());
		this.tfTotalPrice.setValue(invoiceDetail.getTotalPrice());
	}

	private void showHeadData() {
		Agreement agreement = (Agreement) cbbAgreement.getSelectedItem();
		List list = new ArrayList();
		if (agreement != null) {
			list = fixAssetAction.findAgreementInvoiceHead(new Request(
					CommonVars.getCurrUser()), agreement.getSancEmsNo());
		}
		initHeadTable(list);
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(402, 214, 75, 25));
			jButton.setText("保存");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction
							.checkSaveAgreementInovice(new Request(CommonVars
									.getCurrUser()));
					AgreementInvoiceHead invoiceHead = ((AgreementInvoiceHead) tableModelHead
							.getCurrentRow());
					Agreement agreement = (Agreement) cbbAgreement
							.getSelectedItem();
					if (invoiceHead == null || agreement == null) {
						return;
					}
					fillHeadData(invoiceHead);
					invoiceHead = fixAssetAction.saveAgreementInvoiceHead(
							new Request(CommonVars.getCurrUser()), agreement,
							invoiceHead);
					tableModelHead.updateRow(invoiceHead);
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
			jButton1.setBounds(new Rectangle(520, 214, 75, 25));
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					AgreementInvoiceHead invoiceHead = ((AgreementInvoiceHead) tableModelHead
							.getCurrentRow());
					if (invoiceHead == null) {
						return;
					}
					showHeadData(invoiceHead);
				}
			});
		}
		return jButton1;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
