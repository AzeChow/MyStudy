/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.verification;

 
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.entity.VFCategory;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgVFCategory extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jPanel2 = null;

	private JButton btnOK = null;

	private JButton btnCancel = null;

	private FmVFSection vf;

	private VFVerificationAction vfAction = null;
	private JLabel lblNewLabel;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JTextField tfSeqNum;
	private JTextField tfCode;
	private JTextField tfName;
	private JTextField tfSpec;
	private JComboBox cbUnit;
	private VFCategory category;
	private int dataState = DataState.ADD;
	private boolean ok = false;

	public FmVFSection getVf() {
		return vf;
	}
	
	public void setVf(FmVFSection fmVFSection) {
		this.vf = fmVFSection;
	}

	/**
	 * This is the default constructor
	 */
	public DgVFCategory() {
		super();
		vfAction = (VFVerificationAction) CommonVars.getApplicationContext().getBean("vfVerificationAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(464, 280);
		this.setTitle("大类名称");		
		this.setContentPane(getJPanel2());
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			javax.swing.JLabel jLabel = new JLabel();
			jLabel.setHorizontalAlignment(SwingConstants.LEFT);
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setText("大类序号");
			jLabel.setBounds(new Rectangle(108, 19, 54, 23));
			jPanel2.add(jLabel, null);			
			jPanel2.add(getBtnOK(), null);
			jPanel2.add(getBtnCancel(), null);
			jPanel2.add(getLblNewLabel());
			jPanel2.add(getLabel());
			jPanel2.add(getLabel_1());
			jPanel2.add(getLabel_2());
			jPanel2.add(getTfSeqNum());
			jPanel2.add(getTfCode());
			jPanel2.add(getTfName());
			jPanel2.add(getTfSpec());
			jPanel2.add(getCbUnit());
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jButton
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setText("确定");
			btnOK.setBounds(new Rectangle(121, 206, 69, 26));
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ok  = true;
					if(dataState == DataState.ADD)
						category = new VFCategory();
					category.setComplexCode(tfCode.getText().trim());
					category.setComplexName(tfName.getText().trim());
					category.setSpec(tfSpec.getText().trim());					
					category.setUnit(cbUnit.getSelectedItem() == null ? null : ((Unit)cbUnit.getSelectedItem()).getName());
					dispose();
				}
			});
		}
		return btnOK;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.setBounds(new Rectangle(244, 207, 73, 25));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgVFCategory.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("商品编码");
			lblNewLabel.setBounds(108, 61, 54, 15);
		}
		return lblNewLabel;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("商品名称");
			label.setBounds(108, 95, 54, 15);
		}
		return label;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("商品规格");
			label_1.setBounds(108, 129, 54, 15);
		}
		return label_1;
	}
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("计量单位");
			label_2.setBounds(108, 163, 54, 15);
		}
		return label_2;
	}
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setEditable(false);
			tfSeqNum.setBounds(172, 20, 162, 21);
			tfSeqNum.setColumns(10);
		}
		return tfSeqNum;
	}
	public JTextField getTfCode() {
		if (tfCode == null) {
			tfCode = new JTextField();
			tfCode.setBounds(172, 58, 162, 21);
			tfCode.setColumns(10);
		}
		return tfCode;
	}
	public JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(172, 92, 162, 21);
			tfName.setColumns(10);
		}
		return tfName;
	}
	public JTextField getTfSpec() {
		if (tfSpec == null) {
			tfSpec = new JTextField();
			tfSpec.setBounds(172, 126, 162, 21);
			tfSpec.setColumns(10);
		}
		return tfSpec;
	}
	public JComboBox getCbUnit() {
		if (cbUnit == null) {
			cbUnit = new JComboBox();
			cbUnit.setEditable(true);
			cbUnit.setBounds(172, 160, 162, 21);						
			cbUnit.setModel(CustomBaseModel.getInstance().getUnitModel());		
			this.cbUnit.setRenderer(CustomBaseRender.getInstance().getRender());
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbUnit);
		}
		return cbUnit;
	}
	public void setUnit(String unit){		
		if(unit != null && !unit.trim().isEmpty()){
			for(int i = 0 ;i < cbUnit.getItemCount() ;i++){
				if(unit.equals(((Unit)cbUnit.getItemAt(i)).getName())){
					cbUnit.setSelectedIndex(i);
					break;
				}						
			}
		}else{
			cbUnit.setSelectedIndex(-1);
		}
	}	
	public void setVisible(boolean b) {
		if(b){
			if(dataState != DataState.ADD && category != null){
				tfSeqNum.setText(String.valueOf(category.getSeqNum()));
				tfCode.setText(category.getComplexCode());
				tfName.setText(category.getComplexName());
				tfSpec.setText(category.getSpec());				
			}
		}
		super.setVisible(b);		
	}
	
	public void setDataState(int dataState) {
		this.dataState = dataState;
	}
	public void setCategory(VFCategory category) {
		this.category = category;
	}
	public VFCategory getCategory() {
		return category;
	}
	public boolean isOk(){
		return ok;
	}
} 
