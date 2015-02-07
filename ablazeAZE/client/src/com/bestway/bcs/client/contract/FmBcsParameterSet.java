/*
 * Created on 2004-8-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contract;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.message.action.BcsMessageAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.system.action.ContractSystemAction;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.ParameterType;
import com.bestway.cspcard.entity.ICCardInfo;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.FlowLayout;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmBcsParameterSet extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JButton btnSave = null;

	private JButton btnClose = null;

	private ContractSystemAction contractSystemAction = null;

	private JButton btnEdit = null;

	private int dataState = DataState.BROWSE;

	private ButtonGroup group = new ButtonGroup();  //  @jve:decl-index=0:

	private ButtonGroup group1 = new ButtonGroup(); // @jve:decl-index=0:

	private String emsFrom = "1";

	private String contraceFrom = "1"; // @jve:decl-index=0:

	private ContractAction contractAction = null;

	private BcsMessageAction bcsMessageAction = null;

	private JRadioButton rbBcsTenInnerMerge = null;

	private JRadioButton rbComplex = null;

	private JLabel jLabel = null;

	private JCheckBox cbWriteBackExg = null;

	private JCheckBox cbWriteBackImg = null;

	private JLabel jLabel1 = null;

	private JCheckBox jCheckBox = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JCheckBox cbWriteBackBomExg = null;

	private JCheckBox cbWriteBackBomImg = null;

	private JLabel jLabel4 = null;

	private JCheckBox cbModifyUnitPrice = null;

	private JRadioButton rbCustomsCommodity = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel21 = null;

	private JLabel jLabel31 = null;

	private JLabel jLabel41 = null;

	private JTextField tfSendDir = null;

	private JTextField tfRecvDir = null;

	private JTextField tfFinallyDir = null;

	private JTextField tfLogDir = null;

	private JButton btnSendDir = null;

	private JButton btnRecvDir = null;

	private JButton btnFinallyDir = null;

	private JButton btnLogDir = null;

	private JPanel jPanel7 = null;

	private JTextField tfLoadXmlDir = null;

	private JButton btnLoadXmlDir = null;

	private JPanel jPanel71 = null;

	private JRadioButton rbCommonContract = null;

	private JRadioButton rbContractEms = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel3 = null;

	private JPanel jPanel4 = null;

	private JPanel jPanel5 = null;

	private JPanel jPanel6 = null;

	private JPanel jPanel8 = null;

	private JToolBar jJToolBarBar = null;

	private JPanel jPanel9 = null;

	private JPanel jPanel10 = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:

	private JPanel jPanel31 = null;

	private JRadioButton rbFromCard = null;

	private JRadioButton rbFromManual = null;

	private JTabbedPane jTabbedPane1 = null;

	private JPanel jPanel51 = null;

	private JLabel jLabel51 = null;

	private JLabel jLabel6 = null;

	private JTextField tfSeqNo = null;

	private JTextField tfPwd = null;

	private JPanel jPanel61 = null;  //  @jve:decl-index=0:visual-constraint="58,638"

	private JTextField tfIdCard = null;

	private JLabel jLabel411 = null;

	private String tempDir = "./";

	private JLabel jLabel7 = null;

	private JCheckBox jCheckBox1 = null;

	private JPanel jTabbedPane2 = null;

	private JPanel jPanel = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JFormattedTextField tfPrice = null;

	private JFormattedTextField tfAmount = null;

	private JFormattedTextField tfMoney = null;

	private JLabel jLabel12 = null;

	private JLabel jLabel13 = null;

	private JLabel jLabel14 = null;

	private JCheckBox cbControlLength = null;

	private JLabel jLabel15 = null;

	private JLabel jLabel16 = null;

	private JFormattedTextField tfControlLength = null;

	private JPanel jPanel91 = null;

	private JLabel jLabel71 = null;

	private JFormattedTextField tfReportDecimalLength = null;

	private JLabel jLabel91 = null;

	private JPanel jPanel911 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private ButtonGroup bg = null;  //  @jve:decl-index=0:

	private ButtonGroup bgbus = null; // @jve:decl-index=0:

	private JCheckBox cbRemoteSignData = null;

	private JButton btnReadCard = null;

	private JPanel jPanel1 = null;

	private JRadioButton rbNormal = null;

	private JRadioButton rbEnhance = null;

	private ButtonGroup buttonGroup1 = null; // @jve:decl-index=0:visual-constraint="797,167"

	private JCheckBox cbPort9097Open = null;

	private JPanel jPanel11 = null;

	private JTextField tfRemoteHostAddress = null;

	private JLabel jLabel17 = null;

	private JLabel jLabel18 = null;

	private JTextField tfRemoteHostPort = null;

	private JPanel jPanel12 = null;

	private JRadioButton jRadioButton2 = null;

	private JTextField tfRemoteHostICPwd = null;

	private JLabel jLabel19 = null;

	private JLabel jLabel711 = null;

	private JFormattedTextField tfReportDecimalLengthUnitWaste = null;

	private JLabel jLabel911 = null;

	private JLabel jLabel61 = null;
	private JLabel label;
	private JFormattedTextField tfWasteAmount;
	private JLabel label_1;
	private JLabel label_2;
	private JFormattedTextField tfTotalAmount;
	private JLabel label_3;
	private JCheckBox ccbIsTotalMoney;

	/**
	 * This is the default constructor
	 */
	public FmBcsParameterSet() {
		super();
		contractSystemAction = (ContractSystemAction) CommonVars
				.getApplicationContext().getBean("contractSystemAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		bcsMessageAction = (BcsMessageAction) CommonVars
				.getApplicationContext().getBean("bcsMessageAction");
		bcsMessageAction.checkSystemParameterAuthority(new Request(
				CommonVars.getCurrUser()));
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("无纸通关系统参数设置");
		this.setSize(773, 634);
		this.setContentPane(getJContentPane());
		showData();
		dataState = DataState.BROWSE;
		setState();
		getButtonGroup();
		getButtonGroup1();
//		group.add(this.rbFromCard);
		group.add(this.rbFromManual);

		group1.add(rbCustomsCommodity);
		group1.add(rbBcsTenInnerMerge);
		group1.add(rbComplex);
		getBg();
		// getBgbcs();
	}

	private void showData() {
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfPrice, 0);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfAmount, 0);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfMoney, 0);
		CustomFormattedTextFieldUtils.setFormatterFactory(
				this.tfReportDecimalLength, 0);
		CustomFormattedTextFieldUtils.setFormatterFactory(
				this.tfReportDecimalLengthUnitWaste, 0);
		
		CustomFormattedTextFieldUtils.setFormatterFactory(
				this.tfWasteAmount, 0);
		CustomFormattedTextFieldUtils.setFormatterFactory(
				this.tfTotalAmount, 0);
		List list = null;
		ParameterSet para = null;

		list = contractSystemAction.findnameValues(new Request(CommonVars
				.getCurrUser()), ParameterType.contractFrom);
		if (list != null && list.size() > 0) {
			para = (ParameterSet) list.get(0);
			if (para.getNameValues().equals("0")) {
				this.rbCustomsCommodity.setSelected(true);
			} else if (para.getNameValues().equals("1")) {
				this.rbBcsTenInnerMerge.setSelected(true);
			} else if (para.getNameValues().equals("2")) {
				this.rbComplex.setSelected(true);
			}
		} else {
			this.rbCustomsCommodity.setSelected(false);
			this.rbBcsTenInnerMerge.setSelected(false);
			this.rbComplex.setSelected(false);
		}
		//
		// list = contractSystemAction.findnameValues(new Request(CommonVars
		// .getCurrUser()), ParameterType.SpecialCustoms);
		// if (list != null && list.size() > 0) {
		// para = (ParameterSet) list.get(0);
		// if (para.getNameValues().equals("0")) {
		// this.cbbBcsTenInnerMerger.setSelected(true);
		// } else if (para.getNameValues().equals("1")) {
		// this.cbbBcsComplex.setSelected(true);
		// }
		// } else {
		// this.cbbBcsTenInnerMerger.setSelected(false);
		// this.cbbBcsComplex.setSelected(false);
		//
		// }

		BcsParameterSet parameterSet = contractAction
				.findBcsParameterSet(new Request(CommonVars.getCurrUser(), true));
		if (parameterSet == null) {
			return;
		}
		if (parameterSet != null
				&& parameterSet.getUpdateContractBomWriteBackExg() != null
				&& parameterSet.getUpdateContractBomWriteBackExg()) {
			this.cbWriteBackExg.setSelected(true);
		} else {
			this.cbWriteBackExg.setSelected(false);
		}

		if (parameterSet != null
				&& parameterSet.getUpdateContractBomWriteBackImg() != null
				&& parameterSet.getUpdateContractBomWriteBackImg()) {
			this.cbWriteBackImg.setSelected(true);
		} else {
			this.cbWriteBackImg.setSelected(false);
		}

		if (parameterSet != null
				&& parameterSet.getUpdateContractImgWriteBackBomExg() != null
				&& parameterSet.getUpdateContractImgWriteBackBomExg()) {
			this.cbWriteBackBomExg.setSelected(true);
		} else {
			this.cbWriteBackBomExg.setSelected(false);
		}
		if (parameterSet != null && parameterSet.getPutOnRecords() != null
				&& parameterSet.getPutOnRecords()) {
			this.jCheckBox1.setSelected(true);
		} else {
			this.jCheckBox1.setSelected(false);
		}

		if (parameterSet != null
				&& parameterSet.getUpdateContractExgWriteBackBomImg() != null
				&& parameterSet.getUpdateContractExgWriteBackBomImg()) {
			this.cbWriteBackBomImg.setSelected(true);
		} else {
			this.cbWriteBackBomImg.setSelected(false);
		}

		if (parameterSet != null
				&& parameterSet.getCheckIsComputeReturn() != null
				&& parameterSet.getCheckIsComputeReturn()) {
			this.jRadioButton2.setSelected(true);
		} else {
			this.jRadioButton2.setSelected(false);
		}

		if (parameterSet != null && parameterSet.getIsControlLength() != null
				&& parameterSet.getIsControlLength()) {
			this.cbControlLength.setSelected(true);
		} else {
			this.cbControlLength.setSelected(false);
		}

		if (parameterSet != null && parameterSet.getBytesLength() != null) {
			this.tfControlLength.setValue(parameterSet.getBytesLength());
		} else {
			this.tfControlLength.setValue(50);
		}

		// if (parameterSet != null
		// && parameterSet.getReportDecimalLength() != null) {
		// this.tfReportDecimalLength.setValue(parameterSet
		// .getReportDecimalLength());
		// } else {
		// this.tfReportDecimalLength.setValue(2);
		// }

		list = contractSystemAction.findnameValues(new Request(CommonVars
				.getCurrUser()), ParameterType.isVailCustomsOther);
		if (list != null && !list.isEmpty()) {
			para = ((ParameterSet) list.get(0));
			if (para.getNameValues() != null
					&& para.getNameValues().equals("1")) {
				this.jCheckBox.setSelected(true);
				CommonVars.setIsVailCustomsOther(true);
			} else {
				this.jCheckBox.setSelected(false);
				this.jCheckBox.setSelected(false);
			}
		} else {
			CommonVars.setIsVailCustomsOther(false);
			this.jCheckBox.setSelected(false);
		}

		// 保存纸质手册的报关单中的单价是否可以修改
		list = contractSystemAction.findnameValues(new Request(CommonVars
				.getCurrUser()), ParameterType.isCanModifyUnitPrice);
		if (list != null && !list.isEmpty()) {
			para = ((ParameterSet) list.get(0));
			if (para.getNameValues() != null
					&& para.getNameValues().equals("1")) {
				this.cbModifyUnitPrice.setSelected(true);
				CommonVars.setIsCanModifyUnitPrice(true);
			} else {
				this.cbModifyUnitPrice.setSelected(false);
				this.cbModifyUnitPrice.setSelected(false);
			}
		} else {
			CommonVars.setIsCanModifyUnitPrice(false);
			this.cbModifyUnitPrice.setSelected(false);
		}

		this.tfSendDir.setText(parameterSet.getSendDir());
		this.tfRecvDir.setText(parameterSet.getRecvDir());
		this.tfFinallyDir.setText(parameterSet.getFinallyDir());
		this.tfLogDir.setText(parameterSet.getLogDir());
		this.tfLoadXmlDir.setText(parameterSet.getLoadQPDataXmlDir());
		if (parameterSet.getIsContractEms() != null
				&& parameterSet.getIsContractEms()) {
			this.rbContractEms.setSelected(true);
		} else {
			//this.rbCommonContract.setSelected(true);
		}
//		if (parameterSet.getReadFromCard() != null
//				&& parameterSet.getReadFromCard()) {
//			this.rbFromCard.setSelected(true);
//		} else {
//			this.rbFromManual.setSelected(true);
//		}
//		this.cbRemoteSignData
//				.setSelected(parameterSet.getRemoteSignData() != null
//						&& parameterSet.getRemoteSignData());
//		if (parameterSet.getRemoteDxpMail() != null
//				&& parameterSet.getRemoteDxpMail()) {
//			this.rbEnhance.setSelected(true);
//		} else {
//			this.rbNormal.setSelected(true);
//		}
//		this.cbPort9097Open
//				.setSelected(parameterSet.getPort9097IsOpen() != null
//						&& parameterSet.getPort9097IsOpen());
		this.tfRemoteHostAddress.setText(parameterSet.getRemoteHostAddress());
		this.tfRemoteHostPort.setText(parameterSet.getRemoteHostPort());
		this.tfRemoteHostICPwd.setText(parameterSet.getRemoteHostICPwd());
		if (parameterSet.getPutOnRecords() != null
				&& parameterSet.getPutOnRecords()) {
			this.jCheckBox1.setSelected(true);
		} else {
			this.jCheckBox1.setSelected(false);
		}
		if (parameterSet != null && ("1".equals(parameterSet.getManageType()))) {
			getJRadioButton().setSelected(true);
		} else {
			getJRadioButton1().setSelected(true);
		}

//		this.tfSeqNo.setText(parameterSet.getSeqNo());
//		this.tfPwd.setText(parameterSet.getPwd());
		this.tfIdCard.setText(parameterSet.getIdCard());
		this.tfPrice.setValue(parameterSet.getPriceBit() == null ? 5
				: parameterSet.getPriceBit());
		this.tfAmount.setValue(parameterSet.getCountBit() == null ? 5
				: parameterSet.getCountBit());
		this.tfMoney.setValue(parameterSet.getMoneyBit() == null ? 5
				: parameterSet.getMoneyBit());
		this.tfReportDecimalLength.setValue(parameterSet
				.getReportDecimalLength() == null ? 8 : parameterSet
				.getReportDecimalLength());
		this.tfReportDecimalLengthUnitWaste.setValue(parameterSet
				.getReportDecimalLengthUnitWaste() == null ? 8 : parameterSet
				.getReportDecimalLengthUnitWaste());
		this.tfWasteAmount.setValue(parameterSet.getWasteAmount()
				== null ? tfReportDecimalLengthUnitWaste.getValue() : parameterSet
						.getWasteAmount());
		this.tfTotalAmount.setValue(parameterSet.getTotalAmount()
				== null ? tfReportDecimalLengthUnitWaste.getValue() : parameterSet
						.getTotalAmount());
		if(parameterSet!=null&&parameterSet.getIsTotalMoney()!=null&&parameterSet.getIsTotalMoney()){
			this.ccbIsTotalMoney.setSelected(true);
		}
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setBorder(javax.swing.BorderFactory
					.createCompoundBorder(null, null));
			jContentPane.add(getJJToolBarBar(), BorderLayout.SOUTH);
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkNull()) {
						return;
					}

					// 保存合同备案来源选项
					ParameterSet para = null;
					List list = contractSystemAction.findnameValues(
							new Request(CommonVars.getCurrUser()),
							ParameterType.contractFrom);
					para = (ParameterSet) list.get(0);
					String values = (rbCustomsCommodity.isSelected() ? "0"
							: (rbBcsTenInnerMerge.isSelected() ? "1" : "2"));
					para.setNameValues(values);
					CommonVars.setContractFrom(values);
					contractSystemAction.saveValues(new Request(CommonVars
							.getCurrUser()), para);
					list = contractSystemAction.findnameValues(new Request(
							CommonVars.getCurrUser()),
							ParameterType.isVailCustomsOther);
					para = (ParameterSet) list.get(0);
					para.setNameValues(values);
					if (jCheckBox.isSelected()) {
						para.setNameValues("1");
						CommonVars.setIsVailCustomsOther(true);
					} else {
						para.setNameValues("0");
						CommonVars.setIsVailCustomsOther(false);
					}
					contractSystemAction.saveValues(new Request(CommonVars
							.getCurrUser()), para);

					list = contractSystemAction.findnameValues(new Request(
							CommonVars.getCurrUser()),
							ParameterType.isCanModifyUnitPrice);
					if (list == null || list.size() == 0) {
						ParameterSet pa = new ParameterSet();
						pa.setType(ParameterType.isCanModifyUnitPrice);
						pa.setNameValues("0");
						contractSystemAction.saveValues(new Request(CommonVars
								.getCurrUser()), pa);
					}
					list = contractSystemAction.findnameValues(new Request(
							CommonVars.getCurrUser()),
							ParameterType.isCanModifyUnitPrice);
					para = (ParameterSet) list.get(0);
					para.setNameValues(values);
					if (cbModifyUnitPrice.isSelected()) {
						para.setNameValues("1");
						CommonVars.setIsCanModifyUnitPrice(true);
					} else {
						para.setNameValues("0");
						CommonVars.setIsCanModifyUnitPrice(false);
					}
					contractSystemAction.saveValues(new Request(CommonVars
							.getCurrUser()), para);
					list = contractSystemAction
							.findnameValues(new Request(CommonVars
									.getCurrUser()), ParameterType.isPrintAll);
					para = (ParameterSet) list.get(0);
					para.setNameValues(values);
					contractSystemAction.saveValues(new Request(CommonVars
							.getCurrUser()), para);

					BcsParameterSet parameterSet = contractAction
							.findBcsParameterSet(new Request(CommonVars
									.getCurrUser(), true));
					if (parameterSet == null) {
						parameterSet = new BcsParameterSet();
					}
					parameterSet.setSendDir(tfSendDir.getText().trim());
					parameterSet.setRecvDir(tfRecvDir.getText().trim());
					parameterSet.setFinallyDir(tfFinallyDir.getText().trim());
					parameterSet.setLogDir(tfLogDir.getText().trim());
					parameterSet.setLoadQPDataXmlDir(tfLoadXmlDir.getText()
							.trim());
					/**
					 * 2013-1-23 现发送报文不再需求加签和不需要配置唯一号，所以注释并调整
					 */
//					parameterSet.setSeqNo(tfSeqNo.getText().trim());
//					parameterSet.setPwd(tfPwd.getText().trim());
//					parameterSet.setReadFromCard(rbFromCard.isSelected());
					parameterSet.setIdCard(tfIdCard.getText().trim());
//					parameterSet.setRemoteSignData(cbRemoteSignData
//							.isSelected());
//					parameterSet.setRemoteDxpMail(rbEnhance.isSelected());
//					parameterSet.setPort9097IsOpen(cbPort9097Open.isSelected());
					parameterSet.setRemoteHostAddress(tfRemoteHostAddress
							.getText());
					parameterSet.setRemoteHostPort(tfRemoteHostPort.getText());
					parameterSet.setRemoteHostICPwd(tfRemoteHostICPwd.getText()
							.trim());
					parameterSet.setIsContractEms(rbContractEms.isSelected());
					parameterSet
							.setUpdateContractBomWriteBackExg(cbWriteBackExg
									.isSelected());
					parameterSet
							.setUpdateContractBomWriteBackImg(cbWriteBackImg
									.isSelected());
					parameterSet
							.setUpdateContractExgWriteBackBomImg(cbWriteBackBomImg
									.isSelected());
					parameterSet.setCheckIsComputeReturn(jRadioButton2
							.isSelected());
					parameterSet
							.setUpdateContractImgWriteBackBomExg(cbWriteBackBomExg
									.isSelected());
					parameterSet.setPutOnRecords(jCheckBox1.isSelected());

					parameterSet.setCountBit(tfAmount.getValue() == null ? 5
							: Integer.valueOf(tfAmount.getValue().toString()
									.trim()));
					parameterSet.setMoneyBit(tfMoney.getValue() == null ? 5
							: Integer.valueOf(tfMoney.getValue().toString()
									.trim()));
					parameterSet.setPriceBit(tfPrice.getValue() == null ? 5
							: Integer.valueOf(tfPrice.getValue().toString()
									.trim()));
					parameterSet.setIsControlLength(cbControlLength
							.isSelected());

					parameterSet.setBytesLength(Double.valueOf(
							tfControlLength.getValue().toString()).intValue());

					parameterSet.setReportDecimalLength(Double.valueOf(
							tfReportDecimalLength.getValue().toString())
							.intValue());
					parameterSet.setReportDecimalLengthUnitWaste(Double
							.valueOf(
									tfReportDecimalLengthUnitWaste.getValue()
											.toString()).intValue());
					parameterSet.setWasteAmount(Double
							.valueOf(
									tfWasteAmount.getValue()
									.toString()).intValue());
					parameterSet.setTotalAmount(Double
							.valueOf(
									tfTotalAmount.getValue()
									.toString()).intValue());
					parameterSet
							.setManageType(getJRadioButton().isSelected() ? "1"
									: "0");
					//柯鹏程
					parameterSet.setIsTotalMoney(ccbIsTotalMoney.isSelected());
					contractAction.saveBcsParameterSet(new Request(CommonVars
							.getCurrUser(), true), parameterSet);

					dataState = DataState.BROWSE;
					setState();
				}
			});

		}
		return btnSave;
	}

	/**
	 * 
	 * 检查是否为空
	 */
	private boolean checkNull() {
		if (!"".equals(tfPrice.getText().trim())) {
			if (Integer.valueOf(tfPrice.getText()) >= 6) {
				JOptionPane.showMessageDialog(this,
						"单价小数位不能超过海关允许的最大小数位(海关允许的最大小数位为５)！", "提示！", 0);
				return true;
			}
			try {
				Integer.parseInt(tfPrice.getText().trim());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "您输入的单价小数位不是数值形式！", "提示！",
						0);
				return true;
			}
		}
		if (!"".equals(tfAmount.getText().trim())) {
			if (Integer.valueOf(tfAmount.getText()) >= 6) {
				JOptionPane.showMessageDialog(this,
						"数量小数位不能超过海关允许的最大小数位(海关允许的最大小数位为５)！", "提示！", 0);
				return true;
			}
			try {
				Integer.parseInt(tfAmount.getText().trim());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "您输入的数量小数位不是数值形式！", "提示！",
						0);
				return true;
			}
		}
		if (!"".equals(tfMoney.getText().trim())) {
			if (Integer.valueOf(tfMoney.getText()) >= 6) {
				JOptionPane.showMessageDialog(this,
						"金额小数位不能超过海关允许的最大小数位(海关允许的最大小数位为５)！", "提示！", 0);
				return true;
			}
			try {
				Integer.parseInt(tfMoney.getText().trim());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "您输入的金额小数位不是数值形式！", "提示！",
						0);
				return true;
			}
		}
		if (!"".equals(tfReportDecimalLength.getText().trim())) {
			if (Integer.valueOf(tfReportDecimalLength.getText()) >= 9) {
				JOptionPane.showMessageDialog(this, "报表数量金额小数位允许的最大小数位为8！",
						"提示！", 0);
				return true;
			}
			try {
				Integer.parseInt(tfReportDecimalLength.getText().trim());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "您输入的报表数量金额小数位不是数值形式！",
						"提示！", 0);
				return true;
			}
		}
		if (!"".equals(tfReportDecimalLengthUnitWaste.getText().trim())) {
			// if
			// (Integer.valueOf(tfReportDecimalLengthUnitConsumption.getText())
			// >= 9) {
			// JOptionPane.showMessageDialog(this, "报表单耗小数位允许的最大小数位为8！",
			// "提示！", 0);
			// return true;
			// }
			try {
				Integer.parseInt(tfReportDecimalLengthUnitWaste.getText()
						.trim());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "您输入的报表单耗小数位不是数值形式！",
						"提示！", 0);
				return true;
			}
		}
		if (!"".equals(tfWasteAmount.getText().trim())) {
			try {
				Integer.parseInt(tfWasteAmount.getText()
						.trim());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "您输入的报表损耗量小数位不是数值形式！",
						"提示！", 0);
				return true;
			}
		}
		if (!"".equals(tfTotalAmount.getText().trim())) {
			try {
				Integer.parseInt(tfTotalAmount.getText()
						.trim());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "您输入的报表总用量小数位不是数值形式！",
						"提示！", 0);
				return true;
			}
		}
		// if (tfReportDecimalLength.getValue() == null
		// || Double.valueOf(tfReportDecimalLength.getValue().toString())
		// .intValue() == 0) {
		// JOptionPane.showMessageDialog(FmBcsParameterSet.this,
		// "请输入要保留的小数位位数", "提示", JOptionPane.ERROR_MESSAGE);
		// return true;
		//
		// }
		if (!tfRemoteHostICPwd.getText().trim().equals("")
				&& (tfRemoteHostAddress.getText().trim().equals("")
						|| tfRemoteHostPort.getText().trim().equals("") || tfIdCard
						.getText().trim().equals(""))) {
			JOptionPane.showMessageDialog(FmBcsParameterSet.this,
					"请检查确认已输入IP地址/端口和操作员号", "提示", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		
		
//		if (this.cbRemoteSignData.isSelected()
//				&& this.tfRemoteHostPort.getText().trim().equals("")) {
//			JOptionPane.showMessageDialog(FmBcsParameterSet.this,
//					"请输入远程加签服务器端口", "提示", JOptionPane.ERROR_MESSAGE);
//			return true;
//		}

		if (cbControlLength.isSelected()) {
			if (tfControlLength.getValue() == null
					|| Double.valueOf(tfControlLength.getValue().toString())
							.intValue() == 0) {
				JOptionPane.showMessageDialog(FmBcsParameterSet.this,
						"请输入名称、规格限定长度的字节数", "提示", JOptionPane.ERROR_MESSAGE);
				return true;
			} else if (Double.valueOf(tfControlLength.getValue().toString())
					.intValue() < Double.valueOf(0)
					|| Double.valueOf(tfControlLength.getValue().toString())
							.intValue() > 50) {
				JOptionPane.showMessageDialog(FmBcsParameterSet.this,
						"输入名称、规格限定长度应该在1~50个字符", "提示",
						JOptionPane.ERROR_MESSAGE);
				return true;
			}

		}
		return false;
	}

	private void saveStyle(int style) {
		AclUser obj = CommonVars.getCurrUser();
		obj.setStyle(Integer.valueOf(style));
		contractSystemAction.saveAclUser(new Request(CommonVars.getCurrUser()),
				obj);
	}

	// private void saveTxtDir() {
	// systemAction.saveTxtDir(new Request(CommonVars.getCurrUser()),
	// this.jTextField4.getText(), this.jTextField5.getText(),
	// this.jTextField6.getText(), this.jTextField7.getText());
	// }

	private void saveValues(int parameterType, String values, String flat) {
		List list = contractSystemAction.findnameValues(new Request(CommonVars
				.getCurrUser()), parameterType);
		ParameterSet para = (ParameterSet) list.get(0);
		para.setNameValues(values);
		if (flat.equals("0")) {

		} else if (flat.equals("1")) {
			CommonVars.setEmsFrom(values);
		} else if (flat.equals("2")) {
			CommonVars.setContractFrom(values);
		}
		contractSystemAction.saveValues(new Request(CommonVars.getCurrUser()),
				para);

	}

	// private boolean checkNull() {
	// if (jTextField.getText().equals("")) {
	// JOptionPane.showMessageDialog(this, "报文发送路径不能为空！", "提示！", 2);
	// return true;
	// }
	// if (jTextField1.getText().equals("")) {
	// JOptionPane.showMessageDialog(this, "回执存放路径不能为空！", "提示！", 2);
	// return true;
	// }
	// if (jTextField2.getText().equals("")) {
	// JOptionPane.showMessageDialog(this, "处理完的回执存放路径不能为空！", "提示！", 2);
	// return true;
	// }
	// if (jTextField3.getText().equals("")) {
	// JOptionPane.showMessageDialog(this, "日志路径不能为空！", "提示！", 2);
	// return true;
	// }
	/*
	 * if (jTextField4.getText().equals("")) {
	 * JOptionPane.showMessageDialog(this, "文本导入路径不能为空！", "提示！", 2); return
	 * true; } if (jTextField5.getText().equals("")) {
	 * JOptionPane.showMessageDialog(this, "导入成功的文本存放路径不能为空！", "提示！", 2); return
	 * true; } if (jTextField6.getText().equals("")) {
	 * JOptionPane.showMessageDialog(this, "导入失败的文本存放路径不能为空！", "提示！", 2); return
	 * true; } if (jTextField7.getText().equals("")) {
	 * JOptionPane.showMessageDialog(this, "导入文本日志存放路径不能为空！", "提示！", 2); return
	 * true; }
	 */

	// return false;
	//
	// }
	// private boolean checkRepeat() {
	// if (jTextField.getText().equals(jTextField1.getText())) {
	// JOptionPane.showMessageDialog(this, "注意：报文发送路径不能与回执存放路径相同！", "提示！",
	// 2);
	// return true;
	// }
	// if (jTextField.getText().equals(jTextField2.getText())) {
	// JOptionPane.showMessageDialog(this, "注意：报文发送路径不能与处理完的回执存放路径相同！",
	// "提示！", 2);
	// return true;
	// }
	// if (jTextField1.getText().equals(jTextField2.getText())) {
	// JOptionPane.showMessageDialog(this, "注意：回执存放路径不能与处理完的回执存放路径相同！",
	// "提示！", 2);
	// return true;
	// }
	// return false;
	// }
	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					FmBcsParameterSet.this.dispose();

				}
			});

		}
		return btnClose;
	}

	// private class RadioActionListner implements ActionListener {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// if (rbCommonContract.isSelected()) {
	// FmParameterSet.this.setEmsFrom("1");
	// } else if (rbContractEms.isSelected()) {
	// FmParameterSet.this.setEmsFrom("2");
	// } else if (jRadioButton2.isSelected()) {
	// FmParameterSet.this.setEmsFrom("3");
	// }
	// }
	// }

	private class RadioActionListner1 implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (rbCustomsCommodity.isSelected()) {
				FmBcsParameterSet.this.setContraceFrom("0");
			} else if (rbBcsTenInnerMerge.isSelected()) {
				FmBcsParameterSet.this.setContraceFrom("1");
			} else if (rbComplex.isSelected()) {
				FmBcsParameterSet.this.setContraceFrom("2");
			}
		}
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnEdit() {
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

	private void setState() {
		this.jCheckBox1.setEnabled(!(dataState == DataState.BROWSE));
		this.rbBcsTenInnerMerge.setEnabled(!(dataState == DataState.BROWSE));
		this.rbComplex.setEnabled(!(dataState == DataState.BROWSE));
		this.rbCustomsCommodity.setEnabled(!(dataState == DataState.BROWSE));
		this.btnSave.setEnabled(!(dataState == DataState.BROWSE));
		this.btnEdit.setEnabled(dataState == DataState.BROWSE);
		this.cbWriteBackExg.setEnabled(dataState != DataState.BROWSE);
		this.cbWriteBackImg.setEnabled(dataState != DataState.BROWSE);
		this.cbWriteBackBomExg.setEnabled(dataState != DataState.BROWSE);
		this.cbWriteBackBomImg.setEnabled(dataState != DataState.BROWSE);
		this.jRadioButton2.setEnabled(dataState != DataState.BROWSE);
		this.cbModifyUnitPrice.setEnabled(dataState != DataState.BROWSE);
		jCheckBox.setEnabled(dataState != DataState.BROWSE);
		//this.rbCommonContract.setEnabled(dataState != DataState.BROWSE);
		this.rbContractEms.setEnabled(dataState != DataState.BROWSE);
//		this.rbFromCard.setEnabled(dataState != DataState.BROWSE);
//		this.rbFromManual.setEnabled(dataState != DataState.BROWSE);

		this.tfFinallyDir.setEditable(dataState != DataState.BROWSE);
		this.tfLoadXmlDir.setEditable(dataState != DataState.BROWSE);
		this.tfLogDir.setEditable(dataState != DataState.BROWSE);
		this.tfRecvDir.setEditable(dataState != DataState.BROWSE);
		this.tfSendDir.setEditable(dataState != DataState.BROWSE);

		this.tfIdCard.setEditable(dataState != DataState.BROWSE);
//		this.tfPwd.setEditable(dataState != DataState.BROWSE);
//		this.tfSeqNo.setEditable(dataState != DataState.BROWSE);

		this.btnFinallyDir.setEnabled(dataState != DataState.BROWSE);
		this.btnLoadXmlDir.setEnabled(dataState != DataState.BROWSE);
		this.btnLogDir.setEnabled(dataState != DataState.BROWSE);
		this.btnRecvDir.setEnabled(dataState != DataState.BROWSE);
		this.btnSendDir.setEnabled(dataState != DataState.BROWSE);

		this.tfPrice.setEditable(dataState != DataState.BROWSE);
		this.tfAmount.setEditable(dataState != DataState.BROWSE);
		this.tfMoney.setEditable(dataState != DataState.BROWSE);

		cbControlLength.setEnabled(dataState != DataState.BROWSE);
		tfControlLength.setEnabled(dataState != DataState.BROWSE
				&& cbControlLength.isSelected());
		tfReportDecimalLength.setEnabled(dataState != DataState.BROWSE);
		tfReportDecimalLengthUnitWaste
				.setEnabled(dataState != DataState.BROWSE);
		tfWasteAmount.setEnabled(dataState != DataState.BROWSE);
		tfTotalAmount.setEnabled(dataState != DataState.BROWSE);
		getJRadioButton().setEnabled(dataState != DataState.BROWSE);
		getJRadioButton1().setEnabled(dataState != DataState.BROWSE);
		// cbbBcsTenInnerMerger.setEnabled(dataState != DataState.BROWSE);
		// cbbBcsComplex.setEnabled(dataState != DataState.BROWSE);
//		this.cbRemoteSignData.setEnabled(dataState != DataState.BROWSE);
		this.tfRemoteHostAddress.setEditable(dataState != DataState.BROWSE);
		this.tfRemoteHostPort.setEditable(dataState != DataState.BROWSE);
		this.tfRemoteHostICPwd.setEditable(dataState != DataState.BROWSE);
		this.ccbIsTotalMoney.setEnabled(dataState != DataState.BROWSE);
//		this.rbNormal.setEnabled(this.cbRemoteSignData.isSelected()
//				&& dataState != DataState.BROWSE);
//		this.rbEnhance.setEnabled(this.cbRemoteSignData.isSelected()
//				&& dataState != DataState.BROWSE);
//		this.cbPort9097Open.setEnabled(this.rbEnhance.isSelected()
//				&& dataState != DataState.BROWSE);
//		this.btnReadCard.setEnabled(dataState == DataState.BROWSE);
	}

	/**
	 * @return Returns the emsFrom.
	 */
	public String getEmsFrom() {
		return emsFrom;
	}

	/**
	 * @param emsFrom
	 *            The emsFrom to set.
	 */
	public void setEmsFrom(String emsFrom) {
		this.emsFrom = emsFrom;
	}

	/**
	 * This method initializes jRadioButton3
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBcsTenInnerMerge() {
		if (rbBcsTenInnerMerge == null) {
			rbBcsTenInnerMerge = new JRadioButton();
			rbBcsTenInnerMerge.setText("物料与报关对应表");
			rbBcsTenInnerMerge.setBounds(new Rectangle(240, 34, 131, 15));
			rbBcsTenInnerMerge.addActionListener(new RadioActionListner1());
		}
		return rbBcsTenInnerMerge;
	}

	/**
	 * This method initializes jRadioButton4
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbComplex() {
		if (rbComplex == null) {
			rbComplex = new JRadioButton();
			rbComplex.setText("自用商品编码");
			rbComplex.setBounds(new Rectangle(433, 34, 142, 15));
			rbComplex.addActionListener(new RadioActionListner1());
		}
		return rbComplex;
	}

	/**
	 * @return Returns the contraceFrom.
	 */
	public String getContraceFrom() {
		return contraceFrom;
	}

	/**
	 * @param contraceFrom
	 *            The contraceFrom to set.
	 */
	public void setContraceFrom(String contraceFrom) {
		this.contraceFrom = contraceFrom;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbWriteBackExg() {
		if (cbWriteBackExg == null) {
			cbWriteBackExg = new JCheckBox();
			cbWriteBackExg.setText("自动计算成品单价和金额");
			cbWriteBackExg.setBounds(new Rectangle(55, 47, 200, 15));
		}
		return cbWriteBackExg;
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbWriteBackImg() {
		if (cbWriteBackImg == null) {
			cbWriteBackImg = new JCheckBox();
			cbWriteBackImg.setText("自动计算料件数量和金额");
			cbWriteBackImg.setBounds(new Rectangle(264, 47, 181, 15));
		}
		return cbWriteBackImg;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setText("是否检查");
			jCheckBox.setBounds(new Rectangle(53, 41, 125, 15));
		}
		return jCheckBox;
	}

	// protected void paintComponent(Graphics g) {
	// jSeparator.setBounds(3, this.jPanel.getHeight() - 50, this.jPanel
	// .getWidth() - 6, 3);
	// // // jPanel5.setBounds(28, this.jPanel1.getHeight() - 50,
	// // this.jPanel1.getWidth() - 28 -28, 3);
	// btnEdit.setBounds(this.jPanel.getWidth() - 392,
	// this.jPanel.getHeight() - 40, 68, 26);
	// btnSave.setBounds(this.jPanel.getWidth() - 292,
	// this.jPanel.getHeight() - 40, 68, 26);
	// btnClose.setBounds(this.jPanel.getWidth() - 192, this.jPanel
	// .getHeight() - 40, 68, 26);
	// super.paintComponent(g);
	// }

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbWriteBackBomExg() {
		if (cbWriteBackBomExg == null) {
			cbWriteBackBomExg = new JCheckBox();
			cbWriteBackBomExg.setText("自动计算单耗及成品单价和金额");
			cbWriteBackBomExg.setBounds(new Rectangle(55, 101, 215, 15));
		}
		return cbWriteBackBomExg;
	}

	/**
	 * This method initializes jCheckBox2
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbWriteBackBomImg() {
		if (cbWriteBackBomImg == null) {
			cbWriteBackBomImg = new JCheckBox();
			cbWriteBackBomImg.setText("自动计算料件数量和金额");
			cbWriteBackBomImg.setBounds(new Rectangle(55, 157, 221, 15));
		}
		return cbWriteBackBomImg;
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbModifyUnitPrice() {
		if (cbModifyUnitPrice == null) {
			cbModifyUnitPrice = new JCheckBox();
			cbModifyUnitPrice.setText("可修改单价");
			cbModifyUnitPrice.setBounds(new Rectangle(308, 41, 94, 15));
		}
		return cbModifyUnitPrice;
	}

	/**
	 * This method initializes rbCommonContract
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbCustomsCommodity() {
		if (rbCustomsCommodity == null) {
			rbCustomsCommodity = new JRadioButton();
			rbCustomsCommodity.setText("报关商品资料");
			rbCustomsCommodity.setBounds(new Rectangle(55, 34, 109, 15));
		}
		return rbCustomsCommodity;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("注意:所有路径均指在服务器端的路径,请先在服务器端进行设置.");
			jLabel7.setBounds(new Rectangle(76, 7, 397, 20));
			jLabel7.setForeground(Color.RED);
			jLabel41 = new JLabel();
			jLabel41.setBounds(new Rectangle(55, 113, 86, 19));
			jLabel41.setText("\u65e5\u5fd7\u6240\u5728\u8def\u5f84");
			jLabel31 = new JLabel();
			jLabel31.setBounds(new Rectangle(55, 92, 86, 18));
			jLabel31.setText("\u5b58\u653e\u8def\u5f84");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(55, 75, 86, 18));
			jLabel21.setText("\u5904\u7406\u5b8c\u7684\u56de\u6267");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(55, 51, 86, 17));
			jLabel11.setText("\u56de\u6267\u5b58\u653e\u8def\u5f84");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(55, 22, 86, 22));
			jLabel5.setText("\u62a5\u6587\u53d1\u9001\u8def\u5f84");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBorder(BorderFactory.createTitledBorder(null,
					"\u8def\u5f84\u8bbe\u7f6e",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel2.setBounds(new Rectangle(52, 33, 597, 141));
			jPanel2.add(jLabel5, null);
			jPanel2.add(jLabel11, null);
			jPanel2.add(jLabel21, null);
			jPanel2.add(jLabel31, null);
			jPanel2.add(jLabel41, null);
			jPanel2.add(getTfSendDir(), null);
			jPanel2.add(getTfRecvDir(), null);
			jPanel2.add(getTfFinallyDir(), null);
			jPanel2.add(getTfLogDir(), null);
			jPanel2.add(getBtnSendDir(), null);
			jPanel2.add(getBtnRecvDir(), null);
			jPanel2.add(getBtnFinallyDir(), null);
			jPanel2.add(getBtnLogDir(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes tfSendDir
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSendDir() {
		if (tfSendDir == null) {
			tfSendDir = new JTextField();
			tfSendDir.setBounds(new Rectangle(141, 23, 304, 23));
		}
		return tfSendDir;
	}

	/**
	 * This method initializes tfRecvDir
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRecvDir() {
		if (tfRecvDir == null) {
			tfRecvDir = new JTextField();
			tfRecvDir.setBounds(new Rectangle(141, 51, 304, 23));
		}
		return tfRecvDir;
	}

	/**
	 * This method initializes tfFinallyDir
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFinallyDir() {
		if (tfFinallyDir == null) {
			tfFinallyDir = new JTextField();
			tfFinallyDir.setBounds(new Rectangle(141, 80, 304, 23));
		}
		return tfFinallyDir;
	}

	/**
	 * This method initializes tfLogDir
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLogDir() {
		if (tfLogDir == null) {
			tfLogDir = new JTextField();
			tfLogDir.setBounds(new Rectangle(141, 111, 304, 23));
		}
		return tfLogDir;
	}

	/**
	 * This method initializes btnSendDir
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSendDir() {
		if (btnSendDir == null) {
			btnSendDir = new JButton();
			btnSendDir.setBounds(new Rectangle(445, 23, 23, 23));
			btnSendDir.setText("...");
			btnSendDir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(tfSendDir);
				}
			});
		}
		return btnSendDir;
	}

	private void setDir(JTextField tf) {
		if (tf.getText() != null && !"".equals(tf.getText())) {
			File file = new File(tf.getText());
			if (file.exists())
				setTempDir(tf.getText());
		}
		JFileChooser fileChooser = new JFileChooser(getTempDir());
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int state = fileChooser.showDialog(FmBcsParameterSet.this, "选择路径");
		if (state == JFileChooser.APPROVE_OPTION) {
			fileChooser.getSelectedFile();
			File f = fileChooser.getSelectedFile();
			tf.setText(f.getPath());
			setTempDir(tf.getText());
		}
	}

	/**
	 * This method initializes btnRecvDir
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRecvDir() {
		if (btnRecvDir == null) {
			btnRecvDir = new JButton();
			btnRecvDir.setBounds(new Rectangle(445, 51, 23, 22));
			btnRecvDir.setText("...");
			btnRecvDir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(tfRecvDir);
				}
			});
		}
		return btnRecvDir;
	}

	/**
	 * This method initializes btnFinallyDir
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFinallyDir() {
		if (btnFinallyDir == null) {
			btnFinallyDir = new JButton();
			btnFinallyDir.setBounds(new Rectangle(445, 80, 23, 22));
			btnFinallyDir.setText("...");
			btnFinallyDir
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setDir(tfFinallyDir);
						}
					});
		}
		return btnFinallyDir;
	}

	/**
	 * This method initializes btnLogDir
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnLogDir() {
		if (btnLogDir == null) {
			btnLogDir = new JButton();
			btnLogDir.setBounds(new Rectangle(445, 111, 23, 23));
			btnLogDir.setText("...");
			btnLogDir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(tfLogDir);
				}
			});
		}
		return btnLogDir;
	}

	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(null);
			jPanel7.setBorder(BorderFactory.createTitledBorder(null,
					"QP\u6570\u636e\u5bfc\u5165\u8def\u5f84\u8bbe\u5b9a",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel7.setBounds(new Rectangle(52, 184, 597, 65));
			jPanel7.add(getTfLoadXmlDir(), null);
			jPanel7.add(getBtnLoadXmlDir(), null);
		}
		return jPanel7;
	}

	/**
	 * This method initializes tfLoadXmlDir
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLoadXmlDir() {
		if (tfLoadXmlDir == null) {
			tfLoadXmlDir = new JTextField();
			tfLoadXmlDir.setBounds(new Rectangle(55, 30, 393, 23));
		}
		return tfLoadXmlDir;
	}

	/**
	 * This method initializes btnLoadXmlDir
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnLoadXmlDir() {
		if (btnLoadXmlDir == null) {
			btnLoadXmlDir = new JButton();
			btnLoadXmlDir.setBounds(new Rectangle(448, 30, 21, 22));
			btnLoadXmlDir.setText("...");
			btnLoadXmlDir
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setDir(tfLoadXmlDir);
						}
					});
		}
		return btnLoadXmlDir;
	}

	/**
	 * This method initializes jPanel71
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel71() {
		if (jPanel71 == null) {
			jPanel71 = new JPanel();
			jPanel71.setLayout(null);
			jPanel71.setBorder(BorderFactory.createTitledBorder(null,
					"\u7eb8\u8d28\u624b\u518c\u7c7b\u578b\u8bbe\u5b9a",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel71.setBounds(new Rectangle(52, 33, 641, 44));
			//lyh 2013-03-04 根据需求现在海关模式已早就不存在【纸质手册】，古去掉此选项
			//jPanel71.add(getRbCommonContract(), null);
			jPanel71.add(getRbContractEms(), null);
		}
		return jPanel71;
	}

	/**
	 * This method initializes rbCommonContract
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbCommonContract() {
		if (rbCommonContract == null) {
			rbCommonContract = new JRadioButton();
			rbCommonContract.setBounds(new Rectangle(240, 20, 160, 15));
			rbCommonContract.setText("普通纸质手册");
			rbCommonContract.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						jPanel5.setBorder(BorderFactory.createTitledBorder(
								null, "合同备案资料来源",
								TitledBorder.DEFAULT_JUSTIFICATION,
								TitledBorder.DEFAULT_POSITION, new Font(
										"Dialog", Font.BOLD, 12), Color.blue));
						jRadioButton.setSelected(true);
						jRadioButton1.setEnabled(false);
					}
				}
			});
		}
		return rbCommonContract;
	}

	/**
	 * This method initializes rbContractEms
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbContractEms() {
		if (rbContractEms == null) {
			rbContractEms = new JRadioButton();
			rbContractEms.setBounds(new Rectangle(55, 20, 173, 15));
			rbContractEms.setText("电子化手册");
			rbContractEms.setSelected(true);
			rbContractEms.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						jPanel5.setBorder(BorderFactory.createTitledBorder(
								null, "备案资料库来源",
								TitledBorder.DEFAULT_JUSTIFICATION,
								TitledBorder.DEFAULT_POSITION, new Font(
										"Dialog", Font.BOLD, 12), Color.blue));
						jRadioButton1.setEnabled(true);
					}
				}
			});
		}
		return rbContractEms;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(JTabbedPane.TOP);
			jTabbedPane.addTab("路径设定", null, getJPanel4(), null);
			jTabbedPane.addTab("其它设置", null, getJTabbedPane2(), null);
			jTabbedPane.addTab("基础设定", null, getJPanel3(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.add(getJPanel71(), null);
			jPanel3.add(getJPanel5(), null);
			jPanel3.add(getJPanel6(), null);
			jPanel3.add(getJCheckBox1(), null);
			jPanel3.add(getJPanel911(), null);
			jPanel3.add(getJPanel12(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.add(getJPanel7(), null);
			jPanel4.add(getJPanel2(), null);
//			jPanel4.add(getJPanel31(), null);
			jPanel4.add(jLabel7, null);
			jPanel4.add(getJPanel11(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
			jPanel5.setBounds(new Rectangle(52, 80, 641, 81));
			jPanel5
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"备案资料库来源设定",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.BOLD, 12),
									Color.blue));
			jPanel5.add(getRbCustomsCommodity(), null);
			jPanel5.add(getRbBcsTenInnerMerge(), null);
			jPanel5.add(getRbComplex(), null);
			/**
			 * 使用核查分析手册模块时，来源需选择"报关商品资料"
			 */
			JLabel lbNote = new JLabel("(\u4F7F\u7528\u6838\u67E5\u5206\u6790\uFF08\u624B\u518C\uFF09\u6A21\u5757\u65F6,\u6765\u6E90\u9700\u9009\u62E9\"\u62A5\u5173\u5546\u54C1\u8D44\u6599\")");
			lbNote.setBounds(10, 56, 333, 15);
			lbNote.setForeground(Color.blue);
			jPanel5.add(lbNote);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jLabel3 = new JLabel();
			jLabel3.setText("通关手册修改成品时回写单耗及料件设定：");
			jLabel3.setBounds(new Rectangle(26, 131, 305, 15));
			jLabel3.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel3.setForeground(Color.gray);
			jLabel2 = new JLabel();
			jLabel2.setText("通关手册修改料件时回写单耗及成品设定：");
			jLabel2.setBounds(new Rectangle(26, 76, 324, 15));
			jLabel2.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel2.setForeground(Color.gray);
			jLabel = new JLabel();
			jLabel.setForeground(Color.gray);
			jLabel.setBounds(new Rectangle(26, 25, 316, 15));
			jLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel.setText("通关手册修改单耗时回写成品及料件设定：");
			jPanel6.setLayout(null);
			jPanel6
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u901a\u5173\u624b\u518c\u8d44\u6599\u56de\u5199\u8bbe\u5b9a",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.BOLD, 12),
									Color.blue));
			jPanel6.setBounds(new Rectangle(52, 159, 641, 183));
			jPanel6.add(jLabel, null);
			jPanel6.add(getCbWriteBackExg(), null);
			jPanel6.add(getCbWriteBackImg(), null);
			jPanel6.add(jLabel2, null);
			jPanel6.add(getCbWriteBackBomExg(), null);
			jPanel6.add(jLabel3, null);
			jPanel6.add(getCbWriteBackBomImg(), null);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jPanel8 = new JPanel();
			jLabel1 = new JLabel();
			jLabel1.setText("报关单生效是否检查转关附加信息：");
			jLabel1.setBounds(new Rectangle(27, 23, 236, 15));
			jLabel1.setForeground(Color.gray);
			jLabel4 = new JLabel();
			jLabel4.setText("进出口报关单中单价是否可修改设定：");
			jLabel4.setBounds(new Rectangle(282, 24, 238, 15));
			jLabel4.setForeground(Color.gray);
			jPanel8.setLayout(null);
			jPanel8.setBorder(BorderFactory.createTitledBorder(null,
					"\u62a5\u5173\u5355\u8bbe\u5b9a",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel8.setBounds(new Rectangle(50, 307, 641, 65));
			jPanel8.add(jLabel1, null);
			jPanel8.add(getJCheckBox(), null);
			jPanel8.add(getCbModifyUnitPrice(), null);
			jPanel8.add(jLabel4, null);
		}
		return jPanel8;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getJPanel9());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel9() {
		if (jPanel9 == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(5);
			flowLayout.setVgap(0);
			jPanel9 = new JPanel();
			jPanel9.setLayout(flowLayout);
			jPanel9.add(getBtnEdit(), null);
			jPanel9.add(getBtnSave(), null);
			jPanel9.add(getBtnClose(), null);
		}
		return jPanel9;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			//buttonGroup.add(this.getRbCommonContract());
			buttonGroup.add(this.getRbContractEms());
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jPanel31
	 * 
	 * @return javax.swing.JPanel
	 */
//	private JPanel getJPanel31() {
//		if (jPanel31 == null) {
//			jPanel31 = new JPanel();
//			jPanel31.setLayout(null);
//			jPanel31.setBounds(new Rectangle(52, 323, 597, 209));
//			jPanel31
//					.setBorder(BorderFactory
//							.createTitledBorder(
//									null,
//									"\u751f\u6210\u62a5\u6587\u7b7e\u540d\u4fe1\u606f\u7684\u57fa\u672c\u914d\u7f6e",
//									TitledBorder.DEFAULT_JUSTIFICATION,
//									TitledBorder.DEFAULT_POSITION, new Font(
//											"Dialog", Font.BOLD, 12),
//									Color.blue));
//			jPanel31.add(getRbFromCard(), null);
//			jPanel31.add(getJTabbedPane1(), null);
//		}
//		return jPanel31;
//	}

	/**
	 * This method initializes rbFromCard
	 * 
	 * @return javax.swing.JRadioButton
	 */
//	private JRadioButton getRbFromCard() {
//		if (rbFromCard == null) {
//			rbFromCard = new JRadioButton();
//			rbFromCard.setBounds(new Rectangle(61, 25, 207, 26));
//			rbFromCard.setText("从IC卡里读取签名用基本信息");
//			rbFromCard.addItemListener(new java.awt.event.ItemListener() {
//				public void itemStateChanged(java.awt.event.ItemEvent e) {
//					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
//						jTabbedPane1
//								.setSelectedIndex(rbFromCard.isSelected() ? 0
//										: 1);
//					}
//				}
//			});
//		}
//		return rbFromCard;
//	}

	/**
	 * This method initializes rbFromManual
	 * 
	 * @return javax.swing.JRadioButton
	 */
//	private JRadioButton getRbFromManual() {
//		if (rbFromManual == null) {
//			rbFromManual = new JRadioButton();
//			rbFromManual.setBounds(new Rectangle(274, 25, 173, 26));
//			rbFromManual
//					.setText("\u624b\u5de5\u914d\u7f6e\u7b7e\u540d\u7528\u57fa\u672c\u4fe1\u606f");
//			rbFromManual.addItemListener(new java.awt.event.ItemListener() {
//				public void itemStateChanged(java.awt.event.ItemEvent e) {
//					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
//						jTabbedPane1
//								.setSelectedIndex(rbFromManual.isSelected(true));
//					}
//				}
//			});
//		}
//		return rbFromManual;
//	}

	/**
	 * This method initializes jTabbedPane1
	 * 
	 * @return javax.swing.JTabbedPane
	 */
//	private JTabbedPane getJTabbedPane1() {
//		if (jTabbedPane1 == null) {
//			jTabbedPane1 = new JTabbedPane();
//			jTabbedPane1.setBounds(new Rectangle(61, 55, 414, 143));
//			jTabbedPane1.setEnabled(false);
//			jTabbedPane1.addTab("从IC卡里读取签名用基本信息设置", null, getJPanel51(), null);
//			jTabbedPane1
//					.addTab(
//							"\u624b\u5de5\u914d\u7f6e\u7b7e\u540d\u7528\u57fa\u672c\u4fe1\u606f\u8bbe\u7f6e",
//							null, getJPanel61(), null);
//		}
//		return jTabbedPane1;
//	}

	/**
	 * This method initializes jPanel51
	 * 
	 * @return javax.swing.JPanel
	 */
//	private JPanel getJPanel51() {
//		if (jPanel51 == null) {
//			jLabel6 = new JLabel();
//			jLabel6.setBounds(new Rectangle(13, 33, 66, 22));
//			jLabel6.setText("\u8bfb\u5361\u5bc6\u7801");
//			jLabel51 = new JLabel();
//			jLabel51.setBounds(new Rectangle(13, 7, 66, 22));
//			jLabel51.setText("\u8bfb\u5361\u552f\u4e00\u53f7");
//			jPanel51 = new JPanel();
//			jPanel51.setLayout(null);
//			jPanel51.add(jLabel51, null);
//			jPanel51.add(jLabel6, null);
//			jPanel51.add(getTfSeqNo(), null);
//			jPanel51.add(getTfPwd(), null);
//			jPanel51.add(getCbRemoteSignData(), null);
//			jPanel51.add(getBtnReadCard(), null);
//			jPanel51.add(getJPanel1(), null);
//			jPanel51.add(getCbPort9097Open(), null);
//		}
//		return jPanel51;
//	}

	/**
	 * This method initializes tfSeqNo
	 * 
	 * @return javax.swing.JTextField
	 */
//	private JTextField getTfSeqNo() {
//		if (tfSeqNo == null) {
//			tfSeqNo = new JTextField();
//			tfSeqNo.setBounds(new Rectangle(79, 6, 312, 24));
//		}
//		return tfSeqNo;
//	}

	/**
	 * This method initializes tfPwd
	 * 
	 * @return javax.swing.JTextField
	 */
//	private JTextField getTfPwd() {
//		if (tfPwd == null) {
//			tfPwd = new JTextField();
//			tfPwd.setBounds(new Rectangle(79, 33, 312, 24));
//		}
//		return tfPwd;
//	}

	/**
	 * This method initializes jPanel61
	 * 
	 * @return javax.swing.JPanel
	 */
//	private JPanel getJPanel61() {
//		if (jPanel61 == null) {
//			jLabel411 = new JLabel();
//			jLabel411.setText("操作员卡号");
//			jLabel411.setBounds(new Rectangle(20, 58, 66, 22));
//			jPanel61 = new JPanel();
//			jPanel61.setLayout(null);
//		}
//		return jPanel61;
//	}

	/**
	 * This method initializes tfIdCard
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfIdCard() {
		if (tfIdCard == null) {
			tfIdCard = new JTextField();
			tfIdCard.setBounds(new Rectangle(86, 57, 121, 23));
		}
		return tfIdCard;
	}

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox();
			jCheckBox1.setBounds(new Rectangle(476, 6, 171, 23));
			jCheckBox1.setText("允许多本备案资料库备案");
		}
		return jCheckBox1;
	}

	/**
	 * This method initializes jTabbedPane2
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JPanel getJTabbedPane2() {
		if (jTabbedPane2 == null) {
			jTabbedPane2 = new JPanel();
			jTabbedPane2.setLayout(null);
			jTabbedPane2.setPreferredSize(new Dimension(1, 1));
			jTabbedPane2.add(getJPanel(), null);
			jTabbedPane2.add(getJPanel10(), null);
			jTabbedPane2.add(getJPanel91(), null);
			jTabbedPane2.add(getJPanel8(), null);
			jTabbedPane2.add(getJRadioButton2(), null);
			jTabbedPane2.add(getCcbIsTotalMoney());
		}
		return jTabbedPane2;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel14 = new JLabel();
			jLabel14.setBounds(new Rectangle(170, 70, 22, 22));
			jLabel14.setText("位");
			jLabel13 = new JLabel();
			jLabel13.setBounds(new Rectangle(395, 30, 20, 22));
			jLabel13.setText("位");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(170, 30, 20, 22));
			jLabel12.setText("位");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(30, 70, 100, 22));
			jLabel10.setText("金额小数位保留");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(256, 30, 100, 22));
			jLabel9.setText("数量小数位保留");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(30, 30, 100, 22));
			jLabel8.setText("单价小数位保留");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(51, 23, 641, 118));
			jPanel.setBorder(BorderFactory.createTitledBorder(null,
					"\u5408\u540c\u5c0f\u6570\u4f4d\u8bbe\u7f6e",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel.add(jLabel8, null);
			jPanel.add(jLabel9, null);
			jPanel.add(jLabel10, null);
			jPanel.add(getTfPrice(), null);
			jPanel.add(getTfAmount(), null);
			jPanel.add(getTfMoney(), null);
			jPanel.add(jLabel12, null);
			jPanel.add(jLabel13, null);
			jPanel.add(jLabel14, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes tfPrice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfPrice() {
		if (tfPrice == null) {
			tfPrice = new JFormattedTextField();
			tfPrice.setBounds(new Rectangle(132, 30, 35, 22));
		}
		return tfPrice;
	}

	/**
	 * This method initializes tfAmount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfAmount() {
		if (tfAmount == null) {
			tfAmount = new JFormattedTextField();
			tfAmount.setBounds(new Rectangle(360, 30, 35, 22));
		}
		return tfAmount;
	}

	/**
	 * This method initializes tfMoney
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfMoney() {
		if (tfMoney == null) {
			tfMoney = new JFormattedTextField();
			tfMoney.setBounds(new Rectangle(132, 70, 35, 22));
		}
		return tfMoney;
	}

	/**
	 * This method initializes cbWriteBackExg1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbControlLength() {
		if (cbControlLength == null) {
			cbControlLength = new JCheckBox();
			cbControlLength.setBounds(new Rectangle(31, 30, 142, 15));
			cbControlLength.setText("名称、规格限定长度");
			cbControlLength.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (cbControlLength.isSelected())
						tfControlLength.setEnabled(true);
					else
						tfControlLength.setEnabled(false);
				}
			});
		}
		return cbControlLength;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfControlLength() {
		if (tfControlLength == null) {
			tfControlLength = new JCustomFormattedTextField();
			tfControlLength.setBounds(new Rectangle(214, 26, 50, 23));
		}
		return tfControlLength;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel10() {
		if (jPanel10 == null) {
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(266, 25, 41, 23));
			jLabel16.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			// jLabel16.setText("字节 (每个汉字占2个字节,键盘上看到的每个符号占1个字节)");
			jLabel16.setText("字符");
			jLabel16.setForeground(Color.gray);
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(171, 26, 42, 23));
			jLabel15.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel15.setText("长度为:");
			jLabel15.setForeground(Color.gray);
			jPanel10 = new JPanel();
			jPanel10.setLayout(null);
			jPanel10.setBounds(new Rectangle(51, 153, 641, 69));
			jPanel10.setBorder(BorderFactory.createTitledBorder(null,
					"备案资料库和通关手册备案中名称、规格长度设置",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel10.add(getCbControlLength(), null);
			jPanel10.add(jLabel15, null);
			jPanel10.add(jLabel16, null);
			jPanel10.add(getTfControlLength(), null);
		}
		return jPanel10;
	}

	/**
	 * This method initializes jPanel91
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel91() {
		if (jPanel91 == null) {
			jLabel911 = new JLabel();
			jLabel911.setBounds(new Rectangle(281, 21, 44, 23));
			jLabel911.setText("位小数");
			jLabel711 = new JLabel();
			jLabel711.setBounds(new Rectangle(169, 21, 58, 23));
			jLabel711.setText("单耗保留:");
			jLabel91 = new JLabel();
			jLabel91.setBounds(new Rectangle(114, 21, 45, 23));
			jLabel91.setText("位小数");
			jLabel71 = new JLabel();
			jLabel71.setBounds(new Rectangle(13, 21, 55, 23));
			jLabel71.setText("最多保留:");
			jPanel91 = new JPanel();
			jPanel91.setLayout(null);
			jPanel91.setBounds(new Rectangle(50, 233, 641, 58));
			jPanel91.setBorder(BorderFactory.createTitledBorder(null,
					"报表数量、金额栏位小数位控制", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel91.add(jLabel71, null);
			jPanel91.add(getTfReportDecimalLength(), null);
			jPanel91.add(jLabel91, null);
			jPanel91.add(jLabel711, null);
			jPanel91.add(getTfReportDecimalLengthUnitWaste(), null);
			jPanel91.add(jLabel911, null);
			jPanel91.add(getLabel());
			jPanel91.add(getTfWasteAmount());
			jPanel91.add(getLabel_1());
			jPanel91.add(getLabel_2());
			jPanel91.add(getTfTotalAmount());
			jPanel91.add(getLabel_3());
		}
		return jPanel91;
	}

	/**
	 * This method initializes tfReportDecimalLength
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfReportDecimalLength() {
		if (tfReportDecimalLength == null) {
			tfReportDecimalLength = new JFormattedTextField();
			tfReportDecimalLength.setBounds(new Rectangle(68, 21, 46, 23));
		}
		return tfReportDecimalLength;
	}

	/**
	 * This method initializes jPanel911
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel911() {
		if (jPanel911 == null) {
			jPanel911 = new JPanel();
			jPanel911.setLayout(null);
			jPanel911.setBorder(BorderFactory.createTitledBorder(null,
					"软件管理功能设置", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, Color.blue));
			jPanel911.setBounds(new Rectangle(53, 346, 639, 65));
			jPanel911.add(getJRadioButton(), null);
			jPanel911.add(getJRadioButton1(), null);
		}
		return jPanel911;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new Rectangle(17, 32, 227, 19));
			jRadioButton.setText("仅用于关务管理不向海关申报数据");
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setBounds(new Rectangle(249, 32, 222, 19));
			jRadioButton1.setText("既用于关务管理也向海关申报数据");
			jRadioButton1.setSelected(true);
		}
		return jRadioButton1;
	}

	private ButtonGroup getBg() {
		if (bg == null) {
			bg = new ButtonGroup();
			bg.add(getJRadioButton());
			bg.add(getJRadioButton1());
		}
		return bg;
	}

	// /**
	// * This method initializes jPanel81
	// *
	// * @return javax.swing.JPanel
	// */
	//
	// private ButtonGroup getBgbcs() {
	// if (bgbus == null) {
	// bgbus = new ButtonGroup();
	// bgbus.add(getCbbBcsTenInnerMerger());
	// bgbus.add(getCbbBcsComplex());
	// }
	// return bgbus;
	// }

	/**
	 * This method initializes jCheckBox2
	 * 
	 * @return javax.swing.JCheckBox
	 */
//	private JCheckBox getCbRemoteSignData() {
//		if (cbRemoteSignData == null) {
//			cbRemoteSignData = new JCheckBox();
//			cbRemoteSignData.setBounds(new Rectangle(75, 60, 79, 22));
//			cbRemoteSignData.setText("远程加签");
//			cbRemoteSignData.addItemListener(new java.awt.event.ItemListener() {
//				public void itemStateChanged(java.awt.event.ItemEvent e) {
//					setState();
//				}
//			});
//		}
//		return cbRemoteSignData;
//	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
//	private JButton getBtnReadCard() {
//		if (btnReadCard == null) {
//			btnReadCard = new JButton();
//			btnReadCard.setBounds(new Rectangle(302, 62, 87, 22));
//			btnReadCard.setText("读卡测试");
//			btnReadCard.addActionListener(new java.awt.event.ActionListener() {
//				public void actionPerformed(java.awt.event.ActionEvent e) {
//					ICCardInfo cardInfo = bcsMessageAction.getICCardInfo(
//							new Request(CommonVars.getCurrUser()), tfSeqNo
//									.getText().trim(), tfPwd.getText().trim());
//					if (cardInfo != null) {
//						StringBuffer sb = new StringBuffer();
//						// sb.append("------------------"+i);
//						sb.append("卡号-------------" + cardInfo.getIcCode()
//								+ "\n");
//						sb.append("证书-------------" + cardInfo.getCertSeqNo()
//								+ "\n");
//						sb.append("申请者名称--------" + cardInfo.getApplier()
//								+ "\n");
//						sb.append("单位名称----------" + cardInfo.getTradeName()
//								+ "\n");
//						sb.append("单位类型代码-------" + cardInfo.getTradeType()
//								+ "\n");
//						sb.append("单位代码-----------" + cardInfo.getTradeCode()
//								+ "\n");
//						sb.append("数字签名-----------"
//								+ cardInfo.getSignData().substring(0, 10)
//								+ "\n");
//						// System.out.println(sb.toString());
//						JOptionPane.showMessageDialog(FmBcsParameterSet.this,
//								sb.toString(), "提示",
//								JOptionPane.INFORMATION_MESSAGE);
//					} else {
//						JOptionPane.showMessageDialog(FmBcsParameterSet.this,
//								"没有读到卡信息", "提示",
//								JOptionPane.INFORMATION_MESSAGE);
//					}
//				}
//			});
//		}
//		return btnReadCard;
//	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
//	private JPanel getJPanel1() {
//		if (jPanel1 == null) {
//			jPanel1 = new JPanel();
//			jPanel1.setLayout(null);
//			jPanel1.setBounds(new Rectangle(155, 58, 142, 30));
//			jPanel1.setBorder(BorderFactory.createTitledBorder(null, "",
//					TitledBorder.DEFAULT_JUSTIFICATION,
//					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
//							Font.BOLD, 12), new Color(51, 51, 51)));
//			jPanel1.add(getRbNormal(), null);
//			jPanel1.add(getRbEnhance(), null);
//		}
//		return jPanel1;
//	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
//	private JRadioButton getRbNormal() {
//		if (rbNormal == null) {
//			rbNormal = new JRadioButton();
//			rbNormal.setBounds(new Rectangle(3, 5, 68, 20));
//			rbNormal.setText("普通版");
//			rbNormal.addItemListener(new java.awt.event.ItemListener() {
//				public void itemStateChanged(java.awt.event.ItemEvent e) {
//					cbPort9097Open.setSelected(false);
//					setState();
//				}
//			});
//		}
//		return rbNormal;
//	}

	/**
	 * This method initializes jRadioButton3
	 * 
	 * @return javax.swing.JRadioButton
	 */
//	private JRadioButton getRbEnhance() {
//		if (rbEnhance == null) {
//			rbEnhance = new JRadioButton();
//			rbEnhance.setBounds(new Rectangle(70, 4, 65, 21));
//			rbEnhance.setText("加强版");
//			rbEnhance.addItemListener(new java.awt.event.ItemListener() {
//				public void itemStateChanged(java.awt.event.ItemEvent e) {
//					setState();
//				}
//			});
//		}
//		return rbEnhance;
//	}

	/**
	 * This method initializes buttonGroup1
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
//			buttonGroup1.add(this.getRbNormal());
//			buttonGroup1.add(this.getRbEnhance());
		}
		return buttonGroup1;
	}

	/**
	 * This method initializes jCheckBox2
	 * 
	 * @return javax.swing.JCheckBox
	 */
//	private JCheckBox getCbPort9097Open() {
//		if (cbPort9097Open == null) {
//			cbPort9097Open = new JCheckBox();
//			cbPort9097Open.setBounds(new Rectangle(155, 90, 142, 22));
//			cbPort9097Open.setText("9097端口已开通");
//		}
//		return cbPort9097Open;
//	}

	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jLabel61 = new JLabel();
			jLabel61.setBounds(new Rectangle(31, 60, 54, 18));
			jLabel61.setText("操作员号");
			jLabel19 = new JLabel();
			jLabel19.setBounds(new Rectangle(349, 23, 43, 24));
			jLabel19.setText("卡密码");
			jLabel18 = new JLabel();
			jLabel18.setBounds(new Rectangle(210, 24, 30, 24));
			jLabel18.setText("端口");
			jLabel17 = new JLabel();
			jLabel17.setBounds(new Rectangle(31, 23, 40, 24));
			jLabel17.setText("IP地址");
			jPanel11 = new JPanel();
			jPanel11.setLayout(null);
			jPanel11.setBounds(new Rectangle(52, 256, 595, 101));
			jPanel11.setBorder(BorderFactory.createTitledBorder(null,
					"\u8fdc\u7a0b\u670d\u52a1\u5730\u5740",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel11.add(getTfRemoteHostAddress(), null);
			jPanel11.add(jLabel17, null);
			jPanel11.add(jLabel18, null);
			jPanel11.add(getTfRemoteHostPort(), null);
			jPanel11.add(getTfRemoteHostICPwd(), null);
			jPanel11.add(jLabel19, null);
//			jPanel11.add(jLabel411, null);
			jPanel11.add(getTfIdCard(), null);
			jPanel11.add(jLabel61, null);
		}
		return jPanel11;
	}

	/**
	 * This method initializes tfRemoteHostAddress1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemoteHostAddress() {
		if (tfRemoteHostAddress == null) {
			tfRemoteHostAddress = new JTextField();
			tfRemoteHostAddress.setBounds(new Rectangle(86, 24, 121, 24));
		}
		return tfRemoteHostAddress;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemoteHostPort() {
		if (tfRemoteHostPort == null) {
			tfRemoteHostPort = new JTextField();
			tfRemoteHostPort.setBounds(new Rectangle(241, 24, 99, 24));
			tfRemoteHostPort.setDocument(new PlainDocument() {
				public void insertString(int offs, String str, AttributeSet a)
						throws BadLocationException {
					for (int i = 0; i < str.length(); i++) {
						if (str.charAt(i) < '0' || str.charAt(i) > '9') {
							return;
						}
					}
					super.insertString(offs, str, a);
				}
			});
		}
		return tfRemoteHostPort;
	}

	/**
	 * This method initializes jPanel12
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel12() {
		if (jPanel12 == null) {
			jPanel12 = new JPanel();
			jPanel12.setLayout(null);
			jPanel12.setBounds(new Rectangle(54, 417, 638, 64));
			//jPanel12.add(getJRadioButton2(), null);
		}
		return jPanel12;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setText("核销表总进口金额扣减退料出口【复出】金额");
			jRadioButton2.setBounds(new Rectangle(70, 373, 347, 32));
		}
		return jRadioButton2;
	}

	/**
	 * This method initializes tfRemoteHostICPwd
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemoteHostICPwd() {
		if (tfRemoteHostICPwd == null) {
			tfRemoteHostICPwd = new JTextField();
			tfRemoteHostICPwd.setBounds(new Rectangle(399, 23, 121, 24));
		}
		return tfRemoteHostICPwd;
	}

	/**
	 * This method initializes tfReportDecimalLengthUnitWaste
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfReportDecimalLengthUnitWaste() {
		if (tfReportDecimalLengthUnitWaste == null) {
			tfReportDecimalLengthUnitWaste = new JFormattedTextField();
			tfReportDecimalLengthUnitWaste.setBounds(new Rectangle(230, 21, 49, 23));
		}
		return tfReportDecimalLengthUnitWaste;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel();
			label.setText("位小数");
			label.setBounds(new Rectangle(269, 21, 44, 23));
			label.setBounds(431, 21, 44, 23);
		}
		return label;
	}
	private JFormattedTextField getTfWasteAmount() {
		if (tfWasteAmount == null) {
			tfWasteAmount = new JFormattedTextField();
			tfWasteAmount.setEnabled(false);
			tfWasteAmount.setBounds(new Rectangle(218, 21, 49, 23));
			tfWasteAmount.setBounds(380, 21, 49, 23);
		}
		return tfWasteAmount;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel();
			label_1.setText("损耗量:");
			label_1.setBounds(new Rectangle(157, 21, 58, 23));
			label_1.setBounds(333, 21, 51, 23);
		}
		return label_1;
	}
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel();
			label_2.setText("位小数");
			label_2.setBounds(new Rectangle(269, 21, 44, 23));
			label_2.setBounds(587, 21, 44, 23);
		}
		return label_2;
	}
	private JFormattedTextField getTfTotalAmount() {
		if (tfTotalAmount == null) {
			tfTotalAmount = new JFormattedTextField();
			tfTotalAmount.setEnabled(false);
			tfTotalAmount.setBounds(new Rectangle(218, 21, 49, 23));
			tfTotalAmount.setBounds(536, 21, 49, 23);
		}
		return tfTotalAmount;
	}
	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel();
			label_3.setText("总用量:");
			label_3.setBounds(new Rectangle(157, 21, 58, 23));
			label_3.setBounds(485, 21, 46, 23);
		}
		return label_3;
	}
	private JCheckBox getCcbIsTotalMoney() {
		if (ccbIsTotalMoney == null) {
			ccbIsTotalMoney = new JCheckBox("手册表头进口总值是否统计备案料件内购金额");
			ccbIsTotalMoney.setBounds(70, 418, 309, 23);
		}
		return ccbIsTotalMoney;
	}
}
