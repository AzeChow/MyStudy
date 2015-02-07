package com.bestway.client.custom.common.report;

import java.awt.Rectangle;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.custom.common.DgBaseImportCustomsDeclaration;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.MotorCode;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author fhz
 * 
 */
public class DgWExportCommodityEncasementReporLwjgtSet extends JDialogBase {
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

	private JLabel lbPayMode = null;

	private JLabel lbTransac = null;

	private JTextField tfPayMode = null;

	private JComboBox cbbTransac = null;

	private Boolean isOverPrint = false; // @jve:decl-index=0:

	private int projectType;

	private Double process = 0d;// 加工费 // @jve:decl-index=0:

	private BaseCustomsDeclaration baseCustomsDeclaration = null; // @jve:decl-index=0:

	private Map<String, Object> map = null;

	/**
	 * This is the default constructor
	 */
	public DgWExportCommodityEncasementReporLwjgtSet(Map<String, Object> prop) {
		super();
		this.map = prop;
		baseCustomsDeclaration = (BaseCustomsDeclaration) prop
				.get("CustomsDeclaration");
		baseEncAction = (BaseEncAction) prop.get("baseEncAction");
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
		cbbTransac.setModel(CustomBaseModel.getInstance().getTransacModel());
		cbbTransac.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbTransac);
		if (baseCustomsDeclaration != null) {
			cbbTransac.setSelectedItem(baseCustomsDeclaration.getTransac());
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
		this.setSize(284, 168);
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
			lbTransac = new JLabel();
			lbTransac.setBounds(new Rectangle(28, 20, 61, 22));
			lbTransac.setText("成交方式");
			lbPayMode = new JLabel();
			lbPayMode.setBounds(new Rectangle(28, 55, 61, 22));
			lbPayMode.setText("付款方式");
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(lbPayMode, null);
			jContentPane.add(lbTransac, null);
			jContentPane.add(getTfPayMode(), null);
			jContentPane.add(getCbbTransac(), null);
		}
		return jContentPane;
	}

	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(30, 90, 90, 21);
			jButton.setText("打印");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomReportDataSource ds = (CustomReportDataSource) map
							.get("CustomReportDataSource");
					ds.setFirst();
					BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) map
							.get("CustomsDeclaration");
					JTableListModel commInfoModel = (JTableListModel) map
							.get("commInfoModel");
					boolean noTaoda = !(Boolean) map.get("isTaoda");
					CustomsDeclarationSubReportDataSource
							.setSubReportData(commInfoModel.getList());
					CommonVars.setSumMoney(Double.valueOf(0));
					CommonVars.setBeforeSumMoney(Double.valueOf(0));
					Company comp = (Company) customsDeclaration.getCompany();
					try {
						InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/WExportCommodityEncasementReportLwjg.jasper");
						InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/WExportCommodityEncasementSubReportLwjg.jasper");
						String billNo4 = customsDeclaration.getBillListId(); // 报关清单号码
						String impexpno4 = baseEncAction.getImpExpNoByBillNo(
								new Request(CommonVars.getCurrUser()), billNo4);
						JasperReport commInfoReport = (JasperReport) JRLoader
								.loadObject(commInfoReportStream);
						
						// 提运单号
						String billOfLading = customsDeclaration
								.getBillOfLading();
						// 司机姓名
						String name;
						// 运输单位
						String trafficUnit;
						// 付款方式
						String payMode = tfPayMode.getText();
						// 成交方式
						String transac = cbbTransac.getSelectedIndex() > -1 ? ((Transac) (cbbTransac
								.getSelectedItem())).getName() : "";
						if (billOfLading == null) {
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
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("payMode", payMode);
						parameters.put("transac", transac);
						parameters.put("commInfoReport", commInfoReport);
						parameters.put("overprint", !noTaoda);
						parameters.put("isExport", true);
						parameters.put("impexpno", impexpno4);
						parameters.put("outTradeCo", comp.getOutTradeCo());// 外商公司
						parameters.put("name", name);
						parameters.put("trafficUnit", trafficUnit);
						parameters.put("curr", customsDeclaration.getCurrency()==null?"":
								customsDeclaration.getCurrency().getCurrSymb());// 币制
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

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(150, 90, 90, 21);
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
	 * This method initializes tfCustomsEnvelopBillNo4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPayMode() {
		if (tfPayMode == null) {
			tfPayMode = new JTextField();
			tfPayMode.setBounds(new Rectangle(117, 55, 130, 23));
		}
		return tfPayMode;
	}

	/**
	 * This method initializes tfCustomsEnvelopBillNo5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JComboBox getCbbTransac() {
		if (cbbTransac == null) {
			cbbTransac = new JComboBox();
			cbbTransac.setBounds(new Rectangle(117, 20, 130, 23));
		}
		return cbbTransac;
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
} // @jve:decl-index=0:visual-constraint="10,10"
