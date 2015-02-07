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
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class FmFixassetCustomsDeclarationStat extends JInternalFrameBase {

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

	private JPanel jPanel1 = null;

	private JRadioButton rbImport = null;

	private JRadioButton rbExport = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="747,64"

	private CheckFixAuthorityAction checkFixAuthorityAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmFixassetCustomsDeclarationStat() {
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
		this.setTitle("报关单查询");
		this.getButtonGroup();
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
			jLabel1.setText("报关日期从");
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
			jPanel.add(getJPanel1(), null);
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
			btnQuery.setBounds(new Rectangle(564, 18, 76, 25));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction
							.checkFixassetCustomsDeclarationStat(new Request(
									CommonVars.getCurrUser()));
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
			btnPrint.setBounds(new Rectangle(508, 52, 90, 25));
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
			btnClose.setBounds(new Rectangle(564, 49, 76, 25));
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmFixassetCustomsDeclarationStat.this
							.doDefaultCloseAction();
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
			boolean isImport = this.rbImport.isSelected();
			List list = fixAssetAction.findCustomsDeclarationStat(new Request(
					CommonVars.getCurrUser()), agreementNo, beginDate, endDate,
					isImport);
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
				list.add(addColumn("报关日期",
						"baseCustomsDeclaration.declarationDate", 100));
				list.add(addColumn("报关单号",
						"baseCustomsDeclaration.customsDeclarationCode", 100));
				list.add(addColumn("进出口类型",
						"baseCustomsDeclaration.impExpType", 100));
				list.add(addColumn("贸易方式",
						"baseCustomsDeclaration.tradeMode.name", 100));
				list.add(addColumn("客户名称",
						"baseCustomsDeclaration.customer.name", 100));
				list.add(addColumn("运输工具名称",
						"baseCustomsDeclaration.conveyance", 100));
				list.add(addColumn("商品编码", "complex.code", 100));
				list.add(addColumn("品名", "commName", 100));
				list.add(addColumn("规格", "commSpec", 100));
				list.add(addColumn("单位", "unit.name", 50));
				list.add(addColumn("数量", "commAmount", 100));
				list.add(addColumn("价值", "commTotalPrice", 100));
				list.add(addColumn("数量累计", "commAddUpAmount", 100));
				list.add(addColumn("法定数量", "firstAmount", 100));
				list.add(addColumn("法定单位", "legalUnit.name", 100));
				list.add(addColumn("报关单单价", "commUnitPrice", 100));
				list.add(addColumn("毛重", "grossWeight", 100));
				list.add(addColumn("净重", "netWeight", 100));
				list.add(addColumn("总毛重", "baseCustomsDeclaration.grossWeight",
						100));
				list.add(addColumn("总净重", "baseCustomsDeclaration.netWeight",
						100));
				list.add(addColumn("发票号", "baseCustomsDeclaration.invoiceCode",
						100));
				list.add(addColumn("核销单号",
						"baseCustomsDeclaration.authorizeFile", 100));
				list.add(addColumn("加工费单价", "processUnitPrice", 100));
				return list;
			}
		});
		tableModel = (JTableListModel) jTable.getModel();
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(342, 10, 138, 70));
			jPanel1.setBorder(BorderFactory.createTitledBorder(null,
					"\u8fdb\u51fa\u53e3\u7c7b\u578b",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel1.add(getRbImport(), null);
			jPanel1.add(getRbExport(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbImport() {
		if (rbImport == null) {
			rbImport = new JRadioButton();
			rbImport.setBounds(new Rectangle(35, 23, 61, 17));
			rbImport.setSelected(true);
			rbImport.setText("进口");
		}
		return rbImport;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbExport() {
		if (rbExport == null) {
			rbExport = new JRadioButton();
			rbExport.setBounds(new Rectangle(35, 43, 61, 18));
			rbExport.setText("出口");
		}
		return rbExport;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.rbExport);
			buttonGroup.add(this.rbImport);
		}
		return buttonGroup;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
