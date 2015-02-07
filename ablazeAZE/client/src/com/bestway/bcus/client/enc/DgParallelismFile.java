/*
 * Created on 2004-7-22
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ImportApplyCustomsProperty;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Rectangle;

/**
 * @author fhz
 * 
 */
public class DgParallelismFile extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JComboBox cbbImpExpType = null;// 表头－进出口类型

	private JComboBox cbbBillListNo = null;// 表头－清单编号

	private JComboBox cbbUnifiedCode = null;// 表头－清单统一编号

	private JComboBox cbbListDeclareDate = null;// 表头－清单申报日期

	private JComboBox cbbTradeMode = null;// 表头－监管方式

	private JComboBox cbbMemo = null;// 表头－备注

	private JComboBox cbbBeforeSerialNo = null;// 归并前－

	private JComboBox cbbAfBeforeEmsSerialNo = null;// 归并前－

	private JComboBox cbbBeforeMaterielPtNo = null;// 归并前－商品货号

	private JComboBox cbbBeforeMaterielFactoryName = null;// 归并前－

	private JComboBox cbbBeforeMaterielFactorySpec = null;// 归并前－

	private JComboBox cbbAfterEmsSerialNo = null;// 归并后－帐册序号

	private JComboBox cbbAfterComplexCode = null;// 归并后－

	private JComboBox cbbAfterComplexName = null;// 归并后－

	private int fileColumnCount = 0;

	private Hashtable selectedValues = null;  //  @jve:decl-index=0:

	private JButton btnOK = null; // 

	private JButton btnCancel = null;

	private JButton btnRestoreInitValues = null;

	private JLabel jLabel14 = null;

	private JComboBox cbbDeclareCustom = null;// 表头－申报地海关

	private JLabel jLabel15 = null;

	private JComboBox cbbTransportMode = null;// 表头－运输方式

	private JLabel jLabel16 = null;

	private JLabel jLabel17 = null;

	private JComboBox cbbBeforeCustomsNo = null;// 归并前－对应报关单商品号

	private JComboBox cbbAfBeforeComplexCode = null;

	private JButton btEditPattern = null;

	private JButton btSavePattern = null;

	private EncAction encAction = null;

	private JLabel jLabel18 = null;

	private JLabel jLabel19 = null;

	private JLabel jLabel20 = null;

	private JLabel jLabel21 = null;

	private JComboBox cbbDeclareFirm = null;// 表头－申报单位

	private JComboBox cbbEmsNo = null;// 表头－电子帐册编号

	private JComboBox cbbMaterialNum = null;// 表头－商品项数

	private JComboBox cbbEntrancePort = null;// 表头－进/出口岸

	private JLabel jLabel111 = null;

	private JComboBox cbbAfterComplexSpec = null;

	private JLabel jLabel101 = null;

	private JComboBox cbbBeforeCurrency = null;// 归并前－币别

	private JLabel jLabel1011 = null;

	private JLabel jLabel1012 = null;

	private JLabel jLabel1013 = null;

	private JLabel jLabel1014 = null;

	private JLabel jLabel1015 = null;

	private JLabel jLabel1016 = null;

	private JLabel jLabel1017 = null;

	private JLabel jLabel1018 = null;

	private JLabel jLabel1019 = null;

	private JLabel jLabel10110 = null;

	private JLabel jLabel10111 = null;

	private JLabel jLabel10112 = null;

	private JLabel jLabel10113 = null;

	private JLabel jLabel10114 = null;

	private JLabel jLabel10115 = null;

	private JLabel jLabel10116 = null;

	private JComboBox cbbAfBeforeUnit = null;

	private JComboBox cbbAfBeforeLegalUnit = null;

	private JComboBox cbbAfBeforeSecondLegalUnit = null;

	private JComboBox cbbBeforeDeclaredAmount = null;// 归并前－申报数量

	private JComboBox cbbBeforeLegalAmount = null;// 归并前－法定数量

	private JComboBox cbbBeforeSecondLegalAmount = null;// 归并前－第二法定数量

	private JComboBox cbbBeforeDeclaredPrice = null;// 归并前－企业申报单价

	private JComboBox cbbBeforeSalesCountry = null;// 归并前－产销国(地区)

	private JComboBox cbbBeforeLevyModel = null;// 归并前－征免方式

	private JComboBox cbbBeforeUsess = null;// 归并前－用途

	private JComboBox cbbBeforeVersion = null;// 归并前－版本号

	private JComboBox cbbBeforeGrossWeight = null;// 归并前－毛重

	private JComboBox cbbBeforeNetWeight = null;// 归并前－净重

	private JComboBox cbbBeforeMemos = null;// 归并前－备注

	private JComboBox cbbBeforeProjectDept = null;// 归并前－事业部

	private JComboBox cbbBeforeExtendMemo = null;// 归并前－扩展备注

	private JLabel jLabel181 = null;

	private JComboBox cbbListState = null;

	private JButton btnSetParameter = null;

	private ImportApplyCustomsProperty importApplyProperty = null;// 参数设置实体类  //  @jve:decl-index=0:

	private boolean isOk = false;

	private JLabel jLabel22 = null;

	private JComboBox cbbBeforePeice = null;// 归并前－件数

	/**
	 * @return Returns the selectedValues.
	 */
	public Hashtable getSelectedValues() {
		return selectedValues;
	}

	/**
	 * @param selectedValues
	 *            The selectedValues to set.
	 */
	public void setSelectedValues(Hashtable selectedValues) {
		this.selectedValues = selectedValues;
	}

	/**
	 * @return Returns the fileColumnCount.
	 */
	public int getFileColumnCount() {
		return fileColumnCount;
	}

	/**
	 * @param fileColumnCount
	 *            The fileColumnCount to set.
	 */
	public void setFileColumnCount(int fileColumnCount) {
		this.fileColumnCount = fileColumnCount;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgParallelismFile() {
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
		this.setTitle("设定导入资料与文件资料的列对应关系");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setSize(804, 599);

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				btSavePattern.setEnabled(false);
				setState(false);
				initApplyProperty();
				fillAllComboBox();
				if (importApplyProperty != null) {
					initValues(importApplyProperty);
				} else {
					restoreInitValues();
				}

			}
		});
	}

	/**
	 * 初始化importApplyProperty
	 */
	private void initApplyProperty() {
		List list = encAction.findImportApplyToCustomsBillProperty(new Request(
				CommonVars.getCurrUser()));
		System.out.println("list.size()-----------------------------------"
				+ list.size());
		if (list.isEmpty()) {
			importApplyProperty = new ImportApplyCustomsProperty();
		} else {
			importApplyProperty = (ImportApplyCustomsProperty) list.get(0);
		}
	}

	private Integer getInt(Integer i) {
		if (i != null) {
			return i + 1;
		}
		return i;
	}

	/**
	 * 还原为上次保存的列对应
	 * 
	 * @param importApplyProperty
	 */
	private void initValues(ImportApplyCustomsProperty importApplyProperty) {
		// ----------表头
		setComboBoxInitValue(this.cbbImpExpType, importApplyProperty
				.getCbbImpExpType());// 表头－进出口类型
		setComboBoxInitValue(this.cbbBillListNo, importApplyProperty
				.getCbbBillListNo());// 表头－清单编号
		setComboBoxInitValue(this.cbbUnifiedCode, importApplyProperty
				.getCbbUnifiedCode());// 表头－清单统一编号
		setComboBoxInitValue(this.cbbListDeclareDate, importApplyProperty
				.getCbbListDeclareDate());// 表头－清单申报日期
		setComboBoxInitValue(this.cbbDeclareCustom, importApplyProperty
				.getCbbDeclareCustom());// 表头－申报地海关
		setComboBoxInitValue(this.cbbTransportMode, importApplyProperty
				.getCbbTransportMode());// 表头－运输方式
		setComboBoxInitValue(this.cbbDeclareFirm, importApplyProperty
				.getCbbDeclareCompany());// 表头－申报单位
		setComboBoxInitValue(this.cbbEmsNo, importApplyProperty.getCbbEmsNo());// 表头－电子帐册编号
		setComboBoxInitValue(this.cbbMaterialNum, importApplyProperty
				.getCbbMaterialNum());// 表头－商品项数
		setComboBoxInitValue(this.cbbEntrancePort, importApplyProperty
				.getCbbEntrancePort());// 表头－进出口岸
		setComboBoxInitValue(this.cbbTradeMode, importApplyProperty
				.getCbbTradeMode());// 表头－监管方式
		setComboBoxInitValue(this.cbbMemo, importApplyProperty.getCbbMemo());// 表头－备注
		setComboBoxInitValue(this.cbbListState, importApplyProperty
				.getCbbListState());// 表头－清单状态

		// ----------归并后
		setComboBoxInitValue(this.cbbAfterEmsSerialNo, importApplyProperty
				.getCbbAfterEmsSerialNo());// 归并后－帐册序号

		// ----------归并前
		setComboBoxInitValue(this.cbbBeforeMaterielPtNo, importApplyProperty
				.getCbbBeforeMaterielPtNo());// 归并前－商品货号
		setComboBoxInitValue(this.cbbBeforeCustomsNo, importApplyProperty
				.getCbbBeforeCustomsNo());// 归并前－对应报关单商品号
		setComboBoxInitValue(this.cbbBeforeCurrency, importApplyProperty
				.getCbbBeforeCurrency());// 归并前－币别
		setComboBoxInitValue(this.cbbBeforeDeclaredAmount, importApplyProperty
				.getCbbBeforeDeclaredAmount());// 归并前－申报数量
		setComboBoxInitValue(this.cbbBeforeLegalAmount, importApplyProperty
				.getCbbBeforeLegalAmount());// 归并前－法定数量
		setComboBoxInitValue(this.cbbBeforeSecondLegalAmount,
				importApplyProperty.getCbbBeforeSecondLegalAmount());// 归并前－第二法定数量
		setComboBoxInitValue(this.cbbBeforeDeclaredPrice, importApplyProperty
				.getCbbBeforeDeclaredPrice());// 归并前－企业申报单价
		setComboBoxInitValue(this.cbbBeforeSalesCountry, importApplyProperty
				.getCbbBeforeSalesCountry());// 归并前－产销国(地区)
		setComboBoxInitValue(this.cbbBeforeLevyModel, importApplyProperty
				.getCbbBeforeLevyModel());// 归并前－征免方式
		setComboBoxInitValue(this.cbbBeforeUsess, importApplyProperty
				.getCbbBeforeUsess());// 归并前－用途
		setComboBoxInitValue(this.cbbBeforeVersion, importApplyProperty
				.getCbbBeforeVersion());// 归并前－版本号
		setComboBoxInitValue(this.cbbBeforeGrossWeight, importApplyProperty
				.getCbbBeforeGrossWeight());// 归并前－毛重
		setComboBoxInitValue(this.cbbBeforeNetWeight, importApplyProperty
				.getCbbBeforeNetWeight());// 归并前－净重
		setComboBoxInitValue(this.cbbBeforePeice, importApplyProperty.getCbbBeforePeice());// 归并前－件数
		setComboBoxInitValue(this.cbbBeforeMemos, importApplyProperty
				.getCbbBeforeMemos());// 归并前－备注
		setComboBoxInitValue(this.cbbBeforeProjectDept, importApplyProperty
				.getCbbBeforeProjectDept());// 归并前－事业部
		setComboBoxInitValue(this.cbbBeforeExtendMemo, importApplyProperty
				.getCbbBeforeExtendMemo());// 归并前－扩展备注
	}

	/**
	 * 设置可否选择状态
	 * 
	 * @param isedit
	 */
	private void setState(boolean isedit) {
		// ----------表头
		cbbImpExpType.setEnabled(isedit);// 表头－进出口类型
		cbbBillListNo.setEnabled(isedit);// 表头－清单编号
		cbbUnifiedCode.setEnabled(isedit);// 表头－清单统一编号
		cbbListDeclareDate.setEnabled(isedit);// 表头－清单申报日期
		cbbDeclareCustom.setEnabled(isedit);// 表头－申报地海关
		cbbTransportMode.setEnabled(isedit);// 表头－运输方式
		cbbDeclareFirm.setEnabled(isedit);// 表头－申报单位
		cbbEmsNo.setEnabled(isedit);// 表头－电子帐册编号
		cbbMaterialNum.setEnabled(isedit);// 表头－商品项数
		cbbEntrancePort.setEnabled(isedit);// 表头－进出口岸
		cbbTradeMode.setEnabled(isedit);// 表头－监管方式
		cbbMemo.setEnabled(isedit);// 表头－备注
		cbbListState.setEnabled(isedit);// 表头－清单状态

		// ----------归并后
		cbbAfterEmsSerialNo.setEnabled(isedit);// 归并后－帐册序号

		// ----------归并前
		cbbBeforeMaterielPtNo.setEnabled(isedit);// 归并前－商品货号
		cbbBeforeCustomsNo.setEnabled(isedit);// 归并前－对应报关单商品号
		cbbBeforeCurrency.setEnabled(isedit);// 归并前－币别
		cbbBeforeDeclaredAmount.setEnabled(isedit);// 归并前－申报数量
		cbbBeforeLegalAmount.setEnabled(isedit);// 归并前－法定数量
		cbbBeforeSecondLegalAmount.setEnabled(isedit);// 归并前－第二法定数量
		cbbBeforeDeclaredPrice.setEnabled(isedit);// 归并前－企业申报单价
		cbbBeforeSalesCountry.setEnabled(isedit);// 归并前－产销国(地区)
		cbbBeforeLevyModel.setEnabled(isedit);// 归并前－征免方式
		cbbBeforeUsess.setEnabled(isedit);// 归并前－用途
		cbbBeforeVersion.setEnabled(isedit);// 归并前－版本号
		cbbBeforeGrossWeight.setEnabled(isedit);// 归并前－毛重
		cbbBeforeNetWeight.setEnabled(isedit);// 归并前－净重
		cbbBeforePeice.setEnabled(isedit);// 归并前－件数
		cbbBeforeMemos.setEnabled(isedit);// 归并前－备注
		cbbBeforeProjectDept.setEnabled(isedit);// 归并前－事业部
		cbbBeforeExtendMemo.setEnabled(isedit);// 归并前－扩展备注
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
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.add(getBtnRestoreInitValues(), null);
			jContentPane.add(getBtSavePattern(), null);
			jContentPane.add(getBtEditPattern(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getBtnSetParameter(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel181 = new JLabel();
			jLabel181.setBounds(new Rectangle(491, 20, 117, 21));
			jLabel181.setText("清单状态");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(255, 96, 117, 21));
			jLabel21.setText("进/出口岸");
			jLabel20 = new JLabel();
			jLabel20.setBounds(new Rectangle(255, 71, 117, 21));
			jLabel20.setText("商品项数");
			jLabel19 = new JLabel();
			jLabel19.setBounds(new Rectangle(255, 45, 117, 21));
			jLabel19.setText("电子帐册编号");
			jLabel19.setForeground(java.awt.Color.blue);
			jLabel18 = new JLabel();
			jLabel18.setBounds(new Rectangle(255, 20, 117, 21));
			jLabel18.setText("申报单位");
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(17, 147, 117, 21));
			jLabel15.setText("运输方式");
			jLabel14 = new JLabel();
			jLabel14.setBounds(new Rectangle(17, 121, 117, 21));
			jLabel14.setText("申报地海关");
			jPanel = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel5 = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(18, 6, 739, 185);
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"单头资料",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel.setText("进出口类型");
			jLabel.setBounds(17, 20, 117, 21);
			jLabel.setForeground(java.awt.Color.blue);
			jLabel1.setBounds(17, 45, 117, 21);
			jLabel1.setText("清单编号");
			jLabel2.setBounds(17, 71, 117, 21);
			jLabel2.setText("清单统一编号");
			jLabel1.setForeground(java.awt.Color.blue);
			jLabel3.setBounds(17, 96, 117, 21);
			jLabel3.setText("清单申报日期");
			jLabel4.setBounds(255, 121, 117, 21);
			jLabel4.setText("监管方式");
			jLabel5.setBounds(255, 147, 117, 21);
			jLabel5.setText("备注");
			jPanel.add(jLabel, null);
			jPanel.add(getCbbImpExpType(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(getCbbBillListNo(), null);
			jPanel.add(getCbbUnifiedCode(), null);
			jPanel.add(getCbbListDeclareDate(), null);
			jPanel.add(getCbbTradeMode(), null);
			jPanel.add(getCbbMemo(), null);
			jPanel.add(jLabel14, null);
			jPanel.add(getCbbDeclareCustom(), null);
			jPanel.add(jLabel15, null);
			jPanel.add(getCbbTransportMode(), null);
			jPanel.add(jLabel18, null);
			jPanel.add(jLabel19, null);
			jPanel.add(jLabel20, null);
			jPanel.add(jLabel21, null);
			jPanel.add(getCbbDeclareFirm(), null);
			jPanel.add(getCbbEmsNo(), null);
			jPanel.add(getCbbMaterialNum(), null);
			jPanel.add(getCbbEntrancePort(), null);
			jPanel.add(jLabel181, null);
			jPanel.add(getCbbListState(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel22 = new JLabel();
			jLabel22.setText("件数");
			jLabel22.setBounds(new Rectangle(487, 43, 117, 21));
			jLabel10116 = new JLabel();
			jLabel10116.setBounds(new Rectangle(487, 124, 117, 21));
			jLabel10116.setText("扩展备注");
			jLabel10115 = new JLabel();
			jLabel10115.setBounds(new Rectangle(487, 100, 117, 21));
			jLabel10115.setText("事业部");
			jLabel10114 = new JLabel();
			jLabel10114.setBounds(new Rectangle(487, 73, 117, 21));
			jLabel10114.setText("备注");
			jLabel10113 = new JLabel();
			jLabel10113.setBounds(new Rectangle(487, 17, 117, 21));
			jLabel10113.setText("净重");
			jLabel10112 = new JLabel();
			jLabel10112.setBounds(new Rectangle(255, 140, 117, 21));
			jLabel10112.setText("毛重");
			jLabel10111 = new JLabel();
			jLabel10111.setBounds(new Rectangle(255, 117, 117, 21));
			jLabel10111.setText("版本号");
			jLabel10110 = new JLabel();
			jLabel10110.setBounds(new Rectangle(255, 93, 117, 21));
			jLabel10110.setText("用途");
			jLabel1019 = new JLabel();
			jLabel1019.setBounds(new Rectangle(255, 69, 117, 21));
			jLabel1019.setText("征免方式");
			jLabel1018 = new JLabel();
			jLabel1018.setBounds(new Rectangle(255, 42, 117, 21));
			jLabel1018.setText("产销国(地区)");
			jLabel1017 = new JLabel();
			jLabel1017.setBounds(new Rectangle(255, 17, 117, 21));
			jLabel1017.setText("企业申报单价");
			jLabel1016 = new JLabel();
			jLabel1016.setBounds(new Rectangle(16, 144, 117, 21));
			jLabel1016.setText("第二法定数量");
			jLabel1015 = new JLabel();
			jLabel1015.setBounds(new Rectangle(16, 119, 117, 21));
			jLabel1015.setText("法定数量");
			jLabel1014 = new JLabel();
			jLabel1014.setBounds(new Rectangle(16, 93, 117, 21));
			jLabel1014.setText("申报数量");
			jLabel1013 = new JLabel();
			jLabel1013.setBounds(new Rectangle(255, 69, 117, 21));
			jLabel1013.setText("第二法定计量单位");
			jLabel1013.setVisible(false);
			jLabel1012 = new JLabel();
			jLabel1012.setBounds(new Rectangle(255, 44, 117, 21));
			jLabel1012.setText("法定计量单位");
			jLabel1012.setVisible(false);
			jLabel1011 = new JLabel();
			jLabel1011.setBounds(new Rectangle(255, 18, 117, 21));
			jLabel1011.setText("计量单位(归并后)");
			jLabel1011.setVisible(false);
			jLabel101 = new JLabel();
			jLabel101.setBounds(new Rectangle(16, 69, 117, 22));
			jLabel101.setText("币别");
			jLabel17 = new JLabel();
			jLabel17.setBounds(new Rectangle(16, 118, 117, 21));
			jLabel17.setForeground(java.awt.Color.red);
			jLabel17.setText("商品编码");
			jLabel17.setVisible(false);
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(16, 44, 117, 21));
			jLabel16.setText("对应报关单商品号");
			jPanel1 = new JPanel();
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel10 = new JLabel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(19, 275, 739, 183);
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "归并前资料",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel6.setBounds(17, 18, 117, 21);
			jLabel6.setText("商品序号");
			jLabel6.setVisible(false);
			jLabel7.setBounds(16, 44, 117, 21);
			jLabel7.setText("帐册序号");
			jLabel7.setVisible(false);
			jLabel8.setBounds(16, 18, 117, 21);
			jLabel8.setText("商品货号");
			jLabel8.setForeground(java.awt.Color.blue);
			jLabel9.setBounds(16, 92, 117, 21);
			jLabel9.setText("商品名称");
			jLabel9.setVisible(false);
			jLabel10.setBounds(16, 118, 117, 21);
			jLabel10.setText("型号规格");
			jLabel10.setVisible(false);
			// jPanel1.add(jLabel6, null);
			// jPanel1.add(jLabel7, null);
			jPanel1.add(jLabel8, null);
			// jPanel1.add(jLabel9, null);
			// jPanel1.add(jLabel10, null);
			// jPanel1.add(getCbbBeforeSerialNo(), null);
			// jPanel1.add(getCbbAfBeforeEmsSerialNo(), null);
			jPanel1.add(getCbbBeforeMaterielPtNo(), null);
			// jPanel1.add(getCbbBeforeMaterielFactoryName(), null);
			// jPanel1.add(getCbbBeforeMaterielFactorySpec(), null);
			jPanel1.add(jLabel16, null);
			// jPanel1.add(jLabel17, null);
			jPanel1.add(getCbbBeforeCustomsNo(), null);
			// jPanel1.add(getCbbAfBeforeComplexCode(), null);
			jPanel1.add(jLabel101, null);
			jPanel1.add(getCbbBeforeCurrency(), null);
			// jPanel1.add(jLabel1011, null);
			// jPanel1.add(jLabel1012, null);
			// jPanel1.add(jLabel1013, null);
			jPanel1.add(jLabel1014, null);
			jPanel1.add(jLabel1015, null);
			jPanel1.add(jLabel1016, null);
			jPanel1.add(jLabel1017, null);
			jPanel1.add(jLabel1018, null);
			jPanel1.add(jLabel1019, null);
			jPanel1.add(jLabel10110, null);
			jPanel1.add(jLabel10111, null);
			jPanel1.add(jLabel10112, null);
			jPanel1.add(jLabel10113, null);
			jPanel1.add(jLabel10114, null);
			jPanel1.add(jLabel10115, null);
			jPanel1.add(jLabel10116, null);
			// jPanel1.add(getCbbAfBeforeUnit(), null);
			// jPanel1.add(getCbbAfBeforeLegalUnit(), null);
			// jPanel1.add(getCbbAfBeforeSecondLegalUnit(), null);
			jPanel1.add(getCbbBeforeDeclaredAmount(), null);
			jPanel1.add(getCbbBeforeLegalAmount(), null);
			jPanel1.add(getCbbBeforeSecondLegalAmount(), null);
			jPanel1.add(getCbbBeforeDeclaredPrice(), null);
			jPanel1.add(getCbbBeforeSalesCountry(), null);
			jPanel1.add(getCbbBeforeLevyModel(), null);
			jPanel1.add(getCbbBeforeUsess(), null);
			jPanel1.add(getCbbBeforeVersion(), null);
			jPanel1.add(getCbbBeforeGrossWeight(), null);
			jPanel1.add(getCbbBeforeNetWeight(), null);
			jPanel1.add(getCbbBeforeMemos(), null);
			jPanel1.add(getCbbBeforeProjectDept(), null);
			jPanel1.add(getCbbBeforeExtendMemo(), null);
			jPanel1.add(jLabel22, null);
			jPanel1.add(getCbbBeforePeice(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel111 = new JLabel();
			jLabel111.setBounds(new Rectangle(316, 45, 152, 21));
			jLabel111.setText("商品规格");
			jLabel111.setVisible(false);
			jPanel2 = new JPanel();
			javax.swing.JLabel jLabel11 = new JLabel();
			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel13 = new JLabel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(18, 201, 739, 60);
			jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "归并后资料",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel11.setBounds(17, 19, 152, 21);
			jLabel11.setText("帐册序号");
			// jLabel11.setForeground(java.awt.Color.blue);
			jLabel12.setBounds(17, 45, 152, 21);
			jLabel12.setText("商品编码");
			jLabel12.setVisible(false);
			jLabel13.setBounds(316, 19, 152, 21);
			jLabel13.setText("商品名称");
			jLabel13.setVisible(false);
			jPanel2.add(jLabel11, null);
			jPanel2.add(jLabel12, null);
			jPanel2.add(jLabel13, null);
			jPanel2.add(getCbbAfterEmsSerialNo(), null);
			jPanel2.add(getCbbAfterComplexCode(), null);
			jPanel2.add(getCbbAfterComplexName(), null);
			jPanel2.add(jLabel111, null);
			jPanel2.add(getCbbAfterComplexSpec(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes cbbImpExpType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(135, 20, 106, 21);
		}
		return cbbImpExpType;
	}

	/**
	 * This method initializes cbbBeforeMaterielCode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBillListNo() {
		if (cbbBillListNo == null) {
			cbbBillListNo = new JComboBox();
			cbbBillListNo.setBounds(135, 45, 106, 21);
		}
		return cbbBillListNo;
	}

	/**
	 * This method initializes cbbBeforeTenComplexCode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUnifiedCode() {
		if (cbbUnifiedCode == null) {
			cbbUnifiedCode = new JComboBox();
			cbbUnifiedCode.setBounds(135, 71, 106, 21);
		}
		return cbbUnifiedCode;
	}

	/**
	 * This method initializes cbbBeforeMaterielNameSpec
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbListDeclareDate() {
		if (cbbListDeclareDate == null) {
			cbbListDeclareDate = new JComboBox();
			cbbListDeclareDate.setBounds(135, 96, 106, 21);

		}
		return cbbListDeclareDate;
	}

	/**
	 * This method initializes cbbBeforeLegalUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTradeMode() {
		if (cbbTradeMode == null) {
			cbbTradeMode = new JComboBox();
			cbbTradeMode.setBounds(374, 121, 106, 21);
		}
		return cbbTradeMode;
	}

	/**
	 * This method initializes cbbBeforeEnterpriseUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMemo() {
		if (cbbMemo == null) {
			cbbMemo = new JComboBox();
			cbbMemo.setBounds(374, 147, 106, 21);
		}
		return cbbMemo;
	}

	/**
	 * This method initializes cbbAfterTenMemoNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeSerialNo() {
		if (cbbBeforeSerialNo == null) {
			cbbBeforeSerialNo = new JComboBox();
			cbbBeforeSerialNo.setBounds(135, 18, 106, 21);
			cbbBeforeSerialNo.setEditable(false);
			cbbBeforeSerialNo.setVisible(false);
		}
		return cbbBeforeSerialNo;
	}

	/**
	 * This method initializes cbbAfterTenComplexCode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAfBeforeEmsSerialNo() {
		if (cbbAfBeforeEmsSerialNo == null) {
			cbbAfBeforeEmsSerialNo = new JComboBox();
			cbbAfBeforeEmsSerialNo.setBounds(135, 44, 106, 21);
			cbbAfBeforeEmsSerialNo.setEditable(false);
			cbbAfBeforeEmsSerialNo.setVisible(false);
		}
		return cbbAfBeforeEmsSerialNo;
	}

	/**
	 * This method initializes cbbAfterComplexNameSpec
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeMaterielPtNo() {
		if (cbbBeforeMaterielPtNo == null) {
			cbbBeforeMaterielPtNo = new JComboBox();
			cbbBeforeMaterielPtNo.setBounds(135, 18, 106, 21);
		}
		return cbbBeforeMaterielPtNo;
	}

	/**
	 * This method initializes cbbAfterLegalUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeMaterielFactoryName() {
		if (cbbBeforeMaterielFactoryName == null) {
			cbbBeforeMaterielFactoryName = new JComboBox();
			cbbBeforeMaterielFactoryName.setBounds(135, 92, 106, 21);
			cbbBeforeMaterielFactoryName.setEditable(false);
			cbbBeforeMaterielFactoryName.setVisible(false);
		}
		return cbbBeforeMaterielFactoryName;
	}

	/**
	 * This method initializes cbbAfterMemoUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeMaterielFactorySpec() {
		if (cbbBeforeMaterielFactorySpec == null) {
			cbbBeforeMaterielFactorySpec = new JComboBox();
			cbbBeforeMaterielFactorySpec.setBounds(135, 118, 106, 21);
			cbbBeforeMaterielFactorySpec.setEditable(false);
			cbbBeforeMaterielFactorySpec.setVisible(false);
		}
		return cbbBeforeMaterielFactorySpec;
	}

	/**
	 * This method initializes cbbFourNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAfterEmsSerialNo() {
		if (cbbAfterEmsSerialNo == null) {
			cbbAfterEmsSerialNo = new JComboBox();
			cbbAfterEmsSerialNo.setBounds(170, 18, 130, 21);
		}
		return cbbAfterEmsSerialNo;
	}

	/**
	 * This method initializes cbbFourComplexName
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAfterComplexCode() {
		if (cbbAfterComplexCode == null) {
			cbbAfterComplexCode = new JComboBox();
			cbbAfterComplexCode.setBounds(170, 45, 130, 21);
			cbbAfterComplexCode.setEditable(false);
			cbbAfterComplexCode.setVisible(false);
		}
		return cbbAfterComplexCode;
	}

	/**
	 * This method initializes cbbFourComplexCode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAfterComplexName() {
		if (cbbAfterComplexName == null) {
			cbbAfterComplexName = new JComboBox();
			cbbAfterComplexName.setBounds(469, 18, 130, 21);
			cbbAfterComplexName.setEditable(false);
			cbbAfterComplexName.setVisible(false);
		}
		return cbbAfterComplexName;
	}

	private void fillComboBox(JComboBox comboBox, int selectedIndex) {
		comboBox.addItem(new ItemProperty(String.valueOf(0), "  "));
		for (int i = 0; i < fileColumnCount; i++) {
			comboBox.addItem(new ItemProperty(String.valueOf(i + 1), "第"
					+ (i + 1) + "列"));
		}
	}

	private void fillAllComboBox() {
		fillComboBox(this.cbbImpExpType, 0);// 表头－进出口类型
		fillComboBox(this.cbbBillListNo, 0);// 表头－清单编号
		fillComboBox(this.cbbUnifiedCode, 0);// 表头－清单统一编号
		fillComboBox(this.cbbListDeclareDate, 0);// 表头－清单申报日期
		fillComboBox(this.cbbDeclareCustom, 0);// 表头－申报地海关
		fillComboBox(this.cbbTransportMode, 0);// 表头－运输方式
		fillComboBox(this.cbbDeclareFirm, 0);// 表头－申报单位
		fillComboBox(this.cbbEmsNo, 0);// 表头－电子帐册编号
		fillComboBox(this.cbbMaterialNum, 0);// 表头－商品项数
		fillComboBox(this.cbbEntrancePort, 0);// 表头－进出口岸
		fillComboBox(this.cbbTradeMode, 0);// 表头－监管方式
		fillComboBox(this.cbbMemo, 0);// 表头－备注
		fillComboBox(this.cbbListState, 0);// 表头－清单状态

		// ----------归并后
		fillComboBox(this.cbbAfterEmsSerialNo, 0);// 归并后－帐册序号

		// ----------归并前
		fillComboBox(this.cbbBeforeMaterielPtNo, 0);// 归并前－商品货号
		fillComboBox(this.cbbBeforeCustomsNo, 0);// 归并前－对应报关单商品号
		fillComboBox(this.cbbBeforeCurrency, 0);// 归并前－币别
		fillComboBox(this.cbbBeforeDeclaredAmount, 0);// 归并前－申报数量
		fillComboBox(this.cbbBeforeLegalAmount, 0);// 归并前－法定数量
		fillComboBox(this.cbbBeforeSecondLegalAmount, 0);// 归并前－第二法定数量
		fillComboBox(this.cbbBeforeDeclaredPrice, 0);// 归并前－企业申报单价
		fillComboBox(this.cbbBeforeSalesCountry, 0);// 归并前－产销国(地区)
		fillComboBox(this.cbbBeforeLevyModel, 0);// 归并前－征免方式
		fillComboBox(this.cbbBeforeUsess, 0);// 归并前－用途
		fillComboBox(this.cbbBeforeVersion, 0);// 归并前－版本号
		fillComboBox(this.cbbBeforeGrossWeight, 0);// 归并前－毛重
		fillComboBox(this.cbbBeforeNetWeight, 0);// 归并前－净重
		fillComboBox(this.cbbBeforePeice, 0);// 归并前－件数
		fillComboBox(this.cbbBeforeMemos, 0);// 归并前－备注
		fillComboBox(this.cbbBeforeProjectDept, 0);// 归并前－事业部
		fillComboBox(this.cbbBeforeExtendMemo, 0);// 归并前－扩展备注

	}

	// 确定
	private void getSelectedValue() {
		if (selectedValues == null) {
			selectedValues = new Hashtable();
		}

		// ----------表头
		selectedValues.put("cbbImpExpType", cbbImpExpType.getSelectedIndex());// 表头－进出口类型
		selectedValues.put("cbbBillListNo", cbbBillListNo.getSelectedIndex());// 表头－清单编号
		selectedValues.put("cbbUnifiedCode", cbbUnifiedCode.getSelectedIndex());// 表头－清单统一编号
		selectedValues.put("cbbMaterielProductFlag", cbbListDeclareDate
				.getSelectedIndex());// 表头－清单申报日期
		selectedValues.put("cbbDeclareCustom", cbbDeclareCustom
				.getSelectedIndex());// 表头－申报地海关
		selectedValues.put("cbbTransportMode", cbbTransportMode
				.getSelectedIndex());// 表头－运输方式
		selectedValues.put("cbbDeclareFirm", cbbDeclareFirm.getSelectedIndex());// 表头－申报单位
		selectedValues.put("cbbEmsNo", cbbEmsNo.getSelectedIndex());// 表头－电子帐册编号
		selectedValues.put("cbbMaterialNum", cbbMaterialNum.getSelectedIndex());// 表头－商品项数
		selectedValues.put("cbbEntrancePort", cbbEntrancePort
				.getSelectedIndex());// 表头－进出口岸
		selectedValues.put("cbbTradeMode", cbbTradeMode.getSelectedIndex());// 表头－监管方式
		selectedValues.put("cbbMemo", cbbMemo.getSelectedIndex());// 表头－备注
		selectedValues.put("cbbListState", cbbListState.getSelectedIndex());// 表头－清单状态

		// ----------归并后
		selectedValues.put("cbbAfterEmsSerialNo", cbbAfterEmsSerialNo
				.getSelectedIndex());// 归并后－帐册序号

		// ----------归并前
		selectedValues.put("cbbBeforeMaterielPtNo", cbbBeforeMaterielPtNo
				.getSelectedIndex());// 归并前－商品货号
		selectedValues.put("cbbBeforeCustomsNo", cbbBeforeCustomsNo
				.getSelectedIndex());// 归并前－对应报关单商品号
		selectedValues.put("cbbBeforeCurrency", cbbBeforeCurrency
				.getSelectedIndex());// 归并前－币别
		selectedValues.put("cbbBeforeDeclaredAmount", cbbBeforeDeclaredAmount
				.getSelectedIndex());// 归并前－申报数量
		selectedValues.put("cbbBeforeLegalAmount", cbbBeforeLegalAmount
				.getSelectedIndex());// 归并前－法定数量
		selectedValues.put("cbbBeforeSecondLegalAmount",
				cbbBeforeSecondLegalAmount.getSelectedIndex());// 归并前－第二法定数量
		selectedValues.put("cbbBeforeDeclaredPrice", cbbBeforeDeclaredPrice
				.getSelectedIndex());// 归并前－企业申报单价
		selectedValues.put("cbbBeforeSalesCountry", cbbBeforeSalesCountry
				.getSelectedIndex());// 归并前－产销国(地区)
		selectedValues.put("cbbBeforeLevyModel", cbbBeforeLevyModel
				.getSelectedIndex());// 归并前－征免方式
		selectedValues.put("cbbBeforeUsess", cbbBeforeUsess.getSelectedIndex());// 归并前－用途
		selectedValues.put("cbbBeforeVersion", cbbBeforeVersion
				.getSelectedIndex());// 归并前－版本号
		selectedValues.put("cbbBeforeGrossWeight", cbbBeforeGrossWeight
				.getSelectedIndex());// 归并前－毛重
		selectedValues.put("cbbBeforeNetWeight", cbbBeforeNetWeight
				.getSelectedIndex());// 归并前－净重
		selectedValues.put("cbbBeforePeice", cbbBeforePeice.getSelectedIndex());//归并前件数
		selectedValues.put("cbbBeforeMemos", cbbBeforeMemos.getSelectedIndex());// 归并前－备注
		selectedValues.put("cbbBeforeProjectDept", cbbBeforeProjectDept
				.getSelectedIndex());// 归并前－事业部
		selectedValues.put("cbbBeforeExtendMemo", cbbBeforeExtendMemo
				.getSelectedIndex());// 归并前－扩展备注

	}

	private void setComboBoxInitValue(JComboBox comboBox, int selectedIndex) {
		if (selectedIndex > comboBox.getItemCount() - 1) {
			comboBox.setSelectedIndex(0);
		} else {
			comboBox.setSelectedIndex(selectedIndex);
		}
	}

	/**
	 * 恢复初值是把所有的选择为0
	 */
	private void restoreInitValues() {
		// ----------表头
		setComboBoxInitValue(this.cbbImpExpType, 0);// 表头－进出口类型
		setComboBoxInitValue(this.cbbBillListNo, 0);// 表头－清单编号
		setComboBoxInitValue(this.cbbUnifiedCode, 0);// 表头－清单统一编号
		setComboBoxInitValue(this.cbbListDeclareDate, 0);// 表头－清单申报日期
		setComboBoxInitValue(this.cbbDeclareCustom, 0);// 表头－申报地海关
		setComboBoxInitValue(this.cbbTransportMode, 0);// 表头－运输方式
		setComboBoxInitValue(this.cbbDeclareFirm, 0);// 表头－申报单位
		setComboBoxInitValue(this.cbbEmsNo, 0);// 表头－电子帐册编号
		setComboBoxInitValue(this.cbbMaterialNum, 0);// 表头－商品项数
		setComboBoxInitValue(this.cbbEntrancePort, 0);// 表头－进出口岸
		setComboBoxInitValue(this.cbbTradeMode, 0);// 表头－监管方式
		setComboBoxInitValue(this.cbbMemo, 0);// 表头－备注
		setComboBoxInitValue(this.cbbListState, 0);// 表头－清单状态

		// ----------归并后
		setComboBoxInitValue(this.cbbAfterEmsSerialNo, 0);// 归并后－帐册序号

		// ----------归并前
		setComboBoxInitValue(this.cbbBeforeMaterielPtNo, 0);// 归并前－商品货号
		setComboBoxInitValue(this.cbbBeforeCustomsNo, 0);// 归并前－对应报关单商品号
		setComboBoxInitValue(this.cbbBeforeCurrency, 0);// 归并前－币别
		setComboBoxInitValue(this.cbbBeforeDeclaredAmount, 0);// 归并前－申报数量
		setComboBoxInitValue(this.cbbBeforeLegalAmount, 0);// 归并前－法定数量
		setComboBoxInitValue(this.cbbBeforeSecondLegalAmount, 0);// 归并前－第二法定数量
		setComboBoxInitValue(this.cbbBeforeDeclaredPrice, 0);// 归并前－企业申报单价
		setComboBoxInitValue(this.cbbBeforeSalesCountry, 0);// 归并前－产销国(地区)
		setComboBoxInitValue(this.cbbBeforeLevyModel, 0);// 归并前－征免方式
		setComboBoxInitValue(this.cbbBeforeUsess, 0);// 归并前－用途
		setComboBoxInitValue(this.cbbBeforeVersion, 0);// 归并前－版本号
		setComboBoxInitValue(this.cbbBeforeGrossWeight, 0);// 归并前－毛重
		setComboBoxInitValue(this.cbbBeforeNetWeight, 0);// 归并前－净重
		setComboBoxInitValue(this.cbbBeforePeice, 0);// 归并前－件数
		setComboBoxInitValue(this.cbbBeforeMemos, 0);// 归并前－备注
		setComboBoxInitValue(this.cbbBeforeProjectDept, 0);// 归并前－事业部
		setComboBoxInitValue(this.cbbBeforeExtendMemo, 0);// 归并前－扩展备注
	}

	/**
	 * This method initializes btnOK
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// 保存格式
					insertOrder();
					setState(false);
					btEditPattern.setEnabled(true);
					btSavePattern.setEnabled(false);
					// 获取
					getSelectedValue();
					DgParallelismFile.this.dispose();
				}
			});
		}
		return btnOK;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("使用当前格式并退出");
			btnCancel.setBounds(new Rectangle(606, 511, 145, 25));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (btSavePattern.isEnabled() == true) {
						JOptionPane.showMessageDialog(DgParallelismFile.this,
								"请先保存格式!", "提示", JOptionPane.ERROR_MESSAGE);
						return;
					}
					checkErrorDate();
					isOk = true;
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes btnRestoreInitValues
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRestoreInitValues() {
		if (btnRestoreInitValues == null) {
			btnRestoreInitValues = new JButton();
			btnRestoreInitValues.setText("恢复初始值");
			btnRestoreInitValues.setBounds(new Rectangle(186, 511, 94, 25));
			btnRestoreInitValues
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							restoreInitValues();
						}
					});
		}
		return btnRestoreInitValues;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDeclareCustom() {
		if (cbbDeclareCustom == null) {
			cbbDeclareCustom = new JComboBox();
			cbbDeclareCustom.setBounds(new Rectangle(135, 121, 106, 21));
		}
		return cbbDeclareCustom;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTransportMode() {
		if (cbbTransportMode == null) {
			cbbTransportMode = new JComboBox();
			cbbTransportMode.setBounds(new Rectangle(135, 147, 106, 21));
		}
		return cbbTransportMode;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeCustomsNo() {
		if (cbbBeforeCustomsNo == null) {
			cbbBeforeCustomsNo = new JComboBox();
			cbbBeforeCustomsNo.setBounds(new Rectangle(135, 44, 106, 21));
		}
		return cbbBeforeCustomsNo;
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAfBeforeComplexCode() {
		if (cbbAfBeforeComplexCode == null) {
			cbbAfBeforeComplexCode = new JComboBox();
			cbbAfBeforeComplexCode.setBounds(new Rectangle(135, 118, 106, 21));
			cbbAfBeforeComplexCode.setEditable(false);
			cbbAfBeforeComplexCode.setVisible(false);
		}
		return cbbAfBeforeComplexCode;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtEditPattern() {
		if (btEditPattern == null) {
			btEditPattern = new JButton();
			btEditPattern.setText("修改格式");
			btEditPattern.setBounds(new Rectangle(466, 511, 94, 25));
			btEditPattern
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setState(true);
							btEditPattern.setEnabled(false);
							btSavePattern.setEnabled(true);
							isOk = false;
						}
					});
		}
		return btEditPattern;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtSavePattern() {
		if (btSavePattern == null) {
			btSavePattern = new JButton();
			btSavePattern.setText("保存格式");
			btSavePattern.setBounds(new Rectangle(326, 511, 94, 25));
			btSavePattern
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							insertOrder();
							setState(false);
							btEditPattern.setEnabled(true);
							btSavePattern.setEnabled(false);
							checkErrorDate();
							System.out
									.println("importApplyProperty----before------"
											+ importApplyProperty);
							importApplyProperty = encAction
									.saveImportApplyToCustomsBillProperty(
											new Request(CommonVars
													.getCurrUser()),
											importApplyProperty);
							// isOk=true;
							System.out
									.println("importApplyProperty----after------"
											+ importApplyProperty);
						}
					});
		}
		return btSavePattern;
	}

	private void insertOrder() {
		if (importApplyProperty == null) {
			importApplyProperty = new ImportApplyCustomsProperty();
		}

		// ----------表头
		importApplyProperty.setCbbImpExpType(this.cbbImpExpType
				.getSelectedIndex());// 表头－进出口类型
		importApplyProperty.setCbbBillListNo(this.cbbBillListNo
				.getSelectedIndex());// 表头－清单编号
		importApplyProperty.setCbbUnifiedCode(this.cbbUnifiedCode
				.getSelectedIndex());// 表头－清单统一编号
		importApplyProperty.setCbbListDeclareDate(this.cbbListDeclareDate
				.getSelectedIndex());// 表头－清单申报日期
		importApplyProperty.setCbbDeclareCustom(this.cbbDeclareCustom
				.getSelectedIndex());// 表头－申报地海关
		importApplyProperty.setCbbTransportMode(this.cbbTransportMode
				.getSelectedIndex());// 表头－运输方式
		importApplyProperty.setCbbDeclareCompany(this.cbbDeclareFirm
				.getSelectedIndex());// 表头－申报单位
		importApplyProperty.setCbbEmsNo(this.cbbEmsNo.getSelectedIndex());// 表头－电子帐册编号
		importApplyProperty.setCbbMaterialNum(this.cbbMaterialNum
				.getSelectedIndex());// 表头－商品项数
		importApplyProperty.setCbbEntrancePort(this.cbbEntrancePort
				.getSelectedIndex());// 表头－进出口岸
		importApplyProperty.setCbbTradeMode(this.cbbTradeMode
				.getSelectedIndex());// 表头－监管方式
		importApplyProperty.setCbbMemo(this.cbbMemo.getSelectedIndex());// 表头－备注
		importApplyProperty.setCbbListState(this.cbbListState
				.getSelectedIndex());// 表头－清单状态

		// ----------归并后
		importApplyProperty.setCbbAfterEmsSerialNo(this.cbbAfterEmsSerialNo
				.getSelectedIndex());// 归并后－帐册序号

		// ----------归并前
		importApplyProperty.setCbbBeforeMaterielPtNo(this.cbbBeforeMaterielPtNo
				.getSelectedIndex());// 归并前－商品货号
		importApplyProperty.setCbbBeforeCustomsNo(this.cbbBeforeCustomsNo
				.getSelectedIndex());// 归并前－对应报关单商品号
		importApplyProperty.setCbbBeforeCurrency(this.cbbBeforeCurrency
				.getSelectedIndex());// 归并前－币别
		importApplyProperty
				.setCbbBeforeDeclaredAmount(this.cbbBeforeDeclaredAmount
						.getSelectedIndex());// 归并前－申报数量
		importApplyProperty.setCbbBeforeLegalAmount(this.cbbBeforeLegalAmount
				.getSelectedIndex());// 归并前－法定数量
		importApplyProperty
				.setCbbBeforeSecondLegalAmount(this.cbbBeforeSecondLegalAmount
						.getSelectedIndex());// 归并前－第二法定数量
		importApplyProperty
				.setCbbBeforeDeclaredPrice(this.cbbBeforeDeclaredPrice
						.getSelectedIndex());// 归并前－企业申报单价
		importApplyProperty.setCbbBeforeSalesCountry(this.cbbBeforeSalesCountry
				.getSelectedIndex());// 归并前－产销国(地区)
		importApplyProperty.setCbbBeforeLevyModel(this.cbbBeforeLevyModel
				.getSelectedIndex());// 归并前－征免方式
		importApplyProperty.setCbbBeforeUsess(this.cbbBeforeUsess
				.getSelectedIndex());// 归并前－用途
		importApplyProperty.setCbbBeforeVersion(this.cbbBeforeVersion
				.getSelectedIndex());// 归并前－版本号
		importApplyProperty.setCbbBeforeGrossWeight(this.cbbBeforeGrossWeight
				.getSelectedIndex());// 归并前－毛重

		importApplyProperty.setCbbBeforeNetWeight(this.cbbBeforeNetWeight
				.getSelectedIndex());// 归并前－净重
		importApplyProperty.setCbbBeforePeice(this.cbbBeforePeice
				.getSelectedIndex());// 归并前－件数
		importApplyProperty.setCbbBeforeMemos(this.cbbBeforeMemos
				.getSelectedIndex());// 归并前－备注
		importApplyProperty.setCbbBeforeProjectDept(this.cbbBeforeProjectDept
				.getSelectedIndex());// 归并前－事业部
		importApplyProperty.setCbbBeforeExtendMemo(this.cbbBeforeExtendMemo
				.getSelectedIndex());// 归并前－扩展备注

	}

	/**
	 * 检查必须的数据
	 */
	private void checkErrorDate() {
		if (cbbImpExpType.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(this, "请选择单头中的\"进出口类型\"对应列！", "提示",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (cbbBillListNo.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(this, "请选择单头中的\"清单编号\"对应列！", "提示",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (cbbEmsNo.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(this, "请选择单头中的\"电子帐册编号\"对应列！", "提示",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		// if (cbbAfterEmsSerialNo.getSelectedIndex() == 0) {
		// JOptionPane.showMessageDialog(this, "请选择归并后的\"帐册序号\"对应列！", "提示",
		// JOptionPane.ERROR_MESSAGE);
		// return;
		// }
		if (cbbBeforeMaterielPtNo.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(this, "请选择归并前的\"商品货号\"对应列！", "提示",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		HashMap map = new HashMap();
		// ----------表头
		checkRepeatColumn(this.cbbImpExpType, map);// 表头－进出口类型
		checkRepeatColumn(this.cbbBillListNo, map);// 表头－清单编号
		checkRepeatColumn(this.cbbUnifiedCode, map);// 表头－清单统一编号
		checkRepeatColumn(this.cbbListDeclareDate, map);// 表头－清单申报日期
		checkRepeatColumn(this.cbbTransportMode, map);// 表头－申报地海关
		checkRepeatColumn(this.cbbTransportMode, map);// 表头－运输方式
		checkRepeatColumn(this.cbbDeclareFirm, map);// 表头－申报单位
		checkRepeatColumn(this.cbbEmsNo, map);// 表头－电子帐册编号
		checkRepeatColumn(this.cbbMaterialNum, map);// 表头－商品项数
		checkRepeatColumn(this.cbbEntrancePort, map);// 表头－进出口岸
		checkRepeatColumn(this.cbbMemo, map);// 表头－监管方式
		checkRepeatColumn(this.cbbMemo, map);// 表头－备注
		checkRepeatColumn(this.cbbListState, map);// 表头－清单状态

		// ----------归并后
		checkRepeatColumn(this.cbbAfterEmsSerialNo, map);// 归并后－帐册序号

		// ----------归并前
		checkRepeatColumn(this.cbbBeforeMaterielPtNo, map);// 归并前－商品货号
		checkRepeatColumn(this.cbbBeforeCurrency, map);// 归并前－对应报关单商品号
		checkRepeatColumn(this.cbbBeforeCurrency, map);// 归并前－币别
		checkRepeatColumn(this.cbbBeforeLegalAmount, map);// 归并前－申报数量
		checkRepeatColumn(this.cbbBeforeLegalAmount, map);// 归并前－法定数量
		checkRepeatColumn(this.cbbBeforeSecondLegalAmount, map);// 归并前－第二法定数量
		checkRepeatColumn(this.cbbBeforeLevyModel, map);// 归并前－企业申报单价
		checkRepeatColumn(this.cbbBeforeLevyModel, map);// 归并前－产销国(地区)
		checkRepeatColumn(this.cbbBeforeLevyModel, map);// 归并前－征免方式
		checkRepeatColumn(this.cbbBeforeUsess, map);// 归并前－用途
		checkRepeatColumn(this.cbbBeforeVersion, map);// 归并前－版本号
		checkRepeatColumn(this.cbbBeforeGrossWeight, map);// 归并前－毛重
		checkRepeatColumn(this.cbbBeforeNetWeight, map);// 归并前－净重
		checkRepeatColumn(this.cbbBeforePeice, map);// 归并前－件数
		checkRepeatColumn(this.cbbBeforeMemos, map);// 归并前－备注
		checkRepeatColumn(this.cbbBeforeProjectDept, map);// 归并前－事业部
		checkRepeatColumn(this.cbbBeforeExtendMemo, map);// 归并前－扩展备注
	}

	/**
	 * 检查是否有重复的列
	 * 
	 * @param jComboBox
	 */
	private void checkRepeatColumn(JComboBox jComboBox, HashMap map) {
		if (jComboBox.getSelectedIndex() == 0)
			return;
		if (map.get(jComboBox.getSelectedIndex()) != null) {
			JOptionPane.showMessageDialog(this, "存在一列与多个栏位对应，请检查。", "提示",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	/**
	 * This method initializes cbbSerialNo1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDeclareFirm() {
		if (cbbDeclareFirm == null) {
			cbbDeclareFirm = new JComboBox();
			cbbDeclareFirm.setBounds(new Rectangle(374, 20, 106, 21));
		}
		return cbbDeclareFirm;
	}

	/**
	 * This method initializes cbbSerialNo2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmsNo() {
		if (cbbEmsNo == null) {
			cbbEmsNo = new JComboBox();
			cbbEmsNo.setBounds(new Rectangle(374, 45, 106, 21));
		}
		return cbbEmsNo;
	}

	/**
	 * This method initializes cbbSerialNo3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMaterialNum() {
		if (cbbMaterialNum == null) {
			cbbMaterialNum = new JComboBox();
			cbbMaterialNum.setBounds(new Rectangle(374, 71, 106, 21));
		}
		return cbbMaterialNum;
	}

	/**
	 * This method initializes cbbSerialNo4
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEntrancePort() {
		if (cbbEntrancePort == null) {
			cbbEntrancePort = new JComboBox();
			cbbEntrancePort.setBounds(new Rectangle(374, 96, 106, 21));
		}
		return cbbEntrancePort;
	}

	/**
	 * This method initializes cbbFourNo1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAfterComplexSpec() {
		if (cbbAfterComplexSpec == null) {
			cbbAfterComplexSpec = new JComboBox();
			cbbAfterComplexSpec.setBounds(new Rectangle(469, 45, 130, 21));
			cbbAfterComplexSpec.setEditable(false);
			cbbAfterComplexSpec.setVisible(false);
		}
		return cbbAfterComplexSpec;
	}

	/**
	 * This method initializes cbbAfterMemoUnit1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeCurrency() {
		if (cbbBeforeCurrency == null) {
			cbbBeforeCurrency = new JComboBox();
			cbbBeforeCurrency.setBounds(new Rectangle(135, 69, 106, 21));
		}
		return cbbBeforeCurrency;
	}

	/**
	 * This method initializes cbbAfterTenMemoNo1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAfBeforeUnit() {
		if (cbbAfBeforeUnit == null) {
			cbbAfBeforeUnit = new JComboBox();
			cbbAfBeforeUnit.setBounds(new Rectangle(374, 18, 106, 21));
			cbbAfBeforeUnit.setEditable(false);
			cbbAfBeforeUnit.setVisible(false);
		}
		return cbbAfBeforeUnit;
	}

	/**
	 * This method initializes cbbAfterTenMemoNo2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAfBeforeLegalUnit() {
		if (cbbAfBeforeLegalUnit == null) {
			cbbAfBeforeLegalUnit = new JComboBox();
			cbbAfBeforeLegalUnit.setBounds(new Rectangle(374, 44, 106, 21));
			cbbAfBeforeLegalUnit.setEditable(false);
			cbbAfBeforeLegalUnit.setVisible(false);
		}
		return cbbAfBeforeLegalUnit;
	}

	/**
	 * This method initializes cbbAfterTenMemoNo3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAfBeforeSecondLegalUnit() {
		if (cbbAfBeforeSecondLegalUnit == null) {
			cbbAfBeforeSecondLegalUnit = new JComboBox();
			cbbAfBeforeSecondLegalUnit
					.setBounds(new Rectangle(374, 69, 106, 21));
			cbbAfBeforeSecondLegalUnit.setEditable(false);
			cbbAfBeforeSecondLegalUnit.setVisible(false);
		}
		return cbbAfBeforeSecondLegalUnit;
	}

	/**
	 * This method initializes cbbAfterTenMemoNo4
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeDeclaredAmount() {
		if (cbbBeforeDeclaredAmount == null) {
			cbbBeforeDeclaredAmount = new JComboBox();
			cbbBeforeDeclaredAmount.setBounds(new Rectangle(135, 93, 106, 21));
		}
		return cbbBeforeDeclaredAmount;
	}

	/**
	 * This method initializes cbbAfterTenMemoNo5
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeLegalAmount() {
		if (cbbBeforeLegalAmount == null) {
			cbbBeforeLegalAmount = new JComboBox();
			cbbBeforeLegalAmount.setBounds(new Rectangle(135, 119, 106, 21));
		}
		return cbbBeforeLegalAmount;
	}

	/**
	 * This method initializes cbbAfterTenMemoNo6
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeSecondLegalAmount() {
		if (cbbBeforeSecondLegalAmount == null) {
			cbbBeforeSecondLegalAmount = new JComboBox();
			cbbBeforeSecondLegalAmount.setBounds(new Rectangle(136, 144, 106,
					21));
		}
		return cbbBeforeSecondLegalAmount;
	}

	/**
	 * This method initializes cbbAfterTenMemoNo7
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeDeclaredPrice() {
		if (cbbBeforeDeclaredPrice == null) {
			cbbBeforeDeclaredPrice = new JComboBox();
			cbbBeforeDeclaredPrice.setBounds(new Rectangle(374, 17, 106, 21));
		}
		return cbbBeforeDeclaredPrice;
	}

	/**
	 * This method initializes cbbAfterTenMemoNo8
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeSalesCountry() {
		if (cbbBeforeSalesCountry == null) {
			cbbBeforeSalesCountry = new JComboBox();
			cbbBeforeSalesCountry.setBounds(new Rectangle(374, 42, 106, 21));
		}
		return cbbBeforeSalesCountry;
	}

	/**
	 * This method initializes cbbAfterTenMemoNo9
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeLevyModel() {
		if (cbbBeforeLevyModel == null) {
			cbbBeforeLevyModel = new JComboBox();
			cbbBeforeLevyModel.setBounds(new Rectangle(373, 69, 106, 21));
		}
		return cbbBeforeLevyModel;
	}

	/**
	 * This method initializes cbbAfterTenMemoNo10
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeUsess() {
		if (cbbBeforeUsess == null) {
			cbbBeforeUsess = new JComboBox();
			cbbBeforeUsess.setBounds(new Rectangle(373, 93, 106, 21));
		}
		return cbbBeforeUsess;
	}

	/**
	 * This method initializes cbbAfterTenMemoNo11
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeVersion() {
		if (cbbBeforeVersion == null) {
			cbbBeforeVersion = new JComboBox();
			cbbBeforeVersion.setBounds(new Rectangle(373, 117, 106, 21));
		}
		return cbbBeforeVersion;
	}

	/**
	 * This method initializes cbbAfterTenMemoNo12
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeGrossWeight() {
		if (cbbBeforeGrossWeight == null) {
			cbbBeforeGrossWeight = new JComboBox();
			cbbBeforeGrossWeight.setBounds(new Rectangle(373, 140, 106, 21));
		}
		return cbbBeforeGrossWeight;
	}

	/**
	 * This method initializes cbbAfterTenMemoNo13
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeNetWeight() {
		if (cbbBeforeNetWeight == null) {
			cbbBeforeNetWeight = new JComboBox();
			cbbBeforeNetWeight.setBounds(new Rectangle(605, 17, 106, 21));
		}
		return cbbBeforeNetWeight;
	}

	/**
	 * This method initializes cbbAfterTenMemoNo14
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeMemos() {
		if (cbbBeforeMemos == null) {
			cbbBeforeMemos = new JComboBox();
			cbbBeforeMemos.setBounds(new Rectangle(605, 73, 106, 21));
		}
		return cbbBeforeMemos;
	}

	/**
	 * This method initializes cbbAfterTenMemoNo15
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeProjectDept() {
		if (cbbBeforeProjectDept == null) {
			cbbBeforeProjectDept = new JComboBox();
			cbbBeforeProjectDept.setBounds(new Rectangle(605, 100, 106, 21));
		}
		return cbbBeforeProjectDept;
	}

	/**
	 * This method initializes cbbAfterTenMemoNo16
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeExtendMemo() {
		if (cbbBeforeExtendMemo == null) {
			cbbBeforeExtendMemo = new JComboBox();
			cbbBeforeExtendMemo.setBounds(new Rectangle(605, 124, 106, 21));
		}
		return cbbBeforeExtendMemo;
	}

	/**
	 * This method initializes cbbDeclareFirm1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbListState() {
		if (cbbListState == null) {
			cbbListState = new JComboBox();
			cbbListState.setBounds(new Rectangle(609, 20, 106, 21));
		}
		return cbbListState;
	}

	/**
	 * This method initializes btnRestoreInitValues1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSetParameter() {
		if (btnSetParameter == null) {
			btnSetParameter = new JButton();
			btnSetParameter.setBounds(new Rectangle(46, 511, 94, 25));
			btnSetParameter.setText("参数设置");
			btnSetParameter
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgParameterSet dgParameterSet = new DgParameterSet();
							dgParameterSet
									.setImportApplyProperty(importApplyProperty);
							dgParameterSet.setVisible(true);
							importApplyProperty = dgParameterSet
									.getImportApplyProperty();
						}
					});

		}
		return btnSetParameter;
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

	/**
	 * @return the isOk
	 */
	public boolean getIsOk() {
		return isOk;
	}

	/**
	 * This method initializes cbbBeforePeice
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforePeice() {
		if (cbbBeforePeice == null) {
			cbbBeforePeice = new JComboBox();
			cbbBeforePeice.setBounds(new Rectangle(605, 43, 106, 21));
		}
		return cbbBeforePeice;
	}
}
