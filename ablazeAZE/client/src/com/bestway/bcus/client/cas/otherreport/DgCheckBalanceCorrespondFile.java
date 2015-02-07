package com.bestway.bcus.client.cas.otherreport;

import java.awt.Rectangle;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

public class DgCheckBalanceCorrespondFile extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JComboBox cbbF1 = null;

	private JComboBox cbbF2 = null;

	private JComboBox cbbF3 = null;

	private JComboBox cbbF4 = null;

	private int fileColumnCount = 0;

	private Hashtable<Integer, String> selectedValues = null; // @jve:decl-index=0:

	private JButton btnOK = null;

	private JButton btnCancel = null;

	private JButton btnRestoreInitValues = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JComboBox jComboBox = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JComboBox jComboBox1 = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel71 = null;

	private JComboBox jComboBox11 = null;

//	private JLabel jLabel9 = null;

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
	public DgCheckBalanceCorrespondFile() {
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
		this.setSize(424, 411);
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
			jLabel71 = new JLabel();
			jLabel71.setBounds(new Rectangle(17, 179, 169, 24));
			jLabel71.setText("7.BOM版本号");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(90, 269, 279, 24));
			jLabel8.setText(" 4表示外发未收回，5残次品库存，6边角料");
			jLabel8.setForeground(java.awt.Color.red);
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(17,150,169,24));
			jLabel7.setText("6.库存类别");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(5, 247, 363, 23));
			jLabel6.setForeground(java.awt.Color.red);
			jLabel6.setText("库存类别定义：  0表示料件库存，1表示成品库存，2表示在产品库存");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(5, 218, 319, 18));
			jLabel5.setForeground(java.awt.Color.red);
			jLabel5.setText("物料标记定义：  0表示料件，1表示成品，2表示半成品");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(17,124,170,23));
			jLabel4.setText("5.物料标记");
			jPanel = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(17, 14, 386, 324);
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"保税货物盘点库存",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel.setText("1.盘点时间(格式:YYYY-MM-DD)");
			jLabel.setBounds(17, 20, 170, 21);
			jLabel1.setBounds(17, 45, 170, 21);
			jLabel1.setText("2.工厂料号");
			jLabel2.setBounds(17, 71, 170, 21);
			jLabel2.setText("3.仓库(填写系统中存在的名称)");
			jLabel3.setBounds(17, 96, 170, 21);
			jLabel3.setText("4.库存数量");
			jPanel.add(jLabel, null);
			jPanel.add(getCbbF1(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(getCbbF2(), null);
			jPanel.add(getCbbF3(), null);
			jPanel.add(getCbbF4(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(jLabel7, null);
			jPanel.add(getJComboBox1(), null);
			jPanel.add(jLabel8, null);
			jPanel.add(jLabel71, null);
			jPanel.add(getJComboBox11(), null);
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
			cbbF1.setBounds(192, 19, 130, 20);
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
			cbbF2.setBounds(192, 47, 130, 20);
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
			cbbF3.setBounds(192, 72, 130, 20);
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
			cbbF4.setBounds(192, 98, 130, 20);
		}
		return cbbF4;
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
		fillComboBox(this.cbbF1, 1);
		fillComboBox(this.cbbF2, 2);
		fillComboBox(this.cbbF3, 3);
		fillComboBox(this.cbbF4, 4);
		fillComboBox(this.jComboBox, 5);
		fillComboBox(this.jComboBox1, 6);
		fillComboBox(this.jComboBox11, 7);
		
	}

	private void getSelectedValue() {
		if (selectedValues == null) {
			selectedValues = new Hashtable<Integer, String>();
		}	
		selectedValues.put(Integer.valueOf(0),
				getComboBoxSelectValue(this.cbbF1));
		selectedValues.put(Integer.valueOf(1),
				getComboBoxSelectValue(this.cbbF2));
		selectedValues.put(Integer.valueOf(2),
				getComboBoxSelectValue( this.cbbF3));
		selectedValues.put(Integer.valueOf(3),
				getComboBoxSelectValue(this.cbbF4));	
		selectedValues.put(Integer.valueOf(4),
				getComboBoxSelectValue(this.jComboBox));	
		selectedValues.put(Integer.valueOf(5),
				getComboBoxSelectValue(this.jComboBox1));
		selectedValues.put(Integer.valueOf(6),
				getComboBoxSelectValue(this.jComboBox11));
		
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
			setComboBoxInitValue(this.cbbF1,
					1);
			setComboBoxInitValue(this.cbbF2,
					2);
			setComboBoxInitValue(this.cbbF3,
					3);
			setComboBoxInitValue(this.cbbF4,
					4);	
			setComboBoxInitValue(this.jComboBox,
					5);	
			setComboBoxInitValue(this.jComboBox1,
					6);	
			setComboBoxInitValue(this.jComboBox11,
					7);	
			
		} else {			
			this.cbbF1.setSelectedIndex(ItemProperty.getIndexByCode(
					selectedValues.get(
							Integer.valueOf(0))
							.toString(), this.cbbF1)+1);
			this.cbbF2
					.setSelectedIndex(ItemProperty.getIndexByCode(
					selectedValues.get(
							Integer.valueOf(1))
							.toString(),this.cbbF2)+1);
			this.cbbF3
					.setSelectedIndex(ItemProperty.getIndexByCode(
					selectedValues.get(
							Integer.valueOf(2))
							.toString(),this.cbbF3)+1);
			this.cbbF4
					.setSelectedIndex(ItemProperty.getIndexByCode(
					selectedValues.get(
							Integer.valueOf(3))
							.toString(),this.cbbF4)+1);		
			this.jComboBox.setSelectedIndex(ItemProperty.getIndexByCode(
			          selectedValues.get(
					Integer.valueOf(4))
					.toString(),this.jComboBox)+1);
			this.jComboBox1.setSelectedIndex(ItemProperty.getIndexByCode(
			          selectedValues.get(
					Integer.valueOf(5))
					.toString(),this.jComboBox1)+1);
			this.jComboBox11.setSelectedIndex(ItemProperty.getIndexByCode(
			          selectedValues.get(
					Integer.valueOf(6))
					.toString(),this.jComboBox11)+1);
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
			btnOK.setBounds(33, 345, 97, 25);
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
			btnCancel.setBounds(135, 345, 97, 25);
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
			btnRestoreInitValues.setBounds(238, 345, 97, 25);
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
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new java.awt.Rectangle(192,125,130,20));
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
			jComboBox1.setBounds(new Rectangle(191, 151, 130, 21));
		}
		return jComboBox1;
	}

	/**
	 * This method initializes jComboBox11	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox11() {
		if (jComboBox11 == null) {
			jComboBox11 = new JComboBox();
			jComboBox11.setBounds(new Rectangle(191, 179, 130, 21));
		}
		return jComboBox11;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
