/*
 * Created on 2004-12-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ImpBackPortRequestBook;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.client.custom.common.report.CustomsDeclarationSubReportDataSource;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgImpBackPortRequestBook extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnSave = null;

	private JButton btnPrint = null;

	private JButton btnExit = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JTextField tfCompany = null;

	private JTextField tfYear = null;

	private JTextField tfBackFactoryExcuse = null;

	private JTextField tfMonth = null;

	private JTextField tfProductName = null;

	private JTextField tfDay = null;

	private JTextField tfContractFlag = null;

	private JTextField tfContractNo = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel12 = null;

	private JLabel jLabel13 = null;

	private ImpBackPortRequestBook impBackPortRequestBook = null;

	private ImpExpRequestBill impExpRequestBill = null;

	private EncAction encAction = null;

	private JTableListModel tableModel = null;

	/**
	 * This is the default constructor
	 */
	public DgImpBackPortRequestBook() {
		super();
		this.encAction = (EncAction) CommonVars.getApplicationContext()
				.getBean("encAction");
		initialize();
	}

	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			this.initComponents();
			this.showData();
		}
		super.setVisible(isFlag);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("退港申请书-退厂返工");
		this.setSize(649, 482);
		this.setContentPane(getJContentPane());
	}

	/**
	 * @return Returns the impExpRequestBill.
	 */
	public ImpExpRequestBill getImpExpRequestBill() {
		return impExpRequestBill;
	}

	/**
	 * @param impExpRequestBill
	 *            The impExpRequestBill to set.
	 */
	public void setImpExpRequestBill(ImpExpRequestBill impExpRequestBill) {
		this.impExpRequestBill = impExpRequestBill;
	}

	/**
	 * @return Returns the impBackPortRequestBook.
	 */
	public ImpBackPortRequestBook getImpBackPortRequestBook() {
		return impBackPortRequestBook;
	}

	/**
	 * @param impBackPortRequestBook
	 *            The impBackPortRequestBook to set.
	 */
	public void setImpBackPortRequestBook(
			ImpBackPortRequestBook impBackPortRequestBook) {
		this.impBackPortRequestBook = impBackPortRequestBook;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel13 = new JLabel();
			jLabel12 = new JLabel();
			jLabel11 = new JLabel();
			jLabel10 = new JLabel();
			jLabel9 = new JLabel();
			jLabel7 = new JLabel();
			jLabel6 = new JLabel();
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(14, 39, 32, 19);
			jLabel.setText("海关:");
			jLabel1.setBounds(41, 60, 29, 19);
			jLabel1.setText("兹有");
			jLabel2.setBounds(220, 83, 42, 19);
			jLabel2.setText("日出口");
			jLabel3.setBounds(14, 107, 41, 19);
			jLabel3.setText("产品因");
			jLabel4.setBounds(176, 83, 14, 19);
			jLabel4.setText("月");
			jLabel5.setBounds(539, 107, 81, 19);
			jLabel5.setText("原因,需退回我");
			jLabel6.setBounds(14, 130, 365, 19);
			jLabel6.setText("厂翻工好复出,请海关给于检验登记放行,翻工期限一个月.");
			jLabel7.setBounds(537, 83, 82, 19);
			jLabel7.setText("产品,其中表列");
			jLabel9.setBounds(475, 60, 9, 19);
			jLabel9.setText(")");
			jLabel10.setBounds(606, 60, 13, 19);
			jLabel10.setText("号");
			jLabel11.setBounds(134, 82, 13, 19);
			jLabel11.setText("年");
			jLabel12.setBounds(14, 82, 65, 19);
			jLabel12.setText("生产合同于");
			jLabel13.setBounds(368, 60, 23, 19);
			jLabel13.setText("厂(");
			jContentPane.add(getJToolBar(), null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getTfCompany(), null);
			jContentPane.add(getTfYear(), null);
			jContentPane.add(getTfBackFactoryExcuse(), null);
			jContentPane.add(getTfMonth(), null);
			jContentPane.add(getTfProductName(), null);
			jContentPane.add(getTfDay(), null);
			jContentPane.add(getTfContractFlag(), null);
			jContentPane.add(getTfContractNo(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel9, null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(jLabel13, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setBounds(0, 0, 639, 34);
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnExit());
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
					if (validateData() == true) {
						saveData();
					}
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					print();
				}
			});
		}
		return btnPrint;
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
					DgImpBackPortRequestBook.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
			jScrollPane.setBounds(0, 162, 642, 292);
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tfCompany
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCompany() {
		if (tfCompany == null) {
			tfCompany = new JTextField();
			tfCompany.setBounds(71, 60, 294, 19);
		}
		return tfCompany;
	}

	/**
	 * This method initializes tfYear
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfYear() {
		if (tfYear == null) {
			tfYear = new JTextField();
			tfYear.setBounds(80, 82, 52, 19);
		}
		return tfYear;
	}

	/**
	 * This method initializes tfBackFactoryExcuse
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBackFactoryExcuse() {
		if (tfBackFactoryExcuse == null) {
			tfBackFactoryExcuse = new JTextField();
			tfBackFactoryExcuse.setBounds(58, 107, 478, 19);
		}
		return tfBackFactoryExcuse;
	}

	/**
	 * This method initializes tfMonth
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMonth() {
		if (tfMonth == null) {
			tfMonth = new JTextField();
			tfMonth.setBounds(150, 83, 25, 19);
		}
		return tfMonth;
	}

	/**
	 * This method initializes tfProductName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfProductName() {
		if (tfProductName == null) {
			tfProductName = new JTextField();
			tfProductName.setBounds(263, 83, 272, 19);
		}
		return tfProductName;
	}

	/**
	 * This method initializes tfDay
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDay() {
		if (tfDay == null) {
			tfDay = new JTextField();
			tfDay.setBounds(193, 83, 25, 19);
		}
		return tfDay;
	}

	/**
	 * This method initializes tfContractFlag
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContractFlag() {
		if (tfContractFlag == null) {
			tfContractFlag = new JTextField();
			tfContractFlag.setBounds(393, 60, 80, 19);
		}
		return tfContractFlag;
	}

	/**
	 * This method initializes tfContractNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContractNo() {
		if (tfContractNo == null) {
			tfContractNo = new JTextField();
			tfContractNo.setBounds(487, 60, 114, 19);
		}
		return tfContractNo;
	}

	/**
	 * 打印
	 */
	private void print() {
		//
		// 并不保存数据
		//
		fillData();
		List list = new ArrayList();
		if (this.impBackPortRequestBook != null) {
			list.add(this.impBackPortRequestBook);
		}
		CustomReportDataSource ds = new CustomReportDataSource(list);
		CustomsDeclarationSubReportDataSource
				.setSubReportData(encAction
						.findImpExpCommodityInfo(new Request(CommonVars
								.getCurrUser())));
		try {
			InputStream subReportStream = DgImpBackPortRequestBook.this
					.getClass().getResourceAsStream(
							"report/ImpBackPortRequestBookSubReport.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			Map parameters = new HashMap();
			parameters.put("subReport", subReport);
			InputStream reportStream = DgImpBackPortRequestBook.this
					.getClass()
					.getResourceAsStream("report/ImpBackPortRequestBook.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
			dgReportViewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 初始化对象
	 */
	private void initComponents() {
		impBackPortRequestBook = this.encAction.findImpBackPortRequestBookById(
				new Request(CommonVars.getCurrUser()), this.impExpRequestBill
						.getId());
		if (impBackPortRequestBook == null) {
			impBackPortRequestBook = new ImpBackPortRequestBook();
			impBackPortRequestBook.setImpExpRequestBill(this.impExpRequestBill);
		}
	}

	/**
	 * 显示数据
	 */
	private void showData() {
		if (impBackPortRequestBook == null) {
			return;
		}
		if (impBackPortRequestBook.getBackFactoryExcuse() != null) {
			this.tfBackFactoryExcuse.setText(impBackPortRequestBook
					.getBackFactoryExcuse());
		} else {
			this.tfBackFactoryExcuse.setText("");
		}

		if (impBackPortRequestBook.getContractFlag() != null) {
			this.tfContractFlag.setText(impBackPortRequestBook
					.getContractFlag());
		} else {
			this.tfContractFlag.setText("");
		}

		if (impBackPortRequestBook.getContractNo() != null) {
			this.tfContractNo.setText(impBackPortRequestBook.getContractNo());
		} else {
			this.tfContractNo.setText("");
		}

		if (impBackPortRequestBook.getProductName() != null) {
			this.tfProductName.setText(impBackPortRequestBook.getProductName());
		} else {
			this.tfProductName.setText("");
		}

		if (impBackPortRequestBook.getImpExpRequestBill() != null) {
			if (impBackPortRequestBook.getImpExpRequestBill().getCompany() != null) {
				this.tfCompany.setText(impBackPortRequestBook
						.getImpExpRequestBill().getCompany().getName());
			} else {
				this.tfCompany.setText("");
			}
		} else {
			this.tfCompany.setText("");
		}

		if (impBackPortRequestBook.getYear() != null) {
			this.tfYear.setText(impBackPortRequestBook.getYear());
		} else {
			this.tfYear.setText("");
		}
		if (impBackPortRequestBook.getMonth() != null) {
			this.tfMonth.setText(impBackPortRequestBook.getMonth());
		} else {
			this.tfMonth.setText("");
		}
		if (impBackPortRequestBook.getDay() != null) {
			this.tfDay.setText(impBackPortRequestBook.getDay());
		} else {
			this.tfDay.setText("");
		}

		this.initTable();
	}

	/**
	 * 填充数据
	 * 
	 */
	private void fillData() {
		impBackPortRequestBook.setBackFactoryExcuse(this.tfBackFactoryExcuse
				.getText());
		impBackPortRequestBook
				.setCompany(CommonVars.getCurrUser().getCompany());
		impBackPortRequestBook.setContractFlag(this.tfContractFlag.getText());
		impBackPortRequestBook.setContractNo(this.tfContractNo.getText());
		impBackPortRequestBook.setProductName(this.tfProductName.getText());
		impBackPortRequestBook.setYear(this.tfYear.getText());
		impBackPortRequestBook.setMonth(this.tfMonth.getText());
		impBackPortRequestBook.setDay(this.tfDay.getText());
	}

	/**
	 * 验证数据
	 * 
	 */
	private boolean validateData() {
		if (!this.tfYear.getText().trim().equals("")) {
			try {
				Integer.parseInt(this.tfYear.getText());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "年份数字格式不对!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		if (!this.tfMonth.getText().trim().equals("")) {
			try {
				Integer.parseInt(this.tfMonth.getText());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "月份数字格式不对!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		if (!this.tfDay.getText().trim().equals("")) {
			try {
				Integer.parseInt(this.tfDay.getText());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "天数数字格式不对!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		return true;
	}

	/**
	 * 保存数据
	 */
	private void saveData() {
		this.fillData();
		impBackPortRequestBook = this.encAction.saveImpBackPortRequestBook(
				new Request(CommonVars.getCurrUser()),
				this.impBackPortRequestBook);
	}

	/**
	 * 初始化表
	 */
	private void initTable() {
		List list = this.encAction.findImpExpCommodityInfoByPutOnRecord(
				new Request(CommonVars.getCurrUser()),
				this.impBackPortRequestBook.getImpExpRequestBill().getId());
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("料号", "materiel.ptNo", 90));
						list.add(addColumn("名称", "materiel.factoryName", 120));
						list
								.add(addColumn("规格型号", "materiel.factorySpec",
										120));
						list.add(addColumn("单位", "materiel.calUnit.name", 60));
						list.add(addColumn("数量", "quantity", 60));
						list.add(addColumn("单价", "unitPrice", 60));
						list.add(addColumn("总金额", "amountPrice", 60));
						list.add(addColumn("毛重", "grossWeight", 60));
						list.add(addColumn("净重", "netWeight", 60));
						list.add(addColumn("体积", "bulks", 60));
						list.add(addColumn("制单号", "makeBillNo", 60));
						list.add(addColumn("版本号", "version", 60));
						return list;
					}
				});
	}
}