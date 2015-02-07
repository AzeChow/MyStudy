/*
 * Created on 2004-9-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.erpbillcenter;

import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillDetailFixture;
import com.bestway.bcus.cas.bill.entity.BillDetailHalfProduct;
import com.bestway.bcus.cas.bill.entity.BillDetailLeftoverMateriel;
import com.bestway.bcus.cas.bill.entity.BillDetailMateriel;
import com.bestway.bcus.cas.bill.entity.BillDetailProduct;
import com.bestway.bcus.cas.bill.entity.BillDetailRemainProduct;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.cas.parameterset.entity.CasBillOption;
import com.bestway.bcus.cas.parameterset.entity.LotControl;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.InitialFocusSetter;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.client.erpbillcenter.parameter.ErpBillParameterCommonVars;
import com.bestway.common.constant.SBillType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 单据商品项
 * 
 * 刘民添加部分注释 修改时间: 2008-10-18
 * 
 * @author ? // change the template for this generated type comment go to Window
 *         - Preferences - Java - Code Style - Code Templates 海关帐单据明细窗口
 */

@SuppressWarnings("unchecked")
public class DgBillDetail extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JButton btnExit = null;

	private JTextField tfPtPart = null;

	private JTextField tfPtName = null;

	private JTextField tfPtSpec = null;

	private JTextField tfPtProperty = null;

	private JTextField tfCustomNo = null;

	private JTextField tfProductNo = null;

	private JButton btnSelect = null;

	private JTextField tfHsSpec = null;

	private JTextField tfHsName = null;

	private JTextField tfHsUnit = null;

	private JTableListModel tableModel = null;

	// //wss 这个不知道有什么用，故屏蔽
	// private JTableListModel fatherTableModel = null;
	//
	// public JTableListModel getFatherTableModel() {
	// return fatherTableModel;
	// }
	//
	// public void setFatherTableModel(JTableListModel fatherTableModel) {
	// this.fatherTableModel = fatherTableModel;
	// }

	/**
	 * 单据表头
	 */
	private BillMaster billMaster = null;

	/**
	 * 工厂单据明细
	 */
	private BillDetail currBillDetail = null; // @jve:decl-index=0:

	private CasAction casAction = null;


	private JTextField tfPtUnit = null;

	private JFormattedTextField tfPtAmount = null;

	private JFormattedTextField tfHsAmount = null;

	private JFormattedTextField tfPtPrice = null;

	private JFormattedTextField tfHsPrice = null;

	private JFormattedTextField tfMoney = null;

	private JFormattedTextField tfCustomNum = null;

	private JFormattedTextField tfNetWeight = null;

	private JButton btnSave = null;

	/**
	 * 缺省的格式工厂
	 */
	private DefaultFormatterFactory defaultFormatterFactory = null;

	/**
	 * 数字格式
	 */
	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:

	private JComboBox cbbVersion = null;

	/**
	 * 缺省的格式工厂
	 */
	private DefaultFormatterFactory defaultFormatterFactory1 = null;

	/**
	 * 数字格式
	 */
	private NumberFormatter numberFormatter1 = null;

	/**
	 * 海关帐单据类型
	 */
	private int billType = -1;

	private JToolBar jToolBar = null;

	// private JLabel jLabel20 = null;

	private JLabel jLabel21 = null;

	private JLabel jLabel22 = null;

	private JLabel jLabel23 = null;

	private JLabel jLabel17 = null;

	private JComboBox cbbWareSet = null;

	/**
	 * 软件管理操作接口
	 */
	private MaterialManageAction materialManageAction = null;

	private JLabel jLabel18 = null;

	private JLabel lbUnitConvert = null;

	private JLabel jLabel19 = null;

	private JTextField tfNode = null;

	/**
	 * 海关帐单据类别
	 */
	private int billCategory = -1;

	private Double count = 0.0;

	private JLabel jLabel161 = null;

	private JPanel jPanel11 = null;

	private JLabel jLabel25 = null;

	private JLabel jLabel26 = null;

	private JTextField tfEmsNo = null;

	private JFormattedTextField tfHsVersion = null;

	/**
	 * 窗体需要保存的参数商品编码
	 */
	private Complex complex = null; // @jve:decl-index=0:

	/**
	 * 工厂单位
	 */
	private CalUnit mtUnit = null; // @jve:decl-index=0:

	/**
	 * 海关单位
	 */
	private Unit hsUnit = null; // @jve:decl-index=0:

	/** 单位折算 */
	private double unitConvert = 0.0; // @jve:decl-index=0:

	private JLabel jlbLocation = null;

	private JLabel jlbHoldMan = null;

	private JTextField tfHoldMan = null;

	private JTextField tfLocation = null;

	private JLabel jLabel16 = null;

	private JLabel jLabel24 = null;

	private JTextField tfTakeBillNo = null;

	private JTextField tfOderBillNo = null;

	private JButton btnSpecialCustomsContractNoSelect = null;

	private JLabel jLabel27 = null;

	private JLabel lbUnitweight = null;

	/**
	 * 净重变量
	 */
	private String invNetWeight = "1"; // @jve:decl-index=0:

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JButton btnCancel = null;

	private JButton btnEdit = null;

	/**
	 * 组件状态
	 */
	private int dataState = -1;

	private JButton btnProductNo = null;

	private JButton btnWareSetAll = null;

	/**
	 * 海关帐基本单据项目设定
	 */
	private CasBillOption casBillOption = null;

	private boolean isMateriel = true;

	//private JCustomFormattedTextField tfWarehouses = null;

	//private JCustomFormattedTextField tfBalanceAmount = null;

	private JButton btnDeliveryNumber = null;

	private JLabel jLabel29 = null;

	private JTextField tfComplex = null;

	/**
	 * 海关单位 hcl
	 */
	private Unit tmpHsUnit; // @jve:decl-index=0:
	/**
	 * 海关商品编码 hcl
	 */
	private Complex tmpComplex; // @jve:decl-index=0:
	/**
	 * 折算报关单位比率 hcl
	 */
	private Double tmpUnitConvert = null;  //  @jve:decl-index=0:
	
	private MaterialBomVersion version=new MaterialBomVersion();  //  @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public DgBillDetail(BillMaster billMaster, JTableListModel tableModel) {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		this.billMaster = billMaster;
		this.tableModel = tableModel;

		initialize();
		//
		// 设置窗口显示的焦点定位,必须在 visible 前加入
		// Set component with initial focus
		// must be done before the frame is made visible
		//
		InitialFocusSetter.setInitialFocus(this, cbbVersion);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(606, 519);
		this.setContentPane(getJContentPane());
		this.setTitle("单据商品项");
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initUIComponents();
		setAllKeyListener(jContentPane);
	}

	/**
	 * 设置所有的RadioButton的属性和事件
	 * 
	 * @param component
	 */
	private void setAllKeyListener(Component component) {
		if (!(component instanceof Container)) {
			return;
		}
		Container container = (Container) component;
		for (int i = 0; i < container.getComponentCount(); i++) {
			Component temp = container.getComponent(i);
			temp.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_S) {
						saveData();
					}
					if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
						DgBillDetail.this.dispose();
					}
				}
			});
			setAllKeyListener(temp);
		}
	}

	/**
	 * 显示
	 */
	public void setVisible(boolean b) {
		if (b) {
			fixControlUIComponents();// 设备时进行控制
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
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
			jLabel29 = new JLabel();
			jLabel29.setBounds(new Rectangle(291, 411, 62, 21));
			jLabel29.setText("商品编码");
			jLabel29.setHorizontalAlignment(SwingConstants.RIGHT);

			// wss2010.09.16屏蔽
			// jLabel28 = new JLabel();
			// jLabel28.setBounds(new Rectangle(61, 310, 55, 22));
			// jLabel28.setText("结余数量");

			// wss2010.09.16屏蔽
			// jLabel20 = new JLabel();
			// jLabel20.setBounds(new Rectangle(294, 283, 55, 22));
			// jLabel20.setText("仓库数量");

			jLabel24 = new JLabel();
			jLabel24.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel24.setBounds(new Rectangle(60, 257, 55, 22)); // Generated
			jLabel24.setText("送货单号"); // Generated
			jLabel24.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel16 = new JLabel();
			jLabel16.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel16.setBounds(new Rectangle(295, 229, 53, 22)); // Generated
			jLabel16.setText("订单号"); // Generated
			jlbHoldMan = new JLabel();
			jlbHoldMan.setBounds(new Rectangle(60, 283, 55, 22));
			jlbHoldMan.setText("保管人");
			jlbHoldMan.setHorizontalAlignment(SwingConstants.RIGHT);
			jlbLocation = new JLabel();
			jlbLocation.setBounds(new Rectangle(294, 256, 55, 21));
			jlbLocation.setText("存放地点");
			jlbLocation.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel26 = new JLabel();
			jLabel26.setBounds(new Rectangle(60, 411, 59, 21));
			jLabel26.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel26.setText("手册号");
			jLabel25 = new JLabel();
			jLabel25.setBounds(new Rectangle(291, 386, 62, 21));
			jLabel25.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel25.setText("版本号");
			jLabel161 = new JLabel();
			jLabel161.setBounds(new Rectangle(19, 339, 83, 18));
			jLabel161.setText("报关商品资料");
			jLabel19 = new JLabel();
			jLabel19.setBounds(new Rectangle(295, 204, 53, 22));
			jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel19.setText("备注");
			jLabel17 = new JLabel();
			jLabel23 = new JLabel();
			jLabel22 = new JLabel();
			jLabel21 = new JLabel();
			// jLabel20 = new JLabel();
			javax.swing.JLabel jLabel15 = new JLabel();
			javax.swing.JLabel jLabel14 = new JLabel();
			javax.swing.JLabel jLabel13 = new JLabel();
			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel11 = new JLabel();
			javax.swing.JLabel jLabel10 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel5 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setText("商品料号");
			jLabel.setBounds(60, 19, 53, 22);
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel1.setText("规格型号");
			jLabel1.setBounds(60, 45, 53, 22);
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel2.setText("产品属性");
			jLabel2.setBounds(60, 72, 53, 22);
			jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel3.setText("数量");
			jLabel3.setBounds(60, 97, 53, 22);
			jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel4.setText("折算报关数量");
			jLabel4.setBounds(40, 122, 73, 22);
			jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel5.setText("金额");
			jLabel5.setBounds(60, 149, 53, 22);
			jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel6.setText("对应报关单");
			jLabel6.setBounds(41, 176, 72, 22);
			jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel7.setText("净重");
			jLabel7.setBounds(60, 204, 53, 22);
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel8.setText("名称");
			jLabel8.setBounds(297, 19, 53, 22);
			jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setText("单位");
			jLabel9.setBounds(296, 45, 53, 22);
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel15.setText("版本");
			jLabel15.setBounds(295, 72, 53, 22);
			jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			// jLabel10.setText("币制");
			// jLabel10.setBounds(295, 76, 53, 14);
			// jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel11.setText("单价");
			jLabel11.setBounds(296, 97, 53, 22);
			jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel12.setText("海关单价");
			jLabel12.setBounds(295, 122, 53, 22);
			jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel13.setText("制单号");
			jLabel13.setBounds(295, 149, 53, 22);
			jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel14.setText("对应数量");
			jLabel14.setBounds(295, 176, 53, 22);
			jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

			// jLabel15.setText("版本");
			// jLabel15.setBounds(295, 206, 53, 14);
			// jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			// jLabel20.setBounds(56, 326, 59, 21);
			// jLabel20.setText("商品编码");
			// jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel21.setBounds(59, 386, 60, 21);
			jLabel21.setText("规格型号");
			jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel22.setBounds(59, 360, 62, 21);
			jLabel22.setText("名称");
			jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel23.setBounds(291, 360, 59, 21);
			jLabel23.setText("单位");
			jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel17.setBounds(58, 229, 55, 22);
			jLabel17.setText("仓库");
			jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(jLabel6, null);
			jPanel.add(jLabel7, null);
			jPanel.add(jLabel8, null);
			jPanel.add(jLabel9, null);
			jPanel.add(jLabel10, null);
			jPanel.add(jLabel11, null);
			jPanel.add(jLabel12, null);
			jPanel.add(jLabel13, null);
			jPanel.add(jLabel14, null);
			jPanel.add(jLabel15, null);
			jPanel.add(getTfPtPart(), null);
			jPanel.add(getTfPtName(), null);
			jPanel.add(getTfPtSpec(), null);
			jPanel.add(getTfPtProperty(), null);
			jPanel.add(getTfCustomNo(), null);
			jPanel.add(getTfProductNo(), null);
			jPanel.add(getBtnSelect(), null);
			jPanel.add(getTfPtUnit(), null);
			jPanel.add(getCbbVersion(), null);
			// jPanel.add(getJCmbCurr(), null);
			jPanel.add(getTfPtAmount(), null);
			jPanel.add(getTfHsAmount(), null);
			jPanel.add(getTfPtPrice(), null);
			jPanel.add(getTfHsPrice(), null);
			jPanel.add(getTfMoney(), null);
			jPanel.add(getTfCustomNum(), null);
			jPanel.add(getTfNetWeight(), null);
			jPanel.add(getTfNode(), null);
			// jPanel.add(getJFormattedTextField5(), null);
			// jPanel.add(getTfComplex(), null);
			jPanel.add(getTfHsSpec(), null);
			jPanel.add(getTfHsName(), null);
			jPanel.add(getTfHsUnit(), null);
			// jPanel.add(jLabel20, null);
			jPanel.add(jLabel21, null);
			jPanel.add(jLabel22, null);
			jPanel.add(jLabel23, null);
			jPanel.add(jLabel17, null);
			jPanel.add(getCbbWareSet(), null);
			jPanel.add(jLabel19, null);
			// jPanel.add(getTfNode(), null);
			jPanel.add(jLabel161, null);
			jPanel.add(getJPanel11(), null);
			jPanel.add(jLabel25, null);
			jPanel.add(jLabel26, null);
			jPanel.add(getTfEmsNo(), null);
			jPanel.add(getTfHsVersion(), null);
			// jPanel.add(getBtnSelectHs(), null);
			jPanel.add(jlbLocation, null);
			jPanel.add(jlbHoldMan, null);
			jPanel.add(getTfHoldMan(), null);
			jPanel.add(getTfLocation(), null);
			jPanel.add(jLabel16, null); // Generated
			jPanel.add(jLabel24, null); // Generated
			jPanel.add(getTfTakeBillNo(), null); // Generated
			jPanel.add(getTfOderBillNo(), null); // Generated
			jPanel.add(getBtnSpecialCustomsContractNoSelect(), null);
			jPanel.add(getBtnProductNo(), null);
			jPanel.add(getBtnWareSetAll(), null);

			// jPanel.add(jLabel20, null);
			// jPanel.add(getTfWarehouses(), null);wss2010.09.16屏蔽
			// jPanel.add(jLabel28, null);
			// jPanel.add(getTfBalanceAmount(), null);wss2010.09.16屏蔽

			jPanel.add(getBtnDeliveryNumber(), null);
			jPanel.add(jLabel29, null);
			jPanel.add(getTfComplex(), null);
		}
		return jPanel;
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

	/**
	 * This method initializes tfPtPart
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtPart() {
		if (tfPtPart == null) {
			tfPtPart = new JTextField();
			tfPtPart.setEditable(false);
			tfPtPart.setBounds(124, 19, 116, 22);
		}
		return tfPtPart;
	}

	/**
	 * This method initializes tfPtName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtName() {
		if (tfPtName == null) {
			tfPtName = new JTextField();
			tfPtName.setEditable(false);
			tfPtName.setBounds(359, 19, 116, 22);
		}
		return tfPtName;
	}

	/**
	 * This method initializes tfPtSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtSpec() {
		if (tfPtSpec == null) {
			tfPtSpec = new JTextField();
			tfPtSpec.setEditable(false);
			tfPtSpec.setBounds(124, 45, 116, 22);
		}
		return tfPtSpec;
	}

	/**
	 * This method initializes tfPtProperty
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtProperty() {
		if (tfPtProperty == null) {
			tfPtProperty = new JTextField();
			tfPtProperty.setEditable(false);
			tfPtProperty.setBounds(124, 72, 116, 22);
		}
		return tfPtProperty;
	}

	/**
	 * This method initializes tfCustomNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomNo() {
		if (tfCustomNo == null) {
			tfCustomNo = new JTextField();
			tfCustomNo.setBounds(124, 176, 116, 22);
		}
		return tfCustomNo;
	}

	/**
	 * This method initializes tfProductNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfProductNo() {
		if (tfProductNo == null) {
			tfProductNo = new JTextField();
			tfProductNo.setBounds(359, 149, 116, 22);
		}
		return tfProductNo;
	}

	/**
	 * This method initializes btnSelect
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelect() {
		if (btnSelect == null) {
			btnSelect = new JButton();
			btnSelect.setText("...");
			btnSelect.setBounds(241, 18, 21, 21);
			btnSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int billType = billMaster.getBillType().getBillType()
							.intValue();
					List list = new ArrayList();

					// wss：2010.06.09新加（半成品）
					if (8 == billType || 7 == billType) {
						list = ErpBillCenterQuery.getInstance().getBcpFromWlzd(
								false, getMaterielType(billType, isMateriel),
								tableModel.getList());
					}

					// 其它（除半成品）
					else {
						list = ErpBillCenterQuery.getInstance()
								.getFactoryAndFactualCustomsRalationforBill(
										false,
										getMaterielType(billType, isMateriel),
										tableModel.getList());
					}

					if (list != null && list.size() > 0) {
						FactoryAndFactualCustomsRalation ffcr = null;
						Materiel materiel = null;
						Double unitConvert = 0.0;
						if (8 == billType || 7 == billType) {
							materiel = (Materiel) list.get(0);
							unitConvert = materiel.getUnitConvert() == null ? 0.0
									: materiel.getUnitConvert();
							// System.out.println("-hcl.ffcr-"+ffcr.getStatCusNameRelationHsn().getCusName());

							fillWinBySelectMtSemiProduct(materiel);
						} else {
							ffcr = (FactoryAndFactualCustomsRalation) list
									.get(0);
							materiel = ffcr.getMateriel();
							unitConvert = ffcr.getUnitConvert() == null ? 0.0
									: ffcr.getUnitConvert();
							// System.out.println("-hcl.ffcr-"+ffcr.getStatCusNameRelationHsn().getCusName());
							fillWinBySelectMt(ffcr);
						}

						// 净重
						// Double invPtNetWeight = materiel.getPtNetWeight();

						// 单价
						Double newPrice = materiel.getPtPrice() == null ? 0.0
								: materiel.getPtPrice();

						// 净重
						invNetWeight = materiel.getPtNetWeight() == null ? "0.0"
								: materiel.getPtNetWeight().toString();

						if (currBillDetail != null) {
							currBillDetail.setUnitConvert(unitConvert);
						}
						double ptAmount = 0.0;
						if (tfPtAmount.getValue() != null) {
							ptAmount = Double.valueOf(tfPtAmount.getValue()
									.toString().trim().equals("") ? "0.0"
									: tfPtAmount.getValue().toString());
						}

						BigDecimal hsPrice = new BigDecimal(newPrice
								/ Double.parseDouble(unitConvert.toString()));

						tfPtPrice.setValue(newPrice);

						tfHsPrice.setValue(hsPrice.setScale(6,
								BigDecimal.ROUND_HALF_UP).doubleValue());

						BigDecimal hsAmout = new BigDecimal(ptAmount
								* Double.parseDouble(unitConvert.toString()));

						tfHsAmount.setValue(hsAmout.setScale(6,
								BigDecimal.ROUND_HALF_UP).doubleValue());

						doCaclHsMoney();// 金额wss2010.08.25增加
						doCaclNetWeight();// 净重wss2010.08.25增加
					}
				}
			});
		}
		return btnSelect;
	}

	/**
	 * This method initializes tfComplex
	 * 
	 * @return javax.swing.JTextField
	 */
	// private JTextField getTfComplex() {
	// if (tfComplex == null) {
	// tfComplex = new JTextField();
	// tfComplex.setEditable(false);
	// tfComplex.setBounds(124, 326, 116, 21);
	// }
	// return tfComplex;
	// }
	/**
	 * This method initializes tfHsSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsSpec() {
		if (tfHsSpec == null) {
			tfHsSpec = new JTextField();
			tfHsSpec.setEditable(false);
			tfHsSpec.setBounds(128, 383, 116, 21);
		}
		return tfHsSpec;
	}

	/**
	 * This method initializes tfHsName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsName() {
		if (tfHsName == null) {
			tfHsName = new JTextField();
			tfHsName.setEditable(false);
			tfHsName.setBounds(128, 359, 116, 21);
		}
		return tfHsName;
	}

	/**
	 * This method initializes tfHsUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsUnit() {
		if (tfHsUnit == null) {
			tfHsUnit = new JTextField();
			tfHsUnit.setEditable(false);
			tfHsUnit.setBounds(363, 359, 116, 21);
		}
		return tfHsUnit;
	}

	/**
	 * This method initializes tfPtUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtUnit() {
		if (tfPtUnit == null) {
			tfPtUnit = new JTextField();
			tfPtUnit.setLocation(359, 45);
			tfPtUnit.setSize(116, 22);
			tfPtUnit.setEditable(false);
		}
		return tfPtUnit;
	}

	/**
	 * This method initializes cmbCurr
	 * 
	 * @return javax.swing.JComboBox
	 */
	// private JComboBox getJCmbCurr() {
	// if (cmbCurr == null) {
	// cmbCurr = new JComboBox();
	// cmbCurr.setLocation(359, 71);
	// cmbCurr.setSize(116, 22);
	// }
	// return cmbCurr;
	// }
	/**
	 * This method initializes tfPtAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfPtAmount() {
		if (tfPtAmount == null) {
			tfPtAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfPtAmount.setLocation(124, 97);
			tfPtAmount.setSize(116, 22);
			tfPtAmount.getDocument().addDocumentListener(
					new DocumentListener() {

						public void insertUpdate(DocumentEvent e) {
							doCaclHsAmout();
						}

						public void removeUpdate(DocumentEvent e) {
							doCaclHsAmout();
						}

						public void changedUpdate(DocumentEvent e) {
							doCaclHsAmout();
						}
					});
		}
		return tfPtAmount;
	}

	/**
	 * This method initializes tfHsAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfHsAmount() {
		if (tfHsAmount == null) {
			tfHsAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfHsAmount.setLocation(124, 122);
			tfHsAmount.setSize(116, 22);
			tfHsAmount.setFormatterFactory(getDefaultFormatterFactory());
			tfHsAmount.getDocument().addDocumentListener(
					new DocumentListener() {

						public void insertUpdate(DocumentEvent e) {
							doCaclHsMoney();
							doCaclNetWeight();
						}

						public void removeUpdate(DocumentEvent e) {
							doCaclHsMoney();
							doCaclNetWeight();
						}

						public void changedUpdate(DocumentEvent e) {
							doCaclHsMoney();
							doCaclNetWeight();
						}
					});
		}
		return tfHsAmount;
	}

	/**
	 * This method initializes tfPtPrice
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfPtPrice() {
		if (tfPtPrice == null) {
			tfPtPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfPtPrice.setLocation(359, 97);
			tfPtPrice.setSize(116, 22);
			tfPtPrice.setFormatterFactory(getDefaultFormatterFactory());
			tfPtPrice.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					Double ptPrice = 0.0;
					try {
						ptPrice = Double.parseDouble(tfPtPrice.getText());
						tfPtPrice.setValue(ptPrice);
					} catch (Exception ex) {
						tfPtPrice.setText(tfPtPrice.getValue().toString());
						return;
					}
					doCaclHsPrice();
					doCaclHsMoney();// wss2010.08.25新加
				}
			});
			// tfPtPrice.getDocument().addDocumentListener(new
			// DocumentListener() {
			//
			// public void insertUpdate(DocumentEvent e) {
			// doCaclHsPrice();
			// }
			//
			// public void removeUpdate(DocumentEvent e) {
			// doCaclHsPrice();
			// }
			//
			// public void changedUpdate(DocumentEvent e) {
			// doCaclHsPrice();
			// }
			// });

		}
		return tfPtPrice;
	}

	/**
	 * This method initializes tfHsPrice
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfHsPrice() {
		if (tfHsPrice == null) {
			tfHsPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfHsPrice.setLocation(359, 122);
			tfHsPrice.setSize(116, 22);
			tfHsPrice.setFormatterFactory(getDefaultFormatterFactory());
			tfHsPrice.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					Double hsPrice = 0.0;
					try {
						hsPrice = Double.parseDouble(tfHsPrice.getText());
						tfHsPrice.setValue(hsPrice);
					} catch (Exception ex) {
						tfHsPrice.setText(tfHsPrice.getValue().toString());
						return;
					}
					doCaclPtPrice();
					doCaclHsMoney();
				}
			});
			// tfHsPrice.getDocument().addDocumentListener(new
			// DocumentListener() {
			//
			// public void insertUpdate(DocumentEvent e) {
			// // doCaclPtPrice();
			// doCaclHsMoney();
			// }
			//
			// public void removeUpdate(DocumentEvent e) {
			// // doCaclPtPrice();
			// doCaclHsMoney();
			// }
			//
			// public void changedUpdate(DocumentEvent e) {
			// // doCaclPtPrice();
			// doCaclHsMoney();
			// }
			// });
		}
		return tfHsPrice;
	}

	/**
	 * This method initializes tfMoney
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfMoney() {
		if (tfMoney == null) {
			tfMoney = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfMoney.setLocation(124, 149);
			tfMoney.setSize(116, 22);
			tfMoney.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfMoney;
	}

	/**
	 * This method initializes tfCustomNum
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfCustomNum() {
		if (tfCustomNum == null) {
			tfCustomNum = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfCustomNum.setLocation(359, 176);
			tfCustomNum.setSize(116, 22);
			tfCustomNum.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfCustomNum;
	}

	/**
	 * This method initializes tfNetWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfNetWeight() {
		if (tfNetWeight == null) {
			tfNetWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfNetWeight.setLocation(124, 204);
			tfNetWeight.setSize(116, 22);
			tfNetWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfNetWeight;
	}

	/**
	 * @return Returns the currBillDetail.
	 */
	public BillDetail getCurrBillDetail() {
		return currBillDetail;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDone() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					btnSave.requestFocus();
					saveData();
				}
			});
		}
		return btnSave;
	}

	/**
	 * 保存
	 */
	private void saveData() {
		if (validateData() == false) {
			return;
		}
		modifyBillDetail();
		// doWarehouses();wss2010.09.16屏蔽
		dataState = DataState.BROWSE;
		setState();
	}

	/**
	 * 验证查询情况
	 * 
	 * @return
	 */
	private boolean validateData() {// 为 False 返回
		this.casBillOption = casAction.findCasBillOption(new Request(CommonVars
				.getCurrUser()));
		if (tfPtPart.getText().equals("")) {
			JOptionPane.showMessageDialog(DgBillDetail.this,
					"料号不可为空，请选择一个料件或成品", "资料不全", 1);
			return false;
		}
		if (tfPtName.getText().equals("")) {
			JOptionPane.showMessageDialog(DgBillDetail.this,
					"名称不可为空，请选择一个有效的料件或成品", "资料不全", 1);
			return false;
		}
		if (tfPtUnit.getText().equals("")) {
			JOptionPane.showMessageDialog(DgBillDetail.this,
					"单位不可为空，请选择一个有效的料件或成品", "资料不全", 1);
			return false;
		}
		if (tfNode.getText().equals("")
				&& (this.casBillOption.getIsNoticeBillNo() == null || this.casBillOption
						.getIsNoticeBillNo().booleanValue() == true)
				&& billMaster.getBillType().getName().equals("国内购买")) {
			JOptionPane.showMessageDialog(DgBillDetail.this,
					"[国内购买]需在[备注栏]填写发票号,若不想备注发票号请在\n\r"
							+ "[单据中心]--[参数设置]--[单据选项]--[国内购买是否备注发票号]前取消勾选",
					"资料不全", 1);
			return false;
		}
		if (!tfNode.getText().equals("")
				&& (this.casBillOption.getIsNoticeBillNo() == null || this.casBillOption
						.getIsNoticeBillNo().booleanValue() == true)
				&& billMaster.getBillType().getName().equals("国内购买")
				&& tfNode.getText().trim().length() != 8) {
			JOptionPane.showMessageDialog(DgBillDetail.this,
					"请在备注栏位填写备注发票号时,请将其长度设置为8位！", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		// if (currBillDetail.getPtAmount() != currBillDetail.getHsAmount()
		// && currBillDetail.getHsAmount().doubleValue() == 0) {
		// JOptionPane.showMessageDialog(DgBillDetail.this,
		// "收货数量不为0时，折算报关数量不能为0,请填入有效值", "资料不全", 1);
		// return false;
		// }
		//
		// 单据号
		//
		int billCode = Integer
				.parseInt(this.billMaster.getBillType().getCode());
		String productNo = this.tfProductNo.getText().trim();// 制单号
		// System.out.println("------------------"+productNo + " " + billCode);
		if ("".equals(productNo) == true
				&& (billCode == 2101 || billCode == 2102 || billCode == 1006 || billCode == 2002)) {

			LotControl lotControl = ErpBillParameterCommonVars.getLotControl();

			if (billCode == 2101 // 2101 直接出口
					&& lotControl.getIsDirectExportNeedLot() != null
					&& lotControl.getIsDirectExportNeedLot().booleanValue() == true) {
				JOptionPane.showMessageDialog(this,
						"您的参数设置中 [直接出口类型] 需要填入制单号！", "提示！", 2);
				return false;
			} else if (billCode == 2102 // 2102 结转出口
					&& lotControl.getIsTransferFactoryExportNeedLot() != null
					&& lotControl.getIsTransferFactoryExportNeedLot()
							.booleanValue() == true) {
				JOptionPane.showMessageDialog(this,
						"您的参数设置中 [结转出口类型] 需要填入制单号！", "提示！", 2);
				return false;
			} else if (billCode == 1006 // 1006 车间返回
					&& lotControl.getIsWorkshopBackNeedLot() != null
					&& lotControl.getIsWorkshopBackNeedLot().booleanValue() == true) {

				JOptionPane.showMessageDialog(this,
						"您的参数设置中 [车间返回类型] 需要填入制单号！", "提示！", 2);
				return false;
			} else if (billCode == 2002// 2002 车间入库
					&& lotControl.getIsWorkshopImportNeedLot() != null
					&& lotControl.getIsWorkshopImportNeedLot().booleanValue() == true) {
				JOptionPane.showMessageDialog(this,
						"您的参数设置中 [车间入库类型] 需要填入制单号！", "提示！", 2);
				return false;
			}
		}

		// 对工厂数量的控制
		String productNo1 = tfProductNo.getText().trim();
		Double ptAmount = 0.0;
		Double currPtAmount = 0.0;

		if ((billCategory == SBillType.MATERIEL_OUT || billCategory == SBillType.PRODUCE_OUT)
				&& !productNo1.equals("") && productNo1 != null) {
			/**
			 * currPtAmount 实际出仓的工厂数量
			 */

			List curbillTypes = this.casAction.findBillType(new Request(
					CommonVars.getCurrUser()), billCategory);

			currPtAmount = casAction.getPtAmountByproductNo(new Request(
					CommonVars.getCurrUser()), currBillDetail, curbillTypes,
					productNo1)
					- (count == null ? 0.0 : count)
					+ Double.valueOf(tfPtAmount.getText().trim() == null
							&& "".equals(tfPtAmount.getText().trim()) ? "0.0"
							: tfPtAmount.getText().trim());

			if (billCategory == SBillType.MATERIEL_OUT) {
				billCategory = SBillType.MATERIEL_IN;
			} else if (billCategory == SBillType.PRODUCE_OUT) {
				billCategory = SBillType.PRODUCE_IN;
			}
			/**
			 * currPtAmount 实际进仓的工厂数量
			 */

			List billTypes = this.casAction.findBillType(new Request(CommonVars
					.getCurrUser()), billCategory);

			ptAmount = casAction.getPtAmountByproductNo(new Request(CommonVars
					.getCurrUser()), currBillDetail, billTypes, productNo1);

		}

		if(cbbWareSet.getSelectedItem() == null) {
			if("料件期初单".equals(this.billMaster.getBillType().getName())) {
			} else if("委外期初单".equals(this.billMaster.getBillType().getName())) {
			} else if("在产品期初单".equals(this.billMaster.getBillType().getName())) {
			} else if("成品期初单".equals(this.billMaster.getBillType().getName())) {
			} else if("已交货未结转期初单".equals(this.billMaster.getBillType().getName())) {
			} else if("已结转未交货期初单".equals(this.billMaster.getBillType().getName())) {
			} else if("半成品期初单".equals(this.billMaster.getBillType().getName())) {
			} else if("边角料期初单".equals(this.billMaster.getBillType().getName())) {
			} else if("已打税未出库期初单".equals(this.billMaster.getBillType().getName())) {
			} else {
				JOptionPane.showMessageDialog(this,
						"仓库不能为空！", "提示！", 2);
				return false;
			}
		}
		
		return true;
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
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
			defaultFormatterFactory.setNullFormatter(getNumberFormatter());
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
			decimalFormat.setMaximumFractionDigits(6);
			numberFormatter = new NumberFormatter();
			numberFormatter.setFormat(decimalFormat);
		}
		return numberFormatter;
	}

	/**
	 * This method initializes cbbVersion
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JComboBox getCbbVersion() {
		if (cbbVersion == null) {
			cbbVersion = new JComboBox();
			cbbVersion.setBounds(359, 70, 117, 25);
		/*	cbbVersion.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusGained(java.awt.event.FocusEvent e) {
					if(((MaterialBomVersion)cbbVersion
							.getSelectedItem()).getVersion()!=null){
						int version = ((MaterialBomVersion)cbbVersion
								.getSelectedItem()).getVersion();
						cbbVersion.setToolTipText(String.valueOf(version));
					}
				}
			});*/
		}
		
		return cbbVersion;
	}

	/**
	 * This method initializes defaultFormatterFactory1
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory1() {
		if (defaultFormatterFactory1 == null) {
			defaultFormatterFactory1 = new DefaultFormatterFactory();
			defaultFormatterFactory1.setDefaultFormatter(getNumberFormatter1());
			defaultFormatterFactory1.setDisplayFormatter(getNumberFormatter1());
			defaultFormatterFactory1.setEditFormatter(getNumberFormatter1());
		}
		return defaultFormatterFactory1;
	}

	/**
	 * This method initializes numberFormatter1
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter1() {
		if (numberFormatter1 == null) {
			numberFormatter1 = new NumberFormatter();
			DecimalFormat decimalFormat1 = new DecimalFormat(); // @jve:decl-index=0:
			decimalFormat1.setParseIntegerOnly(true);
			numberFormatter1.setFormat(decimalFormat1);
		}
		return numberFormatter1;
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		// cmbCurr.setModel(CustomBaseModel.getInstance().getCurrModel());
		// cmbCurr.setRenderer(CustomBaseRender.getInstance().getRender());
		setState();
		// 初始化仓库
		List list = materialManageAction.findWareSet(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbWareSet.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbWareSet.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbWareSet, "code", "name");
		this.cbbWareSet.setSelectedItem(null);
		/**
		 * wss2010.08.24添加以下内容 如果是残次品，商品料号选择不可用，备注不可修改
		 */
		int billType = billMaster.getBillType().getBillType().intValue();

		if (9 == billType || 10 == billType) {
			btnSelect.setEnabled(false);
			tfNode.setEditable(false);
		}
		

		// CustomFormattedTextFieldUtils.addAutoCalcListener(this.tfPtPrice,
		// new AutoCalcListener() {
		// public void run() {
		// doCaclHsPrice();
		// }
		// });
		//		
		// CustomFormattedTextFieldUtils.addAutoCalcListener(this.tfHsAmount,
		// new AutoCalcListener() {
		// public void run() {
		// doCaclHsMoney();
		// doCaclNetWeight();
		// }
		// });
		//		
		// CustomFormattedTextFieldUtils.addAutoCalcListener(this.tfPtAmount,
		// new AutoCalcListener() {
		// public void run() {
		// doCaclHsAmout();
		// }
		// });

		// CustomFormattedTextFieldUtils.addAutoCalcListener(this.tfHsPrice,
		// new AutoCalcListener() {
		// public void run() {
		// doCaclHsMoney();
		// }
		// });

	}

	/**
	 * 转换
	 * 
	 * @param value
	 * @return
	 */
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
	 * 填充单据明细来自海关帐对照表
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public BillDetail fillBillDetail(FactoryAndFactualCustomsRalation temp) {
		BillDetail billDetail = this.getInstanceBillDetail();
		return fillBillDetail(temp, billDetail);
	}

	/**
	 * 填充单据明细来自报关常用工厂物料
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public BillDetail fillBillDetails(Materiel temp) {
		BillDetail billDetail = this.getInstanceBillDetail();
		return fillBillDetails(temp, billDetail);
	}

	/**
	 * 得到对应表中的单净重
	 */
	public void fillInvNetWeight(String billType, BillDetail billList) {
		String MaterielPtNo = billList.getPtPart();

		invNetWeight = ErpBillCenterQuery.getInstance()
				.getFactoryAndFactualCustomsRalationMaterielForNetWeight(
						billType, MaterielPtNo);

	}

	public void fillInvNetWeightM(String ptNetWeight) {
		invNetWeight = ptNetWeight;
	}

	/**
	 * 实例化其子类
	 * 
	 * @return
	 */
	private BillDetail getInstanceBillDetail() {
		BillDetail billDetail = null;
		int sBillType = billType;

		if (sBillType == SBillType.PRODUCE_IN
				|| sBillType == SBillType.PRODUCE_OUT) {
			billDetail = new BillDetailProduct();

		} else if (sBillType == SBillType.MATERIEL_IN
				|| sBillType == SBillType.MATERIEL_OUT) {
			billDetail = new BillDetailMateriel();

		} else if (sBillType == SBillType.FIXTURE_IN
				|| sBillType == SBillType.FIXTURE_OUT) {
			billDetail = new BillDetailFixture();

		} else if (sBillType == SBillType.HALF_PRODUCE_IN
				|| sBillType == SBillType.HALF_PRODUCE_OUT) {
			billDetail = new BillDetailHalfProduct();

		} else if (sBillType == SBillType.LEFTOVER_MATERIEL_IN
				|| sBillType == SBillType.LEFTOVER_MATERIEL_OUT) {
			billDetail = new BillDetailLeftoverMateriel();

		} else if (sBillType == SBillType.REMAIN_PRODUCE_IN
				|| sBillType == SBillType.REMAIN_PRODUCE_OUT) {
			billDetail = new BillDetailRemainProduct();
		}
		return billDetail;
	}

	/**
	 * 填充单据明细来自海关帐对照表
	 * 
	 * @param temp
	 * @param billDetail
	 * @return
	 */
	private BillDetail fillBillDetail(FactoryAndFactualCustomsRalation temp,
			BillDetail billDetail) {
		billDetail.setBillMaster(this.billMaster);
		billDetail.setCompany(CommonVars.getCurrUser().getCompany());
		Materiel m = temp.getMateriel();
		StatCusNameRelationHsn s = temp.getStatCusNameRelationHsn();
		//
		// 公司料件对象
		//        
		billDetail.setPtName(m.getFactoryName());
		billDetail.setPtPart(m.getPtNo());
		billDetail.setPtPrice(m.getPtPrice());
		billDetail.setPtSpec(m.getFactorySpec());
		billDetail.setPtUnit(m.getCalUnit());
		billDetail.setHsPrice(m.getPtPrice());
		if (s.getComplex() != null)
			complex = s.getComplex();
		// billDetail.setPtAmount(m.getAmount());
		billDetail.setPtPrice(m.getPtPrice());

		String strVersion = m.getPtVersion();
		if (strVersion != null && !"".equals(strVersion)) {
			try {
				Integer version = Integer.valueOf(strVersion);
				billDetail.setVersion(version);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		//
		// 折报关数量 = 工厂数量 * 单位折算
		//

		// 折算比例
		billDetail.setUnitConvert((Double) temp.getUnitConvert());
		double unitConvert = (Double) temp.getUnitConvert() == null ? 1.0
				: (Double) temp.getUnitConvert();
		//
		// 折算报关数量setHsAmount()
		//
		Double ptAmount = billDetail.getPtAmount() == null ? 0.0 : billDetail
				.getPtAmount();
		Double hsAmount = ptAmount * unitConvert;
		hsAmount = (hsAmount.equals(Double.NaN) ? 0.0 : hsAmount);
		billDetail.setHsAmount(hsAmount);
		//
		// 海关单价
		//
		Double ptPrice = billDetail.getPtPrice() == null ? 0.0 : billDetail
				.getPtPrice();
		Double hsPrice = (ptPrice / unitConvert);
		hsPrice = (hsPrice.equals(Double.NaN) ? 0.0 : hsPrice);
		billDetail.setHsPrice(hsPrice);

		// billDetail.setInNetWeight((Double) m.getPtNetWeight());
		// double unitNetWeight = (Double) m.getPtNetWeight() == null ? 1.0
		// : (Double) m.getPtNetWeight();
		// billDetail.setNetWeight(ptAmount * unitNetWeight);

		// =============报关资料填充
		fillHsInfo(s, billDetail);

		return billDetail;
	}

	/**
	 * 填充单据明细来自报关常用工厂物料
	 * 
	 * @param temp
	 * @param billDetail
	 * @return
	 */
	private BillDetail fillBillDetails(Materiel temp, BillDetail billDetail) {
		billDetail.setBillMaster(this.billMaster);
		billDetail.setCompany(CommonVars.getCurrUser().getCompany());

		// 公司料件对象
		billDetail.setPtName(temp.getFactoryName());
		billDetail.setPtPart(temp.getPtNo());
		billDetail.setPtPrice(temp.getPtPrice());
		billDetail.setPtSpec(temp.getFactorySpec());
		billDetail.setPtUnit(temp.getCalUnit());
		billDetail.setHsPrice(temp.getPtPrice());
		if (temp.getComplex() != null)
			complex = temp.getComplex();
		// billDetail.setPtAmount(m.getAmount());
		billDetail.setPtPrice(temp.getPtPrice());

		String strVersion = temp.getPtVersion();
		if (strVersion != null && !"".equals(strVersion)) {
			try {
				Integer version = Integer.valueOf(strVersion);
				billDetail.setVersion(version);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		// 折报关数量 = 工厂数量 * 单位折算
		// 折算比例
		billDetail.setUnitConvert((Double) temp.getUnitConvert());
		double unitConvert = (Double) temp.getUnitConvert() == null ? 1.0
				: (Double) temp.getUnitConvert();

		// 折算报关数量setHsAmount()
		// Double ptAmount = billDetail.getPtAmount() == null ? 0.0 : billDetail
		// .getPtAmount();
		Double ptAmount = 1d;
		Double hsAmount = ptAmount * unitConvert;
		hsAmount = (hsAmount.equals(Double.NaN) ? 0.0 : hsAmount);
		billDetail.setHsAmount(hsAmount);
		billDetail.setPtAmount(ptAmount);

		// 海关单价
		Double ptPrice = billDetail.getPtPrice() == null ? 0.0 : billDetail
				.getPtPrice();
		Double hsPrice = (ptPrice / unitConvert);
		hsPrice = (hsPrice.equals(Double.NaN) ? 0.0 : hsPrice);
		billDetail.setHsPrice(hsPrice);

		// billDetail.setInNetWeight((Double) m.getPtNetWeight());
		double unitNetWeight = (Double) temp.getPtNetWeight() == null ? 1.0
				: (Double) temp.getPtNetWeight();
		System.out.println("净重：" + temp.getPtNetWeight());
		billDetail.setNetWeight(unitNetWeight);
		System.out.println("净重：" + billDetail.getNetWeight());

		// 报关资料填充wss:2010.06.09新添
		fillHsInfos(temp, billDetail);

		return billDetail;
	}

	/**
	 * 填充报关资料（半成品的）
	 * 
	 * @author wss
	 */
	private BillDetail fillHsInfos(Materiel tmp, BillDetail billDetail) {
		if (tmp != null) {
			if (this.currBillDetail == null) {
				billDetail.setComplex(tmp.getComplex());
				billDetail.setHsName(tmp.getPtName());
				hsUnit = tmp.getPtUnit();
				billDetail.setHsUnit(tmp.getPtUnit());

				billDetail.setHsSpec(tmp.getPtSpec());
				// billDetail.setEmsNo(tmp.getEmsNo());半成品没有手册号
				// if (tmp.getVersion() != null) { 半成品也没有报关商品版本号
				// billDetail.setHsVersion(tmp.getVersion());
				// }
				return billDetail;
			} else {
				// tfComplex.setText(tmp.getComplex().getCode());
				tmpComplex = tmp.getComplex();
				tmpHsUnit = tmp.getPtUnit();
				tmpUnitConvert = tmp.getUnitConvert();
				tfHsName.setText(tmp.getPtName());
				if (tmp.getComplex() != null)
					tfComplex.setText(tmp.getComplex().getCode());
				else
					tfComplex.setText(null);
				tfHsSpec.setText(tmp.getPtSpec());
				tfHsUnit.setText(tmp.getPtUnit() == null ? "" : tmp.getPtUnit()
						.getName());
				// tfEmsNo.setText(tmp.getEmsNo());
				// tfHsVersion.setValue(tmp.getVersion());
				currBillDetail.setComplex(tmp.getComplex());
				currBillDetail.setHsName(tmp.getPtName());
				hsUnit = tmp.getPtUnit();
				currBillDetail.setHsUnit(tmp.getPtUnit());
				currBillDetail.setHsSpec(tmp.getPtSpec());
				// currBillDetail.setEmsNo(tmp.getEmsNo());
				// if (tmp.getVersion() != null) {
				// currBillDetail.setHsVersion(tmp.getVersion());
				// }
				return currBillDetail;
			}
		}
		return billDetail;
	}

	/**
	 * 传入的对象是一个通过InnerMergeData对象填充过的对象
	 * 
	 * @param billDetail
	 * @return
	 */
	private BillDetail fillBillDetailFromWindow(BillDetail billDetail) {
		// billDetail.setCurr((Curr) cmbCurr.getSelectedItem());
		billDetail.setCustomNo(tfCustomNo.getText());
		if (tfPtAmount.getText() != null && !tfPtAmount.getText().equals("")) {
			billDetail
					.setPtAmount(strToDouble(tfPtAmount.getValue().toString()));
		} else {
			billDetail.setPtAmount(Double.valueOf(0));
		}
		if (tfPtPrice.getText() != null && !tfPtPrice.getText().equals("")) {
			billDetail.setPtPrice(strToDouble(tfPtPrice.getValue().toString()));
		} else {
			billDetail.setPtPrice(Double.valueOf(0));
		}
		if (tfHsAmount.getText() != null && !tfHsAmount.getText().equals("")) {
			billDetail
					.setHsAmount(strToDouble(tfHsAmount.getValue().toString()));
		} else {
			billDetail.setHsAmount(Double.valueOf(0));
		}

		if (tfHsPrice.getText() != null && !tfHsPrice.getText().equals("")) {
			billDetail.setHsPrice(strToDouble(tfHsPrice.getValue().toString()));
		} else {
			billDetail.setHsPrice(Double.valueOf(0));
		}
		if (tfMoney.getText() != null && !tfMoney.getText().equals("")) {
			billDetail.setMoney(strToDouble(tfMoney.getValue().toString()));
		} else {
			billDetail.setMoney(Double.valueOf(0));
		}

		billDetail.setProductNo(tfProductNo.getText());
		billDetail.setCustomNo(tfCustomNo.getText());

		if (tfCustomNum.getText() != null && !tfCustomNum.getText().equals("")) {
			billDetail.setCustomNum(strToDouble(tfCustomNum.getValue()
					.toString()));
		} else {
			billDetail.setCustomNum(Double.valueOf(0));
		}
		if (tfNetWeight.getText() != null && !tfNetWeight.getText().equals("")) {
			billDetail.setNetWeight(strToDouble(tfNetWeight.getValue()
					.toString()));
		} else {
			billDetail.setNetWeight(Double.valueOf(0));
		}
		if (this.cbbVersion.getSelectedItem() != null) {
			billDetail.setVersion(((MaterialBomVersion) this.cbbVersion
					.getSelectedItem()).getVersion());
		} else {
			this.version.setVersion(0);
			billDetail.setVersion(0);
			this.cbbVersion.setSelectedItem(version);
		}
		billDetail.setWareSet((WareSet) this.cbbWareSet.getSelectedItem());
		billDetail.setNote(this.tfNode.getText());
		// 以下两个是设备用到
		if (tfLocation.getText() != null && !tfLocation.getText().equals(""))
			billDetail.setLocation(tfLocation.getText());// 设备－存放地点
		if (tfHoldMan.getText() != null && !tfHoldMan.getText().equals(""))
			billDetail.setHoldMan(tfHoldMan.getText());// 设备－保管人

		return billDetail;
	}

	/**
	 * 填充对象
	 * 
	 * @param billDetail
	 */
	private void fillData(BillDetail billDetail) {
		tfPtPart.setText(billDetail.getPtPart());
		tfPtName.setText(billDetail.getPtName());
		tfPtSpec.setText(billDetail.getPtSpec());
		this.cbbWareSet.setSelectedItem(billDetail.getWareSet());
		List list = materialManageAction.findMaterialVersion(new Request(
				CommonVars.getCurrUser(), true), tfPtPart.getText());

		this.cbbVersion.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbVersion.setRenderer(CustomBaseRender.getInstance().getRender(
				"version", "notes", 70, 100));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbVersion, "notes", "version");
		if (billDetail.getVersion() != null) {
			version.setVersion(billDetail.getVersion());
			version.setNotes(billDetail.getNote());
		} else {
			version.setVersion(0);
		}
		this.cbbVersion.setSelectedItem(version);

		if (billDetail.getPtUnit() != null) {
			tfPtUnit.setText(billDetail.getPtUnit().getName());
			mtUnit = billDetail.getPtUnit();
		}
		BillType b = billDetail.getBillMaster().getBillType();
		int billType = b.getBillType();
		tfPtProperty.setText(getMaterielTypeString(billType));

		if (billDetail.getPtAmount() != null) {
			tfPtAmount.setValue(billDetail.getPtAmount());
		} else {
			tfPtAmount.setValue(new Double(0));
		}
		if (billDetail.getPtPrice() != null) {
			tfPtPrice.setValue(billDetail.getPtPrice());
		} else {
			tfPtPrice.setValue(new Double(0));
		}
		if (billDetail.getHsAmount() != null) {
			tfHsAmount.setValue(billDetail.getHsAmount());
		} else {
			tfHsAmount.setValue(new Double(0));
		}

		Double hsPrice = billDetail.getHsPrice();
		if (hsPrice != null) {
			if (hsPrice.equals(Double.NEGATIVE_INFINITY)
					|| hsPrice.equals(Double.POSITIVE_INFINITY)
					|| hsPrice.equals(Double.NaN)) {
				tfHsPrice.setValue(new Double(0));
			} else {
				tfHsPrice.setValue(hsPrice);
			}
		} else {
			tfHsPrice.setValue(new Double(0));
		}

		if (billDetail.getMoney() != null) {
			tfMoney.setValue(billDetail.getMoney());
		} else {
			tfMoney.setValue(new Double(0));
		}

		tfProductNo.setText(billDetail.getProductNo());
		tfCustomNo.setText(billDetail.getCustomNo());

		if (billDetail.getCustomNum() != null) {
			tfCustomNum.setValue(billDetail.getCustomNum());
		} else {
			tfCustomNum.setValue(new Double(0));
		}

		if (billDetail.getNetWeight() != null) {
			tfNetWeight.setValue(billDetail.getNetWeight());
		} else {
			tfNetWeight.setValue(new Double(0));
		}

		// 以下两个是设备用到
		if (billDetail.getLocation() != null) {
			tfLocation.setText(billDetail.getLocation());// 设备－存放地点
		}
		if (billDetail.getHoldMan() != null) {
			tfHoldMan.setText(billDetail.getHoldMan());// 设备－保管人
		}

		// 报关资料
		// if (billDetail.getComplex() != null) {
		// tfComplex.setText(billDetail.getComplex().getCode());
		// }
		tmpComplex = billDetail.getComplex();
		tmpHsUnit = billDetail.getHsUnit();
		tmpUnitConvert = billDetail.getUnitConvert();

		tfHsName.setText(billDetail.getHsName());
		if (billDetail.getComplex() != null)
			tfComplex.setText(billDetail.getComplex().getCode());
		else
			tfComplex.setText(null);
		tfHsSpec.setText(billDetail.getHsSpec());
		if (billDetail.getHsUnit() != null) {
			tfHsUnit.setText(billDetail.getHsUnit().getName());
		}
		if (billDetail.getHsVersion() != null) {
			tfHsVersion.setValue(billDetail.getHsVersion());
		} else {
			tfHsVersion.setText("");
		}
		tfEmsNo.setText(billDetail.getEmsNo());
		//
		// 显示报关折算比率
		//
		if (dataState == DataState.EDIT) {
			// String materielType = String.valueOf(b.getProduceType());
			// Double unitConvert = this.casAction.getUnitConvertByPtNo(
			// new Request(CommonVars.getCurrUser(), true), materielType,
			// billDetail.getPtPart());
			if (billDetail.getPtAmount() != null
					&& billDetail.getHsAmount() != null) {

				Double unitConver = billDetail.getHsAmount()
						/ billDetail.getPtAmount();
				if (unitConver.equals(Double.NEGATIVE_INFINITY)
						|| unitConver.equals(Double.POSITIVE_INFINITY)
						|| unitConver.equals(Double.NaN)) {
					billDetail.setUnitConvert(0.0);
				} else {
					billDetail.setUnitConvert(unitConver);
				}
			}
			// billDetail.setUnitConvert(unitConvert);
			// 设置参数
			this.setComplex(billDetail.getComplex());
			this.setHsUnit(billDetail.getHsUnit());
			this.setMtUnit(billDetail.getPtUnit());

		}
		Double unitConvert = billDetail.getUnitConvert() == null ? 1.0
				: billDetail.getUnitConvert();

		if (unitConvert.equals(Double.NEGATIVE_INFINITY)
				|| unitConvert.equals(Double.POSITIVE_INFINITY)
				|| unitConvert.equals(Double.NaN)) {
			billDetail.setUnitConvert(0.0);
			unitConvert = billDetail.getUnitConvert();
		}

		Double unitConverts = billDetail.getUnitWeight() == null ? 1.0
				: billDetail.getUnitWeight();
		
		
		
		if (unitConverts.equals(Double.NEGATIVE_INFINITY)
				|| unitConverts.equals(Double.POSITIVE_INFINITY)
				|| unitConverts.equals(Double.NaN)) {
			unitConverts = billDetail.getUnitWeight();
		}
		BigDecimal bgs = new BigDecimal(unitConverts);
		String unitConvertStrs = bgs.setScale(6, BigDecimal.ROUND_HALF_UP)
				.toString();

		BigDecimal bg = new BigDecimal(unitConvert);
		String unitConvertStr = bg.setScale(6, BigDecimal.ROUND_HALF_UP)
				.toString();
		
		if (unitConverts == 0 || unitConverts.equals(0.0)) {
			unitConvertStrs = invNetWeight;
		}
		lbUnitConvert.setText(unitConvertStr);
		lbUnitweight.setText(unitConvertStrs);
		this.tfNode.setText(billDetail.getNote());
		this.tfTakeBillNo.setText(billDetail.getTakeBillNo());
		this.tfOderBillNo.setText(billDetail.getOderBillNo());
		tmpComplex = billDetail.getComplex();
		tmpUnitConvert = billDetail.getUnitConvert();
		tmpHsUnit = billDetail.getHsUnit();
	}

	/**
	 * 修改对象
	 * 
	 */
	private void modifyBillDetail() {
		saveBillDetail();
		if (vaildatorDataIsNull()) {
			return;
		}
		currBillDetail = casAction.saveBillDetail(new Request(CommonVars
				.getCurrUser()), currBillDetail);
		// String tt = currBillDetail.getClass().getName();
		tableModel.updateRow(currBillDetail);
	}

	private boolean vaildatorDataIsNull() {
		if (null != currBillDetail) {
			List list = casAction.findOwpBillAndBillDetail(new Request(
					CommonVars.getCurrUser()), currBillDetail.getId());
			if (list.size() > 0) {
				JOptionPane.showMessageDialog(null, "单据已被外发加工登记表引用，不得修改！",
						"警告", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		}
		return false;
	}

	/** 转换 */
	private Double strToDouble(String value) { // 转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return (Double) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 转换 */
	private Integer strToInteger(String value) { // 转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				return null;
			}
			getNumberFormatter().setValueClass(Integer.class);
			return (Integer) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获得料件类型来自单据类型
	 * 
	 * @param billType
	 * @return
	 */
	public String getMaterielType(int billType, boolean isMateriel) {
		String materielType = null;

		if (billType == SBillType.MATERIEL_IN
				|| billType == SBillType.MATERIEL_OUT) {
			materielType = MaterielType.MATERIEL;
		} else if (billType == SBillType.PRODUCE_IN
				|| billType == SBillType.PRODUCE_OUT) {
			materielType = MaterielType.FINISHED_PRODUCT;
		} else if (billType == SBillType.HALF_PRODUCE_IN
				|| billType == SBillType.HALF_PRODUCE_OUT
				|| billType == SBillType.HALF_PRODUCE_INOUT) {
			materielType = MaterielType.SEMI_FINISHED_PRODUCT;
		} else if (billType == SBillType.FIXTURE_IN
				|| billType == SBillType.FIXTURE_OUT) {
			materielType = MaterielType.MACHINE;
		} else if (billType == SBillType.LEFTOVER_MATERIEL_IN
				|| billType == SBillType.LEFTOVER_MATERIEL_OUT
				|| billType == SBillType.LEFTOVER_MATERIEL_INOUT) {
			materielType = MaterielType.REMAIN_MATERIEL;
		} else if (billType == SBillType.REMAIN_PRODUCE_IN
				|| billType == SBillType.REMAIN_PRODUCE_OUT
				|| billType == SBillType.REMAIN_PRODUCE_INOUT) {
			// materielType = MaterielType.BAD_PRODUCT;
			if (isMateriel) {
				materielType = MaterielType.MATERIEL;
			} else {
				materielType = MaterielType.FINISHED_PRODUCT;
			}

		}
		return materielType;
	}

	/**
	 * 类型名称
	 * 
	 * @param billType
	 * @return
	 */
	public String getMaterielTypeString(int billType) {
		String materielTypeString = null;
		if (billType == SBillType.MATERIEL_IN
				|| billType == SBillType.MATERIEL_OUT) {
			materielTypeString = "料件";
		} else if (billType == SBillType.PRODUCE_IN
				|| billType == SBillType.PRODUCE_OUT) {
			materielTypeString = "成品";
		} else if (billType == SBillType.HALF_PRODUCE_IN
				|| billType == SBillType.HALF_PRODUCE_OUT
				|| billType == SBillType.HALF_PRODUCE_INOUT) {
			materielTypeString = "半成品";
		} else if (billType == SBillType.FIXTURE_IN
				|| billType == SBillType.FIXTURE_OUT) {
			materielTypeString = "设备";
		} else if (billType == SBillType.LEFTOVER_MATERIEL_IN
				|| billType == SBillType.LEFTOVER_MATERIEL_OUT
				|| billType == SBillType.LEFTOVER_MATERIEL_INOUT) {
			materielTypeString = "边角料";
		} else if (billType == SBillType.REMAIN_PRODUCE_IN
				|| billType == SBillType.REMAIN_PRODUCE_OUT
				|| billType == SBillType.REMAIN_PRODUCE_INOUT) {
			materielTypeString = "残次品";
		}
		return materielTypeString;
	}

	/**
	 * 计算报关数量
	 * 
	 */
	private void doCaclHsAmout() {
		double ptAmount = 0.0;
		if (tfPtAmount.getValue() != null) {
			ptAmount = Double.valueOf(tfPtAmount.getValue().toString().trim()
					.equals("") == true ? "0.0" : tfPtAmount.getValue()
					.toString());
		}
		//
		// 折报关数量 = 工厂数量 * 单位折算
		//
		// double unitConvert2 = Double.valueOf(lbUnitweight.getText());
		// System.out.println("unitConvert2=" + unitConvert2);
		// BillDetail currBillDetail2 = (BillDetail)
		// fatherTableModel.getCurrentRow();

		currBillDetail = (BillDetail) tableModel.getCurrentRow();
		double unitConvert = currBillDetail.getUnitConvert() == null ? 1.0
				: currBillDetail.getUnitConvert(); // 折算比例
		System.out.println("unitConvert=" + unitConvert);
		if (unitConvert == 0.0) {
			tfHsAmount.setValue(0.0);
		} else {
			tfHsAmount.setValue(ptAmount * unitConvert);
		}
	}

	/**
	 * 净重 = 工厂数量 * 单位净重
	 */
	private void doCaclNetWeight() {
		double ptAmount = 0.0;
		if (tfPtAmount.getValue() != null) {
			ptAmount = Double.valueOf(tfPtAmount.getValue().toString().trim()
					.equals("") == true ? "0.0" : tfPtAmount.getValue()
					.toString());
		}
		//
		// 净重 = 工厂数量 * 单位净重
		//
		BigDecimal dg = new BigDecimal(ptAmount
				* Double.parseDouble(invNetWeight.toString()));
		tfNetWeight.setValue(dg.setScale(6, BigDecimal.ROUND_HALF_UP)
				.doubleValue());

	}

	// /**
	// * 计算仓库数量
	// */
	// public void doWarehouses() {
	// System.out.println("被调用了");
	// double ptAmount = 0.0;
	// if (tfPtAmount.getValue() != null) {
	// ptAmount = Double.valueOf(tfPtAmount.getValue().toString().trim()
	// .equals("") == true ? "0.0" : tfPtAmount.getValue()
	// .toString());
	// System.out.println("ptAmount=" + ptAmount);
	// }
	// String ptPart = "";
	// WareSet wareSet = null;
	// if (tfPtPart.getText() != null) {
	// ptPart = tfPtPart.getText().trim().equals("") == true ? ""
	// : tfPtPart.getText();
	// System.out.println("ptPart=" + ptPart);
	// }
	// if (cbbWareSet.getSelectedItem() != null) {
	// wareSet = (WareSet) this.cbbWareSet.getSelectedItem();
	// System.out.println("wareSet.name=" + wareSet.getName());
	// }
	// Date vailDate = currBillDetail.getBillMaster().getValidDate() == null ?
	// null
	// : currBillDetail.getBillMaster().getValidDate();
	// System.out.println("vailDate=" + vailDate);
	//
	// if (currBillDetail.getBillMaster().getBillType().getBillType() ==
	// SBillType.MATERIEL_IN
	// || currBillDetail.getBillMaster().getBillType().getBillType() ==
	// SBillType.MATERIEL_OUT) {
	//			
	// /**
	// * 此 料号 此 仓库 已生效进项单据的工厂数量
	// */
	// Double warehousesMaterielIn = this.casAction
	// .findWarehousesMaterielIn(new Request(CommonVars
	// .getCurrUser()), ptPart, wareSet, vailDate, true);
	// System.out.println("warehousesMaterielIn=" + warehousesMaterielIn);
	//			
	// /**
	// * 此 料号 此 仓库 已生效出项单据的工厂数量
	// */
	// Double warehousesMaterielOut = this.casAction
	// .findWarehousesMaterielOut(new Request(CommonVars
	// .getCurrUser()), ptPart, wareSet, vailDate, true);
	// System.out
	// .println("warehousesMaterielOut=" + warehousesMaterielOut);
	//			
	// // 结余进
	// /**
	// * 此 料号 此 仓库 未生效进项单据的工厂数量
	// */
	// Double warehouseBalanceMaterieiIn = this.casAction
	// .findWarehousesMaterielIn(new Request(CommonVars
	// .getCurrUser()), ptPart, wareSet, vailDate, false);
	// System.out.println("warehouseBalanceMaterieiIn="
	// + warehouseBalanceMaterieiIn);
	// // 结余出
	// /**
	// * 此 料号 此 仓库 未生效出项单据的工厂数量
	// */
	// Double warehouseBalanceMaterieiOut = this.casAction
	// .findWarehousesMaterielOut(new Request(CommonVars
	// .getCurrUser()), ptPart, wareSet, vailDate, false);
	// System.out.println("warehouseBalanceMaterieiOut="
	// + warehouseBalanceMaterieiOut);
	// if (warehousesMaterielIn != null && warehousesMaterielOut != null) {
	// Double warehouses = warehousesMaterielIn
	// - warehousesMaterielOut;
	// tfWarehouses.setValue(warehouses);
	// } else if (warehousesMaterielIn == null
	// && warehousesMaterielOut == null) {
	// tfWarehouses.setValue(0.0);
	// } else if (warehousesMaterielIn != null
	// && warehousesMaterielOut == null) {
	// warehousesMaterielOut = 0.0;
	// Double warehouses = warehousesMaterielIn
	// - warehousesMaterielOut;
	// tfWarehouses.setValue(warehouses);
	// } else if (warehousesMaterielIn == null
	// && warehousesMaterielOut != null) {
	// warehousesMaterielIn = 0.0;
	// Double warehouses = warehousesMaterielIn
	// - warehousesMaterielOut;
	// tfWarehouses.setValue(warehouses);
	// }
	//
	// if (warehouseBalanceMaterieiIn != null
	// && warehouseBalanceMaterieiOut != null) {// - ptAmount
	// Double balance = warehouseBalanceMaterieiIn
	// - warehouseBalanceMaterieiOut;
	// tfBalanceAmount.setValue(balance);
	// } else if (warehouseBalanceMaterieiIn == null
	// && warehouseBalanceMaterieiOut == null) {
	// tfBalanceAmount.setValue(0.0);
	// } else if (warehouseBalanceMaterieiIn != null
	// && warehouseBalanceMaterieiOut == null) {
	// warehouseBalanceMaterieiOut = 0.0;
	// Double balance = warehouseBalanceMaterieiIn
	// - warehouseBalanceMaterieiOut;
	// tfBalanceAmount.setValue(balance);
	// } else if (warehouseBalanceMaterieiIn == null
	// && warehouseBalanceMaterieiOut != null) {
	// warehouseBalanceMaterieiIn = 0.0;
	// Double balance = warehouseBalanceMaterieiIn
	// - warehouseBalanceMaterieiOut;
	// tfBalanceAmount.setValue(balance);
	// }
	// } else if (currBillDetail.getBillMaster().getBillType().getBillType() ==
	// SBillType.PRODUCE_IN
	// || currBillDetail.getBillMaster().getBillType().getBillType() ==
	// SBillType.PRODUCE_OUT) {
	// Double warehousesProductIn = this.casAction
	// .findWarehousesProduceIn(new Request(CommonVars
	// .getCurrUser()), ptPart, wareSet, vailDate, true);
	//
	// Double warehousesProductOut = this.casAction
	// .findWarehousesProduceOut(new Request(CommonVars
	// .getCurrUser()), ptPart, wareSet, vailDate, true);
	//
	// Double warehousesBalanceProductIn = this.casAction
	// .findWarehousesProduceIn(new Request(CommonVars
	// .getCurrUser()), ptPart, wareSet, vailDate, false);
	//
	// Double warehousesBalanceProductOut = this.casAction
	// .findWarehousesProduceOut(new Request(CommonVars
	// .getCurrUser()), ptPart, wareSet, vailDate, false);
	// // 库存数量
	// if (warehousesProductIn != null && warehousesProductOut != null) {
	// Double warehouses = warehousesProductIn - warehousesProductOut;
	// tfWarehouses.setValue(warehouses);
	// } else if (warehousesProductIn == null
	// && warehousesProductOut == null) {
	// tfWarehouses.setValue(0.0);
	// } else if (warehousesProductIn != null
	// && warehousesProductOut == null) {
	// warehousesProductOut = 0.0;
	// Double warehouses = warehousesProductIn - warehousesProductOut;
	// tfWarehouses.setValue(warehouses);
	// } else if (warehousesProductIn == null
	// && warehousesProductOut != null) {
	// warehousesProductIn = 0.0;
	// Double warehouses = warehousesProductIn - warehousesProductOut;
	// tfWarehouses.setValue(warehouses);
	// }
	//
	// // 结余数量
	// if (warehousesBalanceProductIn != null
	// && warehousesBalanceProductOut != null) {// - ptAmount
	// Double balance = warehousesBalanceProductIn
	// - warehousesBalanceProductOut;
	// tfBalanceAmount.setValue(balance);
	// } else if (warehousesBalanceProductIn == null
	// && warehousesBalanceProductOut == null) {
	// tfBalanceAmount.setValue(0.0);
	// } else if (warehousesBalanceProductIn != null
	// && warehousesBalanceProductOut == null) {
	// warehousesBalanceProductOut = 0.0;
	// Double balance = warehousesBalanceProductIn
	// - warehousesBalanceProductOut;
	// tfBalanceAmount.setValue(balance);
	// } else if (warehousesBalanceProductIn == null
	// && warehousesBalanceProductOut != null) {
	// warehousesBalanceProductIn = 0.0;
	// Double balance = warehousesBalanceProductIn
	// - warehousesBalanceProductOut;
	// tfBalanceAmount.setValue(balance);
	// }
	// }
	// }

	//
	// /**
	// * 净重 = 工厂数量 * 单位净重
	// */
	// private void doCaclNetWeights(Double invNetWeight, Double newPtPrice,
	// Double unitConvert) {
	// double ptAmount = 0.0;
	// if (tfPtAmount.getValue() != null) {
	// ptAmount = Double.valueOf(tfPtAmount.getValue().toString().trim()
	// .equals("") == true ? "0.0" : tfPtAmount.getValue()
	// .toString());
	// }
	// // System.out.println("invNetWeight=" + invNetWeight);
	// // System.out.println("newPtPrice=" + newPtPrice);
	// // System.out.println("unitConvert=" + unitConvert);
	// //
	// // //
	// // // 净重 = 工厂数量 * 单位净重
	// // //
	// // BigDecimal dg = new BigDecimal(ptAmount
	// // * Double.parseDouble(invNetWeight.toString()));
	// //
	// System.out.println("tfNetWeight.getValue()="+Double.valueOf(tfNetWeight.getValue().toString()));
	// // this.tfNetWeight.setValue(dg.setScale(6, BigDecimal.ROUND_HALF_UP)
	// // .doubleValue());
	//		
	// BigDecimal hsPrice = new BigDecimal(newPtPrice
	// / Double.parseDouble(unitConvert.toString()));
	//
	// BigDecimal hsAmout = new BigDecimal(ptAmount
	// * Double.parseDouble(unitConvert.toString()));
	// System.out
	// .println("hsPrice.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue()="
	// + hsPrice.setScale(6, BigDecimal.ROUND_HALF_UP)
	// .doubleValue());
	//		
	// // System.out
	// // .println("dg.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue()="
	// // + dg.setScale(6, BigDecimal.ROUND_HALF_UP)
	// // .doubleValue());
	// this.tfPtPrice.setValue(newPtPrice);
	//		
	// // this.tfHsPrice.setValue(hsPrice.setScale(6, BigDecimal.ROUND_HALF_UP)
	// // .doubleValue());
	//		
	// this.tfHsAmount.setValue(hsAmout.setScale(6, BigDecimal.ROUND_HALF_UP)
	// .doubleValue());
	// }

	/**
	 * 计算报关单价
	 * 
	 */
	private void doCaclHsPrice() {
		double ptPrice = 0.0;
		if (tfPtPrice.getValue() != null) {
			ptPrice = Double.valueOf((tfPtPrice.getValue() == null || tfPtPrice
					.getValue().toString().trim().equals("") == true) ? "0.0"
					: tfPtPrice.getValue().toString());
		}
		// 折报关单价 = 工厂单价 / 单位折算
		//
		double unitConvert = currBillDetail.getUnitConvert() == null ? 1.0
				: currBillDetail.getUnitConvert(); // 折算比例
		if (unitConvert == 0) {
			tfHsPrice.setValue(0.0);
		} else {
			tfHsPrice.setValue(ptPrice / unitConvert);
		}
	}

	/**
	 * 根据报关单价反算单价
	 * 
	 */
	private void doCaclPtPrice() {
		double hsPrice = 0.0;
		if (tfHsPrice.getValue() != null) {
			hsPrice = Double.valueOf((tfHsPrice.getValue() == null || tfHsPrice
					.getValue().toString().trim().equals("") == true) ? "0.0"
					: tfHsPrice.getValue().toString());
		}
		// 折报关单价 = 工厂单价 / 单位折算
		//
		double unitConvert = currBillDetail.getUnitConvert() == null ? 1.0
				: currBillDetail.getUnitConvert(); // 折算比例
		if (unitConvert == 0) {
			tfPtPrice.setValue(0.0);
		} else {
			tfPtPrice.setValue(hsPrice * unitConvert);
		}
	}

	/**
	 * 计算报关金额
	 * 
	 */
	private void doCaclHsMoney() {
		double hsAmount = 0.0;
		if (tfHsAmount.getValue() != null) {
			hsAmount = Double.valueOf(tfHsAmount.getValue().toString().trim()
					.equals("") == true ? "0.0" : tfHsAmount.getValue()
					.toString());
		}
		double hsPrice = 0.0;
		if (tfHsPrice.getValue() != null) {
			hsPrice = Double.valueOf(tfHsPrice.getValue().toString().trim()
					.equals("") == true ? "0.0" : tfHsPrice.getValue()
					.toString());
		}

		tfMoney.setValue(hsAmount * hsPrice);

	}

	/**
	 * 设置工厂单据明细表各个对象属性的值
	 * 
	 * @param currBillDetail
	 */
	public void setCurrBillDetail(BillDetail currBillDetail) {
		this.currBillDetail = currBillDetail;
		// System.out.println(currBillDetail.getHsUnit());
		fillData(currBillDetail);
	}

	public void setPtAmount(Double count) {
		this.count = count;
	}

	// /**
	// * 报关商品资料自动对应
	// *
	// * @param ptNo
	// * @param endDate
	// */
	// public BillDetail autoSetFactualCustoms(String ptNo, Date endDate,
	// BillDetail billDetail) {
	// FactoryAndFactualCustomsRalation tmp = casAction.findFactualCustoms(
	// new Request(CommonVars.getCurrUser()), ptNo, endDate);
	// if (billMaster.getBillType().getCode().equals("3005"))
	// return fillBuyInlandHsInfo(tmp.getStatCusNameRelationHsn(),
	// billDetail);
	// else
	// return fillHsInfo(tmp.getStatCusNameRelationHsn(), billDetail);
	// }

	/**
	 * 填充窗体中报关商品资料
	 * 
	 * @param tmp
	 *            选择的报关商品资料
	 * @param billDetail
	 * @return
	 */
	public BillDetail fillHsInfo(StatCusNameRelationHsn tmp,
			BillDetail billDetail) {
		if (tmp != null) {
			if (this.currBillDetail == null) {
				billDetail.setComplex(tmp.getComplex());
				billDetail.setHsName(tmp.getCusName());
				billDetail.setHsUnit(tmp.getCusUnit());
				hsUnit = tmp.getCusUnit();
				billDetail.setHsSpec(tmp.getCusSpec());
				billDetail.setEmsNo(tmp.getEmsNo());
				if (tmp.getVersion() != null) {
					billDetail.setHsVersion(tmp.getVersion());
				}
				return billDetail;
			} else {
				// tfComplex.setText(tmp.getComplex().getCode());
				tmpComplex = tmp.getComplex();
				tmpHsUnit = tmp.getCusUnit();
				tmpUnitConvert = tmp.getUnitConvert();
				tfHsName.setText(tmp.getCusName());
				if (tmp.getComplex() != null)
					tfComplex.setText(tmp.getComplex().getCode());
				else
					tfComplex.setText(null);
				tfHsSpec.setText(tmp.getCusSpec());
				tfHsUnit.setText(tmp.getCusUnit().getName());
				tfEmsNo.setText(tmp.getEmsNo());
				tfHsVersion.setValue(tmp.getVersion());
				currBillDetail.setComplex(tmp.getComplex());
				currBillDetail.setHsName(tmp.getCusName());
				hsUnit = tmp.getCusUnit();
				currBillDetail.setHsUnit(tmp.getCusUnit());
				currBillDetail.setHsSpec(tmp.getCusSpec());
				currBillDetail.setEmsNo(tmp.getEmsNo());
				if (tmp.getVersion() != null) {
					currBillDetail.setHsVersion(tmp.getVersion());
				}
				return currBillDetail;
			}
		} else {
			return billDetail;
		}

	}

	// /**
	// * 填充窗体中报关商品资料
	// *
	// * @param tmp
	// * 选择的报关商品资料
	// * @param billDetail
	// * @return
	// */
	// public BillDetail fillHsInfos(Materiel tmp,
	// BillDetail billDetail) {
	// if (tmp != null) {
	// if (this.currBillDetail == null) {
	// billDetail.setComplex(tmp.getComplex());
	// billDetail.setPtName(tmp.getFactoryName());
	// billDetail.setHsUnit(tmp.getCusUnit());
	// hsUnit = tmp.getCusUnit();
	// billDetail.setHsSpec(tmp.getCusSpec());
	// billDetail.setEmsNo(tmp.getEmsNo());
	// if (tmp.getVersion() != null) {
	// billDetail.setHsVersion(tmp.getVersion());
	// }
	// return billDetail;
	// } else {
	// // tfComplex.setText(tmp.getComplex().getCode());
	// tfHsName.setText(tmp.getCusName());
	// tfHsSpec.setText(tmp.getCusSpec());
	// tfHsUnit.setText(tmp.getCusUnit().getName());
	// tfEmsNo.setText(tmp.getEmsNo());
	// tfHsVersion.setValue(tmp.getVersion());
	// currBillDetail.setComplex(tmp.getComplex());
	// currBillDetail.setHsName(tmp.getCusName());
	// hsUnit = tmp.getCusUnit();
	// currBillDetail.setHsUnit(tmp.getCusUnit());
	// currBillDetail.setHsSpec(tmp.getCusSpec());
	// currBillDetail.setEmsNo(tmp.getEmsNo());
	// if (tmp.getVersion() != null) {
	// currBillDetail.setHsVersion(tmp.getVersion());
	// }
	// return currBillDetail;
	// }
	// } else {
	// return billDetail;
	// }
	//
	// }

	/**
	 * 国内购买时填充窗体中报关商品资料
	 * 
	 * @param tmp
	 *            选择的报关商品资料
	 * @param billDetail
	 * @return
	 */
	// public BillDetail fillBuyInlandHsInfo(StatCusNameRelationHsn tmp,
	// BillDetail billDetail) {
	// if (tmp != null) {
	// if (this.currBillDetail == null) {
	// billDetail.setComplex(tmp.getComplex());
	// billDetail.setHsName(billDetail.getPtName());
	// billDetail.setHsUnit(tmp.getCusUnit());
	// hsUnit = tmp.getCusUnit();
	// billDetail.setHsSpec(billDetail.getPtNameptSpec());
	// billDetail.setEmsNo(tmp.getEmsNo());
	// if (tmp.getVersion() != null) {
	// billDetail.setHsVersion(tmp.getVersion());
	// }
	// return billDetail;
	// } else {
	// tfComplex.setText(tmp.getComplex().getCode());
	// tfHsName.setText(billDetail.getPtName());
	// tfHsSpec.setText(billDetail.getPtNameptSpec());
	// tfHsUnit.setText(tmp.getCusUnit().getName());
	// tfEmsNo.setText(tmp.getEmsNo());
	// tfHsVersion.setValue(tmp.getVersion());
	// currBillDetail.setComplex(tmp.getComplex());
	// currBillDetail.setHsName(billDetail.getPtName());
	// hsUnit = tmp.getCusUnit();
	// currBillDetail.setHsUnit(tmp.getCusUnit());
	// currBillDetail.setHsSpec(billDetail.getPtNameptSpec());
	// currBillDetail.setEmsNo(tmp.getEmsNo());
	// if (tmp.getVersion() != null) {
	// currBillDetail.setHsVersion(tmp.getVersion());
	// }
	// return currBillDetail;
	// }
	// } else {
	// return billDetail;
	// }
	//
	// }
	/**
	 * 设置控件状态
	 * 
	 */
	private void setState() {

		this.btnEdit.setEnabled(this.dataState == DataState.BROWSE);
		this.btnCancel.setEnabled(this.dataState != DataState.BROWSE);
		this.btnPrevious.setEnabled(tableModel.hasPreviousRow());
		this.btnNext.setEnabled(tableModel.hasNextRow());
		this.btnSave.setEnabled(this.dataState != DataState.BROWSE);
		this.btnSelect.setEnabled(this.dataState != DataState.BROWSE);
		// this.btnSelectHs.setEnabled(this.dataState != DataState.BROWSE);

		//
		// data components
		//
		this.cbbWareSet.setEnabled(this.dataState != DataState.BROWSE);
		this.cbbVersion.setEnabled(this.dataState != DataState.BROWSE);
		tfPtAmount.setEditable(this.dataState != DataState.BROWSE);
		tfPtPrice.setEditable(this.dataState != DataState.BROWSE);
		tfHsAmount.setEditable(this.dataState != DataState.BROWSE);

		tfHsPrice.setEditable(this.dataState != DataState.BROWSE);
		tfMoney.setEditable(this.dataState != DataState.BROWSE);

		tfProductNo.setEditable(this.dataState != DataState.BROWSE);
		tfCustomNo.setEditable(this.dataState != DataState.BROWSE);

		tfCustomNum.setEditable(this.dataState != DataState.BROWSE);
		tfNetWeight.setEditable(this.dataState != DataState.BROWSE);

		// 以下两个是设备用到
		tfLocation.setEditable(this.dataState != DataState.BROWSE);// 设备－存放地点
		tfHoldMan.setEditable(this.dataState != DataState.BROWSE);// 设备－保管人

		// 报关资料
		this.tfNode.setEditable(this.dataState != DataState.BROWSE);
		this.tfTakeBillNo.setEditable(this.dataState != DataState.BROWSE);
		this.tfOderBillNo.setEditable(this.dataState != DataState.BROWSE);

		//
		// 参数控制
		//
		if (CasCommonVars.getBillCorrespondingControl().getIsHandContrl() == true) {
			tfCustomNo.setEnabled(true);
			tfCustomNum.setEnabled(true);
		} else {
			tfCustomNo.setEnabled(false);
			tfCustomNum.setEnabled(false);
		}
	}

	/**
	 * @return Returns the billType.
	 */
	public int getBillType() {
		return billType;
	}

	/**
	 * @param billType
	 *            The billType to set.
	 */
	public void setBillType(int billType) {
		this.billType = billType;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			lbUnitweight = new JLabel();
			lbUnitweight.setText(" 空");
			lbUnitweight.setForeground(new java.awt.Color(255, 0, 255));
			jLabel27 = new JLabel();
			jLabel27.setText("                  单净重:    ");
			jLabel27.setForeground(new java.awt.Color(255, 0, 255));
			lbUnitConvert = new JLabel();
			lbUnitConvert.setText("空");
			lbUnitConvert.setForeground(new java.awt.Color(255, 153, 51));
			jLabel18 = new JLabel();
			jLabel18.setText("               折算报关单位比率:   ");
			jLabel18.setForeground(new java.awt.Color(255, 153, 0));
			jToolBar = new JToolBar();
			// jToolBar.add(getBtnAuto());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDone());
			jToolBar.add(getBtnPrevious());
			jToolBar.add(getBtnNext());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnExit());
			jToolBar.add(jLabel18);
			jToolBar.add(lbUnitConvert);
			jToolBar.add(jLabel27);
			jToolBar.add(lbUnitweight);
		}
		return jToolBar;
	}

	/**
	 * This method initializes cbbWareSet
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWareSet() {
		if (cbbWareSet == null) {
			cbbWareSet = new JComboBox();
			cbbWareSet.setBounds(124, 229, 116, 22);
			// cbbWareSet.addItemListener(new java.awt.event.ItemListener() {
			// public void itemStateChanged(java.awt.event.ItemEvent e) {
			// if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
			// // doWarehouses();wss2010.09.16n屏蔽因为太耗时了
			// }
			// }
			// });
		}
		return cbbWareSet;
	}

	/**
	 * This method initializes tfNode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfNode() {
		if (tfNode == null) {
			tfNode = new JTextField();
			tfNode.setBounds(new Rectangle(358, 204, 116, 22));
		}
		return tfNode;
	}

	public void setBillCategoryBillDetail(int billCategory) {
		this.billCategory = billCategory;
	}

	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jPanel11 = new JPanel();
			jPanel11.setLayout(null);
			jPanel11
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel11.setBounds(new Rectangle(103, 347, 410, 2));
		}
		return jPanel11;
	}

	/**
	 * This method initializes tfEmsNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(new Rectangle(128, 408, 116, 22));
			tfEmsNo.setEditable(false);
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes tfHsVersion
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfHsVersion() {
		if (tfHsVersion == null) {
			tfHsVersion = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfHsVersion.setBounds(new Rectangle(363, 383, 116, 22));
			tfHsVersion.setFormatterFactory(defaultFormatterFactory);
			tfHsVersion.setEditable(false);
		}
		return tfHsVersion;
	}

	/**
	 * This method initializes btnSelectHs
	 * 
	 * @return javax.swing.JButton
	 */
	// private JButton getBtnSelectHs() {
	// if (btnSelectHs == null) {
	// btnSelectHs = new JButton();
	// btnSelectHs.setBounds(new Rectangle(242, 326, 21, 21));
	// btnSelectHs.setText("...");
	// btnSelectHs.addActionListener(new java.awt.event.ActionListener() {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// if (currBillDetail != null) {
	// List list = ErpBillCenterQuery.getInstance()
	// .getFacutalCustoms(false, tfPtPart.getText(),
	// DgBillDetail.this.getComplex(),
	// DgBillDetail.this.tfHsSpec.getText(),
	// DgBillDetail.this.getHsUnit());
	// if (list != null && list.size() > 0) {
	// StatCusNameRelationHsn tmp = (StatCusNameRelationHsn) list
	// .get(0);
	// fillWinBySelectHs(tmp);
	// // fillTenInfo(scrh,currBillDetail);
	// }
	// }
	// }
	// });
	// }
	// return btnSelectHs;
	// }
	public double getUnitConvert() {
		return unitConvert;
	}

	public void setUnitConvert(double unitConvert) {
		this.unitConvert = unitConvert;
	}

	/**
	 * 改变工厂物料时重新填充窗体并设置相关参数
	 * 
	 * @param mt
	 */
	public void fillWinBySelectMt(FactoryAndFactualCustomsRalation tmp) {
		// 工厂物料
		Materiel m = (Materiel) tmp.getMateriel();
		tfPtPart.setText(m.getPtNo());
		tfPtName.setText(m.getFactoryName());
		tfPtSpec.setText(m.getFactorySpec());
		tfPtUnit.setText(m.getCalUnit().getName());
		// 增加版本，版本来自报关常用工厂BOM
		List list = materialManageAction.findMaterialVersion(new Request(
				CommonVars.getCurrUser(), true), tfPtPart.getText());

		this.cbbVersion.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbVersion.setRenderer(CustomBaseRender.getInstance().getRender(
				"version", "notes", 100, 10));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbVersion, "notes", "version");
		if (m.getPtVersion() != null&&!m.getPtVersion().equals(""))
			version.setVersion(Integer.valueOf(m.getPtVersion()));
		else
			version.setVersion(0);
		this.cbbVersion.setSelectedItem(version);

		this.setMtUnit(m.getCalUnit());
		BigDecimal bg = new BigDecimal(tmp.getUnitConvert() == null ? 1.0 : tmp
				.getUnitConvert());
		System.out.println("tmp.getUnitConvert()=" + tmp.getUnitConvert());
		this.setUnitConvert(bg.setScale(6, BigDecimal.ROUND_HALF_UP)
				.doubleValue());
		System.out.println("==========tmp.getUnitConvert()="
				+ tmp.getUnitConvert());
		lbUnitConvert.setText((Double) tmp.getUnitConvert() == null ? "1.0"
				: "" + (Double) tmp.getUnitConvert());
		// invNetWeight = m.getPtNetWeight().toString() == null ? "1.0" : ""
		// + m.getPtNetWeight().toString();
		lbUnitweight.setText((Double) m.getPtNetWeight() == null ? "1.0" : ""
				+ (Double) m.getPtNetWeight());
		// lbUnitweight.setText(invNetWeight.toString());
		fillWinBySelectHs(tmp.getStatCusNameRelationHsn());
		// 报关商品资料
		// autofindFactualCustoms(m.getPtNo());
	}

	/**
	 * 改变工厂物料时重新填充窗体并设置相关参数 (半成品部分）
	 * 
	 * @param mt
	 * @author wss(2010.06.09新加)
	 */
	public void fillWinBySelectMtSemiProduct(Materiel m) {
		// 工厂物料
		tfPtPart.setText(m.getPtNo());
		tfPtName.setText(m.getFactoryName());
		tfPtSpec.setText(m.getFactorySpec());
		tfPtUnit.setText(m.getCalUnit().getName());
		// 增加版本，版本来自报关常用工厂BOM
		List list = materialManageAction.findMaterialVersion(new Request(
				CommonVars.getCurrUser(), true), tfPtPart.getText());

		this.cbbVersion.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbVersion.setRenderer(CustomBaseRender.getInstance().getRender(
				"version", "notes", 100 ,10));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbVersion, "notes", "version");
		if (m.getPtVersion() != null)
			version.setVersion(Integer.valueOf(m.getPtVersion()));
		else
			version.setVersion(0);
		this.cbbVersion.setSelectedItem(version);

		this.setMtUnit(m.getCalUnit());
		BigDecimal bg = new BigDecimal(m.getUnitConvert() == null ? 1.0 : m
				.getUnitConvert());
		System.out.println("tmp.getUnitConvert()=" + m.getUnitConvert());
		this.setUnitConvert(bg.setScale(6, BigDecimal.ROUND_HALF_UP)
				.doubleValue());
		System.out.println("==========tmp.getUnitConvert()="
				+ m.getUnitConvert());
		lbUnitConvert.setText((Double) m.getUnitConvert() == null ? "1.0" : ""
				+ (Double) m.getUnitConvert());
		// invNetWeight = m.getPtNetWeight().toString() == null ? "1.0" : ""
		// + m.getPtNetWeight().toString();
		lbUnitweight.setText((Double) m.getPtNetWeight() == null ? "1.0" : ""
				+ (Double) m.getPtNetWeight());
		// lbUnitweight.setText(invNetWeight.toString());
		tmpComplex = m.getComplex();
		tmpHsUnit = m.getPtUnit();
		tmpUnitConvert = m.getUnitConvert();
		tfHsName.setText(m.getPtName());
		if (m.getComplex() != null)
			tfComplex.setText(m.getComplex().getCode());
		tfComplex.setText(null);
		tfHsSpec.setText(m.getPtSpec());
		tfHsUnit.setText(m.getPtUnit() == null ? "" : m.getPtUnit().getName());
		this.setComplex(m.getComplex());
		this.setHsUnit(m.getPtUnit());

	}

	// 折算报关数量，单价，海关单价，净重，金额
//	/**
//	 * 自动对应
//	 * 
//	 * @param ptNo
//	 */
//	private void autofindFactualCustoms(String ptNo) {
//		FactoryAndFactualCustomsRalation tmp = casAction.findFactualCustoms(
//				new Request(CommonVars.getCurrUser()), ptNo, this.billMaster
//						.getValidDate());
//		if (tmp != null && tmp.getStatCusNameRelationHsn() != null) {
//			// tfComplex.setText(tmp.getStatCusNameRelationHsn().getComplex()
//			// .getCode());
//			tmpComplex = tmp.getStatCusNameRelationHsn().getComplex();
//			tmpHsUnit = tmp.getStatCusNameRelationHsn().getCusUnit();
//			tmpUnitConvert = tmp.getStatCusNameRelationHsn().getUnitConvert();
//			tfHsName.setText(tmp.getStatCusNameRelationHsn().getCusName());
//			if (tmp.getStatCusNameRelationHsn().getComplex() != null)
//				tfComplex.setText(tmp.getStatCusNameRelationHsn().getComplex()
//						.getCode());
//			else
//				tfComplex.setText(null);
//			tfHsSpec.setText(tmp.getStatCusNameRelationHsn().getCusSpec());
//			tfHsUnit.setText(tmp.getStatCusNameRelationHsn().getCusUnit()
//					.getName());
//			if (tmp.getStatCusNameRelationHsn().getVersion() != null)
//				tfHsVersion.setValue(tmp.getStatCusNameRelationHsn()
//						.getVersion());
//			else
//				tfHsVersion.setText("");
//			tfEmsNo.setText(tmp.getStatCusNameRelationHsn().getEmsNo());
//			this.setComplex(tmp.getStatCusNameRelationHsn().getComplex());
//			this.setHsUnit(tmp.getStatCusNameRelationHsn().getCusUnit());
//			tmpHsUnit = tmp.getStatCusNameRelationHsn().getCusUnit();
//			tmpComplex = tmp.getStatCusNameRelationHsn().getComplex();
//			tmpUnitConvert = tmp.getStatCusNameRelationHsn().getUnitConvert();
//		} else {
//			// tfComplex.setText(null);
//			tfHsName.setText(null);
//			tfComplex.setText(null);
//			tfHsSpec.setText(null);
//			tfHsUnit.setText(null);
//			tfHsVersion.setText(null);
//			tfEmsNo.setText(null);
//			this.setComplex(null);
//			this.setHsUnit(null);
//		}
//	}

	/**
	 * 改变报关商品时重新填充窗体并设置相关参数
	 * 
	 * @param hsn
	 */
	public void fillWinBySelectHs(StatCusNameRelationHsn tmp) {
		if (tmp != null) {
			// tfComplex.setText(tmp.getComplex().getCode());
			tmpComplex = tmp.getComplex();
			tmpHsUnit = tmp.getCusUnit();
			tmpUnitConvert = tmp.getUnitConvert();
			tfHsName.setText(tmp.getCusName());
			if (tmp.getComplex() != null)
				tfComplex.setText(tmp.getComplex().getCode());
			else
				tfComplex.setText(null);
			tfHsSpec.setText(tmp.getCusSpec());
			tfHsUnit.setText(tmp.getCusUnit().getName());
			if (tmp.getVersion() != null)
				tfHsVersion.setValue(tmp.getVersion());
			tfEmsNo.setText(tmp.getEmsNo());
			this.setComplex(tmp.getComplex());
			this.setHsUnit(tmp.getCusUnit());
		}
	}

	/**
	 * 保存按钮的保存动作
	 * 
	 * @return
	 */
	private void saveBillDetail() {
		BillDetail currBillDetails = (BillDetail) tableModel.getCurrentRow();
		if (currBillDetail != null) {
			// 填充资料 -- 工厂资料部分
			currBillDetail.setPtPart(tfPtPart.getText());
			currBillDetail.setPtName(tfPtName.getText());
			currBillDetail.setPtSpec(tfPtSpec.getText());
			currBillDetail.setPtUnit(this.getMtUnit());
			currBillDetail
					.setComplex(currBillDetails.getComplex() == null ? null
							: currBillDetails.getComplex());
			currBillDetail.setTakeBillNo(tfTakeBillNo.getText());
			currBillDetail.setOderBillNo(tfOderBillNo.getText());
			fillBillDetailFromWindow(currBillDetail);

			// 填充资料-- 报关资料部分
			currBillDetail.setHsName(tfHsName.getText());
			currBillDetail.setHsSpec(tfHsSpec.getText());
			currBillDetail.setComplex(tmpComplex);
			currBillDetail.setHsUnit(tmpHsUnit);
			currBillDetail.setUnitConvert(tmpUnitConvert);
			// 设备用到
			// if (tfHsUnit.getText() != null && !tfHsUnit.getText().equals(""))
			// {// 单位
			// Unit unit = casAction.findUnitByName(new Request(CommonVars
			// .getCurrUser()), tfHsUnit.getText());
			// if (unit == null) {
			// JOptionPane.showMessageDialog(this, "没有这样的报关单位，请检查！", "提示",
			// JOptionPane.ERROR_MESSAGE);
			// return;
			// }
			// hsUnit = unit;
			// }
			// currBillDetail.setHsUnit(this.getHsUnit());
			currBillDetail.setEmsNo(tfEmsNo.getText());
			currBillDetail.setTakeBillNo(this.tfTakeBillNo.getText());
			currBillDetail.setOderBillNo(this.tfOderBillNo.getText());
			if (tfHsVersion.getText() != null
					&& !tfHsVersion.getText().equals("")) {
				currBillDetail.setHsVersion(strToInteger(tfHsVersion.getValue()
						.toString()));
			} else {
				currBillDetail.setHsVersion(null);
			}
		}
		// return currBillDetail;
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public CalUnit getMtUnit() {
		return mtUnit;
	}

	public void setMtUnit(CalUnit mtUnit) {
		this.mtUnit = mtUnit;
	}

	public Unit getHsUnit() {
		return hsUnit;
	}

	public void setHsUnit(Unit hsUnit) {
		this.hsUnit = hsUnit;
	}

	/**
	 * This method initializes tfHoldMan
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHoldMan() {
		if (tfHoldMan == null) {
			tfHoldMan = new JTextField();
			tfHoldMan.setBounds(new Rectangle(124, 284, 116, 22));
		}
		return tfHoldMan;
	}

	/**
	 * This method initializes tfLocation
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLocation() {
		if (tfLocation == null) {
			tfLocation = new JTextField();
			tfLocation.setBounds(new Rectangle(358, 256, 116, 22));
		}
		return tfLocation;
	}

	/**
	 * 用来控制设备单据0
	 */
	private void fixControlUIComponents() {
		if ((MaterielType.MACHINE).equals(getMaterielType(billMaster
				.getBillType().getBillType(), isMateriel))) {
			jlbHoldMan.setVisible(true);
			tfHoldMan.setVisible(true);
			jlbLocation.setVisible(true);
			tfLocation.setVisible(true);
			btnSpecialCustomsContractNoSelect.setVisible(true);

			tfCustomNo.setEnabled(true);
			tfCustomNum.setEnabled(true);
			tfHsName.setEditable(true);
			tfComplex.setEditable(true);
			tfHsSpec.setEditable(true);
			tfHsUnit.setEditable(true);

			jLabel26.setText("协议号");
			tfEmsNo.setEditable(true);
		} else {
			jlbHoldMan.setVisible(false);
			tfHoldMan.setVisible(false);
			jlbLocation.setVisible(false);
			tfLocation.setVisible(false);
			btnSpecialCustomsContractNoSelect.setVisible(false);
		}
	}

	/**
	 * This method initializes tfTakeBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTakeBillNo() {
		if (tfTakeBillNo == null) {
			tfTakeBillNo = new JTextField();
			tfTakeBillNo.setBounds(new Rectangle(124, 256, 116, 22)); // Generated
		}
		return tfTakeBillNo;
	}

	/**
	 * This method initializes tfOderBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOderBillNo() {
		if (tfOderBillNo == null) {
			tfOderBillNo = new JTextField();
			tfOderBillNo.setBounds(new Rectangle(358, 230, 116, 22)); // Generated
		}
		return tfOderBillNo;
	}

	/**
	 * This method initializes btnSpecialCustomsContractNoSelect
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSpecialCustomsContractNoSelect() {
		if (btnSpecialCustomsContractNoSelect == null) {
			btnSpecialCustomsContractNoSelect = new JButton();
			btnSpecialCustomsContractNoSelect.setBounds(new Rectangle(241, 176,
					22, 22));
			btnSpecialCustomsContractNoSelect.setText("...");
			btnSpecialCustomsContractNoSelect
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (currBillDetail != null) {
								if (tfEmsNo.getText() == null
										|| tfEmsNo.getText().equals("")) {
									JOptionPane.showMessageDialog(
											DgBillDetail.this, "请先输入合同协议号",
											"提示", JOptionPane.ERROR_MESSAGE);
									return;
								}
								BaseCustomsDeclaration customsDeclaration = ErpBillCenterQuery
										.getInstance()
										.getSpecialCustomsContractNo(
												tfEmsNo.getText().trim());
								if (customsDeclaration != null) {
									tfCustomNo.setText(customsDeclaration
											.getCustomsDeclarationCode());
								}
							}
						}
					});
		}
		return btnSpecialCustomsContractNoSelect;
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
	 * 显示
	 */
	private void showData() {
		currBillDetail = (BillDetail) tableModel.getCurrentRow();
		fillInvNetWeight(getMaterielType(billMaster.getBillType().getBillType(), isMateriel), currBillDetail);
		fillData(currBillDetail);
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
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("\u53d6\u6d88");
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
					// doWarehouses();//wss2010.09.16屏蔽 计算仓库数量、结余数量太费时间。
					setState();
				}
			});
		}
		return btnEdit;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This method initializes btnProductNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnProductNo() {
		if (btnProductNo == null) {
			btnProductNo = new JButton();
			btnProductNo.setBounds(new Rectangle(475, 149, 28, 21));
			btnProductNo.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/finger1.gif")));
			btnProductNo.setText("");
			btnProductNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String propertyName = "productNo";
					Object propertyValue = tfProductNo.getText().trim();
					if ("".equals(propertyValue)) {
						JOptionPane.showMessageDialog(DgBillDetail.this,
								"制单号不可为空!!", "提示", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(DgBillDetail.this,
							"你确定要同步本单据的全部制单号?", "确认", 0) == 0) {
						List<BillDetail> list = casAction
								.saveAllBillDetailByMaster(new Request(
										CommonVars.getCurrUser()), billMaster,
										propertyName, propertyValue);
						//
						// new refresh data
						//
						tableModel.setList(list);
					}
				}
			});
		}
		return btnProductNo;
	}

	/**
	 * This method initializes btnWareSetAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnWareSetAll() {
		if (btnWareSetAll == null) {
			btnWareSetAll = new JButton();
			btnWareSetAll.setBounds(new Rectangle(239, 229, 28, 22));
			btnWareSetAll.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/finger1.gif")));
			btnWareSetAll.setText("");
			btnWareSetAll
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							//
							// 仓库 WareSet "wareSet
							//
							String propertyName = "wareSet";
							Object propertyValue = cbbWareSet.getSelectedItem();
							if (null == propertyValue) {
								JOptionPane.showMessageDialog(
										DgBillDetail.this, "仓库不可为空!!", "提示",
										JOptionPane.WARNING_MESSAGE);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									DgBillDetail.this, "你确定要同步本单据的全部仓库?", "确认",
									0) == 0) {
								List<BillDetail> list = casAction
										.saveAllBillDetailByMaster(new Request(
												CommonVars.getCurrUser()),
												billMaster, propertyName,
												propertyValue);
								//
								// new refresh data
								//
								tableModel.setList(list);
							}
						}
					});
		}
		return btnWareSetAll;
	}

	public boolean isMateriel() {
		return isMateriel;
	}

	public void setMateriel(boolean isMateriel) {
		this.isMateriel = isMateriel;
	}

	/**
	 * This method initializes tfWarehouses
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
//	private JCustomFormattedTextField getTfWarehouses() {
//		if (tfWarehouses == null) {
//			DecimalFormat decimalFormat2 = new DecimalFormat();
//			decimalFormat2.setMaximumFractionDigits(6);
//			NumberFormatter numberFormatter2 = new NumberFormatter();
//			numberFormatter2.setFormat(decimalFormat2);
//			DefaultFormatterFactory defaultFormatterFactory2 = new DefaultFormatterFactory();
//			defaultFormatterFactory2.setNullFormatter(numberFormatter2);
//			defaultFormatterFactory2.setEditFormatter(numberFormatter2);
//			defaultFormatterFactory2.setDisplayFormatter(numberFormatter2);
//			defaultFormatterFactory2.setDefaultFormatter(numberFormatter2);
//			tfWarehouses = new JCustomFormattedTextField();
//			tfWarehouses.setBounds(new Rectangle(358, 282, 116, 22));
//			tfWarehouses.setFormatterFactory(defaultFormatterFactory2);
//			tfWarehouses.setEditable(false);
//		}
//		return tfWarehouses;
//	}

	/**
	 * This method initializes tfBalanceAmount
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
//	private JCustomFormattedTextField getTfBalanceAmount() {
//		if (tfBalanceAmount == null) {
//			DecimalFormat decimalFormat21 = new DecimalFormat();
//			decimalFormat21.setMaximumFractionDigits(6);
//			NumberFormatter numberFormatter21 = new NumberFormatter();
//			numberFormatter21.setFormat(decimalFormat21);
//			DefaultFormatterFactory defaultFormatterFactory21 = new DefaultFormatterFactory();
//			defaultFormatterFactory21.setNullFormatter(numberFormatter21);
//			defaultFormatterFactory21.setEditFormatter(numberFormatter21);
//			defaultFormatterFactory21.setDisplayFormatter(numberFormatter21);
//			defaultFormatterFactory21.setDefaultFormatter(numberFormatter21);
//			tfBalanceAmount = new JCustomFormattedTextField();
//			tfBalanceAmount.setBounds(new Rectangle(124, 312, 116, 22));
//			tfBalanceAmount.setFormatterFactory(defaultFormatterFactory21);
//			tfBalanceAmount.setEditable(false);
//		}
//		return tfBalanceAmount;
//	}

	/**
	 * This method initializes btnDeliveryNumber
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeliveryNumber() {
		if (btnDeliveryNumber == null) {
			btnDeliveryNumber = new JButton();
			btnDeliveryNumber.setBounds(new Rectangle(240, 256, 28, 22));
			btnDeliveryNumber.setText("");
			btnDeliveryNumber.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/finger1.gif")));
			btnDeliveryNumber
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							String propertyName = "takeBillNo";
							Object propertyValue = tfTakeBillNo.getText()
									.trim();
							if ("".equals(propertyValue)) {
								JOptionPane.showMessageDialog(
										DgBillDetail.this, "送货单号不可为空!!", "提示",
										JOptionPane.WARNING_MESSAGE);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									DgBillDetail.this, "你确定要同步本单据的全部送货单号?",
									"确认", 0) == 0) {
								List<BillDetail> list = casAction
										.saveAllBillDetailByMaster(new Request(
												CommonVars.getCurrUser()),
												billMaster, propertyName,
												propertyValue);
								tableModel.setList(list);
							}
						}
					});
		}
		return btnDeliveryNumber;
	}

	/**
	 * This method initializes tfComplex
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplex() {
		if (tfComplex == null) {
			tfComplex = new JTextField();
			tfComplex.setBounds(new Rectangle(363, 408, 116, 22));
			tfComplex.setEditable(false);
		}
		return tfComplex;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
