package com.bestway.bls.client.storagebill;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.client.dictpor.DgAllChangState;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bls.action.BlsCheckCancelAction;
import com.bestway.bls.action.StorageBillAction;
import com.bestway.bls.entity.Delivery;
import com.bestway.bls.entity.StorageBill;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.TableCellRenderers.TableCheckBoxRender;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * 车次管理主界面 checked by kcb 2009-1-10
 * 
 * @author kangbo
 * 
 */
public class DgBaseDelivery extends JDialogBase {
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JToolBar jJToolBarBar = null;
	private JButton btnEdit = null;
	private JButton btnSave = null;
	private JButton btnCancel = null;
	private JLabel jLabel = null;
	private JTextField tfDeliveryNo = null;
	private JToolBar jJToolBarBar1 = null;
	private JButton btnAddDetail = null;
	private JButton btnEditDetail = null;
	private JButton btnDeleteDetail = null;
	private JButton btnCancel2 = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JComboBox cbbOperType = null;
	private JLabel jLabel3 = null;
	private JTextField tfVehicleLicense = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JTextField tfCarrierCode = null;
	private JTextField tfSealNo2 = null;
	private JTextField tfCarrierName = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel7 = null;
	private JCalendarComboBox CbbschedularArrivalDate = null;
	private JLabel jLabel8 = null;
	private JCustomFormattedTextField tfBillCount = null;
	private JLabel jLabel9 = null;
	private JCustomFormattedTextField tfNetWeight = null;
	private JLabel jLabel10 = null;
	private JTextField tfContainerNo1 = null;
	private JLabel jLabel11 = null;
	private JLabel jLabel12 = null;
	private JCustomFormattedTextField tfGrossWeight = null;
	private JLabel jLabel13 = null;
	private JTextField tfContainerNo2 = null;
	private JTextField tfSealNo1 = null;
	private JLabel jLabel14 = null;
	private JLabel jLabel15 = null;
	private JLabel jLabel16 = null;
	private JCalendarComboBox cbbDeclareTime = null;
	private JLabel jLabel17 = null;
	private Delivery delivery = null; // @jve:decl-index=0:
	protected CustomBaseAction customBaseAction = null;
	private JTextField tfTradeCo = null;
	private JButton btnChose = null;
	private Brief brief = null;
	private JTextField jTextField1 = null;
	private StorageBillAction storageBillAction;
	private int dataState = DataState.ADD;
	private JTableListModel tableModel;
	private JTableListModel fathertableModel;
	private JButton btnEffect = null;
	private JButton btnReEffect = null;
	private JButton btnChoose1 = null;
	private JButton btnChoose3 = null;
	private JTextField tfDeclareSate = null;
	private JTextField jTextField3 = null;
	private JButton btnBorwserDetail = null;
	private JLabel jLabel18 = null;
	private JLabel jLabel19 = null;
	private JComboBox cbbIOFlag = null;
	private JButton btnQuery = null;
	private JPopupMenu pmChangeDeclareState = null;
	private JMenuItem miUndoDeclare = null;
	/**
	 * 保税物流核销表头
	 */
	private BlsCheckCancelAction blsCheckCancelAction = null;
	private JButton btnChangeDeclareState = null;

	public JTableListModel getFathertableModel() {
		return fathertableModel;
	}

	public void setFathertableModel(JTableListModel fathertableModel) {
		this.fathertableModel = fathertableModel;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgBaseDelivery() {
		super();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		storageBillAction = (StorageBillAction) CommonVars
				.getApplicationContext().getBean("storageBillAction");
		blsCheckCancelAction = (BlsCheckCancelAction) CommonVars
				.getApplicationContext().getBean("blsCheckCancelAction");
		initialize();
	}

	@Override
	public void setVisible(boolean f) {
		if (f) {
			showData();
			setState();
			List list = storageBillAction.findStorageBillForDelivery(
					new Request(CommonVars.getCurrUser()), delivery);
			initTable(list);
//			if (fathertableModel != null) {
//				delivery = (Delivery) fathertableModel.getCurrentRow();
//			}
			setState2();
			if (DgBaseDelivery.this instanceof DgImportDelivery) {
				cbbIOFlag.setSelectedIndex(0);
			} else if (DgBaseDelivery.this instanceof DgExportDelivery) {
				cbbIOFlag.setSelectedIndex(1);
			}
			initAuto();
			super.setVisible(f);
		}

	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(677, 470));
		this.setTitle("车次管理");
		this.setContentPane(getJTabbedPane());

	}

	/**
	 * 填充数据
	 */
	private void fillData() {
		if (this.dataState == DataState.ADD) {
			delivery = new Delivery();
			delivery.setDeclareState(DeclareState.APPLY_POR);
		}
		delivery.setDeliveryNo(this.tfDeliveryNo.getText());// 车次号
		delivery.setVehicleLicense(this.tfVehicleLicense.getText());// 车牌号
		delivery.setCarrierCode(this.tfCarrierCode.getText());
		delivery.setCarrierName(this.tfCarrierName.getText());

		delivery.setContainerNo1(this.tfContainerNo1.getText());
		delivery.setContainerNo2(this.tfContainerNo2.getText());
		delivery.setSealNo1(this.tfSealNo1.getText());
		delivery.setSealNo2(this.tfSealNo2.getText());
		// ------------------------------------------------------------
		delivery.setTradeCo(this.brief);
		delivery.setOperType(cbbOperType.getSelectedItem().toString());
		delivery.setSchedularArrivalDate(CbbschedularArrivalDate.getDate());
		delivery.setMessTimeStamp(cbbDeclareTime.getDate());
		delivery.setBillCount(Integer
				.parseInt((tfBillCount.getText() == null || tfBillCount
						.getText().trim().equals("")) ? "0" : tfBillCount
						.getText()));
		// delivery.setBillCount(Integer
		// .parseInt(this.tfBillCount.getText() == null ? "0"
		// : this.tfBillCount.getText()));
		if (this.tfBillCount.getValue() != null) {
			this.delivery.setBillCount(Integer.valueOf(this.tfBillCount
					.getValue().toString()));
		} else {
			this.delivery.setBillCount(null);
		}
		if (this.tfGrossWeight.getValue() != null) {
			this.delivery.setGrossWeight(Double.valueOf(this.tfGrossWeight
					.getValue().toString()));
		} else {
			this.delivery.setGrossWeight(null);
		}

		if (this.tfNetWeight.getValue() != null) {
			this.delivery.setNetWeight(Double.valueOf(this.tfNetWeight
					.getValue().toString()));
		} else {
			this.delivery.setNetWeight(null);
		}
		// delivery.setGrossWeight((Double.longBitsToDouble(((Long)
		// (tfGrossWeight
		// .getValue() == null ? 0 : tfGrossWeight.getValue())))));
		// delivery.setNetWeight((Double.longBitsToDouble(((Long) (tfNetWeight
		// .getValue() == null ? 0 : tfNetWeight.getValue())))));
		delivery.setPackNo(Integer.parseInt(jTextField1.getText()));

		if (cbbIOFlag.getSelectedItem() != null) {
			delivery.setInOut(((ItemProperty) cbbIOFlag.getSelectedItem())
					.getCode());
		}

	}

	/**
	 * 显示数据
	 */
	private void showData() {
		if (delivery == null) {
			delivery = new Delivery();
		}
		this.tfDeliveryNo.setText(delivery.getDeliveryNo());
		// 车次号
		this.tfVehicleLicense.setText(delivery.getVehicleLicense());
		// 车牌号
		this.tfCarrierCode.setText(delivery.getCarrierCode());
		this.tfCarrierName.setText(delivery.getCarrierName());
		// this.tfBillCount.setText(delivery.getBillCount() == null ? "0"
		// : delivery.getBillCount().toString());
		this.tfContainerNo1.setText(delivery.getContainerNo1());
		this.tfContainerNo2.setText(delivery.getContainerNo2());
		this.tfSealNo1.setText(delivery.getSealNo1());
		this.tfSealNo2.setText(delivery.getSealNo2());
		// ------------------------------------------------------------
		if (delivery.getTradeCo() == null) {
			Company com = this.blsCheckCancelAction.findCompany(new Request(
					CommonVars.getCurrUser()));
			if (com != null) {
				this.brief = this.blsCheckCancelAction.findBrief(new Request(
						CommonVars.getCurrUser()), com.getBuCode());
				if (this.brief != null) {
					this.tfTradeCo.setText(this.brief.getCode());
				}
			}

		} else {
			this.tfTradeCo.setText(delivery.getTradeCo() == null ? ""
					: delivery.getTradeCo().getCode());
			this.brief = delivery.getTradeCo();
		}
		cbbOperType.setSelectedItem(delivery.getOperType());
		CbbschedularArrivalDate.setDate(delivery.getSchedularArrivalDate());
		cbbDeclareTime.setDate(delivery.getMessTimeStamp());
		tfBillCount.setValue(delivery.getBillCount() == null ? null : delivery
				.getBillCount());
		tfGrossWeight.setValue(delivery.getGrossWeight() == null ? null
				: delivery.getGrossWeight());
		tfNetWeight.setValue(delivery.getNetWeight() == null ? null : delivery
				.getNetWeight());
		jTextField1.setText(delivery.getPackNo() == null ? "0" : delivery
				.getPackNo().toString());
		tfDeclareSate.setText(DeclareState.getDeclareStateSpec(delivery
				.getDeclareState() == null ? DeclareState.APPLY_POR : delivery
				.getDeclareState()));
		jTextField3.setText(ModifyMarkState.getModifyMarkSpec(delivery
				.getModifyMark() == null ? ModifyMarkState.UNCHANGE : delivery
				.getModifyMark().toString()));

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
			jTabbedPane.addTab("车次基本信息", null, getJPanel(), null);
			jTabbedPane.addTab("仓单信息", null, getJPanel1(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							initTable(new ArrayList());
							if (jTabbedPane.getSelectedIndex() == 1) {
								if (delivery.getId() == null) {
									JOptionPane.showMessageDialog(
											DgBaseDelivery.this, "请先保存表头！",
											"提示！", JOptionPane.WARNING_MESSAGE);
									jTabbedPane.setSelectedIndex(0);
									return;
								}
								List list = storageBillAction
										.findStorageBillForDelivery(
												new Request(CommonVars
														.getCurrUser()),
												delivery);
								initTable(list);
								setState2();
							} else if (jTabbedPane.getSelectedIndex() == 0) {
								List list = storageBillAction
										.findStorageBillForDelivery(
												new Request(CommonVars
														.getCurrUser()),
												delivery);
								initTable(list);
								setState2();
								initAuto();
							}
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * 当界面发声变化里，自动计算相关栏位
	 */
	private void initAuto() {
		if (delivery == null) {
			delivery = new Delivery();
		}
		if (delivery.getId() == null) {
			return;
		}
		Integer int1 = storageBillAction.findAmountpicesFromDelivery(
				new Request(CommonVars.getCurrUser()), delivery);
		Integer int2 = storageBillAction.findCountFromDelivery(new Request(
				CommonVars.getCurrUser()), delivery);
		Double netWeight = 0d;
		Double grossWeight = 0d;
		List<StorageBill> list = tableModel.getList();
		if (list != null && list.size() > 0) {
			for (StorageBill sb : list) {
				netWeight += CommonUtils.getDoubleExceptNull(sb.getNetWeight());
				grossWeight += CommonUtils.getDoubleExceptNull(sb
						.getGrossWeight());
			}
		}
		delivery.setNetWeight(netWeight);
		delivery.setGrossWeight(grossWeight);
		delivery.setBillCount(int2);
		delivery.setPackNo(int2);
		storageBillAction.saveOrUpdateObject(new Request(CommonVars
				.getCurrUser()), delivery);
		showData();
		// tfBillCount.setText(int2 == null ? "" : int2.toString());
		// jTextField1.setText(int1 == null ? "" : int1.toString());

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel19 = new JLabel();
			jLabel19.setBounds(new Rectangle(78, 326, 87, 22));
			jLabel19.setText("进出仓标志");
			jLabel18 = new JLabel();
			jLabel18.setBounds(new Rectangle(371, 326, 212, 20));
			jLabel18.setForeground(Color.blue);
			jLabel18.setText("注意：标注蓝色为必填项。");
			jLabel17 = new JLabel();
			jLabel17.setBounds(new Rectangle(371, 184, 88, 22));
			jLabel17.setText("申报状态");
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(372, 128, 88, 22));
			jLabel16.setText("报文发送时间");
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(372, 158, 88, 22));
			jLabel15.setText("修改标志");
			jLabel14 = new JLabel();
			jLabel14.setBounds(new Rectangle(77, 296, 88, 22));
			jLabel14.setText("第2关锁号");
			jLabel13 = new JLabel();
			jLabel13.setBounds(new Rectangle(77, 268, 88, 22));
			jLabel13.setText("第1关锁号");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(77, 239, 88, 22));
			jLabel12.setText("第2个集装箱号");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(77, 211, 88, 22));
			jLabel11.setText("第1个集装箱号");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(372, 296, 88, 22));
			jLabel10.setText("毛重");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(372, 268, 88, 22));
			jLabel9.setText("净重");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(372, 239, 88, 22));
			jLabel8.setText("该车载货物件数");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(77, 99, 88, 22));
			jLabel7.setText("预计到达日期");
			jLabel7.setForeground(Color.blue);
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(372, 211, 88, 22));
			jLabel6.setText("仓单数");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(77, 158, 88, 22));
			jLabel5.setText("承运单位名称");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(77, 128, 88, 22));
			jLabel4.setText("承运单位编码");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(76, 184, 88, 22));
			jLabel3.setText("车牌号");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(372, 99, 88, 22));
			jLabel2.setForeground(Color.blue);
			jLabel2.setText("业务类型");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(372, 71, 88, 22));
			jLabel1.setText("企业编码");
			jLabel1.setForeground(Color.blue);
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(77, 71, 88, 22));
			jLabel.setForeground(Color.blue);
			jLabel.setText("车次号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJJToolBarBar(), null);
			jPanel.add(jLabel, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJComboBox1(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(getJTextField2(), null);
			jPanel.add(getJTextField3(), null);
			jPanel.add(getJTextField4(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(jLabel7, null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(jLabel8, null);
			jPanel.add(getJTextField5(), null);
			jPanel.add(jLabel9, null);
			jPanel.add(getJTextField6(), null);
			jPanel.add(jLabel10, null);
			jPanel.add(getJTextField7(), null);
			jPanel.add(jLabel11, null);
			jPanel.add(jLabel12, null);
			jPanel.add(getJTextField8(), null);
			jPanel.add(jLabel13, null);
			jPanel.add(getJTextField9(), null);
			jPanel.add(getJTextField10(), null);
			jPanel.add(jLabel14, null);
			jPanel.add(jLabel15, null);
			jPanel.add(jLabel16, null);
			jPanel.add(getJCalendarComboBox1(), null);
			jPanel.add(jLabel17, null);
			jPanel.add(getJTextField12(), null);
			jPanel.add(getJButton8(), null);
			jPanel.add(getJTextField13(), null);
			jPanel.add(getJButton10(), null);
			jPanel.add(getJButton11(), null);
			jPanel.add(getJTextField22(), null);
			jPanel.add(getJTextField32(), null);
			jPanel.add(jLabel18, null);
			jPanel.add(jLabel19, null);
			jPanel.add(getJComboBox(), null);
		}
		return jPanel;
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
			jPanel1.add(getJJToolBarBar1(), BorderLayout.NORTH);
			jPanel1.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.setBounds(new Rectangle(1, 3, 685, 34));
			jJToolBarBar.add(getJButton1());
			jJToolBarBar.add(getJButton2());
			jJToolBarBar.add(getJButton());
			jJToolBarBar.add(getJButton9());
			jJToolBarBar.add(getBtnChangeDeclareState());
			jJToolBarBar.add(getJButton3());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!checkData()) {
						return;
					}
					fillData();
					String id = delivery.getId();
					delivery = (Delivery) storageBillAction.saveOrUpdateObject(
							new Request(CommonVars.getCurrUser()), delivery);
					if (id == null) {
						fathertableModel.addRow(delivery);
					} else {
						fathertableModel.updateRow(delivery);
					}
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}

	/**
	 * 设置状态
	 */
	private void setState() {
		String bs = delivery.getDeclareState() == null ? DeclareState.APPLY_POR
				: delivery.getDeclareState();
		boolean tr = (!bs.equals(DeclareState.PROCESS_EXE) && !bs
				.equals(DeclareState.WAIT_EAA));
		this.tfDeliveryNo.setEditable(tfDeliveryNo.getText() == null
				|| tfDeliveryNo.getText().equals(""));
		// 车次号
		this.tfVehicleLicense.setEditable(dataState != DataState.BROWSE);
		// 车牌号
		this.tfCarrierCode.setEditable(dataState != DataState.BROWSE);
		this.tfCarrierName.setEditable(dataState != DataState.BROWSE);
		this.tfContainerNo1.setEditable(dataState != DataState.BROWSE);
		this.tfContainerNo2.setEditable(dataState != DataState.BROWSE);
		this.tfSealNo1.setEditable(dataState != DataState.BROWSE);
		this.tfSealNo2.setEditable(dataState != DataState.BROWSE);
		btnChose.setEnabled(dataState != DataState.BROWSE);
		btnChoose1.setEnabled(dataState != DataState.BROWSE);
		btnChoose3.setEnabled(dataState != DataState.BROWSE);
		// ------------------------------------------------------------
		cbbOperType.setEditable(dataState != DataState.BROWSE);
		CbbschedularArrivalDate.setEnabled(dataState != DataState.BROWSE);
		// jCalendarComboBox1.setEnabled(dataState!=DataState.BROWSE);
		// tfBillCount.setEditable(dataState!=DataState.BROWSE);
		tfGrossWeight.setEditable(dataState != DataState.BROWSE);
		tfNetWeight.setEditable(dataState != DataState.BROWSE);
		btnEdit
				.setEnabled(dataState == DataState.BROWSE
						&& (delivery.getEffective() == null || !delivery
								.getEffective()) && tr);
		btnSave.setEnabled(dataState != DataState.BROWSE && tr);
		btnEffect.setEnabled((delivery.getEffective() == null || !delivery
				.getEffective())
				&& dataState == DataState.BROWSE && tr);// 生效

		btnReEffect.setEnabled(!(delivery.getEffective() == null || !delivery
				.getEffective())
				&& tr);// 回卷
		jTabbedPane.setEnabled(dataState != DataState.ADD);
		this.getMiUndoDeclare().setEnabled(
				DeclareState.WAIT_EAA.equals(delivery.getDeclareState()));
		// jTextField1.setEditable(dataState!=DataState.BROWSE);
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("关闭");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (tfDeliveryNo == null) {
			tfDeliveryNo = new JTextField();
			tfDeliveryNo.setBounds(new Rectangle(166, 71, 160, 22));
		}
		return tfDeliveryNo;
	}

	/**
	 * This method initializes jJToolBarBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar1() {
		if (jJToolBarBar1 == null) {
			jJToolBarBar1 = new JToolBar();
			jJToolBarBar1.add(getJButton4());
			jJToolBarBar1.add(getJButton12());
			jJToolBarBar1.add(getJButton5());
			jJToolBarBar1.add(getBtnQuery());
			jJToolBarBar1.add(getJButton6());
			jJToolBarBar1.add(getJButton7());
		}
		return jJToolBarBar1;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (btnAddDetail == null) {
			btnAddDetail = new JButton();
			btnAddDetail.setText("新增");
			btnAddDetail.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List addList = getStorageBill();
					if (addList == null) {
						return;
					} else {
						for (int i = 0; i < addList.size(); i++) {
							StorageBill sbill = (StorageBill) addList.get(i);
							sbill.setDelivery(delivery);
						}
						addList = storageBillAction.saveOrUpdateObjects(
								new Request(CommonVars.getCurrUser()), addList);
						tableModel.addRows(addList);
						setState2();
					}

				}
			});
		}
		return btnAddDetail;
	}

	/**
	 * 取得仓单信息
	 * 
	 * @return
	 */
	public List getStorageBill() {
		String inOut = "I";
		if (DgBaseDelivery.this instanceof DgExportDelivery) {
			inOut = "O";
		}
		List list = new Vector();
		list.add(new JTableListColumn("仓单号", "billNo", 70)); //  
		list.add(new JTableListColumn("仓库编码", "wareHouseCode.code", 100));
		list.add(new JTableListColumn("帐册编号", "emsNo", 100));
		list.add(new JTableListColumn("海关代码", "customsCode.name", 100));
		list.add(new JTableListColumn("仓单有效期", "validDate", 100));
		list.add(new JTableListColumn("仓单类型", "billType", 100));
		list.add(new JTableListColumn("进出仓标志", "ioFlag", 100));
		list.add(new JTableListColumn("订单号", "orderNo", 100));
		list.add(new JTableListColumn("计划编号", "planNo", 100));
		list.add(new JTableListColumn("总件数", "packCount", 100));
		list.add(new JTableListColumn("包装种类", "wrap.name", 100));
		list.add(new JTableListColumn("毛重", "grossWeight", 100));
		list.add(new JTableListColumn("净重", "netWeight", 180));
		list.add(new JTableListColumn("商品项数", "itemsCount", 150));
		list.add(new JTableListColumn("转出方名称", "outName", 80));
		list.add(new JTableListColumn("转入方名称", "inName", 80));
		DgCommonQuery.setTableColumns(list);
		List slist = storageBillAction.findStorageBillForDeliveryAdd(
				new Request(CommonVars.getCurrUser()), delivery, inOut);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(slist);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setTitle("请选择仓单！");
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton5() {
		if (btnEditDetail == null) {
			btnEditDetail = new JButton();
			btnEditDetail.setText("修改");
			btnEditDetail
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgBaseDelivery.this, "请选择要修改的数据！",
										"提示！", JOptionPane.WARNING_MESSAGE);
								return;
							}
							if (DgBaseDelivery.this instanceof DgExportDelivery) {
								DgExportStorage dg = new DgExportStorage();
								dg.setDataState(DataState.EDIT);
								dg.setFathertableModel(tableModel);
								dg.setStorageBill((StorageBill) tableModel
										.getCurrentRow());
								dg.setVisible(true);
								setState2();
							} else if (DgBaseDelivery.this instanceof DgImportDelivery) {
								DgImportStorage dg = new DgImportStorage();
								dg.setDataState(DataState.EDIT);
								dg.setFathertableModel(tableModel);
								dg.setStorageBill((StorageBill) tableModel
										.getCurrentRow());
								dg.setVisible(true);
								setState2();
							}
						}
					});
		}
		return btnEditDetail;
	}

	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton6() {
		if (btnDeleteDetail == null) {
			btnDeleteDetail = new JButton();
			btnDeleteDetail.setText("删除");
			btnDeleteDetail
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgBaseDelivery.this, "请选择要删除的数据！",
										"提示！", JOptionPane.WARNING_MESSAGE);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									DgBaseDelivery.this, "你确定要删除数据吗?", "提示！",
									JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
								return;
							}
							List storageBill = tableModel.getCurrentRows();
							for (int i = 0; i < storageBill.size(); i++) {
								StorageBill sbill = (StorageBill) storageBill
										.get(i);
								sbill.setDelivery(null);
							}
							storageBillAction.saveOrUpdateObjects(new Request(
									CommonVars.getCurrUser()), storageBill);
							tableModel.deleteRows(storageBill);
							setState2();
						}
					});
		}
		return btnDeleteDetail;
	}

	/**
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton7() {
		if (btnCancel2 == null) {
			btnCancel2 = new JButton();
			btnCancel2.setText("关闭");
			btnCancel2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel2;
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
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						if (tableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgBaseDelivery.this,
									"请选择要修改的数据！", "提示！",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						if (DgBaseDelivery.this instanceof DgExportDelivery) {
							DgExportStorage dg = new DgExportStorage();
							dg.setDataState(DataState.BROWSE);
							dg.setFathertableModel(tableModel);
							dg.setStorageBill((StorageBill) tableModel
									.getCurrentRow());
							dg.setVisible(true);
							setState2();
						} else if (DgBaseDelivery.this instanceof DgImportDelivery) {
							DgImportStorage dg = new DgImportStorage();
							dg.setDataState(DataState.BROWSE);
							dg.setFathertableModel(tableModel);
							dg.setStorageBill((StorageBill) tableModel
									.getCurrentRow());
							dg.setVisible(true);
							setState2();
						}
					} else if (e.getClickCount() == 1) {
						setState2();
					}
				}
			});
		}
		return jTable;
	}

	private void setState2() {
		if (tableModel.getCurrentRow() != null) {
			StorageBill dery = (StorageBill) tableModel.getCurrentRow();
			boolean ed = dery.getEffective() == null ? false : dery
					.getEffective();
			String bs = dery.getDelivery().getDeclareState() == null ? DeclareState.APPLY_POR
					: dery.getDelivery().getDeclareState();
			boolean tr = (!bs.equals(DeclareState.PROCESS_EXE) && !bs
					.equals(DeclareState.WAIT_EAA));
			btnEditDetail.setEnabled((!ed)
					&& (delivery.getEffective() == null || !delivery
							.getEffective()) && tr);
			btnDeleteDetail
					.setEnabled((delivery.getEffective() == null || !delivery
							.getEffective())
							&& tr);
			btnAddDetail
					.setEnabled((delivery.getEffective() == null || !delivery
							.getEffective())
							&& tr);
		}
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (cbbOperType == null) {
			cbbOperType = new JComboBox();
			cbbOperType.setBounds(new Rectangle(460, 99, 130, 22));
			cbbOperType.addItem("MnlEpz");
			cbbOperType.setEnabled(false);
		}
		return cbbOperType;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (tfVehicleLicense == null) {
			tfVehicleLicense = new JTextField();
			tfVehicleLicense.setBounds(new Rectangle(165, 184, 160, 22));
		}
		return tfVehicleLicense;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (tfCarrierCode == null) {
			tfCarrierCode = new JTextField();
			tfCarrierCode.setBounds(new Rectangle(166, 128, 139, 22));
		}
		return tfCarrierCode;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField3() {
		if (tfSealNo2 == null) {
			tfSealNo2 = new JTextField();
			tfSealNo2.setBounds(new Rectangle(166, 296, 160, 22));
		}
		return tfSealNo2;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField4() {
		if (tfCarrierName == null) {
			tfCarrierName = new JTextField();
			tfCarrierName.setBounds(new Rectangle(166, 158, 139, 22));
		}
		return tfCarrierName;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (CbbschedularArrivalDate == null) {
			CbbschedularArrivalDate = new JCalendarComboBox();
			CbbschedularArrivalDate.setDate(null);
			CbbschedularArrivalDate.setBounds(new Rectangle(166, 99, 160, 22));
		}
		return CbbschedularArrivalDate;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getJTextField5() {
		if (tfBillCount == null) {
			tfBillCount = new JCustomFormattedTextField();
			tfBillCount.setEditable(false);
			tfBillCount.setBounds(new Rectangle(460, 211, 130, 22));
			CustomFormattedTextFieldUtils.setFormatterFactory(tfBillCount, 4);
		}
		return tfBillCount;
	}

	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getJTextField6() {
		if (tfNetWeight == null) {
			tfNetWeight = new JCustomFormattedTextField();
			tfNetWeight.setBounds(new Rectangle(460, 268, 130, 22));
			CustomFormattedTextFieldUtils.setFormatterFactory(tfNetWeight, 4);
		}
		return tfNetWeight;
	}

	/**
	 * This method initializes jTextField7
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField7() {
		if (tfContainerNo1 == null) {
			tfContainerNo1 = new JTextField();
			tfContainerNo1.setBounds(new Rectangle(166, 211, 160, 22));
		}
		return tfContainerNo1;
	}

	/**
	 * This method initializes jTextField8
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getJTextField8() {
		if (tfGrossWeight == null) {
			tfGrossWeight = new JCustomFormattedTextField();
			tfGrossWeight.setBounds(new Rectangle(460, 296, 130, 22));
			CustomFormattedTextFieldUtils.setFormatterFactory(tfNetWeight, 4);
		}
		return tfGrossWeight;
	}

	/**
	 * This method initializes jTextField9
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField9() {
		if (tfContainerNo2 == null) {
			tfContainerNo2 = new JTextField();
			tfContainerNo2.setLocation(new Point(166, 239));
			tfContainerNo2.setSize(new Dimension(160, 22));
		}
		return tfContainerNo2;
	}

	/**
	 * This method initializes jTextField10
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField10() {
		if (tfSealNo1 == null) {
			tfSealNo1 = new JTextField();
			tfSealNo1.setBounds(new Rectangle(166, 268, 160, 22));
		}
		return tfSealNo1;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (cbbDeclareTime == null) {
			cbbDeclareTime = new JCalendarComboBox();
			cbbDeclareTime.setDate(null);
			cbbDeclareTime.setEnabled(false);
			cbbDeclareTime.setBounds(new Rectangle(460, 128, 130, 22));
		}
		return cbbDeclareTime;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField12() {
		if (tfTradeCo == null) {
			tfTradeCo = new JTextField();
			tfTradeCo.setBounds(new Rectangle(461, 71, 111, 22));
			tfTradeCo.setEditable(false);
		}
		return tfTradeCo;
	}

	/**
	 * This method initializes jButton8
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton8() {
		if (btnChose == null) {
			btnChose = new JButton();
			btnChose.setBounds(new Rectangle(571, 72, 19, 22));
			btnChose.setText("...");
			btnChose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					brief = (Brief) CommonQuery.getInstance().getCustomBrief(
							null);
					if (brief != null) {
						tfTradeCo.setText(brief.getCode());
					}
				}
			});
		}
		return btnChose;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField13() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setEditable(false);
			jTextField1.setBounds(new Rectangle(460, 239, 130, 22));
		}
		return jTextField1;
	}

	private boolean checkData() {
		String deliveryNo = this.tfDeliveryNo.getText();
		if (deliveryNo == null || deliveryNo.equals("")) {
			JOptionPane.showMessageDialog(DgBaseDelivery.this, "车次号必需填写！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (tfTradeCo.getText() == null
				|| tfTradeCo.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(DgBaseDelivery.this, "企业编码必需填写！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (cbbOperType.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgBaseDelivery.this, "业务类型必需填写！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (CbbschedularArrivalDate.getDate() == null) {
			JOptionPane.showMessageDialog(DgBaseDelivery.this, "预计到达日期必需填写！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (delivery.getId() == null) {
			List list = storageBillAction.findDelivery(new Request(CommonVars
					.getCurrUser()), deliveryNo);
			if (!list.isEmpty()) {
				JOptionPane.showMessageDialog(DgBaseDelivery.this,
						"车次号重复，请重新填写！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		if (tfBillCount.getText() != null
				&& !tfBillCount.getText().trim().equals("")) {
			try {
				Integer.parseInt(tfBillCount.getText().trim());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(DgBaseDelivery.this,
						"仓单数必需填写有效整数 ！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		if (jTextField1.getText() != null
				&& !jTextField1.getText().trim().equals("")) {
			try {
				Integer.parseInt(jTextField1.getText().trim());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(DgBaseDelivery.this,
						"该车载货件数必需填写有效整数 ！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		Double nw = 0.0;
		Double gw = 0.0;
		if (tfNetWeight.getText() != null
				&& !tfNetWeight.getText().trim().equals("")) {
			try {
				nw = Double.parseDouble(tfNetWeight.getText().trim());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(DgBaseDelivery.this,
						"净重必需填写有效数字 ！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}

		}
		if (tfGrossWeight.getText() != null
				&& !tfGrossWeight.getText().trim().equals("")) {
			try {
				gw = Double.parseDouble(tfGrossWeight.getText().trim());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(DgBaseDelivery.this,
						"毛重必需填写有效数字 ！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}

		if (nw - gw > 0) {
			JOptionPane.showMessageDialog(DgBaseDelivery.this, "毛重必需大于等于净重 ！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	// @jve:decl-index=0:visual-constraint="12,3"
	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("仓单号", "billNo", 100)); //  
						list.add(addColumn("仓库编码", "wareHouseCode.code", 100));
						list.add(addColumn("进出仓标志", "ioFlag", 80));
						list.add(addColumn("生效", "effective", 100));
						list.add(addColumn("车次号", "delivery.deliveryNo", 100));
						list
								.add(addColumn("申报状态", "delivery.declareState",
										100));
						list.add(addColumn("仓单类型", "billType", 100));
						list.add(addColumn("申报海关", "customsCode.name", 100));
						list.add(addColumn("仓单有效期", "validDate", 90));
						list.add(addColumn("订单号", "orderNo", 100));
						list.add(addColumn("计划编号", "planNo", 100));
						list.add(addColumn("总件数", "packCount", 100));
						list.add(addColumn("包装种类", "wrap.name", 100));
						list.add(addColumn("供货商", "supplierCd.name", 150));
						list.add(addColumn("供货方企业", "corrOwnerCode.name", 150));
						list.add(addColumn("毛重", "grossWeight", 80));
						list.add(addColumn("净重", "netWeight", 80));
						list.add(addColumn("手册号", "manualNo", 100));
						list.add(addColumn("帐册编号", "emsNo", 100));
						list.add(addColumn("商品项数", "itemsCount", 90));
						list.add(addColumn("进出口岸", "iePort.name", 100));
						list.add(addColumn("转出方名称", "outName", 150));
						list.add(addColumn("转入方名称", "inName", 150));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(7).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value == null || value.toString().trim().equals("")) {
							super.setText("");
						} else if (value.toString().trim().equals("00")) {
							super.setText("申报初始库存");
						} else if (value.toString().trim().equals("01")) {
							super.setText("后报关方式");
						} else if (value.toString().trim().equals("02")) {
							super.setText("先报关分批送货方式");
						} else if (value.toString().trim().equals("03")) {
							super.setText("特殊审核");
						} else {
							super.setText("");
						}
						return this;
					}

				});
		jTable.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value == null || value.toString().trim().equals("")) {
							super.setText("");
						} else if (value.toString().equals("I")) {
							super.setText("进仓");
						} else if (value.toString().equals("O")) {
							super.setText("出仓");
						} else {
							super.setText("");
						}
						return this;
					}

				});
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super
								.setText((value == null) ? castValue(DeclareState.APPLY_POR)
										: castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return DeclareState.getDeclareStateSpec(value
								.toString());
					}
				});
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnEffect == null) {
			btnEffect = new JButton();
			btnEffect.setText("生效");
			btnEffect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (dataState != DataState.BROWSE) {
						JOptionPane.showMessageDialog(DgBaseDelivery.this,
								"请先保存数据！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					List list = tableModel.getList();
					if (list == null || list.isEmpty()) {
						JOptionPane.showMessageDialog(DgBaseDelivery.this,
								"仓单信息为空，不能第生效！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					String warning = "";
					for (int i = 0; i < list.size(); i++) {
						StorageBill sbill = (StorageBill) list.get(i);
						if (sbill.getEffective() == null
								|| !sbill.getEffective()) {
							warning += ((sbill.getBillNo() == null ? "" : sbill
									.getBillNo()) + ";");
						}
					}
					if (!warning.equals("")) {
						JOptionPane.showMessageDialog(DgBaseDelivery.this,
								"表体以下仓单没有生效，所以该车次不能生效！\n仓单号如下：" + warning,
								"提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					delivery.setEffective(true);
					delivery = (Delivery) storageBillAction.saveOrUpdateObject(
							new Request(CommonVars.getCurrUser()), delivery);
					fathertableModel.updateRow(delivery);
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnEffect;
	}

	/**
	 * This method initializes jButton9
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton9() {
		if (btnReEffect == null) {
			btnReEffect = new JButton();
			btnReEffect.setText("回卷");
			btnReEffect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (dataState != DataState.BROWSE) {
						JOptionPane.showMessageDialog(DgBaseDelivery.this,
								"请先保存数据！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (delivery.getId() != null) {
						delivery = storageBillAction.findDeliveryByID(
								new Request(CommonVars.getCurrUser()), delivery
										.getId());
					}
					if ((delivery.getEffective() == null || !delivery
							.getEffective())
							|| delivery.getDeclareState().equals(
									DeclareState.WAIT_EAA)) {
						fathertableModel.updateRow(delivery);
						setState();
						return;
					}
					delivery.setEffective(false);
					delivery = (Delivery) storageBillAction.saveOrUpdateObject(
							new Request(CommonVars.getCurrUser()), delivery);
					fathertableModel.updateRow(delivery);
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnReEffect;
	}

	/**
	 * This method initializes jButton10
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton10() {
		if (btnChoose1 == null) {
			btnChoose1 = new JButton();
			btnChoose1.setBounds(new Rectangle(304, 128, 22, 22));
			btnChoose1.setText("...");
			btnChoose1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Brief brief = (Brief) CommonQuery.getInstance()
							.getCustomBrief(null);
					if (brief != null) {
						tfCarrierCode.setText(brief.getCode());
						tfCarrierName.setText(brief.getName());
					}
				}
			});
		}
		return btnChoose1;
	}

	/**
	 * This method initializes jButton11
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton11() {
		if (btnChoose3 == null) {
			btnChoose3 = new JButton();
			btnChoose3.setBounds(new Rectangle(304, 158, 22, 22));
			btnChoose3.setText("...");
			btnChoose3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Brief brief = (Brief) CommonQuery.getInstance()
							.getCustomBrief(null);
					if (brief != null) {
						tfCarrierCode.setText(brief.getCode());
						tfCarrierName.setText(brief.getName());
					}

				}
			});
		}
		return btnChoose3;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField22() {
		if (tfDeclareSate == null) {
			tfDeclareSate = new JTextField();
			tfDeclareSate.setBounds(new Rectangle(459, 184, 130, 22));
			tfDeclareSate.setEditable(false);
		}
		return tfDeclareSate;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField32() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(new Rectangle(460, 158, 130, 22));
			jTextField3.setEditable(false);
		}
		return jTextField3;
	}

	/**
	 * This method initializes jButton12
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton12() {
		if (btnBorwserDetail == null) {
			btnBorwserDetail = new JButton();
			btnBorwserDetail.setText("浏览");
			btnBorwserDetail
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgBaseDelivery.this, "请选择要查看的数据！",
										"提示！", JOptionPane.WARNING_MESSAGE);
								return;
							}
							if (DgBaseDelivery.this instanceof DgExportDelivery) {
								DgExportStorage dg = new DgExportStorage();
								dg.setDataState(DataState.BROWSE);
								dg.setFathertableModel(tableModel);
								dg.setStorageBill((StorageBill) tableModel
										.getCurrentRow());
								dg.setVisible(true);
								setState2();
							} else if (DgBaseDelivery.this instanceof DgImportDelivery) {
								DgImportStorage dg = new DgImportStorage();
								dg.setDataState(DataState.BROWSE);
								dg.setFathertableModel(tableModel);
								dg.setStorageBill((StorageBill) tableModel
										.getCurrentRow());
								dg.setVisible(true);
								setState2();
							}
						}
					});
		}
		return btnBorwserDetail;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (cbbIOFlag == null) {
			cbbIOFlag = new JComboBox();
			cbbIOFlag.setBounds(new Rectangle(167, 326, 158, 22));
			cbbIOFlag.setEnabled(false);
			cbbIOFlag.addItem(new ItemProperty("I", "进仓"));
			cbbIOFlag.addItem(new ItemProperty("O", "出仓"));
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbIOFlag,
					"code", "name", 100);
			this.cbbIOFlag.setRenderer(CustomBaseRender.getInstance()
					.getRender("code", "name", 80, 100));
		}
		return cbbIOFlag;
	}

	/**
	 * 填入查询条件
	 */
	public void fillQueryData() {
		List<StorageBill> list = tableModel.getCurrentRows();
		List dataSource = new ArrayList();
		String tradeCo = tfTradeCo.getText();
		if ("".equals(tradeCo.trim())) {
			JOptionPane.showMessageDialog(DgBaseDelivery.this, "仓单号为空！",
					"提示信息", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		for (StorageBill storageBill : list) {
			dataSource.addAll(storageBillAction.findStorageBillStatusFromHP(
					new Request(CommonVars.getCurrUser()), tradeCo, storageBill
							.getBillNo()));
		}
		if (list.size() > 0) {
			DgQueryResult dg = new DgQueryResult();
			dg.setSize(new Dimension(680, 454));
			dg.setDataSource(dataSource);
			dg.setVisibles(true);
		}
	}

	/**
	 * This method initializes btnQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询仓单状态");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgBaseDelivery.this,
								"无仓单！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					fillQueryData();
				}
			});
		}
		return btnQuery;
	}

	/**
	 * This method initializes btnChangeDeclareState
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeDeclareState() {
		if (btnChangeDeclareState == null) {
			btnChangeDeclareState = new JButton();
			btnChangeDeclareState.setText("改变申报状态");
			btnChangeDeclareState
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgAllChangState dg = new DgAllChangState();
							dg.setVisible(true);
							if (!dg.isOk()) {
								return;
							}
							if (dg.isCheckBoxState()) {
								Component comp = (Component) e.getSource();
								getPmChangeDeclareState().show(comp, 0,
										comp.getHeight());
							}
						}
					});
		}
		return btnChangeDeclareState;
	}

	/**
	 * This method initializes pmChangeDeclareState
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmChangeDeclareState() {
		if (pmChangeDeclareState == null) {
			pmChangeDeclareState = new JPopupMenu();
			pmChangeDeclareState.add(getMiUndoDeclare());
		}
		return pmChangeDeclareState;
	}

	/**
	 * This method initializes miUndoDeclare
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiUndoDeclare() {
		if (miUndoDeclare == null) {
			miUndoDeclare = new JMenuItem();
			miUndoDeclare.setText("取消申报");
			miUndoDeclare
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							String declareState = "";
							if (delivery.getTradeCo() != null
									&& !"".equals(delivery.getTradeCo())) {
								declareState = DeclareState.CHANGING_EXE;
							} else {
								declareState = DeclareState.APPLY_POR;
							}
							delivery = storageBillAction
									.changeDeliveryDeclareState(new Request(
											CommonVars.getCurrUser()),
											delivery, declareState);
							setState();
							if (fathertableModel != null) {
								fathertableModel.updateRow(delivery);
							}

						}
					});
		}
		return miUndoDeclare;
	}
} // @jve:decl-index=0:visual-constraint="12,3"
