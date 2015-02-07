package com.bestway.bcus.client.enc;

import java.util.Hashtable;

import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ImportApplyCustomsProperty;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

/**
 * @author fhz
 * 
 */
public class DgParameterSet extends JDialogBase {

	private JPanel jContentPane = null;

	private JButton btSave = null;

	private JButton btExit = null;

	private JButton btEdit = null;

	public Hashtable hs = null;

	private boolean isOk = true;

	private EncAction encAction = null;

	private ImportApplyCustomsProperty importApplyProperty;//参数设置实体类 

	private int state = DataState.BROWSE;

	private JPanel jPanel = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbImpExpTypeCodeName = null;// 进出口类型

	private JLabel jLabel4 = null;

	private JComboBox cbbTradeModeCodeName = null;// 监管方式

	private JLabel jLabel14 = null;

	private JComboBox cbbDeclareCustomCodeName = null;// 申报地海关

	private JLabel jLabel15 = null;

	private JComboBox cbbTransportModeCodeName = null;// 运输方式

	private JLabel jLabel18 = null;

	private JLabel jLabel211 = null;

	private JComboBox cbbDeclareCompanyCodeName = null;// 申报单位

	private JComboBox cbbEntrancePortCodeName = null;// 进出口岸

	private JPanel jPanel1 = null;

	private JLabel jLabel101 = null;

	private JComboBox cbbBeforeCurrencyCodeName = null;// 币别

	private JLabel jLabel1018 = null;

	private JLabel jLabel1019 = null;

	private JLabel jLabel10110 = null;

	private JLabel jLabel10115 = null;

	private JComboBox cbbBeforeSalesCountryCodeName = null;// 产销国(地区)

	private JComboBox cbbBeforeLevyModelCodeName = null;// 征免方式

	private JComboBox cbbBeforeUsessCodeName = null;// 用途

	private JComboBox cbbBeforeProjectDeptCodeName = null;// 事业部

	/**
	 * This is the default constructor
	 */
	public DgParameterSet() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(532, 345);
		this.setContentPane(getJContentPane());
		this.setTitle("报关清单导入参数设置");
		

	}

	public void setVisible(boolean b){
		if(b){
			showData();
			setState();
		}
		super.setVisible(b);
	}
	
	private void setState() {
		cbbImpExpTypeCodeName.setEnabled(state == DataState.EDIT);
		cbbDeclareCompanyCodeName.setEnabled(state == DataState.EDIT);
		cbbDeclareCustomCodeName.setEnabled(state == DataState.EDIT);
		cbbEntrancePortCodeName.setEnabled(state == DataState.EDIT);
		cbbTransportModeCodeName.setEnabled(state == DataState.EDIT);
		cbbTradeModeCodeName.setEnabled(state == DataState.EDIT);

		cbbBeforeProjectDeptCodeName.setEnabled(state == DataState.EDIT);
		cbbBeforeLevyModelCodeName.setEnabled(state == DataState.EDIT);
		cbbBeforeCurrencyCodeName.setEnabled(state == DataState.EDIT);
		cbbBeforeUsessCodeName.setEnabled(state == DataState.EDIT);
		cbbBeforeSalesCountryCodeName.setEnabled(state == DataState.EDIT);

		btSave.setEnabled(state == DataState.EDIT);
		btEdit.setEnabled(state != DataState.EDIT);

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getBtSave(), null);
			jContentPane.add(getBtExit(), null);
			jContentPane.add(getBtEdit(), null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtSave() {
		if (btSave == null) {
			btSave = new JButton();
			btSave.setBounds(new Rectangle(219, 276, 81, 25));
			btSave.setText("保存");
			btSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillData();
					importApplyProperty = encAction
							.saveImportApplyToCustomsBillProperty(new Request(
									CommonVars.getCurrUser()),
									importApplyProperty);
					state = DataState.BROWSE;
					setState();
					JOptionPane.showMessageDialog(DgParameterSet.this, "保存完毕！",
							"提示", 2);
				}
			});
		}
		return btSave;
	}

	/**
	 * 从各个JComboBox中填充ImportApplyToCustomsBillProperty
	 */
	private void fillData() {
		// ----------头
//		importApplyProperty.setCbbImpExpTypeCodeName(cbbImpExpTypeCodeName
//				.getSelectedIndex());// 进出口类型
		importApplyProperty
				.setCbbDeclareCustomCodeName(cbbDeclareCustomCodeName
						.getSelectedIndex());// 申报地海关
		importApplyProperty
				.setCbbTransportModeCodeName(cbbTransportModeCodeName
						.getSelectedIndex());// 运输方式
		importApplyProperty.setCbbDeclareCompanyCodeName(cbbDeclareCompanyCodeName
				.getSelectedIndex());// 申报单位
		importApplyProperty.setCbbEntrancePortCodeName(cbbEntrancePortCodeName
				.getSelectedIndex());// 进出口岸
		importApplyProperty.setCbbTradeModeCodeName(cbbTradeModeCodeName
				.getSelectedIndex());// 监管方式

		// ----------归并前
		importApplyProperty
				.setCbbBeforeCurrencyCodeName(cbbBeforeCurrencyCodeName
						.getSelectedIndex());// 币别
		importApplyProperty
				.setCbbBeforeSalesCountryCodeName(cbbBeforeSalesCountryCodeName
						.getSelectedIndex());// 产销国(地区)
		importApplyProperty
				.setCbbBeforeLevyModelCodeName(cbbBeforeLevyModelCodeName
						.getSelectedIndex());// 征免方式
		importApplyProperty.setCbbBeforeUsessCodeName(cbbBeforeUsessCodeName
				.getSelectedIndex());// 用途
		importApplyProperty
				.setCbbBeforeProjectDeptCodeName(cbbBeforeProjectDeptCodeName
						.getSelectedIndex());// 事业部
	}

	/**
	 * 读取ImportApplyToCustomsBillProperty,给各个JComboBox
	 */
	private void showData() {
		// ----------头
//		cbbImpExpTypeCodeName.setSelectedIndex(importApplyProperty
//				.getCbbImpExpTypeCodeName());// 进出口类型
		cbbDeclareCustomCodeName.setSelectedIndex(importApplyProperty
				.getCbbDeclareCustomCodeName());// 申报地海关
		cbbTransportModeCodeName.setSelectedIndex(importApplyProperty
				.getCbbTransportModeCodeName());// 运输方式
		cbbDeclareCompanyCodeName.setSelectedIndex(importApplyProperty
				.getCbbDeclareCompanyCodeName());// 申报单位
		cbbEntrancePortCodeName.setSelectedIndex(importApplyProperty
				.getCbbEntrancePortCodeName());// 进出口岸
		cbbTradeModeCodeName.setSelectedIndex(importApplyProperty
				.getCbbTradeModeCodeName());// 监管方式

		// ----------归并前
		cbbBeforeCurrencyCodeName.setSelectedIndex(importApplyProperty
				.getCbbBeforeCurrencyCodeName());// 币别
		cbbBeforeSalesCountryCodeName.setSelectedIndex(importApplyProperty
				.getCbbBeforeSalesCountryCodeName());// 产销国(地区)
		cbbBeforeLevyModelCodeName.setSelectedIndex(importApplyProperty
				.getCbbBeforeLevyModelCodeName());// 征免方式
		cbbBeforeUsessCodeName.setSelectedIndex(importApplyProperty
				.getCbbBeforeUsessCodeName());// 用途
		cbbBeforeProjectDeptCodeName.setSelectedIndex(importApplyProperty
				.getCbbBeforeProjectDeptCodeName());// 事业部
	}

	/**
	 * This method initializes jButton1
	 */
	private JButton getBtExit() {
		if (btExit == null) {
			btExit = new JButton();
			btExit.setBounds(new Rectangle(369, 276, 81, 25));
			btExit.setText("退出");
			btExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgParameterSet.this.dispose();
				}
			});
		}
		return btExit;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public void setHs(Hashtable hs) {
		this.hs = hs;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtEdit() {
		if (btEdit == null) {
			btEdit = new JButton();
			btEdit.setBounds(new Rectangle(69, 276, 81, 25));
			btEdit.setText("修改");
			btEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					state = DataState.EDIT;
					setState();
				}
			});
		}
		return btEdit;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel211 = new JLabel();
			jLabel211.setBounds(new Rectangle(255, 46, 117, 21));
			jLabel211.setText("进/出口岸");
			jLabel18 = new JLabel();
			jLabel18.setBounds(new Rectangle(255, 20, 117, 21));
			jLabel18.setText("申报单位");
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(18, 72, 117, 21));
			jLabel15.setText("运输方式");
			jLabel14 = new JLabel();
			jLabel14.setBounds(new Rectangle(18, 46, 117, 21));
			jLabel14.setText("申报地海关");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(17, 20, 117, 21));
			jLabel4.setText("监管方式");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(255, 72, 117, 21));
			jLabel3.setText("进出口类型");
			jLabel3.setVisible(false);
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(12, 10, 497, 114));
			jPanel.setBorder(BorderFactory.createTitledBorder(null, "单头资料设置",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel.add(jLabel3, null);
			jPanel.add(getCbbImpExpTypeCodeName(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getCbbTradeModeCodeName(), null);
			jPanel.add(jLabel14, null);
			jPanel.add(getCbbDeclareCustomCodeName(), null);
			jPanel.add(jLabel15, null);
			jPanel.add(getCbbTransportModeCodeName(), null);
			jPanel.add(jLabel18, null);
			jPanel.add(jLabel211, null);
			jPanel.add(getCbbDeclareCompanyCodeName(), null);
			jPanel.add(getCbbEntrancePortCodeName(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes cbbImpExpType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpTypeCodeName() {
		if (cbbImpExpTypeCodeName == null) {
			cbbImpExpTypeCodeName = new JComboBox();
			cbbImpExpTypeCodeName.addItem("代码");
			cbbImpExpTypeCodeName.addItem("名称");
			cbbImpExpTypeCodeName.setSelectedIndex(0);
			cbbImpExpTypeCodeName.setBounds(new java.awt.Rectangle(374, 72, 106, 21));
			cbbImpExpTypeCodeName.setVisible(false);
		}
		return cbbImpExpTypeCodeName;
	}

	/**
	 * This method initializes cbbTradeMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTradeModeCodeName() {
		if (cbbTradeModeCodeName == null) {
			cbbTradeModeCodeName = new JComboBox();
			cbbTradeModeCodeName.addItem("代码");
			cbbTradeModeCodeName.addItem("名称");
			cbbTradeModeCodeName.setSelectedIndex(0);
			cbbTradeModeCodeName.setBounds(new Rectangle(135, 20,
					106, 21));
		}
		return cbbTradeModeCodeName;
	}

	/**
	 * This method initializes cbbDeclareCustom
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDeclareCustomCodeName() {
		if (cbbDeclareCustomCodeName == null) {
			cbbDeclareCustomCodeName = new JComboBox();
			cbbDeclareCustomCodeName.addItem("代码");
			cbbDeclareCustomCodeName.addItem("名称");
			cbbDeclareCustomCodeName.setSelectedIndex(0);
			cbbDeclareCustomCodeName.setBounds(new Rectangle(136, 46, 106, 21));
		}
		return cbbDeclareCustomCodeName;
	}

	/**
	 * This method initializes cbbTransportMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTransportModeCodeName() {
		if (cbbTransportModeCodeName == null) {
			cbbTransportModeCodeName = new JComboBox();
			cbbTransportModeCodeName.addItem("代码");
			cbbTransportModeCodeName.addItem("名称");
			cbbTransportModeCodeName.setSelectedIndex(0);
			cbbTransportModeCodeName.setBounds(new Rectangle(136, 72, 106, 21));
		}
		return cbbTransportModeCodeName;
	}

	/**
	 * This method initializes cbbDeclareFirm
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDeclareCompanyCodeName() {
		if (cbbDeclareCompanyCodeName == null) {
			cbbDeclareCompanyCodeName = new JComboBox();
			cbbDeclareCompanyCodeName.addItem("代码");
			cbbDeclareCompanyCodeName.addItem("名称");
			cbbDeclareCompanyCodeName.setSelectedIndex(0);
			cbbDeclareCompanyCodeName.setBounds(new Rectangle(374, 20, 106, 21));
		}
		return cbbDeclareCompanyCodeName;
	}

	/**
	 * This method initializes cbbEntrancePort
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEntrancePortCodeName() {
		if (cbbEntrancePortCodeName == null) {
			cbbEntrancePortCodeName = new JComboBox();
			cbbEntrancePortCodeName.addItem("代码");
			cbbEntrancePortCodeName.addItem("名称");
			cbbEntrancePortCodeName.setSelectedIndex(0);
			cbbEntrancePortCodeName.setBounds(new Rectangle(374, 46, 106, 21));
		}
		return cbbEntrancePortCodeName;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel10115 = new JLabel();
			jLabel10115.setBounds(new Rectangle(17, 22, 117, 21));
			jLabel10115.setText("事业部");
			jLabel10110 = new JLabel();
			jLabel10110.setBounds(new Rectangle(253, 47, 117, 21));
			jLabel10110.setText("用途");
			jLabel1019 = new JLabel();
			jLabel1019.setBounds(new Rectangle(253, 23, 117, 21));
			jLabel1019.setText("征免方式");
			jLabel1018 = new JLabel();
			jLabel1018.setBounds(new Rectangle(18, 74, 117, 21));
			jLabel1018.setText("产销国(地区)");
			jLabel101 = new JLabel();
			jLabel101.setBounds(new Rectangle(17, 47, 117, 22));
			jLabel101.setText("币别");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(14, 136, 497, 114));
			jPanel1.setBorder(BorderFactory.createTitledBorder(null, "归并前资料设置",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel1.add(jLabel101, null);
			jPanel1.add(getCbbBeforeCurrencyCodeName(), null);
			jPanel1.add(jLabel1018, null);
			jPanel1.add(jLabel1019, null);
			jPanel1.add(jLabel10110, null);
			jPanel1.add(jLabel10115, null);
			jPanel1.add(getCbbBeforeSalesCountryCodeName(), null);
			jPanel1.add(getCbbBeforeLevyModelCodeName(), null);
			jPanel1.add(getCbbBeforeUsessCodeName(), null);
			jPanel1.add(getCbbBeforeProjectDeptCodeName(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes cbbBeforeCurrency
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeCurrencyCodeName() {
		if (cbbBeforeCurrencyCodeName == null) {
			cbbBeforeCurrencyCodeName = new JComboBox();
			cbbBeforeCurrencyCodeName.addItem("代码");
			cbbBeforeCurrencyCodeName.addItem("名称");
			cbbBeforeCurrencyCodeName.setSelectedIndex(0);
			cbbBeforeCurrencyCodeName
					.setBounds(new Rectangle(136, 47, 106, 21));
		}
		return cbbBeforeCurrencyCodeName;
	}

	/**
	 * This method initializes cbbBeforeSalesCountry
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeSalesCountryCodeName() {
		if (cbbBeforeSalesCountryCodeName == null) {
			cbbBeforeSalesCountryCodeName = new JComboBox();
			cbbBeforeSalesCountryCodeName.addItem("代码");
			cbbBeforeSalesCountryCodeName.addItem("名称");
			cbbBeforeSalesCountryCodeName.setSelectedIndex(0);
			cbbBeforeSalesCountryCodeName.setBounds(new Rectangle(136, 74, 106,
					21));
		}
		return cbbBeforeSalesCountryCodeName;
	}

	/**
	 * This method initializes cbbBeforeLevyModel
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeLevyModelCodeName() {
		if (cbbBeforeLevyModelCodeName == null) {
			cbbBeforeLevyModelCodeName = new JComboBox();
			cbbBeforeLevyModelCodeName.addItem("代码");
			cbbBeforeLevyModelCodeName.addItem("名称");
			cbbBeforeLevyModelCodeName.setSelectedIndex(0);
			cbbBeforeLevyModelCodeName
					.setBounds(new Rectangle(371, 23, 106, 21));
		}
		return cbbBeforeLevyModelCodeName;
	}

	/**
	 * This method initializes cbbBeforeUsess
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeUsessCodeName() {
		if (cbbBeforeUsessCodeName == null) {
			cbbBeforeUsessCodeName = new JComboBox();
			cbbBeforeUsessCodeName.addItem("代码");
			cbbBeforeUsessCodeName.addItem("名称");
			cbbBeforeUsessCodeName.setSelectedIndex(0);
			cbbBeforeUsessCodeName.setBounds(new Rectangle(371, 47, 106, 21));
		}
		return cbbBeforeUsessCodeName;
	}

	/**
	 * This method initializes cbbBeforeProjectDept
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeProjectDeptCodeName() {
		if (cbbBeforeProjectDeptCodeName == null) {
			cbbBeforeProjectDeptCodeName = new JComboBox();
			cbbBeforeProjectDeptCodeName.addItem("代码");
			cbbBeforeProjectDeptCodeName.addItem("名称");
			cbbBeforeProjectDeptCodeName.setSelectedIndex(0);
			cbbBeforeProjectDeptCodeName.setBounds(new Rectangle(135, 22, 106,
					21));
		}
		return cbbBeforeProjectDeptCodeName;
	}

	/**
	 * @return the importApplyProperty
	 */
	public ImportApplyCustomsProperty getImportApplyProperty() {
		return importApplyProperty;
	}

	/**
	 * @param importApplyProperty
	 *            the importApplyProperty to set
	 */
	public void setImportApplyProperty(
			ImportApplyCustomsProperty importApplyProperty) {
		this.importApplyProperty = importApplyProperty;
	}

}
