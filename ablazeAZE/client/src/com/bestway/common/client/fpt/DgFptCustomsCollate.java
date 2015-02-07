/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgFptCustomsCollate extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JTableListModel tableModel = null;
	private List list = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane1 = null;
	private JLabel jLabel = null;
	private JButton jButton = null;
	private List lsResult = null;
	private JButton jButton1 = null;
	private FptManageAction fptManageAction = null;
	private JComboBox jComboBox = null;
	private JLabel jLabel1 = null;
	private JButton jButton2 = null;
	private JComboBox jComboBox1 = null;
	private JLabel jLabel2 = null;
	/**
	 * This is the default constructor
	 */
	public DgFptCustomsCollate() {
		super();
		this.fptManageAction = (FptManageAction) CommonVars
        .getApplicationContext().getBean(
                "fptManageAction");
		initialize();
		
	}

	private void initUIComponents(){
		
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("送货&转厂数量/金额对照表");
		this.setSize(780, 454);
		fptManageAction.findPutRecord(new Request(
				CommonVars.getCurrUser()));
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {

				initUIComponents();
				list = new Vector();
				initTable(list);	
		    }
		});
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		if(list==null){
			list = new ArrayList();
		}
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("厂商名称", "scmCoc", 150));
						list.add(addColumn("商品序号", "seqNum", 60));
						list.add(addColumn("商品名称/规格", "nameSpec", 150));
						list.add(addColumn("商品编码", "complex", 80));
						list.add(addColumn("单位", "unitName", 60));
						list.add(addColumn("类别", "type", 60));
						list.add(addColumn("承接("+String.valueOf(Integer.parseInt(jComboBox.getSelectedItem().toString())-1)+")", "beginNum", 80));
						list.add(addColumn("一月份", "janNum", 80));
						list.add(addColumn("二月份", "febNum", 80));
						list.add(addColumn("三月份", "marNum", 80));				
						list.add(addColumn("四月份", "aprNum", 80));
						list.add(addColumn("五月份", "mayNum", 80));	
						list.add(addColumn("六月份", "junNum", 80));
						list.add(addColumn("七月份", "julNum", 80));
						list.add(addColumn("八月份", "augNum", 80));
						list.add(addColumn("九月份", "sepNum", 80));
						list.add(addColumn("十月份", "octNum", 80));
						list.add(addColumn("十一月份", "movNum", 80));
						list.add(addColumn("十二月份", "decNum", 80));
						list.add(addColumn("本年度合计", "yearTotalNum", 80));
						list.add(addColumn("总累计", "totalNum", 80));
						
						return list;
					}
				});
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
			jSplitPane.setDividerLocation(70);
			jSplitPane.setDividerSize(0);
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
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(264,41,75,23));
			jLabel2.setText("进出口类型：");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(342,15,33,24));
			jLabel1.setText("年份");
			jLabel = new JLabel();
			jLabel.setText("送货&转厂数量/金额对照表");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(5, 2, 326, 34);
			
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 24));
			jLabel.setForeground(java.awt.Color.blue);
			
			jPanel.add(jLabel, null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJComboBox1(), null);
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
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
		}
		return jScrollPane;
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
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable());
		}
		return jScrollPane1;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(470, 15, 94, 23);
			jButton.setText("查询");
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    					
					new find().start();
				}
			});
		}
		return jButton;
	}
	class find extends Thread{
		
		@Override
		public void run(){
			 try {
	            CommonProgress.showProgressDialog();
	            CommonProgress.setMessage("系统正获取数据，请稍后...");
	            String year = jComboBox.getSelectedItem().toString();
	            boolean isType = true;
	            if (jComboBox1.getSelectedItem().equals("进口")){
	            	isType = true;
	            } else {
	            	isType = false;
	            }
			    lsResult = fptManageAction.traCustomsCollate(new Request(
						CommonVars.getCurrUser()),year,isType);
				
				CommonProgress.closeProgressDialog();
			 } catch (Exception e) {
		        CommonProgress.closeProgressDialog();
		        JOptionPane.showMessageDialog(DgFptCustomsCollate.this,
		                "获取数据失败：！" + e.getMessage(), "提示", 2);    
			 } finally {			 	
			 	initTable(lsResult);
			 }
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
			jButton1.setBounds(680, 15, 86, 23);
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.addItem("2000");
			jComboBox.addItem("2001");
			jComboBox.addItem("2002");
			jComboBox.addItem("2003");
			jComboBox.addItem("2004");
			jComboBox.addItem("2005");
			jComboBox.addItem("2006");
			jComboBox.addItem("2007");
			jComboBox.addItem("2008");
			jComboBox.addItem("2009");
			jComboBox.addItem("2010");
			jComboBox.addItem("2011");
			jComboBox.addItem("2012");
			jComboBox.addItem("2013");
			jComboBox.addItem("2014");
			jComboBox.addItem("2015");
			jComboBox.setBounds(new java.awt.Rectangle(379,15,80,24));
			jComboBox.setSelectedItem("2007");
		}
		return jComboBox;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new java.awt.Rectangle(573,15,94,23));
			jButton2.setText("打印");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null
							|| tableModel.getList().size() < 1) {
						return;
					}
					CommonDataSource imgExgDS = new CommonDataSource(tableModel.getList());
					
					List company = new Vector(); //只有一条记录
					company.add(CommonVars.getCurrUser().getCompany());
					CommonDataSource companyDS = new CommonDataSource(company);
					
					InputStream masterReportStream = DgFptCustomsCollate.class
						.getResourceAsStream("report/TransferNumMoneyState.jasper");			
					InputStream detailReportStream = DgFptCustomsCollate.class
						.getResourceAsStream("report/TransferNumMoneyStateSub.jasper");	
					try {
						JasperReport detailReport = (JasperReport)JRLoader.loadObject(detailReportStream);							
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("printDate", CommonVars.nowToDate());	
						parameters.put("imgExgDS",imgExgDS);//子数据源
						parameters.put("detailReport",detailReport);//子报表
						parameters.put("year",jComboBox.getSelectedItem().toString());
						parameters.put("year1",String.valueOf(Integer.parseInt(jComboBox.getSelectedItem().toString())-1));
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
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jComboBox1	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(new java.awt.Rectangle(341,42,118,22));
			jComboBox1.addItem("进口");
			jComboBox1.addItem("出口");
		}
		return jComboBox1;
	}
          }  //  @jve:decl-index=0:visual-constraint="10,10"
