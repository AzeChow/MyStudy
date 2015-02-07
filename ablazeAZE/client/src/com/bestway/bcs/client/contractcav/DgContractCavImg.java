package com.bestway.bcs.client.contractcav;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contractcav.action.ContractCavAction;
import com.bestway.bcs.contractcav.entity.ContractImgCav;
import com.bestway.bcus.client.common.AutoCalcListener;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Rectangle;
import java.awt.Dimension;

public class DgContractCavImg extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JTextField tfName = null;

	private JLabel jLabel1 = null;

	private JTextField tfSpec = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JCustomFormattedTextField tfimpTotal = null;

	private JCustomFormattedTextField tfremainImport = null;

	private JLabel jLabel6 = null;

	private JTextField tfUnit = null;

	private JButton btnEdit = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private JTableListModel tableModel = null;

	private ContractCavAction contractCavAction = null;

	private int dataState = DataState.BROWSE;

	private boolean isShowDataChange = false;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private ContractAction contractAction = null; // 合同
	
	private JCustomFormattedTextField tfleftoverMaterial = null;

	private JCustomFormattedTextField tfremainMaterial = null;

	private JCustomFormattedTextField tftransferFactoryImport = null;

	private JCustomFormattedTextField tfproductWaste = null;

	private JCustomFormattedTextField tfinternalAmount = null;

	private JCustomFormattedTextField tfbackExport = null;
	private boolean modifyProductUsedAmountWriteBack;
	private double oldValue = 0.0; // @jve:decl-index=0:

	private BcsParameterSet parameterSet = null;
	
	private JLabel jLabel91 = null;

	private JCustomFormattedTextField tfStockAmount = null;
	private JLabel label;
	private JCustomFormattedTextField tfDomesticPurchase;
	private JLabel lblkg;
	private JCustomFormattedTextField tfNetLossWeight;
	private JLabel label_1;
	private JTextField tfExplain;

	public boolean isModifyProductUsedAmountWriteBack() {
		return modifyProductUsedAmountWriteBack;
	}

	public void setModifyProductUsedAmountWriteBack(
			boolean modifyProductUsedAmountWriteBack) {
		this.modifyProductUsedAmountWriteBack = modifyProductUsedAmountWriteBack;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgContractCavImg() {
		super();
		
//		parameterSet = contractAction.findBcsParameterSet(new Request(
//				CommonVars.getCurrUser(), true));
		initialize();
		contractCavAction = (ContractCavAction) CommonVars
				.getApplicationContext().getBean("contractCavAction");
		// 这里是控制焦点的顺序，以方便键盘的输！
		/**
		 * 余料数量 ＝ 进口总数量1）－内销数量3）-退运出口数量（复出）－产品总耗用量2）
		 */
		
		List components = new ArrayList();
		components.add(this.tfimpTotal);
		components.add(null);
		components.add(this.tfremainMaterial);
		components.add(this.tfDomesticPurchase);
		components.add(this.tfNetLossWeight);
		components.add(this.tfExplain);
		components.add(this.btnSave);
		components.add(this.btnNext);
//		int countBit = parameterSet.getCountBit() == null ? 5 : parameterSet
//				.getCountBit();
		this.setComponentFocusList(components);
		// 进口总数
		CustomFormattedTextFieldUtils.addAutoCalcListener(tfimpTotal,
		new AutoCalcListener() 
		{
			public void run() 
			{
				tfremainMaterial.setValue(getRemainMaterial());
			}
		});
		CustomFormattedTextFieldUtils.addAutoCalcListener(tfinternalAmount,
				new AutoCalcListener() 
				{
					public void run() 
					{
						tfremainMaterial.setValue(getRemainMaterial());
					}
				});
		
		CustomFormattedTextFieldUtils.addAutoCalcListener(tfbackExport,
				new AutoCalcListener() 
				{
					public void run() 
					{
						tfremainMaterial.setValue(getRemainMaterial());
					}
				});
		CustomFormattedTextFieldUtils.addAutoCalcListener(tfproductWaste,
				new AutoCalcListener() 
				{
					public void run() 
					{
						tfremainMaterial.setValue(getRemainMaterial());
					}
				});
	}

	public void setVisible(boolean b) {
		if (b) {
			this.showData();
			this.setState();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(656, 393));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("料件核销修改");
		this.setContentPane(getJContentPane());

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
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
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnPrevious());
			jToolBar.add(getBtnNext());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setText("上一笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (dataState == DataState.EDIT) {
						if (!saveData()) {
							return;
						}
					} else {
						dataState = DataState.EDIT;
					}
					tableModel.previousRow();
					showData();
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
			btnNext.setText("下一笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (dataState == DataState.EDIT) {
						if (!saveData()) {
							return;
						}
					} else {
						dataState = DataState.EDIT;
					}
					tableModel.nextRow();
					showData();
					setState();
				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!saveData()) {
						return;
					}
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes jButton3
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
					setState();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel91 = new JLabel();
			jLabel91.setBounds(new Rectangle(328, 203, 86, 22));
			jLabel91.setText("库存数量");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new java.awt.Rectangle(28, 204, 62, 21));
			jLabel10.setText("余料");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(328, 166, 86, 18));
			jLabel9.setText("边角料");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new java.awt.Rectangle(28, 168, 62, 21));
			jLabel8.setText("退运出口");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(327, 130, 87, 18));
			jLabel7.setText("内销数量");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(28, 132, 62, 21));
			jLabel6.setText("成品耗用");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(327, 100, 87, 18));
			jLabel5.setText("余料进口");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(28, 100, 62, 21));
			jLabel4.setText("料件转厂");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(327, 68, 87, 18));
			jLabel3.setText("进口总数");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(28, 64, 62, 21));
			jLabel2.setText("计量单位");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(327, 36, 87, 18));
			jLabel1.setText("规格型号");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(28, 34, 62, 21));
			jLabel.setText("商品名称");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getTfName(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getTfSpec(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(getTfimpTotal(), null);

			jPanel.add(getTfremainImport(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(getTfUnit(), null);
			jPanel.add(jLabel7, null);
			jPanel.add(jLabel8, null);
			jPanel.add(jLabel9, null);
			jPanel.add(jLabel10, null);

			jPanel.add(getTftransferFactoryImport(), null);
			jPanel.add(getTfremainMaterial(), null);
			jPanel.add(getTfleftoverMaterial(), null);
			jPanel.add(getTfproductWaste(), null);
			jPanel.add(getTfinternalAmount(), null);
			jPanel.add(getTfbackExport(), null);
			jPanel.add(jLabel91, null);
			jPanel.add(getTfStockAmount(), null);
			jPanel.add(getTftransferFactoryImport(), null);
			jPanel.add(getLabel());
			jPanel.add(getTfDomesticPurchase());
			jPanel.add(getLblkg());
			jPanel.add(getTfNetLossWeight());
			jPanel.add(getLabel_1());
			jPanel.add(getTfExplain());
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(new Rectangle(118, 34, 170, 24));
			tfName.setEditable(false);
		}
		return tfName;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSpec() {
		if (tfSpec == null) {
			tfSpec = new JTextField();
			tfSpec.setBounds(new Rectangle(436, 34, 170, 23));
			tfSpec.setEditable(false);
		}
		return tfSpec;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfimpTotal() {
		if (tfimpTotal == null) {
			tfimpTotal = new JCustomFormattedTextField();
			tfimpTotal.setBounds(new Rectangle(436, 65, 170, 23));
			tfimpTotal.setEditable(true);
			tfimpTotal.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfimpTotal;
	}

	/**
	 * This method initializes productWaste
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	// private JCustomFormattedTextField gettransferFactoryImport() {
	// if (transferFactoryImport == null) {
	// transferFactoryImport = new JCustomFormattedTextField();
	// transferFactoryImport.setBounds(new java.awt.Rectangle(134,134,217,23));
	// // tfTransferFactoryAmount.getDocument().addDocumentListener(
	// // new DocumentListener() {
	// //
	// // public void insertUpdate(DocumentEvent e) {
	// //
	// // if(!isShowDataChange){
	// // try {
	// // tfTransferFactoryAmount.commitEdit();
	// // } catch (ParseException e1) {
	// // }
	// // calExpTotalAmount();
	// // }
	// // }
	// //
	// // public void removeUpdate(DocumentEvent e) {
	// //
	// // if(!isShowDataChange){
	// // try {
	// // tfTransferFactoryAmount.commitEdit();
	// // } catch (ParseException e1) {
	// // }
	// // calExpTotalAmount();
	// // }
	// //
	// // }
	// //
	// // public void changedUpdate(DocumentEvent e) {
	// //
	// // if(!isShowDataChange){
	// // try {
	// // tfTransferFactoryAmount.commitEdit();
	// // } catch (ParseException e1) {
	// // }
	// // calExpTotalAmount();
	// // }
	// // }
	// // });
	// }
	// return transferFactoryImport;
	// }
	/**
	 * This method initializes jCustomFormattedTextField2
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfremainImport() {
		if (tfremainImport == null) {
			tfremainImport = new JCustomFormattedTextField();
			tfremainImport.setBounds(new Rectangle(436, 97, 170, 24));
			tfremainImport.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfremainImport;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfUnit() {
		if (tfUnit == null) {
			tfUnit = new JTextField();
			tfUnit.setBounds(new Rectangle(118, 65, 170, 24));
			tfUnit.setEditable(false);
		}
		return tfUnit;
	}

	/**
	 * This method initializes jButton4
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
			DecimalFormat decimalFormat = new DecimalFormat(); // @jve:decl-index=0:
			decimalFormat.setMaximumFractionDigits(999);
			numberFormatter.setFormat(decimalFormat);
		}
		return numberFormatter;
	}

	private ContractImgCav getContractImgCav() {
		return (ContractImgCav) tableModel.getCurrentRow();
	}

	private void fillData(ContractImgCav imgCav) {
		if (imgCav == null) {
			return;
		}
		imgCav.setImpTotal(convertObjToDouble(this.tfimpTotal.getValue()));
		imgCav
				.setTransferFactoryImport(convertObjToDouble(this.tftransferFactoryImport
						.getValue()));
		imgCav.setRemainImport(convertObjToDouble(this.tfremainImport
				.getValue()));
		imgCav.setProductWaste(convertObjToDouble(this.tfproductWaste
				.getValue()));
		imgCav.setInternalAmount(convertObjToDouble(this.tfinternalAmount
				.getValue()));
		imgCav.setBackExport(convertObjToDouble(this.tfbackExport.getValue()));
		imgCav.setLeftoverMaterial(convertObjToDouble(this.tfleftoverMaterial
				.getValue()));
		/**
		 * 余料数量 ＝ 进口总数量1）－内销数量3）-退运出口数量（复出）－产品总耗用量2）
		 */
		//Double mainMaterial=Double.valueOf((String) this.tfremainMaterial.getValue())
		imgCav.setRemainMaterial(convertObjToDouble(this.tfremainMaterial
				.getValue()));
		imgCav.setDomesticPurchase(convertObjToDouble(this.tfDomesticPurchase.getValue()));
		imgCav.setNetLossWeight(convertObjToDouble(this.tfNetLossWeight.getValue()));
		imgCav.setExplain(this.tfExplain.getText());
		imgCav.setStockAmount(convertObjToDouble(this.tfStockAmount
				.getValue()));
	}

	private double getDoubleExceptNull(Double num) {
		if (num == null) {
			return 0.0;
		}
		return num;
	}

	private double convertObjToDouble(Object obj) {
		if (obj == null) {
			return 0.0;
		}
		return Double.parseDouble(obj.toString());
	}

	private void calImgTotalAmount() {
		// ContractImgCav imgCav = this.getContractImgCav();
		// double directImport = getDoubleExceptNull(imgCav.getDirectImport());
		// double exchangeExport =
		// getDoubleExceptNull(imgCav.getExchangeExport());
		// // 总进口数量=料件进口数量+转厂进口数量-料件退换数量
		// this.tfimpTotal.setValue(CommonVars.getDoubleByDigit(directImport
		// + convertObjToDouble(this.tftransferFactoryImport.getValue())
		// - exchangeExport, 3));
		// // 余料数量=进口总数量-内销数量-产品耗用量
		// this.tfremainMaterial.setValue(CommonVars
		// .getDoubleByDigit(
		// convertObjToDouble(this.tfimpTotal.getValue())
		// - convertObjToDouble(this.tfinternalAmount
		// .getValue())
		// - convertObjToDouble(this.tfproductWaste
		// .getValue()), 3));
	}

	private void showData() {

		isShowDataChange = true;
		ContractImgCav imgCav = this.getContractImgCav();
		oldValue = imgCav.getProductWaste() == null ? 0.0 : imgCav
				.getProductWaste();
		if (imgCav != null) {
			this.tfName.setText(imgCav.getName());
			this.tfSpec.setText(imgCav.getSpec());
			this.tfUnit.setText(imgCav.getUnit() == null ? "" : imgCav
					.getUnit().getName());
			this.tfimpTotal.setValue(imgCav.getImpTotal());
			this.tftransferFactoryImport.setValue(imgCav
					.getTransferFactoryImport());
			this.tfremainImport.setValue(imgCav.getRemainImport());
			this.tfproductWaste.setValue(imgCav.getProductWaste());
			this.tfinternalAmount.setValue(imgCav.getInternalAmount());
			this.tfbackExport.setValue(imgCav.getBackExport());
			this.tfleftoverMaterial.setValue(imgCav.getLeftoverMaterial());
			this.tfremainMaterial.setValue(imgCav.getRemainMaterial());
			this.tfStockAmount.setValue(imgCav.getStockAmount());
			this.tfDomesticPurchase.setValue(imgCav.getDomesticPurchase());
			this.tfExplain.setText(imgCav.getExplain());
			this.tfNetLossWeight.setValue(imgCav.getNetLossWeight());
		} else {
			this.tfName.setText("");
			this.tfSpec.setText("");
			this.tfUnit.setText("");
			this.tfimpTotal.setValue(null);
			this.tftransferFactoryImport.setText(null);
			this.tfremainImport.setValue(null);
			this.tfproductWaste.setValue(null);
			this.tfinternalAmount.setValue(null);
			this.tfbackExport.setValue(null);
			this.tfleftoverMaterial.setValue(null);
			this.tfremainMaterial.setValue(null);
			this.tfDomesticPurchase.setValue(null);
			this.tfNetLossWeight.setValue(null);
			this.tfExplain.setText("");
			this.tfStockAmount.setValue(null);
		}
		isShowDataChange = false;
	}

	private void setState() {
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
		this.btnCancel.setEnabled(dataState != DataState.BROWSE);
		this.btnEdit.setEnabled(dataState == DataState.BROWSE);
		this.btnPrevious.setEnabled(tableModel.hasPreviousRow());
		this.btnNext.setEnabled(tableModel.hasNextRow());
		this.tftransferFactoryImport.setEditable(dataState != DataState.BROWSE);
		this.tfremainImport.setEditable(dataState != DataState.BROWSE);
		this.tfproductWaste.setEditable(dataState != DataState.BROWSE);
		this.tfinternalAmount.setEditable(dataState != DataState.BROWSE);
		this.tfbackExport.setEditable(dataState != DataState.BROWSE);
		this.tfleftoverMaterial.setEditable(dataState != DataState.BROWSE);
		this.tfremainMaterial.setEditable(dataState != DataState.BROWSE);
		this.tfDomesticPurchase.setEditable(dataState != DataState.BROWSE);
		this.tfNetLossWeight.setEditable(dataState != DataState.BROWSE);
		this.tfExplain.setEditable(dataState != DataState.BROWSE);
		this.tfimpTotal.setEditable(dataState != DataState.BROWSE);
		this.tfStockAmount.setEditable(dataState != DataState.BROWSE);
	}
	/**
	 * 余料数量 ＝ 进口总数量1）－内销数量3）-退运出口数量（复出）－产品总耗用量2）
	 */
	private boolean saveData() {
		ContractImgCav imgCav = this.getContractImgCav();
		this.fillData(imgCav);
		List list = new ArrayList();
		boolean isShow = false;
		if (oldValue == imgCav.getProductWaste()) {
			isShow = false;
		} else {
			isShow = true;
			;
		}
		if (this.modifyProductUsedAmountWriteBack && isShow) {
			DgModifyProuctUsedWriteBack dg = new DgModifyProuctUsedWriteBack();
			dg.setImgCav(imgCav);
			dg.setVisible(true);
			if (dg.isOk()) {
				list = dg.getLsResult();
			} else {
				return false;
			}
		}
		imgCav = this.contractCavAction.saveContractImgCav(new Request(
				CommonVars.getCurrUser()), imgCav, list,
				this.modifyProductUsedAmountWriteBack);
		tableModel.updateRow(imgCav);
		return true;
	}

	/**
	 * This method initializes jCustomFormattedTextField4
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfremainMaterial() {
		if (tfremainMaterial == null) {
			tfremainMaterial = new JCustomFormattedTextField();
			tfremainMaterial
					.setBounds(new Rectangle(118, 203, 170, 24));
			tfremainMaterial.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfremainMaterial;
	}

	/**
	 * This method initializes transferFactoryImport
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTftransferFactoryImport() {
		if (tftransferFactoryImport == null) {
			tftransferFactoryImport = new JCustomFormattedTextField();
			tftransferFactoryImport.setBounds(new Rectangle(118, 98, 170, 24));
			tftransferFactoryImport
					.setFormatterFactory(getDefaultFormatterFactory());
			tftransferFactoryImport.getDocument().addDocumentListener(
					new DocumentListener() {
						public void insertUpdate(DocumentEvent e) {
							if (isShowDataChange) {
								return;
							}
							try {
								tftransferFactoryImport.commitEdit();
							} catch (ParseException e1) {
							}
							calImgTotalAmount();
						}

						public void removeUpdate(DocumentEvent e) {
							if (isShowDataChange) {
								return;
							}
							try {
								tftransferFactoryImport.commitEdit();
							} catch (ParseException e1) {
							}
							calImgTotalAmount();
						}

						public void changedUpdate(DocumentEvent e) {
							if (isShowDataChange) {
								return;
							}
							try {
								tftransferFactoryImport.commitEdit();
							} catch (ParseException e1) {
							}
							calImgTotalAmount();
						}
					});
		}
		return tftransferFactoryImport;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfleftoverMaterial() {
		if (tfleftoverMaterial == null) {
			tfleftoverMaterial = new JCustomFormattedTextField();
			tfleftoverMaterial.setBounds(new Rectangle(436, 163, 170, 24));
			tfleftoverMaterial
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfleftoverMaterial;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfproductWaste() {
		if (tfproductWaste == null) {
			tfproductWaste = new JCustomFormattedTextField();
			tfproductWaste.setBounds(new Rectangle(118, 131, 170, 24));
			tfproductWaste.setFormatterFactory(getDefaultFormatterFactory());
			tfproductWaste.getDocument().addDocumentListener(
					new DocumentListener() {
						public void insertUpdate(DocumentEvent e) {
							if (isShowDataChange) {
								return;
							}
							try {
								tfproductWaste.commitEdit();
							} catch (ParseException e1) {
							}
							calImgTotalAmount();
						}

						public void removeUpdate(DocumentEvent e) {
							if (isShowDataChange) {
								return;
							}
							try {
								tfproductWaste.commitEdit();
							} catch (ParseException e1) {
							}
							calImgTotalAmount();
						}

						public void changedUpdate(DocumentEvent e) {
							if (isShowDataChange) {
								return;
							}
							try {
								tfproductWaste.commitEdit();
							} catch (ParseException e1) {
							}
							calImgTotalAmount();
						}
					});
		}
		return tfproductWaste;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfinternalAmount() {
		if (tfinternalAmount == null) {
			tfinternalAmount = new JCustomFormattedTextField();
			tfinternalAmount
					.setBounds(new Rectangle(436, 129, 170, 24));
			tfinternalAmount.setFormatterFactory(getDefaultFormatterFactory());
			tfinternalAmount.getDocument().addDocumentListener(
					new DocumentListener() {
						public void insertUpdate(DocumentEvent e) {
							if (isShowDataChange) {
								return;
							}
							try {
								tfinternalAmount.commitEdit();
							} catch (ParseException e1) {
							}
							calImgTotalAmount();
						}

						public void removeUpdate(DocumentEvent e) {
							if (isShowDataChange) {
								return;
							}
							try {
								tfinternalAmount.commitEdit();
							} catch (ParseException e1) {
							}
							calImgTotalAmount();
						}

						public void changedUpdate(DocumentEvent e) {
							if (isShowDataChange) {
								return;
							}
							try {
								tfinternalAmount.commitEdit();
							} catch (ParseException e1) {
							}
							calImgTotalAmount();
						}
					});
		}
		return tfinternalAmount;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfbackExport() {
		if (tfbackExport == null) {
			tfbackExport = new JCustomFormattedTextField();
			tfbackExport.setBounds(new Rectangle(118, 167, 170, 24));
			tfbackExport.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfbackExport;
	}

	/**
	 * This method initializes tfleftoverMaterial1	
	 * 	
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField	
	 */
	private JCustomFormattedTextField getTfStockAmount() {
		if (tfStockAmount == null) {
			DecimalFormat decimalFormat1 = new DecimalFormat();
			decimalFormat1.setMaximumFractionDigits(999);
			NumberFormatter numberFormatter1 = new NumberFormatter();
			numberFormatter1.setFormat(decimalFormat1);
			DefaultFormatterFactory defaultFormatterFactory1 = new DefaultFormatterFactory();
			defaultFormatterFactory1.setNullFormatter(numberFormatter1);
			defaultFormatterFactory1.setEditFormatter(numberFormatter1);
			defaultFormatterFactory1.setDisplayFormatter(numberFormatter1);
			defaultFormatterFactory1.setDefaultFormatter(numberFormatter1);
			tfStockAmount = new JCustomFormattedTextField();
			tfStockAmount.setBounds(new Rectangle(436, 201, 170, 24));
			tfStockAmount.setFormatterFactory(defaultFormatterFactory1);
		}
		return tfStockAmount;
	}
	
	 //余料数量 ＝ 进口总数量1）－内销数量3）-退运出口数量（复出）－产品总耗用量2） -（余料结转出口 EDIT BY石小凯）·
	
	private double getRemainMaterial() 
	{
		ContractImgCav imgCav = this.getContractImgCav();
		try 
		{
			double remainMaterial = 
				Double.parseDouble(this.tfimpTotal.getValue() == null ? "0"
							: this.tfimpTotal.getValue().toString())
					- Double.parseDouble(tfinternalAmount.getValue() == null ? "0"
							: tfinternalAmount.getValue().toString())
							-Double.parseDouble(tfbackExport.getValue() == null ? "0"
									: tfbackExport.getValue().toString())
									-Double.parseDouble(tfproductWaste.getValue() == null ? "0"
											: tfproductWaste.getValue().toString())
											- imgCav.getRemainExport();
			return remainMaterial;
		} 
		catch(Exception ex) 
		{
		}
		return 0;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel();
			label.setText("内购数量");
			label.setBounds(new Rectangle(28, 204, 62, 21));
			label.setBounds(28, 234, 87, 21);
		}
		return label;
	}
	private JCustomFormattedTextField getTfDomesticPurchase() {
		if (tfDomesticPurchase == null) {
			tfDomesticPurchase = new JCustomFormattedTextField();
			tfDomesticPurchase.setBounds(118, 233, 170, 24);
			tfDomesticPurchase.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfDomesticPurchase;
	}
	private JLabel getLblkg() {
		if (lblkg == null) {
			lblkg = new JLabel();
			lblkg.setText("净耗重量(KG)");
			lblkg.setBounds(new Rectangle(278, 204, 62, 22));
			lblkg.setBounds(328, 233, 109, 22);
		}
		return lblkg;
	}
	private JCustomFormattedTextField getTfNetLossWeight() {
		if (tfNetLossWeight == null) {
			tfNetLossWeight = new JCustomFormattedTextField();
			tfNetLossWeight.setBounds(436, 231, 170, 24);
			tfNetLossWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfNetLossWeight;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel();
			label_1.setText("说明原因");
			label_1.setBounds(new Rectangle(28, 204, 62, 21));
			label_1.setBounds(28, 266, 87, 21);
		}
		return label_1;
	}
	private JTextField getTfExplain() {
		if (tfExplain == null) {
			tfExplain = new JTextField();
			tfExplain.setBounds(new Rectangle(118, 34, 170, 24));
			tfExplain.setBounds(118, 267, 170, 24);
		}
		return tfExplain;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
