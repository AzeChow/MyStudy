package com.bestway.bcus.client.cas.otherreport;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.GridBagLayout;
import java.io.InputStream;

public class DgMaterielStorageInfo extends JDialogBase {

	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JCalendarComboBox startDate = null;
	private JCalendarComboBox endDate = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JLabel jLabel2 = null;
	private JTableListModel tableModel = null;
	private JRadioButton jRadioButton = null;
	private JRadioButton jRadioButton1 = null;
	private CasAction casAction = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	/**
	 * This method initializes 
	 * 
	 */
	public DgMaterielStorageInfo() {
		super();
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
		"casAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setTitle("车间材料库存统计表");
        this.setSize(new Dimension(664, 546));
        this.setContentPane(getJSplitPane());
        initUIComponents();
	}
	
	private void initUIComponents() {
		jRadioButton.setSelected(true);
		jRadioButton1.setSelected(false);
		int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
		.getYearInt();
		Calendar beginClarendar = Calendar.getInstance();
		beginClarendar.set(Calendar.YEAR, year);
		beginClarendar.set(Calendar.MONTH, 0);
		beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
		beginClarendar.set(Calendar.HOUR_OF_DAY, 0);
		beginClarendar.set(Calendar.MINUTE, 0);
		beginClarendar.set(Calendar.SECOND, 0);
		this.startDate.setDate(beginClarendar.getTime());
		this.startDate.setCalendar(beginClarendar);
		
		Calendar endClarendar = Calendar.getInstance();
		endClarendar.set(Calendar.YEAR, year);
		endClarendar.set(Calendar.MONTH, 11);
		endClarendar.set(Calendar.DAY_OF_MONTH, 31);
		beginClarendar.set(Calendar.HOUR_OF_DAY, 23);
		beginClarendar.set(Calendar.MINUTE, 59);
		beginClarendar.set(Calendar.SECOND, 59);
		this.endDate.setDate(endClarendar.getTime());
		this.endDate.setCalendar(endClarendar);
		initTable(new ArrayList());
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(5);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setDividerLocation(60);
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
			jLabel2.setBounds(new Rectangle(9, 34, 416, 30));
			jLabel2.setText("备注:数量1为工厂库存数,数量2为报关库存数");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(196, 6, 54, 23));
			jLabel1.setText("结束日期:");
			jLabel = new JLabel();
			jLabel.setText("开始日期:");
			jLabel.setBounds(new Rectangle(9, 6, 54, 23));
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(getJCalendarComboBox1(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJRadioButton(), null);
			jPanel.add(getJRadioButton1(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (startDate == null) {
			startDate = new JCalendarComboBox();
			startDate.setBounds(new Rectangle(71, 6, 86, 20));
		}
		return startDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (endDate == null) {
			endDate = new JCalendarComboBox();
			endDate.setBounds(new Rectangle(257, 6, 86, 20));
		}
		return endDate;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(477, 33, 78, 23));
			jButton.setText("查询");
			jButton.addActionListener(new java.awt.event.ActionListener(){

				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (startDate.getDate() == null
							|| endDate.getDate() == null) {
						JOptionPane.showMessageDialog(DgMaterielStorageInfo.this,
								"查询数据开始日期或结束日期不可为空!", "提示", 2);
						return;
					}
					new searchThread().start();
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
			jButton1.setBounds(new Rectangle(565, 33, 78, 23));
			jButton1.setText("打印");
			jButton1.addActionListener(new java.awt.event.ActionListener(){

				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(DgMaterielStorageInfo.this.tableModel.getList()==null||
							DgMaterielStorageInfo.this.tableModel.getList().size()==0){
						JOptionPane.showMessageDialog(DgMaterielStorageInfo.this, "没有数据可列印！", "提示", 2);
						return;
					}
					print();
				}
				
			});
		}
		return jButton1;
	}
	
	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						Boolean isPtNo = jRadioButton.isSelected();
						String joinStr = "";
						if(isPtNo){
							joinStr = "工厂";
						}else{
							joinStr = "报关";
						}
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn(joinStr+"品名", "bill2", 100));
						list.add(addColumn(joinStr+"规格、型号", "bill3", 100));
						list.add(addColumn(joinStr+"单位", "bill4", 150));
						if(isPtNo){
							list.add(addColumn("BOM编号(料号)", "bill1", 120));
						}
						list.add(addColumn("数量1", "billSum1", 100));
						list.add(addColumn("数量2", "billSum2", 100));
						if(isPtNo){
							list.add(addColumn("报关名称", "bill5", 120));
							list.add(addColumn("报关规格", "bill6", 120));
							list.add(addColumn("报关单位", "bill7", 120));
						}

						return list;
					}
				});
	}

	/**
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new Rectangle(392, 6, 110, 21));
			jRadioButton.setText("按工厂品名查询");
			jRadioButton.addActionListener(new java.awt.event.ActionListener(){

				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					jRadioButton.setSelected(true);
					jRadioButton1.setSelected(false);
				}
				
			});
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setBounds(new Rectangle(520, 6, 120, 21));
			jRadioButton1.setText("按报关品名查询");
			jRadioButton1.addActionListener(new java.awt.event.ActionListener(){

				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					jRadioButton1.setSelected(true);
					jRadioButton.setSelected(false);
				}
				
			});
		}
		return jRadioButton1;
	}
	
	class searchThread extends Thread {
		
		public void run() {
			jButton.setEnabled(false);
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在获取数据，请稍候...");
				String taskId = CommonProgress.getExeTaskId();
//				CommonStepProgress.showStepProgressDialog(taskId);
//				CommonStepProgress.setStepMessage("系统正在获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				
				List list = casAction.findF25OfBalanceInfo(request, 
						jRadioButton.isSelected(), taskId, startDate.getDate(), endDate.getDate());
				initTable(list);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(DgMaterielStorageInfo.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
				initTable(new ArrayList());
			}finally{
				CommonProgress.closeProgressDialog();
			}
			jButton.setEnabled(true);
		}
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
			jPanel1.add(getJScrollPane(), BorderLayout.CENTER);
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
			jScrollPane.setViewportView(getJTable());
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

	private void print(){
		List list = this.tableModel.getList();
		CommonDataSource imgExgDS = new CommonDataSource(list);
		InputStream ReportStream = null;
		if(jRadioButton.isSelected()){
			ReportStream = DgMaterielStorageInfo.class
				.getResourceAsStream("report/MaterielStoragePtNoReport.jasper");
		}else{
			ReportStream = DgMaterielStorageInfo.class
			.getResourceAsStream("report/MaterielStorageHsReport.jasper");
		}
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			SimpleDateFormat bartDateFormat = new SimpleDateFormat(
					"yyyy年MM月dd日");
			String beginDate = bartDateFormat.format(this.startDate.getDate());
			String endDate = bartDateFormat.format(this.endDate.getDate());
			parameters.put("startDate", beginDate);
			parameters.put("endDate", endDate);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					ReportStream, parameters, imgExgDS);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
