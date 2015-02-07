/*
 * Created on 2005-4-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import java.awt.BorderLayout;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.EmsAnalyHead;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.footer.JFooterTable;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
/**
 * @author xxm
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgEmsAnalyDiff extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JCalendarComboBox jCalendarComboBox = null;
	private JLabel jLabel5 = null;
	private JCalendarComboBox jCalendarComboBox1 = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JSplitPane jSplitPane = null;  //  @jve:decl-index=0:
	private JPanel jPanel7 = null;
	private JFooterTable jTable = null;
	private JFooterScrollPane jScrollPane = null;
	private JButton jButton3 = null;
	private JTableListModel tableModel = null;
	private JTableListModel tableModelCy = null; //差异分析
	private JTextField jTextField = null;
	private EmsAnalyHead head = null;
	private CheckCancelAction checkCancelAction = null;
	
	/**
	 * This is the default constructor
	 */
	public DgEmsAnalyDiff() {
		super();
		checkCancelAction = (CheckCancelAction) CommonVars
                 .getApplicationContext().getBean("checkCancelAction");
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(834, 550);
		this.setContentPane(getJContentPane());
		this.setTitle("报关材料/工厂实物帐面分析");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				if (tableModel.getCurrentRow() != null) {
					head = (EmsAnalyHead) tableModel.getCurrentRow();
				    fillWindow();
				    tableModelCy = EmsLogic.initTableCy(tableModelCy,jTable,head);
			}
			}
		});
	}
	private void fillWindow(){
		if (head != null){
		  this.jTextField.setText(String.valueOf(head.getEmsNo()));
		  this.jCalendarComboBox.setDate(head.getBeginDate());
		  this.jCalendarComboBox1.setDate(head.getEndDate());
		}
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
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
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jLabel.setText("报关材料/工厂实物帐面分析----差异分析");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setForeground(new java.awt.Color(0,102,51));
			jLabel1.setText("");
			jLabel1.setIcon(new ImageIcon(getClass().getResource("/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel2.setText("");
			jLabel2.setIcon(new ImageIcon(getClass().getResource("/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jPanel.setBackground(java.awt.Color.white);
			jPanel.add(jLabel, java.awt.BorderLayout.CENTER);
			jPanel.add(jLabel1, java.awt.BorderLayout.EAST);
			jPanel.add(jLabel2, java.awt.BorderLayout.WEST);
		}
		return jPanel;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel3.setText("核销流水号:");
			jLabel3.setBounds(5, 5, 74, 18);
			jLabel4.setText("起始日期");
			jLabel4.setBounds(165, 6, 52, 18);
			jLabel5.setText("截止日期");
			jLabel5.setBounds(320, 6, 52, 18);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getJCalendarComboBox(), null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getJCalendarComboBox1(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton2(), null);
			jPanel2.add(getJButton3(), null);
			jPanel2.add(getJTextField(), null);
		}
		return jPanel2;
	}
	/**
	 * This method initializes jCalendarComboBox	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */    
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(222, 6, 91, 22);
			jCalendarComboBox.setEnabled(false);
		}
		return jCalendarComboBox;
	}
	/**
	 * This method initializes jCalendarComboBox1	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */    
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.setBounds(376, 6, 91, 22);
			jCalendarComboBox1.setEnabled(false);
		}
		return jCalendarComboBox1;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("统计");
			jButton1.setBounds(478, 4, 80, 25);
			jButton1.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (tableModelCy.getRowCount()>0 &&  JOptionPane.showConfirmDialog(DgEmsAnalyDiff.this,"已经统计过差异分析，是否重新统计？","确认",2)==2){
						return;
					}
					new Jisuan().start();
				}
			});
		}
		return jButton1;
	}
	
	class Jisuan extends Thread {
		public void run(){
			try{
				CommonProgress.showProgressDialog(DgEmsAnalyDiff.this);
				CommonProgress.setMessage("系统正在统计资料，请稍后...");
				checkCancelAction.deleteEmsCyAll(new Request(CommonVars.getCurrUser()),head);
				checkCancelAction.calculateEmsCy(new Request(CommonVars.getCurrUser()),head);
				tableModelCy = EmsLogic.initTableCy(tableModelCy,jTable,head);
				CommonProgress.closeProgressDialog();
				if (tableModelCy.getRowCount() == 0){
					JOptionPane.showMessageDialog(DgEmsAnalyDiff.this,"统计数据为空！","提示",2);
				}
			} catch (Exception e){
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsAnalyDiff.this,"统计失败：！"+e.getMessage(),"提示",2);
			}
		}
	}
	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("打印");
			jButton2.setBounds(568, 4, 80, 25);
			jButton2.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (tableModelCy !=null && tableModelCy.getRowCount()>0){
						List list = checkCancelAction.findEmsCy(new Request(CommonVars.getCurrUser()),head);
					    CommonDataSource imgExgDS = new CommonDataSource(list);
					    List company = new Vector(); 
				    	company.add(CommonVars.getCurrUser().getCompany());
				    	CommonDataSource companyDS = new CommonDataSource(company);
				  
				    	InputStream masterReportStream = DgEmsAnalyDiff.class
			    			.getResourceAsStream("report/cyDataSumReport.jasper");			
				    	InputStream detailReportStream = DgEmsAnalyDiff.class
					    	.getResourceAsStream("report/cyDataSumReportSubb.jasper");
					try {
						JasperReport detailReport = (JasperReport)JRLoader.loadObject(detailReportStream);
						
						Map parameters = new HashMap();
						parameters.put("imgExgDS",imgExgDS);
						parameters.put("detailReport",detailReport);
						parameters.put("reportTilte",DgEmsAnalyDiff.this.getJTextField().getText());
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
		return jButton2;
	}
	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(0);
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setBottomComponent(getJPanel7());
			jSplitPane.setDividerLocation(35);
			jSplitPane.setTopComponent(getJPanel2());
		}
		return jSplitPane;
	}
	/**
	 * This method initializes jPanel7	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(new BorderLayout());
			jPanel7.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel7;
	}
	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	private JFooterTable getJTable() {
		if (jTable == null) {
			jTable = new JFooterTable();
		}
		return jTable;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JFooterScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JFooterScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(657, 4, 80, 25);
			jButton3.setText("关闭");
			jButton3.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					DgEmsAnalyDiff.this.dispose();
				}
			});
		}
		return jButton3;
	}
	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}
	/**
	 * @param tableModel The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setEditable(false);
			jTextField.setBounds(81, 5, 77, 22);
		}
		return jTextField;
	}
    }  //  @jve:decl-index=0:visual-constraint="10,10"
