package com.bestway.bcus.client.cas.otherreport;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMergeFileData;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;

public class DgBalanceInfoCorrespondFile extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JComboBox cbbF1 = null;

	private JComboBox cbbF2 = null;

	private JComboBox cbbF3 = null;

	private JComboBox cbbF4 = null;

	private JComboBox cbbF5 = null;

	private int fileColumnCount = 0;

	private Hashtable<Integer, String> selectedValues = null; // @jve:decl-index=0:

	private JButton btnOK = null;

	private JButton btnCancel = null;

	private JButton btnRestoreInitValues = null;

	private JComboBox cbbNameSpecUnitName = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel5 = null;

	private JComboBox cbbF6 = null;

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
	public DgBalanceInfoCorrespondFile() {
		super();
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
		this.setSize(405, 323);
	}

	public void setVisible(boolean b){
		if(b){
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
			jContentPane.add(getBtnOK(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getBtnRestoreInitValues(), null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(getCbbNameSpecUnitName(), null);
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
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(17, 150, 170, 21));
			jLabel5.setText("3A.成品折原料");
			jLabel10 = new JLabel();
			jLabel10.setText("料件/规格/单位");
			jLabel10.setBounds(new Rectangle(35, 23, 170, 21));
			jPanel = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(17, 47, 360, 179);
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"保税货物盘点库存",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel.setText("1.库存原料");
			jLabel.setBounds(17, 20, 170, 21);
			jLabel1.setBounds(17, 45, 170, 21);
			jLabel1.setText("2.半成品、成品折原料");
			jLabel2.setBounds(17, 71, 170, 21);
			jLabel2.setText("3.库存原材料合计");
			jLabel3.setBounds(17, 96, 170, 21);
			jLabel3.setText("1A.库存原料");
			jLabel4.setBounds(17, 123, 170, 21);
			jLabel4.setText("2A.半成品折原料");
			jPanel.add(jLabel, null);
			jPanel.add(getCbbF1(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(getCbbF2(), null);
			jPanel.add(getCbbF3(), null);
			jPanel.add(getCbbF4(), null);
			jPanel.add(getCbbF5(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getCbbF6(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes cbbSerialNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbF1() {
		if (cbbF1 == null) {
			cbbF1 = new JComboBox();
			cbbF1.setBounds(193, 19, 130, 20);
		}
		return cbbF1;
	}

	/**
	 * This method initializes cbbBeforeMaterielCode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbF2() {
		if (cbbF2 == null) {
			cbbF2 = new JComboBox();
			cbbF2.setBounds(193, 47, 130, 20);
		}
		return cbbF2;
	}

	/**
	 * This method initializes cbbBeforeTenComplexCode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbF3() {
		if (cbbF3 == null) {
			cbbF3 = new JComboBox();
			cbbF3.setBounds(193, 72, 130, 20);
		}
		return cbbF3;
	}

	/**
	 * This method initializes cbbBeforeMaterielNameSpec
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbF4() {
		if (cbbF4 == null) {
			cbbF4 = new JComboBox();
			cbbF4.setBounds(193, 98, 130, 20);
		}
		return cbbF4;
	}

	/**
	 * This method initializes cbbBeforeLegalUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbF5() {
		if (cbbF5 == null) {
			cbbF5 = new JComboBox();
			cbbF5.setBounds(193, 125, 130, 20);
		}
		return cbbF5;
	}

	private void fillComboBox(JComboBox comboBox, int selectedIndex) {
		comboBox.addItem(new ItemProperty(String.valueOf(0), "  "));
		for (int i = 0; i < fileColumnCount; i++) {
			comboBox.addItem(new ItemProperty(String.valueOf(i+1), "第" + (i + 1)
					+ "列"));
		}
		// comboBox.setRenderer(CustomBaseRender.getInstance()
		// .getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(comboBox);
	}

	private void fillAllComboBox() {
		fillComboBox(this.cbbNameSpecUnitName, 1);
		fillComboBox(this.cbbF1, 2);
		fillComboBox(this.cbbF2, 3);
		fillComboBox(this.cbbF3, 4);
		fillComboBox(this.cbbF4, 5);
		fillComboBox(this.cbbF5, 6);
		fillComboBox(this.cbbF6, 7);
		
	}

	private void getSelectedValue() {
		if (selectedValues == null) {
			selectedValues = new Hashtable<Integer, String>();
		}
		selectedValues.put(Integer.valueOf(1),
				getComboBoxSelectValue( this.cbbNameSpecUnitName));		
		selectedValues.put(Integer.valueOf(2),
				getComboBoxSelectValue(this.cbbF1));
		selectedValues.put(Integer.valueOf(3),
				getComboBoxSelectValue(this.cbbF2));
		selectedValues.put(Integer.valueOf(4),
				getComboBoxSelectValue( this.cbbF3));
		selectedValues.put(Integer.valueOf(5),
				getComboBoxSelectValue(this.cbbF4));
		selectedValues.put(Integer.valueOf(6),
				getComboBoxSelectValue(this.cbbF5));
		selectedValues.put(Integer.valueOf(7),
				getComboBoxSelectValue(this.cbbF6));		
		
	}

	private String getComboBoxSelectValue(JComboBox comboBox) {
//		return ((ItemProperty) comboBox.getSelectedItem())
//		.getCode()
		if(comboBox.getSelectedItem()==null){
			return "-1";
		}
		return String.valueOf(Integer.valueOf(((ItemProperty) comboBox
				.getSelectedItem()).getCode()) - 1);
	}

	private void setComboBoxInitValue(JComboBox comboBox, int selectedIndex) {
		if (selectedIndex > comboBox.getItemCount() - 1) {
			comboBox.setSelectedIndex(0);
		} else {
			comboBox.setSelectedIndex(selectedIndex);
		}
	}

	private void restoreInitValues() {
		if (selectedValues == null) {
			setComboBoxInitValue(this.cbbNameSpecUnitName,
					1);			
			setComboBoxInitValue(this.cbbF1,
					2);
			setComboBoxInitValue(this.cbbF2,
					3);
			setComboBoxInitValue(this.cbbF3,
					4);
			setComboBoxInitValue(this.cbbF4,
					5);
			setComboBoxInitValue(this.cbbF5,
					6);
			setComboBoxInitValue(this.cbbF6,
					7);			
			
		} else {
			this.cbbNameSpecUnitName
					.setSelectedIndex(ItemProperty.getIndexByCode(
					selectedValues.get(
							Integer.valueOf(1))
							.toString(), this.cbbNameSpecUnitName));			
			this.cbbF1.setSelectedIndex(ItemProperty.getIndexByCode(
					selectedValues.get(
							Integer.valueOf(2))
							.toString(), this.cbbF1));
			this.cbbF2
					.setSelectedIndex(ItemProperty.getIndexByCode(
					selectedValues.get(
							Integer.valueOf(3))
							.toString(),this.cbbF2));
			this.cbbF3
					.setSelectedIndex(ItemProperty.getIndexByCode(
					selectedValues.get(
							Integer.valueOf(4))
							.toString(),this.cbbF3));
			this.cbbF4
					.setSelectedIndex(ItemProperty.getIndexByCode(
					selectedValues.get(
							Integer.valueOf(5))
							.toString(),this.cbbF4));
			this.cbbF5
					.setSelectedIndex(ItemProperty.getIndexByCode(
					selectedValues.get(
							Integer.valueOf(6))
							.toString(), this.cbbF5));
			this.cbbF6
			.setSelectedIndex(ItemProperty.getIndexByCode(
			selectedValues.get(
					Integer.valueOf(7))
					.toString(), this.cbbF6));			
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
			btnOK.setBounds(35, 249, 97, 25);
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
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
			btnCancel.setBounds(137, 249, 97, 25);
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
			btnRestoreInitValues.setBounds(239, 249, 97, 25);
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
	private JComboBox getCbbNameSpecUnitName() {
		if (cbbNameSpecUnitName == null) {
			cbbNameSpecUnitName = new JComboBox();
			cbbNameSpecUnitName.setBounds(new Rectangle(211, 24, 130, 20));
		}
		return cbbNameSpecUnitName;
	}

	/**
	 * This method initializes cbbF6	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF6() {
		if (cbbF6 == null) {
			cbbF6 = new JComboBox();
			cbbF6.setBounds(new Rectangle(193, 151, 130, 20));
		}
		return cbbF6;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
