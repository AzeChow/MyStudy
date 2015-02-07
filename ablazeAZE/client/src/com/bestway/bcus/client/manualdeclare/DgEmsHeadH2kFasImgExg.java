/*
 * Created on 2004-7-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.text.ParseException;

import javax.swing.JLabel;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;

import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFas;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFasExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFasImg;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;

import javax.swing.JButton;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgEmsHeadH2kFasImgExg extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTextField jTextField = null;

	private JTextField jTextField1 = null;

	private JTextField jTextField2 = null;

	private JTextField jTextField3 = null;

	private JFormattedTextField jFormattedTextField = null;

	private JTextField jTextField7 = null;

	private JFormattedTextField jFormattedTextField3 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private ManualDeclareAction manualdeclearAction = null;

	private JTableListModel tableModel = null;

	private EmsHeadH2kFasImg emsImg = null; //料件

	private EmsHeadH2kFasExg emsExg = null; //成品
	
	private EmsHeadH2kFas emsHeadH2kFas = null; //表头

	private Curr curr = null; //海关币制

	private boolean isChange = false; 

	private Unit unit = null; //计量单位

	private boolean isImg = true; //是否为料件还是成品

	private DefaultFormatterFactory defaultFormatterFactory = null; //  @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; //  @jve:decl-index=0:parse

	private JTextField jTextField8 = null;

	private JButton jButton2 = null;

	private JTextField jTextField4 = null;

	private JButton jButton4 = null;

	/**
	 * This is the default constructor
	 */
	public DgEmsHeadH2kFasImgExg() {
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
		this.setSize(517, 248);
		this.setTitle("编辑电子分册料件");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {

				if (tableModel.getCurrentRow() != null) {
					if (DgEmsHeadH2kFasImgExg.this.isImg) {
						emsImg = (EmsHeadH2kFasImg) tableModel
								.getCurrentRow();
						curr = emsImg.getCurr();
						unit = emsImg.getUnit();
						DgEmsHeadH2kFasImgExg.this.setTitle("编辑电子分册料件");
						fillWindowImg();
					} else {
						emsExg = (EmsHeadH2kFasExg) tableModel
								.getCurrentRow();
						curr = emsExg.getCurr();
						unit = emsExg.getUnit();
						DgEmsHeadH2kFasImgExg.this.setTitle("编辑电子分册成品");
						fillWindowExg();
					}
				}
			}
		});
	}

	private void fillWindowImg() {
		jTextField.setText(String.valueOf(emsImg.getSeqNum()));
		if (emsImg.getComplex()!=null)
		  jTextField1.setText(emsImg.getComplex().getCode());
		else
			jTextField1.setText("");
		jTextField2.setText(emsImg.getName());
		jTextField3.setText(emsImg.getSpec());
		if (emsImg.getUnit()!=null)
		    jTextField4.setText(emsImg.getUnit().getName());
		else
			jTextField4.setText("");
		jFormattedTextField3.setText(doubleToStr(emsImg.getDeclarePrice()));
		jFormattedTextField.setText(doubleToStr(emsImg.getAllowAmount()));		
		jTextField7.setText(emsImg.getNote()); //备注 
		if (emsImg.getCurr() != null) //币制
			jTextField8.setText(emsImg.getCurr().getName());
		else
			jTextField8.setText("");
	}

	private void fillWindowExg() {
		jTextField.setText(String.valueOf(emsExg.getSeqNum()));
		if (emsExg.getComplex()!=null)
		  jTextField1.setText(emsExg.getComplex().getCode());
		else
			jTextField1.setText("");
		jTextField2.setText(emsExg.getName());
		jTextField3.setText(emsExg.getSpec());
		if (emsExg.getUnit()!=null)
		    jTextField4.setText(emsExg.getUnit().getName());
		else
			jTextField4.setText("");
		jFormattedTextField3.setText(doubleToStr(emsExg.getDeclarePrice()));
		jFormattedTextField.setText(doubleToStr(emsExg.getAllowAmount()));		
		jTextField7.setText(emsExg.getNote()); //备注 
		if (emsExg.getCurr() != null) //币制
			jTextField8.setText(emsExg.getCurr().getName());
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
			javax.swing.JLabel jLabel12 = new JLabel();

			javax.swing.JLabel jLabel11 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();

			javax.swing.JLabel jLabel6 = new JLabel();

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
			jLabel4.setBounds(26, 78, 72, 20);
			jLabel4.setText("申报计量单位");
			jLabel6.setBounds(27, 105, 63, 21);
			jLabel6.setText("允许数量");
			jLabel10.setBounds(277, 108, 76, 21);
			jLabel10.setText("企业申报单价");
			jLabel11.setBounds(277, 79, 32, 20);
			jLabel11.setText("币制");
			jLabel12.setBounds(26, 140, 65, 21);
			jLabel12.setText("备注");
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJTextField3(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getJFormattedTextField(), null);
			jContentPane.add(getJTextField7(), null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(getJFormattedTextField3(), null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJTextField8(), null);
			jContentPane.add(getJButton2(), null);
			jContentPane.add(getJTextField4(), null);
			jContentPane.add(getJButton4(), null);
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
			jTextField.setBounds(99, 22, 123, 22);
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
			jTextField1.setBounds(365, 23, 120, 22);
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
			jTextField2.setBounds(99, 48, 123, 22);
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
			jTextField3.setBounds(365, 49, 120, 22);
			jTextField3.setEditable(false);
		}
		return jTextField3;
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
			jFormattedTextField.setBounds(99, 107, 123, 22);
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
			jTextField7.setBounds(98, 140, 390, 22);
		}
		return jTextField7;
	}

	private Double strToDouble(String value) { //转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				//	return new Double("0");
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return (Double) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			//return new Double("0");
			return null;
		}
	}

	private String doubleToStr(Double value) { //转换doubleToStr 取数据
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
			jFormattedTextField3.setBounds(364, 109, 123, 22);
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
			jButton.setBounds(161, 173, 70, 25);
			jButton.setText("确定");

			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (DgEmsHeadH2kFasImgExg.this.isImg) {
						fillDataImg(emsImg);
						emsImg = manualdeclearAction
								.saveEmsHeadH2kFasImg(new Request(
										CommonVars.getCurrUser()), emsImg);
						tableModel.updateRow(emsImg);
						DgEmsHeadH2kFasImgExg.this.dispose();
					} else {
						fillDataExg(emsExg);
						emsExg = manualdeclearAction
								.saveEmsHeadH2kFasExg(new Request(
										CommonVars.getCurrUser()), emsExg);
						tableModel.updateRow(emsExg);
						DgEmsHeadH2kFasImgExg.this.dispose();
					}
				}
			});

		}
		return jButton;
	}

	public void fillDataImg(EmsHeadH2kFasImg emsAfter) { //保存
		EmsHeadH2kFasImg old = new EmsHeadH2kFasImg();
		old = (EmsHeadH2kFasImg) emsAfter.clone();	
		emsAfter.setUnit(unit);
		emsAfter.setAllowAmount(strToDouble(jFormattedTextField.getText()));
		emsAfter.setDeclarePrice(strToDouble(jFormattedTextField3.getText()));
		emsAfter.setCurr(curr);
		emsAfter.setNote(jTextField7.getText());
		if (!emsAfter.fullEquals(old) && !emsImg.getModifyMark().equals(ModifyMarkState.DELETED)
				&& !emsImg.getModifyMark().equals(ModifyMarkState.ADDED) && emsImg.getEmsHeadH2kFas().getDeclareType().equals(DelcareType.MODIFY)){
			emsAfter.setModifyMark(ModifyMarkState.MODIFIED);
			if (emsHeadH2kFas.getDeclareType().equals(DelcareType.MODIFY)){
				emsAfter.setModifyTimes(emsHeadH2kFas.getModifyTimes());
			}
		}		
	}

	public void fillDataExg(EmsHeadH2kFasExg emsAfter) { //保存
		EmsHeadH2kFasExg old = new EmsHeadH2kFasExg();
		old = (EmsHeadH2kFasExg) emsAfter.clone();	
		emsAfter.setUnit(unit);
		emsAfter.setAllowAmount(strToDouble(jFormattedTextField.getText()));
		emsAfter.setDeclarePrice(strToDouble(jFormattedTextField3.getText()));
		emsAfter.setCurr(curr);
		emsAfter.setNote(jTextField7.getText());
		if (!emsAfter.fullEquals(old) && !emsExg.getModifyMark().equals(ModifyMarkState.DELETED)
				&& !emsExg.getModifyMark().equals(ModifyMarkState.ADDED) && emsExg.getEmsHeadH2kFas().getDeclareType().equals(DelcareType.MODIFY)){
			emsAfter.setModifyMark(ModifyMarkState.MODIFIED);
			if (emsHeadH2kFas.getDeclareType().equals(DelcareType.MODIFY)){
				emsAfter.setModifyTimes(emsHeadH2kFas.getModifyTimes());
			}
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
			jButton1.setBounds(285, 173, 69, 25);
			jButton1.setText("取消");

			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgEmsHeadH2kFasImgExg.this.dispose();

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
			jTextField8.setBounds(364, 81, 99, 22);
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
			jButton2.setBounds(461, 81, 25, 22);
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
			jTextField4.setBounds(99, 78, 101, 22);
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
			jButton4.setBounds(199, 78, 22, 21);
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
	 * @param isChange The isChange to set.
	 */
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}
	/**
	 * @return Returns the emsHeadH2kFas.
	 */
	public EmsHeadH2kFas getEmsHeadH2kFas() {
		return emsHeadH2kFas;
	}
	/**
	 * @param emsHeadH2kFas The emsHeadH2kFas to set.
	 */
	public void setEmsHeadH2kFas(EmsHeadH2kFas emsHeadH2kFas) {
		this.emsHeadH2kFas = emsHeadH2kFas;
	}
} //  @jve:decl-index=0:visual-constraint="10,10"
