package com.bestway.bcs.client.contractstat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.client.contractanalyse.DgPreCustomsDeclaration;
import com.bestway.bcs.client.contractanalyse.JContractList;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcs.contractstat.action.ContractStatAction;
import com.bestway.bcs.contractstat.entity.ImpMaterialStat;
import com.bestway.bcs.contractstat.entity.ImpExpCustomsDeclarationCommInfo;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.CommonUtils;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.manualdeclare.FmEmsHeadH2k;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.ReportControl;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

import javax.swing.JCheckBox;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JWindow;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.ComponentOrientation;

public class DgIoMoney extends JInternalFrameBase {

	private JPanel jPanel = null;
	private JToolBar jToolBar = null;
	private JPanel jPanel1 = null;
	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JLabel jLabel1 = null;
	private JTextField jTextField1 = null;
	private JLabel jLabel2 = null;
	private JTextField jTextField2 = null;
	private JLabel jLabel3 = null;
	private JTextField jTextField3 = null;
	private JLabel jLabel11 = null;
	private JTextField jTextField11 = null;
	private JLabel jLabel21 = null;
	private JTextField jTextField21 = null;
	private JTable jTable = null;
	private JLabel jLabel4 = null;
	private JCalendarComboBox cbbBeginDate = null;
	private JLabel jLabel41 = null;
	private JCalendarComboBox cbbBeginDate1 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton11 = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel7 = null;
	private JLabel jLabel8 = null;
	private JLabel jLabel9 = null;
	private JLabel jLabel10 = null;
	private JLabel jLabel12 = null;
	private JSeparator jSeparator3 = null;
	private JLabel jLabel51 = null;
	private JLabel jLabel71 = null;
	private JLabel jLabel81 = null;
	private JLabel jLabel91 = null;
	private JLabel jLabel101 = null;
	private JLabel jLabel121 = null;
	private JSeparator jSeparator4 = null;
	private JSeparator jSeparator41 = null;
	private JSeparator jSeparator24 = null;
	private JTextField jTextField4 = null;
	private JTextField jTextField5 = null;
	private JTextField jTextField6 = null;
	private JTextField jTextField7 = null;
	private JTextField jTextField8 = null;
	private JTextField jTextField41 = null;
	private JTextField jTextField51 = null;
	private JTextField jTextField52 = null;
	private JTextField jTextField53 = null;
	private JTextField jTextField54 = null;
	private ImpMaterialStat impMaterialStat = null;
	/**
	 * 统计报表
	 */
	private ContractStatAction contractStatAction = null;
	private Company company = null;
	private JLabel jLabel14 = null;
	private JLabel jLabel13 = null;
	private JSeparator jSeparator4112 = null;
	private JTextField jTextField9 = null;
	private JLabel label;
	private JSeparator separator;
	private JLabel label_1;
	private JSeparator separator_2;
	private JSeparator separator_3;
	private JSeparator separator_4;
	private JSeparator separator_5;
	private JSeparator separator_6;
	private JSeparator separator_7;
	private JLabel label_2;
	private JLabel label_3;
	private JTextField textField;
	private JSeparator separator_8;
	private JLabel label_4;
	private JLabel label_5;
	private JSeparator separator_9;
	private JLabel label_6;
	private JSeparator separator_10;
	private JSeparator separator_12;
	private JSeparator separator_14;
	private JSeparator separator_15;
	private JSeparator separator_16;
	private JLabel label_12;
	private JLabel label_14;
	private JLabel label_15;
	private JSeparator separator_17;
	private JTextField textField_1;
	private JLabel lbllbcom;
	private JLabel label_16;
	private JSeparator separator_18;
	private JSeparator separator_19;
	private JSeparator separator_1;
	private JSeparator separator_20;
	private JSeparator separator_21;
	private JLabel label_10;
	private JLabel label_11;
	private JSeparator separator_22;
	private JLabel label_13;
	private JLabel label_17;
	private JSeparator separator_11;
	private JLabel label_7;
	private JLabel label_8;
	/**
	 * This method initializes
	 * 
	 */
	public DgIoMoney() {
		super();
		initialize();
		this.setHelpId("company");
		contractStatAction = (ContractStatAction) CommonVars
				.getApplicationContext().getBean("contractStatAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(1252, 680));
		this.setContentPane(getJPanel());
		this.setTitle("统计进出口报关单总值(东莞寮步外经办用)");
		this.cbbBeginDate.setDate(CommonVars.getBeginDate());
		this.cbbBeginDate1.setDate(CommonVars.getEndDate(new Date()));

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel14 = new JLabel();
			jLabel14.setBounds(new Rectangle(19, 400, 798, 18));
			jLabel14
					.setText("注：请企业每月10号前报送上一月的统计表到寮步外经办邮箱．本表格的数值统计单位为万美元．联系人：林先生　　曾小姐 刘小姐");
			jLabel2 = new JLabel();
			jLabel2.setText("Email:");
			jLabel2.setBounds(new Rectangle(366, 3, 42, 18));
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJToolBar(), null);
			jPanel.add(jLabel14, null);
			jPanel.add(getJTextField9(), null);
			jPanel.add(getJSeparator24());
			jPanel.add(getJSeparator4());
			jLabel6 = new JLabel();
			jLabel6.setBounds(9, 70, 181, 88);
			jPanel.add(jLabel6);
			jLabel6.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel6.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel6.setText("企业名称");
			jLabel6.setComponentOrientation(ComponentOrientation.UNKNOWN);
			jLabel13 = new JLabel();
			jLabel13.setBounds(19, 200, 171, 88);
			jPanel.add(jLabel13);
			jLabel13.setText("");
			jPanel.add(getLabel());
			jPanel.add(getSeparator());
			jPanel.add(getLabel_1());
			jPanel.add(getJSeparator41());
			jLabel5 = new JLabel();
			jLabel5.setBounds(200, 111, 107, 53);
			jPanel.add(jLabel5);
			jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel5.setHorizontalTextPosition(SwingConstants.CENTER);
			jLabel5.setText("进口总值");
			jPanel.add(getJTextField4());
			jPanel.add(getJSeparator3());
			jPanel.add(getSeparator_2());
			jPanel.add(getSeparator_3());
			jPanel.add(getSeparator_4());
			jPanel.add(getSeparator_5());
			jPanel.add(getSeparator_6());
			jPanel.add(getSeparator_7());
			jPanel.add(getLabel_2());
			jPanel.add(getLabel_3());
			jPanel.add(getJTextField5());
			jPanel.add(getTextField());
			jPanel.add(getSeparator_8());
			jLabel9 = new JLabel();
			jLabel9.setBounds(530, 124, 82, 34);
			jPanel.add(jLabel9);
			jLabel9.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel9.setText("<html>深加工<br/>结转值</html>");
			jPanel.add(getLabel_4());
			jPanel.add(getLabel_5());
			jPanel.add(getSeparator_9());
			jPanel.add(getJTextField8());
			jPanel.add(getJTextField7());
			jPanel.add(getJTextField6());
			jPanel.add(getLabel_6());
			jPanel.add(getSeparator_10());
			jPanel.add(getSeparator_12());
			jPanel.add(getSeparator_14());
			jPanel.add(getSeparator_15());
			jPanel.add(getSeparator_16());
			jPanel.add(getLabel_12());
			jPanel.add(getLabel_14());
			jPanel.add(getLabel_15());
			jPanel.add(getSeparator_17());
			jPanel.add(getJTextField41());
			jPanel.add(getJTextField51());
			jPanel.add(getJTextField52());
			jPanel.add(getJTextField53());
			jPanel.add(getJTextField54());
			jPanel.add(getTextField_1());
			jPanel.add(getLbllbcom());
			jPanel.add(getLabel_16());
			jPanel.add(getSeparator_18());
			jPanel.add(getSeparator_19());
			jPanel.add(getSeparator_1_1());
			jPanel.add(getSeparator_20());
			jPanel.add(getSeparator_21());
			jPanel.add(getLabel_10_1());
			jPanel.add(getLabel_11_1());
			jPanel.add(getSeparator_22());
			jPanel.add(getLabel_13_1());
			jPanel.add(getLabel_17());
			jPanel.add(getSeparator_11_1());
			jPanel.add(getLabel_7_1());
			jPanel.add(getLabel_8_1());
			jLabel6.setVisible(true);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setBounds(new Rectangle(-1, 0, 830, 60));
			jToolBar.add(getJPanel1());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel41 = new JLabel();
			jLabel41.setBounds(new Rectangle(673, 1, 18, 22));
			jLabel41.setText("至:");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(519, 2, 32, 22));
			jLabel4.setText("日期:");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(366, 34, 42, 18));
			jLabel21.setText("Email:");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(176, 33, 58, 22));
			jLabel11.setText("\u8054\u7cfb\u7535\u8bdd:");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(5, 33, 42, 18));
			jLabel3.setText("联系人:");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(4, 2, 49, 22));
			jLabel.setText("填表人:");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(-1, 0, 830, 60));
			jPanel1.add(jLabel, null);
			jPanel1.add(getJTextField(), null);
			jPanel1.add(getJLabel1(), null);
			jPanel1.add(getJTextField1(), null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(getJTextField2(), null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(getJTextField3(), null);
			jPanel1.add(jLabel11, null);
			jPanel1.add(getJTextField11(), null);
			jPanel1.add(jLabel21, null);
			jPanel1.add(getJTextField21(), null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(getCbbBeginDate(), null);
			jPanel1.add(jLabel41, null);
			jPanel1.add(getCbbBeginDate1(), null);
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton1(), null);
			jPanel1.add(getJButton11(), null);
			jPanel1.add(getJSeparator4112(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(63, 2, 108, 22));
		}
		return jTextField;
	}

	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("联系电话:");
			jLabel1.setBounds(new Rectangle(176, 1, 58, 22));
		}
		return jLabel1;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new Rectangle(238, 2, 123, 22));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(new Rectangle(408, 2, 108, 22));
		}
		return jTextField2;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(new Rectangle(63, 34, 108, 18));
		}
		return jTextField3;
	}

	/**
	 * This method initializes jTextField11
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField11() {
		if (jTextField11 == null) {
			jTextField11 = new JTextField();
			jTextField11.setBounds(new Rectangle(238, 33, 123, 22));
		}
		return jTextField11;
	}

	/**
	 * This method initializes jTextField21
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField21() {
		if (jTextField21 == null) {
			jTextField21 = new JTextField();
			jTextField21.setBounds(new Rectangle(408, 33, 108, 22));
		}
		return jTextField21;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(555, 2, 112, 22));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes cbbBeginDate1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate1() {
		if (cbbBeginDate1 == null) {
			cbbBeginDate1 = new JCalendarComboBox();
			cbbBeginDate1.setBounds(new Rectangle(690, 2, 112, 22));
		}
		return cbbBeginDate1;
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
			jButton.setBounds(new Rectangle(626, 27, 60, 28));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printSum();
				}
			});
		}
		return jButton;
	}
	/**
	 * 打印进出口报关单总值（寮步外经办用）
	 */
	private void printSum() {
		List<Object> list = new ArrayList<Object>();
		list.add("");
		CommonDataSource ds = new CommonDataSource(
				list);
		Company company = (Company) CommonVars.getCurrUser().getCompany();
		Calendar endClarendar = Calendar.getInstance();
		int month = endClarendar.get(Calendar.MONTH);
		try {			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fillName", company.getName());
			parameters.put("ImpdirectMoneywj",getJTextField5().getText());
			parameters.put("ImpdirectMoneywjOut",getTextField().getText());
			parameters.put("ImpSum", getJTextField4().getText());
			parameters.put("ImptransEMoneywj",getJTextField7().getText() );
			parameters.put("ImptransIMoneywj", getJTextField8().getText());
			parameters.put("tansImpSum", getJTextField6().getText());
			parameters.put("EmpSum", getJTextField41().getText());
			parameters.put("OutdirectMoneywj", getJTextField51().getText());
			parameters.put("OutdirectMoneywjOut", getTextField_1().getText());
			parameters.put("tansEmpSum", getJTextField52().getText());
			parameters.put("OuttransEMoneywj", getJTextField53().getText());
			parameters.put("OuttransIMoneywj", getJTextField54().getText());
			parameters.put("FillName1", getJTextField().getText());
			parameters.put("Filltel", getJTextField1().getText());
			parameters.put("FillEmail", getJTextField2().getText());
			parameters.put("LinkName", getJTextField3().getText());
			parameters.put("Linktel", getJTextField11().getText());
			parameters.put("LinkEmail", getJTextField21().getText());
			parameters.put("Year", CommonUtils.getYear());
			parameters.put("month",jTextField9.getText());
			InputStream emsMaterielStream = DgIoMoney.class
					.getResourceAsStream("report/ImpOutMoneyr.jasper");

			JasperPrint jasperPrint = JasperFillManager.fillReport(
					emsMaterielStream, parameters, ds);

			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(559, 27, 60, 28));
			jButton1.setText("查询");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Date beginDate = cbbBeginDate.getDate();
					Date endDate = cbbBeginDate1.getDate();
					ImpMaterialStat totalValue = contractStatAction
							.findIoMoneyValue(new Request(CommonVars
									.getCurrUser()), beginDate, endDate);
					fillContractStatTotalValue(totalValue);
					Company company = (Company) CommonVars.getCurrUser()
							.getCompany();
					String name = company.getName();
					jLabel13.setText("<html>"+name+"</html>");
					showData1();
				}
			});
		}
		return jButton1;
	}
	/**
	 *显示资料
	 */
	public void showData1(){
		Company company = (Company) CommonVars.getCurrUser()
		.getCompany();
		String fillname = company.getAppCusMan();
		String fillTol = company.getAppCusManTel();
		jTextField.setText(fillname);
		jTextField1.setText(fillTol);
		jTextField3.setText("林先生,曾小姐");
		jTextField11.setText("0769-83321502");
		jTextField21.setText("LBB8321502@163.com");
	}
	
	
//	public void fillData() {
//		impMaterialStat.setFillName(jTextField.getText() == null ? ""
//				: jTextField.getText());
//		impMaterialStat.setFillTel(jTextField1.getText() == null ? ""
//				: jTextField1.getText());
//		impMaterialStat.setFillEmail(jTextField2.getText() == null ? ""
//				: jTextField2.getText());
//		impMaterialStat.setLinkName(jTextField3.getText() == null ? ""
//				: jTextField3.getText());
//		impMaterialStat.setLinkTel(jTextField11.getText() == null ? ""
//				: jTextField11.getText());
//		impMaterialStat.setLinkEmail(jTextField21.getText() == null ? ""
//				: jTextField21.getText());
//
//	}
	/**
	 * 填充栏位
	 */
	public void fillContractStatTotalValue(ImpMaterialStat totalValue) {
		
		//黄埔关区内直接进口
		jTextField5.setText(totalValue.getImpdirectMoneywjIn() == null ? ""
				: CommonVars.formatDoubleToString(totalValue
						.getImpdirectMoneywjIn(), 999, 4));
		//黄埔关外内直接进口
		textField.setText(totalValue.getImpdirectMoneywjOut() == null ? ""
				: CommonVars.formatDoubleToString(totalValue
						.getImpdirectMoneywjOut(), 999, 4));
		jTextField7.setText(totalValue.getImptransEMoneywj() == null ? ""
				: CommonVars.formatDoubleToString(totalValue
						.getImptransEMoneywj(), 999, 4));
		jTextField8.setText(totalValue.getImptransIMoneywj() == null ? ""
				: CommonVars.formatDoubleToString(totalValue
						.getImptransIMoneywj(), 999, 4));
		
		//黄埔关区内直接出口
		jTextField51.setText(totalValue.getOutdirectMoneywjIn() == null ? ""
				: CommonVars.formatDoubleToString(totalValue
						.getOutdirectMoneywjIn(), 999, 4));
		
		//黄埔关外内直接出口
		textField_1.setText(totalValue.getOutdirectMoneywjOut() == null ? ""
				: CommonVars.formatDoubleToString(totalValue
						.getOutdirectMoneywjOut(), 999, 4));
		jTextField53.setText(totalValue.getOuttransEMoneywj() == null ? ""
				: CommonVars.formatDoubleToString(totalValue
						.getOuttransEMoneywj(), 999, 4));
		jTextField54.setText(totalValue.getOuttransIMoneywj() == null ? ""
				: CommonVars.formatDoubleToString(totalValue
						.getOuttransIMoneywj(), 999, 4));
		
		
		double ImpSjgO = (CommonVars.formatStrDouble(jTextField7.getText()));//转进口关区外
		double ImpSjgI = (CommonVars.formatStrDouble(jTextField8.getText()));//转进口关区内
		String ImpSum =  CommonVars.formatDoubleToString(ImpSjgO+ImpSjgI,999,4);//转厂总
		double Impdirect =(CommonVars.formatStrDouble(jTextField5.getText()))+
				(CommonVars.formatStrDouble(textField.getText())); //直接进口=黄埔关区内直接进口+黄埔关外内直接进口
		String Impall = (CommonVars.formatDoubleToString(Impdirect+ImpSjgO+ImpSjgI,999,4));
		jTextField6.setText(ImpSum);
		jTextField4.setText(Impall);
		double OutSjgO = (CommonVars.formatStrDouble(jTextField53.getText()));//转出口关区内
		double OutSjgI = (CommonVars.formatStrDouble(jTextField54.getText()));//转出口关区外
		String OutSum = CommonVars.formatDoubleToString(OutSjgO+OutSjgI,999,4);//转厂总
		double Outdirect =(CommonVars.formatStrDouble(jTextField51.getText()))+
				(CommonVars.formatStrDouble(textField_1.getText())); //直接出口=黄埔关区内直接出口+黄埔关外内直接出口
		String Outall = (CommonVars.formatDoubleToString(Outdirect+OutSjgO+OutSjgI,999,4));
		jTextField52.setText(OutSum);
		jTextField41.setText(Outall);
		String month = totalValue.getMonth();
		if(month!=null){
			if(month.length() == 10){
				jTextField9.setText (month.substring(5,6));
			}else{
				jTextField9.setText (month.substring(5));
			}
		}
		System.out.print("值"+ jTextField9.getText());
	}

	/**
	 * This method initializes jButton11
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton11() {
		if (jButton11 == null) {
			jButton11 = new JButton();
			jButton11.setBounds(new Rectangle(695, 27, 60, 28));
			jButton11.setText("关闭");
			jButton11.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton11;
	}

	/**
	 * This method initializes jSeparator3
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getJSeparator3() {
		if (jSeparator3 == null) {
			jSeparator3 = new JSeparator();
			jSeparator3.setBounds(193, 103, 637, 10);
		}
		return jSeparator3;
	}

	/**
	 * This method initializes jSeparator4
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getJSeparator4() {
		if (jSeparator4 == null) {
			jSeparator4 = new JSeparator();
			jSeparator4.setBounds(9, 70, 820, 10);
		}
		return jSeparator4;
	}

	/**
	 * This method initializes jSeparator41
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getJSeparator41() {
		if (jSeparator41 == null) {
			jSeparator41 = new JSeparator();
			jSeparator41.setBounds(193, 168, 637, 10);
		}
		return jSeparator41;
	}

	/**
	 * This method initializes jSeparator24
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getJSeparator24() {
		if (jSeparator24 == null) {
			jSeparator24 = new JSeparator();
			jSeparator24.setBounds(9, 70, 10, 397);
			jSeparator24.setOrientation(SwingConstants.VERTICAL);
		}
		return jSeparator24;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setBounds(200, 188, 106, 29);
			jTextField4.setEditable(false);
		}
		return jTextField4;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			jTextField5.setBounds(315, 188, 97, 31);
			jTextField5.setEditable(false);
		}
		return jTextField5;
	}

	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
			jTextField6.setBounds(524, 188, 94, 31);
			jTextField6.setEditable(false);
		}
		return jTextField6;
	}

	/**
	 * This method initializes jTextField7
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JTextField();
			jTextField7.setBounds(726, 188, 101, 31);
			jTextField7.setEditable(false);
		}
		return jTextField7;
	}

	/**
	 * This method initializes jTextField8
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField8() {
		if (jTextField8 == null) {
			jTextField8 = new JTextField();
			jTextField8.setBounds(626, 188, 95, 31);
			jTextField8.setEditable(false);
		}
		return jTextField8;
	}

	/**
	 * This method initializes jTextField41
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField41() {
		if (jTextField41 == null) {
			jTextField41 = new JTextField();
			jTextField41.setBounds(201, 343, 106, 31);
			jTextField41.setVisible(true);
			jTextField41.setEditable(false);
		}
		return jTextField41;
	}

	/**
	 * This method initializes jTextField51
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField51() {
		if (jTextField51 == null) {
			jTextField51 = new JTextField();
			jTextField51.setBounds(315, 343, 97, 31);
			jTextField51.setEditable(false);
		}
		return jTextField51;
	}

	/**
	 * This method initializes jTextField52
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField52() {
		if (jTextField52 == null) {
			jTextField52 = new JTextField();
			jTextField52.setBounds(524, 343, 94, 31);
			jTextField52.setEditable(false);
		}
		return jTextField52;
	}

	/**
	 * This method initializes jTextField53
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField53() {
		if (jTextField53 == null) {
			jTextField53 = new JTextField();
			jTextField53.setBounds(627, 343, 89, 31);
			jTextField53.setEditable(false);
		}
		return jTextField53;
	}

	/**
	 * This method initializes jTextField54
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField54() {
		if (jTextField54 == null) {
			jTextField54 = new JTextField();
			jTextField54.setBounds(728, 343, 97, 31);
			jTextField54.setEditable(false);
		}
		return jTextField54;
	}

	/**
	 * This method initializes jSeparator4112	
	 * 	
	 * @return javax.swing.JSeparator	
	 */
	private JSeparator getJSeparator4112() {
		if (jSeparator4112 == null) {
			jSeparator4112 = new JSeparator();
			jSeparator4112.setBounds(new Rectangle(0, 27, 812, 10));
		}
		return jSeparator4112;
	}

	/**
	 * This method initializes jTextField9	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField9() {
		if (jTextField9 == null) {
			jTextField9 = new JTextField();
			jTextField9.setVisible(false);
			jTextField9.setBounds(new Rectangle(830, 3, 27, 22));
		}
		return jTextField9;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel();
			label.setText("进口");
			label.setHorizontalTextPosition(SwingConstants.CENTER);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(new Rectangle(226, 20, 107, 53));
			label.setBounds(193, 70, 636, 31);
		}
		return label;
	}
	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
			separator.setOrientation(SwingConstants.VERTICAL);
			separator.setBounds(new Rectangle(224, 17, 8, 197));
			separator.setBounds(193, 70, 8, 321);
		}
		return separator;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel();
			label_1.setText("出口");
			label_1.setHorizontalTextPosition(SwingConstants.CENTER);
			label_1.setHorizontalAlignment(SwingConstants.CENTER);
			label_1.setBounds(new Rectangle(226, 20, 107, 53));
			label_1.setBounds(194, 233, 635, 31);
		}
		return label_1;
	}
	private JSeparator getSeparator_2() {
		if (separator_2 == null) {
			separator_2 = new JSeparator();
			separator_2.setOrientation(SwingConstants.VERTICAL);
			separator_2.setBounds(new Rectangle(335, 17, 8, 197));
			separator_2.setBounds(312, 103, 8, 129);
		}
		return separator_2;
	}
	private JSeparator getSeparator_3() {
		if (separator_3 == null) {
			separator_3 = new JSeparator();
			separator_3.setBounds(313, 135, 207, 10);
		}
		return separator_3;
	}
	private JSeparator getSeparator_4() {
		if (separator_4 == null) {
			separator_4 = new JSeparator();
			separator_4.setBounds(622, 135, 207, 10);
		}
		return separator_4;
	}
	private JSeparator getSeparator_5() {
		if (separator_5 == null) {
			separator_5 = new JSeparator();
			separator_5.setOrientation(SwingConstants.VERTICAL);
			separator_5.setBounds(new Rectangle(224, 17, 8, 197));
			separator_5.setBounds(520, 103, 8, 129);
		}
		return separator_5;
	}
	private JSeparator getSeparator_6() {
		if (separator_6 == null) {
			separator_6 = new JSeparator();
			separator_6.setBounds(192, 231, 637, 10);
		}
		return separator_6;
	}
	private JSeparator getSeparator_7() {
		if (separator_7 == null) {
			separator_7 = new JSeparator();
			separator_7.setOrientation(SwingConstants.VERTICAL);
			separator_7.setBounds(new Rectangle(224, 17, 8, 197));
			separator_7.setBounds(415, 135, 8, 96);
		}
		return separator_7;
	}
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel();
			label_2.setText("黄埔关区内");
			label_2.setHorizontalTextPosition(SwingConstants.CENTER);
			label_2.setHorizontalAlignment(SwingConstants.CENTER);
			label_2.setBounds(new Rectangle(226, 20, 107, 53));
			label_2.setBounds(632, 135, 81, 31);
		}
		return label_2;
	}
	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel();
			label_3.setText("黄埔关区外");
			label_3.setHorizontalTextPosition(SwingConstants.CENTER);
			label_3.setHorizontalAlignment(SwingConstants.CENTER);
			label_3.setBounds(new Rectangle(226, 20, 107, 53));
			label_3.setBounds(733, 135, 89, 31);
		}
		return label_3;
	}
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setEditable(false);
			textField.setBounds(419, 188, 98, 31);
		}
		return textField;
	}
	private JSeparator getSeparator_8() {
		if (separator_8 == null) {
			separator_8 = new JSeparator();
			separator_8.setOrientation(SwingConstants.VERTICAL);
			separator_8.setBounds(new Rectangle(224, 17, 8, 197));
			separator_8.setBounds(621, 103, 8, 129);
		}
		return separator_8;
	}
	private JLabel getLabel_4() {
		if (label_4 == null) {
			label_4 = new JLabel();
			label_4.setText("黄埔关区内");
			label_4.setHorizontalTextPosition(SwingConstants.CENTER);
			label_4.setHorizontalAlignment(SwingConstants.CENTER);
			label_4.setBounds(new Rectangle(226, 20, 107, 53));
			label_4.setBounds(312, 135, 101, 31);
		}
		return label_4;
	}
	private JLabel getLabel_5() {
		if (label_5 == null) {
			label_5 = new JLabel();
			label_5.setText("黄埔关区外");
			label_5.setHorizontalTextPosition(SwingConstants.CENTER);
			label_5.setHorizontalAlignment(SwingConstants.CENTER);
			label_5.setBounds(new Rectangle(226, 20, 107, 53));
			label_5.setBounds(415, 135, 105, 31);
		}
		return label_5;
	}
	private JSeparator getSeparator_9() {
		if (separator_9 == null) {
			separator_9 = new JSeparator();
			separator_9.setOrientation(SwingConstants.VERTICAL);
			separator_9.setBounds(new Rectangle(224, 17, 8, 197));
			separator_9.setBounds(723, 135, 8, 96);
		}
		return separator_9;
	}
	private JLabel getLabel_6() {
		if (label_6 == null) {
			label_6 = new JLabel();
			label_6.setText("出口总值");
			label_6.setHorizontalTextPosition(SwingConstants.CENTER);
			label_6.setHorizontalAlignment(SwingConstants.CENTER);
			label_6.setBounds(197, 271, 107, 53);
		}
		return label_6;
	}
	private JSeparator getSeparator_10() {
		if (separator_10 == null) {
			separator_10 = new JSeparator();
			separator_10.setOrientation(SwingConstants.VERTICAL);
			separator_10.setBounds(new Rectangle(335, 17, 8, 197));
			separator_10.setBounds(312, 262, 8, 129);
		}
		return separator_10;
	}
	private JSeparator getSeparator_12() {
		if (separator_12 == null) {
			separator_12 = new JSeparator();
			separator_12.setOrientation(SwingConstants.VERTICAL);
			separator_12.setBounds(new Rectangle(224, 17, 8, 197));
			separator_12.setBounds(415, 293, 8, 96);
		}
		return separator_12;
	}
	private JSeparator getSeparator_14() {
		if (separator_14 == null) {
			separator_14 = new JSeparator();
			separator_14.setOrientation(SwingConstants.VERTICAL);
			separator_14.setBounds(new Rectangle(224, 17, 8, 197));
			separator_14.setBounds(520, 262, 8, 129);
		}
		return separator_14;
	}
	private JSeparator getSeparator_15() {
		if (separator_15 == null) {
			separator_15 = new JSeparator();
			separator_15.setBounds(622, 293, 207, 10);
		}
		return separator_15;
	}
	private JSeparator getSeparator_16() {
		if (separator_16 == null) {
			separator_16 = new JSeparator();
			separator_16.setOrientation(SwingConstants.VERTICAL);
			separator_16.setBounds(new Rectangle(224, 17, 8, 197));
			separator_16.setBounds(621, 260, 8, 129);
		}
		return separator_16;
	}
	private JLabel getLabel_12() {
		if (label_12 == null) {
			label_12 = new JLabel();
			label_12.setText("直接进口");
			label_12.setHorizontalTextPosition(SwingConstants.CENTER);
			label_12.setHorizontalAlignment(SwingConstants.CENTER);
			label_12.setBounds(new Rectangle(226, 20, 107, 53));
			label_12.setBounds(317, 107, 137, 22);
		}
		return label_12;
	}
	private JLabel getLabel_14() {
		if (label_14 == null) {
			label_14 = new JLabel();
			label_14.setText("物流园/保税监管");
			label_14.setHorizontalTextPosition(SwingConstants.CENTER);
			label_14.setHorizontalAlignment(SwingConstants.CENTER);
			label_14.setBounds(new Rectangle(226, 20, 107, 53));
			label_14.setBounds(629, 103, 200, 22);
		}
		return label_14;
	}
	private JLabel getLabel_15() {
		if (label_15 == null) {
			label_15 = new JLabel();
			label_15.setText("直接出口");
			label_15.setHorizontalTextPosition(SwingConstants.CENTER);
			label_15.setHorizontalAlignment(SwingConstants.CENTER);
			label_15.setBounds(new Rectangle(226, 20, 107, 53));
			label_15.setBounds(317, 265, 137, 22);
		}
		return label_15;
	}
	private JSeparator getSeparator_17() {
		if (separator_17 == null) {
			separator_17 = new JSeparator();
			separator_17.setOrientation(SwingConstants.VERTICAL);
			separator_17.setBounds(new Rectangle(224, 17, 8, 197));
			separator_17.setBounds(830, 70, 8, 397);
		}
		return separator_17;
	}
	private JTextField getTextField_1() {
		if (textField_1 == null) {
			textField_1 = new JTextField();
			textField_1.setEditable(false);
			textField_1.setBounds(420, 343, 97, 31);
		}
		return textField_1;
	}
	private JLabel getLbllbcom() {
		if (lbllbcom == null) {
			lbllbcom = new JLabel();
			lbllbcom.setText("联系电话：83321502   电子邮箱：LB83321502@163.com");
			lbllbcom.setBounds(new Rectangle(10, 284, 798, 18));
			lbllbcom.setBounds(19, 437, 798, 18);
		}
		return lbllbcom;
	}
	private JLabel getLabel_16() {
		if (label_16 == null) {
			label_16 = new JLabel();
			label_16.setText("温馨提示：2014年每月仍需报进出口数，请保留并沿用此表，谢谢合作！");
			label_16.setForeground(Color.red);
			label_16.setBounds(new Rectangle(10, 284, 798, 18));
			label_16.setBounds(19, 476, 798, 18);
		}
		return label_16;
	}
	private JSeparator getSeparator_18() {
		if (separator_18 == null) {
			separator_18 = new JSeparator();
			separator_18.setBounds(9, 466, 820, 10);
		}
		return separator_18;
	}
	private JSeparator getSeparator_19() {
		if (separator_19 == null) {
			separator_19 = new JSeparator();
			separator_19.setBounds(9, 428, 820, 10);
		}
		return separator_19;
	}
	private JSeparator getSeparator_1_1() {
		if (separator_1 == null) {
			separator_1 = new JSeparator();
			separator_1.setBounds(193, 260, 637, 10);
		}
		return separator_1;
	}
	private JSeparator getSeparator_20() {
		if (separator_20 == null) {
			separator_20 = new JSeparator();
			separator_20.setBounds(192, 323, 637, 10);
		}
		return separator_20;
	}
	private JSeparator getSeparator_21() {
		if (separator_21 == null) {
			separator_21 = new JSeparator();
			separator_21.setBounds(9, 390, 820, 10);
		}
		return separator_21;
	}
	private JLabel getLabel_10_1() {
		if (label_10 == null) {
			label_10 = new JLabel();
			label_10.setText("黄埔关区外");
			label_10.setHorizontalTextPosition(SwingConstants.CENTER);
			label_10.setHorizontalAlignment(SwingConstants.CENTER);
			label_10.setBounds(new Rectangle(226, 20, 107, 53));
			label_10.setBounds(733, 293, 89, 31);
		}
		return label_10;
	}
	private JLabel getLabel_11_1() {
		if (label_11 == null) {
			label_11 = new JLabel();
			label_11.setText("黄埔关区内");
			label_11.setHorizontalTextPosition(SwingConstants.CENTER);
			label_11.setHorizontalAlignment(SwingConstants.CENTER);
			label_11.setBounds(new Rectangle(226, 20, 107, 53));
			label_11.setBounds(632, 293, 81, 31);
		}
		return label_11;
	}
	private JSeparator getSeparator_22() {
		if (separator_22 == null) {
			separator_22 = new JSeparator();
			separator_22.setOrientation(SwingConstants.VERTICAL);
			separator_22.setBounds(new Rectangle(224, 17, 8, 197));
			separator_22.setBounds(723, 293, 8, 96);
		}
		return separator_22;
	}
	private JLabel getLabel_13_1() {
		if (label_13 == null) {
			label_13 = new JLabel();
			label_13.setText("物流园/保税监管");
			label_13.setHorizontalTextPosition(SwingConstants.CENTER);
			label_13.setHorizontalAlignment(SwingConstants.CENTER);
			label_13.setBounds(new Rectangle(226, 20, 107, 53));
			label_13.setBounds(629, 266, 200, 22);
		}
		return label_13;
	}
	private JLabel getLabel_17() {
		if (label_17 == null) {
			label_17 = new JLabel();
			label_17.setText("<html>深加工<br/>结转值</html>");
			label_17.setHorizontalAlignment(SwingConstants.CENTER);
			label_17.setBounds(530, 274, 82, 34);
		}
		return label_17;
	}
	private JSeparator getSeparator_11_1() {
		if (separator_11 == null) {
			separator_11 = new JSeparator();
			separator_11.setBounds(313, 293, 207, 10);
		}
		return separator_11;
	}
	private JLabel getLabel_7_1() {
		if (label_7 == null) {
			label_7 = new JLabel();
			label_7.setText("黄埔关区内");
			label_7.setHorizontalTextPosition(SwingConstants.CENTER);
			label_7.setHorizontalAlignment(SwingConstants.CENTER);
			label_7.setBounds(new Rectangle(226, 20, 107, 53));
			label_7.setBounds(312, 293, 101, 31);
		}
		return label_7;
	}
	private JLabel getLabel_8_1() {
		if (label_8 == null) {
			label_8 = new JLabel();
			label_8.setText("黄埔关区外");
			label_8.setHorizontalTextPosition(SwingConstants.CENTER);
			label_8.setHorizontalAlignment(SwingConstants.CENTER);
			label_8.setBounds(new Rectangle(226, 20, 107, 53));
			label_8.setBounds(415, 293, 105, 31);
		}
		return label_8;
	}
} //  @jve:decl-index=0:visual-constraint="10,10"
