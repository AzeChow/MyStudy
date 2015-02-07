/*
 * Created on 2004-7-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import com.bestway.ui.winuicontrol.JDialogBase;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;

/**
 * 选择要海关申报的事项
 * 
 * @author lxr
 * 
 */
public class DgCancelSelectDeclare extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JButton btnOk = null;
	private JButton btnCancel = null;

	private JCheckBox cbHeadOrImg = null;
	private JCheckBox cbCalcImg = null;
	private JCheckBox cbExg = null;
	private JCheckBox cbCalcExg = null;
	private boolean isSelectOk = false;
	private HashMap<String, Boolean> parameter = new HashMap<String, Boolean>(); // @jve:decl-index=0:

	public void setParameter(HashMap<String, Boolean> parameter) {
		this.parameter = parameter;
	}

	public HashMap<String, Boolean> getParameter() {
		return parameter;
	}

	public boolean isSelectOk() {
		return isSelectOk;
	}

	public void setSelectOk(boolean isSelectOk) {
		this.isSelectOk = isSelectOk;
	}

	/**
	 * This is the default constructor
	 */
	public DgCancelSelectDeclare() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(424, 318);
		this.setTitle("申报项选择");
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(35, 17, 344, 209));
			jPanel.setBorder(BorderFactory.createTitledBorder(null,
					"\u9009\u62e9\u8981\u7533\u62a5\u7684\u4e8b\u9879",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel.add(getCbHeadOrImg(), null);
			jPanel.add(getCbCalcImg(), null);
			jPanel.add(getCbExg(), null);
			jPanel.add(getCbCalcExg(), null);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(getBtnOk(), null);
			jPanel1.add(getBtnCancel(), null);
			jPanel1.add(getJPanel(), null);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes btnOk
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(139, 243, 67, 28);
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					parameter
							.put("isSelectHeadOrImg", cbHeadOrImg.isSelected());
					parameter.put("isCalcImg", cbCalcImg.isSelected());
					parameter.put("isSelectExg", cbExg.isSelected());
					parameter.put("isCalcExg", cbCalcExg.isSelected());

					setParameter(parameter);
					isSelectOk = true;
					DgCancelSelectDeclare.this.dispose();
				}
			});

		}
		return btnOk;
	}

	/**
	 * 
	 * This method initializes btnCancel
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(220, 242, 63, 28);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					isSelectOk = false;
					DgCancelSelectDeclare.this.dispose();
				}
			});

		}
		return btnCancel;
	}

	/**
	 * This method initializes cbHeadOrImg
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbHeadOrImg() {
		if (cbHeadOrImg == null) {
			cbHeadOrImg = new JCheckBox();
			cbHeadOrImg.setBounds(new Rectangle(63, 39, 215, 21));
			cbHeadOrImg.setSelected(true);
			cbHeadOrImg.setEnabled(false);
			cbHeadOrImg.setText("报核表头及报核料件表");
		}
		return cbHeadOrImg;
	}

	/**
	 * This method initializes cbCalcImg
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCalcImg() {
		if (cbCalcImg == null) {
			cbCalcImg = new JCheckBox();
			cbCalcImg.setSelected(true);
			cbCalcImg.setBounds(new Rectangle(63, 78, 145, 21));
			cbCalcImg.setText("核算料件表");
		}
		return cbCalcImg;
	}

	/**
	 * This method initializes cbExg
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbExg() {
		if (cbExg == null) {
			cbExg = new JCheckBox();
			cbExg.setSelected(true);
			cbExg.setBounds(new Rectangle(63, 119, 112, 21));
			cbExg.setText("报核成品表");
		}
		return cbExg;
	}

	/**
	 * This method initializes cbCalcExg
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCalcExg() {
		if (cbCalcExg == null) {
			cbCalcExg = new JCheckBox();
			cbCalcExg.setSelected(true);
			cbCalcExg.setBounds(new Rectangle(63, 163, 87, 21));
			cbCalcExg.setText("核算成品表");
		}
		return cbCalcExg;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
