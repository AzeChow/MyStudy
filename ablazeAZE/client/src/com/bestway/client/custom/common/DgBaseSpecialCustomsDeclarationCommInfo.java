/*
 * Created on 2004-8-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcus.client.common.AutoCalcListener;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.customs.common.action.BCSSpecialCustomsAuthorityAction;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.action.DZSCSpecialCustomsAuthorityAction;
import com.bestway.customs.common.action.SpecialCustomsAuthorityAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.TempAllTypeInnerMergeDate;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class DgBaseSpecialCustomsDeclarationCommInfo extends
		JDialogBase {

	protected JLabel lbTax;

	protected JTextField tfTax;

	protected JPanel jContentPane = null;

	protected JPanel jPanel = null;
	protected JLabel jLabel8 = null;

	protected JTextField tfCommName = null;

	protected JTextField tfCommSpec = null;

	protected JFormattedTextField tfCommAmount = null;

	protected JComboBox cbbProduceCountry = null;

	protected JComboBox cbbUses = null;

	protected JComboBox cbbLevyMode = null;

	protected JButton btnOk = null;

	protected JButton btnCancel = null;

	protected JFormattedTextField tfCommUnitPrice = null;

	protected JFormattedTextField tfCommTotalPrice = null;

	protected JFormattedTextField tfLegalAmount = null;

	protected JFormattedTextField tfSecondLegalAmount = null;

	protected JFormattedTextField tfUnitWeight = null;

	protected DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	protected NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	protected JTextField tfComplexCode = null;

	protected JButton btnComplex = null;

	protected JComboBox cbbUnit = null;

	protected JTextField tfFirstLegalUnit = null;

	protected JTextField tfSecondLegalUnit = null;

	protected JTextField tfEmsNo = null;

	protected JTextField tfMaterielNo = null;

	protected Complex complex = null; // @jve:decl-index=0:

	protected JTableListModel tableModel = null;

	protected BaseCustomsDeclarationCommInfo customsDeclarationCommInfo = null;

	protected BaseEncAction baseEncAction = null; // @jve:decl-index=0:

	protected Unit firstLegalUnit = null; // @jve:decl-index=0:

	protected Unit secondLegalUnit = null; // @jve:decl-index=0:

	protected BaseCustomsDeclaration customsDeclaration = null; // @jve:decl-index=0:

	protected boolean effective = false;

	private JLabel jLabel19 = null;

	private JLabel jLabel20 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	protected int totalCount = 0; // 总行

	protected int rowCount = 0; // 当前行

	protected int dataState = DataState.EDIT;

	private JTextField jTextField = null;

	private SpecialCustomsAuthorityAction specialCustomsAuthorityAction = null;
	private DZSCSpecialCustomsAuthorityAction dZSCSpecialCustomsAuthorityAction = null;
	private BCSSpecialCustomsAuthorityAction bCSSpecialCustomsAuthorityAction = null;

	private JLabel jlbShow = null;

	private Double useAmount = 0.0;// 当为设备时,用来记录可使用数量

	protected int projectType = ProjectType.BCUS;

	private JLabel jLabel21 = null;

	private JComboBox jComboBox = null;

	private MaterialManageAction materialManageAction = null;

	private JLabel jLabel22 = null;

	private JLabel jLabel23 = null;

	private JLabel jLabel24 = null;

	private JCustomFormattedTextField tfNetWeight = null;

	private JCustomFormattedTextField tfGrossWeight = null;

	private JCustomFormattedTextField tfPieses = null;

	private JLabel lbBoxNo = null;

	private JTextField tfBoxNo = null;

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
	 * This method initializes
	 * 
	 */
	public DgBaseSpecialCustomsDeclarationCommInfo() {
		super();
		specialCustomsAuthorityAction = (SpecialCustomsAuthorityAction) CommonVars
				.getApplicationContext().getBean(
						"specialCustomsAuthorityAction");
		dZSCSpecialCustomsAuthorityAction = (DZSCSpecialCustomsAuthorityAction) CommonVars
				.getApplicationContext().getBean(
						"dZSCSpecialCustomsAuthorityAction");
		bCSSpecialCustomsAuthorityAction = (BCSSpecialCustomsAuthorityAction) CommonVars
				.getApplicationContext().getBean(
						"bCSSpecialCustomsAuthorityAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	protected void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("特殊报关单商品信息编辑");
		this.setContentPane(getJContentPane());
		this.setSize(510, 536);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	protected JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel20 = new JLabel();
			jLabel19 = new JLabel();
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jLabel19.setBounds(35, 9, 87, 22);
			jLabel19.setText("当前项号：");
			jLabel19.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel19.setForeground(java.awt.Color.red);
			jLabel20.setBounds(123, 9, 99, 21);
			jLabel20.setText("");
			jLabel20.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel20.setForeground(java.awt.Color.red);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(jLabel19, null);
			jContentPane.add(jLabel20, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJButton2(), null);
			jContentPane.add(getJlbShow(), null);
		}
		return jContentPane;
	}

	private JLabel getJlbShow() {
		if (jlbShow == null) {
			jlbShow = new JLabel();
			jlbShow.setBounds(new Rectangle(14, 430, 481, 34));
			jlbShow.setForeground(java.awt.Color.blue);
			jlbShow.setText("");
		}
		return jlbShow;
	}

	/**
	 * @return Returns the effective.
	 */
	public boolean isEffective() {
		return effective;
	}

	/**
	 * @param effective
	 *            The effective to set.
	 */
	public void setEffective(boolean effective) {
		this.effective = effective;
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
	 * @return Returns the complex.
	 */
	public Complex getComplex() {
		return complex;
	}

	/**
	 * @param complex
	 *            The complex to set.
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
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
	protected JPanel getJPanel() {
		if (jPanel == null) {
			lbBoxNo = new JLabel();
			lbBoxNo.setBounds(new Rectangle(251, 331, 37, 20));
			lbBoxNo.setText("箱号");
			jLabel24 = new JLabel();
			jLabel24.setBounds(new Rectangle(10, 331, 81, 20));
			jLabel24.setText("件数");
			jLabel23 = new JLabel();
			jLabel23.setBounds(new Rectangle(251, 309, 40, 20));
			jLabel23.setText("毛重");
			jLabel22 = new JLabel();
			jLabel22.setBounds(new Rectangle(10, 309, 81, 20));
			jLabel22.setText("净重");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(252, 157, 48, 20));
			jLabel21.setText("事业部");
			javax.swing.JLabel jLabel18 = new JLabel();
			javax.swing.JLabel jLabel17 = new JLabel();
			javax.swing.JLabel jLabel16 = new JLabel();
			javax.swing.JLabel jLabel15 = new JLabel();
			javax.swing.JLabel jLabel13 = new JLabel();
			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel11 = new JLabel();
			javax.swing.JLabel jLabel14 = new JLabel();
			jPanel = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel5 = new JLabel();
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			jLabel8 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel10 = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(10, 36, 482, 356);
			jPanel.setBorder(javax.swing.BorderFactory
					.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel.setBounds(10, 36, 57, 20);
			jLabel.setText("名称");
			jLabel1.setBounds(252, 36, 48, 20);
			jLabel1.setText("手册规格");
			jLabel2.setBounds(10, 109, 57, 20);
			jLabel2.setText("数量");
			jLabel3.setBounds(10, 133, 57, 20);
			jLabel3.setText("单价");
			jLabel4.setBounds(252, 133, 28, 20);
			jLabel4.setText("金额");
			jLabel5.setBounds(10, 235, 81, 20);
			jLabel5.setText("第一法定数量");
			jLabel6.setBounds(10, 260, 81, 20);
			jLabel6.setText("第二法定数量");
			jLabel7.setBounds(198, 157, 45, 22);
			jLabel7.setText("千克");
			jLabel8.setBounds(252, 183, 48, 20);
			jLabel8.setText("原产国");
			jLabel9.setBounds(252, 209, 30, 20);
			jLabel9.setText("用途");
			jLabel10.setBounds(10, 286, 81, 20);
			jLabel10.setText("征减免税方式");
			jLabel14.setBounds(10, 11, 57, 20);
			jLabel14.setText("商品编号");
			jLabel11.setBounds(10, 157, 57, 20);
			jLabel11.setText("单位重量");
			jLabel12.setBounds(252, 233, 79, 20);
			jLabel12.setText("对应合同序号");
			jLabel13.setBounds(252, 260, 43, 20);
			jLabel13.setText("版本号");
			jLabel15.setBounds(252, 286, 39, 20);
			jLabel15.setText("货号");
			jLabel16.setBounds(10, 84, 57, 20);
			jLabel16.setText("单位");
			jLabel17.setBounds(10, 183, 81, 20);
			jLabel17.setText("第一法定单位");
			jLabel18.setBounds(10, 208, 81, 20);
			jLabel18.setText("第二法定单位");
			jPanel.add(jLabel, null);
			jPanel.add(getTfCommName(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getTfCommSpec(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getTfCommAmount(), null);
			jPanel.add(getTfCommUnitPrice(), null);
			jPanel.add(getTfCommTotalPrice(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(jLabel6, null);
			jPanel.add(jLabel7, null);
			jPanel.add(jLabel8, null);
			jPanel.add(getCbbProduceCountry(), null);
			jPanel.add(jLabel9, null);
			jPanel.add(getCbbUses(), null);
			jPanel.add(jLabel10, null);
			jPanel.add(getCbbLevyMode(), null);
			jPanel.add(getTfLegalAmount(), null);
			jPanel.add(getTfSecondLegalAmount(), null);
			jPanel.add(jLabel14, null);
			jPanel.add(getTfComplexCode(), null);
			jPanel.add(getBtnComplex(), null);
			jPanel.add(getCbbUnit(), null);
			jPanel.add(jLabel11, null);
			jPanel.add(jLabel12, null);
			jPanel.add(jLabel13, null);
			jPanel.add(jLabel15, null);
			jPanel.add(jLabel16, null);
			jPanel.add(jLabel17, null);
			jPanel.add(jLabel18, null);
			jPanel.add(getTfFirstLegalUnit(), null);
			jPanel.add(getTfSecondLegalUnit(), null);
			jPanel.add(getTfEmsNo(), null);
			jPanel.add(getTfMaterielNo(), null);
			jPanel.add(getJFormattedTextField(), null);
			jPanel.add(getJTextField(), null);
			jPanel.add(jLabel21, null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(jLabel22, null);
			jPanel.add(jLabel23, null);
			jPanel.add(jLabel24, null);
			jPanel.add(getTfNetWeight(), null);
			jPanel.add(getTfGrossWeight(), null);
			jPanel.add(getTfPieses(), null);
			jPanel.add(lbBoxNo, null);
			jPanel.add(getTfBoxNo(), null);
			jPanel.add(getLblNewLabel());
			jPanel.add(getTfDeclareSpec());

			jPanel.add(getLbTax(), null);

			jPanel.add(getTfTax(), null);

		}
		return jPanel;
	}

	protected JTextField getTfTax() {

		if (tfTax == null) {

			tfTax = new JTextField();

			tfTax.setBounds(303, 109, 115, 21);

		}

		return tfTax;
	}

	protected JLabel getLbTax() {

		if (lbTax == null) {

			lbTax = new JLabel("税金");

			lbTax.setBounds(252, 109, 48, 20);

		}

		return lbTax;
	}

	/**
	 * This method initializes tfCommName
	 * 
	 * @return javax.swing.JTextField
	 */
	protected JTextField getTfCommName() {
		if (tfCommName == null) {
			tfCommName = new JTextField();
			tfCommName.setBounds(84, 36, 160, 20);
			tfCommName.setEditable(true);
		}
		return tfCommName;
	}

	/**
	 * This method initializes tfCommSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	protected JTextField getTfCommSpec() {
		if (tfCommSpec == null) {
			tfCommSpec = new JTextField();
			tfCommSpec.setBounds(303, 36, 160, 20);
			tfCommSpec.setEditable(true);
		}
		return tfCommSpec;
	}

	/**
	 * This method initializes tfCommAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	protected JFormattedTextField getTfCommAmount() {
		if (tfCommAmount == null) {
			tfCommAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfCommAmount.setBounds(84, 109, 160, 20);
			tfCommAmount.addPropertyChangeListener("value",
					new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent evt) {
							tfCommTotalPrice.setValue(getTotalPrice());
							// getFirstAmount();
						}

					});
			// tfCommAmount.addFocusListener(new java.awt.event.FocusAdapter() {
			// public void focusLost(java.awt.event.FocusEvent e) {
			// getTotalPrice();
			// }
			// });
			// CustomFormattedTextFieldUtils.setFormatterFactory(tfCommAmount,
			// 4);
			// tfCommAmount.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfCommAmount;
	}

	/**
	 * This method initializes cbbProduceCountry
	 * 
	 * @return javax.swing.JComboBox
	 */
	protected JComboBox getCbbProduceCountry() {
		if (cbbProduceCountry == null) {
			cbbProduceCountry = new JComboBox();
			cbbProduceCountry.setBounds(303, 183, 160, 20);
		}
		return cbbProduceCountry;
	}

	/**
	 * This method initializes cbbUses
	 * 
	 * @return javax.swing.JComboBox
	 */
	protected JComboBox getCbbUses() {
		if (cbbUses == null) {
			cbbUses = new JComboBox();
			cbbUses.setBounds(303, 209, 160, 20);
		}
		return cbbUses;
	}

	/**
	 * This method initializes cbbLevyMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	protected JComboBox getCbbLevyMode() {
		if (cbbLevyMode == null) {
			cbbLevyMode = new JComboBox();
			cbbLevyMode.setBounds(84, 286, 160, 20);
		}
		return cbbLevyMode;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(209, 400, 59, 24);
			btnOk.setText("保存");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						specialCustomsAuthorityAction
								.saveCommodity(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCSpecialCustomsAuthorityAction
								.saveCommodity(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSSpecialCustomsAuthorityAction
								.saveCommodity(new Request(CommonVars
										.getCurrUser()));
					}
					if (validateIsNull() == true) {
						return;
					}
					if (customsDeclaration.getImpExpType() == ImpExpType.GENERAL_TRADE_IMPORT
							|| customsDeclaration.getImpExpType() == ImpExpType.GENERAL_TRADE_EXPORT) {
						if (tfEmsNo.getText() != null
								&& tfEmsNo.getText().trim().length() > 0) {
							if (JOptionPane.NO_OPTION == JOptionPane
									.showOptionDialog(
											getParent(),
											"特殊报关单-一般贸易进/出报关单,合同序号栏位应为空，是否继续保存？",
											"提示", JOptionPane.YES_NO_OPTION,
											JOptionPane.INFORMATION_MESSAGE,
											null, new Object[] { "是", "否" },
											"否")) {
								return;
							}
						}
					}
					if (dataState == DataState.ADD) {
						BaseCustomsDeclarationCommInfo customsDeclarationCommInfo = newCustomsDeclarationCommInfo();
						fillData(customsDeclarationCommInfo);
						if (CommonVars.commonCheckNull(
								DgBaseSpecialCustomsDeclarationCommInfo.this,
								customsDeclarationCommInfo)) {
							return;
						}
						customsDeclarationCommInfo = (BaseCustomsDeclarationCommInfo) baseEncAction
								.saveCustomsDeclarationCommInfo(new Request(
										CommonVars.getCurrUser()),
										customsDeclarationCommInfo);
						tableModel.addRow(customsDeclarationCommInfo);
						emptyData();
					} else if (dataState == DataState.EDIT) {
						fillData(customsDeclarationCommInfo);
						if (CommonVars.commonCheckNull(
								DgBaseSpecialCustomsDeclarationCommInfo.this,
								customsDeclarationCommInfo)) {
							return;
						}
						if (customsDeclarationCommInfo
								.getBaseCustomsDeclaration().getFixType() != null) {
							if (customsDeclarationCommInfo.getCommAmount() > useAmount) {
								JOptionPane
										.showMessageDialog(
												DgBaseSpecialCustomsDeclarationCommInfo.this,
												"数量超出了可用数量，保存不了！", "提示",
												JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						}
						customsDeclarationCommInfo = (BaseCustomsDeclarationCommInfo) baseEncAction
								.saveCustomsDeclarationCommInfo(new Request(
										CommonVars.getCurrUser()),
										customsDeclarationCommInfo);
						tableModel.updateRow(customsDeclarationCommInfo);
					}
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnOk;
	}

	/**
	 * 反写
	 * 
	 * @param customsDeclarationCommInfo
	 */
	protected void writeBackFixtureItemsMount(
			BaseCustomsDeclarationCommInfo customsDeclarationCommInfo) {

	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(276, 400, 59, 24);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showData();
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes tfCommUnitPrice
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	protected JFormattedTextField getTfCommUnitPrice() {
		if (tfCommUnitPrice == null) {
			tfCommUnitPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfCommUnitPrice.setBounds(84, 133, 160, 20);
			// tfCommUnitPrice.addFocusListener(new
			// java.awt.event.FocusAdapter() {
			// public void focusLost(java.awt.event.FocusEvent e) {
			// getTotalPrice();
			// }
			// });
		}
		return tfCommUnitPrice;
	}

	/**
	 * This method initializes tfCommTotalPrice
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	protected JFormattedTextField getTfCommTotalPrice() {
		if (tfCommTotalPrice == null) {
			tfCommTotalPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfCommTotalPrice.setBounds(303, 133, 160, 20);
			tfCommTotalPrice.setEditable(true);
			tfCommTotalPrice.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					String sKey = java.awt.event.KeyEvent.getKeyText(e
							.getKeyCode());
					if (sKey.equalsIgnoreCase("Enter")) {// 为0时或为空才进行计算
						tfCommUnitPrice.setValue(getUnitPrice());
					}
				}
			});
			// tfCommTotalPrice.setFormatterFactory(getDefaultFormatterFactory());
			// tfCommTotalPrice
			// .addFocusListener(new java.awt.event.FocusAdapter() {
			// public void focusLost(java.awt.event.FocusEvent e) {
			// Double commAmount = Double
			// .parseDouble(tfCommAmount.getText() != null
			// && !"".equals(tfCommAmount
			// .getText()) ? tfCommAmount
			// .getText() : "0");
			// Double totaltPrice = Double
			// .parseDouble(tfCommTotalPrice.getText() != null
			// && !"".equals(tfCommTotalPrice
			// .getText()) ? tfCommTotalPrice
			// .getText()
			// : "0");
			// if (commAmount != 0) {
			// Double tmp = Double
			// .parseDouble(tfCommUnitPrice.getText() != null
			// && !"".equals(tfCommUnitPrice
			// .getText()) ? tfCommUnitPrice
			// .getText()
			// : "0");
			// if (tmp == 0) {
			// tfCommUnitPrice
			// .setValue(new Double(
			// CommonUtils
			// .formatDoubleByDigit(
			// totaltPrice
			// / commAmount,
			// 4)));
			// }
			// }
			// }
			// });
			// //
			// CustomFormattedTextFieldUtils.setFormatterFactory(tfCommTotalPrice,
			// // 2);
		}
		return tfCommTotalPrice;
	}

	/**
	 * This method initializes tfLegalAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	protected JFormattedTextField getTfLegalAmount() {
		if (tfLegalAmount == null) {
			tfLegalAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfLegalAmount.setBounds(84, 235, 160, 20);
			// tfLegalAmount.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfLegalAmount;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	protected JFormattedTextField getJFormattedTextField() {
		if (tfUnitWeight == null) {
			tfUnitWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitWeight.setBounds(84, 157, 110, 22);
			// tfUnitWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfUnitWeight;
	}

	/**
	 * This method initializes tfSecondLegalAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	protected JFormattedTextField getTfSecondLegalAmount() {
		if (tfSecondLegalAmount == null) {
			tfSecondLegalAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfSecondLegalAmount.setBounds(84, 260, 160, 20);
			// tfSecondLegalAmount
			// .setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfSecondLegalAmount;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * This method initializes tfComplexCode
	 * 
	 * @return javax.swing.JTextField
	 */
	protected JTextField getTfComplexCode() {
		if (tfComplexCode == null) {
			tfComplexCode = new JTextField();
			tfComplexCode.setEditable(false);
			tfComplexCode.setBounds(84, 11, 130, 22);
		}
		return tfComplexCode;
	}

	/**
	 * This method initializes btnComplex
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnComplex() {
		if (btnComplex == null) {
			btnComplex = new JButton();
			btnComplex.setBounds(220, 13, 24, 19);
			btnComplex.setText("...");
			btnComplex.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String name;
					String spec;
					Unit unit;
					Integer tenSeqNum;
					if (CommonVars.getSpecialCustoms() != null
							&& CommonVars.getSpecialCustoms().equals("0")) {
						boolean isImport = EncCommon
								.isImport(customsDeclaration.getImpExpType()
										.intValue());
						if (customsDeclaration.getImpExpType() == ImpExpType.BACK_FACTORY_REWORK) {
							isImport = false;
						} else if (customsDeclaration.getImpExpType() == ImpExpType.REWORK_EXPORT) {
							isImport = false;
						}
						// TempAllTypeInnerMergeDate temp = null;
						// if (projectType == ProjectType.BCS) {
						TempAllTypeInnerMergeDate temp = (TempAllTypeInnerMergeDate) CommonQuery
								.getInstance().getInnerDateForSpeFix(
										projectType, isImport);
						// } else if (projectType == ProjectType.BCUS) {
						//
						// } else if (projectType == ProjectType.DZSC) {
						// }
						if (temp != null) {
							complex = temp.getHsAfterComplex();
							firstLegalUnit = temp.getHsAfterComplex()
									.getFirstUnit();
							secondLegalUnit = temp.getHsAfterComplex()
									.getSecondUnit();
							tenSeqNum = temp.getHsAfterTenMemoNo();
							unit = temp.getHsAfterLegalUnit();
							name = temp.getHsAfterMaterielTenName();
							spec = temp.getHsAfterMaterielTenSpec();

							DgBaseSpecialCustomsDeclarationCommInfo.this.tfComplexCode
									.setText(complex.getCode());
							DgBaseSpecialCustomsDeclarationCommInfo.this.tfCommName
									.setText(name == null ? "" : name);
							DgBaseSpecialCustomsDeclarationCommInfo.this.tfFirstLegalUnit
									.setText(temp.getHsAfterComplex()
											.getFirstUnit() == null ? "" : temp
											.getHsAfterComplex().getFirstUnit()
											.getName());
							DgBaseSpecialCustomsDeclarationCommInfo.this.tfSecondLegalUnit
									.setText(temp.getHsAfterComplex()
											.getSecondUnit() == null ? ""
											: temp.getHsAfterComplex()
													.getSecondUnit().getName());
							DgBaseSpecialCustomsDeclarationCommInfo.this.tfCommSpec
									.setText(spec == null ? "" : spec);
							DgBaseSpecialCustomsDeclarationCommInfo.this.tfEmsNo
									.setText(tenSeqNum.toString());
							DgBaseSpecialCustomsDeclarationCommInfo.this.cbbUnit
									.setSelectedItem(unit == null ? "" : unit);
						}
					} else {
						Complex temp = (Complex) CommonQuery.getInstance()
								.getComplex();
						if (temp != null) {
							firstLegalUnit = temp.getFirstUnit();
							secondLegalUnit = temp.getSecondUnit();
							complex = temp;
							DgBaseSpecialCustomsDeclarationCommInfo.this.tfComplexCode
									.setText(temp.getCode());
							DgBaseSpecialCustomsDeclarationCommInfo.this.tfCommName
									.setText(temp.getName());
							DgBaseSpecialCustomsDeclarationCommInfo.this.tfFirstLegalUnit
									.setText(temp.getFirstUnit() == null ? ""
											: temp.getFirstUnit().getName());
							DgBaseSpecialCustomsDeclarationCommInfo.this.tfSecondLegalUnit
									.setText(temp.getSecondUnit() == null ? ""
											: temp.getSecondUnit().getName());

						}
					}

				}
				// }
			});
		}
		return btnComplex;
	}

	/**
	 * This method initializes cbbUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	protected JComboBox getCbbUnit() {
		if (cbbUnit == null) {
			cbbUnit = new JComboBox();
			cbbUnit.setBounds(84, 84, 160, 20);
		}
		return cbbUnit;
	}

	/**
	 * This method initializes tfFirstLegalUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	protected JTextField getTfFirstLegalUnit() {
		if (tfFirstLegalUnit == null) {
			tfFirstLegalUnit = new JTextField();
			tfFirstLegalUnit.setBounds(84, 183, 160, 22);
			tfFirstLegalUnit.setEditable(false);
		}
		return tfFirstLegalUnit;
	}

	/**
	 * This method initializes tfSecondLegalUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	protected JTextField getTfSecondLegalUnit() {
		if (tfSecondLegalUnit == null) {
			tfSecondLegalUnit = new JTextField();
			tfSecondLegalUnit.setBounds(84, 208, 160, 22);
			tfSecondLegalUnit.setEditable(false);
		}
		return tfSecondLegalUnit;
	}

	/**
	 * This method initializes tfEmsNo
	 * 
	 * @return javax.swing.JTextField
	 */
	protected JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(335, 233, 128, 20);
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes tfMaterielNo
	 * 
	 * @return javax.swing.JTextField
	 */
	protected JTextField getTfMaterielNo() {
		if (tfMaterielNo == null) {
			tfMaterielNo = new JTextField();
			tfMaterielNo.setBounds(303, 287, 160, 20);
		}
		return tfMaterielNo;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	// protected DefaultFormatterFactory getDefaultFormatterFactory() {
	// if (defaultFormatterFactory == null) {
	// defaultFormatterFactory = new DefaultFormatterFactory();
	// defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
	// defaultFormatterFactory.setEditFormatter(getNumberFormatter());
	// }
	// return defaultFormatterFactory;
	// }
	/**
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	// protected NumberFormatter getNumberFormatter() {
	// if (numberFormatter == null) {
	// DecimalFormat decimalFormat1 = new DecimalFormat();
	// decimalFormat1.setMaximumFractionDigits(4);
	// numberFormatter = new NumberFormatter();
	// numberFormatter.setFormat(decimalFormat1);
	// DecimalFormat decimalFormat = new DecimalFormat(); // @jve:decl-index=0:
	// decimalFormat.setMaximumFractionDigits(999);
	// }
	// return numberFormatter;
	// }
	public void setVisible(boolean b) {
		if (b) {

			totalCount = tableModel.getRowCount(); // 总记录数

			customsDeclarationCommInfo = (BaseCustomsDeclarationCommInfo) tableModel
					.getCurrentRow();

			rowCount = tableModel.getCurrRowCount();// 当前记录

			initUIComponents();

			showData();

			setImpExpState();

			setState();

			if (other1 != null) {
				initParam();

			}

		}

		super.setVisible(b);
	}

	protected void initParam() {
		if (customsDeclarationCommInfo != null) {
			// 原产国
			Country country = customsDeclarationCommInfo.getCountry();

			// 用途
			Uses uses = customsDeclarationCommInfo.getUses();

			// 征免方式
			LevyMode levyMode = customsDeclarationCommInfo.getLevyMode();
			cbbProduceCountry.setSelectedItem(country == null ? other1
					.getCountry() : country);
			cbbUses.setSelectedItem(uses == null ? other1.getUses() : uses);
			cbbLevyMode.setSelectedItem(levyMode == null ? other1.getLevyMode()
					: levyMode);
		} else {
			// 原产国--取报关单参数设置
			cbbProduceCountry.setSelectedItem(other1.getCountry());
			// 默认用途--取报关单参数设置
			cbbUses.setSelectedItem(other1.getUses());
			// 减免方式--取报关单参数设置
			cbbLevyMode.setSelectedItem(other1.getLevyMode());
		}
	}

	/**
	 * 设置进出或出口时的表单状态
	 */
	protected void setImpExpState() {
		if (customsDeclaration == null) {
			return;
		}
		// 进出口类型
		int type = customsDeclaration.getImpExpType();

		// 从进出口类型判断 进/出口
		if (EncCommon.isImport(type) == true) {

			jLabel8.setText("原产国");

		} else {

			jLabel8.setText("目的国");

		}
	}

	/**
	 * 设置组件状态
	 */
	protected void setState() {

		jButton.setEnabled(rowCount > 0);

		jButton1.setEnabled(rowCount < totalCount - 1);

		this.btnOk.setEnabled(dataState != DataState.BROWSE && !effective);// 保存

		jButton2.setEnabled(dataState == DataState.BROWSE && !effective); // 修改

		btnCancel.setEnabled(dataState != DataState.BROWSE && !effective); // 取消

		this.btnComplex.setEnabled(dataState != DataState.BROWSE && !effective);

		setContainerEnabled(jPanel, dataState != DataState.BROWSE && !effective);

		this.jComboBox.setEnabled(dataState != DataState.BROWSE
				&& !this.effective); // 事业部

		jComboBox.setVisible(projectType == ProjectType.BCUS);

		jLabel21.setVisible(projectType == ProjectType.BCUS);

	}

	/**
	 * 设置容器除JLabel外所有控件的enabled属性
	 */
	protected void setContainerEnabled(Container container, boolean isFlag) {
		for (int i = 0; i < container.getComponentCount(); i++) {
			Component component = container.getComponent(i);
			if (!(component instanceof JLabel)) {
				component.setEnabled(isFlag);
				if (component instanceof Container) {
					setContainerEnabled((Container) component, isFlag);
				}
			}
		}
	}

	/**
	 * 初始化组件
	 */
	@SuppressWarnings("unchecked")
	protected void initUIComponents() {

		// 初始化起运国
		this.cbbProduceCountry.setModel(CustomBaseModel.getInstance()
				.getCountryModel());
		this.cbbProduceCountry.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbProduceCountry);
		// 初始化征免性质
		this.cbbLevyMode.setModel(CustomBaseModel.getInstance()
				.getLevymodeModel());
		this.cbbLevyMode
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbLevyMode);
		// 初始化用途
		this.cbbUses.setModel(CustomBaseModel.getInstance().getUseModel());
		this.cbbUses.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbUses);
		// 初始化单位
		this.cbbUnit.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbUnit.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbUnit);

		// 事业部
		List projectList = materialManageAction.findProjectDept(new Request(
				CommonVars.getCurrUser(), true));
		this.jComboBox
				.setModel(new DefaultComboBoxModel(projectList.toArray()));
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox, "code", "name");
		jComboBox.setSelectedIndex(-1);

		CompanyOther other = CommonVars.getOther();

		CustomFormattedTextFieldUtils
				.setFormatterFactory(this.tfGrossWeight, 4);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfNetWeight, 4);
		// 数量
		if (other != null) {
			CustomFormattedTextFieldUtils.setFormatterFactory(
					this.tfCommAmount, other.getCustomAmountNum());
		} else {
			CustomFormattedTextFieldUtils.setFormatterFactory(
					this.tfCommAmount, 4);
		}

		// 第一法定数量
		// if (other != null) {
		// CustomFormattedTextFieldUtils.setFormatterFactory(
		// this.tfLegalAmount, other.getCustomAmountNum());
		// } else {
		CustomFormattedTextFieldUtils
				.setFormatterFactory(this.tfLegalAmount, 4);
		// }
		// 第二法定数量
		// if (other != null) {
		// CustomFormattedTextFieldUtils.setFormatterFactory(
		// this.tfSecondLegalAmount, other.getCustomAmountNum());
		// } else {
		CustomFormattedTextFieldUtils.setFormatterFactory(
				this.tfSecondLegalAmount, 4);
		// }
		// 总价
		if (other != null) {
			CustomFormattedTextFieldUtils.setFormatterFactory(
					this.tfCommTotalPrice, other.getCustomTotalNum());
		} else {
			CustomFormattedTextFieldUtils.setFormatterFactory(
					this.tfCommTotalPrice, 4);
		}
		CustomFormattedTextFieldUtils.addAutoCalcListener(this.tfCommUnitPrice,
				new AutoCalcListener() {
					public void run() {
						tfCommTotalPrice.setValue(getTotalPrice());
					}
				});

	}

	protected void showData() {
		if (dataState == DataState.ADD) {
			this.cbbLevyMode.setSelectedItem(null);
			this.cbbProduceCountry.setSelectedItem(null);
			this.cbbUnit.setSelectedItem(null);
			this.cbbUses.setSelectedItem(null);
		} else if (dataState == DataState.EDIT || dataState == DataState.BROWSE) {
			if (customsDeclarationCommInfo == null) {
				return;
			}
			jLabel20.setText(String.valueOf(customsDeclarationCommInfo
					.getSerialNumber()));
			if (customsDeclarationCommInfo.getComplex() != null) {
				this.complex = customsDeclarationCommInfo.getComplex();
				this.tfComplexCode.setText(complex.getCode());
			}
			this.tfCommName.setText(customsDeclarationCommInfo.getCommName());
			if (customsDeclarationCommInfo.getLegalUnit() != null) {
				this.tfFirstLegalUnit.setText(customsDeclarationCommInfo
						.getLegalUnit().getName());
			}
			if (customsDeclarationCommInfo.getSecondLegalUnit() != null) {
				this.tfSecondLegalUnit.setText(customsDeclarationCommInfo
						.getSecondLegalUnit().getName());
			}
			firstLegalUnit = customsDeclarationCommInfo.getLegalUnit();
			secondLegalUnit = customsDeclarationCommInfo.getSecondLegalUnit();

			this.tfCommSpec.setText(customsDeclarationCommInfo.getCommSpec());
			this.tfCommAmount.setValue(customsDeclarationCommInfo
					.getCommAmount());
			this.tfCommUnitPrice.setValue(customsDeclarationCommInfo
					.getCommUnitPrice());
			this.tfCommTotalPrice.setValue(customsDeclarationCommInfo
					.getCommTotalPrice());
			this.tfLegalAmount.setValue(customsDeclarationCommInfo
					.getFirstAmount());
			this.tfSecondLegalAmount.setValue(customsDeclarationCommInfo
					.getSecondAmount());
			this.tfNetWeight
					.setValue(customsDeclarationCommInfo.getNetWeight());
			this.tfGrossWeight.setValue(customsDeclarationCommInfo
					.getGrossWeight());
			this.tfPieses.setValue(customsDeclarationCommInfo.getPieces());
			if (customsDeclarationCommInfo.getUnitWeight() != null) {
				this.tfUnitWeight.setValue(customsDeclarationCommInfo
						.getUnitWeight());
			} else {
				this.tfUnitWeight.setValue(Double.valueOf("0"));
			}
			if (customsDeclarationCommInfo.getCommSerialNo() != null) {
				this.tfEmsNo.setText(customsDeclarationCommInfo
						.getCommSerialNo().toString());
			} else {
				this.tfEmsNo.setText("");
			}
			if (customsDeclarationCommInfo.getVersion() != null) {
				this.jTextField
						.setText(customsDeclarationCommInfo.getVersion());
			} else {
				this.jTextField.setText("0");
			}
			if (customsDeclarationCommInfo.getMaterielNo() != null) {
				this.tfMaterielNo.setText(customsDeclarationCommInfo
						.getMaterielNo().toString());
			} else {
				this.tfMaterielNo.setText("");
			}
			this.cbbUnit.setSelectedItem(customsDeclarationCommInfo.getUnit());
			this.cbbProduceCountry.setSelectedItem(customsDeclarationCommInfo
					.getCountry());
			this.cbbUses.setSelectedItem(customsDeclarationCommInfo.getUses());
			this.cbbLevyMode.setSelectedItem(customsDeclarationCommInfo
					.getLevyMode());
			// 事业部
			if (customsDeclarationCommInfo.getProjectDept() != null) {
				this.jComboBox.setSelectedItem(customsDeclarationCommInfo
						.getProjectDept());
			} else {
				this.jComboBox.setSelectedIndex(-1);
			}
			this.tfBoxNo.setText(customsDeclarationCommInfo.getBoxNo());

			// 规范申报规格
			tfDeclareSpec.setText(customsDeclarationCommInfo.getDeclareSpec());

			tfTax.setText(cutZeroInEnd(customsDeclarationCommInfo.getTax()
					.toString()));

			showPrintData();
		}

	}

	protected void fillData(
			BaseCustomsDeclarationCommInfo customsDeclarationCommInfo) {
		customsDeclarationCommInfo.setCommSpec(this.tfCommSpec.getText());
		customsDeclarationCommInfo.setCommName(this.tfCommName.getText());
		customsDeclarationCommInfo.setComplex(this.complex);
		customsDeclarationCommInfo
				.setBaseCustomsDeclaration(this.customsDeclaration);
		if (this.tfCommAmount.getValue() != null) {
			customsDeclarationCommInfo.setCommAmount(Double
					.valueOf(this.tfCommAmount.getText().toString()));
		} else {
			customsDeclarationCommInfo.setCommAmount(null);
		}
		if (this.tfCommUnitPrice.getValue() != null) {
			customsDeclarationCommInfo.setCommUnitPrice(Double
					.valueOf(this.tfCommUnitPrice.getText().toString()));
		} else {
			customsDeclarationCommInfo.setCommUnitPrice(null);
		}
		if (this.tfCommTotalPrice.getValue() != null) {
			customsDeclarationCommInfo.setCommTotalPrice(Double
					.valueOf(this.tfCommTotalPrice.getText().toString()));
		} else {
			customsDeclarationCommInfo.setCommTotalPrice(null);
		}
		if (this.tfLegalAmount.getValue() != null) {
			customsDeclarationCommInfo.setFirstAmount(Double
					.valueOf(this.tfLegalAmount.getText().toString()));
		} else {
			customsDeclarationCommInfo.setFirstAmount(null);
		}
		if (this.tfSecondLegalAmount.getValue() != null) {
			customsDeclarationCommInfo.setSecondAmount(Double
					.valueOf(this.tfSecondLegalAmount.getText().toString()));
		} else {
			customsDeclarationCommInfo.setSecondAmount(null);
		}
		if (this.tfNetWeight.getValue() != null) {
			customsDeclarationCommInfo.setNetWeight(Double
					.valueOf(this.tfNetWeight.getText().toString()));
		} else {
			customsDeclarationCommInfo.setNetWeight(null);
		}
		if (this.tfGrossWeight.getValue() != null) {
			customsDeclarationCommInfo.setGrossWeight(Double
					.valueOf(this.tfGrossWeight.getText().toString()));
		} else {
			customsDeclarationCommInfo.setGrossWeight(null);
		}
		if (this.tfPieses.getValue() != null) {
			try {
				customsDeclarationCommInfo.setPieces(Integer
						.valueOf(this.tfPieses.getText().toString()));
			} catch (Exception e) {
				throw new RuntimeException("件数：格式不正确");
			}
		} else {
			customsDeclarationCommInfo.setPieces(null);
		}
		if (this.cbbUses.getSelectedItem() != null) {
			customsDeclarationCommInfo.setUses((Uses) this.cbbUses
					.getSelectedItem());
		} else {
			customsDeclarationCommInfo.setUses(null);
		}
		if (this.cbbLevyMode.getSelectedItem() != null) {
			customsDeclarationCommInfo.setLevyMode((LevyMode) this.cbbLevyMode
					.getSelectedItem());
		} else {
			customsDeclarationCommInfo.setLevyMode(null);
		}
		if (this.tfEmsNo.getText() != null
				&& !this.tfEmsNo.getText().equals("")) {
			try {
				customsDeclarationCommInfo.setCommSerialNo(Integer
						.valueOf(this.tfEmsNo.getText()));
			} catch (Exception e) {

			}
		} else {
			customsDeclarationCommInfo.setCommSerialNo(null);
		}

		if (this.jTextField.getText() != null) {
			customsDeclarationCommInfo.setVersion((this.jTextField.getText()));
		} else {
			customsDeclarationCommInfo.setVersion(null);
		}
		if (this.tfUnitWeight.getValue() != null) {
			customsDeclarationCommInfo.setUnitWeight(Double
					.valueOf(this.tfUnitWeight.getValue().toString()));
		} else {
			customsDeclarationCommInfo.setVersion(null);
		}

		// 事业部
		if (this.jComboBox.getSelectedItem() != null) {
			customsDeclarationCommInfo
					.setProjectDept((ProjectDept) this.jComboBox
							.getSelectedItem());
		} else {
			customsDeclarationCommInfo.setProjectDept(null);
		}
		// 原产国
		if (this.cbbProduceCountry.getSelectedItem() != null) {
			customsDeclarationCommInfo
					.setCountry((Country) this.cbbProduceCountry
							.getSelectedItem());
		} else {
			customsDeclarationCommInfo.setCountry(null);
		}

		customsDeclarationCommInfo.setMaterielNo(this.tfMaterielNo.getText());
		customsDeclarationCommInfo.setUnit((Unit) cbbUnit.getSelectedItem());
		customsDeclarationCommInfo.setLegalUnit(this.firstLegalUnit);
		customsDeclarationCommInfo.setSecondLegalUnit(this.secondLegalUnit);
		customsDeclarationCommInfo.setComplex(this.complex);

		customsDeclarationCommInfo.setBoxNo(tfBoxNo.getText());

		// 规范申报规格
		customsDeclarationCommInfo.setDeclareSpec(tfDeclareSpec.getText());

		// 税金
		if (StringUtils.isNotBlank(tfTax.getText())) {
			customsDeclarationCommInfo.setTax(Double.valueOf(tfTax.getText()));
		} else {
			customsDeclarationCommInfo.setTax(null);
		}

	}

	protected void emptyData() {
		this.complex = null;
		this.tfComplexCode.setText("");
		this.tfCommName.setText("");
		this.tfFirstLegalUnit.setText("");
		this.tfSecondLegalUnit.setText("");
		firstLegalUnit = null;
		secondLegalUnit = null;
		this.tfCommSpec.setText("");
		this.tfCommAmount.setValue(Double.valueOf("0"));
		this.tfCommUnitPrice.setValue(Double.valueOf("0"));
		this.tfCommTotalPrice.setValue(Double.valueOf("0"));
		this.tfLegalAmount.setValue(Double.valueOf("0"));
		this.tfSecondLegalAmount.setValue(Double.valueOf("0"));
		this.tfNetWeight.setValue(Double.valueOf("0"));
		this.tfGrossWeight.setValue(Double.valueOf("0"));
		this.tfPieses.setValue(Integer.valueOf("0"));
		this.tfUnitWeight.setValue(Double.valueOf("0"));
		this.tfEmsNo.setText("");
		this.jTextField.setText("0");
		this.tfMaterielNo.setText("");
		this.cbbUnit.setSelectedItem(null);
		this.cbbProduceCountry.setSelectedItem(null);
		this.cbbUses.setSelectedItem(null);
		this.cbbLevyMode.setSelectedItem(null);
		this.tfBoxNo.setText(null);
		tfTax.setText("");
	}

	protected boolean validateIsNull() {
		if (this.tfComplexCode.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "商品编码不能为空!", "提示!!",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.tfCommName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "商品名称不能为空!", "提示!!",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}

		// if (this.tfDeclareSpec.getText().trim().equals("")) {
		// JOptionPane.showMessageDialog(null, "规范申报规格不能为空!", "提示!!",
		// JOptionPane.INFORMATION_MESSAGE);
		// return true;
		// }

		if (this.cbbUnit.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, "单位不能为空!", "提示!!",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}

		if (Double
				.parseDouble(this.tfCommAmount.getText().trim().equals("") ? "0"
						: this.tfCommAmount.getValue().toString().trim()) <= 0) {
			JOptionPane.showMessageDialog(null, "数量不能为小于0!", "提示!!",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (Double
				.parseDouble(this.tfCommUnitPrice.getText().trim().equals("") ? "0"
						: this.tfCommUnitPrice.getValue().toString()) <= 0) {
			JOptionPane.showMessageDialog(null, "单价不能为0!", "提示!!",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.cbbLevyMode.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, "征减免税方式不能为空!", "提示!!",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.cbbUses.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, "用途不能为空!", "提示!!",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.cbbProduceCountry.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, "原产国不能为空!", "提示!!",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}

		return false;
	}

	/**
	 * 计算单价
	 * 
	 * @return
	 */
	protected Double getUnitPrice() {
		double amount = 0;
		double totalPrice = 0;
		if (this.tfCommAmount.getText() != null
				&& !this.tfCommAmount.getText().equals("")) {
			amount = Double.parseDouble(this.tfCommAmount.getText().toString());
		}
		if (this.tfCommTotalPrice.getText() != null
				&& !this.tfCommTotalPrice.getText().equals("")) {
			totalPrice = Double.parseDouble(this.tfCommTotalPrice.getText()
					.toString());
		}
		BigDecimal bd = new BigDecimal(totalPrice / amount);
		String unitPrice = bd.setScale(4, BigDecimal.ROUND_HALF_UP).toString();
		return Double.valueOf(unitPrice);
	}

	/**
	 * 计算总价
	 * 
	 * @return
	 */
	protected Double getTotalPrice() {
		double amount = 0;
		double unitPrice = 0;
		if (this.tfCommAmount.getText() != null
				&& !"".equals(this.tfCommAmount.getText())) {
			amount = Double.parseDouble(this.tfCommAmount.getText().toString());
		}
		if (this.tfCommUnitPrice.getText() != null
				&& !"".equals(this.tfCommUnitPrice.getText())) {
			unitPrice = Double.parseDouble(this.tfCommUnitPrice.getText()
					.toString());
		}
		BigDecimal bd = new BigDecimal(amount * unitPrice);
		String totalPrice = CommonUtils
				.formatDoubleByDigit(bd.doubleValue(), 2);
		this.tfCommTotalPrice.setText(totalPrice);
		if (totalPrice != null && !"".equals(totalPrice)) {
			this.tfCommTotalPrice.setValue(new Double(totalPrice));
		}

		return Double.valueOf(totalPrice);
	}

	class DocumentListenerAdapter implements DocumentListener {

		public void insertUpdate(DocumentEvent e) {
			tfCommTotalPrice.setValue(getTotalPrice());
		}

		public void removeUpdate(DocumentEvent e) {
			tfCommTotalPrice.setValue(getTotalPrice());
		}

		public void changedUpdate(DocumentEvent e) {
			tfCommTotalPrice.setValue(getTotalPrice());
		}
	}

	protected abstract BaseCustomsDeclarationCommInfo newCustomsDeclarationCommInfo();

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(76, 400, 59, 24);
			jButton.setText("上条");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (RollSave()) {
						return;
					}
					dataState = DataState.BROWSE;
					rowCount--;
					customsDeclarationCommInfo = (BaseCustomsDeclarationCommInfo) tableModel
							.getObjectByRow(rowCount);
					showData();
					setState();
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
			jButton1.setBounds(345, 400, 59, 24);
			jButton1.setText("下条");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (RollSave()) {
						return;
					}
					dataState = DataState.BROWSE;
					rowCount++;
					System.out.println(rowCount);
					customsDeclarationCommInfo = (BaseCustomsDeclarationCommInfo) tableModel
							.getObjectByRow(rowCount);
					showData();
					setState();
				}
			});
		}
		return jButton1;
	}

	private boolean RollSave() {
		if (dataState == DataState.EDIT) {
			if (validateIsNull() == true) {
				return true;
			}
			fillData(customsDeclarationCommInfo);
			if (CommonVars.commonCheckNull(
					DgBaseSpecialCustomsDeclarationCommInfo.this,
					customsDeclarationCommInfo)) {
				return true;
			}
			customsDeclarationCommInfo = baseEncAction
					.saveCustomsDeclarationCommInfo(
							new Request(CommonVars.getCurrUser()),
							customsDeclarationCommInfo);
			tableModel.updateRow(customsDeclarationCommInfo);
		}
		return false;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(142, 400, 59, 24);
			jButton2.setText("修改");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(303, 260, 160, 20);
		}
		return jTextField;
	}

	/**
	 * 在label上输出设备协议中的定量、以进数量、以出数量
	 */
	@SuppressWarnings("unchecked")
	private void showPrintData() {
		if (customsDeclarationCommInfo.getBaseCustomsDeclaration().getFixType() == null) {
			jlbShow.setVisible(false);
			return;
		}

		List list = baseEncAction.statisticsFixInCommInfo(new Request(
				CommonVars.getCurrUser()), customsDeclarationCommInfo);

		Integer type = customsDeclarationCommInfo.getBaseCustomsDeclaration()
				.getImpExpType();
		Double rationAmount = (Double) list.get(0);// 设备表体中的数量
		Double inAmount = 0.0;// 进口数量
		if (type == ImpExpType.EQUIPMENT_IMPORT) {
			inAmount = ((Double) list.get(1) - (tfCommAmount.getValue() == null ? 0.0
					: (Double) tfCommAmount.getValue()));// 进口数量
		} else if (type == ImpExpType.BACK_PORT_REPAIR
				|| type == ImpExpType.EQUIPMENT_BACK_PORT) {
			inAmount = (Double) list.get(1);// 进口数量
		}
		Double leftAmount = rationAmount - inAmount;// 可进口量
		useAmount = leftAmount;
		String str = " 设备定量:" + String.valueOf(rationAmount) + "      已使用量:"
				+ String.valueOf(inAmount) + "      可使用量:"
				+ String.valueOf(leftAmount);
		jlbShow.setText(str);
	}

	/**
	 * @return the projectType
	 */
	public int getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType
	 *            the projectType to set
	 */
	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(303, 157, 160, 20));
			jComboBox.setVisible(projectType == ProjectType.BCUS);
		}
		return jComboBox;
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
			tfNetWeight.setBounds(new Rectangle(84, 309, 160, 20));
			// tfNetWeight.setFormatterFactory(defaultFormatterFactory1);
		}
		return tfNetWeight;
	}

	/**
	 * This method initializes tfGrossWeight
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfGrossWeight() {
		if (tfGrossWeight == null) {
			// DefaultFormatterFactory defaultFormatterFactory2 = new
			// DefaultFormatterFactory();
			// defaultFormatterFactory2.setEditFormatter(new NumberFormatter());
			// defaultFormatterFactory2.setDisplayFormatter(new
			// NumberFormatter());
			tfGrossWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfGrossWeight.setBounds(new Rectangle(303, 309, 160, 20));
			// tfGrossWeight.setFormatterFactory(defaultFormatterFactory2);
		}
		return tfGrossWeight;
	}

	/**
	 * This method initializes tfPieses
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfPieses() {
		if (tfPieses == null) {
			tfPieses = new JCustomFormattedTextField();
			tfPieses.setBounds(new Rectangle(84, 332, 160, 21));
		}
		return tfPieses;
	}

	/**
	 * This method initializes tfBoxNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBoxNo() {
		if (tfBoxNo == null) {
			tfBoxNo = new JTextField();
			tfBoxNo.setBounds(new Rectangle(303, 331, 160, 20));
		}
		return tfBoxNo;
	}

	protected CustomsDeclarationSet other1; // @jve:decl-index=0:
	private JLabel lblNewLabel;
	private JTextField tfDeclareSpec;
	private JTextField textField;

	public CustomsDeclarationSet getOther1() {
		return other1;
	}

	public void setOther1(CustomsDeclarationSet other1) {
		this.other1 = other1;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("规范申报规格");
			lblNewLabel.setBounds(10, 60, 72, 20);
		}
		return lblNewLabel;
	}

	private JTextField getTfDeclareSpec() {
		if (tfDeclareSpec == null) {
			tfDeclareSpec = new JTextField();
			tfDeclareSpec.setBounds(84, 60, 379, 21);
			tfDeclareSpec.setColumns(10);
		}
		return tfDeclareSpec;
	}

	/*
	 * 切割 以 .0 为结尾的 字符 应显示为 xxxx 非 xxxx.0
	 */
	private String cutZeroInEnd(String number) {

		int indexForDot = number.indexOf(".");

		try {

			if (number.substring(indexForDot + 1, indexForDot + 2).equals("0")) {

				if (!number.substring(indexForDot + 2, indexForDot + 3).equals(
						"0")) {

					return number;

				} else {

					return number.substring(0, indexForDot);
				}

			}

		} catch (StringIndexOutOfBoundsException e) {

			return number.substring(0, indexForDot);

		}
		return number.substring(0, indexForDot);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
