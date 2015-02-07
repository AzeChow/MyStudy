/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.client.common.CommonVariables;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import java.awt.Rectangle;

import javax.swing.JComboBox;

import com.bestway.ui.winuicontrol.JNumberTextField;
import javax.swing.JCheckBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgEditTenInnerMerge extends JDialogBase {

	private JPanel					jContentPane			= null;
	private JPanel					jPanel					= null;
	private JTextField				tfName					= null;
	private JButton					btnSave					= null;
	private JButton					btnCancel				= null;
	private JLabel					jLabel1					= null;
	private JLabel					jLabel2					= null;
	private JTextField				tfMemoNo				= null;
	private JTextField				tfCode					= null;
	private CommonBaseCodeAction	commonBaseCodeAction	= null;
	private InnerMergeData			innerMergeData			= null;  //  @jve:decl-index=0:
	private boolean					isOk					= false;
	private List<InnerMergeData>	editListData			= null;  //  @jve:decl-index=0:
	private JTableListModel			tableModel				= null;
	private JLabel					jLabel					= null;
	private JLabel					jLabel3					= null;
	private JTextField				tfSpec					= null;
	private JLabel lblDeclareUnit;
	private JLabel label1;
	private JLabel label;
	private JComboBox cbbUnit;
	private JComboBox cbbFirstUnit;
	private JComboBox cbbSecondUnit;
	private JLabel label2;
	private JLabel label3;
	private JNumberTextField txtUnitRatio;
	private JNumberTextField txtSecondUnitRatio;
	private JLabel label_1;
	private JNumberTextField tfWeigthUnitGene;
	private JCheckBox cbbIsMainImg;

	/**
	 * This method initializes
	 */
	public DgEditTenInnerMerge() {
		super();
		initialize();
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars.getApplicationContext().getBean("commonBaseCodeAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("编辑十码归并");
		this.setContentPane(getJContentPane());
		this.setSize(457, 375);
		
		this.cbbUnit.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbUnit.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbUnit);
		
		this.cbbFirstUnit.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbFirstUnit.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbFirstUnit);
		
		this.cbbSecondUnit.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbSecondUnit.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbSecondUnit);
		
		this.cbbUnit.setEnabled(false);
		this.cbbFirstUnit.setEnabled(false);
		this.cbbSecondUnit.setEnabled(false);
	}

	public void setVisible(boolean b) {
		if (b) {
			if (this.editListData != null && this.editListData.size() > 0) {
				this.innerMergeData = this.editListData.get(0);
			}
			showData();
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
			jContentPane.add(getBtnSave(), null);
			jContentPane.add(getBtnCancel(), null);
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
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(12, 97, 51, 23));
			jLabel3.setText("商品规格");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(12, 69, 51, 23));
			jLabel.setText("商品名称");
			jPanel = new JPanel();
			jLabel1 = new JLabel();
			jLabel2 = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(12, 22, 417, 253);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel1.setBounds(12, 39, 51, 23);
			jLabel1.setText("商品编码");
			jLabel2.setBounds(12, 12, 51, 23);
			jLabel2.setText("备案序号");
			jPanel.add(getTfUnitWeight(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getTfCode(), null);
			jPanel.add(getTfName(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel3, null);
			jPanel.add(getTfSpec(), null);
			jPanel.add(getLblDeclareUnit());
			jPanel.add(getLabel1());
			jPanel.add(getLabel());
			jPanel.add(getCbbUnit());
			jPanel.add(getCbbFirstUnit());
			jPanel.add(getCbbSecondUnit());
			jPanel.add(getLabel2());
			jPanel.add(getLabel3());
			jPanel.add(getTxtUnitRatio());
			jPanel.add(getTxtSecondUnitRatio());
			jPanel.add(getLabel_1());
			jPanel.add(getTfWeigthUnitGene());
			jPanel.add(getCbbIsMainImg());
		}
		return jPanel;
	}

	/**
	 * This method initializes jFormattedTextField1
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JTextField getTfUnitWeight() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(107, 70, 298, 23);
		}
		return tfName;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(276, 285, 65, 24);
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!validateData()) {
						return;
					}
					saveData();
					isOk = true;
					DgEditTenInnerMerge.this.dispose();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(351, 285, 65, 24);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgEditTenInnerMerge.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCode() {
		if (tfMemoNo == null) {
			tfMemoNo = new JTextField();
			tfMemoNo.setBounds(107, 12, 298, 23);
			tfMemoNo.setEditable(false);
			tfMemoNo.setBackground(java.awt.Color.white);
		}
		return tfMemoNo;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfCode == null) {
			tfCode = new JTextField();
			tfCode.setBounds(107, 40, 298, 23);
			tfCode.setEditable(false);
			tfCode.setBackground(java.awt.Color.white);
		}
		return tfCode;
	}

	private void showData() {
		if (this.innerMergeData == null) {
			return;
		}
		if (innerMergeData.getHsAfterComplex() != null) {
			this.tfCode.setText(innerMergeData.getHsAfterComplex().getCode());
		}
		
		if (innerMergeData.getHsAfterTenMemoNo() != null) {
			this.tfMemoNo.setText(String.valueOf(innerMergeData.getHsAfterTenMemoNo()));
		}
		this.tfName.setText(innerMergeData.getHsAfterMaterielTenName());
		this.tfSpec.setText(innerMergeData.getHsAfterMaterielTenSpec());
		
		this.cbbUnit.setSelectedItem(innerMergeData.getHsAfterMemoUnit());
		this.cbbFirstUnit.setSelectedItem(innerMergeData.getHsAfterLegalUnit());
		this.cbbSecondUnit.setSelectedItem(innerMergeData.getHsAfterSecondLegalUnit());
		this.txtUnitRatio.setText(CommonUtils.formatDoubleByDigitNull(innerMergeData.getFristUnitRatio(), 9));
		this.txtSecondUnitRatio.setText(CommonUtils.formatDoubleByDigitNull(innerMergeData.getSecondUnitRatio(),9));
		this.tfWeigthUnitGene.setText(CommonUtils.formatDoubleByDigitNull(innerMergeData.getWeigthUnitGene(), 9));
		this.cbbIsMainImg.setSelected(Boolean.TRUE.equals(innerMergeData.getIsMainImg()));
		
	}

	private void fillData() {
		if (this.innerMergeData == null) {
			return;
		}
		for (InnerMergeData data : editListData) {
			data.setHsAfterMaterielTenName(this.tfName.getText());
			data.setHsAfterMaterielTenSpec(this.tfSpec.getText());
			data.setUpdateDate(new Date());
			
			data.setFristUnitRatio(this.txtUnitRatio.getText().isEmpty() ? null : Double.valueOf(this.txtUnitRatio.getText()));
			data.setSecondUnitRatio(this.txtSecondUnitRatio.getText().isEmpty() ? null : Double.valueOf(this.txtSecondUnitRatio.getText()));
			data.setWeigthUnitGene(this.tfWeigthUnitGene.getText().isEmpty() ? null : Double.valueOf(this.tfWeigthUnitGene.getText()));
			data.setIsMainImg(this.cbbIsMainImg.isSelected());
		}
	}

	private void saveData() {
		if (this.innerMergeData == null) {
			return;
		}
		this.fillData();
		editListData = commonBaseCodeAction.saveInnerMergeData(new Request(
				CommonVars.getCurrUser()), editListData);
		tableModel.updateRows(editListData);
	}

	private boolean validateData() {
		if (this.tfName.getText() == null
				|| this.tfName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "名称规格不可为空!!", "警告!!",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public List<InnerMergeData> getEditListData() {
		return editListData;
	}

	public void setEditListData(List<InnerMergeData> editListData) {
		InnerMergeData data=editListData.get(0);
		this.editListData=commonBaseCodeAction.findInnerMergeDataByTenNo(CommonUtils.getRequest(), data.getImrType(),  String.valueOf((data.getHsAfterTenMemoNo())));
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSpec() {
		if (tfSpec == null) {
			tfSpec = new JTextField();
			tfSpec.setBounds(new Rectangle(107, 98, 298, 23));
		}
		return tfSpec;
	}
	private JLabel getLblDeclareUnit() {
		if (lblDeclareUnit == null) {
			lblDeclareUnit = new JLabel();
			lblDeclareUnit.setText("申报单位");
			lblDeclareUnit.setBounds(new Rectangle(12, 97, 51, 23));
			lblDeclareUnit.setBounds(12, 128, 51, 23);
		}
		return lblDeclareUnit;
	}
	private JLabel getLabel1() {
		if (label1 == null) {
			label1 = new JLabel();
			label1.setText("第一法定单位");
			label1.setBounds(new Rectangle(12, 97, 51, 23));
			label1.setBounds(12, 159, 72, 23);
		}
		return label1;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel();
			label.setText("第二法定单位");
			label.setBounds(new Rectangle(12, 97, 51, 23));
			label.setBounds(12, 190, 72, 23);
		}
		return label;
	}
	private JComboBox getCbbUnit() {
		if (cbbUnit == null) {
			cbbUnit = new JComboBox();
			cbbUnit.setBounds(107, 126, 298, 23);
		}
		return cbbUnit;
	}
	private JComboBox getCbbFirstUnit() {
		if (cbbFirstUnit == null) {
			cbbFirstUnit = new JComboBox();
			cbbFirstUnit.setBounds(107, 157, 103, 23);
		}
		return cbbFirstUnit;
	}
	private JComboBox getCbbSecondUnit() {
		if (cbbSecondUnit == null) {
			cbbSecondUnit = new JComboBox();
			cbbSecondUnit.setBounds(107, 188, 103, 23);
		}
		return cbbSecondUnit;
	}
	private JLabel getLabel2() {
		if (label2 == null) {
			label2 = new JLabel();
			label2.setText("比例因子");
			label2.setBounds(new Rectangle(12, 97, 51, 23));
			label2.setBounds(221, 159, 51, 23);
		}
		return label2;
	}
	private JLabel getLabel3() {
		if (label3 == null) {
			label3 = new JLabel();
			label3.setText("比例因子");
			label3.setBounds(new Rectangle(12, 97, 51, 23));
			label3.setBounds(221, 190, 51, 23);
		}
		return label3;
	}
	private JNumberTextField getTxtUnitRatio() {
		if (txtUnitRatio == null) {
			txtUnitRatio = new JNumberTextField();
			txtUnitRatio.setDecimalLenght(9);
			txtUnitRatio.setBounds(290, 159, 115, 22);
		}
		return txtUnitRatio;
	}
	private JNumberTextField getTxtSecondUnitRatio() {
		if (txtSecondUnitRatio == null) {
			txtSecondUnitRatio = new JNumberTextField();
			txtSecondUnitRatio.setDecimalLenght(9);
			txtSecondUnitRatio.setBounds(290, 190, 115, 22);
		}
		return txtSecondUnitRatio;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel();
			label_1.setText("重量单位比例因子");
			label_1.setBounds(new Rectangle(12, 97, 51, 23));
			label_1.setBounds(10, 220, 103, 23);
		}
		return label_1;
	}
	private JNumberTextField getTfWeigthUnitGene() {
		if (tfWeigthUnitGene == null) {
			tfWeigthUnitGene = new JNumberTextField();
			tfWeigthUnitGene.setDecimalLenght(9);
			tfWeigthUnitGene.setBounds(107, 221, 103, 22);
		}
		return tfWeigthUnitGene;
	}
	private JCheckBox getCbbIsMainImg() {
		if (cbbIsMainImg == null) {
			cbbIsMainImg = new JCheckBox("是否主料");
			cbbIsMainImg.setBounds(221, 219, 103, 23);
		}
		return cbbIsMainImg;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
