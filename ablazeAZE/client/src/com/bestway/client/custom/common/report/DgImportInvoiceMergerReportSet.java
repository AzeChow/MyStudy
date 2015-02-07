package com.bestway.client.custom.common.report;

import java.awt.Rectangle;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author fhz
 * 
 */
public class DgImportInvoiceMergerReportSet extends JDialogBase {
	/**
	 * 
	 */
	/**
	 * 料件管理操作接口
	 */
	private MaterialManageAction materialManageAction = null;

	/**
	 * 报关单的基础类接口
	 */
	protected BaseEncAction baseEncAction = null;

	boolean isExp = false;

	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel71 = null;

	private JLabel jLabel72 = null;

	private JTextField tfCode = null;

	private JTextField tfDate = null;

	private JTextField tfTraffic = null;

	private Boolean isOverPrint = false; // @jve:decl-index=0:

	private int projectType;

	private Double process = 0d;// 加工费 // @jve:decl-index=0:

	private BaseCustomsDeclaration baseCustomsDeclaration = null; // @jve:decl-index=0:

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JTextField tfSendCompay = null;

	private JTextField tfReceiveCompay = null;

	protected JComboBox cbbTransac = null;

	protected JTextField tfDomesticDestinationOrSource = null;

	protected boolean isImport = false; // 标识进口发票

	protected String area = null; // 口岸，属于进口发票弹出框的

	protected String dealWay = null; // 成交方式，属于进口发票弹出框的

	private JLabel lbPayMode = null;

	private JTextField tfPayMode = null;
	
	private Boolean isOk = null;

	public Boolean getIsOk() {
		return isOk;
	}

	public void setIsOk(Boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * This is the default constructor
	 */
	public DgImportInvoiceMergerReportSet() {
		super();
		initialize();
	}

	public DgImportInvoiceMergerReportSet(boolean isImport) {
		super();
		this.setisImport(isImport);
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
		cbbTransac.setModel(CustomBaseModel.getInstance().getTransacModel());
		cbbTransac.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbTransac);
		if (baseCustomsDeclaration != null) {
			cbbTransac.setSelectedItem(baseCustomsDeclaration.getTransac());
		}
		initData();
	}

	@SuppressWarnings("deprecation")
	private void initData() {
		if (baseCustomsDeclaration != null && isImport) {
			tfTraffic.setText(baseCustomsDeclaration.getConveyance());
			tfDate.setText(baseCustomsDeclaration.getDeclarationDate()==null?"":baseCustomsDeclaration.getDeclarationDate()
					.toLocaleString());

			if (isExp) {
				tfSendCompay.setText(baseCustomsDeclaration.getTradeName());
				tfReceiveCompay
						.setText(baseCustomsDeclaration.getCustomer() == null ? ""
								: baseCustomsDeclaration.getCustomer()
										.getName());
			} else {
				tfSendCompay
						.setText(baseCustomsDeclaration.getCustomer() == null ? ""
								: baseCustomsDeclaration.getCustomer()
										.getName());
				tfReceiveCompay.setText(baseCustomsDeclaration.getTradeName());
			}
			tfCode.setText(baseCustomsDeclaration.getInvoiceCode());

			String source = baseCustomsDeclaration
					.getDomesticDestinationOrSource() == null ? ""
					: baseCustomsDeclaration.getDomesticDestinationOrSource()
							.getName();

			tfDomesticDestinationOrSource.setText(!"".equals(source) ? (source
					.getBytes().length > 2 ? source.substring(0, 2) : source)
					: "");
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("打印数据设置");
		this.setSize(355, 352);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {

		if (jContentPane == null && !isImport) {
			lbPayMode = new JLabel();
			lbPayMode.setBounds(new Rectangle(28, 243, 61, 22));
			lbPayMode.setText("付款方式");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(28, 212, 61, 22));
			jLabel3.setText("成交方式");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(28, 181, 65, 22));
			jLabel2.setText("港口地");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(28, 150, 61, 22));
			jLabel1.setText("收货公司");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(28, 119, 61, 22));
			jLabel.setText("发货公司");
			jLabel72 = new JLabel();
			jLabel72.setBounds(new Rectangle(28, 26, 61, 22));
			jLabel72.setText("运输工具");
			jLabel71 = new JLabel();
			jLabel71.setBounds(new Rectangle(28, 57, 61, 22));
			jLabel71.setText("开票日期");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(28, 88, 61, 22));
			jLabel7.setText("发票号码");
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel71, null);
			jContentPane.add(jLabel72, null);
			jContentPane.add(getTfCode(), null);
			jContentPane.add(getTfDate(), null);
			jContentPane.add(getTfTraffic(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getTfSendCompay(), null);
			jContentPane.add(getTfReceiveCompay(), null);
			jContentPane.add(getCbbTransac(), null);
			jContentPane.add(getTfDomesticDestinationOrSource(), null);

			jContentPane.add(lbPayMode, null);
			jContentPane.add(getTfPayMode(), null);
		} else if (jContentPane == null && isImport) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(28, 28, 65, 22));
			jLabel2.setText("港口地");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(28, 65, 61, 22));
			jLabel3.setText("成交方式");

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);

			jContentPane.add(getCbbTransac(), null);
			jContentPane.add(getTfDomesticDestinationOrSource(), null);

			this.setSize(355, 190);
		}

		return jContentPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("打印");

			if (!this.isImport) {
				jButton.setBounds(59, 280, 90, 21);
				jButton.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						setIsOk(true);
						List<BaseCustomsDeclarationCommInfo> list = baseEncAction
								.findCustomsDeclarationsForPrint(new Request(
										CommonVars.getCurrUser()), tfTraffic
										.getText());
						BaseCustomsDeclaration customsDeclaration = null;
						if (list.size() > 0) {
							customsDeclaration = list.get(0)
									.getBaseCustomsDeclaration();
						}else{
							JOptionPane.showMessageDialog(DgImportInvoiceMergerReportSet.this, "运输工具不存在","提示",JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						CustomReportDataSource ds = new CustomReportDataSource(
								list);
						try {
							InputStream commInfoReportStream = null;
							commInfoReportStream = getClass()
									.getResourceAsStream(
											"ImportInvoiceMergerReport.jasper");
							// 发票日期，如果有如入日期则取该日期，如果没有就取报关单的申报日期
							String declarationDate = CommonUtils.getDate(
									customsDeclaration.getDeclarationDate(),
									"yyyy-MM-dd");

							double pieces = 0;
							double totalNums = 0;
							double netWeights = 0;
							double grossWeights = 0;
							double totalMoney = 0;
							String emsHeadH2k = null;
							// 付款方式
							String payMode = tfPayMode.getText(); 
							BaseCustomsDeclarationCommInfo commInfo = null;
							for (int i = 0; list != null && i < list.size(); i++) {
								commInfo = list.get(i);
								if (commInfo.getGrossWeight() != null) {
									grossWeights = grossWeights
											+ commInfo.getGrossWeight();
								}
								if (commInfo.getNetWeight() != null) {
									netWeights = netWeights
											+ commInfo.getNetWeight();
								}
								if (commInfo.getPieces() != null) {
									pieces = pieces + commInfo.getPieces();
								}
								if (commInfo.getCommAmount() != null) {
									totalNums = totalNums
											+ commInfo.getCommAmount();
								}
								if (commInfo.getCommTotalPrice() != null) {
									totalMoney = totalMoney
											+ commInfo.getCommTotalPrice();
								}
								if (commInfo.getBaseCustomsDeclaration()
										.getEmsHeadH2k() != null) {
									if (emsHeadH2k == null) {
										emsHeadH2k = commInfo
												.getBaseCustomsDeclaration()
												.getEmsHeadH2k();
									} else if (!emsHeadH2k.contains(commInfo
											.getBaseCustomsDeclaration()
											.getEmsHeadH2k())) {
										emsHeadH2k = emsHeadH2k
												+ ","
												+ commInfo
														.getBaseCustomsDeclaration()
														.getEmsHeadH2k();
									}
								}
							}
							Map<String, Object> parameters = new HashMap<String, Object>();
							parameters.put("overprint",
									new Boolean(isOverPrint));
							parameters.put("isExport", true);
							parameters.put("pieces", CommonUtils
									.formatDoubleByDigit(pieces, 3));
							parameters.put("totalNums", CommonUtils
									.formatDoubleByDigit(totalNums, 3));
							parameters.put("grossWeights", CommonUtils
									.formatDoubleByDigit(grossWeights, 4));
							parameters.put("netWeights", CommonUtils
									.formatDoubleByDigit(netWeights, 4));
							parameters.put("totalMoney", CommonUtils
									.formatDoubleByDigit(totalMoney, 4));
							parameters.put("emsHeadH2k", emsHeadH2k);
							parameters.put("declarationDate", declarationDate);
							parameters.put("sendCompany", tfSendCompay
									.getText());
							parameters.put("receiveCompany", tfReceiveCompay
									.getText());
							parameters.put("tfCode", tfCode.getText());
							parameters.put("payMode", payMode);

							// 交易方式可以为空
							Object obj = getCbbTransac().getSelectedItem();
							String transacTemp = "";
							if (obj != null) {
								transacTemp = ((Transac) obj).getName();
							}
							parameters.put("transac", transacTemp);

							// 打印设置的港口地截取前两个中文
							String domesticDestinationOrSource = getTfDomesticDestinationOrSource()
									.getText();
							String doTemp = domesticDestinationOrSource;
							if (domesticDestinationOrSource.length() != 0
									&& domesticDestinationOrSource.length() >= 3) {
								doTemp = domesticDestinationOrSource.substring(
										0, 4);
							}
							parameters.put("domesticDestinationOrSource",
									doTemp);

							JasperPrint jasperPrint = JasperFillManager
									.fillReport(commInfoReportStream,
											parameters, ds);
							DgReportViewer viewer = new DgReportViewer(
									jasperPrint);
							viewer.setVisible(true);
						} catch (JRException e1) {
							e1.printStackTrace();
						}
					}
				});
			} else {
				
				jButton.setBounds(59, 120, 90, 21);
				jButton.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						setIsOk(true);
						setArea(tfDomesticDestinationOrSource.getText().trim());
						if (getCbbTransac().getSelectedItem() != null) {
							setDealWay(((Transac) getCbbTransac()
									.getSelectedItem()).getName());
						} else {
							setDealWay("");
						}
						dispose();
					}
				});
			}
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
			if (!this.isImport) {
				jButton1.setBounds(189, 280, 90, 21);
			} else {
				jButton1.setBounds(189, 120, 90, 21);
			}
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
					setIsOk(false);
				}
			});
		}
		return jButton1;
	}

	
	/**
	 * This method initializes tfCustomsEnvelopBillNo3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCode() {
		if (tfCode == null) {
			tfCode = new JTextField();
			tfCode.setBounds(new Rectangle(117, 88, 203, 23));
		}
		return tfCode;
	}

	/**
	 * This method initializes tfCustomsEnvelopBillNo4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDate() {
		if (tfDate == null) {
			tfDate = new JTextField();
			tfDate.setBounds(new Rectangle(117, 57, 203, 23));
		}
		return tfDate;
	}

	/**
	 * This method initializes tfCustomsEnvelopBillNo5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTraffic() {
		if (tfTraffic == null) {
			tfTraffic = new JTextField();
			tfTraffic.setBounds(new Rectangle(117, 26, 203, 23));
		}
		return tfTraffic;
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

	public Double getProcess() {
		return process;
	}

	public void setProcess(Double process) {
		this.process = process;
	}

	public boolean isExp() {
		return isExp;
	}

	public void setExp(boolean isExp) {
		this.isExp = isExp;
	}

	public Boolean getIsOverPrint() {
		return isOverPrint;
	}

	public MaterialManageAction getMaterialManageAction() {
		return materialManageAction;
	}

	public void setMaterialManageAction(
			MaterialManageAction materialManageAction) {
		this.materialManageAction = materialManageAction;
	}

	public BaseEncAction getBaseEncAction() {
		return baseEncAction;
	}

	public void setBaseEncAction(BaseEncAction baseEncAction) {
		this.baseEncAction = baseEncAction;
	}

	public BaseCustomsDeclaration getBaseCustomsDeclaration() {
		return baseCustomsDeclaration;
	}

	public void setBaseCustomsDeclaration(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		this.baseCustomsDeclaration = baseCustomsDeclaration;
	}

	public boolean isImport() {
		return isImport;
	}

	public void setisImport(boolean isImport) {
		this.isImport = isImport;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDealWay() {
		return dealWay;
	}

	public void setDealWay(String dealWay) {
		this.dealWay = dealWay;
	}

	/**
	 * This method initializes tfSendCompay
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSendCompay() {
		if (tfSendCompay == null) {
			tfSendCompay = new JTextField();
			tfSendCompay.setBounds(new Rectangle(117, 119, 203, 22));
		}
		return tfSendCompay;
	}

	/**
	 * This method initializes tfReceiveCompay
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfReceiveCompay() {
		if (tfReceiveCompay == null) {
			tfReceiveCompay = new JTextField();
			tfReceiveCompay.setBounds(new Rectangle(117, 150, 202, 22));
		}
		return tfReceiveCompay;
	}

	protected JComboBox getCbbTransac() {
		if (cbbTransac == null) {
			cbbTransac = new JComboBox();
			if (!this.isImport) {
				cbbTransac.setBounds(117, 212, 202, 22);
			} else {
				cbbTransac.setBounds(117, 65, 202, 22);
			}
		}
		return cbbTransac;
	}

	protected JTextField getTfDomesticDestinationOrSource() {
		if (tfDomesticDestinationOrSource == null) {
			tfDomesticDestinationOrSource = new JTextField();
			if (!this.isImport) {
				tfDomesticDestinationOrSource.setBounds(117, 181, 202, 22);
			} else {
				tfDomesticDestinationOrSource.setBounds(117, 28, 202, 22);
			}

		}
		return tfDomesticDestinationOrSource;
	}

	/**
	 * This method initializes tfPayMode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfPayMode() {
		if (tfPayMode == null) {
			tfPayMode = new JTextField();
			tfPayMode.setBounds(new Rectangle(117, 242, 202, 22));
		}
		return tfPayMode;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
