/*
 * Created on 2005-3-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.client.blsinnermerge;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

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

import com.bestway.bcus.client.common.CommonQueryPage;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bls.action.BlsInnerMergeAction;
import com.bestway.bls.entity.BlsInnerMerge;
import com.bestway.bls.entity.BlsTenInnerMerge;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgBlsTenInnerMerge extends JDialogBase {

	private JPanel jContentPane = null;

	private JButton btnSave = null;

	private JButton btnExit = null;

	private JTextField tfSeqNum = null;

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

	private BlsInnerMergeAction blsInnerMergeAction = null;

//	private ContractAction contractAction = null;

	private DefaultFormatterFactory defaultFormatterFactory = null;

	private NumberFormatter numberFormatter = null;

	private JPanel jPanel = null;

	private JLabel jLabel15 = null;

	private JLabel jLabel = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel13 = null;

	private JLabel jLabel12 = null;

	private JCustomFormattedTextField tfMachPrice = null;

	private JLabel jLabel14 = null;

	private boolean isAddFromBlsTenInnerMerge = false; // 来自十码内部归并新增

	private JTable tb = null;

	private int dataState = DataState.BROWSE;

//	private String materielType = MaterielType.FINISHED_PRODUCT;

	private BlsTenInnerMerge blsTenInnerMerge = new BlsTenInnerMerge();

	private Complex c = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JTableListModel tableModel = null;

	private boolean isCombinShow = false;

	private boolean isInnerMerge = false;

	private JCheckBox cbIsMainImg = null;

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

	/**
	 * This method initializes
	 * 
	 */
	public DgBlsTenInnerMerge() {
		super();
		initialize();
		blsInnerMergeAction = (BlsInnerMergeAction) CommonVars
				.getApplicationContext().getBean("blsInnerMergeAction");
//		contractAction = (ContractAction) CommonVars.getApplicationContext()
//				.getBean("contractAction");
		initUIComponents();
//		BlsParameterSet parameterSet = contractAction
//				.findBlsParameterSet(new Request(CommonVars.getCurrUser(), true));
//		if (parameterSet.getIsControlLength() != null
//				&& parameterSet.getIsControlLength()) {
//			tfName.setDocument(new DocumentControlByGbkByte(parameterSet
//					.getBytesLength() == null? 30:parameterSet
//							.getBytesLength()));
//			tfSpec.setDocument(new DocumentControlByGbkByte(parameterSet
//					.getBytesLength() == null? 30:parameterSet
//							.getBytesLength()));
//		}
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

	public void setVisible(boolean isFalg) {
		if (isFalg) {
			initUIBlsTenInnerMerge();
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
						saveData();
						// DgBlsTenInnerMerge.this.dispose();
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
					DgBlsTenInnerMerge.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes tfCredenceNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setEditable(false);
			tfSeqNum.setBounds(new java.awt.Rectangle(80, 16, 133, 23));
		}
		return tfSeqNum;
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
			tfComplex.setBackground(java.awt.Color.white);
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
			jLabel14 = new JLabel();
			jLabel14.setBounds(new java.awt.Rectangle(7, 223, 71, 22));
			jLabel14.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel14.setText("主料/辅料");
			jLabel14.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel12 = new JLabel();
			jLabel12.setBounds(new java.awt.Rectangle(11, 132, 67, 24));
			jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel12.setText("加工费单价");
			jLabel13 = new JLabel();
			jLabel13.setBounds(new java.awt.Rectangle(426, 103, 38, 23));
			jLabel13.setText("(\u5343\u514b)");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new java.awt.Rectangle(220, 191, 73, 23));
			jLabel11.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel11.setText("\u7b2c\u4e8c\u6cd5\u5b9a\u6570\u91cf");
			jLabel11.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel10 = new JLabel();
			jLabel10.setBounds(new java.awt.Rectangle(221, 160, 72, 23));
			jLabel10.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel10.setText("\u7b2c\u4e8c\u6cd5\u5b9a\u5355\u4f4d");
			jLabel10.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel9 = new JLabel();
			jLabel9.setBounds(new java.awt.Rectangle(235, 103, 58, 23));
			jLabel9.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel9.setText("\u5355\u4f4d\u91cd\u91cf");
			jLabel9.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel8 = new JLabel();
			jLabel8.setForeground(Color.BLUE);
			jLabel8.setBounds(new java.awt.Rectangle(232, 74, 61, 23));
			jLabel8.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel8.setText("\u5e01\u5236");
			jLabel8.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel7 = new JLabel();

			jLabel7.setForeground(new Color(51, 51, 51));
			jLabel7.setBounds(new java.awt.Rectangle(220, 132, 73, 23));
			jLabel7.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel7.setText("\u4ea7\u9500\u56fd");
			jLabel7.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(5, 191, 73, 23));
			jLabel6.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel6.setText("\u7b2c\u4e00\u6cd5\u5b9a\u6570\u91cf");
			jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(5, 160, 73, 23));
			jLabel5.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel5.setText("\u7b2c\u4e00\u6cd5\u5b9a\u5355\u4f4d");
			jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel3 = new JLabel();
			jLabel3.setForeground(Color.BLUE);
			jLabel3.setBounds(new java.awt.Rectangle(5, 103, 73, 23));
			jLabel3.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel3.setText("\u5355\u4f4d");
			jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(5, 74, 73, 23));
			jLabel2.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel2.setText("\u5355\u4ef7");
			jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1 = new JLabel();
			jLabel1.setForeground(Color.BLUE);
			jLabel1.setBounds(new java.awt.Rectangle(5, 45, 73, 23));
			jLabel1.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel1.setText("\u5546\u54c1\u540d\u79f0");
			jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(229, 45, 64, 23));
			jLabel4.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel4.setText("\u578b\u53f7\u89c4\u683c");
			jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel = new JLabel();
			jLabel.setForeground(Color.BLUE);
			jLabel.setBounds(new java.awt.Rectangle(230, 16, 63, 23));
			jLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel.setText("\u5546\u54c1\u7f16\u7801");
			jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel15 = new JLabel();
			jLabel15.setBounds(new java.awt.Rectangle(5, 16, 73, 23));
			jLabel15.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel15.setText("归并序号");
			jLabel15.setHorizontalAlignment(SwingConstants.RIGHT);
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
			jPanel.add(jLabel15, null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel5, null);
			jPanel.add(jLabel6, null);
			jPanel.add(jLabel7, null);
			jPanel.add(jLabel8, null);
			jPanel.add(jLabel9, null);
			jPanel.add(jLabel10, null);
			jPanel.add(jLabel11, null);
			jPanel.add(jLabel13, null);
			jPanel.add(jLabel12, null);
			jPanel.add(getTfMachPrice(), null);
			jPanel.add(jLabel14, null);
			jPanel.add(getCbIsMainImg(), null);
		}
		return jPanel;
	}

	public void setTable(JTable tb) {
		this.tb = tb;
		this.tableModel = (JTableListModel) tb.getModel();
	}

	public BlsInnerMergeAction getBlsInnerMergeAction() {
		return blsInnerMergeAction;
	}

	public void setBlsInnerMergeAction(BlsInnerMergeAction blsInnerMergeAction) {
		this.blsInnerMergeAction = blsInnerMergeAction;
	}

	public JTable getTb() {
		return tb;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

//	public String getMaterielType() {
//		return materielType;
//	}
//
//	public void setMaterielType(String materielType) {
//		this.materielType = materielType;
//	}

	/**
	 * This method initializes jCustomFormattedTextField
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

	public boolean isAddFromBlsTenInnerMerge() {
		return isAddFromBlsTenInnerMerge;
	}

	public void setAddFromBlsTenInnerMerge(boolean isInnerMergeEdit) {
		this.isAddFromBlsTenInnerMerge = isInnerMergeEdit;
	}

	public BlsTenInnerMerge getBlsTenInnerMerge() {
		return blsTenInnerMerge;
	}

	public void setBlsTenInnerMerge(BlsTenInnerMerge blsTenInnerMerge) {
		this.blsTenInnerMerge = blsTenInnerMerge;
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {

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

		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitPrice, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitWeight, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfMachPrice, 5);
		CustomFormattedTextFieldUtils
				.setFormatterFactory(this.tfLegalAmount, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(
				this.tfSecondLegalAmount, 5);
	}

	/**
	 * 初始化十码内部归并
	 * 
	 */
	private void initUIBlsTenInnerMerge() {
		if (dataState == DataState.ADD) {// 新增10位归并
			Object object = ((JTableListModel) tb.getModel()).getCurrentRow();
			if (object == null) {
				return;
			}
			BlsInnerMerge blsInnerMerge = (BlsInnerMerge) object;
			if (isAddFromBlsTenInnerMerge == false) { // 来自新增十码
				Materiel m = blsInnerMerge.getMateriel();
				if (m != null) {
					int maxTenInnerMergeNo = this.blsInnerMergeAction
							.getMaxTenBlsInnerMergeNo(new Request(CommonVars
									.getCurrUser()));
					blsTenInnerMerge.setSeqNum(++maxTenInnerMergeNo);
					blsTenInnerMerge.setComplex(m.getComplex());
					blsTenInnerMerge.setCountry(m.getSysArea() == null ? null
							: m.getSysArea().getCountry());
					blsTenInnerMerge.setCurr(null);
					blsTenInnerMerge.setLegalAmount(null);
					blsTenInnerMerge.setName(m.getFactoryName());
					blsTenInnerMerge.setSpec(m.getFactorySpec());
					blsTenInnerMerge.setSecondLegalAmount(null);
					blsTenInnerMerge.setComUnit(m.getComplex() == null ? null
							: m.getComplex().getFirstUnit());
					blsTenInnerMerge.setPrice(m.getPtPrice());
					blsTenInnerMerge.setUnitWeight(m.getPtNetWeight());
				}
			}
		} else if (dataState == DataState.EDIT) {// 修改10位归并
			Object object = ((JTableListModel) tb.getModel()).getCurrentRow();
			if (object == null) {
				return;
			}
			if (object instanceof BlsInnerMerge) {// 是在BCS内部归并中修改
				BlsInnerMerge tempB = (BlsInnerMerge) object;
				this.blsTenInnerMerge = tempB.getBlsTenInnerMerge();
			} else if (object instanceof BlsTenInnerMerge) {// 是在BCS十码内部归并中修改
				blsTenInnerMerge = (BlsTenInnerMerge) object;
			}
		}
		showData();
	}

	/**
	 * 显示数据
	 * 
	 */
	private void showData() {
		// if (dataState != DataState.ADD && !isAddFromBlsTenInnerMerge) {
		if (this.tableModel.getCurrentRow() instanceof BlsInnerMerge) {
			blsTenInnerMerge = ((BlsInnerMerge) this.tableModel.getCurrentRow())
					.getBlsTenInnerMerge();
		} else {
			blsTenInnerMerge = (BlsTenInnerMerge) this.tableModel
					.getCurrentRow();
		}
		// }
		if (this.blsTenInnerMerge == null) {
			blsTenInnerMerge = new BlsTenInnerMerge();
			return;
		}
		if (this.blsTenInnerMerge.getSeqNum() != null) {
			this.tfSeqNum.setText(String.valueOf(this.blsTenInnerMerge
					.getSeqNum()));
		}
		if (this.blsTenInnerMerge.getName() != null) {
			tfName.setText(this.blsTenInnerMerge.getName());

		} else {
			tfName.setText("");
		}
		if (this.blsTenInnerMerge.getSpec() != null) {
			tfSpec.setText(this.blsTenInnerMerge.getSpec());
		} else {
			tfSpec.setText("");
		}
		if (this.blsTenInnerMerge.getComplex() != null) {
			c = blsTenInnerMerge.getComplex();
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
		if (this.blsTenInnerMerge.getPrice() != null) {
			tfUnitPrice.setValue(this.blsTenInnerMerge.getPrice());
		} else {
			tfUnitPrice.setValue(new Double(0));
		}
		if (this.blsTenInnerMerge.getMachPrice() != null) {
			tfMachPrice.setValue(this.blsTenInnerMerge.getMachPrice());
		} else {
			tfMachPrice.setValue(new Double(0));
		}
		if (this.blsTenInnerMerge.getUnitWeight() != null) {
			tfUnitWeight.setValue(blsTenInnerMerge.getUnitWeight());
		} else {
			tfUnitWeight.setValue(new Double(0));
		}
		cbbCurr.setSelectedItem(this.blsTenInnerMerge.getCurr());
		cbbUnit.setSelectedItem(blsTenInnerMerge.getComUnit());
		cbbCountry.setSelectedItem(this.blsTenInnerMerge.getCountry());
		if (this.blsTenInnerMerge.getLegalAmount() != null) {
			this.tfLegalAmount.setValue(this.blsTenInnerMerge.getLegalAmount());
		} else {
			this.tfLegalAmount.setValue(new Double(0));
		}
		if (this.blsTenInnerMerge.getSecondLegalAmount() != null) {
			this.tfSecondLegalAmount.setValue(this.blsTenInnerMerge
					.getSecondLegalAmount());
		} else {
			this.tfSecondLegalAmount.setValue(new Double(0));
		}
		this.cbIsMainImg
				.setSelected(blsTenInnerMerge.getIsMainImg() == null ? false
						: blsTenInnerMerge.getIsMainImg());
	}

	/**
	 * 填充数据
	 * 
	 */
	private void fillData() {
		this.blsTenInnerMerge.setComplex(c);
		this.blsTenInnerMerge.setCompany(CommonVars.getCurrUser().getCompany());
		this.blsTenInnerMerge.setName(tfName.getText());
		this.blsTenInnerMerge.setSpec(tfSpec.getText());
		this.blsTenInnerMerge.setCurr((Curr) cbbCurr.getSelectedItem());
		blsTenInnerMerge.setComUnit((Unit) cbbUnit.getSelectedItem());
		this.blsTenInnerMerge
				.setCountry((Country) cbbCountry.getSelectedItem());

		if (this.tfUnitWeight.getValue() != null) {
			this.blsTenInnerMerge.setUnitWeight(Double
					.valueOf(this.tfUnitWeight.getValue().toString()));
		} else {
			this.blsTenInnerMerge.setUnitWeight(null);
		}
		if (this.tfUnitPrice.getValue() != null) {
			this.blsTenInnerMerge.setPrice(Double.valueOf(this.tfUnitPrice
					.getValue().toString()));
		} else {
			this.blsTenInnerMerge.setPrice(null);
		}
		if (this.tfLegalAmount.getValue() != null) {
			this.blsTenInnerMerge.setLegalAmount(Double
					.valueOf(this.tfLegalAmount.getValue().toString()));
		} else {
			this.blsTenInnerMerge.setLegalAmount(null);
		}
		if (this.tfSecondLegalAmount.getValue() != null) {
			this.blsTenInnerMerge.setSecondLegalAmount(Double
					.valueOf(this.tfSecondLegalAmount.getValue().toString()));
		} else {
			this.blsTenInnerMerge.setSecondLegalAmount(null);
		}
		if (this.tfMachPrice.getValue() != null) {
			blsTenInnerMerge.setMachPrice(Double.valueOf(this.tfMachPrice
					.getValue().toString()));
		} else {
			blsTenInnerMerge.setMachPrice(null);
		}
//		if (blsTenInnerMerge.getScmCoi() == null
//				|| "".equals(blsTenInnerMerge.getScmCoi())) {
//			blsTenInnerMerge.setScmCoi(this.materielType);
//		}
		// 主料辅料
		blsTenInnerMerge.setIsMainImg(this.cbIsMainImg.isSelected());
	}

	/**
	 * 保存数据
	 * 
	 */
	private void saveData() {

		if (dataState == DataState.ADD) {// 新增10位归并
			if (isAddFromBlsTenInnerMerge == false) { // 来自新增十码
				int maxTenInnerMergeNo = this.blsInnerMergeAction
						.getMaxTenBlsInnerMergeNo(new Request(CommonVars
								.getCurrUser()));
				blsTenInnerMerge.setSeqNum(maxTenInnerMergeNo + 1);
			}
			this.fillData();
			List currentRows = ((JTableListModel) tb.getModel())
					.getCurrentRows();
			List ls = this.blsInnerMergeAction.saveBlsInnerMerge(new Request(
					CommonVars.getCurrUser()), currentRows, blsTenInnerMerge);
			refreshTable(ls);

		} else if (dataState == DataState.EDIT) {// 修改10位归并
			this.updateBlsTenInnerMerge();
		}
	}

	/**
	 * 修改十位商品编码( 当 dataState == DataState.EDIT )
	 * 
	 */
	private void updateBlsTenInnerMerge() {
		Object object = ((JTableListModel) tb.getModel()).getCurrentRow();
		if (object == null) {
			return;
		}
		if (object instanceof BlsInnerMerge) {// 是在BCS内部归并中修改
			BlsInnerMerge tempB = (BlsInnerMerge) object;
			blsTenInnerMerge = tempB.getBlsTenInnerMerge();
//			if (!isValidate(blsTenInnerMerge)) {
//				return;
//			}
			List currentRows = ((JTableListModel) tb.getModel())
					.getCurrentRows();
			this.fillData();
			List ls = this.blsInnerMergeAction.updateBlsInnerMerge(new Request(
					CommonVars.getCurrUser()), currentRows, blsTenInnerMerge);
			refreshTable(ls);

		} else if (object instanceof BlsTenInnerMerge) {// 是在BCS十码内部归并中修改
			blsTenInnerMerge = (BlsTenInnerMerge) object;
//			if (!isValidate(blsTenInnerMerge)) {
//				return;
//			}
			this.fillData();
			blsTenInnerMerge = this.blsInnerMergeAction.saveBlsTenInnerMerge(
					new Request(CommonVars.getCurrUser()), blsTenInnerMerge);
			((JTableListModel) tb.getModel()).updateRow(blsTenInnerMerge);
		}

	}

//	/**
//	 * 验证数据是否被引用
//	 * 
//	 * @param blsTenInnerMerge
//	 * @return
//	 */
//	private boolean isValidate(BlsTenInnerMerge blsTenInnerMerge) {
//		List tempList = blsInnerMergeAction.findBlsInnerMergeInContract(
//				new Request(CommonVars.getCurrUser()), blsTenInnerMerge);
//		if (MaterielType.MATERIEL.equals(materielType)) {
//			String info = "";
//			for (int i = 0; i < tempList.size(); i++) {
////				ContractImg c = (ContractImg) tempList.get(i);
//				// if (DeclareState.PROCESS_EXE.equals(c.getDeclareState())) {
//				// info += (c.getContract().getImpContractNo() + " 合同序号 "
//				// + c.getSeqNum() + " 料件引用 !!" + "\n");
//				// }
//			}
//			if (!info.equals("")) {
//				JOptionPane.showMessageDialog(this, "选中的行中有数据在合同中被\n" + info
//						+ "不能修改!!", "提示", JOptionPane.INFORMATION_MESSAGE);
//				return false;
//			}
//		} else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
//			String info = "";
//			for (int i = 0; i < tempList.size(); i++) {
////				ContractExg c = (ContractExg) tempList.get(i);
//				// if (DeclareState.PROCESS_EXE.equals(c.getDeclareState())) {
//				// info += (c.getContract().getExpContractNo() + " 合同序号 "
//				// + c.getSeqNum() + " 成品引用 !!" + "\n");
//				// }
//			}
//			if (!info.equals("")) {
//				JOptionPane.showMessageDialog(this, "选中的行中有数据在合同中被\n" + info
//						+ "不能修改!!", "提示", JOptionPane.INFORMATION_MESSAGE);
//				return false;
//			}
//		}
//		return true;
//
//	}

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
//		if (this.cbbCountry.getSelectedItem() == null) {
//
//			JOptionPane.showMessageDialog(this, "产销国不能为空!!", "提示",
//					JOptionPane.INFORMATION_MESSAGE);
//			return false;
//
//		}
		if (this.cbbCurr.getSelectedItem() == null) {

			JOptionPane.showMessageDialog(this, "币制不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;

		}
		if (this.cbbUnit.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "单位不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;

		}
		return true;
	}

	/**
	 * This method initializes jButton
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
	 * This method initializes jButton1
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
					if (validateData() == true) {
						saveData();
					} else
						return;
					System.out.println(((BlsTenInnerMerge) tableModel
							.getCurrentRow()).getName());
					if (!tableModel.nextRow()) {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(false);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					System.out.println(((BlsTenInnerMerge) tableModel
							.getCurrentRow()).getName());
					showData();
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnNext;
	}

	private void setState() {
		// this.tfSeqNum.setEditable(this.dataState != DataState.BROWSE);
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
		this.tfSecondLegalAmount
				.setEditable(this.dataState != DataState.BROWSE);
		this.cbIsMainImg.setEnabled(this.dataState != DataState.BROWSE);
		this.btnSave.setEnabled(this.dataState != DataState.BROWSE);
		this.btnPrevious.setVisible(!isInnerMerge);
		this.btnNext.setVisible(!isInnerMerge);
		this.btnPrevious.setEnabled(tableModel.hasPreviousRow());
		this.btnNext.setEnabled(tableModel.hasNextRow());
	}

	/**
	 * This method initializes jCheckBox
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
}
