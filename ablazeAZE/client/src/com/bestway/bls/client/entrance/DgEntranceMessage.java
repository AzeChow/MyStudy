package com.bestway.bls.client.entrance;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import bsh.This;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bls.action.EntranceMessageAction;
import com.bestway.bls.client.storagebill.DgBaseStorage;
import com.bestway.bls.entity.EntranceMessage;
import com.bestway.bls.entity.BlsInOutFlag;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 货到报告编辑窗体
 * 
 * @author hw
 * 
 */
public class DgEntranceMessage extends JDialogBase {

	private JPanel jPanel = null;

	private JTableListModel tableModel = null;

	private JLabel jLabel = null;

	private JTextField tfTradeCo = null;

	private JTextField tfOperType = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JTextField tfDeliveryNo = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JTextField tfVehicleLicense = null;

	private JLabel jLabel5 = null;

	private JTextField tfCarrierCode = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JTextField tfCarrierName = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JTextField tfContainerNo1 = null;

	private JLabel jLabel12 = null;

	private JTextField tfContainerNo2 = null;

	private JLabel jLabel13 = null;

	private JTextField tfDeclareState = null;
	/**
	 * 状态控制参数
	 */
	private int dataState = DataState.ADD;

	/**
	 * 得到状态控制参数
	 * 
	 * @return 状态控制参数
	 */
	public int getDataState() {
		return dataState;
	}

	/**
	 * 设置状态控制参数
	 * 
	 * @param dataState
	 *            状态控制参数
	 */
	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	private JPanel jPanel1 = null;

	private JToolBar jToolBar = null;

	private JButton btnModify = null;
	/**
	 * 企业编码map
	 */
	private HashMap briefMap = new HashMap(); // @jve:decl-index=0:

	private JButton btnSave = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JCustomFormattedTextField tfGrossWeight = null;

	private JCustomFormattedTextField tfNetWeight = null;
	/**
	 * 货到报告实体类对象
	 */
	private EntranceMessage entranceMessage; // @jve:decl-index=0:

	private JComboBox cbbIOFlag = null;

	private JCustomFormattedTextField tfGrossWeight1 = null;

	private JCustomFormattedTextField tfBillCount = null;

	/**
	 * 获取货到报告实体类实例
	 * 
	 * @return
	 */
	public EntranceMessage getEntranceMessage() {
		return entranceMessage;
	}

	/**
	 * 设置货到报告实体类实例
	 * 
	 * @param entranceMessage
	 */
	public void setEntranceMessage(EntranceMessage entranceMessage) {
		this.entranceMessage = entranceMessage;
	}

	/**
	 * 货到报告接口
	 */
	EntranceMessageAction entranceMessageAction = (EntranceMessageAction) CommonVars
			.getApplicationContext().getBean("entranceMessageAction"); // @jve:decl-index=0:

	private JLabel jLabel14 = null;

	private JTextField tfSealNo1 = null;

	private JLabel jLabel15 = null;

	private JTextField tfSealNo2 = null;

	private JButton btnExit = null;

	/**
	 * 货到报告构造方法 This method initializes
	 * 
	 */
	public DgEntranceMessage() {
		super();
		initialize();

		this.cbbIOFlag.removeAllItems();
		this.cbbIOFlag.addItem(new ItemProperty(BlsInOutFlag.IN, "进口"));
		this.cbbIOFlag.addItem(new ItemProperty(BlsInOutFlag.OUT, "出口"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbIOFlag);
		this.cbbIOFlag.setRenderer(CustomBaseRender.getInstance().getRender());
		this.cbbIOFlag.setSelectedIndex(-1);

	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(561, 424));
		this.setContentPane(getJPanel1());
		this.setTitle("货到报告编辑");
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(34, 277, 56, 18));
			jLabel15.setText("第1关锁号");
			jLabel14 = new JLabel();
			jLabel14.setBounds(new Rectangle(284, 277, 55, 18));
			jLabel14.setText("第2关锁号");
			jLabel13 = new JLabel();
			jLabel13.setBounds(new Rectangle(34, 318, 51, 18));
			jLabel13.setText("申报状态");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(284, 238, 81, 17));
			jLabel12.setText("第2个集装箱号");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(34, 238, 81, 18));
			jLabel11.setText("第1个集装箱号");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(284, 198, 47, 18));
			jLabel10.setText("毛重");
			jLabel10.setForeground(Color.blue);
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(34, 198, 47, 18));
			jLabel9.setText("净重");
			jLabel9.setForeground(Color.blue);
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(284, 156, 61, 17));
			jLabel8.setText("仓单数");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(34, 156, 61, 18));
			jLabel7.setText("承运商名称");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(49, 152, 0, 0));
			jLabel6.setText("JLabel");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(284, 112, 61, 17));
			jLabel5.setText("承运商代码");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(34, 112, 47, 18));
			jLabel4.setText("车牌号");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(284, 70, 63, 18));
			jLabel3.setForeground(Color.blue);
			jLabel3.setText("进出口标志");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(34, 69, 36, 18));
			jLabel2.setForeground(Color.blue);
			jLabel2.setText("车次");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(286, 26, 50, 18));
			jLabel1.setForeground(Color.blue);
			jLabel1.setText("业务类型");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(34, 26, 50, 18));
			jLabel.setForeground(Color.blue);
			jLabel.setText("企业编码");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getTfTradeCo(), null);
			jPanel.add(getTfOperType(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getTfDeliveryNo(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(getTfVehicleLicense(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getTfCarrierCode(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(jLabel7, null);
			jPanel.add(getTfCarrierName(), null);
			jPanel.add(jLabel8, null);
			jPanel.add(jLabel9, null);
			jPanel.add(jLabel10, null);
			jPanel.add(jLabel11, null);
			jPanel.add(getTfContainerNo1(), null);
			jPanel.add(jLabel12, null);
			jPanel.add(getTfContainerNo2(), null);
			jPanel.add(jLabel13, null);
			jPanel.add(getTfDeclareState(), null);
			jPanel.add(getTfGrossWeight(), null);
			jPanel.add(getTfNetWeight(), null);
			jPanel.add(getCbbIOFlag(), null);
			jPanel.add(getTfBillCount(), null);
			jPanel.add(jLabel14, null);
			jPanel.add(getTfSealNo1(), null);
			jPanel.add(jLabel15, null);
			jPanel.add(getTfSealNo2(), null);
		}
		return jPanel;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * This method initializes tfTradeCo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTradeCo() {
		if (tfTradeCo == null) {
			tfTradeCo = new JTextField();
			tfTradeCo.setBounds(new Rectangle(116, 24, 135, 22));
			tfTradeCo.setEnabled(false);
		}
		return tfTradeCo;
	}

	/**
	 * This method initializes tfOperType
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOperType() {
		if (tfOperType == null) {
			tfOperType = new JTextField();
			tfOperType.setBounds(new Rectangle(360, 24, 135, 22));
			tfOperType.setEnabled(false);
		}
		return tfOperType;
	}

	/**
	 * This method initializes tfDeliveryNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDeliveryNo() {
		if (tfDeliveryNo == null) {
			tfDeliveryNo = new JTextField();
			tfDeliveryNo.setBounds(new Rectangle(116, 68, 135, 22));
			tfDeliveryNo.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}

				public void focusLost(FocusEvent e) {
				}
			});
		}
		return tfDeliveryNo;
	}

	/**
	 * This method initializes tfVehicleLicense
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfVehicleLicense() {
		if (tfVehicleLicense == null) {
			tfVehicleLicense = new JTextField();
			tfVehicleLicense.setBounds(new Rectangle(116, 111, 135, 22));
		}
		return tfVehicleLicense;
	}

	/**
	 * This method initializes tfCarrierCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCarrierCode() {
		if (tfCarrierCode == null) {
			tfCarrierCode = new JTextField();
			tfCarrierCode.setBounds(new Rectangle(360, 111, 135, 21));
			tfCarrierCode.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}

				public void focusLost(FocusEvent e) {
				}
			});
		}
		return tfCarrierCode;
	}

	/**
	 * This method initializes tfCarrierName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCarrierName() {
		if (tfCarrierName == null) {
			tfCarrierName = new JTextField();
			tfCarrierName.setBounds(new Rectangle(116, 154, 135, 22));
		}
		return tfCarrierName;
	}

	/**
	 * This method initializes tfContainerNo1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContainerNo1() {
		if (tfContainerNo1 == null) {
			tfContainerNo1 = new JTextField();
			tfContainerNo1.setBounds(new Rectangle(116, 237, 135, 22));
		}
		return tfContainerNo1;
	}

	/**
	 * This method initializes tfContainerNo2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContainerNo2() {
		if (tfContainerNo2 == null) {
			tfContainerNo2 = new JTextField();
			tfContainerNo2.setBounds(new Rectangle(360, 235, 135, 22));
		}
		return tfContainerNo2;
	}

	/**
	 * This method initializes tfDeclareState
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDeclareState() {
		if (tfDeclareState == null) {
			tfDeclareState = new JTextField();
			tfDeclareState.setBounds(new Rectangle(116, 318, 135, 22));
		}
		return tfDeclareState;
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
			jPanel1.add(getJToolBar(), BorderLayout.NORTH);
			jPanel1.add(getJPanel(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnModify());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnExit());
			jToolBar.add(getBtnPrevious());
			jToolBar.add(getBtnNext());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnModify
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnModify() {
		if (btnModify == null) {
			btnModify = new JButton();
			btnModify.setText("修改");
			btnModify.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
					// btnModify.setEnabled(false);
					// btnSave.setEnabled(true);
				}
			});
		}
		return btnModify;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!checkData()) {
						return;
					}
					fillData();
					entranceMessage = (EntranceMessage) tableModel
							.getCurrentRow();
					entranceMessage = entranceMessageAction
							.saveEntranceMessage(new Request(CommonVars
									.getCurrUser()), entranceMessage);
					tableModel.updateRow(entranceMessage);
					dataState = DataState.BROWSE;
					setState();
					// btnModify.setEnabled(true);
					// btnSave.setEnabled(false);
				}
			});
		}
		return btnSave;
	}

	/**
	 * 重新保存填入的数据
	 */
	public void fillData() {
		List briefList = entranceMessageAction.findBrief(new Request(CommonVars
				.getCurrUser()));
		for (int i = 0; i < briefList.size(); i++) {
			Brief brief = (Brief) briefList.get(i);
			briefMap.put(brief.getCode(), brief);
		}

		if (tfBillCount.getValue() != null) {
			entranceMessage.setBillCount(Integer.valueOf(tfBillCount.getValue()
					.toString()));
		} else {
			entranceMessage.setBillCount(null);
		}
		if (tfGrossWeight.getValue() != null) {
			entranceMessage.setGrossWeight(Double.valueOf(tfGrossWeight
					.getValue().toString()));
		} else {
			entranceMessage.setGrossWeight(null);
		}
		if (this.tfNetWeight.getValue() != null) {
			entranceMessage.setNetWeight(Double.valueOf(tfNetWeight.getValue()
					.toString()));
		} else {
			entranceMessage.setNetWeight(null);
		}
		ItemProperty item = (ItemProperty) cbbIOFlag.getSelectedItem();
		entranceMessage.setIoFlag(item.getCode());
		entranceMessage.setCarrierCode(this.tfCarrierCode.getText());
		entranceMessage.setCarrierName(this.tfCarrierName.getText());
		entranceMessage.setContainerNo1(this.tfContainerNo1.getText());
		entranceMessage.setContainerNo2(this.tfContainerNo2.getText());
		entranceMessage.setVehicleLicense(this.tfVehicleLicense.getText());
		entranceMessage.setSealNo1(this.tfSealNo1.getText());
		entranceMessage.setSealNo2(this.tfSealNo2.getText());
		Brief brief = (Brief) briefMap.get(tfTradeCo.getText());

		entranceMessage.setTradeCo(brief);
	}

	/**
	 * 显示数据
	 */
	private void showData() {
		if (entranceMessage == null) {
			entranceMessage = new EntranceMessage();
		}
		// if (entranceMessage.getTradeCo().getCode() != null) {
		// tfTradeCo.setText(entranceMessage.getTradeCo().getCode());
		// } else {
		// tfTradeCo.setText("");
		// }
		tfTradeCo.setText(entranceMessage.getTradeCo() == null ? ""
				: entranceMessage.getTradeCo().getCode());
		if (entranceMessage.getOperType() != null) {
			tfOperType.setText(entranceMessage.getOperType());
		} else {
			tfOperType.setText("");
		}
		if (entranceMessage.getDeliveryNo() != null) {
			tfDeliveryNo.setText(entranceMessage.getDeliveryNo());
		} else {
			tfDeliveryNo.setText("");
		}
		if (entranceMessage.getIoFlag() != null) {
			cbbIOFlag.setSelectedIndex(ItemProperty.getIndexByCode(
					this.entranceMessage.getIoFlag().toString(), cbbIOFlag));
		} else {
			cbbIOFlag.setSelectedIndex(-1);
		}
		tfVehicleLicense.setText(entranceMessage.getVehicleLicense());
		tfCarrierCode.setText(entranceMessage.getCarrierCode());
		tfCarrierName.setText(entranceMessage.getCarrierName());
//		tfBillCount.setValue(String.valueOf(entranceMessage.getBillCount()));
		if (entranceMessage.getBillCount() != null) {
			this.tfBillCount.setValue(this.entranceMessage.getBillCount());
		} else {
			this.tfBillCount.setValue(null);
		}
		if (entranceMessage.getNetWeight() != null) {
			this.tfNetWeight.setValue(this.entranceMessage.getNetWeight());
		} else {
			this.tfNetWeight.setText("");
		}
		if (entranceMessage.getGrossWeight() != null) {
			this.tfGrossWeight.setValue(this.entranceMessage.getGrossWeight());
		} else {
			this.tfGrossWeight.setText("");
		}
		tfContainerNo1.setText(entranceMessage.getContainerNo1());
		tfContainerNo2.setText(entranceMessage.getContainerNo2());
		// tfDeclareState
		// .setText(entranceMessage.getDeclareState() == null ?
		// DeclareState.APPLY_POR
		// : entranceMessage.getDeclareState());
		tfDeclareState.setText(DeclareState.getDeclareStateSpec(entranceMessage
				.getDeclareState() == null ? DeclareState.APPLY_POR
				: entranceMessage.getDeclareState()));
		this.tfSealNo1.setText(entranceMessage.getSealNo1());
		this.tfSealNo2.setText(entranceMessage.getSealNo2());
	}

	/**
	 * 设置组建状态
	 */
	private void setState() {
		// 车次号
		// this.tfTradeCo.setEnabled(dataState != DataState.BROWSE);
		// 车牌号
		// this.tfOperType.setEnabled(dataState != DataState.BROWSE);
		// this.tfTradeCo.setEnabled(dataState != DataState.BROWSE);
		this.tfDeliveryNo.setEnabled(dataState != DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(entranceMessage
						.getDeclareState()));
		this.cbbIOFlag.setEnabled(dataState != DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(entranceMessage
						.getDeclareState()));
		this.tfVehicleLicense.setEnabled(dataState != DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(entranceMessage
						.getDeclareState()));
		this.tfCarrierCode.setEnabled(dataState != DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(entranceMessage
						.getDeclareState()));
		this.tfCarrierName.setEnabled(dataState != DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(entranceMessage
						.getDeclareState()));
		this.tfBillCount.setEnabled(dataState != DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(entranceMessage
						.getDeclareState()));
		this.tfNetWeight.setEnabled(dataState != DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(entranceMessage
						.getDeclareState()));
		this.tfGrossWeight.setEnabled(dataState != DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(entranceMessage
						.getDeclareState()));
		this.tfContainerNo1.setEnabled(dataState != DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(entranceMessage
						.getDeclareState()));
		this.tfContainerNo2.setEnabled(dataState != DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(entranceMessage
						.getDeclareState()));
		this.tfDeclareState.setEnabled(dataState != DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(entranceMessage
						.getDeclareState()));
		this.tfSealNo1.setEnabled(dataState != DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(entranceMessage
						.getDeclareState()));
		this.tfSealNo2.setEnabled(dataState != DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(entranceMessage
						.getDeclareState()));

		this.btnModify.setEnabled((entranceMessage.getDeclareState()
				.equals(DeclareState.APPLY_POR))
				&& dataState != DataState.EDIT);

		this.btnSave.setEnabled((entranceMessage.getDeclareState()
				.equals(DeclareState.APPLY_POR))
				&& dataState == DataState.EDIT);

		if (btnModify.isEnabled()) {
			this.btnSave.setEnabled(false);
		}
		if (btnSave.isEnabled()) {
			this.btnModify.setEnabled(false);
		}
		// System.out.println("entranceMessage.getDeclareState()="+entranceMessage.getDeclareState());
	}

	/**
	 * 检查数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		if (cbbIOFlag.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgEntranceMessage.this, "进出口标志必须选择！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		String deliveryNo = tfDeliveryNo.getText();
		if (deliveryNo == null || deliveryNo.equals("")) {
			JOptionPane.showMessageDialog(DgEntranceMessage.this, "车次号必填！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		Double nw = 0.0;
		Double gw = 0.0;
		if (tfNetWeight.getText() != null
				&& !tfNetWeight.getText().trim().equals("")) {
			try {
				nw = Double.parseDouble(tfNetWeight.getText().trim());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(DgEntranceMessage.this,
						"净重必需填写有效数字 ！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(DgEntranceMessage.this, "净重必需填写！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (tfGrossWeight.getText() != null
				&& !tfGrossWeight.getText().trim().equals("")) {
			try {
				gw = Double.parseDouble(tfGrossWeight.getText().trim());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(DgEntranceMessage.this,
						"毛重必需填写有效数字 ！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(DgEntranceMessage.this, "毛重必需填写！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (nw - gw > 0) {
			JOptionPane.showMessageDialog(DgEntranceMessage.this,
					"毛重必需大于等于净重 ！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		String s = this.tfOperType.getText();
		if (s == null || s.equals("")) {
			JOptionPane.showMessageDialog(DgEntranceMessage.this, "业务类型必填！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		// if (tfOperType.getText() == null) {
		// JOptionPane.showMessageDialog(DgEntranceMessage.this, "业务类型填！",
		// "提示！", JOptionPane.WARNING_MESSAGE);
		// return false;
		// }
		return true;
	}

	@Override
	public void setVisible(boolean f) {
		if (f) {
			// if(dataState == DataState.BROWSE )
			// {
			// btnModify.setEnabled(true);
			// btnSave.setEnabled(false);
			// }
			// else
			// {
			// btnModify.setEnabled(false);
			// }
			showData();
			setState();
			super.setVisible(f);
		}
	}

	/**
	 * This method initializes btnPrevious
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setText("上一笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.previousRow()) {
						btnPrevious.setEnabled(false);
						btnNext.setEnabled(true);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					entranceMessage = (EntranceMessage) tableModel
							.getCurrentRow();
					entranceMessage = (EntranceMessage) entranceMessageAction
							.load(EntranceMessage.class, entranceMessage
									.getId());
					tableModel.updateRow(entranceMessage);
					showData();
				}
			});
		}
		return btnPrevious;
	}

	/**
	 * This method initializes btnNext
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setText("下一笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.nextRow()) {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(false);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					entranceMessage = (EntranceMessage) tableModel
							.getCurrentRow();
					entranceMessage = (EntranceMessage) entranceMessageAction
							.load(EntranceMessage.class, entranceMessage
									.getId());
					tableModel.updateRow(entranceMessage);
					showData();
				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes tfGrossWeight
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfGrossWeight() {
		if (tfGrossWeight == null) {
			// DefaultFormatterFactory defaultFormatterFactory = new
			// DefaultFormatterFactory();
			// defaultFormatterFactory.setEditFormatter(new NumberFormatter());
			// defaultFormatterFactory.setDisplayFormatter(new
			// NumberFormatter());
			tfGrossWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfGrossWeight.setBounds(new Rectangle(360, 195, 135, 22));
			CustomFormattedTextFieldUtils.setFormatterFactory(tfGrossWeight, 4);
		}
		return tfGrossWeight;
	}

	/**
	 * This method initializes tfNetWeight
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfNetWeight() {
		if (tfNetWeight == null) {
			// DefaultFormatterFactory defaultFormatterFactory1 = new
			// DefaultFormatterFactory();
			// defaultFormatterFactory1.setEditFormatter(new NumberFormatter());
			// defaultFormatterFactory1.setDisplayFormatter(new
			// NumberFormatter());
			tfNetWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfNetWeight.setBounds(new Rectangle(116, 195, 135, 22));
			CustomFormattedTextFieldUtils.setFormatterFactory(tfNetWeight, 4);
		}
		return tfNetWeight;
	}

	/**
	 * This method initializes cbbIOFlag
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIOFlag() {
		if (cbbIOFlag == null) {
			cbbIOFlag = new JComboBox();
			cbbIOFlag.setBounds(new Rectangle(360, 68, 135, 22));
		}
		return cbbIOFlag;
	}

	/**
	 * This method initializes tfGrossWeight1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfGrossWeight1() {
		if (tfGrossWeight1 == null) {
			DefaultFormatterFactory defaultFormatterFactory2 = new DefaultFormatterFactory();
			defaultFormatterFactory2.setEditFormatter(new NumberFormatter());
			defaultFormatterFactory2.setDisplayFormatter(new NumberFormatter());
			tfGrossWeight1 = new JCustomFormattedTextField();
			tfGrossWeight1.setBounds(new Rectangle(328, 160, 0, 0));
		}
		return tfGrossWeight1;
	}

	/**
	 * This method initializes tfBillCount
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfBillCount() {
		if (tfBillCount == null) {
			// DefaultFormatterFactory defaultFormatterFactory21 = new
			// DefaultFormatterFactory();
			// defaultFormatterFactory21.setEditFormatter(new
			// NumberFormatter());
			// defaultFormatterFactory21
			// .setDisplayFormatter(new NumberFormatter());
			tfBillCount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfBillCount.setBounds(new Rectangle(360, 154, 135, 21));
			CustomFormattedTextFieldUtils.setFormatterFactory(tfBillCount, 4);
		}
		return tfBillCount;
	}

	/**
	 * This method initializes tfSealNo1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSealNo1() {
		if (tfSealNo1 == null) {
			tfSealNo1 = new JTextField();
			tfSealNo1.setBounds(new Rectangle(116, 278, 135, 22));
		}
		return tfSealNo1;
	}

	/**
	 * This method initializes tfSealNo2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSealNo2() {
		if (tfSealNo2 == null) {
			tfSealNo2 = new JTextField();
			tfSealNo2.setBounds(new Rectangle(360, 276, 135, 22));
		}
		return tfSealNo2;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
