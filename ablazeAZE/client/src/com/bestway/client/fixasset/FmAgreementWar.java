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
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.AgreementState;
import com.bestway.fixasset.action.FixAssetAction;
import com.bestway.fixasset.entity.Agreement;
import com.bestway.fixasset.entity.AgreementWarDetail;
import com.bestway.fixasset.entity.AgreementWarHead;
import com.bestway.fixtureonorder.action.CheckFixAuthorityAction;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmAgreementWar extends JInternalFrameBase {

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

	private JTextField tfWarNo = null;

	private JTextField tfApplier = null;

	private JTextField tfLinkMan = null;

	private JTextField tfLinkTel = null;

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

	private JTextField tfComp = null;

	private JTextField tfAddress = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private CheckFixAuthorityAction checkFixAuthorityAction = null;

	public FmAgreementWar() {
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
		this.setSize(new java.awt.Dimension(731, 482));
		this.setContentPane(getJContentPane());
		this.setTitle("进口全新设备保证书");
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
					FmAgreementWar.this.doDefaultCloseAction();
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
									AgreementWarHead warHead = ((AgreementWarHead) tableModel
											.getCurrentRow());
									list = fixAssetAction
											.findAgreementWarDetail(
													new Request(CommonVars
															.getCurrUser()),
													warHead);
									showHeadData(warHead);
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
							AgreementWarHead warHead = ((AgreementWarHead) tableModelHead
									.getCurrentRow());
							if (jTabbedPane.getSelectedIndex() == 0) {
								if (warHead != null) {
									showHeadData(warHead);
								}
							} else if (jTabbedPane.getSelectedIndex() == 1) {
								if (warHead != null) {
									list = fixAssetAction
											.findAgreementWarDetail(
													new Request(CommonVars
															.getCurrUser()),
													warHead);

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
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(34, 151, 59, 21));
			jLabel6.setText("厂址");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(34, 122, 59, 25));
			jLabel5.setText("联系电话");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(34, 97, 59, 24));
			jLabel4.setText("联系人");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(34, 67, 59, 25));
			jLabel3.setText("申请人");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(34, 39, 59, 24));
			jLabel2.setText("申请企业");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(34, 12, 59, 24));
			jLabel1.setText("报检号");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(getTfWarNo(), null);
			jPanel2.add(getTfApplier(), null);
			jPanel2.add(getTfLinkMan(), null);
			jPanel2.add(getTfLinkTel(), null);
			jPanel2.add(getTfComp(), null);
			jPanel2.add(getTfAddress(), null);
			jPanel2.add(getBtnSave(), null);
			jPanel2.add(getBtnCancel(), null);
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
									AgreementWarDetail warDetail = ((AgreementWarDetail) tableModel
											.getCurrentRow());
									showDetailData(warDetail);
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
	private JTextField getTfWarNo() {
		if (tfWarNo == null) {
			tfWarNo = new JTextField();
			tfWarNo.setBounds(new java.awt.Rectangle(96, 12, 127, 25));
		}
		return tfWarNo;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfApplier() {
		if (tfApplier == null) {
			tfApplier = new JTextField();
			tfApplier.setBounds(new java.awt.Rectangle(96, 67, 127, 25));
		}
		return tfApplier;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLinkMan() {
		if (tfLinkMan == null) {
			tfLinkMan = new JTextField();
			tfLinkMan.setBounds(new java.awt.Rectangle(96, 96, 127, 25));
		}
		return tfLinkMan;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLinkTel() {
		if (tfLinkTel == null) {
			tfLinkTel = new JTextField();
			tfLinkTel.setBounds(new java.awt.Rectangle(96, 125, 127, 24));
		}
		return tfLinkTel;
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
				this.setBounds(0, 0, 300, 20);
				return this;
			}
		});
		cbbAgreement.setSelectedItem(null);
		cbbAgreement.setUI(new CustomBaseComboBoxUI(300));
	}

	private void initHeadTable(List list) {
		JTableListModel.dataBind(tbHead, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("报检号", "warNo", 100));
				list.add(addColumn("申请企业", "company.buName", 100));
				list.add(addColumn("申请人", "applier", 100));
				list.add(addColumn("联系人", "linkMan", 100));
				list.add(addColumn("联系电话", "linkTel", 100));
				list.add(addColumn("厂址", "address", 100));
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
				list.add(addColumn("原产地", "country.name", 100));
				return list;
			}
		});
		tableModelDetail = (JTableListModel) tbDetail.getModel();
	}

	private void fillHeadData(AgreementWarHead warHead) {
		if (warHead == null) {
			return;
		}
		warHead.setWarNo(this.tfWarNo.getText().trim());
		warHead.setApplier(this.tfApplier.getText().trim());
		warHead.setLinkMan(this.tfLinkMan.getText().trim());
		warHead.setLinkTel(this.tfLinkTel.getText().trim());
		warHead.setAddress(this.tfAddress.getText().trim());
	}

	private void showHeadData(AgreementWarHead warHead) {
		if (warHead == null) {
			return;
		}
		this.tfWarNo.setText(warHead.getWarNo());
		this.tfApplier.setText(warHead.getApplier());
		this.tfLinkMan.setText(warHead.getLinkMan());
		this.tfLinkTel.setText(warHead.getLinkTel());
		this.tfAddress.setText(warHead.getAddress());
		Company company = (Company) warHead.getCompany();
		this.tfComp.setText(company.getBuName());
	}

	private void showDetailData(AgreementWarDetail warDetail) {
		if (warDetail == null) {
			return;
		}
		this.tfCommName.setText(warDetail.getCommName());
		this.tfAmount.setValue(warDetail.getAmount());
		this.tfUnitPrice.setValue(warDetail.getUnitPrice());
		this.tfTotalPrice.setValue(warDetail.getTotalPrice());
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComp() {
		if (tfComp == null) {
			tfComp = new JTextField();
			tfComp.setBounds(new java.awt.Rectangle(95, 39, 128, 23));
			tfComp.setEditable(false);
		}
		return tfComp;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAddress() {
		if (tfAddress == null) {
			tfAddress = new JTextField();
			tfAddress.setBounds(new java.awt.Rectangle(96, 153, 127, 24));
		}
		return tfAddress;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(new java.awt.Rectangle(283, 13, 87, 25));
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkSaveAgreementWar(new Request(
							CommonVars.getCurrUser()));
					AgreementWarHead warHead = ((AgreementWarHead) tableModelHead
							.getCurrentRow());
					Agreement agreement = (Agreement) cbbAgreement
							.getSelectedItem();
					if (warHead == null || agreement == null) {
						return;
					}
					fillHeadData(warHead);
					warHead = fixAssetAction.saveAgreementWarHead(new Request(
							CommonVars.getCurrUser()), agreement, warHead);
					tableModelHead.updateRow(warHead);
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new java.awt.Rectangle(283, 61, 87, 25));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					AgreementWarHead warHead = ((AgreementWarHead) tableModelHead
							.getCurrentRow());
					if (warHead == null) {
						return;
					}
					showHeadData(warHead);
				}
			});
		}
		return btnCancel;
	}

	private void showHeadData() {
		Agreement agreement = (Agreement) cbbAgreement.getSelectedItem();
		List list = new ArrayList();
		if (agreement != null) {
			list = fixAssetAction.findAgreementWarHead(new Request(CommonVars
					.getCurrUser()), agreement.getSancEmsNo());
		}
		initHeadTable(list);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
