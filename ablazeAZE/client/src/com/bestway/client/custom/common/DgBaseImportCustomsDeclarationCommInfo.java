/*
 * Created on 2004-8-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.AutoCalcListener;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.manualdeclare.entity.CommdityForbid;
import com.bestway.bcus.manualdeclare.entity.RestirictCommodity;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.customs.common.action.BCSImpCustomsAuthorityAction;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.action.DZSCImpCustomsAuthorityAction;
import com.bestway.customs.common.action.ImpCustomsAuthorityAction;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 进口报关单商品信息编辑 lm checked by 2009-12-11
 */
@SuppressWarnings("unchecked")
public class DgBaseImportCustomsDeclarationCommInfo extends JDialogBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JPanel jContentPane = null;

	protected JPanel jPanel = null;

	protected JTextField tfCommName = null;

	protected JTextField tfCommSpec = null;

	protected JFormattedTextField tfCommAmount = null;

	protected JComboBox cbbProduceCountry = null;

	protected JComboBox cbbUses = null;

	protected JComboBox cbbLevyMode = null;

	protected JButton btnSave = null;

	protected JButton btnCancel = null;

	protected JFormattedTextField tfCommUnitPrice = null;

	protected JFormattedTextField tfCommTotalPrice = null;

	protected JFormattedTextField tfLegalAmount = null;

	protected JFormattedTextField tfSecondLegalAmount = null;

	protected JFormattedTextField tfNetWeight = null;

	protected JFormattedTextField tfGrossWeight = null;

	protected JFormattedTextField tfPieces = null;

	protected JTableListModel tableModel = null;

	protected JLabel lbUnit = null;

	protected JLabel lbLegalUnit = null;

	protected JLabel lbSecondLegalUnit = null;

	protected BaseCustomsDeclarationCommInfo customsDeclarationCommInfo = null;

	protected BaseEncAction baseEncAction = null;

	protected DefaultFormatterFactory defaultFormatterFactory = null;

	protected NumberFormatter numberFormatter = null;

	protected boolean isEffective = false;

	private JLabel lbCurrItemNo = null;

	private JLabel lbCurrItemNum = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JButton btnEdit = null;

	protected int dataState = DataState.EDIT;

	protected JLabel lbBoxNo = null;

	protected JTextField tfBoxNo = null;

	private JLabel lbSecondAmount = null;

	private JLabel lbDetailCommSpec = null;

	private JTextField tfDetailCommSpec = null;

	private ImpCustomsAuthorityAction impCustomsAuthorityAction = null;

	private BCSImpCustomsAuthorityAction bCSImpCustomsAuthorityAction = null;

	private DZSCImpCustomsAuthorityAction dZSCImpCustomsAuthorityAction = null;

	protected JLabel lbProjectDept = null;

	protected int projectType = ProjectType.BCUS;

	protected JComboBox cbbProjectDept = null;

	private MaterialManageAction materialManageAction = null;

	protected CustomBaseAction customBaseAction = null;

	private JLabel lbSeqNo = null;

	private JLabel lbSeqNum = null;

	private JLabel lbWrapType = null;

	private JComboBox cbbWrapType = null;

	CustomsDeclarationSet other1;

	private DgBaseImportCustomsDeclaration dgBase = null;

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
	 * 报关单是否生效
	 */
	public boolean isEffective() {
		return isEffective;
	}

	/**
	 * @return Returns the projectType.
	 */
	public int getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType
	 *            The projectType to set.
	 */
	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	/**
	 * 报关单是否生效
	 */
	public void setEffective(boolean isEffective) {
		this.isEffective = isEffective;
	}

	public DgBaseImportCustomsDeclarationCommInfo() {
		super();
		impCustomsAuthorityAction = (ImpCustomsAuthorityAction) CommonVars
				.getApplicationContext().getBean("impCustomsAuthorityAction");
		bCSImpCustomsAuthorityAction = (BCSImpCustomsAuthorityAction) CommonVars
				.getApplicationContext()
				.getBean("bCSImpCustomsAuthorityAction");
		dZSCImpCustomsAuthorityAction = (DZSCImpCustomsAuthorityAction) CommonVars
				.getApplicationContext().getBean(
						"dZSCImpCustomsAuthorityAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		initialize();
		// 初始化包装种类
		List listwarp = customBaseAction.findWrap();
		this.cbbWrapType.setModel(new DefaultComboBoxModel(listwarp.toArray()));
		this.cbbWrapType.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbWrapType, "code", "name");
		cbbWrapType.setSelectedItem(null);
		// 这里是控制焦点的顺序，以方便键盘的输！
		List<Object> components = new ArrayList<Object>();
		components.add(this.tfCommAmount);
		components.add(null);
		components.add(this.cbbLevyMode);
		components.add(this.tfNetWeight);
		components.add(null);
		components.add(this.tfDetailCommSpec);
		components.add(this.btnSave);
		components.add(this.btnNext);
		this.setComponentFocusList(components);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	protected void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setEffective(false);
		this.setTitle("进口报关单商品信息编辑");
		this.setContentPane(getJContentPane());
		this.setSize(500, 455);
		tfCommAmount.setFocusable(true);
		tfCommAmount.requestFocus();
		// tfVersion.setFocusable(false);//edit by cjb 2009.9.25
		// tfDetailCommSpec.setFocusable(false);
		btnPrevious.setFocusable(false);
		// DgBaseImportCustomsDeclaration.getDgBaseImportCustomsDeclaration().setMerd(this);

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	protected JPanel getJContentPane() {
		if (jContentPane == null) {
			lbSeqNum = new JLabel();
			lbSeqNum.setBounds(new java.awt.Rectangle(281, 9, 81, 21));
			lbSeqNum.setForeground(java.awt.Color.red);
			lbSeqNum.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			lbSeqNum.setText("");
			lbSeqNo = new JLabel();
			lbSeqNo.setBounds(new java.awt.Rectangle(226, 9, 49, 21));
			lbSeqNo.setForeground(java.awt.Color.red);
			lbSeqNo.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			lbSeqNo.setText("序号：");
			lbCurrItemNum = new JLabel();
			lbCurrItemNo = new JLabel();
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			lbCurrItemNo.setBounds(40, 9, 79, 19);
			lbCurrItemNo.setText("当前项号：");
			lbCurrItemNo.setFont(new java.awt.Font("Dialog",
					java.awt.Font.BOLD, 14));
			lbCurrItemNo.setForeground(java.awt.Color.red);
			lbCurrItemNum.setBounds(124, 9, 100, 20);
			lbCurrItemNum.setText("");
			lbCurrItemNum.setFont(new java.awt.Font("Dialog",
					java.awt.Font.BOLD, 14));
			lbCurrItemNum.setForeground(java.awt.Color.red);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getBtnSave(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(lbCurrItemNo, null);
			jContentPane.add(lbCurrItemNum, null);
			jContentPane.add(getBtnPrevious(), null);
			jContentPane.add(getBtnNext(), null);
			jContentPane.add(getBtnEdit(), null);
			jContentPane.add(lbSeqNo, null);
			jContentPane.add(lbSeqNum, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	protected JPanel getJPanel() {
		if (jPanel == null) {
			lbVersion = new JLabel();
			lbVersion.setBounds(new Rectangle(259, 267, 55, 18));
			lbVersion.setText("版本");
			lbWrapType = new JLabel();
			lbWrapType.setBounds(new Rectangle(259, 240, 56, 20));
			lbWrapType.setText("包装种类");
			lbProjectDept = new JLabel();
			lbProjectDept.setBounds(new Rectangle(259, 191, 37, 18));
			lbProjectDept.setText("事业部");
			lbDetailCommSpec = new JLabel();
			lbDetailCommSpec.setBounds(new Rectangle(11, 289, 81, 21));
			lbDetailCommSpec.setText("备注");
			lbBoxNo = new JLabel();
			jPanel = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel5 = new JLabel();
			lbSecondAmount = new JLabel();
			lbUnit = new JLabel();
			lbLegalUnit = new JLabel();
			lbSecondLegalUnit = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel10 = new JLabel();
			javax.swing.JLabel jLabel11 = new JLabel();
			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel13 = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(3, 36, 489, 323);
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel.setBounds(11, 11, 57, 20);
			jLabel.setText("名称");
			jLabel1.setBounds(259, 11, 57, 20);
			jLabel1.setText("手册规格");
			jLabel2.setBounds(11, 60, 57, 20);
			jLabel2.setText("数量");
			jLabel3.setBounds(11, 86, 57, 20);
			jLabel3.setText("单价");
			jLabel4.setBounds(259, 84, 28, 20);
			jLabel4.setText("金额");
			jLabel5.setBounds(11, 118, 81, 20);
			jLabel5.setText("第一法定数量");
			lbSecondAmount.setBounds(11, 143, 81, 20);
			lbSecondAmount.setText("第二法定数量");
			lbUnit.setBounds(259, 59, 45, 20);
			lbUnit.setText("千克");
			lbSecondLegalUnit.setBounds(259, 141, 48, 20);
			lbSecondLegalUnit.setText("JLabel");
			lbLegalUnit.setBounds(259, 116, 49, 21);
			lbLegalUnit.setText("JLabel");
			jPanel.add(lbSecondLegalUnit, null);
			jPanel.add(lbLegalUnit, null);
			jLabel8.setBounds(11, 165, 48, 19);
			jLabel8.setText("原产国");
			jLabel8.setForeground(java.awt.Color.blue);
			jLabel9.setBounds(259, 166, 30, 19);
			jLabel9.setText("用途");
			jLabel9.setForeground(java.awt.Color.blue);
			jLabel10.setBounds(11, 189, 79, 20);
			jLabel10.setText("征减免税方式");
			jLabel10.setForeground(java.awt.Color.blue);
			jLabel11.setBounds(11, 212, 41, 20);
			jLabel11.setText("净重");
			jLabel12.setBounds(259, 215, 47, 20);
			jLabel12.setText("毛重");
			jLabel13.setBounds(11, 241, 40, 20);
			jLabel13.setText("件数");
			lbBoxNo.setBounds(11, 266, 43, 18);
			lbBoxNo.setText("箱号");
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
			jPanel.add(lbSecondAmount, null);
			jPanel.add(lbUnit, null);
			jPanel.add(jLabel8, null);
			jPanel.add(getCbbProduceCountry(), null);
			jPanel.add(jLabel9, null);
			jPanel.add(getCbbUses(), null);
			jPanel.add(jLabel10, null);
			jPanel.add(getCbbLevyMode(), null);
			jPanel.add(jLabel11, null);
			jPanel.add(jLabel12, null);
			jPanel.add(jLabel13, null);
			jPanel.add(getTfLegalAmount(), null);
			jPanel.add(getTfSecondLegalAmount(), null);
			jPanel.add(getTfNetWeight(), null);
			jPanel.add(getTfGrossWeight(), null);
			jPanel.add(getTfPieces(), null);
			jPanel.add(lbBoxNo, null);
			jPanel.add(getTfBoxNo(), null);
			jPanel.add(lbDetailCommSpec, null);
			jPanel.add(getTfDetailCommSpec(), null);
			jPanel.add(lbProjectDept, null);
			jPanel.add(getCbbProjectDept(), null);
			jPanel.add(lbWrapType, null);
			jPanel.add(getCbbWrapType(), null);
			jPanel.add(lbVersion, null);
			jPanel.add(getTfVersion(), null);
			jPanel.add(getLblNewLabel());
			jPanel.add(getTfDeclareSpec());
		}
		return jPanel;
	}

	/**
	 * This method initializes tfCommName
	 * 
	 * @return javax.swing.JTextField
	 */
	protected JTextField getTfCommName() {
		if (tfCommName == null) {
			tfCommName = new JTextField();
			tfCommName.setBounds(84, 10, 125, 21);
			tfCommName.setEditable(false);
		}
		return tfCommName;
	}

	// 计算第一法定数量、第二法定数量:
	// 条件1：当申报单位与第一法定单位、第二法定单位相同时第一法定数量与第二法定数量等于申报数量
	// 条件2：当申报单位不等于千克，第一法定数量为千克，第二法定数量为千克时第一法定数量与第二法定数量等于净重
	// 条件3：当条件1与条件2不满足时，申报单位、第一法定单位，第二法定单位根据UnitCollate(计量单折算)实体得出相应的比例
	// 条件4: 当条件1与条件2、条件3都不满足时，申报单位、第一法定单位，第二法定单位根据帐册备的比例因子来计算
	protected void getFirstAmount() {
		double amount = 0;
		if (this.tfCommAmount.getText() != null
				&& !this.tfCommAmount.getText().equals("")) {
			amount = Double.parseDouble(this.tfCommAmount.getText().toString());
		}
		String unit = lbUnit.getText();
		String legalUnit = lbLegalUnit.getText();
		String secondUnit = lbSecondLegalUnit.getText();

		if (unit.equals(legalUnit)) {
			tfLegalAmount.setValue(Double.valueOf(amount));
		} else if (getUnitRateMap().get(unit + "+" + legalUnit) != null) {// 条件3
			Double unitRate = Double.parseDouble(getUnitRateMap().get(
					unit + "+" + legalUnit).toString());
			tfLegalAmount
					.setValue(amount * (unitRate == null ? 0.0 : unitRate));
		} else {
			tfLegalAmount.setValue(customsDeclarationCommInfo
					.getLegalUnitGene() == null ? 0.0
					: customsDeclarationCommInfo.getLegalUnitGene()
							.doubleValue() * Double.valueOf(amount));
		}
		if (unit.equals(secondUnit)) {
			tfSecondLegalAmount.setValue(Double.valueOf(amount));
		} else if (getUnitRateMap().get(unit + "+" + secondUnit) != null) {
			Double unitRate = Double.parseDouble(getUnitRateMap().get(// 条件3
					unit + "+" + secondUnit).toString());
			tfSecondLegalAmount.setValue(amount
					* (unitRate == null ? 0.0 : unitRate));
		} else {
			tfSecondLegalAmount.setValue(customsDeclarationCommInfo
					.getLegalUnit2Gene() == null ? 0.0
					: customsDeclarationCommInfo.getLegalUnit2Gene()
							.doubleValue() * Double.valueOf(amount));
		}
	}

	public Map getUnitRateMap() {
		Map unitRateMap = baseEncAction.findAllUnitRateMap(new Request(
				CommonVars.getCurrUser()));
		return unitRateMap;
	}

	/**
	 * This method initializes tfCommSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	protected JTextField getTfCommSpec() {
		if (tfCommSpec == null) {
			tfCommSpec = new JTextField();
			tfCommSpec.setBounds(319, 11, 160, 21);
			tfCommSpec.setEditable(false);
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
			tfCommAmount.setBounds(84, 59, 125, 20);
			tfCommAmount.addPropertyChangeListener("value",
					new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent evt) {
							tfCommTotalPrice.setValue(getTotalPrice());
							getFirstAmount();
						}

					});
		}
		return tfCommAmount;
	}

	/**
	 * 检查是不是已经禁用
	 * 
	 * @return
	 */
	public boolean checkCommodityForbid() {
		try {
			Integer.valueOf(tfVersion.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "版本号应该为数字型", "提示", 0);
			return true;
		}
		List list = materialManageAction.findVersionNo(
				new Request(CommonVars.getCurrUser()),
				Integer.valueOf(lbSeqNum.getText()),
				Integer.valueOf(tfVersion.getText()));
		if (list != null && list.size() > 0 && list.get(0) != null) {

		} else {
			JOptionPane.showMessageDialog(this, "该版本号在电子帐册中未备案", "提示", 0);
			return true;
		}
		CommdityForbid commodity = materialManageAction.findCommdityForbid(
				new Request(CommonVars.getCurrUser()),
				MaterielType.FINISHED_PRODUCT, lbSeqNum.getText().toString(),
				tfVersion.getText().toString());

		if (commodity != null) {
			if (JOptionPane.showConfirmDialog(this, "该版本已经被禁用,是否继续", "确认", 0) != 0) {
				return true;
			}

		}
		return false;
	}

	/**
	 * 检查是不是限制类商品并且是否超出了限制类商品中定义的数量
	 * 
	 * @return
	 */
	public boolean checkRerictCommodity() {
		double aamount = 0;
		double Amount = 0;
		Boolean isMaterial = EncCommon.isMaterial(customsDeclarationCommInfo
				.getBaseCustomsDeclaration().getImpExpType());
		RestirictCommodity commodity = materialManageAction
				.findRerictCommodity(new Request(CommonVars.getCurrUser()),
						isMaterial, lbSeqNum.getText().toString());
		if (commodity != null) {
			// 得到报关单中的数量
			Double DeclarationCommInfo = materialManageAction
					.findCustomsDeclarationCommInfo(
							new Request(CommonVars.getCurrUser()), isMaterial,
							lbSeqNum.getText().toString(),
							customsDeclarationCommInfo);
			Amount = DeclarationCommInfo
					+ Double.valueOf(tfCommAmount.getText().toString());
			if (commodity.getAmount() != null
					&& !commodity.getAmount().equals("")) {
				aamount = Double.parseDouble(commodity.getAmount().toString());
				if (Amount > aamount) {
					JOptionPane.showMessageDialog(this,
							"备案号：" + lbSeqNum.getText() + "已进数量[" + Amount
									+ "] 超出了限制类商品中备的数量[" + aamount + "]!\n",
							"提示", 0);
					tfCommAmount.requestFocus();
					return true;
				}

			}
		}
		return false;
	}

	/**
	 * This method initializes cbbProduceCountry
	 * 
	 * @return javax.swing.JComboBox
	 */
	protected JComboBox getCbbProduceCountry() {
		if (cbbProduceCountry == null) {
			cbbProduceCountry = new JComboBox();
			cbbProduceCountry.setBounds(84, 165, 125, 20);
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
			cbbUses.setBounds(319, 165, 160, 20);
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
			cbbLevyMode.setBounds(84, 189, 125, 20);
		}
		return cbbLevyMode;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(178, 391, 59, 24);
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						impCustomsAuthorityAction.saveCommodity(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCImpCustomsAuthorityAction
								.saveCommodity(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSImpCustomsAuthorityAction.saveCommodity(new Request(
								CommonVars.getCurrUser()));
					}
					if (checkNull()) {
						return;
					}
					if (projectType == ProjectType.BCUS) {
						if (customsDeclarationCommInfo != null) {
							if (customsDeclarationCommInfo
									.getBaseCustomsDeclaration()
									.getImpExpType() != ImpExpType.BACK_FACTORY_REWORK) {
								if (checkRerictCommodity()) {
									return;
								}
							} else {
								//
								// 是退厂返工
								//
								if (checkCommodityForbid()) {
									return;
								}
							}
						}
					}
					if (!checkAmount()) {
						return;
					}
					fillData();
					if (CommonVars.commonCheckNull(
							DgBaseImportCustomsDeclarationCommInfo.this,
							customsDeclarationCommInfo)) {
						return;
					}
					customsDeclarationCommInfo = baseEncAction
							.saveCustomsDeclarationCommInfo(new Request(
									CommonVars.getCurrUser()),
									customsDeclarationCommInfo);
					tableModel.updateRow(customsDeclarationCommInfo);
					dataState = DataState.BROWSE;
					setState();
					btnPrevious.requestFocus(true);
				}
			});
			btnSave.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {

					if (e.getKeyCode() == 10) {// Enter
						if (e.getSource().getClass().getName().toLowerCase()
								.indexOf("button") >= 0) {
							JButton btn = (JButton) e.getSource();
							btn.dispatchEvent(new KeyEvent((Container) e
									.getSource(), KeyEvent.KEY_PRESSED, 0, 0,
									KeyEvent.VK_SPACE, e.getKeyChar()));
							btn.dispatchEvent(new KeyEvent((Container) e
									.getSource(), KeyEvent.KEY_RELEASED, 0, 0,
									KeyEvent.VK_SPACE, e.getKeyChar()));
							if (getBtnNext().isEnabled()) {
								getBtnNext().requestFocus();
							} else {
								getBtnEdit().requestFocus();
							}
						}
					}
				}
			});
		}
		return btnSave;
	}

	/**
	 * 检查栏位的值是否为空
	 * 
	 * @return
	 */
	public boolean checkNull() {

		// if (this.tfDeclareSpec.getText().trim().equals("")) {
		// JOptionPane.showMessageDialog(null, "规范申报规格不能为空!", "提示!!",
		// JOptionPane.INFORMATION_MESSAGE);
		// return true;
		// }

		if (cbbProduceCountry.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "最终目的国不能为空！", "提示！", 0);
			cbbProduceCountry.requestFocus();
			return true;
		}
		if (cbbUses.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "用途不能为空！", "提示！", 0);
			cbbUses.requestFocus();
			return true;
		}
		if (cbbLevyMode.getSelectedItem() == null) {

			JOptionPane.showMessageDialog(this, "征减免税方式不能为空！", "提示！", 0);
			cbbLevyMode.requestFocus();
			return true;

		}

		if (!tfCommAmount.getText().trim().equals("")) {
			if (Double.valueOf(tfCommAmount.getText().trim()).doubleValue() <= 0) {
				JOptionPane.showMessageDialog(this, "数量不能小于或等于 0！", "提示！", 0);
				tfCommAmount.requestFocus();
				return true;
			}
			if (this.tfLegalAmount.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "第一法定数量不能为空！", "提示！", 0);
				tfLegalAmount.requestFocus();
				return true;
			}
			if (Double.valueOf(tfLegalAmount.getText().trim()).doubleValue() <= 0) {
				JOptionPane.showMessageDialog(this, "第一法定数量不能小于或等于 0！", "提示！",
						0);
				tfLegalAmount.requestFocus();
				return true;
			}
		} else {
			JOptionPane.showMessageDialog(this, "数量不合法或为空！", "提示！", 0);
			tfCommAmount.requestFocus();
			return true;
		}
		if (!tfCommUnitPrice.getText().trim().equals("")) {
			if (Double.valueOf(tfCommUnitPrice.getText().trim()).doubleValue() <= 0) {
				JOptionPane.showMessageDialog(this, "单价不能小于或等于 0！", "提示！", 0);
				tfCommUnitPrice.requestFocus();
				return true;
			}
		} else {
			JOptionPane.showMessageDialog(this, "单价不合法或为空！", "提示！", 0);
			tfCommUnitPrice.requestFocus();
			return true;
		}
		if (!tfCommTotalPrice.getText().trim().equals("")) {
			if (Double.valueOf(tfCommTotalPrice.getText().trim()).doubleValue() <= 0) {
				JOptionPane.showMessageDialog(this, "金额不能小于或等于 0！", "提示！", 0);
				tfCommTotalPrice.requestFocus();
				return true;
			}
		} else {
			JOptionPane.showMessageDialog(this, "金额不合法或为空！", "提示！", 0);
			tfCommTotalPrice.requestFocus();
			return true;
		}

		if (tfLegalAmount.getText() == null
				|| tfLegalAmount.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "第一法定数量没有填写或为空", "提示！", 0);
			tfLegalAmount.requestFocus();
			return true;
		}
		if (customsDeclarationCommInfo instanceof CustomsDeclarationCommInfo) {
			if (customsDeclarationCommInfo.getBaseCustomsDeclaration() != null
					&& (customsDeclarationCommInfo.getBaseCustomsDeclaration()
							.getImpExpType().intValue() == ImpExpType.DIRECT_EXPORT
							|| customsDeclarationCommInfo
									.getBaseCustomsDeclaration()
									.getImpExpType().intValue() == ImpExpType.TRANSFER_FACTORY_EXPORT
							|| customsDeclarationCommInfo
									.getBaseCustomsDeclaration()
									.getImpExpType().intValue() == ImpExpType.REWORK_EXPORT || customsDeclarationCommInfo
							.getBaseCustomsDeclaration().getImpExpType()
							.intValue() == ImpExpType.BACK_FACTORY_REWORK)) {
				if ("".equals(tfVersion.getText())) {
					JOptionPane.showMessageDialog(this, "版本号不可为空！", "提示！", 0);
					lbVersion.requestFocus();
					return true;
				}
			}
		}
		Double netweight = Double.valueOf(0);
		Double gossweight = Double.valueOf(0);
		if (this.tfNetWeight.getValue() != null) {
			netweight = Double.valueOf(this.tfNetWeight.getValue().toString());
		}
		if (this.tfGrossWeight.getValue() != null) {
			gossweight = Double.valueOf(this.tfGrossWeight.getValue()
					.toString());
		}
		if (netweight > gossweight) {
			JOptionPane.showMessageDialog(this, "对不起，净重不可以大于毛重！", "提示！", 0);
			tfNetWeight.requestFocus();
			return true;
		}
		return false;
	}

	/**
	 * 检查毛、净重
	 * 
	 * @return
	 */
	protected boolean checkNetweightAndGrossweight() {
		double netWeight = this.tfNetWeight.getValue() == null ? 0 : Double
				.valueOf(this.tfNetWeight.getValue().toString()).doubleValue();
		double grossWeight = this.tfGrossWeight.getValue() == null ? 0 : Double
				.valueOf(this.tfGrossWeight.getValue().toString())
				.doubleValue();
		if (netWeight - grossWeight > 0) {
			return false;
		}
		return true;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(242, 391, 59, 24);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showData();
					dataState = DataState.BROWSE;
					setState();
				}
			});
			// btnCancel.addKeyListener(new CustomKeyAdapter(getBtnEdit()));
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
			tfCommUnitPrice.setBounds(84, 85, 125, 20);
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
			tfCommTotalPrice.setBounds(319, 85, 160, 20);
			tfCommTotalPrice.addKeyListener(new java.awt.event.KeyAdapter() {
				@SuppressWarnings("static-access")
				public void keyPressed(java.awt.event.KeyEvent e) {
					String sKey = e.getKeyText(e.getKeyCode());
					if (sKey.equalsIgnoreCase("Enter")) {// 为0时或为空才进行计算
						tfCommUnitPrice.setValue(getUnitPrice());
					}
				}
			});
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
			tfLegalAmount.setBounds(84, 117, 125, 20);
			// tfLegalAmount.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfLegalAmount;
	}

	/**
	 * This method initializes tfSecondLegalAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	protected JFormattedTextField getTfSecondLegalAmount() {
		if (tfSecondLegalAmount == null) {
			tfSecondLegalAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfSecondLegalAmount.setBounds(84, 142, 125, 20);
			// tfSecondLegalAmount
			// .setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfSecondLegalAmount;
	}

	/**
	 * This method initializes tfNetWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	protected JFormattedTextField getTfNetWeight() {
		if (tfNetWeight == null) {
			tfNetWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfNetWeight.setBounds(84, 215, 125, 20);
			// tfNetWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfNetWeight;
	}

	/**
	 * This method initializes tfGrossWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	protected JFormattedTextField getTfGrossWeight() {
		if (tfGrossWeight == null) {
			tfGrossWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfGrossWeight.setBounds(319, 215, 160, 20);
			// tfGrossWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfGrossWeight;
	}

	/**
	 * This method initializes tfPieces
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	protected JFormattedTextField getTfPieces() {
		if (tfPieces == null) {
			tfPieces = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfPieces.setBounds(84, 240, 125, 20);
			// tfPieces.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfPieces;
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
		if (tableModel.getCurrRowCount() + 1 == tableModel.getList().size()) {
			isShowAdd = true;
		} else {
			isShowAdd = false;
		}
	}

	/**
	 * 显示窗口
	 */
	public void setVisible(boolean b) {
		if (b) {
			// totalCount = tableModel.getRowCount(); // 总记录数
			customsDeclarationCommInfo = (BaseCustomsDeclarationCommInfo) tableModel
					.getCurrentRow();
			// rowCount = tableModel.getCurrRowCount();// 当前记录
			initUIComponents();
			showData();
			setState();
		}
		super.setVisible(b);
	}

	/**
	 * 初始化UI
	 */
	protected void initUIComponents() {
		// 初始化起运国
		this.cbbProduceCountry.setModel(CustomBaseModel.getInstance()
				.getCountryModel());
		this.cbbProduceCountry.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbProduceCountry);
		// 初始化征免性质
		this.cbbLevyMode.setModel(CustomBaseModel.getInstance()
				.getLevymodeModel());
		this.cbbLevyMode
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbLevyMode);

		// 根据参数设置纸质手册报关单中的单价状态
		tfCommUnitPrice.setEditable((projectType == ProjectType.BCS)
				&& CommonVars.getIsCanModifyUnitPrice());

		// 初始化用途
		this.cbbUses.setModel(CustomBaseModel.getInstance().getUseModel());
		this.cbbUses.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbUses);
		if (EncCommon.isMaterial1(customsDeclarationCommInfo
				.getBaseCustomsDeclaration().getImpExpType())) {
			lbDetailCommSpec.setVisible(true);
			tfDetailCommSpec.setVisible(true);
			lbVersion.setVisible(false);
			tfVersion.setVisible(false);
		} else {
			lbBoxNo.setVisible(true);
			tfBoxNo.setVisible(true);
			lbDetailCommSpec.setVisible(false);
			tfDetailCommSpec.setVisible(false);
			tfVersion.setVisible(true);
			lbVersion.setVisible(true);
		}
		// 事业部
		List projectList = materialManageAction.findProjectDept(new Request(
				CommonVars.getCurrUser(), true));
		this.cbbProjectDept.setModel(new DefaultComboBoxModel(projectList
				.toArray()));
		this.cbbProjectDept.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbProjectDept, "code", "name");
		cbbProjectDept.setSelectedIndex(-1);
		CompanyOther other = CommonVars.getOther();

		CustomFormattedTextFieldUtils
				.setFormatterFactory(this.tfGrossWeight, 4);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfNetWeight, 4);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfPieces, 0);
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
		// }
		// else {
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
		// 单价
		if (other != null) {
			CustomFormattedTextFieldUtils.setFormatterFactory(
					this.tfCommUnitPrice, other.getCustomPriceNum());
		} else {
			CustomFormattedTextFieldUtils.setFormatterFactory(
					this.tfCommUnitPrice, 4);
		}
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

	/**
	 * 设置组件的状态
	 */
	protected void setState() {
		// jButton.setEnabled(rowCount > 0);
		// btnNext.setEnabled(rowCount < totalCount - 1);
		tfBoxNo.setEditable(dataState != DataState.BROWSE && !this.isEffective);
		tfDetailCommSpec.setEditable(dataState != DataState.BROWSE
				&& !this.isEffective);
		this.tfCommAmount.setEditable(dataState != DataState.BROWSE
				&& !this.isEffective);
		tfLegalAmount.setEditable(dataState != DataState.BROWSE
				&& !this.isEffective);

		tfDeclareSpec.setEditable(dataState != DataState.BROWSE
				&& !this.isEffective);

		// 根据参数设置纸质手册报关单中的单价状态
		if (projectType == ProjectType.BCS) {
			if (CommonVars.getIsCanModifyUnitPrice()) {
				this.tfCommUnitPrice.setEditable(dataState != DataState.BROWSE
						&& !this.isEffective);

			} else {
				tfCommUnitPrice.setEditable(CommonVars
						.getIsCanModifyUnitPrice());
			}
		} else {
			this.tfCommUnitPrice.setEditable(dataState != DataState.BROWSE
					&& !this.isEffective);
		}

		tfCommTotalPrice.setEditable(dataState != DataState.BROWSE
				&& !this.isEffective);
		this.tfNetWeight.setEditable(dataState != DataState.BROWSE
				&& !this.isEffective);
		this.tfGrossWeight.setEditable(dataState != DataState.BROWSE
				&& !this.isEffective);
		this.tfPieces.setEditable(dataState != DataState.BROWSE
				&& !this.isEffective);
		this.cbbProduceCountry.setEnabled(dataState != DataState.BROWSE
				&& !this.isEffective);
		this.cbbWrapType.setEnabled(dataState != DataState.BROWSE
				&& !this.isEffective);
		this.cbbUses.setEnabled(dataState != DataState.BROWSE
				&& !this.isEffective);
		this.cbbLevyMode.setEnabled(dataState != DataState.BROWSE
				&& !this.isEffective);
		this.btnSave.setEnabled(dataState != DataState.BROWSE
				&& !this.isEffective);
		this.btnSave.setEnabled(dataState != DataState.BROWSE
				&& !this.isEffective);// 保存
		btnEdit.setEnabled(dataState == DataState.BROWSE && !this.isEffective); // 修改
		btnCancel
				.setEnabled(dataState != DataState.BROWSE && !this.isEffective); // 取消
		tfLegalAmount.setEditable((!lbUnit.getText().equals(
				lbLegalUnit.getText()))
				&& (dataState != DataState.BROWSE && !this.isEffective));
		tfSecondLegalAmount.setEditable((!lbUnit.getText().equals(
				lbSecondLegalUnit.getText()))
				&& (dataState != DataState.BROWSE && !this.isEffective));

		lbSecondAmount
				.setVisible(customsDeclarationCommInfo.getSecondLegalUnit() != null
						&& customsDeclarationCommInfo.getSecondLegalUnit()
								.getCode() != null
						&& !customsDeclarationCommInfo.getSecondLegalUnit()
								.getCode().equals(""));
		tfSecondLegalAmount
				.setVisible(customsDeclarationCommInfo.getSecondLegalUnit() != null
						&& customsDeclarationCommInfo.getSecondLegalUnit()
								.getCode() != null
						&& !customsDeclarationCommInfo.getSecondLegalUnit()
								.getCode().equals(""));

		tfVersion.setEditable(dataState != DataState.BROWSE
				&& !this.isEffective);
		this.cbbProjectDept.setEnabled(dataState != DataState.BROWSE
				&& !this.isEffective); // 事业部
	}

	/**
	 * 数据显示
	 */
	protected void showData() {
		if (other1 != null && customsDeclarationCommInfo != null) {
			// 默认用途--取报关单参数设置
			if (customsDeclarationCommInfo.getUses() == null) {
				customsDeclarationCommInfo.setUses(other1.getUses());
			}
			// 减免方式--取报关单参数设置
			if (customsDeclarationCommInfo.getLevyMode() == null) {
				customsDeclarationCommInfo.setLevyMode(other1.getLevyMode());
			}
			// 包装种类--取报关单参数设置
			if (customsDeclarationCommInfo.getWrapType() == null) {
				customsDeclarationCommInfo
						.setWrapType(other1.getCommWrapType());
			}
			// 原产国--取报关单参数设置
			if (customsDeclarationCommInfo.getCountry() == null) {
				customsDeclarationCommInfo.setCountry(other1.getCountry());
			}
		}
		customsDeclarationCommInfo = (BaseCustomsDeclarationCommInfo) tableModel
				.getCurrentRow();
		if (customsDeclarationCommInfo == null) {
			return;
		}
		tfBoxNo.setText(customsDeclarationCommInfo.getBoxNo());
		tfDetailCommSpec.setText(customsDeclarationCommInfo.getDetailNote());// 表体备注
		lbCurrItemNum.setText(String.valueOf(customsDeclarationCommInfo
				.getSerialNumber()));
		lbSeqNum.setText(String.valueOf(customsDeclarationCommInfo
				.getCommSerialNo()));
		if (customsDeclarationCommInfo.getCommName() != null) {
			this.tfCommName.setText(customsDeclarationCommInfo.getCommName());
		}

		// 手册里面的规格
		this.tfCommSpec.setText(customsDeclarationCommInfo.getCommSpec());

		this.tfCommAmount.setValue(customsDeclarationCommInfo.getCommAmount());
		this.tfCommUnitPrice.setValue(customsDeclarationCommInfo
				.getCommUnitPrice());
		this.tfCommTotalPrice.setValue(customsDeclarationCommInfo
				.getCommTotalPrice());

		this.tfLegalAmount
				.setValue(customsDeclarationCommInfo.getFirstAmount());
		this.tfSecondLegalAmount.setValue(customsDeclarationCommInfo
				.getSecondAmount());

		if (customsDeclarationCommInfo.getCountry() != null) {
			this.cbbProduceCountry.setSelectedItem(customsDeclarationCommInfo
					.getCountry());
		} else {
			this.cbbProduceCountry.setSelectedIndex(-1);
		}
		if (customsDeclarationCommInfo.getUses() != null) {
			this.cbbUses.setSelectedItem(customsDeclarationCommInfo.getUses());
		} else {
			this.cbbUses.setSelectedIndex(-1);
		}
		if (customsDeclarationCommInfo.getLevyMode() != null) {
			this.cbbLevyMode.setSelectedItem(customsDeclarationCommInfo
					.getLevyMode());
		} else {
			this.cbbLevyMode.setSelectedIndex(-1);
		}
		this.tfNetWeight.setValue(customsDeclarationCommInfo.getNetWeight());
		this.tfGrossWeight
				.setValue(customsDeclarationCommInfo.getGrossWeight());
		this.tfPieces.setValue(customsDeclarationCommInfo.getPieces());
		if (customsDeclarationCommInfo.getUnit() != null) {
			this.lbUnit.setText(customsDeclarationCommInfo.getUnit().getName());
		} else {
			this.lbUnit.setText("");
		}
		if (customsDeclarationCommInfo.getLegalUnit() != null) {
			this.lbLegalUnit.setText(customsDeclarationCommInfo.getLegalUnit()
					.getName());
		} else {
			this.lbLegalUnit.setText("");
		}
		if (customsDeclarationCommInfo.getSecondLegalUnit() != null) {
			this.lbSecondLegalUnit.setText(customsDeclarationCommInfo
					.getSecondLegalUnit().getName());
		} else {
			this.lbSecondLegalUnit.setText("");
		}
		// 事业部
		if (customsDeclarationCommInfo.getProjectDept() != null) {
			this.cbbProjectDept.setSelectedItem(customsDeclarationCommInfo
					.getProjectDept());
		} else {
			this.cbbProjectDept.setSelectedIndex(-1);
		}
		this.tfVersion.setText(customsDeclarationCommInfo.getVersion());
		this.cbbWrapType.setSelectedItem(customsDeclarationCommInfo
				.getWrapType());
		this.tfBoxNo.setText(customsDeclarationCommInfo.getBoxNo());
		// List isSpecification = baseEncAction.findIsSpecification(new
		// Request(CommonVars.getCurrUser()));
		// Boolean blIsSpecification = (Boolean) isSpecification.get(0);
		// // 规范申报规格
		// if(blIsSpecification.equals(true)){
		tfDeclareSpec.setText(customsDeclarationCommInfo.getDeclareSpec());
		// }
	}

	/**
	 * 填值
	 */
	protected void fillData() {
		customsDeclarationCommInfo.setCommSpec(this.tfCommSpec.getText());
		customsDeclarationCommInfo.setBoxNo(tfBoxNo.getText());
		customsDeclarationCommInfo.setVersion(tfVersion.getText());
		customsDeclarationCommInfo.setDetailNote(tfDetailCommSpec.getText());
		if (this.tfCommAmount.getValue() != null) {
			customsDeclarationCommInfo.setCommAmount(Double
					.valueOf(this.tfCommAmount.getText().trim().toString()));
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
			customsDeclarationCommInfo
					.setCommTotalPrice(Double.valueOf(this.tfCommTotalPrice
							.getText().trim().toString()));
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
		// 重写第一法定比例因子与第二法定比例因子
		if (this.tfCommAmount.getValue() != null) {
			double Legalamount = 0;
			double Legal2amount = 0;
			BigDecimal bid = new BigDecimal(Double.valueOf(tfLegalAmount
					.getValue() == null ? "0" : this.tfLegalAmount.getText()
					.toString())
					/ Double.valueOf(tfCommAmount.getText().toString()));

			BigDecimal bid2 = new BigDecimal(Double.valueOf(tfSecondLegalAmount
					.getValue() == null ? "0" : tfSecondLegalAmount.getText()
					.toString())
					/ Double.valueOf(tfCommAmount.getText().toString()));
			Legalamount = bid.setScale(9, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			Legal2amount = bid2.setScale(9, BigDecimal.ROUND_HALF_UP)
					.doubleValue();

			customsDeclarationCommInfo.setLegalUnitGene(Legalamount);
			customsDeclarationCommInfo.setLegalUnit2Gene(Legal2amount);
		} else {
			customsDeclarationCommInfo.setLegalUnitGene(null);
			customsDeclarationCommInfo.setLegalUnit2Gene(null);
		}
		if (this.cbbProduceCountry.getSelectedItem() != null) {
			customsDeclarationCommInfo
					.setCountry((Country) this.cbbProduceCountry
							.getSelectedItem());
		} else {
			customsDeclarationCommInfo.setCountry(null);
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
		if (this.tfPieces.getValue() != null) {
			customsDeclarationCommInfo.setPieces(Double.valueOf(
					this.tfPieces.getText().toString()).intValue());
		} else {
			customsDeclarationCommInfo.setPieces(null);
		}
		// 事业部
		if (this.cbbProjectDept.getSelectedItem() != null) {
			customsDeclarationCommInfo
					.setProjectDept((ProjectDept) this.cbbProjectDept
							.getSelectedItem());
		} else {
			customsDeclarationCommInfo.setProjectDept(null);
		}
		customsDeclarationCommInfo.setWrapType((Wrap) this.cbbWrapType
				.getSelectedItem());

		customsDeclarationCommInfo.setBoxNo(tfBoxNo.getText());
		// 第一次新增设置创建时间
		if (customsDeclarationCommInfo.getCreateDate() == null
				|| "".equals(customsDeclarationCommInfo.getCreateDate())) {
			customsDeclarationCommInfo.setCreateDate(new Date());
		}

		// 规范申报规格
		customsDeclarationCommInfo.setDeclareSpec(tfDeclareSpec.getText());
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	// 此方法有问题
	// protected DefaultFormatterFactory getDefaultFormatterFactory() {
	// if (defaultFormatterFactory == null) {
	// defaultFormatterFactory = new DefaultFormatterFactory();
	// defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
	// defaultFormatterFactory.setEditFormatter(getNumberFormatter());
	// }
	// return defaultFormatterFactory;
	// }
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
	 * 计算总价（金额）
	 * 
	 * @return
	 */
	protected Double getTotalPrice() {
		double amount = 0;
		double unitPrice = 0;
		if (this.tfCommAmount.getText() != null
				&& !this.tfCommAmount.getText().equals("")) {
			amount = Double.parseDouble(this.tfCommAmount.getText().toString());
		}
		if (this.tfCommUnitPrice.getText() != null
				&& !this.tfCommUnitPrice.getText().equals("")) {
			unitPrice = Double.parseDouble(this.tfCommUnitPrice.getText()
					.toString());
		}
		BigDecimal bd = new BigDecimal(amount * unitPrice);
		String totalPrice = bd.setScale(4, BigDecimal.ROUND_HALF_UP).toString();
		return Double.valueOf(totalPrice);
	}

	/**
	 * This method initializes btnPrevious
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setBounds(48, 391, 59, 24);
			btnPrevious.setText("上条");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (rollSave()) {
						return;
					}
					if (!tableModel.previousRow()) {
						btnPrevious.setEnabled(false);
						btnNext.setEnabled(true);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					if (isBcusChaKan) {
						dataState = DataState.EDIT;
					}
					showData();
					setState();
				}
			});
		}
		return btnPrevious;
	}

	/**
	 * 依次检查数据、结果返回True or False
	 * 
	 * @return
	 */
	private boolean rollSave() {
		if (dataState == DataState.EDIT) {
			if (checkNull()) {
				return true;
			}
			if (projectType == ProjectType.BCUS) {
				if (customsDeclarationCommInfo != null) {
					if (customsDeclarationCommInfo.getBaseCustomsDeclaration()
							.getImpExpType() != ImpExpType.BACK_FACTORY_REWORK) {
						if (checkRerictCommodity()) {
							return true;
						}
					} else {
						//
						// 是退厂返工
						//
						if (checkCommodityForbid()) {
							// tfVersion.setText("");
							// tfVersion.requestFocus();
							return true;
						}
					}
				}
			}
			// if (checkRerictCommodity()) {
			// return true;
			// }
			// if (checkCommodityForbid()) {
			// return true;
			// }
			if (!checkAmount()) {
				return true;
			}
			fillData();
			if (CommonVars.commonCheckNull(
					DgBaseImportCustomsDeclarationCommInfo.this,
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

	private boolean isShowAdd = false;
	private boolean isBcusChaKan = true;

	protected JLabel lbVersion = null;

	protected JTextField tfVersion = null;
	private JLabel lblNewLabel;
	private JTextField tfDeclareSpec;

	public boolean isBcusChaKan() {
		return isBcusChaKan;
	}

	public void setBcusChaKan(boolean isBcusChaKan) {
		this.isBcusChaKan = isBcusChaKan;
	}

	/**
	 * This method initializes btnNext
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setBounds(306, 391, 59, 24);
			btnNext.setText("下条");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (rollSave()) {
						return;
					}
					if (isShowAdd && isBcusChaKan) {
						if (tableModel.getList().size() < 20) {
							DgBaseImportCustomsDeclarationCommInfo.this
									.setVisible(false);
							dgBase.btnAdd.doClick();
							DgBaseImportCustomsDeclarationCommInfo.this
									.dispose();
						} else {
							JOptionPane
									.showMessageDialog(
											DgBaseImportCustomsDeclarationCommInfo.this,
											"商品项数已达20项");
							DgBaseImportCustomsDeclarationCommInfo.this
									.dispose();
							return;
						}
					}
					if (!tableModel.nextRow()) {
						btnPrevious.setEnabled(true);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					if (isBcusChaKan) {
						dataState = DataState.EDIT;
					}
					showData();
					setState();
					if (tableModel.getCurrRowCount() + 1 == tableModel
							.getList().size()) {
						isShowAdd = true;
					} else {
						isShowAdd = false;
					}
				}
			});
		}
		return btnNext;
	}

	public boolean isShowAdd() {
		return isShowAdd;
	}

	public void setShowAdd(boolean isShowAdd) {
		this.isShowAdd = isShowAdd;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setBounds(113, 391, 59, 24);
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});
			// btnEdit.addKeyListener(new CustomKeyAdapter(getTfCommAmount()));

		}
		return btnEdit;
	}

	/**
	 * This method initializes tfBoxNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBoxNo() {
		// 2011-6-26修改为显示箱号
		if (tfBoxNo == null) {
			tfBoxNo = new JTextField();
			tfBoxNo.setBounds(84, 265, 125, 20);
		}
		return tfBoxNo;
	}

	/**
	 * 数量检查
	 * 
	 * @return
	 */
	protected boolean checkAmount() {
		return true;
	}

	/**
	 * This method initializes tfDetailCommSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDetailCommSpec() {
		if (tfDetailCommSpec == null) {
			tfDetailCommSpec = new JTextField();
			tfDetailCommSpec.setBounds(new Rectangle(84, 290, 318, 20));
		}
		return tfDetailCommSpec;
	}

	/**
	 * This method initializes cbbProjectDept
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbProjectDept() {
		if (cbbProjectDept == null) {
			cbbProjectDept = new JComboBox();
			cbbProjectDept.setBounds(new Rectangle(319, 190, 160, 20));
		}
		return cbbProjectDept;
	}

	// public static class CustomKeyAdapter extends KeyAdapter {
	// private Component component = null;
	//
	// public CustomKeyAdapter(Component component) {
	// this.component = component;
	// }
	//
	// public void keyPressed(KeyEvent e) {
	//
	// if (e.getKeyCode() == 10) {// Enter
	// if (e.getSource().getClass().getName().toLowerCase().indexOf(
	// "button") >= 0) {
	// JButton btn = (JButton) e.getSource();
	// btn.dispatchEvent(new KeyEvent((Container) e.getSource(),
	// KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_SPACE, e
	// .getKeyChar()));
	// btn.dispatchEvent(new KeyEvent((Container) e.getSource(),
	// KeyEvent.KEY_RELEASED, 0, 0, KeyEvent.VK_SPACE, e
	// .getKeyChar()));
	// component.requestFocus();
	// }
	// }
	// }
	// }
	//
	// /** 清除所有焦点回车事件 */
	// private void clearButtonKyeAdapterControl(Component component) {
	// if (!(component instanceof Container)) {
	// return;
	// }
	// Container container = (Container) component;
	// for (int i = 0; i < container.getComponentCount(); i++) {
	// Component temp = container.getComponent(i);
	// if (temp instanceof JButton) {
	// KeyListener[] keyListeners = temp.getKeyListeners();
	// for (KeyListener keyListener : keyListeners) {
	// if (keyListener instanceof KeyAdapterExtend) {
	// temp.removeKeyListener(keyListener);
	// }
	// }
	// } else {
	// clearButtonKyeAdapterControl(temp);
	// }
	// }
	// }

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This method initializes cbbWrapType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWrapType() {
		if (cbbWrapType == null) {
			cbbWrapType = new JComboBox();
			cbbWrapType.setBounds(new Rectangle(319, 240, 160, 20));
		}
		return cbbWrapType;
	}

	public DgBaseImportCustomsDeclaration getDgBase() {
		return dgBase;
	}

	public void setDgBase(DgBaseImportCustomsDeclaration dgBase) {
		this.dgBase = dgBase;
	}

	/**
	 * This method initializes tfVersion
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfVersion() {
		if (tfVersion == null) {
			tfVersion = new JTextField();
			tfVersion.setBounds(new Rectangle(319, 263, 160, 20));
		}
		return tfVersion;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("规范申报规格");
			lblNewLabel.setBounds(11, 35, 72, 20);
		}
		return lblNewLabel;
	}

	private JTextField getTfDeclareSpec() {
		if (tfDeclareSpec == null) {
			tfDeclareSpec = new JTextField();
			tfDeclareSpec.setBounds(84, 35, 395, 21);
			tfDeclareSpec.setColumns(10);
		}
		return tfDeclareSpec;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
