
package com.bestway.common.client.materialbase;

import java.util.List;

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
import com.bestway.common.materialbase.entity.TempEntBomMaster;
import com.bestway.common.materialbase.entity.TempEntBomVersion;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JTextField;

public class DgEnterpriseBomVersion extends JDialogBase {

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

	private MaterialManageAction materialManageAction = null;

	private boolean isOk = false;

	private TempEntBomMaster master = null;

	private TempEntBomVersion version = null;

	private int dataState = DataState.BROWSE;

	private JLabel jLabel3 = null;

	private JTextField tfNote = null;

	public TempEntBomMaster getMaster() {
		return master;
	}

	public void setMaster(TempEntBomMaster master) {
		this.master = master;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dateState) {
		this.dataState = dateState;
	}

	public TempEntBomVersion getVersion() {
		return version;
	}

	public void setVersion(TempEntBomVersion version) {
		this.version = version;
	}

	public boolean isOk() {
		return isOk;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgEnterpriseBomVersion() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
	}

	@Override
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
		this.setSize(new Dimension(328, 222));
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
			btnOk.setBounds(new Rectangle(41, 156, 81, 24));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (dataState == DataState.ADD) {
						if (tfBomVersion.getValue() == null) {
							JOptionPane.showMessageDialog(
									DgEnterpriseBomVersion.this, "请输入版本号", "提示",
									JOptionPane.OK_OPTION);
							return;
						}
						List list = materialManageAction
								.findEnterpriseBomVersionByVersion(new Request(
										CommonVars.getCurrUser()), master.getPtNo(),
										Double.valueOf(tfBomVersion
												.getValue().toString()).intValue());
						if (list.size() > 0) {
							JOptionPane
									.showMessageDialog(DgEnterpriseBomVersion.this,
											"此版本号'"
													+ tfBomVersion.getValue()
															.toString()
													+ "'已经存在，请重新输入", "提示",
											JOptionPane.OK_OPTION);
							return;
						}
						version = new TempEntBomVersion();
						fillData(version);
						materialManageAction.addEnterpriseBomVersion(
								new Request(CommonVars.getCurrUser()), master,version);
					}else if(dataState == DataState.EDIT){
						fillData(version);
						materialManageAction.saveEnterpriseBomVersion(
								new Request(CommonVars.getCurrUser()), master,version);
					}					
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
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(42, 116, 35, 23));
			jLabel3.setText("备注");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(42, 84, 84, 23));
			jLabel2.setText("结束有效日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(42, 56, 84, 23));
			jLabel1.setText("开始有效日期");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(41, 30, 84, 23));
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
			jContentPane.add(jLabel3, null);
			jContentPane.add(getTfNote(), null);
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
			cbbBeginDate.setBounds(new Rectangle(130, 56, 141, 23));
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
			cbbEndDate.setBounds(new Rectangle(130, 84, 141, 23));
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
			btnCancel.setBounds(new Rectangle(185, 156, 81, 24));
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
//			DecimalFormat decimalFormat = new DecimalFormat(); 
//			decimalFormat.setMaximumFractionDigits(0);
			numberFormatter = new NumberFormatter();
//			numberFormatter.setFormat(decimalFormat);
			numberFormatter.setMinimum(0);
		}
		return numberFormatter;
	}

	private void showData(TempEntBomVersion version) {
		if (version == null) {
			return;
		}
		this.tfBomVersion.setValue(version.getVersion());
		this.cbbBeginDate.setDate(version.getBeginDate());
		this.cbbEndDate.setDate(version.getEndDate());
		this.tfNote.setText(version.getNotes());
	}

	private void fillData(TempEntBomVersion version) {
		version.setVersion(this.tfBomVersion.getValue() == null ? 0 : Double
				.valueOf(this.tfBomVersion.getValue().toString()).intValue());
		version.setBeginDate(this.cbbBeginDate.getDate());
		version.setEndDate(this.cbbEndDate.getDate());
		version.setNotes(this.tfNote.getText());
	}

	private void setState() {
		this.tfBomVersion.setEnabled(dataState == DataState.ADD);
	}

	/**
	 * This method initializes tfNote	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextField();
			tfNote.setBounds(new Rectangle(130, 116, 141, 25));
		}
		return tfNote;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
