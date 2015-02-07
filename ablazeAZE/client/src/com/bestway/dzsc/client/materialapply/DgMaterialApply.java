/*
 * Created on 2004-6-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.materialapply;

import java.awt.BorderLayout;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.ScmCoi;
import com.bestway.common.materialbase.entity.ShareCode;
import com.bestway.common.materialbase.entity.SysArea;
import com.bestway.dzsc.materialapply.action.MaterialApplyAction;
import com.bestway.dzsc.materialapply.entity.MaterialApply;
import com.bestway.dzsc.materialapply.entity.MaterialChange;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgMaterialApply extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JTextField tfptNo = null;

	private JTextField tfcomplex = null;

	private JButton jButton1 = null;

	private JTextField tfptName = null;

	private JTextField tfptSpec = null;

	private JTextField tfptDeSpec = null;

	private JFormattedTextField tfptPrice = null;

	private JFormattedTextField tfptNetWeight = null;

	private JFormattedTextField tfptOutWeight = null;

	private JTextField tfptVersion = null;

	private JTextField tfptEnglishName = null;

	private JTextField tfptEnglishSpec = null;

	private JComboBox cfsysArea = null;

	private JComboBox cftaxation = null;

	private JComboBox cfproBonded = null;

	private JButton jButton5 = null;

	private JButton jButton6 = null;

	private MaterialApplyAction materialApplyAction = null;

	private MaterialManageAction materialManageAction = null;

	private CustomBaseAction customBaseAction = null;

	private MaterialApply materialApply = null; // 企业

	private MaterialChange materialChange = null;

	private Complex complex = null; // 海关商品编码

	private ScmCoi scmCoi = null; // 物料类别

	// private boolean isAdd = true;

	private JTableListModel tableModel = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private JTextField jTextField = null;

	private javax.swing.JLabel jLabel14 = new JLabel();

	private JPanel jPanel1 = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel24 = null;

	private JComboBox jComboBox = null;

	private JComboBox jComboBox1 = null;

	private JComboBox jComboBox2 = null;

	private JComboBox jComboBox3 = null;

	// private boolean isEdit = true;

	private JLabel jLabel25 = null;

	private JComboBox cbbPtUnit = null;

	private JLabel jLabel26 = null;

	private JFormattedTextField jFormattedTextField = null;

	private JLabel jLabel27 = null;

	private JComboBox jComboBox5 = null;

	private JLabel jLabel28 = null;

	private JFormattedTextField jFormattedTextField1 = null;

	private JLabel jLabel29 = null;

	private JComboBox jComboBox6 = null;

	private JLabel jLabel30 = null;

	private JLabel jLabel31 = null;

	private JLabel jLabel32 = null;

	private JLabel jLabel33 = null;

	private JLabel jLabel34 = null;

	private JCheckBox jCheckBox = null;

	private JLabel jLabel35 = null;

	private JComboBox jComboBox7 = null;

	private JLabel jLabel36 = null;

	private JFormattedTextField jFormattedTextField2 = null;

	private JLabel jLabel37 = null;

	private JFormattedTextField jFormattedTextField3 = null;

	private JLabel jLabel38 = null;

	private JFormattedTextField jFormattedTextField4 = null;

	private int dataState = DataState.BROWSE;

	// private boolean isChanged = false;

	// public boolean isChanged() {
	// return isChanged;
	// }
	//
	// public void setChanged(boolean isChanged) {
	// this.isChanged = isChanged;
	// }

	/**
	 * This is the default constructor
	 */
	public DgMaterialApply() {
		super();
		initialize();
		materialApplyAction = (MaterialApplyAction) CommonVars
				.getApplicationContext().getBean("materialApplyAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(704, 486);
		this.setTitle("物料备案");
		this.setContentPane(getJContentPane());
	}

	/**
	 * 初始化显示
	 */
	public void setVisible(boolean b) {
		if (b) {
			if (dataState != DataState.ADD) {// isAdd == false
				if (tableModel.getCurrentRow() != null) {
					if (tableModel.getCurrentRow() instanceof MaterialApply) {
						materialApply = (MaterialApply) tableModel
								.getCurrentRow();
						complex = materialApply.getMateriel().getComplex();
						scmCoi = materialApply.getMateriel().getScmCoi();
					} else if (tableModel.getCurrentRow() instanceof MaterialChange) {
						materialChange = (MaterialChange) tableModel
								.getCurrentRow();
						complex = materialChange.getComplex();
						scmCoi = materialChange.getScmCoi();
					}
				}
				initUI();
				fillWindow();
				setState();
			} else {
				initUI();
			}
			getEqualPtNo();
		}
		super.setVisible(b);
	}

	private void initUI() {
		// 类别
		List list = materialManageAction.findScmCois(new Request(CommonVars
				.getCurrUser()));
		this.jComboBox.setModel(new DefaultComboBoxModel(list.toArray()));
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox, "code", "name");
		if (scmCoi != null) // 类别
		{
			jComboBox.setSelectedItem(scmCoi);
		} else {
			jComboBox.setSelectedItem(null);
		}
		// 工厂单位
		List listunit = materialManageAction.findCalUnit(new Request(CommonVars
				.getCurrUser()));
		this.jComboBox1.setModel(new DefaultComboBoxModel(listunit.toArray()));
		this.jComboBox1.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox1, "code", "name");
		jComboBox1.setSelectedIndex(-1);
		// 重量单位
		this.jComboBox2.setModel(new DefaultComboBoxModel(listunit.toArray()));
		this.jComboBox2.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox2, "code", "name");
		jComboBox2.setSelectedIndex(-1);
		// 报关单位
		this.cbbPtUnit.setModel(new DefaultComboBoxModel(CustomBaseList
				.getInstance().getUnits().toArray()));
		this.cbbPtUnit.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbPtUnit, "code", "name");
		cbbPtUnit.setSelectedIndex(-1);
		// 供应商
		List listscm = materialManageAction.findScmManufacturer(new Request(
				CommonVars.getCurrUser()));
		this.jComboBox3.setModel(new DefaultComboBoxModel(listscm.toArray()));
		this.jComboBox3.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 50, 200));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox3, "code", "name");
		this.jComboBox3.setUI(new CustomBaseComboBoxUI(250));
		jComboBox3.setSelectedIndex(-1);
		// 原产国
		List sysarea = materialManageAction.findSysAreas(new Request(CommonVars
				.getCurrUser()));
		this.cfsysArea.setModel(new DefaultComboBoxModel(sysarea.toArray()));
		this.cfsysArea.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cfsysArea, "code", "name");
		cfsysArea.setSelectedIndex(-1);
		// 税制代码
		List taxaTion = materialManageAction.findTaxaTion(new Request(
				CommonVars.getCurrUser()));
		this.cftaxation.setModel(new DefaultComboBoxModel(taxaTion.toArray()));
		this.cftaxation.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cftaxation, "code", "name");
		cftaxation.setSelectedIndex(-1);
		// 初始化包装种类
		List listwarp = customBaseAction.findWrap();
		this.jComboBox7.setModel(new DefaultComboBoxModel(listwarp.toArray()));
		this.jComboBox7.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox7, "code", "name");
		jComboBox7.setSelectedIndex(-1);

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private void fillWindow() {
		if (materialApply != null) {// isAdd
			// ==
			// false
			tfptNo.setText(materialApply.getMateriel().getPtNo()); // 料号
			// tfptName.setText(materiel.getFactoryName()); // 工厂商品名称
			// tfptSpec.setText(materiel.getFactorySpec()); // 工厂型号规格
			tfptName.setText(materialApply.getMateriel().getFactoryName()); // 报关商品名称
			tfptSpec.setText(materialApply.getMateriel().getFactorySpec()); // 报关商品规格
			tfptDeSpec.setText(materialApply.getMateriel().getPtDeSpec()); // 详细规格
			tfptPrice.setText(doubleToStr(materialApply.getMateriel()
					.getPtPrice())); // 单价
			jFormattedTextField4.setText(doubleToStr(materialApply
					.getMateriel().getAmount()));// 库存数量
			tfptNetWeight.setText(doubleToStr(materialApply.getMateriel()
					.getPtNetWeight())); // 净重
			tfptOutWeight.setText(doubleToStr(materialApply.getMateriel()
					.getPtOutWeight())); // 毛重
			jFormattedTextField2.setText(doubleToStr(materialApply
					.getMateriel().getCknetWeight())); // 仓库净重
			jFormattedTextField3.setText(doubleToStr(materialApply
					.getMateriel().getCkoutWeight())); // 仓库毛重
			jFormattedTextField1.setText(doubleToStr(materialApply
					.getMateriel().getScrapWeight()));// 废料重量
			tfptVersion.setText(materialApply.getMateriel().getPtVersion()); // 版本号
			tfptEnglishName.setText(materialApply.getMateriel()
					.getPtEnglishName());// 英文品名
			tfptEnglishSpec.setText(materialApply.getMateriel()
					.getPtEnglishSpec());// 英文规格
			DgMaterialApply.this.jTextField.setText(materialApply.getMateriel()
					.getEqualPtno());
			this.cftaxation.setSelectedItem(materialApply.getMateriel()
					.getTaxation());
			this.jFormattedTextField.setText(doubleToStr(materialApply
					.getMateriel().getUnitConvert()));
			if (materialApply.getMateriel().getKeyHardware() != null
					&& materialApply.getMateriel().getKeyHardware().equals(
							new Boolean(true))) { // 关键零件
				this.jCheckBox.setSelected(true);
			} else {
				this.jCheckBox.setSelected(false);
			}
			if (this.complex != null) // 商品编码
			{
				this.tfcomplex.setText(complex.getCode());
			} else {
				this.tfcomplex.setText("");
			}
			if (this.materialApply.getMateriel().getCalUnit() != null) // 单位
			{
				this.jComboBox1.setSelectedItem(materialApply.getMateriel()
						.getCalUnit());
			} else {
				this.jComboBox1.setSelectedItem(null);
			}
			if (this.materialApply.getMateriel().getScrapCode() != null) { // 废料代码
				this.jComboBox5.setSelectedItem(materialApply.getMateriel()
						.getScrapCode());
			} else {
				this.jComboBox5.setSelectedItem(null);
			}
			if (this.materialApply.getMateriel().getMaterialSource() != null) { // 材料来源
				this.jComboBox6.setSelectedItem(materialApply.getMateriel()
						.getMaterialSource());
			} else {
				this.jComboBox6.setSelectedItem(null);
			}
			if (this.materialApply.getMateriel().getCalWeightUnit() != null) // 重量单位
			{
				this.jComboBox2.setSelectedItem(materialApply.getMateriel()
						.getCalWeightUnit());
			} else {
				this.jComboBox2.setSelectedItem(null);
			}
			if (this.materialApply.getMateriel().getPtUnit() != null) // 报关单位
			{
				this.cbbPtUnit.setSelectedItem(materialApply.getMateriel()
						.getPtUnit());
			} else {
				this.cbbPtUnit.setSelectedItem(null);
			}
			if (this.materialApply.getMateriel().getSysArea() != null) // 原产国
			{
				this.cfsysArea.setSelectedItem(materialApply.getMateriel()
						.getSysArea());
			} else {
				this.cfsysArea.setSelectedItem(null);
			}
			if (this.materialApply.getMateriel().getTaxation() != null) // 税制代码
			{
				this.cftaxation.setSelectedItem(materialApply.getMateriel()
						.getTaxation());
			} else {
				this.cftaxation.setSelectedItem(null);
			}
			if (this.materialApply.getMateriel().getWrap() != null) // 包装种类
			{
				this.jComboBox7.setSelectedItem(materialApply.getMateriel()
						.getWrap());
			} else {
				this.jComboBox7.setSelectedItem(null);
			}

			if (this.materialApply.getMateriel().getScmCoc() != null) // 供应商
			{
				this.jComboBox3.setSelectedItem(materialApply.getMateriel()
						.getScmCoc());
			} else {
				this.jComboBox3.setSelectedItem(null);
			}
			if (materialApply.getMateriel().getProBonded() == "0")
				this.cfproBonded.setSelectedItem("保税");
			else if (materialApply.getMateriel().getProBonded() == "1")
				this.cfproBonded.setSelectedItem("纳税");
		} else if (this.materialChange != null) {
			tfptNo.setText(materialChange.getPtNo()); // 料号
			// tfptName.setText(materiel.getFactoryName()); // 工厂商品名称
			// tfptSpec.setText(materiel.getFactorySpec()); // 工厂型号规格
			tfptName.setText(materialChange.getFactoryName()); // 报关商品名称
			tfptSpec.setText(materialChange.getFactorySpec()); // 报关商品规格
			tfptDeSpec.setText(materialChange.getPtDeSpec()); // 详细规格
			tfptPrice.setText(doubleToStr(materialChange.getPtPrice())); // 单价
			jFormattedTextField4
					.setText(doubleToStr(materialChange.getAmount()));// 库存数量
			tfptNetWeight.setText(doubleToStr(materialChange.getPtNetWeight()));
			// 净重
			tfptOutWeight.setText(doubleToStr(materialChange.getPtOutWeight()));
			// 毛重
			jFormattedTextField2.setText(doubleToStr(materialChange
					.getCknetWeight())); // 仓库净重
			jFormattedTextField3.setText(doubleToStr(materialChange
					.getCkoutWeight())); // 仓库毛重
			jFormattedTextField1.setText(doubleToStr(materialChange
					.getScrapWeight()));// 废料重量
			tfptVersion.setText(materialChange.getPtVersion()); // 版本号
			tfptEnglishName.setText(materialChange.getPtEnglishName());// 英文品名
			tfptEnglishSpec.setText(materialChange.getPtEnglishSpec());// 英文规格
			DgMaterialApply.this.jTextField.setText(materialChange
					.getEqualPtno());
			this.cftaxation.setSelectedItem(materialChange.getTaxation());
			this.jFormattedTextField.setText(doubleToStr(materialChange
					.getUnitConvert()));
			if (materialChange.getKeyHardware() != null
					&& materialChange.getKeyHardware()
							.equals(new Boolean(true))) { // 关键零件
				this.jCheckBox.setSelected(true);
			} else {
				this.jCheckBox.setSelected(false);
			}
			if (this.complex != null) // 商品编码
			{
				this.tfcomplex.setText(complex.getCode());
			} else {
				this.tfcomplex.setText("");
			}
			if (this.materialChange.getCalUnit() != null) // 单位
			{
				this.jComboBox1.setSelectedItem(materialChange.getCalUnit());
			} else {
				this.jComboBox1.setSelectedItem(null);
			}
			if (this.materialChange.getScrapCode() != null) { // 废料代码
				this.jComboBox5.setSelectedItem(materialChange.getScrapCode());
			} else {
				this.jComboBox5.setSelectedItem(null);
			}
			if (this.materialChange.getMaterialSource() != null) { // 材料来源
				this.jComboBox6.setSelectedItem(materialChange
						.getMaterialSource());
			} else {
				this.jComboBox6.setSelectedItem(null);
			}
			if (this.materialChange.getCalWeightUnit() != null) // 重量单位
			{
				this.jComboBox2.setSelectedItem(materialChange
						.getCalWeightUnit());
			} else {
				this.jComboBox2.setSelectedItem(null);
			}
			if (this.materialChange.getPtUnit() != null) // 报关单位
			{
				this.cbbPtUnit.setSelectedItem(materialChange.getPtUnit());
			} else {
				this.cbbPtUnit.setSelectedItem(null);
			}
			if (this.materialChange.getSysArea() != null) // 原产国
			{
				this.cfsysArea.setSelectedItem(materialChange.getSysArea());
			} else {
				this.cfsysArea.setSelectedItem(null);
			}
			if (this.materialChange.getTaxation() != null) // 税制代码
			{
				this.cftaxation.setSelectedItem(materialChange.getTaxation());
			} else {
				this.cftaxation.setSelectedItem(null);
			}
			if (this.materialChange.getWrap() != null) // 包装种类
			{
				this.jComboBox7.setSelectedItem(materialChange.getWrap());
			} else {
				this.jComboBox7.setSelectedItem(null);
			}

			if (this.materialChange.getScmCoc() != null) // 供应商
			{
				this.jComboBox3.setSelectedItem(materialChange.getScmCoc());
			} else {
				this.jComboBox3.setSelectedItem(null);
			}
			if (materialChange.getProBonded() == "0")
				this.cfproBonded.setSelectedItem("保税");
			else if (materialChange.getProBonded() == "1")
				this.cfproBonded.setSelectedItem("纳税");
		}
	}

	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	private Double strToDouble(String value) { // 转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				// return new Double("0");
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return (Double) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			// return new Double("0");
			return null;
		}
	}

	private String doubleToStr(Double value) { // 转换doubleToStr 取数据
		try {
			if (value == null || value.doubleValue() == 0) {
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return getNumberFormatter().valueToString(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0,
					0, 0));
			jPanel.setBackground(java.awt.Color.white);

			// jPanel.add(jLabel14, java.awt.BorderLayout.NORTH);
			jPanel.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
			// jLabel14.setBounds(27, 232, 50, 22);
			// jLabel14.setText("对应料号");
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes tfptNo
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfptNo() {
		if (tfptNo == null) {
			tfptNo = new JTextField();
			tfptNo.setBounds(293, 15, 143, 22);
		}
		return tfptNo;
	}

	/**
	 * 
	 * This method initializes tfcomplex
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfcomplex() {
		if (tfcomplex == null) {
			tfcomplex = new JTextField();
			tfcomplex.setEditable(false);
			tfcomplex.setBounds(532, 15, 117, 22);
		}
		return tfcomplex;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("...");
			jButton1.setBounds(649, 15, 24, 21);
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					Complex complex = (Complex) CommonQuery.getInstance()
							.getComplex(scmCoi.getCoiProperty());
					if (complex != null) {
						DgMaterialApply.this.complex = complex;
						getTfcomplex().setText(complex.getCode());
						DgMaterialApply.this.getTfptName().setText(
								complex.getName());

					}

				}
			});

		}
		return jButton1;
	}

	/**
	 * 
	 * This method initializes tfptName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfptName() {
		if (tfptName == null) {
			tfptName = new JTextField();
			tfptName.setBounds(71, 45, 152, 22);
		}
		return tfptName;
	}

	/**
	 * 
	 * This method initializes tfptSpec
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfptSpec() {
		if (tfptSpec == null) {
			tfptSpec = new JTextField();
			tfptSpec.setBounds(293, 45, 143, 22);
		}
		return tfptSpec;
	}

	/**
	 * 
	 * This method initializes tfptDeSpec
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfptDeSpec() {
		if (tfptDeSpec == null) {
			tfptDeSpec = new JTextField();
			tfptDeSpec.setBounds(532, 45, 140, 22);
		}
		return tfptDeSpec;
	}

	/**
	 * 
	 * This method initializes tfptPrice
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getTfptPrice() {
		if (tfptPrice == null) {
			tfptPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfptPrice.setFormatterFactory(getDefaultFormatterFactory());
			tfptPrice.setBounds(293, 73, 143, 22);
		}
		return tfptPrice;
	}

	/**
	 * 
	 * This method initializes tfptNetWeight
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getTfptNetWeight() {
		if (tfptNetWeight == null) {
			tfptNetWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfptNetWeight.setFormatterFactory(getDefaultFormatterFactory());
			tfptNetWeight.setBounds(71, 108, 152, 22);
		}
		return tfptNetWeight;
	}

	/**
	 * 
	 * This method initializes tfptOutWeight
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getTfptOutWeight() {
		if (tfptOutWeight == null) {
			tfptOutWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfptOutWeight.setFormatterFactory(getDefaultFormatterFactory());
			tfptOutWeight.setBounds(293, 105, 143, 22);
		}
		return tfptOutWeight;
	}

	/**
	 * 
	 * This method initializes tfptVersion
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfptVersion() {
		if (tfptVersion == null) {
			tfptVersion = new JTextField();
			tfptVersion.setBounds(532, 137, 140, 22);
		}
		return tfptVersion;
	}

	/**
	 * 
	 * This method initializes tfptEnglishName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfptEnglishName() {
		if (tfptEnglishName == null) {
			tfptEnglishName = new JTextField();
			tfptEnglishName.setBounds(71, 137, 152, 22);
		}
		return tfptEnglishName;
	}

	/**
	 * 
	 * This method initializes tfptEnglishSpec
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfptEnglishSpec() {
		if (tfptEnglishSpec == null) {
			tfptEnglishSpec = new JTextField();
			tfptEnglishSpec.setBounds(293, 137, 143, 22);
		}
		return tfptEnglishSpec;
	}

	/**
	 * 
	 * This method initializes cfsysArea
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getCfsysArea() {
		if (cfsysArea == null) {
			cfsysArea = new JComboBox();
			cfsysArea.setBounds(71, 167, 152, 22);
		}
		return cfsysArea;
	}

	/**
	 * 
	 * This method initializes cftaxation
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getCftaxation() {
		if (cftaxation == null) {
			cftaxation = new JComboBox();
			cftaxation.setBounds(293, 167, 143, 22);
		}
		return cftaxation;
	}

	/**
	 * 
	 * This method initializes cfproBonded
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getCfproBonded() {
		if (cfproBonded == null) {
			cfproBonded = new JComboBox(new String[] { "保税", "纳税" });
			cfproBonded.setBounds(532, 167, 140, 22);
		}
		return cfproBonded;
	}

	/**
	 * 
	 * This method initializes jButton5
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private boolean checkNull() { // 检查是否为空
		if (this.jComboBox.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "类别不能为空！", "提示！", 0);
			return true;
		}
		if (this.tfptNo.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "料号不能为空！", "提示！", 0);
			return true;
		}
		if (this.tfptName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "商品名称不能为空！", "提示！", 0);
			return true;
		}
		if (this.jComboBox1.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "单位不能为空！", "提示！", 0);
			return true;
		}
		/*
		 * if (this.jComboBox2.getSelectedItem() == null) {
		 * JOptionPane.showMessageDialog(this, "重量单位不能为空！", "提示！", 0); return
		 * true; } if (this.tfptNetWeight.getText().trim().equals("")) {
		 * JOptionPane.showMessageDialog(this, "净重不能为空！", "提示！", 0); return
		 * true; }
		 */
		return false;
	}

	public void fillData(Materiel materiel) { // 保存
		materiel.setScmCoi((ScmCoi) jComboBox.getSelectedItem()); // 类别
		materiel.setPtNo(tfptNo.getText()); // 料号
		materiel.setComplex(DgMaterialApply.this.complex); // 商品编码
		materiel.setCompany(CommonVars.getCurrUser().getCompany()); // 公司id
		materiel.setFactoryName(this.tfptName.getText()); // 报关商品名称
		materiel.setFactorySpec(this.tfptSpec.getText()); // 报关规格型号
		// materiel.setFactoryName(tfptName.getText());// 工厂商品名称
		// materiel.setFactorySpec(tfptSpec.getText());// 工厂商品规格
		materiel.setPtDeSpec(tfptDeSpec.getText()); // 详细型号规格
		materiel.setCalUnit((CalUnit) jComboBox1.getSelectedItem()); // 单位
		materiel.setPtPrice(strToDouble(tfptPrice.getText())); // 单价
		materiel.setPtNetWeight(strToDouble(tfptNetWeight.getText()));// 净重
		materiel.setScrapWeight(strToDouble(jFormattedTextField1.getText()));// 废料重量
		materiel.setCalWeightUnit((CalUnit) jComboBox2.getSelectedItem()); // 重量单位
		materiel.setPtUnit((Unit) cbbPtUnit.getSelectedItem()); // 报关单位
		materiel.setPtOutWeight(strToDouble(tfptOutWeight.getText()));// 毛重
		materiel.setCknetWeight(strToDouble(jFormattedTextField2.getText()));// 仓库净重
		materiel.setCkoutWeight(strToDouble(jFormattedTextField3.getText()));// 仓库毛重
		materiel
				.setUnitConvert(strToDouble(this.jFormattedTextField.getText()));// 单位系数
		materiel.setPtVersion(tfptVersion.getText());// 版本号
		materiel.setPtEnglishName(tfptEnglishName.getText()); // 英文品名
		materiel.setPtEnglishSpec(tfptEnglishSpec.getText()); // 英文规格
		materiel.setScmCoc((ScmCoc) jComboBox3.getSelectedItem()); // 供应商
		materiel.setSysArea((SysArea) cfsysArea.getSelectedItem());// 原产国
		materiel.setTaxation((ShareCode) cftaxation.getSelectedItem());// 税制代码
		if (jComboBox5.getSelectedItem() != null) { // 废料代码
			materiel.setScrapCode(jComboBox5.getSelectedItem().toString());
		} else {
			materiel.setScrapCode("");
		}
		// 包装种类
		materiel.setWrap((Wrap) jComboBox7.getSelectedItem());
		if (jComboBox6.getSelectedItem() != null) { // 材料来源
			materiel.setMaterialSource(jComboBox6.getSelectedItem().toString());
		} else {
			materiel.setMaterialSource("");
		}
		if (this.jCheckBox.isSelected()) { // 关键零件
			materiel.setKeyHardware(new Boolean(true));
		} else {
			materiel.setKeyHardware(new Boolean(false));
		}
		materiel.setEqualPtno(jTextField.getText());
		if (cfproBonded.getSelectedItem() == "保税") // 保纳税
			materiel.setProBonded("0");
		else if (cfproBonded.getSelectedItem() == "纳税")
			materiel.setProBonded("1");
		materiel.setAmount(strToDouble(jFormattedTextField4.getText()));
	}

	public void fillData(MaterialChange materialChange) { // 保存
		materialChange.setScmCoi((ScmCoi) jComboBox.getSelectedItem()); // 类别
		materialChange.setPtNo(tfptNo.getText()); // 料号
		materialChange.setComplex(DgMaterialApply.this.complex); // 商品编码
		materialChange.setCompany(CommonVars.getCurrUser().getCompany()); // 公司id
		materialChange.setFactoryName(this.tfptName.getText()); // 报关商品名称
		materialChange.setFactorySpec(this.tfptSpec.getText()); // 报关规格型号
		// materiel.setFactoryName(tfptName.getText());// 工厂商品名称
		// materiel.setFactorySpec(tfptSpec.getText());// 工厂商品规格
		materialChange.setPtDeSpec(tfptDeSpec.getText()); // 详细型号规格
		materialChange.setCalUnit((CalUnit) jComboBox1.getSelectedItem()); // 单位
		materialChange.setPtPrice(strToDouble(tfptPrice.getText())); // 单价
		materialChange.setPtNetWeight(strToDouble(tfptNetWeight.getText()));// 净重
		materialChange.setScrapWeight(strToDouble(jFormattedTextField1
				.getText()));// 废料重量
		materialChange.setCalWeightUnit((CalUnit) jComboBox2.getSelectedItem()); // 重量单位
		materialChange.setPtUnit((Unit) cbbPtUnit.getSelectedItem()); // 报关单位
		materialChange.setPtOutWeight(strToDouble(tfptOutWeight.getText()));// 毛重
		materialChange.setCknetWeight(strToDouble(jFormattedTextField2
				.getText()));// 仓库净重
		materialChange.setCkoutWeight(strToDouble(jFormattedTextField3
				.getText()));// 仓库毛重
		materialChange.setUnitConvert(strToDouble(this.jFormattedTextField
				.getText()));// 单位系数
		materialChange.setPtVersion(tfptVersion.getText());// 版本号
		materialChange.setPtEnglishName(tfptEnglishName.getText()); // 英文品名
		materialChange.setPtEnglishSpec(tfptEnglishSpec.getText()); // 英文规格
		materialChange.setScmCoc((ScmCoc) jComboBox3.getSelectedItem()); // 供应商
		materialChange.setSysArea((SysArea) cfsysArea.getSelectedItem());// 原产国
		materialChange.setTaxation((ShareCode) cftaxation.getSelectedItem());// 税制代码
		if (jComboBox5.getSelectedItem() != null) { // 废料代码
			materialChange
					.setScrapCode(jComboBox5.getSelectedItem().toString());
		} else {
			materialChange.setScrapCode("");
		}
		// 包装种类
		materialChange.setWrap((Wrap) jComboBox7.getSelectedItem());
		if (jComboBox6.getSelectedItem() != null) { // 材料来源
			materialChange.setMaterialSource(jComboBox6.getSelectedItem()
					.toString());
		} else {
			materialChange.setMaterialSource("");
		}
		if (this.jCheckBox.isSelected()) { // 关键零件
			materialChange.setKeyHardware(new Boolean(true));
		} else {
			materialChange.setKeyHardware(new Boolean(false));
		}
		materialChange.setEqualPtno(jTextField.getText());
		if (cfproBonded.getSelectedItem() == "保税") // 保纳税
			materialChange.setProBonded("0");
		else if (cfproBonded.getSelectedItem() == "纳税")
			materialChange.setProBonded("1");
		materialChange.setAmount(strToDouble(jFormattedTextField4.getText()));
		if (!ModifyMarkState.ADDED.equals(materialChange.getModifyMark())) {
			materialChange.setModifyMark(ModifyMarkState.MODIFIED);
		}
	}

	private void clearData() {
		tfptNo.setText("");
		tfcomplex.setText("");
		tfptName.setText("");
		tfptSpec.setText("");
		// this.jTextField1.setText("");
		// this.jTextField2.setText("");
		tfptDeSpec.setText("");
		this.jComboBox1.setSelectedIndex(-1);
		tfptPrice.setText("");
		jFormattedTextField4.setText("");
		tfptNetWeight.setText("");
		this.jComboBox2.setSelectedIndex(-1);
		this.cbbPtUnit.setSelectedIndex(-1);
		tfptOutWeight.setText("");
		this.jFormattedTextField.setText("");
		tfptVersion.setText("");
		tfptEnglishName.setText("");
		tfptEnglishSpec.setText("");
		this.jComboBox3.setSelectedIndex(-1);
		cfsysArea.setSelectedItem("");
		cftaxation.setSelectedItem("");
		cfproBonded.setSelectedItem("");
		/*
		 * this.complex = null; this.customUnit = null; this.customUnit1 = null;
		 * this.scmCoc = null; this.scmCoi = null;
		 */
	}

	// private boolean checkMultiple() {
	// EnterpriseMaterial ptNo = materialApplyAction
	// .findEnterpriseMaterialByPtNo(new Request(CommonVars
	// .getCurrUser()), this.tfptNo.getText().trim());
	// if (ptNo != null) {
	// if (dataState != DataState.ADD) {// !isAdd
	// if (!ptNo.getId().equals(materiel.getId())) {
	// JOptionPane.showMessageDialog(this, "料号不能重复,请重新输入!", "提示!",
	// 1);
	// return true;
	// }
	// } else {
	// JOptionPane.showMessageDialog(this, "料号不能重复,请重新输入!", "提示!", 1);
	// return true;
	// }
	// }
	// return false;
	// }

	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("确定");
			jButton5.setBounds(38, 363, 74, 24);
			jButton5.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (checkNull()) {
						return;
					}
					// if (checkMultiple()) {
					// return;
					// }
					// if (dataState==DataState.ADD) {//isAdd == true
					// EnterpriseMaterial materiel = new EnterpriseMaterial();
					// fillData(materiel);
					// materiel = (EnterpriseMaterial) materialApplyAction
					// .saveEnterpriseMaterial(new Request(CommonVars
					// .getCurrUser()), materiel);
					// tableModel.addRow(materiel);
					// clearData();
					// } else
					if (dataState == DataState.EDIT) {
						fillData(materialApply.getMateriel());
						materialManageAction.saveMateriel(new Request(
								CommonVars.getCurrUser()), materialApply
								.getMateriel());
						tableModel.updateRow(materialApply);
						DgMaterialApply.this.dispose();

					} else if (dataState == DataState.CHANGE) {
						fillData(materialChange);
						materialChange = (MaterialChange) materialApplyAction
								.saveMaterialChange(new Request(CommonVars
										.getCurrUser()), materialChange);
						tableModel.updateRow(materialChange);
						DgMaterialApply.this.dispose();
					}
				}
			});

		}
		return jButton5;
	}

	/**
	 * 
	 * This method initializes jButton6
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setText("取消");
			jButton6.setBounds(137, 363, 75, 24);
			jButton6.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgMaterialApply.this.dispose();

				}
			});

		}
		return jButton6;
	}

	/**
	 * 
	 * This method initializes defaultFormatterFactory
	 * 
	 * 
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 * 
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * 
	 * This method initializes numberFormatter
	 * 
	 * 
	 * 
	 * @return javax.swing.text.NumberFormatter
	 * 
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
			DecimalFormat decimalFormat1 = new DecimalFormat(); // @jve:decl-index=0:
			decimalFormat1.setMaximumFractionDigits(6);
			numberFormatter.setFormat(decimalFormat1);
		}
		return numberFormatter;
	}

	// /**
	// * @return Returns the isAdd.
	// */
	// public boolean isAdd() {
	// return isAdd;
	// }
	//
	// /**
	// * @param isAdd
	// * The isAdd to set.
	// */
	// public void setAdd(boolean isAdd) {
	// this.isAdd = isAdd;
	// }

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
	 * 
	 * This method initializes jTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(293, 227, 143, 22);
		}
		return jTextField;
	}

	private void getEqualPtNo() {
		if (jComboBox.getSelectedItem() != null) {
			DgMaterialApply.this.jTextField.setVisible(((ScmCoi) jComboBox
					.getSelectedItem()).getName().equals("边角料"));
			DgMaterialApply.this.jLabel14.setVisible(((ScmCoi) jComboBox
					.getSelectedItem()).getName().equals("边角料"));
		} else {
			DgMaterialApply.this.jTextField.setVisible(false);
			DgMaterialApply.this.jLabel14.setVisible(false);
		}
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			// jFormattedTextField1 = new jFormattedTextField1
			jLabel38 = new JLabel();
			jLabel37 = new JLabel();
			jLabel36 = new JLabel();
			jLabel35 = new JLabel();
			jLabel34 = new JLabel();
			jLabel33 = new JLabel();
			jLabel32 = new JLabel();
			jLabel31 = new JLabel();
			jLabel30 = new JLabel();
			jLabel29 = new JLabel();
			jLabel28 = new JLabel();
			jLabel27 = new JLabel();
			jLabel26 = new JLabel();
			jLabel25 = new JLabel();
			jLabel24 = new JLabel();
			javax.swing.JLabel jLabel18 = new JLabel();

			javax.swing.JLabel jLabel17 = new JLabel();

			javax.swing.JLabel jLabel16 = new JLabel();

			javax.swing.JLabel jLabel15 = new JLabel();

			javax.swing.JLabel jLabel13 = new JLabel();

			javax.swing.JLabel jLabel12 = new JLabel();

			javax.swing.JLabel jLabel11 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();

			javax.swing.JLabel jLabel9 = new JLabel();

			javax.swing.JLabel jLabel8 = new JLabel();

			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel6 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(jLabel, null);
			jPanel1.add(getTfptName(), null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(jLabel6, null);
			jPanel1.add(jLabel9, null);
			jPanel1.add(jLabel12, null);
			jPanel1.add(getTfptEnglishName(), null);
			jPanel1.add(getCfsysArea(), null);
			jPanel1.add(jLabel16, null);
			jPanel1.add(getJTextField(), null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(getTfcomplex(), null);
			jPanel1.add(getJButton1(), null);
			jPanel1.add(getTfptDeSpec(), null);
			jPanel1.add(jLabel5, null);
			jPanel1.add(jLabel8, null);
			jPanel1.add(getTfptNetWeight(), null);
			jPanel1.add(getTfptVersion(), null);
			jPanel1.add(jLabel11, null);
			jPanel1.add(jLabel15, null);
			jPanel1.add(getCfproBonded(), null);
			jPanel1.add(jLabel18, null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getTfptNo(), null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(getTfptSpec(), null);
			jPanel1.add(jLabel7, null);
			jPanel1.add(getTfptPrice(), null);
			jPanel1.add(jLabel10, null);
			jPanel1.add(getTfptOutWeight(), null);
			jPanel1.add(jLabel13, null);
			jPanel1.add(getTfptEnglishSpec(), null);
			jPanel1.add(jLabel17, null);
			jPanel1.add(getCftaxation(), null);
			jPanel1.add(getJButton5(), null);
			jPanel1.add(getJButton6(), null);
			jPanel1.setBorder(javax.swing.BorderFactory
					.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jLabel.setText("类别");
			jLabel.setBounds(16, 15, 54, 19);
			jLabel.setForeground(new java.awt.Color(51, 51, 255));
			jLabel1.setText("料号");
			jLabel1.setBounds(240, 15, 30, 20);
			jLabel1.setForeground(java.awt.Color.blue);
			jLabel2.setText("商品编码");
			jLabel2.setForeground(java.awt.Color.blue);
			jLabel2.setBounds(456, 15, 56, 21);
			jLabel3.setText("商品名称");
			jLabel3.setBounds(16, 45, 54, 22);
			jLabel3.setForeground(java.awt.Color.blue);
			jLabel4.setText("型号规格");
			jLabel4.setBounds(240, 45, 50, 22);
			jLabel5.setText("详细型号规格");
			jLabel5.setBounds(456, 45, 77, 21);
			jLabel6.setText("单位");
			jLabel6.setBounds(16, 73, 54, 21);
			jLabel6.setForeground(java.awt.Color.blue);
			jLabel7.setText("单价");
			jLabel7.setBounds(240, 73, 42, 20);
			jLabel8.setText("净重");
			jLabel8.setBounds(16, 106, 54, 20);
			jLabel8.setForeground(java.awt.Color.black);
			jLabel10.setText("毛重");
			jLabel10.setBounds(240, 105, 49, 22);
			jLabel11.setText("版本号");
			jLabel11.setBounds(456, 137, 45, 22);
			jLabel12.setText("英文品名");
			jLabel12.setBounds(16, 137, 54, 23);
			jLabel13.setText("英文规格");
			jLabel13.setBounds(240, 137, 54, 23);
			jLabel15.setText("供应商");
			jLabel15.setBounds(456, 73, 41, 22);
			jLabel16.setText("原产国");
			jLabel16.setBounds(16, 165, 54, 22);
			jLabel17.setText("税制代码");
			jLabel17.setBounds(240, 167, 51, 21);
			jLabel18.setText("保纳税");
			jLabel18.setBounds(456, 167, 43, 19);
			jLabel9.setText("重量单位");
			jLabel9.setBounds(456, 105, 55, 22);
			jLabel9.setForeground(java.awt.Color.black);
			jLabel24.setBounds(15, 315, 174, 18);
			jLabel24.setText("* 注：1,标记为蓝色字体为必填项");
			jLabel24.setForeground(new java.awt.Color(153, 51, 0));
			jLabel25.setBounds(240, 195, 54, 22);
			jLabel25.setForeground(java.awt.Color.blue);
			jLabel25.setText("报关单位");
			jLabel26.setBounds(456, 194, 77, 22);
			jLabel26.setText("单位折算系数");
			jLabel27.setBounds(16, 195, 54, 20);
			jLabel27.setText("废料代码");
			jLabel28.setBounds(16, 224, 54, 20);
			jLabel28.setText("废料重量");
			jLabel29.setBounds(16, 256, 54, 20);
			jLabel29.setText("材料来源");
			jLabel30.setBounds(47, 334, 189, 18);
			jLabel30.setText("2,单位折算等于报关数量/工厂数量");
			jLabel30.setForeground(new java.awt.Color(153, 51, 0));
			jLabel31.setBounds(260, 334, 253, 17);
			jLabel31.setText("1--为使用一库存单位材料本身产生的废料重量");
			jLabel31.setForeground(new java.awt.Color(153, 51, 0));
			jLabel32.setBounds(260, 351, 264, 18);
			jLabel32.setText("2--为生产一库存单位成品会产生的废料重量");
			jLabel32.setForeground(new java.awt.Color(153, 51, 0));
			jLabel33.setBounds(260, 369, 319, 17);
			jLabel33.setText("3--为废料重量由成品决定的情况,并废料重量记录在成品中");
			jLabel33.setForeground(new java.awt.Color(153, 51, 0));
			jLabel34.setBounds(243, 316, 124, 19);
			jLabel34.setText("3,废料代码的含义：");
			jLabel34.setForeground(new java.awt.Color(153, 51, 0));
			jLabel35.setBounds(16, 286, 54, 20);
			jLabel35.setText("包装种类");
			jLabel36.setBounds(240, 255, 52, 23);
			jLabel36.setText("仓库净重");
			jLabel37.setBounds(455, 255, 60, 22);
			jLabel37.setText("仓库毛重");
			jLabel38.setBounds(456, 289, 59, 18);
			jLabel38.setText("库存数量");
			jPanel1.add(jLabel14, java.awt.BorderLayout.NORTH);
			// jPanel1.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
			jPanel1.add(jLabel24, null);
			jPanel1.add(getJComboBox(), null);
			jPanel1.add(getJComboBox1(), null);
			jPanel1.add(getJComboBox2(), null);
			jPanel1.add(getJComboBox3(), null);
			jPanel1.add(jLabel25, null);
			jPanel1.add(getCbbPtUnit(), null);
			jPanel1.add(jLabel26, null);
			jPanel1.add(getJFormattedTextField(), null);
			jPanel1.add(jLabel27, null);
			jPanel1.add(getJComboBox5(), null);
			jPanel1.add(jLabel28, null);
			jPanel1.add(getJFormattedTextField1(), null);
			jPanel1.add(jLabel29, null);
			jPanel1.add(getJComboBox6(), null);
			jPanel1.add(jLabel30, null);
			jPanel1.add(jLabel31, null);
			jPanel1.add(jLabel32, null);
			jPanel1.add(jLabel33, null);
			jPanel1.add(jLabel34, null);
			jPanel1.add(getJCheckBox(), null);
			jPanel1.add(jLabel35, null);
			jPanel1.add(getJComboBox7(), null);
			jPanel1.add(jLabel36, null);
			jPanel1.add(getJFormattedTextField2(), null);
			jPanel1.add(jLabel37, null);
			jPanel1.add(getJFormattedTextField3(), null);
			jPanel1.add(jLabel38, null);
			jPanel1.add(getJFormattedTextField4(), null);
			jLabel14.setBounds(189, 257, 54, 22);
			jLabel14.setText("对应料号");
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jSplitPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(40);
			jSplitPane.setDividerSize(0);
			jSplitPane.setTopComponent(getJPanel2());
			jSplitPane.setBottomComponent(getJPanel1());
		}
		return jSplitPane;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			javax.swing.JLabel jLabel21 = new JLabel();

			javax.swing.JLabel jLabel20 = new JLabel();

			javax.swing.JLabel jLabel19 = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jLabel19.setText("");
			jLabel19.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel20.setText("物料主档");
			jLabel20
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 20));
			jLabel20.setForeground(new java.awt.Color(255, 153, 0));
			jLabel21.setText("");
			jLabel21
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jPanel2.setBackground(java.awt.Color.white);
			jPanel2
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			jPanel2.add(jLabel19, java.awt.BorderLayout.EAST);
			jPanel2.add(jLabel20, java.awt.BorderLayout.CENTER);
			jPanel2.add(jLabel21, java.awt.BorderLayout.WEST);
		}
		return jPanel2;
	}

	/**
	 * @return Returns the scmCoi.
	 */
	public ScmCoi getScmCoi() {
		return scmCoi;
	}

	/**
	 * @param scmCoi
	 *            The scmCoi to set.
	 */
	public void setScmCoi(ScmCoi scmCoi) {
		this.scmCoi = scmCoi;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(71, 14, 152, 22);
		}
		return jComboBox;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(71, 73, 152, 22);
		}
		return jComboBox1;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setBounds(532, 105, 140, 22);
		}
		return jComboBox2;
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox3() {
		if (jComboBox3 == null) {
			jComboBox3 = new JComboBox();
			jComboBox3.setBounds(532, 72, 140, 22);
		}
		return jComboBox3;
	}

	private void setState() {
		jComboBox.setEnabled(dataState != DataState.BROWSE);
		tfptNo.setEditable(dataState != DataState.BROWSE);
		jButton1.setEnabled(dataState != DataState.BROWSE);
		tfptName.setEditable(dataState != DataState.BROWSE);// this.isEdit()
		tfptSpec.setEditable(dataState != DataState.BROWSE);
		tfptDeSpec.setEditable(dataState != DataState.BROWSE);
		jComboBox1.setEnabled(dataState != DataState.BROWSE);
		tfptPrice.setEditable(dataState != DataState.BROWSE);
		tfptNetWeight.setEditable(dataState != DataState.BROWSE);
		tfptVersion.setEditable(dataState != DataState.BROWSE);
		tfptOutWeight.setEditable(dataState != DataState.BROWSE);
		jComboBox2.setEnabled(dataState != DataState.BROWSE);
		tfptEnglishName.setEditable(dataState != DataState.BROWSE);
		tfptEnglishSpec.setEditable(dataState != DataState.BROWSE);
		jComboBox3.setEnabled(dataState != DataState.BROWSE);
		cfproBonded.setEnabled(dataState != DataState.BROWSE);
		cftaxation.setEnabled(dataState != DataState.BROWSE);
		cfsysArea.setEnabled(dataState != DataState.BROWSE);
		jTextField.setEditable(dataState != DataState.BROWSE);
		jButton5.setEnabled(dataState != DataState.BROWSE);
		jButton6.setEnabled(dataState != DataState.BROWSE);
		// jTextField1.setEditable(dataState!=DataState.BROWSE);
		// jTextField2.setEditable(dataState!=DataState.BROWSE);
	}

	/**
	 * This method initializes jComboBox4
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbPtUnit() {
		if (cbbPtUnit == null) {
			cbbPtUnit = new JComboBox();
			cbbPtUnit.setBounds(293, 195, 143, 22);
		}
		return cbbPtUnit;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJFormattedTextField() {
		if (jFormattedTextField == null) {
			jFormattedTextField = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField.setBounds(532, 194, 140, 22);
		}
		return jFormattedTextField;
	}

	/**
	 * This method initializes jComboBox5
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox5() {
		if (jComboBox5 == null) {
			jComboBox5 = new JComboBox(new String[] { "", "1", "2", "3" });
			jComboBox5.setBounds(71, 195, 152, 22);
		}
		return jComboBox5;
	}

	/**
	 * This method initializes jFormattedTextField1
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJFormattedTextField1() {
		if (jFormattedTextField1 == null) {
			jFormattedTextField1 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField1.setBounds(71, 224, 152, 22);
		}
		return jFormattedTextField1;
	}

	/**
	 * This method initializes jComboBox6
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox6() {
		if (jComboBox6 == null) {
			jComboBox6 = new JComboBox(new String[] { "自制", "国内购买", "国外采购",
					"委外加工", "客户供料" });
			jComboBox6.setBounds(71, 256, 152, 22);
		}
		return jComboBox6;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(455, 228, 99, 21);
			jCheckBox.setText("关键零件");
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jComboBox7
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox7() {
		if (jComboBox7 == null) {
			jComboBox7 = new JComboBox();
			jComboBox7.setBounds(71, 286, 152, 22);
		}
		return jComboBox7;
	}

	/**
	 * This method initializes jFormattedTextField2
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJFormattedTextField2() {
		if (jFormattedTextField2 == null) {
			jFormattedTextField2 = new JFormattedTextField();
			jFormattedTextField2.setBounds(293, 255, 143, 22);
		}
		return jFormattedTextField2;
	}

	/**
	 * This method initializes jFormattedTextField3
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJFormattedTextField3() {
		if (jFormattedTextField3 == null) {
			jFormattedTextField3 = new JFormattedTextField();
			jFormattedTextField3.setBounds(532, 255, 140, 22);
		}
		return jFormattedTextField3;
	}

	/**
	 * This method initializes jFormattedTextField4
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJFormattedTextField4() {
		if (jFormattedTextField4 == null) {
			jFormattedTextField4 = new JFormattedTextField();
			jFormattedTextField4.setBounds(532, 286, 140, 22);
		}
		return jFormattedTextField4;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}
}
