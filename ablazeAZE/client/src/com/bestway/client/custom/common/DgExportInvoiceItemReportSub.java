package com.bestway.client.custom.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.custom.common.report.CustomsDeclarationSubReportDataSource;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author fhz
 * 
 */
public class DgExportInvoiceItemReportSub extends JDialogBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbPortOfTransport = null;

	private JButton btPrint = null;

	private JButton jButton1 = null;

	private ToolsAction toolsAction = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel21 = null;

	private JTextField tfMemos = null;

	private BaseCustomsDeclaration customsDeclaration = null; // @jve:decl-index=0:

	private List<BaseCustomsDeclarationCommInfo> declarationCommInfoList = null; // @jve:decl-index=0:

	private JLabel jLabel71 = null;

	private JLabel jLabel72 = null;

	// private JTextField tfInvoicePeople = null;

	private JTextField tfPortOfAim = null;

	private JTextField tfConveyance = null;

	private JTextField tfAccount = null;
	
	private JTextField tfBank = null;

	private Boolean isOverPrint = false; // @jve:decl-index=0:

	private int projectType;

	private JRadioButton rb7 = null;

	private JRadioButton rb8 = null;

	private JRadioButton rb9 = null;
	private ButtonGroup group = null; // @jve:decl-index=0:

	private JLabel jLabel = null;

	private JTextField cbbPortOrTransport = null;

	private JCheckBox jCheckBox = null;

	private JTextField tfInvoicePeople = null;

	private ContractAction contractAction = null;

	private JCheckBox cbIsPrintCustomer = null;

	private JCheckBox cbIsPrintCavOrder = null;

	private JTextField tfCustomer = null;

	private JTextField tfInvoiceNo = null;

	private Double process = 0d;// 加工费 // @jve:decl-index=0:

	private JRadioButton rbtnPrintNo = null;

	private JCheckBox cbIsPrintStyle = null;

	private JLabel jLabel31 = null;

	private JFormattedTextField tfTopPage = null;

	private JFormattedTextField tfButtomPage = null;

	private JFormattedTextField tfLeftPage = null;

	private JFormattedTextField tfRightPage = null;

	private JLabel jLabel32 = null;

	private JLabel jLabel33 = null;

	private JLabel jLabel34 = null;

	private JPanel jPanel = null;

	private CompanyOther companyOther = null;

	private SystemAction systemAction = null;

	/**
	 * This is the default constructor
	 */
	public DgExportInvoiceItemReportSub() {
		super();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		List list = systemAction.findCompanyOther(new Request(
				CommonVars.getCurrUser(),true));
		if (list != null && list.size() > 0) {
			companyOther = (CompanyOther) list.get(0);
		}
		initialize();
	}

	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
		}
		super.setVisible(b);
	}

	/**
	 * 初始化窗口上 控件的值
	 * 
	 */
	private void initUIComponents() {
		// 备注
		// if (customsDeclaration.getMemos() != null) {
		// this.tfMemos.setText(customsDeclaration.getMemos());
		// } else {
		// this.tfMemos.setText("");
		// }
		// // 开票人
		// if (customsDeclaration.getCustomser() != null) {
		// this.tfInvoicePeople.setText(customsDeclaration.getCustomser());
		// } else {
		// this.tfInvoicePeople.setText("");
		// }
		// 发运港
		// this.cbbPortOrTransport.setModel(CustomBaseModel.getInstance()
		// .getCustomModel());
		// this.cbbPortOrTransport.setRenderer(CustomBaseRender.getInstance()
		// .getRender());
		// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
		// this.cbbPortOrTransport);
		//		
		if (customsDeclaration.getCustoms() != null) {
			// cbbPortOrTransport.setText(customsDeclaration.getCustoms()
			// .getName());
			cbbPortOrTransport.setText("东莞");
		}
		// 转运港
		this.cbbPortOfTransport.setModel(CustomBaseModel.getInstance()
				.getPortLinModel());
		this.cbbPortOfTransport.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbPortOfTransport);
		if (customsDeclaration.getPortOfLoadingOrUnloading() != null) {
			cbbPortOfTransport.setSelectedItem(customsDeclaration
					.getPortOfLoadingOrUnloading());
		} else {
			cbbPortOfTransport.setSelectedIndex(-1);
		}
		// 目的港
		if (customsDeclaration.getPortOfLoadingOrUnloading() != null) {
			this.tfPortOfAim.setText(customsDeclaration
					.getPortOfLoadingOrUnloading().getName());
		} else {
			this.tfPortOfAim.setText("");
		}

		// 运输工具
		if (customsDeclaration.getTransferMode() != null) {
			this.tfConveyance.setText(customsDeclaration.getTransferMode()
					.getName());
		} else {
			this.tfConveyance.setText("");
		}
		// 银行帐户
		if (((Company) customsDeclaration.getCompany()).getAccount() != null) {
			this.tfAccount.setText(((Company) customsDeclaration.getCompany())
					.getAccount());
		} else {
			this.tfAccount.setText("");
		}
		
		// 开户银行
		if (((Company) customsDeclaration.getCompany()).getBank() != null) {
			this.tfBank.setText(((Company) customsDeclaration.getCompany())
					.getBank());
		} else {
			this.tfBank.setText("");
		}
		// 显示字体的大小
		BcsParameterSet parameterSet = contractAction
				.findBcsParameterSet(new Request(CommonVars.getCurrUser(), true));

		if (parameterSet != null && parameterSet.getExportfpfont() != null) {
			if (parameterSet.getExportfpfont().toString().equals("0")) {
				getRb7().setSelected(true);
			} else if (parameterSet.getExportfpfont().toString().equals("1")) {
				getRb8().setSelected(true);
			} else if (parameterSet.getExportfpfont().toString().equals("2")) {
				getRb9().setSelected(true);
			}
		} else {
			getRb7().setSelected(true);
		}
		if (parameterSet != null && parameterSet.getPageLeft() != null) {
			tfLeftPage.setValue(parameterSet.getPageLeft());
		} else {
			tfLeftPage.setValue(30);
		}
		if (parameterSet != null && parameterSet.getPageRight() != null) {
			tfRightPage.setValue(30);
		} else {
			tfRightPage.setValue(30);
		}
		if (parameterSet != null && parameterSet.getPageTop() != null) {
			tfTopPage.setValue(parameterSet.getPageTop());
		} else {
			tfTopPage.setValue(20);
		}
		if (parameterSet != null && parameterSet.getPageBottom() != null) {
			tfButtomPage.setValue(parameterSet.getPageBottom());
		} else {
			tfButtomPage.setValue(20);
		}

		if (cbIsPrintCavOrder.isSelected()) {
			tfInvoiceNo.setText(customsDeclaration.getCustomsDeclarationCode());
		}
		if (cbIsPrintCustomer.isSelected()) {
			ScmCoc customer = customsDeclaration.getCustomer();
			if (customer != null) {
				tfCustomer.setText(customer.getName());
			}
		}
		//如果系统参数设置了进出口岸就用默认的，否则就取报关单表头出口口岸
		if(companyOther.getSendCustoms()==null||"".equals(companyOther.getSendCustoms())){
			cbbPortOrTransport.setText(customsDeclaration.getCustoms()==null?"":customsDeclaration.getCustoms().getName());
		}else {
			cbbPortOrTransport.setText(companyOther.getSendCustoms());
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("发票项目");
		this.setSize(595, 372);
		this.setContentPane(getJContentPane());
		this.setContentPane(getJContentPane());
		getGroup();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel34 = new JLabel();
			jLabel34.setText("右边距");
			jLabel34.setBounds(new Rectangle(255, 72, 36, 18));
			jLabel33 = new JLabel();
			jLabel33.setText("左边距");
			jLabel33.setBounds(new Rectangle(255, 43, 36, 18));
			jLabel32 = new JLabel();
			jLabel32.setText("下边距");
			jLabel32.setBounds(new Rectangle(72, 73, 36, 18));
			jLabel31 = new JLabel();
			jLabel31.setText("上边距");
			jLabel31.setBounds(new Rectangle(72, 46, 36, 18));
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(15, 99, 61, 22));
			jLabel.setText("发运港");
			jLabel72 = new JLabel();
			jLabel72.setBounds(new Rectangle(19, 10, 61, 22));
			jLabel72.setText("开户银行");
			jLabel71 = new JLabel();
			jLabel71.setBounds(new Rectangle(281, 12, 88, 22));
			jLabel71.setText("银行帐户");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(281, 67, 88, 22));
			jLabel21.setText("备注");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(18, 40, 61, 22));
			jLabel7.setText("运输工具");
			// jLabel7.setForeground(java.awt.Color.blue);
			// jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel1.setBounds(281, 39, 88, 22);
			jLabel1.setText("目的港");
			jLabel2.setBounds(17, 68, 61, 22);
			jLabel2.setText("转运港");
			// jLabel3.setBounds(36, 190, 60, 22);
			// jLabel3.setText("开票人");
			// jLabel3.setForeground(java.awt.Color.blue);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			// jContentPane.add(jLabel3, null);
			jContentPane.add(getCbbPortOfTransport(), null);
			jContentPane.add(getBtPrint(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel21, null);
			jContentPane.add(getTfMemos(), null);
			jContentPane.add(jLabel71, null);
			jContentPane.add(jLabel72, null);
			// jContentPane.add(getTfInvoicePeople(), null);
			jContentPane.add(getTfPortOfAim(), null);
			jContentPane.add(getTfConveyance(), null);
			jContentPane.add(getTfAccount(), null);
			jContentPane.add(getTfBank(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getCbbPortOrTransport(), null);
			jContentPane.add(getJCheckBox(), null);
			jContentPane.add(getTfInvoicePeople(), null);
			jContentPane.add(getCbIsPrintCustomer(), null);
			jContentPane.add(getCbIsPrintCavOrder(), null);
			jContentPane.add(getTfCustomer(), null);
			jContentPane.add(getTfInvoiceNo(), null);
			jContentPane.add(getRbtnPrintNo(), null);
			jContentPane.add(getCbIsPrintStyle(), null);
			jContentPane.add(getJPanel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbPortOfTransport() {
		if (cbbPortOfTransport == null) {
			cbbPortOfTransport = new JComboBox();
			cbbPortOfTransport.setBounds(92, 67, 179, 24);
		}
		return cbbPortOfTransport;
	}

	/**
	 * This method initializes btPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtPrint() {
		if (btPrint == null) {
			btPrint = new JButton();
			btPrint.setBounds(200, 306, 90, 21);
			btPrint.setText("打印");
			btPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// 先保存字体的型号
					BcsParameterSet parameterSet = contractAction
							.findBcsParameterSet(new Request(CommonVars
									.getCurrUser(), true));
					if (parameterSet == null) {
						parameterSet = new BcsParameterSet();
					}
					parameterSet.setExportfpfont(getRb7().isSelected() ? "0"
							: getRb8().isSelected() ? "1" : "2");
					parameterSet.setPageLeft(tfLeftPage.getText() == null ? 30
							: Integer.valueOf(tfLeftPage.getText()));
					parameterSet
							.setPageRight(tfRightPage.getText() == null ? 30
									: Integer.valueOf(tfRightPage.getText()));
					parameterSet.setPageTop(tfTopPage.getText() == null ? 20
							: Integer.valueOf(tfTopPage.getText()));
					parameterSet
							.setPageBottom(tfButtomPage.getText() == null ? 20
									: Integer.valueOf(tfButtomPage.getText()));
					contractAction.saveBcsParameterSet(new Request(CommonVars
							.getCurrUser(), true), parameterSet);

					if (cbIsPrintCavOrder.isSelected()
							&& "".equals(customsDeclaration.getAuthorizeFile())) {
						if (!(JOptionPane.showConfirmDialog(
								DgExportInvoiceItemReportSub.this,
								"批准文号为空，是否继续？") == JOptionPane.OK_OPTION)) {
							return;
						}
					}
					printExportReport(declarationCommInfoList, isOverPrint);

				}
			});
		}
		return btPrint;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(338, 307, 90, 21);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemos() {
		if (tfMemos == null) {
			tfMemos = new JTextField();
			tfMemos.setBounds(new Rectangle(380, 64, 185, 23));
		}
		return tfMemos;
	}

	// 出口商品发票打印
	private void printExportReport(List commInfoList, boolean isOverPrint) {
		List list = new ArrayList();
		if (customsDeclaration != null) {
			list.add(customsDeclaration);
		} else {
			JOptionPane.showMessageDialog(this, "当前无数据可列印！", "提示",
					JOptionPane.YES_NO_OPTION);
			return;
		}
		CustomReportDataSource ds = new CustomReportDataSource(list);
		try {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoList);
			InputStream subReportStream = null;
			if (getRb7().isSelected()) {
				subReportStream = DgExportInvoiceItemReportSub.class
						.getResourceAsStream("report/ExportInvoiceItemCommodityReport.jasper");
			} else if (getRb8().isSelected()) {
				subReportStream = DgExportInvoiceItemReportSub.class
						.getResourceAsStream("report/ExportInvoiceItemCommodityReport8.jasper");
			} else if (getRb9().isSelected()) {
				subReportStream = DgExportInvoiceItemReportSub.class
						.getResourceAsStream("report/ExportInvoiceItemCommodityReport9.jasper");
			} else {
				subReportStream = DgExportInvoiceItemReportSub.class
						.getResourceAsStream("report/ExportInvoiceItemCommodityReport.jasper");
			}

			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("isOverPrint", Boolean.valueOf(isOverPrint));
			parameters.put("subReport", subReport);
			if (customsDeclaration.getTradeMode() != null
					&& "来料加工".equals(customsDeclaration.getTradeMode()
							.getName())) {
				String currName = customsDeclaration.getCurrency() == null ? ""
						: customsDeclaration.getCurrency().getCurrSymb();
				String str = String.format("%.2f", process);
				String str1 = str==null?"":str;
				parameters.put("process", "加工费(" + currName + "):" + str1);
			}else{
				parameters.put("process", "");
			}
			if (this.getProjectType() == ProjectType.BCUS) {
				parameters
						.put("emsHeadH2k", customsDeclaration.getEmsHeadH2k());
			} else {
				parameters.put("emsHeadH2k", customsDeclaration.getContract());
			}
			// 是否打印备案序号
			parameters.put("isPrintNo", (rbtnPrintNo.isSelected() ? "true"
					: "false"));
			System.out.println(rbtnPrintNo.isSelected() ? "true" : "false");
			// CompanyOther other = CommonVars.getOther();
			if (cbIsPrintCavOrder.isSelected()
					&& !"".equals(customsDeclaration.getCustomsDeclarationCode())) {
				String customsDeclarationCode = customsDeclaration
				.getCustomsDeclarationCode() == null?"":customsDeclaration
						.getCustomsDeclarationCode();
				String memos = tfMemos.getText()== null?"":tfMemos.getText();
				parameters.put("memo",
								"报关单号："+customsDeclarationCode + " "
												+ memos);// 备注
				
			} else {
				parameters.put("memo", this.tfMemos.getText());// 备注
			}		
			Company comp =(Company) CommonVars.getCurrUser().getCompany();
			List isExportPackinglistOrInvoiceList = contractAction.findExportPackinglistOrInvoice(new Request(CommonVars
									.getCurrUser(), true));
			Boolean isExportPackinglistOrInvoice = (Boolean) isExportPackinglistOrInvoiceList.get(0);
			if(isExportPackinglistOrInvoice == null){
				isExportPackinglistOrInvoice = false;
			}
			if (comp != null) {
				parameters.put("companyName", comp.getBuName());// 经营单位名称
				parameters.put("companyAdd", comp.getBuaddress());// 经营单位地址
				parameters.put("companyTel", comp.getButel());// 经营单位电话
				if(true == isExportPackinglistOrInvoice){
					parameters.put("deliverCompanyName", comp.getName());
					parameters.put("deliverCompany", "发  货  单  位");
				}
				System.out.println("outFax" + comp.getOutFax());
				parameters.put("outFax", comp.getOutFax());// 外商公司传真

				parameters.put("openAnAccountBank", this.tfBank.getText());// 开户银行
				parameters.put("bankAccounts", this.tfAccount.getText());// 帐户
				parameters.put("portOfTransport", this.cbbPortOfTransport
						.getSelectedItem() == null ? ""
						: ((PortLin) cbbPortOfTransport.getSelectedItem())
								.getName());// 转运港
				parameters.put("transferModeName", this.tfConveyance.getText());// 运输方式
				parameters.put("conveyance", this.customsDeclaration.getConveyance());// 运输工具
				parameters.put("portOfLoadingOrUnloadingName", this.tfPortOfAim
						.getText());// 目的港
				parameters.put("invoicePeople",
						this.tfInvoicePeople.getText() == null ? ""
								: this.tfInvoicePeople.getText());// 开票人
				parameters.put("invoicePeopleName",
						jCheckBox.isSelected() ? "开票人" : "");// 开票人
				parameters.put("portOrTransport", this.cbbPortOrTransport
						.getText() == null ? "" : cbbPortOrTransport.getText());// 发运港
				if (cbIsPrintCustomer.isSelected()) {
					ScmCoc customer = customsDeclaration.getCustomer();
					if (customer != null) {
						parameters.put("customerName", customer.getName());// 客户公司
						parameters.put("customerTel", customer.getLinkTel());// 客户公司电话
						parameters.put("customerAdd", customer.getAddre());// 客户公司地址
					}
				}
			}
			parameters.put("transac", cbIsPrintStyle.isSelected() ? "成交方式："
					+ customsDeclaration.getTransac().getName() : "");// 成交方式
			InputStream itemRepot = DgExportInvoiceItemReportSub.class
					.getResourceAsStream("report/ExportInvoiceItemReport.jrxml");
			JasperDesign jasperDesign = JRXmlLoader.load(itemRepot);
			jasperDesign.setLeftMargin(tfLeftPage.getValue() == null ? 0
					: (Integer) tfLeftPage.getValue());
			jasperDesign.setRightMargin(tfRightPage.getValue() == null ? 0
					: (Integer) tfRightPage.getValue());
			jasperDesign.setTopMargin(tfTopPage.getValue() == null ? 0
					: (Integer) tfTopPage.getValue());
			jasperDesign.setBottomMargin(tfButtomPage.getValue() == null ? 0
					: (Integer) tfButtomPage.getValue());

			JasperReport js = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(js,
					parameters, ds);
			DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
			dgReportViewer.setVisible(true);

		} catch (JRException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(this, "数据异常", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		} finally {
			System.gc();
		}
	}

	/**
	 * @param customsDeclaration
	 *            the customsDeclaration to set
	 */
	public void setCustomsDeclaration(BaseCustomsDeclaration customsDeclaration) {
		this.customsDeclaration = customsDeclaration;
	}

	/**
	 * @param customsDeclarationCommInfo
	 *            the customsDeclarationCommInfo to set
	 */
	public void setDeclarationCommInfoList(List declarationCommInfoList) {
		this.declarationCommInfoList = declarationCommInfoList;
	}

	// /**
	// * This method initializes tfCustomsEnvelopBillNo1
	// *
	// * @return javax.swing.JTextField
	// */
	// private JTextField getTfInvoicePeople() {
	// if (tfInvoicePeople == null) {
	// tfInvoicePeople = new JTextField();
	// tfInvoicePeople.setBounds(new Rectangle(112, 190, 241, 22));
	// }
	// return tfInvoicePeople;
	// }

	/**
	 * This method initializes tfCustomsEnvelopBillNo2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPortOfAim() {
		if (tfPortOfAim == null) {
			tfPortOfAim = new JTextField();
			tfPortOfAim.setBounds(new Rectangle(380, 36, 185, 23));
		}
		return tfPortOfAim;
	}

	/**
	 * This method initializes tfCustomsEnvelopBillNo3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfConveyance() {
		if (tfConveyance == null) {
			tfConveyance = new JTextField();
			tfConveyance.setBounds(new Rectangle(92, 39, 179, 24));
		}
		return tfConveyance;
	}

	/**
	 * This method initializes tfCustomsEnvelopBillNo4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAccount() {
		if (tfAccount == null) {
			tfAccount = new JTextField();
			tfAccount.setBounds(new Rectangle(380, 9, 185, 23));
		}
		return tfAccount;
	}

	/**
	 * This method initializes tfCustomsEnvelopBillNo5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBank() {
		if (tfBank == null) {
			tfBank = new JTextField();
			tfBank.setBounds(new Rectangle(92, 9, 179, 23));
		}
		return tfBank;
	}

	/**
	 * @param isOverPrint
	 *            the isOverPrint to set
	 */
	public void setIsOverPrint(Boolean isOverPrint) {
		this.isOverPrint = isOverPrint;
	}

	public int getProjectType() {
		return projectType;
	}

	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	/**
	 * This method initializes rb7
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRb7() {
		if (rb7 == null) {
			rb7 = new JRadioButton();
			rb7.setSelected(true);
			rb7.setBounds(new Rectangle(92, 9, 61, 26));
			rb7.setText("七号字");
		}
		return rb7;
	}

	/**
	 * This method initializes rb8
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRb8() {
		if (rb8 == null) {
			rb8 = new JRadioButton();
			rb8.setText("八号字");
			rb8.setBounds(new Rectangle(197, 10, 61, 26));
		}
		return rb8;
	}

	/**
	 * This method initializes rb9
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRb9() {
		if (rb9 == null) {
			rb9 = new JRadioButton();
			rb9.setText("九号字");
			rb9.setBounds(new Rectangle(311, 10, 61, 26));
		}
		return rb9;
	}

	public ButtonGroup getGroup() {
		if (group == null) {
			group = new ButtonGroup();
			group.add(getRb7());
			group.add(getRb8());
			group.add(getRb9());
		}
		return group;
	}

	/**
	 * This method initializes cbbPortOrTransport
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JTextField getCbbPortOrTransport() {
		if (cbbPortOrTransport == null) {
			cbbPortOrTransport = new JTextField();
			cbbPortOrTransport.setBounds(new Rectangle(92, 98, 179, 24));

		}
		return cbbPortOrTransport;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(281, 94, 88, 22));
			jCheckBox.setText("打印开票人");
			jCheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jCheckBox.isSelected()) {
						tfInvoicePeople.setEditable(true);
					} else {
						tfInvoicePeople.setEditable(false);
						tfInvoicePeople.setText("");
					}
				}
			});
		}
		return jCheckBox;
	}

	/**
	 * This method initializes tfInvoicePeople
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInvoicePeople() {
		if (tfInvoicePeople == null) {
			tfInvoicePeople = new JTextField();
			tfInvoicePeople.setBounds(new Rectangle(380, 91, 185, 23));
			tfInvoicePeople.setEditable(false);
		}
		return tfInvoicePeople;
	}

	/**
	 * This method initializes cbIsPrintCustomer
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsPrintCustomer() {
		if (cbIsPrintCustomer == null) {
			cbIsPrintCustomer = new JCheckBox();
			cbIsPrintCustomer.setBounds(new Rectangle(12, 128, 79, 26));
			cbIsPrintCustomer.setText("打印客户");
			cbIsPrintCustomer.setSelected(true);
			cbIsPrintCustomer
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbIsPrintCustomer.isSelected()) {
								ScmCoc customer = customsDeclaration
										.getCustomer();
								if (customer != null) {
									tfCustomer.setText(customer.getName());
								}
							} else {
								tfCustomer.setText("");
								tfCustomer.setEditable(false);
							}
						}
					});
		}
		return cbIsPrintCustomer;
	}

	/**
	 * This method initializes cbIsPrintCavOrder
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsPrintCavOrder() {
		if (cbIsPrintCavOrder == null) {
			cbIsPrintCavOrder = new JCheckBox();
			cbIsPrintCavOrder.setBounds(new Rectangle(281, 123, 99, 22));
			cbIsPrintCavOrder.setText("打印报关单号");
			cbIsPrintCavOrder.setSelected(true);
			cbIsPrintCavOrder
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbIsPrintCavOrder.isSelected()) {
								tfInvoiceNo.setText(customsDeclaration
										.getCustomsDeclarationCode());
							} else {
								tfInvoiceNo.setText("");
								tfInvoiceNo.setEditable(false);
							}
						}
					});
		}
		return cbIsPrintCavOrder;
	}

	/**
	 * This method initializes tfCustomer
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomer() {
		if (tfCustomer == null) {
			tfCustomer = new JTextField();
			tfCustomer.setBounds(new Rectangle(92, 127, 179, 24));
			tfCustomer.setEditable(false);
		}
		return tfCustomer;
	}

	/**
	 * This method initializes tfInvoiceNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInvoiceNo() {
		if (tfInvoiceNo == null) {
			tfInvoiceNo = new JTextField();
			tfInvoiceNo.setBounds(new Rectangle(380, 120, 185, 23));
			tfInvoiceNo.setEditable(false);
		}
		return tfInvoiceNo;
	}

	public Double getProcess() {
		return process;
	}

	public void setProcess(Double process) {
		this.process = process;
	}

	/**
	 * This method initializes rbtnPrintNo
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbtnPrintNo() {
		if (rbtnPrintNo == null) {
			rbtnPrintNo = new JRadioButton();
			rbtnPrintNo.setBounds(new Rectangle(109, 157, 169, 25));
			rbtnPrintNo.setText("订单号内打印商品备案序号");
		}
		return rbtnPrintNo;
	}

	/**
	 * This method initializes cbIsPrintStyle
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsPrintStyle() {
		if (cbIsPrintStyle == null) {
			cbIsPrintStyle = new JCheckBox();
			cbIsPrintStyle.setBounds(new Rectangle(353, 156, 121, 21));
			cbIsPrintStyle.setText("是否打印成交方式");
		}
		return cbIsPrintStyle;
	}

	/**
	 * This method initializes tfTopPage
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfTopPage() {
		if (tfTopPage == null) {
			tfTopPage = new JFormattedTextField();
			tfTopPage.setBounds(new Rectangle(116, 42, 122, 22));
		}
		return tfTopPage;
	}

	/**
	 * This method initializes tfButtomPage
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfButtomPage() {
		if (tfButtomPage == null) {
			tfButtomPage = new JFormattedTextField();
			tfButtomPage.setBounds(new Rectangle(116, 69, 122, 22));
		}
		return tfButtomPage;
	}

	/**
	 * This method initializes tfLeftPage
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfLeftPage() {
		if (tfLeftPage == null) {
			tfLeftPage = new JFormattedTextField();
			tfLeftPage.setBounds(new Rectangle(299, 42, 122, 22));
		}
		return tfLeftPage;
	}

	/**
	 * This method initializes tfRightPage
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfRightPage() {
		if (tfRightPage == null) {
			tfRightPage = new JFormattedTextField();
			tfRightPage.setBounds(new Rectangle(300, 68, 122, 22));
		}
		return tfRightPage;
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
			jPanel.setBounds(new Rectangle(63, 186, 479, 115));
			jPanel.setBorder(BorderFactory.createTitledBorder(null,
					"\u62a5\u8868\u8bbe\u7f6e",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel.add(jLabel32, null);
			jPanel.add(jLabel33, null);
			jPanel.add(jLabel34, null);
			jPanel.add(getTfTopPage(), null);
			jPanel.add(getTfButtomPage(), null);
			jPanel.add(getTfLeftPage(), null);
			jPanel.add(getTfRightPage(), null);
			jPanel.add(jLabel31, null);
			jPanel.add(getRb9(), null);
			jPanel.add(getRb7(), null);
			jPanel.add(getRb8(), null);
		}
		return jPanel;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
