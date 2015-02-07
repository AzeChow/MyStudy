package com.bestway.common.client.erpbill;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcus.client.common.CommonQueryPage;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.erpbill.action.OrderCommonAction;
import com.bestway.common.erpbill.entity.CustomOrder;
import com.bestway.common.erpbill.entity.CustomOrderDetail;
import com.bestway.common.erpbill.entity.Customparames;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
@SuppressWarnings("unchecked")
public class DgCustomOrder extends JDialogBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JButton btnSave = null;

	private JTextField tfBillCode = null;

	private JCalendarComboBox cbbOrderDate = null;

	private JComboBox cbbCustomer = null;

	private JPanel jPanel = null;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JTableListModel tableModelOrder;

	private JTableListModel tableModelDetail;

	private int dataState = DataState.BROWSE;

	private CustomOrder customOrder = null; // // @jve:decl-index=0:

	private JCheckBox chkifok = null;

	private JCheckBox btnordertype = null;

	private JTable tbDetail = null;

	private JButton btnok = null;

	private JButton btncancelok = null;

	private OrderCommonAction orderCommonAction = null;

	private JLabel jLabel3 = null;

	private JButton jButton = null;

	private JButton jButton1 = null; // @jve:decl-index=0:visual-constraint="821,62"

	private Integer customType = null;

	private JSplitPane jSplitPane = null;

	private JScrollPane jScrollPane1 = null;

	private JPanel jPanel2 = null;

	private JToolBar jJToolBarBar = null;

	private JButton btnEditHead = null;

	private JLabel jLabel4 = null;

	private JRadioButton rbSellOrder = null;

	private JRadioButton rbStockOrder = null;
	private ButtonGroup bg = new ButtonGroup(); // @jve:decl-index=0:

	private JLabel jLabel41 = null;

	private JTextField tfCommodityItem = null;

	/**
	 * This method initializes
	 * 
	 */
	public void settableModelOrder(JTableListModel ordertablemodel) {
		this.tableModelOrder = ordertablemodel;
	}


	public void setState(int dataState) {
		this.setItemCount();
		boolean ifok = false;
		if (this.customOrder != null) {
			ifok = customOrder.getIfok() == null ? false : customOrder
					.getIfok();
		}
		// ----------------------------------------------------------------
		// this.btnAdd.setEnabled(dataState != DataState.BROWSE && !ifok);
		// this.btnDelete.setEnabled(dataState != DataState.BROWSE && !ifok);
		// this.btnEdit.setEnabled(dataState == DataState.BROWSE && !ifok);
		// ----------------------------------------------------------------
		this.btnSave.setEnabled((dataState == DataState.ADD)
				|| (dataState == DataState.EDIT));
		this.btnok.setEnabled(!ifok);
		this.btncancelok.setEnabled(ifok);
		getJButton1().setEnabled(ifok);// 转合同
		this.btnEditHead
				.setEnabled(!ifok
						&& !(dataState == DataState.EDIT || dataState == DataState.ADD));
		// ----------------------------------------------------------------
		this.cbbCustomer.setEnabled(dataState != DataState.BROWSE && !ifok);
		this.cbbOrderDate.setEnabled(dataState != DataState.BROWSE && !ifok);
		this.tfBillCode.setEditable(dataState != DataState.BROWSE && !ifok);
		this.btnordertype.setEnabled(dataState != DataState.BROWSE && !ifok);
		btnordertype.setEnabled(dataState != DataState.BROWSE && !ifok);
		rbStockOrder.setEnabled(dataState != DataState.BROWSE && !ifok);
		rbSellOrder.setEnabled(dataState != DataState.BROWSE && !ifok);
		// ----------------------------------------------------------------
	}

	public DgCustomOrder() {
		super();
		orderCommonAction = (OrderCommonAction) CommonVars
				.getApplicationContext().getBean("orderCommonAction");
		Customparames customparames = orderCommonAction
				.findCustomparames(new Request(CommonVars.getCurrUser()));
		customType = customparames.getSetbgtype();
		initialize();
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(767, 530));
		this.setTitle("客户订单管理");
		this.setContentPane(getJContentPane());
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			if (this.dataState == DataState.ADD) {
				customOrder = null;
			} else {
				customOrder = (CustomOrder) tableModelOrder.getCurrentRow();
			}
			showData();
			showDetailData();
			setState(this.dataState);
		}
		super.setVisible(b);
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
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.setPreferredSize(new Dimension(60, 30));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (fillData()) {
						saveOrderData();
						setState(DataState.BROWSE);
						dataState = DataState.BROWSE;
					}
				}
			});
		}
		return btnSave;
	}

	private boolean checkData() {
		if (this.tfBillCode.getText() == null
				|| "".equals(this.tfBillCode.getText().trim())) {
			JOptionPane.showMessageDialog(DgCustomOrder.this, "订单号码不能为空!",
					"提示", JOptionPane.OK_OPTION);
			tfBillCode.setFocusable(true);
			return false;
		}
		if (this.cbbOrderDate.getDate() == null) {
			JOptionPane.showMessageDialog(DgCustomOrder.this, "订单日期不能为空!",
					"提示", JOptionPane.OK_OPTION);
			return false;
		}
		if (this.cbbCustomer.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgCustomOrder.this, "客户不能为空!", "提示",
					JOptionPane.OK_OPTION);
			return false;
		}
		return true;
	}

	private boolean fillData() {
		if (!checkData()) {
			return false;
		}
		if (this.customOrder == null) {
			String billCode = this.tfBillCode.getText().trim();
			CustomOrder co = orderCommonAction.findCustomOrder(new Request(
					CommonVars.getCurrUser()), billCode);
			if (co != null) {
				JOptionPane.showMessageDialog(DgCustomOrder.this, "订单号码不能重复!",
						"提示", JOptionPane.OK_OPTION);
				return false;
			}
			customOrder = new CustomOrder();
		}
		customOrder.setBillCode(this.tfBillCode.getText().trim());
		customOrder.setIfzc(getBtnordertype().isSelected());
		customOrder.setOrderDate(getCbbOrderDate().getDate());
		customOrder.setCustomer((ScmCoc) getCbbCustomer().getSelectedItem());
		customOrder.setContractCount(Integer.valueOf(this.tfCommodityItem
				.getText()));

		return true;
	}

	private boolean saveOrderData() {
		Customparames customparames = new Customparames();
		Integer importcount = 0;
		customparames = orderCommonAction.findCustomparames(new Request(
				CommonVars.getCurrUser()));
		if (dataState == DataState.ADD) {
			customOrder = new CustomOrder();
			customOrder.setCustomparames(customparames);
			customOrder.setCustomType(customparames.getSetbgtype());
			customOrder.setRateSource(customparames.getRateSource());
		}
		if (customOrder == null) {
			return false;
		}
		customOrder.setBillCode(this.tfBillCode.getText());
		Boolean ifzc = false;
		if (this.btnordertype.isSelected()) {
			ifzc = true;
		}
		customOrder.setIfzc(ifzc);
		if (getRbSellOrder().isSelected()) {
			customOrder.setOrdertype(0);
		} else if (getRbStockOrder().isSelected()) {
			customOrder.setOrdertype(1);
		}
		customOrder.setOrderDate(this.cbbOrderDate.getDate());
		customOrder.setCustomer((ScmCoc) this.cbbCustomer.getSelectedItem());
		importcount = orderCommonAction.findCustomOrderMaxImportcount(
				new Request(CommonVars.getCurrUser()), customOrder
						.getBillCode());
		importcount += 1;
		if (customOrder.getImportcount() <= 0) {
			customOrder.setImportcount(importcount);
		}
		customOrder = orderCommonAction.saveCustomOrder(new Request(CommonVars
				.getCurrUser()), customOrder);
		if (dataState == DataState.ADD) {
			tableModelOrder.addRow(customOrder);
		} else if (dataState == DataState.EDIT) {
			tableModelOrder.updateRow(customOrder);
		}
		return true;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBillCode() {
		if (tfBillCode == null) {
			tfBillCode = new JTextField();
			tfBillCode.setBounds(new Rectangle(373, 11, 196, 24));
		}
		return tfBillCode;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbOrderDate() {
		if (cbbOrderDate == null) {
			cbbOrderDate = new JCalendarComboBox();
			cbbOrderDate.setBounds(new Rectangle(92, 45, 196, 24));
		}
		return cbbOrderDate;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustomer() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(new Rectangle(373, 45, 196, 24));
		}
		return cbbCustomer;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel41 = new JLabel();
			jLabel41.setBounds(new Rectangle(575, 17, 48, 18));
			jLabel41.setText("\u5546\u54c1\u9879\u6570");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(10, 7, 72, 24));
			jLabel4.setText("订单类型");
			jLabel3 = new JLabel();
			jLabel3.setText("");
			jLabel3.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(321, 44, 50, 24));
			jLabel2.setText("客户名称");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(10, 44, 72, 24));
			jLabel1.setText("订单日期");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(321, 10, 50, 24));
			jLabel.setText("订单编号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getCbbCustomer(), null);
			jPanel.add(getCbbOrderDate(), null);
			jPanel.add(getTfBillCode(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getChkifok(), null);
			jPanel.add(getBtnordertype(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getRbSellOrder(), null);
			jPanel.add(getRbStockOrder(), null);
			jPanel.add(jLabel41, null);
			jPanel.add(getTfCommodityItem(), null);
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
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.setPreferredSize(new Dimension(750, 34));
			jToolBar.add(getBtnEditHead());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnok());
			jToolBar.add(getBtncancelok());
			// jToolBar.add(getJButton1());
			// jToolBar.add(getJButton2());
			jToolBar.add(getJButton());
		}
		return jToolBar;
	}

	private void addDetial() {
		String materielType = MaterielType.MATERIEL;
		if (getRbStockOrder().isSelected()) {
			materielType = MaterielType.MATERIEL;
		} else if (getRbSellOrder().isSelected()) {
			materielType = MaterielType.FINISHED_PRODUCT;
		}
		Materiel mt = (Materiel) CustomsOrderCommonQuery.getInstance()
				.findMaterielByType(materielType);
		if (mt == null) {
			return;
		}
		CustomOrderDetail detail = new CustomOrderDetail();
		detail.setMateriel(mt);
		detail.setUnitConvert(mt.getUnitConvert());
		detail.setCustomOrder(customOrder);
		detail = orderCommonAction.saveorderdetail(new Request(CommonVars
				.getCurrUser()), detail);
		tableModelDetail.addRow(detail);
		// updateList.add(detail);

	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.setPreferredSize(new Dimension(60, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!checkCaData()) {
						return;
					}

					int orderType = 0;
					if (getRbSellOrder().isSelected()) {
						orderType = 0;
					} else if (getRbStockOrder().isSelected()) {
						orderType = 1;
					}
					if (customOrder.getIfzc() != null && customOrder.getIfzc()) {
						addDetial();
					} else {
						// ----------------------------------------------------------------------
						Integer customType = customOrder.getCustomType();
						// System.out.print("==========customType===="+customType);
						List<CustomOrderDetail> updateList = new ArrayList<CustomOrderDetail>();
						if (customType == 1) {
							List list = CommonQueryPage.getInstance()
									.getOrderMaterielByDzsc();

							if (list == null || list.size() <= 0) {
								return;
							}
							for (int i = 0; i < list.size(); i++) {
								DzscInnerMergeData materiel = (DzscInnerMergeData) list
										.get(i);
								if (materiel != null) {
									CustomOrderDetail customdetail = new CustomOrderDetail();
									if (materiel.getMateriel() != null) {
										customdetail.setMateriel(materiel
												.getMateriel());
										if (materiel.getMateriel().getCalUnit() != null) {
											customdetail
													.setCalUnit(materiel
															.getMateriel()
															.getCalUnit());
										}
										customdetail.setUnitPrice(materiel
												.getMateriel().getPtPrice());
									}
									Double unitConvert = (materiel
											.getMateriel().getUnitConvert() == null || materiel
											.getMateriel().getUnitConvert() == 0.0) ? 1.0
											: materiel.getMateriel()
													.getUnitConvert();
									customdetail.setUnitConvert(unitConvert);
									customdetail.setCustomOrder(customOrder);
									if (materiel.getDzscTenInnerMerge() != null) {
										customdetail.setBgname(materiel
												.getDzscTenInnerMerge()
												.getTenPtName());
										customdetail.setBgspec(materiel
												.getDzscTenInnerMerge()
												.getTenPtSpec());
										customdetail.setBgunit(materiel
												.getDzscTenInnerMerge()
												.getTenUnit());
									} else {
										JOptionPane.showMessageDialog(null,
												"您选中的成品没有归并关系!", "提示",
												JOptionPane.OK_OPTION);
										return;
									}
									customdetail.setVersion(0);
									customdetail = orderCommonAction
											.saveorderdetail(new Request(
													CommonVars.getCurrUser()),
													customdetail);
									tableModelDetail.addRow(customdetail);
									updateList.add(customdetail);
								}
							}
							// --------------------------------------------------------------------
						} else if (customType == 2 || customType == 3) {
							List list = CommonQueryPage.getInstance()
									.getOrderMaterielByBcs(orderType);
							if (list == null || list.size() <= 0) {
								return;
							}
							for (int i = 0; i < list.size(); i++) {
								BcsInnerMerge materiel = (BcsInnerMerge) list
										.get(i);
								if (materiel != null) {
									CustomOrderDetail customdetail = new CustomOrderDetail();
									if (materiel.getMateriel() != null) {
										customdetail.setMateriel(materiel
												.getMateriel());
										customdetail.setCalUnit(materiel
												.getMateriel().getCalUnit());
										customdetail.setUnitPrice(materiel
												.getMateriel().getPtPrice());
									}
									Double unitConvert = (materiel
											.getMateriel().getUnitConvert() == null || materiel
											.getMateriel().getUnitConvert() == 0.0) ? 1.0
											: materiel.getMateriel()
													.getUnitConvert();
									customdetail.setUnitConvert(unitConvert);
									customdetail.setCustomOrder(customOrder);
									if (materiel.getBcsTenInnerMerge() != null) {

										customdetail.setBgname(materiel
												.getBcsTenInnerMerge()
												.getName());
										customdetail.setBgspec(materiel
												.getBcsTenInnerMerge()
												.getSpec());
										customdetail.setBgunit(materiel
												.getBcsTenInnerMerge()
												.getComUnit());
										customdetail.setCurr(materiel
												.getBcsTenInnerMerge()
												.getCurr());
									} else {
										JOptionPane.showMessageDialog(null,
												"您选中的成品没有归并关系!", "提示",
												JOptionPane.OK_OPTION);
										return;
									}
									customdetail.setVersion(0);
									customdetail = orderCommonAction
											.saveorderdetail(new Request(
													CommonVars.getCurrUser()),
													customdetail);
									tableModelDetail.addRow(customdetail);
									updateList.add(customdetail);
								}
							}
						}
						// －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
						if (tableModelDetail.getSize() > 0) {
							customOrder = orderCommonAction.saveCustomOrder(
									new Request(CommonVars.getCurrUser()),
									customOrder);
							tableModelOrder.updateRow(customOrder);
						} else {
							customOrder = orderCommonAction.saveCustomOrder(
									new Request(CommonVars.getCurrUser()),
									customOrder);
							tableModelOrder.updateRow(customOrder);
						}
						setState(DataState.BROWSE);
						String title="";
						if(rbStockOrder.isSelected()){
							title="采购订单管理";
						}else if(rbSellOrder.isSelected()){
							title="销售订单管理";
						}
						DgCustomOrderDetail dg = new DgCustomOrderDetail(
								customOrder, tableModelDetail,title);
						dg.setAdd(true);
						dg.setCurrOrderDetail(updateList.get(0));
						dg.setVisible(true);
					}
				}

			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setPreferredSize(new Dimension(60, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelDetail.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(null, "请选择订单明细!", "提示!",
								JOptionPane.OK_OPTION);
						return;
					}
					if (!checkCaData()) {
						return;
					}
					String title="";
					if(rbStockOrder.isSelected()){
						title="采购订单管理";
					}else if(rbSellOrder.isSelected()){
						title="销售订单管理";
					}
					DgCustomOrderDetail dg = new DgCustomOrderDetail(
							customOrder, tableModelDetail,title);
					dg.setCurrOrderDetail((CustomOrderDetail) tableModelDetail
							.getCurrentRow());
					dg.setDataState(DataState.EDIT);
					dg.setVisible(true);

				}
			});
		}
		return btnEdit;
	}

	private boolean checkCaData() {
		if (customOrder == null) {
			JOptionPane.showMessageDialog(null, "请先保存订单!", "提示!",
					JOptionPane.OK_OPTION);
			return false;
		}
		if (customOrder.getIfok()) {
			JOptionPane.showMessageDialog(DgCustomOrder.this, "订单已经生效,操作取消!",
					"提示!", JOptionPane.OK_OPTION);
			return false;
		}
		return true;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setPreferredSize(new Dimension(60, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!checkCaData()) {
						return;
					}
					List<CustomOrderDetail> listSelect = new ArrayList<CustomOrderDetail>();
					listSelect = tableModelDetail.getCurrentRows();
					if (listSelect.size() == 0) {
						JOptionPane.showMessageDialog(DgCustomOrder.this,
								"请选择要删除的订单明细！", "提示!", JOptionPane.OK_OPTION);
						return;
					} else {
						if (customOrder.getIfok()) {
							JOptionPane.showMessageDialog(null,
									"订单已生效不能删除单体内容!", "提示!",
									JOptionPane.OK_OPTION);
							return;
						}
						if (JOptionPane.showConfirmDialog(DgCustomOrder.this,
								"确定要删除这些订单明细吗?", "确认!", 0) == 0) {
							for (int i = 0; i < listSelect.size(); i++) {
								CustomOrderDetail customOrderDetail = (CustomOrderDetail) listSelect
										.get(i);
								orderCommonAction.droporderdetail(new Request(
										CommonVars.getCurrUser()),
										customOrderDetail);
								tableModelDetail.deleteRow(customOrderDetail);
							}
						}
					}
					setState(DataState.BROWSE);
				}
			});
		}
		return btnDelete;
	}

	private void initUIComponents() {
		List list = orderCommonAction.findScmCocs(new Request(CommonVars
				.getCurrUser()));
		this.cbbCustomer.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCustomer, "code", "name", 270);
		bg.add(rbSellOrder);
		bg.add(rbStockOrder);
	}

	private void shoeEmpityData() {
		getTfBillCode().setText("");
		getBtnordertype().setSelected(false);
		getChkifok().setSelected(false);
		getCbbOrderDate().setDate(new Date());
		getCbbCustomer().setSelectedItem(null);
		tfCommodityItem.setText("0");
	}

	private void showData() {
		if (this.dataState == DataState.ADD) {
			shoeEmpityData();
		} else {
			if (customOrder == null) {
				shoeEmpityData();
			} else {
				getTfBillCode().setText(customOrder.getBillCode());
				getBtnordertype().setSelected(
						customOrder.getIfzc() == null ? false : customOrder
								.getIfzc());
				getChkifok().setSelected(
						customOrder.getIfok() == null ? false : customOrder
								.getIfok());
				getCbbOrderDate().setDate(customOrder.getOrderDate());
				getCbbCustomer().setSelectedItem(customOrder.getCustomer());
				if (customOrder.getOrdertype() == 0) {
					rbSellOrder.setSelected(true);
				} else {
					rbStockOrder.setSelected(true);
				}
				List dataSource = this.orderCommonAction.findCustomOrderDetail(
						new Request(CommonVars.getCurrUser()), customOrder);
				this.tfCommodityItem.setText(String.valueOf(dataSource.size()));
			}
		}

	}

	/**
	 * 设置商品项数
	 * 
	 */
	private void setItemCount() {
		if (this.customOrder != null && this.dataState != DataState.ADD) {
			int itemCount = orderCommonAction.findCustomOrderDetailCount(
					new Request(CommonVars.getCurrUser()), customOrder.getId());
			tfCommodityItem.setText(String.valueOf(itemCount));
			customOrder.setContractCount(Integer.valueOf(this.tfCommodityItem
					.getText()));
		} else {
			tfCommodityItem.setText("0");
		}
	}

	private void showDetailData() {
		List list = customOrder == null ? new ArrayList() : orderCommonAction
				.findCustomOrderDetail(new Request(CommonVars.getCurrUser()),
						customOrder);
		JTableListModel.dataBind(tbDetail, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("成品料号", "materiel.ptNo", 100));
				list.add(addColumn("商品名称", "materiel.factoryName", 150));
				list.add(addColumn("型号规格", "materiel.factorySpec", 150));
				list.add(addColumn("成品版本", "version", 70));
				list.add(addColumn("交货日期", "salesdate", 80));
				list.add(addColumn("计量单位", "calUnit.name", 80));
				list.add(addColumn("币别", "curr.name", 80));
				list.add(addColumn("单价", "unitPrice", 100));
				list.add(addColumn("数量", "amount", 100));
				list.add(addColumn("折算报关数量", "bgamount", 100));
				list.add(addColumn("未转合同数量", "notContractNum", 100));
				list.add(addColumn("已转合同数量", "contractNum", 100));
				if (customOrder != null && customOrder.getIfzc() != null
						&& customOrder.getIfzc()) {
					list.add(addColumn("未转关封数量", "notTransNum", 100));
					list.add(addColumn("已转关封数量", "transNum", 100));
				} else {
					list.add(addColumn("是否已转合同", "ifcustomer", 80));
				}
				list.add(addColumn("总价", "totalPrice", 100));
				return list;
			}
		});
		tableModelDetail = (JTableListModel) tbDetail.getModel();
	}

	/**
	 * This method initializes chkifok
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getChkifok() {
		if (chkifok == null) {
			chkifok = new JCheckBox();
			chkifok.setBounds(new Rectangle(584, 45, 75, 21));
			chkifok.setText("是否生效");
			chkifok.setEnabled(false);
		}
		return chkifok;
	}

	/**
	 * This method initializes btnordertype
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JCheckBox getBtnordertype() {
		if (btnordertype == null) {
			btnordertype = new JCheckBox();
			btnordertype.setBounds(new Rectangle(667, 44, 73, 21));
			btnordertype.setText("是否转厂");
			btnordertype.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					// if (customOrder != null) {
					// customOrder.setIfzc(getBtnordertype().isSelected());
					// }
				}
			});
		}
		return btnordertype;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (tbDetail == null) {
			tbDetail = new JTable();
			tbDetail.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() >= 2) {
						if (tableModelDetail.getCurrentRowsSele().size() <= 0) {
							JOptionPane.showMessageDialog(null, "请选择订单明细！",
									"提示！", JOptionPane.OK_OPTION);
							return;
						}
						if (customOrder.getIfok()) {
							return;
						}
						String title="";
						if(rbStockOrder.isSelected()){
							title="采购订单管理";
						}else if(rbSellOrder.isSelected()){
							title="销售订单管理";
						}
						DgCustomOrderDetail dg = new DgCustomOrderDetail(
								customOrder, tableModelDetail,title);
						dg
								.setCurrOrderDetail((CustomOrderDetail) tableModelDetail
										.getCurrentRow());
						dg.setDataState(DataState.EDIT);
						dg.setVisible(true);
					}
				}
			});
		}
		return tbDetail;
	}

	private boolean checkDetail(CustomOrderDetail detail, int i) {
		if (detail.getSalesDate() == null) {
			JOptionPane.showMessageDialog(DgCustomOrder.this, "第" + i
					+ "笔数据交货日期为空！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;

		}
		if (detail.getUnitPrice() == null || detail.getUnitPrice() == 0.0) {
			JOptionPane.showMessageDialog(DgCustomOrder.this, "第" + i
					+ "笔数据单价不能为空！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;

		}
		if (detail.getCurr() == null) {
			JOptionPane.showMessageDialog(DgCustomOrder.this, "第" + i
					+ "笔数据币别不能为空！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;

		}
		if (detail.getAmount() == null || detail.getAmount() == 0.0) {
			JOptionPane.showMessageDialog(DgCustomOrder.this, "第" + i
					+ "笔数据数量不能为空！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;

		}
		return true;
	}

	/**
	 * This method initializes btnok
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnok() {
		if (btnok == null) {
			btnok = new JButton();
			btnok.setText("生效");
			btnok.setPreferredSize(new Dimension(60, 30));
			btnok.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = tableModelDetail.getList();
					for (int j = 0; j < list.size(); j++) {
						CustomOrderDetail detail = (CustomOrderDetail) list
								.get(j);
						if (!checkDetail(detail, j + 1)) {
							return;
						}
					}
					if (list.size() == 0) {
						JOptionPane.showMessageDialog(DgCustomOrder.this,
								"订单明细没有记录，不能生效！", "提示", JOptionPane.OK_OPTION);
						return;
					}
					if (customOrder.getIfzc() == null && !customOrder.getIfzc()) {
						List listExgBom = orderCommonAction.isExitInCustomBom(
								new Request(CommonVars.getCurrUser()),
								customOrder, "0");
						List listImgBom = orderCommonAction.isExitInCustomBom(
								new Request(CommonVars.getCurrUser()),
								customOrder, "2");
						List listExgInner = orderCommonAction.isExitInInner(
								new Request(CommonVars.getCurrUser()),
								customOrder, "0");
						List listImgInner = orderCommonAction.isExitInInner(
								new Request(CommonVars.getCurrUser()),
								customOrder, "2");
						List listExgRecords = orderCommonAction
								.isExitInRecords(new Request(CommonVars
										.getCurrUser()), customOrder, "0");
						List listImgRecords = orderCommonAction
								.isExitInRecords(new Request(CommonVars
										.getCurrUser()), customOrder, "2");
						System.out.println("1::" + listExgBom + "    2:::"
								+ listImgBom + "   3:::" + listExgInner
								+ "   4:::" + listImgInner + "5:::"
								+ listExgRecords + "   6:::" + listImgRecords);
						if (listExgBom.size() != 0) {
							String mesage = getMesage(listExgBom);
							JOptionPane.showMessageDialog(DgCustomOrder.this,
									"成品料号为" + mesage + "的成品在报关ＢＯＭ中不存在!", "警告",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (listImgBom.size() != 0) {
							String mesage = getMesage(listImgBom);
							JOptionPane.showMessageDialog(DgCustomOrder.this,
									"料件料号为" + mesage + "的料件在报关ＢＯＭ中不存在!", "警告",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (listExgInner.size() != 0) {
							String mesage = getMesage(listExgInner);
							JOptionPane.showMessageDialog(DgCustomOrder.this,
									"成品料号为" + mesage + "的成品在归并关系中不存在!", "警告",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (listImgInner.size() != 0) {
							String mesage = getMesage(listImgInner);
							JOptionPane.showMessageDialog(DgCustomOrder.this,
									"料件料号为" + mesage + "的料件在归并关系中不存在!", "警告",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (listExgRecords.size() != 0) {
							String mesage = getMesage(listExgRecords);
							JOptionPane.showMessageDialog(DgCustomOrder.this,
									"成品料号为" + mesage + "的成品没有备案!", "警告",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (listImgRecords.size() != 0) {
							String mesage = getMesage(listImgRecords);
							JOptionPane.showMessageDialog(DgCustomOrder.this,
									"料件料号为" + mesage + "的料件没有备案!", "警告",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}
					// ----------------------------------------------------------------
					try {
						customOrder = orderCommonAction.okorder(new Request(
								CommonVars.getCurrUser()), customOrder);
						tableModelOrder.updateRow(customOrder);
						setState(DataState.BROWSE);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		return btnok;
	}

	private String getMesage(List list) {
		String mesage = "";
		for (int i = 0; i < list.size(); i++) {
			String ptNo = list.get(i).toString();
			String index = String.valueOf(i + 1);
			mesage += (index + ": " + ptNo + " ");
		}
		return mesage;
	}

	/**
	 * This method initializes btncancelok
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtncancelok() {
		if (btncancelok == null) {
			btncancelok = new JButton();
			btncancelok.setText("回卷");
			btncancelok.setPreferredSize(new Dimension(60, 30));
			btncancelok.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						List listCustomOrder = new ArrayList();
						listCustomOrder.add(customOrder);
						String billNo = orderCommonAction
								.countExsitsNotChangeContract(new Request(
										CommonVars.getCurrUser()),
										listCustomOrder);
						if (!"".equals(billNo)) {
							JOptionPane.showMessageDialog(DgCustomOrder.this,
									"所选订单号" + billNo + " 已转合同,不能回卷", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						orderCommonAction.delCustomOrderAll(new Request(
								CommonVars.getCurrUser()), customOrder);
						customOrder = orderCommonAction.cancelorder(
								new Request(CommonVars.getCurrUser()),
								customOrder);

						tableModelOrder.updateRow(customOrder);
						setState(DataState.BROWSE);
					} catch (Exception ex) {

					}
				}
			});
		}
		return btncancelok;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("关闭");
			jButton.setPreferredSize(new Dimension(60, 30));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
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
			jButton1.setText("转合同");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<CustomOrderDetail> listCustomOrderDetail = new ArrayList<CustomOrderDetail>();

					if (tableModelDetail.getList().size() > 0) {
						JOptionPane.showMessageDialog(DgCustomOrder.this,
								"没有可转合同的订单明细！", "提示", JOptionPane.OK_OPTION);
						return;
					}
					DgCustomOrderToContract dg = new DgCustomOrderToContract();
					// dg.setOrderDetailList(tableModelDetail.getList());
					dg.setCustomType(customType);
					dg.setVisible(true);

					tableModelDetail.updateRows(listCustomOrderDetail);

				}
			});
		}
		return jButton1;
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
			jSplitPane.setBottomComponent(getJPanel2());
			jSplitPane.setDividerLocation(75);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable());
		}
		return jScrollPane1;
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
			jPanel2.add(getJJToolBarBar(), BorderLayout.NORTH);
			jPanel2.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jJToolBarBar = new JToolBar();
			jJToolBarBar.setLayout(f);
			jJToolBarBar.add(getBtnAdd());
			jJToolBarBar.add(getBtnEdit());
			jJToolBarBar.add(getBtnDelete());
		}
		return jJToolBarBar;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This method initializes btnEditHead
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditHead() {
		if (btnEditHead == null) {
			btnEditHead = new JButton();
			btnEditHead.setText("修改");
			btnEditHead.setPreferredSize(new Dimension(60, 30));
			btnEditHead.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setState(DataState.EDIT);
					dataState = DataState.EDIT;
				}
			});
		}
		return btnEditHead;
	}

	/**
	 * This method initializes rbSellOrder
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbSellOrder() {
		if (rbSellOrder == null) {
			rbSellOrder = new JRadioButton();
			rbSellOrder.setBounds(new Rectangle(99, 10, 73, 24));
			rbSellOrder.setSelected(true);
			rbSellOrder.setText("销售订单");
			rbSellOrder.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (getRbSellOrder().isSelected()) {
						// if (customOrder != null) {
						// customOrder.setCustomType(0);
						// }
					}
				}
			});
		}
		return rbSellOrder;
	}

	/**
	 * This method initializes rbStockOrder
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbStockOrder() {
		if (rbStockOrder == null) {
			rbStockOrder = new JRadioButton();
			rbStockOrder.setBounds(new Rectangle(196, 10, 73, 24));
			rbStockOrder.setText("采购订单");
			rbStockOrder.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (getRbStockOrder().isSelected()) {
						// if (customOrder != null) {
						// customOrder.setCustomType(1);
						// }

					}
				}
			});
		}
		return rbStockOrder;
	}

	/**
	 * This method initializes tfCommodityItem
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCommodityItem() {
		if (tfCommodityItem == null) {
			tfCommodityItem = new JTextField();
			tfCommodityItem.setEditable(false);
			tfCommodityItem.setBounds(new Rectangle(627, 14, 97, 22));
		}
		return tfCommodityItem;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
