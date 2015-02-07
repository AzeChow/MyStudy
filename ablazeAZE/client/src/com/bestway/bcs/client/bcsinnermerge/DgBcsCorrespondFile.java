/*
 * Created on 2004-7-22
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.bcsinnermerge;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import com.bestway.bcs.bcsinnermerge.action.BcsInnerMergeAction;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMergeDataOrder;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMergeFileData;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 设定导入资料与文件资料的列对应关系
 * 
 */
public class DgBcsCorrespondFile extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JComboBox cbbSerialNo = null;

	private JComboBox cbbBeforeMaterielCode = null;

	private JComboBox cbbBeforeTenComplexCode = null;

	private JComboBox cbbBeforeMaterielNameSpec = null;

	private JComboBox cbbBeforeUnit = null;

	private JComboBox cbbAfterTenMemoNo = null;

	private JComboBox cbbAfterTenComplexCode = null;

	private JComboBox cbbAfterComplexNameSpec = null;

	private JComboBox cbbAfterUnit = null;

	private int fileColumnCount = 0;

	private Hashtable<Integer, Integer> selectedValues = null; // @jve:decl-index=0:

	private JButton btnOK = null;

	private JButton btnCancel = null;

	private JButton btnRestoreInitValues = null;

	private JComboBox cbbEnterpriseUnit = null;

	private JLabel jLabel10 = null;

	private JPanel jPanel3 = null;

	private JLabel jLabel14 = null;

	private JComboBox cbbUnitConvert = null;

	private JComboBox cbbUnitWeight = null;

	private JLabel jLabel15 = null;

	private JTextPane jTextPane = null;

	private JLabel jLabel811 = null;

	private JLabel jLabel821 = null;

	private JComboBox cbbBeforeMaterielName = null;

	private JComboBox cbbBeforeMaterielSpec = null;

	private JLabel jLabel81 = null;

	private JLabel jLabel82 = null;

	private JComboBox cbbAfterComplexName = null;

	private JComboBox cbbAfterComplexSpec = null;

	private BcsInnerMergeAction bcsInnerMergeAction = null;

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
	public DgBcsCorrespondFile() {
		super();
		initialize();
		bcsInnerMergeAction = (BcsInnerMergeAction) CommonVars
				.getApplicationContext().getBean("bcsInnerMergeAction");
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
		this.setSize(651, 462);
	}

	public void setVisible(boolean b) {
		if (b) {
			fillAllComboBox();
			restoreInitValues();
		}
		super.setVisible(b);
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
			jContentPane.add(getBtnOK(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getBtnRestoreInitValues(), null);
			jContentPane.add(getJPanel3(), null);
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
			jLabel821 = new JLabel();
			jLabel821.setBounds(new Rectangle(17, 148, 124, 24));
			jLabel821.setText("\u89c4\u683c\uff0c\u578b\u53f7");
			jLabel821.setForeground(Color.blue);
			jLabel811 = new JLabel();
			jLabel811.setBounds(new Rectangle(17, 122, 124, 23));
			jLabel811.setText("\u5546\u54c1\u540d\u79f0");
			jLabel811.setForeground(Color.blue);
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(17, 201, 126, 21));
			jLabel10.setText("工厂单位");
			jPanel = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(17, 14, 295, 231);
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"归并前",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel.setText("序号");
			jLabel.setBounds(17, 20, 126, 21);
			jLabel1.setBounds(17, 45, 126, 21);
			jLabel1.setText("料号");
			jLabel2.setBounds(17, 71, 126, 21);
			jLabel2.setText("10位商品编码");
			jLabel3.setBounds(17, 96, 126, 21);
			jLabel3.setText("商品名称，型号，规格");
			jLabel4.setBounds(17, 174, 126, 21);
			jLabel4.setText("申报单位");
			jPanel.add(jLabel, null);
			jPanel.add(getCbbSerialNo(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(getCbbBeforeMaterielCode(), null);
			jPanel.add(getCbbBeforeTenComplexCode(), null);
			jPanel.add(getCbbBeforeMaterielNameSpec(), null);
			jPanel.add(getCbbBeforeUnit(), null);
			jPanel.add(getCbbEnterpriseUnit(), null);
			jPanel.add(jLabel10, null);
			jPanel.add(jLabel811, null);
			jPanel.add(jLabel821, null);
			jPanel.add(getCbbBeforeMaterielName(), null);
			jPanel.add(getCbbBeforeMaterielSpec(), null);
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
			jLabel82 = new JLabel();
			jLabel82.setBounds(new Rectangle(16, 112, 123, 21));
			jLabel82.setText("\u89c4\u683c\uff0c\u578b\u53f7");
			jLabel82.setForeground(Color.blue);
			jLabel81 = new JLabel();
			jLabel81.setBounds(new Rectangle(16, 88, 127, 22));
			jLabel81.setText("\u5546\u54c1\u540d\u79f0");
			jLabel81.setForeground(Color.blue);
			jPanel1 = new JPanel();
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(17, 253, 295, 167);
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "归并后",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel6.setBounds(16, 18, 128, 21);
			jLabel6.setText("备案序号");
			jLabel7.setBounds(16, 39, 128, 21);
			jLabel7.setText("10位商品编码");
			jLabel8.setBounds(16, 62, 128, 21);
			jLabel8.setText("商品名称，规格，型号");
			jLabel9.setBounds(16, 137, 128, 21);
			jLabel9.setText("申报单位");
			jPanel1.add(jLabel6, null);
			jPanel1.add(jLabel7, null);
			jPanel1.add(jLabel8, null);
			jPanel1.add(jLabel9, null);
			jPanel1.add(getCbbAfterTenMemoNo(), null);
			jPanel1.add(getCbbAfterTenComplexCode(), null);
			jPanel1.add(getCbbAfterComplexNameSpec(), null);
			jPanel1.add(getCbbAfterUnit(), null);
			jPanel1.add(jLabel81, null);
			jPanel1.add(jLabel82, null);
			jPanel1.add(getCbbAfterComplexName(), null);
			jPanel1.add(getCbbAfterComplexSpec(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes cbbSerialNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSerialNo() {
		if (cbbSerialNo == null) {
			cbbSerialNo = new JComboBox();
			cbbSerialNo.setBounds(145, 19, 130, 20);
		}
		return cbbSerialNo;
	}

	/**
	 * This method initializes cbbBeforeMaterielCode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeMaterielCode() {
		if (cbbBeforeMaterielCode == null) {
			cbbBeforeMaterielCode = new JComboBox();
			cbbBeforeMaterielCode.setBounds(145, 47, 130, 20);
		}
		return cbbBeforeMaterielCode;
	}

	/**
	 * This method initializes cbbBeforeTenComplexCode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeTenComplexCode() {
		if (cbbBeforeTenComplexCode == null) {
			cbbBeforeTenComplexCode = new JComboBox();
			cbbBeforeTenComplexCode.setBounds(145, 72, 130, 20);
		}
		return cbbBeforeTenComplexCode;
	}

	/**
	 * This method initializes cbbBeforeMaterielNameSpec
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeMaterielNameSpec() {
		if (cbbBeforeMaterielNameSpec == null) {
			cbbBeforeMaterielNameSpec = new JComboBox();
			cbbBeforeMaterielNameSpec.setBounds(145, 98, 130, 20);
			cbbBeforeMaterielNameSpec
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (cbbBeforeMaterielNameSpec.getSelectedIndex() > 0) {
								cbbBeforeMaterielName.setSelectedIndex(-1);
								cbbBeforeMaterielSpec.setSelectedIndex(-1);
							}
						}
					});
		}
		return cbbBeforeMaterielNameSpec;
	}

	/**
	 * This method initializes cbbBeforeLegalUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeUnit() {
		if (cbbBeforeUnit == null) {
			cbbBeforeUnit = new JComboBox();
			cbbBeforeUnit.setBounds(145, 176, 130, 20);
		}
		return cbbBeforeUnit;
	}

	/**
	 * This method initializes cbbAfterTenMemoNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAfterTenMemoNo() {
		if (cbbAfterTenMemoNo == null) {
			cbbAfterTenMemoNo = new JComboBox();
			cbbAfterTenMemoNo.setBounds(147, 17, 130, 20);
		}
		return cbbAfterTenMemoNo;
	}

	/**
	 * This method initializes cbbAfterTenComplexCode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAfterTenComplexCode() {
		if (cbbAfterTenComplexCode == null) {
			cbbAfterTenComplexCode = new JComboBox();
			cbbAfterTenComplexCode.setBounds(147, 40, 130, 20);
		}
		return cbbAfterTenComplexCode;
	}

	/**
	 * This method initializes cbbAfterComplexNameSpec
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAfterComplexNameSpec() {
		if (cbbAfterComplexNameSpec == null) {
			cbbAfterComplexNameSpec = new JComboBox();
			cbbAfterComplexNameSpec.setBounds(147, 64, 130, 20);
			cbbAfterComplexNameSpec
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (cbbAfterComplexNameSpec.getSelectedIndex() > 0) {
								cbbAfterComplexName.setSelectedIndex(-1);
								cbbAfterComplexSpec.setSelectedIndex(-1);
							}
						}
					});
		}
		return cbbAfterComplexNameSpec;
	}

	/**
	 * This method initializes cbbAfterLegalUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAfterUnit() {
		if (cbbAfterUnit == null) {
			cbbAfterUnit = new JComboBox();
			cbbAfterUnit.setBounds(147, 138, 130, 20);
		}
		return cbbAfterUnit;
	}

	private void fillComboBox(JComboBox comboBox) {
		comboBox.addItem(new ItemProperty(String.valueOf(0), "  "));
		for (int i = 0; i < fileColumnCount; i++) {
			comboBox.addItem(new ItemProperty(String.valueOf(i + 1), "第"
					+ (i + 1) + "列"));
		}
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(comboBox);
	}

	private void fillAllComboBox() {
		fillComboBox(this.cbbSerialNo);
		fillComboBox(this.cbbBeforeMaterielCode);
		fillComboBox(this.cbbBeforeTenComplexCode);
		fillComboBox(this.cbbBeforeMaterielNameSpec);
		fillComboBox(this.cbbBeforeMaterielName);
		fillComboBox(this.cbbBeforeMaterielSpec);
		fillComboBox(this.cbbBeforeUnit);
		fillComboBox(this.cbbEnterpriseUnit);
		fillComboBox(this.cbbAfterTenMemoNo);
		fillComboBox(this.cbbAfterTenComplexCode);
		fillComboBox(this.cbbAfterComplexNameSpec);
		fillComboBox(this.cbbAfterComplexName);
		fillComboBox(this.cbbAfterComplexSpec);
		fillComboBox(this.cbbAfterUnit);
		fillComboBox(this.cbbUnitConvert);
		fillComboBox(this.cbbUnitWeight);

	}

	private void getSelectedValue() {
		if (selectedValues == null) {
			selectedValues = new Hashtable<Integer, Integer>();
		}
		selectedValues.put(Integer.valueOf(BcsInnerMergeFileData.SERIAL_NO),
				getComboBoxSelectValue(this.cbbSerialNo));
		selectedValues.put(Integer
				.valueOf(BcsInnerMergeFileData.BEFORE_MATERIEL_CODE),
				getComboBoxSelectValue(this.cbbBeforeMaterielCode));
		selectedValues.put(Integer
				.valueOf(BcsInnerMergeFileData.BEFORE_TEN_COMPLEX_CODE),
				getComboBoxSelectValue(this.cbbBeforeTenComplexCode));
		selectedValues.put(Integer
				.valueOf(BcsInnerMergeFileData.BEFORE_MATERIEL_NAME_SPEC),
				getComboBoxSelectValue(this.cbbBeforeMaterielNameSpec));
		selectedValues.put(Integer
				.valueOf(BcsInnerMergeFileData.BEFORE_MATERIEL_NAME),
				getComboBoxSelectValue(this.cbbBeforeMaterielName));
		selectedValues.put(Integer
				.valueOf(BcsInnerMergeFileData.BEFORE_MATERIEL_SPEC),
				getComboBoxSelectValue(this.cbbBeforeMaterielSpec));
		selectedValues.put(Integer.valueOf(BcsInnerMergeFileData.BEFORE_UNIT),
				getComboBoxSelectValue(this.cbbBeforeUnit));
		selectedValues.put(Integer
				.valueOf(BcsInnerMergeFileData.BEFORE_ENTERPRISE_UNIT),
				getComboBoxSelectValue(this.cbbEnterpriseUnit));
		selectedValues.put(Integer
				.valueOf(BcsInnerMergeFileData.AFTER_TEN_MEMO_NO),
				getComboBoxSelectValue(this.cbbAfterTenMemoNo));
		selectedValues.put(Integer
				.valueOf(BcsInnerMergeFileData.AFTER_TEN_COMPLEX_CODE),
				getComboBoxSelectValue(this.cbbAfterTenComplexCode));
		selectedValues.put(Integer
				.valueOf(BcsInnerMergeFileData.AFTER_COMPLEX_NAME_SPEC),
				getComboBoxSelectValue(this.cbbAfterComplexNameSpec));
		selectedValues.put(Integer
				.valueOf(BcsInnerMergeFileData.AFTER_COMPLEX_NAME),
				getComboBoxSelectValue(this.cbbAfterComplexName));
		selectedValues.put(Integer
				.valueOf(BcsInnerMergeFileData.AFTER_COMPLEX_SPEC),
				getComboBoxSelectValue(this.cbbAfterComplexSpec));
		selectedValues.put(Integer.valueOf(BcsInnerMergeFileData.AFTER_UNIT),
				getComboBoxSelectValue(this.cbbAfterUnit));
		selectedValues.put(Integer.valueOf(BcsInnerMergeFileData.UNIT_CONVERT),
				getComboBoxSelectValue(this.cbbUnitConvert));
		selectedValues.put(Integer.valueOf(BcsInnerMergeFileData.UNIT_WEIGHT),
				getComboBoxSelectValue(this.cbbUnitWeight));

	}

	private Integer getComboBoxSelectValue(JComboBox comboBox) {
		if (comboBox.getSelectedItem() == null) {
			return -1;
		}
		return Integer.valueOf(((ItemProperty) comboBox.getSelectedItem())
				.getCode()) - 1;
	}

	private void setComboBoxInitValue(JComboBox comboBox, int selectedIndex) {
		if (selectedIndex > comboBox.getItemCount() - 1) {
			comboBox.setSelectedIndex(0);
		} else {
			comboBox.setSelectedIndex(selectedIndex);
		}
	}

	private void restoreInitValues() {
		if (selectedValues == null
				|| selectedValues.get(Integer
						.valueOf(BcsInnerMergeFileData.SERIAL_NO)) == null) {
			setComboBoxInitValue(this.cbbSerialNo,
					BcsInnerMergeFileData.SERIAL_NO);
		} else {
			this.cbbSerialNo.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf(selectedValues.get(Integer
							.valueOf(BcsInnerMergeFileData.SERIAL_NO) + 1)),
					this.cbbSerialNo));
		}
		if (selectedValues == null
				|| selectedValues.get(Integer
						.valueOf(BcsInnerMergeFileData.BEFORE_MATERIEL_CODE)) == null) {
			setComboBoxInitValue(this.cbbBeforeMaterielCode,
					BcsInnerMergeFileData.BEFORE_MATERIEL_CODE);
		} else {
			this.cbbBeforeMaterielCode
					.setSelectedIndex(ItemProperty
							.getIndexByCode(
									String
											.valueOf(selectedValues
													.get(Integer
															.valueOf(BcsInnerMergeFileData.BEFORE_MATERIEL_CODE)) + 1),
									this.cbbBeforeMaterielCode));
		}
		if (selectedValues == null
				|| selectedValues
						.get(Integer
								.valueOf(BcsInnerMergeFileData.BEFORE_TEN_COMPLEX_CODE)) == null) {
			setComboBoxInitValue(this.cbbBeforeTenComplexCode,
					BcsInnerMergeFileData.BEFORE_TEN_COMPLEX_CODE);
		} else {
			this.cbbBeforeTenComplexCode
					.setSelectedIndex(ItemProperty
							.getIndexByCode(
									String
											.valueOf(selectedValues
													.get(Integer
															.valueOf(BcsInnerMergeFileData.BEFORE_TEN_COMPLEX_CODE)) + 1),
									this.cbbBeforeTenComplexCode));
		}
		if (selectedValues == null
				|| selectedValues
						.get(Integer
								.valueOf(BcsInnerMergeFileData.BEFORE_MATERIEL_NAME_SPEC)) == null) {
			setComboBoxInitValue(this.cbbBeforeMaterielNameSpec,
					BcsInnerMergeFileData.BEFORE_MATERIEL_NAME_SPEC);
		} else {
			this.cbbBeforeMaterielNameSpec
					.setSelectedIndex(ItemProperty
							.getIndexByCode(
									String
											.valueOf(selectedValues
													.get(Integer
															.valueOf(BcsInnerMergeFileData.BEFORE_MATERIEL_NAME_SPEC)) + 1),
									this.cbbBeforeMaterielNameSpec));
		}
		if (selectedValues == null
				|| selectedValues.get(Integer
						.valueOf(BcsInnerMergeFileData.BEFORE_MATERIEL_NAME)) == null) {
			setComboBoxInitValue(this.cbbBeforeMaterielName,
					BcsInnerMergeFileData.BEFORE_MATERIEL_NAME);
		} else {
			this.cbbBeforeMaterielName
					.setSelectedIndex(ItemProperty
							.getIndexByCode(
									String
											.valueOf(selectedValues
													.get(Integer
															.valueOf(BcsInnerMergeFileData.BEFORE_MATERIEL_NAME)) + 1),
									this.cbbBeforeMaterielName));
		}
		if (selectedValues == null
				|| selectedValues.get(Integer
						.valueOf(BcsInnerMergeFileData.BEFORE_MATERIEL_SPEC)) == null) {
			setComboBoxInitValue(this.cbbBeforeMaterielSpec,
					BcsInnerMergeFileData.BEFORE_MATERIEL_SPEC);
		} else {
			this.cbbBeforeMaterielSpec
					.setSelectedIndex(ItemProperty
							.getIndexByCode(
									String
											.valueOf(selectedValues
													.get(Integer
															.valueOf(BcsInnerMergeFileData.BEFORE_MATERIEL_SPEC)) + 1),
									this.cbbBeforeMaterielSpec));
		}
		if (selectedValues == null
				|| selectedValues.get(Integer
						.valueOf(BcsInnerMergeFileData.BEFORE_UNIT)) == null) {
			setComboBoxInitValue(this.cbbBeforeUnit,
					BcsInnerMergeFileData.BEFORE_UNIT);
		} else {
			this.cbbBeforeUnit.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf(selectedValues.get(Integer
							.valueOf(BcsInnerMergeFileData.BEFORE_UNIT)) + 1),
					this.cbbBeforeUnit));
		}
		if (selectedValues == null
				|| selectedValues.get(Integer
						.valueOf(BcsInnerMergeFileData.BEFORE_ENTERPRISE_UNIT)) == null) {
			setComboBoxInitValue(this.cbbEnterpriseUnit,
					BcsInnerMergeFileData.BEFORE_ENTERPRISE_UNIT);
		} else {
			this.cbbEnterpriseUnit
					.setSelectedIndex(ItemProperty
							.getIndexByCode(
									String
											.valueOf(selectedValues
													.get(Integer
															.valueOf(BcsInnerMergeFileData.BEFORE_ENTERPRISE_UNIT)) + 1),
									this.cbbEnterpriseUnit));
		}
		if (selectedValues == null
				|| selectedValues.get(Integer
						.valueOf(BcsInnerMergeFileData.AFTER_TEN_MEMO_NO)) == null) {
			setComboBoxInitValue(this.cbbAfterTenMemoNo,
					BcsInnerMergeFileData.AFTER_TEN_MEMO_NO);
		} else {
			this.cbbAfterTenMemoNo
					.setSelectedIndex(ItemProperty
							.getIndexByCode(
									String
											.valueOf(selectedValues
													.get(Integer
															.valueOf(BcsInnerMergeFileData.AFTER_TEN_MEMO_NO)) + 1),
									this.cbbAfterTenMemoNo));
		}
		if (selectedValues == null
				|| selectedValues.get(Integer
						.valueOf(BcsInnerMergeFileData.AFTER_TEN_COMPLEX_CODE)) == null) {
			setComboBoxInitValue(this.cbbAfterTenComplexCode,
					BcsInnerMergeFileData.AFTER_TEN_COMPLEX_CODE);
		} else {
			this.cbbAfterTenComplexCode
					.setSelectedIndex(ItemProperty
							.getIndexByCode(
									String
											.valueOf(selectedValues
													.get(Integer
															.valueOf(BcsInnerMergeFileData.AFTER_TEN_COMPLEX_CODE)) + 1),
									this.cbbAfterTenComplexCode));

		}
		if (selectedValues == null
				|| selectedValues
						.get(Integer
								.valueOf(BcsInnerMergeFileData.AFTER_COMPLEX_NAME_SPEC)) == null) {
			setComboBoxInitValue(this.cbbAfterComplexNameSpec,
					BcsInnerMergeFileData.AFTER_COMPLEX_NAME_SPEC);
		} else {
			this.cbbAfterComplexNameSpec
					.setSelectedIndex(ItemProperty
							.getIndexByCode(
									String
											.valueOf(selectedValues
													.get(Integer
															.valueOf(BcsInnerMergeFileData.AFTER_COMPLEX_NAME_SPEC)) + 1),
									this.cbbAfterComplexNameSpec));
		}
		if (selectedValues == null
				|| selectedValues.get(Integer
						.valueOf(BcsInnerMergeFileData.AFTER_COMPLEX_NAME)) == null) {
			setComboBoxInitValue(this.cbbAfterComplexName,
					BcsInnerMergeFileData.AFTER_COMPLEX_NAME);
		} else {
			this.cbbAfterComplexName
					.setSelectedIndex(ItemProperty
							.getIndexByCode(
									String
											.valueOf(selectedValues
													.get(Integer
															.valueOf(BcsInnerMergeFileData.AFTER_COMPLEX_NAME)) + 1),
									this.cbbAfterComplexName));
		}
		if (selectedValues == null
				|| selectedValues.get(Integer
						.valueOf(BcsInnerMergeFileData.AFTER_COMPLEX_SPEC)) == null) {
			setComboBoxInitValue(this.cbbAfterComplexSpec,
					BcsInnerMergeFileData.AFTER_COMPLEX_SPEC);
		} else {
			this.cbbAfterComplexSpec
					.setSelectedIndex(ItemProperty
							.getIndexByCode(
									String
											.valueOf(selectedValues
													.get(Integer
															.valueOf(BcsInnerMergeFileData.AFTER_COMPLEX_SPEC)) + 1),
									this.cbbAfterComplexSpec));
		}
		if (selectedValues == null
				|| selectedValues.get(Integer
						.valueOf(BcsInnerMergeFileData.AFTER_UNIT)) == null) {
			setComboBoxInitValue(this.cbbAfterUnit,
					BcsInnerMergeFileData.AFTER_UNIT);
		} else {
			this.cbbAfterUnit.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf(selectedValues.get(Integer
							.valueOf(BcsInnerMergeFileData.AFTER_UNIT)) + 1),
					this.cbbAfterUnit));
		}
		if (selectedValues == null
				|| selectedValues.get(Integer
						.valueOf(BcsInnerMergeFileData.UNIT_CONVERT)) == null) {
			setComboBoxInitValue(this.cbbUnitConvert,
					BcsInnerMergeFileData.UNIT_CONVERT);
		} else {
			this.cbbUnitConvert.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf(selectedValues.get(Integer
							.valueOf(BcsInnerMergeFileData.UNIT_CONVERT)) + 1),
					this.cbbUnitConvert));
		}
		if (selectedValues == null
				|| selectedValues.get(Integer
						.valueOf(BcsInnerMergeFileData.UNIT_WEIGHT)) == null) {
			setComboBoxInitValue(this.cbbUnitWeight,
					BcsInnerMergeFileData.UNIT_WEIGHT);
		} else {
			this.cbbUnitWeight.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf(selectedValues.get(Integer
							.valueOf(BcsInnerMergeFileData.UNIT_WEIGHT)) + 1),
					this.cbbUnitWeight));
		}

	}

	/**
	 * This method initializes btnOK
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(326, 386, 97, 25);
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					BcsInnerMergeDataOrder order = bcsInnerMergeAction
							.findBcsInnerMergeDataOrder(new Request(CommonVars
									.getCurrUser(), true));
					fillData(order);
					bcsInnerMergeAction.saveBcsInnerMergeDataOrder(new Request(
							CommonVars.getCurrUser(), true), order);
					getSelectedValue();
					dispose();
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
			btnCancel.setBounds(428, 386, 97, 25);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
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
			btnRestoreInitValues.setBounds(530, 386, 97, 25);
			btnRestoreInitValues.setText("恢复初始值");
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
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEnterpriseUnit() {
		if (cbbEnterpriseUnit == null) {
			cbbEnterpriseUnit = new JComboBox();
			cbbEnterpriseUnit.setBounds(new Rectangle(144, 202, 130, 20));
		}
		return cbbEnterpriseUnit;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel15 = new JLabel();
			jLabel15.setBounds(new java.awt.Rectangle(22, 48, 87, 21));
			jLabel15.setText("单重");
			jLabel14 = new JLabel();
			jLabel14.setBounds(new java.awt.Rectangle(22, 22, 87, 21));
			jLabel14.setText("单位折算系数");
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setBounds(new Rectangle(334, 15, 295, 146));
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "其他对应",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel3.add(jLabel14, null);
			jPanel3.add(getCbbUnitConvert(), null);
			jPanel3.add(getCbbUnitWeight(), null);
			jPanel3.add(jLabel15, null);
			jPanel3.add(getJTextPane(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUnitConvert() {
		if (cbbUnitConvert == null) {
			cbbUnitConvert = new JComboBox();
			cbbUnitConvert.setBounds(new Rectangle(143, 21, 135, 23));
		}
		return cbbUnitConvert;
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUnitWeight() {
		if (cbbUnitWeight == null) {
			cbbUnitWeight = new JComboBox();
			cbbUnitWeight.setBounds(new Rectangle(143, 46, 135, 23));
		}
		return cbbUnitWeight;
	}

	/**
	 * This method initializes jTextPane
	 * 
	 * @return javax.swing.JTextPane
	 */
	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			jTextPane.setBounds(new Rectangle(24, 77, 265, 58));
			jTextPane.setForeground(java.awt.Color.blue);
			jTextPane.setEditable(false);
			jTextPane.setBackground(new Color(238, 238, 238));
			jTextPane.setText("单位折算系数=归并后单位数量/归并前工厂的单位数量");
		}
		return jTextPane;
	}

	/**
	 * This method initializes cbbBeforeMaterielName
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeMaterielName() {
		if (cbbBeforeMaterielName == null) {
			cbbBeforeMaterielName = new JComboBox();
			cbbBeforeMaterielName.setBounds(new Rectangle(145, 125, 130, 20));
			cbbBeforeMaterielName
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (cbbBeforeMaterielName.getSelectedIndex() > 0) {
								cbbBeforeMaterielNameSpec.setSelectedIndex(-1);
							}
						}
					});
		}
		return cbbBeforeMaterielName;
	}

	/**
	 * This method initializes cbbBeforeMaterielSpec
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeMaterielSpec() {
		if (cbbBeforeMaterielSpec == null) {
			cbbBeforeMaterielSpec = new JComboBox();
			cbbBeforeMaterielSpec.setBounds(new Rectangle(145, 151, 130, 20));
			cbbBeforeMaterielSpec
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (cbbBeforeMaterielSpec.getSelectedIndex() > 0) {
								cbbBeforeMaterielNameSpec.setSelectedIndex(-1);
							}
						}
					});
		}
		return cbbBeforeMaterielSpec;
	}

	/**
	 * This method initializes cbbAfterComplexName
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAfterComplexName() {
		if (cbbAfterComplexName == null) {
			cbbAfterComplexName = new JComboBox();
			cbbAfterComplexName.setBounds(new Rectangle(147, 88, 130, 20));
			cbbAfterComplexName
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (cbbAfterComplexName.getSelectedIndex() > 0) {
								cbbAfterComplexNameSpec.setSelectedIndex(-1);
							}
						}
					});
		}
		return cbbAfterComplexName;
	}

	/**
	 * This method initializes cbbAfterComplexSpec
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAfterComplexSpec() {
		if (cbbAfterComplexSpec == null) {
			cbbAfterComplexSpec = new JComboBox();
			cbbAfterComplexSpec.setBounds(new Rectangle(147, 113, 130, 20));
			cbbAfterComplexSpec
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (cbbAfterComplexSpec.getSelectedIndex() > 0) {
								cbbAfterComplexNameSpec.setSelectedIndex(-1);
							}
						}
					});
		}
		return cbbAfterComplexSpec;
	}

	private void fillData(BcsInnerMergeDataOrder order) {
		if (this.cbbSerialNo.getSelectedItem() != null) {
			order.setSerialNo(Integer.valueOf(((ItemProperty) this.cbbSerialNo
					.getSelectedItem()).getCode()) - 1);
		} else {
			order.setSerialNo(-1);
		}
		if (this.cbbBeforeMaterielCode.getSelectedItem() != null) {
			order.setBeforeMaterielCode(Integer
					.valueOf(((ItemProperty) this.cbbBeforeMaterielCode
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setBeforeMaterielCode(-1);
		}
		if (this.cbbBeforeTenComplexCode.getSelectedItem() != null) {
			order.setBeforeTenComplexCode(Integer
					.valueOf(((ItemProperty) this.cbbBeforeTenComplexCode
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setBeforeTenComplexCode(-1);
		}
		if (this.cbbBeforeMaterielNameSpec.getSelectedItem() != null) {
			order.setBeforeMaterielNameSpec(Integer
					.valueOf(((ItemProperty) this.cbbBeforeMaterielNameSpec
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setBeforeMaterielNameSpec(-1);
		}
		if (this.cbbBeforeMaterielName.getSelectedItem() != null) {
			order.setBeforeMaterielName(Integer
					.valueOf(((ItemProperty) this.cbbBeforeMaterielName
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setBeforeMaterielName(-1);
		}
		if (this.cbbBeforeMaterielSpec.getSelectedItem() != null) {
			order.setBeforeMaterielSpec(Integer
					.valueOf(((ItemProperty) this.cbbBeforeMaterielSpec
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setBeforeMaterielSpec(-1);
		}
		if (this.cbbBeforeUnit.getSelectedItem() != null) {
			order.setBeforeUnit(Integer
					.valueOf(((ItemProperty) this.cbbBeforeUnit
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setBeforeUnit(-1);
		}
		if (this.cbbEnterpriseUnit.getSelectedItem() != null) {
			order.setBeforeEnterpriseUnit(Integer
					.valueOf(((ItemProperty) this.cbbEnterpriseUnit
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setBeforeEnterpriseUnit(-1);
		}
		if (this.cbbAfterTenMemoNo.getSelectedItem() != null) {
			order.setAfterTenMemoNo(Integer
					.valueOf(((ItemProperty) this.cbbAfterTenMemoNo
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setAfterTenMemoNo(-1);
		}
		if (this.cbbAfterTenComplexCode.getSelectedItem() != null) {
			order.setAfterTenComplexCode(Integer
					.valueOf(((ItemProperty) this.cbbAfterTenComplexCode
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setAfterTenComplexCode(-1);
		}
		if (this.cbbAfterComplexNameSpec.getSelectedItem() != null) {
			order.setAfterComplexlNameSpec(Integer
					.valueOf(((ItemProperty) this.cbbAfterComplexNameSpec
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setAfterComplexlNameSpec(-1);
		}
		if (this.cbbAfterComplexName.getSelectedItem() != null) {
			order.setAfterComplexlName(Integer
					.valueOf(((ItemProperty) this.cbbAfterComplexName
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setAfterComplexlName(-1);
		}
		if (this.cbbAfterComplexSpec.getSelectedItem() != null) {
			order.setAfterComplexSpec(Integer
					.valueOf(((ItemProperty) this.cbbAfterComplexSpec
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setAfterComplexSpec(-1);
		}
		if (this.cbbAfterUnit.getSelectedItem() != null) {
			order.setAfterUnit(Integer
					.valueOf(((ItemProperty) this.cbbAfterUnit
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setAfterUnit(-1);
		}
		if (this.cbbUnitConvert.getSelectedItem() != null) {
			order.setUnitConvert(Integer
					.valueOf(((ItemProperty) this.cbbUnitConvert
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setUnitConvert(-1);
		}
		if (this.cbbUnitWeight.getSelectedItem() != null) {
			order.setUnitWeight(Integer
					.valueOf(((ItemProperty) this.cbbUnitWeight
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setUnitWeight(-1);
		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
