/*
 * Created on 2005-3-18
 * 
 *刘民添加部分注释-- 修改时间: 2009-6-8
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.bcsinnermerge;

/**
 * 物料与报关对应
 * 
 * @author Administrator
 * 
 */
import java.awt.Color;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcs.bcsinnermerge.action.BcsInnerMergeAction;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcus.client.common.CommonQueryPage;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DocumentControlByGbkByte;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgBcsTenInnerMerge extends JDialogBase {

	private JPanel jContentPane = null;

	private JButton btnSave = null;

	private JButton btnExit = null;

	private JCustomFormattedTextField tfSeqNum = null;

	private JTextField tfName = null;

	private JTextField tfLegalUnit = null;

	private JComboBox cbbCountry = null;

	private JComboBox cbbCurr = null;

	private JComboBox cbbUnit = null;

	private JTextField tfSecondLegalUnit = null;

	private JTextField tfSpec = null;

	private JTextField tfComplex = null;

	private JButton btnComplex = null;

	private JFormattedTextField tfSecondLegalAmount = null;

	private JFormattedTextField tfUnitPrice = null;

	private JFormattedTextField tfUnitWeight = null;

	private JFormattedTextField tfLegalAmount = null;
	/** 内部归并接口 */
	private BcsInnerMergeAction bcsInnerMergeAction = null;
	/** 合同接口 */
	private ContractAction contractAction = null;

	private NumberFormatter numberFormatter = null;

	private JPanel jPanel = null;

	private JLabel lbSeqNum = null;

	private JLabel lbComplex = null;

	private JLabel lbSpec = null;

	private JLabel lbName = null;

	private JLabel lbUnitPrice = null;

	private JLabel lbUnit = null;

	private JLabel lbLegalUnit = null;

	private JLabel lbLegalAmount = null;

	private JLabel lbCountry = null;

	private JLabel lbCurr = null;

	private JLabel lbUnitWeight = null;

	private JLabel lbSecondLegalUnit = null;

	private JLabel lbSecondLegalAmount = null;

	private JLabel lbKg = null;

	private JLabel lbMachPrice = null;

	private JCustomFormattedTextField tfMachPrice = null;

	private JLabel lbIsMainImg = null;
	/** 来自十码内部归并新增 */
	private boolean isAddFromBcsTenInnerMerge = false;
	/** 十码内部归并 */
	private JTable tb = null;
	/** 浏览状态 */
	private int dataState = DataState.BROWSE;
	/** 物料类型 */
	private String materielType = MaterielType.FINISHED_PRODUCT; // @jve:decl-index=0:
	/** 存放报关商品资料 */
	private BcsTenInnerMerge bcsTenInnerMerge = new BcsTenInnerMerge(); // @jve:decl-index=0:
	/** 存放自用商品编码资料 */
	private Complex c = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;
	/** JBCUS系统所扩展的JTableModel 通过List数据源来显示&绑定数据 */
	private JTableListModel tableModel = null;
	/** 数据显示 */
	private boolean isCombinShow = false;
	/** 内部归并 */
	private boolean isInnerMerge = false;

	private JCheckBox cbIsMainImg = null;
	/** BCS参数设置 */
	private BcsParameterSet parameterSet = null;
	
	private FmBcsTenInnerMerge fm = null;
	/**是否取消编辑**/
	private boolean isCancel = false;
	
	public boolean isCombinShow() {
		return isCombinShow;
	}

	public void setCombinShow(boolean isCombinShow) {
		this.isCombinShow = isCombinShow;
	}

	public boolean isInnerMerge() {
		return isInnerMerge;
	}

	public void setInnerMerge(boolean isInnerMerge) {
		this.isInnerMerge = isInnerMerge;
	}

	
	public FmBcsTenInnerMerge getFm() {
		return fm;
	}

	public void setFm(FmBcsTenInnerMerge fm) {
		this.fm = fm;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgBcsTenInnerMerge() {
		super();
		initialize();
		bcsInnerMergeAction = (BcsInnerMergeAction) CommonVars
				.getApplicationContext().getBean("bcsInnerMergeAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		parameterSet = contractAction.findBcsParameterSet(new Request(
				CommonVars.getCurrUser(), true));
		initUIComponents();
		if (parameterSet.getIsControlLength() != null
				&& parameterSet.getIsControlLength()) {
			tfName.setDocument(new DocumentControlByGbkByte(parameterSet
					.getBytesLength() == null ? 30 : parameterSet
					.getBytesLength()));
			tfSpec.setDocument(new DocumentControlByGbkByte(parameterSet
					.getBytesLength() == null ? 30 : parameterSet
					.getBytesLength()));
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("物料与报关对应");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setSize(523, 391);
	}

	/**
	 * 设置控件的可见性
	 */

	public void setVisible(boolean isFalg) {
		if (isFalg) {
			initUIBcsTenInnerMerge();
			setState();
		}

		super.setVisible(isFalg);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getBtnExit(), null);
			jContentPane.add(getBtnSave(), null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getBtnPrevious(), null);
			jContentPane.add(getBtnNext(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("确定");
			btnSave.setBounds(new java.awt.Rectangle(96, 314, 65, 26));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validateData() == true) {
						//检查新增的商品是否重复
						if(checkBcsTenInnerMergeIsExist()){
							JOptionPane.showMessageDialog(DgBcsTenInnerMerge.this, "此商品在报关商品资料中已存在，不允许重复新增!", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						saveData();
						dataState = DataState.BROWSE;
						setState();
						if (isInnerMerge) {
							dispose();
						}
					}
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("取消");
			btnExit.setBounds(new java.awt.Rectangle(376, 315, 65, 26));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isCancel = true;
					DgBcsTenInnerMerge.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes tfSeqNum
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JCustomFormattedTextField();
			tfSeqNum.setEditable(false);
			tfSeqNum.setBounds(new java.awt.Rectangle(80, 16, 133, 23));
			tfSeqNum.addPropertyChangeListener("value",
					new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent evt) {
							setTenData();
						}

					});
		}
		return tfSeqNum;
	}

	private void setTextIsnull(){
		tfComplex.setText("");
		tfName.setText("");
		tfSpec.setText("");
		tfUnitPrice.setText("0");
		cbbCurr.setSelectedItem(null);
		cbbUnit.setSelectedItem(null);
		tfUnitWeight.setText("0");
		tfMachPrice.setText("0");
		cbbCountry.setSelectedItem(null);
		tfLegalAmount.setText("0");
		tfSecondLegalAmount.setText("0");
		cbIsMainImg.setSelected(false);
	}
	private void setData(boolean isFalse) {
		if (tfSeqNum.getText().trim().equals(
				bcsTenInnerMerge.getSeqNum().toString())) {
			return;
		}
		tfName.setEditable(isFalse);
		btnComplex.setEnabled(isFalse);
		tfSpec.setEditable(isFalse);
//		tfSeqNum.setEditable(isFalse);
		cbbUnit.setEnabled(isFalse);
	}

	private void setTenData() {
		setData(true);
		List list = null;
		System.out.println("=isAddFromBcsTenInnerMerge="+isAddFromBcsTenInnerMerge);
		if (isAddFromBcsTenInnerMerge == true) {
			list = bcsInnerMergeAction.findBcsTenInnerMergeDataBySeqNum(
					new Request(CommonVars.getCurrUser()), Integer
							.valueOf(tfSeqNum.getText()), this
							.getMaterielType());
			if (list != null && list.size() > 0) {
				setData(false);
				bcsTenInnerMerge = (BcsTenInnerMerge) list.get(0);
			} else {
				setTextIsnull();
			}
		} else {
			list = bcsInnerMergeAction.findBcsInnerMergeDataByTenSeqNum(
					new Request(CommonVars.getCurrUser()), Integer
							.valueOf(tfSeqNum.getText()), this
							.getMaterielType());
			if (list != null && list.size() > 0) {
				setData(false);
				BcsInnerMerge bcsInnerMerge = (BcsInnerMerge) list.get(0);
				bcsTenInnerMerge = bcsInnerMerge.getBcsTenInnerMerge();
			} else {
				setTextIsnull();
			}
		}
       showData();
	}

	/**
	 * This method initializes tfName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(new java.awt.Rectangle(80, 45, 133, 23));

		}
		return tfName;
	}

	/**
	 * This method initializes tfLegalUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLegalUnit() {
		if (tfLegalUnit == null) {
			tfLegalUnit = new JTextField();
			tfLegalUnit.setEditable(false);
			tfLegalUnit.setBounds(new java.awt.Rectangle(80, 160, 133, 23));
		}
		return tfLegalUnit;
	}

	/**
	 * This method initializes cbbCountry
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCountry() {
		if (cbbCountry == null) {
			cbbCountry = new JComboBox();
			cbbCountry.setBounds(new java.awt.Rectangle(294, 132, 133, 23));
		}
		return cbbCountry;
	}

	/**
	 * This method initializes cbbCurr
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCurr() {
		if (cbbCurr == null) {
			cbbCurr = new JComboBox();
			cbbCurr.setBounds(new java.awt.Rectangle(294, 74, 133, 23));
		}
		return cbbCurr;
	}

	/**
	 * This method initializes cbbUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUnit() {
		if (cbbUnit == null) {
			cbbUnit = new JComboBox();
			cbbUnit.setBounds(new java.awt.Rectangle(80, 103, 133, 23));
		}
		return cbbUnit;
	}

	/**
	 * This method initializes tfSecondLegalUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSecondLegalUnit() {
		if (tfSecondLegalUnit == null) {
			tfSecondLegalUnit = new JTextField();
			tfSecondLegalUnit.setEditable(false);
			tfSecondLegalUnit.setBounds(new java.awt.Rectangle(294, 160, 133,
					23));
		}
		return tfSecondLegalUnit;
	}

	/**
	 * This method initializes tfSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSpec() {
		if (tfSpec == null) {
			tfSpec = new JTextField();
			tfSpec.setBounds(new java.awt.Rectangle(294, 45, 133, 23));
		}
		return tfSpec;
	}

	/**
	 * This method initializes tfComplex
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplex() {
		if (tfComplex == null) {
			tfComplex = new JTextField();
			tfComplex.setEditable(false);
			tfComplex.setBounds(new java.awt.Rectangle(294, 16, 111, 23));
		}
		return tfComplex;
	}

	/**
	 * This method initializes btnComplex
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnComplex() {
		if (btnComplex == null) {
			btnComplex = new JButton();
			btnComplex.setText("...");
			btnComplex.setBounds(new java.awt.Rectangle(405, 16, 22, 23));
			btnComplex.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = CommonQueryPage.getInstance().getComplex();
					if (list == null || list.size() <= 0) {
						return;
					}
					c = (Complex) list.get(0);
					tfComplex.setText(c.getCode());
					tfName.setText(c.getName());
					tfLegalUnit.setText(c.getFirstUnit() == null ? "" : c
							.getFirstUnit().getName());
					tfSecondLegalUnit.setText(c.getSecondUnit() == null ? ""
							: c.getSecondUnit().getName());
				}
			});
		}
		return btnComplex;
	}

	/**
	 * This method initializes tfSecondLegalAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfSecondLegalAmount() {
		if (tfSecondLegalAmount == null) {
			tfSecondLegalAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfSecondLegalAmount.setBounds(new java.awt.Rectangle(294, 191, 133,
					23));
			tfSecondLegalAmount.setValue(new Double(0));
		}
		return tfSecondLegalAmount;
	}

	/**
	 * This method initializes tfUnitPrice
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitPrice() {
		if (tfUnitPrice == null) {
			tfUnitPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitPrice.setBounds(new java.awt.Rectangle(80, 74, 133, 23));
			tfUnitPrice.setValue(new Double(0));
		}
		return tfUnitPrice;
	}

	/**
	 * This method initializes tfUnitWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitWeight() {
		if (tfUnitWeight == null) {
			tfUnitWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitWeight.setBounds(new java.awt.Rectangle(294, 103, 134, 23));
			tfUnitWeight.setValue(new Double(0));
		}
		return tfUnitWeight;
	}

	/**
	 * This method initializes tfLegalAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfLegalAmount() {
		if (tfLegalAmount == null) {
			tfLegalAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfLegalAmount.setBounds(new java.awt.Rectangle(80, 191, 133, 23));
			tfLegalAmount.setValue(new Double(0));
		}
		return tfLegalAmount;
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
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			lbIsMainImg = new JLabel();
			lbIsMainImg.setBounds(new java.awt.Rectangle(7, 223, 71, 22));
			lbIsMainImg.setHorizontalTextPosition(SwingConstants.RIGHT);
			lbIsMainImg.setText("主料/辅料");
			lbIsMainImg.setHorizontalAlignment(SwingConstants.RIGHT);
			lbMachPrice = new JLabel();
			lbMachPrice.setBounds(new java.awt.Rectangle(11, 132, 67, 24));
			lbMachPrice
					.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			lbMachPrice.setText("加工费单价");
			lbKg = new JLabel();
			lbKg.setBounds(new Rectangle(429, 103, 35, 23));
			lbKg.setText("(\u5343\u514b)");
			lbSecondLegalAmount = new JLabel();
			lbSecondLegalAmount.setBounds(new java.awt.Rectangle(220, 191, 73,
					23));
			lbSecondLegalAmount.setHorizontalTextPosition(SwingConstants.RIGHT);
			lbSecondLegalAmount.setText("\u7b2c\u4e8c\u6cd5\u5b9a\u6570\u91cf");
			lbSecondLegalAmount.setHorizontalAlignment(SwingConstants.RIGHT);
			lbSecondLegalUnit = new JLabel();
			lbSecondLegalUnit
					.setBounds(new java.awt.Rectangle(221, 160, 72, 23));
			lbSecondLegalUnit.setHorizontalTextPosition(SwingConstants.RIGHT);
			lbSecondLegalUnit.setText("\u7b2c\u4e8c\u6cd5\u5b9a\u5355\u4f4d");
			lbSecondLegalUnit.setHorizontalAlignment(SwingConstants.RIGHT);
			lbUnitWeight = new JLabel();
			lbUnitWeight.setBounds(new java.awt.Rectangle(235, 103, 58, 23));
			lbUnitWeight.setHorizontalTextPosition(SwingConstants.RIGHT);
			lbUnitWeight.setText("\u5355\u4f4d\u91cd\u91cf");
			lbUnitWeight.setHorizontalAlignment(SwingConstants.RIGHT);
			lbCurr = new JLabel();
//			lbCurr.setForeground(Color.BLUE);
			lbCurr.setBounds(new java.awt.Rectangle(232, 74, 61, 23));
			lbCurr.setHorizontalTextPosition(SwingConstants.RIGHT);
			lbCurr.setText("\u5e01\u5236");
			lbCurr.setHorizontalAlignment(SwingConstants.RIGHT);
			lbCountry = new JLabel();

			lbCountry.setForeground(Color.BLUE);
			lbCountry.setBounds(new java.awt.Rectangle(220, 132, 73, 23));
			lbCountry.setHorizontalTextPosition(SwingConstants.RIGHT);
			lbCountry.setText("\u4ea7\u9500\u56fd");
			lbCountry.setHorizontalAlignment(SwingConstants.RIGHT);
			lbLegalAmount = new JLabel();
			lbLegalAmount.setBounds(new java.awt.Rectangle(5, 191, 73, 23));
			lbLegalAmount.setHorizontalTextPosition(SwingConstants.RIGHT);
			lbLegalAmount.setText("\u7b2c\u4e00\u6cd5\u5b9a\u6570\u91cf");
			lbLegalAmount.setHorizontalAlignment(SwingConstants.RIGHT);
			lbLegalUnit = new JLabel();
			lbLegalUnit.setBounds(new java.awt.Rectangle(5, 160, 73, 23));
			lbLegalUnit.setHorizontalTextPosition(SwingConstants.RIGHT);
			lbLegalUnit.setText("\u7b2c\u4e00\u6cd5\u5b9a\u5355\u4f4d");
			lbLegalUnit.setHorizontalAlignment(SwingConstants.RIGHT);
			lbUnit = new JLabel();
			lbUnit.setForeground(Color.BLUE);
			lbUnit.setBounds(new java.awt.Rectangle(5, 103, 73, 23));
			lbUnit.setHorizontalTextPosition(SwingConstants.RIGHT);
			lbUnit.setText("\u5355\u4f4d");
			lbUnit.setHorizontalAlignment(SwingConstants.RIGHT);
			lbUnitPrice = new JLabel();
			lbUnitPrice.setBounds(new java.awt.Rectangle(5, 74, 73, 23));
			lbUnitPrice.setHorizontalTextPosition(SwingConstants.RIGHT);
			lbUnitPrice.setText("\u5355\u4ef7");
			lbUnitPrice.setHorizontalAlignment(SwingConstants.RIGHT);
			lbName = new JLabel();
			lbName.setForeground(Color.BLUE);
			lbName.setBounds(new java.awt.Rectangle(5, 45, 73, 23));
			lbName.setHorizontalTextPosition(SwingConstants.RIGHT);
			lbName.setText("\u5546\u54c1\u540d\u79f0");
			lbName.setHorizontalAlignment(SwingConstants.RIGHT);
			lbSpec = new JLabel();
			lbSpec.setBounds(new java.awt.Rectangle(229, 45, 64, 23));
			lbSpec.setHorizontalTextPosition(SwingConstants.RIGHT);
			lbSpec.setText("\u578b\u53f7\u89c4\u683c");
			lbSpec.setHorizontalAlignment(SwingConstants.RIGHT);
			lbComplex = new JLabel();
			lbComplex.setForeground(Color.BLUE);
			lbComplex.setBounds(new java.awt.Rectangle(230, 16, 63, 23));
			lbComplex.setHorizontalTextPosition(SwingConstants.RIGHT);
			lbComplex.setText("\u5546\u54c1\u7f16\u7801");
			lbComplex.setHorizontalAlignment(SwingConstants.RIGHT);
			lbSeqNum = new JLabel();
			lbSeqNum.setBounds(new java.awt.Rectangle(5, 16, 73, 23));
			lbSeqNum.setHorizontalTextPosition(SwingConstants.RIGHT);
			lbSeqNum.setText("归并序号");
			lbSeqNum.setHorizontalAlignment(SwingConstants.RIGHT);
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new java.awt.Rectangle(24, 26, 465, 275));
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel.add(getTfSeqNum(), null);
			jPanel.add(getTfComplex(), null);
			jPanel.add(getBtnComplex(), null);
			jPanel.add(getTfName(), null);
			jPanel.add(getTfSpec(), null);
			jPanel.add(getTfUnitPrice(), null);
			jPanel.add(getCbbUnit(), null);
			jPanel.add(getCbbCurr(), null);
			jPanel.add(getTfUnitWeight(), null);
			jPanel.add(getTfLegalUnit(), null);
			jPanel.add(getTfSecondLegalUnit(), null);
			jPanel.add(getTfLegalAmount(), null);
			jPanel.add(getTfSecondLegalAmount(), null);
			jPanel.add(getCbbCountry(), null);
			jPanel.add(lbSeqNum, null);
			jPanel.add(lbComplex, null);
			jPanel.add(lbSpec, null);
			jPanel.add(lbName, null);
			jPanel.add(lbUnitPrice, null);
			jPanel.add(lbUnit, null);
			jPanel.add(lbLegalUnit, null);
			jPanel.add(lbLegalAmount, null);
			jPanel.add(lbCountry, null);
			jPanel.add(lbCurr, null);
			jPanel.add(lbUnitWeight, null);
			jPanel.add(lbSecondLegalUnit, null);
			jPanel.add(lbSecondLegalAmount, null);
			jPanel.add(lbKg, null);
			jPanel.add(lbMachPrice, null);
			jPanel.add(getTfMachPrice(), null);
			jPanel.add(lbIsMainImg, null);
			jPanel.add(getCbIsMainImg(), null);
		}
		return jPanel;
	}

	public void setTable(JTable tb) {
		this.tb = tb;
		this.tableModel = (JTableListModel) tb.getModel();
	}

	public BcsInnerMergeAction getBcsInnerMergeAction() {
		return bcsInnerMergeAction;
	}

	public void setBcsInnerMergeAction(BcsInnerMergeAction bcsInnerMergeAction) {
		this.bcsInnerMergeAction = bcsInnerMergeAction;
	}

	public JTable getTb() {
		return tb;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public String getMaterielType() {
		return materielType;
	}

	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}

	/**
	 * This method initializes tfMachPrice
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfMachPrice() {
		if (tfMachPrice == null) {
			DefaultFormatterFactory defaultFormatterFactory1 = new DefaultFormatterFactory();
			defaultFormatterFactory1.setDisplayFormatter(new NumberFormatter());
			defaultFormatterFactory1.setDefaultFormatter(new NumberFormatter());
			defaultFormatterFactory1.setEditFormatter(new NumberFormatter());
			tfMachPrice = new JCustomFormattedTextField();
			tfMachPrice.setBounds(new java.awt.Rectangle(80, 132, 133, 24));
			tfMachPrice.setValue(new Double(0));
			tfMachPrice.setFormatterFactory(defaultFormatterFactory1);
		}
		return tfMachPrice;
	}

	public boolean isAddFromBcsTenInnerMerge() {
		return isAddFromBcsTenInnerMerge;
	}

	public void setAddFromBcsTenInnerMerge(boolean isInnerMergeEdit) {
		this.isAddFromBcsTenInnerMerge = isInnerMergeEdit;
	}

	public BcsTenInnerMerge getBcsTenInnerMerge() {
		return bcsTenInnerMerge;
	}

	public void setBcsTenInnerMerge(BcsTenInnerMerge bcsTenInnerMerge) {
		this.bcsTenInnerMerge = bcsTenInnerMerge;
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		int unitPrice = parameterSet.getPriceBit() == null ? 5 : parameterSet
				.getPriceBit();

		int countBit = parameterSet.getCountBit() == null ? 5 : parameterSet
				.getCountBit();

		// 初始化币制
		this.cbbCurr.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.cbbCurr.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCurr);
		this.cbbCurr.setSelectedItem(null);
		// 初始化国
		this.cbbCountry.setModel(CustomBaseModel.getInstance()
				.getCountryModel());
		this.cbbCountry.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCountry);
		this.cbbCountry.setSelectedItem(null);
		// 初始化单位
		this.cbbUnit.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbUnit.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbUnit);
		this.cbbUnit.setSelectedItem(null);

		// 这里是控制焦点的顺序，以方便键盘的输！
		List<Object> components = new ArrayList<Object>();
		components.add(this.tfName);
		components.add(null);
		components.add(this.cbIsMainImg);
		components.add(this.btnSave);
		components.add(this.btnNext);
		this.setComponentFocusList(components);

		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitPrice,
				unitPrice);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitWeight,
				countBit);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfMachPrice,
				unitPrice);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfLegalAmount,
				countBit);
		CustomFormattedTextFieldUtils.setFormatterFactory(
				this.tfSecondLegalAmount, countBit);
	}

	/**
	 * 初始化十码内部归并
	 * 
	 */
	private void initUIBcsTenInnerMerge() {
		if (dataState == DataState.ADD) {// 新增10位归并
			Object object = ((JTableListModel) tb.getModel()).getCurrentRow();
			if (object == null) {
				return;
			}
			BcsInnerMerge bcsInnerMerge = (BcsInnerMerge) object;
			if (isAddFromBcsTenInnerMerge == false) { // 来自新增十码
				Materiel m = bcsInnerMerge.getMateriel();
				if (m != null) {
					int maxTenInnerMergeNo = this.bcsInnerMergeAction
							.getMaxTenBcsInnerMergeNo(new Request(CommonVars
									.getCurrUser()), materielType);
					bcsTenInnerMerge.setSeqNum(++maxTenInnerMergeNo);
					bcsTenInnerMerge.setComplex(m.getComplex());
				}
			}
		} else if (dataState == DataState.EDIT) {// 修改10位归并
			Object object = ((JTableListModel) tb.getModel()).getCurrentRow();
			if (object == null) {
				return;
			}
			if (object instanceof BcsInnerMerge) {// 是在BCS内部归并中修改
				BcsInnerMerge tempB = (BcsInnerMerge) object;
				this.bcsTenInnerMerge = tempB.getBcsTenInnerMerge();

			} else if (object instanceof BcsTenInnerMerge) {// 是在BCS报关商品十码归并中修改
				bcsTenInnerMerge = (BcsTenInnerMerge) object;

			}
		}
		showData();
	}

	/**
	 * 显示数据
	 * 
	 */
	private void showData() {
		if (this.tableModel.getCurrentRow() instanceof BcsTenInnerMerge) {
			bcsTenInnerMerge = (BcsTenInnerMerge) this.tableModel
					.getCurrentRow();
		}
		if (this.bcsTenInnerMerge == null) {
			bcsTenInnerMerge = new BcsTenInnerMerge();
			return;
		}
		if (this.bcsTenInnerMerge.getSeqNum() != null) {
			this.tfSeqNum.setText(String.valueOf(this.bcsTenInnerMerge
					.getSeqNum()));
		}
		if (this.bcsTenInnerMerge.getName() != null) {
			tfName.setText(this.bcsTenInnerMerge.getName());
		} else {
			tfName.setText("");
		}
		if (this.bcsTenInnerMerge.getSpec() != null) {
			tfSpec.setText(this.bcsTenInnerMerge.getSpec());
		} else {
			tfSpec.setText("");
		}
		if (this.bcsTenInnerMerge.getComplex() != null) {
			c = bcsTenInnerMerge.getComplex();
			tfComplex.setText(c.getCode());
			tfLegalUnit.setText(c.getFirstUnit() == null ? "" : c
					.getFirstUnit().getName());
			tfSecondLegalUnit.setText(c.getSecondUnit() == null ? "" : c
					.getSecondUnit().getName());
		} else {
			tfComplex.setText("");
			tfLegalUnit.setText("");
			tfSecondLegalUnit.setText("");
		}
		if (this.bcsTenInnerMerge.getPrice() != null) {
			tfUnitPrice.setValue(this.bcsTenInnerMerge.getPrice());
		} else {
			tfUnitPrice.setValue(new Double(0));
		}
		if (this.bcsTenInnerMerge.getMachPrice() != null) {
			tfMachPrice.setValue(this.bcsTenInnerMerge.getMachPrice());
		} else {
			tfMachPrice.setValue(new Double(0));
		}
		if (this.bcsTenInnerMerge.getUnitWeight() != null) {
			tfUnitWeight.setValue(bcsTenInnerMerge.getUnitWeight());
		} else {
			tfUnitWeight.setValue(new Double(0));
		}
		cbbCurr.setSelectedItem(this.bcsTenInnerMerge.getCurr());
		cbbUnit.setSelectedItem(bcsTenInnerMerge.getComUnit());
		cbbCountry.setSelectedItem(this.bcsTenInnerMerge.getCountry());
		if (this.bcsTenInnerMerge.getLegalAmount() != null) {
			this.tfLegalAmount.setValue(this.bcsTenInnerMerge.getLegalAmount());
		} else {
			this.tfLegalAmount.setValue(new Double(0));
		}
		if (this.bcsTenInnerMerge.getSecondLegalAmount() != null) {
			this.tfSecondLegalAmount.setValue(this.bcsTenInnerMerge
					.getSecondLegalAmount());
		} else {
			this.tfSecondLegalAmount.setValue(new Double(0));
		}
		this.cbIsMainImg
				.setSelected(bcsTenInnerMerge.getIsMainImg() == null ? false
						: bcsTenInnerMerge.getIsMainImg());
	}

	/**
	 * 填充数据
	 * 
	 */
	private void fillData() {
		int seqNum = Integer.parseInt(tfSeqNum.getText().trim());
		this.bcsTenInnerMerge.setSeqNum(seqNum);
		this.bcsTenInnerMerge.setComplex(c);
		this.bcsTenInnerMerge.setCompany(CommonVars.getCurrUser().getCompany());
		this.bcsTenInnerMerge.setName(tfName.getText());
		this.bcsTenInnerMerge.setSpec(tfSpec.getText());
		this.bcsTenInnerMerge.setCurr((Curr) cbbCurr.getSelectedItem());
		bcsTenInnerMerge.setComUnit((Unit) cbbUnit.getSelectedItem());
		this.bcsTenInnerMerge
				.setCountry((Country) cbbCountry.getSelectedItem());

		if (this.tfUnitWeight.getValue() != null) {
			this.bcsTenInnerMerge.setUnitWeight(Double
					.valueOf(this.tfUnitWeight.getValue().toString()));
		} else {
			this.bcsTenInnerMerge.setUnitWeight(null);
		}
		if (this.tfUnitPrice.getValue() != null) {
			this.bcsTenInnerMerge.setPrice(Double.valueOf(this.tfUnitPrice
					.getValue().toString()));
		} else {
			this.bcsTenInnerMerge.setPrice(null);
		}
		if (this.tfLegalAmount.getValue() != null) {
			this.bcsTenInnerMerge.setLegalAmount(Double
					.valueOf(this.tfLegalAmount.getValue().toString()));
		} else {
			this.bcsTenInnerMerge.setLegalAmount(null);
		}
		if (this.tfSecondLegalAmount.getValue() != null) {
			this.bcsTenInnerMerge.setSecondLegalAmount(Double
					.valueOf(this.tfSecondLegalAmount.getValue().toString()));
		} else {
			this.bcsTenInnerMerge.setSecondLegalAmount(null);
		}
		if (this.tfMachPrice.getValue() != null) {
			bcsTenInnerMerge.setMachPrice(Double.valueOf(this.tfMachPrice
					.getValue().toString()));
		} else {
			bcsTenInnerMerge.setMachPrice(null);
		}
		if (bcsTenInnerMerge.getScmCoi() == null
				|| "".equals(bcsTenInnerMerge.getScmCoi())) {
			bcsTenInnerMerge.setScmCoi(this.materielType);
		}
		// 主料辅料
		bcsTenInnerMerge.setIsMainImg(this.cbIsMainImg.isSelected());
	}

	/**
	 * 保存数据
	 * 
	 */
	private void saveData() {

		if (dataState == DataState.ADD) {// 新增10位归并
			if (isAddFromBcsTenInnerMerge == false) { // 来自新增十码
				int maxTenInnerMergeNo = this.bcsInnerMergeAction
						.getMaxTenBcsInnerMergeNo(new Request(CommonVars
								.getCurrUser()), materielType);
				bcsTenInnerMerge.setSeqNum(maxTenInnerMergeNo + 1);
			}
			this.fillData();
			List currentRows = ((JTableListModel) tb.getModel())
					.getCurrentRows();

			long beginTime = System.currentTimeMillis();

			List ls = this.bcsInnerMergeAction.saveBcsInnerMerge(new Request(
					CommonVars.getCurrUser()), currentRows, bcsTenInnerMerge);

			long endTime = System.currentTimeMillis();
			System.out.println("-----Query time :" + (endTime - beginTime)
					/ 1000.0);

			refreshTable(ls);

		} else if (dataState == DataState.EDIT) {// 修改10位归并
			this.updateBcsTenInnerMerge();
		}
	}

	/**
	 * 修改十位商品编码( 当 dataState == DataState.EDIT )
	 * 
	 */
	private void updateBcsTenInnerMerge() {
		Object object = ((JTableListModel) tb.getModel()).getCurrentRow();
		if (object == null) {
			return;
		}
		if (object instanceof BcsInnerMerge) {// 是在BCS内部归并中修改
			BcsInnerMerge tempB = (BcsInnerMerge) object;
			bcsTenInnerMerge = tempB.getBcsTenInnerMerge();
			List currentRows = ((JTableListModel) tb.getModel())
					.getCurrentRows();
			this.fillData();
			List ls = this.bcsInnerMergeAction.updateBcsInnerMerge(new Request(
					CommonVars.getCurrUser()), currentRows, bcsTenInnerMerge);
			refreshTable(ls);

		} else if (object instanceof BcsTenInnerMerge) {// 是在BCS十码内部归并中修改
			bcsTenInnerMerge = (BcsTenInnerMerge) object;
			this.fillData();
			bcsTenInnerMerge = this.bcsInnerMergeAction.saveBcsTenInnerMerge(
					new Request(CommonVars.getCurrUser()), bcsTenInnerMerge);
			((JTableListModel) tb.getModel()).updateRow(bcsTenInnerMerge);
			
		}

	}
	/**
	 * 检查新增的商品是否已经在商品资料中已经存在。Key = 编码+名称+规格+单位
	 * @return
	 */
   private boolean checkBcsTenInnerMergeIsExist() {
		String code = tfComplex.getText().trim();
		String name = tfName.getText().trim();
		String spec = tfSpec.getText().trim();
		String unit = ((Unit) cbbUnit.getSelectedItem()).getName();
		String key = code + name + spec + unit;
		// 已存在的报关商品资料
		List<BcsTenInnerMerge> listBcsTenInnerMerge = bcsInnerMergeAction
				.findBcsTenInnerMerge(new Request(CommonVars.getCurrUser()),
						materielType);
		for (int i = 0; i < listBcsTenInnerMerge.size(); i++) {
			BcsTenInnerMerge obj = listBcsTenInnerMerge.get(i);
			String tempCode = obj.getComplex().getCode();
			String tempName = obj.getName() == null ? "": obj.getName();
			String tempSpec = obj.getSpec() == null ? "": obj.getSpec();
			String tempUnit = obj.getComUnit() == null ? "": obj.getComUnit().getName();
			String tempKey = tempCode+tempName+tempSpec+tempUnit;
			if(tempKey.equals(key)){
				return true;
			}
		}
		return false;
	}
	
	//
	// /**
	// * 验证数据是否被引用
	// *
	// * @param bcsTenInnerMerge
	// * @return
	// */
	// private boolean isValidate(BcsTenInnerMerge bcsTenInnerMerge) {
	// List tempList = bcsInnerMergeAction.findBcsInnerMergeInContract(
	// new Request(CommonVars.getCurrUser()), bcsTenInnerMerge);
	// if (MaterielType.MATERIEL.equals(materielType)) {
	// String info = "";
	// for (int i = 0; i < tempList.size(); i++) {
	// ContractImg c = (ContractImg) tempList.get(i);
	// // if (DeclareState.PROCESS_EXE.equals(c.getDeclareState())) {
	// // info += (c.getContract().getImpContractNo() + " 合同序号 "
	// // + c.getSeqNum() + " 料件引用 !!" + "\n");
	// // }
	// }
	// if (!info.equals("")) {
	// JOptionPane.showMessageDialog(this, "选中的行中有数据在合同中被\n" + info
	// + "不能修改!!", "提示", JOptionPane.INFORMATION_MESSAGE);
	// return false;
	// }
	// } else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
	// String info = "";
	// for (int i = 0; i < tempList.size(); i++) {
	// ContractExg c = (ContractExg) tempList.get(i);
	// // if (DeclareState.PROCESS_EXE.equals(c.getDeclareState())) {
	// // info += (c.getContract().getExpContractNo() + " 合同序号 "
	// // + c.getSeqNum() + " 成品引用 !!" + "\n");
	// // }
	// }
	// if (!info.equals("")) {
	// JOptionPane.showMessageDialog(this, "选中的行中有数据在合同中被\n" + info
	// + "不能修改!!", "提示", JOptionPane.INFORMATION_MESSAGE);
	// return false;
	// }
	// }
	// return true;
	//
	// }

	/**
	 * 刷新表
	 * 
	 * @param ls
	 */
	private void refreshTable(List ls) {
		tableModel.updateRows(ls);
		if (isCombinShow) {
			((MultiSpanCellTable) tb).combineRows(6, new int[] { 6, 8, 9, 10,
					11, 12 });
		}
	}

	/**
	 * 验证数据
	 * 
	 * @return
	 */
	private boolean validateData() {
		if (this.tfSeqNum.getText() == null
				|| "".equals(this.tfSeqNum.getText().trim())) {
			JOptionPane.showMessageDialog(this, "归并序号不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		} else {
			try {
				Integer.valueOf(this.tfSeqNum.getText().trim());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "归并序号不是有效的整型!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}

		if (Integer.valueOf(this.tfUnitWeight.getText().trim())<0) {
			JOptionPane.showMessageDialog(this, "单重不能为负数!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		if (this.tfName.getText() == null
				|| "".equals(this.tfName.getText().trim())) {

			JOptionPane.showMessageDialog(this, "商品名称不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;

		}
		if (this.c == null) {

			JOptionPane.showMessageDialog(this, "商品编码不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;

		}
		if (this.cbbCountry.getSelectedItem() == null) {

			JOptionPane.showMessageDialog(this, "产销国不能为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;

		}
//		if (this.cbbCurr.getSelectedItem() == null) {
//
//			JOptionPane.showMessageDialog(this, "币制不可为空!!", "提示",
//					JOptionPane.INFORMATION_MESSAGE);
//			return false;
//
//		}
		if (this.cbbUnit.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "单位不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;

		}
		return true;
	}

	/**
	 * This method initializes btnPrevious
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setBounds(new java.awt.Rectangle(182, 315, 76, 26));
			btnPrevious.setMnemonic(java.awt.event.KeyEvent.VK_UP);
			btnPrevious.setText("上笔↑");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validateData() == true) {
						saveData();
					} else
						return;
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
			btnNext.setBounds(new java.awt.Rectangle(282, 315, 73, 26));
			btnNext.setMnemonic(java.awt.event.KeyEvent.VK_DOWN);
			btnNext.setText("下笔↓");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				/*	if (validateData() == true) {
						saveData();
					} else
						return;
					System.out.println(((BcsTenInnerMerge) tableModel
							.getCurrentRow()).getName());*/
					if (!tableModel.nextRow()) {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(false);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					System.out.println(((BcsTenInnerMerge) tableModel
							.getCurrentRow()).getName());
					showData();
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnNext;
	}

	/**
	 * 设置控件的状态
	 */

	private void setState() {
//		this.tfSeqNum.setEditable(this.dataState == DataState.EDIT);
		// this.tfComplex.setEditable(this.dataState != DataState.BROWSE);
		this.tfName.setEditable(this.dataState != DataState.BROWSE);
		this.tfSpec.setEditable(this.dataState != DataState.BROWSE);
		this.tfUnitPrice.setEditable(this.dataState != DataState.BROWSE);
		this.cbbCurr.setEnabled(this.dataState != DataState.BROWSE);
		this.cbbUnit.setEnabled(this.dataState != DataState.BROWSE);
		this.tfUnitWeight.setEditable(this.dataState != DataState.BROWSE);
		this.tfMachPrice.setEditable(this.dataState != DataState.BROWSE);
		this.cbbCountry.setEnabled(this.dataState != DataState.BROWSE);
		this.tfLegalAmount.setEditable(this.dataState != DataState.BROWSE);
		this.tfSecondLegalAmount.setEditable(this.dataState != DataState.BROWSE);
//		if (!"".equals(tfSecondLegalUnit.getText().trim())) {
//			this.tfSecondLegalAmount.setEditable(this.dataState != DataState.BROWSE);
//		} else {
//			this.tfSecondLegalAmount.setEditable(this.dataState == DataState.BROWSE);			
//		}
		this.cbIsMainImg.setEnabled(this.dataState != DataState.BROWSE);
		this.btnSave.setEnabled(this.dataState != DataState.BROWSE);
		this.btnPrevious.setVisible(!isInnerMerge);
		this.btnNext.setVisible(!isInnerMerge);
		this.btnPrevious.setEnabled(tableModel.hasPreviousRow());
		this.btnNext.setEnabled(tableModel.hasNextRow());
	}

	/**
	 * This method initializes cbIsMainImg
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsMainImg() {
		if (cbIsMainImg == null) {
			cbIsMainImg = new JCheckBox();
			cbIsMainImg.setBounds(new Rectangle(81, 225, 103, 21));
			cbIsMainImg.setText("是否是主料");
		}
		return cbIsMainImg;
	}

	public boolean getIsCancel() {
		return isCancel;
	}
}
