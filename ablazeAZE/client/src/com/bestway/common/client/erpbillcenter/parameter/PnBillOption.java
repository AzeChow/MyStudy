/*
 * Created on 2005-7-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.erpbillcenter.parameter;

import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.parameterset.entity.CasBillOption;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.common.Request;
import com.bestway.common.erpbillcenter.authority.ErpBillCenterAuthority;

import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.JFormattedTextField;
import java.awt.GridBagLayout;

/**
 * @author ls // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PnBillOption extends PnErpBillParameterCommon {

	private JPanel jPanel = null;
	private JCheckBox cbIsBillRepeat = null;
	// private JCheckBox cbIsShowStocks = null;
	// private JCheckBox cbIsDoubleClickBillState = null;
	private JCheckBox cbIsInNeedCustomer = null;
	private JCheckBox cbIsOutNeedCustomer = null;
	private JPanel jPanel1 = null;
	private JButton btnEdit = null;
	private JButton btnAvailability = null;
	private JButton btnExit = null;
	private JLabel jLabel = null;
	private static PnBillOption pnBillOption = null;
	private CasBillOption casBillOption = null; // @jve:decl-index=0:

	/**
	 * This method initializes
	 * 
	 */
	private PnBillOption() {
		super();
		casBillOption = ErpBillParameterCommonVars.getCasBillOption();
		initialize();
		showData();
	}

	/**
	 * 初始化 pnBillOption
	 * 
	 * @return
	 */

	public static PnBillOption getInstance() {
		System.out.println("----pn");
		if (pnBillOption == null) {
			pnBillOption = new PnBillOption();
		}
		return pnBillOption;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		System.out.println("----init");
		jLabel2 = new JLabel();
		jLabel2.setBounds(new Rectangle(283, 265, 94, 22));
		jLabel2.setText("时不显示");
		jLabel2.setFont(new Font("Dialog", Font.PLAIN, 12));
		jLabel1 = new JLabel();
		jLabel1.setBounds(22, 406, 140, 26);
		jLabel1.setText("");
		// (*)需重启才能生效!!!
		jLabel = new JLabel();
		this.setLayout(null);
		this.setSize(631, 446);
		jLabel.setBounds(3, 31, 586, 21);
		jLabel.setText("基本单据设定");
		jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		this.add(getJPanel(), null);
		this.add(getJPanel1(), null);
		this.add(getCbIsBillRepeat(), null);
		// this.add(getCbIsShowStocks(), null);
		// this.add(getCbIsDoubleClickBillState(), null);
		this.add(getCbIsInNeedCustomer(), null);
		this.add(getCbIsOutNeedCustomer(), null);
		this.add(getBtnEdit(), null);
		this.add(getBtnAvailability(), null);
		this.add(getBtnExit(), null);
		this.add(jLabel, null);

		this.add(jLabel1, null);
		this.add(getCbIsInitBillNo(), null);
		this.add(getCbIsNoticeBillNo(), null);
		this.add(getCbIsShowBillCorrRecord(), null);
		this.add(getCbWorkShopBack(), null);
		this.add(getCbBackWorkShop(), null);
		this.add(getTfShowBillCorrRecord(), null);
		this.add(jLabel2, null);
		
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
			jPanel.setBounds(3, 55, 625, 3);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jPanel;
	}

	/**
	 * This method initializes cbIsBillRepeat
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsBillRepeat() {
		if (cbIsBillRepeat == null) {
			cbIsBillRepeat = new JCheckBox();
			cbIsBillRepeat.setText("单据号可以重复");
			cbIsBillRepeat.setBounds(49, 85, 112, 21);
			cbIsBillRepeat.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 12));
		}
		return cbIsBillRepeat;
	}

	/**
	 * This method initializes cbIsShowStocks
	 * 
	 * @return javax.swing.JCheckBox
	 */
	// private JCheckBox getCbIsShowStocks() {
	// if (cbIsShowStocks == null) {
	// cbIsShowStocks = new JCheckBox();
	// cbIsShowStocks.setText("显示仓库数量及结余数量");
	// cbIsShowStocks.setBounds(49, 160, 161, 18);
	// cbIsShowStocks.setFont(new java.awt.Font("Dialog",
	// java.awt.Font.PLAIN, 12));
	// }
	// return cbIsShowStocks;
	// }
	/**
	 * This method initializes cbIsDoubleClickBillState
	 * 
	 * @return javax.swing.JCheckBox
	 */
	// private JCheckBox getCbIsDoubleClickBillState() {
	// if (cbIsDoubleClickBillState == null) {
	// cbIsDoubleClickBillState = new JCheckBox();
	// cbIsDoubleClickBillState.setText("双击单据为修改状态，默认为查看状态");
	// cbIsDoubleClickBillState.setBounds(49, 182, 230, 20);
	// cbIsDoubleClickBillState.setFont(new java.awt.Font("Dialog",
	// java.awt.Font.PLAIN, 12));
	// }
	// return cbIsDoubleClickBillState;
	// }
	/**
	 * This method initializes cbIsInNeedCustomer
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsInNeedCustomer() {
		if (cbIsInNeedCustomer == null) {
			cbIsInNeedCustomer = new JCheckBox();
			// cbIsInNeedCustomer.setText("车间入库单据需录入客户名称(*)");
			cbIsInNeedCustomer.setText("车间入库单据需录入客户名称");
			cbIsInNeedCustomer.setBounds(49, 135, 198, 21);
			cbIsInNeedCustomer.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 12));
		}
		return cbIsInNeedCustomer;
	}

	/**
	 * This method initializes cbIsOutNeedCustomer
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsOutNeedCustomer() {
		if (cbIsOutNeedCustomer == null) {
			cbIsOutNeedCustomer = new JCheckBox();
			// cbIsOutNeedCustomer.setText("车间领用单据需录入客户名称(*)");
			cbIsOutNeedCustomer.setText("车间领用单据需录入客户名称");
			cbIsOutNeedCustomer.setBounds(49, 110, 198, 21);
			cbIsOutNeedCustomer.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 12));
		}
		return cbIsOutNeedCustomer;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBounds(3, 394, 625, 3);
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jPanel1;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setBounds(387, 406, 68, 26);
			btnEdit.setText("修改");
			btnEdit
					.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
							12));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					edit();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnAvailability
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAvailability() {
		if (btnAvailability == null) {
			btnAvailability = new JButton();
			btnAvailability.setBounds(463, 406, 68, 26);
			btnAvailability.setText("生效");
			btnAvailability.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 12));
			btnAvailability
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							availability();
						}
					});
		}
		return btnAvailability;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(539, 406, 68, 26);
			btnExit.setText("关闭");
			btnExit
					.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
							12));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					close();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		jPanel.setBounds(3, this.getHeight() - 50, this.getWidth() - 6, 3);
		jPanel1.setBounds(3, 55, this.getWidth() - 6, 3);

		btnExit.setBounds(this.getWidth() - 92, this.getHeight() - 40, 68, 26);
		btnAvailability.setBounds(
				this.getWidth() - 92 - btnExit.getWidth() - 5,
				this.getHeight() - 40, 68, 26);
		btnEdit.setBounds(this.getWidth() - 92 - btnExit.getWidth()
				- btnAvailability.getWidth() - 10, this.getHeight() - 40, 68,
				26);
		jLabel1.setBounds(jLabel1.getX(), btnEdit.getY(), jLabel1.getWidth(),
				jLabel1.getHeight());
	}

	protected void paintComponent(Graphics g) {
		initUIComponents();
		super.paintComponent(g);
	}

	/**
	 * 修改
	 */
	private void edit() {
		//
		// check authority
		//
		ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
				.getApplicationContext().getBean("erpBillCenterAuthority");
		erpBillCenterAuthority.checkErpBillCenterParameterByUpdate(new Request(
				CommonVars.getCurrUser()));

		super.setContainerEnabled(this, true);
		this.dataSate = DataState.EDIT;
		setState();
	}

	/**
	 * 生效
	 */
	private void availability() {
		CasAction casAction = (CasAction) CommonVars.getApplicationContext()
				.getBean("casAction");
		fillData();
		casBillOption = casAction.saveCasBillOption(new Request(CommonVars
				.getCurrUser()), this.casBillOption);
		// System.out.println("this.casBillOption.getIsBackWorkShop()="+this.casBillOption.getIsBackWorkShop());
		// System.out.println("this.casBillOption.getIsWorkShopBack()="+this.casBillOption.getIsWorkShopBack());

		ErpBillParameterCommonVars.setCasBillOption(casBillOption);
		System.out.println("参数："
				+ ErpBillParameterCommonVars.getCasBillOption()
						.getShowBillCorrRecord());
		super.setContainerEnabled(this, false);
		this.dataSate = DataState.BROWSE;
		setState();
	}

	/**
	 * 填充数据
	 * 
	 */
	private void fillData() {
		this.casBillOption.setIsBillRepeat(this.cbIsBillRepeat.isSelected());
		// this.casBillOption
		// .setIsDoubleClickBillState(this.cbIsDoubleClickBillState
		// .isSelected());
		this.casBillOption.setIsInNeedCustomer(this.cbIsInNeedCustomer
				.isSelected());
		this.casBillOption.setIsWorkShopBack(this.cbWorkShopBack.
				isSelected());
		this.casBillOption.setIsBackWorkShop(this.cbBackWorkShop.
				isSelected());
		this.casBillOption.setIsOutNeedCustomer(this.cbIsOutNeedCustomer
				.isSelected());
		this.casBillOption.setIsInitBillNo(this.cbIsInitBillNo.
				isSelected());
		this.casBillOption
				.setIsNoticeBillNo(this.cbIsNoticeBillNo.isSelected());

		this.casBillOption.setIsShowBillCorrRecord(cbIsShowBillCorrRecord
				.isSelected());

		this.casBillOption.setShowBillCorrRecord(Double
				.valueOf(tfShowBillCorrRecord.getValue().toString()));
		

	}

	/**
	 * 显示数据
	 * 
	 */

	private void showData() {
		this.cbIsBillRepeat.setSelected(this.casBillOption.getIsBillRepeat());
		// this.cbIsDoubleClickBillState.setSelected(this.casBillOption
		// .getIsDoubleClickBillState());
		this.cbIsInNeedCustomer.setSelected(this.casBillOption
				.getIsInNeedCustomer());
		this.cbIsOutNeedCustomer.setSelected(this.casBillOption
				.getIsOutNeedCustomer());
		// this.cbIsShowStocks.setSelected(this.casBillOption.getIsShowStocks());
		// setState();
		this.cbIsInitBillNo
				.setSelected(this.casBillOption.getIsInitBillNo() == null ? false
						: this.casBillOption.getIsInitBillNo());
		this.cbIsNoticeBillNo.setSelected(this.casBillOption
				.getIsNoticeBillNo() == null ? false : this.casBillOption
				.getIsNoticeBillNo());

		this.cbIsShowBillCorrRecord.setSelected(this.casBillOption
				.getIsShowBillCorrRecord() == null ? false : this.casBillOption
				.getIsShowBillCorrRecord());
		this.tfShowBillCorrRecord.setValue(casBillOption
				.getShowBillCorrRecord());

		this.cbBackWorkShop
				.setSelected(this.casBillOption.getIsBackWorkShop() == null ? false
						: this.casBillOption.getIsBackWorkShop());

		this.cbWorkShopBack
				.setSelected(this.casBillOption.getIsWorkShopBack() == null ? false
						: this.casBillOption.getIsWorkShopBack());
	

		super.setContainerEnabled(this, false);
	}

	/** 设置初始状态 */
	private int dataSate = DataState.BROWSE;
	private JLabel jLabel1 = null;
	private JCheckBox cbIsInitBillNo = null;
	private JCheckBox cbIsNoticeBillNo = null;
	private JCheckBox cbIsShowBillCorrRecord = null;
	private JCheckBox cbWorkShopBack = null;
	private JCheckBox cbBackWorkShop = null;
	private JFormattedTextField tfShowBillCorrRecord = null;
	private JLabel jLabel2 = null;
	private JCheckBox isInChangehouseMateriel = null;
	private JCheckBox isOutChangehouseMateriel = null;
	private JCheckBox isInChangehouseProduct = null;
	private JCheckBox isOutChangehoursProduct = null;

	/**
	 * 设置组件的状态
	 * 
	 */
	private void setState() {
		btnEdit.setEnabled(dataSate == DataState.BROWSE);
		btnAvailability.setEnabled(dataSate != DataState.BROWSE);
	}

	/**
	 * This method initializes cbIsInitBillNo
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsInitBillNo() {
		if (cbIsInitBillNo == null) {
			cbIsInitBillNo = new JCheckBox();
			cbIsInitBillNo.setBounds(new Rectangle(49, 216, 217, 21));
			cbIsInitBillNo.setText("新增单据时是否要自动产生单据号");
			cbIsInitBillNo.setFont(new Font("Dialog", Font.PLAIN, 12));
		}
		return cbIsInitBillNo;
	}

	/**
	 * This method initializes cbIsNoticeBillNo
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsNoticeBillNo() {
		if (cbIsNoticeBillNo == null) {
			cbIsNoticeBillNo = new JCheckBox();
			cbIsNoticeBillNo.setBounds(new Rectangle(49, 242, 221, 21));
			cbIsNoticeBillNo.setText("国内购买是否备注发票号(长度为8位)");
			cbIsNoticeBillNo.setFont(new Font("Dialog", Font.PLAIN, 12));
		}
		return cbIsNoticeBillNo;
	}

	/**
	 * This method initializes cbIsShowBillCorrRecord
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsShowBillCorrRecord() {
		if (cbIsShowBillCorrRecord == null) {
			cbIsShowBillCorrRecord = new JCheckBox();
			cbIsShowBillCorrRecord.setBounds(new Rectangle(49, 265, 170, 22));
			cbIsShowBillCorrRecord.setText("在单据对应未对应数量小于");
			cbIsShowBillCorrRecord.setFont(new Font("Dialog", Font.PLAIN, 12));
			cbIsShowBillCorrRecord
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							tfShowBillCorrRecord
									.setEditable(cbIsShowBillCorrRecord
											.isSelected());
						}
					});
		}
		return cbIsShowBillCorrRecord;
	}

	/**
	 * This method initializes cbWorkShopBack
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbWorkShopBack() {
		if (cbWorkShopBack == null) {
			cbWorkShopBack = new JCheckBox();
			cbWorkShopBack.setBounds(new Rectangle(49, 163, 198, 21));
			cbWorkShopBack.setText("车间返回需录入客户名称");
		}
		return cbWorkShopBack;
	}

	/**
	 * This method initializes cbBackWorkShop
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbBackWorkShop() {
		if (cbBackWorkShop == null) {
			cbBackWorkShop = new JCheckBox();
			cbBackWorkShop.setBounds(new Rectangle(49, 189, 198, 21));
			cbBackWorkShop.setText("返回车间需录入客户名称");
		}
		return cbBackWorkShop;
	}

	
	/**
	 * This method initializes tfReportDecimalLength
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfShowBillCorrRecord() {
		if (tfShowBillCorrRecord == null) {
			tfShowBillCorrRecord = new JFormattedTextField();
			CustomFormattedTextFieldUtils.setFormatterFactory(
					this.tfShowBillCorrRecord, 4);
			tfShowBillCorrRecord.setBounds(new Rectangle(220, 265, 62, 22));
		}
		return tfShowBillCorrRecord;
	}

	
	
}
