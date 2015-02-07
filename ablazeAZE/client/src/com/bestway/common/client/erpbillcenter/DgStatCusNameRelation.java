/*
 * Created on 2005-7-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.erpbillcenter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.StatCusNameRelation;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgStatCusNameRelation extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JTextField tfCusCode = null;

	private JLabel jLabel1 = null;

	private JTextField tfCusName = null;

	private JTextField tfCusSpec = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbUnit = null;

	private JButton btnOK = null;

	private JButton btnCancel = null;

	private List lsResult = new ArrayList();

	private int dataState = DataState.BROWSE;

	private StatCusNameRelation statCusNameRelation = null;
	
	private CasAction casAction=null;

	private Complex complex=null;
	
	private String materielType;
	
	/**
	 * @return Returns the materielType.
	 */
	public String getMaterielType() {
		return materielType;
	}
	/**
	 * @param materielType The materielType to set.
	 */
	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}
	/**
	 * @return Returns the statCusNameRelation.
	 */
	public StatCusNameRelation getStatCusNameRelation() {
		return statCusNameRelation;
	}

	/**
	 * @param statCusNameRelation
	 *            The statCusNameRelation to set.
	 */
	public void setStatCusNameRelation(StatCusNameRelation statCusNameRelation) {
		this.statCusNameRelation = statCusNameRelation;
		this.complex=statCusNameRelation.getComplex();
	}

	/**
	 * @return Returns the dataState.
	 */
	public int getDataState() {
		return dataState;
	}

	/**
	 * @param dataState
	 *            The dataState to set.
	 */
	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This is the default constructor
	 */
	public DgStatCusNameRelation() {
		super();
		initialize();
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			this.initUIComponents();
			casAction = (CasAction) CommonVars
                      .getApplicationContext().getBean("casAction");
			showData(this.statCusNameRelation);
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("商品大类编辑");
		this.setSize(346, 243);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(33, 21, 67, 23);
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jLabel.setText("商品编码");
			jLabel1.setBounds(33, 56, 67, 23);
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jLabel1.setText("商品名称");
			jLabel2.setBounds(33, 90, 67, 23);
			jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jLabel2.setText("型号规格");
			jLabel3.setBounds(33, 123, 67, 23);
			jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jLabel3.setText("单位");
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfCusCode(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfCusName(), null);
			jContentPane.add(getTfCusSpec(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getCbbUnit(), null);
			jContentPane.add(getBtnOK(), null);
			jContentPane.add(getBtnCancel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCusCode() {
		if (tfCusCode == null) {
			tfCusCode = new JTextField();
			tfCusCode.setBounds(106, 21, 191, 23);
			tfCusCode.setEditable(false);
			tfCusCode.setBackground(java.awt.Color.white);
		}
		return tfCusCode;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCusName() {
		if (tfCusName == null) {
			tfCusName = new JTextField();
			tfCusName.setBounds(106, 56, 191, 23);
		}
		return tfCusName;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCusSpec() {
		if (tfCusSpec == null) {
			tfCusSpec = new JTextField();
			tfCusSpec.setBounds(106, 90, 191, 23);
		}
		return tfCusSpec;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUnit() {
		if (cbbUnit == null) {
			cbbUnit = new JComboBox();
			cbbUnit.setBounds(106, 123, 191, 23);
		}
		return cbbUnit;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(143, 165, 70, 26);
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(dataState==DataState.ADD){
						statCusNameRelation=new StatCusNameRelation();						
					}else if(dataState==DataState.EDIT){
						if(statCusNameRelation==null){
							return;
						}						
					}
					fillData(statCusNameRelation);
					lsResult=casAction.saveStatCusNameRelation(new Request(CommonVars.getCurrUser()),statCusNameRelation);
					dispose();
				}
			});
		}
		return btnOK;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(223, 165, 70, 26);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	private void initUIComponents() {
		// 初始化单位
		this.cbbUnit.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbUnit.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbUnit);
	}

	public List getResult() {
		return lsResult;
	}
	
	private void fillData(StatCusNameRelation relation){
		relation.setCusName(tfCusName.getText());
		relation.setCusSpec(tfCusSpec.getText());
		relation.setCusUnit((Unit)cbbUnit.getSelectedItem());
		if(dataState==DataState.ADD){
			statCusNameRelation.setComplex(complex);
			statCusNameRelation.setMaterielType(this.materielType);
			relation.setCompany(CommonVars.getCurrUser().getCompany());
		}
	}
	
	private void showData(StatCusNameRelation relation){
		if(relation==null){
			return;
		}
		tfCusCode.setText(relation.getComplex().getCode());
		tfCusName.setText(relation.getCusName());
		tfCusSpec.setText(relation.getCusSpec());
		cbbUnit.setSelectedItem(relation.getCusUnit());
	}
 }  //  @jve:decl-index=0:visual-constraint="11,1"
