/*
 * Created on 2005-5-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractstat;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractstat.action.ContractStatAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgImpExpScheduleDetail extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel2 = null;

	private JComboBox cbbImpExpType = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private boolean isImport;

	private Contract contract = null;

	private ContractStatAction contractStatAction = null;

	/**
	 * @return Returns the contract.
	 */
	public Contract getContract() {
		return contract;
	}

	/**
	 * @return Returns the isImport.
	 */
	public boolean isImport() {
		return isImport;
	}

	/**
	 * @param contract
	 *            The contract to set.
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
	}

	/**
	 * @param isImport
	 *            The isImport to set.
	 */
	public void setImport(boolean isImport) {
		this.isImport = isImport;
	}

	/**
	 * This is the default constructor
	 */
	public DgImpExpScheduleDetail() {
		super();
		initialize();
		contractStatAction = (ContractStatAction) CommonVars
				.getApplicationContext().getBean("contractStatAction");
	}
	
	public void setVisible(boolean b){
		if(b){
			initUIComponents();
			if(contract!=null){
				this.cbbBeginDate.setDate(contract.getBeginDate());
			}else{
				this.cbbBeginDate.setDate(null);
			}
			
		}
		super.setVisible(b);
	}
	
	private void initUIComponents() {
		// 初始化进出口类型
		this.cbbImpExpType.removeAllItems();
		if (isImport) {
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.DIRECT_IMPORT).toString(), "料件进口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.TRANSFER_FACTORY_IMPORT).toString(), "料件转厂"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
		} else {
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_EXPORT), "成品出口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_MATERIEL_EXPORT), "退料出口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REWORK_EXPORT), "返工复出"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.EQUIPMENT_IMPORT), "设备进口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_PORT_REPAIR), "退港维修"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.EQUIPMENT_BACK_PORT), "设备退港"));
		}
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		
		cbbImpExpType.setSelectedIndex(-1);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("料件执行进度[明细]");
		this.setSize(371, 214);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(27, 38, 91, 23);
			jLabel.setText("报关日期范围 从");
			jLabel1.setBounds(209, 38, 16, 22);
			jLabel1.setText("到");
			jLabel2.setBounds(28, 85, 83, 23);
			jLabel2.setText("进出口类型");
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getCbbBeginDate(), null);
			jContentPane.add(getCbbEndDate(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getCbbImpExpType(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(118, 38, 86, 22);
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(229, 38, 83, 22);
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(116, 84, 197, 23);
		}
		return cbbImpExpType;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(36, 137, 83, 26);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = new ArrayList();
					if (contract != null) {
						String impExpType = cbbImpExpType.getSelectedItem() == null ? ""
								: ((ItemProperty) cbbImpExpType
										.getSelectedItem()).getCode();
						list = contractStatAction.findImpExpScheduleDetail(
								new Request(CommonVars.getCurrUser()),
								isImport, impExpType, contract, cbbBeginDate
										.getDate(), cbbEndDate.getDate());
						if (list == null || list.size() <= 0) {
							JOptionPane.showMessageDialog(DgImpExpScheduleDetail.this,
									"没有数据可打印", "提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						CustomReportDataSource ds = new CustomReportDataSource(
								list);
						InputStream reportStream = null;
						if (isImport) {
							reportStream = DgImpExpScheduleDetail.class
									.getResourceAsStream("report/ImpScheduleDetail.jasper");
						} else {
							reportStream = DgImpExpScheduleDetail.class
									.getResourceAsStream("report/ExpScheduleDetail.jasper");
						}
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("contractNo", contract
								.getImpContractNo());
						parameters.put("emsNo", contract.getEmsNo());
						parameters
								.put("beginDate",
										cbbBeginDate.getDate() == null ? ""
												: (new SimpleDateFormat(
														"yyyy-MM-dd"))
														.format(cbbBeginDate
																.getDate()));
						parameters.put("endDate",
								cbbEndDate.getDate() == null ? ""
										: (new SimpleDateFormat("yyyy-MM-dd"))
												.format(cbbEndDate.getDate()));
						parameters
								.put("availabilityDate", contract
										.getEndDate() == null ? ""
										: (new SimpleDateFormat("yyyy-MM-dd"))
												.format(contract
														.getEndDate()));
						parameters.put("contractBeginDate", contract
								.getBeginDate() == null ? ""
								: (new SimpleDateFormat("yyyy-MM-dd"))
										.format(contract.getBeginDate()));
						parameters.put("companyName", contract.getCompany()
								.getName());
						JasperPrint jasperPrint;
						try {
							jasperPrint = JasperFillManager.fillReport(
									reportStream, parameters, ds);
							DgReportViewer viewer = new DgReportViewer(
									jasperPrint);
							viewer.setVisible(true);
						} catch (JRException e1) {
							e1.printStackTrace();
						}

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
			jButton1.setBounds(210, 137, 83, 26);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
