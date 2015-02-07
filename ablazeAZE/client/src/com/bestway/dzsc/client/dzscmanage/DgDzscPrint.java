/*
 * Created on 2004-7-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage;

import java.awt.Cursor;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.Request;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorWjHead;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscPrint extends JDialogBase {

	private JPanel				jContentPane			= null;
	private JButton				btnPrint				= null;
	private JButton				btnExit					= null;
	private JPanel				jPanel					= null;
	private JFormattedTextField	tfRequestNumber			= null;
	private JTextField			tfCard					= null;
	private JLabel				jLabel1					= null;
	private DzscAction			dzscAction				= null;
	private DzscEmsPorHead		head					= null;
	private DzscEmsPorWjHead 	EmsWJhead = null;	 // 头
	public static final int		MATERIER_BILL			= 0;
	public static final int		PRODUCT_BILL			= 1;
	public static final int		MATERIEL_BILL_CHANGE	= 2;
	public static final int		PRODUCT_BILL_CHANGE		= 3;
	public static final int		EMS_MATERIEL_BILL_CHANGE = 4;
	public static final int		EMS_PRODUCT_BILL_CHANGE = 5;
	public int					flag					= -1;

	/**
	 * This method initializes
	 * 
	 */
	public DgDzscPrint() {
		super();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("打印");
		this.setSize(305, 178);
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

	}

	/**
	 * 
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getBtnPrint(), null);
			jContentPane.add(getBtnExit(), null);
			jContentPane.add(getJPanel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes btnOK
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setBounds(122, 117, 64, 25);
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printReport();
					dispose();
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
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(189, 117, 61, 25);
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscPrint.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(13, 51, 66, 22));
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel1.setText("报关员证号");
			jPanel = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(22, 13, 246, 94);
			jLabel.setText("申请次数");
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel.setBounds(19, 22, 60, 22);
			jPanel.add(jLabel, null);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel.add(getTfRequestNumber(), null);
			jPanel.add(getTfCard(), null);
			jPanel.add(jLabel1, null);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes tfRequestNumber
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfRequestNumber() {
		if (tfRequestNumber == null) {
			tfRequestNumber = new JCustomFormattedTextField();
			tfRequestNumber.setBounds(new java.awt.Rectangle(82, 22, 138, 22));
		}
		return tfRequestNumber;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	/**
	 * This method initializes tfCard
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCard() {
		if (tfCard == null) {
			tfCard = new JTextField();
			tfCard.setBounds(new java.awt.Rectangle(82, 51, 138, 22));
		}
		return tfCard;
	}

	public DzscEmsPorHead getHead() {
		return head;
	}

	public void setHead(DzscEmsPorHead head) {
		this.head = head;
	}

	/**
	 * 报表打印
	 */
	private void printReport() {
		if (head == null && EmsWJhead == null) {
			JOptionPane.showMessageDialog(this, "请先保存通关备案记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		if(EmsWJhead == null && head == null){
			JOptionPane.showMessageDialog(this, "请先保存合同备案记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		switch (flag) {
		case DgDzscPrint.PRODUCT_BILL: // 出口成品备案清单
			try {
				String contractNo = "";
				String number = this.tfRequestNumber.getText();
				String card = this.tfCard.getText();
				List list = new ArrayList();
				if (head != null) {
					list = dzscAction.findEmsPorExgBill(new Request(CommonVars
							.getCurrUser()), head);
					contractNo = head.getImContractNo() == null ? "" : head
							.getImContractNo();
				}
				CustomReportDataSource ds = new CustomReportDataSource(list);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("emsNo", head.getEmsNo());
				parameters.put("companyName", head.getMachName());
				parameters.put("companyCode", head.getCompany() == null ? ""
						: ((Company) head.getCompany()).getCode());
				parameters.put("buName", head.getTradeName());
				parameters.put("contractNo", contractNo);
				//parameters.put("number", number);
				//parameters.put("card", card);
				parameters.put("currName", head.getCurr() == null ? "" : head
						.getCurr().getName());
				InputStream masterReportStream = DgDzscEmsPor.class
						.getResourceAsStream("report/ExportProductBillReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case DgDzscPrint.MATERIER_BILL: // 进口料件备案清单(新)
			try {
				String contractNo = "";
				String number = this.tfRequestNumber.getText();
				String card = this.tfCard.getText();
				List list = new ArrayList();
				if (head != null) {
					list = dzscAction.findEmsPorImgBill(new Request(CommonVars
							.getCurrUser()), head);
					contractNo = head.getIeContractNo() == null ? "" : head
							.getIeContractNo();
				}
				CustomReportDataSource ds = new CustomReportDataSource(list);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("emsNo", head.getEmsNo());
				parameters.put("companyName", head.getMachName());
				parameters.put("companyCode", head.getCompany() == null ? ""
						: ((Company) head.getCompany()).getCode());
				parameters.put("buName", head.getTradeName());
				parameters.put("totalPrice", head.getImgAmount());
				parameters.put("contractNo", contractNo);
				parameters.put("number", number);
				parameters.put("card", card);
				parameters.put("currName", head.getCurr() == null ? "" : head
						.getCurr().getName());
				InputStream masterReportStream = DgDzscEmsPor.class
						.getResourceAsStream("report/ImportMaterielBillReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case DgDzscPrint.PRODUCT_BILL_CHANGE: // 出口成品备案清单变更表
			try {
				String contractNo = "";
				String number = this.tfRequestNumber.getText();
				String card = this.tfCard.getText();
				List list = new ArrayList();
				List changeList = new ArrayList();
				if (head != null) {
					list = dzscAction.findEmsPorExgBill(new Request(CommonVars
							.getCurrUser()), head);
					contractNo = head.getIeContractNo() == null ? "" : head
							.getIeContractNo();
				}
				for(int i = 0;i < list.size(); i++){
					DzscEmsExgBill dzscEmsExgBill = (DzscEmsExgBill) list.get(i);
					if(dzscEmsExgBill.getModifyMark().equals(ModifyMarkState.MODIFIED) ||
							dzscEmsExgBill.getModifyMark().equals(ModifyMarkState.ADDED)){
						changeList.add(dzscEmsExgBill);
					}
				}
				CustomReportDataSource ds = new CustomReportDataSource(changeList);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("emsNo", head.getEmsNo());
				parameters.put("companyName", head.getMachName());
				parameters.put("companyCode", head.getCompany() == null ? ""
						: ((Company) head.getCompany()).getCode());
				parameters.put("buName", head.getTradeName());
				parameters.put("contractNo", contractNo);
				parameters.put("totalPrice", head.getImgAmount());
				parameters.put("number", number);
				parameters.put("card", card);
				parameters.put("currName", head.getCurr() == null ? "" : head
						.getCurr().getName());
				InputStream masterReportStream = DgDzscEmsPor.class
						.getResourceAsStream("report/ExportProductBillChangeReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case DgDzscPrint.MATERIEL_BILL_CHANGE: // 进口料件备案清单变更表
			try {
				String contractNo = "";
				String number = this.tfRequestNumber.getText();
				String card = this.tfCard.getText();
				List list = new ArrayList();
				List changeList = new ArrayList();
				if (head != null) {
					list = dzscAction.findEmsPorImgBill(new Request(CommonVars
							.getCurrUser()), head);
					contractNo = head.getIeContractNo() == null ? "" : head
							.getIeContractNo();
				}
				
				for(int i = 0;i < list.size(); i++){
					DzscEmsImgBill dzscEmsImgBill = (DzscEmsImgBill) list.get(i);
					if(dzscEmsImgBill.getModifyMark().equals(ModifyMarkState.MODIFIED) ||
							dzscEmsImgBill.getModifyMark().equals(ModifyMarkState.ADDED)){
						changeList.add(dzscEmsImgBill);
					}
				}
				CustomReportDataSource ds = new CustomReportDataSource(changeList);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("emsNo", head.getEmsNo());
				parameters.put("companyName", head.getMachName());
				parameters.put("companyCode", head.getCompany() == null ? ""
						: ((Company) head.getCompany()).getCode());
				parameters.put("buName", head.getTradeName());
				parameters.put("contractNo", contractNo);
				parameters.put("totalPrice", head.getImgAmount());
				parameters.put("number", number);				
				parameters.put("card", card);
				parameters.put("currName", head.getCurr() == null ? "" : head
						.getCurr().getName());
				InputStream masterReportStream = DgDzscEmsPor.class
						.getResourceAsStream("report/ImportMaterielBillChangeReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case DgDzscPrint.EMS_PRODUCT_BILL_CHANGE:
			try {
				String emsNo = "";
				String number = this.tfRequestNumber.getText();
				String card = this.tfCard.getText();
				List list = new ArrayList();
				List changeList = new ArrayList();
				if (EmsWJhead != null) {
					list = dzscAction.findEmsPorExgWj(new Request(CommonVars
							.getCurrUser()), EmsWJhead);
					emsNo = EmsWJhead.getIeContractNo() == null ? "" : EmsWJhead
							.getIeContractNo();
				}
				for(int i = 0;i < list.size(); i++){
					DzscEmsExgWj dzscEmsExgWj = (DzscEmsExgWj) list.get(0);
					if(dzscEmsExgWj.getModifyMark().equals(ModifyMarkState.MODIFIED)||
							dzscEmsExgWj.getModifyMark().equals(ModifyMarkState.ADDED)){
						changeList.add(dzscEmsExgWj);
					}
				}
				CustomReportDataSource ds = new CustomReportDataSource(changeList);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("emsNo", EmsWJhead.getEmsNo());
				parameters.put("companyName", EmsWJhead.getMachName());
				parameters.put("companyCode", EmsWJhead.getCompany() == null ? ""
						: ((Company) EmsWJhead.getCompany()).getCode());
				parameters.put("buName", EmsWJhead.getTradeName());
				parameters.put("emsNo", emsNo);
				parameters.put("totalPrice", EmsWJhead.getImgAmount());
				parameters.put("number", number);
				parameters.put("card", card);
				parameters.put("currName", EmsWJhead.getCurr() == null ? "" : EmsWJhead
						.getCurr().getName());
				InputStream masterReportStream = DgDzscEmsPor.class
						.getResourceAsStream("report/EMSExportProductBillChangeReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case DgDzscPrint.EMS_MATERIEL_BILL_CHANGE:
			try {
				String emsNo = "";
				String number = this.tfRequestNumber.getText();
				String card = this.tfCard.getText();
				List list = new ArrayList();
				List changeList = new ArrayList();
				if (EmsWJhead != null) {
					list = dzscAction.findEmsPorImgWj(new Request(CommonVars
							.getCurrUser()), EmsWJhead);
					emsNo = EmsWJhead.getIeContractNo() == null ? "" : EmsWJhead
							.getIeContractNo();
				}
				
				for(int i = 0;i < list.size(); i++){
					DzscEmsImgWj dzscEmsImgWj = (DzscEmsImgWj) list.get(i);
					if(dzscEmsImgWj.getModifyMark().equals(ModifyMarkState.MODIFIED)||
							dzscEmsImgWj.getModifyMark().equals(ModifyMarkState.ADDED)){
						System.out.println(dzscEmsImgWj.getSeqNum());
						changeList.add(dzscEmsImgWj);
					}
				}
				CustomReportDataSource ds = new CustomReportDataSource(changeList);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("emsNo", EmsWJhead.getEmsNo());
				parameters.put("companyName", EmsWJhead.getMachName());
				parameters.put("companyCode", EmsWJhead.getCompany() == null ? ""
						: ((Company) EmsWJhead.getCompany()).getCode());
				parameters.put("buName", EmsWJhead.getTradeName());
				parameters.put("emsNo", emsNo);
				parameters.put("totalPrice", EmsWJhead.getImgAmount());
				parameters.put("number", number);				
				parameters.put("card", card);
				parameters.put("currName", EmsWJhead.getCurr() == null ? "" : EmsWJhead
						.getCurr().getName());
				InputStream masterReportStream = DgDzscEmsPor.class
						.getResourceAsStream("report/EMSImportMaterielBillChangeReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		}

		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public DzscEmsPorWjHead getEmsWJhead() {
		return EmsWJhead;
	}

	public void setEmsWJhead(DzscEmsPorWjHead emsWJhead) {
		EmsWJhead = emsWJhead;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
