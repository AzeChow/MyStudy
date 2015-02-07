package com.bestway.bcus.client.system;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.ReportControl;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 报表栏位参数设置
 * 
 * @author 石小凯
 * 
 */
public class DgSetReport extends JDialogBase {

	private JPanel jContentPane = null;
	private JToolBar jJToolBarBar = null;
	private JTabbedPane jTabbedPane = null;
	private JButton btnEdit = null;
	private JButton btnSave = null;
	private JButton btnClose = null;
	private JPanel pnCommonList = null;
	private JCheckBox cbSecondLegalUnit = null;
	private JCheckBox cbSecondAmount = null;
	private JCheckBox cbUses = null;
	private JCheckBox cbLevyMode = null;
	private JCheckBox cbPieces = null;
	private JCheckBox cbWrapType = null;
	private JCheckBox cbCustoms = null;
	private JCheckBox cbImpExpDate = null;
	private JCheckBox cbTransferMode = null;
	private JCheckBox cbLevyKind = null;
	private JCheckBox cbLicense = null;
	private JCheckBox cbCountryOfLoadingOrUnloading = null;
	private JCheckBox cbDomesticDestinationOrSource = null;
	private JCheckBox cbPortOfLoadingOrUnloading = null;
	private JCheckBox cbAuthorizeFile = null;
	private JCheckBox cbTransac = null;
	private JCheckBox cbGrossWeight = null;
	private JCheckBox cbNetWeight = null;
	private JCheckBox cbCommodityNum = null;
	private JCheckBox cbContainerNum = null;
	private JCheckBox cbAttachedBill = null;
	private JCheckBox cbDeclarationCustoms = null;
	private JCheckBox cbCertificateCode = null;
	private JCheckBox cbMemos = null;
	private JCheckBox cbPredock = null;
	private JCheckBox cbBondedWarehouse = null;
	private JCheckBox cbCustomsEnvelopBillNo = null;
	private JCheckBox cbRelativeManualNo = null;
	private JCheckBox cbRelativeId = null;
	private int dataState = DataState.BROWSE;
	private SystemAction systemAction = null;
	/**
	 * 报表栏位参数实体
	 */
	private ReportControl reportControl; // @jve:decl-index=0:
	private JCheckBox cbInvoiceCode = null;
	private JCheckBox cbWrapTypeDetail = null;
	private JCheckBox cbPreCustomsDeclarationCode = null;
	private JCheckBox cbCustomdeclarationMun = null;
	private JCheckBox cbCustomdeclarationMun1 = null;
	private JCheckBox cbContractAmount;
	private JCheckBox cbDetailNote = null;
	private JCheckBox cbCustomerCode = null;

	/**
	 * 获取 报表栏位参数实体
	 * 
	 * @return
	 */
	public ReportControl getReportControl() {
		return reportControl;
	}

	/**
	 * 设置 报表栏位参数实体
	 * 
	 * @param reportControl
	 */
	public void setReportControl(ReportControl reportControl) {
		this.reportControl = reportControl;
	}

	/**
	 * 窗口构造函数
	 * 
	 */
	public DgSetReport() {
		super();
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		initialize();
		this.setState();
	}

	public void setVisible(boolean b) {
		fillWondow();
		super.setVisible(b);
	}

	/**
	 * 初始化栏位数值
	 */
	private void fillWondow() {
		cbSecondLegalUnit.setSelected(reportControl.getSecondLegalUnit());
		cbSecondAmount.setSelected(reportControl.getSecondAmount());
		cbUses.setSelected(reportControl.getUses());
		cbDeclarationCustoms.setSelected(reportControl.getDeclarationCustoms());

		cbLevyMode.setSelected(reportControl.getLevyMode());
		cbPieces.setSelected(reportControl.getPieces());
		cbWrapType.setSelected(reportControl.getWrapType());
		cbWrapTypeDetail
				.setSelected(reportControl.getWrapTypeDetail() == null ? false
						: reportControl.getWrapTypeDetail());
		cbCertificateCode.setSelected(reportControl.getCertificateCode());

		cbCustoms.setSelected(reportControl.getCustoms());
		cbImpExpDate.setSelected(reportControl.getImpExpDate());
		cbTransferMode.setSelected(reportControl.getTransferMode());
		cbMemos.setSelected(reportControl.getMemos());

		cbLevyKind.setSelected(reportControl.getLevyKind());
		cbLicense.setSelected(reportControl.getLicense());
		cbCountryOfLoadingOrUnloading.setSelected(reportControl
				.getCountryOfLoadingOrUnloading());
		cbPredock.setSelected(reportControl.getPredock());

		cbDomesticDestinationOrSource.setSelected(reportControl
				.getDomesticDestinationOrSource());
		cbPortOfLoadingOrUnloading.setSelected(reportControl
				.getPortOfLoadingOrUnloading());
		cbAuthorizeFile.setSelected(reportControl.getAuthorizeFile());
		cbBondedWarehouse.setSelected(reportControl.getBondedWarehouse());

		cbTransac.setSelected(reportControl.getTransac());
		cbGrossWeight.setSelected(reportControl.getGrossWeight());
		cbNetWeight.setSelected(reportControl.getNetWeight());
		cbRelativeManualNo.setSelected(reportControl.getRelativeManualNo());

		cbCommodityNum.setSelected(reportControl.getCommodityNum());
		cbContainerNum.setSelected(reportControl.getContainerNum());
		cbAttachedBill.setSelected(reportControl.getAttachedBill());
		cbRelativeId.setSelected(reportControl.getRelativeId());
		cbCustomdeclarationMun.setSelected(reportControl
				.getCustomdeclarationMun() == null ? false : reportControl
				.getCustomdeclarationMun());
		cbCustomdeclarationMun1
				.setSelected(reportControl.getCreater() == null ? false
						: reportControl.getCreater());
		cbContractAmount
				.setSelected(reportControl.getContractAmount() == null ? false
						: reportControl.getContractAmount());
		cbDetailNote.setSelected(reportControl.getDetailNote() == null ? false
				: reportControl.getDetailNote());
		cbCustomerCode.setSelected(Boolean.TRUE.equals(reportControl
				.getCustomerCode()));
		cbCustomsEnvelopBillNo.setSelected(reportControl.getRelativeId());
		cbPreCustomsDeclarationCode.setSelected(reportControl
				.getPreCustomsDeclarationCode() == null ? false : reportControl
				.getPreCustomsDeclarationCode());
		if (null == reportControl.getInvoiceCode()) {
			cbInvoiceCode.setSelected(false);
		} else {
			cbInvoiceCode.setSelected(reportControl.getInvoiceCode());
		}

	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		setSize(new Dimension(706, 482));

		setContentPane(getJContentPane());

		setTitle("报表栏位设置");
	}

	/**
	 * 初始化状态
	 */
	private void setState() {
		this.btnEdit.setEnabled(dataState == DataState.BROWSE);
		this.btnSave.setEnabled(!(dataState == DataState.BROWSE));
		this.cbSecondLegalUnit.setEnabled(!(dataState == DataState.BROWSE));
		this.cbSecondAmount.setEnabled(!(dataState == DataState.BROWSE));
		this.cbUses.setEnabled(!(dataState == DataState.BROWSE));
		this.cbDeclarationCustoms.setEnabled(!(dataState == DataState.BROWSE));
		this.cbLevyMode.setEnabled(!(dataState == DataState.BROWSE));
		this.cbPieces.setEnabled(!(dataState == DataState.BROWSE));
		this.cbWrapType.setEnabled(!(dataState == DataState.BROWSE));
		this.cbWrapTypeDetail.setEnabled(!(dataState == DataState.BROWSE));
		this.cbCertificateCode.setEnabled(!(dataState == DataState.BROWSE));
		this.cbCustoms.setEnabled(!(dataState == DataState.BROWSE));
		this.cbImpExpDate.setEnabled(!(dataState == DataState.BROWSE));
		this.cbTransferMode.setEnabled(!(dataState == DataState.BROWSE));
		this.cbMemos.setEnabled(!(dataState == DataState.BROWSE));
		this.cbLevyKind.setEnabled(!(dataState == DataState.BROWSE));
		this.cbLicense.setEnabled(!(dataState == DataState.BROWSE));
		this.cbCountryOfLoadingOrUnloading
				.setEnabled(!(dataState == DataState.BROWSE));
		this.cbPredock.setEnabled(!(dataState == DataState.BROWSE));
		this.cbDomesticDestinationOrSource
				.setEnabled(!(dataState == DataState.BROWSE));
		this.cbPortOfLoadingOrUnloading
				.setEnabled(!(dataState == DataState.BROWSE));
		this.cbAuthorizeFile.setEnabled(!(dataState == DataState.BROWSE));
		this.cbBondedWarehouse.setEnabled(!(dataState == DataState.BROWSE));
		this.cbTransac.setEnabled(!(dataState == DataState.BROWSE));
		this.cbGrossWeight.setEnabled(!(dataState == DataState.BROWSE));
		this.cbNetWeight.setEnabled(!(dataState == DataState.BROWSE));
		this.cbRelativeManualNo.setEnabled(!(dataState == DataState.BROWSE));
		this.cbCommodityNum.setEnabled(!(dataState == DataState.BROWSE));
		this.cbContainerNum.setEnabled(!(dataState == DataState.BROWSE));
		this.cbAttachedBill.setEnabled(!(dataState == DataState.BROWSE));
		this.cbRelativeId.setEnabled(!(dataState == DataState.BROWSE));
		this.cbCustomsEnvelopBillNo
				.setEnabled(!(dataState == DataState.BROWSE));
		this.cbInvoiceCode.setEnabled(!(dataState == DataState.BROWSE));
		this.cbPreCustomsDeclarationCode
				.setEnabled(!(dataState == DataState.BROWSE));
		this.cbCustomdeclarationMun
				.setEnabled(!(dataState == DataState.BROWSE));
		this.cbCustomdeclarationMun1
				.setEnabled(!(dataState == DataState.BROWSE));
		this.cbContractAmount.setEnabled(!(dataState == DataState.BROWSE));
		this.cbDetailNote.setEnabled(!(dataState == DataState.BROWSE));
		this.cbCustomerCode.setEnabled(!(dataState == DataState.BROWSE));

	}

	/**
	 * 分栏容器
	 * 
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJJToolBarBar(), BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 工具栏容器
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getBtnEdit());
			jJToolBarBar.add(getBtnSave());
			jJToolBarBar.add(getBtnClose());
		}
		return jJToolBarBar;
	}

	/**
	 * 报关登记表 叠页容器
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("报关登记表", null, getPnCommonList(), null);
		}
		return jTabbedPane;
	}

	/**
	 * 修改
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT; // 设置为可编辑
					setState();
				}
			});
		}

		return btnEdit;
	}

	/**
	 * 保存
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillDate(reportControl); // 填充表单实体
					systemAction.saveReportControl(
							new Request(CommonVars.getCurrUser()),
							reportControl); // 保存实体
					System.out.println(reportControl.getDetailNote());
					dataState = DataState.BROWSE;
					setState();

				}
			});
		}
		return btnSave;
	}

	/**
	 * 填充表单实体
	 * 
	 * @param reportControl
	 */
	public void fillDate(ReportControl reportControl) {
		reportControl.setSecondLegalUnit(cbSecondLegalUnit.isSelected());
		reportControl.setSecondAmount(cbSecondAmount.isSelected());
		reportControl.setUses(cbUses.isSelected());
		reportControl.setLevyMode(cbLevyMode.isSelected());

		reportControl.setDeclarationCustoms(cbDeclarationCustoms.isSelected());
		reportControl.setPieces(cbPieces.isSelected());
		reportControl.setWrapType(cbWrapType.isSelected());
		reportControl.setWrapTypeDetail(cbWrapTypeDetail.isSelected());
		reportControl.setCertificateCode(cbCertificateCode.isSelected());

		reportControl.setCustoms(cbCustoms.isSelected());
		reportControl.setImpExpDate(cbImpExpDate.isSelected());
		reportControl.setTransferMode(cbTransferMode.isSelected());
		reportControl.setMemos(cbMemos.isSelected());

		reportControl.setLevyKind(cbLevyKind.isSelected());
		reportControl.setLicense(cbLicense.isSelected());
		reportControl
				.setCountryOfLoadingOrUnloading(cbCountryOfLoadingOrUnloading
						.isSelected());
		reportControl.setPredock(cbPredock.isSelected());

		reportControl
				.setDomesticDestinationOrSource(cbDomesticDestinationOrSource
						.isSelected());
		reportControl.setPortOfLoadingOrUnloading(cbPortOfLoadingOrUnloading
				.isSelected());
		reportControl.setAuthorizeFile(cbAuthorizeFile.isSelected());
		reportControl.setBondedWarehouse(cbBondedWarehouse.isSelected());

		reportControl.setTransac(cbTransac.isSelected());
		reportControl.setGrossWeight(cbGrossWeight.isSelected());
		reportControl.setNetWeight(cbNetWeight.isSelected());
		reportControl.setRelativeManualNo(cbRelativeManualNo.isSelected());

		reportControl.setCommodityNum(cbCommodityNum.isSelected());
		reportControl.setContainerNum(cbContainerNum.isSelected());
		reportControl.setAttachedBill(cbAttachedBill.isSelected());
		reportControl.setRelativeId(cbRelativeId.isSelected());

		reportControl.setCustomsEnvelopBillNo(cbCustomsEnvelopBillNo
				.isSelected());
		reportControl.setInvoiceCode(cbInvoiceCode.isSelected());
		reportControl.setPreCustomsDeclarationCode(cbPreCustomsDeclarationCode
				.isSelected());
		reportControl.setCustomdeclarationMun(cbCustomdeclarationMun
				.isSelected());
		reportControl.setCreater(cbCustomdeclarationMun1.isSelected());
		reportControl.setContractAmount(cbContractAmount.isSelected());
		reportControl.setDetailNote(cbDetailNote.isSelected());
		reportControl.setCustomerCode(cbCustomerCode.isSelected());
	}

	/**
	 * 取消
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
	 * This method initializes pnCommonList
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnCommonList() {
		if (pnCommonList == null) {
			pnCommonList = new JPanel();
			pnCommonList.setLayout(null);
			pnCommonList.add(getCbSecondLegalUnit(), null);
			pnCommonList.add(getCbSecondAmount(), null);
			pnCommonList.add(getCbUses(), null);
			pnCommonList.add(getCbLevyMode(), null);
			pnCommonList.add(getCbPieces(), null);
			pnCommonList.add(getCbWrapType(), null);
			pnCommonList.add(getCbCustoms(), null);
			pnCommonList.add(getCbImpExpDate(), null);
			pnCommonList.add(getCbTransferMode(), null);
			pnCommonList.add(getCbLevyKind(), null);
			pnCommonList.add(getCbLicense(), null);
			pnCommonList.add(getCbCountryOfLoadingOrUnloading(), null);
			pnCommonList.add(getCbDomesticDestinationOrSource(), null);
			pnCommonList.add(getCbPortOfLoadingOrUnloading(), null);
			pnCommonList.add(getCbAuthorizeFile(), null);
			pnCommonList.add(getCbTransac(), null);
			pnCommonList.add(getCbGrossWeight(), null);
			pnCommonList.add(getCbNetWeight(), null);
			pnCommonList.add(getCbCommodityNum(), null);
			pnCommonList.add(getCbContainerNum(), null);
			pnCommonList.add(getCbAttachedBill(), null);
			pnCommonList.add(getCbDeclarationCustoms(), null);
			pnCommonList.add(getCbCertificateCode(), null);
			pnCommonList.add(getCbMemos(), null);
			pnCommonList.add(getCbPredock(), null);
			pnCommonList.add(getCbBondedWarehouse(), null);
			pnCommonList.add(getCbCustomsEnvelopBillNo(), null);
			pnCommonList.add(getCbRelativeManualNo(), null);
			pnCommonList.add(getCbRelativeId(), null);
			pnCommonList.add(getCbInvoiceCode(), null);
			pnCommonList.add(getCbWrapTypeDetail(), null);
			pnCommonList.add(getCbPreCustomsDeclarationCode(), null);
			pnCommonList.add(getCbCustomdeclarationMun(), null);
			pnCommonList.add(getCbCustomdeclarationMun1(), null);
			pnCommonList.add(getCbContractAmount());
			pnCommonList.add(getCbDetailNote());
			pnCommonList.add(getCbCustomerCode());
		}
		return pnCommonList;
	}

	/**
	 * 第二法定单位
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbSecondLegalUnit() {
		if (cbSecondLegalUnit == null) {
			cbSecondLegalUnit = new JCheckBox();
			cbSecondLegalUnit.setBounds(new Rectangle(37, 25, 108, 21));
			cbSecondLegalUnit.setText("第二法定单位");
		}
		return cbSecondLegalUnit;
	}

	/**
	 * 第二法定数量
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbSecondAmount() {
		if (cbSecondAmount == null) {
			cbSecondAmount = new JCheckBox();
			cbSecondAmount.setBounds(new Rectangle(168, 25, 102, 21));
			cbSecondAmount.setText("第二法定数量");
		}
		return cbSecondAmount;
	}

	/**
	 * 用途
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbUses() {
		if (cbUses == null) {
			cbUses = new JCheckBox();
			cbUses.setBounds(new Rectangle(291, 25, 117, 21));
			cbUses.setText("用途");
		}
		return cbUses;
	}

	/**
	 * 征减免税方式
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbLevyMode() {
		if (cbLevyMode == null) {
			cbLevyMode = new JCheckBox();
			cbLevyMode.setBounds(new Rectangle(37, 70, 108, 21));
			cbLevyMode.setText("征减免税方式");
		}
		return cbLevyMode;
	}

	/**
	 * 件数
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbPieces() {
		if (cbPieces == null) {
			cbPieces = new JCheckBox();
			cbPieces.setBounds(new Rectangle(168, 70, 102, 21));
			cbPieces.setText("件数");
		}
		return cbPieces;
	}

	/**
	 * 包装方式
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbWrapType() {
		if (cbWrapType == null) {
			cbWrapType = new JCheckBox();
			cbWrapType.setBounds(new Rectangle(291, 70, 117, 21));
			cbWrapType.setActionCommand("包装方式");
			cbWrapType.setText("包装方式(头)");
		}
		return cbWrapType;
	}

	/**
	 * 进出口口岸
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCustoms() {
		if (cbCustoms == null) {
			cbCustoms = new JCheckBox();
			cbCustoms.setBounds(new Rectangle(168, 114, 102, 21));
			cbCustoms.setText("进出口口岸");
		}
		return cbCustoms;
	}

	/**
	 * 进出口日期
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbImpExpDate() {
		if (cbImpExpDate == null) {
			cbImpExpDate = new JCheckBox();
			cbImpExpDate.setBounds(new Rectangle(291, 111, 117, 21));
			cbImpExpDate.setText("进出口日期");
		}
		return cbImpExpDate;
	}

	/**
	 * 运输方式
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbTransferMode() {
		if (cbTransferMode == null) {
			cbTransferMode = new JCheckBox();
			cbTransferMode.setBounds(new Rectangle(428, 110, 119, 21));
			cbTransferMode.setText("运输方式");
		}
		return cbTransferMode;
	}

	/**
	 * 征免性质
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbLevyKind() {
		if (cbLevyKind == null) {
			cbLevyKind = new JCheckBox();
			cbLevyKind.setBounds(new Rectangle(168, 160, 102, 21));
			cbLevyKind.setText("征免性质");
		}
		return cbLevyKind;
	}

	/**
	 * 许可证号
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbLicense() {
		if (cbLicense == null) {
			cbLicense = new JCheckBox();
			cbLicense.setBounds(new Rectangle(291, 160, 117, 21));
			cbLicense.setText("许可证号");
		}
		return cbLicense;
	}

	/**
	 * 起运国/运抵国
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCountryOfLoadingOrUnloading() {
		if (cbCountryOfLoadingOrUnloading == null) {
			cbCountryOfLoadingOrUnloading = new JCheckBox();
			cbCountryOfLoadingOrUnloading.setBounds(new Rectangle(428, 158,
					119, 21));
			cbCountryOfLoadingOrUnloading.setText("起运国/运抵国");
		}
		return cbCountryOfLoadingOrUnloading;
	}

	/**
	 * 境内目的地
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbDomesticDestinationOrSource() {
		if (cbDomesticDestinationOrSource == null) {
			cbDomesticDestinationOrSource = new JCheckBox();
			cbDomesticDestinationOrSource.setBounds(new Rectangle(168, 207,
					102, 21));
			cbDomesticDestinationOrSource.setText("目的地/货源地");
		}
		return cbDomesticDestinationOrSource;
	}

	/**
	 * 装货港/指运港
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbPortOfLoadingOrUnloading() {
		if (cbPortOfLoadingOrUnloading == null) {
			cbPortOfLoadingOrUnloading = new JCheckBox();
			cbPortOfLoadingOrUnloading.setBounds(new Rectangle(291, 207, 117,
					21));
			cbPortOfLoadingOrUnloading.setText("装货港/指运港");
		}
		return cbPortOfLoadingOrUnloading;
	}

	/**
	 * 核销单号
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbAuthorizeFile() {
		if (cbAuthorizeFile == null) {
			cbAuthorizeFile = new JCheckBox();
			cbAuthorizeFile.setBounds(new Rectangle(428, 205, 119, 21));
			cbAuthorizeFile.setText("核销单号");
		}
		return cbAuthorizeFile;
	}

	/**
	 * 成交方式
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbTransac() {
		if (cbTransac == null) {
			cbTransac = new JCheckBox();
			cbTransac.setBounds(new Rectangle(168, 249, 102, 21));
			cbTransac.setText("成交方式");
		}
		return cbTransac;
	}

	/**
	 * 总毛重
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbGrossWeight() {
		if (cbGrossWeight == null) {
			cbGrossWeight = new JCheckBox();
			cbGrossWeight.setBounds(new Rectangle(291, 249, 117, 21));
			cbGrossWeight.setText("总毛重");
		}
		return cbGrossWeight;
	}

	/**
	 * 总净重
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbNetWeight() {
		if (cbNetWeight == null) {
			cbNetWeight = new JCheckBox();
			cbNetWeight.setBounds(new Rectangle(428, 247, 119, 21));
			cbNetWeight.setText("总净重");
		}
		return cbNetWeight;
	}

	/**
	 * 总件数
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCommodityNum() {
		if (cbCommodityNum == null) {
			cbCommodityNum = new JCheckBox();
			cbCommodityNum.setBounds(new Rectangle(168, 293, 102, 21));
			cbCommodityNum.setText("总件数");
		}
		return cbCommodityNum;
	}

	/**
	 * 集装箱号
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbContainerNum() {
		if (cbContainerNum == null) {
			cbContainerNum = new JCheckBox();
			cbContainerNum.setBounds(new Rectangle(291, 293, 117, 21));
			cbContainerNum.setText("集装箱号");
		}
		return cbContainerNum;
	}

	/**
	 * 随附单据
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbAttachedBill() {
		if (cbAttachedBill == null) {
			cbAttachedBill = new JCheckBox();
			cbAttachedBill.setBounds(new Rectangle(428, 291, 119, 21));
			cbAttachedBill.setText("随附单据");
		}
		return cbAttachedBill;
	}

	/**
	 * 报送海关
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbDeclarationCustoms() {
		if (cbDeclarationCustoms == null) {
			cbDeclarationCustoms = new JCheckBox();
			cbDeclarationCustoms.setBounds(new Rectangle(428, 24, 119, 21));
			cbDeclarationCustoms.setText("报送海关");
		}
		return cbDeclarationCustoms;
	}

	/**
	 * 备注证件代码
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCertificateCode() {
		if (cbCertificateCode == null) {
			cbCertificateCode = new JCheckBox();
			cbCertificateCode.setBounds(new Rectangle(37, 110, 108, 21));
			cbCertificateCode.setText("备注证件代码");
		}
		return cbCertificateCode;
	}

	/**
	 * 备注其他说明
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbMemos() {
		if (cbMemos == null) {
			cbMemos = new JCheckBox();
			cbMemos.setBounds(new Rectangle(37, 160, 108, 21));
			cbMemos.setText("备注其他说明");
		}
		return cbMemos;
	}

	/**
	 * 码头
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbPredock() {
		if (cbPredock == null) {
			cbPredock = new JCheckBox();
			cbPredock.setBounds(new Rectangle(37, 207, 108, 21));
			cbPredock.setText("码头");
		}
		return cbPredock;
	}

	/**
	 * 保税仓库
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbBondedWarehouse() {
		if (cbBondedWarehouse == null) {
			cbBondedWarehouse = new JCheckBox();
			cbBondedWarehouse.setBounds(new Rectangle(37, 249, 108, 21));
			cbBondedWarehouse.setText("保税仓库");
		}
		return cbBondedWarehouse;
	}

	/**
	 * 关封号
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCustomsEnvelopBillNo() {
		if (cbCustomsEnvelopBillNo == null) {
			cbCustomsEnvelopBillNo = new JCheckBox();
			cbCustomsEnvelopBillNo.setBounds(new Rectangle(168, 339, 102, 21));
			cbCustomsEnvelopBillNo.setText("关封号");
		}
		return cbCustomsEnvelopBillNo;
	}

	/**
	 * 关联手册号
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbRelativeManualNo() {
		if (cbRelativeManualNo == null) {
			cbRelativeManualNo = new JCheckBox();
			cbRelativeManualNo.setBounds(new Rectangle(37, 293, 108, 21));
			cbRelativeManualNo.setText("关联手册号");
		}
		return cbRelativeManualNo;
	}

	/**
	 * 关联报关单号
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbRelativeId() {
		if (cbRelativeId == null) {
			cbRelativeId = new JCheckBox();
			cbRelativeId.setBounds(new Rectangle(37, 339, 108, 21));
			cbRelativeId.setText("关联报关单号");
		}
		return cbRelativeId;
	}

	/**
	 * This method initializes cbInvoiceCode
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbInvoiceCode() {
		if (cbInvoiceCode == null) {
			cbInvoiceCode = new JCheckBox();
			cbInvoiceCode.setBounds(new Rectangle(291, 339, 117, 21));
			cbInvoiceCode.setText("发票号");
		}
		return cbInvoiceCode;
	}

	/**
	 * This method initializes cbWrapTypeDetail
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbWrapTypeDetail() {
		if (cbWrapTypeDetail == null) {
			cbWrapTypeDetail = new JCheckBox();
			cbWrapTypeDetail.setBounds(new Rectangle(428, 70, 119, 21));
			cbWrapTypeDetail.setText("包装方式(体)");
			cbWrapTypeDetail.setActionCommand("\u5305\u88c5\u65b9\u5f0f");
		}
		return cbWrapTypeDetail;
	}

	/**
	 * This method initializes cbPreCustomsDeclarationCode
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbPreCustomsDeclarationCode() {
		if (cbPreCustomsDeclarationCode == null) {
			cbPreCustomsDeclarationCode = new JCheckBox();
			cbPreCustomsDeclarationCode.setBounds(new Rectangle(428, 337, 81,
					21));
			cbPreCustomsDeclarationCode.setText("预录入号");
		}
		return cbPreCustomsDeclarationCode;
	}

	/**
	 * This method initializes cbCustomdeclarationMun
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCustomdeclarationMun() {
		if (cbCustomdeclarationMun == null) {
			cbCustomdeclarationMun = new JCheckBox();
			cbCustomdeclarationMun.setBounds(new Rectangle(554, 23, 107, 21));
			cbCustomdeclarationMun.setText("报关单份数");
		}
		return cbCustomdeclarationMun;
	}

	/**
	 * This method initializes cbCustomdeclarationMun1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCustomdeclarationMun1() {
		if (cbCustomdeclarationMun1 == null) {
			cbCustomdeclarationMun1 = new JCheckBox();
			cbCustomdeclarationMun1.setBounds(new Rectangle(555, 70, 85, 26));
			cbCustomdeclarationMun1.setSelected(false);
			cbCustomdeclarationMun1.setText("录入员");
		}
		return cbCustomdeclarationMun1;
	}

	private JCheckBox getCbContractAmount() {
		if (cbContractAmount == null) {
			cbContractAmount = new JCheckBox();
			cbContractAmount.setText("合同金额");
			cbContractAmount.setBounds(554, 110, 103, 23);
		}
		return cbContractAmount;
	}

	private JCheckBox getCbDetailNote() {
		if (cbDetailNote == null) {
			cbDetailNote = new JCheckBox();
			cbDetailNote.setText("表体备注");
			cbDetailNote.setBounds(554, 156, 103, 23);
		}
		return cbDetailNote;
	}

	private JCheckBox getCbCustomerCode() {
		if (cbCustomerCode == null) {
			cbCustomerCode = new JCheckBox();
			cbCustomerCode.setText("供应商代码");
			cbCustomerCode.setEnabled(false);
			cbCustomerCode.setBounds(554, 206, 103, 23);
		}
		return cbCustomerCode;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
