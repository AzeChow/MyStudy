package com.bestway.client.fixasset;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.AgreementState;
import com.bestway.fixasset.action.FixAssetAction;
import com.bestway.fixasset.entity.Agreement;
import com.bestway.fixtureonorder.action.CheckFixAuthorityAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class FmFixassetStatusQuery extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JLabel jLabel = null;

	private JComboBox cbbAgreement = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel2 = null;

	private JButton btnQuery = null;

	private JButton btnPrint = null;

	private JButton btnClose = null;

	private FixAssetAction fixAssetAction;

	private JTableListModel tableModel;

	private CheckFixAuthorityAction checkFixAuthorityAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmFixassetStatusQuery() {
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
		this.setSize(new Dimension(721, 441));
		this.setContentPane(getJContentPane());
		this.setTitle("设备状态(进度)查询");
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						initUIComponents();
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
			jSplitPane.setDividerSize(4);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setDividerLocation(100);
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
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(204, 56, 15, 21));
			jLabel2.setText("到");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(42, 56, 70, 18));
			jLabel1.setText("收件日期从");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(42, 22, 60, 19));
			jLabel.setText("协议号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbAgreement(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnPrint(), null);
			jPanel.add(getBtnClose(), null);
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
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAgreement() {
		if (cbbAgreement == null) {
			cbbAgreement = new JComboBox();
			cbbAgreement.setBounds(new Rectangle(113, 19, 198, 23));
		}
		return cbbAgreement;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(113, 54, 90, 23));
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
			cbbEndDate.setBounds(new Rectangle(220, 54, 91, 23));
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
			btnQuery.setBounds(new Rectangle(508, 17, 90, 25));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkFixassetStatusQuery(new Request(CommonVars.getCurrUser()));
					showData();
				}
			});
		}
		return btnQuery;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setBounds(new Rectangle(508, 56, 90, 25));
			btnPrint.setText("打印");
			btnPrint.setVisible(false);
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setBounds(new Rectangle(508, 57, 90, 25));
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmFixassetStatusQuery.this.doDefaultCloseAction();
				}
			});
		}
		return btnClose;
	}

	private void showData() {
		if (this.cbbAgreement.getItemCount() <= 0) {
			initGroupDetailTable(new ArrayList());
			return;
		} else {
			if (this.cbbAgreement.getSelectedItem() == null) {
				initGroupDetailTable(new ArrayList());
				return;
			}
			Agreement agreement = (Agreement) this.cbbAgreement
					.getSelectedItem();
			String agreementNo = agreement.getSancEmsNo();
			Date beginDate = this.cbbBeginDate.getDate();
			Date endDate = this.cbbEndDate.getDate();
			List list = fixAssetAction.findFixassetStatus(new Request(
					CommonVars.getCurrUser()), agreementNo, beginDate, endDate);
			initGroupDetailTable(list);
		}
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
		cbbAgreement.setSelectedIndex(-1);

		this.cbbBeginDate.setDate(null);
		this.cbbEndDate.setDate(null);
	}

	private void initGroupDetailTable(List list) {
		JTableListModel.dataBind(jTable, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("状态", "stateMark", 100));
				list.add(addColumn("批次", "groupNo", 100));
				// list.add(addColumn("申请人", "applier", 100));
				list.add(addColumn("报关单号", "customsDeclarationCode", 100));
				list.add(addColumn("发票号", "invoiceNo", 100));
				list.add(addColumn("征免税号", "certNo", 100));
				list.add(addColumn("保证书号", "warNo", 100));
				list.add(addColumn("商品编号", "complexCode", 100));
				list.add(addColumn("名称", "commName", 100));
				list.add(addColumn("规格", "commSpec", 100));
				list.add(addColumn("收件日期", "createDate", 100));
				list.add(addColumn("征免税证递单日期", "dutyCertApplyDate", 100));
				list.add(addColumn("征免税证出单日期", "dutyCertApproveDate", 100));
				list.add(addColumn("待出货通知日期", "prepExportNoteDate", 100));
				list.add(addColumn("设备到港日期", "arriveHKDate", 100));
				list.add(addColumn("设备到莞日期", "arriveDGDate", 100));
				list.add(addColumn("商检日期", "inspectDate", 100));
				list.add(addColumn("报关日期", "declareDate", 100));
				list.add(addColumn("进厂日期", "inFactDate", 100));
				return list;
			}
		});
		tableModel = (JTableListModel) jTable.getModel();
	}
} // @jve:decl-index=0:visual-constraint="10,10"
