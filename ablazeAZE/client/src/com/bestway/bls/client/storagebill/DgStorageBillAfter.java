/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.client.storagebill;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.AutoCalcListener;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bls.action.StorageBillAction;
import com.bestway.bls.entity.StorageBill;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.Dimension;

/**
 * 仓单位管理-归并后主界面 checked by kcb 2009-1-10
 * 
 * @author kangbo
 * 
 */
public class DgStorageBillAfter extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JToolBar jToolBar = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private JTextField tfCopGNo = null;

	private JTextField tfComplexName = null;

	private JTextField tfSecondLegalUnit = null;

	private JCustomFormattedTextField tfEmsSerialNo = null;

	private JTextField tfComplexCode = null;

	private JTextField tfComplexSpec = null;

	private JTextField tfLegalUnit = null;

	private JComboBox cbbCurrency = null;

	private JFormattedTextField tfDeclaredAmount = null;

	private JFormattedTextField tfSecondLegalAmount = null;

	private JFormattedTextField tfDeclaredPrice = null;

	private JFormattedTextField tfLegalAmount = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private DefaultFormatterFactory fourPointdefaultFormatterFactory = null; // @jve:decl-index=0:

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:
	private NumberFormatter integerFormatter = null; // @jve:decl-index=0:

	private NumberFormatter fourPointNumberFormatter = null; // @jve:decl-index=0:

	private JTableListModel fathertableModel;
	public int inOutFlag = 0;

	// private AtcMergeBeforeComInfo beforeCommInfo = null; //
	// @jve:decl-index=0:

	// private EncAction encAction = null;

	public JTableListModel getFathertableModel() {
		return fathertableModel;
	}

	public void setFathertableModel(JTableListModel fathertableModel) {
		this.fathertableModel = fathertableModel;
	}

	private boolean isOk = false;

	private boolean isMakeCustomsDeclaration = false;

	private JLabel jLabel29 = null;

	private JTextField tfSeqNo = null;

	private JLabel jLabel31 = null;

	private JComboBox cbbCountry = null;

	private JFormattedTextField tfTotalPrice = null;

	private JButton btnPrevious = null;// 查看上一笔记录

	private JButton btnNext = null;// 查看下一笔记录

	private StorageBillAfter storageBillAfter = null; // @jve:decl-index=0:

	public StorageBillAfter getStorageBillAfter() {
		return storageBillAfter;
	}

	public void setStorageBillAfter(StorageBillAfter storageBillAfter) {
		this.storageBillAfter = storageBillAfter;
	}

	private StorageBillAction storageBillAction = null;
	protected CustomBaseAction customBaseAction = null;

	private JLabel jLabel17 = null;

	private JTextField tfTafName = null;

	private JLabel jLabel18 = null;

	private JLabel jLabel19 = null;

	private JLabel jLabel27 = null;

	private JCalendarComboBox jCalendarComboBox = null;

	private JLabel jLabel28 = null;

	private JTextField tfEntryID = null;

	private JLabel jLabel32 = null;

	private JCustomFormattedTextField tfEntryGNo = null;

	private JLabel jLabel33 = null;

	private JTextField tfCorrBillNo = null;

	private JComboBox cbbTradeMode = null;

	private JComboBox cbbTransac = null;

	private JLabel jLabel34 = null;

	private JCustomFormattedTextField tfCorrBillGNo = null;

	private JComboBox cbbUnit = null;
	private int dataState = DataState.ADD;

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	private JButton btnEdit = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JButton btnPrevious1 = null;

	private JButton btnNext1 = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgStorageBillAfter(JTableListModel tableModel) {
		super();
		this.fathertableModel = tableModel;
		initialize();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		storageBillAction = (StorageBillAction) CommonVars
				.getApplicationContext().getBean("storageBillAction");
		initUIComponents();
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgStorageBillAfter() {
		super();
		initialize();
		initUIComponents();
	}

	@Override
	public void setVisible(boolean f) {
		if (f) {
			showData();
			setState();
			if (inOutFlag == 0) {
				jLabel33.setVisible(false);
				tfCorrBillNo.setVisible(false);
				jLabel34.setVisible(false);
				tfCorrBillGNo.setVisible(false);
			}
			super.setVisible(f);
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("仓单归并后商品信息编辑");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setSize(643, 402);

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				if (fathertableModel.getCurrentRow() != null) {
					// beforeCommInfo = (AtcMergeBeforeComInfo) fathertableModel
					// .getCurrentRow();
					showData();
					setState();
				}
			}
		});
	}

	private void setState() {
		boolean ef = this.storageBillAfter.getStorageBill().getEffective() == null ? false
				: this.storageBillAfter.getStorageBill().getEffective();
		cbbTradeMode.setEnabled(dataState != DataState.BROWSE);
		tfCopGNo.setEditable(dataState != DataState.BROWSE);
		cbbTransac.setEnabled(dataState != DataState.BROWSE);

		cbbCurrency.setEnabled(dataState != DataState.BROWSE);
		cbbUnit.setEnabled(dataState != DataState.BROWSE);

		tfDeclaredAmount.setEditable(dataState != DataState.BROWSE);
		tfLegalAmount.setEditable(dataState != DataState.BROWSE
				&& tfLegalUnit.getText() != null
				&& !tfLegalUnit.getText().trim().equals(""));
		tfSecondLegalAmount.setEditable(dataState != DataState.BROWSE
				&& tfSecondLegalUnit.getText() != null
				&& !tfSecondLegalUnit.getText().trim().equals(""));
		tfDeclaredPrice.setEditable(dataState != DataState.BROWSE);
		tfTotalPrice.setEditable(dataState != DataState.BROWSE);
		cbbCountry.setEnabled(dataState != DataState.BROWSE);
		tfTafName.setEditable(dataState != DataState.BROWSE);
		tfEntryID.setEditable(dataState != DataState.BROWSE);
		tfEmsSerialNo.setEditable(dataState != DataState.BROWSE);
		jCalendarComboBox.setEnabled(dataState != DataState.BROWSE);
		tfEntryGNo.setEditable(dataState != DataState.BROWSE);
		// jTextField5.setEditable(dataState != DataState.BROWSE);
		// jTextField6.setEditable(dataState != DataState.BROWSE);
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
		this.btnEdit.setEnabled(dataState == DataState.BROWSE);
		tfComplexName.setEditable(dataState != DataState.BROWSE);
		tfComplexSpec.setEditable(dataState != DataState.BROWSE);
		btnPrevious1.setEnabled(fathertableModel.hasPreviousRow());
		btnNext1.setEnabled(fathertableModel.hasNextRow());
		btnEdit.setEnabled(dataState == DataState.BROWSE && !ef);
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
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel34 = new JLabel();
			jLabel34.setBounds(new Rectangle(355, 305, 90, 25));
			jLabel34.setText("对应入仓单序号");
			jLabel33 = new JLabel();
			jLabel33.setBounds(new Rectangle(58, 309, 98, 25));
			jLabel33.setText("入仓单号");
			jLabel32 = new JLabel();
			jLabel32.setBounds(new Rectangle(354, 279, 90, 25));
			jLabel32.setText("报关单序号");
			jLabel28 = new JLabel();
			jLabel28.setBounds(new Rectangle(58, 256, 98, 25));
			jLabel28.setText("报关单号");
			jLabel27 = new JLabel();
			jLabel27.setBounds(new Rectangle(58, 279, 98, 25));
			jLabel27.setText("申报日期");
			jLabel27.setForeground(Color.blue);
			jLabel19 = new JLabel();
			jLabel19.setBounds(new Rectangle(355, 44, 90, 25));
			jLabel19.setText("成交方式");
			jLabel18 = new JLabel();
			jLabel18.setBounds(new Rectangle(355, 17, 90, 25));
			jLabel18.setText("贸易方式");
			jLabel17 = new JLabel();
			jLabel17.setBounds(new Rectangle(355, 229, 90, 25));
			jLabel17.setText("载货清单");
			jLabel31 = new JLabel();
			jLabel31.setBounds(new Rectangle(355, 203, 90, 25));
			// jLabel31.setForeground(java.awt.Color.blue);
			jLabel31.setForeground(Color.blue);
			jLabel31.setText("总价");
			jLabel29 = new JLabel();
			jLabel29.setBounds(new Rectangle(58, 21, 98, 25));
			// jLabel29.setForeground(java.awt.Color.blue);
			jLabel29.setForeground(Color.blue);
			jLabel29.setText("商品序号");
			jPanel = new JPanel();
			javax.swing.JLabel jLabel10 = new JLabel();
			javax.swing.JLabel jLabel11 = new JLabel();
			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel13 = new JLabel();
			javax.swing.JLabel jLabel14 = new JLabel();
			javax.swing.JLabel jLabel15 = new JLabel();
			javax.swing.JLabel jLabel16 = new JLabel();
			javax.swing.JLabel jLabel20 = new JLabel();
			javax.swing.JLabel jLabel21 = new JLabel();
			javax.swing.JLabel jLabel22 = new JLabel();
			javax.swing.JLabel jLabel23 = new JLabel();
			javax.swing.JLabel jLabel24 = new JLabel();
			javax.swing.JLabel jLabel25 = new JLabel();
			javax.swing.JLabel jLabel26 = new JLabel();
			jPanel.setLayout(null);
			jLabel10.setBounds(58, 46, 98, 25);
			// jLabel10.setForeground(java.awt.Color.blue);
			jLabel10.setText("企业内部编号");
			jLabel11.setBounds(355, 73, 90, 25);
			// jLabel11.setForeground(java.awt.Color.blue);
			jLabel11.setForeground(Color.blue);
			jLabel11.setText("商品名称");
			jLabel12.setBounds(58, 126, 98, 25);
			// jLabel12.setForeground(java.awt.Color.blue);
			jLabel12.setForeground(Color.blue);
			jLabel12.setText("申报单位");
			jLabel13.setBounds(355, 126, 90, 25);
			// jLabel13.setForeground(java.awt.Color.blue);
			jLabel13.setForeground(Color.blue);
			jLabel13.setText("申报数量");
			jLabel14.setBounds(58, 176, 98, 25);
			// jLabel14.setForeground(java.awt.Color.blue);
			jLabel14.setText("第二法定单位");
			jLabel15.setBounds(355, 176, 90, 25);
			// jLabel15.setForeground(java.awt.Color.blue);
			jLabel15.setText("第二法定数量");
			jLabel16.setBounds(58, 230, 98, 25);
			// jLabel16.setForeground(java.awt.Color.blue);
			jLabel16.setText("产销国（地区）");
			jLabel20.setBounds(355, 252, 90, 25);
			// jLabel20.setForeground(java.awt.Color.blue);
			jLabel20.setText("帐册序号");
			jLabel20.setForeground(Color.blue);
			jLabel21.setBounds(58, 73, 98, 25);
			// jLabel21.setForeground(java.awt.Color.blue);
			jLabel21.setForeground(Color.blue);
			jLabel21.setText("商品编码");
			jLabel22.setBounds(355, 100, 90, 25);
			// jLabel22.setForeground(java.awt.Color.blue);
			// jLabel22.setForeground(Color.blue);
			jLabel22.setText("商品规格");
			jLabel23.setBounds(58, 203, 98, 25);
			// jLabel23.setForeground(java.awt.Color.blue);
			jLabel23.setForeground(Color.blue);
			jLabel23.setText("企业申报单价");
			jLabel24.setBounds(58, 150, 98, 25);
			// jLabel24.setForeground(java.awt.Color.blue);
			jLabel24.setText("第一法定单位");
			jLabel25.setBounds(355, 150, 90, 25);
			// jLabel25.setForeground(java.awt.Color.blue);
			jLabel25.setText("第一法定数量");
			jLabel26.setBounds(58, 100, 98, 25);
			// jLabel26.setForeground(java.awt.Color.blue);
			jLabel26.setForeground(Color.blue);
			jLabel26.setText("币制");
			jPanel.add(jLabel10, null);
			jPanel.add(jLabel11, null);
			jPanel.add(jLabel12, null);
			jPanel.add(jLabel13, null);
			jPanel.add(jLabel14, null);
			jPanel.add(jLabel15, null);
			jPanel.add(jLabel16, null);
			jPanel.add(jLabel20, null);
			jPanel.add(jLabel21, null);
			jPanel.add(jLabel22, null);
			jPanel.add(jLabel23, null);
			jPanel.add(jLabel24, null);
			jPanel.add(jLabel25, null);
			jPanel.add(jLabel26, null);
			jPanel.add(getTfMaterielCode(), null);
			jPanel.add(getTfComplexName(), null);
			jPanel.add(getTfSecondLegalUnit(), null);
			jPanel.add(getTfEmsSerialNo(), null);
			jPanel.add(getTfComplexCode(), null);
			jPanel.add(getTfComplexSpec(), null);
			jPanel.add(getTfLegalUnit(), null);
			jPanel.add(getCbbCurrency(), null);
			jPanel.add(getTfDeclaredAmount(), null);
			jPanel.add(getTfSecondLegalAmount(), null);
			jPanel.add(getTfDeclaredPrice(), null);
			jPanel.add(getTfLegalAmount(), null);
			jPanel.add(jLabel29, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(jLabel31, null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(getTfTotalPrice(), null);
			jPanel.add(jLabel17, null);
			jPanel.add(getJTextField2(), null);
			jPanel.add(jLabel18, null);
			jPanel.add(jLabel19, null);
			jPanel.add(jLabel27, null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(jLabel28, null);
			jPanel.add(getJTextField3(), null);
			jPanel.add(jLabel32, null);
			jPanel.add(getJTextField4(), null);
			jPanel.add(jLabel33, null);
			jPanel.add(getJTextField5(), null);
			jPanel.add(getJComboBox1(), null);
			jPanel.add(getJComboBox12(), null);
			jPanel.add(jLabel34, null);
			jPanel.add(getJTextField6(), null);
			jPanel.add(getJComboBox13(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("");
			jLabel1.setForeground(Color.blue);
			jLabel = new JLabel();
			jLabel
					.setText("                                                          归并序号：");
			jLabel.setForeground(Color.blue);
			jToolBar = new JToolBar();
			jToolBar.add(getBtnSave());
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnPrevious(), null);
			jToolBar.add(getBtnNext(), null);
			jToolBar.add(jLabel);
			jToolBar.add(jLabel1);
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
					if (!checkData()) {
						return;
					}
					fillData();
					if (storageBillAction == null) {
						System.out.println("ssssssssssss");
					}
					if (storageBillAfter == null) {
						System.out.println("ddddddddddddddd");
					}
					storageBillAction = (StorageBillAction) CommonVars
							.getApplicationContext().getBean(
									"storageBillAction");
					storageBillAction.saveOrUpdateObject(new Request(CommonVars
							.getCurrUser()), storageBillAfter);
					fathertableModel.updateRow(storageBillAfter);
					dataState = DataState.BROWSE;
					setState();
					setOk(true);

					// dispose();
				}
			});
		}
		return btnSave;
	}

	// 检查是不是限制类商品并且是否超出了限制类商品中定义的数量
	public boolean checkRerictCommodity() {
		// double aamount = 0;
		// double Amount =0;
		// Boolean isMaterial = EncCommon.isMaterial(beforeCommInfo
		// .getAfterComInfo().getBillList().getImpExpType());
		//		
		// Integer yy=EncCommon.getMaterielTypeByBillType(beforeCommInfo
		// .getAfterComInfo().getBillList().getImpExpType());
		//
		// RestirictCommodity commodity = materialManageAction
		// .findRerictCommodity(new Request(CommonVars.getCurrUser()),
		// isMaterial, tfEmsSerialNo.getText().toString());
		//
		// // 得到清单中的数量
		// Double DeclarationCommInfo = materialManageAction
		// .findCustomsBeforeComInfo(
		// new Request(CommonVars.getCurrUser()), isMaterial,
		// tfEmsSerialNo.getText().toString(),yy.toString());
		//		
		// Amount=DeclarationCommInfo+Double.valueOf(tfDeclaredAmount.getText().toString());
		// if (commodity != null) {
		// if (commodity.getAmount() != null
		// && !commodity.getAmount().equals("")) {
		// aamount = Double.parseDouble(commodity.getAmount().toString());
		// if (Amount > aamount) {
		// JOptionPane.showMessageDialog(this, "备案号："
		// + tfEmsSerialNo.getText() + "已进(出)数量[" + Amount
		// + "] 超出了限制类商品中备的数量[" + aamount + "]!\n", "提示", 0);
		// tfDeclaredAmount.requestFocus();
		// return true;
		// }
		// }
		// }

		return false;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("关闭");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes tfMaterielCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterielCode() {
		if (tfCopGNo == null) {
			tfCopGNo = new JTextField();
			tfCopGNo.setBounds(158, 46, 133, 22);
			tfCopGNo.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}
				public void focusLost(FocusEvent e) {
				}
			});
			// tfMaterielCode.setEditable(false);
		}
		return tfCopGNo;
	}

	/**
	 * This method initializes tfComplexName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplexName() {
		if (tfComplexName == null) {
			tfComplexName = new JTextField();
			tfComplexName.setBounds(451, 73, 122, 22);
			tfComplexName.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}
				public void focusLost(FocusEvent e) {
				}
			});
		}
		return tfComplexName;
	}

	/**
	 * This method initializes tfSecondLegalUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSecondLegalUnit() {
		if (tfSecondLegalUnit == null) {
			tfSecondLegalUnit = new JTextField();
			tfSecondLegalUnit.setBounds(158, 176, 133, 22);
			tfSecondLegalUnit.setEditable(false);
			tfSecondLegalUnit.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}
				public void focusLost(FocusEvent e) {
				}
			});
		}
		return tfSecondLegalUnit;
	}

	/**
	 * This method initializes tfEmsSerialNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getTfEmsSerialNo() {
		if (tfEmsSerialNo == null) {
			tfEmsSerialNo = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfEmsSerialNo.setBounds(451, 254, 122, 22);
			tfEmsSerialNo.setFormatterFactory(getIntegerFormatterFactory());
			tfEmsSerialNo.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}
				public void focusLost(FocusEvent e) {
				}
			});
			setNoZero(tfEmsSerialNo);
		}
		return tfEmsSerialNo;
	}

	/**
	 * This method initializes tfComplexCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplexCode() {
		if (tfComplexCode == null) {
			tfComplexCode = new JTextField();
			tfComplexCode.setBounds(158, 73, 133, 22);
			tfComplexCode.setEditable(false);
			tfComplexCode.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}
				public void focusLost(FocusEvent e) {
				}
			});
		}
		return tfComplexCode;
	}

	/**
	 * This method initializes tfComplexSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplexSpec() {
		if (tfComplexSpec == null) {
			tfComplexSpec = new JTextField();
			tfComplexSpec.setBounds(451, 100, 122, 22);
			tfComplexSpec.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}
				public void focusLost(FocusEvent e) {
				}
			});
			// tfComplexSpec.setEditable(false);
		}
		return tfComplexSpec;
	}

	/**
	 * This method initializes tfLegalUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLegalUnit() {
		if (tfLegalUnit == null) {
			tfLegalUnit = new JTextField();
			tfLegalUnit.setBounds(158, 150, 133, 22);
			tfLegalUnit.setEditable(false);
		}
		return tfLegalUnit;
	}

	/**
	 * This method initializes cbbCurrency
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCurrency() {
		if (cbbCurrency == null) {
			cbbCurrency = new JComboBox();
			cbbCurrency.setBounds(158, 100, 133, 22);
		}
		return cbbCurrency;
	}

	/**
	 * This method initializes tfDeclaredAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfDeclaredAmount() {
		if (tfDeclaredAmount == null) {
			tfDeclaredAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfDeclaredAmount.setBounds(451, 126, 122, 22);
			tfDeclaredAmount.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}
				public void focusLost(FocusEvent e) {
				}
			});
			tfDeclaredAmount
					.setHorizontalAlignment(javax.swing.JTextField.LEFT);
			CustomFormattedTextFieldUtils.setFormatterFactory(tfDeclaredAmount,
					4);
			tfDeclaredAmount.addPropertyChangeListener("value",
					new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent evt) {
							getFirstAmount();
						}

					});
			setNoZero(tfDeclaredAmount);
		}
		return tfDeclaredAmount;
	}

	// 当申报单位与第一法定单位相同，第一法定数量等于申报数量
	protected void getFirstAmount() {
		double amount = 0;
		if (this.tfDeclaredAmount.getValue() != null
				&& !this.tfDeclaredAmount.getValue().equals("")) {
			amount = Double
					.parseDouble(this.tfDeclaredAmount.getValue() == null ? "0"
							: this.tfDeclaredAmount.getValue().toString());
		}
		if (cbbUnit.getSelectedItem() != null) {
			Unit ut = (Unit) cbbUnit.getSelectedItem();
			String fst = tfLegalUnit.getText() == null ? "" : tfLegalUnit
					.getText().trim();
			String set = tfSecondLegalUnit.getText() == null ? ""
					: tfSecondLegalUnit.getText().trim();
			if (ut != null && !ut.getName().trim().equals("")) {
				if (fst.equals(ut.getName().trim())) {
					if (amount != 0) {
						tfLegalAmount.setValue(amount);
					}
				}
				if (set.equals(ut.getName().trim())) {
					if (amount != 0) {
						tfSecondLegalAmount.setValue(amount);
					}
				}
			}
		}
	}

	/**
	 * This method initializes tfSecondLegalAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfSecondLegalAmount() {
		if (tfSecondLegalAmount == null) {
			tfSecondLegalAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfSecondLegalAmount.setBounds(451, 176, 122, 22);
			tfSecondLegalAmount.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}
				public void focusLost(FocusEvent e) {
				}
			});
			// tfSecondLegalAmount
			// .setFormatterFactory(getDefaultFormatterFactory());
			CustomFormattedTextFieldUtils.setFormatterFactory(
					tfSecondLegalAmount, 4);
			setNoZero(tfSecondLegalAmount);
		}
		return tfSecondLegalAmount;
	}

	/**
	 * This method initializes tfDeclaredPrice
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfDeclaredPrice() {
		if (tfDeclaredPrice == null) {
			tfDeclaredPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfDeclaredPrice.setBounds(158, 203, 133, 22);
			tfDeclaredPrice.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}
				public void focusLost(FocusEvent e) {
				}
			});
			// tfDeclaredPrice
			// .setFormatterFactory(getFourPointdefaultFormatterFactory());
			CustomFormattedTextFieldUtils.setFormatterFactory(tfDeclaredPrice,
					4);
			setNoZero(tfDeclaredPrice);
			// tfDeclaredPrice
			// .addCaretListener(new javax.swing.event.CaretListener() {
			// public void caretUpdate(javax.swing.event.CaretEvent e) {
			// if (tfDeclaredAmount.getValue() != null
			// && tfDeclaredPrice.getValue() != null) {
			// jFormattedTextField.setValue(Double
			// .valueOf(tfDeclaredAmount.getValue()
			// .toString())
			// * Double.valueOf(tfDeclaredPrice
			// .getValue().toString()));
			// }
			// }
			// });
		}
		return tfDeclaredPrice;
	}

	/**
	 * This method initializes tfLegalAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfLegalAmount() {
		if (tfLegalAmount == null) {
			tfLegalAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfLegalAmount.setBounds(451, 150, 122, 22);
			tfLegalAmount.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}
				public void focusLost(FocusEvent e) {
				}
			});
			// tfLegalAmount.setFormatterFactory(getDefaultFormatterFactory());
			CustomFormattedTextFieldUtils.setFormatterFactory(tfLegalAmount, 4);
			setNoZero(tfLegalAmount);
		}
		return tfLegalAmount;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getFourPointdefaultFormatterFactory() {
		if (fourPointdefaultFormatterFactory == null) {
			fourPointdefaultFormatterFactory = new DefaultFormatterFactory();
			fourPointdefaultFormatterFactory
					.setDisplayFormatter(getFourPointNumberFormatter());
			fourPointdefaultFormatterFactory
					.setEditFormatter(getFourPointNumberFormatter());
			// fourPointdefaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			// fourPointdefaultFormatterFactory.setEditFormatter(getNumberFormatter());
			// fourPointdefaultFormatterFactory.setNullFormatter(getNumberFormatter());
			// fourPointdefaultFormatterFactory.setDisplayFormatter(getNumberFormatter());

		}
		return fourPointdefaultFormatterFactory;
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
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getFourPointNumberFormatter() {
		if (fourPointNumberFormatter == null) {
			DecimalFormat decimalFormat = new DecimalFormat("#.####");
			fourPointNumberFormatter = new NumberFormatter(decimalFormat);
		}
		return fourPointNumberFormatter;
	}

	/**
	 * 将窗口控件上的数据付值到data实体上，以用来保存
	 * 
	 * @param data
	 */
	private void fillData() {
		storageBillAfter.setTradeModel((Trade) cbbTradeMode.getSelectedItem());// 贸易方式
		storageBillAfter.setCopGNo(tfCopGNo.getText());// 企业内部编号
		storageBillAfter.setTransModel((Transac) cbbTransac.getSelectedItem());// 成交方式
		storageBillAfter.setCurr((Curr) this.cbbCurrency.getSelectedItem());// 币制
		storageBillAfter.setUnit((Unit) cbbUnit.getSelectedItem());// 申报单位
		storageBillAfter.setQty((this.tfDeclaredAmount.getValue() == null ? null
				: Double.parseDouble(this.tfDeclaredAmount.getText())));// 申报数量
		storageBillAfter.setQty1(this.tfLegalAmount.getValue() == null ? null
				: Double.parseDouble(this.tfLegalAmount.getText()));// 法定数量1
		storageBillAfter
				.setQty2(this.tfSecondLegalAmount.getValue() == null ? null
						: Double
								.parseDouble(this.tfSecondLegalAmount.getText()));// 法定数量2
		storageBillAfter
				.setUnitPrice(this.tfDeclaredPrice.getValue() == null ? null
						: Double.parseDouble(this.tfDeclaredPrice.getText()));// 单价
		storageBillAfter.setTotalP(this.tfTotalPrice.getValue() == null ? null
				: Double.parseDouble(this.tfTotalPrice.getText()));// 申报总价

		storageBillAfter.setOriginCountry((Country) this.cbbCountry
				.getSelectedItem());// 产销国
		storageBillAfter.setTafName(tfTafName.getText());// 载货清单号
		storageBillAfter.setEntryID(tfEntryID.getText());// 报关单号
		storageBillAfter.setContrItem(tfEmsSerialNo.getValue() == null ? null
				: Integer.parseInt(tfEmsSerialNo.getText()));// 对应帐册序号

		storageBillAfter.setApprTime(jCalendarComboBox.getDate());// 报关单申报日期

		storageBillAfter.setEntryGNo(tfEntryGNo.getValue() == null ? null
				: Integer.parseInt(tfEntryGNo.getText()));// 对应帐册序号

		storageBillAfter.setCorrBillNo(tfCorrBillNo.getText());// 入仓单号
		storageBillAfter.setCorrBillGNo(tfCorrBillGNo.getValue() == null ? null
				: Integer.parseInt(tfCorrBillGNo.getText()));// 对应帐册序号

	}

	/**
	 * 将实体内的数据显示到窗口上
	 * 
	 * @param data
	 */
	private void showData() {
		if (storageBillAfter == null) {
			JOptionPane.showMessageDialog(DgStorageBillAfter.this, "归并后数据为空！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return;
		}
		this.tfSeqNo.setText(storageBillAfter.getSeqNo() == null ? ""
				: String.valueOf(storageBillAfter.getSeqNo()));// 商品序号
		this.cbbTransac.setSelectedItem(storageBillAfter.getTransModel());// 成交方式
		this.tfCopGNo.setText(storageBillAfter.getCopGNo());// 企业内部编号
		this.cbbTradeMode.setSelectedItem(storageBillAfter.getTradeModel());// 贸易方式
		this.tfComplexCode.setText(storageBillAfter.getCodeTS() == null ? ""
				: storageBillAfter.getCodeTS().getCode());// 商品编码
		this.tfComplexName.setText(storageBillAfter.getName());// 商品名称
		this.cbbCurrency.setSelectedItem(storageBillAfter.getCurr());// 币制
		this.tfComplexSpec.setText(storageBillAfter.getModel());// 商品规格
		this.cbbUnit.setSelectedItem(storageBillAfter.getUnit());
		this.tfDeclaredAmount.setValue(storageBillAfter.getQty());// 申报数量
		this.tfLegalUnit
				.setText(storageBillAfter.getCodeTS().getFirstUnit() == null ? ""
						: storageBillAfter.getCodeTS().getFirstUnit().getName());// 第一法定单位
		this.tfLegalAmount.setValue(storageBillAfter.getQty1());// 第一法定数量
		this.tfSecondLegalUnit.setText(storageBillAfter.getCodeTS()
				.getSecondUnit() == null ? "" : storageBillAfter.getCodeTS()
				.getSecondUnit().getName());// 第二法定单位
		this.tfSecondLegalAmount.setValue(storageBillAfter.getQty2());// 第二法定数量

		this.tfDeclaredPrice.setValue(storageBillAfter.getUnitPrice());// 申报单价
		this.tfTotalPrice.setValue(storageBillAfter.getTotalP());// 申报总价
		this.cbbCountry.setSelectedItem(storageBillAfter.getOriginCountry());// 产销国
		this.tfTafName.setText(storageBillAfter.getTafName());// 载货清单
		this.tfEntryID.setText(storageBillAfter.getEntryID());// 报关单
		this.tfEmsSerialNo.setValue(storageBillAfter.getContrItem());// 帐册序号
		//storageBillAfter.getSeqNum()  storageBillAfter.getContrItem()
		this.jCalendarComboBox.setDate(storageBillAfter.getApprTime());// 报关单日期
		tfEntryGNo.setValue(storageBillAfter.getEntryGNo());// 对应报关单号
		tfCorrBillNo.setText(storageBillAfter.getCorrBillNo());// 入仓单号
		tfCorrBillGNo.setValue(storageBillAfter.getCorrBillGNo());// 入仓单序号
		jLabel1.setText(storageBillAfter.getSeqNum() == null ? "无"
				: storageBillAfter.getSeqNum().toString());
	}

	private boolean checkData() {
		if (tfComplexCode.getText() == null
				|| tfComplexCode.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(DgStorageBillAfter.this, "商品编码不能为空！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (tfComplexName.getText() == null
				|| tfComplexName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(DgStorageBillAfter.this, "商品名称不能为空！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		// if (tfComplexSpec.getText() == null
		// || tfComplexSpec.getText().trim().equals("")) {
		// JOptionPane.showMessageDialog(DgStorageBillAfter.this,
		// "商品型号规格不能为空！", "提示！", JOptionPane.WARNING_MESSAGE);
		// return false;
		// }
		if (cbbCurrency.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgStorageBillAfter.this, "币制不能为空！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (cbbUnit.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgStorageBillAfter.this, "申报单位不能为空！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (tfDeclaredAmount.getValue() == null) {
			JOptionPane.showMessageDialog(DgStorageBillAfter.this, "申报数量不能为空！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (tfDeclaredPrice.getValue() == null) {
			JOptionPane.showMessageDialog(DgStorageBillAfter.this, "申报单价不能为空！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (tfTotalPrice.getValue() == null) {
			JOptionPane.showMessageDialog(DgStorageBillAfter.this, "申报总价不能为空！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private void saveData() {
		fillData();
		// beforeCommInfo = encAction.saveAtcMergeBeforeComInfo(new Request(
		// CommonVars.getCurrUser()), beforeCommInfo);
		// fathertableModel.updateRow(beforeCommInfo);
	}

	private void initUIComponents() {
		// 初始化货币
		this.cbbCurrency.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.cbbCurrency
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCurrency);
		this.cbbCurrency.setSelectedIndex(-1);
		// 产销国
		this.cbbCountry
				.setModel(CustomBaseModel.getInstance().getCountryModel());
		this.cbbCountry.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance()
				.setComboBoxEditor(this.cbbCountry);
		this.cbbCountry.setSelectedIndex(-1);
		// 初始化成交方式
		this.cbbTransac.setModel(CustomBaseModel.getInstance()
				.getTransacModel());
		this.cbbTransac.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTransac);

		// 初始化贸易方式
		this.cbbTradeMode.setModel(CustomBaseModel.getInstance()
				.getTradeModel());
		this.cbbTradeMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTradeMode);
		cbbTradeMode.setSelectedItem(null);

		// 初始化申报单位
		this.cbbUnit.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbUnit.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbUnit);
		cbbTradeMode.setSelectedItem(null);

		CustomFormattedTextFieldUtils.addAutoCalcListener(tfDeclaredAmount,
				new AutoCalcListener() {
					public void run() {
						double amount = (tfDeclaredAmount.getValue() == null ? 0.0
								: Double.parseDouble(tfDeclaredAmount
										.getValue().toString()));
						double unitPrice = (tfDeclaredPrice.getValue() == null ? 0.0
								: Double.parseDouble(tfDeclaredPrice.getValue()
										.toString()));
						double totalPrice = CommonVars.getDoubleByDigit(
								unitPrice * amount, 5);
						tfTotalPrice.setValue(totalPrice);
					}
				});

		CustomFormattedTextFieldUtils.addAutoCalcListener(tfDeclaredPrice,
				new AutoCalcListener() {
					public void run() {
						double unitPrice = (tfDeclaredPrice.getValue() == null ? 0.0
								: Double.parseDouble(tfDeclaredPrice.getValue()
										.toString()));
						double amount = (tfDeclaredAmount.getValue() == null ? 0.0
								: Double.parseDouble(tfDeclaredAmount
										.getValue().toString()));
						double totalPrice = CommonVars.getDoubleByDigit(
								unitPrice * amount, 5);
						System.out.println("totalPrice="+totalPrice);
						tfTotalPrice.setValue(totalPrice);
					}
				});

		CustomFormattedTextFieldUtils.addAutoCalcListener(tfTotalPrice,
				new AutoCalcListener() {
					public void run() {
						double totalPrice = (tfTotalPrice.getValue() == null ? 0.0
								: Double.parseDouble(tfTotalPrice.getValue()
										.toString()));
						double amount = (tfDeclaredAmount.getValue() == null ? 0.0
								: Double.parseDouble(tfDeclaredAmount
										.getValue().toString()));
						if (amount == 0) {
							amount = 1.0;
						}
						double unitPrice = CommonVars.getDoubleByDigit(
								totalPrice / amount, 5);
						tfDeclaredPrice.setValue(unitPrice);
					}
				});

	}

	public JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setText("上笔");
			btnPrevious.setVisible(false);
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (beforeCommInfo == null) {
					// return;
					// }
					// if (checkRerictCommodity()) {
					// return;
					// }
					// if (!checkData()) {
					// return;
					// }
					// fillData();
					//
					// saveData();
					//
					// fathertableModel.updateRow(beforeCommInfo);
					// if (!fathertableModel.previousRow()) {
					// btnPrevious.setEnabled(false);
					// btnNext.setEnabled(true);
					// } else {
					// btnPrevious.setEnabled(true);
					// btnNext.setEnabled(true);
					// }
					//
					// beforeCommInfo = (AtcMergeBeforeComInfo) fathertableModel
					// .getCurrentRow();
					//
					// showData(beforeCommInfo);
					//
					// setState();
				}
			});
		}
		return btnPrevious;
	}

	public JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setText("下笔");
			btnNext.setVisible(false);
			if (fathertableModel != null
					&& fathertableModel.getCurrRowCount() >= fathertableModel
							.getRowCount() - 2) {
				btnNext.setEnabled(false);
				btnPrevious.setEnabled(true);
			}
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (beforeCommInfo == null) {
					// return;
					// }
					// if (checkRerictCommodity()) {
					// return;
					// }
					// if (!checkData()) {
					// return;
					// }
					//
					// fillData();
					//
					// saveData();
					//
					// fathertableModel.updateRow(beforeCommInfo);
					// // if (tableModel.getRowCount() >
					// // tableModel.getCurrRowCount()) {
					// if (!fathertableModel.nextRow()
					// || fathertableModel.getCurrRowCount() >=
					// (fathertableModel
					// .getRowCount() - 2)) {
					// btnPrevious.setEnabled(true);
					// btnNext.setEnabled(false);
					// } else {
					// btnPrevious.setEnabled(true);
					// btnNext.setEnabled(true);
					// }
					//
					// beforeCommInfo = (AtcMergeBeforeComInfo) fathertableModel
					// .getCurrentRow();
					//
					// showData(beforeCommInfo);
					//
					// setState();
				}
			});
		}
		return btnNext;
	}

	/**
	 * @return Returns the isMakeCustomsDeclaration.
	 */
	public boolean isMakeCustomsDeclaration() {
		return isMakeCustomsDeclaration;
	}

	/**
	 * @param isMakeCustomsDeclaration
	 *            The isMakeCustomsDeclaration to set.
	 */
	public void setMakeCustomsDeclaration(boolean isMakeCustomsDeclaration) {
		this.isMakeCustomsDeclaration = isMakeCustomsDeclaration;
	}

	/**
	 * @return Returns the isOk.
	 */
	public boolean isOk() {
		return isOk;
	}

	/**
	 * @param isOk
	 *            The isOk to set.
	 */
	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return fathertableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.fathertableModel = tableModel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (tfSeqNo == null) {
			tfSeqNo = new JTextField();
			tfSeqNo.setEditable(false);
			tfSeqNo.setBounds(new Rectangle(158, 21, 133, 22));
			tfSeqNo.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}
				public void focusLost(FocusEvent e) {
				}
			});
		}
		return tfSeqNo;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (cbbCountry == null) {
			cbbCountry = new JComboBox();
			cbbCountry.setBounds(new Rectangle(158, 230, 133, 22));
		}
		return cbbCountry;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfTotalPrice() {
		if (tfTotalPrice == null) {
			tfTotalPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfTotalPrice.setBounds(new Rectangle(451, 203, 122, 22));
			tfTotalPrice.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}
				public void focusLost(FocusEvent e) {
				}
			});
			// tfTotalPrice.setFormatterFactory(getDefaultFormatterFactory());
			CustomFormattedTextFieldUtils.setFormatterFactory(tfTotalPrice, 4);
			setNoZero(tfTotalPrice);

			tfTotalPrice.setEditable(true);
		}
		return tfTotalPrice;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (tfTafName == null) {
			tfTafName = new JTextField();
			tfTafName.setBounds(new Rectangle(451, 229, 122, 22));
			tfTafName.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}
				public void focusLost(FocusEvent e) {
				}
			});
		}
		return tfTafName;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(new Rectangle(158, 281, 133, 22));
			jCalendarComboBox.setDate(null);
		}
		return jCalendarComboBox;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField3() {
		if (tfEntryID == null) {
			tfEntryID = new JTextField();
			tfEntryID.setBounds(new Rectangle(159, 256, 133, 22));
			tfEntryID.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}
				public void focusLost(FocusEvent e) {
				}
			});
		}
		return tfEntryID;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getJTextField4() {
		if (tfEntryGNo == null) {
			tfEntryGNo = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfEntryGNo.setBounds(new Rectangle(451, 279, 122, 22));
			tfEntryGNo.setFormatterFactory(getIntegerFormatterFactory());
			tfEntryGNo.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}
				public void focusLost(FocusEvent e) {
				}
			});
			setNoZero(tfEntryGNo);
		}
		return tfEntryGNo;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField5() {
		if (tfCorrBillNo == null) {
			tfCorrBillNo = new JTextField();
			tfCorrBillNo.setBounds(new Rectangle(157, 309, 133, 22));
			tfCorrBillNo.setEditable(false);
		}
		return tfCorrBillNo;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (cbbTradeMode == null) {
			cbbTradeMode = new JComboBox();
			cbbTradeMode.setBounds(new Rectangle(451, 17, 122, 22));
		}
		return cbbTradeMode;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox12() {
		if (cbbTransac == null) {
			cbbTransac = new JComboBox();
			cbbTransac.setBounds(new Rectangle(451, 45, 122, 22));
		}
		return cbbTransac;
	}

	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getJTextField6() {
		if (tfCorrBillGNo == null) {
			tfCorrBillGNo = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfCorrBillGNo.setBounds(new Rectangle(452, 304, 122, 22));
			tfCorrBillGNo.setFormatterFactory(getIntegerFormatterFactory());
			tfCorrBillGNo.setEditable(false);

		}
		return tfCorrBillGNo;
	}

	private NumberFormatter getIntegerFormatter() {

		if (integerFormatter == null) {
			integerFormatter = new NumberFormatter();
			DecimalFormat decimalFormat = new DecimalFormat(); // @jve:decl-index=0:
			decimalFormat.setGroupingSize(0);
			decimalFormat.setMaximumFractionDigits(0);
			integerFormatter.setFormat(decimalFormat);
		}
		return integerFormatter;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getIntegerFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDisplayFormatter(getIntegerFormatter());
			defaultFormatterFactory.setEditFormatter(getIntegerFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox13() {
		if (cbbUnit == null) {
			cbbUnit = new JComboBox();
			cbbUnit.setBounds(new Rectangle(158, 127, 133, 22));
		}
		return cbbUnit;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
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
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (btnPrevious1 == null) {
			btnPrevious1 = new JButton();
			btnPrevious1.setText("上笔");
			btnPrevious1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					previous();
				}
			});
		}
		return btnPrevious1;
	}

	private void next() {
		if (fathertableModel.hasNextRow()) {
			fathertableModel.nextRow();
			this.storageBillAfter = (StorageBillAfter) fathertableModel
					.getCurrentRow();
			showData();
			setState();
			if (inOutFlag == 0) {
				jLabel33.setVisible(false);
				tfCorrBillNo.setVisible(false);
				jLabel34.setVisible(false);
				tfCorrBillGNo.setVisible(false);
			}
		}
	}

	private void previous() {
		if (fathertableModel.hasPreviousRow()) {
			fathertableModel.previousRow();
			this.storageBillAfter = (StorageBillAfter) fathertableModel
					.getCurrentRow();
			showData();
			setState();

			if (inOutFlag == 0) {
				jLabel33.setVisible(false);
				tfCorrBillNo.setVisible(false);
				jLabel34.setVisible(false);
				tfCorrBillGNo.setVisible(false);
			}
		}
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (btnNext1 == null) {
			btnNext1 = new JButton();
			btnNext1.setText("下笔");
			btnNext1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					next();
				}
			});
		}
		return btnNext1;
	}

	private void setNoZero(final JFormattedTextField tf) {
		FocusListener[] fcs = tf.getFocusListeners();
		for (int i = 0; i < fcs.length; i++) {
			if (fcs[i].getClass().getName().startsWith(
					"com.bestway.ui.winuicontrol")) {
				System.out.println(fcs[i].getClass().getName());
				tf.removeFocusListener(fcs[i]);
			}
		}
		// tf.addFocusListener(new java.awt.event.FocusAdapter() {
		// @Override
		// public void focusLost(java.awt.event.FocusEvent e) {
		// SwingUtilities.invokeLater(new Runnable() {
		// public void run() {
		// try {
		// Double newVaule = (tf.getValue() == null ? 0.0
		// : Double.valueOf(tf.getValue().toString()));
		// tf.setValue(newVaule);
		// tf.commitEdit();
		// } catch (Exception ex) {
		// }
		//
		// }
		// });
		// }
		// });
	}
} // @jve:decl-index=0:visual-constraint="-29,5"
