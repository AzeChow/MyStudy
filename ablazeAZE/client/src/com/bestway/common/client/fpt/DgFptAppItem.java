/*
 * Created on 2004-7-26
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcs.client.contractexe.DgBcsExportCustomsDeclarationCommInfo;
import com.bestway.bcus.client.common.AutoCalcListener;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.common.fpt.entity.FptParameterSet;
import com.bestway.common.fpt.entity.TempFptExeInfo;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.util.RegexUtil;

/**
 * @author bsway // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DgFptAppItem extends DgCommon {

	private static final long serialVersionUID = 1L;
	private JPanel jpan = null;
	private JToolBar jToolBar = null;
	private JButton btnSave = null;
	private JButton btnCancel = null;
	private JTextField tfGName = null;
	private JTextField tfComplexCode = null;
	private JTextField tfGModel = null;
	private JFormattedTextField tfQty = null;
	private JFormattedTextField tfUnitPrice = null;
	private JTableListModel tableModel = null;
	private FptAppHead fptAppHead = null; // @jve:decl-index=0:
	private int dataState = DataState.BROWSE;
	private JTextField tfStandComplexCode = null;
	private JComboBox cbbUnit = null;

	private DefaultFormatterFactory defaultFormatterFactory1 = null;
	private NumberFormatter numberFormatter = null;
	private JLabel jLabel16 = null;
	private JLabel jLabel17 = null;
	private JTextField tfTrNo = null;
	private JLabel jLabel18 = null;
	private JTextField tfListNo = null;
	private JLabel jLabel19 = null;
	private JTextField tfInEmsNo = null;
	private JLabel jLabel131 = null;
	private JLabel jLabel132 = null;
	private JLabel jLabel71 = null;
	private JLabel jLabel20 = null;
	private JTextField tfNote = null;
	private JPanel jContentPane = null;
	private JButton btnEdit = null;
	private JButton btnPrevious = null;
	private JButton btnNext = null;
	private JButton btnClose = null;
	private JLabel lbInfo = null;
	private JButton btnComplex = null;
	private JButton btnComplex1 = null;
	private JComboBox cbbUnit1 = null;
	private JCustomFormattedTextField tfQty1 = null;
	private JComboBox cbbCurrency = null;
	private JCustomFormattedTextField tfTotalPrice = null;
	private String fptInOutFlag = FptInOutFlag.OUT; // @jve:decl-index=0:
	private FptParameterSet parameterSet = null;
	public DgFptAppItem() {
		super();
		initialize();
	}

	private void initialize() {
//		this.setContentPane(getJContentPane());
		this.setTitle("转厂申请表明细编辑");
		this.setSize(668, 405);
		this.setContentPane(getJContentPane());
	}

	@Override
	public void setVisible(boolean isFlag) {
		if (isFlag) {
			initUIComponents();
			if (dataState == DataState.ADD) {
				// emptyData();
				// fillMateriel();
				// ok!!
			} else {
				showData();
			}
			// if (this.getFptInOutFlag().equals(FptInOutFlag.IN)) {
			// this.setTitle("转入申请单明细编辑");
			// } else {
			// this.setTitle("转出申请单明细编辑");
			// }
			setState();
		}
		super.setVisible(isFlag);
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
	public void setTransferFactoryManageAction(FptManageAction transferFactoryManageAction) {
		this.fptManageAction = transferFactoryManageAction;
	}

	/**
	 * @return Returns the dataState.
	 */
	public int getDataState() {
		return dataState;
	}

	/**
	 * @param dataState
	 *            The dataState to set.
	 */
	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * @return Returns the impExpRequestBill.
	 */
	public FptAppHead getFptAppHead() {
		return fptAppHead;
	}

	/**
	 * @param impExpRequestBill
	 *            The impExpRequestBill to set.
	 */
	public void setFptAppHead(FptAppHead impExpRequestBill) {
		this.fptAppHead = impExpRequestBill;
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
	 * This method initializes
	 * 
	 */
	/**
	 * This method initializes jpan
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJpan() {
		if (jpan == null) {

			lbInfo = new JLabel();
			lbInfo.setBounds(new Rectangle(18, 11, 635, 28));
			lbInfo.setFont(new Font("Dialog", Font.BOLD, 14));
			lbInfo.setForeground(new Color(0, 51, 255));
			lbInfo.setBackground(new Color(238, 238, 238));
			lbInfo.setText("转出转入信息提示:");
			jLabel20 = new JLabel();
			jLabel20.setBounds(new Rectangle(35, 230, 92, 22));
			jLabel20.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel20.setText("备注");
			jLabel71 = new JLabel();
			jLabel71.setBounds(new Rectangle(304, 116, 92, 22));
			jLabel71.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel71.setText("备用商品编码");
			jLabel132 = new JLabel();
			jLabel132.setBounds(new Rectangle(304, 202, 92, 22));
			jLabel132.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel132.setForeground(Color.blue);
			jLabel132.setText("法定单位");
			jLabel131 = new JLabel();
			jLabel131.setBounds(new Rectangle(59, 202, 68, 22));
			jLabel131.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel131.setText("法定数量");
			jLabel19 = new JLabel();
			jLabel18 = new JLabel();
			java.awt.GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			javax.swing.JLabel jLabel14 = new JLabel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel13 = new JLabel();
			javax.swing.JLabel jLabel11 = new JLabel();
			javax.swing.JLabel jLabel10 = new JLabel();
			jpan = new JPanel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			jLabel10.setText("商品名称");
			jLabel10.setForeground(Color.blue);
			jLabel10.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel11.setText("商品编码");
			jLabel11.setForeground(Color.blue);
			jLabel11.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel13.setText("申报数量");
			jLabel13.setForeground(Color.blue);
			jLabel13.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel14.setText("币制");
			jLabel14.setHorizontalAlignment(SwingConstants.RIGHT);
			jpan.setLayout(null);

			jLabel17 = new JLabel();
			jLabel16 = new JLabel();
			jLabel16.setText("转出序号");
			jLabel16.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel16.setForeground(Color.black);
			jLabel16.setBounds(new Rectangle(75, 53, 52, 22));
			jLabel17.setText("商品项号");
			jLabel17.setForeground(Color.blue);
			jLabel17.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel17.setBounds(new Rectangle(304, 87, 92, 22));

			jLabel.setText("规格型号");
			jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel.setBounds(304, 144, 92, 22);
			jLabel2.setText("申报单位");
			jLabel2.setForeground(Color.blue);
			jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel2.setBounds(304, 174, 92, 22);
			jLabel6.setText("单价");
			jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel6.setBounds(55, 247, 68, 22);
			jLabel6.setVisible(false);
			jLabel7.setText("总价");
			jLabel7.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel7.setBounds(55, 272, 68, 22);
			jLabel7.setVisible(false);
			gridBagConstraints10.gridx = 3;
			gridBagConstraints10.gridy = 7;
			gridBagConstraints10.ipadx = -24;
			gridBagConstraints10.ipady = -8;
			gridBagConstraints10.insets = new java.awt.Insets(3, 1, 2, 31);
			gridBagConstraints11.gridx = 3;
			gridBagConstraints11.gridy = 6;
			gridBagConstraints11.ipadx = -24;
			gridBagConstraints11.ipady = -8;
			gridBagConstraints11.insets = new java.awt.Insets(5, 1, 1, 31);
			jLabel10.setBounds(59, 144, 68, 22);
			jLabel11.setBounds(59, 115, 68, 22);
			jLabel11.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
			jLabel13.setBounds(59, 172, 68, 22);
			jLabel14.setBounds(300, 248, 92, 22);
			jLabel14.setVisible(false);
			jLabel18.setBounds(59, 87, 68, 22);
			jLabel18.setForeground(Color.blue);
			jLabel18.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel18.setText("序号");
			jLabel19.setBounds(304, 53, 92, 22);
			jLabel19.setBackground(new Color(238, 238, 238));
			jLabel19.setForeground(Color.black);
			jLabel19.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel19.setText("转入手册/帐册号");
			jpan.add(getTfGName(), null);
			jpan.add(getTfComplexCode(), null);
			jpan.add(getTfGModel(), null);
			jpan.add(jLabel10, null);
			jpan.add(jLabel11, null);
			jpan.add(jLabel13, null);
			jpan.add(jLabel14, null);
			jpan.add(jLabel, null);
			jpan.add(jLabel2, null);
			jpan.add(jLabel6, null);
			jpan.add(jLabel7, null);
			jpan.add(getTfQty(), null);
			jpan.add(getTfUnitPrice(), null);
			jpan.add(jLabel18, null);
			jpan.add(getTfListNo(), null);
			jpan.add(jLabel19, null);
			jpan.add(getTfInEmsNo(), null);
			jpan.add(jLabel16, null);
			jpan.add(jLabel17, null);
			jpan.add(getTfTrNo(), null);
			// jpan.add(jLabel9, null);
			jpan.add(getTfStandComplexCode(), null);
			// jpan.add(jLabel12, null);
			jpan.add(getCbbUnit(), null);
			jpan.add(jLabel131, null);
			jpan.add(jLabel132, null);
			jpan.add(jLabel71, null);
			jpan.add(jLabel20, null);
			jpan.add(getTfNote(), null);
			jpan.add(lbInfo, null);
			jpan.add(getBtnComplex(), null);
			jpan.add(getBtnComplex1(), null);
			jpan.add(getCbbUnit1(), null);
			jpan.add(getTfQty1(), null);
			jpan.add(getCbbCurrency(), null);
			jpan.add(getTfTotalPrice(), null);
			jpan.add(getTfInOutNo(), null);
			jpan.add(getBtnInOutNo(), null);
		}
		return jpan;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnPrevious());
			jToolBar.add(getBtnNext());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnClose());
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
					if (vaildatorDataIsNull()) {
						return;
					}
					if(validateOther()){
						return;
					}
					saveData();
					dataState = DataState.BROWSE;
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
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.BROWSE;
					showData();
					setState();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes tfOppositeQuantity
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfQty() {
		if (tfQty == null) {
			tfQty = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfQty.setFormatterFactory(getDefaultFormatterFactory1());
			tfQty.setText("0");
			CustomFormattedTextFieldUtils.addAutoCalcListener(tfQty, new AutoCalcListener() {
				public void run() {
					doTotalPrice();
					doAccountQty1();
				}
			});
			// tfQty.getDocument().addDocumentListener(
			// new DocumentListenerAdapter());
			tfQty.setBounds(129, 172, 148, 22);

		}
		return tfQty;
	}

	/**
	 * This method initializes tfOppositeQuantity
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitPrice() {
		if (tfUnitPrice == null) {
			tfUnitPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitPrice.setEditable(true);
			// tfUnitPrice.getDocument().addDocumentListener(
			// new DocumentListenerAdapter());
			CustomFormattedTextFieldUtils.addAutoCalcListener(tfUnitPrice, new AutoCalcListener() {
				public void run() {
					doTotalPrice();
				}
			});
			tfUnitPrice.setFormatterFactory(getDefaultFormatterFactory1());
			tfUnitPrice.setBounds(125, 247, 148, 22);
			tfUnitPrice.setVisible(false);
		}
		return tfUnitPrice;
	}

	/**
	 * This method initializes tfGName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfGName() {
		if (tfGName == null) {
			tfGName = new JTextField();
			tfGName.setEditable(false);
			tfGName.setBounds(129, 144, 148, 22);
			// tfGName.setBackground(java.awt.Color.white);

		}
		return tfGName;
	}

	// /**
	// * This method initializes jContentPane
	// *
	// * @return javax.swing.JPanel
	// */
	// private JPanel getJContentPane() {
	// if (jContentPane == null) {
	// jLabel17 = new JLabel();
	// jLabel16 = new JLabel();
	// jLabel16.setText("转出序号");
	// jLabel16.setHorizontalAlignment(SwingConstants.RIGHT);
	// jLabel16.setBounds(new Rectangle(59, 69, 68, 22));
	// jLabel17.setText("商品项号");
	// jLabel17.setForeground(Color.blue);
	// jLabel17.setHorizontalAlignment(SwingConstants.RIGHT);
	// jLabel17.setBounds(new Rectangle(304, 70, 92, 22));
	// }
	// return jContentPane;
	// }

	/**
	 * This method initializes tfStandComplexCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfStandComplexCode() {
		if (tfStandComplexCode == null) {
			tfStandComplexCode = new JTextField();
//			tfStandComplexCode.setBounds(new Rectangle(398, 116, 131, 22));
			tfStandComplexCode.setBounds(new Rectangle(398, 116, 148, 22));
		}
		return tfStandComplexCode;
	}

	/**
	 * This method initializes cbbUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUnit() {
		if (cbbUnit == null) {
			cbbUnit = new JComboBox();
			cbbUnit.setEnabled(false);
			cbbUnit.setBounds(new Rectangle(398, 174, 148, 22));
			// cbbUnit.addItemListener(new java.awt.event.ItemListener() {
			// public void itemStateChanged(java.awt.event.ItemEvent e) {
			// if (e.getStateChange() == ItemEvent.SELECTED) {
			// doAccountQty1();
			// }
			// }
			// });
		}
		return cbbUnit;
	}

	/**
	 * This method initializes tfComplexCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplexCode() {
		if (tfComplexCode == null) {
			tfComplexCode = new JTextField();
			tfComplexCode.setEnabled(false);
			tfComplexCode.setEditable(false);
//			tfComplexCode.setBounds(129, 115, 129, 22);
			tfComplexCode.setBounds(129, 115, 148, 22);
			// tfComplexCode.setBackground(java.awt.Color.white);
		}
		return tfComplexCode;
	}

	/**
	 * This method initializes tfGModel
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfGModel() {
		if (tfGModel == null) {
			tfGModel = new JTextField();
			 tfGModel.setEditable(false);
			tfGModel.setBounds(398, 144, 148, 22);
			// tfGModel.setBackground(java.awt.Color.white);
		}
		return tfGModel;
	}

	private void setState() {

		FptAppItem fptAppItem = (FptAppItem) tableModel.getCurrentRow();
		if (fptAppItem == null) {
			return;
		}
		//
		// 是新增状态
		//
		boolean isAdd = false;
		boolean isDelete = false;
		if (fptAppItem != null) {
			isAdd = ModifyMarkState.ADDED.equals(fptAppItem.getModifyMarkState());
			isDelete = ModifyMarkState.DELETED.equals(fptAppItem.getModifyMarkState());
		}
		//
		// 重置进出状态标识
		//
		this.setFptInOutFlag(fptAppItem.getInOutFlag());
		//
		// 获得其申报状态
		//
		String declareState = this.fptAppHead.getDeclareState();
		boolean isChangingExe = DeclareState.CHANGING_EXE.equals(declareState);
		// boolean isProcessExe = DeclareState.PROCESS_EXE.equals(declareState);
		boolean isApplyPor = DeclareState.APPLY_POR.equals(declareState);
		// boolean isWaitEaa = DeclareState.WAIT_EAA.equals(declareState);
		//
		// 能编辑
		//
		boolean isCanEdit = isApplyPor || isChangingExe;

		this.btnEdit.setEnabled(this.dataState == DataState.BROWSE && isCanEdit && !isDelete);
		this.btnCancel.setEnabled(this.dataState != DataState.BROWSE && isCanEdit);
		this.btnPrevious.setEnabled(tableModel.hasPreviousRow());
		this.btnNext.setEnabled(tableModel.hasNextRow());
		
		this.btnSave.setEnabled(this.dataState != DataState.BROWSE && isCanEdit);
		
		this.btnComplex.setEnabled(this.dataState != DataState.BROWSE && isCanEdit);
		this.btnComplex1.setEnabled(this.dataState != DataState.BROWSE && isCanEdit);

		// this.tfTrNo.setEditable(this.dataState != DataState.BROWSE && isAdd
		// && isCanEdit);
		
		// this.tfComplexCode.setEditable(this.dataState != DataState.BROWSE &&
		// isCanEdit);
		this.tfComplexCode.setEditable(false);
		this.tfStandComplexCode.setEditable(false);

		// this.tfGName.setEditable(this.dataState != DataState.BROWSE &&
		// isCanEdit);
		
		//this.tfGModel.setEditable(this.dataState != DataState.BROWSE && isCanEdit);
		
		this.tfQty.setEditable(this.dataState != DataState.BROWSE && isCanEdit);
		
		this.tfQty1.setEditable(this.dataState != DataState.BROWSE && isCanEdit);
		
		// this.cbbUnit1.setEnabled(this.dataState != DataState.BROWSE &&
		// isCanEdit);
		
		this.cbbCurrency.setEnabled(this.dataState != DataState.BROWSE && isCanEdit);
		
		this.tfUnitPrice.setEditable(this.dataState != DataState.BROWSE && isCanEdit);
		
		this.tfNote.setEditable(this.dataState != DataState.BROWSE && isCanEdit);
		
		// this.cbbUnit.setEnabled(this.dataState != DataState.BROWSE &&
		// isCanEdit);

		if (this.getFptInOutFlag().equals(FptInOutFlag.IN)) {
			this.setTitle("转入申请单明细编辑");
			jLabel18.setText("转入序号");
			jLabel19.setForeground(Color.blue);
			jLabel16.setForeground(Color.blue);
			this.tfInEmsNo.setEditable(this.dataState != DataState.BROWSE && isCanEdit);
			this.tfInOutNo.setEnabled(this.dataState != DataState.BROWSE && isCanEdit);
			// btnInOutNo.setEnabled(this.dataState != DataState.BROWSE
			// && isCanEdit);
			jLabel19.setVisible(true);
			jLabel16.setVisible(true);
			this.tfInEmsNo.setVisible(true);
			this.tfInOutNo.setVisible(true);
			btnInOutNo.setVisible(false);
			tfInOutNo.setBounds(new Rectangle(130, 52, 148, 23));

		} else {
			jLabel19.setVisible(false);
			jLabel16.setVisible(false);
			this.tfInEmsNo.setVisible(false);
			this.tfInOutNo.setVisible(false);
			btnInOutNo.setVisible(false);
			this.setTitle("转出申请单明细编辑");
		}

	}

	private Complex codeTs = null; // @jve:decl-index=0:
	private Complex standComplex = null; // @jve:decl-index=0:

	/**
	 * 显示数据
	 * 
	 */
	private void showData() {
		FptAppItem f = (FptAppItem) tableModel.getCurrentRow();
		if (this.dataState == DataState.ADD) {
			codeTs = null;
			standComplex = null;
		} else if (this.dataState == DataState.EDIT || this.dataState == DataState.BROWSE) {
			this.tfListNo.setText(f.getListNo() == null ? "" : f.getListNo().toString());
			this.tfInEmsNo.setText(f.getInEmsNo());
			if (f.getInOutNo() != null) {
				this.tfInOutNo.setValue(f.getInOutNo());
			} else {
				this.tfInOutNo.setValue(null);
			}

			// this.cbbInOutNo(f.getInOutNo());
			this.tfTrNo.setText(f.getTrNo() == null ? "" : f.getTrNo().toString());

			codeTs = f.getCodeTs();
			standComplex = f.getStandComplex();
			if (codeTs != null) {
				this.tfComplexCode.setText(codeTs.getCode());
			} else {
				this.tfComplexCode.setText("");
			}
			if (standComplex != null) {
				this.tfStandComplexCode.setText(standComplex.getCode());
			} else {
				this.tfStandComplexCode.setText("");
			}

			this.tfGName.setText(f.getName());
			this.tfGModel.setText(f.getSpec());

			if (f.getQty() != null) {
				this.tfQty.setValue(f.getQty());
			} else {
				this.tfQty.setValue(new Double(0));
			}
			if (f.getQty1() != null) {
				this.tfQty1.setValue(f.getQty1());
			} else {
				this.tfQty1.setValue(new Double(0));
			}
			// this.tfQty.setValue(f.getQty());
			// this.tfQty1.setValue(f.getQty1());
			this.cbbUnit.setSelectedItem(f.getUnit());
			this.cbbUnit1.setSelectedItem(f.getUnit1());
			// this.tfUnitPrice.setValue(f.getUnitPrice());
			if (f.getUnitPrice() != null) {
				this.tfUnitPrice.setValue(f.getUnitPrice());
			} else {
				this.tfUnitPrice.setValue(new Double(0));
			}
			this.cbbCurrency.setSelectedItem(f.getCurr());
			this.tfNote.setText(f.getNote());
			this.doTotalPrice();
		}
		//
		// 显示数据信息
		//
		showFptExeInfo(f);
	}

	private TempFptExeInfo info = new TempFptExeInfo(); // @jve:decl-index=0:
	private JCustomFormattedTextField tfInOutNo = null;
	private JButton btnInOutNo = null;

	/**
	 * 显示数据信息
	 * 
	 * @param fptAppItem
	 */
	private void showFptExeInfo(FptAppItem fptAppItem) {
		Integer projectType = fptAppItem.getFptAppHead().getProjectType();
		String inOutFlag = fptAppItem.getInOutFlag();

		boolean isSearch = true;
		StringBuffer sb = new StringBuffer();
		if (projectType == null) {
			sb.append("当前记录的项目类型为空;");
			isSearch = false;
		}
		Integer seqNum = fptAppItem.getTrNo();
		if (seqNum == null) {
			sb.append("当前记录的商品项号为空;");
			isSearch = false;
		}
		String emsNo = null;
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 进,料件
			emsNo = fptAppItem.getInEmsNo();
			if (emsNo == null || "".equals(emsNo.trim())) {
				sb.append("当前记录转入手册/帐册编号为空;");
				isSearch = false;
			}
		} else { // 转出,成品
			emsNo = fptAppItem.getFptAppHead().getEmsNo();
			if (emsNo == null || "".equals(emsNo.trim())) {
				sb.append("当前记录转出手册/帐册编号为空;");
				isSearch = false;
			}
		}
		if (isSearch) {
			info = this.fptManageAction.findContractExeInfoByFpt(new Request(CommonVars.getCurrUser(), true),
					projectType, inOutFlag, emsNo, seqNum, fptAppItem);
			String str = "";
			if (projectType == ProjectType.BCUS) {
				str = "正在执行的转厂使用量:" + info.getContractRemain() + "; 当前转厂使用量:" + info.getCurrentRemain();
			} else {
				str = "合同定量:" + info.getContractAmount() + "; 合同余量:" + info.getContractRemain() 
				+ "; 当前余量:" + info.getCurrentRemain() 
				+ "; 可申请量:" + info.getApplyRemain();
			}
			this.lbInfo.setText(str);
		} else {
			this.lbInfo.setText(sb.toString());
		}
	}

	/**
	 * 填充数据
	 */
	private void fillData(FptAppItem f) {
		// f.getListNo(this.tfListNo.getText() == null ? "" : f.getListNo()
		// .toString());
		f.setInEmsNo(this.tfInEmsNo.getText());
		if (this.getFptInOutFlag().equals(FptInOutFlag.IN)) {
			f.setInOutNo(!RegexUtil.checkNumber(this.tfInOutNo.getValue().toString()) ? null : Integer
					.valueOf(this.tfInOutNo.getValue().toString()));
		}
		f.setTrNo(!RegexUtil.checkNumber(this.tfTrNo.getText().trim()) ? null : Integer.valueOf(this.tfTrNo.getText()));
		f.setCodeTs(codeTs);
		f.setStandComplex(standComplex);
		f.setName(this.tfGName.getText());
		f.setSpec(this.tfGModel.getText());

		f.setQty(Double.valueOf(this.tfQty.getValue().toString()));
		f.setQty1(Double.valueOf(this.tfQty1.getValue().toString()));
		f.setUnit((Unit) this.cbbUnit.getSelectedItem());
		f.setUnit1((Unit) this.cbbUnit1.getSelectedItem());
		f.setUnitPrice(Double.valueOf(this.tfUnitPrice.getValue().toString()));
		f.setCurr((Curr) this.cbbCurrency.getSelectedItem());
		f.setNote(this.tfNote.getText());

		if (ModifyMarkState.UNCHANGE.equals(f.getModifyMarkState())) {
			f.setModifyMarkState(ModifyMarkState.MODIFIED);
		}

	}

	/**
	 * 保存数据
	 */
	private void saveData() {
		if (!RegexUtil.checkNumber(this.tfInOutNo.getValue() == null ? "":this.tfInOutNo.getValue().toString()) && this.fptInOutFlag.equals(FptInOutFlag.IN)) {
			JOptionPane.showMessageDialog(null, "转出序号为空", "警告", JOptionPane.INFORMATION_MESSAGE);
		}

		if (dataState == DataState.ADD) {
			FptAppItem data = new FptAppItem();
			fillData(data);
			this.fptManageAction.saveFptAppItem(new Request(CommonVars.getCurrUser()), data);
			tableModel.addRow(data);
		} else if (dataState == DataState.EDIT) {
			FptAppItem data = (FptAppItem) tableModel.getCurrentRow();
			fillData(data);
			this.fptManageAction.saveFptAppItem(new Request(CommonVars.getCurrUser()), data);
			tableModel.updateRow(data);
		}
	}

	/**
	 * 检查数量
	 * 
	 * @return
	 */
	private boolean validateOther() {
		//获取参数设置
		parameterSet = fptManageAction .findTransParameterSet(new Request(CommonVars .getCurrUser(), true));
		// 判断交易数量是否超量
		double currentAmount = CommonUtils.getDoubleExceptNull(Double
				.valueOf(this.tfQty.getValue().toString()));
		Integer projectType =this.fptAppHead.getProjectType();
		//当选择控制申请表商品当前余数
		if(parameterSet.getControlAppCurrentAmount()==null){
			parameterSet.setControlAppCurrentAmount(true);
		}
		if(parameterSet.getControlAppCurrentAmount()&&projectType!= ProjectType.BCUS){
			if (currentAmount > info.getCurrentRemain()) {
				JOptionPane.showMessageDialog(null, "当前申请数量超出了当前余量!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}else{
				return false;
			}
		}else{
			if (currentAmount > info.getCurrentRemain()) {
				if (JOptionPane.showConfirmDialog(
						this," 当前申请数量超出了当前余量!确定继续吗？",
						"严重警告", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}
	
	
	private boolean vaildatorDataIsNull() {
		FptAppItem f = (FptAppItem) tableModel.getCurrentRow();
		//
		// 商品项号
		//
		if (this.tfTrNo.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "商品项号不可为空", "警告", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		//
		// 商品编码
		//
		if (this.tfComplexCode.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "商品编码不可为空", "警告", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		//
		// 商品名称
		//
		if (this.tfGName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "商品名称不可为空", "警告", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		//
		// 验证数量
		//
		if (this.tfQty.getValue() == null) {
			JOptionPane.showMessageDialog(null, "申报数量不可为空", "警告", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		double qty = Double.parseDouble(this.tfQty.getValue().toString());
		if (qty <= 0.0) {
			JOptionPane.showMessageDialog(null, "申报数量不可为空", "警告", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		//
		// 申报单位
		//
		if (this.cbbUnit.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, "申报单位不可为空", "警告", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		//
		// 法定单位
		//
		if (this.cbbUnit1.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, "法定单位不可为空", "警告", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		//
		// 转入验证
		//
		if (this.fptInOutFlag.equals(FptInOutFlag.IN)) {
			if (this.tfInEmsNo.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "转入手册/帐册号不可为空!!", "警告", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (this.tfInOutNo.getValue() == null) {
				JOptionPane.showMessageDialog(null, "转出序号不可为空!!", "警告", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		}
		return false;
	}

	// /**
	// * 统计数据
	// */
	// private String getAmountStr() {
	// String amountStr = "0";
	// try {
	// double amount = Double.parseDouble(tfQty.getValue().toString())
	// * Double.parseDouble(tfUnitPrice.getValue().toString());
	// BigDecimal bd = new BigDecimal(amount);
	// amountStr = bd.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	// } catch (Exception ex) {
	// }
	// return amountStr;
	// }

	/**
	 * 初始化组件
	 */
	private void initUIComponents() {
		// 对方公司商品信息单位
		this.cbbUnit.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbUnit.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbUnit);
		cbbUnit.setSelectedItem(null);

		this.cbbUnit1.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbUnit1.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbUnit1);
		cbbUnit1.setSelectedItem(null);

		this.cbbCurrency.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.cbbCurrency.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbCurrency);
		cbbCurrency.setSelectedItem(null);

		if (this.getFptInOutFlag().equals(FptInOutFlag.IN)) {
			// this.btnInOutNo.setVisible(false);

			// List<Integer> listNos = this.fptManageAction
			// .findFptAppItemListNoByOut(new Request(CommonVars
			// .getCurrUser()), this.fptAppHead.getId());
			// this.cbbInOutNo
			// .setModel(new DefaultComboBoxModel(listNos.toArray()));
			// this.cbbInOutNo.setSelectedItem(null);

			// List dataSource = this.fptManageAction.findFptAppItems(
			// new Request(CommonVars.getCurrUser()), this.fptAppHead
			// .getId(), FptInOutFlag.OUT);
			// initTable(dataSource);
		} else {
			// List dataSource = this.fptManageAction.findFptAppItems(
			// new Request(CommonVars.getCurrUser()), this.fptAppHead
			// .getId(), FptInOutFlag.IN);
			// initTable(dataSource);
		}
		cbbUnit.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					doAccountQty1();
				}
			}
		});
		cbbUnit1.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					doAccountQty1();
				}
			}
		});
	}

	// /**
	// *
	// * 初始化表格对象
	// */
	// private void initTable(List list) {
	// // JTableListModel a = new JTableListModel(jTable, list,
	// // new JTableListModelAdapter() {
	// // public List InitColumns() {
	// // List<JTableListColumn> list = new Vector<JTableListColumn>();
	// //
	// // list.add(addColumn("转入转出标记", "inOutFlag", 100));
	// // list.add(addColumn("修改标记", "modifyMarkState", 75));
	// // list.add(addColumn("序号", "listNo", 50));
	// // list.add(addColumn("转出序号", "inOutNo", 80));
	// // list.add(addColumn("转入手册号", "inEmsNo", 100));
	// //
	// // list.add(addColumn("商品项号", "trNo", 80));
	// // list.add(addColumn("商品编码", "codeTs.code", 120));
	// // list.add(addColumn("商品名称", "name", 80));
	// // list.add(addColumn("规格型号", "spec", 80));
	// // list.add(addColumn("计量单位", "unit.name", 80));
	// //
	// // list.add(addColumn("法定单位", "unit1.name", 80));
	// // list.add(addColumn("申报数量", "qty", 80));
	// // list.add(addColumn("法定数量", "qty1", 80));
	// // list.add(addColumn("单价", "unitPrice", 60));
	// // list.add(addColumn("总价", "totalPrice", 60));
	// //
	// // list.add(addColumn("币制", "curr.name", 60));
	// // list.add(addColumn("备用商品编码", "standComplex.code", 100));
	// // list.add(addColumn("备注", "note", 60));
	// // return list;
	// // }
	// // });
	// // jTable.getColumnModel().getColumn(1).setCellRenderer(
	// // new DefaultTableCellRenderer() {
	// // public Component getTableCellRendererComponent(
	// // JTable table, Object value, boolean isSelected,
	// // boolean hasFocus, int row, int column) {
	// // super.getTableCellRendererComponent(table, value,
	// // isSelected, hasFocus, row, column);
	// // super.setText((value == null) ? "" : castValue(value));
	// // return this;
	// // }
	// //
	// // private String castValue(Object value) {
	// // return FptInOutFlag.getNote(value.toString());
	// // }
	// // });
	// // jTable.getColumnModel().getColumn(2).setCellRenderer(
	// // new DefaultTableCellRenderer() {
	// // public Component getTableCellRendererComponent(
	// // JTable table, Object value, boolean isSelected,
	// // boolean hasFocus, int row, int column) {
	// // super.getTableCellRendererComponent(table, value,
	// // isSelected, hasFocus, row, column);
	// // super.setText((value == null) ? "" : castValue(value));
	// // return this;
	// // }
	// //
	// // private String castValue(Object value) {
	// // return ModifyMarkState.getModifyMarkSpec(value
	// // .toString());
	// // }
	// // });
	// //
	// //
	// jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	// }

	/**
	 * This method initializes defaultFormatterFactory1
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory1() {
		if (defaultFormatterFactory1 == null) {
			defaultFormatterFactory1 = new DefaultFormatterFactory();
			defaultFormatterFactory1.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory1.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory1.setEditFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory1;
	}

	/**
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setMaximumFractionDigits(5);
			decimalFormat.setGroupingSize(5);
			numberFormatter = new NumberFormatter();
			numberFormatter.setFormat(decimalFormat);
		}
		return numberFormatter;
	}

	/**
	 * This method initializes tfTrNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTrNo() {
		if (tfTrNo == null) {
			tfTrNo = new JTextField();
			tfTrNo.setEnabled(false);
			tfTrNo.setBounds(new Rectangle(398, 87, 148, 22));
		}
		return tfTrNo;
	}

	/**
	 * This method initializes tfListNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfListNo() {
		if (tfListNo == null) {
			tfListNo = new JTextField();
			tfListNo.setEditable(false);
			tfListNo.setLocation(new Point(129, 87));
			tfListNo.setSize(new Dimension(148, 22));
			// tfListNo.setBackground(java.awt.Color.white);
		}
		return tfListNo;
	}

	/**
	 * This method initializes tfInEmsNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInEmsNo() {
		if (tfInEmsNo == null) {
			tfInEmsNo = new JTextField();
			tfInEmsNo.setBounds(398, 53, 148, 22);
			// tfInEmsNo.setEditable(false);
			// tfInEmsNo.setBackground(java.awt.Color.white);
		}
		return tfInEmsNo;
	}

	/**
	 * This method initializes tfNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextField();

			tfNote.setBounds(new Rectangle(129, 230, 148, 22));
		}
		return tfNote;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJpan(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("\u4fee\u6539");
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
			btnPrevious.setText("\u4e0a\u7b14");
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
			btnNext.setText("\u4e0b\u7b14");
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
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("\u5173\u95ed");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes btnComplex
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnComplex() {
		if (btnComplex == null) {
			btnComplex = new JButton();
			btnComplex.setVisible(false);
			btnComplex.setBounds(new Rectangle(258, 115, 19, 22));
			btnComplex.setText("...");
			btnComplex.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Complex obj = (Complex) CommonQuery.getInstance().getComplex();
					if (obj != null) {
						codeTs = obj;
						tfComplexCode.setText(codeTs.getCode());
						cbbUnit1.setSelectedItem(codeTs.getFirstUnit());
						FptAppItem img = (FptAppItem) tableModel.getCurrentRow();
						String modifyMark = img.getModifyMarkState();
						if (ModifyMarkState.ADDED.equals(modifyMark)) {
							tfGName.setText(codeTs.getName());
							tfGModel.setText(codeTs.getName());
						}
					}
				}
			});
		}
		return btnComplex;
	}

	/**
	 * This method initializes btnComplex1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnComplex1() {
		if (btnComplex1 == null) {
			btnComplex1 = new JButton();
			btnComplex1.setVisible(false);
			btnComplex1.setBounds(new Rectangle(527, 116, 19, 22));
			btnComplex1.setText("...");
			btnComplex1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Complex obj = (Complex) CommonQuery.getInstance().getComplex();
					if (obj != null) {
						standComplex = obj;
						tfStandComplexCode.setText(standComplex.getCode());
					}
				}
			});
		}
		return btnComplex1;
	}

	/**
	 * This method initializes cbbUnit1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUnit1() {
		if (cbbUnit1 == null) {
			cbbUnit1 = new JComboBox();
			cbbUnit1.setEnabled(false);
			cbbUnit1.setBounds(new Rectangle(398, 202, 148, 22));

		}
		return cbbUnit1;
	}

	/**
	 * This method initializes tfQty1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfQty1() {
		if (tfQty1 == null) {
			tfQty1 = new JCustomFormattedTextField();
			tfQty1.setFormatterFactory(getDefaultFormatterFactory1());
			tfQty1.setBounds(new Rectangle(129, 202, 148, 22));
			tfQty1.setText("0");
		}
		return tfQty1;
	}

	/**
	 * This method initializes cbbCurrency
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCurrency() {
		if (cbbCurrency == null) {
			cbbCurrency = new JComboBox();
			cbbCurrency.setBounds(new Rectangle(394, 248, 147, 21));
			cbbCurrency.setVisible(false);
		}
		return cbbCurrency;
	}

	/**
	 * This method initializes tfTotalPrice
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfTotalPrice() {
		if (tfTotalPrice == null) {
			tfTotalPrice = new JCustomFormattedTextField();
			tfTotalPrice.setBounds(new Rectangle(125, 273, 148, 22));
			tfTotalPrice.setFormatterFactory(getDefaultFormatterFactory1());
			tfTotalPrice.setEditable(true);
			tfTotalPrice.setVisible(false);
		}
		return tfTotalPrice;
	}

	/**
	 * @return the fptInOutFlag
	 */
	public String getFptInOutFlag() {
		return fptInOutFlag;
	}

	/**
	 * @param fptInOutFlag
	 *            the fptInOutFlag to set
	 */
	public void setFptInOutFlag(String fptInOutFlag) {
		this.fptInOutFlag = fptInOutFlag;
	}

	/**
	 * 计算报关数量
	 * 
	 */
	private void doTotalPrice() {
		double ptAmount = 0.0;
		double price = 0.0;
		if (tfQty.getValue() != null) {
			ptAmount = Double.valueOf(tfQty.getValue().toString().trim().equals("") == true ? "0.0" : tfQty.getValue()
					.toString());
		}
		if (tfUnitPrice.getValue() != null) {
			price = Double.valueOf(tfUnitPrice.getValue().toString().trim().equals("") == true ? "0.0" : tfUnitPrice
					.getValue().toString());
		}
		tfTotalPrice.setValue(ptAmount * price);
	}

	/**
	 * 计算报关数量
	 * 
	 */
	private void doAccountQty1() {
		Unit unit = (Unit) this.cbbUnit.getSelectedItem();
		Unit unit1 = (Unit) this.cbbUnit1.getSelectedItem();
		if (unit == null || unit1 == null) {
			return;
		}
		if (!unit.getCode().equals(unit1.getCode())) {
			return;
		}
		double ptAmount = 0.0;
		if (tfQty.getValue() != null) {
			ptAmount = Double.valueOf(tfQty.getValue().toString().trim().equals("") == true ? "0.0" : tfQty.getValue()
					.toString());
		}
		this.tfQty1.setValue(ptAmount);
	}

	/**
	 * This method initializes tfInOutNo
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfInOutNo() {
		if (tfInOutNo == null) {
			DecimalFormat decimalFormat1 = new DecimalFormat();
			decimalFormat1.setMaximumFractionDigits(8);
			decimalFormat1.setGroupingSize(8);
			NumberFormatter numberFormatter1 = new NumberFormatter();
			numberFormatter1.setFormat(decimalFormat1);
			DefaultFormatterFactory defaultFormatterFactory11 = new DefaultFormatterFactory();
			defaultFormatterFactory11.setDefaultFormatter(numberFormatter1);
			defaultFormatterFactory11.setDisplayFormatter(numberFormatter1);
			defaultFormatterFactory11.setEditFormatter(numberFormatter1);
			tfInOutNo = new JCustomFormattedTextField();
			tfInOutNo.setBounds(new Rectangle(131, 52, 129, 23));
			tfInOutNo.setFormatterFactory(defaultFormatterFactory11);
			tfInOutNo.setValue(null);
		}
		return tfInOutNo;
	}

	/**
	 * This method initializes btnInOutNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnInOutNo() {
		if (btnInOutNo == null) {
			btnInOutNo = new JButton();
			btnInOutNo.setBounds(new Rectangle(258, 52, 19, 22));
			btnInOutNo.setText("...");
			btnInOutNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					FptAppItem fptAppItem = FptQuery.getInstance().findFptAppItemsByOut(fptAppHead);
					if (fptAppItem != null) {
						tfInOutNo.setValue(fptAppItem.getListNo());
					}
				}
			});
		}
		return btnInOutNo;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
