package com.bestway.bcus.client.cas.finance;


import java.awt.BorderLayout;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.MarginDetail;
import com.bestway.bcus.cas.entity.MarginMaster;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgFinanceMarginEdit extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private javax.swing.JLabel jLabel1 = new JLabel();
	
	private CasAction casAction = null;
	private JToolBar jToolBar = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private MarginMaster marginMaster = null;
	private MarginDetail marginDetail = null;
	private JTableListModel tableModelMaster = null;
	private JTableListModel tableModel = null;
	private List list = null;
	private DefaultFormatterFactory defaultFormatterFactory = null;   //  @jve:decl-index=0:parse
	private NumberFormatter numberFormatter = null;   //  @jve:decl-index=0:parse
	/**
	 * This is the default constructor
	 */
	public DgFinanceMarginEdit() {
		super();
		casAction = (CasAction) CommonVars.getApplicationContext()
	       .getBean( "casAction");
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("利润表");
		this.setSize(733, 541);
		this.setContentPane(getJContentPane());
		
	}
    
    
    public void setVisible(boolean isFlag){
        if(isFlag){
            marginMaster = (MarginMaster) tableModelMaster.getCurrentRow();
               list = casAction.findMarginDetail(new Request(CommonVars.getCurrUser()),marginMaster);
               if (list!=null && !list.isEmpty())
               {
                   initTable(list);
               } else {
                   initTable(new Vector());
               }
               initUI();
        }
        super.setVisible(isFlag);
    }
    
	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("项目", "item", 200));
						list.add(addColumn("行次", "times", 30));
						list.add(addColumn("本期数", "thisCount", 60));
						list.add(addColumn("本年累计数", "thisSumCount", 80));
						list.add(addColumn("上年同期累计数", "priveSumCount", 100));
						return list;
					}
				});
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private void initUI(){
		this.jLabel1.setText(CommonVars.dateToString(marginMaster.getReportDate()));
	}
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	/**

	 * This method initializes jSplitPane	

	 * 	

	 * @return javax.swing.JSplitPane	

	 */    
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(2);
			jSplitPane.setDividerLocation(58);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
		}
		return jSplitPane;
	}

	/**

	 * This method initializes jPanel	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBackground(java.awt.Color.white);
			jLabel.setBounds(247, 4, 240, 25);
			jLabel.setText("外商投资工业企业会计报表");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel2.setBounds(322, 27, 83, 14);
			jLabel2.setText("利 润 表");
			jLabel2.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jLabel1.setBounds(256, 38, 70, 16);
			jLabel1.setText("riqi");
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
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
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**

	 * This method initializes jToolBar	

	 * 	

	 * @return javax.swing.JToolBar	

	 */    
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
		}
		return jToolBar;
	}

	/**

	 * This method initializes jButton	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("修改");
			jButton.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					try{
					  DgFinanceMarginEditEdit dg = new DgFinanceMarginEditEdit();
					  dg.setTableModel(tableModel);
					  dg.setVisible(true);
					}
					finally{
					    list = casAction.findMarginDetail(new Request(CommonVars.getCurrUser()),marginMaster); 
						initTable(list);
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
			jButton1.setText("打印");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					if (list !=null && !list.isEmpty()){
					    CommonDataSource imgExgDS = new CommonDataSource(list);
					    List company = new Vector(); 
				    	company.add(CommonVars.getCurrUser().getCompany());
				    	CommonDataSource companyDS = new CommonDataSource(company);
				  
				    	InputStream masterReportStream = DgFinanceMarginEdit.class
			    			.getResourceAsStream("report/FinanceMarginReport.jasper");			
				    	InputStream detailReportStream = DgFinanceMarginEdit.class
					    	.getResourceAsStream("report/FinanceMarginReportSubb.jasper");
					try {
						JasperReport detailReport = (JasperReport)JRLoader.loadObject(detailReportStream);
						
						SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
						String defaultDate = bartDateFormat.format(marginMaster.getReportDate());
						
						Map parameters = new HashMap();
						parameters.put("imgExgDS",imgExgDS);
						parameters.put("detailReport",detailReport);
						parameters.put("riqi",defaultDate); 
						JasperPrint jasperPrint;
						jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, companyDS);
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
		return jButton1;
	}

	/**

	 * This method initializes jButton2	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("关闭");
			jButton2.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					DgFinanceMarginEdit.this.dispose();
				}
			});

		}
		return jButton2;
	}

	/**

	 * This method initializes jTable	

	 * 	

	 * @return javax.swing.JTable	

	 */    
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter() { 

				public void mouseClicked(java.awt.event.MouseEvent e) {    

					/*if (e.getClickCount()==1){
						setState();
						String yy = String.valueOf(DgFinanceMoneyFluxEdit.this.jTable.getTableHeader().getColumnModel().getSelectionModel().getLeadSelectionIndex());
						if (yy.equals("3")){
							moneyDetail = (MoneyDetail) tableModel.getCurrentRow();
							DgFinanceMoneyFluxEdit.this.jFormattedTextField.setText(doubleToStr(moneyDetail.getMoney1()));
							if (moneyDetail.getTimes1()!=null && !moneyDetail.getTimes1().equals("")){
							  moneyDetail.setMoney1(strToDouble(jFormattedTextField.getText()));
							  casAction.saveMoneyDetails(new Request(CommonVars.getCurrUser()),moneyDetail);
							  casAction.account(new Request(CommonVars.getCurrUser()),moneyDetail.getMoneyMaster(),moneyDetail.getTimes1().trim());	
							}
						} else if (yy.equals("6")){
							moneyDetail = (MoneyDetail) tableModel.getCurrentRow();
							DgFinanceMoneyFluxEdit.this.jFormattedTextField.setText(doubleToStr(moneyDetail.getMoney2()));
							if (moneyDetail.getTimes2()!=null && !moneyDetail.getTimes2().equals("")){
							  moneyDetail.setMoney2(strToDouble(jFormattedTextField.getText()));
							  casAction.saveMoneyDetails(new Request(CommonVars.getCurrUser()),moneyDetail);
							  casAction.account(new Request(CommonVars.getCurrUser()),moneyDetail.getMoneyMaster(),moneyDetail.getTimes2().trim());	
							}
						} else if (yy.equals("9")){
							moneyDetail = (MoneyDetail) tableModel.getCurrentRow();
							DgFinanceMoneyFluxEdit.this.jFormattedTextField.setText(doubleToStr(moneyDetail.getMoney3()));
							if (moneyDetail.getTimes3()!=null && !moneyDetail.getTimes3().equals("")){
							  moneyDetail.setMoney3(strToDouble(jFormattedTextField.getText()));
							  casAction.saveMoneyDetails(new Request(CommonVars.getCurrUser()),moneyDetail);
							  casAction.account(new Request(CommonVars.getCurrUser()),moneyDetail.getMoneyMaster(),moneyDetail.getTimes3().trim());	
							}
						}
					}*/
				}
			});

		}
		return jTable;
	}
	private Double strToDouble(String value) { //转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				//	return new Double("0");
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return (Double) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			//return new Double("0");
			return null;
		}
	}

	private String doubleToStr(Double value) { //转换doubleToStr 取数据
		try {
			if (value == null || value.doubleValue() == 0) {
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return getNumberFormatter().valueToString(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
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
		}
		return jScrollPane;
	}

	/**
	 * @return Returns the tableModelMaster.
	 */
	public JTableListModel getTableModelMaster() {
		return tableModelMaster;
	}
	/**
	 * @param tableModelMaster The tableModelMaster to set.
	 */
	public void setTableModelMaster(JTableListModel tableModelMaster) {
		this.tableModelMaster = tableModelMaster;
	}
	/**

	 * This method initializes defaultFormatterFactory	

	 * 	

	 * @return javax.swing.text.DefaultFormatterFactory	

	 */    
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**

	 * This method initializes numberFormatter	

	 * 	

	 * @return javax.swing.text.NumberFormatter	

	 */    
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}
	private void setState(){
		/*this.jFormattedTextField.setEditable(this.moneyDetail.get!=null && !this.jTextField4.getText().equals("") 
				&& !this.jTextField4.getText().equals("6") && !this.jTextField4.getText().equals("14") && !this.jTextField4.getText().equals("15") && !this.jTextField4.getText().equals("21"));
		this.jFormattedTextField1.setEditable(this.jTextField3.getText()!=null && !this.jTextField3.getText().equals("")
				&& !this.jTextField3.getText().equals("26") && !this.jTextField3.getText().equals("27") && !this.jTextField3.getText().equals("32") && !this.jTextField3.getText().equals("40") && !this.jTextField3.getText().equals("41"));
		this.jFormattedTextField2.setEditable(this.jTextField5.getText()!=null && !this.jTextField5.getText().equals(""));*/
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
