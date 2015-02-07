package com.bestway.dzsc.client.materialapply;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.dzsc.materialapply.action.MaterialApplyAction;
import com.bestway.dzsc.materialapply.entity.MaterialBomVersionApply;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgApplyBomVersion extends JDialogBase {

	private JButton btnOk = null;

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JCustomFormattedTextField tfBomVersion = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton btnCancel = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private MaterialApplyAction materialApplyAction = null;

	private boolean isOk = false;
	
	private boolean isChange=false;

	// private MaterialBomMaster master = null;

	private MaterialBomVersionApply version = null;

	private int dataState = DataState.BROWSE;

	// public MaterialBomMaster getMaster() {
	// return master;
	// }
	//
	// public void setMaster(MaterialBomMaster master) {
	// this.master = master;
	// }

	public boolean isChange() {
		return isChange;
	}

	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dateState) {
		this.dataState = dateState;
	}

	public MaterialBomVersionApply getVersion() {
		return version;
	}

	public void setVersion(MaterialBomVersionApply version) {
		this.version = version;
	}

	public boolean isOk() {
		return isOk;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgApplyBomVersion() {
		super();
		initialize();
		materialApplyAction = (MaterialApplyAction) CommonVars
				.getApplicationContext().getBean("materialApplyAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			this.showData(version);
			setState();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(328, 202));
		this.setTitle("BOM版本编辑");
		this.setContentPane(getJContentPane());

	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new java.awt.Rectangle(40, 124, 81, 24));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (dataState == DataState.ADD) {
					 if (tfBomVersion.getValue() == null) {
						JOptionPane.showMessageDialog(DgApplyBomVersion.this,
								"请输入版本号", "提示", JOptionPane.OK_OPTION);
						return;
					}
					// List list = materialManageAction
					// .findMaterielBomVersionByVersion(new Request(
					// CommonVars.getCurrUser()), master,
					// Integer.parseInt(tfBomVersion
					// .getValue().toString()));
					// if (list.size() > 0) {
					// JOptionPane
					// .showMessageDialog(DgApplyBomVersion.this,
					// "此版本号'"
					// + tfBomVersion.getValue()
					// .toString()
					// + "'已经存在，请重新输入", "提示",
					// JOptionPane.OK_OPTION);
					// return;
					// }
					// version = new MaterialBomVersion();
					// version.setMaster(master);
					// }
					fillData(version);
					// if (dataState == DataState.EDIT) {
					version=materialApplyAction
							.saveMaterialBomVersionApply(new Request(CommonVars
									.getCurrUser()), version,isChange);
					// } else if (dataState == DataState.CHANGE) {
					// version = materialApplyAction.changeBomVersion(
					// new Request(CommonVars.getCurrUser()), version);
					// }
					isOk = true;
					dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(41, 83, 84, 18));
			jLabel2.setText("结束有效日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(41, 55, 84, 22));
			jLabel1.setText("开始有效日期");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(41, 30, 84, 21));
			jLabel.setText("BOM版本号 ");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfBomVersion(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getCbbBeginDate(), null);
			jContentPane.add(getCbbEndDate(), null);
			jContentPane.add(getBtnCancel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfBomVersion() {
		if (tfBomVersion == null) {
			tfBomVersion = new JCustomFormattedTextField();
			tfBomVersion.setBounds(new java.awt.Rectangle(130, 30, 141, 22));
			tfBomVersion.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfBomVersion;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new java.awt.Rectangle(130, 55, 141, 23));
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
			cbbEndDate.setBounds(new java.awt.Rectangle(130, 83, 141, 21));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new java.awt.Rectangle(184, 124, 81, 24));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOk = false;
					dispose();
				}
			});
		}
		return btnCancel;
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

	private void showData(MaterialBomVersionApply version) {
		if (version == null) {
			return;
		}
		this.tfBomVersion.setValue(version.getVersion());
		this.cbbBeginDate.setDate(version.getBeginDate());
		this.cbbEndDate.setDate(version.getEndDate());
	}

	private void fillData(MaterialBomVersionApply version) {
		version.setVersion(this.tfBomVersion.getValue() == null ? 0 : Integer
				.parseInt(this.tfBomVersion.getValue().toString()));
		version.setBeginDate(this.cbbBeginDate.getDate());
		version.setEndDate(this.cbbEndDate.getDate());
	}

	private void setState() {
		this.tfBomVersion.setEnabled(dataState == DataState.ADD);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
