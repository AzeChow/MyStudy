package com.bestway.common.client.materialbase;

import java.awt.Dialog;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgVersionSelected extends JDialogBase {

	private JPanel jContentPane = null;

	private JRadioButton rbNewVersion = null;

	private JRadioButton rbAppendedVersion = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JComboBox cbbVersion = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="474,60"

	private JLabel jLabel1 = null;

	private JTextField tfVersion = null;

	private MaterialManageAction materialManageAction = null;

	private int bomStructureType;

	private Integer version = null;  //  @jve:decl-index=0:

	public int getBomStructureType() {
		return bomStructureType;
	}

	public void setBomStructureType(int bomStructureType) {
		this.bomStructureType = bomStructureType;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public DgVersionSelected() {
		super();
		initialize();
	}

	public DgVersionSelected(boolean isModal) {
		super(isModal);
		initialize();
	}

	public DgVersionSelected(JFrame owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	public DgVersionSelected(Dialog owner, boolean isModal) {
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
		this.setSize(new java.awt.Dimension(425, 255));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("选择版本 ");
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
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(49, 124, 171, 26));
			jLabel1.setText("你选择的或生成新的版本号为：");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(49, 83, 170, 26));
			jLabel.setText("请选择要增加到的版本");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getCbbVersion(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfVersion(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbNewVersion() {
		if (rbNewVersion == null) {
			rbNewVersion = new JRadioButton();
			rbNewVersion.setBounds(new java.awt.Rectangle(2, 6, 143, 29));
			rbNewVersion.setText("展多套版本");
			rbNewVersion.setSelected(true);
			rbNewVersion.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() != java.awt.event.ItemEvent.SELECTED) {
						return;
					}
					if (rbAppendedVersion.isSelected()) {
						cbbVersion.setEnabled(true);
						cbbVersion.setSelectedIndex(-1);
						tfVersion.setText("");
					} else {
						cbbVersion.setSelectedIndex(-1);
						cbbVersion.setEnabled(false);
						Integer maxVersion = materialManageAction
								.findMaxMaterialBomVersion(new Request(
										CommonVars.getCurrUser(), true));
						tfVersion.setText(maxVersion.toString());
					}
				}
			});
		}
		return rbNewVersion;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAppendedVersion() {
		if (rbAppendedVersion == null) {
			rbAppendedVersion = new JRadioButton();
			rbAppendedVersion
					.setBounds(new java.awt.Rectangle(164, 6, 140, 29));
			rbAppendedVersion.setSelected(false);
			rbAppendedVersion.setText("展阶版本");
			rbAppendedVersion
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (e.getStateChange() != java.awt.event.ItemEvent.SELECTED) {
								return;
							}
							if (rbAppendedVersion.isSelected()) {
								cbbVersion.setEnabled(true);
								cbbVersion.setSelectedIndex(-1);
								tfVersion.setText("");
							} else {
								cbbVersion.setSelectedIndex(-1);
								cbbVersion.setEnabled(false);
								Integer maxVersion = materialManageAction
										.findMaxMaterialBomVersion(new Request(
												CommonVars.getCurrUser(), true));
								tfVersion.setText(maxVersion.toString());
							}
						}
					});
		}
		return rbAppendedVersion;
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
			jPanel.setBounds(new java.awt.Rectangle(49, 35, 325, 39));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel.add(getRbNewVersion(), null);
			jPanel.add(getRbAppendedVersion(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbVersion() {
		if (cbbVersion == null) {
			cbbVersion = new JComboBox();
			cbbVersion.setBounds(new java.awt.Rectangle(218, 85, 157, 23));
			cbbVersion.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						tfVersion.setText(cbbVersion.getSelectedItem().toString());
					}
				}
			});
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
			btnOk.setBounds(new java.awt.Rectangle(63, 174, 92, 27));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if ("".equals(tfVersion.getText().trim())) {
						JOptionPane.showMessageDialog(DgVersionSelected.this,
								"你没有选择版本号", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					version = Integer.valueOf(tfVersion.getText().trim());
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
			btnCancel.setBounds(new java.awt.Rectangle(266, 174, 92, 27));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.rbAppendedVersion);
			buttonGroup.add(this.rbNewVersion);
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfVersion() {
		if (tfVersion == null) {
			tfVersion = new JTextField();
			tfVersion.setBounds(new java.awt.Rectangle(218, 124, 157, 25));
			tfVersion.setEditable(false);
		}
		return tfVersion;
	}
	
	private void initUIComponents(){
		List list= materialManageAction
		.findExistMaterialBomVersion(new Request(
				CommonVars.getCurrUser(), true));
		cbbVersion.setModel(new DefaultComboBoxModel(list.toArray()));
		
		cbbVersion.setSelectedIndex(-1);
		cbbVersion.setEnabled(false);
		Integer maxVersion = materialManageAction
				.findMaxMaterialBomVersion(new Request(
						CommonVars.getCurrUser(), true));
		tfVersion.setText(maxVersion.toString());
	}

} // @jve:decl-index=0:visual-constraint="10,10"
