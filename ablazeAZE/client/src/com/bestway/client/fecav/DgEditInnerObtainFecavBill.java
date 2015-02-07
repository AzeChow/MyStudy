package com.bestway.client.fecav;

import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.common.Request;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgEditInnerObtainFecavBill extends JDialogBase {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JLabel jLabel1 = null;
	private JTextField jTextField1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JTextField jTextField3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JButton BtnOk = null;
	private JButton BtnCancel = null;
	private FecavBill fecavBill = null; // @jve:decl-index=0:
	private FecavAction fecavAction = null;
	private boolean isOk = false;
	private JComboBox jComboBox = null;
	private JCalendarComboBox JCalendarComboBox = null;
	private JCalendarComboBox JCalendarComboBox1 = null;

	/**
	 * @param owner
	 */
	public DgEditInnerObtainFecavBill() {
		super();
		initialize();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(343, 302);
		this.setTitle("编辑内部领单");
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(44, 180, 94, 18));
			jLabel5.setText("出口口岸");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(43, 146, 94, 18));
			jLabel4.setText("内部操作日期");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(45, 114, 94, 18));
			jLabel3.setText("内部操作人");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(44, 81, 94, 18));
			jLabel2.setText("内部领单日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(46, 45, 94, 18));
			jLabel1.setText("内部领单人");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(45, 8, 94, 18));
			jLabel.setText("核销单号");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJTextField3(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getJComboBox(), null);
			jContentPane.add(getJCalendarComboBox1(), null);
			jContentPane.add(getJCalendarComboBox(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setEditable(false);
			jTextField.setBounds(new Rectangle(145, 6, 150, 22));
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
			jTextField1.setEditable(false);
			jTextField1.setBounds(new Rectangle(145, 45, 153, 24));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setEditable(false);
			jTextField3.setBounds(new Rectangle(145, 114, 153, 24));
		}
		return jTextField3;
	}

	/**
	 * This method initializes BtnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (BtnOk == null) {
			BtnOk = new JButton();
			BtnOk.setBounds(new Rectangle(49, 218, 91, 27));
			BtnOk.setText("确定");
			BtnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveFecavBill(); // TODO Auto-generated Event stub
					// actionPerformed()
				}
			});
		}
		return BtnOk;
	}

	public void setVisible(boolean b) {
		if (b) {
			// 进出口岸
			this.jComboBox.setModel(CustomBaseModel.getInstance()
					.getCustomModel());
			this.jComboBox.setRenderer(CustomBaseRender.getInstance()
					.getRender());
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					this.jComboBox);
			this.jComboBox.setSelectedIndex(-1);
			showData();
		}
		super.setVisible(b);
	}

	private void showData() {
		if (this.fecavBill == null) {
			return;
		}
		this.jTextField.setText(this.fecavBill.getCode());
		this.jTextField1.setText(this.fecavBill.getInnerObtain());
		this.JCalendarComboBox.setValue(this.fecavBill.getInnerObtainDate());
		this.jTextField3.setText(this.fecavBill.getInnerOperator());
		this.JCalendarComboBox1.setValue(this.fecavBill.getInnerOperatorDate());
		this.jComboBox.setSelectedItem(this.fecavBill.getImpExpCIQ());
	}

	/**
	 * 保存内部领单
	 * 
	 */
	private void saveFecavBill() {
		if (fecavBill == null) {
			return;
		}
		// if (jComboBox.getSelectedItem() != null) {
		fillData();
		fecavAction.saveFecavBillAndUpdateCustomsDeclaration(new Request(
				CommonVars.getCurrUser()), fecavBill);
		isOk = true;
		// }
		dispose();
	}

	private void fillData() {
		if (this.fecavBill == null) {
			return;
		}
		if (jComboBox.getSelectedItem() != null) {
			fecavBill.setImpExpCIQ((Customs) this.jComboBox.getSelectedItem());
		} else {
			fecavBill.setImpExpCIQ(null);
		}

	}

	/**
	 * This method initializes BtnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (BtnCancel == null) {
			BtnCancel = new JButton();
			BtnCancel.setBounds(new Rectangle(191, 218, 91, 27));
			BtnCancel.setText("　取消");
			BtnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose(); // TODO Auto-generated Event stub
					// actionPerformed()
				}
			});
		}
		return BtnCancel;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public FecavBill getFecavBill() {
		return fecavBill;
	}

	public void setFecavBill(FecavBill fecavBill) {
		this.fecavBill = fecavBill;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(144, 177, 153, 24));
		}
		return jComboBox;
	}

	/**
	 * This method initializes JCalendarComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (JCalendarComboBox == null) {
			JCalendarComboBox = new JCalendarComboBox();
			JCalendarComboBox.setDate(null);
			JCalendarComboBox.setEnabled(false);
			JCalendarComboBox.setBounds(new Rectangle(145, 80, 153, 24));
		}
		return JCalendarComboBox;
	}

	/**
	 * This method initializes JCalendarComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (JCalendarComboBox1 == null) {
			JCalendarComboBox1 = new JCalendarComboBox();
			JCalendarComboBox1.setDate(null);
			JCalendarComboBox1.setEnabled(false);
			JCalendarComboBox1.setBounds(new Rectangle(145, 144, 153, 24));
		}
		return JCalendarComboBox1;
	}

} // @jve:decl-index=0:visual-constraint="228,45"
