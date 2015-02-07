package com.bestway.client.selfcheck;

import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Rectangle;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingWorker;

import java.awt.Point;
import javax.swing.JCheckBox;
import java.awt.ComponentOrientation;
import javax.swing.JButton;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.selfcheck.DgMaterialThatDayBalance.Find;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Condition;
import com.bestway.common.Request;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import javax.swing.JSplitPane;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgProductBOM extends JDialogBase {

	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel7 = null;
	private JLabel jLabel8 = null;
	private JLabel jLabel9 = null;
	private JLabel jLabel10 = null;
	private JLabel jLabel11 = null;
	private JTextField tfProductName = null;
	private JTextField tfFactoryMaterialName = null;
	private JTextField tfCustomMaterialName = null;
	private JTextField tfProductSpec = null;
	private JTextField tfCustomMaterialSpec = null;
	private JTextField tfFactoryMaterialSpec = null;
	private JComboBox cbMaterialType = null;
	private JComboBox cbDocumentType = null;
	private JTable jTable = null;
	private JCheckBox cbFactoryName = null;
	private JCheckBox cbCustomName = null;
	private JButton btQuerry = null;
	private JButton btprint = null;
	private JButton btClose = null;
	private JTableListModel tableModel = null;
	private ArrayList dataSource = null;
	private JSplitPane jSplitPane = null;
	private JCalendarComboBox cbStartDate = null;
	private JCalendarComboBox cbEndDate = null;
	private JButton btProductSpec = null;
	private JButton btProductName = null;
	private JButton btFacotryName = null;
	private JButton btCustomMaterialName = null;
	private JButton btFactoryMaterialSpec = null;
	private JButton btCustomMaterialSpec = null;
	
	/**
	 * 物料类型
	 */
	private String materielType = null;  //  @jve:decl-index=0:
	
	/**
	 * 查询action
	 */
	private CasAction casAction = null;
	/**
	 * This method initializes
	 * 
	 */
	public DgProductBOM() {
		super();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
		"casAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(733, 541));
		this.setContentPane(getJSplitPane());
		this.setTitle("单据折料情况");
		Vector list=new Vector();
		list.add(null);
		list.add(null);
		list.add(null);
		list.add(null);
		list.add(null);
		list.add(null);
		list.add(null);
		list.add(null);
		list.add(null);
		list.add(null);
		jTable.setModel(initTableModel(list));

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
			jPanel.add(getJPanel1(), null);
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
			jLabel11.setText("至");
			jLabel11.setSize(new Dimension(24, 22));
			jLabel11
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel11.setLocation(new Point(295, 130));
			jLabel10 = new JLabel();
			jLabel10.setText("报关日期");
			jLabel10.setSize(new Dimension(80, 20));
			jLabel10
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel10.setLocation(new Point(320, 130));
			jLabel9 = new JLabel();
			jLabel9.setText("报关物料规格");
			jLabel9.setSize(new Dimension(80, 20));
			jLabel9.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel9.setLocation(new Point(320, 105));
			jLabel8 = new JLabel();
			jLabel8.setText("工厂物料规格");
			jLabel8.setSize(new Dimension(80, 20));
			jLabel8.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel8.setLocation(new Point(320, 80));
			jLabel7 = new JLabel();
			jLabel7.setText("产品规格");
			jLabel7.setSize(new Dimension(80, 20));
			jLabel7.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel7.setLocation(new Point(320, 55));
			jLabel6 = new JLabel();
			jLabel6.setText("单据类型");
			jLabel6.setSize(new Dimension(80, 20));
			jLabel6.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel6.setLocation(new Point(320, 30));
			jLabel5 = new JLabel();
			jLabel5.setText("报关日期");
			jLabel5.setSize(new Dimension(80, 20));
			jLabel5.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel5.setLocation(new Point(20, 130));
			jLabel4 = new JLabel();
			jLabel4.setText("报关物料名称");
			jLabel4.setSize(new Dimension(80, 20));
			jLabel4.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel4.setLocation(new Point(20, 105));
			jLabel3 = new JLabel();
			jLabel3.setText("工厂物料名称");
			jLabel3.setSize(new Dimension(80, 20));
			jLabel3.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel3.setLocation(new Point(20, 80));
			jLabel1 = new JLabel();
			jLabel1.setText("产品名称");
			jLabel1.setSize(new Dimension(80, 20));
			jLabel1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel1.setLocation(new Point(20, 55));
			jLabel = new JLabel();
			jLabel.setText("物料类型");
			jLabel.setLocation(new Point(20, 30));
			jLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel.setSize(new Dimension(80, 20));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(10, 10, 696, 177));
			jPanel1.setBorder(BorderFactory.createTitledBorder(null,
					"\u6298\u6599\u9009\u9879",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 14), Color.blue));
			jPanel1.setFont(new Font("Dialog", Font.PLAIN, 12));
			jPanel1.add(jLabel, null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(jLabel5, null);
			jPanel1.add(jLabel6, null);
			jPanel1.add(jLabel7, null);
			jPanel1.add(jLabel8, null);
			jPanel1.add(jLabel9, null);
			jPanel1.add(jLabel10, null);
			jPanel1.add(jLabel11, null);
			jPanel1.add(getTfProductName(), null);
			jPanel1.add(getTfFactoryMaterialName(), null);
			jPanel1.add(getTfCustomMaterialName(), null);
			jPanel1.add(getTfProductSpec(), null);
			jPanel1.add(getTfCustomMaterialSpec(), null);
			jPanel1.add(getTfFactoryMaterialSpec(), null);
			jPanel1.add(getCbMaterialType(), null);
			jPanel1.add(getCbDocumentType(), null);
			jPanel1.add(getCbFactoryName(), null);
			jPanel1.add(getCbCustomName(), null);
			jPanel1.add(getBtQuerry(), null);
			jPanel1.add(getBtprint(), null);
			jPanel1.add(getBtClose(), null);
			jPanel1.add(getCbStartDate(), null);
			jPanel1.add(getCbEndDate(), null);
			jPanel1.add(getBtProductSpec(), null);
			jPanel1.add(getBtProductName(), null);
			jPanel1.add(getBtFacotryName(), null);
			jPanel1.add(getBtCustomMaterialName(), null);
			jPanel1.add(getBtFactoryMaterialSpec(), null);
			jPanel1.add(getBtCustomMaterialSpec(), null);
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
	 * This method initializes tfProductName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfProductName() {
		if (tfProductName == null) {
			tfProductName = new JTextField();
			tfProductName.setLocation(new Point(110, 55));
			tfProductName.setSize(new Dimension(150, 22));
		}
		return tfProductName;
	}

	/**
	 * This method initializes tfFactoryMaterialName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFactoryMaterialName() {
		if (tfFactoryMaterialName == null) {
			tfFactoryMaterialName = new JTextField();
			tfFactoryMaterialName.setLocation(new Point(110, 80));
			tfFactoryMaterialName.setSize(new Dimension(150, 22));
		}
		return tfFactoryMaterialName;
	}

	/**
	 * This method initializes tfCustomMaterialName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomMaterialName() {
		if (tfCustomMaterialName == null) {
			tfCustomMaterialName = new JTextField();
			tfCustomMaterialName.setLocation(new Point(110, 105));
			tfCustomMaterialName.setSize(new Dimension(150, 22));
		}
		return tfCustomMaterialName;
	}

	/**
	 * This method initializes tfProductSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfProductSpec() {
		if (tfProductSpec == null) {
			tfProductSpec = new JTextField();
			tfProductSpec.setLocation(new Point(405, 55));
			tfProductSpec.setSize(new Dimension(150, 22));
		}
		return tfProductSpec;
	}

	/**
	 * This method initializes tfCustomMaterialSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomMaterialSpec() {
		if (tfCustomMaterialSpec == null) {
			tfCustomMaterialSpec = new JTextField();
			tfCustomMaterialSpec.setLocation(new Point(405, 105));
			tfCustomMaterialSpec.setSize(new Dimension(150, 22));
		}
		return tfCustomMaterialSpec;
	}

	/**
	 * This method initializes tfFactoryMaterialSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFactoryMaterialSpec() {
		if (tfFactoryMaterialSpec == null) {
			tfFactoryMaterialSpec = new JTextField();
			tfFactoryMaterialSpec.setLocation(new Point(405, 80));
			tfFactoryMaterialSpec.setSize(new Dimension(150, 22));
		}
		return tfFactoryMaterialSpec;
	}

	/**
	 * This method initializes cbMaterialType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbMaterialType() {
		if (cbMaterialType == null) {
			cbMaterialType = new JComboBox();
			cbMaterialType.setLocation(new Point(110, 30));
			cbMaterialType.setSize(new Dimension(173, 20));
		}
		return cbMaterialType;
	}

	/**
	 * This method initializes cbDocumentType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbDocumentType() {
		if (cbDocumentType == null) {
			cbDocumentType = new JComboBox();
			cbDocumentType.setLocation(new Point(405, 30));
			cbDocumentType.setSize(new Dimension(173, 20));
		}
		return cbDocumentType;
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
	 * This method initializes cbFactoryName
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbFactoryName() {
		if (cbFactoryName == null) {
			cbFactoryName = new JCheckBox();
			cbFactoryName.setText("按工厂名称汇总");
			cbFactoryName.setLocation(new Point(86, 153));
			cbFactoryName.setSize(new Dimension(143, 21));
		}
		return cbFactoryName;
	}

	/**
	 * This method initializes cbCustomName
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCustomName() {
		if (cbCustomName == null) {
			cbCustomName = new JCheckBox();
			cbCustomName.setText("按报关名称汇总");
			cbCustomName.setLocation(new Point(317, 153));
			cbCustomName.setSize(new Dimension(157, 21));
		}
		return cbCustomName;
	}

	/**
	 * This method initializes btQuerry
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtQuerry() {
		if (btQuerry == null) {
			btQuerry = new JButton();
			btQuerry.setText("查询");
			btQuerry.setSize(new Dimension(65, 23));
			btQuerry.setLocation(new Point(600, 30));
			btQuerry.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {//查询
					//组织查询条件
					List<Condition> conditions = new ArrayList<Condition>();
					//生效的单据
					conditions.add(new Condition("and", null,"billMaster.isValid", "=",new Boolean(true), null));
					//生效日期
					if (cbStartDate.getDate() != null) { // 开始日期
						Date date = cbStartDate.getDate();
						date.setHours(0);
						date.setMinutes(0);
						date.setSeconds(0);
						conditions.add(new Condition("and", null,"billMaster.validDate", ">=",CommonVars.getBeginDate(cbStartDate.getDate()), null));
					}
					//工厂名称
					if (!tfFactoryMaterialName.getText().trim().equals("")) {
						conditions.add(new Condition("and", null, "ptName",
								"=", tfFactoryMaterialName.getText(), null));
					}
					//工厂规格
					if (!tfFactoryMaterialSpec.getText().trim().equals("")) {
						conditions.add(new Condition("and", null, "ptSpec",
								"=", tfFactoryMaterialSpec.getText(), null));
					}
					//报关名称
					if (!tfCustomMaterialName.getText().trim().equals("")) {
						conditions.add(new Condition("and", null, "hsName",
								"=", tfCustomMaterialName.getText(), null));
					}
					 //报关规格
					if (!tfCustomMaterialSpec.getText().trim().equals("")) {
						conditions.add(new Condition("and", null, "hsSpec",
								"=", tfCustomMaterialSpec.getText(), null));
					}
					
					String billDetail = BillUtil.getBillDetailTableNameByMaterielType(getMaterielType());
					//执行查询线程
					new Find(conditions,"",billDetail,cbFactoryName.isSelected(),cbCustomName.isSelected()).execute();
				}
			});
		}
		return btQuerry;
	}
	
	class Find extends SwingWorker{
		//查询条件
		List<Condition> conditions = null;
        //查询目标表
		String billDetail = null;
		//报关名称汇总
		Boolean ishsTaotal = false;
		//显示负数结存
		Boolean isptTaotal = false;
		
		public Find(List<Condition> conditions, String orderBy,
				String billDetail,Boolean isptTaotal,Boolean ishsTaotal) {
			this.conditions = conditions;
			this.billDetail = billDetail;
			this.ishsTaotal = ishsTaotal;
			this.isptTaotal = isptTaotal;
		}

		@Override
		protected Object doInBackground() throws Exception {//后台计算
			//查询返回结果
			List list = null;
			//查询
			CommonProgress.showProgressDialog(DgProductBOM.this);
			CommonProgress.setMessage("系统正在执行查询，请稍后...");
//			list = casAction.getCurrentBillDetailBom(new Request(CommonVars.getCurrUser()), conditions,
//					getMaterielType(),cbFactoryName.isSelected(),cbCustomName.isSelected());
			return list;
		}

		@Override
		protected void done() {//更新视图
			List list=null;
			try {
				list = (List)this.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(list==null){
				list=new ArrayList();
			}
			CommonProgress.closeProgressDialog();
			initTableModel(list);
		}
	}

	/**
	 * This method initializes btprint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtprint() {
		if (btprint == null) {
			btprint = new JButton();
			btprint.setText("打印");
			btprint.setSize(new Dimension(65, 23));
			btprint.setLocation(new Point(600, 75));
		}
		return btprint;
	}

	/**
	 * This method initializes btClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtClose() {
		if (btClose == null) {
			btClose = new JButton();
			btClose.setText("关闭");
			btClose.setSize(new Dimension(65, 23));
			btClose.setLocation(new Point(600, 120));
		}
		return btClose;
	}

	/**
	 * This method initializes dataSource
	 * 
	 * @return java.util.ArrayList
	 */
	private ArrayList getDataSource() {
		if (dataSource == null) {
			dataSource = new ArrayList();
		}
		return dataSource;
	}

	/**
	 * This method initializes tableModel
	 * 
	 * @return com.bestway.client.util.mutispan.AttributiveCellTableModel
	 */
	private JTableListModel initTableModel(List list) {
		if (tableModel == null) {
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List<JTableListColumn> InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("单据日期", null, 100));
							list.add(addColumn("单据类型", null, 100));
							list.add(addColumn("产品名称", null, 100));
							list.add(addColumn("产品规格", null, 100));
							list.add(addColumn("工厂名称", null, 100));
							list.add(addColumn("工厂规格", null, 100));
							list.add(addColumn("工厂单位", null, 100));
							list.add(addColumn("报关名称", null, 100));
							list.add(addColumn("报关规格", null, 100));
							list.add(addColumn("报关单位", null, 100));
							list.add(addColumn("单耗", null, 100));
							list.add(addColumn("损耗", null, 100));
							list.add(addColumn("单项用量", null, 100));
							list.add(addColumn("总用量", null, 100));
							list.add(addColumn("折算系数", null, 100));
							list.add(addColumn("报关数量", null, 100));
							return list;
						}
					});
		}
		return tableModel;
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
			jSplitPane.setDividerLocation(190);
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setTopComponent(getJPanel());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes cbStartDate	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getCbStartDate() {
		if (cbStartDate == null) {
			cbStartDate = new JCalendarComboBox();
			cbStartDate.setLocation(new Point(110, 130));
			cbStartDate.setSize(new Dimension(173, 20));
		}
		return cbStartDate;
	}

	/**
	 * This method initializes cbEndDate	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getCbEndDate() {
		if (cbEndDate == null) {
			cbEndDate = new JCalendarComboBox();
			cbEndDate.setLocation(new Point(405, 130));
			cbEndDate.setSize(new Dimension(173, 20));
		}
		return cbEndDate;
	}

	/**
	 * This method initializes btProductSpec	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtProductSpec() {
		if (btProductSpec == null) {
			btProductSpec = new JButton();
			btProductSpec.setText("...");
			btProductSpec.setSize(new Dimension(18, 20));
			btProductSpec.setLocation(new Point(560, 55));
		}
		return btProductSpec;
	}

	/**
	 * This method initializes btProductName	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtProductName() {
		if (btProductName == null) {
			btProductName = new JButton();
			btProductName.setText("...");
			btProductName.setSize(new Dimension(18, 20));
			btProductName.setLocation(new Point(265, 55));
		}
		return btProductName;
	}

	/**
	 * This method initializes btFacotryName	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtFacotryName() {
		if (btFacotryName == null) {
			btFacotryName = new JButton();
			btFacotryName.setText("...");
			btFacotryName.setSize(new Dimension(18, 20));
			btFacotryName.setLocation(new Point(265, 80));
		}
		return btFacotryName;
	}

	/**
	 * This method initializes btCustomMaterialName	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtCustomMaterialName() {
		if (btCustomMaterialName == null) {
			btCustomMaterialName = new JButton();
			btCustomMaterialName.setText("...");
			btCustomMaterialName.setSize(new Dimension(18, 20));
			btCustomMaterialName.setLocation(new Point(265, 105));
		}
		return btCustomMaterialName;
	}

	/**
	 * This method initializes btFactoryMaterialSpec	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtFactoryMaterialSpec() {
		if (btFactoryMaterialSpec == null) {
			btFactoryMaterialSpec = new JButton();
			btFactoryMaterialSpec.setText("...");
			btFactoryMaterialSpec.setSize(new Dimension(18, 20));
			btFactoryMaterialSpec.setLocation(new Point(560, 80));
		}
		return btFactoryMaterialSpec;
	}

	/**
	 * This method initializes btCustomMaterialSpec	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtCustomMaterialSpec() {
		if (btCustomMaterialSpec == null) {
			btCustomMaterialSpec = new JButton();
			btCustomMaterialSpec.setText("...");
			btCustomMaterialSpec.setSize(new Dimension(18, 20));
			btCustomMaterialSpec.setLocation(new Point(560, 105));
		}
		return btCustomMaterialSpec;
	}

	/**
	 * @return Returns the intOutFlat.
	 */
	public String getMaterielType() {
		return materielType;
	}

	/**
	 * @param intOutFlat
	 *            The intOutFlat to set.
	 */
	public void setMaterielType(String intOutFlat) {
		this.materielType = intOutFlat;
	}
}
