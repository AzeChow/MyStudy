/*
 * Created on 2004-7-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.checkcancel;

import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscState;
import com.bestway.dzsc.checkcancel.action.DzscContractCavAction;
import com.bestway.dzsc.checkcancel.entity.DzscCheckImg;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscCheckImg extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTextField jTextField = null;

	private JTextField jTextField1 = null;

	private JTextField jTextField2 = null;

	private JTextField jTextField3 = null;

	private JTextField jTextField4 = null;

	private JFormattedTextField jFormattedTextField3 = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private JTextField jTextField6 = null;

	private DzscContractCavAction dzscContractCavAction = null;

	private JTableListModel tableModel = null;

	private DzscCheckImg checkImg = null; // 料件

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private JFormattedTextField jFormattedTextField = null;

	private JFormattedTextField jFormattedTextField1 = null;

	private JFormattedTextField jFormattedTextField2 = null;

	private JFormattedTextField jFormattedTextField4 = null;

	private JFormattedTextField jFormattedTextField5 = null;

	private JFormattedTextField jFormattedTextField6 = null;

	private JFormattedTextField jFormattedTextField7 = null;

	private JFormattedTextField jFormattedTextField8 = null;

	private JFormattedTextField jFormattedTextField9 = null;

	private JFormattedTextField jFormattedTextField10 = null;

	private JFormattedTextField jFormattedTextField11 = null;

	private JFormattedTextField jFormattedTextField12 = null;

	private JFormattedTextField jFormattedTextField13 = null;

	private JFormattedTextField jFormattedTextField14 = null;

	private JFormattedTextField jFormattedTextField15 = null;

	/**
	 * This is the default constructor
	 */
	public DgDzscCheckImg() {
		super();
		initialize();
		dzscContractCavAction = (DzscContractCavAction) CommonVars
				.getApplicationContext().getBean("dzscContractCavAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(582, 347);
		this.setTitle("中期核查料件维护");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {

				if (tableModel.getCurrentRow() != null) {
					checkImg = (DzscCheckImg) tableModel.getCurrentRow();
					showData();
					setState();
				}
			}
		});
	}

	private void showData() {
		jTextField.setText(checkImg.getSeqNum());
		jTextField1.setText(checkImg.getPtNum());
		jTextField3.setText(checkImg.getName());
		jTextField2.setText(checkImg.getComplex().getCode());
		jTextField4.setText(checkImg.getSpec());
		jTextField6.setText(checkImg.getUnit().getName());
		jFormattedTextField3.setText(doubleToStr(checkImg.getTurnInNoReport()));
		jFormattedTextField.setText(doubleToStr(checkImg.getMaterByWay()));
		jFormattedTextField1.setText(doubleToStr(checkImg.getMaterStock()));
		jFormattedTextField2.setText(doubleToStr(checkImg.getDepose()));
		jFormattedTextField4.setText(doubleToStr(checkImg.getOnlines()));
		jFormattedTextField14.setText(doubleToStr(checkImg.getOverMater()));
		jFormattedTextField5
				.setText(doubleToStr(checkImg.getThisMaterInWare()));
		jFormattedTextField13.setText(doubleToStr(checkImg.getMaterGet()));
		jFormattedTextField6
				.setText(doubleToStr(checkImg.getThisMaterCancel()));
		jFormattedTextField7.setText(doubleToStr(checkImg.getThisThrow()));
		jFormattedTextField15.setText(doubleToStr(checkImg.getPassExgUse()));
		jFormattedTextField8.setText(doubleToStr(checkImg.getOtherConvert()));
		jFormattedTextField9.setText(doubleToStr(checkImg.getMaterReuse()));
		jFormattedTextField10
				.setText(doubleToStr(checkImg.getMaterExitChange()));
		jFormattedTextField11
				.setText(doubleToStr(checkImg.getHalfExgConvert()));
		jFormattedTextField12.setText(doubleToStr(checkImg
				.getThisThrowRemainExg()));
	}

	private void setState() {
		String state = DzscState.ORIGINAL;
		if (checkImg != null) {
			state = checkImg.getCheckHead().getDeclareState();
		}
		this.btnSave.setEnabled(DzscState.ORIGINAL.equals(state));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			javax.swing.JLabel jLabel21 = new JLabel();

			javax.swing.JLabel jLabel20 = new JLabel();

			javax.swing.JLabel jLabel19 = new JLabel();

			javax.swing.JLabel jLabel18 = new JLabel();

			javax.swing.JLabel jLabel17 = new JLabel();

			javax.swing.JLabel jLabel16 = new JLabel();

			javax.swing.JLabel jLabel15 = new JLabel();

			javax.swing.JLabel jLabel14 = new JLabel();

			javax.swing.JLabel jLabel13 = new JLabel();

			javax.swing.JLabel jLabel11 = new JLabel();

			javax.swing.JLabel jLabel9 = new JLabel();

			javax.swing.JLabel jLabel8 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel6 = new JLabel();

			javax.swing.JLabel jLabel12 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("料号");
			jLabel.setBounds(191, 22, 28, 18);
			jLabel1.setBounds(14, 22, 35, 17);
			jLabel1.setText("序号");
			jLabel2.setBounds(14, 49, 54, 20);
			jLabel2.setText("商品编码");
			jLabel3.setBounds(379, 22, 59, 17);
			jLabel3.setText("料件名称");
			jLabel4.setBounds(191, 49, 53, 20);
			jLabel4.setText("型号规格");
			jLabel10.setBounds(14, 75, 76, 21);
			jLabel10.setText("转进未报数量");
			jLabel12.setBounds(379, 75, 78, 21);
			jLabel12.setText("原料库存数量");
			jLabel6.setBounds(379, 49, 78, 21);
			jLabel6.setText("备案计量单位");
			jLabel7.setBounds(191, 75, 72, 19);
			jLabel7.setText("原料在途数量");
			jLabel5.setBounds(14, 101, 55, 20);
			jLabel5.setText("废料数量");
			jLabel8.setBounds(191, 101, 70, 20);
			jLabel8.setText("在线数量");
			jLabel9.setBounds(379, 101, 75, 21);
			jLabel9.setText("边角料数量");
			jLabel11.setBounds(14, 129, 100, 21);
			jLabel11.setText("本期原料入库数量");
			jLabel13.setBounds(300, 129, 87, 19);
			jLabel13.setText("原料领料数量");
			jLabel14.setBounds(14, 157, 101, 20);
			jLabel14.setText("本期原料内销数量");
			jLabel15.setBounds(300, 157, 100, 23);
			jLabel15.setText("本期放弃料件数量");
			jLabel16.setBounds(14, 183, 100, 21);
			jLabel16.setText("合格成品耗用数量");
			jLabel17.setBounds(300, 183, 123, 21);
			jLabel17.setText("废品，残次品折料数量");
			jLabel18.setBounds(14, 209, 101, 20);
			jLabel18.setText("原料复出");
			jLabel19.setBounds(300, 209, 62, 22);
			jLabel19.setText("原料退换");
			jLabel20.setBounds(14, 237, 99, 22);
			jLabel20.setText("半成品折料数量");
			jLabel21.setBounds(300, 237, 116, 22);
			jLabel21.setText("本期放弃残次品折料");
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJTextField3(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getJTextField4(), null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(getJFormattedTextField3(), null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(getBtnSave(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getJTextField6(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getJFormattedTextField(), null);
			jContentPane.add(getJFormattedTextField1(), null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getJFormattedTextField2(), null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(getJFormattedTextField4(), null);
			jContentPane.add(jLabel9, null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(getJFormattedTextField5(), null);
			jContentPane.add(jLabel13, null);
			jContentPane.add(jLabel14, null);
			jContentPane.add(getJFormattedTextField6(), null);
			jContentPane.add(jLabel15, null);
			jContentPane.add(getJFormattedTextField7(), null);
			jContentPane.add(jLabel16, null);
			jContentPane.add(jLabel17, null);
			jContentPane.add(getJFormattedTextField8(), null);
			jContentPane.add(jLabel18, null);
			jContentPane.add(getJFormattedTextField9(), null);
			jContentPane.add(jLabel19, null);
			jContentPane.add(getJFormattedTextField10(), null);
			jContentPane.add(jLabel20, null);
			jContentPane.add(getJFormattedTextField11(), null);
			jContentPane.add(jLabel21, null);
			jContentPane.add(getJFormattedTextField12(), null);
			jContentPane.add(getJFormattedTextField13(), null);
			jContentPane.add(getJFormattedTextField14(), null);
			jContentPane.add(getJFormattedTextField15(), null);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(90, 22, 92, 22);
			jTextField.setEditable(false);
		}
		return jTextField;
	}

	/**
	 * 
	 * This method initializes jTextField1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(268, 22, 93, 22);
			jTextField1.setEditable(false);
		}
		return jTextField1;
	}

	/**
	 * 
	 * This method initializes jTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(89, 49, 92, 22);
			jTextField2.setEditable(false);
		}
		return jTextField2;
	}

	/**
	 * 
	 * This method initializes jTextField3
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(463, 22, 98, 22);
			jTextField3.setEditable(false);
		}
		return jTextField3;
	}

	/**
	 * 
	 * This method initializes jTextField4
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setBounds(267, 49, 94, 22);
			jTextField4.setEditable(false);
		}
		return jTextField4;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField3
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField3() {
		if (jFormattedTextField3 == null) {
			jFormattedTextField3 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField3.setBounds(89, 75, 93, 22);
			jFormattedTextField3
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField3;
	}

	private Double strToDouble(String value) { // 转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				// return new Double("0");
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return (Double) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			// return new Double("0");
			return null;
		}
	}

	private String doubleToStr(Double value) { // 转换doubleToStr 取数据
		try {
			if (value == null || value.doubleValue() == 0) {
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return getNumberFormatter().valueToString(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
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
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(198, 276, 70, 25);
			btnSave.setText("确定");
			btnSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillDataImg(checkImg);
					checkImg = dzscContractCavAction.saveDzscCheckImg(
							new Request(CommonVars.getCurrUser()), checkImg);
					tableModel.updateRow(checkImg);
					DgDzscCheckImg.this.dispose();
				}
			});

		}
		return btnSave;
	}

	public void fillDataImg(DzscCheckImg checkImg) { // 保存
		checkImg.setTurnInNoReport(strToDouble(jFormattedTextField3.getText()));
		checkImg.setMaterByWay(strToDouble(jFormattedTextField.getText()));
		checkImg.setMaterStock(strToDouble(jFormattedTextField1.getText()));
		checkImg.setDepose(strToDouble(jFormattedTextField2.getText()));
		checkImg.setOnlines(strToDouble(jFormattedTextField4.getText()));
		checkImg.setOverMater(strToDouble(jFormattedTextField14.getText()));
		checkImg
				.setThisMaterInWare(strToDouble(jFormattedTextField5.getText()));
		checkImg.setMaterGet(strToDouble(jFormattedTextField13.getText()));
		checkImg
				.setThisMaterCancel(strToDouble(jFormattedTextField6.getText()));
		checkImg.setThisThrow(strToDouble(jFormattedTextField7.getText()));
		checkImg.setPassExgUse(strToDouble(jFormattedTextField15.getText()));
		checkImg
				.setMaterExitChange(strToDouble(jFormattedTextField10.getText()));
		checkImg
				.setHalfExgConvert(strToDouble(jFormattedTextField11.getText()));
		checkImg.setThisThrowRemainExg(strToDouble(jFormattedTextField12
				.getText()));
		checkImg.setMaterReuse(strToDouble(jFormattedTextField9.getText()));
		checkImg.setOtherConvert(strToDouble(jFormattedTextField8.getText()));

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
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(305, 277, 69, 25);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgDzscCheckImg.this.dispose();
				}
			});

		}
		return btnCancel;
	}

	/**
	 * 
	 * This method initializes jTextField6
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
			jTextField6.setBounds(462, 49, 98, 22);
			jTextField6.setEditable(false);
		}
		return jTextField6;
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
	 * 
	 * This method initializes defaultFormatterFactory
	 * 
	 * 
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 * 
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * 
	 * This method initializes numberFormatter
	 * 
	 * 
	 * 
	 * @return javax.swing.text.NumberFormatter
	 * 
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField() {
		if (jFormattedTextField == null) {
			jFormattedTextField = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField.setBounds(268, 75, 94, 22);
			jFormattedTextField
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField1
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField1() {
		if (jFormattedTextField1 == null) {
			jFormattedTextField1 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField1.setBounds(463, 75, 99, 22);
			jFormattedTextField1
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField1;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField2() {
		if (jFormattedTextField2 == null) {
			jFormattedTextField2 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField2.setBounds(89, 101, 93, 22);
			jFormattedTextField2
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField2;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField4
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField4() {
		if (jFormattedTextField4 == null) {
			jFormattedTextField4 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField4.setBounds(269, 101, 94, 22);
			jFormattedTextField4
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField4;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField5
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField5() {
		if (jFormattedTextField5 == null) {
			jFormattedTextField5 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField5.setBounds(118, 129, 159, 22);
			jFormattedTextField5
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField5;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField6
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField6() {
		if (jFormattedTextField6 == null) {
			jFormattedTextField6 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField6.setBounds(119, 157, 159, 22);
			jFormattedTextField6
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField6;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField7
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField7() {
		if (jFormattedTextField7 == null) {
			jFormattedTextField7 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField7.setBounds(397, 157, 163, 22);
			jFormattedTextField7
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField7;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField8
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField8() {
		if (jFormattedTextField8 == null) {
			jFormattedTextField8 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField8.setBounds(428, 183, 132, 22);
			jFormattedTextField8
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField8;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField9
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField9() {
		if (jFormattedTextField9 == null) {
			jFormattedTextField9 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField9.setBounds(119, 209, 159, 22);
			jFormattedTextField9
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField9;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField10
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField10() {
		if (jFormattedTextField10 == null) {
			jFormattedTextField10 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField10.setBounds(398, 209, 162, 22);
			jFormattedTextField10
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField10;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField11
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField11() {
		if (jFormattedTextField11 == null) {
			jFormattedTextField11 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField11.setBounds(120, 237, 158, 22);
			jFormattedTextField11
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField11;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField12
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField12() {
		if (jFormattedTextField12 == null) {
			jFormattedTextField12 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField12.setBounds(429, 237, 131, 22);
			jFormattedTextField12
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField12;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField13
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField13() {
		if (jFormattedTextField13 == null) {
			jFormattedTextField13 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField13.setBounds(397, 129, 164, 22);
			jFormattedTextField13
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField13;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField14
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField14() {
		if (jFormattedTextField14 == null) {
			jFormattedTextField14 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField14.setBounds(464, 100, 97, 22);
			jFormattedTextField14
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField14;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField15
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField15() {
		if (jFormattedTextField15 == null) {
			jFormattedTextField15 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField15.setBounds(120, 183, 157, 22);
			jFormattedTextField15
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField15;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
