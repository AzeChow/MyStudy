/*
 * Created on 2004-8-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.bestway.bcs.contract.action.ContractAction;
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
import com.bestway.customs.common.action.BCSExpCustomsAuthorityAction;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.action.DZSCExpCustomsAuthorityAction;
import com.bestway.customs.common.action.ExpCustomsAuthorityAction;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.bcs.contract.entity.ContractExg;

import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Font;
import java.awt.Color;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings( { "static-access", "unchecked" })
public class DgBaseExportCustomsDeclarationCommInfo extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3010491873475117761L;

	protected JPanel jContentPane = null;

	protected JPanel lbWorkUsd = null;

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

	protected JFormattedTextField tfNetWeight = null;

	protected JFormattedTextField tfGrossWeight = null;

	protected JFormattedTextField tfPieces = null;

	protected JTableListModel tableModel = null;

	protected JLabel lbUnit = null;

	protected JLabel lbLegalUnit = null;

	protected JLabel lbSecondLegalUnit = null;

	protected BaseCustomsDeclarationCommInfo customsDeclarationCommInfo = null;

	protected BaseEncAction baseEncAction = null; // @jve:decl-index=0:

	protected DefaultFormatterFactory defaultFormatterFactory = null;

	protected NumberFormatter numberFormatter = null;

	protected int projectType = ProjectType.BCUS;

	protected boolean isEffective = false;

	protected JButton btnPrevious = null;

	protected JButton btnNext = null;

	protected JButton btnEdit = null;

	// protected int totalCount = 0; // 总行
	//
	// protected int rowCount = 0; // 当前行

	protected int dataState = DataState.EDIT;

	private JLabel jLabel7 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel14 = null;

	protected JLabel jLabel15 = null;

	protected JTextField jTextField = null;

	private ExpCustomsAuthorityAction expCustomsAuthorityAction = null;

	private BCSExpCustomsAuthorityAction bCSExpCustomsAuthorityAction = null;
	
	private DZSCExpCustomsAuthorityAction dZSCExpCustomsAuthorityAction = null;

	private MaterialManageAction materialManageAction = null;
	//hwy 2012-10-15
	private ContractAction contractAction = null;

	protected JLabel jLabel16 = null;

	protected JComboBox jComboBox = null;

	private JLabel jLabel17 = null;

	private JLabel jLabel18 = null;

	private DgBaseExportCustomsDeclaration dgBase = null;
	
	protected CustomBaseAction customBaseAction = null;

	CustomsDeclarationSet other1;

	public DgBaseExportCustomsDeclaration getDgBase() {
		return dgBase;
	}

	public void setDgBase(DgBaseExportCustomsDeclaration dgBase) {
		this.dgBase = dgBase;
	}

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
	 * 报关单是否生效
	 */
	public void setEffective(boolean isEffective) {
		this.isEffective = isEffective;
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

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgBaseExportCustomsDeclarationCommInfo() {
		super();
		expCustomsAuthorityAction = (ExpCustomsAuthorityAction) CommonVars
				.getApplicationContext().getBean("expCustomsAuthorityAction");
		bCSExpCustomsAuthorityAction = (BCSExpCustomsAuthorityAction) CommonVars
		.getApplicationContext().getBean("bCSExpCustomsAuthorityAction");
		dZSCExpCustomsAuthorityAction = (DZSCExpCustomsAuthorityAction) CommonVars
		.getApplicationContext().getBean("dZSCExpCustomsAuthorityAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		//hwy 2012-10-20
		contractAction = (ContractAction) CommonVars
		.getApplicationContext().getBean("contractAction");

		initialize();
		// 初始化包装种类
		List listwarp = customBaseAction.findWrap();
		this.cbbWrapType.setModel(new DefaultComboBoxModel(listwarp.toArray()));
		this.cbbWrapType.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbWrapType, "code", "name");
		cbbWrapType.setSelectedItem(null);
		//
		// 设置最初的焦点
		//
		// InitialFocusSetter.setInitialFocus(this, tfCommAmount);
		// 这里是控制焦点的顺序，以方便键盘的输！
		List<Object> components = new ArrayList<Object>();
		components.add(this.tfCommAmount);
		components.add(null);
		components.add(this.cbbLevyMode);
		components.add(this.tfNetWeight);
		components.add(null);
		components.add(this.tfWorkUsd);
		components.add(this.btnOk);
		components.add(this.btnNext);
		this.setComponentFocusList(components);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	protected void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("出口报关单商品信息编辑");
		this.setContentPane(getJContentPane());
		this.setSize(500, 455);
		tfCommAmount.setFocusable(true);
		tfCommAmount.requestFocus();

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	protected JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel18 = new JLabel();
			jLabel18.setBounds(new java.awt.Rectangle(262, 12, 83, 20));
			jLabel18
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel18.setForeground(java.awt.Color.red);
			jLabel18.setText("");
			jLabel17 = new JLabel();
			jLabel17.setBounds(new java.awt.Rectangle(216, 12, 45, 20));
			jLabel17.setForeground(java.awt.Color.red);
			jLabel17
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel17.setText("序号：");
			jLabel14 = new JLabel();
			jLabel7 = new JLabel();
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jLabel7.setBounds(38, 12, 75, 19);
			jLabel7.setText("当前项号：");
			jLabel7.setForeground(java.awt.Color.red);
			jLabel7
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel14.setBounds(120, 12, 72, 20);
			jLabel14.setText("");
			jLabel14.setForeground(java.awt.Color.red);
			jLabel14
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getBtnPrevious(), null);
			jContentPane.add(getBtnNext(), null);
			jContentPane.add(getBtnEdit(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel14, null);
			jContentPane.add(jLabel17, null);
			jContentPane.add(jLabel18, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	protected JPanel getJPanel() {
		if (lbWorkUsd == null) {
			jLabel19 = new JLabel();
			jLabel19.setBounds(new Rectangle(5, 298, 72, 20));
			jLabel19.setText("工缴费");
			lbBoxNo = new JLabel();
			lbBoxNo.setBounds(new Rectangle(243, 271, 72, 18));
			lbBoxNo.setText("箱号");
			jLabel131 = new JLabel();
			jLabel131.setBounds(new Rectangle(5, 271, 72, 20));
			jLabel131.setText("包装种类");
			lbDetailCommSpec = new JLabel();
			lbDetailCommSpec.setBounds(new Rectangle(243, 298, 72, 18));// 柏能插件
			lbDetailCommSpec.setText("备注");
//			lbDetailCommSpec.setVisible(false);
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(243, 194, 72, 18));
			jLabel16.setText("事业部");
			jLabel15 = new JLabel();
			lbWorkUsd = new JPanel();
			lbUnit = new JLabel();
			lbLegalUnit = new JLabel();
			lbSecondLegalUnit = new JLabel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel5 = new JLabel();
			jLabel6 = new JLabel();

			javax.swing.JLabel jLabel8 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel10 = new JLabel();
			javax.swing.JLabel jLabel11 = new JLabel();
			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel13 = new JLabel();
			lbWorkUsd.setLayout(null);
			lbWorkUsd.setBounds(3, 38, 489, 321);
			lbWorkUsd.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel.setBounds(5, 8, 72, 20);
			jLabel.setText("名称");
			jLabel.setForeground(java.awt.Color.blue);
			jLabel1.setBounds(243, 8, 72, 20);
			jLabel1.setText("手册规格");
			jLabel1.setForeground(java.awt.Color.blue);
			jLabel2.setBounds(5, 57, 72, 20);
			jLabel2.setText("数量");
			jLabel3.setBounds(5, 83, 72, 20);
			jLabel3.setText("单价");
			jLabel4.setBounds(243, 83, 72, 18);
			jLabel4.setText("金额");
			jLabel5.setBounds(5, 115, 72, 20);
			jLabel5.setText("第一法定数量");
			jLabel6.setBounds(5, 140, 72, 20);
			jLabel6.setText("第二法定数量");

			jLabel8.setBounds(5, 167, 72, 20);
			jLabel8.setText("最终目的国");
			jLabel8.setForeground(java.awt.Color.blue);
			jLabel9.setBounds(243, 167, 72, 18);
			jLabel9.setText("用途");
			jLabel9.setForeground(java.awt.Color.blue);
			jLabel10.setBounds(5, 194, 72, 20);
			jLabel10.setText("征减免税方式");
			jLabel10.setForeground(java.awt.Color.blue);
			jLabel11.setBounds(5, 221, 72, 20);
			jLabel11.setText("净重");
			jLabel12.setBounds(243, 221, 72, 18);
			jLabel12.setText("毛重");
			jLabel13.setBounds(5, 246, 72, 20);
			jLabel13.setText("件数");
			lbUnit.setBounds(243, 57, 72, 18);
			lbUnit.setText("千克");
			lbSecondLegalUnit.setBounds(243, 140, 72, 18);
			lbSecondLegalUnit.setText("JLabel");
			lbLegalUnit.setBounds(243, 115, 72, 18);
			lbLegalUnit.setText("JLabel");
			jLabel15.setBounds(243, 246, 72, 18);
			jLabel15.setText("版本");
			lbWorkUsd.add(lbSecondLegalUnit, null);
			lbWorkUsd.add(lbLegalUnit, null);
			lbWorkUsd.add(lbUnit, null);
			lbWorkUsd.add(jLabel, null);
			lbWorkUsd.add(getTfCommName(), null);
			lbWorkUsd.add(jLabel1, null);
			lbWorkUsd.add(getTfCommSpec(), null);
			lbWorkUsd.add(jLabel2, null);
			lbWorkUsd.add(getTfCommAmount(), null);
			lbWorkUsd.add(getTfCommUnitPrice(), null);
			lbWorkUsd.add(getTfCommTotalPrice(), null);
			lbWorkUsd.add(jLabel3, null);
			lbWorkUsd.add(jLabel4, null);
			lbWorkUsd.add(jLabel5, null);
			lbWorkUsd.add(jLabel6, null);
			lbWorkUsd.add(jLabel8, null);
			lbWorkUsd.add(getCbbProduceCountry(), null);
			lbWorkUsd.add(jLabel9, null);
			lbWorkUsd.add(getCbbUses(), null);
			lbWorkUsd.add(jLabel10, null);
			lbWorkUsd.add(getCbbLevyMode(), null);
			lbWorkUsd.add(jLabel11, null);
			lbWorkUsd.add(jLabel12, null);
			lbWorkUsd.add(jLabel13, null);
			lbWorkUsd.add(getTfLegalAmount(), null);
			lbWorkUsd.add(getTfSecondLegalAmount(), null);
			lbWorkUsd.add(getTfNetWeight(), null);
			lbWorkUsd.add(getTfGrossWeight(), null);
			lbWorkUsd.add(getTfPieces(), null);
			lbWorkUsd.add(jLabel15, null);
			lbWorkUsd.add(getJTextField(), null);
			lbWorkUsd.add(jLabel16, null);
			lbWorkUsd.add(getJComboBox(), null);
			lbWorkUsd.add(lbDetailCommSpec, null);
			lbWorkUsd.add(getTfDetailCommSpec(), null);
			lbWorkUsd.add(jLabel131, null);
			lbWorkUsd.add(getCbbWrapType(), null);
			lbWorkUsd.add(lbBoxNo, null);
			lbWorkUsd.add(getTfBoxNo(), null);
			lbWorkUsd.add(jLabel19, null);
			lbWorkUsd.add(getJTextField1(), null);
			lbWorkUsd.add(getLblNewLabel());
			lbWorkUsd.add(getTfDeclareSpec());
		}
		return lbWorkUsd;
	}

	/**
	 * This method initializes tfCommName
	 * 
	 * @return javax.swing.JTextField
	 */
	protected JTextField getTfCommName() {
		if (tfCommName == null) {
			tfCommName = new JTextField();
			tfCommName.setBounds(78, 8, 160, 20);
			tfCommName.setEditable(false);
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
			tfCommSpec.setBounds(319, 8, 160, 20);
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
			tfCommAmount.setBounds(78, 57, 160, 20);
			tfCommAmount.addPropertyChangeListener("value",
					new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent evt) {
							tfCommTotalPrice.setValue(getTotalPrice());
							getFirstAndSencAmount();
							//hwy2012-10-20
							getJTextField1();
							tfWorkUsd.setValue(getWorkUsd());
						}

					});
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
			cbbProduceCountry.setBounds(78, 167, 160, 20);
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
			cbbUses.setBounds(319, 167, 160, 20);
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
			cbbLevyMode.setBounds(78, 194, 160, 20);
		}
		return cbbLevyMode;
	}

	private boolean rollSave() {
		if (dataState == DataState.EDIT) {
			if (checkNull()) {
				return true;
			}
			if (projectType == ProjectType.BCUS) {
				if (customsDeclarationCommInfo != null) {
					if (customsDeclarationCommInfo.getBaseCustomsDeclaration()
							.getImpExpType() != ImpExpType.BACK_MATERIEL_EXPORT
							&&customsDeclarationCommInfo.getBaseCustomsDeclaration()
							.getImpExpType() != ImpExpType.REMAIN_FORWARD_EXPORT) {
						if (checkRerictCommodity()) {
							return true;
						}
						if (checkCommodityForbid()) {
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
					DgBaseExportCustomsDeclarationCommInfo.this,
					customsDeclarationCommInfo)) {
				return true;
			}
			customsDeclarationCommInfo = baseEncAction
					.saveCustomsDeclarationCommInfo(new Request(CommonVars
							.getCurrUser()), customsDeclarationCommInfo);
			tableModel.updateRow(customsDeclarationCommInfo);
		}
		return false;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(216, 392, 59, 24);
			btnOk.setText("保存");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						expCustomsAuthorityAction.saveCommodity(new Request(CommonVars
								.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCExpCustomsAuthorityAction.saveCommodity(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSExpCustomsAuthorityAction.saveCommodity(new Request(
								CommonVars.getCurrUser()));
					}
					if (checkNull()) {
						return;
					}
					if (projectType == ProjectType.BCUS) {
						if (customsDeclarationCommInfo != null) {
							if (customsDeclarationCommInfo
									.getBaseCustomsDeclaration()
									.getImpExpType() != ImpExpType.BACK_MATERIEL_EXPORT
									&& customsDeclarationCommInfo.getBaseCustomsDeclaration()
									.getImpExpType() != ImpExpType.REMAIN_FORWARD_EXPORT) {
								if (checkRerictCommodity()) {
									return;
								}
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
							DgBaseExportCustomsDeclarationCommInfo.this,
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

				}
			});
			// btnOk.addKeyListener(new KeyAdapter() {
			// public void keyPressed(KeyEvent e) {
			//
			// if (e.getKeyCode() == 10) {// Enter
			// if (e.getSource().getClass().getName().toLowerCase()
			// .indexOf("button") >= 0) {
			// JButton btn = (JButton) e.getSource();
			// btn.dispatchEvent(new KeyEvent((Container) e
			// .getSource(), KeyEvent.KEY_PRESSED, 0, 0,
			// KeyEvent.VK_SPACE, e.getKeyChar()));
			// btn.dispatchEvent(new KeyEvent((Container) e
			// .getSource(), KeyEvent.KEY_RELEASED, 0, 0,
			// KeyEvent.VK_SPACE, e.getKeyChar()));
			// if (getBtnNext().isEnabled()) {
			// getBtnNext().requestFocus();
			// } else {
			// getBtnEdit().requestFocus();
			// }
			// }
			// }
			// }
			// });
		}
		return btnOk;
	}

	// protected boolean checkNull(){
	// if (cbbProduceCountry.getSelectedItem()==null){
	// JOptionPane.showMessageDialog(this, "最终目的国不能为空！", "提示！", 0);
	// return true;
	// }
	// if (cbbUses.getSelectedItem()==null){
	// JOptionPane.showMessageDialog(this, "用途不能为空！", "提示！", 0);
	// return true;
	// }
	// if (cbbLevyMode.getSelectedItem()==null){
	// JOptionPane.showMessageDialog(this, "征减免税方式不能为空！", "提示！", 0);
	// return true;
	// }
	// return false;
	// }
	/**
	 * 检查是不是已经禁用
	 * 
	 * @return
	 */
	public boolean checkCommodityForbid() {
		Integer version = null;
		try {
			version = Integer.valueOf(jTextField.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "版本号应该为数字型", "提示", 0);
			return true;
		}
		List list = materialManageAction.findVersionNo(new Request(CommonVars
				.getCurrUser()), Integer.valueOf(jLabel18.getText()), version);
		if (list != null && list.size() > 0 && list.get(0) != null) {

		} else {
			if (version != null) {
				JOptionPane.showMessageDialog(this, "该版本号在电子帐册中未备案", "提示", 0);
				return true;
			}
		}
		CommdityForbid commodity = materialManageAction.findCommdityForbid(
				new Request(CommonVars.getCurrUser()),
				MaterielType.FINISHED_PRODUCT, jLabel18.getText().toString(),
				jTextField.getText().toString());

		if (commodity != null) {
			if (customsDeclarationCommInfo.getBaseCustomsDeclaration()
					.getImpExpType() == ImpExpType.REWORK_EXPORT || customsDeclarationCommInfo.getBaseCustomsDeclaration()
					.getImpExpType() == ImpExpType.TRANSFER_FACTORY_EXPORT) {
				if (JOptionPane.showConfirmDialog(this, "该版本已经被禁用,是否继续", "确认",
						0) != 0) {
					return true;
				}
			} else {
				JOptionPane.showMessageDialog(this, "该版本已经被禁用", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;

			}
		}
		return false;
	}

	// 检查是不是限制类商品并且是否超出了限制类商品中定义的数量
	public boolean checkRerictCommodity() {
		double aamount = 0;
		double Amount = 0;
		Boolean isMaterial = EncCommon.isMaterial(customsDeclarationCommInfo
				.getBaseCustomsDeclaration().getImpExpType());
		// 得到限制类商品的数量
		RestirictCommodity commodity = materialManageAction
				.findRerictCommodity(new Request(CommonVars.getCurrUser()),
						isMaterial, jLabel18.getText().toString());
		if (commodity != null) {
			// 得到报关单中的数量
			Double DeclarationCommInfo = materialManageAction
					.findCustomsDeclarationCommInfo(new Request(CommonVars
							.getCurrUser()), isMaterial, jLabel18.getText()
							.toString(), customsDeclarationCommInfo);
			Amount = DeclarationCommInfo
					+ Double.valueOf(tfCommAmount.getText().toString());

			if (commodity.getAmount() != null
					&& !commodity.getAmount().equals("")) {
				aamount = Double.parseDouble(commodity.getAmount().toString());
				if (Amount > aamount) {
					JOptionPane.showMessageDialog(this, "备案号："
							+ jLabel18.getText() + "已出数量[" + Amount
							+ "] 超出了限制类商品中备的数量[" + aamount + "]!\n", "提示", 0);
					tfCommAmount.requestFocus();
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkNull() {
//		if (this.tfDeclareSpec.getText().trim().equals("")) {
//			JOptionPane.showMessageDialog(null, "规范申报规格不能为空!", "提示!!",
//					JOptionPane.INFORMATION_MESSAGE);
//			return true;
//		}
		
		if (cbbProduceCountry.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "最终目的国不能为空！", "提示！", 0);
			cbbProduceCountry.requestFocus();
			return true;
		}
		// if (cbbUses.getSelectedItem() == null) {
		// JOptionPane.showMessageDialog(this, "用途不能为空！", "提示！", 0);
		// cbbUses.requestFocus();
		// return true;
		// }
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
				if ("".equals(jTextField.getText())) {
					JOptionPane.showMessageDialog(this, "版本号不可为空！", "提示！", 0);
					jTextField.requestFocus();
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
			JOptionPane.showMessageDialog(this, "净重不可以大于毛重！", "提示！", 0);
			tfNetWeight.requestFocus();
			return true;
		}
		return false;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(285, 392, 59, 24);
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
			tfCommUnitPrice.setBounds(78, 83, 160, 20);
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
			tfCommTotalPrice.setBounds(319, 83, 160, 20);
			tfCommTotalPrice.addKeyListener(new java.awt.event.KeyAdapter() {
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
			tfLegalAmount.setBounds(78, 115, 160, 20);
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
			tfSecondLegalAmount.setBounds(78, 140, 160, 20);
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
			tfNetWeight.setBounds(78, 221, 160, 20);
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
			tfGrossWeight.setBounds(319, 221, 160, 20);
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
			tfPieces.setBounds(78, 246, 160, 20);
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

	// public void setVisibleAfter() {
	// //
	// // 清除 KeyAdapterControl
	// //
	// clearButtonKyeAdapterControl(this.getJContentPane());
	// }

	protected void setState() {

		// jButton.setEnabled(rowCount > 0);
		// btnNext.setEnabled(rowCount < totalCount - 1);
		this.tfCommAmount.setEditable(dataState != DataState.BROWSE
				&& !this.isEffective);
		jTextField.setEditable(dataState != DataState.BROWSE
				&& !this.isEffective);
		
		tfDeclareSpec.setEditable(dataState != DataState.BROWSE
				&& !this.isEffective);
		
		tfDetailCommSpec.setEditable(dataState != DataState.BROWSE
				&& !this.isEffective);

		tfBoxNo.setEditable(dataState != DataState.BROWSE && !this.isEffective);
		
		

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
		
		
		tfCommAmount.setEditable(dataState !=DataState.BROWSE
				&& !this.isEffective);
		
		this.tfNetWeight.setEditable(dataState != DataState.BROWSE
				&& !this.isEffective);
		this.tfGrossWeight.setEditable(dataState != DataState.BROWSE
				&& !this.isEffective);
		this.tfPieces.setEditable(dataState != DataState.BROWSE
				&& !this.isEffective);

		this.cbbProduceCountry.setEnabled(dataState != DataState.BROWSE
				&& !this.isEffective);
		this.cbbUses.setEnabled(dataState != DataState.BROWSE
				&& !this.isEffective);
		this.cbbLevyMode.setEnabled(dataState != DataState.BROWSE
				&& !this.isEffective);
		this.btnOk.setEnabled(dataState != DataState.BROWSE
				&& !this.isEffective);// 保存
		btnEdit.setEnabled(dataState == DataState.BROWSE && !this.isEffective); // 修改
		btnCancel
				.setEnabled(dataState != DataState.BROWSE && !this.isEffective); // 取消
		this.jComboBox.setEnabled(dataState != DataState.BROWSE
				&& !this.isEffective); // 事业部
		this.cbbWrapType.setEnabled(dataState != DataState.BROWSE
				&& !this.isEffective);// 包装种类 2010-05-21 hcl
		//hwy
		this.tfWorkUsd.setEditable(dataState != DataState.BROWSE
				&& !this.isEffective);
		
		// String leagalUnit = "";
		// String unit = "";
		// String SecondLegalUnit = "";
		// if (customsDeclarationCommInfo.getLegalUnit() != null) {
		// leagalUnit = customsDeclarationCommInfo.getLegalUnit().getName();
		// }
		// if (customsDeclarationCommInfo.getUnit() != null) {
		// unit = customsDeclarationCommInfo.getUnit().getName();
		// }
		// if (customsDeclarationCommInfo.getSecondLegalUnit() != null){
		// SecondLegalUnit =
		// customsDeclarationCommInfo.getSecondLegalUnit().getName();
		// }
		tfLegalAmount.setEditable((!lbUnit.getText().equals(
				lbLegalUnit.getText()))
				&& (dataState != DataState.BROWSE && !this.isEffective));
		tfSecondLegalAmount.setEditable((!lbUnit.getText().equals(
				lbSecondLegalUnit.getText()))
				&& (dataState != DataState.BROWSE && !this.isEffective));

		jLabel6
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

	}

	// 计算第一法定数量、第二法定数量:
	// 条件1：当申报单位与第一法定单位、第二法定单位相同时第一法定数量与第二法定数量等于申报数量
	// 条件2：当申报单位不等于千克，第一法定数量为千克，第二法定数量为千克时第一法定数量与第二法定数量等于净重
	// 条件3：当条件1与条件2不满足时，申报单位、第一法定单位，第二法定单位根据UnitCollate(计量单折算)实体得出相应的比例
	// 条件4: 当条件1与条件2、条件3都不满足时，申报单位、第一法定单位，第二法定单位根据帐册备的比例因子来计算
	protected void getFirstAndSencAmount() {
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
							.doubleValue()
							* Double.valueOf(amount));
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
							.doubleValue()
							* Double.valueOf(amount));
		}
	}

	/**
	 * 存放单位折算对照表
	 */
	public Map getUnitRateMap() {
		Map unitRateMap = baseEncAction.findAllUnitRateMap(new Request(
				CommonVars.getCurrUser()));
		return unitRateMap;
	}

	protected void initUIComponents() {
		// 初始化起运国
		this.cbbProduceCountry.setModel(CustomBaseModel.getInstance()
				.getCountryModel());
		this.cbbProduceCountry.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbProduceCountry);

		// 根据参数设置纸质手册报关单中的单价状态
		tfCommUnitPrice.setEditable((projectType == ProjectType.BCS)
				&& CommonVars.getIsCanModifyUnitPrice());

		// 初始化征免性质
		this.cbbLevyMode.setModel(CustomBaseModel.getInstance()
				.getLevymodeModel());
		this.cbbLevyMode
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbLevyMode);
		// 初始化用途
		this.cbbUses.setModel(CustomBaseModel.getInstance().getUseModel());
		this.cbbUses.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbUses);
		if (EncCommon.isMaterial(customsDeclarationCommInfo
				.getBaseCustomsDeclaration().getImpExpType())) {
			jLabel15.setVisible(false);
			jTextField.setVisible(false);
		} else {
			jLabel15.setVisible(true);
			jTextField.setVisible(true);
		}
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
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfPieces, 0);
		
		
		
		// 数量 第一法定数量与第二法定数量小数位保留一致
		if (other != null) {
			CustomFormattedTextFieldUtils.setFormatterFactory(
					this.tfCommAmount, other.getCustomAmountNum());
		} else {
			CustomFormattedTextFieldUtils.setFormatterFactory(
					this.tfCommAmount, 4);
		}

		// 第一法定数量
//		if (other != null) {
//			CustomFormattedTextFieldUtils.setFormatterFactory(
//					this.tfLegalAmount, other.getCustomAmountNum());
//		} else {
			CustomFormattedTextFieldUtils.setFormatterFactory(
					this.tfLegalAmount, 4);
//		}
		// 第二法定数量
//		if (other != null) {
//			CustomFormattedTextFieldUtils.setFormatterFactory(
//					this.tfSecondLegalAmount, other.getCustomAmountNum());
//		} else {
			CustomFormattedTextFieldUtils.setFormatterFactory(
					this.tfSecondLegalAmount, 4);
//		}
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
		tfDetailCommSpec.setText(customsDeclarationCommInfo.getDetailNote());// 表体备注
		jLabel14.setText(String.valueOf(customsDeclarationCommInfo
				.getSerialNumber()));
		jLabel18.setText(String.valueOf(customsDeclarationCommInfo
				.getCommSerialNo()));
		jTextField.setText(customsDeclarationCommInfo.getVersion());
		if (customsDeclarationCommInfo.getComplex() != null) {
			this.tfCommName.setText(customsDeclarationCommInfo.getCommName());
		}
		this.tfCommSpec.setText(customsDeclarationCommInfo.getCommSpec());
		this.tfCommAmount.setValue(customsDeclarationCommInfo.getCommAmount());
		//hwy
//		this.tfWorkUsd.setValue(customsDeclarationCommInfo.getWorkUsd());
		
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
		// 事业部
		if (customsDeclarationCommInfo.getProjectDept() != null) {
			this.jComboBox.setSelectedItem(customsDeclarationCommInfo
					.getProjectDept());
		} else {
			this.jComboBox.setSelectedIndex(-1);
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
		// 包装种类 2010-05-21 hcl
		this.cbbWrapType.setSelectedItem(customsDeclarationCommInfo
				.getWrapType());
		// 箱号
		tfBoxNo.setText(customsDeclarationCommInfo.getBoxNo());

		// 工缴费显示
		tfWorkUsd.setText(customsDeclarationCommInfo.getWorkUsd() == null ? ""
				: customsDeclarationCommInfo.getWorkUsd().toString());	
		
		// 规范申报规格
		tfDeclareSpec.setText(customsDeclarationCommInfo.getDeclareSpec());
	}
	

	protected void fillData() {
		customsDeclarationCommInfo.setCommSpec(this.tfCommSpec.getText());
		customsDeclarationCommInfo.setVersion(jTextField.getText());
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
		// 事业部
		if (this.jComboBox.getSelectedItem() != null) {
			customsDeclarationCommInfo
					.setProjectDept((ProjectDept) this.jComboBox
							.getSelectedItem());
		} else {
			customsDeclarationCommInfo.setProjectDept(null);
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
		// 包装种类 2010-05-25 hcl
		customsDeclarationCommInfo.setWrapType((Wrap) this.cbbWrapType
				.getSelectedItem());

		// 箱号
		customsDeclarationCommInfo.setBoxNo(tfBoxNo.getText());
		// 工缴费  hwy2012-10-20
		
		if (projectType == ProjectType.BCS) {
			customsDeclarationCommInfo
			.setWorkUsd(tfWorkUsd.getText() != null && ! tfWorkUsd.getText().equals("") ? (Double
							.valueOf(this.tfWorkUsd.getValue().toString()))
							: Double.valueOf("0.0"));
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
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	// protected NumberFormatter getNumberFormatter() {
	// if (numberFormatter == null) {
	// numberFormatter = new NumberFormatter();
	// DecimalFormat decimalFormat = new DecimalFormat("0.####"); //
	// @jve:decl-index=0:
	// // decimalFormat.setGroupingSize(0);
	// // decimalFormat.setMaximumFractionDigits(4);
	// numberFormatter.setFormat(decimalFormat);
	// }
	// return numberFormatter;
	// }
	// 得到总价
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

	// 得到单价
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setBounds(78, 392, 59, 24);
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

	private boolean isShowAdd = false;
	private boolean isBcusChaKan = true;

	private JLabel lbDetailCommSpec = null;

	private JTextField tfDetailCommSpec = null;

	private JLabel jLabel131 = null;

	private JComboBox cbbWrapType = null;

	private JLabel lbBoxNo = null;

	private JTextField tfBoxNo = null;

	private JLabel jLabel19 = null;

	private JFormattedTextField tfWorkUsd = null;
	private JLabel lblNewLabel;
	private JTextField tfDeclareSpec;

	public boolean isBcusChaKan() {
		return isBcusChaKan;
	}

	public void setBcusChaKan(boolean isBcusChaKan) {
		this.isBcusChaKan = isBcusChaKan;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setBounds(354, 392, 59, 24);
			btnNext.setText("下条");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (rollSave()) {
						return;
					}

					if (isShowAdd && isBcusChaKan) {// 当点了下一条但是不点查看
						if (tableModel.getList().size() < 20) {
							DgBaseExportCustomsDeclarationCommInfo.this
									.setVisible(false);
							dgBase.btnAdd.doClick();
							DgBaseExportCustomsDeclarationCommInfo.this
									.dispose();
							return;
						} else {
							JOptionPane
									.showMessageDialog(
											DgBaseExportCustomsDeclarationCommInfo.this,
											"商品项数已达20项");
							DgBaseExportCustomsDeclarationCommInfo.this
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
					if (tableModel.getCurrRowCount() + 1 == tableModel
							.getList().size()) {

						isShowAdd = true;
					} else {
						isShowAdd = false;
					}
					showData();
					setState();

				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setBounds(147, 392, 59, 24);
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
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(319, 246, 160, 20);
		}
		return jTextField;
	}

	protected boolean checkAmount() {
		return true;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(319, 194, 160, 20));
		}
		return jComboBox;
	}

	/**
	 * This method initializes tfDetailCommSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDetailCommSpec() {// 柏能插件
		if (tfDetailCommSpec == null) {
			tfDetailCommSpec = new JTextField();
			tfDetailCommSpec.setBounds(new Rectangle(319, 298, 160, 20));
			tfDetailCommSpec.setFont(new Font("Dialog", Font.PLAIN, 22));
//			tfDetailCommSpec.setVisible(false);
		}
		return tfDetailCommSpec;
	}

	/**
	 * This method initializes cbbWrapType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWrapType() {
		if (cbbWrapType == null) {
			cbbWrapType = new JComboBox();
			cbbWrapType.setBounds(new Rectangle(78, 271, 160, 20));
		}
		return cbbWrapType;
	}

	/**
	 * This method initializes tfBoxNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBoxNo() {
		if (tfBoxNo == null) {
			tfBoxNo = new JTextField();
			tfBoxNo.setBounds(new Rectangle(319, 271, 160, 20));
		}
		return tfBoxNo;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (tfWorkUsd == null) {
			tfWorkUsd = new JFormattedTextField();
			tfWorkUsd.setBounds(new Rectangle(78, 298, 160, 20));
		}
		return tfWorkUsd;
	}
	
//	// 得到工缴费
	//hwy 2012-10-20
	protected Double getWorkUsd() {
		double amount = 0;//申报数量
		double unitPrice = 0;//加工费单价
		if (this.tfCommAmount.getText() !=null
				&& !this.tfCommAmount.getText().equals("")) {
			amount = Double.parseDouble(this.tfCommAmount.getText().toString());
		}
		//加工费单价
		List<ContractExg> contractExgs = contractAction.findContractExgBySeqNum(new Request(CommonVars
				.getCurrUser()), customsDeclarationCommInfo.getBaseCustomsDeclaration().getEmsHeadH2k(), 
				customsDeclarationCommInfo.getCommSerialNo().toString());
		for(ContractExg contractExg : contractExgs){
			if (customsDeclarationCommInfo.getCommSerialNo()!= null && contractExg!=null) {
				if(contractExg.getProcessUnitPrice()!=null){
					unitPrice =contractExg.getProcessUnitPrice();
				}else {
					unitPrice = 0;
				}
		}
		}
		BigDecimal workused = new BigDecimal(amount * unitPrice);
		String WorkUsd = workused.setScale(4, BigDecimal.ROUND_HALF_UP).toString();
		return Double.valueOf(WorkUsd);
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("规范申报规格");
			lblNewLabel.setBounds(5, 32, 72, 20);
		}
		return lblNewLabel;
	}
	private JTextField getTfDeclareSpec() {
		if (tfDeclareSpec == null) {
			tfDeclareSpec = new JTextField();
			tfDeclareSpec.setBounds(78, 32, 401, 20);
			tfDeclareSpec.setColumns(10);
		}
		return tfDeclareSpec;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
