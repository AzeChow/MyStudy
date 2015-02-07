/*
 * Created on 2004-7-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.text.ParseException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgBefore;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.SendState;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class DgEmsEdiMergerBefore extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTextField jTextField = null;

	private JTextField jTextField1 = null;

	private JTextField jTextField2 = null;

	private JTextField jTextField3 = null;

	private JTextField jTextField4 = null;

	private JTextField jTextField5 = null;

	private JTextField jTextField7 = null;

	private JFormattedTextField jFormattedTextField3 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTextField jTextField6 = null;

	private JTextField jTextField8 = null;

	private ManualDeclareAction manualdeclearAction = null;

	private JTableListModel tableModel = null;

	private EmsEdiMergerImgBefore emsImgBefore = null; // 料件

	private EmsEdiMergerExgBefore emsExgBefore = null; // 成品
	
	private EmsEdiMergerImgAfter emsImgAfter = null; // 归并后料件  //  @jve:decl-index=0:

	private EmsEdiMergerExgAfter emsExgAfter = null; // 归并后成品
	
	private EmsEdiMergerHead emsEdiMergerHead = null; // 表头 //
	// @jve:decl-index=0:

	private boolean isImg = true; // 是否为料件还是成品

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private JLabel jLabel8 = null;
	private JFormattedTextField jFormattedTextField = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;

	private int totalCount = 0; // 总行
	private int rowCount = 0; // 当前行

	private String isSendSign = ""; // @jve:decl-index=0:

	private boolean isModifySeqNUm = false; // 可否修改备案序号

	public boolean isModifySeqNUm() {
		return isModifySeqNUm;
	}

	public void setModifySeqNUm(boolean isModifySeqNUm) {
		this.isModifySeqNUm = isModifySeqNUm;
	}

	/**
	 * This is the default constructor
	 */
	public DgEmsEdiMergerBefore() {
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
		this.setSize(451, 268);
		this.setTitle("编辑归并前料件");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				isSendSign = manualdeclearAction.getBpara(new Request(
						CommonVars.getCurrUser()), BcusParameter.EmsSEND_Sign);
				if (tableModel.getCurrentRow() != null) {
					totalCount = tableModel.getRowCount(); // 总记录数
					if (DgEmsEdiMergerBefore.this.isImg) {
						emsImgBefore = (EmsEdiMergerImgBefore) tableModel
								.getCurrentRow();
						DgEmsEdiMergerBefore.this.setTitle("编辑归并前料件");
						fillWindowImg();
					} else {
						emsExgBefore = (EmsEdiMergerExgBefore) tableModel
								.getCurrentRow();
						DgEmsEdiMergerBefore.this.setTitle("编辑归并前成品");
						fillWindowExg();
					}
					rowCount = tableModel.getCurrRowCount();// 当前记录
					setstate();
				}
			}
		});
	}

	private void setstate() {
		jButton2.setEnabled(rowCount > 0);
		jButton3.setEnabled(rowCount < totalCount - 1);
	}

	private void fillWindowImg() {
		if (this.isModifySeqNUm) {
			jTextField1.setEditable(true);
		} else {
			jTextField1.setEditable(false);
		}
		jTextField.setText(emsImgBefore.getPtNo());// 货号
		jTextField1.setText(String.valueOf(emsImgBefore.getSeqNum()));// 序号
		if (emsImgBefore.getComplex() != null)
			jTextField2.setText(emsImgBefore.getComplex().getCode());// 商品编码
		else
			jTextField2.setText("");
		jTextField3.setText(emsImgBefore.getName());// 名称
		jTextField4.setText(emsImgBefore.getSpec());// 规格
		if (emsImgBefore.getUnit() != null)
			jTextField5.setText(emsImgBefore.getUnit().getName());// 报关单位
		else
			jTextField5.setText("");
		jFormattedTextField3
				.setText(doubleToStr(emsImgBefore.getMaxApprSpace()));
		jFormattedTextField.setText(doubleToStr(emsImgBefore.getPrice()));
		if (emsImgBefore.getComplex().getFirstUnit() != null)
			jTextField6.setText(emsImgBefore.getComplex().getFirstUnit()
					.getName());
		else
			jTextField6.setText("");
		if (emsImgBefore.getComplex().getSecondUnit() != null)
			jTextField8.setText(emsImgBefore.getComplex().getSecondUnit()
					.getName());
		else
			jTextField8.setText("");
		jTextField7.setText(emsImgBefore.getNote());
	}

	private void fillWindowExg() {
		if (this.isModifySeqNUm) {
			jTextField1.setEditable(true);
		} else {
			jTextField1.setEditable(false);
		}
		jTextField.setText(emsExgBefore.getPtNo());
		jTextField1.setText(String.valueOf(emsExgBefore.getSeqNum()));
		if (emsExgBefore.getComplex() != null)
			jTextField2.setText(emsExgBefore.getComplex().getCode());
		else
			jTextField2.setText("");
		jTextField3.setText(emsExgBefore.getName());
		jTextField4.setText(emsExgBefore.getSpec());
		if (emsExgBefore.getUnit() != null)
			jTextField5.setText(emsExgBefore.getUnit().getName());
		else
			jTextField5.setText("");
		jFormattedTextField3
				.setText(doubleToStr(emsExgBefore.getMaxApprSpace()));
		jFormattedTextField.setText(doubleToStr(emsExgBefore.getPrice()));
		if (emsExgBefore.getComplex().getFirstUnit() != null)
			jTextField6.setText(emsExgBefore.getComplex().getFirstUnit()
					.getName());
		else
			jTextField6.setText("");
		if (emsExgBefore.getComplex().getSecondUnit() != null)
			jTextField8.setText(emsExgBefore.getComplex().getSecondUnit()
					.getName());
		else
			jTextField8.setText("");
		jTextField7.setText(emsExgBefore.getNote());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel8 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel6 = new JLabel();

			javax.swing.JLabel jLabel12 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("料件货号");
			jLabel.setBounds(19, 22, 53, 18);
			jLabel1.setBounds(227, 24, 60, 17);
			jLabel1.setText("料件序号");
			jLabel2.setBounds(19, 48, 54, 20);
			jLabel2.setText("商品编码");
			jLabel3.setBounds(227, 50, 59, 17);
			jLabel3.setText("商品名称");
			jLabel4.setBounds(18, 75, 53, 20);
			jLabel4.setText("型号规格");
			jLabel5.setBounds(228, 77, 61, 18);
			jLabel5.setText("计量单位");
			jLabel10.setBounds(17, 104, 76, 21);
			jLabel10.setText("批准最大余量");
			jLabel12.setBounds(18, 160, 65, 21);
			jLabel12.setText("备注");
			jLabel6.setBounds(228, 103, 78, 21);
			jLabel6.setText("法定计量单位");
			jLabel7.setBounds(228, 133, 102, 19);
			jLabel7.setText("第二法定计量单位");
			jLabel8.setBounds(16, 133, 77, 22);
			jLabel8.setText("企业申报单价");
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
			jContentPane.add(getJTextField7(), null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(getJFormattedTextField3(), null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getJTextField6(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getJTextField8(), null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(getJFormattedTextField(), null);
			jContentPane.add(getJButton2(), null);
			jContentPane.add(getJButton3(), null);
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
			jTextField.setBounds(91, 22, 123, 22);
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
			jTextField1.setBounds(306, 23, 120, 22);
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
			jTextField2.setBounds(91, 48, 123, 22);
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
			jTextField3.setBounds(306, 49, 120, 22);
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
			jTextField4.setBounds(90, 75, 124, 22);
			// jTextField4.setEditable(false);
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
			jTextField5.setBounds(305, 76, 121, 22);
			jTextField5.setEditable(false);
		}
		return jTextField5;
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
			jTextField7.setBounds(90, 160, 339, 22);
		}
		return jTextField7;
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
			jFormattedTextField3.setBounds(89, 104, 127, 22);
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
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(145, 197, 70, 25);
			jButton.setText("保存");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					boolean ischange = false;
					if (JOptionPane.showConfirmDialog(
							DgEmsEdiMergerBefore.this, "是否同时更新到【报关常用工厂物料】中?",
							"提示", 0) == 0) {
						ischange = true;
					}

					if (DgEmsEdiMergerBefore.this.isImg) {
						if (ischange) {
							manualdeclearAction.changeMateriel(new Request(
									CommonVars.getCurrUser()), emsImgBefore
									.getPtNo(), jTextField3.getText(),
									jTextField4.getText());
						}
						fillDataImg(emsImgBefore);
						emsImgBefore = manualdeclearAction
								.saveEmsEdiMergerImgBefore(new Request(
										CommonVars.getCurrUser()), emsImgBefore);
						tableModel.updateRow(emsImgBefore);

						DgEmsEdiMergerBefore.this.dispose();

					} else {
						if (ischange) {
							manualdeclearAction.changeMateriel(new Request(
									CommonVars.getCurrUser()), emsExgBefore
									.getPtNo(), jTextField3.getText(),
									jTextField4.getText());
						}
						fillDataExg(emsExgBefore);
						emsExgBefore = manualdeclearAction
								.saveEmsEdiMergerExgBefore(new Request(
										CommonVars.getCurrUser()), emsExgBefore);
						tableModel.updateRow(emsExgBefore);
						DgEmsEdiMergerBefore.this.dispose();
					}
				}
			});

		}
		return jButton;
	}

	public void fillDataImg(EmsEdiMergerImgBefore emsBefore) { // 保存
		EmsEdiMergerImgBefore old = new EmsEdiMergerImgBefore();
		old = (EmsEdiMergerImgBefore) emsBefore.clone();
		emsBefore.setSpec(jTextField4.getText());// 规格
		emsBefore.setName(jTextField3.getText());// 名称
		emsBefore.setMaxApprSpace(strToDouble(jFormattedTextField3.getText()));
		emsBefore.setPrice(strToDouble(jFormattedTextField.getText()));
		emsBefore.setNote(jTextField7.getText());
		Integer oldPtNo = Integer.valueOf(emsBefore.getSeqNum());
		Integer newPtNo = Integer.valueOf(jTextField1.getText());
		if (!oldPtNo.equals(newPtNo)) { // 说明已有修改备案序号
			// 更新内部归并的序号
			emsImgAfter= manualdeclearAction.modityInnergerSeqNumImgId(
					new Request(CommonVars.getCurrUser()),newPtNo);
			manualdeclearAction.modityInnergerSeqNumSingle(new Request(CommonVars
					.getCurrUser()), "2", oldPtNo, newPtNo, emsBefore.getPtNo());
			emsBefore.setEmsEdiMergerImgAfter(emsImgAfter);
			emsBefore.setSeqNum(Integer.valueOf(jTextField1.getText()));
		}

		if (!emsBefore.fullEquals(old)
				&& !emsImgBefore.getModifyMark()
						.equals(ModifyMarkState.DELETED)
				&& !emsImgBefore.getModifyMark().equals(ModifyMarkState.ADDED)
				&& emsImgBefore.getEmsEdiMergerImgAfter().getEmsEdiMergerHead()
						.getDeclareType().equals(DelcareType.MODIFY)) {
			emsBefore.setModifyMark(ModifyMarkState.MODIFIED); // 已修改
			// if (isSendSign != null && "1".equals(isSendSign)){
			emsBefore.setSendState(Integer.valueOf(SendState.WAIT_SEND));// 准备申报
			// }
			emsBefore.setChangeDate(new Date());
			emsBefore.setModifyTimes(emsEdiMergerHead.getModifyTimes());
		}
		// 修改BOM 名称，规格
		manualdeclearAction.modityEmsEdiMergerBom(new Request(CommonVars
				.getCurrUser()), emsBefore.getPtNo(), emsBefore.getName(),
				emsBefore.getSpec(), emsImgBefore.getEmsEdiMergerImgAfter()
						.getEmsEdiMergerHead());

	}

	public void fillDataExg(EmsEdiMergerExgBefore emsBefore) { // 保存
		EmsEdiMergerExgBefore old = new EmsEdiMergerExgBefore();
		old = (EmsEdiMergerExgBefore) emsBefore.clone();
		emsBefore.setSpec(jTextField4.getText());// 规格
		emsBefore.setName(jTextField3.getText());// 名称
		emsBefore.setMaxApprSpace(strToDouble(jFormattedTextField3.getText()));
		emsBefore.setPrice(strToDouble(jFormattedTextField.getText()));
		emsBefore.setNote(jTextField7.getText());
		Integer oldPtNo = Integer.valueOf(emsBefore.getSeqNum());
		Integer newPtNo = Integer.valueOf(jTextField1.getText());
		if (!oldPtNo.equals(newPtNo)) { // 说明已有修改备案序号
			// 更新内部归并的序号
			emsExgAfter= manualdeclearAction.modityInnergerSeqNumExgId(
					new Request(CommonVars.getCurrUser()),newPtNo);
			manualdeclearAction.modityInnergerSeqNum(new Request(CommonVars
					.getCurrUser()), "0", oldPtNo, newPtNo);
			emsBefore.setEmsEdiMergerExgAfter(emsExgAfter);
			emsBefore.setSeqNum(Integer.valueOf(jTextField1.getText()));
		}
		if (!emsBefore.fullEquals(old)
				&& !emsExgBefore.getModifyMark()
						.equals(ModifyMarkState.DELETED)
				&& !emsExgBefore.getModifyMark().equals(ModifyMarkState.ADDED)
				&& emsExgBefore.getEmsEdiMergerExgAfter().getEmsEdiMergerHead()
						.getDeclareType().equals(DelcareType.MODIFY)) {
			emsBefore.setModifyMark(ModifyMarkState.MODIFIED); // 已修改
			// if (isSendSign != null && "1".equals(isSendSign)){
			emsBefore.setSendState(Integer.valueOf(SendState.WAIT_SEND)); // 准备申报
			// }
			emsBefore.setChangeDate(new Date());
			emsBefore.setModifyTimes(emsEdiMergerHead.getModifyTimes());
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
			jButton1.setBounds(220, 197, 69, 25);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgEmsEdiMergerBefore.this.dispose();
				}
			});

		}
		return jButton1;
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
			jTextField6.setBounds(306, 102, 121, 22);
			jTextField6.setEditable(false);
		}
		return jTextField6;
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
			jTextField8.setBounds(336, 133, 92, 22);
			jTextField8.setEditable(false);

		}
		return jTextField8;
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
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJFormattedTextField() {
		if (jFormattedTextField == null) {
			jFormattedTextField = new JFormattedTextField();
			jFormattedTextField.setBounds(90, 134, 125, 21);
			jFormattedTextField
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(68, 197, 72, 25);
			jButton2.setText("上一条");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (DgEmsEdiMergerBefore.this.isImg) {
						fillDataImg(emsImgBefore);
						emsImgBefore = manualdeclearAction
								.saveEmsEdiMergerImgBefore(new Request(
										CommonVars.getCurrUser()), emsImgBefore);
						tableModel.updateRow(emsImgBefore);
						rowCount--;
						emsImgBefore = (EmsEdiMergerImgBefore) tableModel
								.getObjectByRow(rowCount);
						fillWindowImg();
						setstate();
					} else {
						fillDataExg(emsExgBefore);
						emsExgBefore = manualdeclearAction
								.saveEmsEdiMergerExgBefore(new Request(
										CommonVars.getCurrUser()), emsExgBefore);
						tableModel.updateRow(emsExgBefore);
						rowCount--;
						emsExgBefore = (EmsEdiMergerExgBefore) tableModel
								.getObjectByRow(rowCount);
						fillWindowExg();
						setstate();
					}
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(293, 197, 72, 25);
			jButton3.setText("下一条");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (DgEmsEdiMergerBefore.this.isImg) {
						fillDataImg(emsImgBefore);
						emsImgBefore = manualdeclearAction
								.saveEmsEdiMergerImgBefore(new Request(
										CommonVars.getCurrUser()), emsImgBefore);
						tableModel.updateRow(emsImgBefore);
						rowCount++;
						emsImgBefore = (EmsEdiMergerImgBefore) tableModel
								.getObjectByRow(rowCount);
						fillWindowImg();
						setstate();
					} else {
						fillDataExg(emsExgBefore);
						emsExgBefore = manualdeclearAction
								.saveEmsEdiMergerExgBefore(new Request(
										CommonVars.getCurrUser()), emsExgBefore);
						tableModel.updateRow(emsExgBefore);
						rowCount++;
						emsExgBefore = (EmsEdiMergerExgBefore) tableModel
								.getObjectByRow(rowCount);
						fillWindowExg();
						setstate();
					}

				}
			});
		}
		return jButton3;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
