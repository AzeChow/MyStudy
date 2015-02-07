package com.bestway.client.fecav;

import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.common.Request;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavParameters;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
/*
 * 外汇核销管理参数设置
 * 
 * @author liusir
 * 
 * check 2008-8-30
 */
public class FmFecavParameters extends JInternalFrameBase {

	private JPanel pnContent = null;

	private JLabel lbUnder = null;

	private JCheckBox cbImpWhiteBillUseOnce = null;

	private JLabel lbInVerification = null;

	private JCustomFormattedTextField tfImpLowestMoney = null;

	private JLabel lbNoMoreVerification = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private FecavAction fecavAction = null;

	private JSeparator jSeparator = null;

	private JButton btnAmend = null;

	private JButton btnSave = null;

	private JButton btnClose = null;

	private int dataState = DataState.BROWSE;

	private JLabel lbOutDecimal_Place = null;

	private JCustomFormattedTextField tfFecavControlDigitsNum = null;

	private JLabel lbBit = null;

	public FmFecavParameters() {
		super();
		initialize();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
		FecavParameters fecavParameters = fecavAction
				.findFecavParameters(new Request(CommonVars.getCurrUser()));
		showData(fecavParameters);
		setState();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(725, 435));
		this.setContentPane(getPnContent());
		this.setTitle("参数设置");
	}

	/**
	 * This method initializes pnContent
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnContent() {
		if (pnContent == null) {
			lbBit = new JLabel();
			lbBit.setBounds(new java.awt.Rectangle(157, 54, 16, 21));
			lbBit.setText("位");
			lbOutDecimal_Place = new JLabel();
			lbOutDecimal_Place.setBounds(new java.awt.Rectangle(39, 24, 357, 25));
			lbOutDecimal_Place
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			lbOutDecimal_Place.setForeground(java.awt.Color.blue);
			lbOutDecimal_Place.setText("出口外汇管制中（核销值 物料值 结汇值）小数位数");
			lbNoMoreVerification = new JLabel();
			lbNoMoreVerification.setBounds(new java.awt.Rectangle(255, 167, 100, 22));
			lbNoMoreVerification.setText("就不再进行核销");
			lbInVerification = new JLabel();
			lbInVerification.setBounds(new java.awt.Rectangle(39, 98, 180, 24));
			lbInVerification
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			lbInVerification.setForeground(java.awt.Color.blue);
			lbInVerification.setText("进口白单核销参数");
			lbUnder = new JLabel();
			lbUnder.setBounds(new java.awt.Rectangle(126, 167, 80, 22));
			lbUnder.setText("白单余额低于");
			pnContent = new JPanel();
			pnContent.setLayout(null);
			pnContent.add(lbUnder, null);
			pnContent.add(getCbImpWhiteBillUseOnce(), null);
			pnContent.add(lbInVerification, null);
			pnContent.add(getTfImpLowestMoney(), null);
			pnContent.add(getJSeparator(), null);
			pnContent.add(getBtnAmend(), null);
			pnContent.add(getBtnSave(), null);
			pnContent.add(getBtnClose(), null);
			pnContent.add(lbNoMoreVerification, null);
			pnContent.add(lbOutDecimal_Place, null);
			pnContent.add(getTfFecavControlDigitsNum(), null);
			pnContent.add(lbBit, null);
		}
		return pnContent;
	}

	/**
	 * This method initializes cbImpWhiteBillUseOnce
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbImpWhiteBillUseOnce() {
		if (cbImpWhiteBillUseOnce == null) {
			cbImpWhiteBillUseOnce = new JCheckBox();
			cbImpWhiteBillUseOnce.setBounds(new java.awt.Rectangle(124, 124,
					258, 33));
			cbImpWhiteBillUseOnce.setText("每份白单只核销一次");
			cbImpWhiteBillUseOnce
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							setState();
						}
					});
		}
		return cbImpWhiteBillUseOnce;
	}

	/**
	 * This method initializes tfImpLowestMoney
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfImpLowestMoney() {
		if (tfImpLowestMoney == null) {
			tfImpLowestMoney = new JCustomFormattedTextField();
			tfImpLowestMoney
					.setBounds(new java.awt.Rectangle(206, 167, 47, 22));
			tfImpLowestMoney.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfImpLowestMoney;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
			defaultFormatterFactory.setNullFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	/**
	 * This method initializes jSeparator
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getJSeparator() {
		if (jSeparator == null) {
			jSeparator = new JSeparator();
			jSeparator.setBounds(new java.awt.Rectangle(39, 335, 644, 8));
		}
		return jSeparator;
	}

	/**
	 * This method initializes btnAmend
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAmend() {
		if (btnAmend == null) {
			btnAmend = new JButton();
			btnAmend.setBounds(new java.awt.Rectangle(352, 346, 67, 27));
			btnAmend.setText("修改");
			btnAmend.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnAmend;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(new java.awt.Rectangle(449, 346, 67, 27));
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FecavParameters fecavParameters = fecavAction
							.findFecavParameters(new Request(CommonVars
									.getCurrUser(), true));
					fillData(fecavParameters);
					fecavAction.saveFecavParameters(new Request(CommonVars
							.getCurrUser()), fecavParameters);
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setBounds(new java.awt.Rectangle(545, 346, 67, 27));
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmFecavParameters.this.doDefaultCloseAction();
				}
			});
		}
		return btnClose;
	}
	/**
	 * 数据显示
	 * 
	 * @param fecavParameters
	 */

	private void showData(FecavParameters fecavParameters) {
		if (fecavParameters == null) {
			return;
		}
		this.cbImpWhiteBillUseOnce.setSelected(fecavParameters
				.getImpWhiteBillUseOnce() != null
				&& fecavParameters.getImpWhiteBillUseOnce());
		this.tfImpLowestMoney.setValue(fecavParameters.getImpLowestMoney());
		this.tfFecavControlDigitsNum.setValue(fecavParameters
				.getFecavControlDigitsNum());
	}
	/**
	 * 填充数据
	 * @param fecavParameters
	 */

	private void fillData(FecavParameters fecavParameters) {
		if (fecavParameters == null) {
			return;
		}
		fecavParameters.setImpWhiteBillUseOnce(this.cbImpWhiteBillUseOnce
				.isSelected());
		if (this.tfImpLowestMoney.getValue() != null) {
			fecavParameters.setImpLowestMoney(Double
					.parseDouble(this.tfImpLowestMoney.getValue().toString()));
		} else {
			fecavParameters.setImpLowestMoney(0.0);
		}
		if (this.tfFecavControlDigitsNum.getValue() != null) {
			fecavParameters.setFecavControlDigitsNum(Double.valueOf(
					this.tfFecavControlDigitsNum.getValue().toString())
					.intValue());
		} else {
			fecavParameters.setFecavControlDigitsNum(0);
		}

	}
	/**
	 * 设置状态
	 */
	private void setState() {
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
		this.btnAmend.setEnabled(dataState == DataState.BROWSE);
		this.cbImpWhiteBillUseOnce.setEnabled(dataState != DataState.BROWSE);
		this.tfImpLowestMoney.setEnabled(!this.cbImpWhiteBillUseOnce
				.isSelected()
				&& dataState != DataState.BROWSE);
		this.tfFecavControlDigitsNum.setEnabled(dataState != DataState.BROWSE);
	}
	/**
	 * 绘制控件
	 */

	protected void paintComponent(Graphics g) {
		jSeparator.setBounds(3, this.pnContent.getHeight() - 50,
				this.pnContent.getWidth() - 6, 3);
		// // jPanel5.setBounds(28, this.jPanel1.getHeight() - 50,
		// this.jPanel1.getWidth() - 28 -28, 3);
		btnAmend.setBounds(this.pnContent.getWidth() - 392, this.pnContent
				.getHeight() - 40, 68, 26);
		btnSave.setBounds(this.pnContent.getWidth() - 292, this.pnContent
				.getHeight() - 40, 68, 26);
		btnClose.setBounds(this.pnContent.getWidth() - 192,
				this.pnContent.getHeight() - 40, 68, 26);
		super.paintComponent(g);
	}

	/**
	 * This method initializes tfFecavControlDigitsNum
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfFecavControlDigitsNum() {
		if (tfFecavControlDigitsNum == null) {
			DefaultFormatterFactory defaultFormatterFactory1 = new DefaultFormatterFactory();
			NumberFormatter numberFormatter1 = new NumberFormatter();
			DecimalFormat decimalFormat = new DecimalFormat(); // @jve:decl-index=0:
			decimalFormat.setMaximumFractionDigits(0);
			numberFormatter1.setFormat(decimalFormat);
			defaultFormatterFactory1.setNullFormatter(numberFormatter1);
			defaultFormatterFactory1.setEditFormatter(numberFormatter1);
			defaultFormatterFactory1.setDefaultFormatter(numberFormatter1);
			defaultFormatterFactory1.setDisplayFormatter(numberFormatter1);
			tfFecavControlDigitsNum = new JCustomFormattedTextField();
			tfFecavControlDigitsNum.setBounds(new java.awt.Rectangle(128, 54,
					30, 22));
			tfFecavControlDigitsNum
					.setFormatterFactory(defaultFormatterFactory1);
		}
		return tfFecavControlDigitsNum;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
