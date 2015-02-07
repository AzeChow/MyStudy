/*
 * Created on 2004-7-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JLabel;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.SendState;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;

import javax.swing.JButton;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.JCheckBox;
import java.awt.Rectangle;
import java.awt.Dimension;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgEmsEdiMergerAfter extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTextField jTextField = null;

	private JTextField jTextField1 = null;

	private JTextField jTextField2 = null;

	private JTextField jTextField3 = null;

	private JTextField jTextField4 = null;

	private JTextField jTextField5 = null;

	private JTextField jTextField6 = null;

	private JFormattedTextField jFormattedTextField = null;

	private JTextField jTextField7 = null;

	private JFormattedTextField jFormattedTextField1 = null;

	private JFormattedTextField jFormattedTextField2 = null;

	private JFormattedTextField jFormattedTextField3 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private ManualDeclareAction manualdeclearAction = null;

	private JTableListModel tableModel = null;

	private EmsEdiMergerImgAfter emsImgAfter = null; // 料件

	private EmsEdiMergerExgAfter emsExgAfter = null; // 成品

	private EmsEdiMergerHead emsEdiMergerHead = null; // 归并关系表头

	private Curr curr = null; // 海关币制

	private boolean isImg = true; // 是否为料件还是成品

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private JTextField jTextField8 = null;

	private JButton jButton2 = null;

	private JLabel jLabel13 = null;
	private JFormattedTextField jFormattedTextField4 = null;
	private String scomplex = ""; // @jve:decl-index=0:
	private String sunit = ""; // @jve:decl-index=0:
	private Complex complex = null; // @jve:decl-index=0:

	private JButton jButton3 = null;

	private JButton jButton4 = null;

	private Unit unit = null; // 计量单位
	private String isSendSign = "";

	private JCheckBox cbIsMain = null;

	private JLabel jLabel15 = null;

	/**
	 * This is the default constructor
	 */
	public DgEmsEdiMergerAfter() {
		super();
		initialize();
		manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(481, 325);
		this.setTitle("编辑归并后料件");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				isSendSign = manualdeclearAction.getBpara(new Request(
						CommonVars.getCurrUser()), BcusParameter.EmsSEND_Sign);
				if (tableModel.getCurrentRow() != null) {
					if (DgEmsEdiMergerAfter.this.isImg) {
						emsImgAfter = (EmsEdiMergerImgAfter) tableModel
								.getCurrentRow();
						curr = emsImgAfter.getCurr();
						if (emsImgAfter.getUnit() != null)
							unit = emsImgAfter.getUnit();
						DgEmsEdiMergerAfter.this.setTitle("编辑归并后料件");
						fillWindowImg();
					} else {
						emsExgAfter = (EmsEdiMergerExgAfter) tableModel
								.getCurrentRow();
						curr = emsExgAfter.getCurr();
						if (emsExgAfter.getUnit() != null)
							unit = emsExgAfter.getUnit();
						DgEmsEdiMergerAfter.this.setTitle("编辑归并后成品");
						fillWindowExg();
					}
				}
			}
		});
	}

	private void fillWindowImg() {
		jTextField.setText(String.valueOf(emsImgAfter.getSeqNum()));
		if (emsImgAfter.getComplex() != null)
			jTextField1.setText(emsImgAfter.getComplex().getCode());
		else
			jTextField1.setText("");
		jTextField2.setText(emsImgAfter.getName());
		jTextField3.setText(emsImgAfter.getSpec());
		if (emsImgAfter.getUnit() != null)
			jTextField4.setText(emsImgAfter.getUnit().getName());
		else
			jTextField4.setText("");
		complex = emsImgAfter.getComplex();
		scomplex = complex.getCode();
		sunit = unit.getCode();
		if (emsImgAfter.getComplex().getFirstUnit() != null)
			jTextField5.setText(emsImgAfter.getComplex().getFirstUnit()
					.getName());
		else
			jTextField5.setText("");
		jFormattedTextField
				.setText(doubleToStr(emsImgAfter.getLegalUnitGene()));
		if (emsImgAfter.getComplex().getSecondUnit() != null)
			jTextField6.setText(emsImgAfter.getComplex().getSecondUnit()
					.getName());
		else
			jTextField6.setText("");
		jFormattedTextField1.setText(doubleToStr(emsImgAfter
				.getLegalUnit2Gene()));
		jFormattedTextField2.setText(doubleToStr(emsImgAfter
				.getWeigthUnitGene()));
		jFormattedTextField4.setText(doubleToStr(emsImgAfter.getPrice()));
		jFormattedTextField3
				.setText(doubleToStr(emsImgAfter.getMaxApprSpace()));
		jTextField7.setText(emsImgAfter.getNote());
		cbIsMain.setSelected(emsImgAfter.getIsMainImg() == null ? false
				: emsImgAfter.getIsMainImg());
		if (emsImgAfter.getCurr() != null)
			jTextField8.setText(emsImgAfter.getCurr().getName());
		else
			jTextField8.setText("");
	}

	private void fillWindowExg() {
		jTextField.setText(String.valueOf(emsExgAfter.getSeqNum()));
		if (emsExgAfter.getComplex() != null)
			jTextField1.setText(emsExgAfter.getComplex().getCode());
		else
			jTextField1.setText("");
		jTextField2.setText(emsExgAfter.getName());
		jTextField3.setText(emsExgAfter.getSpec());
		if (emsExgAfter.getUnit() != null)
			jTextField4.setText(emsExgAfter.getUnit().getName());
		else
			jTextField4.setText("");
		complex = emsExgAfter.getComplex();
		scomplex = complex.getCode();
		sunit = unit.getCode();
		if (emsExgAfter.getComplex().getFirstUnit() != null)
			jTextField5.setText(emsExgAfter.getComplex().getFirstUnit()
					.getName());
		else
			jTextField5.setText("");
		jFormattedTextField
				.setText(doubleToStr(emsExgAfter.getLegalUnitGene()));
		if (emsExgAfter.getComplex().getSecondUnit() != null)
			jTextField6.setText(emsExgAfter.getComplex().getSecondUnit()
					.getName());
		else
			jTextField6.setText("");
		jFormattedTextField1.setText(doubleToStr(emsExgAfter
				.getLegalUnit2Gene()));
		jFormattedTextField2.setText(doubleToStr(emsExgAfter
				.getWeigthUnitGene()));
		jFormattedTextField3
				.setText(doubleToStr(emsExgAfter.getMaxApprSpace()));
		jFormattedTextField4.setText(doubleToStr(emsExgAfter.getPrice()));
		jTextField7.setText(emsExgAfter.getNote());
		if (emsExgAfter.getCurr() != null)
			jTextField8.setText(emsExgAfter.getCurr().getName());
		else
			jTextField8.setText("");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(246, 187, 73, 23));
			jLabel15.setText("是否主料：");
			jLabel13 = new JLabel();
			javax.swing.JLabel jLabel12 = new JLabel();

			javax.swing.JLabel jLabel11 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();

			javax.swing.JLabel jLabel9 = new JLabel();

			javax.swing.JLabel jLabel8 = new JLabel();

			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel6 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("料件序号");
			jLabel.setBounds(19, 22, 53, 18);
			jLabel1.setBounds(249, 24, 60, 17);
			jLabel1.setText("商品编码");
			jLabel2.setBounds(19, 48, 54, 20);
			jLabel2.setText("商品名称");
			jLabel3.setBounds(249, 50, 59, 17);
			jLabel3.setText("型号规格");
			jLabel4.setBounds(18, 75, 53, 20);
			jLabel4.setText("计量单位");
			jLabel5.setBounds(248, 77, 75, 18);
			jLabel5.setText("法定计量单位");
			jLabel6.setBounds(19, 102, 99, 21);
			jLabel6.setText("法定单位比例因子");
			jLabel7.setBounds(249, 104, 76, 19);
			jLabel7.setText("第二法定单位");
			jLabel8.setBounds(18, 131, 125, 18);
			jLabel8.setText("第二法定单位比例因子");
			jLabel9.setBounds(248, 133, 77, 20);
			jLabel9.setText("重量比例因子");
			jLabel10.setBounds(18, 159, 75, 21);
			jLabel10.setText("批准最大余量");
			jLabel11.setBounds(248, 161, 32, 20);
			jLabel11.setText("币制");
			jLabel12.setBounds(18, 223, 65, 21);
			jLabel12.setText("备注");
			jLabel13.setBounds(17, 189, 72, 23);
			jLabel13.setText("企业申报单价");
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
			jContentPane.add(jLabel5, null);
			jContentPane.add(getJTextField5(), null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getJTextField6(), null);
			jContentPane.add(getJFormattedTextField(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(getJTextField7(), null);
			jContentPane.add(getJFormattedTextField1(), null);
			jContentPane.add(jLabel9, null);
			jContentPane.add(getJFormattedTextField2(), null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(getJFormattedTextField3(), null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJTextField8(), null);
			jContentPane.add(getJButton2(), null);
			jContentPane.add(jLabel13, null);
			jContentPane.add(getJFormattedTextField4(), null);
			jContentPane.add(getJButton3(), null);
			jContentPane.add(getJButton4(), null);
			jContentPane.add(getCbIsMain(), null);
			jContentPane.add(jLabel15, null);
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
			jTextField.setBounds(91, 22, 141, 25);
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
			jTextField1.setBounds(328, 23, 95, 22);
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
			jTextField2.setBounds(91, 48, 141, 25);
			// jTextField2.setEditable(false);
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
			jTextField3.setBounds(328, 49, 120, 22);
			// jTextField3.setEditable(false);
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
			jTextField4.setBounds(90, 75, 114, 22);
			jTextField4.setEditable(false);
		}
		return jTextField4;
	}

	/**
	 * 
	 * This method initializes jTextField5
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			jTextField5.setBounds(327, 76, 121, 22);
			jTextField5.setEditable(false);
		}
		return jTextField5;
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
			jTextField6.setBounds(328, 103, 120, 22);
			jTextField6.setEditable(false);
		}
		return jTextField6;
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
			jFormattedTextField.setBounds(142, 104, 90, 25);
			jFormattedTextField
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField;
	}

	/**
	 * 
	 * This method initializes jTextField7
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JTextField();
			jTextField7.setBounds(90, 223, 336, 22);
		}
		return jTextField7;
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
			jFormattedTextField1.setBounds(142, 131, 90, 25);
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
			jFormattedTextField2.setBounds(327, 132, 122, 22);
			jFormattedTextField2
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField2;
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
			jFormattedTextField3.setBounds(90, 159, 142, 25);
			jFormattedTextField3
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField3;
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
			jButton.setBounds(176, 256, 70, 25);
			jButton.setText("确定");

			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					boolean ischange = false;
					if (JOptionPane.showConfirmDialog(DgEmsEdiMergerAfter.this,
							"是否同时更新到【内部归并】中?", "提示", 0) == 0) {
						ischange = true;
					}

					if (DgEmsEdiMergerAfter.this.isImg) {

						if (ischange) {
							manualdeclearAction.changeInner(new Request(
									CommonVars.getCurrUser()),
									MaterielType.MATERIEL, emsImgAfter
											.getSeqNum(), complex, jTextField2
											.getText(), jTextField3.getText(),
									unit);
						}
						fillDataImg(emsImgAfter);

						emsImgAfter = manualdeclearAction
								.saveEmsEdiMergerImgAfter(new Request(
										CommonVars.getCurrUser()), emsImgAfter);
						tableModel.updateRow(emsImgAfter);
						DgEmsEdiMergerAfter.this.dispose();
					} else {

						if (ischange) {
							manualdeclearAction.changeInner(new Request(
									CommonVars.getCurrUser()),
									MaterielType.FINISHED_PRODUCT, emsExgAfter
											.getSeqNum(), complex, jTextField2
											.getText(), jTextField3.getText(),
									unit);
						}

						fillDataExg(emsExgAfter);
						emsExgAfter = manualdeclearAction
								.saveEmsEdiMergerExgAfter(new Request(
										CommonVars.getCurrUser()), emsExgAfter);
						tableModel.updateRow(emsExgAfter);
						DgEmsEdiMergerAfter.this.dispose();
					}

				}
			});

		}
		return jButton;
	}

	public void fillDataImg(EmsEdiMergerImgAfter emsAfter) { // 保存
		EmsEdiMergerImgAfter old = new EmsEdiMergerImgAfter();
		old = (EmsEdiMergerImgAfter) emsAfter.clone();
		emsAfter.setComplex(complex);
		emsAfter.setSpec(jTextField3.getText());
		emsAfter.setName(jTextField2.getText());
		emsAfter.setLegalUnitGene(strToDouble(jFormattedTextField.getText()));
		emsAfter.setLegalUnit2Gene(strToDouble(jFormattedTextField1.getText()));
		emsAfter.setWeigthUnitGene(strToDouble(jFormattedTextField2.getText()));
		emsAfter.setPrice(strToDouble(jFormattedTextField4.getText()));
		emsAfter.setMaxApprSpace(strToDouble(jFormattedTextField3.getText()));
		emsAfter.setNote(jTextField7.getText());
		emsAfter.setCurr(curr);
		emsAfter.setUnit(unit);
		boolean isMainIsChange = true;// 标记“是否主料”栏位是否有修改
		if ((emsAfter.getIsMainImg() != null && emsAfter.getIsMainImg().equals(
				(Boolean) cbIsMain.isSelected()))
				|| emsAfter.getModifyMark().equals("1"))
			isMainIsChange = false;
		emsAfter.setIsMainImg(cbIsMain.isSelected());
		if (!emsAfter.fullEquals(old)
				&& !emsImgAfter.getModifyMark().equals(ModifyMarkState.DELETED)
				&& !emsImgAfter.getModifyMark().equals(ModifyMarkState.ADDED)
				&& emsImgAfter.getEmsEdiMergerHead().getDeclareType()
						.equals(DelcareType.MODIFY)) {
			emsAfter.setModifyMark(ModifyMarkState.MODIFIED); // 已修改
			// if (isSendSign != null && "1".equals(isSendSign)){
			emsAfter.setSendState(Integer.valueOf(SendState.WAIT_SEND));// 准备申报
			// }
			emsAfter.setChangeDate(new Date());
			emsAfter.setModifyTimes(emsEdiMergerHead.getModifyTimes());
		}
		// SXK 修改于7.12
		manualdeclearAction.changeBeforeComplexUnit(
				new Request(CommonVars.getCurrUser()), emsAfter, scomplex,
				complex.getCode(), sunit, (unit == null ? "" : unit.getCode()),
				isMainIsChange);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + scomplex
				+ ">>>>>>>>>>>>>>>>>>" + complex.getCode());
	}

	public void fillDataExg(EmsEdiMergerExgAfter emsAfter) { // 保存
		EmsEdiMergerExgAfter old = new EmsEdiMergerExgAfter();
		old = (EmsEdiMergerExgAfter) emsAfter.clone();
		emsAfter.setComplex(complex);
		emsAfter.setSpec(jTextField3.getText());
		emsAfter.setName(jTextField2.getText());
		emsAfter.setLegalUnitGene(strToDouble(jFormattedTextField.getText()));
		emsAfter.setLegalUnit2Gene(strToDouble(jFormattedTextField1.getText()));
		emsAfter.setWeigthUnitGene(strToDouble(jFormattedTextField2.getText()));
		emsAfter.setMaxApprSpace(strToDouble(jFormattedTextField3.getText()));
		emsAfter.setPrice(strToDouble(jFormattedTextField4.getText()));
		emsAfter.setNote(jTextField7.getText());
		emsAfter.setCurr(curr);
		emsAfter.setUnit(unit);
		if (!emsAfter.fullEquals(old)
				&& !emsExgAfter.getModifyMark().equals(ModifyMarkState.DELETED)
				&& !emsExgAfter.getModifyMark().equals(ModifyMarkState.ADDED)
				&& emsExgAfter.getEmsEdiMergerHead().getDeclareType()
						.equals(DelcareType.MODIFY)) {
			emsAfter.setModifyMark(ModifyMarkState.MODIFIED); // 已修改
			// if (isSendSign != null && "1".equals(isSendSign)){
			emsAfter.setSendState(Integer.valueOf(SendState.WAIT_SEND));// 准备申报
			// }
			emsAfter.setChangeDate(new Date());
			emsAfter.setModifyTimes(emsEdiMergerHead.getModifyTimes());
		}

		// SXK 修改于7.12
		manualdeclearAction.changeBeforeComplexUnit(
				new Request(CommonVars.getCurrUser()), emsAfter, scomplex,
				complex.getCode(), sunit, (unit == null ? "" : unit.getCode()));
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
			jButton1.setBounds(258, 256, 69, 25);
			jButton1.setText("取消");

			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgEmsEdiMergerAfter.this.dispose();

				}
			});

		}
		return jButton1;
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
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setMaximumFractionDigits(9);
			decimalFormat.setGroupingSize(0);
			decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
			numberFormatter.setFormat(decimalFormat);
		}
		return numberFormatter;
	}

	/**
	 * 
	 * This method initializes jTextField8
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField8() {
		if (jTextField8 == null) {
			jTextField8 = new JTextField();
			jTextField8.setBounds(327, 160, 99, 22);
			jTextField8.setEditable(false);
		}
		return jTextField8;
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(424, 160, 25, 22);
			jButton2.setText("...");
			jButton2.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					Curr c = (Curr) CommonQuery.getInstance().getCustomCurr();
					if (c != null) {
						curr = c;
						getJTextField8().setText(c.getName());
					}
				}
			});
		}
		return jButton2;
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
	 * @return Returns the isImg.
	 */
	public boolean isImg() {
		return isImg;
	}

	/**
	 * @param isImg
	 *            The isImg to set.
	 */
	public void setImg(boolean isImg) {
		this.isImg = isImg;
	}

	/**
	 * @return Returns the emsEdiMergerHead.
	 */
	public EmsEdiMergerHead getEmsEdiMergerHead() {
		return emsEdiMergerHead;
	}

	/**
	 * @param emsEdiMergerHead
	 *            The emsEdiMergerHead to set.
	 */
	public void setEmsEdiMergerHead(EmsEdiMergerHead emsEdiMergerHead) {
		this.emsEdiMergerHead = emsEdiMergerHead;
	}

	/**
	 * This method initializes jFormattedTextField4
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJFormattedTextField4() {
		if (jFormattedTextField4 == null) {
			jFormattedTextField4 = new JFormattedTextField();
			jFormattedTextField4.setBounds(91, 190, 142, 25);
			jFormattedTextField4
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField4;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(421, 23, 27, 22));
			jButton3.setText("...");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Complex s = (Complex) CommonQuery.getInstance()
							.getComplex();
					if (s != null) {
						complex = s;
						getJTextField1().setText(s.getCode());
						if (DgEmsEdiMergerAfter.this.isImg) {
							if (complex.getFirstUnit() != null)
								jTextField5.setText(complex.getFirstUnit()
										.getName());
							else
								jTextField5.setText("");
							if (complex.getSecondUnit() != null)
								jTextField6.setText(complex.getSecondUnit()
										.getName());
							else
								jTextField6.setText("");
						} else {
							if (complex.getFirstUnit() != null)
								jTextField5.setText(complex.getFirstUnit()
										.getName());
							else
								jTextField5.setText("");
							if (complex.getSecondUnit() != null)
								jTextField6.setText(complex.getSecondUnit()
										.getName());
							else
								jTextField6.setText("");
						}
					}
				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(new Rectangle(204, 75, 26, 22));
			jButton4.setText("...");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Unit c = (Unit) CommonQuery.getInstance().getCustomUnit();
					if (c != null) {
						unit = c;
						getJTextField4().setText(c.getName());
					}
				}
			});
		}
		return jButton4;
	}

	/**
	 * This method initializes cbIsMain
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsMain() {
		if (cbIsMain == null) {
			cbIsMain = new JCheckBox();
			cbIsMain.setBounds(new Rectangle(324, 189, 58, 18));
			cbIsMain.setText("主料");
		}
		return cbIsMain;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
