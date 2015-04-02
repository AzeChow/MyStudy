/*
 * Created on 2004-7-26
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.InitialFocusSetter;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.CaleUtil;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.util.RegexUtil;

/**
 * @author bsway // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DgImpExpCommodityInfo extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private JTextField tfVersion = null;

	private JTextField tfName = null;

	private JTextField tfUnit = null;

	private JTextField tfBillNo = null;

	private JTextField tfSpec = null;

	private JTextField tfMakeBillNo = null;

	private JTextField tfMaterielNo = null;

	private JTableListModel tableModel = null;

	private EncAction encAction = null; // @jve:decl-index=0:

	private ImpExpRequestBill impExpRequestBill = null; // @jve:decl-index=0:

	private int dataState = DataState.BROWSE;

	private Materiel materiel = null; // @jve:decl-index=0:

	private JFormattedTextField tfQuantity = null;

	private DefaultFormatterFactory defaultFormatterFactory = null;

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:

	private JFormattedTextField tfUnitPrice = null;

	private JFormattedTextField tfAmount = null;

	private JFormattedTextField tfGrossWeight = null;

	private JFormattedTextField tfBulks = null;

	private JFormattedTextField tfNetWeight = null;

	private JComboBox cbbTradeCountry = null;

	private JComboBox cbbCurr = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private int totalCount = 0; // 总行

	private int rowCount = 0; // 当前行

	private ImpExpCommodityInfo impExpCommodityInfo = null; // @jve:decl-index=0:

	private JLabel jLabel18 = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel13 = null;

	private JComboBox cbbWareSet = null;

	private MaterialManageAction materialManageAction = null;

	private CustomBaseAction customBaseAction = null;

	private JLabel jLabel19 = null;

	private JTextField tfPiece = null;

	private JLabel jLabel191 = null;

	private DecimalFormat decimalFormat1 = null;

	private JTextField tfExtendMemo = null;

	private JLabel jLabel20 = null;

	private JLabel jLabel21 = null;

	private JLabel jLabel22 = null;

	private JTextField tfAftername = null;

	private JTextField tfAfterspec = null;

	private JTextField tfAfterunit = null;

	private JLabel jLabel23 = null;

	private JTextField tfSetNum = null;

	private JLabel jLabel24 = null;

	private JLabel jLabel25 = null;

	private JFormattedTextField tfinvGrossWeight = null;

	private DecimalFormat decimalFormat2 = null; // @jve:decl-index=0:

	private JFormattedTextField tfinvNetWeight = null;

	private DecimalFormat decimalFormat3 = null; // @jve:decl-index=0:

	private JTextField tfBoxNo = null;

	private JLabel lbBoxNo = null;

	private JLabel jLabel26 = null;

	private JFormattedTextField tfWorkUsd = null;

	private JLabel jLabel27 = null;

	private JTextField tfCmpVersion = null;

	private JLabel jLabel28 = null;

	private JTextField tfStartBoxNo = null;

	private JLabel jLabel29 = null;

	private JTextField tfEndBoxNo = null;

	private JLabel jLabel30 = null;

	private JLabel jLabel31 = null;

	private JTextField tfTotalBoxNum = null;

	private JComboBox cbbWrapType = null;
	private CompanyOther other = CommonVars.getOther();

	public DgImpExpCommodityInfo() {
		super();
		initialize();
		this.encAction = (EncAction) CommonVars.getApplicationContext()
				.getBean("encAction");
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		initUIComponents();
	}

	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("进出口申请单---商品信息表");
		this.setSize(507, 447);
	}

	public void setVisible(boolean isShow) {
		if (isShow) {
			totalCount = tableModel.getRowCount(); // 总记录数
			if (impExpCommodityInfo == null) {
				impExpCommodityInfo = (ImpExpCommodityInfo) tableModel
						.getCurrentRow();
			}
			rowCount = tableModel.getCurrRowCount();// 当前记录

			showData(impExpCommodityInfo);
			setstate();
			InitialFocusSetter.setInitialFocus(this, getJFormattedTextField());
		}
		super.setVisible(isShow);
	}

	/**
	 * @return Returns the materiel.
	 */
	public Materiel getMateriel() {
		return materiel;
	}

	/**
	 * @param materiel
	 *            The materiel to set.
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
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
	 * @return Returns the encAction.
	 */
	public EncAction getEncAction() {
		return encAction;
	}

	/**
	 * @param encAction
	 *            The encAction to set.
	 */
	public void setEncAction(EncAction encAction) {
		this.encAction = encAction;
	}

	/**
	 * @return Returns the impExpRequestBill.
	 */
	public ImpExpRequestBill getImpExpRequestBill() {
		return impExpRequestBill;
	}

	/**
	 * @param impExpRequestBill
	 *            The impExpRequestBill to set.
	 */
	public void setImpExpRequestBill(ImpExpRequestBill impExpRequestBill) {
		this.impExpRequestBill = impExpRequestBill;
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
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel31 = new JLabel();
			jLabel31.setBounds(new Rectangle(257, 446, 67, 25));
			jLabel31.setText("包装方式：");
			jLabel31.setVisible(false);
			jLabel30 = new JLabel();
			jLabel30.setBounds(new Rectangle(22, 446, 67, 25));
			jLabel30.setText("箱数：");
			jLabel30.setVisible(false);
			jLabel29 = new JLabel();
			jLabel29.setBounds(new Rectangle(257, 418, 67, 25));
			jLabel29.setText("结束箱号：");
			jLabel29.setVisible(false);
			jLabel28 = new JLabel();
			jLabel28.setBounds(new Rectangle(22, 418, 67, 25));
			jLabel28.setText("起始箱号：");
			jLabel28.setVisible(false);
			jLabel27 = new JLabel();
			jLabel27.setBounds(new Rectangle(145, 301, 51, 18));
			jLabel27.setText("企业版本");
			jLabel26 = new JLabel();

			jLabel26.setBounds(new Rectangle(22, 215, 67, 25));
			jLabel26.setText("加工费总价");
			lbBoxNo = new JLabel();
			lbBoxNo.setBounds(new Rectangle(257, 359, 67, 25));
			lbBoxNo.setText("箱号");
			jLabel25 = new JLabel();
			jLabel25.setBounds(new Rectangle(22, 243, 67, 25));
			jLabel25.setText("单净重");
			jLabel24 = new JLabel();
			jLabel24.setBounds(new Rectangle(22, 270, 67, 25));
			jLabel24.setText("单毛重");
			jLabel23 = new JLabel();
			jLabel23.setBounds(new Rectangle(257, 45, 67, 25));
			jLabel23.setText("备案序号");
			jLabel22 = new JLabel();
			jLabel22.setBounds(new Rectangle(257, 128, 67, 25));
			jLabel22.setText("备案单位");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(257, 100, 67, 25));
			jLabel21.setText("备案规格");
			jLabel20 = new JLabel();
			jLabel20.setBounds(new Rectangle(257, 72, 67, 25));
			jLabel20.setText("备案名称");
			jLabel191 = new JLabel();
			jLabel191.setBounds(new Rectangle(22, 386, 67, 25));
			jLabel191.setText("扩展备注");
			jLabel19 = new JLabel();
			jLabel19.setBounds(new Rectangle(22, 359, 67, 25));
			jLabel19.setText("件数");
			jLabel18 = new JLabel();
			jLabel18.setBounds(new Rectangle(257, 332, 67, 25));
			jLabel18.setText("仓库名称");
			jLabel8 = new JLabel();
			jLabel8.setText("海关版本");
			jLabel8.setBounds(new Rectangle(22, 299, 67, 25));
			javax.swing.JLabel jLabel17 = new JLabel();
			javax.swing.JLabel jLabel16 = new JLabel();
			javax.swing.JLabel jLabel15 = new JLabel();
			javax.swing.JLabel jLabel14 = new JLabel();
			jLabel13 = new JLabel();
			javax.swing.JLabel jLabel11 = new JLabel();
			javax.swing.JLabel jLabel10 = new JLabel();
			jContentPane = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(0, 0, 0, 0));
			javax.swing.JLabel jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(0, 0, 0, 0));
			javax.swing.JLabel jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(0, 0, 0, 0));
			jLabel10.setText("名称");
			jLabel10.setBounds(new Rectangle(23, 100, 67, 25));
			jLabel11.setText("规格型号");
			jLabel11.setBounds(new Rectangle(23, 128, 67, 25));
			jLabel13.setText("数量");
			jLabel13.setBounds(new Rectangle(257, 157, 67, 25));
			jLabel14.setText("总金额");
			jLabel14.setBounds(new Rectangle(257, 186, 67, 25));
			jLabel15.setText("毛重");
			jLabel15.setBounds(new Rectangle(257, 273, 67, 25));
			jLabel16.setText("产销国");
			jLabel16.setBounds(new Rectangle(257, 214, 67, 25));
			jLabel17.setText("制单号");
			jLabel17.setBounds(new Rectangle(257, 301, 67, 25));
			jContentPane.setLayout(null);
			jLabel.setText("料号");
			jLabel.setBounds(new Rectangle(23, 72, 67, 25));
			jLabel1.setText("单据号");
			jLabel1.setBounds(new Rectangle(23, 45, 67, 25));
			jLabel2.setText("单位");
			jLabel2.setBounds(new Rectangle(23, 157, 67, 25));
			jLabel6.setText("单价");
			jLabel6.setBounds(new Rectangle(22, 186, 67, 25));
			jLabel6.setForeground(java.awt.Color.blue);
			jLabel7.setText("体积");
			jLabel7.setBounds(new Rectangle(22, 329, 67, 25));
			jLabel9.setText("净重");
			jLabel9.setBounds(new Rectangle(257, 243, 67, 25));
			jLabel9.setForeground(java.awt.Color.blue);
			jLabel12.setText("币别");
			jLabel12.setBounds(new Rectangle(156, 215, 30, 25));
			jLabel12.setForeground(java.awt.Color.blue);
			jLabel13.setForeground(java.awt.Color.blue);
			jLabel14.setForeground(java.awt.Color.blue);
			jLabel15.setForeground(java.awt.Color.blue);
			jLabel16.setForeground(java.awt.Color.blue);
			jContentPane.add(getTfVersion(), null);
			jContentPane.add(getTfName(), null);
			jContentPane.add(getTfUnit(), null);
			jContentPane.add(getTfBillNo(), null);
			jContentPane.add(getTfSpec(), null);
			jContentPane.add(getTfMakeBillNo(), null);
			jContentPane.add(getTfMaterielNo(), null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(jLabel13, null);
			jContentPane.add(jLabel14, null);
			jContentPane.add(jLabel15, null);
			jContentPane.add(jLabel16, null);
			jContentPane.add(jLabel17, null);
			jContentPane.add(getJToolBar(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(jLabel9, null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(getJFormattedTextField(), null);
			jContentPane.add(getJFormattedTextField2(), null);
			jContentPane.add(getJFormattedTextField3(), null);
			jContentPane.add(getJFormattedTextField4(), null);
			jContentPane.add(getJFormattedTextField1(), null);
			jContentPane.add(getJFormattedTextField22(), null);
			jContentPane.add(getCbbTradeCountry(), null);
			jContentPane.add(getCbbCurr(), null);
			jContentPane.add(jLabel18, null);
			jContentPane.add(getCbbWareSet(), null);
			jContentPane.add(jLabel19, null);
			jContentPane.add(getTfPiece(), null);
			jContentPane.add(jLabel191, null);
			jContentPane.add(getTfExtendMemo(), null);
			jContentPane.add(jLabel20, null);
			jContentPane.add(jLabel21, null);
			jContentPane.add(jLabel22, null);
			jContentPane.add(getTfAftername(), null);
			jContentPane.add(getTfAfterspec(), null);
			jContentPane.add(getTfAfterunit(), null);
			jContentPane.add(jLabel23, null);
			jContentPane.add(getTfSetNum(), null);
			jContentPane.add(jLabel24, null);
			jContentPane.add(jLabel25, null);
			jContentPane.add(getTfinvGrossWeight(), null);
			jContentPane.add(getTfinvNetWeight(), null);
			jContentPane.add(getTfBoxNo(), null);
			jContentPane.add(lbBoxNo, null);
			jContentPane.add(jLabel26, null);
			jContentPane.add(getTfWorkUsd(), null);
			jContentPane.add(jLabel27, null);
			jContentPane.add(getTfCmpVersion(), null);
			jContentPane.add(jLabel28, null);
			jContentPane.add(getTfStartBoxNo(), null);
			jContentPane.add(jLabel29, null);
			jContentPane.add(getTfEndBoxNo(), null);
			jContentPane.add(jLabel30, null);
			jContentPane.add(jLabel31, null);
			jContentPane.add(getTfTotalBoxNum(), null);
			jContentPane.add(getCbbWrapType(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJFormattedTextField4() {
		if (tfGrossWeight == null) {
			tfGrossWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfGrossWeight.setBounds(new Rectangle(329, 273, 151, 25));
			tfGrossWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfGrossWeight;
	}

	/**
	 * This method initializes jFormattedTextField1
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJFormattedTextField1() {
		if (tfBulks == null) {
			tfBulks = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfBulks.setBounds(new Rectangle(94, 326, 151, 25));
			tfBulks.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfBulks;
	}

	/**
	 * This method initializes jFormattedTextField2
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJFormattedTextField22() {
		if (tfNetWeight == null) {
			tfNetWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfNetWeight.setBounds(new Rectangle(329, 243, 151, 25));
			tfNetWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfNetWeight;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setBounds(new java.awt.Rectangle(3, 5, 493, 31));
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
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
			btnSave.setText("   保存   ");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (vaildatorDataIsNull()) {
						return;
					}
					if (!vaildatorDataOther()) {
						return;
					}
					saveData();
					DgImpExpCommodityInfo.this.dispose();
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
			btnCancel.setText("   取消   ");
		}
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				DgImpExpCommodityInfo.this.dispose();
			}
		});
		return btnCancel;
	}

	/**
	 * This method initializes tfVersion
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfVersion() {
		if (tfVersion == null) {
			tfVersion = new JTextField();
			tfVersion.setBounds(new Rectangle(94, 297, 49, 25));
		}
		return tfVersion;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJFormattedTextField() {
		if (tfQuantity == null) {
			tfQuantity = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfQuantity.setBounds(new Rectangle(329, 157, 151, 25));
			tfQuantity.setFormatterFactory(getDefaultFormatterFactory());
			tfQuantity.getDocument().addDocumentListener(
					new DocumentListenerAdapter());
		}
		return tfQuantity;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setNullFormatter(getNumberFormatter());
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
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
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setMaximumFractionDigits(999);
			decimalFormat.setGroupingSize(9999);
			numberFormatter = new NumberFormatter(decimalFormat);
		}
		return numberFormatter;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJFormattedTextField2() {
		if (tfUnitPrice == null) {
			tfUnitPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitPrice.setBounds(new Rectangle(94, 186, 151, 25));
			tfUnitPrice.setFormatterFactory(getDefaultFormatterFactory());
			tfUnitPrice.setText("0");
			// tfUnitPrice.getDocument().addDocumentListener(
			// new DocumentListenerAdapter());
			tfUnitPrice.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {

					tfAmount.setValue(getAmountStr());
					tfGrossWeight.setValue(getGWeightStr());
					tfNetWeight.setValue(getNWeightStr());
				}

			});
		}
		return tfUnitPrice;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJFormattedTextField3() {
		if (tfAmount == null) {
			tfAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfAmount.setEditable(false);
			tfAmount.setBounds(new Rectangle(329, 186, 151, 25));
			tfAmount.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfAmount;
	}

	/**
	 * This method initializes tfName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setEditable(false);
			tfName.setBounds(new Rectangle(94, 100, 151, 25));
		}
		return tfName;
	}

	/**
	 * This method initializes tfUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfUnit() {
		if (tfUnit == null) {
			tfUnit = new JTextField();
			tfUnit.setEditable(false);
			tfUnit.setBounds(new Rectangle(94, 157, 151, 25));
		}
		return tfUnit;
	}

	/**
	 * This method initializes tfBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBillNo() {
		if (tfBillNo == null) {
			tfBillNo = new JTextField();
			tfBillNo.setEditable(false);
			tfBillNo.setBounds(new Rectangle(94, 45, 151, 25));
		}
		return tfBillNo;
	}

	/**
	 * This method initializes tfSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSpec() {
		if (tfSpec == null) {
			tfSpec = new JTextField();
			tfSpec.setEditable(false);
			tfSpec.setBounds(new Rectangle(94, 128, 151, 25));
		}
		return tfSpec;
	}

	/**
	 * This method initializes tfMakeBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMakeBillNo() {
		if (tfMakeBillNo == null) {
			tfMakeBillNo = new JTextField();
			tfMakeBillNo.setBounds(new Rectangle(329, 301, 151, 25));
		}
		return tfMakeBillNo;
	}

	/**
	 * This method initializes tfMaterielNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterielNo() {
		if (tfMaterielNo == null) {
			tfMaterielNo = new JTextField();
			tfMaterielNo.setEditable(false);
			tfMaterielNo.setBounds(new Rectangle(94, 72, 151, 25));
		}
		return tfMaterielNo;
	}

	// 显示记录
	public void showData(ImpExpCommodityInfo data) {
		tfBulks.setValue(data.getBulks() == null ? null : data.getBulks());
		this.cbbTradeCountry.setSelectedItem(data.getCountry());
		this.cbbCurr.setSelectedItem(data.getCurrency());

		tfinvNetWeight.setValue(data.getInvnetWeight() == null ? Double
				.valueOf("0") : data.getInvnetWeight());

		
		// 单毛重
		tfinvGrossWeight.setValue(data.getInvgrossWeight() == null ? Double
				.valueOf("0") : data.getInvgrossWeight());

		// 净重
		tfNetWeight.setValue(data.getNWeight() == null ? Double.valueOf("0")
				: data.getNWeight());
	
		tfGrossWeight.setValue(data.getGWeight() == null ? Double.valueOf("0")
				: data.getGWeight());

		this.impExpRequestBill = this.getImpExpRequestBill();
		if (impExpRequestBill != null) {
			this.tfBillNo.setText(impExpRequestBill.getBillNo().toString());
		}
		tfMakeBillNo.setText(data.getMakeBillNo() == null ? "" : data
				.getMakeBillNo().toString());
		this.materiel = data.getMateriel();
		if (this.materiel != null) {
			this.tfSpec.setText(this.materiel.getFactorySpec());
			this.tfMaterielNo.setText(this.materiel.getPtNo());
			this.tfName.setText(this.materiel.getFactoryName());
			if (this.materiel.getCalUnit() != null) {
				this.tfUnit.setText(this.materiel.getCalUnit().getName());
			}
		}

		tfQuantity.setValue(data.getQuantity() == null ? Double.valueOf("0")
				: data.getQuantity());

		tfPiece.setText(data.getPiece() == null ? "" : String.valueOf(data
				.getPiece()));
		tfUnitPrice.setValue(data.getUnitPrice() == null ? Double.valueOf("0")
				: data.getUnitPrice());
		tfVersion.setText(data.getVersion() == null ? "" : data.getVersion()
				.toString());
		tfCmpVersion.setText(data.getCmpVersion() == null ? "" : data
				.getCmpVersion().toString());
		tfAmount.setValue(data.getAmountPrice() == null ? 0.0 : data
				.getAmountPrice());

		tfWorkUsd.setValue(data.getWorkUsd() == null ? 0.0 : data.getWorkUsd());

		this.cbbWareSet.setSelectedItem(this.impExpRequestBill.getWareSet());
		this.tfExtendMemo.setText(this.impExpCommodityInfo.getExtendMemo());

		tfBoxNo.setText(impExpCommodityInfo.getBoxNo());
		tfSetNum.setText(impExpCommodityInfo.getSeqNum() == null ? ""
				: impExpCommodityInfo.getSeqNum().toString());
		tfAftername.setText(this.impExpCommodityInfo.getAfterName());

		tfAfterspec.setText(this.impExpCommodityInfo.getAfterSpec());
		tfAfterunit.setText(this.impExpCommodityInfo.getAfterUnit());
		tfStartBoxNo.setText(data.getStartBoxNo());
		tfEndBoxNo.setText(data.getEndBoxNo());
		if (data.getTotalBoxNum() != null
				&& !"".equals(data.getTotalBoxNum().trim())) {
			tfTotalBoxNum.setText(data.getTotalBoxNum());
		}
		if (data.getWrapType() != null) {
			cbbWrapType.setSelectedItem(data.getWrapType());
		}

	}

	protected void fillData(ImpExpCommodityInfo data) {
		data.setBulks(tfBulks.getText() == null || "".equals(tfBulks.getText()) ? null
				: Double.valueOf(tfBulks.getValue().toString()));
		data.setCountry((Country) this.cbbTradeCountry.getSelectedItem());
		data.setCurrency((Curr) this.cbbCurr.getSelectedItem());
		data.setGrossWeight(Double.valueOf(tfGrossWeight.getValue().toString()));
		data.setMakeBillNo(tfMakeBillNo.getText());
		data.setMateriel(this.materiel);
		data.setNetWeight(tfNetWeight.getValue() == null ? 0.0 : Double
				.valueOf(tfNetWeight.getText().toString()));
		data.setInvgrossWeight(tfinvGrossWeight.getValue() == null ? 0.0
				: Double.valueOf(tfinvGrossWeight.getText().toString()));
		data.setInvnetWeight(tfinvNetWeight.getValue() == null ? 0.0 : Double
				.valueOf(tfinvNetWeight.getText().toString()));
		data.setQuantity(tfQuantity.getValue() == null ? 0.0 : Double
				.valueOf(tfQuantity.getText().toString()));
		data.setWorkUsd(tfWorkUsd.getValue() == null ? 0.0 : Double
				.valueOf(tfWorkUsd.getText()));
		Integer piece = 0;
		try {
			piece = Integer.valueOf(tfPiece.getText());
		} catch (Exception e) {
		}
		data.setPiece(piece);
		data.setUnitPrice(tfUnitPrice.getValue() == null ? 0.0 : Double
				.valueOf(tfUnitPrice.getText().toString()));
		data.setVersion(tfVersion.getText());
		data.setCmpVersion(tfCmpVersion.getText());
		data.getImpExpRequestBill().setWareSet(
				(WareSet) this.cbbWareSet.getSelectedItem());
		data.setBoxNo(tfBoxNo.getText().trim());
		data.setExtendMemo(tfExtendMemo.getText().trim());
		data.setAmountPrice(CaleUtil.multiply(data.getQuantity() == null ? 0.0
				: data.getQuantity(),
				data.getUnitPrice() == null ? 0.0 : data.getUnitPrice(), 5));
		if (tfStartBoxNo.getText().trim().length() > 0) {
			data.setStartBoxNo(tfStartBoxNo.getText().trim());
		} else {
			data.setStartBoxNo("");
		}
		if (tfEndBoxNo.getText().trim().length() > 0) {
			data.setEndBoxNo(tfEndBoxNo.getText().trim());
		} else {
			data.setEndBoxNo("");
		}
		if (tfTotalBoxNum.getText().trim().length() > 0) {
			data.setTotalBoxNum(tfTotalBoxNum.getText().trim());
		} else {
			data.setTotalBoxNum("");
		}
		if (cbbWrapType.getSelectedItem() != null) { // 包装种类
			data.setWrapType((Wrap) cbbWrapType.getSelectedItem());
		}
	}

	public void saveData() {
		List<ImpExpCommodityInfo> list = new ArrayList<ImpExpCommodityInfo>();

		if (dataState == DataState.ADD) {
			ImpExpCommodityInfo data = new ImpExpCommodityInfo();
			fillData(data);
			list.add(data);
			this.encAction.saveImpExpRequestBill(
					new Request(CommonVars.getCurrUser(), true),
					data.getImpExpRequestBill());
			this.encAction.saveImpExpCommodityInfo(
					new Request(CommonVars.getCurrUser()), list);
			tableModel.addRow(data);
		} else if (dataState == DataState.EDIT) {
			fillData(impExpCommodityInfo);
			list.add(impExpCommodityInfo);
			this.encAction.saveImpExpRequestBill(
					new Request(CommonVars.getCurrUser(), true),
					impExpCommodityInfo.getImpExpRequestBill());
			list = this.encAction.saveImpExpCommodityInfo(new Request(
					CommonVars.getCurrUser()), list);
			tableModel.updateRow(list.get(0));
		}
	}

	public boolean vaildatorDataIsNull() {
		if (this.tfQuantity.getText().trim().equals("")
				|| this.tfQuantity.getText().trim().equals("0")) {
			JOptionPane.showMessageDialog(this, "数量不可为空", "警告", 1);
			return true;
		}
		if (this.tfUnitPrice.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "单价不可为空", "警告", 1);
			return true;
		}
		if (this.tfAmount.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "总金额不可为空", "警告", 1);
			return true;
		}
		if (this.cbbCurr.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "币别不可为空", "警告", 1);
			return true;
		}
		// by lxr 2011-05-25因需求把毛重与净重的放开
		// if (this.tfGrossWeight.getText().trim().equals("")
		// || this.tfGrossWeight.getText().trim().equals("0")) {
		// JOptionPane.showMessageDialog(this, "毛重不可为空", "警告", 1);
		// return true;
		// }
		if (this.cbbTradeCountry.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "产销国名称不可为空", "警告", 1);
			return true;
		}
		// if (this.tfNetWeight.getText().equals("")
		// || this.tfNetWeight.getText().equals("0")) {
		// JOptionPane.showMessageDialog(null, "净重不可为空", "警告", 1);
		// return true;
		// }

		return false;
	}

	public boolean vaildatorDataOther() {
		try {
			if (Double.parseDouble(tfGrossWeight.getValue().toString()) == Double
					.valueOf(0)) {
				if (JOptionPane.showConfirmDialog(null, "毛重等于零，确定吗？", "提示", 0) != 0) {
					return false;
				}
			}
			if (Double.parseDouble(tfGrossWeight.getValue().toString()) != Double
					.valueOf(0)
					&& Double.parseDouble(tfGrossWeight.getValue().toString()) < Double
							.parseDouble(tfNetWeight.getValue().toString())) {
				JOptionPane.showMessageDialog(null, "毛重不等于零时，毛重必须大于净重!!", "警告",
						1);
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	private void emptyData() {

	}

	private Double getAmountStr() {
		tfQuantity.validate();
		try {
			BigDecimal amount = new BigDecimal(
					tfQuantity.getValue() == null ? "0.0" : tfQuantity
							.getValue().toString());
			BigDecimal bd = amount.multiply(new BigDecimal(tfUnitPrice
					.getValue() == null ? "0.0" : tfUnitPrice.getValue()
					.toString()));
			int amountNum = 0;
			if (other != null) {
				amountNum = other.getCustomTotalNumSq();
			} else {
				amountNum = 4;
			}
			return bd.setScale(amountNum, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0.0;
	}

	private Double getGWeightStr() {
		String amountStr = "0";
		double amount = 0;
		try {
			if (tfinvGrossWeight.getText() != null
					&& Double.parseDouble(tfinvGrossWeight.getValue()
							.toString()) != 0) {
				amount = Double
						.parseDouble(tfQuantity.getValue() == null ? "0.0"
								: tfQuantity.getValue().toString())
						* Double.parseDouble(tfinvGrossWeight.getValue() == null ? "0.0"
								: tfinvGrossWeight.getValue().toString());
			} else {
				amount = Double
						.parseDouble(tfGrossWeight.getValue() == null ? "0.0"
								: tfGrossWeight.getValue().toString());
			}
			BigDecimal bd = new BigDecimal(amount);
			amountStr = bd.setScale(4, BigDecimal.ROUND_HALF_UP).toString();
		} catch (Exception ex) {
		}
		return Double.parseDouble(amountStr);
	}

	private Double getNWeightStr() {
		String amountStr = "0";
		double amount = 0;
		try {
			if (tfinvNetWeight.getText() != null
					&& Double.parseDouble(tfinvNetWeight.getValue().toString()) != 0) {
				amount = Double
						.parseDouble(tfQuantity.getValue() == null ? "0.0"
								: tfQuantity.getValue().toString())
						* Double.parseDouble(tfinvNetWeight.getValue() == null ? "0.0"
								: tfinvNetWeight.getValue().toString());
			} else if ("千克".equals(tfUnit.getText())) {
				amount = Double
						.parseDouble(tfQuantity.getValue() == null ? "0.0"
								: tfQuantity.getValue().toString());
			} else {
				amount = Double.parseDouble(tfNetWeight.getValue().toString());

			}
			BigDecimal bd = new BigDecimal(amount);
			amountStr = bd.setScale(4, BigDecimal.ROUND_HALF_UP).toString();
		} catch (Exception ex) {
		}
		return Double.parseDouble(amountStr);
	}

	class DocumentListenerAdapter implements DocumentListener {

		public void insertUpdate(DocumentEvent e) {

			tfAmount.setValue(getAmountStr());
			tfGrossWeight.setValue(getGWeightStr());
			tfNetWeight.setValue(getNWeightStr());
		}

		public void removeUpdate(DocumentEvent e) {
			tfAmount.setValue(getAmountStr());
			tfGrossWeight.setValue(getGWeightStr());
			tfNetWeight.setValue(getNWeightStr());
		}

		public void changedUpdate(DocumentEvent e) {
			tfAmount.setValue(getAmountStr());
			tfGrossWeight.setValue(getGWeightStr());
			tfNetWeight.setValue(getNWeightStr());
		}

	}

	/**
	 * This method initializes cbbTradeCountry
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTradeCountry() {
		if (cbbTradeCountry == null) {
			cbbTradeCountry = new JComboBox();
			cbbTradeCountry.setBounds(new Rectangle(329, 214, 151, 25));
		}
		return cbbTradeCountry;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCurr() {
		if (cbbCurr == null) {
			cbbCurr = new JComboBox();
			cbbCurr.setBounds(new Rectangle(181, 214, 64, 25));
		}
		return cbbCurr;
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		// 贸易国别
		this.cbbTradeCountry.setModel(CustomBaseModel.getInstance()
				.getCountryModel());
		this.initComboBoxRenderer(cbbTradeCountry);
		// 初始化币制
		this.cbbCurr.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.initComboBoxRenderer(cbbCurr);
		List wareSetList = this.materialManageAction.findWareSet(new Request(
				CommonVars.getCurrUser()));
		this.cbbWareSet
				.setModel(new DefaultComboBoxModel(wareSetList.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbWareSet,
				"code", "name", 250);
		this.cbbWareSet.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));
		// 初始化包装方式
		List listwarp = customBaseAction.findWrap();
		this.cbbWrapType.setModel(new DefaultComboBoxModel(listwarp.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbWrapType,
				"code", "name", 250);
		this.cbbWrapType.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		cbbWrapType.setSelectedIndex(-1);
		CompanyOther other = CommonVars.getOther();
		// 数量,单价,总金额
		if (other != null) {
			CustomFormattedTextFieldUtils.setFormatterFactory(this.tfQuantity,
					other.getCustomAmountNumSq());
			CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitPrice,
					other.getCustomPriceNumSq());
			CustomFormattedTextFieldUtils.setFormatterFactory(this.tfAmount,
					other.getCustomTotalNumSq());
		} else {
			CustomFormattedTextFieldUtils.setFormatterFactory(this.tfQuantity,
					9);
			CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitPrice,
					9);
			CustomFormattedTextFieldUtils.setFormatterFactory(this.tfAmount, 5);
		}
	}

	/**
	 * 初始化Cbb呈现
	 * 
	 * @param cbb
	 */
	private void initComboBoxRenderer(JComboBox cbb) {
		cbb.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbb);
		// cbb.setSelectedItem(null);
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("   上条   ");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (vaildatorDataIsNull()) {
						return;
					}
					if (!vaildatorDataOther()) {
						return;
					}
					saveData();
					rowCount--;
					impExpCommodityInfo = (ImpExpCommodityInfo) tableModel
							.getObjectByRow(rowCount);
					showData(impExpCommodityInfo);
					setstate();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("   下条   ");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (vaildatorDataIsNull()) {
						return;
					}
					if (!vaildatorDataOther()) {
						return;
					}
					saveData();
					rowCount++;
					impExpCommodityInfo = (ImpExpCommodityInfo) tableModel
							.getObjectByRow(rowCount);
					showData(impExpCommodityInfo);
					setstate();
				}
			});
		}
		return jButton1;
	}

	public void setstate() {
		tfBillNo.setEditable(dataState != DataState.BROWSE);
		tfAftername.setEditable(dataState != DataState.BROWSE);
		tfAfterspec.setEditable(dataState != DataState.BROWSE);
		tfAfterunit.setEditable(dataState != DataState.BROWSE);
		tfAmount.setEditable(dataState != DataState.BROWSE);
		tfBulks.setEditable(dataState != DataState.BROWSE);
		tfExtendMemo.setEditable(dataState != DataState.BROWSE);
		tfGrossWeight.setEditable(dataState != DataState.BROWSE);
		tfinvGrossWeight.setEditable(dataState != DataState.BROWSE);
		tfinvNetWeight.setEditable(dataState != DataState.BROWSE);
		tfMakeBillNo.setEditable(dataState != DataState.BROWSE);
		tfMaterielNo.setEditable(dataState != DataState.BROWSE);
		tfName.setEditable(dataState != DataState.BROWSE);
		tfNetWeight.setEditable(dataState != DataState.BROWSE);
		tfQuantity.setEditable(dataState != DataState.BROWSE);
		tfSetNum.setEditable(dataState != DataState.BROWSE);
		tfSpec.setEditable(dataState != DataState.BROWSE);
		tfUnit.setEditable(dataState != DataState.BROWSE);
		tfBoxNo.setEditable(dataState != DataState.BROWSE);

		if (impExpCommodityInfo != null
				&& impExpCommodityInfo.getMateriel().getScmCoi() != null
				&& impExpCommodityInfo.getMateriel().getScmCoi()
						.getCoiProperty().equals(MaterielType.FINISHED_PRODUCT)) {
			tfWorkUsd.setEditable(dataState != DataState.BROWSE);
			tfVersion.setEditable(dataState != DataState.BROWSE);
			tfCmpVersion.setEditable(dataState != DataState.BROWSE);
		} else {
			this.tfWorkUsd.setEditable(false);
			tfVersion.setEditable(false);
			tfCmpVersion.setEditable(false);
		}
		tfUnitPrice.setEditable(dataState != DataState.BROWSE);
		cbbCurr.setEnabled(dataState != DataState.BROWSE);
		cbbTradeCountry.setEnabled(dataState != DataState.BROWSE);
		cbbWareSet.setEnabled(dataState != DataState.BROWSE);
		tfPiece.setEditable(dataState != DataState.BROWSE);
		btnSave.setEnabled(dataState != DataState.BROWSE);
		btnCancel.setEnabled(dataState != DataState.BROWSE);
		jButton.setEnabled(rowCount > 0);
		jButton1.setEnabled(rowCount < totalCount - 1);
		tfStartBoxNo.setEditable(dataState != DataState.BROWSE);
		tfEndBoxNo.setEditable(dataState != DataState.BROWSE);
		tfTotalBoxNum.setEditable(dataState != DataState.BROWSE);
		cbbWrapType.setEnabled(dataState != DataState.BROWSE);
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWareSet() {
		if (cbbWareSet == null) {
			cbbWareSet = new JComboBox();
			cbbWareSet.setBounds(new Rectangle(329, 330, 151, 25));
		}
		return cbbWareSet;
	}

	/**
	 * This method initializes tfPiece
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPiece() {
		if (tfPiece == null) {
			tfPiece = new JTextField();
			tfPiece.setBounds(new Rectangle(94, 359, 151, 25));
			tfPiece.setText("0");
			tfPiece.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyReleased(java.awt.event.KeyEvent e) {
					if (CommonUtils.notEmpty(tfPiece.getText())) {
						tfTotalBoxNum.setText(tfPiece.getText());
					}
				}
			});
		}
		return tfPiece;
	}

	/**
	 * This method initializes decimalFormat1
	 * 
	 * @return java.text.DecimalFormat
	 */
	private DecimalFormat getDecimalFormat1() {
		if (decimalFormat1 == null) {
			decimalFormat1 = new DecimalFormat();
			decimalFormat1.setMaximumFractionDigits(999);
		}
		return decimalFormat1;
	}

	/**
	 * This method initializes tfMakeBillNo1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExtendMemo() {
		if (tfExtendMemo == null) {
			tfExtendMemo = new JTextField();
			tfExtendMemo.setBounds(new Rectangle(94, 387, 387, 25));
		}
		return tfExtendMemo;
	}

	public ImpExpCommodityInfo getImpExpCommodityInfo() {
		return impExpCommodityInfo;
	}

	public void setImpExpCommodityInfo(ImpExpCommodityInfo impExpCommodityInfo) {
		this.impExpCommodityInfo = impExpCommodityInfo;
	}

	/**
	 * This method initializes tfAftername
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAftername() {
		if (tfAftername == null) {
			tfAftername = new JTextField();
			tfAftername.setEditable(false);
			tfAftername.setBounds(new Rectangle(329, 72, 151, 25));
		}
		return tfAftername;
	}

	/**
	 * This method initializes tfAfterspec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAfterspec() {
		if (tfAfterspec == null) {
			tfAfterspec = new JTextField();
			tfAfterspec.setEditable(false);
			tfAfterspec.setBounds(new Rectangle(329, 100, 151, 25));
		}
		return tfAfterspec;
	}

	/**
	 * This method initializes tfAfterunit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAfterunit() {
		if (tfAfterunit == null) {
			tfAfterunit = new JTextField();
			tfAfterunit.setEditable(false);
			tfAfterunit.setBounds(new Rectangle(329, 128, 151, 25));
		}
		return tfAfterunit;
	}

	/**
	 * This method initializes tfSetNum
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSetNum() {
		if (tfSetNum == null) {
			tfSetNum = new JTextField();
			tfSetNum.setEditable(false);
			tfSetNum.setBounds(new Rectangle(329, 45, 151, 25));
		}
		return tfSetNum;
	}

	/**
	 * This method initializes tfinvGrossWeight
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JFormattedTextField getTfinvGrossWeight() {
		if (tfinvGrossWeight == null) {
			tfinvGrossWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfinvGrossWeight.setBounds(new Rectangle(94, 270, 151, 25));
			tfinvGrossWeight.setFormatterFactory(getDefaultFormatterFactory());
			tfinvGrossWeight.getDocument().addDocumentListener(
					new DocumentListenerAdapter());
		}
		return tfinvGrossWeight;
	}

	/**
	 * This method initializes decimalFormat2
	 * 
	 * @return java.text.DecimalFormat
	 */
	private DecimalFormat getDecimalFormat2() {
		if (decimalFormat2 == null) {
			decimalFormat2 = new DecimalFormat();
			decimalFormat2.setMaximumFractionDigits(999);
		}
		return decimalFormat2;
	}

	/**
	 * This method initializes tfinvNetWeight
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JFormattedTextField getTfinvNetWeight() {
		if (tfinvNetWeight == null) {
			tfinvNetWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfinvNetWeight.setBounds(new Rectangle(94, 243, 151, 25));
			tfinvNetWeight.setFormatterFactory(getDefaultFormatterFactory());
			tfinvNetWeight.getDocument().addDocumentListener(
					new DocumentListenerAdapter());
		}
		return tfinvNetWeight;
	}

	/**
	 * This method initializes decimalFormat3
	 * 
	 * @return java.text.DecimalFormat
	 */
	private DecimalFormat getDecimalFormat3() {
		if (decimalFormat3 == null) {
			decimalFormat3 = new DecimalFormat();
			decimalFormat3.setMaximumFractionDigits(999);
		}
		return decimalFormat3;
	}

	/**
	 * This method initializes tfBoxNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBoxNo() {
		if (tfBoxNo == null) {
			tfBoxNo = new JTextField();
			tfBoxNo.setBounds(new Rectangle(329, 359, 151, 25));
			tfBoxNo.setText("0-0");
		}
		return tfBoxNo;
	}

	/**
	 * This method initializes tfWorkUsd
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfWorkUsd() {
		if (tfWorkUsd == null) {
			tfWorkUsd = new JCustomFormattedTextField();
			tfWorkUsd.setBounds(new Rectangle(94, 215, 64, 25));
			CustomFormattedTextFieldUtils
					.setFormatterFactory(this.tfWorkUsd, 2);
		}
		return tfWorkUsd;
	}

	/**
	 * This method initializes tfCmpVersion
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCmpVersion() {
		if (tfCmpVersion == null) {
			tfCmpVersion = new JTextField();
			tfCmpVersion.setBounds(new Rectangle(198, 299, 44, 22));
		}
		return tfCmpVersion;
	}

	/**
	 * This method initializes tfStartBoxNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfStartBoxNo() {
		if (tfStartBoxNo == null) {
			tfStartBoxNo = new JTextField();
			// tfStartBoxNo.setText("0");
			tfStartBoxNo.setVisible(false);
			tfStartBoxNo.setBounds(new Rectangle(93, 418, 151, 25));
			tfStartBoxNo
					.addCaretListener(new javax.swing.event.CaretListener() {
						public void caretUpdate(javax.swing.event.CaretEvent e) {
							setBoxNo();
							if (!ImpExpType.isImportType(impExpCommodityInfo
									.getImpExpRequestBill().getBillType())) {
								setTotalBoxNum();
							}
						}
					});
		}
		return tfStartBoxNo;
	}

	/**
	 * This method initializes tfEndBoxNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEndBoxNo() {
		if (tfEndBoxNo == null) {
			tfEndBoxNo = new JTextField();
			tfEndBoxNo.setText("0");
			tfEndBoxNo.setVisible(false);
			tfEndBoxNo.setBounds(new Rectangle(329, 418, 151, 25));
			tfEndBoxNo.addCaretListener(new javax.swing.event.CaretListener() {
				public void caretUpdate(javax.swing.event.CaretEvent e) {
					setBoxNo();
					if (!ImpExpType.isImportType(impExpCommodityInfo
							.getImpExpRequestBill().getBillType())) {
						setTotalBoxNum();
					}
				}
			});
		}
		return tfEndBoxNo;
	}

	private void setBoxNo() {
		if (CommonUtils.isEmpty(tfStartBoxNo.getText())
				&& !CommonUtils.isEmpty(tfEndBoxNo.getText())) {
			tfBoxNo.setText(tfEndBoxNo.getText());
		} else if (CommonUtils.isEmpty(tfEndBoxNo.getText())
				&& !CommonUtils.isEmpty(tfStartBoxNo.getText())) {
			tfBoxNo.setText(tfStartBoxNo.getText());
		} else if (!CommonUtils.isEmpty(tfEndBoxNo.getText())
				&& !CommonUtils.isEmpty(tfStartBoxNo.getText())) {
			tfBoxNo.setText(tfStartBoxNo.getText() + "-" + tfEndBoxNo.getText());
		}
	}

	/**
	 * 出口申请单手工录入时，输入起始箱号和截止箱号，不能自动输出箱数，要求实现： 1、当申请单的起始箱号和截止箱号是纯数字时，启用公式 箱数=
	 * 截止箱号-起始箱号+1 2、当申请单的起始箱号和截止箱号是两位字母+6为数字的格式时， 使用公式 箱数=截止箱号后六位数字-起始箱号后六位数字+1
	 */
	private void setTotalBoxNum() {
		Integer endBoxNo = 0;
		Integer startBoxNo = 0;
		if (!"".equals(tfStartBoxNo.getText())
				&& !"".equals(tfEndBoxNo.getText())
				&& RegexUtil.checkNumber(tfStartBoxNo.getText().trim())
				&& RegexUtil.checkNumber(tfEndBoxNo.getText().trim())) {
			if (!tfStartBoxNo.getText().trim().equals("0")) {
				startBoxNo = Integer.valueOf(tfStartBoxNo.getText().trim()
						.replaceFirst("^(0+)", ""));
			} else {
				startBoxNo = Integer.valueOf(tfStartBoxNo.getText().trim());
			}
			if (!tfEndBoxNo.getText().trim().equals("0")) {
				endBoxNo = Integer.valueOf(tfEndBoxNo.getText().trim()
						.replaceFirst("^(0+)", ""));
			} else {
				endBoxNo = Integer.valueOf(tfEndBoxNo.getText());
			}
			tfTotalBoxNum.setText(String.valueOf(endBoxNo - startBoxNo + 1));
		} else if (!"".equals(tfStartBoxNo.getText())
				&& !"".equals(tfEndBoxNo.getText())
				&& RegexUtil.checkMatch(tfStartBoxNo.getText().trim(),
						"^[^0-9]{2}[0-9]{6}$")
				&& RegexUtil.checkMatch(tfEndBoxNo.getText().trim(),
						"^[^0-9]{2}[0-9]{6}$")) {
			String start = tfStartBoxNo.getText().trim()
					.substring(2, tfStartBoxNo.getText().trim().length());
			String end = tfEndBoxNo.getText().trim()
					.substring(2, tfEndBoxNo.getText().trim().length());
			startBoxNo = Integer.valueOf(start.replaceFirst("^(0+)", ""));
			endBoxNo = Integer.valueOf(end.replaceFirst("^(0+)", ""));
			tfTotalBoxNum.setText(String.valueOf(endBoxNo - startBoxNo + 1));
		}
	}

	/**
	 * This method initializes tfTotalBoxNum
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTotalBoxNum() {
		if (tfTotalBoxNum == null) {
			tfTotalBoxNum = new JTextField();
			tfTotalBoxNum.setText("0");
			tfTotalBoxNum.setVisible(false);
			tfTotalBoxNum.setBounds(new Rectangle(93, 446, 151, 25));
			tfTotalBoxNum
					.addCaretListener(new javax.swing.event.CaretListener() {
						public void caretUpdate(javax.swing.event.CaretEvent e) {
							System.out.println("caretUpdate");
							tfPiece.setText(tfTotalBoxNum.getText());
						}
					});

		}
		return tfTotalBoxNum;
	}

	/**
	 * This method initializes cbbWrapType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWrapType() {
		if (cbbWrapType == null) {
			cbbWrapType = new JComboBox();
			cbbWrapType.setBounds(new Rectangle(329, 446, 151, 25));
			cbbWrapType.setVisible(false);
		}
		return cbbWrapType;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
