/*
 * Created on 2004-6-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import java.math.RoundingMode;
import java.text.ParseException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.ScmCoi;
import com.bestway.common.materialbase.entity.ShareCode;
import com.bestway.common.materialbase.entity.SysArea;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import java.text.DecimalFormat;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Point;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 * 报关常用工厂物料的修改
 */
public class DgMateriel extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel = null;

	/**
	 * 料号
	 */
	private JTextField tfptNo = null;

	/**
	 * 商品编码 
	 */
	private JTextField tfcomplex = null;

	/**
	 * 选择商品编码
	 */
	private JButton btnComplex = null;

	/**
	 * 商品名称
	 */
	private JTextField tfPtFactoryName = null;

	/**
	 * 型号规格
	 */
	private JTextField tfPtFactorySpec = null;

	/**
	 * 详细规格
	 */
	private JTextField tfptDeSpec = null;

	/**
	 * 单价
	 */
	private JFormattedTextField tfptPrice = null;

	/**
	 * 净重
	 */
	private JFormattedTextField tfptNetWeight = null;

	/**
	 * 毛重
	 */
	private JFormattedTextField tfptOutWeight = null;

	/**
	 * 版本号
	 */
	private JTextField tfptVersion = null;

	/**
	 * 英文名
	 */
	private JTextField tfptEnglishName = null;

	/**
	 * 英文规格
	 */
	private JTextField tfptEnglishSpec = null;

	/**
	 * 原产地
	 */
	private JComboBox cfsysArea = null;

	/**
	 * 税制代码
	 */
	private JComboBox cftaxation = null;

	/**
	 * 保纳税
	 */
	private JComboBox cfproBonded = null;

	/**
	 * 保存
	 */
	private JButton btnSave = null;

	/**
	 * 取消
	 */
	private JButton btnCancel = null;

	/**
	 * 料件管理操作接口
	 */
	private MaterialManageAction materialManageAction = null;

	private CustomBaseAction customBaseAction = null;

	/**
	 * 企业
	 */
	private Materiel materiel = null;

	/**
	 * 海关商品编码
	 */
	private Complex complex = null;

	/**
	 * 物料类别
	 */
	private ScmCoi scmCoi = null;

	private boolean isAdd = true;

	private JTableListModel tableModel = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	/**
	 * 报关助词码
	 */
	private JTextField tfMemoryNo = null;

	private javax.swing.JLabel jLabel14 = new JLabel();

	private JPanel jPanel1 = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel24 = null;

	/**
	 * 物料类别
	 */
	private JComboBox cbbType = null;

	/**
	 * 单位
	 */
	private JComboBox cbbCalUnit = null;

	/**
	 * 重量单位
	 */
	private JComboBox cbbWeightUnit = null;

	/**
	 * 供应商/客户商
	 */
	private JComboBox cbbCustomer = null;

	private boolean isEdit = true;

	private JLabel jLabel25 = null;

	/**
	 * 报关单位
	 */
	private JComboBox cbbPtUnit = null;

	private JLabel jLabel26 = null;

	/**
	 * 单位折算系数
	 */
	private JFormattedTextField ftfUnit = null;

	private JLabel jLabel27 = null;

	/**
	 * 废料代码
	 */
	private JComboBox cbbScrapNo = null;

	private JLabel jLabel28 = null;

	private JFormattedTextField ftfScrapWeight = null;

	private JLabel jLabel29 = null;

	/**
	 * 材料来源
	 */
	private JComboBox cbxMaterialSource = null;

	private JLabel jLabel30 = null;

	private JLabel jLabel31 = null;

	private JLabel jLabel32 = null;

	private JLabel jLabel33 = null;

	private JLabel jLabel34 = null;

	private JCheckBox cbPivotal = null;

	private JLabel jLabel35 = null;

	/**
	 * 包装种类
	 */
	private JComboBox cbxPackgeType = null;

	private JLabel jLabel36 = null;

	/**
	 * 仓库净重
	 */
	private JFormattedTextField ftStorageFrealityWeight = null;

	private JLabel jLabel37 = null;

	/**
	 * 仓库毛重
	 */
	private JFormattedTextField ftfStorageWeight = null;

	private JLabel jLabel38 = null;

	/**
	 * 仓库数量
	 */
	private JFormattedTextField ftfStorageCount = null;

	/**
	 * 是否委外
	 */
	private JCheckBox cbPrecative = null;

	/**
	 * 版本号选择
	 */
	private JButton btnVersion = null;

	private JLabel jLabel22 = null;

	private JLabel jLabel23 = null;

	/**
	 * 报关名称
	 */
	private JTextField tfPtName = null;

	/**
	 * 报关规格
	 */
	private JTextField tfPtSpec = null;

	/**
	 * 上一笔
	 */
	private JButton btnPrevious = null;

	/**
	 * 下一笔
	 */
	private JButton btnNext = null;

	/**
	 * 报关助词码
	 */
	private JTextField tfCustomsNo = null;

	private JLabel jLabel39 = null;

	/**
	 * This is the default constructor
	 */
	public DgMateriel() {
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
		this.setSize(659, 495);
		this.setTitle("物料主档");
		this.setContentPane(getJContentPane());
	}

	/**
	 * 初始化显示
	 */
	public void setVisible(boolean b) {
		if (b) {
			if (isAdd == false) {
				if (tableModel.getCurrentRow() != null) {
					materiel = (Materiel) tableModel.getCurrentRow();
					complex = materiel.getComplex();
					scmCoi = materiel.getScmCoi();
				}
				initUI();
				showData();
				setState();
			} else {
				initUI();
			}
			getEqualPtNo();
		}
		super.setVisible(b);
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		// 类别
		List list = materialManageAction.findScmCois(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbType.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbType.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbType,
				"code", "name");
		if (scmCoi != null) // 类别
		{
			cbbType.setSelectedItem(scmCoi);
		} else {
			cbbType.setSelectedItem(null);
		}
		// 工厂单位
		List listunit = materialManageAction.findCalUnit(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbCalUnit.setModel(new DefaultComboBoxModel(listunit.toArray()));
		this.cbbCalUnit.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCalUnit, "code", "name");
		cbbCalUnit.setSelectedIndex(-1);
		// 重量单位
		this.cbbWeightUnit.setModel(new DefaultComboBoxModel(listunit.toArray()));
		this.cbbWeightUnit.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbWeightUnit, "code", "name");
		cbbWeightUnit.setSelectedIndex(-1);
		// 报关单位
		this.cbbPtUnit.setModel(new DefaultComboBoxModel(CustomBaseList
				.getInstance().getUnits().toArray()));
		this.cbbPtUnit.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbPtUnit, "code", "name");
		cbbPtUnit.setSelectedIndex(-1);
		// 供应商
		List listscm = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbCustomer.setModel(new DefaultComboBoxModel(listscm.toArray()));
		this.cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 50, 200));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCustomer, "code", "name");
		this.cbbCustomer.setUI(new CustomBaseComboBoxUI(250));
		cbbCustomer.setSelectedIndex(-1);
		// 原产国
		List sysarea = materialManageAction.findSysAreas(new Request(CommonVars
				.getCurrUser(), true));
		this.cfsysArea.setModel(new DefaultComboBoxModel(sysarea.toArray()));
		this.cfsysArea.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cfsysArea, "code", "name");
		cfsysArea.setSelectedIndex(-1);
		// 税制代码
		List taxaTion = materialManageAction.findTaxaTion(new Request(
				CommonVars.getCurrUser(), true));
		this.cftaxation.setModel(new DefaultComboBoxModel(taxaTion.toArray()));
		this.cftaxation.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cftaxation, "code", "name");
		cftaxation.setSelectedIndex(-1);
		// 初始化包装种类
		List listwarp = customBaseAction.findWrap();
		this.cbxPackgeType.setModel(new DefaultComboBoxModel(listwarp.toArray()));
		this.cbxPackgeType.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbxPackgeType, "code", "name");
		cbxPackgeType.setSelectedIndex(-1);

	}

	/**
	 * This method initializes jContentPane
	 * 显示数据
	 * @return javax.swing.JPanel
	 */
	private void showData() {
		if (materiel != null && isAdd == false) {
			tfptNo.setText(materiel.getPtNo()); // 料号
			tfPtFactoryName.setText(materiel.getFactoryName()); // 工厂商品名称
			tfPtFactorySpec.setText(materiel.getFactorySpec()); // 工厂型号规格
			this.tfPtName.setText(materiel.getPtName()); // 报关商品名称
			this.tfPtSpec.setText(materiel.getPtSpec()); // 报关商品规格
			tfptDeSpec.setText(materiel.getPtDeSpec()); // 详细规格
			tfptPrice.setText(doubleToStr(materiel.getPtPrice())); // 单价
			ftfStorageCount.setText(doubleToStr(materiel.getAmount()));// 库存数量
			tfptNetWeight.setText(doubleToStr(materiel.getPtNetWeight())); // 净重
			// java.text.DecimalFormat df =new
			// java.text.DecimalFormat("#.000000");
			// df.format(float param);
			// df.format(doubleToStr(materiel.getPtNetWeight()));
			tfptOutWeight.setText(doubleToStr(materiel.getPtOutWeight())); // 毛重
			tfCustomsNo.setText(materiel.getCustomsNo());//报关助记码
			ftStorageFrealityWeight
					.setText(doubleToStr(materiel.getCknetWeight())); // 仓库净重
			ftfStorageWeight
					.setText(doubleToStr(materiel.getCkoutWeight())); // 仓库毛重
			ftfScrapWeight
					.setText(doubleToStr(materiel.getScrapWeight()));// 废料重量
			tfptVersion.setText(materiel.getPtVersion()); // 版本号
			tfptEnglishName.setText(materiel.getPtEnglishName());// 英文品名
			tfptEnglishSpec.setText(materiel.getPtEnglishSpec());// 英文规格
			DgMateriel.this.tfMemoryNo.setText(materiel.getEqualPtno());
			this.cftaxation.setSelectedItem(materiel.getTaxation());
			this.ftfUnit.setText(doubleToStr(materiel
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
				this.tfcomplex.setText(complex.getCode());
			} else {
				this.tfcomplex.setText("");
			}

			if (this.materiel.getCalUnit() != null) // 单位
			{
				this.cbbCalUnit.setSelectedItem(materiel.getCalUnit());
			} else {
				this.cbbCalUnit.setSelectedItem(null);
			}
			if (this.materiel.getScrapCode() != null) { // 废料代码
				this.cbbScrapNo.setSelectedItem(materiel.getScrapCode());
			} else {
				this.cbbScrapNo.setSelectedItem(null);
			}
			if (this.materiel.getMaterialSource() != null) { // 材料来源
				this.cbxMaterialSource.setSelectedItem(materiel.getMaterialSource());
			} else {
				this.cbxMaterialSource.setSelectedItem(null);
			}
			if (this.materiel.getCalWeightUnit() != null) // 重量单位
			{
				this.cbbWeightUnit.setSelectedItem(materiel.getCalWeightUnit());
			} else {
				this.cbbWeightUnit.setSelectedItem(null);
			}
			if (this.materiel.getPtUnit() != null) // 报关单位
			{
				this.cbbPtUnit.setSelectedItem(materiel.getPtUnit());
			} else {
				this.cbbPtUnit.setSelectedItem(null);
			}
			if (this.materiel.getSysArea() != null) // 原产国
			{
				this.cfsysArea.setSelectedItem(materiel.getSysArea());
			} else {
				this.cfsysArea.setSelectedItem(null);
			}
			if (this.materiel.getTaxation() != null) // 税制代码
			{
				this.cftaxation.setSelectedItem(materiel.getTaxation());
			} else {
				this.cftaxation.setSelectedItem(null);
			}
			if (this.materiel.getWrap() != null) // 包装种类
			{
				this.cbxPackgeType.setSelectedItem(materiel.getWrap());
			} else {
				this.cbxPackgeType.setSelectedItem(null);
			}

			if (this.materiel.getScmCoc() != null) // 供应商
			{
				this.cbbCustomer.setSelectedItem(materiel.getScmCoc());
			} else {
				this.cbbCustomer.setSelectedItem(null);
			}
			if (materiel.getProBonded() == "0")
				this.cfproBonded.setSelectedItem("保税");
			else if (materiel.getProBonded() == "1")
				this.cfproBonded.setSelectedItem("纳税");
		}
	}

	/**
	 * 获取内容面板
	 * @return
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 字符串转成Double型
	 * @param value
	 * @return
	 */
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

	/**
	 * Double转字符串
	 * @param value
	 * @return
	 */
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
			tfptNo.setBounds(271, 14, 125, 22);
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
			tfcomplex.setBounds(480, 14, 104, 22);
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
	private JButton getBtnComplex() {
		if (btnComplex == null) {
			btnComplex = new JButton();
			btnComplex.setText("...");
			btnComplex.setBounds(585, 14, 24, 21);
			btnComplex.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					Complex complex = (Complex) CommonQuery.getInstance()
							.getComplex(scmCoi.getCoiProperty());
					if (complex != null) {
						DgMateriel.this.complex = complex;
						getTfcomplex().setText(complex.getCode());
						if(getTfPtName().getText()==null||getTfPtName().getText().equals("")){
							getTfPtName().setText(complex.getName());
//							getTfPtSpec().setText(complex.get)
//							getCbbPtUnit().setSelectedItem(anObject);
						}
						
						
					}
				}
			});

		}
		return btnComplex;
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
	private JTextField getTfPtFactoryName() {
		if (tfPtFactoryName == null) {
			tfPtFactoryName = new JTextField();
			tfPtFactoryName.setBounds(73, 45, 130, 22);
		}
		return tfPtFactoryName;
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
	private JTextField getTfPtFactorySpec() {
		if (tfPtFactorySpec == null) {
			tfPtFactorySpec = new JTextField();
			tfPtFactorySpec.setBounds(271, 44, 125, 22);
		}
		return tfPtFactorySpec;
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
			tfptDeSpec.setBounds(73, 72, 322, 22);
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
			tfptPrice.setBounds(271, 99, 125, 22);
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
			tfptNetWeight.setBounds(72, 134, 130, 22);
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
			tfptOutWeight.setBounds(270, 130, 125, 22);
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
			tfptVersion.setBounds(480, 163, 107, 22);
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
			tfptEnglishName.setBounds(72, 163, 130, 22);
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
			tfptEnglishSpec.setBounds(270, 162, 125, 22);
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
			cfsysArea.setBounds(72, 193, 130, 21);
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
			cftaxation.setBounds(270, 192, 125, 22);
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
			cfproBonded.setBounds(480, 193, 128, 21);
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
		if (this.cbbType.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "类别不能为空！", "提示！", 0);
			return true;
		}
		if (this.tfptNo.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "料号不能为空！", "提示！", 0);
			return true;
		}
		if (this.tfPtFactoryName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "商品名称不能为空！", "提示！", 0);
			return true;
		}
		if (this.cbbCalUnit.getSelectedItem() == null) {
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
	 * @param materiel
	 */
	public void fillData(Materiel materiel) { // 保存
		materiel.setScmCoi((ScmCoi) cbbType.getSelectedItem()); // 类别
		materiel.setPtNo(tfptNo.getText()); // 料号
		materiel.setComplex(DgMateriel.this.complex); // 商品编码
		materiel.setCompany(CommonVars.getCurrUser().getCompany()); // 公司id
		materiel.setFactoryName(this.tfPtFactoryName.getText()); // 工厂商品名称
		materiel.setFactorySpec(this.tfPtFactorySpec.getText()); // 工厂商品规格
		materiel.setPtName(tfPtName.getText());// 报关商品名称
		materiel.setPtSpec(tfPtSpec.getText());// 报关规格型号
		materiel.setPtDeSpec(tfptDeSpec.getText()); // 详细型号规格
		materiel.setCalUnit((CalUnit) cbbCalUnit.getSelectedItem()); // 单位
		materiel.setPtPrice(strToDouble(tfptPrice.getText())); // 单价
		materiel.setPtNetWeight(strToDouble(tfptNetWeight.getText()));// 净重
		materiel.setScrapWeight(strToDouble(ftfScrapWeight.getText()));// 废料重量
		materiel.setCalWeightUnit((CalUnit) cbbWeightUnit.getSelectedItem()); // 重量单位
		materiel.setPtUnit((Unit) cbbPtUnit.getSelectedItem()); // 报关单位
		materiel.setPtOutWeight(strToDouble(tfptOutWeight.getText()));// 毛重
		materiel.setCknetWeight(strToDouble(ftStorageFrealityWeight.getText()));// 仓库净重
		materiel.setCkoutWeight(strToDouble(ftfStorageWeight.getText()));// 仓库毛重
		materiel
				.setUnitConvert(strToDouble(this.ftfUnit.getText()));// 单位系数
		materiel.setPtVersion(tfptVersion.getText());// 版本号
		materiel.setPtEnglishName(tfptEnglishName.getText()); // 英文品名
		materiel.setPtEnglishSpec(tfptEnglishSpec.getText()); // 英文规格
		materiel.setScmCoc((ScmCoc) cbbCustomer.getSelectedItem()); // 供应商
		materiel.setSysArea((SysArea) cfsysArea.getSelectedItem());// 原产国
		materiel.setTaxation((ShareCode) cftaxation.getSelectedItem());// 税制代码
		materiel.setCustomsNo(tfCustomsNo.getText());//报关助记码
		if (cbbScrapNo.getSelectedItem() != null) { // 废料代码
			materiel.setScrapCode(cbbScrapNo.getSelectedItem().toString());
		} else {
			materiel.setScrapCode("");
		}
		// 包装种类
		materiel.setWrap((Wrap) cbxPackgeType.getSelectedItem());
		if (cbxMaterialSource.getSelectedItem() != null) { // 材料来源
			materiel.setMaterialSource(cbxMaterialSource.getSelectedItem().toString());
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
		materiel.setEqualPtno(tfMemoryNo.getText());
		if (cfproBonded.getSelectedItem() == "保税") // 保纳税
			materiel.setProBonded("0");
		else if (cfproBonded.getSelectedItem() == "纳税")
			materiel.setProBonded("1");
		materiel.setAmount(strToDouble(ftfStorageCount.getText()));
		// materiel.setIsUseInBcsInnerMerge(new Boolean(true));
	}

	/**
	 * 清除数据
	 */
	private void clearData() {
		tfptNo.setText("");
		tfcomplex.setText("");
		tfPtFactoryName.setText("");
		tfPtFactorySpec.setText("");
		// this.jTextField1.setText("");
		// this.jTextField2.setText("");
		tfptDeSpec.setText("");
		this.cbbCalUnit.setSelectedIndex(-1);
		tfptPrice.setText("");
		ftfStorageCount.setText("");
		tfptNetWeight.setText("");
		this.cbbWeightUnit.setSelectedIndex(-1);
		this.cbbPtUnit.setSelectedIndex(-1);
		tfptOutWeight.setText("");
		this.ftfUnit.setText("");
		tfptVersion.setText("");
		tfptEnglishName.setText("");
		tfptEnglishSpec.setText("");
		this.cbbCustomer.setSelectedIndex(-1);
		cfsysArea.setSelectedItem("");
		cftaxation.setSelectedItem("");
		cfproBonded.setSelectedItem("");
		tfCustomsNo.setText("");
		/*
		 * this.complex = null; this.customUnit = null; this.customUnit1 = null;
		 * this.scmCoc = null; this.scmCoi = null;
		 */
	}

	/**
	 * 输入检查
	 */
	private boolean checkMultiple() {
		Materiel ptNo = materialManageAction.findMaterielByPtNo(new Request(
				CommonVars.getCurrUser(), true), this.tfptNo.getText().trim());
		if (ptNo != null) {
			if (!isAdd) {
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

	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存(S)");
			btnSave.setMnemonic(java.awt.event.KeyEvent.VK_S);
			btnSave.setBounds(37, 386, 74, 24);
			btnSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (checkNull()) {
						return;
					}
					if (checkMultiple()) {
						return;
					}
					if (isAdd == true) {
						Materiel materiel = new Materiel();
						fillData(materiel);
						
						long beginTime=System.currentTimeMillis();
						materiel = (Materiel) materialManageAction
								.saveMateriel(new Request(CommonVars
										.getCurrUser(), true), materiel);
						long endTime=System.currentTimeMillis();
						System.out.println("------Save Time :"+(endTime-beginTime)/1000.0);
						
						if (materiel.getScmCoi().equals(scmCoi)) {
							tableModel.addRow(materiel);
						}
						clearData();
					} else {
						fillData(materiel);
						materiel = (Materiel) materialManageAction
								.saveMateriel(new Request(CommonVars
										.getCurrUser(), true), materiel);
						if (materiel.getScmCoi().equals(scmCoi)) {
							tableModel.updateRow(materiel);
						} else {
							tableModel.deleteRow(materiel);
						}
						DgMateriel.this.dispose();

					}
				}
			});

		}
		return btnSave;
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
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消(X)");
			btnCancel.setMnemonic(java.awt.event.KeyEvent.VK_X);
			btnCancel.setBounds(136, 386, 74, 24);
			btnCancel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgMateriel.this.dispose();

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

	/**
	 * @return Returns the isAdd.
	 */
	public boolean isAdd() {
		return isAdd;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
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
	 * 
	 * This method initializes jTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField() {
		if (tfMemoryNo == null) {
			tfMemoryNo = new JTextField();
			tfMemoryNo.setBounds(273, 281, 125, 22);
		}
		return tfMemoryNo;
	}

	/**
	 * 设置报关助词码是否可见
	 */
	private void getEqualPtNo() {
		DgMateriel.this.tfMemoryNo.setVisible(((ScmCoi) cbbType
				.getSelectedItem()).getName().equals("边角料"));
		DgMateriel.this.jLabel14
				.setVisible(((ScmCoi) cbbType.getSelectedItem()).getName()
						.equals("边角料"));
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
			jLabel39 = new JLabel();
			jLabel39.setBounds(new Rectangle(208, 282, 61, 22));
			jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel39.setText("报关助记码");
			jLabel23 = new JLabel();
			jLabel23.setBounds(new java.awt.Rectangle(412, 72, 68, 21));
			jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel23.setText("报关规格");
			jLabel22 = new JLabel();
			jLabel22.setBounds(new java.awt.Rectangle(420, 43, 59, 23));
			jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel22.setText("报关名称");
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
			jPanel1.add(getTfPtFactoryName(), null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(jLabel6, null);
			jPanel1.add(jLabel9, null);
			jPanel1.add(jLabel12, null);
			jPanel1.add(getTfptEnglishName(), null);
			jPanel1.add(getCfsysArea(), null);
			jPanel1.add(jLabel16, null);
			jPanel1.add(getJTextField(), null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(getTfCustomsNo(), null);
			jPanel1.add(getTfcomplex(), null);
			jPanel1.add(getBtnComplex(), null);
			jPanel1.add(jLabel22, null);
			jPanel1.add(jLabel23, null);
			jPanel1.add(getTfPtName(), null);
			jPanel1.add(getTfPtSpec(), null);
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
			jPanel1.add(getTfPtFactorySpec(), null);
			jPanel1.add(jLabel7, null);
			jPanel1.add(getTfptPrice(), null);
			jPanel1.add(jLabel10, null);
			jPanel1.add(getTfptOutWeight(), null);
			jPanel1.add(jLabel13, null);
			jPanel1.add(getTfptEnglishSpec(), null);
			jPanel1.add(jLabel17, null);
			jPanel1.add(getCftaxation(), null);
			jPanel1.add(getBtnSave(), null);
			jPanel1.add(getBtnCancel(), null);
			jPanel1.setBorder(javax.swing.BorderFactory
					.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jLabel.setText("类别");
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel.setBounds(40, 15, 30, 19);
			jLabel.setForeground(new java.awt.Color(51, 51, 255));
			jLabel1.setText("料号");
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel1.setBounds(239, 14, 30, 20);
			jLabel1.setForeground(java.awt.Color.blue);
			jLabel2.setText("商品编码");
			jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel2.setBounds(422, 14, 56, 21);
			jLabel3.setText("商品名称");
			jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel3.setBounds(20, 45, 50, 22);
			jLabel3.setForeground(java.awt.Color.blue);
			jLabel4.setText("型号规格");
			jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel4.setBounds(219, 44, 50, 22);
			jLabel5.setText("详细规格");
			jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel5.setBounds(-4, 72, 77, 21);
			jLabel6.setText("单位");
			jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel6.setBounds(26, 100, 44, 21);
			jLabel6.setForeground(java.awt.Color.blue);
			jLabel7.setText("单价");
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel7.setBounds(227, 99, 42, 20);
			jLabel8.setText("净重");
			jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel8.setBounds(19, 132, 50, 20);
			jLabel8.setForeground(java.awt.Color.black);
			jLabel10.setText("毛重");
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setBounds(219, 130, 49, 22);
			jLabel11.setText("版本号");
			jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel11.setBounds(410, 163, 67, 22);
			jLabel12.setText("英文品名");
			jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel12.setBounds(18, 163, 51, 23);
			jLabel13.setText("英文规格");
			jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel13.setBounds(214, 162, 54, 23);
			jLabel15.setText("供应商/客户");
			jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel15.setBounds(200, 222, 68, 22);
			jLabel16.setText("原产国");
			jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel16.setBounds(25, 191, 44, 22);
			jLabel17.setText("税制代码");
			jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel17.setBounds(217, 192, 51, 21);
			jLabel18.setText("保纳税");
			jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel18.setBounds(414, 193, 63, 19);
			jLabel9.setText("重量单位");
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setBounds(212, 251, 57, 22);
			jLabel9.setForeground(java.awt.Color.black);
			jLabel24.setBounds(14, 340, 174, 18);
			jLabel24.setText("* 注：1,标记为蓝色字体为必填项");
			jLabel24.setForeground(new java.awt.Color(153, 51, 0));
			jLabel25.setBounds(424, 100, 54, 22);
			jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel25.setText("报关单位");
			jLabel26.setBounds(401, 130, 77, 22);
			jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel26.setText("单位折算系数");
			jLabel27.setBounds(17, 221, 52, 20);
			jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel27.setText("废料代码");
			jLabel28.setBounds(12, 250, 57, 20);
			jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel28.setText("废料重量");
			jLabel29.setBounds(16, 282, 53, 20);
			jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel29.setText("材料来源");
			jLabel30.setBounds(46, 359, 189, 18);
			jLabel30.setText("2,单位折算等于报关数量/工厂数量");
			jLabel30.setForeground(new java.awt.Color(153, 51, 0));
			jLabel31.setBounds(350, 338, 253, 17);
			jLabel31.setText("1--为使用一库存单位材料本身产生的废料重量");
			jLabel31.setForeground(new java.awt.Color(153, 51, 0));
			jLabel32.setBounds(350, 355, 264, 18);
			jLabel32.setText("2--为生产一库存单位成品会产生的废料重量");
			jLabel32.setForeground(new java.awt.Color(153, 51, 0));
			jLabel33.setBounds(350, 373, 280, 17);
			jLabel33.setText("3--为废料重量由成品决定的情况,并废料重量记录在成品中");
			jLabel33.setForeground(new java.awt.Color(153, 51, 0));
			jLabel34.setBounds(242, 341, 110, 19);
			jLabel34.setText("3,废料代码的含义：");
			jLabel34.setForeground(new java.awt.Color(153, 51, 0));
			jLabel35.setBounds(15, 312, 54, 20);
			jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel35.setText("包装种类");
			jLabel36.setBounds(217, 308, 52, 23);
			jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel36.setText("仓库净重");
			jLabel37.setBounds(417, 252, 60, 22);
			jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel37.setText("仓库毛重");
			jLabel38.setBounds(418, 286, 59, 18);
			jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel38.setText("库存数量");
			jPanel1.add(jLabel14, java.awt.BorderLayout.NORTH);
			// jPanel1.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
			jPanel1.add(jLabel24, null);
			jPanel1.add(getJComboBox(), null);
			jPanel1.add(getCbbCalUnit(), null);
			jPanel1.add(getCbbWeightUnit(), null);
			jPanel1.add(getCbbCustomer(), null);
			jPanel1.add(jLabel25, null);
			jPanel1.add(getCbbPtUnit(), null);
			jPanel1.add(jLabel26, null);
			jPanel1.add(getFtfUnit(), null);
			jPanel1.add(jLabel27, null);
			jPanel1.add(getCbbScrapNo(), null);
			jPanel1.add(jLabel28, null);
			jPanel1.add(getFtfScrapWeight(), null);
			jPanel1.add(jLabel29, null);
			jPanel1.add(getCbxMaterialSource(), null);
			jPanel1.add(jLabel30, null);
			jPanel1.add(jLabel31, null);
			jPanel1.add(jLabel32, null);
			jPanel1.add(jLabel33, null);
			jPanel1.add(jLabel34, null);
			jPanel1.add(getCbPivotal(), null);
			jPanel1.add(jLabel35, null);
			jPanel1.add(getJComboBox7(), null);
			jPanel1.add(jLabel36, null);
			jPanel1.add(getFtStorageFrealityWeight(), null);
			jPanel1.add(jLabel37, null);
			jPanel1.add(getFtfStorageWeight(), null);
			jPanel1.add(jLabel38, null);
			jPanel1.add(getFtfStorageCount(), null);
			jPanel1.add(getCbPrecative(), null);
			jPanel1.add(getBtnVersion(), null);
			jPanel1.add(getBtnPrevious(), null);
			jPanel1.add(getBtnNext(), null);
			jPanel1.add(jLabel39, null);
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
			jLabel20.setText("报关常用工厂物料");
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
		if (cbbType == null) {
			cbbType = new JComboBox();
			cbbType.setBounds(73, 14, 130, 21);
		}
		return cbbType;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCalUnit() {
		if (cbbCalUnit == null) {
			cbbCalUnit = new JComboBox();
			cbbCalUnit.setBounds(73, 100, 130, 23);
		}
		return cbbCalUnit;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWeightUnit() {
		if (cbbWeightUnit == null) {
			cbbWeightUnit = new JComboBox();
			cbbWeightUnit.setBounds(271, 251, 123, 23);
		}
		return cbbWeightUnit;
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustomer() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(270, 223, 124, 23);
		}
		return cbbCustomer;
	}

	/**
	 * @return Returns the isEdit.
	 */
	public boolean isEdit() {
		return isEdit;
	}

	/**
	 * @param isEdit
	 *            The isEdit to set.
	 */
	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	/**
	 * 设置状态
	 */
	private void setState() {
		cbbType.setEnabled(this.isEdit());
		tfptNo.setEditable(this.isEdit());
		btnComplex.setEnabled(this.isEdit());
		tfPtFactoryName.setEditable(this.isEdit());
		tfPtFactorySpec.setEditable(this.isEdit());
		tfPtName.setEditable(this.isEdit());
		tfPtSpec.setEditable(this.isEdit());
		tfptDeSpec.setEditable(this.isEdit());
		cbbCalUnit.setEnabled(this.isEdit());
		tfptPrice.setEditable(this.isEdit());
		tfptNetWeight.setEditable(this.isEdit());
		// tfptVersion.setEditable(this.isEdit());
		tfptOutWeight.setEditable(this.isEdit());
		cbbWeightUnit.setEnabled(this.isEdit());
		tfptEnglishName.setEditable(this.isEdit());
		tfptEnglishSpec.setEditable(this.isEdit());
		cbbCustomer.setEnabled(this.isEdit());
		cfproBonded.setEnabled(this.isEdit());
		cftaxation.setEnabled(this.isEdit());
		cfsysArea.setEnabled(this.isEdit());
		tfMemoryNo.setEditable(this.isEdit());
		cbPrecative.setVisible(materiel.getScmCoi().getCoiProperty().equals(
				MaterielType.SEMI_FINISHED_PRODUCT));
		// jTextField1.setEditable(this.isEdit());
		// jTextField2.setEditable(this.isEdit());
	}

	/**
	 * This method initializes jComboBox4
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbPtUnit() {
		if (cbbPtUnit == null) {
			cbbPtUnit = new JComboBox();
			cbbPtUnit.setBounds(480, 100, 128, 24);
		}
		return cbbPtUnit;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getFtfUnit() {
		if (ftfUnit == null) {
			ftfUnit = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			ftfUnit.setBounds(480, 130, 128, 24);
		}
		return ftfUnit;
	}

	/**
	 * This method initializes jComboBox5
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScrapNo() {
		if (cbbScrapNo == null) {
			cbbScrapNo = new JComboBox(new String[] { "", "1", "2", "3" });
			cbbScrapNo.setBounds(72, 221, 130, 20);
		}
		return cbbScrapNo;
	}

	/**
	 * This method initializes jFormattedTextField1
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getFtfScrapWeight() {
		if (ftfScrapWeight == null) {
			ftfScrapWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			ftfScrapWeight.setBounds(72, 250, 130, 22);
		}
		return ftfScrapWeight;
	}

	/**
	 * This method initializes jComboBox6
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbxMaterialSource() {
		if (cbxMaterialSource == null) {
			cbxMaterialSource = new JComboBox(new String[] { "自制", "市购", "采购", "国内购买",
					"国外采购", "委外加工", "客户供料" });
			cbxMaterialSource.setSize(new Dimension(130, 22));
			cbxMaterialSource.setLocation(new Point(72, 282));
		}
		return cbxMaterialSource;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbPivotal() {
		if (cbPivotal == null) {
			cbPivotal = new JCheckBox();
			cbPivotal.setBounds(422, 222, 82, 21);
			cbPivotal.setText("关键零件");
		}
		return cbPivotal;
	}

	/**
	 * This method initializes jComboBox7
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox7() {
		if (cbxPackgeType == null) {
			cbxPackgeType = new JComboBox();
			cbxPackgeType.setBounds(72, 312, 130, 22);
		}
		return cbxPackgeType;
	}

	/**
	 * This method initializes jFormattedTextField2
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getFtStorageFrealityWeight() {
		if (ftStorageFrealityWeight == null) {
			ftStorageFrealityWeight = new JFormattedTextField();
			ftStorageFrealityWeight.setBounds(273, 309, 120, 22);
		}
		return ftStorageFrealityWeight;
	}

	/**
	 * This method initializes jFormattedTextField3
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getFtfStorageWeight() {
		if (ftfStorageWeight == null) {
			ftfStorageWeight = new JFormattedTextField();
			ftfStorageWeight.setBounds(483, 252, 128, 22);
		}
		return ftfStorageWeight;
	}

	/**
	 * This method initializes jFormattedTextField4
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getFtfStorageCount() {
		if (ftfStorageCount == null) {
			ftfStorageCount = new JFormattedTextField();
			ftfStorageCount.setBounds(483, 283, 128, 22);
		}
		return ftfStorageCount;
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbPrecative() {
		if (cbPrecative == null) {
			cbPrecative = new JCheckBox();
			cbPrecative.setBounds(new java.awt.Rectangle(512, 224, 98, 18));
			cbPrecative.setText("是否委外");
		}
		return cbPrecative;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnVersion() {
		if (btnVersion == null) {
			btnVersion = new JButton();
			btnVersion.setBounds(new java.awt.Rectangle(585, 162, 23, 23));
			btnVersion.setText("...");
			btnVersion.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					MaterialBomVersion version = (MaterialBomVersion) CommonQuery
							.getInstance()
							.getVersionFromBgBom(tfptNo.getText());
					tfptVersion.setText(version == null ? null : String
							.valueOf(version.getVersion()));

				}
			});
		}
		return btnVersion;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtName() {
		if (tfPtName == null) {
			tfPtName = new JTextField();
			tfPtName.setBounds(new java.awt.Rectangle(480, 41, 128, 24));
		}
		return tfPtName;
	}

	public JTextField getTfCustomsNo() {
		if (tfCustomsNo == null) {
			tfCustomsNo = new JTextField();
			tfCustomsNo.setBounds(new Rectangle(273, 282, 121, 24));
		}
		return tfCustomsNo;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtSpec() {
		if (tfPtSpec == null) {
			tfPtSpec = new JTextField();
			tfPtSpec.setBounds(new java.awt.Rectangle(480, 70, 128, 24));
		}
		return tfPtSpec;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setBounds(new Rectangle(232, 386, 74, 24));
			btnPrevious.setText("上笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkNull()) {
						return;
					}
					if (checkMultiple()) {
						return;
					}
					fillData(materiel);
					materiel = (Materiel) materialManageAction.saveMateriel(
							new Request(CommonVars.getCurrUser(), true),
							materiel);
					tableModel.updateRow(materiel);
					if (!tableModel.previousRow()) {
						btnPrevious.setEnabled(false);
						btnNext.setEnabled(true);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					materiel = (Materiel) tableModel.getCurrentRow();
					showData();
					// dataState = DataState.EDIT ;
					setState();
				}
			});
		}
		return btnPrevious;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setBounds(new Rectangle(330, 386, 74, 24));
			btnNext.setText("下笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkNull()) {
						return;
					}
					if (checkMultiple()) {
						return;
					}
					fillData(materiel);
					materiel = (Materiel) materialManageAction.saveMateriel(
							new Request(CommonVars.getCurrUser(), true),
							materiel);
					tableModel.updateRow(materiel);
					if (!tableModel.nextRow()) {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(false);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					materiel = (Materiel) tableModel.getCurrentRow();
					showData();
					// dataState = DataState.EDIT ;
					setState();
				}
			});
		}
		return btnNext;
	}
} // @jve:decl-