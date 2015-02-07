package com.bestway.client.custom.common;


import com.bestway.bcs.client.contract.DgContract;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.ComponentOrientation;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.swing.JTextField;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgPrintInfo extends JDialogBase{

	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel7 = null;
	private JPanel jPanel2 = null;
	private JButton jButton = null;
	private JTextField tfInvoiceNo = null;
	private JTextField tfContractNo = null;
	private JTextField tfPoNo = null;
	private JTextField tfTerms = null;
	private JTextField tfPayment = null;
	private List printList = null;
	private JLabel jLabel8 = null;
	private JTextField tfBlNo = null;
	private JLabel jLabel9 = null;
	private JTextField tfMadeIn = null;
	private JLabel jLabel10 = null;
	private JLabel jLabel11 = null;
	private JTextField tfLoading = null;
	private JTextField tfDestination = null;
	private Boolean isOut = false;//进出口标志
	private ImpExpRequestBill impExpRequestBill = null;
	private JButton jButton1 = null;
	private JCalendarComboBox cbbIssuedDate = null;
    private Properties pro = null;
	/**
	 * This method initializes 
	 * 
	 */
	public DgPrintInfo(ImpExpRequestBill impExpRequestBill,List printList,Boolean isOut) {
		super();
		initialize();
		if(isOut){
			initOut();
		}else{
			initIn();
		}
		//初始化
		InputStream in = DgPrintInfo.this.getClass().getResourceAsStream("commercialInvoiceOfIn.properties");
		pro = new Properties();
		try {
			pro.load(in);
			getTfInvoiceNo().setText(pro.getProperty("invoiceNo"));
			getTfContractNo().setText(pro.getProperty("contractNo"));
			getTfPoNo().setText(pro.getProperty("poNo"));
			getTfBlNo().setText(pro.getProperty("blNo"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.printList = printList;
		this.isOut = isOut;
		this.impExpRequestBill = impExpRequestBill;
		this.setVisible(true);
	}
	
	/**
	 * 进口初始化
	 */
	private void initIn(){//TODO
		tfTerms.setText("CIF GUANG ZHOU,CHINA");
		tfPayment.setText("by T/T Net 90 Days");
		tfMadeIn.setText("KOREA");
		tfLoading.setText("INCHEON,KOREA");
		tfDestination.setText("GUANGZHOU,CHINA");
	}
	
	/**
	 * 出口初始化
	 */
    private void initOut(){
    	tfTerms.setText("CIF GUANG ZHOU,CHINA");
		tfPayment.setText("by T/T Net 90 Days");
		tfMadeIn.setText("CHINA");
		tfLoading.setText("GUANGZHOU,CHINA");
		tfDestination.setText(" INCHEON,KOREA");
    }
	
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(346, 462));
        this.setTitle("COMMERCIAL INVOICE");
        this.setContentPane(getJPanel());
        this.addWindowListener(new java.awt.event.WindowAdapter() {
        	public void windowClosing(java.awt.event.WindowEvent e) {
//        		System.out.println("关闭窗口...............");
        		pro.setProperty("invoiceNo", getTfInvoiceNo().getText().trim());
    			pro.setProperty("contractNo", getTfContractNo().getText().trim());
    			pro.setProperty("poNo", getTfPoNo().getText().trim());
    			pro.setProperty("blNo", getTfBlNo().getText().trim());
    			OutputStream out;
				try {
				    String path = DgPrintInfo.class.getResource("").getPath();
				    path.substring(1);
					out = new FileOutputStream(new File(path+"/commercialInvoiceOfIn.properties"));
					pro.store(out, "");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
    			
//    			System.out.println("关闭窗口...............");
        	}
        });
			
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel1(), BorderLayout.CENTER);
			jPanel.add(getJPanel2(), BorderLayout.SOUTH);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(2, 352, 137, 18));
			jLabel11.setText("PORT OF DESTINATION:");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(28, 318, 111, 18));
			jLabel10.setText("PORT OF LOADING:");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(42, 283, 51, 18));
			jLabel9.setText("MADE IN:");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(53, 156, 42, 18));
			jLabel8.setText("B/L NO:");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(45, 253, 48, 18));
			jLabel7.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel7.setText("付款方式");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(17, 235, 76, 18));
			jLabel6.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel6.setText("PAYMENT BY:");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(47, 206, 48, 18));
			jLabel5.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel5.setText("交货条件");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(-1, 189, 96, 18));
			jLabel4.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel4.setText("ELIVERY TERMS:");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(51, 118, 44, 18));
			jLabel3.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel3.setText("P/O NO:");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(5, 84, 90, 18));
			jLabel2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel2.setText("CONTRACT NO:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(13, 50, 82, 18));
			jLabel1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel1.setText("ISSUED DATE:");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(20, 16, 75, 18));
			jLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel.setText("INVOICE NO :");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(jLabel, null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(jLabel5, null);
			jPanel1.add(jLabel6, null);
			jPanel1.add(jLabel7, null);
			jPanel1.add(getTfInvoiceNo(), null);
			jPanel1.add(getTfContractNo(), null);
			jPanel1.add(getTfPoNo(), null);
			jPanel1.add(getTfTerms(), null);
			jPanel1.add(getTfPayment(), null);
			jPanel1.add(jLabel8, null);
			jPanel1.add(getTfBlNo(), null);
			jPanel1.add(jLabel9, null);
			jPanel1.add(getTfMadeIn(), null);
			jPanel1.add(jLabel10, null);
			jPanel1.add(jLabel11, null);
			jPanel1.add(getTfLoading(), null);
			jPanel1.add(getTfDestination(), null);
			jPanel1.add(getCbbIssuedDate(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new FlowLayout());
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
		}
		return jPanel2;
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
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(printList==null || printList.size()<=0){
						JOptionPane.showMessageDialog(DgPrintInfo.this, "没有数据,打印退出！");
						return;
					}
					try {
						CustomReportDataSource ds = new CustomReportDataSource(
								printList);
						Map<String, Object> parameters = new HashMap<String, Object>();
						//shipper
						ScmCoc coc = impExpRequestBill.getScmCoc();
						parameters.put("shipper", (coc==null?"":coc.getName()));
						parameters.put("address", (coc==null?"":coc.getAddre()));
						parameters.put("postCode", (coc==null?"":coc.getFlatCode()));
						parameters.put("tel", (coc==null?"":coc.getLinkTel()));
						parameters.put("fax", (coc==null?"":coc.getFax()));
						String dateStr = "";
						if(cbbIssuedDate.getDate()!=null){
						   DateFormat df = new SimpleDateFormat("dd-MMM-yyyy",Locale.US);
						   dateStr = df.format(cbbIssuedDate.getDate());
						}
						parameters.put("issuedDate", dateStr);
						parameters.put("invoiceNo", tfInvoiceNo.getText().trim());
						parameters.put("contractNo", tfContractNo.getText().trim());
						parameters.put("poNo", tfPoNo.getText().trim());
						parameters.put("blNo", tfBlNo.getText().trim());
						parameters.put("eliveryTerms", tfTerms.getText().trim());
						parameters.put("payment", tfPayment.getText().trim());
						parameters.put("madeIn", tfMadeIn.getText().trim());
						parameters.put("loading", tfLoading.getText().trim());
						parameters.put("destination", tfDestination.getText().trim());
						//图像
						InputStream header = DgPrintInfo.class
								.getResourceAsStream("report/head.jpg");
						parameters.put("header", header);
						InputStream footer = DgPrintInfo.class
								.getResourceAsStream("report/foot.jpg");
						parameters.put("footer", footer);
						InputStream masterReportStream = null;
						if(isOut){
							 masterReportStream = DgPrintInfo.class
							.getResourceAsStream("report/commercialInvoiceOfOut.jasper");
						}else{
							 masterReportStream = DgPrintInfo.class
							.getResourceAsStream("report/commercialInvoiceOfIn.jasper");
						}
						JasperPrint jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					}catch(Exception el){
						el.printStackTrace();
					}
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes tfInvoiceNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfInvoiceNo() {
		if (tfInvoiceNo == null) {
			tfInvoiceNo = new JTextField();
			tfInvoiceNo.setBounds(new Rectangle(101, 16, 210, 30));
		}
		return tfInvoiceNo;
	}

	/**
	 * This method initializes tfContractNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfContractNo() {
		if (tfContractNo == null) {
			tfContractNo = new JTextField();
			tfContractNo.setBounds(new Rectangle(101, 84, 210, 30));
		}
		return tfContractNo;
	}

	/**
	 * This method initializes tfPoNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfPoNo() {
		if (tfPoNo == null) {
			tfPoNo = new JTextField();
			tfPoNo.setBounds(new Rectangle(101, 118, 210, 30));
		}
		return tfPoNo;
	}

	/**
	 * This method initializes tfTerms	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfTerms() {
		if (tfTerms == null) {
			tfTerms = new JTextField();
			tfTerms.setBounds(new Rectangle(101, 189, 210, 37));
			
		}
		return tfTerms;
	}

	/**
	 * This method initializes tfPayment	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfPayment() {
		if (tfPayment == null) {
			tfPayment = new JTextField();
			tfPayment.setBounds(new Rectangle(101, 235, 210, 37));
			
		}
		return tfPayment;
	}

	/**
	 * This method initializes tfBlNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfBlNo() {
		if (tfBlNo == null) {
			tfBlNo = new JTextField();
			tfBlNo.setBounds(new Rectangle(101, 152, 209, 31));
		}
		return tfBlNo;
	}

	/**
	 * This method initializes tfMadeIn	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfMadeIn() {
		if (tfMadeIn == null) {
			tfMadeIn = new JTextField();
			tfMadeIn.setBounds(new Rectangle(100, 283, 213, 28));
			
		}
		return tfMadeIn;
	}

	/**
	 * This method initializes tfLoading	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfLoading() {
		if (tfLoading == null) {
			tfLoading = new JTextField();
			tfLoading.setBounds(new Rectangle(145, 318, 163, 30));
			
		}
		return tfLoading;
	}

	/**
	 * This method initializes tfDestination	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfDestination() {
		if (tfDestination == null) {
			tfDestination = new JTextField();
			tfDestination.setBounds(new Rectangle(146, 352, 161, 30));
			
		}
		return tfDestination;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("打印1");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if(printList==null || printList.size()<=0){
						JOptionPane.showMessageDialog(DgPrintInfo.this, "没有数据,打印退出！");
						return;
					}
					try {
						CustomReportDataSource ds = new CustomReportDataSource(
								printList);
						Map<String, Object> parameters = new HashMap<String, Object>();
						//shipper
						ScmCoc coc = impExpRequestBill.getScmCoc();
						parameters.put("shipper", (coc==null?"":coc.getName()));
						parameters.put("address", (coc==null?"":coc.getAddre()));
						parameters.put("postCode", (coc==null?"":coc.getFlatCode()));
						parameters.put("tel", (coc==null?"":coc.getLinkTel()));
						parameters.put("fax", (coc==null?"":coc.getFax()));
						String dateStr = "";
						if(cbbIssuedDate.getDate()!=null){
						   DateFormat df = new SimpleDateFormat("dd-MMM-yyyy",Locale.US);
						   dateStr = df.format(cbbIssuedDate.getDate());
						}
						parameters.put("issuedDate", dateStr);
						parameters.put("invoiceNo", tfInvoiceNo.getText().trim());
						parameters.put("contractNo", tfContractNo.getText().trim());
						parameters.put("poNo", tfPoNo.getText().trim());
						parameters.put("blNo", tfBlNo.getText().trim());
						parameters.put("eliveryTerms", tfTerms.getText().trim());
						parameters.put("payment", tfPayment.getText().trim());
						parameters.put("madeIn", tfMadeIn.getText().trim());
						parameters.put("loading", tfLoading.getText().trim());
						parameters.put("destination", tfDestination.getText().trim());
						//图像
						InputStream header = DgPrintInfo.class
								.getResourceAsStream("report/head.jpg");
						parameters.put("header", header);
						InputStream footer = DgPrintInfo.class
								.getResourceAsStream("report/foot.jpg");
						parameters.put("footer", footer);
						InputStream masterReportStream = null;
						if(isOut){
							 masterReportStream = DgPrintInfo.class
							.getResourceAsStream("report/packingListOfOut.jasper");
						}else{
							 masterReportStream = DgPrintInfo.class
							.getResourceAsStream("report/packingListOfIn.jasper");
						}
						JasperPrint jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					}catch(Exception el){
						el.printStackTrace();
					}
				
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes cbbIssuedDate	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getCbbIssuedDate() {
		if (cbbIssuedDate == null) {
			cbbIssuedDate = new JCalendarComboBox();
			cbbIssuedDate.setBounds(new Rectangle(101, 49, 118, 31));
		}
		return cbbIssuedDate;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
