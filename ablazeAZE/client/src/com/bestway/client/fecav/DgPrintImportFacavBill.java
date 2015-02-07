/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.fecav;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.client.cas.specificontrol.DgFeeRate;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.fecav.report.FecavReportVars;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavBillStrike;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgPrintImportFacavBill extends JDialogBase {

	private JPanel					jContentPane			= null;
	private JPanel					jPanel					= null;
	private JTextField				tfTransactor			= null;
	private JButton					btnPrint				= null;
	private JButton					btnCancel				= null;
	private JLabel					jLabel1					= null;
	private JLabel					jLabel2					= null;
	private JTextField				tfEnterpriseNo			= null;
	private JTextField				tfTownship				= null;
	private JLabel					jLabel					= null;
	private JLabel					jLabel3					= null;
	private JTextField				tfTel					= null;
	private JLabel					jLabel4					= null;
	private JCalendarComboBox		cbbCancelDate			= null;
	private FecavBillStrike			fecavBillStrike			= null;
	private JComboBox				cbbPrint				= null;
	private FecavAction				fecavAction				= null;
	private JLabel					jLabel6					= null;
	private JButton					btnRate					= null;
	private MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
	.getApplicationContext().getBean("materialManageAction");

	private static int				ALL_REPORT_PAGE			= 0;
	private static int				FIRST_REPORT_PAGE		= 1;
	private static int				SECOND_REPORT_PAGE		= 2;
	private static int				THREE_REPORT_PAGE		= 3;
	private JLabel jLabel5 = null;
	private JTextField tfChecker = null;

	/**
	 * This method initializes
	 */
	public DgPrintImportFacavBill() {
		super();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
		initialize();
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("填写进口报关核查清单打印信息");
		this.setContentPane(getJContentPane());
		this.setSize(440, 269);

	}

	public FecavBillStrike getFecavBillStrike() {
		return fecavBillStrike;
	}

	public void setFecavBillStrike(FecavBillStrike fecavBillStrike) {
		this.fecavBillStrike = fecavBillStrike;
	}

	public void setVisible(boolean b) {
		if (b) {
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getBtnPrint(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getCbbPrint(), null);
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
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(206,84,51,23));
			jLabel5.setText("核查人员");
			jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(20,114,55,23));
			jLabel6.setText("修改汇率");
			jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(20,83,55,23));
			jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel4.setText("核查日期");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(206,50,51,23));
			jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel3.setText("联系电话");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(4,50,71,23));
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel.setText("企业经办人");
			jPanel = new JPanel();
			jLabel1 = new JLabel();
			jLabel2 = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(28, 22, 372, 155);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel1.setBounds(206, 18, 51, 23);
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel1.setText("所在镇区");
			jLabel2.setBounds(24, 18, 51, 23);
			jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel2.setText("企业编号");
			jPanel.add(getTfUnitWeight(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getTfTownship(), null);
			jPanel.add(getTfTransactor(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel3, null);
			jPanel.add(getTfTel(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getCbbCancelDate(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(getBtnRate(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getJTextField(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jFormattedTextField1
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JTextField getTfUnitWeight() {
		if (tfTransactor == null) {
			tfTransactor = new JTextField();
			tfTransactor.setBounds(76, 50, 99, 23);
		}
		return tfTransactor;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setBounds(266, 187, 65, 24);
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printReport();
					DgPrintImportFacavBill.this.dispose();

				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(335, 187, 65, 24);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgPrintImportFacavBill.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTownship() {
		if (tfEnterpriseNo == null) {
			tfEnterpriseNo = new JTextField();
			tfEnterpriseNo.setBounds(76, 18, 97, 23);
			tfEnterpriseNo.setBackground(java.awt.Color.white);
		}
		return tfEnterpriseNo;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTransactor() {
		if (tfTownship == null) {
			tfTownship = new JTextField();
			tfTownship.setBounds(257, 18, 97, 23);
			tfTownship.setBackground(java.awt.Color.white);
		}
		return tfTownship;
	}

	/**
	 * This method initializes cbbPrint
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbPrint() {
		if (cbbPrint == null) {
			cbbPrint = new JComboBox();
			cbbPrint.setBounds(new java.awt.Rectangle(28,186,219,24));
		}
		return cbbPrint;
	}

	/**
	 * This method initializes btnRate
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRate() {
		if (btnRate == null) {
			btnRate = new JButton();
			btnRate.setBounds(new java.awt.Rectangle(76,114,99,23));
			btnRate.setText("汇率设置");
			btnRate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgFeeRate dg = new DgFeeRate();
					dg.setVisible(true);
				}
			});
		}
		return btnRate;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTel() {
		if (tfTel == null) {
			tfTel = new JTextField();
			tfTel.setBounds(new java.awt.Rectangle(257,50,97,23));
		}
		return tfTel;
	}

	/**
	 * This method initializes cbbCancelDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbCancelDate() {
		if (cbbCancelDate == null) {
			cbbCancelDate = new JCalendarComboBox();
			cbbCancelDate.setBounds(new java.awt.Rectangle(76,83,99,23));
		}
		return cbbCancelDate;
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		// 打印
		cbbPrint.removeAllItems();
		cbbPrint.addItem("一式三联打印");
		cbbPrint.addItem("第一联 (外汇局核留存联)");
		cbbPrint.addItem("第二联 (外汇局存联)");
		cbbPrint.addItem("第三联 (企业留存)");
		cbbPrint.setUI(new CustomBaseComboBoxUI(250));
		cbbPrint.setSelectedIndex(0);
		//
		// 初始化企业编码
		//
		this.tfEnterpriseNo.setText(((Company) CommonVars.getCurrUser()
				.getCompany()).getCode());
	}

	/**
	 * 打印报表
	 * 
	 */
	private void printReport() {
		int index = this.cbbPrint.getSelectedIndex();
		switch (index) {
		case 0:// 一式三联打印
			printByIndex(DgPrintImportFacavBill.ALL_REPORT_PAGE);
			break;
		case 1:// 第一联 (外汇局核留存联)
			printByIndex(DgPrintImportFacavBill.FIRST_REPORT_PAGE);
			break;
		case 2:// 第二联 (外汇局存联)
			printByIndex(DgPrintImportFacavBill.SECOND_REPORT_PAGE);
			break;
		case 3:// 第三联 (企业留存)
			printByIndex(DgPrintImportFacavBill.THREE_REPORT_PAGE);
			break;
		}
	}

	/**
	 * 打印报表
	 * 
	 * @param index
	 */
	private void printByIndex(int index) {
		try {
			List list = new ArrayList();
			if (fecavBillStrike != null) {
				list = fecavAction.findBrikeImpCustomsDeclaration(new Request(
						CommonVars.getCurrUser()), fecavBillStrike);
			}
			List<JasperPrint> allPrintReport = new ArrayList<JasperPrint>();
			CustomReportDataSource ds = new CustomReportDataSource(list);
			InputStream masterReportStream = DgPrintImportFacavBill.class
					.getResourceAsStream("report/ImportFecavBillReport.jasper");
			JasperReport masterReport = (JasperReport) JRLoader
					.loadObject(masterReportStream);
			List rateList = materialManageAction.findCurrRate(new Request(
					CommonVars.getCurrUser()));
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("rateList", rateList);
			parameters.put("dataSource", list);
			parameters.put("enterpriseNo", this.tfEnterpriseNo.getText());
			parameters.put("township", this.tfTownship.getText());
			parameters.put("checker", this.tfChecker.getText());
			String cancelDate = "";
			if (this.cbbCancelDate.getDate() != null) {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				cancelDate = bartDateFormat
						.format(this.cbbCancelDate.getDate());
			}
			parameters.put("cancelDate", cancelDate);
			parameters.put("tel", this.tfTel.getText());
			parameters.put("transactor", this.tfTransactor.getText());
			//
			// 所有报表
			//
			if (index == DgPrintImportFacavBill.ALL_REPORT_PAGE) {
				for (int i = 0; i < 3; i++) {
					if (i == 0) {
						parameters.put("attachInfo", "第一联 : 外汇局核留存联");
					} else if (i == 1) {
						parameters.put("attachInfo", "第二联 : 外汇局存联");
					} else if (i == 2) {
						parameters.put("attachInfo", "第三联 : 企业留存");
					}
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							masterReport, parameters, new CustomReportDataSource(list));
					allPrintReport.add(jasperPrint);
				}
			} else if (index == DgPrintImportFacavBill.FIRST_REPORT_PAGE) {
				parameters.put("attachInfo", "第一联 : 外汇局核留存联");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReport, parameters, ds);
				allPrintReport.add(jasperPrint);
			} else if (index == DgPrintImportFacavBill.SECOND_REPORT_PAGE) {
				parameters.put("attachInfo", "第二联 : 外汇局存联");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReport, parameters, ds);
				allPrintReport.add(jasperPrint);
			} else if (index == DgPrintImportFacavBill.THREE_REPORT_PAGE) {
				parameters.put("attachInfo", "第三联 : 企业留存");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReport, parameters, ds);
				allPrintReport.add(jasperPrint);
			}

			//
			// 显示所有报表
			//
			if (allPrintReport.size() <= 0) {
				return;
			}
			JasperPrint showJasperPrint = allPrintReport.get(0);
			for (int i = 1; i < allPrintReport.size(); i++) {
				JasperPrint jasperPrint = allPrintReport.get(i);
				int size = jasperPrint.getPages().size();
				for (int k = 0; k < size; k++) {
					showJasperPrint.addPage((JRPrintPage) jasperPrint
							.getPages().get(k));
				}
			}
			DgReportViewer viewer = new DgReportViewer(showJasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (tfChecker == null) {
			tfChecker = new JTextField();
			tfChecker.setBounds(new java.awt.Rectangle(258,85,97,23));
		}
		return tfChecker;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
