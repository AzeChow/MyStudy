package com.bestway.client.fixasset;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.AgreementState;
import com.bestway.fixasset.action.FixAssetAction;
import com.bestway.fixasset.entity.Agreement;
import com.bestway.fixtureonorder.action.CheckFixAuthorityAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmFixassetRemainMoneyQuery extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JComboBox cbbAgreement = null;

	private JButton btnQuery = null;

	private JButton btnPrint = null;

	private JButton btnClose = null;

	private FixAssetAction fixAssetAction;

	private JTableListModel tableModelFactory;

	private JTableListModel tableModelCustoms;

	private JTabbedPane jTabbedPane = null;

	private JScrollPane jScrollPane = null;

	private JTable tbFactory = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbCustoms = null;

	private CheckFixAuthorityAction checkFixAuthorityAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmFixassetRemainMoneyQuery() {
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
		this.setTitle("设备金额(余额)查询");
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
			jSplitPane.setBottomComponent(getJTabbedPane());
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
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(42, 22, 60, 19));
			jLabel.setText("协议号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbAgreement(), null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnPrint(), null);
			jPanel.add(getBtnClose(), null);
		}
		return jPanel;
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(new Rectangle(445, 19, 69, 25));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction
							.checkFixassetRemainMoneyQuery(new Request(
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
			btnPrint.setBounds(new Rectangle(531, 19, 69, 25));
			btnPrint.setText("打印");
			btnPrint.setVisible(true);
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction
							.checkFixassetRemainMoneyPrint(new Request(
									CommonVars.getCurrUser()));
					if (jTabbedPane.getSelectedIndex() == 0) {
						if (tableModelFactory == null) {
							return;
						}
						List list = new ArrayList();
						list.addAll(tableModelFactory.getList());
						CustomReportDataSource ds = new CustomReportDataSource(
								list);
						InputStream reportStream = FmFixassetRemainMoneyQuery.class
								.getResourceAsStream("report/FactoryFixassetRemianMoneyReport.jasper");
						Map parameters = new HashMap();
						// SimpleDateFormat dateFormat = new SimpleDateFormat(
						// "yyyy-MM-dd");
						// parameters.put("beginDate",
						// dateFormat.format(cbbBeginDate
						// .getDate()));
						// parameters.put("endDate",
						// dateFormat.format(cbbEndDate
						// .getDate()));
						// parameters
						// .put("currentDate", dateFormat.format(new Date()));
						JasperPrint jasperPrint = null;
						try {
							jasperPrint = JasperFillManager.fillReport(
									reportStream, parameters, ds);
							DgReportViewer viewer = new DgReportViewer(
									jasperPrint);
							viewer.setVisible(true);
						} catch (JRException e1) {
							e1.printStackTrace();
						}
					} else {
						if (tableModelCustoms == null) {
							return;
						}
						List list = new ArrayList();
						list.addAll(tableModelCustoms.getList());
						CustomReportDataSource ds = new CustomReportDataSource(
								list);
						InputStream reportStream = FmFixassetRemainMoneyQuery.class
								.getResourceAsStream("report/CustomsFixassetRemianMoneyReport.jasper");
						Map parameters = new HashMap();
						// SimpleDateFormat dateFormat = new SimpleDateFormat(
						// "yyyy-MM-dd");
						// parameters.put("beginDate",
						// dateFormat.format(cbbBeginDate
						// .getDate()));
						// parameters.put("endDate",
						// dateFormat.format(cbbEndDate
						// .getDate()));
						// parameters
						// .put("currentDate", dateFormat.format(new Date()));
						JasperPrint jasperPrint = null;
						try {
							jasperPrint = JasperFillManager.fillReport(
									reportStream, parameters, ds);
							DgReportViewer viewer = new DgReportViewer(
									jasperPrint);
							viewer.setVisible(true);
						} catch (JRException e1) {
							e1.printStackTrace();
						}
					}
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
			btnClose.setBounds(new Rectangle(613, 19, 69, 25));
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmFixassetRemainMoneyQuery.this.doDefaultCloseAction();
				}
			});
		}
		return btnClose;
	}

	private void showData() {
		if (this.cbbAgreement.getItemCount() <= 0) {
			initFactoryTable(new ArrayList());
			initCustomsTable(new ArrayList());
			return;
		} else {
			if (this.cbbAgreement.getSelectedItem() == null) {
				initFactoryTable(new ArrayList());
				initCustomsTable(new ArrayList());
				return;
			}
			Agreement agreement = (Agreement) this.cbbAgreement
					.getSelectedItem();
			String agreementNo = agreement.getSancEmsNo();
			if (jTabbedPane.getSelectedIndex() == 0) {
				List list = fixAssetAction.findFixassetFactoryRemainMoney(
						new Request(CommonVars.getCurrUser()), agreementNo);
				initFactoryTable(list);
			} else if (jTabbedPane.getSelectedIndex() == 1) {
				List list = fixAssetAction.findFixassetCustomsRemainMoney(
						new Request(CommonVars.getCurrUser()), agreementNo);
				initCustomsTable(list);
			}
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
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
			jTabbedPane.addTab("工厂设备余额", null, getJScrollPane(), null);
			jTabbedPane.addTab("海关设备余额", null, getJScrollPane1(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbFactory());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbFactory() {
		if (tbFactory == null) {
			tbFactory = new JTable();
		}
		return tbFactory;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbCustoms());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbCustoms() {
		if (tbCustoms == null) {
			tbCustoms = new JTable();
		}
		return tbCustoms;
	}

	private void initFactoryTable(List list) {
		JTableListModel.dataBind(tbFactory, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("序号", "mainNo", 100));
				list.add(addColumn("海关编号", "complex.code", 100));
				list.add(addColumn("名称", "commName", 100));
				list.add(addColumn("规格", "commSpec", 100));
				list.add(addColumn("单位", "unit.name", 100));
				list.add(addColumn("备案数量(1)", "porAmount", 100));
				list.add(addColumn("备案金额(2)", "porMoney", 100));
				list.add(addColumn("已变更数量(3)", "changedAmount", 100));
				list.add(addColumn("已变更金额(4)", "changedMoney", 100));
				list.add(addColumn("变更中数量(5)", "changingAmount", 100));
				list.add(addColumn("变更中金额(6)", "changingMoney", 100));
				list.add(addColumn("清单数量=(1)-(3)-(5)", "listAmount", 150));
				list.add(addColumn("工厂金额=(2)-(4)-(6)", "factoryMoney", 150));
				return list;
			}
		});
		tableModelFactory = (JTableListModel) tbFactory.getModel();
	}

	private void initCustomsTable(List list) {
		JTableListModel.dataBind(tbCustoms, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("序号", "mainNo", 100));
				list.add(addColumn("海关编号", "complex.code", 100));
				list.add(addColumn("名称", "commName", 100));
				list.add(addColumn("规格", "commSpec", 100));
				list.add(addColumn("单位", "unit.name", 100));
				list.add(addColumn("备案数量(1)", "porAmount", 100));
				list.add(addColumn("备案金额(2)", "porMoney", 100));
				list.add(addColumn("已办证免税金额(3)", "dutyCertedMoney", 100));
				list.add(addColumn("证免税申请中金额(5)", "dutyCertingMoney", 100));
				list.add(addColumn("海关金额=(2)-(3)-(5)", "customsMoney", 150));
				// list.add(addColumn("工厂金额", "factoryMoney", 100));
				return list;
			}
		});
		tableModelCustoms = (JTableListModel) tbCustoms.getModel();
	}
} // @jve:decl-index=0:visual-constraint="10,10"
