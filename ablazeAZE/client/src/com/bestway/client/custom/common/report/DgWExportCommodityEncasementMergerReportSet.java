package com.bestway.client.custom.common.report;

import java.awt.Rectangle;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.MotorCode;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author fhz
 * 
 */
public class DgWExportCommodityEncasementMergerReportSet extends JDialogBase {
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

	/**
	 * This is the default constructor
	 */
	public DgWExportCommodityEncasementMergerReportSet() {
		super();
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
	@SuppressWarnings("deprecation")
	private void initUIComponents() {
		if (baseCustomsDeclaration != null) {
			tfTraffic.setText(baseCustomsDeclaration.getConveyance());
			tfDate
					.setText(baseCustomsDeclaration.getDeclarationDate() == null ? ""
							: baseCustomsDeclaration.getDeclarationDate()
									.toLocaleString());
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
		this.setSize(355, 207);
		this.setContentPane(getJContentPane());
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
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
			jButton.setBounds(59, 130, 90, 21);
			jButton.setText("打印");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<BaseCustomsDeclarationCommInfo> list = baseEncAction
							.findCustomsDeclarationsForPrint(new Request(
									CommonVars.getCurrUser()), tfTraffic
									.getText());
					BaseCustomsDeclaration customsDeclaration = null;
					if (list == null || list.size() <= 0) {
						JOptionPane
								.showMessageDialog(
										DgWExportCommodityEncasementMergerReportSet.this,
										"运输工具在报关单中不存在", "提示",
										JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (list.size() > 0) {
						customsDeclaration = list.get(0)
								.getBaseCustomsDeclaration();
					}
					// 表体数据
					ArrayList<BaseCustomsDeclaration> headList = new ArrayList<BaseCustomsDeclaration>();
					headList.add(customsDeclaration);
					CustomReportDataSource ds = new CustomReportDataSource(
							headList);
					CustomsDeclarationSubReportDataSource
							.setSubReportData(list);
					try {
						InputStream masterReportStream = null;
						InputStream commInfoReportStream = null;
						masterReportStream = getClass()
								.getResourceAsStream(
										"WExportCommodityEncasementMergerReport.jasper");
						commInfoReportStream = getClass()
								.getResourceAsStream(
										"WExportCommodityEncasementMergerSubReport.jasper");
						String billOfLading = customsDeclaration
								.getBillOfLading();// 提运单号

						String name;// 司机姓名
						String trafficUnit;// 运输单位
						if (billOfLading == null || "".equals(billOfLading)) {
							name = "";
							trafficUnit = "";

						} else {
							MotorCode motor = materialManageAction
									.findMotorCodeByCode(new Request(CommonVars
											.getCurrUser()), billOfLading);
							if (motor == null) {
								name = "";
								trafficUnit = "";
							} else {
								name = motor.getName() == null ? "" : motor
										.getName();// 司机姓名
								trafficUnit = motor.getTrafficUnit() == null ? ""
										: motor.getTrafficUnit();// 运输单位
							}
						}
						String billNo5 = customsDeclaration.getBillListId(); // 报关清单号码
						String impexpno5 = baseEncAction.getImpExpNoByBillNo(
								new Request(CommonVars.getCurrUser()), billNo5);
						JasperReport commInfoReport = (JasperReport) JRLoader
								.loadObject(commInfoReportStream);

						double pieces = 0;
						double totalNums = 0;
						double netWeights = 0;
						double grossWeights = 0;
						String emsHeadH2k = null;
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
						String sendCompany = null;
						String receiveCompany = null;
						if (isExp) {
							sendCompany = baseCustomsDeclaration.getTradeName();
							receiveCompany = baseCustomsDeclaration
									.getCustomer() == null ? ""
									: baseCustomsDeclaration.getCustomer()
											.getName();
						} else {
							sendCompany = baseCustomsDeclaration.getCustomer() == null ? ""
									: baseCustomsDeclaration.getCustomer()
											.getName();
							receiveCompany = baseCustomsDeclaration
									.getTradeName();
						}
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("commInfoReport", commInfoReport);
						parameters.put("overprint", new Boolean(isOverPrint));
						parameters.put("isExp", isExp);
						parameters.put("impexpno", impexpno5);
						parameters.put("name", name);
						parameters.put("trafficUnit", trafficUnit);
						parameters
								.put("complexOrBoxNo", Boolean.valueOf(isExp));// 是否打开箱号还是商品编码
						parameters.put("projectType", String
								.valueOf(projectType));
						parameters.put("pieces", pieces + "");
						parameters.put("totalNums", totalNums + "");
						parameters.put("grossWeights", grossWeights + "");
						parameters.put("netWeights", netWeights + "");
						parameters.put("emsHeadH2k", emsHeadH2k);
						parameters.put("sendCompany", sendCompany);
						parameters.put("receiveCompany", receiveCompany);

						JasperPrint jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
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
			jButton1.setBounds(189, 129, 90, 21);
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

} // @jve:decl-index=0:visual-constraint="10,10"
