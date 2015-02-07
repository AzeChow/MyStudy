/*
 * Created on 2004-9-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.awt.Cursor;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcs.contractexe.entity.BcsExportInvoiceItem;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.ExportInvoiceItem;
import com.bestway.client.custom.common.report.CustomsDeclarationSubReportDataSource;
import com.bestway.common.Request;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseExportInvoiceItem;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author bsway
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgExportInvoiceItem extends JDialogBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7403764501653597288L;
	private JPanel					jContentPane				= null;
	private JPanel					jPanel						= null;
	private JButton					btnSave						= null;
	private JButton					btnPrint					= null;
	private JButton					btnCancel					= null;
	private JTextField				tfOpenAnAccountBank			= null;
	private JTextField				tfMakeInvoiceEmployeeName	= null;
	private JTextField				tfBankAccounts				= null;
	private JTextField				jTextField					= null;
	private JLabel					jLabel6						= null;
	private JTextField				jTextField1					= null;
	private JTextField				tfMemo						= null;
	private JComboBox				cbbPortOfLoadingOrUnloading	= null;

	private BaseExportInvoiceItem	baseExportInvoiceItem		= null;
	private BaseEncAction			baseEncAction				= null;
	private BaseCustomsDeclaration	customsDeclaration			= null;	// 出口报关单
	private boolean					overPrint					= false;
	private List					commInfoList				= null;

	/**
	 * @return Returns the baseEncAction.
	 */
	public BaseEncAction getBaseEncAction() {
		return baseEncAction;
	}

	public List getCommInfoList() {
		return commInfoList;
	}

	public void setCommInfoList(List commInfoList) {
		this.commInfoList = commInfoList;
	}

	/**
	 * @param baseEncAction
	 *            The baseEncAction to set.
	 */
	public void setBaseEncAction(BaseEncAction baseEncAction) {
		this.baseEncAction = baseEncAction;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgExportInvoiceItem() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(355, 275);
		this.setTitle("广东省出口商品发票项目");
		this.setContentPane(getJContentPane());
	}

	/**
	 * 显示
	 */
	public void setVisible(boolean isFlag) {
		if (isFlag) {
			this.initUiComponents();
			this.showData();
		}
		super.setVisible(isFlag);
	}

	/**
	 * @return Returns the overPrint.
	 */
	public boolean isOverPrint() {
		return overPrint;
	}

	/**
	 * @param overPrint
	 *            The overPrint to set.
	 */
	public void setOverPrint(boolean overPrint) {
		this.overPrint = overPrint;
	}

	/**
	 * @return Returns the customsDeclaration.
	 */
	public BaseCustomsDeclaration getCustomsDeclaration() {
		return customsDeclaration;
	}

	/**
	 * @param customsDeclaration
	 *            The customsDeclaration to set.
	 */
	public void setCustomsDeclaration(BaseCustomsDeclaration customsDeclaration) {
		this.customsDeclaration = customsDeclaration;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setToolTipText("载运货物驶运出境地的运输工具");
			jTextField.setBounds(75, 73, 220, 22);
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setToolTipText("最终目的国的港口,如有多个应全部填写");
			jTextField1.setBounds(75, 100, 257, 22);
		}
		return jTextField1;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel6 = new JLabel();
			jContentPane = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel5 = new JLabel();
			jContentPane.setLayout(null);
			jLabel.setBounds(23, 21, 51, 22);
			jLabel.setText("开户银行");
			jLabel1.setBounds(23, 151, 51, 22);
			jLabel1.setText("开票人");
			jLabel2.setBounds(23, 125, 51, 22);
			jLabel2.setText("转运港");
			jLabel3.setBounds(23, 177, 51, 22);
			jLabel3.setText("备注");
			jLabel4.setBounds(23, 47, 51, 22);
			jLabel4.setText("银行帐号");
			jLabel5.setBounds(23, 73, 51, 22);
			jLabel5.setText("运输工具");
			jLabel6.setBounds(23, 99, 51, 22);
			jLabel6.setText("目的港");
			jContentPane.add(getJPanel(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfOpenAnAccountBank(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfMakeInvoiceEmployeeName(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getTfBankAccounts(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getTfMemo(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getCbbPortOfLoadingOrUnloading(), null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getJTextField1(), null);
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
			jPanel.setBounds(-1, 217, 348, 31);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel.add(getBtnSave(), null);
			jPanel.add(getBtnPrint(), null);
			jPanel.add(getBtnCancel(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(129, 4, 61, 23);
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgExportInvoiceItem.this.setCursor(new Cursor(
							Cursor.WAIT_CURSOR));
					try {
						saveData();
						JOptionPane.showMessageDialog(null, "信息保存成功!!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (java.lang.Exception ex) {
						JOptionPane.showMessageDialog(null, "信息保存失败!!", "提示",
								JOptionPane.ERROR_MESSAGE);
					} finally {
						DgExportInvoiceItem.this.setCursor(new Cursor(
								Cursor.DEFAULT_CURSOR));
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
			btnPrint.setBounds(193, 4, 61, 23);
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgExportInvoiceItem.this.setCursor(new Cursor(
							Cursor.WAIT_CURSOR));
					printReport();
					DgExportInvoiceItem.this.setCursor(new Cursor(
							Cursor.DEFAULT_CURSOR));
				}

			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(257, 4, 61, 23);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgExportInvoiceItem.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes tfOpenAnAccountBank
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOpenAnAccountBank() {
		if (tfOpenAnAccountBank == null) {
			tfOpenAnAccountBank = new JTextField();
			tfOpenAnAccountBank.setBounds(75, 21, 222, 22);
		}
		return tfOpenAnAccountBank;
	}

	/**
	 * This method initializes tfMakeInvoiceEmployeeName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMakeInvoiceEmployeeName() {
		if (tfMakeInvoiceEmployeeName == null) {
			tfMakeInvoiceEmployeeName = new JTextField();
			tfMakeInvoiceEmployeeName.setBounds(75, 151, 220, 22);
		}
		return tfMakeInvoiceEmployeeName;
	}

	/**
	 * This method initializes tfBankAccounts
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBankAccounts() {
		if (tfBankAccounts == null) {
			tfBankAccounts = new JTextField();
			tfBankAccounts.setBounds(75, 47, 220, 22);
		}
		return tfBankAccounts;
	}

	/**
	 * This method initializes tfMemo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setBounds(75, 177, 220, 22);
		}
		return tfMemo;
	}

	/**
	 * This method initializes cbbPortOfLoadingOrUnloading
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbPortOfLoadingOrUnloading() {
		if (cbbPortOfLoadingOrUnloading == null) {
			cbbPortOfLoadingOrUnloading = new JComboBox();
			cbbPortOfLoadingOrUnloading.setBounds(75, 125, 220, 22);
		}
		return cbbPortOfLoadingOrUnloading;
	}

	/**
	 * 初始化组件
	 */
	private void initUiComponents() {
		// 初始化运输方式
		// this.cbbTransferMode.setModel(CustomBaseModel.getInstance()
		// .getTransfModel());
		// this.cbbTransferMode.setRenderer(CustomBaseRender.getInstance()
		// .getRender());
		// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbTransferMode);
		// 初始化装货港
		this.cbbPortOfLoadingOrUnloading.setModel(CustomBaseModel.getInstance()
				.getPortLinModel());
		this.cbbPortOfLoadingOrUnloading.setRenderer(CustomBaseRender
				.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbPortOfLoadingOrUnloading);
	}

	/**
	 * 设置出口商品发票项目
	 */
	private void setExportInvoiceItem() {
		List list = this.baseEncAction
				.findExportInvoiceItemByCustomsDeclarationId(new Request(
						CommonVars.getCurrUser()), this.customsDeclaration
						.getId());
		if (list.size() > 0) {
			if (list.get(0) instanceof BaseExportInvoiceItem) {
				this.baseExportInvoiceItem = (BaseExportInvoiceItem) list
						.get(0);
			}
		}
	}

	/**
	 * 显示数据
	 */
	private void showData() {
		this.setExportInvoiceItem();
		if (this.baseExportInvoiceItem != null) {
			if (this.baseExportInvoiceItem.getBankAccounts() != null) {
				this.tfBankAccounts.setText(this.baseExportInvoiceItem
						.getBankAccounts());
			} else {
				this.tfBankAccounts.setText("");
			}
			if (this.baseExportInvoiceItem.getOpenAnAccountBank() != null) {
				this.tfOpenAnAccountBank.setText(this.baseExportInvoiceItem
						.getOpenAnAccountBank());
			} else {
				this.tfOpenAnAccountBank.setText("");
			}
			if (this.baseExportInvoiceItem.getMakeInvoiceEmployeeName() != null) {
				this.tfMakeInvoiceEmployeeName
						.setText(this.baseExportInvoiceItem
								.getMakeInvoiceEmployeeName());
			} else {
				this.tfMakeInvoiceEmployeeName.setText("");
			}
			if (this.baseExportInvoiceItem.getMemo() != null) {
				this.tfMemo.setText(this.baseExportInvoiceItem.getMemo());
			} else {
				this.tfMemo.setText("");
			}
			if (this.baseExportInvoiceItem.getTransferMode() != null) {
				this.jTextField.setText(this.baseExportInvoiceItem
						.getTransferMode());
			} else {
				this.jTextField.setText("");
			}
			if (this.baseExportInvoiceItem.getPortDest() != null) {
				this.jTextField1.setText(this.baseExportInvoiceItem
						.getPortDest());
			} else {
				this.jTextField1.setText("");
			}
			this.cbbPortOfLoadingOrUnloading
					.setSelectedItem(this.baseExportInvoiceItem
							.getPortOfLoadingOrUnloading());
			// this.cbbTransferMode.setSelectedItem(this.exportInvoiceItem.getTransferMode());
		} else {
			this.cbbPortOfLoadingOrUnloading.setSelectedItem(null);
			// this.cbbTransferMode.setSelectedItem(null);
		}
	}

	/**
	 * 填充对象
	 */
	private void fillData() {
		if (baseExportInvoiceItem == null) {
			if (customsDeclaration instanceof CustomsDeclaration) {
				baseExportInvoiceItem = new ExportInvoiceItem();
			} else if (customsDeclaration instanceof BcsCustomsDeclaration) {
				baseExportInvoiceItem = new BcsExportInvoiceItem();
			} 
		}
		baseExportInvoiceItem.setBankAccounts(this.tfBankAccounts.getText());
		baseExportInvoiceItem.setCompany(CommonVars.getCurrUser().getCompany());
		baseExportInvoiceItem.setTransferMode(this.jTextField.getText());
		baseExportInvoiceItem.setPortDest(this.jTextField1.getText());
		baseExportInvoiceItem
				.setPortOfLoadingOrUnloading((PortLin) this.cbbPortOfLoadingOrUnloading
						.getSelectedItem());
		baseExportInvoiceItem
				.setMakeInvoiceEmployeeName(this.tfMakeInvoiceEmployeeName
						.getText());
		baseExportInvoiceItem.setMemo(this.tfMemo.getText());
		baseExportInvoiceItem.setOpenAnAccountBank(this.tfOpenAnAccountBank
				.getText());
		baseExportInvoiceItem.setBaseCustomsDeclaration(customsDeclaration);
	}

	/**
	 * 保存数据
	 */
	private void saveData() {
		this.fillData();
		this.baseEncAction.saveExportInvoiceItem(new Request(CommonVars
				.getCurrUser()), this.baseExportInvoiceItem);
	}

	/**
	 * 打印报表
	 * 
	 */
	private void printReport() {
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
			InputStream subReportStream = DgExportInvoiceItem.class
					.getResourceAsStream("report/ExportInvoiceItemCommodityReport.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("isOverPrint", Boolean.valueOf(isOverPrint()));
			parameters.put("subReport", subReport);
			if (baseExportInvoiceItem != null) {
				parameters.put("openAnAccountBank", baseExportInvoiceItem
						.getOpenAnAccountBank());
				parameters.put("bankAccounts", baseExportInvoiceItem
						.getBankAccounts());
				parameters.put("memo", baseExportInvoiceItem.getMemo());
				parameters.put("transferModeName", baseExportInvoiceItem
						.getTransferMode());
				parameters
						.put(
								"portOfLoadingOrUnloadingName",
								baseExportInvoiceItem
										.getPortOfLoadingOrUnloading() == null ? ""
										: baseExportInvoiceItem
												.getPortOfLoadingOrUnloading()
												.getName());
				parameters.put("portDest", baseExportInvoiceItem.getPortDest());
			}
			InputStream reportStream = DgExportInvoiceItem.class
					.getResourceAsStream("report/ExportInvoiceItemReport.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
			dgReportViewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

}
