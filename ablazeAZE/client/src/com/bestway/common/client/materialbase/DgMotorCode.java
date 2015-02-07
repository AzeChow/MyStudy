/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.MotorCode;
import com.bestway.ui.winuicontrol.JDialogBase;

import java.awt.Rectangle;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgMotorCode extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private MaterialManageAction materialManageAction = null;

	private boolean isAdd = true;

	private MotorCode motor = null; //

	private JTextField tfCode = null;

	private JTextField tfName = null;

	private JTextField tfSign = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private JTextField jTextField = null;

	private JTextField jTextField1 = null;

	private JTextField jTextField2 = null;

	private JTextField jTextField4 = null;

	private JTextField jTextField5 = null;

	private JTextField jTextField6 = null;

	private JComboBox cbbCustoms = null;

	private JLabel jLabel10 = null;

	private JTextField tfSealNumbers = null;

	private JLabel jLabel11 = null;

	private JTextField tfPassWord = null;

	private JLabel jLabel12 = null;

	private JTextField jTextField3 = null;

	/**
	 * This is the default constructor
	 */
	public DgMotorCode() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel2());
		this.setSize(392, 432);
		this.setTitle("司机资料设置编辑");

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {

			}
		});
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			initUICompoments();
			if (isAdd == false) {
				if (tableModel.getCurrentRow() != null) {
					motor = (MotorCode) tableModel.getCurrentRow();
				}
				fillWindow();
			}
		}
		super.setVisible(b);
	}

	private void initUICompoments() {
		// 初始化出口口岸
		this.cbbCustoms
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		this.cbbCustoms.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCustoms);
		this.cbbCustoms.setSelectedIndex(-1);
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(40, 326, 90, 18));
			jLabel12.setText("货柜箱型号规格");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(40, 301, 78, 19));
			jLabel11.setText("密码");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(40, 276, 78, 19));
			jLabel10.setText("封条号码");
			jLabel9 = new JLabel();
			jLabel8 = new JLabel();
			jLabel7 = new JLabel();
			jLabel6 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setText("代码");
			jLabel.setBounds(40, 16, 33, 23);
			jLabel2.setText("司机姓名");
			jLabel2.setBounds(40, 41, 57, 23);
			jLabel5.setText("国内车牌");
			jLabel5.setBounds(40, 67, 59, 23);
			jLabel1.setText("香港车牌");
			jLabel1.setBounds(40, 94, 57, 23);
			jLabel3.setBounds(40, 122, 89, 24);
			jLabel3.setText("司机本海关编码");
			jLabel4.setBounds(40, 147, 59, 22);
			jLabel4.setText("IC卡");
			jLabel6.setBounds(40, 172, 63, 22);
			jLabel6.setText("结关口岸");
			jLabel7.setBounds(40, 198, 61, 25);
			jLabel7.setText("运输单位");
			jLabel8.setBounds(40, 226, 78, 19);
			jLabel8.setText("运输单位地址");
			jLabel9.setBounds(40, 250, 78, 19);
			jLabel9.setText("运输单位电话");
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getTfCode(), null);
			jPanel2.add(getTfName(), null);
			jPanel2.add(getTfSign(), null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(jLabel7, null);
			jPanel2.add(jLabel8, null);
			jPanel2.add(jLabel9, null);
			jPanel2.add(getJTextField(), null);
			jPanel2.add(getJTextField1(), null);
			jPanel2.add(getJTextField2(), null);
			jPanel2.add(getJTextField4(), null);
			jPanel2.add(getJTextField5(), null);
			jPanel2.add(getJTextField6(), null);
			jPanel2.add(getCbbCustoms(), null);
			jPanel2.add(jLabel10, null);
			jPanel2.add(getTfSealNumbers(), null);
			jPanel2.add(jLabel11, null);
			jPanel2.add(getTfPassWord(), null);
			jPanel2.add(jLabel12, null);
			jPanel2.add(getJTextField3(), null);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("确定");
			jButton.setMnemonic(java.awt.event.KeyEvent.VK_S);
			jButton.setBounds(102, 361, 75, 26);
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (isAdd == true) {
						if(addData()){
							clearData();
						}
					} else {
						modifyData();
						DgMotorCode.this.dispose();
					}
					// if(tfCode.getSize()!= null){
					// JOptionPane.showMessageDialog(DgMotorCode.this,
					// "请检查编号", "提示", JOptionPane.OK_OPTION);
					// }

				}
			});

		}
		return jButton;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("取消");
			jButton1.setMnemonic(java.awt.event.KeyEvent.VK_X);
			jButton1.setBounds(204, 361, 74, 25);
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgMotorCode.this.dispose();

				}

			});

		}
		return jButton1;
	}

	private void fillWindow() {
		if (motor != null) {
			this.tfCode.setText(motor.getCode());
			this.tfName.setText(motor.getName());
			this.tfSign.setText(motor.getHomeCard());
			jTextField.setText(motor.getHongkongCard());
			jTextField1.setText(motor.getComplex());
			jTextField2.setText(motor.getIcCard());
			this.cbbCustoms.setSelectedIndex(getCbbCustomsIndexByCode(motor
					.getCustomsPort()));
			jTextField4.setText(motor.getTrafficUnit());
			jTextField5.setText(motor.getTrafficUnitAdd());
			jTextField6.setText(motor.getTrafficUnitTel());
			this.tfSealNumbers.setText(motor.getSealNumbers() == null ? ""
					: motor.getSealNumbers());
			this.tfPassWord.setText(motor.getPassWord() == null ? "" : motor
					.getPassWord());
			jTextField3.setText(motor.getContainerSpec());
		}
	}

	private int getCbbCustomsIndexByCode(String customsCode) {
		if (customsCode == null || "".equals(customsCode.trim())) {
			return -1;
		}
		for (int i = 0; i < this.cbbCustoms.getItemCount(); i++) {
			Customs c = (Customs) this.cbbCustoms.getItemAt(i);
			if (c != null && c.getCode().equals(customsCode)) {
				return i;
			}
		}
		return -1;
	}

	private void fillCurrCode(MotorCode motor) {
		motor.setCode(this.tfCode.getText().trim());
		motor.setName(this.tfName.getText().trim());
		motor.setHomeCard(this.tfSign.getText().trim());
		motor.setHongkongCard(this.jTextField.getText().trim());
		motor.setComplex(jTextField1.getText().trim());
		motor.setIcCard(jTextField2.getText().trim());
		if (this.cbbCustoms.getSelectedItem() != null) {
			motor.setCustomsPort(((Customs) this.cbbCustoms.getSelectedItem())
					.getCode());
		} else {
			motor.setCustomsPort("");
		}
		motor.setTrafficUnit(jTextField4.getText().trim());
		motor.setTrafficUnitAdd(jTextField5.getText().trim());
		motor.setTrafficUnitTel(jTextField6.getText().trim());
		motor.setSealNumbers(this.tfSealNumbers.getText().trim());
		motor.setPassWord(this.tfPassWord.getText().trim());
		motor.setCompany(CommonVars.getCurrUser().getCompany());
		motor.setContainerSpec(jTextField3.getText().trim());
	}

	private void clearData() {
		this.tfCode.setText("");
		this.tfName.setText("");
		this.tfSign.setText("");
		jTextField.setText("");
		jTextField1.setText("");
		jTextField2.setText("");
		this.cbbCustoms.setSelectedItem(null);
		jTextField4.setText("");
		jTextField5.setText("");
		jTextField6.setText("");
		jTextField3.setText("");
	}

	class CustomDocument extends PlainDocument {
		@Override
		public void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException {
			if (str == null) {
				return;
			}
			if (super.getLength() >= 10 || str.length() > 10
					|| super.getLength() + str.length() > 10) {
				return;
			}
			super.insertString(offs, str, a);
		}
	}

	private boolean addData() {
		MotorCode motor = new MotorCode();
		fillCurrCode(motor);
		boolean isExists = materialManageAction.checkMotorCodeHomeCard(new Request(CommonVars
				.getCurrUser()),motor);
		if(isExists){
			JOptionPane.showMessageDialog(this, "国内车牌或海关编码已经存在！");
			return false;
		}
		motor = materialManageAction.saveMotorCode(new Request(CommonVars
				.getCurrUser()), motor);
		tableModel.addRow(motor);
		System.out.println("保存成功！");
		return true;
	}

	private void modifyData() {
		fillCurrCode(motor);
		motor = materialManageAction.saveMotorCode(new Request(CommonVars
				.getCurrUser()), motor);
		tableModel.updateRow(motor);
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * @return Returns the isAdd.
	 */
	public boolean isAdd() {
		return isAdd;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setIsAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	/**
	 * 
	 * This method initializes tfCode
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfCode() {
		if (tfCode == null) {
			tfCode = new JTextField();
			tfCode.setDocument(new CustomDocument());
			tfCode.setBounds(130, 17, 162, 22);
		}
		return tfCode;
	}

	/**
	 * 
	 * This method initializes tfName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(130, 43, 162, 22);
		}
		return tfName;
	}

	/**
	 * 
	 * This method initializes tfSign
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfSign() {
		if (tfSign == null) {
			tfSign = new JTextField();
			tfSign.setBounds(130, 69, 162, 22);
		}
		return tfSign;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(130, 96, 162, 22);
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(130, 122, 162, 22);
			jTextField1.setDocument(new CustomDocument());
		}
		return jTextField1;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(130, 148, 162, 22);
		}
		return jTextField2;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setBounds(130, 200, 221, 22);
		}
		return jTextField4;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			jTextField5.setBounds(130, 226, 222, 22);
		}
		return jTextField5;
	}

	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
			jTextField6.setBounds(130, 250, 162, 22);
		}
		return jTextField6;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustoms() {
		if (cbbCustoms == null) {
			cbbCustoms = new JComboBox();
			cbbCustoms.setBounds(new java.awt.Rectangle(130, 172, 161, 24));
		}
		return cbbCustoms;
	}

	/**
	 * This method initializes tfSealNumbers
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSealNumbers() {
		if (tfSealNumbers == null) {
			tfSealNumbers = new JTextField();
			tfSealNumbers.setBounds(new Rectangle(130, 276, 162, 22));
		}
		return tfSealNumbers;
	}

	/**
	 * This method initializes tfPassWord
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPassWord() {
		if (tfPassWord == null) {
			tfPassWord = new JTextField();
			tfPassWord.setBounds(new Rectangle(130, 301, 162, 22));
		}
		return tfPassWord;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(new Rectangle(129, 325, 204, 22));
		}
		return jTextField3;
	}
} // @jve:decl-index=0:visual-constraint="100,100"
