package com.bestway.common.client.erpbillcenter;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.dataimport.entity.ImportDataOrder;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgHsnCorrespondFile extends JDialogBase {

	private JPanel jContentPane = null;

	private int fileColumnCount = 0;

	private Hashtable<Integer, Integer> selectedValues = null; // @jve:decl-index=0:

	private JButton btnOK = null;

	private JButton btnCancel = null;

	private JButton btnRestoreInitValues = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel12 = null;

	private JComboBox cbbF1 = null;

	private JComboBox cbbF2 = null;

	private JComboBox cbbF3 = null;

	private JComboBox cbbF4 = null;

	private JComboBox cbbF5 = null;

	private JComboBox cbbF6 = null;

	private JLabel jLabel15 = null;

	private JLabel jLabel16 = null;

	private JComboBox cbbF7 = null;

	private JLabel jLabel17 = null;

	private JComboBox cbbF8 = null;
	
	private String materielType = null;
	private CasAction casAction = null;
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
	public DgHsnCorrespondFile() {
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
		this.setSize(326, 333);
	}

	@Override
	public void setVisible(boolean b){
		if(b){
			fillAllComboBox();
			restoreInitValues();
			if(!materielType.equals(MaterielType.FINISHED_PRODUCT)){
				jLabel17.setVisible(false);
				getCbbF8().setVisible(false);
			}else{
				jLabel17.setVisible(true);
				getCbbF8().setVisible(true);
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
			jContentPane.add(getBtnOK(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getBtnRestoreInitValues(), null);
			jContentPane.add(getJPanel1(), null);
		}
		return jContentPane;
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
	}

	private void getSelectedValue() {
		
		if (selectedValues == null) {
			selectedValues = new Hashtable<Integer, Integer>();
		}	
		System.out.println("getComboBoxSelectValue(this.cbbF1):"+getComboBoxSelectValue(this.cbbF1));
		
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
				|| selectedValues.get(Integer.valueOf(1)) == null) {
			setComboBoxInitValue(this.cbbF1,1);
		} else {			
			this.cbbF1.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf(selectedValues.get(Integer
							.valueOf(1))+1),
					this.cbbF1));
		}
		
		if (selectedValues == null
				|| selectedValues.get(Integer.valueOf(2)) == null) {
			setComboBoxInitValue(this.cbbF2,2);
		} else {
			this.cbbF2.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf(selectedValues.get(Integer
							.valueOf(2))+1),
					this.cbbF2));
		}
		
		if (selectedValues == null
				|| selectedValues.get(Integer.valueOf(3)) == null) {
			setComboBoxInitValue(this.cbbF3,3);
		} else {
			this.cbbF3.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf(selectedValues.get(Integer
							.valueOf(3))+1),
					this.cbbF3));
		}
		
		if (selectedValues == null
				|| selectedValues.get(Integer.valueOf(4)) == null) {
			setComboBoxInitValue(this.cbbF4,4);
		} else {
			this.cbbF4.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf(selectedValues.get(Integer
							.valueOf(4))+1),
					this.cbbF4));
		}
		
		if (selectedValues == null
				|| selectedValues.get(Integer.valueOf(5)) == null) {
			setComboBoxInitValue(this.cbbF5,5);
		} else {
			this.cbbF5.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf(selectedValues.get(Integer
							.valueOf(5))+1),
					this.cbbF5));
		}
		
		if (selectedValues == null
				|| selectedValues.get(Integer.valueOf(6)) == null) {
			setComboBoxInitValue(this.cbbF6,6);
		} else {
			this.cbbF6.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf(selectedValues.get(Integer
							.valueOf(6))+1),
					this.cbbF6));
		}
		
		if (selectedValues == null
				|| selectedValues.get(Integer.valueOf(7)) == null) {
			setComboBoxInitValue(this.cbbF7,7);
		} else {
			this.cbbF7.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf(selectedValues.get(Integer
							.valueOf(7))+1),
					this.cbbF7));
		}
		
		if (selectedValues == null
				|| selectedValues.get(Integer.valueOf(8)) == null) {
			setComboBoxInitValue(this.cbbF8,8);
		} else {
			this.cbbF8.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf(selectedValues.get(Integer
							.valueOf(8))+1),
					this.cbbF8));
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
			btnOK.setBounds(8, 260, 97, 25);
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					CasInnerMergeDataOrder order = casAction.findCasInnerMergeDataOrder(new Request(CommonVars
//							.getCurrUser(), true));
					
					/**
					 * edit by prince
					 */
					List<ImportDataOrder> list = fillData();
					casAction.batchUpdateDataOrder(new Request(
							CommonVars.getCurrUser(), true), list);
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
	private List<ImportDataOrder> fillData() {
		List<ImportDataOrder> list = new ArrayList();
		ImportDataOrder order1 = new ImportDataOrder();
		ImportDataOrder order2 = new ImportDataOrder();
		ImportDataOrder order3 = new ImportDataOrder();
		ImportDataOrder order4 = new ImportDataOrder();
		ImportDataOrder order5 = new ImportDataOrder();
		ImportDataOrder order6 = new ImportDataOrder();
		ImportDataOrder order7 = new ImportDataOrder();
		ImportDataOrder order8 = new ImportDataOrder();
		order1.setTableName("StatCusNameRelationHsn");
		order2.setTableName("StatCusNameRelationHsn");
		order3.setTableName("StatCusNameRelationHsn");
		order4.setTableName("StatCusNameRelationHsn");
		order5.setTableName("StatCusNameRelationHsn");
		order6.setTableName("StatCusNameRelationHsn");
		order7.setTableName("StatCusNameRelationHsn");
		order8.setTableName("StatCusNameRelationHsn");
 		if (this.getCbbF1().getSelectedItem() != null) {
			order1.setColumnName("complex");
			order1.setPosition(Integer.valueOf(((ItemProperty) this.cbbF1
					.getSelectedItem()).getCode())-1);
		} else {
			order1.setColumnName("complex");
			order1.setPosition(-1);
		}
 		list.add(order1);
		if (this.getCbbF2().getSelectedItem() != null) {
			order2.setColumnName("cusName");
			order2.setPosition(Integer.valueOf(((ItemProperty) this.cbbF2
					.getSelectedItem()).getCode())-1);
		} else {
			order2.setColumnName("cusName");
			order2.setPosition(-1);
		}
		list.add(order2);
		if (this.getCbbF3().getSelectedItem() != null) {
			order3.setColumnName("cusSpec");
			order3.setPosition(Integer.valueOf(((ItemProperty) this.cbbF3
					.getSelectedItem()).getCode())-1);
		} else {
			order3.setColumnName("cusSpec");
			order3.setPosition(-1);
		}
		list.add(order3);
		if (this.getCbbF4().getSelectedItem() != null) {
			order4.setColumnName("cusUnit");
			order4.setPosition(Integer.valueOf(((ItemProperty) this.cbbF4
					.getSelectedItem()).getCode())-1);
		} else {
			order4.setColumnName("cusUnit");
			order4.setPosition(-1);
		}
		list.add(order4);
		if (this.getCbbF5().getSelectedItem() != null) {
			order5.setColumnName("emsNo");
			order5.setPosition(Integer.valueOf(((ItemProperty) this.cbbF5
					.getSelectedItem()).getCode())-1);
		} else {
			order5.setColumnName("emsNo");
			order5.setPosition(-1);
		}
		list.add(order5);
		if (this.getCbbF6().getSelectedItem() != null) {
			order6.setColumnName("emsBeginDate");
			order6.setPosition(Integer.valueOf(((ItemProperty) this.cbbF6
					.getSelectedItem()).getCode())-1);
		} else {
			order6.setColumnName("emsBeginDate");
			order6.setPosition(-1);
		}
		list.add(order6);
		if (this.getCbbF7().getSelectedItem() != null) {
			order7.setColumnName("emsEndDate");
			order7.setPosition(Integer.valueOf(((ItemProperty) this.cbbF7
					.getSelectedItem()).getCode())-1);
		} else {
			order7.setColumnName("emsEndDate");
			order7.setPosition(-1);
		}
		list.add(order7);
		if (this.getCbbF8().getSelectedItem() != null) {
			order8.setColumnName("version");
			order8.setPosition(Integer.valueOf(((ItemProperty) this.cbbF8
					.getSelectedItem()).getCode())-1);
		} else {
			order8.setColumnName("version");
			order8.setPosition(-1);
		}
		list.add(order8);
		return list;
	}
	

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(110, 260, 97, 25);
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
			btnRestoreInitValues.setBounds(212, 260, 97, 25);
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
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel17 = new JLabel();
			jLabel17.setBounds(new Rectangle(20, 204, 112, 21));
			jLabel17.setText("8.成品版本号");
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(20, 176, 112, 21));
			jLabel16.setText("7.手册结束日期");
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(20, 149, 112, 21));
			jLabel15.setText("6.手册开始日期");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(20, 123, 112, 21));
			jLabel12.setText("5.手册号");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(20, 96, 112, 21));
			jLabel11.setText("4.报关单位");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(20, 71, 112, 21));
			jLabel10.setText("3.报关规格");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(20, 45, 112, 21));
			jLabel9.setText("2.报关名称");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(20, 20, 112, 21));
			jLabel8.setText("1.报关编码");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(8, 11, 287, 237));
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
			jPanel1.add(getCbbF1(), null);
			jPanel1.add(getCbbF2(), null);
			jPanel1.add(getCbbF3(), null);
			jPanel1.add(getCbbF4(), null);
			jPanel1.add(getCbbF5(), null);
			jPanel1.add(getCbbF6(), null);
			jPanel1.add(jLabel15, null);
			jPanel1.add(jLabel16, null);
			jPanel1.add(getCbbF7(), null);
			jPanel1.add(jLabel17, null);
			jPanel1.add(getCbbF8(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes cbbF8	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF1() {
		if (cbbF1 == null) {
			cbbF1 = new JComboBox();
			cbbF1.setBounds(new Rectangle(140, 20, 127, 20));
		}
		return cbbF1;
	}

	/**
	 * This method initializes cbbF9	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF2() {
		if (cbbF2 == null) {
			cbbF2 = new JComboBox();
			cbbF2.setBounds(new Rectangle(140, 45, 127, 20));
		}
		return cbbF2;
	}

	/**
	 * This method initializes cbbF10	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF3() {
		if (cbbF3 == null) {
			cbbF3 = new JComboBox();
			cbbF3.setBounds(new Rectangle(140, 71, 127, 20));
		}
		return cbbF3;
	}

	/**
	 * This method initializes cbbF11	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF4() {
		if (cbbF4 == null) {
			cbbF4 = new JComboBox();
			cbbF4.setBounds(new Rectangle(140, 96, 127, 20));
		}
		return cbbF4;
	}

	/**
	 * This method initializes cbbF12	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF5() {
		if (cbbF5 == null) {
			cbbF5 = new JComboBox();
			cbbF5.setBounds(new Rectangle(140, 123, 127, 20));
		}
		return cbbF5;
	}

	/**
	 * This method initializes cbbF14	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF6() {
		if (cbbF6 == null) {
			cbbF6 = new JComboBox();
			cbbF6.setBounds(new Rectangle(140, 149, 127, 20));
		}
		return cbbF6;
	}

	/**
	 * This method initializes cbbF15	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF7() {
		if (cbbF7 == null) {
			cbbF7 = new JComboBox();
			cbbF7.setBounds(new Rectangle(140, 176, 127, 20));
		}
		return cbbF7;
	}

	/**
	 * This method initializes cbbF17	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbF8() {
		if (cbbF8 == null) {
			cbbF8 = new JComboBox();
			cbbF8.setBounds(new Rectangle(140, 204, 127, 20));
		}
		return cbbF8;
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

}  //  @jve:decl-index=0:visual-constraint="10,10"
