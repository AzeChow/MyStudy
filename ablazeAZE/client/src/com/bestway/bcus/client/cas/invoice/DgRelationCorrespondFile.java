package com.bestway.bcus.client.cas.invoice;

import java.awt.Rectangle;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.common.MaterielType;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;

public class DgRelationCorrespondFile extends JDialogBase {

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

	private JLabel jLabel5 = null;

	private JComboBox cbbF6 = null;

	private JLabel jLabel6 = null;

	private JComboBox cbbF7 = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel12 = null;

	private JLabel jLabel13 = null;

	private JComboBox cbbF8 = null;

	private JComboBox cbbF9 = null;

	private JComboBox cbbF10 = null;

	private JComboBox cbbF11 = null;

	private JComboBox cbbF12 = null;

	private JComboBox cbbF13 = null;
	
	private final String tableName = "CasInvoice";//国内购买发票模块字段设置

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
	public DgRelationCorrespondFile() {
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
		this.setSize(624, 284);
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
			jContentPane.add(getJPanel1(), null);
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
			jLabel6 = new JLabel();
			jLabel6.setText("7.发票名称");
			jLabel6.setBounds(new Rectangle(12, 47, 112, 21));
			jLabel5 = new JLabel();
			jLabel5.setText("6.商品编码");
			jLabel5.setBounds(new Rectangle(12, 19, 112, 21));
			jPanel = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(14, 11, 297, 159);
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"发票单据头",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel.setText("1.发票号码");
			jLabel.setBounds(17, 20, 143, 21);
			jLabel1.setBounds(17, 45, 143, 21);
			jLabel1.setText("2.发票日期(YYYY-MM-DD)");
			jLabel2.setBounds(17, 71, 143, 21);
			jLabel2.setText("3.客户供应商");
			jLabel3.setBounds(17, 96, 143, 21);
			jLabel3.setText("4.手册号");
			jLabel4.setBounds(17, 123, 143, 21);
			jLabel4.setText("5.是否核销(0为否,1为是)");
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
			cbbF1.setBounds(163, 20, 114, 20);
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
			cbbF2.setBounds(163, 46, 114, 20);
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
			cbbF3.setBounds(163, 71, 114, 20);
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
			cbbF4.setBounds(163, 97, 114, 20);
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
			cbbF5.setBounds(163, 124, 114, 20);
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
		fillComboBox(this.cbbF1, 1);
		fillComboBox(this.cbbF2, 2);
		fillComboBox(this.cbbF3, 3);
		fillComboBox(this.cbbF4, 4);
		fillComboBox(this.cbbF5, 5);
		fillComboBox(this.cbbF6, 6);
		fillComboBox(this.cbbF7, 7);
		fillComboBox(this.cbbF8, 8);
		fillComboBox(this.cbbF9, 9);
		fillComboBox(this.cbbF10, 10);
		fillComboBox(this.cbbF11, 11);
		fillComboBox(this.cbbF12, 12);
		fillComboBox(this.cbbF13, 13);
	}

	private void getSelectedValue() {
		if (selectedValues == null) {
			selectedValues = new Hashtable<Integer, String>();
		}	
		selectedValues.put(Integer.valueOf(1),
				getComboBoxSelectValue(this.cbbF1));
		selectedValues.put(Integer.valueOf(2),
				getComboBoxSelectValue(this.cbbF2));
		selectedValues.put(Integer.valueOf(3),
				getComboBoxSelectValue( this.cbbF3));
		selectedValues.put(Integer.valueOf(4),
				getComboBoxSelectValue(this.cbbF4));
		selectedValues.put(Integer.valueOf(5),
				getComboBoxSelectValue(this.cbbF5));
		selectedValues.put(Integer.valueOf(6),
				getComboBoxSelectValue(this.cbbF6));
		selectedValues.put(Integer.valueOf(7),
				getComboBoxSelectValue(this.cbbF7));
		selectedValues.put(Integer.valueOf(8),
				getComboBoxSelectValue(this.cbbF8));
		selectedValues.put(Integer.valueOf(9),
				getComboBoxSelectValue(this.cbbF9));
		selectedValues.put(Integer.valueOf(10),
				getComboBoxSelectValue(this.cbbF10));
		selectedValues.put(Integer.valueOf(11),
				getComboBoxSelectValue(this.cbbF11));
		selectedValues.put(Integer.valueOf(12),
				getComboBoxSelectValue(this.cbbF12));
		selectedValues.put(Integer.valueOf(13),
				getComboBoxSelectValue(this.cbbF13));
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
			setComboBoxInitValue(this.cbbF5,
					5);
			setComboBoxInitValue(this.cbbF6,
					6);
			setComboBoxInitValue(this.cbbF7,
					7);
			setComboBoxInitValue(this.cbbF8,
					8);
			setComboBoxInitValue(this.cbbF9,
					9);
			setComboBoxInitValue(this.cbbF10,
					10);
			setComboBoxInitValue(this.cbbF11,
					11);
			setComboBoxInitValue(this.cbbF12,
					12);
			setComboBoxInitValue(this.cbbF13,
					13);
		} else {			
			this.cbbF1.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf((Integer.parseInt(selectedValues.get(
							Integer.valueOf(1)))+1)),
							this.cbbF1));
			this.cbbF2
					.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf((Integer.parseInt(selectedValues.get(
							Integer.valueOf(2)))+1)),
							this.cbbF2));
			this.cbbF3
					.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf((Integer.parseInt(selectedValues.get(
							Integer.valueOf(3)))+1)),
							this.cbbF3));
			this.cbbF4
					.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf((Integer.parseInt(selectedValues.get(
							Integer.valueOf(4)))+1)),
							this.cbbF4));
			this.cbbF5
					.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf((Integer.parseInt(selectedValues.get(
							Integer.valueOf(5)))+1)), 
							this.cbbF5));
			this.cbbF6
			.setSelectedIndex(ItemProperty.getIndexByCode(
			String.valueOf((Integer.parseInt(selectedValues.get(
					Integer.valueOf(6)))+1)),
					 this.cbbF6));
			this.cbbF7
			.setSelectedIndex(ItemProperty.getIndexByCode(
			String.valueOf((Integer.parseInt(selectedValues.get(
					Integer.valueOf(7)))+1)),
					 this.cbbF7));
			this.cbbF8
			.setSelectedIndex(ItemProperty.getIndexByCode(
			String.valueOf((Integer.parseInt(selectedValues.get(
					Integer.valueOf(8)))+1)),
					 this.cbbF8));
			this.cbbF9
			.setSelectedIndex(ItemProperty.getIndexByCode(
			String.valueOf((Integer.parseInt(selectedValues.get(
					Integer.valueOf(9)))+1)),
					 this.cbbF9));
			this.cbbF10
			.setSelectedIndex(ItemProperty.getIndexByCode(
			String.valueOf((Integer.parseInt(selectedValues.get(
					Integer.valueOf(10)))+1)),
					 this.cbbF10));
			this.cbbF11
			.setSelectedIndex(ItemProperty.getIndexByCode(
			String.valueOf((Integer.parseInt(selectedValues.get(
					Integer.valueOf(11)))+1)),
					 this.cbbF11));
			this.cbbF12
			.setSelectedIndex(ItemProperty.getIndexByCode(
			String.valueOf((Integer.parseInt(selectedValues.get(
					Integer.valueOf(12)))+1)),
					 this.cbbF12));
			this.cbbF13
			.setSelectedIndex(ItemProperty.getIndexByCode(
			String.valueOf((Integer.parseInt(selectedValues.get(
					Integer.valueOf(13)))+1)),
					 this.cbbF13));
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
			btnOK.setBounds(11, 204, 97, 25);
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
			btnCancel.setBounds(113, 204, 97, 25);
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
			btnRestoreInitValues.setBounds(215, 204, 97, 25);
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
	 * This method initializes cbbF6	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF6() {
		if (cbbF6 == null) {
			cbbF6 = new JComboBox();
			cbbF6.setBounds(new Rectangle(131, 19, 127, 20));
		}
		return cbbF6;
	}

	/**
	 * This method initializes cbbF7	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF7() {
		if (cbbF7 == null) {
			cbbF7 = new JComboBox();
			cbbF7.setBounds(new Rectangle(131, 47, 127, 20));
		}
		return cbbF7;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel13 = new JLabel();
			jLabel13.setBounds(new Rectangle(12, 202, 112, 21));
			jLabel13.setText("13.核销数量");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(12, 176, 112, 21));
			jLabel12.setText("12.总净重");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(12, 149, 112, 21));
			jLabel11.setText("11.单价");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(12, 124, 112, 21));
			jLabel10.setText("10.数量");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(12, 98, 112, 21));
			jLabel9.setText("9.发票单位");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(12, 73, 112, 21));
			jLabel8.setText("8.发票规格");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(320, 11, 287, 233));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"发票单据体",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));			
			jPanel1.add(jLabel8, null);
			jPanel1.add(jLabel9, null);
			jPanel1.add(jLabel10, null);
			jPanel1.add(jLabel11, null);
			jPanel1.add(jLabel12, null);
			jPanel1.add(jLabel13, null);
			jPanel1.add(getCbbF8(), null);
			jPanel1.add(getCbbF9(), null);
			jPanel1.add(getCbbF10(), null);
			jPanel1.add(getCbbF11(), null);
			jPanel1.add(getCbbF12(), null);
			jPanel1.add(getCbbF13(), null);
			jPanel1.add(jLabel5, null);
			jPanel1.add(jLabel6, null);
			jPanel1.add(getCbbF6(), null);
			jPanel1.add(getCbbF7(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes cbbF8	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF8() {
		if (cbbF8 == null) {
			cbbF8 = new JComboBox();
			cbbF8.setBounds(new Rectangle(131, 73, 127, 20));
		}
		return cbbF8;
	}

	/**
	 * This method initializes cbbF9	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF9() {
		if (cbbF9 == null) {
			cbbF9 = new JComboBox();
			cbbF9.setBounds(new Rectangle(131, 98, 127, 20));
		}
		return cbbF9;
	}

	/**
	 * This method initializes cbbF10	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF10() {
		if (cbbF10 == null) {
			cbbF10 = new JComboBox();
			cbbF10.setBounds(new Rectangle(131, 124, 127, 20));
		}
		return cbbF10;
	}

	/**
	 * This method initializes cbbF11	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF11() {
		if (cbbF11 == null) {
			cbbF11 = new JComboBox();
			cbbF11.setBounds(new Rectangle(131, 149, 127, 20));
		}
		return cbbF11;
	}

	/**
	 * This method initializes cbbF12	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF12() {
		if (cbbF12 == null) {
			cbbF12 = new JComboBox();
			cbbF12.setBounds(new Rectangle(131, 176, 127, 20));
		}
		return cbbF12;
	}

	/**
	 * This method initializes cbbF13	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF13() {
		if (cbbF13 == null) {
			cbbF13 = new JComboBox();
			cbbF13.setBounds(new Rectangle(131, 203, 127, 20));
		}
		return cbbF13;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
