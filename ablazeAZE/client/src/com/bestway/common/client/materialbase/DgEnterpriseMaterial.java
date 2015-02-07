/*
 * Created on 2004-6-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import java.awt.BorderLayout;
import java.math.RoundingMode;
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
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.ScmCoi;
import com.bestway.common.materialbase.entity.ShareCode;
import com.bestway.common.materialbase.entity.SysArea;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Rectangle;
import javax.swing.SwingConstants;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates checked by 刘民
 *         date:2008.11.25 物料主档
 */
public class DgEnterpriseMaterial extends JDialogBase {

	private javax.swing.JPanel pnContent = null;

	private JPanel pn = null;

	private JTextField tfPtNo = null;

	private JTextField tfPtCode = null;

	private JButton btnPtCode = null;

	private JTextField tfPtName = null;

	private JTextField tfPtSpec = null;

	private JTextField tfPtDeSpec = null;
	/** 单价 */

	private JFormattedTextField tfPtPrice = null;
	/** 净重 */
	private JFormattedTextField tfPtNetWeight = null;
	/** 毛重 */

	private JFormattedTextField tfPtOutWeight = null;

	private JTextField tfPtVersion = null;

	private JTextField tfPtEnglishName = null;

	private JTextField tfPtEnglishSpec = null;

	private JComboBox cbbSysArea = null;

	private JComboBox cbbTaxation = null;

	private JComboBox cbbProBonded = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;
	/** 物料主档接口 */

	private CustomBaseAction customBaseAction = null;
	/** 料件管理操作接口 */

	private MaterialManageAction materialManageAction = null;
	/** 存放工厂物料主档资料 */

	private EnterpriseMaterial materiel = null;
	/** 海关商品编码 */

	private Complex complex = null;
	/** 物料类别 */

	private ScmCoi scmCoi = null;

	// private boolean isAdd = true;

	private JTableListModel tableModel = null;
	/** 格式化任意对象工厂 */

	private DefaultFormatterFactory defaultFormatterFactory = null;
	/** 数字格式化 */

	private NumberFormatter numberFormatter = null;

	private JTextField tfCorrespondingptNo = null;

	private JPanel pn1 = null;

	private JSplitPane spn = null;

	private JPanel pn2 = null;

	private JLabel lbMarker = null;

	private JComboBox cbbSort = null;

	private JComboBox cbbUnit = null;

	private JComboBox cbbWeightUnit = null;

	private JComboBox cbbClient = null;

	// private boolean isEdit = true;

	private JLabel lbDeclareUnit = null;

	private JComboBox cbbDeclareUnit = null;

	private JLabel lbUnitConvertQuotiety = null;
	/** 单位折算系数 */

	private JFormattedTextField tfUnitConvertQuotiety = null;

	private JLabel lbWasteCode = null;

	private JComboBox cbbWasteCode = null;

	private JLabel lbWasteWeight = null;
	/** 废料重量 */

	private JFormattedTextField tfWasteWeight = null;

	private JLabel lbMaterialSource = null;

	private JComboBox cbbMaterialSource = null;

	private JLabel lbUnitConvert = null;

	private JLabel lbForuse = null;

	private JLabel lbForproduce = null;

	private JLabel lbForWaste = null;

	private JLabel lbWaste = null;

	private JCheckBox cbPivotal = null;

	private JLabel lbPackKind = null;

	private JComboBox cbbPackKind = null;

	private JLabel lbWarehouseSuttle = null;
	/** 仓库净重 */

	private JFormattedTextField tfWarehouseSuttle = null;

	private JLabel lbWarehouseGross_weight = null;
	/** 仓库毛重 */

	private JFormattedTextField tfWarehouseGross_weight = null;

	private JLabel lbStockNumber = null;
	/** 库存数量 */

	private JFormattedTextField tfStockNumber = null;
	/** 数据状态 */

	private int dataState = DataState.BROWSE;

	private JLabel lbClient = null;

	private JCheckBox cbPrecative = null;

	private JLabel lbCorrespondingptNo = null;

	private JLabel lbDeclareName = null;

	private JLabel lbDeclareSpec = null;

	private JTextField tfDeclareName = null;

	private JTextField tfDeclareSpec = null;

	private JLabel lbDeclareMnemonicCode = null;

	private JTextField tfCustomsNo = null;

	/**
	 * This is the default constructor
	 */
	public DgEnterpriseMaterial() {
		super();
		initialize();
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
		this.setSize(711, 508);
		this.setTitle("物料主档");
		this.setContentPane(getPnContent());
	}

	/**
	 * 初始化显示
	 */
	public void setVisible(boolean b) {
		if (b) {
			if (dataState != DataState.ADD) {// isAdd == false
				if (tableModel.getCurrentRow() != null) {
					materiel = (EnterpriseMaterial) tableModel.getCurrentRow();
					complex = materiel.getComplex();
					scmCoi = materiel.getScmCoi();
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

	/***
	 * 初始化UI各个组件
	 */

	private void initUI() {
		// 类别
		List list = materialManageAction.findScmCois(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbSort.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbSort.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbSort,
				"code", "name");
		if (scmCoi != null) // 类别
		{
			cbbSort.setSelectedItem(scmCoi);
		} else {
			cbbSort.setSelectedItem(null);
		}
		// 工厂单位
		List listunit = materialManageAction.findCalUnit(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbUnit.setModel(new DefaultComboBoxModel(listunit.toArray()));
		this.cbbUnit.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbUnit,
				"code", "name");
		cbbUnit.setSelectedIndex(-1);
		// 重量单位
		this.cbbWeightUnit
				.setModel(new DefaultComboBoxModel(listunit.toArray()));
		this.cbbWeightUnit.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbWeightUnit, "code", "name");
		cbbWeightUnit.setSelectedIndex(-1);
		// 报关单位
		this.cbbDeclareUnit.setModel(new DefaultComboBoxModel(CustomBaseList
				.getInstance().getUnits().toArray()));
		this.cbbDeclareUnit.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbDeclareUnit, "code", "name");
		cbbDeclareUnit.setSelectedIndex(-1);
		// 供应商
		List listscm = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbClient.setModel(new DefaultComboBoxModel(listscm.toArray()));
		this.cbbClient.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 50, 200));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbClient, "code", "name");
		this.cbbClient.setUI(new CustomBaseComboBoxUI(250));
		cbbClient.setSelectedIndex(-1);
		// 原产国
		List sysarea = materialManageAction.findSysAreas(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbSysArea.setModel(new DefaultComboBoxModel(sysarea.toArray()));
		this.cbbSysArea.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbSysArea, "code", "name");
		cbbSysArea.setSelectedIndex(-1);
		// 税制代码
		List taxaTion = materialManageAction.findTaxaTion(new Request(
				CommonVars.getCurrUser(), true));
		this.cbbTaxation.setModel(new DefaultComboBoxModel(taxaTion.toArray()));
		this.cbbTaxation.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTaxation, "code", "name");
		cbbTaxation.setSelectedIndex(-1);
		// 初始化包装种类
		List listwarp = customBaseAction.findWrap();
		this.cbbPackKind.setModel(new DefaultComboBoxModel(listwarp.toArray()));
		this.cbbPackKind.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbPackKind, "code", "name");
		cbbPackKind.setSelectedIndex(-1);
		// 是否委外

	}

	/**
	 * This method initializes pnContent
	 * 
	 * @return javax.swing.JPanel
	 */
	private void fillWindow() {
		if (materiel != null && dataState != DataState.ADD) {// isAdd ==
			// false
			tfPtNo.setText(materiel.getPtNo()); // 料号
			// tfPtName.setText(materiel.getFactoryName()); // 工厂商品名称
			// tfPtSpec.setText(materiel.getFactorySpec()); // 工厂型号规格
			this.tfDeclareName.setText(materiel.getPtName()); // 报关商品名称
			this.tfDeclareSpec.setText(materiel.getPtSpec()); // 报关商品规格
			tfPtName.setText(materiel.getFactoryName()); // 工厂商品名称
			tfPtSpec.setText(materiel.getFactorySpec()); // 工厂商品规格
			tfPtDeSpec.setText(materiel.getPtDeSpec()); // 详细规格
			tfPtPrice.setText(doubleToStr(materiel.getPtPrice())); // 单价
			tfStockNumber.setText(doubleToStr(materiel.getAmount()));// 库存数量
			tfPtNetWeight.setText(doubleToStr(materiel.getPtNetWeight())); // 净重
			tfPtOutWeight.setText(doubleToStr(materiel.getPtOutWeight())); // 毛重
			tfCustomsNo.setText(materiel.getCustomsNo());// 报关助记码
			tfWarehouseSuttle.setText(doubleToStr(materiel.getCknetWeight())); // 仓库净重
			tfWarehouseGross_weight.setText(doubleToStr(materiel
					.getCkoutWeight())); // 仓库毛重
			tfWasteWeight.setText(doubleToStr(materiel.getScrapWeight()));// 废料重量
			tfPtVersion.setText(materiel.getPtVersion()); // 版本号
			tfPtEnglishName.setText(materiel.getPtEnglishName());// 英文品名
			tfPtEnglishSpec.setText(materiel.getPtEnglishSpec());// 英文规格
			DgEnterpriseMaterial.this.tfCorrespondingptNo.setText(materiel
					.getEqualPtno());
			this.cbbTaxation.setSelectedItem(materiel.getTaxation());
			this.tfUnitConvertQuotiety.setText(doubleToStr(materiel
					.getUnitConvert()));
			if (materiel.getKeyHardware() != null
					&& materiel.getKeyHardware().equals(new Boolean(true))) { // 关键零件
				this.cbPivotal.setSelected(true);
			} else {
				this.cbPivotal.setSelected(false);
			}
			if (materiel.getIsOutsource() != null
					&& materiel.getIsOutsource().equals(new Boolean(true))) { // 关键零件
				this.cbPrecative.setSelected(true);
			} else {
				this.cbPrecative.setSelected(false);
			}

			if (this.complex != null) // 商品编码
			{
				this.tfPtCode.setText(complex.getCode());
			} else {
				this.tfPtCode.setText("");
			}
			if (this.materiel.getCalUnit() != null) // 单位
			{
				this.cbbUnit.setSelectedItem(materiel.getCalUnit());
			} else {
				this.cbbUnit.setSelectedItem(null);
			}
			if (this.materiel.getScrapCode() != null) { // 废料代码
				this.cbbWasteCode.setSelectedItem(materiel.getScrapCode());
			} else {
				this.cbbWasteCode.setSelectedItem(null);
			}
			if (this.materiel.getMaterialSource() != null) { // 材料来源
				this.cbbMaterialSource.setSelectedItem(materiel
						.getMaterialSource());
			} else {
				this.cbbMaterialSource.setSelectedItem(null);
			}
			if (this.materiel.getCalWeightUnit() != null) // 重量单位
			{
				this.cbbWeightUnit.setSelectedItem(materiel.getCalWeightUnit());
			} else {
				this.cbbWeightUnit.setSelectedItem(null);
			}
			if (this.materiel.getPtUnit() != null) // 报关单位
			{
				this.cbbDeclareUnit.setSelectedItem(materiel.getPtUnit());
			} else {
				this.cbbDeclareUnit.setSelectedItem(null);
			}
			if (this.materiel.getSysArea() != null) // 原产国
			{
				this.cbbSysArea.setSelectedItem(materiel.getSysArea());
			} else {
				this.cbbSysArea.setSelectedItem(null);
			}
			if (this.materiel.getTaxation() != null) // 税制代码
			{
				this.cbbTaxation.setSelectedItem(materiel.getTaxation());
			} else {
				this.cbbTaxation.setSelectedItem(null);
			}
			if (this.materiel.getWrap() != null) // 包装种类
			{
				this.cbbPackKind.setSelectedItem(materiel.getWrap());
			} else {
				this.cbbPackKind.setSelectedItem(null);
			}

			if (this.materiel.getScmCoc() != null) // 供应商
			{
				this.cbbClient.setSelectedItem(materiel.getScmCoc());
			} else {
				this.cbbClient.setSelectedItem(null);
			}
			if (materiel.getProBonded() == null
					|| materiel.getProBonded().equals("0"))
				this.cbbProBonded.setSelectedItem("保税");
			else if (materiel.getProBonded().equals("1"))
				this.cbbProBonded.setSelectedItem("纳税");
		}
	}

	/**
	 * 初始化pnContent
	 * 
	 * @return
	 */

	private javax.swing.JPanel getPnContent() {
		if (pnContent == null) {
			pnContent = new javax.swing.JPanel();
			pnContent.setLayout(new java.awt.BorderLayout());
			pnContent.add(getPn(), java.awt.BorderLayout.CENTER);
		}
		return pnContent;
	}

	/**
	 * 转换strToDouble 填充数据
	 * 
	 * @param value
	 * @return
	 */

	private Double strToDouble(String value) {
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

	/**
	 * 转换doubleToStr 取数据
	 * 
	 * @param value
	 * @return
	 */

	private String doubleToStr(Double value) {
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
	 * This method initializes pn
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPn() {
		if (pn == null) {
			pn = new JPanel();
			pn.setLayout(new BorderLayout());
			pn.setBorder(javax.swing.BorderFactory
					.createEmptyBorder(0, 0, 0, 0));
			pn.setBackground(java.awt.Color.white);

			// pn.add(lbCorrespondingptNo, java.awt.BorderLayout.NORTH);
			pn.add(getSpn(), java.awt.BorderLayout.CENTER);
			// lbCorrespondingptNo.setBounds(27, 232, 50, 22);
			// lbCorrespondingptNo.setText("对应料号");
		}
		return pn;
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
	private JTextField getTfPtNo() {
		if (tfPtNo == null) {
			tfPtNo = new JTextField();
			tfPtNo.setBounds(293, 14, 143, 22);
		}
		return tfPtNo;
	}

	/**
	 * 
	 * This method initializes tfPtCode
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfPtCode() {
		if (tfPtCode == null) {
			tfPtCode = new JTextField();
			tfPtCode.setEditable(false);
			tfPtCode.setBounds(532, 14, 117, 22);
		}
		return tfPtCode;
	}

	/**
	 * 
	 * This method initializes btnPtCode
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnPtCode() {
		if (btnPtCode == null) {
			btnPtCode = new JButton();
			btnPtCode.setText("...");
			btnPtCode.setBounds(649, 14, 24, 22);
			btnPtCode.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					Complex complex = (Complex) CommonQuery.getInstance()
							.getComplex(scmCoi.getCoiProperty());
					if (complex != null) {
						DgEnterpriseMaterial.this.complex = complex;
						getTfPtCode().setText(complex.getCode());
						DgEnterpriseMaterial.this.getTfPtName().setText(
								complex.getName());

					}

				}
			});

		}
		return btnPtCode;
	}

	/**
	 * 
	 * This method initializes tfPtName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfPtName() {
		if (tfPtName == null) {
			tfPtName = new JTextField();
			tfPtName.setBounds(70, 44, 152, 24);
		}
		return tfPtName;
	}

	/**
	 * 
	 * This method initializes tfPtSpec
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfPtSpec() {
		if (tfPtSpec == null) {
			tfPtSpec = new JTextField();
			tfPtSpec.setBounds(293, 44, 143, 24);
		}
		return tfPtSpec;
	}

	/**
	 * 
	 * This method initializes tfPtDeSpec
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfPtDeSpec() {
		if (tfPtDeSpec == null) {
			tfPtDeSpec = new JTextField();
			tfPtDeSpec.setBounds(70, 75, 365, 26);
		}
		return tfPtDeSpec;
	}

	/**
	 * 
	 * This method initializes tfPtPrice
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getTfPtPrice() {
		if (tfPtPrice == null) {
			tfPtPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfPtPrice.setFormatterFactory(getDefaultFormatterFactory());
			tfPtPrice.setBounds(293, 109, 143, 23);
		}
		return tfPtPrice;
	}

	/**
	 * 
	 * This method initializes tfPtNetWeight
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getTfPtNetWeight() {
		if (tfPtNetWeight == null) {
			tfPtNetWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfPtNetWeight.setFormatterFactory(getDefaultFormatterFactory());
			tfPtNetWeight.setBounds(70, 141, 152, 22);
		}
		return tfPtNetWeight;
	}

	/**
	 * 
	 * This method initializes tfPtOutWeight
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getTfPtOutWeight() {
		if (tfPtOutWeight == null) {
			tfPtOutWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfPtOutWeight.setFormatterFactory(getDefaultFormatterFactory());
			tfPtOutWeight.setBounds(293, 141, 143, 22);
		}
		return tfPtOutWeight;
	}

	/**
	 * 
	 * This method initializes tfPtVersion
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfPtVersion() {
		if (tfPtVersion == null) {
			tfPtVersion = new JTextField();
			tfPtVersion.setBounds(532, 172, 140, 23);
		}
		return tfPtVersion;
	}

	/**
	 * 
	 * This method initializes tfPtEnglishName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfPtEnglishName() {
		if (tfPtEnglishName == null) {
			tfPtEnglishName = new JTextField();
			tfPtEnglishName.setBounds(70, 172, 152, 23);
		}
		return tfPtEnglishName;
	}

	/**
	 * 
	 * This method initializes tfPtEnglishSpec
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfPtEnglishSpec() {
		if (tfPtEnglishSpec == null) {
			tfPtEnglishSpec = new JTextField();
			tfPtEnglishSpec.setBounds(293, 172, 143, 23);
		}
		return tfPtEnglishSpec;
	}

	/**
	 * 
	 * This method initializes cbbSysArea
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getCbbSysArea() {
		if (cbbSysArea == null) {
			cbbSysArea = new JComboBox();
			cbbSysArea.setBounds(70, 201, 152, 22);
		}
		return cbbSysArea;
	}

	/**
	 * 
	 * This method initializes cbbTaxation
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getCbbTaxation() {
		if (cbbTaxation == null) {
			cbbTaxation = new JComboBox();
			cbbTaxation.setBounds(293, 201, 143, 22);
		}
		return cbbTaxation;
	}

	/**
	 * 
	 * This method initializes cbbProBonded
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getCbbProBonded() {
		if (cbbProBonded == null) {
			cbbProBonded = new JComboBox(new String[] { "保税", "纳税" });
			cbbProBonded.setBounds(532, 201, 140, 22);
		}
		return cbbProBonded;
	}

	/**
	 * 
	 * This method initializes btnSave
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private boolean checkNull() { // 检查是否为空
		if (this.cbbSort.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "类别不能为空！", "提示！", 0);
			return true;
		}
		if (this.tfPtNo.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "料号不能为空！", "提示！", 0);
			return true;
		}
		if (this.tfPtName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "商品名称不能为空！", "提示！", 0);
			return true;
		}
		if (this.cbbUnit.getSelectedItem() == null) {
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

	/**
	 * 填充数据
	 * 
	 * @param materiel
	 */

	public void fillData(EnterpriseMaterial materiel) { // 保存
		materiel.setScmCoi((ScmCoi) cbbSort.getSelectedItem()); // 类别
		materiel.setPtNo(tfPtNo.getText()); // 料号
		materiel.setComplex(DgEnterpriseMaterial.this.complex); // 商品编码
		materiel.setCompany(CommonVars.getCurrUser().getCompany()); // 公司id
		materiel.setFactoryName(this.tfPtName.getText()); // 报关商品名称
		materiel.setFactorySpec(this.tfPtSpec.getText()); // 报关规格型号
		// materiel.setFactoryName(tfPtName.getText());// 工厂商品名称
		// materiel.setFactorySpec(tfPtSpec.getText());// 工厂商品规格
		materiel.setPtName(tfDeclareName.getText());// 报关商品名称
		materiel.setPtSpec(tfDeclareSpec.getText());// 报关规格型号
		materiel.setPtDeSpec(tfPtDeSpec.getText()); // 详细型号规格
		if (cbbUnit.getSelectedItem() != null) {
			materiel.setCalUnit((CalUnit) cbbUnit.getSelectedItem()); // 单位
		}

		materiel.setPtPrice(strToDouble(tfPtPrice.getText())); // 单价
		materiel.setPtNetWeight(strToDouble(tfPtNetWeight.getText()));// 净重
		materiel.setScrapWeight(strToDouble(tfWasteWeight.getText()));// 废料重量
		materiel.setCustomsNo(tfCustomsNo.getText());// 报关助记码
		if (cbbWeightUnit.getSelectedItem() != null) {
			materiel
					.setCalWeightUnit((CalUnit) cbbWeightUnit.getSelectedItem()); // 重量单位
		}
		if (cbbDeclareUnit.getSelectedItem() != null) {
			materiel.setPtUnit((Unit) cbbDeclareUnit.getSelectedItem()); // 报关单位
		}
		materiel.setPtOutWeight(strToDouble(tfPtOutWeight.getText()));// 毛重
		materiel.setCknetWeight(strToDouble(tfWarehouseSuttle.getText()));// 仓库净重
		materiel.setCkoutWeight(strToDouble(tfWarehouseGross_weight.getText()));// 仓库毛重
		materiel.setUnitConvert(strToDouble(this.tfUnitConvertQuotiety
				.getText()));// 单位系数
		materiel.setPtVersion(tfPtVersion.getText());// 版本号
		materiel.setPtEnglishName(tfPtEnglishName.getText()); // 英文品名
		materiel.setPtEnglishSpec(tfPtEnglishSpec.getText()); // 英文规格
		materiel.setScmCoc((ScmCoc) cbbClient.getSelectedItem()); // 供应商
		materiel.setSysArea((SysArea) cbbSysArea.getSelectedItem());// 原产国
		materiel.setTaxation((ShareCode) cbbTaxation.getSelectedItem());// 税制代码
		if (cbbWasteCode.getSelectedItem() != null) { // 废料代码
			materiel.setScrapCode(cbbWasteCode.getSelectedItem().toString());
		} else {
			materiel.setScrapCode("");
		}
		// 包装种类
		materiel.setWrap((Wrap) cbbPackKind.getSelectedItem());
		if (cbbMaterialSource.getSelectedItem() != null) { // 材料来源
			materiel.setMaterialSource(cbbMaterialSource.getSelectedItem()
					.toString());
		} else {
			materiel.setMaterialSource("");
		}
		if (this.cbPivotal.isSelected()) { // 关键零件
			materiel.setKeyHardware(new Boolean(true));
		} else {
			materiel.setKeyHardware(new Boolean(false));
		}
		if (this.cbPrecative.isSelected()) { // 是否委外
			materiel.setIsOutsource(new Boolean(true));
		} else {
			materiel.setIsOutsource(new Boolean(false));
		}

		materiel.setEqualPtno(tfCorrespondingptNo.getText());
		if (cbbProBonded.getSelectedItem() == "保税") // 保纳税
			materiel.setProBonded("0");
		else if (cbbProBonded.getSelectedItem() == "纳税")
			materiel.setProBonded("1");
		materiel.setAmount(strToDouble(tfStockNumber.getText()));
	}

	/**
	 * 清空数据
	 */

	private void clearData() {
		tfPtNo.setText("");
		tfPtCode.setText("");
		tfPtName.setText("");
		tfPtSpec.setText("");
		tfDeclareName.setText("");
		tfDeclareSpec.setText("");
		// this.jTextField1.setText("");
		// this.jTextField2.setText("");
		tfPtDeSpec.setText("");
		this.cbbUnit.setSelectedIndex(-1);
		tfPtPrice.setText("");
		tfStockNumber.setText("");
		tfPtNetWeight.setText("");
		this.cbbWeightUnit.setSelectedIndex(-1);
		this.cbbDeclareUnit.setSelectedIndex(-1);
		tfPtOutWeight.setText("");
		this.tfUnitConvertQuotiety.setText("");
		tfPtVersion.setText("");
		tfPtEnglishName.setText("");
		tfPtEnglishSpec.setText("");
		this.cbbClient.setSelectedIndex(-1);
		// cbbSysArea.setSelectedItem("");
		// cbbTaxation.setSelectedItem("");
		// cbbProBonded.setSelectedItem("");
		cbbSysArea.setSelectedIndex(-1);
		cbbTaxation.setSelectedIndex(-1);
		cbbProBonded.setSelectedIndex(-1);
		tfCustomsNo.setText("");
		/*
		 * this.complex = null; this.customUnit = null; this.customUnit1 = null;
		 * this.scmCoc = null; this.scmCoi = null;
		 */
	}

	/**
	 * 检查料号是否重复
	 * 
	 * @return
	 */

	private boolean checkMultiple() {
		EnterpriseMaterial ptNo = materialManageAction
				.findEnterpriseMaterialByPtNo(new Request(CommonVars
						.getCurrUser(), true), this.tfPtNo.getText().trim());
		if (ptNo != null) {
			if (dataState != DataState.ADD) {// !isAdd
				if (!ptNo.getId().equals(materiel.getId())) {
					JOptionPane.showMessageDialog(this, "料号不能重复,请重新输入!", "提示!",
							1);
					return true;
				}
			} else {
				JOptionPane.showMessageDialog(this, "料号不能重复,请重新输入!", "提示!", 1);
				return true;
			}
		}
		return false;
	}

	/**
	 * 初始化btnSave
	 * 
	 * @return
	 */

	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存(S)");
			btnSave.setMnemonic(java.awt.event.KeyEvent.VK_S);
			btnSave.setBounds(37, 399, 74, 24);
			btnSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (checkNull()) {
						return;
					}
					if (checkMultiple()) {
						return;
					}
					if (dataState == DataState.ADD) {// isAdd == true
						EnterpriseMaterial materiel = new EnterpriseMaterial();
						fillData(materiel);
						
						long beginTime=System.currentTimeMillis();
						
						materiel = (EnterpriseMaterial) materialManageAction
								.saveEnterpriseMaterial(new Request(CommonVars
										.getCurrUser(), true), materiel);
						long endTime=System.currentTimeMillis();
						
						System.out.println("------Save Time :"+(endTime-beginTime)/1000.0);

						if (materiel.getScmCoi().equals(scmCoi)) {
							tableModel.addRow(materiel);
						}
						clearData();

					} else if (dataState == DataState.EDIT) {
						fillData(materiel);
						
						 long beginTime=System.currentTimeMillis();
						materiel = (EnterpriseMaterial) materialManageAction
								.saveEnterpriseMaterial(new Request(CommonVars
										.getCurrUser(), true), materiel);
						
						long endTime=System.currentTimeMillis();
						System.out.println("------Edit Time :"+(endTime - beginTime)/1000.0);
						
						
						if (materiel.getScmCoi().equals(scmCoi)) {
							tableModel.updateRow(materiel);
						} else {
							tableModel.deleteRow(materiel);
						}
						DgEnterpriseMaterial.this.dispose();

					}
					// else if(dataState==DataState.CHANGE){
					// fillData(materiel);
					// materiel = (EnterpriseMaterial) materialManageAction
					// .changeEnterpriseMaterial(new Request(CommonVars
					// .getCurrUser()), materiel);
					// tableModel.updateRow(materiel);
					// DgCustomMaterial.this.dispose();
					// }
				}
			});

		}
		return btnSave;
	}

	/**
	 * 
	 * This method initializes btnCancel
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消(X)");
			btnCancel.setMnemonic(java.awt.event.KeyEvent.VK_X);
			btnCancel.setBounds(136, 399, 75, 24);
			btnCancel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgEnterpriseMaterial.this.dispose();

				}
			});

		}
		return btnCancel;
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
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setMaximumFractionDigits(9);
			decimalFormat.setGroupingSize(0);
			decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
			numberFormatter.setFormat(decimalFormat);
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
	 * This method initializes tfCorrespondingptNo
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfCorrespondingptNo() {
		if (tfCorrespondingptNo == null) {
			tfCorrespondingptNo = new JTextField();
			tfCorrespondingptNo.setBounds(293, 269, 143, 22);
		}
		return tfCorrespondingptNo;
	}

	private void getEqualPtNo() {
		if (cbbSort.getSelectedItem() != null
				&& ((ScmCoi) cbbSort.getSelectedItem()).getName() != null) {
			DgEnterpriseMaterial.this.tfCorrespondingptNo
					.setVisible(((ScmCoi) cbbSort.getSelectedItem()).getName()
							.equals("边角料"));
			DgEnterpriseMaterial.this.lbCorrespondingptNo
					.setVisible(((ScmCoi) cbbSort.getSelectedItem()).getName()
							.equals("边角料"));
		}
	}

	/**
	 * 
	 * This method initializes pn1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPn1() {
		if (pn1 == null) {
			// tfWasteWeight = new tfWasteWeight

			lbDeclareMnemonicCode = new JLabel();
			lbDeclareMnemonicCode.setBounds(new Rectangle(235, 324, 65, 24));
			lbDeclareMnemonicCode.setHorizontalAlignment(SwingConstants.RIGHT);
			lbDeclareMnemonicCode.setText("报关助记码");
			lbDeclareSpec = new JLabel();
			lbDeclareSpec.setBounds(new Rectangle(456, 75, 51, 23));
			lbDeclareSpec.setText("\u62a5\u5173\u89c4\u683c");
			lbDeclareSpec.setHorizontalAlignment(SwingConstants.RIGHT);
			lbDeclareName = new JLabel();
			lbDeclareName.setBounds(new Rectangle(456, 44, 49, 24));
			lbDeclareName.setText("\u62a5\u5173\u540d\u79f0");
			lbDeclareName.setHorizontalAlignment(SwingConstants.RIGHT);
			lbCorrespondingptNo = new JLabel();
			lbCorrespondingptNo.setBounds(new Rectangle(240, 269, 52, 24));
			lbCorrespondingptNo.setText("对应料号");
			lbClient = new JLabel();
			lbClient.setBounds(new Rectangle(456, 109, 70, 23));
			lbClient.setText("供应商/客户");
			lbStockNumber = new JLabel();
			lbWarehouseGross_weight = new JLabel();
			lbWarehouseSuttle = new JLabel();
			lbPackKind = new JLabel();
			lbWaste = new JLabel();
			lbForWaste = new JLabel();
			lbForproduce = new JLabel();
			lbForuse = new JLabel();
			lbUnitConvert = new JLabel();
			lbMaterialSource = new JLabel();
			lbWasteWeight = new JLabel();
			lbWasteCode = new JLabel();
			lbUnitConvertQuotiety = new JLabel();
			lbDeclareUnit = new JLabel();
			lbMarker = new JLabel();
			javax.swing.JLabel lbproBonded = new JLabel();

			javax.swing.JLabel lbTaxation = new JLabel();

			javax.swing.JLabel lbsysArea = new JLabel();

			javax.swing.JLabel lbptEnglishSpec = new JLabel();

			javax.swing.JLabel lbptEnglishName = new JLabel();

			javax.swing.JLabel lbptVersion = new JLabel();

			javax.swing.JLabel lbptOutWeight = new JLabel();

			javax.swing.JLabel lbWeightUnit = new JLabel();

			javax.swing.JLabel lbptNetWeight = new JLabel();

			javax.swing.JLabel lbptPrice = new JLabel();

			javax.swing.JLabel lbUnit = new JLabel();

			javax.swing.JLabel lbptDeSpec = new JLabel();

			javax.swing.JLabel lbTypeSpec = new JLabel();

			javax.swing.JLabel lbptName = new JLabel();

			javax.swing.JLabel lbptCode = new JLabel();

			javax.swing.JLabel lbptNo = new JLabel();

			javax.swing.JLabel lbSort = new JLabel();

			pn1 = new JPanel();
			pn1.setLayout(null);
			pn1.add(lbSort, null);
			pn1.add(getTfPtName(), null);
			pn1.add(lbptName, null);
			pn1.add(lbUnit, null);
			pn1.add(lbWeightUnit, null);
			pn1.add(lbptEnglishName, null);
			pn1.add(getTfPtEnglishName(), null);
			pn1.add(getCbbSysArea(), null);
			pn1.add(lbsysArea, null);
			pn1.add(getTfCorrespondingptNo(), null);
			pn1.add(lbptCode, null);
			pn1.add(getTfPtCode(), null);
			pn1.add(getBtnPtCode(), null);
			pn1.add(getTfPtDeSpec(), null);
			pn1.add(lbptDeSpec, null);
			pn1.add(lbptNetWeight, null);
			pn1.add(getTfPtNetWeight(), null);
			pn1.add(getTfPtVersion(), null);
			pn1.add(lbptVersion, null);
			pn1.add(getCbbProBonded(), null);
			pn1.add(lbproBonded, null);
			pn1.add(lbptNo, null);
			pn1.add(getTfPtNo(), null);
			pn1.add(lbTypeSpec, null);
			pn1.add(getTfPtSpec(), null);
			pn1.add(lbptPrice, null);
			pn1.add(getTfPtPrice(), null);
			pn1.add(lbptOutWeight, null);
			pn1.add(getTfPtOutWeight(), null);
			pn1.add(lbptEnglishSpec, null);
			pn1.add(getTfPtEnglishSpec(), null);
			pn1.add(lbTaxation, null);
			pn1.add(getCbbTaxation(), null);
			pn1.add(getBtnSave(), null);
			pn1.add(getBtnCancel(), null);
			pn1.setBorder(javax.swing.BorderFactory
					.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			lbSort.setText("类别");
			lbSort.setBounds(16, 14, 54, 22);
			lbSort.setForeground(new java.awt.Color(51, 51, 255));
			lbptNo.setText("料号");
			lbptNo.setBounds(240, 14, 30, 22);
			lbptNo.setForeground(java.awt.Color.blue);
			lbptCode.setText("商品编码");
			lbptCode.setBounds(456, 14, 56, 22);
			lbptName.setText("商品名称");
			lbptName.setBounds(16, 44, 54, 24);
			lbptName.setForeground(java.awt.Color.blue);
			lbTypeSpec.setText("型号规格");
			lbTypeSpec.setBounds(240, 44, 50, 24);
			lbptDeSpec.setText("详细规格");
			lbptDeSpec.setBounds(16, 75, 57, 24);
			lbUnit.setText("单位");
			lbUnit.setBounds(16, 109, 54, 23);
			lbUnit.setForeground(java.awt.Color.blue);
			lbptPrice.setText("单价");
			lbptPrice.setBounds(240, 109, 42, 23);
			lbptNetWeight.setText("净重");
			lbptNetWeight.setBounds(16, 141, 54, 22);
			lbptNetWeight.setForeground(java.awt.Color.black);
			lbptOutWeight.setText("毛重");
			lbptOutWeight.setBounds(240, 141, 49, 22);
			lbptVersion.setText("版本号");
			lbptVersion.setBounds(456, 172, 45, 23);
			lbptEnglishName.setText("英文品名");
			lbptEnglishName.setBounds(16, 172, 54, 23);
			lbptEnglishSpec.setText("英文规格");
			lbptEnglishSpec.setBounds(240, 172, 54, 23);
			lbsysArea.setText("原产国");
			lbsysArea.setBounds(16, 201, 54, 22);
			lbTaxation.setText("税制代码");
			lbTaxation.setBounds(240, 201, 51, 22);
			lbproBonded.setText("保纳税");
			lbproBonded.setBounds(456, 201, 43, 22);
			lbWeightUnit.setText("重量单位");
			lbWeightUnit.setBounds(456, 141, 55, 22);
			lbWeightUnit.setForeground(java.awt.Color.black);
			lbMarker.setBounds(16, 351, 174, 18);
			lbMarker.setText("* 注：1,标记为蓝色字体为必填项");
			lbMarker.setForeground(new java.awt.Color(153, 51, 0));
			lbDeclareUnit.setBounds(240, 234, 51, 23);
			lbDeclareUnit.setText("报关单位");
			lbUnitConvertQuotiety.setBounds(456, 234, 77, 23);
			lbUnitConvertQuotiety.setText("单位折算系数");
			lbWasteCode.setBounds(16, 234, 54, 23);
			lbWasteCode.setText("废料代码");
			lbWasteWeight.setBounds(16, 269, 54, 20);
			lbWasteWeight.setText("废料重量");
			lbMaterialSource.setBounds(16, 296, 54, 20);
			lbMaterialSource.setText("材料来源");
			lbUnitConvert.setBounds(46, 370, 189, 18);
			lbUnitConvert.setText("2,单位折算等于报关数量/工厂数量");
			lbUnitConvert.setForeground(new java.awt.Color(153, 51, 0));
			lbForuse.setBounds(259, 370, 253, 17);
			lbForuse.setText("1--为使用一库存单位材料本身产生的废料重量");
			lbForuse.setForeground(new java.awt.Color(153, 51, 0));
			lbForproduce.setBounds(259, 387, 264, 18);
			lbForproduce.setText("2--为生产一库存单位成品会产生的废料重量");
			lbForproduce.setForeground(new java.awt.Color(153, 51, 0));
			lbForWaste.setBounds(259, 405, 319, 17);
			lbForWaste.setText("3--为废料重量由成品决定的情况,并废料重量记录在成品中");
			lbForWaste.setForeground(new java.awt.Color(153, 51, 0));
			lbWaste.setBounds(242, 352, 110, 19);
			lbWaste.setText("3,废料代码的含义：");
			lbWaste.setForeground(new java.awt.Color(153, 51, 0));
			lbPackKind.setBounds(16, 326, 54, 20);
			lbPackKind.setText("包装种类");
			lbWarehouseSuttle.setBounds(240, 295, 52, 23);
			lbWarehouseSuttle.setText("仓库净重");
			lbWarehouseGross_weight.setBounds(456, 295, 60, 22);
			lbWarehouseGross_weight.setText("仓库毛重");
			lbStockNumber.setBounds(456, 329, 59, 18);
			lbStockNumber.setText("库存数量");
			pn1.add(lbMarker, null);
			pn1.add(getCbbSort(), null);
			pn1.add(getCbbUnit(), null);
			pn1.add(getCbbWeightUnit(), null);
			pn1.add(getCbbClient(), null);
			pn1.add(lbDeclareUnit, null);
			pn1.add(getCbbDeclareUnit(), null);
			pn1.add(lbUnitConvertQuotiety, null);
			pn1.add(getTfUnitConvertQuotiety(), null);
			pn1.add(lbWasteCode, null);
			pn1.add(getCbbWasteCode(), null);
			pn1.add(lbWasteWeight, null);
			pn1.add(getTfWasteWeight(), null);
			pn1.add(lbMaterialSource, null);
			pn1.add(getCbbMaterialSource(), null);
			pn1.add(lbUnitConvert, null);
			pn1.add(lbForuse, null);
			pn1.add(lbForproduce, null);
			pn1.add(lbForWaste, null);
			pn1.add(lbWaste, null);
			pn1.add(getCbPivotal(), null);
			pn1.add(lbPackKind, null);
			pn1.add(getCbbPackKind(), null);
			pn1.add(lbWarehouseSuttle, null);
			pn1.add(getTfWarehouseSuttle(), null);
			pn1.add(lbWarehouseGross_weight, null);
			pn1.add(getTfWarehouseGross_weight(), null);
			pn1.add(lbStockNumber, null);
			pn1.add(getTfStockNumber(), null);
			pn1.add(lbClient, null);
			pn1.add(getCbPrecative(), null);

			pn1.add(lbCorrespondingptNo, null);
			pn1.add(lbDeclareName, null);
			pn1.add(lbDeclareSpec, null);
			pn1.add(getTfDeclareName(), null);
			pn1.add(getTfDeclareSpec(), null);
			pn1.add(lbDeclareMnemonicCode, null);
			pn1.add(getTfCustomsNo(), null);
		}
		return pn1;
	}

	/**
	 * 
	 * This method initializes spn
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getSpn() {
		if (spn == null) {
			spn = new JSplitPane();
			spn.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			spn.setDividerLocation(40);
			spn.setDividerSize(0);
			spn.setTopComponent(getPn2());
			spn.setBottomComponent(getPn1());
		}
		return spn;
	}

	/**
	 * 
	 * This method initializes pn2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPn2() {
		if (pn2 == null) {
			javax.swing.JLabel jLabel21 = new JLabel();

			javax.swing.JLabel jLabel20 = new JLabel();

			javax.swing.JLabel jLabel19 = new JLabel();

			pn2 = new JPanel();
			pn2.setLayout(new BorderLayout());
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
			pn2.setBackground(java.awt.Color.white);
			pn2
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			pn2.add(jLabel19, java.awt.BorderLayout.EAST);
			pn2.add(jLabel20, java.awt.BorderLayout.CENTER);
			pn2.add(jLabel21, java.awt.BorderLayout.WEST);
		}
		return pn2;
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
	 * This method initializes cbbSort
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSort() {
		if (cbbSort == null) {
			cbbSort = new JComboBox();
			cbbSort.setBounds(70, 14, 152, 22);
			cbbSort.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					getEqualPtNo();
				}
			});
		}
		return cbbSort;
	}

	/**
	 * This method initializes cbbUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUnit() {
		if (cbbUnit == null) {
			cbbUnit = new JComboBox();
			cbbUnit.setBounds(70, 109, 152, 23);
		}
		return cbbUnit;
	}

	/**
	 * This method initializes cbbWeightUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWeightUnit() {
		if (cbbWeightUnit == null) {
			cbbWeightUnit = new JComboBox();
			cbbWeightUnit.setBounds(532, 141, 140, 22);
		}
		return cbbWeightUnit;
	}

	/**
	 * This method initializes cbbClient
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbClient() {
		if (cbbClient == null) {
			cbbClient = new JComboBox();
			cbbClient.setBounds(532, 109, 140, 23);
		}
		return cbbClient;
	}

	// /**
	// * @return Returns the isEdit.
	// */
	// public boolean isEdit() {
	// return isEdit;
	// }
	//
	// /**
	// * @param isEdit
	// * The isEdit to set.
	// */
	// public void setEdit(boolean isEdit) {
	// this.isEdit = isEdit;
	// }
	/**
	 * 设置控件状态
	 */

	private void setState() {
		cbbSort.setEnabled(dataState != DataState.BROWSE);
		tfPtNo.setEditable(dataState != DataState.BROWSE);
		btnPtCode.setEnabled(dataState != DataState.BROWSE);
		tfPtName.setEditable(dataState != DataState.BROWSE);// this.isEdit()
		tfPtSpec.setEditable(dataState != DataState.BROWSE);
		tfPtDeSpec.setEditable(dataState != DataState.BROWSE);
		cbbUnit.setEnabled(dataState != DataState.BROWSE);
		tfPtPrice.setEditable(dataState != DataState.BROWSE);
		tfPtNetWeight.setEditable(dataState != DataState.BROWSE);
		tfPtVersion.setEditable(dataState != DataState.BROWSE);
		tfPtOutWeight.setEditable(dataState != DataState.BROWSE);
		cbbWeightUnit.setEnabled(dataState != DataState.BROWSE);
		tfPtEnglishName.setEditable(dataState != DataState.BROWSE);
		tfPtEnglishSpec.setEditable(dataState != DataState.BROWSE);
		cbbClient.setEnabled(dataState != DataState.BROWSE);
		cbbProBonded.setEnabled(dataState != DataState.BROWSE);
		cbbTaxation.setEnabled(dataState != DataState.BROWSE);
		cbbSysArea.setEnabled(dataState != DataState.BROWSE);
		tfCorrespondingptNo.setEditable(dataState != DataState.BROWSE);
		// jTextField1.setEditable(dataState!=DataState.BROWSE);
		// jTextField2.setEditable(dataState!=DataState.BROWSE);
		this.cbPrecative.setVisible(materiel.getScmCoi().getCoiProperty()
				.equals(MaterielType.SEMI_FINISHED_PRODUCT));
	}

	/**
	 * This method initializes cbbDeclareUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDeclareUnit() {
		if (cbbDeclareUnit == null) {
			cbbDeclareUnit = new JComboBox();
			cbbDeclareUnit.setBounds(293, 234, 143, 23);
		}
		return cbbDeclareUnit;
	}

	/**
	 * This method initializes tfUnitConvertQuotiety
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitConvertQuotiety() {
		if (tfUnitConvertQuotiety == null) {
			tfUnitConvertQuotiety = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitConvertQuotiety.setBounds(532, 234, 140, 23);
		}
		return tfUnitConvertQuotiety;
	}

	/**
	 * This method initializes cbbWasteCode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWasteCode() {
		if (cbbWasteCode == null) {
			cbbWasteCode = new JComboBox(new String[] { "", "1", "2", "3" });
			cbbWasteCode.setBounds(70, 234, 152, 23);
		}
		return cbbWasteCode;
	}

	/**
	 * This method initializes tfWasteWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfWasteWeight() {
		if (tfWasteWeight == null) {
			tfWasteWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfWasteWeight.setBounds(70, 269, 152, 22);
		}
		return tfWasteWeight;
	}

	/**
	 * This method initializes cbbMaterialSource
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMaterialSource() {
		if (cbbMaterialSource == null) {
			cbbMaterialSource = new JComboBox(new String[] {"","自制", "国内购买",
					"国外采购", "委外加工", "客户供料" });
			cbbMaterialSource.setBounds(70, 296, 152, 22);
		}
		return cbbMaterialSource;
	}

	/**
	 * This method initializes cbPivotal
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbPivotal() {
		if (cbPivotal == null) {
			cbPivotal = new JCheckBox();
			cbPivotal.setBounds(456, 270, 78, 22);
			cbPivotal.setText("关键零件");
		}
		return cbPivotal;
	}

	/**
	 * This method initializes cbbPackKind
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbPackKind() {
		if (cbbPackKind == null) {
			cbbPackKind = new JComboBox();
			cbbPackKind.setBounds(70, 326, 152, 22);
		}
		return cbbPackKind;
	}

	/**
	 * This method initializes tfWarehouseSuttle
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfWarehouseSuttle() {
		if (tfWarehouseSuttle == null) {
			tfWarehouseSuttle = new JFormattedTextField();
			tfWarehouseSuttle.setBounds(293, 295, 143, 22);
		}
		return tfWarehouseSuttle;
	}

	/**
	 * This method initializes tfWarehouseGross_weight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfWarehouseGross_weight() {
		if (tfWarehouseGross_weight == null) {
			tfWarehouseGross_weight = new JFormattedTextField();
			tfWarehouseGross_weight.setBounds(532, 295, 140, 22);
		}
		return tfWarehouseGross_weight;
	}

	/**
	 * This method initializes tfStockNumber
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfStockNumber() {
		if (tfStockNumber == null) {
			tfStockNumber = new JFormattedTextField();
			tfStockNumber.setBounds(532, 326, 140, 22);
		}
		return tfStockNumber;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This method initializes cbPrecative
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbPrecative() {
		if (cbPrecative == null) {
			cbPrecative = new JCheckBox();
			cbPrecative.setBounds(new Rectangle(543, 270, 82, 23));
			cbPrecative.setText("是否委外");
		}
		return cbPrecative;
	}

	/**
	 * This method initializes tfDeclareName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDeclareName() {
		if (tfDeclareName == null) {
			tfDeclareName = new JTextField();
			tfDeclareName.setBounds(new Rectangle(532, 45, 142, 24));
		}
		return tfDeclareName;
	}

	/**
	 * This method initializes tfDeclareSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDeclareSpec() {
		if (tfDeclareSpec == null) {
			tfDeclareSpec = new JTextField();
			tfDeclareSpec.setBounds(new Rectangle(532, 75, 140, 24));
		}
		return tfDeclareSpec;
	}

	/**
	 * This method initializes tfCustomsNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomsNo() {
		if (tfCustomsNo == null) {
			tfCustomsNo = new JTextField();
			tfCustomsNo.setBounds(new Rectangle(304, 326, 131, 22));
		}
		return tfCustomsNo;
	}
}
