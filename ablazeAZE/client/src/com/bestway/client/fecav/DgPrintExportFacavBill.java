/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.fecav;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.common.Request;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.fecav.entity.FecavBillStrike;
import com.bestway.fecav.entity.TempFecavBill;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgPrintExportFacavBill extends JDialogBase {

	private JPanel					jContentPane			= null;
	private JPanel					jPanel					= null;
	private JButton					btnPrint				= null;
	private JButton					btnCancel				= null;
	private FecavBillStrike			fecavBillStrike			= null;
	private JComboBox				cbbPrint				= null;
	private FecavAction				fecavAction				= null;
	private CommonBaseCodeAction	commonBaseCodeAction	= (CommonBaseCodeAction) CommonVars
																	.getApplicationContext()
																	.getBean(
																			"commonBaseCodeAction");

	private static int				ALL_REPORT_PAGE			= 0;
	private static int				FIRST_REPORT_PAGE		= 1;
	private static int				SECOND_REPORT_PAGE		= 2;
	private static int				THREE_REPORT_PAGE		= 3;

	/**
	 * This method initializes
	 */
	public DgPrintExportFacavBill() {
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
		this.setTitle("打印出口收汇核销档案信息登记表");
		this.setContentPane(getJContentPane());
		this.setSize(362, 153);

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
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(28, 22, 291, 57);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel.add(getCbbPrint(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setBounds(new java.awt.Rectangle(195, 89, 65, 24));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						printReport();
						DgPrintExportFacavBill.this.dispose();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(
								DgPrintExportFacavBill.this, "数据有错！！", "提示",
								JOptionPane.OK_OPTION);
						ex.printStackTrace();
					}
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
			btnCancel.setText("取消");
			btnCancel.setBounds(new java.awt.Rectangle(264, 89, 65, 24));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgPrintExportFacavBill.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes cbbPrint
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbPrint() {
		if (cbbPrint == null) {
			cbbPrint = new JComboBox();
			cbbPrint.setBounds(new java.awt.Rectangle(15, 17, 260, 24));
		}
		return cbbPrint;
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

	}

	/**
	 * 打印报表
	 * 
	 */
	private void printReport() throws Exception {
		int index = this.cbbPrint.getSelectedIndex();
		switch (index) {
		case 0:// 一式三联打印
			printByIndex(DgPrintExportFacavBill.ALL_REPORT_PAGE);
			break;
		case 1:// 第一联 (外汇局核留存联)
			printByIndex(DgPrintExportFacavBill.FIRST_REPORT_PAGE);
			break;
		case 2:// 第二联 (外汇局存联)
			printByIndex(DgPrintExportFacavBill.SECOND_REPORT_PAGE);
			break;
		case 3:// 第三联 (企业留存)
			printByIndex(DgPrintExportFacavBill.THREE_REPORT_PAGE);
			break;
		}
	}

	/**
	 * 打印报表
	 * 
	 * @throws Exception
	 */
	private void printByIndex(int index) throws Exception {
		Map<String, List<FecavBill>> map = this.fecavAction
				.getFecavBillMap(new Request(CommonVars.getCurrUser()));
		if (map.isEmpty()) {
			JOptionPane.showMessageDialog(DgPrintExportFacavBill.this,
					"没有列印的数据", "提示", JOptionPane.OK_OPTION);
			return;
		}
		InputStream masterReportStream = FmControlFecavBill.class
				.getResourceAsStream("report/ExportFecavCancelReport.jasper");
		InputStream attachReportStream = FmControlFecavBill.class
				.getResourceAsStream("report/ExportFecavCancelAttachReport.jasper");
		JasperReport masterReport = (JasperReport) JRLoader
				.loadObject(masterReportStream);
		JasperReport attachReport = (JasperReport) JRLoader
				.loadObject(attachReportStream);
		List<JasperPrint> allPrintReport = new ArrayList<JasperPrint>();
		//
		// 所有报表
		//
		if (index == DgPrintExportFacavBill.ALL_REPORT_PAGE) {
			for (int i = 0; i < 3; i++) {
				if (i == 0) {
					addJasperPrint(map, allPrintReport, masterReport,
							attachReport, "第一联 : 外汇局核留存联");
				} else if (i == 1) {
					addJasperPrint(map, allPrintReport, masterReport,
							attachReport, "第二联 : 外汇局存联");
				} else if (i == 2) {
//					map = this.fecavAction.getFecavBillMap(new Request(
//							CommonVars.getCurrUser()));
					addJasperPrint(map, allPrintReport, masterReport,
							attachReport, "第三联 : 企业留存");
				}
			}
		} else if (index == DgPrintExportFacavBill.FIRST_REPORT_PAGE) {
			addJasperPrint(map, allPrintReport, masterReport, attachReport,
					"第一联 : 外汇局核留存联");
		} else if (index == DgPrintExportFacavBill.SECOND_REPORT_PAGE) {
			addJasperPrint(map, allPrintReport, masterReport, attachReport,
					"第二联 : 外汇局存联");
		} else if (index == DgPrintExportFacavBill.THREE_REPORT_PAGE) {
			addJasperPrint(map, allPrintReport, masterReport, attachReport,
					"第三联 : 企业留存");
		}
		if (allPrintReport.size() <= 0) {
			return;
		}
		JasperPrint showJasperPrint = allPrintReport.get(0);
		for (int i = 1; i < allPrintReport.size(); i++) {
			JasperPrint jasperPrint = allPrintReport.get(i);
			int size = jasperPrint.getPages().size();
			for (int k = 0; k < size; k++) {
				showJasperPrint.addPage((JRPrintPage) jasperPrint.getPages()
						.get(k));
			}
		}
		//
		// 显示所有报表
		//
		DgReportViewer viewer = new DgReportViewer(showJasperPrint);
		viewer.setVisible(true);

	}

	/**
	 * 加入所有报表
	 * 
	 * @param map
	 * @param masterReport
	 * @param attachReport
	 * @return
	 * @throws JRException
	 */
	private void addJasperPrint(Map<String, List<FecavBill>> map,
			List<JasperPrint> allPrintReport, JasperReport masterReport,
			JasperReport attachReport, String attachInfo) throws JRException {
		Set set = map.keySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			List<FecavBill> dataSource = map.get(key);
			if (dataSource.size() <= 0) {
				continue;
			}
			List<FecavBill> onePage = new ArrayList<FecavBill>();
			List<TempFecavBill> twoPage = new ArrayList<TempFecavBill>();
			FecavBill value = null;
			double totalPrice = 0.0;
			for (int i = 0; i < dataSource.size(); i++) {
				if (i == 0) {
					value = dataSource.get(0);
					for (int k = 0; k < 51; k++) {
						if (k < dataSource.size()) {
							totalPrice += (dataSource.get(k).getTotalPrice() == null ? 0.0
									: dataSource.get(k).getTotalPrice());
						}
					}
				}
				if (i < 11) {
					onePage.add(dataSource.get(i));
					if (i == 10 || i == dataSource.size() - 1) {
						Map<String, Object> paramterMap = new HashMap<String, Object>();
						paramterMap.put("companyName", CommonVars.getCurrUser()
								.getCompany().getName());
						paramterMap.put("pageCount",
								dataSource.size() > 11 ? "2" : "1");
						paramterMap.put("currSymbol",
								value.getCurr() == null ? "" : value.getCurr()
										.getCurrSymb());
						paramterMap.put("exportAmount", totalPrice);
						paramterMap.put("attachInfo", attachInfo);
						CustomReportDataSource onePageDataSource = new CustomReportDataSource(
								onePage);
						JasperPrint masterJasperPrint = JasperFillManager
								.fillReport(masterReport, paramterMap,
										onePageDataSource);
						allPrintReport.add(masterJasperPrint);
					}
				} else if (i < 51) {
					if (i < 31) {
						TempFecavBill temp = new TempFecavBill();
						temp.setFecavBill1(dataSource.get(i));
						twoPage.add(temp);
					} else {
						TempFecavBill temp = twoPage.get(i - 31);
						temp.setFecavBill2(dataSource.get(i));
					}
					if (i == 50 || i == dataSource.size() - 1) {
						Map<String, Object> paramterMap = new HashMap<String, Object>();
						paramterMap.put("companyName", CommonVars.getCurrUser()
								.getCompany().getName());
						paramterMap.put("pageCount", "2");
						paramterMap.put("currSymbol",
								value.getCurr() == null ? "" : value.getCurr()
										.getCurrSymb());
						paramterMap.put("exportAmount", totalPrice);
						paramterMap.put("attachInfo", attachInfo);
						CustomReportDataSource twoPageDataSource = new CustomReportDataSource(
								twoPage);
						JasperPrint attachJasperPrint = JasperFillManager
								.fillReport(attachReport, paramterMap,
										twoPageDataSource);
						allPrintReport.add(attachJasperPrint);
					}
				}
				if (i == 50) {
					i = -1;
					onePage = new ArrayList<FecavBill>();
					twoPage = new ArrayList<TempFecavBill>();
					if(dataSource.size() > 51){
						dataSource = dataSource.subList(51,dataSource.size());
					}
//					for (int j = 50; j >= 0; j--) {
//						dataSource.remove(j);
//					}
					totalPrice = 0.0;
				}
			}
		}
	}

}
