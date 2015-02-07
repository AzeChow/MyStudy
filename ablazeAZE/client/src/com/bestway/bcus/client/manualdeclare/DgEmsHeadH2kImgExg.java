/*
 * Created on 2004-7-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.Rectangle;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.SendState;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JNumberTextField;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgEmsHeadH2kImgExg extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTextField jTextField = null;

	private JTextField jTextField1 = null;

	private JTextField jTextField2 = null;

	private JTextField jTextField3 = null;

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

	private EmsHeadH2kImg emsImg = null; // 料件

	private EmsHeadH2kExg emsExg = null; // 成品

	private EmsHeadH2k emsHeadH2k = null; // 表头 // @jve:decl-index=0:

	private Curr curr = null; // 海关币制

	private Complex complex = null;// 商品编码库

	private LevyMode levyMode = null; // 征免方式

	private boolean isChange = false;

	private Unit unit = null; // 计量单位

	private boolean isImg = true; // 是否为料件还是成品

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private JTextField jTextField8 = null;

	private JButton jButton2 = null;

	private JTextField jTextField9 = null;

	private JButton jButton3 = null;

	private JFormattedTextField jFormattedTextField4 = null;

	private JTextField jTextField4 = null;

	private JButton jButton4 = null;

	private JButton jButton5 = null;

	private boolean isModityComplex = false;

	private String seqNum = null;

	private JLabel jLabel15 = null;

	private JTextField jTextField10 = null;

	private JLabel jLabel16 = null;

	private String isEmsH2kSendSign = "";

	private Integer sendState;

	private JCustomFormattedTextField tfQty = null;

	private JLabel jLabel17 = null;

	private JLabel jLabel171 = null;

	// private JCustomFormattedTextField tffirstQty = null;

	// private JFormattedTextField tffirstQty;

	private JNumberTextField tffirstQty;

	private JLabel jLabel1711 = null;

	private JCheckBox cbIsMainMateriel = null;

	/**
	 * This is the default constructor
	 */
	public DgEmsHeadH2kImgExg() {
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
		this.setSize(522, 379);
		this.setTitle("编辑电子帐册料件");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				isEmsH2kSendSign = manualdeclearAction.getBpara(new Request(
						CommonVars.getCurrUser()),
						BcusParameter.EmsEdiH2kSend_Sign);
				if (tableModel.getCurrentRow() != null) {
					if (DgEmsHeadH2kImgExg.this.isImg) {
						emsImg = (EmsHeadH2kImg) tableModel.getCurrentRow();
						curr = emsImg.getCurr();
						seqNum = String.valueOf(emsImg.getSeqNum());
						complex = emsImg.getComplex();
						levyMode = emsImg.getLevyMode();
						unit = emsImg.getUnit();
						sendState = emsImg.getSendState();
						DgEmsHeadH2kImgExg.this.setTitle("编辑电子帐册料件");
						fillWindowImg();
					} else {
						emsExg = (EmsHeadH2kExg) tableModel.getCurrentRow();
						curr = emsExg.getCurr();
						seqNum = String.valueOf(emsExg.getSeqNum());
						complex = emsExg.getComplex();
						levyMode = emsExg.getLevyMode();
						unit = emsExg.getUnit();
						sendState = emsExg.getSendState();
						DgEmsHeadH2kImgExg.this.setTitle("编辑电子帐册成品");
						fillWindowExg();
					}
				}
				setstate();
			}
		});
	}

	private void setstate() {
		jLabel15.setVisible(DgEmsHeadH2kImgExg.this.isImg);
		jLabel16.setVisible(DgEmsHeadH2kImgExg.this.isImg);
		jTextField10.setVisible(DgEmsHeadH2kImgExg.this.isImg);
		jButton4.setEnabled(!SendState.SEND.equals(sendState == null ? "0"
				: String.valueOf(sendState)));

	}

	private void fillWindowImg() {
		jTextField.setText(String.valueOf(emsImg.getSeqNum()));
		getJTextField9().setText("全免");
		if (complex != null) {
			jTextField1.setText(complex.getCode());
		} else
			jTextField1.setText("");
		jTextField2.setText(emsImg.getName());
		jTextField3.setText(emsImg.getSpec());
		if (emsImg.getUnit() != null)
			jTextField4.setText(emsImg.getUnit().getName());
		else
			jTextField4.setText("");
		if (emsImg.getComplex().getFirstUnit() != null)
			jTextField5.setText(emsImg.getComplex().getFirstUnit().getName());
		else
			jTextField5.setText("");
		jFormattedTextField.setText(doubleToStr(emsImg.getLegalUnitGene()));
		if (emsImg.getComplex().getSecondUnit() != null)
			jTextField6.setText(emsImg.getComplex().getSecondUnit().getName());
		else
			jTextField6.setText("");
		jFormattedTextField1.setText(doubleToStr(emsImg.getLegalUnit2Gene()));
		jFormattedTextField2.setText(doubleToStr(emsImg.getWeigthUnitGene()));
		if (emsImg.getLevyMode() != null)
			jTextField9.setText(emsImg.getLevyMode().getName());
		else
			jTextField9.setText("");
		jFormattedTextField3.setText(doubleToStr(emsImg.getDeclarePrice()));
		jFormattedTextField4.setText(doubleToStr(emsImg.getDeclarePriceMo()));
		tfQty.setText(doubleToStr(emsImg.getQty()));
		cbIsMainMateriel.setSelected(emsImg.getIsMainImg() == null ? false
				: emsImg.getIsMainImg());
		// this.tffirstQty.setText(doubleToStr(emsImg.getFirstQty()));

		tffirstQty.setText(emsImg.getFirstQty() == null ? "" : emsImg
				.getFirstQty().toString());

		jTextField7.setText(emsImg.getNote());
		jTextField10.setText(emsImg.getDetailNote());
		if (emsImg.getCurr() != null) {
			jTextField8.setText(emsImg.getCurr().getName());
		} else
			jTextField8.setText("");
	}

	private void fillWindowExg() {
		jTextField.setText(String.valueOf(emsExg.getSeqNum()));
		if (complex != null)
			jTextField1.setText(complex.getCode());
		else
			jTextField1.setText("");
		jTextField2.setText(emsExg.getName());
		jTextField3.setText(emsExg.getSpec());
		if (emsExg.getUnit() != null)
			jTextField4.setText(emsExg.getUnit().getName());
		else
			jTextField4.setText("");
		if (emsExg.getComplex().getFirstUnit() != null)
			jTextField5.setText(emsExg.getComplex().getFirstUnit().getName());
		else
			jTextField5.setText("");
		jFormattedTextField.setText(doubleToStr(emsExg.getLegalUnitGene()));
		if (emsExg.getComplex().getSecondUnit() != null)
			jTextField6.setText(emsExg.getComplex().getSecondUnit().getName());
		else
			jTextField6.setText("");
		jFormattedTextField1.setText(doubleToStr(emsExg.getLegalUnit2Gene()));
		jFormattedTextField2.setText(doubleToStr(emsExg.getWeigthUnitGene()));
		if (emsExg.getLevyMode() != null)
			jTextField9.setText(emsExg.getLevyMode().getName());
		else
			jTextField9.setText("全免");
		if (emsExg.getDeclarePrice() != null)
			jFormattedTextField3.setText(doubleToStr(emsExg.getDeclarePrice()));
		else
			jFormattedTextField3.setText(doubleToStr(0.0));
		jFormattedTextField4.setText(doubleToStr(emsExg.getDeclarePriceMo()));
		tfQty.setText(doubleToStr(emsExg.getQty()));

		// this.tffirstQty.setText(doubleToStr(emsExg.getFirstQty()));
		tffirstQty.setText(emsExg.getFirstQty() == null ? "" : emsExg
				.getFirstQty().toString());
		jTextField7.setText(emsExg.getNote());
		if (emsExg.getCurr() != null)
			jTextField8.setText(emsExg.getCurr().getName());
		else
			jTextField8.setText("");

		// jFormattedTextField4.setText(String.valueOf(0));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel1711 = new JLabel();
			jLabel1711.setBounds(new Rectangle(367, 243, 69, 21));
			jLabel1711.setText("是否主料：");
			jLabel171 = new JLabel();
			jLabel171.setBounds(new Rectangle(276, 222, 82, 18));
			jLabel171.setText("初始库存数");
			jLabel17 = new JLabel();
			jLabel17.setBounds(new Rectangle(24, 218, 76, 21));
			jLabel17.setText("数量");
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(24, 287, 72, 19));
			jLabel16.setText("(报关单附页)");
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(24, 272, 72, 21));
			jLabel15.setForeground(java.awt.Color.black);
			jLabel15.setText("详细型号规格");
			javax.swing.JLabel jLabel14 = new JLabel();

			javax.swing.JLabel jLabel13 = new JLabel();

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
			jLabel.setBounds(27, 22, 53, 18);
			jLabel1.setBounds(278, 24, 60, 17);
			jLabel1.setText("商品编码");
			jLabel2.setBounds(27, 48, 54, 20);
			jLabel2.setText("商品名称");
			jLabel3.setBounds(278, 50, 59, 17);
			jLabel3.setText("型号规格");
			jLabel4.setBounds(26, 75, 53, 20);
			jLabel4.setText("计量单位");
			jLabel5.setBounds(277, 77, 75, 18);
			jLabel5.setText("法定计量单位");
			jLabel6.setBounds(27, 102, 99, 21);
			jLabel6.setText("法定单位比例因子");
			jLabel7.setBounds(278, 104, 76, 19);
			jLabel7.setText("第二法定单位");
			jLabel8.setBounds(26, 131, 125, 18);
			jLabel8.setText("第二法定单位比例因子");
			jLabel9.setBounds(277, 133, 77, 20);
			jLabel9.setText("重量比例因子");
			jLabel10.setBounds(24, 188, 76, 21);
			jLabel10.setText("企业申报单价");
			jLabel11.setBounds(277, 161, 32, 20);
			jLabel11.setText("币制");
			jLabel12.setBounds(24, 246, 30, 21);
			jLabel12.setText("备注");
			jLabel13.setBounds(26, 157, 68, 22);
			jLabel13.setText("征免方式");
			jLabel14.setBounds(277, 192, 91, 20);
			jLabel14.setText("申报单价人民币");
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJTextField3(), null);
			jContentPane.add(jLabel4, null);
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
			jContentPane.add(getJTextField9(), null);
			jContentPane.add(getJButton3(), null);
			jContentPane.add(jLabel14, null);
			jContentPane.add(getJFormattedTextField4(), null);
			jContentPane.add(getJTextField4(), null);
			jContentPane.add(getJButton4(), null);
			jContentPane.add(getJButton5(), null);
			jContentPane.add(jLabel15, null);
			jContentPane.add(getJTextField10(), null);
			jContentPane.add(jLabel16, null);
			jContentPane.add(getTfQty(), null);
			jContentPane.add(jLabel17, null);
			jContentPane.add(jLabel171, null);
			jContentPane.add(getTffirstQty(), null);
			jContentPane.add(jLabel1711, null);
			jContentPane.add(getCbIsMainMateriel(), null);
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
			jTextField.setBounds(99, 22, 150, 22);
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
			jTextField1.setBounds(365, 23, 121, 22);
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
			jTextField2.setBounds(99, 48, 150, 22);
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
			jTextField3.setBounds(365, 49, 120, 22);
			// jTextField3.setEditable(false);
		}
		return jTextField3;
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
			jTextField5.setBounds(364, 76, 121, 22);
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
			jTextField6.setBounds(365, 103, 120, 22);
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
			jFormattedTextField.setBounds(150, 104, 99, 22);
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
			jTextField7.setBounds(100, 243, 261, 22);
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
			jFormattedTextField1.setBounds(151, 131, 99, 22);
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
			jFormattedTextField2.setBounds(364, 132, 122, 22);
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
			jFormattedTextField3.setBounds(100, 189, 151, 22);
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

			jButton.setBounds(131, 307, 70, 25);

			jButton.setText("确定");

			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (checkIsSendState()) {

						return;

					}

					boolean ischange = false;

					if (JOptionPane.showConfirmDialog(DgEmsHeadH2kImgExg.this,
							"是否同时更新到【内部归并】中?", "提示", 0) == 0) {

						ischange = true;

					}

					if (DgEmsHeadH2kImgExg.this.isImg) {

						if (ischange) {

							manualdeclearAction.changeInner(new Request(
									CommonVars.getCurrUser()),
									MaterielType.MATERIEL, emsImg.getSeqNum(),
									complex, jTextField2.getText(), jTextField3
											.getText(), unit);

						}

						fillDataImg(emsImg);

						emsImg = manualdeclearAction.saveEmsHeadH2kImg(
								new Request(CommonVars.getCurrUser()), emsImg);

						tableModel.updateRow(emsImg);

						DgEmsHeadH2kImgExg.this.dispose();

					} else {

						if (ischange) {

							manualdeclearAction.changeInner(new Request(
									CommonVars.getCurrUser()),
									MaterielType.FINISHED_PRODUCT, emsExg
											.getSeqNum(), complex, jTextField2
											.getText(), jTextField3.getText(),
									unit);
						}

						fillDataExg(emsExg);

						emsExg = manualdeclearAction.saveEmsHeadH2kExg(
								new Request(CommonVars.getCurrUser()), emsExg);

						tableModel.updateRow(emsExg);

						DgEmsHeadH2kImgExg.this.dispose();

					}
				}
			});

		}
		return jButton;
	}

	/**
	 * 判断此账册是否已经发送
	 * 
	 * @return
	 */
	private boolean checkIsSendState() {
		emsHeadH2k = manualdeclearAction.getH2k(
				new Request(CommonVars.getCurrUser()), emsHeadH2k.getId());
		if (DeclareState.WAIT_EAA.equals(emsHeadH2k.getDeclareState())) {
			JOptionPane.showMessageDialog(DgEmsHeadH2kImgExg.this,
					"此账册已经发送，正等待审批，不能执行此动作！");
			return true;
		}
		return false;
	}

	public void fillDataImg(EmsHeadH2kImg emsAfter) { // 保存
		EmsHeadH2kImg old = new EmsHeadH2kImg();
		old = (EmsHeadH2kImg) emsAfter.clone();
		emsAfter.setUnit(unit);
		if (isModityComplex) {
			emsAfter.setComplex(complex);
		}
		emsAfter.setName(jTextField2.getText());
		emsAfter.setSpec(jTextField3.getText());
		emsAfter.setLegalUnitGene(strToDouble(jFormattedTextField.getText()));
		emsAfter.setLegalUnit2Gene(strToDouble(jFormattedTextField1.getText()));
		emsAfter.setWeigthUnitGene(strToDouble(jFormattedTextField2.getText()));
		emsAfter.setDeclarePrice(strToDouble(jFormattedTextField3.getText()));
		emsAfter.setDeclarePriceMo(strToDouble(jFormattedTextField4.getText()));
		emsAfter.setQty(strToDouble(tfQty.getText()));
		emsAfter.setIsMainImg((Boolean) cbIsMainMateriel.isSelected());

		String tffirstQtyStr = tffirstQty.getText();

		// 初始库存数 获取值 如果用户并没有填入任何数值 那么就直接设置 这个 数值 为 null 保存
		if (tffirstQtyStr.equals("") || tffirstQtyStr.length() == 0
				|| tffirstQtyStr == null) {

			emsAfter.setFirstQty(null);

		} else {

			emsAfter.setFirstQty(Double.parseDouble(tffirstQtyStr));

		}

		// String str = tffirstQty.getText() == null ? "0" :
		// tffirstQty.getText()
		// .toString();
		//
		// if (str == null || "".equals(str)) {
		//
		// emsAfter.setFirstQty(0d);
		//
		// } else {
		//
		// emsAfter.setFirstQty(Double.parseDouble(str));
		// }

		emsAfter.setCurr(curr);
		emsAfter.setLevyMode(levyMode);
		emsAfter.setNote(jTextField7.getText());
		if (!emsAfter.fullEquals(old)
				&& !emsImg.getModifyMark().equals(ModifyMarkState.DELETED)
				&& !emsImg.getModifyMark().equals(ModifyMarkState.ADDED)
				&& emsImg.getEmsHeadH2k().getDeclareType()
						.equals(DelcareType.MODIFY)) {
			emsAfter.setModifyMark(ModifyMarkState.MODIFIED);
			// if (isEmsH2kSendSign != null && "1".equals(isEmsH2kSendSign)){
			emsAfter.setSendState(1);// 准备申报
			// }
			emsAfter.setModifyTimes(emsAfter.getEmsHeadH2k().getModifyTimes());// 变更次数
			emsAfter.setChangeDate(new Date());
		}
		emsAfter.setDetailNote(jTextField10.getText());
		// 修改BOM 单价,名称，规格，单位，编码
		manualdeclearAction.modityBomFromPrice(
				new Request(CommonVars.getCurrUser()), seqNum, emsAfter,
				emsImg.getEmsHeadH2k());

	}

	// 保存
	public void fillDataExg(EmsHeadH2kExg emsAfter) {

		EmsHeadH2kExg old = new EmsHeadH2kExg();

		old = (EmsHeadH2kExg) emsAfter.clone();

		emsAfter.setUnit(unit);

		if (isModityComplex) {

			emsAfter.setComplex(complex);

		}

		emsAfter.setName(jTextField2.getText());

		emsAfter.setSpec(jTextField3.getText());

		emsAfter.setLegalUnitGene(strToDouble(jFormattedTextField.getText()));

		emsAfter.setLegalUnit2Gene(strToDouble(jFormattedTextField1.getText()));

		emsAfter.setWeigthUnitGene(strToDouble(jFormattedTextField2.getText()));

		emsAfter.setDeclarePrice(strToDouble(jFormattedTextField3.getText()));

		emsAfter.setDeclarePriceMo(strToDouble(jFormattedTextField4.getText()));

		emsAfter.setQty(strToDouble(tfQty.getText()));

		String tffirstQtyStr = tffirstQty.getText();

		// 初始库存数 获取值 如果用户并没有填入任何数值 那么就直接设置 这个 数值 为 null 保存
		if (tffirstQtyStr.equals("") || tffirstQtyStr.length() == 0
				|| tffirstQtyStr == "") {

			emsAfter.setFirstQty(null);

		} else {

			emsAfter.setFirstQty(Double.parseDouble(tffirstQtyStr));

		}

		// String str = tffirstQty.getText() == null ? "" : tffirstQty.getText()
		// .toString();
		//
		// if (str == null || "".equals(str)) {
		//
		// emsAfter.setFirstQty(null);
		//
		// } else {
		//
		// emsAfter.setFirstQty(Double.parseDouble(str));
		//
		// }

		emsAfter.setCurr(curr);

		emsAfter.setLevyMode(levyMode);

		emsAfter.setNote(jTextField7.getText());

		if (!emsAfter.fullEquals(old)
				&& !emsExg.getModifyMark().equals(ModifyMarkState.DELETED)
				&& !emsExg.getModifyMark().equals(ModifyMarkState.ADDED)
				&& emsExg.getEmsHeadH2k().getDeclareType()
						.equals(DelcareType.MODIFY)) {

			emsAfter.setModifyMark(ModifyMarkState.MODIFIED);

			// if (isEmsH2kSendSign != null && "1".equals(isEmsH2kSendSign)){
			emsAfter.setSendState(1);// 准备申报

			// }
			emsAfter.setModifyTimes(emsAfter.getEmsHeadH2k().getModifyTimes());// 变更次数

			emsAfter.setChangeDate(new Date());
		}
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
			jButton1.setBounds(218, 307, 69, 25);
			jButton1.setText("取消");

			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgEmsHeadH2kImgExg.this.dispose();

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
			jTextField8.setBounds(364, 160, 99, 22);
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
			jButton2.setBounds(461, 160, 25, 22);
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
	 * 
	 * This method initializes jTextField9
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField9() {
		if (jTextField9 == null) {
			jTextField9 = new JTextField();
			jTextField9.setEditable(false);
			jTextField9.setBounds(100, 158, 126, 22);
		}
		return jTextField9;
	}

	/**
	 * 
	 * This method initializes jButton3
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(226, 157, 23, 23);
			jButton3.setText("...");
			jButton3.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					LevyMode c = (LevyMode) CommonQuery.getInstance()
							.getLevyMode();
					if (c != null) {
						levyMode = c;
						getJTextField9().setText(c.getName());
					}
				}
			});

		}
		return jButton3;
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
			jFormattedTextField4.setBounds(366, 191, 122, 22);
			jFormattedTextField4
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField4;
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
			jTextField4.setEditable(false);
			jTextField4.setBounds(99, 75, 128, 22);
		}
		return jTextField4;
	}

	/**
	 * 
	 * This method initializes jButton4
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(227, 74, 22, 21);
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
	 * @return Returns the isChange.
	 */
	public boolean isChange() {
		return isChange;
	}

	/**
	 * @param isChange
	 *            The isChange to set.
	 */
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	/**
	 * @return Returns the emsHeadH2k.
	 */
	public EmsHeadH2k getEmsHeadH2k() {
		return emsHeadH2k;
	}

	/**
	 * @param emsHeadH2k
	 *            The emsHeadH2k to set.
	 */
	public void setEmsHeadH2k(EmsHeadH2k emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setBounds(305, 307, 91, 25);
			jButton5.setText("变更编码");
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Complex s = (Complex) CommonQuery.getInstance()
							.getComplex();
					if (s != null) {
						complex = s;
						getJTextField1().setText(s.getCode());
						isModityComplex = true;
						manualdeclearAction.modityComplex(new Request(
								CommonVars.getCurrUser()), isImg, seqNum,
								complex);
						if (isImg) {
							manualdeclearAction.modityBomFromComplex(
									new Request(CommonVars.getCurrUser()),
									seqNum, complex, emsImg.getEmsHeadH2k());
						}
					}
				}
			});
		}
		return jButton5;
	}

	/**
	 * This method initializes jTextField10
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField10() {
		if (jTextField10 == null) {
			jTextField10 = new JTextField();
			jTextField10.setBounds(new Rectangle(100, 273, 388, 23));
		}
		return jTextField10;
	}

	/**
	 * This method initializes jFormattedTextField31
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfQty() {
		if (tfQty == null) {
			tfQty = new JCustomFormattedTextField();
			tfQty.setBounds(new Rectangle(101, 218, 151, 22));
			tfQty.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfQty;
	}

	/**
	 * This method initializes tffirstQty
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	// private JCustomFormattedTextField getTffirstQty() {
	// if (tffirstQty == null) {
	// DefaultFormatterFactory defaultFormatterFactory1 = new
	// DefaultFormatterFactory();
	// NumberFormatter format = new NumberFormatter();
	// NumberFormat f = NumberFormat.getInstance();
	// f.setMaximumFractionDigits(5);
	// f.setGroupingUsed(false);
	// format.setFormat(f);
	// defaultFormatterFactory1.setDefaultFormatter(format);
	// defaultFormatterFactory1.setDisplayFormatter(format);
	// tffirstQty = new JCustomFormattedTextField();
	// tffirstQty.setBounds(new Rectangle(365, 219, 123, 22));
	// tffirstQty.setFormatterFactory(defaultFormatterFactory1);
	// }
	// return tffirstQty;
	// }

	/**
	 * 初始化 组件 栏位 <初始库存数>
	 *
	 * @return
	 */
	private JNumberTextField getTffirstQty() {

		if (tffirstQty == null) {

			tffirstQty = new JNumberTextField();

			tffirstQty.setBounds(new Rectangle(365, 219, 123, 22));

		}

		return tffirstQty;

	}

	/**
	 * This method initializes cbIsMainMateriel
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsMainMateriel() {
		if (cbIsMainMateriel == null) {
			cbIsMainMateriel = new JCheckBox();
			cbIsMainMateriel.setBounds(new Rectangle(437, 243, 51, 20));
			cbIsMainMateriel.setText("主料");

		}
		return cbIsMainMateriel;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
