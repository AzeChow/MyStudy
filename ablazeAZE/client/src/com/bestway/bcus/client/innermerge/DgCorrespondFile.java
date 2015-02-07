/*
 * Created on 2004-7-22
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;

import java.util.Hashtable;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.InnerMergeDataOrder;
import com.bestway.bcus.innermerge.entity.InnerMergeFileData;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCorrespondFile extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JComboBox cbbSerialNo = null;

	private JComboBox cbbBeforeMaterielCode = null;

	private JComboBox cbbBeforeTenComplexCode = null;

	private JComboBox cbbBeforeMaterielNameSpec = null;

	private JComboBox cbbBeforeLegalUnit = null;

	private JComboBox cbbBeforeEnterpriseUnit = null;

	private JComboBox cbbAfterTenMemoNo = null;

	private JComboBox cbbAfterTenComplexCode = null;

	private JComboBox cbbAfterComplexNameSpec = null;

	private JComboBox cbbAfterLegalUnit = null;

	private JComboBox cbbAfterMemoUnit = null;

	private JComboBox cbbFourNo = null;

	private JComboBox cbbFourComplexName = null;

	private JComboBox cbbFourComplexCode = null;

	private int fileColumnCount = 0;

	private Hashtable selectedValues = null;

	private JButton btnOK = null;

	private JButton btnCancel = null;

	private JButton btnRestoreInitValues = null;

	private JLabel jLabel14 = null;

	private JComboBox jComboBox = null;

	private JLabel jLabel15 = null;

	private JComboBox jComboBox1 = null;

	private boolean isBeforeMerger = true;// 归并前商品名称型号规格是否合并显示

	private boolean isAfterMerger = true; // 归并后商品名称型号规格是否合并显示

	private JLabel jLabel16 = null;

	private JLabel jLabel17 = null;

	private JComboBox jComboBox2 = null;

	private JComboBox jComboBox3 = null;

	private JPanel jPanel3 = null;

	private JLabel jLabel18 = null;

	private JComboBox jComboBox4 = null;

	private JLabel jLabel19 = null;

	private JComboBox jComboBox5 = null;

	private JLabel jLabel20 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private CommonBaseCodeAction commonBaseCodeAction = null;

	private InnerMergeDataOrder obj = null; // @jve:decl-index=0:

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
	public DgCorrespondFile() {
		super();
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
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
		this.setSize(710, 574);

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				jButton1.setEnabled(false);
				setState(false);
				fillAllComboBox();
				List ls = commonBaseCodeAction
						.findInnerMergedataOrder(new Request(CommonVars
								.getCurrUser()));
				if (ls != null && ls.size() > 0) { // 初始值是空
					obj = (InnerMergeDataOrder) ls.get(0);
					initValues(obj);
				} else {
					restoreInitValues();
				}
			}
		});
	}

	private Integer getInt(Integer i) {
		if (i != null && i < fileColumnCount) {
			return i + 1;
		} else if (i != null && i > fileColumnCount)
			return -1;
		return i;
	}

	private void initValues(InnerMergeDataOrder obj) {
		cbbSerialNo.setSelectedIndex(getInt(obj.getSeqNum()));
		cbbBeforeMaterielCode.setSelectedIndex(getInt(obj.getPtNo()));
		cbbBeforeTenComplexCode.setSelectedIndex(getInt(obj.getTenComplex()));
		cbbBeforeMaterielNameSpec.setSelectedIndex(getInt(obj.getNamespec()));
		jComboBox.setSelectedIndex(getInt(obj.getName()));
		jComboBox1.setSelectedIndex(getInt(obj.getSpec()));
		cbbBeforeLegalUnit.setSelectedIndex(getInt(obj.getFunit()));
		cbbBeforeEnterpriseUnit.setSelectedIndex(getInt(obj.getCalunit()));
		cbbAfterTenMemoNo.setSelectedIndex(getInt(obj.getHsSeqNum()));
		cbbAfterTenComplexCode.setSelectedIndex(getInt(obj.getHsComplex()));
		cbbAfterComplexNameSpec.setSelectedIndex(getInt(obj.getHsnamespec()));
		jComboBox2.setSelectedIndex(getInt(obj.getHsname()));
		jComboBox3.setSelectedIndex(getInt(obj.getHsspec()));
		cbbAfterLegalUnit.setSelectedIndex(getInt(obj.getHsfunit()));
		cbbAfterMemoUnit.setSelectedIndex(getInt(obj.getHsunit()));
		cbbFourNo.setSelectedIndex(getInt(obj.getFourseqnum()));
		cbbFourComplexName.setSelectedIndex(getInt(obj.getFourName()));
		cbbFourComplexCode.setSelectedIndex(getInt(obj.getFourcomplex()));
		jComboBox4.setSelectedIndex(getInt(obj.getUnitWeight()));
		jComboBox5.setSelectedIndex(getInt(obj.getUnitConvert()));
	}

	private void setState(boolean isedit) {
		cbbSerialNo.setEnabled(isedit);
		cbbBeforeMaterielCode.setEnabled(isedit);
		cbbBeforeTenComplexCode.setEnabled(isedit);
		cbbBeforeMaterielNameSpec.setEnabled(isedit);
		cbbBeforeLegalUnit.setEnabled(isedit);
		cbbBeforeEnterpriseUnit.setEnabled(isedit);
		cbbAfterTenMemoNo.setEnabled(isedit);
		cbbAfterTenComplexCode.setEnabled(isedit);
		cbbAfterComplexNameSpec.setEnabled(isedit);
		cbbAfterLegalUnit.setEnabled(isedit);
		cbbAfterMemoUnit.setEnabled(isedit);
		cbbFourNo.setEnabled(isedit);
		cbbFourComplexName.setEnabled(isedit);
		cbbFourComplexCode.setEnabled(isedit);
		jComboBox4.setEnabled(isedit);
		jComboBox5.setEnabled(isedit);
		jButton1.setEnabled(isedit);
		jComboBox.setEnabled(isedit);
		jComboBox1.setEnabled(isedit);
		jComboBox2.setEnabled(isedit);
		jComboBox3.setEnabled(isedit);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel20 = new JLabel();
			jLabel20.setBounds(new Rectangle(461, 68, 235, 158));
			jLabel20
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/importPic.gif")));
			jLabel20.setText("");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.add(getBtnOK(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getBtnRestoreInitValues(), null);
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(jLabel20, null);
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
			jLabel15 = new JLabel();
			jLabel15.setBounds(new java.awt.Rectangle(17, 147, 152, 22));
			jLabel15.setForeground(java.awt.Color.red);
			jLabel15.setText("型号规格");
			jLabel14 = new JLabel();
			jLabel14.setBounds(new java.awt.Rectangle(17, 121, 152, 24));
			jLabel14.setForeground(java.awt.Color.red);
			jLabel14.setText("商品名称");
			jPanel = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel5 = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(18, 4, 338, 231);
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"归并前",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel.setText("序号");
			jLabel.setBounds(17, 20, 151, 21);
			jLabel1.setBounds(17, 45, 151, 21);
			jLabel1.setText("料号");
			jLabel2.setBounds(17, 71, 151, 21);
			jLabel2.setText("10位商品编码");
			jLabel3.setBounds(17, 96, 151, 21);
			jLabel3.setText("商品名称，型号，规格");
			jLabel4.setBounds(16, 174, 151, 21);
			jLabel4.setText("计量单位（法定）");
			jLabel5.setBounds(16, 202, 151, 21);
			jLabel5.setText("计量单位（企业使用）");
			jPanel.add(jLabel, null);
			jPanel.add(getCbbSerialNo(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(getCbbBeforeMaterielCode(), null);
			jPanel.add(getCbbBeforeTenComplexCode(), null);
			jPanel.add(getCbbBeforeMaterielNameSpec(), null);
			jPanel.add(getCbbBeforeLegalUnit(), null);
			jPanel.add(getCbbBeforeEnterpriseUnit(), null);
			jPanel.add(jLabel14, null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(jLabel15, null);
			jPanel.add(getJComboBox1(), null);
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
			jLabel17 = new JLabel();
			jLabel17.setBounds(new java.awt.Rectangle(16, 118, 150, 21));
			jLabel17.setForeground(java.awt.Color.red);
			jLabel17.setText("型号规格");
			jLabel16 = new JLabel();
			jLabel16.setBounds(new java.awt.Rectangle(16, 92, 153, 23));
			jLabel16.setForeground(java.awt.Color.red);
			jLabel16.setText("商品名称");
			jPanel1 = new JPanel();
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel10 = new JLabel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(18, 247, 338, 195);
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "归并后",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel6.setBounds(16, 18, 153, 21);
			jLabel6.setText("备案序号");
			jLabel7.setBounds(16, 44, 153, 21);
			jLabel7.setText("10位商品编码");
			jLabel8.setBounds(16, 69, 153, 21);
			jLabel8.setText("商品名称，规格，型号");
			jLabel9.setBounds(16, 144, 153, 21);
			jLabel9.setText("计量单位（法定）");
			jLabel10.setBounds(16, 168, 153, 21);
			jLabel10.setText("计量单位（备案）");
			jPanel1.add(jLabel6, null);
			jPanel1.add(jLabel7, null);
			jPanel1.add(jLabel8, null);
			jPanel1.add(jLabel9, null);
			jPanel1.add(jLabel10, null);
			jPanel1.add(getCbbAfterTenMemoNo(), null);
			jPanel1.add(getCbbAfterTenComplexCode(), null);
			jPanel1.add(getCbbAfterComplexNameSpec(), null);
			jPanel1.add(getCbbAfterLegalUnit(), null);
			jPanel1.add(getCbbAfterMemoUnit(), null);
			jPanel1.add(jLabel16, null);
			jPanel1.add(jLabel17, null);
			jPanel1.add(getJComboBox2(), null);
			jPanel1.add(getJComboBox3(), null);
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
			jPanel2 = new JPanel();
			javax.swing.JLabel jLabel11 = new JLabel();
			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel13 = new JLabel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(18, 446, 338, 90);
			jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "4位编码",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel11.setBounds(17, 19, 154, 17);
			jLabel11.setText("4位编码序号");
			jLabel12.setBounds(17, 40, 154, 17);
			jLabel12.setText("4位编码商品名称");
			jLabel13.setBounds(17, 64, 154, 17);
			jLabel13.setText("4位商品编码");
			jPanel2.add(jLabel11, null);
			jPanel2.add(jLabel12, null);
			jPanel2.add(jLabel13, null);
			jPanel2.add(getCbbFourNo(), null);
			jPanel2.add(getCbbFourComplexName(), null);
			jPanel2.add(getCbbFourComplexCode(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes cbbSerialNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSerialNo() {
		if (cbbSerialNo == null) {
			cbbSerialNo = new JComboBox();
			cbbSerialNo.setBounds(185, 19, 130, 20);
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
			cbbBeforeMaterielCode.setBounds(185, 47, 130, 20);
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
			cbbBeforeTenComplexCode.setBounds(185, 72, 130, 20);
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
			cbbBeforeMaterielNameSpec.setBounds(185, 98, 130, 20);
			/*
			 * cbbBeforeMaterielNameSpec.addItemListener(new
			 * java.awt.event.ItemListener() { public void
			 * itemStateChanged(java.awt.event.ItemEvent e) {
			 *  } });
			 */
			/*
			 * cbbBeforeMaterielNameSpec .addActionListener(new
			 * java.awt.event.ActionListener() { public void
			 * actionPerformed(java.awt.event.ActionEvent e) { if
			 * (cbbBeforeMaterielNameSpec.getSelectedItem() == null ||
			 * "".equals(cbbBeforeMaterielNameSpec.getSelectedItem().toString().trim())){
			 * isBeforeMerger = false; jComboBox.setEnabled(true);
			 * jComboBox1.setEnabled(true); } else { isBeforeMerger =
			 * true;//是合并到一块 jComboBox.setSelectedItem(" ");
			 * jComboBox1.setSelectedItem(" "); jComboBox.setEnabled(false);
			 * jComboBox1.setEnabled(false); } } });
			 */
		}
		return cbbBeforeMaterielNameSpec;
	}

	/**
	 * This method initializes cbbBeforeLegalUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeLegalUnit() {
		if (cbbBeforeLegalUnit == null) {
			cbbBeforeLegalUnit = new JComboBox();
			cbbBeforeLegalUnit.setBounds(184, 176, 130, 20);
		}
		return cbbBeforeLegalUnit;
	}

	/**
	 * This method initializes cbbBeforeEnterpriseUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeforeEnterpriseUnit() {
		if (cbbBeforeEnterpriseUnit == null) {
			cbbBeforeEnterpriseUnit = new JComboBox();
			cbbBeforeEnterpriseUnit.setBounds(184, 203, 130, 20);
		}
		return cbbBeforeEnterpriseUnit;
	}

	/**
	 * This method initializes cbbAfterTenMemoNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAfterTenMemoNo() {
		if (cbbAfterTenMemoNo == null) {
			cbbAfterTenMemoNo = new JComboBox();
			cbbAfterTenMemoNo.setBounds(185, 17, 130, 20);
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
			cbbAfterTenComplexCode.setBounds(185, 45, 130, 20);
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
			cbbAfterComplexNameSpec.setBounds(185, 71, 130, 20);
			/*
			 * cbbAfterComplexNameSpec.addItemListener(new
			 * java.awt.event.ItemListener() { public void
			 * itemStateChanged(java.awt.event.ItemEvent e) {
			 *  } });
			 */
			/*
			 * cbbAfterComplexNameSpec.addActionListener(new
			 * java.awt.event.ActionListener() { public void
			 * actionPerformed(java.awt.event.ActionEvent e) { if
			 * (cbbAfterComplexNameSpec.getSelectedItem() == null ||
			 * "".equals(cbbAfterComplexNameSpec.getSelectedItem().toString().trim())){
			 * isAfterMerger = false; jComboBox2.setEnabled(true);
			 * jComboBox3.setEnabled(true); } else { isAfterMerger =
			 * true;//是合并到一块 jComboBox2.setSelectedItem("");
			 * jComboBox3.setSelectedItem(""); jComboBox2.setEnabled(false);
			 * jComboBox3.setEnabled(false); } } });
			 */
		}
		return cbbAfterComplexNameSpec;
	}

	/**
	 * This method initializes cbbAfterLegalUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAfterLegalUnit() {
		if (cbbAfterLegalUnit == null) {
			cbbAfterLegalUnit = new JComboBox();
			cbbAfterLegalUnit.setBounds(185, 145, 130, 20);
		}
		return cbbAfterLegalUnit;
	}

	/**
	 * This method initializes cbbAfterMemoUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAfterMemoUnit() {
		if (cbbAfterMemoUnit == null) {
			cbbAfterMemoUnit = new JComboBox();
			cbbAfterMemoUnit.setBounds(185, 168, 130, 20);
		}
		return cbbAfterMemoUnit;
	}

	/**
	 * This method initializes cbbFourNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFourNo() {
		if (cbbFourNo == null) {
			cbbFourNo = new JComboBox();
			cbbFourNo.setBounds(186, 18, 132, 20);
		}
		return cbbFourNo;
	}

	/**
	 * This method initializes cbbFourComplexName
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFourComplexName() {
		if (cbbFourComplexName == null) {
			cbbFourComplexName = new JComboBox();
			cbbFourComplexName.setBounds(186, 40, 132, 20);
		}
		return cbbFourComplexName;
	}

	/**
	 * This method initializes cbbFourComplexCode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFourComplexCode() {
		if (cbbFourComplexCode == null) {
			cbbFourComplexCode = new JComboBox();
			cbbFourComplexCode.setBounds(186, 62, 132, 20);
		}
		return cbbFourComplexCode;
	}

	private void fillComboBox(JComboBox comboBox, int selectedIndex) {
		comboBox.addItem(new ItemProperty(String.valueOf(-1), "  "));
		for (int i = 0; i < fileColumnCount; i++) {
			comboBox.addItem(new ItemProperty(String.valueOf(i), "第" + (i + 1)
					+ "列"));
		}
	}

	private void fillAllComboBox() {
		fillComboBox(this.cbbSerialNo, InnerMergeFileData.SERIAL_NO);
		fillComboBox(this.cbbBeforeMaterielCode,
				InnerMergeFileData.BEFORE_MATERIEL_CODE);
		fillComboBox(this.cbbBeforeTenComplexCode,
				InnerMergeFileData.BEFORE_TEN_COMPLEX_CODE);
		fillComboBox(this.cbbBeforeMaterielNameSpec,
				InnerMergeFileData.BEFORE_MATERIEL_NAME_SPEC);
		fillComboBox(this.cbbBeforeLegalUnit,
				InnerMergeFileData.BEFORE_LEGAL_UNIT);
		fillComboBox(this.cbbBeforeEnterpriseUnit,
				InnerMergeFileData.BEFORE_ENTERPRISE_UNIT);
		fillComboBox(this.cbbAfterTenMemoNo,
				InnerMergeFileData.AFTER_TEN_MEMO_NO);
		fillComboBox(this.cbbAfterTenComplexCode,
				InnerMergeFileData.AFTER_TEN_COMPLEX_CODE);
		fillComboBox(this.cbbAfterComplexNameSpec,
				InnerMergeFileData.AFTER_COMPLEX_NAME_SPEC);
		fillComboBox(this.cbbAfterLegalUnit,
				InnerMergeFileData.AFTER_LEGAL_UNIT);
		fillComboBox(this.cbbAfterMemoUnit, InnerMergeFileData.AFTER_MEMO_UNIT);
		fillComboBox(this.cbbFourNo, InnerMergeFileData.FOUR_NO);
		fillComboBox(this.cbbFourComplexName,
				InnerMergeFileData.FOUR_COMPLEX_NAME);
		fillComboBox(this.cbbFourComplexCode,
				InnerMergeFileData.FOUR_COMPLEX_CODE);
		fillComboBox(this.jComboBox, InnerMergeFileData.Before_Name);
		fillComboBox(this.jComboBox1, InnerMergeFileData.Before_Spec);
		fillComboBox(this.jComboBox2, InnerMergeFileData.After_Name);
		fillComboBox(this.jComboBox3, InnerMergeFileData.After_Spec);
		fillComboBox(this.jComboBox4, InnerMergeFileData.UNIT_WEIGHT); // 单重
		fillComboBox(this.jComboBox5, InnerMergeFileData.UNIT_CONVERT);// 单位折算系数
	}

	// 确定
	private void getSelectedValue() {
		if (selectedValues == null) {
			selectedValues = new Hashtable();
		}
		selectedValues.put(Integer.valueOf(InnerMergeFileData.SERIAL_NO),
				((ItemProperty) this.cbbSerialNo.getSelectedItem()).getCode());

		selectedValues.put(Integer
				.valueOf(InnerMergeFileData.BEFORE_MATERIEL_CODE),
				((ItemProperty) this.cbbBeforeMaterielCode.getSelectedItem())
						.getCode());
		selectedValues.put(Integer
				.valueOf(InnerMergeFileData.BEFORE_TEN_COMPLEX_CODE),
				((ItemProperty) this.cbbBeforeTenComplexCode.getSelectedItem())
						.getCode());
		selectedValues.put(Integer
				.valueOf(InnerMergeFileData.BEFORE_MATERIEL_NAME_SPEC),
				((ItemProperty) this.cbbBeforeMaterielNameSpec
						.getSelectedItem()).getCode());
		selectedValues.put(Integer
				.valueOf(InnerMergeFileData.BEFORE_LEGAL_UNIT),
				((ItemProperty) this.cbbBeforeLegalUnit.getSelectedItem())
						.getCode());
		selectedValues.put(Integer
				.valueOf(InnerMergeFileData.BEFORE_ENTERPRISE_UNIT),
				((ItemProperty) this.cbbBeforeEnterpriseUnit.getSelectedItem())
						.getCode());
		selectedValues.put(Integer
				.valueOf(InnerMergeFileData.AFTER_TEN_MEMO_NO),
				((ItemProperty) this.cbbAfterTenMemoNo.getSelectedItem())
						.getCode());
		selectedValues.put(Integer
				.valueOf(InnerMergeFileData.AFTER_TEN_COMPLEX_CODE),
				((ItemProperty) this.cbbAfterTenComplexCode.getSelectedItem())
						.getCode());
		selectedValues.put(Integer
				.valueOf(InnerMergeFileData.AFTER_COMPLEX_NAME_SPEC),
				((ItemProperty) this.cbbAfterComplexNameSpec.getSelectedItem())
						.getCode());
		selectedValues.put(
				Integer.valueOf(InnerMergeFileData.AFTER_LEGAL_UNIT),
				((ItemProperty) this.cbbAfterLegalUnit.getSelectedItem())
						.getCode());
		selectedValues.put(Integer.valueOf(InnerMergeFileData.AFTER_MEMO_UNIT),
				((ItemProperty) this.cbbAfterMemoUnit.getSelectedItem())
						.getCode());
		selectedValues.put(Integer.valueOf(InnerMergeFileData.FOUR_NO),
				((ItemProperty) this.cbbFourNo.getSelectedItem()).getCode());
		selectedValues.put(Integer
				.valueOf(InnerMergeFileData.FOUR_COMPLEX_NAME),
				((ItemProperty) this.cbbFourComplexName.getSelectedItem())
						.getCode());
		selectedValues.put(Integer
				.valueOf(InnerMergeFileData.FOUR_COMPLEX_CODE),
				((ItemProperty) this.cbbFourComplexCode.getSelectedItem())
						.getCode());
		// ---------------------------------------------------------------
		selectedValues.put(Integer.valueOf(InnerMergeFileData.Before_Name),
				((ItemProperty) this.jComboBox.getSelectedItem()).getCode());
		selectedValues.put(Integer.valueOf(InnerMergeFileData.Before_Spec),
				((ItemProperty) this.jComboBox1.getSelectedItem()).getCode());
		selectedValues.put(Integer.valueOf(InnerMergeFileData.After_Name),
				((ItemProperty) this.jComboBox2.getSelectedItem()).getCode());
		selectedValues.put(Integer.valueOf(InnerMergeFileData.After_Spec),
				((ItemProperty) this.jComboBox3.getSelectedItem()).getCode());
		selectedValues.put(Integer.valueOf(InnerMergeFileData.UNIT_WEIGHT),
				((ItemProperty) this.jComboBox4.getSelectedItem()).getCode());
		selectedValues.put(Integer.valueOf(InnerMergeFileData.UNIT_CONVERT),
				((ItemProperty) this.jComboBox5.getSelectedItem()).getCode());
		// -----------------------------------------------------------------
	}

	private void setComboBoxInitValue(JComboBox comboBox, int selectedIndex) {
		if (selectedIndex > comboBox.getItemCount() - 1) {
			comboBox.setSelectedIndex(0);
		} else {
			comboBox.setSelectedIndex(selectedIndex);
		}
	}

	private void restoreInitValues() {
		// if (selectedValues == null) {
		setComboBoxInitValue(this.cbbSerialNo, InnerMergeFileData.SERIAL_NO);
		setComboBoxInitValue(this.cbbBeforeMaterielCode,
				InnerMergeFileData.BEFORE_MATERIEL_CODE);
		setComboBoxInitValue(this.cbbBeforeTenComplexCode,
				InnerMergeFileData.BEFORE_TEN_COMPLEX_CODE);
		setComboBoxInitValue(this.cbbBeforeMaterielNameSpec,
				InnerMergeFileData.BEFORE_MATERIEL_NAME_SPEC);
		setComboBoxInitValue(this.cbbBeforeLegalUnit,
				InnerMergeFileData.BEFORE_LEGAL_UNIT);
		setComboBoxInitValue(this.cbbBeforeEnterpriseUnit,
				InnerMergeFileData.BEFORE_ENTERPRISE_UNIT);
		setComboBoxInitValue(this.cbbAfterTenMemoNo,
				InnerMergeFileData.AFTER_TEN_MEMO_NO);
		setComboBoxInitValue(this.cbbAfterTenComplexCode,
				InnerMergeFileData.AFTER_TEN_COMPLEX_CODE);
		setComboBoxInitValue(this.cbbAfterComplexNameSpec,
				InnerMergeFileData.AFTER_COMPLEX_NAME_SPEC);
		setComboBoxInitValue(this.cbbAfterLegalUnit,
				InnerMergeFileData.AFTER_LEGAL_UNIT);
		setComboBoxInitValue(this.cbbAfterMemoUnit,
				InnerMergeFileData.AFTER_MEMO_UNIT);
		setComboBoxInitValue(this.cbbFourNo, InnerMergeFileData.FOUR_NO);
		setComboBoxInitValue(this.cbbFourComplexName,
				InnerMergeFileData.FOUR_COMPLEX_NAME);
		setComboBoxInitValue(this.cbbFourComplexCode,
				InnerMergeFileData.FOUR_COMPLEX_CODE);

		setComboBoxInitValue(this.jComboBox4, 15);
		setComboBoxInitValue(this.jComboBox5, 16);
		// ----归并前
		/*
		 * jComboBox.setSelectedItem(" "); jComboBox1.setSelectedItem(" ");
		 * jComboBox.setEnabled(false); jComboBox1.setEnabled(false);
		 */
		// ----归并后
		/*
		 * jComboBox2.setSelectedItem(" "); jComboBox3.setSelectedItem(" ");
		 * jComboBox2.setEnabled(false); jComboBox3.setEnabled(false);
		 */
		// } else {
		/*
		 * this.cbbSerialNo.setSelectedIndex(ItemProperty.getIndexByCode(
		 * selectedValues.get( Integer.valueOf(InnerMergeFileData.SERIAL_NO))
		 * .toString(), this.cbbSerialNo)); this.cbbBeforeMaterielCode
		 * .setSelectedIndex(ItemProperty .getIndexByCode( selectedValues .get(
		 * Integer .valueOf(InnerMergeFileData.BEFORE_MATERIEL_CODE))
		 * .toString(), this.cbbBeforeMaterielCode));
		 * this.cbbBeforeTenComplexCode .setSelectedIndex(ItemProperty
		 * .getIndexByCode( selectedValues .get( Integer
		 * .valueOf(InnerMergeFileData.BEFORE_TEN_COMPLEX_CODE)) .toString(),
		 * this.cbbBeforeTenComplexCode)); this.cbbBeforeMaterielNameSpec
		 * .setSelectedIndex(ItemProperty .getIndexByCode( selectedValues .get(
		 * Integer .valueOf(InnerMergeFileData.BEFORE_MATERIEL_NAME_SPEC))
		 * .toString(), this.cbbBeforeMaterielNameSpec));
		 * this.cbbBeforeLegalUnit .setSelectedIndex(ItemProperty
		 * .getIndexByCode( selectedValues .get( Integer
		 * .valueOf(InnerMergeFileData.BEFORE_LEGAL_UNIT)) .toString(),
		 * this.cbbBeforeLegalUnit)); this.cbbBeforeEnterpriseUnit
		 * .setSelectedIndex(ItemProperty .getIndexByCode( selectedValues .get(
		 * Integer .valueOf(InnerMergeFileData.BEFORE_ENTERPRISE_UNIT))
		 * .toString(), this.cbbBeforeEnterpriseUnit)); this.cbbAfterTenMemoNo
		 * .setSelectedIndex(ItemProperty .getIndexByCode( selectedValues .get(
		 * Integer .valueOf(InnerMergeFileData.AFTER_TEN_MEMO_NO)) .toString(),
		 * this.cbbAfterTenMemoNo)); this.cbbAfterTenComplexCode
		 * .setSelectedIndex(ItemProperty .getIndexByCode( selectedValues .get(
		 * Integer .valueOf(InnerMergeFileData.AFTER_TEN_COMPLEX_CODE))
		 * .toString(), this.cbbAfterTenComplexCode));
		 * this.cbbAfterComplexNameSpec .setSelectedIndex(ItemProperty
		 * .getIndexByCode( selectedValues .get( Integer
		 * .valueOf(InnerMergeFileData.AFTER_COMPLEX_NAME_SPEC)) .toString(),
		 * this.cbbAfterComplexNameSpec)); this.cbbAfterLegalUnit
		 * .setSelectedIndex(ItemProperty .getIndexByCode( selectedValues .get(
		 * Integer .valueOf(InnerMergeFileData.AFTER_LEGAL_UNIT)) .toString(),
		 * this.cbbAfterLegalUnit)); this.cbbAfterMemoUnit
		 * .setSelectedIndex(ItemProperty .getIndexByCode( selectedValues .get(
		 * Integer .valueOf(InnerMergeFileData.AFTER_MEMO_UNIT)) .toString(),
		 * this.cbbAfterMemoUnit));
		 * this.cbbFourNo.setSelectedIndex(ItemProperty.getIndexByCode(
		 * selectedValues.get( Integer.valueOf(InnerMergeFileData.FOUR_NO))
		 * .toString(),
		 * 
		 * this.cbbFourNo)); this.cbbFourComplexName
		 * .setSelectedIndex(ItemProperty .getIndexByCode( selectedValues .get(
		 * Integer .valueOf(InnerMergeFileData.FOUR_COMPLEX_NAME)) .toString(),
		 * this.cbbFourComplexName)); this.cbbFourComplexCode
		 * .setSelectedIndex(ItemProperty .getIndexByCode( selectedValues .get(
		 * Integer .valueOf(InnerMergeFileData.FOUR_COMPLEX_CODE)) .toString(),
		 * this.cbbFourComplexCode));
		 * 
		 * this.jComboBox.setSelectedIndex(ItemProperty.getIndexByCode(
		 * selectedValues.get(Integer.valueOf(InnerMergeFileData.Before_Name)).toString(),
		 * this.jComboBox));
		 * this.jComboBox1.setSelectedIndex(ItemProperty.getIndexByCode(
		 * selectedValues.get(Integer.valueOf(InnerMergeFileData.Before_Spec)).toString(),
		 * this.jComboBox1));
		 * this.jComboBox2.setSelectedIndex(ItemProperty.getIndexByCode(
		 * selectedValues.get(Integer.valueOf(InnerMergeFileData.After_Name)).toString(),
		 * this.jComboBox2));
		 * this.jComboBox3.setSelectedIndex(ItemProperty.getIndexByCode(
		 * selectedValues.get(Integer.valueOf(InnerMergeFileData.After_Spec)).toString(),
		 * this.jComboBox3));
		 * this.jComboBox4.setSelectedIndex(ItemProperty.getIndexByCode(
		 * selectedValues.get(Integer.valueOf(InnerMergeFileData.UNIT_WEIGHT)).toString(),
		 * this.jComboBox4));
		 * this.jComboBox5.setSelectedIndex(ItemProperty.getIndexByCode(
		 * selectedValues.get(Integer.valueOf(InnerMergeFileData.UNIT_CONVERT)).toString(),
		 * this.jComboBox5));
		 */

		// }
	}

	/**
	 * This method initializes btnOK
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(363, 16, 95, 27);
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// 保存格式
					// insertOrder(); //确定，不保存
					setState(false);
					jButton.setEnabled(true);
					jButton1.setEnabled(false);
					// 获取
					getSelectedValue();
					DgCorrespondFile.this.dispose();
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
			btnCancel.setBounds(363, 98, 95, 27);
			btnCancel.setText("退出");
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
			btnRestoreInitValues.setBounds(363, 57, 95, 27);
			btnRestoreInitValues.setText("恢复默认值");
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
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new java.awt.Rectangle(185, 122, 130, 20));
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
			jComboBox1.setBounds(new java.awt.Rectangle(185, 150, 130, 20));
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
			jComboBox2.setBounds(new java.awt.Rectangle(185, 95, 130, 20));
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
			jComboBox3.setBounds(new java.awt.Rectangle(185, 121, 130, 20));
		}
		return jComboBox3;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel19 = new JLabel();
			jLabel19.setBounds(new java.awt.Rectangle(40, 51, 124, 18));
			jLabel19.setText("单位折算系数");
			jLabel18 = new JLabel();
			jLabel18.setBounds(new java.awt.Rectangle(40, 26, 124, 18));
			jLabel18.setText("单重");
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setBounds(new java.awt.Rectangle(367, 246, 317, 289));
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "扩展导入",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
					java.awt.Color.black));
			jPanel3.add(jLabel18, null);
			jPanel3.add(getJComboBox4(), null);
			jPanel3.add(jLabel19, null);
			jPanel3.add(getJComboBox5(), null);
			jPanel3.add(getJButton(), null);
			jPanel3.add(getJButton1(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jComboBox4
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox4() {
		if (jComboBox4 == null) {
			jComboBox4 = new JComboBox();
			jComboBox4.setBounds(new java.awt.Rectangle(171, 25, 130, 20));
		}
		return jComboBox4;
	}

	/**
	 * This method initializes jComboBox5
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox5() {
		if (jComboBox5 == null) {
			jComboBox5 = new JComboBox();
			jComboBox5.setBounds(new java.awt.Rectangle(171, 51, 130, 20));
		}
		return jComboBox5;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(14, 131, 85, 28));
			jButton.setText("修改格式");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setState(true);
					jButton.setEnabled(false);
					jButton1.setEnabled(true);
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
			jButton1.setBounds(new java.awt.Rectangle(120, 131, 85, 28));
			jButton1.setText("保存格式");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					insertOrder();
					setState(false);
					jButton.setEnabled(true);
					jButton1.setEnabled(false);
				}
			});
		}
		return jButton1;
	}

	private void insertOrder() {
		if (obj == null) {
			obj = new InnerMergeDataOrder();
		}
		if (cbbSerialNo.getSelectedItem() != null
				&& !cbbSerialNo.getSelectedItem().equals("")) {
			obj.setSeqNum(Integer.valueOf(((ItemProperty) this.cbbSerialNo
					.getSelectedItem()).getCode()));
		}
		if (cbbBeforeMaterielCode.getSelectedItem() != null
				&& !cbbBeforeMaterielCode.getSelectedItem().equals("")) {
			obj.setPtNo(Integer
					.valueOf(((ItemProperty) this.cbbBeforeMaterielCode
							.getSelectedItem()).getCode()));
		}
		if (cbbBeforeTenComplexCode.getSelectedItem() != null
				&& !cbbBeforeTenComplexCode.getSelectedItem().equals("")) {
			obj.setTenComplex(Integer
					.valueOf(((ItemProperty) this.cbbBeforeTenComplexCode
							.getSelectedItem()).getCode()));
		}
		if (cbbBeforeMaterielNameSpec.getSelectedItem() != null
				&& !cbbBeforeMaterielNameSpec.getSelectedItem().equals("")) {
			obj.setNamespec(Integer
					.valueOf(((ItemProperty) this.cbbBeforeMaterielNameSpec
							.getSelectedItem()).getCode()));
		}
		if (jComboBox.getSelectedItem() != null
				&& !jComboBox.getSelectedItem().equals("")) {
			obj.setName(Integer.valueOf(((ItemProperty) this.jComboBox
					.getSelectedItem()).getCode()));
		}
		if (jComboBox1.getSelectedItem() != null
				&& !jComboBox1.getSelectedItem().equals("")) {
			obj.setSpec(Integer.valueOf(((ItemProperty) this.jComboBox1
					.getSelectedItem()).getCode()));
		}
		if (cbbBeforeLegalUnit.getSelectedItem() != null
				&& !cbbBeforeLegalUnit.getSelectedItem().equals("")) {
			obj.setFunit(Integer
					.valueOf(((ItemProperty) this.cbbBeforeLegalUnit
							.getSelectedItem()).getCode()));
		}
		if (cbbBeforeEnterpriseUnit.getSelectedItem() != null
				&& !cbbBeforeEnterpriseUnit.getSelectedItem().equals("")) {
			obj.setCalunit(Integer
					.valueOf(((ItemProperty) this.cbbBeforeEnterpriseUnit
							.getSelectedItem()).getCode()));
		}
		if (cbbAfterTenMemoNo.getSelectedItem() != null
				&& !cbbAfterTenMemoNo.getSelectedItem().equals("")) {
			obj.setHsSeqNum(Integer
					.valueOf(((ItemProperty) this.cbbAfterTenMemoNo
							.getSelectedItem()).getCode()));
		}
		if (cbbAfterTenComplexCode.getSelectedItem() != null
				&& !cbbAfterTenComplexCode.getSelectedItem().equals("")) {
			obj.setHsComplex(Integer
					.valueOf(((ItemProperty) this.cbbAfterTenComplexCode
							.getSelectedItem()).getCode()));
		}
		if (cbbAfterComplexNameSpec.getSelectedItem() != null
				&& !cbbAfterComplexNameSpec.getSelectedItem().equals("")) {
			obj.setHsnamespec(Integer
					.valueOf(((ItemProperty) this.cbbAfterComplexNameSpec
							.getSelectedItem()).getCode()));
		}
		if (jComboBox2.getSelectedItem() != null
				&& !jComboBox2.getSelectedItem().equals("")) {
			obj.setHsname(Integer.valueOf(((ItemProperty) this.jComboBox2
					.getSelectedItem()).getCode()));
		}
		if (jComboBox3.getSelectedItem() != null
				&& !jComboBox3.getSelectedItem().equals("")) {
			obj.setHsspec(Integer.valueOf(((ItemProperty) this.jComboBox3
					.getSelectedItem()).getCode()));
		}
		if (cbbAfterLegalUnit.getSelectedItem() != null
				&& !cbbAfterLegalUnit.getSelectedItem().equals("")) {
			obj.setHsfunit(Integer
					.valueOf(((ItemProperty) this.cbbAfterLegalUnit
							.getSelectedItem()).getCode()));
		}
		if (cbbAfterMemoUnit.getSelectedItem() != null
				&& !cbbAfterMemoUnit.getSelectedItem().equals("")) {
			obj.setHsunit(Integer.valueOf(((ItemProperty) this.cbbAfterMemoUnit
					.getSelectedItem()).getCode()));
		}
		if (cbbFourNo.getSelectedItem() != null
				&& !cbbFourNo.getSelectedItem().equals("")) {
			obj.setFourseqnum(Integer.valueOf(((ItemProperty) this.cbbFourNo
					.getSelectedItem()).getCode()));
		}
		if (cbbFourComplexName.getSelectedItem() != null
				&& !cbbFourComplexName.getSelectedItem().equals("")) {
			obj.setFourName(Integer
					.valueOf(((ItemProperty) this.cbbFourComplexName
							.getSelectedItem()).getCode()));
		}
		if (cbbFourComplexCode.getSelectedItem() != null
				&& !cbbFourComplexCode.getSelectedItem().equals("")) {
			obj.setFourcomplex(Integer
					.valueOf(((ItemProperty) this.cbbFourComplexCode
							.getSelectedItem()).getCode()));
		}
		if (jComboBox4.getSelectedItem() != null
				&& !jComboBox4.getSelectedItem().equals("")) {
			obj.setUnitWeight(Integer.valueOf(((ItemProperty) this.jComboBox4
					.getSelectedItem()).getCode()));
		}
		if (jComboBox5.getSelectedItem() != null
				&& !jComboBox5.getSelectedItem().equals("")) {
			obj.setUnitConvert(Integer.valueOf(((ItemProperty) this.jComboBox5
					.getSelectedItem()).getCode()));
		}
		obj = commonBaseCodeAction.saveInnerOrder(new Request(CommonVars
				.getCurrUser()), obj);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
