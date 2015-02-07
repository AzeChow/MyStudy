/*
 * Created on 2004-7-26
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.text.PlainDocument;

import org.apache.commons.lang.math.NumberUtils;




import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.CaleUtil;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.fpt.entity.FptBillItem;
import com.bestway.common.fpt.entity.FptParameterSet;
import com.bestway.common.fpt.entity.TempFptBillExeInfo;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.util.RegexUtil;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * 结转单据商品信息表
 * by check hw 2008-08-30 
 * @author lxr
 * 
 */
public class DgFptBillItem extends DgCommon {

	private JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JButton btnSave = null;
	private JButton btnCancel = null;
	private JTextField tfappGNo = null;
	private JTextField tfNote = null;
	private JTextField tflistNo = null;
	private JTextField tfoutNo = null;
	private JTextField tfcopGName = null;
	private JTextField tfcopGModel = null;
	private JTextField tfcommSpec = null;
	private JTextField tfcommName = null;
	private FptBillHead fptBillHead = null;
	private JTableListModel tableModel = null;
	/**
	 * 是否是归并关系与申请单转入
	 */
	private Boolean isAppOrMerger = null;
	/**
	 * 状态参数
	 */
	private int dataState = -1;

	private boolean isdataState = false;
	/**
	 * 转入转出
	 */
	//private String fptOutFlag = FptInOutFlag.OUT;  //  @jve:decl-index=0:
	/**
	 * 转入
	 */
	//private String fptInFlag = FptInOutFlag.IN;  //  @jve:decl-index=0:
	/**
	 * 单据类型
	 */
	
	private String fptInOutFlag = null;
	private String fptBusinessType = FptBusinessType.FPT_BILL;  //  @jve:decl-index=0:

	private DefaultFormatterFactory defaultFormatterFactory = null;
	private NumberFormatter numberFormatter = null;
	private JTextField tftradeQty = null;
	private JTextField tfcomplex = null;
	private JLabel lbInfo = null;
	private Double leftAmount = 0.0;
	private JLabel jLabel194 = null;
	private JTextField tfEmsNo = null;
	private JLabel jLabel1941 = null;
	private JTextField tfcopGNo = null;
	private JLabel jLabel61 = null;
	private JLabel jLabel171 = null;
	private JTextField tfqty = null;
	private JComboBox cbbtradeUnit = null;
	private JComboBox cbbunitName = null;
	private JTextField tftrGno = null;
	private JButton btnComplex = null;
	private Complex complex = null;
	private JLabel jLabel2 = null;
	private JButton btnEdit = null;
	private JButton btnPrevious = null;
	private JButton btnNext = null;
	private JButton btnExit = null;
	private JButton btnAddTop = null;
	private String action = null;
	private FptParameterSet parameterSet = null;
	private String outNoTextInfo="";

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public DgFptBillItem() {
		super();
		initialize();
	}

	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("结转单据---商品信息表");
		this.setSize(512, 450);
	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {
			initComponents();
			if (FptInOutFlag.OUT.equals(fptInOutFlag)) {
				this.setTitle("结转单据---转出商品信息表");
			} else {
				this.setTitle("结转单据---转入商品信息表");
			}
			String emptyErrorInfo="";
			String typeErrorInfo="";
			if (FptInOutFlag.IN.equals(fptInOutFlag) && FptBusinessType.FPT_BILL.equals(fptBusinessType)){
				outNoTextInfo="发货序号";
			}else if(FptInOutFlag.OUT.equals(fptInOutFlag) && FptBusinessType.FPT_BILL_BACK.equals(fptBusinessType) ){
				outNoTextInfo="发退货序号";
			}
			setState();
			showData();
		}
		super.setVisible(isFlag);
	}

	private void initAddData() {
		emptyData();

		/**
		 * 获取明细的最大序号
		 */
		tflistNo.setText(String.valueOf(fptManageAction.addBillListNoInteger(
				new Request(CommonVars.getCurrUser()), fptBillHead)));
		tfcopGNo.requestFocus();
	}

	private void emptyData() {
		this.tfcopGNo.setText("");
		this.tfappGNo.setText("");
		this.tfEmsNo.setText("");
		this.tfcopGName.setText("");
		this.tfcopGModel.setText("");
		this.tfoutNo.setText("");
		this.tfcomplex.setText("");
		this.tftrGno.setText("");
		this.tfcommName.setText("");
		this.tfcommSpec.setText("");
		this.cbbunitName.setSelectedItem(null);
		this.cbbtradeUnit.setSelectedItem(null);
		this.tftradeQty.setText("0");
		this.tfqty.setText("0");
		this.tfNote.setText("");
	}

	private void setState() {
		FptBillItem Item = (FptBillItem) tableModel.getCurrentRow();
		if (Item == null) {
			return;
		}

		String declareState = fptBillHead.getAppState();
		boolean isChangingExe = DeclareState.CHANGING_EXE.equals(declareState);
		boolean isApplyPor = DeclareState.APPLY_POR.equals(declareState);
		/**
		 * 能编辑
		 */
		boolean isCanEdit = isApplyPor || isChangingExe;
		this.btnEdit.setEnabled(this.dataState == DataState.BROWSE && isCanEdit
				&& isdataState);
		this.btnCancel.setEnabled(this.dataState != DataState.BROWSE
				&& isCanEdit && isdataState);
		this.btnPrevious.setEnabled(tableModel.hasPreviousRow());
		this.btnNext.setEnabled(tableModel.hasNextRow());
		this.btnSave.setEnabled(this.dataState != DataState.BROWSE && isCanEdit
				&& isdataState);

		this.tfqty.setEditable(this.dataState != DataState.BROWSE && isCanEdit
				&& isdataState);
		this.tftradeQty.setEditable(this.dataState != DataState.BROWSE && isCanEdit && isdataState);
		//this.tftradeQty.setEditable(false);
		this.tfNote.setEditable(this.dataState != DataState.BROWSE && isCanEdit
				&& isdataState);
		
		
		/**
		 * 是否隐藏料号级的栏位isAppOrMerger存在不用
		 */
//		this.tfcopGNo.setEditable(ModifyMarkState.ADDED.equals(Item
//				.getModifyMake())
//				&& dataState != DataState.BROWSE && isCanEdit && isdataState);
//		this.tfcopGModel.setEditable(this.dataState != DataState.BROWSE
//				&& isCanEdit && isdataState);
//		this.tfcopGName.setEditable(this.dataState != DataState.BROWSE
//				&& isCanEdit && isdataState);
//		this.cbbptUnit.setEnabled(this.dataState != DataState.BROWSE
//				&& isCanEdit && isdataState);
//		this.tfptAmount.setEditable(this.dataState != DataState.BROWSE
//				&& isCanEdit && isdataState);
//		this.tfoutNo.setVisible(aFlag)
		this.tfoutNo.setEditable(this.dataState != DataState.BROWSE && isCanEdit
				&& isdataState&&(FptBusinessType.FPT_BILL.equals(fptBusinessType)&&FptInOutFlag.IN.equals(fptInOutFlag)
						||FptBusinessType.FPT_BILL_BACK.equals(fptBusinessType)&&FptInOutFlag.OUT.equals(fptInOutFlag)));

		this.tfEmsNo.setEnabled(this.dataState != DataState.BROWSE && isCanEdit
				&& isdataState);
		
		this.cbbtradeUnit.setEnabled(this.dataState != DataState.BROWSE && isCanEdit && isdataState);
		this.cbbunitName.setEnabled(this.dataState != DataState.BROWSE && isCanEdit && isdataState);
		//this.cbbtradeUnit.setEnabled(false);
		//this.cbbunitName.setEnabled(false);
		this.cbbptUnit.setEnabled(this.dataState != DataState.BROWSE && isCanEdit && isdataState);
		
		//不能编辑
		this.tftrGno.setEditable(false);
		this.btnComplex.setEnabled(false);
		this.tfcomplex.setEditable(false);
		this.tfcommName.setEditable(false);
		this.tfcommSpec.setEditable(false);
	
		this.jLabel2.setVisible(!"".equals(this.outNoTextInfo.trim()));
		this.tfoutNo.setVisible(!"".equals(this.outNoTextInfo.trim()));
	}

	/**
	 * @return Returns the transferFactoryManageAction.
	 */
	public FptManageAction getTransferFactoryManageAction() {
		return fptManageAction;
	}

	/**
	 * @param transferFactoryManageAction
	 *            The transferFactoryManageAction to set.
	 */
	public void setTransferFactoryManageAction(
			FptManageAction transferFactoryManageAction) {
		this.fptManageAction = transferFactoryManageAction;
	}

	/**
	 * @return Returns the leftAmount.
	 */
	public Double getLeftAmount() {
		return leftAmount;
	}

	/**
	 * @param leftAmount
	 *            The leftAmount to set.
	 */
	public void setLeftAmount(Double leftAmount) {
		this.leftAmount = leftAmount;
	}

	/**
	 * @return Returns the dataState.
	 */
	public int getDataState() {
		return dataState;
	}

	/**
	 * @return Returns the isImportGoods.
	 */
	public String getFptInOutFlag() {
		return fptInOutFlag;
	}

	/**
	 * @param isImportGoods
	 *            The isImportGoods to set.
	 */
	public void setFptInOutFlag(String fptInOutFlag) {
		this.fptInOutFlag = fptInOutFlag;
	}

//	public String getFptInFlag() {
//		return fptInFlag;
//	}
//
//	public void setFptInFlag(String fptInFlag) {
//		this.fptInFlag = fptInFlag;
//	}
	/**
	 * @param dataState
	 *            The dataState to set.
	 */
	public void setDataState(int dataState) {
		this.dataState = dataState;
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

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel6111 = new JLabel();
			jLabel6111.setText("工厂数量");
			jLabel6111.setBounds(new Rectangle(250, 86, 80, 18));
			jLabel611 = new JLabel();
			jLabel611.setText("工厂单位");
			jLabel611.setBounds(new Rectangle(3, 86, 88, 18));
			jLabel171 = new JLabel();
			jLabel171.setBounds(new Rectangle(7, 136, 88, 18));
			jLabel171.setText("申报数量");
			jLabel171.setForeground(Color.blue);
			jLabel61 = new JLabel();
			jLabel61.setBounds(new Rectangle(9, 112, 88, 18));
			jLabel61.setForeground(Color.blue);
			jLabel61.setText("申报单位");
			jLabel1941 = new JLabel();
			jLabel1941.setText("料号");
			jLabel1941.setBounds(new Rectangle(5, 5, 88, 23));
			jLabel194 = new JLabel();
			jLabel194.setBounds(new Rectangle(10, 84, 88, 20));
			jLabel194.setText("手册/帐册号");
			lbInfo = new JLabel();
			lbInfo.setBounds(new Rectangle(20, 386, 449, 24));
			lbInfo.setText(" 该商品信息提示:");
			lbInfo.setForeground(java.awt.Color.blue);
			lbInfo.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			javax.swing.JLabel jLabel18 = new JLabel();
			javax.swing.JLabel jLabel17 = new JLabel();
			javax.swing.JLabel jLabel15 = new JLabel();
			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel13 = new JLabel();
			javax.swing.JLabel jLabel10 = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			jLabel10.setText("申请表序号");
			jLabel13.setText("项号");
			jLabel1.setText("序号");
			jLabel1.setBounds(10, 58, 88, 20);
			jLabel2 = new JLabel();
			jLabel2.setText("收发货序号");
			jLabel2.setBounds(253, 84, 80, 18);
			jLabel6.setText("交易单位");
			jLabel6.setForeground(Color.blue);
			jLabel6.setBounds(253, 110, 80, 22);
			jLabel7.setText("备注");
			jLabel7.setBounds(7, 162, 80, 22);
			jLabel10.setBounds(253, 56, 80, 22);
			jLabel13.setBounds(10, 305, 88, 22);
			jLabel12.setBounds(253, 305, 80, 22);
			jLabel12.setText("商品编码");
			jLabel15.setBounds(10, 330, 60, 22);
			jLabel15.setText("商品名称");
			jLabel17.setBounds(254, 136, 80, 22);
			jLabel17.setText("交易数量");
			jLabel17.setForeground(java.awt.Color.blue);
			jLabel18.setBounds(10, 358, 60, 18);
			jLabel18.setText("规格型号");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getTfappGNo(), null);
			jContentPane.add(getTfNote(), null);
			jContentPane.add(getTflistNo(), null);
			jContentPane.add(getTfoutNo(), null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(jLabel13, null);
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getTftradeQty(), null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(jLabel15, null);
			jContentPane.add(jLabel17, null);
			jContentPane.add(jLabel18, null);
			jContentPane.add(getTfcommSpec(), null);
			jContentPane.add(getTfcommName(), null);
			jContentPane.add(getTfcomplex(), null);
			jContentPane.add(lbInfo, null);
			jContentPane.add(jLabel194, null);
			jContentPane.add(getTfEmsNo(), null);
			jContentPane.add(jLabel61, null);
			jContentPane.add(jLabel171, null);
			jContentPane.add(getTfqty(), null);
			jContentPane.add(getCbbtradeUnit(), null);
			jContentPane.add(getCbbunitName(), null);
			jContentPane.add(getTftrGno(), null);
			jContentPane.add(getBtnComplex(), null);
			jContentPane.add(getJPanel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * jContentPane.add(jLabel191, null);
	 * 
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
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
	 * This method initializes tftradeQty
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JTextField getTftradeQty() {
		if (tftradeQty == null) {
			tftradeQty = new JTextField();
			tftradeQty.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					String stu = CommonUtils.formatDoubleByDigit(NumberUtils.toDouble(tftradeQty.getText()) ,5);
					tftradeQty.setText(stu);
				}
			});
			tftradeQty = new JTextField();
//			tftradeQty.addFocusListener(new FocusAdapter() {
//				@Override
//				public void focusLost(FocusEvent e) {
//					
//				}
//			});
			tftradeQty.setBounds(339, 136, 135, 22);
			tftradeQty.setDocument(new PlainDocument(){
				@Override
				public void insertString(int offs, String str, AttributeSet a)
						throws BadLocationException {
					try {
						Double.parseDouble(str);
						super.insertString(offs, str, a);
					} catch (Exception e) {
						System.out.println(str);
						if(".".equals(str)){
							super.insertString(offs, str, a);
						}
					}
				}
			});
//			tftradeQty.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tftradeQty;
	}

	/**
	 * This method initializes tfcomplex
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JTextField getTfcomplex() {
		if (tfcomplex == null) {
			tfcomplex = new JTextField();
			tfcomplex.setBounds(339, 305, 106, 22);
		}
		return tfcomplex;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setBounds(1, 2, 493, 35);
			jToolBar.add(getBtnAddTop());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnPrevious());
			jToolBar.add(getBtnNext());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validateDataIsNull()) {
						return;
					}
					if (validateOther()) {
						return;
					}
					dataState = saveData();
					setState();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
		}
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dataState = DataState.BROWSE;
				showData();
				setState();
			}
		});
		return btnCancel;
	}

	/**
	 * This method initializes tfGrossWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JTextField getTfcopGModel() {
		if (tfcopGModel == null) {
			tfcopGModel = new JTextField();
			tfcopGModel.setBounds(new Rectangle(95, 57, 235, 22));
			tfcopGModel.setEditable(false);
		}
		return tfcopGModel;
	}

	/**
	 * This method initializes tfappGNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfappGNo() {
		if (tfappGNo == null) {
			tfappGNo = new JTextField();
			tfappGNo.setBounds(339, 58, 134, 22);
			tfappGNo.setEditable(false);
		}
		return tfappGNo;
	}

	/**
	 * This method initializes tfNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextField();
			tfNote.setBounds(103, 162, 370, 22);
		}
		return tfNote;
	}

	/**
	 * This method initializes tfcommSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfcommSpec() {
		if (tfcommSpec == null) {
			tfcommSpec = new JTextField();
			tfcommSpec.setBounds(103, 358, 370, 22);
		}
		return tfcommSpec;
	}

	/**
	 * This method initializes tfcommName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfcommName() {
		if (tfcommName == null) {
			tfcommName = new JTextField();
			tfcommName.setBounds(103, 331, 370, 22);
		}
		return tfcommName;
	}

	/**
	 * This method initializes tflistNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTflistNo() {
		if (tflistNo == null) {
			tflistNo = new JTextField();
			tflistNo.setBounds(103, 58, 144, 22);
			tflistNo.setEditable(false);
		}
		return tflistNo;
	}

	/**
	 * This method initializes tfoutNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfoutNo() {
		if (tfoutNo == null) {
			tfoutNo = new JTextField();
			tfoutNo.setBounds(339, 83, 134, 22);
		}
		return tfoutNo;
	}

	/**
	 * This method initializes tfMaterielNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfcopGName() {
		if (tfcopGName == null) {
			tfcopGName = new JTextField();
			tfcopGName.setBounds(new Rectangle(95, 30, 235, 22));
			tfcopGName.setEditable(false);
		}
		return tfcopGName;
	}

	/**
	 * 显示数据
	 */
	private void showData() {
		FptBillItem data = (FptBillItem) tableModel.getCurrentRow();
		this.jLabel2.setText(this.outNoTextInfo);
		this.tflistNo.setText(String.valueOf(data.getListNo()));
		if (data.getAppGNo() != null) {// 申请表序号
			this.tfappGNo.setText(String.valueOf(data.getAppGNo()));
		} else {
			this.tfappGNo.setText("");
		}
		if (data.getListNo() != null) {
			this.tfEmsNo.setText(data.getEmsNo());
		} else {
			this.tfEmsNo.setText("");
		}// 手册号
		if (data.getCopGNo() != null && !"".equals(data.getCopGNo())) {// 料号
			this.tfcopGNo.setText(data.getCopGNo().toString());
			this.setIsAppOrMerger(false);
		} else {
			this.setIsAppOrMerger(true);
			this.tfcopGNo.setText("");
		}
		if (data.getCopGName() != null) {// 归前名称
			this.tfcopGName.setText(data.getCopGName().toString());
		} else {
			this.tfcopGName.setText("");
		}
		if (data.getCopGModel() != null) {// 归前规格
			this.tfcopGModel.setText(data.getCopGModel());
		} else {
			this.tfcopGModel.setText("");
		}

		if (data.getOutNo() != null) {// 发货序号
			this.tfoutNo.setText(String.valueOf(data.getOutNo()));
		} else {
			this.tfoutNo.setText("");
		}

		if (data.getComplex() != null) {// 商品编码
			complex = data.getComplex();
			this.tfcomplex.setText(data.getComplex().getCode());
		} else {
			this.tfcomplex.setText("");
		}
		if (data.getTrGno() != null) {// 项号
			this.tftrGno.setText(String.valueOf(data.getTrGno()));
		} else {
			this.tftrGno.setText("");
		}
		if (data.getCommName() != null) {// 商名
			this.tfcommName.setText(data.getCommName());
		} else {
			this.tfcommName.setText("");
		}
		if (data.getCommSpec() != null) {// 商规
			this.tfcommSpec.setText(data.getCommSpec());
		} else {
			this.tfcommSpec.setText("");
		}
		if (data.getUnit() != null) {// 申报单位
			this.cbbunitName.setSelectedItem((Unit) data.getUnit());
		} else {
			this.cbbunitName.setSelectedItem(null);
		}
//			if("new".equals(this.action)){
//				//新增的时候 申报、交易单位默认相等
//				this.cbbtradeUnit.setSelectedItem(null != data ? (Unit) data.getUnit() : null);
//			}else {
//				//非新增的时候 申报、交易单位不默认相等
//				this.cbbtradeUnit.setSelectedItem(null != data ? (Unit) data.getTradeUnit() : null);  //dataState
//			}
		this.cbbtradeUnit.setSelectedItem(data.getTradeUnit());
		if (data.getTradeQty() != null) {// 交yi数量
			this.tftradeQty.setText(data.getTradeQty()+"");
		} else {
			this.tftradeQty.setText("0");
		}
		if (data.getQty() != null) {// 申报数量
			this.tfqty.setText(data.getQty()+"");
		} else {
			this.tfqty.setText("0");
		}
		if (data.getNote() != null) {// 备注
			this.tfNote.setText(data.getNote());
		} else {
			this.tfNote.setText("");
		}
		if (data.getPtUnit() != null) {// 工厂单位
			this.cbbptUnit.setSelectedItem((CalUnit) data.getPtUnit());
		} else {
			this.cbbptUnit.setSelectedItem(null);
		}
		if (data.getPtAmount() != null) {// 工厂数量
			this.tfptAmount.setValue(data.getPtAmount());
		} else {
			this.tfptAmount.setValue(new Double(0));
		}
		//
		// 显示数据信息
		//
		showFptExeInfo(data);
	}

	private TempFptBillExeInfo info = new TempFptBillExeInfo();

	private JPanel jPanel = null;

	private JLabel jLabel611 = null;

	private JComboBox cbbptUnit = null;

	private JLabel jLabel6111 = null;

	private JCustomFormattedTextField tfptAmount = null;

	/**
	 * 显示数据信息
	 * 
	 * @param f
	 */
	private void showFptExeInfo(FptBillItem Item) {
		/**
		 * 判断交易数量是否超量
		 */
		info = this.fptManageAction.findFptBillExeInfoByFpt(new Request(
				CommonVars.getCurrUser(), true), Item);
		
		info.setFptbillRemain(CommonUtils.getDoubleByDigit(info.getFptbillRemain(),2));
		info.setFptbillcurrentRemain(CommonUtils.getDoubleByDigit(info.getFptbillcurrentRemain(),2));
		
		String str = "";
		if (FptInOutFlag.OUT.equals(fptInOutFlag)) {
			if (FptBusinessType.FPT_BILL.equals(fptBusinessType)) {
				str = "申请表数量:" + info.getFptBillAmount() + "; 已发货数量:"
						+ info.getFptbillRemain() + "; 当前可发货数量:"
						+ info.getFptbillcurrentRemain();
			} else {
				str = "发货数量:" + info.getFptBillAmount() + "; 已收退货数量:"
						+ info.getFptbillRemain() + "; 当前可收退货数量:"
						+ info.getFptbillcurrentRemain();
			}
		} else {
			if (FptBusinessType.FPT_BILL.equals(fptBusinessType)) {
				str = "申请表数量:" + info.getFptBillAmount() + "; 已收货数量:"
						+ info.getFptbillRemain() + "; 当前可收货数量:"
						+ info.getFptbillcurrentRemain();
			} else {
				str = "收货数量:" + info.getFptBillAmount() + "; 已发退货数量:"
						+ info.getFptbillRemain() + "; 当前可发退货数量:"
						+ info.getFptbillcurrentRemain();
			}
		}
		this.lbInfo.setText(str);
	}

	/**
	 * 填充数据
	 */
	private int fillData(FptBillItem data) {
		data.setAppGNo(Integer.valueOf(this.tfappGNo.getText()));
		data.setCommName(this.tfcommName.getText());
		data.setCommSpec(this.tfcommSpec.getText());
		data.setComplex(complex);
		data.setCopGNo(this.tfcopGNo.getText());
		data.setEmsNo(this.tfEmsNo.getText());
		
				
		if ((FptInOutFlag.IN.equals(fptInOutFlag) && FptBusinessType.FPT_BILL.equals(fptBusinessType))
				|| (FptInOutFlag.OUT.equals(fptInOutFlag) && FptBusinessType.FPT_BILL_BACK.equals(fptBusinessType) )) {
			if (null == this.tfoutNo.getText() || "".equals(this.tfoutNo.getText())) {
				this.tfoutNo.setFocusable(true);
				this.tfoutNo.setBackground(Color.PINK);
				this.setDataState(DataState.EDIT);
				JOptionPane.showMessageDialog(DgFptBillItem.this,
						outNoTextInfo+"不能为空！", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return DataState.EDIT;
			}else {
				if (!RegexUtil.checkInteger(this.tfoutNo.getText())) {
					this.tfoutNo.setText(null);
					this.tfoutNo.setFocusable(true);
					this.tfoutNo.setBackground(Color.PINK);
					this.setDataState(DataState.EDIT);
					JOptionPane.showMessageDialog(DgFptBillItem.this,
							outNoTextInfo+"类型错误！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return DataState.EDIT;
					}
				data.setOutNo(Integer.valueOf(this.tfoutNo.getText()));
				this.tfoutNo.setBackground(this.tflistNo.getForeground());
			}
		} else {
			data.setOutNo(null);
		}
		data.setCopGName(this.tfcopGName.getText());
		data.setCopGModel(this.tfcopGModel.getText());
		data.setTrGno(Integer.valueOf(this.tftrGno.getText()));

		data.setNote(this.tfNote.getText());
		data.setQty(Double.valueOf(tfqty.getText()==null?"0":tfqty.getText()));
		data.setTradeQty(Double.valueOf(this.tftradeQty.getText()==null?"0":tftradeQty.getText()));

		data.setTradeUnit((Unit) this.cbbtradeUnit.getSelectedItem());
		data.setUnit((Unit) this.cbbunitName.getSelectedItem());

		data.setPtUnit((CalUnit) this.cbbptUnit.getSelectedItem());
		data.setPtAmount(Double.valueOf(this.tfptAmount.getValue().toString()));
		
		return DataState.BROWSE;
	}

	private int saveData() {
		int x = 0;
		if (dataState == DataState.ADD) {
			FptBillItem data = new FptBillItem();
			data.setListNo(Integer.valueOf(this.tflistNo.getText()));
			data.setFptBillHead(fptBillHead);
//			data.setModifyMake(ModifyMarkState.ADDED);
			x = fillData(data);
			data = this.fptManageAction.saveFptBillItem(new Request(CommonVars
					.getCurrUser()), data);
			tableModel.addRow(data);
		} else if (dataState == DataState.EDIT) {
			FptBillItem data = (FptBillItem) tableModel.getCurrentRow();
			x = fillData(data);
			data = this.fptManageAction.saveFptBillItem(new Request(CommonVars
					.getCurrUser()), data);
			tableModel.updateRow(data);
		}
		return x;
	}

	/**
	 * 检查是数字型是否合法
	 * 
	 * @return
	 */
	private boolean validateOther() {		
		try {
			Integer.valueOf(tftrGno.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "项号应该为数字型!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		try {
			Integer.valueOf(tfappGNo.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "申请表应该为数字型!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (FptInOutFlag.IN.equals(fptInOutFlag) && FptBusinessType.FPT_BILL.equals(fptBusinessType)) {
			try {
				Integer.valueOf(tfoutNo.getText());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, outNoTextInfo+"应该为数字型!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		}
		//控制发货，发退货序号不可以重复
		if(!"".equals(this.tfoutNo.getText().trim())){
//			boolean isExist = fptManageAction.isExistFptBillItemByOutNo(new Request(
//					CommonVars.getCurrUser()),fptBillHead, Integer.valueOf(this.tfoutNo.getText().trim()), fptInOutFlag);
			boolean isExist = isExist((FptBillItem) tableModel.getCurrentRow(),Integer.valueOf(this.tfoutNo.getText().trim()));
			if(isExist){
				if(FptBusinessType.FPT_BILL.equals(fptBusinessType)&&FptInOutFlag.IN.equals(fptInOutFlag)){
					JOptionPane.showMessageDialog(null, "发货序号已经存在不允许重复!", "警告",
							JOptionPane.INFORMATION_MESSAGE);
					return true;
				}else if(FptBusinessType.FPT_BILL_BACK.equals(fptBusinessType)&&FptInOutFlag.OUT.equals(fptInOutFlag)){
					JOptionPane.showMessageDialog(null, "发退货序号已经存在不允许重复!", "警告",
							JOptionPane.INFORMATION_MESSAGE);
					return true;
				}
			}
		}
		// 获取参数设置
				parameterSet = fptManageAction.findTransParameterSet(new Request(
						CommonVars.getCurrUser(), true));
				// 判断交易数量是否超量
				double currentAmount = CommonUtils.getDoubleExceptNull(Double
						.valueOf(tfqty.getText()==null?"0":tfqty.getText()));
				// 当选择控制收发货商品当前余数
				if(parameterSet.getControlBillCurrentAmount()==null){
					parameterSet.setControlBillCurrentAmount(true);
				}
				if (parameterSet.getControlBillCurrentAmount()) {
					if (currentAmount > info.getFptbillcurrentRemain()) {
						JOptionPane.showMessageDialog(null, "当前申请数量超出了当前余量!", "警告",
								JOptionPane.INFORMATION_MESSAGE);
						return true;
					} 
				} else {
					if (currentAmount > info.getFptbillcurrentRemain()) {
						if (JOptionPane.showConfirmDialog(this,
								" 当前申请数量超出了当前余量!确定继续吗？", "严重警告",
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
							return true;
						} 
					}
				}
		return false;
	}

	
	
	public boolean isExist(FptBillItem fptBillItem,int outNo){
		
		List list = tableModel.getList();
		for(int i = 0 ;i<list.size();i++){
			if(list.get(i) instanceof FptBillItem){
				FptBillItem item = (FptBillItem)list.get(i);
				if(item.getOutNo()!=null&&!fptBillItem.getId().equals(item.getId())){
					int newOutNO = item.getOutNo();
					if(outNo == newOutNO){
						return true;
					}
				}
			}
			
		}
		return false;
	}
	
	/**
	 * 检查数据是否存在空值
	 * 
	 * @return
	 */
	private boolean validateDataIsNull() {
		if (FptInOutFlag.IN.equals(fptInOutFlag) && FptBusinessType.FPT_BILL.equals(fptBusinessType)||
				FptInOutFlag.OUT.equals(fptInOutFlag) && FptBusinessType.FPT_BILL_BACK.equals(fptBusinessType)) {
			if (this.tfoutNo.getText().trim().equals("")) {
				this.tfoutNo.setFocusable(true);
				this.setDataState(DataState.EDIT);
				JOptionPane.showMessageDialog(null, outNoTextInfo+"不可为空!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}		

		}
		if (FptInOutFlag.IN.equals(fptInOutFlag) && FptBusinessType.FPT_BILL.equals(fptBusinessType)){
			if (this.tfEmsNo.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "手册/帐册号不可为空!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		}
		if (this.tftrGno.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "项号不可为空!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.tfappGNo.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "申请表序号不可为空!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.cbbunitName.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, "申报单位不可为空!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}

		if (this.cbbtradeUnit.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, "交易单位不可为空!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (fptBillHead.getProjectType().equals(ProjectType.BCUS)) {
			if (Double.parseDouble(this.tfqty.getText()==null?"0":tfqty.getText()) < 0) {
				JOptionPane.showMessageDialog(null, "申报数量小于零!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (Double.parseDouble(this.tftradeQty.getText()==null?"0":tftradeQty.getText()) < 0) {
				JOptionPane.showMessageDialog(null, "交易数量小于零!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		} else {
			if (Double.parseDouble(this.tfqty.getText()==null?"0":tfqty.getText()) <= 0) {
				JOptionPane.showMessageDialog(null, "申报数量小于等于零!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (Double.parseDouble(this.tftradeQty.getText()==null?"0":tftradeQty.getText()) <= 0) {
				JOptionPane.showMessageDialog(null, "交易数量小于等于零!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}

		}
		
		return false;
	}

	private void initComponents() {
		// TODO Auto-generated method stub
//		if (FptInOutFlag.OUT.equals(fptInOutFlag)) {
//			this.tfEmsNo.setEnabled(false);
//			this.tfEmsNo.setEditable(false);
//			if (FptBusinessType.FPT_BILL.equals(fptBusinessType)) {
//				this.tfoutNo.setEditable(false);
//			} else {
//				this.tfoutNo.setEditable(true);
//			}
//		} else {
//			this.tfEmsNo.setEditable(true);
//			this.tfEmsNo.setEnabled(true);
//			if (FptBusinessType.FPT_BILL.equals(fptBusinessType) ||
//					FptBusinessType.FPT_BILL_BACK.equals(fptBusinessType)) {
//				this.tfoutNo.setEnabled(true);
//				this.tfoutNo.setEditable(true);
//			} else {
//				this.tfoutNo.setEnabled(false);
//				this.tfoutNo.setEditable(false);
//			}
//		}
		if (FptBusinessType.FPT_BILL.equals(fptBusinessType)) {
			jLabel2.setText("收发货序号");
		} else {
			jLabel2.setText("收退货序号");
		}
		// 单位
		this.cbbunitName.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbunitName
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbunitName);
		this.cbbunitName.setSelectedItem(null);

		// 交单位
		this.cbbtradeUnit
				.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbtradeUnit.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbtradeUnit);
		//this.cbbtradeUnit.setSelectedItem(null);
		this.cbbtradeUnit.setSelectedItem(this.cbbunitName.getSelectedItem());
		// 工厂单位
		List listunit = materialManageAction.findCalUnit(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbptUnit.setModel(new DefaultComboBoxModel(listunit.toArray()));
		this.cbbptUnit.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbptUnit, "code", "name");
		this.cbbptUnit.setSelectedItem(null);
		// 这里是控制焦点的顺序，以方便键盘的输！
		List<Object> components = new ArrayList<Object>();
		components.add(this.cbbtradeUnit);
		components.add(null);
		components.add(this.tfNote);
		components.add(this.btnSave);
		components.add(new Component[] { this.btnNext, this.btnExit });
		this.setComponentFocusList(components);

	}

	/**
	 * This method initializes tfTransferFactoryBillNo1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(new Rectangle(103, 83, 144, 22));
			tfEmsNo.setEditable(false);
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes tfTransferFactoryBillNo11
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfcopGNo() {
		if (tfcopGNo == null) {
			tfcopGNo = new JTextField();
			tfcopGNo.setBounds(new Rectangle(95, 3, 142, 24));
			tfcopGNo.setEditable(false);
		}
		return tfcopGNo;
	}

	/**
	 * This method initializes tfqty
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JTextField getTfqty() {
		if (tfqty == null) {
//			tfqty = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfqty = new JTextField();
			tfqty.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					String stu = CommonUtils.formatDoubleByDigit(NumberUtils.toDouble(tfqty.getText()) ,5);
					tfqty.setText(stu);
					getFirstAmount();
				}
			});

//			CustomFormattedTextFieldUtils.addAutoCalcListener(tfqty, new AutoCalcListener() {
//				public void run() {
//					getFirstAmount();
//				}
//			});
//			
			tfqty.setBounds(104, 136, 144, 22);
			tfqty.setDocument(new PlainDocument(){
				@Override
				public void insertString(int offs, String str, AttributeSet a)
						throws BadLocationException {
					try {
						Double.parseDouble(str);
						super.insertString(offs, str, a);
					} catch (Exception e) {
						System.out.println(str);
						if(".".equals(str)){
							super.insertString(offs, str, a);
						}
					}
				}
			});
//			tfqty.setFormatterFactory(getDefaultFormatterFactory());
//			tfqty.addPropertyChangeListener("value",
//					new PropertyChangeListener() {
//						public void propertyChange(PropertyChangeEvent evt) {
//							getFirstAmount();
//						}
//
//					});
//			tfqty.addMouseListener(new java.awt.event.MouseAdapter() {
//				public void mousePressed(java.awt.event.MouseEvent e) {
//					getFirstAmount();
//				}
//			});
		}
		return tfqty;
	}
	
	public Map getUnitRateMap() {
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("克+千克", 0.001);
		map.put("个+千个", 0.001);
		map.put("支+千支", 0.001);
		map.put("块+千块", 0.001);
		map.put("米+千米", 0.001);
		map.put("千克+克", 1000.0);
		map.put("千个+个", 1000.0);
		map.put("千支+支", 1000.0);
		map.put("千块+块", 1000.0);
		map.put("千米+米", 1000.0);
		return map;
	}

	/**
	 * 当申报单位与交易单位相等时,申报数量等于交易数量
	 */
	private void getFirstAmount() {
		// TODO Auto-generated method stub
		double amount = 0;
		if (this.tfqty.getText() != null && !this.tfqty.getText().equals("")) {
			amount = Double.parseDouble(this.tfqty.getText()==null?"0":tfqty.getText());
		}
		
		Map<String, Double> map = getUnitRateMap();
		Unit unit = (Unit)cbbunitName.getSelectedItem();
		Unit trade = (Unit)cbbtradeUnit.getSelectedItem();
		if(unit==null||trade==null){
			return;
		}
		
		if(unit.getName().equals(trade.getName())){
			tftradeQty.setText(tfqty.getText());
		}else{
			Double d = map.get(unit.getName()+"+"+trade.getName());
			if(d==null){
				return;
			}
			String stu = CommonUtils.formatDoubleByDigit(
					CaleUtil.multiply(NumberUtils.toDouble(tfqty.getText()), d) ,5);
			tftradeQty.setText(stu);
		}
	}

	/**
	 * This method initializes cbbtradeUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbtradeUnit() {
		if (cbbtradeUnit == null) {
			cbbtradeUnit = new JComboBox();
			cbbtradeUnit.setBounds(new Rectangle(339, 110, 136, 23));
		}
		return cbbtradeUnit;
	}

	/**
	 * This method initializes cbbunitName
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbunitName() {
		if (cbbunitName == null) {
			cbbunitName = new JComboBox();
			 cbbunitName.addItemListener(new java.awt.event.ItemListener() {
		            public void itemStateChanged(java.awt.event.ItemEvent evt) {
		            	//cbbtradeUnit.setSelectedItem(cbbunitName.getSelectedItem());
		            }
		        });
			cbbunitName.setBounds(new Rectangle(103, 110, 144, 22));
		}
		return cbbunitName;
	}

	/**
	 * This method initializes tftrGno
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTftrGno() {
		if (tftrGno == null) {
			tftrGno = new JTextField();
			tftrGno.setBounds(new Rectangle(103, 305, 144, 22));
		}
		return tftrGno;
	}

	/**
	 * This method initializes btnComplex
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnComplex() {
		if (btnComplex == null) {
			btnComplex = new JButton();
			btnComplex.setBounds(new Rectangle(445, 304, 29, 24));
			btnComplex.setText("...");
			btnComplex.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Complex obj = (Complex) CommonQuery.getInstance()
							.getComplex();
					if (obj != null) {
						complex = obj;
						tfcomplex.setText(complex.getCode());
						tfcommName.setText(complex.getName());
						tfcommSpec.setText(complex.getName());
					}

				}
			});
		}
		return btnComplex;
	}

	public String getFptBusinessType() {
		return fptBusinessType;
	}

	public void setFptBusinessType(String fptBusinessType) {
		this.fptBusinessType = fptBusinessType;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnPrevious
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setText("上笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.previousRow()) {
						btnPrevious.setEnabled(false);
						btnNext.setEnabled(true);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					showData();
					dataState = DataState.EDIT;
					setState();

				}
			});
		}
		return btnPrevious;
	}

	/**
	 * This method initializes btnNext
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setText("下笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.nextRow()) {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(false);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					showData();
					dataState = DataState.EDIT;
					setState();

				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	public FptBillHead getFptBillHead() {
		return fptBillHead;
	}

	public void setFptBillHead(FptBillHead fptBillHead) {
		this.fptBillHead = fptBillHead;
	}

	/**
	 * This method initializes btnAddTop
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddTop() {
		if (btnAddTop == null) {
			btnAddTop = new JButton();
			btnAddTop.setText("新增");
			btnAddTop.setVisible(false);
			btnAddTop.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initAddData();
					dataState = DataState.ADD;
					setState();
				}
			});
		}
		return btnAddTop;
	}

	public boolean isIsdataState() {
		return isdataState;
	}

	public void setIsdataState(boolean isdataState) {
		this.isdataState = isdataState;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(8, 188, 478, 112));
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel11 = new JLabel();
			jLabel11.setText("归并前型号规格");
			jLabel11.setBounds(new Rectangle(5, 30, 88, 22));
			jLabel.setText("归并前商品名称");
			jLabel.setBounds(new Rectangle(5, 56, 88, 22));
			jPanel.add(getTfcopGName(), null);
			jPanel.add(getTfcopGModel(), null);
			jPanel.add(jLabel1941, null);
			jPanel.add(getTfcopGNo(), null);
			jPanel.add(jLabel11, null);
			jPanel.add(jLabel, null);
			jPanel.add(getTfptAmount(), null);
			jPanel.add(jLabel6111, null);
			jPanel.add(jLabel611, null);
			jPanel.add(getCbbptUnit(), null);
		}
		return jPanel;
	}

	public Boolean getIsAppOrMerger() {
		return isAppOrMerger;
	}

	public void setIsAppOrMerger(Boolean isAppOrMerger) {
		this.isAppOrMerger = isAppOrMerger;
	}

	/**
	 * This method initializes cbbptUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbptUnit() {
		if (cbbptUnit == null) {
			cbbptUnit = new JComboBox();
			cbbptUnit.setBounds(new Rectangle(95, 84, 144, 23));
		}
		return cbbptUnit;
	}

	/**
	 * This method initializes tfptAmount
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfptAmount() {
		if (tfptAmount == null) {
			DefaultFormatterFactory defaultFormatterFactory1 = new DefaultFormatterFactory();
			defaultFormatterFactory1.setDefaultFormatter(new NumberFormatter());
			defaultFormatterFactory1.setDisplayFormatter(new NumberFormatter());
			defaultFormatterFactory1.setEditFormatter(new NumberFormatter());
			tfptAmount = new JCustomFormattedTextField();
			tfptAmount.setBounds(new Rectangle(335, 84, 133, 21));
			tfptAmount.setFormatterFactory(defaultFormatterFactory1);
			tfptAmount.setEditable(false);
		}
		return tfptAmount;
	}


} // @jve:decl-index=0:visual-constraint="10,10"
