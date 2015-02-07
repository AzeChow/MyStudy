package com.bestway.common.client.materialbase;

import java.awt.Dialog;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgUpdateMaterialBomWaste extends JDialogBase {

	private JPanel jContentPane = null;

	private JCheckBox cbAll = null;

	private JLabel jLabel = null;

	private JFormattedTextField tfWaste = null;

	private JLabel jLabel1 = null;

	private JComboBox cbbVersion = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private Integer version = null;

	private Double waste = null;

	private boolean isByUnitUsed = true;

	private MaterialManageAction materialManageAction = null;

	private boolean isOk = false;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private JPanel jPanel = null;

	private JRadioButton rbIsByUnitUsed = null;

	private JRadioButton rbIsByUnitWaste = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="354,68"

	private JComboBox cbbPFourCode = null;

	private JComboBox cbbMFourCode = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private String pFourCode;

	private String mFourCode;

	public String getMFourCode() {
		return mFourCode;
	}

	public void setMFourCode(String fourCode) {
		mFourCode = fourCode;
	}

	public String getPFourCode() {
		return pFourCode;
	}

	public void setPFourCode(String fourCode) {
		pFourCode = fourCode;
	}

	public boolean isByUnitUsed() {
		return isByUnitUsed;
	}

	public void setByUnitUsed(boolean isByUnitUsed) {
		this.isByUnitUsed = isByUnitUsed;
	}

	public Double getWaste() {
		return waste;
	}

	public void setWaste(Double waste) {
		this.waste = waste;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public DgUpdateMaterialBomWaste() {
		super();
		initialize();
	}

	public DgUpdateMaterialBomWaste(boolean isModal) {
		super(isModal);
		initialize();
	}

	public DgUpdateMaterialBomWaste(JFrame owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	public DgUpdateMaterialBomWaste(Dialog owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(313, 289));
		this.setTitle("批量更新损耗率");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.getButtonGroup();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(43, 79, 51, 24));
			jLabel3.setText("料件4码");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(43, 52, 49, 18));
			jLabel2.setText("成品4码");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(43, 109, 36, 23));
			jLabel1.setText("版本");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(43, 26, 50, 23));
			jLabel.setText("损耗率");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getCbAll(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfWaste(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getCbbVersion(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getCbbPFourCode(), null);
			jContentPane.add(getCbbMFourCode(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbAll() {
		if (cbAll == null) {
			cbAll = new JCheckBox();
			cbAll.setBounds(new java.awt.Rectangle(205, 108, 51, 28));
			cbAll.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
			cbAll.setText("全部");
			cbAll.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						cbbVersion.setSelectedIndex(-1);
						cbbVersion.setEnabled(false);
					} else if (e.getStateChange() == java.awt.event.ItemEvent.DESELECTED) {
						cbbVersion.setSelectedIndex(-1);
						cbbVersion.setEnabled(true);
					}
				}
			});
		}
		return cbAll;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfWaste() {
		if (tfWaste == null) {
			tfWaste = new JFormattedTextField();
			tfWaste.setBounds(new java.awt.Rectangle(95, 26, 163, 24));
			tfWaste.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfWaste;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbVersion() {
		if (cbbVersion == null) {
			cbbVersion = new JComboBox();
			cbbVersion.setBounds(new java.awt.Rectangle(95, 109, 107, 23));
		}
		return cbbVersion;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new java.awt.Rectangle(53, 211, 61, 23));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbPFourCode.getSelectedIndex() < 0) {
						JOptionPane.showMessageDialog(
								DgUpdateMaterialBomWaste.this, "请选择成品4码", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					} else {
						pFourCode = cbbPFourCode.getSelectedItem().toString();
					}
					if (cbbMFourCode.getSelectedIndex() < 0) {
						JOptionPane.showMessageDialog(
								DgUpdateMaterialBomWaste.this, "请选择料件4码", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					} else {
						mFourCode = cbbMFourCode.getSelectedItem().toString();
					}
					if (tfWaste.getValue() == null) {
						JOptionPane.showMessageDialog(
								DgUpdateMaterialBomWaste.this, "请输入损耗率!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					waste = Double.valueOf(tfWaste.getValue().toString());
					if (waste >= 1 || waste < 0) {
						JOptionPane.showMessageDialog(
								DgUpdateMaterialBomWaste.this,
								"损耗率必须大于等于0,小于1", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (cbAll.isSelected()) {
						version = null;
					} else {
						if (cbbVersion.getSelectedIndex() < 0) {
							JOptionPane.showMessageDialog(
									DgUpdateMaterialBomWaste.this,
									"请选择你要更新损耗率的版本号", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						} else {
							version = Integer.valueOf(cbbVersion
									.getSelectedItem().toString());
						}
					}
					if (rbIsByUnitUsed.isSelected()) {
						isByUnitUsed = true;
					} else {
						isByUnitUsed = false;
					}
					isOk = true;
					dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new java.awt.Rectangle(195, 212, 61, 23));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	private void initUIComponents() {
		List list = materialManageAction
				.findInnerMergeFourCodeByMaterialType(new Request(CommonVars
						.getCurrUser(), true),MaterielType.FINISHED_PRODUCT);
		cbbPFourCode.setModel(new DefaultComboBoxModel(list.toArray()));
		cbbPFourCode.setSelectedIndex(-1);

		list = materialManageAction.findInnerMergeFourCodeByMaterialType(new Request(
				CommonVars.getCurrUser(), true),MaterielType.MATERIEL);
		cbbMFourCode.setModel(new DefaultComboBoxModel(list.toArray()));
		cbbMFourCode.setSelectedIndex(-1);

		list = materialManageAction.findExistMaterialBomVersion(new Request(
				CommonVars.getCurrUser(), true));
		cbbVersion.setModel(new DefaultComboBoxModel(list.toArray()));
		cbbVersion.setSelectedIndex(-1);
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
			defaultFormatterFactory.setNullFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
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
			jPanel.setBounds(new java.awt.Rectangle(36,141,227,51));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel.add(getRbIsByUnitUsed(), null);
			jPanel.add(getRbIsByUnitWaste(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbIsByUnitUsed() {
		if (rbIsByUnitUsed == null) {
			rbIsByUnitUsed = new JRadioButton();
			rbIsByUnitUsed.setBounds(new java.awt.Rectangle(6, 5, 204, 19));
			rbIsByUnitUsed.setSelected(true);
			rbIsByUnitUsed.setText("根据单项用量和损耗率反算单耗");
		}
		return rbIsByUnitUsed;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbIsByUnitWaste() {
		if (rbIsByUnitWaste == null) {
			rbIsByUnitWaste = new JRadioButton();
			rbIsByUnitWaste.setBounds(new java.awt.Rectangle(6, 27, 201, 20));
			rbIsByUnitWaste.setText("根据单耗和损耗率反算单项用量");
		}
		return rbIsByUnitWaste;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.rbIsByUnitUsed);
			buttonGroup.add(this.rbIsByUnitWaste);
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbPFourCode() {
		if (cbbPFourCode == null) {
			cbbPFourCode = new JComboBox();
			cbbPFourCode.setBounds(new java.awt.Rectangle(95, 53, 161, 24));
		}
		return cbbPFourCode;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMFourCode() {
		if (cbbMFourCode == null) {
			cbbMFourCode = new JComboBox();
			cbbMFourCode.setBounds(new java.awt.Rectangle(95, 82, 161, 22));
		}
		return cbbMFourCode;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
