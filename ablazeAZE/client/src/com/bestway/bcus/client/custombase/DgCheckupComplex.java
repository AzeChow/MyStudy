package com.bestway.bcus.client.custombase;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.hscode.CheckupComplex;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgCheckupComplex extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel lbCode = null;

	private JLabel lbName = null;

	private JLabel lbFirstUnit = null;

	private JLabel lbSecondUnit = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private JTextField tfCode = null;

	private JTextField tfName = null;

	private JComboBox cbbFirstUnit = null;

	private JComboBox cbbSecondUnit = null;

	private boolean isOk = false;

	private CheckupComplex checkComplex = null;  //  @jve:decl-index=0:

	private CustomBaseAction customBaseAction = null;

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private JLabel jLabel = null;

	private JTextField tfccontrol = null;

	
	
	public CheckupComplex getCheckComplex() {
		return checkComplex;
	}

	public void setCheckComplex(CheckupComplex checkComplex) {
		this.checkComplex = checkComplex;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgCheckupComplex() {
		super();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		initialize();
		initUIComponents();
	}

	public void setVisible(boolean b) {
		if (b) {
			showData();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(377, 220));
		this.setContentPane(getJContentPane());
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("商品编码资料编辑");

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(15, 102, 73, 18));
			jLabel.setText("许可证代码");
			lbSecondUnit = new JLabel();
			lbSecondUnit.setBounds(new java.awt.Rectangle(185, 73, 76, 18));
			lbSecondUnit.setText("第二法定单位");
			lbFirstUnit = new JLabel();
			lbFirstUnit.setBounds(new java.awt.Rectangle(16, 71, 74, 18));
			lbFirstUnit.setText("第一法定单位");
			lbName = new JLabel();
			lbName.setBounds(new java.awt.Rectangle(16, 43, 52, 18));
			lbName.setText("商品名称");
			lbCode = new JLabel();
			lbCode.setBounds(new java.awt.Rectangle(16, 14, 66, 18));
			lbCode.setText("商品编码");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(lbCode, null);
			jContentPane.add(lbName, null);
			jContentPane.add(lbFirstUnit, null);
			jContentPane.add(lbSecondUnit, null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getTfCode(), null);
			jContentPane.add(getTfName(), null);
			jContentPane.add(getCbbFirstUnit(), null);
			jContentPane.add(getCbbSecondUnit(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfccontrol(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new Rectangle(93, 147, 80, 24));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(checkComplex==null){
						return;
					}
					Complex complex=checkComplex.getComplex();
					complex.setCcontrol(tfccontrol.getText());
					customBaseAction.saveCheckupComplex(new Request(CommonVars.getCurrUser()), complex);
					isOk = true;
					dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(203, 147, 79, 23));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOk = false;
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes tfCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCode() {
		if (tfCode == null) {
			tfCode = new JTextField();
			tfCode.setEditable(false);
			tfCode.setBounds(new Rectangle(91, 13, 247, 22));
		}
		return tfCode;
	}

	/**
	 * This method initializes tfName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setEditable(false);
			tfName.setBounds(new Rectangle(91, 42, 248, 22));
		}
		return tfName;
	}

	/**
	 * This method initializes cbbFirstUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFirstUnit() {
		if (cbbFirstUnit == null) {
			cbbFirstUnit = new JComboBox();
			cbbFirstUnit.setBounds(new java.awt.Rectangle(91, 71, 83, 21));
		}
		return cbbFirstUnit;
	}

	/**
	 * This method initializes cbbSecondUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSecondUnit() {
		if (cbbSecondUnit == null) {
			cbbSecondUnit = new JComboBox();
			cbbSecondUnit.setBounds(new Rectangle(257, 71, 81, 21));
		}
		return cbbSecondUnit;
	}

	private void initUIComponents() {
		// 初始化第一单位
		this.cbbFirstUnit
				.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbFirstUnit.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbFirstUnit);
		// 初始化第二单位
		this.cbbSecondUnit.setModel(CustomBaseModel.getInstance()
				.getUnitModel());
		this.cbbSecondUnit.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbSecondUnit);
		cbbFirstUnit.setEnabled(false);
		cbbSecondUnit.setEnabled(false);
	}

	private void showData() {
		if (checkComplex == null) {
			return;
		}
		Complex complex=checkComplex.getComplex();
		this.tfCode.setText(complex.getCode());
		this.tfName.setText(complex.getName());

		this.cbbFirstUnit.setSelectedItem(complex.getFirstUnit());
		this.cbbSecondUnit.setSelectedItem(complex.getSecondUnit());
		this.tfccontrol.setText(complex.getCcontrol());
	}



	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * This method initializes tfccontrol	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfccontrol() {
		if (tfccontrol == null) {
			tfccontrol = new JTextField();
			tfccontrol.setBounds(new Rectangle(92, 102, 246, 22));
		}
		return tfccontrol;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
