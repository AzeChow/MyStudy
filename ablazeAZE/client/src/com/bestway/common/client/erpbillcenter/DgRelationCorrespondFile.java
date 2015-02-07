package com.bestway.common.client.erpbillcenter;

import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.CasInnerMergeDataOrder;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgRelationCorrespondFile extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JComboBox cbbF1 = null;

	private JComboBox cbbF2 = null;

	private JComboBox cbbF3 = null;

	private JComboBox cbbF4 = null;

	private JComboBox cbbF5 = null;

	private int fileColumnCount = 0;

	private int[] selectedValues = null; // @jve:decl-index=0:

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

	private JLabel jLabel14 = null;

	private JComboBox cbbF8 = null;

	private JComboBox cbbF9 = null;

	private JComboBox cbbF10 = null;

	private JComboBox cbbF11 = null;

	private JComboBox cbbF12 = null;

	private JComboBox cbbF13 = null;

	private JComboBox cbbF14 = null;

	private JLabel jLabel15 = null;

	private JLabel jLabel16 = null;

	private JComboBox cbbF15 = null;

	private JComboBox cbbF16 = null;

	private JLabel jLabel17 = null;

	private JComboBox cbbF17 = null;
	
	private String materielType = null;
	private CasAction casAction = null;
	private JLabel label;
	private JComboBox cbbRemark;
	/**
	 * @return Returns the selectedValues.
	 */
	public int[] getSelectedValues() {
		return selectedValues;
	}

	/**
	 * @param selectedValues
	 *            The selectedValues to set.
	 */
	public void setSelectedValues(int[] selectedValues) {
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
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
		"casAction");
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
		this.setSize(624, 368);
	}

	@Override
	public void setVisible(boolean b){
		if(b){
			fillAllComboBox();
			restoreInitValues();
			if(!materielType.equals(MaterielType.FINISHED_PRODUCT)){
				jLabel17.setVisible(false);
				getCbbF17().setVisible(false);
			}else{
				jLabel17.setVisible(true);
				getCbbF17().setVisible(true);
			}
				
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
			jLabel6.setBounds(new Rectangle(17, 211, 112, 21));
			jLabel6.setText("7.单 位 折  算");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(17, 180, 112, 21));
			jLabel5.setText("6.单        位");
			jPanel = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(10, 11, 269, 251);
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"工厂物料资料",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel.setText("1.料        号");
			jLabel.setBounds(17, 20, 112, 21);
			jLabel1.setBounds(17, 53, 112, 21);
			jLabel1.setText("2.工厂商品名称");
			jLabel2.setBounds(17, 85, 112, 21);
			jLabel2.setText("3.工厂型号规格");
			jLabel3.setBounds(17, 116, 112, 21);
			jLabel3.setText("4.净        重");
			jLabel4.setBounds(17, 148, 112, 21);
			jLabel4.setText("5.参 考 单  价");
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
			jPanel.add(jLabel6, null);
			jPanel.add(getCbbF7(), null);
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
			cbbF1.setBounds(136, 19, 114, 20);
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
			cbbF2.setBounds(136, 55, 114, 20);
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
			cbbF3.setBounds(136, 86, 114, 20);
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
			cbbF4.setBounds(136, 118, 114, 20);
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
			cbbF5.setBounds(136, 150, 114, 20);
		}
		return cbbF5;
	}

	private void fillComboBox(JComboBox comboBox, int selectedIndex) {
		comboBox.addItem(new ItemProperty(String.valueOf(0), "  "));
		for (int i = 0; i < fileColumnCount; i++) {
			comboBox.addItem(new ItemProperty(String.valueOf(i+1), "第" + (i + 1)
					+ "列"));
		}
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
		fillComboBox(this.cbbF14, 14);
		fillComboBox(this.cbbF15, 15);
		fillComboBox(this.cbbF16, 16);
		if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
			fillComboBox(this.cbbF17, 17);
			fillComboBox(this.cbbRemark, 18);
		} else {
			fillComboBox(this.cbbRemark, 17);
		}
	}

	private void getSelectedValue() {
		
		if (selectedValues == null) {
			selectedValues = new int[20];
		}	
		System.out.println("getComboBoxSelectValue(this.cbbF1):"+getComboBoxSelectValue(this.cbbF1));
		
		selectedValues[0] = getComboBoxSelectValue(this.cbbF1);
		selectedValues[1] = getComboBoxSelectValue(this.cbbF2);
		selectedValues[2] = getComboBoxSelectValue(this.cbbF3);
		selectedValues[3] = getComboBoxSelectValue(this.cbbF4);
		selectedValues[4] = getComboBoxSelectValue(this.cbbF5);
		selectedValues[5] = getComboBoxSelectValue(this.cbbF6);
		selectedValues[6] = getComboBoxSelectValue(this.cbbF7);
		selectedValues[7] = getComboBoxSelectValue(this.cbbF8);
		selectedValues[8] = getComboBoxSelectValue(this.cbbF9);
		selectedValues[9] = getComboBoxSelectValue(this.cbbF10);
		selectedValues[10] = getComboBoxSelectValue(this.cbbF11);
		selectedValues[11] = getComboBoxSelectValue(this.cbbF12);
		selectedValues[12] = getComboBoxSelectValue(this.cbbF13);
		selectedValues[13] = getComboBoxSelectValue(this.cbbF14);
		selectedValues[14] = getComboBoxSelectValue(this.cbbF15);
		selectedValues[15] = getComboBoxSelectValue(this.cbbF16);
		if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
			selectedValues[16] = getComboBoxSelectValue(this.cbbF17);
			selectedValues[17] = getComboBoxSelectValue(this.cbbRemark);
		} else {
			selectedValues[16] = getComboBoxSelectValue(this.cbbRemark);
		}
	}


	
	private Integer getComboBoxSelectValue(JComboBox comboBox) {
		if (comboBox.getSelectedItem() == null) {
			return -1;
		}
		return Integer.valueOf(((ItemProperty) comboBox
				.getSelectedItem()).getCode()) - 1;
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
				|| selectedValues[0] == -1) {
			setComboBoxInitValue(this.cbbF1, 0);
		} else {			
			this.cbbF1.setSelectedIndex(ItemProperty.getIndexByCode(selectedValues[0] + 1 + "",
					this.cbbF1));
		}
		
		if (selectedValues == null
				|| selectedValues[1] == -1) {
			setComboBoxInitValue(this.cbbF2, 0);
		} else {			
			this.cbbF2.setSelectedIndex(ItemProperty.getIndexByCode(selectedValues[1] + 1 + "",
					this.cbbF2));
		}
		
		if (selectedValues == null
				|| selectedValues[2] == -1) {
			setComboBoxInitValue(this.cbbF3, 0);
		} else {			
			this.cbbF3.setSelectedIndex(ItemProperty.getIndexByCode(selectedValues[2] + 1 + "",
					this.cbbF3));
		}
		
		if (selectedValues == null
				|| selectedValues[3] == -1) {
			setComboBoxInitValue(this.cbbF4, 0);
		} else {			
			this.cbbF4.setSelectedIndex(ItemProperty.getIndexByCode(selectedValues[3] + 1 + "",
					this.cbbF4));
		}
		
		if (selectedValues == null
				|| selectedValues[4] == -1) {
			setComboBoxInitValue(this.cbbF5, 0);
		} else {			
			this.cbbF5.setSelectedIndex(ItemProperty.getIndexByCode(selectedValues[4] + 1 + "",
					this.cbbF5));
		}
		
		if (selectedValues == null
				|| selectedValues[5] == -1) {
			setComboBoxInitValue(this.cbbF6, 0);
		} else {			
			this.cbbF6.setSelectedIndex(ItemProperty.getIndexByCode(selectedValues[5] + 1 + "",
					this.cbbF6));
		}
		
		if (selectedValues == null
				|| selectedValues[6] == -1) {
			setComboBoxInitValue(this.cbbF7, 0);
		} else {			
			this.cbbF7.setSelectedIndex(ItemProperty.getIndexByCode(selectedValues[6] + 1 + "",
					this.cbbF7));
		}
		
		if (selectedValues == null
				|| selectedValues[7] == -1) {
			setComboBoxInitValue(this.cbbF8, 0);
		} else {			
			this.cbbF8.setSelectedIndex(ItemProperty.getIndexByCode(selectedValues[7] + 1 + "",
					this.cbbF8));
		}
		
		if (selectedValues == null
				|| selectedValues[8] == -1) {
			setComboBoxInitValue(this.cbbF9, 0);
		} else {			
			this.cbbF9.setSelectedIndex(ItemProperty.getIndexByCode(selectedValues[8] + 1 + "",
					this.cbbF9));
		}
		
		if (selectedValues == null
				|| selectedValues[9] == -1) {
			setComboBoxInitValue(this.cbbF10, 0);
		} else {			
			this.cbbF10.setSelectedIndex(ItemProperty.getIndexByCode(selectedValues[9] + 1 + "",
					this.cbbF10));
		}
		
		if (selectedValues == null
				|| selectedValues[10] == -1) {
			setComboBoxInitValue(this.cbbF11, 0);
		} else {			
			this.cbbF11.setSelectedIndex(ItemProperty.getIndexByCode(selectedValues[10] + 1 + "",
					this.cbbF11));
		}
		
		if (selectedValues == null
				|| selectedValues[11] == -1) {
			setComboBoxInitValue(this.cbbF12, 0);
		} else {			
			this.cbbF12.setSelectedIndex(ItemProperty.getIndexByCode(selectedValues[11] + 1 + "",
					this.cbbF12));
		}
		
		if (selectedValues == null
				|| selectedValues[12] == -1) {
			setComboBoxInitValue(this.cbbF13, 0);
		} else {			
			this.cbbF13.setSelectedIndex(ItemProperty.getIndexByCode(selectedValues[12] + 1 + "",
					this.cbbF13));
		}
		
		if (selectedValues == null
				|| selectedValues[13] == -1) {
			setComboBoxInitValue(this.cbbF14, 0);
		} else {			
			this.cbbF14.setSelectedIndex(ItemProperty.getIndexByCode(selectedValues[13] + 1 + "",
					this.cbbF14));
		}
		
		if (selectedValues == null
				|| selectedValues[14] == -1) {
			setComboBoxInitValue(this.cbbF15, 0);
		} else {			
			this.cbbF15.setSelectedIndex(ItemProperty.getIndexByCode(selectedValues[14] + 1 + "",
					this.cbbF15));
		}
		
		if (selectedValues == null
				|| selectedValues[15] == -1) {
			setComboBoxInitValue(this.cbbF16, 0);
		} else {			
			this.cbbF16.setSelectedIndex(ItemProperty.getIndexByCode(selectedValues[15] + 1 + "",
					this.cbbF16));
		}
		if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
			if (selectedValues == null || selectedValues[16] == -1) {
				setComboBoxInitValue(this.cbbF17, 0);
			} else {
				this.cbbF17.setSelectedIndex(ItemProperty.getIndexByCode(
						selectedValues[16] + 1 + "", this.cbbF17));
			}
			if (selectedValues == null || selectedValues[17] == -1) {
				setComboBoxInitValue(this.cbbRemark, 0);
			} else {
				this.cbbRemark.setSelectedIndex(ItemProperty.getIndexByCode(
						selectedValues[17] + 1 + "", this.cbbRemark));
			}
		} else {
			if (selectedValues == null || selectedValues[16] == -1) {
				setComboBoxInitValue(this.cbbRemark, 0);
			} else {
				this.cbbRemark.setSelectedIndex(ItemProperty.getIndexByCode(
						selectedValues[16] + 1 + "", this.cbbRemark));
			}
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
			btnOK.setBounds(0, 295, 97, 25);
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CasInnerMergeDataOrder order = casAction.findCasInnerMergeDataOrder(new Request(CommonVars
							.getCurrUser(), true));
					
					/**
					 * edit by prince
					 */
					fillData(order);
					casAction.saveCasInnerMergeDataOrder(new Request(
							CommonVars.getCurrUser(), true), order);
					getSelectedValue();
					dispose();
					
				}
			});
		}
		return btnOK;
	}
	
	/**
	 * add by prince
	 * @param order
	 */
	private void fillData(CasInnerMergeDataOrder order) {
		if (this.getCbbF1().getSelectedItem() != null) {
			order.setCasF1(Integer.valueOf(((ItemProperty) this.cbbF1
					.getSelectedItem()).getCode())-1);
		} else {
			order.setCasF1(-1);
		}
		if (this.getCbbF2().getSelectedItem() != null) {
			order.setCasF2(Integer
					.valueOf(((ItemProperty) this.cbbF2
							.getSelectedItem()).getCode())-1);
		} else {
			order.setCasF2(-1);
		}
		if (this.getCbbF3().getSelectedItem() != null) {
			order.setCasF3(Integer
					.valueOf(((ItemProperty) this.cbbF3
							.getSelectedItem()).getCode())-1);
		} else {
			order.setCasF3(-1);
		}
		if (this.getCbbF4().getSelectedItem() != null) {
			order.setCasF4(Integer
					.valueOf(((ItemProperty) this.cbbF4
							.getSelectedItem()).getCode())-1);
		} else {
			order.setCasF4(-1);
		}
		if (this.getCbbF5().getSelectedItem() != null) {
			order.setCasF5(Integer
					.valueOf(((ItemProperty) this.cbbF5
							.getSelectedItem()).getCode())-1);
		} else {
			order.setCasF5(-1);
		}
		if (this.getCbbF6().getSelectedItem() != null) {
			order.setCasF6(Integer
					.valueOf(((ItemProperty) this.cbbF6
							.getSelectedItem()).getCode())-1);
		} else {
			order.setCasF6(-1);
		}
		if (this.getCbbF7().getSelectedItem() != null) {
			order.setCasF7(Integer
					.valueOf(((ItemProperty) this.cbbF7
							.getSelectedItem()).getCode())-1);
		} else {
			order.setCasF7(-1);
		}
		if (this.getCbbF8().getSelectedItem() != null) {
			order.setCasF8(Integer
					.valueOf(((ItemProperty) this.cbbF8
							.getSelectedItem()).getCode())-1);
		} else {
			order.setCasF8(-1);
		}
		if (this.getCbbF9().getSelectedItem() != null) {
			order.setCasF9(Integer
					.valueOf(((ItemProperty) this.cbbF9
							.getSelectedItem()).getCode())-1);
		} else {
			order.setCasF9(-1);
		}
		if (this.getCbbF10().getSelectedItem() != null) {
			order.setCasF10(Integer
					.valueOf(((ItemProperty) this.cbbF10
							.getSelectedItem()).getCode())-1);
		} else {
			order.setCasF10(-1);
		}
		if (this.getCbbF11().getSelectedItem() != null) {
			order.setCasF11(Integer
					.valueOf(((ItemProperty) this.cbbF11
							.getSelectedItem()).getCode())-1);
		} else {
			order.setCasF11(-1);
		}
		if (this.getCbbF12().getSelectedItem() != null) {
			order.setCasF12(Integer
					.valueOf(((ItemProperty) this.cbbF12
							.getSelectedItem()).getCode())-1);
		} else {
			order.setCasF12(-1);
		}
		if (this.getCbbF13().getSelectedItem() != null) {
			order.setCasF13(Integer
					.valueOf(((ItemProperty) this.cbbF13
							.getSelectedItem()).getCode())-1);
		} else {
			order.setCasF13(-1);
		}
		if (this.getCbbF14().getSelectedItem() != null) {
			order.setCasF14(Integer
					.valueOf(((ItemProperty) this.cbbF14
							.getSelectedItem()).getCode())-1);
		} else {
			order.setCasF14(-1);
		}
		if (this.getCbbF15().getSelectedItem() != null) {
			order.setCasF15(Integer
					.valueOf(((ItemProperty) this.cbbF15
							.getSelectedItem()).getCode())-1);
		} else {
			order.setCasF15(-1);
		}
		if (this.getCbbF16().getSelectedItem() != null) {
			order.setCasF16(Integer
					.valueOf(((ItemProperty) this.cbbF16
							.getSelectedItem()).getCode())-1);
		} else {
			order.setCasF16(-1);
		}
		if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
			if (this.getCbbF17().getSelectedItem() != null) {
				order.setCasF17(Integer.valueOf(((ItemProperty) this.cbbF17
						.getSelectedItem()).getCode()) - 1);
			} else {
				order.setCasF17(-1);
			}
			if (this.getCbbRemark().getSelectedItem() != null) {
				order.setRemark(Integer.valueOf(((ItemProperty) this.cbbRemark
						.getSelectedItem()).getCode()) - 1);
			} else {
				order.setRemark(-1);
			}
		} else {
			if (this.getCbbRemark().getSelectedItem() != null) {
				order.setRemark(Integer.valueOf(((ItemProperty) this.cbbRemark
						.getSelectedItem()).getCode()) - 1);
			} else {
				order.setRemark(-1);

			}
		}
		
	}
	

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(102, 295, 97, 25);
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
			btnRestoreInitValues.setBounds(204, 295, 97, 25);
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
			cbbF6.setBounds(new Rectangle(136, 181, 114, 20));
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
			cbbF7.setBounds(new Rectangle(136, 211, 114, 20));
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
			jLabel17 = new JLabel();
			jLabel17.setBounds(new Rectangle(20, 259, 112, 21));
			jLabel17.setText("17.成品版本号");
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(20, 202, 112, 21));
			jLabel16.setText("15.手册结束日期");
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(20, 175, 112, 21));
			jLabel15.setText("14.手册开始日期");
			jLabel14 = new JLabel();
			jLabel14.setBounds(new Rectangle(20, 230, 112, 21));
			jLabel14.setText("16.管 理 类  型");
			jLabel13 = new JLabel();
			jLabel13.setBounds(new Rectangle(20, 149, 112, 21));
			jLabel13.setText("13.归 并 序  号");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(20, 123, 112, 21));
			jLabel12.setText("12.手   册   号");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(20, 96, 112, 21));
			jLabel11.setText("11.报 关 单  位");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(20, 71, 112, 21));
			jLabel10.setText("10.报 关 规  格");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(20, 45, 112, 21));
			jLabel9.setText("9.报  关  名 称");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(20, 20, 112, 21));
			jLabel8.setText("8.报 关  编  码");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(305, 11, 287, 309));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"实际报关资料",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));			
			jPanel1.add(jLabel8, null);
			jPanel1.add(jLabel9, null);
			jPanel1.add(jLabel10, null);
			jPanel1.add(jLabel11, null);
			jPanel1.add(jLabel12, null);
			jPanel1.add(jLabel13, null);
			jPanel1.add(jLabel14, null);
			jPanel1.add(getCbbF8(), null);
			jPanel1.add(getCbbF9(), null);
			jPanel1.add(getCbbF10(), null);
			jPanel1.add(getCbbF11(), null);
			jPanel1.add(getCbbF12(), null);
			jPanel1.add(getCbbF13(), null);
			jPanel1.add(getCbbF14(), null);
			jPanel1.add(jLabel15, null);
			jPanel1.add(jLabel16, null);
			jPanel1.add(getCbbF15(), null);
			jPanel1.add(getCbbF16(), null);
			jPanel1.add(jLabel17, null);
			jPanel1.add(getCbbF17(), null);
			jPanel1.add(getLabel());
			jPanel1.add(getCbbRemark());
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
			cbbF8.setBounds(new Rectangle(140, 20, 127, 20));
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
			cbbF9.setBounds(new Rectangle(140, 45, 127, 20));
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
			cbbF10.setBounds(new Rectangle(140, 71, 127, 20));
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
			cbbF11.setBounds(new Rectangle(140, 96, 127, 20));
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
			cbbF12.setBounds(new Rectangle(140, 123, 127, 20));
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
			cbbF13.setBounds(new Rectangle(140, 150, 127, 20));
		}
		return cbbF13;
	}

	/**
	 * This method initializes cbbF14	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF14() {
		if (cbbF14 == null) {
			cbbF14 = new JComboBox();
			cbbF14.setBounds(new Rectangle(140, 175, 127, 20));
		}
		return cbbF14;
	}

	/**
	 * This method initializes cbbF15	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF15() {
		if (cbbF15 == null) {
			cbbF15 = new JComboBox();
			cbbF15.setBounds(new Rectangle(140, 202, 127, 20));
		}
		return cbbF15;
	}

	/**
	 * This method initializes cbbF16	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF16() {
		if (cbbF16 == null) {
			cbbF16 = new JComboBox();
			cbbF16.setBounds(new Rectangle(140, 230, 127, 20));
		}
		return cbbF16;
	}

	/**
	 * This method initializes cbbF17	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF17() {
		if (cbbF17 == null) {
			cbbF17 = new JComboBox();
			cbbF17.setBounds(new Rectangle(140, 259, 127, 20));
		}
		return cbbF17;
	}

	/**
	 * @return the materielType
	 */
	public String getMaterielType() {
		return materielType;
	}

	/**
	 * @param materielType the materielType to set
	 */
	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel();
			label.setText("18.备        注");
			label.setBounds(new Rectangle(20, 259, 112, 21));
			label.setBounds(20, 288, 112, 21);
		}
		return label;
	}
	private JComboBox getCbbRemark() {
		if (cbbRemark == null) {
			cbbRemark = new JComboBox();
			cbbRemark.setBounds(new Rectangle(140, 259, 127, 20));
			cbbRemark.setBounds(140, 288, 127, 20);
		}
		return cbbRemark;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"

