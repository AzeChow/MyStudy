package com.bestway.dzsc.client.checkcancel;

import java.awt.BorderLayout;
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

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.dzsc.checkcancel.action.DzscContractCavAction;
import com.bestway.dzsc.checkcancel.entity.DzscContractImgCav;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Rectangle;

public class DgDzscContractCavImg extends JDialogBase {

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

	private DzscContractCavAction contractCavAction = null;

	private int dataState = DataState.BROWSE;

	private boolean isShowDataChange = false;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JCustomFormattedTextField tfleftoverMaterial = null;

	private JCustomFormattedTextField tfremainMaterial = null;

	private JCustomFormattedTextField tftransferFactoryImport = null;

	private JCustomFormattedTextField tfproductWaste = null;

	private JCustomFormattedTextField tfinternalAmount = null;

	private JCustomFormattedTextField tfbackExport = null;

	private boolean modifyProductUsedAmountWriteBack;

	private JLabel jLabel101 = null;

	private JCustomFormattedTextField tfStockAmount = null;

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
	public DgDzscContractCavImg() {
		super();
		initialize();
		contractCavAction = (DzscContractCavAction) CommonVars
				.getApplicationContext().getBean("dzscContractCavAction");
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
		this.setSize(new java.awt.Dimension(552, 332));
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
						if (!saveData()){
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
						if (!saveData()){
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
			jLabel101 = new JLabel();
			jLabel101.setBounds(new Rectangle(278, 201, 62, 18));
			jLabel101.setText("库存数量");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new java.awt.Rectangle(28, 204, 62, 21));
			jLabel10.setText("余料");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new java.awt.Rectangle(278, 167, 62, 18));
			jLabel9.setText("边角料");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new java.awt.Rectangle(28, 168, 62, 21));
			jLabel8.setText("退运出口");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(277, 131, 62, 18));
			jLabel7.setText("内销数量");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(28, 132, 62, 21));
			jLabel6.setText("成品耗用");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(277, 101, 62, 18));
			jLabel5.setText("余料进口");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(28, 100, 62, 21));
			jLabel4.setText("料件转厂");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(277, 69, 62, 18));
			jLabel3.setText("进口总数");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(28, 64, 62, 21));
			jLabel2.setText("计量单位");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(277, 37, 62, 18));
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
			jPanel.add(jLabel101, null);
			jPanel.add(getTfStockAmount(), null);
			jPanel.add(getTftransferFactoryImport(), null);
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
			tfName.setBounds(new java.awt.Rectangle(90, 34, 170, 24));
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
			tfSpec.setBounds(new java.awt.Rectangle(340, 36, 170, 23));
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
			tfimpTotal.setBounds(new java.awt.Rectangle(340, 67, 170, 23));
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
			tfremainImport.setBounds(new java.awt.Rectangle(340, 99, 170, 24));
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
			tfUnit.setBounds(new java.awt.Rectangle(90, 65, 170, 24));
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

	private DzscContractImgCav getContractImgCav() {
		return (DzscContractImgCav) tableModel.getCurrentRow();
	}

	private void fillData(DzscContractImgCav imgCav) {
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
		imgCav.setRemainMaterial(convertObjToDouble(this.tfremainMaterial
				.getValue()));
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
//		ContractImgCav imgCav = this.getContractImgCav();
//		double directImport = getDoubleExceptNull(imgCav.getDirectImport());
//		double exchangeExport = getDoubleExceptNull(imgCav.getExchangeExport());
//		// 总进口数量=料件进口数量+转厂进口数量-料件退换数量
//		this.tfimpTotal.setValue(CommonVars.getDoubleByDigit(directImport
//				+ convertObjToDouble(this.tftransferFactoryImport.getValue())
//				- exchangeExport, 3));
//		// 余料数量=进口总数量-内销数量-产品耗用量
//		this.tfremainMaterial.setValue(CommonVars
//				.getDoubleByDigit(
//						convertObjToDouble(this.tfimpTotal.getValue())
//								- convertObjToDouble(this.tfinternalAmount
//										.getValue())
//								- convertObjToDouble(this.tfproductWaste
//										.getValue()), 3));
	}

	private void showData() {
		isShowDataChange = true;
		DzscContractImgCav imgCav = this.getContractImgCav();
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
		this.tfStockAmount.setEditable(dataState != DataState.BROWSE);
	}

	private boolean saveData() {
		DzscContractImgCav imgCav = this.getContractImgCav();
		this.fillData(imgCav);
		List list = new ArrayList();
		if (this.modifyProductUsedAmountWriteBack) {
			DgDzscModifyProuctUsedWriteBack dg = new DgDzscModifyProuctUsedWriteBack();
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
					.setBounds(new java.awt.Rectangle(90, 203, 170, 24));
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
			tftransferFactoryImport.setBounds(new java.awt.Rectangle(90, 98,
					170, 24));
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
			tfleftoverMaterial.setBounds(new java.awt.Rectangle(340, 165, 170,
					24));
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
			tfproductWaste.setBounds(new java.awt.Rectangle(90, 131, 170, 24));
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
					.setBounds(new java.awt.Rectangle(340, 131, 170, 24));
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
			tfbackExport.setBounds(new java.awt.Rectangle(90, 167, 170, 24));
			tfbackExport.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfbackExport;
	}

	/**
	 * This method initializes tfremainMaterial1	
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
			tfStockAmount.setBounds(new Rectangle(340, 200, 170, 24));
			tfStockAmount.setFormatterFactory(defaultFormatterFactory1);
		}
		return tfStockAmount;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
