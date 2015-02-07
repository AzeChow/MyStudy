package com.bestway.client.custom.common.supplement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JPopupMenu.Separator;
import javax.swing.border.TitledBorder;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.PreDock;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.message.entity.DecSupplementList;
import com.bestway.bcus.system.action.TcsParametersAction;
import com.bestway.bcus.system.entity.TcsParameters;
import com.bestway.client.custom.common.TcsReplenishReport;
import com.bestway.common.Request;
import com.bestway.common.constant.RepDeclarationType;
import com.bestway.common.constant.SupplmentType;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
/**
 * TCS补充申报信息窗口
 * @author Administrator
 *
 */
public class DgReplenishDeclareCommonInfo extends JDialogBase {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel pnCommonInfo = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JComboBox supType = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel5 = null;
	private JTextField brandCN = null;
	private JTextField brandEN = null;
	private JTextField Buyer = null;
	private JTextField buyerContact = null;
	private JTextField buyerAddr = null;
	private JTextField buyerTel = null;
	private JTextField seller = null;
	private JTextField sellerContact = null;
	private JLabel jLabel11 = null;
	private JTextField sellerAddr = null;
	private JTextField sellerTel = null;
	private JTextField factory = null;
	private JLabel jLabel14 = null;
	private JTextField factoryContact = null;
	private JTextField factoryAddr = null;
	private JLabel jLabel16 = null;
	private JTextField factoryTel = null;
	private JLabel jLabel17 = null;
	private JTextField contrNo = null;
	private JLabel jLabel18 = null;
	private JCalendarComboBox contrDate = null;
	private JLabel jLabel19 = null;
	private JTextField invoiceNo = null;
	private JLabel jLabel20 = null;
	private JCalendarComboBox invoiceDate = null;
	private JTextField otherNote = null;
	private JComboBox isSecret = null;
	private JLabel jLabel23 = null;
	private JTextField declAddr = null;
	private JLabel jLabel24 = null;
	private JTextField declPost = null;
	private JTextField declTel = null;
	private JTabbedPane tpMain = null;
	private JToolBar jToolBar = null;
	private JButton btnSave = null;
	private JButton btnClose = null;
	private JButton btnPrint = null;
	private JButton btnEdit = null;
	private JLabel jLabel28 = null;
	private JLabel jLabel29 = null;
	private JLabel jLabel30 = null;
	private JLabel jLabel31 = null;
	private JLabel jLabel32 = null;
	private JLabel jLabel33 = null;
	private JLabel jLabel341 = null;
	private JLabel jLabel342 = null;
	private JLabel jLabel343 = null;
	private JLabel jLabel344 = null;
	private JLabel jLabel345 = null;
	private JLabel jLabel346 = null;
	private JPanel pnPrice1 = null;
	private JLabel jLabel41 = null;
	private JLabel jLabel42 = null;
	private JComboBox cbI_PriceEffect = null;
	private JLabel jLabel43 = null;
	private JComboBox cbI_PriceClose = null;
	private JLabel jLabel44 = null;
	private JComboBox cbI_OtherLimited = null;
	private JLabel jLabel45 = null;
	private JComboBox cbI_OtherEffect = null;
	private JLabel jLabel46 = null;
	private JTextField tfI_Note1 = null;
	private JLabel jLabel47 = null;
	private JLabel jLabel48 = null;
	private JTextField tfInvoicePrice = null;
	private JLabel jLabel49 = null;
	private JTextField tfInvoiceAmount = null;
	private JLabel jLabel410 = null;
	private JTextField tfInvoiceNote = null;
	private JLabel jLabel411 = null;
	private JTextField tfGoodsPrice = null;
	private JLabel jLabel412 = null;
	private JTextField tfGoodsAmount = null;
	private JLabel jLabel413 = null;
	private JTextField tfGoodsNote = null;
	private JButton btnGoodsNote = null;
	private JLabel jLabel414 = null;
	private JTextField tfI_CommissionPrice = null;
	private JLabel jLabel415 = null;
	private JTextField tfI_CommissionAmount = null;
	private JLabel jLabel416 = null;
	private JTextField tfI_CommissionNote = null;
	private JLabel jLabel417 = null;
	private JTextField tfI_ContainerPrice = null;
	private JLabel jLabel418 = null;
	private JTextField tfI_ContainerAmount = null;
	private JLabel jLabel419 = null;
	private JTextField tfI_ContainerNote = null;
	private JButton btnI_ContainerNote = null;
	private JLabel jLabel420 = null;
	private JTextField tfI_PackPrice = null;
	private JLabel jLabel421 = null;
	private JTextField tfI_PackAmount = null;
	private JLabel jLabel422 = null;
	private JTextField tfI_PackNote = null;
	private JButton btnI_PackNote = null;
	private JLabel jLabel423 = null;
	private JTextField tfI_PartPrice = null;
	private JLabel jLabel424 = null;
	private JTextField tfI_PartAmount = null;
	private JLabel jLabel425 = null;
	private JTextField tfI_PartNote = null;
	private JButton btnI_PartNote = null;
	private JPanel pnPrice2 = null;
	private JLabel jLabel427 = null;
	private JLabel jLabel428 = null;
	private JTextField tfI_ToolAmount = null;
	private JTextField tfI_ToolNote = null;
	private JLabel jLabel429 = null;
	private JTextField tfI_LossPrice = null;
	private JLabel jLabel430 = null;
	private JTextField tfI_LossAmount = null;
	private JLabel jLabel431 = null;
	private JTextField tfI_LossNote = null;
	private JLabel jLabel432 = null;
	private JTextField tfI_DesignPrice = null;
	private JLabel jLabel433 = null;
	private JTextField tfI_DesignAmount = null;
	private JLabel jLabel434 = null;
	private JTextField tfI_DesignNote = null;
	private JLabel jLabel435 = null;
	private JTextField tfI_UsefeePrice = null;
	private JLabel jLabel436 = null;
	private JTextField tfI_UsefeeAmount = null;
	private JLabel jLabel437 = null;
	private JTextField tfI_UsefeeNote = null;
	private JButton btnI_UsefeeNote = null;
	private JButton btnI_ToolNote = null;
	private JButton btnI_LossNote = null;
	private JButton btnI_DesignNote = null;
	private JLabel jLabel438 = null;
	private JTextField tfI_ProfitPrice = null;
	private JLabel jLabel439 = null;
	private JTextField tfI_ProfitAmount = null;
	private JLabel jLabel440 = null;
	private JTextField tfI_ProfitNote = null;
	private JLabel jLabel441 = null;
	private JTextField tfI_FeePrice = null;
	private JLabel jLabel442 = null;
	private JTextField tfI_FeeAmount = null;
	private JLabel jLabel443 = null;
	private JTextField tfI_FeeNote = null;
	private JButton btnI_FeeNote = null;
	private JLabel jLabel444 = null;
	private JTextField tfI_OtherPrice = null;
	private JLabel jLabel445 = null;
	private JTextField tfI_OtherAmount = null;
	private JLabel jLabel446 = null;
	private JTextField tfI_OtherNote = null;
	private JButton btnI_OtherNote = null;
	private JLabel jLabel447 = null;
	private JTextField tfI_InsurPrice = null;
	private JLabel jLabel448 = null;
	private JTextField tfI_InsurAmount = null;
	private JLabel jLabel449 = null;
	private JTextField tfI_InsurNote = null;
	private JButton btnI_InsurNote = null;
	private JLabel jLabel450 = null;
	private JPanel pnMerger = null;
	private JLabel jLabel451 = null;
	private JTextField tfGNameOther = null;
	private JLabel jLabel452 = null;
	private JButton bntGNameOther = null;
	private JTextField tfCodeTsOther = null;
	private JLabel jLabel453 = null;
	private JComboBox cbIsClassDecision = null;
	private JLabel jLabel454 = null;
	private JTextField tfDecisionNO = null;
	private JLabel jLabel455 = null;
	private JTextField tfCodeTsDecision = null;
	private JLabel jLabel456 = null;
	private JLabel jLabel457 = null;
	private JComboBox cbIsSampleTest = null;
	private JLabel jLabel458 = null;
	private JPanel pnOrigin = null;
	private JLabel jLabel459 = null;
	private JLabel jLabel460 = null;
	private JLabel jLabel461 = null;
	private JComboBox cbIsDirectTraf = null;
	private JLabel jLabel462 = null;
	private JLabel jLabel463 = null;
	private JLabel jLabel464 = null;
	private JTextField tfBillNo = null;
	private JLabel jLabel465 = null;
	private JLabel jLabel466 = null;
	private JLabel jLabel467 = null;
	private JLabel jLabel468 = null;
	private JTextField tfCertNO = null;
	private JLabel jLabel469 = null;
	private JPanel pnIndirectPayment = null;
	private JPanel pnCommission = null;
	private JPanel pnContainer = null;
	private JPanel pnContainer2 = null;
	private JPanel pnService = null;
	private JPanel pnInvoice = null;
	private JPanel pnPart = null;
	private JLabel jLabel426 = null;
	private JTextField tfI_ToolPrice = null;
	private JPanel pnCmaterials = null;
	private JPanel pnEdesign = null;
	private JPanel pnResale = null;
	private JPanel pnFreight = null;
	private JPanel pnRelateFreight = null;
	private JPanel pnConcession = null;
	private JPanel pnInsurance = null;
	private JButton btnI_ProfitNote = null;
	private JTextField tfGoptions = null;
	private JButton btnGoptions = null;
	private JComboBox cbCertStandard = null;
	private JPanel pnBuyer = null;
	private JPanel pnSeller = null;
	private JPanel pnFactory = null;
	private JPanel pnRepSign = null;
	private JLabel jLabel470 = null;
	private JLabel jLabel471 = null;
	private JComboBox cbOperType = null;
	private JTextField tfIcCardId = null;
	private JLabel jLabel472 = null;
	private JTextField tfClientSeqNo = null;
	private JLabel jLabel473 = null;
	private JCalendarComboBox tfSignDate = null;
	private JLabel jLabel474 = null;
	private JButton btnBuySellRel = null;
	private JTextField tfI_BabRel = null;
	private JTextField tfSignInfo = null;
	private BaseEncAction baseEncAction;
	private int dataState = DataState.ADD;
	private String supplmentType = null;// 申报类型s
	public Integer result = Integer.valueOf(0); // @jve:decl-index=0:
	private BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo;
	private BaseCustomsDeclaration baseCustomsDeclaration;
	
	private DecSupplementList decSupplement; // @jve:decl-index=0:
	
	private JTextField tfGno = null;
	private JTextField tfcommSerialNo = null;
	private String errorInfo = ""; // @jve:decl-index=0:
	private JButton btI_CommissionNote = null;
	private JComboBox cbbDecisionCus = null;
	private JComboBox cbbTrafMode = null;
	private JComboBox cbbOriginCountry = null;
	private JComboBox cbbTransitCountry = null;
	private JComboBox cbbCertCountry = null;
	private JComboBox cbbDestPort = null;
	private JComboBox cbbDeclPort = null;
	private JComboBox cbbAgentType = null;
	private JComboBox cbbCurr = null;
	
	private JPopupMenu pmPrint = null;
	private JMenuItem miNotOverprintPrintPriceReplenish = null;
	private JMenuItem miOverprintPrintPriceReplenish = null;
	private JMenuItem miNotOverprintPrintMergerReplenish = null;
	private JMenuItem miOverprintPrintMergerReplenish = null;
	private JMenuItem miNotOverprintOriginReplenish = null;
	private JMenuItem miOverprintPrintOriginReplenish= null;
	private JComboBox cbbE_IsDutyDel = null;
	private JLabel jLabel4 = null;
	private int customsDeclarationsupplmentType;
	private JLabel jLabel6 = null;
	private JComboBox cbbI_IsUsefee = null;
	private JLabel jLabel7 = null;
	private JComboBox cbbI_IsProfit = null;
	private JComboBox cbbOriginMark = null;
	private TcsParametersAction tcsParametersAction = null;
	TcsParameters para =null;
	/**
	 * This is the default constructor
	 */
	public DgReplenishDeclareCommonInfo(String supplmentType,
			BaseCustomsDeclaration baseCustomsDeclaration,
			BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo,Integer customsDeclarationsupplmentType) {
		super();
		this.supplmentType = supplmentType;//价格，归类，原产地补充类型
		this.customsDeclarationsupplmentType = customsDeclarationsupplmentType;//主被动申报类型
		this.baseCustomsDeclarationCommInfo = baseCustomsDeclarationCommInfo;
		this.baseCustomsDeclaration = baseCustomsDeclaration;
		baseEncAction = (BaseEncAction) CommonVars.getApplicationContext()
				.getBean("encAction");
		tcsParametersAction = (TcsParametersAction) CommonVars
		.getApplicationContext().getBean("tcsParametersAction");
		para = tcsParametersAction.getTcsParameters(new Request(
				CommonVars.getCurrUser()));
		initialize();
		initUIComponents();
	}
	/*
	 * 初始化窗口上控件的初始值
	 */
	public void initUIComponents() {
		if (baseCustomsDeclaration != null) {
			tfcommSerialNo.setText(baseCustomsDeclaration.getSerialNumber()
					.toString());// 取得流水号
			contrNo.setText(baseCustomsDeclaration.getContract());// 合同协议号
			invoiceNo.setText(baseCustomsDeclaration.getInvoiceCode());// 发票编号
		}
		if (baseCustomsDeclarationCommInfo != null) {
			tfGno.setText(baseCustomsDeclarationCommInfo.getCommSerialNo()// 补充申报单商品序号
					.toString());
			if(baseCustomsDeclarationCommInfo
					.getComplex()!=null){
				tfCodeTsDecision.setText(baseCustomsDeclarationCommInfo
						.getComplex().getCode());	
				tfCodeTsOther.setText(baseCustomsDeclarationCommInfo.getComplex()
						.getCode());
			}
			if (SupplmentType.PASSIVITY_SYPPLMENT == customsDeclarationsupplmentType) {
				if (baseCustomsDeclarationCommInfo.getBaseCustomsDeclaration() != null) {
					tfClientSeqNo.setText(baseCustomsDeclarationCommInfo
							.getBaseCustomsDeclaration().getUnificationCode());
				}
			}
		}
		if (para != null) {
			if (para.getIcCardNo() != null) {
				tfIcCardId.setText(para.getIcCardNo());
			}
		}
		if (supplmentType != null) {// 补充申报类型
			supType.setSelectedItem(new ItemProperty(supplmentType.toString(),
					RepDeclarationType.getSupType(supplmentType.toString())));
		} else {
			supType.setSelectedItem(new ItemProperty(
					RepDeclarationType.SupTypePrice, RepDeclarationType
							.getSupType(RepDeclarationType.SupTypePrice)));
		}
	}
	public DgReplenishDeclareCommonInfo(String supplmentType,
			BaseCustomsDeclaration baseCustomsDeclaration,
			BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo,
			int dataState) {
		super();
		this.supplmentType = supplmentType;
		this.baseCustomsDeclarationCommInfo = baseCustomsDeclarationCommInfo;
		this.baseCustomsDeclaration = baseCustomsDeclaration;
		this.dataState = dataState;
		baseEncAction = (BaseEncAction) CommonVars.getApplicationContext()
				.getBean("encAction");
		initialize();
		initUIComponents();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(800, 577);
		this.setContentPane(getJContentPane());
		btnState(dataState);
		this.setResizable(false);
		this.setTitle("补充申报信息");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				result = 0;
			}
		});
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
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getTpMain(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes pnCommonInfo
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnCommonInfo() {
		if (pnCommonInfo == null) {
			jLabel4 = new JLabel();
			jLabel4.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel4.setSize(new Dimension(108, 36));
			jLabel4.setLocation(new Point(491, 80));
			jLabel4.setText("<html><body>以上申报内容是否<br>需要海关予以保密：</body></html>");
			jLabel346 = new JLabel();
			jLabel346.setBounds(new Rectangle(490, 265, 101, 18));
			jLabel346.setText("申报人电话：");
			jLabel345 = new JLabel();
			jLabel345.setBounds(new Rectangle(490, 140, 101, 18));
			jLabel345.setForeground(Color.blue);
			jLabel345.setText("申报单位类型：");
			jLabel344 = new JLabel();
			jLabel344.setBounds(new Rectangle(251, 361, 86, 18));
			jLabel344.setText("其他情况说明：");
			jLabel343 = new JLabel();
			jLabel343.setText("地址：");
			jLabel343.setBounds(new Rectangle(5, 87, 68, 18));
			jLabel342 = new JLabel();
			jLabel342.setText("名称：");
			jLabel342.setBounds(new Rectangle(4, 24, 68, 18));
			jLabel341 = new JLabel();
			jLabel341.setText("电话：");
			jLabel341.setBounds(new Rectangle(5, 114, 67, 18));
			jLabel33 = new JLabel();
			jLabel33.setText("联系人：");
			jLabel33.setBounds(new Rectangle(5, 48, 67, 18));
			jLabel32 = new JLabel();
			jLabel32.setText("名称：");
			jLabel32.setBounds(new Rectangle(5, 16, 67, 18));
			jLabel31 = new JLabel();
			jLabel31.setText("电话");
			jLabel31.setBounds(new Rectangle(6, 112, 67, 18));
			jLabel30 = new JLabel();
			jLabel30.setText("地址：");
			jLabel30.setBounds(new Rectangle(6, 81, 67, 18));
			jLabel29 = new JLabel();
			jLabel29.setText("联系人：");
			jLabel29.setBounds(new Rectangle(6, 44, 67, 18));
			jLabel28 = new JLabel();
			jLabel28.setBounds(new Rectangle(11, 105, 67, 18));
			jLabel28.setText("英文名称：");
			jLabel24 = new JLabel();
			jLabel24.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel24.setSize(new Dimension(101, 18));
			jLabel24.setLocation(new Point(490, 231));
			jLabel24.setText("申报人邮编：");
			jLabel23 = new JLabel();
			jLabel23.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel23.setSize(new Dimension(101, 18));
			jLabel23.setLocation(new Point(490, 200));
			jLabel23.setText("申报人单位地址：");
			jLabel20 = new JLabel();
			jLabel20.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel20.setSize(new Dimension(83, 18));
			jLabel20.setLocation(new Point(251, 333));
			jLabel20.setText("发票日期：");
			jLabel19 = new JLabel();
			jLabel19.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel19.setSize(new Dimension(83, 18));
			jLabel19.setLocation(new Point(251, 293));
			jLabel19.setText("发票编号：");
			jLabel18 = new JLabel();
			jLabel18.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel18.setSize(new Dimension(83, 18));
			jLabel18.setLocation(new Point(251, 263));
			jLabel18.setText("签约日期：");
			jLabel17 = new JLabel();
			jLabel17.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel17.setSize(new Dimension(83, 18));
			jLabel17.setLocation(new Point(251, 231));
			jLabel17.setText("合同协议号：");
			jLabel16 = new JLabel();
			jLabel16.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel16.setBounds(new Rectangle(5, 119, 68, 18));
			jLabel16.setText("电话：");
			jLabel14 = new JLabel();
			jLabel14.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel14.setBounds(new Rectangle(4, 58, 68, 18));
			jLabel14.setText("联系人：");
			jLabel11 = new JLabel();
			jLabel11.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel11.setBounds(new Rectangle(5, 79, 67, 18));
			jLabel11.setText("地址：");
			jLabel5 = new JLabel();
			jLabel5.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel5.setBounds(new Rectangle(6, 16, 67, 18));
			jLabel5.setText("名称：");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(12, 71, 67, 18));
			jLabel3.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel3.setText("中文名称：");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(382, 12, 91, 18));
			jLabel2.setForeground(Color.blue);
			jLabel2.setText("申报补充类型：");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(165, 12, 124, 18));
			jLabel1.setForeground(Color.blue);
			jLabel1.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel1.setText("补充申报单商品序号：");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(14, 14, 63, 18));
			jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel.setForeground(java.awt.Color.blue);
			jLabel.setText("流水号：");
			pnCommonInfo = new JPanel();
			pnCommonInfo.setLayout(null);
			pnCommonInfo.setBounds(new Rectangle(1, 1, 521, 618));
			pnCommonInfo.setFont(new Font("Dialog", Font.PLAIN, 12));
			pnCommonInfo.add(jLabel, null);
			pnCommonInfo.add(jLabel1, null);
			pnCommonInfo.add(getTfcommSerialNo(), null);
			pnCommonInfo.add(getTfGno(), null);
			pnCommonInfo.add(jLabel2, null);
			pnCommonInfo.add(getSupType(), null);
			pnCommonInfo.add(jLabel3, null);
			pnCommonInfo.add(getBrandCN(), null);
			pnCommonInfo.add(getBrandEN(), null);
			pnCommonInfo.add(getPnBuyer(), null);
			pnCommonInfo.add(getPnSeller(), null);
			pnCommonInfo.add(getPnFactory(), null);
			pnCommonInfo.add(jLabel17, null);
			pnCommonInfo.add(getContrNo(), null);
			pnCommonInfo.add(jLabel18, null);
			pnCommonInfo.add(getContrDate(), null);
			pnCommonInfo.add(jLabel19, null);
			pnCommonInfo.add(getInvoiceNo(), null);
			pnCommonInfo.add(jLabel20, null);
			pnCommonInfo.add(getInvoiceDate(), null);
			pnCommonInfo.add(getOtherNote(), null);
			pnCommonInfo.add(getIsSecret(), null);
			pnCommonInfo.add(getCbbAgentType(), null);
			pnCommonInfo.add(jLabel23, null);
			pnCommonInfo.add(getDeclAddr(), null);
			pnCommonInfo.add(jLabel24, null);
			pnCommonInfo.add(getDeclPost(), null);
			pnCommonInfo.add(getDeclTel(), null);
			pnCommonInfo.add(jLabel28, null);
			pnCommonInfo.add(jLabel344, null);
			pnCommonInfo.add(jLabel345, null);
			pnCommonInfo.add(jLabel346, null);
			pnCommonInfo.add(jLabel4, null);
		}
		return pnCommonInfo;
	}

	/**
	 * This method initializes supType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getSupType() {
		if (supType == null) {
			supType = new JComboBox();
			supType.setEditable(false);
			supType.setBounds(new Rectangle(477, 7, 130, 22));
			supType.addItem(new ItemProperty(RepDeclarationType.SupTypePrice,
					RepDeclarationType
							.getSupType(RepDeclarationType.SupTypePrice)));
			supType.addItem(new ItemProperty(RepDeclarationType.SupTypeMerger,
					RepDeclarationType
							.getSupType(RepDeclarationType.SupTypeMerger)));
			supType.addItem(new ItemProperty(RepDeclarationType.SupTypeOrigin,
					RepDeclarationType
							.getSupType(RepDeclarationType.SupTypeOrigin)));
			supType.setEnabled(false);
		}
		return supType;
	}

	/**
	 * This method initializes brandCN
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getBrandCN() {
		if (brandCN == null) {
			brandCN = new JTextField();
			brandCN.setBounds(new Rectangle(101, 69, 134, 22));
		}
		return brandCN;
	}

	/**
	 * This method initializes brandEN
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getBrandEN() {
		if (brandEN == null) {
			brandEN = new JTextField();
			brandEN.setSize(new Dimension(134, 22));
			brandEN.setLocation(new Point(99, 103));
		}
		return brandEN;
	}

	/**
	 * This method initializes Buyer
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getBuyer() {
		if (Buyer == null) {
			Buyer = new JTextField();
			Buyer.setBounds(new Rectangle(88, 13, 134, 22));
		}
		return Buyer;
	}

	/**
	 * This method initializes buyerContact
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getBuyerContact() {
		if (buyerContact == null) {
			buyerContact = new JTextField();
			buyerContact.setBounds(new Rectangle(88, 43, 134, 22));
		}
		return buyerContact;
	}

	/**
	 * This method initializes buyerAddr
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getBuyerAddr() {
		if (buyerAddr == null) {
			buyerAddr = new JTextField();
			buyerAddr.setBounds(new Rectangle(88, 78, 134, 22));
		}
		return buyerAddr;
	}

	/**
	 * This method initializes buyerTel
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getBuyerTel() {
		if (buyerTel == null) {
			buyerTel = new JTextField();
			buyerTel.setBounds(new Rectangle(88, 110, 134, 22));
		}
		return buyerTel;
	}

	/**
	 * This method initializes seller
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSeller() {
		if (seller == null) {
			seller = new JTextField();
			seller.setBounds(new Rectangle(88, 14, 134, 22));
		}
		return seller;
	}

	/**
	 * This method initializes sellerContact
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSellerContact() {
		if (sellerContact == null) {
			sellerContact = new JTextField();
			sellerContact.setBounds(new Rectangle(87, 49, 134, 22));
		}
		return sellerContact;
	}

	/**
	 * This method initializes sellerAddr
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSellerAddr() {
		if (sellerAddr == null) {
			sellerAddr = new JTextField();
			sellerAddr.setBounds(new Rectangle(87, 80, 134, 22));
		}
		return sellerAddr;
	}

	/**
	 * This method initializes sellerTel
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSellerTel() {
		if (sellerTel == null) {
			sellerTel = new JTextField();
			sellerTel.setBounds(new Rectangle(87, 112, 134, 22));
		}
		return sellerTel;
	}

	/**
	 * This method initializes factory
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getFactory() {
		if (factory == null) {
			factory = new JTextField();
			factory.setBounds(new Rectangle(90, 23, 134, 21));
		}
		return factory;
	}

	/**
	 * This method initializes factoryContact
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getFactoryContact() {
		if (factoryContact == null) {
			factoryContact = new JTextField();
			factoryContact.setBounds(new Rectangle(90, 55, 134, 22));
		}
		return factoryContact;
	}

	/**
	 * This method initializes factoryAddr
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getFactoryAddr() {
		if (factoryAddr == null) {
			factoryAddr = new JTextField();
			factoryAddr.setBounds(new Rectangle(90, 86, 134, 22));
		}
		return factoryAddr;
	}

	/**
	 * This method initializes factoryTel
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getFactoryTel() {
		if (factoryTel == null) {
			factoryTel = new JTextField();
			factoryTel.setBounds(new Rectangle(90, 115, 134, 22));
		}
		return factoryTel;
	}

	/**
	 * This method initializes contrNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getContrNo() {
		if (contrNo == null) {
			contrNo = new JTextField();
			contrNo.setSize(new Dimension(134, 22));
			contrNo.setLocation(new Point(343, 230));
		}
		return contrNo;
	}

	/**
	 * This method initializes contrDate
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCalendarComboBox getContrDate() {
		if (contrDate == null) {
			contrDate = new JCalendarComboBox();
			contrDate.setSize(new Dimension(134, 22));
			contrDate.setLocation(new Point(343, 262));
		}
		return contrDate;
	}

	/**
	 * This method initializes invoiceNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getInvoiceNo() {
		if (invoiceNo == null) {
			invoiceNo = new JTextField();
			invoiceNo.setSize(new Dimension(134, 22));
			invoiceNo.setLocation(new Point(343, 292));
		}
		return invoiceNo;
	}

	/**
	 * This method initializes invoiceDate
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCalendarComboBox getInvoiceDate() {
		if (invoiceDate == null) {
			invoiceDate = new JCalendarComboBox();
			invoiceDate.setSize(new Dimension(134, 22));
			invoiceDate.setLocation(new Point(343, 328));
		}
		return invoiceDate;
	}

	/**
	 * This method initializes otherNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getOtherNote() {
		if (otherNote == null) {
			otherNote = new JTextField();
			otherNote.setBounds(new Rectangle(343, 359, 134, 22));
		}
		return otherNote;
	}

	/**
	 * This method initializes isSecret
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getIsSecret() {
		if (isSecret == null) {
			isSecret = new JComboBox();
			isSecret.setSize(new Dimension(134, 22));
			isSecret.setLocation(new Point(620, 85));
			isSecret.addItem(new ItemProperty(RepDeclarationType.IsSecretN,
					RepDeclarationType
							.getIsSecret(RepDeclarationType.IsSecretN)));
			isSecret.addItem(new ItemProperty(RepDeclarationType.IsSecretY,
					RepDeclarationType
							.getIsSecret(RepDeclarationType.IsSecretY)));
		}
		return isSecret;
	}

	/**
	 * This method initializes declAddr
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getDeclAddr() {
		if (declAddr == null) {
			declAddr = new JTextField();
			declAddr.setBounds(new Rectangle(620, 195, 134, 22));
		}
		return declAddr;
	}

	/**
	 * This method initializes declPost
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getDeclPost() {
		if (declPost == null) {
			declPost = new JTextField();
			declPost.setSize(new Dimension(134, 22));
			declPost.setLocation(new Point(620, 235));
		}
		return declPost;
	}

	/**
	 * This method initializes declTel
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getDeclTel() {
		if (declTel == null) {
			declTel = new JTextField();
			declTel.setSize(new Dimension(134, 22));
			declTel.setLocation(new Point(620, 262));
		}
		return declTel;
	}

	/**
	 * This method initializes tpMain
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getTpMain() {
		if (tpMain == null) {
			tpMain = new JTabbedPane(JTabbedPane.BOTTOM);
			tpMain.add("公共信息", getPnCommonInfo());
			tpMain.addTab("申报价格1", null, getPnPrice1(), null);
			tpMain.addTab("申报价格2", null, getPnPrice2(), null);
			tpMain.addTab("申报归类", null, getPnMerger(), null);
			tpMain.addTab("申报原地", null, getPnOrigin(), null);
			tpMain.addTab("补充报关单签名信息", null, getPnRepSign(), null);
			if (RepDeclarationType.SupTypePrice.equals(supplmentType)) {
				tpMain.remove(pnMerger);
				tpMain.remove(pnOrigin);
			}
			if (RepDeclarationType.SupTypeMerger.equals(supplmentType)) {
				tpMain.remove(pnPrice1);
				tpMain.remove(pnPrice2);
				tpMain.remove(pnOrigin);
			}
			if (RepDeclarationType.SupTypeOrigin.equals(supplmentType)) {
				tpMain.remove(pnPrice1);
				tpMain.remove(pnPrice2);
				tpMain.remove(pnMerger);
			}
		}
		return tpMain;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setPreferredSize(new Dimension(630, 30));
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnPrite());
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
					checkParameter(((ItemProperty) supType.getSelectedItem())
							.getCode());
					if (!errorInfo
							.equalsIgnoreCase("<html><body></body></html>")) {// 判断有没填写错误的信息
						btnState(DataState.BROWSE);
						saveData(((ItemProperty) supType.getSelectedItem())
								.getCode());
					}
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes btnPrite
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrite() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(ActionEvent e) {
					getPmPrint().show(btnPrint, 0, btnPrint.getHeight());
				}

			});
		}
		return btnPrint;
	}


	public JMenuItem getMiNotOverprintPrintPriceReplenish() {
		if(miNotOverprintPrintPriceReplenish == null) {
			miNotOverprintPrintPriceReplenish = new JMenuItem();
			miNotOverprintPrintPriceReplenish.setText("非套打价格申报");
			miNotOverprintPrintPriceReplenish
					.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Boolean isOverprint = new Boolean(false);
							TcsReplenishReport.getInstance().printReplenish(DgReplenishDeclareCommonInfo.this,decSupplement,isOverprint);
						}
					});
		}
		return miNotOverprintPrintPriceReplenish;
	}

	public JMenuItem getMiOverprintPrintPriceReplenish() {
		if(miOverprintPrintPriceReplenish == null) {
			miOverprintPrintPriceReplenish = new JMenuItem();
			miOverprintPrintPriceReplenish.setText("套打价格申报");
			miOverprintPrintPriceReplenish
					.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Boolean isOverprint = new Boolean(true);
							TcsReplenishReport.getInstance().printReplenish(DgReplenishDeclareCommonInfo.this,decSupplement,isOverprint);
						}
					});
		}
		return miOverprintPrintPriceReplenish;
	}

	public JMenuItem getMiNotOverprintPrintMergerReplenish() {
		if(miNotOverprintPrintMergerReplenish == null) {
			miNotOverprintPrintMergerReplenish = new JMenuItem();
			miNotOverprintPrintMergerReplenish.setText("非套打商品归类申报");
			miNotOverprintPrintMergerReplenish
					.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Boolean isOverprint = new Boolean(false);
							TcsReplenishReport.getInstance().mergerReplenish(DgReplenishDeclareCommonInfo.this,decSupplement,isOverprint);
						}
					});
		}
		return miNotOverprintPrintMergerReplenish;
	}

	public JMenuItem getMiOverprintPrintMergerReplenish() {
		if(miOverprintPrintMergerReplenish == null) {
			miOverprintPrintMergerReplenish = new JMenuItem();
			miOverprintPrintMergerReplenish.setText("套打商品归类申报");
			miOverprintPrintMergerReplenish
					.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Boolean isOverprint = new Boolean(true);
							TcsReplenishReport.getInstance().mergerReplenish(DgReplenishDeclareCommonInfo.this,decSupplement,isOverprint);						}
					});
		}
		return miOverprintPrintMergerReplenish;
	}

	public JMenuItem getMiNotOverprintOriginReplenish() {
		if(miNotOverprintOriginReplenish == null) {
			miNotOverprintOriginReplenish = new JMenuItem();
			miNotOverprintOriginReplenish.setText("非套打原产地申报");
			miNotOverprintOriginReplenish
					.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Boolean isOverprint = new Boolean(false);
							TcsReplenishReport.getInstance().originReplenish(DgReplenishDeclareCommonInfo.this,decSupplement,isOverprint);
						}
					});
		}
		return miNotOverprintOriginReplenish;
	}

	public JMenuItem getMiOverprintPrintOriginReplenish() {
		if(miOverprintPrintOriginReplenish == null) {
			miOverprintPrintOriginReplenish = new JMenuItem();
			miOverprintPrintOriginReplenish.setText("套打原产地申报");
			miOverprintPrintOriginReplenish
					.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Boolean isOverprint = new Boolean(true);
							TcsReplenishReport.getInstance().originReplenish(DgReplenishDeclareCommonInfo.this,decSupplement,isOverprint);
						}
					});
		}
		return miOverprintPrintOriginReplenish;
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
			btnEdit.setPreferredSize(new Dimension(36, 26));
			btnEdit.setLocation(new Point(50, 1));
			btnEdit.setSize(new Dimension(36, 26));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// List dec =
					// baseEncAction.getDecSupplementList(supplmentType,
					// baseCustomsDeclarationCommInfo.getId());
					// decSupplement= (DecSupplementList)dec.get(0);
					entityToui(decSupplement);
					btnState(DataState.ADD);

				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes pnPrice1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnPrice1() {
		if (pnPrice1 == null) {
			jLabel425 = new JLabel();
			jLabel425.setText("备注:");
			jLabel425.setLocation(new Point(15, 95));
			jLabel425.setSize(new Dimension(88, 20));
			jLabel424 = new JLabel();
			jLabel424.setText("总金额:");
			jLabel424.setBounds(new Rectangle(15, 59, 84, 18));
			jLabel423 = new JLabel();
			jLabel423.setText("单位金额:");
			jLabel423.setBounds(new Rectangle(15, 22, 84, 18));
			jLabel422 = new JLabel();
			jLabel422.setText("备注:");
			jLabel422.setLocation(new Point(5, 92));
			jLabel422.setSize(new Dimension(52, 18));
			jLabel421 = new JLabel();
			jLabel421.setText("总金额:");
			jLabel421.setBounds(new Rectangle(4, 59, 52, 18));
			jLabel420 = new JLabel();
			jLabel420.setText("单位金额:");
			jLabel420.setSize(new Dimension(52, 18));
			jLabel420.setLocation(new Point(5, 26));
			jLabel419 = new JLabel();
			jLabel419.setText("备注:");
			jLabel419.setSize(new Dimension(52, 18));
			jLabel419.setLocation(new Point(5, 109));
			jLabel418 = new JLabel();
			jLabel418.setText("总金额:");
			jLabel418.setSize(new Dimension(52, 18));
			jLabel418.setLocation(new Point(5, 69));
			jLabel417 = new JLabel();
			jLabel417.setText("单位金额:");
			jLabel417.setSize(new Dimension(52, 18));
			jLabel417.setLocation(new Point(5, 32));
			jLabel416 = new JLabel();
			jLabel416.setText("备注:");
			jLabel416.setBounds(new Rectangle(20, 107, 84, 18));
			jLabel415 = new JLabel();
			jLabel415.setText("总金额:");
			jLabel415.setSize(new Dimension(89, 18));
			jLabel415.setLocation(new Point(20, 71));
			jLabel414 = new JLabel();
			jLabel414.setText("单位金额:");
			jLabel414.setSize(new Dimension(92, 18));
			jLabel414.setLocation(new Point(19, 29));
			jLabel413 = new JLabel();
			jLabel413.setText("备注:");
			jLabel413.setSize(new Dimension(99, 18));
			jLabel413.setLocation(new Point(21, 110));
			jLabel412 = new JLabel();
			jLabel412.setText("总金额:");
			jLabel412.setSize(new Dimension(99, 18));
			jLabel412.setLocation(new Point(19, 70));
			jLabel411 = new JLabel();
			jLabel411.setText("单位金额:");
			jLabel411.setSize(new Dimension(99, 18));
			jLabel411.setLocation(new Point(21, 32));
			jLabel410 = new JLabel();
			jLabel410.setText("备注:");
			jLabel410.setBounds(new Rectangle(7, 96, 70, 18));
			jLabel49 = new JLabel();
			jLabel49.setText("总金额:");
			jLabel49.setBounds(new Rectangle(5, 60, 71, 18));
			jLabel48 = new JLabel();
			jLabel48.setText("单位金额:");
			jLabel48.setSize(new Dimension(70, 18));
			jLabel48.setLocation(new Point(4, 21));
			jLabel47 = new JLabel();
			jLabel47.setText("币制:");
			jLabel47.setSize(new Dimension(53, 18));
			jLabel47.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel47.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel47.setForeground(Color.blue);
			jLabel47.setLocation(new Point(570, 420));
			jLabel46 = new JLabel();
			jLabel46.setBounds(new Rectangle(15, 309, 102, 18));
			jLabel46.setText("备注:");
			jLabel45 = new JLabel();
			jLabel45.setBounds(new Rectangle(570, 314, 97, 79));
			jLabel45
					.setText("<html><body>货物的价格是否受<br>到使货物的成交价<br>格无法确定的条件<br>或因素的影响:</body></html>");
			jLabel44 = new JLabel();
			jLabel44
					.setText("<html><body>买方处置或使用货<br>物时是否受到除行<br>政法规规定的限制<br>以及对货物销售地<br>域限制以外的限制<br>:</body></html>");
			jLabel44.setLocation(new Point(15, 195));
			jLabel44.setSize(new Dimension(102, 103));
			jLabel43 = new JLabel();
			jLabel43.setText("<html><body>进口货物成交价格<br>相近情况:</body></html>");
			jLabel43.setSize(new Dimension(98, 39));
			jLabel43.setPreferredSize(new Dimension(120, 34));
			jLabel43.setLocation(new Point(15, 145));
			jLabel42 = new JLabel();
			jLabel42
					.setText("<html><body>买卖双方关系是否<br>影响进口货物成交<br>价格:</body></html>");
			jLabel42.setSize(new Dimension(102, 64));
			jLabel42.setPreferredSize(new Dimension(120, 34));
			jLabel42.setLocation(new Point(15, 68));
			jLabel41 = new JLabel();
			jLabel41.setPreferredSize(new Dimension(120, 34));
			jLabel41.setLocation(new Point(15, 17));
			jLabel41.setSize(new Dimension(103, 34));
			jLabel41.setText("<html><body>买卖双方之间存在<br>的关系:</body></html>");
			pnPrice1 = new JPanel();
			pnPrice1.setLayout(null);
			pnPrice1.add(jLabel41, null);
			pnPrice1.add(getTfI_BabRel(), null);
			pnPrice1.add(getBtnBuySellRel(), null);
			pnPrice1.add(jLabel42, null);
			pnPrice1.add(getCbI_PriceEffect(), null);
			pnPrice1.add(jLabel43, null);
			pnPrice1.add(getCbI_PriceClose(), null);
			pnPrice1.add(jLabel44, null);
			pnPrice1.add(getCbI_OtherLimited(), null);
			pnPrice1.add(jLabel45, null);
			pnPrice1.add(jLabel46, null);
			pnPrice1.add(getTfI_Note1(), null);
			pnPrice1.add(getPnInvoice(), null);
			pnPrice1.add(getPnIndirectPayment(), null);
			pnPrice1.add(getPnCommission(), null);
			pnPrice1.add(getPnContainer(), null);
			pnPrice1.add(getPnContainer2(), null);
			pnPrice1.add(getPnService(), null);
			pnPrice1.add(jLabel47, null);
			pnPrice1.add(getCbI_OtherEffect(), null);
			pnPrice1.add(getCbbCurr(), null);
		}
		return pnPrice1;
	}

	/**
	 * This method initializes cbI_PriceEffect
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbI_PriceEffect() {
		if (cbI_PriceEffect == null) {
			cbI_PriceEffect = new JComboBox();
			cbI_PriceEffect.setBounds(new Rectangle(124, 93, 125, 22));
			cbI_PriceEffect.setPreferredSize(new Dimension(31, 22));
			cbI_PriceEffect
					.addItem(new ItemProperty(
							RepDeclarationType.I_PriceEffectN,
							RepDeclarationType
									.getI_PriceEffect(RepDeclarationType.I_PriceEffectN)));
			cbI_PriceEffect
					.addItem(new ItemProperty(
							RepDeclarationType.I_PriceEffectY,
							RepDeclarationType
									.getI_PriceEffect(RepDeclarationType.I_PriceEffectY)));
		}
		return cbI_PriceEffect;
	}

	/**
	 * This method initializes cbI_PriceClose
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbI_PriceClose() {
		if (cbI_PriceClose == null) {
			cbI_PriceClose = new JComboBox();
			cbI_PriceClose.setBounds(new Rectangle(123, 153, 125, 22));
			cbI_PriceClose
					.addItem(new ItemProperty(
							RepDeclarationType.I_PriceCloseT,
							RepDeclarationType
									.getI_PriceClose((RepDeclarationType.I_PriceCloseT))));
			cbI_PriceClose
					.addItem(new ItemProperty(
							RepDeclarationType.I_PriceCloseC,
							RepDeclarationType
									.getI_PriceClose((RepDeclarationType.I_PriceCloseC))));
			cbI_PriceClose
					.addItem(new ItemProperty(
							RepDeclarationType.I_PriceCloseUd,
							RepDeclarationType
									.getI_PriceClose((RepDeclarationType.I_PriceCloseUd))));
			cbI_PriceClose
					.addItem(new ItemProperty(
							RepDeclarationType.I_PriceCloseN,
							RepDeclarationType
									.getI_PriceClose((RepDeclarationType.I_PriceCloseN))));
			cbI_PriceClose.setUI(new CustomBaseComboBoxUI(430));
		}
		return cbI_PriceClose;
	}

	/**
	 * This method initializes cbI_OtherLimited
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbI_OtherLimited() {
		if (cbI_OtherLimited == null) {
			cbI_OtherLimited = new JComboBox();
			cbI_OtherLimited.setSize(new Dimension(125, 22));
			cbI_OtherLimited.setLocation(new Point(124, 240));
			cbI_OtherLimited
					.addItem(new ItemProperty(
							RepDeclarationType.I_OtherLimitedN,
							RepDeclarationType
									.getI_OtherLimited((RepDeclarationType.I_OtherLimitedN))));
			cbI_OtherLimited
					.addItem(new ItemProperty(
							RepDeclarationType.I_OtherLimitedY,
							RepDeclarationType
									.getI_OtherLimited((RepDeclarationType.I_OtherLimitedY))));

		}
		return cbI_OtherLimited;
	}

	/**
	 * This method initializes cbI_OtherEffect
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbI_OtherEffect() {
		if (cbI_OtherEffect == null) {
			cbI_OtherEffect = new JComboBox();
			cbI_OtherEffect.setBounds(new Rectangle(670, 340, 93, 22));
			cbI_OtherEffect
					.addItem(new ItemProperty(
							RepDeclarationType.I_OtherEffectN,
							RepDeclarationType
									.getI_OtherEffect(RepDeclarationType.I_OtherEffectN)));
			cbI_OtherEffect
					.addItem(new ItemProperty(
							RepDeclarationType.I_OtherEffectY,
							RepDeclarationType
									.getI_OtherEffect(RepDeclarationType.I_OtherEffectY)));
		}
		return cbI_OtherEffect;
	}

	/**
	 * This method initializes tfI_Note1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_Note1() {
		if (tfI_Note1 == null) {
			tfI_Note1 = new JTextField();
			tfI_Note1.setBounds(new Rectangle(124, 310, 125, 22));
		}
		return tfI_Note1;
	}

	/**
	 * This method initializes tfInvoicePrice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInvoicePrice() {
		if (tfInvoicePrice == null) {
			tfInvoicePrice = new JTextField();
			tfInvoicePrice.setSize(new Dimension(125, 22));
			tfInvoicePrice.setLocation(new Point(109, 21));
		}
		return tfInvoicePrice;
	}

	/**
	 * This method initializes tfInvoiceAmount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInvoiceAmount() {
		if (tfInvoiceAmount == null) {
			tfInvoiceAmount = new JTextField();
			tfInvoiceAmount.setSize(new Dimension(125, 22));
			tfInvoiceAmount.setLocation(new Point(109, 59));
		}
		return tfInvoiceAmount;
	}

	/**
	 * This method initializes tfInvoiceNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInvoiceNote() {
		if (tfInvoiceNote == null) {
			tfInvoiceNote = new JTextField();
			tfInvoiceNote.setBounds(new Rectangle(109, 96, 125, 22));
		}
		return tfInvoiceNote;
	}

	/**
	 * This method initializes tfGoodsPrice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfGoodsPrice() {
		if (tfGoodsPrice == null) {
			tfGoodsPrice = new JTextField();
			tfGoodsPrice.setBounds(new Rectangle(122, 32, 125, 22));
		}
		return tfGoodsPrice;
	}

	/**
	 * This method initializes tfGoodsAmount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfGoodsAmount() {
		if (tfGoodsAmount == null) {
			tfGoodsAmount = new JTextField();
			tfGoodsAmount.setBounds(new Rectangle(123, 68, 125, 22));
		}
		return tfGoodsAmount;
	}

	/**
	 * This method initializes tfGoodsNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfGoodsNote() {
		if (tfGoodsNote == null) {
			tfGoodsNote = new JTextField();
			tfGoodsNote.setBounds(new Rectangle(125, 107, 98, 22));
		}
		return tfGoodsNote;
	}

	/**
	 * This method initializes btnGoodsNote
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnGoodsNote() {
		if (btnGoodsNote == null) {
			btnGoodsNote = new JButton();
			btnGoodsNote.setBounds(new Rectangle(228, 107, 25, 22));
			btnGoodsNote.setText("...");
			btnGoodsNote.setName("GoodsNote");
			btnGoodsNote.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String goodsNote  = tfGoodsNote.getText();
					DgRepDeclarationNote dgNote = new DgRepDeclarationNote(
							btnGoodsNote,goodsNote);
					dgNote.setVisible(true);
					tfGoodsNote.setText(dgNote.getNote());
				}
			});
		}
		return btnGoodsNote;
	}

	/**
	 * This method initializes tfI_CommissionPrice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_CommissionPrice() {
		if (tfI_CommissionPrice == null) {
			tfI_CommissionPrice = new JTextField();
			tfI_CommissionPrice.setSize(new Dimension(125, 22));
			tfI_CommissionPrice.setLocation(new Point(126, 28));
		}
		return tfI_CommissionPrice;
	}

	/**
	 * This method initializes tfI_CommissionAmount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_CommissionAmount() {
		if (tfI_CommissionAmount == null) {
			tfI_CommissionAmount = new JTextField();
			tfI_CommissionAmount.setBounds(new Rectangle(126, 71, 125, 22));
		}
		return tfI_CommissionAmount;
	}

	/**
	 * This method initializes tfI_CommissionNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_CommissionNote() {
		if (tfI_CommissionNote == null) {
			tfI_CommissionNote = new JTextField();
			tfI_CommissionNote.setBounds(new Rectangle(126, 107, 99, 22));
		}
		return tfI_CommissionNote;
	}

	/**
	 * This method initializes tfI_ContainerPrice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_ContainerPrice() {
		if (tfI_ContainerPrice == null) {
			tfI_ContainerPrice = new JTextField();
			tfI_ContainerPrice.setBounds(new Rectangle(62, 29, 121, 22));
		}
		return tfI_ContainerPrice;
	}

	/**
	 * This method initializes tfI_ContainerAmount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_ContainerAmount() {
		if (tfI_ContainerAmount == null) {
			tfI_ContainerAmount = new JTextField();
			tfI_ContainerAmount.setBounds(new Rectangle(62, 67, 121, 22));
		}
		return tfI_ContainerAmount;
	}

	/**
	 * This method initializes tfI_ContainerNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_ContainerNote() {
		if (tfI_ContainerNote == null) {
			tfI_ContainerNote = new JTextField();
			tfI_ContainerNote.setBounds(new Rectangle(62, 107, 98, 22));

		}
		return tfI_ContainerNote;
	}

	/**
	 * This method initializes btnI_ContainerNote
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnI_ContainerNote() {
		if (btnI_ContainerNote == null) {
			btnI_ContainerNote = new JButton();
			btnI_ContainerNote.setBounds(new Rectangle(162, 106, 25, 22));
			btnI_ContainerNote.setText("...");
			btnI_ContainerNote
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String containerNote = tfI_ContainerNote.getText();
							DgRepDeclarationNote dgNote = new DgRepDeclarationNote(
									btnI_ContainerNote,containerNote);
							dgNote.setVisible(true);
							tfI_ContainerNote.setText(dgNote.getNote());
						}
					});
		}
		return btnI_ContainerNote;
	}

	/**
	 * This method initializes tfI_PackPrice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_PackPrice() {
		if (tfI_PackPrice == null) {
			tfI_PackPrice = new JTextField();
			tfI_PackPrice.setLocation(new Point(62, 24));
			tfI_PackPrice.setSize(new Dimension(125, 22));
		}
		return tfI_PackPrice;
	}

	/**
	 * This method initializes tfI_PackAmount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_PackAmount() {
		if (tfI_PackAmount == null) {
			tfI_PackAmount = new JTextField();
			tfI_PackAmount.setBounds(new Rectangle(62, 58, 125, 22));
		}
		return tfI_PackAmount;
	}

	/**
	 * This method initializes tfI_PackNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_PackNote() {
		if (tfI_PackNote == null) {
			tfI_PackNote = new JTextField();
			tfI_PackNote.setLocation(new Point(62, 90));
			tfI_PackNote.setSize(new Dimension(100, 22));
		}
		return tfI_PackNote;
	}

	/**
	 * This method initializes btnI_PackNote
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnI_PackNote() {
		if (btnI_PackNote == null) {
			btnI_PackNote = new JButton();
			btnI_PackNote.setBounds(new Rectangle(162, 90, 25, 22));
			btnI_PackNote.setText("...");
			btnI_PackNote
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String packNote = tfI_PackNote.getText();
							DgRepDeclarationNote dgNote = new DgRepDeclarationNote(
									btnI_PackNote,packNote);
							dgNote.setVisible(true);
							tfI_PackNote.setText(dgNote.getNote());
						}
					});
		}
		return btnI_PackNote;
	}

	/**
	 * This method initializes tfI_PartPrice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_PartPrice() {
		if (tfI_PartPrice == null) {
			tfI_PartPrice = new JTextField();
			tfI_PartPrice.setSize(new Dimension(126, 22));
			tfI_PartPrice.setLocation(new Point(127, 21));
		}
		return tfI_PartPrice;
	}

	/**
	 * This method initializes tfI_PartAmount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_PartAmount() {
		if (tfI_PartAmount == null) {
			tfI_PartAmount = new JTextField();
			tfI_PartAmount.setSize(new Dimension(126, 22));
			tfI_PartAmount.setLocation(new Point(126, 57));
		}
		return tfI_PartAmount;
	}

	/**
	 * This method initializes tfI_PartNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_PartNote() {
		if (tfI_PartNote == null) {
			tfI_PartNote = new JTextField();
			tfI_PartNote.setLocation(new Point(126, 95));
			tfI_PartNote.setSize(new Dimension(100, 22));
		}
		return tfI_PartNote;
	}

	/**
	 * This method initializes btnI_PartNote
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnI_PartNote() {
		if (btnI_PartNote == null) {
			btnI_PartNote = new JButton();
			btnI_PartNote.setText("...");
			btnI_PartNote.setLocation(new Point(227, 94));
			btnI_PartNote.setSize(new Dimension(25, 22));
			btnI_PartNote
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String partNote = tfI_PartNote.getText();
							DgRepDeclarationNote dgNote = new DgRepDeclarationNote(
									btnI_PartNote,partNote);
							dgNote.setVisible(true);
							tfI_PartNote.setText(dgNote.getNote());
						}
					});
		}
		return btnI_PartNote;
	}

	/**
	 * This method initializes pnPrice2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnPrice2() {
		if (pnPrice2 == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(540, 400, 113, 69));
			jLabel7.setText("<html><body>卖方是否直接或间接<br>从买方对该货物进口<br>后销售、处置或者使<br>用所得中获得收益:</body></html>");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(540, 322, 113, 72));
			jLabel6.setText("<html><body>买方是否应直接或间<br>接支付与进口货物有<br>关并作为货物销售条<br>件的特许权使用费:</body></html>");
			jLabel450 = new JLabel();
			jLabel450.setBounds(new Rectangle(540, 278, 113, 36));
			jLabel450
					.setText("<html><body>出口关税是否已经从<br>申报价格中扣除:</body></html>");
			jLabel449 = new JLabel();
			jLabel449.setText("备注:");
			jLabel449.setBounds(new Rectangle(21, 80, 63, 19));
			jLabel448 = new JLabel();
			jLabel448.setText("总金额:");
			jLabel448.setBounds(new Rectangle(21, 50, 63, 19));
			jLabel447 = new JLabel();
			jLabel447.setText("单位金额:");
			jLabel447.setSize(new Dimension(63, 19));
			jLabel447.setLocation(new Point(21, 22));
			jLabel446 = new JLabel();
			jLabel446.setText("备注:");
			jLabel446.setBounds(new Rectangle(27, 86, 63, 19));
			jLabel445 = new JLabel();
			jLabel445.setText("总金额:");
			jLabel445.setBounds(new Rectangle(27, 55, 63, 19));
			jLabel444 = new JLabel();
			jLabel444.setText("单位金额:");
			jLabel444.setSize(new Dimension(63, 19));
			jLabel444.setLocation(new Point(26, 23));
			jLabel443 = new JLabel();
			jLabel443.setText("备注:");
			jLabel443.setBounds(new Rectangle(25, 89, 63, 19));
			jLabel442 = new JLabel();
			jLabel442.setText("总金额:");
			jLabel442.setBounds(new Rectangle(25, 55, 63, 19));
			jLabel441 = new JLabel();
			jLabel441.setText("单位金额:");
			jLabel441.setBounds(new Rectangle(25, 26, 63, 19));
			jLabel440 = new JLabel();
			jLabel440.setText("备注：");
			jLabel440.setSize(new Dimension(60, 20));
			jLabel440.setLocation(new Point(21, 56));
			jLabel439 = new JLabel();
			jLabel439.setText("总金额:");
			jLabel439.setSize(new Dimension(70, 19));
			jLabel439.setLocation(new Point(289, 26));
			jLabel438 = new JLabel();
			jLabel438.setText("单位金额:");
			jLabel438.setSize(new Dimension(60, 20));
			jLabel438.setLocation(new Point(20, 26));
			jLabel437 = new JLabel();
			jLabel437.setText("备注:");
			jLabel437.setBounds(new Rectangle(20, 81, 63, 19));
			jLabel436 = new JLabel();
			jLabel436.setText("总金额:");
			jLabel436.setBounds(new Rectangle(20, 53, 63, 19));
			jLabel435 = new JLabel();
			jLabel435.setText("单位金额:");
			jLabel435.setSize(new Dimension(63, 19));
			jLabel435.setLocation(new Point(20, 24));
			jLabel434 = new JLabel();
			jLabel434.setText("备注:");
			jLabel434.setBounds(new Rectangle(20, 62, 60, 20));
			jLabel433 = new JLabel();
			jLabel433.setText("总金额:");
			jLabel433.setBounds(new Rectangle(289, 24, 59, 21));
			jLabel432 = new JLabel();
			jLabel432.setText("单位金额:");
			jLabel432.setBounds(new Rectangle(20, 28, 60, 20));
			jLabel431 = new JLabel();
			jLabel431.setText("备注:");
			jLabel431.setBounds(new Rectangle(23, 88, 60, 18));
			jLabel430 = new JLabel();
			jLabel430.setText("总金额:");
			jLabel430.setBounds(new Rectangle(23, 55, 60, 18));
			jLabel429 = new JLabel();
			jLabel429.setText("单位金额：");
			jLabel429.setLocation(new Point(23, 24));
			jLabel429.setSize(new Dimension(60, 18));
			jLabel428 = new JLabel();
			jLabel428.setText("备注:");
			jLabel428.setBounds(new Rectangle(25, 90, 57, 18));
			jLabel427 = new JLabel();
			jLabel427.setText("单位金额：");
			jLabel427.setSize(new Dimension(60, 18));
			jLabel427.setLocation(new Point(23, 21));
			pnPrice2 = new JPanel();
			pnPrice2.setLayout(null);
			pnPrice2.add(jLabel450, null);
			pnPrice2.add(getPnPart(), null);
			pnPrice2.add(getPnCmaterials(), null);
			pnPrice2.add(getPnFreight(), null);
			pnPrice2.add(getPnRelateFreight(), null);
			pnPrice2.add(getPnConcession(), null);
			pnPrice2.add(getPnInsurance(), null);
			pnPrice2.add(getPnEdesign(), null);
			pnPrice2.add(getPnResale(), null);
			pnPrice2.add(getCbbE_IsDutyDel(), null);
			pnPrice2.add(jLabel6, null);
			pnPrice2.add(getCbbI_IsUsefee(), null);
			pnPrice2.add(jLabel7, null);
			pnPrice2.add(getCbbI_IsProfit(), null);
		}
		return pnPrice2;
	}

	/**
	 * This method initializes tfI_ToolAmount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_ToolAmount() {
		if (tfI_ToolAmount == null) {
			tfI_ToolAmount = new JTextField();
			tfI_ToolAmount.setPreferredSize(new Dimension(120, 22));
			tfI_ToolAmount.setBounds(new Rectangle(90, 52, 125, 22));
		}
		return tfI_ToolAmount;
	}

	/**
	 * This method initializes tfI_ToolNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_ToolNote() {
		if (tfI_ToolNote == null) {
			tfI_ToolNote = new JTextField();
			tfI_ToolNote.setPreferredSize(new Dimension(97, 22));
			tfI_ToolNote.setBounds(new Rectangle(91, 89, 101, 22));
		}
		return tfI_ToolNote;
	}

	/**
	 * This method initializes tfI_LossPrice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_LossPrice() {
		if (tfI_LossPrice == null) {
			tfI_LossPrice = new JTextField();
			tfI_LossPrice.setBounds(new Rectangle(91, 22, 125, 22));
		}
		return tfI_LossPrice;
	}

	/**
	 * This method initializes tfI_LossAmount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_LossAmount() {
		if (tfI_LossAmount == null) {
			tfI_LossAmount = new JTextField();
			tfI_LossAmount.setBounds(new Rectangle(91, 50, 125, 22));
		}
		return tfI_LossAmount;
	}

	/**
	 * This method initializes tfI_LossNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_LossNote() {
		if (tfI_LossNote == null) {
			tfI_LossNote = new JTextField();
			tfI_LossNote.setBounds(new Rectangle(91, 86, 97, 22));
		}
		return tfI_LossNote;
	}

	/**
	 * This method initializes tfI_DesignPrice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_DesignPrice() {
		if (tfI_DesignPrice == null) {
			tfI_DesignPrice = new JTextField();
			tfI_DesignPrice.setBounds(new Rectangle(87, 27, 125, 22));
		}
		return tfI_DesignPrice;
	}

	/**
	 * This method initializes tfI_DesignAmount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_DesignAmount() {
		if (tfI_DesignAmount == null) {
			tfI_DesignAmount = new JTextField();
			tfI_DesignAmount.setSize(new Dimension(125, 22));
			tfI_DesignAmount.setLocation(new Point(362, 24));
		}
		return tfI_DesignAmount;
	}

	/**
	 * This method initializes tfI_DesignNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_DesignNote() {
		if (tfI_DesignNote == null) {
			tfI_DesignNote = new JTextField();
			tfI_DesignNote.setBounds(new Rectangle(88, 62, 101, 22));
		}
		return tfI_DesignNote;
	}

	/**
	 * This method initializes tfI_UsefeePrice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_UsefeePrice() {
		if (tfI_UsefeePrice == null) {
			tfI_UsefeePrice = new JTextField();
			tfI_UsefeePrice.setBounds(new Rectangle(88, 24, 125, 22));
		}
		return tfI_UsefeePrice;
	}

	/**
	 * This method initializes tfI_UsefeeAmount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_UsefeeAmount() {
		if (tfI_UsefeeAmount == null) {
			tfI_UsefeeAmount = new JTextField();
			tfI_UsefeeAmount.setBounds(new Rectangle(88, 52, 125, 22));
		}
		return tfI_UsefeeAmount;
	}

	/**
	 * This method initializes tfI_UsefeeNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_UsefeeNote() {
		if (tfI_UsefeeNote == null) {
			tfI_UsefeeNote = new JTextField();
			tfI_UsefeeNote.setBounds(new Rectangle(88, 80, 101, 22));
		}
		return tfI_UsefeeNote;
	}

	/**
	 * This method initializes btnI_UsefeeNote
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnI_UsefeeNote() {
		if (btnI_UsefeeNote == null) {
			btnI_UsefeeNote = new JButton();
			btnI_UsefeeNote.setText("...");
			btnI_UsefeeNote.setBounds(new Rectangle(188, 81, 25, 22));
			btnI_UsefeeNote
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String usefeeNote = tfI_UsefeeNote.getText();
							DgRepDeclarationNote dgNote = new DgRepDeclarationNote(
									btnI_UsefeeNote,usefeeNote);
							dgNote.setVisible(true);
							tfI_UsefeeNote.setText(dgNote.getNote());
						}
					});
		}
		return btnI_UsefeeNote;
	}

	/**
	 * This method initializes btnI_ToolNote
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnI_ToolNote() {
		if (btnI_ToolNote == null) {
			btnI_ToolNote = new JButton();
			btnI_ToolNote.setPreferredSize(new Dimension(25, 22));
			btnI_ToolNote.setBounds(new Rectangle(191, 88, 25, 22));
			btnI_ToolNote.setText("...");
			btnI_ToolNote
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String toolNote = tfI_ToolNote.getText();
							DgRepDeclarationNote dgNote = new DgRepDeclarationNote(
									btnI_ToolNote,toolNote);
							dgNote.setVisible(true);
							tfI_ToolNote.setText(dgNote.getNote());
						}
					});
		}
		return btnI_ToolNote;
	}

	/**
	 * This method initializes btnI_LossNote
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnI_LossNote() {
		if (btnI_LossNote == null) {
			btnI_LossNote = new JButton();
			btnI_LossNote.setText("...");
			btnI_LossNote.setBounds(new Rectangle(189, 86, 25, 22));
			btnI_LossNote
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String lossNote = tfI_LossNote.getText();
							DgRepDeclarationNote dgNote = new DgRepDeclarationNote(
									btnI_LossNote,lossNote);
							dgNote.setVisible(true);
							tfI_LossNote.setText(dgNote.getNote());
						}
					});
		}
		return btnI_LossNote;
	}

	/**
	 * This method initializes btnI_DesignNote
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnI_DesignNote() {
		if (btnI_DesignNote == null) {
			btnI_DesignNote = new JButton();
			btnI_DesignNote.setText("...");
			btnI_DesignNote.setBounds(new Rectangle(187, 62, 25, 22));
			btnI_DesignNote
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String designNote = tfI_DesignNote.getText();
							DgRepDeclarationNote dgNote = new DgRepDeclarationNote(
									btnI_DesignNote,designNote);
							dgNote.setVisible(true);
							tfI_DesignNote.setText(dgNote.getNote());
						}
					});
		}
		return btnI_DesignNote;
	}

	/**
	 * This method initializes tfI_ProfitPrice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_ProfitPrice() {
		if (tfI_ProfitPrice == null) {
			tfI_ProfitPrice = new JTextField();
			tfI_ProfitPrice.setSize(new Dimension(125, 23));
			tfI_ProfitPrice.setLocation(new Point(88, 25));
		}
		return tfI_ProfitPrice;
	}

	/**
	 * This method initializes tfI_ProfitAmount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_ProfitAmount() {
		if (tfI_ProfitAmount == null) {
			tfI_ProfitAmount = new JTextField();
			tfI_ProfitAmount.setSize(new Dimension(125, 22));
			tfI_ProfitAmount.setLocation(new Point(362, 25));
		}
		return tfI_ProfitAmount;
	}

	/**
	 * This method initializes tfI_ProfitNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_ProfitNote() {
		if (tfI_ProfitNote == null) {
			tfI_ProfitNote = new JTextField();
			tfI_ProfitNote.setSize(new Dimension(101, 22));
			tfI_ProfitNote.setLocation(new Point(88, 56));
		}
		return tfI_ProfitNote;
	}

	/**
	 * This method initializes tfI_FeePrice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_FeePrice() {
		if (tfI_FeePrice == null) {
			tfI_FeePrice = new JTextField();
			tfI_FeePrice.setBounds(new Rectangle(96, 25, 125, 22));
		}
		return tfI_FeePrice;
	}

	/**
	 * This method initializes tfI_FeeAmount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_FeeAmount() {
		if (tfI_FeeAmount == null) {
			tfI_FeeAmount = new JTextField();
			tfI_FeeAmount.setBounds(new Rectangle(96, 59, 125, 22));
		}
		return tfI_FeeAmount;
	}

	/**
	 * This method initializes tfI_FeeNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_FeeNote() {
		if (tfI_FeeNote == null) {
			tfI_FeeNote = new JTextField();
			tfI_FeeNote.setBounds(new Rectangle(96, 86, 101, 22));
		}
		return tfI_FeeNote;
	}

	/**
	 * This method initializes btnI_FeeNote
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnI_FeeNote() {
		if (btnI_FeeNote == null) {
			btnI_FeeNote = new JButton();
			btnI_FeeNote.setText("...");
			btnI_FeeNote.setBounds(new Rectangle(195, 86, 25, 22));
			btnI_FeeNote.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String feeNote = tfI_FeeNote.getText(); 
					DgRepDeclarationNote dgNote = new DgRepDeclarationNote(
							btnI_FeeNote,feeNote);
					dgNote.setVisible(true);
					tfI_FeeNote.setText(dgNote.getNote());
				}
			});
		}
		return btnI_FeeNote;
	}

	/**
	 * This method initializes tfI_OtherPrice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_OtherPrice() {
		if (tfI_OtherPrice == null) {
			tfI_OtherPrice = new JTextField();
			tfI_OtherPrice.setSize(new Dimension(125, 22));
			tfI_OtherPrice.setLocation(new Point(96, 23));
		}
		return tfI_OtherPrice;
	}

	/**
	 * This method initializes tfI_OtherAmount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_OtherAmount() {
		if (tfI_OtherAmount == null) {
			tfI_OtherAmount = new JTextField();
			tfI_OtherAmount.setSize(new Dimension(125, 22));
			tfI_OtherAmount.setLocation(new Point(96, 54));
		}
		return tfI_OtherAmount;
	}

	/**
	 * This method initializes tfI_OtherNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_OtherNote() {
		if (tfI_OtherNote == null) {
			tfI_OtherNote = new JTextField();
			tfI_OtherNote.setSize(new Dimension(101, 22));
			tfI_OtherNote.setLocation(new Point(96, 85));
		}
		return tfI_OtherNote;
	}

	/**
	 * This method initializes btnI_OtherNote
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnI_OtherNote() {
		if (btnI_OtherNote == null) {
			btnI_OtherNote = new JButton();
			btnI_OtherNote.setText("...");
			btnI_OtherNote.setBounds(new Rectangle(204, 84, 25, 22));
			btnI_OtherNote
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String otherNote = tfI_OtherNote.getText();
							DgRepDeclarationNote dgNote = new DgRepDeclarationNote(
									btnI_OtherNote,otherNote);
							dgNote.setVisible(true);
							tfI_OtherNote.setText(dgNote.getNote());
						}
					});
		}
		return btnI_OtherNote;
	}

	/**
	 * This method initializes tfI_InsurPrice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_InsurPrice() {
		if (tfI_InsurPrice == null) {
			tfI_InsurPrice = new JTextField();
			tfI_InsurPrice.setSize(new Dimension(125, 22));
			tfI_InsurPrice.setLocation(new Point(88, 22));
		}
		return tfI_InsurPrice;
	}

	/**
	 * This method initializes tfI_InsurAmount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_InsurAmount() {
		if (tfI_InsurAmount == null) {
			tfI_InsurAmount = new JTextField();
			tfI_InsurAmount.setSize(new Dimension(125, 22));
			tfI_InsurAmount.setLocation(new Point(88, 49));
		}
		return tfI_InsurAmount;
	}

	/**
	 * This method initializes tfI_InsurNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_InsurNote() {
		if (tfI_InsurNote == null) {
			tfI_InsurNote = new JTextField();
			tfI_InsurNote.setSize(new Dimension(101, 22));
			tfI_InsurNote.setLocation(new Point(88, 79));
		}
		return tfI_InsurNote;
	}

	/**
	 * This method initializes btnI_InsurNote
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnI_InsurNote() {
		if (btnI_InsurNote == null) {
			btnI_InsurNote = new JButton();
			btnI_InsurNote.setText("...");
			btnI_InsurNote.setLocation(new Point(188, 79));
			btnI_InsurNote.setSize(new Dimension(25, 22));
			btnI_InsurNote
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String insurNote = tfI_InsurNote.getText();
							DgRepDeclarationNote dgNote = new DgRepDeclarationNote(
									btnI_InsurNote,insurNote);
							dgNote.setVisible(true);
							tfI_InsurNote.setText(dgNote.getNote());
						}
					});
		}
		return btnI_InsurNote;
	}

	/**
	 * This method initializes pnMerger
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnMerger() {
		if (pnMerger == null) {
			jLabel458 = new JLabel();
			jLabel458.setText("商品信息选项:");
			jLabel458.setSize(new Dimension(124, 18));
			jLabel458.setLocation(new Point(412, 365));
			jLabel457 = new JLabel();
			jLabel457.setText("<html><body>该商品是否曾被海关取<br>样化验:</body></html>");
			jLabel457.setSize(new Dimension(124, 34));
			jLabel457.setForeground(Color.blue);
			jLabel457.setLocation(new Point(412, 277));
			jLabel456 = new JLabel();
			jLabel456.setText("<html><body>作出预归类决定的直属<br>海关:</body></html>");
			jLabel456.setSize(new Dimension(124, 34));
			jLabel456.setForeground(Color.blue);
			jLabel456.setLocation(new Point(412, 190));
			jLabel455 = new JLabel();
			jLabel455.setText("预归类决定书商品编码:");
			jLabel455.setSize(new Dimension(124, 18));
			jLabel455.setForeground(Color.blue);
			jLabel455.setLocation(new Point(412, 120));
			jLabel454 = new JLabel();
			jLabel454.setText("预归类决定书编号:");
			jLabel454.setSize(new Dimension(124, 18));
			jLabel454.setLocation(new Point(102, 365));
			jLabel453 = new JLabel();
			jLabel453
					.setText("<html><body>该商品是否取得过海关<br>预归类决定书:</body></html>");
			jLabel453.setSize(new Dimension(124, 42));
			jLabel453.setForeground(Color.blue);
			jLabel453.setLocation(new Point(102, 277));
			jLabel452 = new JLabel();
			jLabel452.setText("<html><body>进/出口国地区海关商<br>品编码:</body></html>");
			jLabel452.setSize(new Dimension(124, 40));
			jLabel452.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel452.setForeground(Color.blue);
			jLabel452.setLocation(new Point(102, 190));
			jLabel451 = new JLabel();
			jLabel451.setText("商品其他名：:");
			jLabel451.setSize(new Dimension(124, 28));
			jLabel451.setPreferredSize(new Dimension(124, 88));
			jLabel451.setLocation(new Point(102, 120));
			pnMerger = new JPanel();
			pnMerger.setLayout(null);
			pnMerger.add(jLabel451, null);
			pnMerger.add(getTfGNameOther(), null);
			pnMerger.add(jLabel452, null);
			pnMerger.add(getBntGNameOther(), null);
			pnMerger.add(getTfCodeTsOther(), null);
			pnMerger.add(jLabel453, null);
			pnMerger.add(getCbIsClassDecision(), null);
			pnMerger.add(jLabel454, null);
			pnMerger.add(getTfDecisionNO(), null);
			pnMerger.add(jLabel455, null);
			pnMerger.add(getTfCodeTsDecision(), null);
			pnMerger.add(jLabel456, null);
			pnMerger.add(getCbDecisionCus(), null);
			pnMerger.add(jLabel457, null);
			pnMerger.add(getCbIsSampleTest(), null);
			pnMerger.add(jLabel458, null);
			pnMerger.add(getTfGoptions(), null);
			pnMerger.add(getBtnGoptions(), null);
			
		}
		return pnMerger;
	}

	/**
	 * This method initializes tfGNameOther
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfGNameOther() {
		if (tfGNameOther == null) {
			tfGNameOther = new JTextField();
			tfGNameOther.setPreferredSize(new Dimension(101, 22));
			tfGNameOther.setSize(new Dimension(101, 22));
			tfGNameOther.setLocation(new Point(238, 124));
		}
		return tfGNameOther;
	}

	/**
	 * This method initializes bntGNameOther
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBntGNameOther() {
		if (bntGNameOther == null) {
			bntGNameOther = new JButton();
			bntGNameOther.setLocation(new Point(338, 124));
			bntGNameOther.setText("...");
			bntGNameOther.setSize(new Dimension(25, 22));
			bntGNameOther
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String gNameOther = tfGNameOther.getText();
							DgRepDeclarationNote dgNote = new DgRepDeclarationNote(
									bntGNameOther,gNameOther);
							dgNote.setVisible(true);
							tfGNameOther.setText(dgNote.getNote());
						}
					});

		}
		return bntGNameOther;
	}

	/**
	 * This method initializes tfCodeTsOther
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCodeTsOther() {
		if (tfCodeTsOther == null) {
			tfCodeTsOther = new JTextField();
			tfCodeTsOther.setPreferredSize(new Dimension(125, 22));
			tfCodeTsOther.setSize(new Dimension(125, 22));
			tfCodeTsOther.setLocation(new Point(238, 197));
		}
		return tfCodeTsOther;
	}

	/**
	 * This method initializes cbIsClassDecision
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbIsClassDecision() {
		if (cbIsClassDecision == null) {
			cbIsClassDecision = new JComboBox();
			cbIsClassDecision.setLocation(new Point(238, 280));
			cbIsClassDecision.setPreferredSize(new Dimension(125, 22));
			cbIsClassDecision.setSize(new Dimension(125, 22));
			cbIsClassDecision
					.addItem(new ItemProperty(
							RepDeclarationType.IsClassDecisionN,
							RepDeclarationType
									.getIsClassDecision((RepDeclarationType.IsClassDecisionN))));
			cbIsClassDecision
					.addItem(new ItemProperty(
							RepDeclarationType.IsClassDecisionY,
							RepDeclarationType
									.getIsClassDecision((RepDeclarationType.IsClassDecisionY))));
		}
		return cbIsClassDecision;
	}

	/**
	 * This method initializes tfDecisionNO
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDecisionNO() {
		if (tfDecisionNO == null) {
			tfDecisionNO = new JTextField();
			tfDecisionNO.setSize(new Dimension(125, 22));
			tfDecisionNO.setLocation(new Point(240, 365));
		}
		return tfDecisionNO;
	}

	/**
	 * This method initializes tfCodeTsDecision
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCodeTsDecision() {
		if (tfCodeTsDecision == null) {
			tfCodeTsDecision = new JTextField();
			tfCodeTsDecision.setBounds(new Rectangle(552, 118, 125, 22));
		}
		return tfCodeTsDecision;
	}

	/**
	 * This method initializes cbIsSampleTest
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbIsSampleTest() {
		if (cbIsSampleTest == null) {
			cbIsSampleTest = new JComboBox();
			cbIsSampleTest.setLocation(new Point(552, 280));
			cbIsSampleTest.setSize(new Dimension(125, 22));
			cbIsSampleTest
					.addItem(new ItemProperty(
							RepDeclarationType.IsSampleTestN,
							RepDeclarationType
									.getIsSampleTest(RepDeclarationType.IsSampleTestN)));
			cbIsSampleTest
					.addItem(new ItemProperty(
							RepDeclarationType.IsSampleTestY,
							RepDeclarationType
									.getIsSampleTest(RepDeclarationType.IsSampleTestY)));
		}
		return cbIsSampleTest;
	}

	/**
	 * This method initializes pnOrigin
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnOrigin() {
		if (pnOrigin == null) {
			jLabel469 = new JLabel();
			jLabel469.setBounds(new Rectangle(426, 330, 124, 18));
			jLabel469.setForeground(Color.blue);
			jLabel469.setText("适用的原产地标准：");
			jLabel468 = new JLabel();
			jLabel468.setBounds(new Rectangle(426, 275, 124, 18));
			jLabel468.setText("原产地证书编号：");
			jLabel467 = new JLabel();
			jLabel467.setBounds(new Rectangle(426, 222, 124, 33));
			jLabel467
					.setText("<html><body>原产地证书签发机构<br>及所在国家（地区）：</body></html>");
			jLabel466 = new JLabel();
			jLabel466.setBounds(new Rectangle(426, 171, 140, 21));
			jLabel466.setText("原产国(地区)标记的位置：");
			jLabel465 = new JLabel();
			jLabel465.setBounds(new Rectangle(426, 100, 124, 34));
			jLabel465.setForeground(Color.blue);
			jLabel465.setText("原产国（地区）：");
			jLabel464 = new JLabel();
			jLabel464.setBounds(new Rectangle(117, 377, 124, 18));
			jLabel464.setForeground(Color.darkGray);
			jLabel464.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel464.setText("提单编号：");
			jLabel463 = new JLabel();
			jLabel463.setBounds(new Rectangle(117, 330, 124, 18));
			jLabel463.setForeground(Color.blue);
			jLabel463.setText("申报口岸：");
			jLabel462 = new JLabel();
			jLabel462.setBounds(new Rectangle(117, 275, 124, 18));
			jLabel462.setForeground(Color.blue);
			jLabel462.setText("到货口岸：");
			jLabel461 = new JLabel();
			jLabel461.setBounds(new Rectangle(117, 228, 124, 18));
			jLabel461.setText("中转国地区：");
			jLabel460 = new JLabel();
			jLabel460.setBounds(new Rectangle(117, 164, 124, 34));
			jLabel460.setForeground(Color.blue);
			jLabel460.setText("是否直接运输：");
			jLabel459 = new JLabel();
			jLabel459.setText("运输方式：");
			jLabel459.setSize(new Dimension(124, 37));
			jLabel459.setForeground(Color.blue);
			jLabel459.setLocation(new Point(117, 100));
			pnOrigin = new JPanel();
			pnOrigin.setLayout(null);
			pnOrigin.add(jLabel459, null);
			pnOrigin.add(getCbbTrafMode(), null);
			pnOrigin.add(jLabel460, null);
			pnOrigin.add(getCbIsDirectTraf(), null);
			pnOrigin.add(jLabel461, null);
			pnOrigin.add(getCbbTransitCountry(), null);
			pnOrigin.add(jLabel462, null);
			pnOrigin.add(getCbbDestPort(), null);
			pnOrigin.add(getCbbDeclPort(), null);
			pnOrigin.add(jLabel463, null);
			pnOrigin.add(getTfBillNo(), null);
			pnOrigin.add(jLabel464, null);
			pnOrigin.add(getCbbOriginCountry(), null);
			pnOrigin.add(jLabel465, null);
			pnOrigin.add(jLabel466, null);
			pnOrigin.add(getCbbCertCountry(), null);
			pnOrigin.add(jLabel467, null);
			pnOrigin.add(jLabel468, null);
			pnOrigin.add(getTfCertNO(), null);
			pnOrigin.add(jLabel469, null);
			pnOrigin.add(getCbCertStandard(), null);
			pnOrigin.add(getCbbOriginMark(), null);
		}
		return pnOrigin;
	}

	/**
	 * This method initializes cbIsDirectTraf
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbIsDirectTraf() {
		if (cbIsDirectTraf == null) {
			cbIsDirectTraf = new JComboBox();
			cbIsDirectTraf.setLocation(new Point(250, 169));
			cbIsDirectTraf.setSize(new Dimension(125, 22));
			cbIsDirectTraf
					.addItem(new ItemProperty(
							RepDeclarationType.IsDirectTrafN,
							RepDeclarationType
									.getIsDirectTraf((RepDeclarationType.IsDirectTrafN))));
			cbIsDirectTraf
					.addItem(new ItemProperty(
							RepDeclarationType.IsDirectTrafY,
							RepDeclarationType
									.getIsDirectTraf((RepDeclarationType.IsDirectTrafY))));
		}
		return cbIsDirectTraf;
	}

	/**
	 * This method initializes tfBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBillNo() {
		if (tfBillNo == null) {
			tfBillNo = new JTextField();
			tfBillNo.setBounds(new Rectangle(249, 374, 125, 22));
		}
		return tfBillNo;
	}

	/**
	 * This method initializes tfCertNO
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCertNO() {
		if (tfCertNO == null) {
			tfCertNO = new JTextField();
			tfCertNO.setBounds(new Rectangle(566, 275, 125, 22));
		}
		return tfCertNO;
	}

	/**
	 * This method initializes pnIndirectPayment
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnIndirectPayment() {
		if (pnIndirectPayment == null) {
			pnIndirectPayment = new JPanel();
			pnIndirectPayment.setLayout(null);
			pnIndirectPayment
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u95f4\u63a5\u652f\u4ed8/\u6536\u53d6\u7684\u8d27\u6b3e(\u8fdb\u53e3\u662f\u95f4\u63a5\u652f\u4ed8\uff0c\u51fa\u53e3\u662f\u95f4\u63a5\u6536\u53d6)",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.PLAIN, 12),
									new Color(51, 51, 51)));
			pnIndirectPayment.setSize(new Dimension(308, 146));
			pnIndirectPayment.setLocation(new Point(255, 17));
			pnIndirectPayment.add(jLabel411, null);
			pnIndirectPayment.add(jLabel412, null);
			pnIndirectPayment.add(jLabel413, null);
			pnIndirectPayment.add(getTfGoodsPrice(), null);
			pnIndirectPayment.add(getTfGoodsAmount(), null);
			pnIndirectPayment.add(getTfGoodsNote(), null);
			pnIndirectPayment.add(getBtnGoodsNote(), null);
		}
		return pnIndirectPayment;
	}

	/**
	 * This method initializes pnCommission
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnCommission() {
		if (pnCommission == null) {
			pnCommission = new JPanel();
			pnCommission.setLayout(null);
			pnCommission
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u8fdb\u53e3\u8d27\u7269\u9664\u8d2d\u8d27\u4f63\u91d1\u4ee5\u5916\u7684\u4f63\u91d1\u548c\u7ecf\u7eaa\u8d39",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.PLAIN, 12),
									new Color(51, 51, 51)));
			pnCommission.setSize(new Dimension(306, 147));
			pnCommission.setLocation(new Point(256, 172));
			pnCommission.add(jLabel414, null);
			pnCommission.add(jLabel415, null);
			pnCommission.add(jLabel416, null);
			pnCommission.add(getTfI_CommissionPrice(), null);
			pnCommission.add(getTfI_CommissionAmount(), null);
			pnCommission.add(getTfI_CommissionNote(), null);
			pnCommission.add(getBtI_CommissionNote(), null);
		}
		return pnCommission;
	}

	/**
	 * This method initializes pnContainer
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnContainer() {
		if (pnContainer == null) {
			pnContainer = new JPanel();
			pnContainer.setLayout(null);
			pnContainer.setBounds(new Rectangle(565, 170, 201, 141));
			pnContainer
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u4e0e\u8be5\u8fdb\u53e3\u8d27\u7269\u89c6\u4e3a\u4e00\u4f53\u7684\u5bb9\u5668\u8d39\u7528",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.PLAIN, 12),
									new Color(51, 51, 51)));
			pnContainer.add(getTfI_ContainerPrice(), null);
			pnContainer.add(jLabel417, null);
			pnContainer.add(getTfI_ContainerAmount(), null);
			pnContainer.add(jLabel418, null);
			pnContainer.add(jLabel419, null);
			pnContainer.add(getTfI_ContainerNote(), null);
			pnContainer.add(getBtnI_ContainerNote(), null);
		}
		return pnContainer;
	}

	/**
	 * This method initializes pnContainer2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnContainer2() {
		if (pnContainer2 == null) {
			pnContainer2 = new JPanel();
			pnContainer2.setLayout(null);
			pnContainer2
					.setBorder(BorderFactory.createTitledBorder(null, "\u8fdb\u53e3\u8d27\u7269\u5305\u88c5\u6750\u6599\u548c\u5305\u88c5\u52b3\u52a1\u8d39\u7528", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 12), new Color(51, 51, 51)));
			pnContainer2.setSize(new Dimension(194, 129));
			pnContainer2.setLocation(new Point(570, 17));
			pnContainer2.add(jLabel420, null);
			pnContainer2.add(getTfI_PackPrice(), null);
			pnContainer2.add(getTfI_PackAmount(), null);
			pnContainer2.add(jLabel421, null);
			pnContainer2.add(jLabel422, null);
			pnContainer2.add(getTfI_PackNote(), null);
			pnContainer2.add(getBtnI_PackNote(), null);
		}
		return pnContainer2;
	}

	/**
	 * This method initializes pnService
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnService() {
		if (pnService == null) {
			pnService = new JPanel();
			pnService.setLayout(null);
			pnService.setBounds(new Rectangle(259, 330, 300, 137));
			pnService
					.setBorder(BorderFactory.createTitledBorder(null, "\u8fdb\u53e3\u8d27\u7269\u5305\u542b\u7684\u6750\u6599\u3001\u90e8\u4ef6\u3001\u96f6\u4ef6\u548c\u7c7b\u4f3c\u8d27\u7269", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 12), new Color(51, 51, 51)));
			pnService.add(getTfI_PartPrice(), null);
			pnService.add(jLabel423, null);
			pnService.add(getTfI_PartAmount(), null);
			pnService.add(jLabel424, null);
			pnService.add(jLabel425, null);
			pnService.add(getTfI_PartNote(), null);
			pnService.add(getBtnI_PartNote(), null);
		}
		return pnService;
	}

	/**
	 * This method initializes pnInvoice
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnInvoice() {
		if (pnInvoice == null) {
			pnInvoice = new JPanel();
			pnInvoice.setLayout(null);
			pnInvoice.setBounds(new Rectangle(15, 336, 239, 130));
			pnInvoice.setBorder(BorderFactory.createTitledBorder(null,
					"\u53d1\u7968\u4ef7\u683c",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			pnInvoice.add(jLabel48, null);
			pnInvoice.add(jLabel49, null);
			pnInvoice.add(jLabel410, null);
			pnInvoice.add(getTfInvoiceAmount(), null);
			pnInvoice.add(getTfInvoiceNote(), null);
			pnInvoice.add(getTfInvoicePrice(), null);
		}
		return pnInvoice;
	}

	/**
	 * This method initializes pnPart
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnPart() {
		if (pnPart == null) {
			jLabel426 = new JLabel();
			jLabel426.setBounds(new Rectangle(23, 54, 61, 18));
			jLabel426.setText("总金额：");
			pnPart = new JPanel();
			pnPart.setLayout(null);
			pnPart.setBounds(new Rectangle(14, 18, 257, 119));
			pnPart
					.setBorder(BorderFactory.createTitledBorder(null, "\u751f\u4ea7\u8fdb\u53e3\u8d27\u7269\u8fc7\u7a0b\u4e2d\u4f7f\u7528\u7684\u5de5\u5177\u6a21\u5177\u548c\u7c7b\u4f3c\u8d27\u7269", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 12), new Color(51, 51, 51)));
			pnPart.add(jLabel427, null);
			pnPart.add(getTfI_ToolAmount(), null);
			pnPart.add(jLabel426, null);
			pnPart.add(getTfI_ToolPrice(), null);
			pnPart.add(getTfI_ToolNote(), null);
			pnPart.add(getBtnI_ToolNote(), null);
			pnPart.add(jLabel428, null);
		}
		return pnPart;
	}

	/**
	 * This method initializes tfI_ToolPrice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_ToolPrice() {
		if (tfI_ToolPrice == null) {
			tfI_ToolPrice = new JTextField();
			tfI_ToolPrice.setSize(new Dimension(125, 22));
			tfI_ToolPrice.setLocation(new Point(91, 19));
		}
		return tfI_ToolPrice;
	}

	/**
	 * This method initializes pnCmaterials
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnCmaterials() {
		if (pnCmaterials == null) {
			pnCmaterials = new JPanel();
			pnCmaterials.setLayout(null);
			pnCmaterials.setBounds(new Rectangle(14, 148, 257, 119));
			pnCmaterials
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u5728\u751f\u4ea7\u8fdb\u53e3\u8d27\u7269\u8fc7\u7a0b\u4e2d\u6d88\u8017\u7684\u6750\u6599",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.PLAIN, 12),
									new Color(51, 51, 51)));
			pnCmaterials.add(jLabel429, null);
			pnCmaterials.add(getTfI_LossPrice(), null);
			pnCmaterials.add(jLabel430, null);
			pnCmaterials.add(getTfI_LossAmount(), null);
			pnCmaterials.add(jLabel431, null);
			pnCmaterials.add(getTfI_LossNote(), null);
			pnCmaterials.add(getBtnI_LossNote(), null);
		}
		return pnCmaterials;
	}

	/**
	 * This method initializes pnEdesign
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnEdesign() {
		if (pnEdesign == null) {
			pnEdesign = new JPanel();
			pnEdesign.setLayout(null);
			pnEdesign.setBounds(new Rectangle(15, 278, 504, 91));
			pnEdesign
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u8fdb\u53e3\u8d27\u7269\u5728\u5883\u5916\u8fdb\u884c\u7684\u4e3a\u751f\u4ea7\u8fdb\u53e3\u8d27\u7269\u6240\u9700\u7684\u5de5\u7a0b\u8bbe\u8ba1\u3001\u6280\u672f\u7814\u53d1\u3001\u5de5\u827a\u53ca\u5236\u56fe\u7b49\u76f8\u5173\u670d\u52a1",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.PLAIN, 12),
									new Color(51, 51, 51)));
			pnEdesign.add(getTfI_DesignPrice(), null);
			pnEdesign.add(jLabel432, null);
			pnEdesign.add(jLabel433, null);
			pnEdesign.add(jLabel434, null);
			pnEdesign.add(getTfI_DesignNote(), null);
			pnEdesign.add(getBtnI_DesignNote(), null);
			pnEdesign.add(getTfI_DesignAmount(), null);
		}
		return pnEdesign;
	}

	/**
	 * This method initializes pnResale
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnResale() {
		if (pnResale == null) {
			pnResale = new JPanel();
			pnResale.setLayout(null);
			pnResale
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u5356\u65b9\u76f4\u63a5\u6216\u95f4\u63a5\u4ece\u4e70\u65b9\u5bf9\u8d27\u7269\u8fdb\u53e3\u540e\u8f6c\u552e\u3001\u5904\u7f6e\u6216\u4f7f\u7528\u6240\u5f97\u4e2d\u83b7\u5f97\u7684\u6536\u76ca",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.PLAIN, 12),
									new Color(51, 51, 51)));
			pnResale.setBounds(new Rectangle(15, 381, 504, 90));
			pnResale.add(jLabel438, null);
			pnResale.add(jLabel439, null);
			pnResale.add(getTfI_ProfitPrice(), null);
			pnResale.add(getTfI_ProfitAmount(), null);
			pnResale.add(jLabel440, null);
			pnResale.add(getTfI_ProfitNote(), null);
			pnResale.add(getBtnI_ProfitNote(), null);
		}
		return pnResale;
	}

	/**
	 * This method initializes pnFreight
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnFreight() {
		if (pnFreight == null) {
			pnFreight = new JPanel();
			pnFreight.setLayout(null);
			pnFreight.setBounds(new Rectangle(285, 18, 236, 119));
			pnFreight.setBorder(BorderFactory.createTitledBorder(null,
					"\u8fdb\u53e3\u8d27\u7269\u8fd0\u8f93\u8d39\u7528",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			pnFreight.add(jLabel441, null);
			pnFreight.add(getTfI_FeePrice(), null);
			pnFreight.add(jLabel442, null);
			pnFreight.add(getTfI_FeeAmount(), null);
			pnFreight.add(jLabel443, null);
			pnFreight.add(getTfI_FeeNote(), null);
			pnFreight.add(getBtnI_FeeNote(), null);
		}
		return pnFreight;
	}

	/**
	 * This method initializes pnRelateFreight
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnRelateFreight() {
		if (pnRelateFreight == null) {
			pnRelateFreight = new JPanel();
			pnRelateFreight.setLayout(null);
			pnRelateFreight.setBounds(new Rectangle(285, 148, 236, 119));
			pnRelateFreight
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u8fdb\u53e3\u8d27\u7269\u8fd0\u8f93\u76f8\u5173\u5176\u4ed6\u8d39\u7528",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.PLAIN, 12),
									new Color(51, 51, 51)));
			pnRelateFreight.add(jLabel444, null);
			pnRelateFreight.add(getTfI_OtherPrice(), null);
			pnRelateFreight.add(jLabel445, null);
			pnRelateFreight.add(getTfI_OtherAmount(), null);
			pnRelateFreight.add(jLabel446, null);
			pnRelateFreight.add(getTfI_OtherNote(), null);
			pnRelateFreight.add(getBtnI_OtherNote(), null);
		}
		return pnRelateFreight;
	}

	/**
	 * This method initializes pnConcession
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnConcession() {
		if (pnConcession == null) {
			pnConcession = new JPanel();
			pnConcession.setLayout(null);
			pnConcession.setBounds(new Rectangle(540, 18, 230, 119));
			pnConcession.setBorder(BorderFactory.createTitledBorder(null,
					"\u7279\u8bb8\u6743\u4f7f\u7528",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			pnConcession.add(jLabel435, null);
			pnConcession.add(getTfI_UsefeePrice(), null);
			pnConcession.add(jLabel436, null);
			pnConcession.add(getTfI_UsefeeAmount(), null);
			pnConcession.add(jLabel437, null);
			pnConcession.add(getTfI_UsefeeNote(), null);
			pnConcession.add(getBtnI_UsefeeNote(), null);
		}
		return pnConcession;
	}

	/**
	 * This method initializes pnInsurance
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnInsurance() {
		if (pnInsurance == null) {
			pnInsurance = new JPanel();
			pnInsurance.setLayout(null);
			pnInsurance.setBounds(new Rectangle(540, 148, 230, 119));
			pnInsurance.setBorder(BorderFactory.createTitledBorder(null,
					"\u8fdb\u53e3\u8d27\u7269\u4fdd\u9669\u8d39",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			pnInsurance.add(jLabel447, null);
			pnInsurance.add(getTfI_InsurPrice(), null);
			pnInsurance.add(jLabel448, null);
			pnInsurance.add(getTfI_InsurAmount(), null);
			pnInsurance.add(jLabel449, null);
			pnInsurance.add(getTfI_InsurNote(), null);
			pnInsurance.add(getBtnI_InsurNote(), null);
		}
		return pnInsurance;
	}

	/**
	 * This method initializes btnI_ProfitNote
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnI_ProfitNote() {
		if (btnI_ProfitNote == null) {
			btnI_ProfitNote = new JButton();
			btnI_ProfitNote.setText("...");
			btnI_ProfitNote.setSize(new Dimension(25, 22));
			btnI_ProfitNote.setLocation(new Point(187, 55));
			btnI_ProfitNote
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String profitNote = tfI_ProfitNote.getText();
							DgRepDeclarationNote dgNote = new DgRepDeclarationNote(
									btnI_ProfitNote,profitNote);
							dgNote.setVisible(true);
							tfI_ProfitNote.setText(dgNote.getNote());
						}
					});
		}
		return btnI_ProfitNote;
	}

	/**
	 * This method initializes tfGoptions
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfGoptions() {
		if (tfGoptions == null) {
			tfGoptions = new JTextField();
			tfGoptions.setBounds(new Rectangle(550, 363, 101, 22));
		}
		return tfGoptions;
	}

	/**
	 * This method initializes btnGoptions
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnGoptions() {
		if (btnGoptions == null) {
			btnGoptions = new JButton();
			btnGoptions.setBounds(new Rectangle(652, 363, 25, 22));
			btnGoptions.setText("...");
			btnGoptions.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DgGoptions dgGoptions;
					if(decSupplement!=null){
					String goptions = tfGoptions.getText();
					     dgGoptions = new DgGoptions(goptions);
					}else{
						 dgGoptions = new DgGoptions();
					}
					dgGoptions.setVisible(true);
					String lables = dgGoptions.getStrGoptions();
					if(lables!=""){
					tfGoptions.setText("");
					String[] strLables = lables.split(",");
					String strLables2 = "<html><body>";
					String strLables3 = "";
					for (int i = 0; i < strLables.length; i++) {
						strLables2 += strLables[i] + "<br>";
					}
					strLables2 += "</body></html>";
					tfGoptions.setToolTipText(strLables2);
					for (int i = 0; i < strLables.length; i++) {
						strLables3 += strLables[i].substring(0, 3) + ",";
					}
					tfGoptions.setText(strLables3);
					}
				}
			});
		}
		return btnGoptions;
	}

	/**
	 * This method initializes cbCertStandard
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbCertStandard() {
		if (cbCertStandard == null) {
			cbCertStandard = new JComboBox();
			cbCertStandard.setBounds(new Rectangle(566, 329, 125, 22));
			cbCertStandard
					.addItem(new ItemProperty(
							RepDeclarationType.CertStandardAll,
							RepDeclarationType
									.getCertStandard(RepDeclarationType.CertStandardAll)));
			cbCertStandard
					.addItem(new ItemProperty(
							RepDeclarationType.CertStandardT,
							RepDeclarationType
									.getCertStandard(RepDeclarationType.CertStandardT)));
			cbCertStandard
					.addItem(new ItemProperty(
							RepDeclarationType.CertStandardP,
							RepDeclarationType
									.getCertStandard(RepDeclarationType.CertStandardP)));
			cbCertStandard
					.addItem(new ItemProperty(
							RepDeclarationType.CertStandardPt,
							RepDeclarationType
									.getCertStandard(RepDeclarationType.CertStandardPt)));
			cbCertStandard
					.addItem(new ItemProperty(
							RepDeclarationType.CertStandardM,
							RepDeclarationType
									.getCertStandard(RepDeclarationType.CertStandardM)));
			cbCertStandard
					.addItem(new ItemProperty(
							RepDeclarationType.CertStandardO,
							RepDeclarationType
									.getCertStandard(RepDeclarationType.CertStandardO)));
		}
		return cbCertStandard;
	}

	/**
	 * This method initializes pnBuyer
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnBuyer() {
		if (pnBuyer == null) {
			pnBuyer = new JPanel();
			pnBuyer.setLayout(null);
			pnBuyer.setBounds(new Rectangle(11, 142, 227, 143));
			pnBuyer.setBorder(BorderFactory.createTitledBorder(null,
					"\u4e70\u65b9", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			pnBuyer.add(jLabel5, null);
			pnBuyer.add(jLabel29, null);
			pnBuyer.add(getBuyer(), null);
			pnBuyer.add(getBuyerContact(), null);
			pnBuyer.add(jLabel30, null);
			pnBuyer.add(getBuyerAddr(), null);
			pnBuyer.add(jLabel31, null);
			pnBuyer.add(getBuyerTel(), null);
		}
		return pnBuyer;
	}

	/**
	 * This method initializes pnSeller
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnSeller() {
		if (pnSeller == null) {
			pnSeller = new JPanel();
			pnSeller.setLayout(null);
			pnSeller.setBounds(new Rectangle(13, 301, 227, 143));
			pnSeller.setBorder(BorderFactory.createTitledBorder(null,
					"\u5356\u65b9", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			pnSeller.add(jLabel32, null);
			pnSeller.add(jLabel33, null);
			pnSeller.add(jLabel11, null);
			pnSeller.add(jLabel341, null);
			pnSeller.add(getSeller(), null);
			pnSeller.add(getSellerContact(), null);
			pnSeller.add(getSellerAddr(), null);
			pnSeller.add(getSellerTel(), null);
		}
		return pnSeller;
	}

	/**
	 * This method initializes pnFactory
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnFactory() {
		if (pnFactory == null) {
			pnFactory = new JPanel();
			pnFactory.setLayout(null);
			pnFactory.setBounds(new Rectangle(250, 70, 232, 143));
			pnFactory.setBorder(BorderFactory.createTitledBorder(null,
					"\u751f\u4ea7\u5382\u5546",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			pnFactory.add(jLabel342, null);
			pnFactory.add(jLabel14, null);
			pnFactory.add(jLabel343, null);
			pnFactory.add(jLabel16, null);
			pnFactory.add(getFactory(), null);
			pnFactory.add(getFactoryContact(), null);
			pnFactory.add(getFactoryAddr(), null);
			pnFactory.add(getFactoryTel(), null);
		}
		return pnFactory;
	}

	/**
	 * This method initializes pnRepSign
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnRepSign() {
		if (pnRepSign == null) {
			jLabel474 = new JLabel();
			jLabel474.setBounds(new Rectangle(251, 318, 112, 18));
			jLabel474.setText("数字签名信息：");
			jLabel473 = new JLabel();
			jLabel473.setBounds(new Rectangle(251, 270, 112, 18));
			jLabel473.setText("签名日期：");
			jLabel472 = new JLabel();
			jLabel472.setBounds(new Rectangle(251, 222, 146, 18));
			jLabel472.setText("报关单数据中心统一编号：");
			jLabel471 = new JLabel();
			jLabel471.setBounds(new Rectangle(251, 172, 96, 18));
			jLabel471.setText("操作员IC卡号：");
			jLabel470 = new JLabel();
			jLabel470.setBounds(new Rectangle(251, 120, 68, 18));
			jLabel470.setText("操作类型：");
			pnRepSign = new JPanel();
			pnRepSign.setVisible(false);
			pnRepSign.setLayout(null);
			pnRepSign.add(jLabel470, null);
			pnRepSign.add(jLabel471, null);
			pnRepSign.add(getCbOperType(), null);
			pnRepSign.add(getTfIcCardId(), null);
			pnRepSign.add(jLabel472, null);
			pnRepSign.add(getTfClientSeqNo(), null);
			pnRepSign.add(jLabel473, null);
			pnRepSign.add(getTfSignDate(), null);
			pnRepSign.add(jLabel474, null);
			pnRepSign.add(getTfSignInfo(), null);
		}
		return pnRepSign;
	}

	/**
	 * This method initializes cbOperType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbOperType() {
		if (cbOperType == null) {
			cbOperType = new JComboBox();
			cbOperType.setBounds(new Rectangle(405, 116, 160, 22));
			cbOperType.addItem(new ItemProperty(
					RepDeclarationType.OperTypeSUSPEND, RepDeclarationType
							.getOperType(RepDeclarationType.OperTypeSUSPEND)));
			cbOperType.addItem(new ItemProperty(
					RepDeclarationType.OperTypeREPORT, RepDeclarationType
							.getOperType(RepDeclarationType.OperTypeREPORT)));
			cbOperType.setSelectedItem(new ItemProperty(
					RepDeclarationType.OperTypeSUSPEND, RepDeclarationType
							.getOperType(RepDeclarationType.OperTypeSUSPEND)));
		}
		return cbOperType;
	}

	/**
	 * This method initializes tfIcCardId
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfIcCardId() {
		if (tfIcCardId == null) {
			tfIcCardId = new JTextField();
			tfIcCardId.setBounds(new Rectangle(405, 173, 160, 22));
		}
		return tfIcCardId;
	}

	/**
	 * This method initializes tfClientSeqNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfClientSeqNo() {
		if (tfClientSeqNo == null) {
			tfClientSeqNo = new JTextField();
			tfClientSeqNo.setBounds(new Rectangle(406, 221, 160, 22));
		}
		return tfClientSeqNo;
	}

	/**
	 * This method initializes tfSignDate
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCalendarComboBox getTfSignDate() {
		if (tfSignDate == null) {
			tfSignDate = new JCalendarComboBox();
			tfSignDate.setBounds(new Rectangle(405, 270, 160, 22));
		}
		return tfSignDate;
	}

	/**
	 * This method initializes btnBuySellRel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBuySellRel() {
		if (btnBuySellRel == null) {
			btnBuySellRel = new JButton();
			btnBuySellRel.setText("选择关系");
			btnBuySellRel.setSize(new Dimension(25, 22));
			btnBuySellRel.setLocation(new Point(224, 23));
			btnBuySellRel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							String bsr = tfI_BabRel.getText().trim();
							DgBuySellRelation dgbsr = new DgBuySellRelation(bsr);
							dgbsr.setVisible(true);
							String lables = dgbsr.getLables();
							if(lables!=""){
							tfI_BabRel.setText("");
							String[] strLables = lables.split(",");
							String strLables2 = "<html><body>";
							String strLables3 = "";
							for (int i = 0; i < strLables.length; i++) {
								strLables2 += strLables[i] + "<br>";
							}
							strLables2 += "</body></html>";
							tfI_BabRel.setToolTipText(strLables2);
							for (int i = 0; i < strLables.length; i++) {
								strLables3 += strLables[i].substring(0, 3)
										+ ",";
							}
							tfI_BabRel.setText(strLables3);
							}
						}
					});
		}
		return btnBuySellRel;
	}

	/**
	 * This method initializes tfI_BabRel
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfI_BabRel() {
		if (tfI_BabRel == null) {
			tfI_BabRel = new JTextField();
			tfI_BabRel.setEnabled(false);
			tfI_BabRel.setLocation(new Point(124, 23));
			tfI_BabRel.setPreferredSize(new Dimension(100, 22));
			tfI_BabRel.setSize(new Dimension(100, 22));
		}
		return tfI_BabRel;
	}

	/**
	 * 把界面组件信息通过实体保存到数据库中
	 * 
	 * @param saveType
	 */
	private void saveData(String saveType) {

		if (decSupplement == null) {
			decSupplement = new DecSupplementList();
		}
		decSupplement.setGno(tfGno.getText().trim());
		decSupplement.setSupType(((ItemProperty) supType.getSelectedItem())
				.getCode());
		decSupplement.setBrandCN(brandCN.getText().trim());
		decSupplement.setBrandEN(brandEN.getText().trim());
		decSupplement.setBuyer(Buyer.getText().trim());
		decSupplement.setBuyerContact(buyerContact.getText().trim());
		decSupplement.setBuyerAddr(buyerAddr.getText().trim());
		decSupplement.setBuyerTel(buyerTel.getText().trim());
		decSupplement.setSeller(seller.getText().trim());
		decSupplement.setSellerContact(sellerContact.getText().trim());
		decSupplement.setSellerAddr(sellerAddr.getText().trim());
		decSupplement.setSellerTel(sellerTel.getText().trim());
		decSupplement.setFactory(factory.getText().trim());
		decSupplement.setFactoryContact(factoryContact.getText().trim());
		decSupplement.setFactoryAddr(factoryAddr.getText().trim());
		decSupplement.setFactoryTel(factoryTel.getText().trim());
		decSupplement.setContrNo(contrNo.getText().trim());
		decSupplement.setContrDate(contrDate.getDate());
		decSupplement.setInvoiceDate(invoiceDate.getDate());
		decSupplement.setInvoiceNo(invoiceNo.getText().trim());
		decSupplement.setOtherNote(otherNote.getText().trim());
		decSupplement.setIsSecret(((ItemProperty) isSecret.getSelectedItem())
				.getCode());
		decSupplement.setAgentType(((ItemProperty)cbbAgentType.getSelectedItem()).getCode());
		decSupplement.setDeclAddr(declAddr.getText().trim());
		decSupplement.setDeclPost(declPost.getText().trim());
		decSupplement.setDeclTel(declTel.getText().trim());

		if(RepDeclarationType.SupTypePrice.equals(saveType)){
		decSupplement.setI_BabRel(RepDeclarationType
				.getI_BabRelToNum(tfI_BabRel.getText().trim()));
		decSupplement.setI_PriceEffect(((ItemProperty) cbI_PriceEffect
				.getSelectedItem()).getCode());
		decSupplement.setI_PriceClose(((ItemProperty) cbI_PriceClose
				.getSelectedItem()).getCode());
		decSupplement.setI_OtherLimited(((ItemProperty) cbI_OtherLimited
				.getSelectedItem()).getCode());
		decSupplement.setI_Note1(tfI_Note1.getText().trim());
		decSupplement.setInvoicePrice(tfInvoicePrice.getText().trim());
		decSupplement.setInvoiceAmount(tfInvoiceAmount.getText().trim());
		decSupplement.setInvoiceNote(tfInvoiceNote.getText().trim());
		decSupplement.setGoodsPrice(tfGoodsPrice.getText().trim());
		decSupplement.setGoodsAmount(tfGoodsAmount.getText().trim());
		decSupplement.setGoodsNote(tfGoodsNote.getText().trim());
		decSupplement
				.setI_CommissionPrice(tfI_CommissionPrice.getText().trim());
		decSupplement.setI_CommissionAmount(tfI_CommissionAmount.getText()
				.trim());
		decSupplement.setI_ContainerNote(tfI_ContainerNote.getText().trim());
		decSupplement.setI_ContainerPrice(tfI_ContainerPrice.getText().trim());
		decSupplement
				.setI_ContainerAmount(tfI_ContainerAmount.getText().trim());
		decSupplement.setI_ContainerNote(tfI_ContainerNote.getText().trim());
		decSupplement.setI_PackPrice(tfI_PackPrice.getText().trim());
		decSupplement.setI_PackAmount(tfI_PackAmount.getText().trim());
		decSupplement.setI_PackNote(tfI_PackNote.getText().trim());
		decSupplement.setI_PartPrice(tfI_PartPrice.getText().trim());
		decSupplement.setI_PartAmount(tfI_PartAmount.getText().trim());
		decSupplement.setI_PartNote(tfI_PartNote.getText().trim());
		decSupplement.setI_OtherEffect(((ItemProperty) cbI_OtherEffect
				.getSelectedItem()).getCode());
		decSupplement.setCurr((Curr)cbbCurr.getSelectedItem());
		decSupplement.setI_ToolAmount(tfI_ToolAmount.getText().trim());
		decSupplement.setI_ToolNote(tfI_ToolNote.getText().trim());
		decSupplement.setI_ToolPrice(tfI_ToolPrice.getText().trim());
		decSupplement.setI_LossAmount(tfI_LossAmount.getText().trim());
		decSupplement.setI_LossNote(tfI_LossNote.getText().trim());
		decSupplement.setI_LossPrice(tfI_LossPrice.getText().trim());
		decSupplement.setI_DesignAmount(tfI_DesignAmount.getText().trim());
		decSupplement.setI_DesignNote(tfI_DesignNote.getText().trim());
		decSupplement.setI_DesignPrice(tfI_DesignPrice.getText().trim());
		decSupplement.setI_ProfitAmount(tfI_ProfitAmount.getText().trim());
		decSupplement.setI_ProfitNote(tfI_ProfitNote.getText().trim());
		decSupplement.setI_ProfitPrice(tfI_ProfitPrice.getText().trim());
		decSupplement.setI_FeeAmount(tfI_FeeAmount.getText().trim());
		decSupplement.setI_FeeNote(tfI_FeeNote.getText().trim());
		decSupplement.setI_FeePrice(tfI_FeePrice.getText().trim());
		decSupplement.setI_OtherAmount(tfI_OtherAmount.getText().trim());
		decSupplement.setI_OtherNote(tfI_OtherNote.getText().trim());
		decSupplement.setI_OtherPrice(tfI_OtherPrice.getText().trim());
		decSupplement.setI_UsefeeAmount(tfI_UsefeeAmount.getText().trim());
		decSupplement.setI_UsefeeNote(tfI_UsefeeNote.getText().trim());
		decSupplement.setI_UsefeePrice(tfI_UsefeePrice.getText().trim());
		decSupplement.setI_InsurAmount(tfI_InsurAmount.getText().trim());
		decSupplement.setI_InsurNote(tfI_InsurNote.getText().trim());
		decSupplement.setI_InsurPrice(tfI_InsurPrice.getText().trim());
		decSupplement.setE_IsDutyDel(((ItemProperty)cbbE_IsDutyDel.getSelectedItem()).getCode());
		decSupplement.setI_IsUsefee(((ItemProperty)cbbI_IsUsefee.getSelectedItem()).getCode());
		decSupplement.setI_IsProfit(((ItemProperty)cbbI_IsProfit.getSelectedItem()).getCode());
		}
		if(RepDeclarationType.SupTypeMerger.equals(saveType)){
		decSupplement.setGnameOther(tfGNameOther.getText().trim());
		decSupplement.setCodeTsOther(tfCodeTsOther.getText().trim());
		decSupplement.setIsClassDecision(((ItemProperty) cbIsClassDecision
				.getSelectedItem()).getCode());
		decSupplement.setDecisionNo(tfDecisionNO.getText().trim());
		decSupplement.setCodeTsDecision(tfCodeTsDecision.getText().trim());
		decSupplement.setDecisionCus((Customs)(cbbDecisionCus.getSelectedItem()));
		decSupplement.setIsSampleTest(((ItemProperty) cbIsSampleTest
				.getSelectedItem()).getCode());
		decSupplement.setGoptions(RepDeclarationType
				.getGoptionsToNum(tfGoptions.getText().trim()));
		}
		if(RepDeclarationType.SupTypeOrigin.equals(saveType)){
		decSupplement.setOriginMark(((ItemProperty)cbbOriginMark.getSelectedItem()).getCode());
		decSupplement.setIsDirectTraf(((ItemProperty) cbIsDirectTraf
				.getSelectedItem()).getCode());
		decSupplement.setBillNo(tfBillNo.getText().trim());
		decSupplement.setCertNo(tfCertNO.getText().trim());
		decSupplement.setCertStandard(((ItemProperty) cbCertStandard.getSelectedItem()).getCode());
		decSupplement.setTrafMode((Transf)cbbTrafMode.getSelectedItem());
		decSupplement.setTransitCountry((Country)cbbTransitCountry.getSelectedItem());
		decSupplement.setDestPort((PreDock)cbbDestPort.getSelectedItem());
		decSupplement.setDeclPort((PreDock)cbbDestPort.getSelectedItem());
		decSupplement.setOriginCountry((Country)cbbTransitCountry.getSelectedItem());
		decSupplement.setCertCountry((Country)cbbTransitCountry.getSelectedItem());
		}

		decSupplement.setOperType(((ItemProperty) cbOperType.getSelectedItem()).getCode());
		decSupplement.setIcCardId(tfIcCardId.getText().trim());
		decSupplement.setClientSeqNo(tfClientSeqNo.getText().trim());
		decSupplement.setSignDate(tfSignDate.getDate());
		decSupplement.setSignDate(Calendar.getInstance().getTime());
		decSupplement.setSignInfo(tfSignInfo.getText().trim());
		decSupplement.setIsCheck(SupplmentType.CHECK_N.toString());
		decSupplement.setIsSend(SupplmentType.SEND_N.toString());
		Date inputDate = new Date();
		decSupplement.setInputDate(inputDate);
		decSupplement
				.setBaseCustomsDeclarationCommInfo(baseCustomsDeclarationCommInfo.getId());
		baseCustomsDeclaration.setSupplmentType(customsDeclarationsupplmentType);
		BaseCustomsDeclaration baration = null;
		baration = baseEncAction.saveCustomsDeclaration(new Request(CommonVars.getCurrUser()), baseCustomsDeclaration);
	    
		String decSupplementid=	baseEncAction.saveDecSupplementList(decSupplement);
		decSupplement.setId(decSupplementid); 
	}

	/**
	 * This method initializes tfSignInfo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSignInfo() {
		if (tfSignInfo == null) {
			tfSignInfo = new JTextField();
			tfSignInfo.setBounds(new Rectangle(405, 317, 160, 22));
		}
		return tfSignInfo;
	}

	/**
	 * 设置界面组件状态
	 * 
	 * @param edit
	 */
	private void isEditOrNot(Boolean edit, String edittype) {
		brandCN.setEditable(edit);
		brandEN.setEditable(edit);
		Buyer.setEditable(edit);
		buyerContact.setEditable(edit);
		buyerAddr.setEditable(edit);
		buyerTel.setEditable(edit);
		seller.setEditable(edit);
		sellerContact.setEditable(edit);
		sellerAddr.setEditable(edit);
		sellerTel.setEditable(edit);
		factory.setEditable(edit);
		factoryContact.setEditable(edit);
		factoryAddr.setEditable(edit);
		factoryTel.setEditable(edit);
		contrNo.setEditable(edit);
		contrDate.setEnabled(edit);
		invoiceDate.setEnabled(edit);
		invoiceNo.setEditable(edit);
		otherNote.setEditable(edit);
		isSecret.setEnabled(edit);
		cbbAgentType.setEnabled(edit);
		declAddr.setEditable(edit);
		declPost.setEditable(edit);
		declTel.setEditable(edit);
		cbOperType.setEnabled(edit);
		tfIcCardId.setEditable(edit);
		tfClientSeqNo.setEditable(edit);
		tfSignDate.setEnabled(edit);
		tfSignInfo.setEditable(edit);
		if (RepDeclarationType.SupTypePrice.equals(edittype)) {
			tfI_BabRel.setEditable(edit);
			cbI_PriceEffect.setEnabled(edit);
			cbI_PriceClose.setEnabled(edit);
			cbI_OtherLimited.setEnabled(edit);
			tfI_Note1.setEditable(edit);
			tfInvoicePrice.setEditable(edit);
			tfInvoiceAmount.setEditable(edit);
			tfInvoiceNote.setEditable(edit);
			tfGoodsPrice.setEditable(edit);
			tfGoodsAmount.setEditable(edit);
			tfGoodsNote.setEditable(edit);
			tfI_CommissionPrice.setEditable(edit);
			tfI_CommissionAmount.setEditable(edit);
			tfI_ContainerNote.setEditable(edit);
			tfI_ContainerPrice.setEditable(edit);
			tfI_ContainerAmount.setEditable(edit);
			tfI_ContainerNote.setEditable(edit);
			tfI_PackPrice.setEditable(edit);
			tfI_PackAmount.setEditable(edit);
			tfI_PackNote.setEditable(edit);
			tfI_PartPrice.setEditable(edit);
			tfI_PartAmount.setEditable(edit);
			tfI_PartNote.setEditable(edit);
			cbI_OtherEffect.setEnabled(edit);
			cbbCurr.setEnabled(edit);
			tfI_ToolAmount.setEditable(edit);
			tfI_ToolNote.setEditable(edit);
			tfI_ToolPrice.setEditable(edit);
			tfI_LossAmount.setEditable(edit);
			tfI_LossPrice.setEditable(edit);
			tfI_LossNote.setEditable(edit);
			tfI_DesignAmount.setEditable(edit);
			tfI_DesignNote.setEditable(edit);
			tfI_DesignPrice.setEditable(edit);
			tfI_ProfitAmount.setEditable(edit);
			tfI_ProfitNote.setEditable(edit);
			tfI_ProfitPrice.setEditable(edit);
			tfI_FeeAmount.setEditable(edit);
			tfI_FeeNote.setEditable(edit);
			tfI_FeePrice.setEditable(edit);
			tfI_OtherAmount.setEditable(edit);
			tfI_OtherNote.setEditable(edit);
			tfI_OtherPrice.setEditable(edit);
			tfI_UsefeeAmount.setEditable(edit);
			tfI_UsefeeNote.setEditable(edit);
			tfI_UsefeePrice.setEditable(edit);
			tfI_InsurAmount.setEditable(edit);
			tfI_InsurNote.setEditable(edit);
			tfI_InsurPrice.setEditable(edit);
			cbbE_IsDutyDel.setEnabled(edit);
			cbbI_IsUsefee.setEnabled(edit);
			cbbI_IsProfit.setEnabled(edit);
			tfI_CommissionNote.setEditable(edit);
			btnBuySellRel.setEnabled(edit);
			btnGoodsNote.setEnabled(edit);
			btI_CommissionNote.setEnabled(edit);
			btnI_ContainerNote.setEnabled(edit);
			btnI_PackNote.setEnabled(edit);
			btnI_PartNote.setEnabled(edit);
			btnI_ToolNote.setEnabled(edit);
			btnI_LossNote.setEnabled(edit);
			btnI_DesignNote.setEnabled(edit);
			btnI_ProfitNote.setEnabled(edit);
			btnI_FeeNote.setEnabled(edit);
			btnI_OtherNote.setEnabled(edit);
			btnI_UsefeeNote.setEnabled(edit);
			btnI_InsurNote.setEnabled(edit);
			btnGoodsNote.setEnabled(edit);
		} else if (RepDeclarationType.SupTypeMerger.equals(edittype)) {
			tfGNameOther.setEditable(edit);
			tfCodeTsOther.setEditable(edit);
			cbIsClassDecision.setEnabled(edit);
			tfDecisionNO.setEditable(edit);
			tfCodeTsDecision.setEditable(edit);
			cbbDecisionCus.setEnabled(edit);
			cbIsSampleTest.setEnabled(edit);
			tfGoptions.setEditable(edit);
			bntGNameOther.setEnabled(edit);
			btnGoptions.setEnabled(edit);
		} else if (RepDeclarationType.SupTypeOrigin.equals(edittype)) {
			cbIsDirectTraf.setEnabled(edit);
			cbbTransitCountry.setEnabled(edit);
			cbbDestPort.setEnabled(edit);
			cbbDeclPort.setEnabled(edit);
			tfBillNo.setEditable(edit);
			cbbOriginCountry.setEnabled(edit);
			cbbOriginMark.setEnabled(edit);
			cbbCertCountry.setEnabled(edit);
			tfCertNO.setEditable(edit);
			cbCertStandard.setEnabled(edit);
			cbbTrafMode.setEnabled(edit);
		}
	}

	/**
	 * 对按钮的状态设置及其它组件
	 * 
	 * @param state
	 */
	private void btnState(int state) {
		if (state == DataState.ADD) {
			btnEdit.setEnabled(false);
			btnPrint.setEnabled(false);
			btnSave.setEnabled(true);
			isEditOrNot(true, ((ItemProperty) supType.getSelectedItem())
					.getCode());
		} else if (state == DataState.EDIT) {
			btnEdit.setEnabled(false);
			btnPrint.setEnabled(false);
			btnSave.setEnabled(true);
			isEditOrNot(true, ((ItemProperty) supType.getSelectedItem())
					.getCode());
		} else if (state == DataState.BROWSE) {
			btnEdit.setEnabled(true);
			btnPrint.setEnabled(true);
			btnSave.setEnabled(false);
			isEditOrNot(false, ((ItemProperty) supType.getSelectedItem())
					.getCode());
		}
	}

	/**
	 * 根据选择补充报关类型对各个参数的检查
	 */
	private void checkParameter(String supplmentType) {
		errorInfo = "<html><body>";
		if (RepDeclarationType.SupTypePrice.equals(supplmentType)) {
			errorInfo += "价格申报:<br>";
			if (cbbCurr.getSelectedIndex() < 0) {
				errorInfo += "请完整填写币制<br>";
			}
			if (tfI_BabRel.getText().length() < 1) {
				errorInfo += "请完整填写买卖双方关系<br>";
			}
		} else if (RepDeclarationType.SupTypeMerger.equals(supplmentType)) {
			errorInfo += "归类申报:<br>";
			if ((RepDeclarationType.IsClassDecisionY)
					.equals(((ItemProperty) cbIsClassDecision.getSelectedItem())
							.getCode())) {
			}
		} else if (RepDeclarationType.SupTypeOrigin.equals(supplmentType)) {
			errorInfo += "原产地申报:<br>";
		}
		errorInfo += "</body></html>";
		if (errorInfo.equalsIgnoreCase("<html><body></body></html>")) {
			JOptionPane.showMessageDialog(DgReplenishDeclareCommonInfo.this,
					errorInfo, "提示", 0);
		}
	}

	/**
	 * This method initializes tfGno
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfGno() {
		if (tfGno == null) {
			tfGno = new JTextField();
			tfGno.setEditable(false);
			tfGno.setBounds(new Rectangle(291, 11, 84, 22));
		}
		return tfGno;
	}

	/**
	 * This method initializes tfcommSerialNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfcommSerialNo() {
		if (tfcommSerialNo == null) {
			tfcommSerialNo = new JTextField();
			tfcommSerialNo.setBounds(new Rectangle(73, 12, 87, 22));
			tfcommSerialNo.setEditable(false);
		}
		return tfcommSerialNo;
	}

	/**
	 * 把实体类信息显示到界面组件上
	 * 
	 * @param decSupplementList
	 */
	public void entityToui(DecSupplementList decSupplementList) {
		decSupplement = decSupplementList;
		decSupplement.setId(decSupplementList.getId());
		if (decSupplement.getGno() == null) {
			tfGno.setText("");
		} else {
			tfGno.setText(decSupplement.getGno());
		}
		if (decSupplement.getBrandCN() == null) {
			brandCN.setText("");
		} else {
			brandCN.setText(decSupplement.getBrandCN());
		}
		if (decSupplement.getBrandEN() == null) {
			brandEN.setText("");
		} else {
			brandEN.setText(decSupplement.getBrandEN());
		}
		if (decSupplement.getBuyer() == null) {
			Buyer.setText("");
		} else {
			Buyer.setText(decSupplement.getBuyer());
		}
		if (decSupplement.getBuyerContact() == null) {
			buyerContact.setText("");
		} else {
			buyerContact.setText(decSupplement.getBuyerContact());
		}
		if (decSupplement.getBuyerAddr() == null) {
			buyerAddr.setText("");
		} else {
			buyerAddr.setText(decSupplement.getBuyerAddr());
		}
		if (decSupplement.getBuyerTel() == null) {
			buyerTel.setText("");
		} else {
			buyerTel.setText(decSupplement.getBuyerTel());
		}
		if (decSupplement.getSeller() == null) {
			seller.setText("");
		} else {
			seller.setText(decSupplement.getSeller());
		}
		if (decSupplement.getSellerContact() == null) {
			sellerContact.setText("");
		} else {
			sellerContact.setText(decSupplement.getSellerContact());
		}
		if (decSupplement.getSellerAddr() == null) {
			sellerAddr.setText("");
		} else {
			sellerAddr.setText(decSupplement.getSellerAddr());
		}
		if (decSupplement.getSellerTel() == null) {
			sellerTel.setText("");
		} else {
			sellerTel.setText(decSupplement.getSellerTel());
		}
		if (decSupplement.getFactory() == null) {
			factory.setText("");
		} else {
			factory.setText(decSupplement.getFactory());
		}
		if (decSupplement.getFactory() == null) {
			factory.setText("");
		} else {
			factory.setText(decSupplement.getFactory());
		}
		if (decSupplement.getFactoryContact() == null) {
			factoryContact.setText("");
		} else {
			factoryContact.setText(decSupplement.getFactoryContact());
		}
		if (decSupplement.getFactoryAddr() == null) {
			factoryAddr.setText("");
		} else {
			factoryAddr.setText(decSupplement.getFactoryAddr());
		}
		if (decSupplement.getFactoryTel() == null) {
			factoryTel.setText("");
		} else {
			factoryTel.setText(decSupplement.getFactoryTel());
		}
		if (decSupplement.getContrNo() == null) {
			contrNo.setText("");
		} else {
			contrNo.setText(decSupplement.getContrNo());
		}
		if (decSupplement.getContrDate() == null) {
			contrDate.setDate(null);
		} else {
			contrDate.setDate(decSupplement.getContrDate());
		}
		if (decSupplement.getInvoiceDate() == null) {
			invoiceDate.setDate(null);
		} else {
			invoiceDate.setDate(decSupplement.getInvoiceDate());
		}
		if (decSupplement.getInvoiceNo() == null) {
			invoiceNo.setText("");
		} else {
			invoiceNo.setText(decSupplement.getInvoiceNo());
		}
		if (decSupplement.getOtherNote() == null) {
			otherNote.setText("");
		} else {
			otherNote.setText(decSupplement.getOtherNote());
		}
		if (decSupplement.getIsSecret() == null) {
			isSecret.setSelectedIndex(1);
		} else {
			isSecret.setSelectedItem(new ItemProperty(decSupplement
					.getIsSecret(), RepDeclarationType
					.getIsSecret(decSupplement.getIsSecret())));
		}
		if (decSupplement.getAgentType() == null) {
			cbbAgentType.setSelectedIndex(1);
		} else {
			cbbAgentType.setSelectedItem(new ItemProperty(decSupplement
					.getAgentType(),RepDeclarationType
					.getAgentType(Integer.valueOf(decSupplement.getAgentType()))));
		}
		if (decSupplement.getDeclAddr() == null) {
			declAddr.setText("");
		} else {
			declAddr.setText(decSupplement.getDeclAddr());
		}
		if (decSupplement.getDeclPost() == null) {
			declPost.setText("");
		} else {
			declPost.setText(decSupplement.getDeclPost());
		}
		if (decSupplement.getDeclTel() == null) {
			declTel.setText("");
		} else {
			declTel.setText(decSupplement.getDeclTel());
		}
		if (decSupplement.getI_BabRel() == null) {
			tfI_BabRel.setText("");
		} else {
			tfI_BabRel.setText(RepDeclarationType.getI_BabRel(decSupplement
					.getI_BabRel()));
		}
		if (decSupplement.getI_PriceEffect() == null) {
			cbI_PriceEffect.setSelectedIndex(1);
		} else {
			cbI_PriceEffect.setSelectedItem(new ItemProperty(decSupplement
					.getI_PriceEffect(), RepDeclarationType
					.getI_PriceEffect(decSupplement.getI_PriceEffect())));
		}
		if (decSupplement.getI_PriceClose() == null) {
			cbI_PriceClose.setSelectedIndex(1);
		} else {
			cbI_PriceClose.setSelectedItem(new ItemProperty(decSupplement
					.getI_PriceClose(), RepDeclarationType
					.getI_PriceClose(decSupplement.getI_PriceClose())));
		}
		if (decSupplement.getI_OtherLimited() == null) {
			cbI_OtherLimited.setSelectedIndex(1);
		} else {
			cbI_OtherLimited.setSelectedItem(new ItemProperty(decSupplement
					.getI_OtherLimited(), RepDeclarationType
					.getI_OtherLimited(decSupplement.getI_OtherLimited())));
		}
		if (decSupplement.getI_OtherLimited() == null) {
			cbI_OtherLimited.setSelectedIndex(1);
		} else {
			cbI_OtherLimited.setSelectedItem(new ItemProperty(decSupplement
					.getI_OtherLimited(), RepDeclarationType
					.getI_OtherLimited(decSupplement.getI_OtherLimited())));
		}
		if (decSupplement.getI_Note1() == null) {
			tfI_Note1.setText("");
		} else {
			tfI_Note1.setText(decSupplement.getI_Note1());
		}
		if (decSupplement.getInvoicePrice() == null) {
			tfInvoicePrice.setText("");
		} else {
			tfInvoicePrice.setText(decSupplement.getInvoicePrice());
		}
		if (decSupplement.getInvoiceAmount() == null) {
			tfInvoiceAmount.setText("");
		} else {
			tfInvoiceAmount.setText(decSupplement.getInvoiceAmount());
		}
		if (decSupplement.getInvoiceNote() == null) {
			tfInvoiceNote.setText("");
		} else {
			tfInvoiceNote.setText(decSupplement.getInvoiceNote());
		}
		if (decSupplement.getGoodsPrice() == null) {
			tfGoodsPrice.setText("");
		} else {
			tfGoodsPrice.setText(decSupplement.getGoodsPrice());
		}
		if (decSupplement.getGoodsAmount() == null) {
			tfGoodsAmount.setText("");
		} else {
			tfGoodsAmount.setText(decSupplement.getGoodsAmount());
		}
		if (decSupplement.getGoodsNote() == null) {
			tfGoodsNote.setText("");
		} else {
			tfGoodsNote.setText(decSupplement.getGoodsNote());
		}
		if (decSupplement.getI_CommissionPrice() == null) {
			tfI_CommissionPrice.setText("");
		} else {
			tfI_CommissionPrice.setText(decSupplement.getI_CommissionPrice());
		}
		if (decSupplement.getI_CommissionAmount() == null) {
			tfI_CommissionAmount.setText("");
		} else {
			tfI_CommissionAmount.setText(decSupplement.getI_CommissionAmount());
		}
		if (decSupplement.getI_ContainerNote() == null) {
			tfI_ContainerNote.setText("");
		} else {
			tfI_ContainerNote.setText(decSupplement.getI_ContainerNote());
		}
		if (decSupplement.getI_ContainerPrice() == null) {
			tfI_ContainerPrice.setText("");
		} else {
			tfI_ContainerPrice.setText(decSupplement.getI_ContainerPrice());
		}
		if (decSupplement.getI_ContainerAmount() == null) {
			tfI_ContainerAmount.setText("");
		} else {
			tfI_ContainerAmount.setText(decSupplement.getI_ContainerAmount());
		}
		if (decSupplement.getI_ContainerNote() == null) {
			tfI_ContainerNote.setText("");
		} else {
			tfI_ContainerNote.setText(decSupplement.getI_ContainerNote());
		}
		if (decSupplement.getI_PackPrice() == null) {
			tfI_PackPrice.setText("");
		} else {
			tfI_PackPrice.setText(decSupplement.getI_PackPrice());
		}
		if (decSupplement.getI_PackAmount() == null) {
			tfI_PackAmount.setText("");
		} else {
			tfI_PackAmount.setText(decSupplement.getI_PackAmount());
		}
		if (decSupplement.getI_PackAmount() == null) {
			tfI_PackAmount.setText("");
		} else {
			tfI_PackAmount.setText(decSupplement.getI_PackAmount());
		}
		if (decSupplement.getI_PackAmount() == null) {
			tfI_PackNote.setText("");
		} else {
			tfI_PackNote.setText(decSupplement.getI_PackAmount());
		}
		if (decSupplement.getI_PartPrice() == null) {
			tfI_PartPrice.setText("");
		} else {
			tfI_PartPrice.setText(decSupplement.getI_PartPrice());
		}
		if (decSupplement.getI_PartAmount() == null) {
			tfI_PartAmount.setText("");
		} else {
			tfI_PartAmount.setText(decSupplement.getI_PartAmount());
		}
		if (decSupplement.getI_PartNote() == null) {
			tfI_PartNote.setText("");
		} else {
			tfI_PartNote.setText(decSupplement.getI_PartNote());
		}
		if (decSupplement.getI_OtherEffect() == null) {
			cbI_OtherEffect.setSelectedIndex(0);
		} else {
			cbI_OtherEffect.setSelectedItem(new ItemProperty(decSupplement
					.getI_OtherEffect(), RepDeclarationType
					.getI_OtherEffect(decSupplement.getI_OtherEffect())));
		}
		if (decSupplement.getCurr() == null) {
			cbbCurr.setSelectedIndex(1);
		} else {
			cbbCurr.setSelectedItem(decSupplement.getCurr());
		}
		if (decSupplement.getI_ToolAmount() == null) {
			tfI_ToolAmount.setText("");
		} else {
			tfI_ToolAmount.setText(decSupplement.getI_ToolAmount());
		}
		if (decSupplement.getI_ToolNote() == null) {
			tfI_ToolNote.setText("");
		} else {
			tfI_ToolNote.setText(decSupplement.getI_ToolNote());
		}
		if (decSupplement.getI_ToolPrice() == null) {
			tfI_ToolPrice.setText("");
		} else {
			tfI_ToolPrice.setText(decSupplement.getI_ToolPrice());
		}
		if (decSupplement.getI_LossAmount() == null) {
			tfI_LossAmount.setText("");
		} else {
			tfI_LossAmount.setText(decSupplement.getI_LossAmount());
		}
		if (decSupplement.getI_LossNote() == null) {
			tfI_LossNote.setText("");
		} else {
			tfI_LossNote.setText(decSupplement.getI_LossNote());
		}
		if (decSupplement.getI_LossPrice() == null) {
			tfI_LossPrice.setText("");
		} else {
			tfI_LossPrice.setText(decSupplement.getI_LossPrice());
		}
		if (decSupplement.getI_DesignAmount() == null) {
			tfI_DesignAmount.setText("");
		} else {
			tfI_DesignAmount.setText(decSupplement.getI_DesignAmount());
		}
		if (decSupplement.getI_DesignNote() == null) {
			tfI_DesignNote.setText("");
		} else {
			tfI_DesignNote.setText(decSupplement.getI_DesignNote());
		}
		if (decSupplement.getI_DesignNote() == null) {
			tfI_DesignNote.setText("");
		} else {
			tfI_DesignNote.setText(decSupplement.getI_DesignNote());
		}
		if (decSupplement.getI_DesignPrice() == null) {
			tfI_DesignPrice.setText("");
		} else {
			tfI_DesignPrice.setText(decSupplement.getI_DesignPrice());
		}
		if (decSupplement.getI_ProfitAmount() == null) {
			tfI_ProfitAmount.setText("");
		} else {
			tfI_ProfitAmount.setText(decSupplement.getI_ProfitAmount());
		}
		if (decSupplement.getI_ProfitNote() == null) {
			tfI_ProfitNote.setText("");
		} else {
			tfI_ProfitNote.setText(decSupplement.getI_ProfitNote());
		}
		if (decSupplement.getI_ProfitPrice() == null) {
			tfI_ProfitPrice.setText("");
		} else {
			tfI_ProfitPrice.setText(decSupplement.getI_ProfitPrice());
		}
		if (decSupplement.getI_FeeAmount() == null) {
			tfI_FeeAmount.setText("");
		} else {
			tfI_FeeAmount.setText(decSupplement.getI_FeeAmount());
		}
		if (decSupplement.getI_FeeNote() == null) {
			tfI_FeeNote.setText("");
		} else {
			tfI_FeeNote.setText(decSupplement.getI_FeeNote());
		}
		if (decSupplement.getI_FeePrice() == null) {
			tfI_FeePrice.setText("");
		} else {
			tfI_FeePrice.setText(decSupplement.getI_FeePrice());
		}
		if (decSupplement.getI_OtherAmount() == null) {
			tfI_OtherAmount.setText("");
		} else {
			tfI_OtherAmount.setText(decSupplement.getI_OtherAmount());
		}
		if (decSupplement.getI_OtherNote() == null) {
			tfI_OtherNote.setText("");
		} else {
			tfI_OtherNote.setText(decSupplement.getI_OtherNote());
		}
		if (decSupplement.getI_OtherPrice() == null) {
			tfI_OtherPrice.setText("");
		} else {
			tfI_OtherPrice.setText(decSupplement.getI_OtherPrice());
		}
		if (decSupplement.getI_UsefeeAmount() == null) {
			tfI_UsefeeAmount.setText("");
		} else {
			tfI_UsefeeAmount.setText(decSupplement.getI_UsefeeAmount());
		}
		if (decSupplement.getI_UsefeeNote() == null) {
			tfI_UsefeeNote.setText("");
		} else {
			tfI_UsefeeNote.setText(decSupplement.getI_UsefeeNote());
		}
		if (decSupplement.getI_UsefeePrice() == null) {
			tfI_UsefeePrice.setText("");
		} else {
			tfI_UsefeePrice.setText(decSupplement.getI_UsefeePrice());
		}
		if (decSupplement.getI_InsurAmount() == null) {
			tfI_InsurAmount.setText("");
		} else {
			tfI_InsurAmount.setText(decSupplement.getI_InsurAmount());
		}
		if (decSupplement.getI_InsurNote() == null) {
			tfI_InsurNote.setText("");
		} else {
			tfI_InsurNote.setText(decSupplement.getI_InsurNote());
		}
		if (decSupplement.getI_InsurPrice() == null) {
			tfI_InsurPrice.setText("");
		} else {
			tfI_InsurPrice.setText(decSupplement.getI_InsurPrice());
		}
		if (decSupplement.getE_IsDutyDel() == null) {
			cbbE_IsDutyDel.setSelectedIndex(0);
		} else {
			cbbE_IsDutyDel.setSelectedItem(new ItemProperty(decSupplement
					.getE_IsDutyDel(), RepDeclarationType
					.getE_IsDutyDel(decSupplement.getE_IsDutyDel())));
		}
		if (decSupplement.getI_IsUsefee() == null) {
			cbbI_IsUsefee.setSelectedIndex(0);
		} else {
			cbbI_IsUsefee.setSelectedItem(new ItemProperty(decSupplement
					.getI_IsUsefee(), RepDeclarationType
					.getI_IsUsefee(decSupplement.getI_IsUsefee())));
		}
		if (decSupplement.getI_IsProfit() == null) {
			cbbI_IsProfit.setSelectedIndex(0);
		} else {
			cbbI_IsProfit.setSelectedItem(new ItemProperty(decSupplement
					.getI_IsProfit(), RepDeclarationType
					.getE_IsDutyDel(decSupplement.getI_IsProfit())));
		}
		if (decSupplement.getGnameOther() == null) {
			tfGNameOther.setText("");
		} else {
			tfGNameOther.setText(decSupplement.getGnameOther());
		}
		if (decSupplement.getCodeTsOther() == null) {
			tfCodeTsOther.setText("");
		} else {
			tfCodeTsOther.setText(decSupplement.getCodeTsOther());
		}
		if (decSupplement.getIsClassDecision() == null) {
			cbIsClassDecision.setSelectedIndex(1);
		} else {
			cbIsClassDecision.setSelectedItem(new ItemProperty(decSupplement
					.getIsClassDecision(), RepDeclarationType
					.getIsClassDecision(decSupplement.getIsClassDecision())));
		}
		if (decSupplement.getDecisionNo() == null) {
			tfDecisionNO.setText("");
		} else {
			tfDecisionNO.setText(decSupplement.getDecisionNo());
		}
		if (decSupplement.getCodeTsDecision() == null) {
			tfCodeTsDecision.setText("");
		} else {
			tfCodeTsDecision.setText(decSupplement.getCodeTsDecision());
		}
		if (decSupplement.getDecisionCus() == null) {
			cbbDecisionCus.setSelectedIndex(0);
		} else {
			 cbbDecisionCus.setSelectedItem(decSupplement.getDecisionCus());
		}
		if (decSupplement.getGoptions() == null) {
			tfGoptions.setText("");
		} else {
			tfGoptions.setText(RepDeclarationType.getGoptions(decSupplement
					.getGoptions()));
		}
		if (decSupplement.getIsSampleTest() == null) {
			cbIsSampleTest.setSelectedIndex(1);
		} else {
			cbIsSampleTest.setSelectedItem(new ItemProperty(decSupplement
					.getIsSampleTest(), RepDeclarationType
					.getIsSampleTest(decSupplement.getIsSampleTest())));
		}
		if (decSupplement.getTrafMode() == null) {
			cbbTrafMode.setSelectedIndex(0);
		} else {
			cbbTrafMode.setSelectedItem(decSupplement.getTrafMode());
		}
		if (decSupplement.getIsDirectTraf() == null) {
			cbIsDirectTraf.setSelectedIndex(1);
		} else {
			cbIsDirectTraf.setSelectedItem(new ItemProperty(decSupplement
					.getIsDirectTraf(), RepDeclarationType
					.getIsDirectTraf(decSupplement.getIsDirectTraf())));
		}
		if (decSupplement.getTransitCountry() == null) {
			cbbTransitCountry.setSelectedIndex(0);
		} else {
			cbbTransitCountry.setSelectedItem(decSupplement.getTransitCountry());
		}
		if (decSupplement.getDestPort() == null) {
			cbbDestPort.setSelectedIndex(0);
		} else {
			cbbDestPort.setSelectedItem(decSupplement.getDestPort());
		}
		if (decSupplement.getDeclPort() == null) {
			cbbDeclPort.setSelectedIndex(0);
		} else {
			cbbDeclPort.setSelectedItem(decSupplement.getDeclPort());
		}
		if (decSupplement.getBillNo() == null) {
			tfBillNo.setText("");
		} else {
			tfBillNo.setText(decSupplement.getBillNo());
		}

		if (decSupplement.getOriginCountry() == null) {
			cbbOriginCountry.setSelectedIndex(0);
		} else {
			cbbOriginCountry.setSelectedItem(decSupplement.getOriginCountry());
		}
		if (decSupplement.getOriginMark() == null) {
			cbbOriginMark.setSelectedIndex(0);
		} else {
			cbbOriginMark.setSelectedItem(new ItemProperty(decSupplement
					.getOriginMark(), RepDeclarationType
					.getOriginMark(decSupplement.getOriginMark())));
			
		}
		if (decSupplement.getCertCountry() == null) {
			cbbCertCountry.setSelectedIndex(0);
		} else {
			cbbCertCountry.setSelectedItem(decSupplement.getCertCountry());
		}
		if (decSupplement.getCertNo() == null) {
			tfCertNO.setText("");
		} else {
			tfCertNO.setText(decSupplement.getCertNo());
		}

		if (decSupplement.getCertStandard() == null) {
			cbCertStandard.setSelectedIndex(1);
		} else {
			cbCertStandard.setSelectedItem(new ItemProperty(decSupplement
					.getCertStandard(), RepDeclarationType
					.getCertStandard(decSupplement.getCertStandard())));
		}
		if (decSupplement.getOperType() == null) {
			cbOperType.setSelectedIndex(1);
		} else {
			cbOperType.setSelectedItem(new ItemProperty(decSupplement
					.getOperType(), RepDeclarationType
					.getOperType(decSupplement.getOperType())));
		}
		if (decSupplement.getIcCardId() == null) {
			tfIcCardId.setText("");
		} else {
			tfIcCardId.setText(decSupplement.getIcCardId());
		}
		if (decSupplement.getClientSeqNo() == null) {
			tfClientSeqNo.setText("");
		} else {
			tfClientSeqNo.setText(decSupplement.getClientSeqNo());
		}
		if (decSupplement.getSignDate() == null) {
			tfSignDate.setDate(null);
		} else {
			tfSignDate.setDate(decSupplement.getSignDate());
		}
		if (decSupplement.getSignInfo() == null) {
			tfSignInfo.setText("");
		} else {
			tfSignInfo.setText(decSupplement.getSignInfo());
		}

	}
	
	
	/**
	 * 把实体类公共信息显示到界面组件上
	 * 
	 * @param decSupplementList
	 */
	public void entityCommonInfo(DecSupplementList decSupplementList) {
		/*
		 * 公共信息模块，当第一次增加报关单的一个商品序号后，
		 * 公共信息面版栏位值已填写，要求在增加同一份报关单中的其它商品信息时。
		 * 公共信息面版栏位值自动带第一个商品信息的公共信息值。
		 */
		if (decSupplement.getBrandCN() == null) {
			brandCN.setText("");
		} else {
			brandCN.setText(decSupplement.getBrandCN());
		}
		if (decSupplement.getBrandEN() == null) {
			brandEN.setText("");
		} else {
			brandEN.setText(decSupplement.getBrandEN());
		}
		if (decSupplement.getBuyer() == null) {
			Buyer.setText("");
		} else {
			Buyer.setText(decSupplement.getBuyer());
		}
		if (decSupplement.getBuyerContact() == null) {
			buyerContact.setText("");
		} else {
			buyerContact.setText(decSupplement.getBuyerContact());
		}
		if (decSupplement.getBuyerAddr() == null) {
			buyerAddr.setText("");
		} else {
			buyerAddr.setText(decSupplement.getBuyerAddr());
		}
		if (decSupplement.getBuyerTel() == null) {
			buyerTel.setText("");
		} else {
			buyerTel.setText(decSupplement.getBuyerTel());
		}
		if (decSupplement.getSeller() == null) {
			seller.setText("");
		} else {
			seller.setText(decSupplement.getSeller());
		}
		if (decSupplement.getSellerContact() == null) {
			sellerContact.setText("");
		} else {
			sellerContact.setText(decSupplement.getSellerContact());
		}
		if (decSupplement.getSellerAddr() == null) {
			sellerAddr.setText("");
		} else {
			sellerAddr.setText(decSupplement.getSellerAddr());
		}
		if (decSupplement.getSellerTel() == null) {
			sellerTel.setText("");
		} else {
			sellerTel.setText(decSupplement.getSellerTel());
		}
		if (decSupplement.getFactory() == null) {
			factory.setText("");
		} else {
			factory.setText(decSupplement.getFactory());
		}
		if (decSupplement.getFactory() == null) {
			factory.setText("");
		} else {
			factory.setText(decSupplement.getFactory());
		}
		if (decSupplement.getFactoryContact() == null) {
			factoryContact.setText("");
		} else {
			factoryContact.setText(decSupplement.getFactoryContact());
		}
		if (decSupplement.getFactoryAddr() == null) {
			factoryAddr.setText("");
		} else {
			factoryAddr.setText(decSupplement.getFactoryAddr());
		}
		if (decSupplement.getFactoryTel() == null) {
			factoryTel.setText("");
		} else {
			factoryTel.setText(decSupplement.getFactoryTel());
		}
		if (decSupplement.getContrNo() == null) {
			contrNo.setText("");
		} else {
			contrNo.setText(decSupplement.getContrNo());
		}
		if (decSupplement.getContrDate() == null) {
			contrDate.setDate(null);
		} else {
			contrDate.setDate(decSupplement.getContrDate());
		}
		if (decSupplement.getInvoiceDate() == null) {
			invoiceDate.setDate(null);
		} else {
			invoiceDate.setDate(decSupplement.getInvoiceDate());
		}
		if (decSupplement.getInvoiceNo() == null) {
			invoiceNo.setText("");
		} else {
			invoiceNo.setText(decSupplement.getInvoiceNo());
		}
		if (decSupplement.getOtherNote() == null) {
			otherNote.setText("");
		} else {
			otherNote.setText(decSupplement.getOtherNote());
		}
		if (decSupplement.getIsSecret() == null) {
			isSecret.setSelectedIndex(1);
		} else {
			isSecret.setSelectedItem(new ItemProperty(decSupplement
					.getIsSecret(), RepDeclarationType
					.getIsSecret(decSupplement.getIsSecret())));
		}
		if (decSupplement.getAgentType() == null) {
			cbbAgentType.setSelectedIndex(1);
		} else {
			cbbAgentType.setSelectedItem(new ItemProperty(decSupplement
					.getAgentType(),RepDeclarationType
					.getAgentType(Integer.valueOf(decSupplement.getAgentType()))));
		}
		if (decSupplement.getDeclAddr() == null) {
			declAddr.setText("");
		} else {
			declAddr.setText(decSupplement.getDeclAddr());
		}
		if (decSupplement.getDeclPost() == null) {
			declPost.setText("");
		} else {
			declPost.setText(decSupplement.getDeclPost());
		}
		if (decSupplement.getDeclTel() == null) {
			declTel.setText("");
		} else {
			declTel.setText(decSupplement.getDeclTel());
		}

		if (decSupplement.getOperType() == null) {
			cbOperType.setSelectedIndex(1);
		} else {
			cbOperType.setSelectedItem(new ItemProperty(decSupplement
					.getOperType(), RepDeclarationType
					.getOperType(decSupplement.getOperType())));
		}
		if (decSupplement.getIcCardId() == null) {
			tfIcCardId.setText("");
		} else {
			tfIcCardId.setText(decSupplement.getIcCardId());
		}
		if (decSupplement.getClientSeqNo() == null) {
			tfClientSeqNo.setText("");
		} else {
			tfClientSeqNo.setText(decSupplement.getClientSeqNo());
		}
		if (decSupplement.getSignDate() == null) {
			tfSignDate.setDate(null);
		} else {
			tfSignDate.setDate(decSupplement.getSignDate());
		}
		if (decSupplement.getSignInfo() == null) {
			tfSignInfo.setText("");
		} else {
			tfSignInfo.setText(decSupplement.getSignInfo());
		}

	}

	/**
	 * This method initializes btI_CommissionNote
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtI_CommissionNote() {
		if (btI_CommissionNote == null) {
			btI_CommissionNote = new JButton();
			btI_CommissionNote.setBounds(new Rectangle(228, 106, 26, 21));
			btI_CommissionNote.setText("...");
			btI_CommissionNote.setName("I_CommissionNote");
			btI_CommissionNote
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String commissionNote = tfI_CommissionNote.getText();
							DgRepDeclarationNote dgNote = new DgRepDeclarationNote(
									btI_CommissionNote,commissionNote);
							dgNote.setVisible(true);
							tfI_CommissionNote.setText(dgNote.getNote());
						}
					});
		}
		return btI_CommissionNote;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This method initializes cbDecisionCus
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbDecisionCus() {
		if (cbbDecisionCus == null) {
			cbbDecisionCus = new JComboBox();
			cbbDecisionCus.setBounds(new Rectangle(554, 196, 125, 22));
			cbbDecisionCus.setModel(CustomBaseModel.getInstance()
					.getCustomModel());
			cbbDecisionCus.setRenderer(CustomBaseRender.getInstance()
					.getRender());
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					cbbDecisionCus);
		}
		return cbbDecisionCus;
	}

	/**
	 * This method initializes cbbTrafMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTrafMode() {
		if (cbbTrafMode == null) {
			cbbTrafMode = new JComboBox();
			cbbTrafMode.setBounds(new Rectangle(250, 104, 125, 22));

			cbbTrafMode
					.setModel(CustomBaseModel.getInstance().getTransfModel());
			cbbTrafMode.setRenderer(CustomBaseRender.getInstance().getRender());
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					cbbTrafMode);
		}
		return cbbTrafMode;
	}

	/**
	 * This method initializes cbbOriginCountry
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbOriginCountry() {
		if (cbbOriginCountry == null) {
			cbbOriginCountry = new JComboBox();
			cbbOriginCountry.setBounds(new Rectangle(566, 103, 125, 22));

			cbbOriginCountry.setModel(CustomBaseModel.getInstance()
					.getCountryModel());
			cbbOriginCountry.setRenderer(CustomBaseRender.getInstance()
					.getRender());
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					cbbOriginCountry);
		}
		return cbbOriginCountry;
	}

	/**
	 * This method initializes cbbTransitCountry
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTransitCountry() {
		if (cbbTransitCountry == null) {
			cbbTransitCountry = new JComboBox();
			cbbTransitCountry.setBounds(new Rectangle(250, 224, 125, 22));

			cbbTransitCountry.setModel(CustomBaseModel.getInstance()
					.getCountryModel());
			cbbTransitCountry.setRenderer(CustomBaseRender.getInstance()
					.getRender());
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					cbbTransitCountry);
		}
		return cbbTransitCountry;
	}

	/**
	 * This method initializes cbbCertCountry
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCertCountry() {
		if (cbbCertCountry == null) {
			cbbCertCountry = new JComboBox();
			cbbCertCountry.setBounds(new Rectangle(566, 225, 125, 22));

			cbbCertCountry.setModel(CustomBaseModel.getInstance()
					.getCountryModel());
			cbbCertCountry.setRenderer(CustomBaseRender.getInstance()
					.getRender());
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					cbbCertCountry);
		}
		return cbbCertCountry;
	}

	/**
	 * This method initializes cbbDestPort
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDestPort() {
		if (cbbDestPort == null) {
			cbbDestPort = new JComboBox();
			cbbDestPort.setBounds(new Rectangle(250, 275, 125, 22));

			cbbDestPort
					.setModel(CustomBaseModel.getInstance().getPreDockModel());
			cbbDestPort.setRenderer(CustomBaseRender.getInstance().getRender());
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					cbbDestPort);

		}
		return cbbDestPort;
	}

	/**
	 * This method initializes cbbDeclPort
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDeclPort() {
		if (cbbDeclPort == null) {
			cbbDeclPort = new JComboBox();
			cbbDeclPort.setBounds(new Rectangle(250, 325, 125, 22));

			cbbDeclPort
					.setModel(CustomBaseModel.getInstance().getPreDockModel());
			cbbDeclPort.setRenderer(CustomBaseRender.getInstance().getRender());
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					cbbDeclPort);
		}
		return cbbDeclPort;
	}

	/**
	 * This method initializes cbbAgentType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbAgentType() {
		if (cbbAgentType == null) {
			cbbAgentType = new JComboBox();
			cbbAgentType.setBounds(new Rectangle(620, 138, 134, 22));
			cbbAgentType
			.addItem(new ItemProperty(
					RepDeclarationType.AgentType_PerSon.toString(),
					RepDeclarationType
							.getAgentType(RepDeclarationType.AgentType_PerSon)));
			cbbAgentType
			.addItem(new ItemProperty(
					RepDeclarationType.AgentType_Factory.toString(),
					RepDeclarationType
							.getAgentType(RepDeclarationType.AgentType_Factory)));
		}
		return cbbAgentType;
	}

	/**
	 * This method initializes cbbCurr	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbCurr() {
		if (cbbCurr == null) {
			cbbCurr = new JComboBox();
			cbbCurr.setBounds(new Rectangle(633, 416, 93, 22));
			this.cbbCurr.setModel(CustomBaseModel.getInstance().getCurrModel());
			this.cbbCurr.setRenderer(CustomBaseRender.getInstance().getRender());
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					this.cbbCurr);
		}
		return cbbCurr;
	}
	
	/**
	 * This method initializes pmPrint
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmPrint() {
		if(pmPrint == null) {
			pmPrint = new JPopupMenu();
			pmPrint
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			pmPrint.add(getMiOverprintPrintPriceReplenish());
			Separator separator0 = new Separator();
			separator0.setForeground(Color.gray);
			pmPrint.add(getMiNotOverprintPrintPriceReplenish());
			Separator separator1 = new Separator();
			separator1.setForeground(Color.gray);
			pmPrint.add(separator1);
			pmPrint.add(getMiOverprintPrintOriginReplenish());
			Separator separator2 = new Separator();
			separator2.setForeground(Color.GRAY);
			pmPrint.add(separator2);
			pmPrint.add(getMiNotOverprintOriginReplenish());
			Separator separator3 = new Separator();
			separator3.setForeground(Color.GRAY);
			pmPrint.add(separator3);
			pmPrint.add(getMiOverprintPrintMergerReplenish());
			Separator separator4 = new Separator();
			separator4.setForeground(Color.GRAY);
			pmPrint.add(separator4);
			pmPrint.add(getMiNotOverprintPrintMergerReplenish());
		}
		return pmPrint;
	}

	/**
	 * This method initializes cbbE_IsDutyDel	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbE_IsDutyDel() {
		if (cbbE_IsDutyDel == null) {
			cbbE_IsDutyDel = new JComboBox();
			cbbE_IsDutyDel.setBounds(new Rectangle(654, 286, 114, 21));
			cbbE_IsDutyDel
			.addItem(new ItemProperty(
					RepDeclarationType.E_IsDutyDelN,
					RepDeclarationType
							.getE_IsDutyDel(RepDeclarationType.E_IsDutyDelN)));
			cbbE_IsDutyDel
			.addItem(new ItemProperty(
					RepDeclarationType.E_IsDutyDelY,
					RepDeclarationType
							.getE_IsDutyDel(RepDeclarationType.E_IsDutyDelY)));

		}
		return cbbE_IsDutyDel;
	}

	public int getCustomsDeclarationsupplmentType() {
		return customsDeclarationsupplmentType;
	}

	public void setCustomsDeclarationsupplmentType(
			int customsDeclarationsupplmentType) {
		this.customsDeclarationsupplmentType = customsDeclarationsupplmentType;
	}

	/**
	 * This method initializes cbbI_IsUsefee	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbI_IsUsefee() {
		if (cbbI_IsUsefee == null) {
			cbbI_IsUsefee = new JComboBox();
			cbbI_IsUsefee.setBounds(new Rectangle(654, 348, 114, 21));
			cbbI_IsUsefee
			.addItem(new ItemProperty(
					RepDeclarationType.I_IsUsefeeN,
					RepDeclarationType
							.getE_IsDutyDel(RepDeclarationType.I_IsUsefeeN)));
			cbbI_IsUsefee
			.addItem(new ItemProperty(
					RepDeclarationType.I_IsUsefeeY,
					RepDeclarationType
							.getE_IsDutyDel(RepDeclarationType.I_IsUsefeeY)));
		}
		return cbbI_IsUsefee;
	}

	/**
	 * This method initializes cbbI_IsProfit	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbI_IsProfit() {
		if (cbbI_IsProfit == null) {
			cbbI_IsProfit = new JComboBox();
			cbbI_IsProfit.setBounds(new Rectangle(654, 424, 114, 21));
			cbbI_IsProfit
			.addItem(new ItemProperty(
					RepDeclarationType.I_IsProfitN,
					RepDeclarationType
							.getE_IsDutyDel(RepDeclarationType.I_IsProfitN)));
			cbbI_IsProfit
			.addItem(new ItemProperty(
					RepDeclarationType.I_IsProfitY,
					RepDeclarationType
							.getE_IsDutyDel(RepDeclarationType.I_IsProfitY)));
		}
		return cbbI_IsProfit;
	}

	/**
	 * This method initializes cbbOriginMark	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbOriginMark() {
		if (cbbOriginMark == null) {
			cbbOriginMark = new JComboBox();
			cbbOriginMark.setBounds(new Rectangle(569, 169, 122, 22));
			cbbOriginMark
			.addItem(new ItemProperty(
					RepDeclarationType.OriginMarkNo,
					RepDeclarationType.getOriginMark(RepDeclarationType.OriginMarkNo)));
			cbbOriginMark
			.addItem(new ItemProperty(
					RepDeclarationType.OriginMarkOUT,
					RepDeclarationType.getOriginMark(RepDeclarationType.OriginMarkOUT)));
			cbbOriginMark
			.addItem(new ItemProperty(
					RepDeclarationType.OriginMarkIN,
					RepDeclarationType.getOriginMark(RepDeclarationType.OriginMarkIN)));
			cbbOriginMark
			.addItem(new ItemProperty(
					RepDeclarationType.OriginMarkItSelf,
					RepDeclarationType.getOriginMark(RepDeclarationType.OriginMarkItSelf)));
					}
		return cbbOriginMark;
	}
	
}


