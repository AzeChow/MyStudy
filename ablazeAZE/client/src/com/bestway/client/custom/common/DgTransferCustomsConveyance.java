package com.bestway.client.custom.common;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.MotorCode;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgTransferCustomsConveyance extends JDialogBase {

	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JButton btnOk = null;
	private JButton btnCancel = null;
	private JTextField tfOverseasConveyanceCode = null;
	private JTextField tfOverseasConveyanceName = null;
	private JTextField tfOverseasConveyanceFlights = null;
	private JTextField tfOverseasConveyanceBillOfLading = null;
	private JTextField tfDomesticConveyanceCode = null;
	private JTextField tfDomesticConveyanceName = null;
	private JTextField tfDomesticConveyanceFlights = null;
	private JComboBox cbbTransportMode = null;

	private int dataState = -1;
	private BaseCustomsDeclaration customsDeclaration = null; // @jve:decl-index=0:

	private boolean isTruckTransfer = false;
	private Transf transf = null; // @jve:decl-index=0:
	private String importDate = null;
	private String numberPlate = null; // 提运单号 // @jve:decl-index=0:

	private BaseEncAction baseEncAction = null;
	private MaterialManageAction materialManageAction = null;
	private ManualDeclareAction manualDecleareAction = null;
	public boolean isok = true;

	private JLabel jLabel19 = null;
	private JButton jButton = null;

	/**
	 * @return Returns the baseEncAction.
	 */
	public BaseEncAction getBaseEncAction() {
		return baseEncAction;
	}

	/**
	 * @param baseEncAction
	 *            The baseEncAction to set.
	 */
	public void setBaseEncAction(BaseEncAction baseEncAction) {
		this.baseEncAction = baseEncAction;
	}

	/**
	 * @return Returns the importDate.
	 */
	public String getImportDate() {
		return importDate;
	}

	/**
	 * @param importDate
	 *            The importDate to set.
	 */
	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}

	/**
	 * @return Returns the isTruckTransfer.
	 */
	public boolean isTruckTransfer() {
		return isTruckTransfer;
	}

	/**
	 * @param isTruckTransfer
	 *            The isTruckTransfer to set.
	 */
	public void setTruckTransfer(boolean isTruckTransfer) {
		this.isTruckTransfer = isTruckTransfer;
	}

	/**
	 * @return Returns the numberPlate.
	 */
	public String getNumberPlate() {
		return numberPlate;
	}

	/**
	 * @param numberPlate
	 *            The numberPlate to set.
	 */
	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}

	/**
	 * @return Returns the transf.
	 */
	public Transf getTransf() {
		return transf;
	}

	/**
	 * @param transf
	 *            The transf to set.
	 */
	public void setTransf(Transf transf) {
		this.transf = transf;
	}

	public DgTransferCustomsConveyance() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();
		initUIComponents();
		setIsok(false);
	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {
			this.initUIComponents();
		}
		super.setVisible(isFlag);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("转关运输填写");
		this.setContentPane(getJContentPane());
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(449, 345);
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				setState();
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
			jLabel19 = new JLabel();
			jContentPane = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel5 = new JLabel();
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel10 = new JLabel();
			javax.swing.JLabel jLabel11 = new JLabel();
			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel13 = new JLabel();
			javax.swing.JLabel jLabel14 = new JLabel();
			jContentPane.setLayout(null);
			jLabel.setBounds(26, 9, 108, 22);
			jLabel.setText("境外运输工具编号");
			jLabel1.setBounds(26, 49, 108, 22);
			jLabel1.setText("境外运输工具名称");
			jLabel2.setBounds(26, 106, 108, 22);
			jLabel2.setText("境外运输工具提单号");
			jLabel3.setBounds(26, 135, 108, 22);
			jLabel3.setText("境内运输方式");
			jLabel4.setBounds(26, 164, 108, 22);
			jLabel4.setText("境内运输工具编号");
			jLabel5.setBounds(26, 78, 108, 22);
			jLabel5.setText("境外运输工具航次");
			jLabel6.setBounds(318, 203, 107, 22);
			jLabel6.setText("(汽车则填车牌号)");
			jLabel7.setBounds(26, 203, 108, 22);
			jLabel7.setText("境内运输工具名称");
			jLabel8.setBounds(318, 232, 107, 22);
			jLabel8.setText("(汽车则填日期)");
			jLabel9.setBounds(26, 232, 108, 22);
			jLabel9.setText("境内运输工具航次");
			jLabel10.setBounds(318, 9, 107, 22);
			jLabel10.setText("(汽车则填司机本号)");
			jLabel11.setBounds(318, 49, 107, 22);
			jLabel11.setText("(汽车则填车牌号)");
			jLabel12.setBounds(318, 78, 107, 22);
			jLabel12.setText("(汽车则填日期)");
			jLabel13.setBounds(318, 106, 107, 22);
			jLabel13.setText("(汽车则填车牌号)");
			jLabel14.setBounds(318, 164, 107, 22);
			jLabel14.setText("(汽车则填司机本号)");
			jLabel19.setBounds(307, 135, 12, 22);
			jLabel19.setText("*");
			jLabel19.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel19.setForeground(new java.awt.Color(51, 51, 255));
			jContentPane.add(getJPanel(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(jLabel9, null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(jLabel13, null);
			jContentPane.add(jLabel14, null);
			jContentPane.add(getTfOverseasConveyanceCode(), null);
			jContentPane.add(getTfOverseasConveyanceName(), null);
			jContentPane.add(getTfOverseasConveyanceFlights(), null);
			jContentPane.add(getTfOverseasConveyanceBillOfLading(), null);
			jContentPane.add(getTfDomesticConveyanceCode(), null);
			jContentPane.add(getTfDomesticConveyanceName(), null);
			jContentPane.add(getTfDomesticConveyanceFlights(), null);
			jContentPane.add(getCbbTransportMode(), null);
			jContentPane.add(jLabel19, null);
		}
		return jContentPane;
	}

	/**
	 * @return Returns the customsDeclaration.
	 */
	public BaseCustomsDeclaration getCustomsDeclaration() {
		return customsDeclaration;
	}

	/**
	 * @param customsDeclaration
	 *            The customsDeclaration to set.
	 */
	public void setCustomsDeclaration(BaseCustomsDeclaration customsDeclaration) {
		this.customsDeclaration = customsDeclaration;
	}

	/**
	 * @return Returns the dataState.
	 */
	public int getDataState() {
		return dataState;
	}

	/**
	 * @param dataState
	 *            The dataState to set.
	 */
	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(0, 258, 441, 41);
			jPanel.setBorder(javax.swing.BorderFactory
					.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel.add(getBtnOk(), null);
			jPanel.add(getBtnCancel(), null);
			jPanel.add(getJButton(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(285, 8, 66, 24);
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillData();
					/*
					 * customsDeclaration = (BaseCustomsDeclaration)
					 * baseEncAction .saveCustomsDeclaration(new
					 * Request(CommonVars .getCurrUser()), customsDeclaration);
					 */
					String homeCard = numberPlate;// 提运单号
					MotorCode motor = null;
					motor = materialManageAction.findMotorCodeByCode(
							new Request(CommonVars.getCurrUser()), homeCard);
					if (motor != null) {
						motor.setComplex(customsDeclaration
								.getDomesticConveyanceCode());
					} else {
						motor = new MotorCode();
						motor.setHomeCard(numberPlate); // 国内车牌 = 提运单号
						motor.setComplex(customsDeclaration
								.getDomesticConveyanceCode());// 海关编码 = 境内运输编号
						motor.setCompany(CommonVars.getCurrUser().getCompany());
						materialManageAction.saveMotorCodeToCustoms(
								new Request(CommonVars.getCurrUser()), motor);
					}
					setIsok(true);
					DgTransferCustomsConveyance.this.dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(356, 8, 66, 24);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgTransferCustomsConveyance.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes tfOverseasConveyanceCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOverseasConveyanceCode() {
		if (tfOverseasConveyanceCode == null) {
			tfOverseasConveyanceCode = new JTextField();
			tfOverseasConveyanceCode.setBounds(137, 8, 168, 34);
			tfOverseasConveyanceCode.setDocument(new CustomDocument());
			tfOverseasConveyanceCode.setFont(new java.awt.Font("Dialog",
					java.awt.Font.BOLD, 18));
			tfOverseasConveyanceCode.setForeground(java.awt.Color.black);

			tfOverseasConveyanceCode.getDocument().addDocumentListener(
					new DocumentListener() {

						public void insertUpdate(DocumentEvent e) {

							// 境外运输工具是否自动带出境内运输工具编号
							String jwjn = manualDecleareAction.getBpara(
									new Request(CommonVars.getCurrUser()),
									BcusParameter.CUSTOMS_JW_JN);

							if (jwjn != null && "1".equals(jwjn)) {

								tfDomesticConveyanceCode
										.setText(tfOverseasConveyanceCode
												.getText());

							}
						}

						public void removeUpdate(DocumentEvent e) {
							// tfDomesticConveyanceCode.setText(tfOverseasConveyanceCode.getText());
						}

						public void changedUpdate(DocumentEvent e) {
						}
					});
		}
		return tfOverseasConveyanceCode;
	}

	/**
	 * This method initializes tfOverseasConveyanceName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOverseasConveyanceName() {
		if (tfOverseasConveyanceName == null) {
			tfOverseasConveyanceName = new JTextField();
			tfOverseasConveyanceName.setBounds(137, 48, 168, 22);
		}
		return tfOverseasConveyanceName;
	}

	/**
	 * This method initializes tfOverseasConveyanceFlights
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOverseasConveyanceFlights() {
		if (tfOverseasConveyanceFlights == null) {
			tfOverseasConveyanceFlights = new JTextField();
			tfOverseasConveyanceFlights.setBounds(137, 77, 168, 22);
		}
		return tfOverseasConveyanceFlights;
	}

	/**
	 * This method initializes tfOverseasConveyanceBillOfLading
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOverseasConveyanceBillOfLading() {
		if (tfOverseasConveyanceBillOfLading == null) {
			tfOverseasConveyanceBillOfLading = new JTextField();
			tfOverseasConveyanceBillOfLading.setBounds(137, 105, 168, 22);
		}
		return tfOverseasConveyanceBillOfLading;
	}

	/**
	 * This method initializes tfDomesticConveyanceCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDomesticConveyanceCode() {
		if (tfDomesticConveyanceCode == null) {
			tfDomesticConveyanceCode = new JTextField();
			tfDomesticConveyanceCode.setBounds(137, 163, 168, 34);
			tfDomesticConveyanceCode.setDocument(new CustomDocument());
			tfDomesticConveyanceCode.setForeground(java.awt.Color.black);
			tfDomesticConveyanceCode.setFont(new java.awt.Font("Dialog",
					java.awt.Font.BOLD, 18));
		}
		return tfDomesticConveyanceCode;
	}

	/**
	 * This method initializes tfDomesticConveyanceName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDomesticConveyanceName() {
		if (tfDomesticConveyanceName == null) {
			tfDomesticConveyanceName = new JTextField();
			tfDomesticConveyanceName.setBounds(137, 202, 168, 22);
		}
		return tfDomesticConveyanceName;
	}

	/**
	 * This method initializes tfDomesticConveyanceFlights
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDomesticConveyanceFlights() {
		if (tfDomesticConveyanceFlights == null) {
			tfDomesticConveyanceFlights = new JTextField();
			tfDomesticConveyanceFlights.setBounds(137, 231, 168, 22);
		}
		return tfDomesticConveyanceFlights;
	}

	/**
	 * This method initializes cbbTransportMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTransportMode() {
		if (cbbTransportMode == null) {
			cbbTransportMode = new JComboBox();
			cbbTransportMode.setBounds(137, 134, 168, 22);
		}
		return cbbTransportMode;
	}

	/**
	 * 初始化组件
	 */
	private void initUIComponents() {
		cbbTransportMode.setModel(CustomBaseModel.getInstance()
				.getTransfModel());
		cbbTransportMode
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbTransportMode);
		showData();
		if (this.isTruckTransfer == true) {
			this.cbbTransportMode.setSelectedItem(this.getTransf());
		}

	}

	private void setState() {
		// 填写默认值
		jButton.setVisible(true);
		// jButton.setVisible(customsDeclaration.getTransferMode() != null &&
		// (customsDeclaration.getTransferMode().getName().equals("公路运输")
		// || customsDeclaration.getTransferMode().getName().equals("水路运输")
		// || customsDeclaration.getTransferMode().getName().equals("物流中心")
		// || customsDeclaration.getTransferMode().getName().equals("物流园区")
		// || customsDeclaration.getTransferMode().getName().equals("出口加工")
		// ));
	}

	private void showData() {
		if (this.customsDeclaration == null) {
			return;
		}
		if (this.customsDeclaration.getOverseasConveyanceBillOfLading() != null) {
			this.tfOverseasConveyanceBillOfLading
					.setText(this.customsDeclaration
							.getOverseasConveyanceBillOfLading());
		} else {
			this.tfOverseasConveyanceBillOfLading.setText("");
		}
		if (this.customsDeclaration.getOverseasConveyanceCode() != null) {
			this.tfOverseasConveyanceCode.setText(this.customsDeclaration
					.getOverseasConveyanceCode());
		} else {
			this.tfOverseasConveyanceCode.setText("");
		}
		if (this.customsDeclaration.getOverseasConveyanceFlights() != null) {
			this.tfOverseasConveyanceFlights.setText(this.customsDeclaration
					.getOverseasConveyanceFlights());
		} else {
			this.tfOverseasConveyanceFlights.setText("");
		}
		if (this.customsDeclaration.getOverseasConveyanceName() != null) {
			this.tfOverseasConveyanceName.setText(this.customsDeclaration
					.getOverseasConveyanceName());
		} else {
			this.tfOverseasConveyanceName.setText("");
		}
		if (this.customsDeclaration.getDomesticTransferMode() != null) {
			this.cbbTransportMode.setSelectedItem(this.customsDeclaration
					.getDomesticTransferMode());
		} else {
			this.cbbTransportMode.setSelectedIndex(-1);
		}
		if (this.customsDeclaration.getDomesticConveyanceCode() != null) {
			this.tfDomesticConveyanceCode.setText(this.customsDeclaration
					.getDomesticConveyanceCode());
		} else {
			this.tfDomesticConveyanceCode.setText("");
		}
		if (this.customsDeclaration.getDomesticConveyanceName() != null) {
			this.tfDomesticConveyanceName.setText(this.customsDeclaration
					.getDomesticConveyanceName());
		} else {
			this.tfDomesticConveyanceName.setText("");
		}
		if (this.customsDeclaration.getDomesticConveyanceFlights() != null) {
			this.tfDomesticConveyanceFlights.setText(this.customsDeclaration
					.getDomesticConveyanceFlights());
		} else {
			this.tfDomesticConveyanceFlights.setText("");
		}

		// 填写默认值
		if (customsDeclaration.getId() == null) {
			writeDefaultValue();
		}
	}

	private void fillData() {
		if (this.customsDeclaration == null) {
			return;
		}
		this.customsDeclaration
				.setOverseasConveyanceBillOfLading(this.tfOverseasConveyanceBillOfLading
						.getText());
		this.customsDeclaration
				.setOverseasConveyanceCode(this.tfOverseasConveyanceCode
						.getText());
		this.customsDeclaration
				.setOverseasConveyanceFlights(this.tfOverseasConveyanceFlights
						.getText());
		this.customsDeclaration
				.setOverseasConveyanceName(this.tfOverseasConveyanceName
						.getText());
		this.customsDeclaration
				.setDomesticTransferMode((Transf) this.cbbTransportMode
						.getSelectedItem());
		this.customsDeclaration
				.setDomesticConveyanceCode(this.tfDomesticConveyanceCode
						.getText());
		this.customsDeclaration
				.setDomesticConveyanceName(this.tfDomesticConveyanceName
						.getText());
		this.customsDeclaration
				.setDomesticConveyanceFlights(this.tfDomesticConveyanceFlights
						.getText());

	}

	// 判断字符限数
	class CustomDocument extends PlainDocument {
		public void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException {
			if (str == null) {
				return;
			}
			if (super.getLength() >= 10 || str.length() > 10
					|| super.getLength() + str.length() > 10) {
				return;
			}

			super.insertString(offs, str, a);
		}
	}

	/**
	 * @return Returns the isok.
	 */
	public boolean isIsok() {
		return isok;
	}

	/**
	 * @param isok
	 *            The isok to set.
	 */
	public void setIsok(boolean isok) {
		this.isok = isok;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(182, 8, 98, 24);
			jButton.setText("填写默认值");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					writeDefaultValue();
				}
			});
		}
		return jButton;
	}

	private void writeDefaultValue() {
		String homeCard = numberPlate;// 提运单号
		MotorCode motor = materialManageAction.findMotorCodeByCode(new Request(
				CommonVars.getCurrUser()), homeCard);
		if (customsDeclaration.getTransferMode() == null) {
			return;
		}

		if (customsDeclaration.getTransferMode().getName().trim()
				.equals("水路运输")) {
			if (motor != null) {
				tfDomesticConveyanceCode.setText(motor.getComplex());
				tfDomesticConveyanceName.setText(numberPlate);
			}
		} else {
			if (motor == null) {
				tfOverseasConveyanceName.setText(numberPlate);
				tfOverseasConveyanceBillOfLading.setText(numberPlate);
				tfDomesticConveyanceName.setText(numberPlate);
			} else {
				tfOverseasConveyanceCode.setText(motor.getComplex());
				tfOverseasConveyanceName.setText(motor.getHongkongCard());
				tfOverseasConveyanceBillOfLading.setText(motor.getHomeCard());
				tfDomesticConveyanceCode.setText(motor.getComplex());
				tfDomesticConveyanceName.setText(numberPlate);
			}

		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
